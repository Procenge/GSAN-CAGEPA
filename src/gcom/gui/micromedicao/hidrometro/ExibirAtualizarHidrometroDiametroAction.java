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
import gcom.micromedicao.hidrometro.FiltroHidrometroDiametro;
import gcom.micromedicao.hidrometro.HidrometroDiametro;
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
 * [UC] MANTER DIAMETRO HIDROMETRO
 * 
 * @author Deyverson Santos
 * @date 15/12/2008
 */

public class ExibirAtualizarHidrometroDiametroAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("atualizarHidrometroDiametro");

		AtualizarHidrometroDiametroActionForm atualizarHidrometroDiametroActionForm = (AtualizarHidrometroDiametroActionForm) actionForm;

		HttpSession sessao = httpServletRequest.getSession(false);

		Fachada fachada = Fachada.getInstancia();

		HidrometroDiametro hidrometroDiametro = null;

		String idHidrometroDiametro = null;

		if(httpServletRequest.getParameter("idHidrometroDiametro") != null){
			// tela do manter
			idHidrometroDiametro = (String) httpServletRequest.getParameter("idHidrometroDiametro");
			sessao.setAttribute("idHidrometroDiametro", idHidrometroDiametro);
			sessao.setAttribute("caminhoRetornoVoltar", "/gsan/exibirManterHidrometroDiametroAction.do");
		}else if(sessao.getAttribute("idHidrometroDiametro") != null){
			// tela do filtrar
			idHidrometroDiametro = (String) sessao.getAttribute("idHidrometroDiametro");
			sessao.setAttribute("caminhoRetornoVoltar", "/gsan/exibirFiltrarHidrometroDiametroAction.do");
		}else if(httpServletRequest.getParameter("idRegistroInseridoAtualizar") != null){
			// link na tela de sucesso do inserir sistema esgoto
			idHidrometroDiametro = (String) httpServletRequest.getParameter("idRegistroInseridoAtualizar");
			sessao.setAttribute("caminhoRetornoVoltar", "/gsan/exibirFiltrarHidrometroDiametroAction.do?menu=sim");
		}

		if(idHidrometroDiametro == null){

			if(sessao.getAttribute("idRegistroAtualizar") != null){
				hidrometroDiametro = (HidrometroDiametro) sessao.getAttribute("idRegistroAtualizar");
			}else{
				idHidrometroDiametro = (String) httpServletRequest.getParameter("idHidrometroDiametro").toString();
			}
		}

		// ------Inicio da parte que verifica se vem da página de sistema_esgoto_manter.jsp
		if(hidrometroDiametro == null){

			if(idHidrometroDiametro != null && !idHidrometroDiametro.equals("")){

				FiltroHidrometroDiametro filtroHidrometroDiametro = new FiltroHidrometroDiametro();

				filtroHidrometroDiametro.adicionarParametro(new ParametroSimples(FiltroHidrometroDiametro.ID, idHidrometroDiametro));

				Collection colecaoHidrometroDiametro = fachada.pesquisar(filtroHidrometroDiametro, HidrometroDiametro.class.getName());

				if(colecaoHidrometroDiametro != null && !colecaoHidrometroDiametro.isEmpty()){

					hidrometroDiametro = (HidrometroDiametro) Util.retonarObjetoDeColecao(colecaoHidrometroDiametro);

				}
			}
		}

		// ------ O diametro do Hidrometro foi encontrada
		atualizarHidrometroDiametroActionForm.setIdHidrometroDiametro(hidrometroDiametro.getId().toString());
		atualizarHidrometroDiametroActionForm.setDescricaoDiametroHidrometro(hidrometroDiametro.getDescricao());
		atualizarHidrometroDiametroActionForm.setDescricaoAbreviada(hidrometroDiametro.getDescricaoAbreviada());
		atualizarHidrometroDiametroActionForm.setIndicadorUso(hidrometroDiametro.getIndicadorUso().toString());

		sessao.setAttribute("hidrometroDiametro", hidrometroDiametro);

		httpServletRequest.setAttribute("idHidrometroDiametro", idHidrometroDiametro);

		// ------ Fim da parte que verifica se vem da página de hidrometro_diametro_manter.jsp

		return retorno;
	}
}
