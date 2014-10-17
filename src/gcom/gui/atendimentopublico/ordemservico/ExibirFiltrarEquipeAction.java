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

package gcom.gui.atendimentopublico.ordemservico;

import gcom.atendimentopublico.ordemservico.FiltroServicoPerfilTipo;
import gcom.atendimentopublico.ordemservico.ServicoPerfilTipo;
import gcom.cadastro.funcionario.FiltroFuncionario;
import gcom.cadastro.funcionario.Funcionario;
import gcom.cadastro.unidade.FiltroUnidadeOrganizacional;
import gcom.cadastro.unidade.UnidadeOrganizacional;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Permite filtrar resolu��es de diretoria [UC0219] Filtrar Resolu��o de
 * Diretoria
 * 
 * @author Rafael Corr�a
 * @since 31/03/2006
 */
public class ExibirFiltrarEquipeAction
				extends GcomAction {

	/**
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("exibirFiltrarEquipe");

		FiltrarEquipeActionForm filtrarEquipeActionForm = (FiltrarEquipeActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		HttpSession sessao = httpServletRequest.getSession(false);

		sessao.removeAttribute("equipeAtualizar");

		if(httpServletRequest.getParameter("menu") != null){

			filtrarEquipeActionForm.setAtualizar("1");
			filtrarEquipeActionForm.setIndicadorUso("");
			httpServletRequest.setAttribute("nomeCampo", "codigo");
			sessao.setAttribute("atualizar", "1");

		}else{

			if(httpServletRequest.getParameter("paginacao") == null && sessao.getAttribute("filtrar") == null){

				String atualizar = httpServletRequest.getParameter("atualizar");

				if(atualizar != null && !atualizar.equals("")){
					sessao.setAttribute("atualizar", atualizar);
				}else{
					sessao.removeAttribute("atualizar");
				}

			}
		}

		if(sessao.getAttribute("filtrar") != null){
			httpServletRequest.setAttribute("nomeCampo", "codigo");
		}

		sessao.removeAttribute("filtroEquipe");

		// Recupera os valores da unidade do form
		String idUnidade = filtrarEquipeActionForm.getIdUnidade();
		String nomeUnidade = filtrarEquipeActionForm.getNomeUnidade();

		// Verifica se o usu�rio solicitou a pesquisa de unidade
		if(idUnidade != null && !idUnidade.trim().equals("") && (nomeUnidade == null || nomeUnidade.trim().equals(""))){
			FiltroUnidadeOrganizacional filtroUnidadeOrganizacional = new FiltroUnidadeOrganizacional();
			filtroUnidadeOrganizacional.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.ID, idUnidade));

			Collection colecaoUnidade = fachada.pesquisar(filtroUnidadeOrganizacional, UnidadeOrganizacional.class.getName());

			if(colecaoUnidade != null && !colecaoUnidade.isEmpty()){

				UnidadeOrganizacional unidadeOrganizacional = (UnidadeOrganizacional) Util.retonarObjetoDeColecao(colecaoUnidade);

				filtrarEquipeActionForm.setNomeUnidade(unidadeOrganizacional.getDescricao());
				httpServletRequest.setAttribute("nomeCampo", "idFuncionario");

			}else{

				filtrarEquipeActionForm.setIdUnidade("");
				filtrarEquipeActionForm.setNomeUnidade("Unidade inexistente");
				httpServletRequest.setAttribute("idUnidadeNaoEncontrado", "true");
				httpServletRequest.setAttribute("nomeCampo", "idUnidade");

			}

		}else if(nomeUnidade != null && !nomeUnidade.trim().equals("") && (idUnidade == null || idUnidade.trim().equals(""))){
			filtrarEquipeActionForm.setNomeUnidade("");
		}

		// Recupera os valores do funcion�rio do form
		String idFuncionario = filtrarEquipeActionForm.getIdFuncionario();
		String nomeFuncionario = filtrarEquipeActionForm.getNomeFuncionario();

		// Verifica se o usu�rio solicitou a pesquisa de funcion�rio
		if(idFuncionario != null && !idFuncionario.trim().equals("") && (nomeFuncionario == null || nomeFuncionario.trim().equals(""))){
			FiltroFuncionario filtroFuncionario = new FiltroFuncionario();
			filtroFuncionario.adicionarParametro(new ParametroSimples(FiltroFuncionario.ID, idFuncionario));

			Collection colecaoFuncionario = fachada.pesquisar(filtroFuncionario, Funcionario.class.getName());

			if(colecaoFuncionario != null && !colecaoFuncionario.isEmpty()){

				Funcionario funcionario = (Funcionario) Util.retonarObjetoDeColecao(colecaoFuncionario);

				filtrarEquipeActionForm.setNomeFuncionario(funcionario.getNome());
				httpServletRequest.setAttribute("nomeCampo", "idPerfilServico");

			}else{

				filtrarEquipeActionForm.setIdFuncionario("");
				filtrarEquipeActionForm.setNomeFuncionario("Funcion�rio inexistente");
				httpServletRequest.setAttribute("idFuncionarioNaoEncontrado", "true");
				httpServletRequest.setAttribute("nomeCampo", "idFuncionario");

			}

		}else if(nomeFuncionario != null && !nomeFuncionario.trim().equals("")
						&& (idFuncionario == null || idFuncionario.trim().equals(""))){
			filtrarEquipeActionForm.setNomeFuncionario("");
		}

		// Recupera os valores do servi�o perfil tipo do form
		String idPerfilServico = filtrarEquipeActionForm.getIdPerfilServico();
		String descricaoPerfilServico = filtrarEquipeActionForm.getDescricaoPerfilServico();

		// Verifica se o usu�rio solicitou a pesquisa de funcion�rio
		if(idPerfilServico != null && !idPerfilServico.trim().equals("")
						&& (descricaoPerfilServico == null || descricaoPerfilServico.trim().equals(""))){
			FiltroServicoPerfilTipo filtroServicoPerfilTipo = new FiltroServicoPerfilTipo();
			filtroServicoPerfilTipo.adicionarParametro(new ParametroSimples(FiltroServicoPerfilTipo.ID, idPerfilServico));

			Collection colecaoPerfilServico = fachada.pesquisar(filtroServicoPerfilTipo, ServicoPerfilTipo.class.getName());

			if(colecaoPerfilServico != null && !colecaoPerfilServico.isEmpty()){

				ServicoPerfilTipo servicoPerfilTipo = (ServicoPerfilTipo) Util.retonarObjetoDeColecao(colecaoPerfilServico);

				filtrarEquipeActionForm.setDescricaoPerfilServico(servicoPerfilTipo.getDescricao());
				httpServletRequest.setAttribute("nomeCampo", "indicadorUso");

			}else{

				filtrarEquipeActionForm.setIdPerfilServico("");
				filtrarEquipeActionForm.setDescricaoPerfilServico("Servi�o Tipo Perfil inexistente");
				httpServletRequest.setAttribute("idServicoPerfilNaoEncontrado", "true");
				httpServletRequest.setAttribute("nomeCampo", "idPerfilServico");

			}

		}else if(descricaoPerfilServico != null && !descricaoPerfilServico.trim().equals("")
						&& (idPerfilServico == null || idPerfilServico.trim().equals(""))){
			filtrarEquipeActionForm.setDescricaoPerfilServico("");
		}

		return retorno;

	}

}
