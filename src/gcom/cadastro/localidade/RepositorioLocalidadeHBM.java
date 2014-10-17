/*
 * Copyright (C) 2007-2007 the GSAN � Sistema Integrado de Gest�o de Servi�os de Saneamento
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
 * Foundation, Inc., 59 Temple Place � Suite 330, Boston, MA 02111-1307, USA
 */

/*
 * GSAN � Sistema Integrado de Gest�o de Servi�os de Saneamento
 * Copyright (C) <2007> 
 * Adriano Britto Siqueira
 * Alexandre Santos Cabral
 * Ana Carolina Alves Breda
 * Ana Maria Andrade Cavalcante
 * Aryed Lins de Ara�jo
 * Bruno Leonardo Rodrigues Barros
 * Carlos Elmano Rodrigues Ferreira
 * Cl�udio de Andrade Lira
 * Denys Guimar�es Guenes Tavares
 * Eduardo Breckenfeld da Rosa Borges
 * Fab�ola Gomes de Ara�jo
 * Fl�vio Leonardo Cavalcanti Cordeiro
 * Francisco do Nascimento J�nior
 * Homero Sampaio Cavalcanti
 * Ivan S�rgio da Silva J�nior
 * Jos� Edmar de Siqueira
 * Jos� Thiago Ten�rio Lopes
 * K�ssia Regina Silvestre de Albuquerque
 * Leonardo Luiz Vieira da Silva
 * M�rcio Roberto Batista da Silva
 * Maria de F�tima Sampaio Leite
 * Micaela Maria Coelho de Ara�jo
 * Nelson Mendon�a de Carvalho
 * Newton Morais e Silva
 * Pedro Alexandre Santos da Silva Filho
 * Rafael Corr�a Lima e Silva
 * Rafael Francisco Pinto
 * Rafael Koury Monteiro
 * Rafael Palermo de Ara�jo
 * Raphael Veras Rossiter
 * Roberto Sobreira Barbalho
 * Rodrigo Avellar Silveira
 * Rosana Carvalho Barbosa
 * S�vio Luiz de Andrade Cavalcante
 * Tai Mu Shih
 * Thiago Augusto Souza do Nascimento
 * Tiago Moreno Rodrigues
 * Vivianne Barbosa Sousa
 *
 * Este programa � software livre; voc� pode redistribu�-lo e/ou
 * modific�-lo sob os termos de Licen�a P�blica Geral GNU, conforme
 * publicada pela Free Software Foundation; vers�o 2 da
 * Licen�a.
 * Este programa � distribu�do na expectativa de ser �til, mas SEM
 * QUALQUER GARANTIA; sem mesmo a garantia impl�cita de
 * COMERCIALIZA��O ou de ADEQUA��O A QUALQUER PROP�SITO EM
 * PARTICULAR. Consulte a Licen�a P�blica Geral GNU para obter mais
 * detalhes.
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU
 * junto com este programa; se n�o, escreva para Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 * 02111-1307, USA.
 */

package gcom.cadastro.localidade;

import gcom.cadastro.endereco.LogradouroBairro;
import gcom.cadastro.endereco.LogradouroCep;
import gcom.micromedicao.Rota;
import gcom.util.*;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.hibernate.*;

/**
 * 
 * Title: GCOM
 * 
 * Description: Reposit�rio de Localdiade
 * 
 * Copyright: Copyright (c) 2005
 * 
 * Company: COMPESA - Companhia Pernambucana de Saneamento
 * 
 * @author Pedro Alexandre
 * @created 13 de Janeiro de 2006
 * @version 1.0
 */

/**
 * @author Administrador
 *
 */
/**
 * @author Administrador
 */
