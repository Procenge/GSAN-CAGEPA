/*
 * Copyright (C) 2007-2007 the GSAN - Sistema Integrado de Gest�o de Servi�os de Saneamento
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
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA
 */

/*
 * GSAN - Sistema Integrado de Gest�o de Servi�os de Saneamento
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

package gcom.gui.cadastro.cliente.atividadeeconomica;

import gcom.atendimentopublico.ligacaoesgoto.FiltroLigacaoEsgotoPerfil;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoPerfil;
import gcom.cadastro.cliente.AtividadeEconomica;
import gcom.cadastro.cliente.FiltroAtividadeEconomica;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
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
 * [UC3150] Manter Atividade Econ�mica
 * [SB0001] - Atualizar Atividade Econ�mica
 * 
 * @author Anderson Italo
 * @date 29/06/2014
 */
public class ExibirAtualizarAtividadeEconomicaAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("atividadeEconomicaAtualizar");

		AtualizarAtividadeEconomicaActionForm form = (AtualizarAtividadeEconomicaActionForm) actionForm;

		HttpSession sessao = httpServletRequest.getSession(false);

		Fachada fachada = Fachada.getInstancia();

		String id = null;

		if(httpServletRequest.getParameter("idRegistroAtualizacao") != null){

			id = httpServletRequest.getParameter("idRegistroAtualizacao");
		}else{

			id = ((Integer) sessao.getAttribute("idRegistroAtualizacao")).toString();
		}

		if(httpServletRequest.getParameter("manter") != null){

			sessao.setAttribute("manter", true);
		}else if(httpServletRequest.getParameter("filtrar") != null){

			sessao.removeAttribute("manter");
		}

		AtividadeEconomica atividadeEconomica = new AtividadeEconomica();

		if(id != null && !id.trim().equals("") && Integer.parseInt(id) > 0){

			FiltroAtividadeEconomica filtroAtividadeEconomica = new FiltroAtividadeEconomica();
			filtroAtividadeEconomica.adicionarCaminhoParaCarregamentoEntidade(FiltroAtividadeEconomica.LIGACAO_ESGOTO_PERFIL);

			filtroAtividadeEconomica.adicionarParametro(new ParametroSimples(FiltroAtividadeEconomica.ID, id));

			Collection colecaoAtividadeEconomica = fachada.pesquisar(filtroAtividadeEconomica, AtividadeEconomica.class.getName());

			if(colecaoAtividadeEconomica != null && !colecaoAtividadeEconomica.isEmpty()){

				atividadeEconomica = (AtividadeEconomica) Util.retonarObjetoDeColecao(colecaoAtividadeEconomica);
			}

			if(atividadeEconomica.getId() != null && !atividadeEconomica.getId().equals("")){

				form.setId(atividadeEconomica.getId().toString());
			}

			if(atividadeEconomica.getCodigo() != null && !atividadeEconomica.getCodigo().equals("")){

				form.setCodigo(atividadeEconomica.getCodigo());
			}

			if(atividadeEconomica.getDescricao() != null && !atividadeEconomica.getDescricao().equals("")){

				form.setDescricao(atividadeEconomica.getDescricao());
			}

			form.setIndicadorUso(atividadeEconomica.getIndicadorUso());

			if(atividadeEconomica.getLigacaoEsgotoPerfil() != null){

				if(atividadeEconomica.getLigacaoEsgotoPerfil().getId() != null
								&& !atividadeEconomica.getLigacaoEsgotoPerfil().getId().equals("")){

					form.setIdLigacaoEsgotoPerfil(atividadeEconomica.getLigacaoEsgotoPerfil().getId().toString());
				}
			}

			FiltroLigacaoEsgotoPerfil filtroLigacaoEsgotoPerfil = new FiltroLigacaoEsgotoPerfil(FiltroLigacaoEsgotoPerfil.ID);
			filtroLigacaoEsgotoPerfil.adicionarParametro(new ParametroSimples(FiltroLigacaoEsgotoPerfil.INDICADOR_USO,
							ConstantesSistema.SIM));
			Collection<LigacaoEsgotoPerfil> colecaoLigacaoEsgotoPerfil = fachada.pesquisar(filtroLigacaoEsgotoPerfil,
							LigacaoEsgotoPerfil.class.getName());

			// [FS0001] - Verificar exist�ncia de dados
			if(!Util.isVazioOrNulo(colecaoLigacaoEsgotoPerfil)){

				this.getSessao(httpServletRequest).setAttribute("colecaoLigacaoEsgotoPerfil", colecaoLigacaoEsgotoPerfil);
			}

			sessao.setAttribute("atualizarAtividadeEconomica", atividadeEconomica);

			if(sessao.getAttribute("colecaoAtividadeEconomica") != null){

				sessao.setAttribute("caminhoRetornoVoltar", "/gsan/filtrarAtividadeEconomicaAction.do");
			}else{

				sessao.setAttribute("caminhoRetornoVoltar", "/gsan/exibirFiltrarAtividadeEconomicaAction.do");
			}

		}

		return retorno;
	}
}