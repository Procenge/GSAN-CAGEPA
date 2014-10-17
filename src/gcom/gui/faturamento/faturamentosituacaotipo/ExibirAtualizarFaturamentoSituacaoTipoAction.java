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
import gcom.fachada.Fachada;
import gcom.faturamento.FaturamentoSituacaoTipo;
import gcom.faturamento.FiltroFaturamentoSituacaoTipo;
import gcom.faturamento.debito.DebitoTipo;
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

public class ExibirAtualizarFaturamentoSituacaoTipoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("atualizarFaturamentoSituacaoTipo");
		DynaActionForm form = (DynaActionForm) actionForm;
		Fachada fachada = Fachada.getInstancia();
		HttpSession sessao = httpServletRequest.getSession(false);

		// Verifica se veio do filtrar ou do manter
		if(httpServletRequest.getParameter("manter") != null){
			sessao.setAttribute("manter", true);
		}else if(httpServletRequest.getParameter("filtrar") != null){
			sessao.removeAttribute("manter");
		}

		// Se a tecla Enter foi pressionada para pesquisar
		if(this.pesquisaEnter(httpServletRequest, form, fachada)){
			// Se veio da tela de filtrar
			if(httpServletRequest.getParameter("filtrar") != null){
				retorno = actionMapping.findForward("filtrarFaturamentoSituacaoTipo");
			}
			return retorno;
		}

		String idFaturamentoSituacaoTipo = httpServletRequest.getParameter("idFaturamentoSituacaoTipo");

		if(idFaturamentoSituacaoTipo != null && !idFaturamentoSituacaoTipo.equals("")){
			sessao.setAttribute("idFaturamentoSituacaoTipo", idFaturamentoSituacaoTipo);

		}else{
			if(sessao.getAttribute("objetoFaturamentoSituacaoTipo") != null){
				FaturamentoSituacaoTipo faturamentoSituacaoTipo = (FaturamentoSituacaoTipo) sessao
								.getAttribute("objetoFaturamentoSituacaoTipo");
				idFaturamentoSituacaoTipo = faturamentoSituacaoTipo.getId().toString();
				// codigoEmpresa = (String) sessao.getAttribute("codigoEmpresa");
			}
		}

		httpServletRequest.setAttribute("idFaturamentoSituacaoTipo", idFaturamentoSituacaoTipo);
		sessao.setAttribute("idFaturamentoSituacaoTipo", idFaturamentoSituacaoTipo);

		FiltroFaturamentoSituacaoTipo filtroFaturamentoSituacaoTipo = new FiltroFaturamentoSituacaoTipo();
		filtroFaturamentoSituacaoTipo.adicionarCaminhoParaCarregamentoEntidade(FiltroFaturamentoSituacaoTipo.LEITURA_ANORMALIDADE_CONSUMOCOM_LEITURA);
		filtroFaturamentoSituacaoTipo.adicionarCaminhoParaCarregamentoEntidade(FiltroFaturamentoSituacaoTipo.LEITURA_ANORMALIDADE_CONSUMOSEM_LEITURA);
		filtroFaturamentoSituacaoTipo.adicionarCaminhoParaCarregamentoEntidade(FiltroFaturamentoSituacaoTipo.LEITURA_ANORMALIDADE_LEITURACOM_LEITURA);
		filtroFaturamentoSituacaoTipo.adicionarCaminhoParaCarregamentoEntidade(FiltroFaturamentoSituacaoTipo.LEITURA_ANORMALIDADE_LEITURASEM_LEITURA);
		filtroFaturamentoSituacaoTipo.adicionarParametro(new ParametroSimples(FiltroFaturamentoSituacaoTipo.ID, idFaturamentoSituacaoTipo));

		// Pesquisando a empresa que foi escolhida
		Collection<FaturamentoSituacaoTipo> colecaoFaturamentoSituacaoTipo = fachada.pesquisar(filtroFaturamentoSituacaoTipo,
						FaturamentoSituacaoTipo.class.getName());

		// Não foi encontrado o registro
		if(colecaoFaturamentoSituacaoTipo == null || colecaoFaturamentoSituacaoTipo.isEmpty()){
			throw new ActionServletException("atencao.atualizacao.timestamp");
		}

		httpServletRequest.setAttribute("colecaoFaturamentoSituacaoTipo", colecaoFaturamentoSituacaoTipo);
		FaturamentoSituacaoTipo faturamentoSituacaoTipo = (FaturamentoSituacaoTipo) Util.retonarObjetoDeColecao(colecaoFaturamentoSituacaoTipo);

		// Setando valores no form para ser exibido na tela de alualizar.
		form.set("idFaturamentoSituacaoTipo", faturamentoSituacaoTipo.getId().toString());
		form.set("descricao", faturamentoSituacaoTipo.getDescricao());
		form.set("indicadorParalisacaoFaturamento", faturamentoSituacaoTipo.getIndicadorParalisacaoFaturamento().toString());
		form.set("indicadorParalisacaoLeitura", faturamentoSituacaoTipo.getIndicadorParalisacaoLeitura().toString());
		form.set("indicadorUso", faturamentoSituacaoTipo.getIndicadorUso().toString());
		form.set("indicadorValidoAgua", faturamentoSituacaoTipo.getIndicadorValidoAgua().toString());
		form.set("indicadorValidoEsgoto", faturamentoSituacaoTipo.getIndicadorValidoEsgoto().toString());
		form.set("indicadorPercentualEsgoto", faturamentoSituacaoTipo.getIndicadorPercentualEsgoto().toString());
		form.set("indicadorFaturamentoParalisacaoEsgoto", faturamentoSituacaoTipo.getIndicadorFaturamentoParalisacaoEsgoto().toString());
		form.set("leituraAnormalidadeLeituraComLeitura", faturamentoSituacaoTipo.getLeituraAnormalidadeLeituraComLeitura().getId().toString());
		form.set("leituraAnormalidadeLeituraSemLeitura", faturamentoSituacaoTipo.getLeituraAnormalidadeLeituraSemLeitura().getId().toString());
		form.set("leituraAnormalidadeConsumoComLeitura", faturamentoSituacaoTipo.getLeituraAnormalidadeConsumoComLeitura().getId().toString());
		form.set("leituraAnormalidadeConsumoSemLeitura", faturamentoSituacaoTipo.getLeituraAnormalidadeConsumoSemLeitura().getId().toString());
		

		sessao.setAttribute("faturamentoSituacaoTipoAtualizar", faturamentoSituacaoTipo);
		httpServletRequest.setAttribute("indicadorUsoAux", faturamentoSituacaoTipo.getIndicadorUso() + "");

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