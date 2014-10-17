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

package gcom.integracao.cagepa.faturamento;

import gcom.cadastro.cliente.ClienteRelacaoTipo;
import gcom.util.ConstantesSistema;
import gcom.util.ErroRepositorioException;
import gcom.util.HibernateUtil;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 * O repositório faz a comunicação com a base de dados através do hibernate.
 * 
 * @author Pitang
 */
public class RepositorioIntegracaoCagepaFaturamentoHBM
				implements IRepositorioIntegracaoCagepaFaturamento {

	private static IRepositorioIntegracaoCagepaFaturamento instancia;

	/**
	 * Retorna o valor de instância
	 * 
	 * @return O valor de instância
	 */
	public static IRepositorioIntegracaoCagepaFaturamento getInstancia(){

		if(instancia == null){
			instancia = new RepositorioIntegracaoCagepaFaturamentoHBM();
		}

		return instancia;
	}

	/**
	 * [UC3012] Gerar Arquivo Texto Faturamento Imediato
	 * [SB0101] – Gerar Integração para Faturamento Imediato – Modelo 3
	 * Retorna o id do banco, de acordo com o Item 20 do Caso de Uso
	 * 
	 * @author Luciano Galvao
	 * @date 20/11/2012
	 */
	public Integer obterBancoId(Integer imovelId) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		Integer retorno = null;
		try{

			String consulta = " SELECT A.BNCO_ID FROM AGENCIA A " + " INNER JOIN DEBITO_AUTOMATICO DA ON DA.AGEN_ID = A.AGEN_ID "
							+ " WHERE DA.IMOV_ID = :imovelId " + " AND DA.DEBA_DTEXCLUSAO IS NULL ";

			retorno = (Integer) session.createSQLQuery(consulta).addScalar("BNCO_ID", Hibernate.INTEGER).setInteger("imovelId", imovelId)
							.setMaxResults(1).uniqueResult();

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
	 * [UC3012] Gerar Arquivo Texto Faturamento Imediato
	 * [SB0101] – Gerar Integração para Faturamento Imediato – Modelo 3
	 * Retorna o telefone padrão do cliente usuário a partir do imovelId passado
	 * 
	 * @author Luciano Galvao
	 * @date 20/11/2012
	 */
	public String obterTelefonePadrao(Integer imovelId) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		String retorno = null;
		try{

			String consulta = " SELECT CF.CFON_NNFONE FROM CLIENTE_FONE CF " + " INNER JOIN CLIENTE_IMOVEL CI ON CI.CLIE_ID = CF.CLIE_ID "
							+ " WHERE CF.CFON_ICFONEPADRAO = :indFonePadrao " + " AND CI.CLIM_DTRELACAOFIM IS NULL "
							+ " AND CI.CRTP_ID = :clienteRelacaoTipoId " + " AND CI.IMOV_ID = :imovelId ";

			retorno = (String) session.createSQLQuery(consulta).addScalar("CFON_NNFONE", Hibernate.STRING)
							.setShort("indFonePadrao", ConstantesSistema.SIM)
							.setInteger("clienteRelacaoTipoId", ClienteRelacaoTipo.USUARIO).setInteger("imovelId", imovelId)
							.setMaxResults(1).uniqueResult();

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
	 * [UC3012] Gerar Arquivo Texto Faturamento Imediato
	 * [SB0101] – Gerar Integração para Faturamento Imediato – Modelo 3
	 * Retorna uma lista com o tipo de consumo dos últimos meses de consumo, a partir de um dado
	 * imóvel e a quantidade de meses a ser considerada
	 * 
	 * @author Luciano Galvao
	 * @date 20/11/2012
	 */
	public List<Integer> obterTipoConsumoUltimosMeses(Integer imovelId, Integer qtdeMeses) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		List<Integer> retorno = null;
		try{

			String consulta = " SELECT CH.CSTP_ID FROM CONSUMO_HISTORICO CH WHERE CH.IMOV_ID = :imovelId "
							+ " AND CH.CSHI_NNCONSUMOFATURADOMES > 0 ORDER BY CH.CSHI_AMFATURAMENTO DESC ";

			retorno = session.createSQLQuery(consulta).addScalar("CSTP_ID", Hibernate.INTEGER).setInteger("imovelId", imovelId)
							.setMaxResults(qtdeMeses).list();

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
	 * [UC3012] Gerar Arquivo Texto Faturamento Imediato
	 * [SB0101] – Gerar Integração para Faturamento Imediato – Modelo 3
	 * Retorna uma lista com as anormalidades do tipo de consumo dos últimos meses de consumo, a
	 * partir de um dado
	 * imóvel e a quantidade de meses a ser considerada
	 * 
	 * @author Luciano Galvao
	 * @date 20/11/2012
	 */
	public List<Integer> obterAnormalidadeConsumoUltimosMeses(Integer imovelId, Integer qtdeMeses) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		List<Integer> retorno = null;
		try{

			String consulta = " SELECT CH.CSAN_ID FROM CONSUMO_HISTORICO CH "
							+ " WHERE CH.IMOV_ID = :imovelId ORDER BY CH.CSHI_AMFATURAMENTO DESC ";

			retorno = session.createSQLQuery(consulta).addScalar("CSAN_ID", Hibernate.INTEGER).setInteger("imovelId", imovelId)
							.setMaxResults(qtdeMeses).list();

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
	 * [UC3012] Gerar Arquivo Texto Faturamento Imediato
	 * [SB0101] – Gerar Integração para Faturamento Imediato – Modelo 3
	 * Retorna a soma das alíquotas dos impostos do ano/mês de referência mais recente
	 * 
	 * @author Luciano Galvao
	 * @date 20/11/2012
	 */
	public BigDecimal obterSomaAliquotasImpostos() throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		BigDecimal retorno = null;
		try{

			String consulta = " SELECT SUM(ITA.IMTA_PCALIQUOTA) AS SOMA_ALIQUOTA FROM IMPOSTO_TIPO_ALIQUOTA ITA "
							+ " WHERE ITA.IMTA_AMREFERENCIA = (SELECT MAX(I.IMTA_AMREFERENCIA) FROM IMPOSTO_TIPO_ALIQUOTA I) ";

			retorno = (BigDecimal) session.createSQLQuery(consulta).addScalar("SOMA_ALIQUOTA", Hibernate.BIG_DECIMAL).uniqueResult();

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
	 * [UC3012] Gerar Arquivo Texto Faturamento Imediato
	 * [SB0101] – Gerar Integração para Faturamento Imediato – Modelo 3
	 * Retorna <true> se o imóvel tiver situação de faturamento de acordo com o
	 * faturamentoSituacaoTipoId. Retorna <false> caso contrário.
	 * 
	 * @author Luciano Galvao
	 * @date 20/11/2012
	 */
	public boolean possuiSituacaoFaturamentoImovel(Integer imovelId, Integer faturamentoSituacaoTipoId) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		List<Integer> resultadoConsulta = null;
		try{

			String consulta = " SELECT FSH.FTST_ID FROM FATURAMENTO_SITUACAO_HISTORICO FSH "
							+ " WHERE FSH.IMOV_ID = :imovelId AND FSH.FTST_ID = :faturamentoSituacaoTipoId ";

			resultadoConsulta = session.createSQLQuery(consulta).addScalar("FTST_ID", Hibernate.INTEGER).setInteger("imovelId", imovelId)
							.setInteger("faturamentoSituacaoTipoId", faturamentoSituacaoTipoId).list();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		return (resultadoConsulta != null && !resultadoConsulta.isEmpty());
	}

	/**
	 * [UC3012] Gerar Arquivo Texto Faturamento Imediato
	 * [SB0101] – Gerar Integração para Faturamento Imediato – Modelo 3
	 * Retorna a data prevista (FTAC_DTPREVISTA) do Cronograma de Atividade do Faturamento
	 * (FATURAMENTO_GRUPO_CRON_MENSAL) a partir da referência, do id do grupo de faturamento e do id
	 * da atividade de faturamento
	 * 
	 * @author Luciano Galvao
	 * @date 20/11/2012
	 */
	public Date obterDataPrevistaFaturamentoAtivCronograma(Integer anoMesReferencia, Integer faturamentoGrupoId,
					Integer faturamentoAtividadeId) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		Date retorno = null;
		try{

			String consulta = " SELECT FAC.FTAC_DTPREVISTA FROM FATURAMENTO_ATIVIDADE_CRON FAC "
							+ " INNER JOIN FATURAMENTO_GRUPO_CRON_MENSAL FGCM ON FGCM.FTCM_ID = FAC.FTCM_ID "
							+ " WHERE FGCM.FTCM_AMREFERENCIA = :anoMesReferencia AND FGCM.FTGR_ID = :faturamentoGrupoId "
							+ " AND FAC.FTAT_ID = :faturamentoAtividadeId ";

			retorno = (Date) session.createSQLQuery(consulta).addScalar("FTAC_DTPREVISTA", Hibernate.DATE)
							.setInteger("anoMesReferencia", anoMesReferencia)
							.setInteger("faturamentoGrupoId", faturamentoGrupoId)
							.setInteger("faturamentoAtividadeId", faturamentoAtividadeId).uniqueResult();

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
	 * [UC3012] Gerar Arquivo Texto Faturamento Imediato
	 * [SB0101] – Gerar Integração para Faturamento Imediato – Modelo 3
	 * Retorna as informações valor de leitura real mais recente, junto com o seu ano/mês de
	 * faturamento
	 * 
	 * @author Luciano Galvao
	 * @date 20/11/2012
	 */
	public Object[] obterUltimaLeituraConsumoReal(Integer imovelId, Integer consumoTipoId) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		Object[] retorno = null;
		try{

			String consulta = " SELECT CH.CSHI_AMFATURAMENTO, MH.MDHI_NNLEITURAATUALFATURAMENTO FROM MEDICAO_HISTORICO MH "
							+ " INNER JOIN CONSUMO_HISTORICO CH ON CH.IMOV_ID = MH.LAGU_ID AND CH.CSHI_AMFATURAMENTO = MH.MDHI_AMLEITURA "
							+ " WHERE CH.IMOV_ID = :imovelId AND CH.CSTP_ID = :consumoTipoId ORDER BY CH.CSHI_AMFATURAMENTO DESC ";

			retorno = (Object[]) session.createSQLQuery(consulta).addScalar("CSHI_AMFATURAMENTO", Hibernate.INTEGER)
							.addScalar("MDHI_NNLEITURAATUALFATURAMENTO", Hibernate.INTEGER).setInteger("imovelId", imovelId)
							.setInteger("consumoTipoId", consumoTipoId).setMaxResults(1).uniqueResult();

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
	 * [UC3012] Gerar Arquivo Texto Faturamento Imediato
	 * [SB0101] – Gerar Integração para Faturamento Imediato – Modelo 3
	 * Retorna o ano/Mês de Faturamento do consumo mais recente do imóvel
	 * 
	 * @author Luciano Galvao
	 * @date 20/11/2012
	 */
	public Integer obterAnoMesFaturamentoConsumoMaisRecente(Integer imovelId) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		Integer retorno = null;
		try{

			String consulta = " SELECT CH.CSHI_AMFATURAMENTO FROM CONSUMO_HISTORICO CH WHERE CH.IMOV_ID = :imovelId ORDER BY CH.CSHI_AMFATURAMENTO DESC ";

			retorno = (Integer) session.createSQLQuery(consulta).addScalar("CSHI_AMFATURAMENTO", Hibernate.INTEGER)
							.setInteger("imovelId", imovelId).setMaxResults(1).uniqueResult();

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
	 * [UC3012] Gerar Arquivo Texto Faturamento Imediato
	 * [SB0101] – Gerar Integração para Faturamento Imediato – Modelo 3
	 * Retorna o tipo de anormalidade consumo (CSAN_ID) do Histórico de Consumo do Imóvel
	 * (CONSUMO_HISTORICO)
	 * 
	 * @author Luciano Galvao
	 * @date 20/11/2012
	 */
	public Integer obterTipoAnormalidadeConsumo(Integer imovelId) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		Integer retorno = null;
		try{

			String consulta = " SELECT CH.CSAN_ID FROM CONSUMO_HISTORICO CH WHERE CH.IMOV_ID = :imovelId "
							+ " ORDER BY CH.CSHI_AMFATURAMENTO DESC ";

			retorno = (Integer) session.createSQLQuery(consulta).addScalar("CSAN_ID", Hibernate.INTEGER).setInteger("imovelId", imovelId)
							.setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}
}
