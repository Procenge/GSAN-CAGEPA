/*
 * Copyright (C) 2007-2007 the GSAN � Sistema Integrado de Gest�o de Servi�os de Saneamento
 *
 * This file is part of GSAN, an integrated service management system for Sanitation
 *
 * GSAN is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License.
 *
 * GSAN is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place � Suite 330, Boston, MA 02111-1307, USA
 */

/*
 * GSAN � Sistema Integrado de Gest�o de Servi�os de Saneamento
 * Copyright (C) <2007> 
 * Adriano Britto Siqueira
 * Alexandre Santos Cabral
 * Ana Carolina Alves Breda
 * Ana Maria Andrade Cavalcante
 * Aryed Lins de Ara�jo
 * Bruno Leonardo Rodrigues Barros
 * Carlos Elmano Rodrigues Ferreira
 * Cl�udio de Andrade Lira
 * Denys Guimar�es Guenes Tavares
 * Eduardo Breckenfeld da Rosa Borges
 * Fab�ola Gomes de Ara�jo
 * Fl�vio Leonardo Cavalcanti Cordeiro
 * Francisco do Nascimento J�nior
 * Homero Sampaio Cavalcanti
 * Ivan S�rgio da Silva J�nior
 * Jos� Edmar de Siqueira
 * Jos� Thiago Ten�rio Lopes
 * K�ssia Regina Silvestre de Albuquerque
 * Leonardo Luiz Vieira da Silva
 * M�rcio Roberto Batista da Silva
 * Maria de F�tima Sampaio Leite
 * Micaela Maria Coelho de Ara�jo
 * Nelson Mendon�a de Carvalho
 * Newton Morais e Silva
 * Pedro Alexandre Santos da Silva Filho
 * Rafael Corr�a Lima e Silva
 * Rafael Francisco Pinto
 * Rafael Koury Monteiro
 * Rafael Palermo de Ara�jo
 * Raphael Veras Rossiter
 * Roberto Sobreira Barbalho
 * Rodrigo Avellar Silveira
 * Rosana Carvalho Barbosa
 * S�vio Luiz de Andrade Cavalcante
 * Tai Mu Shih
 * Thiago Augusto Souza do Nascimento
 * Tiago Moreno Rodrigues
 * Vivianne Barbosa Sousa
 * 
 * GSANPCG
 * Andr� Nishimura
 * Eduardo Henrique Bandeira Carneiro da Silva
 * Saulo Vasconcelos de Lima
 * Virginia Santos de Melo
 *
 * Este programa � software livre; voc� pode redistribu�-lo e/ou
 * modific�-lo sob os termos de Licen�a P�blica Geral GNU, conforme
 * publicada pela Free Software Foundation; vers�o 2 da
 * Licen�a.
 * Este programa � distribu�do na expectativa de ser �til, mas SEM
 * QUALQUER GARANTIA; sem mesmo a garantia impl�cita de
 * COMERCIALIZA��O ou de ADEQUA��O A QUALQUER PROP�SITO EM
 * PARTICULAR. Consulte a Licen�a P�blica Geral GNU para obter mais
 * detalhes.
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU
 * junto com este programa; se n�o, escreva para Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 * 02111-1307, USA.
 */

package gcom.gui.cobranca;

import gcom.cobranca.bean.ContaValoresHelper;
import gcom.cobranca.bean.GuiaPagamentoValoresHelper;
import gcom.cobranca.bean.IndicadoresParcelamentoHelper;
import gcom.cobranca.bean.NegociacaoOpcoesParcelamentoHelper;
import gcom.cobranca.parcelamento.Parcelamento;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.Util;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

/**
 * Permite efetuar o parcelamento dos d�bitos de um im�vel
 * [UC0214] Efetuar Parcelamento de D�bitos
 * 
 * @author Roberta Costa
 * @date 24/01/2006
 */
