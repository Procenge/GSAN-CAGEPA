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

package gcom.gui.cadastro.entidadebeneficente;

import gcom.cadastro.imovel.FiltroEntidadeBeneficente;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.filtro.ParametroSimples;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

public class FiltrarEntidadeBeneficenteAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirManterEntidadeBeneficente");
		HttpSession sessao = httpServletRequest.getSession(false);

		DynaActionForm form = (DynaActionForm) actionForm;

		String idCliente = (String) form.get("idCliente");
		String idEmpresa = (String) form.get("idEmpresa");
		String nomeCliente = (String) form.get("nomeCliente");
		String indicadorUso = (String) form.get("indicadorUso");
		String idDebitoTipo = (String) form.get("idDebitoTipo");
		String descricaoDebitoTipo = (String) form.get("descricaoDebitoTipo");

		FiltroEntidadeBeneficente filtroEntidadeBeneficente = new FiltroEntidadeBeneficente();
		boolean peloMenosUmParametroInformado = false;
		
		filtroEntidadeBeneficente.adicionarCaminhoParaCarregamentoEntidade(FiltroEntidadeBeneficente.CLIENTE);
		filtroEntidadeBeneficente.adicionarCaminhoParaCarregamentoEntidade(FiltroEntidadeBeneficente.DEBITO_TIPO);

		// -> Inserindo os parâmetros informados no filtro

		// Verifica se idCliente foi informado.
		if(idCliente != null && !idCliente.trim().equalsIgnoreCase("")){
			peloMenosUmParametroInformado = true;
			filtroEntidadeBeneficente.adicionarParametro(new ParametroSimples(FiltroEntidadeBeneficente.ID_CLIENTE, idCliente));
		}

		// Verifica se idDebitoTipo foi informado.
		if(idDebitoTipo != null && !idDebitoTipo.trim().equalsIgnoreCase("")){
			peloMenosUmParametroInformado = true;
			filtroEntidadeBeneficente.adicionarParametro(new ParametroSimples(FiltroEntidadeBeneficente.ID_DEBITO_TIPO, idDebitoTipo));
		}

		// Verifica se idEmpresa foi informado.
		if(idEmpresa != null && !idEmpresa.trim().equalsIgnoreCase("")){
			peloMenosUmParametroInformado = true;
			filtroEntidadeBeneficente.adicionarParametro(new ParametroSimples(FiltroEntidadeBeneficente.ID_EMPRESA, Integer
							.valueOf(idEmpresa)));
		}

		// Verifica se o indicador de uso foi informado.
		if(indicadorUso != null && !indicadorUso.trim().equalsIgnoreCase("")){
			peloMenosUmParametroInformado = true;
			filtroEntidadeBeneficente.adicionarParametro(new ParametroSimples(FiltroEntidadeBeneficente.INDICADOR_USO, indicadorUso));
		}

		// Caso o usuário não tenha informado nenhum parâmetro
		if(!peloMenosUmParametroInformado){
			throw new ActionServletException("atencao.filtro.nenhum_parametro_informado");
		}

		/*
		 * Verifica se o checkbox Atualizar está marcado e em caso
		 * afirmativo manda pelo um request uma variável para o
		 * ExibirManterEntidadeBeneficenteAction e nele verificar se irá para o
		 * atualizar ou para o manter
		 */
		if(form.get("checkAtualizar") != null && form.get("checkAtualizar").toString().equalsIgnoreCase("1")){
			httpServletRequest.setAttribute("atualizar", form.get("checkAtualizar"));

		}

		// Manda o filtro pelo request para o ExibirManterEntidadeBeneficenteAction
		httpServletRequest.setAttribute("filtroEntidadeBeneficente", filtroEntidadeBeneficente); // ?
		sessao.setAttribute("filtroEntidadeBeneficente", filtroEntidadeBeneficente);

		return retorno;

	}
}
