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

package gcom.gui.faturamento.faturamentosituacaotipo;

import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.imovel.EntidadeBeneficente;
import gcom.cadastro.imovel.FiltroEntidadeBeneficente;
import gcom.fachada.Fachada;
import gcom.faturamento.debito.DebitoTipo;
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
import org.apache.struts.action.DynaActionForm;

public class ExibirFaturamentoSituacaoTipoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("atualizarEntidadeBeneficente");
		DynaActionForm form = (DynaActionForm) actionForm;
		Fachada fachada = Fachada.getInstancia();
		HttpSession sessao = httpServletRequest.getSession(false);

		// Verifica se veio do filtrar ou do manter
		if(httpServletRequest.getParameter("manter") != null){
			sessao.setAttribute("manter", true);
		}else if(httpServletRequest.getParameter("filtrar") != null){
			sessao.removeAttribute("manter");
		}

		// Se a tecla Enter foi pressionada para pesquisar o cliente ou o tipo de débito
		if(this.pesquisaEnter(httpServletRequest, form, fachada)){
			// Se veio da tela de filtrar
			if(httpServletRequest.getParameter("filtrar") != null){
				retorno = actionMapping.findForward("filtrarEntidadeBeneficente");
			}
			return retorno;
		}

		String idEntidadeBeneficente = httpServletRequest.getParameter("idEntidadeBeneficente");

		if(idEntidadeBeneficente != null && !idEntidadeBeneficente.equals("")){
			sessao.setAttribute("idEntidadeBeneficente", idEntidadeBeneficente);

		}else{
			if(sessao.getAttribute("objetoEntidadeBeneficente") != null){
				EntidadeBeneficente entidadeBeneficente = (EntidadeBeneficente) sessao.getAttribute("objetoEntidadeBeneficente");
				idEntidadeBeneficente = entidadeBeneficente.getId().toString();
				// codigoEmpresa = (String) sessao.getAttribute("codigoEmpresa");
			}
		}

		httpServletRequest.setAttribute("idEntidadeBeneficente", idEntidadeBeneficente);
		sessao.setAttribute("idEntidadeBeneficente", idEntidadeBeneficente);

		FiltroEntidadeBeneficente filtroEntidadeBeneficente = new FiltroEntidadeBeneficente();
		filtroEntidadeBeneficente.adicionarCaminhoParaCarregamentoEntidade(FiltroEntidadeBeneficente.CLIENTE);
		filtroEntidadeBeneficente.adicionarParametro(new ParametroSimples(FiltroEntidadeBeneficente.ID, idEntidadeBeneficente));

		// Pesquisando a empresa que foi escolhida
		Collection<EntidadeBeneficente> colecaoEntidadeBeneficente = fachada.pesquisar(filtroEntidadeBeneficente,
						EntidadeBeneficente.class.getName());

		// Não foi encontrado o registro
		if(colecaoEntidadeBeneficente == null || colecaoEntidadeBeneficente.isEmpty()){
			throw new ActionServletException("atencao.atualizacao.timestamp");
		}

		httpServletRequest.setAttribute("colecaoEntidadeBeneficente", colecaoEntidadeBeneficente);
		EntidadeBeneficente entidadeBeneficente = (EntidadeBeneficente) colecaoEntidadeBeneficente.iterator().next();

		// Setando valores no form para ser exibido na tela de alualizar.
		form.set("idCliente", entidadeBeneficente.getCliente().getId().toString());
		form.set("idDebitoTipo", entidadeBeneficente.getDebitoTipo().getId().toString());
		form.set("indicadorUso", entidadeBeneficente.getIndicadorUso() + "");

		sessao.setAttribute("entidadeBeneficenteAtualizar", entidadeBeneficente);
		httpServletRequest.setAttribute("indicadorUsoAux", entidadeBeneficente.getIndicadorUso() + "");

		return retorno;

	}

	private boolean pesquisaEnter(HttpServletRequest request, DynaActionForm form, Fachada fachada){

		boolean retorno = false;
		if(request.getParameter("pesquisarClienteEnter") != null){
			String idCliente = form.get("idCliente").toString();
			if(idCliente != null && !idCliente.equals("")){
				Cliente cliente = fachada.pesquisarClienteDigitado(Integer.parseInt(idCliente));

				if(cliente != null){
					form.set("nomeCliente", cliente.getNome());
					request.setAttribute("corFonteCliente", "#000000");
				}else{
					form.set("nomeCliente", "CLIENTE INEXISTENTE");
					request.setAttribute("corFonteCliente", "#ff0000");
					// throw new ActionServletException("atencao.pesquisa.cliente.inexistente",
					// null,
					// idCliente);
				}
			}
			retorno = true;

		}else if(request.getParameter("pesquisarDebitoTipoEnter") != null){
			String idDebitoTipo = form.get("idDebitoTipo").toString();
			if(idDebitoTipo != null && !idDebitoTipo.equals("")){
				DebitoTipo debitoTipo = fachada.pesquisarTipoDebitoDigitado(Integer.parseInt(idDebitoTipo));

				if(debitoTipo != null){
					form.set("descricaoDebitoTipo", debitoTipo.getDescricao());
					request.setAttribute("corFonteCliente", "#000000");
				}else{
					form.set("descricaoDebitoTipo", "TIPO DE DEBITO INEXISTENTE");
					request.setAttribute("corFonteCliente", "#ff0000");
					// throw new ActionServletException("atencao.pesquisa.cliente.inexistente",
					// null,
					// idCliente);
				}
			}
			retorno = true;

		}

		return retorno;
	}

}