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
 * 
 * GSANPCG
 * Eduardo Henrique
 */

package gcom.gerencial.faturamento;

import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoSituacao;
import gcom.cadastro.cliente.ClienteRelacaoTipo;
import gcom.cadastro.cliente.EsferaPoder;
import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.imovel.ImovelPerfil;
import gcom.faturamento.ResumoFaturamentoSituacaoEspecial;
import gcom.gerencial.bean.InformarDadosGeracaoRelatorioConsultaHelper;
import gcom.relatorio.gerencial.faturamento.GeradorSqlResumoAnaliseFaturamento;
import gcom.relatorio.gerencial.faturamento.GeradorSqlResumoAnaliseFaturamento.RestricaoTipoEnum;
import gcom.util.ConstantesSistema;
import gcom.util.ErroRepositorioException;
import gcom.util.HibernateUtil;
import gcom.util.Util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.hibernate.*;

/**
 * < <Descrição da Classe>>
 * 
 * @author Administrador
 */
public class RepositorioGerencialFaturamentoHBM
				implements IRepositorioGerencialFaturamento {

	private static final String ESPACAMENTO = "  ";

	private static IRepositorioGerencialFaturamento instancia;

	/**
	 * Construtor da classe RepositorioMicromedicaoHBM
	 */
	private RepositorioGerencialFaturamentoHBM() {

	}

	/**
	 * Retorna o valor de instancia
	 * 
	 * @return O valor de instancia
	 */
	public static IRepositorioGerencialFaturamento getInstancia(){

		if(instancia == null){
			instancia = new RepositorioGerencialFaturamentoHBM();
		}

		return instancia;
	}

	/**
	 * Método que consulta os ResumoFaturamentoSituacaoEspecialHelper
	 * 
	 * @author Thiago Toscano
	 * @date 15/05/2006
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List getResumoFaturamentoSituacaoEspecialHelper(Integer idLocalidade) throws ErroRepositorioException{

		// cria a coleção de retorno
		List retorno = null;
		SQLQuery query = null;

		// obtém a sessão
		Session session = HibernateUtil.getSession();

		try{
			// pesquisa a coleção de atividades e atribui a variável "retorno"

			String sql = " SELECT" //
							+ "     DISTINCT(imovel.imov_id) as idImovel," //
							+ "     gerenciaRegional.greg_id as idGerenciaRegional," //
							+ "     localidade.loca_id as idLocalidade," //
							+ "     setorComercial.stcm_id as idSetorComercial," //
							+ "     rota.rota_id as idRota," //
							+ "     quadra.qdra_id as idQuadra," //
							+ "     setorComercial.stcm_cdsetorcomercial as cdSetorComercial," //
							+ "     quadra.qdra_nnQuadra as numeroQuadra," //
							+ "     imovelPerfil.iper_id as idImovelPerfil," //
							+ "     ligacaoAguaSituacao.last_id as idLigacaoAguaSituacao," //
							+ "     ligacaoEsgotoSituacao.lest_id as idLigacaoEsgotoSituacao," //
							+ "     (select " //
							+ "     	esferaPoder.epod_id " //
							+ "     from cliente_Imovel clienteImoveis" //
							+ " 	left join cliente_Relacao_Tipo clienteRelacaoTipo on clienterelacaotipo.crtp_id = clienteimoveis.crtp_id" //
							+ " 	left join cliente cliente on cliente.clie_id = clienteimoveis.clie_id" //
							+ " 	left join cliente_Tipo clienteTipo on clientetipo.cltp_id = cliente.cltp_id" //
							+ " 	left join esfera_Poder esferaPoder on clientetipo.epod_id = esferaPoder.epod_id" //
							+ " 	where clienteImoveis.imov_id = imovel.imov_id and clienteRelacaoTipo.crtp_id = "
							+ ClienteRelacaoTipo.RESPONSAVEL //
							+ " 		and clienteimoveis.clim_dtrelacaofim is null) as esferaPoder," //
							+ "     faturamentoSituacaoTipo.ftst_id as idFaturamentoSituacaoTipo," //
							+ "     faturamentoSituacaoMotivo.ftsm_id as idFaturamentoSituacaoMotivo," //
							+ "     faturamentoSituacaoHistorico.ftsh_amfaturamentosituacaoinic as anoMesFaturamentoSitInicio ," //
							+ "     faturamentoSituacaoHistorico.ftsh_amfaturamentosituacaofim as anoMesFaturamentoSitFim ," //
							+ "     unidadeNegocio.uneg_id as idUnidadeNegocio" //
							+ " FROM" //
							+ "     FATURAMENTO_SITUACAO_HISTORICO faturamentoSituacaoHistorico " //
							+ "     INNER JOIN FATURAMENTO_SITUACAO_TIPO faturamentoSituacaoTipo ON faturamentoSituacaoHistorico.FTST_ID = faturamentoSituacaoTipo.FTST_ID" //
							+ "     INNER JOIN FATURAMENTO_SITUACAO_MOTIVO faturamentoSituacaoMotivo ON faturamentoSituacaoHistorico.FTSM_ID = faturamentoSituacaoMotivo.FTSM_ID" //
							+ "     INNER JOIN IMOVEL imovel ON faturamentoSituacaoHistorico.IMOV_ID = imovel.IMOV_ID" //
							+ "     INNER JOIN LOCALIDADE localidade on imovel.loca_id = localidade.loca_id " //
							+ "     LEFT JOIN UNIDADE_NEGOCIO unidadeNegocio ON localidade.UNEG_ID = unidadeNegocio.UNEG_ID" //
							+ "     INNER JOIN GERENCIA_REGIONAL gerenciaRegional on localidade.greg_id = gerenciaRegional.greg_id" //
							+ "     INNER JOIN SETOR_COMERCIAL setorComercial on setorComercial.stcm_id = imovel.stcm_id " //
							+ "     INNER JOIN QUADRA quadra on imovel.qdra_id = quadra.qdra_id" //
							+ "     INNER JOIN ROTA rota on imovel.rota_id = rota.rota_id" //
							+ "     INNER JOIN LIGACAO_AGUA_SITUACAO ligacaoAguaSituacao on ligacaoAguaSituacao.last_id = imovel.last_id" //
							+ "     INNER JOIN LIGACAO_ESGOTO_SITUACAO ligacaoEsgotoSituacao on ligacaoEsgotoSituacao.lest_id = imovel.lest_id" //
							+ "     LEFT JOIN IMOVEL_PERFIL imovelPerfil on imovel.iper_id = imovelPerfil.iper_id" //
							+ " WHERE" //
							+ "     faturamentoSituacaoHistorico.ftsh_amfaturamentoretirada IS NULL" //
							+ "     AND localidade.loca_id = :idLocalidade"; //

			query = session.createSQLQuery(sql);

			query.setInteger("idLocalidade", idLocalidade);

			retorno = query.addScalar("idImovel", Hibernate.INTEGER)
							//
							.addScalar("idGerenciaRegional", Hibernate.INTEGER)
							.addScalar("idLocalidade", Hibernate.INTEGER)
							//
							.addScalar("idSetorComercial", Hibernate.INTEGER)
							.addScalar("idRota", Hibernate.INTEGER)
							//
							.addScalar("idQuadra", Hibernate.INTEGER)
							.addScalar("cdSetorComercial", Hibernate.INTEGER)
							//
							.addScalar("numeroQuadra", Hibernate.INTEGER).addScalar("idImovelPerfil", Hibernate.INTEGER)
							.addScalar("idLigacaoAguaSituacao", Hibernate.INTEGER).addScalar("idLigacaoEsgotoSituacao", Hibernate.INTEGER)
							.addScalar("esferaPoder", Hibernate.INTEGER).addScalar("idFaturamentoSituacaoTipo", Hibernate.INTEGER)
							//
							.addScalar("idFaturamentoSituacaoMotivo", Hibernate.INTEGER)
							.addScalar("anoMesFaturamentoSitInicio", Hibernate.INTEGER)//
							.addScalar("anoMesFaturamentoSitFim", Hibernate.INTEGER).addScalar("idUnidadeNegocio", Hibernate.INTEGER) //
							.list();
			// retorno = session.createQuery(hql).setMaxResults(1000).list();

			// erro no hibernate
		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}
		// retorna a coleção de atividades pesquisada(s)
		return retorno;
	}

	public Collection pesquisarResumoFaturamentoSituacaoEspecialConsultaHelper(Integer[] idSituacaoTipo, Integer[] idSituacaoMotivo)
					throws ErroRepositorioException{

		// cria a coleção de retorno
		List retorno = null;

		// obtém a sessão
		Session session = HibernateUtil.getSession();

		try{
			// pesquisa a coleção de atividades e atribui a variável "retorno"

			if(idSituacaoTipo != null && idSituacaoMotivo != null){

				String hql = " select " + " new " + ResumoFaturamentoSituacaoEspecialConsultaFinalHelper.class.getName() + " ( "
								+ "	SUM(rfse.quantidadeImovel) " + " ) " + " from " + "	ResumoFaturamentoSituacaoEspecial rfse"
								+ "	inner join rfse.gerenciaRegional gerenciaRegional " + "	inner join rfse.localidade localidade "
								+ "	inner join rfse.faturamentoSituacaoTipo faturamentoTipo "
								+ "	inner join rfse.faturamentoSituacaoMotivo faturamentoMotivo " + " where " +

								"	faturamentoTipo.id in (:idSituacaoTipo) AND" +

								"	faturamentoMotivo.id in (:idSituacaoMotivo) " + " ";

				retorno = session.createQuery(hql).setParameterList("idSituacaoTipo", idSituacaoTipo)
								.setParameterList("idSituacaoMotivo", idSituacaoMotivo).list();
			}else if(idSituacaoTipo != null){
				String hql = " select " + " new " + ResumoFaturamentoSituacaoEspecialConsultaFinalHelper.class.getName() + " ( "
								+ "	SUM(rfse.quantidadeImovel) " + " ) " + " from " + "	ResumoFaturamentoSituacaoEspecial rfse"
								+ "	inner join rfse.gerenciaRegional gerenciaRegional " + "	inner join rfse.localidade localidade "
								+ "	inner join rfse.faturamentoSituacaoTipo faturamentoTipo "
								+ "	inner join rfse.faturamentoSituacaoMotivo faturamentoMotivo " + " where "
								+ "	faturamentoTipo.id in (:idSituacaoTipo)" + " ";
				retorno = session.createQuery(hql).setParameterList("idSituacaoTipo", idSituacaoTipo).list();
			}else if(idSituacaoMotivo != null){
				String hql = " select " + " new " + ResumoFaturamentoSituacaoEspecialConsultaFinalHelper.class.getName() + " ( "
								+ "	SUM(rfse.quantidadeImovel) " + " ) " + " from " + "	ResumoFaturamentoSituacaoEspecial rfse"
								+ "	inner join rfse.gerenciaRegional gerenciaRegional " + "	inner join rfse.localidade localidade "
								+ "	inner join rfse.faturamentoSituacaoTipo faturamentoTipo "
								+ "	inner join rfse.faturamentoSituacaoMotivo faturamentoMotivo " + " where "
								+ "	faturamentoMotivo.id in (:idSituacaoMotivo)" + " ";
				retorno = session.createQuery(hql).setParameterList("idSituacaoMotivo", idSituacaoMotivo).list();
			}else{
				String hql = " select " + " new " + ResumoFaturamentoSituacaoEspecialConsultaFinalHelper.class.getName() + " ( "
								+ "	SUM(rfse.quantidadeImovel) " + " ) " + " from " + "	ResumoFaturamentoSituacaoEspecial rfse"
								+ "	inner join rfse.gerenciaRegional gerenciaRegional " + "	inner join rfse.localidade localidade "
								+ "	inner join rfse.faturamentoSituacaoTipo faturamentoTipo "
								+ "	inner join rfse.faturamentoSituacaoMotivo faturamentoMotivo " + " ";
				retorno = session.createQuery(hql).list();
			}

			// erro no hibernate
		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}
		// retorna a coleção de atividades pesquisada(s)
		return retorno;
	}

	public Collection pesquisarResumoFaturamentoSituacaoEspecialConsultaMotivoHelper(Integer idGerencia, Integer idLocalidade,
					Integer idTipo, Integer[] idSituacaoTipo, Integer[] idSituacaoMotivo) throws ErroRepositorioException{

		// cria a coleção de retorno
		List retorno = null;

		// obtém a sessão
		Session session = HibernateUtil.getSession();
		boolean flagIdSituacaoTipo = false;
		boolean flagIdSituacaoMotivo = false;

		try{
			// pesquisa a coleção de atividades e atribui a variável "retorno"

			StringBuilder hql = new StringBuilder();
			hql.append(" select new ");
			hql.append(ResumoFaturamentoSituacaoEspecialConsultaMotivoHelper.class.getName());
			hql.append(" ( ");
			hql.append(" faturamentoMotivo.id, ");
			hql.append(" faturamentoMotivo.descricao, ");
			hql.append(" MIN(rfse.anoMesInicioSituacaoEspecial), ");
			hql.append(" MAX(rfse.anoMesFinalSituacaoEspecial), ");
			hql.append(" case when ( ");
			hql.append(" SUM(rfse.quantidadeImovel) is null ");
			hql.append(" ) ");
			hql.append(" then 0 ");
			hql.append(" else ");
			hql.append(" SUM(rfse.quantidadeImovel) ");
			hql.append(" end, ");
			hql.append(" null, ");
			hql.append(" null, ");
			hql.append(" null ");
			hql.append(" ) ");
			hql.append(" from ");
			hql.append(" ResumoFaturamentoSituacaoEspecial rfse ");
			hql.append(" inner join rfse.gerenciaRegional gerenciaRegional ");
			hql.append(" inner join rfse.localidade localidade ");
			hql.append(" inner join rfse.faturamentoSituacaoTipo faturamentoTipo ");
			hql.append(" inner join rfse.faturamentoSituacaoMotivo faturamentoMotivo ");
			hql.append(" where ");
			hql.append(" rfse.gerenciaRegional.id = :idGerencia ");
			hql.append(" AND ");
			hql.append(" rfse.localidade.id = :idLocalidade ");
			hql.append(" AND ");
			hql.append(" rfse.faturamentoSituacaoTipo.id = :idTipo ");

			if(idSituacaoTipo != null || idSituacaoMotivo != null){
				if(idSituacaoTipo != null){
					hql.append(" AND faturamentoTipo.id in (:idSituacaoTipo) ");
					flagIdSituacaoTipo = true;
				}

				if(idSituacaoMotivo != null){
					hql.append(" AND faturamentoMotivo.id in (:idSituacaoMotivo) ");
					flagIdSituacaoMotivo = true;
				}
			}

			hql.append(" group by ");
			hql.append(" faturamentoMotivo.id, ");
			hql.append(" faturamentoMotivo.descricao ");
			hql.append(" order by ");
			hql.append(" faturamentoMotivo.id ");

			Query createQuery = session.createQuery(hql.toString());
			createQuery.setInteger("idGerencia", idGerencia);
			createQuery.setInteger("idLocalidade", idLocalidade);
			createQuery.setInteger("idTipo", idTipo);

			if(flagIdSituacaoTipo){
				createQuery.setParameterList("idSituacaoTipo", idSituacaoTipo);
			}
			if(flagIdSituacaoMotivo){
				createQuery.setParameterList("idSituacaoMotivo", idSituacaoMotivo);
			}

			retorno = createQuery.list();

			/*
			 * if(idSituacaoTipo != null && idSituacaoMotivo != null){
			 * String hql = " select " + " new " +
			 * ResumoFaturamentoSituacaoEspecialConsultaMotivoHelper.class.getName() + " ( "
			 * +
			 * "	faturamentoMotivo.id, faturamentoMotivo.descricao, MIN(rfse.anoMesInicioSituacaoEspecial), "
			 * + "	MAX(rfse.anoMesFinalSituacaoEspecial), case when ( "
			 * + "    SUM(rfse.quantidadeImovel) is null ) then 0 " + " else "
			 * + "    SUM(rfse.quantidadeImovel) end , null, null, null" + " ) " + " from "
			 * + "	ResumoFaturamentoSituacaoEspecial rfse" +
			 * "	inner join rfse.gerenciaRegional gerenciaRegional "
			 * + "	inner join rfse.localidade localidade " +
			 * "	inner join rfse.faturamentoSituacaoTipo faturamentoTipo "
			 * + "	inner join rfse.faturamentoSituacaoMotivo faturamentoMotivo " + " where "
			 * + "	rfse.gerenciaRegional.id = :idGerencia AND " +
			 * "   rfse.localidade.id = :idLocalidade AND "
			 * + "   rfse.faturamentoSituacaoTipo.id = :idTipo AND " +
			 * "	faturamentoTipo.id in (:idSituacaoTipo) AND"
			 * + "	faturamentoMotivo.id in (:idSituacaoMotivo) " + " group by"
			 * + " faturamentoMotivo.id, faturamentoMotivo.descricao" +
			 * " order by faturamentoMotivo.id";
			 * retorno = session.createQuery(hql).setInteger("idGerencia",
			 * idGerencia).setInteger("idLocalidade", idLocalidade)
			 * .setInteger("idTipo", idTipo).setParameterList("idSituacaoTipo",
			 * idSituacaoTipo).setParameterList(
			 * "idSituacaoMotivo", idSituacaoMotivo).list();
			 * }else if(idSituacaoTipo != null){
			 * String hql = " select " + " new " +
			 * ResumoFaturamentoSituacaoEspecialConsultaMotivoHelper.class.getName() + " ( "
			 * +
			 * "	faturamentoMotivo.id, faturamentoMotivo.descricao, MIN(rfse.anoMesInicioSituacaoEspecial), "
			 * + "	MAX(rfse.anoMesFinalSituacaoEspecial), case when ( "
			 * + "    SUM(rfse.quantidadeImovel) is null ) then 0 " + " else "
			 * + "    SUM(rfse.quantidadeImovel) end , null, null, null" + " ) " + " from "
			 * + "	ResumoFaturamentoSituacaoEspecial rfse" +
			 * "	inner join rfse.gerenciaRegional gerenciaRegional "
			 * + "	inner join rfse.localidade localidade " +
			 * "	inner join rfse.faturamentoSituacaoTipo faturamentoTipo "
			 * + "	inner join rfse.faturamentoSituacaoMotivo faturamentoMotivo " + " where "
			 * + "	rfse.gerenciaRegional.id = :idGerencia AND " +
			 * "   rfse.localidade.id = :idLocalidade AND "
			 * + "   rfse.faturamentoSituacaoTipo.id = :idTipo AND " +
			 * "	faturamentoTipo.id in (:idSituacaoTipo) "
			 * + " group by" + " faturamentoMotivo.id, faturamentoMotivo.descricao" +
			 * " order by faturamentoMotivo.id";
			 * retorno = session.createQuery(hql).setInteger("idGerencia",
			 * idGerencia).setInteger("idLocalidade", idLocalidade)
			 * .setInteger("idTipo", idTipo).setParameterList("idSituacaoTipo",
			 * idSituacaoTipo).list();
			 * }else if(idSituacaoMotivo != null){
			 * String hql = " select " + " new " +
			 * ResumoFaturamentoSituacaoEspecialConsultaMotivoHelper.class.getName() + " ( "
			 * +
			 * "	faturamentoMotivo.id, faturamentoMotivo.descricao, MIN(rfse.anoMesInicioSituacaoEspecial), "
			 * + "	MAX(rfse.anoMesFinalSituacaoEspecial), case when ( "
			 * + "    SUM(rfse.quantidadeImovel) is null ) then 0 " + " else "
			 * + "    SUM(rfse.quantidadeImovel) end , null, null, null" + " ) " + " from "
			 * + "	ResumoFaturamentoSituacaoEspecial rfse" +
			 * "	inner join rfse.gerenciaRegional gerenciaRegional "
			 * + "	inner join rfse.localidade localidade " +
			 * "	inner join rfse.faturamentoSituacaoTipo faturamentoTipo "
			 * + "	inner join rfse.faturamentoSituacaoMotivo faturamentoMotivo " + " where "
			 * + "	rfse.gerenciaRegional.id = :idGerencia AND " +
			 * "   rfse.localidade.id = :idLocalidade AND "
			 * + "   rfse.faturamentoSituacaoTipo.id = :idTipo AND " +
			 * "	faturamentoMotivo.id in (:idSituacaoMotivo) "
			 * + " group by" + " faturamentoMotivo.id, faturamentoMotivo.descricao" +
			 * " order by faturamentoMotivo.id";
			 * retorno = session.createQuery(hql).setInteger("idGerencia",
			 * idGerencia).setInteger("idLocalidade", idLocalidade)
			 * .setInteger("idTipo", idTipo).setParameterList("idSituacaoMotivo",
			 * idSituacaoMotivo).list();
			 * }else{
			 * String hql = " select " + " new " +
			 * ResumoFaturamentoSituacaoEspecialConsultaMotivoHelper.class.getName() + " ( "
			 * +
			 * "	faturamentoMotivo.id, faturamentoMotivo.descricao, MIN(rfse.anoMesInicioSituacaoEspecial), "
			 * + "	MAX(rfse.anoMesFinalSituacaoEspecial), case when ( "
			 * + "    SUM(rfse.quantidadeImovel) is null ) then 0 " + " else "
			 * + "    SUM(rfse.quantidadeImovel) end , null, null, null" + " ) " + " from "
			 * + "	ResumoFaturamentoSituacaoEspecial rfse" +
			 * "	inner join rfse.gerenciaRegional gerenciaRegional "
			 * + "	inner join rfse.localidade localidade " +
			 * "	inner join rfse.faturamentoSituacaoTipo faturamentoTipo "
			 * + "	inner join rfse.faturamentoSituacaoMotivo faturamentoMotivo " + " where "
			 * + "	rfse.gerenciaRegional.id = :idGerencia AND " +
			 * "   rfse.localidade.id = :idLocalidade AND "
			 * + "   rfse.faturamentoSituacaoTipo.id = :idTipo " + " group by"
			 * + " faturamentoMotivo.id, faturamentoMotivo.descricao" +
			 * " order by faturamentoMotivo.id";
			 * retorno = session.createQuery(hql).setInteger("idGerencia",
			 * idGerencia).setInteger("idLocalidade", idLocalidade)
			 * .setInteger("idTipo", idTipo).list();
			 */

			// erro no hibernate
		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}
		// retorna a coleção de atividades pesquisada(s)
		return retorno;
	}

	public Collection pesquisarResumoFaturamentoSituacaoEspecialConsultaSitTipoHelper(Integer idGerencia, Integer idLocalidade,
					Integer[] idSituacaoTipo, Integer[] idSituacaoMotivo) throws ErroRepositorioException{

		// cria a coleção de retorno
		List retorno = null;

		// obtém a sessão
		Session session = HibernateUtil.getSession();
		boolean flagIdSituacaoTipo = false;
		boolean flagIdSituacaoMotivo = false;

		try{

			StringBuilder hql = new StringBuilder();
			hql.append(" select new ");
			hql.append(ResumoFaturamentoSituacaoEspecialConsultaSitTipoHelper.class.getName());
			hql.append(" ( ");
			hql.append(" faturamentoTipo.id, ");
			hql.append(" faturamentoTipo.descricao, ");
			hql.append(" case when ( ");
			hql.append(" SUM(rfse.quantidadeImovel) is null ");
			hql.append(" ) ");
			hql.append(" then 0 ");
			hql.append(" else ");
			hql.append(" SUM(rfse.quantidadeImovel) ");
			hql.append(" end ");
			hql.append(" ) ");
			hql.append(" from ");
			hql.append(" ResumoFaturamentoSituacaoEspecial rfse ");
			hql.append(" inner join rfse.gerenciaRegional gerenciaRegional ");
			hql.append(" inner join rfse.localidade localidade ");
			hql.append(" inner join rfse.faturamentoSituacaoTipo faturamentoTipo ");
			hql.append(" inner join rfse.faturamentoSituacaoMotivo faturamentoMotivo ");
			hql.append(" where ");
			hql.append(" rfse.gerenciaRegional.id = :idGerencia ");
			hql.append(" AND ");
			hql.append(" rfse.localidade.id = :idLocalidade ");

			if(idSituacaoTipo != null || idSituacaoMotivo != null){
				if(idSituacaoTipo != null){
					hql.append(" AND faturamentoTipo.id in (:idSituacaoTipo) ");
					flagIdSituacaoTipo = true;
				}

				if(idSituacaoMotivo != null){
					hql.append(" AND faturamentoMotivo.id in (:idSituacaoMotivo) ");
					flagIdSituacaoMotivo = true;
				}
			}

			hql.append(" group by ");
			hql.append(" faturamentoTipo.id, ");
			hql.append(" faturamentoTipo.descricao ");
			hql.append(" order by ");
			hql.append(" faturamentoTipo.id ");

			Query createQuery = session.createQuery(hql.toString());
			createQuery.setInteger("idGerencia", idGerencia);
			createQuery.setInteger("idLocalidade", idLocalidade);

			if(flagIdSituacaoTipo){
				createQuery.setParameterList("idSituacaoTipo", idSituacaoTipo);
			}
			if(flagIdSituacaoMotivo){
				createQuery.setParameterList("idSituacaoMotivo", idSituacaoMotivo);
			}

			retorno = createQuery.list();

			// pesquisa a coleção de atividades e atribui a variável "retorno"
			/*
			 * if(idSituacaoTipo != null && idSituacaoMotivo != null){
			 * String hql = " select " + " new " +
			 * ResumoFaturamentoSituacaoEspecialConsultaSitTipoHelper.class.getName() + " ( "
			 * + "   faturamentoTipo.id, faturamentoTipo.descricao, " + "	case when ( "
			 * + "    SUM(rfse.quantidadeImovel) is null ) then 0 " + " else " +
			 * "    SUM(rfse.quantidadeImovel) end  "
			 * + " ) " + " from "
			 * + "	ResumoFaturamentoSituacaoEspecial rfse" +
			 * "	inner join rfse.gerenciaRegional gerenciaRegional "
			 * + "	inner join rfse.localidade localidade " +
			 * "	inner join rfse.faturamentoSituacaoTipo faturamentoTipo "
			 * + "	inner join rfse.faturamentoSituacaoMotivo faturamentoMotivo " + " where "
			 * + "	rfse.gerenciaRegional.id = :idGerencia AND " +
			 * "   rfse.localidade.id = :idLocalidade AND "
			 * + "   faturamentoTipo.id in (:idSituacaoTipo) AND" +
			 * "	faturamentoMotivo.id in (:idSituacaoMotivo) "
			 * + " group by" + "  faturamentoTipo.id, faturamentoTipo.descricao" +
			 * " order by faturamentoTipo.id";
			 * retorno = session.createQuery(hql).setInteger("idGerencia",
			 * idGerencia).setInteger("idLocalidade", idLocalidade)
			 * .setParameterList("idSituacaoTipo",
			 * idSituacaoTipo).setParameterList("idSituacaoMotivo", idSituacaoMotivo)
			 * .list();
			 * }else if(idSituacaoTipo != null){
			 * String hql = " select " + " new " +
			 * ResumoFaturamentoSituacaoEspecialConsultaSitTipoHelper.class.getName() + " ( "
			 * + "   faturamentoTipo.id, faturamentoTipo.descricao, " + "	case when ( "
			 * + "    SUM(rfse.quantidadeImovel) is null ) then 0 " + " else " +
			 * "    SUM(rfse.quantidadeImovel) end  "
			 * + " ) " + " from "
			 * + "	ResumoFaturamentoSituacaoEspecial rfse" +
			 * "	inner join rfse.gerenciaRegional gerenciaRegional "
			 * + "	inner join rfse.localidade localidade " +
			 * "	inner join rfse.faturamentoSituacaoTipo faturamentoTipo "
			 * + "	inner join rfse.faturamentoSituacaoMotivo faturamentoMotivo " + " where "
			 * + "	rfse.gerenciaRegional.id = :idGerencia AND " +
			 * "   rfse.localidade.id = :idLocalidade AND "
			 * + "   faturamentoTipo.id in (:idSituacaoTipo) " + " group by"
			 * + "  faturamentoTipo.id, faturamentoTipo.descricao" + " order by faturamentoTipo.id";
			 * retorno = session.createQuery(hql).setInteger("idGerencia",
			 * idGerencia).setInteger("idLocalidade", idLocalidade)
			 * .setParameterList("idSituacaoTipo", idSituacaoTipo).list();
			 * }else if(idSituacaoMotivo != null){
			 * String hql = " select " + " new " +
			 * ResumoFaturamentoSituacaoEspecialConsultaSitTipoHelper.class.getName() + " ( "
			 * + "   faturamentoTipo.id, faturamentoTipo.descricao, " + "	case when ( "
			 * + "    SUM(rfse.quantidadeImovel) is null ) then 0 " + " else " +
			 * "    SUM(rfse.quantidadeImovel) end  "
			 * + " ) " + " from "
			 * + "	ResumoFaturamentoSituacaoEspecial rfse" +
			 * "	inner join rfse.gerenciaRegional gerenciaRegional "
			 * + "	inner join rfse.localidade localidade " +
			 * "	inner join rfse.faturamentoSituacaoTipo faturamentoTipo "
			 * + "	inner join rfse.faturamentoSituacaoMotivo faturamentoMotivo " + " where "
			 * + "	rfse.gerenciaRegional.id = :idGerencia AND " +
			 * "   rfse.localidade.id = :idLocalidade AND "
			 * + "	faturamentoMotivo.id in (:idSituacaoMotivo) " + " group by"
			 * + "  faturamentoTipo.id, faturamentoTipo.descricao" + " order by faturamentoTipo.id";
			 * retorno = session.createQuery(hql).setInteger("idGerencia",
			 * idGerencia).setInteger("idLocalidade", idLocalidade)
			 * .setParameterList("idSituacaoMotivo", idSituacaoMotivo).list();
			 * }else{
			 * String hql = " select " + " new " +
			 * ResumoFaturamentoSituacaoEspecialConsultaSitTipoHelper.class.getName() + " ( "
			 * + "   faturamentoTipo.id, faturamentoTipo.descricao, " + "	case when ( "
			 * + "    SUM(rfse.quantidadeImovel) is null ) then 0 " + " else " +
			 * "    SUM(rfse.quantidadeImovel) end  "
			 * + " ) " + " from "
			 * + "	ResumoFaturamentoSituacaoEspecial rfse" +
			 * "	inner join rfse.gerenciaRegional gerenciaRegional "
			 * + "	inner join rfse.localidade localidade " +
			 * "	inner join rfse.faturamentoSituacaoTipo faturamentoTipo "
			 * + "	inner join rfse.faturamentoSituacaoMotivo faturamentoMotivo " + " where "
			 * + "	rfse.gerenciaRegional.id = :idGerencia AND " +
			 * "   rfse.localidade.id = :idLocalidade " + " group by"
			 * + "  faturamentoTipo.id, faturamentoTipo.descricao" + " order by faturamentoTipo.id";
			 * retorno = session.createQuery(hql).setInteger("idGerencia",
			 * idGerencia).setInteger("idLocalidade", idLocalidade).list();
			 * }
			 */
			// erro no hibernate
		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}
		// retorna a coleção de atividades pesquisada(s)
		return retorno;
	}

	public Collection pesquisarResumoFaturamentoSituacaoEspecialConsultaLocalidadeHelper(Integer idGerenciaRegional,
					Integer idUnidadeNegocio, Integer[] idSituacaoTipo, Integer[] idSituacaoMotivo) throws ErroRepositorioException{

		// cria a coleção de retorno
		List retorno = null;

		// obtém a sessão
		Session session = HibernateUtil.getSession();

		boolean flagIdSituacaoTipo = false;
		boolean flagIdSituacaoMotivo = false;

		try{

			StringBuilder hql = new StringBuilder();
			hql.append(" select new ");
			hql.append(ResumoFaturamentoSituacaoEspecialConsultaLocalidadeHelper.class.getName());
			hql.append(" ( ");
			hql.append(" localidade.id,  ");
			hql.append(" localidade.descricao, ");
			hql.append(" case when ( ");
			hql.append(" SUM(rfse.quantidadeImovel) is null ");
			hql.append(" ) ");
			hql.append(" then 0 ");
			hql.append(" else ");
			hql.append(" SUM(rfse.quantidadeImovel) ");
			hql.append(" end ");
			hql.append(" ) ");
			hql.append(" from ");
			hql.append(" ResumoFaturamentoSituacaoEspecial rfse ");
			hql.append(" inner join rfse.gerenciaRegional gerenciaRegional ");
			hql.append(" inner join rfse.localidade localidade ");
			hql.append(" inner join rfse.faturamentoSituacaoTipo faturamentoTipo ");
			hql.append(" inner join rfse.faturamentoSituacaoMotivo faturamentoMotivo ");
			hql.append(" where ");
			hql.append(" rfse.gerenciaRegional.id = :idGerenciaRegional ");
			hql.append(" AND ");
			hql.append(" rfse.localidade.unidadeNegocio.id = :idUnidadeNegocio ");

			if(idSituacaoTipo != null || idSituacaoMotivo != null){
				if(idSituacaoTipo != null){
					hql.append(" AND faturamentoTipo.id in (:idSituacaoTipo) ");
					flagIdSituacaoTipo = true;
				}

				if(idSituacaoMotivo != null){
					hql.append(" AND faturamentoMotivo.id in (:idSituacaoMotivo) ");
					flagIdSituacaoMotivo = true;
				}
			}

			hql.append(" group by ");
			hql.append(" localidade.id,  ");
			hql.append(" localidade.descricao ");
			hql.append(" order by ");
			hql.append(" localidade.id ");

			Query createQuery = session.createQuery(hql.toString());
			createQuery.setInteger("idGerenciaRegional", idGerenciaRegional);
			createQuery.setInteger("idUnidadeNegocio", idUnidadeNegocio);

			if(flagIdSituacaoTipo){
				createQuery.setParameterList("idSituacaoTipo", idSituacaoTipo);
			}
			if(flagIdSituacaoMotivo){
				createQuery.setParameterList("idSituacaoMotivo", idSituacaoMotivo);
			}

			retorno = createQuery.list();

			// pesquisa a coleção de localidades e atribui a variável "retorno"

			/*
			 * if(idSituacaoTipo != null && idSituacaoMotivo != null){
			 * String hql = " select " + " new " +
			 * ResumoFaturamentoSituacaoEspecialConsultaLocalidadeHelper.class.getName() + " ( "
			 * + "	localidade.id, localidade.descricao, case when ( " +
			 * "    SUM(rfse.quantidadeImovel) is null ) then 0 "
			 * + " else " + "    SUM(rfse.quantidadeImovel) end " + " ) " + " from "
			 * + "	ResumoFaturamentoSituacaoEspecial rfse" +
			 * "	inner join rfse.gerenciaRegional gerenciaRegional "
			 * + "	inner join rfse.localidade localidade " +
			 * "	inner join rfse.faturamentoSituacaoTipo faturamentoTipo "
			 * + "	inner join rfse.faturamentoSituacaoMotivo faturamentoMotivo " + " where "
			 * + " gerenciaRegional.id = :idGerenciaRegional AND "
			 * + " rfse.localidade.unidadeNegocio.id = :idUnidadeNegocio AND "
			 * + "	faturamentoTipo.id in (:idSituacaoTipo) AND "
			 * + "	faturamentoMotivo.id in (:idSituacaoMotivo) " + " group by" +
			 * "  localidade.id, localidade.descricao "
			 * + " order by localidade.id ";
			 * retorno = session.createQuery(hql).setInteger("idGerenciaRegional",
			 * idGerenciaRegional)
			 * .setInteger("idUnidadeNegocio", idUnidadeNegocio)
			 * .setParameterList("idSituacaoTipo", idSituacaoTipo)
			 * .setParameterList("idSituacaoMotivo", idSituacaoMotivo).list();
			 * }else if(idSituacaoTipo != null){
			 * String hql = " select " + " new " +
			 * ResumoFaturamentoSituacaoEspecialConsultaLocalidadeHelper.class.getName() + " ( "
			 * + "	localidade.id, localidade.descricao, case when ( " +
			 * "    SUM(rfse.quantidadeImovel) is null ) then 0 "
			 * + " else " + "    SUM(rfse.quantidadeImovel) end " + " ) " + " from "
			 * + "	ResumoFaturamentoSituacaoEspecial rfse" +
			 * "	inner join rfse.gerenciaRegional gerenciaRegional "
			 * + "	inner join rfse.localidade localidade " +
			 * "	inner join rfse.faturamentoSituacaoTipo faturamentoTipo "
			 * + "	inner join rfse.faturamentoSituacaoMotivo faturamentoMotivo " + " where "
			 * + " gerenciaRegional.id = :idGerenciaRegional AND "
			 * + "   rfse.localidade.unidadeNegocio.id = :idUnidadeNegocio AND "
			 * + "	faturamentoTipo.id in (:idSituacaoTipo) "
			 * + " group by" + "  localidade.id, localidade.descricao " + " order by localidade.id";
			 * retorno = session.createQuery(hql).setInteger("idGerenciaRegional",
			 * idGerenciaRegional)
			 * .setInteger("idUnidadeNegocio", idUnidadeNegocio)
			 * .setParameterList("idSituacaoTipo", idSituacaoTipo)
			 * .list();
			 * }else if(idSituacaoMotivo != null){
			 * String hql = " select " + " new " +
			 * ResumoFaturamentoSituacaoEspecialConsultaLocalidadeHelper.class.getName() + " ( "
			 * + "	localidade.id, localidade.descricao, case when ( " +
			 * "    SUM(rfse.quantidadeImovel) is null ) then 0 "
			 * + " else " + "    SUM(rfse.quantidadeImovel) end " + " ) " + " from "
			 * + "	ResumoFaturamentoSituacaoEspecial rfse" +
			 * "	inner join rfse.gerenciaRegional gerenciaRegional "
			 * + "	inner join rfse.localidade localidade " +
			 * "	inner join rfse.faturamentoSituacaoTipo faturamentoTipo "
			 * + "	inner join rfse.faturamentoSituacaoMotivo faturamentoMotivo " + " where "
			 * + " gerenciaRegional.id = :idGerenciaRegional AND "
			 * + "   rfse.localidade.unidadeNegocio.id = :idUnidadeNegocio AND "
			 * + "	faturamentoMotivo.id in (:idSituacaoMotivo) "
			 * + " group by" + "  localidade.id, localidade.descricao " + " order by localidade.id";
			 * retorno = session.createQuery(hql).setInteger("idGerenciaRegional",
			 * idGerenciaRegional)
			 * .setInteger("idUnidadeNegocio", idUnidadeNegocio)
			 * .setParameterList("idSituacaoMotivo",
			 * idSituacaoMotivo).list();
			 * }else{
			 * String hql = " select " + " new " +
			 * ResumoFaturamentoSituacaoEspecialConsultaLocalidadeHelper.class.getName() + " ( "
			 * + "	localidade.id, localidade.descricao, case when ( " +
			 * "    SUM(rfse.quantidadeImovel) is null ) then 0 "
			 * + " else " + "    SUM(rfse.quantidadeImovel) end " + " ) " + " from "
			 * + "	ResumoFaturamentoSituacaoEspecial rfse" +
			 * "	inner join rfse.gerenciaRegional gerenciaRegional "
			 * + "	inner join rfse.localidade localidade " +
			 * "	inner join rfse.faturamentoSituacaoTipo faturamentoTipo "
			 * + "	inner join rfse.faturamentoSituacaoMotivo faturamentoMotivo " + " where "
			 * + " gerenciaRegional.id = :idGerenciaRegional AND "
			 * + "   rfse.localidade.unidadeNegocio.id = :idUnidadeNegocio " + " group by"
			 * + "  localidade.id, localidade.descricao "
			 * + " order by localidade.id";
			 * retorno = session.createQuery(hql).setInteger("idGerenciaRegional",
			 * idGerenciaRegional)
			 * .setInteger("idUnidadeNegocio", idUnidadeNegocio).list();
			 * }
			 */
			// erro no hibernate
		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}
		// retorna a coleção de atividades pesquisada(s)
		return retorno;
	}

	public Collection pesquisarResumoFaturamentoSituacaoEspecialConsultaUnidadeNegocioHelper(Integer idGerencia, Integer[] idSituacaoTipo,
					Integer[] idSituacaoMotivo) throws ErroRepositorioException{

		// cria a coleção de retorno
		List retorno = null;

		// obtém a sessão
		Session session = HibernateUtil.getSession();

		boolean flagIdSituacaoTipo = false;
		boolean flagIdSituacaoMotivo = false;

		try{

			StringBuilder hql = new StringBuilder();
			hql.append(" select new ");
			hql.append(ResumoFaturamentoSituacaoEspecialConsultaUnidadeNegocioHelper.class.getName());
			hql.append(" ( ");
			hql.append(" case when ( ");
			hql.append(" unidadeNegocio.id is null ");
			hql.append(" ) ");
			hql.append(" then 0 ");
			hql.append(" else ");
			hql.append(" unidadeNegocio.id ");
			hql.append(" end, ");
			hql.append(" case when ( ");
			hql.append(" unidadeNegocio.nome is null ");
			hql.append(" ) ");
			hql.append(" then ' ' ");
			hql.append(" else ");
			hql.append(" unidadeNegocio.nome ");
			hql.append(" end, ");
			hql.append(" case when ( ");
			hql.append(" SUM(rfse.quantidadeImovel) is null ");
			hql.append(" ) ");
			hql.append(" then 0 ");
			hql.append(" else ");
			hql.append(" SUM(rfse.quantidadeImovel) ");
			hql.append(" end ");
			hql.append(" ) ");
			hql.append(" from ");
			hql.append(" ResumoFaturamentoSituacaoEspecial rfse ");
			hql.append(" inner join rfse.gerenciaRegional gerenciaRegional ");
			hql.append(" inner join rfse.localidade localidade ");
			hql.append(" inner join rfse.faturamentoSituacaoTipo faturamentoTipo ");
			hql.append(" inner join rfse.faturamentoSituacaoMotivo faturamentoMotivo ");
			hql.append(" inner join localidade.unidadeNegocio unidadeNegocio ");
			hql.append(" where ");
			hql.append(" rfse.gerenciaRegional.id = :idGerencia ");

			if(idSituacaoTipo != null || idSituacaoMotivo != null){
				if(idSituacaoTipo != null){
					hql.append(" AND faturamentoTipo.id in (:idSituacaoTipo) ");
					flagIdSituacaoTipo = true;
				}

				if(idSituacaoMotivo != null){
					hql.append(" AND faturamentoMotivo.id in (:idSituacaoMotivo) ");
					flagIdSituacaoMotivo = true;
				}
			}

			hql.append(" group by ");
			hql.append(" unidadeNegocio.id, ");
			hql.append(" unidadeNegocio.nome ");
			hql.append(" order by ");
			hql.append(" unidadeNegocio.id ");

			Query createQuery = session.createQuery(hql.toString());
			createQuery.setInteger("idGerencia", idGerencia);

			if(flagIdSituacaoTipo){
				createQuery.setParameterList("idSituacaoTipo", idSituacaoTipo);
			}
			if(flagIdSituacaoMotivo){
				createQuery.setParameterList("idSituacaoMotivo", idSituacaoMotivo);
			}

			retorno = createQuery.list();
			// pesquisa a coleção de atividades e atribui a variável "retorno"
			/*
			 * if(idSituacaoTipo != null && idSituacaoMotivo != null){
			 * String hql = " select "
			 * + " new "
			 * + ResumoFaturamentoSituacaoEspecialConsultaUnidadeNegocioHelper.class.getName()
			 * + " ( "//
			 * + " case when ( unidadeNegocio.id is null ) then 0 else unidadeNegocio.id end, "
			 * +
			 * "  case when ( unidadeNegocio.nome is null ) then ' ' else unidadeNegocio.nome end, "
			 * +
			 * "	case when ( SUM(rfse.quantidadeImovel) is null ) then 0 else SUM(rfse.quantidadeImovel) end "
			 * + " ) "
			 * + " from "
			 * + "	ResumoFaturamentoSituacaoEspecial rfse" +
			 * "	inner join rfse.gerenciaRegional gerenciaRegional "
			 * + "	inner join rfse.localidade localidade " +
			 * "	inner join rfse.faturamentoSituacaoTipo faturamentoTipo "
			 * + "	inner join rfse.faturamentoSituacaoMotivo faturamentoMotivo "
			 * + " inner join localidade.unidadeNegocio unidadeNegocio " + " where "
			 * + "   rfse.gerenciaRegional.id = :idGerencia AND " +
			 * "	faturamentoTipo.id in (:idSituacaoTipo) AND"
			 * + "	faturamentoMotivo.id in (:idSituacaoMotivo) " + " group by"
			 * + "  unidadeNegocio.id, unidadeNegocio.nome "
			 * + " order by unidadeNegocio.id";
			 * retorno = session.createQuery(hql).setInteger("idGerencia",
			 * idGerencia).setParameterList("idSituacaoTipo", idSituacaoTipo)
			 * .setParameterList("idSituacaoMotivo", idSituacaoMotivo).list();
			 * }else if(idSituacaoTipo != null){
			 * String hql = " select "
			 * + " new "
			 * + ResumoFaturamentoSituacaoEspecialConsultaUnidadeNegocioHelper.class.getName()
			 * + " ( "//
			 * + " case when ( unidadeNegocio.id is null ) then 0 else unidadeNegocio.id end, "
			 * +
			 * "  case when ( unidadeNegocio.nome is null ) then ' ' else unidadeNegocio.nome end, "
			 * +
			 * "	case when ( SUM(rfse.quantidadeImovel) is null ) then 0 else SUM(rfse.quantidadeImovel) end "
			 * + " ) "
			 * + " from "
			 * + "	ResumoFaturamentoSituacaoEspecial rfse" +
			 * "	inner join rfse.gerenciaRegional gerenciaRegional "
			 * + "	inner join rfse.localidade localidade " +
			 * "	inner join rfse.faturamentoSituacaoTipo faturamentoTipo "
			 * + "	inner join rfse.faturamentoSituacaoMotivo faturamentoMotivo "
			 * + " inner join localidade.unidadeNegocio unidadeNegocio " + " where "
			 * + "   rfse.gerenciaRegional.id = :idGerencia AND " +
			 * "	faturamentoTipo.id in (:idSituacaoTipo) "
			 * + " group by unidadeNegocio.id, unidadeNegocio.nome "
			 * + " order by unidadeNegocio.id";
			 * retorno = session.createQuery(hql).setInteger("idGerencia",
			 * idGerencia).setParameterList("idSituacaoTipo", idSituacaoTipo)
			 * .list();
			 * }else if(idSituacaoMotivo != null){
			 * String hql = " select " + " new " +
			 * ResumoFaturamentoSituacaoEspecialConsultaUnidadeNegocioHelper.class.getName() + " ( "
			 * + " case when ( unidadeNegocio.id is null ) then 0 else unidadeNegocio.id end, "
			 * +
			 * "  case when ( unidadeNegocio.nome is null ) then ' ' else unidadeNegocio.nome end, "
			 * +
			 * "	case when ( SUM(rfse.quantidadeImovel) is null ) then 0 else SUM(rfse.quantidadeImovel) end "
			 * + " ) "
			 * + " from "
			 * + "	ResumoFaturamentoSituacaoEspecial rfse" +
			 * "	inner join rfse.gerenciaRegional gerenciaRegional "
			 * + "	inner join rfse.localidade localidade " +
			 * "	inner join rfse.faturamentoSituacaoTipo faturamentoTipo "
			 * + "	inner join rfse.faturamentoSituacaoMotivo faturamentoMotivo "
			 * + " inner join localidade.unidadeNegocio unidadeNegocio " + " where "
			 * + "   rfse.gerenciaRegional.id = :idGerencia AND " +
			 * "	faturamentoMotivo.id in (:idSituacaoMotivo) "
			 * + " group by" + "  unidadeNegocio.id, unidadeNegocio.nome "
			 * + " order by unidadeNegocio.id";
			 * retorno = session.createQuery(hql).setInteger("idGerencia", idGerencia)
			 * .setParameterList("idSituacaoMotivo", idSituacaoMotivo).list();
			 * }else{
			 * String hql = " select "
			 * + " new "
			 * + ResumoFaturamentoSituacaoEspecialConsultaUnidadeNegocioHelper.class.getName() //
			 * + " ( "//
			 * + " case when ( unidadeNegocio.id is null ) then 0 else unidadeNegocio.id end, "
			 * +
			 * "  case when ( unidadeNegocio.nome is null ) then ' ' else unidadeNegocio.nome end, "
			 * +
			 * "	case when ( SUM(rfse.quantidadeImovel) is null ) then 0 else SUM(rfse.quantidadeImovel) end "
			 * + " ) "
			 * + " from ResumoFaturamentoSituacaoEspecial rfse" //
			 * + "	inner join rfse.gerenciaRegional gerenciaRegional "//
			 * + "	inner join rfse.localidade localidade " //
			 * + "	inner join rfse.faturamentoSituacaoTipo faturamentoTipo "//
			 * + "	inner join rfse.faturamentoSituacaoMotivo faturamentoMotivo " //
			 * + " inner join localidade.unidadeNegocio unidadeNegocio "//
			 * + " where rfse.gerenciaRegional.id = :idGerencia " //
			 * + " group by" //
			 * + "  unidadeNegocio.id, unidadeNegocio.nome "//
			 * + " order by unidadeNegocio.id";
			 * retorno = session.createQuery(hql).setInteger("idGerencia", idGerencia).list();
			 * }
			 */
			// erro no hibernate
		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}
		// retorna a coleção de atividades pesquisada(s)
		return retorno;
	}

	public Collection pesquisarResumoFaturamentoSituacaoEspecialConsultaGerenciaRegionalHelper(Integer[] idSituacaoTipo,
					Integer[] idSituacaoMotivo) throws ErroRepositorioException{

		// cria a coleção de retorno
		List retorno = null;

		// obtém a sessão
		Session session = HibernateUtil.getSession();

		boolean flagIdSituacaoTipo = false;
		boolean flagIdSituacaoMotivo = false;

		try{

			StringBuilder hql = new StringBuilder();
			hql.append(" select new ");
			hql.append(ResumoFaturamentoSituacaoEspecialConsultaGerenciaRegHelper.class.getName());
			hql.append(" ( ");
			hql.append(" gerenciaRegional.id, ");
			hql.append(" gerenciaRegional.nomeAbreviado,  ");
			hql.append(" gerenciaRegional.nome, ");
			hql.append(" case when ( ");
			hql.append(" SUM(rfse.quantidadeImovel) is null ");
			hql.append(" ) ");
			hql.append(" then 0 ");
			hql.append(" else ");
			hql.append(" SUM(rfse.quantidadeImovel) ");
			hql.append(" end ");
			hql.append(" ) ");
			hql.append(" from ");
			hql.append(" ResumoFaturamentoSituacaoEspecial rfse ");
			hql.append(" inner join rfse.gerenciaRegional gerenciaRegional ");
			hql.append(" inner join rfse.localidade localidade ");
			hql.append(" left join localidade.unidadeNegocio unidadeNegocio ");
			hql.append(" inner join rfse.faturamentoSituacaoTipo faturamentoTipo ");
			hql.append(" inner join rfse.faturamentoSituacaoMotivo faturamentoMotivo ");

			if(idSituacaoTipo != null || idSituacaoMotivo != null){
				hql.append(" where ");
				if(idSituacaoTipo != null){
					hql.append(" faturamentoTipo.id in (:idSituacaoTipo) ");
					flagIdSituacaoTipo = true;
				}

				if(idSituacaoMotivo != null){
					if(flagIdSituacaoTipo){
						hql.append(" AND ");
					}
					hql.append(" faturamentoMotivo.id in (:idSituacaoMotivo) ");
					flagIdSituacaoMotivo = true;
				}
			}

			hql.append(" group by ");
			hql.append(" gerenciaRegional.id, ");
			hql.append(" gerenciaRegional.nomeAbreviado, ");
			hql.append(" gerenciaRegional.nome, ");
			hql.append(" unidadeNegocio.id, ");
			hql.append(" unidadeNegocio.nome ");
			hql.append(" order by ");
			hql.append(" unidadeNegocio.id ");

			Query createQuery = session.createQuery(hql.toString());

			if(flagIdSituacaoTipo){
				createQuery.setParameterList("idSituacaoTipo", idSituacaoTipo);
			}
			if(flagIdSituacaoMotivo){
				createQuery.setParameterList("idSituacaoMotivo", idSituacaoMotivo);
			}

			retorno = createQuery.list();
			// pesquisa a coleção de atividades e atribui a variável "retorno"

			/*
			 * if(idSituacaoTipo != null && idSituacaoMotivo != null){
			 * String hql = " select "
			 * + " new "
			 * + ResumoFaturamentoSituacaoEspecialConsultaGerenciaRegHelper.class.getName()
			 * + " ( "
			 * + "	gerenciaRegional.id, gerenciaRegional.nomeAbreviado, gerenciaRegional.nome, "
			 * +
			 * " case when ( SUM(rfse.quantidadeImovel) is null ) then 0 else SUM(rfse.quantidadeImovel) end "
			 * + " ) "
			 * + " from "
			 * + "	ResumoFaturamentoSituacaoEspecial rfse"
			 * + "	inner join rfse.gerenciaRegional gerenciaRegional " +
			 * "	inner join rfse.localidade localidade "
			 * + " left join localidade.unidadeNegocio unidadeNegocio "
			 * + "	inner join rfse.faturamentoSituacaoTipo faturamentoTipo "
			 * + "	inner join rfse.faturamentoSituacaoMotivo faturamentoMotivo " + " where "
			 * + "	faturamentoTipo.id in (:idSituacaoTipo) AND" +
			 * "	faturamentoMotivo.id in (:idSituacaoMotivo) "
			 * +
			 * " group by gerenciaRegional.id, gerenciaRegional.nomeAbreviado, gerenciaRegional.nome, unidadeNegocio.id, unidadeNegocio.nome "
			 * + " order by gerenciaRegional.id ";
			 * retorno = session.createQuery(hql).setParameterList("idSituacaoTipo",
			 * idSituacaoTipo).setParameterList("idSituacaoMotivo",
			 * idSituacaoMotivo).list();
			 * }else if(idSituacaoTipo != null){
			 * String hql = " select "
			 * + " new "
			 * + ResumoFaturamentoSituacaoEspecialConsultaGerenciaRegHelper.class.getName()
			 * + " ( "
			 * + "	gerenciaRegional.id, gerenciaRegional.nomeAbreviado, gerenciaRegional.nome, "
			 * +
			 * " case when ( SUM(rfse.quantidadeImovel) is null ) then 0 else SUM(rfse.quantidadeImovel) end "
			 * + " ) "
			 * + " from "
			 * + "	ResumoFaturamentoSituacaoEspecial rfse"
			 * + "	inner join rfse.gerenciaRegional gerenciaRegional " +
			 * "	inner join rfse.localidade localidade "
			 * + " left join localidade.unidadeNegocio unidadeNegocio "
			 * + "	inner join rfse.faturamentoSituacaoTipo faturamentoTipo "
			 * + "	inner join rfse.faturamentoSituacaoMotivo faturamentoMotivo " + " where "
			 * + "	faturamentoTipo.id in (:idSituacaoTipo) " + " group by"
			 * +
			 * "  gerenciaRegional.id, gerenciaRegional.nomeAbreviado, gerenciaRegional.nome, unidadeNegocio.id, unidadeNegocio.nome "
			 * + " order by gerenciaRegional.id ";
			 * retorno = session.createQuery(hql).setParameterList("idSituacaoTipo",
			 * idSituacaoTipo).list();
			 * }else if(idSituacaoMotivo != null){
			 * String hql = " select "
			 * + " new "
			 * + ResumoFaturamentoSituacaoEspecialConsultaGerenciaRegHelper.class.getName()
			 * + " ( "
			 * + "	gerenciaRegional.id, gerenciaRegional.nomeAbreviado, gerenciaRegional.nome, "
			 * +
			 * " case when ( SUM(rfse.quantidadeImovel) is null ) then 0 else SUM(rfse.quantidadeImovel) end "
			 * + " ) "
			 * + " from "
			 * + "	ResumoFaturamentoSituacaoEspecial rfse"
			 * + "	inner join rfse.gerenciaRegional gerenciaRegional " +
			 * "	inner join rfse.localidade localidade "
			 * + " left join localidade.unidadeNegocio unidadeNegocio "
			 * + "	inner join rfse.faturamentoSituacaoTipo faturamentoTipo "
			 * + "	inner join rfse.faturamentoSituacaoMotivo faturamentoMotivo " + " where "
			 * + "	faturamentoMotivo.id in (:idSituacaoMotivo) "
			 * + " group by "
			 * +
			 * "  gerenciaRegional.id, gerenciaRegional.nomeAbreviado, gerenciaRegional.nome, unidadeNegocio.id, unidadeNegocio.nome "
			 * + " order by gerenciaRegional.id ";
			 * retorno = session.createQuery(hql).setParameterList("idSituacaoMotivo",
			 * idSituacaoMotivo).list();
			 * }else{
			 * String hql = " select "
			 * + " new "
			 * + ResumoFaturamentoSituacaoEspecialConsultaGerenciaRegHelper.class.getName()
			 * + " ( "
			 * + "	gerenciaRegional.id, gerenciaRegional.nomeAbreviado, gerenciaRegional.nome, "//
			 * +
			 * " case when ( SUM(rfse.quantidadeImovel) is null ) then 0 else SUM(rfse.quantidadeImovel) end "
			 * //
			 * + " ) " //
			 * + " from ResumoFaturamentoSituacaoEspecial rfse"
			 * + "	inner join rfse.gerenciaRegional gerenciaRegional " +
			 * "	inner join rfse.localidade localidade "
			 * + "	inner join rfse.faturamentoSituacaoTipo faturamentoTipo "
			 * + "	inner join rfse.faturamentoSituacaoMotivo faturamentoMotivo " + " group by "
			 * + "  gerenciaRegional.id, gerenciaRegional.nomeAbreviado, gerenciaRegional.nome "
			 * + " order by gerenciaRegional.id";
			 * retorno = session.createQuery(hql).list();
			 * }
			 */
			// erro no hibernate
		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}
		// retorna a coleção de atividades pesquisada(s)
		return retorno;
	}

	public Collection pesquisarResumoFaturamentoSituacaoEspecialConsultaFatEstimadoHelper(Integer anoMesReferencia, Integer localidade)
					throws ErroRepositorioException{

		// cria a coleção de retorno
		List retorno = null;

		// obtém a sessão
		Session session = HibernateUtil.getSession();

		try{
			// pesquisa a coleção de atividades e atribui a variável "retorno"
			String hql = " select " + " new " + ResumoFaturamentoSituacaoEspecialConsultaFatEstimadoHelper.class.getName() + " ( "
							+ "	SUM(rfsi.valorAgua) ,  SUM(rfsi.valorEsgoto) , SUM(rfsi.valorDebitos) , SUM(rfsi.valorCreditos) " + " ) "
							+ " from " + "	ResumoFaturamentoSimulacao rfsi" + "	inner join rfsi.localidade localidade" + " where "
							+ "	rfsi.anoMesReferencia = :anoMesReferencia AND" + "   rfsi.localidade.id = :localidade " + "";

			retorno = session.createQuery(hql).setInteger("anoMesReferencia", anoMesReferencia).setInteger("localidade", localidade).list();

			if(retorno == null){
				retorno = new ArrayList();
				retorno.add(new ResumoFaturamentoSituacaoEspecialConsultaFatEstimadoHelper(new BigDecimal("0.00"), new BigDecimal("0.00"),
								new BigDecimal("0.00"), new BigDecimal("0.00")));
			}

			// erro no hibernate
		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}
		// retorna a coleção de atividades pesquisada(s)
		return retorno;
	}

	public Collection pesquisarResumoFaturamentoSituacaoEspecialConsultaQtLigacoesHelper(Integer anoMesReferencia, Integer localidade)
					throws ErroRepositorioException{

		// cria a coleção de retorno
		List retorno = null;

		// obtém a sessão
		Session session = HibernateUtil.getSession();

		try{// Comentado por Eduardo Henrique para execução do relatório
			// pesquisa a coleção de atividades e atribui a variável "retorno"

			String hql = " select " //
							+ " new " + ResumoFaturamentoSituacaoEspecialConsultaQtLigacoesHelper.class.getName()
							+ " ( "
							+ "	case when (SUM(rle.quantidadeLigacoes) is null) then 0 else SUM(rle.quantidadeLigacoes) end" //
							+ " ) " //
							+ " from ResumoLigacoesEconomia rle" //
							+ "	inner join rle.localidade localidade" //
							+ " where " //
							+ " rle.anoMesReferencia = :anoMesReferencia AND" //
							+ " localidade.id = :localidade ";
			retorno = session.createQuery(hql).setInteger("anoMesReferencia", anoMesReferencia).setInteger("localidade", localidade).list();

			if(retorno == null){
				retorno = new ArrayList<ResumoFaturamentoSituacaoEspecialConsultaQtLigacoesHelper>();
				retorno.add(new ResumoFaturamentoSituacaoEspecialConsultaQtLigacoesHelper(new Integer("0")));
			}

			// erro no hibernate
		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}
		// retorna a coleção de atividades pesquisada(s)
		return retorno;
	}

	/**
	 * Método que insere o ResumoFaturamentoSituacaoEspecial em batch
	 * 
	 * @author Thiago Toscano
	 * @date 15/05/2006
	 * @param listResumoFaturamentoSituacaoEspecialHelper
	 * @throws ErroRepositorioException
	 */
	public void inserirResumoFaturamentoSituacaoEspecial(List<ResumoFaturamentoSituacaoEspecial> list) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();

		if(list != null && !list.isEmpty()){
			Iterator it = list.iterator();
			int i = 1;
			try{
				while(it.hasNext()){

					Object obj = it.next();

					session.save(obj);

					if(i % 50 == 0 || !it.hasNext()){
						// 20, same as the JDBC batch size
						// flush a batch of inserts and release memory:
						session.flush();
						session.clear();
					}
					i++;

				}
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				HibernateUtil.closeSession(session);
			}
		}
	}

	/**
	 * Método que exclui todos os ResumoFaturamentoSituacaoEspecial
	 * [CU0341] - Gerar Resumo de Situacao Especial de Faturamento
	 * 
	 * @author Thiago Toscano
	 * @date 15/05/2006
	 * @throws ErroRepositorioException
	 */
	public void excluirTodosResumoFaturamentoSituacaoEspecial(Integer idLocalidade) throws ErroRepositorioException{

		// obtém a sessão
		Session session = HibernateUtil.getSession();

		try{
			// pesquisa a coleção de atividades e atribui a variável "retorno"

			session.createQuery(
							"delete gcom.faturamento.ResumoFaturamentoSituacaoEspecial resumoFaturamentoSituacao where resumoFaturamentoSituacao.localidade.id = :idLocalidade")
							.setInteger("idLocalidade", idLocalidade).executeUpdate();

			// erro no hibernate
		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

	}

	/**
	 * Este caso de uso permite consultar o resumo de análise de faturamento, com a opção de
	 * impressão da
	 * consulta.
	 * Dependendo da opção de totalização sempre é gerado o relatório, sem a feração da consulta.
	 * [UC0305] Consultar Análise do Faturamento
	 * consultarResumoAnaliseFaturamento
	 * 
	 * @author Fernanda Paiva
	 * @date 31/05/2006
	 * @author Luciano Galvao - Correção da query e inclusão da unidade de negócio
	 * @date 18/07/2012
	 * @param dadosFiltroHelper
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List consultarResumoAnaliseFaturamento(InformarDadosGeracaoRelatorioConsultaHelper dadosFiltroHelper)
					throws ErroRepositorioException{

		// Cria a coleção de retorno
		int opcaoTotalizacao = dadosFiltroHelper.getOpcaoTotalizacao();
		List retorno = null;

		// Obtém a sessão
		Session session = HibernateUtil.getSession();

		try{

			switch(opcaoTotalizacao){
				case ConstantesSistema.CODIGO_ESTADO:
				case ConstantesSistema.CODIGO_ESTADO_GRUPO_FATURAMENTO:
				case ConstantesSistema.CODIGO_ESTADO_GERENCIA_REGIONAL:
				case ConstantesSistema.CODIGO_ESTADO_UNIDADE_NEGOCIO:
				case ConstantesSistema.CODIGO_GRUPO_FATURAMENTO:
				case ConstantesSistema.CODIGO_GERENCIA_REGIONAL:
				case ConstantesSistema.CODIGO_GERENCIA_REGIONAL_ELO_POLO:
				case ConstantesSistema.CODIGO_UNIDADE_NEGOCIO:
				case ConstantesSistema.CODIGO_UNIDADE_NEGOCIO_ELO_POLO:
				case ConstantesSistema.CODIGO_ELO_POLO:
				case ConstantesSistema.CODIGO_ELO_POLO_LOCALIDADE:
				case ConstantesSistema.CODIGO_LOCALIDADE:
				case ConstantesSistema.CODIGO_LOCALIDADE_SETOR_COMERCIAL:
				case ConstantesSistema.CODIGO_SETOR_COMERCIAL:
				case ConstantesSistema.CODIGO_SETOR_COMERCIAL_QUADRA:
				case ConstantesSistema.CODIGO_QUADRA:
					retorno = consultarAnaliseFaturamentoComAgrupamentoUnico(session, dadosFiltroHelper);
					break;
				case ConstantesSistema.CODIGO_ESTADO_ELO_POLO:
					retorno = consultarAnaliseFaturamentoAgrupadoPorGerenciaRegional(session, dadosFiltroHelper, Boolean.FALSE);
					break;
				case ConstantesSistema.CODIGO_ESTADO_LOCALIDADE:
					retorno = consultarAnaliseFaturamentoAgrupadoPorGerenciaRegional(session, dadosFiltroHelper, Boolean.FALSE);
					break;
				case ConstantesSistema.CODIGO_GERENCIA_REGIONAL_LOCALIDADE:
				case ConstantesSistema.CODIGO_UNIDADE_NEGOCIO_LOCALIDADE:
					retorno = consultarAnaliseFaturamentoAgrupadoPorEloPolo(session, dadosFiltroHelper);
					break;
				case ConstantesSistema.CODIGO_ELO_POLO_SETOR_COMERCIAL:
					retorno = consultarAnaliseFaturamentoAgrupadoPorLocalidade(session, dadosFiltroHelper);
					break;
				case ConstantesSistema.CODIGO_LOCALIDADE_QUADRA:
					retorno = consultarAnaliseFaturamentoAgrupadoPorSetorComercial(session, dadosFiltroHelper);
					break;
			}

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
	 * Realiza a consulta de dados para o Resumo de Análise de Faturamento para os tipos de
	 * totalização "Estado por Elo Polo" e "Estado por Localidade", que utilizam a hierarquia:
	 * Gerência Regional
	 * Unidade de Negócio
	 * Elo Polo
	 * Localidade (válido apenas para o tipo "Estado por Localidade")
	 * 
	 * @author Luciano Galvao
	 * @date 24/07/2012
	 * @param session
	 * @param dadosFiltroHelper
	 * @return
	 */
	private List consultarAnaliseFaturamentoAgrupadoPorGerenciaRegional(Session session,
					InformarDadosGeracaoRelatorioConsultaHelper dadosFiltroHelper, boolean expandirLocalidade){

		GeradorSqlResumoAnaliseFaturamento geradorSqlRelatorio = new GeradorSqlResumoAnaliseFaturamento(dadosFiltroHelper);
		List<String> comandosSql = geradorSqlRelatorio.getComandosSQLResumoAnaliseFaturamento();
		List registros = new ArrayList<ResumoAnaliseFaturamentoHelper>();
		List resultado = new ArrayList<ResumoAnaliseFaturamentoHelper>();
		List<Object[]> colecaoGerenciaRegional = null;
		List<Object[]> colecaoUnidadeNegocio = null;
		List<Object[]> colecaoEloPolo = null;
		List<Object[]> colecaoLocalidade = null;

		ResumoAnaliseFaturamentoHelper totalizador = new ResumoAnaliseFaturamentoHelper(dadosFiltroHelper.getOpcaoTotalizacao());

		if(!Util.isVazioOrNulo(comandosSql)){
			String sql = comandosSql.get(0);

			sql = geradorSqlRelatorio.adicionarCondicionalRestricao(sql, null, null);

			// Executa a consulta de gerências regionais - 1º Nível do Relatório
			colecaoGerenciaRegional = executarSQLAnaliseFaturamentoAtribuindoCamposScalar(session, sql, dadosFiltroHelper);

			if(!Util.isVazioOrNulo(colecaoGerenciaRegional)){
				for(Object[] gerenciaRegional : colecaoGerenciaRegional){

					ResumoAnaliseFaturamentoHelper objHelper = construirResumoAnaliseFaturamentoHelper(gerenciaRegional, 1);

					// Acumula os valores no registro totalizador
					totalizador.acumularValores(objHelper);

					// Adiciona uma gerência regional à lista de resultados - 1º Nível do Relatório
					registros.add(objHelper);

					// Adiciona a gerência regional como uma restrição para a consulta de unidades
					// de negócio
					sql = geradorSqlRelatorio.adicionarCondicionalRestricao(comandosSql.get(1),
									RestricaoTipoEnum.RESTRICAO_GERENCIA_REGIONAL, (Integer) gerenciaRegional[0]);

					// Para cada gerência regional, executa a consulta de unidades de negócio - 2º
					// Nível do Relatório
					colecaoUnidadeNegocio = executarSQLAnaliseFaturamentoAtribuindoCamposScalar(session, sql, dadosFiltroHelper);

					if(!Util.isVazioOrNulo(colecaoUnidadeNegocio)){
						for(Object[] unidadeNegocio : colecaoUnidadeNegocio){

							// Adiciona uma unidade de negócio à lista de resultados - 2º Nível do
							// Relatório
							if(expandirLocalidade){

								registros.add(construirResumoAnaliseFaturamentoHelper(unidadeNegocio, 2));
							}

							// Adiciona a unidade de negócio como uma restrição para a consulta de
							// elo polos
							sql = geradorSqlRelatorio.adicionarCondicionalRestricao(comandosSql.get(2),
											RestricaoTipoEnum.RESTRICAO_UNIDADE_NEGOCIO, (Integer) unidadeNegocio[0]);

							// Para cada unidade de negócio, executa a consulta de elo polos - 3º
							// Nível do Relatório
							colecaoEloPolo = executarSQLAnaliseFaturamentoAtribuindoCamposScalar(session, sql, dadosFiltroHelper);

							for(Object[] eloPolo : colecaoEloPolo){
								// Adiciona um elo polo à lista de resultados - 3º Nível do
								// Relatório
								registros.add(construirResumoAnaliseFaturamentoHelper(eloPolo, 3));

								// Se foi solicitado a expansão até o 4º nível (localidade)
								if(expandirLocalidade){

									// Adiciona o elo polo como uma restrição para a consulta de
									// localidades
									sql = geradorSqlRelatorio.adicionarCondicionalRestricao(comandosSql.get(3),
													RestricaoTipoEnum.RESTRICAO_ELO_POLO, (Integer) eloPolo[0]);

									// Para cada elo polo, executa a consulta de localidades - 3º
									// Nível do Relatório
									colecaoLocalidade = executarSQLAnaliseFaturamentoAtribuindoCamposScalar(session, sql, dadosFiltroHelper);

									for(Object[] localidade : colecaoLocalidade){
										// Adiciona uma localidade à lista de resultados - 4º Nível
										// do Relatório
										registros.add(construirResumoAnaliseFaturamentoHelper(localidade, 4));
									}
								}
							}
						}
					}

				}
			}
		}

		resultado.add(totalizador);
		resultado.addAll(registros);

		return resultado;
	}

	/**
	 * Realiza a consulta de dados para o Resumo de Análise de Faturamento para os tipos de
	 * totalização "Gerência Regional por Localidade" e "Unidade de Negócio por Localidade", que
	 * utilizam a hierarquia:
	 * Elo Polo
	 * Localidade
	 * 
	 * @author Luciano Galvao
	 * @date 24/07/2012
	 * @param session
	 * @param dadosFiltroHelper
	 * @return
	 */
	private List consultarAnaliseFaturamentoAgrupadoPorEloPolo(Session session,
					InformarDadosGeracaoRelatorioConsultaHelper dadosFiltroHelper){

		GeradorSqlResumoAnaliseFaturamento geradorSqlRelatorio = new GeradorSqlResumoAnaliseFaturamento(dadosFiltroHelper);
		List<String> comandosSql = geradorSqlRelatorio.getComandosSQLResumoAnaliseFaturamento();
		List registros = new ArrayList<ResumoAnaliseFaturamentoHelper>();
		List resultado = new ArrayList<ResumoAnaliseFaturamentoHelper>();
		List<Object[]> colecaoEloPolo = null;
		List<Object[]> colecaoLocalidade = null;

		ResumoAnaliseFaturamentoHelper totalizador = new ResumoAnaliseFaturamentoHelper(dadosFiltroHelper.getOpcaoTotalizacao());

		if(!Util.isVazioOrNulo(comandosSql)){
			String sql = comandosSql.get(0);
			sql = geradorSqlRelatorio.adicionarCondicionalRestricao(sql, null, null);

			// Executa a consulta de elo polos - 1º Nível do Relatório
			colecaoEloPolo = executarSQLAnaliseFaturamentoAtribuindoCamposScalar(session, sql, dadosFiltroHelper);

			for(Object[] eloPolo : colecaoEloPolo){
				ResumoAnaliseFaturamentoHelper objHelper = construirResumoAnaliseFaturamentoHelper(eloPolo, 1);
				int contador = 0;

				// Acumula os valores no registro totalizador
				totalizador.acumularValores(objHelper);

				// Adiciona um elo polo à lista de resultados - 1º Nível do Relatório
				registros.add(objHelper);

				// Adiciona o elo polo como uma restrição para a consulta de localidades
				sql = geradorSqlRelatorio.adicionarCondicionalRestricao(comandosSql.get(1), RestricaoTipoEnum.RESTRICAO_ELO_POLO,
								(Integer) eloPolo[0]);

				// Para cada elo polo, executa a consulta de localidades - 2º Nível do Relatório
				colecaoLocalidade = executarSQLAnaliseFaturamentoAtribuindoCamposScalar(session, sql, dadosFiltroHelper);

				for(Object[] localidade : colecaoLocalidade){
					// Adiciona uma localidade à lista de resultados - 2º Nível do Relatório
					registros.add(construirResumoAnaliseFaturamentoHelper(localidade, 2));
				}

				if(colecaoLocalidade.size() == ConstantesSistema.SIM.intValue()){

					List<ResumoAnaliseFaturamentoHelper> colecaoDados = registros;

					while(colecaoDados.size() > contador){

						ResumoAnaliseFaturamentoHelper localidadeD = colecaoDados.get(contador);

						if(localidadeD.getIdCampo() == eloPolo[0]){

							registros.remove(contador + 1);

						}

						contador++;
					}

				}
			}
		}

		resultado.add(totalizador);
		resultado.addAll(registros);
		return resultado;
	}

	/**
	 * Realiza a consulta de dados para o Resumo de Análise de Faturamento para o tipo de
	 * totalização "Elo por Setor Comercial". Utiliza a hierarquia:
	 * Localidade
	 * Setor Comercial
	 * 
	 * @author Luciano Galvao
	 * @date 24/07/2012
	 * @param session
	 * @param dadosFiltroHelper
	 * @return
	 */
	private List consultarAnaliseFaturamentoAgrupadoPorLocalidade(Session session,
					InformarDadosGeracaoRelatorioConsultaHelper dadosFiltroHelper){

		GeradorSqlResumoAnaliseFaturamento geradorSqlRelatorio = new GeradorSqlResumoAnaliseFaturamento(dadosFiltroHelper);
		List<String> comandosSql = geradorSqlRelatorio.getComandosSQLResumoAnaliseFaturamento();
		List registros = new ArrayList<ResumoAnaliseFaturamentoHelper>();
		List resultado = new ArrayList<ResumoAnaliseFaturamentoHelper>();
		List<Object[]> colecaoLocalidade = null;
		List<Object[]> colecaoSetorComercial = null;

		ResumoAnaliseFaturamentoHelper totalizador = new ResumoAnaliseFaturamentoHelper(dadosFiltroHelper.getOpcaoTotalizacao());

		if(!Util.isVazioOrNulo(comandosSql)){
			String sql = comandosSql.get(0);
			sql = geradorSqlRelatorio.adicionarCondicionalRestricao(sql, null, null);

			// Executa a consulta de localidades - 1º Nível do Relatório
			colecaoLocalidade = executarSQLAnaliseFaturamentoAtribuindoCamposScalar(session, sql, dadosFiltroHelper);

			for(Object[] localidade : colecaoLocalidade){

				ResumoAnaliseFaturamentoHelper objHelper = construirResumoAnaliseFaturamentoHelper(localidade, 1);

				// Acumula valores no registro totalizador
				totalizador.acumularValores(objHelper);

				// Adiciona uma localidade à lista de resultados - 1º Nível do Relatório
				registros.add(objHelper);

				// Adiciona o elo polo como uma restrição para a consulta de localidades
				sql = geradorSqlRelatorio.adicionarCondicionalRestricao(comandosSql.get(1), RestricaoTipoEnum.RESTRICAO_LOCALIDADE,
								(Integer) localidade[0]);

				// Para cada localidade, executa a consulta de setores comerciais - 2º Nível do
				// Relatório
				colecaoSetorComercial = executarSQLAnaliseFaturamentoAtribuindoCamposScalar(session, sql, dadosFiltroHelper);

				for(Object[] setorComercial : colecaoSetorComercial){

					// Adiciona um setor comercial à lista de resultados - 2º Nível do Relatório
					registros.add(construirResumoAnaliseFaturamentoHelper(setorComercial, 2));
				}
			}

		}

		resultado.add(totalizador);
		resultado.addAll(registros);

		return resultado;
	}

	/**
	 * Realiza a consulta de dados para o Resumo de Análise de Faturamento para o tipo de
	 * totalização "Localidade Por Quadra". Utiliza a hierarquia:
	 * Setor Comercial
	 * Quadra
	 * 
	 * @author Luciano Galvao
	 * @date 24/07/2012
	 * @param session
	 * @param dadosFiltroHelper
	 * @return
	 */
	private List consultarAnaliseFaturamentoAgrupadoPorSetorComercial(Session session,
					InformarDadosGeracaoRelatorioConsultaHelper dadosFiltroHelper){

		GeradorSqlResumoAnaliseFaturamento geradorSqlRelatorio = new GeradorSqlResumoAnaliseFaturamento(dadosFiltroHelper);
		List<String> comandosSql = geradorSqlRelatorio.getComandosSQLResumoAnaliseFaturamento();
		List registros = new ArrayList<ResumoAnaliseFaturamentoHelper>();
		List resultado = new ArrayList<ResumoAnaliseFaturamentoHelper>();
		List<Object[]> colecaoSetorComercial = null;
		List<Object[]> colecaoQuadra = null;

		ResumoAnaliseFaturamentoHelper totalizador = new ResumoAnaliseFaturamentoHelper(dadosFiltroHelper.getOpcaoTotalizacao());

		if(!Util.isVazioOrNulo(comandosSql)){
			String sql = comandosSql.get(0);
			sql = geradorSqlRelatorio.adicionarCondicionalRestricao(sql, null, null);

			// Executa a consulta de setores comerciais - 1º Nível do Relatório
			colecaoSetorComercial = executarSQLAnaliseFaturamentoAtribuindoCamposScalar(session, sql, dadosFiltroHelper);

			for(Object[] setorComercial : colecaoSetorComercial){

				ResumoAnaliseFaturamentoHelper objHelper = construirResumoAnaliseFaturamentoHelper(setorComercial, 1);

				// Acumula os valores no registro totalizador
				totalizador.acumularValores(objHelper);

				// Adiciona um setor comercial à lista de resultados - 1º Nível do Relatório
				registros.add(objHelper);

				// Adiciona o elo polo como uma restrição para a consulta de localidades
				sql = geradorSqlRelatorio.adicionarCondicionalRestricao(comandosSql.get(1), RestricaoTipoEnum.RESTRICAO_SETOR_COMERCIAL,
								(Integer) setorComercial[0]);

				// Para cada setor comercial, executa a consulta de quadras - 2º Nível do
				// Relatório
				colecaoQuadra = executarSQLAnaliseFaturamentoAtribuindoCamposScalar(session, sql, dadosFiltroHelper);

				for(Object[] quadra : colecaoQuadra){
					// Adiciona uma quadra à lista de resultados - 2º Nível do Relatório
					registros.add(construirResumoAnaliseFaturamentoHelper(quadra, 2));
				}
			}
		}

		resultado.add(totalizador);
		resultado.addAll(registros);

		return resultado;
	}

	/**
	 * Converte um array de objetos com o resultado da consulta para um objeto do tipo
	 * ResumoAnaliseFaturamentoHelper
	 * 
	 * @author Luciano Galvao
	 * @date 24/07/2012
	 * @param objeto
	 * @param nivel
	 * @return
	 */
	private ResumoAnaliseFaturamentoHelper construirResumoAnaliseFaturamentoHelper(Object[] objeto, int nivel){

		String tabulacao = "";
		Integer ZERO = new Integer(0);

		for(int i = 2; i <= nivel; i++){
			tabulacao = tabulacao + ESPACAMENTO;
		}

		// Captura as informações resultantes da query
		Integer idCampo = objeto[0] == null ? ZERO : (Integer) objeto[0];
		String descricaoCampo = objeto[1] == null ? "" : tabulacao + ((String) objeto[1]).trim();
		Integer quantidadeConta = objeto[2] == null ? ZERO : (Integer) objeto[2];
		Integer quantidadeEconomia = objeto[3] == null ? ZERO : (Integer) objeto[3];
		Integer volumeConsumidoAgua = objeto[4] == null ? ZERO : (Integer) objeto[4];
		BigDecimal valorFaturadoAgua = objeto[5] == null ? BigDecimal.ZERO : (BigDecimal) objeto[5];
		Integer volumeColetadoEsgoto = objeto[6] == null ? ZERO : (Integer) objeto[6];
		BigDecimal valorFaturadoEsgoto = objeto[7] == null ? BigDecimal.ZERO : (BigDecimal) objeto[7];
		BigDecimal debitosCobrados = objeto[8] == null ? BigDecimal.ZERO : (BigDecimal) objeto[8];
		BigDecimal creditosRealizados = objeto[9] == null ? BigDecimal.ZERO : (BigDecimal) objeto[9];
		// Calcula o valor total cobrado
		BigDecimal totalCobrado = valorFaturadoAgua.add(valorFaturadoEsgoto).add(debitosCobrados).subtract(creditosRealizados);
		totalCobrado = totalCobrado.setScale(2, BigDecimal.ROUND_HALF_UP);

		// Construção do objeto ResumoAnaliseFaturamentoHelper
		ResumoAnaliseFaturamentoHelper helper = new ResumoAnaliseFaturamentoHelper(idCampo, descricaoCampo, quantidadeConta,
						quantidadeEconomia, volumeConsumidoAgua, valorFaturadoAgua, volumeColetadoEsgoto, valorFaturadoEsgoto,
						debitosCobrados, creditosRealizados, totalCobrado);

		return helper;
	}

	/**
	 * Realiza a consulta do Resumo de Análise de Faturamento com a opção de totalização
	 * "Estado por Grupo de Faturamento"
	 * 
	 * @author Luciano Galvao
	 * @date 24/07/2012
	 * @param dadosFiltroHelper
	 * @return
	 */
	private List consultarAnaliseFaturamentoComAgrupamentoUnico(Session session,
					InformarDadosGeracaoRelatorioConsultaHelper dadosFiltroHelper){

		GeradorSqlResumoAnaliseFaturamento geradorSqlRelatorio = new GeradorSqlResumoAnaliseFaturamento(dadosFiltroHelper);
		List<String> comandosSql = geradorSqlRelatorio.getComandosSQLResumoAnaliseFaturamento();
		List<ResumoAnaliseFaturamentoHelper> registros = new ArrayList<ResumoAnaliseFaturamentoHelper>();
		List<ResumoAnaliseFaturamentoHelper> resultado = new ArrayList<ResumoAnaliseFaturamentoHelper>();
		List<Object[]> colecaoObjetos = null;
		ResumoAnaliseFaturamentoHelper totalizador = new ResumoAnaliseFaturamentoHelper(dadosFiltroHelper.getOpcaoTotalizacao());

		if(!Util.isVazioOrNulo(comandosSql)){
			String sql = comandosSql.get(0);
			sql = geradorSqlRelatorio.adicionarCondicionalRestricao(sql, null, null);

			colecaoObjetos = executarSQLAnaliseFaturamentoAtribuindoCamposScalar(session, sql, dadosFiltroHelper);

			for(Object[] objeto : colecaoObjetos){
				// Adiciona o objeto resultado da consulta à lista de resultados - 1º Nível do
				// Relatório
				ResumoAnaliseFaturamentoHelper objHelper = construirResumoAnaliseFaturamentoHelper(objeto, 1);
				totalizador.acumularValores(objHelper);
				registros.add(objHelper);
			}
		}

		resultado.add(totalizador);
		resultado.addAll(registros);
		return resultado;
	}

	/**
	 * Atribui os campos Scalar e executa a query do Resumo de Análise de Faturamento
	 * 
	 * @author Luciano Galvao
	 * @created 18/07/2012
	 * @param session
	 * @param sql
	 * @param dadosConsulta
	 * @return
	 */
	private List executarSQLAnaliseFaturamentoAtribuindoCamposScalar(Session session, String sql,
					InformarDadosGeracaoRelatorioConsultaHelper dadosConsulta){

		SQLQuery sqlQuery = session.createSQLQuery(sql);
		sqlQuery.addScalar("idCampo", Hibernate.INTEGER) //
						.addScalar("descCampo", Hibernate.STRING)//
						.addScalar("somatorio1", Hibernate.INTEGER)//
						.addScalar("somatorio2", Hibernate.INTEGER)//
						.addScalar("somatorio3", Hibernate.INTEGER)//
						.addScalar("somatorio4", Hibernate.BIG_DECIMAL)//
						.addScalar("somatorio5", Hibernate.INTEGER)//
						.addScalar("somatorio6", Hibernate.BIG_DECIMAL)//
						.addScalar("somatorio7", Hibernate.BIG_DECIMAL)//
						.addScalar("somatorio8", Hibernate.BIG_DECIMAL);//

		return sqlQuery.list(); // LISTAR
	}

	/**
	 * Este caso de uso permite consultar o resumo do análise do faturamento, com a opção de
	 * impressão da consulta.
	 * Dependendo da opção de totalização sempre é gerado o relatório, sem a feração da consulta.
	 * [UC0305] Consultar Análise Faturamento
	 * consultarResumoAnaliseFaturamento
	 * 
	 * @author Fernanda Paiva
	 * @date 31/05/2006
	 * @param informarDadosGeracaoRelatorioConsultaHelper
	 * @return
	 * @throws ErroRepositorioException
	 */
	@Deprecated
	public String criarCondicionaisResumoAnaliseFaturamento(
					InformarDadosGeracaoRelatorioConsultaHelper informarDadosGeracaoRelatorioConsultaHelper, String nomeColunaTabela){

		String sql = " ";
		// boolean existeWhere = false;
		// A partir daqui sera montanda a parte dos condicionais da query
		// estas condicionais serão usadas se necessarias, o q determina seus usos
		// são os parametros que veem carregados no objeto
		// InformarDadosGeracaoRelatorioConsultaHelper
		// que é recebido do caso de uso [UC0304] Informar Dados para Geração de Relatorio ou
		// COnsulta
		if(informarDadosGeracaoRelatorioConsultaHelper != null){

			// Inicio Parametros simples
			if(informarDadosGeracaoRelatorioConsultaHelper.getAnoMesReferencia() != null
							&& !informarDadosGeracaoRelatorioConsultaHelper.getAnoMesReferencia().toString().equals("")){
				sql = sql + "re." + nomeColunaTabela + "_amreferencia = "
								+ informarDadosGeracaoRelatorioConsultaHelper.getAnoMesReferencia() + " and ";
				// existeWhere = true;
			}

			if(informarDadosGeracaoRelatorioConsultaHelper.getTipoAnaliseFaturamento() == 2){
				sql = sql + " re.rfts_icsimulacao = 2 and ";
			}

			if(informarDadosGeracaoRelatorioConsultaHelper.getTipoAnaliseFaturamento() == 1){
				sql = sql + " re.rfts_icsimulacao = 1 and ";
			}

			if(informarDadosGeracaoRelatorioConsultaHelper.getFaturamentoGrupo() != null
							&& informarDadosGeracaoRelatorioConsultaHelper.getFaturamentoGrupo().getId() != null){
				sql = sql + " re.ftgr_id = " + informarDadosGeracaoRelatorioConsultaHelper.getFaturamentoGrupo().getId() + " and ";
				// existeWhere = true;
			}

			if(informarDadosGeracaoRelatorioConsultaHelper.getGerenciaRegional() != null
							&& informarDadosGeracaoRelatorioConsultaHelper.getGerenciaRegional().getId() != null){
				sql = sql + " re.greg_id = " + informarDadosGeracaoRelatorioConsultaHelper.getGerenciaRegional().getId() + " and ";
				// existeWhere = true;
			}

			if(informarDadosGeracaoRelatorioConsultaHelper.getEloPolo() != null
							&& informarDadosGeracaoRelatorioConsultaHelper.getEloPolo().getId() != null){
				sql = sql + " eloPolo.loca_id = " + informarDadosGeracaoRelatorioConsultaHelper.getEloPolo().getId() + " and ";
				// existeWhere = true;
			}

			if(informarDadosGeracaoRelatorioConsultaHelper.getLocalidade() != null
							&& informarDadosGeracaoRelatorioConsultaHelper.getLocalidade().getId() != null){
				sql = sql + " re.loca_id = " + informarDadosGeracaoRelatorioConsultaHelper.getLocalidade().getId() + " and ";
				// existeWhere = true;
			}

			if(informarDadosGeracaoRelatorioConsultaHelper.getSetorComercial() != null
							&& informarDadosGeracaoRelatorioConsultaHelper.getSetorComercial().getId() != null){
				sql = sql + " re.stcm_id = " + informarDadosGeracaoRelatorioConsultaHelper.getSetorComercial().getId() + " and ";
				// existeWhere = true;
			}

			if(informarDadosGeracaoRelatorioConsultaHelper.getQuadra() != null
							&& informarDadosGeracaoRelatorioConsultaHelper.getQuadra().getId() != null){
				sql = sql + " re.qdra_id = " + informarDadosGeracaoRelatorioConsultaHelper.getQuadra().getId() + " and ";
				// existeWhere = true;
			}

			// Inicio de parametros por colecão
			// sera lida a colecao e montado um IN() a partis dos id extraidos de cada objeto da
			// colecao.
			if(informarDadosGeracaoRelatorioConsultaHelper.getColecaoImovelPerfil() != null
							&& !informarDadosGeracaoRelatorioConsultaHelper.getColecaoImovelPerfil().isEmpty()){

				Iterator iterator = informarDadosGeracaoRelatorioConsultaHelper.getColecaoImovelPerfil().iterator();
				ImovelPerfil imovelPerfil = null;

				sql = sql + " re.iper_id in (";
				while(iterator.hasNext()){
					imovelPerfil = (ImovelPerfil) iterator.next();
					sql = sql + imovelPerfil.getId() + ",";
				}
				sql = Util.formatarHQL(sql, 1);
				sql = sql + ") and ";
				// existeWhere = true;
			}

			if(informarDadosGeracaoRelatorioConsultaHelper.getColecaoLigacaoAguaSituacao() != null
							&& !informarDadosGeracaoRelatorioConsultaHelper.getColecaoLigacaoAguaSituacao().isEmpty()){

				Iterator iterator = informarDadosGeracaoRelatorioConsultaHelper.getColecaoLigacaoAguaSituacao().iterator();
				LigacaoAguaSituacao ligacaoAguaSituacao = null;

				sql = sql + " re.last_id in (";
				while(iterator.hasNext()){
					ligacaoAguaSituacao = (LigacaoAguaSituacao) iterator.next();
					sql = sql + ligacaoAguaSituacao.getId() + ",";
				}
				sql = Util.formatarHQL(sql, 1);
				sql = sql + ") and ";
				// existeWhere = true;
			}

			if(informarDadosGeracaoRelatorioConsultaHelper.getColecaoLigacaoEsgotoSituacao() != null
							&& !informarDadosGeracaoRelatorioConsultaHelper.getColecaoLigacaoEsgotoSituacao().isEmpty()){

				Iterator iterator = informarDadosGeracaoRelatorioConsultaHelper.getColecaoLigacaoEsgotoSituacao().iterator();
				LigacaoEsgotoSituacao ligacaoEsgotoSituacao = null;

				sql = sql + " re.lest_id in (";
				while(iterator.hasNext()){
					ligacaoEsgotoSituacao = (LigacaoEsgotoSituacao) iterator.next();
					sql = sql + ligacaoEsgotoSituacao.getId() + ",";
				}
				sql = Util.formatarHQL(sql, 1);
				sql = sql + ") and ";
				// existeWhere = true;
			}

			if(informarDadosGeracaoRelatorioConsultaHelper.getColecaoCategoria() != null
							&& !informarDadosGeracaoRelatorioConsultaHelper.getColecaoCategoria().isEmpty()){

				Iterator iterator = informarDadosGeracaoRelatorioConsultaHelper.getColecaoCategoria().iterator();
				Categoria categoria = null;

				sql = sql + " re.catg_id in (";
				while(iterator.hasNext()){
					categoria = (Categoria) iterator.next();
					sql = sql + categoria.getId() + ",";
				}
				sql = Util.formatarHQL(sql, 1);
				sql = sql + ") and ";
				// existeWhere = true;
			}

			if(informarDadosGeracaoRelatorioConsultaHelper.getColecaoEsferaPoder() != null
							&& !informarDadosGeracaoRelatorioConsultaHelper.getColecaoEsferaPoder().isEmpty()){

				Iterator iterator = informarDadosGeracaoRelatorioConsultaHelper.getColecaoEsferaPoder().iterator();
				EsferaPoder esferaPoder = null;

				sql = sql + " re.epod_id in (";
				while(iterator.hasNext()){
					esferaPoder = (EsferaPoder) iterator.next();
					sql = sql + esferaPoder.getId() + ",";
				}
				sql = Util.formatarHQL(sql, 1);
				sql = sql + ") and ";
				// existeWhere = true;
			}
		}

		// if(existeWhere){
		// sql = " where " + sql;
		// }
		// retira o " and " q fica sobrando no final da query
		sql = Util.formatarHQL(sql, 4);

		return sql;
	}

	/**
	 * Método que retona uma lista de objeto xxxxx que representa o resumo do
	 * faturamento
	 * 
	 * @author Marcio Roberto
	 * @date 12/05/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List getResumoFaturamentoAguaEsgoto(int idSetor, int anoMes, int indice, int qtRegistros) throws ErroRepositorioException{

		List retorno = null;

		Session session = HibernateUtil.getSession();
		try{
			String hql = "select "
							+ "  conta.id, "// 0
							+ "  imovel.id, "// 1
							+ "  conta.localidade.gerenciaRegional.id, "// 2
							+ "  conta.localidade.unidadeNegocio.id, "// 3
							+ "  conta.localidade.localidade.id, "// 4 elo
							+ "  conta.localidade.id, "// 5 localidade
							+ "  quadra.setorComercial.id, "// 6
							+ "  rota.id, "// 7
							+ "  quadra.id, "// 8
							+ "  imovel.setorComercial.codigo, "// 9
							+ "  quadra.numeroQuadra, "// 10
							+ "  imovel.imovelPerfil.id, "// 11
							+ "  conta.ligacaoAguaSituacao.id, "// 12
							+ "  conta.ligacaoEsgotoSituacao.id, "// 13
							+ "  case when ( "
							+ "    imovel.ligacaoAgua.ligacaoAguaPerfil.id is null ) then "
							+ "    0 "
							+ "  else "
							+ "    imovel.ligacaoAgua.ligacaoAguaPerfil.id "
							+ "  end, "// 14
							+ "  case when ( "
							+ "    imovel.ligacaoEsgoto.ligacaoEsgotoPerfil.id is null ) then "
							+ "    0 "
							+ "  else "
							+ "    imovel.ligacaoEsgoto.ligacaoEsgotoPerfil.id "
							+ "  end, "// 15
							+ "  conta.consumoAgua, "// 16
							+ "  conta.consumoEsgoto, "// 17
							+ "  conta.valorAgua, "// 18
							+ "  conta.valorEsgoto, "// 19
							+ "  conta.referencia, "// 20
							+ "  conta.debitoCreditoSituacaoAtual.id, "// 21
							+ "  rota.empresa.id, "// 22
							+ "  imovel.quantidadeEconomias, "// 23
							+ "  conta.consumoTarifa.id, "// 24
							+ "  rota.faturamentoGrupo.id "// 25
							+ " from " + "  gcom.cadastro.sistemaparametro.SistemaParametro sistemaParametro, "
							+ "  gcom.faturamento.conta.Conta conta " + "  inner join conta.imovel imovel "
							+ "  inner join imovel.quadra quadra " + "  inner join imovel.rota rota "
							+ "  inner join imovel.setorComercial setorComercial " + "  left join imovel.ligacaoAgua ligacaoAgua "
							+ "  left join imovel.ligacaoEsgoto ligacaoEsgoto " + " where "
							+ "  sistemaParametro.anoMesFaturamento = conta.referencia and "
							+ "  conta.referencia = :anoMesReferencia and "
							+ "  (conta.debitoCreditoSituacaoAtual.id = 0 or conta.debitoCreditoSituacaoAnterior.id = 0) and "
							+ "  quadra.setorComercial.id = :idSetor " + " order by " + " conta.id ";

			retorno = session.createQuery(hql).setInteger("anoMesReferencia", anoMes).setInteger("idSetor", idSetor).setFirstResult(indice)
							.setMaxResults(qtRegistros).list();
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * Método que retona uma lista de objeto xxxxx que representa o resumo do
	 * faturamento (Débitos Cobrados)
	 * 
	 * @author Marcio Roberto
	 * @date 17/05/2007
	 * @return
	 * @throws ErroRepositorioException
	 */

	public List getPesquisaDebitoCobrado(int idConta, int idImovel, int mesAno) throws ErroRepositorioException{

		List retorno = null;

		Session session = HibernateUtil.getSession();
		try{
			String sql = "select "

							// debito cobrado
							+ "  financiamentotipo.fntp_id as financiamentotipo,  " // 0
							+ "  1 as documentotipo, " // 1
							+ "  lancamentoitemcontabil.lict_id as lancamentoitemcontabil, " // 2
							+ "  sum(debitocobrado.dbcb_vlprestacao) as valordebitos, " // 3
							+ "  count(debitocobrado.dbcb_id) as qtddocumentos " // 4
							+ " from "
							+ " debito_cobrado debitocobrado "
							+ " inner join lancamento_item_contabil lancamentoitemcontabil on debitocobrado.lict_id = lancamentoitemcontabil.lict_id "
							+ " inner join conta conta on debitocobrado.cnta_id = conta.cnta_id "
							+ " inner join financiamento_tipo financiamentotipo on debitocobrado.fntp_id = financiamentotipo.fntp_id "
							+ " where " + " conta.cnta_id = :idConta " + " and (conta.dcst_idatual = 0 or conta.dcst_idanterior = 0) "
							+ " and conta.cnta_vldebitos > 0 " + " group by financiamentotipo, documentotipo, lancamentoitemcontabil "
							// + " union select "
							// //guias pagamento
							// + "  financiamentotipo.fntp_id as financiamentotipo,  "
							// + "  7 as documentotipo, "
							// + "  lancamentoitemcontabil.lict_id as lancamentoitemcontabil, "
							// + "  sum(guiapagamento.gpag_vldebito) as valordebitos, "
							// + "  count(guiapagamento.gpag_id) as qtddocumentos"
							// + " from "
							// + " guia_pagamento as guiapagamento "
							// +
							// " inner join lancamento_item_contabil lancamentoitemcontabil on guiapagamento.lict_id = lancamentoitemcontabil.lict_id "
							// +
							// " inner join imovel imovel on guiapagamento.imov_id = imovel.imov_id "
							// +
							// " inner join financiamento_tipo financiamentotipo on guiapagamento.fntp_id = financiamentotipo.fntp_id "
							// + " where "
							// + " imovel.imov_id = :idImovel "
							// +
							// " and (guiapagamento.dcst_idatual = 0 or guiapagamento.dcst_idanterior = 0) "
							// + " and guiapagamento.gpag_amreferenciacontabil = :mesAno "
							// +
							// " group by financiamentotipo, documentotipo, lancamentoitemcontabil "
							+ " order by financiamentotipo, documentotipo, lancamentoitemcontabil ";

			retorno = session.createSQLQuery(sql).addScalar("financiamentotipo", Hibernate.INTEGER)
							.addScalar("documentotipo", Hibernate.INTEGER).addScalar("lancamentoitemcontabil", Hibernate.INTEGER)
							.addScalar("valordebitos", Hibernate.BIG_DECIMAL).addScalar("qtddocumentos", Hibernate.INTEGER)
							.setInteger("idConta", idConta)
							// .setInteger("idImovel", idImovel)
							// .setInteger("mesAno", mesAno)
							.list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * Método que retona uma lista de objeto xxxxx que representa o resumo do
	 * faturamento (Créditos Realizados)
	 * 
	 * @author Marcio Roberto
	 * @date 17/05/2007
	 * @return
	 * @throws ErroRepositorioException
	 */

	public List getPesquisaCreditoRealizado(int idSetor, int mesAno) throws ErroRepositorioException{

		List retorno = null;

		Session session = HibernateUtil.getSession();
		try{
			String hql = " select "
							+ "  creditorealizado.creditoOrigem.id, " // 0
							+ "  creditorealizado.lancamentoItemContabil.id, " // 1
							+ "  sum(creditorealizado.valorCredito) as valorcredito, " // 2
							+ "  count(creditorealizado.creditoOrigem.id), " // 3
							+ "  creditorealizado.creditoTipo.id " // 4
							+ " from "
							+ "  gcom.faturamento.credito.CreditoRealizado creditorealizado "
							+ " where creditorealizado.conta.id in(select conta.id "
							+ "                                    from gcom.faturamento.conta.Conta conta "
							+ "                                    inner join conta.imovel imovel "
							+ "                                    inner join imovel.quadra quadra "
							+ "                                    where conta.referencia = :mesAno and "
							+ "                                    (conta.debitoCreditoSituacaoAtual.id = 0 or conta.debitoCreditoSituacaoAnterior.id = 0) and "
							+ "                                    quadra.setorComercial.id = :idSetor) "
							+ " group by creditorealizado.creditoOrigem.id, creditorealizado.lancamentoItemContabil.id, creditorealizado.creditoTipo.id "
							+ " order by creditorealizado.creditoOrigem.id, creditorealizado.lancamentoItemContabil.id, creditorealizado.creditoTipo.id ";
			retorno = session.createQuery(hql).setInteger("mesAno", mesAno).setInteger("idSetor", idSetor).list();
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * pesquisarEsferaPoderClienteResponsavelImovel
	 * 
	 * @author Marcio Roberto
	 * @date 05/06/2007
	 * @param id
	 *            do imovel a ser pesquisado
	 * @return Esfera de poder do cliente responsavel pelo imovel
	 * @exception ErroRepositorioException
	 */
	public Integer pesquisarEsferaPoderClienteResponsavelImovel(Integer idImovel) throws ErroRepositorioException{

		Object retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "select " + "  case when ep.id is not null then " + "    ep.id " + "  else  " + "    0 " + "  end " + "from "
							+ "  gcom.cadastro.imovel.Imovel i " + "  inner join i.clienteImoveis ci " + "  inner join ci.cliente c "
							+ "  inner join c.clienteTipo ct " + "  inner join ct.esferaPoder ep "
							+ "  inner join ci.clienteRelacaoTipo crt " + "where "
							+ "  crt.id = :clienteResponsavel and ci.dataFimRelacao is null and i.id =:idImovel";

			retorno = session.createQuery(consulta).setInteger("clienteResponsavel", ClienteRelacaoTipo.RESPONSAVEL)
							.setInteger("idImovel", idImovel).uniqueResult();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		if(retorno != null){
			return (Integer) retorno;
		}else{
			return 0;
		}
	}

	/**
	 * pesquisarTipoClienteClienteResponsavelImovel
	 * 
	 * @author Marcio Roberto
	 * @date 05/06/2007
	 * @param imovel
	 *            a ser pesquisado
	 * @return Tipo de cliente do cliente responsavel pelo imovel
	 * @exception ErroRepositorioException
	 */
	public Integer pesquisarTipoClienteClienteResponsavelImovel(Integer idImovel) throws ErroRepositorioException{

		Object retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "select " + "  case when ct.id is not null then " + "    ct.id " + "  else  " + "    0 " + "  end " + "from "
							+ "  gcom.cadastro.imovel.Imovel i " + "  inner join i.clienteImoveis ci " + "  inner join ci.cliente c "
							+ "  inner join c.clienteTipo ct " + "  inner join ci.clienteRelacaoTipo crt " + "where "
							+ "  crt.id = :clienteResponsavel and ci.dataFimRelacao is null and i.id =:idImovel";

			retorno = session.createQuery(consulta).setInteger("clienteResponsavel", ClienteRelacaoTipo.RESPONSAVEL)
							.setInteger("idImovel", idImovel).uniqueResult();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		if(retorno != null){
			return (Integer) retorno;
		}else{
			return 0;
		}
	}

	/**
	 * Método que retona uma lista de objeto xxxxx que representa o resumo do
	 * Re-faturamento
	 * 
	 * @author Marcio Roberto
	 * @date 12/05/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List getResumoReFaturamento(int idSetor, int anoMes, int indice, int qtRegistros) throws ErroRepositorioException{

		List retorno = null;

		Session session = HibernateUtil.getSession();
		try{
			String hql = "select "
							+ "  conta.id, "// 0
							+ "  imovel.id, "// 1
							+ "  conta.localidade.gerenciaRegional.id, "// 2
							+ "  conta.localidade.unidadeNegocio.id, "// 3
							+ "  conta.localidade.localidade.id, "// 4 elo
							+ "  conta.localidade.id, "// 5 localidade
							+ "  quadra.setorComercial.id, "// 6
							+ "  rota.id, "// 7
							+ "  quadra.id, "// 8
							+ "  imovel.setorComercial.codigo, "// 9
							+ "  quadra.numeroQuadra, "// 10
							+ "  imovel.imovelPerfil.id, "// 11
							+ "  conta.ligacaoAguaSituacao.id, "// 12
							+ "  conta.ligacaoEsgotoSituacao.id, "// 13
							+ "  case when ( "
							+ "    imovel.ligacaoAgua.ligacaoAguaPerfil.id is null ) then "
							+ "    0 "
							+ "  else "
							+ "    imovel.ligacaoAgua.ligacaoAguaPerfil.id "
							+ "  end, "// 14
							+ "  case when ( "
							+ "    imovel.ligacaoEsgoto.ligacaoEsgotoPerfil.id is null ) then "
							+ "    0 "
							+ "  else "
							+ "    imovel.ligacaoEsgoto.ligacaoEsgotoPerfil.id "
							+ "  end, "// 15
							+ "  conta.consumoAgua, "// 16
							+ "  conta.consumoEsgoto, "// 17
							+ "  conta.valorAgua, "// 18
							+ "  conta.valorEsgoto, "// 19
							+ "  conta.referencia, "// 20
							+ "  conta.debitoCreditoSituacaoAtual "// 21
							+ " from " + "  gcom.cadastro.sistemaparametro.SistemaParametro sistemaParametro, "
							+ "  gcom.faturamento.conta.Conta conta " + "  inner join conta.imovel imovel "
							+ "  inner join imovel.quadra quadra " + "  inner join imovel.rota rota "
							+ "  inner join imovel.setorComercial setorComercial "
							+ "  inner join conta.debitoCreditoSituacaoAtual debitoCreditoAtual"
							+ "  left join imovel.ligacaoAgua ligacaoAgua " + "  left join imovel.ligacaoEsgoto ligacaoEsgoto " + " where "
							+ "  sistemaParametro.anoMesFaturamento = conta.referencia and "
							+ "  conta.referencia = :anoMesReferencia and " + "  (conta.dcst_idatual in (1,2,3)) and "
							+ "  quadra.setorComercial.id = :idSetor " + " order by conta.debitoCreditoSituacaoAtual ";
			retorno = session.createQuery(hql).setInteger("anoMesReferencia", anoMes).setInteger("idSetor", idSetor).setFirstResult(indice)
							.setMaxResults(qtRegistros).list();
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	// debito a cobrar, outros, impostos, creditos.
	public List getContasResumoFaturamentoAguaEsgoto(int idSetor, int anoMes, int indice, int qtRegistros) throws ErroRepositorioException{

		List retorno = null;

		Session session = HibernateUtil.getSession();
		try{
			String hql = "select "
							+ "  conta.id, "// 0
							+ "  imovel.id, "// 1
							+ "  conta.localidade.gerenciaRegional.id, "// 2
							+ "  conta.localidade.unidadeNegocio.id, "// 3
							+ "  conta.localidade.localidade.id, "// 4 elo
							+ "  conta.localidade.id, "// 5 localidade
							+ "  quadra.setorComercial.id, "// 6
							+ "  rota.id, "// 7
							+ "  quadra.id, "// 8
							+ "  imovel.setorComercial.codigo, "// 9
							+ "  quadra.numeroQuadra, "// 10
							+ "  imovel.imovelPerfil.id, "// 11
							+ "  conta.ligacaoAguaSituacao.id, "// 12
							+ "  conta.ligacaoEsgotoSituacao.id, "// 13
							+ "  case when ( "
							+ "    imovel.ligacaoAgua.ligacaoAguaPerfil.id is null ) then "
							+ "    0 "
							+ "  else "
							+ "    imovel.ligacaoAgua.ligacaoAguaPerfil.id "
							+ "  end, "// 14
							+ "  case when ( "
							+ "    imovel.ligacaoEsgoto.ligacaoEsgotoPerfil.id is null ) then "
							+ "    0 "
							+ "  else "
							+ "    imovel.ligacaoEsgoto.ligacaoEsgotoPerfil.id "
							+ "  end, "// 15
							+ "  conta.consumoAgua, "// 16
							+ "  conta.consumoEsgoto, "// 17
							+ "  conta.valorAgua, "// 18
							+ "  conta.valorEsgoto, "// 19
							+ "  conta.referencia, "// 20
							+ "  conta.debitoCreditoSituacaoAtual.id, "// 21
							+ "  rota.empresa.id, "// 22
							+ "  conta.consumoTarifa.id, "// 23
							+ "  rota.faturamentoGrupo.id "// 24
							+ " from " + "  gcom.cadastro.sistemaparametro.SistemaParametro sistemaParametro, "
							+ "  gcom.faturamento.conta.Conta conta " + "  inner join conta.imovel imovel "
							+ "  inner join imovel.quadra quadra " + "  inner join imovel.rota rota "
							+ "  inner join imovel.setorComercial setorComercial " + "  left join imovel.ligacaoAgua ligacaoAgua "
							+ "  left join imovel.ligacaoEsgoto ligacaoEsgoto " + " where "
							+ "  sistemaParametro.anoMesFaturamento = conta.referencia and "
							+ "  conta.referencia = :anoMesReferencia and "
							+ "  (conta.debitoCreditoSituacaoAtual.id = 0 or conta.debitoCreditoSituacaoAnterior.id = 0) and "
							+ "  quadra.setorComercial.id = :idSetor " + " order by "
							+ "  conta.id, conta.localidade.gerenciaRegional.id, conta.localidade.unidadeNegocio.id, "
							+ "  conta.localidade.id, " + "  quadra.setorComercial.id, quadra.id, quadra.numeroQuadra";

			retorno = session.createQuery(hql).setInteger("anoMesReferencia", anoMes).setInteger("idSetor", idSetor).setFirstResult(indice)
							.setMaxResults(qtRegistros).list();
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	public List getImoveisResumoFaturamento(int idSetor, int anoMes, int indice, int qtRegistros) throws ErroRepositorioException{

		List retorno = null;

		Session session = HibernateUtil.getSession();
		try{
			String hql = "select "
							+ "  sistemaParametro.anoMesFaturamento, "// 0
							+ "  imovel.id, "// 1
							+ "  imovel.localidade.gerenciaRegional.id, "// 2
							+ "  imovel.localidade.unidadeNegocio.id, "// 3
							+ "  imovel.localidade.localidade.id, "// 4 elo
							+ "  imovel.localidade.id, "// 5 localidade
							+ "  quadra.setorComercial.id, "// 6
							+ "  rota.id, "// 7
							+ "  quadra.id, "// 8
							+ "  imovel.setorComercial.codigo, "// 9
							+ "  quadra.numeroQuadra, "// 10
							+ "  imovel.imovelPerfil.id, "// 11
							+ "  imovel.ligacaoAguaSituacao.id, "// 12
							+ "  imovel.ligacaoEsgotoSituacao.id, "// 13
							+ "  case when ( "
							+ "    imovel.ligacaoAgua.ligacaoAguaPerfil.id is null ) then "
							+ "    0 "
							+ "  else "
							+ "    imovel.ligacaoAgua.ligacaoAguaPerfil.id "
							+ "  end, "// 14
							+ "  case when ( "
							+ "    imovel.ligacaoEsgoto.ligacaoEsgotoPerfil.id is null ) then "
							+ "    0 "
							+ "  else "
							+ "    imovel.ligacaoEsgoto.ligacaoEsgotoPerfil.id "
							+ "  end "// 15
							+ " from " + "  gcom.cadastro.sistemaparametro.SistemaParametro sistemaParametro, "
							+ "  gcom.cadastro.imovel.Imovel imovel " + "  inner join imovel.quadra quadra "
							+ "  inner join imovel.rota rota " + "  inner join imovel.setorComercial setorComercial "
							+ "  left join imovel.ligacaoAgua ligacaoAgua " + "  left join imovel.ligacaoEsgoto ligacaoEsgoto " + " where "
							+ "  sistemaParametro.anoMesFaturamento = :anoMesReferencia " + "  quadra.setorComercial.id = :idSetor "
							+ "  order by " + "  imovel.id, imovel.localidade.gerenciaRegional.id, imovel.localidade.unidadeNegocio.id, "
							+ "  imovel.localidade.id, " + "  quadra.setorComercial.id, quadra.id, quadra.numeroQuadra";

			retorno = session.createQuery(hql).setInteger("anoMesReferencia", anoMes).setInteger("idSetor", idSetor).setFirstResult(indice)
							.setMaxResults(qtRegistros).list();
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	public Collection<Integer> pesquisarIdsSetores() throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{

			consulta = " select " + " quad.setorComercial.id as idSetor " + "		 from " + "  Conta cont  " + "  inner join cont.imovel imo "
							+ "  inner join imo.quadra quad " + " where " + "  cont.referencia = 200704 and "
							+ "  (cont.debitoCreditoSituacaoAtual.id = 0 or cont.debitoCreditoSituacaoAnterior.id = 0) and "
							+ "  cont.localidade.id in (96) " + " group by quad.setorComercial.id ";

			// ,41,96,99,142,339,341,342,347,734

			retorno = session.createQuery(consulta).list();
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * Método que retona uma lista de objeto xxxxx que representa o resumo do
	 * faturamento (Débitos a Cobrar)
	 * 
	 * @author Marcio Roberto
	 * @date 17/05/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	// int idSetor, int anoMes, int indice, int qtRegistros
	public List getPesquisaDebitoACobrar(int idSetor, int anoMes) throws ErroRepositorioException{

		List retorno = null;

		Session session = HibernateUtil.getSession();
		try{

			String hql = " select "
							+ " 1, " // 00 = FINANCIAMENTO TIPO
							+ " 6, " // 01 = DOCUMENTO TIPO
							+ " debitoacobrar.lancamentoItemContabil.id, " // 02 = LANCAMENTO ITEM
							// CONTABIL
							+ " loca.gerenciaRegional.id, " // 03 = GERENCIA REGIONAL
							+ " loca.unidadeNegocio.id, " // 04 = UNIDADE NEGOCIO
							+ " loca.localidade.id, " // elo // 05 = ELO
							+ " loca.id, "// idlocalidade // 06 = LOCALIDADE
							+ " debitoacobrar.quadra.setorComercial.id, " // 07 = SETOR COMERCIAL
							+ " debitoacobrar.imov.rota.id, " // 08 = ROTA
							+ " debitoacobrar.quadra.id, " // 09 = QUADRA
							+ " debitoacobrar.codigoSetorComercial, " // 10 = CODIGO SETOR COMERCIAL
							+ " debitoacobrar.numeroQuadra, " // 11 = NUMERO QUADRA
							+ " imov.imovelPerfil.id, " // 12 = PERFIL DO IMOVEL
							+ " imov.ligacaoAguaSituacao.id, " // 13 = SITUACAO AGUA LIGACAO
							+ " imov.ligacaoEsgotoSituacao.id, " // 14 = SITUACAO ESGOTO LIGACAO
							+ "  case when ( " // -------------------
							+ "    imov.ligacaoAgua.ligacaoAguaPerfil.id is null ) then " // CHAVES
							// DE
							// QUEBRA!
							+ "    0 " // -------------------
							+ "  else " //
							+ "    imov.ligacaoAgua.ligacaoAguaPerfil.id " //
							+ "  end, " // // 15
							+ "  case when ( " //
							+ "    imov.ligacaoEsgoto.ligacaoEsgotoPerfil.id is null ) then " //
							+ "    0 " //
							+ "  else " //
							+ "    imov.ligacaoEsgoto.ligacaoEsgotoPerfil.id " // 16
							+ "  end, "// //
							+ " debitoacobrar.debitoCreditoSituacaoAtual.id, " // 17
							+ " debitoacobrar.imovel.rota.empresa.id, " // 18 = EMPRESA
							+ " imov.consumoTarifa.id, " // 19 = CONSUMO TARIFA
							+ " debitoacobrar.imovel.rota.faturamentoGrupo.id, " // 20 = GRUPO DE
							// FATURAMENTO
							// ======== VALORES ======== VALORES ======== VALORES ======== VALORES
							// ===================================================
							+ "  case when debitoacobrar.debitoCreditoSituacaoAtual.id = 0 or debitoacobrar.debitoCreditoSituacaoAnterior.id = 0 then "
							+ "       sum(debitoacobrar.valorDebito) end as valorIncluido , " // 21
							// =
							// VALOR
							// FINANCIAMENTO
							// INCLUIDOS
							+ "  0 , " // 22 = QUANTIDADE FINANCIAMENTO INCLUIDOS
							+ "  case when debitoacobrar.debitoCreditoSituacaoAtual.id = 3 then "
							+ "       sum(  (debitoacobrar.valorDebito) - ( "
							+ "       (debitoacobrar.valorDebito)/(debitoacobrar.numeroPrestacaoDebito)* "
							+ "       (debitoacobrar.numeroPrestacaoCobradas)) "
							+ "       )end as valorCancelado , " // 23 = VALOR FINANCIAMENTOS
							// CANCELADOS
							+ "  0, " // 24 = QUANTIDADE FINANCIAMENTOS CANCELADOS
							// =======================================================================================================================
							+ "  debitoacobrar.debitoTipo.id,  " // 25 = DEBITO TIPO
							+ "  imov.id " // 26
							+ "     "
							+ "     "
							+ " from "
							+ "  gcom.faturamento.debito.DebitoACobrar debitoacobrar "
							+ "  inner join debitoacobrar.lancamentoItemContabil lancamentoitemcontabil "
							+ "  inner join debitoacobrar.imovel imov "
							+ "  inner join imov.localidade loca "
							+ "  left  join imov.ligacaoAgua ligacaoAgua "
							+ "  left  join imov.ligacaoEsgoto ligacaoEsgoto "
							+ "     "
							+ "     "
							+ " where "
							+ "  debitoacobrar.anoMesReferenciaContabil = :anoMes  and "
							+ "  debitoacobrar.quadra.setorComercial.id = :idSetor and "
							+ "  debitoacobrar.financiamentoTipo.id = 1 "
							+ "     "
							+ "     "
							+ " group by "
							+ "    debitoacobrar.lancamentoItemContabil.id, loca.gerenciaRegional.id, loca.unidadeNegocio.id, "
							+ "    loca.localidade.id, loca.id, debitoacobrar.quadra.setorComercial.id,  "
							+ "    debitoacobrar.imovel.rota.id, debitoacobrar.quadra.id, debitoacobrar.codigoSetorComercial, "
							+ "    debitoacobrar.numeroQuadra, imov.imovelPerfil.id, imov.ligacaoAguaSituacao.id, "
							+ "    imov.ligacaoEsgotoSituacao.id, imov.ligacaoAgua.ligacaoAguaPerfil.id, "
							+ "    imov.ligacaoEsgoto.ligacaoEsgotoPerfil.id, debitoacobrar.debitoCreditoSituacaoAtual.id, "
							+ "    debitoacobrar.debitoCreditoSituacaoAnterior.id, debitoacobrar.imovel.rota.empresa.id,   "
							+ "    imov.consumoTarifa.id, debitoacobrar.imovel.rota.faturamentoGrupo.id, "
							+ "    debitoacobrar.debitoTipo.id, imov.id "
							+ "     "
							+ "     "
							+ " order by "
							+ "    loca.gerenciaRegional.id, loca.unidadeNegocio.id, loca.localidade.id,  "
							+ "    loca.id, debitoacobrar.quadra.setorComercial.id,  "
							+ "    debitoacobrar.imovel.rota.id, debitoacobrar.quadra.id, debitoacobrar.codigoSetorComercial, "
							+ "    debitoacobrar.numeroQuadra, imov.imovelPerfil.id, imov.ligacaoAguaSituacao.id, "
							+ "    imov.ligacaoEsgotoSituacao.id, imov.ligacaoAgua.ligacaoAguaPerfil.id, "
							+ "    imov.ligacaoEsgoto.ligacaoEsgotoPerfil.id, debitoacobrar.debitoCreditoSituacaoAtual.id, "
							+ "    debitoacobrar.imovel.rota.empresa.id, imov.consumoTarifa.id, debitoacobrar.imovel.rota.faturamentoGrupo.id, "
							+ "    debitoacobrar.debitoTipo.id ";

			retorno = session.createQuery(hql).setInteger("anoMes", anoMes).setInteger("idSetor", idSetor).list();
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * @author Roberto Barbalho
	 * @date 28/08/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List getPesquisaImpostos(int idConta) throws ErroRepositorioException{

		List retorno = null;
		Session session = HibernateUtil.getSession();
		try{

			String hql = " select " + "   cid.impostoTipo.id,  " + "   cid.valorImposto " + " from "
							+ "   gcom.faturamento.conta.ContaImpostosDeduzidos cid " + " where cid.conta.id = :idConta ";

			retorno = session.createQuery(hql).setInteger("idConta", idConta).list();
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * Método que retona uma lista de objeto xxxxx que representa o resumo do
	 * faturamento (Guia de Pagamento)
	 * 
	 * @author Marcio Roberto
	 * @date 05/09/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List getPesquisaGuiaPagamento(int idSetor, int anoMes) throws ErroRepositorioException{

		List retorno = null;

		Session session = HibernateUtil.getSession();
		try{

			String hql = " select "
							+ " 1, " // 00 = FINANCIAMENTO TIPO
							+ " 7, " // 01 = DOCUMENTO TIPO
							+ " guiaPagamento.lancamentoItemContabil.id, " // 02 = LANCAMENTO ITEM
							// CONTABIL
							+ " loca.gerenciaRegional.id, " // 03 = GERENCIA REGIONAL
							+ " loca.unidadeNegocio.id, " // 04 = UNIDADE NEGOCIO
							+ " loca.localidade.id, " // elo // 05 = ELO
							+ " loca.id, "// idlocalidade // 06 = LOCALIDADE
							+ " imov.quadra.setorComercial.id, " // 07 = SETOR COMERCIAL
							+ " imov.rota.id, " // 08 = ROTA
							+ " imov.quadra.id, " // 09 = QUADRA
							+ " imov.quadra.setorComercial.codigo, " // 10 = CODIGO SETOR COMERCIAL
							+ " imov.quadra.numeroQuadra, " // 11 = NUMERO QUADRA
							+ " imov.imovelPerfil.id, " // 12 = PERFIL DO IMOVEL
							+ " imov.ligacaoAguaSituacao.id, " // 13 = SITUACAO AGUA LIGACAO
							+ " imov.ligacaoEsgotoSituacao.id, " // 14 = SITUACAO ESGOTO LIGACAO
							+ "  case when ( " // -------------------
							+ "    imov.ligacaoAgua.ligacaoAguaPerfil.id is null ) then " // CHAVES
							// DE
							// QUEBRA!
							+ "    0 " // -------------------
							+ "  else " //
							+ "    imov.ligacaoAgua.ligacaoAguaPerfil.id " //
							+ "  end, " // // 15 = PERFIL LIGACAO AGUA
							+ "  case when ( " //
							+ "    imov.ligacaoEsgoto.ligacaoEsgotoPerfil.id is null ) then " //
							+ "    0 " //
							+ "  else " //
							+ "    imov.ligacaoEsgoto.ligacaoEsgotoPerfil.id " // 16 = PERFIL
							// LIGACAO ESGOTO
							+ "  end, "// //
							+ " guiaPagamento.debitoCreditoSituacaoAtual.id, " // 17
							+ " imov.rota.empresa.id, " // 18 = EMPRESA
							+ " imov.consumoTarifa.id, " // 19 = CONSUMO TARIFA
							+ " imov.rota.faturamentoGrupo.id, " // 20 = GRUPO DE FATURAMENTO
							+ " sum(guiaPagamento.valorDebito) as valordebitos, " // 21 = VALOR
							// DEBITO GUIA
							+ " count(guiaPagamento.id) as qtddocumentos, " // 22 = QUANTIDADE GUIA
							+ " guiaPagamento.debitoTipo.id,  " // 23 = DEBITO TIPO
							+ " imov.id " // 24 = IMOVEL
							+ "     " + "     " + " from " + " gcom.arrecadacao.pagamento.GuiaPagamento guiaPagamento "
							+ " inner join guiaPagamento.lancamentoItemContabil lancamentoitemcontabil "
							+ " inner join guiaPagamento.imovel imov " + " inner join imov.localidade loca "
							+ " left  join imov.ligacaoAgua ligacaoAgua " + " left  join imov.ligacaoEsgoto ligacaoEsgoto " + "     "
							+ "     " + " where " + "  guiaPagamento.anoMesReferenciaContabil = :anoMes  and "
							+ "  imov.quadra.setorComercial.id = :idSetor and "
							+ "  (guiaPagamento.debitoCreditoSituacaoAtual.id = 0 or guiaPagamento.debitoCreditoSituacaoAnterior.id = 0) "
							+ "     " + "     " + " group by "
							+ "   guiaPagamento.lancamentoItemContabil.id, loca.gerenciaRegional.id, loca.unidadeNegocio.id, "
							+ "   loca.localidade.id, loca.id, imov.quadra.setorComercial.id, imov.rota.id, "
							+ "   imov.quadra.id, imov.quadra.setorComercial.codigo, imov.quadra.numeroQuadra, imov.imovelPerfil.id, "
							+ "   imov.ligacaoAguaSituacao.id, imov.ligacaoEsgotoSituacao.id, "
							+ "   imov.ligacaoAgua.ligacaoAguaPerfil.id, imov.ligacaoEsgoto.ligacaoEsgotoPerfil.id, "
							+ "   guiaPagamento.debitoCreditoSituacaoAtual.id, imov.rota.empresa.id, "
							+ "   imov.consumoTarifa.id, imov.rota.faturamentoGrupo.id, guiaPagamento.debitoTipo.id, imov.id " + "     "
							+ "     " + " order by "
							+ "   guiaPagamento.lancamentoItemContabil.id, loca.gerenciaRegional.id, loca.unidadeNegocio.id, "
							+ "   loca.localidade.id, loca.id, imov.quadra.setorComercial.id, imov.rota.id, "
							+ "   imov.quadra.id, imov.quadra.setorComercial.codigo, imov.quadra.numeroQuadra, imov.imovelPerfil.id, "
							+ "   imov.ligacaoAguaSituacao.id, imov.ligacaoEsgotoSituacao.id, "
							+ "   imov.ligacaoAgua.ligacaoAguaPerfil.id, imov.ligacaoEsgoto.ligacaoEsgotoPerfil.id, "
							+ "   guiaPagamento.debitoCreditoSituacaoAtual.id, imov.rota.empresa.id, "
							+ "   imov.consumoTarifa.id, imov.rota.faturamentoGrupo.id, guiaPagamento.debitoTipo.id  ";

			retorno = session.createQuery(hql).setInteger("anoMes", anoMes).setInteger("idSetor", idSetor).list();
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

}
