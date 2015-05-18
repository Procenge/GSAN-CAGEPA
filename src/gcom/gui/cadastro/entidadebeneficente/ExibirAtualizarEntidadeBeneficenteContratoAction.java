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

package gcom.gui.cadastro.entidadebeneficente;

import gcom.cadastro.imovel.EntidadeBeneficente;
import gcom.cadastro.imovel.EntidadeBeneficenteContrato;
import gcom.cadastro.imovel.FiltroEntidadeBeneficente;
import gcom.cadastro.imovel.FiltroEntidadeBeneficenteContrato;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ExibirAtualizarEntidadeBeneficenteContratoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("atualizarEntidadeBeneficenteContrato");
		AtualizarEntidadeBeneficenteContratoActionForm form = (AtualizarEntidadeBeneficenteContratoActionForm) actionForm;
		Fachada fachada = Fachada.getInstancia();
		HttpSession sessao = httpServletRequest.getSession(false);

		// Verifica se veio do filtrar ou do manter
		if(httpServletRequest.getParameter("manter") != null){
			sessao.setAttribute("manter", true);
		}else if(httpServletRequest.getParameter("filtrar") != null){
			sessao.removeAttribute("manter");
		}

		/*** Define filtro pra pesquisar e alimenta a colecao de entidade beneficente ***/
		FiltroEntidadeBeneficente filtroEntidadeBeneficente = new FiltroEntidadeBeneficente(FiltroEntidadeBeneficente.CLIENTE_NOME);
		filtroEntidadeBeneficente.adicionarCaminhoParaCarregamentoEntidade(FiltroEntidadeBeneficente.CLIENTE);

		Collection<EntidadeBeneficente> colecaoEntidadeBeneficente = fachada.pesquisar(filtroEntidadeBeneficente,
						EntidadeBeneficente.class.getName());

		/*** Seta colecao de entidade beneficente na sessão ***/
		sessao.setAttribute("colecaoEntidadeBeneficente", colecaoEntidadeBeneficente);

		String idEntidadeBeneficenteContrato = httpServletRequest.getParameter("idEntidadeBeneficenteContrato");

		if(idEntidadeBeneficenteContrato != null && !idEntidadeBeneficenteContrato.equals("")){
			sessao.setAttribute("idEntidadeBeneficenteContrato", idEntidadeBeneficenteContrato);
		}else{
			if(sessao.getAttribute("objetoEntidadeBeneficenteContrato") != null){
				EntidadeBeneficenteContrato entidadeBeneficenteContrato = (EntidadeBeneficenteContrato) sessao
								.getAttribute("objetoEntidadeBeneficenteContrato");
				idEntidadeBeneficenteContrato = entidadeBeneficenteContrato.getId().toString();
			}
		}

		httpServletRequest.setAttribute("idEntidadeBeneficenteContrato", idEntidadeBeneficenteContrato);
		sessao.setAttribute("idEntidadeBeneficenteContrato", idEntidadeBeneficenteContrato);

		FiltroEntidadeBeneficenteContrato filtroEntidadeBeneficenteContrato = new FiltroEntidadeBeneficenteContrato();
		filtroEntidadeBeneficenteContrato.adicionarParametro(new ParametroSimples(FiltroEntidadeBeneficenteContrato.ID,
						idEntidadeBeneficenteContrato));

		// Pesquisando a empresa que foi escolhida
		EntidadeBeneficenteContrato entidadeBeneficenteContrato = (EntidadeBeneficenteContrato) Util.retonarObjetoDeColecao(fachada
						.pesquisar(
						filtroEntidadeBeneficenteContrato, EntidadeBeneficenteContrato.class.getName()));

		// Não foi encontrado o registro
		if(entidadeBeneficenteContrato == null){
			throw new ActionServletException("atencao.atualizacao.timestamp");
		}

		httpServletRequest.setAttribute("objetoEntidadeBeneficenteContrato", entidadeBeneficenteContrato);

		// Setando valores no form para ser exibido na tela de alualizar.
		if(entidadeBeneficenteContrato.getEntidadeBeneficente() != null){
			form.setIdEntidadeBeneficente(entidadeBeneficenteContrato.getEntidadeBeneficente().getId().toString());
		}else{
			form.setIdEntidadeBeneficente("");
		}

		if(entidadeBeneficenteContrato.getNumeroContrato() != null){
			form.setNumeroContrato(entidadeBeneficenteContrato.getNumeroContrato().toString());
		}else{
			form.setNumeroContrato("");
		}

		SimpleDateFormat fmtData = new SimpleDateFormat("dd/MM/yyyy");
		if(entidadeBeneficenteContrato.getDataInicioContrato() != null){
			form.setDataInicioContrato(fmtData.format((Date) entidadeBeneficenteContrato.getDataInicioContrato()));
		}else{
			form.setDataInicioContrato("");
		}

		sessao.removeAttribute("desabilitarAtualizar");
		if(entidadeBeneficenteContrato.getDataFimContrato() != null){
			form.setDataFimContrato(fmtData.format((Date) entidadeBeneficenteContrato.getDataFimContrato()));

			sessao.setAttribute("desabilitarAtualizar", "S");
		}else{
			form.setDataFimContrato("");
		}

		if(entidadeBeneficenteContrato.getDataEncerramento() != null){
			form.setDataEncerramento(fmtData.format((Date) entidadeBeneficenteContrato.getDataEncerramento()));
		}else{
			form.setDataEncerramento("");
		}

		if(entidadeBeneficenteContrato.getPercentualRemuneracao() != null){
			form.setPercentualRemuneracao(entidadeBeneficenteContrato.getPercentualRemuneracao().toString());
		}else{
			form.setPercentualRemuneracao("");
		}

		if(entidadeBeneficenteContrato.getValorRemuneracao() != null){
			form.setValorRemuneracao(entidadeBeneficenteContrato.getValorRemuneracao().toString());
		}else{
			form.setValorRemuneracao("");
		}

		if(entidadeBeneficenteContrato.getValorMinimoDoacao() != null){
			form.setValorMinimoDoacao(entidadeBeneficenteContrato.getValorMinimoDoacao().toString());
		}else{
			form.setValorMinimoDoacao("");
		}

		if(entidadeBeneficenteContrato.getValorMaximoDoacao() != null){
			form.setValorMaximoDoacao(entidadeBeneficenteContrato.getValorMaximoDoacao().toString());
		}else{
			form.setValorMaximoDoacao("");
		}

		sessao.setAttribute("entidadeBeneficenteContratoAtualizar", entidadeBeneficenteContrato);

		return retorno;

	}



}