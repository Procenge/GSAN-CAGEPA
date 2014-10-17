/**
 * 
 */
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

package gcom.gui.faturamento.debito;

import gcom.fachada.Fachada;
import gcom.gui.GcomAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Tela de filtrar Cancelar Debito A Cobrar Rateio Macromedidores.
 * 
 * @author Josenildo Neves
 * @date 20/08/2013
 */
public class CancelarDebitoACobrarRateioMacromedidoresAction
				extends GcomAction {

	/**
	 * [UC3103] Cancelar Débito a Cobrar de Rateio por Macromedidor
	 * 
	 * @author Josenildo Neves
	 * @date 20/08/2013
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("telaSucesso");

		FiltrarCancelarDebitoACobrarRateioMacromedidoresActionForm form = (FiltrarCancelarDebitoACobrarRateioMacromedidoresActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		// Obtém os ids de remoção
		String[] ids = form.getIdDebitoACobrar();

		// 8.3. Cancelar um ou mais débitos (botão "Cancelar Débito")
		// [SB0001 - Cancelar Débito a Cobrar Rateio].
		fachada.cancelarDebitoACobrar(ids, getUsuarioLogado(httpServletRequest), Integer.valueOf(form.getMatriculaImovel()), Boolean.TRUE);

		// Monta a página de sucesso
		if(retorno.getName().equalsIgnoreCase("telaSucesso")){
			montarPaginaSucesso(httpServletRequest, ids.length + " Débito(s) a Cobrar foi(ram) cancelado(s) com sucesso.",
							"Realizar outro Cancelamento de Débito a Cobrar",
							"exibirFiltrarCancelarDebitoACobrarRateioMacromedidoresAction.do?menu=sim");
		}

		return retorno;

	}

}
