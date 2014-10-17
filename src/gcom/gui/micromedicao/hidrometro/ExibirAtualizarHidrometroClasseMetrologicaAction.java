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

package gcom.gui.micromedicao.hidrometro;

import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.micromedicao.hidrometro.FiltroHidrometroClasseMetrologica;
import gcom.micromedicao.hidrometro.HidrometroClasseMetrologica;
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
 * @author Victon Santos
 * @date 02/07/2013
 */

public class ExibirAtualizarHidrometroClasseMetrologicaAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("atualizarHidrometroClasseMetrologica");

		AtualizarHidrometroClasseMetrologicaActionForm atualizarHidrometroClasseMetrologicaActionForm = (AtualizarHidrometroClasseMetrologicaActionForm) actionForm;

		HttpSession sessao = httpServletRequest.getSession(false);

		Fachada fachada = Fachada.getInstancia();

		HidrometroClasseMetrologica hidrometroClasseMetrologica = null;

		String idHidrometroClasseMetrologica = null;

		if(httpServletRequest.getParameter("idHidrometroClasseMetrologica") != null){
			// tela do manter
			idHidrometroClasseMetrologica = (String) httpServletRequest.getParameter("idHidrometroClasseMetrologica");
			sessao.setAttribute("idHidrometroClasseMetrologica", idHidrometroClasseMetrologica);
			sessao.setAttribute("caminhoRetornoVoltar", "/gsan/exibirManterHidrometroClasseMetrologicaAction.do");
		}else if(sessao.getAttribute("idHidrometroClasseMetrologica") != null){
			// tela do filtrar
			idHidrometroClasseMetrologica = (String) sessao.getAttribute("idHidrometroClasseMetrologica");
			sessao.setAttribute("caminhoRetornoVoltar", "/gsan/exibirFiltrarHidrometroClasseMetrologicaAction.do");
		}else if(httpServletRequest.getParameter("idRegistroInseridoAtualizar") != null){
			// link na tela de sucesso do inserir sistema esgoto
			idHidrometroClasseMetrologica = (String) httpServletRequest.getParameter("idRegistroInseridoAtualizar");
			sessao.setAttribute("caminhoRetornoVoltar", "/gsan/exibirFiltrarHidrometroClasseMetrologicaAction.do?menu=sim");
		}

		if(idHidrometroClasseMetrologica == null){

			if(sessao.getAttribute("idRegistroAtualizar") != null){
				hidrometroClasseMetrologica = (HidrometroClasseMetrologica) sessao.getAttribute("idRegistroAtualizar");
			}else{
				idHidrometroClasseMetrologica = (String) httpServletRequest.getParameter("idHidrometroClasseMetrologica").toString();
			}
		}

		// ------Inicio da parte que verifica se vem da página de sistema_esgoto_manter.jsp
		if(hidrometroClasseMetrologica == null){

			if(idHidrometroClasseMetrologica != null && !idHidrometroClasseMetrologica.equals("")){

				FiltroHidrometroClasseMetrologica filtroHidrometroClasseMetrologica = new FiltroHidrometroClasseMetrologica();

				filtroHidrometroClasseMetrologica.adicionarParametro(new ParametroSimples(FiltroHidrometroClasseMetrologica.ID,
								idHidrometroClasseMetrologica));

				Collection colecaoHidrometroClasseMetrologica = fachada.pesquisar(filtroHidrometroClasseMetrologica,
								HidrometroClasseMetrologica.class.getName());

				if(colecaoHidrometroClasseMetrologica != null && !colecaoHidrometroClasseMetrologica.isEmpty()){

					hidrometroClasseMetrologica = (HidrometroClasseMetrologica) Util
									.retonarObjetoDeColecao(colecaoHidrometroClasseMetrologica);

				}
			}
		}

		// ------ O ClasseMetrologica do Hidrometro foi encontrada
		atualizarHidrometroClasseMetrologicaActionForm.setIdHidrometroClasseMetrologica(hidrometroClasseMetrologica.getId().toString());
		atualizarHidrometroClasseMetrologicaActionForm.setDescricaoClasseMetrologicaHidrometro(hidrometroClasseMetrologica.getDescricao());
		atualizarHidrometroClasseMetrologicaActionForm.setDescricaoAbreviada(hidrometroClasseMetrologica.getDescricaoAbreviada());
		atualizarHidrometroClasseMetrologicaActionForm.setIndicadorUso(hidrometroClasseMetrologica.getIndicadorUso().toString());

		sessao.setAttribute("hidrometroClasseMetrologica", hidrometroClasseMetrologica);

		httpServletRequest.setAttribute("idHidrometroClasseMetrologica", idHidrometroClasseMetrologica);

		// ------ Fim da parte que verifica se vem da página de
		// hidrometro_Classe_Metrologica_manter.jsp

		return retorno;
	}
}
