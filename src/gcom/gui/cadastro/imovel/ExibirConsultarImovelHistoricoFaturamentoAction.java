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

package gcom.gui.cadastro.imovel;

import gcom.cadastro.imovel.Imovel;
import gcom.fachada.Fachada;
import gcom.faturamento.conta.Conta;
import gcom.faturamento.conta.ContaHistorico;
import gcom.gui.GcomAction;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.comparators.ComparatorChain;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC0473] Consultar Imovel
 * Metodo da 4° Aba - Histórico de Faturamento
 * 
 * @author Rafael Santos
 * @since 13/09/2006
 */
public class ExibirConsultarImovelHistoricoFaturamentoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// localiza o action no objeto actionmapping
		ActionForward retorno = actionMapping.findForward("consultarImovelHistoricoFaturamento");

		// cria uma instância da fachada
		Fachada fachada = Fachada.getInstancia();

		// cria uma instância da sessão
		HttpSession sessao = httpServletRequest.getSession(true);

		ConsultarImovelActionForm consultarImovelActionForm = (ConsultarImovelActionForm) actionForm;
		// recupera o form de consultar histórico do faturamento
		// ConsultarHistoricoFaturamentoActionForm consultarHistoricoFaturamentoActionForm =
		// (ConsultarHistoricoFaturamentoActionForm) actionForm;

		Collection<ContaHistorico> colecaoContaHistoricoImovel = new ArrayList<ContaHistorico>();
		Collection<Conta> colecaoContaImovel = new ArrayList<Conta>();
		/*
		 * Pesquisar o imóvel a partir da matrícula do imóvel informada na página
		 * ======================================================================
		 */
		// recupera o código do imóvel
		String idImovelHistoricoFaturamento = consultarImovelActionForm.getIdImovelHistoricoFaturamento();

		// recupera a flag de limpar o form
		String limparForm = httpServletRequest.getParameter("limparForm");
		String indicadorNovo = httpServletRequest.getParameter("indicadorNovo");
		String idImovelPrincipalAba = null;
		if(sessao.getAttribute("idImovelPrincipalAba") != null){
			idImovelPrincipalAba = (String) sessao.getAttribute("idImovelPrincipalAba");
		}

		if(limparForm != null && !limparForm.equals("")){
			// limpar os dados
			httpServletRequest.setAttribute("idImovelHistoricoFaturamentoNaoEncontrado", null);


			consultarImovelActionForm.setMatriculaImovelHistoricoFaturamento(null);
			consultarImovelActionForm.setSituacaoAguaHistoricoFaturamento(null);
			consultarImovelActionForm.setSituacaoEsgotoHistoricoFaturamento(null);
			consultarImovelActionForm.setConta(null);
			consultarImovelActionForm.setDescricaoImovel(null);

			consultarImovelActionForm.setIdImovelDadosComplementares(null);
			consultarImovelActionForm.setIdImovelDadosCadastrais(null);
			consultarImovelActionForm.setIdImovelAnaliseMedicaoConsumo(null);
			consultarImovelActionForm.setIdImovelHistoricoFaturamento(null);
			consultarImovelActionForm.setIdImovelDebitos(null);
			consultarImovelActionForm.setIdImovelPagamentos(null);
			consultarImovelActionForm.setIdImovelDevolucoesImovel(null);
			consultarImovelActionForm.setIdImovelDocumentosCobranca(null);
			consultarImovelActionForm.setIdImovelParcelamentosDebitos(null);
			consultarImovelActionForm.setIdImovelRegistroAtendimento(null);

			sessao.removeAttribute("imovelDadosHistoricoFaturamento");
			sessao.removeAttribute("colecaoContaImovel");
			sessao.removeAttribute("colecaoContaHistoricoImovel");
			sessao.removeAttribute("idImovelPrincipalAba");
			consultarImovelActionForm.setSituacaoCobrancaDadosComplementares(null);
			if(indicadorNovo == null || indicadorNovo.equals("")){
				idImovelHistoricoFaturamento = null;
				idImovelPrincipalAba = null;
			}

		}

		if((idImovelHistoricoFaturamento != null && !idImovelHistoricoFaturamento.equalsIgnoreCase(""))
						|| (idImovelPrincipalAba != null && !idImovelPrincipalAba.equalsIgnoreCase(""))){

			if(idImovelHistoricoFaturamento != null && !idImovelHistoricoFaturamento.equalsIgnoreCase("")){

				if(idImovelPrincipalAba != null && !idImovelPrincipalAba.equalsIgnoreCase("")){

					if(indicadorNovo != null && !indicadorNovo.equals("")){
						consultarImovelActionForm.setIdImovelHistoricoFaturamento(idImovelHistoricoFaturamento);
						// idImovelHistoricoFaturamento = idImovelHistoricoFaturamento;

					}else if(!(idImovelHistoricoFaturamento.equals(idImovelPrincipalAba))){
						consultarImovelActionForm.setIdImovelHistoricoFaturamento(idImovelPrincipalAba);
						idImovelHistoricoFaturamento = idImovelPrincipalAba;
					}

				}
			}else if(idImovelPrincipalAba != null && !idImovelPrincipalAba.equalsIgnoreCase("")){
				consultarImovelActionForm.setIdImovelRegistroAtendimento(idImovelPrincipalAba);
				idImovelHistoricoFaturamento = idImovelPrincipalAba;
			}

			Imovel imovel = null;
			// verifica se o objeto imovel é o mesmo ja pesquisado, para não precisar pesquisar mas.
			boolean imovelNovoPesquisado = false;
			if(sessao.getAttribute("imovelDadosHistoricoFaturamento") != null){
				imovel = (Imovel) sessao.getAttribute("imovelDadosHistoricoFaturamento");
				if(!(imovel.getId().toString().equals(idImovelHistoricoFaturamento.trim()))){
					imovel = fachada.consultarImovelHistoricoFaturamento(Integer.valueOf(idImovelHistoricoFaturamento.trim()));
					imovelNovoPesquisado = true;
				}

			}else{
				imovel = fachada.consultarImovelHistoricoFaturamento(Integer.valueOf(idImovelHistoricoFaturamento.trim()));
				imovelNovoPesquisado = true;
			}

			if(imovel != null){
				sessao.setAttribute("imovelDadosHistoricoFaturamento", imovel);
				sessao.setAttribute("idImovelPrincipalAba", imovel.getId().toString());
				consultarImovelActionForm.setIdImovelHistoricoFaturamento(imovel.getId().toString());

				// caso o imovel pesquisado seja diferente do pesquisado anterior ou seja a primeira
				// vez que se esteja pesquisando
				if(imovelNovoPesquisado){

					consultarImovelActionForm.setMatriculaImovelHistoricoFaturamento(fachada.pesquisarInscricaoImovel(Integer
									.valueOf(idImovelHistoricoFaturamento.trim()), true));

					httpServletRequest.setAttribute("idImovelHistoricoFaturamentoNaoEncontrado", null);

					// seta a situação de agua
					consultarImovelActionForm.setSituacaoAguaHistoricoFaturamento(imovel.getLigacaoAguaSituacao().getDescricao());
					// seta a situação de esgoto
					consultarImovelActionForm.setSituacaoEsgotoHistoricoFaturamento(imovel.getLigacaoEsgotoSituacao().getDescricao());

					colecaoContaImovel = fachada.consultarContasImovel(imovel.getId(), false);

					colecaoContaHistoricoImovel = fachada.consultarContasHistoricosImovel(imovel.getId());

					ComparatorChain sortContaHistorico = new ComparatorChain();
					sortContaHistorico.addComparator(new BeanComparator("anoMesReferenciaContabil"), true);

					ComparatorChain sortConta = new ComparatorChain();
					sortConta.addComparator(new BeanComparator("referencia"), true);

					if(colecaoContaImovel != null && !colecaoContaImovel.isEmpty()){
						Collections.sort((List) colecaoContaImovel, sortConta);
					}
					if(colecaoContaHistoricoImovel != null && !colecaoContaHistoricoImovel.isEmpty()){
						Collections.sort((List) colecaoContaHistoricoImovel, sortContaHistorico);
					}

					List<Object> colecaoCompleta = new ArrayList<Object>();
					if(colecaoContaImovel != null){
						colecaoCompleta.addAll(colecaoContaImovel);
					}
					if(colecaoContaHistoricoImovel != null){
						colecaoCompleta.addAll(colecaoContaHistoricoImovel);
					}

					Collections.sort(colecaoCompleta, new Comparator<Object>() {

						public int compare(Object o1, Object o2){

							Integer referenciaO1 = 0;
							if(o1 instanceof Conta){
								referenciaO1 = ((Conta) o1).getReferencia();
							}else if(o1 instanceof ContaHistorico){
								referenciaO1 = ((ContaHistorico) o1).getAnoMesReferenciaContabil();
							}

							Integer referenciaO2 = 0;
							if(o2 instanceof Conta){
								referenciaO2 = ((Conta) o2).getReferencia();
							}else if(o2 instanceof ContaHistorico){
								referenciaO2 = ((ContaHistorico) o2).getAnoMesReferenciaContabil();
							}

							return referenciaO2.compareTo(referenciaO1);
						}

					});

					// coloca na sessão a coleção com as contas do imóvel selecionado
					sessao.setAttribute("colecaoCompleta", colecaoCompleta);

					// APAGAR OBJETOS DA SESSAO!
					sessao.setAttribute("colecaoContaImovel", colecaoContaImovel);
					sessao.setAttribute("colecaoContaHistoricoImovel", colecaoContaHistoricoImovel);
					// APAGAR OBJETOS DA SESSAO!

					// Não há Histórico de Faturamento para o imóvel de matrícula {0}.
					/*
					 * if ((colecaoContaImovel == null || colecaoContaImovel.isEmpty()) &&
					 * (colecaoContaHistoricoImovel == null ||
					 * colecaoContaHistoricoImovel.isEmpty())) {
					 * httpServletRequest.setAttribute(
					 * "idImovelHistoricoFaturamentoNaoEncontrado", null);
					 * consultarImovelActionForm.setIdImovelHistoricoFaturamento(null);
					 * consultarImovelActionForm.setMatriculaImovelHistoricoFaturamento(null);
					 * consultarImovelActionForm.setSituacaoAguaHistoricoFaturamento(null);
					 * consultarImovelActionForm.setSituacaoEsgotoHistoricoFaturamento(null);
					 * consultarImovelActionForm.setConta(null);
					 * consultarImovelActionForm.setDescricaoImovel(null);
					 * sessao.removeAttribute("imovelDadosHistoricoFaturamento");
					 * sessao.removeAttribute("colecaoContaImovel");
					 * sessao.removeAttribute("colecaoContaHistoricoImovel");
					 * sessao.removeAttribute("idImovelPrincipalAba");
					 * throw new ActionServletException(
					 * "atencao.nao.historico_faturamento.imovel", null, ""
					 * + imovel.getId().toString());
					 * }
					 */

				}

			}else{
				httpServletRequest.setAttribute("idImovelHistoricoFaturamentoNaoEncontrado", "true");

				consultarImovelActionForm.setMatriculaImovelHistoricoFaturamento("IMÓVEL INEXISTENTE");

				consultarImovelActionForm.setIdImovelHistoricoFaturamento(null);
				consultarImovelActionForm.setSituacaoAguaHistoricoFaturamento(null);
				consultarImovelActionForm.setSituacaoEsgotoHistoricoFaturamento(null);
				consultarImovelActionForm.setConta(null);
				consultarImovelActionForm.setDescricaoImovel(null);

				sessao.removeAttribute("imovelDadosHistoricoFaturamento");
				sessao.removeAttribute("colecaoContaImovel");
				sessao.removeAttribute("colecaoContaHistoricoImovel");
			}

		}else{
			// remove da sessão a coleção das contas do imóvel
			consultarImovelActionForm.setIdImovelHistoricoFaturamento(idImovelHistoricoFaturamento);

			httpServletRequest.setAttribute("idImovelHistoricoFaturamentoNaoEncontrado", null);

			consultarImovelActionForm.setMatriculaImovelHistoricoFaturamento(null);
			consultarImovelActionForm.setSituacaoAguaHistoricoFaturamento(null);
			consultarImovelActionForm.setSituacaoEsgotoHistoricoFaturamento(null);
			consultarImovelActionForm.setConta(null);
			consultarImovelActionForm.setDescricaoImovel(null);

			sessao.removeAttribute("imovelDadosHistoricoFaturamento");
			sessao.removeAttribute("colecaoContaImovel");
			sessao.removeAttribute("colecaoContaHistoricoImovel");
			sessao.removeAttribute("idImovelPrincipalAba");

		}
		// httpServletRequest.setAttribute("colecaoContaImovel", colecaoContaImovel);
		// httpServletRequest.setAttribute("colecaoContaHistoricoImovel",
		// colecaoContaHistoricoImovel);

		// retorna o mapeamento contido na variável retorno
		return retorno;
	}

}