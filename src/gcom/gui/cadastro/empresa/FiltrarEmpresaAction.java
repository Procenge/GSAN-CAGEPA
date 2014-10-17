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

package gcom.gui.cadastro.empresa;

import gcom.cadastro.empresa.FiltroEmpresa;
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

public class FiltrarEmpresaAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirManterEmpresa");
		HttpSession sessao = httpServletRequest.getSession(false);
		FiltrarEmpresaActionForm filtrarEmpresaActionForm = (FiltrarEmpresaActionForm) actionForm;

		String codigoEmpresa = (String) filtrarEmpresaActionForm.getCodigoEmpresa();
		String nomeEmpresa = (String) filtrarEmpresaActionForm.getNomeEmpresa();
		String indicadorUso = (String) filtrarEmpresaActionForm.getIndicadorUso();
		String tipoPesquisa = (String) filtrarEmpresaActionForm.getTipoPesquisa();

		// FiltroEmpresa filtroEmpresa = new FiltroEmpresa(FiltroEmpresa.ID);
		FiltroEmpresa filtroEmpresa = new FiltroEmpresa();
		boolean peloMenosUmParametroInformado = false;

		// -> Inserindo os parâmetros informados no filtro

		// Verifica se codEmpresa foi informado.
		if(codigoEmpresa != null && !codigoEmpresa.trim().equalsIgnoreCase("")){
			peloMenosUmParametroInformado = true;
			filtroEmpresa.adicionarParametro(new ParametroSimples(FiltroEmpresa.ID, new Integer(codigoEmpresa)));
		}

		// Verifica se o nome foi informado.
		if(nomeEmpresa != null && !nomeEmpresa.trim().equalsIgnoreCase("")){

			peloMenosUmParametroInformado = true;

			if(tipoPesquisa != null && tipoPesquisa.equals(ConstantesSistema.TIPO_PESQUISA_COMPLETA.toString())){
				filtroEmpresa.adicionarParametro(new ComparacaoTextoCompleto(FiltroEmpresa.DESCRICAO, nomeEmpresa));
			}else{
				filtroEmpresa.adicionarParametro(new ComparacaoTexto(FiltroEmpresa.DESCRICAO, nomeEmpresa));
			}
		}

		// Verifica se o indicador de uso foi informado.
		if(indicadorUso != null && !indicadorUso.trim().equalsIgnoreCase("")){
			peloMenosUmParametroInformado = true;
			filtroEmpresa.adicionarParametro(new ParametroSimples(FiltroEmpresa.INDICADORUSO, indicadorUso));
		}

		// Caso o usuário não tenha informado nenhum parâmetro
		if(!peloMenosUmParametroInformado){
			throw new ActionServletException("atencao.filtro.nenhum_parametro_informado");
		}

		/*
		 * Verifica se o checkbox Atualizar está marcado e em caso
		 * afirmativo manda pelo um request uma variável para o
		 * ExibirManterEmpresaAction e nele verificar se irá para o
		 * atualizar ou para o manter
		 */
		if(filtrarEmpresaActionForm.getCheckAtualizar() != null && filtrarEmpresaActionForm.getCheckAtualizar().equalsIgnoreCase("1")){
			httpServletRequest.setAttribute("atualizar", filtrarEmpresaActionForm.getCheckAtualizar());

		}

		// Manda o filtro pelo request para o ExibirManterEmpresaAction
		httpServletRequest.setAttribute("filtroEmpresa", filtroEmpresa); // ?
		sessao.setAttribute("filtroEmpresa", filtroEmpresa);

		return retorno;

	}
}
