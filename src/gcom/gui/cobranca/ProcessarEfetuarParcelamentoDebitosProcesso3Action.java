/*
 * Copyright (C) 2007-2007 the GSAN – Sistema Integrado de Gestão de Serviços de Saneamento
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
 * Foundation, Inc., 59 Temple Place – Suite 330, Boston, MA 02111-1307, USA
 */

/*
 * GSAN – Sistema Integrado de Gestão de Serviços de Saneamento
 * Copyright (C) <2007> 
 * Adriano Britto Siqueira
 * Alexandre Santos Cabral
 * Ana Carolina Alves Breda
 * Ana Maria Andrade Cavalcante
 * Aryed Lins de Araújo
 * Bruno Leonardo Rodrigues Barros
 * Carlos Elmano Rodrigues Ferreira
 * Cláudio de Andrade Lira
 * Denys Guimarães Guenes Tavares
 * Eduardo Breckenfeld da Rosa Borges
 * Fabíola Gomes de Araújo
 * Flávio Leonardo Cavalcanti Cordeiro
 * Francisco do Nascimento Júnior
 * Homero Sampaio Cavalcanti
 * Ivan Sérgio da Silva Júnior
 * José Edmar de Siqueira
 * José Thiago Tenório Lopes
 * Kássia Regina Silvestre de Albuquerque
 * Leonardo Luiz Vieira da Silva
 * Márcio Roberto Batista da Silva
 * Maria de Fátima Sampaio Leite
 * Micaela Maria Coelho de Araújo
 * Nelson Mendonça de Carvalho
 * Newton Morais e Silva
 * Pedro Alexandre Santos da Silva Filho
 * Rafael Corrêa Lima e Silva
 * Rafael Francisco Pinto
 * Rafael Koury Monteiro
 * Rafael Palermo de Araújo
 * Raphael Veras Rossiter
 * Roberto Sobreira Barbalho
 * Rodrigo Avellar Silveira
 * Rosana Carvalho Barbosa
 * Sávio Luiz de Andrade Cavalcante
 * Tai Mu Shih
 * Thiago Augusto Souza do Nascimento
 * Tiago Moreno Rodrigues
 * Vivianne Barbosa Sousa
 * 
 * GSANPCG
 * André Nishimura
 * Eduardo Henrique Bandeira Carneiro da Silva
 * Saulo Vasconcelos de Lima
 * Virginia Santos de Melo
 *
 * Este programa é software livre; você pode redistribuí-lo e/ou
 * modificá-lo sob os termos de Licença Pública Geral GNU, conforme
 * publicada pela Free Software Foundation; versão 2 da
 * Licença.
 * Este programa é distribuído na expectativa de ser útil, mas SEM
 * QUALQUER GARANTIA; sem mesmo a garantia implícita de
 * COMERCIALIZAÇÃO ou de ADEQUAÇÃO A QUALQUER PROPÓSITO EM
 * PARTICULAR. Consulte a Licença Pública Geral GNU para obter mais
 * detalhes.
 * Você deve ter recebido uma cópia da Licença Pública Geral GNU
 * junto com este programa; se não, escreva para Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 * 02111-1307, USA.
 */

package gcom.gui.cobranca;

import gcom.cadastro.geografico.Municipio;
import gcom.cobranca.bean.*;
import gcom.cobranca.parcelamento.Parcelamento;
import gcom.cobranca.parcelamento.ParcelamentoConfiguracaoPrestacao;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesAplicacao;
import gcom.util.ConstantesSistema;
import gcom.util.Util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

/**
 * Permite efetuar o parcelamento dos débitos de um imóvel
 * [UC0214] Efetuar Parcelamento de Débitos
 * 
 * @author Roberta Costa
 * @date 24/01/2006
 * @author eduardo henrique
 * @date 03/02/2009
 *       Alteração para tratamento de situações onde a situação de agua ou esgoto sejam nulas
 */
