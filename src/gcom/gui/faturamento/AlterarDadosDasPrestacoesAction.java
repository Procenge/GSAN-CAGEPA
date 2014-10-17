/* Feito por Felipe e Rafael na data 16/12/2005*/

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

package gcom.gui.faturamento;

import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.gui.faturamento.bean.GuiaPagamentoPrestacaoHelper;
import gcom.gui.faturamento.bean.ListaDadosPrestacaoGuiaHelper;
import gcom.util.ConstantesSistema;
import gcom.util.Util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [SB0003] � Alterar Dados das Presta��es
 * 
 * @author Carlos Chrystian Ramos
 * @date 21/05/2013
 *       Classe Helper utilizada na altera��o dos dados da Presta��es de uma Guia de Pagamento
 */
public class AlterarDadosDasPrestacoesAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("alterarDadosDasPrestacoes");

		Collection<GuiaPagamentoPrestacaoHelper> colecaoGuiaPagamentoPrestacao = null;
		
		BigDecimal valorTotalDebito = BigDecimal.ZERO;
		BigDecimal somatorioValorDebitoNaPrestacao = BigDecimal.ZERO;
		Collection<ListaDadosPrestacaoGuiaHelper> colecaoListaDadosPrestacoesGuia = null;
		Collection<ListaDadosPrestacaoGuiaHelper> colecaoListaDadosPrestacoesGuiaAlterada = null;

		// Obt�m a sess�o
		HttpSession sessao = httpServletRequest.getSession(false);

		AlterarDadosDasPrestacoesForm alterarDadosDasPrestacoesForm = (AlterarDadosDasPrestacoesForm) actionForm;

		String[] arrayNuPrestacao = alterarDadosDasPrestacoesForm.getNumeroPrestacao();

		String[] arrayDataVencimento = alterarDadosDasPrestacoesForm.getDataVencimentoPrestacao();

		String[] arrayNumeroPrestacao = alterarDadosDasPrestacoesForm.getNumeroPrestacaoArray();

		String[] arrayDataVencimentoPrestacao = alterarDadosDasPrestacoesForm.getDataVencimentoPrestacaoArray();

		String[] arrayDebitoTipo = alterarDadosDasPrestacoesForm.getDebitoTipo();

		String[] arrayValorDebitoNaPrestacao = alterarDadosDasPrestacoesForm.getValorDebitoNaPrestacao();

		// Atualiza a data alterada
		if(!Util.isVazioOrNulo(arrayNuPrestacao) && arrayNuPrestacao.length > 0 && !Util.isVazioOrNulo(arrayDataVencimento)
						&& arrayDataVencimento.length > 0){
			int i = 0;

			for(String prestacaoAlterarda : arrayNuPrestacao){
				if(!Util.isVazioOrNulo(arrayNumeroPrestacao) && arrayNumeroPrestacao.length > 0){

					int indice = 0;

					for(String nuPrestacao : arrayNumeroPrestacao){
						if(nuPrestacao.equals(prestacaoAlterarda)){
							arrayDataVencimentoPrestacao[indice] = arrayDataVencimento[i];
						}

						indice++;
					}
				}

				i++;
			}
		}

		// [FS0022] � Verificar valores das presta��es
		if(sessao.getAttribute("colecaoGuiaPrestacaoHelper") != null && !sessao.getAttribute("colecaoGuiaPrestacaoHelper").equals("")){
			colecaoGuiaPagamentoPrestacao = (Collection) sessao.getAttribute("colecaoGuiaPrestacaoHelper");
		}

		// Obt�m o valor pela cole��o Helper, que representam as Presta��es
		for(Iterator iterator = colecaoGuiaPagamentoPrestacao.iterator(); iterator.hasNext();){
			GuiaPagamentoPrestacaoHelper prestacaoHelper = (GuiaPagamentoPrestacaoHelper) iterator.next();

			valorTotalDebito = new BigDecimal(0.00);

			valorTotalDebito = valorTotalDebito.add(prestacaoHelper.getValorTipoDebito());

			if(!Util.isVazioOrNulo(arrayValorDebitoNaPrestacao) && arrayValorDebitoNaPrestacao.length > 0){
				String debitoTipo = null;
				String valorDebito = null;

				// Para cada tipo de d�bito da guia:
				// Caso o valor do d�bito distribu�do nas presta��es
				// (somat�rio de �Valor do D�bito na Presta��o�)
				somatorioValorDebitoNaPrestacao = new BigDecimal(0.00);

				// Acumula valores preenchidos na tela
				int i = 0;
				for(String nuPrestacaoComValorDebito : arrayValorDebitoNaPrestacao){
					debitoTipo = arrayDebitoTipo[i];
					valorDebito = nuPrestacaoComValorDebito;

					if(Integer.parseInt(debitoTipo) == prestacaoHelper.getId().intValue()){
						somatorioValorDebitoNaPrestacao = somatorioValorDebitoNaPrestacao.add(Util
										.formatarMoedaRealparaBigDecimal(valorDebito));
					}

					i++;
				}

				// n�o corresponda ao valor do d�bito (�Valor do D�bito�),exibir a mensagem
				if(somatorioValorDebitoNaPrestacao.compareTo(valorTotalDebito) != 0){
					throw new ActionServletException(
									"atencao.faturamento.alterar_dados_prestacao_valor_debito_distribuido_nao_corresponde",
									prestacaoHelper.getDescricaoTipoDebito(), Util.formatarMoedaReal(somatorioValorDebitoNaPrestacao),
									Util.formatarMoedaReal(valorTotalDebito));
				}
			}
		}

		if(!Util.isVazioOuBranco(sessao.getAttribute("colecaoListaDadosPrestacoesGuia"))){
			colecaoListaDadosPrestacoesGuia = (Collection<ListaDadosPrestacaoGuiaHelper>) sessao
							.getAttribute("colecaoListaDadosPrestacoesGuia");
		}

		String nuTotalPrestacoes = null;

		// Recupera o numero total de presta��es
		if(!Util.isVazioOuBranco(sessao.getAttribute("numeroTotalPrestacoes"))){
			nuTotalPrestacoes = (String) sessao.getAttribute("numeroTotalPrestacoes");
		}


		// O sistema inclui as presta��es da guia de pagamento a partir dos dados da lista
		// dos dados das presta��es da guia:
		if(!Util.isVazioOrNulo(colecaoListaDadosPrestacoesGuia)){
			// Cole��o com os dados alterados
			colecaoListaDadosPrestacoesGuiaAlterada = new ArrayList<ListaDadosPrestacaoGuiaHelper>();

			// Atribui o valor 1 ao N�mero da Presta��o.
			Integer numeroPrestacao = 1;

			// Enquanto o N�mero da Presta��o for menor ou igual ao �N�mero de
			// Presta��es�:
			int numeroTotalPrestacoes = nuTotalPrestacoes != null ? Integer.parseInt(nuTotalPrestacoes) : 0;

			for(ListaDadosPrestacaoGuiaHelper listaDadosPrestacaoGuiaHelper : colecaoListaDadosPrestacoesGuia){
				// Enquanto o N�mero da Presta��o for menor ou igual ao �N�mero de
				// Presta��es�:
				if(numeroPrestacao.intValue() <= numeroTotalPrestacoes){

					if(!Util.isVazioOrNulo(arrayDataVencimentoPrestacao) && arrayDataVencimentoPrestacao.length > 0){
						String nuPrestacaoData = null;
						Date dataVencimento = null;

						int i = 0;

						for(String nuPrestacaoComDataVencimento : arrayDataVencimentoPrestacao){
							nuPrestacaoData = arrayNumeroPrestacao[i];
							dataVencimento = Util.converterStringParaData(nuPrestacaoComDataVencimento);

							if(Integer.parseInt(nuPrestacaoData) == listaDadosPrestacaoGuiaHelper.getPrestacao().intValue()){
								listaDadosPrestacaoGuiaHelper.setDataVencimentoPrestacao(dataVencimento);
							}

							i++;
						}
					}

					// Para cada tipo de d�bito da presta��o,
					// inclui os dados do tipo de d�bito na presta��o
					// (inclui na tabela GUIA_PAGAMENTO_PRESTACAO):
					Map<Integer, BigDecimal> mapalistaDadosPrestacoesGuia = listaDadosPrestacaoGuiaHelper
									.getMapValorDebitoNaPrestacaoPorTipoDebito();

					for(Integer chaveDebitoTipo : mapalistaDadosPrestacoesGuia.keySet()){
						if(!Util.isVazioOrNulo(arrayValorDebitoNaPrestacao) && arrayValorDebitoNaPrestacao.length > 0){
							String nuPrestacao = null;
							String tipoDebito = null;
							String valorDebito = null;

							int i = 0;

							// Acumula valores preenchidos na tela
							for(String nuPrestacaoComValorDebito : arrayValorDebitoNaPrestacao){
								nuPrestacao = arrayNumeroPrestacao[i];
								tipoDebito = arrayDebitoTipo[i];
								valorDebito = nuPrestacaoComValorDebito;

								if(Integer.valueOf(tipoDebito) == chaveDebitoTipo.intValue()){
									// Realiza o ac�mulo do valor por Tipo de D�bito
									// para a Presta��o
									if((Integer.parseInt(nuPrestacao) == listaDadosPrestacaoGuiaHelper.getPrestacao().intValue())
													&& Integer.parseInt(tipoDebito) == chaveDebitoTipo.intValue()){
										mapalistaDadosPrestacoesGuia
														.put(chaveDebitoTipo, Util.formatarMoedaRealparaBigDecimal(valorDebito));
									}
								}

								i++;
							}
						}
					}
				}

				// N�mero da Presta��o = N�mero da Presta��o mais 1 (um).
				numeroPrestacao++;

				colecaoListaDadosPrestacoesGuiaAlterada.add(listaDadosPrestacaoGuiaHelper);
			}
			// Remove a cole��o antiga da sess�o
			sessao.removeAttribute("colecaoListaDadosPrestacoesGuia");
		}

		// Coloca na sess�o a lista alterada
		sessao.setAttribute("colecaoListaDadosPrestacoesGuia", colecaoListaDadosPrestacoesGuiaAlterada);

		sessao.setAttribute("indicadorDadosAlterados", ConstantesSistema.SIM);

		httpServletRequest.setAttribute("fechaPopup", "true");

		return retorno;
	}

}
