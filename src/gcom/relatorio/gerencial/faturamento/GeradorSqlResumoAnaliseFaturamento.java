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

package gcom.relatorio.gerencial.faturamento;

import gcom.gerencial.bean.InformarDadosGeracaoRelatorioConsultaHelper;
import gcom.util.ConstantesSistema;
import gcom.util.GeradorSqlRelatorio;
import gcom.util.Util;

import java.util.ArrayList;
import java.util.List;

public class GeradorSqlResumoAnaliseFaturamento {

	public static enum RestricaoTipoEnum {
		RESTRICAO_GERENCIA_REGIONAL, RESTRICAO_UNIDADE_NEGOCIO, RESTRICAO_ELO_POLO, RESTRICAO_LOCALIDADE, RESTRICAO_SETOR_COMERCIAL
	}

	private String sqlTemplate = "select  #ID_CAMPO# as idCampo, #DESC_CAMPO# as descCampo, #SOMATORIOS# from #TABELAS# where #CONDICIONAL_FILTRO# #CONDICIONAL_COLECOES# #CONDICIONAL_RESTRICAO# group by  #ID_CAMPO#, #DESC_CAMPO# order by #DESC_CAMPO#";

	// Solução Emergencial (Provisória)
	private String sqlTemplatePorESTADO = "select  #ID_CAMPO# as idCampo, #DESC_CAMPO# as descCampo, #SOMATORIOS# from #TABELAS# where #CONDICIONAL_FILTRO# #CONDICIONAL_COLECOES# #CONDICIONAL_RESTRICAO# group by  #ID_CAMPO#";

	private Integer opcaoTotalizacao;

	private String somatorios = "";

	private String tabelas = "";

	private List<String[]> agrupamentos;

	private String condicionalColecoes = "";

	private String condicionalFiltro = "";

	/**
	 * Construtor default.
	 */
	public GeradorSqlResumoAnaliseFaturamento(InformarDadosGeracaoRelatorioConsultaHelper dadosFiltroHelper) {

		this.opcaoTotalizacao = dadosFiltroHelper.getOpcaoTotalizacao();
		this.somatorios = construirSomatorios();
		this.tabelas = construirTabelas();
		this.condicionalFiltro = construirCondicionaisFiltro(dadosFiltroHelper);
		this.condicionalColecoes = construirCondicionaisColecoes(dadosFiltroHelper);
		construirAgrupamentos(opcaoTotalizacao);
	}

	/**
	 * Monta o trecho da query com as tabelas que estão sendo consultadas
	 * 
	 * @author Luciano Galvao
	 * @date 23/07/2012
	 * @return
	 */
	private String construirTabelas(){

		StringBuffer buffer = new StringBuffer();
		buffer.append(" resumo_faturamento_simulacao re ");
		buffer.append("left join gerencia_regional gr on re.greg_id = gr.greg_id ");
		buffer.append("left join localidade l on re.loca_id = l.loca_id ");
		buffer.append("left join setor_comercial sc on re.stcm_id = sc.stcm_id ");
		buffer.append("left join quadra q on re.qdra_id = q.qdra_id ");
		buffer.append("left join categoria cat on re.catg_id = cat.catg_id ");
		buffer.append("left join localidade elo on l.loca_cdelo = elo.loca_id ");
		buffer.append("left join unidade_negocio uni on re.uneg_id = uni.uneg_id ");

		if(ConstantesSistema.CODIGO_ESTADO_GRUPO_FATURAMENTO == opcaoTotalizacao.intValue()
						|| ConstantesSistema.CODIGO_GRUPO_FATURAMENTO == opcaoTotalizacao.intValue()){
			buffer.append("left join faturamento_grupo fg on fg.ftgr_id = re.ftgr_id ");
		}

		return buffer.toString();
	}

