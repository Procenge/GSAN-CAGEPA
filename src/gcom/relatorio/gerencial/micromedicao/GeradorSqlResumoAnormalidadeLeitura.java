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

package gcom.relatorio.gerencial.micromedicao;

import gcom.gerencial.bean.InformarDadosGeracaoRelatorioConsultaHelper;
import gcom.gerencial.micromedicao.ResumoAnormalidadeConsultaHelper;
import gcom.util.ConstantesSistema;
import gcom.util.GeradorSqlRelatorio;
import gcom.util.Util;

public class GeradorSqlResumoAnormalidadeLeitura {

	private String sqlTemplate = "select #CAMPO# as campo, #NOME_CAMPO_FIXO# from #TABELA# where #CONDICIONAL_FIXO# #CONDICIONAL_RESUMO# #CONDICIONAL_RESTRICAO# group by #CAMPO#, #GROUP_BY# #ORDER_BY#";

	private String sqlTemplate2 = "select #CAMPO# as campo, #NOME_CAMPO_FIXO# from #TABELA# where #CONDICIONAL_FIXO# #CONDICIONAL_RESUMO# #CONDICIONAL_RESTRICAO# group by #GROUP_BY# #ORDER_BY#";

	private String sqlTemplateTotalSemAnormalidade = "select COUNT(*) from #TABELA# where #CONDICIONAL_FIXO# #CONDICIONAL_RESUMO# AND #CONDICIONAL_RESTRICAO#";

	private String nomeCampoFixo = "";

	private String condicionalFixo = "";

	private String condicionalResumo = "";

	private String tabela = "";

	private String condicionalRestricao = "";

	private String orderBy = "";

	private String groupBy = "";

	private Boolean classificarGrupoFaturamento = false;

	private Boolean classificarGerencia = false;

	private Boolean classificarElo = false;

	private Boolean classificarUnidadeNegocio = false;

	private Boolean classificarLocalidade = false;

	private Boolean classificarSetor = false;

	private Boolean classificarQuadra = false;

	// SEM ANORMALIDADE -- INICIO
	/**
	 * Construtor usado para criar o SQL de obter o total resumo sem anormalidade a partir dos dados
	 * do bean e filtro tela.
	 */
	public GeradorSqlResumoAnormalidadeLeitura(InformarDadosGeracaoRelatorioConsultaHelper dadosFiltroHelper,
												ResumoAnormalidadeConsultaHelper itemAnterior) {

		int opcaoTotalizacao = dadosFiltroHelper.getOpcaoTotalizacao();

		tabela = montarTabelaFixoSemAnormalidade(opcaoTotalizacao);

		this.condicionalResumo = "  re." + getNomeCampoFiltro() + " = " + itemAnterior.getIdMedicaoTipo();

		// Definindo where
		String condicionaisAdcionais = obtercondicionais(dadosFiltroHelper);
		this.condicionalFixo = montarLigacoesFixasSemAnormalidade(opcaoTotalizacao) + condicionaisAdcionais;

		this.condicionalRestricao = "re." + getNomeCampoMesAnoReferencia() + " = " + dadosFiltroHelper.getAnoMesReferencia();

	}

	private String obtercondicionais(InformarDadosGeracaoRelatorioConsultaHelper dadosFiltroHelper){

		String condicionaisAdcionais = GeradorSqlRelatorio.condicionaisListas(dadosFiltroHelper);
		if(!Util.isVazioOuBranco(condicionaisAdcionais) && condicionaisAdcionais.trim().toUpperCase().startsWith("AND")){
			condicionaisAdcionais = condicionaisAdcionais.trim();
			condicionaisAdcionais = condicionaisAdcionais.substring(3); // REMOVE O AND DO INÍCIO.
			condicionaisAdcionais = condicionaisAdcionais + "AND";
		}
		return condicionaisAdcionais;
	}

	private String montarLigacoesFixasSemAnormalidade(Integer opcaoTotalizacao){

		StringBuffer buffer = new StringBuffer();
		buffer.append(" re." + getNomeCampoAnormalidadeId() + " is null  and ");
		buffer.append("re.catg_id = cat.catg_id and ");
		buffer.append("re.greg_id = gr.greg_id and ");
		buffer.append("re.loca_id = l.loca_id and ");
		buffer.append("re.qdra_id = q.qdra_id and ");
		buffer.append("re.stcm_id = sc.stcm_id  and ");
		buffer.append("re.loca_cdelo = elo.loca_id and ");
		buffer.append("re.uneg_id = uni.uneg_id and ");
		if(ConstantesSistema.CODIGO_ESTADO_GRUPO_FATURAMENTO == opcaoTotalizacao.intValue()
						|| ConstantesSistema.CODIGO_GRUPO_FATURAMENTO == opcaoTotalizacao.intValue()){
			buffer.append("re.rota_id = rt.rota_id and");
		}
		return buffer.toString();
	}

