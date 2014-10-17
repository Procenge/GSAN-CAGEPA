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

package gcom.atendimentopublico.ligacaoagua;

import gcom.atendimentopublico.ligacaoagua.bean.ConsultarHistoricoManutencaoLigacaoHelper;
import gcom.atendimentopublico.ligacaoagua.bean.DadosEfetuacaoCorteLigacaoAguaHelper;
import gcom.atendimentopublico.ordemservico.bean.DadosUltimoCorteHelper;
import gcom.util.ControladorException;
import gcom.util.ErroRepositorioException;
import gcom.util.HibernateUtil;
import gcom.util.Util;

import java.util.Collection;
import java.util.Date;

import org.hibernate.*;

/**
 * Implementação do RepositorioLigacaoAgua
 * 
 * @author Leonardo Regis
 * @date 09/09/2006
 */
public class RepositorioLigacaoAguaHBM
				implements IRepositorioLigacaoAgua {

	private static IRepositorioLigacaoAgua instancia;

	/**
	 * Construtor da classe RepositorioLigacaoAguaHBM
	 */
	private RepositorioLigacaoAguaHBM() {

	}

	/**
	 * Retorna o valor de instancia
	 * 
	 * @return O valor de instancia
	 */
	public static IRepositorioLigacaoAgua getInstancia(){

		if(instancia == null){
			instancia = new RepositorioLigacaoAguaHBM();
		}
		return instancia;
	}

	/**
	 * [UC0463] Atualizar Consumo Mínimo da Ligação de Água
	 * 
	 * @author Leonardo Regis
	 * @date 30/08/2006
	 * @param ligacaoAgua
	 * @exception ErroRepositorioExceptions
	 */
	public void atualizarConsumoMinimoLigacaoAgua(LigacaoAgua ligacaoAgua) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		String update;

		try{

			update = "update LigacaoAgua set ";
			if(ligacaoAgua.getNumeroConsumoMinimoAgua() != null){
				update = update + "numeroConsumoMinimoAgua = :consumoMinimo, ";
			}else{
				update = update + "numeroConsumoMinimoAgua = null, ";
			}

			update = update + "ultimaAlteracao = :dataCorrente " + "where id = :ligacaoAguaId";

			if(ligacaoAgua.getNumeroConsumoMinimoAgua() != null){

				session.createQuery(update).setInteger("consumoMinimo", ligacaoAgua.getNumeroConsumoMinimoAgua()).setTimestamp(
								"dataCorrente", ligacaoAgua.getUltimaAlteracao()).setInteger("ligacaoAguaId", ligacaoAgua.getId())
								.executeUpdate();

			}else{

				session.createQuery(update).setTimestamp("dataCorrente", ligacaoAgua.getUltimaAlteracao()).setInteger("ligacaoAguaId",
								ligacaoAgua.getId()).executeUpdate();

			}
		}catch(HibernateException e){
			e.printStackTrace();
			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
	}

	/**
	 * [UC0463] Atualizar Consumo Mínimo da Ligação de Água
	 * [SB0001] Atualizar Imóvel/Ligação Água/Histórico de Instalação de Hidrômetro
	 * 
	 * @author Leonardo Regis
	 * @date 25/09/2006
	 * @param helper
	 * @exception ErroRepositorioExceptions
	 */
	public void efetuarCorteLigacaoAgua(DadosEfetuacaoCorteLigacaoAguaHelper helper) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		String update;
		Query query = null;
		try{
			update = "update Imovel set " + "ligacaoAguaSituacao.id = :ligacaoAguaSituacao, " + "ultimaAlteracao = :dataCorrente "
							+ "where id = :imovelId";
			session.createQuery(update).setInteger("ligacaoAguaSituacao", helper.getImovel().getLigacaoAguaSituacao().getId())
							.setTimestamp("dataCorrente", helper.getImovel().getUltimaAlteracao()).setInteger("imovelId",
											helper.getImovel().getId()).executeUpdate();

			update = "update LigacaoAgua set " + "dataCorte = :dataCorte, numeroSeloCorte =" + helper.getLigacaoAgua().getNumeroSeloCorte();
			update += ",corteTipo.id = :corteTipoId, "
							+ "motivoCorte.id = :motivoCorteId, "
							+ "numeroCorteFaltaPagamento = "
							+ helper.getLigacaoAgua().getNumeroCorteFaltaPagamento().toString()
							+ ", "
							+ "numeroCorteInfracao = "
							+ helper.getLigacaoAgua().getNumeroCorteInfracao().toString()
							+ ", "
							+ "numeroCortePedido = "
							+ helper.getLigacaoAgua().getNumeroCortePedido().toString()
							+ ", "
							+ "funcionarioCorte.id = "
							+ (helper.getLigacaoAgua().getFuncionarioCorte() != null ? helper.getLigacaoAgua().getFuncionarioCorte()
											.getId().toString() : "null") + ", "
							+ "ultimaAlteracao = :dataCorrente, "
							// Incrementando Quantidade de corte: se ele for null coloca como 1 se
							// ñ, incrementa
							+ "numeroCorte = case when (numeroCorte is not null) then (numeroCorte + 1) else 1 end "
							+ "where id = :ligacaoAguaId";

			query = session.createQuery(update).setTimestamp("dataCorte", helper.getLigacaoAgua().getDataCorte())
							.setTimestamp("dataCorrente", helper.getLigacaoAgua().getUltimaAlteracao())
							.setInteger("corteTipoId", helper.getLigacaoAgua().getCorteTipo().getId())
							.setInteger("motivoCorteId", helper.getLigacaoAgua().getMotivoCorte().getId())
							.setInteger("ligacaoAguaId", helper.getLigacaoAgua().getId());
			query.executeUpdate();

			if(helper.getHidrometroInstalacaoHistorico() != null){
				update = "update HidrometroInstalacaoHistorico set ";
				update += "numeroLeituraCorte =" + helper.getHidrometroInstalacaoHistorico().getNumeroLeituraCorte();
				update += ",ultimaAlteracao = :dataCorrente " + "where id = :hidrometroId";

				query = session.createQuery(update).setTimestamp("dataCorrente", helper.getLigacaoAgua().getUltimaAlteracao()).setInteger(
								"hidrometroId", helper.getHidrometroInstalacaoHistorico().getId());

				query.executeUpdate();
			}
		}catch(HibernateException e){
			e.printStackTrace();
			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
	}

	/**
	 * [UC0463] Atualizar Consumo Mínimo da Ligação de Água
	 * [SB0001] Atualizar Imóvel/Ligação Água/Histórico de Instalação de Hidrômetro
	 * 
	 * @author Leonardo Regis
	 * @date 25/09/2006
	 * @param helper
	 * @exception ErroRepositorioExceptions
	 */
	public void efetuarCorteAdministrativoLigacaoAgua(DadosEfetuacaoCorteLigacaoAguaHelper helper) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		String update;
		try{
			update = "update LigacaoAgua set "
							+ "corteTipo.id = :corteTipoId, "
							+ "ultimaAlteracao = :dataCorrente, "
							+ "numeroCorteFaltaPagamento = "
							+ helper.getLigacaoAgua().getNumeroCorteFaltaPagamento().toString()
							+ ", "
							+ "numeroCorteInfracao = "
							+ helper.getLigacaoAgua().getNumeroCorteInfracao().toString()
							+ ", "
							+ "numeroCortePedido = "
							+ helper.getLigacaoAgua().getNumeroCortePedido().toString()
							+ ", "
							+ "funcionarioCorte.id = "
							+ (helper.getLigacaoAgua().getFuncionarioCorte() != null ? helper.getLigacaoAgua().getFuncionarioCorte()
											.getId().toString() : "null") + ", "
							+ "dataCorteAdministrativo = :dataCorteAdministrativo, "
							// Incrementando Quantidade de corte: se ele for null coloca como 1 se
							// ñ, incrementa
							+ "numeroCorte = case when (numeroCorte is not null) then (numeroCorte + 1) else 1 end "
							+ "where id = :ligacaoAguaId";

			session.createQuery(update).setTimestamp("dataCorrente", new Date()).setInteger("corteTipoId",
							helper.getLigacaoAgua().getCorteTipo().getId()).setTimestamp("dataCorteAdministrativo",
							helper.getLigacaoAgua().getDataCorteAdministrativo()).setInteger("ligacaoAguaId",
							helper.getLigacaoAgua().getId()).executeUpdate();
		}catch(HibernateException e){
			e.printStackTrace();
			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
	}

	/**
	 * Efetuar Restabelecimento da Ligação de Água
	 * [SB0001] Atualizar Imóvel/Ligação Água
	 * 
	 * @author Flávio Cordeiro
	 * @date 28/09/2006
	 * @param ligacaoAgua
	 * @exception ErroRepositorioExceptions
	 */
	public void atualizarLigacaoAguaRestabelecimento(LigacaoAgua ligacaoAgua) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		String update;
		try{
			update = "update LigacaoAgua set "
							+ "dataRestabelecimentoAgua = :dataRestabelecimentoAgua, "
							+ "numeroRestabelecimento = "
							+ ligacaoAgua.getNumeroRestabelecimento().toString()
							+ ", "
							+ "numeroRestabelecimentoParcial = "
							+ ligacaoAgua.getNumeroRestabelecimentoParcial().toString()
							+ ", "
							+ "numeroRestabelecimentoTotal = "
							+ ligacaoAgua.getNumeroRestabelecimentoTotal().toString()
							+ ", "
							+ "funcionarioRestabelecimento.id = "
							+ (ligacaoAgua.getFuncionarioRestabelecimento() != null ? ligacaoAgua.getFuncionarioRestabelecimento().getId()
											.toString() : "null") + ", " + "ultimaAlteracao = :dataCorrente " + "where id = :ligacaoAguaId";

			session.createQuery(update).setTimestamp("dataRestabelecimentoAgua", ligacaoAgua.getDataRestabelecimentoAgua()).setTimestamp(
							"dataCorrente", new Date()).setInteger("ligacaoAguaId", ligacaoAgua.getId()).executeUpdate();
		}catch(HibernateException e){
			e.printStackTrace();
			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
	}

	/**
	 * [UC0357] Efetuar Religação de Água
	 * [SB0001] Atualizar Imóvel/Ligação Água
	 * 
	 * @author Flávio Cordeiro
	 * @date 28/09/2006
	 * @param ligacaoAgua
	 * @exception ErroRepositorioExceptions
	 */
	public void atualizarLigacaoAguaReligacao(LigacaoAgua ligacaoAgua) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		String update;
		try{
			update = "update LigacaoAgua set "
							+ "dataReligacao = :dataReligacao, numeroReligacao = "
							+ ligacaoAgua.getNumeroReligacao().toString()
							+ ", "
							+ "funcionarioReligacaoAgua.id = "
							+ (ligacaoAgua.getFuncionarioReligacaoAgua() != null ? ligacaoAgua.getFuncionarioReligacaoAgua().getId()
											.toString() : "null") + ", " + "ultimaAlteracao = :dataCorrente " + "where id = :ligacaoAguaId";

			session.createQuery(update).setTimestamp("dataReligacao", ligacaoAgua.getDataReligacao()).setTimestamp("dataCorrente",
							new Date()).setInteger("ligacaoAguaId", ligacaoAgua.getId()).executeUpdate();
		}catch(HibernateException e){
			e.printStackTrace();
			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
	}

	/**
	 * [UC0488] Informar Retorno Ordem de Fiscalização
	 * Recupera os parámetros necessários da Ligacao de água
	 * 
	 * @author Sávio Luiz
	 * @date 14/11/2006
	 * @param idOS
	 * @return OrdemServico
	 * @throws ControladorException
	 */
	public Object[] pesquisarParmsLigacaoAgua(Integer idImovel) throws ErroRepositorioException{

		Object[] retornoConsulta = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{

			consulta = "SELECT ligAgua.id,ligAgua.dataCorte,ligAgua.dataSupressao " + "FROM LigacaoAgua ligAgua "
							+ "WHERE ligAgua.id = :idLigacaoAgua";

			retornoConsulta = (Object[]) session.createQuery(consulta).setInteger("idLigacaoAgua", idImovel).setMaxResults(1)
							.uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retornoConsulta;
	}

	/**
	 * [UC0054] - Inserir Dados da Tarifa Social
	 * Recupera o consumo mínimo fixado do Imóvel
	 * 
	 * @author Rafael Corrêa
	 * @date 04/0/2006
	 * @param idImovel
	 * @return Integer
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarConsumoMinimoFixado(Integer idImovel) throws ErroRepositorioException{

		Integer consumoFixado = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{

			consulta = "SELECT ligAgua.numeroConsumoMinimoAgua " + "FROM LigacaoAgua ligAgua " + "WHERE ligAgua.id = :idLigacaoAgua";

			consumoFixado = (Integer) session.createQuery(consulta).setInteger("idLigacaoAgua", idImovel).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return consumoFixado;
	}

	/**
	 * Pesquisa o id do hidrometro
	 * 
	 * @author Sávio Luiz
	 * @date 19/02/2007
	 * @param idOS
	 * @return OrdemServico
	 * @throws ControladorException
	 */
	public Integer pesquisarIdHidrometroInstalacaoHistorico(Integer idImovel) throws ErroRepositorioException{

		Integer retornoConsulta = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{

			consulta = "SELECT hidInstHist.id " + "FROM LigacaoAgua ligAgua "
							+ "LEFT JOIN ligAgua.hidrometroInstalacaoHistorico hidInstHist " + "WHERE ligAgua.id = :idLigacaoAgua";

			retornoConsulta = (Integer) session.createQuery(consulta).setInteger("idLigacaoAgua", idImovel).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retornoConsulta;
	}

	public Collection verificaExistenciaLigacaoAgua(Integer idImovel) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		Collection retorno = null;

		try{

			String consulta = "SELECT ligAgua.id " + "FROM LigacaoAgua ligAgua "
							+ "INNER JOIN ligAgua.hidrometroInstalacaoHistorico hidInstHist " + "WHERE ligAgua.id = :idLigacaoAgua";

			retorno = session.createQuery(consulta).setInteger("idLigacaoAgua", idImovel).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * @author eduardo henrique
	 * @date 19/05/2009
	 *       Método retorna uma LigacaoAgua, buscado por um Id
	 * @param id
	 *            - id da Ligação [obrigatório]
	 * @return
	 * @throws ErroRepositorioException
	 */
	public LigacaoAgua pesquisarLigacaoAgua(Integer id) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		LigacaoAgua retorno = null;

		try{

			String consulta = "FROM LigacaoAgua ligAgua " + //
							"LEFT JOIN FETCH ligAgua.hidrometroInstalacaoHistorico hidInstHist " + //
							"LEFT JOIN FETCH ligAgua.hidrometroInstalacaoHistorico.hidrometro hidrometro " + //
							"LEFT JOIN FETCH ligAgua.corteTipo corteTipo " + "LEFT JOIN FETCH ligAgua.supressaoTipo supressaoTipo " //
							+ "WHERE ligAgua.id = :idLigacaoAgua";

			retorno = (LigacaoAgua) session.createQuery(consulta).setInteger("idLigacaoAgua", id).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;

	}

	/**
	 * @author isilva
	 * @date 24/05/2011
	 *       Obtem Dados do último corte
	 * @param idImovel
	 *            Identificador do Imóvel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public DadosUltimoCorteHelper obterDadosUltimoCorte(Integer idImovel) throws ErroRepositorioException{

		DadosUltimoCorteHelper retorno = null;

		Session session = HibernateUtil.getSession();

		try{
			StringBuffer hql = new StringBuffer("select ");
			hql.append("new gcom.atendimentopublico.ordemservico.bean.DadosUltimoCorteHelper( ");
			hql.append("ligacao.dataCorte, ");
			hql.append("ligacao.numeroSeloCorte, ");
			hql.append("hidrometro.numeroLeituraCorte, ");
			hql.append("corte.id, ");
			hql.append("corte.descricao, ");
			hql.append("ligacao.dataCorteAdministrativo ");
			hql.append(") ");

			hql.append("from ");
			hql.append("LigacaoAgua ligacao ");
			hql.append("left join ligacao.hidrometroInstalacaoHistorico hidrometro ");
			hql.append("left join ligacao.corteTipo corte ");
			hql.append("where ");
			hql.append("ligacao.id = :idImovel");

			retorno = (DadosUltimoCorteHelper) session.createQuery(hql.toString()).setInteger("idImovel", idImovel.intValue())
							.uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * @param id
	 * @return
	 * @throws ErroRepositorioException
	 */
	public LigacaoAguaSituacao pesquisarLigacaoAguaSituacao(Integer id) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		LigacaoAguaSituacao retorno = null;

		try{

			String consulta = "FROM LigacaoAguaSituacao ligAguaSit WHERE ligAguaSit.id = :id";

			retorno = (LigacaoAguaSituacao) session.createQuery(consulta).setInteger("id", id).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;

	}

	private String getConsultaHistoricoManutencaoLigacao(ConsultarHistoricoManutencaoLigacaoHelper helper){

		StringBuilder builder = new StringBuilder();
		boolean existeClausula = false;

		builder.append("SELECT ");
		builder.append("  HIST.IMOV_ID, ");
		builder.append("  HIST.HMLI_DTEMISSAO, ");
		builder.append("  DCTP.DOTP_DSABREVIADO, ");
		builder.append("  SVTP.SVTP_DSABREVIADO, ");
		builder.append("  HIST.CBDO_ID, ");
		builder.append("  HIST.CAST_ID, ");
		builder.append("  HIST.HMLI_DTSITUACAODOCUMENTO, ");
		builder.append("  HISTASSOC.ORSE_ID ORSE_ID_ASSOCIADO, ");
		builder.append("  HIST.ORSE_ID, ");
		builder.append("  HIST.HMLI_CDSITUACAOOS, ");
		builder.append("  DCTP.DOTP_DSDOCUMENTOTIPO, ");
		builder.append("  CSIT.CAST_DSSITUACAOACAO, ");
		builder.append("  SVTPASSOC.SVTP_DSSERVICOTIPO SVTP_DSSVTIPO_ASSOCIADO, ");
		builder.append("  HISTASSOC.HMLI_DTEMISSAO HMLI_DTEMISSAO_ASSOCIADO, ");
		builder.append("  SVTP.SVTP_DSSERVICOTIPO, ");
		builder.append("  HIST.HMLI_NNLEITURAEXECUCAO, ");
		builder.append("  COTP.COTP_DSCORTETIPO, ");
		builder.append("  USUR.USUR_NMLOGIN, ");
		builder.append("  HIST.AMEN_ID, ");
		builder.append("  HISTASSOC.HMLI_NNLEITURAEXECUCAO HMLI_NNLEITEXECUCAO_ASSOCIADO, ");
		builder.append("  COTPASSOC.COTP_DSCORTETIPO COTP_DSCORTETIPO_ASSOCIADO, ");
		builder.append("  USURASSOC.USUR_NMLOGIN USUR_NMLOGIN_ASSOC, ");
		builder.append("  HISTASSOC.AMEN_ID AMEN_ID_ASSOCIADO ");
		builder.append("FROM HISTORICO_MANUTENCAO_LIGACAO HIST ");
		builder.append("  LEFT JOIN DOCUMENTO_TIPO DCTP ON DCTP.DOTP_ID = HIST.DOTP_ID ");
		builder.append("  LEFT JOIN SERVICO_TIPO SVTP ON SVTP.SVTP_ID = HIST.SVTP_ID ");
		builder.append("  LEFT JOIN COBRANCA_ACAO_SITUACAO CSIT ON CSIT.CAST_ID = HIST.CAST_ID ");
		builder.append("  LEFT JOIN HISTORICO_MANUTENCAO_LIGACAO HISTASSOC ON HIST.HMLI_IDASSOCIADO = HISTASSOC.HMLI_ID ");
		builder.append("  LEFT JOIN SERVICO_TIPO SVTPASSOC ON HISTASSOC.SVTP_ID = SVTPASSOC.SVTP_ID ");
		builder.append("  LEFT JOIN CORTE_TIPO COTP ON HIST.COTP_ID = COTP.COTP_ID ");
		builder.append("  LEFT JOIN USUARIO USUR ON HIST.USUR_IDEXECUCAO = USUR.USUR_ID ");
		builder.append("  LEFT JOIN CORTE_TIPO COTPASSOC ON HISTASSOC.COTP_ID = COTPASSOC.COTP_ID ");
		builder.append("  LEFT JOIN USUARIO USURASSOC ON HISTASSOC.USUR_IDEXECUCAO = USURASSOC.USUR_ID ");
		builder.append("WHERE ");

		boolean existeFiltroOrdemServico = !Util.isVazioOrNulo(helper.getTiposServico());
		boolean existeFiltroDocumentoCobranca = !Util.isVazioOrNulo(helper.getFormasEmissao())
						|| !Util.isVazioOrNulo(helper.getTiposDocumento());

		if(helper.getImovelId() != null){
			existeClausula = true;
			builder.append(" HIST.IMOV_ID = " + helper.getImovelId());
		}

		if(helper.getLocalidadeIdInicial() != null && helper.getLocalidadeIdFinal() != null){
			if(existeClausula) builder.append(" AND ");
			existeClausula = true;

			builder.append(" HIST.LOCA_ID ");
			builder.append(" BETWEEN " + helper.getLocalidadeIdInicial());
			builder.append(" AND " + helper.getLocalidadeIdFinal());
		}
		if(helper.getSetorComercialIdInicial() != null && helper.getSetorComercialIdFinal() != null){
			if(existeClausula) builder.append(" AND ");
			existeClausula = true;

			builder.append(" HIST.HMLI_CDSETORCOMERCIAL ");
			builder.append(" BETWEEN " + helper.getSetorComercialIdInicial());
			builder.append(" AND " + helper.getSetorComercialIdFinal());
		}

		if(!Util.isVazioOrNulo(helper.getPerfisImovel())){
			if(existeClausula) builder.append(" AND ");
			existeClausula = true;

			builder.append(" HIST.IPER_ID IN ( ");
			for(Integer imovelPerfilId : helper.getPerfisImovel()){
				builder.append(imovelPerfilId);
				builder.append(",");
			}
			builder.deleteCharAt(builder.length() - 1);
			builder.append(" ) ");
		}

		if(!Util.isVazioOrNulo(helper.getFormasEmissao())){
			if(existeClausula) builder.append(" AND ");
			existeClausula = true;

			if(existeFiltroOrdemServico) builder.append(" (( ");

			builder.append(" HIST.DEMF_ID IN ( ");
			for(Integer formasEmissaoId : helper.getFormasEmissao()){
				builder.append(formasEmissaoId);
				builder.append(",");
			}
			builder.deleteCharAt(builder.length() - 1);
			builder.append(" ) ");

			if(existeFiltroOrdemServico){
				builder.append(" ) OR ");
				builder.append(" ( HIST.ORSE_ID IS NOT NULL  ");
				builder.append(" AND HIST.DEMF_ID IS NULL )) ");
			}
		}

		if(!Util.isVazioOrNulo(helper.getTiposDocumento())){
			if(existeClausula) builder.append(" AND ");
			existeClausula = true;

			if(existeFiltroOrdemServico) builder.append(" (( ");

			builder.append(" HIST.DOTP_ID IN ( ");
			for(Integer tiposDocumentoId : helper.getTiposDocumento()){
				builder.append(tiposDocumentoId);
				builder.append(",");
			}
			builder.deleteCharAt(builder.length() - 1);
			builder.append(" ) ");

			if(existeFiltroOrdemServico){
				builder.append(" ) OR ");
				builder.append(" ( HIST.ORSE_ID IS NOT NULL  ");
				builder.append(" AND HIST.DOTP_ID IS NULL )) ");
			}
		}

		if(!Util.isVazioOrNulo(helper.getTiposServico())){
			if(existeClausula) builder.append(" AND ");
			existeClausula = true;

			if(existeFiltroDocumentoCobranca) builder.append(" (( ");

			builder.append(" HIST.SVTP_ID IN ( ");
			for(Integer tiposServicoId : helper.getTiposServico()){
				builder.append(tiposServicoId);
				builder.append(",");
			}
			builder.deleteCharAt(builder.length() - 1);
			builder.append(" ) ");

			if(existeFiltroDocumentoCobranca){
				builder.append(" ) OR ");
				builder.append(" ( HIST.CBDO_ID IS NOT NULL  ");
				builder.append(" AND HIST.SVTP_ID IS NULL )) ");
			}
		}

		if(helper.getValorDebitoInicial() != null && helper.getValorDebitoFinal() != null){
			if(existeClausula) builder.append(" AND ");
			existeClausula = true;

			builder.append(" HIST.HMLI_VLDEBITO ");
			builder.append(" BETWEEN " + helper.getValorDebitoInicial());
			builder.append(" AND " + helper.getValorDebitoFinal());
		}

		if(helper.getPeriodoEmissaoInicial() != null && helper.getPeriodoEmissaoFinal() != null){
			if(existeClausula) builder.append(" AND ");
			existeClausula = true;

			builder.append(" HIST.HMLI_DTEMISSAO BETWEEN TO_DATE('");
			builder.append(Util.formatarData(helper.getPeriodoEmissaoInicial()));
			builder.append(" 00:00:00");
			builder.append("', 'dd/mm/yyyy HH24:MI:SS') AND TO_DATE('");
			builder.append(Util.formatarData(helper.getPeriodoEmissaoFinal()));
			builder.append(" 23:59:59");
			builder.append("', 'dd/mm/yyyy HH24:MI:SS') ");
		}

		builder.append(" ORDER BY ");
		builder.append("   HIST.IMOV_ID ASC, ");
		builder.append("   HIST.HMLI_DTEMISSAO DESC ");

		String sqlString = builder.toString();
		return sqlString;
	}

	/**
	 * Consulta os dados do Histórico da Manutenção da Ligação de Água
	 * {@link ConsultarHistoricoManutencaoLigacaoHelper}
	 * 
	 * @param {@link ConsultarHistoricoManutencaoLigacaoHelper}
	 * @param numeroPagina
	 * @return Collection<Object[]>
	 */
	public Collection<Object[]> consultarHistoricoManutencaoLigacao(ConsultarHistoricoManutencaoLigacaoHelper helper, Integer numeroPagina)
					throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		Collection<Object[]> resultado;
		try{
			String sqlStringBase = getConsultaHistoricoManutencaoLigacao(helper);
			SQLQuery query = session.createSQLQuery(sqlStringBase);

			query.addScalar("IMOV_ID", Hibernate.INTEGER); // 0
			query.addScalar("HMLI_DTEMISSAO", Hibernate.DATE); // 1
			query.addScalar("DOTP_DSABREVIADO", Hibernate.STRING); // 2
			query.addScalar("SVTP_DSABREVIADO", Hibernate.STRING); // 3
			query.addScalar("CBDO_ID", Hibernate.INTEGER); // 4
			query.addScalar("CAST_ID", Hibernate.INTEGER); // 5
			query.addScalar("HMLI_DTSITUACAODOCUMENTO", Hibernate.DATE); // 6
			query.addScalar("ORSE_ID_ASSOCIADO", Hibernate.INTEGER); // 7
			query.addScalar("ORSE_ID", Hibernate.INTEGER); // 8
			query.addScalar("HMLI_CDSITUACAOOS", Hibernate.SHORT); // 9
			query.addScalar("DOTP_DSDOCUMENTOTIPO", Hibernate.STRING); // 10
			query.addScalar("CAST_DSSITUACAOACAO", Hibernate.STRING); // 11
			query.addScalar("SVTP_DSSVTIPO_ASSOCIADO", Hibernate.STRING); // 12
			query.addScalar("HMLI_DTEMISSAO_ASSOCIADO", Hibernate.DATE); // 13
			query.addScalar("SVTP_DSSERVICOTIPO", Hibernate.STRING); // 14
			query.addScalar("HMLI_NNLEITURAEXECUCAO", Hibernate.INTEGER); // 15
			query.addScalar("COTP_DSCORTETIPO", Hibernate.STRING); // 16
			query.addScalar("USUR_NMLOGIN", Hibernate.STRING); // 17
			query.addScalar("AMEN_ID", Hibernate.INTEGER); // 18
			query.addScalar("HMLI_NNLEITEXECUCAO_ASSOCIADO", Hibernate.INTEGER); // 19
			query.addScalar("COTP_DSCORTETIPO_ASSOCIADO", Hibernate.STRING); // 20
			query.addScalar("USUR_NMLOGIN_ASSOC", Hibernate.STRING); // 21
			query.addScalar("AMEN_ID_ASSOCIADO", Hibernate.INTEGER); // 22

			if(numeroPagina != null){
				resultado = query.setFirstResult(10 * numeroPagina).setMaxResults(10).list();
			}else{
				resultado = query.list();
			}

		}catch(Exception e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro durante a consulta");
		}finally{

			// fecha a sessão
			HibernateUtil.closeSession(session);
		}
		return resultado;
	}

	/**
	 * [UC3076] Retorna a quantidade de registros retornados pela consulta do Histórico da
	 * Manutenção da Ligação de Água
	 * 
	 * @param {@link ConsultarHistoricoManutencaoLigacaoHelper}
	 * @return Integer
	 */
	public Integer consultarTotalRegistrosHistoricoManutencaoLigacao(ConsultarHistoricoManutencaoLigacaoHelper helper)
					throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		Collection<Object[]> resultadoConsulta;
		Integer totalRegistros = 0;
		try{
			String sqlStringBase = getConsultaHistoricoManutencaoLigacao(helper);
			SQLQuery query = session.createSQLQuery(sqlStringBase);

			query.addScalar("IMOV_ID", Hibernate.INTEGER); // 0

			resultadoConsulta = query.list();

			if(!Util.isVazioOrNulo(resultadoConsulta)){
				totalRegistros = resultadoConsulta.size();
			}

		}catch(Exception e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro durante a consulta");
		}finally{

			// fecha a sessão
			HibernateUtil.closeSession(session);
		}
		return totalRegistros;
	}
}