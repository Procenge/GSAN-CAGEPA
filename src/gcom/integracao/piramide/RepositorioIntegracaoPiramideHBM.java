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
package gcom.integracao.piramide;

import gcom.integracao.piramide.bean.LancamentoDeferimentoAnteriorHelper;
import gcom.integracao.piramide.bean.LancamentoDeferimentoHelper;
import gcom.util.ControladorException;
import gcom.util.ErroRepositorioException;
import gcom.util.HibernateUtil;
import gcom.util.Util;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.*;
import org.hibernate.transform.Transformers;

/**
 * O repositório faz a comunicação com a base de dados através do hibernate.
 * 
 * @author Pitang
 */
public class RepositorioIntegracaoPiramideHBM
				implements IRepositorioIntegracaoPiramide {

	private static Logger LOGGER = Logger.getLogger(RepositorioIntegracaoPiramideHBM.class);
	private static IRepositorioIntegracaoPiramide instancia;

	/**
	 * Retorna o valor de instância
	 * 
	 * @return O valor de instância
	 */
	public static IRepositorioIntegracaoPiramide getInstancia(){

		if(instancia == null){
			instancia = new RepositorioIntegracaoPiramideHBM();
		}

		return instancia;
	}

	/**
	 * [UC3065] Gerar Integração Contábil
	 * [SB0001] – Obter data inicial de lançamento para a Integração Contábil
	 * 
	 * @return dataInicial
	 * @throws ErroRepositorioException
	 */
	public Date obterDataInicialLancamentoIntegracaoContabil() throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		Date retorno = null;
		try{

			String consulta = " select MAX(DAT_LANCAMENTO) from TI_GRP_CONT_GCS_N ";
			retorno = (Date) session.createSQLQuery(consulta).setMaxResults(1).uniqueResult();

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
	 * [UC3066] Gerar Integração Retenção
	 * [SB0001 – Obter data inicial de pagamento para a integração de retenção]
	 * 
	 * @return dataInicial
	 * @throws ControladorException
	 */
	public Date obterDataInicialPagamentoRetencao() throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		Date retorno = null;
		try{

			String consulta = " select MAX(retida.DAT_RETENCAO) from TI_APU_CSOC_RETIDA retida ";
			retorno = (Date) session.createSQLQuery(consulta).setMaxResults(1).uniqueResult();

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
	 * Método obterMaiorReferenciaGerada
	 * <p>
	 * Esse método implementa a busca da maior referência já gerada a partir da tabela
	 * TI_DIFER_PERIODO.
	 * </p>
	 * RASTREIO: [OC827869][UC3067][SB0001]
	 * 
	 * @return Referencia no formato YYYYMM
	 * @author Marlos Ribeiro
	 * @throws ErroRepositorioException
	 * @since 03/10/2012
	 */
	public String obterMaiorReferenciaGerada() throws ErroRepositorioException{

		String consulta = "SELECT MAX(SUBSTR(COD_MES_ANO_REFERENCIA,3,4) || SUBSTR(COD_MES_ANO_REFERENCIA,1,2) ) FROM TI_DIFER_PERIODO";

		Session session = HibernateUtil.getSession();
		String referenciaYYYYMM = null;
		try{
			referenciaYYYYMM = (String) session.createSQLQuery(consulta).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		return referenciaYYYYMM;
	}

	/**
	 * Método quantaidadeLancamentosDeferimento
	 * <p>
	 * Esse método implementa consulta a quantidade de lançamentos na tabela TI_DIF_ANT_REC_PER para
	 * a referência base
	 * </p>
	 * RASTREIO: [OC827869][UC3067][FS0004]
	 * 
	 * @param referenciaMesAno
	 *            Referencia no formato MMYYYY
	 * @return quantidade de lancamentos
	 * @author Marlos Ribeiro
	 * @since 03/10/2012
	 */
	public Integer quantaidadeLancamentosDeferimento(String referenciaMesAno) throws ErroRepositorioException{

		StringBuffer consultaBuffer = new StringBuffer();
		consultaBuffer.append("SELECT COUNT(*) FROM ");
		consultaBuffer.append(TabelaIntegracaoDeferimentoAnteriorReferenciaBase.class.getName());
		consultaBuffer.append(" TI_DIF_ANT WHERE ");
		consultaBuffer.append(" TI_DIF_ANT.chavePrimaria.mesAnoReferencia = :referenciaMesAno");

		Integer retorno = null;

		Session session = HibernateUtil.getSession();
		try{
			Query query = session.createQuery(consultaBuffer.toString());
			query.setString("referenciaMesAno", referenciaMesAno);
			query.setMaxResults(1);
			Object resultado = query.uniqueResult();
			retorno = ((Number) resultado).intValue();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * Método consultarContasDeferimento
	 * <p>
	 * Esse método implementa consulta de : <br>
	 * contas com referência igual a referência base, categoria publica e que o CPF/CGC do
	 * proprietário ou inquilino estejam preenchidos a partir da tabela CONTA com
	 * CNTA_AMREFERENCIACONTA igual referencia base; só exista apenas uma ocorrência na tabela
	 * CONTA_CATEGORIA com CNTA_ID igual a CNTA_ID da tabela CONTA que esta única ocorrência seja
	 * com CATG_ID = 4 <br>
	 * <br>
	 * UNIAO com <br>
	 * <br>
	 * contas em histórico com referência igual a referência base, categoria publica e que o CPF/CGC
	 * do proprietário ou inquilino estejam preenchidos a partir da tabela CONTA-HISTORICO com
	 * CNHI_AMREFERENCIACONTA igual referencia base; sejam de situação Normal, Incluída ou
	 * Retificadas (DCST_IDATUAL igual a 0, 1 ou 2); só exista apenas uma ocorrência na tabela
	 * CONTA_CATEGORIA_HISTORICO com CNTA_ID igual a CNTA_ID da tabela CONTA_HISTORICO que esta
	 * única ocorrência seja com CATG_ID = 4 e que tenham sido pagas depois da data final de
	 * deferimento (PGHI_DTPAGAMENTO > data final deferimento a partir da tabela PAGAMENTO_HISTORICO
	 * com CNTA_ID = CNTA_ID da tabela CONTA_HISTORICO). <br>
	 * <br>
	 * AGRUPAR POR código de regional e CNPJ/CPF
	 * </p>
	 * RASTREIO: [OC827869][UC3067][SB0003.1.1], [SB0003.1.2] e [SB0003.2]
	 * 
	 * @param referenciaBase
	 * @param categoriaConta
	 * @param tiposClienteRelacionamento
	 * @return
	 * @author Marlos Ribeiro
	 * @param dataFimDeferimento
	 * @param situacaoConta
	 * @since 05/10/2012
	 */
	public List<LancamentoDeferimentoHelper> consultarContasDeferimento(String referenciaBase, Integer categoriaConta,
					Integer[] tiposClienteRelacionamento, Date dataFimDeferimento, Integer[] situacaoConta)
					throws ErroRepositorioException{

		StringBuffer buffer = new StringBuffer();
		// [OC827869][UC3067][SB0003.1.1]
		buffer.append("(SELECT ");
		buffer.append("   l.GREG_ID codigoRegional, ");
		buffer.append("   CASE ");
		buffer.append("   WHEN cl.CLIE_NNCPF  IS NOT NULL  THEN cl.CLIE_NNCPF ");
		buffer.append("   WHEN cl.CLIE_NNCNPJ IS NOT NULL  THEN cl.CLIE_NNCNPJ ");
		buffer.append("   ELSE  NULL ");
		buffer.append("   END  documentoCliente, ");
		buffer.append("    ct.IMOV_ID matriculaImovel, ");
		buffer.append("    (ct.CNTA_VLAGUA + ct.CNTA_VLESGOTO + ct.CNTA_VLDEBITOS - ct.CNTA_VLCREDITOS )  valorLancamento ");
		buffer.append("FROM ");
		buffer.append("  CONTA  ct ");
		buffer.append(" ,LOCALIDADE  l ");
		buffer.append(" ,CLIENTE  cl ");
		buffer.append(" ,CLIENTE_CONTA  cc ");
		buffer.append(" ,CLIENTE_TIPO CTP  ");
		buffer.append("WHERE ");
		buffer.append(" ct.CNTA_AMREFERENCIACONTA = :referenciaBase ");
		buffer.append(" AND ct.CNTA_ID  IN (SELECT ccat.CNTA_ID   FROM  CONTA_CATEGORIA ccat ");
		buffer.append(" WHERE ccat.CNTA_ID  = ct.CNTA_ID ");
		buffer.append(" AND ccat.CATG_ID = :categoriaConta ");
		buffer.append(" GROUP BY   ccat.CNTA_ID ");
		buffer.append(" HAVING COUNT(ccat.CNTA_ID)  = 1 ) ");
		buffer.append(" AND  ct.LOCA_ID  =  l.LOCA_ID ");
		buffer.append(" AND  ct.CNTA_ID = cc.CNTA_ID ");
		buffer.append(" AND  cc.CLIE_ID  = cl.CLIE_ID ");
		buffer.append(" AND  CTP.CLTP_ID = CL.CLTP_ID ");
		buffer.append(" AND  cl.CLIE_NNCNPJ  IS NOT NULL ");
		buffer.append(" AND  CTP.EPOD_ID IN (1,2,3) ");
		buffer.append(" AND  cc.CRTP_ID  =  (SELECT NVL(MAX(CC1.CRTP_ID),1) ");
		buffer.append(" FROM CLIENTE_CONTA CC1, CLIENTE  CLI  WHERE CC1.CNTA_ID = ct.CNTA_ID AND CC1.CLIE_ID = CLI.CLIE_ID  AND CLI.CLIE_NNCNPJ IS NOT NULL )) ");
		buffer.append("UNION ");
		// [OC827869][UC3067][SB0003.1.2]
		buffer.append("(SELECT l.GREG_ID codigoRegional, ");
		buffer.append(" CASE WHEN cl.CLIE_NNCPF  IS NOT NULL  THEN cl.CLIE_NNCPF ");
		buffer.append(" WHEN cl.CLIE_NNCNPJ IS NOT NULL  THEN cl.CLIE_NNCNPJ ");
		buffer.append(" ELSE NULL ");
		buffer.append(" END  cnpj_cpf, ");
		buffer.append(" ch.IMOV_ID     matriculaImovel, ");
		buffer.append(" ch.CNHI_VLAGUA + ch.CNHI_VLESGOTO + ch.CNHI_VLDEBITOS - ch.CNHI_VLCREDITOS valorLancamento ");
		buffer.append(" FROM CONTA_HISTORICO ch ");
		buffer.append("  , PAGAMENTO_HISTORICO ph ");
		buffer.append("  , LOCALIDADE l ");
		buffer.append("  , CLIENTE cl ");
		buffer.append("  , CLIENTE_CONTA_HISTORICO cch ");
		buffer.append("  , CLIENTE_TIPO CTP  ");
		buffer.append("  WHERE ch.CNHI_AMREFERENCIACONTA = :referenciaBase ");
		buffer.append("  AND ph.PGHI_DTPAGAMENTO  > :dataFimDeferimento ");
		buffer.append("  AND ch.DCST_IDATUAL IN (:situacaoConta) ");
		buffer.append("  AND ch.CNTA_ID  IN (SELECT cch1.CNTA_ID   FROM CONTA_CATEGORIA_HISTORICO cch1 ");
		buffer.append("  WHERE cch1.CNTA_ID  = ch.CNTA_ID AND cch1.CATG_ID = :categoriaConta ");
		buffer.append("  GROUP BY    cch1.CNTA_ID  HAVING COUNT(cch1.CNTA_ID) = 1 )  "); 
		buffer.append("  AND  ph.LOCA_ID =  l.LOCA_ID  AND  ph.CNTA_ID = ch.CNTA_ID ");
		buffer.append("  AND  ch.CNTA_ID = cch.CNTA_ID AND  cch.CLIE_ID = cl.CLIE_ID ");
		buffer.append("  AND  CTP.CLTP_ID = CL.CLTP_ID ");
		buffer.append("  AND  cl.CLIE_NNCNPJ  IS NOT NULL ");
		buffer.append("  AND   cch.CRTP_ID =  (SELECT NVL(MAX(CCH2.CRTP_ID),1) ");
		buffer.append("  FROM CLIENTE_CONTA_HISTORICO CCH2, CLIENTE CLI  WHERE CCH2.CNTA_ID =  CH.CNTA_ID");
		buffer.append("  AND CCH2.CLIE_ID =  CLI.CLIE_ID  AND CLI.CLIE_NNCNPJ IS NOT NULL )) ");
		// [OC827869][UC3067][SB0003.2] - AGRUPAMENTO
		buffer.append("  ORDER BY codigoRegional,documentoCliente ");
		
		Session session = HibernateUtil.getSession();
		List<LancamentoDeferimentoHelper> retorno;
		try{
			Query query = session.createSQLQuery(buffer.toString())//
							.addScalar("codigoRegional", Hibernate.INTEGER)//
							.addScalar("documentoCliente", Hibernate.STRING)
							.addScalar("matriculaImovel", Hibernate.INTEGER)//
							.addScalar("valorLancamento", Hibernate.BIG_DECIMAL)//
			;
			query.setString("referenciaBase", referenciaBase);
			query.setInteger("categoriaConta", categoriaConta);
			// query.setParameterList("tiposClienteRelacionamento", tiposClienteRelacionamento);
			query.setParameterList("situacaoConta", situacaoConta);
			query.setDate("dataFimDeferimento", dataFimDeferimento);
			query.setResultTransformer(Transformers.aliasToBean(LancamentoDeferimentoHelper.class));
			retorno = query.list();
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * Método consultarPercentualAliquota
	 * <p>
	 * Esse método implementa consulta da Percentual mais recente da Alíquota (IMTA_PCALIQUOTA da
	 * tabela IMPOSTO_TIPO_ALIQUOTA com IMTP_ID = tipo e MAX (IMTA_AMREFERENCIA)
	 * </p>
	 * 
	 * @param i
	 * @return
	 * @author Marlos Ribeiro
	 * @throws ErroRepositorioException
	 * @since 05/10/2012
	 */
	public BigDecimal consultarPercentualAliquota(int imtpID) throws ErroRepositorioException{

		String consulta = "SELECT i.IMTA_PCALIQUOTA FROM IMPOSTO_TIPO_ALIQUOTA i WHERE i.IMTP_ID = :tipo AND i.IMTA_AMREFERENCIA = (SELECT MAX(IMTA_AMREFERENCIA) FROM IMPOSTO_TIPO_ALIQUOTA  WHERE IMTP_ID = :tipo)";
		Session session = HibernateUtil.getSession();
		BigDecimal retorno;
		try{
			Query query = session.createSQLQuery(consulta);
			query.setInteger("tipo", imtpID);
			query.setMaxResults(1);
			retorno = (BigDecimal) query.uniqueResult();
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * Método consultaValorNaoRecebido
	 * <p>
	 * Esse método implementa Somatório dos Valores das contas para a mesma regional e mesmo CNPJ /
	 * CPF (CNTA_VLAGUA + CNTA_VLESGOTO + CNTA_VLDEBITOS + CNTA_VLCREDITOS a partir da tabela CONTA
	 * com LOCA_ID = LOCA_ID da tabela LOCALIDADE com GERE_ID = COD_FILIAL_ORIGEM e CNTA_ID =
	 * CNTA_ID da tabela CLIENTE_CONTA com CLIE_ID = CLIE_ID da tabela CLIENTE com CLIE_NNCNPJ ou
	 * CLIE_NNCPF = COD_CNPJ)
	 * </p>
	 * RASTREIO: [OC827869][UC3067][SB0003.2][ITEM 16]
	 * 
	 * @param ultimoCodReg
	 * @param ultimoCliDoc
	 * @return
	 * @author Marlos Ribeiro
	 * @since 05/10/2012
	 * @author Josenildo Neves
	 * @since 19/10/2012
	 */
	public BigDecimal consultaValorNaoRecebido(Integer gerenciaRegionalID, String clienteDoc, String referenciaBase,
					Integer[] tiposClienteRelacionamento)
					throws ErroRepositorioException{

		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT ");
		buffer.append(" SUM (ct.CNTA_VLAGUA + ct.CNTA_VLESGOTO + ct.CNTA_VLDEBITOS - ct.CNTA_VLCREDITOS) ");
		buffer.append(" FROM CONTA ct, ");
		buffer.append(" LOCALIDADE l, ");   
		buffer.append(" CLIENTE cl, ");
		buffer.append(" CLIENTE_CONTA  cc ");
		buffer.append(" WHERE ct.CNTA_AMREFERENCIACONTA  =  :referenciaBase ");
		buffer.append(" AND ct.CNTA_ID IN ( SELECT cc.CNTA_ID   FROM  CONTA_CATEGORIA cc ");
		buffer.append(" WHERE cc.CNTA_ID  = ct.CNTA_ID ");
		buffer.append("  AND cc.CATG_ID = 4 ");
		buffer.append("   GROUP BY cc.CNTA_ID ");
		buffer.append("   HAVING COUNT (cc.CNTA_ID)  = 1)  ");
		buffer.append("   AND  ct.LOCA_ID =  l.LOCA_ID  ");
		buffer.append("   AND  ct.CNTA_ID = cc.CNTA_ID  ");
		buffer.append("   AND  cc.CLIE_ID = cl.CLIE_ID  ");
		buffer.append("   AND  (cl.CLIE_NNCPF = :clienteDoc  ");
		buffer.append("   OR   cl.CLIE_NNCNPJ   = :clienteDoc)  ");
		buffer.append("   AND   cc.CRTP_ID  =  (SELECT NVL (MAX (CC1.CRTP_ID),1) ");
		buffer.append("   FROM CLIENTE_CONTA CC1, ");
		buffer.append("    CLIENTE CLI ");
		buffer.append("    WHERE CC1.CNTA_ID = CT.CNTA_ID ");
		buffer.append("	   AND l.GREG_ID = :gerenciaRegionalID ");
		buffer.append("    AND CC1.CLIE_ID = CLI.CLIE_ID ");                          
		buffer.append("    AND (CLI.CLIE_NNCPF  IS NOT NULL ");                     
		buffer.append("    OR  CLI.CLIE_NNCNPJ IS NOT NULL)) ");                       
		
                                
		Session session = HibernateUtil.getSession();
		BigDecimal retorno;
		try{
			Query query = session.createSQLQuery(buffer.toString());
			query.setInteger("gerenciaRegionalID", gerenciaRegionalID);
			query.setString("clienteDoc", clienteDoc);
			query.setInteger("referenciaBase", Integer.valueOf(referenciaBase));
			query.setMaxResults(1);

			retorno = (BigDecimal) query.uniqueResult();
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * Método consultarPagamentosParaDeferimento
	 * RASTREIO: [OC827869][UC3067][SB0004]
	 * 
	 * @param referenciaBase
	 * @param categoriaConta
	 * @param tipoClienteRelacao
	 * @param contaSituacao
	 * @param dataInicioPeriodo
	 * @param dataFimPeriodo
	 * @return
	 * @throws ErroRepositorioException
	 * @author Marlos Ribeiro
	 * @since 08/10/2012
	 */
	public List<LancamentoDeferimentoAnteriorHelper> consultarPagamentosParaDeferimento(Integer referenciaBase, Integer categoriaConta,
					Integer[] tipoClienteRelacao, Integer[] contaSituacao, Date dataInicioPeriodo, Date dataFimPeriodo)
					throws ErroRepositorioException{

		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT ");
		buffer.append(" l.GREG_ID codigoRegional, ");
		buffer.append(" ch.CNHI_AMREFERENCIACONTA referenciaConta, ");
		buffer.append("  CASE ");
		buffer.append("  WHEN cl.CLIE_NNCPF  IS NOT NULL  THEN cl.CLIE_NNCPF ");
		buffer.append("  WHEN cl.CLIE_NNCNPJ IS NOT NULL  THEN cl.CLIE_NNCNPJ ");
		buffer.append("  ELSE NULL ");
		buffer.append("  END documentoCliente, ");
		buffer.append("  ph.PGHI_DTPAGAMENTO dataPagamento, ");
		buffer.append("  ch.IMOV_ID matriculaImovel, ");
		buffer.append("  ph.PGHI_VLPAGAMENTO valorPagamento, ");
		buffer.append("  ch.CNHI_VLAGUA + ch.CNHI_VLESGOTO + ch.CNHI_VLDEBITOS - ch.CNHI_VLCREDITOS  valorLancamento ");   
		buffer.append("  FROM CONTA_HISTORICO CH, ");
		buffer.append("  PAGAMENTO_HISTORICO PH, ");
		buffer.append("  CLIENTE  CL, ");
		buffer.append("  CLIENTE_CONTA_HISTORICO CCH, ");
		buffer.append("  LOCALIDADE L, ");
		buffer.append("  CLIENTE_TIPO CTP ");
		buffer.append("  WHERE  ");
		buffer.append("  PH.CNTA_ID IS NOT  NULL ");
		buffer.append("  AND   PH.CNTA_ID =  CH.CNTA_ID ");   
		buffer.append("  AND   PH.LOCA_ID = L.LOCA_ID ");    
		buffer.append("  AND   CL.CLTP_ID = CTP.CLTP_ID  ");
		buffer.append("  AND   PH.PGHI_DTPAGAMENTO BETWEEN :dataInicioPeriodo AND :dataFimPeriodo ");
		buffer.append("  AND   CH.CNHI_AMREFERENCIACONTA < :referenciaBase ");
		buffer.append("  AND   CH.DCST_IDATUAL IN (:contaSituacao) ");
		buffer.append("  AND   CTP.EPOD_ID IN (1,2,3)  ");
		buffer.append("  AND   CH.CNTA_ID IN (SELECT CNTA_ID ");  
		buffer.append("  FROM CONTA_CATEGORIA_HISTORICO "); 
		buffer.append("  WHERE CNTA_ID = CH.CNTA_ID ");  
		buffer.append("  AND CATG_ID = :categoriaConta "); 
		buffer.append("  GROUP BY CNTA_ID ");   
		buffer.append("  HAVING COUNT(CNTA_ID) = 1) ");                         
		buffer.append("  AND CH.CNTA_ID = CCH.CNTA_ID ");                       
		buffer.append("  AND CCH.CLIE_ID = CL.CLIE_ID ");
		buffer.append("  AND CCH.CLIE_ID = CL.CLIE_ID ");
		buffer.append("  AND CL.CLIE_NNCNPJ IS NOT NULL ");
		buffer.append("  AND CCH.CRTP_ID = ");
		buffer.append("  (SELECT NVL(MAX(CCH1.CRTP_ID),1) ");
		buffer.append("  FROM CLIENTE_CONTA_HISTORICO CCH1, ");
		buffer.append("  CLIENTE CLI "); 
		buffer.append("  WHERE CCH1.CNTA_ID = CH.CNTA_ID ");				 
		buffer.append("  AND CCH1.CLIE_ID  = CLI.CLIE_ID ");					 
		buffer.append("  AND CLI.CLIE_NNCNPJ IS NOT NULL ) ");
		// TODO RETIRAR FILTRO DE DOCS NULL
		buffer.append("ORDER BY 1,2,3,4");

		Session session = HibernateUtil.getSession();
		List<LancamentoDeferimentoAnteriorHelper> retorno;
		try{
			Query query = session.createSQLQuery(buffer.toString())//
							.addScalar("codigoRegional", Hibernate.INTEGER)//
							.addScalar("referenciaConta", Hibernate.INTEGER)//
							.addScalar("documentoCliente", Hibernate.STRING)//
							.addScalar("dataPagamento", Hibernate.DATE)//
							.addScalar("matriculaImovel", Hibernate.INTEGER)//
							.addScalar("valorPagamento", Hibernate.BIG_DECIMAL)//
							.addScalar("valorLancamento", Hibernate.BIG_DECIMAL)//
			;
			query.setInteger("referenciaBase", referenciaBase);
			query.setInteger("categoriaConta", categoriaConta);
			query.setParameterList("contaSituacao", contaSituacao);
			query.setDate("dataInicioPeriodo", dataInicioPeriodo);
			query.setDate("dataFimPeriodo", dataFimPeriodo);
			query.setResultTransformer(Transformers.aliasToBean(LancamentoDeferimentoAnteriorHelper.class));
			retorno = query.list();
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * {@inheritDoc}
	 */
	public Integer consultarUltimoSequencialConslDiaNfcl() throws ErroRepositorioException{

		Integer retorno;
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT MAX(ti.codigoSequenciaOrigem) FROM ");
		sql.append(TabelaIntegracaoConslDiaNfcl.class.getName());
		sql.append(" ti");
		Session session = HibernateUtil.getSession();
		try{
			Query query = session.createQuery(sql.toString());
			retorno = (Integer) query.uniqueResult();
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno == null ? 0 : retorno;
	}

	/**
	 * {@inheritDoc}
	 */
	public Collection<Object[]> consultarModulosPorDiaPorMunicipio(Date dtInicio, Date dtFim, Integer idMunicipio)
					throws ErroRepositorioException{

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT LACS.LACS_DTCONTABIL dtContabil, LOC.MUNI_ID idMunicipio, EC.MODC_ID idModulo ");
		sql.append("FROM   LANCAMENTO_CONTABIL_SINTETICO LACS, ");
		sql.append("   EVENTO_COMERCIAL              EC, ");
		sql.append("   EVENTO_COMERCIAL_LANCAMENTO   ECL, ");
		sql.append("   CONTA_CONTABIL                CC, ");
		sql.append("   LOCALIDADE                    LOC ");
		sql.append("WHERE  LACS.LACS_DTCONTABIL BETWEEN :data_inicial AND :data_final ");
		sql.append("  AND  LACS.UNCO_ID   = LOC.LOCA_ID  ");
		sql.append("  AND  LOC.MUNI_ID    = :idMunicipio  ");
		sql.append("  AND  LACS.EVCO_ID   = EC.EVCO_ID ");
		sql.append("  AND  EC.EVCO_ID     = ECL.EVCO_ID ");
		sql.append("  AND  LOC.LOCA_ID    = LACS.UNCO_ID ");
		sql.append("  AND (ECL.CNCT_ID_DEBITO  = CC.CNCT_ID OR ECL.CNCT_ID_CREDITO = CC.CNCT_ID) ");
		sql.append("  AND  SUBSTR(CC.CNCT_NNCONTA,1, 1) = '3' ");
		sql.append("ORDER BY 1, 2, 3 ");

		Collection<Object[]> retorno;
		Session session = HibernateUtil.getSession();
		try{
			SQLQuery query = session.createSQLQuery(sql.toString());
			query.setDate("data_inicial", dtInicio);
			query.setDate("data_final", dtFim);
			query.setInteger("idMunicipio", idMunicipio);

			query.addScalar("dtContabil", Hibernate.DATE);
			query.addScalar("idMunicipio", Hibernate.INTEGER);
			query.addScalar("idModulo", Hibernate.INTEGER);
			retorno = query.list();
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @param idsEventos
	 */
	public Collection<Object[]> consultarLancamentosContabeis(Date dataContabil, Integer idMunicipio, boolean isFinanceiro,
					Integer... idsEventos)
					throws ErroRepositorioException{

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT LACA.LACA_IDOBJETO idObjeto, ");
		sql.append("       SUM(LACA.LACA_VL) sumVlLancamento, ");
		sql.append("       MAX(LACS.CATG_ID) idCategoria  ");
		if(isFinanceiro) sql.append(",LACS.LICT_ID itemContabil ");
		sql.append("FROM   LANCAMENTO_CONTABIL_SINTETICO LACS,  ");
		sql.append("       LANCAMENTO_CONTABIL_ANALITICO LACA,  ");
		sql.append("       LOCALIDADE                    LOC  ");
		sql.append("WHERE  LACS.LACS_DTCONTABIL = :data_contabil  ");
		sql.append("  AND  LACS.UNCO_ID   = LOC.LOCA_ID  ");
		sql.append("  AND  LOC.MUNI_ID   = :idMunicipio  ");
		sql.append("  AND  LACS.EVCO_ID IN (:idsEventos)  ");
		sql.append("  AND  LACS.LACS_ID   =  LACA.LACS_ID  ");
		sql.append("GROUP  BY  LACA.LACA_IDOBJETO  ");
		if(isFinanceiro) sql.append(",LACS.LICT_ID ");

		Collection<Object[]> retorno;
		Session session = HibernateUtil.getSession();
		try{
			SQLQuery query = session.createSQLQuery(sql.toString());
			query.setDate("data_contabil", dataContabil);
			query.setInteger("idMunicipio", idMunicipio);
			query.setParameterList("idsEventos", idsEventos, Hibernate.INTEGER);

			query.addScalar("idObjeto", Hibernate.INTEGER);
			query.addScalar("sumVlLancamento", Hibernate.BIG_DECIMAL);
			query.addScalar("idCategoria", Hibernate.INTEGER);
			if(isFinanceiro) query.addScalar("itemContabil", Hibernate.INTEGER);
			retorno = query.list();
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * {@inheritDoc}
	 */
	public Integer consultarClassificacaoLancamento(Date dtVigencia, Integer idCategoria, BigDecimal vlConta) throws ErroRepositorioException{

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT CSFS_CDCONSUMOSPED faixaConsumo ");
		sql.append("FROM   CONSUMO_FORNECIMENTO_SPED ");
		sql.append("WHERE  CATG_ID = :idCategoria ");
		sql.append("  AND  :valor_conta BETWEEN CSFS_NNCOSUMOFAIXAINICIO  ");
		sql.append("                        AND CSFS_NNCOSUMOFAIXAFIM  ");
		sql.append("  AND  CSFS_DTVIGENCIA   =  (SELECT MAX(CSFS_DTVIGENCIA) ");
		sql.append("                             FROM   CONSUMO_FORNECIMENTO_SPED ");
		sql.append("                             WHERE  CSFS_DTVIGENCIA <= :data_inicial) ");

		Integer retorno;
		Session session = HibernateUtil.getSession();
		try{
			SQLQuery query = session.createSQLQuery(sql.toString());
			query.setInteger("idCategoria", idCategoria);
			query.setBigDecimal("valor_conta", vlConta);
			query.setDate("data_inicial", dtVigencia);

			query.addScalar("faixaConsumo", Hibernate.INTEGER);
			retorno = (Integer) query.uniqueResult();
			if(Util.isVazioOuBranco(retorno)){
				String mensagem = "CODIGO_CONSUMO_SPED não encontrado para: dtVigencia=" + Util.formatarData(dtVigencia) + ", idCategoria="
								+ idCategoria + ", vlConta=" + vlConta;
				LOGGER.error(mensagem);
				throw new ErroRepositorioException(mensagem);
			}
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	public Collection<Object[]> consultarLancamentosContas(Integer idMunicipio, Date dataContabil,
					Integer[] idsEventosContabil)
					throws ErroRepositorioException{
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT LACA.LACA_IDOBJETO id_objeto, ");
		sql.append("       CNHI.CNHI_AMREFERENCIACONTA referencia, ");
		sql.append("       LACS.LACS_DTCONTABIL dt_emissao, ");
		sql.append("       CNHI.CNHI_TMCONTAHISTORICO dt_cancelamento, ");
		sql.append("       SUM (LACA.LACA_VL) vl_lancamento, ");
		sql.append("       MAX(LACS.CATG_ID) id_categoria ");
		sql.append("FROM   LANCAMENTO_CONTABIL_SINTETICO LACS, ");
		sql.append("       LANCAMENTO_CONTABIL_ANALITICO LACA, ");
		sql.append("       CONTA_HISTORICO               CNHI, ");
		sql.append("       LOCALIDADE                    LOC ");
		sql.append("WHERE  LACS.LACS_DTCONTABIL = :dataContabil ");
		sql.append("  AND  LACS.UNCO_ID   = LOC.LOCA_ID  ");
		sql.append("  AND  LOC.MUNI_ID   = :idMunicipio  ");
		sql.append("  AND  LACS.EVCO_ID IN (:idsEventosContabil) ");
		sql.append("  AND  LACS.LACS_ID   =  LACA.LACS_ID ");
		sql.append("  AND  LACA_IDOBJETO  =  CNHI.CNTA_ID ");
		sql.append("GROUP BY LACA.LACA_IDOBJETO , CNHI.CNHI_AMREFERENCIACONTA , LACS.LACS_DTCONTABIL , CNHI.CNHI_TMCONTAHISTORICO ");

		Session session = HibernateUtil.getSession();
		Collection<Object[]> retorno;
		try{
			SQLQuery query = session.createSQLQuery(sql.toString());
			query.setDate("dataContabil", dataContabil);
			query.setInteger("idMunicipio", idMunicipio);
			query.setParameterList("idsEventosContabil", idsEventosContabil, Hibernate.INTEGER);

			query.addScalar("id_objeto", Hibernate.INTEGER);
			query.addScalar("referencia", Hibernate.INTEGER);
			query.addScalar("dt_emissao", Hibernate.DATE);
			query.addScalar("dt_cancelamento", Hibernate.DATE);
			query.addScalar("vl_lancamento", Hibernate.BIG_DECIMAL);
			query.addScalar("id_categoria", Hibernate.INTEGER);

			List contas = query.list();
			LOGGER.debug("[" + contas.size() + "] CONTAS selecionadas para: Municipio[" + idMunicipio + "], Data Geracao["
							+ Util.formatarData(dataContabil) + "], Evento[" + Arrays.toString(idsEventosContabil) + "]");
			retorno = contas;
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;

	}

	/**
	 * {@inheritDoc}
	 */
	public void atualizarValoresCmDiaNfcl(Integer codigoSequenciaOrigem, String codigoSituacaoTributariaPis, BigDecimal valItem,
					BigDecimal valBasePis, BigDecimal valPis, BigDecimal valBaseCofins, BigDecimal valCofins)
					throws ErroRepositorioException{

		StringBuilder hql = new StringBuilder();
		hql.append("UPDATE ");
		hql.append(TabelaIntegracaoCmConDiaNfcl.class.getName());
		hql.append(" ti SET ");
		hql.append("	ti.valorItem = :valorItem, ");
		hql.append("	ti.valorBasePis = :vlBasePis, ");
		hql.append("	ti.valorPis = :valorPis, ");
		hql.append("	ti.valorBaseConfins = :valorBaseConfins, ");
		hql.append("	ti.valorConfins = :valorConfins ");
		hql.append(" WHERE ");
		hql.append(" 	ti.comp_id.codigoSequencialOrigem = :codigoSequencialOrigem AND ");
		hql.append(" 	ti.codigoSituacaoTributariaPis = :codigoSituacaoTributariaPis ");
		Session session = HibernateUtil.getSession();
		try{
			Query query = session.createQuery(hql.toString());
			query.setBigDecimal("valorItem", valItem);
			query.setBigDecimal("vlBasePis", valBasePis);
			query.setBigDecimal("valorPis", valPis);
			query.setBigDecimal("valorBaseConfins", valBaseCofins);
			query.setBigDecimal("valorConfins", valCofins);
			query.setInteger("codigoSequencialOrigem", codigoSequenciaOrigem);
			query.setString("codigoSituacaoTributariaPis", codigoSituacaoTributariaPis);
			query.executeUpdate();
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

	}

	/**
	 * {@inheritDoc}
	 */
	public BigDecimal recuperarValorItemTICmConDiaNFCL(Integer codigoSequenciaOrigem, String codigoSituacaoTributariaPis)
					throws ErroRepositorioException{

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT val_item vlItem FROM ti_cm_con_dia_nfcl WHERE COD_SEQUENCIAL_ORIGEM = :codigoSequencialOrigem AND cod_cst_pis = :codigoSituacaoTributariaPis");
		Session session = HibernateUtil.getSession();
		BigDecimal retorno;
		try{
			SQLQuery query = session.createSQLQuery(sql.toString());
			query.setInteger("codigoSequencialOrigem", codigoSequenciaOrigem);
			query.setString("codigoSituacaoTributariaPis", codigoSituacaoTributariaPis);
			query.addScalar("vlItem", Hibernate.BIG_DECIMAL);
			retorno = (BigDecimal) query.uniqueResult();
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	public Collection<TabelaIntegracaoConsCanNtfisc> consultaTIConsCanNTFISC(TabelaIntegracaoConsCanNtfiscPk comp_id)
					throws ErroRepositorioException{

		StringBuilder hql = new StringBuilder();
		hql.append("FROM ");
		hql.append(TabelaIntegracaoConsCanNtfisc.class.getName());
		hql.append(" ti ");
		hql.append(" WHERE ");
		hql.append(" ti.comp_id.codigoFilialOrigem = :codigoFilialOrigem");
		hql.append(" AND ti.comp_id.codigoSistemaOrigem = :codigoSistemaOrigem");
		hql.append(" AND ti.comp_id.dataCancelamento = :dataCancelamento");
		hql.append(" AND ti.codigoOperacaoRegistro = :codigoOperacaoRegistro");
		Session session = HibernateUtil.getSession();
		List<TabelaIntegracaoConsCanNtfisc> retorno;
		try{
			Query query = session.createQuery(hql.toString());
			query.setString("codigoFilialOrigem", comp_id.getCodigoFilialOrigem());
			query.setString("codigoSistemaOrigem", comp_id.getCodigoSistemaOrigem());
			query.setDate("dataCancelamento", comp_id.getDataCancelamento());
			query.setString("codigoOperacaoRegistro", "I");
			retorno = query.list();
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			session.close();
		}
		return retorno;
	}

	public Object[] consultarDataValorCategoriaOrigemCancelamento(Date dataContabil, Integer idMunicipio, Integer idObjetoContabil,
					Integer idItemContabil, Integer idCategoria, Integer... idsEventosContabil) throws ErroRepositorioException{

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT MAX(LACS.LACS_DTGERACAO) dtGeracao ");
		sql.append("     , SUM(LACA.LACA_VL) vlLancamento ");
		sql.append("     ,  MAX(LACS.CATG_ID) idCategoria  ");
		sql.append("FROM   LANCAMENTO_CONTABIL_ANALITICO LACA, ");
		sql.append("       LANCAMENTO_CONTABIL_SINTETICO LACS, ");
		sql.append("       LOCALIDADE                    LOC ");
		sql.append("WHERE  LACS.LACS_ID         =  LACA.LACS_ID ");
		sql.append("  AND  LACS.LACS_DTCONTABIL between :dataIni and :dataContabil ");
		sql.append("  AND  LACS.UNCO_ID   = LOC.LOCA_ID  ");
		sql.append("  AND  LOC.MUNI_ID   = :idMunicipio  ");
		sql.append("  AND  LACS.EVCO_ID        IN  (:idsEventosContabil) ");
		sql.append("  AND  LACS.LACS_ID         =  LACA.LACS_ID ");
		sql.append("  AND  LACA_IDOBJETO        =  :idObjetoContabil ");
		if(idItemContabil != null){
			sql.append("  AND  LACS.CATG_ID = :idCategoria ");
			sql.append("  AND  LACS.LICT_ID = :idItemContabil ");
		}

		Session session = HibernateUtil.getSession();
		Object[] retorno;
		try{
			SQLQuery query = session.createSQLQuery(sql.toString());
			query.setDate("dataIni", Util.obterDataPrimeiroDiaMes(dataContabil));
			query.setDate("dataContabil", dataContabil);
			query.setInteger("idMunicipio", idMunicipio);
			query.setParameterList("idsEventosContabil", idsEventosContabil, Hibernate.INTEGER);
			query.setInteger("idObjetoContabil", idObjetoContabil);
			if(idItemContabil != null){
				query.setInteger("idCategoria", idCategoria);
				query.setInteger("idItemContabil", idItemContabil);
			}

			query.addScalar("dtGeracao", Hibernate.DATE);
			query.addScalar("vlLancamento", Hibernate.BIG_DECIMAL);
			query.addScalar("idCategoria", Hibernate.INTEGER);

			retorno = (Object[]) query.uniqueResult();
			if(retorno == null){
				LOGGER.warn("Não existe lancamento de origem para o LACA_IDOBJETO[" + idObjetoContabil + "] na LACS_DTGERACAO["
								+ Util.formatarData(dataContabil) + "] na MUNICIPIO[" + idMunicipio + "] com os EVENTOS "
								+ Arrays.toString(idsEventosContabil));
			}else{
				LOGGER.debug("Origem do LACA_IDOBJETO[" + idObjetoContabil + "] = LACS_DTGERACAO[" + Util.formatarData((Date) retorno[0])
							+ "] CATG_ID[" + retorno[2] + "] com LACA_VL[" + retorno[1] + "]");
				if(Integer.valueOf(3).equals(retorno[2])){
					retorno[2] = 2;
				}
			}
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

}
