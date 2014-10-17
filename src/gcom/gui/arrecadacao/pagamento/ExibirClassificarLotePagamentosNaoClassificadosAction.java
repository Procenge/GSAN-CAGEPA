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

package gcom.gui.arrecadacao.pagamento;

import gcom.arrecadacao.pagamento.PagamentoSituacao;
import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.Localidade;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action respons�vel por exibir a p�gina de Classificar em Lote Pagamentos N�o Classificados.
 * [UC3080] Classificar em Lote Pagamentos N�o Classificados.
 * 
 * @author Josenildo Neves
 * @date 29/11/2012
 */
public class ExibirClassificarLotePagamentosNaoClassificadosAction
				extends GcomAction {

	/**
	 * @author Josenildo Neves
	 * @date 29/11/2012
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("exibirClassificarLotePagamentosNaoClassificadosAction");

		Fachada fachada = Fachada.getInstancia();

		// Flag indicando que o usu�rio fez uma consulta a partir da tecla Enter
		String objetoConsulta = httpServletRequest.getParameter("objetoConsulta");

		// 2.2.1. Localidade Inicial:
		// Pesquisar Localidade Inicial
		if(objetoConsulta != null && !objetoConsulta.trim().equals("") && (objetoConsulta.trim().equals("1"))){

			// Faz a consulta de Localidade
			this.pesquisarLocalidadeInicial(actionForm, objetoConsulta);
		}

		// 2.2.2. Localidade Final:
		// Pesquisar Localidade Final
		if(objetoConsulta != null && !objetoConsulta.trim().equals("") && (objetoConsulta.trim().equals("2"))){

			// Faz a consulta de Localidade
			this.pesquisarLocalidadeFinal(actionForm, objetoConsulta);
		}
		
		// 2.6. Situa��o do Pagamento
		this.pesquisarSituacaoPagamento(httpServletRequest, fachada);

		// Seta os request�s encontrados
		this.setaRequest(httpServletRequest, actionForm);

		return retorno;

	}

	private void pesquisarLocalidadeInicial(ActionForm actionForm, String objetoConsulta){

		// Pega o formul�rio
		ClassificarLotePagamentosNaoClassificadosActionForm form = (ClassificarLotePagamentosNaoClassificadosActionForm) actionForm;

		Integer loca_i = form.getLocalidadeInicial();

		if(!objetoConsulta.trim().equals("1")){
			loca_i = form.getLocalidadeFinal();
		}

		FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
		filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, loca_i));

		// Recupera Localidade
		Collection colecaoLocalidade = this.getFachada().pesquisar(filtroLocalidade, Localidade.class.getName());

		if(colecaoLocalidade != null && !colecaoLocalidade.isEmpty()){

			Localidade localidade = (Localidade) Util.retonarObjetoDeColecao(colecaoLocalidade);

			if(objetoConsulta.trim().equals(ConstantesSistema.SIM.toString())){
				form.setLocalidadeInicial(localidade.getId());
				form.setNomeLocalidadeInicial(localidade.getDescricao());
			}

			form.setLocalidadeFinal(localidade.getId());
			form.setNomeLocalidadeFinal(localidade.getDescricao());

		}else{
			// [FS0003] � Verificar exist�ncia da localidade
			if(objetoConsulta.trim().equals("1")){
				form.setLocalidadeInicial(null);
				form.setNomeLocalidadeInicial("Localidade Inicial inexistente");

				form.setLocalidadeFinal(null);
				form.setNomeLocalidadeFinal(null);
			}else{
				// [FS0003] � Verificar exist�ncia da localidade
				form.setLocalidadeFinal(null);
				form.setNomeLocalidadeFinal("Localidade Final inexistente");
			}
		}
	}

	private void pesquisarLocalidadeFinal(ActionForm actionForm, String objetoConsulta){

		// Pega o formul�rio
		ClassificarLotePagamentosNaoClassificadosActionForm form = (ClassificarLotePagamentosNaoClassificadosActionForm) actionForm;

		Integer loca_f = form.getLocalidadeFinal();

		FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
		filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, loca_f));

		// Recupera Localidade
		Collection colecaoLocalidade = this.getFachada().pesquisar(filtroLocalidade, Localidade.class.getName());

		if(colecaoLocalidade != null && !colecaoLocalidade.isEmpty()){

			Localidade localidade = (Localidade) Util.retonarObjetoDeColecao(colecaoLocalidade);

			if(objetoConsulta.trim().equals("2")){
				form.setLocalidadeFinal(localidade.getId());
				form.setNomeLocalidadeFinal(localidade.getDescricao());
			}

		}else{
			// [FS0003] � Verificar exist�ncia da localidade
			form.setLocalidadeFinal(null);
			form.setNomeLocalidadeFinal("Localidade Final inexistente");
		}

	}

	private void setaRequest(HttpServletRequest httpServletRequest, ActionForm actionForm){

		// Pega o formul�rio
		ClassificarLotePagamentosNaoClassificadosActionForm form = (ClassificarLotePagamentosNaoClassificadosActionForm) actionForm;

		// Localidade Inicial
		if(form.getLocalidadeInicial() != null && !form.getLocalidadeInicial().equals("") && form.getNomeLocalidadeInicial() != null
						&& !form.getNomeLocalidadeInicial().equals("")){

			httpServletRequest.setAttribute("localidadeInicialEncontrada", "true");
			httpServletRequest.setAttribute("localidadeFinalEncontrada", "true");

		}else{

			if(form.getLocalidadeFinal() != null && !form.getLocalidadeFinal().equals("") && form.getNomeLocalidadeFinal() != null
							&& !form.getNomeLocalidadeFinal().equals("")){

				httpServletRequest.setAttribute("localidadeFinalEncontrada", "true");

			}
		}

	}

	private void pesquisarSituacaoPagamento(HttpServletRequest httpServletRequest, Fachada fachada){

		Collection<PagamentoSituacao> colecaoSituacaoPagamento = fachada.pesquisarPagamentoSituacao();
		httpServletRequest.setAttribute("colecaoSituacaoPagamento", colecaoSituacaoPagamento);
		if(colecaoSituacaoPagamento == null || colecaoSituacaoPagamento.isEmpty()){
			throw new ActionServletException("atencao.naocadastrado", null, "Pagamento Situa��o");
		}else{
			httpServletRequest.setAttribute("colecaoSituacaoPagamento", colecaoSituacaoPagamento);
		}
	}

}
