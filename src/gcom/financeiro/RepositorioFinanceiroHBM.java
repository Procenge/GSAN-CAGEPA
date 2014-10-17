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

package gcom.financeiro;

import gcom.cadastro.cliente.ClienteRelacaoTipo;
import gcom.cadastro.cliente.EsferaPoder;
import gcom.cadastro.imovel.Categoria;
import gcom.cobranca.ParcelamentoGrupo;
import gcom.faturamento.credito.CreditoOrigem;
import gcom.faturamento.debito.DebitoCreditoSituacao;
import gcom.financeiro.lancamento.LancamentoItemContabil;
import gcom.relatorio.financeiro.FiltroRelatorioPosicaoContasAReceberContabil;
import gcom.relatorio.financeiro.RelatorioSaldoContasAReceberContabil;
import gcom.util.ConstantesSistema;
import gcom.util.ErroRepositorioException;
import gcom.util.HibernateUtil;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

import org.hibernate.*;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 * Repositorio para financeiro
 * 
 * @author Raphael Rossiter
 * @since 09/01/2006
 */
public class RepositorioFinanceiroHBM
				implements IRepositorioFinanceiro {

	/** Repositorio Financeiro Hibernate */
	private static RepositorioFinanceiroHBM instancia;

	/**
	 * Construtor da classe RepositorioFinanceiroHBM
	 */
	protected RepositorioFinanceiroHBM() {

	}

	/**
	 * Retorna o valor de instancia
	 * 
	 * @return O valor de instancia
	 */
	public static RepositorioFinanceiroHBM getInstancia(){

		if(instancia == null){
			instancia = new RepositorioFinanceiroHBM();
		}

		return instancia;
	}

	/**
	 * Obtém os dados do resumoFaturamento a partir do ano e mês de referência
	 * [UC0175] - Gerar Lançamentos Contábeis do Faturamento
	 * 
	 * @author Raphael Rossiter, Pedro Alexandre
	 * @date 16/01/2006, 24/05/2007
	 * @param anoMesReferenciaFaturamento
	 * @param idLocalidade
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<Object[]> obterDadosResumoFaturamento(Integer anoMesReferenciaFaturamento, Integer idLocalidade)
					throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "SELECT loca.id, lctp.id, lcit.id, lict.id, catg.id, SUM(rfat.valorItemFaturamento) "
							+ "FROM ResumoFaturamento rfat " + "LEFT JOIN rfat.localidade loca " + "LEFT JOIN rfat.lancamentoTipo lctp "
							+ "LEFT JOIN rfat.lancamentoItem lcit " + "LEFT JOIN rfat.lancamentoItemContabil lict "
							+ "LEFT JOIN rfat.categoria catg "
							+ "WHERE rfat.anoMesReferencia = :anoMesReferenciaFaturamento AND loca.id = :idLocalidade "
							+ "GROUP BY loca.id, lctp.id, lcit.id, lict.id, catg.id "
							+ "ORDER BY loca.id, lctp.id, lcit.id, lict.id, catg.id ";

			retorno = session.createQuery(consulta).setInteger("anoMesReferenciaFaturamento", anoMesReferenciaFaturamento).setInteger(
							"idLocalidade", idLocalidade).list();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * Obtém a conta contábil a partir do número da razão contábil e do núemro da conta
	 * 
	 * @param razao
	 * @param conta
	 * @return ContaContabil
	 * @throws ErroRepositorioException
	 */
	public ContaContabil obterContaContabil(Short razao, Integer conta) throws ErroRepositorioException{

		ContaContabil retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "SELECT new ContaContabil(cnct.id, cnct.razao, cnct.conta, cnct.prefixo, cnct.ultimaAlteracao) "
							+ "FROM contaContabil cnct " + "WHERE  cnct.razao = :nnRazao AND cnct.conta = :nnConta ";

			retorno = (ContaContabil) session.createQuery(consulta).setInteger("nnRazao", razao.intValue()).setInteger("nnConta",
							conta.intValue()).uniqueResult();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * Obtém a conta contábil a partir da tabela LANCAMENTO_ITEM_CONTABIL
	 * 
	 * @param razao
	 * @param conta
	 * @return ContaContabil
	 * @throws ErroRepositorioException
	 */
	public ContaContabil obterContaContabil(LancamentoItemContabil lancamentoItemContabil) throws ErroRepositorioException{

		ContaContabil retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "SELECT new ContaContabil(cnct.id, cnct.razao, cnct.conta, cnct.prefixo, cnct.ultimaAlteracao) "
							+ "FROM lancamentoItemContabil lict " + "INNER JOIN lict.contaContabil cnct "
							+ "WHERE  lict.id = :idlancamentoItemContabil ";

			retorno = (ContaContabil) session.createQuery(consulta).setInteger("idlancamentoItemContabil",
							lancamentoItemContabil.getId().intValue()).uniqueResult();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * Gera Lançamentos Contabeis do Faturamento
	 * [UC000348] - Gerar Lançamento Constábeis da Arrecadação
	 * 
	 * @author Rafael Santos, Pedro Alexandre
	 * @date 23/05/2006, 25/05/2007
	 * @param anoMesArrecadacao
	 * @param idLocalidade
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection obterDadosResumoArrecadacao(Integer anoMesReferenciaArrecadacao, Integer idLocalidade)
					throws ErroRepositorioException{

		Collection retorno;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{

			consulta = "SELECT loca.id, rctp.id, lctp.id, lcit.id, lict.id, catg.id, SUM(rarr.valorItemArrecadacao) "
							+ "FROM ResumoArrecadacao rarr " + "LEFT JOIN rarr.localidade loca " + "LEFT JOIN rarr.lancamentoTipo lctp "
							+ "LEFT JOIN rarr.lancamentoItem lcit " + "LEFT JOIN rarr.lancamentoItemContabil lict "
							+ "LEFT JOIN rarr.categoria catg " + "LEFT JOIN rarr.recebimentoTipo rctp "
							+ "WHERE rarr.anoMesReferencia = :anoMesReferenciaArrecadacao AND loca.id = :idLocalidade "
							+ "GROUP BY loca.id, rctp.id, lctp.id, lcit.id, lict.id, catg.id "
							+ "ORDER BY loca.id, rctp.id, lctp.id, lcit.id, lict.id, catg.id ";

			retorno = session.createQuery(consulta).setInteger("anoMesReferenciaArrecadacao", anoMesReferenciaArrecadacao).setInteger(
							"idLocalidade", idLocalidade).list();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		return retorno;

	}

	/**
	 * Gera Lançamentos Contabeis do Faturamento
	 * [UC000348] - Gerar Lançamento Constábeis da Arrecadação
	 * Obter O Parametros Contabile Arrecadacao
	 * 
	 * @author Rafael Santos
	 * @date 23/05/2006
	 * @param anoMesArrecadacao
	 * @return
	 * @throws ErroRepositorioException
	 */
	/*
	 * public Collection obterParametrosContabilArrecadacao(Integer idCategoria,Integer
	 * idItemLancamentoContabil,
	 * Integer idItemLancamento,Integer idTipoLancamento,
	 * Integer idTipoRecebimento) throws ErroRepositorioException{
	 * Collection retorno;
	 * Session session = HibernateUtil.getSession();
	 * String consulta;
	 * try {
	 * consulta =
	 * "SELECT acp.contaContabilDebito.id,acp.contaContabilCredito.id,acp.descricaoHistoricoDebito,acp.descricaoHistoricoCredito "
	 * + " from ArrecadacaoContabilParametros acp "
	 * +
	 * " where acp.categoria.id = :idCategoria,acp.lancamentoItemContabil = :idItemLancamentoContabil,"
	 * + " acp.lancamentoItem.id = :idItemLancamento,acp.lancamentoTipo.id = :idTipoLancamento,"
	 * + " acp.recebimentoTipo.id = :idTipoRecebimento";
	 * retorno = session.createQuery(consulta)
	 * .setInteger("idCategoria",idCategoria.intValue())
	 * .setInteger("idItemLancamentoContabil",idItemLancamentoContabil.intValue())
	 * .setInteger("idItemLancamento",idItemLancamento.intValue())
	 * .setInteger("idTipoLancamento",idTipoLancamento.intValue())
	 * .setInteger("idTipoRecebimento",idTipoRecebimento.intValue()).list();
	 * } catch (HibernateException e) {
	 * // levanta a exceção para a próxima camada
	 * throw new ErroRepositorioException(e, "Erro no Hibernate");
	 * } finally {
	 * // fecha a sessão
	 * HibernateUtil.closeSession(session);
	 * }
	 * return retorno;
	 * }
	 */

	/**
	 * Este metódo é utilizado para pesquisar os registros q serão
	 * usados para contrução do txt do caso de uso
	 * [UC0469] Gerar Integração para a Contabilidade
	 * 
	 * @author Pedro Alexandre
	 * @date 28/05/2007
	 * @param idLancamentoOrigem
	 * @param anoMes
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarGerarIntegracaoContabilidade(String idLancamentoOrigem, String anoMes) throws ErroRepositorioException{

		Collection retorno;

		Session session = HibernateUtil.getSession();
		String consulta;
		try{
			consulta = "select  lcor.numeroCartao ," + " lcor.codigoTipo ," + " lcor.numeroFolha ," + " cntc.indicadorLinha ,"
							+ " cntc.prefixoContabil ," + " cntc.numeroConta ," + " cntc.numeroDigito ," + " cntc.numeroTerceiros ,"
							+ " lcor.codigoReferencia ," + " lcti.valorLancamento ," + " lcti.indicadorDebitoCredito ,"
							+ " lcor.numeroCartao2 ," + " lcor.numeroVersao ," + " loca.id " + " from LancamentoContabilItem lcti " // lançamento
							// contabil
							// item
							+ " left join lcti.lancamentoContabil lcnb" // lançamento contábil
							+ " left join lcnb.localidade loca" // localidade
							+ " left join lcnb.lancamentoOrigem lcor" // lançamento origem
							+ " left join lcti.contaContabil cntc" // conta contabil
							+ " where lcnb.anoMes= :anoMes and lcor.id= :idLancamentoOrigem" + " order by loca.id";

			retorno = session.createQuery(consulta).setInteger("anoMes", new Integer(anoMes)).setInteger("idLancamentoOrigem",
							new Integer(idLancamentoOrigem)).list();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * Exclui os registros da tabela RESUMO_DEVEDORES_DUVIDOSOS
	 * por ano mês refência contábil
	 * [UC0485] - Gerar Resumo dos Devedores Duvidosos
	 * 
	 * @author Rafael Pinto
	 * @date 22/11/2006
	 * @param anoMesReferenciaContabil
	 * @throws ErroRepositorioException
	 */
	public void removeResumoDevedoresDuvidososPorAnoMesReferenciaContabil(int anoMesReferenciaContabil) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		try{
			String delete = "delete ResumoDevedoresDuvidosos as resumo " + "where resumo.anoMesReferenciaContabil = :anoMesReferencia ";

			session.createQuery(delete).setInteger("anoMesReferencia", anoMesReferenciaContabil).executeUpdate();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
	}

	/**
	 * Atualiza com o valor nulo o mês/ano de referência de baixa contábil
	 * das contas baixadas contabilmente no ano/mês de referência
	 * [UC0485] - Gerar Resumo dos Devedores Duvidosos
	 * 
	 * @author Rafael Pinto
	 * @date 22/11/2006
	 * @param anoMesReferenciaContabil
	 * @throws ErroRepositorioException
	 */
	public void atualizaContaAnoMesReferenciaContabil(int anoMesReferenciaContabil) throws ErroRepositorioException{

		/*
		 * String update;
		 * Session session = HibernateUtil.getSession();
		 * try {
		 * update = "UPDATE gcom.faturamento.conta.Conta SET "
		 * + "cnta_amreferenciabaixacontabil = NULL, "
		 * + "cnta_tmultimaalteracao = :dataUltimaAlteracao "
		 * + "WHERE cnta_amreferenciacontabil = :anoMesReferenciaContabil ";
		 * session.createQuery(update)
		 * .setInteger("anoMesReferenciaContabil",anoMesReferenciaContabil)
		 * .setTimestamp("dataUltimaAlteracao",new Date())
		 * .executeUpdate();
		 * } catch (HibernateException e) {
		 * throw new ErroRepositorioException(e, "Erro no Hibernate");
		 * } finally {
		 * HibernateUtil.closeSession(session);
		 * }
		 */
		String update;
		Session session = HibernateUtil.getSession();
		PreparedStatement st = null;

		try{

			Connection jdbcCon = session.connection();

			update = "update conta set " + "cnta_amreferenciabaixacontabil = null " + "where cnta_id in ( " + "select "
							+ "cnta_id as contaid " + "from " + "conta_categoria contacategoria, " + "conta conta " + "where "
							+ "contacategoria.cnta_id=conta.cnta_id " + "and contacategoria.catg_id <> ? "
							// + "and conta.loca_id= ? "
							+ "and (conta.dcst_idatual in (?, ?, ?)) " + "and (conta.cnta_amreferenciabaixacontabil is not null) "
							+ "and conta.cnta_amreferenciaconta < ? " + ")";

			st = jdbcCon.prepareStatement(update);
			st.setInt(1, Categoria.PUBLICO);
			// st.setInt(2, idLocalidade);
			st.setInt(2, DebitoCreditoSituacao.NORMAL);
			st.setInt(3, DebitoCreditoSituacao.INCLUIDA);
			st.setInt(4, DebitoCreditoSituacao.RETIFICADA);
			st.setInt(5, anoMesReferenciaContabil);

			st.executeUpdate();
		}catch(SQLException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			if(null != st) try{
				st.close();
			}catch(SQLException e){
				throw new ErroRepositorioException(e, "Erro no Hibernate");
			}
			HibernateUtil.closeSession(session);
		}
	}

	/**
	 * Seleciona todas as ocorrencias dos itens dos parâmetros
	 * baixa contábil
	 * [UC0485] - Gerar Resumo dos Devedores Duvidosos
	 * 
	 * @author Rafael Pinto, Pedro Alexandre
	 * @date 22/11/2006, 24/07/2007
	 * @param idParametrosDevedoresDuvidosos
	 * @throws ErroRepositorioException
	 */
	public Collection<ParametrosDevedoresDuvidososItem> pesquisaParametrosDevedoresDuvidososItem(Integer idParametrosDevedoresDuvidosos)
					throws ErroRepositorioException{

		Collection<ParametrosDevedoresDuvidososItem> retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "SELECT parametroItem " + "FROM ParametrosDevedoresDuvidososItem parametroItem "
							+ "INNER JOIN parametroItem.parametrosDevedoresDuvidosos parametro "
							+ "WHERE parametro.id = :idParametrosDevedoresDuvidosos";

			retorno = session.createQuery(consulta).setInteger("idParametrosDevedoresDuvidosos", idParametrosDevedoresDuvidosos).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0485] - Gerar Resumo dos Devedores Duvidosos
	 * Linha 01 Retorna o valor de água acumulado,
	 * de acordo com o ano/mês de referência baixa contabil, a localiade, a categoria
	 * 
	 * @param anoMesReferencia
	 *            Ano e mês de referência do faturamento
	 * @param idLocalidade
	 *            Código da localidade
	 * @param idCategoria
	 *            Código da categoria
	 * @return retorna o valor acumulado de acordo com os parâmetros informados
	 * @throws ErroRepositorioException
	 *             Erro no Hibernate
	 */
	public ResumoDevedoresDuvidosos acumularValorAgua(int anoMesReferenciaBaixaContabil, int idLocalidade, int idCategoria)
					throws ErroRepositorioException{

		// cria o objeto de resumo de faturamento
		ResumoDevedoresDuvidosos retorno = null;

		// cria uma sessão com o hibernate
		Session session = HibernateUtil.getSession();

		// cria a variável que vai conter o hql
		String consulta;

		try{
			// constroi o hql
			consulta = "select new ResumoDevedoresDuvidosos(sum(ctcg.valorAgua)," + "cnta.localidade,"
							+ "cnta.localidade.gerenciaRegional," + "ctcg.comp_id.categoria) " + "from ContaCategoria ctcg "
							+ "inner join ctcg.comp_id.conta cnta " + "where cnta.referenciaBaixaContabil = :anoMesReferencia "
							+ "and cnta.localidade.id = :localidade " + "and ctcg.comp_id.categoria.id = :categoria "
							+ "group by cnta.localidade,cnta.localidade.gerenciaRegional,ctcg.comp_id.categoria ";

			// executa o hql
			retorno = (ResumoDevedoresDuvidosos) session.createQuery(consulta)
							.setInteger("anoMesReferencia", anoMesReferenciaBaixaContabil).setInteger("localidade", idLocalidade)
							.setInteger("categoria", idCategoria).uniqueResult();

			// erro no hibernate
		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão com o hibernate
			HibernateUtil.closeSession(session);
		}

		// retorna o resumo de faturamento criado
		return retorno;

	}

	/**
	 * [UC0485] - Gerar Resumo dos Devedores Duvidosos
	 * Linha 02 Retorna o valor do esgoto acumulado,
	 * de acordo com o ano/mês de referência baixa contabil, a localiade, a categoria
	 * 
	 * @param anoMesReferencia
	 *            Ano e mês de referência do faturamento
	 * @param idLocalidade
	 *            Código da localidade
	 * @param idCategoria
	 *            Código da categoria
	 * @return retorna o valor acumulado de acordo com os parâmetros informados
	 * @throws ErroRepositorioException
	 *             Erro no Hibernate
	 */
	public ResumoDevedoresDuvidosos acumularValorEsgoto(int anoMesReferenciaBaixaContabil, int idLocalidade, int idCategoria)
					throws ErroRepositorioException{

		// cria o objeto de resumo de faturamento
		ResumoDevedoresDuvidosos retorno = null;

		// cria uma sessão com o hibernate
		Session session = HibernateUtil.getSession();

		// cria a variável que vai conter o hql
		String consulta;

		try{
			// constroi o hql
			consulta = "select new ResumoDevedoresDuvidosos(sum(ctcg.valorEsgoto)," + "cnta.localidade,"
							+ "cnta.localidade.gerenciaRegional," + "ctcg.comp_id.categoria) " + "from ContaCategoria ctcg "
							+ "inner join ctcg.comp_id.conta cnta " + "where cnta.referenciaBaixaContabil = :anoMesReferencia "
							+ "and cnta.localidade.id = :localidade " + "and ctcg.comp_id.categoria.id = :categoria "
							+ "group by cnta.localidade,cnta.localidade.gerenciaRegional,ctcg.comp_id.categoria ";

			// executa o hql
			retorno = (ResumoDevedoresDuvidosos) session.createQuery(consulta)
							.setInteger("anoMesReferencia", anoMesReferenciaBaixaContabil).setInteger("localidade", idLocalidade)
							.setInteger("categoria", idCategoria).uniqueResult();

			// erro no hibernate
		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão com o hibernate
			HibernateUtil.closeSession(session);
		}

		// retorna o resumo de faturamento criado
		return retorno;

	}

	/**
	 * [UC0485] - Gerar Resumo dos Devedores Duvidosos
	 * Linha 03 Retorna o valor da categoria acumulado por tipo financiamento por parcelamento agua,
	 * de acordo com o ano/mês de referência baixa contabil, a localiade, a categoria
	 * 
	 * @param anoMesReferencia
	 *            Ano e mês de referência do faturamento
	 * @param idLocalidade
	 *            Código da localidade
	 * @param idCategoria
	 *            Código da categoria
	 * @return retorna o valor acumulado de acordo com os parâmetros informados
	 * @throws ErroRepositorioException
	 *             Erro no Hibernate
	 */
	public ResumoDevedoresDuvidosos acumularValorCategoriaDebitoCobradoCategoriaTipoFinanciamentoParcelamentoAgua(
					int anoMesReferenciaBaixaContabil, int idLocalidade, int idCategoria) throws ErroRepositorioException{

		// cria o objeto de resumo de faturamento
		ResumoDevedoresDuvidosos retorno = null;

		// cria uma sessão com o hibernate
		Session session = HibernateUtil.getSession();

		// cria a variável que vai conter o hql
		String consulta;

		try{
			// constroi o hql
			consulta = "select new ResumoDevedoresDuvidosos(sum(dccg.valorCategoria)," + "dbcb.localidade,"
							+ "dbcb.localidade.gerenciaRegional," + "dccg.categoria ) " + "from DebitoCobradoCategoria dccg "
							+ "inner join dccg.debitoCobrado dbcb " + "inner join dbcb.conta cnta "
							+ "where cnta.referenciaBaixaContabil = :anoMesReferencia " + "and dbcb.localidade.id = :localidade "
							+ "and dccg.categoria.id = :categoria " + "and dbcb.financiamentoTipo = :tipoFinanciamento "
							+ "group by dbcb.localidade,dbcb.localidade.gerenciaRegional,dccg.categoria ";

			// executa o hql
			retorno = (ResumoDevedoresDuvidosos) session.createQuery(consulta)
							.setInteger("anoMesReferencia", anoMesReferenciaBaixaContabil).setInteger("localidade", idLocalidade)
							.setInteger("categoria", idCategoria).setInteger("tipoFinanciamento", FinanciamentoTipo.PARCELAMENTO_AGUA)
							.uniqueResult();

			// erro no hibernate
		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		// retorna o resumo de faturamento criado
		return retorno;
	}

	/**
	 * [UC0485] - Gerar Resumo dos Devedores Duvidosos
	 * Linha 04 Retorna o valor da categoria acumulado por financiamento por parcelamento esgoto,
	 * de acordo com o ano/mês de referência baixa contabil, a localiade, a categoria
	 * 
	 * @param anoMesReferencia
	 *            Ano e mês de referência do faturamento
	 * @param idLocalidade
	 *            Código da localidade
	 * @param idCategoria
	 *            Código da categoria
	 * @return retorna o valor acumulado de acordo com os parâmetros informados
	 * @throws ErroRepositorioException
	 *             Erro no Hibernate
	 */
	public ResumoDevedoresDuvidosos acumularValorCategoriaDebitoCobradoCategoriaTipoFinanciamentoParcelamentoEsgoto(
					int anoMesReferenciaBaixaContabil, int idLocalidade, int idCategoria) throws ErroRepositorioException{

		// cria o objeto de resumo de faturamento
		ResumoDevedoresDuvidosos retorno = null;

		// cria uma sessão com o hibernate
		Session session = HibernateUtil.getSession();

		// cria a variável que vai conter o hql
		String consulta;

		try{
			// constroi o hql
			consulta = "select new ResumoDevedoresDuvidosos(sum(dccg.valorCategoria)," + "dbcb.localidade,"
							+ "dbcb.localidade.gerenciaRegional," + "dccg.categoria ) " + "from DebitoCobradoCategoria dccg "
							+ "inner join dccg.debitoCobrado dbcb " + "inner join dbcb.conta cnta "
							+ "where cnta.referenciaBaixaContabil = :anoMesReferencia " + "and dbcb.localidade.id = :localidade "
							+ "and dccg.categoria.id = :categoria " + "and dbcb.financiamentoTipo = :tipoFinanciamento "
							+ "group by dbcb.localidade,dbcb.localidade.gerenciaRegional,dccg.categoria ";

			// executa o hql
			retorno = (ResumoDevedoresDuvidosos) session.createQuery(consulta)
							.setInteger("anoMesReferencia", anoMesReferenciaBaixaContabil).setInteger("localidade", idLocalidade)
							.setInteger("categoria", idCategoria).setInteger("tipoFinanciamento", FinanciamentoTipo.PARCELAMENTO_ESGOTO)
							.uniqueResult();

			// erro no hibernate
		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		// retorna o resumo de faturamento criado
		return retorno;
	}

	/**
	 * [UC0485] - Gerar Resumo dos Devedores Duvidosos
	 * Linha 05 Retorna o valor da categoria acumulado por financiamento por parcelamento serviço,
	 * de acordo com o ano/mês de referência baixa contabil, a localiade, a categoria
	 * 
	 * @param anoMesReferencia
	 *            Ano e mês de referência do faturamento
	 * @param idLocalidade
	 *            Código da localidade
	 * @param idCategoria
	 *            Código da categoria
	 * @return retorna o valor acumulado de acordo com os parâmetros informados
	 * @throws ErroRepositorioException
	 *             Erro no Hibernate
	 */
	public Collection acumularValorCategoriaDebitoCobradoCategoriaTipoFinanciamentoParcelamentoServicos(int anoMesReferenciaBaixaContabil,
					int idLocalidade, int idCategoria) throws ErroRepositorioException{

		// cria a variável que vai armazenar a coleção pesquisada
		Collection retorno = null;

		// cria uma sessão com o hibernate
		Session session = HibernateUtil.getSession();

		// cria a variável que vai conter o hql
		String consulta;

		try{
			// constroi o hql
			consulta = "select new ResumoDevedoresDuvidosos(sum(dccg.valorCategoria)," + "lict.sequenciaImpressao," + "lict,"
							+ "dbcb.localidade," + "dbcb.localidade.gerenciaRegional," + "dccg.categoria ) "
							+ "from DebitoCobradoCategoria dccg " + "inner join dccg.debitoCobrado dbcb " + "inner join dbcb.conta cnta "
							+ "inner join dbcb.lancamentoItemContabil lict " + "where cnta.referenciaBaixaContabil = :anoMesReferencia "
							+ "and dbcb.localidade.id = :localidade" + "and dccg.categoria.id = :categoria"
							+ "and dbcb.financiamentoTipo = :tipoFinanciamento"
							+ "group by lict.sequenciaImpressao,lict,dbcb.localidade,dbcb.localidade.gerenciaRegional,dccg.categoria ";

			// executa o hql
			retorno = session.createQuery(consulta).setInteger("anoMesReferencia", anoMesReferenciaBaixaContabil).setInteger("localidade",
							idLocalidade).setInteger("categoria", idCategoria).setInteger("tipoFinanciamento",
							FinanciamentoTipo.PARCELAMENTO_SERVICO).list();

			// erro no hibernate
		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		// retorna a coleção de resumo de faturamento criada
		return retorno;

	}

	/**
	 * [UC0485] - Gerar Resumo dos Devedores Duvidosos
	 * Linha 06 Retorna o valor da categoria acumulado por financiamento juros parcelamentos,
	 * de acordo com o ano/mês de referência baixa contabil, a localiade, a categoria
	 * 
	 * @param anoMesReferencia
	 *            Ano e mês de referência do faturamento
	 * @param idLocalidade
	 *            Código da localidade
	 * @param idCategoria
	 *            Código da categoria
	 * @return retorna o valor acumulado de acordo com os parâmetros informados
	 * @throws ErroRepositorioException
	 *             Erro no Hibernate
	 */
	public ResumoDevedoresDuvidosos acumularValorCategoriaDebitoCobradoCategoriaTipoFinanciamentoJurosParcelamento(
					int anoMesReferenciaBaixaContabil, int idLocalidade, int idCategoria) throws ErroRepositorioException{

		// cria o objeto de resumo de faturamento
		ResumoDevedoresDuvidosos retorno = null;

		// cria uma sessão com o hibernate
		Session session = HibernateUtil.getSession();

		// cria a variável que vai conter o hql
		String consulta;

		try{
			// constroi o hql
			consulta = "select new ResumoDevedoresDuvidosos(sum(dccg.valorCategoria)," + "dbcb.localidade,"
							+ "dbcb.localidade.gerenciaRegional," + "dccg.categoria ) " + "from DebitoCobradoCategoria dccg "
							+ "inner join dccg.debitoCobrado dbcb " + "inner join dbcb.conta cnta "
							+ "where cnta.referenciaBaixaContabil = :anoMesReferencia " + "and dbcb.localidade.id = :localidade "
							+ "and dccg.categoria.id = :categoria " + "and dbcb.financiamentoTipo = :tipoFinanciamento "
							+ "group by dbcb.localidade,dbcb.localidade.gerenciaRegional,dccg.categoria ";

			// executa o hql
			retorno = (ResumoDevedoresDuvidosos) session.createQuery(consulta)
							.setInteger("anoMesReferencia", anoMesReferenciaBaixaContabil).setInteger("localidade", idLocalidade)
							.setInteger("categoria", idCategoria).setInteger("tipoFinanciamento", FinanciamentoTipo.JUROS_PARCELAMENTO)
							.uniqueResult();

			// erro no hibernate
		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		// retorna o resumo de faturamento criado
		return retorno;
	}

	/**
	 * [UC0485] - Gerar Resumo dos Devedores Duvidosos
	 * Linha 07 Retorna o valor da categoria acumulado por financiamento por serviço,
	 * de acordo com o ano/mês de referência baixa contabil, a localiade, a categoria
	 * 
	 * @param anoMesReferencia
	 *            Ano e mês de referência do faturamento
	 * @param idLocalidade
	 *            Código da localidade
	 * @param idCategoria
	 *            Código da categoria
	 * @return retorna o valor acumulado de acordo com os parâmetros informados
	 * @throws ErroRepositorioException
	 *             Erro no Hibernate
	 */
	public Collection acumularValorCategoriaDebitoTipoFinanciamentoServico(int anoMesReferenciaBaixaContabil, int idLocalidade,
					int idCategoria) throws ErroRepositorioException{

		// cria a variável que vai armazenar a coleção pesquisada
		Collection retorno = null;

		// cria uma sessão com o hibernate
		Session session = HibernateUtil.getSession();

		// cria a variável que vai conter o hql
		String consulta;

		try{
			// constroi o hql
			consulta = "select new ResumoDevedoresDuvidosos(sum(dccg.valorCategoria)," + "lict.sequenciaImpressao," + "lict,"
							+ "dbcb.localidade," + "dbcb.localidade.gerenciaRegional," + "dccg.categoria ) "
							+ "from DebitoCobradoCategoria dccg " + "inner join dccg.debitoCobrado dbcb " + "inner join dbcb.conta cnta "
							+ "inner join dbcb.lancamentoItemContabil lict " + "where cnta.referenciaBaixaContabil = :anoMesReferencia "
							+ "and dbcb.localidade.id = :localidade" + "and dccg.categoria.id = :categoria"
							+ "and dbcb.financiamentoTipo = :tipoFinanciamento"
							+ "group by lict.sequenciaImpressao,lict,dbcb.localidade,dbcb.localidade.gerenciaRegional,dccg.categoria ";

			// executa o hql
			retorno = session.createQuery(consulta).setInteger("anoMesReferencia", anoMesReferenciaBaixaContabil).setInteger("localidade",
							idLocalidade).setInteger("categoria", idCategoria).setInteger("tipoFinanciamento",
							FinanciamentoTipo.SERVICO_NORMAL).list();

			// erro no hibernate
		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		// retorna a coleção pesquisada
		return retorno;
	}

	/**
	 * [UC0345] - Gerar Relatorio de Resumo da Arrecadação
	 * 
	 * @author Vivianne Sousa
	 * @date 10/04/2007
	 * @param idLancamentoTipo
	 * @throws ErroRepositorioException
	 */
	public String obterDescricaoLancamentoTipo(Integer idLancamentoTipo) throws ErroRepositorioException{

		String retorno;
		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "SELECT descricao " + "FROM LancamentoTipo " + "WHERE id = :idLancamentoTipo ";

			retorno = (String) session.createQuery(consulta).setInteger("idLancamentoTipo", idLancamentoTipo).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC00175] Gerar Lançamentos Contábeis do Faturamento
	 * Pesquisa os parâmetros contábil do
	 * 
	 * @author Pedro Alexandre
	 * @date 24/05/2007
	 * @param idCategoria
	 * @param idItemLancamentoContabil
	 * @param idItemLancamento
	 * @param idTipoLancamento
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Object[] obterParametrosContabilFaturamento(Integer idCategoria, Integer idLancamentoItemContabil, Integer idItemLancamento,
					Integer idTipoLancamento) throws ErroRepositorioException{

		Object[] retorno;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "SELECT plcf.contaContabilDebito.id,plcf.contaContabilCredito.id,plcf.descricaoHistoricoDebito,plcf.descricaoHistoricoCredito "
							+ "FROM FaturamentoContabilParametros plcf "
							+ "LEFT JOIN plcf.categoria catg "
							+ "LEFT JOIN plcf.lancamentoItemContabil lict "
							+ "LEFT JOIN plcf.lancamentoItem lcit "
							+ "LEFT JOIN plcf.lancamentoTipo lctp "
							+ "WHERE catg.id = :idCategoria AND "
							+ "lcit.id = :idItemLancamento AND " + "lctp.id = :idTipoLancamento ";

			if(idLancamentoItemContabil == null){
				consulta = consulta + "AND lict.id is null ";
			}else{
				consulta = consulta + "AND lict.id = :idLancamentoItemContabil ";
			}

			if(idLancamentoItemContabil == null){
				retorno = (Object[]) session.createQuery(consulta).setInteger("idCategoria", idCategoria).setInteger("idItemLancamento",
								idItemLancamento).setInteger("idTipoLancamento", idTipoLancamento).uniqueResult();
			}else{
				retorno = (Object[]) session.createQuery(consulta).setInteger("idCategoria", idCategoria).setInteger(
								"idLancamentoItemContabil", idLancamentoItemContabil).setInteger("idItemLancamento", idItemLancamento)
								.setInteger("idTipoLancamento", idTipoLancamento).uniqueResult();
			}
		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		return retorno;

	}

	/**
	 * Pesquisa as localidades que tem resumo de faturamento
	 * para o ano/mês de faturamento informado.
	 * [UC00175] Gerar Lançamentos Contábeis do Faturamento
	 * 
	 * @author Pedro Alexandre
	 * @date 25/05/2007
	 * @param anoMesFaturamento
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<Integer> pesquisarIdsLocalidadesParaGerarLancamentosContabeisFaturamento(Integer anoMesFaturamento)
					throws ErroRepositorioException{

		Collection retorno;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "SELECT DISTINCT (loca.id) " + "FROM ResumoFaturamento rfat " + "LEFT JOIN rfat.localidade loca "
							+ "WHERE rfat.anoMesReferencia = :anoMesFaturamento";

			retorno = session.createQuery(consulta).setInteger("anoMesFaturamento", anoMesFaturamento).list();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		return retorno;

	}

	/**
	 * [UC00348] Gerar Lançamentos Contábeis da Arrecadação
	 * Pesquisa os parâmetros contábil da arrecadação.
	 * 
	 * @author Pedro Alexandre
	 * @date 31/05/2007
	 * @param idRecebimentoTipo
	 * @param idCategoria
	 * @param idItemLancamentoContabil
	 * @param idItemLancamento
	 * @param idTipoLancamento
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Object[] obterParametrosContabilArrecadacao(Integer idRecebimentoTipo, Integer idCategoria, Integer idLancamentoItemContabil,
					Integer idItemLancamento, Integer idTipoLancamento) throws ErroRepositorioException{

		Object[] retorno;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "SELECT plca.contaContabilDebito.id,plca.contaContabilCredito.id,plca.descricaoHistoricoDebito,plca.descricaoHistoricoCredito "
							+ "FROM ArrecadacaoContabilParametros plca "
							+ "LEFT JOIN plca.recebimentoTipo rctp "
							+ "LEFT JOIN plca.categoria catg "
							+ "LEFT JOIN plca.lancamentoItemContabil lict "
							+ "LEFT JOIN plca.lancamentoItem lcit "
							+ "LEFT JOIN plca.lancamentoTipo lctp "
							+ "WHERE rctp.id = :idRecebimentoTipo AND "
							+ "catg.id = :idCategoria AND "
							+ "lcit.id = :idItemLancamento AND " + "lctp.id = :idTipoLancamento ";

			if(idLancamentoItemContabil == null){
				consulta = consulta + "AND lict.id is null ";
			}else{
				consulta = consulta + "AND lict.id = :idLancamentoItemContabil ";
			}

			if(idLancamentoItemContabil == null){
				retorno = (Object[]) session.createQuery(consulta).setInteger("idRecebimentoTipo", idRecebimentoTipo).setInteger(
								"idCategoria", idCategoria).setInteger("idItemLancamento", idItemLancamento).setInteger("idTipoLancamento",
								idTipoLancamento).uniqueResult();
			}else{
				retorno = (Object[]) session.createQuery(consulta).setInteger("idRecebimentoTipo", idRecebimentoTipo).setInteger(
								"idCategoria", idCategoria).setInteger("idLancamentoItemContabil", idLancamentoItemContabil).setInteger(
								"idItemLancamento", idItemLancamento).setInteger("idTipoLancamento", idTipoLancamento).uniqueResult();
			}
		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		return retorno;

	}

	/**
	 * Pesquisa as localidades que tem resumo da arrecadação
	 * para o ano/mês de arrecadação informado.
	 * [UC00348] Gerar Lançamentos Contábeis da Arrecadação
	 * 
	 * @author Pedro Alexandre
	 * @date 31/05/2007
	 * @param anoMesArrecadacao
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<Integer> pesquisarIdsLocalidadesParaGerarLancamentosContabeisArrecadacao(Integer anoMesArrecadacao)
					throws ErroRepositorioException{

		Collection retorno;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "SELECT DISTINCT (loca.id) " + "FROM ResumoArrecadacao rarr " + "LEFT JOIN rarr.localidade loca "
							+ "WHERE rarr.anoMesReferencia = :anoMesArrecadacao";

			retorno = session.createQuery(consulta).setInteger("anoMesArrecadacao", anoMesArrecadacao).list();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		return retorno;

	}

	/**
	 * Pesquisa os parâmetros dos devedores duvidosos por
	 * ano/mês de referência contábil.
	 * [UC0485] Gerar Resumo dos Devedores Duvidosos
	 * 
	 * @author Pedro Alexandre
	 * @date 06/06/2007
	 * @param anoMesReferenciaContabil
	 * @return
	 * @throws ErroRepositorioException
	 */
	public ParametrosDevedoresDuvidosos pesquisarParametrosDevedoresDuvidosos(Integer anoMesReferenciaContabil)
					throws ErroRepositorioException{

		ParametrosDevedoresDuvidosos retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "SELECT pded " + "FROM ParametrosDevedoresDuvidosos pded "
							+ "WHERE pded.anoMesReferenciaContabil = :anoMesReferenciaContabil ";

			retorno = (ParametrosDevedoresDuvidosos) session.createQuery(consulta).setInteger("anoMesReferenciaContabil",
							anoMesReferenciaContabil).uniqueResult();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * Atualiza com o valor nulo o mês/ano de referência de baixa contábil
	 * das contas baixadas contabilmente no ano/mês de referência
	 * para a localidade informada.
	 * [UC0485] - Gerar Resumo dos Devedores Duvidosos
	 * 
	 * @author Rafael Pinto, Pedro Alexandre
	 * @date 22/11/2006, 06/06/2007
	 * @param anoMesReferenciaContabil
	 * @param idLocalidade
	 * @throws ErroRepositorioException
	 */
	public void atualizaContaAnoMesReferenciaContabil(int anoMesReferenciaContabil, Integer idLocalidade) throws ErroRepositorioException{

		/*
		 * String update;
		 * Session session = HibernateUtil.getSession();
		 * PreparedStatement st = null;
		 * try {
		 * Connection jdbcCon = session.connection();
		 * update = "update conta set "
		 * + "cnta_amreferenciabaixacontabil = null "
		 * + "where conta.loca_id= ? "
		 * + "and conta.cnta_amreferenciabaixacontabil = ? " ;
		 * st = jdbcCon.prepareStatement(update);
		 * st.setInt(1, idLocalidade);
		 * st.setInt(2, anoMesReferenciaContabil);
		 * st.executeUpdate();
		 * } catch (SQLException e) {
		 * throw new ErroRepositorioException(e, "Erro no Hibernate");
		 * } finally {
		 * if (null != st)
		 * try {
		 * st.close();
		 * } catch (SQLException e) {
		 * throw new ErroRepositorioException(e, "Erro no Hibernate");
		 * }
		 * HibernateUtil.closeSession(session);
		 * }
		 */

		Session session = HibernateUtil.getSession();

		String atualizar;

		try{

			atualizar = "UPDATE Conta as cnta " + "SET cnta.referenciaBaixaContabil = null , cnta.ultimaAlteracao = :dataAlteracao "
							+ "WHERE cnta.localidade.id = :idLocalidade and cnta.referenciaBaixaContabil = :referenciaContabil";

			session.createQuery(atualizar).setInteger("idLocalidade", idLocalidade).setInteger("referenciaContabil",
							anoMesReferenciaContabil).setTimestamp("dataAlteracao", new Date()).executeUpdate();

		}catch(HibernateException e){

			throw new ErroRepositorioException(e, "Erro no Hibernate");

		}finally{
			HibernateUtil.closeSession(session);
		}

	}

	/**
	 * Exclui os registros da tabela RESUMO_DEVEDORES_DUVIDOSOS
	 * por ano mês refência contábil e localidade
	 * [UC0485] - Gerar Resumo dos Devedores Duvidosos
	 * 
	 * @author Rafael Pinto, Pedro Alexandre
	 * @date 22/11/2006, 06/06/2007
	 * @param anoMesReferenciaContabil
	 * @param idLocalidade
	 * @throws ErroRepositorioException
	 */
	public void removeResumoDevedoresDuvidososPorAnoMesReferenciaContabil(int anoMesReferenciaContabil, Integer idLocalidade)
					throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		try{
			String delete = "delete ResumoDevedoresDuvidosos as resumo " + "where resumo.anoMesReferenciaContabil = :anoMesReferencia "
							+ "and resumo.localidade.id = :idLocalidade ";

			session.createQuery(delete).setInteger("anoMesReferencia", anoMesReferenciaContabil).setInteger("idLocalidade", idLocalidade)
							.executeUpdate();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
	}

	/**
	 * Pesquisa a coleção de ids das localidades para processar o resumo
	 * dos devedores duvidosos.
	 * [UC0485] Gerar Resumo dos Devedores Duvidosos
	 * 
	 * @author Pedro Alexandre
	 * @date 25/06/2007
	 * @param anoMesReferenciaContabil
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<Integer> pesquisarIdsLocalidadesGerarResumoDevedoresDuvidosos(int anoMesReferenciaContabil)
					throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{

			consulta = "SELECT DISTINCT loca.id " + "FROM Localidade loca " + "WHERE loca.indicadorUso = :indicadorUso ";

			retorno = session.createQuery(consulta).setInteger("indicadorUso", ConstantesSistema.SIM).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * Linha 01 Retorna o valor de água acumulado,
	 * de acordo com o ano/mês de referência baixa contabil, a localiade, a categoria
	 * [UC0485] - Gerar Resumo dos Devedores Duvidosos
	 * 
	 * @author Pedro Alexandre
	 * @date 12/06/2007
	 * @param anoMesReferenciaBaixaContabil
	 * @param idLocalidade
	 * @param idCategoria
	 * @param colecaoIdsContas
	 * @return
	 * @throws ErroRepositorioException
	 */
	public BigDecimal acumularValorAgua(int anoMesReferenciaBaixaContabil, int idLocalidade, int idCategoria,
					Collection<Integer> colecaoIdsContas) throws ErroRepositorioException{

		// cria o objeto de resumo de faturamento
		BigDecimal retorno = null;

		// cria uma sessão com o hibernate
		Session session = HibernateUtil.getSession();

		// cria a variável que vai conter o hql
		String consulta;

		try{
			// constroi o hql
			consulta = "select sum(ctcg.valorAgua) " + "from ContaCategoria ctcg " + "inner join ctcg.comp_id.conta cnta "
							+ "inner join ctcg.comp_id.categoria catg " + "inner join cnta.localidade loca "
							+ "where cnta.referenciaBaixaContabil = :anoMesReferencia " + "and loca.id = :localidade "
							+ "and catg.id = :categoria " + "and cnta.id in(:idsContas) ";

			// executa o hql
			retorno = (BigDecimal) session.createQuery(consulta).setInteger("anoMesReferencia", anoMesReferenciaBaixaContabil).setInteger(
							"localidade", idLocalidade).setInteger("categoria", idCategoria)
							.setParameterList("idsContas", colecaoIdsContas).uniqueResult();

			// erro no hibernate
		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão com o hibernate
			HibernateUtil.closeSession(session);
		}

		// retorna o resumo de faturamento criado
		return retorno;

	}

	/**
	 * Linha 02 Retorna o valor do esgoto acumulado,
	 * de acordo com o ano/mês de referência baixa contabil, a localiade, a categoria
	 * [UC0485] - Gerar Resumo dos Devedores Duvidosos
	 * 
	 * @author Pedro Alexandre
	 * @date 13/06/2007
	 * @param anoMesReferenciaBaixaContabil
	 * @param idLocalidade
	 * @param idCategoria
	 * @param colecaoIdsContas
	 * @return retorna o valor acumulado de acordo com os parâmetros informados
	 * @throws ErroRepositorioException
	 */
	public BigDecimal acumularValorEsgoto(int anoMesReferenciaBaixaContabil, int idLocalidade, int idCategoria,
					Collection<Integer> colecaoIdsContas) throws ErroRepositorioException{

		// cria o objeto de resumo de faturamento
		BigDecimal retorno = null;

		// cria uma sessão com o hibernate
		Session session = HibernateUtil.getSession();

		// cria a variável que vai conter o hql
		String consulta;

		try{
			// constroi o hql
			consulta = "select sum(ctcg.valorEsgoto) " + "from ContaCategoria ctcg " + "inner join ctcg.comp_id.conta cnta "
							+ "inner join ctcg.comp_id.categoria catg " + "inner join cnta.localidade loca "
							+ "where cnta.referenciaBaixaContabil = :anoMesReferencia " + "and loca.id = :localidade "
							+ "and catg.id = :categoria " + "and cnta.id in(:idsContas) ";

			// executa o hql
			retorno = (BigDecimal) session.createQuery(consulta).setInteger("anoMesReferencia", anoMesReferenciaBaixaContabil).setInteger(
							"localidade", idLocalidade).setInteger("categoria", idCategoria)
							.setParameterList("idsContas", colecaoIdsContas).uniqueResult();

			// erro no hibernate
		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão com o hibernate
			HibernateUtil.closeSession(session);
		}

		// retorna o resumo de faturamento criado
		return retorno;
	}

	/**
	 * Linha 03 Retorna o valor da categoria acumulado por tipo financiamento por parcelamento agua,
	 * de acordo com o ano/mês de referência baixa contabil, a localiade, a categoria
	 * [UC0485] - Gerar Resumo dos Devedores Duvidosos
	 * 
	 * @author Pedro Alexandre
	 * @date 13/06/2007
	 * @param anoMesReferenciaBaixaContabil
	 * @param idLocalidade
	 * @param idCategoria
	 * @param colecaoIdsContas
	 * @return retorna o valor acumulado de acordo com os parâmetros informados
	 * @throws ErroRepositorioException
	 */
	public BigDecimal acumularValorCategoriaDebitoCobradoCategoriaTipoFinanciamentoParcelamentoAgua(int anoMesReferenciaBaixaContabil,
					int idLocalidade, int idCategoria, Collection<Integer> colecaoIdsContas) throws ErroRepositorioException{

		// cria o objeto de resumo de faturamento
		BigDecimal retorno = null;

		// cria uma sessão com o hibernate
		Session session = HibernateUtil.getSession();

		// cria a variável que vai conter o hql
		String consulta;

		try{
			// constroi o hql
			consulta = "select sum(dccg.valorCategoria) " + "from DebitoCobradoCategoria dccg " + "inner join dccg.debitoCobrado dbcb "
							+ "inner join dbcb.conta cnta " + "inner join dccg.categoria catg " + "inner join dbcb.localidade loca "
							+ "inner join dbcb.financiamentoTipo fntp " + "where cnta.referenciaBaixaContabil = :anoMesReferencia "
							+ "and loca.id = :localidade " + "and catg.id = :categoria " + "and fntp.id = :tipoFinanciamento "
							+ "and cnta.id in (:idsContas) ";

			// executa o hql
			retorno = (BigDecimal) session.createQuery(consulta).setInteger("anoMesReferencia", anoMesReferenciaBaixaContabil).setInteger(
							"localidade", idLocalidade).setInteger("categoria", idCategoria).setInteger("tipoFinanciamento",
							FinanciamentoTipo.PARCELAMENTO_AGUA).setParameterList("idsContas", colecaoIdsContas).uniqueResult();

			// erro no hibernate
		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		// retorna o resumo de faturamento criado
		return retorno;
	}

	/**
	 * Linha 04 Retorna o valor da categoria acumulado por financiamento por parcelamento esgoto,
	 * de acordo com o ano/mês de referência baixa contabil, a localiade, a categoria.
	 * [UC0485] - Gerar Resumo dos Devedores Duvidosos
	 * 
	 * @author Pedro Alexandre
	 * @date 13/06/2007
	 * @param anoMesReferenciaBaixaContabil
	 * @param idLocalidade
	 * @param idCategoria
	 * @param colecaoIdsContas
	 * @return retorna o valor acumulado de acordo com os parâmetros informados
	 * @throws ErroRepositorioException
	 */
	public BigDecimal acumularValorCategoriaDebitoCobradoCategoriaTipoFinanciamentoParcelamentoEsgoto(int anoMesReferenciaBaixaContabil,
					int idLocalidade, int idCategoria, Collection<Integer> colecaoIdsContas) throws ErroRepositorioException{

		// cria o objeto de resumo de faturamento
		BigDecimal retorno = null;

		// cria uma sessão com o hibernate
		Session session = HibernateUtil.getSession();

		// cria a variável que vai conter o hql
		String consulta;

		try{
			// constroi o hql
			consulta = "select sum(dccg.valorCategoria) " + "from DebitoCobradoCategoria dccg " + "inner join dccg.debitoCobrado dbcb "
							+ "inner join dbcb.conta cnta " + "inner join dbcb.localidade loca " + "inner join dccg.categoria catg "
							+ "inner join dbcb.financiamentoTipo fntp " + "where cnta.referenciaBaixaContabil = :anoMesReferencia "
							+ "and loca.id = :localidade " + "and catg.id = :categoria " + "and fntp.id = :tipoFinanciamento "
							+ "and cnta.id in (:idsContas) ";

			// executa o hql
			retorno = (BigDecimal) session.createQuery(consulta).setInteger("anoMesReferencia", anoMesReferenciaBaixaContabil).setInteger(
							"localidade", idLocalidade).setInteger("categoria", idCategoria).setInteger("tipoFinanciamento",
							FinanciamentoTipo.PARCELAMENTO_ESGOTO).setParameterList("idsContas", colecaoIdsContas).uniqueResult();

			// erro no hibernate
		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		// retorna o resumo de faturamento criado
		return retorno;
	}

	/**
	 * Linha 06 Retorna o valor da categoria acumulado por financiamento juros parcelamentos,
	 * de acordo com o ano/mês de referência baixa contabil, a localiade, a categoria
	 * [UC0485] - Gerar Resumo dos Devedores Duvidosos
	 * 
	 * @author Pedro Alexandre
	 * @date 13/06/2007
	 * @param anoMesReferenciaBaixaContabil
	 * @param idLocalidade
	 * @param idCategoria
	 * @param colecaoIdsContas
	 * @return retorna o valor acumulado de acordo com os parâmetros informados
	 * @throws ErroRepositorioException
	 */
	public BigDecimal acumularValorCategoriaDebitoCobradoCategoriaTipoFinanciamentoJurosParcelamento(int anoMesReferenciaBaixaContabil,
					int idLocalidade, int idCategoria, Collection<Integer> colecaoIdsContas) throws ErroRepositorioException{

		// cria o objeto de resumo de faturamento
		BigDecimal retorno = null;

		// cria uma sessão com o hibernate
		Session session = HibernateUtil.getSession();

		// cria a variável que vai conter o hql
		String consulta;

		try{
			// constroi o hql
			consulta = "select sum(dccg.valorCategoria) " + "from DebitoCobradoCategoria dccg " + "inner join dccg.debitoCobrado dbcb "
							+ "inner join dbcb.conta cnta " + "inner join dbcb.localidade loca " + "inner join dccg.categoria catg "
							+ "inner join dbcb.financiamentoTipo fntp " + "where cnta.referenciaBaixaContabil = :anoMesReferencia "
							+ "and loca.id = :localidade " + "and catg.id = :categoria " + "and fntp.id = :tipoFinanciamento "
							+ "and cnta.id in (:idsContas) ";

			// executa o hql
			retorno = (BigDecimal) session.createQuery(consulta).setInteger("anoMesReferencia", anoMesReferenciaBaixaContabil).setInteger(
							"localidade", idLocalidade).setInteger("categoria", idCategoria).setInteger("tipoFinanciamento",
							FinanciamentoTipo.JUROS_PARCELAMENTO).setParameterList("idsContas", colecaoIdsContas).uniqueResult();

			// erro no hibernate
		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		// retorna o resumo de faturamento criado
		return retorno;
	}

	/**
	 * Linha 05 Retorna o valor da categoria acumulado por financiamento por parcelamento serviço,
	 * de acordo com o ano/mês de referência baixa contabil, a localiade, a categoria
	 * [UC0485] - Gerar Resumo dos Devedores Duvidosos
	 * 
	 * @author Pedro Alexandre
	 * @date 13/06/2007
	 * @param anoMesReferenciaBaixaContabil
	 * @param idLocalidade
	 * @param idCategoria
	 * @param colecaoIdsContas
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection acumularValorCategoriaDebitoCobradoCategoriaPorTipoFinanciamento(int anoMesReferenciaBaixaContabil, int idLocalidade,
					int idCategoria, int idFinanciamentoTipo, Collection<Integer> colecaoIdsContas) throws ErroRepositorioException{

		// cria a variável que vai armazenar a coleção pesquisada
		Collection retorno = null;

		Collection colecaoTiposFinanciamento = new ArrayList();
		colecaoTiposFinanciamento.add(idFinanciamentoTipo);
		colecaoTiposFinanciamento.add(FinanciamentoTipo.ARRASTO_AGUA);
		colecaoTiposFinanciamento.add(FinanciamentoTipo.ARRASTO_ESGOTO);
		colecaoTiposFinanciamento.add(FinanciamentoTipo.ARRASTO_SERVICO);

		// cria uma sessão com o hibernate
		Session session = HibernateUtil.getSession();

		// cria a variável que vai conter o hql
		String consulta;

		try{
			// constroi o hql
			consulta = "select sum(dccg.valorCategoria),lict.sequenciaImpressao,lict.id " + "from DebitoCobradoCategoria dccg "
							+ "inner join dccg.debitoCobrado dbcb " + "inner join dbcb.conta cnta "
							+ "inner join dbcb.lancamentoItemContabil lict " + "inner join dbcb.localidade loca "
							+ "inner join dccg.categoria catg " + "inner join dbcb.financiamentoTipo fntp "
							+ "where cnta.referenciaBaixaContabil = :anoMesReferencia " + "and loca.id = :localidade "
							+ "and catg.id = :categoria " + "and fntp.id in (:idsTiposFinanciamento) " + "and cnta.id in (:idsContas) "
							+ "group by lict.sequenciaImpressao,lict.id ";

			// executa o hql
			retorno = session.createQuery(consulta).setInteger("anoMesReferencia", anoMesReferenciaBaixaContabil).setInteger("localidade",
							idLocalidade).setInteger("categoria", idCategoria).setParameterList("idsTiposFinanciamento",
							colecaoTiposFinanciamento).setParameterList("idsContas", colecaoIdsContas).list();

			// erro no hibernate
		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		// retorna a coleção de resumo de faturamento criada
		return retorno;

	}

	/**
	 * Atualiza com o valor de referência de baixa contábil
	 * das contas baixadas contabilmente no ano/mês de referência
	 * [UC0485] - Gerar Resumo dos Devedores Duvidosos
	 * 
	 * @author Rafael Pinto, Pedro Alexandre
	 * @date 22/11/2006, 14/06/2007
	 * @param anoMesReferenciaContabil
	 * @param colecaoIdsContas
	 * @throws ErroRepositorioException
	 */
	public void atualizaContaAnoMesReferenciaContabil(int anoMesReferenciaContabil, Collection<Integer> colecaoIdsContas)
					throws ErroRepositorioException{

		String update;
		Session session = HibernateUtil.getSession();

		try{
			update = "UPDATE Conta cnta SET " + "cnta.referenciaBaixaContabil = :anoMesReferenciaContabil, "
							+ "cnta.ultimaAlteracao = :dataUltimaAlteracao " + "WHERE cnta.id in (:idsContas) ";

			session.createQuery(update).setInteger("anoMesReferenciaContabil", anoMesReferenciaContabil).setTimestamp(
							"dataUltimaAlteracao", new Date()).setParameterList("idsContas", colecaoIdsContas).executeUpdate();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
	}

	// *******************************Caern
	/**
	 * Este metódo é utilizado para pesquisar os registros q serão
	 * usados para contrução do txt do caso de uso
	 * [UC0469] Gerar Integração para a Contabilidade
	 * 
	 * @author Flávio Cordeiro
	 * @date 06/06/2007
	 * @param idLancamentoOrigem
	 * @param anoMes
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarGerarIntegracaoContabilidadeCaern(String idLancamentoOrigem, String anoMes) throws ErroRepositorioException{

		Collection retorno;

		Session session = HibernateUtil.getSession();
		String consulta;
		try{
			consulta = "select  lcor.numeroCartao ,"// 0
							+ " lcor.codigoTipo ,"// 1
							+ " lcor.numeroFolha ,"// 2
							+ " cntc.indicadorLinha ,"// 3
							+ " cntc.prefixoContabil ,"// 4
							+ " cntc.numeroConta ,"// 5
							+ " cntc.numeroDigito ,"// 6
							+ " cntc.numeroTerceiros ,"// 7
							+ " lcor.codigoReferencia ,"// 8
							+ " sum(lcti.valorLancamento) ,"// 9
							+ " lcti.indicadorDebitoCredito ,"// 10
							+ " lcor.numeroCartao2 ,"// 11
							+ " lcor.numeroVersao ,"// 12
							+ " loca.id, "// 13
							+ " loca.codigoCentroCusto"// 14
							+ " from LancamentoContabilItem lcti " // lançamento contabil item
							+ " left join lcti.lancamentoContabil lcnb" // lançamento contábil
							+ " left join lcnb.localidade loca" // localidade
							+ " left join lcnb.lancamentoOrigem lcor" // lançamento origem
							+ " left join lcti.contaContabil cntc" // conta contabil
							+ " where lcnb.anoMes= :anoMes and lcor.id= :idLancamentoOrigem"
							+ " group by loca.id, lcti.indicadorDebitoCredito,"
							+ " cntc.numeroConta ,cntc.numeroDigito ,cntc.numeroTerceiros ,"
							+ " lcor.numeroCartao, lcor.codigoTipo,"
							+ " lcor.numeroFolha, cntc.indicadorLinha ,cntc.prefixoContabil ,"
							+ " lcor.codigoReferencia ,"
							+ " lcor.numeroCartao2 ,lcor.numeroVersao ,loca.codigoCentroCusto" + " order by loca.id";

			retorno = session.createQuery(consulta).setInteger("anoMes", new Integer(anoMes)).setInteger("idLancamentoOrigem",
							new Integer(idLancamentoOrigem)).list();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * Obtém os dados do resumo dos devedores duvidosos
	 * a partir do ano e mês de referência contábil e da localidade.
	 * [UC0486] - Gerar Lançamentos Contábeis dos Devedores Duvidosos
	 * 
	 * @author Pedro Alexandre
	 * @date 21/06/2007
	 * @param anoMesReferenciaContabil
	 * @param idLocalidade
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<Object[]> obterDadosResumoDevedoresDuvidosos(Integer anoMesReferenciaContabil, Integer idLocalidade)
					throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "SELECT loca.id, lctp.id, lcit.id, lict.id, catg.id, SUM(rded.valorBaixado) "
							+ "FROM ResumoDevedoresDuvidosos rded " + "LEFT JOIN rded.localidade loca "
							+ "LEFT JOIN rded.lancamentoTipo lctp " + "LEFT JOIN rded.lancamentoItem lcit "
							+ "LEFT JOIN rded.lancamentoItemContabil lict " + "LEFT JOIN rded.categoria catg "
							+ "WHERE rded.anoMesReferenciaContabil = :anoMesReferenciaContabil AND loca.id = :idLocalidade "
							+ "GROUP BY loca.id, lctp.id, lcit.id, lict.id, catg.id "
							+ "ORDER BY loca.id, lctp.id, lcit.id, lict.id, catg.id ";

			retorno = session.createQuery(consulta).setInteger("anoMesReferenciaContabil", anoMesReferenciaContabil).setInteger(
							"idLocalidade", idLocalidade).list();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0486] Gerar Lançamentos Contábeis dos Devedores Duvidosos
	 * Pesquisa os parâmetros contábil dos devedores duvidosos.
	 * 
	 * @author Pedro Alexandre
	 * @date 21/06/2007
	 * @param idCategoria
	 * @param idItemLancamentoContabil
	 * @param idItemLancamento
	 * @param idTipoLancamento
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Object[] obterParametrosContabilDevedoresDuvidosos(Integer idCategoria, Integer idLancamentoItemContabil,
					Integer idItemLancamento, Integer idTipoLancamento) throws ErroRepositorioException{

		Object[] retorno;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "SELECT ddcp.contaContabilDebito.id,ddcp.contaContabilCredito.id,ddcp.descricaoHistoricoDebito,ddcp.descricaoHistoricoCredito "
							+ "FROM DevedoresDuvidososContabilParametro ddcp "
							+ "LEFT JOIN ddcp.categoria catg "
							+ "LEFT JOIN ddcp.lancamentoItemContabil lict "
							+ "LEFT JOIN ddcp.lancamentoItem lcit "
							+ "LEFT JOIN ddcp.lancamentoTipo lctp "
							+ "WHERE catg.id = :idCategoria AND "
							+ "lcit.id = :idItemLancamento AND " + "lctp.id = :idTipoLancamento ";

			if(idLancamentoItemContabil == null){
				consulta = consulta + "AND lict.id is null ";
			}else{
				consulta = consulta + "AND lict.id = :idLancamentoItemContabil ";
			}

			if(idLancamentoItemContabil == null){
				retorno = (Object[]) session.createQuery(consulta).setInteger("idCategoria", idCategoria).setInteger("idItemLancamento",
								idItemLancamento).setInteger("idTipoLancamento", idTipoLancamento).uniqueResult();
			}else{
				retorno = (Object[]) session.createQuery(consulta).setInteger("idCategoria", idCategoria).setInteger(
								"idLancamentoItemContabil", idLancamentoItemContabil).setInteger("idItemLancamento", idItemLancamento)
								.setInteger("idTipoLancamento", idTipoLancamento).uniqueResult();
			}
		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		return retorno;

	}

	/**
	 * Pesquisa a coleção de ids das localidades para processar os lançamentos
	 * contábeis dos devedores duvidosos.
	 * [UC0485] Gerar Lançamentos Contábeis dos Devedores Duvidosos
	 * 
	 * @author Pedro Alexandre
	 * @date 25/06/2007
	 * @param anoMesReferenciaContabil
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<Integer> pesquisarIdsLocalidadesGerarLancamentosContabeisDevedoresDuvidosos(int anoMesReferenciaContabil)
					throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{

			consulta = "SELECT DISTINCT loca.id " + "FROM ResumoDevedoresDuvidosos rded " + "INNER JOIN rded.localidade loca "
							+ "WHERE rded.anoMesReferenciaContabil = :anoMesReferenciaContabil ";

			retorno = session.createQuery(consulta).setInteger("anoMesReferenciaContabil", anoMesReferenciaContabil).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * Pesquisa uma coleção de ids de lançamentos contábeis por localidade.
	 * 
	 * @author Pedro Alexandre
	 * @date 26/06/2007
	 * @param anoMesReferenciaContabil
	 * @param idLocalidade
	 * @param idLancamentoOrigem
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<Integer> pesquisarIdsLancamentosContabeis(Integer anoMesReferenciaContabil, Integer idLocalidade,
					Integer idLancamentoOrigem) throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{

			consulta = "SELECT lcnb.id " + "FROM LancamentoContabil lcnb " + "INNER JOIN lcnb.localidade loca "
							+ "INNER JOIN lcnb.lancamentoOrigem lcor " + "WHERE lcnb.anoMes = :anoMesReferenciaContabil "
							+ "AND loca.id = :idLocalidade " + "AND lcor.id = :idLancamentoOrigem ";

			retorno = session.createQuery(consulta).setInteger("anoMesReferenciaContabil", anoMesReferenciaContabil).setInteger(
							"idLocalidade", idLocalidade).setInteger("idLancamentoOrigem", idLancamentoOrigem).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * Remove os Itens do lançamento contábil.
	 * 
	 * @author Pedro Alexandre
	 * @date 26/06/2007
	 * @param idLancamentoContabil
	 * @throws ErroRepositorioException
	 */
	public void removerItensLancamentoContabil(Integer idLancamentoContabil) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		try{
			String delete = "delete LancamentoContabilItem lcti " + "where lcti.lancamentoContabil.id = :idLancamentoContabil ";

			session.createQuery(delete).setInteger("idLancamentoContabil", idLancamentoContabil).executeUpdate();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
	}

	/**
	 * Remove os Lançamentos Contábeis.
	 * 
	 * @author Pedro Alexandre
	 * @date 26/06/2007
	 * @param colecaoIdsLancamentosContabeis
	 * @throws ErroRepositorioException
	 */
	public void removerLancamentosContabeis(Collection<Integer> colecaoIdsLancamentosContabeis) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		try{
			String delete = "delete LancamentoContabil lcnb " + "where lcnb.id in (:idsLancamentosContabeis) ";

			session.createQuery(delete).setParameterList("idsLancamentosContabeis", colecaoIdsLancamentosContabeis).executeUpdate();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
	}

	/**
	 * Linha 05 Retorna o valor da categoria acumulado por financiamento por parcelamento serviço,
	 * de acordo com o ano/mês de referência baixa contabil, a localiade, a categoria
	 * [UC0485] - Gerar Resumo dos Devedores Duvidosos
	 * 
	 * @author Pedro Alexandre
	 * @date 13/06/2007
	 * @param anoMesReferenciaBaixaContabil
	 * @param idLocalidade
	 * @param idCategoria
	 * @param colecaoIdsContas
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection acumularValorCategoriaDebitoCobradoCategoriaTipoFinanciamentoParcelamentoServico(int anoMesReferenciaBaixaContabil,
					int idLocalidade, int idCategoria, Collection<Integer> colecaoIdsContas) throws ErroRepositorioException{

		// cria a variável que vai armazenar a coleção pesquisada
		Collection retorno = null;

		// cria uma sessão com o hibernate
		Session session = HibernateUtil.getSession();

		// cria a variável que vai conter o hql
		String consulta;

		try{
			// constroi o hql
			consulta = "select sum(dccg.valorCategoria),lict.sequenciaImpressao,lict.id " + "from DebitoCobradoCategoria dccg "
							+ "inner join dccg.debitoCobrado dbcb " + "inner join dbcb.conta cnta "
							+ "inner join dbcb.lancamentoItemContabil lict " + "inner join dbcb.localidade loca "
							+ "inner join dccg.categoria catg " + "inner join dbcb.financiamentoTipo fntp "
							+ "where cnta.referenciaBaixaContabil = :anoMesReferencia " + "and loca.id = :localidade "
							+ "and catg.id = :categoria " + "and fntp.id = :idTipoFinanciamento " + "and cnta.id in (:idsContas) "
							+ "group by lict.sequenciaImpressao,lict.id ";

			// executa o hql
			retorno = session.createQuery(consulta).setInteger("anoMesReferencia", anoMesReferenciaBaixaContabil).setInteger("localidade",
							idLocalidade).setInteger("categoria", idCategoria).setInteger("idTipoFinanciamento",
							FinanciamentoTipo.PARCELAMENTO_SERVICO).setParameterList("idsContas", colecaoIdsContas).list();

			// erro no hibernate
		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		// retorna a coleção de resumo de faturamento criada
		return retorno;

	}

	// ////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Consulta ResumoDevedoresDuvidosos para a geração do relatório
	 * [UC0487] Gerar Relatório de Resumo de Devedores Duvidosos
	 * de acordo com a opção de totalização.
	 * 
	 * @author Vivianne Sousa
	 * @created 19/07/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection consultarResumoDevedoresDuvidososRelatorioPorEstado(int anoMesReferencia) throws ErroRepositorioException{

		Collection retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta = null;
		try{

			consulta = "select sum(rdd.valorBaixado), " // 0
							+ "lt.descricao, " // 1
							+ "li.descricao, " // 2
							+ "lic.descricao, " // 3
							+ "lt.indicadorImpressao, " // 4
							+ "lt.indicadorTotal, " // 5
							+ "lt.id, " // 6
							+ "lt.lancamentoTipo.id, " // 7
							+ "rdd.categoria.id, " // 8
							+ "rdd.sequencialTipoLancamento, " // 9
							+ "rdd.sequencialItemTipoLancamento " // 10
							+ "from ResumoDevedoresDuvidosos rdd "
							+ "left join rdd.lancamentoTipo lt "
							+ "left join rdd.lancamentoItem li "
							+ "left join rdd.lancamentoItemContabil lic "
							+ "where rdd.anoMesReferenciaContabil = :anoMesReferencia and "
							+ "(rdd.categoria.id = 1 or "
							+ "rdd.categoria.id = 2 or "
							+ "rdd.categoria.id = 3 or "
							+ "rdd.categoria.id = 4) "
							+ "group by lt.descricao,li.descricao,lic.descricao,lt.indicadorImpressao,lt.indicadorTotal,lt.id,lt.lancamentoTipo.id,rdd.categoria.id,rdd.sequencialTipoLancamento,rdd.sequencialItemTipoLancamento  "
							+ "order by rdd.sequencialTipoLancamento,rdd.sequencialItemTipoLancamento,rdd.categoria.id ";

			retorno = session.createQuery(consulta).setInteger("anoMesReferencia", anoMesReferencia).list();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * Consulta ResumoDevedoresDuvidosos para a geração do relatório
	 * [UC0487] Gerar Relatório de Resumo de Devedores Duvidosos
	 * de acordo com a opção de totalização.
	 * 
	 * @author Vivianne Sousa
	 * @created 19/07/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection consultarResumoDevedoresDuvidososRelatorioPorEstadoPorGerenciaRegional(int anoMesReferencia)
					throws ErroRepositorioException{

		Collection retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta = null;
		try{

			consulta = "select sum(rdd.valorBaixado), "// 0
							+ "lt.descricao, "// 1
							+ "li.descricao, "// 2
							+ "lic.descricao, "// 3
							+ "lt.indicadorImpressao, "// 4
							+ "lt.indicadorTotal, "// 5
							+ "lt.id, "// 6
							+ "lt.lancamentoTipo.id, "// 7
							+ "rdd.categoria.id, "// 8
							+ "rdd.gerenciaRegional.nome, "// 9
							+ "rdd.gerenciaRegional.id, "// 10
							+ "rdd.sequencialTipoLancamento, "// 11
							+ "rdd.sequencialItemTipoLancamento "// 12
							+ "from ResumoDevedoresDuvidosos rdd "
							+ "left join rdd.lancamentoTipo lt "
							+ "left join rdd.lancamentoItem li "
							+ "left join rdd.lancamentoItemContabil lic "
							+ "where rdd.anoMesReferenciaContabil = :anoMesReferencia and  "
							+ "(rdd.categoria.id = 1 or rdd.categoria.id = 2 or rdd.categoria.id = 3 or rdd.categoria.id = 4) "
							+ "group by rdd.gerenciaRegional.nome, rdd.gerenciaRegional.id, lt.descricao, li.descricao, "
							+ "lic.descricao, lt.indicadorImpressao,lt.indicadorTotal, lt.id,lt.lancamentoTipo.id, "
							+ "rdd.categoria.id, rdd.sequencialTipoLancamento,rdd.sequencialItemTipoLancamento "
							+ "order by rdd.gerenciaRegional.id, rdd.sequencialTipoLancamento, "
							+ "rdd.sequencialItemTipoLancamento, rdd.categoria.id ";

			retorno = session.createQuery(consulta).setInteger("anoMesReferencia", anoMesReferencia).list();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * Consulta ResumoDevedoresDuvidosos para a geração do relatório
	 * [UC0487] Gerar Relatório de Resumo de Devedores Duvidosos
	 * de acordo com a opção de totalização.
	 * 
	 * @author Vivianne Sousa
	 * @created 19/07/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection consultarResumoDevedoresDuvidososRelatorioEstadoPorUnidadeNegocio(int anoMesReferencia)
					throws ErroRepositorioException{

		Collection retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta = null;
		try{

			consulta = "select sum(rdd.valorBaixado), "// 0
							+ "lt.descricao, "// 1
							+ "li.descricao, "// 2
							+ "lic.descricao, "// 3
							+ "lt.indicadorImpressao, "// 4
							+ "lt.indicadorTotal, "// 5
							+ "lt.id, "// 6
							+ "lt.lancamentoTipo.id, "// 7
							+ "rdd.categoria.id, "// 8
							// + "rdd.unidadeNegocio.nome, "//9
							// + "rdd.unidadeNegocio.id, "//10
							+ "rdd.localidade.unidadeNegocio.nome, "// 9
							+ "rdd.localidade.unidadeNegocio.id, "// 10
							+ "rdd.sequencialTipoLancamento, "// 11
							+ "rdd.sequencialItemTipoLancamento, "// 12
							+ "rdd.gerenciaRegional.nome, "// 13
							+ "rdd.gerenciaRegional.id "// 14
							+ "from ResumoDevedoresDuvidosos rdd "
							+ "left join rdd.lancamentoTipo lt "
							+ "left join rdd.lancamentoItem li "
							+ "left join rdd.lancamentoItemContabil lic "
							+ "where rdd.anoMesReferenciaContabil = :anoMesReferencia and "
							+ "(rdd.categoria.id = 1 or rdd.categoria.id = 2 or rdd.categoria.id = 3 or rdd.categoria.id = 4) "
							+ "group by rdd.localidade.unidadeNegocio.nome,rdd.localidade.unidadeNegocio.id,"
							+ "lt.descricao, li.descricao, lic.descricao, lt.indicadorImpressao, lt.indicadorTotal, lt.id, "
							+ "lt.lancamentoTipo.id, rdd.categoria.id, rdd.sequencialTipoLancamento, rdd.sequencialItemTipoLancamento, "
							+ "rdd.gerenciaRegional.nome, rdd.gerenciaRegional.id "
							+ "order by rdd.gerenciaRegional.nome, rdd.localidade.unidadeNegocio.nome, rdd.sequencialTipoLancamento, "
							+ "rdd.sequencialItemTipoLancamento, rdd.categoria.id ";

			retorno = session.createQuery(consulta).setInteger("anoMesReferencia", anoMesReferencia).list();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * Consulta ResumoDevedoresDuvidosos para a geração do relatório
	 * [UC0487] Gerar Relatório de Resumo de Devedores Duvidosos
	 * de acordo com a opção de totalização.
	 * 
	 * @author Vivianne Sousa
	 * @created 19/07/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection consultarResumoDevedoresDuvidososRelatorioPorEstadoPorLocalidade(int anoMesReferencia)
					throws ErroRepositorioException{

		Collection retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta = null;
		try{

			consulta = "select sum(rdd.valorBaixado), "// 0
							+ "lt.descricao, " // 1
							+ "li.descricao, " // 2
							+ "lic.descricao, " // 3
							+ "lt.indicadorImpressao, "// 4
							+ "lt.indicadorTotal, " // 5
							+ "lt.id, " // 6
							+ "lt.lancamentoTipo.id, "// 7
							+ "rdd.categoria.id, " // 8
							+ "rdd.gerenciaRegional.nome, "// 9
							+ "rdd.gerenciaRegional.id, " // 10
							+ "rdd.localidade.descricao, " // 11
							+ "rdd.localidade.id, " // 12
							+ "rdd.sequencialTipoLancamento, "// 13
							+ "rdd.sequencialItemTipoLancamento, "// 14
							+ "rdd.localidade.unidadeNegocio.nome, " // 15
							+ "rdd.localidade.unidadeNegocio.id " // 16
							+ "from ResumoDevedoresDuvidosos rdd "
							+ "left join rdd.lancamentoTipo lt "
							+ "left join rdd.lancamentoItem li "
							+ "left join rdd.lancamentoItemContabil lic "
							+ "where rdd.anoMesReferenciaContabil = :anoMesReferencia and "
							+ "(rdd.categoria.id = 1 or rdd.categoria.id = 2 or rdd.categoria.id = 3 or rdd.categoria.id = 4) "
							+ "group by rdd.gerenciaRegional.nome, rdd.gerenciaRegional.id, rdd.localidade.descricao, rdd.localidade.id, "
							+ "lt.descricao, li.descricao, lic.descricao, lt.indicadorImpressao, lt.indicadorTotal, lt.id, "
							+ "lt.lancamentoTipo.id, rdd.categoria.id, rdd.sequencialTipoLancamento, "
							+ "rdd.sequencialItemTipoLancamento,rdd.localidade.unidadeNegocio.nome, rdd.localidade.unidadeNegocio.id "
							+ " order by " + " rdd.localidade.id," + " rdd.localidade.unidadeNegocio.id,"
							+ " rdd.gerenciaRegional.id,"
							+ " rdd.sequencialTipoLancamento," + " rdd.sequencialItemTipoLancamento," + " rdd.categoria.id";

			retorno = session.createQuery(consulta).setInteger("anoMesReferencia", anoMesReferencia).list();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * Consulta ResumoDevedoresDuvidosos para a geração do relatório
	 * [UC0487] Gerar Relatório de Resumo de Devedores Duvidosos
	 * de acordo com a opção de totalização.
	 * 
	 * @author Vivianne Sousa
	 * @created 19/07/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection consultarResumoDevedoresDuvidososRelatorioPorGerenciaRegional(int anoMesReferencia, Integer gerenciaRegional)
					throws ErroRepositorioException{

		Collection retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta = null;
		try{

			consulta = "select sum(rdd.valorBaixado), "// 0
							+ "lt.descricao, "// 1
							+ "li.descricao, "// 2
							+ "lic.descricao, "// 3
							+ "lt.indicadorImpressao, "// 4
							+ "lt.indicadorTotal, "// 5
							+ "lt.id, "// 6
							+ "lt.lancamentoTipo.id, "// 7
							+ "rdd.categoria.id, "// 8
							+ "rdd.gerenciaRegional.nome, "// 9
							+ "rdd.gerenciaRegional.id, "// 10
							+ "rdd.sequencialTipoLancamento, "// 11
							+ "rdd.sequencialItemTipoLancamento "// 12
							+ "from ResumoDevedoresDuvidosos rdd "
							+ "left join rdd.lancamentoTipo lt "
							+ "left join rdd.lancamentoItem li "
							+ "left join rdd.lancamentoItemContabil lic "
							+ "where rdd.anoMesReferenciaContabil = :anoMesReferencia and rdd.gerenciaRegional = :gerenciaRegional "
							+ "and (rdd.categoria.id = 1 or rdd.categoria.id = 2 or rdd.categoria.id = 3 or rdd.categoria.id = 4) "
							+ "group by lt.descricao, li.descricao, lic.descricao, lt.indicadorImpressao, "
							+ "lt.indicadorTotal, lt.id, lt.lancamentoTipo.id, rdd.categoria.id, rdd.gerenciaRegional.nome, "
							+ "rdd.gerenciaRegional.id, rdd.sequencialTipoLancamento, rdd.sequencialItemTipoLancamento "
							+ "order by rdd.sequencialTipoLancamento, rdd.sequencialItemTipoLancamento, rdd.categoria.id ";

			retorno = session.createQuery(consulta).setInteger("anoMesReferencia", anoMesReferencia).setInteger("gerenciaRegional",
							gerenciaRegional).list();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * Consulta ResumoDevedoresDuvidosos para a geração do relatório
	 * [UC0487] Gerar Relatório de Resumo de Devedores Duvidosos
	 * de acordo com a opção de totalização.
	 * 
	 * @author Vivianne Sousa
	 * @created 19/07/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection consultarResumoDevedoresDuvidososRelatorioPorGerenciaRegionalPorLocalidade(int anoMesReferencia,
					Integer gerenciaRegional) throws ErroRepositorioException{

		Collection retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta = null;
		try{

			consulta = "select sum(rdd.valorBaixado), "// 0
							+ "lt.descricao, "// 1
							+ "li.descricao, "// 2
							+ "lic.descricao, "// 3
							+ "lt.indicadorImpressao, "// 4
							+ "lt.indicadorTotal, "// 5
							+ "lt.id, "// 6
							+ "lt.lancamentoTipo.id, "// 7
							+ "rdd.categoria.id, "// 8
							+ "rdd.gerenciaRegional.nome, "// 9
							+ "rdd.gerenciaRegional.id, "// 10
							+ "rdd.localidade.descricao, "// 11
							+ "rdd.localidade.id, "// 12
							+ "rdd.sequencialTipoLancamento, "// 13
							+ "rdd.sequencialItemTipoLancamento "// 14
							+ "from ResumoDevedoresDuvidosos rdd "
							+ "left join rdd.lancamentoTipo lt left join rdd.lancamentoItem li "
							+ "left join rdd.lancamentoItemContabil lic "
							+ "where rdd.anoMesReferenciaContabil = :anoMesReferencia and rdd.gerenciaRegional = :gerenciaRegional and "
							+ "(rdd.categoria.id = 1 or rdd.categoria.id = 2 or rdd.categoria.id = 3 or rdd.categoria.id = 4) "
							+ "group by rdd.localidade.descricao, rdd.localidade.id,  lt.descricao, "
							+ "li.descricao, lic.descricao, lt.indicadorImpressao, lt.indicadorTotal, lt.id, "
							+ "lt.lancamentoTipo.id, rdd.categoria.id, rdd.gerenciaRegional.nome, rdd.gerenciaRegional.id, "
							+ "rdd.sequencialTipoLancamento, rdd.sequencialItemTipoLancamento  "
							+ "order by rdd.localidade.id, rdd.sequencialTipoLancamento,  "
							+ "rdd.sequencialItemTipoLancamento, rdd.categoria.id ";

			retorno = session.createQuery(consulta).setInteger("anoMesReferencia", anoMesReferencia).setInteger("gerenciaRegional",
							gerenciaRegional).list();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * Consulta ResumoDevedoresDuvidosos para a geração do relatório
	 * [UC0487] Gerar Relatório de Resumo de Devedores Duvidosos
	 * de acordo com a opção de totalização.
	 * 
	 * @author Vivianne Sousa
	 * @created 19/07/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection consultarResumoDevedoresDuvidososRelatorioPorUnidadeNegocio(int anoMesReferencia, Integer unidadeNegocio)
					throws ErroRepositorioException{

		Collection retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta = null;
		try{

			consulta = "select sum(rdd.valorBaixado), "// 0
							+ "lt.descricao, "// 1
							+ "li.descricao, "// 2
							+ "lic.descricao, "// 3
							+ "lt.indicadorImpressao, "// 4
							+ "lt.indicadorTotal, "// 5
							+ "lt.id, "// 6
							+ "lt.lancamentoTipo.id, "// 7
							+ "rdd.categoria.id, "// 8
							+ "rdd.localidade.unidadeNegocio.nome, "// 9
							+ "rdd.localidade.unidadeNegocio.id, "// 10
							+ "rdd.sequencialTipoLancamento, "// 11
							+ "rdd.sequencialItemTipoLancamento, "// 12
							+ "rdd.gerenciaRegional.nome," // 13
							+ "rdd.gerenciaRegional.id " // 14
							+ "from ResumoDevedoresDuvidosos rdd "
							+ "left join rdd.lancamentoTipo lt "
							+ "left join rdd.lancamentoItem li "
							+ "left join rdd.lancamentoItemContabil lic "
							+ "where rdd.anoMesReferenciaContabil = :anoMesReferencia and "
							+ "rdd.localidade.unidadeNegocio = :unidadeNegocio and "
							+ "(rdd.categoria.id = 1 or rdd.categoria.id = 2 or rdd.categoria.id = 3 or rdd.categoria.id = 4) "
							+ "group by  lt.descricao, li.descricao, lic.descricao, lt.indicadorImpressao, lt.indicadorTotal, lt.id, lt.lancamentoTipo.id, "
							+ "rdd.categoria.id, rdd.localidade.unidadeNegocio.nome, rdd.localidade.unidadeNegocio.id, rdd.sequencialTipoLancamento, rdd.sequencialItemTipoLancamento, rdd.gerenciaRegional.nome, rdd.gerenciaRegional.id  "
							+ "order by rdd.sequencialTipoLancamento, rdd.sequencialItemTipoLancamento, rdd.categoria.id ";

			retorno = session.createQuery(consulta).setInteger("anoMesReferencia", anoMesReferencia).setInteger("unidadeNegocio",
							unidadeNegocio).list();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * Consulta ResumoDevedoresDuvidosos para a geração do relatório
	 * [UC0487] Gerar Relatório de Resumo de Devedores Duvidosos
	 * de acordo com a opção de totalização.
	 * 
	 * @author Vivianne Sousa
	 * @created 19/07/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection consultarResumoDevedoresDuvidososRelatorioPorLocalidade(int anoMesReferencia, Integer localidade)
					throws ErroRepositorioException{

		Collection retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta = null;
		try{

			consulta = "select sum(rdd.valorBaixado), "// 0
							+ "lt.descricao, "// 1
							+ "li.descricao, "// 2
							+ "lic.descricao, "// 3
							+ "lt.indicadorImpressao, "// 4
							+ "lt.indicadorTotal, "// 5
							+ "lt.id, "// 6
							+ "lt.lancamentoTipo.id, "// 7
							+ "rdd.categoria.id, "// 8
							+ "rdd.localidade.descricao, "// 9
							+ "rdd.localidade.id, "// 10
							+ "rdd.sequencialTipoLancamento, "// 11
							+ "rdd.sequencialItemTipoLancamento "// 12
							+ "from ResumoDevedoresDuvidosos rdd "
							+ "left join rdd.lancamentoTipo lt "
							+ "left join rdd.lancamentoItem li "
							+ "left join rdd.lancamentoItemContabil lic "
							+ "where rdd.anoMesReferenciaContabil = :anoMesReferencia and "
							+ "rdd.localidade = :localidade and "
							+ "(rdd.categoria.id = 1 or rdd.categoria.id = 2 or rdd.categoria.id = 3 or rdd.categoria.id = 4) "
							+ "group by  lt.descricao, li.descricao, lic.descricao, lt.indicadorImpressao, lt.indicadorTotal, lt.id, lt.lancamentoTipo.id, "
							+ "rdd.categoria.id, rdd.localidade.descricao, rdd.localidade.id, rdd.sequencialTipoLancamento, rdd.sequencialItemTipoLancamento "
							+ "order by rdd.sequencialTipoLancamento, rdd.sequencialItemTipoLancamento, rdd.categoria.id ";

			retorno = session.createQuery(consulta).setInteger("anoMesReferencia", anoMesReferencia).setInteger("localidade", localidade)
							.list();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * [UC0714] - Gerar Contas a Receber Contábil
	 * Remove as contas a receber contábil
	 * 
	 * @author Rafael Corrêa
	 * @date 08/11/2007
	 * @param idLocalidade
	 * @param anoMesReferenciaContabil
	 *            Ano e mês de referência contabil
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public void removerContasAReceberContabil(int anoMesReferenciaContabil, Integer idLocalidade) throws ErroRepositorioException{

		// cria uma sessão com o hibernate
		Session session = HibernateUtil.getSession();

		// cria a variável que vai conter o hql
		String consulta;

		try{
			// constroi o hql
			consulta = "DELETE FROM ContaAReceberContabil contaAReceberContabil "
							+ " WHERE contaAReceberContabil.localidade.id = :idLocalidade "
							+ " and contaAReceberContabil.anoMesReferencia = :anoMesReferencia";

			session.createQuery(consulta).setInteger("idLocalidade", idLocalidade).setInteger("anoMesReferencia", anoMesReferenciaContabil)
							.executeUpdate();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

	}

	/**
	 * [UC0714] - Gerar Contas a Receber Contábil
	 * Acumula os valores de água e esgoto pela gerência, localidade e categoria
	 * 
	 * @author Rafael Corrêa
	 * @date 08/11/2007
	 * @param idLocalidade
	 * @param anoMesReferenciaContabil
	 *            Ano e mês de referência contabil
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public Collection<Object[]> pesquisarDadosContasCategoriaValorAguaEsgoto(int anoMesReferenciaContabil, Integer idLocalidade)
					throws ErroRepositorioException{

		Collection<Object[]> retorno = null;

		// cria uma sessão com o hibernate
		Session session = HibernateUtil.getSession();

		// cria a variável que vai conter o hql
		String consulta;

		try{

			consulta = "select loca.greg_id as idGerencia, "
							+ "loca.uneg_id as idUnidadeNegocio, "
							+ "loca.loca_id as idLocalidade, "
							+ "contaCat.catg_id as idCategoria, "
							+ "sum(contaCat.ctcg_vlagua) as valorAgua, "
							+ "sum(contaCat.ctcg_vlesgoto) as valorEsgoto "
							+ "from conta_categoria contaCat inner join conta conta on (conta.cnta_id = contaCat.cnta_id) "
							+ "inner join localidade loca on (loca.loca_id = conta.loca_id) "
							+ "WHERE loca.loca_id = :idLocalidade and conta.cnta_amreferenciaconta <= :anoMesReferenciaContabil and conta.dcst_idatual in ( :situacaoNormal, :situacaoIncluida, :situacaoRetificada, :situacaoEntradaParc ) "
							+ "GROUP BY loca.greg_id, loca.uneg_id, loca.loca_id, contaCat.catg_id "
							+ "UNION ALL "
							+ "select loca.greg_id as idGerencia, "
							+ "loca.uneg_id as idUnidadeNegocio, "
							+ "loca.loca_id as idLocalidade, "
							+ "contaCat.catg_id as idCategoria, "
							+ "sum(contacat.ctch_vlagua) as valorAgua, "
							+ "sum(contaCat.ctch_vlesgoto) as valorEsgoto "
							+ "from conta_categoria_historico contaCat "
							+ "INNER JOIN conta_historico conta on (conta.cnta_id = contaCat.cnta_id) "
							+ "INNER JOIN localidade loca on (loca.loca_id = conta.loca_id)	"
							+ "WHERE loca.loca_id = :idLocalidade and conta.cnhi_amreferenciaconta <= :anoMesReferenciaContabil and conta.dcst_idatual in ( :situacaoNormal, :situacaoIncluida, :situacaoRetificada, :situacaoEntradaParc ) and (select min(pagto.pghi_amreferenciaarrecadacao) "
							+ "from pagamento_historico pagto where pagto.imov_id = conta.imov_id and pagto.pghi_amreferenciapagamento = conta.cnhi_amreferenciaconta) > :anoMesReferenciaContabil "
							+ "GROUP BY loca.greg_id, loca.uneg_id, loca.loca_id, contaCat.catg_id "
							+ "ORDER BY idGerencia, idUnidadeNegocio, idLocalidade, idCategoria";

			retorno = session.createSQLQuery(consulta).addScalar("idGerencia", Hibernate.INTEGER).addScalar("idUnidadeNegocio",
							Hibernate.INTEGER).addScalar("idLocalidade", Hibernate.INTEGER).addScalar("idCategoria", Hibernate.INTEGER)
							.addScalar("valorAgua", Hibernate.BIG_DECIMAL).addScalar("valorEsgoto", Hibernate.BIG_DECIMAL).setInteger(
											"situacaoNormal", DebitoCreditoSituacao.NORMAL).setInteger("situacaoIncluida",
											DebitoCreditoSituacao.INCLUIDA).setInteger("situacaoRetificada",
											DebitoCreditoSituacao.RETIFICADA).setInteger("situacaoEntradaParc",
											DebitoCreditoSituacao.ENTRADA_DE_PARCELAMENTO).setInteger("anoMesReferenciaContabil",
											anoMesReferenciaContabil).setInteger("idLocalidade", idLocalidade).list();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0714] - Gerar Contas a Receber Contábil
	 * Acumula os valores dos débitos cobrados para serviços pela gerência,
	 * localidade e categoria
	 * 
	 * @author Rafael Corrêa
	 * @date 08/11/2007
	 * @param idLocalidade
	 * @param anoMesReferenciaContabil
	 *            Ano e mês de referência contabil
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public Collection<Object[]> pesquisarDadosDebitosCobradosCategoriaServico(int anoMesReferenciaContabil, Integer idLocalidade)
					throws ErroRepositorioException{

		Collection<Object[]> retorno = null;

		// cria uma sessão com o hibernate
		Session session = HibernateUtil.getSession();

		// cria a variável que vai conter o hql
		String consulta;

		try{

			consulta = "SELECT loca.greg_id as idGerencia, loca.uneg_id as idUnidadeNegocio, loca.loca_id as idLocalidade, debCobCat.catg_id as idCategoria, sum(debCobCat.dccg_vlcategoria) as valorCategoria "
							+ "FROM conta conta "
							+ "INNER JOIN localidade loca on (loca.loca_id = conta.loca_id) "
							+ "INNER JOIN debito_cobrado debCob on (debCob.cnta_id = conta.cnta_id) "
							+ "INNER JOIN debito_cobrado_categoria debCobCat on (debCobCat.dbcb_id = debCob.dbcb_id) "
							+ "WHERE loca.loca_id = :idLocalidade "
							+ "and conta.cnta_amreferenciaconta <= :anoMesReferenciaContabil "
							+ "and conta.dcst_idatual in ( :situacaoNormal, :situacaoIncluida, :situacaoRetificada, :situacaoEntradaParc ) "
							+ "and ( debCob.fntp_id = :servico ) "
							+ "group by loca.greg_id, loca.uneg_id, loca.loca_id, debCobCat.catg_id "
							+ "UNION ALL "
							+ "SELECT loca.greg_id as idGerencia, loca.uneg_id as idUnidadeNegocio, loca.loca_id as idLocalidade, debCobCat.catg_id as idCategoria, sum(debCobCat.dcch_vlcategoria) as valorCategoria "
							+ "FROM conta_historico conta "
							+ "INNER JOIN localidade loca on (loca.loca_id = conta.loca_id) "
							+ "INNER JOIN debito_cobrado_historico debCob on (debCob.cnta_id = conta.cnta_id) "
							+ "INNER JOIN debito_cobrado_categoria_hist debCobCat on (debCobCat.dbhi_id = debCob.dbhi_id) "
							+ "WHERE loca.loca_id = :idLocalidade "
							+ "and conta.cnhi_amreferenciaconta <= :anoMesReferenciaContabil "
							+ "and conta.dcst_idatual in ( :situacaoNormal, :situacaoIncluida, :situacaoRetificada, :situacaoEntradaParc ) "
							+ "and (select min(pagto.pghi_amreferenciaarrecadacao) from pagamento_historico pagto "
							+ "where pagto.imov_id = conta.imov_id and "
							+ "pagto.pghi_amreferenciapagamento = conta.cnhi_amreferenciaconta) > :anoMesReferenciaContabil "
							+ "and ( debCob.fntp_id = :servico ) "
							+ "group by loca.greg_id, loca.uneg_id, loca.loca_id, debCobCat.catg_id "
							+ "ORDER BY idGerencia, idUnidadeNegocio, idLocalidade, idCategoria";

			// executa o sql
			retorno = session.createSQLQuery(consulta).addScalar("idGerencia", Hibernate.INTEGER).addScalar("idUnidadeNegocio",
							Hibernate.INTEGER).addScalar("idLocalidade", Hibernate.INTEGER).addScalar("idCategoria", Hibernate.INTEGER)
							.addScalar("valorCategoria", Hibernate.BIG_DECIMAL).setInteger("idLocalidade", idLocalidade).setInteger(
											"anoMesReferenciaContabil", anoMesReferenciaContabil).setInteger("situacaoNormal",
											DebitoCreditoSituacao.NORMAL).setInteger("situacaoIncluida", DebitoCreditoSituacao.INCLUIDA)
							.setInteger("situacaoRetificada", DebitoCreditoSituacao.RETIFICADA).setInteger("situacaoEntradaParc",
											DebitoCreditoSituacao.ENTRADA_DE_PARCELAMENTO).setInteger("servico",
											FinanciamentoTipo.SERVICO_NORMAL).list();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0714] - Gerar Contas a Receber Contábil
	 * Acumula os valores dos débitos cobrados para parcelamentos pela gerência,
	 * localidade e categoria
	 * 
	 * @author Rafael Corrêa
	 * @date 08/11/2007
	 * @param idLocalidade
	 * @param anoMesReferenciaContabil
	 *            Ano e mês de referência contabil
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public Collection<Object[]> pesquisarDadosDebitosCobradosCategoriaParcelamento(int anoMesReferenciaContabil, Integer idLocalidade,
					Collection<Integer> tiposParcelamento) throws ErroRepositorioException{

		Collection<Object[]> retorno = null;

		// cria uma sessão com o hibernate
		Session session = HibernateUtil.getSession();

		// cria a variável que vai conter o hql
		String consulta;

		try{
			// constroi o sql
			consulta = "SELECT loca.greg_id as idGerencia, loca.uneg_id as idUnidadeNegocio, loca.loca_id as idLocalidade, debCobCat.catg_id as idCategoria, sum(debCobCat.dccg_vlcategoria) as valorCategoria "
							+ "FROM conta conta "
							+ "INNER JOIN localidade loca on (loca.loca_id = conta.loca_id) "
							+ "INNER JOIN debito_cobrado debCob on (debCob.cnta_id = conta.cnta_id) "
							+ "INNER JOIN debito_cobrado_categoria debCobCat on (debCobCat.dbcb_id = debCob.dbcb_id) "
							+ "WHERE loca.loca_id = :idLocalidade "
							+ "and conta.cnta_amreferenciaconta <= :anoMesReferenciaContabil "
							+ "and conta.dcst_idatual in ( :situacaoNormal, :situacaoIncluida, :situacaoRetificada, :situacaoEntradaParc ) "
							+ "and debCob.fntp_id in (:parcelamentoAgua, :parcelamentoEsgoto, :parcelamentoServico, :arrastoAgua, :arrastoEsgoto, :arrastoServico, :jurosParcelamento, :entratradaParcelamento, :parcExerc, :parcExercAnte, :parcPrefeitura, :arrastoExerc, :arrastoExercAnt) "
							+ "group by loca.greg_id, loca.uneg_id, loca.loca_id, debCobCat.catg_id "
							+ "UNION ALL "
							+ "SELECT loca.greg_id as idGerencia, loca.uneg_id as idUnidadeNegocio, loca.loca_id as idLocalidade, debCobCat.catg_id as idCategoria, sum(debCobCat.dcch_vlcategoria) as valorCategoria "
							+ "FROM conta_historico conta "
							+ "INNER JOIN localidade loca on (loca.loca_id = conta.loca_id) "
							+ "INNER JOIN debito_cobrado_historico debCob on (debCob.cnta_id = conta.cnta_id) "
							+ "INNER JOIN debito_cobrado_categoria_hist debCobCat on (debCobCat.dbhi_id = debCob.dbhi_id) "
							+ "WHERE loca.loca_id = :idLocalidade "
							+ "and conta.cnhi_amreferenciaconta <= :anoMesReferenciaContabil "
							+ "and conta.dcst_idatual in ( :situacaoNormal, :situacaoIncluida, :situacaoRetificada, :situacaoEntradaParc ) "
							+ "and (select min(pagto.pghi_amreferenciaarrecadacao) from pagamento_historico pagto "
							+ "where pagto.imov_id = conta.imov_id and "
							+ "pagto.pghi_amreferenciapagamento = conta.cnhi_amreferenciaconta) > :anoMesReferenciaContabil "
							+ "and debCob.fntp_id in (:parcelamentoAgua, :parcelamentoEsgoto, :parcelamentoServico, :arrastoAgua, :arrastoEsgoto, :arrastoServico, :jurosParcelamento, :entratradaParcelamento, :parcExerc, :parcExercAnte, :parcPrefeitura, :arrastoExerc, :arrastoExercAnt)  "
							+ "group by loca.greg_id, loca.uneg_id, loca.loca_id, debCobCat.catg_id "
							+ "ORDER BY idGerencia, idUnidadeNegocio, idLocalidade, idCategoria";

			// executa o sql
			retorno = session.createSQLQuery(consulta).addScalar("idGerencia", Hibernate.INTEGER).addScalar("idUnidadeNegocio",
							Hibernate.INTEGER).addScalar("idLocalidade", Hibernate.INTEGER).addScalar("idCategoria", Hibernate.INTEGER)
							.addScalar("valorCategoria", Hibernate.BIG_DECIMAL).setInteger("idLocalidade", idLocalidade).setInteger(
											"anoMesReferenciaContabil", anoMesReferenciaContabil).setInteger("situacaoNormal",
											DebitoCreditoSituacao.NORMAL).setInteger("situacaoIncluida", DebitoCreditoSituacao.INCLUIDA)
							.setInteger("situacaoRetificada", DebitoCreditoSituacao.RETIFICADA).setInteger("situacaoEntradaParc",
											DebitoCreditoSituacao.ENTRADA_DE_PARCELAMENTO).setInteger("parcelamentoAgua",
											FinanciamentoTipo.PARCELAMENTO_AGUA).setInteger("parcelamentoEsgoto",
											FinanciamentoTipo.PARCELAMENTO_ESGOTO).setInteger("parcelamentoServico",
											FinanciamentoTipo.PARCELAMENTO_SERVICO).setInteger("arrastoAgua",
											FinanciamentoTipo.ARRASTO_AGUA).setInteger("arrastoEsgoto", FinanciamentoTipo.ARRASTO_ESGOTO)
							.setInteger("arrastoServico", FinanciamentoTipo.ARRASTO_SERVICO).setInteger("jurosParcelamento",
											FinanciamentoTipo.JUROS_PARCELAMENTO).setInteger("entratradaParcelamento",
											FinanciamentoTipo.ENTRADA_PARCELAMENTO).setInteger("parcExerc",
											FinanciamentoTipo.PARCELAMENTO_EXERCICIO).setInteger("parcExercAnte",
											FinanciamentoTipo.PARCEL_EXERC_ANTERIOR).setInteger("parcPrefeitura",
											FinanciamentoTipo.PARCELAMENTO_PREFEITURAS).setInteger("arrastoExerc",
											FinanciamentoTipo.ARRASTO_DO_EXERCICIO).setInteger("arrastoExercAnt",
											FinanciamentoTipo.ARRASTO_EXERC_ANTERIOR).list();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0714] - Gerar Contas a Receber Contábil
	 * Acumula os valores das guias de pagamento para entradas de parcelamento
	 * pela gerência, localidade e categoria
	 * 
	 * @author Rafael Corrêa
	 * @date 08/11/2007
	 * @param idLocalidade
	 * @param anoMesReferenciaContabil
	 *            Ano e mês de referência contabil
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public Collection<Object[]> pesquisarDadosGuiasPagamentoCategoriaEntradaParcelamento(int anoMesReferenciaContabil, Integer idLocalidade)
					throws ErroRepositorioException{

		Collection<Object[]> retorno = null;

		Collection<Integer> financiamentosTipos = new ArrayList<Integer>();
		financiamentosTipos.add(FinanciamentoTipo.PARCELAMENTO_AGUA);
		financiamentosTipos.add(FinanciamentoTipo.PARCELAMENTO_ESGOTO);
		financiamentosTipos.add(FinanciamentoTipo.PARCELAMENTO_SERVICO);
		financiamentosTipos.add(FinanciamentoTipo.JUROS_PARCELAMENTO);
		financiamentosTipos.add(FinanciamentoTipo.ENTRADA_PARCELAMENTO);
		financiamentosTipos.add(FinanciamentoTipo.PARCELAMENTO_AGUA_PDD);
		financiamentosTipos.add(FinanciamentoTipo.PARCELAMENTO_ESGOTO_PDD);
		financiamentosTipos.add(FinanciamentoTipo.PARCELAMENTO_SERVICO_PDD);
		financiamentosTipos.add(FinanciamentoTipo.PARCELAMENTO_EXERCICIO);
		financiamentosTipos.add(FinanciamentoTipo.PARCEL_EXERC_ANTERIOR);
		financiamentosTipos.add(FinanciamentoTipo.PARCELAMENTO_PREFEITURAS);

		// cria uma sessão com o hibernate
		Session session = HibernateUtil.getSession();

		// cria a variável que vai conter o hql
		String consulta;

		try{

			consulta = "SELECT loca.greg_id as idGerencia, loca.uneg_id as idUnidadeNegocio, loca.loca_id as idLocalidade, gpagCat.catg_id as idCategoria, sum(gpagCat.gpcg_vlcategoria) as valorCategoria "
							+ "FROM guia_pagamento_categoria gpagCat "
							+ "INNER JOIN guia_pagamento_prestacao gpagp on (gpagp.gpag_id = gpagCat.gpag_id and gpagp.gppr_nnprestacao = gpagcat.gpcg_nnprestacao ) "
							+ "INNER JOIN guia_pagamento gpag on (gpagp.gpag_id = gpag.gpag_id) "
							+ "INNER JOIN localidade loca on (loca.loca_id = gpag.loca_id) "
							+ "WHERE loca.loca_id = :idLocalidade "
							+ "and gpagp.gppr_amreferenciafaturamento <= :anoMesReferenciaContabil "
							+ "and gpagp.dcst_id in ( :situacaoNormal, :situacaoIncluida, :situacaoRetificada, :situacaoEntradaParc ) "
							+ "and gpagp.fntp_id in (:financiamentosTipos)  "
							+ "GROUP BY loca.greg_id, loca.uneg_id, loca.loca_id, gpagCat.catg_id "
							+ "UNION ALL "
							+ "SELECT loca.greg_id as idGerencia, loca.uneg_id as idUnidadeNegocio, loca.loca_id as idLocalidade, gpagCat.catg_id as idCategoria, sum(gpagCat.gpch_vlcategoria) as valorCategoria "
							+ "FROM guia_pagamento_categoria_hist gpagCat "
							+ "INNER JOIN guia_pagamento_prestacao_hist gpagp on (gpagp.gpag_id = gpagCat.gpag_id and gpagp.gpph_nnprestacao = gpagcat.gpch_nnprestacao ) "
							+ "INNER JOIN guia_pagamento_historico gpag on (gpagp.gpag_id = gpag.gpag_id) "
							+ "INNER JOIN localidade loca on (loca.loca_id = gpag.loca_id) "
							+ "WHERE loca.loca_id = :idLocalidade "
							+ "and gpagp.gpph_amreferenciafaturamento <= :anoMesReferenciaContabil "
							+ "and gpagp.gpph_amreferenciaarrecadacao > :anoMesReferenciaContabil "
							+ "and gpagp.dcst_id in ( :situacaoNormal, :situacaoIncluida, :situacaoRetificada, :situacaoEntradaParc ) "
							+ "and gpagp.fntp_id in (:financiamentosTipos) "
							+ "GROUP BY loca.greg_id, loca.uneg_id, loca.loca_id, gpagCat.catg_id "
							+ "ORDER BY idGerencia, idUnidadeNegocio, idLocalidade, idCategoria";

			// executa o sql
			retorno = session.createSQLQuery(consulta).addScalar("idGerencia", Hibernate.INTEGER).addScalar("idUnidadeNegocio",
							Hibernate.INTEGER).addScalar("idLocalidade", Hibernate.INTEGER).addScalar("idCategoria", Hibernate.INTEGER)
							.addScalar("valorCategoria", Hibernate.BIG_DECIMAL).setInteger("idLocalidade", idLocalidade).setInteger(
											"anoMesReferenciaContabil", anoMesReferenciaContabil).setInteger("situacaoNormal",
											DebitoCreditoSituacao.NORMAL).setInteger("situacaoIncluida", DebitoCreditoSituacao.INCLUIDA)
							.setInteger("situacaoRetificada", DebitoCreditoSituacao.RETIFICADA).setInteger("situacaoEntradaParc",
											DebitoCreditoSituacao.ENTRADA_DE_PARCELAMENTO).setParameterList("financiamentosTipos",
											financiamentosTipos).list();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0714] - Gerar Contas a Receber Contábil
	 * Acumula os valores das guias de pagamento para serviços pela gerência,
	 * localidade e categoria
	 * 
	 * @author Rafael Corrêa
	 * @date 08/11/2007
	 * @param idLocalidade
	 * @param anoMesReferenciaContabil
	 *            Ano e mês de referência contabil
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public Collection<Object[]> pesquisarDadosGuiasPagamentoCategoriaServico(int anoMesReferenciaContabil, Integer idLocalidade)
					throws ErroRepositorioException{

		Collection<Object[]> retorno = null;

		// cria uma sessão com o hibernate
		Session session = HibernateUtil.getSession();

		// cria a variável que vai conter o hql
		String consulta;

		try{

			consulta = "SELECT loca.greg_id as idGerencia, loca.uneg_id as idUnidadeNegocio, loca.loca_id as idLocalidade, gpagCat.catg_id as idCategoria, sum(gpagCat.gpcg_vlcategoria) as valorCategoria "
							+ "FROM guia_pagamento_categoria gpagCat "
							+ "INNER JOIN guia_pagamento_prestacao gpagp on (gpagp.gpag_id = gpagCat.gpag_id and gpagp.gppr_nnprestacao = gpagcat.gpcg_nnprestacao ) "
							+ "INNER JOIN guia_pagamento gpag on (gpagp.gpag_id = gpag.gpag_id) "
							+ "INNER JOIN localidade loca on (loca.loca_id = gpag.loca_id) "
							+ "WHERE loca.loca_id = :idLocalidade "
							+ "and gpagp.gppr_amreferenciafaturamento <= :anoMesReferenciaContabil "
							+ "and gpagp.dcst_id in ( :situacaoNormal, :situacaoIncluida, :situacaoRetificada, :situacaoEntradaParc ) "
							+ "and ( gpagp.fntp_id = :servico ) "
							+ "GROUP BY loca.greg_id, loca.uneg_id, loca.loca_id, gpagCat.catg_id "
							+ "UNION ALL "
							+ "SELECT loca.greg_id as idGerencia, loca.uneg_id as idUnidadeNegocio, loca.loca_id as idLocalidade, gpagCat.catg_id as idCategoria, sum(gpagCat.gpch_vlcategoria) as valorCategoria "
							+ "FROM guia_pagamento_categoria_hist gpagCat "
							+ "INNER JOIN guia_pagamento_prestacao_hist gpagp on (gpagp.gpag_id = gpagCat.gpag_id and gpagp.gpph_nnprestacao = gpagcat.gpch_nnprestacao ) "
							+ "INNER JOIN guia_pagamento_historico gpag on (gpagp.gpag_id = gpag.gpag_id) "
							+ "INNER JOIN localidade loca on (loca.loca_id = gpag.loca_id) "
							+ "WHERE loca.loca_id = :idLocalidade "
							+ "and gpagp.gpph_amreferenciafaturamento <= :anoMesReferenciaContabil "
							+ "and gpagp.gpph_amreferenciaarrecadacao > :anoMesReferenciaContabil "
							+ "and gpagp.dcst_id in ( :situacaoNormal, :situacaoIncluida, :situacaoRetificada, :situacaoEntradaParc ) "
							+ "and ( gpagp.fntp_id = :servico ) "
							+ "GROUP BY loca.greg_id, loca.uneg_id, loca.loca_id, gpagCat.catg_id "
							+ "ORDER BY idGerencia, idUnidadeNegocio, idLocalidade, idCategoria";

			// executa o sql
			retorno = session.createSQLQuery(consulta).addScalar("idGerencia", Hibernate.INTEGER).addScalar("idUnidadeNegocio",
							Hibernate.INTEGER).addScalar("idLocalidade", Hibernate.INTEGER).addScalar("idCategoria", Hibernate.INTEGER)
							.addScalar("valorCategoria", Hibernate.BIG_DECIMAL).setInteger("idLocalidade", idLocalidade).setInteger(
											"anoMesReferenciaContabil", anoMesReferenciaContabil).setInteger("situacaoNormal",
											DebitoCreditoSituacao.NORMAL).setInteger("situacaoIncluida", DebitoCreditoSituacao.INCLUIDA)
							.setInteger("situacaoRetificada", DebitoCreditoSituacao.RETIFICADA).setInteger("situacaoEntradaParc",
											DebitoCreditoSituacao.ENTRADA_DE_PARCELAMENTO).setInteger("servico",
											FinanciamentoTipo.SERVICO_NORMAL).list();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0714] - Gerar Contas a Receber Contábil
	 * Acumula os valores dos créditos realizados para pagamentos em duplicidade
	 * ou em excesso pela gerência, localidade e categoria
	 * 
	 * @author Rafael Corrêa
	 * @date 08/11/2007
	 * @param idLocalidade
	 * @param anoMesReferenciaContabil
	 *            Ano e mês de referência contabil
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public Collection<Object[]> pesquisarDadosCreditosRealizadosCategoriaPagamentoExcesso(int anoMesReferenciaContabil, Integer idLocalidade)
					throws ErroRepositorioException{

		Collection<Object[]> retorno = null;

		// cria uma sessão com o hibernate
		Session session = HibernateUtil.getSession();

		// cria a variável que vai conter o hql
		String consulta;

		try{

			consulta = "SELECT loca.greg_id as idGerencia, loca.uneg_id as idUnidadeNegocio, loca.loca_id as idLocalidade, credRealizadoCat.catg_id as idCategoria, sum(credRealizadoCat.crcg_vlcategoria) as valorCategoria "
							+ "FROM conta conta "
							+ "INNER JOIN localidade loca on loca.loca_id = conta.loca_id "
							+ "INNER JOIN credito_realizado credRealizado on credRealizado.cnta_id = conta.cnta_id "
							+ "INNER JOIN credito_realizado_categoria credRealizadoCat on credRealizadoCat.crrz_id = credRealizado.crrz_id "
							+ "WHERE loca.loca_id = :idLocalidade "
							+ "and conta.cnta_amreferenciaconta <= :anoMesReferenciaContabil "
							+ "and conta.dcst_idatual in ( :situacaoNormal, :situacaoIncluida, :situacaoRetificada, :situacaoEntradaParc ) "
							+ "and ( credRealizado.crog_id = :pagamentoEmExcesso ) "
							+ "group by loca.greg_id, loca.uneg_id, loca.loca_id, credRealizadoCat.catg_id "
							+ "UNION ALL "
							+ "SELECT loca.greg_id as idGerencia, loca.uneg_id as idUnidadeNegocio, loca.loca_id as idLocalidade, credRealizadoCat.catg_id as idCategoria, sum(credRealizadoCat.crch_vlcategoria) as valorCategoria "
							+ "FROM conta_historico conta "
							+ "INNER JOIN localidade loca on loca.loca_id = conta.loca_id "
							+ "INNER JOIN credito_realizado_historico credRealizado on credRealizado.cnta_id = conta.cnta_id "
							+ "INNER JOIN credito_realizado_catg_hist credRealizadoCat on credRealizadoCat.crhi_id = credRealizado.crhi_id "
							+ "WHERE loca.loca_id = :idLocalidade "
							+ "and conta.cnhi_amreferenciaconta <= :anoMesReferenciaContabil "
							+ "and conta.dcst_idatual in ( :situacaoNormal, :situacaoIncluida, :situacaoRetificada, :situacaoEntradaParc ) "
							+ "and (select min(pagto.pghi_amreferenciaarrecadacao) from pagamento_historico pagto where pagto.imov_id = conta.imov_id and pagto.pghi_amreferenciapagamento = conta.cnhi_amreferenciaconta) > :anoMesReferenciaContabil "
							+ "and ( credRealizado.crog_id = :pagamentoEmExcesso ) "
							+ "group by loca.greg_id, loca.uneg_id, loca.loca_id, credRealizadoCat.catg_id "
							+ "ORDER BY idGerencia, idUnidadeNegocio, idLocalidade, idCategoria ";

			// executa o sql
			retorno = session.createSQLQuery(consulta).addScalar("idGerencia", Hibernate.INTEGER).addScalar("idUnidadeNegocio",
							Hibernate.INTEGER).addScalar("idLocalidade", Hibernate.INTEGER).addScalar("idCategoria", Hibernate.INTEGER)
							.addScalar("valorCategoria", Hibernate.BIG_DECIMAL).setInteger("idLocalidade", idLocalidade).setInteger(
											"anoMesReferenciaContabil", anoMesReferenciaContabil).setInteger("situacaoNormal",
											DebitoCreditoSituacao.NORMAL).setInteger("situacaoIncluida", DebitoCreditoSituacao.INCLUIDA)
							.setInteger("situacaoRetificada", DebitoCreditoSituacao.RETIFICADA).setInteger("situacaoEntradaParc",
											DebitoCreditoSituacao.ENTRADA_DE_PARCELAMENTO).setInteger("pagamentoEmExcesso",
											CreditoOrigem.CONTAS_PAGAS_EM_DUPLICIDADE_EXCESSO).list();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0714] - Gerar Contas a Receber Contábil
	 * Acumula os valores dos créditos realizados para descontos no parcelamento
	 * pela gerência, localidade e categoria
	 * 
	 * @author Rafael Corrêa
	 * @date 08/11/2007
	 * @param idLocalidade
	 * @param anoMesReferenciaContabil
	 *            Ano e mês de referência contabil
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public Collection<Object[]> pesquisarDadosCreditosRealizadosCategoriaDescontoParcelamento(int anoMesReferenciaContabil,
					Integer idLocalidade) throws ErroRepositorioException{

		Collection<Object[]> retorno = null;

		// cria uma sessão com o hibernate
		Session session = HibernateUtil.getSession();

		// cria a variável que vai conter o hql
		String consulta;

		try{

			consulta = "SELECT loca.greg_id as idGerencia, loca.uneg_id as idUnidadeNegocio, loca.loca_id as idLocalidade, credRealizadoCat.catg_id as idCategoria, sum(credRealizadoCat.crcg_vlcategoria) as valorCategoria "
							+ "FROM conta conta "
							+ "INNER JOIN localidade loca on loca.loca_id = conta.loca_id "
							+ "INNER JOIN credito_realizado credRealizado on credRealizado.cnta_id = conta.cnta_id "
							+ "INNER JOIN credito_realizado_categoria credRealizadoCat on credRealizadoCat.crrz_id = credRealizado.crrz_id "
							+ "WHERE loca.loca_id = :idLocalidade "
							+ "and conta.cnta_amreferenciaconta <= :anoMesReferenciaContabil "
							+ "and conta.dcst_idatual in ( :situacaoNormal, :situacaoIncluida, :situacaoRetificada, :situacaoEntradaParc ) "
							+ "and ( credRealizado.crog_id = :descontoParcelamento ) "
							+ "group by loca.greg_id, loca.uneg_id, loca.loca_id, credRealizadoCat.catg_id "
							+ "UNION ALL "
							+ "SELECT loca.greg_id as idGerencia, loca.uneg_id as idUnidadeNegocio, loca.loca_id as idLocalidade, credRealizadoCat.catg_id as idCategoria, sum(credRealizadoCat.crch_vlcategoria) as valorCategoria "
							+ "FROM conta_historico conta "
							+ "INNER JOIN localidade loca on loca.loca_id = conta.loca_id "
							+ "INNER JOIN credito_realizado_historico credRealizado on credRealizado.cnta_id = conta.cnta_id "
							+ "INNER JOIN credito_realizado_catg_hist credRealizadoCat on credRealizadoCat.crhi_id = credRealizado.crhi_id "
							+ "WHERE loca.loca_id = :idLocalidade "
							+ "and conta.cnhi_amreferenciaconta <= :anoMesReferenciaContabil "
							+ "and conta.dcst_idatual in ( :situacaoNormal, :situacaoIncluida, :situacaoRetificada, :situacaoEntradaParc ) "
							+ "and (select min(pagto.pghi_amreferenciaarrecadacao) from pagamento_historico pagto where pagto.imov_id = conta.imov_id and pagto.pghi_amreferenciapagamento = conta.cnhi_amreferenciaconta) > :anoMesReferenciaContabil "
							+ "and ( credRealizado.crog_id = :descontoParcelamento ) "
							+ "group by loca.greg_id, loca.uneg_id, loca.loca_id, credRealizadoCat.catg_id "
							+ "ORDER BY idGerencia, idUnidadeNegocio, idLocalidade, idCategoria ";

			// executa o sql
			retorno = session.createSQLQuery(consulta).addScalar("idGerencia", Hibernate.INTEGER).addScalar("idUnidadeNegocio",
							Hibernate.INTEGER).addScalar("idLocalidade", Hibernate.INTEGER).addScalar("idCategoria", Hibernate.INTEGER)
							.addScalar("valorCategoria", Hibernate.BIG_DECIMAL).setInteger("idLocalidade", idLocalidade).setInteger(
											"anoMesReferenciaContabil", anoMesReferenciaContabil).setInteger("situacaoNormal",
											DebitoCreditoSituacao.NORMAL).setInteger("situacaoIncluida", DebitoCreditoSituacao.INCLUIDA)
							.setInteger("situacaoRetificada", DebitoCreditoSituacao.RETIFICADA).setInteger("situacaoEntradaParc",
											DebitoCreditoSituacao.ENTRADA_DE_PARCELAMENTO).setInteger("descontoParcelamento",
											CreditoOrigem.DESCONTOS_CONCEDIDOS_NO_PARCELAMENTO).list();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0714] - Gerar Contas a Receber Contábil
	 * Acumula os valores dos créditos realizados para descontos condicionais
	 * pela gerência, localidade e categoria
	 * 
	 * @author Rafael Corrêa
	 * @date 08/11/2007
	 * @param idLocalidade
	 * @param anoMesReferenciaContabil
	 *            Ano e mês de referência contabil
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public Collection<Object[]> pesquisarDadosCreditosRealizadosCategoriaDescontoCondicional(int anoMesReferenciaContabil,
					Integer idLocalidade) throws ErroRepositorioException{

		Collection<Object[]> retorno = null;

		// cria uma sessão com o hibernate
		Session session = HibernateUtil.getSession();

		// cria a variável que vai conter o hql
		String consulta;

		try{

			consulta = "SELECT loca.greg_id as idGerencia, loca.uneg_id as idUnidadeNegocio, loca.loca_id as idLocalidade, credRealizadoCat.catg_id as idCategoria, sum(credRealizadoCat.crcg_vlcategoria) as valorCategoria "
							+ "FROM conta conta "
							+ "INNER JOIN localidade loca on loca.loca_id = conta.loca_id "
							+ "INNER JOIN credito_realizado credRealizado on credRealizado.cnta_id = conta.cnta_id "
							+ "INNER JOIN credito_realizado_categoria credRealizadoCat on credRealizadoCat.crrz_id = credRealizado.crrz_id "
							+ "WHERE loca.loca_id = :idLocalidade "
							+ "and conta.cnta_amreferenciaconta <= :anoMesReferenciaContabil "
							+ "and conta.dcst_idatual in ( :situacaoNormal, :situacaoIncluida, :situacaoRetificada, :situacaoEntradaParc ) "
							+ "and ( credRealizado.crog_id = :descontoCondicional ) "
							+ "group by loca.greg_id, loca.uneg_id, loca.loca_id, credRealizadoCat.catg_id "
							+ "UNION ALL "
							+ "SELECT loca.greg_id as idGerencia, loca.uneg_id as idUnidadeNegocio, loca.loca_id as idLocalidade, credRealizadoCat.catg_id as idCategoria, sum(credRealizadoCat.crch_vlcategoria) as valorCategoria "
							+ "FROM conta_historico conta "
							+ "INNER JOIN localidade loca on loca.loca_id = conta.loca_id "
							+ "INNER JOIN credito_realizado_historico credRealizado on credRealizado.cnta_id = conta.cnta_id "
							+ "INNER JOIN credito_realizado_catg_hist credRealizadoCat on credRealizadoCat.crhi_id = credRealizado.crhi_id "
							+ "WHERE loca.loca_id = :idLocalidade "
							+ "and conta.cnhi_amreferenciaconta <= :anoMesReferenciaContabil "
							+ "and conta.dcst_idatual in ( :situacaoNormal, :situacaoIncluida, :situacaoRetificada, :situacaoEntradaParc ) "
							+ "and (select min(pagto.pghi_amreferenciaarrecadacao) from pagamento_historico pagto where pagto.imov_id = conta.imov_id and pagto.pghi_amreferenciapagamento = conta.cnhi_amreferenciaconta) > :anoMesReferenciaContabil "
							+ "and ( credRealizado.crog_id = :descontoCondicional ) "
							+ "group by loca.greg_id, loca.uneg_id, loca.loca_id, credRealizadoCat.catg_id "
							+ "ORDER BY idGerencia, idUnidadeNegocio, idLocalidade, idCategoria ";

			// executa o sql
			retorno = session.createSQLQuery(consulta).addScalar("idGerencia", Hibernate.INTEGER).addScalar("idUnidadeNegocio",
							Hibernate.INTEGER).addScalar("idLocalidade", Hibernate.INTEGER).addScalar("idCategoria", Hibernate.INTEGER)
							.addScalar("valorCategoria", Hibernate.BIG_DECIMAL).setInteger("idLocalidade", idLocalidade).setInteger(
											"anoMesReferenciaContabil", anoMesReferenciaContabil).setInteger("situacaoNormal",
											DebitoCreditoSituacao.NORMAL).setInteger("situacaoIncluida", DebitoCreditoSituacao.INCLUIDA)
							.setInteger("situacaoRetificada", DebitoCreditoSituacao.RETIFICADA).setInteger("situacaoEntradaParc",
											DebitoCreditoSituacao.ENTRADA_DE_PARCELAMENTO).setInteger("descontoCondicional",
											CreditoOrigem.DESCONTOS_CONDICIONAIS).list();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0714] - Gerar Contas a Receber Contábil
	 * Acumula os valores dos créditos realizados para descontos incondicionais
	 * pela gerência, localidade e categoria
	 * 
	 * @author Rafael Corrêa
	 * @date 08/11/2007
	 * @param idLocalidade
	 * @param anoMesReferenciaContabil
	 *            Ano e mês de referência contabil
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public Collection<Object[]> pesquisarDadosCreditosRealizadosCategoriaDescontoIncondicional(int anoMesReferenciaContabil,
					Integer idLocalidade) throws ErroRepositorioException{

		Collection<Object[]> retorno = null;

		// cria uma sessão com o hibernate
		Session session = HibernateUtil.getSession();

		// cria a variável que vai conter o hql
		String consulta;

		try{

			consulta = "SELECT loca.greg_id as idGerencia, loca.uneg_id as idUnidadeNegocio, loca.loca_id as idLocalidade, credRealizadoCat.catg_id as idCategoria, sum(credRealizadoCat.crcg_vlcategoria) as valorCategoria "
							+ "FROM conta conta "
							+ "INNER JOIN localidade loca on loca.loca_id = conta.loca_id "
							+ "INNER JOIN credito_realizado credRealizado on credRealizado.cnta_id = conta.cnta_id "
							+ "INNER JOIN credito_realizado_categoria credRealizadoCat on credRealizadoCat.crrz_id = credRealizado.crrz_id "
							+ "WHERE loca.loca_id = :idLocalidade "
							+ "and conta.cnta_amreferenciaconta <= :anoMesReferenciaContabil "
							+ "and conta.dcst_idatual in ( :situacaoNormal, :situacaoIncluida, :situacaoRetificada, :situacaoEntradaParc ) "
							+ "and ( credRealizado.crog_id = :descontoIncondicional ) "
							+ "group by loca.greg_id, loca.uneg_id, loca.loca_id, credRealizadoCat.catg_id "
							+ "UNION ALL "
							+ "SELECT loca.greg_id as idGerencia, loca.uneg_id as idUnidadeNegocio, loca.loca_id as idLocalidade, credRealizadoCat.catg_id as idCategoria, sum(credRealizadoCat.crch_vlcategoria) as valorCategoria "
							+ "FROM conta_historico conta "
							+ "INNER JOIN localidade loca on loca.loca_id = conta.loca_id "
							+ "INNER JOIN credito_realizado_historico credRealizado on credRealizado.cnta_id = conta.cnta_id "
							+ "INNER JOIN credito_realizado_catg_hist credRealizadoCat on credRealizadoCat.crhi_id = credRealizado.crhi_id "
							+ "WHERE loca.loca_id = :idLocalidade "
							+ "and conta.cnhi_amreferenciaconta <= :anoMesReferenciaContabil "
							+ "and conta.dcst_idatual in ( :situacaoNormal, :situacaoIncluida, :situacaoRetificada, :situacaoEntradaParc ) "
							+ "and (select min(pagto.pghi_amreferenciaarrecadacao) from pagamento_historico pagto where pagto.imov_id = conta.imov_id and pagto.pghi_amreferenciapagamento = conta.cnhi_amreferenciaconta) > :anoMesReferenciaContabil "
							+ "and ( credRealizado.crog_id = :descontoIncondicional ) "
							+ "group by loca.greg_id, loca.uneg_id, loca.loca_id, credRealizadoCat.catg_id "
							+ "ORDER BY idGerencia, idUnidadeNegocio, idLocalidade, idCategoria ";

			// executa o sql
			retorno = session.createSQLQuery(consulta).addScalar("idGerencia", Hibernate.INTEGER).addScalar("idUnidadeNegocio",
							Hibernate.INTEGER).addScalar("idLocalidade", Hibernate.INTEGER).addScalar("idCategoria", Hibernate.INTEGER)
							.addScalar("valorCategoria", Hibernate.BIG_DECIMAL).setInteger("idLocalidade", idLocalidade).setInteger(
											"anoMesReferenciaContabil", anoMesReferenciaContabil).setInteger("situacaoNormal",
											DebitoCreditoSituacao.NORMAL).setInteger("situacaoIncluida", DebitoCreditoSituacao.INCLUIDA)
							.setInteger("situacaoRetificada", DebitoCreditoSituacao.RETIFICADA).setInteger("situacaoEntradaParc",
											DebitoCreditoSituacao.ENTRADA_DE_PARCELAMENTO).setInteger("descontoIncondicional",
											CreditoOrigem.DESCONTOS_INCONDICIONAIS).list();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0714] - Gerar Contas a Receber Contábil
	 * Acumula os valores dos créditos realizados para ajustes para zerar conta
	 * pela gerência, localidade e categoria
	 * 
	 * @author Rafael Corrêa
	 * @date 08/11/2007
	 * @param idLocalidade
	 * @param anoMesReferenciaContabil
	 *            Ano e mês de referência contabil
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public Collection<Object[]> pesquisarDadosCreditosRealizadosCategoriaAjusteZerarConta(int anoMesReferenciaContabil, Integer idLocalidade)
					throws ErroRepositorioException{

		Collection<Object[]> retorno = null;

		// cria uma sessão com o hibernate
		Session session = HibernateUtil.getSession();

		// cria a variável que vai conter o hql
		String consulta;

		try{

			consulta = "SELECT loca.greg_id as idGerencia, loca.uneg_id as idUnidadeNegocio, loca.loca_id as idLocalidade, credRealizadoCat.catg_id as idCategoria, sum(credRealizadoCat.crcg_vlcategoria) as valorCategoria "
							+ "FROM conta conta "
							+ "INNER JOIN localidade loca on loca.loca_id = conta.loca_id "
							+ "INNER JOIN credito_realizado credRealizado on credRealizado.cnta_id = conta.cnta_id "
							+ "INNER JOIN credito_realizado_categoria credRealizadoCat on credRealizadoCat.crrz_id = credRealizado.crrz_id "
							+ "WHERE loca.loca_id = :idLocalidade "
							+ "and conta.cnta_amreferenciaconta <= :anoMesReferenciaContabil "
							+ "and conta.dcst_idatual in ( :situacaoNormal, :situacaoIncluida, :situacaoRetificada, :situacaoEntradaParc ) "
							+ "and ( credRealizado.crog_id = :ajusteZerarConta ) "
							+ "group by loca.greg_id, loca.uneg_id, loca.loca_id, credRealizadoCat.catg_id "
							+ "UNION ALL "
							+ "SELECT loca.greg_id as idGerencia, loca.uneg_id as idUnidadeNegocio, loca.loca_id as idLocalidade, credRealizadoCat.catg_id as idCategoria, sum(credRealizadoCat.crch_vlcategoria) as valorCategoria "
							+ "FROM conta_historico conta "
							+ "INNER JOIN localidade loca on loca.loca_id = conta.loca_id "
							+ "INNER JOIN credito_realizado_historico credRealizado on credRealizado.cnta_id = conta.cnta_id "
							+ "INNER JOIN credito_realizado_catg_hist credRealizadoCat on credRealizadoCat.crhi_id = credRealizado.crhi_id "
							+ "WHERE loca.loca_id = :idLocalidade "
							+ "and conta.cnhi_amreferenciaconta <= :anoMesReferenciaContabil "
							+ "and conta.dcst_idatual in ( :situacaoNormal, :situacaoIncluida, :situacaoRetificada, :situacaoEntradaParc ) "
							+ "and (select min(pagto.pghi_amreferenciaarrecadacao) from pagamento_historico pagto where pagto.imov_id = conta.imov_id and pagto.pghi_amreferenciapagamento = conta.cnhi_amreferenciaconta) > :anoMesReferenciaContabil "
							+ "and ( credRealizado.crog_id = :ajusteZerarConta ) "
							+ "group by loca.greg_id, loca.uneg_id, loca.loca_id, credRealizadoCat.catg_id "
							+ "ORDER BY idGerencia, idUnidadeNegocio, idLocalidade, idCategoria ";

			// executa o sql
			retorno = session.createSQLQuery(consulta).addScalar("idGerencia", Hibernate.INTEGER).addScalar("idUnidadeNegocio",
							Hibernate.INTEGER).addScalar("idLocalidade", Hibernate.INTEGER).addScalar("idCategoria", Hibernate.INTEGER)
							.addScalar("valorCategoria", Hibernate.BIG_DECIMAL).setInteger("idLocalidade", idLocalidade).setInteger(
											"anoMesReferenciaContabil", anoMesReferenciaContabil).setInteger("situacaoNormal",
											DebitoCreditoSituacao.NORMAL).setInteger("situacaoIncluida", DebitoCreditoSituacao.INCLUIDA)
							.setInteger("situacaoRetificada", DebitoCreditoSituacao.RETIFICADA).setInteger("situacaoEntradaParc",
											DebitoCreditoSituacao.ENTRADA_DE_PARCELAMENTO).setInteger("ajusteZerarConta",
											CreditoOrigem.AJUSTES_PARA_ZERAR_CONTA).list();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0714] - Gerar Contas a Receber Contábil
	 * Acumula os valores dos créditos realizados para devoluções pela gerência,
	 * localidade e categoria
	 * 
	 * @author Rafael Corrêa
	 * @date 08/11/2007
	 * @param idLocalidade
	 * @param anoMesReferenciaContabil
	 *            Ano e mês de referência contabil
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public Collection<Object[]> pesquisarDadosCreditosRealizadosCategoriaDevolucao(int anoMesReferenciaContabil, Integer idLocalidade)
					throws ErroRepositorioException{

		Collection<Object[]> retorno = null;

		// cria uma sessão com o hibernate
		Session session = HibernateUtil.getSession();

		// cria a variável que vai conter o hql
		String consulta;

		try{

			consulta = "SELECT loca.greg_id as idGerencia, loca.uneg_id as idUnidadeNegocio, loca.loca_id as idLocalidade, credRealizadoCat.catg_id as idCategoria, sum(credRealizadoCat.crcg_vlcategoria) as valorCategoria "
							+ "FROM conta conta "
							+ "INNER JOIN localidade loca on loca.loca_id = conta.loca_id "
							+ "INNER JOIN credito_realizado credRealizado on credRealizado.cnta_id = conta.cnta_id "
							+ "INNER JOIN credito_realizado_categoria credRealizadoCat on credRealizadoCat.crrz_id = credRealizado.crrz_id "
							+ "WHERE loca.loca_id = :idLocalidade "
							+ "and conta.cnta_amreferenciaconta <= :anoMesReferenciaContabil "
							+ "and conta.dcst_idatual in ( :situacaoNormal, :situacaoIncluida, :situacaoRetificada, :situacaoEntradaParc ) "
							+ "and credRealizado.crog_id in (:devolucaoAgua, :devolucaoEsgoto, :servicoPagoIndevidamente, :devolucaoJuros) "
							+ "group by loca.greg_id, loca.uneg_id, loca.loca_id, credRealizadoCat.catg_id "
							+ "UNION ALL "
							+ "SELECT loca.greg_id as idGerencia, loca.uneg_id as idUnidadeNegocio, loca.loca_id as idLocalidade, credRealizadoCat.catg_id as idCategoria, sum(credRealizadoCat.crch_vlcategoria) as valorCategoria "
							+ "FROM conta_historico conta "
							+ "INNER JOIN localidade loca on loca.loca_id = conta.loca_id "
							+ "INNER JOIN credito_realizado_historico credRealizado on credRealizado.cnta_id = conta.cnta_id "
							+ "INNER JOIN credito_realizado_catg_hist credRealizadoCat on credRealizadoCat.crhi_id = credRealizado.crhi_id "
							+ "WHERE loca.loca_id = :idLocalidade "
							+ "and conta.cnhi_amreferenciaconta <= :anoMesReferenciaContabil "
							+ "and conta.dcst_idatual in ( :situacaoNormal, :situacaoIncluida, :situacaoRetificada, :situacaoEntradaParc ) "
							+ "and (select min(pagto.pghi_amreferenciaarrecadacao) from pagamento_historico pagto where pagto.imov_id = conta.imov_id and pagto.pghi_amreferenciapagamento = conta.cnhi_amreferenciaconta) > :anoMesReferenciaContabil "
							+ "and credRealizado.crog_id in (:devolucaoAgua, :devolucaoEsgoto, :servicoPagoIndevidamente, :devolucaoJuros)  "
							+ "group by loca.greg_id, loca.uneg_id, loca.loca_id, credRealizadoCat.catg_id "
							+ "ORDER BY idGerencia, idUnidadeNegocio, idLocalidade, idCategoria ";

			// executa o sql
			retorno = session.createSQLQuery(consulta).addScalar("idGerencia", Hibernate.INTEGER).addScalar("idUnidadeNegocio",
							Hibernate.INTEGER).addScalar("idLocalidade", Hibernate.INTEGER).addScalar("idCategoria", Hibernate.INTEGER)
							.addScalar("valorCategoria", Hibernate.BIG_DECIMAL).setInteger("idLocalidade", idLocalidade).setInteger(
											"anoMesReferenciaContabil", anoMesReferenciaContabil).setInteger("situacaoNormal",
											DebitoCreditoSituacao.NORMAL).setInteger("situacaoIncluida", DebitoCreditoSituacao.INCLUIDA)
							.setInteger("situacaoRetificada", DebitoCreditoSituacao.RETIFICADA).setInteger("situacaoEntradaParc",
											DebitoCreditoSituacao.ENTRADA_DE_PARCELAMENTO).setInteger("devolucaoAgua",
											CreditoOrigem.DEVOLUCAO_TARIFA_AGUA).setInteger("devolucaoEsgoto",
											CreditoOrigem.DEVOLUCAO_TARIFA_ESGOTO).setInteger("servicoPagoIndevidamente",
											CreditoOrigem.SERVICOS_INDIRETOS_PAGOS_INDEVIDAMENTE).setInteger("devolucaoJuros",
											CreditoOrigem.DEVOLUCAO_JUROS_PARCELAMENTO).list();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0714] - Gerar Contas a Receber Contábil
	 * Acumula os valores dos débitos a cobrar para serviço pela gerência,
	 * localidade e categoria
	 * 
	 * @author Rafael Corrêa
	 * @date 08/11/2007
	 * @param idLocalidade
	 * @param anoMesReferenciaContabil
	 *            Ano e mês de referência contabil
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public Collection<Object[]> pesquisarDadosDebitoACobrarCategoriaServico(int anoMesReferenciaContabil, Integer idLocalidade)
					throws ErroRepositorioException{

		Collection<Object[]> retorno = null;

		// cria uma sessão com o hibernate
		Session session = HibernateUtil.getSession();

		// cria a variável que vai conter o hql
		String consulta;

		try{

			consulta = "SELECT loca.greg_id as idGerencia, loca.uneg_id as idUnidadeNegocio, loca.loca_id as idLocalidade, dbacCat.catg_id as idCategoria, "
							+ "sum( "
							+ "CASE WHEN ( ( dbac.dbac_nnprestacaodebito - dbac.dbac_nnprestacaocobradas ) < 13 ) "
							+ "THEN dbacCat.dbcg_vlcategoria - (round( (dbacCat.dbcg_vlcategoria / dbac.dbac_nnprestacaodebito), 2 ) * dbac.dbac_nnprestacaocobradas) "
							+ "ELSE "
							+ "round( ( dbacCat.dbcg_vlcategoria / dbac.dbac_nnprestacaodebito), 2 ) * 12 "
							+ "END "
							+ ") as valorCurtoPrazo, "
							+ "sum( "
							+ "CASE WHEN ( ( dbac.dbac_nnprestacaodebito - dbac.dbac_nnprestacaocobradas ) < 13 ) "
							+ "THEN 0.00 "
							+ "ELSE "
							+ "dbacCat.dbcg_vlcategoria - ( round( ( dbacCat.dbcg_vlcategoria / dbac.dbac_nnprestacaodebito ), 2 ) * ( dbac.dbac_nnprestacaocobradas + 12) ) "
							+ "END "
							+ ") as valorLongoPrazo "
							+ "FROM debito_a_cobrar_categoria dbacCat "
							+ "INNER JOIN debito_a_cobrar dbac on dbacCat.dbac_id = dbac.dbac_id "
							+ "INNER JOIN localidade loca on dbac.loca_id = loca.loca_id "
							+ "WHERE loca.loca_id = :idLocalidade "
							+ "and dbac.dbac_amreferenciacontabil <= :anoMesReferenciaContabil "
							+ "and dbac.dcst_idatual in ( :situacaoNormal, :situacaoIncluida, :situacaoRetificada, :situacaoEntradaParc ) "
							+ "and ( dbac.fntp_id = :servico ) "
							+ "GROUP BY loca.greg_id, loca.uneg_id, loca.loca_id, dbacCat.catg_id "
							+ "UNION ALL "
							+ "SELECT loca.greg_id as idGerencia, loca.uneg_id as idUnidadeNegocio, loca.loca_id as idLocalidade, debCobCat.catg_id as idCategoria, sum(debCobCat.dccg_vlcategoria) as valorCurtoPrazo, 0.00 as valorLongoPrazo "
							+ "FROM conta conta "
							+ "INNER JOIN localidade loca on (loca.loca_id = conta.loca_id) "
							+ "INNER JOIN debito_cobrado debCob on (debCob.cnta_id = conta.cnta_id) "
							+ "INNER JOIN debito_cobrado_categoria debCobCat on (debCobCat.dbcb_id = debCob.dbcb_id) "
							+ "WHERE loca.loca_id = :idLocalidade "
							+ "and conta.cnta_amreferenciaconta > :anoMesReferenciaContabil "
							+ "and conta.dcst_idatual in ( :situacaoNormal, :situacaoIncluida, :situacaoRetificada, :situacaoEntradaParc ) "
							+ "and ( debCob.fntp_id = :servico ) "
							+ "group by loca.greg_id, loca.uneg_id, loca.loca_id, debCobCat.catg_id "
							+ "UNION ALL "
							+ "SELECT loca.greg_id as idGerencia, loca.uneg_id as idUnidadeNegocio, loca.loca_id as idLocalidade, debCobCat.catg_id as idCategoria, sum(debCobCat.dcch_vlcategoria) as valorCurtoPrazo, 0.00 as valorLongoPrazo "
							+ "FROM conta_historico conta "
							+ "INNER JOIN localidade loca on (loca.loca_id = conta.loca_id) "
							+ "INNER JOIN debito_cobrado_historico debCob on (debCob.cnta_id = conta.cnta_id) "
							+ "INNER JOIN debito_cobrado_categoria_hist debCobCat on (debCobCat.dbhi_id = debCob.dbhi_id) "
							+ "WHERE loca.loca_id = :idLocalidade "
							+ "and conta.cnhi_amreferenciaconta > :anoMesReferenciaContabil "
							+ "and conta.dcst_idatual in ( :situacaoNormal, :situacaoIncluida, :situacaoRetificada, :situacaoEntradaParc ) "
							+ "and (select min(pagto.pghi_amreferenciaarrecadacao) from pagamento_historico pagto where pagto.imov_id = conta.imov_id and pagto.pghi_amreferenciapagamento = conta.cnhi_amreferenciaconta) > :anoMesReferenciaContabil "
							+ "and ( debCob.fntp_id = :servico ) "
							+ "GROUP BY loca.greg_id, loca.uneg_id, loca.loca_id, debCobCat.catg_id "
							+ "ORDER BY idGerencia, idUnidadeNegocio, idLocalidade, idCategoria";

			// executa o sql
			retorno = session.createSQLQuery(consulta).addScalar("idGerencia", Hibernate.INTEGER).addScalar("idUnidadeNegocio",
							Hibernate.INTEGER).addScalar("idLocalidade", Hibernate.INTEGER).addScalar("idCategoria", Hibernate.INTEGER)
							.addScalar("valorCurtoPrazo", Hibernate.BIG_DECIMAL).addScalar("valorLongoPrazo", Hibernate.BIG_DECIMAL)
							.setInteger("idLocalidade", idLocalidade).setInteger("anoMesReferenciaContabil", anoMesReferenciaContabil)
							.setInteger("situacaoNormal", DebitoCreditoSituacao.NORMAL).setInteger("situacaoIncluida",
											DebitoCreditoSituacao.INCLUIDA).setInteger("situacaoRetificada",
											DebitoCreditoSituacao.RETIFICADA).setInteger("situacaoEntradaParc",
											DebitoCreditoSituacao.ENTRADA_DE_PARCELAMENTO).setInteger("servico",
											FinanciamentoTipo.SERVICO_NORMAL).list();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0714] - Gerar Contas a Receber Contábil
	 * Acumula os valores dos débitos a cobrar para documentos emitidos pela
	 * gerência, localidade e categoria
	 * 
	 * @author Rafael Corrêa
	 * @date 08/11/2007
	 * @param idLocalidade
	 * @param anoMesReferenciaContabil
	 *            Ano e mês de referência contabil
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public Collection<Object[]> pesquisarDadosDebitoACobrarCategoriaDocumentosEmitidos(int anoMesReferenciaContabil, Integer idLocalidade)
					throws ErroRepositorioException{

		Collection<Object[]> retorno = null;

		// cria uma sessão com o hibernate
		Session session = HibernateUtil.getSession();

		// cria a variável que vai conter o hql
		String consulta;

		try{
			// constroi o sql
			consulta = "SELECT loca.greg_id as idGerencia, loca.uneg_id as idUnidadeNegocio, loca.loca_id as idLocalidade, "
							+ " dbacCat.catg_id as idCategoria, "
							+ " sum( "
							+ " CASE WHEN ( ( dbac.dbac_nnprestacaodebito - dbac.dbac_nnprestacaocobradas ) < 13 ) "
							+ " THEN dbacCat.dbcg_vlcategoria - (round( (dbacCat.dbcg_vlcategoria / dbac.dbac_nnprestacaodebito), 2 ) * dbac.dbac_nnprestacaocobradas) "
							+ " ELSE "
							+ " round( ( dbacCat.dbcg_vlcategoria / dbac.dbac_nnprestacaodebito), 2 ) * 12 "
							+ " END "
							+ " ) as valorCurtoPrazo, "
							+ " sum( "
							+ " CASE WHEN ( ( dbac.dbac_nnprestacaodebito - dbac.dbac_nnprestacaocobradas ) < 13 ) "
							+ " THEN 0.00 "
							+ " ELSE "
							+ " dbacCat.dbcg_vlcategoria - ( round( ( dbacCat.dbcg_vlcategoria / dbac.dbac_nnprestacaodebito ), 2 ) * ( 12 + dbac.dbac_nnprestacaocobradas ) ) "
							+ " END " + " ) as valorLongoPrazo " + " FROM debito_a_cobrar_categoria dbacCat "
							+ " INNER JOIN debito_a_cobrar dbac " + " on dbacCat.dbac_id = dbac.dbac_id " + " INNER JOIN localidade loca "
							+ " on dbac.loca_id = loca.loca_id " + " WHERE loca.loca_id = :idLocalidade "
							+ " and ( ( dbac.dbac_amreferenciacontabil <= :anoMesReferenciaContabil "
							+ " and dbac.dcst_idatual in ( :situacaoNormal, " + " :situacaoIncluida, :situacaoRetificada ) ) "
							+ " or ( dbac.dbac_amreferenciacontabil > :anoMesReferenciaContabil "
							+ " and dbac.dcst_idatual in ( :situacaoCancelada, :situacaoCanceladaPorRetificacao, "
							+ " :situacaoParcelada, :situacaoDebitoPrescrito ) ) ) " + " and ( dbac.pcgr_id = :documentoEmitido ) "
							+ " GROUP BY idGerencia, idUnidadeNegocio, idLocalidade, idCategoria "
							+ " ORDER BY idGerencia, idUnidadeNegocio, idLocalidade, idCategoria ";

			// executa o sql
			retorno = session.createSQLQuery(consulta).addScalar("idGerencia", Hibernate.INTEGER).addScalar("idUnidadeNegocio",
							Hibernate.INTEGER).addScalar("idLocalidade", Hibernate.INTEGER).addScalar("idCategoria", Hibernate.INTEGER)
							.addScalar("valorCurtoPrazo", Hibernate.BIG_DECIMAL).addScalar("valorLongoPrazo", Hibernate.BIG_DECIMAL)
							.setInteger("idLocalidade", idLocalidade).setInteger("anoMesReferenciaContabil", anoMesReferenciaContabil)
							.setInteger("situacaoNormal", DebitoCreditoSituacao.NORMAL).setInteger("situacaoIncluida",
											DebitoCreditoSituacao.INCLUIDA).setInteger("situacaoRetificada",
											DebitoCreditoSituacao.RETIFICADA).setInteger("situacaoCancelada",
											DebitoCreditoSituacao.CANCELADA).setInteger("situacaoCanceladaPorRetificacao",
											DebitoCreditoSituacao.CANCELADA_POR_RETIFICACAO).setInteger("situacaoParcelada",
											DebitoCreditoSituacao.PARCELADA).setInteger("situacaoDebitoPrescrito",
 DebitoCreditoSituacao.PRESCRITA)
							.setInteger("documentoEmitido",
											ParcelamentoGrupo.DOCUMENTOS_EMITIDOS).list();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0714] - Gerar Contas a Receber Contábil
	 * Acumula os valores dos débitos a cobrar para financiamentos a cobrar de
	 * curto prazo pela gerência, localidade e categoria
	 * 
	 * @author Rafael Corrêa
	 * @date 08/11/2007
	 * @param idLocalidade
	 * @param anoMesReferenciaContabil
	 *            Ano e mês de referência contabil
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public Collection<Object[]> pesquisarDadosDebitoACobrarCategoriaFinancimentosCurtoPrazo(int anoMesReferenciaContabil,
					Integer idLocalidade) throws ErroRepositorioException{

		Collection<Object[]> retorno = null;

		// cria uma sessão com o hibernate
		Session session = HibernateUtil.getSession();

		// cria a variável que vai conter o hql
		String consulta;

		try{
			// constroi o sql
			consulta = "SELECT loca.greg_id as idGerencia, loca.uneg_id as idUnidadeNegocio, loca.loca_id as idLocalidade, "
							+ " dbacCat.catg_id as idCategoria, "
							+ " sum( "
							+ " CASE WHEN ( ( dbac.dbac_nnprestacaodebito - dbac.dbac_nnprestacaocobradas ) < 13 ) "
							+ " THEN dbacCat.dbcg_vlcategoria - (round( (dbacCat.dbcg_vlcategoria / dbac.dbac_nnprestacaodebito), 2 ) * dbac.dbac_nnprestacaocobradas) "
							+ " ELSE "
							+ " round( ( dbacCat.dbcg_vlcategoria / dbac.dbac_nnprestacaodebito), 2 ) * 12 "
							+ " END "
							+ " ) as valorCurtoPrazo, "
							+ " sum( "
							+ " CASE WHEN ( ( dbac.dbac_nnprestacaodebito - dbac.dbac_nnprestacaocobradas ) < 13 ) "
							+ " THEN 0.00 "
							+ " ELSE "
							+ " dbacCat.dbcg_vlcategoria - ( round( ( dbacCat.dbcg_vlcategoria / dbac.dbac_nnprestacaodebito ), 2 ) * ( 12 + dbac.dbac_nnprestacaocobradas ) ) "
							+ " END " + " ) as valorLongoPrazo " + " FROM debito_a_cobrar_categoria dbacCat "
							+ " INNER JOIN debito_a_cobrar dbac " + " on dbacCat.dbac_id = dbac.dbac_id " + " INNER JOIN localidade loca "
							+ " on dbac.loca_id = loca.loca_id " + " WHERE loca.loca_id = :idLocalidade "
							+ " and ( ( dbac.dbac_amreferenciacontabil <= :anoMesReferenciaContabil "
							+ " and dbac.dcst_idatual in ( :situacaoNormal, " + " :situacaoIncluida, :situacaoRetificada ) ) "
							+ " or ( dbac.dbac_amreferenciacontabil > :anoMesReferenciaContabil "
							+ " and dbac.dcst_idatual in ( :situacaoCancelada, :situacaoCanceladaPorRetificacao, "
							+ " :situacaoParcelada, :situacaoDebitoPrescrito ) ) ) " + " and ( dbac.pcgr_id = :financiamentoCurtoPrazo ) "
							+ " GROUP BY idGerencia, idUnidadeNegocio, idLocalidade, idCategoria "
							+ " ORDER BY idGerencia, idUnidadeNegocio, idLocalidade, idCategoria ";

			// executa o sql
			retorno = session.createSQLQuery(consulta).addScalar("idGerencia", Hibernate.INTEGER).addScalar("idUnidadeNegocio",
							Hibernate.INTEGER).addScalar("idLocalidade", Hibernate.INTEGER).addScalar("idCategoria", Hibernate.INTEGER)
							.addScalar("valorCurtoPrazo", Hibernate.BIG_DECIMAL).addScalar("valorLongoPrazo", Hibernate.BIG_DECIMAL)
							.setInteger("idLocalidade", idLocalidade).setInteger("anoMesReferenciaContabil", anoMesReferenciaContabil)
							.setInteger("situacaoNormal", DebitoCreditoSituacao.NORMAL).setInteger("situacaoIncluida",
											DebitoCreditoSituacao.INCLUIDA).setInteger("situacaoRetificada",
											DebitoCreditoSituacao.RETIFICADA).setInteger("situacaoCancelada",
											DebitoCreditoSituacao.CANCELADA).setInteger("situacaoCanceladaPorRetificacao",
											DebitoCreditoSituacao.CANCELADA_POR_RETIFICACAO).setInteger("situacaoParcelada",
											DebitoCreditoSituacao.PARCELADA).setInteger("situacaoDebitoPrescrito",
 DebitoCreditoSituacao.PRESCRITA)
							.setInteger("financiamentoCurtoPrazo",
											ParcelamentoGrupo.FINANCIAMENTOS_A_COBRAR_CURTO_PRAZO).list();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0714] - Gerar Contas a Receber Contábil
	 * Acumula os valores dos débitos a cobrar para financiamentos a cobrar de
	 * longo prazo pela gerência, localidade e categoria
	 * 
	 * @author Rafael Corrêa
	 * @date 08/11/2007
	 * @param idLocalidade
	 * @param anoMesReferenciaContabil
	 *            Ano e mês de referência contabil
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public Collection<Object[]> pesquisarDadosDebitoACobrarCategoriaFinancimentosLongoPrazo(int anoMesReferenciaContabil,
					Integer idLocalidade) throws ErroRepositorioException{

		Collection<Object[]> retorno = null;

		// cria uma sessão com o hibernate
		Session session = HibernateUtil.getSession();

		// cria a variável que vai conter o hql
		String consulta;

		try{
			// constroi o sql
			consulta = "SELECT loca.greg_id as idGerencia, loca.uneg_id as idUnidadeNegocio, loca.loca_id as idLocalidade, "
							+ " dbacCat.catg_id as idCategoria, "
							+ " sum( "
							+ " CASE WHEN ( ( dbac.dbac_nnprestacaodebito - dbac.dbac_nnprestacaocobradas ) < 13 ) "
							+ " THEN dbacCat.dbcg_vlcategoria - (round( (dbacCat.dbcg_vlcategoria / dbac.dbac_nnprestacaodebito), 2 ) * dbac.dbac_nnprestacaocobradas) "
							+ " ELSE "
							+ " round( ( dbacCat.dbcg_vlcategoria / dbac.dbac_nnprestacaodebito), 2 ) * 12 "
							+ " END "
							+ " ) as valorCurtoPrazo, "
							+ " sum( "
							+ " CASE WHEN ( ( dbac.dbac_nnprestacaodebito - dbac.dbac_nnprestacaocobradas ) < 13 ) "
							+ " THEN 0.00 "
							+ " ELSE "
							+ " dbacCat.dbcg_vlcategoria - ( round( ( dbacCat.dbcg_vlcategoria / dbac.dbac_nnprestacaodebito ), 2 ) * ( 12 + dbac.dbac_nnprestacaocobradas ) ) "
							+ " END " + " ) as valorLongoPrazo " + " FROM debito_a_cobrar_categoria dbacCat "
							+ " INNER JOIN debito_a_cobrar dbac " + " on dbacCat.dbac_id = dbac.dbac_id " + " INNER JOIN localidade loca "
							+ " on dbac.loca_id = loca.loca_id " + " WHERE loca.loca_id = :idLocalidade "
							+ " and ( ( dbac.dbac_amreferenciacontabil <= :anoMesReferenciaContabil "
							+ " and dbac.dcst_idatual in ( :situacaoNormal, " + " :situacaoIncluida, :situacaoRetificada ) ) "
							+ " or ( dbac.dbac_amreferenciacontabil > :anoMesReferenciaContabil "
							+ " and dbac.dcst_idatual in ( :situacaoCancelada, :situacaoCanceladaPorRetificacao, "
							+ " :situacaoParcelada, :situacaoDebitoPrescrito ) ) ) " + " and ( dbac.pcgr_id = :financiamentoLongoPrazo ) "
							+ " GROUP BY idGerencia, idUnidadeNegocio, idLocalidade, idCategoria "
							+ " ORDER BY idGerencia, idUnidadeNegocio, idLocalidade, idCategoria ";

			// executa o sql
			retorno = session.createSQLQuery(consulta).addScalar("idGerencia", Hibernate.INTEGER).addScalar("idUnidadeNegocio",
							Hibernate.INTEGER).addScalar("idLocalidade", Hibernate.INTEGER).addScalar("idCategoria", Hibernate.INTEGER)
							.addScalar("valorCurtoPrazo", Hibernate.BIG_DECIMAL).addScalar("valorLongoPrazo", Hibernate.BIG_DECIMAL)
							.setInteger("idLocalidade", idLocalidade).setInteger("anoMesReferenciaContabil", anoMesReferenciaContabil)
							.setInteger("situacaoNormal", DebitoCreditoSituacao.NORMAL).setInteger("situacaoIncluida",
											DebitoCreditoSituacao.INCLUIDA).setInteger("situacaoRetificada",
											DebitoCreditoSituacao.RETIFICADA).setInteger("situacaoCancelada",
											DebitoCreditoSituacao.CANCELADA).setInteger("situacaoCanceladaPorRetificacao",
											DebitoCreditoSituacao.CANCELADA_POR_RETIFICACAO).setInteger("situacaoParcelada",
											DebitoCreditoSituacao.PARCELADA).setInteger("situacaoDebitoPrescrito",
 DebitoCreditoSituacao.PRESCRITA)
							.setInteger("financiamentoLongoPrazo",
											ParcelamentoGrupo.FINANCIAMENTOS_A_COBRAR_LONGO_PRAZO).list();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0714] - Gerar Contas a Receber Contábil
	 * Acumula os valores dos débitos a cobrar para parcelamentos a cobrar de
	 * curto prazo pela gerência, localidade e categoria
	 * 
	 * @author Rafael Corrêa
	 * @date 08/11/2007
	 * @param idLocalidade
	 * @param anoMesReferenciaContabil
	 *            Ano e mês de referência contabil
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public Collection<Object[]> pesquisarDadosDebitoACobrarCategoriaParcelamentosCurtoPrazo(int anoMesReferenciaContabil,
					Integer idLocalidade) throws ErroRepositorioException{

		Collection<Object[]> retorno = null;

		// cria uma sessão com o hibernate
		Session session = HibernateUtil.getSession();

		// cria a variável que vai conter o hql
		String consulta;

		try{
			// constroi o sql
			consulta = "SELECT loca.greg_id as idGerencia, loca.uneg_id as idUnidadeNegocio, loca.loca_id as idLocalidade, "
							+ " dbacCat.catg_id as idCategoria, "
							+ " sum( "
							+ " CASE WHEN ( ( dbac.dbac_nnprestacaodebito - dbac.dbac_nnprestacaocobradas ) < 13 ) "
							+ " THEN dbacCat.dbcg_vlcategoria - (round( (dbacCat.dbcg_vlcategoria / dbac.dbac_nnprestacaodebito), 2 ) * dbac.dbac_nnprestacaocobradas) "
							+ " ELSE "
							+ " round( ( dbacCat.dbcg_vlcategoria / dbac.dbac_nnprestacaodebito), 2 ) * 12 "
							+ " END "
							+ " ) as valorCurtoPrazo, "
							+ " sum( "
							+ " CASE WHEN ( ( dbac.dbac_nnprestacaodebito - dbac.dbac_nnprestacaocobradas ) < 13 ) "
							+ " THEN 0.00 "
							+ " ELSE "
							+ " dbacCat.dbcg_vlcategoria - ( round( ( dbacCat.dbcg_vlcategoria / dbac.dbac_nnprestacaodebito ), 2 ) * ( 12 + dbac.dbac_nnprestacaocobradas ) ) "
							+ " END " + " ) as valorLongoPrazo " + " FROM debito_a_cobrar_categoria dbacCat "
							+ " INNER JOIN debito_a_cobrar dbac " + " on dbacCat.dbac_id = dbac.dbac_id " + " INNER JOIN localidade loca "
							+ " on dbac.loca_id = loca.loca_id " + " WHERE loca.loca_id = :idLocalidade "
							+ " and ( ( dbac.dbac_amreferenciacontabil <= :anoMesReferenciaContabil "
							+ " and dbac.dcst_idatual in ( :situacaoNormal, " + " :situacaoIncluida, :situacaoRetificada ) ) "
							+ " or ( dbac.dbac_amreferenciacontabil > :anoMesReferenciaContabil "
							+ " and dbac.dcst_idatual in ( :situacaoCancelada, :situacaoCanceladaPorRetificacao, "
							+ " :situacaoParcelada, :situacaoDebitoPrescrito ) ) ) " + " and ( dbac.pcgr_id = :parcelamentoCurtoPrazo ) "
							+ " GROUP BY idGerencia, idUnidadeNegocio, idLocalidade, idCategoria "
							+ " ORDER BY idGerencia, idUnidadeNegocio, idLocalidade, idCategoria ";

			// executa o sql
			retorno = session.createSQLQuery(consulta).addScalar("idGerencia", Hibernate.INTEGER).addScalar("idUnidadeNegocio",
							Hibernate.INTEGER).addScalar("idLocalidade", Hibernate.INTEGER).addScalar("idCategoria", Hibernate.INTEGER)
							.addScalar("valorCurtoPrazo", Hibernate.BIG_DECIMAL).addScalar("valorLongoPrazo", Hibernate.BIG_DECIMAL)
							.setInteger("idLocalidade", idLocalidade).setInteger("anoMesReferenciaContabil", anoMesReferenciaContabil)
							.setInteger("situacaoNormal", DebitoCreditoSituacao.NORMAL).setInteger("situacaoIncluida",
											DebitoCreditoSituacao.INCLUIDA).setInteger("situacaoRetificada",
											DebitoCreditoSituacao.RETIFICADA).setInteger("situacaoCancelada",
											DebitoCreditoSituacao.CANCELADA).setInteger("situacaoCanceladaPorRetificacao",
											DebitoCreditoSituacao.CANCELADA_POR_RETIFICACAO).setInteger("situacaoParcelada",
											DebitoCreditoSituacao.PARCELADA).setInteger("situacaoDebitoPrescrito",
 DebitoCreditoSituacao.PRESCRITA)
							.setInteger("parcelamentoCurtoPrazo",
											ParcelamentoGrupo.PARCELAMENTOS_A_COBRAR_CURTO_PRAZO).list();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0714] - Gerar Contas a Receber Contábil
	 * Acumula os valores dos débitos a cobrar para parcelamentos a cobrar de
	 * longo prazo pela gerência, localidade e categoria
	 * 
	 * @author Rafael Corrêa
	 * @date 08/11/2007
	 * @param idLocalidade
	 * @param anoMesReferenciaContabil
	 *            Ano e mês de referência contabil
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public Collection<Object[]> pesquisarDadosDebitoACobrarCategoriaParcelamentosLongoPrazo(int anoMesReferenciaContabil,
					Integer idLocalidade) throws ErroRepositorioException{

		Collection<Object[]> retorno = null;

		// cria uma sessão com o hibernate
		Session session = HibernateUtil.getSession();

		// cria a variável que vai conter o hql
		String consulta;

		try{

			consulta = "SELECT loca.greg_id as idGerencia, loca.uneg_id as idUnidadeNegocio, loca.loca_id as idLocalidade, dbacCat.catg_id as idCategoria, "
							+ "sum( "
							+ "CASE WHEN ( ( dbac.dbac_nnprestacaodebito - dbac.dbac_nnprestacaocobradas ) < 13 ) "
							+ "THEN dbacCat.dbcg_vlcategoria - (round( (dbacCat.dbcg_vlcategoria / dbac.dbac_nnprestacaodebito), 2 ) * dbac.dbac_nnprestacaocobradas) "
							+ "ELSE "
							+ "round( ( dbacCat.dbcg_vlcategoria / dbac.dbac_nnprestacaodebito), 2 ) * 12 "
							+ "END "
							+ ") as valorCurtoPrazo, "
							+ "sum( "
							+ "CASE WHEN ( ( dbac.dbac_nnprestacaodebito - dbac.dbac_nnprestacaocobradas ) < 13 ) "
							+ "THEN 0.00 "
							+ "ELSE "
							+ "dbacCat.dbcg_vlcategoria - ( round( ( dbacCat.dbcg_vlcategoria / dbac.dbac_nnprestacaodebito ), 2 ) * ( dbac.dbac_nnprestacaocobradas + 12) ) "
							+ "END "
							+ ") as valorLongoPrazo "
							+ "FROM debito_a_cobrar_categoria dbacCat "
							+ "INNER JOIN debito_a_cobrar dbac on dbacCat.dbac_id = dbac.dbac_id "
							+ "INNER JOIN localidade loca on dbac.loca_id = loca.loca_id "
							+ "WHERE loca.loca_id = :idLocalidade "
							+ "and dbac.dbac_amreferenciacontabil <= :anoMesReferenciaContabil "
							+ "and dbac.dcst_idatual in ( :situacaoNormal, :situacaoIncluida, :situacaoRetificada, :situacaoEntradaParc ) "
							+ "and dbac.fntp_id in (:parcelamentoAgua, :parcelamentoEsgoto, :parcelamentoServico, :jurosParcelamento, :entratradaParcelamento, :parcExerc, :parcExercAnte, :parcPrefeitura) "
							+ "GROUP BY loca.greg_id, loca.uneg_id, loca.loca_id, dbacCat.catg_id "
							+ "UNION ALL "
							+ "SELECT loca.greg_id as idGerencia, loca.uneg_id as idUnidadeNegocio, loca.loca_id as idLocalidade, debCobCat.catg_id as idCategoria, sum(debCobCat.dccg_vlcategoria) as valorCurtoPrazo, 0.00 as valorLongoPrazo "
							+ "FROM conta conta "
							+ "INNER JOIN localidade loca on (loca.loca_id = conta.loca_id) "
							+ "INNER JOIN debito_cobrado debCob on (debCob.cnta_id = conta.cnta_id) "
							+ "INNER JOIN debito_cobrado_categoria debCobCat on (debCobCat.dbcb_id = debCob.dbcb_id) "
							+ "WHERE loca.loca_id = :idLocalidade "
							+ "and conta.cnta_amreferenciaconta > :anoMesReferenciaContabil "
							+ "and conta.dcst_idatual in ( :situacaoNormal, :situacaoIncluida, :situacaoRetificada, :situacaoEntradaParc ) "
							+ "and debCob.fntp_id in (:parcelamentoAgua, :parcelamentoEsgoto, :parcelamentoServico, :jurosParcelamento, :entratradaParcelamento, :parcExerc, :parcExercAnte, :parcPrefeitura) "
							+ "group by loca.greg_id, loca.uneg_id, loca.loca_id, debCobCat.catg_id "
							+ "UNION ALL "
							+ "SELECT loca.greg_id as idGerencia, loca.uneg_id as idUnidadeNegocio, loca.loca_id as idLocalidade, debCobCat.catg_id as idCategoria, sum(debCobCat.dcch_vlcategoria) as valorCurtoPrazo, 0.00 as valorLongoPrazo "
							+ "FROM conta_historico conta "
							+ "INNER JOIN localidade loca on (loca.loca_id = conta.loca_id) "
							+ "INNER JOIN debito_cobrado_historico debCob on (debCob.cnta_id = conta.cnta_id) "
							+ "INNER JOIN debito_cobrado_categoria_hist debCobCat on (debCobCat.dbhi_id = debCob.dbhi_id) "
							+ "WHERE loca.loca_id = :idLocalidade "
							+ "and conta.cnhi_amreferenciaconta > :anoMesReferenciaContabil "
							+ "and conta.dcst_idatual in ( :situacaoNormal, :situacaoIncluida, :situacaoRetificada, :situacaoEntradaParc ) "
							+ "and (select min(pagto.pghi_amreferenciaarrecadacao) from pagamento_historico pagto where pagto.imov_id = conta.imov_id and pagto.pghi_amreferenciapagamento = conta.cnhi_amreferenciaconta) > :anoMesReferenciaContabil "
							+ "and debCob.fntp_id in (:parcelamentoAgua, :parcelamentoEsgoto, :parcelamentoServico, :jurosParcelamento, :entratradaParcelamento, :parcExerc, :parcExercAnte, :parcPrefeitura) "
							+ "GROUP BY loca.greg_id, loca.uneg_id, loca.loca_id, debCobCat.catg_id "
							+ "ORDER BY idGerencia, idUnidadeNegocio, idLocalidade, idCategoria";

			// executa o sql
			retorno = session.createSQLQuery(consulta).addScalar("idGerencia", Hibernate.INTEGER).addScalar("idUnidadeNegocio",
							Hibernate.INTEGER).addScalar("idLocalidade", Hibernate.INTEGER).addScalar("idCategoria", Hibernate.INTEGER)
							.addScalar("valorCurtoPrazo", Hibernate.BIG_DECIMAL).addScalar("valorLongoPrazo", Hibernate.BIG_DECIMAL)
							.setInteger("idLocalidade", idLocalidade).setInteger("anoMesReferenciaContabil", anoMesReferenciaContabil)
							.setInteger("situacaoNormal", DebitoCreditoSituacao.NORMAL).setInteger("situacaoIncluida",
											DebitoCreditoSituacao.INCLUIDA).setInteger("situacaoRetificada",
											DebitoCreditoSituacao.RETIFICADA).setInteger("situacaoEntradaParc",
											DebitoCreditoSituacao.ENTRADA_DE_PARCELAMENTO)

							.setInteger("parcelamentoAgua", FinanciamentoTipo.PARCELAMENTO_AGUA).setInteger("parcelamentoEsgoto",
											FinanciamentoTipo.PARCELAMENTO_ESGOTO).setInteger("parcelamentoServico",
											FinanciamentoTipo.PARCELAMENTO_SERVICO).setInteger("jurosParcelamento",
											FinanciamentoTipo.JUROS_PARCELAMENTO).setInteger("entratradaParcelamento",
											FinanciamentoTipo.ENTRADA_PARCELAMENTO).setInteger("parcExerc",
											FinanciamentoTipo.PARCELAMENTO_EXERCICIO).setInteger("parcExercAnte",
											FinanciamentoTipo.PARCEL_EXERC_ANTERIOR).setInteger("parcPrefeitura",
											FinanciamentoTipo.PARCELAMENTO_PREFEITURAS)

							.list();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0714] - Gerar Contas a Receber Contábil
	 * Acumula os valores dos débitos a cobrar para arrasto de água, arrasto de
	 * esgoto e arrasto de serviço pela gerência, localidade e categoria
	 * 
	 * @author Rafael Corrêa
	 * @date 08/11/2007
	 * @param idLocalidade
	 * @param anoMesReferenciaContabil
	 *            Ano e mês de referência contabil
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public Collection<Object[]> pesquisarDadosDebitoACobrarCategoriaArrasto(int anoMesReferenciaContabil, Integer idLocalidade)
					throws ErroRepositorioException{

		Collection<Object[]> retorno = null;

		// cria uma sessão com o hibernate
		Session session = HibernateUtil.getSession();

		// cria a variável que vai conter o hql
		String consulta;

		try{
			// constroi o sql
			consulta = "SELECT loca.greg_id as idGerencia, loca.uneg_id as idUnidadeNegocio, loca.loca_id as idLocalidade, dbacCat.catg_id as idCategoria, sum(dbacCat.dbcg_vlcategoria) as valorCategoria "
							+ "FROM debito_a_cobrar_categoria dbacCat "
							+ "INNER JOIN debito_a_cobrar dbac on dbacCat.dbac_id = dbac.dbac_id "
							+ "INNER JOIN localidade loca on dbac.loca_id = loca.loca_id "
							+ "WHERE loca.loca_id = :idLocalidade "
							+ "and dbac.dbac_amreferenciacontabil <= :anoMesReferenciaContabil "
							+ "and dbac.dcst_idatual in ( :situacaoNormal, :situacaoIncluida, :situacaoRetificada, :situacaoEntradaParc ) "
							+ "and dbac.fntp_id in ( :arrastoAgua, :arrastoEsgoto, :arrastoServico, :arrastoExer, :arrastoExerAnt ) "
							+ "GROUP BY loca.greg_id, loca.uneg_id, loca.loca_id, dbacCat.catg_id "
							+ "UNION ALL "
							+ "SELECT loca.greg_id as idGerencia, loca.uneg_id as idUnidadeNegocio, loca.loca_id as idLocalidade, debCobCat.catg_id as idCategoria, sum(debCobCat.dccg_vlcategoria) as valorCategoria "
							+ "FROM conta conta "
							+ "INNER JOIN localidade loca on (loca.loca_id = conta.loca_id) "
							+ "INNER JOIN debito_cobrado debCob on (debCob.cnta_id = conta.cnta_id) "
							+ "INNER JOIN debito_cobrado_categoria debCobCat on (debCobCat.dbcb_id = debCob.dbcb_id) "
							+ "WHERE loca.loca_id = :idLocalidade "
							+ "and conta.cnta_amreferenciaconta > :anoMesReferenciaContabil "
							+ "and conta.dcst_idatual in ( :situacaoNormal, :situacaoIncluida, :situacaoRetificada, :situacaoEntradaParc ) "
							+ "and ( debCob.fntp_id in (:arrastoAgua, :arrastoEsgoto, :arrastoServico, :arrastoExer, :arrastoExerAnt) ) "
							+ "group by loca.greg_id, loca.uneg_id, loca.loca_id, debCobCat.catg_id "
							+ "UNION ALL "
							+ "SELECT loca.greg_id as idGerencia, loca.uneg_id as idUnidadeNegocio, loca.loca_id as idLocalidade, debCobCat.catg_id as idCategoria, sum(debCobCat.dcch_vlcategoria) as valorCategoria "
							+ "FROM conta_historico conta "
							+ "INNER JOIN localidade loca on (loca.loca_id = conta.loca_id) "
							+ "INNER JOIN debito_cobrado_historico debCob on (debCob.cnta_id = conta.cnta_id) "
							+ "INNER JOIN debito_cobrado_categoria_hist debCobCat on (debCobCat.dbhi_id = debCob.dbhi_id) "
							+ "WHERE loca.loca_id = :idLocalidade "
							+ "and conta.cnhi_amreferenciaconta > :anoMesReferenciaContabil "
							+ "and conta.dcst_idatual in ( :situacaoNormal, :situacaoIncluida, :situacaoRetificada, :situacaoEntradaParc ) "
							+ "and ( debCob.fntp_id in (:arrastoAgua, :arrastoEsgoto, :arrastoServico, :arrastoExer, :arrastoExerAnt) ) "
							+ "and (select min(pagto.pghi_amreferenciaarrecadacao) from pagamento_historico pagto where pagto.imov_id = conta.imov_id and pagto.pghi_amreferenciapagamento = conta.cnhi_amreferenciaconta) > :anoMesReferenciaContabil "
							+ "GROUP BY loca.greg_id, loca.uneg_id, loca.loca_id, debCobCat.catg_id "
							+ "ORDER BY idGerencia, idUnidadeNegocio, idLocalidade, idCategoria";

			// executa o sql
			retorno = session.createSQLQuery(consulta).addScalar("idGerencia", Hibernate.INTEGER).addScalar("idUnidadeNegocio",
							Hibernate.INTEGER).addScalar("idLocalidade", Hibernate.INTEGER).addScalar("idCategoria", Hibernate.INTEGER)
							.addScalar("valorCategoria", Hibernate.BIG_DECIMAL).setInteger("idLocalidade", idLocalidade).setInteger(
											"anoMesReferenciaContabil", anoMesReferenciaContabil).setInteger("situacaoNormal",
											DebitoCreditoSituacao.NORMAL).setInteger("situacaoIncluida", DebitoCreditoSituacao.INCLUIDA)
							.setInteger("situacaoRetificada", DebitoCreditoSituacao.RETIFICADA).setInteger("situacaoEntradaParc",
											DebitoCreditoSituacao.ENTRADA_DE_PARCELAMENTO).setInteger("arrastoAgua",
											FinanciamentoTipo.ARRASTO_AGUA).setInteger("arrastoEsgoto", FinanciamentoTipo.ARRASTO_ESGOTO)
							.setInteger("arrastoServico", FinanciamentoTipo.ARRASTO_SERVICO).setInteger("arrastoExer",
											FinanciamentoTipo.ARRASTO_DO_EXERCICIO).setInteger("arrastoExerAnt",
											FinanciamentoTipo.ARRASTO_EXERC_ANTERIOR).list();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0714] - Gerar Contas a Receber Contábil
	 * Acumula os valores dos créditos a realizar para descontos concedidos no
	 * parcelamento pela gerência, localidade e categoria
	 * 
	 * @author Rafael Corrêa
	 * @date 08/11/2007
	 * @param idLocalidade
	 * @param anoMesReferenciaContabil
	 *            Ano e mês de referência contabil
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public Collection<Object[]> pesquisarDadosCreditoARealizarCategoriaDescontosParcelamento(int anoMesReferenciaContabil,
					Integer idLocalidade) throws ErroRepositorioException{

		Collection<Object[]> retorno = null;

		// cria uma sessão com o hibernate
		Session session = HibernateUtil.getSession();

		// cria a variável que vai conter o hql
		String consulta;

		try{

			consulta = "SELECT loca.greg_id as idGerencia, loca.uneg_id as idUnidadeNegocio, loca.loca_id as idLocalidade, crarCat.catg_id as idCategoria, "
							+ "sum( "
							+ "CASE WHEN ( ( crar.crar_nnprestacaocredito - crar.crar_nnprestacaorealizadas ) < 13 ) "
							+ "THEN crarCat.cacg_vlcategoria - (round( ( crarCat.cacg_vlcategoria / crar.crar_nnprestacaocredito), 2 ) * crar.crar_nnprestacaorealizadas) "
							+ "ELSE "
							+ "round( ( crarCat.cacg_vlcategoria / crar.crar_nnprestacaocredito), 2 ) * 12 "
							+ "END "
							+ ") as valorCurtoPrazo, "
							+ "sum( "
							+ "CASE WHEN ( ( crar.crar_nnprestacaocredito - crar.crar_nnprestacaorealizadas ) < 13 ) "
							+ "THEN 0.00 "
							+ "ELSE "
							+ "crarCat.cacg_vlcategoria - ( round( ( crarCat.cacg_vlcategoria / crar.crar_nnprestacaocredito ), 2 ) * ( crar.crar_nnprestacaorealizadas + 12 ) ) "
							+ "END "
							+ ") as valorLongoPrazo "
							+ "FROM credito_a_realizar_categoria crarCat "
							+ "INNER JOIN credito_a_realizar crar on crarCat.crar_id = crar.crar_id "
							+ "INNER JOIN localidade loca on crar.loca_id = loca.loca_id "
							+ "WHERE loca.loca_id = :idLocalidade "
							+ "and crar.crar_amreferenciacontabil <= :anoMesReferenciaContabil "
							+ "and crar.dcst_idatual in ( :situacaoNormal, :situacaoIncluida, :situacaoRetificada ) "
							+ "and ( crar.crog_id = :descontoParcelamento ) "
							+ "GROUP BY loca.greg_id, loca.uneg_id, loca.loca_id, crarCat.catg_id "
							+ "UNION ALL "
							+ "SELECT loca.greg_id as idGerencia, loca.uneg_id as idUnidadeNegocio, loca.loca_id as idLocalidade, credRealizadoCat.catg_id as idCategoria, sum(credRealizadoCat.crcg_vlcategoria) as valorCurtoPrazo, 0.00 as valorLongoPrazo "
							+ "FROM conta conta "
							+ "INNER JOIN localidade loca on (loca.loca_id = conta.loca_id) "
							+ "INNER JOIN credito_realizado credRealizado on (credRealizado.cnta_id = conta.cnta_id) "
							+ "INNER JOIN credito_realizado_categoria credRealizadoCat on (credRealizadoCat.crrz_id = credRealizado.crrz_id) "
							+ "WHERE loca.loca_id = :idLocalidade "
							+ "and conta.cnta_amreferenciaconta > :anoMesReferenciaContabil "
							+ "and conta.dcst_idatual in ( :situacaoNormal, :situacaoIncluida, :situacaoRetificada, :situacaoEntradaParc ) "
							+ "and ( credRealizado.crog_id = :descontoParcelamento ) "
							+ "group by loca.greg_id, loca.uneg_id, loca.loca_id, credRealizadoCat.catg_id "
							+ "UNION ALL "
							+ "SELECT loca.greg_id as idGerencia, loca.uneg_id as idUnidadeNegocio, loca.loca_id as idLocalidade, credRealizadoCat.catg_id as idCategoria, sum(credRealizadoCat.crch_vlcategoria) as valorCurtoPrazo, 0.00 as valorLongoPrazo "
							+ "FROM conta_historico conta "
							+ "INNER JOIN localidade loca on (loca.loca_id = conta.loca_id) "
							+ "INNER JOIN credito_realizado_historico credRealizado on (credRealizado.cnta_id = conta.cnta_id) "
							+ "INNER JOIN CREDITO_REALIZADO_CATG_HIST credRealizadoCat on (credrealizadocat.crhi_id = credRealizado.crhi_id) "
							+ "WHERE loca.loca_id = :idLocalidade "
							+ "and conta.cnhi_amreferenciaconta > :anoMesReferenciaContabil "
							+ "and conta.dcst_idatual in ( :situacaoNormal, :situacaoIncluida, :situacaoRetificada, :situacaoEntradaParc ) "
							+ "and (select min(pagto.pghi_amreferenciaarrecadacao) from pagamento_historico pagto where pagto.imov_id = conta.imov_id and pagto.pghi_amreferenciapagamento = conta.cnhi_amreferenciaconta) > :anoMesReferenciaContabil "
							+ "and ( credRealizado.crog_id = :descontoParcelamento ) "
							+ "GROUP BY loca.greg_id, loca.uneg_id, loca.loca_id, credRealizadoCat.catg_id "
							+ "ORDER BY idGerencia, idUnidadeNegocio, idLocalidade, idCategoria";

			retorno = session.createSQLQuery(consulta).addScalar("idGerencia", Hibernate.INTEGER).addScalar("idUnidadeNegocio",
							Hibernate.INTEGER).addScalar("idLocalidade", Hibernate.INTEGER).addScalar("idCategoria", Hibernate.INTEGER)
							.addScalar("valorCurtoPrazo", Hibernate.BIG_DECIMAL).addScalar("valorLongoPrazo", Hibernate.BIG_DECIMAL)
							.setInteger("idLocalidade", idLocalidade).setInteger("anoMesReferenciaContabil", anoMesReferenciaContabil)
							.setInteger("situacaoNormal", DebitoCreditoSituacao.NORMAL).setInteger("situacaoIncluida",
											DebitoCreditoSituacao.INCLUIDA).setInteger("situacaoRetificada",
											DebitoCreditoSituacao.RETIFICADA).setInteger("situacaoEntradaParc",
											DebitoCreditoSituacao.ENTRADA_DE_PARCELAMENTO).setInteger("descontoParcelamento",
											CreditoOrigem.DESCONTOS_CONCEDIDOS_NO_PARCELAMENTO).list();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0714] - Gerar Contas a Receber Contábil
	 * Acumula os valores dos créditos a realizar para devoluções pela gerência,
	 * localidade e categoria
	 * 
	 * @author Rafael Corrêa
	 * @date 08/11/2007
	 * @param idLocalidade
	 * @param anoMesReferenciaContabil
	 *            Ano e mês de referência contabil
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public Collection<Object[]> pesquisarDadosCreditosARealizarCategoriaDevolucao(int anoMesReferenciaContabil, Integer idLocalidade)
					throws ErroRepositorioException{

		Collection<Object[]> retorno = null;

		// cria uma sessão com o hibernate
		Session session = HibernateUtil.getSession();

		// cria a variável que vai conter o hql
		String consulta;

		try{

			consulta = "SELECT loca.greg_id as idGerencia, loca.uneg_id as idUnidadeNegocio, loca.loca_id as idLocalidade, crarCat.catg_id as idCategoria, "
							+ "sum( "
							+ "CASE WHEN ( ( crar.crar_nnprestacaocredito - crar.crar_nnprestacaorealizadas ) < 13 ) "
							+ "THEN crarCat.cacg_vlcategoria - (round( ( crarCat.cacg_vlcategoria / crar.crar_nnprestacaocredito), 2 ) * crar.crar_nnprestacaorealizadas) "
							+ "ELSE "
							+ "round( ( crarCat.cacg_vlcategoria / crar.crar_nnprestacaocredito), 2 ) * 12 "
							+ "END "
							+ ") as valorCurtoPrazo, "
							+ "sum( "
							+ "CASE WHEN ( ( crar.crar_nnprestacaocredito - crar.crar_nnprestacaorealizadas ) < 13 ) "
							+ "THEN 0.00 "
							+ "ELSE "
							+ "crarCat.cacg_vlcategoria - ( round( ( crarCat.cacg_vlcategoria / crar.crar_nnprestacaocredito ), 2 ) * ( crar.crar_nnprestacaorealizadas + 12 ) ) "
							+ "END "
							+ ") as valorLongoPrazo "
							+ "FROM credito_a_realizar_categoria crarCat "
							+ "INNER JOIN credito_a_realizar crar on crarCat.crar_id = crar.crar_id "
							+ "INNER JOIN localidade loca on crar.loca_id = loca.loca_id "
							+ "WHERE loca.loca_id = :idLocalidade "
							+ "and crar.crar_amreferenciacontabil <= :anoMesReferenciaContabil "
							+ "and crar.dcst_idatual in ( :situacaoNormal, :situacaoIncluida, :situacaoRetificada ) "
							+ "and crar.crog_id in (:devolucaoAgua, :devolucaoEsgoto, :servicoPagoIndevidamente, :devolucaoJuros)  "
							+ "GROUP BY loca.greg_id, loca.uneg_id, loca.loca_id, crarCat.catg_id "
							+ "UNION ALL "
							+ "SELECT loca.greg_id as idGerencia, loca.uneg_id as idUnidadeNegocio, loca.loca_id as idLocalidade, credRealizadoCat.catg_id as idCategoria, sum(credRealizadoCat.crcg_vlcategoria) as valorCurtoPrazo, 0.00 as valorLongoPrazo "
							+ "FROM conta conta "
							+ "INNER JOIN localidade loca on (loca.loca_id = conta.loca_id) "
							+ "INNER JOIN credito_realizado credRealizado on (credRealizado.cnta_id = conta.cnta_id) "
							+ "INNER JOIN credito_realizado_categoria credRealizadoCat on (credRealizadoCat.crrz_id = credRealizado.crrz_id) "
							+ "WHERE loca.loca_id = :idLocalidade "
							+ "and conta.cnta_amreferenciaconta > :anoMesReferenciaContabil "
							+ "and conta.dcst_idatual in ( :situacaoNormal, :situacaoIncluida, :situacaoRetificada, :situacaoEntradaParc ) "
							+ "and credRealizado.crog_id in (:devolucaoAgua, :devolucaoEsgoto, :servicoPagoIndevidamente, :devolucaoJuros) "
							+ "group by loca.greg_id, loca.uneg_id, loca.loca_id, credRealizadoCat.catg_id "
							+ "UNION ALL "
							+ "SELECT loca.greg_id as idGerencia, loca.uneg_id as idUnidadeNegocio, loca.loca_id as idLocalidade, credRealizadoCat.catg_id as idCategoria, sum(credRealizadoCat.crch_vlcategoria) as valorCurtoPrazo, 0.00 as valorLongoPrazo "
							+ "FROM conta_historico conta "
							+ "INNER JOIN localidade loca on (loca.loca_id = conta.loca_id) "
							+ "INNER JOIN credito_realizado_historico credRealizado on (credRealizado.cnta_id = conta.cnta_id) "
							+ "INNER JOIN CREDITO_REALIZADO_CATG_HIST credRealizadoCat on (credrealizadocat.crhi_id = credRealizado.crhi_id) "
							+ "WHERE loca.loca_id = :idLocalidade "
							+ "and conta.cnhi_amreferenciaconta > :anoMesReferenciaContabil "
							+ "and conta.dcst_idatual in ( :situacaoNormal, :situacaoIncluida, :situacaoRetificada, :situacaoEntradaParc ) "
							+ "and (select min(pagto.pghi_amreferenciaarrecadacao) from pagamento_historico pagto where pagto.imov_id = conta.imov_id and pagto.pghi_amreferenciapagamento = conta.cnhi_amreferenciaconta) > :anoMesReferenciaContabil "
							+ "and credRealizado.crog_id in (:devolucaoAgua, :devolucaoEsgoto, :servicoPagoIndevidamente, :devolucaoJuros) "
							+ "GROUP BY loca.greg_id, loca.uneg_id, loca.loca_id, credRealizadoCat.catg_id "
							+ "ORDER BY idGerencia, idUnidadeNegocio, idLocalidade, idCategoria";

			retorno = session.createSQLQuery(consulta).addScalar("idGerencia", Hibernate.INTEGER).addScalar("idUnidadeNegocio",
							Hibernate.INTEGER).addScalar("idLocalidade", Hibernate.INTEGER).addScalar("idCategoria", Hibernate.INTEGER)
							.addScalar("valorCurtoPrazo", Hibernate.BIG_DECIMAL).addScalar("valorLongoPrazo", Hibernate.BIG_DECIMAL)
							.setInteger("idLocalidade", idLocalidade).setInteger("anoMesReferenciaContabil", anoMesReferenciaContabil)
							.setInteger("situacaoNormal", DebitoCreditoSituacao.NORMAL).setInteger("situacaoIncluida",
											DebitoCreditoSituacao.INCLUIDA).setInteger("situacaoRetificada",
											DebitoCreditoSituacao.RETIFICADA).setInteger("situacaoEntradaParc",
											DebitoCreditoSituacao.ENTRADA_DE_PARCELAMENTO).setInteger("devolucaoAgua",
											CreditoOrigem.DEVOLUCAO_TARIFA_AGUA).setInteger("devolucaoEsgoto",
											CreditoOrigem.DEVOLUCAO_TARIFA_ESGOTO).setInteger("servicoPagoIndevidamente",
											CreditoOrigem.SERVICOS_INDIRETOS_PAGOS_INDEVIDAMENTE).setInteger("devolucaoJuros",
											CreditoOrigem.DEVOLUCAO_JUROS_PARCELAMENTO).list();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0714] - Gerar Contas a Receber Contábil
	 * Acumula os valores dos créditos a realizar para descontos incondicionais
	 * pela gerência, localidade e categoria
	 * 
	 * @author Rafael Corrêa
	 * @date 08/11/2007
	 * @param idLocalidade
	 * @param anoMesReferenciaContabil
	 *            Ano e mês de referência contabil
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public Collection<Object[]> pesquisarDadosCreditosARealizarCategoriaDescontoIncondicional(int anoMesReferenciaContabil,
					Integer idLocalidade) throws ErroRepositorioException{

		Collection<Object[]> retorno = null;

		// cria uma sessão com o hibernate
		Session session = HibernateUtil.getSession();

		// cria a variável que vai conter o hql
		String consulta;

		try{

			consulta = "SELECT loca.greg_id as idGerencia, loca.uneg_id as idUnidadeNegocio, loca.loca_id as idLocalidade, crarCat.catg_id as idCategoria, "
							+ "sum( "
							+ "CASE WHEN ( ( crar.crar_nnprestacaocredito - crar.crar_nnprestacaorealizadas ) < 13 ) "
							+ "THEN crarCat.cacg_vlcategoria - (round( ( crarCat.cacg_vlcategoria / crar.crar_nnprestacaocredito), 2 ) * crar.crar_nnprestacaorealizadas) "
							+ "ELSE "
							+ "round( ( crarCat.cacg_vlcategoria / crar.crar_nnprestacaocredito), 2 ) * 12 "
							+ "END "
							+ ") as valorCurtoPrazo, "
							+ "sum( "
							+ "CASE WHEN ( ( crar.crar_nnprestacaocredito - crar.crar_nnprestacaorealizadas ) < 13 ) "
							+ "THEN 0.00 "
							+ "ELSE "
							+ "crarCat.cacg_vlcategoria - ( round( ( crarCat.cacg_vlcategoria / crar.crar_nnprestacaocredito ), 2 ) * ( crar.crar_nnprestacaorealizadas + 12 ) ) "
							+ "END "
							+ ") as valorLongoPrazo "
							+ "FROM credito_a_realizar_categoria crarCat "
							+ "INNER JOIN credito_a_realizar crar on crarCat.crar_id = crar.crar_id "
							+ "INNER JOIN localidade loca on crar.loca_id = loca.loca_id "
							+ "WHERE loca.loca_id = :idLocalidade "
							+ "and crar.crar_amreferenciacontabil <= :anoMesReferenciaContabil "
							+ "and crar.dcst_idatual in ( :situacaoNormal, :situacaoIncluida, :situacaoRetificada ) "
							+ "and ( crar.crog_id = :descontoIncondicional ) "
							+ "GROUP BY loca.greg_id, loca.uneg_id, loca.loca_id, crarCat.catg_id "
							+ "UNION ALL "
							+ "SELECT loca.greg_id as idGerencia, loca.uneg_id as idUnidadeNegocio, loca.loca_id as idLocalidade, credRealizadoCat.catg_id as idCategoria, sum(credRealizadoCat.crcg_vlcategoria) as valorCurtoPrazo, 0.00 as valorLongoPrazo "
							+ "FROM conta conta "
							+ "INNER JOIN localidade loca on (loca.loca_id = conta.loca_id) "
							+ "INNER JOIN credito_realizado credRealizado on (credRealizado.cnta_id = conta.cnta_id) "
							+ "INNER JOIN credito_realizado_categoria credRealizadoCat on (credRealizadoCat.crrz_id = credRealizado.crrz_id) "
							+ "WHERE loca.loca_id = :idLocalidade "
							+ "and conta.cnta_amreferenciaconta > :anoMesReferenciaContabil "
							+ "and conta.dcst_idatual in ( :situacaoNormal, :situacaoIncluida, :situacaoRetificada, :situacaoEntradaParc ) "
							+ "and ( credRealizado.crog_id = :descontoIncondicional ) "
							+ "group by loca.greg_id, loca.uneg_id, loca.loca_id, credRealizadoCat.catg_id "
							+ "UNION ALL "
							+ "SELECT loca.greg_id as idGerencia, loca.uneg_id as idUnidadeNegocio, loca.loca_id as idLocalidade, credRealizadoCat.catg_id as idCategoria, sum(credRealizadoCat.crch_vlcategoria) as valorCurtoPrazo, 0.00 as valorLongoPrazo "
							+ "FROM conta_historico conta "
							+ "INNER JOIN localidade loca on (loca.loca_id = conta.loca_id) "
							+ "INNER JOIN credito_realizado_historico credRealizado on (credRealizado.cnta_id = conta.cnta_id) "
							+ "INNER JOIN CREDITO_REALIZADO_CATG_HIST credRealizadoCat on (credrealizadocat.crhi_id = credRealizado.crhi_id) "
							+ "WHERE loca.loca_id = :idLocalidade "
							+ "and conta.cnhi_amreferenciaconta > :anoMesReferenciaContabil "
							+ "and conta.dcst_idatual in ( :situacaoNormal, :situacaoIncluida, :situacaoRetificada, :situacaoEntradaParc ) "
							+ "and (select min(pagto.pghi_amreferenciaarrecadacao) from pagamento_historico pagto where pagto.imov_id = conta.imov_id and pagto.pghi_amreferenciapagamento = conta.cnhi_amreferenciaconta) > :anoMesReferenciaContabil "
							+ "and ( credRealizado.crog_id = :descontoIncondicional ) "
							+ "GROUP BY loca.greg_id, loca.uneg_id, loca.loca_id, credRealizadoCat.catg_id "
							+ "ORDER BY idGerencia, idUnidadeNegocio, idLocalidade, idCategoria";

			retorno = session.createSQLQuery(consulta).addScalar("idGerencia", Hibernate.INTEGER).addScalar("idUnidadeNegocio",
							Hibernate.INTEGER).addScalar("idLocalidade", Hibernate.INTEGER).addScalar("idCategoria", Hibernate.INTEGER)
							.addScalar("valorCurtoPrazo", Hibernate.BIG_DECIMAL).addScalar("valorLongoPrazo", Hibernate.BIG_DECIMAL)
							.setInteger("idLocalidade", idLocalidade).setInteger("anoMesReferenciaContabil", anoMesReferenciaContabil)
							.setInteger("situacaoNormal", DebitoCreditoSituacao.NORMAL).setInteger("situacaoIncluida",
											DebitoCreditoSituacao.INCLUIDA).setInteger("situacaoRetificada",
											DebitoCreditoSituacao.RETIFICADA).setInteger("situacaoEntradaParc",
											DebitoCreditoSituacao.ENTRADA_DE_PARCELAMENTO).setInteger("descontoIncondicional",
											CreditoOrigem.DESCONTOS_INCONDICIONAIS).list();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0714] - Gerar Contas a Receber Contábil
	 * Acumula os valores dos créditos a realizar para contas pagas em excesso
	 * ou em duplicidade pela gerência, localidade e categoria
	 * 
	 * @author Rafael Corrêa
	 * @date 08/11/2007
	 * @param idLocalidade
	 * @param anoMesReferenciaContabil
	 *            Ano e mês de referência contabil
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public Collection<Object[]> pesquisarDadosCreditosARealizarCategoriaPagamentoExcesso(int anoMesReferenciaContabil, Integer idLocalidade)
					throws ErroRepositorioException{

		Collection<Object[]> retorno = null;

		// cria uma sessão com o hibernate
		Session session = HibernateUtil.getSession();

		// cria a variável que vai conter o hql
		String consulta;

		try{

			consulta = "SELECT loca.greg_id as idGerencia, loca.uneg_id as idUnidadeNegocio, loca.loca_id as idLocalidade, crarCat.catg_id as idCategoria, "
							+ "sum( "
							+ "CASE WHEN ( ( crar.crar_nnprestacaocredito - crar.crar_nnprestacaorealizadas ) < 13 ) "
							+ "THEN crarCat.cacg_vlcategoria - (round( ( crarCat.cacg_vlcategoria / crar.crar_nnprestacaocredito), 2 ) * crar.crar_nnprestacaorealizadas) "
							+ "ELSE "
							+ "round( ( crarCat.cacg_vlcategoria / crar.crar_nnprestacaocredito), 2 ) * 12 "
							+ "END "
							+ ") as valorCurtoPrazo, "
							+ "sum( "
							+ "CASE WHEN ( ( crar.crar_nnprestacaocredito - crar.crar_nnprestacaorealizadas ) < 13 ) "
							+ "THEN 0.00 "
							+ "ELSE "
							+ "crarCat.cacg_vlcategoria - ( round( ( crarCat.cacg_vlcategoria / crar.crar_nnprestacaocredito ), 2 ) * ( crar.crar_nnprestacaorealizadas + 12 ) ) "
							+ "END "
							+ ") as valorLongoPrazo "
							+ "FROM credito_a_realizar_categoria crarCat "
							+ "INNER JOIN credito_a_realizar crar on crarCat.crar_id = crar.crar_id "
							+ "INNER JOIN localidade loca on crar.loca_id = loca.loca_id "
							+ "WHERE loca.loca_id = :idLocalidade "
							+ "and crar.crar_amreferenciacontabil <= :anoMesReferenciaContabil "
							+ "and crar.dcst_idatual in ( :situacaoNormal, :situacaoIncluida, :situacaoRetificada ) "
							+ "and ( crar.crog_id = :pagamentoExcesso ) "
							+ "GROUP BY loca.greg_id, loca.uneg_id, loca.loca_id, crarCat.catg_id "
							+ "UNION ALL "
							+ "SELECT loca.greg_id as idGerencia, loca.uneg_id as idUnidadeNegocio, loca.loca_id as idLocalidade, credRealizadoCat.catg_id as idCategoria, sum(credRealizadoCat.crcg_vlcategoria) as valorCurtoPrazo, 0.00 as valorLongoPrazo "
							+ "FROM conta conta "
							+ "INNER JOIN localidade loca on (loca.loca_id = conta.loca_id) "
							+ "INNER JOIN credito_realizado credRealizado on (credRealizado.cnta_id = conta.cnta_id) "
							+ "INNER JOIN credito_realizado_categoria credRealizadoCat on (credRealizadoCat.crrz_id = credRealizado.crrz_id) "
							+ "WHERE loca.loca_id = :idLocalidade "
							+ "and conta.cnta_amreferenciaconta > :anoMesReferenciaContabil "
							+ "and conta.dcst_idatual in ( :situacaoNormal, :situacaoIncluida, :situacaoRetificada, :situacaoEntradaParc ) "
							+ "and ( credRealizado.crog_id = :pagamentoExcesso ) "
							+ "group by loca.greg_id, loca.uneg_id, loca.loca_id, credRealizadoCat.catg_id "
							+ "UNION ALL "
							+ "SELECT loca.greg_id as idGerencia, loca.uneg_id as idUnidadeNegocio, loca.loca_id as idLocalidade, credRealizadoCat.catg_id as idCategoria, sum(credRealizadoCat.crch_vlcategoria) as valorCurtoPrazo, 0.00 as valorLongoPrazo "
							+ "FROM conta_historico conta "
							+ "INNER JOIN localidade loca on (loca.loca_id = conta.loca_id) "
							+ "INNER JOIN credito_realizado_historico credRealizado on (credRealizado.cnta_id = conta.cnta_id) "
							+ "INNER JOIN CREDITO_REALIZADO_CATG_HIST credRealizadoCat on (credrealizadocat.crhi_id = credRealizado.crhi_id) "
							+ "WHERE loca.loca_id = :idLocalidade "
							+ "and conta.cnhi_amreferenciaconta > :anoMesReferenciaContabil "
							+ "and conta.dcst_idatual in ( :situacaoNormal, :situacaoIncluida, :situacaoRetificada, :situacaoEntradaParc ) "
							+ "and (select min(pagto.pghi_amreferenciaarrecadacao) from pagamento_historico pagto where pagto.imov_id = conta.imov_id and pagto.pghi_amreferenciapagamento = conta.cnhi_amreferenciaconta) > :anoMesReferenciaContabil "
							+ "and ( credRealizado.crog_id = :pagamentoExcesso ) "
							+ "GROUP BY loca.greg_id, loca.uneg_id, loca.loca_id, credRealizadoCat.catg_id "
							+ "ORDER BY idGerencia, idUnidadeNegocio, idLocalidade, idCategoria";

			retorno = session.createSQLQuery(consulta).addScalar("idGerencia", Hibernate.INTEGER).addScalar("idUnidadeNegocio",
							Hibernate.INTEGER).addScalar("idLocalidade", Hibernate.INTEGER).addScalar("idCategoria", Hibernate.INTEGER)
							.addScalar("valorCurtoPrazo", Hibernate.BIG_DECIMAL).addScalar("valorLongoPrazo", Hibernate.BIG_DECIMAL)
							.setInteger("idLocalidade", idLocalidade).setInteger("anoMesReferenciaContabil", anoMesReferenciaContabil)
							.setInteger("situacaoNormal", DebitoCreditoSituacao.NORMAL).setInteger("situacaoIncluida",
											DebitoCreditoSituacao.INCLUIDA).setInteger("situacaoRetificada",
											DebitoCreditoSituacao.RETIFICADA).setInteger("situacaoEntradaParc",
											DebitoCreditoSituacao.ENTRADA_DE_PARCELAMENTO).setInteger("pagamentoExcesso",
											CreditoOrigem.CONTAS_PAGAS_EM_DUPLICIDADE_EXCESSO).list();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0714] - Gerar Contas a Receber Contábil
	 * Acumula os valores dos créditos a realizar para descontos condicionais
	 * pela gerência, localidade e categoria
	 * 
	 * @author Rafael Corrêa
	 * @date 08/11/2007
	 * @param idLocalidade
	 * @param anoMesReferenciaContabil
	 *            Ano e mês de referência contabil
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public Collection<Object[]> pesquisarDadosCreditosARealizarCategoriaDescontoCondicional(int anoMesReferenciaContabil,
					Integer idLocalidade) throws ErroRepositorioException{

		Collection<Object[]> retorno = null;

		// cria uma sessão com o hibernate
		Session session = HibernateUtil.getSession();

		// cria a variável que vai conter o hql
		String consulta;

		try{

			consulta = "SELECT loca.greg_id as idGerencia, loca.uneg_id as idUnidadeNegocio, loca.loca_id as idLocalidade, crarCat.catg_id as idCategoria, "
							+ "sum( "
							+ "CASE WHEN ( ( crar.crar_nnprestacaocredito - crar.crar_nnprestacaorealizadas ) < 13 ) "
							+ "THEN crarCat.cacg_vlcategoria - (round( ( crarCat.cacg_vlcategoria / crar.crar_nnprestacaocredito), 2 ) * crar.crar_nnprestacaorealizadas) "
							+ "ELSE "
							+ "round( ( crarCat.cacg_vlcategoria / crar.crar_nnprestacaocredito), 2 ) * 12 "
							+ "END "
							+ ") as valorCurtoPrazo, "
							+ "sum( "
							+ "CASE WHEN ( ( crar.crar_nnprestacaocredito - crar.crar_nnprestacaorealizadas ) < 13 ) "
							+ "THEN 0.00 "
							+ "ELSE "
							+ "crarCat.cacg_vlcategoria - ( round( ( crarCat.cacg_vlcategoria / crar.crar_nnprestacaocredito ), 2 ) * ( crar.crar_nnprestacaorealizadas + 12 ) ) "
							+ "END "
							+ ") as valorLongoPrazo "
							+ "FROM credito_a_realizar_categoria crarCat "
							+ "INNER JOIN credito_a_realizar crar on crarCat.crar_id = crar.crar_id "
							+ "INNER JOIN localidade loca on crar.loca_id = loca.loca_id "
							+ "WHERE loca.loca_id = :idLocalidade "
							+ "and crar.crar_amreferenciacontabil <= :anoMesReferenciaContabil "
							+ "and crar.dcst_idatual in ( :situacaoNormal, :situacaoIncluida, :situacaoRetificada ) "
							+ "and ( crar.crog_id = :descontoCondicional ) "
							+ "GROUP BY loca.greg_id, loca.uneg_id, loca.loca_id, crarCat.catg_id "
							+ "UNION ALL "
							+ "SELECT loca.greg_id as idGerencia, loca.uneg_id as idUnidadeNegocio, loca.loca_id as idLocalidade, credRealizadoCat.catg_id as idCategoria, sum(credRealizadoCat.crcg_vlcategoria) as valorCurtoPrazo, 0.00 as valorLongoPrazo "
							+ "FROM conta conta "
							+ "INNER JOIN localidade loca on (loca.loca_id = conta.loca_id) "
							+ "INNER JOIN credito_realizado credRealizado on (credRealizado.cnta_id = conta.cnta_id) "
							+ "INNER JOIN credito_realizado_categoria credRealizadoCat on (credRealizadoCat.crrz_id = credRealizado.crrz_id) "
							+ "WHERE loca.loca_id = :idLocalidade "
							+ "and conta.cnta_amreferenciaconta > :anoMesReferenciaContabil "
							+ "and conta.dcst_idatual in ( :situacaoNormal, :situacaoIncluida, :situacaoRetificada, :situacaoEntradaParc ) "
							+ "and ( credRealizado.crog_id = :descontoCondicional ) "
							+ "group by loca.greg_id, loca.uneg_id, loca.loca_id, credRealizadoCat.catg_id "
							+ "UNION ALL "
							+ "SELECT loca.greg_id as idGerencia, loca.uneg_id as idUnidadeNegocio, loca.loca_id as idLocalidade, credRealizadoCat.catg_id as idCategoria, sum(credRealizadoCat.crch_vlcategoria) as valorCurtoPrazo, 0.00 as valorLongoPrazo "
							+ "FROM conta_historico conta "
							+ "INNER JOIN localidade loca on (loca.loca_id = conta.loca_id) "
							+ "INNER JOIN credito_realizado_historico credRealizado on (credRealizado.cnta_id = conta.cnta_id) "
							+ "INNER JOIN CREDITO_REALIZADO_CATG_HIST credRealizadoCat on (credrealizadocat.crhi_id = credRealizado.crhi_id) "
							+ "WHERE loca.loca_id = :idLocalidade "
							+ "and conta.cnhi_amreferenciaconta > :anoMesReferenciaContabil "
							+ "and conta.dcst_idatual in ( :situacaoNormal, :situacaoIncluida, :situacaoRetificada, :situacaoEntradaParc ) "
							+ "and (select min(pagto.pghi_amreferenciaarrecadacao) from pagamento_historico pagto where pagto.imov_id = conta.imov_id and pagto.pghi_amreferenciapagamento = conta.cnhi_amreferenciaconta) > :anoMesReferenciaContabil "
							+ "and ( credRealizado.crog_id = :descontoCondicional ) "
							+ "GROUP BY loca.greg_id, loca.uneg_id, loca.loca_id, credRealizadoCat.catg_id "
							+ "ORDER BY idGerencia, idUnidadeNegocio, idLocalidade, idCategoria";

			retorno = session.createSQLQuery(consulta).addScalar("idGerencia", Hibernate.INTEGER).addScalar("idUnidadeNegocio",
							Hibernate.INTEGER).addScalar("idLocalidade", Hibernate.INTEGER).addScalar("idCategoria", Hibernate.INTEGER)
							.addScalar("valorCurtoPrazo", Hibernate.BIG_DECIMAL).addScalar("valorLongoPrazo", Hibernate.BIG_DECIMAL)
							.setInteger("idLocalidade", idLocalidade).setInteger("anoMesReferenciaContabil", anoMesReferenciaContabil)
							.setInteger("situacaoNormal", DebitoCreditoSituacao.NORMAL).setInteger("situacaoIncluida",
											DebitoCreditoSituacao.INCLUIDA).setInteger("situacaoRetificada",
											DebitoCreditoSituacao.RETIFICADA).setInteger("situacaoEntradaParc",
											DebitoCreditoSituacao.ENTRADA_DE_PARCELAMENTO).setInteger("descontoCondicional",
											CreditoOrigem.DESCONTOS_CONDICIONAIS).list();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0714] - Gerar Contas a Receber Contábil
	 * Acumula os valores dos créditos a realizar para ajustes para zerar contas
	 * pela gerência, localidade e categoria
	 * 
	 * @author Rafael Corrêa
	 * @date 08/11/2007
	 * @param idLocalidade
	 * @param anoMesReferenciaContabil
	 *            Ano e mês de referência contabil
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public Collection<Object[]> pesquisarDadosCreditosARealizarCategoriaAjusteZerarConta(int anoMesReferenciaContabil, Integer idLocalidade)
					throws ErroRepositorioException{

		Collection<Object[]> retorno = null;

		// cria uma sessão com o hibernate
		Session session = HibernateUtil.getSession();

		// cria a variável que vai conter o hql
		String consulta;

		try{

			consulta = "SELECT loca.greg_id as idGerencia, loca.uneg_id as idUnidadeNegocio, loca.loca_id as idLocalidade, crarCat.catg_id as idCategoria, "
							+ "sum( "
							+ "CASE WHEN ( ( crar.crar_nnprestacaocredito - crar.crar_nnprestacaorealizadas ) < 13 ) "
							+ "THEN crarCat.cacg_vlcategoria - (round( ( crarCat.cacg_vlcategoria / crar.crar_nnprestacaocredito), 2 ) * crar.crar_nnprestacaorealizadas) "
							+ "ELSE "
							+ "round( ( crarCat.cacg_vlcategoria / crar.crar_nnprestacaocredito), 2 ) * 12 "
							+ "END "
							+ ") as valorCurtoPrazo, "
							+ "sum( "
							+ "CASE WHEN ( ( crar.crar_nnprestacaocredito - crar.crar_nnprestacaorealizadas ) < 13 ) "
							+ "THEN 0.00 "
							+ "ELSE "
							+ "crarCat.cacg_vlcategoria - ( round( ( crarCat.cacg_vlcategoria / crar.crar_nnprestacaocredito ), 2 ) * ( crar.crar_nnprestacaorealizadas - 12 ) ) "
							+ "END "
							+ ") as valorLongoPrazo "
							+ "FROM credito_a_realizar_categoria crarCat "
							+ "INNER JOIN credito_a_realizar crar on crarCat.crar_id = crar.crar_id "
							+ "INNER JOIN localidade loca on crar.loca_id = loca.loca_id "
							+ "WHERE loca.loca_id = :idLocalidade "
							+ "and crar.crar_amreferenciacontabil <= :anoMesReferenciaContabil "
							+ "and crar.dcst_idatual in ( :situacaoNormal, :situacaoIncluida, :situacaoRetificada ) "
							+ "and ( crar.crog_id = :ajusteZerarConta ) "
							+ "GROUP BY loca.greg_id, loca.uneg_id, loca.loca_id, crarCat.catg_id "
							+ "UNION ALL "
							+ "SELECT loca.greg_id as idGerencia, loca.uneg_id as idUnidadeNegocio, loca.loca_id as idLocalidade, credRealizadoCat.catg_id as idCategoria, sum(credRealizadoCat.crcg_vlcategoria) as valorCurtoPrazo, 0.00 as valorLongoPrazo "
							+ "FROM conta conta "
							+ "INNER JOIN localidade loca on (loca.loca_id = conta.loca_id) "
							+ "INNER JOIN credito_realizado credRealizado on (credRealizado.cnta_id = conta.cnta_id) "
							+ "INNER JOIN credito_realizado_categoria credRealizadoCat on (credRealizadoCat.crrz_id = credRealizado.crrz_id) "
							+ "WHERE loca.loca_id = :idLocalidade "
							+ "and conta.cnta_amreferenciaconta > :anoMesReferenciaContabil "
							+ "and conta.dcst_idatual in ( :situacaoNormal, :situacaoIncluida, :situacaoRetificada, :situacaoEntradaParc ) "
							+ "and ( credRealizado.crog_id = :ajusteZerarConta ) "
							+ "group by loca.greg_id, loca.uneg_id, loca.loca_id, credRealizadoCat.catg_id "
							+ "UNION ALL "
							+ "SELECT loca.greg_id as idGerencia, loca.uneg_id as idUnidadeNegocio, loca.loca_id as idLocalidade, credRealizadoCat.catg_id as idCategoria, sum(credRealizadoCat.crch_vlcategoria) as valorCurtoPrazo, 0.00 as valorLongoPrazo "
							+ "FROM conta_historico conta "
							+ "INNER JOIN localidade loca on (loca.loca_id = conta.loca_id) "
							+ "INNER JOIN credito_realizado_historico credRealizado on (credRealizado.cnta_id = conta.cnta_id) "
							+ "INNER JOIN CREDITO_REALIZADO_CATG_HIST credRealizadoCat on (credrealizadocat.crhi_id = credRealizado.crhi_id) "
							+ "WHERE loca.loca_id = :idLocalidade "
							+ "and conta.cnhi_amreferenciaconta > :anoMesReferenciaContabil "
							+ "and conta.dcst_idatual in ( :situacaoNormal, :situacaoIncluida, :situacaoRetificada, :situacaoEntradaParc ) "
							+ "and (select min(pagto.pghi_amreferenciaarrecadacao) from pagamento_historico pagto where pagto.imov_id = conta.imov_id and pagto.pghi_amreferenciapagamento = conta.cnhi_amreferenciaconta) > :anoMesReferenciaContabil "
							+ "and ( credRealizado.crog_id = :ajusteZerarConta ) "
							+ "GROUP BY loca.greg_id, loca.uneg_id, loca.loca_id, credRealizadoCat.catg_id "
							+ "ORDER BY idGerencia, idUnidadeNegocio, idLocalidade, idCategoria";

			retorno = session.createSQLQuery(consulta).addScalar("idGerencia", Hibernate.INTEGER).addScalar("idUnidadeNegocio",
							Hibernate.INTEGER).addScalar("idLocalidade", Hibernate.INTEGER).addScalar("idCategoria", Hibernate.INTEGER)
							.addScalar("valorCurtoPrazo", Hibernate.BIG_DECIMAL).addScalar("valorLongoPrazo", Hibernate.BIG_DECIMAL)
							.setInteger("idLocalidade", idLocalidade).setInteger("anoMesReferenciaContabil", anoMesReferenciaContabil)
							.setInteger("situacaoNormal", DebitoCreditoSituacao.NORMAL).setInteger("situacaoIncluida",
											DebitoCreditoSituacao.INCLUIDA).setInteger("situacaoRetificada",
											DebitoCreditoSituacao.RETIFICADA).setInteger("situacaoEntradaParc",
											DebitoCreditoSituacao.ENTRADA_DE_PARCELAMENTO).setInteger("ajusteZerarConta",
											CreditoOrigem.AJUSTES_PARA_ZERAR_CONTA).list();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * Seleciona as quadras da localidade informada onde existe contas a serem
	 * baixadas contabiolmente
	 * [UC0485] - Gerar Resumo dos Devedores Duvidosos
	 * 
	 * @author Pedro Alexandre
	 * @date 22/11/2006
	 * @param anoMesReferenciaContabil
	 * @param idLocalidade
	 * @throws ErroRepositorioException
	 */
	public Collection<Integer> obterQuadrasPorLocalidadeParaGerarResumoDevedoresDuvidosos(int anoMesReferenciaContabil, int idLocalidade)
					throws ErroRepositorioException{

		Collection<Integer> retorno = null;

		StatelessSession session = HibernateUtil.getStatelessSession();
		String consulta;

		try{
			consulta = "select " + "distinct (cnta.qdra_id) as col_0 " + "from " + "faturamento.conta_categoria ctcg "
							+ "inner join conta cnta on ctcg.catg_id<> :idCategoria and ctcg.cnta_id=cnta.cnta_id "
							+ "inner join imovel imov on cnta.imov_id=imov.imov_id and imov.loca_id = :idLocalidade "
							+ "where cnta.loca_id=:idLocalidade " + "and cnta.dcst_idatual in ( :normal , :incluida , :retificada) "
							+ "and cnta.cnta_amreferenciaconta< :anoMesReferencia";

			retorno = session.createSQLQuery(consulta).addScalar("col_0", Hibernate.INTEGER).setInteger("idCategoria", Categoria.PUBLICO)
							.setInteger("idLocalidade", idLocalidade).setInteger("normal", DebitoCreditoSituacao.NORMAL).setInteger(
											"incluida", DebitoCreditoSituacao.INCLUIDA).setInteger("retificada",
											DebitoCreditoSituacao.RETIFICADA).setInteger("anoMesReferencia", anoMesReferenciaContabil)
							.list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0485] Gerar Resumo dos Devedores Duvidosos
	 * verifica se a conta informada possui cliente responsável
	 * com esfera de poder de tipo de cliente igual a municipal,
	 * estadual ou federal.
	 * 
	 * @author Pedro Alexandre
	 * @date 23/07/2007
	 * @param idConta
	 * @return
	 * @throws ErroRepositorioException
	 */
	public boolean verificarExistenciaClienteResponsavelConta(int idConta) throws ErroRepositorioException{

		boolean retorno = false;

		Collection retornoPesquisa = null;
		Collection<Short> colecaoIdsEsferasPoder = new ArrayList();
		colecaoIdsEsferasPoder.add(EsferaPoder.MUNICIPAL);
		colecaoIdsEsferasPoder.add(EsferaPoder.ESTADUAL);
		colecaoIdsEsferasPoder.add(EsferaPoder.FEDERAL);

		StatelessSession session = HibernateUtil.getStatelessSession();
		String consulta;

		try{
			consulta = "select clct.id " + "from ClienteConta clct " + "inner join clct.conta cnta " + "inner join clct.cliente clie "
							+ "inner join clct.clienteRelacaoTipo crtp " + "inner join clie.clienteTipo cltp "
							+ "inner join cltp.esferaPoder epod " + "where cnta.id = :idConta and crtp.id = :idRelacaoTipo "
							+ "and epod.id in (:idsEsferasPoder) ";

			retornoPesquisa = session.createQuery(consulta).setInteger("idConta", idConta).setInteger("idRelacaoTipo",
							ClienteRelacaoTipo.RESPONSAVEL).setParameterList("idsEsferasPoder", colecaoIdsEsferasPoder).list();

			if(retornoPesquisa != null && !retornoPesquisa.isEmpty()){
				retorno = true;
			}

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * Consultar Saldo da Evolucao de Contas a Receber Contabil
	 * [UC0718 - Gerar Relatório da Evolução do Contas a Receber Contábil] por Estado
	 * 
	 * @author: Francisco do Nascimento
	 * @date: 21/12/2007
	 * @param anoMesReferencia
	 *            ano/mes
	 * @param gerencia
	 *            Id da gerencia regional
	 * @param unidadeNegocio
	 *            Id da unidade de negocio
	 * @param localidade
	 *            Id da localidade
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection consultarSaldoEvolucaoContasAReceberContabilRelatorioPorEstado(int anoMesReferencia, Integer gerencia,
					Integer unidadeNegocio, Integer localidade) throws ErroRepositorioException{

		Collection retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta = null;
		try{
			// foram colocados os primeiros campos com valores fixos para permitir um unico
			// tratamento
			// na pesquisa por Estado, por Gerencia, por Unidade e por Localidade
			consulta = "select '', 0, 0, ct.id, sum(crc.valorItemLancamento) " + "from ContaAReceberContabil crc "
							+ "left join crc.categoria cat " + "left join cat.categoriaTipo ct "
							+ "where crc.anoMesReferencia = :anoMesReferencia "
							+ (gerencia != null ? " and crc.gerenciaRegional = " + gerencia : "")
							+ (unidadeNegocio != null ? " and crc.unidadeNegocio = " + unidadeNegocio : "")
							+ (localidade != null ? " and crc.localidade = " + localidade : "") + " group by ct.id " + "order by ct.id ";

			retorno = session.createQuery(consulta).setInteger("anoMesReferencia", anoMesReferencia).list();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * Consultar dados da Evolucao de Contas a Receber Contabil
	 * [UC0718 - Gerar Relatório da Evolução do Contas a Receber Contábil] por Estado
	 * 
	 * @author: Francisco do Nascimento
	 * @date: 26/12/2007
	 * @param anoMesReferencia
	 *            ano/mes
	 * @param gerencia
	 *            Id da gerencia regional
	 * @param unidadeNegocio
	 *            Id da unidade de negocio
	 * @param localidade
	 *            Id da localidade
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection consultarDadosEvolucaoContasAReceberContabilRelatorioPorEstado(int anoMesReferencia, Integer gerencia,
					Integer unidadeNegocio, Integer localidade) throws ErroRepositorioException{

		Collection retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta = null;
		try{
			// foram colocados os dois primeiros campos com valores fixos para permitir um unico
			// tratamento
			// na pesquisa por Estado, por Gerencia, por Unidade e por Localidade
			consulta = "select '', 0, rf.sequenciaTipoLancamento, ct.id, sum(rf.valorItemFaturamento) " + "from ResumoFaturamento rf "
							+ "left join rf.categoria cat " + "left join cat.categoriaTipo ct "
							+ "where rf.anoMesReferencia = :anoMesReferencia "
							+ (gerencia != null ? " and rf.gerenciaRegional = " + gerencia : "")
							+ (unidadeNegocio != null ? " and rf.unidadeNegocio = " + unidadeNegocio : "")
							+ (localidade != null ? " and rf.localidade = " + localidade : "")
							+ " group by rf.sequenciaTipoLancamento, ct.id " + "order by rf.sequenciaTipoLancamento, ct.id ";

			retorno = session.createQuery(consulta).setInteger("anoMesReferencia", anoMesReferencia).list();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * Consultar dados de recebimentos do Relatorio da Evolucao de Contas a Receber Contabil
	 * [UC0718 - Gerar Relatório da Evolução do Contas a Receber Contábil] por Estado
	 * 
	 * @author: Francisco do Nascimento
	 * @date: 07/01/2008
	 * @param anoMesReferencia
	 *            ano/mes
	 * @param gerencia
	 *            Id da gerencia regional
	 * @param unidadeNegocio
	 *            Id da unidade de negocio
	 * @param localidade
	 *            Id da localidade
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection consultarRecebimentosContasAReceberContabilRelatorioPorEstado(int anoMesReferencia, Integer gerencia,
					Integer unidadeNegocio, Integer localidade) throws ErroRepositorioException{

		Collection retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta = null;
		try{
			// foram colocados os dois primeiros campos com valores fixos para permitir um unico
			// tratamento
			// na pesquisa por Estado, por Gerencia, por Unidade e por Localidade
			consulta = "select '', 0,  ra.sequenciaTipoLancamento, ct.id, sum(ra.valorItemArrecadacao) " + "from ResumoArrecadacao ra "
							+ "left join ra.categoria cat " + "left join cat.categoriaTipo ct "
							+ "where ra.anoMesReferencia = :anoMesReferencia "
							+ (gerencia != null ? " and ra.gerenciaRegional = " + gerencia : "")
							+ (unidadeNegocio != null ? " and ra.unidadeNegocio = " + unidadeNegocio : "")
							+ (localidade != null ? " and ra.localidade = " + localidade : "")
							+ " group by ra.sequenciaTipoLancamento, ct.id " + "order by ra.sequenciaTipoLancamento, ct.id ";

			retorno = session.createQuery(consulta).setInteger("anoMesReferencia", anoMesReferencia).list();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * Consultar Saldo da Evolucao de Contas a Receber Contabil
	 * [UC0718 - Gerar Relatório da Evolução do Contas a Receber Contábil] por Estado e por Gerencia
	 * Regional
	 * 
	 * @author: Francisco do Nascimento
	 * @date: 21/12/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection consultarSaldoEvolucaoContasAReceberContabilRelatorioPorGerenciaRegional(int anoMesReferencia)
					throws ErroRepositorioException{

		Collection retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta = null;
		try{

			consulta = "select crc.gerenciaRegional.nome, crc.gerenciaRegional.id, 0, ct.id, " + "sum(crc.valorItemLancamento) "
							+ "from ContaAReceberContabil crc " + "left join crc.categoria cat " + "left join cat.categoriaTipo ct "
							+ "where crc.anoMesReferencia = :anoMesReferencia "
							+ "group by crc.gerenciaRegional.nome, crc.gerenciaRegional.id, ct.id "
							+ "order by crc.gerenciaRegional.id, ct.id ";

			retorno = session.createQuery(consulta).setInteger("anoMesReferencia", anoMesReferencia).list();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * Consultar dados da Evolucao de Contas a Receber Contabil
	 * [UC0718 - Gerar Relatório da Evolução do Contas a Receber Contábil] por Estado e por Gerencia
	 * Regional
	 * 
	 * @author: Francisco do Nascimento
	 * @date: 26/12/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection consultarDadosEvolucaoContasAReceberContabilRelatorioPorGerenciaRegional(int anoMesReferencia)
					throws ErroRepositorioException{

		Collection retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta = null;
		try{

			consulta = "select rf.gerenciaRegional.nome, rf.gerenciaRegional.id, "
							+ "rf.sequenciaTipoLancamento, ct.id, sum(rf.valorItemFaturamento) " + "from ResumoFaturamento rf "
							+ "left join rf.categoria cat " + "left join cat.categoriaTipo ct "
							+ "where rf.anoMesReferencia = :anoMesReferencia "
							+ "group by rf.gerenciaRegional.nome, rf.gerenciaRegional.id,rf.sequenciaTipoLancamento, ct.id "
							+ "order by rf.gerenciaRegional.id, rf.sequenciaTipoLancamento, ct.id ";

			retorno = session.createQuery(consulta).setInteger("anoMesReferencia", anoMesReferencia).list();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * Consultar dados de recebimentos do Relatorio da Evolucao de Contas a Receber Contabil
	 * [UC0718 - Gerar Relatório da Evolução do Contas a Receber Contábil] por Estado e por Gerencia
	 * Regional
	 * 
	 * @author: Francisco do Nascimento
	 * @date: 07/01/2008
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection consultarRecebimentosContasAReceberContabilRelatorioPorGerenciaRegional(int anoMesReferencia)
					throws ErroRepositorioException{

		Collection retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta = null;
		try{

			consulta = "select ra.gerenciaRegional.nome, ra.gerenciaRegional.id, "
							+ "ra.sequenciaTipoLancamento, ct.id, sum(ra.valorItemArrecadacao) " + "from ResumoArrecadacao ra "
							+ "left join ra.categoria cat " + "left join cat.categoriaTipo ct "
							+ "where ra.anoMesReferencia = :anoMesReferencia "
							+ "group by ra.gerenciaRegional.nome, ra.gerenciaRegional.id, ra.sequenciaTipoLancamento, ct.id "
							+ "order by ra.gerenciaRegional.id, ra.sequenciaTipoLancamento, ct.id ";

			retorno = session.createQuery(consulta).setInteger("anoMesReferencia", anoMesReferencia).list();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * Consultar Saldo da Evolucao de Contas a Receber Contabil
	 * [UC0718 - Gerar Relatório da Evolução do Contas a Receber Contábil] por Estado e por Unidade
	 * de Negocio
	 * 
	 * @author: Francisco do Nascimento
	 * @date: 21/12/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection consultarSaldoEvolucaoContasAReceberContabilRelatorioPorUnidadeNegocio(int anoMesReferencia, Integer gerencia)
					throws ErroRepositorioException{

		Collection retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta = null;
		try{

			consulta = "select crc.unidadeNegocio.nome, crc.unidadeNegocio.id , 0, ct.id, " + "sum(crc.valorItemLancamento) "
							+ "from ContaAReceberContabil crc " + "left join crc.categoria cat " + "left join cat.categoriaTipo ct "
							+ "where crc.anoMesReferencia = :anoMesReferencia "
							+ (gerencia != null ? " and crc.gerenciaRegional = " + gerencia : "")
							+ " group by crc.unidadeNegocio.nome, crc.unidadeNegocio.id, ct.id " + "order by crc.unidadeNegocio.id, ct.id ";

			retorno = session.createQuery(consulta).setInteger("anoMesReferencia", anoMesReferencia).list();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * Consultar dados da Evolucao de Contas a Receber Contabil
	 * [UC0718 - Gerar Relatório da Evolução do Contas a Receber Contábil] por Estado e por Unidade
	 * de Negocio
	 * 
	 * @author: Francisco do Nascimento
	 * @date: 26/12/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection consultarDadosEvolucaoContasAReceberContabilRelatorioPorUnidadeNegocio(int anoMesReferencia, Integer gerencia)
					throws ErroRepositorioException{

		Collection retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta = null;
		try{

			consulta = "select rf.unidadeNegocio.nome, rf.unidadeNegocio.id, "
							+ "rf.sequenciaTipoLancamento, ct.id, sum(rf.valorItemFaturamento) " + "from ResumoFaturamento rf "
							+ "left join rf.categoria cat " + "left join cat.categoriaTipo ct "
							+ "where rf.anoMesReferencia = :anoMesReferencia "
							+ (gerencia != null ? " and rf.gerenciaRegional = " + gerencia : "")
							+ " group by rf.unidadeNegocio.nome, rf.unidadeNegocio.id, rf.sequenciaTipoLancamento, ct.id "
							+ "order by rf.unidadeNegocio.id, rf.sequenciaTipoLancamento, ct.id ";

			retorno = session.createQuery(consulta).setInteger("anoMesReferencia", anoMesReferencia).list();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * Consultar dados de recebimentos do Relatorio da Evolucao de Contas a Receber Contabil
	 * [UC0718 - Gerar Relatório da Evolução do Contas a Receber Contábil] por Estado e por Unidade
	 * de Negocio
	 * 
	 * @author: Francisco do Nascimento
	 * @date: 07/01/2008
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection consultarRecebimentosContasAReceberContabilRelatorioPorUnidadeNegocio(int anoMesReferencia, Integer gerencia)
					throws ErroRepositorioException{

		Collection retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta = null;
		try{

			consulta = "select ra.unidadeNegocio.nome, ra.unidadeNegocio.id, "
							+ "ra.sequenciaTipoLancamento, ct.id, sum(ra.valorItemArrecadacao) " + "from ResumoArrecadacao ra "
							+ "left join ra.categoria cat " + "left join cat.categoriaTipo ct "
							+ "where ra.anoMesReferencia = :anoMesReferencia "
							+ (gerencia != null ? " and ra.gerenciaRegional = " + gerencia : "")
							+ " group by ra.unidadeNegocio.nome, ra.unidadeNegocio.id, ra.sequenciaTipoLancamento, ct.id "
							+ "order by ra.unidadeNegocio.id, ra.sequenciaTipoLancamento, ct.id ";

			retorno = session.createQuery(consulta).setInteger("anoMesReferencia", anoMesReferencia).list();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * Consultar Saldo da Evolucao de Contas a Receber Contabil
	 * [UC0718 - Gerar Relatório da Evolução do Contas a Receber Contábil] por Estado e por Unidade
	 * de Negocio
	 * 
	 * @author: Francisco do Nascimento
	 * @date: 21/12/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection consultarSaldoEvolucaoContasAReceberContabilRelatorioPorLocalidade(int anoMesReferencia, Integer gerencia,
					Integer unidadeNegocio) throws ErroRepositorioException{

		Collection retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta = null;
		try{

			consulta = "select crc.localidade.descricao, crc.localidade.id , 0, ct.id, " + "sum(crc.valorItemLancamento) "
							+ "from ContaAReceberContabil crc " + "left join crc.categoria cat " + "left join cat.categoriaTipo ct "
							+ "where crc.anoMesReferencia = :anoMesReferencia "
							+ (gerencia != null ? " and crc.gerenciaRegional = " + gerencia : "")
							+ (unidadeNegocio != null ? " and crc.unidadeNegocio = " + unidadeNegocio : "")
							+ " group by crc.localidade.descricao, crc.localidade.id, ct.id " + "order by crc.localidade.id, ct.id ";

			retorno = session.createQuery(consulta).setInteger("anoMesReferencia", anoMesReferencia).list();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * Consultar dados da Evolucao de Contas a Receber Contabil
	 * [UC0718 - Gerar Relatório da Evolução do Contas a Receber Contábil] por Estado e por Unidade
	 * de Negocio
	 * 
	 * @author: Francisco do Nascimento
	 * @date: 26/12/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection consultarDadosEvolucaoContasAReceberContabilRelatorioPorLocalidade(int anoMesReferencia, Integer gerencia,
					Integer unidadeNegocio) throws ErroRepositorioException{

		Collection retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta = null;
		try{

			consulta = "select rf.localidade.descricao, rf.localidade.id, "
							+ "rf.sequenciaTipoLancamento, ct.id, sum(rf.valorItemFaturamento) " + "from ResumoFaturamento rf "
							+ "left join rf.categoria cat " + "left join cat.categoriaTipo ct "
							+ "where rf.anoMesReferencia = :anoMesReferencia "
							+ (gerencia != null ? " and rf.gerenciaRegional = " + gerencia : "")
							+ (unidadeNegocio != null ? " and rf.unidadeNegocio = " + unidadeNegocio : "")
							+ " group by rf.localidade.descricao, rf.localidade.id, rf.sequenciaTipoLancamento, ct.id "
							+ "order by rf.localidade.id, rf.sequenciaTipoLancamento, ct.id ";

			retorno = session.createQuery(consulta).setInteger("anoMesReferencia", anoMesReferencia).list();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * Consultar dados de recebimentos do Relatorio da Evolucao de Contas a Receber Contabil
	 * [UC0718 - Gerar Relatório da Evolução do Contas a Receber Contábil] por Estado e por Unidade
	 * de Negocio
	 * 
	 * @author: Francisco do Nascimento
	 * @date: 07/01/2008
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection consultarRecebimentosContasAReceberContabilRelatorioPorLocalidade(int anoMesReferencia, Integer gerencia,
					Integer unidadeNegocio) throws ErroRepositorioException{

		Collection retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta = null;
		try{

			consulta = "select ra.localidade.descricao, ra.localidade.id, "
							+ "ra.sequenciaTipoLancamento, ct.id, sum(ra.valorItemArrecadacao) " + "from ResumoArrecadacao ra "
							+ "left join ra.categoria cat " + "left join cat.categoriaTipo ct "
							+ "where ra.anoMesReferencia = :anoMesReferencia "
							+ (gerencia != null ? " and ra.gerenciaRegional = " + gerencia : "")
							+ (unidadeNegocio != null ? " and ra.unidadeNegocio = " + unidadeNegocio : "")
							+ " group by ra.localidade.descricao, ra.localidade.id, ra.sequenciaTipoLancamento, ct.id "
							+ "order by ra.localidade.id, ra.sequenciaTipoLancamento, ct.id ";

			retorno = session.createQuery(consulta).setInteger("anoMesReferencia", anoMesReferencia).list();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * UC0207 - Gerar/Atualizar Resumo do Faturamento
	 * Pesquisar se um 'agrupamento' de Resumo Faturamento já existe
	 * 
	 * @author eduardo henrique
	 * @date 24/10/2008
	 * @param ResumoFaturamento
	 *            -> ResumoFaturamento deve vir com todos os dados do 'agrupamento' populados.
	 * @return ResumoFaturamento ResumoFaturamento correspondente ao 'agrupamento' passa
	 */
	public ResumoFaturamento pesquisarResumoFaturamento(ResumoFaturamento resumoFaturamentoPesquisa) throws ErroRepositorioException{

		ResumoFaturamento retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = " from ResumoFaturamento resumoFaturamento " + "where resumoFaturamento.anoMesReferencia = :anoMesReferencia "
							+ " and resumoFaturamento.dataEvento = :dataEvento "
							+ " and resumoFaturamento.gerenciaRegional = :gerenciaRegional  "
							+ " and resumoFaturamento.localidade = :localidade  "
							+ " and resumoFaturamento.unidadeNegocio = :unidadeNegocio  "
							+ " and resumoFaturamento.categoria= :categoria  " + " and resumoFaturamento.lancamentoTipo = :lancamentoTipo "
							+ " and resumoFaturamento.lancamentoItem= :lancamentoItem ";

			if(resumoFaturamentoPesquisa.getLancamentoItemContabil() != null){
				consulta += " and resumoFaturamento.lancamentoItemContabil = :lancamentoItemContabil ";
			}
			if(resumoFaturamentoPesquisa.getSetorComercial() != null){
				consulta += " and resumoFaturamento.setorComercial = :setorComercial ";
			}
			if(resumoFaturamentoPesquisa.getImovelPerfil() != null){
				consulta += " and resumoFaturamento.imovelPerfil = :imovelPerfil ";
			}

			// faz a query com os parâmetros que foram setados
			// Seta 'manualmente' a data do evento, pois reflection do Hibernate não está mapeando
			// tipo para Date
			Date dataEvento = resumoFaturamentoPesquisa.getDataEvento();
			resumoFaturamentoPesquisa.setDataEvento(null);

			retorno = (ResumoFaturamento) session.createQuery(consulta).setProperties(resumoFaturamentoPesquisa).setDate("dataEvento",
							dataEvento).setMaxResults(1).uniqueResult();
			// reset do valor original
			resumoFaturamentoPesquisa.setDataEvento(dataEvento);
		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		return retorno;

	}

	/**
	 * UC0207 - Gerar/Atualizar Resumo do Faturamento
	 * Pesquisar se um 'agrupamento' de Resumo Faturamento já existe
	 * 
	 * @author eduardo henrique
	 * @date 24/10/2008
	 * @param Collection
	 *            <ResumoFaturamento>
	 */
	public void inserirOuAtualizarResumoFaturamento(Collection<ResumoFaturamento> colecaoResumoFaturamento) throws ErroRepositorioException{

		StatelessSession session = HibernateUtil.getStatelessSession();

		Iterator iteratorColecaoResumoFaturamento = colecaoResumoFaturamento.iterator();

		try{
			while(iteratorColecaoResumoFaturamento != null && iteratorColecaoResumoFaturamento.hasNext()){

				ResumoFaturamento resumoFaturamento = (ResumoFaturamento) iteratorColecaoResumoFaturamento.next();

				if(resumoFaturamento.getId() == null){
					session.insert(resumoFaturamento);
				}else{
					session.update(resumoFaturamento);
				}
			}

		}catch(HibernateException e){
			e.printStackTrace();
			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{

			HibernateUtil.closeSession(session);
		}
	}

	/**
	 * [UC3077] Gerar Relatório Posição de Contas a Receber Contábil
	 * 
	 * @author Anderson Italo
	 * @date 20/11/2012
	 */
	public Collection<Object[]> pesquisarContasRelatorioPosicaoContasAReceberContabil(FiltroRelatorioPosicaoContasAReceberContabil filtro)
					throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();

		Collection<Object[]> retorno = null;
		StringBuffer consulta = new StringBuffer();
		String condicoes = "";

		try{

			if(filtro.getIndicadorTipoCategoria().equals(FiltroRelatorioPosicaoContasAReceberContabil.TIPO_CATEGORIA_RESIDENCIAL)){

				condicoes += " and cat.catg_id = " + Categoria.RESIDENCIAL.toString();
			}else if(filtro.getIndicadorTipoCategoria().equals(FiltroRelatorioPosicaoContasAReceberContabil.TIPO_CATEGORIA_COMERCIAL)){

				condicoes += " and cat.catg_id = " + Categoria.COMERCIAL.toString();
			}else if(filtro.getIndicadorTipoCategoria().equals(FiltroRelatorioPosicaoContasAReceberContabil.TIPO_CATEGORIA_INDUSTRIAL)){

				condicoes += " and cat.catg_id = " + Categoria.INDUSTRIAL.toString();
			}else if(filtro.getIndicadorTipoCategoria().equals(FiltroRelatorioPosicaoContasAReceberContabil.TIPO_CATEGORIA_PUBLICO)){

				condicoes += " and cat.catg_id = " + Categoria.PUBLICO.toString();
			}else if(filtro.getIndicadorTipoCategoria().equals(FiltroRelatorioPosicaoContasAReceberContabil.TIPO_CATEGORIA_PARTICULAR)){

				condicoes += " and cat.catg_id <> " + Categoria.PUBLICO.toString();
			}

			if(filtro.getIdGerenciaRegional() != null){

				condicoes += " and gre.greg_id = " + filtro.getIdGerenciaRegional().toString();
			}

			if(filtro.getIdLocalidade() != null){

				condicoes += " and loc.loca_id = " + filtro.getIdLocalidade().toString();
			}

			String primeiraParteConsulta = "";
			if(filtro.getOpcaoTotalizacao().contains("estado")){

				if(filtro.getOpcaoTotalizacao().equals("estadoGerencia")){

					primeiraParteConsulta = " idGerencia, nomeGerencia, ";
				}else if(filtro.getOpcaoTotalizacao().equals("estadoLocalidade")){

					primeiraParteConsulta = " idLocalidade, nomeLocalidade,";
				}
			}else if(filtro.getOpcaoTotalizacao().contains("gerenciaRegional")){

				if(filtro.getOpcaoTotalizacao().equals("gerenciaRegional")){

					primeiraParteConsulta = " idGerencia, nomeGerencia, ";
				}else if(filtro.getOpcaoTotalizacao().equals("gerenciaRegionalLocalidade")){

					primeiraParteConsulta = " idGerencia, nomeGerencia, idLocalidade, nomeLocalidade,";
				}
			}else if(filtro.getOpcaoTotalizacao().equals("localidade")){

				primeiraParteConsulta = " idLocalidade, nomeLocalidade,";
			}

			consulta.append(" select " + primeiraParteConsulta + "                                       ");
			consulta.append(" idCategoria, nomeCategoria, tipoLancamento,                                                          ");
			consulta.append(" sum(valorAVencer) as val1, sum(valorAtrasoAte30Dias) as val2, sum(valorAtraso30A60Dias) as val3,     ");
			consulta.append(" sum(valorAtraso60A90Dias) as val4,sum(valorAtrasoMais90Dias) as val5, sum(valorTotalLinha) as val6,  ");
			consulta.append(" sum(quantidadeAVencer) as qtd1, sum(quantidadeAtrasoAte30Dias) as qtd2,                              ");
			consulta.append(" sum(quantidadeAtraso30A60Dias) as qtd3, sum(quantidadeAtraso60A90Dias) as qtd4,                      ");
			consulta.append(" sum(quantidadeAtrasoMais90Dias) as qtd5,sum(quantidadeTotalLinha) as qtd6                            ");
			consulta.append(" from (                                                                                               ");
			consulta.append(" select gre.greg_id as idGerencia, gre.greg_nmregional as nomeGerencia,                               ");
			consulta.append(" loc.loca_id as idLocalidade, loc.loca_nmlocalidade as nomeLocalidade,                                ");
			consulta.append(" cat.catg_id as idCategoria, cat.catg_dscategoria as nomeCategoria,                                   ");
			consulta.append(" 'Água' as tipoLancamento,                                                                            ");
			consulta.append(" (case when con.cnta_dtvencimentoconta > current_timestamp                                            ");
			consulta.append(" then sum(nvl(cct.ctcg_vlagua,0)) else 0 end) as valorAVencer,                                        ");
			consulta.append(" (case when con.cnta_dtvencimentoconta < current_timestamp and (to_date(to_char(current_timestamp,    ");
			consulta.append(" 'dd/mm/yy'), 'dd/mm/yy') - con.cnta_dtvencimentoconta) < 30                                          ");
			consulta.append(" then sum(nvl(cct.ctcg_vlagua,0)) else 0 end) as valorAtrasoAte30Dias,                                ");
			consulta.append(" (case when con.cnta_dtvencimentoconta < current_timestamp and (to_date(to_char(current_timestamp,    ");
			consulta.append(" 'dd/mm/yy'), 'dd/mm/yy') - con.cnta_dtvencimentoconta) >= 30                                         ");
			consulta.append(" and (to_date(to_char(current_timestamp, 'dd/mm/yy'), 'dd/mm/yy') - con.cnta_dtvencimentoconta) < 60  ");
			consulta.append(" then sum(nvl(cct.ctcg_vlagua,0)) else 0 end) as valorAtraso30A60Dias,                                ");
			consulta.append(" (case when con.cnta_dtvencimentoconta < current_timestamp and (to_date(to_char(current_timestamp,    ");
			consulta.append(" 'dd/mm/yy'), 'dd/mm/yy') - con.cnta_dtvencimentoconta) >= 60                                         ");
			consulta.append(" and (to_date(to_char(current_timestamp, 'dd/mm/yy'), 'dd/mm/yy') - con.cnta_dtvencimentoconta) < 90  ");
			consulta.append(" then sum(nvl(cct.ctcg_vlagua,0)) else 0 end) as valorAtraso60A90Dias,                                ");
			consulta.append(" (case when con.cnta_dtvencimentoconta < current_timestamp and (to_date(to_char(current_timestamp,    ");
			consulta.append(" 'dd/mm/yy'), 'dd/mm/yy') - con.cnta_dtvencimentoconta) > 90                                          ");
			consulta.append(" then sum(nvl(cct.ctcg_vlagua,0)) else 0 end) as valorAtrasoMais90Dias,                               ");
			consulta.append(" sum(nvl(cct.ctcg_vlagua,0)) as valorTotalLinha,                                                      ");
			consulta.append(" (case when con.cnta_dtvencimentoconta > current_timestamp                                            ");
			consulta.append(" then count(distinct(con.cnta_id)) else 0 end) as quantidadeAVencer,                                  ");
			consulta.append(" (case when con.cnta_dtvencimentoconta < current_timestamp and (to_date(to_char(current_timestamp,    ");
			consulta.append(" 'dd/mm/yy'), 'dd/mm/yy') - con.cnta_dtvencimentoconta) < 30                                          ");
			consulta.append(" then count(distinct(con.cnta_id)) else 0 end) as quantidadeAtrasoAte30Dias,                          ");
			consulta.append(" (case when con.cnta_dtvencimentoconta < current_timestamp and (to_date(to_char(current_timestamp,    ");
			consulta.append(" 'dd/mm/yy'), 'dd/mm/yy') - con.cnta_dtvencimentoconta) >= 30                                         ");
			consulta.append(" and (to_date(to_char(current_timestamp, 'dd/mm/yy'), 'dd/mm/yy') - con.cnta_dtvencimentoconta) < 60  ");
			consulta.append(" then count(distinct(con.cnta_id)) else 0 end) quantidadeAtraso30A60Dias,                             ");
			consulta.append(" (case when con.cnta_dtvencimentoconta < current_timestamp and (to_date(to_char(current_timestamp,    ");
			consulta.append(" 'dd/mm/yy'), 'dd/mm/yy') - con.cnta_dtvencimentoconta) >= 60                                         ");
			consulta.append(" and (to_date(to_char(current_timestamp, 'dd/mm/yy'), 'dd/mm/yy') - con.cnta_dtvencimentoconta) < 90  ");
			consulta.append(" then count(distinct(con.cnta_id)) else 0 end) as quantidadeAtraso60A90Dias,                          ");
			consulta.append(" (case when con.cnta_dtvencimentoconta < current_timestamp and (to_date(to_char(current_timestamp,    ");
			consulta.append(" 'dd/mm/yy'), 'dd/mm/yy') - con.cnta_dtvencimentoconta) > 90                                          ");
			consulta.append(" then count(distinct(con.cnta_id)) else 0 end) as quantidadeAtrasoMais90Dias,                         ");
			consulta.append(" count(distinct(con.cnta_id)) as quantidadeTotalLinha                                                 ");
			consulta.append(" from conta_categoria cct                                                                             ");
			consulta.append(" inner join conta con on con.cnta_id = cct.cnta_id                                                    ");
			consulta.append(" inner join categoria cat on cat.catg_id = cct.catg_id                                                ");
			consulta.append(" inner join localidade loc on loc.loca_id = con.loca_id                                               ");
			consulta.append(" inner join gerencia_regional gre on gre.greg_id = loc.greg_id                                        ");
			consulta.append(" where                                                                                                ");
			consulta.append(" con.cnta_amreferenciacontabil <= " + filtro.getAnoMesReferencia());
			consulta.append(" and con.dcst_idatual in (");
			consulta.append(DebitoCreditoSituacao.NORMAL.toString() + ",");
			consulta.append(DebitoCreditoSituacao.RETIFICADA.toString() + ",");
			consulta.append(DebitoCreditoSituacao.INCLUIDA.toString() + ")");
			consulta.append(condicoes);
			consulta.append(" group by gre.greg_id, gre.greg_nmregional, loc.loca_id, loc.loca_nmlocalidade, cat.catg_id,          ");
			consulta.append(" cat.catg_dscategoria, 'Água', con.cnta_dtvencimentoconta                                             ");
			consulta.append("                                                                                                      ");
			consulta.append(" union all                                                                                            ");
			consulta.append("                                                                                                      ");
			consulta.append(" select gre.greg_id as idGerencia, gre.greg_nmregional as nomeGerencia,                               ");
			consulta.append(" loc.loca_id as idLocalidade, loc.loca_nmlocalidade as nomeLocalidade,                                ");
			consulta.append(" cat.catg_id as idCategoria, cat.catg_dscategoria as nomeCategoria,                                   ");
			consulta.append(" 'Esgoto' as tipoLancamento,                                                                          ");
			consulta.append(" (case when con.cnta_dtvencimentoconta > current_timestamp                                            ");
			consulta.append(" then sum(nvl(cct.ctcg_vlesgoto,0)) else 0 end) as valorAVencer,                                      ");
			consulta.append(" (case when con.cnta_dtvencimentoconta < current_timestamp and (to_date(to_char(current_timestamp,    ");
			consulta.append(" 'dd/mm/yy'), 'dd/mm/yy') - con.cnta_dtvencimentoconta) < 30                                          ");
			consulta.append(" then sum(nvl(cct.ctcg_vlesgoto,0)) else 0 end) as valorAtrasoAte30Dias,                              ");
			consulta.append(" (case when con.cnta_dtvencimentoconta < current_timestamp and (to_date(to_char(current_timestamp,    ");
			consulta.append(" 'dd/mm/yy'), 'dd/mm/yy') - con.cnta_dtvencimentoconta) >= 30                                         ");
			consulta.append(" and (to_date(to_char(current_timestamp, 'dd/mm/yy'), 'dd/mm/yy') - con.cnta_dtvencimentoconta) < 60  ");
			consulta.append(" then sum(nvl(cct.ctcg_vlesgoto,0)) else 0 end) as valorAtraso30A60Dias,                              ");
			consulta.append(" (case when con.cnta_dtvencimentoconta < current_timestamp and (to_date(to_char(current_timestamp,    ");
			consulta.append(" 'dd/mm/yy'), 'dd/mm/yy') - con.cnta_dtvencimentoconta) >= 60                                         ");
			consulta.append(" and (to_date(to_char(current_timestamp, 'dd/mm/yy'), 'dd/mm/yy') - con.cnta_dtvencimentoconta) < 90  ");
			consulta.append(" then sum(nvl(cct.ctcg_vlesgoto,0)) else 0 end) as valorAtraso60A90Dias,                              ");
			consulta.append(" (case when con.cnta_dtvencimentoconta < current_timestamp and (to_date(to_char(current_timestamp,    ");
			consulta.append(" 'dd/mm/yy'), 'dd/mm/yy') - con.cnta_dtvencimentoconta) > 90                                          ");
			consulta.append(" then sum(nvl(cct.ctcg_vlesgoto,0)) else 0 end) as valorAtrasoMais90Dias,                             ");
			consulta.append(" sum(nvl(cct.ctcg_vlesgoto,0)) as valorTotalLinha,                                                    ");
			consulta.append(" 0 as quantidadeAVencer, 0 as quantidadeAtrasoAte30Dias,                                              ");
			consulta.append(" 0 as quantidadeAtraso30A60Dias, 0 as quantidadeAtraso60A90Dias,                                      ");
			consulta.append(" 0 as quantidadeAtrasoMais90Dias,0 as quantidadeTotalLinha                                            ");
			consulta.append(" from conta_categoria cct                                                                             ");
			consulta.append(" inner join conta con on con.cnta_id = cct.cnta_id                                                    ");
			consulta.append(" inner join categoria cat on cat.catg_id = cct.catg_id                                                ");
			consulta.append(" inner join localidade loc on loc.loca_id = con.loca_id                                               ");
			consulta.append(" inner join gerencia_regional gre on gre.greg_id = loc.greg_id                                        ");
			consulta.append(" where                                                                                                ");
			consulta.append(" con.cnta_amreferenciacontabil <= " + filtro.getAnoMesReferencia());
			consulta.append(" and con.dcst_idatual in (");
			consulta.append(DebitoCreditoSituacao.NORMAL.toString() + ",");
			consulta.append(DebitoCreditoSituacao.RETIFICADA.toString() + ",");
			consulta.append(DebitoCreditoSituacao.INCLUIDA.toString() + ")");
			consulta.append(condicoes);
			consulta.append(" group by gre.greg_id, gre.greg_nmregional, loc.loca_id, loc.loca_nmlocalidade, cat.catg_id,          ");
			consulta.append(" cat.catg_dscategoria, 'Esgoto', con.cnta_dtvencimentoconta                                           ");
			consulta.append("                                                                                                      ");
			consulta.append(" union all                                                                                            ");
			consulta.append("                                                                                                      ");
			consulta.append(" select gre.greg_id as idGerencia, gre.greg_nmregional as nomeGerencia,                               ");
			consulta.append(" loc.loca_id as idLocalidade, loc.loca_nmlocalidade as nomeLocalidade,                                ");
			consulta.append(" cat.catg_id as idCategoria, cat.catg_dscategoria as nomeCategoria,                                   ");
			consulta.append(" 'Financiamento' as tipoLancamento,                                                                   ");
			consulta.append(" (case when con.cnta_dtvencimentoconta > current_timestamp                                            ");
			consulta.append(" then sum(nvl(dcat.dccg_vlcategoria,0)) else 0 end) as valorAVencer,                                  ");
			consulta.append(" (case when con.cnta_dtvencimentoconta < current_timestamp and (to_date(to_char(current_timestamp,    ");
			consulta.append(" 'dd/mm/yy'), 'dd/mm/yy') - con.cnta_dtvencimentoconta) < 30                                          ");
			consulta.append(" then sum(nvl(dcat.dccg_vlcategoria,0)) else 0 end) as valorAtrasoAte30Dias,                          ");
			consulta.append(" (case when con.cnta_dtvencimentoconta < current_timestamp and (to_date(to_char(current_timestamp,    ");
			consulta.append(" 'dd/mm/yy'), 'dd/mm/yy') - con.cnta_dtvencimentoconta) >= 30                                         ");
			consulta.append(" and (to_date(to_char(current_timestamp, 'dd/mm/yy'), 'dd/mm/yy') - con.cnta_dtvencimentoconta) < 60  ");
			consulta.append(" then sum(nvl(dcat.dccg_vlcategoria,0)) else 0 end) as valorAtraso30A60Dias,                          ");
			consulta.append(" (case when con.cnta_dtvencimentoconta < current_timestamp and (to_date(to_char(current_timestamp,    ");
			consulta.append(" 'dd/mm/yy'), 'dd/mm/yy') - con.cnta_dtvencimentoconta) >= 60                                         ");
			consulta.append(" and (to_date(to_char(current_timestamp, 'dd/mm/yy'), 'dd/mm/yy') - con.cnta_dtvencimentoconta) < 90  ");
			consulta.append(" then sum(nvl(dcat.dccg_vlcategoria,0)) else 0 end) as valorAtraso60A90Dias,                          ");
			consulta.append(" (case when con.cnta_dtvencimentoconta < current_timestamp and (to_date(to_char(current_timestamp,    ");
			consulta.append(" 'dd/mm/yy'), 'dd/mm/yy') - con.cnta_dtvencimentoconta) > 90                                          ");
			consulta.append(" then sum(nvl(dcat.dccg_vlcategoria,0)) else 0 end) as valorAtrasoMais90Dias,                         ");
			consulta.append(" sum(nvl(dcat.dccg_vlcategoria,0)) as valorTotalLinha,                                                ");
			consulta.append(" 0 as quantidadeAVencer, 0 as quantidadeAtrasoAte30Dias,                                              ");
			consulta.append(" 0 as quantidadeAtraso30A60Dias, 0 as quantidadeAtraso60A90Dias,                                      ");
			consulta.append(" 0 as quantidadeAtrasoMais90Dias,0 as quantidadeTotalLinha                                            ");
			consulta.append(" from conta con                                                                                       ");
			consulta.append(" inner join debito_cobrado dc on dc.cnta_id = con.cnta_id                                             ");
			consulta.append(" inner join debito_cobrado_categoria dcat on dcat.dbcb_id = dc.dbcb_id                                ");
			consulta.append(" inner join categoria cat on cat.catg_id = dcat.catg_id                                               ");
			consulta.append(" inner join localidade loc on loc.loca_id = con.loca_id                                               ");
			consulta.append(" inner join gerencia_regional gre on gre.greg_id = loc.greg_id                                        ");
			consulta.append(" where                                                                                                ");
			consulta.append(" con.cnta_amreferenciacontabil <= " + filtro.getAnoMesReferencia());
			consulta.append(" and con.dcst_idatual in (");
			consulta.append(DebitoCreditoSituacao.NORMAL.toString() + ",");
			consulta.append(DebitoCreditoSituacao.RETIFICADA.toString() + ",");
			consulta.append(DebitoCreditoSituacao.INCLUIDA.toString() + ")");
			consulta.append(condicoes);
			consulta.append(" and (dc.dbtp_id not in (select ps.pasi_vlparametro from parametro_sistema                            ");
			consulta.append(" ps where ps.pasi_cdparametro like 'P_TIPO_DEBITO_PARCELAMENTO%'))                                    ");
			consulta.append(" group by gre.greg_id, gre.greg_nmregional, loc.loca_id, loc.loca_nmlocalidade, cat.catg_id,          ");
			consulta.append(" cat.catg_dscategoria, 'Financiamento', con.cnta_dtvencimentoconta                                    ");
			consulta.append("                                                                                                      ");
			consulta.append(" union all                                                                                            ");
			consulta.append("                                                                                                      ");
			consulta.append(" select  gre.greg_id as idGerencia, gre.greg_nmregional as nomeGerencia,                              ");
			consulta.append(" loc.loca_id as idLocalidade, loc.loca_nmlocalidade as nomeLocalidade,                                ");
			consulta.append(" cat.catg_id as idCategoria, cat.catg_dscategoria as nomeCategoria,                                   ");
			consulta.append(" 'Financiamento' as tipoLancamento,                                                                   ");
			consulta.append(" (case when con.cnta_dtvencimentoconta > current_timestamp                                            ");
			consulta.append(" then (sum(nvl(crcat.crcg_vlcategoria,0)) * -1) else 0 end) as valorAVencer,                          ");
			consulta.append(" (case when con.cnta_dtvencimentoconta < current_timestamp and (to_date(to_char(current_timestamp,    ");
			consulta.append(" 'dd/mm/yy'), 'dd/mm/yy') - con.cnta_dtvencimentoconta) < 30                                          ");
			consulta.append(" then (sum(nvl(crcat.crcg_vlcategoria,0)) * -1) else 0 end) as valorAtrasoAte30Dias,                  ");
			consulta.append(" (case when con.cnta_dtvencimentoconta < current_timestamp and (to_date(to_char(current_timestamp,    ");
			consulta.append(" 'dd/mm/yy'), 'dd/mm/yy') - con.cnta_dtvencimentoconta) >= 30                                         ");
			consulta.append(" and (to_date(to_char(current_timestamp, 'dd/mm/yy'), 'dd/mm/yy') - con.cnta_dtvencimentoconta) < 60  ");
			consulta.append(" then (sum(nvl(crcat.crcg_vlcategoria,0)) * -1) else 0 end) as valorAtraso30A60Dias,                  ");
			consulta.append(" (case when con.cnta_dtvencimentoconta < current_timestamp and (to_date(to_char(current_timestamp,    ");
			consulta.append(" 'dd/mm/yy'), 'dd/mm/yy') - con.cnta_dtvencimentoconta) >= 60                                         ");
			consulta.append(" and (to_date(to_char(current_timestamp, 'dd/mm/yy'), 'dd/mm/yy') - con.cnta_dtvencimentoconta) < 90  ");
			consulta.append(" then (sum(nvl(crcat.crcg_vlcategoria,0)) * -1) else 0 end) as valorAtraso60A90Dias,                  ");
			consulta.append(" (case when con.cnta_dtvencimentoconta < current_timestamp and (to_date(to_char(current_timestamp,    ");
			consulta.append(" 'dd/mm/yy'), 'dd/mm/yy') - con.cnta_dtvencimentoconta) > 90                                          ");
			consulta.append(" then (sum(nvl(crcat.crcg_vlcategoria,0)) * -1) else 0 end) as valorAtrasoMais90Dias,                 ");
			consulta.append(" (sum(nvl(crcat.crcg_vlcategoria,0)) * -1) as valorTotalLinha,                                        ");
			consulta.append(" 0 as quantidadeAVencer, 0 as quantidadeAtrasoAte30Dias,                                              ");
			consulta.append(" 0 as quantidadeAtraso30A60Dias, 0 as quantidadeAtraso60A90Dias,                                      ");
			consulta.append(" 0 as quantidadeAtrasoMais90Dias,0 as quantidadeTotalLinha                                            ");
			consulta.append(" from conta con                                                                                       ");
			consulta.append(" inner join credito_realizado cr on cr.cnta_id = con.cnta_id                                          ");
			consulta.append(" inner join credito_realizado_categoria crcat on crcat.crrz_id = cr.crrz_id                           ");
			consulta.append(" inner join categoria cat on cat.catg_id = crcat.catg_id                                              ");
			consulta.append(" inner join localidade loc on loc.loca_id = con.loca_id                                               ");
			consulta.append(" inner join gerencia_regional gre on gre.greg_id = loc.greg_id                                        ");
			consulta.append(" where                                                                                                ");
			consulta.append(" con.cnta_amreferenciacontabil <= " + filtro.getAnoMesReferencia());
			consulta.append(" and con.dcst_idatual in (");
			consulta.append(DebitoCreditoSituacao.NORMAL.toString() + ",");
			consulta.append(DebitoCreditoSituacao.RETIFICADA.toString() + ",");
			consulta.append(DebitoCreditoSituacao.INCLUIDA.toString() + ")");
			consulta.append(condicoes);
			consulta.append(" group by gre.greg_id, gre.greg_nmregional, loc.loca_id, loc.loca_nmlocalidade, cat.catg_id,          ");
			consulta.append(" cat.catg_dscategoria, 'Financiamento', con.cnta_dtvencimentoconta                                    ");
			consulta.append("                                                                                                      ");
			consulta.append(" union all                                                                                            ");
			consulta.append("                                                                                                      ");
			consulta.append(" select gre.greg_id as idGerencia, gre.greg_nmregional as nomeGerencia,                               ");
			consulta.append(" loc.loca_id as idLocalidade, loc.loca_nmlocalidade as nomeLocalidade,                                ");
			consulta.append(" cat.catg_id as idCategoria, cat.catg_dscategoria as nomeCategoria,                                   ");
			consulta.append(" 'Parcelamento' as tipoLancamento,                                                                    ");
			consulta.append(" (case when con.cnta_dtvencimentoconta > current_timestamp                                            ");
			consulta.append(" then sum(nvl(dcat.dccg_vlcategoria,0)) else 0 end) as valorAVencer,                                  ");
			consulta.append(" (case when con.cnta_dtvencimentoconta < current_timestamp and (to_date(to_char(current_timestamp,    ");
			consulta.append(" 'dd/mm/yy'), 'dd/mm/yy') - con.cnta_dtvencimentoconta) < 30                                          ");
			consulta.append(" then sum(nvl(dcat.dccg_vlcategoria,0)) else 0 end) as valorAtrasoAte30Dias,                          ");
			consulta.append(" (case when con.cnta_dtvencimentoconta < current_timestamp and (to_date(to_char(current_timestamp,    ");
			consulta.append(" 'dd/mm/yy'), 'dd/mm/yy') - con.cnta_dtvencimentoconta) >= 30                                         ");
			consulta.append(" and (to_date(to_char(current_timestamp, 'dd/mm/yy'), 'dd/mm/yy') - con.cnta_dtvencimentoconta) < 60  ");
			consulta.append(" then sum(nvl(dcat.dccg_vlcategoria,0)) else 0 end) as valorAtraso30A60Dias,                          ");
			consulta.append(" (case when con.cnta_dtvencimentoconta < current_timestamp and (to_date(to_char(current_timestamp,    ");
			consulta.append(" 'dd/mm/yy'), 'dd/mm/yy') - con.cnta_dtvencimentoconta) >= 60                                         ");
			consulta.append(" and (to_date(to_char(current_timestamp, 'dd/mm/yy'), 'dd/mm/yy') - con.cnta_dtvencimentoconta) < 90  ");
			consulta.append(" then sum(nvl(dcat.dccg_vlcategoria,0)) else 0 end) as valorAtraso60A90Dias,                          ");
			consulta.append(" (case when con.cnta_dtvencimentoconta < current_timestamp and (to_date(to_char(current_timestamp,    ");
			consulta.append(" 'dd/mm/yy'), 'dd/mm/yy') - con.cnta_dtvencimentoconta) > 90                                          ");
			consulta.append(" then sum(nvl(dcat.dccg_vlcategoria,0)) else 0 end) as valorAtrasoMais90Dias,                         ");
			consulta.append(" sum(nvl(dcat.dccg_vlcategoria,0)) as valorTotalLinha,                                                ");
			consulta.append(" 0 as quantidadeAVencer, 0 as quantidadeAtrasoAte30Dias,                                              ");
			consulta.append(" 0 as quantidadeAtraso30A60Dias, 0 as quantidadeAtraso60A90Dias,                                      ");
			consulta.append(" 0 as quantidadeAtrasoMais90Dias,0 as quantidadeTotalLinha                                            ");
			consulta.append(" from conta con                                                                                       ");
			consulta.append(" inner join debito_cobrado dc on dc.cnta_id = con.cnta_id                                             ");
			consulta.append(" inner join debito_cobrado_categoria dcat on dcat.dbcb_id = dc.dbcb_id                                ");
			consulta.append(" inner join categoria cat on cat.catg_id = dcat.catg_id                                               ");
			consulta.append(" inner join localidade loc on loc.loca_id = con.loca_id                                               ");
			consulta.append(" inner join gerencia_regional gre on gre.greg_id = loc.greg_id                                        ");
			consulta.append(" where                                                                                                ");
			consulta.append(" con.cnta_amreferenciacontabil <= " + filtro.getAnoMesReferencia());
			consulta.append(" and con.dcst_idatual in (");
			consulta.append(DebitoCreditoSituacao.NORMAL.toString() + ",");
			consulta.append(DebitoCreditoSituacao.RETIFICADA.toString() + ",");
			consulta.append(DebitoCreditoSituacao.INCLUIDA.toString() + ")");
			consulta.append(condicoes);
			consulta.append(" and (dc.dbtp_id in (select ps.pasi_vlparametro from parametro_sistema                                ");
			consulta.append(" ps where ps.pasi_cdparametro like 'P_TIPO_DEBITO_PARCELAMENTO%'))                                    ");
			consulta.append(" group by gre.greg_id, gre.greg_nmregional, loc.loca_id, loc.loca_nmlocalidade, cat.catg_id,          ");
			consulta.append(" cat.catg_dscategoria, 'Parcelamento', con.cnta_dtvencimentoconta                                     ");
			consulta.append("                                                                                                      ");
			consulta.append(" )                                                                                                    ");
			consulta.append(" group by " + primeiraParteConsulta + "                                     ");
			consulta.append(" idCategoria, nomeCategoria, tipoLancamento                                                           ");
			consulta.append(" order by " + primeiraParteConsulta + "                                     ");
			consulta.append(" idCategoria, nomeCategoria, tipoLancamento                                                           ");

			if(filtro.getOpcaoTotalizacao().equals("gerenciaRegionalLocalidade")){

				retorno = (Collection<Object[]>) session.createSQLQuery(consulta.toString()).addScalar("idGerencia", Hibernate.INTEGER)
								.addScalar("nomeGerencia", Hibernate.STRING).addScalar("idLocalidade", Hibernate.INTEGER).addScalar(
												"nomeLocalidade", Hibernate.STRING).addScalar("idCategoria", Hibernate.INTEGER).addScalar(
												"nomeCategoria", Hibernate.STRING).addScalar("tipoLancamento", Hibernate.STRING).addScalar(
												"val1", Hibernate.BIG_DECIMAL).addScalar("val2", Hibernate.BIG_DECIMAL).addScalar("val3",
												Hibernate.BIG_DECIMAL).addScalar("val4", Hibernate.BIG_DECIMAL).addScalar("val5",
												Hibernate.BIG_DECIMAL).addScalar("val6", Hibernate.BIG_DECIMAL).addScalar("qtd1",
												Hibernate.INTEGER).addScalar("qtd2", Hibernate.INTEGER)
								.addScalar("qtd3", Hibernate.INTEGER).addScalar("qtd4", Hibernate.INTEGER).addScalar("qtd5",
												Hibernate.INTEGER).addScalar("qtd6", Hibernate.INTEGER).list();
			}else if(filtro.getOpcaoTotalizacao().equals("gerenciaRegional") || filtro.getOpcaoTotalizacao().equals("estadoGerencia")){

				retorno = (Collection<Object[]>) session.createSQLQuery(consulta.toString()).addScalar("idGerencia", Hibernate.INTEGER)
								.addScalar("nomeGerencia", Hibernate.STRING).addScalar("idCategoria", Hibernate.INTEGER).addScalar(
												"nomeCategoria", Hibernate.STRING).addScalar("tipoLancamento", Hibernate.STRING).addScalar(
												"val1", Hibernate.BIG_DECIMAL).addScalar("val2", Hibernate.BIG_DECIMAL).addScalar("val3",
												Hibernate.BIG_DECIMAL).addScalar("val4", Hibernate.BIG_DECIMAL).addScalar("val5",
												Hibernate.BIG_DECIMAL).addScalar("val6", Hibernate.BIG_DECIMAL).addScalar("qtd1",
												Hibernate.INTEGER).addScalar("qtd2", Hibernate.INTEGER)
								.addScalar("qtd3", Hibernate.INTEGER).addScalar("qtd4", Hibernate.INTEGER).addScalar("qtd5",
												Hibernate.INTEGER).addScalar("qtd6", Hibernate.INTEGER).list();
			}else if(filtro.getOpcaoTotalizacao().equals("localidade") || filtro.getOpcaoTotalizacao().equals("estadoLocalidade")){

				retorno = (Collection<Object[]>) session.createSQLQuery(consulta.toString()).addScalar("idLocalidade", Hibernate.INTEGER)
								.addScalar("nomeLocalidade", Hibernate.STRING).addScalar("idCategoria", Hibernate.INTEGER).addScalar(
												"nomeCategoria", Hibernate.STRING).addScalar("tipoLancamento", Hibernate.STRING).addScalar(
												"val1", Hibernate.BIG_DECIMAL).addScalar("val2", Hibernate.BIG_DECIMAL).addScalar("val3",
												Hibernate.BIG_DECIMAL).addScalar("val4", Hibernate.BIG_DECIMAL).addScalar("val5",
												Hibernate.BIG_DECIMAL).addScalar("val6", Hibernate.BIG_DECIMAL).addScalar("qtd1",
												Hibernate.INTEGER).addScalar("qtd2", Hibernate.INTEGER)
								.addScalar("qtd3", Hibernate.INTEGER).addScalar("qtd4", Hibernate.INTEGER).addScalar("qtd5",
												Hibernate.INTEGER).addScalar("qtd6", Hibernate.INTEGER).list();
			}else{

				retorno = (Collection<Object[]>) session.createSQLQuery(consulta.toString()).addScalar("idCategoria", Hibernate.INTEGER)
								.addScalar("nomeCategoria", Hibernate.STRING).addScalar("tipoLancamento", Hibernate.STRING).addScalar(
												"val1", Hibernate.BIG_DECIMAL).addScalar("val2", Hibernate.BIG_DECIMAL).addScalar("val3",
												Hibernate.BIG_DECIMAL).addScalar("val4", Hibernate.BIG_DECIMAL).addScalar("val5",
												Hibernate.BIG_DECIMAL).addScalar("val6", Hibernate.BIG_DECIMAL).addScalar("qtd1",
												Hibernate.INTEGER).addScalar("qtd2", Hibernate.INTEGER)
								.addScalar("qtd3", Hibernate.INTEGER).addScalar("qtd4", Hibernate.INTEGER).addScalar("qtd5",
												Hibernate.INTEGER).addScalar("qtd6", Hibernate.INTEGER).list();
			}

		}catch(HibernateException e){

			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{

			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC3077] Gerar Relatório Posição de Contas a Receber Contábil
	 * 
	 * @author Anderson Italo
	 * @date 20/11/2012
	 */
	public Collection<Object[]> obterDebitosACobrarRelatorioPosicaoContasAReceberContabil(Integer idGerenciaRegional, Integer idLocalidade,
					Integer anoMesReferencia) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();

		Collection<Object[]> retorno = null;
		StringBuffer consulta = new StringBuffer();

		try{

			String condicoes = "";

			if(idLocalidade != null){

				condicoes += " and loc.loca_id = " + idLocalidade.toString();
			}else if(idGerenciaRegional != null){

				condicoes += " and loc.greg_id = " + idGerenciaRegional.toString();
			}

			consulta.append(" select sum(valor) as val1, categ, tipo from (");
			consulta.append(" select sum(nvl(dcat.dbcg_vlcategoria,0)) as valor, ");
			consulta.append(" dcat.catg_id as categ, 'Financiamento' as tipo ");
			consulta.append(" from imovel im ");
			consulta.append(" inner join debito_a_cobrar dbac on dbac.imov_id = im.imov_id ");
			consulta.append(" inner join debito_a_cobrar_categoria dcat on dbac.dbac_id = dcat.dbac_id ");
			consulta.append(" inner join localidade loc on loc.loca_id = im.loca_id ");
			consulta.append(" where ");

			consulta.append(" dbac.dbac_amreferenciacontabil <= " + anoMesReferencia.toString());
			consulta.append(" and dbac.dbtp_id not in (select ps.pasi_vlparametro from parametro_sistema ");
			consulta.append(" ps where ps.pasi_cdparametro like 'P_TIPO_DEBITO_PARCELAMENTO%') ");
			consulta.append(condicoes);

			consulta.append(" group by dcat.catg_id, 'Financiamento' ");

			consulta.append(" union all ");

			consulta.append(" select (sum(nvl(crcat.cacg_vlcategoria,0)) * -1) as valor, ");
			consulta.append(" crcat.catg_id as categ, 'Financiamento' as tipo ");
			consulta.append(" from imovel im ");
			consulta.append(" inner join credito_a_realizar cr on cr.imov_id = im.imov_id ");
			consulta.append(" inner join credito_a_realizar_categoria crcat on crcat.crar_id = im.imov_id ");
			consulta.append(" inner join localidade loc on loc.loca_id = im.loca_id ");
			consulta.append(" where ");

			consulta.append(" cr.crar_amreferenciacontabil <= " + anoMesReferencia.toString());
			consulta.append(condicoes);

			consulta.append(" group by crcat.catg_id, 'Financiamento' ");

			consulta.append(" union all ");

			consulta.append(" select sum(nvl(dcat.dbcg_vlcategoria,0)) as valor, ");
			consulta.append(" dcat.catg_id as categ, 'Parcelamento' as tipo ");
			consulta.append(" from imovel im ");
			consulta.append(" inner join debito_a_cobrar dbac on dbac.imov_id = im.imov_id ");
			consulta.append(" inner join debito_a_cobrar_categoria dcat on dbac.dbac_id = dcat.dbac_id ");
			consulta.append(" inner join localidade loc on loc.loca_id = im.loca_id ");
			consulta.append(" where ");
			consulta.append(" dbac.dbac_amreferenciacontabil <= " + anoMesReferencia.toString());
			consulta.append(" and dbac.dbtp_id in (select ps.pasi_vlparametro from parametro_sistema ");
			consulta.append(" ps where ps.pasi_cdparametro like 'P_TIPO_DEBITO_PARCELAMENTO%') ");
			consulta.append(condicoes);

			consulta.append(" group by dcat.catg_id, 'Parcelamento' ");
			consulta.append(") group by categ, tipo");
			consulta.append(" order by categ, tipo ");

			retorno = (Collection<Object[]>) session.createSQLQuery(consulta.toString()).addScalar("val1", Hibernate.BIG_DECIMAL)
							.addScalar("categ", Hibernate.INTEGER).addScalar("tipo", Hibernate.STRING).list();

		}catch(HibernateException e){

			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{

			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * 
	 * gcom.financeiro.IRepositorioFinanceiro#calcularQtdaRegistrosRelatorioSaldoContasAReceberContabil
	 * (java.util.Map)
	 */
	public Integer calcularQtdaRegistrosRelatorioSaldoContasAReceberContabil(Map<String, Object> filtro) throws ErroRepositorioException{

		Session session = null;
		Integer retorno = 0;

		try{

			session = HibernateUtil.getSession();

			Criteria criteria = this.obterCriteriaRelatorioSaldoContasAReceberContabil(filtro, session);
			criteria.setProjection(Projections.rowCount());

			retorno = (Integer) criteria.uniqueResult();

		}catch(HibernateException e){

			e.printStackTrace();
			throw new ErroRepositorioException("Erro no Hibernate");

		}finally{

			HibernateUtil.closeSession(session);

		}

		return retorno;

	}

	/*
	 * (non-Javadoc)
	 * @see
	 * gcom.financeiro.IRepositorioFinanceiro#obterRegistrosRelatorioSaldoContasAReceberContabil
	 * (java.util.Map)
	 */
	public List<ContaAReceberContabil> obterRegistrosRelatorioSaldoContasAReceberContabil(Map<String, Object> filtro)
					throws ErroRepositorioException{

		Session session = null;
		List<ContaAReceberContabil> retorno = new ArrayList<ContaAReceberContabil>();

		try{

			session = HibernateUtil.getSession();

			Criteria criteria = this.obterCriteriaRelatorioSaldoContasAReceberContabil(filtro, session);

			retorno = criteria.list();

		}catch(HibernateException e){

			e.printStackTrace();
			throw new ErroRepositorioException("Erro no Hibernate");

		}finally{

			HibernateUtil.closeSession(session);

		}

		return retorno;

	}

	private Criteria obterCriteriaRelatorioSaldoContasAReceberContabil(Map<String, Object> filtro, Session session)
					throws ErroRepositorioException{

		Criteria criteria = session.createCriteria(ContaAReceberContabil.class);
		criteria.createAlias("gerenciaRegional", "gerenciaRegional", Criteria.INNER_JOIN);
		criteria.createAlias("categoria", "categoria", Criteria.INNER_JOIN);
		criteria.createAlias("categoria.categoriaTipo", "categoriaTipo", Criteria.INNER_JOIN);
		criteria.createAlias("unidadeNegocio", "unidadeNegocio", Criteria.INNER_JOIN);
		criteria.createAlias("localidade", "localidade", Criteria.INNER_JOIN);
		criteria.createAlias("lancamentoTipo", "lancamentoTipo", Criteria.INNER_JOIN);
		criteria.createAlias("lancamentoItem", "lancamentoItem", Criteria.INNER_JOIN);
		criteria.add(Restrictions.eq("anoMesReferencia", filtro.get(RelatorioSaldoContasAReceberContabil.ANO_MES_REFERENCIA)));

		String opcaoTotalizacao = (String) filtro.get(RelatorioSaldoContasAReceberContabil.OPCAO_TOTALIZACAO);

		if(opcaoTotalizacao.equals(RelatorioSaldoContasAReceberContabil.TOTALIZACAO_POR_ESTADO)){

			// 3.1. Estado (agrupando e ordenando o valor por Seqüência do Tipo de Lançamento,
			// Seqüência do Item de Lançamento e Categoria);
			criteria.addOrder(Order.asc("numeroSequenciaTipoLancamento"));
			criteria.addOrder(Order.asc("numeroSequenciaItemTipoLancamento"));
			criteria.addOrder(Order.asc("categoria.descricao"));

		}else if(opcaoTotalizacao.equals(RelatorioSaldoContasAReceberContabil.TOTALIZACAO_ESTADO_POR_GERENCIA_REGIONAL)){

			// 3.2. Estado por Gerência Regional (agrupando e ordenando o valor por Gerência
			// Regional, Seqüência do Tipo de Lançamento, Seqüência do Item de Lançamento e
			// Categoria);
			criteria.addOrder(Order.asc("gerenciaRegional.nome"));
			criteria.addOrder(Order.asc("numeroSequenciaTipoLancamento"));
			criteria.addOrder(Order.asc("numeroSequenciaItemTipoLancamento"));
			criteria.addOrder(Order.asc("categoria.descricao"));

		}else if(opcaoTotalizacao.equals(RelatorioSaldoContasAReceberContabil.TOTALIZACAO_ESTADO_POR_UNIDADE_NEGOCIO)){

			// 3.3. Estado por Unidade de Negócio (agrupando e ordenando o valor por Gerência
			// Regional,Unidade de Negócio,Seqüência do Tipo de Lançamento, Seqüência do Item de
			// Lançamento e Categoria);
			criteria.addOrder(Order.asc("gerenciaRegional.nome"));
			criteria.addOrder(Order.asc("unidadeNegocio.nome"));
			criteria.addOrder(Order.asc("numeroSequenciaTipoLancamento"));
			criteria.addOrder(Order.asc("numeroSequenciaItemTipoLancamento"));
			criteria.addOrder(Order.asc("categoria.descricao"));

		}else if(opcaoTotalizacao.equals(RelatorioSaldoContasAReceberContabil.TOTALIZACAO_ESTADO_POR_LOCALIDADE)){

			// 3.4. Estado por Localidade (agrupando e ordenando o valor por Gerência
			// Regional,Unidade de Negocio,Localidade, Seqüência do Tipo de Lançamento, Seqüência do
			// Item de Lançamento e Categoria);
			criteria.addOrder(Order.asc("gerenciaRegional.nome"));
			criteria.addOrder(Order.asc("unidadeNegocio.nome"));
			criteria.addOrder(Order.asc("localidade.descricao"));
			criteria.addOrder(Order.asc("numeroSequenciaTipoLancamento"));
			criteria.addOrder(Order.asc("numeroSequenciaItemTipoLancamento"));
			criteria.addOrder(Order.asc("categoria.descricao"));

		}else if(opcaoTotalizacao.equals(RelatorioSaldoContasAReceberContabil.TOTALIZACAO_POR_GERENCIA_REGIONAL)){

			// 3.5. Gerência Regional (selecionando apenas a gerência regional informada (GREG_ID),
			// agrupando e ordenando o valor por Seqüência do Tipo de Lançamento, Seqüência do Item
			// de Lançamento e Categoria);
			criteria.add(Restrictions.eq("gerenciaRegional.id", filtro.get(RelatorioSaldoContasAReceberContabil.GERENCIA_REGIONAL)));
			criteria.addOrder(Order.asc("numeroSequenciaTipoLancamento"));
			criteria.addOrder(Order.asc("numeroSequenciaItemTipoLancamento"));
			criteria.addOrder(Order.asc("categoria.descricao"));

		}else if(opcaoTotalizacao.equals(RelatorioSaldoContasAReceberContabil.TOTALIZACAO_POR_UNIDADE_NEGOCIO)){

			// 3.6. Unidade de Negócio (selecionando apenas a Unidade de Negócio informada
			// (UNNE_ID), agrupando e ordenando o valor por Seqüência do Tipo de Lançamento,
			// Seqüência do Item de Lançamento e Categoria);

			// 3.10. Unidade de Negócio (selecionando apenas a unidade de negócio informada
			// (UNNE_ID), agrupando e ordenando o valor por Seqüência do Tipo de Lançamento,
			// Seqüência do Item de Lançamento e Categoria).
			criteria.add(Restrictions.eq("unidadeNegocio.id", filtro.get(RelatorioSaldoContasAReceberContabil.UNIDADE_NEGOCIO)));
			criteria.addOrder(Order.asc("numeroSequenciaTipoLancamento"));
			criteria.addOrder(Order.asc("numeroSequenciaItemTipoLancamento"));
			criteria.addOrder(Order.asc("categoria.descricao"));

		}else if(opcaoTotalizacao.equals(RelatorioSaldoContasAReceberContabil.TOTALIZACAO_GERENCIA_REGIONAL_POR_UNIDADE_NEGOCIO)){

			// 3.7. Gerência Regional por Unidade de Negócio (selecionando apenas a gerência
			// regional informada (GREG_ID), agrupando e ordenando o valor por Unidade de Negócio,
			// Seqüência do Tipo de Lançamento, Seqüência do Item de Lançamento e Categoria);
			criteria.add(Restrictions.eq("gerenciaRegional.id", filtro.get(RelatorioSaldoContasAReceberContabil.GERENCIA_REGIONAL)));
			criteria.addOrder(Order.asc("unidadeNegocio.nome"));
			criteria.addOrder(Order.asc("numeroSequenciaTipoLancamento"));
			criteria.addOrder(Order.asc("numeroSequenciaItemTipoLancamento"));
			criteria.addOrder(Order.asc("categoria.descricao"));

		}else if(opcaoTotalizacao.equals(RelatorioSaldoContasAReceberContabil.TOTALIZACAO_GERENCIAL_REGIONAL_POR_LOCALIDADE)){

			// 3.8. Gerência Regional por Localidade (selecionando apenas a gerência regional
			// informada (GREG_ID), agrupando e ordenando o valor por Unidade de Negócio,
			// Localidade, Seqüência do Tipo de Lançamento, Seqüência do Item de Lançamento e
			// Categoria);
			criteria.add(Restrictions.eq("gerenciaRegional.id", filtro.get(RelatorioSaldoContasAReceberContabil.GERENCIA_REGIONAL)));
			criteria.addOrder(Order.asc("unidadeNegocio.nome"));
			criteria.addOrder(Order.asc("localidade.descricao"));
			criteria.addOrder(Order.asc("numeroSequenciaTipoLancamento"));
			criteria.addOrder(Order.asc("numeroSequenciaItemTipoLancamento"));
			criteria.addOrder(Order.asc("categoria.descricao"));

		}else if(opcaoTotalizacao.equals(RelatorioSaldoContasAReceberContabil.TOTALIZACAO_UNIDADE_NEGOCIO_POR_LOCALIDADE)){

			// 3.9. Unidade de Negócio por Localidade (selecionando apenas a unidade de negócio
			// informada (UNEG_ID), agrupando e ordenando o valor por Localidade, Seqüência do Tipo
			// de Lançamento, Seqüência do Item de Lançamento e Categoria).
			criteria.add(Restrictions.eq("unidadeNegocio.id", filtro.get(RelatorioSaldoContasAReceberContabil.UNIDADE_NEGOCIO)));
			criteria.addOrder(Order.asc("localidade.descricao"));
			criteria.addOrder(Order.asc("numeroSequenciaTipoLancamento"));
			criteria.addOrder(Order.asc("numeroSequenciaItemTipoLancamento"));
			criteria.addOrder(Order.asc("categoria.descricao"));

		}else if(opcaoTotalizacao.equals(RelatorioSaldoContasAReceberContabil.TOTALIZACAO_POR_LOCALIDADE)){

			// 3.11. Localidade (selecionando apenas a localidade informada (LOCA_ID), agrupando e
			// ordenando o valor por Seqüência do Tipo de Lançamento, Seqüência do Item de
			// Lançamento e Categoria).
			criteria.add(Restrictions.eq("localidade.id", filtro.get(RelatorioSaldoContasAReceberContabil.LOCALIDADE)));
			criteria.addOrder(Order.asc("numeroSequenciaTipoLancamento"));
			criteria.addOrder(Order.asc("numeroSequenciaItemTipoLancamento"));
			criteria.addOrder(Order.asc("categoria.descricao"));

		}

		return criteria;

	}

}