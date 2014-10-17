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

package gcom.gui.relatorio.faturamento;

import gcom.cadastro.localidade.*;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Pré-processamento da primeira página de [UC3048] Gerar Relatorio Inclusoes Cancelamentos de
 * Faturamento Original
 * 
 * @author Ricardo Rodrigues.
 */
public class ExibirGerarRelatorioAction
				extends GcomAction {

	private static final String RESUMO_ARRECADACAO = "RelatorioResumoDeArrecadacao.rpt";

	private static final String RESUMO_FATURAMENTO = "RelatorioResumoDeFaturamento.rpt";

	private static final String INCLUSAO_CANCELAMENTO_FATURAMENTO = "RelatorioDeCancelamentosInclusoesFaturamento.rpt";

	private static final String RELATORIO_CONCILIACAO_CONTABIL = "RelatorioConciliacaoContabil.rpt";

	private static final String RELATORIO_RESUMO_RECEBIMENTO_ARRECADADOR = "RelatorioResumoRecebimentoArrecadador.rpt";

	private static final String RELATORIO_RESUMO_RECEBIMENTO_FORA_PRAZO_CONTRATUAL = "RelatorioResumoRecebimentoForaPrazoContratual.rpt";

	private static final String RELATORIO_CONTA_RECEBER_CONTABIL = "RelatorioContasReceberContabil.rpt";

	private static final String SIM = "1";

	private static final String NAO = "2";

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		httpServletRequest.setAttribute("limparTela", "s");

		ActionForward retorno = actionMapping.findForward("exibirGerarRelatorioAction");

		String tipoRelatorio = (String) httpServletRequest.getParameter("tipoRelatorio");

		// Indicadores criado para desativar cada tipo de totalizador na tela.
		// Caso queira a tela apenas com o campo de referencia deve-se destivar o
		// indicadorMostraTotalizadores
		Integer indicadorMostraTotalizadores = ConstantesSistema.SIM.intValue();

		Integer indicadorMostraTotalizadorEstado = ConstantesSistema.SIM.intValue();
		Integer indicadorMostraTotalizadorEstadoGerencia = ConstantesSistema.SIM.intValue();
		Integer indicadorMostraTotalizadorEstadoUnidadeNegocio = ConstantesSistema.SIM.intValue();
		Integer indicadorMostraTotalizadorEstadoLocalidade = ConstantesSistema.SIM.intValue();
		Integer indicadorMostraTotalizadorGerenciaRegional = ConstantesSistema.SIM.intValue();
		Integer indicadorMostraTotalizadorUnidadeNegocio = ConstantesSistema.SIM.intValue();
		Integer indicadorMostraTotalizadorGerenciaLocalidade = ConstantesSistema.SIM.intValue();
		Integer indicadorMostraTotalizadorLocalidade = ConstantesSistema.SIM.intValue();

		// String que será exibida como label do campo AMReferencia
		String descricaoAMReferencia = "";

		if(tipoRelatorio != null && tipoRelatorio.equals("resumoArrecadacao")){
			httpServletRequest.setAttribute("relatorio", RESUMO_ARRECADACAO);
			httpServletRequest.setAttribute("tituloRelatorio", "Relatório de Resumo de Arrecadação ou Cobrança");
			httpServletRequest.setAttribute("ehRelatorioBatch", SIM);
			descricaoAMReferencia = "Mês/Ano do Faturamento";
		}else if(tipoRelatorio != null && tipoRelatorio.equals("resumoFaturamento")){
			httpServletRequest.setAttribute("relatorio", RESUMO_FATURAMENTO);
			httpServletRequest.setAttribute("tituloRelatorio", "Relatório de Resumo de Faturamento");
			httpServletRequest.setAttribute("ehRelatorioBatch", SIM);
			descricaoAMReferencia = "Mês/Ano do Faturamento";
		}else if(tipoRelatorio != null && tipoRelatorio.equals("inclusaoCancelamentoFaturamento")){
			httpServletRequest.setAttribute("relatorio", INCLUSAO_CANCELAMENTO_FATURAMENTO);
			httpServletRequest.setAttribute("tituloRelatorio", "Relatório de Inclusões de Cancelamentos ao Faturamento Original");
			httpServletRequest.setAttribute("ehRelatorioBatch", SIM);
			descricaoAMReferencia = "Mês/Ano do Faturamento";
		}else if(tipoRelatorio != null && tipoRelatorio.equals("conciliacaoContabil")){
			httpServletRequest.setAttribute("relatorio", RELATORIO_CONCILIACAO_CONTABIL);
			httpServletRequest.setAttribute("tituloRelatorio", "Relatório para Conciliação Contábil");
			httpServletRequest.setAttribute("ehRelatorioBatch", SIM);
			indicadorMostraTotalizadorEstadoUnidadeNegocio = ConstantesSistema.NAO.intValue();
			indicadorMostraTotalizadorGerenciaRegional = ConstantesSistema.NAO.intValue();
			indicadorMostraTotalizadorUnidadeNegocio = ConstantesSistema.NAO.intValue();
			indicadorMostraTotalizadorGerenciaLocalidade = ConstantesSistema.NAO.intValue();
			indicadorMostraTotalizadorLocalidade = ConstantesSistema.NAO.intValue();
			descricaoAMReferencia = "Mês/Ano Contábil";

		}else if(tipoRelatorio != null && tipoRelatorio.equals("resumoRecebimentoArrecadador")){
			httpServletRequest.setAttribute("relatorio", RELATORIO_RESUMO_RECEBIMENTO_ARRECADADOR);
			httpServletRequest.setAttribute("tituloRelatorio", "Relatório Resumo de Recebimento por Arrecadador");
			httpServletRequest.setAttribute("ehRelatorioBatch", SIM);
			indicadorMostraTotalizadores = ConstantesSistema.NAO.intValue();
			descricaoAMReferencia = "Mês/Ano da Arrecadação";
		}else if(tipoRelatorio != null && tipoRelatorio.equals("recebimentoForaPrazoContratual")){
			httpServletRequest.setAttribute("relatorio", RELATORIO_RESUMO_RECEBIMENTO_FORA_PRAZO_CONTRATUAL);
			httpServletRequest.setAttribute("tituloRelatorio", "Relatório Resumo de Recebimento Fora do Prazo Contratual");
			httpServletRequest.setAttribute("ehRelatorioBatch", SIM);

			indicadorMostraTotalizadores = ConstantesSistema.NAO.intValue();
			descricaoAMReferencia = "Mês/Ano da Arrecadação";
		}else if(tipoRelatorio != null && tipoRelatorio.equals("contasReceberContabil")){

			httpServletRequest.setAttribute("relatorio", RELATORIO_CONTA_RECEBER_CONTABIL);
			httpServletRequest.setAttribute("tituloRelatorio", "Relatório de Contas a Receber Contábil");
			httpServletRequest.setAttribute("ehRelatorioBatch", SIM);
			indicadorMostraTotalizadorEstadoUnidadeNegocio = ConstantesSistema.NAO.intValue();
			indicadorMostraTotalizadorGerenciaRegional = ConstantesSistema.NAO.intValue();
			indicadorMostraTotalizadorUnidadeNegocio = ConstantesSistema.NAO.intValue();
			indicadorMostraTotalizadorGerenciaLocalidade = ConstantesSistema.NAO.intValue();
			indicadorMostraTotalizadorLocalidade = ConstantesSistema.NAO.intValue();
			descricaoAMReferencia = "Mês/Ano Contábil";
		}

		httpServletRequest.setAttribute("indicadorMostraTotalizadorEstado", indicadorMostraTotalizadorEstado);
		httpServletRequest.setAttribute("indicadorMostraTotalizadorEstadoGerencia", indicadorMostraTotalizadorEstadoGerencia);
		httpServletRequest.setAttribute("indicadorMostraTotalizadorEstadoUnidadeNegocio", indicadorMostraTotalizadorEstadoUnidadeNegocio);
		httpServletRequest.setAttribute("indicadorMostraTotalizadorEstadoLocalidade", indicadorMostraTotalizadorEstadoLocalidade);
		httpServletRequest.setAttribute("indicadorMostraTotalizadorGerenciaRegional", indicadorMostraTotalizadorGerenciaRegional);
		httpServletRequest.setAttribute("indicadorMostraTotalizadorUnidadeNegocio", indicadorMostraTotalizadorUnidadeNegocio);
		httpServletRequest.setAttribute("indicadorMostraTotalizadorGerenciaLocalidade", indicadorMostraTotalizadorGerenciaLocalidade);
		httpServletRequest.setAttribute("indicadorMostraTotalizadorLocalidade", indicadorMostraTotalizadorLocalidade);

		httpServletRequest.setAttribute("indicadorMostraTotalizadores", indicadorMostraTotalizadores);

		httpServletRequest.getSession().setAttribute("descricaoAMReferencia", descricaoAMReferencia);

		httpServletRequest.setAttribute("relatorioTipoReloadPage", tipoRelatorio);


		// Pesquisa as Gerencias Regionais
		this.pesquisarGerenciasRegionais(httpServletRequest);

		// Pesquisa as Unidades de Negócios
		this.pesquisarUnidadesNegocios(httpServletRequest);

		// Pega os codigos que o usuario digitou para a pesquisa direta de
		// localidade
		String psCodigoLocalidade = httpServletRequest.getParameter("psCodigoLocalidade");
		if(psCodigoLocalidade != null && !psCodigoLocalidade.trim().equals("")){
			pesquisarLocalidade(psCodigoLocalidade, httpServletRequest);
		}

		return retorno;
	}

	/**
	 * Método que pesquisa todas as unidades de negócios e
	 * seta uma coleção com todos os valores para a requisição.
	 * 
	 * @author Ricardo Rodrigues
	 * @date 23/03/2012
	 * @param request
	 */
	private void pesquisarUnidadesNegocios(HttpServletRequest request){

		Fachada fachada = Fachada.getInstancia();

		FiltroUnidadeNegocio filtroUnidadeNegocio = new FiltroUnidadeNegocio();

		Collection<UnidadeNegocio> unidadesNegocios = fachada.pesquisar(filtroUnidadeNegocio, UnidadeNegocio.class.getName());

		request.setAttribute("colecaoUnidadeNegocio", unidadesNegocios);

	}

	/**
	 * Método que pesquisa todas as gerências regionais e
	 * seta uma coleção com todos os valores para a requisição.
	 * 
	 * @author Ricardo Rodrigues
	 * @date 23/03/2012
	 * @param request
	 */
	private void pesquisarGerenciasRegionais(HttpServletRequest request){

		Fachada fachada = Fachada.getInstancia();

		FiltroGerenciaRegional filtroGerenciaRegional = new FiltroGerenciaRegional();

		Collection<GerenciaRegional> gerenciasRegionais = fachada.pesquisar(filtroGerenciaRegional, GerenciaRegional.class.getName());

		request.setAttribute("colecaoGerenciaRegional", gerenciasRegionais);

	}

	/**
	 * Pesquisa uma localidade informada e prepara os dados para exibição na
	 * tela
	 * 
	 * @author Ricardo Rodrigues
	 * @date 23/03/2012
	 * @param idLocalidade
	 *            , httpServletRequest
	 */
	private void pesquisarLocalidade(String idLocalidade, HttpServletRequest httpServletRequest){

		Fachada fachada = Fachada.getInstancia();

		// Pesquisa a localidade na base
		FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
		filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, idLocalidade));

		Collection<Localidade> localidadePesquisada = fachada.pesquisar(filtroLocalidade, Localidade.class.getName());

		// Se nenhuma localidade for encontrada a mensagem é enviada para a
		// página
		if(localidadePesquisada == null || localidadePesquisada.isEmpty()){
			// [FS0001 - Verificar existência de dados]
			httpServletRequest.setAttribute("limparTela", "n");
			httpServletRequest.setAttribute("psCodigoLocalidade", "");
			httpServletRequest.setAttribute("descricaoLocalidade", "Localidade Inexistente".toUpperCase());
		}

		// obtem o imovel pesquisado
		if(localidadePesquisada != null && !localidadePesquisada.isEmpty()){
			Localidade localidade = localidadePesquisada.iterator().next();
			// Manda a Localidade pelo request
			httpServletRequest.setAttribute("limparTela", "n");
			httpServletRequest.setAttribute("psCodigoLocalidade", idLocalidade);
			httpServletRequest.setAttribute("descricaoLocalidade", localidade.getDescricao());
		}

	}
}