public class ProcessarEfetuarParcelamentoDebitosProcesso3Action
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		if("sim".equalsIgnoreCase((String) httpServletRequest.getAttribute("popupEfetuarParcelamento"))
						|| "sim".equalsIgnoreCase((String) httpServletRequest.getParameter("popupEfetuarParcelamento"))){
			httpServletRequest.setAttribute("popupEfetuarParcelamento", "sim");
		}

		ActionForward retorno = actionMapping.findForward("processo3");
		HttpSession sessao = httpServletRequest.getSession(false);
		Fachada fachada = Fachada.getInstancia();
		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");
		DynaActionForm efetuarParcelamentoDebitosActionForm = (DynaActionForm) actionForm;

		// Pega variáveis do formulário
		String codigoImovel = (String) efetuarParcelamentoDebitosActionForm.get("matriculaImovel");

		Integer situacaoAguaId = null;
		if(efetuarParcelamentoDebitosActionForm.get("situacaoAguaId") != null
						&& !((String) efetuarParcelamentoDebitosActionForm.get("situacaoAguaId")).trim().equalsIgnoreCase("")){
			situacaoAguaId = Integer.valueOf((String) efetuarParcelamentoDebitosActionForm.get("situacaoAguaId"));
		}

		Integer situacaoEsgotoId = null;
		if(efetuarParcelamentoDebitosActionForm.get("situacaoEsgotoId") != null
						&& !((String) efetuarParcelamentoDebitosActionForm.get("situacaoEsgotoId")).trim().equalsIgnoreCase("")){
			situacaoEsgotoId = Integer.valueOf((String) efetuarParcelamentoDebitosActionForm.get("situacaoEsgotoId"));
		}

		Integer perfilImovel = null;
		if(efetuarParcelamentoDebitosActionForm.get("perfilImovel") != null
						&& !((String) efetuarParcelamentoDebitosActionForm.get("perfilImovel")).trim().equalsIgnoreCase("")){
			perfilImovel = Integer.valueOf((String) efetuarParcelamentoDebitosActionForm.get("perfilImovel"));
		}

		Integer numeroReparcelamentoConsecutivos = null;
		if((String) efetuarParcelamentoDebitosActionForm.get("numeroReparcelamentoConsecutivos") != null
						&& !((String) efetuarParcelamentoDebitosActionForm.get("numeroReparcelamentoConsecutivos")).trim()
										.equalsIgnoreCase("")){
			numeroReparcelamentoConsecutivos = Integer.valueOf((String) efetuarParcelamentoDebitosActionForm
							.get("numeroReparcelamentoConsecutivos"));
		}

		String resolucaoDiretoria = (String) efetuarParcelamentoDebitosActionForm.get("resolucaoDiretoria");
		String inicioIntervaloParcelamento = (String) efetuarParcelamentoDebitosActionForm.get("inicioIntervaloParcelamento");
		BigDecimal valorDebitoTotalAtualizado = Util.formatarMoedaRealparaBigDecimal((String) efetuarParcelamentoDebitosActionForm
						.get("valorDebitoTotalAtualizado"));
		String valorDebitoACobrarParcelamentoImovel = ((String) efetuarParcelamentoDebitosActionForm
						.get("valorDebitoACobrarParcelamentoImovel"));

		BigDecimal valorDebitoACobrarParcelamentoImovelBigDecimal = BigDecimal.ZERO;
		String fimIntervaloParcelamento = (String) efetuarParcelamentoDebitosActionForm.get("fimIntervaloParcelamento");

		// Integer fimIntervaloParcelamentoFormatado =
		// Util.formatarMesAnoComBarraParaAnoMes(fimIntervaloParcelamento);
		// Integer inicioIntervaloParcelamentoFormatado =
		// Util.formatarMesAnoComBarraParaAnoMes(inicioIntervaloParcelamento);
		Integer inicioIntervaloParcelamentoFormatado = null;
		if(inicioIntervaloParcelamento != null && !inicioIntervaloParcelamento.trim().equals("")){
			inicioIntervaloParcelamentoFormatado = Util.formatarMesAnoComBarraParaAnoMes(inicioIntervaloParcelamento);
		}
		Integer fimIntervaloParcelamentoFormatado = null;
		if(fimIntervaloParcelamento != null && !fimIntervaloParcelamento.trim().equals("")){
			fimIntervaloParcelamentoFormatado = Util.formatarMesAnoComBarraParaAnoMes(fimIntervaloParcelamento);
		}

		Integer indicadorGuiasPagamento = Integer.valueOf((String) (efetuarParcelamentoDebitosActionForm.get("indicadorGuiasPagamento")));
		Integer indicadorDebitosACobrar = Integer.valueOf((String) (efetuarParcelamentoDebitosActionForm.get("indicadorDebitosACobrar")));
		Integer indicadorCreditoARealizar = Integer
						.valueOf((String) (efetuarParcelamentoDebitosActionForm.get("indicadorCreditoARealizar")));
		Integer indicadorAcrescimosImpotualidade = Integer.valueOf((String) efetuarParcelamentoDebitosActionForm
						.get("indicadorAcrescimosImpotualidade"));
		Integer indicadorContasRevisao = Integer.valueOf((String) efetuarParcelamentoDebitosActionForm.get("indicadorContasRevisao"));

		String indicadorParcelamentoCobrancaBancaria = (String) efetuarParcelamentoDebitosActionForm
						.get("indicadorParcelamentoCobrancaBancaria");

		IndicadoresParcelamentoHelper indicadoresParcelamentoHelper = new IndicadoresParcelamentoHelper();
		indicadoresParcelamentoHelper.setIndicadorContasRevisao(indicadorContasRevisao);
		indicadoresParcelamentoHelper.setIndicadorDebitosACobrar(indicadorDebitosACobrar);
		indicadoresParcelamentoHelper.setIndicadorCreditoARealizar(indicadorCreditoARealizar);
		indicadoresParcelamentoHelper.setIndicadorGuiasPagamento(indicadorGuiasPagamento);
		indicadoresParcelamentoHelper.setIndicadorAcrescimosImpotualidade(indicadorAcrescimosImpotualidade);

		if(!Util.isVazioOuBranco(indicadorParcelamentoCobrancaBancaria)){
			indicadoresParcelamentoHelper.setIndicadorCobrancaBancaria(Integer.valueOf(indicadorParcelamentoCobrancaBancaria));
		}

		if(valorDebitoACobrarParcelamentoImovel != null && !valorDebitoACobrarParcelamentoImovel.trim().equalsIgnoreCase("")){
			valorDebitoACobrarParcelamentoImovelBigDecimal = Util.formatarMoedaRealparaBigDecimal(valorDebitoACobrarParcelamentoImovel);
		}

		// Valor de Entrada
		BigDecimal valorEntradaInformado = BigDecimal.ZERO;

		if(efetuarParcelamentoDebitosActionForm.get("valorEntradaInformado") != null
						&& !((String) efetuarParcelamentoDebitosActionForm.get("valorEntradaInformado")).equalsIgnoreCase("")){
			valorEntradaInformado = Util.formatarMoedaRealparaBigDecimal(
							(String) efetuarParcelamentoDebitosActionForm.get("valorEntradaInformado"), 2).setScale(2);
		}

		BigDecimal valorEntradaInformadoAntes = BigDecimal.ZERO;
		if(efetuarParcelamentoDebitosActionForm.get("valorEntradaInformadoAntes") != null
						&& !efetuarParcelamentoDebitosActionForm.get("valorEntradaInformadoAntes").equals("")){
			valorEntradaInformadoAntes = Util.formatarMoedaRealparaBigDecimal(
							(String) efetuarParcelamentoDebitosActionForm.get("valorEntradaInformadoAntes")).setScale(2);
		}

		// Verifica se o botão calcular foi acionado quando o valor de entrada for alterado
		if(!valorEntradaInformadoAntes.equals(valorEntradaInformado)){
			throw new ActionServletException("atencao.valor.entrada.alterado.necessario.calcular");
		}

		// Data de Entrada Parcelamento
		String dataVencimentoEntradaParcelamento = null;
		if(efetuarParcelamentoDebitosActionForm.get("dataVencimentoEntradaParcelamento") != null
						&& !efetuarParcelamentoDebitosActionForm.get("dataVencimentoEntradaParcelamento").equals("")){
			dataVencimentoEntradaParcelamento = (String) efetuarParcelamentoDebitosActionForm.get("dataVencimentoEntradaParcelamento");

		}

		// Armazena a Coleção de Opções de Parcelamento
		Collection<OpcoesParcelamentoHelper> colecaoOpcoesParcelamento = (Collection<OpcoesParcelamentoHelper>) sessao
						.getAttribute("colecaoOpcoesParcelamento");
		Collection<ParcelamentoConfiguracaoPrestacao> colecaoParcelamentoConfiguracaoPrestacao = (Collection<ParcelamentoConfiguracaoPrestacao>) sessao
						.getAttribute("colecaoParcelamentoConfiguracaoPrestacao");

		Integer numeroDiasVencimentoDaEntrada = null;
		boolean checarValorRestante = false;

		// Verifica se alguma opção de parcelamento foi marcada
		if(colecaoOpcoesParcelamento != null && !colecaoOpcoesParcelamento.isEmpty()
						&& !Util.isVazioOuBranco(efetuarParcelamentoDebitosActionForm.get("indicadorQuantidadeParcelas"))){

			Iterator<OpcoesParcelamentoHelper> opcoesParcelamentoValores = colecaoOpcoesParcelamento.iterator();
			boolean opcaoMarcada = false;
			while(opcoesParcelamentoValores.hasNext()){
				OpcoesParcelamentoHelper opcoesParcelamentoHelper = opcoesParcelamentoValores.next();

				if(((String) efetuarParcelamentoDebitosActionForm.get("indicadorQuantidadeParcelas")).equals(opcoesParcelamentoHelper
								.getQuantidadePrestacao().toString())){
					opcaoMarcada = true;
					opcoesParcelamentoHelper.setIndicadorQuantidadeParcelas(Short.valueOf(opcoesParcelamentoHelper.getQuantidadePrestacao()
									.toString()));

					numeroDiasVencimentoDaEntrada = opcoesParcelamentoHelper.getNumeroDiasVencimentoDaEntrada();
				}

			}

			if(!opcaoMarcada){

				if(httpServletRequest.getParameter("destino") == null){
					throw new ActionServletException("atencao.pelo.menos.uma.opcao.parcelamento.marcada");
				}else if(httpServletRequest.getParameter("destino").equals("4")){
					throw new ActionServletException("atencao.pelo.menos.uma.opcao.parcelamento.marcada");
				}
			}else{

				sessao.removeAttribute("colecaoParcelamentoConfiguracaoPrestacao");
			}
		}else{

			if(!Util.isVazioOrNulo(colecaoParcelamentoConfiguracaoPrestacao)){

				checarValorRestante = true;
			}else{

				if(httpServletRequest.getParameter("destino") == null){
					throw new ActionServletException("atencao.pelo.menos.uma.opcao.parcelamento.marcada");
				}else if(httpServletRequest.getParameter("destino").equals("4")){
					throw new ActionServletException("atencao.pelo.menos.uma.opcao.parcelamento.marcada");
				}
			}
		}

		if(checarValorRestante){

			// [FS0047] - Verificar valor restante do parcelamento configurável igual a zero
			if(efetuarParcelamentoDebitosActionForm.get("indicadorCobrancaParcelamento").toString()
							.equals(ConstantesSistema.INDICADOR_COBRANCA_PARC_GUIA_PAGAMENTO)){

				BigDecimal valorRestante = Util.formatarMoedaRealparaBigDecimal(efetuarParcelamentoDebitosActionForm.get("valorRestante")
								.toString());

				if(!(valorRestante.compareTo(BigDecimal.ZERO) == 0)){

					throw new ActionServletException("atencao.valor_restante_configuravel_nao_equivalente");
				}
			}
		}

		// Valida a Data de Entrada Parcelamento com base no Número de Dias do Vencimento
		if(numeroDiasVencimentoDaEntrada != null){
			Collection collOpcaoDataVencEntrada = new ArrayList();

			String idMunicipio = ConstantesAplicacao.get("empresa.municipio");
			Municipio municipio = new Municipio();
			municipio.setId(Util.converterStringParaInteger(idMunicipio));

			for(int i = numeroDiasVencimentoDaEntrada - 1; i >= 0; i--){
				Date dataRetorno = Util.adicionarNumeroDiasDeUmaData(new Date(), i);
				Date dataUtil = this.getFachada().verificarDataUtilVencimento(dataRetorno, municipio);
				collOpcaoDataVencEntrada.add(Util.formatarData(dataUtil));
			}

			if(!Util.isVazioOrNulo(collOpcaoDataVencEntrada) && !collOpcaoDataVencEntrada.contains(dataVencimentoEntradaParcelamento)){
				Object[] array = collOpcaoDataVencEntrada.toArray();
				throw new ActionServletException("atencao.data_entrada_nao_pode_ser_maior", null, array[0].toString());
			}
		}

		BigDecimal valorTotalMultas = BigDecimal.ZERO;
		BigDecimal valorTotalJurosMora = BigDecimal.ZERO;
		BigDecimal valorTotalAtualizacoesMonetarias = BigDecimal.ZERO;
		BigDecimal valorEntradaMinima = BigDecimal.ZERO;

		// Faz os cálculos quando a entrada for modificada
		String calculaOpcaoParcelamento = httpServletRequest.getParameter("calculaOpcaoParcelamento");

		// O indicador só será usado caso a situação de Água do Imóvel seja
		// SUPRIMIDO, SUPRIMIDO PARCIAL, SUPRIMIDO PARCIAL A PEDIDO
		Integer indicadorRestabelecimento = Integer.valueOf("0");
		if(efetuarParcelamentoDebitosActionForm.get("indicadorRestabelecimento") != null
						&& !efetuarParcelamentoDebitosActionForm.get("indicadorRestabelecimento").equals("")){
			indicadorRestabelecimento = Integer.valueOf(String.valueOf(efetuarParcelamentoDebitosActionForm
							.get("indicadorRestabelecimento")));
		}

		Collection<ContaValoresHelper> colecaoContaValoresNegociacao = null;
		if(sessao.getAttribute("colecaoContaValores") != null){
			colecaoContaValoresNegociacao = (Collection<ContaValoresHelper>) sessao.getAttribute("colecaoContaValores");
		}else{
			colecaoContaValoresNegociacao = (Collection<ContaValoresHelper>) sessao.getAttribute("colecaoContaValoresImovel");
		}

		// Guia de Pagamento
		Collection<GuiaPagamentoValoresHelper> colecaoGuiaPagamento = (Collection<GuiaPagamentoValoresHelper>) sessao
						.getAttribute("colecaoGuiaPagamentoValoresSelecionadas");

		if(calculaOpcaoParcelamento != null && calculaOpcaoParcelamento.equals("1")){

			// Verifica se a entrada informada é menor que a mínima caso venha da aba 2
			if(colecaoContaValoresNegociacao != null && !colecaoContaValoresNegociacao.isEmpty()){
				Iterator<ContaValoresHelper> contaValoresNegociacao = colecaoContaValoresNegociacao.iterator();
				while(contaValoresNegociacao.hasNext()){
					ContaValoresHelper contaValoresHelperNegociacao = contaValoresNegociacao.next();
					if(sessao.getAttribute("colecaoContaValores") != null){
						// Caso não tenha marcado a conta
						if(contaValoresHelperNegociacao.getIndicadorContasDebito() == null){
							if(contaValoresHelperNegociacao.getValorMulta() != null){
								valorTotalMultas.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
								valorTotalMultas = valorTotalMultas.add(contaValoresHelperNegociacao.getValorMulta());
							}
							if(contaValoresHelperNegociacao.getValorJurosMora() != null){
								valorTotalJurosMora.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
								valorTotalJurosMora = valorTotalJurosMora.add(contaValoresHelperNegociacao.getValorJurosMora());
							}
							if(contaValoresHelperNegociacao.getValorAtualizacaoMonetaria() != null){
								valorTotalAtualizacoesMonetarias.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
								valorTotalAtualizacoesMonetarias = valorTotalAtualizacoesMonetarias.add(contaValoresHelperNegociacao
												.getValorAtualizacaoMonetaria());
							}
						}
					}
				}
			}

			// Limpando as opções da sessão
			sessao.removeAttribute("opcoesParcelamento");
			sessao.removeAttribute("colecaoOpcoesParcelamento");

			// [SB0002] - Obter Opções de Parcelamento de acordo com a entrada informada
			NegociacaoOpcoesParcelamentoHelper opcoesParcelamento = fachada.obterOpcoesDeParcelamento(Integer.valueOf(resolucaoDiretoria),
							Integer.valueOf(codigoImovel), valorEntradaInformado, situacaoAguaId, situacaoEsgotoId, perfilImovel,
							inicioIntervaloParcelamento, indicadorRestabelecimento, colecaoContaValoresNegociacao,
							valorDebitoTotalAtualizado, valorTotalMultas, valorTotalJurosMora, valorTotalAtualizacoesMonetarias,
							numeroReparcelamentoConsecutivos, colecaoGuiaPagamento, usuario,
							valorDebitoACobrarParcelamentoImovelBigDecimal, inicioIntervaloParcelamentoFormatado,
							fimIntervaloParcelamentoFormatado, indicadoresParcelamentoHelper, dataVencimentoEntradaParcelamento);

			valorEntradaMinima = opcoesParcelamento.getValorEntrada();

			colecaoOpcoesParcelamento = opcoesParcelamento.getOpcoesParcelamento();

			// Verificar permissão especial
			boolean temPermissaoValMinimoEntrada = fachada.verificarPermissaoValMinimoEntrada(usuario);

			if(valorEntradaInformado.compareTo(valorEntradaMinima.setScale(2, BigDecimal.ROUND_HALF_UP)) == -1
							&& !temPermissaoValMinimoEntrada){
				throw new ActionServletException("atencao.valor.entrada.deve.ser.maior.igual.minima", null, Util
								.formatarMoedaReal(valorEntradaMinima));
			}else{
				valorEntradaMinima = valorEntradaInformado;
			}

			if(valorEntradaInformado.compareTo(valorDebitoTotalAtualizado) > 0){
				throw new ActionServletException("atencao.valor_entrada_nao_pode_ser_maior_que_debito_total");
			}

			sessao.setAttribute("opcoesParcelamento", opcoesParcelamento);

			// Limpa os EP da Coleção de Contas
			Iterator<ContaValoresHelper> contaValores = colecaoContaValoresNegociacao.iterator();
			while(contaValores.hasNext()){
				ContaValoresHelper contaValoresHelper = contaValores.next();
				contaValoresHelper.setIndicadorContasDebito(null);
			}

			// Limpando a opção de parcelamento
			if(colecaoOpcoesParcelamento != null && !colecaoOpcoesParcelamento.equals("")){
				Iterator<OpcoesParcelamentoHelper> opcoesParcelamentoValores = colecaoOpcoesParcelamento.iterator();
				while(opcoesParcelamentoValores.hasNext()){
					OpcoesParcelamentoHelper opcoesParcelamentoHelper = opcoesParcelamentoValores.next();
					opcoesParcelamentoHelper.setIndicadorQuantidadeParcelas(null);
				}
			}
		}

		// Verifica se a entrada informada é menor que a mínima caso venha da aba 2
		if(colecaoContaValoresNegociacao != null && !colecaoContaValoresNegociacao.isEmpty()){
			Iterator<ContaValoresHelper> contaValoresNegociacao = colecaoContaValoresNegociacao.iterator();
			while(contaValoresNegociacao.hasNext()){
				ContaValoresHelper contaValoresHelperNegociacao = contaValoresNegociacao.next();
				if(sessao.getAttribute("colecaoContaValores") != null){
					// Caso não tenha marcado a conta
					if(contaValoresHelperNegociacao.getIndicadorContasDebito() == null){
						if(contaValoresHelperNegociacao.getValorMulta() != null){
							valorTotalMultas.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
							valorTotalMultas = valorTotalMultas.add(contaValoresHelperNegociacao.getValorMulta());
						}
						if(contaValoresHelperNegociacao.getValorJurosMora() != null){
							valorTotalJurosMora.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
							valorTotalJurosMora = valorTotalJurosMora.add(contaValoresHelperNegociacao.getValorJurosMora());
						}
						if(contaValoresHelperNegociacao.getValorAtualizacaoMonetaria() != null){
							valorTotalAtualizacoesMonetarias.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
							valorTotalAtualizacoesMonetarias = valorTotalAtualizacoesMonetarias.add(contaValoresHelperNegociacao
											.getValorAtualizacaoMonetaria());
						}
					}
				}
			}
		}

		if(valorEntradaInformado.compareTo(valorEntradaMinima.setScale(2, BigDecimal.ROUND_HALF_UP)) == -1){
			throw new ActionServletException("atencao.valor.entrada.deve.ser.maior.igual.minima", null, Util
							.formatarMoedaReal(valorEntradaMinima));
		}else{
			valorEntradaMinima = valorEntradaInformado;
		}

		sessao.setAttribute("colecaoOpcoesParcelamento", colecaoOpcoesParcelamento);

		return retorno;
	}
}