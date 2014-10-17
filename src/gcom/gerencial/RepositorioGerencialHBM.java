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

package gcom.gerencial;

import gcom.gerencial.bean.FiltrarRelatorioOrcamentoSINPHelper;
import gcom.gerencial.bean.FiltrarRelatorioQuadroMetasAcumuladoHelper;
import gcom.gerencial.bean.InformarDadosGeracaoRelatorioConsultaHelper;
import gcom.util.ConstantesSistema;
import gcom.util.ErroRepositorioException;
import gcom.util.GeradorSqlRelatorio;
import gcom.util.HibernateUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 * @author Raphael Rossiter
 * @created 20/05/2006
 */
public class RepositorioGerencialHBM
				implements IRepositorioGerencial {

	/**
	 * Constantes do Relatorio de Quadro de Metas Aculumado
	 */
	// Ligacoes Cadastradas
	public final static Integer INDEX_QUANTIDADE_LIGACOES_CADASTRADAS_SUBCATEGORIA_101 = 0;

	public final static Integer INDEX_QUANTIDADE_LIGACOES_CADASTRADAS_SUBCATEGORIA_102 = 1;

	public final static Integer INDEX_QUANTIDADE_LIGACOES_CADASTRADAS_SUBCATEGORIA_103 = 2;

	public final static Integer INDEX_QUANTIDADE_LIGACOES_CADASTRADAS_SUBCATEGORIA_200 = 3;

	public final static Integer INDEX_QUANTIDADE_LIGACOES_CADASTRADAS_SUBCATEGORIA_300 = 4;

	public final static Integer INDEX_QUANTIDADE_LIGACOES_CADASTRADAS_SUBCATEGORIA_400 = 5;

	public final static Integer INDEX_QUANTIDADE_LIGACOES_CADASTRADAS_SUBCATEGORIA_116 = 6;

	public final static Integer INDEX_QUANTIDADE_LIGACOES_CADASTRADAS_SUBCATEGORIA_115 = 7;

	public final static Integer INDEX_QUANTIDADE_LIGACOES_CADASTRADAS = 8;

	// Ligacoes Cortadas
	public final static Integer INDEX_QUANTIDADE_LIGACOES_CORTADAS_SUBCATEGORIA_101 = 9;

	public final static Integer INDEX_QUANTIDADE_LIGACOES_CORTADAS_SUBCATEGORIA_102 = 10;

	public final static Integer INDEX_QUANTIDADE_LIGACOES_CORTADAS_SUBCATEGORIA_103 = 11;

	public final static Integer INDEX_QUANTIDADE_LIGACOES_CORTADAS_SUBCATEGORIA_200 = 12;

	public final static Integer INDEX_QUANTIDADE_LIGACOES_CORTADAS_SUBCATEGORIA_300 = 13;

	public final static Integer INDEX_QUANTIDADE_LIGACOES_CORTADAS_SUBCATEGORIA_400 = 14;

	public final static Integer INDEX_QUANTIDADE_LIGACOES_CORTADAS_SUBCATEGORIA_116 = 15;

	public final static Integer INDEX_QUANTIDADE_LIGACOES_CORTADAS_SUBCATEGORIA_115 = 16;

	public final static Integer INDEX_QUANTIDADE_LIGACOES_CORTADAS = 17;

	// Ligacoes Suprimidas
	public final static Integer INDEX_QUANTIDADE_LIGACOES_SUPRIMIDAS_SUBCATEGORIA_101 = 18;

	public final static Integer INDEX_QUANTIDADE_LIGACOES_SUPRIMIDAS_SUBCATEGORIA_102 = 19;

	public final static Integer INDEX_QUANTIDADE_LIGACOES_SUPRIMIDAS_SUBCATEGORIA_103 = 20;

	public final static Integer INDEX_QUANTIDADE_LIGACOES_SUPRIMIDAS_SUBCATEGORIA_200 = 21;

	public final static Integer INDEX_QUANTIDADE_LIGACOES_SUPRIMIDAS_SUBCATEGORIA_300 = 22;

	public final static Integer INDEX_QUANTIDADE_LIGACOES_SUPRIMIDAS_SUBCATEGORIA_400 = 23;

	public final static Integer INDEX_QUANTIDADE_LIGACOES_SUPRIMIDAS_SUBCATEGORIA_116 = 24;

	public final static Integer INDEX_QUANTIDADE_LIGACOES_SUPRIMIDAS_SUBCATEGORIA_115 = 25;

	public final static Integer INDEX_QUANTIDADE_LIGACOES_SUPRIMIDAS = 26;

	// Ligacoes Ativas
	public final static Integer INDEX_QUANTIDADE_LIGACOES_ATIVAS_SUBCATEGORIA_101 = 27;

	public final static Integer INDEX_QUANTIDADE_LIGACOES_ATIVAS_SUBCATEGORIA_102 = 28;

	public final static Integer INDEX_QUANTIDADE_LIGACOES_ATIVAS_SUBCATEGORIA_103 = 29;

	public final static Integer INDEX_QUANTIDADE_LIGACOES_ATIVAS_SUBCATEGORIA_200 = 30;

	public final static Integer INDEX_QUANTIDADE_LIGACOES_ATIVAS_SUBCATEGORIA_300 = 31;

	public final static Integer INDEX_QUANTIDADE_LIGACOES_ATIVAS_SUBCATEGORIA_400 = 32;

	public final static Integer INDEX_QUANTIDADE_LIGACOES_ATIVAS_SUBCATEGORIA_116 = 33;

	public final static Integer INDEX_QUANTIDADE_LIGACOES_ATIVAS_SUBCATEGORIA_115 = 34;

	public final static Integer INDEX_QUANTIDADE_LIGACOES_ATIVAS = 35;

	// Ligacoes Ativas com débitos a mais de 3 meses
	public final static Integer INDEX_QUANTIDADE_LIGACOES_ATIVAS_DEBITOS_3M_SUBCATEGORIA_101 = 36;

	public final static Integer INDEX_QUANTIDADE_LIGACOES_ATIVAS_DEBITOS_3M_SUBCATEGORIA_102 = 37;

	public final static Integer INDEX_QUANTIDADE_LIGACOES_ATIVAS_DEBITOS_3M_SUBCATEGORIA_103 = 38;

	public final static Integer INDEX_QUANTIDADE_LIGACOES_ATIVAS_DEBITOS_3M_SUBCATEGORIA_200 = 39;

	public final static Integer INDEX_QUANTIDADE_LIGACOES_ATIVAS_DEBITOS_3M_SUBCATEGORIA_300 = 40;

	public final static Integer INDEX_QUANTIDADE_LIGACOES_ATIVAS_DEBITOS_3M_SUBCATEGORIA_400 = 41;

	public final static Integer INDEX_QUANTIDADE_LIGACOES_ATIVAS_DEBITOS_3M_SUBCATEGORIA_116 = 42;

	public final static Integer INDEX_QUANTIDADE_LIGACOES_ATIVAS_DEBITOS_3M_SUBCATEGORIA_115 = 43;

	public final static Integer INDEX_QUANTIDADE_LIGACOES_ATIVAS_DEBITOS_3M = 44;

	// Ligacoes Consumo Medido
	public final static Integer INDEX_QUANTIDADE_LIGACOES_CONSUMO_MEDIDO_SUBCATEGORIA_101 = 45;

	public final static Integer INDEX_QUANTIDADE_LIGACOES_CONSUMO_MEDIDO_SUBCATEGORIA_102 = 46;

	public final static Integer INDEX_QUANTIDADE_LIGACOES_CONSUMO_MEDIDO_SUBCATEGORIA_103 = 47;

	public final static Integer INDEX_QUANTIDADE_LIGACOES_CONSUMO_MEDIDO_SUBCATEGORIA_200 = 48;

	public final static Integer INDEX_QUANTIDADE_LIGACOES_CONSUMO_MEDIDO_SUBCATEGORIA_300 = 49;

	public final static Integer INDEX_QUANTIDADE_LIGACOES_CONSUMO_MEDIDO_SUBCATEGORIA_400 = 50;

	public final static Integer INDEX_QUANTIDADE_LIGACOES_CONSUMO_MEDIDO_SUBCATEGORIA_116 = 51;

	public final static Integer INDEX_QUANTIDADE_LIGACOES_CONSUMO_MEDIDO_SUBCATEGORIA_115 = 52;

	public final static Integer INDEX_QUANTIDADE_LIGACOES_CONSUMO_MEDIDO = 53;

	// Ligacoes Consumo 5m3
	public final static Integer INDEX_QUANTIDADE_LIGACOES_CONSUMO_5M3_SUBCATEGORIA_101 = 54;

	public final static Integer INDEX_QUANTIDADE_LIGACOES_CONSUMO_5M3_SUBCATEGORIA_102 = 55;

	public final static Integer INDEX_QUANTIDADE_LIGACOES_CONSUMO_5M3_SUBCATEGORIA_103 = 56;

	public final static Integer INDEX_QUANTIDADE_LIGACOES_CONSUMO_5M3_SUBCATEGORIA_200 = 57;

	public final static Integer INDEX_QUANTIDADE_LIGACOES_CONSUMO_5M3_SUBCATEGORIA_300 = 58;

	public final static Integer INDEX_QUANTIDADE_LIGACOES_CONSUMO_5M3_SUBCATEGORIA_400 = 59;

	public final static Integer INDEX_QUANTIDADE_LIGACOES_CONSUMO_5M3_SUBCATEGORIA_116 = 60;

	public final static Integer INDEX_QUANTIDADE_LIGACOES_CONSUMO_5M3_SUBCATEGORIA_115 = 61;

	public final static Integer INDEX_QUANTIDADE_LIGACOES_CONSUMO_5M3 = 62;

	// Ligacoes Consumo Não Medido
	public final static Integer INDEX_QUANTIDADE_LIGACOES_CONSUMO_NAO_MEDIDO_SUBCATEGORIA_101 = 63;

	public final static Integer INDEX_QUANTIDADE_LIGACOES_CONSUMO_NAO_MEDIDO_SUBCATEGORIA_102 = 64;

	public final static Integer INDEX_QUANTIDADE_LIGACOES_CONSUMO_NAO_MEDIDO_SUBCATEGORIA_103 = 65;

	public final static Integer INDEX_QUANTIDADE_LIGACOES_CONSUMO_NAO_MEDIDO_SUBCATEGORIA_200 = 66;

	public final static Integer INDEX_QUANTIDADE_LIGACOES_CONSUMO_NAO_MEDIDO_SUBCATEGORIA_300 = 67;

	public final static Integer INDEX_QUANTIDADE_LIGACOES_CONSUMO_NAO_MEDIDO_SUBCATEGORIA_400 = 68;

	public final static Integer INDEX_QUANTIDADE_LIGACOES_CONSUMO_NAO_MEDIDO_SUBCATEGORIA_116 = 69;

	public final static Integer INDEX_QUANTIDADE_LIGACOES_CONSUMO_NAO_MEDIDO_SUBCATEGORIA_115 = 70;

	public final static Integer INDEX_QUANTIDADE_LIGACOES_CONSUMO_NAO_MEDIDO = 71;

	// Ligacoes Consumo Media
	public final static Integer INDEX_QUANTIDADE_LIGACOES_CONSUMO_MEDIA_SUBCATEGORIA_101 = 72;

	public final static Integer INDEX_QUANTIDADE_LIGACOES_CONSUMO_MEDIA_SUBCATEGORIA_102 = 73;

	public final static Integer INDEX_QUANTIDADE_LIGACOES_CONSUMO_MEDIA_SUBCATEGORIA_103 = 74;

	public final static Integer INDEX_QUANTIDADE_LIGACOES_CONSUMO_MEDIA_SUBCATEGORIA_200 = 75;

	public final static Integer INDEX_QUANTIDADE_LIGACOES_CONSUMO_MEDIA_SUBCATEGORIA_300 = 76;

	public final static Integer INDEX_QUANTIDADE_LIGACOES_CONSUMO_MEDIA_SUBCATEGORIA_400 = 77;

	public final static Integer INDEX_QUANTIDADE_LIGACOES_CONSUMO_MEDIA_SUBCATEGORIA_116 = 78;

	public final static Integer INDEX_QUANTIDADE_LIGACOES_CONSUMO_MEDIA_SUBCATEGORIA_115 = 79;

	public final static Integer INDEX_QUANTIDADE_LIGACOES_CONSUMO_MEDIA = 80;

	// Ligacoes Quantidade Economias
	public final static Integer INDEX_QUANTIDADE_ECONOMIAS_SUBCATEGORIA_101 = 81;

	public final static Integer INDEX_QUANTIDADE_ECONOMIAS_SUBCATEGORIA_102 = 82;

	public final static Integer INDEX_QUANTIDADE_ECONOMIAS_SUBCATEGORIA_103 = 83;

	public final static Integer INDEX_QUANTIDADE_ECONOMIAS_SUBCATEGORIA_200 = 84;

	public final static Integer INDEX_QUANTIDADE_ECONOMIAS_SUBCATEGORIA_300 = 85;

	public final static Integer INDEX_QUANTIDADE_ECONOMIAS_SUBCATEGORIA_400 = 86;

	public final static Integer INDEX_QUANTIDADE_ECONOMIAS_SUBCATEGORIA_116 = 87;

	public final static Integer INDEX_QUANTIDADE_ECONOMIAS_SUBCATEGORIA_115 = 88;

	public final static Integer INDEX_QUANTIDADE_ECONOMIAS = 89;

	public final static Integer INDEX_ID_GERENCIA = 90;

	public final static Integer INDEX_ID_UNIDADE_NEGOCIO = 91;

	public final static Integer INDEX_ID_LOCALIDADE = 92;

	public final static Integer INDEX_CODIGO_CENTRO_CUSTO = 93;

	public final static Integer TOTALIZACAO_ESTADO = 1;

	public final static Integer TOTALIZACAO_ESTADO_GERENCIA_REGIONAL = 2;

	public final static Integer TOTALIZACAO_ESTADO_UNIDADE_NEGOCIO = 3;

	public final static Integer TOTALIZACAO_ESTADO_LOCALIDADE = 5;

	public final static Integer TOTALIZACAO_GERENCIA_REGIONAL = 6;

	public final static Integer TOTALIZACAO_UNIDADE_NEGOCIO = 10;

	public final static Integer TOTALIZACAO_LOCALIDADE = 16;

	private static IRepositorioGerencial instancia;

	/**
	 * Construtor da classe RepositorioMicromedicaoHBM
	 */
	private RepositorioGerencialHBM() {

	}

	/**
	 * Retorna o valor de instancia
	 * 
	 * @return O valor de instancia
	 */
	public static IRepositorioGerencial getInstancia(){

		if(instancia == null){
			instancia = new RepositorioGerencialHBM();
		}

		return instancia;
	}

	/**
	 * <Breve descrição sobre o caso de uso>
	 * <Identificador e nome do caso de uso>
	 * 
	 * @author Pedro Alexandre
	 * @date 09/06/2006
	 * @param informarDadosGeracaoRelatorioConsultaHelper
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List consultarComparativoResumosFaturamentoArrecadacaoPendencia(
					InformarDadosGeracaoRelatorioConsultaHelper informarDadosGeracaoRelatorioConsultaHelper)
					throws ErroRepositorioException{

		// Integer anoMesReferencia =
		// informarDadosGeracaoRelatorioConsultaHelper.getAnoMesReferencia();

		List retorno = new ArrayList();
		Session session = HibernateUtil.getSession();

		try{/*
			 * String sqlTabelaEstrutura = "select "+
			 * "'ESTADO'as estado, "+
			 * "e.greg_id as greg_id, lpad(e.greg_id,3,0)||' - '||e.greg_nmabreviado||' - '||e.greg_nmregional as gerencia, "
			 * +
			 * "d.loca_id as elo_id, lpad(d.loca_id,3,0)||' - '||d.loca_nmlocalidade as elo, "+
			 * 
			 * "c.loca_id as loca_id, lpad(c.loca_id,3,0)||' - '||c.loca_nmlocalidade as localidade, "+
			 * "f.stcm_id as stcm_id, lpad(f.stcm_cdsetorcomercial,3,0)||' - '||stcm_nmsetorcomercial as setor, "
			 * +
			 * "g.qdra_id as qdra_id, lpad(g.qdra_nnquadra,3,0) as quadra "+
			 * "into TEMP table estrutura "+
			 * 
			 * "from  localidade c, localidade d, gerencia_regional e, setor_comercial f, quadra g "+
			 * "where c.loca_cdelo = d.loca_id "+
			 * "and   c.greg_id = e.greg_id "+
			 * "and   d.loca_id = f.loca_id "+
			 * "and   f.stcm_id = g.stcm_id "+
			 * "group by 1,2,3,4,5,6,7,8,9,10,11 "+
			 * "union "+
			 * "select "+
			 * "'ESTADO'as estado, "+
			 * "e.greg_id as greg_id, lpad(e.greg_id,3,0)||' - '||e.greg_nmabreviado||' - '||e.greg_nmregional as gerencia, "
			 * +
			 * "d.loca_id as elo_id, lpad(d.loca_id,3,0)||' - '||d.loca_nmlocalidade as elo, "+
			 * 
			 * "c.loca_id as loca_id, lpad(c.loca_id,3,0)||' - '||c.loca_nmlocalidade as localidade, "+
			 * "f.stcm_id as stcm_id, lpad(f.stcm_cdsetorcomercial,3,0)||' - '||stcm_nmsetorcomercial as setor, "
			 * +
			 * "null,'TOTAL' as quadra "+
			 * "from  localidade c, localidade d, gerencia_regional e, setor_comercial f "+
			 * "where c.loca_cdelo = d.loca_id "+
			 * "and   c.greg_id = e.greg_id "+
			 * "and   d.loca_id = f.loca_id "+
			 * "union "+
			 * "select "+
			 * "'ESTADO'as estado, "+
			 * "e.greg_id as greg_id, lpad(e.greg_id,3,0)||' - '||e.greg_nmabreviado||' - '||e.greg_nmregional as gerencia, "
			 * +
			 * "d.loca_id as elo_id, lpad(d.loca_id,3,0)||' - '||d.loca_nmlocalidade as elo, "+
			 * 
			 * "c.loca_id as loca_id, lpad(c.loca_id,3,0)||' - '||c.loca_nmlocalidade as localidade, "+
			 * "null,'TOTAL' as setor, "+
			 * "null,'TOTAL' as quadra "+
			 * "from  localidade c, localidade d, gerencia_regional e "+
			 * "where c.loca_cdelo = d.loca_id "+
			 * "and   c.greg_id = e.greg_id "+
			 * "union "+
			 * "select "+
			 * "'ESTADO'as estado, "+
			 * "e.greg_id as greg_id, lpad(e.greg_id,3,0)||' - '||e.greg_nmabreviado||' - '||e.greg_nmregional as gerencia, "
			 * +
			 * "d.loca_id as elo_id, lpad(d.loca_id,3,0)||' - '||d.loca_nmlocalidade as elo, "+
			 * "null,'TOTAL' as localidade, "+
			 * "null,'TOTAL' as setor, "+
			 * "null,'TOTAL' as quadra "+
			 * "from  localidade c, localidade d, gerencia_regional e "+
			 * "where c.loca_cdelo = d.loca_id "+
			 * "and c.greg_id = e.greg_id "+
			 * "union "+
			 * "select "+
			 * "'ESTADO'as estado, "+
			 * "e.greg_id as greg_id, lpad(e.greg_id,3,0)||' - '||e.greg_nmabreviado||' - '||e.greg_nmregional as gerencia, "
			 * +
			 * "null,'TOTAL' as elo, "+
			 * "null,'TOTAL' as localidade, "+
			 * "null,'TOTAL' as setor, "+
			 * "null,'TOTAL' as quadra "+
			 * "from  gerencia_regional e "+
			 * "union "+
			 * "select "+
			 * "'ESTADO'as estado, "+
			 * "null,'TOTAL' as gerencia, "+
			 * "null,'TOTAL' as elo, "+
			 * "null,'TOTAL' as localidade, "+
			 * "null,'TOTAL' as setor, "+
			 * "null,'TOTAL' as quadra "+
			 * "from  gerencia_regional e "+
			 * "order by 1,2,3,4,5,6,7,8,9,10,11";
			 * session.createSQLQuery(sqlTabelaEstrutura).executeUpdate();
			 * String sqlTabelaFaturamento = "select " +
			 * "'ESTADO'as estado,  " +
			 * "e.greg_id as greg_id, lpad(e.greg_id,3,0)||' - '||e.greg_nmabreviado||' - '||e.greg_nmregional as gerencia, "
			 * +
			 * "d.loca_id as elo_id, lpad(d.loca_id,3,0)||' - '||d.loca_nmlocalidade as elo,  " +
			 * "c.loca_id as loca_id, lpad(c.loca_id,3,0)||' - '||c.loca_nmlocalidade as localidade, "
			 * +
			 * "f.stcm_id as stcm_id, lpad(f.stcm_cdsetorcomercial,3,0)||' - '||stcm_nmsetorcomercial as setor, "
			 * +
			 * "g.qdra_id as qdra_id, lpad(g.qdra_nnquadra,3,0) as quadra, " +
			 * "sum(a.rfts_qtcontas) as FatContas, sum(a.rfts_vlagua + a.rfts_vlesgoto + a.rfts_vldebitos - a.rfts_vlcreditos) as FatValor "
			 * +
			 * "into TEMP table faturamento " +
			 * "from  resumo_faturamento_simulacao a, localidade c, localidade d, gerencia_regional e, setor_comercial f, quadra g "
			 * +
			 * "where a.loca_id = c.loca_id  " +
			 * "and   c.loca_cdelo = d.loca_id  " +
			 * "and   a.greg_id = e.greg_id " +
			 * "and   a.stcm_id = f.stcm_id " +
			 * "and   a.qdra_id = g.qdra_id  " +
			 * "and   a.rfts_amreferencia="+anoMesReferencia+" "+
			 * "group by 1,2,3,4,5,6,7,8,9,10,11 " +
			 * "union " +
			 * "select  " +
			 * "'ESTADO'as estado, " +
			 * "e.greg_id as greg_id, lpad(e.greg_id,3,0)||' - '||e.greg_nmabreviado||' - '||e.greg_nmregional as gerencia, "
			 * +
			 * "d.loca_id as elo_id, lpad(d.loca_id,3,0)||' - '||d.loca_nmlocalidade as elo,  " +
			 * "c.loca_id as loca_id, lpad(c.loca_id,3,0)||' - '||c.loca_nmlocalidade as localidade, "
			 * +
			 * "f.stcm_id as stcm_id, lpad(f.stcm_cdsetorcomercial,3,0)||' - '||stcm_nmsetorcomercial as setor, "
			 * +
			 * "null,'TOTAL' as quadra, " +
			 * "sum(a.rfts_qtcontas) as FatContas, sum(a.rfts_vlagua + a.rfts_vlesgoto + a.rfts_vldebitos - a.rfts_vlcreditos) as FatValor "
			 * +
			 * "from  resumo_faturamento_simulacao a, localidade c, localidade d, gerencia_regional e, setor_comercial f "
			 * +
			 * "where a.loca_id = c.loca_id  " +
			 * "and   c.loca_cdelo = d.loca_id  " +
			 * "and   a.greg_id = e.greg_id " +
			 * "and   a.stcm_id = f.stcm_id " +
			 * "and   a.rfts_amreferencia ="+anoMesReferencia+ " "+
			 * "group by 1,2,3,4,5,6,7,8,9 " +
			 * "union " +
			 * "select  " +
			 * "'ESTADO'as estado, " +
			 * "e.greg_id as greg_id, lpad(e.greg_id,3,0)||' - '||e.greg_nmabreviado||' - '||e.greg_nmregional as gerencia, "
			 * +
			 * "d.loca_id as elo_id, lpad(d.loca_id,3,0)||' - '||d.loca_nmlocalidade as elo,  " +
			 * "c.loca_id as loca_id, lpad(c.loca_id,3,0)||' - '||c.loca_nmlocalidade as localidade, "
			 * +
			 * "null,'LOCAL' as setor,  " +
			 * "null,'LOCAL' as quadra, " +
			 * "sum(a.rfts_qtcontas) as FatContas, sum(a.rfts_vlagua + a.rfts_vlesgoto + a.rfts_vldebitos - a.rfts_vlcreditos) as FatValor "
			 * +
			 * "from  resumo_faturamento_simulacao a, localidade c, localidade d, gerencia_regional e "
			 * +
			 * "where a.loca_id = c.loca_id  " +
			 * "and   c.loca_cdelo = d.loca_id  " +
			 * "and   a.greg_id = e.greg_id " +
			 * "and   a.rfts_amreferencia ="+anoMesReferencia+" "+
			 * "group by 1,2,3,4,5,6,7 " +
			 * "union " +
			 * "select " +
			 * "'ESTADO'as estado, " +
			 * "e.greg_id as greg_id, lpad(e.greg_id,3,0)||' - '||e.greg_nmabreviado||' - '||e.greg_nmregional as gerencia, "
			 * +
			 * "d.loca_id as elo_id, lpad(d.loca_id,3,0)||' - '||d.loca_nmlocalidade as elo,  " +
			 * "null,'LOCAL' as localidade,  " +
			 * "null,'LOCAL' as setor,  " +
			 * "null,'LOCAL' as quadra, " +
			 * "sum(a.rfts_qtcontas) as FatContas, sum(a.rfts_vlagua + a.rfts_vlesgoto + a.rfts_vldebitos - a.rfts_vlcreditos) as FatValor "
			 * +
			 * "from  resumo_faturamento_simulacao a, localidade c, localidade d, gerencia_regional e "
			 * +
			 * "where a.loca_id = c.loca_id  " +
			 * "and   c.loca_cdelo = d.loca_id  " +
			 * "and   a.greg_id = e.greg_id " +
			 * "and   a.rfts_amreferencia ="+anoMesReferencia+ " "+
			 * "group by 1,2,3,4,5 " +
			 * "union " +
			 * "select " +
			 * "'ESTADO'as estado, " +
			 * "e.greg_id as greg_id, lpad(e.greg_id,3,0)||' - '||e.greg_nmabreviado||' - '||e.greg_nmregional as gerencia, "
			 * +
			 * "null,'LOCAL' as elo,  " +
			 * "null,'LOCAL' as localidade, " +
			 * "null,'LOCAL' as setor,  " +
			 * "null,'LOCAL' as quadra, " +
			 * "sum(a.rfts_qtcontas) as FatContas, sum(a.rfts_vlagua + a.rfts_vlesgoto + a.rfts_vldebitos - a.rfts_vlcreditos) as FatValor "
			 * +
			 * "from  resumo_faturamento_simulacao a, gerencia_regional e " +
			 * "where a.greg_id = e.greg_id " +
			 * "and   a.rfts_amreferencia = "+anoMesReferencia+ " "+
			 * "group by 1,2,3 " +
			 * "union " +
			 * "select  " +
			 * "'ESTADO'as estado, " +
			 * "null,'TOTAL' as gerencia, " +
			 * "null,'TOTAL' as elo,  " +
			 * "null,'TOTAL' as localidade, " +
			 * "null,'TOTAL' as setor,  " +
			 * "null,'TOTAL' as quadra, " +
			 * "sum(a.rfts_qtcontas) as FatContas, sum(a.rfts_vlagua + a.rfts_vlesgoto + a.rfts_vldebitos - a.rfts_vlcreditos) as FatValor "
			 * +
			 * "from  resumo_faturamento_simulacao a, gerencia_regional e " +
			 * "where a.greg_id = e.greg_id " +
			 * "and   a.rfts_amreferencia = "+anoMesReferencia+ " "+
			 * "group by 1 " +
			 * "order by 1,2,3,4,5,6,7,8,9,10,11 ";
			 * session.createSQLQuery(sqlTabelaFaturamento).executeUpdate();
			 * String sqlTabelaArrecadacao = "select " +
			 * "'ESTADO' as estado," +
			 * "e.greg_id as greg_id, lpad(e.greg_id,3,0)||' - '||e.greg_nmabreviado||' - '||e.greg_nmregional as gerencia, "
			 * +
			 * "d.loca_id as elo_id, lpad(d.loca_id,3,0)||' - '||d.loca_nmlocalidade as elo,  " +
			 * "c.loca_id as loca_id, lpad(c.loca_id,3,0)||' - '||c.loca_nmlocalidade as localidade, "
			 * +
			 * "f.stcm_id as stcm_id, lpad(f.stcm_cdsetorcomercial,3,0)||' - '||stcm_nmsetorcomercial as setor, "
			 * +
			 * "g.qdra_id as qdra_id, lpad(g.qdra_nnquadra,3,0) as quadra, " +
			 * "sum(a.ardd_qtpagamentos) as ArrContas, sum(a.ardd_vlpagamentos) as ArrValor " +
			 * "into TEMP table arrecadacao " +
			 * "from  arrecadacao_dados_diarios a, localidade c, localidade d, gerencia_regional e, setor_comercial f, quadra g "
			 * +
			 * "where a.loca_id = c.loca_id  " +
			 * "and   c.loca_cdelo = d.loca_id  " +
			 * "and   a.greg_id = e.greg_id " +
			 * "and   a.stcm_id = f.stcm_id " +
			 * "and   a.qdra_id = g.qdra_id  " +
			 * "and   a.ardd_amreferenciaarrecadacao = "+anoMesReferencia+ " and a.dotp_id = 1 " +
			 * "group by 1,2,3,4,5,6,7,8,9,10,11 " +
			 * "union " +
			 * "select  " +
			 * "'ESTADO'as estado, " +
			 * "e.greg_id as greg_id, lpad(e.greg_id,3,0)||' - '||e.greg_nmabreviado||' - '||e.greg_nmregional as gerencia, "
			 * +
			 * "d.loca_id as elo_id, lpad(d.loca_id,3,0)||' - '||d.loca_nmlocalidade as elo,  " +
			 * "c.loca_id as loca_id, lpad(c.loca_id,3,0)||' - '||c.loca_nmlocalidade as localidade, "
			 * +
			 * "f.stcm_id as stcm_id, lpad(f.stcm_cdsetorcomercial,3,0)||' - '||stcm_nmsetorcomercial as setor, "
			 * +
			 * "null,'TOTAL' as quadra, " +
			 * "sum(a.ardd_qtpagamentos) as ArrContas, sum(a.ardd_vlpagamentos) as ArrValor " +
			 * "from  arrecadacao_dados_diarios a, localidade c, localidade d, gerencia_regional e, setor_comercial f "
			 * +
			 * "where a.loca_id = c.loca_id  " +
			 * "and   c.loca_cdelo = d.loca_id  " +
			 * "and   a.greg_id = e.greg_id " +
			 * "and   a.stcm_id = f.stcm_id " +
			 * "and   a.ardd_amreferenciaarrecadacao = "+anoMesReferencia+ " and a.dotp_id = 1 " +
			 * "group by 1,2,3,4,5,6,7,8,9 " +
			 * "union " +
			 * "select  " +
			 * "'ESTADO'as estado, " +
			 * "e.greg_id as greg_id, lpad(e.greg_id,3,0)||' - '||e.greg_nmabreviado||' - '||e.greg_nmregional as gerencia, "
			 * +
			 * "d.loca_id as elo_id, lpad(d.loca_id,3,0)||' - '||d.loca_nmlocalidade as elo,  " +
			 * "c.loca_id as loca_id, lpad(c.loca_id,3,0)||' - '||c.loca_nmlocalidade as localidade, "
			 * +
			 * "null,'TOTAL' as setor,  " +
			 * "null,'TOTAL' as quadra, " +
			 * "sum(a.ardd_qtpagamentos) as ArrContas, sum(a.ardd_vlpagamentos) as ArrValor " +
			 * "from  arrecadacao_dados_diarios a, localidade c, localidade d, gerencia_regional e "
			 * +
			 * "where a.loca_id = c.loca_id  " +
			 * "and   c.loca_cdelo = d.loca_id  " +
			 * "and   a.greg_id = e.greg_id " +
			 * "and   a.ardd_amreferenciaarrecadacao = "+anoMesReferencia+ " and a.dotp_id = 1 " +
			 * "group by 1,2,3,4,5,6,7 " +
			 * "union " +
			 * "select  " +
			 * "'ESTADO'as estado, " +
			 * "e.greg_id as greg_id, lpad(e.greg_id,3,0)||' - '||e.greg_nmabreviado||' - '||e.greg_nmregional as gerencia, "
			 * +
			 * "d.loca_id as elo_id, lpad(d.loca_id,3,0)||' - '||d.loca_nmlocalidade as elo,  " +
			 * "null,'TOTAL' as localidade,  " +
			 * "null,'TOTAL' as setor,  " +
			 * "null,'TOTAL' as quadra, " +
			 * "sum(a.ardd_qtpagamentos) as ArrContas, sum(a.ardd_vlpagamentos) as ArrValor " +
			 * "from  arrecadacao_dados_diarios a, localidade c, localidade d, gerencia_regional e "
			 * +
			 * "where a.loca_id = c.loca_id  " +
			 * "and   c.loca_cdelo = d.loca_id  " +
			 * "and   a.greg_id = e.greg_id " +
			 * "and   a.ardd_amreferenciaarrecadacao = "+anoMesReferencia+ " and a.dotp_id = 1 " +
			 * "group by 1,2,3,4,5 " +
			 * "union " +
			 * "select  " +
			 * "'ESTADO'as estado, " +
			 * "e.greg_id as greg_id, lpad(e.greg_id,3,0)||' - '||e.greg_nmabreviado||' - '||e.greg_nmregional as gerencia, "
			 * +
			 * "null,'TOTAL' as elo,  " +
			 * "null,'TOTAL' as localidade, " +
			 * "null,'TOTAL' as setor,  " +
			 * "null,'TOTAL' as quadra, " +
			 * "sum(a.ardd_qtpagamentos) as ArrContas, sum(a.ardd_vlpagamentos) as ArrValor " +
			 * "from  arrecadacao_dados_diarios a, gerencia_regional e " +
			 * "where a.greg_id = e.greg_id " +
			 * "and   a.ardd_amreferenciaarrecadacao = "+anoMesReferencia+ " and a.dotp_id = 1 " +
			 * "group by 1,2,3 " +
			 * "union " +
			 * "select " +
			 * "'ESTADO'as estado, " +
			 * "null,'TOTAL' as gerencia, " +
			 * "null,'TOTAL' as elo,  " +
			 * "null,'TOTAL' as localidade, " +
			 * "null,'TOTAL' as setor,  " +
			 * "null,'TOTAL' as quadra, " +
			 * "sum(a.ardd_qtpagamentos) as ArrContas, sum(a.ardd_vlpagamentos) as ArrValor " +
			 * "from  arrecadacao_dados_diarios a, gerencia_regional e " +
			 * "where a.greg_id = e.greg_id " +
			 * "and a.ardd_amreferenciaarrecadacao = "+anoMesReferencia+ " and a.dotp_id = 1 " +
			 * "group by 1 " +
			 * "order by 1,2,3,4,5,6,7,8,9,10,11 ";
			 * session.createSQLQuery(sqlTabelaArrecadacao).executeUpdate();
			 * String sqlTabelaPendencia = "select "+
			 * "'ESTADO'as estado, "+
			 * "e.greg_id as greg_id, lpad(e.greg_id,3,0)||' - '||e.greg_nmabreviado||' - '||e.greg_nmregional as gerencia, "
			 * +
			 * "d.loca_id as elo_id, lpad(d.loca_id,3,0)||' - '||d.loca_nmlocalidade as elo, "+
			 * 
			 * "c.loca_id as loca_id, lpad(c.loca_id,3,0)||' - '||c.loca_nmlocalidade as localidade, "+
			 * "f.stcm_id as stcm_id, lpad(f.stcm_cdsetorcomercial,3,0)||' - '||stcm_nmsetorcomercial as setor, "
			 * +
			 * "g.qdra_id as qdra_id, lpad(g.qdra_nnquadra,3,0) as quadra, "+
			 * "sum(a.rpen_qtdocumentos) as PenContas, sum(a.rpen_vldebito) as PenValor "+
			 * "into TEMP table pendencia "+
			 * "from  resumo_pendencia a, localidade c, localidade d, gerencia_regional e, setor_comercial f, quadra g "
			 * +
			 * "where a.loca_id = c.loca_id  "+
			 * "and   c.loca_cdelo = d.loca_id  "+
			 * "and   a.greg_id = e.greg_id "+
			 * "and   a.stcm_id = f.stcm_id "+
			 * "and   a.qdra_id = g.qdra_id  "+
			 * "and   a.rpen_amreferencia = "+anoMesReferencia+ " and a.dotp_id = 1 "+
			 * "group by 1,2,3,4,5,6,7,8,9,10,11 "+
			 * "union "+
			 * "select  "+
			 * "'ESTADO'as estado, "+
			 * "e.greg_id as greg_id, lpad(e.greg_id,3,0)||' - '||e.greg_nmabreviado||' - '||e.greg_nmregional as gerencia, "
			 * +
			 * "d.loca_id as elo_id, lpad(d.loca_id,3,0)||' - '||d.loca_nmlocalidade as elo,  "+
			 * 
			 * "c.loca_id as loca_id, lpad(c.loca_id,3,0)||' - '||c.loca_nmlocalidade as localidade, "+
			 * "f.stcm_id as stcm_id, lpad(f.stcm_cdsetorcomercial,3,0)||' - '||stcm_nmsetorcomercial as setor, "
			 * +
			 * "null,'TOTAL' as quadra, "+
			 * "sum(a.rpen_qtdocumentos) as PenContas, sum(a.rpen_vldebito) as PenValor "+
			 * "from  resumo_pendencia a, localidade c, localidade d, gerencia_regional e, setor_comercial f "
			 * +
			 * "where a.loca_id = c.loca_id  "+
			 * "and   c.loca_cdelo = d.loca_id  "+
			 * "and   a.greg_id = e.greg_id "+
			 * "and   a.stcm_id = f.stcm_id "+
			 * "and   a.rpen_amreferencia = "+anoMesReferencia+ " and a.dotp_id = 1 "+
			 * "group by 1,2,3,4,5,6,7,8,9 "+
			 * "union "+
			 * "select  "+
			 * "'ESTADO'as estado, "+
			 * "e.greg_id as greg_id, lpad(e.greg_id,3,0)||' - '||e.greg_nmabreviado||' - '||e.greg_nmregional as gerencia, "
			 * +
			 * "d.loca_id as elo_id, lpad(d.loca_id,3,0)||' - '||d.loca_nmlocalidade as elo,  "+
			 * 
			 * "c.loca_id as loca_id, lpad(c.loca_id,3,0)||' - '||c.loca_nmlocalidade as localidade, "+
			 * "null,'TOTAL' as setor,  "+
			 * "null,'TOTAL' as quadra, "+
			 * "sum(a.rpen_qtdocumentos) as PenContas, sum(a.rpen_vldebito) as PenValor "+
			 * "from  resumo_pendencia a, localidade c, localidade d, gerencia_regional e "+
			 * "where a.loca_id = c.loca_id  "+
			 * "and   c.loca_cdelo = d.loca_id  "+
			 * "and   a.greg_id = e.greg_id "+
			 * "and   a.rpen_amreferencia = "+anoMesReferencia+ " and a.dotp_id = 1 "+
			 * "group by 1,2,3,4,5,6,7 "+
			 * "union "+
			 * "select  "+
			 * "'ESTADO'as estado, "+
			 * "e.greg_id as greg_id, lpad(e.greg_id,3,0)||' - '||e.greg_nmabreviado||' - '||e.greg_nmregional as gerencia, "
			 * +
			 * "d.loca_id as elo_id, lpad(d.loca_id,3,0)||' - '||d.loca_nmlocalidade as elo,  "+
			 * "null,'TOTAL' as localidade,  "+
			 * "null,'TOTAL' as setor,  "+
			 * "null,'TOTAL' as quadra, "+
			 * "sum(a.rpen_qtdocumentos) as PenContas, sum(a.rpen_vldebito) as PenValor "+
			 * "from  resumo_pendencia a, localidade c, localidade d, gerencia_regional e "+
			 * "where a.loca_id = c.loca_id  "+
			 * "and   c.loca_cdelo = d.loca_id  "+
			 * "and   a.greg_id = e.greg_id "+
			 * "and   a.rpen_amreferencia = "+anoMesReferencia+ " and a.dotp_id = 1 "+
			 * "group by 1,2,3,4,5 "+
			 * "union "+
			 * "select  "+
			 * "'ESTADO'as estado, "+
			 * "e.greg_id as greg_id, lpad(e.greg_id,3,0)||' - '||e.greg_nmabreviado||' - '||e.greg_nmregional as gerencia, "
			 * +
			 * "null,'LOCAL' as elo,  "+
			 * "null,'LOCAL' as localidade, "+
			 * "null,'LOCAL' as setor,  "+
			 * "null,'LOCAL' as quadra, "+
			 * "sum(a.rpen_qtdocumentos) as PenContas, sum(a.rpen_vldebito) as PenValor "+
			 * "from  resumo_pendencia a, gerencia_regional e "+
			 * "where a.greg_id = e.greg_id "+
			 * "and   a.rpen_amreferencia = "+anoMesReferencia+ " and a.dotp_id = 1 "+
			 * "group by 1,2,3 "+
			 * "union "+
			 * "select  "+
			 * "'ESTADO'as estado, "+
			 * "null,'LOCAL' as gerencia, "+
			 * "null,'LOCAL' as elo,  "+
			 * "null,'LOCAL' as localidade, "+
			 * "null,'LOCAL' as setor,  "+
			 * "null,'LOCAL' as quadra, "+
			 * "sum(a.rpen_qtdocumentos) as PenContas, sum(a.rpen_vldebito) as PenValor "+
			 * "from  resumo_pendencia a, gerencia_regional e "+
			 * "where a.greg_id = e.greg_id "+
			 * "and   a.rpen_amreferencia ="+anoMesReferencia+ " and a.dotp_id = 1 "+
			 * "group by 1 "+
			 * "order by 1,2,3,4,5,6,7,8,9,10,11 ";
			 * session.createSQLQuery(sqlTabelaPendencia).executeUpdate();
			 * String resultadoSemPercentuais = "select a.estado, "+
			 * "a.greg_id, "+
			 * "a.gerencia, "+
			 * "a.elo_id, "+
			 * "a.elo, "+
			 * "a.loca_id, "+
			 * "a.localidade, "+
			 * "a.stcm_id, "+
			 * "a.setor, "+
			 * "a.qdra_id, "+
			 * "a.quadra, "+
			 * "b.fatcontas, b.fatvalor, c.arrcontas, c.arrvalor, d.pencontas, d.penvalor "+
			 * "into temp resultado "+
			 * "from estrutura a "+
			 * "left join faturamento b on a.estado = b.estado "+
			 * "and a.gerencia = b.gerencia "+
			 * "and a.elo = b.elo  "+
			 * "and a.localidade = b.localidade "+
			 * "and a.setor = b.setor "+
			 * "and a.quadra = b.quadra  "+
			 * "left  join arrecadacao c on a.estado = c.estado "+
			 * "and a.gerencia = c.gerencia "+
			 * "and a.elo = c.elo "+
			 * "and a.localidade = c.localidade "+
			 * "and a.setor = c.setor "+
			 * "and a.quadra = c.quadra "+
			 * "left  join pendencia d on a.estado = d.estado "+
			 * "and a.gerencia = d.gerencia "+
			 * "and a.elo = d.elo "+
			 * "and a.localidade = d.localidade "+
			 * "and a.setor = d.setor "+
			 * "and a.quadra = d.quadra ";
			 * session.createSQLQuery(resultadoSemPercentuais).executeUpdate();
			 */
			String resultadoComPercentuais = "select a.estado, "
							+ "a.greg_id, "
							+ "a.gerencia, "
							+ "a.elo_id, "
							+ "a.elo, "
							+ "a.loca_id, "
							+ "a.localidade, "
							+ "a.stcm_id, "
							+ "a.setor,  "
							+ "a.qdra_id, "
							+ "a.quadra, "
							+ "a.fatcontas, a.fatvalor, a.arrcontas, a.arrvalor,(a.arrcontas/a.fatcontas)* 100 , (a.arrvalor/a.fatvalor) * 100, a.pencontas, a.penvalor, "
							+ "(a.pencontas/a.fatcontas), (a.penvalor/a.fatvalor) " + "into temp resultadocompercentual "
							+ "from resultado a " + "where (a.fatcontas is not null " + "and    a.fatcontas > 0) "
							+ "and   (a.fatvalor is not null " + "and    a.fatvalor  > 0) " + "union " + "select a.estado, "
							+ "a.greg_id, " + "a.gerencia, " + "a.elo_id, " + "a.elo, " + "a.loca_id, " + "a.localidade, " + "a.stcm_id, "
							+ "a.setor, " + "a.qdra_id, " + "a.quadra, "
							+ "a.fatcontas, a.fatvalor, a.arrcontas, a.arrvalor,0 , 0, a.pencontas, a.penvalor, " + "0, 0 "
							+ "from resultado a " + "where (a.fatcontas is  null " + "or a.fatcontas = 0) " + "or (a.fatvalor is  null "
							+ "or a.fatvalor  = 0) " + "order by 1,2,3,4,5,6,7,8,9,10,11";

			session.createSQLQuery(resultadoComPercentuais).executeUpdate();

			// OPÇÕES DE TOTALIZADOR
			String sqlConsultar = "select fatcontas,fatvalor,arrcontas,arrvalor,percontas,pervalor,pencontas,penvalor,nvezesfatcontas,nvezesfatvalor from resultadocompercentual ";
			switch(informarDadosGeracaoRelatorioConsultaHelper.getOpcaoTotalizacao().intValue()){

				case ConstantesSistema.CODIGO_GRUPO_FATURAMENTO:
					sqlConsultar = sqlConsultar + "where ";
					break;
				case ConstantesSistema.CODIGO_GERENCIA_REGIONAL:
					sqlConsultar = sqlConsultar + "where greg_id=" + informarDadosGeracaoRelatorioConsultaHelper.getGerenciaRegional()
									+ "and elo_id is null";
					break;
				case ConstantesSistema.CODIGO_ELO_POLO:
					sqlConsultar = sqlConsultar + "where ";
					break;
				case ConstantesSistema.CODIGO_LOCALIDADE:
					sqlConsultar = sqlConsultar + "where ";
					break;
				case ConstantesSistema.CODIGO_SETOR_COMERCIAL:
					sqlConsultar = sqlConsultar + "where ";
					break;
				case ConstantesSistema.CODIGO_QUADRA:
					sqlConsultar = sqlConsultar + "where ";
					break;

				case ConstantesSistema.CODIGO_ESTADO:
					sqlConsultar = sqlConsultar + "where greg_id is null";
					break;
			}

			retorno = session.createSQLQuery(resultadoComPercentuais).addScalar("fatcontas", Hibernate.INTEGER).addScalar("fatvalor",
							Hibernate.BIG_DECIMAL).addScalar("arrcontas", Hibernate.INTEGER).addScalar("arrvalor", Hibernate.BIG_DECIMAL)
							.addScalar("percontas", Hibernate.DOUBLE).addScalar("pervalor", Hibernate.DOUBLE).addScalar("pencontas",
											Hibernate.INTEGER).addScalar("penvalor", Hibernate.BIG_DECIMAL).addScalar("nvezesfatcontas",
											Hibernate.DOUBLE).addScalar("nvezesfatvalor", Hibernate.DOUBLE).list();

			// erro no hibernate
		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}
		// retorna a coleção com os resultados da pesquisa
		return retorno;

	}

	/**
	 * Pesquisa o valor e a quantidade de contas do resumo da faturamento
	 * [UC0350] - Consultar Comparativo entre os Resumos do Faturamento, Arrecadação e da Pendência.
	 * 
	 * @author Pedro Alexandre
	 * @date 09/06/2006
	 * @param informarDadosGeracaoRelatorioConsultaHelper
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List consultarResumoFaturamento(InformarDadosGeracaoRelatorioConsultaHelper informarDadosGeracaoRelatorioConsultaHelper)
					throws ErroRepositorioException{

		// Cria a coleção de retorno
		List retorno = new ArrayList();

		// Obtém a sessão
		Session session = HibernateUtil.getSession();

		// A query abaixo realiza uma consulta a tabela de ResumoAnaliseFaturamento
		try{
			// Gera o sql para acumular o valor e a quantidade das contas
			GeradorSqlRelatorio geradorSqlRelatorio = new GeradorSqlRelatorio(GeradorSqlRelatorio.RESUMO_FATURAMENTO,
							informarDadosGeracaoRelatorioConsultaHelper);

			// Concatena o sql gerado
			String sql = geradorSqlRelatorio.sqlNivelUm(geradorSqlRelatorio.getNomeCampoFixo(), geradorSqlRelatorio.getNomeTabelaFixo(),
							geradorSqlRelatorio.getNomeTabelaFixoTotal(), "'"
											+ informarDadosGeracaoRelatorioConsultaHelper.getDescricaoOpcaoTotalizacao() + "'", "", "", "",
							false);

			// Retira os alias a mais
			sql.replaceAll("as somatorio", "");
			String sqlFormatado = sql.replaceAll("as somatorio", "").replaceAll("as campo  as estado, as descricao", " as descricao,");

			// Faz a pesquisa
			retorno = session.createSQLQuery(sqlFormatado).addScalar("fatcontas", Hibernate.INTEGER)
							.addScalar("fatvalor", Hibernate.BIG_DECIMAL)
							.list();

			// Erro no hibernate
		}catch(HibernateException e){
			// Levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// Fecha a sessão
			HibernateUtil.closeSession(session);
		}

		// Retorna a coleção com os resultados da pesquisa
		return retorno;
	}

	/**
	 * Pesquisa o valor e a quantidade de contas do resumo da arrecadação
	 * [UC0350] - Consultar Comparativo entre os Resumos do Faturamento, Arrecadação e da Pendência.
	 * 
	 * @author Pedro Alexandre
	 * @date 09/06/2006
	 * @param informarDadosGeracaoRelatorioConsultaHelper
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List consultarResumoArrecadacao(InformarDadosGeracaoRelatorioConsultaHelper informarDadosGeracaoRelatorioConsultaHelper)
					throws ErroRepositorioException{

		// Cria a coleção de retorno
		List retorno = new ArrayList();

		// Obtém a sessão
		Session session = HibernateUtil.getSession();

		// A query abaixo realiza uma consulta a tabela de ResumoAnaliseFaturamento
		try{
			// Gera o sql para acumular o valor e a quantidade das contas
			GeradorSqlRelatorio geradorSqlRelatorio = new GeradorSqlRelatorio(GeradorSqlRelatorio.RESUMO_ARRECADACAO,
							informarDadosGeracaoRelatorioConsultaHelper);

			// Concatena o sql gerado
			String sql = geradorSqlRelatorio.sqlNivelUm(geradorSqlRelatorio.getNomeCampoFixo(), geradorSqlRelatorio.getNomeTabelaFixo(),
							geradorSqlRelatorio.getNomeTabelaFixoTotal(), "'"
											+ informarDadosGeracaoRelatorioConsultaHelper.getDescricaoOpcaoTotalizacao() + "'", "", "", "",
							false);

			// Retira os alias desnecessarios
			sql.replaceAll("as somatorio", "");
			String sqlFormatado = sql.replaceAll("as somatorio", "").replaceAll("as campo  as estado, as descricao", " as descricao,");

			// Faz a pesquisa
			retorno = session.createSQLQuery(sqlFormatado).addScalar("arrcontas", Hibernate.INTEGER)
							.addScalar("arrvalor", Hibernate.BIG_DECIMAL)
							.list();

			// Erro no hibernate
		}catch(HibernateException e){
			// Levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// Fecha a sessão
			HibernateUtil.closeSession(session);
		}

		// Retorna a coleção com os resultados da pesquisa
		return retorno;
	}

	/**
	 * Pesquisa o valor e a quantidade de contas do resumo da pendência.
	 * [UC0350] - Consultar Comparativo entre os Resumos do Faturamento, Arrecadação e da Pendência.
	 * 
	 * @author Pedro Alexandre
	 * @date 09/06/2006
	 * @param informarDadosGeracaoRelatorioConsultaHelper
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List consultarComparativoResumoPendencia(InformarDadosGeracaoRelatorioConsultaHelper informarDadosGeracaoRelatorioConsultaHelper)
					throws ErroRepositorioException{

		// Cria a coleção de retorno
		List retorno = new ArrayList();

		// Obtém a sessão
		Session session = HibernateUtil.getSession();

		// A query abaixo realiza uma consulta a tabela de ResumoAnaliseFaturamento
		try{
			// Gera o sql para acumular o valor e a quantidade das contas
			GeradorSqlRelatorio geradorSqlRelatorio = new GeradorSqlRelatorio(GeradorSqlRelatorio.RESUMO_PENDENCIA,
							informarDadosGeracaoRelatorioConsultaHelper);

			// Concatena o sql gerado
			String sql = geradorSqlRelatorio.sqlNivelUm(geradorSqlRelatorio.getNomeCampoFixo(), geradorSqlRelatorio.getNomeTabelaFixo(),
							geradorSqlRelatorio.getNomeTabelaFixoTotal(), "'"
											+ informarDadosGeracaoRelatorioConsultaHelper.getDescricaoOpcaoTotalizacao() + "'", "", "", "",
							false);

			// Retira os alias a mais
			sql.replaceAll("as somatorio", "");
			String sqlFormatado = sql.replaceAll("as somatorio", "").replaceAll("as campo  as estado, as descricao", " as descricao,");


			// Faz a pesquisa
			retorno = session.createSQLQuery(sqlFormatado).addScalar("pencontas", Hibernate.INTEGER)
							.addScalar("penvalor", Hibernate.BIG_DECIMAL)
							.list();

			// Erro no hibernate
		}catch(HibernateException e){
			// Levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// Fecha a sessão
			HibernateUtil.closeSession(session);
		}

		// Retorna a coleção com os resultados da pesquisa
		return retorno;
	}

	/**
	 * Pesquisa os valores necessarios na tabela un_resumo_ligacao_economia.
	 * [UC0722] - Gerar Relatorio para Orçamento e SINP
	 * 
	 * @author Rafael Pinto
	 * @date 20/11/2006
	 * @param FiltrarRelatorioOrcamentoSINPHelper
	 * @return Collection<Object[35]>
	 *         Object[0] - aguaTotalLigacoesCadastradas
	 *         Object[1] - esgotoTotalLigacoesCadastradas
	 *         Object[2] - esgotoTotalLigacoesCadastradasConvencional
	 *         Object[3] - aguaTotalLigacoesAtivas
	 *         Object[4] - esgotoTotalLigacoesCadastradasCondominial
	 *         Object[5] - aguaTotalLigacoesMedidas
	 *         Object[6] - esgotoTotalLigacoesAtivasConvencional
	 *         Object[7] - aguaTotalLigacoesComHidrometro
	 *         Object[8] - esgotoTotalLigacoesAtivasCondominial
	 *         Object[9] - aguaTotalLigacoesResidencialCadastradas
	 *         Object[10] - esgotoTotalLigacoesResidencialCadastradas
	 *         Object[11] - aguaTotalLigacoesDesligadas
	 *         Object[12] - aguaTotalEconomiasCadastradas
	 *         Object[13] - esgotoTotalEconomiasCadastradasConverncional
	 *         Object[14] - aguaTotalEconomiasAtivas
	 *         Object[15] - aguaTotalEconomiasCadastradasCondominial
	 *         Object[16] - aguaTotalEconomiasAtivasMedidas
	 *         Object[17] - esgotoTotalEconomiasAtivasConvencional
	 *         Object[18] - aguaTotalEconomiasResidencialCadastradas
	 *         Object[19] - esgotoTotalEconomiasAtivasCondominial
	 *         Object[20] - aguaTotalEconomiasResidencialAtivasMicromedidas
	 *         Object[21] - esgotoTotalEconomiasResidencialCadastradas
	 *         Object[22] - aguaTotalEconomiasResidencialAtivas
	 *         Object[23] - esgotoTotalEconomiasResidencialAtivas
	 *         Object[24] - aguaTotalEconomiasComercialAtivas
	 *         Object[25] - esgotoTotalEconomiasComercialAtivas
	 *         Object[26] - aguaTotalEconomiasIndustrialAtivas
	 *         Object[27] - esgotoTotalEconomiasIndustrialAtivas
	 *         Object[28] - aguaTotalEconomiasPublicoAtivas
	 *         Object[29] - esgotoTotalEconomiasPublicoAtivas
	 *         Object[30] - aguaTotalEconomiasRuralAtivas
	 *         Object[31] - aguaTotalLigacoesSuprimidas
	 *         Object[32] - campoSelecionado pode ser (loca_id,uneg_id,greg_id)
	 * @throws ErroRepositorioException
	 */
	public Object[] pesquisarRelatorioOrcamentoSINPResumoLigacaoEconomia(
					FiltrarRelatorioOrcamentoSINPHelper filtrarRelatorioOrcamentoSINPHelper) throws ErroRepositorioException{

		Object[] retorno = null;

		int anoMes = filtrarRelatorioOrcamentoSINPHelper.getAnoMesReferencia();

		Integer localidade = filtrarRelatorioOrcamentoSINPHelper.getLocalidade();
		Integer unidade = filtrarRelatorioOrcamentoSINPHelper.getUnidadeNegocio();
		Integer gerencia = filtrarRelatorioOrcamentoSINPHelper.getGerenciaRegional();

		String groupBy = filtrarRelatorioOrcamentoSINPHelper.getGroupBy();
		String campoSelecionado = null;

		Session session = HibernateUtil.getSessionGerencial();

		try{
			String sql = "SELECT  "
							+ "SUM(CASE WHEN last_id <> 1 AND last_id <> 2 THEN rele_qtligacoes ELSE 0 END) AS aguaTotalLigacoesCadastradas,"
							+ "SUM(CASE WHEN lest_id <> 1 AND lest_id <> 2 THEN rele_qtligacoes ELSE 0 END) AS esgotoTotalLigacoesCadastradas,"
							+ "SUM(CASE WHEN lest_id <> 1 AND lest_id <> 2 AND lepf_id <> 2 THEN rele_qtligacoes ELSE 0 END) AS esgotoTotalLigacoesCadastradasConvencional,"
							+ "SUM(CASE WHEN last_id = 3 THEN rele_qtligacoes ELSE 0 END) AS aguaTotalLigacoesAtivas,"
							+ "SUM(CASE WHEN lest_id <> 1 AND lest_id <> 2 AND lepf_id = 2 THEN rele_qtligacoes ELSE 0 END) AS esgotoTotalLigacoesCadastradasCondominial,"
							+ "SUM(CASE WHEN last_id = 3 AND rele_ichidrometro=1 THEN rele_qtligacoes ELSE 0 END) AS aguaTotalLigacoesMedidas, "
							+ "SUM(CASE WHEN lepf_id <> 2 AND lest_id = 3 THEN rele_qtligacoes ELSE 0 END) AS esgotoTotalLigacoesAtivasConvencional,"
							+ "SUM(CASE WHEN rele_ichidrometro=1 THEN rele_qtligacoes ELSE 0 END) AS aguaTotalLigacoesComHidrometro,"
							+ "SUM(CASE WHEN lepf_id = 2 AND lest_id = 3 THEN rele_qtligacoes ELSE 0 END) AS esgotoTotalLigacoesAtivasCondominial,"
							+ "SUM(CASE WHEN last_id <> 1 AND last_id <> 2 AND catg_id = 1 THEN rele_qtligacoes ELSE 0 END) AS aguaTotalLigacoesResidencialCadastradas,"
							+ "SUM(CASE WHEN lest_id <> 1 AND lest_id <> 2 AND catg_id = 1 THEN rele_qtligacoes ELSE 0 END) AS esgotoTotalLigacoesResidencialCadastradas,"
							+ "SUM(CASE WHEN last_id = 5 THEN rele_qtligacoes ELSE 0 END) AS aguaTotalLigacoesDesligadas,"
							+ "SUM(CASE WHEN last_id <> 1 or last_id <> 2 THEN rele_qteconomias ELSE 0 END) AS aguaTotalEconomiasCadastradas,"
							+ "SUM(CASE WHEN lest_id <> 1 AND lest_id <> 2 AND lepf_id <> 2 THEN rele_qteconomias ELSE 0 END) AS esgotoTotalEconomiasCadastradasConvencional,"
							+ "SUM(CASE WHEN last_id = 3 THEN rele_qteconomias ELSE 0 END) AS aguaTotalEconomiasAtivas,"
							+ "SUM(CASE WHEN lest_id <> 1 AND lest_id <> 2 AND lepf_id = 2 THEN rele_qteconomias ELSE 0 END) AS esgotoTotalEconomiasCadastradasCondominial,"
							+ "SUM(CASE WHEN last_id = 3 AND rele_ichidrometro=1 THEN rele_qteconomias ELSE 0 END) AS aguaTotalEconomiasAtivasMedidas,"
							+ "SUM(CASE WHEN lepf_id <> 2 AND lepf_id = 3 THEN rele_qteconomias ELSE 0 END) AS esgotoTotalEconomiasAtivasConvencional,"
							+ "SUM(CASE WHEN last_id <> 1 AND last_id <> 2 AND catg_id=1 THEN rele_qteconomias ELSE 0 END) AS aguaTotalEconomiasResidencialCadastradas,"
							+ "SUM(CASE WHEN lepf_id = 2 AND lest_id = 3 THEN rele_qteconomias ELSE 0 END) AS esgotoTotalEconomiasAtivasCondominial,"
							+ "SUM(CASE WHEN last_id = 3 AND rele_ichidrometro = 1 AND catg_id=1 THEN rele_qtligacoes ELSE 0 END) AS aguaTotalEconomiasResidencialAtivasMicromedidas,"
							+ "SUM(CASE WHEN lest_id <> 1 AND lest_id <> 2 AND catg_id=1 THEN rele_qteconomias ELSE 0 END) AS esgotoTotalEconomiasResidencialCadastradas,"
							+ "SUM(CASE WHEN last_id = 3 AND catg_id=1 THEN rele_qtligacoes ELSE 0 END) AS aguaTotalEconomiasResidencialAtivas,"
							+ "SUM(CASE WHEN lest_id = 3 AND catg_id=1 THEN rele_qteconomias ELSE 0 END) AS esgotoTotalEconomiasResidencialAtivas,"
							+ "SUM(CASE WHEN last_id = 3 AND catg_id=2 THEN rele_qtligacoes ELSE 0 END) AS aguaTotalEconomiasComercialAtivas,"
							+ "SUM(CASE WHEN lest_id = 3 AND catg_id=2 THEN rele_qteconomias ELSE 0 END) AS esgotoTotalEconomiasComercialAtivas,"
							+ "SUM(CASE WHEN last_id = 3 AND catg_id=3 THEN rele_qtligacoes ELSE 0 END) AS aguaTotalEconomiasIndustrialAtivas,"
							+ "SUM(CASE WHEN lest_id = 3 AND catg_id=3 THEN rele_qteconomias ELSE 0 END) AS esgotoTotalEconomiasIndustrialAtivas,"
							+ "SUM(CASE WHEN last_id = 3 AND catg_id=4 THEN rele_qtligacoes ELSE 0 END) AS aguaTotalEconomiasPublicoAtivas,"
							+ "SUM(CASE WHEN lest_id = 3 AND catg_id=4 THEN rele_qteconomias ELSE 0 END) AS esgotoTotalEconomiasPublicoAtivas,"
							+ "SUM(CASE WHEN last_id = 3 AND catg_id=1 and scat_id in (13,62,63,64,70,71,77,78)THEN rele_qtligacoes ELSE 0 END) AS aguaTotalEconomiasRuralAtivas, "
							+ "SUM(CASE WHEN last_id in (6,7,8) THEN rele_qtligacoes ELSE 0 END) AS aguaTotalLigacoesSuprimidas " + groupBy
							+ " FROM un_resumo_ligacao_economia " + "WHERE rele_amreferencia = :anoMes";

			// Localidade
			if(localidade != null){
				sql += " AND loca_id = " + localidade;
				campoSelecionado = "loca_id";
			}

			// Unidade de Negocio
			if(unidade != null){
				sql += " AND uneg_id = " + unidade;
				campoSelecionado = "uneg_id";
			}

			// Gerencia Regional
			if(gerencia != null){
				sql += " AND greg_id = " + gerencia;
				campoSelecionado = "greg_id";
			}

			if(!groupBy.equals("")){
				sql += " GROUP BY " + campoSelecionado + " ORDER BY " + campoSelecionado;
			}

			if(campoSelecionado != null){

				// Faz a pesquisa
				retorno = (Object[]) session.createSQLQuery(sql).addScalar("aguaTotalLigacoesCadastradas", Hibernate.INTEGER).addScalar(
								"esgotoTotalLigacoesCadastradas", Hibernate.INTEGER).addScalar(
								"esgotoTotalLigacoesCadastradasConvencional", Hibernate.INTEGER).addScalar("aguaTotalLigacoesAtivas",
								Hibernate.INTEGER).addScalar("esgotoTotalLigacoesCadastradasCondominial", Hibernate.INTEGER).addScalar(
								"aguaTotalLigacoesMedidas", Hibernate.INTEGER).addScalar("esgotoTotalLigacoesAtivasConvencional",
								Hibernate.INTEGER).addScalar("aguaTotalLigacoesComHidrometro", Hibernate.INTEGER).addScalar(
								"esgotoTotalLigacoesAtivasCondominial", Hibernate.INTEGER).addScalar(
								"aguaTotalLigacoesResidencialCadastradas", Hibernate.INTEGER).addScalar(
								"esgotoTotalLigacoesResidencialCadastradas", Hibernate.INTEGER).addScalar("aguaTotalLigacoesDesligadas",
								Hibernate.INTEGER).addScalar("aguaTotalEconomiasCadastradas", Hibernate.INTEGER).addScalar(
								"esgotoTotalEconomiasCadastradasConvencional", Hibernate.INTEGER).addScalar("aguaTotalEconomiasAtivas",
								Hibernate.INTEGER).addScalar("esgotoTotalEconomiasCadastradasCondominial", Hibernate.INTEGER).addScalar(
								"aguaTotalEconomiasAtivasMedidas", Hibernate.INTEGER).addScalar("esgotoTotalEconomiasAtivasConvencional",
								Hibernate.INTEGER).addScalar("aguaTotalEconomiasResidencialCadastradas", Hibernate.INTEGER).addScalar(
								"esgotoTotalEconomiasAtivasCondominial", Hibernate.INTEGER).addScalar(
								"aguaTotalEconomiasResidencialAtivasMicromedidas", Hibernate.INTEGER).addScalar(
								"esgotoTotalEconomiasResidencialCadastradas", Hibernate.INTEGER).addScalar(
								"aguaTotalEconomiasResidencialAtivas", Hibernate.INTEGER).addScalar(
								"esgotoTotalEconomiasResidencialAtivas", Hibernate.INTEGER).addScalar("aguaTotalEconomiasComercialAtivas",
								Hibernate.INTEGER).addScalar("esgotoTotalEconomiasComercialAtivas", Hibernate.INTEGER).addScalar(
								"aguaTotalEconomiasIndustrialAtivas", Hibernate.INTEGER).addScalar("esgotoTotalEconomiasIndustrialAtivas",
								Hibernate.INTEGER).addScalar("aguaTotalEconomiasPublicoAtivas", Hibernate.INTEGER).addScalar(
								"esgotoTotalEconomiasPublicoAtivas", Hibernate.INTEGER).addScalar("aguaTotalEconomiasRuralAtivas",
								Hibernate.INTEGER).addScalar("aguaTotalLigacoesSuprimidas", Hibernate.INTEGER).addScalar(campoSelecionado,
								Hibernate.INTEGER).setInteger("anoMes", anoMes).setMaxResults(1).uniqueResult();

			}else{
				// Faz a pesquisa
				retorno = (Object[]) session.createSQLQuery(sql).addScalar("aguaTotalLigacoesCadastradas", Hibernate.INTEGER).addScalar(
								"esgotoTotalLigacoesCadastradas", Hibernate.INTEGER).addScalar(
								"esgotoTotalLigacoesCadastradasConvencional", Hibernate.INTEGER).addScalar("aguaTotalLigacoesAtivas",
								Hibernate.INTEGER).addScalar("esgotoTotalLigacoesCadastradasCondominial", Hibernate.INTEGER).addScalar(
								"aguaTotalLigacoesMedidas", Hibernate.INTEGER).addScalar("esgotoTotalLigacoesAtivasConvencional",
								Hibernate.INTEGER).addScalar("aguaTotalLigacoesComHidrometro", Hibernate.INTEGER).addScalar(
								"esgotoTotalLigacoesAtivasCondominial", Hibernate.INTEGER).addScalar(
								"aguaTotalLigacoesResidencialCadastradas", Hibernate.INTEGER).addScalar(
								"esgotoTotalLigacoesResidencialCadastradas", Hibernate.INTEGER).addScalar("aguaTotalLigacoesDesligadas",
								Hibernate.INTEGER).addScalar("aguaTotalEconomiasCadastradas", Hibernate.INTEGER).addScalar(
								"esgotoTotalEconomiasCadastradasConvencional", Hibernate.INTEGER).addScalar("aguaTotalEconomiasAtivas",
								Hibernate.INTEGER).addScalar("esgotoTotalEconomiasCadastradasCondominial", Hibernate.INTEGER).addScalar(
								"aguaTotalEconomiasAtivasMedidas", Hibernate.INTEGER).addScalar("esgotoTotalEconomiasAtivasConvencional",
								Hibernate.INTEGER).addScalar("aguaTotalEconomiasResidencialCadastradas", Hibernate.INTEGER).addScalar(
								"esgotoTotalEconomiasAtivasCondominial", Hibernate.INTEGER).addScalar(
								"aguaTotalEconomiasResidencialAtivasMicromedidas", Hibernate.INTEGER).addScalar(
								"esgotoTotalEconomiasResidencialCadastradas", Hibernate.INTEGER).addScalar(
								"aguaTotalEconomiasResidencialAtivas", Hibernate.INTEGER).addScalar(
								"esgotoTotalEconomiasResidencialAtivas", Hibernate.INTEGER).addScalar("aguaTotalEconomiasComercialAtivas",
								Hibernate.INTEGER).addScalar("esgotoTotalEconomiasComercialAtivas", Hibernate.INTEGER).addScalar(
								"aguaTotalEconomiasIndustrialAtivas", Hibernate.INTEGER).addScalar("esgotoTotalEconomiasIndustrialAtivas",
								Hibernate.INTEGER).addScalar("aguaTotalEconomiasPublicoAtivas", Hibernate.INTEGER).addScalar(
								"esgotoTotalEconomiasPublicoAtivas", Hibernate.INTEGER).addScalar("aguaTotalEconomiasRuralAtivas",
								Hibernate.INTEGER).addScalar("aguaTotalLigacoesSuprimidas", Hibernate.INTEGER).setInteger("anoMes", anoMes)
								.setMaxResults(1).uniqueResult();

			}

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * Pesquisa os valores necessarios na tabela un_resumo_faturamento
	 * [UC0722] - Gerar Relatorio para Orçamento e SINP
	 * 
	 * @author Rafael Pinto
	 * @date 20/11/2006
	 * @param FiltrarRelatorioOrcamentoSINPHelper
	 * @return Object[29]
	 *         Object[0] - aguaTotalVolumeFaturadoMedido
	 *         Object[1] - esgotoTotalVolumeFaturadoResidencial
	 *         Object[2] - esgotoTotalVolumeFaturadoComercial
	 *         Object[3] - aguaTotalVolumeFaturadoEstimado
	 *         Object[4] - esgotoTotalVolumeFaturadoIndustrial
	 *         Object[5] - esgotoTotalVolumeFaturadoPublico
	 *         Object[6] - aguaTotalVolumeFaturadoResidencial
	 *         Object[7] - esgotoTotalVolumeFaturadoGeral
	 *         Object[8] - aguaTotalVolumeFaturadoComercial
	 *         Object[9] - aguaTotalVolumeFaturadoIndustrial
	 *         Object[10] - aguaTotalVolumeFaturadoPublico
	 *         Object[11] - aguaTotalVolumeFaturadoRural
	 *         Object[12] - aguaTotalVolumeFaturadoGeral
	 *         Object[13] - aguaTotalFaturadoResidencial
	 *         Object[14] - esgotoTotalFaturadoResidencial
	 *         Object[15] - aguaTotalFaturadoComercial
	 *         Object[16] - esgotoTotalFaturadoComercial
	 *         Object[17] - aguaTotalFaturadoIndustrial
	 *         Object[18] - esgotoTotalFaturadoIndustrial
	 *         Object[19] - aguaTotalFaturadoPublico
	 *         Object[20] - esgotoTotalFaturadoPublico
	 *         Object[21] - aguaTotalFaturadoDireto
	 *         Object[22] - esgotoTotalFaturadoDireto
	 *         Object[23] - aguaTotalFaturadoIndireto
	 *         Object[24] - esgotoTotalFaturadoIndireto
	 *         Object[25] - devolucao
	 *         Object[26] - campoSelecionado pode ser (loca_id,uneg_id,greg_id)
	 * @throws ErroRepositorioException
	 */
	public Object[] pesquisarRelatorioOrcamentoSINPResumoFaturamento(FiltrarRelatorioOrcamentoSINPHelper filtrarRelatorioOrcamentoSINPHelper)
					throws ErroRepositorioException{

		Object[] retorno = null;

		int anoMes = filtrarRelatorioOrcamentoSINPHelper.getAnoMesReferencia();

		Integer localidade = filtrarRelatorioOrcamentoSINPHelper.getLocalidade();
		Integer unidade = filtrarRelatorioOrcamentoSINPHelper.getUnidadeNegocio();
		Integer gerencia = filtrarRelatorioOrcamentoSINPHelper.getGerenciaRegional();

		Session session = HibernateUtil.getSessionGerencial();

		String groupBy = filtrarRelatorioOrcamentoSINPHelper.getGroupBy();
		String campoSelecionado = null;

		try{

			String sql = "SELECT "
							+ "SUM(CASE WHEN refa_ichidrometro = 1 THEN refa_vofaturadoagua ELSE 0 END) AS aguaTotalVolumeFaturadoMedido,"
							+ "SUM(CASE WHEN catg_id = 1 THEN refa_vofaturadoesgoto ELSE 0 END) AS esgotoTotalVolumeFaturadoResidencial,"
							+ "SUM(CASE WHEN catg_id = 2 THEN refa_vofaturadoesgoto ELSE 0 END) AS esgotoTotalVolumeFaturadoComercial,"
							+ "SUM(CASE WHEN refa_ichidrometro = 2 THEN refa_vofaturadoagua ELSE 0 END) AS aguaTotalVolumeFaturadoEstimado,"
							+ "SUM(CASE WHEN catg_id = 3 THEN refa_vofaturadoesgoto ELSE 0 END) AS esgotoTotalVolumeFaturadoIndustrial,"
							+ "SUM(CASE WHEN catg_id = 4 THEN refa_vofaturadoesgoto ELSE 0 END) AS esgotoTotalVolumeFaturadoPublico,"
							+ "SUM(CASE WHEN catg_id = 1 AND scat_id not in (13,62,63,64,70,71,77,78) THEN refa_vofaturadoagua ELSE 0 END) AS aguaTotalVolumeFaturadoResidencial,"
							+ "SUM(COALESCE(refa_vofaturadoesgoto,0)) AS esgotoTotalVolumeFaturadoGeral,"
							+ "SUM(CASE WHEN catg_id = 2 THEN refa_vofaturadoagua ELSE 0 END) AS aguaTotalVolumeFaturadoComercial,"
							+ "SUM(CASE WHEN catg_id = 3 THEN refa_vofaturadoagua ELSE 0 END) AS aguaTotalVolumeFaturadoIndustrial,"
							+ "SUM(CASE WHEN catg_id = 4 THEN refa_vofaturadoagua ELSE 0 END) AS aguaTotalVolumeFaturadoPublico,"
							+ "SUM(CASE WHEN catg_id = 1 AND scat_id in (13,62,63,64,70,71,77,78) THEN refa_vofaturadoagua ELSE 0 END) AS aguaTotalVolumeFaturadoRural,"
							+ "SUM(COALESCE(refa_vofaturadoagua,0)) AS aguaTotalVolumeFaturadoGeral,"
							+ "SUM(CASE WHEN catg_id = 1 THEN refa_vlfaturadoagua ELSE 0 END) AS aguaTotalFaturadoResidencial,"
							+ "SUM(CASE WHEN catg_id = 1 THEN refa_vlfaturadoesgoto ELSE 0 END) AS esgotoTotalFaturadoResidencial,"
							+ "SUM(CASE WHEN catg_id = 2 THEN refa_vlfaturadoagua ELSE 0 END) AS aguaTotalFaturadoComercial,"
							+ "SUM(CASE WHEN catg_id = 2 THEN refa_vlfaturadoesgoto ELSE 0 END) AS esgotoTotalFaturadoComercial,"
							+ "SUM(CASE WHEN catg_id = 3 THEN refa_vlfaturadoagua ELSE 0 END) AS aguaTotalFaturadoIndustrial,"
							+ "SUM(CASE WHEN catg_id = 3 THEN refa_vlfaturadoesgoto ELSE 0 END) AS esgotoTotalFaturadoIndustrial,"
							+ "SUM(CASE WHEN catg_id = 4 THEN refa_vlfaturadoagua ELSE 0 END) AS aguaTotalFaturadoPublico,"
							+ "SUM(CASE WHEN catg_id = 4 THEN refa_vlfaturadoesgoto ELSE 0 END) AS esgotoTotalFaturadoPublico,"
							+ "SUM(COALESCE(refa_vlfaturadoagua,0)) AS aguaTotalFaturadoDireto,"
							+ "SUM(COALESCE(refa_vlfaturadoesgoto,0)) AS esgotoTotalFaturadoDireto,"
							+ "SUM(CASE WHEN fntp_id = 1 AND lict_id in (1,2,3,4,5,6,10,12) THEN refa_vldocumentosfaturadosoutros ELSE 0 END) AS aguaTotalFaturadoIndireto ,"
							+ "SUM(CASE WHEN fntp_id = 1 AND lict_id in (7,8,9,11) THEN refa_vldocumentosfaturadosoutros ELSE 0 END) AS esgotoTotalFaturadoIndireto,"
							+ "SUM(COALESCE(refa_vldocumentosfaturadoscredito,0)) AS devolucao " + groupBy + " FROM un_resumo_faturamento "
							+ "WHERE refa_amreferencia = :anoMes";

			// Localidade
			if(localidade != null){
				sql += " AND loca_id = " + localidade;
				campoSelecionado = "loca_id";
			}

			// Unidade de Negocio
			if(unidade != null){
				sql += " AND uneg_id = " + unidade;
				campoSelecionado = "uneg_id";
			}

			// Gerencia Regional
			if(gerencia != null){
				sql += " AND greg_id = " + gerencia;
				campoSelecionado = "greg_id";
			}

			if(!groupBy.equals("")){
				sql += " GROUP BY " + campoSelecionado + " ORDER BY " + campoSelecionado;
			}

			if(campoSelecionado != null){

				// Faz a pesquisa
				retorno = (Object[]) session.createSQLQuery(sql).addScalar("aguaTotalVolumeFaturadoMedido", Hibernate.INTEGER).addScalar(
								"esgotoTotalVolumeFaturadoResidencial", Hibernate.INTEGER).addScalar("esgotoTotalVolumeFaturadoComercial",
								Hibernate.INTEGER).addScalar("aguaTotalVolumeFaturadoEstimado", Hibernate.INTEGER).addScalar(
								"esgotoTotalVolumeFaturadoIndustrial", Hibernate.INTEGER).addScalar("esgotoTotalVolumeFaturadoPublico",
								Hibernate.INTEGER).addScalar("aguaTotalVolumeFaturadoResidencial", Hibernate.INTEGER).addScalar(
								"esgotoTotalVolumeFaturadoGeral", Hibernate.INTEGER).addScalar("aguaTotalVolumeFaturadoComercial",
								Hibernate.INTEGER).addScalar("aguaTotalVolumeFaturadoIndustrial", Hibernate.INTEGER).addScalar(
								"aguaTotalVolumeFaturadoPublico", Hibernate.INTEGER).addScalar("aguaTotalVolumeFaturadoRural",
								Hibernate.INTEGER).addScalar("aguaTotalVolumeFaturadoGeral", Hibernate.INTEGER).addScalar(
								"aguaTotalFaturadoResidencial", Hibernate.BIG_DECIMAL).addScalar("esgotoTotalFaturadoResidencial",
								Hibernate.BIG_DECIMAL).addScalar("aguaTotalFaturadoComercial", Hibernate.BIG_DECIMAL).addScalar(
								"esgotoTotalFaturadoComercial", Hibernate.BIG_DECIMAL).addScalar("aguaTotalFaturadoIndustrial",
								Hibernate.BIG_DECIMAL).addScalar("esgotoTotalFaturadoIndustrial", Hibernate.BIG_DECIMAL).addScalar(
								"aguaTotalFaturadoPublico", Hibernate.BIG_DECIMAL).addScalar("esgotoTotalFaturadoPublico",
								Hibernate.BIG_DECIMAL).addScalar("aguaTotalFaturadoDireto", Hibernate.BIG_DECIMAL).addScalar(
								"esgotoTotalFaturadoDireto", Hibernate.BIG_DECIMAL).addScalar("aguaTotalFaturadoIndireto",
								Hibernate.BIG_DECIMAL).addScalar("esgotoTotalFaturadoIndireto", Hibernate.BIG_DECIMAL).addScalar(
								"devolucao", Hibernate.BIG_DECIMAL).addScalar(campoSelecionado, Hibernate.INTEGER).setInteger("anoMes",
								anoMes).setMaxResults(1).uniqueResult();

			}else{

				// Faz a pesquisa
				retorno = (Object[]) session.createSQLQuery(sql).addScalar("aguaTotalVolumeFaturadoMedido", Hibernate.INTEGER).addScalar(
								"esgotoTotalVolumeFaturadoResidencial", Hibernate.INTEGER).addScalar("esgotoTotalVolumeFaturadoComercial",
								Hibernate.INTEGER).addScalar("aguaTotalVolumeFaturadoEstimado", Hibernate.INTEGER).addScalar(
								"esgotoTotalVolumeFaturadoIndustrial", Hibernate.INTEGER).addScalar("esgotoTotalVolumeFaturadoPublico",
								Hibernate.INTEGER).addScalar("aguaTotalVolumeFaturadoResidencial", Hibernate.INTEGER).addScalar(
								"esgotoTotalVolumeFaturadoGeral", Hibernate.INTEGER).addScalar("aguaTotalVolumeFaturadoComercial",
								Hibernate.INTEGER).addScalar("aguaTotalVolumeFaturadoIndustrial", Hibernate.INTEGER).addScalar(
								"aguaTotalVolumeFaturadoPublico", Hibernate.INTEGER).addScalar("aguaTotalVolumeFaturadoRural",
								Hibernate.INTEGER).addScalar("aguaTotalVolumeFaturadoGeral", Hibernate.INTEGER).addScalar(
								"aguaTotalFaturadoResidencial", Hibernate.BIG_DECIMAL).addScalar("esgotoTotalFaturadoResidencial",
								Hibernate.BIG_DECIMAL).addScalar("aguaTotalFaturadoComercial", Hibernate.BIG_DECIMAL).addScalar(
								"esgotoTotalFaturadoComercial", Hibernate.BIG_DECIMAL).addScalar("aguaTotalFaturadoIndustrial",
								Hibernate.BIG_DECIMAL).addScalar("esgotoTotalFaturadoIndustrial", Hibernate.BIG_DECIMAL).addScalar(
								"aguaTotalFaturadoPublico", Hibernate.BIG_DECIMAL).addScalar("esgotoTotalFaturadoPublico",
								Hibernate.BIG_DECIMAL).addScalar("aguaTotalFaturadoDireto", Hibernate.BIG_DECIMAL).addScalar(
								"esgotoTotalFaturadoDireto", Hibernate.BIG_DECIMAL).addScalar("aguaTotalFaturadoIndireto",
								Hibernate.BIG_DECIMAL).addScalar("esgotoTotalFaturadoIndireto", Hibernate.BIG_DECIMAL).addScalar(
								"devolucao", Hibernate.BIG_DECIMAL).setInteger("anoMes", anoMes).setMaxResults(1).uniqueResult();

			}

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * Pesquisa o total das ligaçoes tabela un_resumo_ligacao_economia
	 * [UC0722] - Gerar Relatorio para Orçamento e SINP
	 * 
	 * @author Rafael Pinto
	 * @date 20/11/2006
	 * @param FiltrarRelatorioOrcamentoSINPHelper
	 * @return Object[2]
	 *         Object[0] - quantidadeLigacoesAgua
	 *         Object[1] - quantidadeLigacoesEsgoto
	 * @throws ErroRepositorioException
	 */
	public Object[] pesquisarRelatorioOrcamentoSINPTotalLigacoesResumoLigacaoEconomia(
					FiltrarRelatorioOrcamentoSINPHelper filtrarRelatorioOrcamentoSINPHelper) throws ErroRepositorioException{

		Object[] retorno = null;

		int anoMesAnterior = filtrarRelatorioOrcamentoSINPHelper.getAnoMesReferenciaAnterior();

		Integer localidade = filtrarRelatorioOrcamentoSINPHelper.getLocalidade();
		Integer unidade = filtrarRelatorioOrcamentoSINPHelper.getUnidadeNegocio();
		Integer gerencia = filtrarRelatorioOrcamentoSINPHelper.getGerenciaRegional();

		Session session = HibernateUtil.getSessionGerencial();

		try{

			String sql = "SELECT "
							+ "SUM(CASE WHEN last_id <> 1 AND last_id <> 2 THEN rele_qtligacoes ELSE 0 END) AS quantidadeLigacoesAgua, "
							+ "SUM(CASE WHEN lest_id <> 1 AND lest_id <> 2 THEN rele_qtligacoes ELSE 0 END) AS quantidadeLigacoesEsgoto "
							+ "FROM un_resumo_ligacao_economia " + "WHERE rele_amreferencia = :anoMesAnterior";

			// Localidade
			if(localidade != null){
				sql += " AND loca_id = " + localidade;
			}

			// Unidade de Negocio
			if(unidade != null){
				sql += " AND uneg_id = " + unidade;
			}

			// Gerencia Regional
			if(gerencia != null){
				sql += " AND greg_id = " + gerencia;
			}

			// Faz a pesquisa
			retorno = (Object[]) session.createSQLQuery(sql).addScalar("quantidadeLigacoesAgua", Hibernate.INTEGER).addScalar(
							"quantidadeLigacoesEsgoto", Hibernate.INTEGER).setInteger("anoMesAnterior", anoMesAnterior).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * Pesquisa o total dos consumos tabela un_resumo_consumo_agua
	 * [UC0722] - Gerar Relatorio para Orçamento e SINP
	 * 
	 * @author Rafael Pinto
	 * @date 20/11/2006
	 * @param FiltrarRelatorioOrcamentoSINPHelper
	 * @return Object[2]
	 *         Object[0] - volumeFaturadoMicroMedido
	 *         Object[1] - volumeFaturadoEconomiasResidenciasAtivasMicroMedido
	 * @throws ErroRepositorioException
	 */
	public Object[] pesquisarRelatorioOrcamentoSINPTotalComumoResumoConsumoAgua(
					FiltrarRelatorioOrcamentoSINPHelper filtrarRelatorioOrcamentoSINPHelper) throws ErroRepositorioException{

		Object[] retorno = null;

		int anoMes = filtrarRelatorioOrcamentoSINPHelper.getAnoMesReferencia();

		Integer localidade = filtrarRelatorioOrcamentoSINPHelper.getLocalidade();
		Integer unidade = filtrarRelatorioOrcamentoSINPHelper.getUnidadeNegocio();
		Integer gerencia = filtrarRelatorioOrcamentoSINPHelper.getGerenciaRegional();

		Session session = HibernateUtil.getSessionGerencial();

		try{

			String sql = "SELECT "
							+ "SUM(COALESCE(reca_consumoagua,0)) AS volumeFaturadoMicroMedido, "
							+ "SUM(CASE WHEN catg_id = 1 THEN reca_consumoagua ELSE 0 END) AS volumeFaturadoEconomiasResidenciasAtivasMicroMedido "
							+ "FROM un_resumo_consumo_agua " + "WHERE reca_amreferencia = :anoMes " + "AND cstp_id NOT IN(5,7)";

			// Localidade
			if(localidade != null){
				sql += " AND loca_id = " + localidade;
			}

			// Unidade de Negocio
			if(unidade != null){
				sql += " AND uneg_id = " + unidade;
			}

			// Gerencia Regional
			if(gerencia != null){
				sql += " AND greg_id = " + gerencia;
			}

			// Faz a pesquisa
			retorno = (Object[]) session.createSQLQuery(sql).addScalar("volumeFaturadoMicroMedido", Hibernate.INTEGER).addScalar(
							"volumeFaturadoEconomiasResidenciasAtivasMicroMedido", Hibernate.INTEGER).setInteger("anoMes", anoMes)
							.uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * Pesquisa o total dos consumos tabela un_resumo_arrecadacao
	 * [UC0722] - Gerar Relatorio para Orçamento e SINP
	 * 
	 * @author Rafael Pinto
	 * @date 20/11/2006
	 * @param FiltrarRelatorioOrcamentoSINPHelper
	 * @return BigDecimal
	 * @throws ErroRepositorioException
	 */
	public BigDecimal pesquisarRelatorioOrcamentoSINPTotalArrecadacaoResumoArrecadacao(
					FiltrarRelatorioOrcamentoSINPHelper filtrarRelatorioOrcamentoSINPHelper, boolean ehAnterior)
					throws ErroRepositorioException{

		BigDecimal retorno = null;

		Integer localidade = filtrarRelatorioOrcamentoSINPHelper.getLocalidade();
		Integer unidade = filtrarRelatorioOrcamentoSINPHelper.getUnidadeNegocio();
		Integer gerencia = filtrarRelatorioOrcamentoSINPHelper.getGerenciaRegional();

		Session session = HibernateUtil.getSessionGerencial();

		int anoMes = filtrarRelatorioOrcamentoSINPHelper.getAnoMesReferencia();

		String and = "AND rear_amreferenciadocumento >= :anoMes ";

		if(ehAnterior){
			and = "AND rear_amreferenciadocumento < :anoMes ";
		}

		try{

			String sql = "SELECT " + "SUM( " + "COALESCE(rear_vlagua,0) +" + "COALESCE(rear_vlesgoto,0) +"
							+ "COALESCE(rear_vlnaoidentificado,0) +" + "COALESCE(rear_vldocumentosrecebidosoutros,0) - "
							+ "COALESCE(rear_vldocumentosrecebidoscredito,0) ) AS totalArrecadacao " + "FROM un_resumo_arrecadacao "
							+ "WHERE rear_amreferencia = :anoMes " + and;

			// Localidade
			if(localidade != null){
				sql += " AND loca_id = " + localidade;
			}

			// Unidade de Negocio
			if(unidade != null){
				sql += " AND uneg_id = " + unidade;
			}

			// Gerencia Regional
			if(gerencia != null){
				sql += " AND greg_id = " + gerencia;
			}

			// Faz a pesquisa
			retorno = (BigDecimal) session.createSQLQuery(sql).addScalar("totalArrecadacao", Hibernate.BIG_DECIMAL).setInteger("anoMes",
							anoMes).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * Pesquisa o total de contas a receber consumos tabela un_resumo_pendencia
	 * [UC0722] - Gerar Relatorio para Orçamento e SINP
	 * 
	 * @author Rafael Pinto
	 * @date 30/11/2006
	 * @param FiltrarRelatorioOrcamentoSINPHelper
	 * @return BigDecimal
	 * @throws ErroRepositorioException
	 */
	public BigDecimal pesquisarRelatorioOrcamentoSINPTotalContasResumoPendencia(
					FiltrarRelatorioOrcamentoSINPHelper filtrarRelatorioOrcamentoSINPHelper) throws ErroRepositorioException{

		BigDecimal retorno = null;

		Integer localidade = filtrarRelatorioOrcamentoSINPHelper.getLocalidade();
		Integer unidade = filtrarRelatorioOrcamentoSINPHelper.getUnidadeNegocio();
		Integer gerencia = filtrarRelatorioOrcamentoSINPHelper.getGerenciaRegional();

		int anoMes = filtrarRelatorioOrcamentoSINPHelper.getAnoMesReferencia();

		Session session = HibernateUtil.getSessionGerencial();

		try{

			String sql = "SELECT " + "SUM(  " + "(COALESCE(rpen_vlpendente_agua,0) + " + "COALESCE(rpen_vlpendente_esgoto,0) + "
							+ "COALESCE(rpen_vlpendente_debitos,0) ) - " + "(COALESCE(rpen_vlpendente_creditos,0) + "
							+ "COALESCE(rpen_vlpendente_impostos,0) )  " + ") AS total " + "FROM un_resumo_pendencia "
							+ "WHERE rpen_amreferencia = :anoMes " + "AND rpen_icvencido = 1 ";

			// Localidade
			if(localidade != null){
				sql += " AND loca_id = " + localidade;
			}

			// Unidade de Negocio
			if(unidade != null){
				sql += " AND uneg_id = " + unidade;
			}

			// Gerencia Regional
			if(gerencia != null){
				sql += " AND greg_id = " + gerencia;
			}

			// Faz a pesquisa
			retorno = (BigDecimal) session.createSQLQuery(sql).addScalar("total", Hibernate.BIG_DECIMAL).setInteger("anoMes", anoMes)
							.uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * Pesquisa todas as localidades da tabela g_localidade
	 * [UC0722] - Gerar Relatorio para Orçamento e SINP
	 * 
	 * @author Rafael Pinto
	 * @date 21/11/2006
	 * @return Collection<Integer>
	 * @throws ErroRepositorioException
	 */
	public Collection<Integer> pesquisarLocalidades() throws ErroRepositorioException{

		Collection<Integer> retorno = null;

		Session session = HibernateUtil.getSessionGerencial();

		try{

			String consulta = "SELECT  localidade.id " + "FROM GLocalidade localidade";

			retorno = session.createQuery(consulta).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * Pesquisa todas as unidades da tabela g_unidade_negocio
	 * [UC0722] - Gerar Relatorio para Orçamento e SINP
	 * 
	 * @author Rafael Pinto
	 * @date 21/11/2006
	 * @return Collection<Integer>
	 * @throws ErroRepositorioException
	 */
	public Collection<Integer> pesquisarUnidadesNegocios() throws ErroRepositorioException{

		Collection<Integer> retorno = null;

		Session session = HibernateUtil.getSessionGerencial();

		try{

			String consulta = "SELECT unidade.id " + "FROM GUnidadeNegocio unidade ";

			retorno = session.createQuery(consulta).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * Pesquisa todas as unidades da tabela g_gerencia_regional
	 * [UC0722] - Gerar Relatorio para Orçamento e SINP
	 * 
	 * @author Rafael Pinto
	 * @date 21/11/2006
	 * @return Collection<Integer>
	 * @throws ErroRepositorioException
	 */
	public Collection<Integer> pesquisarGerenciasRegionais() throws ErroRepositorioException{

		Collection<Integer> retorno = null;

		Session session = HibernateUtil.getSessionGerencial();

		try{

			String consulta = "SELECT gerencia.id " + "FROM GGerenciaRegional gerencia ";

			retorno = session.createQuery(consulta).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0733] Gerar Quadro de metas Acumulado
	 * 
	 * @author Bruno Barros
	 * @param filtrarRelatorioQuadroMetasAcumuladoHelper
	 * @return
	 */
	public List pesquisarRelatorioQuadroMetasAcumulado(FiltrarRelatorioQuadroMetasAcumuladoHelper filtrarRelatorioQuadroMetasAcumuladoHelper)
					throws ErroRepositorioException{

		List retorno = null;

		Session session = HibernateUtil.getSessionGerencial();

		Integer qtdGrupos = 0;

		String select = "";
		String where = "";
		String groupby = "group by \n";
		String orderby = "order by \n";

		// opções de totalização
		if(filtrarRelatorioQuadroMetasAcumuladoHelper.getOpcaoTotalizacao() == TOTALIZACAO_GERENCIA_REGIONAL
						|| filtrarRelatorioQuadroMetasAcumuladoHelper.getOpcaoTotalizacao() == TOTALIZACAO_UNIDADE_NEGOCIO
						|| filtrarRelatorioQuadroMetasAcumuladoHelper.getOpcaoTotalizacao() == TOTALIZACAO_LOCALIDADE
						|| filtrarRelatorioQuadroMetasAcumuladoHelper.getOpcaoTotalizacao() == TOTALIZACAO_ESTADO_GERENCIA_REGIONAL
						|| filtrarRelatorioQuadroMetasAcumuladoHelper.getOpcaoTotalizacao() == TOTALIZACAO_ESTADO_UNIDADE_NEGOCIO
						|| filtrarRelatorioQuadroMetasAcumuladoHelper.getOpcaoTotalizacao() == TOTALIZACAO_ESTADO_LOCALIDADE){
			select += "   ,r.gerGerenciaRegional.id \n";
			groupby += "   r.gerGerenciaRegional.id \n";
			orderby += "   r.gerGerenciaRegional.id \n";

			qtdGrupos++;
		}

		if(filtrarRelatorioQuadroMetasAcumuladoHelper.getOpcaoTotalizacao() == TOTALIZACAO_UNIDADE_NEGOCIO
						|| filtrarRelatorioQuadroMetasAcumuladoHelper.getOpcaoTotalizacao() == TOTALIZACAO_LOCALIDADE
						|| filtrarRelatorioQuadroMetasAcumuladoHelper.getOpcaoTotalizacao() == TOTALIZACAO_ESTADO_UNIDADE_NEGOCIO
						|| filtrarRelatorioQuadroMetasAcumuladoHelper.getOpcaoTotalizacao() == TOTALIZACAO_ESTADO_LOCALIDADE){
			select += "   ,r.gerUnidadeNegocio.id \n";
			groupby += "   " + (qtdGrupos > 0 ? "," : "") + "r.gerUnidadeNegocio.id \n";
			orderby += "   " + (qtdGrupos > 0 ? "," : "") + "r.gerUnidadeNegocio.id \n";

			qtdGrupos++;
		}

		if(filtrarRelatorioQuadroMetasAcumuladoHelper.getOpcaoTotalizacao() == TOTALIZACAO_LOCALIDADE
						|| filtrarRelatorioQuadroMetasAcumuladoHelper.getOpcaoTotalizacao() == TOTALIZACAO_ESTADO_LOCALIDADE){
			select += "   ,r.gerLocalidade.id, r.gerLocalidade.codigoCentroCusto \n";
			groupby += "   " + (qtdGrupos > 0 ? "," : "") + "r.gerLocalidade.id, r.gerLocalidade.codigoCentroCusto \n";
			orderby += "   " + (qtdGrupos > 0 ? "," : "") + "r.gerLocalidade.id \n, r.gerLocalidade.codigoCentroCusto";
			qtdGrupos++;
		}

		if(qtdGrupos == 0){
			select = "";
			groupby = "";
			orderby = "";
		}

		// Montamos a clausula where
		if(filtrarRelatorioQuadroMetasAcumuladoHelper.getGerenciaRegional() != null
						&& !filtrarRelatorioQuadroMetasAcumuladoHelper.getGerenciaRegional().equals(0)){
			where += "   and r.gerGerenciaRegional.id = " + filtrarRelatorioQuadroMetasAcumuladoHelper.getGerenciaRegional();
		}

		if(filtrarRelatorioQuadroMetasAcumuladoHelper.getUnidadeNegocio() != null
						&& !filtrarRelatorioQuadroMetasAcumuladoHelper.getUnidadeNegocio().equals(0)){
			where += "   and r.gerUnidadeNegocio.id = " + filtrarRelatorioQuadroMetasAcumuladoHelper.getUnidadeNegocio();
		}

		if(filtrarRelatorioQuadroMetasAcumuladoHelper.getLocalidade() != null
						&& !filtrarRelatorioQuadroMetasAcumuladoHelper.getLocalidade().equals(0)){
			where += "   and r.gerLocalidade.id = " + filtrarRelatorioQuadroMetasAcumuladoHelper.getLocalidade();
		}

		try{

			String consulta = " select \n"
							+
							// INDICE 0 - Ligacoes Cadastradas, Grupo Subcategoria 101
							"   sum(  \n"
							+ "     case when ( r.codigoGrupoSubcategoria = 101 ) then \n"
							+ "       r.quantidadeLigacoesCadastradas \n"
							+ "     else 0 end ), \n"
							+
							// INDICE 1 - Ligacoes Cadastradas, Grupo Subcategoria 102
							"   sum( \n"
							+ "     case when ( r.codigoGrupoSubcategoria = 102 ) then \n"
							+ "       r.quantidadeLigacoesCadastradas \n"
							+ "     else 0 end ), \n"
							+
							// INDICE 2 - Ligacoes Cadastradas, Grupo Subcategoria 103
							"   sum( \n"
							+ "    case when ( r.codigoGrupoSubcategoria = 103 ) then \n"
							+ "    r.quantidadeLigacoesCadastradas \n"
							+ "     else 0 end ), \n"
							+
							// INDICE 3 - Ligacoes Cadastradas, Grupo Subcategoria 200
							"   sum( \n"
							+ "     case when ( r.codigoGrupoSubcategoria = 200 ) then \n"
							+ "       r.quantidadeLigacoesCadastradas \n"
							+ "     else 0 end ), \n"
							+
							// INDICE 4 - Ligacoes Cadastradas, Grupo Subcategoria 300
							"   sum(  \n"
							+ "     case when ( r.codigoGrupoSubcategoria = 300 ) then \n"
							+ "       r.quantidadeLigacoesCadastradas \n"
							+ "     else 0 end ), \n"
							+
							// INDICE 5 - Ligacoes Cadastradas, Grupo Subcategoria 400
							"   sum( \n"
							+ "     case when ( r.codigoGrupoSubcategoria = 400 ) then \n"
							+ "       r.quantidadeLigacoesCadastradas \n"
							+ "     else 0 end ), \n"
							+
							// INDICE 6 - Ligacoes Cadastradas, Grupo Subcategoria 116
							"   sum(  \n"
							+ "     case when ( r.codigoGrupoSubcategoria = 116 ) then \n"
							+ "       r.quantidadeLigacoesCadastradas \n"
							+ "     else 0 end ), \n"
							+
							// INDICE 7 - Ligacoes Cadastradas, Grupo Subcategoria 115
							"   sum(  \n"
							+ "     case when ( r.codigoGrupoSubcategoria = 115 ) then \n"
							+ "       r.quantidadeLigacoesCadastradas \n"
							+ "     else 0 end ), \n"
							+
							// INDICE 8 - Ligacoes Cadastradas
							"   sum( r.quantidadeLigacoesCadastradas ), \n"
							+
							// INDICE 9 - Ligacoes Cortadas, Grupo Subcategoria 101
							"   sum(  \n"
							+ "     case when ( r.codigoGrupoSubcategoria = 101 ) then \n"
							+ "       r.quantidadeLigacoesCortadas \n"
							+ "     else 0 end ), \n"
							+
							// INDICE 10 - Ligacoes Cortadas, Grupo Subcategoria 102
							"   sum( \n"
							+ "     case when ( r.codigoGrupoSubcategoria = 102 ) then \n"
							+ "       r.quantidadeLigacoesCortadas   \n"
							+ "     else 0 end ), \n"
							+
							// INDICE 11 - Ligacoes Cortadas, Grupo Subcategoria 103
							"   sum(  \n"
							+ "     case when ( r.codigoGrupoSubcategoria = 103 ) then \n"
							+ "       r.quantidadeLigacoesCortadas \n"
							+ "     else 0 end ), \n"
							+
							// INDICE 12 - Ligacoes Cortadas, Grupo Subcategoria 200
							"   sum(  \n"
							+ "     case when ( r.codigoGrupoSubcategoria = 200 ) then \n"
							+ "       r.quantidadeLigacoesCortadas \n"
							+ "     else 0 end ), \n"
							+
							// INDICE 13 - Ligacoes Cortadas, Grupo Subcategoria 300
							"   sum(  \n"
							+ "     case when ( r.codigoGrupoSubcategoria = 300 ) then \n"
							+ "       r.quantidadeLigacoesCortadas \n"
							+ "     else 0 end ), \n"
							+
							// INDICE 14 - Ligacoes Cortadas, Grupo Subcategoria 400
							"   sum( \n"
							+ "     case when ( r.codigoGrupoSubcategoria = 400 ) then \n"
							+ "       r.quantidadeLigacoesCortadas \n"
							+ "     else 0 end ), \n"
							+
							// INDICE 15 - Ligacoes Cortadas, Grupo Subcategoria 116
							"   sum( \n"
							+ "     case when ( r.codigoGrupoSubcategoria = 116 ) then \n"
							+ "       r.quantidadeLigacoesCortadas \n"
							+ "     else 0 end ), \n"
							+
							// INDICE 16 - Ligacoes Cortadas, Grupo Subcategoria 115
							"   sum(  \n"
							+ "     case when ( r.codigoGrupoSubcategoria = 115 ) then \n"
							+ "       r.quantidadeLigacoesCortadas   \n"
							+ "     else 0 end ), \n"
							+
							// INDICE 17 - Ligacoes Cortadas
							"   sum( r.quantidadeLigacoesCortadas ), \n"
							+
							// INDICE 18 - Ligacoes Suprimidas, Grupo Subcategoria 101
							"   sum( \n"
							+ "     case when ( r.codigoGrupoSubcategoria = 101 ) then \n"
							+ "       r.quantidadeLigacoesSuprimidas \n"
							+ "     else 0 end ), \n"
							+
							// INDICE 19 - Ligacoes Suprimidas, Grupo Subcategoria 102
							"   sum(  \n"
							+ "     case when ( r.codigoGrupoSubcategoria = 102 ) then \n"
							+ "       r.quantidadeLigacoesSuprimidas \n"
							+ "     else 0 end ), \n"
							+
							// INDICE 20 - Ligacoes Suprimidas, Grupo Subcategoria 103
							"   sum( \n"
							+ "     case when ( r.codigoGrupoSubcategoria = 103 ) then \n"
							+ "       r.quantidadeLigacoesSuprimidas \n"
							+ "     else 0 end ), \n"
							+
							// INDICE 21 - Ligacoes Suprimidas, Grupo Subcategoria 200
							"   sum( \n"
							+ "     case when ( r.codigoGrupoSubcategoria = 200 ) then \n"
							+ "       r.quantidadeLigacoesSuprimidas \n"
							+ "     else 0 end ), \n"
							+
							// INDICE 22 - Ligacoes Suprimidas, Grupo Subcategoria 300
							"   sum( \n"
							+ "     case when ( r.codigoGrupoSubcategoria = 300 ) then \n"
							+ "       r.quantidadeLigacoesSuprimidas \n"
							+ "     else 0 end ), \n"
							+
							// INDICE 23 - Ligacoes Suprimidas, Grupo Subcategoria 400
							"   sum(  \n"
							+ "     case when ( r.codigoGrupoSubcategoria = 400 ) then \n"
							+ "       r.quantidadeLigacoesSuprimidas \n"
							+ "     else 0 end ), \n"
							+
							// INDICE 24 - Ligacoes Suprimidas, Grupo Subcategoria 116
							"   sum( \n"
							+ "     case when ( r.codigoGrupoSubcategoria = 116 ) then \n"
							+ "       r.quantidadeLigacoesSuprimidas \n"
							+ "     else 0 end ), \n"
							+
							// INDICE 25 - Ligacoes Suprimidas, Grupo Subcategoria 115
							"   sum(  \n"
							+ "     case when ( r.codigoGrupoSubcategoria = 115 ) then \n"
							+ "       r.quantidadeLigacoesSuprimidas \n"
							+ "     else 0 end ), \n"
							+
							// INDICE 26 - Ligacoes Suprimidas
							"   sum( r.quantidadeLigacoesSuprimidas ), \n"
							+
							// INDICE 27 - Ligacoes Ativas, Grupo Subcategoria 101
							"   sum(  \n"
							+ "     case when ( r.codigoGrupoSubcategoria = 101 ) then \n"
							+ "       r.quantidadeLigacoesAtivas   \n"
							+ "     else 0 end ), \n"
							+
							// INDICE 28 - Ligacoes Ativas, Grupo Subcategoria 102
							"   sum( \n"
							+ "     case when ( r.codigoGrupoSubcategoria = 102 ) then \n"
							+ "       r.quantidadeLigacoesAtivas \n"
							+ "     else 0 end ), \n"
							+
							// INDICE 29 - Ligacoes Ativas, Grupo Subcategoria 103
							"   sum(  \n"
							+ "     case when ( r.codigoGrupoSubcategoria = 103 ) then \n"
							+ "       r.quantidadeLigacoesAtivas \n"
							+ "     else 0 end ), \n"
							+
							// INDICE 30 - Ligacoes Ativas, Grupo Subcategoria 200
							"   sum( \n"
							+ "     case when ( r.codigoGrupoSubcategoria = 200 ) then \n"
							+ "       r.quantidadeLigacoesAtivas \n"
							+ "     else 0 end ), \n"
							+
							// INDICE 31 - Ligacoes Ativas, Grupo Subcategoria 300
							"   sum(  \n"
							+ "     case when ( r.codigoGrupoSubcategoria = 300 ) then \n"
							+ "       r.quantidadeLigacoesAtivas \n"
							+ "     else 0 end ), \n"
							+
							// INDICE 32 - Ligacoes Ativas, Grupo Subcategoria 400
							"   sum(  \n"
							+ "     case when ( r.codigoGrupoSubcategoria = 400 ) then \n"
							+ "       r.quantidadeLigacoesAtivas \n"
							+ "     else 0 end ), \n"
							+
							// INDICE 33 - Ligacoes Ativas, Grupo Subcategoria 116
							"   sum( \n"
							+ "     case when ( r.codigoGrupoSubcategoria = 116 ) then \n"
							+ "       r.quantidadeLigacoesAtivas \n"
							+ "     else 0 end ), \n"
							+
							// INDICE 34 - Ligacoes Ativas, Grupo Subcategoria 115
							"   sum( \n"
							+ "     case when ( r.codigoGrupoSubcategoria = 115 ) then \n"
							+ "       r.quantidadeLigacoesAtivas \n"
							+ "     else 0 end ), \n"
							+
							// INDICE 35 - Ligacoes Ativas
							"   sum( r.quantidadeLigacoesAtivas ), \n"
							+
							// INDICE 36 - Ligacoes Ativas debito 3m, Grupo Subcategoria 101
							"   sum(  \n"
							+ "     case when ( r.codigoGrupoSubcategoria = 101 ) then \n"
							+ "       r.quantidadeLigacoesAtivasDebito3m \n"
							+ "     else 0 end ), \n"
							+
							// INDICE 37 - Ligacoes Ativas debito 3m, Grupo Subcategoria 102
							"   sum( \n"
							+ "     case when ( r.codigoGrupoSubcategoria = 102 ) then \n"
							+ "       r.quantidadeLigacoesAtivasDebito3m \n"
							+ "     else 0 end ), \n"
							+
							// INDICE 38 - Ligacoes Ativas debito 3m, Grupo Subcategoria 103
							"   sum( \n"
							+ "     case when ( r.codigoGrupoSubcategoria = 103 ) then \n"
							+ "       r.quantidadeLigacoesAtivasDebito3m \n"
							+ "     else 0 end ), \n"
							+
							// INDICE 39 - Ligacoes Ativas debito 3m, Grupo Subcategoria 200
							"   sum(  \n"
							+ "     case when ( r.codigoGrupoSubcategoria = 200 ) then \n"
							+ "       r.quantidadeLigacoesAtivasDebito3m \n"
							+ "     else 0 end ), \n"
							+
							// INDICE 40 - Ligacoes Ativas debito 3m, Grupo Subcategoria 300
							"   sum(  \n"
							+ "     case when ( r.codigoGrupoSubcategoria = 300 ) then \n"
							+ "       r.quantidadeLigacoesAtivasDebito3m \n"
							+ "     else 0 end ), \n"
							+
							// INDICE 41 - Ligacoes Ativas debito 3m, Grupo Subcategoria 400
							"   sum(  \n"
							+ "     case when ( r.codigoGrupoSubcategoria = 400 ) then \n"
							+ "       r.quantidadeLigacoesAtivasDebito3m   \n"
							+ "     else 0 end ), \n"
							+
							// INDICE 42 - Ligacoes Ativas debito 3m, Grupo Subcategoria 116
							"   sum( \n"
							+ "     case when ( r.codigoGrupoSubcategoria = 116 ) then \n"
							+ "       r.quantidadeLigacoesAtivasDebito3m \n"
							+ "     else 0 end ), \n"
							+
							// INDICE 43 - Ligacoes Ativas debito 3m, Grupo Subcategoria 115
							"   sum(  \n"
							+ "     case when ( r.codigoGrupoSubcategoria = 115 ) then \n"
							+ "       r.quantidadeLigacoesAtivasDebito3m \n"
							+ "     else 0 end ), \n"
							+
							// INDICE 44 - Ligacoes Ativas debito 3m
							"   sum( r.quantidadeLigacoesAtivasDebito3m ), \n"
							+
							// INDICE 45 - Ligacoes Consumo Medido, Grupo Subcategoria 101
							"   sum(  \n"
							+ "     case when ( r.codigoGrupoSubcategoria = 101 ) then \n"
							+ "       r.quantidadeLigacoesConsumoMedido \n"
							+ "     else 0 end ), \n"
							+
							// INDICE 46 - Ligacoes Consumo Medido, Grupo Subcategoria 102
							"   sum( \n"
							+ "     case when ( r.codigoGrupoSubcategoria = 102 ) then \n"
							+ "       r.quantidadeLigacoesConsumoMedido \n"
							+ "     else 0 end ), \n"
							+
							// INDICE 47 - Ligacoes Consumo Medido, Grupo Subcategoria 103
							"   sum( \n"
							+ "     case when ( r.codigoGrupoSubcategoria = 103 ) then \n"
							+ "       r.quantidadeLigacoesConsumoMedido \n"
							+ "     else 0 end ), \n"
							+
							// INDICE 48 - Ligacoes Consumo Medido, Grupo Subcategoria 200
							"   sum( \n"
							+ "     case when ( r.codigoGrupoSubcategoria = 200 ) then \n"
							+ "       r.quantidadeLigacoesConsumoMedido \n"
							+ "     else 0 end ), \n"
							+
							// INDICE 49 - Ligacoes Consumo Medido, Grupo Subcategoria 300
							"   sum( \n"
							+ "     case when ( r.codigoGrupoSubcategoria = 300 ) then \n"
							+ "       r.quantidadeLigacoesConsumoMedido \n"
							+ "     else 0 end ), \n"
							+
							// INDICE 50 - Ligacoes Consumo Medido, Grupo Subcategoria 400
							"   sum(  \n"
							+ "     case when ( r.codigoGrupoSubcategoria = 400 ) then \n"
							+ "       r.quantidadeLigacoesConsumoMedido \n"
							+ "     else 0 end ), \n"
							+
							// INDICE 51 - Ligacoes Consumo Medido, Grupo Subcategoria 116
							"   sum(  \n"
							+ "     case when ( r.codigoGrupoSubcategoria = 116 ) then \n"
							+ "       r.quantidadeLigacoesConsumoMedido \n"
							+ "     else 0 end ), \n"
							+
							// INDICE 52 - Ligacoes Consumo Medido, Grupo Subcategoria 115
							"   sum(  \n"
							+ "     case when ( r.codigoGrupoSubcategoria = 115 ) then \n"
							+ "       r.quantidadeLigacoesConsumoMedido \n"
							+ "     else 0 end ), \n"
							+
							// INDICE 53 - Ligacoes Consumo Medido
							"   sum( r.quantidadeLigacoesConsumoMedido ), \n"
							+
							// INDICE 54 - Ligacoes Consumo ate 5m3, Grupo Subcategoria 101
							"   sum(  \n"
							+ "     case when ( r.codigoGrupoSubcategoria = 101 ) then \n"
							+ "       r.quantidadeLigacoesConsumoAte5m3 \n"
							+ "     else 0 end ), \n"
							+
							// INDICE 55 - Ligacoes Consumo ate 5m3, Grupo Subcategoria 102
							"   sum(  \n"
							+ "     case when ( r.codigoGrupoSubcategoria = 102 ) then \n"
							+ "       r.quantidadeLigacoesConsumoAte5m3 \n"
							+ "     else 0 end ), \n"
							+
							// INDICE 56 - Ligacoes Consumo ate 5m3, Grupo Subcategoria 103
							"   sum(  \n"
							+ "     case when ( r.codigoGrupoSubcategoria = 103 ) then \n"
							+ "       r.quantidadeLigacoesConsumoAte5m3 \n"
							+ "     else 0 end ), \n"
							+
							// INDICE 57 - Ligacoes Consumo ate 5m3, Grupo Subcategoria 200
							"   sum(  \n"
							+ "     case when ( r.codigoGrupoSubcategoria = 200 ) then \n"
							+ "       r.quantidadeLigacoesConsumoAte5m3 \n"
							+ "     else 0 end ), \n"
							+
							// INDICE 58 - Ligacoes Consumo ate 5m3, Grupo Subcategoria 300
							"   sum(  \n"
							+ "     case when ( r.codigoGrupoSubcategoria = 300 ) then \n"
							+ "       r.quantidadeLigacoesConsumoAte5m3 \n"
							+ "     else 0 end ), \n"
							+
							// INDICE 59 - Ligacoes Consumo ate 5m3, Grupo Subcategoria 400
							"   sum(  \n"
							+ "     case when ( r.codigoGrupoSubcategoria = 400 ) then \n"
							+ "       r.quantidadeLigacoesConsumoAte5m3 \n"
							+ "     else 0 end ), \n"
							+
							// INDICE 60 - Ligacoes Consumo ate 5m3, Grupo Subcategoria 116
							"   sum(  \n"
							+ "     case when ( r.codigoGrupoSubcategoria = 116 ) then \n"
							+ "       r.quantidadeLigacoesConsumoAte5m3 \n"
							+ "     else 0 end ), \n"
							+
							// INDICE 61 - Ligacoes Consumo ate 5m3, Grupo Subcategoria 115
							"   sum( \n"
							+ "     case when ( r.codigoGrupoSubcategoria = 115 ) then \n"
							+ "       r.quantidadeLigacoesConsumoAte5m3 \n"
							+ "     else 0 end ), \n"
							+
							// INDICE 62 - Ligacoes Consumo ate 5m3
							"   sum( r.quantidadeLigacoesConsumoAte5m3 ), \n"
							+
							// INDICE 63 - Ligacoes Consumo Nao Medido, Grupo Subcategoria 101
							"   sum(  \n"
							+ "     case when ( r.codigoGrupoSubcategoria = 101 ) then \n"
							+ "       r.quantidadeLigacoesConsumoNaoMedido \n"
							+ "     else 0 end ), \n"
							+
							// INDICE 64 - Ligacoes Consumo Nao Medido, Grupo Subcategoria 102
							"   sum(  \n"
							+ "     case when ( r.codigoGrupoSubcategoria = 102 ) then \n"
							+ "       r.quantidadeLigacoesConsumoNaoMedido \n"
							+ "     else 0 end ), \n"
							+
							// INDICE 65 - Ligacoes Consumo Nao Medido, Grupo Subcategoria 103
							"   sum(  \n"
							+ "     case when ( r.codigoGrupoSubcategoria = 103 ) then \n"
							+ "       r.quantidadeLigacoesConsumoNaoMedido \n"
							+ "     else 0 end ), \n"
							+
							// INDICE 66 - Ligacoes Consumo Nao Medido, Grupo Subcategoria 200
							"   sum(  \n"
							+ "     case when ( r.codigoGrupoSubcategoria = 200 ) then \n"
							+ "       r.quantidadeLigacoesConsumoNaoMedido \n"
							+ "     else 0 end ), \n"
							+
							// INDICE 67 - Ligacoes Consumo Nao Medido, Grupo Subcategoria 300
							"   sum(  \n"
							+ "     case when ( r.codigoGrupoSubcategoria = 300 ) then \n"
							+ "       r.quantidadeLigacoesConsumoNaoMedido \n"
							+ "     else 0 end ), \n"
							+
							// INDICE 68 - Ligacoes Consumo Nao Medido, Grupo Subcategoria 400
							"   sum(  \n"
							+ "     case when ( r.codigoGrupoSubcategoria = 400 ) then \n"
							+ "       r.quantidadeLigacoesConsumoNaoMedido \n"
							+ "     else 0 end ), \n"
							+
							// INDICE 69 - Ligacoes Consumo Nao Medido, Grupo Subcategoria 116
							"   sum(  \n"
							+ "     case when ( r.codigoGrupoSubcategoria = 116 ) then \n"
							+ "       r.quantidadeLigacoesConsumoNaoMedido \n"
							+ "     else 0 end ), \n"
							+
							// INDICE 70 - Ligacoes Consumo Nao Medido, Grupo Subcategoria 115
							"   sum(  \n"
							+ "     case when ( r.codigoGrupoSubcategoria = 115 ) then \n"
							+ "       r.quantidadeLigacoesConsumoNaoMedido \n"
							+ "     else 0 end ), \n"
							+
							// INDICE 71 - Ligacoes Consumo Nao Medido
							"   sum( r.quantidadeLigacoesConsumoNaoMedido ), \n"
							+
							// INDICE 72 - Ligacoes Consumo Media, Grupo Subcategoria 101
							"   sum( \n"
							+ "     case when ( r.codigoGrupoSubcategoria = 101 ) then \n"
							+ "       r.quantidadeLigacoesConsumoMedia \n"
							+ "     else 0 end ), \n"
							+
							// INDICE 73 - Ligacoes Consumo Media, Grupo Subcategoria 102
							"   sum(  \n"
							+ "     case when ( r.codigoGrupoSubcategoria = 102 ) then \n"
							+ "       r.quantidadeLigacoesConsumoMedia \n"
							+ "     else 0 end ), \n"
							+
							// INDICE 74 - Ligacoes Consumo Media, Grupo Subcategoria 103
							"   sum( \n"
							+ "     case when ( r.codigoGrupoSubcategoria = 103 ) then \n"
							+ "      r.quantidadeLigacoesConsumoMedia \n"
							+ "     else 0 end ), \n"
							+
							// INDICE 75 - Ligacoes Consumo Media, Grupo Subcategoria 200
							"   sum(  \n"
							+ "     case when ( r.codigoGrupoSubcategoria = 200 ) then \n"
							+ "       r.quantidadeLigacoesConsumoMedia \n"
							+ "     else 0 end ), \n"
							+
							// INDICE 76 - Ligacoes Consumo Media, Grupo Subcategoria 300
							"   sum(  \n"
							+ "     case when ( r.codigoGrupoSubcategoria = 300 ) then \n"
							+ "       r.quantidadeLigacoesConsumoMedia \n"
							+ "     else 0 end ), \n"
							+
							// INDICE 77 - Ligacoes Consumo Media, Grupo Subcategoria 400
							"   sum(  \n"
							+ "     case when ( r.codigoGrupoSubcategoria = 400 ) then \n"
							+ "       r.quantidadeLigacoesConsumoMedia \n"
							+ "     else 0 end ), \n"
							+
							// INDICE 78 - Ligacoes Consumo Media, Grupo Subcategoria 116
							"   sum(  \n"
							+ "     case when ( r.codigoGrupoSubcategoria = 116 ) then \n"
							+ "       r.quantidadeLigacoesConsumoMedia \n"
							+ "     else 0 end ), \n"
							+
							// INDICE 79 - Ligacoes Consumo Media, Grupo Subcategoria 115
							"   sum( \n"
							+ "     case when ( r.codigoGrupoSubcategoria = 115 ) then \n"
							+ "       r.quantidadeLigacoesConsumoMedia \n"
							+ "     else 0 end ), \n"
							+
							// INDICE 80 - Ligacoes Consumo Media
							"   sum( r.quantidadeLigacoesConsumoMedia ), \n"
							+
							// INDICE 81 - Quantidade Economias, Grupo Subcategoria 101
							"   sum(  \n"
							+ "     case when ( r.codigoGrupoSubcategoria = 101 ) then \n"
							+ "       r.quantidadeEconomias \n"
							+ "     else 0 end ), \n"
							+
							// INDICE 82 - Quantidade Economias, Grupo Subcategoria 102
							"   sum(  \n"
							+ "     case when ( r.codigoGrupoSubcategoria = 102 ) then \n"
							+ "       r.quantidadeEconomias \n"
							+ "     else 0 end ), \n"
							+
							// INDICE 83 - Quantidade Economias, Grupo Subcategoria 103
							"   sum(  \n"
							+ "     case when ( r.codigoGrupoSubcategoria = 103 ) then \n"
							+ "       r.quantidadeEconomias \n"
							+ "     else 0 end ), \n"
							+
							// INDICE 84 - Quantidade Economias, Grupo Subcategoria 200
							"   sum(  \n"
							+ "     case when ( r.codigoGrupoSubcategoria = 200 ) then \n"
							+ "       r.quantidadeEconomias   \n"
							+ "     else 0 end ), \n"
							+
							// INDICE 85 - Quantidade Economias, Grupo Subcategoria 300
							"   sum(  \n"
							+ "     case when ( r.codigoGrupoSubcategoria = 300 ) then \n"
							+ "       r.quantidadeEconomias \n"
							+ "     else 0 end ), \n"
							+
							// INDICE 86 - Quantidade Economias, Grupo Subcategoria 400
							"   sum(  \n"
							+ "     case when ( r.codigoGrupoSubcategoria = 400 ) then \n"
							+ "       r.quantidadeEconomias \n"
							+ "     else 0 end ), \n"
							+
							// INDICE 87 - Quantidade Economias, Grupo Subcategoria 116
							"   sum(  \n" + "     case when ( r.codigoGrupoSubcategoria = 116 ) then \n"
							+ "       r.quantidadeEconomias \n"
							+ "     else 0 end ), \n"
							+
							// INDICE 88 - Quantidade Economias, Grupo Subcategoria 115
							"   sum(  \n" + "     case when ( r.codigoGrupoSubcategoria = 115 ) then \n"
							+ "       r.quantidadeEconomias   \n" + "     else 0 end ), \n"
							+
							// INDICE 89 - Quantidade Economias, Grupo Subcategoria 101
							"   sum( r.quantidadeEconomias ) \n" + select + " from \n" + "   UnResumoMetasAcumulado r \n" + " where \n "
							+ "   r.anoMesReferencia = :amReferencia \n" + where + groupby + orderby;

			retorno = session.createQuery(consulta).setInteger("amReferencia",
							filtrarRelatorioQuadroMetasAcumuladoHelper.getMesAnoReferencia()).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * Pesquisa todas as tabelas de resumo para o Orcamento
	 * [UC0722] - Gerar Relatorio para Orçamento e SINP
	 * 
	 * @author Rafael Pinto
	 * @date 11/01/2008
	 * @return anoMesReferencia
	 * @throws ErroRepositorioException
	 */
	public boolean existeDadosUnResumoParaOrcamentoSINP(int anoMesReferencia) throws ErroRepositorioException{

		boolean existeDados = true;
		Integer retorno = null;

		Session session = HibernateUtil.getSessionGerencial();

		try{

			String consulta = "SELECT count(*) " + "FROM UnResumoLigacaoEconomia resumo " + "WHERE resumo.referencia = :referencia ";

			retorno = ((Number) session.createQuery(consulta).setInteger("referencia", anoMesReferencia).setMaxResults(1).uniqueResult())
							.intValue();

			if(retorno == null || retorno == 0){
				existeDados = false;
			}

			if(existeDados){

				consulta = "SELECT count(*) " + "FROM UnResumoConsumoAgua resumo " + "WHERE resumo.referencia = :referencia ";

				retorno = ((Number) session.createQuery(consulta).setInteger("referencia", anoMesReferencia).setMaxResults(1)
								.uniqueResult()).intValue();

				if(retorno == null || retorno == 0){
					existeDados = false;
				}

				if(existeDados){

					consulta = "SELECT count(*) " + "FROM UnResumoPendencia resumo " + "WHERE resumo.anoMesReferencia = :referencia ";

					retorno = ((Number) session.createQuery(consulta).setInteger("referencia", anoMesReferencia).setMaxResults(1)
									.uniqueResult()).intValue();

					if(retorno == null || retorno == 0){
						existeDados = false;
					}

					if(existeDados){
						consulta = "SELECT count(*) " + "FROM UnResumoFaturamento resumo " + "WHERE resumo.referencia = :referencia ";

						retorno = ((Number) session.createQuery(consulta).setInteger("referencia", anoMesReferencia).setMaxResults(1)
										.uniqueResult()).intValue();

						if(retorno == null || retorno == 0){
							existeDados = false;
						}

						if(existeDados){
							consulta = "SELECT count(*) " + "FROM UnResumoArrecadacao resumo "
											+ "WHERE resumo.anoMesReferencia = :referencia ";

							retorno = ((Number) session.createQuery(consulta).setInteger("referencia", anoMesReferencia).setMaxResults(1)
											.uniqueResult()).intValue();

							if(retorno == null || retorno == 0){
								existeDados = false;
							}
						}
					}
				}
			}

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return existeDados;
	}

	/**
	 * [UC3095] Apresentar Quadro Comparativo Faturamento e Arrecadação
	 * Consulta o quadro comparativo de faturamento e arrecadação por estado
	 * 
	 * @author Luciano Galvao
	 * @date 07/05/2013
	 */
	public List consultarQuadroComparativoFaturamentoArrecadacaoPorEstado(Integer anoMesReferencia)
					throws ErroRepositorioException{

		List retorno = null;

		Session session = HibernateUtil.getSession();

		try{

			String sql = "SELECT C.CATG_ID as catgId, C.CATG_DSCATEGORIA as catgDs, " //
							+ " SUM(QDFA_VLCONTAFATURADA) as valorContaFaturada, " //
							+ " SUM(QDFA_VLCONTAINCLUIDA) as valorContaIncluida, " //
							+ " SUM(QDFA_VLCONTACANCELADA) as valorContaCancelada, " //
							+ " SUM(QDFA_VLCONTACANCPARC) as valorContaCancPorParc, " //
							+ " SUM(QDFA_VLCONTAPAGA) as valorContaPaga, " //
							+ " SUM(QDFA_VLCONTAPAGAEMDIA) as valorContaPagaEmDia, " //
							+ " SUM(QDFA_VLCONTAFATPARCEL) as valorParcCobrContaFat " //
							+ " FROM QUADRO_FATURAMENTO_ARRECADACAO QFA " //
							+ " INNER JOIN CATEGORIA C ON C.CATG_ID = QFA.CATG_ID " //
							+ " WHERE QDFA_AMREFERENCIA = :anoMesReferencia " //
							+ " GROUP  BY C.CATG_ID, C.CATG_DSCATEGORIA " //
							+ " ORDER  BY C.CATG_ID";

				// Faz a pesquisa
			retorno = (List) session.createSQLQuery(sql).addScalar("catgId", Hibernate.INTEGER).addScalar("catgDs", Hibernate.STRING)
							.addScalar("valorContaFaturada", Hibernate.BIG_DECIMAL)
							.addScalar("valorContaIncluida", Hibernate.BIG_DECIMAL).addScalar("valorContaCancelada", Hibernate.BIG_DECIMAL)
							.addScalar("valorContaCancPorParc", Hibernate.BIG_DECIMAL)
							.addScalar("valorContaPaga", Hibernate.BIG_DECIMAL).addScalar("valorContaPagaEmDia", Hibernate.BIG_DECIMAL)
							.addScalar("valorParcCobrContaFat", Hibernate.BIG_DECIMAL)
.setInteger("anoMesReferencia", anoMesReferencia)
							.list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC3095] Apresentar Quadro Comparativo Faturamento e Arrecadação
	 * Consulta o quadro comparativo de faturamento e arrecadação por gerência regional
	 * 
	 * @author Luciano Galvao
	 * @date 07/05/2013
	 */
	public List consultarQuadroComparativoFaturamentoArrecadacaoPorGerenciaRegional(Integer anoMesReferencia, Integer idGerenciaRegional)
					throws ErroRepositorioException{

		List retorno = null;

		Session session = HibernateUtil.getSession();

		try{

			String sql = "SELECT C.CATG_ID as catgId, C.CATG_DSCATEGORIA as catgDs, " //
							+ " SUM(QDFA_VLCONTAFATURADA) as valorContaFaturada, " //
							+ " SUM(QDFA_VLCONTAINCLUIDA) as valorContaIncluida, " //
							+ " SUM(QDFA_VLCONTACANCELADA) as valorContaCancelada, " //
							+ " SUM(QDFA_VLCONTACANCPARC) as valorContaCancPorParc, " //
							+ " SUM(QDFA_VLCONTAPAGA) as valorContaPaga, " //
							+ " SUM(QDFA_VLCONTAPAGAEMDIA) as valorContaPagaEmDia, " //
							+ " SUM(QDFA_VLCONTAFATPARCEL) as valorParcCobrContaFat " //
							+ " FROM QUADRO_FATURAMENTO_ARRECADACAO QFA " //
							+ " INNER JOIN LOCALIDADE LOC ON LOC.LOCA_ID = QFA.LOCA_ID " //
							+ " INNER JOIN CATEGORIA C ON C.CATG_ID = QFA.CATG_ID " //
							+ " WHERE QDFA_AMREFERENCIA = :anoMesReferencia " //
							+ " AND LOC.GREG_ID = :gerenciaRegional " //
							+ " GROUP BY C.CATG_ID, C.CATG_DSCATEGORIA " //
							+ " ORDER BY C.CATG_ID ";

			// Faz a pesquisa
			retorno = (List) session.createSQLQuery(sql).addScalar("catgId", Hibernate.INTEGER).addScalar("catgDs", Hibernate.STRING)
							.addScalar("valorContaFaturada", Hibernate.BIG_DECIMAL)
							.addScalar("valorContaIncluida", Hibernate.BIG_DECIMAL).addScalar("valorContaCancelada", Hibernate.BIG_DECIMAL)
							.addScalar("valorContaCancPorParc", Hibernate.BIG_DECIMAL)
							.addScalar("valorContaPaga", Hibernate.BIG_DECIMAL).addScalar("valorContaPagaEmDia", Hibernate.BIG_DECIMAL)
							.addScalar("valorParcCobrContaFat", Hibernate.BIG_DECIMAL)
							.setInteger("anoMesReferencia", anoMesReferencia).setInteger("gerenciaRegional", idGerenciaRegional).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC3095] Apresentar Quadro Comparativo Faturamento e Arrecadação
	 * Consulta o quadro comparativo de faturamento e arrecadação por localidade
	 * 
	 * @author Luciano Galvao
	 * @date 07/05/2013
	 */
	public List consultarQuadroComparativoFaturamentoArrecadacaoPorLocalidade(Integer anoMesReferencia, Integer idLocalidade)
					throws ErroRepositorioException{

		List retorno = null;

		Session session = HibernateUtil.getSession();

		try{

			String sql = "SELECT C.CATG_ID as catgId, C.CATG_DSCATEGORIA as catgDs, " //
							+ " QDFA_VLCONTAFATURADA as valorContaFaturada, " //
							+ " QDFA_VLCONTAINCLUIDA as valorContaIncluida, " //
							+ " QDFA_VLCONTACANCELADA as valorContaCancelada, " //
							+ " QDFA_VLCONTACANCPARC as valorContaCancPorParc, " //
							+ " QDFA_VLCONTAPAGA as valorContaPaga, " //
							+ " QDFA_VLCONTAPAGAEMDIA as valorContaPagaEmDia, " //
							+ " QDFA_VLCONTAFATPARCEL as valorParcCobrContaFat " //
							+ " FROM QUADRO_FATURAMENTO_ARRECADACAO QFA " //
							+ " INNER JOIN CATEGORIA C ON C.CATG_ID = QFA.CATG_ID " //
							+ " WHERE QDFA_AMREFERENCIA = :anoMesReferencia " //
							+ " AND LOCA_ID = :localidade " //
							+ " ORDER BY C.CATG_ID";

			// Faz a pesquisa
			retorno = (List) session.createSQLQuery(sql).addScalar("catgId", Hibernate.INTEGER).addScalar("catgDs", Hibernate.STRING)
							.addScalar("valorContaFaturada", Hibernate.BIG_DECIMAL)
							.addScalar("valorContaIncluida", Hibernate.BIG_DECIMAL).addScalar("valorContaCancelada", Hibernate.BIG_DECIMAL)
							.addScalar("valorContaCancPorParc", Hibernate.BIG_DECIMAL)
							.addScalar("valorContaPaga", Hibernate.BIG_DECIMAL).addScalar("valorContaPagaEmDia", Hibernate.BIG_DECIMAL)
							.addScalar("valorParcCobrContaFat", Hibernate.BIG_DECIMAL)
							.setInteger("anoMesReferencia", anoMesReferencia).setInteger("localidade", idLocalidade).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

}
