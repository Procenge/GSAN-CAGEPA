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
 * Adriano Britto Siqueira
 * Alexandre Santos Cabral
 * Ana Carolina Alves Breda
 * Ana Maria Andrade Cavalcante
 * Aryed Lins de Araújo
 * Bruno Leonardo Rodrigues Barros
 * Carlos Elmano Rodrigues Ferreira
 * Cláudio de Andrade Lira
 * Denys Guimarães Guenes Tavares
 * Eduardo Breckenfeld da Rosa Borges
 * Fabíola Gomes de Araújo
 * Flávio Leonardo Cavalcanti Cordeiro
 * Francisco do Nascimento Júnior
 * Homero Sampaio Cavalcanti
 * Ivan Sérgio da Silva Júnior
 * José Edmar de Siqueira
 * José Thiago Tenório Lopes
 * Kássia Regina Silvestre de Albuquerque
 * Leonardo Luiz Vieira da Silva
 * Márcio Roberto Batista da Silva
 * Maria de Fátima Sampaio Leite
 * Micaela Maria Coelho de Araújo
 * Nelson Mendonça de Carvalho
 * Newton Morais e Silva
 * Pedro Alexandre Santos da Silva Filho
 * Rafael Corrêa Lima e Silva
 * Rafael Francisco Pinto
 * Rafael Koury Monteiro
 * Rafael Palermo de Araújo
 * Raphael Veras Rossiter
 * Roberto Sobreira Barbalho
 * Rodrigo Avellar Silveira
 * Rosana Carvalho Barbosa
 * Sávio Luiz de Andrade Cavalcante
 * Tai Mu Shih
 * Thiago Augusto Souza do Nascimento
 * Tiago Moreno Rodrigues
 * Vivianne Barbosa Sousa
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

package gcom.util;

