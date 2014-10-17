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

package gcom.gui.atendimentopublico.registroatendimento;

import gcom.atendimentopublico.registroatendimento.FiltroSolicitacaoTipo;
import gcom.atendimentopublico.registroatendimento.FiltroSolicitacaoTipoEspecificacao;
import gcom.atendimentopublico.registroatendimento.RegistroAtendimento;
import gcom.atendimentopublico.registroatendimento.SolicitacaoTipo;
import gcom.atendimentopublico.registroatendimento.SolicitacaoTipoEspecificacao;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.cliente.FiltroCliente;
import gcom.cadastro.imovel.FiltroImovel;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.unidade.FiltroUnidadeOrganizacional;
import gcom.cadastro.unidade.UnidadeOrganizacional;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.FiltroUsuario;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Filtrar Registro Atendimento Incompleto - Exibir
 * 
 * @author Andre Nishimura - 05/02/2010
 */
public class ExibirFiltrarRegistroAtendimentoIncompletoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("filtrarRegistroAtendimentoIncompleto");
		Fachada fachada = Fachada.getInstancia();
		HttpSession sessao = httpServletRequest.getSession(false);

		FiltrarRegistroAtendimentoIncompletoActionForm filtrarRegistroAtendimentoActionForm = (FiltrarRegistroAtendimentoIncompletoActionForm) actionForm;

		String menu = httpServletRequest.getParameter("menu");

		String apareceMenu = httpServletRequest.getParameter("apareceMenu");
		if(apareceMenu != null && !apareceMenu.equalsIgnoreCase("")){
			httpServletRequest.setAttribute(apareceMenu, apareceMenu);
		}

		if(menu != null && !menu.equalsIgnoreCase("")){
			filtrarRegistroAtendimentoActionForm.setIndicadorRetornoChamada(ConstantesSistema.TODOS.toString());
			Integer qtdDias = 30;

			Date dataAtual = new Date();
			Date dataSugestao = Util.subtrairNumeroDiasDeUmaData(dataAtual, qtdDias);

			filtrarRegistroAtendimentoActionForm.setPeriodoAtendimentoInicial(Util.formatarData(dataSugestao));
			filtrarRegistroAtendimentoActionForm.setPeriodoAtendimentoFinal(Util.formatarData(dataAtual));
		}
		sessao.removeAttribute("filtroATIN");
		// Matrícula
		if(filtrarRegistroAtendimentoActionForm.getMatriculaImovel() != null
						&& !filtrarRegistroAtendimentoActionForm.getMatriculaImovel().equals("")){
			this.getImovel(filtrarRegistroAtendimentoActionForm, httpServletRequest);
		}else{
			filtrarRegistroAtendimentoActionForm.setInscricaoImovel("");
		}

		if(filtrarRegistroAtendimentoActionForm.getCliente() != null && !filtrarRegistroAtendimentoActionForm.getCliente().equals("")){
			this.getCliente(filtrarRegistroAtendimentoActionForm.getCliente(), filtrarRegistroAtendimentoActionForm, fachada,
							httpServletRequest);
		}

		if(filtrarRegistroAtendimentoActionForm.getUsuarioAtendimento() != null
						&& !filtrarRegistroAtendimentoActionForm.getUsuarioAtendimento().equals("")){
			String usuarioAtendimentoDescricao = this.getUsuarioDescricao(filtrarRegistroAtendimentoActionForm, fachada,
							filtrarRegistroAtendimentoActionForm.getUsuarioAtendimento());
			if(!usuarioAtendimentoDescricao.equals("Usuario Inexistente")){
				filtrarRegistroAtendimentoActionForm.setUsuarioAtendimentoDescricao(usuarioAtendimentoDescricao);
				httpServletRequest.setAttribute("usuarioAtendimentoDescricao", "Usuario Existente");
			}else{
				filtrarRegistroAtendimentoActionForm.setUsuarioAtendimento("");
				filtrarRegistroAtendimentoActionForm.setUsuarioAtendimentoDescricao("Usuário Inexistente");
			}
		}else{
			filtrarRegistroAtendimentoActionForm.setUsuarioAtendimentoDescricao("");
		}

		if(filtrarRegistroAtendimentoActionForm.getUnidadeAtendimento() != null
						&& !filtrarRegistroAtendimentoActionForm.getUnidadeAtendimento().equals("")){
			String unidadeAtendimentoDescricao = this.getUnidadeDescricao(filtrarRegistroAtendimentoActionForm, fachada,
							filtrarRegistroAtendimentoActionForm.getUnidadeAtendimento());
			if(!unidadeAtendimentoDescricao.equals("Unidade Inexistente")){
				filtrarRegistroAtendimentoActionForm.setUnidadeAtendimentoDescricao(unidadeAtendimentoDescricao);
				httpServletRequest.setAttribute("unidadeAtendimentoEncontrada", "Usuario Existente");
			}else{
				filtrarRegistroAtendimentoActionForm.setUnidadeAtendimento("");
				filtrarRegistroAtendimentoActionForm.setUnidadeAtendimentoDescricao("Unidade Inexistente");
			}
		}else{
			filtrarRegistroAtendimentoActionForm.setUnidadeAtendimentoDescricao("");
		}

		if(filtrarRegistroAtendimentoActionForm.getUsuarioRetornoChamada() != null
						&& !filtrarRegistroAtendimentoActionForm.getUsuarioRetornoChamada().equalsIgnoreCase("")){
			String usuarioRetornoChamadaDescricao = this.getUsuarioDescricao(filtrarRegistroAtendimentoActionForm, fachada,
							filtrarRegistroAtendimentoActionForm.getUsuarioRetornoChamada());
			if(!usuarioRetornoChamadaDescricao.equals("Usuario Inexistente")){
				filtrarRegistroAtendimentoActionForm.setUsuarioRetornoChamadaDescricao(usuarioRetornoChamadaDescricao);
				httpServletRequest.setAttribute("usuarioRetornoChamadaDescricao", "Usuario Existente");
			}else{
				filtrarRegistroAtendimentoActionForm.setUsuarioRetornoChamada("");
				filtrarRegistroAtendimentoActionForm.setUsuarioRetornoChamadaDescricao("Usuário Inexistente");
			}
		}else{
			filtrarRegistroAtendimentoActionForm.setUsuarioRetornoChamadaDescricao("");
		}

		if(filtrarRegistroAtendimentoActionForm.getUnidadeRetornoChamada() != null
						&& !filtrarRegistroAtendimentoActionForm.getUnidadeRetornoChamada().equalsIgnoreCase("")){
			String unidadeRetornoChamadaDescricao = this.getUnidadeDescricao(filtrarRegistroAtendimentoActionForm, fachada,
							filtrarRegistroAtendimentoActionForm.getUnidadeRetornoChamada());
			if(!unidadeRetornoChamadaDescricao.equals("Unidade Inexistente")){
				filtrarRegistroAtendimentoActionForm.setUnidadeRetornoChamadaDescricao(unidadeRetornoChamadaDescricao);
				httpServletRequest.setAttribute("unidadeRetornoChamadaDescricao", "Usuario Existente");
			}else{
				filtrarRegistroAtendimentoActionForm.setUnidadeRetornoChamada("");
				filtrarRegistroAtendimentoActionForm.setUnidadeRetornoChamadaDescricao("Unidade Inexistente");
			}
		}else{
			filtrarRegistroAtendimentoActionForm.setUnidadeRetornoChamadaDescricao("");
		}

		if(filtrarRegistroAtendimentoActionForm.getRADefinitivo() != null
						&& !filtrarRegistroAtendimentoActionForm.getRADefinitivo().equalsIgnoreCase("")){
			String raDefinitivaDescricao = this.getRADefinitiva(filtrarRegistroAtendimentoActionForm.getRADefinitivo(), fachada,
							httpServletRequest);
			if(!raDefinitivaDescricao.equals("RA Inexistente")){
				filtrarRegistroAtendimentoActionForm.setDescricaoRA(raDefinitivaDescricao);
			}else{
				filtrarRegistroAtendimentoActionForm.setRADefinitivo("");
				filtrarRegistroAtendimentoActionForm.setDescricaoRA("RA Inexistente");
			}
		}else{
			filtrarRegistroAtendimentoActionForm.setDescricaoRA("");
		}

		// Tipo Solicitação
		getSolicitacaoTipoCollection(sessao, fachada);

		// Especificação
		if(filtrarRegistroAtendimentoActionForm.getTipoSolicitacao() != null
						&& filtrarRegistroAtendimentoActionForm.getTipoSolicitacao().length > 0){

			getSolicitacaoTipoEspecificacaoCollection(sessao, fachada, filtrarRegistroAtendimentoActionForm);
		}else{

			filtrarRegistroAtendimentoActionForm.setSelectedTipoSolicitacaoSize("0");
		}

		try{
			Collection colecaoMotivoAtendimentoIncompleto = fachada.listarMotivoAtendimentoIncompleto();
			sessao.setAttribute("colecaoMotivoAtendimentoIncompleto", colecaoMotivoAtendimentoIncompleto);
		}catch(ControladorException e){
			throw new ActionServletException("", e);
		}
		httpServletRequest.setAttribute("nomeCampo", "numeroRA");
		return retorno;
	}

	/**
	 * Recupera Imóvel
	 * 
	 * @author Leonardo Regis
	 * @date 02/08/2006
	 * @param filtrarRegistroAtendimentoIncompletoActionForm
	 * @param fachada
	 */
	private void getImovel(FiltrarRegistroAtendimentoIncompletoActionForm filtrarRegistroAtendimentoIncompletoActionForm,
					HttpServletRequest httpServletRequest){

		Fachada fachada = Fachada.getInstancia();
		HttpSession sessao = httpServletRequest.getSession(false);
		// [F0001] Valida Imovel
		// if (isValidateObject(filtrarRegistroAtendimentoActionForm)) {
		// Filtra Imovel
		FiltroImovel filtroImovel = new FiltroImovel();
		filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, filtrarRegistroAtendimentoIncompletoActionForm
						.getMatriculaImovel()));
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade("localidade");
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade("setorComercial");
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade("quadra");
		// Recupera Imóvel
		Collection colecaoImovel = fachada.pesquisar(filtroImovel, Imovel.class.getName());
		if(colecaoImovel != null && !colecaoImovel.isEmpty()){
			sessao.setAttribute("inscricaoImovelEncontrada", "true");
			Imovel imovel = (Imovel) colecaoImovel.iterator().next();
			filtrarRegistroAtendimentoIncompletoActionForm.setInscricaoImovel(imovel.getInscricaoFormatada());
		}else{
			sessao.removeAttribute("inscricaoImovelEncontrada");
			filtrarRegistroAtendimentoIncompletoActionForm.setMatriculaImovel("");
			filtrarRegistroAtendimentoIncompletoActionForm.setInscricaoImovel("Matrícula inexistente");
		}
		// }
	}

	/**
	 * Carrega coleção de solicitação tipo
	 * 
	 * @author Leonardo Regis
	 * @date 03/08/2006
	 * @param sessao
	 * @param fachada
	 */
	private void getSolicitacaoTipoCollection(HttpSession sessao, Fachada fachada){

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
	 * @author Leonardo Regis
	 * @date 03/08/2006
	 * @param sessao
	 * @param fachada
	 */
	private void getSolicitacaoTipoEspecificacaoCollection(HttpSession sessao, Fachada fachada,
					FiltrarRegistroAtendimentoIncompletoActionForm filtrarRegistroAtendimentoIncompletoActionForm){

		String[] solicitacaoTipo = (String[]) filtrarRegistroAtendimentoIncompletoActionForm.getTipoSolicitacao();
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
			}

		}else{
			sessao.setAttribute("colecaoEspecificacao", new ArrayList());
		}
	}

	/**
	 * Recupera a Unidade Organizacional (Atendimento, Atual e Superior)
	 * [F0006] Valida Unidade
	 * 
	 * @author Leonardo Regis
	 * @date 04/08/2006
	 * @param filtrarRegistroAtendimentoIncompletoActionForm
	 * @param fachada
	 * @param unidadeId
	 * @return Descrição da Unidade Filtrada
	 */
	private String getUnidadeDescricao(FiltrarRegistroAtendimentoIncompletoActionForm filtrarRegistroAtendimentoIncompletoActionForm,
					Fachada fachada, String unidadeId){

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

	private String getUsuarioDescricao(FiltrarRegistroAtendimentoIncompletoActionForm filtrarRegistroAtendimentoIncompletoActionForm,
					Fachada fachada, String usuarioId){

		// Filtra Unidade
		FiltroUsuario filtroUsuario = new FiltroUsuario();
		filtroUsuario.adicionarParametro(new ParametroSimples(FiltroUsuario.ID, usuarioId));
		// filtroUnidadeOrganizacional.adicionarCaminhoParaCarregamentoEntidade("descricao");
		// Recupera Unidade Organizacional
		Collection colecaoUsuarioOrganizacional = fachada.pesquisar(filtroUsuario, Usuario.class.getName());
		if(colecaoUsuarioOrganizacional != null && !colecaoUsuarioOrganizacional.isEmpty()){
			Usuario usuario = (Usuario) colecaoUsuarioOrganizacional.iterator().next();
			filtrarRegistroAtendimentoIncompletoActionForm.setUsuarioNaoEncontrado("false");
			return usuario.getNomeUsuario();
		}else{
			filtrarRegistroAtendimentoIncompletoActionForm.setUsuarioNaoEncontrado("true");
			return "Usuario Inexistente";
		}
	}

	public void getCliente(String idCliente, FiltrarRegistroAtendimentoIncompletoActionForm filtrarImovelFiltrarActionForm,
					Fachada fachada, HttpServletRequest httpServletRequest){

		FiltroCliente filtroCliente = new FiltroCliente();

		filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.ID, idCliente));
		filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));

		Collection clienteEncontrado = fachada.pesquisar(filtroCliente, Cliente.class.getName());

		if(clienteEncontrado != null && !clienteEncontrado.isEmpty()){

			filtrarImovelFiltrarActionForm.setCliente("" + ((Cliente) ((List) clienteEncontrado).get(0)).getId());
			filtrarImovelFiltrarActionForm.setClientedescricao(((Cliente) ((List) clienteEncontrado).get(0)).getNome());

			httpServletRequest.setAttribute("nomeCampo", "matriculaImovel");

		}else{
			filtrarImovelFiltrarActionForm.setCliente("");
			filtrarImovelFiltrarActionForm.setClientedescricao("Cliente inexistente");
			httpServletRequest.setAttribute("idClienteNaoEncontrado", "true");
			httpServletRequest.setAttribute("nomeCampo", "matriculaImovel");
			httpServletRequest.getSession(false).setAttribute("filtrarRegistroAtendimentoIncompletoActionForm",
							filtrarImovelFiltrarActionForm);
		}
	}

	public String getRADefinitiva(String idRA, Fachada fachada, HttpServletRequest httpServletRequest){

		RegistroAtendimento ra = fachada.pesquisarRegistroAtendimento(Integer.valueOf(idRA));
		if(ra != null){
			httpServletRequest.setAttribute("numeroRAEncontrada", "true");
			return ra.getSolicitacaoTipoEspecificacao().getDescricao();
		}else{
			return "RA Inexistente";
		}
	}
}