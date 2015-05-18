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

package gcom.gui.faturamento.faturamentogrupo;

import gcom.fachada.Fachada;
import gcom.faturamento.FaturamentoGrupo;
import gcom.faturamento.FiltroFaturamentoGrupo;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

public class ExibirAtualizarFaturamentoGrupoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("atualizarFaturamentoGrupo");
		DynaActionForm form = (DynaActionForm) actionForm;
		Fachada fachada = Fachada.getInstancia();
		HttpSession sessao = httpServletRequest.getSession(false);

		// Verifica se veio do filtrar ou do manter
		if(httpServletRequest.getParameter("manter") != null){
			sessao.setAttribute("manter", true);
		}else if(httpServletRequest.getParameter("filtrar") != null){
			sessao.removeAttribute("manter");
		}

		String idFaturamentoGrupo = httpServletRequest.getParameter("idFaturamentoGrupo");

		if(idFaturamentoGrupo != null && !idFaturamentoGrupo.equals("")){
			sessao.setAttribute("idFaturamentoGrupo", idFaturamentoGrupo);

		}else{
			if(sessao.getAttribute("objetoFaturamentoGrupo") != null){
				FaturamentoGrupo faturamentoGrupo = (FaturamentoGrupo) sessao
								.getAttribute("objetoFaturamentoGrupo");
				idFaturamentoGrupo = faturamentoGrupo.getId().toString();
				// codigoEmpresa = (String) sessao.getAttribute("codigoEmpresa");
			}
		}

		httpServletRequest.setAttribute("idFaturamentoGrupo", idFaturamentoGrupo);
		sessao.setAttribute("idFaturamentoGrupo", idFaturamentoGrupo);

		FiltroFaturamentoGrupo filtroFaturamentoGrupo = new FiltroFaturamentoGrupo();
		filtroFaturamentoGrupo.adicionarParametro(new ParametroSimples(FiltroFaturamentoGrupo.ID, idFaturamentoGrupo));

		// Pesquisando a empresa que foi escolhida
		Collection<FaturamentoGrupo> colecaoFaturamentoGrupo = fachada.pesquisar(filtroFaturamentoGrupo,
						FaturamentoGrupo.class.getName());

		// Não foi encontrado o registro
		if(colecaoFaturamentoGrupo == null || colecaoFaturamentoGrupo.isEmpty()){
			throw new ActionServletException("atencao.atualizacao.timestamp");
		}

		httpServletRequest.setAttribute("colecaoFaturamentoGrupo", colecaoFaturamentoGrupo);
		FaturamentoGrupo faturamentoGrupo = (FaturamentoGrupo) Util.retonarObjetoDeColecao(colecaoFaturamentoGrupo);

		// Setando valores no form para ser exibido na tela de alualizar.
		form.set("idFaturamentoGrupo", faturamentoGrupo.getId().toString());
		form.set("descricao", faturamentoGrupo.getDescricao());
		form.set("descricaoAbreviada", faturamentoGrupo.getDescricaoAbreviada());
		form.set("anoMesReferencia", faturamentoGrupo.getMesAno());
		form.set("indicadorUso", faturamentoGrupo.getIndicadorUso().toString());
		form.set("diaVencimento", faturamentoGrupo.getDiaVencimento().toString());
		form.set("indicadorVencimentoMesFatura", faturamentoGrupo.getIndicadorVencimentoMesFatura().toString());

		if(faturamentoGrupo.getDiaVencimentoDebitoAutomatico() != null
						&& faturamentoGrupo.getDiaVencimentoDebitoAutomatico().intValue() > 0){
			form.set("diaVencimentoDebitoAutomatico", faturamentoGrupo.getDiaVencimentoDebitoAutomatico().toString());
		}else{
			form.set("diaVencimentoDebitoAutomatico", null);
		}
		if(faturamentoGrupo.getDiaVencimentoEntregaAlternativa() != null
						&& faturamentoGrupo.getDiaVencimentoEntregaAlternativa().intValue() > 0){
			form.set("diaVencimentoEntregaAlternativa", faturamentoGrupo.getDiaVencimentoEntregaAlternativa().toString());
		}else{
			form.set("diaVencimentoEntregaAlternativa", null);
		}



		sessao.setAttribute("faturamentoGrupoAtualizar", faturamentoGrupo);
		httpServletRequest.setAttribute("indicadorUsoAux", faturamentoGrupo.getIndicadorUso() + "");

		return retorno;

	}

}