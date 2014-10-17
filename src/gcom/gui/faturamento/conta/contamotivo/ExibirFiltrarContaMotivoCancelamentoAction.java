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
 * Andr� Nishimura
 * Eduardo Henrique Bandeira Carneiro da Silva
 * Jos� Gilberto de Fran�a Matos
 * Lucas Daniel Medeiros
 * Saulo Vasconcelos de Lima
 * Virginia Santos de Melo
 *
 * Este programa � software livre; voc� pode redistribu�-lo e/ou
 * modific�-lo sob os termos de Licen�a P�blica Geral GNU, conforme
 * publicada pela Free Software Foundation; vers�o 2 da Licen�a.
 * Este programa � distribu�do na expectativa de ser �til, mas SEM
 * QUALQUER GARANTIA; sem mesmo a garantia impl�cita de
 * COMERCIALIZA��O ou de ADEQUA��O A QUALQUER PROP�SITO EM
 * PARTICULAR. 
 * Consulte a Licen�a P�blica Geral GNU para obter mais detalhes.
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU
 * junto com este programa; se n�o, escreva para Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 * 02111-1307, USA.
 */

package gcom.gui.faturamento.conta.contamotivo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;

public class ExibirFiltrarContaMotivoCancelamentoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Obt�m a inst�ncia da fachada
		Fachada fachada = Fachada.getInstancia();

		FiltrarContaMotivoCancelamentoActionForm filtrarContaMotivoCancelamentoActionForm = (FiltrarContaMotivoCancelamentoActionForm) actionForm;
		// C�digo para checar ou n�o o ATUALIZAR
		if(filtrarContaMotivoCancelamentoActionForm == null){
			filtrarContaMotivoCancelamentoActionForm = new FiltrarContaMotivoCancelamentoActionForm();
		}
		filtrarContaMotivoCancelamentoActionForm.setIndicadorAtualizar("1");

		if(httpServletRequest.getParameter("menu") != null){
			filtrarContaMotivoCancelamentoActionForm.setDescricaoMotivoCancelamentoConta("");
		}

		filtrarContaMotivoCancelamentoActionForm.setIndicadorAtualizar("1");

		// Mudar isso quando tiver esquema de seguran�a
		HttpSession sessao = httpServletRequest.getSession(false);

		String primeiraVez = httpServletRequest.getParameter("menu");
		if(primeiraVez != null && !primeiraVez.equals("")){
			sessao.setAttribute("indicadorAtualizar", "1");
			filtrarContaMotivoCancelamentoActionForm.setTipoPesquisa(ConstantesSistema.TIPO_PESQUISA_INICIAL.toString());
		}
		// Se voltou da tela de Atualizar Sistema de Esgoto
		if(sessao.getAttribute("voltar") != null && sessao.getAttribute("voltar").equals("filtrar")){
			sessao.setAttribute("indicadorAtualizar", "1");
			if(sessao.getAttribute("tipoPesquisa") != null){
				filtrarContaMotivoCancelamentoActionForm.setTipoPesquisa(sessao.getAttribute("tipoPesquisa").toString());
			}
		}

		sessao.setAttribute("filtrarContaMotivoCancelamentoActionForm", filtrarContaMotivoCancelamentoActionForm);

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("exibirFiltrarContaMotivoCancelamento");
		return retorno;
	}
}