public class RepositorioLocalidadeHBM
				implements IRepositorioLocalidade {

	// cria uma vari�vel da inteface do reposit�rio de localidade
	private static IRepositorioLocalidade instancia;

	// construtor da classe
	private RepositorioLocalidadeHBM() {

	}

	// retorna uma inst�ncia da reposit�rio
	public static IRepositorioLocalidade getInstancia(){

		// se n�o existe ainda a inst�ncia
		if(instancia == null){
			// cria a inst�ncia do reposit�rio
			instancia = new RepositorioLocalidadeHBM();
		}
		// retorna a inst�ncia do reposit�rio
		return instancia;
	}

	/**
	 * Pesquisa uma cole��o de localidades por ger�ncia regional
	 * 
	 * @param idGerenciaRegional
	 *            C�digo da ger�ncia regional solicitada
	 * @return Cole��o de Localidades da Ger�ncia Regional solicitada
	 * @exception ErroRepositorioException
	 *                Erro no hibernate
	 */
	public Collection<Localidade> pesquisarLocalidadePorGerenciaRegional(int idGerenciaRegional) throws ErroRepositorioException{

		// cria a vari�vel que vai armazenar a cole��o pesquisada
		Collection retorno = null;

		// cria a sess�o com o hibernate
		Session session = HibernateUtil.getSession();

		try{
			// cria o HQL para consulta
			String consulta = "select localidade " + "from Localidade localidade " + "inner join localidade.gerenciaRegional greg "
							+ "where greg = :idGerenciaRegional ";

			// pesquisa a cole��o de acordo com o par�metro informado
			retorno = session.createQuery(consulta).setInteger("idGerenciaRegional", idGerenciaRegional).list();

			// erro no hibernate
		}catch(HibernateException e){
			// levanta a exce��o para a pr�xima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sess�o
			HibernateUtil.closeSession(session);
		}
		// retorna a cole��o pesquisada
		return retorno;
	}

	/**
	 * Obt�m o id da localidade
	 * 
	 * @author S�vio Luiz
	 * @date 08/03/2006
	 * @param idImovel
	 * @return Um integer que representa o id da localidade
	 * @throws ControladorException
	 */
	public Integer pesquisarIdLocalidade(Integer idImovel) throws ErroRepositorioException{

		Integer retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "SELECT localidade.id " + "FROM Imovel imovel " + "LEFT JOIN imovel.localidade localidade "
							+ "WHERE imovel.id = :idImovel ";

			retorno = (Integer) session.createQuery(consulta).setInteger("idImovel", idImovel.intValue()).setMaxResults(1).uniqueResult();
		}catch(HibernateException e){

			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;

	}

	/**
	 * Obt�m o id da localidade
	 * 
	 * @author S�vio Luiz
	 * @date 08/03/2006
	 * @param idImovel
	 * @return Um integer que representa o id da localidade
	 * @throws ControladorException
	 */
	public Collection pesquisarTodosIdLocalidade() throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "SELECT loc.id " + "FROM Localidade loc";

			retorno = session.createQuery(consulta).list();
		}catch(HibernateException e){

			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;

	}

	/**
	 * M�todo que retorna o maior id da Localidade
	 * 
	 * @author Vivianne Sousa
	 * @date 12/07/2006
	 * @return
	 * @throws ControladorException
	 */

	public int pesquisarMaximoIdLocalidade() throws ErroRepositorioException{

		int retorno = 0;
		Object maxIdLocalidade;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "SELECT max(l.id) " + "FROM Localidade l ";

			maxIdLocalidade = session.createQuery(consulta).setMaxResults(1).uniqueResult();

			if(maxIdLocalidade != null){
				retorno = ((Number) maxIdLocalidade).intValue();
			}

		}catch(HibernateException e){
			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * Pesquisa uma localidade pelo id
	 * 
	 * @author Rafael Corr�a
	 * @date 01/08/2006
	 * @return Localidade
	 * @exception ErroRepositorioException
	 *                Erro no hibernate
	 */
	public Object[] pesquisarObjetoLocalidadeRelatorio(Integer idLocalidade) throws ErroRepositorioException{

		// cria a vari�vel que vai armazenar a cole��o pesquisada

		Object[] retorno = null;

		// cria a sess�o com o hibernate
		Session session = HibernateUtil.getSession();

		try{
			// cria o HQL para consulta
			String consulta = "select loc.loca_id as id, " + "loc.loca_nmlocalidade as descricao " + "from localidade loc "
							+ "where loc.loca_id = " + idLocalidade.toString();

			// pesquisa a cole��o de acordo com o par�metro informado
			Collection colecaoLocalidades = session.createSQLQuery(consulta).addScalar("id", Hibernate.INTEGER).addScalar("descricao",
							Hibernate.STRING).list();

			retorno = Util.retonarObjetoDeColecaoArray(colecaoLocalidades);

			// erro no hibernate
		}catch(HibernateException e){
			// levanta a exce��o para a pr�xima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sess�o
			HibernateUtil.closeSession(session);
		}
		// retorna a cole��o pesquisada
		return retorno;
	}

	public Integer verificarExistenciaLocalidade(Integer idLocalidade) throws ErroRepositorioException{

		// cria a vari�vel que vai armazenar a cole��o pesquisada

		Integer retorno = null;

		// cria a sess�o com o hibernate
		Session session = HibernateUtil.getSession();

		try{
			// cria o HQL para consulta
			String consulta = "select id " + "from Localidade loc " + "where loc.id =" + idLocalidade.toString();

			// pesquisa a cole��o de acordo com o par�metro informado
			retorno = (Integer) session.createQuery(consulta).setMaxResults(1).uniqueResult();

			// erro no hibernate
		}catch(HibernateException e){
			// levanta a exce��o para a pr�xima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sess�o
			HibernateUtil.closeSession(session);
		}
		// retorna a cole��o pesquisada
		return retorno;
	}

	/**
	 * Atualiza logradouroBairro de um ou mais im�veis
	 * [UC0] Atualizar Logradouro
	 * 
	 * @author Raphael Rossiter
	 * @date 22/02/2007
	 * @param
	 * @return void
	 */
	public void atualizarLogradouroBairroGerenciaRegional(LogradouroBairro logradouroBairroAntigo, LogradouroBairro logradouroBairroNovo)
					throws ErroRepositorioException{

		String consulta = "";

		Session session = HibernateUtil.getSession();

		try{

			consulta = "UPDATE gcom.cadastro.localidade.GerenciaRegional SET "
							+ "lgbr_id = :idLogradouroBairroNovo, greg_tmultimaalteracao = :ultimaAlteracao "
							+ "WHERE lgbr_id = :idLogradouroBairroAntigo ";

			session.createQuery(consulta).setInteger("idLogradouroBairroNovo", logradouroBairroNovo.getId()).setTimestamp(
							"ultimaAlteracao", new Date()).setInteger("idLogradouroBairroAntigo", logradouroBairroAntigo.getId())
							.executeUpdate();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
	}

	/**
	 * Atualiza logradouroCep de um ou mais im�veis
	 * [UC0] Atualizar Logradouro
	 * 
	 * @author Raphael Rossiter
	 * @date 22/02/2007
	 * @param
	 * @return void
	 */
	public void atualizarLogradouroCepGerenciaRegional(LogradouroCep logradouroCepAntigo, LogradouroCep logradouroCepNovo)
					throws ErroRepositorioException{

		String consulta = "";

		Session session = HibernateUtil.getSession();

		try{

			consulta = "UPDATE gcom.cadastro.localidade.GerenciaRegional SET "
							+ "lgcp_id = :idLogradouroCepNovo, greg_tmultimaalteracao = :ultimaAlteracao "
							+ "WHERE lgcp_id = :idLogradouroCepAntigo ";

			session.createQuery(consulta).setInteger("idLogradouroCepNovo", logradouroCepNovo.getId()).setTimestamp("ultimaAlteracao",
							new Date()).setInteger("idLogradouroCepAntigo", logradouroCepAntigo.getId()).executeUpdate();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
	}

	/**
	 * Atualiza logradouroBairro de um ou mais im�veis
	 * [UC0] Atualizar Logradouro
	 * 
	 * @author Raphael Rossiter
	 * @date 22/02/2007
	 * @param
	 * @return void
	 */
	public void atualizarLogradouroBairro(LogradouroBairro logradouroBairroAntigo, LogradouroBairro logradouroBairroNovo)
					throws ErroRepositorioException{

		String consulta = "";

		Session session = HibernateUtil.getSession();

		try{

			consulta = "UPDATE gcom.cadastro.localidade.Localidade SET "
							+ "lgbr_id = :idLogradouroBairroNovo, loca_tmultimaalteracao = :ultimaAlteracao "
							+ "WHERE lgbr_id = :idLogradouroBairroAntigo ";

			session.createQuery(consulta).setInteger("idLogradouroBairroNovo", logradouroBairroNovo.getId()).setTimestamp(
							"ultimaAlteracao", new Date()).setInteger("idLogradouroBairroAntigo", logradouroBairroAntigo.getId())
							.executeUpdate();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
	}

	/**
	 * Atualiza logradouroCep de um ou mais im�veis
	 * [UC0] Atualizar Logradouro
	 * 
	 * @author Raphael Rossiter
	 * @date 22/02/2007
	 * @param
	 * @return void
	 */
	public void atualizarLogradouroCep(LogradouroCep logradouroCepAntigo, LogradouroCep logradouroCepNovo) throws ErroRepositorioException{

		String consulta = "";

		Session session = HibernateUtil.getSession();

		try{

			consulta = "UPDATE gcom.cadastro.localidade.Localidade SET "
							+ "lgcp_id = :idLogradouroCepNovo, loca_tmultimaalteracao = :ultimaAlteracao "
							+ "WHERE lgcp_id = :idLogradouroCepAntigo ";

			session.createQuery(consulta).setInteger("idLogradouroCepNovo", logradouroCepNovo.getId()).setTimestamp("ultimaAlteracao",
							new Date()).setInteger("idLogradouroCepAntigo", logradouroCepAntigo.getId()).executeUpdate();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
	}

	/**
	 * Obt�m Elo P�lo
	 * 
	 * @author Ana Maria
	 * @date 10/12/2007
	 * @throws ControladorException
	 */
	public Collection pesquisarEloPolo() throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "select loca " + "from Localidade loca " + "where loca.id in "
							+ "(select codElo.localidade.id from Localidade codElo) " + "order by loca.id";

			retorno = session.createQuery(consulta).list();

		}catch(HibernateException e){

			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;

	}

	/**
	 * Obt�m uma instancia de Localidade com dados de Gerencia Regional, Unid. Negocio e
	 * LocalidadeElo
	 * 
	 * @author eduardo henrique
	 * @date 03/09/2009
	 * @throws ControladorException
	 */
	public Localidade obterLocalidade(Integer idLocalidade) throws ErroRepositorioException{

		Localidade retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "from Localidade l " + "left join fetch l.gerenciaRegional gr " + "left join fetch l.unidadeNegocio un "
							+ "left join fetch l.localidade elo " + "where l.id = :idLocalidade  ";

			retorno = (Localidade) session.createQuery(consulta).setInteger("idLocalidade", idLocalidade).uniqueResult();

		}catch(HibernateException e){

			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;

	}

	public Collection pesquisarBairroPorSetorComercial(Integer setorComercial) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		String consulta;
		Collection retorno = null;
		try{
			consulta = "select distinct quadra.bairro from Quadra quadra inner join quadra.bairro bairro where quadra.setorComercial = :setorComercial";
			retorno = session.createQuery(consulta).setInteger("setorComercial", setorComercial).list();
		}catch(HibernateException e){
			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * Obt�m uma cole��o de Localidades com dados do intervalo de Id's
	 * 
	 * @author Anderson Italo
	 * @date 27/01/2011
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarLocalidadePorIdIntervalo(Integer idLocalidadeInicial, Integer idLocalidadeFinal)
					throws ErroRepositorioException{

		Collection retorno = null;

		// cria a sess�o com o hibernate
		Session session = HibernateUtil.getSession();

		try{
			// cria o HQL para consulta
			String consulta = "select loc.id, " // 0
							+ "loc.descricao, " // 1
							+ "loc.municipio.id " // 2
							+ "from Localidade loc " + "where loc.id between :idLocalidadeInicial and :idLocalidadeFinal ";

			// pesquisa a cole��o de acordo com o par�metro informado
			Collection colecaoLocalidades = session.createQuery(consulta).setInteger("idLocalidadeInicial", idLocalidadeInicial.intValue())
							.setInteger("idLocalidadeFinal", idLocalidadeFinal.intValue()).list();

			retorno = colecaoLocalidades;

		}catch(HibernateException e){
			// levanta a exce��o para a pr�xima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{

			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * @author Ailton Sousa
	 * @date 12/10/2011
	 * @param idLocalidade
	 * @return
	 * @throws ErroRepositorioException
	 */
	public String obterDescricaoLocalidade(Integer idLocalidade) throws ErroRepositorioException{

		String retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "select l.descricao from Localidade l " + "where l.id = :idLocalidade  ";

			retorno = (String) session.createQuery(consulta).setInteger("idLocalidade", idLocalidade).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){

			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;

	}

	/**
	 * @author Diogo Monteiro
	 * @date 04/06/2012
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List<Integer> consultarSetoresComerciaisAvisoCorte(Integer idFaturamentoAtvCron) throws ErroRepositorioException{

		List<Integer> retorno = null;

		Session session = HibernateUtil.getSession();
		StringBuilder consulta = new StringBuilder();

		try{

			consulta.append("select i.STCM_ID from IMOVEL i ");
			consulta.append(" inner join FATURAMENTO_ATIV_CRON_ROTA facr on i.ROTA_ID = facr.ROTA_ID ");
			consulta.append(" inner join FATURAMENTO_ATIVIDADE_CRON fac on facr.FTAC_ID = fac.FTAC_ID ");
			consulta.append(" WHERE fac.FTAC_ID = :ftac_id ");

			SQLQuery query = session.createSQLQuery(consulta.toString());
			query.setInteger("ftac_id", idFaturamentoAtvCron);

			retorno = query.list();

		}catch(HibernateException e){
			e.printStackTrace();
			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;

	}

	/**
	 * {@inheritDoc}
	 */
	public Collection<Object[]> pesquisarDadosResumidosSetorComercial(Integer idEmpresa) throws ErroRepositorioException{

		StringBuilder builder = new StringBuilder();
		builder.append(" SELECT DISTINCT s.LOCA_ID localidade_id, l.LOCA_NMLOCALIDADE localidade_desc, s.STCM_CDSETORCOMERCIAL setor_codigo,");
		builder.append(" l.LOCA_NNCONSUMOGRANDEUSUARIO limite_consumo, r.ROTA_ICCONSUMOAJUSTE indicador_ajuste, l.LOCA_CDELO localidade_elo_id,");
		builder.append(" l.GREG_ID gerencia_regional_id, s.STCM_ID setor_id ");
		builder.append(" FROM ");
		builder.append("     SETOR_COMERCIAL s  ");
		builder.append("     INNER JOIN imovel i ON s.STCM_ID = i.STCM_ID ");
		builder.append("     INNER JOIN LOCALIDADE l ON s.LOCA_ID = l.LOCA_ID ");
		builder.append("     INNER JOIN ROTA r on r.ROTA_ID = i.ROTA_ID AND r.EMPR_ID = :idEmpresa ");
		builder.append(" ORDER BY ");
		builder.append(" s.LOCA_ID, ");
		builder.append(" s.STCM_CDSETORCOMERCIAL ");
		Collection<Object[]> resultado;

		Session session = HibernateUtil.getSession();
		try{
			SQLQuery query = session.createSQLQuery(builder.toString());
			query.setInteger("idEmpresa", idEmpresa);

			query.addScalar("localidade_id", Hibernate.INTEGER);
			query.addScalar("localidade_desc", Hibernate.STRING);
			query.addScalar("setor_codigo", Hibernate.INTEGER);
			query.addScalar("limite_consumo", Hibernate.INTEGER);
			query.addScalar("indicador_ajuste", Hibernate.INTEGER);
			query.addScalar("localidade_elo_id", Hibernate.INTEGER);
			query.addScalar("gerencia_regional_id", Hibernate.INTEGER);
			query.addScalar("setor_id", Hibernate.INTEGER);

			resultado = query.list();

		}catch(HibernateException e){
			e.printStackTrace();
			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return resultado;
	}

	public Collection<Localidade> pesquisarLocalidadePorFiltro(FiltroLocalidade filtro, Integer pageOffset) throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();
		StringBuilder consulta = new StringBuilder("select loca ");

		try{
			consulta.append(" from Localidade loca ");
			consulta.append(" left join fetch loca.gerenciaRegional gr ");
			consulta.append(" left join fetch loca.unidadeNegocio un  ");
			consulta.append(" left join fetch loca.concessionarias c  ");

			consulta.append(" where ");

			if(Util.isNaoNuloBrancoZero(filtro.getIdLocalidade())){
				consulta.append(" loca.id = " + filtro.getIdLocalidade());
				consulta.append(" AND ");
			}

			if(Util.isNaoNuloBrancoZero(filtro.getNomeLocalidade())){
				if(ConstantesSistema.TIPO_PESQUISA_COMPLETA.equals(filtro.getIndicadorNomeIniciandoOuContendo())){
					consulta.append(" loca.descricao like '%" + filtro.getNomeLocalidade().trim().toUpperCase() + "%'");
				}else{
					consulta.append(" loca.descricao like '" + filtro.getNomeLocalidade().trim().toUpperCase() + "%'");
				}
				consulta.append(" AND ");
			}

			if(Util.isNaoNuloBrancoZero(filtro.getConcessionaria())){
				consulta.append(" c.id = " + filtro.getConcessionaria());
				consulta.append(" AND ");
			}

			if(Util.isNaoNuloBrancoZero(filtro.getGerenciaRegional())){
				consulta.append(" gr.id = " + filtro.getGerenciaRegional());
				consulta.append(" AND ");
			}

			if(Util.isNaoNuloBrancoZero(filtro.getUnidadeNegocio())){
				consulta.append(" un.id = " + filtro.getUnidadeNegocio());
				consulta.append(" AND ");
			}

			if(Util.isNaoNuloBrancoZero(filtro.getIndicadorUso())){
				consulta.append(" loca.indicadorUso = " + filtro.getIndicadorUso());
				consulta.append(" AND ");
			}

			consulta.append(" 0 = 0"); // �ltimo AND
			consulta.append(" order by loca.descricao");

			Query query = session.createQuery(consulta.toString());

			if(pageOffset == null){
				retorno = query.list();
			}else{
				retorno = query.setFirstResult(ConstantesSistema.NUMERO_MAXIMO_REGISTROS_CONSULTA * pageOffset)
								.setMaxResults(ConstantesSistema.NUMERO_MAXIMO_REGISTROS_CONSULTA).list();
			}

		}catch(HibernateException e){

			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;

	}

	/**
	 * Pesquisar Localidade por Faturamento Grupo
	 * 
	 * @author Felipe Rosacruz
	 * @param idFaturamentoGrupo
	 * @param idLocalidade
	 * @date 04/10/2013
	 * @throws ControladorException
	 */
	public Collection<Localidade> pesquisarLocalidadePorIdEFaturamentoGrupo(Integer idFaturamentoGrupo, Integer idLocalidade)
					throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();
		StringBuilder consulta = new StringBuilder();

		try{
			consulta.append("select localidade ");
			consulta.append("from Rota rota ");
			consulta.append("inner join rota.setorComercial setorComercial ");
			consulta.append("inner join setorComercial.localidade localidade ");
			consulta.append("where rota.faturamentoGrupo.id = :idFaturamentoGrupo ");
			consulta.append("and localidade.id = :idLocalidade ");

			Collection colecaoLocalidades = session.createQuery(consulta.toString())
							.setInteger("idFaturamentoGrupo", idFaturamentoGrupo.intValue())
							.setInteger("idLocalidade", idLocalidade.intValue()).list();

			retorno = colecaoLocalidades;

		}catch(HibernateException e){

			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * Pesquisar Setor Comercial por Faturamento Grupo
	 * 
	 * @author Felipe Rosacruz
	 * @param idFaturamentoGrupo
	 * @param cdSetorComercial
	 * @date 05/10/2013
	 * @throws ControladorException
	 */
	public Collection<SetorComercial> pesquisarSetorComercialPorIdEFaturamentoGrupo(Integer idFaturamentoGrupo, Integer cdSetorComercial)
					throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();
		StringBuilder consulta = new StringBuilder();

		try{
			consulta.append("select setorComercial ");
			consulta.append("from Rota rota ");
			consulta.append("inner join rota.setorComercial setorComercial ");
			consulta.append("where rota.faturamentoGrupo.id = :idFaturamentoGrupo ");
			consulta.append("and setorComercial.codigo = :cdSetorComercial ");

			Collection colecaoSetorComercial = session.createQuery(consulta.toString())
							.setInteger("idFaturamentoGrupo", idFaturamentoGrupo.intValue())
							.setInteger("cdSetorComercial", cdSetorComercial.intValue()).list();

			retorno = colecaoSetorComercial;

		}catch(HibernateException e){

			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * Pesquisar rota por Faturamento Grupo
	 * 
	 * @author Felipe Rosacruz
	 * @param idFaturamentoGrupo
	 * @param cdRota
	 * @date 05/10/2013
	 * @throws ControladorException
	 */
	public Collection<Rota> pesquisarRotaPorIdEFaturamentoGrupo(Integer idFaturamentoGrupo, Integer cdRota) throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();
		StringBuilder consulta = new StringBuilder();

		try{
			consulta.append("select rota ");
			consulta.append("from Rota rota ");
			consulta.append("where rota.faturamentoGrupo.id = :idFaturamentoGrupo ");
			consulta.append("and rota.codigo = :cdRota ");

			Collection colecaoRota = session.createQuery(consulta.toString())
							.setInteger("idFaturamentoGrupo", idFaturamentoGrupo.intValue()).setInteger("cdRota", cdRota.intValue()).list();

			retorno = colecaoRota;

		}catch(HibernateException e){

			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}
}