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

import gcom.cobranca.contrato.FiltroCobrancaContrato;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.Intervalo;
import gcom.util.filtro.ParametroSimples;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class FiltrarContratoCobrancaAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirManterContratoCobranca");
		FiltrarContratoCobrancaActionForm form = (FiltrarContratoCobrancaActionForm) actionForm;
		FiltroCobrancaContrato filtro = new FiltroCobrancaContrato();
		HttpSession sessao = httpServletRequest.getSession(false);
		boolean peloMenosUmParametroInformado = false;

		String numeroContrato = form.getNumeroContrato();
		String idEmpresa = form.getEmpresa();
		String dataInicio = form.getDataInicio();
		String dataFim = form.getDataFim();

		// Verifica se o campo ncontrato
		if(numeroContrato != null && !numeroContrato.trim().equalsIgnoreCase("")){
			peloMenosUmParametroInformado = true;
			filtro.adicionarParametro(new ParametroSimples(FiltroCobrancaContrato.NUMERO_CONTRATO, numeroContrato));
		}

		// Verifica se o campo empresa foi informado
		if(idEmpresa != null && !idEmpresa.trim().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			peloMenosUmParametroInformado = true;
			filtro.adicionarParametro(new ParametroSimples(FiltroCobrancaContrato.EMPRESA_ID, idEmpresa));
		}

		// Verificando datas - Se data inicial é posterior a hoje
		if(!"".equals(dataInicio)){

			peloMenosUmParametroInformado = true;

			Date data = Util.converteStringParaDate(dataInicio);

			if(data == null){
				throw new ActionServletException("atencao.data.inicio.invalida");
			}

			if(data.getTime() > new Date(System.currentTimeMillis()).getTime()){
				throw new ActionServletException("atencao.data_inicial.posterior.hoje", null, Util.formatarData(new Date()));
			}
		}

		// Verificando datas - Intervalo Inválido
		if(!"".equals(dataInicio) && !"".equals(dataFim)){

			Date dataInicial = Util.converteStringParaDate(dataInicio);
			Date dataFinal = Util.converteStringParaDate(dataFim);

			if(dataFinal.getTime() < dataInicial.getTime()){
				throw new ActionServletException("atencao.data.intervalo.invalido", null, Util.formatarData(new Date()));
			}
		}

		// Erro caso o usuário mandou Pesquisar sem nenhum parâmetro
		if(!peloMenosUmParametroInformado){
			throw new ActionServletException("atencao.filtro.nenhum_parametro_informado");
		}

		if(dataInicio != null && !dataInicio.equals("") && dataFim != null && !dataFim.equals("")){
			filtro.adicionarParametro(new Intervalo(FiltroCobrancaContrato.DATA_INICIAL, dataInicio, dataFim));
		}

		// Verifica se o checkbox Atualizar está marcado e em caso afirmativo manda pelo um request
		// uma variável para o
		// ExibirManterContratoCobrancaAction e nele verificar se irá para o atualizar ou para o
		// manter
		if(form.getCheckAtualizar() != null && form.getCheckAtualizar().equalsIgnoreCase("1")){
			httpServletRequest.setAttribute("atualizar", form.getCheckAtualizar());

		}

		filtro.adicionarCaminhoParaCarregamentoEntidade("empresa");

		// Manda o filtro pelo sessao para o ExibirFuncionalidadeAction
		sessao.setAttribute("filtroContratoCobranca", filtro);
		httpServletRequest.setAttribute("filtroContratoCobranca", filtro);

		return retorno;

	}

}
