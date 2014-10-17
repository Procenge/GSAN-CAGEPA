/**
 * 
 */
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

		// Verificando datas - Se data inicial � posterior a hoje
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

		// Verificando datas - Intervalo Inv�lido
		if(!"".equals(dataInicio) && !"".equals(dataFim)){

			Date dataInicial = Util.converteStringParaDate(dataInicio);
			Date dataFinal = Util.converteStringParaDate(dataFim);

			if(dataFinal.getTime() < dataInicial.getTime()){
				throw new ActionServletException("atencao.data.intervalo.invalido", null, Util.formatarData(new Date()));
			}
		}

		// Erro caso o usu�rio mandou Pesquisar sem nenhum par�metro
		if(!peloMenosUmParametroInformado){
			throw new ActionServletException("atencao.filtro.nenhum_parametro_informado");
		}

		if(dataInicio != null && !dataInicio.equals("") && dataFim != null && !dataFim.equals("")){
			filtro.adicionarParametro(new Intervalo(FiltroCobrancaContrato.DATA_INICIAL, dataInicio, dataFim));
		}

		// Verifica se o checkbox Atualizar est� marcado e em caso afirmativo manda pelo um request
		// uma vari�vel para o
		// ExibirManterContratoCobrancaAction e nele verificar se ir� para o atualizar ou para o
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
