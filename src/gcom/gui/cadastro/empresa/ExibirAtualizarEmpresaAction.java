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
 * 
 * GSANPCG
 * Virgínia Melo
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

package gcom.gui.cadastro.empresa;

import gcom.cadastro.empresa.Empresa;
import gcom.cadastro.empresa.FiltroEmpresa;
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

public class ExibirAtualizarEmpresaAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("atualizarEmpresa");
		AtualizarEmpresaActionForm form = (AtualizarEmpresaActionForm) actionForm;
		Fachada fachada = Fachada.getInstancia();
		HttpSession sessao = httpServletRequest.getSession(false);

		// Verifica se veio do filtrar ou do manter
		if(httpServletRequest.getParameter("manter") != null){
			sessao.setAttribute("manter", true);
		}else if(httpServletRequest.getParameter("filtrar") != null){
			sessao.removeAttribute("manter");
		}

		String codigoEmpresa = null;

		if(httpServletRequest.getParameter("codigoEmpresa") == null || httpServletRequest.getParameter("codigoEmpresa").equals("")){
			Empresa empresa = (Empresa) sessao.getAttribute("objetoEmpresa");
			codigoEmpresa = empresa.getId().toString();
			// codigoEmpresa = (String) sessao.getAttribute("codigoEmpresa");
		}else{
			codigoEmpresa = (String) httpServletRequest.getParameter("codigoEmpresa");
			sessao.setAttribute("codigoEmpresa", codigoEmpresa);
		}

		httpServletRequest.setAttribute("idEmpresa", codigoEmpresa);
		sessao.setAttribute("codigoEmpresa", codigoEmpresa);

		FiltroEmpresa filtroEmpresa = new FiltroEmpresa();
		filtroEmpresa.adicionarParametro(new ParametroSimples(FiltroEmpresa.ID, codigoEmpresa));

		// Pesquisando a empresa que foi escolhida
		Collection<Empresa> colecaoEmpresa = fachada.pesquisar(filtroEmpresa, Empresa.class.getName());

		// Não foi encontrado o registro
		if(colecaoEmpresa == null || colecaoEmpresa.isEmpty()){
			throw new ActionServletException("atencao.atualizacao.timestamp");
		}

		httpServletRequest.setAttribute("colecaoEmpresa", colecaoEmpresa);
		Empresa empresa = (Empresa) colecaoEmpresa.iterator().next();

		// Setando valores no form para ser exibido na tela de alualizar.
		form.setCodigoEmpresa(String.valueOf(empresa.getId()));
		form.setNomeEmpresa(empresa.getDescricao() == null ? "" : String.valueOf(empresa.getDescricao()));
		form.setEmailEmpresa(empresa.getEmail() == null ? "" : String.valueOf(empresa.getEmail()));
		form.setNomeEmpresaAbreviado(empresa.getDescricaoAbreviada() == null ? "" : String.valueOf(empresa.getDescricaoAbreviada()));
		form.setIndicadorUso(empresa.getIndicadorUso() == null ? "" : String.valueOf(empresa.getIndicadorUso()));

		if(empresa.getLogoEmpresa() != null && empresa.getLogoEmpresa().length > 0){
			httpServletRequest.setAttribute("logoEmpresa", empresa.getLogoEmpresa());
		}else{
			httpServletRequest.setAttribute("logoEmpresa", null);
		}

		sessao.setAttribute("empresaAtualizar", empresa);

		// -------------- bt DESFAZER ---------------
		/*
		 * if (httpServletRequest.getParameter("desfazer") != null
		 * && httpServletRequest.getParameter("desfazer")
		 * .equalsIgnoreCase("S")) {
		 * String codigoEmpresaAnterior = null;
		 * if (httpServletRequest.getParameter("codigoEmpresa") == null ||
		 * httpServletRequest.getParameter("codigoEmpresa").equals("")) {
		 * codigoEmpresaAnterior = (String) sessao.getAttribute("codigoEmpresa");
		 * } else {
		 * codigoEmpresaAnterior = (String) httpServletRequest.getParameter("codigoEmpresa");
		 * sessao.setAttribute("codigoEmpresa", codigoEmpresaAnterior);
		 * }
		 * if (codigoEmpresaAnterior.equalsIgnoreCase("")) {
		 * codigoEmpresaAnterior = null;
		 * }
		 * if (codigoEmpresaAnterior == null) {
		 * Empresa objEmpresa = (Empresa) sessao.getAttribute("objetoEmpresa");
		 * form.setCodigoEmpresa(objEmpresa.getId().toString());
		 * form.setNomeEmpresa(objEmpresa.getDescricao());
		 * form.setEmailEmpresa(objEmpresa.getEmail());
		 * form.setNomeEmpresaAbreviado(objEmpresa.getDescricaoAbreviada());
		 * sessao.setAttribute("empresaAtualizar", empresa);
		 * sessao.removeAttribute("empresa");
		 * }
		 * if (codigoEmpresaAnterior != null) {
		 * FiltroEmpresa filtroEmpresaAnterior = new FiltroEmpresa();
		 * filtroEmpresa.adicionarParametro(new ParametroSimples(FiltroEmpresa.ID,
		 * codigoEmpresaAnterior));
		 * Collection<Empresa> colecaoEmpresaAnterior = fachada.pesquisar(filtroEmpresaAnterior,
		 * Empresa.class.getName());
		 * if (colecaoEmpresaAnterior == null || colecaoEmpresaAnterior.isEmpty()) {
		 * throw new ActionServletException("atencao.atualizacao.timestamp");
		 * }
		 * httpServletRequest.setAttribute("colecaoEmpresa", colecaoEmpresaAnterior);
		 * Empresa empresaAnterior = (Empresa) colecaoEmpresaAnterior.iterator().next();
		 * form.setCodigoEmpresa(empresaAnterior.getId().toString());
		 * form.setNomeEmpresa(empresaAnterior.getDescricao().toString());
		 * form.setEmailEmpresa(empresaAnterior.getEmail().toString());
		 * form.setNomeEmpresaAbreviado(empresaAnterior.getDescricaoAbreviada().toString());
		 * }
		 * }
		 */
		return retorno;

	}

}