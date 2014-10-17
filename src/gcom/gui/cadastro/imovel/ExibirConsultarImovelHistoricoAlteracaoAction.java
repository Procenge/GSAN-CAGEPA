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
 * Vitor Hora
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

import gcom.atendimentopublico.ligacaoagua.bean.ConsultarHistoricoManutencaoLigacaoHelper;
import gcom.atendimentopublico.ligacaoagua.bean.HistoricoManutencaoLigacaoHelper;
import gcom.cadastro.imovel.Imovel;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.util.Util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 11° Aba - Historico Alteração Imóvel
 * 
 * @author Vitor Hora
 * @since 07/07/2008
 */
public class ExibirConsultarImovelHistoricoAlteracaoAction
				extends GcomAction {

	/**
	 * @param actionMapping
	 *            Descrição do parâmetro
	 * @param actionForm
	 *            Descrição do parâmetro
	 * @param httpServletRequest
	 *            Descrição do parâmetro
	 * @param httpServletResponse
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("consultarImovelHistoricoAlteracao");

		// Obtendo uma instancia da sessao
		HttpSession sessao = httpServletRequest.getSession(false);
		
		// Obtém a instância da Fachada
		Fachada fachada = Fachada.getInstancia();

		ConsultarImovelActionForm consultarImovelActionForm = (ConsultarImovelActionForm) actionForm;

		// id do imovel da aba historico alteracao
		String idImovelHistoricoAlteracao = consultarImovelActionForm.getIdImovelHistoricoAlteracao();
		String limparForm = httpServletRequest.getParameter("limparForm");

		String indicadorNovo = httpServletRequest.getParameter("indicadorNovo");
		String idImovelPrincipalAba = null;
		if(sessao.getAttribute("idImovelPrincipalAba") != null){
			idImovelPrincipalAba = (String) sessao.getAttribute("idImovelPrincipalAba");
		}

		if(limparForm != null && !limparForm.equals("")){
			// limpar os dados
			httpServletRequest.setAttribute("idImovelRegistroAlteracaoNaoEncontrado", null);

			sessao.removeAttribute("imovelHistoricoAlteracao");
			sessao.removeAttribute("idImovelPrincipalAba");
			sessao.removeAttribute("imovelClientes");
			sessao.removeAttribute("colecaoExibirHistoricoAlteracao");

			consultarImovelActionForm.setMatriculaImovelHistoricoAlteracao(null);
			consultarImovelActionForm.setIdAlteracao(null);
			consultarImovelActionForm.setDataRealizacaoAlteracao(null);
			consultarImovelActionForm.setDescricaoOperacaoAlteracao(null);
			consultarImovelActionForm.setUsuarioOperacaoAlteracao(null);
			consultarImovelActionForm.setDescricaoTabelaAlteracao(null);
			consultarImovelActionForm.setTipoAlteracao(null);
			consultarImovelActionForm.setCampoTabelaAlteracao(null);
			consultarImovelActionForm.setConteudoAnteriorAlteracao(null);
			consultarImovelActionForm.setConteudoAtualAlteracao(null);
			consultarImovelActionForm.setIdImovelHistoricoAlteracao(null);

			consultarImovelActionForm.setSituacaoCobrancaDadosComplementares(null);

		}

		if((idImovelHistoricoAlteracao != null && !idImovelHistoricoAlteracao.equalsIgnoreCase(""))
						|| (idImovelPrincipalAba != null && !idImovelPrincipalAba.equalsIgnoreCase(""))){

			if(idImovelHistoricoAlteracao != null && !idImovelHistoricoAlteracao.equalsIgnoreCase("")){

				if(idImovelPrincipalAba != null && !idImovelPrincipalAba.equalsIgnoreCase("")){

					if(indicadorNovo != null && !indicadorNovo.equals("")){
						consultarImovelActionForm.setIdImovelHistoricoAlteracao(idImovelHistoricoAlteracao);
						// idImovelHistoricoAlteracao = idImovelHistoricoAlteracao;

					}else if(!(idImovelHistoricoAlteracao.equals(idImovelPrincipalAba))){
						consultarImovelActionForm.setIdImovelHistoricoAlteracao(idImovelPrincipalAba);
						idImovelHistoricoAlteracao = idImovelPrincipalAba;
					}

				}
			}else if(idImovelPrincipalAba != null && !idImovelPrincipalAba.equalsIgnoreCase("")){
				consultarImovelActionForm.setIdImovelHistoricoAlteracao(idImovelPrincipalAba);
				idImovelHistoricoAlteracao = idImovelPrincipalAba;
			}

			Imovel imovel = null;

			// verifica se o objeto imovel é o mesmo ja pesquisado, para não precisar pesquisar mas.
			boolean imovelNovoPesquisado = false;
			if(sessao.getAttribute("imovelHistoricoAlteracao") != null){
				imovel = (Imovel) sessao.getAttribute("imovelHistoricoAlteracao");
				if(!(imovel.getId().toString().equals(idImovelHistoricoAlteracao.trim()))){
					imovel = fachada.consultarImovelHistoricoFaturamento(new Integer(idImovelHistoricoAlteracao.trim()));
					imovelNovoPesquisado = true;
				}
			}else{
				imovel = fachada.consultarImovelHistoricoFaturamento(new Integer(idImovelHistoricoAlteracao.trim()));
				imovelNovoPesquisado = true;
			}

			if(imovel != null){
				sessao.setAttribute("imovelHistoricoAlteracao", imovel);
				sessao.setAttribute("idImovelPrincipalAba", imovel.getId().toString());
				consultarImovelActionForm.setIdImovelHistoricoAlteracao(imovel.getId().toString());

				// caso o imovel pesquisado seja diferente do pesquisado anterior ou seja a primeira
				// vez que se esteja pesquisando
				if(imovelNovoPesquisado){
					// seta na tela a inscrição do imovel
					httpServletRequest.setAttribute("matriculaImovelHistoricoAlteracaoNaoEncontrado", null);

					consultarImovelActionForm.setMatriculaImovelHistoricoAlteracao(fachada.pesquisarInscricaoImovel(new Integer(
									idImovelHistoricoAlteracao.trim()), true));

					// seta a situação de agua
					if(imovel.getLigacaoAguaSituacao() != null){
						consultarImovelActionForm.setSituacaoAguaHistoricoAlteracao(imovel.getLigacaoAguaSituacao().getDescricao());
					}
					// seta a situação de esgoto
					if(imovel.getLigacaoEsgotoSituacao() != null){
						consultarImovelActionForm.setSituacaoEsgotoHistoricoAlteracao(imovel.getLigacaoEsgotoSituacao().getDescricao());
					}

					// Recupera coleção histórico alteração
					Collection colecaoHistoricoAlteracao = fachada.pesquisarEntidadeOperacoesEfetuadasHql(new Integer(
									idImovelHistoricoAlteracao.trim()), 10);
					Collection colecaoExibirHistoricoAlteracao = new ArrayList();

					if(!colecaoHistoricoAlteracao.isEmpty()){

						for(Iterator iterator = colecaoHistoricoAlteracao.iterator(); iterator.hasNext();){
							Object[] historicoAlteracao = (Object[]) iterator.next();

							ConsultarImovelActionForm consultarImovelHistoricoAlteracaoActionForm = new ConsultarImovelActionForm();

							// id alteracao
							if(historicoAlteracao[0] != null){
								consultarImovelHistoricoAlteracaoActionForm.setIdAlteracao("" + historicoAlteracao[0]);
							}

							// data alteracao
							if(historicoAlteracao[1] != null){
								consultarImovelHistoricoAlteracaoActionForm.setDataRealizacaoAlteracao(""
												+ historicoAlteracao[1].toString().substring(8, 10) + "/"
												+ historicoAlteracao[1].toString().substring(5, 7) + "/"
												+ historicoAlteracao[1].toString().substring(0, 4));
							}

							// descricao alteracao
							if(historicoAlteracao[2] != null){
								consultarImovelHistoricoAlteracaoActionForm.setDescricaoOperacaoAlteracao("" + historicoAlteracao[2]);
							}

							// usuário alteracao
							if(historicoAlteracao[3] != null){
								consultarImovelHistoricoAlteracaoActionForm.setUsuarioOperacaoAlteracao("" + historicoAlteracao[3]);
							}

							// nome tabela alteracao
							if(historicoAlteracao[4] != null){
								consultarImovelHistoricoAlteracaoActionForm.setDescricaoTabelaAlteracao("" + historicoAlteracao[4]);
							}

							// tipo alteracao
							if(historicoAlteracao[5] != null){
								consultarImovelHistoricoAlteracaoActionForm.setTipoAlteracao("" + historicoAlteracao[5]);
							}

							// campo tabela alteracao
							if(historicoAlteracao[6] != null){
								consultarImovelHistoricoAlteracaoActionForm.setCampoTabelaAlteracao("" + historicoAlteracao[6]);
							}

							// conteudo anterior alteracao
							if(historicoAlteracao[7] != null){
								consultarImovelHistoricoAlteracaoActionForm.setConteudoAnteriorAlteracao("" + historicoAlteracao[7]);
							}

							// conteudo atual
							if(historicoAlteracao[8] != null){
								consultarImovelHistoricoAlteracaoActionForm.setConteudoAtualAlteracao("" + historicoAlteracao[8]);
							}

							colecaoExibirHistoricoAlteracao.add(consultarImovelHistoricoAlteracaoActionForm);
						}
					}

					sessao.setAttribute("colecaoExibirHistoricoAlteracao", colecaoExibirHistoricoAlteracao);

				}
			}else{
				httpServletRequest.setAttribute("idImovelHistoricoAlteracaoNaoEncontrado", "true");
				consultarImovelActionForm.setMatriculaImovelHistoricoAlteracao("IMÓVEL INEXISTENTE");

				// limpar os dados pesquisados
				sessao.removeAttribute("imovelHistoricoAlteracao");
				consultarImovelActionForm.setIdImovelHistoricoAlteracao(null);
				consultarImovelActionForm.setSituacaoAguaHistoricoAlteracao(null);
				consultarImovelActionForm.setSituacaoEsgotoHistoricoAlteracao(null);
				sessao.removeAttribute("colecaoExibirHistoricoAlteracao");

			}
		}else{
			consultarImovelActionForm.setMatriculaImovelHistoricoAlteracao(idImovelHistoricoAlteracao);

			httpServletRequest.setAttribute("idImovelHistoricoAlteracaoNaoEncontrado", null);

			sessao.removeAttribute("imovelHistoricoAlteracao");
			sessao.removeAttribute("idImovelPrincipalAba");
			consultarImovelActionForm.setMatriculaImovelHistoricoAlteracao(null);
			consultarImovelActionForm.setSituacaoAguaHistoricoAlteracao(null);
			consultarImovelActionForm.setSituacaoEsgotoHistoricoAlteracao(null);
			sessao.removeAttribute("colecaoExibirHistoricoAlteracao");

		}
		
		// ******************************************************
		// Consulta do Histórico da Manutenção da Ligação de Água
		// ******************************************************

		if(!Util.isVazioOuBranco(idImovelHistoricoAlteracao)) {
			ConsultarHistoricoManutencaoLigacaoHelper helper = new ConsultarHistoricoManutencaoLigacaoHelper();
			helper.setImovelId(new Integer(idImovelHistoricoAlteracao));
			
			// Consulta o histórico da manutenção da ligação de água
			Collection<HistoricoManutencaoLigacaoHelper> colecaoHistoricoManutencaoLigacao = fachada.consultarHistoricoManutencaoLigacao(
							helper, null);
	
			sessao.setAttribute("colecaoHistoricoManutencaoLigacao", colecaoHistoricoManutencaoLigacao);
		}

		return retorno;
	}

}
