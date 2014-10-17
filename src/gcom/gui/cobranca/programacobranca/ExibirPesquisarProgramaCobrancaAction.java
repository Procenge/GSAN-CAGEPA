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
 * Action responsável pela exibição da tela de pesquisa de Programa de Cobrança.
 * 
 * @author Virgínia Melo
 */
public class ExibirPesquisarProgramaCobrancaAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("exibirPesquisarProgramaCobrancaAction");

		// Mudar isso quando tiver esquema de segurança
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

			// Se não tiver setado nada em Tipo Pesquisa, será marcado o "Iniciando pelo texto"
			if(form.getTipoPesquisa() == null || form.getTipoPesquisa().equals("")){
				form.setTipoPesquisa(ConstantesSistema.TIPO_PESQUISA_INICIAL.toString());
			}

			// Se não tiver setado nada em Tipo Pesquisa Descricao, será marcado o
			// "Iniciando pelo texto"
			if(form.getTipoPesquisaDescricao() == null || form.getTipoPesquisaDescricao().equals("")){
				form.setTipoPesquisaDescricao(ConstantesSistema.TIPO_PESQUISA_INICIAL.toString());
			}

		}

		// verificar se Critério de Cobrança foi digitado.
		String idCriterio = form.getIdCriterio();

		if(idCriterio != null && !idCriterio.trim().equalsIgnoreCase("")){

			FiltroCobrancaCriterio filtroCriterio = new FiltroCobrancaCriterio();
			filtroCriterio.adicionarParametro(new ParametroSimples(FiltroCobrancaCriterio.ID, idCriterio));
			filtroCriterio.adicionarParametro(new ParametroSimples(FiltroCobrancaCriterio.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			// Retorna o Critério de Cobrança
			Collection colecaoPesquisa = fachada.pesquisar(filtroCriterio, CobrancaCriterio.class.getName());

			if(colecaoPesquisa == null || colecaoPesquisa.isEmpty()){
				// throw new ActionServletException("atencao.pesquisa.criterio_inexistente");
				form.setIdCriterio("");
				httpServletRequest.setAttribute("idCriterioNaoEncontrado", "exception");
				form.setDescricaoCriterio("Critério cobrança inexistente");
				httpServletRequest.setAttribute("nomeCampo", "idCriterio");
			}else{
				// Critério encontrado
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
