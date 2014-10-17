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
 * Pr�-processamento da primeira p�gina de [UC3048] Gerar Relatorio Inclusoes Cancelamentos de
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

		// String que ser� exibida como label do campo AMReferencia
		String descricaoAMReferencia = "";

		if(tipoRelatorio != null && tipoRelatorio.equals("resumoArrecadacao")){
			httpServletRequest.setAttribute("relatorio", RESUMO_ARRECADACAO);
			httpServletRequest.setAttribute("tituloRelatorio", "Relat�rio de Resumo de Arrecada��o ou Cobran�a");
			httpServletRequest.setAttribute("ehRelatorioBatch", SIM);
			descricaoAMReferencia = "M�s/Ano do Faturamento";
		}else if(tipoRelatorio != null && tipoRelatorio.equals("resumoFaturamento")){
			httpServletRequest.setAttribute("relatorio", RESUMO_FATURAMENTO);
			httpServletRequest.setAttribute("tituloRelatorio", "Relat�rio de Resumo de Faturamento");
			httpServletRequest.setAttribute("ehRelatorioBatch", SIM);
			descricaoAMReferencia = "M�s/Ano do Faturamento";
		}else if(tipoRelatorio != null && tipoRelatorio.equals("inclusaoCancelamentoFaturamento")){
			httpServletRequest.setAttribute("relatorio", INCLUSAO_CANCELAMENTO_FATURAMENTO);
			httpServletRequest.setAttribute("tituloRelatorio", "Relat�rio de Inclus�es de Cancelamentos ao Faturamento Original");
			httpServletRequest.setAttribute("ehRelatorioBatch", SIM);
			descricaoAMReferencia = "M�s/Ano do Faturamento";
		}else if(tipoRelatorio != null && tipoRelatorio.equals("conciliacaoContabil")){
			httpServletRequest.setAttribute("relatorio", RELATORIO_CONCILIACAO_CONTABIL);
			httpServletRequest.setAttribute("tituloRelatorio", "Relat�rio para Concilia��o Cont�bil");
			httpServletRequest.setAttribute("ehRelatorioBatch", SIM);
			indicadorMostraTotalizadorEstadoUnidadeNegocio = ConstantesSistema.NAO.intValue();
			indicadorMostraTotalizadorGerenciaRegional = ConstantesSistema.NAO.intValue();
			indicadorMostraTotalizadorUnidadeNegocio = ConstantesSistema.NAO.intValue();
			indicadorMostraTotalizadorGerenciaLocalidade = ConstantesSistema.NAO.intValue();
			indicadorMostraTotalizadorLocalidade = ConstantesSistema.NAO.intValue();
			descricaoAMReferencia = "M�s/Ano Cont�bil";

		}else if(tipoRelatorio != null && tipoRelatorio.equals("resumoRecebimentoArrecadador")){
			httpServletRequest.setAttribute("relatorio", RELATORIO_RESUMO_RECEBIMENTO_ARRECADADOR);
			httpServletRequest.setAttribute("tituloRelatorio", "Relat�rio Resumo de Recebimento por Arrecadador");
			httpServletRequest.setAttribute("ehRelatorioBatch", SIM);
			indicadorMostraTotalizadores = ConstantesSistema.NAO.intValue();
			descricaoAMReferencia = "M�s/Ano da Arrecada��o";
		}else if(tipoRelatorio != null && tipoRelatorio.equals("recebimentoForaPrazoContratual")){
			httpServletRequest.setAttribute("relatorio", RELATORIO_RESUMO_RECEBIMENTO_FORA_PRAZO_CONTRATUAL);
			httpServletRequest.setAttribute("tituloRelatorio", "Relat�rio Resumo de Recebimento Fora do Prazo Contratual");
			httpServletRequest.setAttribute("ehRelatorioBatch", SIM);

			indicadorMostraTotalizadores = ConstantesSistema.NAO.intValue();
			descricaoAMReferencia = "M�s/Ano da Arrecada��o";
		}else if(tipoRelatorio != null && tipoRelatorio.equals("contasReceberContabil")){

			httpServletRequest.setAttribute("relatorio", RELATORIO_CONTA_RECEBER_CONTABIL);
			httpServletRequest.setAttribute("tituloRelatorio", "Relat�rio de Contas a Receber Cont�bil");
			httpServletRequest.setAttribute("ehRelatorioBatch", SIM);
			indicadorMostraTotalizadorEstadoUnidadeNegocio = ConstantesSistema.NAO.intValue();
			indicadorMostraTotalizadorGerenciaRegional = ConstantesSistema.NAO.intValue();
			indicadorMostraTotalizadorUnidadeNegocio = ConstantesSistema.NAO.intValue();
			indicadorMostraTotalizadorGerenciaLocalidade = ConstantesSistema.NAO.intValue();
			indicadorMostraTotalizadorLocalidade = ConstantesSistema.NAO.intValue();
			descricaoAMReferencia = "M�s/Ano Cont�bil";
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

		// Pesquisa as Unidades de Neg�cios
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
	 * M�todo que pesquisa todas as unidades de neg�cios e
	 * seta uma cole��o com todos os valores para a requisi��o.
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
	 * M�todo que pesquisa todas as ger�ncias regionais e
	 * seta uma cole��o com todos os valores para a requisi��o.
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
	 * Pesquisa uma localidade informada e prepara os dados para exibi��o na
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

		// Se nenhuma localidade for encontrada a mensagem � enviada para a
		// p�gina
		if(localidadePesquisada == null || localidadePesquisada.isEmpty()){
			// [FS0001 - Verificar exist�ncia de dados]
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