	public String montarComandoSQLResumoSemAnormalidade(Integer opcaoTotalizacao, RelatorioResumoAnormalidadeLeituraBean bean,
					InformarDadosGeracaoRelatorioConsultaHelper dadosParametrosConsulta){

		String sql = "";

		String ands = definirCampoCondicaoDeQuebra(opcaoTotalizacao, bean, dadosParametrosConsulta);

		if(!"".equals(ands)){
			condicionalResumo = ands + " AND " + condicionalResumo;
		}

		sql = sqlTemplateTotalSemAnormalidade.replaceAll("#TABELA#", tabela)//
						.replaceAll("#CONDICIONAL_FIXO#", condicionalFixo)//
						.replaceAll("#CONDICIONAL_RESUMO#", condicionalResumo)//
						.replaceAll("#CONDICIONAL_RESTRICAO#", condicionalRestricao);

		return sql;

	}

	private String montarTabelaFixoSemAnormalidade(Integer opcaoTotalizacao){

		StringBuffer buffer = new StringBuffer();
		buffer.append(getNomeTabelaResumoAnormalidade() + " re, ");
		buffer.append("gerencia_regional gr, ");
		buffer.append("localidade l , ");
		buffer.append("setor_comercial sc, ");
		buffer.append("quadra q, ");
		buffer.append("categoria cat, ");
		buffer.append("localidade elo, ");
		buffer.append("unidade_negocio uni ");
		if(ConstantesSistema.CODIGO_ESTADO_GRUPO_FATURAMENTO == opcaoTotalizacao.intValue()
						|| ConstantesSistema.CODIGO_GRUPO_FATURAMENTO == opcaoTotalizacao.intValue()){
			buffer.append(", rota rt ");
		}
		return buffer.toString();
	}