	/**
	 * Monta o trecho da query com os somatórios que serão retornados
	 * 
	 * @author Luciano Galvao
	 * @date 23/07/2012
	 * @return
	 */
	private String construirSomatorios(){

		StringBuffer buffer = new StringBuffer();

		buffer.append(" sum(re.rfts_qtcontas) as somatorio1, ");
		buffer.append(" sum(re.rfts_qteconomia) as somatorio2, ");
		buffer.append(" sum(re.rfts_nnconsumoagua) as somatorio3, ");
		buffer.append(" sum(re.rfts_vlagua) as somatorio4, ");
		buffer.append(" sum(re.rfts_nnconsumoesgoto) as somatorio5, ");
		buffer.append(" sum(re.rfts_vlesgoto) as somatorio6, ");
		buffer.append(" sum(re.rfts_vldebitos) as somatorio7, ");
		buffer.append(" sum(re.rfts_vlcreditos) as somatorio8 ");

		return buffer.toString();
	}

	private String construirCondicionaisColecoes(InformarDadosGeracaoRelatorioConsultaHelper dadosFiltroHelper){

		return GeradorSqlRelatorio.condicionaisListas(dadosFiltroHelper);
	}

	/**
	 * Define os campos de agrupamento que serão utilizados nas consultas, seguindo sempre o padrão
	 * <idCampo, descricaoCampo>
	 * 
	 * @author Luciano Galvao
	 * @date 23/07/2012
	 * @param opcaoTotalizacao
	 */
	private void construirAgrupamentos(int opcaoTotalizacao){

		this.agrupamentos = new ArrayList<String[]>();

		switch(opcaoTotalizacao){
			case ConstantesSistema.CODIGO_ESTADO_GRUPO_FATURAMENTO:
			case ConstantesSistema.CODIGO_GRUPO_FATURAMENTO:
				agrupamentos.add(new String[] {"fg.ftgr_id", "fg.ftgr_dsfaturamentogrupo"});
				break;
			case ConstantesSistema.CODIGO_ESTADO:
				agrupamentos.add(new String[] {"1", "'ESTADO'"});
				break;
			case ConstantesSistema.CODIGO_ESTADO_GERENCIA_REGIONAL:
			case ConstantesSistema.CODIGO_GERENCIA_REGIONAL:
				agrupamentos.add(new String[] {"gr.greg_id", "gr.greg_nmregional"});
				break;
			case ConstantesSistema.CODIGO_ESTADO_UNIDADE_NEGOCIO:
				agrupamentos.add(new String[] {"uni.uneg_id", "uni.uneg_nmunidadenegocio"});
				break;
			case ConstantesSistema.CODIGO_ESTADO_ELO_POLO:
				agrupamentos.add(new String[] {"gr.greg_id", "gr.greg_nmregional"});
				agrupamentos.add(new String[] {"uni.uneg_id", "uni.uneg_nmunidadenegocio"});
				agrupamentos.add(new String[] {"elo.loca_id", "elo.loca_nmlocalidade"});
				break;
			case ConstantesSistema.CODIGO_ESTADO_LOCALIDADE:
				agrupamentos.add(new String[] {"gr.greg_id", "gr.greg_nmregional"});
				agrupamentos.add(new String[] {"uni.uneg_id", "uni.uneg_nmunidadenegocio"});
				agrupamentos.add(new String[] {"elo.loca_id", "elo.loca_nmlocalidade"});
				agrupamentos.add(new String[] {"l.loca_id", "l.loca_nmlocalidade"});
				break;
			case ConstantesSistema.CODIGO_GERENCIA_REGIONAL_ELO_POLO:
			case ConstantesSistema.CODIGO_UNIDADE_NEGOCIO_ELO_POLO:
			case ConstantesSistema.CODIGO_ELO_POLO:
				agrupamentos.add(new String[] {"elo.loca_id", "elo.loca_nmlocalidade"});
				break;
			case ConstantesSistema.CODIGO_GERENCIA_REGIONAL_LOCALIDADE:
			case ConstantesSistema.CODIGO_UNIDADE_NEGOCIO_LOCALIDADE:
				agrupamentos.add(new String[] {"elo.loca_id", "elo.loca_nmlocalidade"});
				agrupamentos.add(new String[] {"l.loca_id", "l.loca_nmlocalidade"});
				break;
			case ConstantesSistema.CODIGO_UNIDADE_NEGOCIO:
				agrupamentos.add(new String[] {"uni.uneg_id", "uni.uneg_nmunidadenegocio"});
				break;
			case ConstantesSistema.CODIGO_ELO_POLO_LOCALIDADE:
			case ConstantesSistema.CODIGO_LOCALIDADE:
				agrupamentos.add(new String[] {"l.loca_id", "l.loca_nmlocalidade"});
				break;
			case ConstantesSistema.CODIGO_ELO_POLO_SETOR_COMERCIAL:
				agrupamentos.add(new String[] {"l.loca_id", "l.loca_nmlocalidade"});
				agrupamentos.add(new String[] {"sc.stcm_id", "sc.stcm_nmsetorcomercial"});
				break;
			case ConstantesSistema.CODIGO_LOCALIDADE_SETOR_COMERCIAL:
			case ConstantesSistema.CODIGO_SETOR_COMERCIAL:
				agrupamentos.add(new String[] {"sc.stcm_id", "sc.stcm_nmsetorcomercial"});
				break;
			case ConstantesSistema.CODIGO_LOCALIDADE_QUADRA:
				agrupamentos.add(new String[] {"sc.stcm_id", "sc.stcm_nmsetorcomercial"});
				agrupamentos.add(new String[] {"q.qdra_id", "q.qdra_nnquadra"});
				break;
			case ConstantesSistema.CODIGO_SETOR_COMERCIAL_QUADRA:
			case ConstantesSistema.CODIGO_QUADRA:
				agrupamentos.add(new String[] {"q.qdra_id", "q.qdra_nnquadra"});
				break;
		}
	}

