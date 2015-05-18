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
 ** GSANPCG
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

package gcom.gui.cadastro.documentoeletronico;

import gcom.cadastro.atendimento.FiltroDocumentoEletronico;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.filtro.ParametroSimples;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Classe respons�vel por atualizar uma DocumentoEletronico.
 * 
 * @author Gicevalter Couto
 * @date: 22/09/2014
 */

public class FiltrarDocumentoEletronicoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirManterDocumentoEletronico");
		HttpSession sessao = httpServletRequest.getSession(false);
		FiltrarDocumentoEletronicoActionForm filtrarDocumentoEletronicoActionForm = (FiltrarDocumentoEletronicoActionForm) actionForm;

		String idImovel = (String) filtrarDocumentoEletronicoActionForm.getIdImovel();
		String idCliente = (String) filtrarDocumentoEletronicoActionForm.getIdCliente();
		String idTipoDocumento = (String) filtrarDocumentoEletronicoActionForm.getIdTipoDocumento();

		// FiltroDocumentoEletronico filtroDocumentoEletronico = new FiltroDocumentoEletronico(FiltroDocumentoEletronico.ID);
		FiltroDocumentoEletronico filtroDocumentoEletronico = new FiltroDocumentoEletronico();
		boolean peloMenosUmParametroInformado = false;

		// -> Inserindo os par�metros informados no filtro

		if(idImovel != null && !idImovel.trim().equalsIgnoreCase("")){
			peloMenosUmParametroInformado = true;
			filtroDocumentoEletronico.adicionarParametro(new ParametroSimples(FiltroDocumentoEletronico.ID_IMOVEL, new Integer(idImovel)));
		}

		if(idCliente != null && !idCliente.trim().equalsIgnoreCase("")){
			peloMenosUmParametroInformado = true;
			filtroDocumentoEletronico
							.adicionarParametro(new ParametroSimples(FiltroDocumentoEletronico.ID_CLIENTE, new Integer(idCliente)));
		}

		if(idTipoDocumento != null && !idTipoDocumento.trim().equalsIgnoreCase("") && !idTipoDocumento.trim().equalsIgnoreCase("-1")){
			peloMenosUmParametroInformado = true;
			filtroDocumentoEletronico.adicionarParametro(new ParametroSimples(FiltroDocumentoEletronico.ATENDIMENTO_DOCUMENTO_TIPO,
							idTipoDocumento));
		}

		// Caso o usu�rio n�o tenha informado nenhum par�metro
		if(!peloMenosUmParametroInformado){
			throw new ActionServletException("atencao.filtro.nenhum_parametro_informado");
		}

		/*
		 * Verifica se o checkbox Atualizar est� marcado e em caso
		 * afirmativo manda pelo um request uma vari�vel para o
		 * ExibirManterDocumentoEletronicoAction e nele verificar se ir� para o
		 * atualizar ou para o manter
		 */
		if(filtrarDocumentoEletronicoActionForm.getCheckConsultar() != null
						&& filtrarDocumentoEletronicoActionForm.getCheckConsultar().equalsIgnoreCase("1")){
			httpServletRequest.setAttribute("consultar", filtrarDocumentoEletronicoActionForm.getCheckConsultar());

		}

		// Manda o filtro pelo request para o ExibirManterDocumentoEletronicoAction
		httpServletRequest.setAttribute("filtroDocumentoEletronico", filtroDocumentoEletronico); // ?
		sessao.setAttribute("filtroDocumentoEletronico", filtroDocumentoEletronico);

		return retorno;

	}
}