	private String definirCampoCondicaoDeQuebra(int opcaoTotalizacao, RelatorioResumoAnormalidadeLeituraBean bean,
					InformarDadosGeracaoRelatorioConsultaHelper dadosParametrosConsulta){

		String clausulaWhere = "";
		switch(opcaoTotalizacao){
			case ConstantesSistema.CODIGO_ESTADO:
				break;
			case ConstantesSistema.CODIGO_ELO_POLO:
			case ConstantesSistema.CODIGO_ESTADO_ELO_POLO:
				clausulaWhere = "re.loca_cdelo  = " + bean.getIdCampoQuebra();
				break;
			case ConstantesSistema.CODIGO_GERENCIA_REGIONAL:
			case ConstantesSistema.CODIGO_ESTADO_GERENCIA_REGIONAL:
				clausulaWhere = "re.greg_id  = " + bean.getIdCampoQuebra();
				break;
			case ConstantesSistema.CODIGO_GRUPO_FATURAMENTO:
			case ConstantesSistema.CODIGO_ESTADO_GRUPO_FATURAMENTO:
				clausulaWhere = "re.rota_id  = " + bean.getIdCampoQuebra();
				break;
			case ConstantesSistema.CODIGO_LOCALIDADE:
			case ConstantesSistema.CODIGO_ESTADO_LOCALIDADE:
				clausulaWhere = "re.loca_id  = " + bean.getIdCampoQuebra();
				break;
			case ConstantesSistema.CODIGO_GERENCIA_REGIONAL_ELO_POLO:
				clausulaWhere = "re.greg_id =" + dadosParametrosConsulta.getGerenciaRegional().getId() + " AND re.loca_cdelo  = "
								+ bean.getIdCampoQuebra();
				break;
			case ConstantesSistema.CODIGO_GERENCIA_REGIONAL_LOCALIDADE:
				clausulaWhere = "re.greg_id =" + dadosParametrosConsulta.getGerenciaRegional().getId() + " AND re.loca_id  = "
								+ bean.getIdCampoQuebra();
				break;
			case ConstantesSistema.CODIGO_ELO_POLO_LOCALIDADE:
				clausulaWhere = "re.loca_cdelo  = " + dadosParametrosConsulta.getEloPolo().getId() + " AND re.loca_id  = "
								+ bean.getIdCampoQuebra();
				break;
			case ConstantesSistema.CODIGO_ELO_POLO_SETOR_COMERCIAL:
				clausulaWhere = "re.loca_cdelo  = " + dadosParametrosConsulta.getEloPolo().getId() + " AND re.stcm_id = "
								+ bean.getIdCampoQuebra();
				break;
			case ConstantesSistema.CODIGO_LOCALIDADE_QUADRA:
				clausulaWhere = "re.loca_id = " + dadosParametrosConsulta.getLocalidade().getId() + " AND  re.qdra_id = "
								+ bean.getIdCampoQuebra();
				break;
			case ConstantesSistema.CODIGO_LOCALIDADE_SETOR_COMERCIAL:
				clausulaWhere = "re.loca_id = " + dadosParametrosConsulta.getLocalidade().getId() + " AND re.stcm_id = "
								+ bean.getIdCampoQuebra();
				break;
			case ConstantesSistema.CODIGO_SETOR_COMERCIAL:
				clausulaWhere = "re.loca_id = " + dadosParametrosConsulta.getLocalidade().getId() + " AND re.stcm_id = "
								+ bean.getIdCampoQuebra();
				break;
			case ConstantesSistema.CODIGO_QUADRA:
			case ConstantesSistema.CODIGO_SETOR_COMERCIAL_QUADRA:
				clausulaWhere = "re.loca_id = " + dadosParametrosConsulta.getLocalidade().getId() + " AND re.stcm_id = " //
								+ dadosParametrosConsulta.getSetorComercial().getId() + " AND  re.qdra_id = " + bean.getIdCampoQuebra();
				break;
			case ConstantesSistema.CODIGO_ESTADO_UNIDADE_NEGOCIO:
			case ConstantesSistema.CODIGO_UNIDADE_NEGOCIO:
				clausulaWhere = "re.uneg_id  = " + bean.getIdCampoQuebra();
				break;
			case ConstantesSistema.CODIGO_UNIDADE_NEGOCIO_ELO_POLO:
				clausulaWhere = "re.uneg_id =" + dadosParametrosConsulta.getUnidadeNegocio().getId() + " AND re.loca_cdelo  = "
								+ bean.getIdCampoQuebra();
				break;
			case ConstantesSistema.CODIGO_UNIDADE_NEGOCIO_LOCALIDADE:
				clausulaWhere = "re.uneg_id =" + dadosParametrosConsulta.getUnidadeNegocio().getId() + " AND re.loca_id  = "
								+ bean.getIdCampoQuebra();
				break;
			default:
				clausulaWhere = "ERRO!!!";
				break;
		}

		return clausulaWhere;

	}

	// SEM ANORMALIDADE -- FIM

	/**
	 * Construtor default.
	 */
	public GeradorSqlResumoAnormalidadeLeitura(InformarDadosGeracaoRelatorioConsultaHelper dadosFiltroHelper) {

		int opcaoTotalizacao = dadosFiltroHelper.getOpcaoTotalizacao();

		// Define os tipos de totalizadores a ser add na query
		definirAgrupamento(opcaoTotalizacao);

		// Definindo GroupBy
		groupBy = montarCamposGroupBy();

		// Definindo FROM
		tabela = montarTabelaFixoAnormalidade(opcaoTotalizacao);

		// Definição de campos de consulta.
		this.nomeCampoFixo = montarCamposFixos();

		// Where
		this.condicionalFixo = obtercondicionais(dadosFiltroHelper);
		this.condicionalResumo = montarCondicionais(dadosFiltroHelper);

		// Definindo Ordenação
		orderBy = montarCamposOrdenacao(opcaoTotalizacao);
	}

