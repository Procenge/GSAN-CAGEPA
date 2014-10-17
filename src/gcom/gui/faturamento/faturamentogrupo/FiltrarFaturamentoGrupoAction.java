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

package gcom.gui.faturamento.faturamentogrupo;

import gcom.faturamento.FiltroFaturamentoGrupo;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ComparacaoTexto;
import gcom.util.filtro.ComparacaoTextoCompleto;
import gcom.util.filtro.ParametroSimples;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

public class FiltrarFaturamentoGrupoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirManterFaturamentoGrupo");
		HttpSession sessao = httpServletRequest.getSession(false);

		DynaActionForm form = (DynaActionForm) actionForm;

		String idFaturamentoGrupo = (String) form.get("idFaturamentoGrupo");
		String descricao = (String) form.get("descricao");
		String descricaoAbreviada = (String) form.get("descricaoAbreviada");
		String indicadorUso = (String) form.get("indicadorUso");
		String anoMesReferencia = (String) form.get("anoMesReferencia");
		String diaVencimento = (String) form.get("diaVencimento");
		String indicadorVencimentoMesFatura = (String) form.get("indicadorVencimentoMesFatura");
		String checkAtualizar = (String) form.get("checkAtualizar");
		String tipoPesquisaDescricao = (String) form.get("tipoPesquisaDescricao");

		FiltroFaturamentoGrupo filtroFaturamentoGrupo = new FiltroFaturamentoGrupo(FiltroFaturamentoGrupo.DESCRICAO);

		boolean peloMenosUmParametroInformado = false;
		
		// -> Inserindo os parâmetros informados no filtro

		// Descricao
		if(!Util.isVazioOuBranco(idFaturamentoGrupo)){

			peloMenosUmParametroInformado = true;
			filtroFaturamentoGrupo.adicionarParametro(new ParametroSimples(FiltroFaturamentoGrupo.ID, idFaturamentoGrupo));
		}

		//Descricao
		if(!Util.isVazioOuBranco(descricao)){

			peloMenosUmParametroInformado = true;

			if(tipoPesquisaDescricao != null && tipoPesquisaDescricao.equalsIgnoreCase(ConstantesSistema.TIPO_PESQUISA_COMPLETA.toString())){
				filtroFaturamentoGrupo.adicionarParametro(new ComparacaoTextoCompleto(FiltroFaturamentoGrupo.DESCRICAO, descricao));
			}else{
				filtroFaturamentoGrupo.adicionarParametro(new ComparacaoTexto(FiltroFaturamentoGrupo.DESCRICAO, descricao));
			}
		}
		
		// Descricao Abreviada
		if(!Util.isVazioOuBranco(descricaoAbreviada)){
			peloMenosUmParametroInformado = true;
			filtroFaturamentoGrupo.adicionarParametro(new ComparacaoTexto(FiltroFaturamentoGrupo.DESCRICAO_ABREVIADA, descricaoAbreviada));
		}
		
		// Indicador de Uso
		if(!Util.isVazioOuBranco(indicadorUso) && !indicadorUso.equalsIgnoreCase(String.valueOf(ConstantesSistema.TODOS))){
			peloMenosUmParametroInformado = true;
			if(indicadorUso.equalsIgnoreCase(String.valueOf(ConstantesSistema.INDICADOR_USO_ATIVO))){
				filtroFaturamentoGrupo.adicionarParametro(new ParametroSimples(FiltroFaturamentoGrupo.INDICADOR_USO,
								ConstantesSistema.INDICADOR_USO_ATIVO));
			}else{
				filtroFaturamentoGrupo.adicionarParametro(new ParametroSimples(FiltroFaturamentoGrupo.INDICADOR_USO,
								ConstantesSistema.INDICADOR_USO_DESATIVO));
			}
		}

		// Mes Ano Faturamento
		if(!Util.isVazioOuBranco(anoMesReferencia)){
			peloMenosUmParametroInformado = true;
			filtroFaturamentoGrupo.adicionarParametro(new ParametroSimples(FiltroFaturamentoGrupo.ANO_MES_REFERENCIA, Util
							.formatarMesAnoParaAnoMesSemBarra(anoMesReferencia)));
		}

		// Dia Vencimento
		if(!Util.isVazioOuBranco(diaVencimento)){
			peloMenosUmParametroInformado = true;
			filtroFaturamentoGrupo.adicionarParametro(new ParametroSimples(FiltroFaturamentoGrupo.DIA_VENCIMENTO, diaVencimento));
		}

		// Indicador Vencimeto Mes Fatura
		if(!Util.isVazioOuBranco(indicadorVencimentoMesFatura)
						&& !indicadorVencimentoMesFatura.equalsIgnoreCase(String.valueOf(ConstantesSistema.TODOS))){
			peloMenosUmParametroInformado = true;
			if(indicadorVencimentoMesFatura.equalsIgnoreCase(String.valueOf(ConstantesSistema.INDICADOR_USO_ATIVO))){
				filtroFaturamentoGrupo.adicionarParametro(new ParametroSimples(FiltroFaturamentoGrupo.INDICADOR_VENCIMENTO_MES_FATURA,
								ConstantesSistema.INDICADOR_USO_ATIVO));
			}else{
				filtroFaturamentoGrupo.adicionarParametro(new ParametroSimples(FiltroFaturamentoGrupo.INDICADOR_VENCIMENTO_MES_FATURA,
								ConstantesSistema.INDICADOR_USO_DESATIVO));
			}
		}

		// Caso o usuário não tenha informado nenhum parâmetro
		if(!peloMenosUmParametroInformado){
			throw new ActionServletException("atencao.filtro.nenhum_parametro_informado");
		}

		/*
		 * Verifica se o checkbox Atualizar está marcado e em caso
		 * afirmativo manda pelo um request uma variável para o
		 * ExibirManterFaturamentoGrupoAction e nele verificar se irá para o
		 * atualizar ou para o manter
		 */
		if(form.get("checkAtualizar") != null && form.get("checkAtualizar").toString().equalsIgnoreCase("1")){
			httpServletRequest.setAttribute("atualizar", form.get("checkAtualizar"));

		}

		// Manda o filtro pelo request para o ExibirManterFaturamentoGrupo
		httpServletRequest.setAttribute("filtroFaturamentoGrupo", filtroFaturamentoGrupo); // ?
		sessao.setAttribute("filtroFaturamentoGrupo", filtroFaturamentoGrupo);

		return retorno;

	}
}
