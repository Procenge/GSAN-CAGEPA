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

package gcom.cadastro;

import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao;
import gcom.cadastro.aguaparatodos.bean.AguaParaTodosMotivoExclusaoHelper;
import gcom.cadastro.cliente.ClienteRelacaoTipo;
import gcom.cadastro.endereco.LogradouroBairro;
import gcom.cadastro.imovel.ImovelPerfil;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.Quadra;
import gcom.cadastro.localidade.SetorComercial;
import gcom.cadastro.sistemaparametro.bean.DadosEnvioEmailHelper;
import gcom.cobranca.NacionalFeriado;
import gcom.cobranca.bean.FiltroRelatorioDebitoPorResponsavelHelper;
import gcom.relatorio.cadastro.imovel.*;
import gcom.util.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import org.hibernate.*;
import org.hibernate.criterion.Expression;
import org.hibernate.dialect.Dialect;
import org.hibernate.exception.GenericJDBCException;

/**
 * @author Administrador
 */
public class RepositorioCadastroHBM
				implements IRepositorioCadastro {

	private static IRepositorioCadastro instancia;

	/**
	 * Construtor da classe RepositorioFaturamentoHBM
	 */
	private RepositorioCadastroHBM() {

	}

	/**
	 * Retorna o valor de instance
	 * 
	 * @return O valor de instance
	 */
	public static IRepositorioCadastro getInstancia(){

		if(instancia == null){
			instancia = new RepositorioCadastroHBM();
		}
		return instancia;
	}

	/**
	 * Pesquisa os feriados(nacionais e municipais)
	 * 
	 * @author Kássia Albuquerque
	 * @date 22/01/2007
	 */
	public Collection pesquisarFeriado(Short tipoFeriado, String descricao, Date dataFeriadoInicio, Date dataFeriadoFim,
					Integer idMunicipio, Integer numeroPagina) throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			if(tipoFeriado != 1 && tipoFeriado != 4){

				consulta = " select '2' as tipoFeriado, mfer.mfer_id as id, mfer.mfer_dsferiado as descricao, "
								+ " muni.muni_nmmunicipio as descricaoMunicipio, mfer.mfer_dtferiado as data"
								+ " from municipio_feriado mfer" + " inner join municipio muni on(mfer.muni_id = muni.muni_id)";

				if((descricao != null && !descricao.equals("")) || (dataFeriadoInicio != null && !dataFeriadoInicio.equals(""))
								|| (idMunicipio != null && !idMunicipio.equals(""))){
					consulta += "where ";
					if(descricao != null && !descricao.equals("")){
						consulta += "upper(mfer.mfer_dsferiado) like '" + descricao.toUpperCase() + "%' and ";
					}

					if(dataFeriadoInicio != null && !dataFeriadoInicio.equals("")){
						consulta += "mfer.mfer_dtferiado between :dataInicio and :dataFim and ";
					}

					if(idMunicipio != null && !idMunicipio.equals("")){
						consulta += "mfer.muni_id = " + idMunicipio + " and ";
					}

					consulta = Util.formatarHQL(consulta, 4);
				}
			}

			if(tipoFeriado == 3){
				consulta += "union all";
			}

			if(tipoFeriado != 2){
				consulta += " select NFER_CDFERIADOABRANGENCIA as tipoFeriado, nfer_id as id, nfer_dsferiado as descricao,"
								+ " '' as descricaoMunicipio, nfer_dtferiado as data" + " from nacional_feriado ";

				if((descricao != null && !descricao.equals("")) || (dataFeriadoInicio != null && !dataFeriadoInicio.equals(""))
								|| (tipoFeriado == 1 || tipoFeriado == 4)){
					consulta += "where ";
					if(descricao != null && !descricao.equals("")){
						consulta += "upper(nfer_dsferiado) like '" + descricao.toUpperCase() + "%' and ";

					}

					if(dataFeriadoInicio != null && !dataFeriadoInicio.equals("")){
						consulta += "nfer_dtferiado between :dataInicio and :dataFim and ";
					}

					if(tipoFeriado == 1){
						consulta += "NFER_CDFERIADOABRANGENCIA = '" + NacionalFeriado.NACIONAL + "' and ";
					}else if(tipoFeriado == 4){
						consulta += "NFER_CDFERIADOABRANGENCIA = '" + NacionalFeriado.ESTADUAL + "' and ";
					}

					consulta = Util.formatarHQL(consulta, 4);
				}
			}

			consulta = consulta + "order by data";

			if(numeroPagina != null){
				if(dataFeriadoInicio != null && !dataFeriadoInicio.equals("")){
					retorno = session.createSQLQuery(consulta).addScalar("tipoFeriado", Hibernate.CHARACTER)
									.addScalar("id", Hibernate.INTEGER)
									.addScalar("descricao", Hibernate.STRING).addScalar("descricaoMunicipio", Hibernate.STRING).addScalar(
													"data", Hibernate.DATE).setDate("dataInicio", dataFeriadoInicio).setDate("dataFim",
													dataFeriadoFim).setFirstResult(10 * numeroPagina).setMaxResults(10).list();
				}else{
					retorno = session.createSQLQuery(consulta).addScalar("tipoFeriado", Hibernate.CHARACTER)
									.addScalar("id", Hibernate.INTEGER)
									.addScalar("descricao", Hibernate.STRING).addScalar("descricaoMunicipio", Hibernate.STRING).addScalar(
													"data", Hibernate.DATE).setFirstResult(10 * numeroPagina).setMaxResults(10).list();
				}
			}else{
				if(dataFeriadoInicio != null && !dataFeriadoInicio.equals("")){
					retorno = session.createSQLQuery(consulta).addScalar("tipoFeriado", Hibernate.CHARACTER)
									.addScalar("id", Hibernate.INTEGER)
									.addScalar("descricao", Hibernate.STRING).addScalar("descricaoMunicipio", Hibernate.STRING).addScalar(
													"data", Hibernate.DATE).setDate("dataInicio", dataFeriadoInicio).setDate("dataFim",
													dataFeriadoFim).list();
				}else{
					retorno = session.createSQLQuery(consulta).addScalar("tipoFeriado", Hibernate.CHARACTER)
									.addScalar("id", Hibernate.INTEGER)
									.addScalar("descricao", Hibernate.STRING).addScalar("descricaoMunicipio", Hibernate.STRING).addScalar(
													"data", Hibernate.DATE).list();
				}
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
	 * Pesquisar quantidade de registro dos feriados(nacionais e municipais)
	 * 
	 * @author Kássia Albuquerque
	 * @date 22/01/2007
	 */
	public Integer pesquisarFeriadoCount(Short tipoFeriado, String descricao, Date dataFeriadoInicio, Date dataFeriadoFim,
					Integer idMunicipio) throws ErroRepositorioException{

		int retorno = 0;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			if(tipoFeriado != 1 && tipoFeriado != 4){

				consulta = " select count(mfer.mfer_id) as id" + " from municipio_feriado mfer"
								+ " inner join municipio muni on(mfer.muni_id = muni.muni_id)";

				if((descricao != null && !descricao.equals("")) || (dataFeriadoInicio != null && !dataFeriadoInicio.equals(""))
								|| (idMunicipio != null && !idMunicipio.equals(""))){
					consulta += "where ";
					if(descricao != null && !descricao.equals("")){
						consulta += "upper(mfer.mfer_dsferiado) like '" + descricao.toUpperCase() + "%' and ";
					}

					if(dataFeriadoInicio != null && !dataFeriadoInicio.equals("")){
						consulta += "mfer.mfer_dtferiado between :dataInicio and :dataFim and ";
					}

					if(idMunicipio != null && !idMunicipio.equals("")){
						consulta += "mfer.muni_id = " + idMunicipio + " and ";
					}

					consulta = Util.formatarHQL(consulta, 4);
				}
			}

			if(tipoFeriado == 3){
				consulta += "union all";
			}

			if(tipoFeriado != 2){
				consulta += " select count(nfer_id) as id" + " from nacional_feriado ";

				if((descricao != null && !descricao.equals("")) || (dataFeriadoInicio != null && !dataFeriadoInicio.equals(""))
								|| (tipoFeriado == 1 || tipoFeriado == 4)){
					consulta += "where ";
					if(descricao != null && !descricao.equals("")){
						consulta += "upper(nfer_dsferiado) like '" + descricao.toUpperCase() + "%' and ";
					}

					if(dataFeriadoInicio != null && !dataFeriadoInicio.equals("")){
						consulta += "nfer_dtferiado between :dataInicio and :dataFim and ";
					}

					if(tipoFeriado == 1){
						consulta += "NFER_CDFERIADOABRANGENCIA = '" + NacionalFeriado.NACIONAL + "' and ";
					}else if(tipoFeriado == 4){
						consulta += "NFER_CDFERIADOABRANGENCIA = '" + NacionalFeriado.ESTADUAL + "' and ";
					}

					consulta = Util.formatarHQL(consulta, 4);
				}
			}

			Collection valores = null;
			if(dataFeriadoInicio != null && !dataFeriadoInicio.equals("")){
				valores = (Collection) session.createSQLQuery(consulta).addScalar("id", Hibernate.INTEGER).setDate("dataInicio",
								dataFeriadoInicio).setDate("dataFim", dataFeriadoFim).list();
			}else{
				valores = (Collection) session.createSQLQuery(consulta).addScalar("id", Hibernate.INTEGER).list();
			}

			Integer valor = 0;
			Iterator iteratorValor = valores.iterator();
			while(iteratorValor.hasNext()){
				valor = valor + (Integer) iteratorValor.next();
			}

			retorno = valor;

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
	 * Faz um Update na base
	 * 
	 * @author Kassia Albuquerque e Ana Maria
	 * @date 06/03/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public void atualizarMensagemSistema(String mensagemSistema) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		String consulta = "update SistemaParametro sp "
						+ "set sp.mensagemSistema =:mensagemSistema, sp.ultimaAlteracao = :dataAtual where sp.parmId = "
						+ ConstantesConfig.getIdEmpresa();

		try{

			session.createQuery(consulta).setString("mensagemSistema", mensagemSistema).setTimestamp("dataAtual", new Date())
							.executeUpdate();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);

		}
	}

	/**
	 * Pesquisa os dados do email do batch para ser enviado
	 * 
	 * @author Sávio Luiz
	 * @date 13/03/2007
	 */
	public EnvioEmail pesquisarEnvioEmail(Integer idEnvioEmail) throws ErroRepositorioException{

		EnvioEmail retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = "select envioEmail " + "from EnvioEmail envioEmail " + "where envioEmail.id = :idEnvioEmail";

			retorno = (EnvioEmail) session.createQuery(consulta).setInteger("idEnvioEmail", idEnvioEmail).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	public DadosEnvioEmailHelper pesquisarDadosEmailSistemaParametros() throws ErroRepositorioException{

		DadosEnvioEmailHelper retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = "select new gcom.cadastro.sistemaparametro.bean.DadosEnvioEmailHelper(ipServidorSmtp, dsEmailResponsavel, nomeAbreviadoEmpresa) "
							+ "from SistemaParametro sistemaParametro ";

			retorno = (DadosEnvioEmailHelper) session.createQuery(consulta).setMaxResults(1).uniqueResult();

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
	 * Pesquisar todos ids dos setores comerciais.
	 * [UC0564 - Gerar Resumo das Instalações de Hidrômetros]
	 * 
	 * @author Pedro Alexandre
	 * @date 25/04/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<Integer> pesquisarTodosIdsSetorComercial() throws ErroRepositorioException{

		Collection<Integer> retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{

			consulta = "select stcm.id from SetorComercial stcm  ";

			retorno = session.createQuery(consulta).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * Migração dos dados do município de Ribeirão - O sistema
	 * gerar as tabelas cliente, cliente_endereço, imovel, cliente_imovel,
	 * imovel_subcategoria, ligacao_agua a parti da tabela Cadastro_ribeirao;
	 * 
	 * @author Ana Maria
	 * @throws ControladorException
	 */

	public Object[] pesquisarSetorQuadra(Integer idLocalidade) throws ErroRepositorioException{

		Object[] retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = "select stcm.id, qdra.id " + "from Quadra qdra " + "inner join qdra.setorComercial stcm "
							+ "where stcm.localidade.id = :idLocalidade";

			retorno = (Object[]) session.createQuery(consulta).setInteger("idLocalidade", idLocalidade).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	public Integer pesquisarCEP() throws ErroRepositorioException{

		Integer retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = "select cep.cepId " + "from Cep cep " + "where cep.municipio like 'RIBEIRAO'";

			retorno = (Integer) session.createQuery(consulta).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	public Integer pesquisarBairro() throws ErroRepositorioException{

		Integer retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = "select bair.id " + "from Bairro bair " + "where bair.municipio.id = 1180";

			retorno = (Integer) session.createQuery(consulta).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	public Integer pesquisarLogradouroBairro(Integer codigoLogradouro) throws ErroRepositorioException{

		Integer retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = "select logB.id " + "from LogradouroBairro logB " + "where logB.logradouro.id = :codigoLogradouro";

			retorno = (Integer) session.createQuery(consulta).setInteger("codigoLogradouro", codigoLogradouro).setMaxResults(1)
							.uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	public Integer pesquisarLogradouroCep(Integer codigoLogradouro) throws ErroRepositorioException{

		Integer retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = "select logC.id " + "from LogradouroCep logC " + "where logC.logradouro.id = :codigoLogradouro";

			retorno = (Integer) session.createQuery(consulta).setInteger("codigoLogradouro", codigoLogradouro).setMaxResults(1)
							.uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * @author eduardo henrique
	 * @date 12/11/2008
	 *       Alteração de método legado do GSAN para utilizar dataHora adequado para o Oracle
	 */
	public void inserirClienteEndereco(Integer idCliente, String numeroImovelMenor, String numeroImovelMaior, Integer idCep,
					Integer idBairro, Integer idLograd, Integer idLogradBairro, Integer idLogradCep) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		String insert;

		Connection con = null;
		Statement stmt = null;

		try{

			con = session.connection();
			stmt = con.createStatement();

			insert = "insert into cliente_endereco(cled_id, clie_id, edtp_id, " + "edrf_id, cled_nnimovel, cled_dscomplementoendereco, "
							+ "cep_id, bair_id, cled_icenderecocorrespondencia, " + "cled_tmultimaalteracao, logr_id, lgbr_id, lgcp_id) "
							+ "values (nextval('sq_cliente_end'), " + idCliente + ", 1, 1, " + numeroImovelMenor + ", " + numeroImovelMaior
							+ ", " + idCep + ", " + idBairro + ", 1, " + " sysdate, " + idLograd + ", " + idLogradBairro + ", "
							+ idLogradCep + ")";

			stmt.executeUpdate(insert);

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}catch(SQLException e){
			throw new ErroRepositorioException(e, "Erro no Insert");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);

			try{
				stmt.close();
				con.close();
			}catch(SQLException e){
				throw new ErroRepositorioException(e, "Erro ao fechar conexões");
			}
		}
	}

	/**
	 * @author eduardo henrique
	 * @date 12/11/2008
	 *       Alteração de método legado do GSAN para utilizar dataHora adequado para o Oracle
	 */
	public void inserirClienteImovel(Integer idCliente, Integer idImovel, String data) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		String insert;

		Connection con = null;
		Statement stmt = null;

		try{

			con = session.connection();
			stmt = con.createStatement();

			insert = "insert into cliente_imovel(clim_id, " + "clie_id, imov_id, clim_dtrelacaoinicio, " + "clim_tmultimaalteracao, "
							+ "crtp_id, clim_icnomeconta) " + "values (nextval('sq_cliente_imov'), " + idCliente + ", " + idImovel + ", "
							+ data + ", " + " sysdate, " + "2, " + "1)";

			stmt.executeUpdate(insert);

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}catch(SQLException e){
			throw new ErroRepositorioException(e, "Erro no Insert");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);

			try{
				stmt.close();
				con.close();
			}catch(SQLException e){
				throw new ErroRepositorioException(e, "Erro ao fechar conexões");
			}
		}
	}

	public void inserirImovelSubcategoria(Integer idImovel, Integer idSubcategoria) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		String insert;

		Connection con = null;
		Statement stmt = null;

		try{

			con = session.connection();
			stmt = con.createStatement();

			insert = "insert into imovel_subcategoria(imov_id, scat_id, " + "imsb_qteconomia, imsb_tmultimaalteracao) " + "values ( "
							+ idImovel + ", " + idSubcategoria + ", " + "1, " + "sysdate)";

			stmt.executeUpdate(insert);

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}catch(SQLException e){
			throw new ErroRepositorioException(e, "Erro no Insert");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);

			try{
				stmt.close();
				con.close();
			}catch(SQLException e){
				throw new ErroRepositorioException(e, "Erro ao fechar conexões");
			}
		}
	}

	public void inserirLigacaoAgua(Integer idImovel, String dataBD) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		String insert;

		Connection con = null;
		Statement stmt = null;

		try{

			con = session.connection();
			stmt = con.createStatement();

			insert = "insert into ligacao_agua(lagu_id, lagu_dtimplantacao, lagu_dtligacaoagua, "
							+ "lagu_icemissaocortesupressao, lagd_id, lagm_id, lapf_id, lagu_tmultimaalteracao) " + "values ( " + idImovel
							+ ", " + dataBD + ", " + dataBD + ", 1, 2, 1, 1," + " sysdate)";

			stmt.executeUpdate(insert);

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}catch(SQLException e){
			throw new ErroRepositorioException(e, "Erro no Insert");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);

			try{
				stmt.close();
				con.close();
			}catch(SQLException e){
				throw new ErroRepositorioException(e, "Erro ao fechar conexões");
			}
		}
	}

	public Collection pesquisarCadastroRibeiraop() throws ErroRepositorioException{

		Collection retorno = new ArrayList();

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = "select crp " + "from CadastroRibeiraop crp " + "where crp.imovel.id is null " + "order by crp.codigo";

			retorno = session.createQuery(consulta).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	public void atualizarImovelRibeirao(Integer idImovel, Integer codigo) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();

		String atualizarValorExcedente;

		try{

			atualizarValorExcedente = "UPDATE CadastroRibeiraop " + "SET imov_id = :idImovel " + "WHERE codigo = :codigo ";

			session.createQuery(atualizarValorExcedente).setInteger("idImovel", idImovel).setInteger("codigo", codigo).executeUpdate();

		}catch(HibernateException e){

			throw new ErroRepositorioException(e, "Erro no Hibernate");

		}finally{
			HibernateUtil.closeSession(session);
		}
	}

	/**
	 * Pesquisa os imóveis do cliente de acordo com o tipo de relação
	 * [UC0251] Gerar Atividade de Ação de Cobrança
	 * [SB0001] Gerar Atividade de Ação de Cobrança para os Imóveis do Cliente
	 * 
	 * @author Sávio Luiz
	 * @created 23/11/2007
	 * @param cliente
	 * @param relacaoClienteImovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarClientesSubordinados(Integer idCliente)

	throws ErroRepositorioException{

		// cria a coleção de retorno
		Collection retorno = null;

		// Query
		StringBuilder consulta = new StringBuilder();

		// obtém a sessão
		Session session = HibernateUtil.getSession();

		try{

			consulta.append(" select ci.id ").append(" from Cliente ci ").append(" where ci.cliente.id = :idCliente");

			retorno = (Collection) session.createQuery(consulta.toString()).setInteger("idCliente", idCliente).list();

			// erro no hibernate
		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
			consulta = null;
		}
		// retorna a coleção de atividades pesquisada(s)
		return retorno;
	}

	/**
	 * [UC0624] Gerar Relatório para Atualização Cadastral
	 */

	public Collection pesquisarRelatorioAtualizacaoCadastral(Collection idLocalidades, Collection idSetores, Collection idQuadras,
					String rotaInicial, String rotaFinal, String sequencialRotaInicial, String sequencialRotaFinal)
					throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = "select imovel.id, "// 0
							+ " cliente.nome, "// 1
							+ " localidade.id, "// 2
							+ " localidade.descricao, "// 3
							+ " setorComercial.codigo, "// 4
							+ " setorComercial.descricao, "// 5
							+ " unidadeNegocio.nome,"// 6
							+ " imovel.quantidadeEconomias, "// 7
							+ " rota.codigo,"// 8
							+ " imovel.numeroSequencialRota, "// 9
							+ " imovel.indicadorExclusao,"// 10
							+ " unidadeNegocio.id "// 11
							+ " from ClienteImovel clienteImovel "
							+ " inner join clienteImovel.clienteRelacaoTipo relacaoTipo"
							+ " inner join clienteImovel.imovel imovel "
							+ " ineer join imovel.localidade localidade "
							+ " inner join imovel.setorComercial setorComercial "
							+ " inner join imovel.quadra quadra "
							+ " inner join imovel.rota rota "
							+ " inner join clienteImovel.cliente cliente"
							+ " inner join localidade.unidadeNegocio unidadeNegocio" + " where relacaoTipo.id =:idRelacaoTipo ";

			if(idLocalidades != null && !idLocalidades.isEmpty()){
				consulta = consulta + " and localidade.id in (";
				Iterator iterator = idLocalidades.iterator();
				while(iterator.hasNext()){
					Localidade localidade = (Localidade) iterator.next();
					consulta = consulta + localidade.getId().toString() + ",";
				}
				consulta = consulta.substring(0, (consulta.length() - 1));
				consulta = consulta + ")";
			}
			if(idSetores != null && !idSetores.isEmpty()){
				consulta = consulta + " and setorComercial.codigo in (:setores)";
				Iterator iterator = idSetores.iterator();
				while(iterator.hasNext()){
					SetorComercial setorComercial = (SetorComercial) iterator.next();
					consulta = consulta + setorComercial.getId().toString() + ",";
				}
				consulta = consulta.substring(0, (consulta.length() - 1));
				consulta = consulta + ")";
			}
			if(idQuadras != null && !idQuadras.isEmpty()){
				consulta = consulta + " and quadra.numeroQuadra in (:quadras)";
				Iterator iterator = idQuadras.iterator();
				while(iterator.hasNext()){
					Quadra quadra = (Quadra) iterator.next();
					consulta = consulta + quadra.getId().toString() + ",";
				}
				consulta = consulta.substring(0, (consulta.length() - 1));
				consulta = consulta + ")";
			}
			if(rotaInicial != null && !rotaInicial.trim().equals("") && rotaFinal != null && !rotaFinal.trim().equals("")){
				consulta = consulta + " and (rota.codigo >= " + rotaInicial + " and rota.codigo <= " + rotaFinal + ")";
			}

			if(sequencialRotaInicial != null && !sequencialRotaInicial.trim().equals("") && sequencialRotaFinal != null
							&& !sequencialRotaFinal.trim().equals("")){
				consulta = consulta + " and (imovel.numeroSequencialRota >= " + sequencialRotaInicial
								+ " and imovel.numeroSequencialRota <= " + sequencialRotaFinal + ")";
			}

			retorno = session.createQuery(consulta).setInteger("idRelacaoTipo", ClienteRelacaoTipo.USUARIO).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0725] Gerar Relatório de Imóveis por Situação da Ligação de Agua
	 * 
	 * @author Rafael Pinto
	 * @date 03/12/2007
	 * @param FiltrarRelatorioImoveisSituacaoLigacaoAguaHelper
	 * @return Collection<Object[]>
	 * @throws ErroRepositorioException
	 */
	public Collection<Object[]> pesquisarRelatorioImoveisSituacaoLigacaoAgua(FiltrarRelatorioImoveisSituacaoLigacaoAguaHelper filtro)
					throws ErroRepositorioException{

		Collection retorno = null;

		Integer localidadeInicial = filtro.getLocalidadeInicial();
		Integer localidadeFinal = filtro.getLocalidadeFinal();

		Integer setorComercialInicial = filtro.getSetorComercialInicial();
		Integer setorComercialFinal = filtro.getSetorComercialFinal();

		Integer rotaInicial = filtro.getRotaInicial();
		Integer rotaFinal = filtro.getRotaFinal();

		Integer sequencialRotaInicial = filtro.getSequencialRotalInicial();
		Integer sequencialRotaFinal = filtro.getSequencialRotalFinal();

		Integer unidadeNegocio = filtro.getUnidadeNegocio();
		Integer gerencia = filtro.getGerenciaRegional();

		Collection<Integer> colecaoSituacaoLigacaoAgua = filtro.getSituacaoLigacaoAgua();
		Collection<Integer> colecaoSituacaoLigacaoEsgoto = filtro.getSituacaoLigacaoEsgoto();

		String indicadorOrdenacao = filtro.getIndicadorOrdenacao();

		String consulta = "";
		Query query = null;
		Map parameters = new HashMap();

		Session session = HibernateUtil.getSession();

		try{
			consulta = "select imovel.id, " // 0
							+ " gerenciaRegional.id, " // 1
							+ " gerenciaRegional.nome, "// 2
							+ " unidadeNegocio.id," // 3
							+ " unidadeNegocio.nome," // 4
							+ " localidade.id, " // 5
							+ " localidade.descricao, " // 6
							+ " setorComercial.codigo, "// 7
							+ " setorComercial.descricao, "// 8
							+ " quadra.numeroQuadra, " // 9
							+ " cliente.nome, " // 10
							+ " ligacaoAguaSituacao.descricao, " // 11
							+ " ligacaoEsgotoSituacao.descricao, " // 12
							+ " rota.codigo," // 13
							+ " imovel.numeroSequencialRota, " // 14
							+ " imovel.lote, " // 15
							+ " imovel.subLote, " // 16
							+ " setorComercial.id, "// 17
							+ " rota.id, " // 18
							+ " rotaImovel.id, " // 19
							+ " imovel.numeroSegmento " // 20
							+ " from ClienteImovel clienteImovel "
							+ " inner join clienteImovel.clienteRelacaoTipo relacaoTipo"
							+ " inner join clienteImovel.imovel imovel "
							+ " inner join imovel.localidade localidade "
							+ " inner join imovel.setorComercial setorComercial "
							+ " inner join imovel.quadra quadra "
							+ " inner join imovel.rota rota"
							+ " inner join clienteImovel.cliente cliente"
							+ " inner join localidade.unidadeNegocio unidadeNegocio"
							+ " inner join localidade.gerenciaRegional gerenciaRegional"
							+ " inner join imovel.ligacaoAguaSituacao ligacaoAguaSituacao"
							+ " left join imovel.ligacaoEsgotoSituacao ligacaoEsgotoSituacao"
							+ " left join imovel.rota rotaImovel"
							+ " where ligacaoAguaSituacao.id in (:situacaoAgua) "
							+ " and relacaoTipo.id = :idRelacaoTipo"
							+ " and clienteImovel.dataFimRelacao is null ";

			parameters.put("situacaoAgua", colecaoSituacaoLigacaoAgua);
			parameters.put("idRelacaoTipo", ClienteRelacaoTipo.USUARIO);

			if(colecaoSituacaoLigacaoEsgoto != null && !colecaoSituacaoLigacaoEsgoto.isEmpty()){
				consulta = consulta + " and ligacaoEsgotoSituacao.id in (:situacaoEsgoto) ";
				parameters.put("situacaoEsgoto", colecaoSituacaoLigacaoEsgoto);
			}

			if(unidadeNegocio != null){
				consulta = consulta + " and unidadeNegocio.id in (:unidade) ";
				parameters.put("unidade", unidadeNegocio);
			}

			if(gerencia != null){
				consulta = consulta + " and gerenciaRegional.id in (:gerencia) ";
				parameters.put("gerencia", gerencia);
			}

			if(localidadeInicial != null){
				consulta = consulta + " and (localidade.id >= " + localidadeInicial + " and localidade.id <= " + localidadeFinal + ")";
			}

			if(setorComercialInicial != null){
				consulta = consulta + " and (setorComercial.codigo >= " + setorComercialInicial + " and setorComercial.codigo <= "
								+ setorComercialFinal + ")";
			}

			if(rotaInicial != null){
				consulta = consulta + " and (rotaImovel.codigo >= " + rotaInicial + " and rotaImovel.codigo <= " + rotaFinal + ")";
			}

			if(sequencialRotaInicial != null){
				consulta = consulta + " and (imovel.numeroSequencialRota >= " + sequencialRotaInicial
								+ " and imovel.numeroSequencialRota <= " + sequencialRotaFinal + ")";
			}

			if(!Util.isVazioOuBranco(indicadorOrdenacao)){

				if(indicadorOrdenacao.equals("1")){
					consulta += " order by gerenciaRegional.id,unidadeNegocio.id,localidade.id,"
									+ "setorComercial.id,imovel.id,rota.codigo,imovel.numeroSequencialRota";
				}

				if(indicadorOrdenacao.equals("2")){
					// LOCAL/SETOR/QUADRA/LOTE/SUBLOTE.
					consulta += " order by localidade.id, setorComercial.codigo, quadra.numeroQuadra, imovel.lote, imovel.subLote";
				}

				if(indicadorOrdenacao.equals("3")){
					// LOCAL/SETOR/ROTA/SEGMENTO/LOTE/SUBLOTE
					consulta += " order by localidade.id, setorComercial.codigo, rota.codigo, imovel.numeroSequencialRota, imovel.lote, imovel.subLote";
				}

			}

			query = session.createQuery(consulta);

			Set set = parameters.keySet();
			Iterator iterMap = set.iterator();
			while(iterMap.hasNext()){
				String key = (String) iterMap.next();
				if(parameters.get(key) instanceof Collection){
					Collection collection = (ArrayList) parameters.get(key);
					query.setParameterList(key, collection);
				}else{
					query.setParameter(key, parameters.get(key));
				}

			}

			retorno = query.list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0725] Gerar Relatório de Imóveis por Situação da Ligação de Agua
	 * Pesquisa o Total Registro
	 * 
	 * @author Rafael Pinto
	 * @date 04/12/2007
	 * @param FiltrarRelatorioImoveisSituacaoLigacaoAguaHelper
	 * @return Integer
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarTotalRegistroRelatorioImoveisSituacaoLigacaoAgua(FiltrarRelatorioImoveisSituacaoLigacaoAguaHelper filtro)
					throws ErroRepositorioException{

		Integer retorno = 0;

		Integer localidadeInicial = filtro.getLocalidadeInicial();
		Integer localidadeFinal = filtro.getLocalidadeFinal();

		Integer setorComercialInicial = filtro.getSetorComercialInicial();
		Integer setorComercialFinal = filtro.getSetorComercialFinal();

		Integer rotaInicial = filtro.getRotaInicial();
		Integer rotaFinal = filtro.getRotaFinal();

		Integer sequencialRotaInicial = filtro.getSequencialRotalInicial();
		Integer sequencialRotaFinal = filtro.getSequencialRotalFinal();

		Integer unidadeNegocio = filtro.getUnidadeNegocio();
		Integer gerencia = filtro.getGerenciaRegional();

		Collection<Integer> colecaoSituacaoLigacaoAgua = filtro.getSituacaoLigacaoAgua();
		Collection<Integer> colecaoSituacaoLigacaoEsgoto = filtro.getSituacaoLigacaoEsgoto();

		String consulta = "";
		Query query = null;
		Map parameters = new HashMap();
		Session session = HibernateUtil.getSession();
		try{
			consulta = "select count(*) " // 0
							+ " from ClienteImovel clienteImovel "
							+ " inner join clienteImovel.clienteRelacaoTipo relacaoTipo"
							+ " inner join clienteImovel.imovel imovel "
							+ " inner join imovel.localidade localidade "
							+ " inner join imovel.setorComercial setorComercial "
							+ " inner join imovel.quadra quadra "
							+ " inner join imovel.rota rota"
							+ " inner join clienteImovel.cliente cliente"
							+ " inner join localidade.unidadeNegocio unidadeNegocio"
							+ " inner join localidade.gerenciaRegional gerenciaRegional"
							+ " inner join imovel.ligacaoAguaSituacao ligacaoAguaSituacao"
							+ " left join imovel.ligacaoEsgotoSituacao ligacaoEsgotoSituacao"
							+ " where ligacaoAguaSituacao.id in (:situacaoAgua) "
							+ " and relacaoTipo.id = :idRelacaoTipo"
							+ " and clienteImovel.dataFimRelacao is null ";

			parameters.put("situacaoAgua", colecaoSituacaoLigacaoAgua);
			parameters.put("idRelacaoTipo", ClienteRelacaoTipo.USUARIO);

			if(colecaoSituacaoLigacaoEsgoto != null && !colecaoSituacaoLigacaoEsgoto.isEmpty()){
				consulta = consulta + " and ligacaoEsgotoSituacao.id in (:situacaoEsgoto) ";
				parameters.put("situacaoEsgoto", colecaoSituacaoLigacaoEsgoto);
			}

			if(unidadeNegocio != null){
				consulta = consulta + " and unidadeNegocio.id in (:unidade) ";
				parameters.put("unidade", unidadeNegocio);
			}

			if(gerencia != null){
				consulta = consulta + " and gerenciaRegional.id in (:gerencia) ";
				parameters.put("gerencia", gerencia);
			}

			if(localidadeInicial != null){
				consulta = consulta + " and (localidade.id >= " + localidadeInicial + " and localidade.id <= " + localidadeFinal + ")";
			}

			if(setorComercialInicial != null){
				consulta = consulta + " and (setorComercial.codigo >= " + setorComercialInicial + " and setorComercial.codigo <= "
								+ setorComercialFinal + ")";
			}

			if(rotaInicial != null){
				consulta = consulta + " and (rota.codigo >= " + rotaInicial + " and rota.codigo <= " + rotaFinal + ")";
			}

			if(sequencialRotaInicial != null){
				consulta = consulta + " and (imovel.numeroSequencialRota >= " + sequencialRotaInicial
								+ " and imovel.numeroSequencialRota <= " + sequencialRotaFinal + ")";
			}

			query = session.createQuery(consulta);

			Set set = parameters.keySet();
			Iterator iterMap = set.iterator();
			while(iterMap.hasNext()){
				String key = (String) iterMap.next();
				if(parameters.get(key) instanceof Collection){
					Collection collection = (ArrayList) parameters.get(key);
					query.setParameterList(key, collection);
				}else{
					query.setParameter(key, parameters.get(key));
				}

			}

			retorno = ((Number) query.setMaxResults(1).uniqueResult()).intValue();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0726] Gerar Relatório de Imóveis com Faturas em Atraso
	 * 
	 * @author Bruno Barros
	 * @date 06/12/2007
	 * @param FiltrarRelatorioImoveisFaturasAtraso
	 * @return Collection<Object[]>
	 * @throws ErroRepositorioException
	 */
	public Collection<Object[]> pesquisarRelatorioImoveisFaturasAtraso(FiltrarRelatorioImoveisFaturasAtrasoHelper filtro)
					throws ErroRepositorioException{

		Collection retorno = null;

		Integer localidadeInicial = filtro.getLocalidadeInicial();
		Integer localidadeFinal = filtro.getLocalidadeFinal();

		Integer setorComercialInicial = filtro.getSetorComercialInicial();
		Integer setorComercialFinal = filtro.getSetorComercialFinal();

		Integer rotaInicial = filtro.getRotaInicial();
		Integer rotaFinal = filtro.getRotaFinal();

		Integer sequencialRotaInicial = filtro.getSequencialRotalInicial();
		Integer sequencialRotaFinal = filtro.getSequencialRotalFinal();

		Integer unidadeNegocio = filtro.getUnidadeNegocio();
		Integer gerencia = filtro.getGerenciaRegional();

		Integer referenciaImoveisFaturasAtrasoInicial = filtro.getReferenciaFaturasAtrasoInicial();
		Integer referenciaImoveisFaturasAtrasoFinal = filtro.getReferenciaFaturasAtrasoFinal();

		Collection<Integer> colecaoSituacaoLigacaoAgua = filtro.getSituacaoLigacaoAgua();

		Integer quantidadeFaturasAtrasoInicial = filtro.getQuantidadeFaturasAtrasoInicial();
		Integer quantidadeFaturasAtrasoFinal = filtro.getQuantidadeFaturasAtrasoFinal();

		BigDecimal valorFaturasAtrasoInicial = filtro.getValorFaturasAtrasoInicial();
		BigDecimal valorFaturasAtrasoFinal = filtro.getValorFaturasAtrasoFinal();

		String consulta = "";
		Query query = null;
		Map parameters = new HashMap();

		Session session = HibernateUtil.getSession();

		try{

			consulta = " select " + "   c.imovel.id, " + "   c.imovel.localidade.gerenciaRegional.id, "
							+ "   c.imovel.localidade.gerenciaRegional.nome, " + "   c.imovel.localidade.unidadeNegocio.id, "
							+ "   c.imovel.localidade.unidadeNegocio.nome, " + "   c.imovel.localidade.id, "
							+ "   c.imovel.localidade.descricao, " + "   c.imovel.setorComercial.codigo, "
							+ "   c.imovel.setorComercial.descricao, " + "   c.imovel.quadra.numeroQuadra, " + "   ci.cliente.nome, "
							+ "   c.imovel.ligacaoAguaSituacao.descricao, " + "   c.imovel.ligacaoEsgotoSituacao.descricao, "
							+ "   c.imovel.rota.codigo, " + "   c.imovel.numeroSequencialRota, " + "   c.imovel.lote, "
							+ "   c.imovel.subLote, " + "   c.imovel.setorComercial.id, " + "   c.imovel.rota.id, "
							+ "   count(*) as quantidade, "
							+ "   sum( c.valorAgua + c.valorEsgoto + c.debitos - c.valorCreditos ) as valor " + " from " + "   Conta c, "
							+ "   ClienteImovel ci " + " where " + "   c.imovel.id = ci.imovel.id and "
							+ "   c.debitoCreditoSituacaoAtual in ( 0,1,2 ) and " + "   ci.clienteRelacaoTipo.id = "
							+ ClienteRelacaoTipo.USUARIO;

			if(unidadeNegocio != null){
				consulta += " and c.imovel.localidade.unidadeNegocio.id = :unidadeNegocio";
				parameters.put("unidadeNegocio", unidadeNegocio);
			}

			if(gerencia != null){
				consulta += " and c.imovel.localidade.gerenciaRegional.id = :gerenciaRegional";
				parameters.put("gerenciaRegional", gerencia);
			}

			if(localidadeInicial != null){
				consulta += " and c.imovel.localidade.id between :localidadeInicial and :localidadeFinal";
				parameters.put("localidadeInicial", localidadeInicial);
				parameters.put("localidadeFinal", localidadeFinal);
			}

			if(setorComercialInicial != null){
				consulta += " and c.imovel.setorComercial.codigo between :setorComercialInicial and :setorComercialFinal ";

				parameters.put("setorComercialInicial", setorComercialInicial);
				parameters.put("setorComercialFinal", setorComercialFinal);
			}

			if(rotaInicial != null){
				consulta += " and  c.imovel.rota.id between :rotaInicial and :rotaFinal ";

				parameters.put("rotaInicial", rotaInicial);
				parameters.put("rotaFinal", rotaFinal);
			}

			if(sequencialRotaInicial != null){
				consulta += " c.imovel.numeroSequencialRota between :sequencialRotaInicial and :sequencialRotaFinal ";

				parameters.put("sequencialRotaInicial", sequencialRotaInicial);
				parameters.put("sequencialRotaFinal", sequencialRotaFinal);
			}

			if(referenciaImoveisFaturasAtrasoInicial != null){
				consulta += " and c.referencia between :referenciaImoveisFaturasAtrasoInicial and :referenciaImoveisFaturasAtrasoFinal ";

				parameters.put("referenciaImoveisFaturasAtrasoInicial", referenciaImoveisFaturasAtrasoInicial);
				parameters.put("referenciaImoveisFaturasAtrasoFinal", referenciaImoveisFaturasAtrasoFinal);
			}

			if(colecaoSituacaoLigacaoAgua != null && colecaoSituacaoLigacaoAgua.size() > 0){
				consulta += " and c.imovel.ligacaoAguaSituacao.id in (:situacaoLigacaoAgua) ";
				parameters.put("situacaoLigacaoAgua", colecaoSituacaoLigacaoAgua);
			}

			// Agrupamos por todas as colunas
			consulta += "  group by " + "   c.imovel.id, " + "   c.imovel.localidade.gerenciaRegional.id, "
							+ "   c.imovel.localidade.gerenciaRegional.nome, " + "   c.imovel.localidade.unidadeNegocio.id, "
							+ "   c.imovel.localidade.unidadeNegocio.nome, " + "   c.imovel.localidade.id, "
							+ "   c.imovel.localidade.descricao, " + "   c.imovel.setorComercial.codigo, "
							+ "   c.imovel.setorComercial.descricao, " + "   c.imovel.quadra.numeroQuadra, " + "   ci.cliente.nome, "
							+ "   c.imovel.ligacaoAguaSituacao.descricao, " + "   c.imovel.ligacaoEsgotoSituacao.descricao, "
							+ "   c.imovel.rota.codigo, " + "   c.imovel.numeroSequencialRota, " + "   c.imovel.lote, "
							+ "   c.imovel.subLote, " + "   c.imovel.setorComercial.id, " + "   c.imovel.rota.id ";

			if(valorFaturasAtrasoInicial != null || quantidadeFaturasAtrasoInicial != null){
				consulta += " having ";

				if(quantidadeFaturasAtrasoInicial != null){
					consulta += "  count(*) between :quantidadeFaturasAtrasoInicial and :quantidadeFaturasAtrasoFinal";

					parameters.put("quantidadeFaturasAtrasoInicial", Long.parseLong(String.valueOf(quantidadeFaturasAtrasoInicial)));
					parameters.put("quantidadeFaturasAtrasoFinal", Long.parseLong(String.valueOf(quantidadeFaturasAtrasoFinal)));
				}

				if(valorFaturasAtrasoInicial != null){
					if(quantidadeFaturasAtrasoInicial != null){
						consulta += " and ";
					}

					consulta += "  sum( c.valorAgua + c.valorEsgoto + c.debitos - c.valorCreditos ) between :valorFaturasAtrasoInicial and :valorFaturasAtrasoFinal";

					parameters.put("valorFaturasAtrasoInicial", valorFaturasAtrasoInicial);
					parameters.put("valorFaturasAtrasoFinal", valorFaturasAtrasoFinal);
				}
			}

			consulta += " order by c.imovel.localidade.gerenciaRegional.id, c.imovel.localidade.unidadeNegocio.id, c.imovel.localidade.id,"
							+ "c.imovel.setorComercial.id,c.imovel.id,c.imovel.rota.codigo,c.imovel.numeroSequencialRota";

			query = session.createQuery(consulta);

			Set set = parameters.keySet();
			Iterator iterMap = set.iterator();
			while(iterMap.hasNext()){
				String key = (String) iterMap.next();
				if(parameters.get(key) instanceof Collection){
					Collection collection = (ArrayList) parameters.get(key);
					query.setParameterList(key, collection);
				}else{
					query.setParameter(key, parameters.get(key));
				}

			}

			retorno = query.list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0726] Gerar Relatório de Imóveis com Faturas em Atraso
	 * Pesquisa o Total Registro
	 * 
	 * @author Rafael Pinto
	 * @date 04/12/2007
	 * @param FiltrarRelatorioImoveisFaturasAtrasoHelper
	 * @return Integer
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarTotalRegistroRelatorioImoveisFaturasAtraso(FiltrarRelatorioImoveisFaturasAtrasoHelper filtro)
					throws ErroRepositorioException{

		Collection retorno = null;

		Integer localidadeInicial = filtro.getLocalidadeInicial();
		Integer localidadeFinal = filtro.getLocalidadeFinal();

		Integer setorComercialInicial = filtro.getSetorComercialInicial();
		Integer setorComercialFinal = filtro.getSetorComercialFinal();

		Integer rotaInicial = filtro.getRotaInicial();
		Integer rotaFinal = filtro.getRotaFinal();

		Integer sequencialRotaInicial = filtro.getSequencialRotalInicial();
		Integer sequencialRotaFinal = filtro.getSequencialRotalFinal();

		Integer unidadeNegocio = filtro.getUnidadeNegocio();
		Integer gerencia = filtro.getGerenciaRegional();

		Integer referenciaImoveisFaturasAtrasoInicial = filtro.getReferenciaFaturasAtrasoInicial();
		Integer referenciaImoveisFaturasAtrasoFinal = filtro.getReferenciaFaturasAtrasoFinal();

		Collection<Integer> colecaoSituacaoLigacaoAgua = filtro.getSituacaoLigacaoAgua();
		Collection<Integer> colecaoCategorias = filtro.getCategorias();

		Integer quantidadeFaturasAtrasoInicial = filtro.getQuantidadeFaturasAtrasoInicial();
		Integer quantidadeFaturasAtrasoFinal = filtro.getQuantidadeFaturasAtrasoFinal();

		BigDecimal valorFaturasAtrasoInicial = filtro.getValorFaturasAtrasoInicial();
		BigDecimal valorFaturasAtrasoFinal = filtro.getValorFaturasAtrasoFinal();

		String consulta = "";
		Query query = null;
		Map parameters = new HashMap();
		Session session = HibernateUtil.getSession();
		try{
			consulta = " select " + " c.imov_id, " + " count(*) as quantidade, "
							+ " sum( c.cnta_vlagua + c.cnta_vlesgoto + c.cnta_vldebitos - c.cnta_vlcreditos ) as valor " + " from "
							+ " Conta c " + " inner join imovel i on c.imov_id = i.imov_id "
							+ " inner join localidade lc on i.loca_id = lc.loca_id "
							+ " inner join ligacao_agua_situacao li on li.last_id = i.last_id " + " where "
							+ "   c.dcst_idatual in ( 0,1,2 ) ";

			if(unidadeNegocio != null){
				consulta += " and lc.uneg_id = :unidadeNegocio";
				parameters.put("unidadeNegocio", unidadeNegocio);
			}

			if(gerencia != null){
				consulta += " and lc.greg_id = :gerenciaRegional";
				parameters.put("gerenciaRegional", gerencia);
			}

			if(localidadeInicial != null){
				consulta += " and lc.loca_id between :localidadeInicial and :localidadeFinal";
				parameters.put("localidadeInicial", localidadeInicial);
				parameters.put("localidadeFinal", localidadeFinal);
			}

			if(setorComercialInicial != null){
				consulta += " and i.stcm_cdsetorcomercial between :setorComercialInicial and :setorComercialFinal ";

				parameters.put("setorComercialInicial", setorComercialInicial);
				parameters.put("setorComercialFinal", setorComercialFinal);
			}

			if(rotaInicial != null){
				consulta += " and i.rota_id between :rotaInicial and :rotaFinal ";

				parameters.put("rotaInicial", rotaInicial);
				parameters.put("rotaFinal", rotaFinal);
			}

			if(sequencialRotaInicial != null){
				consulta += " i.imov_nnsequencialrota between :sequencialRotaInicial and :sequencialRotaFinal ";

				parameters.put("sequencialRotaInicial", sequencialRotaInicial);
				parameters.put("sequencialRotaFinal", sequencialRotaFinal);
			}

			if(referenciaImoveisFaturasAtrasoInicial != null){
				consulta += " and c.cnta_amreferenciaconta between :referenciaImoveisFaturasAtrasoInicial and :referenciaImoveisFaturasAtrasoFinal ";

				parameters.put("referenciaImoveisFaturasAtrasoInicial", referenciaImoveisFaturasAtrasoInicial);
				parameters.put("referenciaImoveisFaturasAtrasoFinal", referenciaImoveisFaturasAtrasoFinal);
			}

			if(colecaoSituacaoLigacaoAgua != null && colecaoSituacaoLigacaoAgua.size() > 0){
				consulta += " and li.last_id in (:situacaoLigacaoAgua) ";
				parameters.put("situacaoLigacaoAgua", colecaoSituacaoLigacaoAgua);
			}

			if(colecaoCategorias != null && colecaoCategorias.size() > 0){
				consulta += " and (select catg_id from  " + " ( select cc.catg_id, sum( ctcg_qteconomia ) as soma "
								+ " from conta_categoria cc, conta c where cc.cnta_id = c.cnta_id group by cc.catg_id ) "
								+ " where soma =  (select max(soma) from " + " (select cc.catg_id, sum(ctcg_qteconomia) as soma "
								+ " from conta_categoria cc, conta c where cc.cnta_id = c.cnta_id group by cc.catg_id ) " + " )"
								+ " ) in ( :categorias )  ";

				parameters.put("categorias", colecaoCategorias);
			}

			consulta += " group by " + "   c.imov_id ";

			if(valorFaturasAtrasoInicial != null || quantidadeFaturasAtrasoInicial != null){
				consulta += " having ";

				if(quantidadeFaturasAtrasoInicial != null){
					consulta += "  count(*) between :quantidadeFaturasAtrasoInicial and :quantidadeFaturasAtrasoFinal";

					parameters.put("quantidadeFaturasAtrasoInicial", Long.parseLong(String.valueOf(quantidadeFaturasAtrasoInicial)));
					parameters.put("quantidadeFaturasAtrasoFinal", Long.parseLong(String.valueOf(quantidadeFaturasAtrasoFinal)));
				}

				if(valorFaturasAtrasoInicial != null){
					if(quantidadeFaturasAtrasoInicial != null){
						consulta += " and ";
					}

					consulta += "  sum( c.cnta_vlagua + c.cnta_vlesgoto + c.cnta_vldebitos - c.cnta_vlcreditos)  between :valorFaturasAtrasoInicial and :valorFaturasAtrasoFinal";

					parameters.put("valorFaturasAtrasoInicial", valorFaturasAtrasoInicial);
					parameters.put("valorFaturasAtrasoFinal", valorFaturasAtrasoFinal);
				}
			}

			query = session.createSQLQuery(consulta);

			Set set = parameters.keySet();
			Iterator iterMap = set.iterator();
			while(iterMap.hasNext()){
				String key = (String) iterMap.next();
				if(parameters.get(key) instanceof Collection){
					Collection collection = (ArrayList) parameters.get(key);
					query.setParameterList(key, collection);
				}else{
					query.setParameter(key, parameters.get(key));
				}

			}

			retorno = query.list();
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0727] Gerar Relatório de Imóveis por Consumo Medio
	 * 
	 * @author Bruno Barros
	 * @date 17/12/2007
	 * @param FiltrarRelatorioImoveisConsumoMedio
	 * @return Collection<FiltrarRelatorioImoveisConsumoMedio[]>
	 * @throws ErroRepositorioException
	 */
	public Collection<RelatorioImoveisConsumoMedioHelper> pesquisarRelatorioImoveisConsumoMedio(
					FiltrarRelatorioImoveisConsumoMedioHelper filtro) throws ErroRepositorioException{

		Collection retorno = null;

		Integer referencia = filtro.getReferencia();

		Integer localidadeInicial = filtro.getLocalidadeInicial();
		Integer localidadeFinal = filtro.getLocalidadeFinal();

		Integer setorComercialInicial = filtro.getSetorComercialInicial();
		Integer setorComercialFinal = filtro.getSetorComercialFinal();

		Integer rotaInicial = filtro.getRotaInicial();
		Integer rotaFinal = filtro.getRotaFinal();

		Integer sequencialRotaInicial = filtro.getSequencialRotalInicial();
		Integer sequencialRotaFinal = filtro.getSequencialRotalFinal();

		Integer unidadeNegocio = filtro.getUnidadeNegocio();
		Integer gerencia = filtro.getGerenciaRegional();

		Integer consumoMedioAguaInicial = filtro.getConsumoMedioAguaInicial();
		Integer consumoMedioAguaFinal = filtro.getConsumoMedioAguaFinal();

		Integer consumoMedioEsgotoInicial = filtro.getConsumoMedioEsgotoInicial();
		Integer consumoMedioEsgotoFinal = filtro.getConsumoMedioEsgotoFinal();

		String consulta = "";
		Query query = null;
		Map parameters = new HashMap();

		Session session = HibernateUtil.getSession();

		try{

			consulta = " select "
							+ "   gr.id, "
							+ // 0
							"   gr.nome, "
							+ // 1
							"   un.id, "
							+ // 2
							"   un.nome, "
							+ // 3
							"   loc.id, "
							+ // 4
							"   loc.descricao, "
							+ // 5
							"   sc.codigo, "
							+ // 6
							"   sc.descricao, "
							+ // 7
							"   to_char(gr.id) || '/' || to_char(loc.id) || '/' || to_char(sc.id) || '/' || to_char(qua.id) || '/' || substr(to_char(imo.id), length(imo.id) -1, 1) as inscricao, "
							+ // 8
							"   cli.nome, "
							+ // 9
							"   las.descricao, "
							+ // 10
							"   cha.consumoMedio, "
							+ // 11
							"   rot.codigo,"
							+ // 12
							"   imo.numeroSequencialRota, "
							+ // 13
							"   imo.id, "
							+ // 14
							"   case when ( logr.nome != null ) then "
							+ "     trim( logr.nome ) "
							+ "   end || "
							+ "   case when ( imo.numeroImovel != null ) then "
							+ "     concat( ', ', trim( imo.numeroImovel ) ) "
							+ "   end || \n"
							+ "   case when ( bairro.nome != null ) then "
							+ "     concat( ' - ', trim( bairro.nome ) ) "
							+ "   end as endereco, "
							+ // 15
							"   les.descricao, "
							+ // 16
							"   che.consumoMedio, "
							+ // 17
							"   imo.lote, "
							+ // 18
							"   imo.subLote, "
							+ // 19
							"   qua.numeroQuadra "
							+ // 20
							"from ClienteImovel ci inner join ci.imovel imo "
							+ "inner join imo.localidade loc inner join loc.gerenciaRegional gr "
							+ "inner join loc.unidadeNegocio un inner join imo.setorComercial sc "
							+ "inner join imo.quadra qua inner join imo.rota rot inner join ci.cliente cli "
							+ "inner join ci.clienteRelacaoTipo crt with ( crt.id = 2 ) "
							+ "inner join imo.ligacaoAguaSituacao las inner join imo.ligacaoEsgotoSituacao les "
							+ "left join imo.logradouroCep logradouroCep left join logradouroCep.logradouro logr "
							+ "left join imo.logradouroBairro logradouroBairro left join logradouroBairro.bairro bairro, "
							+ "ConsumoHistorico cha, ConsumoHistorico che where "
							+ "ci.dataFimRelacao is null and cha.imovel.id = imo.id and "
 + "cha.ligacaoTipo.id = 1 and "
							+ "che.imovel.id = imo.id and che.ligacaoTipo.id = 2 ";

			if(referencia != null){
				consulta += " and cha.referenciaFaturamento = :referencia and che.referenciaFaturamento = :referencia ";
				parameters.put("referencia", referencia);
			}

			if(unidadeNegocio != null){
				consulta += " and un.id = :unidadeNegocio";
				parameters.put("unidadeNegocio", unidadeNegocio);
			}

			if(gerencia != null){
				consulta += " and gr.id = :gerenciaRegional";
				parameters.put("gerenciaRegional", gerencia);
			}

			if(localidadeInicial != null){
				consulta += " and loc.id between :localidadeInicial and :localidadeFinal";
				parameters.put("localidadeInicial", localidadeInicial);
				parameters.put("localidadeFinal", localidadeFinal);
			}

			if(setorComercialInicial != null){
				consulta += " and sc.codigo between :setorComercialInicial and :setorComercialFinal";

				parameters.put("setorComercialInicial", setorComercialInicial);
				parameters.put("setorComercialFinal", setorComercialFinal);
			}

			if(rotaInicial != null){
				consulta += " and  rot.id between :rotaInicial and :rotaFinal";

				parameters.put("rotaInicial", rotaInicial);
				parameters.put("rotaFinal", rotaFinal);
			}

			if(sequencialRotaInicial != null){
				consulta += " and imo.numeroSequencialRota between :sequencialRotaInicial and :sequencialRotaFinal";

				parameters.put("sequencialRotaInicial", sequencialRotaInicial);
				parameters.put("sequencialRotaFinal", sequencialRotaFinal);
			}

			if(consumoMedioAguaInicial != null){
				consulta += " and cha.consumoMedio between :consumoMedioAguaInicial and :consumoMedioAguaFinal";

				parameters.put("consumoMedioAguaInicial", consumoMedioAguaInicial);
				parameters.put("consumoMedioAguaFinal", consumoMedioAguaFinal);
			}

			if(consumoMedioEsgotoInicial != null){
				consulta += " and che.consumoMedio between :consumoMedioEsgotoInicial and :consumoMedioEsgotoFinal";

				parameters.put("consumoMedioEsgotoInicial", consumoMedioEsgotoInicial);
				parameters.put("consumoMedioEsgotoFinal", consumoMedioEsgotoFinal);
			}

			// Ordenamos
			consulta += " order by "
							+ "gr.id, "
							+ "un.id, "
							+ "loc.id, "
							+ "sc.id, "
							+ "to_char(gr.id) || '/' || to_char(loc.id) || '/' || to_char(sc.id) || '/' || to_char(qua.id) || '/' || substr(to_char(imo.id), length(imo.id) -1, 1), "
							+ "rot.id, imo.numeroSequencialRota ";

			query = session.createQuery(consulta);

			Set set = parameters.keySet();
			Iterator iterMap = set.iterator();
			while(iterMap.hasNext()){
				String key = (String) iterMap.next();
				if(parameters.get(key) instanceof Collection){
					Collection collection = (ArrayList) parameters.get(key);
					query.setParameterList(key, collection);
				}else{
					query.setParameter(key, parameters.get(key));
				}

			}

			retorno = query.list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0727] Gerar Relatório de Imóveis por Consumo Medio
	 * Pesquisa a quantidade de imoveis para o relatorio de imoveis por consumo medio
	 * 
	 * @author Bruno Barros
	 * @data 17/12/2007
	 * @param filtro
	 * @return Integer
	 * @throws ControladorException
	 */
	public Integer pesquisarTotalRegistroRelatorioImoveisConsumoMedio(FiltrarRelatorioImoveisConsumoMedioHelper filtro)
					throws ErroRepositorioException{

		Integer retorno = null;

		Integer referencia = filtro.getReferencia();

		Integer localidadeInicial = filtro.getLocalidadeInicial();
		Integer localidadeFinal = filtro.getLocalidadeFinal();

		Integer setorComercialInicial = filtro.getSetorComercialInicial();
		Integer setorComercialFinal = filtro.getSetorComercialFinal();

		Integer rotaInicial = filtro.getRotaInicial();
		Integer rotaFinal = filtro.getRotaFinal();

		Integer sequencialRotaInicial = filtro.getSequencialRotalInicial();
		Integer sequencialRotaFinal = filtro.getSequencialRotalFinal();

		Integer unidadeNegocio = filtro.getUnidadeNegocio();
		Integer gerencia = filtro.getGerenciaRegional();

		Integer consumoMedioAguaInicial = filtro.getConsumoMedioAguaInicial();
		Integer consumoMedioAguaFinal = filtro.getConsumoMedioAguaFinal();

		Integer consumoMedioEsgotoInicial = filtro.getConsumoMedioEsgotoInicial();
		Integer consumoMedioEsgotoFinal = filtro.getConsumoMedioEsgotoFinal();

		String consulta = "";
		Query query = null;
		Map parameters = new HashMap();
		Session session = HibernateUtil.getSession();
		try{

			consulta = " select count(*) from ClienteImovel ci inner join ci.imovel imo "
							+ "inner join imo.localidade loc inner join loc.gerenciaRegional gr "
							+ "inner join loc.unidadeNegocio un inner join imo.setorComercial sc "
							+ "inner join imo.quadra qua inner join imo.rota rot inner join ci.cliente cli "
							+ "inner join ci.clienteRelacaoTipo crt with ( crt.id = 2 ) "
							+ "inner join imo.ligacaoAguaSituacao las inner join imo.ligacaoEsgotoSituacao les "
							+ "left join imo.logradouroCep logradouroCep left join logradouroCep.logradouro logr "
							+ "left join imo.logradouroBairro logradouroBairro left join logradouroBairro.bairro bairro, "
							+ "ConsumoHistorico cha, ConsumoHistorico che where "
							+ "ci.dataFimRelacao is null and cha.imovel.id = imo.id and "
 + "cha.ligacaoTipo.id = 1 and "
							+ "che.imovel.id = imo.id and che.ligacaoTipo.id = 2 ";

			if(referencia != null){
				consulta += " and cha.referenciaFaturamento = :referencia and che.referenciaFaturamento = :referencia ";
				parameters.put("referencia", referencia);
			}

			if(unidadeNegocio != null){
				consulta += " and un.id = :unidadeNegocio";
				parameters.put("unidadeNegocio", unidadeNegocio);
			}

			if(gerencia != null){
				consulta += " and gr.id = :gerenciaRegional";
				parameters.put("gerenciaRegional", gerencia);
			}

			if(localidadeInicial != null){
				consulta += " and loc.id between :localidadeInicial and :localidadeFinal";
				parameters.put("localidadeInicial", localidadeInicial);
				parameters.put("localidadeFinal", localidadeFinal);
			}

			if(setorComercialInicial != null){
				consulta += " and sc.id between :setorComercialInicial and :setorComercialFinal";

				parameters.put("setorComercialInicial", setorComercialInicial);
				parameters.put("setorComercialFinal", setorComercialFinal);
			}

			if(rotaInicial != null){
				consulta += " and rot.id between :rotaInicial and :rotaFinal";

				parameters.put("rotaInicial", rotaInicial);
				parameters.put("rotaFinal", rotaFinal);
			}

			if(sequencialRotaInicial != null){
				consulta += " and imo.numeroSequencialRota between :sequencialRotaInicial and :sequencialRotaFinal";

				parameters.put("sequencialRotaInicial", sequencialRotaInicial);
				parameters.put("sequencialRotaFinal", sequencialRotaFinal);
			}

			if(consumoMedioAguaInicial != null){
				consulta += " and cha.consumoMedio between :consumoMedioAguaInicial and :consumoMedioAguaFinal";

				parameters.put("consumoMedioAguaInicial", consumoMedioAguaInicial);
				parameters.put("consumoMedioAguaFinal", consumoMedioAguaFinal);
			}

			if(consumoMedioEsgotoInicial != null){
				consulta += " and che.consumoMedio between :consumoMedioEsgotoInicial and :consumoMedioEsgotoFinal";

				parameters.put("consumoMedioEsgotoInicial", consumoMedioEsgotoInicial);
				parameters.put("consumoMedioEsgotoFinal", consumoMedioEsgotoFinal);
			}

			query = session.createQuery(consulta);

			Set set = parameters.keySet();
			Iterator iterMap = set.iterator();
			while(iterMap.hasNext()){
				String key = (String) iterMap.next();
				if(parameters.get(key) instanceof Collection){
					Collection collection = (ArrayList) parameters.get(key);
					query.setParameterList(key, collection);
				}else{
					query.setParameter(key, parameters.get(key));
				}

			}

			retorno = ((Number) query.uniqueResult()).intValue();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0731] Gerar Relatório de Imóveis com os Ultimos Consumos de Agua
	 * 
	 * @author Rafael Pinto
	 * @date 18/12/2007
	 * @param FiltrarRelatorioImoveisUltimosConsumosAguaHelper
	 * @return Collection<Object[]>
	 * @throws ErroRepositorioException
	 */
	public Collection<Object[]> pesquisarRelatorioImoveisUltimosConsumosAgua(FiltrarRelatorioImoveisUltimosConsumosAguaHelper filtro)
					throws ErroRepositorioException{

		Collection retorno = null;

		Integer localidadeInicial = filtro.getLocalidadeInicial();
		Integer localidadeFinal = filtro.getLocalidadeFinal();

		Integer setorComercialInicial = filtro.getSetorComercialInicial();
		Integer setorComercialFinal = filtro.getSetorComercialFinal();

		Integer rotaInicial = filtro.getRotaInicial();
		Integer rotaFinal = filtro.getRotaFinal();

		Integer sequencialRotaInicial = filtro.getSequencialRotalInicial();
		Integer sequencialRotaFinal = filtro.getSequencialRotalFinal();

		Integer unidadeNegocio = filtro.getUnidadeNegocio();
		Integer gerencia = filtro.getGerenciaRegional();

		Collection<Integer> colecaoSituacaoLigacaoAgua = filtro.getSituacaoLigacaoAgua();
		Collection<Integer> colecaoCategorias = filtro.getCategorias();

		String consulta = "";
		Query query = null;
		Map parameters = new HashMap();

		Session session = HibernateUtil.getSession();

		try{
			consulta = "select imovel.id, " // 0
							+ " gerenciaRegional.id, " // 1
							+ " gerenciaRegional.nome, "// 2
							+ " unidadeNegocio.id," // 3
							+ " unidadeNegocio.nome," // 4
							+ " localidade.id, " // 5
							+ " localidade.descricao, " // 6
							+ " setorComercial.codigo, "// 7
							+ " setorComercial.descricao, "// 8
							+ " quadra.numeroQuadra, " // 9
							+ " cliente.nome, " // 10
							+ " ligacaoAguaSituacao.descricao, " // 11
							+ " ligacaoEsgotoSituacao.descricao, " // 12
							+ " rota.codigo," // 13
							+ " imovel.numeroSequencialRota, " // 14
							+ " imovel.lote, " // 15
							+ " imovel.subLote, " // 16
							+ " setorComercial.id, "// 17
							+ " rota.id, " // 18
							+ " imovel.quantidadeEconomias " // 19
							+ " from ClienteImovel clienteImovel,"
							+ " ImovelSubcategoria imovelSubcateg"
							+ " left join imovelSubcateg.comp_id.subcategoria subcateg"
							+ " left join subcateg.categoria categ"
							+ " left join imovelSubcateg.comp_id.imovel imovelCateg"
							+ " inner join clienteImovel.clienteRelacaoTipo relacaoTipo"
							+ " inner join clienteImovel.imovel imovel "
							+ " inner join imovel.localidade localidade "
							+ " inner join imovel.setorComercial setorComercial "
							+ " inner join imovel.quadra quadra "
							+ " inner join imovel.rota rota"
							+ " inner join clienteImovel.cliente cliente"
							+ " inner join localidade.unidadeNegocio unidadeNegocio"
							+ " inner join localidade.gerenciaRegional gerenciaRegional"
							+ " inner join imovel.ligacaoAguaSituacao ligacaoAguaSituacao"
							+ " left join imovel.ligacaoEsgotoSituacao ligacaoEsgotoSituacao"
							+ " where imovelCateg.id = clienteImovel.imovel.id "
							+ " and relacaoTipo.id = :idRelacaoTipo"
							+ " and clienteImovel.dataFimRelacao is null ";

			if(colecaoSituacaoLigacaoAgua != null && colecaoSituacaoLigacaoAgua.size() > 0){
				consulta = consulta + " and ligacaoAguaSituacao.id in (:situacaoAgua) ";
				parameters.put("situacaoAgua", colecaoSituacaoLigacaoAgua);
			}

			parameters.put("idRelacaoTipo", ClienteRelacaoTipo.USUARIO);

			if(colecaoCategorias != null && colecaoCategorias.size() > 0){
				consulta = consulta + " and categ.id in (:colecaoCategorias) ";
				parameters.put("colecaoCategorias", colecaoCategorias);
			}

			if(unidadeNegocio != null){
				consulta = consulta + " and unidadeNegocio.id in (:unidade) ";
				parameters.put("unidade", unidadeNegocio);
			}

			if(gerencia != null){
				consulta = consulta + " and gerenciaRegional.id in (:gerencia) ";
				parameters.put("gerencia", gerencia);
			}

			if(localidadeInicial != null){
				consulta = consulta + " and localidade.id between " + localidadeInicial + " and " + localidadeFinal;
			}

			if(setorComercialInicial != null){
				consulta = consulta + " and setorComercial.codigo between " + setorComercialInicial + " and " + setorComercialFinal;
			}

			if(rotaInicial != null){
				consulta = consulta + " and rota.codigo between " + rotaInicial + " and " + rotaFinal;
			}

			if(sequencialRotaInicial != null){
				consulta = consulta + " and imovel.numeroSequencialRota between " + sequencialRotaInicial + " and " + sequencialRotaFinal;
			}

			consulta += " order by gerenciaRegional.id,unidadeNegocio.id,localidade.id,"
							+ "setorComercial.id,imovel.id,rota.codigo,imovel.numeroSequencialRota";

			query = session.createQuery(consulta);

			Set set = parameters.keySet();
			Iterator iterMap = set.iterator();
			while(iterMap.hasNext()){
				String key = (String) iterMap.next();
				if(parameters.get(key) instanceof Collection){
					Collection collection = (ArrayList) parameters.get(key);
					query.setParameterList(key, collection);
				}else{
					query.setParameter(key, parameters.get(key));
				}

			}

			retorno = query.list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0731] Gerar Relatório de Imóveis com os Ultimos Consumos de Agua
	 * 
	 * @author Rafael Pinto
	 * @date 18/12/2007
	 * @param FiltrarRelatorioImoveisUltimosConsumosAguaHelper
	 * @return Integer
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarTotalRegistroRelatorioImoveisUltimosConsumosAgua(FiltrarRelatorioImoveisUltimosConsumosAguaHelper filtro)
					throws ErroRepositorioException{

		Integer retorno = null;

		Integer localidadeInicial = filtro.getLocalidadeInicial();
		Integer localidadeFinal = filtro.getLocalidadeFinal();

		Integer setorComercialInicial = filtro.getSetorComercialInicial();
		Integer setorComercialFinal = filtro.getSetorComercialFinal();

		Integer rotaInicial = filtro.getRotaInicial();
		Integer rotaFinal = filtro.getRotaFinal();

		Integer sequencialRotaInicial = filtro.getSequencialRotalInicial();
		Integer sequencialRotaFinal = filtro.getSequencialRotalFinal();

		Integer unidadeNegocio = filtro.getUnidadeNegocio();
		Integer gerencia = filtro.getGerenciaRegional();

		Collection<Integer> colecaoSituacaoLigacaoAgua = filtro.getSituacaoLigacaoAgua();
		Collection<Integer> colecaoCategorias = filtro.getCategorias();

		String consulta = "";
		Query query = null;
		Map parameters = new HashMap();
		Session session = HibernateUtil.getSession();
		try{
			consulta = "select count(*) " + " from ClienteImovel clienteImovel," + " ImovelSubcategoria imovelSubcateg"
							+ " left join imovelSubcateg.comp_id.subcategoria subcateg" + " left join subcateg.categoria categ"
							+ " left join imovelSubcateg.comp_id.imovel imovelCateg"
							+ " inner join clienteImovel.clienteRelacaoTipo relacaoTipo" + " inner join clienteImovel.imovel imovel "
							+ " inner join imovel.localidade localidade " + " inner join imovel.setorComercial setorComercial "
							+ " inner join imovel.quadra quadra " + " inner join imovel.rota rota"
							+ " inner join clienteImovel.cliente cliente" + " inner join localidade.unidadeNegocio unidadeNegocio"
							+ " inner join localidade.gerenciaRegional gerenciaRegional"
							+ " inner join imovel.ligacaoAguaSituacao ligacaoAguaSituacao"
							+ " left join imovel.ligacaoEsgotoSituacao ligacaoEsgotoSituacao"
							+ " where imovelCateg.id = clienteImovel.imovel.id " + " and relacaoTipo.id = :idRelacaoTipo"
							+ " and clienteImovel.dataFimRelacao is null ";

			if(colecaoSituacaoLigacaoAgua != null && colecaoSituacaoLigacaoAgua.size() > 0){
				consulta = consulta + " and ligacaoAguaSituacao.id in (:situacaoAgua) ";
				parameters.put("situacaoAgua", colecaoSituacaoLigacaoAgua);
			}

			parameters.put("idRelacaoTipo", ClienteRelacaoTipo.USUARIO);

			if(colecaoCategorias != null && colecaoCategorias.size() > 0){
				consulta = consulta + " and categ.id in (:colecaoCategorias) ";
				parameters.put("colecaoCategorias", colecaoCategorias);
			}

			if(unidadeNegocio != null){
				consulta = consulta + " and unidadeNegocio.id in (:unidade) ";
				parameters.put("unidade", unidadeNegocio);
			}

			if(gerencia != null){
				consulta = consulta + " and gerenciaRegional.id in (:gerencia) ";
				parameters.put("gerencia", gerencia);
			}

			if(localidadeInicial != null){
				consulta = consulta + " and localidade.id between " + localidadeInicial + " and " + localidadeFinal;
			}

			if(setorComercialInicial != null){
				consulta = consulta + " and setorComercial.codigo between " + setorComercialInicial + " and " + setorComercialFinal;
			}

			if(rotaInicial != null){
				consulta = consulta + " and rota.codigo between " + rotaInicial + " and " + rotaFinal;
			}

			if(sequencialRotaInicial != null){
				consulta = consulta + " and imovel.numeroSequencialRota between " + sequencialRotaInicial + " and " + sequencialRotaFinal;
			}

			query = session.createQuery(consulta);

			Set set = parameters.keySet();
			Iterator iterMap = set.iterator();
			while(iterMap.hasNext()){
				String key = (String) iterMap.next();
				if(parameters.get(key) instanceof Collection){
					Collection collection = (ArrayList) parameters.get(key);
					query.setParameterList(key, collection);
				}else{
					query.setParameter(key, parameters.get(key));
				}

			}

			retorno = ((Number) query.uniqueResult()).intValue();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC00728] Gerar Relatório de Imóveis Ativos e Não Medidos
	 * 
	 * @author Rafael Pinto
	 * @date 03/01/2008
	 * @param FiltrarRelatorioImoveisAtivosNaoMedidosHelper
	 * @return Collection<Object[]>
	 * @throws ErroRepositorioException
	 */
	public Collection<Object[]> pesquisarRelatorioImoveisAtivosNaoMedidos(FiltrarRelatorioImoveisAtivosNaoMedidosHelper filtro)
					throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();
		try{
			Integer localidadeInicial = filtro.getLocalidadeInicial();
			Integer localidadeFinal = filtro.getLocalidadeFinal();

			Integer setorComercialInicial = filtro.getSetorComercialInicial();
			Integer setorComercialFinal = filtro.getSetorComercialFinal();

			Integer rotaInicial = filtro.getRotaInicial();
			Integer rotaFinal = filtro.getRotaFinal();

			Integer sequencialRotaInicial = filtro.getSequencialRotalInicial();
			Integer sequencialRotaFinal = filtro.getSequencialRotalFinal();

			Integer unidadeNegocio = filtro.getUnidadeNegocio();
			Integer gerencia = filtro.getGerenciaRegional();

			Integer[] idsSituacaoAguaImovelAtivo = filtro.getIdsSituacaoAguaImovelAtivo();

			String consulta = "";
			Query query = null;
			Map parameters = new HashMap();

			consulta = "select imovel.id, " // 0
							+ " gerenciaRegional.id, " // 1
							+ " gerenciaRegional.nome, "// 2
							+ " unidadeNegocio.id," // 3
							+ " unidadeNegocio.nome," // 4
							+ " localidade.id, " // 5
							+ " localidade.descricao, " // 6
							+ " setorComercial.codigo, "// 7
							+ " setorComercial.descricao, "// 8
							+ " quadra.numeroQuadra, " // 9
							+ " cliente.nome, " // 10
							+ " ligacaoAguaSituacao.descricao, " // 11
							+ " ligacaoEsgotoSituacao.descricao, " // 12
							+ " rota.codigo," // 13
							+ " imovel.numeroSequencialRota, " // 14
							+ " imovel.lote, " // 15
							+ " imovel.subLote, " // 16
							+ " setorComercial.id, "// 17
							+ " rota.id " // 18
							+ " from ClienteImovel clienteImovel "
							+ " inner join clienteImovel.clienteRelacaoTipo relacaoTipo"
							+ " inner join clienteImovel.imovel imovel "
							+ " inner join imovel.localidade localidade "
							// ------UC0728
							+ " inner join imovel.ligacaoAgua ligacaoagua"
							// ------
							+ " inner join imovel.setorComercial setorComercial "
							+ " inner join imovel.quadra quadra "
							+ " inner join imovel.rota rota"
							+ " inner join clienteImovel.cliente cliente"
							+ " inner join localidade.unidadeNegocio unidadeNegocio"
							+ " inner join localidade.gerenciaRegional gerenciaRegional"
							+ " inner join imovel.ligacaoAguaSituacao ligacaoAguaSituacao"
							+ " left join imovel.ligacaoEsgotoSituacao ligacaoEsgotoSituacao"
							+ " where ligacaoagua.hidrometroInstalacaoHistorico is null"
							+ " and relacaoTipo.id = :idRelacaoTipo"
							+ " and clienteImovel.dataFimRelacao is null ";

			if(!Util.isVazioOrNulo(idsSituacaoAguaImovelAtivo)){
				consulta = consulta + " and ligacaoAguaSituacao.id in (:situacaoAgua) ";
				parameters.put("situacaoAgua", idsSituacaoAguaImovelAtivo);
			}

			parameters.put("idRelacaoTipo", ClienteRelacaoTipo.USUARIO);

			if(unidadeNegocio != null){
				consulta = consulta + " and unidadeNegocio.id in (:unidade) ";
				parameters.put("unidade", unidadeNegocio);
			}

			if(gerencia != null){
				consulta = consulta + " and gerenciaRegional.id in (:gerencia) ";
				parameters.put("gerencia", gerencia);
			}

			if(localidadeInicial != null){
				consulta = consulta + " and localidade.id between " + localidadeInicial + " and " + localidadeFinal;
			}

			if(setorComercialInicial != null){
				consulta = consulta + " and setorComercial.codigo between " + setorComercialInicial + " and " + setorComercialFinal;
			}

			if(rotaInicial != null){
				consulta = consulta + " and rota.codigo between " + rotaInicial + " and " + rotaFinal;
			}

			if(sequencialRotaInicial != null){
				consulta = consulta + " and imovel.numeroSequencialRota between " + sequencialRotaInicial + " and " + sequencialRotaFinal;
			}

			consulta += " order by gerenciaRegional.id,unidadeNegocio.id,localidade.id,"
							+ "setorComercial.id,imovel.id,rota.codigo,imovel.numeroSequencialRota";

			query = session.createQuery(consulta);

			Set set = parameters.keySet();
			Iterator iterMap = set.iterator();
			while(iterMap.hasNext()){
				String key = (String) iterMap.next();
				if(parameters.get(key) instanceof Collection){
					Collection collection = (ArrayList) parameters.get(key);
					query.setParameterList(key, collection);
				}else if(parameters.get(key) instanceof Integer[]){
					Integer[] collection = (Integer[]) parameters.get(key);
					query.setParameterList(key, collection);
				}else{
					query.setParameter(key, parameters.get(key));
				}

			}

			retorno = query.list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC00728] Gerar Relatório de Imóveis Ativos e Não Medidos
	 * 
	 * @author Rafael Pinto
	 * @date 03/01/2008
	 * @param FiltrarRelatorioImoveisAtivosNaoMedidosHelper
	 * @return Collection<Object[]>
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarTotalRegistroRelatorioImoveisAtivosNaoMedidos(FiltrarRelatorioImoveisAtivosNaoMedidosHelper filtro)
					throws ErroRepositorioException{

		Integer retorno = null;

		Integer localidadeInicial = filtro.getLocalidadeInicial();
		Integer localidadeFinal = filtro.getLocalidadeFinal();

		Integer setorComercialInicial = filtro.getSetorComercialInicial();
		Integer setorComercialFinal = filtro.getSetorComercialFinal();

		Integer rotaInicial = filtro.getRotaInicial();
		Integer rotaFinal = filtro.getRotaFinal();

		Integer sequencialRotaInicial = filtro.getSequencialRotalInicial();
		Integer sequencialRotaFinal = filtro.getSequencialRotalFinal();

		Integer unidadeNegocio = filtro.getUnidadeNegocio();
		Integer gerencia = filtro.getGerenciaRegional();

		Integer[] idsSituacaoAguaImovelAtivo = filtro.getIdsSituacaoAguaImovelAtivo();

		String consulta = "";
		Query query = null;
		Map parameters = new HashMap();
		Session session = HibernateUtil.getSession();
		try{
			consulta = "select count(*) " + " from ClienteImovel clienteImovel "
							+ " inner join clienteImovel.clienteRelacaoTipo relacaoTipo" + " inner join clienteImovel.imovel imovel "
							+ " inner join imovel.localidade localidade " + " inner join imovel.setorComercial setorComercial "
							+ " inner join imovel.quadra quadra " + " inner join imovel.rota rota"
							+ " inner join clienteImovel.cliente cliente" + " inner join localidade.unidadeNegocio unidadeNegocio"
							+ " inner join localidade.gerenciaRegional gerenciaRegional"
							+ " inner join imovel.ligacaoAguaSituacao ligacaoAguaSituacao"
							+ " left join imovel.ligacaoEsgotoSituacao ligacaoEsgotoSituacao"
							+ " where imovel.hidrometroInstalacaoHistorico is null" + " and relacaoTipo.id = :idRelacaoTipo"
							+ " and clienteImovel.dataFimRelacao is null ";

			if(!Util.isVazioOrNulo(idsSituacaoAguaImovelAtivo)){
				consulta = consulta + " and ligacaoAguaSituacao.id in (:situacaoAgua) ";
				parameters.put("situacaoAgua", idsSituacaoAguaImovelAtivo);
			}

			parameters.put("idRelacaoTipo", ClienteRelacaoTipo.USUARIO);

			if(unidadeNegocio != null){
				consulta = consulta + " and unidadeNegocio.id in (:unidade) ";
				parameters.put("unidade", unidadeNegocio);
			}

			if(gerencia != null){
				consulta = consulta + " and gerenciaRegional.id in (:gerencia) ";
				parameters.put("gerencia", gerencia);
			}

			if(localidadeInicial != null){
				consulta = consulta + " and localidade.id between " + localidadeInicial + " and " + localidadeFinal;
			}

			if(setorComercialInicial != null){
				consulta = consulta + " and setorComercial.codigo between " + setorComercialInicial + " and " + setorComercialFinal;
			}

			if(rotaInicial != null){
				consulta = consulta + " and rota.codigo between " + rotaInicial + " and " + rotaFinal;
			}

			if(sequencialRotaInicial != null){
				consulta = consulta + " and imovel.numeroSequencialRota between " + sequencialRotaInicial + " and " + sequencialRotaFinal;
			}

			query = session.createQuery(consulta);

			Set set = parameters.keySet();
			Iterator iterMap = set.iterator();
			while(iterMap.hasNext()){
				String key = (String) iterMap.next();
				if(parameters.get(key) instanceof Collection){
					Collection collection = (ArrayList) parameters.get(key);
					query.setParameterList(key, collection);
				}else if(parameters.get(key) instanceof Integer[]){
					Integer[] collection = (Integer[]) parameters.get(key);
					query.setParameterList(key, collection);
				}else{
					query.setParameter(key, parameters.get(key));
				}

			}

			retorno = ((Number) query.uniqueResult()).intValue();
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC00728] Gerar Relatório de Imóveis Excluidos
	 * 
	 * @author Alcira Rocha
	 * @date 03/01/2008
	 * @param FiltrarRelatorioImoveisExcluidosHelper
	 * @return Collection<Object[]>
	 * @throws ErroRepositorioException
	 */
	public Collection<Object[]> pesquisarRelatorioImoveisExcluidos(FiltrarRelatorioImoveisExcluidosHelper filtro)
					throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();
		try{
			Integer localidadeInicial = filtro.getLocalidadeInicial();
			Integer localidadeFinal = filtro.getLocalidadeFinal();

			Integer setorComercialInicial = filtro.getSetorComercialInicial();
			Integer setorComercialFinal = filtro.getSetorComercialFinal();

			Integer rotaInicial = filtro.getRotaInicial();
			Integer rotaFinal = filtro.getRotaFinal();

			Integer sequencialRotaInicial = filtro.getSequencialRotalInicial();
			Integer sequencialRotaFinal = filtro.getSequencialRotalFinal();

			Integer unidadeNegocio = filtro.getUnidadeNegocio();
			Integer gerencia = filtro.getGerenciaRegional();

			String consulta = "";
			Query query = null;
			Map parameters = new HashMap();

			consulta = "select imovel.id, " // 0
							+ " gerenciaRegional.id, " // 1
							+ " gerenciaRegional.nome, "// 2
							+ " unidadeNegocio.id," // 3
							+ " unidadeNegocio.nome," // 4
							+ " localidade.id, " // 5
							+ " localidade.descricao, " // 6
							+ " setorComercial.codigo, "// 7
							+ " setorComercial.descricao, "// 8
							+ " quadra.numeroQuadra, " // 9
							+ " cliente.nome, " // 10
							+ " ligacaoAguaSituacao.descricao, " // 11
							+ " ligacaoEsgotoSituacao.descricao, " // 12
							+ " rota.codigo," // 13
							+ " imovel.numeroSequencialRota, " // 14
							+ " imovel.lote, " // 15
							+ " imovel.subLote, " // 16
							+ " setorComercial.id, "// 17
							+ " rota.id " // 18
							+ " from ClienteImovel clienteImovel "
							+ " inner join clienteImovel.clienteRelacaoTipo relacaoTipo"
							+ " inner join clienteImovel.imovel imovel "
							+ " inner join imovel.localidade localidade "
							+ " inner join imovel.setorComercial setorComercial "
							+ " inner join imovel.quadra quadra "
							+ " inner join imovel.rota rota"
							+ " inner join clienteImovel.cliente cliente"
							+ " inner join localidade.unidadeNegocio unidadeNegocio"
							+ " inner join localidade.gerenciaRegional gerenciaRegional"
							+ " inner join imovel.ligacaoAguaSituacao ligacaoAguaSituacao"
							+ " left join imovel.ligacaoEsgotoSituacao ligacaoEsgotoSituacao"
							// + " where ligacaoAguaSituacao.id in (:situacaoAgua1,:situacaoAgua2) "
							// Alteração conforme OC0857313
							// .......................................................
							+ " where imovel.indicadorExclusao = 1 "
							// .......................................................
							+ " and imovel.hidrometroInstalacaoHistorico is null"
							+ " and relacaoTipo.id = :idRelacaoTipo"
							+ " and clienteImovel.dataFimRelacao is null ";

			// parameters.put("indicadorExclusao", LigacaoAguaSituacao.FATURAMENTO_ATIVO);
			// parameters.put("situacaoAgua2", LigacaoAguaSituacao.CORTADO);

			parameters.put("idRelacaoTipo", ClienteRelacaoTipo.USUARIO);

			if(unidadeNegocio != null){
				consulta = consulta + " and unidadeNegocio.id in (:unidade) ";
				parameters.put("unidade", unidadeNegocio);
			}

			if(gerencia != null){
				consulta = consulta + " and gerenciaRegional.id in (:gerencia) ";
				parameters.put("gerencia", gerencia);
			}

			if(localidadeInicial != null){
				consulta = consulta + " and localidade.id between " + localidadeInicial + " and " + localidadeFinal;
			}

			if(setorComercialInicial != null){
				consulta = consulta + " and setorComercial.codigo between " + setorComercialInicial + " and " + setorComercialFinal;
			}

			if(rotaInicial != null){
				consulta = consulta + " and rota.codigo between " + rotaInicial + " and " + rotaFinal;
			}

			if(sequencialRotaInicial != null){
				consulta = consulta + " and imovel.numeroSequencialRota between " + sequencialRotaInicial + " and " + sequencialRotaFinal;
			}

			consulta += " order by gerenciaRegional.id,unidadeNegocio.id,localidade.id,"
							+ "setorComercial.id,imovel.id,rota.codigo,imovel.numeroSequencialRota";

			query = session.createQuery(consulta);

			Set set = parameters.keySet();
			Iterator iterMap = set.iterator();
			while(iterMap.hasNext()){
				String key = (String) iterMap.next();
				if(parameters.get(key) instanceof Collection){
					Collection collection = (ArrayList) parameters.get(key);
					query.setParameterList(key, collection);
				}else{
					query.setParameter(key, parameters.get(key));
				}

			}

			retorno = query.list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC00728] Gerar Relatório de Imóveis Excluidos
	 * 
	 * @author Alcira Rocha
	 * @date 31/01/2013
	 * @param FiltrarRelatorioImoveisExcluidosHelper
	 * @return Collection<Object[]>
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarTotalRegistroRelatorioImoveisExcluidos(FiltrarRelatorioImoveisExcluidosHelper filtro)
					throws ErroRepositorioException{

		Integer retorno = null;

		Integer localidadeInicial = filtro.getLocalidadeInicial();
		Integer localidadeFinal = filtro.getLocalidadeFinal();

		Integer setorComercialInicial = filtro.getSetorComercialInicial();
		Integer setorComercialFinal = filtro.getSetorComercialFinal();

		Integer rotaInicial = filtro.getRotaInicial();
		Integer rotaFinal = filtro.getRotaFinal();

		Integer sequencialRotaInicial = filtro.getSequencialRotalInicial();
		Integer sequencialRotaFinal = filtro.getSequencialRotalFinal();

		Integer unidadeNegocio = filtro.getUnidadeNegocio();
		Integer gerencia = filtro.getGerenciaRegional();

		String consulta = "";
		Query query = null;
		Map parameters = new HashMap();
		Session session = HibernateUtil.getSession();
		try{
			consulta = "select count(*) " + " from ClienteImovel clienteImovel "
							+ " inner join clienteImovel.clienteRelacaoTipo relacaoTipo" + " inner join clienteImovel.imovel imovel "
							+ " inner join imovel.localidade localidade " + " inner join imovel.setorComercial setorComercial "
							+ " inner join imovel.quadra quadra " + " inner join imovel.rota rota"
							+ " inner join clienteImovel.cliente cliente" + " inner join localidade.unidadeNegocio unidadeNegocio"
							+ " inner join localidade.gerenciaRegional gerenciaRegional"
							+ " inner join imovel.ligacaoAguaSituacao ligacaoAguaSituacao"
							+ " left join imovel.ligacaoEsgotoSituacao ligacaoEsgotoSituacao"
							+ " where ligacaoAguaSituacao.id in (:situacaoAgua1,:situacaoAgua2) "
							+ " and imovel.hidrometroInstalacaoHistorico is null" + " and relacaoTipo.id = :idRelacaoTipo"
							+ " and clienteImovel.dataFimRelacao is null ";

			parameters.put("situacaoAgua1", LigacaoAguaSituacao.LIGADO);
			parameters.put("situacaoAgua2", LigacaoAguaSituacao.CORTADO);

			parameters.put("idRelacaoTipo", ClienteRelacaoTipo.USUARIO);

			if(unidadeNegocio != null){
				consulta = consulta + " and unidadeNegocio.id in (:unidade) ";
				parameters.put("unidade", unidadeNegocio);
			}

			if(gerencia != null){
				consulta = consulta + " and gerenciaRegional.id in (:gerencia) ";
				parameters.put("gerencia", gerencia);
			}

			if(localidadeInicial != null){
				consulta = consulta + " and localidade.id between " + localidadeInicial + " and " + localidadeFinal;
			}

			if(setorComercialInicial != null){
				consulta = consulta + " and setorComercial.codigo between " + setorComercialInicial + " and " + setorComercialFinal;
			}

			if(rotaInicial != null){
				consulta = consulta + " and rota.codigo between " + rotaInicial + " and " + rotaFinal;
			}

			if(sequencialRotaInicial != null){
				consulta = consulta + " and imovel.numeroSequencialRota between " + sequencialRotaInicial + " and " + sequencialRotaFinal;
			}

			query = session.createQuery(consulta);

			Set set = parameters.keySet();
			Iterator iterMap = set.iterator();
			while(iterMap.hasNext()){
				String key = (String) iterMap.next();
				if(parameters.get(key) instanceof Collection){
					Collection collection = (ArrayList) parameters.get(key);
					query.setParameterList(key, collection);
				}else{
					query.setParameter(key, parameters.get(key));
				}

			}

			retorno = ((Number) query.uniqueResult()).intValue();
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC00730] Gerar Relatório de Imóveis com Faturas Recentes em Dia e Faturas Antigas em Atraso
	 * 
	 * @author Rafael Pinto
	 * @date 08/01/2008
	 * @param FiltrarRelatorioImoveisFaturasRecentesDiaFaturasAntigasAtrasoHelper
	 * @return Collection<Object[]>
	 * @throws ErroRepositorioException
	 */
	public Collection<Object[]> pesquisarRelatorioImoveisFaturasRecentesDiaFaturasAntigasAtraso(
					FiltrarRelatorioImoveisFaturasRecentesDiaFaturasAntigasAtrasoHelper filtro) throws ErroRepositorioException{

		Collection retorno = null;

		Integer localidadeInicial = filtro.getLocalidadeInicial();
		Integer localidadeFinal = filtro.getLocalidadeFinal();

		Integer setorComercialInicial = filtro.getSetorComercialInicial();
		Integer setorComercialFinal = filtro.getSetorComercialFinal();

		Integer rotaInicial = filtro.getRotaInicial();
		Integer rotaFinal = filtro.getRotaFinal();

		Integer sequencialRotaInicial = filtro.getSequencialRotalInicial();
		Integer sequencialRotaFinal = filtro.getSequencialRotalFinal();

		Integer unidadeNegocio = filtro.getUnidadeNegocio();
		Integer gerencia = filtro.getGerenciaRegional();

		Collection<Integer> colecaoSituacaoLigacaoAgua = filtro.getSituacaoLigacaoAgua();
		Collection<Integer> colecaoCategorias = filtro.getCategorias();

		Integer referenciaDiaInicial = filtro.getReferenciaFaturasDiaInicial();
		Integer referenciaDiaFinal = filtro.getReferenciaFaturasDiaFinal();

		Integer referenciaAtrasoInicial = filtro.getReferenciaFaturasAtrasoInicial();
		Integer referenciaAtrasoFinal = filtro.getReferenciaFaturasAtrasoFinal();

		String consulta = "";
		Query query = null;
		Map parameters = new HashMap();

		Session session = HibernateUtil.getSession();

		try{

			consulta = "select "
							+ "imov.imov_id as idImovel, " // 0
							+ "gerencia.greg_id as idGerencia, " // 1
							+ "gerencia.greg_nmregional as nomeGerencia, " // 2
							+ "unidade.uneg_id as idUnidade, " // 3
							+ "unidade.uneg_nmunidadenegocio as nomeUnidade, " // 4
							+ "local.loca_id as idLocalidade, " // 5
							+ "local.loca_nmlocalidade as nomeLocalidade, " // 6
							+ "setor.stcm_cdsetorcomercial as codigoSetor, "// 7
							+ "setor.stcm_nmsetorcomercial as nomeSetor, " // 8
							+ "qua.qdra_nnquadra as numeroQuadra, " // 9
							+ "cli.clie_nmcliente as nomeCliente, " // 10
							+ "ligacaoAgua.last_dsligacaoaguasituacao as situacaoAgua, " // 11
							+ "ligacaoEsgoto.lest_dsligacaoesgotosituacao as situacaoEsgoto, " // 12
							+ "rot.rota_cdrota as codigoRota, " // 13
							+ "imov.imov_nnsequencialrota as sequenciaRota, " // 14
							+ "imov.imov_nnlote as numeroLote, " // 15
							+ "imov.imov_nnsublote as numeroSubLote, " // 16
							+ "setor.stcm_id as idSetor, " // 17
							+ "rot.rota_id as idRota, " // 18
							+ "imov.imov_qteconomia as qtdEconomia "// 19
							+ "from imovel imov "
							// + "( select i.imov_id from "
							// + "imovel i "
							// + "where exists( "
							// + "select contaAtual.cnta_id "
							// + "from conta contaAtual "
							// + "where contaAtual.dcst_idatual in ( 0,1,2 ) and "
							// +
							// "contaAtual.cnta_amreferenciaconta between "+referenciaDiaInicial+" and "+referenciaDiaFinal+" and "
							// + "contaAtual.imov_id = i.imov_id and "
							// +
							// "not exists( select pgto.pgmt_id from pagamento pgto where contaAtual.cnta_id = pgto.cnta_id ) ) "
							// + ") as imoveis_em_dia "

							// + "inner join imovel imov on imov.imov_id = imoveis_em_dia.imov_id "
							+ "inner join cliente_imovel cliImovel on cliImovel.imov_id = imov.imov_id "
							+ "inner join cliente cli on cli.clie_id = cliImovel.clie_id "
							+ "inner join cliente_relacao_tipo relacaoTipo on relacaoTipo.crtp_id = cliImovel.crtp_id "
							+ "inner join localidade local on local.loca_id = imov.loca_id "
							+ "inner join setor_comercial setor on setor.stcm_id = imov.stcm_id "
							+ "inner join quadra qua on qua.qdra_id = imov.qdra_id " + "inner join rota rot on rot.rota_id = imov.rota_id "
							+ "inner join unidade_negocio unidade on unidade.uneg_id = local.uneg_id "
							+ "inner join gerencia_regional gerencia on gerencia.greg_id = local.greg_id "
							+ "inner join ligacao_agua_situacao ligacaoAgua on ligacaoAgua.last_id = imov.last_id "
							+ "left join ligacao_esgoto_situacao ligacaoEsgoto on ligacaoEsgoto.lest_id = imov.lest_id ";

			if(colecaoCategorias != null && colecaoCategorias.size() > 0){
				consulta = consulta + "inner join imovel_subcategoria imovelSubCat on imovelSubCat.imov_id = imov.imov_id "
								+ "inner join subcategoria subCat on subCat.scat_id = imovelSubCat.scat_id ";
			}

			consulta = consulta + "where not exists( " + "select contaAtual.cnta_id " + "from conta contaAtual "
							+ "where contaAtual.dcst_idatual in ( 0,1,2 ) and " + "contaAtual.cnta_amreferenciaconta between "
							+ referenciaAtrasoInicial + " and " + referenciaAtrasoFinal + " and "
							+ "contaAtual.imov_id = imov.imov_id and "
							+ "not exists( select pgto.pgmt_id from pagamento pgto where contaAtual.cnta_id = pgto.cnta_id ) ) "

							+ "and relacaoTipo.crtp_id = :idRelacaoTipo " + "and cliImovel.clim_dtrelacaofim is null "
							+ "and imov.imov_id in ( select i.imov_id from " + "imovel i " + "where exists( "
							+ "select contaAtual.cnta_id " + "from conta contaAtual " + "where contaAtual.dcst_idatual in ( 0,1,2 ) and "
							+ "contaAtual.cnta_amreferenciaconta between " + referenciaDiaInicial + " and " + referenciaDiaFinal + " and "
							+ "contaAtual.imov_id = i.imov_id and "
							+ "not exists( select pgto.pgmt_id from pagamento pgto where contaAtual.cnta_id = pgto.cnta_id ) ) " + ") ";

			parameters.put("idRelacaoTipo", ClienteRelacaoTipo.USUARIO);

			if(colecaoSituacaoLigacaoAgua != null && colecaoSituacaoLigacaoAgua.size() > 0){
				consulta = consulta + " and ligacaoAgua.last_id in (:situacaoAgua) ";
				parameters.put("situacaoAgua", colecaoSituacaoLigacaoAgua);
			}

			if(colecaoCategorias != null && colecaoCategorias.size() > 0){
				consulta = consulta + " and subCat.catg_id in (:colecaoCategorias) ";
				parameters.put("colecaoCategorias", colecaoCategorias);
			}

			if(unidadeNegocio != null){
				consulta = consulta + " and unidade.uneg_id in (:unidade) ";
				parameters.put("unidade", unidadeNegocio);
			}

			if(gerencia != null){
				consulta = consulta + " and gerencia.greg_id in (:gerencia) ";
				parameters.put("gerencia", gerencia);
			}

			if(localidadeInicial != null){
				consulta = consulta + " and local.loca_id between " + localidadeInicial + " and " + localidadeFinal;
			}

			if(setorComercialInicial != null){
				consulta = consulta + " and setor.stcm_cdsetorcomercial between " + setorComercialInicial + " and " + setorComercialFinal;
			}

			if(rotaInicial != null){
				consulta = consulta + " and rot.rota_cdrota between " + rotaInicial + " and " + rotaFinal;
			}

			if(sequencialRotaInicial != null){
				consulta = consulta + " and imov.imov_nnsequencialrota between " + sequencialRotaInicial + " and " + sequencialRotaFinal;
			}

			consulta += " order by gerencia.greg_id,unidade.uneg_id,local.loca_id,"
							+ "setor.stcm_id,imov.imov_id,rot.rota_cdrota,imov.imov_nnsequencialrota ";

			query = session.createSQLQuery(consulta).addScalar("idImovel", Hibernate.INTEGER).addScalar("idGerencia", Hibernate.INTEGER)
							.addScalar("nomeGerencia", Hibernate.STRING).addScalar("idUnidade", Hibernate.INTEGER).addScalar("nomeUnidade",
											Hibernate.STRING).addScalar("idLocalidade", Hibernate.INTEGER).addScalar("nomeLocalidade",
											Hibernate.STRING).addScalar("codigoSetor", Hibernate.INTEGER).addScalar("nomeSetor",
											Hibernate.STRING).addScalar("numeroQuadra", Hibernate.INTEGER).addScalar("nomeCliente",
											Hibernate.STRING).addScalar("situacaoAgua", Hibernate.STRING).addScalar("situacaoEsgoto",
											Hibernate.STRING).addScalar("codigoRota", Hibernate.SHORT).addScalar("sequenciaRota",
											Hibernate.INTEGER).addScalar("numeroLote", Hibernate.SHORT).addScalar("numeroSubLote",
											Hibernate.SHORT).addScalar("idSetor", Hibernate.INTEGER).addScalar("idRota", Hibernate.INTEGER)
							.addScalar("qtdEconomia", Hibernate.SHORT);

			Set set = parameters.keySet();
			Iterator iterMap = set.iterator();
			while(iterMap.hasNext()){
				String key = (String) iterMap.next();
				if(parameters.get(key) instanceof Collection){
					Collection collection = (ArrayList) parameters.get(key);
					query.setParameterList(key, collection);
				}else{
					query.setParameter(key, parameters.get(key));
				}

			}

			retorno = query.list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC00730] Gerar Relatório de Imóveis com Faturas Recentes em Dia e Faturas Antigas em Atraso
	 * 
	 * @author Rafael Pinto
	 * @date 08/01/2008
	 * @param FiltrarRelatorioImoveisFaturasRecentesDiaFaturasAntigasAtrasoHelper
	 * @return Integer
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarTotalRegistroRelatorioImoveisFaturasRecentesDiaFaturasAntigasAtraso(
					FiltrarRelatorioImoveisFaturasRecentesDiaFaturasAntigasAtrasoHelper filtro) throws ErroRepositorioException{

		Integer retorno = null;

		Integer localidadeInicial = filtro.getLocalidadeInicial();
		Integer localidadeFinal = filtro.getLocalidadeFinal();

		Integer setorComercialInicial = filtro.getSetorComercialInicial();
		Integer setorComercialFinal = filtro.getSetorComercialFinal();

		Integer rotaInicial = filtro.getRotaInicial();
		Integer rotaFinal = filtro.getRotaFinal();

		Integer sequencialRotaInicial = filtro.getSequencialRotalInicial();
		Integer sequencialRotaFinal = filtro.getSequencialRotalFinal();

		Integer unidadeNegocio = filtro.getUnidadeNegocio();
		Integer gerencia = filtro.getGerenciaRegional();

		Collection<Integer> colecaoSituacaoLigacaoAgua = filtro.getSituacaoLigacaoAgua();
		Collection<Integer> colecaoCategorias = filtro.getCategorias();

		Integer referenciaDiaInicial = filtro.getReferenciaFaturasDiaInicial();
		Integer referenciaDiaFinal = filtro.getReferenciaFaturasDiaFinal();

		Integer referenciaAtrasoInicial = filtro.getReferenciaFaturasAtrasoInicial();
		Integer referenciaAtrasoFinal = filtro.getReferenciaFaturasAtrasoFinal();

		String consulta = "";
		Query query = null;
		Map parameters = new HashMap();
		Session session = HibernateUtil.getSession();
		try{
			// Collection imoveisEmDia =
			// this.pesquisarImoveisEmDiaComContaSemPagamento(referenciaDiaInicial,referenciaDiaFinal);

			consulta = "select count(*) as quantidade "
							+ "from "
							// + "( select i.imov_id from "
							// + "imovel i "
							// + "where exists( "
							// + "select contaAtual.cnta_id "
							// + "from conta contaAtual "
							// + "where contaAtual.dcst_idatual in ( 0,1,2 ) and "
							// +
							// "contaAtual.cnta_amreferenciaconta between "+referenciaDiaInicial+" and "+referenciaDiaFinal+" and "
							// + "contaAtual.imov_id = i.imov_id and "
							// +
							// "not exists( select pgto.pgmt_id from pagamento pgto where contaAtual.cnta_id = pgto.cnta_id ) ) "
							// + ") as imoveis_em_dia "

							+ "Imovel imov " + "inner join cliente_imovel cliImovel on cliImovel.imov_id = imov.imov_id "
							+ "inner join cliente cli on cli.clie_id = cliImovel.clie_id "
							+ "inner join cliente_relacao_tipo relacaoTipo on relacaoTipo.crtp_id = cliImovel.crtp_id "
							+ "inner join localidade local on local.loca_id = imov.loca_id "
							+ "inner join setor_comercial setor on setor.stcm_id = imov.stcm_id "
							+ "inner join quadra qua on qua.qdra_id = imov.qdra_id " + "inner join rota rot on rot.rota_id = imov.rota_id "
							+ "inner join unidade_negocio unidade on unidade.uneg_id = local.uneg_id "
							+ "inner join gerencia_regional gerencia on gerencia.greg_id = local.greg_id "
							+ "inner join ligacao_agua_situacao ligacaoAgua on ligacaoAgua.last_id = imov.last_id "
							+ "left join ligacao_esgoto_situacao ligacaoEsgoto on ligacaoEsgoto.lest_id = imov.lest_id ";

			if(colecaoCategorias != null && colecaoCategorias.size() > 0){
				consulta = consulta + "inner join imovel_subcategoria imovelSubCat on imovelSubCat.imov_id = imov.imov_id "
								+ "inner join subcategoria subCat on subCat.scat_id = imovelSubCat.scat_id ";
			}

			consulta = consulta + "where not exists( " + "select contaAtual.cnta_id " + "from conta contaAtual "
							+ "where contaAtual.dcst_idatual in ( 0,1,2 ) and " + "contaAtual.cnta_amreferenciaconta between "
							+ referenciaAtrasoInicial + " and " + referenciaAtrasoFinal + " and "
							+ "contaAtual.imov_id = imov.imov_id and "
							+ "not exists( select pgto.pgmt_id from pagamento pgto where contaAtual.cnta_id = pgto.cnta_id ) ) "

							+ "and relacaoTipo.crtp_id = :idRelacaoTipo " + "and cliImovel.clim_dtrelacaofim is null "
							+ "and imov.imov_id in (" + "select i.imov_id as imovel from " + "Imovel i " + "where exists( "
							+ "select contaAtual.cnta_id " + "from conta contaAtual " + "where contaAtual.dcst_idatual in ( 0,1,2 ) and "
							+ "contaAtual.cnta_amreferenciaconta between " + referenciaDiaInicial + " and " + referenciaDiaFinal + " and "
							+ "contaAtual.imov_id = i.imov_id and "
							+ "not exists( select pgto.pgmt_id from pagamento pgto where contaAtual.cnta_id = pgto.cnta_id ) ))";

			parameters.put("idRelacaoTipo", ClienteRelacaoTipo.USUARIO);

			if(colecaoSituacaoLigacaoAgua != null && colecaoSituacaoLigacaoAgua.size() > 0){
				consulta = consulta + " and ligacaoAgua.last_id in (:situacaoAgua) ";
				parameters.put("situacaoAgua", colecaoSituacaoLigacaoAgua);
			}

			if(colecaoCategorias != null && colecaoCategorias.size() > 0){
				consulta = consulta + " and subCat.catg_id in (:colecaoCategorias) ";
				parameters.put("colecaoCategorias", colecaoCategorias);
			}

			if(unidadeNegocio != null){
				consulta = consulta + " and unidade.uneg_id in (:unidade) ";
				parameters.put("unidade", unidadeNegocio);
			}

			if(gerencia != null){
				consulta = consulta + " and gerencia.greg_id in (:gerencia) ";
				parameters.put("gerencia", gerencia);
			}

			if(localidadeInicial != null){
				consulta = consulta + " and local.loca_id between " + localidadeInicial + " and " + localidadeFinal;
			}

			if(setorComercialInicial != null){
				consulta = consulta + " and setor.stcm_cdsetorcomercial between " + setorComercialInicial + " and " + setorComercialFinal;
			}

			if(rotaInicial != null){
				consulta = consulta + " and rot.rota_cdrota between " + rotaInicial + " and " + rotaFinal;
			}

			if(sequencialRotaInicial != null){
				consulta = consulta + " and imov.imov_nnsequencialrota between " + sequencialRotaInicial + " and " + sequencialRotaFinal;
			}

			query = session.createSQLQuery(consulta).addScalar("quantidade", Hibernate.INTEGER);

			Set set = parameters.keySet();
			Iterator iterMap = set.iterator();
			while(iterMap.hasNext()){
				String key = (String) iterMap.next();
				if(parameters.get(key) instanceof Collection){
					Collection collection = (ArrayList) parameters.get(key);
					query.setParameterList(key, collection);
				}else{
					query.setParameter(key, parameters.get(key));
				}

			}

			retorno = (Integer) query.uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0729] Gerar Relatório de Imóveis por Tipo Consumo
	 * 
	 * @author Bruno Barros
	 * @date 14/01/2008
	 * @param FiltrarRelatorioImoveisTipoConsumo
	 * @return Collection<Object[]>
	 * @throws ErroRepositorioException
	 */
	public Collection<Object[]> pesquisarRelatorioImoveisTipoConsumo(FiltrarRelatorioImoveisTipoConsumoHelper filtro)
					throws ErroRepositorioException{

		Collection retorno = null;

		Integer localidadeInicial = filtro.getLocalidadeInicial();
		Integer localidadeFinal = filtro.getLocalidadeFinal();

		Integer setorComercialInicial = filtro.getSetorComercialInicial();
		Integer setorComercialFinal = filtro.getSetorComercialFinal();

		Integer rotaInicial = filtro.getRotaInicial();
		Integer rotaFinal = filtro.getRotaFinal();

		Integer sequencialRotaInicial = filtro.getSequencialRotalInicial();
		Integer sequencialRotaFinal = filtro.getSequencialRotalFinal();

		Integer unidadeNegocio = filtro.getUnidadeNegocio();
		Integer gerencia = filtro.getGerenciaRegional();

		Integer referenciaImoveisInicial = filtro.getReferenciaInicial();
		Integer referenciaImoveisFinal = filtro.getReferenciaFinal();

		Collection<Integer> colecaoTiposConsumo = filtro.getTiposConsumo();

		String consulta = "";
		Query query = null;
		Map parameters = new HashMap();

		Session session = HibernateUtil.getSession();

		try{

			consulta = " select \n"
							+
							// Gerencia Regional id - Indice 0
							"   gr.id, \n"
							+
							// Gerencia Regional nome - Indice 1
							"   gr.nome, \n"
							+
							// Unidade Negócio id - Indice 2
							"   un.id, \n"
							+
							// Unidade Negócio nome - Indice 3
							"   un.nome, \n"
							+
							// Localidade id - Indice 4
							"   loca.id, \n"
							+
							// Localidade descricao - Indice 5
							"   loca.descricao, \n"
							+
							// Setor Comercial codigo - Indice 6
							"   sc.codigo, \n"
							+
							// Setor Comercial descricao - Indice 7
							"   sc.descricao, \n"
							+
							// Imovel id - Indice 8
							"   imo.id, \n"
							+
							// Cliente nome- Indice 9
							"   cl.nome, \n"
							+
							// Ligacao Agua Situacao Descricao - Indice 10
							"   las.descricao, \n"
							+
							// Consumo Tipo descricao - Indice 11
							"   ct.descricao, \n"
							+
							// Rota codigo - Indice 12
							"   rt.codigo, \n"
							+
							// Numero do Sequencial da Rota - Indice 13
							"   imo.numeroSequencialRota, \n"
							+
							// Ligacao Esgoto Situacao - Indice 14
							"   les.descricao, \n"
							+
							// Numero da Quadra - Indice 15
							"   qua.numeroQuadra, \n"
							+
							// Lote - Indice 16
							"   imo.lote, \n"
							+
							// Sublote - Indice 17
							" 	imo.subLote \n" + " from \n" + "   ConsumoHistorico ch \n" + "   inner join ch.imovel imo \n"
							+ "   inner join imo.localidade loca \n" + "   inner join loca.gerenciaRegional gr \n"
							+ "   inner join loca.unidadeNegocio un \n" + "   inner join imo.setorComercial sc \n"
							+ "   inner join imo.ligacaoAguaSituacao las \n" + "   inner join ch.consumoTipo ct \n"
							+ "   inner join imo.quadra qua \n" + "   inner join imo.rota rt \n"
							+ "   inner join imo.ligacaoEsgotoSituacao les, \n" + "   ClienteImovel ci \n"
							+ "   inner join ci.cliente cl \n" + " where \n" + "   ci.imovel.id = imo.id and \n"
							+ "   ci.clienteRelacaoTipo.id = " + ClienteRelacaoTipo.USUARIO + " and \n" + "   ci.dataFimRelacao is null \n";

			if(unidadeNegocio != null){
				consulta += " and un.id = :unidadeNegocio \n";
				parameters.put("unidadeNegocio", unidadeNegocio);
			}

			if(gerencia != null){
				consulta += " and gr.id = :gerenciaRegional \n";
				parameters.put("gerenciaRegional", gerencia);
			}

			if(localidadeInicial != null){
				consulta += " and loca.id between :localidadeInicial and :localidadeFinal \n";
				parameters.put("localidadeInicial", localidadeInicial);
				parameters.put("localidadeFinal", localidadeFinal);
			}

			if(setorComercialInicial != null){
				consulta += " and sc.id between :setorComercialInicial and :setorComercialFinal \n";

				parameters.put("setorComercialInicial", setorComercialInicial);
				parameters.put("setorComercialFinal", setorComercialFinal);
			}

			if(rotaInicial != null){
				consulta += " and rt.id between :rotaInicial and :rotaFinal \n";

				parameters.put("rotaInicial", rotaInicial);
				parameters.put("rotaFinal", rotaFinal);
			}

			if(sequencialRotaInicial != null){
				consulta += " and imo.numeroSequencialRota between :sequencialRotaInicial and :sequencialRotaFinal \n";

				parameters.put("sequencialRotaInicial", sequencialRotaInicial);
				parameters.put("sequencialRotaFinal", sequencialRotaFinal);
			}

			if(referenciaImoveisInicial != null){
				consulta += " and ch.referenciaFaturamento between :amInicial and :amFinal \n";

				parameters.put("amInicial", referenciaImoveisInicial);
				parameters.put("amFinal", referenciaImoveisFinal);
			}

			if(colecaoTiposConsumo != null && colecaoTiposConsumo.size() > 0){
				consulta += " and ch.consumoTipo.id in (:tiposConsumo) \n";
				parameters.put("tiposConsumo", colecaoTiposConsumo);
			}

			consulta += " order by \n " + "   gr.id, \n " + "   un.id, \n " + "   loca.id, \n " + "   sc.id, \n " + "   imo.id, \n "
							+ "   rt.codigo, \n " + "   imo.numeroSequencialRota";

			query = session.createQuery(consulta);

			Set set = parameters.keySet();

			Iterator iterMap = set.iterator();
			while(iterMap.hasNext()){
				String key = (String) iterMap.next();
				if(parameters.get(key) instanceof Collection){
					Collection collection = (ArrayList) parameters.get(key);
					query.setParameterList(key, collection);
				}else{
					query.setParameter(key, parameters.get(key));
				}

			}

			retorno = query.list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0729] Gerar Relatório de Imóveis por Tipo Consumo
	 * 
	 * @author Bruno Barros
	 * @date 14/01/2008
	 * @param FiltrarRelatorioImoveisTipoConsumo
	 * @return Collection<Object[]>
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarTotalRegistroRelatorioImoveisTipoConsumo(FiltrarRelatorioImoveisTipoConsumoHelper filtro)
					throws ErroRepositorioException{

		Integer retorno = null;

		Integer localidadeInicial = filtro.getLocalidadeInicial();
		Integer localidadeFinal = filtro.getLocalidadeFinal();

		Integer setorComercialInicial = filtro.getSetorComercialInicial();
		Integer setorComercialFinal = filtro.getSetorComercialFinal();

		Integer rotaInicial = filtro.getRotaInicial();
		Integer rotaFinal = filtro.getRotaFinal();

		Integer sequencialRotaInicial = filtro.getSequencialRotalInicial();
		Integer sequencialRotaFinal = filtro.getSequencialRotalFinal();

		Integer unidadeNegocio = filtro.getUnidadeNegocio();
		Integer gerencia = filtro.getGerenciaRegional();

		Integer referenciaImoveisInicial = filtro.getReferenciaInicial();
		Integer referenciaImoveisFinal = filtro.getReferenciaFinal();

		Collection<Integer> colecaoTiposConsumo = filtro.getTiposConsumo();

		String consulta = "";
		Query query = null;
		Map parameters = new HashMap();
		Session session = HibernateUtil.getSession();
		try{

			consulta = " select \n" + "   count(*) \n" + " from \n" + "   ConsumoHistorico ch \n" + "   inner join ch.imovel imo \n"
							+ "   inner join imo.localidade loca \n" + "   inner join loca.gerenciaRegional gr \n"
							+ "   inner join loca.unidadeNegocio un \n" + "   inner join imo.setorComercial sc \n"
							+ "   inner join imo.ligacaoAguaSituacao las \n" + "   inner join ch.consumoTipo ct \n"
							+ "   inner join imo.quadra qua \n" + "   inner join imo.rota rt \n"
							+ "   inner join imo.ligacaoEsgotoSituacao les, \n" + "   ClienteImovel ci \n"
							+ "   inner join ci.cliente cl \n" + " where \n" + "   ci.imovel.id = imo.id and \n"
							+ "   ci.clienteRelacaoTipo.id = " + ClienteRelacaoTipo.USUARIO + " and \n" + "   ci.dataFimRelacao is null \n";

			if(unidadeNegocio != null){
				consulta += " and un.id = :unidadeNegocio \n";
				parameters.put("unidadeNegocio", unidadeNegocio);
			}

			if(gerencia != null){
				consulta += " and gr.id = :gerenciaRegional \n";
				parameters.put("gerenciaRegional", gerencia);
			}

			if(localidadeInicial != null){
				consulta += " and loca.id between :localidadeInicial and :localidadeFinal \n";
				parameters.put("localidadeInicial", localidadeInicial);
				parameters.put("localidadeFinal", localidadeFinal);
			}

			if(setorComercialInicial != null){
				consulta += " and sc.id between :setorComercialInicial and :setorComercialFinal \n";

				parameters.put("setorComercialInicial", setorComercialInicial);
				parameters.put("setorComercialFinal", setorComercialFinal);
			}

			if(rotaInicial != null){
				consulta += " and rt.id between :rotaInicial and :rotaFinal \n";

				parameters.put("rotaInicial", rotaInicial);
				parameters.put("rotaFinal", rotaFinal);
			}

			if(sequencialRotaInicial != null){
				consulta += " and imo.numeroSequencialRota between :sequencialRotaInicial and :sequencialRotaFinal \n";

				parameters.put("sequencialRotaInicial", sequencialRotaInicial);
				parameters.put("sequencialRotaFinal", sequencialRotaFinal);
			}

			if(referenciaImoveisInicial != null){
				consulta += " and ch.referenciaFaturamento between :amInicial and :amFinal \n";

				parameters.put("amInicial", referenciaImoveisInicial);
				parameters.put("amFinal", referenciaImoveisFinal);
			}

			if(colecaoTiposConsumo != null && colecaoTiposConsumo.size() > 0){
				consulta += " and ch.consumoTipo.id in (:tiposConsumo) \n";
				parameters.put("tiposConsumo", colecaoTiposConsumo);
			}

			query = session.createQuery(consulta);

			Set set = parameters.keySet();

			Iterator iterMap = set.iterator();
			while(iterMap.hasNext()){
				String key = (String) iterMap.next();
				if(parameters.get(key) instanceof Collection){
					Collection collection = (ArrayList) parameters.get(key);
					query.setParameterList(key, collection);
				}else{
					query.setParameter(key, parameters.get(key));
				}

			}

			retorno = ((Number) query.setMaxResults(1).uniqueResult()).intValue();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * Método responsável por verificar se existe LogradouroBairro com o id informado.
	 * 
	 * @author Virgínia Melo
	 * @date 07/07/2009
	 * @param loginUsuario
	 * @return
	 * @throws ErroRepositorioException
	 */
	public boolean verificarExistenciaLogradouroBairro(Integer idLogradouroBairro) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		boolean retorno = false;

		try{

			Criteria criteria = session.createCriteria(LogradouroBairro.class).add(Expression.eq("id", idLogradouroBairro));

			List<LogradouroBairro> lista = criteria.list();
			if(lista != null && !lista.isEmpty()){
				retorno = true;
			}

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	private Collection pesquisarImoveisEmDiaComContaSemPagamento(Integer referenciaDiaInicial, Integer referenciaDiaFinal)
					throws ErroRepositorioException{

		Collection retorno = null;
		Session session = HibernateUtil.getSession();
		String hql = "select i.imov_id as imovel from " + "Imovel i " + "where exists( " + "select contaAtual.cnta_id "
						+ "from conta contaAtual " + "where contaAtual.dcst_idatual in ( 0,1,2 ) and "
						+ "contaAtual.cnta_amreferenciaconta between " + referenciaDiaInicial + " and " + referenciaDiaFinal + " and "
						+ "contaAtual.imov_id = i.imov_id and "
						+ "not exists( select pgto.pgmt_id from pagamento pgto where contaAtual.cnta_id = pgto.cnta_id ) )";
		try{
			retorno = session.createSQLQuery(hql).addScalar("imovel", Hibernate.INTEGER).list();
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0XXX] Gerar Relatório Grandes Consumidores
	 * 
	 * @author Anderson Italo
	 * @date 15/02/2011
	 *       Obter dados dos Grandes Consumidores pelos parametros informados
	 */
	public Collection pesquisaRelatorioGrandesConsumidores(Integer idGerencia, Integer idLocalidadeInicial, Integer idLocalidadeFinal)
					throws ErroRepositorioException{

		Collection retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta = null;

		try{

			consulta = " select "
							+
							// Imovel id - Indice 0
							" imo.id, "
							+
							// Gerencia Regional id - Indice 1
							" gr.id, "
							+
							// Gerencia Regional nome - Indice 2
							" gr.nome, "
							+
							// Localidade id - Indice 3
							" loca.id, "
							+
							// Localidade descricao - Indice 4
							" loca.descricao, "
							+
							// Perfil do Imóvel - Indice 5
							" imo.imovelPerfil.id, "
							+
							// Setor Comercial Código 6
							" setor.codigo, "
							+
							// Rota - Indice 7
							" rotaImovel.id, "
							+
							// Segmento - Indice 8
							" imo.numeroSegmento, "
							+
							// Lote - Indice 9
							" imo.lote, "
							+
							// Sublote - Indice 10
							" imo.subLote, "
							+
							// Ligacao Agua Situacao Descricao - Indice 11
							" las.descricao, "
							+
							// Ligacao Esgoto Situacao - Indice 12
							" les.descricao, "
							+
							// Cliente nome- Indice 13
							" cl.nome, "
							+
							// Setor Comercial Id 14
							" setor.id " + " from " + " Imovel imo, " + " ClienteImovel ci " + " inner join ci.cliente cl "
							+ " inner join imo.setorComercial setor " + " inner join imo.localidade loca "
							+ " inner join loca.gerenciaRegional gr " + " inner join imo.ligacaoAguaSituacao las "
							+ " inner join imo.ligacaoEsgotoSituacao les " + " left join imo.rota rotaImovel " + " where "
							+ " ci.imovel.id = imo.id and " + " ci.clienteRelacaoTipo.id = " + ClienteRelacaoTipo.USUARIO + " and "
							+ " ci.dataFimRelacao is null ";

			if(idGerencia != null && idGerencia.intValue() > 0){
				consulta += " and gr.id = :idGerencia";
			}

			if(idLocalidadeInicial != null && idLocalidadeInicial.intValue() > 0){
				consulta += " and loca.id >= " + idLocalidadeInicial.toString();
			}

			if(idLocalidadeFinal != null && idLocalidadeFinal.intValue() > 0){
				consulta += " and loca.id <= " + idLocalidadeFinal.toString();
			}

			consulta += " and imo.imovelPerfil.id in ( " + ImovelPerfil.GRANDE.toString() + ", " + ImovelPerfil.GRANDE_NO_MES.toString()
							+ ") ";

			consulta += " order by gr.id, loca.id, setor.id, imo.numeroSegmento, " + " imo.lote, imo.subLote, imo.id";

			retorno = session.createQuery(consulta).setInteger("idGerencia", idGerencia.intValue()).list();

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
	 * [UC0XXX] Gerar Relatório Grandes Consumidores
	 * 
	 * @author Anderson Italo
	 * @date 18/02/2011
	 *       Obter total de registros do Relatório Grandes
	 *       Consumidores
	 */
	public Integer pesquisarTotalRegistrosRelatorioGrandesConsumidores(Integer idGerencia, Integer idLocalidadeInicial,
					Integer idLocalidadeFinal) throws ErroRepositorioException{

		Integer retorno = 0;
		Object retornoConsulta = null;
		Session session = HibernateUtil.getSession();
		String consulta = null;

		try{
			consulta = " select "
							+
							// Imovel id - Indice 0
							" count(imo.id) " + " from " + " Imovel imo, " + " ClienteImovel ci " + " inner join ci.cliente cl "
							+ " inner join imo.setorComercial setor " + " inner join imo.localidade loca "
							+ " inner join loca.gerenciaRegional gr " + " inner join imo.ligacaoAguaSituacao las "
							+ " inner join imo.ligacaoEsgotoSituacao les " + " where " + " ci.imovel.id = imo.id and "
							+ " ci.clienteRelacaoTipo.id = " + ClienteRelacaoTipo.USUARIO + " and " + " ci.dataFimRelacao is null ";

			if(idGerencia != null && idGerencia.intValue() > 0){
				consulta += " and gr.id = :idGerencia";
			}

			if(idLocalidadeInicial != null && idLocalidadeInicial.intValue() > 0){
				consulta += " and loca.id >= " + idLocalidadeInicial.toString();
			}

			if(idLocalidadeFinal != null && idLocalidadeFinal.intValue() > 0){
				consulta += " and loca.id <= " + idLocalidadeFinal.toString();
			}

			consulta += " and imo.imovelPerfil.id in ( " + ImovelPerfil.GRANDE.toString() + ", " + ImovelPerfil.GRANDE_NO_MES.toString()
							+ ") ";

			consulta += " order by gr.id, loca.id, setor.id, imo.numeroSegmento, " + " imo.lote, imo.subLote, imo.id";

			retornoConsulta = session.createQuery(consulta).setInteger("idGerencia", idGerencia.intValue()).uniqueResult();

			if(retornoConsulta != null){
				retorno = ((Number) retornoConsulta).intValue();
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
	 * [UC0XXX] Relatório Usuários em Débito Automático
	 * 
	 * @author Anderson Italo
	 * @date 23/02/2011
	 *       Obter dados dos Usuários com Débito Automático pelos parametros informados
	 */
	public Collection pesquisaRelatorioUsuarioDebitoAutomatico(Integer idBancoInicial, Integer idBancoFinal)
					throws ErroRepositorioException{

		Collection retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta = null;

		try{

			consulta = " select "
							+
							// Banco id - Indice 0
							" ban.id, "
							+
							// Banco nome - Indice 1
							" ban.descricao, "
							+
							// Agencia codigo - Indice 2
							" age.codigoAgencia, "
							+
							// Conta Corrente - Indice 3
							" deb.identificacaoClienteBanco, "
							+
							// Imovel id - Indice 4
							" imo.id, "
							+
							// Cliente nome - Indice 5
							" cl.nome, "
							+
							// Data Opção - Indice 6
							" deb.dataOpcaoDebitoContaCorrente " + " from " + " DebitoAutomatico deb " + " inner join deb.agencia age "
							+ " inner join age.banco ban " + " inner join deb.imovel imo, " + " ClienteImovel ci "
							+ " inner join ci.cliente cl " + " where " + " ci.imovel.id = imo.id and " + " ci.clienteRelacaoTipo.id = "
							+ ClienteRelacaoTipo.USUARIO + " and " + " ci.dataFimRelacao is null ";

			if(idBancoInicial != null && idBancoInicial.intValue() > 0){
				consulta += " and ban.id >= " + idBancoInicial.toString();
			}

			if(idBancoFinal != null && idBancoFinal.intValue() > 0){
				consulta += " and ban.id <= " + idBancoFinal.toString();
			}

			consulta += " and deb.dataExclusao is null ";

			consulta += " order by ban.id, age.codigoAgencia, deb.dataOpcaoDebitoContaCorrente";

			retorno = session.createQuery(consulta).list();

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
	 * [UC0XXX] Relatório Usuários em Débito Automático
	 * 
	 * @author Anderson Italo
	 * @date 24/02/2011 Obter Total de Registros do Relatório Usuários com
	 *       Débito Automático
	 */
	public Integer pesquisarTotalRegistrosRelatorioUsuarioDebitoAutomatico(Integer idBancoInicial, Integer idBancoFinal)
					throws ErroRepositorioException{

		Integer retorno = 0;
		Object retornoConsulta = null;
		Session session = HibernateUtil.getSession();
		String consulta = null;

		try{

			consulta = " select count(imo.id) from " + " DebitoAutomatico deb " + " inner join deb.agencia age "
							+ " inner join age.banco ban " + " inner join deb.imovel imo, " + " ClienteImovel ci "
							+ " inner join ci.cliente cl " + " where " + " ci.imovel.id = imo.id and " + " ci.clienteRelacaoTipo.id = "
							+ ClienteRelacaoTipo.USUARIO + " and " + " ci.dataFimRelacao is null ";

			if(idBancoInicial != null && idBancoInicial.intValue() > 0){
				consulta += " and ban.id >= " + idBancoInicial.toString();
			}

			if(idBancoFinal != null && idBancoFinal.intValue() > 0){
				consulta += " and ban.id <= " + idBancoFinal.toString();
			}

			consulta += " and deb.dataExclusao is null ";

			retornoConsulta = session.createQuery(consulta).uniqueResult();

			if(retornoConsulta != null){
				retorno = ((Number) retornoConsulta).intValue();
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
	 * Consulta os motivos de exclusão do programa Água Para Todos
	 * 
	 * @author Luciano Galvão
	 * @date 20/03/2012
	 */
	public List consultarAguaParaTodosMotivosExclusao() throws ErroRepositorioException{

		List<AguaParaTodosMotivoExclusaoHelper> resultado = null;
		List retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = " select aptme.apme_id as id, aptme.apme_dsmotivo as motivo from agua_para_todos_motivo_excl aptme "
							+ " order by aptme.apme_id ";

			retorno = session.createSQLQuery(consulta).list();

			if((retorno != null) && (!retorno.isEmpty())){
				resultado = new ArrayList<AguaParaTodosMotivoExclusaoHelper>();

				for(int i = 0; i < retorno.size(); i++){
					Object obj = retorno.get(i);

					if(obj instanceof Object[]){
						Object[] linha = (Object[]) obj;
						resultado.add(new AguaParaTodosMotivoExclusaoHelper((BigDecimal) linha[0], (String) linha[1]));
					}
				}
			}

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		return resultado;
	}

	/**
	 * Atualiza a imagem de Relatório da empresa na tabela de parâmetros do sistema
	 * 
	 * @author lgalvao (Luciano Galvão)
	 * @date 22/05/2012
	 * @param sistemaParametroId
	 * @param imagemRelatorio
	 * @throws ErroRepositorioException
	 */
	public void atualizarImagemRelatorio(Integer sistemaParametroId, byte[] imagemRelatorio) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();

		String sql;

		try{

			sql = "UPDATE sistema_parametros " + "SET parm_imrelatorio = :imagemRelatorio, parm_tmultimaalteracao = :dataAlteracao "
							+ "WHERE parm_id = :sistemaParametroId";

			session.createSQLQuery(sql).setBinary("imagemRelatorio", imagemRelatorio).setTimestamp("dataAlteracao", new Date()).setInteger(
							"sistemaParametroId", sistemaParametroId).executeUpdate();

		}catch(HibernateException e){

			throw new ErroRepositorioException(e, "Erro no Hibernate");

		}finally{
			HibernateUtil.closeSession(session);
		}
	}

	public Collection obterLogradouro() throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();
		StringBuilder consulta = new StringBuilder();

		try{

			consulta.append("select l.logr_id as idLogradouro, "); // 0
			consulta.append(" l.logr_nmlogradouro as nomeLogradouro , "); // 1
			consulta.append(" l.muni_id as idMunicipio, "); // 2
			consulta.append(" lgtp.lgtp_dsabreviado as descricaoLogrTpAbreviada, "); // 3
			consulta.append("lgtt.lgtt_dsabreviado as descricaoLogrTtAbreviada "); // 4
			consulta.append(" from logradouro l ");
			consulta.append(" left join logradouro_tipo lgtp on lgtp.lgtp_id = l.lgtp_id ");
			consulta.append(" left join logradouro_titulo lgtt on lgtt.lgtt_id = l.lgtt_id ");

			Query query = session.createSQLQuery(consulta.toString()).addScalar("idLogradouro", Hibernate.INTEGER).addScalar(
							"nomeLogradouro", Hibernate.STRING).addScalar("idMunicipio", Hibernate.INTEGER).addScalar(
							"descricaoLogrTpAbreviada", Hibernate.STRING).addScalar("descricaoLogrTtAbreviada", Hibernate.STRING);

			retorno = (Collection) query.list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC3012 – Gerar Arquivo Texto Faturamento Imediado]
	 * Obtém o id da Esfera de Poder do Cliente Responsável.
	 * 
	 * @author Anderson Italo
	 * @date 22/08/2011
	 * @param idImovel
	 * @return
	 * @throws ControladorException
	 */
	public Integer pesquisarIdEsferaPoderClienteResponsavelImovel(Integer idImovel) throws ErroRepositorioException{

		Object retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "select ct.esferaPoder.id from Imovel i inner join i.clienteImoveis ci inner join ci.cliente c "
							+ "inner join c.clienteTipo ct "
							+ "where ci.clienteRelacaoTipo.id = :clienteResponsavel and ci.dataFimRelacao is null and i.id =:idImovel";

			retorno = session.createQuery(consulta).setInteger("clienteResponsavel", ClienteRelacaoTipo.RESPONSAVEL).setInteger("idImovel",
							idImovel).uniqueResult();

		}catch(HibernateException e){

			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{

			HibernateUtil.closeSession(session);
		}

		if(retorno != null){

			return (Integer) retorno;
		}else{

			return 0;
		}
	}

	/**
	 * [UC0XXX] Relatório Débito por Reponsável
	 * 
	 * @author Anderson Italo
	 * @date 25/09/2012
	 */
	public Collection<Object[]> pesquisarRelatorioDebitoPorReponsavel(FiltroRelatorioDebitoPorResponsavelHelper filtro)
					throws ErroRepositorioException{

		Collection<Object[]> retorno = null;
		Session session = HibernateUtil.getSession();
		StringBuffer consulta = new StringBuffer();

		try{

			consulta.append(" SELECT DISTINCT(CO.CNTA_ID) AS idConta, "); // 0
			consulta.append(" CLI.CLIE_ID AS idCliente, "); // 1
			consulta.append(" CLI.CLIE_NMCLIENTE AS nomeCliente, "); // 2
			consulta.append(" IM.IMOV_ID AS idImovel, "); // 3
			consulta.append(" LO.LOCA_ID AS idLocalidade, "); // 4
			consulta.append(" LO.LOCA_NMLOCALIDADE AS descricaoLocalidade, "); // 5
			consulta.append(" SE.STCM_CDSETORCOMERCIAL AS codigoSetor, "); // 6
			consulta.append(" QU.QDRA_NNQUADRA AS numeroQuadra, "); // 7
			consulta.append(" IM.IMOV_NNLOTE AS numeroLote, "); // 8
			consulta.append(" IM.IMOV_NNSUBLOTE AS numeroSubLote, "); // 9
			consulta.append(" (SELECT CL.CLIE_NMCLIENTE FROM CLIENTE_IMOVEL CI ");
			consulta.append(" INNER JOIN CLIENTE CL ON CL.CLIE_ID = CI.CLIE_ID ");
			consulta.append(" WHERE CI.IMOV_ID = IM.IMOV_ID AND CI.CRTP_ID = " + ClienteRelacaoTipo.USUARIO.toString());
			consulta.append(" AND CI.CLIM_DTRELACAOFIM IS NULL) AS clienteUsuario, "); // 10
			consulta.append(" LAST.LAST_ID AS idLigacaoAguaSituacao, "); // 11
			consulta.append(" IM.LEST_ID AS idLigacaoEsgotoSituacao, "); // 12
			consulta.append(" CO.CNTA_ICCOBRANCAMULTA AS indicadorCobrancaMulta, "); // 13
			consulta.append(" (CO.CNTA_VLAGUA +  CO.CNTA_VLESGOTO + CO.CNTA_VLDEBITOS - (CO.CNTA_VLCREDITOS + ");
			consulta.append(" CO.CNTA_VLIMPOSTOS)) AS valorNominal, "); // 14
			consulta.append(" CO.CNTA_AMREFERENCIACONTA AS anoMesReferencia, "); // 15
			consulta.append(" CO.CNTA_DTVENCIMENTOCONTA AS dataVencimento, "); // 16
			consulta.append(" CO.CNTA_NNCONSUMOAGUA AS consumoAgua, "); // 17
			consulta.append(" CO.CNTA_NNCONSUMOMINIMOESGOTO AS consumoEsgoto, ");// 18
			consulta.append(" CLITP.CLTP_ID AS idClienteTipo, "); // 19
			consulta.append(" CLITP.CLTP_DSCLIENTETIPO AS descricaoClienteTipo, "); // 20
			consulta.append(" LAST.LAST_DSLIGACAOAGUASITUACAO AS descricaoLigacaoAguaSituacao, "); // 21
			consulta.append(" ESPOD.EPOD_DSESFERAPODER as descricaoEsferaPoder "); // 22
			consulta.append(" FROM IMOVEL IM ");
			consulta.append(" INNER JOIN CONTA CO ON CO.IMOV_ID = IM.IMOV_ID ");
			consulta.append(" INNER JOIN CLIENTE_CONTA CC ON (CC.CNTA_ID = CO.CNTA_ID ");
			consulta.append(" AND CC.CRTP_ID = " + ClienteRelacaoTipo.RESPONSAVEL.toString() + ")");
			consulta.append(" INNER JOIN CLIENTE CLI on CLI.CLIE_ID = CC.CLIE_ID ");
			consulta.append(" INNER JOIN LOCALIDADE LO ON LO.LOCA_ID = IM.LOCA_ID ");
			consulta.append(" INNER JOIN SETOR_COMERCIAL SE ON SE.STCM_ID = IM.STCM_ID ");
			consulta.append(" INNER JOIN QUADRA QU ON QU.QDRA_ID = IM.QDRA_ID ");
			consulta.append(" INNER JOIN CLIENTE_TIPO CLITP ON CLITP.CLTP_ID = CLI.CLTP_ID ");
			consulta.append(" INNER JOIN LIGACAO_AGUA_SITUACAO LAST ON LAST.LAST_ID = IM.LAST_ID ");
			consulta.append(" INNER JOIN ESFERA_PODER ESPOD ON CLITP.EPOD_ID = ESPOD.EPOD_ID ");
			consulta.append(" WHERE 1=1 ");

			// Reponsabilidade
			if(filtro.getIndicadorResponsabilidade().equals("D")){

				// Reponsável Inicial
				if(filtro.getIdClienteInicial() != null){

					consulta.append(" AND CLI.CLIE_ID >= " + filtro.getIdClienteInicial().toString());
				}

				// Reponsável Final
				if(filtro.getIdClienteFinal() != null){

					consulta.append(" AND CLI.CLIE_ID <= " + filtro.getIdClienteFinal().toString());
				}

			}else if(filtro.getIndicadorResponsabilidade().equals("I")){

				// Reponsável Inicial
				if(filtro.getIdClienteInicial() != null){

					consulta.append(" AND CLI.CLIE_CDCLIENTERESPONSAVEL >= " + filtro.getIdClienteInicial().toString());
				}

				// Reponsável Final
				if(filtro.getIdClienteFinal() != null){

					consulta.append(" AND CLI.CLIE_CDCLIENTERESPONSAVEL <= " + filtro.getIdClienteFinal().toString());
				}
			}else{

				// Reponsável Inicial
				if(filtro.getIdClienteInicial() != null){

					consulta.append(" AND ((CLI.CLIE_ID >= " + filtro.getIdClienteInicial().toString());
					consulta.append(" AND CLI.CLIE_ID <= " + filtro.getIdClienteInicial().toString() + ")");
				}

				// Reponsável Final
				if(filtro.getIdClienteFinal() != null){

					consulta.append(" OR (CLI.CLIE_CDCLIENTERESPONSAVEL >= " + filtro.getIdClienteFinal().toString());
					consulta.append(" AND CLI.CLIE_CDCLIENTERESPONSAVEL <= " + filtro.getIdClienteFinal().toString() + "))");
				}
			}

			// Tipo Reponsável Inicial
			if(filtro.getIdTipoClienteInicial() != null){

				consulta.append(" AND CLI.CLTP_ID >= " + filtro.getIdTipoClienteInicial().toString());
			}

			// Tipo Reponsável Final
			if(filtro.getIdTipoClienteFinal() != null){

				consulta.append(" AND CLI.CLTP_ID <= " + filtro.getIdTipoClienteFinal().toString());
			}

			// Referência Inicial
			if(filtro.getReferenciaDebitoInicial() != null){

				consulta.append(" AND CO.CNTA_AMREFERENCIACONTA >= " + filtro.getReferenciaDebitoInicial().toString());
			}

			// Referência Final
			if(filtro.getReferenciaDebitoFinal() != null){

				consulta.append(" AND CO.CNTA_AMREFERENCIACONTA <= " + filtro.getReferenciaDebitoFinal().toString());
			}

			// Conta em Revisão
			if(filtro.getIndicadorContasEmRevisao() != null){

				if(filtro.getIndicadorContasEmRevisao().equals(ConstantesSistema.SIM)){

					consulta.append(" AND CO.CMRV_ID IS NOT NULL");
				}else{

					consulta.append(" AND CO.CMRV_ID IS NULL");
				}

				if(filtro.getIdMotivoRevisao() != null && Util.verificarIdNaoVazio(filtro.getIdMotivoRevisao().toString())){

					consulta.append(" AND CO.CMRV_ID = " + filtro.getIdMotivoRevisao().toString());
				}
			}

			if(!filtro.getIdEsferaPoder().equals(ConstantesSistema.INVALIDO_ID)){

				consulta.append(" AND ESPOD.EPOD_ID = " + filtro.getIdEsferaPoder().toString());
			}

			consulta.append(" ORDER BY CLI.CLIE_ID ASC, IM.IMOV_ID ASC, CO.CNTA_AMREFERENCIACONTA DESC ");

			retorno = (Collection<Object[]>) session.createSQLQuery(consulta.toString()).addScalar("idConta", Hibernate.INTEGER).addScalar(
							"idCliente", Hibernate.INTEGER).addScalar("nomeCliente", Hibernate.STRING).addScalar("idImovel",
							Hibernate.INTEGER).addScalar("idLocalidade", Hibernate.INTEGER).addScalar("descricaoLocalidade",
							Hibernate.STRING).addScalar("codigoSetor", Hibernate.INTEGER).addScalar("numeroQuadra", Hibernate.INTEGER)
							.addScalar("numeroLote", Hibernate.SHORT).addScalar("numeroSubLote", Hibernate.SHORT).addScalar(
											"clienteUsuario", Hibernate.STRING).addScalar("idLigacaoAguaSituacao", Hibernate.INTEGER)
							.addScalar("idLigacaoEsgotoSituacao", Hibernate.INTEGER).addScalar("indicadorCobrancaMulta", Hibernate.SHORT)
							.addScalar("valorNominal", Hibernate.BIG_DECIMAL).addScalar("anoMesReferencia", Hibernate.INTEGER).addScalar(
											"dataVencimento", Hibernate.DATE).addScalar("consumoAgua", Hibernate.INTEGER).addScalar(
											"consumoEsgoto", Hibernate.INTEGER).addScalar("idClienteTipo", Hibernate.INTEGER).addScalar(
											"descricaoClienteTipo", Hibernate.STRING).addScalar("descricaoLigacaoAguaSituacao",
											Hibernate.STRING).addScalar("descricaoEsferaPoder",Hibernate.STRING).list();

		}catch(HibernateException e){

			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{

			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0XXX] Relatório Débito por Reponsável
	 * 
	 * @author Anderson Italo
	 * @date 25/09/2012
	 */
	public Integer pesquisarTotalRegistrosRelatorioDebitoPorReponsavel(FiltroRelatorioDebitoPorResponsavelHelper filtro)
					throws ErroRepositorioException{

		Integer retorno = null;
		Session session = HibernateUtil.getSession();
		StringBuffer consulta = new StringBuffer();

		try{

			consulta.append(" SELECT COUNT (DISTINCT(CO.CNTA_ID)) ");
			consulta.append(" FROM IMOVEL IM ");
			consulta.append(" INNER JOIN CONTA CO ON CO.IMOV_ID = IM.IMOV_ID ");
			consulta.append(" INNER JOIN CLIENTE_CONTA CC ON (CC.CNTA_ID = CO.CNTA_ID ");
			consulta.append(" AND CC.CRTP_ID = " + ClienteRelacaoTipo.RESPONSAVEL.toString() + ")");
			consulta.append(" INNER JOIN CLIENTE CLI on CLI.CLIE_ID = CC.CLIE_ID ");
			consulta.append(" WHERE 1=1 ");

			// Reponsabilidade
			if(filtro.getIndicadorResponsabilidade().equals("D")){

				// Reponsável Inicial
				if(filtro.getIdClienteInicial() != null){

					consulta.append(" AND CLI.CLIE_ID >= " + filtro.getIdClienteInicial().toString());
				}

				// Reponsável Final
				if(filtro.getIdClienteFinal() != null){

					consulta.append(" AND CLI.CLIE_ID <= " + filtro.getIdClienteFinal().toString());
				}

			}else if(filtro.getIndicadorResponsabilidade().equals("I")){

				// Reponsável Inicial
				if(filtro.getIdClienteInicial() != null){

					consulta.append(" AND CLI.CLIE_CDCLIENTERESPONSAVEL >= " + filtro.getIdClienteInicial().toString());
				}

				// Reponsável Final
				if(filtro.getIdClienteFinal() != null){

					consulta.append(" AND CLI.CLIE_CDCLIENTERESPONSAVEL <= " + filtro.getIdClienteFinal().toString());
				}
			}else{

				// Reponsável Inicial
				if(filtro.getIdClienteInicial() != null){

					consulta.append(" AND ((CLI.CLIE_ID >= " + filtro.getIdClienteInicial().toString());
					consulta.append(" AND CLI.CLIE_ID <= " + filtro.getIdClienteInicial().toString() + ")");
				}

				// Reponsável Final
				if(filtro.getIdClienteFinal() != null){

					consulta.append(" OR (CLI.CLIE_CDCLIENTERESPONSAVEL >= " + filtro.getIdClienteFinal().toString());
					consulta.append(" AND CLI.CLIE_CDCLIENTERESPONSAVEL <= " + filtro.getIdClienteFinal().toString() + "))");
				}
			}

			// Tipo Reponsável Inicial
			if(filtro.getIdTipoClienteInicial() != null){

				consulta.append(" AND CLI.CLTP_ID >= " + filtro.getIdTipoClienteInicial().toString());
			}

			// Tipo Reponsável Final
			if(filtro.getIdTipoClienteFinal() != null){

				consulta.append(" AND CLI.CLTP_ID <= " + filtro.getIdTipoClienteFinal().toString());
			}

			// Referência Inicial
			if(filtro.getReferenciaDebitoInicial() != null){

				consulta.append(" AND CO.CNTA_AMREFERENCIACONTA >= " + filtro.getReferenciaDebitoInicial().toString());
			}

			// Referência Final
			if(filtro.getReferenciaDebitoFinal() != null){

				consulta.append(" AND CO.CNTA_AMREFERENCIACONTA <= " + filtro.getReferenciaDebitoFinal().toString());
			}

			// Conta em Revisão
			if(filtro.getIndicadorContasEmRevisao() != null){

				if(filtro.getIndicadorContasEmRevisao().equals(ConstantesSistema.SIM)){

					consulta.append(" AND CO.CMRV_ID IS NOT NULL");
				}else{

					consulta.append(" AND CO.CMRV_ID IS NULL");
				}

				if(filtro.getIdMotivoRevisao() != null && Util.verificarIdNaoVazio(filtro.getIdMotivoRevisao().toString())){

					consulta.append(" AND CO.CMRV_ID = " + filtro.getIdMotivoRevisao().toString());
				}
			}

			retorno = ((Number) session.createSQLQuery(consulta.toString()).setMaxResults(1).uniqueResult()).intValue();

		}catch(HibernateException e){

			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{

			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	public void criarSequence(String nomeSequence) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "CREATE SEQUENCE " + nomeSequence + " INCREMENT BY 1 MINVALUE 1 MAXVALUE 9999999999999999999 START WITH 1";

			session.createSQLQuery(consulta).executeUpdate();

		}catch(HibernateException e){

			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{

			HibernateUtil.closeSession(session);
		}

	}

	// Recupera o próximo valor da sequence
	public Integer obterNextValSequence(String nomeSequence) throws ErroRepositorioException{

		Integer retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta = null;

		try{
			Dialect dialect = Dialect.getDialect();

			consulta = dialect.getSequenceNextValString(nomeSequence);

			Number retornoConsulta = (Number) session.createSQLQuery(consulta).uniqueResult();

			if(retornoConsulta != null){
				retorno = retornoConsulta.intValue();
			}
		}catch(GenericJDBCException ex){
			throw new ErroRepositorioException(ex, "Erro no Hibernate");
		}catch(CallbackException e){
			throw new ErroRepositorioException(e, e.getMessage());
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}
}
