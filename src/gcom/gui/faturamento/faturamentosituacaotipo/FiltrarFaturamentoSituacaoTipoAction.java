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

package gcom.gui.faturamento.faturamentosituacaotipo;

import gcom.faturamento.FiltroFaturamentoSituacaoTipo;
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

public class FiltrarFaturamentoSituacaoTipoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirManterFaturamentoSituacaoTipo");
		HttpSession sessao = httpServletRequest.getSession(false);

		DynaActionForm form = (DynaActionForm) actionForm;

		String descricao = (String) form.get("descricao");
		String indicadorParalisacaoFaturamento = (String) form.get("indicadorParalisacaoFaturamento");
		String indicadorParalisacaoLeitura = (String) form.get("indicadorParalisacaoLeitura");
		String indicadorUso = (String) form.get("indicadorUso");
		String indicadorValidoAgua = (String) form.get("indicadorValidoAgua");
		String indicadorValidoEsgoto = (String) form.get("indicadorValidoEsgoto");
		String indicadorPercentualEsgoto = (String) form.get("indicadorPercentualEsgoto");
		String indicadorFaturamentoParalisacaoEsgoto = (String) form.get("indicadorFaturamentoParalisacaoEsgoto");
		String leituraAnormalidadeLeituraComLeitura = (String) form.get("leituraAnormalidadeLeituraComLeitura");
		String leituraAnormalidadeLeituraSemLeitura = (String) form.get("leituraAnormalidadeLeituraSemLeitura");
		String leituraAnormalidadeConsumoComLeitura = (String) form.get("leituraAnormalidadeConsumoComLeitura");
		String leituraAnormalidadeConsumoSemLeitura = (String) form.get("leituraAnormalidadeConsumoSemLeitura");
		String checkAtualizar = (String) form.get("checkAtualizar");
		String tipoPesquisaDescricao = (String) form.get("tipoPesquisaDescricao");

		FiltroFaturamentoSituacaoTipo filtroFaturamentoSituacaoTipo = new FiltroFaturamentoSituacaoTipo();
		// filtroFaturamentoSituacaoTipo
		// .adicionarCaminhoParaCarregamentoEntidade(FiltroFaturamentoSituacaoTipo.LEITURA_ANORMALIDADE_CONSUMOCOM_LEITURA);
		// filtroFaturamentoSituacaoTipo
		// .adicionarCaminhoParaCarregamentoEntidade(FiltroFaturamentoSituacaoTipo.LEITURA_ANORMALIDADE_CONSUMOSEM_LEITURA);
		// filtroFaturamentoSituacaoTipo
		// .adicionarCaminhoParaCarregamentoEntidade(FiltroFaturamentoSituacaoTipo.LEITURA_ANORMALIDADE_LEITURACOM_LEITURA);
		// filtroFaturamentoSituacaoTipo
		// .adicionarCaminhoParaCarregamentoEntidade(FiltroFaturamentoSituacaoTipo.LEITURA_ANORMALIDADE_LEITURASEM_LEITURA);
		boolean peloMenosUmParametroInformado = false;
		
		// -> Inserindo os parâmetros informados no filtro

		//Descricao
		if(!Util.isVazioOuBranco(descricao)){

			peloMenosUmParametroInformado = true;

			if(tipoPesquisaDescricao != null && tipoPesquisaDescricao.equalsIgnoreCase(ConstantesSistema.TIPO_PESQUISA_COMPLETA.toString())){
				filtroFaturamentoSituacaoTipo.adicionarParametro(new ComparacaoTextoCompleto(FiltroFaturamentoSituacaoTipo.DESCRICAO, descricao));
			}else{
				filtroFaturamentoSituacaoTipo.adicionarParametro(new ComparacaoTexto(FiltroFaturamentoSituacaoTipo.DESCRICAO, descricao));
			}
		}
		
		//indicadorParalisacaoFaturamento
		if(!Util.isVazioOuBranco(indicadorParalisacaoFaturamento) && !indicadorParalisacaoFaturamento.equalsIgnoreCase(String.valueOf(ConstantesSistema.TODOS))){
			peloMenosUmParametroInformado = true;
			if(indicadorParalisacaoFaturamento.equalsIgnoreCase(String.valueOf(ConstantesSistema.INDICADOR_USO_ATIVO))){
				filtroFaturamentoSituacaoTipo.adicionarParametro(new ParametroSimples(
								FiltroFaturamentoSituacaoTipo.INDICADOR_PARALISACAO_FATURAMENTO, ConstantesSistema.INDICADOR_USO_ATIVO));
			}else{
				filtroFaturamentoSituacaoTipo.adicionarParametro(new ParametroSimples(
								FiltroFaturamentoSituacaoTipo.INDICADOR_PARALISACAO_FATURAMENTO, ConstantesSistema.INDICADOR_USO_DESATIVO));
			}
		}
		
		//indicadorParalisacaoLeitura
		if(!Util.isVazioOuBranco(indicadorParalisacaoLeitura) && !indicadorParalisacaoLeitura.equalsIgnoreCase(String.valueOf(ConstantesSistema.TODOS))){
			peloMenosUmParametroInformado = true;
			if(indicadorParalisacaoLeitura.equalsIgnoreCase(String.valueOf(ConstantesSistema.INDICADOR_USO_ATIVO))){
				filtroFaturamentoSituacaoTipo.adicionarParametro(new ParametroSimples(
								FiltroFaturamentoSituacaoTipo.INDICADOR_PARALISACAO_LEITURA, ConstantesSistema.INDICADOR_USO_ATIVO));
			}else{
				filtroFaturamentoSituacaoTipo.adicionarParametro(new ParametroSimples(
								FiltroFaturamentoSituacaoTipo.INDICADOR_PARALISACAO_LEITURA, ConstantesSistema.INDICADOR_USO_DESATIVO));
			}
		}
		
		//indicadorValidoAgua
		if(!Util.isVazioOuBranco(indicadorValidoAgua) && !indicadorValidoAgua.equalsIgnoreCase(String.valueOf(ConstantesSistema.TODOS))){
			peloMenosUmParametroInformado = true;
			if(indicadorValidoAgua.equalsIgnoreCase(String.valueOf(ConstantesSistema.INDICADOR_USO_ATIVO))){
				filtroFaturamentoSituacaoTipo.adicionarParametro(new ParametroSimples(FiltroFaturamentoSituacaoTipo.INDICADOR_VALIDO_AGUA,
								ConstantesSistema.INDICADOR_USO_ATIVO));
			}else{
				filtroFaturamentoSituacaoTipo.adicionarParametro(new ParametroSimples(FiltroFaturamentoSituacaoTipo.INDICADOR_VALIDO_AGUA,
								ConstantesSistema.INDICADOR_USO_DESATIVO));
			}
		}

		// indicadorValidoEsgoto
		if(!Util.isVazioOuBranco(indicadorValidoEsgoto) && !indicadorValidoEsgoto.equalsIgnoreCase(String.valueOf(ConstantesSistema.TODOS))){
			peloMenosUmParametroInformado = true;
			if(indicadorValidoEsgoto.equalsIgnoreCase(String.valueOf(ConstantesSistema.INDICADOR_USO_ATIVO))){
				filtroFaturamentoSituacaoTipo.adicionarParametro(new ParametroSimples(
								FiltroFaturamentoSituacaoTipo.INDICADOR_VALIDO_ESGOTO, ConstantesSistema.INDICADOR_USO_ATIVO));
			}else{
				filtroFaturamentoSituacaoTipo.adicionarParametro(new ParametroSimples(
								FiltroFaturamentoSituacaoTipo.INDICADOR_VALIDO_ESGOTO, ConstantesSistema.INDICADOR_USO_DESATIVO));
			}
		}
		
		// Indicador de Uso
		if(!Util.isVazioOuBranco(indicadorUso) && !indicadorUso.equalsIgnoreCase(String.valueOf(ConstantesSistema.TODOS))){
			peloMenosUmParametroInformado = true;
			if(indicadorUso.equalsIgnoreCase(String.valueOf(ConstantesSistema.INDICADOR_USO_ATIVO))){
				filtroFaturamentoSituacaoTipo.adicionarParametro(new ParametroSimples(FiltroFaturamentoSituacaoTipo.INDICADOR_USO,
								ConstantesSistema.INDICADOR_USO_ATIVO));
			}else{
				filtroFaturamentoSituacaoTipo.adicionarParametro(new ParametroSimples(FiltroFaturamentoSituacaoTipo.INDICADOR_USO,
								ConstantesSistema.INDICADOR_USO_DESATIVO));
			}
		}

		// indicadorPercentualEsgoto
		if(!Util.isVazioOuBranco(indicadorPercentualEsgoto)
						&& !indicadorPercentualEsgoto.equalsIgnoreCase(String.valueOf(ConstantesSistema.TODOS))){
			peloMenosUmParametroInformado = true;
			if(indicadorPercentualEsgoto.equalsIgnoreCase(String.valueOf(ConstantesSistema.INDICADOR_USO_ATIVO))){
				filtroFaturamentoSituacaoTipo.adicionarParametro(new ParametroSimples(
								FiltroFaturamentoSituacaoTipo.INDICADOR_PERCENTUAL_ESGOTO, ConstantesSistema.INDICADOR_USO_ATIVO));
			}else{
				filtroFaturamentoSituacaoTipo.adicionarParametro(new ParametroSimples(
								FiltroFaturamentoSituacaoTipo.INDICADOR_PERCENTUAL_ESGOTO, ConstantesSistema.INDICADOR_USO_DESATIVO));
			}
		}

		// indicadorFaturamentoParalisacaoEsgoto
		if(!Util.isVazioOuBranco(indicadorFaturamentoParalisacaoEsgoto)
						&& !indicadorFaturamentoParalisacaoEsgoto.equalsIgnoreCase(String.valueOf(ConstantesSistema.TODOS))){
			peloMenosUmParametroInformado = true;
			if(indicadorFaturamentoParalisacaoEsgoto.equalsIgnoreCase(String.valueOf(ConstantesSistema.INDICADOR_USO_ATIVO))){
				filtroFaturamentoSituacaoTipo.adicionarParametro(new ParametroSimples(
								FiltroFaturamentoSituacaoTipo.INDICADOR_FATURAMENTO_PARALISACAO_ESGOTO,
								ConstantesSistema.INDICADOR_USO_ATIVO));
			}else{
				filtroFaturamentoSituacaoTipo.adicionarParametro(new ParametroSimples(
								FiltroFaturamentoSituacaoTipo.INDICADOR_FATURAMENTO_PARALISACAO_ESGOTO,
								ConstantesSistema.INDICADOR_USO_DESATIVO));
			}
		}

		// leituraAnormalidadeLeituraComLeitura
		if(leituraAnormalidadeLeituraComLeitura != null
						&& !leituraAnormalidadeLeituraComLeitura.trim().equalsIgnoreCase(
										String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){

			peloMenosUmParametroInformado = true;

			filtroFaturamentoSituacaoTipo
							.adicionarParametro(new ParametroSimples(
											FiltroFaturamentoSituacaoTipo.LEITURA_ANORMALIDADE_LEITURACOM_LEITURA_ID,
											leituraAnormalidadeLeituraComLeitura));
		}

		// leituraAnormalidadeLeituraSemLeitura
		if(leituraAnormalidadeLeituraSemLeitura != null
						&& !leituraAnormalidadeLeituraSemLeitura.trim().equalsIgnoreCase(
										String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){

			peloMenosUmParametroInformado = true;

			filtroFaturamentoSituacaoTipo
							.adicionarParametro(new ParametroSimples(
											FiltroFaturamentoSituacaoTipo.LEITURA_ANORMALIDADE_LEITURASEM_LEITURA_ID,
											leituraAnormalidadeLeituraSemLeitura));
		}

		// leituraAnormalidadeConsumoComLeitura
		if(leituraAnormalidadeConsumoComLeitura != null
						&& !leituraAnormalidadeConsumoComLeitura.trim().equalsIgnoreCase(
										String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){

			peloMenosUmParametroInformado = true;

			filtroFaturamentoSituacaoTipo
							.adicionarParametro(new ParametroSimples(
											FiltroFaturamentoSituacaoTipo.LEITURA_ANORMALIDADE_CONSUMOCOM_LEITURA_ID,
											leituraAnormalidadeConsumoComLeitura));
		}

		// leituraAnormalidadeConsumoSemLeitura
		if(leituraAnormalidadeConsumoSemLeitura != null
						&& !leituraAnormalidadeConsumoSemLeitura.trim().equalsIgnoreCase(
										String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){

			peloMenosUmParametroInformado = true;

			filtroFaturamentoSituacaoTipo
							.adicionarParametro(new ParametroSimples(
											FiltroFaturamentoSituacaoTipo.LEITURA_ANORMALIDADE_CONSUMOSEM_LEITURA_ID,
											leituraAnormalidadeConsumoSemLeitura));
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
		httpServletRequest.setAttribute("filtroFaturamentoSituacaoTipo", filtroFaturamentoSituacaoTipo); // ?
		sessao.setAttribute("filtroFaturamentoSituacaoTipo", filtroFaturamentoSituacaoTipo);

		return retorno;

	}
}
