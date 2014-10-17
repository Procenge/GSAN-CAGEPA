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
package gcom.faturamento.histograma;

import gcom.faturamento.ConsumoFaixaCategoria;
import gcom.faturamento.bean.FiltrarEmitirHistogramaEsgotoEconomiaHelper;
import gcom.faturamento.bean.HistogramaEsgotoEconomiaDTO;
import gcom.util.ControladorException;
import gcom.util.ServiceLocator;
import gcom.util.Util;

import java.math.BigDecimal;
import java.util.*;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.type.Type;

/**
 * @author mgrb
 *
 */
public abstract class AbstractRepositorioHistogramaEsgotoEconomiaHelper
				implements RepositorioHistogramaEsgotoEconomiaHelper {

	private static final Logger LOGGER = Logger.getLogger(AbstractRepositorioHistogramaEsgotoEconomiaHelper.class);

	protected static final String DIVISOR_TOTALIZADOR_ID_DESC = "-";
	protected static final String DIVISOR_TOTALIZADOR_ITEM = " / ";

	protected static final String FK_HEE_LOCA_ID = "hee.LOCA_ID";
	protected static final String FK_HEE_LOCA_CDELO = "hee.LOCA_CDELO";
	protected static final String FK_HEE_UNEG_ID = "hee.UNEG_ID";
	protected static final String FK_HEE_GREG_ID = "hee.GREG_ID";
	protected static final String FK_HEE_STCM_ID = "hee.STCM_ID";

	protected static final String FK_HEE_QDRA_ID = "hee.QDRA_ID";

	private FiltrarEmitirHistogramaEsgotoEconomiaHelper filtro;
	private StringBuffer queryTemplate;

	private List<RelacionamentoInnerJoin> relacionamentos;

	private List<ColunaAdcional> colunasAdcionais;

	private List<ClausulaWare> clausulas;

	/**
	 * AbstractRepositorioHistogramaEsgotoEconomiaHelper
	 * <p>
	 * Esse método Constroi uma instância de AbstractRepositorioHistogramaEsgotoEconomiaHelper.
	 * </p>
	 * 
	 * @author Marlos Ribeiro
	 * @since 04/06/2013
	 */
	public AbstractRepositorioHistogramaEsgotoEconomiaHelper() {
		queryTemplate = iniciarQueryTemplate();
	}

	/** 
	 * Método iniciarQueryTemplate
	 * <p>Esse método implementa [mgrb] descrever o que implemntar</p> 
	 * RASTREIO: [OCXXXXX][UCXXXX][SBXXXX]
	 * @return
	 * @author Marlos Ribeiro 
	 * @since 04/06/2013
	 */
	private StringBuffer iniciarQueryTemplate(){
		StringBuffer buffer = new StringBuffer();

		buffer.append(" SELECT  ");
		/* CAMPOS QUBRA DE RELATORIO ------------------------------- */
		buffer.append(" 	#COLUNAS_ADCIONAIS# ");
		/* DADOS COMUNS ------------------------------- */
		buffer.append(" 	hee.HEGE_NNPERCENTUALESGOTO HEE_PERCENTUAL, ");
		buffer.append(" 	c.CATG_ID HEE_CATEGORIA_ID, c.CATG_DSCATEGORIA HEE_CATEGORIA,");
		buffer.append(" 	cfc.CFCG_NNFAIXAINICIO HEE_FAIXA_INI, ");
		buffer.append(" 	cfc.CFCG_NNFAIXAFIM HEE_FAIXA_FIM,	 ");
		/*-----------COM HIDROMETRO------------*/
		buffer.append(" 	SUM(CASE WHEN hee.HEGE_ICHIDROMETRO = 1 AND hee.HEGE_QTCONSUMO BETWEEN cfc.CFCG_NNFAIXAINICIO AND cfc.CFCG_NNFAIXAFIM THEN hee.HEGE_QTECONOMIA + hee.HEGE_QTECONOMIAREFAT ELSE 0 END) HEE_CH_QTD_ECONOMIA, ");
		buffer.append(" 	SUM(CASE WHEN hee.HEGE_ICHIDROMETRO = 1 AND hee.HEGE_QTCONSUMO BETWEEN cfc.CFCG_NNFAIXAINICIO AND cfc.CFCG_NNFAIXAFIM THEN hee.HEGE_QTLIGACAO + hee.HEGE_QTLIGACAOREFAT ELSE 0 END) HEE_CH_QTD_LIGACAO, ");
		buffer.append(" 	SUM(CASE WHEN hee.HEGE_ICHIDROMETRO = 1 AND hee.HEGE_QTCONSUMO BETWEEN cfc.CFCG_NNFAIXAINICIO AND cfc.CFCG_NNFAIXAFIM THEN hee.HEGE_VOFATURADOECONOMIA + hee.HEGE_VOFATURADOECONOMIAREFAT ELSE 0 END) HEE_CH_VOL_FATURADO, ");

		// buffer.append(" 	SUM(CASE WHEN hee.HEGE_ICHIDROMETRO = 1 AND hee.HEGE_QTCONSUMO BETWEEN cfc.CFCG_NNFAIXAINICIO AND cfc.CFCG_NNFAIXAFIM THEN hee.HEGE_QTCONSUMO ELSE 0 END) HEE_CH_QTD_CONSUMO, ");
		buffer.append(" 	SUM(CASE WHEN hee.HEGE_ICHIDROMETRO = 1 AND hee.HEGE_QTCONSUMO BETWEEN cfc.CFCG_NNFAIXAINICIO AND cfc.CFCG_NNFAIXAFIM THEN hee.HEGE_QTCONSUMO *  (hee.hege_qteconomia + hee.hege_qteconomiarefat) ELSE 0 END) HEE_CH_QTD_CONSUMO, ");

		/*-----------SEM HIDROMETRO------------*/
		buffer.append(" 	SUM(CASE WHEN hee.HEGE_ICHIDROMETRO = 2 AND hee.HEGE_QTCONSUMO BETWEEN cfc.CFCG_NNFAIXAINICIO AND cfc.CFCG_NNFAIXAFIM THEN hee.HEGE_QTECONOMIA + hee.HEGE_QTECONOMIAREFAT ELSE 0 END) HEE_SH_QTD_ECONOMIA, ");
		buffer.append(" 	SUM(CASE WHEN hee.HEGE_ICHIDROMETRO = 2 AND hee.HEGE_QTCONSUMO BETWEEN cfc.CFCG_NNFAIXAINICIO AND cfc.CFCG_NNFAIXAFIM THEN hee.HEGE_QTLIGACAO + hee.HEGE_QTLIGACAOREFAT ELSE 0 END) HEE_SH_QTD_LIGACAO, ");
		buffer.append(" 	SUM(CASE WHEN hee.HEGE_ICHIDROMETRO = 2 AND hee.HEGE_QTCONSUMO BETWEEN cfc.CFCG_NNFAIXAINICIO AND cfc.CFCG_NNFAIXAFIM THEN hee.HEGE_VOFATURADOECONOMIA + hee.HEGE_VOFATURADOECONOMIAREFAT ELSE 0 END) HEE_SH_VOL_FATURADO, ");

		// buffer.append(" 	SUM(CASE WHEN hee.HEGE_ICHIDROMETRO = 2 AND hee.HEGE_QTCONSUMO BETWEEN cfc.CFCG_NNFAIXAINICIO AND cfc.CFCG_NNFAIXAFIM THEN hee.HEGE_QTCONSUMO ELSE 0 END) HEE_SH_QTD_CONSUMO ");
		buffer.append(" 	SUM(CASE WHEN hee.HEGE_ICHIDROMETRO = 2 AND hee.HEGE_QTCONSUMO BETWEEN cfc.CFCG_NNFAIXAINICIO AND cfc.CFCG_NNFAIXAFIM THEN hee.HEGE_QTCONSUMO *  (hee.hege_qteconomia + hee.hege_qteconomiarefat) ELSE 0 END) HEE_SH_QTD_CONSUMO ");

		buffer.append(" FROM  ");
		buffer.append(" 	HISTOGRAMA_ESGOTO_ECONOMIA hee ");
		buffer.append(" 	INNER JOIN CONSUMO_FAIXA_CATEGORIA cfc ON cfc.CATG_ID = hee.CATG_ID  ");
		buffer.append(" 	INNER JOIN CATEGORIA c ON c.CATG_ID = cfc.CATG_ID ");
		/* RELACIONAMENTOS ADCIONAIS ------------------------------- */
		buffer.append(" 	#TABELAS_ADCIONAIS# ");
		buffer.append(" WHERE  ");
		buffer.append(" 	1=1 AND #CLAUSULAS_WHERE# ");
		buffer.append(" GROUP BY  ");
		/* CAMPOS QUBRA DE RELATORIO [AGRUPAMENTO ADCIONAL] ------------------------------- */
		buffer.append(" 	#GROUP_BY_ADCIONAIS#	 ");
		/* AGRUPAMENTO FIXO ------------------------------- */
		buffer.append(" 	c.CATG_ID, c.CATG_DSCATEGORIA, ");
		buffer.append(" 	cfc.CFCG_NNFAIXAINICIO, ");
		buffer.append(" 	cfc.CFCG_NNFAIXAFIM,	 ");
		buffer.append(" 	hee.HEGE_NNPERCENTUALESGOTO ");
		buffer.append(" ORDER BY ");
		/* CAMPOS QUEBRA DE RELATORIO [ORDENAÇÃO ADCIONAL] ------------------------------- */
		buffer.append(" 	#ORDER_BY_ADCIONAIS# ");
		/* ORDENAÇÃO FIXA ------------------------------- */
		buffer.append(" 	hee.HEGE_NNPERCENTUALESGOTO, ");
		buffer.append(" 	c.CATG_ID, c.CATG_DSCATEGORIA, ");
		buffer.append(" 	cfc.CFCG_NNFAIXAINICIO, ");
		buffer.append(" 	cfc.CFCG_NNFAIXAFIM ");

		return buffer;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<HistogramaEsgotoEconomiaDTO> executarPesquisa(Session session){

		LOGGER.info("EXECUTANDO: " + OpcaoTotalizacaoEnum.get(filtro.getOpcaoTotalizacao()).getRepositorioHelperImplClass().getSimpleName());
		String sqlFormatada = montarSQL();
		SQLQuery query = session.createSQLQuery(sqlFormatada);
		montarScalares(query);
		preencherClausulasWare(query);

		List<Object[]> dadosHistogramaTemp = query.list();
		if(Util.isVazioOrNulo(dadosHistogramaTemp)) return Collections.EMPTY_LIST;
		else return normalizarResultado(dadosHistogramaTemp, session);
	}

	/**
	 * Método preencherClausulasWare
	 * <p>
	 * Esse método implementa [mgrb] descrever o que implemntar
	 * </p>
	 * RASTREIO: [OCXXXXX][UCXXXX][SBXXXX]
	 * 
	 * @param query
	 * @author Marlos Ribeiro
	 * @since 10/06/2013
	 */
	private void preencherClausulasWare(SQLQuery query){

		for(ClausulaWare clausula : clausulas){
			Class vlClass = clausula.valor.getClass();
			if(Integer.class.equals(vlClass)) query.setInteger(clausula.alias, (Integer) clausula.valor);
			if(Short.class.equals(vlClass)) query.setShort(clausula.alias, (Short) clausula.valor);
			if(Long.class.equals(vlClass)) query.setLong(clausula.alias, (Long) clausula.valor);
			if(Collection.class.isAssignableFrom(vlClass)) query.setParameterList(clausula.alias, (Collection) clausula.valor);
		}

	}

	/**
	 * Método montarFiltro
	 * <p>
	 * Esse método implementa [mgrb] descrever o que implemntar
	 * </p>
	 * RASTREIO: [OCXXXXX][UCXXXX][SBXXXX]
	 * 
	 * @return
	 * @author Marlos Ribeiro
	 * @since 10/06/2013
	 */
	private List<ClausulaWare> montarClausulas(){

		List<ClausulaWare> clausulas = new ArrayList<ClausulaWare>();
		// CATEGORIA
		if(!Util.isVazioOrNulo(filtro.getColecaoCategoria())) //
		clausulas.add(new ClausulaWare("hee.CATG_ID IN (:FILTRO_LISTA_CATG_ID)", "FILTRO_LISTA_CATG_ID", filtro.getColecaoCategoria()));
		// TARIFA
		if(!Util.isVazioOrNulo(filtro.getColecaoTarifa())) //
		clausulas.add(new ClausulaWare("hee.CSTF_ID IN (:FILTRO_LISTA_CSTF_ID)", "FILTRO_LISTA_CSTF_ID", filtro.getColecaoTarifa()));
		// PERFIL DO IMOVEL
		if(!Util.isVazioOrNulo(filtro.getColecaoPerfilImovel())) //
		clausulas.add(new ClausulaWare("hee.IPER_ID IN (:FILTRO_LISTA_IPER_ID)", "FILTRO_LISTA_IPER_ID", filtro.getColecaoPerfilImovel()));
		// ESFERA DO PODER
		if(!Util.isVazioOrNulo(filtro.getColecaoEsferaPoder())) //
		clausulas.add(new ClausulaWare("hee.EPOD_ID IN (:FILTRO_LISTA_EPOD_ID)", "FILTRO_LISTA_EPOD_ID", filtro.getColecaoEsferaPoder()));
		// SITUACAO DA LIGACAO DO ESGOTO
		if(!Util.isVazioOrNulo(filtro.getColecaoSituacaoLigacaoEsgoto())) //
		clausulas.add(new ClausulaWare("hee.LEST_ID IN (:FILTRO_LISTA_LEST_ID)", "FILTRO_LISTA_LEST_ID", filtro
						.getColecaoSituacaoLigacaoEsgoto()));
		// PERCENTUAL DE ESGOTO
		if(!Util.isVazioOrNulo(filtro.getColecaoPercentualLigacaoEsgoto())) //
		clausulas.add(new ClausulaWare("hee.HEGE_NNPERCENTUALESGOTO IN (:FILTRO_LISTA_HEGE_NNPERCENTUALESGOTO)",
						"FILTRO_LISTA_HEGE_NNPERCENTUALESGOTO", filtro
						.getColecaoPercentualLigacaoEsgoto()));
		// GERENCIA REGIONAL
		if(filtro.getGerenciaRegional() != null) //
		clausulas.add(new ClausulaWare("hee.GREG_ID = :FILTRO_GREG_ID", "FILTRO_GREG_ID", filtro.getGerenciaRegional().getId()));
		// UNIDADE DE NEGOCIO
		if(filtro.getUnidadeNegocio() != null) //
		clausulas.add(new ClausulaWare("hee.UNEG_ID = :FILTRO_UNEG_ID", "FILTRO_UNEG_ID", filtro.getUnidadeNegocio().getId()));
		// ELO POLO
		if(filtro.getEloPolo() != null) //
		clausulas.add(new ClausulaWare("hee.LOCA_CDELO = :FILTRO_LOCA_CDELO", "FILTRO_LOCA_CDELO", filtro.getEloPolo().getId()));
		// LOCALIDADE
		if(filtro.getLocalidade() != null) //
		clausulas.add(new ClausulaWare("hee.LOCA_ID = :FILTRO_LOCA_ID", "FILTRO_LOCA_ID", filtro.getLocalidade().getId()));
		// SETOR COMERCIAL
		if(filtro.getSetorComercial() != null) //
		clausulas.add(new ClausulaWare("hee.STCM_ID = :FILTRO_STCM_ID", "FILTRO_STCM_ID", filtro.getSetorComercial().getId()));
		// QUADRA
		if(filtro.getQuadra() != null) //
		clausulas.add(new ClausulaWare("hee.QDRA_ID = :FILTRO_QDRA_ID", "FILTRO_QDRA_ID", filtro.getQuadra().getId()));
		// REFERENCIA
		if(filtro.getMesAnoFaturamento() != null) //
		clausulas.add(new ClausulaWare("hee.HEGE_AMREFERENCIA = :FILTRO_HEGE_AMREFERENCIA", "FILTRO_HEGE_AMREFERENCIA", filtro
						.getMesAnoFaturamento()));
		// INDICADO CONSUMO
		if(filtro.getConsumo() != null) //
		clausulas.add(new ClausulaWare("hee.HEGE_ICCONSUMOREAL = :FILTRO_HEGE_ICCONSUMOREAL", "FILTRO_HEGE_ICCONSUMOREAL", filtro
						.getConsumo()));
		// INDICADO POÇO
		if(filtro.getPoco() != null) //
		clausulas.add(new ClausulaWare("hee.HEGE_ICPOCO = :FILTRO_HEGE_ICPOCO", "FILTRO_HEGE_ICPOCO", filtro.getPoco()));
		// INDICADO VOLUME FIXO AGUA
		if(filtro.getMedicao() != null) //
		clausulas.add(new ClausulaWare("hee.HEGE_ICVOLFIXADOESGOTO = :FILTRO_HEGE_ICVOLFIXADOESGOTO", "FILTRO_HEGE_ICVOLFIXADOESGOTO",
						filtro.getMedicao()));

		return clausulas;
	}

	/**
	 * Método montarScalares
	 * <p>
	 * Esse método implementa [mgrb] descrever o que implemntar
	 * </p>
	 * RASTREIO: [OCXXXXX][UCXXXX][SBXXXX]
	 * 
	 * @param query
	 * @author Marlos Ribeiro
	 * @since 05/06/2013
	 */
	private void montarScalares(SQLQuery query){

		if(!Util.isVazioOrNulo(colunasAdcionais)){
		for(ColunaAdcional coluna : colunasAdcionais){
			query.addScalar(coluna.alias, coluna.tipo);
			}
		}
		query.addScalar("HEE_PERCENTUAL", Hibernate.SHORT);
		query.addScalar("HEE_CATEGORIA_ID", Hibernate.INTEGER);
		query.addScalar("HEE_CATEGORIA", Hibernate.STRING);
		query.addScalar("HEE_FAIXA_INI", Hibernate.INTEGER);
		query.addScalar("HEE_FAIXA_FIM", Hibernate.INTEGER);
		query.addScalar("HEE_CH_QTD_ECONOMIA", Hibernate.LONG);
		query.addScalar("HEE_CH_QTD_LIGACAO", Hibernate.LONG);
		query.addScalar("HEE_CH_VOL_FATURADO", Hibernate.LONG);
		// query.addScalar("HEE_CH_VAL_FATURADO", Hibernate.BIG_DECIMAL);
		query.addScalar("HEE_CH_QTD_CONSUMO", Hibernate.LONG);
		query.addScalar("HEE_SH_QTD_ECONOMIA", Hibernate.LONG);
		query.addScalar("HEE_SH_QTD_LIGACAO", Hibernate.LONG);
		query.addScalar("HEE_SH_VOL_FATURADO", Hibernate.LONG);
		// query.addScalar("HEE_SH_VAL_FATURADO", Hibernate.BIG_DECIMAL);
		query.addScalar("HEE_SH_QTD_CONSUMO", Hibernate.LONG);
	}

	/**
	 * Método montarSQL
	 * <p>
	 * Esse método implementa [mgrb] descrever o que implemntar
	 * </p>
	 * RASTREIO: [OCXXXXX][UCXXXX][SBXXXX]
	 * 
	 * @return
	 * @author Marlos Ribeiro
	 * @since 05/06/2013
	 */
	private String montarSQL(){

		String sqlPreenchida = queryTemplate.toString();
		sqlPreenchida = sqlPreenchida.replace("#COLUNAS_ADCIONAIS#", getColunasAdcionais(true));
		sqlPreenchida = sqlPreenchida.replace("#TABELAS_ADCIONAIS#", getTabelasAdcionais());
		sqlPreenchida = sqlPreenchida.replace("#CLAUSULAS_WHERE#", getClausulasWhare());
		sqlPreenchida = sqlPreenchida.replace("#ORDER_BY_ADCIONAIS#", getColunasAdcionais(false));
		sqlPreenchida = sqlPreenchida.replace("#GROUP_BY_ADCIONAIS#", getColunasAdcionais(false));
		return sqlPreenchida;
	}

	/**
	 * Método getClausulasWhare
	 * <p>
	 * Esse método implementa [mgrb] descrever o que implemntar
	 * </p>
	 * RASTREIO: [OCXXXXX][UCXXXX][SBXXXX]
	 * 
	 * @return
	 * @author Marlos Ribeiro
	 * @since 10/06/2013
	 */
	private CharSequence getClausulasWhare(){

		StringBuffer buffer = new StringBuffer();
		for(ClausulaWare clausula : clausulas){
			if(buffer.length() > 0) buffer.append(" AND ");
			buffer.append(clausula.clausula);
		}
		return buffer.toString();
	}

	/**
	 * Método getColunasAdcionais
	 * <p>
	 * Esse método implementa [mgrb] descrever o que implemntar
	 * </p>
	 * RASTREIO: [OCXXXXX][UCXXXX][SBXXXX]
	 * 
	 * @return
	 * @author Marlos Ribeiro
	 * @since 05/06/2013
	 */
	private String getColunasAdcionais(boolean comAlias){

		if(Util.isVazioOrNulo(colunasAdcionais)) return "";

		StringBuffer buffer = new StringBuffer();
		for(ColunaAdcional coluna : colunasAdcionais){
			if(buffer.length() > 0) buffer.append(", ");
			buffer.append(coluna.nome);
			if(comAlias){
				buffer.append(' ');
				buffer.append(coluna.alias);
			}
		}
		if(buffer.length() > 0) buffer.append(", ");
		return buffer.toString();
	}

	/**
	 * Método getTabelasAdcionais
	 * <p>
	 * Esse método implementa [mgrb] descrever o que implemntar
	 * </p>
	 * RASTREIO: [OCXXXXX][UCXXXX][SBXXXX]
	 * 
	 * @return
	 * @author Marlos Ribeiro
	 * @since 05/06/2013
	 */
	private String getTabelasAdcionais(){

		if(Util.isVazioOrNulo(relacionamentos)) return "";
		StringBuffer buffer = new StringBuffer();
		for(RelacionamentoInnerJoin relacionamento : this.relacionamentos){
			buffer.append(" INNER JOIN ");
			buffer.append(relacionamento.tabela);
			buffer.append(" ");
			buffer.append(relacionamento.alias);
			buffer.append(" ON ");
			buffer.append(relacionamento.alias);
			buffer.append('.');
			buffer.append(relacionamento.colunaPk);
			buffer.append('=');
			buffer.append(relacionamento.colunaFk);
			buffer.append(' ');
		}
		return buffer.toString();
	}


	/**
	 * {@inheritDoc}
	 * 
	 * @param session
	 */
	public HistogramaEsgotoEconomiaDTO criarEmitirHistogramaAguaEsgotoEconomiaHelper(Object[] objects, Session session){

		/*
		 * 0 - HEE_PERCENTUAL
		 * 1 - HEE_CATEGORIA_ID
		 * 2 - HEE_CATEGORIA
		 * 3 - HEE_FAIXA_INI
		 * 4 - HEE_FAIXA_FIM
		 * 5 - HEE_CH_QTD_ECONOMIA
		 * 6 - HEE_CH_QTD_LIGACAO
		 * 7 - HEE_CH_VOL_FATURADO
		 * 8 - HEE_CH_QTD_CONSUMO
		 * 9 - HEE_SH_QTD_ECONOMIA
		 * 10- HEE_SH_QTD_LIGACAO
		 * 11- HEE_SH_VOL_FATURADO
		 * 12- HEE_SH_QTD_CONSUMO
		 */
		int idx = Util.isVazioOrNulo(colunasAdcionais) ? 0 : colunasAdcionais.size();
		int faixaInicio;
		int faixaFim;
		int categoriaId;
		HistogramaEsgotoEconomiaDTO histobramaDTO = preencherHistogramaHelper(objects);

		histobramaDTO.setPercentualEsgoto((Short) objects[idx++]); // 0
		categoriaId = (Integer) objects[idx++]; // 1
		histobramaDTO.setIdCategoria(categoriaId); // 1
		histobramaDTO.setDescricaoCategoria((String) objects[idx++]); // 2
		faixaInicio = (Integer) objects[idx++]; // 3
		histobramaDTO.setInicio(faixaInicio);
		faixaFim = (Integer) objects[idx++]; // 4
		histobramaDTO.setFim(faixaFim);
		String descricaFaixa = (String.valueOf(faixaInicio)).concat(" a ").concat(String.valueOf(faixaFim)); // 4
		histobramaDTO.setFaixaDescricao(descricaFaixa); // 4

		histobramaDTO.setTotalEconomiasMedido((Long) objects[idx++]); // 5
		histobramaDTO.setTotalLigacoesMedido((Long) objects[idx++]); // 6
		histobramaDTO.setTotalVolumeFaturadoMedido((Long) objects[idx++]); // 7
		histobramaDTO.setTotalConsumoMedido((Long) objects[idx++]); // 8

		histobramaDTO.setTotalEconomiasNaoMedido((Long) objects[idx++]); // 9
		histobramaDTO.setTotalLigacoesNaoMedido((Long) objects[idx++]); // 10
		histobramaDTO.setTotalVolumeFaturadoNaoMedido((Long) objects[idx++]); // 11
		histobramaDTO.setTotalConsumoNaoMedido((Long) objects[idx++]); // 12



		// Calculo Volumes Medio
		calcularVolumesMedios(histobramaDTO);

		// Calculo das Receitas
		calcularReceitas(session, faixaInicio, faixaFim, categoriaId, histobramaDTO);

		// calcularConsumosGerados(histobramaDTO);


		return histobramaDTO;
	}

	private void calcularConsumosGerados(HistogramaEsgotoEconomiaDTO histobramaDTO){

		Long totalConumoMedido = histobramaDTO.getTotalConsumoMedioMedido().longValue() * histobramaDTO.getTotalEconomiasMedido();
		histobramaDTO.setTotalConsumoMedido(totalConumoMedido);

		Long totalConumoNaoMedido = histobramaDTO.getTotalConsumoMedioNaoMedido().longValue() * histobramaDTO.getTotalEconomiasNaoMedido();
		histobramaDTO.setTotalConsumoNaoMedido(totalConumoNaoMedido);

	}

	private void calcularVolumesMedios(HistogramaEsgotoEconomiaDTO histobramaDTO){

		if(Long.valueOf(0).equals(histobramaDTO.getTotalEconomiasMedido())) histobramaDTO.setTotalConsumoMedioMedido(BigDecimal.ZERO);
		else histobramaDTO.setTotalConsumoMedioMedido(Util.dividirArredondando(BigDecimal.valueOf(histobramaDTO.getTotalConsumoMedido()),
						BigDecimal.valueOf(histobramaDTO.getTotalEconomiasMedido()), 2));

		if(Long.valueOf(0).equals(histobramaDTO.getTotalEconomiasNaoMedido())) histobramaDTO.setTotalConsumoMedioNaoMedido(BigDecimal.ZERO);
		else histobramaDTO.setTotalConsumoMedioNaoMedido(Util.dividirArredondando(
						BigDecimal.valueOf(histobramaDTO.getTotalConsumoNaoMedido()),
						BigDecimal.valueOf(histobramaDTO.getTotalEconomiasNaoMedido()), 2));
	}

	private void calcularReceitas(Session session, int faixaInicio, int faixaFim, int categoriaId, HistogramaEsgotoEconomiaDTO histobramaDTO){

		BigDecimal vlTarifa = getValorTarifa(session, categoriaId, faixaInicio, faixaFim);
		if(vlTarifa == null) vlTarifa = BigDecimal.ZERO;

		BigDecimal valorReceitaMedido;
		BigDecimal valorReceitaNaoMedido;
		// SE FAIXA 01: Pegar a qtd de economias SENAO volumeFaturado
		if(faixaInicio == 0){
			valorReceitaMedido = BigDecimal.valueOf(histobramaDTO.getTotalEconomiasMedido());
			valorReceitaNaoMedido = BigDecimal.valueOf(histobramaDTO.getTotalEconomiasNaoMedido());
		}else{
			valorReceitaMedido = BigDecimal.valueOf(histobramaDTO.getTotalVolumeFaturadoMedido());
			valorReceitaNaoMedido = BigDecimal.valueOf(histobramaDTO.getTotalVolumeFaturadoNaoMedido());
		}

		// valor da receita é multiplicado pela TARIFA da faixa
		valorReceitaMedido = valorReceitaMedido.multiply(vlTarifa);
		valorReceitaNaoMedido = valorReceitaNaoMedido.multiply(vlTarifa);

		BigDecimal percentualEsgoto = BigDecimal.valueOf(histobramaDTO.getPercentualEsgoto()).setScale(2)
						.divide(BigDecimal.valueOf(100),
						BigDecimal.ROUND_HALF_UP);
		valorReceitaMedido = valorReceitaMedido.multiply(percentualEsgoto);
		valorReceitaNaoMedido = valorReceitaNaoMedido.multiply(percentualEsgoto);

		histobramaDTO.setTotalReceitaMedido(valorReceitaMedido.setScale(2, BigDecimal.ROUND_HALF_UP));
		histobramaDTO.setTotalReceitaNaoMedido(valorReceitaNaoMedido.setScale(2, BigDecimal.ROUND_HALF_UP));
	}

	private BigDecimal getValorTarifa(Session session, int categoriaId, int faixaInicio, int faixaFim){

		StringBuffer buffer = new StringBuffer();
		buffer.append(" SELECT VL_TARIFA FROM ( ");
		buffer.append(" 	SELECT  ");
		buffer.append(" 		CASE ctf.CTFX_NNCOSUMOFAIXAINICIO   ");
		buffer.append(" 			WHEN 0 THEN ctc.CSTC_VLTARIFAMINIMA ");
		buffer.append(" 			ELSE ctf.CTFX_VLCONSUMOTARIFA ");
		buffer.append(" 		END VL_TARIFA ");
		buffer.append(" 	FROM  ");
		buffer.append(" 		CONSUMO_TARIFA_FAIXA ctf ");
		buffer.append(" 		INNER JOIN CONSUMO_TARIFA_CATEGORIA ctc ON ctc.CSTC_ID = ctf.CSTC_ID ");
		buffer.append(" 		INNER JOIN CONSUMO_TARIFA_VIGENCIA ctv ON ctv.CSTV_ID = ctc.CSTV_ID ");
		buffer.append(" 	WHERE  ");
		buffer.append(" 		ctv.CSTF_ID = :ID_CONSUMO_TARIFA ");
		buffer.append(" 		AND ctv.CSTV_DTVIGENCIA <= CURRENT_TIMESTAMP ");
		buffer.append(" 		AND ctc.CATG_ID = :ID_CATEGORIA ");
		buffer.append(" 		AND ctf.CTFX_NNCOSUMOFAIXAINICIO = :FAIXA_INICIO ");
		buffer.append(" 		AND ctf.CTFX_NNCONSUMOFAIXAFIM = :FAIXA_FIM ");
		buffer.append(" 	ORDER BY ctv.CSTV_DTVIGENCIA DESC	 ");
		buffer.append(" ) WHERE ROWNUM = 1 ");

		SQLQuery query = session.createSQLQuery(buffer.toString());
		int consumoTarifaId;
		try{
			consumoTarifaId = ServiceLocator.getInstancia().getControladorUtil().pesquisarParametrosDoSistema().getConsumoTarifaDefault()
							.getId();
		}catch(ControladorException e){
			throw new IllegalStateException("Problemas ao consultar ID da tarifa consumo padrão.", e);
		}
		query.setInteger("ID_CONSUMO_TARIFA", consumoTarifaId);
		query.setInteger("ID_CATEGORIA", categoriaId);
		query.setInteger("FAIXA_INICIO", faixaInicio);
		query.setInteger("FAIXA_FIM", faixaFim);

		query.addScalar("VL_TARIFA", Hibernate.BIG_DECIMAL);

		return (BigDecimal) query.uniqueResult();

	}

	/**
	 * Método preencherHistogramaHelper
	 * <p>
	 * Esse método implementa [mgrb] descrever o que implemntar
	 * </p>
	 * RASTREIO: [OCXXXXX][UCXXXX][SBXXXX]
	 * 
	 * @return
	 * @author Marlos Ribeiro
	 * @param objects
	 * @since 05/06/2013
	 */
	protected abstract HistogramaEsgotoEconomiaDTO preencherHistogramaHelper(Object[] objects);

	/**
	 * @return the filtro
	 */
	public FiltrarEmitirHistogramaEsgotoEconomiaHelper getFiltro(){

		return filtro;
	}

	/**
	 * Método adcionarRelacionamento
	 * <p>
	 * Esse método implementa [mgrb] descrever o que implemntar
	 * </p>
	 * RASTREIO: [OCXXXXX][UCXXXX][SBXXXX]
	 * 
	 * @author Marlos Ribeiro
	 * @since 05/06/2013
	 */
	protected void adcionarRelacionamento(String tabela, String alias, String colunaPk, String colunaFk){

		RelacionamentoInnerJoin realacionamento = new RelacionamentoInnerJoin(tabela, alias, colunaPk, colunaFk);
		if(this.relacionamentos == null) this.relacionamentos = new ArrayList<AbstractRepositorioHistogramaEsgotoEconomiaHelper.RelacionamentoInnerJoin>();
		this.relacionamentos.add(realacionamento);

	}

	protected void adcionarColunaAdcional(String nomeColuna, String alias, Type tipo){

		ColunaAdcional coluna = new ColunaAdcional(nomeColuna, alias, tipo);
		if(colunasAdcionais == null) colunasAdcionais = new ArrayList<AbstractRepositorioHistogramaEsgotoEconomiaHelper.ColunaAdcional>();
		colunasAdcionais.add(coluna);
	}

	/**
	 * {@inheritDoc}
	 */
	public void setFiltro(FiltrarEmitirHistogramaEsgotoEconomiaHelper filtro){
		this.filtro = filtro;
		this.clausulas = montarClausulas();
	}

	public Map<String, Long> obterVolumeASerReduzido(int index, List<HistogramaEsgotoEconomiaDTO> detalhes){

		Map<String, Long> volumeReduzido = new HashMap<String, Long>();

		Long volumeReduzidoMedido = Long.valueOf(0);

		Long volumeReduzidoNaoMedido = Long.valueOf(0);

		HistogramaEsgotoEconomiaDTO detalheTratado = detalhes.get(index);

		// volumeReduzidoMedido = detalheTratado.getTotalVolumeFaturadoMedido();
		//
		// volumeReduzidoNaoMedido = detalheTratado.getTotalVolumeFaturadoNaoMedido();

		volumeReduzidoMedido = detalheTratado.getTotalConsumoMedido();

		volumeReduzidoNaoMedido = detalheTratado.getTotalConsumoNaoMedido();

		int i = 0;

		for(HistogramaEsgotoEconomiaDTO detalhe : detalhes){

			volumeReduzidoMedido = (volumeReduzidoMedido - (detalheTratado.getTotalEconomiasMedido() * detalhe.getDifConsumoFaixa()));

			volumeReduzidoNaoMedido = (volumeReduzidoNaoMedido - (detalheTratado.getTotalEconomiasNaoMedido() * detalhe
							.getDifConsumoFaixa()));


			if(i == (index - 1)){

				break;

			}

			i++;

		}

		volumeReduzido.put("VOLUME_REDUZIDO_MEDIDO", volumeReduzidoMedido);

		volumeReduzido.put("VOLUME_REDUZIDO_NAO_MEDIDO", volumeReduzidoNaoMedido);

		return volumeReduzido;

	}

	public HistogramaEsgotoEconomiaDTO obterVolumeFaturadoPelaFaixa(HistogramaEsgotoEconomiaDTO histogramaDTO,
					List<HistogramaEsgotoEconomiaDTO> dadosHistograma, Integer categoriaIdInt, Session session, int idx)
					throws ControladorException{


		int limite = dadosHistograma.size();

		Long volumeFaturadoMedido = Long.valueOf(0);
		Long volumeFaturadoNaoMedido = Long.valueOf(0);

		Integer inicio = histogramaDTO.getInicio();
		Integer fim = histogramaDTO.getFim();
		HistogramaEsgotoEconomiaDTO histogramaEsgotoEconomiaDTO = null;

		if(inicio.equals(0)){

			Long quantidadeEconomiasMedidoCategoria = new Long("0");
			Long quantidadeEconomiasNaoMedidoCategoria = new Long("0");
			Iterator it = dadosHistograma.iterator();

			while(it.hasNext()){
				histogramaEsgotoEconomiaDTO = (HistogramaEsgotoEconomiaDTO) it.next();
				quantidadeEconomiasMedidoCategoria = quantidadeEconomiasMedidoCategoria
								+ histogramaEsgotoEconomiaDTO.getTotalEconomiasMedido();
				quantidadeEconomiasNaoMedidoCategoria = quantidadeEconomiasNaoMedidoCategoria
								+ histogramaEsgotoEconomiaDTO.getTotalEconomiasNaoMedido();
			}

			histogramaDTO.setTotalVolumeFaturadoMedido(quantidadeEconomiasMedidoCategoria * fim);
			histogramaDTO.setTotalVolumeFaturadoNaoMedido(quantidadeEconomiasNaoMedidoCategoria * fim);

			// Calculo Volumes Medio
			calcularVolumesMedios(histogramaDTO);

			// Calculo das Receitas
			calcularReceitas(session, inicio, fim, categoriaIdInt, histogramaDTO);

		}else{

			// Calculo Volumes Medio
			calcularVolumesMedios(histogramaDTO);

			// Calculo das Receitas
			calcularReceitas(session, inicio, fim, categoriaIdInt, histogramaDTO);

			 int i = idx;
			
			 i = (i + 1);

			// if(i == limite){
			// i = limite - 1;
			// }
			//
			 if(i < dadosHistograma.size()){

				Long somatorioEconomiasPosterioresMedido = new Long("0");
				Long somatorioEconomiasPosterioreNaoMedido = new Long("0");
			
				while(i < dadosHistograma.size()){



					histogramaEsgotoEconomiaDTO = dadosHistograma.get(i);
			
					// volumeFaturadoMedido = (volumeFaturadoMedido +
					// histogramaEsgotoEconomiaDTO.getTotalEconomiasMedido());
					//
					// volumeFaturadoNaoMedido = (volumeFaturadoNaoMedido +
					// histogramaEsgotoEconomiaDTO.getTotalEconomiasNaoMedido());

					somatorioEconomiasPosterioresMedido = somatorioEconomiasPosterioresMedido
									+ histogramaEsgotoEconomiaDTO.getTotalEconomiasMedido();
					somatorioEconomiasPosterioreNaoMedido = somatorioEconomiasPosterioreNaoMedido
									+ histogramaEsgotoEconomiaDTO.getTotalEconomiasNaoMedido();
			
					i++;
			
				}
			

			
				Map<String, Long> volumeASerReduzido = this.obterVolumeASerReduzido(idx, dadosHistograma);

				histogramaEsgotoEconomiaDTO = dadosHistograma.get(idx);
			
				// volumeFaturadoMedido = ((volumeFaturadoMedido *
				// histogramaEsgotoEconomiaDTO.getDifConsumoFaixa()) + volumeASerReduzido
				// .get("VOLUME_REDUZIDO_MEDIDO"));
				//
				// volumeFaturadoNaoMedido = (volumeFaturadoNaoMedido *
				// histogramaEsgotoEconomiaDTO.getDifConsumoFaixa())
				// + volumeASerReduzido.get("VOLUME_REDUZIDO_NAO_MEDIDO");

				volumeFaturadoMedido = volumeASerReduzido.get("VOLUME_REDUZIDO_MEDIDO")
								+ (somatorioEconomiasPosterioresMedido * histogramaEsgotoEconomiaDTO.getDifConsumoFaixa());
			
				volumeFaturadoNaoMedido = volumeASerReduzido.get("VOLUME_REDUZIDO_NAO_MEDIDO")
								+ (somatorioEconomiasPosterioreNaoMedido * histogramaEsgotoEconomiaDTO.getDifConsumoFaixa());
			

			 }else{

				histogramaEsgotoEconomiaDTO = dadosHistograma.get(i - 1);
			
				Map<String, Long> volumeASerReduzido = this.obterVolumeASerReduzido(idx, dadosHistograma);
			
				// volumeFaturadoMedido = ((volumeFaturadoMedido *
				// histogramaEsgotoEconomiaDTO.getDifConsumoFaixa()) + volumeASerReduzido
				// .get("VOLUME_REDUZIDO_MEDIDO"));
				//
				// volumeFaturadoNaoMedido = (volumeFaturadoNaoMedido *
				// histogramaEsgotoEconomiaDTO.getDifConsumoFaixa())
				// + volumeASerReduzido.get("VOLUME_REDUZIDO_NAO_MEDIDO");

				volumeFaturadoMedido = volumeASerReduzido.get("VOLUME_REDUZIDO_MEDIDO");

				volumeFaturadoNaoMedido = volumeASerReduzido.get("VOLUME_REDUZIDO_NAO_MEDIDO");

			
			 }

			histogramaDTO.setTotalVolumeFaturadoMedido(volumeFaturadoMedido);
			histogramaDTO.setTotalVolumeFaturadoNaoMedido(volumeFaturadoNaoMedido);

		}


		return histogramaDTO;

	}

	private Integer obterDiferencaConsumoFaixa(ConsumoFaixaCategoria faixa, int index){

		Integer difConsumoFaixa = null;

		if(index == 0){

			difConsumoFaixa = faixa.getNumeroFaixaFim();

		}else{

			difConsumoFaixa = ((faixa.getNumeroFaixaFim() - faixa.getNumeroFaixaInicio()) + 1);

		}

		return difConsumoFaixa;

	}


	private List<HistogramaEsgotoEconomiaDTO> retornarListaDados(List<HistogramaEsgotoEconomiaDTO> dadosHistograma, Session session,
					Collection<ConsumoFaixaCategoria> faixasConsumo){
		
		HistogramaEsgotoEconomiaDTO  retornoHistogramaEsgotoEconomiaDTO  = null;

		List<HistogramaEsgotoEconomiaDTO> retornoListaHistogramaEsgotoEconomiaDTO = new ArrayList<HistogramaEsgotoEconomiaDTO>();

		Iterator it = dadosHistograma.iterator();
		Iterator itt = faixasConsumo.iterator();

		int i = 0;


		while(it.hasNext()){

			HistogramaEsgotoEconomiaDTO histogramaEsgotoEconomiaDTO = (HistogramaEsgotoEconomiaDTO) it.next();

			if(itt.hasNext()){

				ConsumoFaixaCategoria consumoFaixaCategoria = (ConsumoFaixaCategoria) itt.next();

				Integer difConsumoFaixa = this.obterDiferencaConsumoFaixa(consumoFaixaCategoria, i);
				histogramaEsgotoEconomiaDTO.setDifConsumoFaixa(difConsumoFaixa);

				try{
					retornoHistogramaEsgotoEconomiaDTO = obterVolumeFaturadoPelaFaixa(histogramaEsgotoEconomiaDTO, dadosHistograma,
									consumoFaixaCategoria.getCategoria().getId(), session, i);

				}catch(ControladorException e){
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}


			retornoListaHistogramaEsgotoEconomiaDTO.add(retornoHistogramaEsgotoEconomiaDTO);

			i++;

		}
		
		
      return retornoListaHistogramaEsgotoEconomiaDTO;
 

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @param session
	 */
	public List<HistogramaEsgotoEconomiaDTO> normalizarResultado(List<Object[]> dadosHistogramaTemp, Session session){

		LinkedHashMap mapConsumoFaixaCategoria = filtro.getLinkedHashMapConsumoFaixaCategoria();
		SortedSet<String> keys = new TreeSet<String>(mapConsumoFaixaCategoria.keySet());
		Collection<ConsumoFaixaCategoria> faixasConsumo;
		List<HistogramaEsgotoEconomiaDTO> dadosHistograma = new ArrayList<HistogramaEsgotoEconomiaDTO>();
		List<HistogramaEsgotoEconomiaDTO> dadosHistogramaRetoro = new ArrayList<HistogramaEsgotoEconomiaDTO>();
		HistogramaEsgotoEconomiaDTO histogramaEsgotoEconomiaDTO = null;
		Integer inicio;
		Integer fim;
		Integer categoriaIdInt;
		int i = 0;
		Object[] dadosHistogramaArray;
		Object[] dados;
		do{
			for(Object categoriaId : keys){

				categoriaIdInt = Integer.valueOf(categoriaId.toString());

				dadosHistograma.clear();

				faixasConsumo = (Collection<ConsumoFaixaCategoria>) mapConsumoFaixaCategoria.get(categoriaId);

				for(ConsumoFaixaCategoria consumoFaixaCategoria : faixasConsumo){

					inicio = consumoFaixaCategoria.getNumeroFaixaInicio();
					fim = consumoFaixaCategoria.getNumeroFaixaFim();
					if(dadosHistogramaTemp.size() == 0) dadosHistogramaArray = null;
					else
					dadosHistogramaArray = dadosHistogramaTemp.size() == i ? dadosHistogramaTemp.get(i - 1)
									: dadosHistogramaTemp.get(i);
					int colunasAdcionaisSize = Util.isVazioOrNulo(colunasAdcionais) ? 0 : colunasAdcionais.size();

					// Avalia se existe quebra de totalizador
					if(dadosHistogramaArray != null //
									&& categoriaIdInt.equals(dadosHistogramaArray[colunasAdcionaisSize + 1])//
									&& inicio.equals(dadosHistogramaArray[colunasAdcionaisSize + 3]) //
									&& fim.equals(dadosHistogramaArray[colunasAdcionaisSize + 4])){
						dados = dadosHistogramaArray;
						i++;
					}else{
						int idx;
						if(i == 0) idx = 0;
						if(dadosHistogramaTemp.size() == i) idx = i - 1;
						else idx = i;
						dados = dadosHistogramaTemp.get(idx).clone();
						dados[colunasAdcionaisSize + 1] = categoriaId == null ? 0 : Integer.valueOf(categoriaId.toString());
						dados[colunasAdcionaisSize + 2] = consumoFaixaCategoria.getCategoria().getDescricao();
						dados[colunasAdcionaisSize + 3] = consumoFaixaCategoria.getNumeroFaixaInicio();
						dados[colunasAdcionaisSize + 4] = consumoFaixaCategoria.getNumeroFaixaFim();
						for(int j = colunasAdcionaisSize + 5; j < dados.length; j++){
							dados[j] = 0L;
						}
					}


					histogramaEsgotoEconomiaDTO = criarEmitirHistogramaAguaEsgotoEconomiaHelper(dados, session);


					dadosHistograma.add(histogramaEsgotoEconomiaDTO);

				}



				dadosHistogramaRetoro.addAll(retornarListaDados(dadosHistograma, session, faixasConsumo));

			}

		}while(i < dadosHistogramaTemp.size());
		return dadosHistogramaRetoro;
	}

	/**
	 * {@inheritDoc}
	 */
	private List<Integer[]> getItensQuebraDePagina(String totalizadorAnterior, String totalizadorCorrente){

		String[] itensAnteriores = totalizadorAnterior.split(DIVISOR_TOTALIZADOR_ITEM);
		String[] itensCorrentes = totalizadorCorrente.split(DIVISOR_TOTALIZADOR_ITEM);
		if(itensAnteriores.length > itensCorrentes.length) throw new IllegalStateException("Totalizadores de naturezas diferentes.");
		if(totalizadorAnterior.equals(totalizadorCorrente)) return null;

		List<Integer> idsItensAnteriores = new ArrayList<Integer>();
		List<Integer> idsItensCorrentes = new ArrayList<Integer>();
		String itensAnt;
		String itensCorr;
		for(int i = 0; i < itensAnteriores.length; i++){
			itensAnt = itensAnteriores[i];
			itensCorr = itensCorrentes[i];
			idsItensAnteriores.add(Integer.valueOf(itensAnt.substring(0, itensAnt.indexOf(DIVISOR_TOTALIZADOR_ID_DESC)).trim()));
			idsItensCorrentes.add(Integer.valueOf(itensCorr.substring(0, itensCorr.indexOf(DIVISOR_TOTALIZADOR_ID_DESC)).trim()));
		}

		List<Integer[]> idsItensTotalizadores = new ArrayList<Integer[]>();
		Integer[] ids;
		for(int i = idsItensAnteriores.size() - 2; i >= 0; i--){
			if(!idsItensAnteriores.get(i).equals(idsItensCorrentes.get(i))){
				ids = new Integer[i + 1];
				idsItensAnteriores.subList(0, i + 1).toArray(ids);
				idsItensTotalizadores.add(ids);
			}
		}
		return idsItensTotalizadores;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<HistogramaEsgotoEconomiaDTO> executarPesquisa(String totalizadorAnterior, String totalizadorCorrente, Session session){

		List<Integer[]> listaQuebras = getItensQuebraDePagina(totalizadorAnterior, totalizadorCorrente);
		List<HistogramaEsgotoEconomiaDTO> resultado = new ArrayList<HistogramaEsgotoEconomiaDTO>();
		RepositorioHistogramaEsgotoEconomiaHelper repositorioQuebraHelper;
		for(Integer[] itemQuebra : listaQuebras){
			repositorioQuebraHelper = RepositorioHistogramaEsgotoEconomiaHelperFactory.getGerador(criarFiltroQuebra(filtro, itemQuebra));
			resultado.addAll(repositorioQuebraHelper.executarPesquisa(session));
		}
		return resultado;
	}

	/**
	 * Método setarQuebraFiltro
	 * <p>
	 * Esse método implementa [mgrb] descrever o que implemntar
	 * </p>
	 * RASTREIO: [OCXXXXX][UCXXXX][SBXXXX]
	 * 
	 * @param itemQuebra
	 * @author Marlos Ribeiro
	 * @param filtro
	 * @return
	 * @since 11/06/2013
	 */
	protected abstract FiltrarEmitirHistogramaEsgotoEconomiaHelper criarFiltroQuebra(FiltrarEmitirHistogramaEsgotoEconomiaHelper filtro,
					Integer[] itemQuebra);

	/*
	 * --------------------------------------------------------------------------------------------
	 * INNER CLASSES
	 * --------------------------------------------------------------------------------------------
	 */
	class RelacionamentoInnerJoin {
		private String tabela;
		private String alias;
		private String colunaPk;
		private String colunaFk;
		/**
		 * AbstractRepositorioHistogramaEsgotoEconomiaHelper.RelacionamentoInnerJoin
		 * <p>
		 * Esse método Constroi uma instância de
		 * AbstractRepositorioHistogramaEsgotoEconomiaHelper.RelacionamentoInnerJoin.
		 * </p>
		 * 
		 * @author Marlos Ribeiro
		 * @since 05/06/2013
		 */
		public RelacionamentoInnerJoin(String tabela, String alias, String colunaPk, String colunaFk) {

			this.tabela = tabela;
			this.alias = alias;
			this.colunaPk = colunaPk;
			this.colunaFk = colunaFk;
		}
	}

	class ColunaAdcional {

		private String nome;

		private String alias;

		private Type tipo;

		/**
		 * AbstractRepositorioHistogramaEsgotoEconomiaHelper.ColunaAdcional
		 * <p>
		 * Esse método Constroi uma instância de
		 * AbstractRepositorioHistogramaEsgotoEconomiaHelper.ColunaAdcional.
		 * </p>
		 * 
		 * @author Marlos Ribeiro
		 * @since 05/06/2013
		 */
		public ColunaAdcional(String nome, String alias, Type tipo) {

			this.alias = alias;
			this.nome = nome;
			this.tipo = tipo;
		}
	}

	class ClausulaWare {

		private String clausula;

		private String alias;

		private Object valor;

		/**
		 * AbstractRepositorioHistogramaEsgotoEconomiaHelper.ClausulaWare
		 * <p>
		 * Esse método Constroi uma instância de
		 * AbstractRepositorioHistogramaEsgotoEconomiaHelper.ClausulaWare.
		 * </p>
		 * 
		 * @author Marlos Ribeiro
		 * @since 10/06/2013
		 */
		public ClausulaWare(String clausula, String alias, Object valor) {

			this.clausula = clausula;
			this.alias = alias;
			this.valor = valor;
		}
	}
}