	private String construirCondicionaisFiltro(InformarDadosGeracaoRelatorioConsultaHelper dadosFiltroHelper){

		StringBuffer buffer = new StringBuffer();
		if(!Util.isVazioOuBrancoOuZero(dadosFiltroHelper.getAnoMesReferencia())){
			buffer.append("re.rfts_amreferencia = ");
			buffer.append(dadosFiltroHelper.getAnoMesReferencia());
		}
		if(dadosFiltroHelper.getFaturamentoGrupo() != null){
			if(buffer.length() > 0){
				buffer.append(" AND ");
			}
			buffer.append("fg.ftgr_id = ");
			buffer.append(dadosFiltroHelper.getFaturamentoGrupo().getId());
		}
		if(dadosFiltroHelper.getGerenciaRegional() != null){
			if(buffer.length() > 0){
				buffer.append(" AND ");
			}
			buffer.append("re.greg_id = ");
			buffer.append(dadosFiltroHelper.getGerenciaRegional().getId());
		}
		if(dadosFiltroHelper.getLocalidade() != null){
			if(buffer.length() > 0){
				buffer.append(" AND ");
			}
			buffer.append("re.loca_id = ");
			buffer.append(dadosFiltroHelper.getLocalidade().getId());
		}
		if(dadosFiltroHelper.getSetorComercial() != null){
			if(buffer.length() > 0){
				buffer.append(" AND ");
			}
			buffer.append("re.stcm_id = ");
			buffer.append(dadosFiltroHelper.getSetorComercial().getId());
		}
		if(dadosFiltroHelper.getQuadra() != null){
			if(buffer.length() > 0){
				buffer.append(" AND ");
			}
			buffer.append("re.qdra_id = ");
			buffer.append(dadosFiltroHelper.getQuadra().getId());
		}
		if(dadosFiltroHelper.getEloPolo() != null){
			if(buffer.length() > 0){
				buffer.append(" AND ");
			}
			buffer.append("elo.loca_id = ");
			buffer.append(dadosFiltroHelper.getEloPolo().getId());
		}
		if(dadosFiltroHelper.getUnidadeNegocio() != null){
			if(buffer.length() > 0){
				buffer.append(" AND ");
			}
			buffer.append("re.uneg_id = ");
			buffer.append(dadosFiltroHelper.getUnidadeNegocio().getId());
		}
		if(dadosFiltroHelper.getTipoAnaliseFaturamento() != null){
			if(buffer.length() > 0){
				buffer.append(" AND ");
			}
			buffer.append("re.rfts_icsimulacao = ");
			buffer.append(dadosFiltroHelper.getTipoAnaliseFaturamento());
		}

		return buffer.toString();
	}

