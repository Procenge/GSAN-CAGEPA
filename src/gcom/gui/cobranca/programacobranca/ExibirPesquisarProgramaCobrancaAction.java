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

package gcom.gui.cobranca.programacobranca;

import gcom.cobranca.CobrancaCriterio;
import gcom.cobranca.FiltroCobrancaCriterio;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action respons�vel pela exibi��o da tela de pesquisa de Programa de Cobran�a.
 * 
 * @author Virg�nia Melo
 */
public class ExibirPesquisarProgramaCobrancaAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("exibirPesquisarProgramaCobrancaAction");

		// Mudar isso quando tiver esquema de seguran�a
		HttpSession sessao = httpServletRequest.getSession(false);

		Fachada fachada = Fachada.getInstancia();

		PesquisarProgramaCobrancaActionForm form = (PesquisarProgramaCobrancaActionForm) actionForm;

		String limpaForm = httpServletRequest.getParameter("limpaForm");
		// String menu = httpServletRequest.getParameter("menu");

		if(limpaForm != null){

			form.setNomeProgramaCobranca("");
			form.setDescricaoProgramaCobranca("");
			form.setIdCriterio("");
			form.setDescricaoCriterio("");
			form.setDataCriacaoInicial("");
			form.setDataCriacaoFinal("");
			form.setDataInicioInicial("");
			form.setDataInicioFinal("");
			form.setDataUltimoMovimentoInicial("");
			form.setDataUltimoMovimentoFinal("");

			// Se n�o tiver setado nada em Tipo Pesquisa, ser� marcado o "Iniciando pelo texto"
			if(form.getTipoPesquisa() == null || form.getTipoPesquisa().equals("")){
				form.setTipoPesquisa(ConstantesSistema.TIPO_PESQUISA_INICIAL.toString());
			}

			// Se n�o tiver setado nada em Tipo Pesquisa Descricao, ser� marcado o
			// "Iniciando pelo texto"
			if(form.getTipoPesquisaDescricao() == null || form.getTipoPesquisaDescricao().equals("")){
				form.setTipoPesquisaDescricao(ConstantesSistema.TIPO_PESQUISA_INICIAL.toString());
			}

		}

		// verificar se Crit�rio de Cobran�a foi digitado.
		String idCriterio = form.getIdCriterio();

		if(idCriterio != null && !idCriterio.trim().equalsIgnoreCase("")){

			FiltroCobrancaCriterio filtroCriterio = new FiltroCobrancaCriterio();
			filtroCriterio.adicionarParametro(new ParametroSimples(FiltroCobrancaCriterio.ID, idCriterio));
			filtroCriterio.adicionarParametro(new ParametroSimples(FiltroCobrancaCriterio.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			// Retorna o Crit�rio de Cobran�a
			Collection colecaoPesquisa = fachada.pesquisar(filtroCriterio, CobrancaCriterio.class.getName());

			if(colecaoPesquisa == null || colecaoPesquisa.isEmpty()){
				// throw new ActionServletException("atencao.pesquisa.criterio_inexistente");
				form.setIdCriterio("");
				httpServletRequest.setAttribute("idCriterioNaoEncontrado", "exception");
				form.setDescricaoCriterio("Crit�rio cobran�a inexistente");
				httpServletRequest.setAttribute("nomeCampo", "idCriterio");
			}else{
				// Crit�rio encontrado
				form.setIdCriterio("" + ((CobrancaCriterio) ((List) colecaoPesquisa).get(0)).getId());
				form.setDescricaoCriterio(((CobrancaCriterio) ((List) colecaoPesquisa).get(0)).getDescricaoCobrancaCriterio());
			}
		}

		if(httpServletRequest.getParameter("tipoConsulta") != null && !httpServletRequest.getParameter("tipoConsulta").equals("")){

			if(httpServletRequest.getParameter("tipoConsulta") != null){
				if(httpServletRequest.getParameter("tipoConsulta").equals("criterioCobranca")){
					form.setIdCriterio(httpServletRequest.getParameter("idCampoEnviarDados"));
					form.setDescricaoCriterio(httpServletRequest.getParameter("descricaoCampoEnviarDados"));
					httpServletRequest.setAttribute("idClienteEncontrado", "true");

				}
			}
		}else{
			if(httpServletRequest.getParameter("objetoConsulta") == null || httpServletRequest.getParameter("objetoConsulta").equals("")){

				form.setNomeProgramaCobranca("");
				form.setDescricaoProgramaCobranca("");
				form.setIdCriterio("");
				form.setDescricaoCriterio("");
				form.setDataCriacaoInicial("");
				form.setDataCriacaoFinal("");
				form.setDataInicioInicial("");
				form.setDataInicioFinal("");
				form.setDataUltimoMovimentoInicial("");
				form.setDataUltimoMovimentoFinal("");
				sessao.removeAttribute("caminhoRetornoTelaPesquisa");
				sessao.removeAttribute("caminhoRetornoTelaPesquisaProgramaCobranca");
			}
		}

		if(httpServletRequest.getParameter("popup") != null){
			sessao.setAttribute("popup", httpServletRequest.getParameter("popup"));
		}

		if(httpServletRequest.getParameter("caminhoRetornoTelaPesquisaProgramaCobranca") != null){
			sessao.setAttribute("caminhoRetornoTelaPesquisaProgramaCobranca", httpServletRequest
							.getParameter("caminhoRetornoTelaPesquisaProgramaCobranca"));

		}

		// recupera no ExibirAdicionar...Action
		if(httpServletRequest.getParameter("idPrograma") != null){
			sessao.setAttribute("idPrograma", httpServletRequest.getParameter("idPrograma"));
		}

		return retorno;

	}
}