	private void definirAgrupamento(int opcaoTotalizacao){

		switch(opcaoTotalizacao){
			case ConstantesSistema.CODIGO_ELO_POLO:
				classificarElo = true;
				break;
			case ConstantesSistema.CODIGO_ELO_POLO_LOCALIDADE:
				classificarLocalidade = true;
				classificarElo = true;
				break;
			case ConstantesSistema.CODIGO_ELO_POLO_SETOR_COMERCIAL:
				classificarElo = true;
				classificarSetor = true;
				break;
			case ConstantesSistema.CODIGO_ESTADO_ELO_POLO:
				classificarElo = true;
				break;
			case ConstantesSistema.CODIGO_ESTADO_GERENCIA_REGIONAL:
				classificarGerencia = true;
				break;
			case ConstantesSistema.CODIGO_GRUPO_FATURAMENTO:
			case ConstantesSistema.CODIGO_ESTADO_GRUPO_FATURAMENTO:
				classificarGrupoFaturamento = true;
				break;
			case ConstantesSistema.CODIGO_LOCALIDADE:
			case ConstantesSistema.CODIGO_ESTADO_LOCALIDADE:
				classificarLocalidade = true;
				break;
			case ConstantesSistema.CODIGO_GERENCIA_REGIONAL:
				classificarGerencia = true;
				break;
			case ConstantesSistema.CODIGO_GERENCIA_REGIONAL_ELO_POLO:
				classificarGerencia = true;
				classificarElo = true;
				break;
			case ConstantesSistema.CODIGO_GERENCIA_REGIONAL_LOCALIDADE:
				classificarGerencia = true;
				classificarLocalidade = true;
				break;
			case ConstantesSistema.CODIGO_LOCALIDADE_QUADRA:
				classificarLocalidade = true;
				classificarQuadra = true;
				break;
			case ConstantesSistema.CODIGO_LOCALIDADE_SETOR_COMERCIAL:
				classificarLocalidade = true;
				classificarSetor = true;
				break;
			case ConstantesSistema.CODIGO_QUADRA:
				classificarQuadra = true;
				break;
			case ConstantesSistema.CODIGO_SETOR_COMERCIAL:
				classificarSetor = true;
				break;
			case ConstantesSistema.CODIGO_SETOR_COMERCIAL_QUADRA:
				classificarSetor = true;
				classificarQuadra = true;
				break;
			case ConstantesSistema.CODIGO_ESTADO_UNIDADE_NEGOCIO:
			case ConstantesSistema.CODIGO_UNIDADE_NEGOCIO:
				classificarUnidadeNegocio = true;
				break;
			case ConstantesSistema.CODIGO_UNIDADE_NEGOCIO_ELO_POLO:
				classificarUnidadeNegocio = true;
				classificarElo = true;
				break;
			case ConstantesSistema.CODIGO_UNIDADE_NEGOCIO_LOCALIDADE:
				classificarUnidadeNegocio = true;
				classificarLocalidade = true;
				break;
		}
	}

	private String montarCondicionais(InformarDadosGeracaoRelatorioConsultaHelper dadosFiltroHelper){

		StringBuffer buffer = new StringBuffer();
		if(!Util.isVazioOuBrancoOuZero(dadosFiltroHelper.getAnoMesReferencia())){
			buffer.append("re." + getNomeCampoMesAnoReferencia() + " = ");
			buffer.append(dadosFiltroHelper.getAnoMesReferencia());
		}
		if(dadosFiltroHelper.getFaturamentoGrupo() != null){
			if(buffer.length() > 0){
				buffer.append(" AND ");
			}
			buffer.append("rt.FTGR_ID = ");
			buffer.append(dadosFiltroHelper.getFaturamentoGrupo().getId());
		}
		if(dadosFiltroHelper.getGerenciaRegional() != null){
			if(buffer.length() > 0){
				buffer.append(" AND ");
			}
			buffer.append("re.GREG_ID = ");
			buffer.append(dadosFiltroHelper.getGerenciaRegional().getId());
		}
		if(dadosFiltroHelper.getLocalidade() != null){
			if(buffer.length() > 0){
				buffer.append(" AND ");
			}
			buffer.append("re.LOCA_ID = ");
			buffer.append(dadosFiltroHelper.getLocalidade().getId());
		}
		if(dadosFiltroHelper.getSetorComercial() != null){
			if(buffer.length() > 0){
				buffer.append(" AND ");
			}
			buffer.append("re.STCM_ID = ");
			buffer.append(dadosFiltroHelper.getSetorComercial().getId());
		}
		if(dadosFiltroHelper.getQuadra() != null){
			if(buffer.length() > 0){
				buffer.append(" AND ");
			}
			buffer.append("re.QDRA_ID = ");
			buffer.append(dadosFiltroHelper.getQuadra().getId());
		}
		if(dadosFiltroHelper.getEloPolo() != null){
			if(buffer.length() > 0){
				buffer.append(" AND ");
			}
			buffer.append("re.LOCA_CDELO = ");
			buffer.append(dadosFiltroHelper.getEloPolo().getId());
		}
		if(dadosFiltroHelper.getUnidadeNegocio() != null){
			if(buffer.length() > 0){
				buffer.append(" AND ");
			}
			buffer.append("re.UNEG_ID = ");
			buffer.append(dadosFiltroHelper.getUnidadeNegocio().getId());
		}

		buffer.append(" AND ");
		buffer.append(" la." + getNomeCampoAnormalidadeId() + " is not null ");

		return buffer.toString();
	}