public class ProcessarEfetuarParcelamentoDebitosProcesso2Action
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("processo2");
		HttpSession sessao = httpServletRequest.getSession(false);
		Fachada fachada = Fachada.getInstancia();
		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");
		DynaActionForm efetuarParcelamentoDebitosActionForm = (DynaActionForm) actionForm;
		if("sim".equalsIgnoreCase((String) httpServletRequest.getAttribute("popupEfetuarParcelamento"))
						|| "sim".equalsIgnoreCase(httpServletRequest.getParameter("popupEfetuarParcelamento"))){
			httpServletRequest.setAttribute("popupEfetuarParcelamento", "sim");
		}

		// if(httpServletRequest.getParameter("idCampoEnviarDados") != null){ // caso tenha sido
		// // chamado pelo
		// // calendario pop-up
		// CalculadorParcelamentoHelper.recalcularParcelamento(httpServletRequest, fachada, sessao,
		// efetuarParcelamentoDebitosActionForm,
		// "sessao");
		// }else{
			CalculadorParcelamentoHelper.recalcularParcelamento(httpServletRequest, fachada, sessao, efetuarParcelamentoDebitosActionForm,
							"request");
		// }

		boolean marcadaEP = false;
		if(sessao.getAttribute("marcadaEP") != null){
			marcadaEP = sessao.getAttribute("marcadaEP").equals("true");
		}

		// Verifica se as entradas escolhidas s�o menores que a entrada minima
		if(marcadaEP){

			Integer indicadorGuiasPagamento = Integer
							.valueOf((String) (efetuarParcelamentoDebitosActionForm.get("indicadorGuiasPagamento")));
			Integer indicadorDebitosACobrar = Integer
							.valueOf((String) (efetuarParcelamentoDebitosActionForm.get("indicadorDebitosACobrar")));
			Integer indicadorCreditoARealizar = Integer.valueOf((String) (efetuarParcelamentoDebitosActionForm
							.get("indicadorCreditoARealizar")));
			String valorDebitoACobrarParcelamentoImovel = ((String) efetuarParcelamentoDebitosActionForm
							.get("valorDebitoACobrarParcelamentoImovel"));
			String indicadorAcrescimosImpotualidade = (String) efetuarParcelamentoDebitosActionForm.get("indicadorAcrescimosImpotualidade");
			Integer indicadorContasRevisao = Integer.valueOf((String) efetuarParcelamentoDebitosActionForm.get("indicadorContasRevisao"));
			String inicioIntervaloParcelamento = (String) efetuarParcelamentoDebitosActionForm.get("inicioIntervaloParcelamento");
			String fimIntervaloParcelamento = (String) efetuarParcelamentoDebitosActionForm.get("fimIntervaloParcelamento");
			String indicadorParcelamentoCobrancaBancaria = (String) efetuarParcelamentoDebitosActionForm
							.get("indicadorParcelamentoCobrancaBancaria");

			Collection<ContaValoresHelper> colecaoContaValores = (Collection<ContaValoresHelper>) sessao
							.getAttribute("colecaoContaValores");
			Integer inicioIntervaloParcelamentoFormatado = null;
			if(inicioIntervaloParcelamento != null && !inicioIntervaloParcelamento.trim().equals("")){
				inicioIntervaloParcelamentoFormatado = Util.formatarMesAnoComBarraParaAnoMes(inicioIntervaloParcelamento);
			}
			Integer fimIntervaloParcelamentoFormatado = null;
			if(fimIntervaloParcelamento != null && !fimIntervaloParcelamento.trim().equals("")){
				fimIntervaloParcelamentoFormatado = Util.formatarMesAnoComBarraParaAnoMes(fimIntervaloParcelamento);
			}
			BigDecimal valorDebitoACobrarParcelamentoImovelBigDecimal = BigDecimal.ZERO;
			if(valorDebitoACobrarParcelamentoImovel != null && !valorDebitoACobrarParcelamentoImovel.trim().equals("")){
				valorDebitoACobrarParcelamentoImovelBigDecimal = Util.formatarMoedaRealparaBigDecimal(valorDebitoACobrarParcelamentoImovel);
			}

			IndicadoresParcelamentoHelper indicadoresParcelamentoHelper = new IndicadoresParcelamentoHelper();
			indicadoresParcelamentoHelper.setIndicadorContasRevisao(indicadorContasRevisao);
			indicadoresParcelamentoHelper.setIndicadorDebitosACobrar(indicadorDebitosACobrar);
			indicadoresParcelamentoHelper.setIndicadorCreditoARealizar(indicadorCreditoARealizar);
			indicadoresParcelamentoHelper.setIndicadorGuiasPagamento(indicadorGuiasPagamento);
			indicadoresParcelamentoHelper.setIndicadorAcrescimosImpotualidade(Integer.valueOf(indicadorAcrescimosImpotualidade));

			if(!Util.isVazioOuBranco(indicadorParcelamentoCobrancaBancaria)){
				indicadoresParcelamentoHelper.setIndicadorCobrancaBancaria(Integer.valueOf(indicadorParcelamentoCobrancaBancaria));
			}

			// Pega vari�veis do formul�rio
			String codigoImovel = (String) efetuarParcelamentoDebitosActionForm.get("matriculaImovel");
			Integer situacaoAguaId = Integer.valueOf((String) efetuarParcelamentoDebitosActionForm.get("situacaoAguaId"));
			Integer situacaoEsgotoId = Integer.valueOf((String) efetuarParcelamentoDebitosActionForm.get("situacaoEsgotoId"));
			Integer perfilImovel = Integer.valueOf((String) efetuarParcelamentoDebitosActionForm.get("perfilImovel"));
			Integer numeroReparcelamentoConsecutivos = Integer.valueOf((String) efetuarParcelamentoDebitosActionForm
							.get("numeroReparcelamentoConsecutivos"));
			String resolucaoDiretoria = (String) efetuarParcelamentoDebitosActionForm.get("resolucaoDiretoria");

			BigDecimal valorDebitoTotalAtualizado = Util.formatarMoedaRealparaBigDecimal((String) efetuarParcelamentoDebitosActionForm
							.get("valorDebitoTotalAtualizado"));

			// O indicador s� ser� usado caso a situa��o de �gua do Im�vel seja
			// SUPRIMIDO, SUPRIMIDO PARCIAL, SUPRIMIDO PARCIAL A PEDIDO
			Integer indicadorRestabelecimento = Integer.valueOf("0");
			if(efetuarParcelamentoDebitosActionForm.get("indicadorRestabelecimento") != null
							&& !efetuarParcelamentoDebitosActionForm.get("indicadorRestabelecimento").equals("")){
				indicadorRestabelecimento = Integer.valueOf(String.valueOf(efetuarParcelamentoDebitosActionForm
								.get("indicadorRestabelecimento")));
			}

			BigDecimal valorTotalMultas = BigDecimal.ZERO;
			BigDecimal valorTotalJurosMora = BigDecimal.ZERO;
			BigDecimal valorTotalAtualizacoesMonetarias = BigDecimal.ZERO;

			Collection<GuiaPagamentoValoresHelper> colecaoGuiaPagamentoHelper = (Collection<GuiaPagamentoValoresHelper>) sessao
							.getAttribute("colecaoGuiaPagamentoValores");

			if(colecaoContaValores != null && !colecaoContaValores.isEmpty()){
				Iterator<ContaValoresHelper> contaValores = colecaoContaValores.iterator();
				while(contaValores.hasNext()){
					ContaValoresHelper contaValoresHelper = contaValores.next();

					// Caso n�o tenha marcado a conta
					if(contaValoresHelper.getIndicadorContasDebito() == null){
						if(contaValoresHelper.getValorMulta() != null){
							valorTotalMultas.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
							valorTotalMultas = valorTotalMultas.add(contaValoresHelper.getValorMulta());
						}
						if(contaValoresHelper.getValorJurosMora() != null){
							valorTotalJurosMora.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
							valorTotalJurosMora = valorTotalJurosMora.add(contaValoresHelper.getValorJurosMora());
						}
						if(contaValoresHelper.getValorAtualizacaoMonetaria() != null){
							valorTotalAtualizacoesMonetarias.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
							valorTotalAtualizacoesMonetarias = valorTotalAtualizacoesMonetarias.add(contaValoresHelper
											.getValorAtualizacaoMonetaria());
						}
					}
				}
			}

			BigDecimal valorEntradaInformado = BigDecimal.ZERO;
			if(efetuarParcelamentoDebitosActionForm.get("valorEntradaInformado") != null
							&& !efetuarParcelamentoDebitosActionForm.get("valorEntradaInformado").equals("")
							&& !efetuarParcelamentoDebitosActionForm.get("valorEntradaInformado").equals("0.00")){
				valorEntradaInformado = Util.formatarMoedaRealparaBigDecimal((String) efetuarParcelamentoDebitosActionForm
								.get("valorEntradaInformado"));
			}

			String dataVencimentoEntradaParcelamento = null;
			
			if(httpServletRequest.getParameter("idCampoEnviarDados") != null){
				// caso tenha sido chamado pelo calendario pop-up
				dataVencimentoEntradaParcelamento = httpServletRequest.getParameter("idCampoEnviarDados");
				efetuarParcelamentoDebitosActionForm.set("dataVencimentoEntradaParcelamento", dataVencimentoEntradaParcelamento);

			} else  if(efetuarParcelamentoDebitosActionForm.get("dataVencimentoEntradaParcelamento") != null
							&& !efetuarParcelamentoDebitosActionForm.get("dataVencimentoEntradaParcelamento").equals("")){
				dataVencimentoEntradaParcelamento = (String) efetuarParcelamentoDebitosActionForm.get("dataVencimentoEntradaParcelamento");
			}

			// [SB0002] - Obter Op��es de Parcelamento de acordo com a entrada informada
			NegociacaoOpcoesParcelamentoHelper opcoesParcelamento = fachada.obterOpcoesDeParcelamento(Integer.valueOf(resolucaoDiretoria),
							Integer.valueOf(codigoImovel), BigDecimal.ZERO, situacaoAguaId, situacaoEsgotoId, perfilImovel,
							inicioIntervaloParcelamento, indicadorRestabelecimento, colecaoContaValores, valorDebitoTotalAtualizado,
							valorTotalMultas, valorTotalJurosMora, valorTotalAtualizacoesMonetarias, numeroReparcelamentoConsecutivos,
							colecaoGuiaPagamentoHelper, usuario, valorDebitoACobrarParcelamentoImovelBigDecimal,
							inicioIntervaloParcelamentoFormatado, fimIntervaloParcelamentoFormatado, indicadoresParcelamentoHelper,
							dataVencimentoEntradaParcelamento);

			BigDecimal valorEntradaMinima = opcoesParcelamento.getValorEntrada();

			// Atualizando o valor do d�bito total
			efetuarParcelamentoDebitosActionForm.set("valorDebitoTotalAtualizado", Util.formatarMoedaReal(valorDebitoTotalAtualizado));

			BigDecimal valorEntradaParcelamento = BigDecimal.ZERO;
			if(sessao.getAttribute("valorEntradaParcelamento") != null){
				valorEntradaParcelamento = (BigDecimal) sessao.getAttribute("valorEntradaParcelamento");
			}

			// Verifica se existe valor de entrada de parcelamento marcada pelas EP
			if(valorEntradaParcelamento != null && valorEntradaParcelamento.compareTo(BigDecimal.ZERO) != 0){

				// Verificar permiss�o especial
				boolean temPermissaoValMinimoEntrada = fachada.verificarPermissaoValMinimoEntrada(usuario);

				if(valorEntradaParcelamento.compareTo(valorEntradaMinima.setScale(2, BigDecimal.ROUND_HALF_UP)) == -1
								&& !temPermissaoValMinimoEntrada){
					throw new ActionServletException("atencao.valor.entrada.deve.ser.maior.igual.minima", null, Util
									.formatarMoedaReal(valorEntradaMinima));
				}
				valorEntradaMinima = valorEntradaParcelamento;

			}else{
				valorEntradaMinima = valorEntradaInformado;
			}

			// Colocando o valor da entrada na sess�o caso tenha sido marcada EP
			sessao.setAttribute("valorEntradaMinima", valorEntradaMinima);
		}else{
			sessao.setAttribute("valorEntradaMinima", BigDecimal.ZERO);
		}

		return retorno;
	}
}