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
 ** GSANPCG
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
 * Classe responsável por atualizar uma DocumentoEletronico.
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

		// -> Inserindo os parâmetros informados no filtro

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

		// Caso o usuário não tenha informado nenhum parâmetro
		if(!peloMenosUmParametroInformado){
			throw new ActionServletException("atencao.filtro.nenhum_parametro_informado");
		}

		/*
		 * Verifica se o checkbox Atualizar está marcado e em caso
		 * afirmativo manda pelo um request uma variável para o
		 * ExibirManterDocumentoEletronicoAction e nele verificar se irá para o
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
