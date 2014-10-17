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

package gcom.gui.atendimentopublico.ordemservico;

import gcom.atendimentopublico.ordemservico.OrdemServico;
import gcom.cadastro.unidade.FiltroUnidadeOrganizacional;
import gcom.cadastro.unidade.UnidadeOrganizacional;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.FiltroUsuario;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * ExibirTramitarOrdemServicoAction
 * 
 * @author Ailton Sousa
 * @date 08/02/2012
 */
public class ExibirTramitarOrdemServicoAction
				extends GcomAction {

	/**
	 * Exibe a Tela para Tramitar o OS
	 * 
	 * @author Ailton Sousa
	 * @date 08/02/2012
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("tramitarOrdemServico");
		TramitarOrdemServicoActionForm tramitarOrdemServicoActionForm = (TramitarOrdemServicoActionForm) actionForm;
		HttpSession sessao = httpServletRequest.getSession(false);
		// Usuario logado no sistema
		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");

		Fachada fachada = Fachada.getInstancia();

		OrdemServico ordemServico = null;

		// Verifica se o usuário deseja desfezar as alterações efetuadas
		if(httpServletRequest.getParameter("desfazer") != null){
			tramitarOrdemServicoActionForm.resetarTramitacao();
		}

		// Reseta Tramitação
		if(tramitarOrdemServicoActionForm.getResetarTramitacao().equalsIgnoreCase("true")){
			tramitarOrdemServicoActionForm.resetarTramitacao();
		}

		String numeroOS = (String) httpServletRequest.getParameter("numeroOS");

		if(numeroOS != null){
			tramitarOrdemServicoActionForm.setNumeroOS(numeroOS);
			ordemServico = this.pesquisarOrdemServico(Util.converterStringParaInteger(numeroOS), fachada);

			this.verificarSituacaoOS(ordemServico, fachada);
		}

		sessao.setAttribute("ordemServico", ordemServico);

		if(tramitarOrdemServicoActionForm.getValidaUnidadeDestino().equalsIgnoreCase("false")
						&& tramitarOrdemServicoActionForm.getValidaUsuarioResponsavel().equalsIgnoreCase("false")){

			setUsuarioRegistro(tramitarOrdemServicoActionForm, usuario);
			if(tramitarOrdemServicoActionForm.getUsuarioResponsavelId() == null
							|| tramitarOrdemServicoActionForm.getUsuarioResponsavelId().equals("")){
				tramitarOrdemServicoActionForm.setUsuarioResponsavelId(usuario.getId().toString());
				getUsuarioResponsavel(tramitarOrdemServicoActionForm, sessao);
			}

			tramitarOrdemServicoActionForm.setDataTramite(Util.formatarData(new Date()));
			tramitarOrdemServicoActionForm.setHoraTramite(Util.formatarHoraSemSegundos(new Date()));
		}else{
			// Unidade de Destino
			if(tramitarOrdemServicoActionForm.getUnidadeDestinoId() != null
							&& !tramitarOrdemServicoActionForm.getUnidadeDestinoId().equals("")){
				getUnidadeDestino(tramitarOrdemServicoActionForm, sessao);
			}
			// Usuário Responsável
			if(tramitarOrdemServicoActionForm.getUsuarioResponsavelId() != null
							&& !tramitarOrdemServicoActionForm.getUsuarioResponsavelId().equals("")){
				getUsuarioResponsavel(tramitarOrdemServicoActionForm, sessao);
			}else{
				tramitarOrdemServicoActionForm.setUsuarioResponsavelId(usuario.getId().toString());
				getUsuarioResponsavel(tramitarOrdemServicoActionForm, sessao);
			}
		}

		return retorno;
	}

	/**
	 * Carrega Usuário de Registro
	 * 
	 * @author Ailton Sousa
	 * @date 08/02/2012
	 * @param form
	 */
	private void setUsuarioRegistro(TramitarOrdemServicoActionForm tramitarOrdemServicoActionForm, Usuario usuario){

		tramitarOrdemServicoActionForm.setUsuarioRegistroId(usuario.getId() + "");
		tramitarOrdemServicoActionForm.setUsuarioRegistroNome(usuario.getNomeUsuario());
		tramitarOrdemServicoActionForm.setUsuarioRegistroUnidade(usuario.getUnidadeOrganizacional().getId() + "");
		tramitarOrdemServicoActionForm.setUsuarioRegistroUnidadeIndicadorTarifaSocial(usuario.getUnidadeOrganizacional()
						.getIndicadorTarifaSocial()
						+ "");
	}

	/**
	 * Recupera Unidade de Destino
	 * 
	 * @author Ailton Sousa
	 * @date 08/02/2012
	 * @param form
	 */
	private void getUnidadeDestino(TramitarOrdemServicoActionForm form, HttpSession sessao){

		// [F0004] Valida Unidade de Destino
		Fachada fachada = Fachada.getInstancia();
		// Filtra Unidade de Destino
		FiltroUnidadeOrganizacional filtroUnidadeDestino = new FiltroUnidadeOrganizacional();
		filtroUnidadeDestino.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.ID, form.getUnidadeDestinoId()));
		filtroUnidadeDestino.adicionarCaminhoParaCarregamentoEntidade("unidadeTipo");
		// filtroUnidadeDestino.adicionarCaminhoParaCarregamentoEntidade("unidadeTipo.codigoTipo");
		// Recupera Unidade de Destino
		Collection colecaoUnidadeDestino = fachada.pesquisar(filtroUnidadeDestino, UnidadeOrganizacional.class.getName());
		if(colecaoUnidadeDestino != null && !colecaoUnidadeDestino.isEmpty()){
			UnidadeOrganizacional unidadeDestino = (UnidadeOrganizacional) Util.retonarObjetoDeColecao(colecaoUnidadeDestino);
			form.setUnidadeDestinoDescricao(unidadeDestino.getDescricao());
			form.setUnidadeDestinoIndicadorTramite(unidadeDestino.getIndicadorTramite() + "");
			form.setUnidadeDestinoCodigoTipo(unidadeDestino.getUnidadeTipo().getCodigoTipo());
			sessao.setAttribute("unidadeDestinoEncontrada", "true");
		}else{
			sessao.removeAttribute("unidadeDestinoEncontrada");
			form.setUnidadeDestinoDescricao("Unidade Organizacional inexistente");
			form.setUnidadeDestinoId("");
		}
		form.setValidaUnidadeDestino("false");
	}

	/**
	 * Recupera Usuário Responsável
	 * 
	 * @author Ailton Sousa
	 * @date 08/02/2012
	 * @param form
	 */
	private void getUsuarioResponsavel(TramitarOrdemServicoActionForm form, HttpSession sessao){

		// [F0005] Valida Usuário Responsável
		Fachada fachada = Fachada.getInstancia();
		// Filtra usuário
		FiltroUsuario filtroUsuarioResponsavel = new FiltroUsuario();
		filtroUsuarioResponsavel.adicionarParametro(new ParametroSimples(FiltroUsuario.ID, form.getUsuarioResponsavelId()));
		// Recupera usuário
		Collection colecaoUsuarioResponsavel = fachada.pesquisar(filtroUsuarioResponsavel, Usuario.class.getName());
		if(colecaoUsuarioResponsavel != null && !colecaoUsuarioResponsavel.isEmpty()){
			Usuario usuario = (Usuario) Util.retonarObjetoDeColecao(colecaoUsuarioResponsavel);
			form.setUsuarioResponsavelNome(usuario.getNomeUsuario());
			sessao.setAttribute("usuarioResponsavelEncontrada", "true");
		}else{
			sessao.removeAttribute("usuarioResponsavelEncontrada");
			form.setUsuarioResponsavelNome("Usuário inexistente");
			form.setUsuarioResponsavelId("");
		}
		form.setValidaUsuarioResponsavel("false");
	}

	/**
	 * Consulta a ordem de serviço pelo id informado
	 * 
	 * @author Ailton Sousa
	 * @created 10/02/2012
	 */
	private OrdemServico pesquisarOrdemServico(Integer id, Fachada fachada){

		OrdemServico retorno = fachada.consultarDadosOrdemServico(id);
		if(retorno == null){
			throw new ActionServletException("atencao.naocadastrado", null, "Ordem de Serviço");
		}
		return retorno;
	}

	/**
	 * [FS0001] - Verificar situação das OS
	 * 
	 * @author Ailton Sousa
	 * @date 10/02/2012
	 * @param ordemServico
	 * @param fachada
	 */
	private void verificarSituacaoOS(OrdemServico ordemServico, Fachada fachada){

		if(ordemServico != null){
			if(ordemServico.getSituacao() == OrdemServico.SITUACAO_ENCERRADO.shortValue()){
				throw new ActionServletException("atencao.ordem_servico_encerrada_nao_tramita");
			}

			if(ordemServico.getSituacao() == OrdemServico.SITUACAO_AGUARDANDO_LIBERACAO.shortValue()){
				throw new ActionServletException("atencao.ordem_servico_pendente_nao_tramita", ordemServico.getOsReferencia().getId()
								.toString());
			}

			if(ordemServico.getSituacao() == OrdemServico.PROGRAMADA.shortValue()){
				Integer idOS = fachada.pesquisarOSProgramacaoAtiva(ordemServico.getId());

				if(idOS != null){
					throw new ActionServletException("atencao.ordem_servico_programada_nao_tramita");
				}
			}
		}
	}
}