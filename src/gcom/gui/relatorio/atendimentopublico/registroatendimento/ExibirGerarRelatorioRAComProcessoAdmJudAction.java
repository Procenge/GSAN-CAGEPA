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

package gcom.gui.relatorio.atendimentopublico.registroatendimento;

import gcom.atendimentopublico.registroatendimento.FiltroSolicitacaoTipo;
import gcom.atendimentopublico.registroatendimento.FiltroSolicitacaoTipoEspecificacao;
import gcom.atendimentopublico.registroatendimento.SolicitacaoTipo;
import gcom.atendimentopublico.registroatendimento.SolicitacaoTipoEspecificacao;
import gcom.cadastro.unidade.FiltroUnidadeOrganizacional;
import gcom.cadastro.unidade.UnidadeOrganizacional;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.relatorio.atendimentopublico.RelatorioEstatisticoRegistroAtendimentoHelper;
import gcom.seguranca.acesso.usuario.FiltroUsuario;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.filtro.ParametroSimples;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Carlos Chrystian
 * @date 31/01/2014
 */
public class ExibirGerarRelatorioRAComProcessoAdmJudAction
				extends GcomAction {

	private Fachada fachada = Fachada.getInstancia();

	private static final int UNIDADE_SUPERIOR = 1;

	private static final int UNIDADE_ATENDIMENTO = 2;

	private static final int UNIDADE_ATUAL = 3;

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("exibirGerarRelatorioRAComProcessoAdmJud");

		// Mudar isso quando tiver esquema de seguran�a
		HttpSession sessao = httpServletRequest.getSession(false);

		// Form
		GerarRelatorioRAComProcessoAdmJudActionForm form = (GerarRelatorioRAComProcessoAdmJudActionForm) actionForm;

		// Usu�rio
		if(form.getLoginUsuario() != null && !form.getLoginUsuario().equals("")){

			getUsuario(form, form.getLoginUsuario(), sessao);
		}

		// Situa��o
		if(form.getSituacao() == null){
			form.setSituacao(RelatorioEstatisticoRegistroAtendimentoHelper.SITUACAO_TODOS);
		}

		// Tipo Solicita��o
		getSolicitacaoTipoCollection(sessao);

		// Especifica��o
		if(form.getTipoSolicitacao() != null && form.getTipoSolicitacao().length > 0){

			getSolicitacaoTipoEspecificacaoCollection(sessao, form);
		}else{

			form.setSelectedTipoSolicitacaoSize("0");
			form.setEspecificacao(null);
		}

		// Unidade Superior
		if(form.getUnidadeSuperiorId() != null && !form.getUnidadeSuperiorId().equals("")){

			getUnidade(form, sessao, UNIDADE_SUPERIOR);
		}
		// Unidade de Atendimento
		if(form.getUnidadeAtendimentoId() != null && !form.getUnidadeAtendimentoId().equals("")){

			getUnidade(form, sessao, UNIDADE_ATENDIMENTO);
		}
		// Unidade Atual
		if(form.getUnidadeAtualId() != null && !form.getUnidadeAtualId().equals("")){

			getUnidade(form, sessao, UNIDADE_ATUAL);
		}

		return retorno;
	}

	/**
	 * Carrega cole��o de solicita��o tipo
	 * 
	 * @author Carlos Chrystian
	 * @date 31/01/2014
	 * @param sessao
	 */
	private void getSolicitacaoTipoCollection(HttpSession sessao){

		// Filtra Solicita��o Tipo
		FiltroSolicitacaoTipo filtroSolicitacaoTipo = new FiltroSolicitacaoTipo();
		filtroSolicitacaoTipo.setCampoOrderBy(FiltroSolicitacaoTipo.DESCRICAO);
		Collection colecaoSolicitacaoTipo = fachada.pesquisar(filtroSolicitacaoTipo, SolicitacaoTipo.class.getName());

		if(colecaoSolicitacaoTipo != null && !colecaoSolicitacaoTipo.isEmpty()){

			sessao.setAttribute("colecaoTipoSolicitacao", colecaoSolicitacaoTipo);
		}else{

			throw new ActionServletException("atencao.naocadastrado", null, "Especifica��o");
		}
	}

	/**
	 * Carrega cole��o de solicita��o tipo especifica��o.
	 * 
	 * @author Carlos Chrystian
	 * @date 31/01/2014
	 * @param sessao
	 */
	private void getSolicitacaoTipoEspecificacaoCollection(HttpSession sessao, GerarRelatorioRAComProcessoAdmJudActionForm form){

		String[] solicitacaoTipo = (String[]) form.getTipoSolicitacao();
		form.setSelectedTipoSolicitacaoSize(solicitacaoTipo.length + "");

		if(solicitacaoTipo.length == 1){

			// Filtra Solicita��o Tipo Especifica��o
			FiltroSolicitacaoTipoEspecificacao filtroSolicitacaoTipoEspecificacao = new FiltroSolicitacaoTipoEspecificacao();
			filtroSolicitacaoTipoEspecificacao.adicionarParametro(new ParametroSimples(
							FiltroSolicitacaoTipoEspecificacao.SOLICITACAO_TIPO_ID, solicitacaoTipo[0]));
			filtroSolicitacaoTipoEspecificacao.setCampoOrderBy(FiltroSolicitacaoTipoEspecificacao.DESCRICAO);

			Collection colecaoSolicitacaoTipoEspecificacao = fachada.pesquisar(filtroSolicitacaoTipoEspecificacao,
							SolicitacaoTipoEspecificacao.class.getName());

			if(colecaoSolicitacaoTipoEspecificacao != null && !colecaoSolicitacaoTipoEspecificacao.isEmpty()){

				sessao.setAttribute("colecaoEspecificacao", colecaoSolicitacaoTipoEspecificacao);
			}else{
				sessao.setAttribute("colecaoEspecificacao", new ArrayList());
				form.setEspecificacao(null);
			}

		}else{
			sessao.setAttribute("colecaoEspecificacao", new ArrayList());
			form.setEspecificacao(null);
		}
	}

	/**
	 * Recupera Unidade
	 * 
	 * @author Carlos Chrystian
	 * @date 31/01/2014
	 * @param form
	 * @param fachada
	 * @param tipo
	 */
	private void getUnidade(GerarRelatorioRAComProcessoAdmJudActionForm form, HttpSession sessao, int tipo){

		String unidadeId = "";

		switch(tipo){

			case UNIDADE_SUPERIOR:

				unidadeId = form.getUnidadeSuperiorId();
				UnidadeOrganizacional unidadeSuperior = new UnidadeOrganizacional();
				unidadeSuperior.setId(new Integer(unidadeId));
				form.setUnidadeSuperiorDescricao(getUnidadeDescricao(unidadeId));

				if(form.getUnidadeSuperiorDescricao().equalsIgnoreCase("Unidade Inexistente")){

					sessao.removeAttribute("unidadeSuperiorEncontrada");
					form.setUnidadeSuperiorId("");
				}else{

					// [FS008] Verificar exist�ncia de unidades subordinadas
					fachada.verificarExistenciaUnidadesSubordinadas(unidadeSuperior);
					sessao.setAttribute("unidadeSuperiorEncontrada", "true");
					form.setValidaUnidadeSuperior("false");
					sessao.removeAttribute("unidadeAtendimentoEncontrada");
				}

				break;

			case UNIDADE_ATENDIMENTO:

				unidadeId = form.getUnidadeAtendimentoId();
				form.setUnidadeAtendimentoDescricao(getUnidadeDescricao(unidadeId));

				if(form.getUnidadeAtendimentoDescricao().equalsIgnoreCase("Unidade Inexistente")){

					sessao.removeAttribute("unidadeAtendimentoEncontrada");
					form.setUnidadeAtendimentoId("");
				}else{

					sessao.setAttribute("unidadeAtendimentoEncontrada", "true");
					form.setValidaUnidadeAtendimento("false");
					sessao.removeAttribute("unidadeSuperiorEncontrada");
				}

				break;

			case UNIDADE_ATUAL:

				unidadeId = form.getUnidadeAtualId();
				form.setUnidadeAtualDescricao(getUnidadeDescricao(unidadeId));

				if(form.getUnidadeAtualDescricao().equalsIgnoreCase("Unidade Inexistente")){

					sessao.removeAttribute("unidadeAtualEncontrada");
					form.setUnidadeAtualId("");
				}else{

					sessao.setAttribute("unidadeAtualEncontrada", "true");
					form.setValidaUnidadeAtual("false");
				}

				break;
			default:
				break;
		}
	}

	/**
	 * Recupera a Unidade Organizacional (Atendimento, Atual e Superior)
	 * 
	 * @author Carlos Chrystian
	 * @date 31/01/2014
	 * @param unidadeId
	 * @return Descri��o da Unidade Filtrada
	 */
	private String getUnidadeDescricao(String unidadeId){

		// Filtra Unidade
		FiltroUnidadeOrganizacional filtroUnidadeOrganizacional = new FiltroUnidadeOrganizacional();
		filtroUnidadeOrganizacional.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.ID, unidadeId));
		// filtroUnidadeOrganizacional.adicionarCaminhoParaCarregamentoEntidade("descricao");
		// Recupera Unidade Organizacional
		Collection colecaoUnidadeOrganizacional = fachada.pesquisar(filtroUnidadeOrganizacional, UnidadeOrganizacional.class.getName());

		if(colecaoUnidadeOrganizacional != null && !colecaoUnidadeOrganizacional.isEmpty()){

			UnidadeOrganizacional unidadeOrganizacional = (UnidadeOrganizacional) colecaoUnidadeOrganizacional.iterator().next();
			return unidadeOrganizacional.getDescricao();
		}else{

			return "Unidade Inexistente";
		}
	}

	/**
	 * Recupera o Usu�rio
	 * 
	 * @author Carlos Chrystian
	 * @date 31/01/2014
	 * @param form
	 * @param idUsuario
	 * @return Descri��o da Unidade Filtrada
	 */
	private void getUsuario(GerarRelatorioRAComProcessoAdmJudActionForm form, String idUsuario, HttpSession sessao){

		// Filtra Usuario
		FiltroUsuario filtroUsuario = new FiltroUsuario();
		filtroUsuario.adicionarParametro(new ParametroSimples(FiltroUsuario.LOGIN, idUsuario.toLowerCase()));

		// Recupera Usu�rio
		Collection colecaoUsuario = fachada.pesquisar(filtroUsuario, Usuario.class.getName());
		if(colecaoUsuario != null && !colecaoUsuario.isEmpty()){

			Usuario usuario = (Usuario) colecaoUsuario.iterator().next();
			sessao.setAttribute("usuarioEncontrado", "true");
			form.setNomeUsuario(usuario.getNomeUsuario());
		}else{

			sessao.removeAttribute("usuarioEncontrado");
			form.setLoginUsuario("");
			form.setNomeUsuario("Usu�rio Inexistente");
		}
	}

}