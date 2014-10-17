/* Feito por Felipe e Rafael na data 16/12/2005*/

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
 * [SB0003] – Alterar Dados das Prestações
 * 
 * @author Carlos Chrystian Ramos
 * @date 21/05/2013
 *       Classe Helper utilizada na alteração dos dados da Prestações de uma Guia de Pagamento
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

		// Obtém a sessão
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

		// [FS0022] – Verificar valores das prestações
		if(sessao.getAttribute("colecaoGuiaPrestacaoHelper") != null && !sessao.getAttribute("colecaoGuiaPrestacaoHelper").equals("")){
			colecaoGuiaPagamentoPrestacao = (Collection) sessao.getAttribute("colecaoGuiaPrestacaoHelper");
		}

		// Obtém o valor pela coleção Helper, que representam as Prestações
		for(Iterator iterator = colecaoGuiaPagamentoPrestacao.iterator(); iterator.hasNext();){
			GuiaPagamentoPrestacaoHelper prestacaoHelper = (GuiaPagamentoPrestacaoHelper) iterator.next();

			valorTotalDebito = new BigDecimal(0.00);

			valorTotalDebito = valorTotalDebito.add(prestacaoHelper.getValorTipoDebito());

			if(!Util.isVazioOrNulo(arrayValorDebitoNaPrestacao) && arrayValorDebitoNaPrestacao.length > 0){
				String debitoTipo = null;
				String valorDebito = null;

				// Para cada tipo de débito da guia:
				// Caso o valor do débito distribuído nas prestações
				// (somatório de “Valor do Débito na Prestação”)
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

				// não corresponda ao valor do débito (“Valor do Débito”),exibir a mensagem
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

		// Recupera o numero total de prestações
		if(!Util.isVazioOuBranco(sessao.getAttribute("numeroTotalPrestacoes"))){
			nuTotalPrestacoes = (String) sessao.getAttribute("numeroTotalPrestacoes");
		}


		// O sistema inclui as prestações da guia de pagamento a partir dos dados da lista
		// dos dados das prestações da guia:
		if(!Util.isVazioOrNulo(colecaoListaDadosPrestacoesGuia)){
			// Coleção com os dados alterados
			colecaoListaDadosPrestacoesGuiaAlterada = new ArrayList<ListaDadosPrestacaoGuiaHelper>();

			// Atribui o valor 1 ao Número da Prestação.
			Integer numeroPrestacao = 1;

			// Enquanto o Número da Prestação for menor ou igual ao “Número de
			// Prestações”:
			int numeroTotalPrestacoes = nuTotalPrestacoes != null ? Integer.parseInt(nuTotalPrestacoes) : 0;

			for(ListaDadosPrestacaoGuiaHelper listaDadosPrestacaoGuiaHelper : colecaoListaDadosPrestacoesGuia){
				// Enquanto o Número da Prestação for menor ou igual ao “Número de
				// Prestações”:
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

					// Para cada tipo de débito da prestação,
					// inclui os dados do tipo de débito na prestação
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
									// Realiza o acúmulo do valor por Tipo de Débito
									// para a Prestação
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

				// Número da Prestação = Número da Prestação mais 1 (um).
				numeroPrestacao++;

				colecaoListaDadosPrestacoesGuiaAlterada.add(listaDadosPrestacaoGuiaHelper);
			}
			// Remove a coleção antiga da sessão
			sessao.removeAttribute("colecaoListaDadosPrestacoesGuia");
		}

		// Coloca na sessão a lista alterada
		sessao.setAttribute("colecaoListaDadosPrestacoesGuia", colecaoListaDadosPrestacoesGuiaAlterada);

		sessao.setAttribute("indicadorDadosAlterados", ConstantesSistema.SIM);

		httpServletRequest.setAttribute("fechaPopup", "true");

		return retorno;
	}

}
