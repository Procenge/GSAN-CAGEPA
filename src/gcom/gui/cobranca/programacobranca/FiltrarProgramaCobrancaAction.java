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

package gcom.gui.cobranca.programacobranca;

import gcom.cobranca.programacobranca.FiltroProgramaCobranca;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ComparacaoTexto;
import gcom.util.filtro.ComparacaoTextoCompleto;
import gcom.util.filtro.MaiorQue;
import gcom.util.filtro.MenorQue;
import gcom.util.filtro.ParametroSimples;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action responsável por montar o filtro que será utilizado na consulta dos programas de cobrança.
 * 
 * @author Virgínia Melo
 * @date 27/08/2008
 */
public class FiltrarProgramaCobrancaAction
				extends GcomAction {

	boolean peloMenosUmParametroInformado = false;

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirManterProgramaCobranca");
		HttpSession sessao = httpServletRequest.getSession(false);
		FiltrarProgramaCobrancaActionForm form = (FiltrarProgramaCobrancaActionForm) actionForm;

		String codigoPrograma = (String) form.getCodigoProgramaCobranca();
		String nomePrograma = (String) form.getNomeProgramaCobranca();
		String descricaoPrograma = (String) form.getDescricaoProgramaCobranca();
		String idCriterio = (String) form.getIdCriterio();

		String dataCriacaoInicial = form.getDataCriacaoInicial();
		String dataCriacaoFinal = form.getDataCriacaoFinal();

		String dataInicioInicial = form.getDataInicioInicial();
		String dataInicioFinal = form.getDataInicioFinal();

		String dataEncerramentoInicial = form.getDataEncerramentoInicial();
		String dataEncerramentoFinal = form.getDataEncerramentoFinal();

		String dataUltimoMovimentoInicial = form.getDataUltimoMovimentoInicial();
		String dataUltimoMovimentoFinal = form.getDataUltimoMovimentoFinal();

		String tipoPesquisa = form.getTipoPesquisa();
		String tipoPesquisaDescricao = form.getTipoPesquisaDescricao();

		FiltroProgramaCobranca filtro = new FiltroProgramaCobranca(FiltroProgramaCobranca.ID);
		peloMenosUmParametroInformado = false;

		// Inserindo os parâmetros informados no filtro

		// Verifica se o código do programa foi informado
		if(codigoPrograma != null && !codigoPrograma.trim().equalsIgnoreCase("")){
			peloMenosUmParametroInformado = true;
			filtro.adicionarParametro(new ParametroSimples(FiltroProgramaCobranca.ID, new Integer(codigoPrograma)));
		}

		// Verifica se o nome foi informado
		if(nomePrograma != null && !nomePrograma.trim().equalsIgnoreCase("")){

			if(tipoPesquisa != null && tipoPesquisa.equals(ConstantesSistema.TIPO_PESQUISA_COMPLETA.toString())){
				filtro.adicionarParametro(new ComparacaoTextoCompleto(FiltroProgramaCobranca.NOME, nomePrograma));
			}else{
				filtro.adicionarParametro(new ComparacaoTexto(FiltroProgramaCobranca.NOME, nomePrograma));
			}

			peloMenosUmParametroInformado = true;
			// filtro.adicionarParametro(new ParametroSimples(FiltroProgramaCobranca.NOME,
			// nomePrograma));
		}

		// Verifica se a descrição foi informada
		if(descricaoPrograma != null && !descricaoPrograma.trim().equalsIgnoreCase("")){

			if(tipoPesquisaDescricao != null && tipoPesquisaDescricao.equals(ConstantesSistema.TIPO_PESQUISA_COMPLETA.toString())){
				filtro.adicionarParametro(new ComparacaoTextoCompleto(FiltroProgramaCobranca.DESCRICAO, descricaoPrograma));
			}else{
				filtro.adicionarParametro(new ComparacaoTexto(FiltroProgramaCobranca.DESCRICAO, descricaoPrograma));
			}

			peloMenosUmParametroInformado = true;
			// filtro.adicionarParametro(new ParametroSimples(FiltroProgramaCobranca.DESCRICAO,
			// descricaoPrograma));
		}

		// Verifica se o ID do Critério foi informado
		if(idCriterio != null && !idCriterio.trim().equalsIgnoreCase("")){
			peloMenosUmParametroInformado = true;
			filtro.adicionarParametro(new ParametroSimples(FiltroProgramaCobranca.CRITERIO_ID, new Integer(idCriterio)));
		}

		// Verificando se todas as datas estão com intervalo correto. A data final deve ser maior
		// que a inicial.
		Util.validarIntervaloDatas(dataCriacaoInicial, dataCriacaoFinal);
		Util.validarIntervaloDatas(dataInicioInicial, dataInicioFinal);
		Util.validarIntervaloDatas(dataEncerramentoInicial, dataEncerramentoFinal);
		Util.validarIntervaloDatas(dataUltimoMovimentoInicial, dataUltimoMovimentoFinal);

		// Setando no filtro as 8 datas informadas
		// Data de Criação:
		adicionarDataInicialFiltro(dataCriacaoInicial, FiltroProgramaCobranca.DATA_CRIACAO, filtro);
		adicionarDataFinalFiltro(dataCriacaoFinal, FiltroProgramaCobranca.DATA_CRIACAO, filtro);

		// Data de início do programa de cobrança:
		adicionarDataInicialFiltro(dataInicioInicial, FiltroProgramaCobranca.DATA_INICIO, filtro);
		adicionarDataFinalFiltro(dataInicioFinal, FiltroProgramaCobranca.DATA_INICIO, filtro);

		// Data de encerramento do programa:
		adicionarDataInicialFiltro(dataEncerramentoInicial, FiltroProgramaCobranca.DATA_ENCERRAMENTO, filtro);
		adicionarDataFinalFiltro(dataEncerramentoFinal, FiltroProgramaCobranca.DATA_ENCERRAMENTO, filtro);

		// Data da última movimentação:
		adicionarDataInicialFiltro(dataUltimoMovimentoInicial, FiltroProgramaCobranca.DATA_ULTIMO_MOVIMENTO, filtro);
		adicionarDataFinalFiltro(dataUltimoMovimentoFinal, FiltroProgramaCobranca.DATA_ULTIMO_MOVIMENTO, filtro);

		// Caso o usuário não tenha informado nenhum parâmetro
		if(!peloMenosUmParametroInformado){
			throw new ActionServletException("atencao.filtro.nenhum_parametro_informado");
		}

		/*
		 * Caso o check Atualizar esteja marcado, manda pelo request uma variável para o
		 * 'exibirManter'.
		 * Nele verifica se irá para o atualizar ou manter.
		 */
		if(form.getAtualizar() != null && form.getAtualizar().equalsIgnoreCase("1")){
			httpServletRequest.setAttribute("atualizar", form.getAtualizar());

		}

		// Manda o filtro pelo request para o ExibirManterProgramaCobrancaAction
		httpServletRequest.setAttribute("filtroPrograma", filtro); // ?
		sessao.setAttribute("filtroPrograma", filtro);

		return retorno;

	}

	/**
	 * Método utilizado para adicionar uma data inicial no filtro
	 * 
	 * @param data
	 *            Data a ser setada
	 * @param campo
	 *            Campo ao qual a data se refere.
	 * @param filtro
	 *            Filtro no qual será adicionado o parâmetro da data.
	 */
	private void adicionarDataInicialFiltro(String data, String campo, FiltroProgramaCobranca filtro){

		if(data != null && !data.trim().equalsIgnoreCase("")){
			SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
			Date dataInicial = null;

			try{
				dataInicial = formatoData.parse(data);
			}catch(ParseException e){
				dataInicial = null;
			}

			filtro.adicionarParametro(new MaiorQue(campo, dataInicial));
			peloMenosUmParametroInformado = true;
		}
	}

	/**
	 * Método utilizado para adicionar uma data final no filtro
	 * 
	 * @param data
	 *            Data a ser setada
	 * @param campo
	 *            Campo ao qual a data se refere.
	 * @param filtro
	 *            Filtro no qual será adicionado o parâmetro data.
	 */
	private void adicionarDataFinalFiltro(String data, String campo, FiltroProgramaCobranca filtro){

		if(data != null && !data.trim().equalsIgnoreCase("")){
			SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
			Date dataFinal = null;

			try{
				dataFinal = formatoData.parse(data);
			}catch(ParseException e){
				dataFinal = null;
			}

			filtro.adicionarParametro(new MenorQue(campo, dataFinal));
			peloMenosUmParametroInformado = true;
		}
	}

}
