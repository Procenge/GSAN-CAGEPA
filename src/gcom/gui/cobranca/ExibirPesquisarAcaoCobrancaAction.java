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

package gcom.gui.cobranca;

import gcom.atendimentopublico.ordemservico.FiltroServicoTipo;
import gcom.atendimentopublico.ordemservico.ServicoTipo;
import gcom.cobranca.CobrancaEstagio;
import gcom.cobranca.DocumentoTipo;
import gcom.cobranca.FiltroCobrancaEstagio;
import gcom.cobranca.FiltroDocumentoTipo;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
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
public class ExibirPesquisarAcaoCobrancaAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("exibirPesquisarAcaoCobrancaAction");

		// Mudar isso quando tiver esquema de seguran�a
		HttpSession sessao = httpServletRequest.getSession(false);

		Fachada fachada = Fachada.getInstancia();

		PesquisarAcaoCobrancaActionForm form = (PesquisarAcaoCobrancaActionForm) actionForm;
		String limpaForm = httpServletRequest.getParameter("limpaForm");

		if(limpaForm != null){
			form.setDescricaoAcao("");
			form.setIdCobrancaEstagio("");
			form.setIdTipoDocumentoGerado("");
			form.setIdServicoTipo("");
			form.setDescricaoServicoTipo("");
		}

		// pesquisa os tipos de documentos
		FiltroDocumentoTipo filtroDocumentoTipo = new FiltroDocumentoTipo();
		filtroDocumentoTipo.setCampoOrderBy(FiltroDocumentoTipo.DESCRICAO);
		filtroDocumentoTipo.adicionarParametro(new ParametroSimples(FiltroDocumentoTipo.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));

		Collection colecaoDocumentoTipo = fachada.pesquisar(filtroDocumentoTipo, DocumentoTipo.class.getName());
		if(colecaoDocumentoTipo == null || colecaoDocumentoTipo.isEmpty()){
			throw new ActionServletException("atencao.pesquisa_inexistente", null, "Documento Tipo");
		}

		sessao.setAttribute("colecaoDocumentoTipo", colecaoDocumentoTipo);

		// pesquisa os Est�gios de Cobran�a
		FiltroCobrancaEstagio filtroCobrancaEstagio = new FiltroCobrancaEstagio();
		filtroCobrancaEstagio.setCampoOrderBy(FiltroCobrancaEstagio.DESCRICAO);
		filtroCobrancaEstagio.adicionarParametro(new ParametroSimples(FiltroCobrancaEstagio.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));

		Collection colecaoCobrancaEstagio = fachada.pesquisar(filtroCobrancaEstagio, CobrancaEstagio.class.getName());
		if(colecaoCobrancaEstagio == null || colecaoCobrancaEstagio.isEmpty()){
			throw new ActionServletException("atencao.pesquisa_inexistente", null, "Cobran�a Est�gio");
		}

		sessao.setAttribute("colecaoCobrancaEstagio", colecaoCobrancaEstagio);

		// verificar se Crit�rio de Cobran�a foi digitado.
		String idTipoServico = form.getIdServicoTipo();

		if(idTipoServico != null && !idTipoServico.trim().equalsIgnoreCase("")){

			FiltroServicoTipo filtro = new FiltroServicoTipo();
			filtro.adicionarParametro(new ParametroSimples(FiltroServicoTipo.ID, idTipoServico));
			filtro.adicionarParametro(new ParametroSimples(FiltroServicoTipo.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));

			// Retorna o Servi�o Tipo
			Collection colecaoPesquisa = fachada.pesquisar(filtro, ServicoTipo.class.getName());

			if(colecaoPesquisa == null || colecaoPesquisa.isEmpty()){
				form.setIdServicoTipo("");
				httpServletRequest.setAttribute("idServicoTipoNaoEncontrado", "exception");
				form.setDescricaoServicoTipo("Tipo de Servi�o inexistente");
				httpServletRequest.setAttribute("nomeCampo", "idServicoTipo");
			}else{
				// Tipo Servi�o encontrado
				form.setIdServicoTipo("" + ((ServicoTipo) ((List) colecaoPesquisa).get(0)).getId());
				form.setDescricaoServicoTipo(((ServicoTipo) ((List) colecaoPesquisa).get(0)).getDescricao());
			}
		}

		if(httpServletRequest.getParameter("tipoConsulta") != null && !httpServletRequest.getParameter("tipoConsulta").equals("")){

			if(httpServletRequest.getParameter("tipoConsulta") != null){
				if(httpServletRequest.getParameter("tipoConsulta").equals("tipoServico")){
					form.setIdServicoTipo(httpServletRequest.getParameter("idCampoEnviarDados"));
					form.setDescricaoServicoTipo(httpServletRequest.getParameter("descricaoCampoEnviarDados"));
					httpServletRequest.setAttribute("idServicoEncontrado", "true");

				}
			}
		}else{
			if(httpServletRequest.getParameter("objetoConsulta") == null || httpServletRequest.getParameter("objetoConsulta").equals("")){

				form.setDescricaoAcao("");
				form.setIdCobrancaEstagio("");
				form.setIdTipoDocumentoGerado("");
				form.setIdServicoTipo("");
				form.setDescricaoServicoTipo("");
			}
		}

		if(httpServletRequest.getParameter("popup") != null){
			sessao.setAttribute("popup", httpServletRequest.getParameter("popup"));
		}

		if(httpServletRequest.getParameter("caminhoRetornoTelaPesquisaAcaoCobranca") != null){
			sessao.setAttribute("caminhoRetornoTelaPesquisaAcaoCobranca", httpServletRequest
							.getParameter("caminhoRetornoTelaPesquisaAcaoCobranca"));

		}

		// recupera no ExibirAdicionar...Action
		if(httpServletRequest.getParameter("idAcao") != null){
			sessao.setAttribute("idAcao", httpServletRequest.getParameter("idAcao"));
		}

		return retorno;

	}
}
