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

package gcom.gui.atendimentopublico.registroatendimento;

import gcom.atendimentopublico.ordemservico.EspecificacaoServicoTipo;
import gcom.atendimentopublico.ordemservico.FiltroEspecificacaoServicoTipo;
import gcom.atendimentopublico.registroatendimento.*;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Descri��o da classe
 * 
 * @author R�mulo Aur�lio
 * @date 07/11/2006
 */
public class ExibirAtualizarTipoSolicitacaoEspecificacaoAction
				extends GcomAction {

	/**
	 * Description of the Method
	 * 
	 * @param actionMapping
	 *            Description of the Parameter
	 * @param actionForm
	 *            Description of the Parameter
	 * @param httpServletRequest
	 *            Description of the Parameter
	 * @param httpServletResponse
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Mudar isso quando tiver esquema de seguran�a
		HttpSession sessao = httpServletRequest.getSession(false);

		// Set no mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("atualizarTipoSolicitacaoEspecificacao");

		AtualizarTipoSolicitacaoEspecificacaoActionForm atualizarTipoSolicitacaoEspecificacaoActionForm = (AtualizarTipoSolicitacaoEspecificacaoActionForm) actionForm;

		String id = null;

		String idSolicitacaoTipo = null;

		if(!Util.isVazioOuBranco(httpServletRequest.getParameter("idTipoSolicitacao"))){

			if(sessao.getAttribute("adicionar") != null){

				sessao.removeAttribute("objetoTipoSolicitacao");
				sessao.removeAttribute("adicionar");

			}else{
				sessao.removeAttribute("objetoTipoSolicitacao");
				sessao.removeAttribute("colecaoTipoSolicitacao");
				sessao.removeAttribute("colecaoSolicitacaoTipoEspecificacao");
			}

		}

		// Verifica se veio do filtrar ou do manter

		if(httpServletRequest.getParameter("manter") != null){
			sessao.removeAttribute("colecaoSolicitacaoTipoEspecificacaoRemovidos");
			sessao.setAttribute("manter", true);
		}else if(httpServletRequest.getParameter("filtrar") != null){
			sessao.removeAttribute("manter");

		}

		Fachada fachada = Fachada.getInstancia();

		FiltroSolicitacaoTipoGrupo filtroSolicitacaoTipoGrupo = new FiltroSolicitacaoTipoGrupo();
		filtroSolicitacaoTipoGrupo.adicionarParametro(new ParametroSimples(FiltroSolicitacaoTipoGrupo.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));
		Collection colecaoSolicitacaoTipoGrupo = fachada.pesquisar(filtroSolicitacaoTipoGrupo, SolicitacaoTipoGrupo.class.getName());
		httpServletRequest.setAttribute("colecaoSolicitacaoTipoGrupo", colecaoSolicitacaoTipoGrupo);

		// Verifica se o servicoCobrancaValor j� est� na sess�o, em caso
		// afirmativo
		// significa que o usu�rio j� entrou na tela e apenas selecionou algum
		// item que deu um reload na tela e em caso negativo significa que ele
		// est� entrando pela primeira vez

		if(sessao.getAttribute("colecaoTipoSolicitacaoTela") == null){

			if(sessao.getAttribute("objetoSolicitacaoTipo") != null){

				SolicitacaoTipo solicitacaoTipo = (SolicitacaoTipo) sessao.getAttribute("objetoSolicitacaoTipo");

				sessao.setAttribute("idTipoSolicitacao", solicitacaoTipo.getId().toString());

				atualizarTipoSolicitacaoEspecificacaoActionForm.setIdTipoSolicitacao(solicitacaoTipo.getId().toString());

				atualizarTipoSolicitacaoEspecificacaoActionForm.setDescricao(solicitacaoTipo.getDescricao());

				atualizarTipoSolicitacaoEspecificacaoActionForm.setIdgrupoTipoSolicitacao(solicitacaoTipo.getSolicitacaoTipoGrupo().getId()
								.toString());

				atualizarTipoSolicitacaoEspecificacaoActionForm.setIndicadorFaltaAgua("" + solicitacaoTipo.getIndicadorFaltaAgua());

				atualizarTipoSolicitacaoEspecificacaoActionForm.setIndicadorTarifaSocial("" + solicitacaoTipo.getIndicadorTarifaSocial());

				atualizarTipoSolicitacaoEspecificacaoActionForm.setIndicadorUso("" + solicitacaoTipo.getIndicadorUso());

				id = solicitacaoTipo.getId().toString();

				sessao.setAttribute("idSolicitacaoTipo", solicitacaoTipo.getId().toString());

				sessao.setAttribute("solicitacaoTipoAtualizar", solicitacaoTipo);
				sessao.removeAttribute("objetoSolicitacaoTipo");

				/*
				 * Faz o filtro pesquisando o tipo de especifica��o da
				 * solicita��o
				 */
				FiltroSolicitacaoTipoEspecificacao filtroSolicitacaoTipoEspecificacao = new FiltroSolicitacaoTipoEspecificacao();
				filtroSolicitacaoTipoEspecificacao.adicionarParametro(new ParametroSimples(
								FiltroSolicitacaoTipoEspecificacao.SOLICITACAO_TIPO, solicitacaoTipo.getId().toString()));

				filtroSolicitacaoTipoEspecificacao.adicionarCaminhoParaCarregamentoEntidade("solicitacaoTipo");

				filtroSolicitacaoTipoEspecificacao.adicionarCaminhoParaCarregamentoEntidade("unidadeOrganizacional");

				filtroSolicitacaoTipoEspecificacao.adicionarCaminhoParaCarregamentoEntidade("servicoTipo");

				filtroSolicitacaoTipoEspecificacao.adicionarCaminhoParaCarregamentoEntidade("especificacaoImovelSituacao");

				filtroSolicitacaoTipoEspecificacao
								.adicionarCaminhoParaCarregamentoEntidade(FiltroSolicitacaoTipoEspecificacao.SOLICITACAO_TIPO_ESPECIFICACAO_MENSAGEM);

				Collection<SolicitacaoTipoEspecificacao> colecaoSolicitacaoTipoEspecificacao = fachada.pesquisar(
								filtroSolicitacaoTipoEspecificacao, SolicitacaoTipoEspecificacao.class.getName());

				if(colecaoSolicitacaoTipoEspecificacao == null || colecaoSolicitacaoTipoEspecificacao.isEmpty()){
					colecaoSolicitacaoTipoEspecificacao = new ArrayList();
				}else{
					Collection<EspecificacaoServicoTipo> colecaoEspecificacaoServicoTipo = null;

					FiltroEspecificacaoServicoTipo filtroEspecificacaoServicoTipo = null;
					Integer idSolicitacaoTipoEspecificacao = null;

					for(SolicitacaoTipoEspecificacao solicitacaoTipoEspecificacao : colecaoSolicitacaoTipoEspecificacao){
						idSolicitacaoTipoEspecificacao = solicitacaoTipoEspecificacao.getId();

						filtroEspecificacaoServicoTipo = new FiltroEspecificacaoServicoTipo();
						filtroEspecificacaoServicoTipo.adicionarParametro(new ParametroSimples(
										FiltroEspecificacaoServicoTipo.SOLICITACAO_TIPO_ESPECIFICACAO_ID, idSolicitacaoTipoEspecificacao));
						filtroEspecificacaoServicoTipo
										.adicionarCaminhoParaCarregamentoEntidade(FiltroEspecificacaoServicoTipo.SOLICITACAO_TIPO_ESPECIFICACAO);

						colecaoEspecificacaoServicoTipo = fachada.pesquisar(filtroEspecificacaoServicoTipo, EspecificacaoServicoTipo.class
										.getName());

						solicitacaoTipoEspecificacao.setEspecificacaoServicoTipos(new HashSet(colecaoEspecificacaoServicoTipo));
					}
				}

				sessao.setAttribute("colecaoSolicitacaoTipoEspecificacao", colecaoSolicitacaoTipoEspecificacao);

			}else{

				SolicitacaoTipo solicitacaoTipo = null;

				// idSolicitacaoTipo = null;

				if(Util.isVazioOuBranco(httpServletRequest.getParameter("idTipoSolicitacao"))){
					solicitacaoTipo = (SolicitacaoTipo) sessao.getAttribute("objetoSolicitacaoTipo");
				}else{
					idSolicitacaoTipo = (String) httpServletRequest.getParameter("idTipoSolicitacao");
					sessao.setAttribute("idTipoSolicitacao", idSolicitacaoTipo);
				}

				httpServletRequest.setAttribute("idTipoSolicitacao", idSolicitacaoTipo);

				if(idSolicitacaoTipo != null){

					id = idSolicitacaoTipo;

					FiltroSolicitacaoTipo filtroSolicitacaoTipo = new FiltroSolicitacaoTipo();

					filtroSolicitacaoTipo.adicionarCaminhoParaCarregamentoEntidade("solicitacaoTipoGrupo");

					filtroSolicitacaoTipo.adicionarParametro(new ParametroSimples(FiltroSolicitacaoTipo.ID, idSolicitacaoTipo));

					Collection<SolicitacaoTipo> colecaoSolicitacaoTipo = fachada.pesquisar(filtroSolicitacaoTipo, SolicitacaoTipo.class
									.getName());

					if(colecaoSolicitacaoTipo == null || colecaoSolicitacaoTipo.isEmpty()){
						throw new ActionServletException("atencao.atualizacao.timestamp");
					}

					httpServletRequest.setAttribute("colecaoSolicitacaoTipo", colecaoSolicitacaoTipo);

					solicitacaoTipo = (SolicitacaoTipo) colecaoSolicitacaoTipo.iterator().next();

				}

				if(solicitacaoTipo == null){

					FiltroSolicitacaoTipo filtroSolicitacaoTipo = new FiltroSolicitacaoTipo();
					filtroSolicitacaoTipo.adicionarParametro(new ParametroSimples(FiltroSolicitacaoTipo.ID, sessao
									.getAttribute("idTipoSolicitacao")));

					Collection colecaoSolicitacaoTipo = fachada.pesquisar(filtroSolicitacaoTipo, SolicitacaoTipo.class.getName());

					solicitacaoTipo = (SolicitacaoTipo) colecaoSolicitacaoTipo.iterator().next();

				}

				atualizarTipoSolicitacaoEspecificacaoActionForm.setIdTipoSolicitacao(solicitacaoTipo.getId().toString());

				atualizarTipoSolicitacaoEspecificacaoActionForm.setDescricao(solicitacaoTipo.getDescricao());

				if(solicitacaoTipo.getSolicitacaoTipoGrupo() != null){
					atualizarTipoSolicitacaoEspecificacaoActionForm.setIdgrupoTipoSolicitacao(solicitacaoTipo.getSolicitacaoTipoGrupo()
									.getId().toString());
				}else{
					atualizarTipoSolicitacaoEspecificacaoActionForm.setIdgrupoTipoSolicitacao("");
				}

				atualizarTipoSolicitacaoEspecificacaoActionForm.setIndicadorFaltaAgua("" + solicitacaoTipo.getIndicadorFaltaAgua());

				atualizarTipoSolicitacaoEspecificacaoActionForm.setIndicadorTarifaSocial("" + solicitacaoTipo.getIndicadorTarifaSocial());

				atualizarTipoSolicitacaoEspecificacaoActionForm.setIndicadorUso("" + solicitacaoTipo.getIndicadorUso());

				sessao.setAttribute("idSolicitacaoTipo", solicitacaoTipo.getId().toString());

				sessao.setAttribute("solicitacaoTipoAtualizar", solicitacaoTipo);

				Collection<SolicitacaoTipoEspecificacao> colecaoSolicitacaoTipoEspecificacao = (Collection<SolicitacaoTipoEspecificacao>) sessao
								.getAttribute("colecaoSolicitacaoTipoEspecificacao");

				if(colecaoSolicitacaoTipoEspecificacao == null || colecaoSolicitacaoTipoEspecificacao.isEmpty()){

					/*
					 * Faz o filtro pesquisando o tipo de especifica��o da
					 * solicita��o
					 */
					FiltroSolicitacaoTipoEspecificacao filtroSolicitacaoTipoEspecificacao = new FiltroSolicitacaoTipoEspecificacao();
					filtroSolicitacaoTipoEspecificacao.adicionarParametro(new ParametroSimples(
									FiltroSolicitacaoTipoEspecificacao.SOLICITACAO_TIPO_ID, solicitacaoTipo.getId()));

					filtroSolicitacaoTipoEspecificacao.adicionarCaminhoParaCarregamentoEntidade("solicitacaoTipo");

					filtroSolicitacaoTipoEspecificacao.adicionarCaminhoParaCarregamentoEntidade("unidadeOrganizacional");

					filtroSolicitacaoTipoEspecificacao.adicionarCaminhoParaCarregamentoEntidade("servicoTipo");

					filtroSolicitacaoTipoEspecificacao.adicionarCaminhoParaCarregamentoEntidade("especificacaoImovelSituacao");

					filtroSolicitacaoTipoEspecificacao
									.adicionarCaminhoParaCarregamentoEntidade(FiltroSolicitacaoTipoEspecificacao.SOLICITACAO_TIPO_ESPECIFICACAO_MENSAGEM);

					colecaoSolicitacaoTipoEspecificacao = fachada.pesquisar(filtroSolicitacaoTipoEspecificacao,
									SolicitacaoTipoEspecificacao.class.getName());

					if(colecaoSolicitacaoTipoEspecificacao == null || colecaoSolicitacaoTipoEspecificacao.isEmpty()){
						colecaoSolicitacaoTipoEspecificacao = new ArrayList();
					}else{
						Collection<EspecificacaoServicoTipo> colecaoEspecificacaoServicoTipo = null;

						FiltroEspecificacaoServicoTipo filtroEspecificacaoServicoTipo = null;
						Integer idSolicitacaoTipoEspecificacao = null;

						for(SolicitacaoTipoEspecificacao solicitacaoTipoEspecificacao : colecaoSolicitacaoTipoEspecificacao){
							idSolicitacaoTipoEspecificacao = solicitacaoTipoEspecificacao.getId();

							filtroEspecificacaoServicoTipo = new FiltroEspecificacaoServicoTipo();
							filtroEspecificacaoServicoTipo.adicionarParametro(new ParametroSimples(
											FiltroEspecificacaoServicoTipo.SOLICITACAO_TIPO_ESPECIFICACAO_ID,
											idSolicitacaoTipoEspecificacao));
							filtroEspecificacaoServicoTipo
											.adicionarCaminhoParaCarregamentoEntidade(FiltroEspecificacaoServicoTipo.SOLICITACAO_TIPO_ESPECIFICACAO);

							colecaoEspecificacaoServicoTipo = fachada.pesquisar(filtroEspecificacaoServicoTipo,
											EspecificacaoServicoTipo.class.getName());

							solicitacaoTipoEspecificacao.setEspecificacaoServicoTipos(new HashSet(colecaoEspecificacaoServicoTipo));
						}
					}

					sessao.setAttribute("colecaoSolicitacaoTipoEspecificacao", colecaoSolicitacaoTipoEspecificacao);

				}
			}
		}

		// Verifica se o usu�rio removeu um componente e em caso afirmativo
		// remove o componente da cole��o
		if(!Util.isVazioOuBranco(httpServletRequest.getParameter("deleteComponente"))){

			Collection colecaoSolicitacaoTipoEspecificacaoRemovidos = null;

			Collection colecaoSolicitacaoTipoEspecificacao = (Collection) sessao.getAttribute("colecaoSolicitacaoTipoEspecificacao");

			if(colecaoSolicitacaoTipoEspecificacao != null && !colecaoSolicitacaoTipoEspecificacao.isEmpty()){

				int posicaoComponente = Integer.valueOf((String) httpServletRequest.getParameter("deleteComponente")).intValue();

				int index = 0;

				Iterator colecaoSolicitacaoTipoEspecificacaoIterator = colecaoSolicitacaoTipoEspecificacao.iterator();

				while(colecaoSolicitacaoTipoEspecificacaoIterator.hasNext()){

					index++;

					SolicitacaoTipoEspecificacao solicitacaoTipoEspecificacao = (SolicitacaoTipoEspecificacao) colecaoSolicitacaoTipoEspecificacaoIterator
									.next();

					if(index == posicaoComponente){

						if(solicitacaoTipoEspecificacao.getId() != null){

							if(sessao.getAttribute("colecaoSolicitacaoTipoEspecificacaoRemovidos") == null
							/*
							 * || colecaoSolicitacaoTipoEspecificacaoRemovidos == null ||
							 * colecaoSolicitacaoTipoEspecificacaoRemovidos.isEmpty()
							 */){
								colecaoSolicitacaoTipoEspecificacaoRemovidos = new ArrayList();
							}else{
								colecaoSolicitacaoTipoEspecificacaoRemovidos = (Collection) sessao
												.getAttribute("colecaoSolicitacaoTipoEspecificacaoRemovidos");
							}

							colecaoSolicitacaoTipoEspecificacaoRemovidos.add(solicitacaoTipoEspecificacao);
							sessao.setAttribute("colecaoSolicitacaoTipoEspecificacaoRemovidos",
											colecaoSolicitacaoTipoEspecificacaoRemovidos);
						}

						colecaoSolicitacaoTipoEspecificacao.remove(solicitacaoTipoEspecificacao);

						atualizarTipoSolicitacaoEspecificacaoActionForm.setTamanhoColecao("" + colecaoSolicitacaoTipoEspecificacao.size());

						sessao.setAttribute("colecaoSolicitacaoTipoEspecificacao", colecaoSolicitacaoTipoEspecificacao);

						break;
					}
				}
			}
		}

		// -------------- bt DESFAZER ---------------

		if(!Util.isVazioOuBranco(httpServletRequest.getParameter("desfazer"))
						&& httpServletRequest.getParameter("desfazer").equalsIgnoreCase("S")){

			sessao.removeAttribute("colecaoSolicitacaoTipoTela");

			String solicitacaoTipoID = null;

			if(Util.isVazioOuBranco(httpServletRequest.getParameter("idTipoSolicitacao"))){
				solicitacaoTipoID = (String) sessao.getAttribute("idTipoSolicitacao");
			}else{
				solicitacaoTipoID = (String) httpServletRequest.getParameter("idTipoSolicitacao");
				sessao.setAttribute("idTipoSolicitacao", solicitacaoTipoID);
			}

			if(solicitacaoTipoID.equalsIgnoreCase("")){
				solicitacaoTipoID = null;
			}

			if((solicitacaoTipoID == null) && (id == null)){

				SolicitacaoTipo solicitacaoTipo = (SolicitacaoTipo) sessao.getAttribute("objetoSolicitacaoTipo");

				atualizarTipoSolicitacaoEspecificacaoActionForm.setIdTipoSolicitacao(solicitacaoTipo.getId().toString());

				atualizarTipoSolicitacaoEspecificacaoActionForm.setDescricao(solicitacaoTipo.getDescricao());

				atualizarTipoSolicitacaoEspecificacaoActionForm.setIndicadorFaltaAgua("" + solicitacaoTipo.getIndicadorFaltaAgua());

				atualizarTipoSolicitacaoEspecificacaoActionForm.setIndicadorTarifaSocial("" + solicitacaoTipo.getIndicadorTarifaSocial());

				atualizarTipoSolicitacaoEspecificacaoActionForm.setIndicadorUso("" + solicitacaoTipo.getIndicadorUso());

				atualizarTipoSolicitacaoEspecificacaoActionForm.setIdgrupoTipoSolicitacao(solicitacaoTipo.getSolicitacaoTipoGrupo().getId()
								.toString());

				sessao.setAttribute("idSolicitacaoTipo", solicitacaoTipo.getId().toString());

				sessao.setAttribute("solicitacaoTipoAtualizar", solicitacaoTipo);
				sessao.removeAttribute("solicitacaoTipo");

				/*
				 * Faz o filtro pesquisando o tipo de especifica��o da
				 * solicita��o
				 */
				FiltroSolicitacaoTipoEspecificacao filtroSolicitacaoTipoEspecificacao = new FiltroSolicitacaoTipoEspecificacao();
				filtroSolicitacaoTipoEspecificacao.adicionarParametro(new ParametroSimples(
								FiltroSolicitacaoTipoEspecificacao.SOLICITACAO_TIPO, solicitacaoTipo.getId().toString()));

				filtroSolicitacaoTipoEspecificacao.adicionarCaminhoParaCarregamentoEntidade("solicitacaoTipo");

				filtroSolicitacaoTipoEspecificacao.adicionarCaminhoParaCarregamentoEntidade("unidadeOrganizacional");

				filtroSolicitacaoTipoEspecificacao.adicionarCaminhoParaCarregamentoEntidade("servicoTipo");

				filtroSolicitacaoTipoEspecificacao.adicionarCaminhoParaCarregamentoEntidade("especificacaoImovelSituacao");

				filtroSolicitacaoTipoEspecificacao
								.adicionarCaminhoParaCarregamentoEntidade(FiltroSolicitacaoTipoEspecificacao.SOLICITACAO_TIPO_ESPECIFICACAO_MENSAGEM);

				filtroSolicitacaoTipoEspecificacao.adicionarParametro(new ParametroSimples(
								FiltroSolicitacaoTipoEspecificacao.SOLICITACAO_TIPO, solicitacaoTipo.getId().toString()));

				Collection<SolicitacaoTipoEspecificacao> colecaoSolicitacaoTipoEspecificacao = fachada.pesquisar(
								filtroSolicitacaoTipoEspecificacao, SolicitacaoTipoEspecificacao.class.getName());

				if(colecaoSolicitacaoTipoEspecificacao == null || colecaoSolicitacaoTipoEspecificacao.isEmpty()){
					colecaoSolicitacaoTipoEspecificacao = new ArrayList();
				}else{
					Collection<EspecificacaoServicoTipo> colecaoEspecificacaoServicoTipo = null;

					FiltroEspecificacaoServicoTipo filtroEspecificacaoServicoTipo = null;
					Integer idSolicitacaoTipoEspecificacao = null;

					for(SolicitacaoTipoEspecificacao solicitacaoTipoEspecificacao : colecaoSolicitacaoTipoEspecificacao){
						idSolicitacaoTipoEspecificacao = solicitacaoTipoEspecificacao.getId();

						filtroEspecificacaoServicoTipo = new FiltroEspecificacaoServicoTipo();
						filtroEspecificacaoServicoTipo.adicionarParametro(new ParametroSimples(
										FiltroEspecificacaoServicoTipo.SOLICITACAO_TIPO_ESPECIFICACAO_ID, idSolicitacaoTipoEspecificacao));
						filtroEspecificacaoServicoTipo
										.adicionarCaminhoParaCarregamentoEntidade(FiltroEspecificacaoServicoTipo.SOLICITACAO_TIPO_ESPECIFICACAO);

						colecaoEspecificacaoServicoTipo = fachada.pesquisar(filtroEspecificacaoServicoTipo, EspecificacaoServicoTipo.class
										.getName());

						solicitacaoTipoEspecificacao.setEspecificacaoServicoTipos(new HashSet(colecaoEspecificacaoServicoTipo));
					}
				}

				sessao.setAttribute("colecaoSolicitacaoTipoEspecificacao", colecaoSolicitacaoTipoEspecificacao);
			}

			if((solicitacaoTipoID == null) && (id != null)){

				solicitacaoTipoID = id;
			}

			if(solicitacaoTipoID != null){

				FiltroSolicitacaoTipo filtroSolicitacaoTipo = new FiltroSolicitacaoTipo();

				filtroSolicitacaoTipo.adicionarParametro(new ParametroSimples(FiltroSolicitacaoTipo.ID, solicitacaoTipoID));

				Collection<SolicitacaoTipo> colecaoSolicitacaoTipo = fachada.pesquisar(filtroSolicitacaoTipo, SolicitacaoTipo.class
								.getName());

				if(colecaoSolicitacaoTipo == null || colecaoSolicitacaoTipo.isEmpty()){
					throw new ActionServletException("atencao.atualizacao.timestamp");
				}

				httpServletRequest.setAttribute("colecaoSolicitacaoTipo", colecaoSolicitacaoTipo);

				SolicitacaoTipo solicitacaoTipo = (SolicitacaoTipo) colecaoSolicitacaoTipo.iterator().next();

				atualizarTipoSolicitacaoEspecificacaoActionForm.setIdTipoSolicitacao(solicitacaoTipo.getId().toString());

				sessao.setAttribute("idSolicitacaoTipo", solicitacaoTipo.getId().toString());

				atualizarTipoSolicitacaoEspecificacaoActionForm.setIdgrupoTipoSolicitacao(solicitacaoTipo.getSolicitacaoTipoGrupo().getId()
								.toString());

				atualizarTipoSolicitacaoEspecificacaoActionForm.setDescricao(solicitacaoTipo.getDescricao());

				atualizarTipoSolicitacaoEspecificacaoActionForm.setIndicadorFaltaAgua("" + solicitacaoTipo.getIndicadorFaltaAgua());

				atualizarTipoSolicitacaoEspecificacaoActionForm.setIndicadorTarifaSocial("" + solicitacaoTipo.getIndicadorTarifaSocial());

				atualizarTipoSolicitacaoEspecificacaoActionForm.setIndicadorUso("" + solicitacaoTipo.getIndicadorUso());

				httpServletRequest.setAttribute("idSolicitacaoTipo", solicitacaoTipoID);
				sessao.setAttribute("solicitacaoTipoAtualizar", solicitacaoTipo);

				// if
				// (sessao.getAttribute("colecaoSolicitacaoTipoEspecificacao")
				// == null) {

				/*
				 * Faz o filtro pesquisando o tipo de especifica��o da
				 * solicita��o
				 */
				FiltroSolicitacaoTipoEspecificacao filtroSolicitacaoTipoEspecificacao = new FiltroSolicitacaoTipoEspecificacao();
				filtroSolicitacaoTipoEspecificacao.adicionarParametro(new ParametroSimples(
								FiltroSolicitacaoTipoEspecificacao.SOLICITACAO_TIPO, solicitacaoTipo.getId().toString()));

				filtroSolicitacaoTipoEspecificacao.adicionarCaminhoParaCarregamentoEntidade("solicitacaoTipo");

				filtroSolicitacaoTipoEspecificacao.adicionarCaminhoParaCarregamentoEntidade("unidadeOrganizacional");

				filtroSolicitacaoTipoEspecificacao.adicionarCaminhoParaCarregamentoEntidade("servicoTipo");

				filtroSolicitacaoTipoEspecificacao.adicionarCaminhoParaCarregamentoEntidade("especificacaoImovelSituacao");

				filtroSolicitacaoTipoEspecificacao
								.adicionarCaminhoParaCarregamentoEntidade(FiltroSolicitacaoTipoEspecificacao.SOLICITACAO_TIPO_ESPECIFICACAO_MENSAGEM);

				filtroSolicitacaoTipoEspecificacao.adicionarParametro(new ParametroSimples(
								FiltroSolicitacaoTipoEspecificacao.SOLICITACAO_TIPO, solicitacaoTipo.getId().toString()));

				Collection<SolicitacaoTipoEspecificacao> colecaoSolicitacaoTipoEspecificacao = fachada.pesquisar(
								filtroSolicitacaoTipoEspecificacao, SolicitacaoTipoEspecificacao.class.getName());

				if(colecaoSolicitacaoTipoEspecificacao == null || colecaoSolicitacaoTipoEspecificacao.isEmpty()){
					colecaoSolicitacaoTipoEspecificacao = new ArrayList();
				}else{
					Collection<EspecificacaoServicoTipo> colecaoEspecificacaoServicoTipo = null;

					FiltroEspecificacaoServicoTipo filtroEspecificacaoServicoTipo = null;
					Integer idSolicitacaoTipoEspecificacao = null;

					for(SolicitacaoTipoEspecificacao solicitacaoTipoEspecificacao : colecaoSolicitacaoTipoEspecificacao){
						idSolicitacaoTipoEspecificacao = solicitacaoTipoEspecificacao.getId();

						filtroEspecificacaoServicoTipo = new FiltroEspecificacaoServicoTipo();
						filtroEspecificacaoServicoTipo.adicionarParametro(new ParametroSimples(
										FiltroEspecificacaoServicoTipo.SOLICITACAO_TIPO_ESPECIFICACAO_ID, idSolicitacaoTipoEspecificacao));
						filtroEspecificacaoServicoTipo
										.adicionarCaminhoParaCarregamentoEntidade(FiltroEspecificacaoServicoTipo.SOLICITACAO_TIPO_ESPECIFICACAO);

						colecaoEspecificacaoServicoTipo = fachada.pesquisar(filtroEspecificacaoServicoTipo, EspecificacaoServicoTipo.class
										.getName());

						solicitacaoTipoEspecificacao.setEspecificacaoServicoTipos(new HashSet(colecaoEspecificacaoServicoTipo));
					}
				}

				sessao.setAttribute("colecaoSolicitacaoTipoEspecificacao", colecaoSolicitacaoTipoEspecificacao);

				// Retira da sess�o os itens selecionados para a exclus�o
				sessao.removeAttribute("colecaoSolicitacaoTipoEspecificacaoRemovidos");

				// }
			}
		}
		// -------------- bt DESFAZER ---------------

		httpServletRequest.setAttribute("colecaoSolicitacaoTipoTela", sessao.getAttribute("colecaoSolicitacaoTipoTela"));

		return retorno;
	}

}