import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoSituacao;
import gcom.cadastro.cliente.EsferaPoder;
import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.imovel.ImovelPerfil;
import gcom.cobranca.DocumentoTipo;
import gcom.gerencial.bean.InformarDadosGeracaoRelatorioConsultaHelper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class GeradorSqlRelatorio {

	private String sqlTemplate = "select  #CAMPO# as campo #NOME_CAMPO_FIXO# as descricao #SOMATORIO# as somatorio from #NOME_TABELA_FIXO# #TABELA# where #CONDICIONAL_FIXO# #CONDICIONAL_RESUMO# #CONDICIONAL_LIGACAO# #CONDICIONAL_RESTRICAO# #GROUP_BY#";

	private String condicionalFixo = new String(" re.ltan_id = la.ltan_id ");

	// private String somatorio = new String(",sum(re.real_qtdmedicao) as somatorio ");

	private String somatorio = new String(",sum(re.real_qtdmedicao) ");

	// private String nomeCampoFixo = new
	// String(",la.ltan_dsleituraanormalidade as descricao, re.medt_id as idMedicaoTipo ");

	private String nomeCampoFixo = new String(",la.ltan_dsleituraanormalidade , re.medt_id  ");

	private String nomeTabelaFixo = new String("resumo_anormalidade_leitura re, " + "leitura_anormalidade la ");

	private String nomeTabelaFixoTotal = new String("resumo_anormalidade_leitura re, " + "leitura_anormalidade la ");

	private String condicionalResumo = "";

	public static final int ANORMALIDADE_LEITURA = 1;

	public static final int ANALISE_FATURAMENTO = 2;

	public static final int PENDENCIA = 3;

	public static final int ANORMALIDADE_CONSUMO = 4;

	public static final int RESUMO_LIGACOES_ECONOMIAS = 5;

	public static final int RESUMO_FATURAMENTO = 6;

	public static final int RESUMO_ARRECADACAO = 7;

	public static final int RESUMO_PENDENCIA = 8;

	public static final int RESUMO_REGISTRO_ATENDIMENTO = 9;

	public static final int RESUMO_HIDROMETRO = 10;

	private Collection<GeradorSqlRelatorio.ConfiguracaoGeradorSqlRelatorio> configuracoes = new ArrayList();

	private String orderBy = "";

	public GeradorSqlRelatorio() {

	}

	// ------------ NIVEL 1

	public String sqlNivelUmPendencia(){

		String sql = "";

		// String nomeCampoFixo = this.getNomeCampoFixo();
		String nomeTabelaFixo = this.getNomeTabelaFixo();

		// String nomeTabelaFixoTotal = this.getNomeTabelaFixoTotal();
		String groupBy = " group by 1, b.cgtp_dscategoriatipo, c.catg_dscategoria, 4 , re.rpen_amreferencia ";
		String orderBy = " order by 1,2,3,4,5 ";
		String condicionalFixo = this.getCondicionalFixo();

		if(this.configuracoes != null && !this.configuracoes.isEmpty()){

			for(ConfiguracaoGeradorSqlRelatorio configuracao : this.configuracoes){

				String condicionalLigacao = configuracao.getCondicionalLigacao();
				String campo = configuracao.getCampo();
				String tabela = configuracao.getTabela();
				String condicionalRestricao = configuracao.getCondicionalRestricao();

				sql = sql + "select "
								+ "\'AA-ESTADO\' as estado, b.cgtp_dscategoriatipo as tipoCategoria, c.catg_dscategoria as Categoria, "
								+ campo + "re.rpen_amreferencia as anoMesReferencia"

								+ somatorio + " from " + nomeTabelaFixo + tabela + "where " + condicionalFixo + condicionalLigacao
								+ this.condicionalResumo + condicionalRestricao + groupBy + " union ";

			}
			// tirar o union do final da query
			// Botar o group by no final
			sql = sql.substring(0, sql.length() - 6) + orderBy;

		}

		return sql;
	}

	public String sqlNivelUm(String nomeCampoFixo, String nomeTabelaFixo, String nomeTabelaFixoTotal, String campo, String tabela,
					String condicionalLigacao, String condicionalRestricao, boolean uniao){

		String sql = "";
		String sqlUnion = "";
		String condicionalLigacaoSemAnd = "";
		String groupBy = "";

		if(condicionalLigacao.length() > 0){
			condicionalLigacaoSemAnd = condicionalLigacao.substring(3, condicionalLigacao.length());
		}

		if(uniao){
			groupBy = " group by " + campo + nomeCampoFixo + "";
		}

		sql = sqlTemplate.replaceAll("#CAMPO#", campo)//
						.replaceAll("#NOME_CAMPO_FIXO#", nomeCampoFixo)//
						.replaceAll("#SOMATORIO#", somatorio)//
						.replaceAll("#NOME_TABELA_FIXO#", nomeTabelaFixo)//
						.replaceAll("#TABELA#", tabela)//
						.replaceAll("#CONDICIONAL_FIXO#", condicionalFixo)//
						.replaceAll("#CONDICIONAL_RESUMO#", condicionalResumo)//
						.replaceAll("#CONDICIONAL_LIGACAO#", condicionalLigacao)//
						.replaceAll("#CONDICIONAL_RESTRICAO#", condicionalRestricao)//
						.replaceAll("#GROUP_BY#", groupBy)//
						.replaceAll("#ORDER_BY#", "ORDER BY " + orderBy);

		if(uniao){
			sqlUnion = "union " + " select " + campo + " as campo, \'AA - TOTAL\' as descricao " + somatorio + " as somatorio from "
							+ nomeTabelaFixoTotal + tabela + "where " + condicionalLigacao + this.condicionalResumo + condicionalRestricao
							+ " group by " + campo;
		}

		return sql + sqlUnion;

	}

	public String getCondicionalFixo(){

		return condicionalFixo;
	}

	public GeradorSqlRelatorio(int tipoConsulta, InformarDadosGeracaoRelatorioConsultaHelper dadosFiltroHelper) {

		switch(tipoConsulta){
			case GeradorSqlRelatorio.ANORMALIDADE_LEITURA:
				this.condicionalFixo = " re.ltan_id = la.ltan_id ";
				this.somatorio = ",sum(re.real_qtdmedicao) ";
				this.nomeCampoFixo = ",la.ltan_dsleituraanormalidade  ";
				this.nomeTabelaFixo = "resumo_anormalidade_leitura re, leitura_anormalidade la ";
				this.nomeTabelaFixoTotal = "resumo_anormalidade_leitura re ,leitura_anormalidade la ";
				break;

			case GeradorSqlRelatorio.RESUMO_LIGACOES_ECONOMIAS:
				configurarQueryLigacoesEconomia(dadosFiltroHelper);
				break;

			case GeradorSqlRelatorio.ANORMALIDADE_CONSUMO:
				this.condicionalFixo = " re.csan_id = ca.csan_id ";
				this.somatorio = ",sum(re.reac_qtdligacao) as somatorio ";
				this.nomeCampoFixo = ",ca.csan_dsconsumoanormalidade as descricao ";
				this.nomeTabelaFixo = "resumo_anormalidade_consumo re, consumo_anormalidade ca ";
				this.nomeTabelaFixoTotal = "resumo_anormalidade_consumo as re ,consumo_anormalidade ca ";
				break;

			case GeradorSqlRelatorio.ANALISE_FATURAMENTO:
				this.condicionalFixo = "";
				this.somatorio = ",sum(re.rfts_qtcontas) as somatorio1, sum(re.rfts_qteconomia) as somatorio2, "
								+ "sum(re.rfts_nnconsumoagua) as somatorio3, sum(re.rfts_vlagua) as somatorio4, "
								+ "sum(re.rfts_nnconsumoesgoto) as somatorio5, sum(re.rfts_vlesgoto) as somatorio6, "
								+ "sum(re.rfts_vlcreditos) as somatorio7, sum(re.rfts_vldebitos) as somatorio8, 0 ";
				this.nomeCampoFixo = " ,eloPolo.loca_nmlocalidade ";
				this.nomeTabelaFixo = "resumo_faturamento_simulacao re, localidade eloPolo ";
				this.nomeTabelaFixoTotal = "resumo_faturamento_simulacao re, localidade eloPolo ";
				break;
			case GeradorSqlRelatorio.PENDENCIA:
				this.condicionalFixo = " re.catg_id = c.catg_id AND b.cgtp_id = c.cgtp_id ";
				this.somatorio = ",sum(re.rpen_qtligacoes) as somatorioLigacoes, sum(re.rpen_qtdocumentos) as somatorioDocumentos, sum(re.RPEN_VLPENDENTE_DEBITOS) as somatorioDebitos ";
				this.nomeCampoFixo = "";
				this.nomeTabelaFixo = " resumo_pendencia re, categoria_tipo b, categoria c ";
				this.nomeTabelaFixoTotal = "";
				this.condicionalResumo = this.criarCondicionaisResumos(dadosFiltroHelper, "rpen");

				configuracoes.add(this.new ConfiguracaoGeradorSqlRelatorio("\'POTENCIAL\' as tipoSituacaoAguaEsgoto, ", "",
								"AND re.last_id = 1 ", "AND re.lest_id not in (3,5,6) "));

				configuracoes.add(this.new ConfiguracaoGeradorSqlRelatorio("\'FACTÍVEL\' as tipoSituacaoAguaEsgoto, ", "",
								"AND re.last_id in (2,4) ", "AND re.lest_id not in (3,5,6) "));

				configuracoes.add(this.new ConfiguracaoGeradorSqlRelatorio("\'LIGADO DE ÁGUA\' as tipoSituacaoAguaEsgoto, ", "",
								"AND re.last_id = 3 ", ""));

				configuracoes.add(this.new ConfiguracaoGeradorSqlRelatorio("\'CORTADO\' as tipoSituacaoAguaEsgoto, ", "",
								"AND re.last_id = 5 ", ""));

				configuracoes.add(this.new ConfiguracaoGeradorSqlRelatorio("\'LIGADO SÓ DE ESGOTO\' as tipoSituacaoAguaEsgoto, ", "",
								"AND re.last_id not in (3,5) ", "AND re.lest_id = 3 "));

				configuracoes.add(this.new ConfiguracaoGeradorSqlRelatorio("\'ESGOTO FORA DE USO\' as tipoSituacaoAguaEsgoto, ", "",
								"AND re.last_id not in (3,5) ", "AND re.lest_id = 5 "));

				configuracoes.add(this.new ConfiguracaoGeradorSqlRelatorio("\'ESGOTO TAMPONADO\' as tipoSituacaoAguaEsgoto, ", "",
								"AND re.last_id not in (3,5) ", "AND re.lest_id = 6 "));

				configuracoes.add(this.new ConfiguracaoGeradorSqlRelatorio("\'SUPRIMIDO TOTAL\' as tipoSituacaoAguaEsgoto, ", "",
								"AND re.last_id = 6 ", "AND re.lest_id not in (3,5,6) "));

				configuracoes.add(this.new ConfiguracaoGeradorSqlRelatorio("\'SUPRIMIDO PARCIAL\' as tipoSituacaoAguaEsgoto, ", "",
								"AND re.last_id = 7 ", "AND re.lest_id not in (3,5,6) "));

				configuracoes.add(this.new ConfiguracaoGeradorSqlRelatorio("\'SUPRIMIDO A PEDIDO\' as tipoSituacaoAguaEsgoto, ", "",
								"AND re.last_id = 8 ", "AND re.lest_id not in (3,5,6) "));
				break;

			case GeradorSqlRelatorio.RESUMO_FATURAMENTO:
				this.condicionalFixo = "re.loca_id = eloPolo.loca_id ";
				this.somatorio = " sum(re.rfts_qtcontas) as fatcontas, sum(re.rfts_vlagua + re.rfts_vlesgoto + re.rfts_vldebitos - re.rfts_vlcreditos) as fatvalor";
				this.nomeCampoFixo = " as estado,";
				this.nomeTabelaFixo = "resumo_faturamento_simulacao re, localidade eloPolo ";
				this.nomeTabelaFixoTotal = "";
				this.condicionalResumo = this.criarCondicionaisResumos(dadosFiltroHelper, "rfts");
				break;

			case GeradorSqlRelatorio.RESUMO_ARRECADACAO:
				this.condicionalFixo = "re.loca_id = eloPolo.loca_id and re.dotp_id=" + DocumentoTipo.CONTA + " ";
				this.somatorio = " sum(re.ardd_qtpagamentos) as arrcontas, sum(re.ardd_vlpagamentos) as arrvalor";
				this.nomeCampoFixo = " as estado,";
				this.nomeTabelaFixo = "arrecadacao_dados_diarios re, localidade eloPolo ";
				this.nomeTabelaFixoTotal = "";
				this.condicionalResumo = this.criarCondicionaisResumos(dadosFiltroHelper, "ardd");
				break;

			case GeradorSqlRelatorio.RESUMO_PENDENCIA:
				this.condicionalFixo = "re.loca_id = eloPolo.loca_id and re.dotp_id=" + DocumentoTipo.CONTA + " ";
				this.somatorio = " sum(re.rpen_qtdocumentos) as pencontas, sum(re.RPEN_VLPENDENTE_DEBITOS) as penvalor";
				this.nomeCampoFixo = " as estado,";
				this.nomeTabelaFixo = "resumo_pendencia re, localidade eloPolo ";
				this.nomeTabelaFixoTotal = "";
				this.condicionalResumo = this.criarCondicionaisResumos(dadosFiltroHelper, "rpen");
				break;
		}

	}

	private void configurarQueryLigacoesEconomia(InformarDadosGeracaoRelatorioConsultaHelper dadosFiltroHelper){

		/*
		 * Definindo as flags para configuracao dos campos retornados e metodo de agregação da SQL
		 */
		int opcaoTotalizacao = dadosFiltroHelper.getOpcaoTotalizacao();
		Boolean classificarGrupoFaturamento = definirAgrupamento("classificarGrupoFaturamento", opcaoTotalizacao);
		Boolean classificarGerencia = definirAgrupamento("classificarGerencia", opcaoTotalizacao);
		Boolean classificarElo = definirAgrupamento("classificarElo", opcaoTotalizacao);
		Boolean classificarLocalidade = definirAgrupamento("classificarLocalidade", opcaoTotalizacao);
		Boolean classificarSetor = definirAgrupamento("classificarSetor", opcaoTotalizacao);
		Boolean classificarQuadra = definirAgrupamento("classificarQuadra", opcaoTotalizacao);
		Boolean classificarUnidadeNegocio = definirAgrupamento("classificarUnidadeNegocio", opcaoTotalizacao);

		String camposGroupBy = montarCamposGroupBy(classificarGrupoFaturamento, classificarGerencia, classificarElo, classificarLocalidade,
						classificarSetor, classificarQuadra, classificarUnidadeNegocio);
		sqlTemplate = "select  #CAMPO# as campo, 'ESTADO' as estado, #NOME_CAMPO_FIXO# from #NOME_TABELA_FIXO# #TABELA# where #CONDICIONAL_FIXO# #CONDICIONAL_RESUMO# #CONDICIONAL_LIGACAO# #CONDICIONAL_RESTRICAO# group by #CAMPO#, 'ESTADO', #GROUP_BY# #ORDER_BY#";
		sqlTemplate = sqlTemplate.replaceAll("#GROUP_BY#", camposGroupBy);
		orderBy = montarCamposOrdenacao(opcaoTotalizacao);
		this.nomeCampoFixo = montarCamposFixos(classificarGrupoFaturamento, classificarGerencia, classificarElo, classificarLocalidade,
						classificarSetor, classificarQuadra, classificarUnidadeNegocio);
		this.nomeTabelaFixo = montarTabelaFixo(opcaoTotalizacao);
		String condicionaisadcionais = condicionaisListas(dadosFiltroHelper);
		if(!Util.isVazioOuBranco(condicionaisadcionais) && !condicionaisadcionais.trim().toUpperCase().startsWith("AND")){
			condicionaisadcionais = " AND " + condicionaisadcionais;
		}
		this.condicionalFixo = montarLigacoesFixas(opcaoTotalizacao) + condicionaisadcionais;
		this.condicionalResumo = " AND " + montarCondicionais(dadosFiltroHelper);
		this.somatorio = "";
	}

	private boolean definirAgrupamento(String tipoClassificacao, int opcaoTotalizacao){

		boolean retorno = false;
		switch(opcaoTotalizacao){
			case ConstantesSistema.CODIGO_ELO_POLO:
				retorno = "classificarElo".equals(tipoClassificacao);
				break;
			case ConstantesSistema.CODIGO_ELO_POLO_LOCALIDADE:
				retorno = "classificarLocalidade".equals(tipoClassificacao) || "classificarElo".equals(tipoClassificacao);
				break;
			case ConstantesSistema.CODIGO_ELO_POLO_SETOR_COMERCIAL:
				retorno = "classificarElo".equals(tipoClassificacao) || "classificarSetor".equals(tipoClassificacao);
				break;
			case ConstantesSistema.CODIGO_ESTADO_ELO_POLO:
			case ConstantesSistema.CODIGO_UNIDADE_NEGOCIO_ELO_POLO:
				retorno = "classificarElo".equals(tipoClassificacao);
				break;
			case ConstantesSistema.CODIGO_ESTADO_GERENCIA_REGIONAL:
				retorno = "classificarGerencia".equals(tipoClassificacao);
				break;
			case ConstantesSistema.CODIGO_UNIDADE_NEGOCIO:
			case ConstantesSistema.CODIGO_ESTADO_UNIDADE_NEGOCIO:
				retorno = "classificarUnidadeNegocio".equals(tipoClassificacao);
				break;
			case ConstantesSistema.CODIGO_GRUPO_FATURAMENTO:
			case ConstantesSistema.CODIGO_ESTADO_GRUPO_FATURAMENTO:
				retorno = "classificarGrupoFaturamento".equals(tipoClassificacao);
				break;
			case ConstantesSistema.CODIGO_LOCALIDADE:
			case ConstantesSistema.CODIGO_ESTADO_LOCALIDADE:
			case ConstantesSistema.CODIGO_UNIDADE_NEGOCIO_LOCALIDADE:
				retorno = "classificarLocalidade".equals(tipoClassificacao);
				break;
			case ConstantesSistema.CODIGO_GERENCIA_REGIONAL:
				retorno = "classificarGerencia".equals(tipoClassificacao);
				break;
			case ConstantesSistema.CODIGO_GERENCIA_REGIONAL_ELO_POLO:
				retorno = "classificarGerencia".equals(tipoClassificacao) || "classificarElo".equals(tipoClassificacao);
				break;
			case ConstantesSistema.CODIGO_GERENCIA_REGIONAL_LOCALIDADE:
				retorno = "classificarGerencia".equals(tipoClassificacao) || "classificarLocalidade".equals(tipoClassificacao);
				break;
			case ConstantesSistema.CODIGO_LOCALIDADE_QUADRA:
				retorno = "classificarLocalidade".equals(tipoClassificacao) || "classificarQuadra".equals(tipoClassificacao);
				break;
			case ConstantesSistema.CODIGO_LOCALIDADE_SETOR_COMERCIAL:
				retorno = "classificarLocalidade".equals(tipoClassificacao) || "classificarSetor".equals(tipoClassificacao);
				break;
			case ConstantesSistema.CODIGO_QUADRA:
				retorno = "classificarQuadra".equals(tipoClassificacao);
				break;
			case ConstantesSistema.CODIGO_SETOR_COMERCIAL:
				retorno = "classificarSetor".equals(tipoClassificacao);
				break;
			case ConstantesSistema.CODIGO_SETOR_COMERCIAL_QUADRA:
				retorno = "classificarSetor".equals(tipoClassificacao) || "classificarQuadra".equals(tipoClassificacao);
				break;
		}
		return retorno;
	}

	private String montarCondicionais(InformarDadosGeracaoRelatorioConsultaHelper dadosFiltroHelper){

		StringBuffer buffer = new StringBuffer();
		if(!Util.isVazioOuBrancoOuZero(dadosFiltroHelper.getAnoMesReferencia())){
			buffer.append("re.RELE_AMREFERENCIA = ");
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
		if(dadosFiltroHelper.getEloPolo() != null){
			if(buffer.length() > 0){
				buffer.append(" AND ");
			}
			buffer.append("re.LOCA_CDELO = ");
			buffer.append(dadosFiltroHelper.getEloPolo().getId());
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
		if(dadosFiltroHelper.getUnidadeNegocio() != null){
			if(buffer.length() > 0){
				buffer.append(" AND ");
			}
			buffer.append("re.UNEG_ID = ");
			buffer.append(dadosFiltroHelper.getUnidadeNegocio().getId());
		}

		buffer.append(" AND L.LOCA_ICUSO = 1 ");

		return buffer.toString();
	}

	private String montarCamposGroupBy(boolean classificarGrupoFaturamento, boolean classificarGerencia, boolean classificarElo,
					boolean classificarLocalidade, boolean classificarSetor, boolean classificarQuadra, boolean classificarUnidadeNegocio){

		StringBuffer buffer = new StringBuffer();

		if(classificarGrupoFaturamento){
			buffer.append("rt.FTGR_ID, ");
		}

		if(classificarGerencia){
			buffer.append("re.greg_id, ");
			buffer.append("gr.greg_nmregional, ");
		}else{
			buffer.append("'0', '0', ");
		}

		if(classificarUnidadeNegocio){
			buffer.append(" re.UNEG_ID, ");
			buffer.append(" un.UNEG_NMUNIDADENEGOCIO, ");
		}else{
			buffer.append(" '0', '0', ");
		}

		if(classificarElo){
			buffer.append("re.LOCA_CDELO, ");
			buffer.append("elo.loca_nmlocalidade, ");
		}else{
			buffer.append("'0', '0', ");
		}

		if(classificarLocalidade){
			buffer.append("re.loca_id , ");
			buffer.append("l.loca_nmlocalidade, ");
		}else{
			buffer.append("'0', '0', ");
		}

		if(classificarSetor){
			buffer.append("re.stcm_id, ");
			buffer.append("sc.stcm_nmsetorcomercial, ");
		}else{
			buffer.append("'0', '0', ");
		}

		if(classificarQuadra){
			buffer.append("re.qdra_id, ");
			buffer.append("q.qdra_nnquadra, ");
		}else{
			buffer.append("'0', '0', ");
		}

		buffer
						.append("re.last_id, las.last_dsligacaoaguasituacao, re.lest_id, les.lest_dsligacaoesgotosituacao, re.catg_id, cat.catg_dscategoria");

		return buffer.toString();
	}

	private String montarCamposOrdenacao(Integer opcaoTotalizacao){

		StringBuffer buffer = new StringBuffer();
		buffer.append("campo, ");
		buffer.append("estado, ");
		if(ConstantesSistema.CODIGO_ESTADO_GRUPO_FATURAMENTO == opcaoTotalizacao.intValue()
						|| ConstantesSistema.CODIGO_GRUPO_FATURAMENTO == opcaoTotalizacao.intValue()){
			buffer.append("idGrupoFaturamento, ");
		}

		buffer.append("idGerencia , ");
		buffer.append("descricaoGerencia , ");
		buffer.append("idUnidadeNegocio , ");
		buffer.append("descricaoUnidadeNegocio , ");
		buffer.append("idElo , ");
		buffer.append("descricaoElo , ");
		buffer.append("idLocalidade , ");
		buffer.append("descricaoLocalidade , ");
		buffer.append("idSetorComercial , ");
		buffer.append("descricaoSetorComercial ,");
		buffer.append("idQuadra ,");
		buffer.append("descricaoQuadra ,");
		buffer.append("idLigacaoAguaSituacao ,");
		buffer.append("descricaoLigacaoAguaSituacao ,");
		buffer.append("idLigacaoEsgotoSituacao ,");
		buffer.append("descricaoLigacaoEsgotoSituacao ,");
		buffer.append("idCategoria ,");
		buffer.append("descricaoCategoria");
		return buffer.toString();
	}

	private String montarLigacoesFixas(Integer opcaoTotalizacao){

		StringBuffer buffer = new StringBuffer();
		buffer.append("re.catg_id = cat.catg_id and ");
		buffer.append("re.greg_id = gr.greg_id and ");
		buffer.append("re.last_id = las.last_id and ");
		buffer.append("re.lest_id = les.lest_id and ");
		buffer.append("re.loca_id = l.loca_id and ");
		buffer.append("re.qdra_id = q.qdra_id and ");
		buffer.append("re.stcm_id = sc.stcm_id  and ");
		buffer.append("re.loca_cdelo = elo.loca_id and ");
		buffer.append("re.UNEG_ID = un.UNEG_ID ");
		if(ConstantesSistema.CODIGO_ESTADO_GRUPO_FATURAMENTO == opcaoTotalizacao.intValue()
						|| ConstantesSistema.CODIGO_GRUPO_FATURAMENTO == opcaoTotalizacao.intValue()){
			buffer.append(" and re.rota_id = rt.rota_id ");
		}
		return buffer.toString();
	}

	private String montarTabelaFixo(Integer opcaoTotalizacao){

		StringBuffer buffer = new StringBuffer();
		buffer.append("resumo_ligacoes_economia re, ");
		buffer.append("gerencia_regional gr, ");
		buffer.append("localidade l , ");
		buffer.append("localidade elo, ");
		buffer.append("setor_comercial sc, ");
		buffer.append("quadra q, ");
		buffer.append("ligacao_agua_situacao las, ");
		buffer.append("ligacao_esgoto_situacao les, ");
		buffer.append("categoria cat, ");
		buffer.append("UNIDADE_NEGOCIO un ");

		if(ConstantesSistema.CODIGO_ESTADO_GRUPO_FATURAMENTO == opcaoTotalizacao.intValue()
						|| ConstantesSistema.CODIGO_GRUPO_FATURAMENTO == opcaoTotalizacao.intValue()){
			buffer.append(", rota rt ");
		}
		return buffer.toString();
	}

	private String montarCamposFixos(boolean classificarGrupoFaturamento, boolean classificarGerencia, boolean classificarElo,
					boolean classificarLocalidade, boolean classificarSetor, boolean classificarQuadra, boolean classificarUnidadeNegocio){

		StringBuffer buffer = new StringBuffer();

		String campoAgrupamento = "'ESTADO'";
		if(classificarGrupoFaturamento){
			buffer.append("rt.FTGR_ID as idGrupoFaturamento, ");
			campoAgrupamento = "rt.FTGR_ID";
		}

		if(classificarGerencia){
			buffer.append("re.greg_id as idGerencia, ");
			buffer.append("gr.greg_nmregional as descricaoGerencia, ");
			campoAgrupamento = "gr.greg_id";
		}else{
			buffer.append("'0' as idGerencia, ");
			buffer.append("'0' as descricaoGerencia, ");
		}

		if(classificarUnidadeNegocio){
			buffer.append("re.UNEG_ID as idUnidadeNegocio, ");
			buffer.append("un.UNEG_NMUNIDADENEGOCIO as descricaoUnidadeNegocio, ");
			campoAgrupamento = "un.UNEG_NMUNIDADENEGOCIO";
		}else{
			buffer.append("'0' as idUnidadeNegocio, ");
			buffer.append("'0' as descricaoUnidadeNegocio, ");
		}

		if(classificarElo){
			buffer.append("re.LOCA_CDELO as idElo, ");
			buffer.append("elo.loca_nmlocalidade as descricaoElo, ");
			campoAgrupamento = "elo.LOCA_CDELO";
		}else{
			buffer.append("'0' as idElo, ");
			buffer.append("'0' as descricaoElo, ");
		}

		if(classificarLocalidade){
			buffer.append("re.loca_id as idLocalidade, ");
			buffer.append("l.loca_nmlocalidade as descricaoLocalidade, ");
			campoAgrupamento = "l.loca_id";
		}else{
			buffer.append("'0' as idLocalidade, ");
			buffer.append("'0' as descricaoLocalidade, ");
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

		buffer.append("re.last_id as idLigacaoAguaSituacao, ");
		buffer.append("las.last_dsligacaoaguasituacao as descricaoLigacaoAguaSituacao, ");
		buffer.append("re.lest_id as idLigacaoEsgotoSituacao, ");
		buffer.append("les.lest_dsligacaoesgotosituacao as descricaoLigacaoEsgotoSituacao, ");
		buffer.append("re.catg_id as idCategoria, ");
		buffer.append("cat.catg_dscategoria as descricaoCategoria, ");
		buffer.append("sum(CASE WHEN re.rele_ichidrometro = 1  THEN re.rele_qtligacoes ELSE 0 END) as qtdLigacoesComHidrometro, ");
		buffer.append("sum(CASE WHEN re.rele_ichidrometro = 1  THEN 0 ELSE re.rele_qtligacoes END) as qtdLigacoesSemHidrometro, ");
		buffer.append("sum(re.rele_qtligacoes) as qtdLigacoesTotal, ");
		buffer.append("sum(CASE WHEN re.rele_ichidrometro = 1  THEN re.rele_qteconomias ELSE 0 END) as qtdEconomiasComHidrometro, ");
		buffer.append("sum(CASE WHEN re.rele_ichidrometro = 1  THEN 0 ELSE re.rele_qteconomias END) as qtdEconomiasSemHidrometro, ");
		buffer.append("sum(re.rele_qteconomias) as qtdEconomiasTotal ");

		sqlTemplate = sqlTemplate.replaceAll("#CAMPO#", campoAgrupamento);

		return buffer.toString();
	}

	public void setCondicionalFixo(String condicionalFixo){

		this.condicionalFixo = condicionalFixo;
	}

	public String getNomeCampoFixo(){

		return nomeCampoFixo;
	}

	public void setNomeCampoFixo(String nomeCampoFixo){

		this.nomeCampoFixo = nomeCampoFixo;
	}

	public String getNomeTabelaFixo(){

		return nomeTabelaFixo;
	}

	public void setNomeTabelaFixo(String nomeTabelaFixo){

		this.nomeTabelaFixo = nomeTabelaFixo;
	}

	public String getNomeTabelaFixoTotal(){

		return nomeTabelaFixoTotal;
	}

	public void setNomeTabelaFixoTotal(String nomeTabelaFixoTotal){

		this.nomeTabelaFixoTotal = nomeTabelaFixoTotal;
	}

	public String getSomatorio(){

		return somatorio;
	}

	public void setSomatorio(String somatorio){

		this.somatorio = somatorio;
	}

	public String getCondicionalResumo(){

		return condicionalResumo;
	}

	public void setCondicionalResumo(String condicionalResumo){

		this.condicionalResumo = condicionalResumo;
	}

	public static void main(String[] args){

		InformarDadosGeracaoRelatorioConsultaHelper informarDadosGeracaoRelatorioConsultaHelper = new InformarDadosGeracaoRelatorioConsultaHelper();
		GeradorSqlRelatorio geradorSqlRelatorio = new GeradorSqlRelatorio(GeradorSqlRelatorio.PENDENCIA,
						informarDadosGeracaoRelatorioConsultaHelper);

		System.out.println(geradorSqlRelatorio.sqlNivelUmPendencia());

	}

	public class ConfiguracaoGeradorSqlRelatorio {

		private String campo, tabela, condicionalLigacao, condicionalRestricao;

		public ConfiguracaoGeradorSqlRelatorio(String campo, String tabela, String condicionalLigacao, String condicionalRestricao) {

			super();
			this.campo = campo;
			this.tabela = tabela;
			this.condicionalLigacao = condicionalLigacao;
			this.condicionalRestricao = condicionalRestricao;
		}

		public String getCampo(){

			return campo;
		}

		public void setCampo(String campo){

			this.campo = campo;
		}

		public String getCondicionalLigacao(){

			return condicionalLigacao;
		}

		public void setCondicionalLigacao(String condicionalLigacao){

			this.condicionalLigacao = condicionalLigacao;
		}

		public String getCondicionalRestricao(){

			return condicionalRestricao;
		}

		public void setCondicionalRestricao(String condicionalRestricao){

			this.condicionalRestricao = condicionalRestricao;
		}

		public String getTabela(){

			return tabela;
		}

		public void setTabela(String tabela){

			this.tabela = tabela;
		}

	}

	private String criarCondicionaisResumos(InformarDadosGeracaoRelatorioConsultaHelper informarDadosGeracaoRelatorioConsultaHelper,
					String nomeColunaTabela){

		String sql = " ";

		// A partir daqui sera montanda a parte dos condicionais da query
		// estas condicionais serão usadas se necessarias, o q determina seus
		// usos
		// são os parametros que veem carregados no objeto
		// InformarDadosGeracaoRelatorioConsultaHelper
		// que é recebido do caso de uso [UC0304] Informar Dados para Geração de
		// Relatorio ou COnsulta
		if(informarDadosGeracaoRelatorioConsultaHelper != null){

			// Inicio Parametros simples
			if(informarDadosGeracaoRelatorioConsultaHelper.getAnoMesReferencia() != null
							&& !informarDadosGeracaoRelatorioConsultaHelper.getAnoMesReferencia().toString().equals("")){
				if(nomeColunaTabela.equals("ardd")){
					sql = sql + " and re." + nomeColunaTabela + "_amreferenciaarrecadacao = "
									+ informarDadosGeracaoRelatorioConsultaHelper.getAnoMesReferencia() + " ";

				}else{
					sql = sql + " and re." + nomeColunaTabela + "_amreferencia = "
									+ informarDadosGeracaoRelatorioConsultaHelper.getAnoMesReferencia() + " ";

				}
			}

			if(informarDadosGeracaoRelatorioConsultaHelper.getFaturamentoGrupo() != null
							&& informarDadosGeracaoRelatorioConsultaHelper.getFaturamentoGrupo().getId() != null){
				sql = sql + " and re." + nomeColunaTabela + "_id = "
								+ informarDadosGeracaoRelatorioConsultaHelper.getFaturamentoGrupo().getId() + " ";
			}

			if(informarDadosGeracaoRelatorioConsultaHelper.getGerenciaRegional() != null
							&& informarDadosGeracaoRelatorioConsultaHelper.getGerenciaRegional().getId() != null){
				sql = sql + " and re.greg_id = " + informarDadosGeracaoRelatorioConsultaHelper.getGerenciaRegional().getId() + " ";
			}

			if(informarDadosGeracaoRelatorioConsultaHelper.getEloPolo() != null
							&& informarDadosGeracaoRelatorioConsultaHelper.getEloPolo().getId() != null){
				sql = sql + " and eloPolo.loca_id = " + informarDadosGeracaoRelatorioConsultaHelper.getEloPolo().getId() + " ";
			}

			if(informarDadosGeracaoRelatorioConsultaHelper.getLocalidade() != null
							&& informarDadosGeracaoRelatorioConsultaHelper.getLocalidade().getId() != null){
				sql = sql + " and re.loca_id = " + informarDadosGeracaoRelatorioConsultaHelper.getLocalidade().getId() + " ";
			}

			if(informarDadosGeracaoRelatorioConsultaHelper.getSetorComercial() != null
							&& informarDadosGeracaoRelatorioConsultaHelper.getSetorComercial().getId() != null){
				sql = sql + " and re.stcm_id = " + informarDadosGeracaoRelatorioConsultaHelper.getSetorComercial().getId() + " ";
			}

			if(informarDadosGeracaoRelatorioConsultaHelper.getQuadra() != null
							&& informarDadosGeracaoRelatorioConsultaHelper.getQuadra().getId() != null){
				sql = sql + " and re.qdra_id = " + informarDadosGeracaoRelatorioConsultaHelper.getQuadra().getId() + " ";
			}

			// Inicio de parametros por colecão
			// sera lida a colecao e montado um IN() a partis dos id extraidos
			// de cada objeto da colecao.
			if(informarDadosGeracaoRelatorioConsultaHelper.getColecaoImovelPerfil() != null
							&& !informarDadosGeracaoRelatorioConsultaHelper.getColecaoImovelPerfil().isEmpty()){

				Iterator iterator = informarDadosGeracaoRelatorioConsultaHelper.getColecaoImovelPerfil().iterator();
				ImovelPerfil imovelPerfil = null;

				sql = sql + " and re.iper_id in (";
				while(iterator.hasNext()){
					imovelPerfil = (ImovelPerfil) iterator.next();
					sql = sql + imovelPerfil.getId() + ",";
				}
				sql = Util.formatarHQL(sql, 1);
				sql = sql + ") ";
			}

			if(informarDadosGeracaoRelatorioConsultaHelper.getColecaoLigacaoAguaSituacao() != null
							&& !informarDadosGeracaoRelatorioConsultaHelper.getColecaoLigacaoAguaSituacao().isEmpty()){

				Iterator iterator = informarDadosGeracaoRelatorioConsultaHelper.getColecaoLigacaoAguaSituacao().iterator();
				LigacaoAguaSituacao ligacaoAguaSituacao = null;

				sql = sql + " and re.last_id in (";
				while(iterator.hasNext()){
					ligacaoAguaSituacao = (LigacaoAguaSituacao) iterator.next();
					sql = sql + ligacaoAguaSituacao.getId() + ",";
				}
				sql = Util.formatarHQL(sql, 1);
				sql = sql + ") ";
			}

			if(informarDadosGeracaoRelatorioConsultaHelper.getColecaoLigacaoEsgotoSituacao() != null
							&& !informarDadosGeracaoRelatorioConsultaHelper.getColecaoLigacaoEsgotoSituacao().isEmpty()){

				Iterator iterator = informarDadosGeracaoRelatorioConsultaHelper.getColecaoLigacaoEsgotoSituacao().iterator();
				LigacaoEsgotoSituacao ligacaoEsgotoSituacao = null;

				sql = sql + " and re.lest_id in (";
				while(iterator.hasNext()){
					ligacaoEsgotoSituacao = (LigacaoEsgotoSituacao) iterator.next();
					sql = sql + ligacaoEsgotoSituacao.getId() + ",";
				}
				sql = Util.formatarHQL(sql, 1);
				sql = sql + ") ";
			}

			if(informarDadosGeracaoRelatorioConsultaHelper.getColecaoCategoria() != null
							&& !informarDadosGeracaoRelatorioConsultaHelper.getColecaoCategoria().isEmpty()){

				Iterator iterator = informarDadosGeracaoRelatorioConsultaHelper.getColecaoCategoria().iterator();
				Categoria categoria = null;

				sql = sql + " and re.catg_id in (";
				while(iterator.hasNext()){
					categoria = (Categoria) iterator.next();
					sql = sql + categoria.getId() + ",";
				}
				sql = Util.formatarHQL(sql, 1);
				sql = sql + ") ";
			}

			if(informarDadosGeracaoRelatorioConsultaHelper.getColecaoEsferaPoder() != null
							&& !informarDadosGeracaoRelatorioConsultaHelper.getColecaoEsferaPoder().isEmpty()){

				Iterator iterator = informarDadosGeracaoRelatorioConsultaHelper.getColecaoEsferaPoder().iterator();
				EsferaPoder esferaPoder = null;

				sql = sql + " and re.epod_id in (";
				while(iterator.hasNext()){
					esferaPoder = (EsferaPoder) iterator.next();
					sql = sql + esferaPoder.getId() + ",";
				}
				sql = Util.formatarHQL(sql, 1);
				sql = sql + ") ";
			}

		}

		// sql = Util.formatarHQL(sql, 4);

		return sql;
	}

	public static String condicionaisListas(InformarDadosGeracaoRelatorioConsultaHelper informarDadosGeracaoRelatorioConsultaHelper){

		String condicionaisAdicionais = "";

		// Inicio de parametros por colecão
		// sera lida a colecao e montado um IN() a partis dos id extraidos de
		// cada objeto da colecao.
		if(informarDadosGeracaoRelatorioConsultaHelper.getColecaoImovelPerfil() != null
						&& !informarDadosGeracaoRelatorioConsultaHelper.getColecaoImovelPerfil().isEmpty()){

			Iterator iterator = informarDadosGeracaoRelatorioConsultaHelper.getColecaoImovelPerfil().iterator();
			ImovelPerfil imovelPerfil = null;

			condicionaisAdicionais = condicionaisAdicionais + " and re.iper_id in (";
			while(iterator.hasNext()){
				imovelPerfil = (ImovelPerfil) iterator.next();
				condicionaisAdicionais = condicionaisAdicionais + imovelPerfil.getId() + ",";
			}
			condicionaisAdicionais = Util.formatarHQL(condicionaisAdicionais, 1);
			condicionaisAdicionais = condicionaisAdicionais + ") ";
		}

		if(informarDadosGeracaoRelatorioConsultaHelper.getColecaoLigacaoAguaSituacao() != null
						&& !informarDadosGeracaoRelatorioConsultaHelper.getColecaoLigacaoAguaSituacao().isEmpty()){

			Iterator iterator = informarDadosGeracaoRelatorioConsultaHelper.getColecaoLigacaoAguaSituacao().iterator();
			LigacaoAguaSituacao ligacaoAguaSituacao = null;

			condicionaisAdicionais = condicionaisAdicionais + " and re.last_id in (";
			while(iterator.hasNext()){
				ligacaoAguaSituacao = (LigacaoAguaSituacao) iterator.next();
				condicionaisAdicionais = condicionaisAdicionais + ligacaoAguaSituacao.getId() + ",";
			}
			condicionaisAdicionais = Util.formatarHQL(condicionaisAdicionais, 1);
			condicionaisAdicionais = condicionaisAdicionais + ") ";
		}

		if(informarDadosGeracaoRelatorioConsultaHelper.getColecaoLigacaoEsgotoSituacao() != null
						&& !informarDadosGeracaoRelatorioConsultaHelper.getColecaoLigacaoEsgotoSituacao().isEmpty()){

			Iterator iterator = informarDadosGeracaoRelatorioConsultaHelper.getColecaoLigacaoEsgotoSituacao().iterator();
			LigacaoEsgotoSituacao ligacaoEsgotoSituacao = null;

			condicionaisAdicionais = condicionaisAdicionais + " and re.lest_id in (";
			while(iterator.hasNext()){
				ligacaoEsgotoSituacao = (LigacaoEsgotoSituacao) iterator.next();
				condicionaisAdicionais = condicionaisAdicionais + ligacaoEsgotoSituacao.getId() + ",";
			}
			condicionaisAdicionais = Util.formatarHQL(condicionaisAdicionais, 1);
			condicionaisAdicionais = condicionaisAdicionais + ") ";
		}

		if(informarDadosGeracaoRelatorioConsultaHelper.getColecaoCategoria() != null
						&& !informarDadosGeracaoRelatorioConsultaHelper.getColecaoCategoria().isEmpty()){

			Iterator iterator = informarDadosGeracaoRelatorioConsultaHelper.getColecaoCategoria().iterator();
			Categoria categoria = null;

			condicionaisAdicionais = condicionaisAdicionais + " and re.catg_id in (";
			while(iterator.hasNext()){
				categoria = (Categoria) iterator.next();
				condicionaisAdicionais = condicionaisAdicionais + categoria.getId() + ",";
			}
			condicionaisAdicionais = Util.formatarHQL(condicionaisAdicionais, 1);
			condicionaisAdicionais = condicionaisAdicionais + ") ";
		}

		if(informarDadosGeracaoRelatorioConsultaHelper.getColecaoEsferaPoder() != null
						&& !informarDadosGeracaoRelatorioConsultaHelper.getColecaoEsferaPoder().isEmpty()){

			Iterator iterator = informarDadosGeracaoRelatorioConsultaHelper.getColecaoEsferaPoder().iterator();
			EsferaPoder esferaPoder = null;

			condicionaisAdicionais = condicionaisAdicionais + " and re.epod_id in (";
			while(iterator.hasNext()){
				esferaPoder = (EsferaPoder) iterator.next();
				condicionaisAdicionais = condicionaisAdicionais + esferaPoder.getId() + ",";
			}
			condicionaisAdicionais = Util.formatarHQL(condicionaisAdicionais, 1);
			condicionaisAdicionais = condicionaisAdicionais + ") ";
		}

		return condicionaisAdicionais;

	}
}
