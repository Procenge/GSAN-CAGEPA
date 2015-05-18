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

package gcom.gui.cadastro.atendimento;

import gcom.cadastro.atendimento.FiltroNormaProcedimental;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.filtro.ComparacaoTexto;
import gcom.util.filtro.ComparacaoTextoCompleto;
import gcom.util.filtro.ParametroSimples;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Classe respons�vel por atualizar uma NormaProcedimental.
 * 
 * @author Gicevalter Couto
 * @date: 22/09/2014
 */

public class FiltrarNormaProcedimentalAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirManterNormaProcedimental");
		HttpSession sessao = httpServletRequest.getSession(false);
		FiltrarNormaProcedimentalActionForm filtrarNormaProcedimentalActionForm = (FiltrarNormaProcedimentalActionForm) actionForm;

		String codigoNormaProcedimental = (String) filtrarNormaProcedimentalActionForm.getCodigoNormaProcedimental();
		String nomeNormaProcedimental = (String) filtrarNormaProcedimentalActionForm.getTituloNormaProcedimental();
		String indicadorUso = (String) filtrarNormaProcedimentalActionForm.getIndicadorUso();
		String tipoPesquisa = (String) filtrarNormaProcedimentalActionForm.getTipoPesquisa();

		// FiltroNormaProcedimental filtroNormaProcedimental = new FiltroNormaProcedimental(FiltroNormaProcedimental.ID);
		FiltroNormaProcedimental filtroNormaProcedimental = new FiltroNormaProcedimental();
		boolean peloMenosUmParametroInformado = false;

		// -> Inserindo os par�metros informados no filtro

		// Verifica se codNormaProcedimental foi informado.
		if(codigoNormaProcedimental != null && !codigoNormaProcedimental.trim().equalsIgnoreCase("")){
			peloMenosUmParametroInformado = true;
			filtroNormaProcedimental.adicionarParametro(new ParametroSimples(FiltroNormaProcedimental.ID, new Integer(codigoNormaProcedimental)));
		}

		// Verifica se o nome foi informado.
		if(nomeNormaProcedimental != null && !nomeNormaProcedimental.trim().equalsIgnoreCase("")){

			peloMenosUmParametroInformado = true;

			if(tipoPesquisa != null && tipoPesquisa.equals(ConstantesSistema.TIPO_PESQUISA_COMPLETA.toString())){
				filtroNormaProcedimental.adicionarParametro(new ComparacaoTextoCompleto(FiltroNormaProcedimental.DESCRICAO, nomeNormaProcedimental));
			}else{
				filtroNormaProcedimental.adicionarParametro(new ComparacaoTexto(FiltroNormaProcedimental.DESCRICAO, nomeNormaProcedimental));
			}
		}

		// Verifica se o indicador de uso foi informado.
		if(indicadorUso != null && !indicadorUso.trim().equalsIgnoreCase("")){
			peloMenosUmParametroInformado = true;
			filtroNormaProcedimental.adicionarParametro(new ParametroSimples(FiltroNormaProcedimental.INDICADOR_USO, indicadorUso));
		}

		// Caso o usu�rio n�o tenha informado nenhum par�metro
		if(!peloMenosUmParametroInformado){
			throw new ActionServletException("atencao.filtro.nenhum_parametro_informado");
		}

		/*
		 * Verifica se o checkbox Atualizar est� marcado e em caso
		 * afirmativo manda pelo um request uma vari�vel para o
		 * ExibirManterNormaProcedimentalAction e nele verificar se ir� para o
		 * atualizar ou para o manter
		 */
		if(filtrarNormaProcedimentalActionForm.getCheckAtualizar() != null && filtrarNormaProcedimentalActionForm.getCheckAtualizar().equalsIgnoreCase("1")){
			httpServletRequest.setAttribute("atualizar", filtrarNormaProcedimentalActionForm.getCheckAtualizar());

		}

		// Manda o filtro pelo request para o ExibirManterNormaProcedimentalAction
		httpServletRequest.setAttribute("filtroNormaProcedimental", filtroNormaProcedimental); // ?
		sessao.setAttribute("filtroNormaProcedimental", filtroNormaProcedimental);

		return retorno;

	}
}
