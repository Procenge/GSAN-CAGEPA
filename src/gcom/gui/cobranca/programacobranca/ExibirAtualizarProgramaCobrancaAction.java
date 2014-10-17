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
import gcom.cobranca.programacobranca.FiltroProgramaCobranca;
import gcom.cobranca.programacobranca.ProgramaCobranca;
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
 * Action responsável por exibir a página de atualização de Programa de Critério.
 * 
 * @author Virgínia Melo
 * @date 27/08/2008
 */
public class ExibirAtualizarProgramaCobrancaAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("atualizarProgramaCobranca");
		AtualizarProgramaCobrancaActionForm form = (AtualizarProgramaCobrancaActionForm) actionForm;
		Fachada fachada = Fachada.getInstancia();
		HttpSession sessao = httpServletRequest.getSession(false);

		// Verifica se veio do filtrar ou do manter
		if(httpServletRequest.getParameter("manter") != null){
			sessao.setAttribute("manter", true);
		}else if(httpServletRequest.getParameter("filtrar") != null){
			sessao.removeAttribute("manter");
		}

		String codigoPrograma = null;

		if(httpServletRequest.getParameter("codigoPrograma") == null || httpServletRequest.getParameter("codigoPrograma").equals("")){
			ProgramaCobranca programa = (ProgramaCobranca) sessao.getAttribute("objetoPrograma");
			codigoPrograma = programa.getId().toString();
		}else{
			codigoPrograma = (String) httpServletRequest.getParameter("codigoPrograma");
			sessao.setAttribute("codigoPrograma", codigoPrograma);
		}

		httpServletRequest.setAttribute("idPrograma", codigoPrograma);
		sessao.setAttribute("codigoPrograma", codigoPrograma);

		// IDCriterio digitado
		String idCriterio = (String) form.getIdCriterio();

		// Verifica se idCriterio foi informado
		if(idCriterio != null && !idCriterio.trim().equals("") && Integer.parseInt(idCriterio) > 0){
			FiltroCobrancaCriterio filtroCriterio = new FiltroCobrancaCriterio();
			filtroCriterio.adicionarParametro(new ParametroSimples(FiltroCobrancaCriterio.ID, idCriterio));
			filtroCriterio.adicionarParametro(new ParametroSimples(FiltroCobrancaCriterio.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			Collection criterioEncontrado = fachada.pesquisar(filtroCriterio, CobrancaCriterio.class.getName());

			if(criterioEncontrado != null && !criterioEncontrado.isEmpty()){

				// O critério foi encontrado
				form.setIdCriterio(((CobrancaCriterio) ((List) criterioEncontrado).get(0)).getId().toString());
				form.setDescricaoCriterio(((CobrancaCriterio) ((List) criterioEncontrado).get(0)).getDescricaoCobrancaCriterio());

			}else{

				// O critério não foi encontrado
				form.setIdCriterio("");
				httpServletRequest.setAttribute("idCriterioNaoEncontrado", "exception");
				form.setDescricaoCriterio("Critério cobrança inexistente");
				httpServletRequest.setAttribute("nomeCampo", "idCriterio");
			}
		}

		if(httpServletRequest.getParameter("enter") == null){

			FiltroProgramaCobranca filtro = new FiltroProgramaCobranca();
			filtro.adicionarCaminhoParaCarregamentoEntidade("criterio");
			filtro.adicionarParametro(new ParametroSimples(FiltroProgramaCobranca.ID, codigoPrograma));

			// Pesquisando o programa que foi escolhido
			Collection<ProgramaCobranca> colecaoPrograma = fachada.pesquisar(filtro, ProgramaCobranca.class.getName());

			// Não foi encontrado o registro
			if(colecaoPrograma == null || colecaoPrograma.isEmpty()){
				throw new ActionServletException("atencao.atualizacao.timestamp");
			}

			httpServletRequest.setAttribute("colecaoPrograma", colecaoPrograma);
			ProgramaCobranca programa = (ProgramaCobranca) colecaoPrograma.iterator().next();

			// Setando valores no form para ser exibido na tela de alualizar.
			form.setCodigoProgramaCobranca(programa.getId().toString());
			form.setNomeProgramaCobranca(programa.getNome().toString());
			form.setDescricaoProgramaCobranca(programa.getDescricao());
			form.setIdCriterio(programa.getCriterio().getId().toString());
			form.setDescricaoCriterio(programa.getCriterio().getDescricaoCobrancaCriterio());

			sessao.setAttribute("programaAtualizar", programa);
		}

		return retorno;

	}

}