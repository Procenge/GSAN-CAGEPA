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
 * 
 * GSANPCG
 * Virg�nia Melo
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

package gcom.gui.cadastro.atendimento;

import gcom.cadastro.atendimento.FiltroNormaProcedimental;
import gcom.cadastro.atendimento.NormaProcedimental;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ExibirAtualizarNormaProcedimentalAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("atualizarNormaProcedimental");
		AtualizarNormaProcedimentalActionForm form = (AtualizarNormaProcedimentalActionForm) actionForm;
		Fachada fachada = Fachada.getInstancia();
		HttpSession sessao = httpServletRequest.getSession(false);

		// Verifica se veio do filtrar ou do manter
		if(httpServletRequest.getParameter("manter") != null){
			sessao.setAttribute("manter", true);
		}else if(httpServletRequest.getParameter("filtrar") != null){
			sessao.removeAttribute("manter");
		}

		String codigoNormaProcedimental = null;

		if(httpServletRequest.getParameter("codigoNormaProcedimental") == null || httpServletRequest.getParameter("codigoNormaProcedimental").equals("")){
			NormaProcedimental NormaProcedimental = (NormaProcedimental) sessao.getAttribute("objetoNormaProcedimental");
			codigoNormaProcedimental = NormaProcedimental.getId().toString();
		}else{
			codigoNormaProcedimental = (String) httpServletRequest.getParameter("codigoNormaProcedimental");
			sessao.setAttribute("codigoNormaProcedimental", codigoNormaProcedimental);
		}

		httpServletRequest.setAttribute("idNormaProcedimental", codigoNormaProcedimental);
		sessao.setAttribute("codigoNormaProcedimental", codigoNormaProcedimental);

		FiltroNormaProcedimental filtroNormaProcedimental = new FiltroNormaProcedimental();
		filtroNormaProcedimental.adicionarParametro(new ParametroSimples(FiltroNormaProcedimental.ID, codigoNormaProcedimental));

		// Pesquisando a NormaProcedimental que foi escolhida
		Collection<NormaProcedimental> colecaoNormaProcedimental = fachada.pesquisar(filtroNormaProcedimental, NormaProcedimental.class.getName());

		// N�o foi encontrado o registro
		if(colecaoNormaProcedimental == null || colecaoNormaProcedimental.isEmpty()){
			throw new ActionServletException("atencao.atualizacao.timestamp");
		}

		httpServletRequest.setAttribute("colecaoNormaProcedimental", colecaoNormaProcedimental);
		NormaProcedimental normaProcedimental = (NormaProcedimental) colecaoNormaProcedimental.iterator().next();

		// Setando valores no form para ser exibido na tela de alualizar.
		form.setCodigoNormaProcedimental(String.valueOf(normaProcedimental.getId()));
		form.setTituloNormaProcedimental(normaProcedimental.getDescricao() == null ? "" : String.valueOf(normaProcedimental.getDescricao()));
		form.setIndicadorUso(normaProcedimental.getIndicadorUso() == null ? "" : String.valueOf(normaProcedimental.getIndicadorUso()));

		if(normaProcedimental.getConteudo() != null && normaProcedimental.getConteudo().length > 0){
			httpServletRequest.setAttribute("conteudoNormaProcedimental", normaProcedimental.getConteudo());
		}else{
			httpServletRequest.setAttribute("conteudoNormaProcedimental", null);
		}

		sessao.setAttribute("NormaProcedimentalAtualizar", normaProcedimental);

		return retorno;

	}

}