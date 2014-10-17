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
 * Filtrar Registro Atendimento - Exibir
 * 
 * @author Leonardo Regis - 02/08/2006
 * @author eduardo henrique
 * @date 10/03/2009 - Inclusão de filtros de Datas de Previsão Inicial e Final de Atendimento
 */
public class ExibirGerarRelatorioEstatisticoRegistroAtendimentoAction
				extends GcomAction {

	private Fachada fachada = Fachada.getInstancia();

	private static final int UNIDADE_SUPERIOR = 1;

	private static final int UNIDADE_ATENDIMENTO = 2;

	private static final int UNIDADE_ATUAL = 3;

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("exibirGerarRelatorioEstatisticoRegistroAtendimento");

		// Mudar isso quando tiver esquema de segurança
		HttpSession sessao = httpServletRequest.getSession(false);

		// Form
		GerarRelatorioEstatisticoRegistroAtendimentoActionForm form = (GerarRelatorioEstatisticoRegistroAtendimentoActionForm) actionForm;

		// Usuário
		if(form.getLoginUsuario() != null && !form.getLoginUsuario().equals("")){

			getUsuario(form, form.getLoginUsuario(), sessao);
		}

		// Situação
		if(form.getSituacao() == null){
			form.setSituacao(RelatorioEstatisticoRegistroAtendimentoHelper.SITUACAO_TODOS);
		}

		// Tipo Solicitação
		getSolicitacaoTipoCollection(sessao);

		// Especificação
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
	 * Carrega coleção de solicitação tipo
	 * 
	 * @author Luciano Galvao
	 * @date 30/08/2012
	 * @param sessao
	 */
	private void getSolicitacaoTipoCollection(HttpSession sessao){

		// Filtra Solicitação Tipo
		FiltroSolicitacaoTipo filtroSolicitacaoTipo = new FiltroSolicitacaoTipo();
		filtroSolicitacaoTipo.setCampoOrderBy(FiltroSolicitacaoTipo.DESCRICAO);
		Collection colecaoSolicitacaoTipo = fachada.pesquisar(filtroSolicitacaoTipo, SolicitacaoTipo.class.getName());

		if(colecaoSolicitacaoTipo != null && !colecaoSolicitacaoTipo.isEmpty()){

			sessao.setAttribute("colecaoTipoSolicitacao", colecaoSolicitacaoTipo);
		}else{

			throw new ActionServletException("atencao.naocadastrado", null, "Especificação");
		}
	}

	/**
	 * Carrega coleção de solicitação tipo especificação.
	 * 
	 * @author Luciano Galvao
	 * @date 30/08/2012
	 * @param sessao
	 */
	private void getSolicitacaoTipoEspecificacaoCollection(HttpSession sessao, GerarRelatorioEstatisticoRegistroAtendimentoActionForm form){

		String[] solicitacaoTipo = (String[]) form.getTipoSolicitacao();
		form.setSelectedTipoSolicitacaoSize(solicitacaoTipo.length + "");

		if(solicitacaoTipo.length == 1){

			// Filtra Solicitação Tipo Especificação
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
	 * @author Luciano Galvao
	 * @date 30/08/2012
	 * @param form
	 * @param fachada
	 * @param tipo
	 */
	private void getUnidade(GerarRelatorioEstatisticoRegistroAtendimentoActionForm form, HttpSession sessao, int tipo){

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

					// [FS008] Verificar existência de unidades subordinadas
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
	 * [F0006] Valida Unidade
	 * 
	 * @author Luciano Galvao
	 * @date 30/08/2012
	 * @param unidadeId
	 * @return Descrição da Unidade Filtrada
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
	 * Recupera o Usuário
	 * 
	 * @author Luciano Galvao
	 * @date 30/08/2012
	 * @param form
	 * @param idUsuario
	 * @return Descrição da Unidade Filtrada
	 */
	private void getUsuario(GerarRelatorioEstatisticoRegistroAtendimentoActionForm form, String idUsuario, HttpSession sessao){

		// Filtra Usuario
		FiltroUsuario filtroUsuario = new FiltroUsuario();
		filtroUsuario.adicionarParametro(new ParametroSimples(FiltroUsuario.LOGIN, idUsuario.toLowerCase()));

		// Recupera Usuário
		Collection colecaoUsuario = fachada.pesquisar(filtroUsuario, Usuario.class.getName());
		if(colecaoUsuario != null && !colecaoUsuario.isEmpty()){

			Usuario usuario = (Usuario) colecaoUsuario.iterator().next();
			sessao.setAttribute("usuarioEncontrado", "true");
			form.setNomeUsuario(usuario.getNomeUsuario());
		}else{

			sessao.removeAttribute("usuarioEncontrado");
			form.setLoginUsuario("");
			form.setNomeUsuario("Usuário Inexistente");
		}
	}

}