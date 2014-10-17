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
import gcom.atendimentopublico.ordemservico.OrdemServico;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Esta classe tem por finalidade exibir para o usuário a tela que receberá os parâmetros para
 * realização da atualização do dados das atividades de
 * uma OS
 * 
 * @author Rodrigo Oliveira
 * @date 21/02/2011
 */
public class ReprogramarOrdemServicoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("ordemServicoReprogramadas");
		ReprogramarOrdemServicoActionForm reprogramarOrdemServicoActionForm = (ReprogramarOrdemServicoActionForm) actionForm;
		HttpSession sessao = request.getSession(false);
		Fachada fachada = Fachada.getInstancia();

		// --------------------------------------------
		String idsOrdemServico = null;

		if(sessao.getAttribute("idsOSReprogramar") != null){
			idsOrdemServico = (String) sessao.getAttribute("idsOSReprogramar");
		}

		String[] idsOS = idsOrdemServico.split("\\$");
		List<OrdemServico> listaOrdemServico = new ArrayList<OrdemServico>();

		for(String idOS : idsOS){
			OrdemServico ordemServico = fachada.recuperaOSPorId(Integer.valueOf(idOS));
			listaOrdemServico.add(ordemServico);
		}
		Equipe novaEquipe = null;

		// ***********************************************************************
		// ***********************************************************************
		if(!GenericValidator.isBlankOrNull(reprogramarOrdemServicoActionForm.getEquipe())){
			FiltroEquipe filtroEquipe = new FiltroEquipe();
			filtroEquipe.adicionarParametro(new ParametroSimples(FiltroEquipe.INDICADOR_USO, ConstantesSistema.SIM));
			filtroEquipe.adicionarParametro(new ParametroSimples(FiltroEquipe.ID, reprogramarOrdemServicoActionForm.getEquipe()));

			Collection colecaoEquipes = fachada.pesquisar(filtroEquipe, Equipe.class.getName());
			if(colecaoEquipes != null && !colecaoEquipes.isEmpty()){
				novaEquipe = (Equipe) Util.retonarObjetoDeColecao(colecaoEquipes);
			}else{
				throw new ActionServletException("atencao.naocadastrado", null, "Equipe");
			}
		}else{
			throw new ActionServletException("atencao.selecione_uma_equipe");

		}
		// ***********************************************************************
		// ***********************************************************************

		Date dataRoteiro = Util.formatarDataInicial(Util.converteStringParaDate(reprogramarOrdemServicoActionForm.getDataRoteiro()));
		Date novaDataRoteiro = null;
		Date dataAtual = Util.formatarDataInicial(new Date());

		if(!GenericValidator.isBlankOrNull(reprogramarOrdemServicoActionForm.getNovaDataRoteiro())){
			if(!GenericValidator.isDate(reprogramarOrdemServicoActionForm.getNovaDataRoteiro(), "dd/MM/yyyy", false)){
				throw new ActionServletException("atencao.data.invalida", null, reprogramarOrdemServicoActionForm.getNovaDataRoteiro());
			}
			novaDataRoteiro = Util.formatarDataInicial(Util.converteStringParaDate(reprogramarOrdemServicoActionForm.getNovaDataRoteiro()));

			if(novaDataRoteiro.compareTo(dataAtual) < 0){
				throw new ActionServletException("atencao.data_menor_igual_atual");
			}
		}else{
			throw new ActionServletException("atencao.data_requerido");
		}

		fachada.reprogramarOrdensServico(listaOrdemServico, novaEquipe, dataRoteiro, novaDataRoteiro, getUsuarioLogado(request));
		// --------------------------------------------

		String caminhoRetorno = reprogramarOrdemServicoActionForm.getCaminhoRetorno();
		if(!GenericValidator.isBlankOrNull(caminhoRetorno)){
			request.setAttribute("caminhoRetorno", caminhoRetorno);
		}
		request.setAttribute("fecharPopup", "OK");
		// depois que fizer a reprogramação, remover o atributo que colocou na sessão
		sessao.removeAttribute("idsOSReprogramar");

		return retorno;
	}

}
