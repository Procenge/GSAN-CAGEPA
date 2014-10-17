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
 * Metodo da 4� Aba - Hist�rico de Faturamento
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

		// cria uma inst�ncia da fachada
		Fachada fachada = Fachada.getInstancia();

		// cria uma inst�ncia da sess�o
		HttpSession sessao = httpServletRequest.getSession(true);

		ConsultarImovelActionForm consultarImovelActionForm = (ConsultarImovelActionForm) actionForm;
		// recupera o form de consultar hist�rico do faturamento
		// ConsultarHistoricoFaturamentoActionForm consultarHistoricoFaturamentoActionForm =
		// (ConsultarHistoricoFaturamentoActionForm) actionForm;

		Collection<ContaHistorico> colecaoContaHistoricoImovel = new ArrayList<ContaHistorico>();
		Collection<Conta> colecaoContaImovel = new ArrayList<Conta>();
		/*
		 * Pesquisar o im�vel a partir da matr�cula do im�vel informada na p�gina
		 * ======================================================================
		 */
		// recupera o c�digo do im�vel
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
			// verifica se o objeto imovel � o mesmo ja pesquisado, para n�o precisar pesquisar mas.
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

					// seta a situa��o de agua
					consultarImovelActionForm.setSituacaoAguaHistoricoFaturamento(imovel.getLigacaoAguaSituacao().getDescricao());
					// seta a situa��o de esgoto
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

					// coloca na sess�o a cole��o com as contas do im�vel selecionado
					sessao.setAttribute("colecaoCompleta", colecaoCompleta);

					// APAGAR OBJETOS DA SESSAO!
					sessao.setAttribute("colecaoContaImovel", colecaoContaImovel);
					sessao.setAttribute("colecaoContaHistoricoImovel", colecaoContaHistoricoImovel);
					// APAGAR OBJETOS DA SESSAO!

					// N�o h� Hist�rico de Faturamento para o im�vel de matr�cula {0}.
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

				consultarImovelActionForm.setMatriculaImovelHistoricoFaturamento("IM�VEL INEXISTENTE");

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
			// remove da sess�o a cole��o das contas do im�vel
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

		// retorna o mapeamento contido na vari�vel retorno
		return retorno;
	}

}