	protected String getNomeCampoMesAnoReferencia(){

		return "REAL_AMREFERENCIA";
	}

	private String montarCamposGroupBy(){

		StringBuffer buffer = new StringBuffer();

		buffer.append(" la." + getNomeCampoAnormalidadeId() + ", ");
		buffer.append(" la." + getNomeCampoAnormalidadeDescricao() + " ");

		if(classificarGerencia){
			buffer.append(", re.greg_id ");
			buffer.append(", gr.greg_nmregional ");
		}else{
			// buffer.append("'0', '0', ");
		}

		if(classificarLocalidade){
			buffer.append(", re.loca_id ");
			buffer.append(", l.loca_nmlocalidade ");
		}else{
			// buffer.append("'0', '0', ");
		}

		if(classificarSetor){
			buffer.append(", re.stcm_id ");
			buffer.append(", sc.stcm_nmsetorcomercial ");
		}else{
			// buffer.append("'0', '0', ");
		}

		if(classificarQuadra){
			buffer.append(", re.qdra_id ");
			buffer.append(", q.qdra_nnquadra ");
		}else{
			// buffer.append("'0', '0', ");
		}

		if(classificarElo){
			buffer.append(", re.LOCA_CDELO ");
			buffer.append(", elo.loca_nmlocalidade ");
		}else{
			// buffer.append("'0', '0', ");
		}

		if(classificarUnidadeNegocio){
			buffer.append(", re.UNEG_ID ");
			buffer.append(", uni.UNEG_NMUNIDADENEGOCIO ");
		}else{
			// buffer.append("'0', '0' ");
		}

		return buffer.toString();
	}

	protected String getNomeCampoAnormalidadeDescricao(){

		return "ltan_dsleituraanormalidade";
	}

	protected String getNomeCampoAnormalidadeId(){

		return "ltan_id";
	}

	public String montarComandoSQLResumoAnormalidade(Integer filtroTipo){

		String condicionalSqlAgua = " and re." + getNomeCampoFiltro() + " = " + filtroTipo;

		this.condicionalResumo = condicionalResumo + condicionalSqlAgua;

		String sql = "";

		sql = sqlTemplate.replaceAll("#NOME_CAMPO_FIXO#", nomeCampoFixo)//
						.replaceAll("#TABELA#", tabela)//
						.replaceAll("#CONDICIONAL_FIXO#", condicionalFixo)//
						.replaceAll("#CONDICIONAL_RESUMO#", condicionalResumo)//
						.replaceAll("#CONDICIONAL_RESTRICAO#", condicionalRestricao)//
						.replaceAll("#GROUP_BY#", groupBy)//
						.replaceAll("#ORDER_BY#", "ORDER BY " + orderBy);

		return sql;

	}

	protected String getNomeCampoFiltro(){

		return "medt_id";
	}

	private String montarTabelaFixoAnormalidade(Integer opcaoTotalizacao){

		StringBuffer buffer = new StringBuffer();
		buffer.append(getNomeTabelaResumoAnormalidade() + " re ");
		buffer.append("left join " + getNomeTabelaDominioAnormalidade() + " la on re." + getNomeCampoAnormalidadeId() + " = la."
						+ getNomeCampoAnormalidadeId() + " AND la." + getNomeCampoAnormalidadeId() + " is not null ");
		buffer.append("left join gerencia_regional gr on re.greg_id = gr.greg_id ");
		buffer.append("left join localidade l on re.loca_id = l.loca_id ");
		buffer.append("left join setor_comercial sc on re.stcm_id = sc.stcm_id ");
		buffer.append("left join quadra q on re.qdra_id = q.qdra_id ");
		buffer.append("left join categoria cat on re.catg_id = cat.catg_id ");
		buffer.append("left join localidade elo on re.loca_cdelo = elo.loca_id ");
		buffer.append("left join unidade_negocio uni on re.uneg_id = uni.uneg_id ");
		if(ConstantesSistema.CODIGO_ESTADO_GRUPO_FATURAMENTO == opcaoTotalizacao.intValue()
						|| ConstantesSistema.CODIGO_GRUPO_FATURAMENTO == opcaoTotalizacao.intValue()){
			buffer.append("left join rota rt on re.rota_id = rt.rota_id ");
		}
		return buffer.toString();
	}

	protected String getNomeTabelaDominioAnormalidade(){

		return "leitura_anormalidade";
	}

