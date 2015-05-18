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
 * 
 * GSANPCG
 * Eduardo Henrique
 */
package gcom.util;

import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoSituacao;
import gcom.cadastro.cliente.EsferaPoder;
import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.imovel.ImovelPerfil;
import gcom.gerencial.bean.InformarDadosGeracaoRelatorioConsultaHelper;
import gcom.util.parametrizacao.ExecutorParametro;
import gcom.util.parametrizacao.Parametrizacao;
import gcom.util.parametrizacao.cadastro.ExecutorParametrosCadastro;
import gcom.util.parametrizacao.cadastro.ParametroCadastro;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


/**
 * @author mgrb
 *
 */
public class ResumoLigacoesEconomiaSqlBuilder
				implements Parametrizacao {

	private static final String TOTALIZADOR_CLAUSULA = "##TOTALIZADOR_CLAUSULA##";
	private static final String TOTALIZADOR_DESC = "##TOTALIZADOR_DESC##";
	private static final String TOTALIZADOR_ID = "##TOTALIZADOR_ID##";
	private static final String TOTALIZADOR_TABLE = "##TOTALIZADOR_TABLE##";
	public static final int RESUMO_DETALHE = 1;
	public static final int SUMARIO_LIGACOES = 2;
	private static final String RESUMO_DETALHE_BASE_SQL;
	private static final String SUMARIO_LIGACOES_BASE_SQL;

	public static final int GRUPO_DETALHE_FUNCIONANDO_AGUA = 1;

	public static final int GRUPO_DETALHE_FUNCIONANDO_AGUA_ESGOTO = 2;

	public static final int GRUPO_DETALHE_FUNCIONANDO_ESGOTO = 3;

	public static final int GRUPO_DETALHE_DESLIGADA_AGUA = 4;

	public static final int GRUPO_DETALHE_DESLIGADA_AGUA_ESGOTO = 5;

	public static final int GRUPO_DETALHE_DESLIGADA_ESGOTO = 6;

	static{
		StringBuilder builderDetalhe = new StringBuilder();
		StringBuilder builderSumario = new StringBuilder();
		
		builderDetalhe.append("SELECT ");
		builderDetalhe.append("    ##TOTALIZADOR_ID## TOTALIZADOR_ID, ");
		builderDetalhe.append("    ##TOTALIZADOR_DESC## TOTALIZADOR, ");
		builderDetalhe.append("    ##GRUPO_DETALHE_ID## DETALHE_ID, ");
		builderDetalhe.append("    '##GRUPO_DETALHE_DESC##' DETALHE, ");
		builderDetalhe.append("    c.CATG_ID CATEGORIA_ID, ");
		builderDetalhe.append("    c.CATG_DSCATEGORIA CATEGORIA, ");
		builderDetalhe.append("    SUM(CASE WHEN rle.RELE_ICHIDROMETRO = 1 THEN rle.RELE_QTLIGACOES ELSE 0 END) LIG_COM_HID, ");
		builderDetalhe.append("    SUM(CASE WHEN rle.RELE_ICHIDROMETRO = 2 THEN rle.RELE_QTLIGACOES ELSE 0 END) LIG_SEM_HID, ");
		builderDetalhe.append("    SUM(CASE WHEN rle.RELE_ICHIDROMETRO = 1 THEN rle.RELE_QTECONOMIAS ELSE 0 END) ECO_COM_HID, ");
		builderDetalhe.append("    SUM(CASE WHEN rle.RELE_ICHIDROMETRO = 2 THEN rle.RELE_QTECONOMIAS ELSE 0 END) ECO_SEM_HID ");
		builderDetalhe.append("FROM ");
		builderDetalhe.append("    RESUMO_LIGACOES_ECONOMIA rle ");
		builderDetalhe.append("    INNER JOIN CATEGORIA c ON rle.CATG_ID = c.CATG_ID ");
		builderDetalhe.append("    INNER JOIN LIGACAO_ESGOTO_SITUACAO les ON rle.LEST_ID = les.LEST_ID ");
		builderDetalhe.append("    ##TOTALIZADOR_TABLE## ");
		builderDetalhe.append("WHERE 1=1 ");
		builderDetalhe.append("    ##TOTALIZADOR_CLAUSULA## ");
		builderDetalhe.append("    ##PARAMETROS_FORMULARIO## ");
		builderDetalhe.append("    ##GRUPO_DETALHE## ");
		builderDetalhe.append("GROUP BY ");
		builderDetalhe.append("    ##TOTALIZADOR_ID##, ");
		builderDetalhe.append("    ##TOTALIZADOR_DESC##, ");
		builderDetalhe.append("    ##GRUPO_DETALHE_ID##, ");
		builderDetalhe.append("    '##GRUPO_DETALHE_DESC##', ");
		builderDetalhe.append("    c.CATG_ID, ");
		builderDetalhe.append("    c.CATG_DSCATEGORIA ");
		RESUMO_DETALHE_BASE_SQL = builderDetalhe.toString();
		
		builderSumario.append("SELECT ");
		builderSumario.append("    ##TOTALIZADOR_ID## totalizadorId, ");
		builderSumario.append("    ##TOTALIZADOR_DESC## totalizador, ");
		builderSumario.append("    las.LAST_ID ligacaoAguaSituacaoId, ");
		builderSumario.append("    las.LAST_DSLIGACAOAGUASITUACAO ligacaoAguaSituacao, ");
		builderSumario.append("    c.CATG_ID categoriaId, ");
		builderSumario.append("    c.CATG_DSCATEGORIA categoria, ");
		// builderSumario.append("    SUM(rle.RELE_QTLIGACOES) qtdLigacoes, ");
		builderSumario.append("    CASE WHEN rle.LAST_ID = 2 THEN  ");
		builderSumario.append("    		CASE WHEN (rle.LEST_ID = 1 OR rle.LEST_ID = 2 OR rle.LEST_ID = 6 OR rle.LEST_ID = 9) THEN  ");
		builderSumario.append("    			SUM(rle.RELE_QTLIGACOES) ");
		builderSumario.append("    		ELSE 0 END ");
		builderSumario.append("    ELSE SUM(rle.RELE_QTLIGACOES) END qtdLigacoes, ");
		// builderSumario.append("    SUM(rle.RELE_QTECONOMIAS) qtdEconomias ");
		builderSumario.append("    CASE WHEN rle.LAST_ID = 2 THEN  ");
		builderSumario.append("    		CASE WHEN (rle.LEST_ID = 1 OR rle.LEST_ID = 2 OR rle.LEST_ID = 6 OR rle.LEST_ID = 9) THEN  ");
		builderSumario.append("    			SUM(RELE_QTECONOMIAS) ");
		builderSumario.append("    		ELSE 0 END ");
		builderSumario.append("    ELSE SUM(RELE_QTECONOMIAS) END qtdEconomias ");
		builderSumario.append("FROM ");
		builderSumario.append("    RESUMO_LIGACOES_ECONOMIA rle ");
		builderSumario.append("    INNER JOIN CATEGORIA c ON rle.CATG_ID = c.CATG_ID ");
		builderSumario.append("    INNER JOIN LIGACAO_AGUA_SITUACAO las ON rle.LAST_ID = las.LAST_ID ");
		builderSumario.append("    ##TOTALIZADOR_TABLE## ");
		builderSumario.append("WHERE 1=1 ");
		builderSumario.append("    ##TOTALIZADOR_CLAUSULA## ");
		builderSumario.append("    ##PARAMETROS_FORMULARIO## ");
		builderSumario.append("GROUP BY "); 
		builderSumario.append("    ##TOTALIZADOR_ID##, ");
		builderSumario.append("    ##TOTALIZADOR_DESC##, ");
		builderSumario.append("    las.LAST_ID, ");
		builderSumario.append("    las.LAST_DSLIGACAOAGUASITUACAO, ");
		builderSumario.append("    c.CATG_ID, ");
		builderSumario.append("    c.CATG_DSCATEGORIA, ");
		builderSumario.append("    rle.LAST_ID, ");
		builderSumario.append("    rle.LEST_ID ");
		builderSumario.append("ORDER BY "); 
		builderSumario.append("    totalizadorId, ");
		builderSumario.append("    ligacaoAguaSituacaoId, ");
		builderSumario.append("    categoriaId ");
		SUMARIO_LIGACOES_BASE_SQL = builderSumario.toString();
	}

	private String sqlQuery;

	/**
	 * ResumoLigacoesEconomiaSqlBuilder
	 * <p>
	 * Esse método Constroi uma instância de ResumoLigacoesEconomiaSqlBuilder.
	 * </p>
	 * 
	 * @author Marlos Ribeiro
	 * @param parametrosPesquisa
	 * @param codigoEmpresa
	 * @since 21/11/2012
	 */
	public ResumoLigacoesEconomiaSqlBuilder(int tipoConsulta, InformarDadosGeracaoRelatorioConsultaHelper parametrosPesquisa) {

		String sqlBase = null;
		switch(tipoConsulta){
			case SUMARIO_LIGACOES:
				sqlBase = SUMARIO_LIGACOES_BASE_SQL;
				break;
			case RESUMO_DETALHE:
				sqlBase = construirSQLExtendida(RESUMO_DETALHE_BASE_SQL);
				break;
			default:
				throw new IllegalArgumentException("Tipo de consulta, " + tipoConsulta + ", não implementado.");
		}
		Map<String, String> metaDadosTotalizador = obterMetaDadoTotalizador(parametrosPesquisa);
		sqlBase = sqlBase//
						.replaceAll(TOTALIZADOR_TABLE, metaDadosTotalizador.get(TOTALIZADOR_TABLE))//
						.replaceAll(TOTALIZADOR_ID, metaDadosTotalizador.get(TOTALIZADOR_ID))//
						.replaceAll(TOTALIZADOR_DESC, metaDadosTotalizador.get(TOTALIZADOR_DESC))//
						.replaceAll(TOTALIZADOR_CLAUSULA, metaDadosTotalizador.get(TOTALIZADOR_CLAUSULA))//
						.replaceAll("##PARAMETROS_FORMULARIO##", obterParametrosPesquisa(parametrosPesquisa));
		this.sqlQuery = sqlBase;
	}

	/**
	 * Método construirSQLExtendida
	 * <p>
	 * Esse método implementa construcao do SQL para consulta dos dados de detalhe.
	 * </p>
	 * RASTREIO: [OC897714][UC0269]
	 * 
	 * @param resumoDetalheBaseSql
	 * @return
	 * @author Marlos Ribeiro
	 * @since 21/11/2012
	 */
	private String construirSQLExtendida(String resumoDetalheBaseSql){

		StringBuilder builder = new StringBuilder();
		builder.append("SELECT ");
		builder.append("    TOTALIZADOR_ID totalizadorId, ");
		builder.append("    TOTALIZADOR totalizador, ");
		builder.append("    DETALHE_ID detalheId, ");
		builder.append("    DETALHE detalhe, ");
		builder.append("    CATEGORIA_ID categoriaId, ");
		builder.append("    CATEGORIA categoria, ");
		builder.append("    LIG_COM_HID ligacoesComHidrometro, ");
		builder.append("    LIG_SEM_HID ligacoesSemHidrometro, ");
		builder.append("    ECO_COM_HID economiasComHidrometro, ");
		builder.append("    ECO_SEM_HID economiasSemHidrometro ");
		builder.append("FROM ( ");
		builder.append("( ");
		builder.append(obterSubConsultaDetalheResumoLigacaoEconomia(GRUPO_DETALHE_FUNCIONANDO_AGUA));
		builder.append(" ) UNION ( ");
		builder.append(obterSubConsultaDetalheResumoLigacaoEconomia(GRUPO_DETALHE_FUNCIONANDO_AGUA_ESGOTO));
		builder.append(" ) UNION ( ");
		builder.append(obterSubConsultaDetalheResumoLigacaoEconomia(GRUPO_DETALHE_FUNCIONANDO_ESGOTO));
		builder.append(" ) UNION ( ");
		builder.append(obterSubConsultaDetalheResumoLigacaoEconomia(GRUPO_DETALHE_DESLIGADA_AGUA));
		builder.append(" ) UNION ( ");
		builder.append(obterSubConsultaDetalheResumoLigacaoEconomia(GRUPO_DETALHE_DESLIGADA_AGUA_ESGOTO));
		builder.append(" ) UNION ( ");
		builder.append(obterSubConsultaDetalheResumoLigacaoEconomia(GRUPO_DETALHE_DESLIGADA_ESGOTO));
		builder.append(" ) ");
		builder.append(") DADOS ##PAGINACAO## ORDER BY totalizadorId, detalheId, categoriaId");
		return builder.toString();
	}

	/**
	 * Método obterSubConsultaDetalheResumoLigacaoEconomia
	 * <p>
	 * Esse método implementa construcao das restriçoes para selecao dos grupos de detalhe.
	 * [DESLIGaDA, FUNCIONAMENTO] -> AGUA, ESGOTO, AGUA-ESGOTO
	 * </p>
	 * RASTREIO: [OC897714][UC0269]
	 * 
	 * @param grupoDetalhe
	 * @return
	 * @author Marlos Ribeiro
	 * @since 21/11/2012
	 */
	private Object obterSubConsultaDetalheResumoLigacaoEconomia(int grupoDetalhe){

		Object[] dadosRetorno = new Object[3];
		String clusulaGrupoDetalhe = null;
		Integer grupoDetalheId = null;
		String grupoDetalheDesc = null;

		try{
			dadosRetorno = (Object[]) ParametroCadastro.P_OBTER_SUB_CONSULTA_DETALHE_REL_RESUMO_LIGACOES_ECONOMIA.executar(this, -1, null,
							grupoDetalhe);
		}catch(ControladorException e){
			throw new RuntimeException(e);
		}

		clusulaGrupoDetalhe = (String) dadosRetorno[0];
		grupoDetalheId = (Integer) dadosRetorno[1];
		grupoDetalheDesc = (String) dadosRetorno[2];

		return RESUMO_DETALHE_BASE_SQL//
						.replaceAll("##GRUPO_DETALHE##", clusulaGrupoDetalhe)//
						.replaceAll("##GRUPO_DETALHE_ID##", grupoDetalheId.toString())//
						.replaceAll("##GRUPO_DETALHE_DESC##", grupoDetalheDesc);
	}

	/**
	 * Método obterParametrosPesquisa
	 * <p>
	 * Esse método implementa as restriçoes para o SQL de acordo com o formulario WEB.
	 * </p>
	 * RASTREIO: [OC897714][UC0269]
	 * 
	 * @param parametrosPesquisa
	 * @return
	 * @author Marlos Ribeiro
	 * @since 21/11/2012
	 */
	private String obterParametrosPesquisa(InformarDadosGeracaoRelatorioConsultaHelper parametrosPesquisa){

		StringBuilder builder = new StringBuilder();

		Integer last_id = LigacaoAguaSituacao.SUPRIMIDO_DEFINITIVO;

		if(!Util.isVazioOuBrancoOuZero(last_id)){
			builder.append(" AND rle.LAST_ID <> " + last_id);
		}

		builder.append(" AND rle.RELE_AMREFERENCIA = ");
		builder.append(parametrosPesquisa.getAnoMesReferencia());
		builder.append(" ");

		// if(parametrosPesquisa.getOpcaoTotalizacao() == 1){
		// builder.append(" AND rle.LOCA_ID in (Select Loca_ID from Localidade where LOCA_ICUSO = 1) ");
		// }

		if(!Util.isVazioOrNulo(parametrosPesquisa.getColecaoCategoria())){
			builder.append(" AND rle.CATG_ID IN ( ");
			for(Categoria cetegoria : (Collection<Categoria>) parametrosPesquisa.getColecaoCategoria()){
				builder.append(cetegoria.getId());
				builder.append(",");
			}
			builder.deleteCharAt(builder.length() - 1);
			builder.append(" ) ");
		}
		if(!Util.isVazioOrNulo(parametrosPesquisa.getColecaoEsferaPoder())){
			builder.append(" AND rle.EPOD_ID IN ( ");
			for(EsferaPoder esfera : (Collection<EsferaPoder>) parametrosPesquisa.getColecaoEsferaPoder()){
				builder.append(esfera.getId());
				builder.append(",");
			}
			builder.deleteCharAt(builder.length() - 1);
			builder.append(" ) ");
		}
		if(!Util.isVazioOrNulo(parametrosPesquisa.getColecaoImovelPerfil())){
			builder.append(" AND rle.IPER_ID IN ( ");
			for(ImovelPerfil perfil : (Collection<ImovelPerfil>) parametrosPesquisa.getColecaoImovelPerfil()){
				builder.append(perfil.getId());
				builder.append(",");
			}
			builder.deleteCharAt(builder.length() - 1);
			builder.append(" ) ");
		}
		if(!Util.isVazioOrNulo(parametrosPesquisa.getColecaoLigacaoAguaSituacao())){
			builder.append(" AND rle.LAST_ID IN ( ");
			for(LigacaoAguaSituacao ligacao : (Collection<LigacaoAguaSituacao>) parametrosPesquisa.getColecaoLigacaoAguaSituacao()){
				builder.append(ligacao.getId());
				builder.append(",");
			}
			builder.deleteCharAt(builder.length() - 1);
			builder.append(" ) ");
		}
		if(!Util.isVazioOrNulo(parametrosPesquisa.getColecaoLigacaoEsgotoSituacao())){
			builder.append(" AND rle.LEST_ID IN ( ");
			for(LigacaoEsgotoSituacao ligacao : (Collection<LigacaoEsgotoSituacao>) parametrosPesquisa.getColecaoLigacaoEsgotoSituacao()){
				builder.append(ligacao.getId());
				builder.append(",");
			}
			builder.deleteCharAt(builder.length() - 1);
			builder.append(" ) ");
		}

		return builder.toString();
	}

	/**
	 * Método obterMetaDadoTotalizador
	 * <p>
	 * Esse método implementa a construcao das ligacoes e restrições para o SQL.
	 * </p>
	 * RASTREIO: [OC897714][UC0269]
	 * 
	 * @param parametrosPesquisa
	 * @return
	 * @author Marlos Ribeiro
	 * @since 21/11/2012
	 */
	private Map<String, String> obterMetaDadoTotalizador(InformarDadosGeracaoRelatorioConsultaHelper parametrosPesquisa){

		String tabela = "";
		String descricao = "";
		String id = "";
		String clausula = "";
		switch(parametrosPesquisa.getOpcaoTotalizacao()){
			case ConstantesSistema.CODIGO_ELO_POLO:
				tabela = "INNER JOIN LOCALIDADE t ON rle.LOCA_CDELO = t.LOCA_ID";
				descricao = "t.LOCA_NMLOCALIDADE";
				id = "t.LOCA_ID";
				clausula = " AND rle.LOCA_CDELO = " + parametrosPesquisa.getLocalidade().getId() + " AND t.LOCA_ICUSO = 1 ";
				break;
			case ConstantesSistema.CODIGO_ELO_POLO_LOCALIDADE:
				tabela = "INNER JOIN LOCALIDADE t ON rle.LOCA_ID = t.LOCA_ID";
				descricao = "t.LOCA_NMLOCALIDADE";
				id = "t.LOCA_ID";
				clausula = " AND rle.LOCA_CDELO = " + parametrosPesquisa.getEloPolo().getId() + " AND t.LOCA_ICUSO = 1 ";
				break;
			case ConstantesSistema.CODIGO_ELO_POLO_SETOR_COMERCIAL:
				tabela = "INNER JOIN SETOR_COMERCIAL t ON rle.STCM_ID = t.STCM_ID";
				descricao = "t.STCM_NMSETORCOMERCIAL";
				id = "t.STCM_ID";
				clausula = " AND rle.LOCA_CDELO = " + parametrosPesquisa.getEloPolo().getId();
				break;
			case ConstantesSistema.CODIGO_ESTADO:
				tabela = "INNER JOIN LOCALIDADE t ON rle.LOCA_ID = t.LOCA_ID";
				descricao = "'ESTADO'";
				id = "0";
				clausula = " AND t.LOCA_ICUSO = 1 ";
				break;
			case ConstantesSistema.CODIGO_ESTADO_ELO_POLO:
				tabela = "INNER JOIN LOCALIDADE t ON rle.LOCA_CDELO = t.LOCA_ID";
				descricao = "t.LOCA_NMLOCALIDADE";
				id = "t.LOCA_ID";
				clausula = " AND t.LOCA_ICUSO = 1 ";
				break;
			case ConstantesSistema.CODIGO_ESTADO_GERENCIA_REGIONAL:
				tabela = "INNER JOIN GERENCIA_REGIONAL t ON rle.GREG_ID = t.GREG_ID";
				descricao = "t.GREG_NMREGIONAL";
				id = "t.GREG_ID";
				break;
			case ConstantesSistema.CODIGO_ESTADO_GRUPO_FATURAMENTO:
				tabela = "INNER JOIN ROTA r ON rle.ROTA_ID = r.ROTA_ID INNER JOIN FATURAMENTO_GRUPO t ON r.FTGR_ID = t.FTGR_ID";
				descricao = "t.FTGR_DSFATURAMENTOGRUPO";
				id = "t.FTGR_ID";
				break;
			case ConstantesSistema.CODIGO_ESTADO_LOCALIDADE:
				tabela = "INNER JOIN LOCALIDADE t ON rle.LOCA_ID = t.LOCA_ID";
				descricao = "t.LOCA_NMLOCALIDADE";
				id = "t.LOCA_ID";
				clausula = " AND t.LOCA_ICUSO = 1 ";
				break;
			case ConstantesSistema.CODIGO_ESTADO_UNIDADE_NEGOCIO:
				tabela = "INNER JOIN UNIDADE_NEGOCIO t ON rle.UNEG_ID = t.UNEG_ID";
				descricao = "t.UNEG_NMUNIDADENEGOCIO";
				id = "t.UNEG_ID";
				break;
			case ConstantesSistema.CODIGO_GERENCIA_REGIONAL:
				tabela = "INNER JOIN GERENCIA_REGIONAL t ON rle.GREG_ID = t.GREG_ID";
				descricao = "t.GREG_NMREGIONAL";
				id = "t.GREG_ID";
				clausula = " AND rle.GREG_ID = " + parametrosPesquisa.getGerenciaRegional().getId();
				break;
			case ConstantesSistema.CODIGO_GERENCIA_REGIONAL_ELO_POLO:
				tabela = "INNER JOIN LOCALIDADE t ON rle.LOCA_CDELO = t.LOCA_ID";
				descricao = "t.LOCA_NMLOCALIDADE";
				id = "t.LOCA_ID";
				clausula = " AND rle.GREG_ID = " + parametrosPesquisa.getGerenciaRegional().getId() + " AND t.LOCA_ICUSO = 1 ";
				break;
			case ConstantesSistema.CODIGO_GERENCIA_REGIONAL_LOCALIDADE:
				tabela = "INNER JOIN LOCALIDADE t ON rle.LOCA_ID = t.LOCA_ID";
				descricao = "t.LOCA_NMLOCALIDADE";
				id = "t.LOCA_ID";
				clausula = " AND rle.GREG_ID = " + parametrosPesquisa.getGerenciaRegional().getId() + " AND t.LOCA_ICUSO = 1 ";
				break;
			case ConstantesSistema.CODIGO_GRUPO_FATURAMENTO:
				tabela = "INNER JOIN ROTA r ON rle.ROTA_ID = r.ROTA_ID INNER JOIN FATURAMENTO_GRUPO t ON r.FTGR_ID = t.FTGR_ID";
				descricao = "t.FTGR_DSFATURAMENTOGRUPO";
				id = "t.FTGR_ID";
				clausula = " AND t.FTGR_ID = " + parametrosPesquisa.getFaturamentoGrupo().getId();
				break;
			case ConstantesSistema.CODIGO_LOCALIDADE:
				tabela = "INNER JOIN LOCALIDADE t ON rle.LOCA_ID = t.LOCA_ID";
				descricao = "t.LOCA_NMLOCALIDADE";
				id = "t.LOCA_ID";
				clausula = " AND rle.LOCA_ID = " + parametrosPesquisa.getLocalidade().getId() + " AND t.LOCA_ICUSO = 1 ";
				break;
			case ConstantesSistema.CODIGO_LOCALIDADE_QUADRA:
				tabela = "INNER JOIN QUADRA t ON rle.QDRA_ID = t.QDRA_ID";
				descricao = "t.QDRA_NNQUADRA";
				id = "t.QDRA_ID";
				clausula = " AND rle.LOCA_ID = " + parametrosPesquisa.getLocalidade().getId() + " AND t.QDRA_ICUSO = 1 ";
				break;
			case ConstantesSistema.CODIGO_LOCALIDADE_SETOR_COMERCIAL:
				tabela = "INNER JOIN SETOR_COMERCIAL t ON rle.STCM_ID = t.STCM_ID";
				descricao = "t.STCM_NMSETORCOMERCIAL";
				id = "t.STCM_ID";
				clausula = " AND rle.LOCA_ID = " + parametrosPesquisa.getLocalidade().getId() + " AND t.STCM_ICUSO = 1 ";
				break;
			case ConstantesSistema.CODIGO_QUADRA:
				tabela = "INNER JOIN QUADRA t ON rle.QDRA_ID = t.QDRA_ID";
				descricao = "t.QDRA_NNQUADRA";
				id = "t.QDRA_ID";
				clausula = " AND rle.QDRA_ID = " + parametrosPesquisa.getQuadra().getId();
				break;
			case ConstantesSistema.CODIGO_SETOR_COMERCIAL:
				tabela = "INNER JOIN SETOR_COMERCIAL t ON rle.STCM_ID = t.STCM_ID";
				descricao = "t.STCM_NMSETORCOMERCIAL";
				id = "t.STCM_ID";
				clausula = " AND rle.STCM_ID = " + parametrosPesquisa.getSetorComercial().getId();
				break;
			case ConstantesSistema.CODIGO_SETOR_COMERCIAL_QUADRA:
				tabela = "INNER JOIN QUADRA t ON rle.QDRA_ID = t.QDRA_ID";
				descricao = "t.QDRA_NNQUADRA";
				id = "t.QDRA_ID";
				clausula = " AND rle.STCM_ID = " + parametrosPesquisa.getSetorComercial().getId();
				break;
			case ConstantesSistema.CODIGO_UNIDADE_NEGOCIO:
				tabela = "INNER JOIN UNIDADE_NEGOCIO t ON rle.UNEG_ID = t.UNEG_ID "
								+ "INNER JOIN LOCALIDADE loc ON t.UNEG_ID = loc.UNEG_ID ";
				descricao = "t.UNEG_NMUNIDADENEGOCIO";
				id = "t.UNEG_ID";
				clausula = " AND rle.UNEG_ID = " + parametrosPesquisa.getUnidadeNegocio().getId() + " AND loc.LOCA_ICUSO = 1 ";
				break;
			case ConstantesSistema.CODIGO_UNIDADE_NEGOCIO_ELO_POLO:
				tabela = "INNER JOIN LOCALIDADE t ON rle.LOCA_CDELO = t.LOCA_ID";
				descricao = "t.LOCA_NMLOCALIDADE";
				id = "t.LOCA_ID";
				clausula = " AND rle.UNEG_ID = " + parametrosPesquisa.getUnidadeNegocio().getId() + " AND t.LOCA_ICUSO = 1 ";
				break;
			case ConstantesSistema.CODIGO_UNIDADE_NEGOCIO_LOCALIDADE:
				tabela = "INNER JOIN LOCALIDADE t ON rle.LOCA_ID = t.LOCA_ID";
				descricao = "t.LOCA_NMLOCALIDADE";
				id = "t.LOCA_ID";
				clausula = " AND rle.UNEG_ID = " + parametrosPesquisa.getUnidadeNegocio().getId() + " AND t.LOCA_ICUSO = 1 ";
				break;
		}

		Map<String, String> metaDado = new HashMap<String, String>();
		metaDado.put(TOTALIZADOR_TABLE, tabela);
		metaDado.put(TOTALIZADOR_DESC, descricao);
		metaDado.put(TOTALIZADOR_ID, id);
		metaDado.put(TOTALIZADOR_CLAUSULA, clausula + " ");
		return metaDado;
	}

	/**
	 * Método getSQLQuery
	 * <p>
	 * Esse método retorna a SQL.
	 * </p>
	 * RASTREIO: [OC897714][UC0269]
	 * 
	 * @return
	 * @author Marlos Ribeiro
	 * @since 22/11/2012
	 */
	public String getSQLQuery(){

		return sqlQuery;
	}

	public ExecutorParametro getExecutorParametro(){

		return ExecutorParametrosCadastro.getInstancia();
	}
}
