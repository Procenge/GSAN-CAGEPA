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
 *
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
 * Classe responsável por atualizar uma NormaProcedimental.
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
			// Buscar o próximo id para cadastrar uma NormaProcedimental.
			int proximoID = fachada.pesquisarProximoIdNormaProcedimental();
			form.setCodigoNormaProcedimental(Integer.toString(proximoID));
		}

		return retorno;
	}
}
