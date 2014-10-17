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

package gcom.gui.cadastro.empresa;

import gcom.cadastro.empresa.Empresa;
import gcom.cadastro.empresa.FiltroEmpresa;
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
 * Action responsável pela pre-exibição da página de Inserir Empresa
 * 
 * @author Virginia Melo
 * @created 01 de Agosto de 2008
 */
public class ExibirInserirEmpresaAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("inserirEmpresa");
		InserirEmpresaActionForm form = (InserirEmpresaActionForm) actionForm;
		Fachada fachada = Fachada.getInstancia();

		String idDigitadoEnterEmpresa = (String) form.getCodigoEmpresa();

		if(idDigitadoEnterEmpresa != null && !idDigitadoEnterEmpresa.trim().equals("")){
			FiltroEmpresa filtroEmpresa = new FiltroEmpresa();
			filtroEmpresa.adicionarParametro(new ParametroSimples(FiltroEmpresa.ID, idDigitadoEnterEmpresa));

			Collection codigoEmpresaEncontrado = fachada.pesquisar(filtroEmpresa, Empresa.class.getName());

			if(codigoEmpresaEncontrado != null && !codigoEmpresaEncontrado.isEmpty()){
				form.setCodigoEmpresa("");
				httpServletRequest.setAttribute("corEmpresa", "exception");
				form.setMensagemEmpresa(ConstantesSistema.CODIGO_EMPRESA_JA_CADASTRADO);
				form.setOkEmpresa("false");
			}else{
				form.setOkEmpresa("true");
			}
		}else{
			// Buscar o próximo id para cadastrar uma empresa.
			int proximoID = fachada.pesquisarProximoIdEmpresa();
			form.setCodigoEmpresa(Integer.toString(proximoID));
		}

		return retorno;
	}
}
