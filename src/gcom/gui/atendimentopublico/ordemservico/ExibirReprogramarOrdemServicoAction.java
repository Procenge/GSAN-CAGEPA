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

import gcom.atendimentopublico.ordemservico.Equipe;
import gcom.atendimentopublico.ordemservico.FiltroEquipe;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
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
 * Esta classe tem por finalidade exibir para o usuário a tela que receberá os parâmetros para
 * reprogramação de uma OS
 * 
 * @author Rodrigo Oliveira
 * @date 18/02/2011
 */
public class ExibirReprogramarOrdemServicoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirReprogramarOrdemServico");
		ReprogramarOrdemServicoActionForm reprogramarOrdemServicoActionForm = (ReprogramarOrdemServicoActionForm) actionForm;
		HttpSession sessao = request.getSession(false);
		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");
		Fachada fachada = Fachada.getInstancia();

		String[] idsOS = request.getParameter("idsOS").split("\\$");

		reprogramarOrdemServicoActionForm.setSelecionadas(idsOS.length + "");

		sessao.setAttribute("idsOSReprogramar", request.getParameter("idsOS"));
		// -----------------------------------------------------------------------------------------
		// -----------------------------------------------------------------------------------------
		Integer idUnidadeLotacao = usuario.getUnidadeOrganizacional().getId();

		Date dataRoteiro = Util.converteStringParaDate(reprogramarOrdemServicoActionForm.getDataRoteiro());

		for(int i = 0; i < idsOS.length; i++){
			Collection<Equipe> colecao = fachada.recuperaEquipeDaOSProgramacaoPorDataRoteiro(new Integer(idsOS[i]), dataRoteiro);

			if(colecao != null && colecao.size() > 1){
				throw new ActionServletException("atencao.ordem_servico_programada_varias_equipes_impossivel_reprogramar");
			}
		}
		// -----------------------------------------------------------------------------------------
		// -----------------------------------------------------------------------------------------

		// Pesquisa as equipes associadas a unidade
		this.pesquisarEquipe(sessao, usuario);

		String caminhoRetorno = "";
		if(request.getParameter("caminhoRetorno") != null){
			caminhoRetorno = request.getParameter("caminhoRetorno");
		}
		reprogramarOrdemServicoActionForm.setCaminhoRetorno(caminhoRetorno);

		return retorno;
	}

	private void pesquisarEquipe(HttpSession sessao, Usuario usuario){

		// Filtro para Equipe
		Collection colecaoEquipes = (Collection) sessao.getAttribute("colecaoEquipes");

		if(colecaoEquipes == null){

			FiltroEquipe filtroEquipe = new FiltroEquipe();
			filtroEquipe.adicionarParametro(new ParametroSimples(FiltroEquipe.INDICADOR_USO, ConstantesSistema.SIM));
			filtroEquipe.adicionarParametro(new ParametroSimples(FiltroEquipe.ID_UNIDADE_ORGANIZACIONAL, usuario.getUnidadeOrganizacional()
							.getId()));

			filtroEquipe.setCampoOrderBy(FiltroEquipe.NOME);

			colecaoEquipes = Fachada.getInstancia().pesquisar(filtroEquipe, Equipe.class.getName());

			if(colecaoEquipes != null && !colecaoEquipes.isEmpty()){
				sessao.setAttribute("colecaoEquipes", colecaoEquipes);
			}else{
				throw new ActionServletException("atencao.naocadastrado", null, "Equipe");
			}
		}

	}

}