	protected String getNomeTabelaResumoAnormalidade(){

		return "resumo_anormalidade_leitura";
	}

	private String montarCamposFixos(){

		StringBuffer buffer = new StringBuffer();

		String campoAgrupamento = "'ESTADO'";
		buffer.append(" la." + getNomeCampoAnormalidadeId() + " as codigo,  ");
		buffer.append(" la." + getNomeCampoAnormalidadeDescricao() + " as descricao,  ");
		buffer.append(" sum(re." + getNomeCampoAnomaliaSomatorio() + ")  as quantidade,  ");

		if(classificarGerencia){
			buffer.append("re.greg_id as idGerencia, ");
			buffer.append("gr.greg_nmregional as descricaoGerencia, ");
			campoAgrupamento = "gr.greg_id";
		}else{
			buffer.append("'0' as idGerencia, ");
			buffer.append("'0' as descricaoGerencia, ");
		}

		if(classificarLocalidade){
			buffer.append("re.loca_id as idLocalidade, ");
			buffer.append("l.loca_nmlocalidade as descricaoLocalidade, ");
			campoAgrupamento = "l.loca_id";
		}else{
			buffer.append("'0' as idLocalidade, ");
			buffer.append("'0' as descricaoLocalidade, ");
		}

		if(classificarUnidadeNegocio){
			buffer.append("re.UNEG_ID as idUni, ");
			buffer.append("uni.UNEG_NMUNIDADENEGOCIO as nomeUni, ");
			campoAgrupamento = "uni.UNEG_ID";
		}else{
			buffer.append("'0' as idUni, ");
			buffer.append("'0' as nomeUni, ");
		}

		if(classificarSetor){
			buffer.append("re.stcm_id as idSetorComercial, ");
			buffer.append("sc.stcm_nmsetorcomercial as descricaoSetorComercial, ");
			campoAgrupamento = "sc.stcm_id";
		}else{
			buffer.append("'0' as idSetorComercial, ");
			buffer.append("'0' as descricaoSetorComercial, ");
		}

		if(classificarQuadra){
			buffer.append("q.qdra_nnquadra as idQuadra, ");
			buffer.append("q.qdra_nnquadra as descricaoQuadra, ");
			campoAgrupamento = "q.qdra_nnquadra";
		}else{
			buffer.append("'0' as idQuadra, ");
			buffer.append("'0' as descricaoQuadra, ");
		}

		if(classificarElo){
			buffer.append("re.LOCA_CDELO as idElo, ");
			buffer.append("elo.loca_nmlocalidade as descricaoElo ");
			campoAgrupamento = "elo.LOCA_CDELO";
		}else{
			buffer.append("'0' as idElo, ");
			buffer.append("'0' as descricaoElo ");
		}

		if(classificarGrupoFaturamento){
			buffer.append(", rt.FTGR_ID as idGrupoFaturamento ");
			campoAgrupamento = " rt.FTGR_ID";
		}

		// Ajuste necessário para executar o SQL no PostgreSQL
		if(campoAgrupamento.equals("'ESTADO'")){
			sqlTemplate = sqlTemplate2;
		}

		sqlTemplate = sqlTemplate.replaceAll("#CAMPO#", campoAgrupamento);

		return buffer.toString();
	}

	protected String getNomeCampoAnomaliaSomatorio(){

		return "real_qtdmedicao";
	}

	private String montarCamposOrdenacao(Integer opcaoTotalizacao){

		StringBuffer buffer = new StringBuffer();
		buffer.append("campo, ");
		buffer.append("codigo, ");
		buffer.append("descricao, ");

		buffer.append("idGerencia , ");
		buffer.append("descricaoGerencia , ");
		buffer.append("idLocalidade , ");
		buffer.append("descricaoLocalidade , ");
		buffer.append("idUni, ");
		buffer.append("nomeUni, ");
		buffer.append("idSetorComercial, ");
		buffer.append("descricaoSetorComercial,");
		buffer.append("idQuadra,");
		buffer.append("descricaoQuadra, ");
		buffer.append("idElo, ");
		buffer.append("descricaoElo ");

		if(ConstantesSistema.CODIGO_ESTADO_GRUPO_FATURAMENTO == opcaoTotalizacao.intValue()
						|| ConstantesSistema.CODIGO_GRUPO_FATURAMENTO == opcaoTotalizacao.intValue()){
			buffer.append(", idGrupoFaturamento ");
		}

		return buffer.toString();
	}
}
