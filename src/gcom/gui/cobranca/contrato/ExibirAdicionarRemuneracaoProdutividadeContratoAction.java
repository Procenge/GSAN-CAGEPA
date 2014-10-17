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

package gcom.gui.cobranca.contrato;

import gcom.atendimentopublico.ordemservico.ServicoTipo;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.util.Util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ExibirAdicionarRemuneracaoProdutividadeContratoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		AdicionarRemuneracaoProdutividadeContratoActionForm form = (AdicionarRemuneracaoProdutividadeContratoActionForm) actionForm;
		String servicoTipo = (String) httpServletRequest.getParameter("idCampoEnviarDados");
		String descricao = (String) httpServletRequest.getParameter("descricaoCampoEnviarDados");
		Fachada fachada = Fachada.getInstancia();
		if(servicoTipo != null && !servicoTipo.equals("")){
			form.setServicoTipo(servicoTipo);
			form.setDescricaoServicoTipo(descricao);
		}else{
			// pesquisa do enter Serviço Tipo Referência
			String idServicoTipoReferencia = form.getServicoTipo();
			if(idServicoTipoReferencia != null && !idServicoTipoReferencia.equals("")){
				Integer idTipoServicoReferenciaInteger = Util.converterStringParaInteger(form.getServicoTipo());

				ServicoTipo st = fachada.pesquisarSevicoTipo(idTipoServicoReferenciaInteger);
				if(st != null && !st.equals("")){
					form.setServicoTipo(st.getId().toString());
					form.setDescricaoServicoTipo(st.getDescricao());
				}else{
					form.setServicoTipo("");
					form.setDescricaoServicoTipo("");
					httpServletRequest.setAttribute("nomeCampo", "idServicoTipoReferencia");
					httpServletRequest.setAttribute("idServicoTipoReferenciaNaoEncontrado", "exception");
					form.setDescricaoServicoTipo("Serviço Tipo Referência Inexistente");
				}
			}
		}
		if(form.getServicoTipo() != null && !form.getServicoTipo().equals("")){
			httpServletRequest.setAttribute("servicoTipoEncontrado", "true");
		}
		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("exibirAdicionarRemuneracaoProdutividadeContrato");

		return retorno;
	}

}
