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
 *
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

package gcom.gui.cadastro.atendimento;

import gcom.cadastro.atendimento.FiltroNormaProcedimental;
import gcom.cadastro.atendimento.NormaProcedimental;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Classe respons�vel por atualizar uma NormaProcedimental.
 * 
 * @author Gicevalter Couo
 * @date: 22/09/2014
 */
public class ExibirInserirNormaProcedimentalAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("inserirNormaProcedimental");
		InserirNormaProcedimentalActionForm form = (InserirNormaProcedimentalActionForm) actionForm;
		Fachada fachada = Fachada.getInstancia();

		String idDigitadoEnterNormaProcedimental = (String) form.getCodigoNormaProcedimental();

		if(idDigitadoEnterNormaProcedimental != null && !idDigitadoEnterNormaProcedimental.trim().equals("")){
			FiltroNormaProcedimental filtroNormaProcedimental = new FiltroNormaProcedimental();
			filtroNormaProcedimental.adicionarParametro(new ParametroSimples(FiltroNormaProcedimental.ID, idDigitadoEnterNormaProcedimental));

			Collection codigoNormaProcedimentalEncontrado = fachada.pesquisar(filtroNormaProcedimental, NormaProcedimental.class.getName());

			if(codigoNormaProcedimentalEncontrado != null && !codigoNormaProcedimentalEncontrado.isEmpty()){
				form.setCodigoNormaProcedimental("");
				httpServletRequest.setAttribute("corNormaProcedimental", "exception");
				form.setMensagemNormaProcedimental(ConstantesSistema.CODIGO_NORMA_PROCEDIMENTAL_JA_CADASTRADO);
				form.setOkNormaProcedimental("false");
			}else{
				form.setOkNormaProcedimental("true");
			}
		}else{
			// Buscar o pr�ximo id para cadastrar uma NormaProcedimental.
			int proximoID = fachada.pesquisarProximoIdNormaProcedimental();
			form.setCodigoNormaProcedimental(Integer.toString(proximoID));
		}

		return retorno;
	}
}