	/**
	 * Retorna a lista de SQLs que devem ser executadas para consultar as informações do relatório.
	 * As queries devem ser executadas na ordem retornada. Para cada query, é necessário chamar o
	 * método "adicionarCondicionalRestricao" antes de executá-la. Este controle deve ser realizado
	 * pelo Repositório, pois esta restrição depende do retorno de cada consulta
	 * 
	 * @author Luciano Galvao
	 * @date 23/07/2012
	 * @return
	 */
	public List<String> getComandosSQLResumoAnaliseFaturamento(){

		String sql = "";
		List<String> comandosSql = new ArrayList<String>();

		if(!Util.isVazioOrNulo(agrupamentos)){
			for(String[] agrupamento : agrupamentos){

				if(agrupamento != null){
					// Solução Emergencial (Provisória)
					// Está dando erro no postgres quando é montado o group by com a string 'ESTADO'
					String query = "";
					if(agrupamento[1].equals("'ESTADO'")){
						query = sqlTemplatePorESTADO;
					}else{
						query = sqlTemplate;
					}

					sql = query.replaceAll("#ID_CAMPO#", agrupamento[0])//
									.replaceAll("#DESC_CAMPO#", agrupamento[1])//
									.replaceAll("#SOMATORIOS#", this.somatorios)//
									.replaceAll("#TABELAS#", tabelas)//
									.replaceAll("#CONDICIONAL_FILTRO#", condicionalFiltro)//
									.replaceAll("#CONDICIONAL_COLECOES#", condicionalColecoes);//

					comandosSql.add(sql);
				}
			}
		}

		return comandosSql;

	}

	/**
	 * Adiciona a condição de restrição devido ao agrupamento hierárquico do relatório. Se a
	 * consulta não tiver restrição, deve ser passado Null nos parâmetros restricaoTipo e
	 * idRestricao. Desta forma, não será criada uma restrição na consulta
	 * 
	 * @author Luciano Galvao
	 * @date 23/07/2012
	 * @param sql
	 * @param restricaoTipo
	 * @param idRestricao
	 * @return
	 */
	public String adicionarCondicionalRestricao(String sql, RestricaoTipoEnum restricaoTipo, Integer idRestricao){

		sql = sql.replaceAll("#CONDICIONAL_RESTRICAO#", construirCondicionalRestricao(restricaoTipo, idRestricao));
		return sql;
	}

	/**
	 * Monta a condicional com o restrição de agrupamento
	 * 
	 * @author Luciano Galvao
	 * @date 23/07/2012
	 * @param restricaoTipo
	 * @param idRestricao
	 * @return
	 */
	private String construirCondicionalRestricao(RestricaoTipoEnum restricaoTipo, Integer idRestricao){

		StringBuilder retorno = new StringBuilder(" ");

		if((restricaoTipo != null) && (idRestricao != null)){
			retorno.append(" AND ");

			switch(restricaoTipo){
				case RESTRICAO_GERENCIA_REGIONAL:
					retorno.append("re.greg_id = " + idRestricao);
					break;
				case RESTRICAO_UNIDADE_NEGOCIO:
					retorno.append("re.uneg_id = " + idRestricao);
					break;
				case RESTRICAO_ELO_POLO:
					retorno.append("elo.loca_id = " + idRestricao);
					break;
				case RESTRICAO_LOCALIDADE:
					retorno.append("re.loca_id = " + idRestricao);
					break;
				case RESTRICAO_SETOR_COMERCIAL:
					retorno.append("re.stcm_id = " + idRestricao);
					break;
				default:
					break;
			}
		}

		return retorno.toString();
	}

	protected String getNomeCampoFiltro(){

		return "medt_id";
	}

	public String getSomatorios(){

		return somatorios;
	}

	public void setSomatorios(String somatorios){

		this.somatorios = somatorios;
	}
}
