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
 * Thiago Silva Toscano de Brito
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

package gcom.spcserasa;

import gcom.cadastro.cliente.*;
import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.imovel.ImovelCobrancaSituacao;
import gcom.cadastro.imovel.ImovelPerfil;
import gcom.cadastro.localidade.GerenciaRegional;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.UnidadeNegocio;
import gcom.cobranca.*;
import gcom.cobranca.bean.ComandoNegativacaoHelper;
import gcom.cobranca.bean.ComandoNegativacaoTipoCriterioHelper;
import gcom.cobranca.bean.DadosConsultaNegativacaoHelper;
import gcom.faturamento.conta.Conta;
import gcom.faturamento.conta.ContaHistorico;
import gcom.gui.cobranca.spcserasa.RelatorioAcompanhamentoClientesNegativadosHelper;
import gcom.micromedicao.Rota;
import gcom.spcserasa.bean.InserirComandoNegativacaoPorCriterioHelper;
import gcom.spcserasa.bean.NegativadorMovimentoHelper;
import gcom.util.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import org.hibernate.*;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 * @author Administrador
 */
public class RepositorioSpcSerasaHBM
				implements IRepositorioSpcSerasa {

	private static IRepositorioSpcSerasa instancia;

	/**
	 * Construtor da classe RepositorioFaturamentoHBM
	 */
	private RepositorioSpcSerasaHBM() {

	}

	/**
	 * Retorna o valor de instance
	 * 
	 * @return O valor de instance
	 */
	public static IRepositorioSpcSerasa getInstancia(){

		if(instancia == null){
			instancia = new RepositorioSpcSerasaHBM();
		}
		return instancia;
	}

	/**
	 * Seleciona o dados do Negativador
	 * 
	 * @author Marcio Roberto
	 * @date 30/10/2007
	 * @param
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List getDadosNegativador(int idNegativador) throws ErroRepositorioException{

		List retorno = null;
		Session session = HibernateUtil.getSession();
		try{
			Criteria criteria = session.createCriteria(Negativador.class);
			criteria.add(Restrictions.eq("id", Integer.valueOf(idNegativador)));
			retorno = criteria.list();
			/*
			 * String hql = " select "
			 * + " neg "
			 * + " from gcom.cobranca.Negativador neg "
			 * + " where neg.id = :idNegativador ";
			 * retorno = session.createQuery(hql).setInteger("idNegativador", idNegativador).list();
			 */
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate getDadosNegativador");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * Seleciona o dados do Negativador
	 * 
	 * @author Marcio Roberto
	 * @date 30/10/2007
	 * @param
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List getDadosContratoNegativador(int idNegativador) throws ErroRepositorioException{

		List retorno = null;
		Session session = HibernateUtil.getSession();
		try{
			StringBuffer hql = new StringBuffer(" select ").append(" negCon ").append(" from gcom.cobranca.NegativadorContrato negCon ")
							.append(" where negCon.negativador.id = :idNegativador ")
							.append(" and  (negCon.dataContratoEncerramento = null or negCon.dataContratoEncerramento > :dataAtual) ");
			retorno = session.createQuery(hql.toString()).setInteger("idNegativador", idNegativador).setDate("dataAtual", new Date())
							.list();
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate getDadosContratoNegativador");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * Insere comando Negativação
	 * 
	 * @author Marcio Roberto
	 * @date 30/10/2007
	 * @param
	 * @return
	 * @throws ErroRepositorioException
	 */
	@Deprecated
	public Integer inserirComandoNegativacao(int idNegativador, int idUsuarioResponsavel, String identificacaoCI)
					throws ErroRepositorioException{

		String insert;
		Session session = HibernateUtil.getSession();
		Connection con = null;
		Statement stmt = null;
		int nextValId = 0;
		try{
			con = session.connection();
			stmt = con.createStatement();
			insert = "insert into negativacao_comando (" + " ngcm_id, " // 01
							+ " ngcm_icsimulacao," // 02
							+ " ngcm_iccomandocriterio, " // 03
							+ " ngcm_dtprevista, " // 04
							+ " ngcm_tmcomando, " // 05
							+ " ngcm_tmrealizacao, " // 06
							+ " ngcm_qtinclusoes, " // 07
							+ " ngcm_vldebito, " // 08
							+ " ngcm_qtitensincluidos, " // 09
							+ " ngcm_tmultimaalteracao, " // 10
							+ " usur_id, " // 11
							+ " ngcm_dsci, " // 12
							+ " negt_id ) " // 13
							+ "  values ( " + " SQ_NEGTVDR_COMANDO.NEXTVAL," // 01
							+ " 2, " // 02
							+ " 2, " // 03
							+ " current_timestamp, " // 04
							+ " current_timestamp, " // 05
							+ " current_timestamp, " // 06
							+ "null," // 07
							+ "null," // 08
							+ "null," // 09
							+ " current_timestamp, " // 10
							+ idUsuarioResponsavel + ", " // 11
							+ "'" + identificacaoCI + "'" + ", " // 12
							+ idNegativador + ")"; // 13

			stmt.executeUpdate(insert);
			System.out.print("1 - INSERINDO NEGATIVACAO COMANDO!! ");
			nextValId = this.getNextNegativadorComando();
		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			nextValId = 0;
			throw new ErroRepositorioException(e, "Erro no Hibernate negativacao comando ");
		}catch(SQLException e){
			nextValId = 0;
			throw new ErroRepositorioException(e, "Erro no Insert negativacao comando ");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
			try{
				stmt.close();
				con.close();
			}catch(SQLException e){
				throw new ErroRepositorioException(e, "Erro ao fechar conexões");
			}
			return nextValId;
		}
	}

	/**
	 * Insere comando Negativação
	 * 
	 * @author Marcio Roberto
	 * @date 30/10/2007
	 * @param
	 * @return
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List getComandoCriterioNegativacao(int idNegativador, int idUsuarioResponsavel, String identificacaoCI)
					throws ErroRepositorioException{

		List retorno = null;
		Session session = HibernateUtil.getSession();
		try{

			String hql = "select "
							+ "   negCom.id, " // 01
							+ "   negCom.indicadorComandoCriterio, " // 02
							+ "   negCom.dataPrevista, " // 03
							+ "   negCom.dataHoraComando, " // 04
							+ "   negCom.dataHoraRealizacao, " // 05
							+ "   negCom.quantidadeInclusoes, " // 06
							+ "   negCom.valorDebito, " // 07
							+ "   negCom.quantidadeItensIncluidos, " // 08
							+ "   negCom.descricaoComunicacaoInterna, " // 09
							+ "   negCom.indicadorSimulacao, " // 10
							+ "   negCom.usuario, " // 11
							+ "   negCrit.cliente.id " // 12
							+ " from gcom.cobranca.NegativacaoCriterio negCrit " + "   inner join negCrit.negativacaoComando negCom "
							+ "   inner join negCrit.cliente cli " + " where negCom.negativador.id = :idNegativador ";

			retorno = session.createQuery(hql).setInteger("idNegativador", idNegativador).list();
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate getComandoCriterioNegativacao");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * Obtem dados Imovel
	 * 
	 * @author Marcio Roberto
	 * @date 01/10/2007
	 * @param
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List getDadosImoveis(int idImovel) throws ErroRepositorioException{

		List retorno = null;
		Session session = HibernateUtil.getSession();
		try{
			StringBuffer hql = new StringBuffer("select ").append(" imov ").append(" from gcom.cadastro.imovel.Imovel imov ")
							.append(" inner join fetch imov.localidade loca ").append(" inner join fetch imov.quadra quand ")
							.append(" inner join fetch imov.imovelPerfil iper ").append(" inner join fetch quand.setorComercial stcom ")
							.append(" where imov.id = :idImovel ");

			retorno = session.createQuery(hql.toString()).setInteger("idImovel", idImovel).list();
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate getDadosImoveis");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * Obtem DadosCliente
	 * 
	 * @author Marcio Roberto
	 * @date 01/10/2007
	 * @param
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List getDadosCliente(int idCliente) throws ErroRepositorioException{

		List retorno = null;
		Session session = HibernateUtil.getSession();
		try{
			StringBuffer hql = new StringBuffer("select ").append(" cli ").append(" from gcom.cadastro.cliente.Cliente cli ")
							.append(" left join cli.clienteEnderecos cli_ender ").append(" where cli.id = :idCliente ");

			retorno = session.createQuery(hql.toString()).setInteger("idCliente", idCliente).list();
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate getDadosImoveis");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * Insere RegistroNegativacao
	 * 
	 * @author Marcio Roberto
	 * @date 30/10/2007
	 * @param
	 * @return
	 * @throws ErroRepositorioException
	 */
	@Deprecated
	public Integer geraRegistroNegativacao(int idNegativador, int idUsuarioResponsavel, int saenvio, int idNegativadorComando)
					throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		String insert;
		Connection con = null;
		Statement stmt = null;
		int nextValue = 0;
		try{
			con = session.connection();
			stmt = con.createStatement();
			insert = "insert into negativador_movimento (" + " ngmv_id, " // 01
							+ " ngmv_cdmovimento, " // 02
							+ " ngmv_dtenvio, " // 03
							+ " ngmv_dtprocessamentoenvio, " // 04
							+ " ngmv_dtretorno, " // 05
							+ " ngmv_dtprocessamentoretorno, " // 06
							+ " ngmv_nnnsaenvio, " // 07
							+ " ngmv_nnnsaretorno, " // 08
							+ " ngmv_nnregistrosenvio, " // 09
							+ " ngmv_nnregistrosretorno, " // 10
							+ " ngmv_vltotalenvio, " // 11
							+ " ngmv_tmultimaalteracao, " // 12
							+ " negt_id, " // 13
							+ " ngcm_id ) " // 14
							+ " values ( " + " nextval('sq_negtvdr_movt') , " // 01
							+ " 1, " // 02
							+ " now(), " // 03
							+ " now(), " // 04
							+ "null," // 05
							+ "null," // 06
							+ (saenvio + 1) + ", " // 07
							+ "null," // 08
							+ "null," // 09
							+ "null," // 10
							+ "null," // 11
							+ " now(), " // 12
							+ idNegativador + ", " // 13
							+ idNegativadorComando + ") "; // 14
			stmt.executeUpdate(insert);
			System.out.print("1 - INSERINDO RegistroNegativadorMovimento! ");
			nextValue = this.getNegativadorMovimento();
		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate RegistroNegativadorMovimento ");
		}catch(SQLException e){
			throw new ErroRepositorioException(e, "Erro no Insert RegistroNegativadorMovimento ");
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
		return nextValue;
	}

	/**
	 * Insere geraRegistroNegativacaoReg
	 * 
	 * @author Marcio Roberto
	 * @date 30/10/2007
	 * @param
	 * @return
	 * @throws ErroRepositorioException
	 */
	@Deprecated
	public Integer geraRegistroNegativacaoReg(int idNegativador, int idUsuarioResponsavel, int saenvio, int idNegativadorComando,
					int idNegativacaoMovimento, StringBuilder registroHeader, int quantidadeRegistros) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		String insert;
		Connection con = null;
		Statement stmt = null;
		int nextValId = 0;
		try{
			con = session.connection();
			stmt = con.createStatement();
			// MARCIO
			Integer idNrTipo = this.getTpoRegistro(idNegativador);

			insert = "insert into negativador_movimento_reg (" + " nmrg_id, " // 01
							+ " ngmv_id, " // 02
							+ " nmrg_idreginclusao, " // 03
							+ " nrtp_id, " // 04
							+ " nmrg_cnregistro, " // 05
							+ " nmrg_tmultimaalteracao, " // 06
							+ " usur_id, " // 07
							+ " nmrg_cdexclusaotipo, " // 08
							+ " nmrg_icaceito, " // 09
							+ " nmrg_iccorrecao, " // 10
							+ " nmrg_vldebito, " // 11
							+ " cdst_id, " // 12
							+ " nmrg_dtsituacaodebito, " // 13
							+ " imov_id, " // 14
							+ " loca_id, " // 15
							+ " qdra_id, " // 16
							+ " nmrg_cdsetorcomercial, " // 17
							+ " nmrg_nnquadra, " // 18
							+ " iper_id, " // 19
							+ " ngct_id, " // 20
							+ " clie_id, " // 21
							+ " catg_id, " // 22
							+ " cpft_id, " // 23
							+ " nmrg_nncpf, " // 24
							+ " nmrg_nncnpj, " // 25
							+ " nemt_id ) " // 26
							+ " values ( " + " nextval('sq_negtvdr_movt_req'), " // 01
							+ idNegativacaoMovimento + ", " // 02
							+ "null, " // 03
							+ idNrTipo + ", " // 04
							+ "'" + registroHeader.toString() + "'" + ", " // 05
							+ " now(), " // 06
							+ "null," // 07
							+ "null," // 08
							+ "null," // 09
							+ "null," // 10
							+ "null," // 11
							+ "null," // 12
							+ "null," // 13
							+ "null," // 14
							+ "null," // 15
							+ "null," // 16
							+ "null," // 17
							+ "null," // 18
							+ "null," // 19
							+ "null," // 20
							+ "null," // 21
							+ "null," // 22
							+ "null," // 23
							+ "null," // 24
							+ "2, " // 25
							+ quantidadeRegistros + ")"; // 26

			stmt.executeUpdate(insert);
			System.out.print("1 - INSERINDO geraRegistroNegativacaoReg!! ");
			nextValId = this.getNextNegativadorMovimentoReg();
		}catch(HibernateException e){
			nextValId = 0;
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate geraRegistroNegativacaoReg ");
		}catch(SQLException e){
			nextValId = 0;
			throw new ErroRepositorioException(e, "Erro no Insert geraRegistroNegativacaoReg ");
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
		return nextValId;
	}

	/**
	 * [UC 0653] Pesquisar Comando Negativação
	 * 
	 * @author Kássia Albuquerque
	 * @date 29/10/2007
	 * @param comandoNegativacaoHelper
	 * @return Collection
	 * @throws ErroRepositorioException
	 */

	public Integer pesquisarComandoNegativacao(ComandoNegativacaoHelper comandoNegativacaoHelper) throws ErroRepositorioException{

		Integer retorno = null;
		Session session = HibernateUtil.getSession();
		Query query = null;
		StringBuffer hql = new StringBuffer();

		try{

			hql.append("select count(*) as total");
			hql.append(" from NegativacaoComando negatComando");
			hql.append(" left join negatComando.negativacaoCriterios negaCriterios");
			hql.append(" left join negatComando.usuario usu");

			query = criarCondicionaisComandoNegativacao(hql, comandoNegativacaoHelper);

			retorno = ((Number) query.uniqueResult()).intValue();

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
	 * [UC 0653] Pesquisar Comando Negativação
	 * 
	 * @author Kássia Albuquerque
	 * @date 29/10/2007
	 * @param comandoNegativacaoHelper
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Query criarCondicionaisComandoNegativacao(StringBuffer hql, ComandoNegativacaoHelper comandoNegativacaoHelper){

		Session session = HibernateUtil.getSession();

		Query query = null;

		Map parameters = new HashMap();

		Map queryString = new HashMap();

		hql.append(" where ");

		if(comandoNegativacaoHelper.getTituloComando() != null && !comandoNegativacaoHelper.getTituloComando().equals("")){

			hql.append("upper(negaCriterios.descricaoTitulo) like :descrTitulo and ");

			if(comandoNegativacaoHelper.getTipoPesquisaTituloComando().equals(ConstantesSistema.TIPO_PESQUISA_INICIAL)){
				queryString.put("descrTitulo", comandoNegativacaoHelper.getTituloComando().toUpperCase() + "%");
			}else{
				queryString.put("descrTitulo", "%" + comandoNegativacaoHelper.getTituloComando().toUpperCase() + "%");
			}
		}

		if(comandoNegativacaoHelper.getIndicadorComandoSimulado() != null
						&& !(comandoNegativacaoHelper.getIndicadorComandoSimulado().equals(new Short("3")))){
			hql.append(" negatComando.indicadorSimulacao = :indicSimulacao and ");
			parameters.put("indicSimulacao", comandoNegativacaoHelper.getIndicadorComandoSimulado());
		}

		if(comandoNegativacaoHelper.getGeracaoComandoInicio() != null && !comandoNegativacaoHelper.getGeracaoComandoInicio().equals("")){
			Date data1 = Util.formatarDataSemHora(comandoNegativacaoHelper.getGeracaoComandoInicio());
			hql.append(" negatComando.dataHoraComando >= :dtHoraComando1 and ");
			parameters.put("dtHoraComando1", data1);
		}
		if(comandoNegativacaoHelper.getGeracaoComandoFim() != null && !comandoNegativacaoHelper.getGeracaoComandoFim().equals("")){
			Date data2 = Util.formatarDataSemHora(comandoNegativacaoHelper.getGeracaoComandoFim());
			hql.append(" negatComando.dataHoraComando <= :dtHoraComando2 and ");
			parameters.put("dtHoraComando2", data2);
		}

		if(comandoNegativacaoHelper.getExecucaoComandoInicio() != null && !comandoNegativacaoHelper.getExecucaoComandoInicio().equals("")){
			Date data1 = Util.formatarDataSemHora(comandoNegativacaoHelper.getExecucaoComandoInicio());
			hql.append(" negatComando.dataHoraRealizacao >= :dtHoraRealizacao1 and ");
			parameters.put("dtHoraRealizacao1", data1);
		}
		if(comandoNegativacaoHelper.getExecucaoComandoFim() != null && !comandoNegativacaoHelper.getExecucaoComandoFim().equals("")){
			Date data2 = Util.formatarDataSemHora(comandoNegativacaoHelper.getExecucaoComandoFim());
			hql.append(" negatComando.dataHoraRealizacao <= :dtHoraRealizacao2 and ");
			parameters.put("dtHoraRealizacao2", data2);
		}

		if(comandoNegativacaoHelper.getIdUsuarioResponsavel() != null){
			hql.append(" usu.id = :usuaId and ");
			parameters.put("usuaId", comandoNegativacaoHelper.getIdUsuarioResponsavel());
		}

		// retira o " and " q fica sobrando no final da query
		hql = new StringBuffer(hql.substring(0, hql.length() - 4));

		query = session.createQuery(hql.toString());

		Set setQueryString = queryString.keySet();
		Iterator iterMapQueryString = setQueryString.iterator();
		while(iterMapQueryString.hasNext()){
			String key = (String) iterMapQueryString.next();
			String parametroString = (String) queryString.get(key);
			query.setString(key, parametroString);
		}

		query = Util.configuraParamentrosEmQuery(query, parameters);

		return query;
	}

	/**
	 * [UC 0653] Pesquisar Comando Negativação
	 * 
	 * @author Kássia Albuquerque
	 * @date 29/10/2007
	 * @param comandoNegativacaoHelper
	 * @return Collection
	 * @throws ErroRepositorioException
	 */

	public Collection pesquisarComandoNegativacaoParaPaginacao(ComandoNegativacaoHelper comandoNegativacaoHelper, Integer numeroPagina)
					throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();

		Query query = null;

		StringBuffer hql = new StringBuffer();

		try{

			hql.append("select ");
			hql.append(" negatComando.id as idNegatComando,");
			hql.append(" negaCriterios.descricaoTitulo as tituloNegatCriterio,");
			hql.append(" negatComando.indicadorSimulacao as indicadorSimulacao,");
			hql.append(" negatComando.dataHoraComando as dataHoraComando,");
			hql.append(" negatComando.dataHoraRealizacao as dataHoraRealizacao,");
			hql.append(" negatComando.quantidadeInclusoes as quantidadeInclusoes,");
			hql.append(" usu.nomeUsuario as nomeUsuario");
			hql.append(" from NegativacaoComando negatComando");
			hql.append(" left join negatComando.negativacaoCriterios negaCriterios");
			hql.append(" left join negatComando.usuario usu");

			query = criarCondicionaisComandoNegativacao(hql, comandoNegativacaoHelper);

			retorno = query.setFirstResult(10 * numeroPagina).setMaxResults(10).list();

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
	 * [UC 0653] Pesquisar Comando Negativação
	 * 
	 * @author Kássia Albuquerque
	 * @date 31/10/2007
	 * @param idComandoNegativacao
	 * @return Collection
	 * @throws ErroRepositorioException
	 */

	public Integer pesquisarDadosInclusoesComandoNegativacao(Integer idComandoNegativacao) throws ErroRepositorioException{

		Integer retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{

			consulta = "select count(*)" + "	from NegativadorMovimentoReg negMovReg" + "	inner join negMovReg.negativadorMovimento negMov"
							+ "	inner join negMov.negativacaoComando negCom" + " inner join negCom.negativador neg"
							+ "	left join neg.cliente clie" + " left join negMovReg.imovel imovNegMovReg"
							+ "	left join negMovReg.cobrancaDebitoSituacao cobDebSit" + "	left join negMovReg.usuario usur"
							+ "	where negCom.id = :idComandoNegativacao and imovNegMovReg.id is not null";

			retorno = ((Number) session.createQuery(consulta).setInteger("idComandoNegativacao", idComandoNegativacao).uniqueResult())
							.intValue();

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
	 * [UC 0653] Pesquisar Comando Negativação
	 * 
	 * @author Kássia Albuquerque
	 * @date 31/10/2007
	 * @param idComandoNegativacao
	 * @return Collection
	 * @throws ErroRepositorioException
	 */

	public Collection pesquisarDadosInclusoesComandoNegativacaoParaPaginacao(Integer idComandoNegativacao, Integer numeroPagina)
					throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{

			consulta = "select" + "	clie.nome," + "	negCom.quantidadeInclusoes," + "	negCom.valorDebito,"
							+ "	negCom.quantidadeItensIncluidos," + "	imovNegMovReg.id," + "	negMovReg.numeroCpf,"
							+ "	negMovReg.numeroCnpj," + "	negMovReg.valorDebito," + "	cobDebSit.descricao,"
							+ "	negMovReg.dataSituacaoDebito," + "	negMovReg.indicadorAceito," + "	negMovReg.indicadorCorrecao,"
							+ "	negMovReg.codigoExclusaoTipo," + "	usur.nomeUsuario" + "	from NegativadorMovimentoReg negMovReg"
							+ "	inner join negMovReg.negativadorMovimento negMov" + "	inner join negMov.negativacaoComando negCom"
							+ " inner join negCom.negativador neg" + "	left join neg.cliente clie"
							+ " left join negMovReg.imovel imovNegMovReg" + "	left join negMovReg.cobrancaDebitoSituacao cobDebSit"
							+ "	left join negMovReg.usuario usur"
							+ "	where negCom.id = :idComandoNegativacao and imovNegMovReg.id is not null";

			retorno = session.createQuery(consulta).setInteger("idComandoNegativacao", idComandoNegativacao)
							.setFirstResult(10 * numeroPagina).setMaxResults(10).list();

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
	 * [UC 0653] Pesquisar Comando Negativação
	 * 
	 * @author Kássia Albuquerque
	 * @date 09/11/2007
	 * @param idComandoNegativacao
	 * @return Object[]
	 * @throws ErroRepositorioException
	 */

	public Object[] pesquisarParametrosComandoNegativacao(Integer idComandoNegativacao) throws ErroRepositorioException{

		Object[] retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "select "
							// DADOS GERAIS

							+ " clieNeg.clie_nmcliente as negativador," // 00
							+ " negCom.ngcm_qtinclusoes as quantidadeInclusoes," // 01
							+ " negCom.ngcm_vldebito as valorTotalDebito," // 02
							+ " negCom.ngcm_qtitensincluidos as quantidadeItensIncluidos," // 03
							+ " negCri.ngct_dstitulo as tituloComando," // 04
							+ " negCri.ngct_dssolicitacao as descricaoSolicitacao," // 05
							+ " negCom.ngcm_icsimulacao as indicadorSimulacao," // 06
							+ " negCom.ngcm_dtprevista as dataPrevistaExecucao," // 07
							+ " usur.usur_nmusuario as nomeUsuario," // 08
							+ " negCri.ngct_qtmaximainclusoes as quantidadeMaximaInclusoes,"// 09

							// DADOS DO DEBITO
							+ " negCri.ngct_amreferenciacontainicial as referenciaInicial," // 10
							+ " negCri.ngct_amreferenciacontafinal as referenciaFinal," // 11
							+ " negCri.ngct_dtvencimentodebitoinicial as vencimentoInicial,"// 12
							+ " negCri.ngct_dtvencimentodebitofinal as vencimentoFinal," // 13
							+ " negCri.ngct_vlminimodebito as valoMinimoDebito," // 14
							+ " negCri.ngct_vlmaximodebito as valoMaximoDebito," // 15
							+ " negCri.ngct_qtminimacontas as qtdMinimaContas," // 16
							+ " negCri.ngct_qtmaximacontas as qtdMaximaContas," // 17
							+ " negCri.ngct_icnegativcontarevisao as indicadorContaRevisao,"// 18
							+ " negCri.ngct_icnegativguiapagamento as indicadorGuiaPagamento,"// 19
							+ " negCri.ngct_icparcelamentoatraso as indicadorParcelamentoAtraso,"// 20
							+ " negCri.ngct_nndiasparcelamentoatraso as numDiasAtrasoParcelamento,"// 21
							+ " negCri.ngct_icnegativrecbmtocartaparc as indicadorCartaParcAtraso,"// 22
							+ " negCri.ngct_nndiasatrasorecbcartaparc as numDiasAtrasoAposRecCarta,"// 23

							// DADOS DO IMOVEL

							+ " clie.clie_id as idCliente," // 24
							+ " clie.clie_nmcliente as nomeCliente," // 25
							+ " cliReTipo.crtp_dsclienterelacaotipo as tipoRelClie," // 26
							+ " negCri.ngct_icnegativimovelparalisaca as indicadorEspCobranca,"// 27
							+ " negCri.ngct_icnegativimovelsitcobranc as indicadorSitCobranca,"// 28

							// DADOS DA LOCALIZAÇÃO

							+ " locInicial.loca_nmlocalidade as locInicial," // 29
							+ " locFinal.loca_nmlocalidade as locFinal," // 30
							+ " setorInicial.stcm_nmsetorcomercial as setComInicial," // 31
							+ " setorFinal.stcm_nmsetorcomercial as setComFinal" // 32

							+ " from negativacao_comando negCom" + " inner join negativador neg on neg.negt_id = negCom.negt_id"
							+ " left join cliente clieNeg on clieNeg.clie_id = neg.clie_id"
							+ " left join negativacao_criterio negCri on negCom.ngcm_id = negCri.ngcm_id"
							+ " left join cliente clie on negCri.clie_id = clie.clie_id"
							+ " left join usuario usur on usur.usur_id = negCom.usur_id"
							+ " left join cliente_relacao_tipo cliReTipo on cliReTipo.crtp_id = negCri.crtp_id"
							+ " left join negativacao_criterio_subcatg negCritSub on negCritSub.ngct_id = negCri.ngct_id"
							+ " left join localidade locInicial on locInicial.loca_id = negCri.loca_idInicial"
							+ " left join setor_comercial setorInicial on locInicial.loca_id = setorInicial.loca_id"
							+ " left join localidade locFinal on locFinal.loca_id = negCri.loca_idFinal"
							+ " left join setor_comercial setorFinal on locFinal.loca_id = setorFinal.loca_id"
							+ " where negCom.ngcm_id = :id";

			retorno = (Object[]) session.createSQLQuery(consulta).addScalar("negativador", Hibernate.STRING)
							.addScalar("quantidadeInclusoes", Hibernate.INTEGER).addScalar("valorTotalDebito", Hibernate.BIG_DECIMAL)
							.addScalar("quantidadeItensIncluidos", Hibernate.INTEGER).addScalar("tituloComando", Hibernate.STRING)
							.addScalar("descricaoSolicitacao", Hibernate.STRING).addScalar("indicadorSimulacao", Hibernate.SHORT)
							.addScalar("dataPrevistaExecucao", Hibernate.DATE).addScalar("nomeUsuario", Hibernate.STRING)
							.addScalar("quantidadeMaximaInclusoes", Hibernate.INTEGER)

							.addScalar("referenciaInicial", Hibernate.INTEGER).addScalar("referenciaFinal", Hibernate.INTEGER)
							.addScalar("vencimentoInicial", Hibernate.DATE).addScalar("vencimentoFinal", Hibernate.DATE)
							.addScalar("valoMinimoDebito", Hibernate.BIG_DECIMAL).addScalar("valoMaximoDebito", Hibernate.BIG_DECIMAL)
							.addScalar("qtdMinimaContas", Hibernate.INTEGER).addScalar("qtdMaximaContas", Hibernate.INTEGER)
							.addScalar("indicadorContaRevisao", Hibernate.SHORT).addScalar("indicadorGuiaPagamento", Hibernate.SHORT)
							.addScalar("indicadorParcelamentoAtraso", Hibernate.SHORT)
							.addScalar("numDiasAtrasoParcelamento", Hibernate.INTEGER)
							.addScalar("indicadorCartaParcAtraso", Hibernate.SHORT)
							.addScalar("numDiasAtrasoAposRecCarta", Hibernate.INTEGER)

							.addScalar("idCliente", Hibernate.INTEGER).addScalar("nomeCliente", Hibernate.STRING)
							.addScalar("tipoRelClie", Hibernate.STRING).addScalar("indicadorEspCobranca", Hibernate.SHORT)
							.addScalar("indicadorSitCobranca", Hibernate.SHORT)

							.addScalar("locInicial", Hibernate.STRING).addScalar("locFinal", Hibernate.STRING)
							.addScalar("setComInicial", Hibernate.STRING).addScalar("setComFinal", Hibernate.STRING)
							.setInteger("id", new Integer(idComandoNegativacao).intValue()).setMaxResults(1).uniqueResult();

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
	 * [UC 0653] Pesquisar Comando Negativação
	 * 
	 * @author Kássia Albuquerque
	 * @date 22/11/2007
	 * @param idComandoNegativacao
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarTitularidadeCpfCnpjNegativacao(Integer idComandoNegativacao) throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "select " + "cpfTipo.cpft_dstipocpf as titularidadeNeg, " + "negCriTipo.ncct_nnordemselecao as ordem, "
							+ "negCriTipo.ncct_iccoincidente as coincidente, " + "cpfTipo.cpft_id as idTitularidadeNeg "

							+ "from negativacao_comando negCom "
							+ "inner join negativacao_criterio negCri on negCom.ngcm_id = negCri.ngcm_id "
							+ "inner join negativacao_criterio_cpf_tipo negCriTipo on negCriTipo.ngct_id = negCri.ngct_id "
							+ "inner join cpf_tipo cpfTipo on negCriTipo.cpft_id = cpfTipo.cpft_id " + "where negCom.ngcm_id = :id";

			retorno = session.createSQLQuery(consulta).addScalar("titularidadeNeg", Hibernate.STRING).addScalar("ordem", Hibernate.SHORT)
							.addScalar("coincidente", Hibernate.SHORT).addScalar("idTitularidadeNeg", Hibernate.INTEGER)
							.setInteger("id", new Integer(idComandoNegativacao).intValue()).list();

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
	 * [UC 0653] Pesquisar Comando Negativação
	 * 
	 * @author Kássia Albuquerque
	 * @date 23/11/2007
	 * @param idComandoNegativacao
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarGrupoCobranca(Integer idComandoNegativacao) throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "select " + "cobGrp.cbgr_dscobrancagrupo as descricaoGrupo, " + "cobGrp.cbgr_id as idGrupo "
							+ "from negativacao_comando negCom "
							+ "inner join negativacao_criterio negCri on negCom.ngcm_id = negCri.ngcm_id "
							+ "inner join negativ_crit_cobr_grupo negCriCobGrp on negCriCobGrp.ngct_id = negCri.ngct_id "
							+ "inner join cobranca_grupo cobGrp on cobGrp.cbgr_id = negCriCobGrp.cbgr_id " + "where negCom.ngcm_id = :id";

			retorno = session.createSQLQuery(consulta).addScalar("descricaoGrupo", Hibernate.STRING)
							.addScalar("idGrupo", Hibernate.INTEGER).setInteger("id", new Integer(idComandoNegativacao).intValue()).list();

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
	 * [UC 0653] Pesquisar Comando Negativação
	 * 
	 * @author Kássia Albuquerque
	 * @date 23/11/2007
	 * @param idComandoNegativacao
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarGerenciaRegional(Integer idComandoNegativacao) throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "select " + "gerReg.greg_nmregional as nomeRegional, " + "gerReg.greg_id as idRegional "
							+ "from negativacao_comando negCom "
							+ "inner join negativacao_criterio negCri on negCom.ngcm_id = negCri.ngcm_id "
							+ "inner join negativ_crit_ger_reg negCriGerReg  on negCriGerReg.ngct_id = negCri.ngct_id "
							+ "inner join gerencia_regional gerReg on gerReg.greg_id = negCriGerReg.greg_id "
							+ "where negCom.ngcm_id = :id";

			retorno = session.createSQLQuery(consulta).addScalar("nomeRegional", Hibernate.STRING)
							.addScalar("idRegional", Hibernate.INTEGER).setInteger("id", new Integer(idComandoNegativacao).intValue())
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
	 * [UC 0653] Pesquisar Comando Negativação
	 * 
	 * @author Kássia Albuquerque
	 * @date 23/11/2007
	 * @param idComandoNegativacao
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarUnidadeNegocio(Integer idComandoNegativacao) throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "select " + "uniNeg.uneg_nmunidadenegocio as unidadeNegocio, " + "uniNeg.uneg_id as idUnidadeNegocio "
							+ "from negativacao_comando negCom "
							+ "inner join negativacao_criterio negCri on negCom.ngcm_id = negCri.ngcm_id "
							+ "inner join negativ_crit_und_neg negCriUnNeg  on negCriUnNeg.ngct_id = negCri.ngct_id "
							+ "inner join unidade_negocio uniNeg on uniNeg.uneg_id = negCriUnNeg.uneg_id " + "where negCom.ngcm_id = :id";

			retorno = session.createSQLQuery(consulta).addScalar("unidadeNegocio", Hibernate.STRING)
							.addScalar("idUnidadeNegocio", Hibernate.INTEGER)
							.setInteger("id", new Integer(idComandoNegativacao).intValue()).list();

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
	 * [UC 0653] Pesquisar Comando Negativação
	 * 
	 * @author Kássia Albuquerque
	 * @date 26/11/2007
	 * @param idComandoNegativacao
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarEloPolo(Integer idComandoNegativacao) throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "select " + "loc.loca_nmlocalidade as nomeLocalidade, " + "loc.loca_id as idLocalidade "
							+ "from negativacao_comando negCom "
							+ "inner join negativacao_criterio negCri on negCom.ngcm_id = negCri.ngcm_id "
							+ "inner join negativ_crit_elo negCriElo on negCriElo.ngct_id= negCri.ngct_id "
							+ "inner join localidade loc on loc.loca_id = negCriElo.loca_id " + "where negCom.ngcm_id = :id";

			retorno = session.createSQLQuery(consulta).addScalar("nomeLocalidade", Hibernate.STRING)
							.addScalar("idLocalidade", Hibernate.INTEGER).setInteger("id", new Integer(idComandoNegativacao).intValue())
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
	 * [UC 0653] Pesquisar Comando Negativação
	 * 
	 * @author Kássia Albuquerque
	 * @date 26/11/2007
	 * @param idComandoNegativacao
	 * @return Collection
	 * @throws ErroRepositorioException
	 */

	public Collection pesquisarSubcategoria(Integer idComandoNegativacao) throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "select " + "sub.scat_dssubcategoria as decricaoSubcategoria, " + "sub.scat_id as idSubcategoria "

			+ "from negativacao_comando negCom " + "inner join negativacao_criterio negCri on negCom.ngcm_id = negCri.ngcm_id "
							+ "inner join negativacao_criterio_subcatg negCriSub on negCriSub.ngct_id = negCri.ngct_id "
							+ "inner join subcategoria sub on sub.scat_id = negCriSub.scat_id " + "where negCom.ngcm_id = :id";

			retorno = session.createSQLQuery(consulta).addScalar("decricaoSubcategoria", Hibernate.STRING)
							.addScalar("idSubcategoria", Hibernate.INTEGER).setInteger("id", new Integer(idComandoNegativacao).intValue())
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
	 * [UC 0653] Pesquisar Comando Negativação
	 * 
	 * @author Kássia Albuquerque
	 * @date 26/11/2007
	 * @param idComandoNegativacao
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarPerfilImovel(Integer idComandoNegativacao) throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "select " + "imvPerf.iper_dsimovelperfil as decricaoPerfilImovel, " + "imvPerf.iper_id as idPerfilImovel "
							+ "from negativacao_comando negCom "
							+ "inner join negativacao_criterio negCri on negCom.ngcm_id = negCri.ngcm_id "
							+ "inner join negativacao_crit_imov_perfil negCriImPer on negCriImPer.ngct_id = negCri.ngct_id "
							+ "inner join imovel_perfil imvPerf on imvPerf.iper_id = negCriImPer.iper_id " + "where negCom.ngcm_id = :id";

			retorno = session.createSQLQuery(consulta).addScalar("decricaoPerfilImovel", Hibernate.STRING)
							.addScalar("idPerfilImovel", Hibernate.INTEGER).setInteger("id", new Integer(idComandoNegativacao).intValue())
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
	 * Obtem Negativador Movimento id
	 * 
	 * @author Marcio Roberto
	 * @date 07/11/2007
	 * @param
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer getNegativadorMovimento() throws ErroRepositorioException{

		Integer retorno = null;
		Session session = HibernateUtil.getSession();
		try{
			String hql = "select max(id) from gcom.cobranca.NegativadorMovimento ";
			retorno = ((Number) session.createQuery(hql).uniqueResult()).intValue();
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate getNegativadorComando");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * @author Marcio Roberto
	 * @date 07/11/2007
	 * @param
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer getTpoRegistro(int idNegativador) throws ErroRepositorioException{

		Integer retorno = null;
		Session session = HibernateUtil.getSession();
		try{
			StringBuffer hql = new StringBuffer(" select ").append(" negTp.id ")
							.append(" from gcom.cobranca.NegativadorRegistroTipo negTp ").append(" inner join negTp.negativador neg ")
							.append(" where neg.id = :idNegativador ");

			retorno = (Integer) session.createQuery(hql.toString()).setInteger("idNegativador", idNegativador).uniqueResult();
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate getTpoRegistro");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * @author Marcio Roberto
	 * @date 08/11/2007
	 * @param
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer getSaEnvioContratoNegativador(int idNegativador) throws ErroRepositorioException{

		Integer retorno = null;
		Session session = HibernateUtil.getSession();
		try{
			StringBuffer hql = new StringBuffer("select ").append("   max(negCon.numeroSequencialEnvio) ")
							.append(" from gcom.cobranca.NegativadorContrato negCon ")
							.append(" where negCon.negativador.id = :idNegativador ")
							.append(" and  (negCon.dataContratoEncerramento = null or negCon.dataContratoEncerramento > now()) ");
			retorno = (Integer) session.createQuery(hql.toString()).setInteger("idNegativador", idNegativador).uniqueResult();
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate getNegativadorComando");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * Obtem getDadosEnderecoCliente
	 * 
	 * @author Marcio Roberto
	 * @date 08/11/2007
	 * @param
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List getDadosEnderecoCliente(int idCliente) throws ErroRepositorioException{

		List retorno = null;
		Session session = HibernateUtil.getSession();
		try{
			String hql = "select " + " cli_ender " + " from gcom.cadastro.cliente.ClienteEndereco cli_ender "
							+ "  inner join fetch cli_ender.logradouroCep logCep " + "  inner join fetch cli_ender.cliente cli "
							+ "  inner join fetch cli_ender.logradouroBairro logBairr " + "  inner join fetch logBairr.bairro bairr "
							+ "  inner join fetch logCep.cep cep " + " where cli.id = :idCliente "
							+ " and cli_ender.indicadorEnderecoCorrespondencia = 1 ";

			retorno = session.createQuery(hql).setInteger("idCliente", idCliente).list();
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate getDadosEnderecoCliente");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * Obtem getDadosEnderecoClienteAlternativo
	 * 
	 * @author Marcio Roberto
	 * @date 08/11/2007
	 * @param
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List getBairroCep(int idCliEnder) throws ErroRepositorioException{

		List retorno = null;
		Session session = HibernateUtil.getSession();
		try{
			String hql = "select " + " cep.bairro " + " from gcom.cadastro.endereco.LogradouroCep logCep " + " inner join logCep.cep cep "
							+ " where logCep.id = :idCliEnder ";

			retorno = session.createQuery(hql).setInteger("idCliEnder", idCliEnder).list();
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate getDadosEnderecoClienteAlternativo");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * Obtem getDadosEnderecoClienteAlternativo
	 * 
	 * @author Marcio Roberto
	 * @date 08/11/2007
	 * @param
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List getCep(int idCliEnder) throws ErroRepositorioException{

		List retorno = null;
		Session session = HibernateUtil.getSession();
		try{
			String hql = "select " + " logCep " + " from gcom.cadastro.endereco.LogradouroCep logCep " + " inner join logCep.cep cep "
							+ " where logCep.id = :idCliEnder ";

			retorno = session.createQuery(hql).setInteger("idCliEnder", idCliEnder).list();
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate getDadosEnderecoClienteAlternativo");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * Obtem getMunicipio
	 * 
	 * @author Marcio Roberto
	 * @date 08/11/2007
	 * @param
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List getMunicipio(int idLogradouroBairro) throws ErroRepositorioException{

		List retorno = null;
		Session session = HibernateUtil.getSession();
		try{
			String hql = "select " + " logBairro " + " from gcom.cadastro.endereco.LogradouroBairro logBairro "
							+ " inner join logBairro.bairro bairro " + " inner join bairro.municipio muni "
							+ " where logBairro.id = :idLogradouroBairro ";

			retorno = session.createQuery(hql).setInteger("idLogradouroBairro", idLogradouroBairro).list();
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate getMunicipio");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * Obtem getMunicipioCep
	 * 
	 * @author Marcio Roberto
	 * @date 08/11/2007
	 * @param
	 * @return
	 * @throws ErroRepositorioException
	 */
	public String getMunicipioCep(int idCliEnder) throws ErroRepositorioException{

		String retorno = null;
		Session session = HibernateUtil.getSession();
		try{
			String hql = "select " + " cep.municipio " + " from gcom.cadastro.endereco.LogradouroCep logCep "
							+ " inner join logCep.cep cep " + " where logCep.id = :idCliEnder ";

			retorno = (String) session.createQuery(hql).setInteger("idCliEnder", idCliEnder).uniqueResult();
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate getMunicipioCep");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * Obtem getUnidadeFederativa
	 * 
	 * @author Marcio Roberto
	 * @date 08/11/2007
	 * @param
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List getUnidadeFederativa(int idLogradouroBairro) throws ErroRepositorioException{

		List retorno = null;
		Session session = HibernateUtil.getSession();
		try{
			String hql = "select " + " logBairro " + " from gcom.cadastro.endereco.LogradouroBairro logBairro "
							+ " inner join fetch logBairro.bairro bairro " + " inner join fetch bairro.municipio muni "
							+ " inner join fetch muni.unidadeFederacao unFe " + " where logBairro.id = :idLogradouroBairro ";

			retorno = session.createQuery(hql).setInteger("idLogradouroBairro", idLogradouroBairro).list();
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate getUnidadeFederativa");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * Obtem getDDD
	 * 
	 * @author Marcio Roberto
	 * @date 08/11/2007
	 * @param
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List getDddFone(int idCliente) throws ErroRepositorioException{

		List retorno = null;
		Session session = HibernateUtil.getSession();
		try{

			String hql = "select " + " cliFone " + " from gcom.cadastro.cliente.ClienteFone cliFone " + " inner join cliFone.cliente cli "
							+ " where cliFone.cliente.id = :idCliente " + " and cliFone.indicadorTelefonePadrao = 1";

			retorno = session.createQuery(hql).setInteger("idCliente", idCliente).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate getDDD");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * Obtem geraRegistroNegativacaoRegDetalhe
	 * 
	 * @author Marcio Roberto
	 * @date 08/11/2007
	 * @param
	 * @return
	 * @throws ErroRepositorioException
	 */
	@Deprecated
	public void geraRegistroNegativacaoRegDetalhe(int idNegativador, int idUsuarioResponsavel, int saenvio, int idNegativadorComando,
					int idNegativacaoMovimento, StringBuilder registro, int quantidadeRegistros) throws ErroRepositorioException{

		Session session = HibernateUtil.getSessionGerencial();
		String insert;
		Connection con = null;
		Statement stmt = null;
		try{
			con = session.connection();
			stmt = con.createStatement();

			Integer idNrTipo = this.getTpoRegistro(idNegativador);

			insert = "insert into negativador_movimento_reg (" + " nmrg_id, " // 01
							+ " ngmv_id, " // 02
							+ " nmrg_idreginclusao, " // 03
							+ " nrtp_id, " // 04
							+ " nmrg_cnregistro, " // 05
							+ " nmrg_tmultimaalteracao, " // 06
							+ " usur_id, " // 07
							+ " nmrg_cdexclusaotipo, " // 08
							+ " nmrg_icaceito, " // 09
							+ " nmrg_iccorrecao, " // 10
							+ " nmrg_vldebito, " // 11
							+ " cdst_id, " // 12
							+ " nmrg_dtsituacaodebito, " // 13
							+ " imov_id, " // 14
							+ " loca_id, " // 15
							+ " qdra_id, " // 16
							+ " nmrg_cdsetorcomercial, " // 17
							+ " nmrg_nnquadra, " // 18
							+ " iper_id, " // 19
							+ " ngct_id, " // 20
							+ " clie_id, " // 21
							+ " catg_id, " // 22
							+ " cpft_id, " // 23
							+ " nmrg_nncpf, " // 24
							+ " nmrg_nncnpj, " // 25
							+ " nemt_id ) " // 26
							+ " values ( " + " nextval('sq_negtvdr_movt_reg'), " // 01
							+ idNegativacaoMovimento + ", " // 02
							+ "null, " // 03
							+ idNrTipo + ", " // 04
							+ registro.toString() + ", " // 05
							+ " now(), " // 06
							+ "null," // 07
							+ "null," // 08
							+ "null," // 09
							+ "null," // 10
							+ "null," // 11
							+ "null," // 12
							+ "null," // 13
							+ "null," // 14
							+ "null," // 15
							+ "null," // 16
							+ "null," // 17
							+ "null," // 18
							+ "null," // 19
							+ "null," // 20
							+ "null," // 21
							+ "null," // 22
							+ "null," // 23
							+ "null," // 24
							+ "2, " // 25
							+ quantidadeRegistros + ")"; // 26

			stmt.executeUpdate(insert);
			System.out.print("1 - INSERINDO geraRegistroNegativacaoRegDetalhe!! ");
		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate geraRegistroNegativacaoRegDetalhe ");
		}catch(SQLException e){
			throw new ErroRepositorioException(e, "Erro no Insert geraRegistroNegativacaoRegDetalhe ");
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
	 * Obtem geraRegistroNegativacaoMovimentoReg
	 * 
	 * @author Marcio Roberto
	 * @date 12/11/2007
	 * @param
	 * @return
	 * @throws ErroRepositorioException
	 */
	@Deprecated
	public void geraRegistroNegativacaoRegDetalheSPC(int idNegativador, int idUsuarioResponsavel, int saenvio, int idNegativadorComando,
					int idNegativacaoMovimento, StringBuilder registro, int quantidadeRegistros, BigDecimal valorTotalDebitos,
					int idDebitoSituacao, int idImovel, int idLocalidade, int idQuadra, int stComercialCD, int numeroQuadra, int iper_id,
					int idCliente, int idCategoria, String cpfCliente, String cnpjCliente) throws ErroRepositorioException{

		Session session = HibernateUtil.getSessionGerencial();
		StringBuffer insert = new StringBuffer();
		Connection con = null;
		Statement stmt = null;
		try{
			con = session.connection();
			stmt = con.createStatement();

			Integer idNrTipo = this.getTpoRegistro(idNegativador);

			insert.append("insert into negativador_movimento_reg (").append(" nmrg_id, ") // 01
							.append(" ngmv_id, ") // 02
							.append(" nmrg_idreginclusao, ") // 03
							.append(" nrtp_id, ") // 04
							.append(" nmrg_cnregistro, ") // 05
							.append(" nmrg_tmultimaalteracao, ") // 06
							.append(" usur_id, ") // 07
							.append(" nmrg_cdexclusaotipo, ") // 08
							.append(" nmrg_icaceito, ") // 09
							.append(" nmrg_iccorrecao, ") // 10
							.append(" nmrg_vldebito, ") // 11
							.append(" cdst_id, ") // 12
							.append(" nmrg_dtsituacaodebito, ") // 13
							.append(" imov_id, ") // 14
							.append(" loca_id, ") // 15
							.append(" qdra_id, ") // 16
							.append(" nmrg_cdsetorcomercial, ") // 17
							.append(" nmrg_nnquadra, ") // 18
							.append(" iper_id, ") // 19
							.append(" ngct_id, ") // 20
							.append(" clie_id, ") // 21
							.append(" catg_id, ") // 22
							.append(" nemt_id, ") // 23
							.append(" nmrg_nncpf, ") // 24
							.append(" nmrg_nncnpj, ") // 25
							.append(" cpft_id, ") // 26
							.append(" nmrg_icsitdefinitiva, ") // 27
							.append(" nmrg_nnregistro) ") // 28
							.append(" values ( ").append(" SQ_NEGTVDR_MOVT_REG.NEXTVAL, ") // 01
							.append(idNegativacaoMovimento).append(", ") // 02
							.append("null, ") // 03
							.append(idNrTipo).append(", ") // 04
							.append(registro).append(", ") // 05
							.append(" current_timestamp, ") // 06
							.append("null,") // 07
							.append("null,") // 08
							.append("null,") // 09
							.append("null,") // 10
							.append(valorTotalDebitos).append(", ") // 11
							.append(idDebitoSituacao).append(", ") // 12
							.append(Util.formatarData(new Date())).append(" ,") // 13
							.append(idImovel).append(", ") // 14
							.append(idLocalidade).append(", ") // 15
							.append(idQuadra).append(", ") // 16
							.append(stComercialCD).append(", ") // 17
							.append(numeroQuadra).append(", ") // 18
							.append(iper_id).append(", ") // 19
							.append("null,") // 20
							.append(idCliente).append(", ") // 21
							.append(idCategoria).append(", ") // 22
							.append("null,") // 23
							.append(cpfCliente).append(", ") // 24
							.append(cnpjCliente).append(", ") // 25
							.append("null,") // 26
							.append("2, ") // 27
							.append(quantidadeRegistros + ")"); // 28

			stmt.executeUpdate(insert.toString());
			System.out.print("1 - INSERINDO geraRegistroNegativacaoRegDetalheSPC!! ");
		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate geraRegistroNegativacaoRegDetalheSPC ");
		}catch(SQLException e){
			throw new ErroRepositorioException(e, "Erro no Insert geraRegistroNegativacaoRegDetalheSPC ");
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
	 * obtemDebitoSituacao
	 * 
	 * @author Marcio Roberto
	 * @date 12/11/2007
	 * @param
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer obtemDebitoSituacao() throws ErroRepositorioException{

		Integer retorno;
		Session session = HibernateUtil.getSession();
		try{
			String hql = "select " + " cobDebSit.id " + " from gcom.cobranca.CobrancaDebitoSituacao cobDebSit "
							+ " where cobDebSit.descricao = " + "'" + "PENDENTE" + "'";

			retorno = (Integer) session.createQuery(hql).uniqueResult();
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate obtemDebitoSituacao");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * geraRegistroNegativacaoMovimentoRegItem
	 * 
	 * @author Marcio Roberto
	 * @date 13/11/2007
	 * @param
	 * @return
	 * @throws ErroRepositorioException
	 */
	@Deprecated
	public void geraRegistroNegativacaoMovimentoRegItem(int numeroRegistro, int idDebSit, BigDecimal valorDoc)
					throws ErroRepositorioException{

		Session session = HibernateUtil.getSessionGerencial();
		String insert;
		Connection con = null;
		Statement stmt = null;
		try{
			con = session.connection();
			stmt = con.createStatement();

			insert = "insert into negativador_movimento_reg_item (" + " nmri_id , " // 01
							+ " nmrg_id , " // 02
							+ " dotp_id , " // 03
							+ " dbac_id , " // 04
							+ " gpag_id , " // 05
							+ " cnta_id , " // 06
							+ " cdst_id , " // 07
							+ " nmri_vldebito , " // 08
							+ " nmri_dtsituacaodebito , " // 09
							+ " cdst_idaposexclusao , " // 10
							+ " nmri_dtsitdebaposexclusao , " // 11
							+ " nmri_icsitdefinitiva ) " // 12
							+ " values ( " + " nextval('sq_negtvdr_movt_reg_item'), " // 01
							+ numeroRegistro + ", " // 02
							+ "1, " // 03
							+ "null, " // 04
							+ "null, " // 05
							+ "null, " // 06
							+ idDebSit + ", " // 07
							+ valorDoc + ", " // 08
							+ " now(), " // 09
							+ "null, " // 10
							+ "null, " // 11
							+ "2) "; // 12

			stmt.executeUpdate(insert);
			System.out.print("1 - INSERINDO geraRegistroNegativacaoMovimentoRegItem!! ");
		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate geraRegistroNegativacaoMovimentoRegItem ");
		}catch(SQLException e){
			throw new ErroRepositorioException(e, "Erro no Insert geraRegistroNegativacaoMovimentoRegItem ");
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
	 * geraRegistroImovelNegativacao
	 * 
	 * @author Marcio Roberto
	 * @date 13/11/2007
	 * @param
	 * @return
	 * @throws ErroRepositorioException
	 */
	@Deprecated
	public void geraRegistroImovelNegativacao(int idNegativadorComando, int idImovel) throws ErroRepositorioException{

		Session session = HibernateUtil.getSessionGerencial();
		String insert;
		Connection con = null;
		Statement stmt = null;
		try{
			con = session.connection();
			stmt = con.createStatement();

			insert = "insert into negativacao_imoveis (" + " ngim_id , " // 01
							+ " ngim_tmultimaalteracao , " // 02
							+ " ngcm_id , " // 03
							+ " imov_id , " // 04
							+ " ngim_icexcluido , " // 05
							+ " ngim_dtexclusao) " // 06
							+ " values ( " + " nextval('sq_negtvc_imov   '), " // 01
							+ idNegativadorComando + ", " // 02
							+ idImovel + ", " // 03
							+ "now(), " // 04
							+ "2, " // 05
							+ "null) "; // 06

			stmt.executeUpdate(insert);
			System.out.print("1 - INSERINDO geraRegistroImovelNegativacao!! ");
		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate geraRegistroImovelNegativacao ");
		}catch(SQLException e){
			throw new ErroRepositorioException(e, "Erro no Insert geraRegistroImovelNegativacao ");
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
	 * getNextNegativadorMovimentoReg
	 * 
	 * @author Marcio Roberto
	 * @date 14/11/2007
	 * @param
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer getNextNegativadorMovimentoReg() throws ErroRepositorioException{

		Integer retorno = null;
		Session session = HibernateUtil.getSession();
		try{
			String hql = "select max(id) from gcom.cobranca.NegativadorMovimentoReg  ";
			retorno = ((Number) session.createQuery(hql).uniqueResult()).intValue();
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate getNextNegativadorMovimentoReg");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * Obtem Negativacao Comando
	 * 
	 * @author Marcio Roberto
	 * @date 26/11/2007
	 * @param
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List getNegativacaoComando(int idNegativacaoComando) throws ErroRepositorioException{

		List retorno = null;
		Session session = HibernateUtil.getSession();
		try{

			StringBuffer hql = new StringBuffer("select ").append(" negComando ")
							.append(" from gcom.cobranca.NegativacaoComando negComando ")
							.append(" where negComando.id = :idNegativacaoComando ");

			retorno = session.createQuery(hql.toString()).setInteger("idNegativacaoComando", idNegativacaoComando).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate getNegativacaoComando");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * Obtem Negativacao Criterio
	 * 
	 * @author Marcio Roberto
	 * @date 26/11/2007
	 * @param
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List getNegativacaoCriterio(int idNegativacaoComando) throws ErroRepositorioException{

		List retorno = null;
		Session session = HibernateUtil.getSession();
		try{

			StringBuffer hql = new StringBuffer("select ").append(" negCriterio ")
							.append(" from gcom.cobranca.NegativacaoCriterio negCriterio ")
							.append(" inner join fetch negCriterio.negativacaoComando negComando ")
							.append(" where negComando.id = :idNegativacaoComando ");

			retorno = session.createQuery(hql.toString()).setInteger("idNegativacaoComando", idNegativacaoComando).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate getNegativacaoCriterio");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * Obtem Negativacao Criterio
	 * 
	 * @author Marcio Roberto
	 * @date 26/11/2007
	 * @param
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection getImoveisClienteCriterio(int idCliente) throws ErroRepositorioException{

		Collection retorno = null;
		Session session = HibernateUtil.getSession();
		try{

			StringBuffer hql = new StringBuffer(" select ").append("   imov ").append(" from gcom.cadastro.imovel.Imovel imov, ")
							.append("      gcom.cobranca.NegativacaoCriterio negCriterio ")
							.append("   inner join fetch imov.clienteImoveis clienteImov ")
							.append("   inner join fetch imov.imovelPerfil imovPerf ")
							.append("   inner join fetch negCriterio.cliente cli ").append(" where cli.id = :idCliente ");

			retorno = session.createQuery(hql.toString()).setInteger("idCliente", idCliente).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate getNegativacaoCriterio");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * [UC 0653] Pesquisar Comando Negativação
	 * 
	 * @author Kássia Albuquerque
	 * @date 26/11/2007
	 * @param idComandoNegativacao
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarTipoCliente(Integer idComandoNegativacao) throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "select " + "clTipo.cltp_dsclientetipo as descricaoClienteTipo, " + "clTipo.cltp_id as idClienteTipo "
							+ "from negativacao_comando negCom "
							+ "inner join negativacao_criterio negCri on negCom.ngcm_id = negCri.ngcm_id "
							+ "inner join negativacao_criterio_cli_tipo criCliTip on criCliTip.ngct_id = negCri.ngct_id "
							+ "inner join cliente_tipo clTipo on  clTipo.cltp_id = criCliTip.cltp_id " + "where negCom.ngcm_id = :id";

			retorno = session.createSQLQuery(consulta).addScalar("descricaoClienteTipo", Hibernate.STRING)
							.addScalar("idClienteTipo", Hibernate.INTEGER).setInteger("id", new Integer(idComandoNegativacao).intValue())
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
	 * Verifica se há negativação para aquele imovel
	 * 
	 * @author Marcio Roberto
	 * @date 29/11/2007
	 * @param
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer verificaExistenciaNegativacao(int idImovel, int idNegativador) throws ErroRepositorioException{

		Integer retorno = null;
		Session session = HibernateUtil.getSession();
		try{

			StringBuffer hql = new StringBuffer(" select ").append("  count(negImovel.id) ")
							.append(" from gcom.cobranca.NegativacaoImovei negImovel ").append("   inner join negImovel.imovel imov ")
							.append("   inner join negImovel.negativacaoComando negCom ").append("   inner join negCom.negativador neg ")
							.append(" where negImovel.indicadorExcluido = 2 ").append(" and negImovel.imovel.id = :idImovel ")
							.append(" and neg.id = :idNegativador ");

			retorno = ((Number) session.createQuery(hql.toString()).setInteger("idImovel", idImovel)
							.setInteger("idNegativadorComando", idNegativador).uniqueResult()).intValue();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate verificaExistenciaNegativacao");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * obtem dados cliente da negativacao
	 * 
	 * @author Marcio Roberto
	 * @date 29/11/2007
	 * @param
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List obtemTitularidadesDocumentos(int idNegativadorCriterio) throws ErroRepositorioException{

		List retorno = null;
		Session session = HibernateUtil.getSession();
		try{

			StringBuffer hql = new StringBuffer(" select ").append("  negCritCpfTp ")
							.append(" from gcom.cobranca.NegativacaoCriterioCpfTipo negCritCpfTp ")
							.append("   inner join fetch negCritCpfTp.negativacaoCriterio negCrit ")
							.append("   inner join fetch negCritCpfTp.cpfTipo cpfTipo ")
							.append(" where negCritCpfTp.negativacaoCriterio.id = :idNegativadorCriterio ")
							.append(" order by negCritCpfTp.numeroOrdemSelecao ");

			retorno = session.createQuery(hql.toString()).setInteger("idNegativadorCriterio", idNegativadorCriterio).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate obtemTitularidadesDocumentos");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * obtem titularidade dos documentos
	 * 
	 * @author Marcio Roberto
	 * @date 29/11/2007
	 * @param
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List obtemDadosClienteNegativacao(int idImovel, int idTitularidade) throws ErroRepositorioException{

		List retorno = null;
		Session session = HibernateUtil.getSession();
		try{

			if(idTitularidade == 1 || idTitularidade == 2 || idTitularidade == 3){
				StringBuffer hql = new StringBuffer(" select ").append("  cliente ").append(" from gcom.cadastro.cliente.Cliente cliente ")
								.append("   inner join fetch cliente.clienteImoveis cliImov ")
								.append("   inner join fetch cliImov.imovel imov ").append(" where cliImov.dataFimRelacao is null ")
								.append(" and imov.id = :idImovel ").append(" and cliImov.clienteRelacaoTipo = :idTitularidade ");

				retorno = session.createQuery(hql.toString()).setInteger("idImovel", idImovel).setInteger("idTitularidade", idTitularidade)
								.list();
			}else if(idTitularidade == 3){
				String hql = " select " + "  cliente " + " from gcom.cobranca.parcelamento.Parcelamento parc "
								+ "   inner join fetch parc.cliente cliente " + "   inner join fetch parc.imovel imov "
								+ " where imov.id = :idImovel ";
				retorno = session.createQuery(hql).setInteger("idImovel", idImovel).list();
			}
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate obtemDadosClienteNegativacao");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * Verifica se há ocorrencia do imovel na tabela cobranca situacao.
	 * 
	 * @author Marcio Roberto
	 * @date 03/12/2007
	 * @param
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer verificaExistenciaImovelCobrancaSituacao(int idImovel) throws ErroRepositorioException{

		Integer retorno = null;
		Session session = HibernateUtil.getSession();
		try{

			StringBuffer hql = new StringBuffer(" select ").append("  count(imoCobSit.id) ")
							.append(" from gcom.cadastro.imovel.ImovelCobrancaSituacao imoCobSit ")
							.append("   inner join fetch imoCobSit.imovel imov ").append(" where imoCobSit.imovel.id = :idImovel ")
							.append(" and imoCobSit.dataRetiradaCobranca is null ");

			retorno = ((Number) session.createQuery(hql.toString()).setInteger("idImovel", idImovel).uniqueResult()).intValue();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate verificaExistenciaImovelCobrancaSituacao");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * Verifica se as subCategorias do imovel corresponde as subCategorias do criterio da
	 * negativacao.
	 * 
	 * @author Marcio Roberto
	 * @date 03/12/2007
	 * @param
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer verificaSubCategoriaImovelNegativacaoCriterio(int idImovel, int idCriterio) throws ErroRepositorioException{

		Integer retorno = null;
		Session session = HibernateUtil.getSession();
		try{

			StringBuffer hql = new StringBuffer(" select ").append("   count( negCriSubCat.comp_id.subcategoria.id) ")
							.append(" from gcom.cobranca.NegativacaoCriterioSubcategoria negCriSubCat ")
							.append(" where negCriSubCat.comp_id.subcategoria.id in (select ")
							.append(" 	  											subCat.comp_id.subcategoria.id ")
							.append(" 												from gcom.cadastro.imovel.ImovelSubcategoria subCat ")
							.append(" 												where subCat.comp_id.imovel.id = :idImovel) ").append(" and ")
							.append(" negCriSubCat.comp_id.negativacaoCriterio.id = :idCriterio ");

			retorno = ((Number) session.createQuery(hql.toString()).setInteger("idImovel", idImovel).setInteger("idCriterio", idCriterio)
							.uniqueResult()).intValue();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate verificaSubCategoriaImovelNegativacaoCriterio");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * Verifica se os Perfis do imovel corresponde aos Perfis do criterio da negativacao.
	 * 
	 * @author Marcio Roberto
	 * @date 03/12/2007
	 * @param
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer verificaPerfilImovelNegativacaoCriterio(int idCriterio, int imovelPerfil) throws ErroRepositorioException{

		Integer retorno = null;
		Session session = HibernateUtil.getSession();
		try{

			StringBuffer hql = new StringBuffer(" select ").append(" count( negCriImoPer.comp_id.imovelPerfil) ")
							.append(" from gcom.cobranca.NegativacaoCriterioImovelPerfil negCriImoPer ")
							.append(" where negCriImoPer.comp_id.negativacaoCriterio.id = :idCriterio ")
							.append(" and negCriImoPer.comp_id.imovelPerfil = :imovelPerfil ");

			retorno = ((Number) session.createQuery(hql.toString()).setInteger("idCriterio", idCriterio)
							.setInteger("imovelPerfil", imovelPerfil).uniqueResult()).intValue();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate verificaPerfilImovelNegativacaoCriterio");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * Verifica se o cliente usuario do imovel corresponde ao cliente tipo da negativacao criterio
	 * 
	 * @author Marcio Roberto
	 * @date 03/12/2007
	 * @param
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer verificaTipoClienteNegativacaoCriterio(int idImovel, int idCriterio) throws ErroRepositorioException{

		Integer retorno = null;
		Session session = HibernateUtil.getSession();
		try{

			StringBuffer hql = new StringBuffer(" select ").append("  count(negCriCliTip.comp_id.clienteTipo) ")
							.append(" from gcom.cobranca.NegativacaoCriterioClienteTipo negCriCliTip ")
							.append(" where negCriCliTip.comp_id.clienteTipo = (select ").append("   case when (")
							.append("     cliente.clienteTipo.id is null ) then").append("     0").append("   else")
							.append("     cliente.clienteTipo.id").append("   end").append(" from gcom.cadastro.cliente.Cliente cliente ")
							.append("   inner join cliente.clienteImoveis cliImov ").append("   inner join cliImov.imovel imov ")
							.append(" where cliImov.dataFimRelacao is null ").append(" and imov.id = :idImovel ")
							.append(" and cliImov.clienteRelacaoTipo = 2)") // cliente
							// usuário = 2
							.append(" and negCriCliTip.comp_id.negativacaoCriterio = :idCriterio ");

			retorno = ((Number) session.createQuery(hql.toString()).setInteger("idImovel", idImovel).setInteger("idCriterio", idCriterio)
							.uniqueResult()).intValue();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate verificaTipoClienteNegativacaoCriterio");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * Verifica ocorrencia debito cobrado conta imovel.
	 * 
	 * @author Marcio Roberto
	 * @date 05/12/2007
	 * @param
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer verificaDebitoCobradoConta(int idConta) throws ErroRepositorioException{

		Integer retorno = 0;
		Session session = HibernateUtil.getSession();
		try{

			StringBuffer hql = new StringBuffer(" select ").append("  imov.id ")
							.append(" from gcom.faturamento.debito.DebitoCobrado debCob ").append("  inner join debCob.conta conta ")
							.append("  inner join debCob.financiamentoTipo finTipo ").append("  inner join conta.imovel imov ")
							.append(" where finTipo.id in (2,3,4,8) ").append("  and conta.id = :idConta ");

			retorno = (Integer) session.createQuery(hql.toString()).setInteger("idConta", idConta).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate verificaDebitoCobradoConta");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * Verifica ocorrencia debito cobrado conta imovel.
	 * 
	 * @author Marcio Roberto
	 * @date 05/12/2007
	 * @param
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List verificaImovelParcelamento(int idImovel) throws ErroRepositorioException{

		List retorno;
		Session session = HibernateUtil.getSession();
		try{

			StringBuffer hql = new StringBuffer(" select parc ").append(" from gcom.cobranca.parcelamento.Parcelamento as parc ")
							.append(" inner join parc.imovel as imov ").append(" inner join fetch parc.cliente as cli ")
							.append(" where  imov.id = :idImovel ").append(" order by parc.parcelamento desc ");

			retorno = (List) session.createQuery(hql.toString()).setInteger("idImovel", idImovel).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate verificaImovelParcelamento");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * Verifica ocorrencia debito cobrado conta imovel.
	 * 
	 * @author Marcio Roberto
	 * @date 05/12/2007
	 * @param
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer verificaCartaAvisoParcelamento(int idImovel, int numeroDiasAtrasoRecebCartaParcel) throws ErroRepositorioException{

		Integer retorno;
		Session session = HibernateUtil.getSession();
		try{

			StringBuffer hql = new StringBuffer(" select ").append("  count(docCob.id) ")
							.append(" from gcom.cobranca.CobrancaDocumento docCob ").append("  inner join docCob.documentoTipo docTipo ")
							.append("  inner join docCob.imovel imov ").append(" where  imov.id = :idImovel ")
							.append(" and docCob.emissao < (now()-").append(numeroDiasAtrasoRecebCartaParcel).append(") ")
							.append(" and docTipo.id = 26 ");

			retorno = ((Number) session.createQuery(hql.toString()).setInteger("idImovel", idImovel).uniqueResult()).intValue();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate verificaCartaAvisoParcelamento");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * Referente a [SB0004] UC0671 - Gerar Movimento de Inclusão de Negativação
	 * Condições 1,2,3,4,5 e 6 referente a diferentes critérios
	 * 
	 * @author Marcio Roberto
	 * @date 10/12/2007
	 * @param
	 * @return List
	 * @throws ErroRepositorioException
	 */
	public List getImovelCondicao(int idNegativacaoCriterio, int tipoCondicao) throws ErroRepositorioException{

		List retorno;
		Session session = HibernateUtil.getSession();
		StringBuffer hql = new StringBuffer();
		try{
			switch(tipoCondicao){
				case 1:
					// condicao 1
					hql.append(" select ")
									.append("  imov ")
									.append(" from gcom.cadastro.imovel.Imovel imov ")
									.append("  inner join fetch imov.quadra quad ")
									.append("  inner join fetch quad.rota rot ")
									.append(" where  rot.cobrancaGrupo.id in (select ")
									.append("                                  negCritGrupo.comp_id.cobrancaGrupo")
									.append("                                 from gcom.cobranca.NegativCritCobrGrupo negCritGrupo ")
									.append("                                 where negCritGrupo.comp_id.negativacaoCriterio = :idNegativacaoCriterio)");
					break;
				case 2:
					// condicao 2
					hql.append(" select ")
									.append("  imov ")
									.append(" from gcom.cadastro.imovel.Imovel imov ")
									.append(" inner join fetch imov.localidade loc ")
									.append(" inner join fetch loc.unidadeNegocio undNeg ")
									.append(" where undNeg.gerenciaRegional.id in (select ")
									.append("                                       negCriGerReg.comp_id.gerenciaRegional ")
									.append("                                      from gcom.cobranca.NegativCritGerReg negCriGerReg ")
									.append("                                      where negCriGerReg.comp_id.negativacaoCriterio = :idNegativacaoCriterio)");
					break;
				case 3:
					// condicao 3
					hql.append(" select ")
									.append("  imov ")
									.append(" from gcom.cadastro.imovel.Imovel imov ")
									.append(" inner join fetch imov.localidade loc ")
									.append(" inner join fetch loc.unidadeNegocio undNeg ")
									.append(" where undNeg.id in (select ")
									.append("                     negCriUndNeg.comp_id.unidadeNegocio ")
									.append("                     from gcom.cobranca.NegativCritUndNeg negCriUndNeg ")
									.append("                     where negCriUndNeg.comp_id.negativacaoCriterio = :idNegativacaoCriterio)");
					break;
				case 4:
					// condicao 4
					hql.append(" select ").append("  imov ").append(" from gcom.cadastro.imovel.Imovel imov ")
									.append(" inner join fetch imov.localidade loc ").append(" where loc.id in (select ")
									.append("                  negCriElo.comp_id.localidade ")
									.append("                  from gcom.cobranca.NegativCritElo negCriElo ")
									.append("                  where negCriElo.comp_id.negativacaoCriterio = :idNegativacaoCriterio)");
					break;
				case 5:
					// condicao 5
					hql.append(" select ").append("  imov ").append(" from gcom.cadastro.imovel.Imovel imov ")
									.append(" inner join fetch imov.localidade loc ").append(" where ( loc.id in (select ")
									.append("                    negCri.localidadeInicial.id   ")
									.append("                    from gcom.cobranca.NegativacaoCriterio negCri ")
									.append("                    where negCri.id = :idNegativacaoCriterio ")
									.append("                    and negCri.localidadeInicial.id is not null")
									.append("                    and negCri.codigoSetorComercialInicial is null)")
									.append(" or      loc.id in (select ").append("                    negCri.localidadeFinal.id ")
									.append("                    from gcom.cobranca.NegativacaoCriterio negCri ")
									.append("                    where negCri.id = :idNegativacaoCriterio ")
									.append("                    and negCri.localidadeInicial.id is not null ")
									.append("                    and negCri.codigoSetorComercialInicial is null)").append("         ) ");
					break;
				case 6:
					// condicao 6
					hql.append(" select ").append(" imov ").append(" from gcom.cadastro.imovel.Imovel imov ")
									.append(" inner join fetch imov.setorComercial setCom ")
									.append(" inner join fetch setCom.localidade loc  ").append(" where ( loc.id in (select ")
									.append("                    negCri.localidadeInicial.id ")
									.append("                    from gcom.cobranca.NegativacaoCriterio negCri ")
									.append("                    inner join negCri.localidadeInicial locIni ")
									.append("                    where negCri.id = :idNegativacaoCriterio ")
									.append("                    and locIni.id is not null)) ").append(" and (setCom.id in (select ")
									.append("                     negCri.codigoSetorComercialInicial ")
									.append("                     from gcom.cobranca.NegativacaoCriterio negCri ")
									.append("                     inner join negCri.localidadeInicial locIni ")
									.append("                     where negCri.id = :idNegativacaoCriterio ")
									.append("                     and locIni.id is not null ")
									.append("                     and negCri.codigoSetorComercialInicial is not null) ")
									.append("   or setCom.id in (select  ").append("                    negCri.codigoSetorComercialFinal ")
									.append("                    from gcom.cobranca.NegativacaoCriterio negCri ")
									.append("                    inner join negCri.localidadeInicial locIni ")
									.append("                    where negCri.id = :idNegativacaoCriterio ")
									.append("                    and locIni.id is not null ")
									.append("                    and negCri.codigoSetorComercialInicial is not null) ").append("     ) ");
					break;

				default:
					hql.append(" select ").append(" imov ").append(" from gcom.cadastro.imovel.Imovel imov ");
			}
			retorno = (List) session.createQuery(hql.toString()).setInteger("idNegativacaoCriterio", idNegativacaoCriterio).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate getImovelCindicao");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * getNextNegativadorComando
	 * 
	 * @author Marcio Roberto
	 * @date 19/12/2007
	 * @param
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer getNextNegativadorComando() throws ErroRepositorioException{

		Integer retorno = null;
		Session session = HibernateUtil.getSession();
		try{
			String hql = "select max(id) from gcom.cobranca.NegativacaoComando ";
			retorno = ((Number) session.createQuery(hql).uniqueResult()).intValue();
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate getNextNegativadorComando ");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * Método consuta os Negativadores que tenham movimento de Exclusão do spc ou serasa
	 * [UC0673] - Gerar Movimento da Exclusão de Negativação
	 * [SB0003] - Selecionar Negativadores
	 * 
	 * @author Thiago Toscano
	 * @date 21/12/2007
	 */
	public Collection consultarNegativadoresParaExclusaoMovimento() throws ErroRepositorioException{

		List retorno = new ArrayList();
		Session session = HibernateUtil.getSession();
		try{

			// String data1 = Util.recuperaDataInvertida(new Date());

			String consulta = " select distinct(n.negt_id ) as idNegativador, c.clie_id as idCliente, c.clie_nmcliente  as nomeCliente "
							+ "  from  negativador_movimento_reg nmr, "
							+ " negativador_movimento nm ,  "
							+ " negativador_registro_tipo nrt, "
							+ " negativador n,  "
							+ " cliente c  "
							+ " where  "
							+ " nmr.nrtp_id = nrt.nrtp_id and "
							+ " nmr.ngmv_id = nm.ngmv_id and  "
							+ " n.negt_id = nm.negt_id and  "
							+ " n.clie_id = c.clie_id and  "
							+ " nm.ngmv_cdmovimento = 1 and "
							+ " nmr.nmrg_cdexclusaotipo is null and "
							+ " nrt.nrtp_cdregistro = 'D' and "
							// +
							// " nmr.nmrg_id in(select nmrg_id from negativador_movimento_reg_item where cdst_id <> 1 ) and "
							+ " nm.negt_id in (select nc.negt_id from negativador_contrato nc where nc.ngcn_dtcontratoencerramento is null) ";

			Collection coll = (Collection) session.createSQLQuery(consulta).addScalar("idNegativador", Hibernate.INTEGER)
							.addScalar("idCliente", Hibernate.INTEGER).addScalar("nomeCliente", Hibernate.STRING).list();

			if(coll != null){

				Iterator it = coll.iterator();
				while(it.hasNext()){
					Object[] obj = (Object[]) it.next();

					Cliente c = new Cliente();
					c.setId((Integer) obj[1]);
					c.setNome((String) obj[2]);

					Negativador n = new Negativador();
					n.setId((Integer) obj[0]);
					n.setCliente(c);
					retorno.add(n);

				}
			}

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate getImovelCondicao");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * Método consuta os NegativadoresMovimentoReg que tenham movimento de Exclusão do spc ou serasa
	 * [UC0673] - Gerar Movimento da Exclusão de Negativação
	 * [SB0003] - Selecionar Negativadores
	 * 
	 * @param ids
	 *            -id do negativador
	 * @return Colecao de negativadorMovimentoReg
	 * @author Luciano Galvão
	 * @date 21/02/2015
	 */
	public Collection consultarNegativacoesParaExclusaoMovimento(Integer[] ids) throws ErroRepositorioException{

		List retorno = new ArrayList();
		Collection negativacoes = null;

			if(!Util.isVazioOrNulo(ids)){
			for(Integer negativadorId : ids){

				negativacoes = consultarNegativacoesParaExclusaoMovimentoPorNegativador(negativadorId);

				if(!Util.isVazioOrNulo(negativacoes)){
					retorno.addAll(negativacoes);
				}
			}
					}

		return retorno;
				}

	/**
	 * Método consuta os NegativadoresMovimentoReg que tenham movimento de Exclusão do spc ou serasa
	 * [UC0673] - Gerar Movimento da Exclusão de Negativação
	 * [SB0003] - Selecionar Negativadores
	 * 
	 * @param ids
	 *            -id do negativador
	 * @return Colecao de negativadorMovimentoReg
	 * @author Thiago Toscano
	 * @date 21/12/2007
	 */
	private Collection consultarNegativacoesParaExclusaoMovimentoPorNegativador(Integer negativadorId) throws ErroRepositorioException{

		List retorno = new ArrayList();
		Session session = HibernateUtil.getSession();
		try{
			StringBuffer consulta = new StringBuffer();

			consulta.append(" select nmrg.nmrg_id as codigo ");
			consulta.append(" from negativador_movimento_reg nmrg, ");
			consulta.append("      negativador_movimento ngmv, ");
			consulta.append("      negativador negt, ");
			consulta.append("      negativador_registro_tipo nrtp ");
			consulta.append(" where  negt.negt_id = " + negativadorId);
			consulta.append("   and negt.negt_id = ngmv.negt_id ");
			consulta.append("   and negt.negt_id = nrtp.negt_id ");
			consulta.append("   and nmrg.nrtp_id = nrtp.nrtp_id ");
			consulta.append("   and nmrg.ngmv_id = ngmv.ngmv_id ");
			consulta.append("   and ngmv.ngmv_cdmovimento = " + NegativadorMovimento.CODIGO_MOVIMENTO_INCLUSAO);
			consulta.append("   and nmrg.nmrg_cdexclusaotipo is null ");
			consulta.append("   and nmrg.nmrg_icaceito = " + NegativadorMovimentoReg.INDICADOR_ACEITO);
			consulta.append("   and nmrg.cdst_id is not null ");
			consulta.append("   and nmrg.cdst_id <> " + CobrancaDebitoSituacao.PENDENTE);
			consulta.append("   and nrtp.nrtp_cdregistro = 'D' ");
			consulta.append(" order by nmrg.nmrg_id, nmrg.nmrg_nnregistro ");

			// Ordena pelas posições 15 a 16 de NMRG_CNREGISTRO, caso o negativador seja o
			// SPC-BOA_VISTA
			if(Negativador.NEGATIVADOR_SPC_BOA_VISTA.equals(negativadorId)){
				consulta.append(", substring(nmrg_cnregistro, 15, 2)");
			}

			Collection coll = (Collection) session.createSQLQuery(consulta.toString()).addScalar("codigo", Hibernate.INTEGER).list();

			if(coll != null && !coll.isEmpty()){

				if(coll.size() <= 1000){
					StringBuffer restricao2 = new StringBuffer();
					if(coll.size() > 0){
						restricao2.append(" where m.id in (");
						Iterator it = coll.iterator();
						while(it.hasNext()){
							Integer codigo = (Integer) it.next();
							restricao2.append(codigo.toString());
							restricao2.append(",");
						}
						restricao2.delete(restricao2.length() - 1, restricao2.length());
						restricao2.append(")");
					}
					StringBuffer hql = new StringBuffer();
					hql.append("select m");
					hql.append(" from NegativadorMovimentoReg m");
					hql.append(" inner join fetch m.imovel i");
					hql.append(" left join fetch i.cobrancaSituacao cs");
					hql.append(" inner join fetch m.negativadorMovimento nm");
					// hql.append(" left join fetch nm.negativacaoComando nc");
					hql.append(" inner join fetch nm.negativador n");
					hql.append(" inner join fetch n.cliente c");
					hql.append(" left join fetch m.cobrancaDebitoSituacao cds");
					hql.append(" inner join fetch m.cliente cli");
					// hql.append(" where ");
					hql.append(restricao2.toString());

					Query query = session.createQuery(hql.toString());
					retorno.addAll((List) query.list());
				}else{

					Iterator iterator = coll.iterator();
					int qtdIteracoes = coll.size() / 1000;
					for(int i = 0; i <= qtdIteracoes; i++){
						Collection temp = new ArrayList();
						int count = 0;

						while(iterator.hasNext()){
							if(count < 1000){
								temp.add(iterator.next());
								count++;
							}else{
								break;
							}
						}

						StringBuffer restricao2 = new StringBuffer();
						if(coll.size() > 0){
							restricao2.append(" where m.id in (");
							Iterator it = temp.iterator();
							while(it.hasNext()){
								Integer codigo = (Integer) it.next();
								restricao2.append(codigo.toString());
								restricao2.append(",");
							}
							restricao2.delete(restricao2.length() - 1, restricao2.length());
							restricao2.append(")");
						}
						StringBuffer hql = new StringBuffer();
						hql.append("select m");
						hql.append(" from NegativadorMovimentoReg m");
						hql.append(" inner join fetch m.imovel i");
						hql.append(" left join fetch i.cobrancaSituacao cs");
						hql.append(" inner join fetch m.negativadorMovimento nm");
						// hql.append(" left join fetch nm.negativacaoComando nc");
						hql.append(" inner join fetch nm.negativador n");
						hql.append(" inner join fetch n.cliente c");
						hql.append(" inner join fetch m.cobrancaDebitoSituacao cds");
						hql.append(" inner join fetch m.cliente cli");
						// hql.append(" where ");
						hql.append(restricao2.toString());

						Query query = session.createQuery(hql.toString());
						retorno.addAll((List) query.list());
					}

				}
			}

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// session.flush();
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * Consulta todos os contratos em vigencia de um negativador
	 * 
	 * @author Thiago Toscano
	 * @date 26/12/2007
	 * @param negativador
	 * @return
	 * @throws ErroRepositorioException
	 */
	public NegativadorContrato consultarNegativadorContratoVigente(Integer negativador) throws ErroRepositorioException{

		NegativadorContrato retorno;
		Session session = HibernateUtil.getSession();
		String hql = null;
		try{

			String data1 = Util.recuperaDataInvertida(new Date());

			hql = " select nc " + " from gcom.cobranca.NegativadorContrato nc" + " inner join fetch nc.negativador negativador "
							+ " where " + " 	(nc.dataContratoEncerramento is null or " + "	nc.dataContratoFim >= '" + data1 + "') and "
							+ "	negativador.id =  " + negativador + "";

			List coll = session.createQuery(hql).setMaxResults(1).list();
			if(coll != null && !coll.isEmpty()){
				retorno = (NegativadorContrato) coll.iterator().next();
			}else{
				retorno = null;
			}
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate getImovelCindicao");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * Método que consulta os NegativadorMovimentoReg que representam o arquivo
	 * dos movimentos de exclusao de negativacao, para a geracao do arquvo txt
	 * [UC0673] - Gerar Movimento da Exclusão de Negativação
	 * [SB0009] - Gerar Arquivo TxT para Envio ao Negativador
	 * 
	 * @author Thiago Toscano
	 * @date 27/12/2007
	 * @param idMovimento
	 * @throws ErroRepositorioException
	 */
	public Collection consultarNegativadorMovimentoRegistroParaGerarArquivo(Integer codigoNegativadorMovimento, String tipoRegistro)
					throws ErroRepositorioException{

		Collection retorno = null;
		Session session = HibernateUtil.getSession();
		String hql = null;
		try{

			hql = " select nmr " + " from gcom.cobranca.NegativadorMovimentoReg nmr "
							+ " inner join fetch nmr.negativadorRegistroTipo nrt " + " inner join fetch nmr.negativadorMovimento nm "
							+ " inner join fetch nm.negativador n " + " where " + " nm.id = " + codigoNegativadorMovimento + " and "
							+ " nrt.codigoRegistro = '" + tipoRegistro + "'" + " order by nmr.id";

			retorno = session.createQuery(hql).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate getImovelCindicao");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * Método usado para pesquisa de Comando Negativação (Helper)
	 * [UC0655] Filtrar Comando Negativação
	 * 
	 * @author Thiago Vieira
	 * @date 02/01/2008
	 * @param comandoNegativacaoHelper
	 * @return Collection
	 * @throws ErroRepositorioException
	 */

	public Collection pesquisarComandoNegativacaoHelper(ComandoNegativacaoHelper comandoNegativacaoHelper) throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{

			consulta = "select " + " negatComando.id," + " negatCriterio.descricaoTitulo," + " negatComando.indicadorSimulacao,"
							+ " negatComando.dataHoraComando," + " negatComando.dataHoraRealizacao," + " negatComando.quantidadeInclusoes,"
							+ " usuario.nomeUsuario, " + " cliente.nome" + " from gcom.cobranca.NegativacaoCriterio negatCriterio "
							+ " inner join negatCriterio.negativacaoComando negatComando "
							+ " inner join negatComando.negativador negativador " + " inner join negatComando.usuario usuario "
							+ " inner join negativador.cliente cliente";

			String sql = " where negatComando.indicadorComandoCriterio = 1 and negatComando.dataHoraRealizacao is null and ";

			if(comandoNegativacaoHelper.getTituloComando() != null && !comandoNegativacaoHelper.getTituloComando().equals("")){
				if(comandoNegativacaoHelper.getTipoPesquisaTituloComando().equals(ConstantesSistema.TIPO_PESQUISA_INICIAL)){
					sql = sql + "upper(negatCriterio.descricaoTitulo) like '" + comandoNegativacaoHelper.getTituloComando().toUpperCase()
									+ "%' and ";
				}else{
					sql = sql + "upper(negatCriterio.descricaoTitulo) like '%" + comandoNegativacaoHelper.getTituloComando().toUpperCase()
									+ "%' and ";
				}
			}

			if(comandoNegativacaoHelper.getIndicadorComandoSimulado() != null
							&& !((Short.valueOf("3")).equals(comandoNegativacaoHelper.getIndicadorComandoSimulado()))){
				sql = sql + " negatComando.indicadorSimulacao = " + comandoNegativacaoHelper.getIndicadorComandoSimulado() + " and ";
			}
			if(comandoNegativacaoHelper.getGeracaoComandoInicio() != null && !comandoNegativacaoHelper.getGeracaoComandoInicio().equals("")
							&& comandoNegativacaoHelper.getGeracaoComandoFim() != null
							&& !comandoNegativacaoHelper.getGeracaoComandoFim().equals("")
							&& comandoNegativacaoHelper.getGeracaoComandoInicio().equals(comandoNegativacaoHelper.getGeracaoComandoFim())){

				String data1 = Util.recuperaDataInvertida(comandoNegativacaoHelper.getGeracaoComandoInicio());

				if(data1 != null && !data1.equals("") && data1.trim().length() == 8){
					data1 = data1.substring(0, 4) + "-" + data1.substring(4, 6) + "-" + data1.substring(6, 8);
				}
				sql = sql + " negatComando.dataHoraComando >= '" + data1 + "' and ";
				sql = sql + " negatComando.dataHoraComando < '" + data1 + "  24:00:00' and ";
			}else{
				if(comandoNegativacaoHelper.getGeracaoComandoInicio() != null
								&& !comandoNegativacaoHelper.getGeracaoComandoInicio().equals("")){
					String data1 = Util.recuperaDataInvertida(comandoNegativacaoHelper.getGeracaoComandoInicio());
					if(data1 != null && !data1.equals("") && data1.trim().length() == 8){
						data1 = data1.substring(0, 4) + "-" + data1.substring(4, 6) + "-" + data1.substring(6, 8);
					}
					sql = sql + " negatComando.dataHoraComando >= '" + data1 + "' and ";
				}
				if(comandoNegativacaoHelper.getGeracaoComandoFim() != null && !comandoNegativacaoHelper.getGeracaoComandoFim().equals("")){
					String data2 = Util.recuperaDataInvertida(comandoNegativacaoHelper.getGeracaoComandoFim());
					if(data2 != null && !data2.equals("") && data2.trim().length() == 8){
						data2 = data2.substring(0, 4) + "-" + data2.substring(4, 6) + "-" + data2.substring(6, 8);
					}
					sql = sql + " negatComando.dataHoraComando <= '" + data2 + " 24:00:00' and ";
				}
			}
			if(comandoNegativacaoHelper.getIdUsuarioResponsavel() != null){
				sql = sql + " usuario.id = " + comandoNegativacaoHelper.getIdUsuarioResponsavel() + " and ";
			}

			if(comandoNegativacaoHelper.getIdNegativador() != null && comandoNegativacaoHelper.getIdNegativador() != -1){
				sql = sql + " negativador.id = " + comandoNegativacaoHelper.getIdNegativador() + " and ";
			}

			if(!sql.equals(" where ")){
				consulta = consulta + sql;
			}

			if(consulta.substring(consulta.length() - 5, consulta.length()).equals(" and ")){
				consulta = Util.formatarHQL(consulta, 4);
			}

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
	 * [UC0651] Inserir Comando Negativação
	 * [SB0003] Determinar Data Prevista para Execução do Comando
	 * 
	 * @author Ana Maria
	 * @date 11/12/2007
	 * @param idNegativador
	 * @return Date
	 * @throws ErroRepositorioException
	 */
	public Date pesquisarUltimaDataRealizacaoComando(Integer idNegativador) throws ErroRepositorioException{

		Date retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "select max(ngcm.dataHoraRealizacao) " + "from NegativacaoComando ngcm "
							+ "where ngcm.dataHoraRealizacao is not null and ngcm.negativador.id = :idNegativador";

			retorno = (Date) session.createQuery(consulta).setInteger("idNegativador", idNegativador).uniqueResult();

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
	 * [UC0651] Inserir Comando Negativação
	 * [FS0015] Verificar existência de negativação para o imóvel no negativador
	 * 
	 * @author Ana Maria
	 * @date 04/12/2007
	 * @param idNegativador
	 * @param idImovel
	 * @return Boolean
	 * @throws ErroRepositorioException
	 */
	public Boolean verificarExistenciaNegativacaoImovel(Integer idNegativador, Integer idImovel) throws ErroRepositorioException{

		String pesquisar = null;
		Boolean retorno = false;
		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "select negt.cliente.nome " + "from NegativacaoImovei ngim " + "inner join ngim.imovel imov "
							+ "inner join ngim.negativacaoComando ngcm " + "inner join ngcm.negativador negt "
							+ "where ngim.indicadorExcluido = 2 and negt.id =:idNegativador " + "and imov.id = :idImovel";

			pesquisar = (String) session.createQuery(consulta).setInteger("idNegativador", idNegativador).setInteger("idImovel", idImovel)
							.uniqueResult();

			if(pesquisar != null && !pesquisar.equals("")){
				retorno = true;
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
	 * [UC0651] Inserir Comando Negativação
	 * //[FS0014]- Verificar existência de comando para os mesmos parâmetros
	 * 
	 * @author Ana Maria
	 * @date 13/12/2007
	 * @param InserirComandoNegativacaoPorCriterioHelper
	 * @return Integer
	 * @throws ErroRepositorioException
	 */
	public String verificarExistenciaComandoMesmoParametro(InserirComandoNegativacaoPorCriterioHelper helper)
					throws ErroRepositorioException{

		String retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = " select negt.cliente.nome" + " from NegativacaoComando ngcm" + " inner join ngcm.negativador negt"
							+ " where negt.id = :idNegativador" + " and ngcm.indicadorComandoCriterio = :icComandoCriterio"
							+ " and ngcm.indicadorSimulacao = :icSimulacao" + " and ngcm.dataPrevista = :dataPrevista"
							+ " and ngcm.dataHoraRealizacao is null";

			retorno = (String) session.createQuery(consulta)
							.setInteger("idNegativador", helper.getNegativacaoComando().getNegativador().getId())
							.setShort("icComandoCriterio", helper.getNegativacaoComando().getIndicadorComandoCriterio())
							.setShort("icSimulacao", helper.getNegativacaoComando().getIndicadorSimulacao())
							.setDate("dataPrevista", helper.getNegativacaoComando().getDataPrevista()).uniqueResult();

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
	 * Consulta os Negativadores para a geracao do resumo diario da negativacao
	 * [UC0688] Gerar Resumo Diário da Negativação
	 * Fluxo principal Item 1.0
	 * 
	 * @author Thiago Toscano
	 * @date 07/01/2008
	 */
	public List consultarNegativacaoParaGerarResumoDiarioNegativacao() throws ErroRepositorioException{

		List retorno = new ArrayList();
		Session session = HibernateUtil.getSession();

		try{
			String hql = " select nmr" + " from gcom.cobranca.NegativadorMovimentoReg nmr"
							+ " inner join fetch nmr.cobrancaDebitoSituacao cds " + " inner join fetch nmr.imovel i "
							+ " inner join fetch i.quadra qua " + " inner join fetch qua.rota rot "
							+ " inner join fetch rot.cobrancaGrupo cobGrup " + " inner join fetch nmr.localidade l "
							+ " inner join fetch l.gerenciaRegional gr " + " inner join fetch l.unidadeNegocio un "
							+ " inner join fetch l.localidade lelo " + " inner join fetch l.quadra q "
							+ " inner join fetch q.setorComercial sc " + " inner join fetch nmr.imovelPerfil ip "
							+ " inner join fetch nmr.categoria c " + " inner join fetch nmr.cliente clie "
							+ " inner join fetch clie.clienteTipo ct " + " inner join fetch clie.esferaPoder ep "
							+ " inner join fetch nmr.negativadorMovimento nm " + " inner join fetch nm.negativacaoComando nc "
							+ " inner join fetch nm.negativador n " + " where nmr.negativadorMovimento.codigoMovimento = 1"
							+ " and nmr.imovel is not null " + " ";

			retorno = (List) session.createQuery(hql).list();

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
	 * Apaga todos os ResumoNegativacao
	 * [UC0688] Gerar Resumo Diário da Negativação
	 * Fluxo principal Item 2.0
	 * 
	 * @author Thiago Toscano
	 * @date 07/01/2008
	 */
	public void apagarResumoNegativacao() throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();

		try{
			String hql = " delete " + " from gcom.cobranca.ResumoNegativacao" + " ";

			session.createQuery(hql).executeUpdate();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}
	}

	/**
	 * Consulta os itens do registro do NegativadorMovimentoReg passado
	 * [UC0688] Gerar Resumo Diário da Negativação
	 * [SB0001] Processar Itens da Negativação
	 * 
	 * @author Thiago Toscano
	 * @date 07/01/2008
	 */
	public List consultarNegativadorMovimentoRegItem(Integer codigoNegativadorMovimentoReg) throws ErroRepositorioException{

		List retorno = new ArrayList();
		Session session = HibernateUtil.getSession();

		try{
			String hql = " select nmri " + " from gcom.cobranca.NegativadorMovimentoRegItem nmri "
							+ " left join fetch nmri.negativadorMovimentoReg nmr " + " left join fetch nmri.cobrancaDebitoSituacao cds "
							+ " left join fetch nmri.contaGeral cg " + " left join fetch cg.conta c "
							+ " left join fetch c.debitoCreditoSituacaoAtual " + " left join fetch c.debitoCreditoSituacaoAnterior "
							+ " left join fetch cg.contaHistorico ch " + " left join fetch ch.debitoCreditoSituacaoAnterior "
							+ " left join fetch ch.debitoCreditoSituacaoAtual " + " left join fetch nmri.guiaPagamentoGeral cpg "
							+ " left join fetch cpg.guiaPagamento gp " + " left join fetch gp.debitoCreditoSituacaoAtual "
							// + " inner join fetch gp.debitoCreditoSituacaoAnterior "
							+ " left join fetch cpg.guiaPagamentoHistorico gph " + " left join fetch gph.debitoCreditoSituacaoAtual "
							// + " inner join fetch gph.debitoCreditoSituacaoByDcstIdanterior "

							+ " where nmri.negativadorMovimentoReg.id = " + codigoNegativadorMovimentoReg + " ";

			retorno = (List) session.createQuery(hql).list();

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
	 * @author Saulo Lima
	 * @date 30/09/2010
	 * @param idNegativadorMovimentoReg
	 * @throws ErroRepositorioException
	 */
	public void atualizarNegativadorMovimentoRegItem(Integer idNegativadorMovimentoReg) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();

		String atualizar;

		try{
			/*
			 * atualizar = "update gcom.cobranca.NegativadorMovimentoRegItem "
			 * +
			 * "set cdst_idaposexclusao = cdst_id, nmri_dtsitdebaposexclusao = nmri_dtsituacaodebito where nmrg_id = :id"
			 * ;
			 * session.createQuery(atualizar)
			 * .setInteger("id", idNegativadorMovimentoReg)
			 * .executeUpdate();
			 */

			Connection jdbcCon = session.connection();
			PreparedStatement st = null;

			atualizar = "update /*+ RULE*/ negativador_movimento_reg_item "
							+ "set cdst_idaposexclusao = cdst_id, nmri_dtsitdebaposexclusao = nmri_dtsituacaodebito where nmrg_id = ?";

			// abre a conexao
			st = jdbcCon.prepareStatement(atualizar);

			st.setInt(1, idNegativadorMovimentoReg.intValue());

			// executa o update
			st.executeUpdate();

		}catch(Exception e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}
	}

	/**
	 * Consulta a NegativacaoComando de um negativadormovimento
	 * [UC0688] Gerar Resumo Diário da Negativação
	 * [SB0005] Determinar Negativação do Imovel
	 * 
	 * @author Thiago Toscano
	 * @date 07/01/2008
	 */
	public NegativacaoImovei consultarNegativacaoImoveiDoNegativadorMovimento(Integer codigoNegativadorMovimento)
					throws ErroRepositorioException{

		NegativacaoImovei retorno = null;
		Session session = HibernateUtil.getSession();

		try{
			String hql = " select ni " + " from gcom.cobranca.NegativacaoImovei ni " + " where ni.negativacaoComando.id in "
							+ " 			( 	select nm.negativacaoComando.id " + " 				from gcom.cobranca.NegativadorMovimento nm "
							+ " 				where nm.id = " + codigoNegativadorMovimento + " )";

			List l = session.createQuery(hql).list();
			if(l != null && !l.isEmpty()){
				retorno = (NegativacaoImovei) l.iterator().next();
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

	// /**
	// * Consultar uma contahistorico a partir de uma contageral
	// *
	// * [UC0688] Gerar Resumo Diário da Negativação
	// * [SB0002] Determinar Situação do Débito do Item da Negativação
	// * Item 1.1.2.1
	// *
	// * @author Thiago Toscano
	// * @date 09/01/2008
	// *
	// * @param codigoConta
	// * @return
	// */
	// public Conta consultaConta(Integer codigoConta) throws ErroRepositorioException {
	//
	// Conta retorno = null;
	// Session session = HibernateUtil.getSession();
	//
	// try {
	// String hql =
	//
	// " select ch "
	// + " from gcom.faturamento.conta.Conta ch "
	// + " inner join fetch ch.debitoCreditoSituacaoAtual dcsa "
	// + " where ch.id = " + codigoConta
	// + " ";
	//
	// List l = session.createQuery(hql).list();
	// if (l != null && !l.isEmpty()) {
	// retorno = (Conta)l.iterator().next();
	// }
	// } catch (HibernateException e) {
	// // levanta a exceção para a próxima camada
	// throw new ErroRepositorioException(e, "Erro no Hibernate");
	// } finally {
	// // fecha a sessão
	// HibernateUtil.closeSession(session);
	// }
	//
	// return retorno;
	// }

	// /**
	// * Consultar uma contahistorico a partir de uma contageral
	// *
	// * [UC0688] Gerar Resumo Diário da Negativação
	// * [SB0002] Determinar Situação do Débito do Item da Negativação
	// * Item 1.1.2.1
	// *
	// * @author Thiago Toscano
	// * @date 09/01/2008
	// *
	// * @param codigoConta
	// * @return
	// */
	// public ContaHistorico consultaContaHistorico(Integer codigoConta) throws
	// ErroRepositorioException {
	//
	// ContaHistorico retorno = null;
	// Session session = HibernateUtil.getSession();
	//
	// try {
	// String hql =
	//
	// " select ch "
	// + " from gcom.faturamento.conta.ContaHistorico ch "
	// + " inner join fetch ch.debitoCreditoSituacaoAtual dcsa "
	// + " where ch.id = " + codigoConta
	// + " ";
	//
	// List l = session.createQuery(hql).list();
	// if (l != null && !l.isEmpty()) {
	// retorno = (ContaHistorico)l.iterator().next();
	// }
	// } catch (HibernateException e) {
	// // levanta a exceção para a próxima camada
	// throw new ErroRepositorioException(e, "Erro no Hibernate");
	// } finally {
	// // fecha a sessão
	// HibernateUtil.closeSession(session);
	// }
	//
	// return retorno;
	// }

	/**
	 * Consulta uma conta historico caso a conta mais atual esteja no historico
	 * [UC0688] Gerar Resumo Diário da Negativação
	 * [SB0002] Determinar Situação do Débito do Item da Negativação
	 * Item 1.1.2.2.3
	 * 
	 * @author Thiago Toscano
	 * @date 09/01/2008
	 * @param codigoConta
	 * @return
	 */
	public ContaHistorico consultaContaHistoricoMaisAtual(Integer codigoImovel, int anoMesReferenciaConta) throws ErroRepositorioException{

		ContaHistorico retorno = null;
		Session session = HibernateUtil.getSession();

		try{
			String hql =

			" select ch " + " from gcom.faturamento.conta.ContaHistorico ch " + " where ch.anoMesReferenciaConta = "
							+ anoMesReferenciaConta + " and ch.debitoCreditoSituacaoAtual.id <> 4 " + " and c.imovel.id = " + codigoImovel
							+ " ";

			List l = session.createQuery(hql).list();
			if(l != null && !l.isEmpty()){
				retorno = (ContaHistorico) l.iterator().next();
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
	 * Consulta uma conta caso a conta mais atual ainda não esteja no historico
	 * [UC0688] Gerar Resumo Diário da Negativação
	 * [SB0002] Determinar Situação do Débito do Item da Negativação
	 * Item 1.1.2.2.3
	 * 
	 * @author Thiago Toscano
	 * @date 09/01/2008
	 * @param codigoConta
	 * @return
	 */
	public Conta consultaContaMaisAtual(Integer codigoImovel, int anoMesReferenciaConta) throws ErroRepositorioException{

		Conta retorno = null;
		Session session = HibernateUtil.getSession();

		try{
			String hql = " select ch " + " from gcom.faturamento.conta.Conta c " + " where c.referencia = " + anoMesReferenciaConta
							+ " and c.debitoCreditoSituacaoAtual.id <> 4 " + " and c.imovel.id = " + codigoImovel + " ";

			List l = session.createQuery(hql).list();
			if(l != null && !l.isEmpty()){
				retorno = (Conta) l.iterator().next();
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
	 * Método que atualiza o imovel cobranca situacao tipo
	 * [UC0688] Gerar Resumo Diario da Negativacao
	 * [SB0005] Determinar Negativação do Imovle
	 * Item 2.1.4
	 * 
	 * @author Thiago Toscano
	 * @date 08/01/2008
	 * @param codigoImovel
	 * @param codigoCobrancaSituacao
	 */
	public List consultarImovelCobrancaSituacao(Integer codigoImovel, Integer codigoCobrancaSituacao) throws ErroRepositorioException{

		List retorno = new ArrayList();
		Session session = HibernateUtil.getSession();

		try{
			String hql = " select ics" + " from gcom.cadastro.imovel.ImovelCobrancaSituacao ics" + " where ics.imovel.id = " + codigoImovel
							+ " and ics.dataRetiradaCobranca is null " + " and ics.cobrancaSituacao.id = " + codigoCobrancaSituacao + " ";

			retorno = (List) session.createQuery(hql).list();

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
	 * Método que consulta os pagamentos passando o codigo da conta ou do guia de pagamento
	 * [UC0688] Gerar Resumo Diário da Negativação
	 * [SB0002] Determinar Situação do Débito do Item da Negativação
	 * Item 4.1.1 ou Item 4.2.1
	 * 
	 * @author Thiago Toscano
	 * @date 10/01/2008
	 * @param codigoConta
	 * @param codigoGuiaPagamento
	 * @return
	 */
	public Collection consultarPagamentoDoItem(Integer codigoConta, Integer codigoGuiaPagamento) throws ErroRepositorioException{

		List retorno = new ArrayList();
		Session session = HibernateUtil.getSession();

		try{
			String hql = " select p" + " from gcom.arrecadacao.pagamento.Pagamento p " + " where " + " ";

			if(codigoConta != null){
				hql = hql + " p.conta.id = " + codigoConta;
			}else if(codigoGuiaPagamento != null){
				hql = hql + " p.guiaPagamento.id = " + codigoGuiaPagamento;
			}

			retorno = (List) session.createQuery(hql).list();

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
	 * Método que consulta os pagamentos historcio passando o codigo da conta historico ou do guia
	 * de pagamento
	 * [UC0688] Gerar Resumo Diário da Negativação
	 * [SB0002] Determinar Situação do Débito do Item da Negativação
	 * Item 4.1.2 ou Item 4.2.2
	 * 
	 * @author Thiago Toscano
	 * @date 10/01/2008
	 * @param codigoContaHistorico
	 * @param codigoGuiaPagamento
	 * @return
	 */
	public Collection consultarPagamentoHistorcioDoItem(Integer codigoContaHistorico, Integer codigoGuiaPagamento)
					throws ErroRepositorioException{

		List retorno = new ArrayList();
		Session session = HibernateUtil.getSession();

		try{
			String hql = " select p" + " from gcom.arrecadacao.pagamento.PagamentoHistorico p " + " where " + " ";

			if(codigoContaHistorico != null){
				hql = hql + " p.conta.id = " + codigoContaHistorico;
			}else if(codigoGuiaPagamento != null){
				hql = hql + " p.guiaPagamento.id = " + codigoGuiaPagamento;
			}

			retorno = (List) session.createQuery(hql).list();

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
	 * [UC0688] Gerar Resumo Diário da Negativação
	 * Fluxo principal Item 2.0
	 * 
	 * @author Vivianne Sousa
	 * @date 30/09/2009
	 */
	public void apagarResumoNegativacao(Integer numeroExecucaoResumoNegativacao, Integer idRota) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();

		try{
			String hql = " delete " + " from gcom.cobranca.ResumoNegativacao "
							+ " where numeroExecucaoResumoNegativacao = :numeroExecucaoResumoNegativacao " + " and quadra.id in  "
							+ "(select q.id from Quadra q where q.rota.id = :idRota)";

			long t1 = System.currentTimeMillis();
			session.createQuery(hql).setInteger("numeroExecucaoResumoNegativacao", numeroExecucaoResumoNegativacao)
							.setInteger("idRota", idRota).executeUpdate();
			long t2 = System.currentTimeMillis();
			System.out.println("[UC0688]apagarResumoNegativacao " + (t2 - t1));
		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}
	}

	/**
	 * Consulta os Negativadores para a geracao do resumo diario da negativacao
	 * [UC0688] Gerar Resumo Diário da Negativação
	 * Fluxo principal Item 1.0
	 * 
	 * @author Thiago Toscano,Vivianne Sousa
	 * @date 07/01/2008,30/10/2009
	 */
	public List consultarNegativacaoParaGerarResumoDiarioNegativacao(Integer idRota) throws ErroRepositorioException{

		List retorno = new ArrayList();
		Session session = HibernateUtil.getSession();

		try{

			String sql = "select "
							+ "       ngmv.negt_id as idNegativador,"
							+ "       ngmv.ngcm_id as idNegativadorComando,"
							+ "       ngmv.ngmv_dtprocessamentoenvio as dataProcessamento,"
							+ "       case when nmrg.nmrg_cdexclusaotipo is not null then"
							+ "         case when (ngim_dtexclusao - ngmv_dtprocessamentoenvio) > 30 then 1"
							+ "         else 2 end "
							+ "       else "
							+ "         case when (current_date - ngmv_dtprocessamentoenvio) > 30 then 1"
							+ "         else 2 end "
							+ "       end as confirmada,"
							+ "       nmrg.cdst_id as idCobrancaDebitoSituacao, "
							+ "       rota.cbgr_id as idCobrancaGrupo, "
							+ "       loca.greg_id as idGerenciaRegional, "
							+ "       loca.uneg_id as idUnidadeNegocio, "
							+ "       loca.loca_cdelo as codigoElo, "
							+ "       nmrg.loca_id as idLocalidade, "
							+ "       qdra.stcm_id as idSetorComercil, "
							+ "       nmrg.qdra_id as idQuadra, "
							+ "       nmrg.nmrg_cdsetorcomercial as codigoSetorComercial, "
							+ "       nmrg.nmrg_nnquadra as numeroQuadra,"
							+ "       nmrg.iper_id as idImovelPerfil, "
							+ "       nmrg.catg_id as idCategoria, "
							+ "       clie.cltp_id as idClienteTipo, "
							+ "       cltp.epod_id as idEsferaPoder,"
							+ "       count(distinct(nmrg.nmrg_id)) as qtdeNegativadorMovimentoReg, "
							+ "       sum(nmri_vldebito) as valorDebito,"
							+ "       sum(case when nmri.cdst_id=1 then nmri_vldebito else 0 end) as valorPendente,"
							+ "       sum(case when nmri.cdst_id=2 then nmri_vldebito else 0 end) as valorPago,"
							+ "       sum(case when nmri.cdst_id=3 then nmri_vldebito else 0 end) as valorParcelado,"
							+ "       sum(case when nmri.cdst_id=4 then nmri_vldebito else 0 end) as valorCancelado"
							+ " from"
							+ "             cobranca.negativador_movimento_reg      nmrg"
							+ "  inner join cobranca.negativador_movimento          ngmv on ngmv.ngmv_id=nmrg.ngmv_id"
							+ "  inner join cobranca.negativacao_imoveis            ngim on ngim.ngcm_id=ngmv.ngcm_id and ngim.imov_id=nmrg.imov_id"
							+ "  inner join cadastro.quadra                         qdra on qdra.qdra_id=nmrg.qdra_id"
							+ "  inner join micromedicao.rota                       rota on rota.rota_id=qdra.rota_id"
							+ "  inner join cadastro.localidade                     loca on loca.loca_id=nmrg.loca_id"
							+ "  inner join cadastro.cliente                        clie on clie.clie_id=nmrg.clie_id"
							+ "  inner join cadastro.cliente_tipo                   cltp on cltp.cltp_id=clie.cltp_id"
							+ "  inner join cobranca.negativador_movimento_reg_item nmri on nmri.nmrg_id=nmrg.nmrg_id" + " where"
							+ "     nmrg.nmrg_icaceito=1" + " and ngmv.ngmv_cdmovimento=1 " + " and nmrg.imov_id is not null"
							+ " and rota.rota_id = :idRota" + " group by" + " 1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18";

			retorno = (List) session.createSQLQuery(sql).addScalar("idNegativador", Hibernate.INTEGER)
							.addScalar("idNegativadorComando", Hibernate.INTEGER).addScalar("dataProcessamento", Hibernate.DATE)
							.addScalar("confirmada", Hibernate.INTEGER).addScalar("idCobrancaDebitoSituacao", Hibernate.INTEGER)
							.addScalar("idCobrancaGrupo", Hibernate.INTEGER).addScalar("idGerenciaRegional", Hibernate.INTEGER)
							.addScalar("idUnidadeNegocio", Hibernate.INTEGER).addScalar("codigoElo", Hibernate.INTEGER)
							.addScalar("idLocalidade", Hibernate.INTEGER).addScalar("idSetorComercil", Hibernate.INTEGER)
							.addScalar("idQuadra", Hibernate.INTEGER).addScalar("codigoSetorComercial", Hibernate.INTEGER)
							.addScalar("numeroQuadra", Hibernate.INTEGER).addScalar("idImovelPerfil", Hibernate.INTEGER)
							.addScalar("idCategoria", Hibernate.INTEGER).addScalar("idClienteTipo", Hibernate.INTEGER)
							.addScalar("idEsferaPoder", Hibernate.INTEGER).addScalar("qtdeNegativadorMovimentoReg", Hibernate.INTEGER)
							.addScalar("valorDebito", Hibernate.BIG_DECIMAL).addScalar("valorPendente", Hibernate.BIG_DECIMAL)
							.addScalar("valorPago", Hibernate.BIG_DECIMAL).addScalar("valorParcelado", Hibernate.BIG_DECIMAL)
							.addScalar("valorCancelado", Hibernate.BIG_DECIMAL).setInteger("idRota", idRota).list();
			// alterada por Vivianne Sousa,30/10/2009
			// query feita por Fatiam Sampaio e Francisco
			// String hql = " select nmr"
			// + " from gcom.cobranca.NegativadorMovimentoReg nmr"
			// + " left join fetch nmr.cobrancaDebitoSituacao as cds "
			// + " left join fetch nmr.quadra as quad "
			// + " left join fetch quad.rota as rot "
			// + " left join fetch rot.cobrancaGrupo as cobGrup "
			// + " left join fetch nmr.localidade as l "
			// + " left join fetch l.gerenciaRegional as gr "
			// + " left join fetch l.unidadeNegocio as un "
			// + " left join fetch l.localidade as lelo "
			// + " left join fetch quad.setorComercial as sc "
			// + " left join fetch nmr.imovelPerfil as ip "
			// + " left join fetch nmr.categoria as c "
			// + " left join fetch nmr.cliente as clie "
			// + " left join fetch clie.clienteTipo as ct "
			// + " left join fetch ct.esferaPoder as ep "
			// + " left join fetch nmr.negativadorMovimento as nm "
			// + " left join fetch nm.negativacaoComando as nc "
			// + " left join fetch nm.negativador as n "
			// // + " left join fetch nmr.parcelamento as parc "
			// + " where "
			// + " nm.codigoMovimento = 1 "
			// + " and nmr.imovel is not null  "
			// + " and nmr.indicadorAceito = 1 "
			// + " and rot.id = " + idRota
			// + " order by nm.id,nmr.id ";
			//
			// retorno = (List) session.createQuery(hql).list();

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
	 * [UC0651] Manter Comando de Nagativação Critério
	 * Remove Titularidades do CPF/CNPJ da Negativação, Subcategorias, Perfis de imóvel,
	 * Tipos de cliente, Grupos de Cobrança, Gerências Regionais, Unidades Negócio,
	 * Elos Pólo do critério
	 * 
	 * @author Ana Maria
	 * @date 21/01/2008
	 * @param idNegativacaoCriterio
	 * @return Integer
	 * @throws ErroRepositorioException
	 */
	public void removerParametrosCriterio(Integer idNegativacaoCriterio) throws ErroRepositorioException{

		String remocao = null;

		Session session = HibernateUtil.getSession();

		try{

			// Remove Titularidades do CPF/CNPJ da Negativação
			remocao = "delete NegativacaoCriterioCpfTipo " + "where ngct_id =:idNegativacaoCriterio";

			session.createQuery(remocao).setInteger("idNegativacaoCriterio", idNegativacaoCriterio).executeUpdate();

			// Remove situação da ligação de água
			remocao = "delete NegativacaoCriterioLigacaoAgua " + "where ngct_id =:idNegativacaoCriterio";

			session.createQuery(remocao).setInteger("idNegativacaoCriterio", idNegativacaoCriterio).executeUpdate();

			// Remove situação da ligação de esgoto
			remocao = "delete NegativacaoCriterioLigacaoEsgoto " + "where ngct_id =:idNegativacaoCriterio";

			session.createQuery(remocao).setInteger("idNegativacaoCriterio", idNegativacaoCriterio).executeUpdate();

			// Remove Subcategorias
			remocao = "delete NegativacaoCriterioSubcategoria " + "where ngct_id =:idNegativacaoCriterio";

			session.createQuery(remocao).setInteger("idNegativacaoCriterio", idNegativacaoCriterio).executeUpdate();

			// Remove Perfis de imóvel
			remocao = "delete NegativacaoCriterioImovelPerfil " + "where ngct_id =:idNegativacaoCriterio";

			session.createQuery(remocao).setInteger("idNegativacaoCriterio", idNegativacaoCriterio).executeUpdate();

			// Remove Tipos de cliente
			remocao = "delete NegativacaoCriterioClienteTipo " + "where ngct_id =:idNegativacaoCriterio";

			session.createQuery(remocao).setInteger("idNegativacaoCriterio", idNegativacaoCriterio).executeUpdate();

			// Remove Grupos de Cobrança
			remocao = "delete NegativCritCobrGrupo " + "where ngct_id =:idNegativacaoCriterio";

			session.createQuery(remocao).setInteger("idNegativacaoCriterio", idNegativacaoCriterio).executeUpdate();

			// Remove Gerências Regionais
			remocao = "delete NegativCritGerReg " + "where ngct_id =:idNegativacaoCriterio";

			session.createQuery(remocao).setInteger("idNegativacaoCriterio", idNegativacaoCriterio).executeUpdate();

			// Remove Unidades Negócio
			remocao = "delete NegativCritUndNeg " + "where ngct_id =:idNegativacaoCriterio";

			session.createQuery(remocao).setInteger("idNegativacaoCriterio", idNegativacaoCriterio).executeUpdate();

			// Remove Elos Pólo
			remocao = "delete NegativCritElo " + "where ngct_id =:idNegativacaoCriterio";

			session.createQuery(remocao).setInteger("idNegativacaoCriterio", idNegativacaoCriterio).executeUpdate();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}
	}

	/**
	 * [UC0652] Atualizar Comando Negativação
	 * //[FS0012]- Verificar existência de comando para os mesmos parâmetros
	 * 
	 * @author Ana Maria
	 * @date 24/01/2008
	 * @param InserirComandoNegativacaoPorCriterioHelper
	 * @return Integer
	 * @throws ErroRepositorioException
	 */
	public String verificarExistenciaComandoMesmoParametroAtualizacao(InserirComandoNegativacaoPorCriterioHelper helper)
					throws ErroRepositorioException{

		String retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = " select negt.cliente.nome" + " from NegativacaoComando ngcm" + " inner join ngcm.negativador negt"
							+ " where ngcm.id <> :idNegativadoComando" + " and negt.id = :idNegativador"
							+ " and ngcm.indicadorComandoCriterio = :icComandoCriterio" + " and ngcm.indicadorSimulacao = :icSimulacao"
							+ " and ngcm.dataPrevista = :dataPrevista" + " and ngcm.dataHoraRealizacao is null";

			retorno = (String) session.createQuery(consulta).setInteger("idNegativadoComando", helper.getNegativacaoComando().getId())
							.setInteger("idNegativador", helper.getNegativacaoComando().getNegativador().getId())
							.setShort("icComandoCriterio", helper.getNegativacaoComando().getIndicadorComandoCriterio())
							.setShort("icSimulacao", helper.getNegativacaoComando().getIndicadorSimulacao())
							.setDate("dataPrevista", helper.getNegativacaoComando().getDataPrevista()).uniqueResult();

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
	 * Retorna o ImovelCobrancaSituacao pelo imovel do NegativadorMovimentoReg
	 * [UC0693] Gerar Relatório Acompanhamaneto de Clientes Negativados
	 * 
	 * @author Yara Taciane
	 * @date 17/03/2008
	 */
	public Collection consultarImovelCobrancaSituacaoPorNegativador(Imovel imovel, Integer codigoNegativador)
					throws ErroRepositorioException{

		Collection retorno = new ArrayList();
		Session session = HibernateUtil.getSession();

		try{
			String restricao = "";
			String hql = " select ics" + " from gcom.cadastro.imovel.ImovelCobrancaSituacao as ics"
							+ " inner join fetch ics.imovel as imov " + " inner join fetch ics.cobrancaSituacao as cbst "
							+ " where ics.imovel.id = " + imovel.getId() + " and ics.dataRetiradaCobranca is null ";

			if(codigoNegativador.equals(Negativador.NEGATIVADOR_SPC_SAO_PAULO)){
				restricao = restricao + " and ics.cobrancaSituacao.id in (11,13,15)" + " ";
			}else if(codigoNegativador.equals(Negativador.NEGATIVADOR_SPC_BRASIL)){
				restricao = restricao + " and ics.cobrancaSituacao.id in (11,13,15)" + " ";
			}else if(codigoNegativador.equals(Negativador.NEGATIVADOR_SERASA)){
				restricao = restricao + " and ics.cobrancaSituacao.id in (12,14,15)" + " ";
			}

			hql = hql + restricao;

			retorno = (List) session.createQuery(hql).list();

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
	 * UC0671 - Gerar movimento de inclusão da negativação
	 * Pesquisar imoveis do comando de simulacao por rota
	 * 
	 * @author Unknown, Francisco do Nascimento
	 * @date Unknown, 23/01/2009
	 * @param nComando
	 *            Comando de negativacao
	 * @param idRota
	 *            Identificador da rota
	 * @return Colecao de matriculas de imoveis
	 * @throws ErroRepositorioException
	 */
	public List consultarImoveisNegativacaoSimulada(NegativacaoComando nComando, Integer idRota)

	throws ErroRepositorioException{

		List resposta = new ArrayList();
		Session session = HibernateUtil.getSession();
		String sql = null;

		try{
			Rota rota = new Rota();
			rota.setId(idRota);
			Criteria criteria = session.createCriteria(NegativadorResultadoSimulacao.class);
			criteria.createCriteria("imovel", "imovel"); // Join com imovel
			criteria.createCriteria("imovel.quadra", "quadra"); // Join de imovel com quadra
			criteria.add(Restrictions.eq("negativacaoComando", nComando));
			criteria.add(Restrictions.eq("imovel.indicadorExclusao", ConstantesSistema.NAO));
			criteria.add(Restrictions.eq("imovel.rota", rota));
			criteria.addOrder(Order.asc("imovel"));
			resposta = criteria.list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate consultarImoveisCliente");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return resposta;
	}

	/**
	 * Método consutla um negativacaoComando
	 * [UC0671] Gerar Movimento de Inclusao de Negativacao
	 * [SB003] Gear moviemnto de inclusao de negativacao para os imoveis do clietne
	 * item 1.0
	 * 
	 * @author Thiago Toscano
	 * @date 21/02/2008
	 * @throws ErroRepositorioException
	 */
	public List consultarImoveisCliente(NegativacaoCriterio nCriterio, Integer idRota) throws ErroRepositorioException{

		List resposta = new ArrayList();
		Session session = HibernateUtil.getSession();
		try{
			String hql = " select i.id " + " from gcom.cadastro.imovel.Imovel as i " + " inner join i.clienteImoveis as ci "
							+ " inner join ci.cliente as cliente " + " inner join i.quadra q " + " inner join q.rota r "
							+ " where cliente.id = :idCliente " + " and i.indicadorExclusao = " + ConstantesSistema.NAO;
			if(nCriterio.getQuantidadeMaximaInclusoes() == null || nCriterio.getQuantidadeMaximaInclusoes().equals("")){
				hql = hql + " and r.id = " + idRota;
			}
			if(nCriterio.getClienteRelacaoTipo() != null && nCriterio.getClienteRelacaoTipo().getId() != null){
				hql = hql + " and ci.clienteRelacaoTipo.id = " + nCriterio.getClienteRelacaoTipo().getId();
			}

			resposta = session.createQuery(hql).setInteger("idCliente", nCriterio.getCliente().getId()).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate consultarImoveisCliente");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return resposta;
	}

	public List pesquisarImoveisParaNegativacao(Integer idRota, Integer idComando) throws ErroRepositorioException{

		List retorno = new ArrayList();
		Session session = HibernateUtil.getSession();
		String sql = null;
		try{
			sql = " select imov.imov_id as idImovel " + " from imovel as imov " + " inner join quadra qdra on(imov.qdra_id = qdra.qdra_id)"
							+ " where imov.iper_id <> 4 and qdra.qdpf_id <> 2 and imov.imov_icexclusao = "
							+ ConstantesSistema.INDICADOR_IMOVEL_ATIVO + " and qdra.rota_id = " + idRota + " and not exists ( "
							+ " SELECT ngsm.imov_id FROM negativador_resultado_simulacao ngsm " + " WHERE ngcm_id = " + idComando
							+ " AND ngsm.imov_id = imov.imov_id LIMIT 1 " + " ) ";

			// switch (tipoCondicao) {
			// case 1:
			//
			// sql = " select imov.imov_id as idImovel "
			// + " from cadastro.imovel as imov "
			// + " inner join cadastro.quadra qdra on(imov.qdra_id = qdra.qdra_id)"
			// + " inner join micromedicao.rota rota on(qdra.rota_id = rota.rota_id)"
			// + " where imov.iper_id <> 4 and qdra.qdpf_id <> 2 and "
			// + " rota.cbgr_id in (select nccg.cbgr_id "
			// + "                  from cobranca.negativacao_criterio as ngct "
			// +
			// " 			     inner join cobranca.negativ_crit_cobr_grupo as nccg on(ngct.ngct_id = nccg.ngct_id) "
			// + " 			     where ngct.ngct_id  = " + nCriterio.getId() +" ) ";
			// break;
			// case 2:
			// sql = " select imov.imov_id as idImovel "
			// + " from cadastro.imovel as imov "
			// + " inner join cadastro.quadra qdra on(imov.qdra_id = qdra.qdra_id)"
			// + " inner join cadastro.localidade loca on(imov.loca_id = loca.loca_id)"
			// + " where imov.iper_id <> 4 and qdra.qdpf_id <> 2 and "
			// + " loca.greg_id in (select  ncgr.greg_id"
			// + "             	 from cobranca.negativacao_criterio as ngct "
			// +
			// "             	 inner join cobranca.negativ_crit_ger_reg as ncgr on(ngct.ngct_id = ncgr.ngct_id)"
			// + "					 where ngct.ngct_id  = " + nCriterio.getId() +" ) ";
			// break;
			// case 3:
			// sql = " select imov.imov_id as idImovel "
			// + " from cadastro.imovel as imov "
			// + " inner join cadastro.quadra qdra on(imov.qdra_id = qdra.qdra_id)"
			// + " inner join cadastro.localidade loca on(imov.loca_id = loca.loca_id)"
			// + " where imov.iper_id <> 4 and qdra.qdpf_id <> 2 and "
			// + " loca.uneg_id  in (select ncun.uneg_id "
			// + " 	              from cobranca.negativacao_criterio as ngct "
			// +
			// " 	              inner join cobranca.negativ_crit_und_neg as ncun on(ngct.ngct_id = ncun.ngct_id) "
			// + " 	              where ngct.ngct_id  =" + nCriterio.getId() +" ) ";
			// break;
			// case 4:
			// sql = " select imov.imov_id as idImovel "
			// + " from cadastro.imovel as imov "
			// + " inner join cadastro.quadra qdra on(imov.qdra_id = qdra.qdra_id)"
			// + " where imov.iper_id <> 4 and qdra.qdpf_id <> 2 and "
			// + " imov.loca_id in (select ncep.loca_id"
			// + "                  from cobranca.negativacao_criterio as ngct "
			// +
			// "                  inner join cobranca.negativ_crit_elo as ncep on(ngct.ngct_id = ncep.ngct_id) "
			// + "                  where ngct.ngct_id  =" + nCriterio.getId() +" ) ";
			// break;
			// case 5:
			// sql = " select imov.imov_id as idImovel "
			// + " from cadastro.imovel as imov "
			// + " inner join cadastro.quadra qdra on(imov.qdra_id = qdra.qdra_id)"
			// + " inner join cadastro.setor_comercial stcm on(imov.stcm_id = stcm.stcm_id)"
			// + " where imov.iper_id <> 4 and qdra.qdpf_id <> 2 and "
			// + " imov.loca_id  between "+nCriterio.getLocalidadeInicial().getId()+" and "
			// +nCriterio.getLocalidadeFinal().getId();
			// if(nCriterio.getCodigoSetorComercialInicial() != null &&
			// nCriterio.getCodigoSetorComercialFinal() != null){
			// sql = sql +
			// " and stcm.stcm_cdsetorcomercial  between "+nCriterio.getCodigoSetorComercialInicial()+""
			// + " and "+nCriterio.getCodigoSetorComercialFinal();
			// }
			// break;
			// default:
			// sql = " select i.imov_id as idImovel "
			// + " from cadastro.imovel as i "
			// + " inner join cadastro.localidade loca on(i.loca_id = loca.loca_id)"
			// + " inner join cadastro.quadra q on(i.qdra_id = q.qdra_id)"
			// + " where i.iper_id <> 4 and q.qdpf_id <> 2"; //i.last_id = 4 and
			// if(nCriterio.getQuantidadeMaximaInclusoes() == null ||
			// nCriterio.getQuantidadeMaximaInclusoes().equals("")){
			// sql = sql + " and i.loca_id ="+idLocalidade;
			// }
			//
			// }

			retorno = (List) session.createSQLQuery(sql).addScalar("idImovel", Hibernate.INTEGER).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate getImovelCindicao");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * [UC0651] Inserir Comando Negativação
	 * [FS0015] Verificar existência de negativação para o imóvel no negativador
	 * 
	 * @author Ana Maria
	 * @date 04/12/2007
	 * @param idImovel
	 * @return Boolean
	 * @throws ErroRepositorioException
	 */
	public Boolean verificarExistenciaNegativacaoImovel(Integer idImovel) throws ErroRepositorioException{

		Integer pesquisar = null;
		Boolean retorno = false;
		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "select imov.id " + "from NegativacaoImovei ngim " + "inner join ngim.imovel imov "
							+ "where ngim.indicadorExcluido = 2 " + "and imov.id = :idImovel";

			pesquisar = (Integer) session.createQuery(consulta).setInteger("idImovel", idImovel).setMaxResults(1).uniqueResult();

			if(pesquisar != null && !pesquisar.equals("")){
				retorno = true;
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
	 * Insere geraRegistroNegativacaoReg
	 * 
	 * @author Marcio Roberto
	 * @date 30/10/2007
	 * @param
	 * @return
	 * @throws ErroRepositorioException
	 */
	@Deprecated
	public Integer geraRegistroNegativacaoReg(int idNegativador, int saenvio,
	// int idNegativadorComando,
					int idNegativacaoMovimento, StringBuilder registroHeader, int quantidadeRegistros, Integer idNegCriterio)
					throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		String insert;
		Connection con = null;
		Statement stmt = null;
		int nextValId = 0;
		try{
			con = session.connection();
			stmt = con.createStatement();

			Integer idNrTipo = NegativadorRegistroTipo.ID_SERASA_HEADER;
			if(new Integer(idNegativador).equals(Negativador.NEGATIVADOR_SPC_SAO_PAULO)){
				idNrTipo = NegativadorRegistroTipo.ID_SPC_HEADER_SP;
			}else if(new Integer(idNegativador).equals(Negativador.NEGATIVADOR_SPC_BRASIL)){
				idNrTipo = NegativadorRegistroTipo.ID_SPC_HEADER_BR;
			}
			insert = "insert into negativador_movimento_reg (" + " nmrg_id, " // 01
							+ " ngmv_id, " // 02
							+ " nmrg_idreginclusao, " // 03
							+ " nrtp_id, " // 04
							+ " nmrg_cnregistro, " // 05
							+ " nmrg_tmultimaalteracao, " // 06
							+ " usur_id, " // 07
							+ " nmrg_cdexclusaotipo, " // 08
							+ " nmrg_icaceito, " // 09
							+ " nmrg_iccorrecao, " // 10
							+ " nmrg_vldebito, " // 11
							+ " cdst_id, " // 12
							+ " nmrg_dtsituacaodebito, " // 13
							+ " imov_id, " // 14
							+ " loca_id, " // 15
							+ " qdra_id, " // 16
							+ " nmrg_cdsetorcomercial, " // 17
							+ " nmrg_nnquadra, " // 18
							+ " iper_id, " // 19
							+ " ngct_id, " // 20
							+ " clie_id, " // 21
							+ " catg_id, " // 22
							+ " cpft_id, " // 23
							+ " nmrg_nncpf, " // 24
							+ " nmrg_nncnpj, " // 25
							+ " nmrg_icsitdefinitiva, " // 26
							+ " nmrg_nnregistro,  " // 27
							+ " cbst_id ) " // 28
							+ " values ( " + " nextval('SQ_NEGTVDR_MOVT_REG'), " // 01
							+ idNegativacaoMovimento + ", " // 02
							+ "null, " // 03
							+ idNrTipo + ", " // 04
							+ "'" + registroHeader.toString() + "'" + ", " // 05
							+ " now(), " // 06
							+ "null," // 07
							+ "null," // 08
							+ "null," // 09
							+ "null," // 10
							+ "null," // 11
							+ "null," // 12
							+ "null," // 13
							+ "null," // 14
							+ "null," // 15
							+ "null," // 16
							+ "null," // 17
							+ "null," // 18
							+ "null," // 19
							+ idNegCriterio + ", " // 20
							+ "null," // 21
							+ "null," // 22
							+ "null," // 23
							+ "null," // 24
							+ "null," // 25
							+ "2, " // 26
							+ (quantidadeRegistros) // 27
							+ ",null )"; // 28
			stmt.executeUpdate(insert);
			// System.out.print("1 - INSERINDO geraRegistroNegativacaoReg!! ");
			nextValId = this.getNextNegativadorMovimentoReg();
		}catch(HibernateException e){
			nextValId = 0;
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate geraRegistroNegativacaoReg ");
		}catch(SQLException e){
			nextValId = 0;
			throw new ErroRepositorioException(e, "Erro no Insert geraRegistroNegativacaoReg ");
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
		return nextValId;
	}

	/**
	 * Consulta os Negativadores para a geracao do resumo diario da negativacao
	 * [UC0688] Gerar Resumo Diário da Negativação
	 * Fluxo principal Item 1.0
	 * 
	 * @author Vivianne Sousa
	 * @date 30/10/2009
	 */
	public List consultarNegativadorMovimentoReg(Integer idRota) throws ErroRepositorioException{

		List retorno = new ArrayList();
		Session session = HibernateUtil.getSession();

		try{
			String hql = " select nmr" + " from gcom.cobranca.NegativadorMovimentoReg nmr" + " left join fetch nmr.quadra as quad "
							+ " left join fetch quad.rota as rot " + " left join fetch nmr.negativadorMovimento as nm "
							// + " left join fetch nm.negativador as n "
							+ " where " + " nm.codigoMovimento = 1 " + " and nmr.imovel is not null  " + " and nmr.indicadorAceito = 1 "
							+ " and rot.id = " + idRota + " order by nm.id,nmr.id ";

			retorno = (List) session.createQuery(hql).list();

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
	 * Informações Atualizadas em (maior data e hora da última execução
	 * 
	 * @author Yara Taciane
	 * @date 28/07/2008
	 */
	public Date getDataUltimaAtualizacaoResumoNegativacao(Integer numeroExecucaoResumoNegativacao) throws ErroRepositorioException{

		Date retorno = null;
		Session session = HibernateUtil.getSession();
		try{
			String hql = "select max(ultimaAlteracao) from gcom.cobranca.ResumoNegativacao "
							+ " where numeroExecucaoResumoNegativacao= :numeroExecucaoResumoNegativacao ";
			retorno = (Date) session.createQuery(hql).setInteger("numeroExecucaoResumoNegativacao", numeroExecucaoResumoNegativacao)
							.uniqueResult();
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate getNextNegativadorComando ");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	public Object[] pesquisarComandoNegativacaoSimulado(Integer idComandoNegativacao) throws ErroRepositorioException{

		Object[] retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "select negCri.ngct_dstitulo as descricaoTitulo,       "// 0
							+ " negCom.ngcm_icsimulacao as indicadorSimulacao,		  "// 1
							+ " negCom.ngcm_tmrealizacao as dataHoraRealizacao        "// 2
							+ " from negativacao_comando negCom"
							+ " inner join negativacao_criterio negCri on negCom.ngcm_id = negCri.ngcm_id" + " where negCom.ngcm_id = :id";

			retorno = (Object[]) session.createSQLQuery(consulta).addScalar("descricaoTitulo", Hibernate.STRING)
							.addScalar("indicadorSimulacao", Hibernate.SHORT).addScalar("dataHoraRealizacao", Hibernate.DATE)
							.setInteger("id", new Integer(idComandoNegativacao)).setMaxResults(1).uniqueResult();

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
	 * Método usado para consulta de comandos de negativação por tipo de comando
	 * (nesse caso critério)usado no caso de uso [UC0691]
	 * 
	 * @author Thiago Vieira
	 * @date 16/01/2008
	 * @return Collection
	 * @throws ErroRepositorioException
	 */

	public Collection pesquisarComandoNegativacaoTipoCriterio(ComandoNegativacaoTipoCriterioHelper comandoNegativacaoTipoCriterioHelper,
					Integer numeroPagina) throws ErroRepositorioException{

		List retorno = new ArrayList();

		Session session = HibernateUtil.getSession();

		try{

			String hql = "select ncri from " + "gcom.cobranca.NegativacaoCriterio ncri "
							+ "left join fetch ncri.negativacaoComando as ncom " + "left join fetch ncri.cliente as clie "
							+ "left join fetch ncri.clienteRelacaoTipo as crtp " + "left join fetch ncom.negativador as nega "
							+ "left join fetch ncom.usuario as usua " + "where 1 = 1 ";

			String filtro = "";

			if(comandoNegativacaoTipoCriterioHelper.getIdNegativador() != null
							&& comandoNegativacaoTipoCriterioHelper.getIdNegativador() > 0){
				filtro = filtro + "and nega.id = " + comandoNegativacaoTipoCriterioHelper.getIdNegativador();
			}

			if(comandoNegativacaoTipoCriterioHelper.getTitulo() != null && !comandoNegativacaoTipoCriterioHelper.getTitulo().equals("")){
				if(comandoNegativacaoTipoCriterioHelper.getTipoPesquisaTitulo().equals(ConstantesSistema.TIPO_PESQUISA_INICIAL)){
					hql = hql + " and upper(ncri.descricaoTitulo) like '" + comandoNegativacaoTipoCriterioHelper.getTitulo().toUpperCase()
									+ "%'";
				}else{
					hql = hql + " and upper(ncri.descricaoTitulo) like '%" + comandoNegativacaoTipoCriterioHelper.getTitulo().toUpperCase()
									+ "%'";
				}
			}

			if(comandoNegativacaoTipoCriterioHelper.getComandoSimulado() != 0
							&& comandoNegativacaoTipoCriterioHelper.getComandoSimulado() != ConstantesSistema.TODOS){
				filtro = filtro + " and ncom.indicadorSimulacao = " + comandoNegativacaoTipoCriterioHelper.getComandoSimulado();
			}

			if(comandoNegativacaoTipoCriterioHelper.getCodigoCliente() != null
							&& comandoNegativacaoTipoCriterioHelper.getCodigoCliente() > 0){
				filtro = filtro + " and clie.id = " + comandoNegativacaoTipoCriterioHelper.getCodigoCliente();
			}

			if(comandoNegativacaoTipoCriterioHelper.getIdTipoRelacao() != null
							&& comandoNegativacaoTipoCriterioHelper.getIdTipoRelacao() > 0){
				filtro = filtro + " and crtp.id = " + comandoNegativacaoTipoCriterioHelper.getIdTipoRelacao();
			}

			if(comandoNegativacaoTipoCriterioHelper.getIdGrupoCobranca() != null
							&& comandoNegativacaoTipoCriterioHelper.getIdGrupoCobranca() > 0){
				filtro = filtro
								+ " and ncri.id in (select nccg.comp_id.negativacaoCriterio.id from gcom.cobranca.NegativCritCobrGrupo nccg where nccg.comp_id.cobrancaGrupo.id = "
								+ comandoNegativacaoTipoCriterioHelper.getIdGrupoCobranca() + ")";
			}

			if(comandoNegativacaoTipoCriterioHelper.getIdGerenciaRegional() != null
							&& comandoNegativacaoTipoCriterioHelper.getIdGerenciaRegional() > 0){
				filtro = filtro
								+ " and ncri.id in (select ncgr.comp_id.negativacaoCriterio.id from gcom.cobranca.NegativCritGerReg ncgr where ncgr.comp_id.gerenciaRegional.id = "
								+ comandoNegativacaoTipoCriterioHelper.getIdGerenciaRegional() + ")";
			}

			if(comandoNegativacaoTipoCriterioHelper.getIdUnidadeNegocio() != null
							&& comandoNegativacaoTipoCriterioHelper.getIdUnidadeNegocio() > 0){
				filtro = filtro
								+ " and ncri.id in (select ncun.comp_id.negativacaoCriterio.id from gcom.cobranca.NegativCritUndNeg ncun where ncun.comp_id.unidadeNegocio.id = "
								+ comandoNegativacaoTipoCriterioHelper.getIdUnidadeNegocio() + ")";
			}

			if(comandoNegativacaoTipoCriterioHelper.getIdEloPolo() != null && comandoNegativacaoTipoCriterioHelper.getIdEloPolo() > 0){
				filtro = filtro
								+ " and ncri.id in (select ncel.comp_id.negativacaoCriterio.id from gcom.cobranca.NegativCritElo ncel where ncel.comp_id.localidade.id = "
								+ comandoNegativacaoTipoCriterioHelper.getIdEloPolo() + ")";
			}

			if(comandoNegativacaoTipoCriterioHelper.getCodigoLocalidadeInicial() != null
							&& comandoNegativacaoTipoCriterioHelper.getCodigoLocalidadeInicial() > 0){
				filtro = filtro + " and ncri.localidadeInicial = " + comandoNegativacaoTipoCriterioHelper.getCodigoLocalidadeInicial();
				filtro = filtro + " and ncri.localidadeFinal = " + comandoNegativacaoTipoCriterioHelper.getCodigoLocalidadeFinal();
			}

			if(comandoNegativacaoTipoCriterioHelper.getCodigoSetorComercialInicial() != null
							&& comandoNegativacaoTipoCriterioHelper.getCodigoSetorComercialInicial() > 0){
				filtro = filtro + " and ncri.codigoSetorComercialInicial = "
								+ comandoNegativacaoTipoCriterioHelper.getCodigoSetorComercialInicial();
				filtro = filtro + " and ncri.codigoSetorComercialFinal = "
								+ comandoNegativacaoTipoCriterioHelper.getCodigoSetorComercialFinal();
			}

			if(comandoNegativacaoTipoCriterioHelper.getGeracaoComandoDataInicial() != null
							&& !comandoNegativacaoTipoCriterioHelper.getGeracaoComandoDataInicial().equals("")){
				String geracaoComandoDataInicialString = Util.recuperaDataInvertida(comandoNegativacaoTipoCriterioHelper
								.getGeracaoComandoDataInicial());
				String geracaoComandoDataFinalString = Util.recuperaDataInvertida(comandoNegativacaoTipoCriterioHelper
								.getGeracaoComandoDataFinal());

				if(geracaoComandoDataInicialString != null && !geracaoComandoDataInicialString.equals("")
								&& geracaoComandoDataInicialString.trim().length() == 8){
					geracaoComandoDataInicialString = geracaoComandoDataInicialString.substring(0, 4) + "-"
									+ geracaoComandoDataInicialString.substring(4, 6) + "-"
									+ geracaoComandoDataInicialString.substring(6, 8);
				}
				if(geracaoComandoDataFinalString != null && !geracaoComandoDataFinalString.equals("")
								&& geracaoComandoDataFinalString.trim().length() == 8){
					geracaoComandoDataFinalString = geracaoComandoDataFinalString.substring(0, 4) + "-"
									+ geracaoComandoDataFinalString.substring(4, 6) + "-" + geracaoComandoDataFinalString.substring(6, 8);
				}

				if(geracaoComandoDataInicialString != null && !geracaoComandoDataInicialString.equals("")
								&& geracaoComandoDataFinalString != null && !geracaoComandoDataFinalString.equals("")){

					filtro = filtro + " and ncom.dataHoraComando >= '" + geracaoComandoDataInicialString + "'";
					filtro = filtro + " and ncom.dataHoraComando <= '" + geracaoComandoDataFinalString + " 24:00:00'";
				}
			}

			if(comandoNegativacaoTipoCriterioHelper.getSituacaoComando().toString().equals(ConstantesSistema.COMANDO_SIMULADO_SIM)){

				if(comandoNegativacaoTipoCriterioHelper.getExecucaoComandoDataInicial() != null
								&& !comandoNegativacaoTipoCriterioHelper.getExecucaoComandoDataInicial().equals("")){
					String execucaoComandoDataInicialString = Util.recuperaDataInvertida(comandoNegativacaoTipoCriterioHelper
									.getExecucaoComandoDataInicial());
					String execucaoComandoDataFinalString = Util.recuperaDataInvertida(comandoNegativacaoTipoCriterioHelper
									.getExecucaoComandoDataFinal());

					if(execucaoComandoDataInicialString != null && !execucaoComandoDataInicialString.equals("")
									&& execucaoComandoDataInicialString.trim().length() == 8){
						execucaoComandoDataInicialString = execucaoComandoDataInicialString.substring(0, 4) + "-"
										+ execucaoComandoDataInicialString.substring(4, 6) + "-"
										+ execucaoComandoDataInicialString.substring(6, 8);
					}
					if(execucaoComandoDataFinalString != null && !execucaoComandoDataFinalString.equals("")
									&& execucaoComandoDataFinalString.trim().length() == 8){
						execucaoComandoDataFinalString = execucaoComandoDataFinalString.substring(0, 4) + "-"
										+ execucaoComandoDataFinalString.substring(4, 6) + "-"
										+ execucaoComandoDataFinalString.substring(6, 8);
					}

					if(execucaoComandoDataInicialString != null && !execucaoComandoDataInicialString.equals("")
									&& execucaoComandoDataFinalString != null && !execucaoComandoDataFinalString.equals("")){

						filtro = filtro + " and ncom.dataHoraRealizacao >= '" + execucaoComandoDataInicialString + "'";
						filtro = filtro + " and ncom.dataHoraRealizacao <= '" + execucaoComandoDataFinalString + " 24:00:00'";
					}
				}else{
					filtro = filtro + " and ncom.dataHoraRealizacao is not null";
				}
			}else if(comandoNegativacaoTipoCriterioHelper.getSituacaoComando().toString().equals(ConstantesSistema.COMANDO_SIMULADO_NAO)){
				filtro = filtro + " and ncom.dataHoraRealizacao is null ";
			}

			if(comandoNegativacaoTipoCriterioHelper.getReferenciaDebitoDataInicial() != null
							&& comandoNegativacaoTipoCriterioHelper.getReferenciaDebitoDataInicial() > 0){
				filtro = filtro + " and ncri.anoMesReferenciaContaInicial >= "
								+ comandoNegativacaoTipoCriterioHelper.getReferenciaDebitoDataInicial();
				filtro = filtro + " and ncri.anoMesReferenciaContaFinal <= "
								+ comandoNegativacaoTipoCriterioHelper.getReferenciaDebitoDataFinal();
			}

			if(comandoNegativacaoTipoCriterioHelper.getVencimentoDebitoDataInicial() != null
							&& !comandoNegativacaoTipoCriterioHelper.getVencimentoDebitoDataInicial().equals("")){
				String vencimentoDebitoDataInicialString = Util.recuperaDataInvertida(comandoNegativacaoTipoCriterioHelper
								.getVencimentoDebitoDataInicial());
				String vencimentoDebitoDataFinalString = Util.recuperaDataInvertida(comandoNegativacaoTipoCriterioHelper
								.getVencimentoDebitoDataFinal());

				if(vencimentoDebitoDataInicialString != null && !vencimentoDebitoDataInicialString.equals("")
								&& vencimentoDebitoDataInicialString.trim().length() == 8){
					vencimentoDebitoDataInicialString = vencimentoDebitoDataInicialString.substring(0, 4) + "-"
									+ vencimentoDebitoDataInicialString.substring(4, 6) + "-"
									+ vencimentoDebitoDataInicialString.substring(6, 8);
				}
				if(vencimentoDebitoDataFinalString != null && !vencimentoDebitoDataFinalString.equals("")
								&& vencimentoDebitoDataFinalString.trim().length() == 8){
					vencimentoDebitoDataFinalString = vencimentoDebitoDataFinalString.substring(0, 4) + "-"
									+ vencimentoDebitoDataFinalString.substring(4, 6) + "-"
									+ vencimentoDebitoDataFinalString.substring(6, 8);
				}

				if(vencimentoDebitoDataInicialString != null && !vencimentoDebitoDataInicialString.equals("")
								&& vencimentoDebitoDataFinalString != null && !vencimentoDebitoDataFinalString.equals("")){
					filtro = filtro + " and ncri.dataVencimentoDebitoInicial >= '" + vencimentoDebitoDataInicialString + "'";
					filtro = filtro + " and ncri.dataVencimentoDebitoFinal <= '" + vencimentoDebitoDataFinalString + " 24:00:00'";
				}
			}

			if(comandoNegativacaoTipoCriterioHelper.getValorDebitoInicial() != null){
				filtro = filtro + " and ncri.valorMinimoDebito >= " + comandoNegativacaoTipoCriterioHelper.getValorDebitoInicial();
				filtro = filtro + " and ncri.valorMaximoDebito <= " + comandoNegativacaoTipoCriterioHelper.getValorDebitoFinal();
			}

			if(comandoNegativacaoTipoCriterioHelper.getNumeroContasInicial() > 0){
				filtro = filtro + "ncri.quantidadeMinimaContas >= " + comandoNegativacaoTipoCriterioHelper.getNumeroContasInicial()
								+ " and ";
				filtro = filtro + "ncri.quantidadeMaximaContas <= " + comandoNegativacaoTipoCriterioHelper.getNumeroContasFinal() + " and ";
			}

			if(comandoNegativacaoTipoCriterioHelper.getCartaParcelamentoAtraso() == ConstantesSistema.SIM){
				filtro = filtro + "ncri.indicadorNegativacaoRecebimentoCartaParcelamento = " + ConstantesSistema.SIM + " and ";
			}

			retorno = (List) session.createQuery(hql + filtro).setFirstResult(10 * numeroPagina).setMaxResults(10).list();

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
	 * Método usado para consulta de comandos de negativação por tipo de comando
	 * (nesse caso critério)usado no caso de uso [UC0691]
	 * 
	 * @author Thiago Vieira
	 * @date 16/01/2008
	 * @return Collection
	 * @throws ErroRepositorioException
	 */

	public Collection pesquisarComandoNegativacaoTipoCriterio(ComandoNegativacaoTipoCriterioHelper comandoNegativacaoTipoCriterioHelper)
					throws ErroRepositorioException{

		List retorno = new ArrayList();

		Session session = HibernateUtil.getSession();

		try{

			String hql = "select ncri from " + "gcom.cobranca.NegativacaoCriterio ncri "
							+ "left join fetch ncri.negativacaoComando as ncom " + "left join fetch ncri.cliente as clie "
							+ "left join fetch ncri.clienteRelacaoTipo as crtp " + "left join fetch ncom.negativador as nega "
							+ "left join fetch ncom.usuario as usua " + "where 1 = 1 ";

			String filtro = "";

			if(comandoNegativacaoTipoCriterioHelper.getIdNegativador() != null
							&& comandoNegativacaoTipoCriterioHelper.getIdNegativador() > 0){
				filtro = filtro + "and nega.id = " + comandoNegativacaoTipoCriterioHelper.getIdNegativador();
			}

			if(comandoNegativacaoTipoCriterioHelper.getTitulo() != null && !comandoNegativacaoTipoCriterioHelper.getTitulo().equals("")){
				if(comandoNegativacaoTipoCriterioHelper.getTipoPesquisaTitulo().equals(ConstantesSistema.TIPO_PESQUISA_INICIAL)){
					hql = hql + " and upper(ncri.descricaoTitulo) like '" + comandoNegativacaoTipoCriterioHelper.getTitulo().toUpperCase()
									+ "%'";
				}else{
					hql = hql + " and upper(ncri.descricaoTitulo) like '%" + comandoNegativacaoTipoCriterioHelper.getTitulo().toUpperCase()
									+ "%'";
				}
			}

			if(comandoNegativacaoTipoCriterioHelper.getComandoSimulado() != 0
							&& comandoNegativacaoTipoCriterioHelper.getComandoSimulado() != ConstantesSistema.TODOS){
				filtro = filtro + " and ncom.indicadorSimulacao = " + comandoNegativacaoTipoCriterioHelper.getComandoSimulado();
			}

			if(comandoNegativacaoTipoCriterioHelper.getCodigoCliente() != null
							&& comandoNegativacaoTipoCriterioHelper.getCodigoCliente() > 0){
				filtro = filtro + " and clie.id = " + comandoNegativacaoTipoCriterioHelper.getCodigoCliente();
			}

			if(comandoNegativacaoTipoCriterioHelper.getIdTipoRelacao() != null
							&& comandoNegativacaoTipoCriterioHelper.getIdTipoRelacao() > 0){
				filtro = filtro + " and crtp.id = " + comandoNegativacaoTipoCriterioHelper.getIdTipoRelacao();
			}

			if(comandoNegativacaoTipoCriterioHelper.getIdGrupoCobranca() != null
							&& comandoNegativacaoTipoCriterioHelper.getIdGrupoCobranca() > 0){
				filtro = filtro
								+ " and ncri.id in (select nccg.comp_id.negativacaoCriterio.id from gcom.cobranca.NegativCritCobrGrupo nccg where nccg.comp_id.cobrancaGrupo.id = "
								+ comandoNegativacaoTipoCriterioHelper.getIdGrupoCobranca() + ")";
			}

			if(comandoNegativacaoTipoCriterioHelper.getIdGerenciaRegional() != null
							&& comandoNegativacaoTipoCriterioHelper.getIdGerenciaRegional() > 0){
				filtro = filtro
								+ " and ncri.id in (select ncgr.comp_id.negativacaoCriterio.id from gcom.cobranca.NegativCritGerReg ncgr where ncgr.comp_id.gerenciaRegional.id = "
								+ comandoNegativacaoTipoCriterioHelper.getIdGerenciaRegional() + ")";
			}

			if(comandoNegativacaoTipoCriterioHelper.getIdUnidadeNegocio() != null
							&& comandoNegativacaoTipoCriterioHelper.getIdUnidadeNegocio() > 0){
				filtro = filtro
								+ " and ncri.id in (select ncun.comp_id.negativacaoCriterio.id from gcom.cobranca.NegativCritUndNeg ncun where ncun.comp_id.unidadeNegocio.id = "
								+ comandoNegativacaoTipoCriterioHelper.getIdUnidadeNegocio() + ")";
			}

			if(comandoNegativacaoTipoCriterioHelper.getIdEloPolo() != null && comandoNegativacaoTipoCriterioHelper.getIdEloPolo() > 0){
				filtro = filtro
								+ " and ncri.id in (select ncel.comp_id.negativacaoCriterio.id from gcom.cobranca.NegativCritElo ncel where ncel.comp_id.localidade.id = "
								+ comandoNegativacaoTipoCriterioHelper.getIdEloPolo() + ")";
			}

			if(comandoNegativacaoTipoCriterioHelper.getCodigoLocalidadeInicial() != null
							&& comandoNegativacaoTipoCriterioHelper.getCodigoLocalidadeInicial() > 0){
				filtro = filtro + " and ncri.localidadeInicial = " + comandoNegativacaoTipoCriterioHelper.getCodigoLocalidadeInicial();
				filtro = filtro + " and ncri.localidadeFinal = " + comandoNegativacaoTipoCriterioHelper.getCodigoLocalidadeFinal();
			}

			if(comandoNegativacaoTipoCriterioHelper.getCodigoSetorComercialInicial() != null
							&& comandoNegativacaoTipoCriterioHelper.getCodigoSetorComercialInicial() > 0){
				filtro = filtro + " and ncri.codigoSetorComercialInicial = "
								+ comandoNegativacaoTipoCriterioHelper.getCodigoSetorComercialInicial();
				filtro = filtro + " and ncri.codigoSetorComercialFinal = "
								+ comandoNegativacaoTipoCriterioHelper.getCodigoSetorComercialFinal();
			}

			if(comandoNegativacaoTipoCriterioHelper.getGeracaoComandoDataInicial() != null
							&& !comandoNegativacaoTipoCriterioHelper.getGeracaoComandoDataInicial().equals("")){
				String geracaoComandoDataInicialString = Util.recuperaDataInvertida(comandoNegativacaoTipoCriterioHelper
								.getGeracaoComandoDataInicial());
				String geracaoComandoDataFinalString = Util.recuperaDataInvertida(comandoNegativacaoTipoCriterioHelper
								.getGeracaoComandoDataFinal());

				if(geracaoComandoDataInicialString != null && !geracaoComandoDataInicialString.equals("")
								&& geracaoComandoDataInicialString.trim().length() == 8){
					geracaoComandoDataInicialString = geracaoComandoDataInicialString.substring(0, 4) + "-"
									+ geracaoComandoDataInicialString.substring(4, 6) + "-"
									+ geracaoComandoDataInicialString.substring(6, 8);
				}
				if(geracaoComandoDataFinalString != null && !geracaoComandoDataFinalString.equals("")
								&& geracaoComandoDataFinalString.trim().length() == 8){
					geracaoComandoDataFinalString = geracaoComandoDataFinalString.substring(0, 4) + "-"
									+ geracaoComandoDataFinalString.substring(4, 6) + "-" + geracaoComandoDataFinalString.substring(6, 8);
				}

				if(geracaoComandoDataInicialString != null && !geracaoComandoDataInicialString.equals("")
								&& geracaoComandoDataFinalString != null && !geracaoComandoDataFinalString.equals("")){

					filtro = filtro + " and ncom.dataHoraComando >= '" + geracaoComandoDataInicialString + "'";
					filtro = filtro + " and ncom.dataHoraComando <= '" + geracaoComandoDataFinalString + " 24:00:00'";
				}
			}

			if(comandoNegativacaoTipoCriterioHelper.getSituacaoComando().toString().equals(ConstantesSistema.COMANDO_SIMULADO_SIM)){

				if(comandoNegativacaoTipoCriterioHelper.getExecucaoComandoDataInicial() != null
								&& !comandoNegativacaoTipoCriterioHelper.getExecucaoComandoDataInicial().equals("")){
					String execucaoComandoDataInicialString = Util.recuperaDataInvertida(comandoNegativacaoTipoCriterioHelper
									.getExecucaoComandoDataInicial());
					String execucaoComandoDataFinalString = Util.recuperaDataInvertida(comandoNegativacaoTipoCriterioHelper
									.getExecucaoComandoDataFinal());

					if(execucaoComandoDataInicialString != null && !execucaoComandoDataInicialString.equals("")
									&& execucaoComandoDataInicialString.trim().length() == 8){
						execucaoComandoDataInicialString = execucaoComandoDataInicialString.substring(0, 4) + "-"
										+ execucaoComandoDataInicialString.substring(4, 6) + "-"
										+ execucaoComandoDataInicialString.substring(6, 8);
					}
					if(execucaoComandoDataFinalString != null && !execucaoComandoDataFinalString.equals("")
									&& execucaoComandoDataFinalString.trim().length() == 8){
						execucaoComandoDataFinalString = execucaoComandoDataFinalString.substring(0, 4) + "-"
										+ execucaoComandoDataFinalString.substring(4, 6) + "-"
										+ execucaoComandoDataFinalString.substring(6, 8);
					}

					if(execucaoComandoDataInicialString != null && !execucaoComandoDataInicialString.equals("")
									&& execucaoComandoDataFinalString != null && !execucaoComandoDataFinalString.equals("")){

						filtro = filtro + " and ncom.dataHoraRealizacao >= '" + execucaoComandoDataInicialString + "'";
						filtro = filtro + " and ncom.dataHoraRealizacao <= '" + execucaoComandoDataFinalString + " 24:00:00'";
					}
				}else{
					filtro = filtro + " and ncom.dataHoraRealizacao is not null";
				}
			}else if(comandoNegativacaoTipoCriterioHelper.getSituacaoComando().toString().equals(ConstantesSistema.COMANDO_SIMULADO_NAO)){
				filtro = filtro + " and ncom.dataHoraRealizacao is null ";
			}

			if(comandoNegativacaoTipoCriterioHelper.getReferenciaDebitoDataInicial() != null
							&& comandoNegativacaoTipoCriterioHelper.getReferenciaDebitoDataInicial() > 0){
				filtro = filtro + " and ncri.anoMesReferenciaContaInicial >= "
								+ comandoNegativacaoTipoCriterioHelper.getReferenciaDebitoDataInicial();
				filtro = filtro + " and ncri.anoMesReferenciaContaFinal <= "
								+ comandoNegativacaoTipoCriterioHelper.getReferenciaDebitoDataFinal();
			}

			if(comandoNegativacaoTipoCriterioHelper.getVencimentoDebitoDataInicial() != null
							&& !comandoNegativacaoTipoCriterioHelper.getVencimentoDebitoDataInicial().equals("")){
				String vencimentoDebitoDataInicialString = Util.recuperaDataInvertida(comandoNegativacaoTipoCriterioHelper
								.getVencimentoDebitoDataInicial());
				String vencimentoDebitoDataFinalString = Util.recuperaDataInvertida(comandoNegativacaoTipoCriterioHelper
								.getVencimentoDebitoDataFinal());

				if(vencimentoDebitoDataInicialString != null && !vencimentoDebitoDataInicialString.equals("")
								&& vencimentoDebitoDataInicialString.trim().length() == 8){
					vencimentoDebitoDataInicialString = vencimentoDebitoDataInicialString.substring(0, 4) + "-"
									+ vencimentoDebitoDataInicialString.substring(4, 6) + "-"
									+ vencimentoDebitoDataInicialString.substring(6, 8);
				}
				if(vencimentoDebitoDataFinalString != null && !vencimentoDebitoDataFinalString.equals("")
								&& vencimentoDebitoDataFinalString.trim().length() == 8){
					vencimentoDebitoDataFinalString = vencimentoDebitoDataFinalString.substring(0, 4) + "-"
									+ vencimentoDebitoDataFinalString.substring(4, 6) + "-"
									+ vencimentoDebitoDataFinalString.substring(6, 8);
				}

				if(vencimentoDebitoDataInicialString != null && !vencimentoDebitoDataInicialString.equals("")
								&& vencimentoDebitoDataFinalString != null && !vencimentoDebitoDataFinalString.equals("")){
					filtro = filtro + " and ncri.dataVencimentoDebitoInicial >= '" + vencimentoDebitoDataInicialString + "'";
					filtro = filtro + " and ncri.dataVencimentoDebitoFinal <= '" + vencimentoDebitoDataFinalString + " 24:00:00'";
				}
			}

			if(comandoNegativacaoTipoCriterioHelper.getValorDebitoInicial() != null){
				filtro = filtro + " and ncri.valorMinimoDebito >= " + comandoNegativacaoTipoCriterioHelper.getValorDebitoInicial();
				filtro = filtro + " and ncri.valorMaximoDebito <= " + comandoNegativacaoTipoCriterioHelper.getValorDebitoFinal();
			}

			if(comandoNegativacaoTipoCriterioHelper.getNumeroContasInicial() > 0){
				filtro = filtro + "ncri.quantidadeMinimaContas >= " + comandoNegativacaoTipoCriterioHelper.getNumeroContasInicial()
								+ " and ";
				filtro = filtro + "ncri.quantidadeMaximaContas <= " + comandoNegativacaoTipoCriterioHelper.getNumeroContasFinal() + " and ";
			}

			if(comandoNegativacaoTipoCriterioHelper.getCartaParcelamentoAtraso() == ConstantesSistema.SIM){
				filtro = filtro + "ncri.indicadorNegativacaoRecebimentoCartaParcelamento = " + ConstantesSistema.SIM + " and ";
			}

			retorno = (List) session.createQuery(hql + filtro).list();

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
	 * Método usado para consulta de comandos de negativação por tipo de comando
	 * (nesse caso matrícula)usado no caso de uso [UC0691]
	 * 
	 * @author Thiago Vieira
	 * @date 16/01/2008
	 * @return Collection
	 * @throws ErroRepositorioException
	 */

	public Collection pesquisarComandoNegativacaoTipoMatricula(ComandoNegativacaoHelper comandoNegativacaoHelper)
					throws ErroRepositorioException{

		List retorno = new ArrayList();
		Session session = HibernateUtil.getSession();

		try{
			String hql = " select ncom from gcom.cobranca.NegativacaoComando as ncom inner join fetch ncom.negativador as n "
							+ " inner join fetch ncom.usuario as u inner join fetch ncom.negativador.cliente as c ";

			String filtro = "where ";
			if(comandoNegativacaoHelper.getIdNegativador() != null && comandoNegativacaoHelper.getIdNegativador() > 0){
				filtro = filtro + "n.id = " + comandoNegativacaoHelper.getIdNegativador() + " and ";
			}
			if(comandoNegativacaoHelper.getIdentificacaoCI() != null && !comandoNegativacaoHelper.getIdentificacaoCI().equals("")){
				if(comandoNegativacaoHelper.getTipoPesquisaIdentificacaoCI().equals(ConstantesSistema.TIPO_PESQUISA_INICIAL)){
					filtro = filtro + "upper(ncom.descricaoComunicacaoInterna) like '"
									+ comandoNegativacaoHelper.getIdentificacaoCI().toUpperCase() + "%' and ";
				}else{
					filtro = filtro + "upper(ncom.descricaoComunicacaoInterna) like '%"
									+ comandoNegativacaoHelper.getIdentificacaoCI().toUpperCase() + "%' and ";
				}
			}
			if(comandoNegativacaoHelper.getIdUsuarioResponsavel() != null && comandoNegativacaoHelper.getIdUsuarioResponsavel() > 0){
				filtro = filtro + "u.id = " + comandoNegativacaoHelper.getIdUsuarioResponsavel();
			}

			if(!filtro.equals("where ")){
				hql = hql + filtro;
			}

			if(hql.substring(hql.length() - 5, hql.length()).equals(" and ")){
				hql = Util.removerUltimosCaracteres(hql, 4);
			}

			retorno = (List) session.createQuery(hql).list();

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
	 * Método usado para consulta de comandos de negativação por tipo de comando
	 * (nesse caso matrícula)usado no caso de uso [UC0691]
	 * 
	 * @author Thiago Vieira
	 * @date 16/01/2008
	 * @return Collection
	 * @throws ErroRepositorioException
	 */

	public Collection pesquisarComandoNegativacaoTipoMatricula(ComandoNegativacaoHelper comandoNegativacaoHelper, Integer numeroPagina)
					throws ErroRepositorioException{

		List retorno = new ArrayList();
		Session session = HibernateUtil.getSession();

		try{
			String hql = " select ncom from  " + "gcom.cobranca.NegativacaoComando as ncom " + " inner join fetch ncom.negativador as n "
							+ " inner join fetch ncom.usuario as u " + " inner join fetch ncom.negativador.cliente as c ";

			String filtro = "where ";
			if(comandoNegativacaoHelper.getIdNegativador() != null && comandoNegativacaoHelper.getIdNegativador() > 0){
				filtro = filtro + "n.id = " + comandoNegativacaoHelper.getIdNegativador() + " and ";
			}
			if(comandoNegativacaoHelper.getIdentificacaoCI() != null && !comandoNegativacaoHelper.getIdentificacaoCI().equals("")){
				if(comandoNegativacaoHelper.getTipoPesquisaIdentificacaoCI().equals(ConstantesSistema.TIPO_PESQUISA_INICIAL)){
					filtro = filtro + "upper(ncom.descricaoComunicacaoInterna) like '"
									+ comandoNegativacaoHelper.getIdentificacaoCI().toUpperCase() + "%' and ";
				}else{
					filtro = filtro + "upper(ncom.descricaoComunicacaoInterna) like '%"
									+ comandoNegativacaoHelper.getIdentificacaoCI().toUpperCase() + "%' and ";
				}
			}
			if(comandoNegativacaoHelper.getIdUsuarioResponsavel() != null && comandoNegativacaoHelper.getIdUsuarioResponsavel() > 0){
				filtro = filtro + "u.id = " + comandoNegativacaoHelper.getIdUsuarioResponsavel();
			}

			if(!filtro.equals("where ")){
				hql = hql + filtro;
			}

			if(hql.substring(hql.length() - 5, hql.length()).equals(" and ")){
				hql = Util.removerUltimosCaracteres(hql, 4);
			}

			// retorno = (List) session.createQuery(hql).list();
			retorno = (List) session.createQuery(hql).setFirstResult(10 * numeroPagina).setMaxResults(10).list();

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
	 * [UC0688] - Gerar Resumo Diario da Negativação
	 * [UC0694] - Gerar Relatório Negativações Excluídas
	 * pesquisa ocorrência na tabela NEGATIVADOR_MOVIMENTO_REG_PARCELAMENTO para NMRG_ID=NMRG_ID
	 * da tabela NEGATIVADOR_MOVIMENTO_REG)
	 * 
	 * @author Vivianne Sousa
	 * @data 22/04/2009
	 * @param idNegativadorMovimentoReg
	 * @return NegativadorMovimentoRegParcelamento
	 */
	public Collection pesquisarNegativadorMovimentoRegParcelamento(Integer idNegativadorMovimentoReg) throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();
		Query query = null;

		StringBuffer hql = new StringBuffer();
		Map parameters = new HashMap();

		try{

			hql.append("select nmrp ");
			hql.append("from NegativadorMovimentoRegParcelamento nmrp, NegativadorMovimentoReg nmg ");
			hql.append("where nmrp.negativadorMovimentoReg.id = nmg.id and ");
			hql.append("nmg.id = :idNegativadorMovimentoReg ");

			// hql.append("SELECT nmrp ");
			// hql.append("FROM NegativadorMovimentoRegParcelamento as nmrp ");
			// hql.append("INNER JOIN nmrp.negativadorMovimentoReg as nmr ");
			// hql.append("WHERE nmr.id = :idNegativadorMovimentoReg ");

			parameters.put("idNegativadorMovimentoReg", idNegativadorMovimentoReg);

			query = session.createQuery(hql.toString());
			query = Util.configuraParamentrosEmQuery(query, parameters);

			retorno = query.list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * Pesquisa a Data da Exclusão da Negativação
	 * 
	 * @author Yara Taciane
	 * @date 9/05/2008
	 * @param
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Date pesquisarDataExclusaoNegativacao(int idImovel, int idNegativacaoComando) throws ErroRepositorioException{

		Date retorno = null;
		Session session = HibernateUtil.getSession();
		try{

			String hql = " select " + "  negImovel.dataExclusao" + " from gcom.cobranca.NegativacaoImovei as negImovel "
							+ "   inner join negImovel.imovel as imov " + "   inner join negImovel.cobrancaAcaoAtividadeComando as negCom "
							+ "   where " + " imov.id = :idImovel  "
							+ " and negCom.id = :idNegativacaoComando " + " and negImovel.dataExclusao is not null ";

			retorno = (Date) session.createQuery(hql).setInteger("idImovel", idImovel)
							.setInteger("idNegativacaoComando", idNegativacaoComando).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate verificaExistenciaNegativacao");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * Pesquisar se a negativação do imóvel .
	 * [UC0675] Excluir Negativação Online.
	 * 
	 * @author Yara Taciane
	 * @date 22/01/2008
	 */

	public Collection pesquisarImovelNegativado(Imovel imovel, Negativador negativador) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		Collection retorno = new ArrayList();
		try{
			String hql = " select ngim "
							+ " from gcom.cobranca.NegativacaoImovei ngim "
							+ " where ngim.imovel.id="
							+ imovel.getId()
							+ " and   ngim.indicadorExcluido = 2 "
							+ " and   (ngim.cobrancaAcaoAtividadeComando.id in "
							+ "(select ngmv.cobrancaAcaoAtividadeComando.id from gcom.cobranca.NegativadorMovimento ngmv where ngmv.negativador.id = "
							+ negativador.getId()
							+ " ) or  ngim.cobrancaAcaoAtividadeCronograma.id in "
							+ "(select ngmv.cobrancaAcaoAtividadeCronograma.id from gcom.cobranca.NegativadorMovimento ngmv where ngmv.negativador.id = "
							+ +negativador.getId() + "))";

			Query query = session.createQuery(hql);
			retorno = (List) query.list();

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
	 * Pesquisar se a inclusão do imóvel está com retorno ou foi aceita.
	 * [UC0675] Excluir Negativação Online.
	 * 
	 * @author Yara Taciane
	 * @date 22/01/2008
	 */

	public Collection pesquisarNegatiacaoParaImovel(Imovel imovel, Negativador negativador) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		Collection retorno = new ArrayList();
		try{
			String hql = " select nmrg " + " from gcom.cobranca.NegativadorMovimentoReg nmrg " + " where nmrg.imovel.id=" + imovel.getId()
							+ " and   nmrg.indicadorAceito = 1 " + " and   nmrg.negativadorMovimento.id in "
							+ "(select ngmv.id from gcom.cobranca.NegativadorMovimento ngmv  where ngmv.negativador.id = "
							+ negativador.getId() + " )";

			Query query = session.createQuery(hql);
			retorno = (List) query.list();

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
	 * [UC0651] Manter Comando de Nagativação Critério
	 * 
	 * @author Ana Maria
	 * @date 21/01/2008
	 * @param idComandoNegativacao
	 * @return Integer
	 * @throws ErroRepositorioException
	 */
	public NegativacaoCriterio pesquisarNegativacaoCriterio(Integer idComandoNegativacao) throws ErroRepositorioException{

		NegativacaoCriterio retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = " select ngct" + " from NegativacaoCriterio ngct" + " where ngct.negativacaoComando.id = :idComandoNegativacao";

			retorno = (NegativacaoCriterio) session.createQuery(consulta).setInteger("idComandoNegativacao", idComandoNegativacao)
							.uniqueResult();

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
	 * Método usado para consulta de movimento do negativador
	 * usado no caso de uso [UC0682] (com paginação)
	 * 
	 * @author Yara Taciane
	 * @date 21/01/2008
	 * @return Collection
	 * @throws ErroRepositorioException
	 */

	public Collection pesquisarNegativadorMovimento(NegativadorMovimentoHelper negativadorMovimentoHelper, Integer numeroPagina)
					throws ErroRepositorioException{

		List retorno = new ArrayList();
		Session session = HibernateUtil.getSession();
		try{

			String restricao1 = "";

			if(negativadorMovimentoHelper.getIndicadorRegistro() == ConstantesSistema.ACEITO){
				restricao1 = restricao1
								+ " ngmv.id in ( select nmrg.negativadorMovimento.id  from gcom.cobranca.NegativadorMovimentoReg nmrg where nmrg.indicadorAceito = "
								+ ConstantesSistema.ACEITO;
				if(negativadorMovimentoHelper.getIdImovel() != null){
					restricao1 = restricao1 + " and nmrg.imovel.id = " + negativadorMovimentoHelper.getIdImovel() + " )";
				}else{
					restricao1 = restricao1 + " )";
				}
			}else if(negativadorMovimentoHelper.getIndicadorRegistro() == ConstantesSistema.NAO_ACEITO){
				restricao1 = restricao1
								+ " ngmv.id in ( select nmrg.negativadorMovimento.id  from gcom.cobranca.NegativadorMovimentoReg nmrg where nmrg.indicadorAceito = "
								+ ConstantesSistema.NAO_ACEITO;
				if(negativadorMovimentoHelper.getIndicadorCorrigido() == ConstantesSistema.CORRIGIDO){
					restricao1 = restricao1 + " and nmrg.indicadorCorrecao = " + ConstantesSistema.CORRIGIDO;
				}else if(negativadorMovimentoHelper.getIndicadorCorrigido() == ConstantesSistema.NAO_CORRIGIDO){
					restricao1 = restricao1 + " and nmrg.indicadorCorrecao = " + ConstantesSistema.NAO_CORRIGIDO;
				}
				if(negativadorMovimentoHelper.getIdImovel() != null){
					restricao1 = restricao1 + " and nmrg.imovel.id = " + negativadorMovimentoHelper.getIdImovel() + " )";
				}else{
					restricao1 = restricao1 + " )";
				}
			}else{

				if(negativadorMovimentoHelper.getIdImovel() != null){
					restricao1 = restricao1
									+ " ngmv.id in ( select nmrg.negativadorMovimento.id  from gcom.cobranca.NegativadorMovimentoReg nmrg where nmrg.imovel.id ="
									+ negativadorMovimentoHelper.getIdImovel() + " )";

				}else{
					restricao1 = restricao1 + "1=1";
				}
			}

			String hql2 = "select ngmv " + " from gcom.cobranca.NegativadorMovimento ngmv "
							+ "  inner join fetch ngmv.negativador as negt " + "  inner join fetch negt.cliente as clie " + " where "
							+ restricao1;

			String restricao = "";
			if(negativadorMovimentoHelper.getIdNegativador() != null && negativadorMovimentoHelper.getIdNegativador() > 0){
				restricao = restricao + " and ngmv.negativador.id = " + negativadorMovimentoHelper.getIdNegativador();
			}
			if(negativadorMovimentoHelper.getCodigoMovimento() != -1){
				restricao = restricao + " and ngmv.codigoMovimento = " + negativadorMovimentoHelper.getCodigoMovimento();
			}

			if(negativadorMovimentoHelper.getNumeroSequencialArquivo() != null
							&& !"".equals(negativadorMovimentoHelper.getNumeroSequencialArquivo())){
				restricao = restricao + " and ngmv.numeroSequencialEnvio = " + negativadorMovimentoHelper.getNumeroSequencialArquivo();
			}

			if((negativadorMovimentoHelper.getDataProcessamentoInicial() != null && !negativadorMovimentoHelper
							.getDataProcessamentoInicial().equals(""))
							&& (negativadorMovimentoHelper.getDataProcessamentoFinal() != null && !negativadorMovimentoHelper
											.getDataProcessamentoFinal().equals(""))){
				restricao = restricao + " and ngmv.dataProcessamentoEnvio between '"
								+ negativadorMovimentoHelper.getDataProcessamentoInicial() + "' and '"
								+ negativadorMovimentoHelper.getDataProcessamentoFinal() + "' ";
			}

			if(negativadorMovimentoHelper.getIndicadorMovimento() == ConstantesSistema.COM_RETORNO){
				restricao = restricao + " and ngmv.dataProcessamentoRetorno is not null ";
			}else if(negativadorMovimentoHelper.getIndicadorMovimento() == ConstantesSistema.SEM_RETORNO){
				restricao = restricao + " and ngmv.dataProcessamentoRetorno is null";
			}

			hql2 = hql2 + restricao;

			Query query = session.createQuery(hql2);
			retorno = (List) query.setFirstResult(10 * numeroPagina).setMaxResults(10).list();

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
	 * Método usado para consulta de comandos de negativação por tipo de comando
	 * (nesse caso matrícula)usado no caso de uso [UC0691] (sem paginação)
	 * 
	 * @author Yara Taciane
	 * @date 21/01/2008
	 * @return Collection
	 * @throws ErroRepositorioException
	 */

	public Collection pesquisarNegativadorMovimento(NegativadorMovimentoHelper negativadorMovimentoHelper) throws ErroRepositorioException{

		List retorno = new ArrayList();
		Session session = HibernateUtil.getSession();

		try{

			String restricao1 = "";

			if(negativadorMovimentoHelper.getIndicadorRegistro() == ConstantesSistema.ACEITO){
				restricao1 = restricao1
								+ " ngmv.id in ( select nmrg.negativadorMovimento.id  from gcom.cobranca.NegativadorMovimentoReg nmrg where nmrg.indicadorAceito = "
								+ ConstantesSistema.ACEITO;
				if(negativadorMovimentoHelper.getIdImovel() != null){
					restricao1 = restricao1 + " and nmrg.imovel.id = " + negativadorMovimentoHelper.getIdImovel() + " )";
				}else{
					restricao1 = restricao1 + " )";
				}
			}else if(negativadorMovimentoHelper.getIndicadorRegistro() == ConstantesSistema.NAO_ACEITO){
				restricao1 = restricao1
								+ " ngmv.id in ( select nmrg.negativadorMovimento.id  from gcom.cobranca.NegativadorMovimentoReg nmrg where nmrg.indicadorAceito = "
								+ ConstantesSistema.NAO_ACEITO;
				if(negativadorMovimentoHelper.getIndicadorCorrigido() == ConstantesSistema.CORRIGIDO){
					restricao1 = restricao1 + " and nmrg.indicadorCorrecao = " + ConstantesSistema.CORRIGIDO;
				}else if(negativadorMovimentoHelper.getIndicadorCorrigido() == ConstantesSistema.NAO_CORRIGIDO){
					restricao1 = restricao1 + " and nmrg.indicadorCorrecao = " + ConstantesSistema.NAO_CORRIGIDO;
				}
				if(negativadorMovimentoHelper.getIdImovel() != null){
					restricao1 = restricao1 + " and nmrg.imovel.id = " + negativadorMovimentoHelper.getIdImovel() + " )";
				}else{
					restricao1 = restricao1 + " )";
				}
			}else{

				if(negativadorMovimentoHelper.getIdImovel() != null){
					restricao1 = restricao1
									+ " ngmv.id in ( select nmrg.negativadorMovimento.id  from gcom.cobranca.NegativadorMovimentoReg nmrg where nmrg.imovel.id ="
									+ negativadorMovimentoHelper.getIdImovel() + " )";

				}else{
					restricao1 = restricao1 + "1=1";
				}
			}

			String hql2 = "select ngmv " + " from gcom.cobranca.NegativadorMovimento ngmv "
							+ "  inner join fetch ngmv.negativador as negt " + "  inner join fetch negt.cliente as clie " + " where "
							+ restricao1;

			String restricao = "";
			if(negativadorMovimentoHelper.getIdNegativador() != null && negativadorMovimentoHelper.getIdNegativador() > 0){
				restricao = restricao + " and ngmv.negativador.id = " + negativadorMovimentoHelper.getIdNegativador();
			}
			if(negativadorMovimentoHelper.getCodigoMovimento() != -1){
				restricao = restricao + " and ngmv.codigoMovimento = " + negativadorMovimentoHelper.getCodigoMovimento();
			}

			if(negativadorMovimentoHelper.getNumeroSequencialArquivo() != null
							&& !"".equals(negativadorMovimentoHelper.getNumeroSequencialArquivo())){
				restricao = restricao + " and ngmv.numeroSequencialEnvio = " + negativadorMovimentoHelper.getNumeroSequencialArquivo();
			}

			if((negativadorMovimentoHelper.getDataProcessamentoInicial() != null && !negativadorMovimentoHelper
							.getDataProcessamentoInicial().equals(""))
							&& (negativadorMovimentoHelper.getDataProcessamentoFinal() != null && !negativadorMovimentoHelper
											.getDataProcessamentoFinal().equals(""))){
				restricao = restricao + " and ngmv.dataProcessamentoEnvio between '"
								+ negativadorMovimentoHelper.getDataProcessamentoInicial() + "' and '"
								+ negativadorMovimentoHelper.getDataProcessamentoFinal() + "' ";
			}

			if(negativadorMovimentoHelper.getIndicadorMovimento() == ConstantesSistema.COM_RETORNO){
				restricao = restricao + " and ngmv.dataProcessamentoRetorno is not null ";
			}else if(negativadorMovimentoHelper.getIndicadorMovimento() == ConstantesSistema.SEM_RETORNO){
				restricao = restricao + " and ngmv.dataProcessamentoRetorno is null";
			}

			hql2 = hql2 + restricao;

			Query query = session.createQuery(hql2);
			retorno = (List) query.list();

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
	 * Retorna o NegativadorMovimentoReg
	 * [UC0673] Excluir Negativação Online
	 * 
	 * @author Yara Taciane
	 * @date 27/03/2008
	 */
	public NegativadorMovimentoReg pesquisarNegativadorMovimentoRegInclusao(Imovel imovel, Negativador negativador)
					throws ErroRepositorioException{

		NegativadorMovimentoReg retorno = null;
		Session session = HibernateUtil.getSession();

		StringBuilder consulta = new StringBuilder();
		try{
			consulta.append(" select nmrg");
			consulta.append(" from gcom.cobranca.NegativadorMovimentoReg nmrg ");
			consulta.append(" left join nmrg.cobrancaDebitoSituacao as cdst ");
			consulta.append(" left join fetch nmrg.negativadorMovimento as ngmv ");
			consulta.append(" left join ngmv.negativador neg ");
			consulta.append(" left join neg.cliente c ");
			consulta.append(" left join nmrg.imovel as imov ");
			consulta.append(" where nmrg.imovel.id = ");
			consulta.append(imovel.getId());
			consulta.append(" and  nmrg.codigoExclusaoTipo is null");
			consulta.append(" and  ngmv.negativador.id = ");
			consulta.append(negativador.getId());
			consulta.append(" and  ngmv.codigoMovimento= ");
			consulta.append(NegativadorMovimento.CODIGO_MOVIMENTO_INCLUSAO);
			consulta.append(" and  nmrg.indicadorAceito= ");
			consulta.append(NegativadorMovimentoReg.INDICADOR_ACEITO);

			retorno = (NegativadorMovimentoReg) session.createQuery(consulta.toString()).uniqueResult();

		}catch(HibernateException ex){
			ex.printStackTrace();
			throw new ErroRepositorioException(ex, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * Apresenta todos os registros do NegativadorMovimento aceitos.
	 * [UC0681] Consultar Movimentos dos Negativadores.
	 * 
	 * @author Yara Taciane
	 * @date 22/01/2008
	 */
	public Collection pesquisarNegativadorMovimentoRegistroAceito(NegativadorMovimentoHelper negativadorMovimentoHelper)
					throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		Collection retorno = new ArrayList();
		try{

			String hql = " select nmrg " + " from gcom.cobranca.NegativadorMovimentoReg nmrg "
							+ " inner join fetch nmrg.negativadorRegistroTipo as nrtp "
							+ " inner join fetch nmrg.negativadorMovimento as ngmv " + " where nmrg.negativadorMovimento.id = "
							+ negativadorMovimentoHelper.getIdNegativadorMovimento();

			String restricao = "";

			if(negativadorMovimentoHelper.getIdImovel() != null){

				if(negativadorMovimentoHelper.getIdNegativador().equals(Negativador.NEGATIVADOR_SPC_SAO_PAULO)){

					Integer numeroRegistro = this.pesquisarNumeroRegistro(negativadorMovimentoHelper.getIdNegativadorMovimento(),
									negativadorMovimentoHelper.getIdImovel());

					if(numeroRegistro != null){

						Integer numeroRegistroAnterior = numeroRegistro - 1;

						restricao = restricao + " and nmrg.numeroRegistro in (" + numeroRegistro + "," + numeroRegistroAnterior + " )";
					}
				}else{
					restricao = restricao + " and nmrg.imovel.id = " + negativadorMovimentoHelper.getIdImovel();
				}
			}

			if(negativadorMovimentoHelper.getCodigoMovimento() != -1){
				restricao = restricao + " and ngmv.codigoMovimento = " + negativadorMovimentoHelper.getCodigoMovimento();
			}

			if(negativadorMovimentoHelper.getNumeroSequencialArquivo() != null
							&& !"".equals(negativadorMovimentoHelper.getNumeroSequencialArquivo())){
				restricao = restricao + " and ngmv.numeroSequencialEnvio = " + negativadorMovimentoHelper.getNumeroSequencialArquivo();
			}

			if((negativadorMovimentoHelper.getDataProcessamentoInicial() != null && !negativadorMovimentoHelper
							.getDataProcessamentoInicial().equals(""))
							&& (negativadorMovimentoHelper.getDataProcessamentoFinal() != null && !negativadorMovimentoHelper
											.getDataProcessamentoFinal().equals(""))){
				restricao = restricao + " and ngmv.dataProcessamentoEnvio between '"
								+ negativadorMovimentoHelper.getDataProcessamentoInicial() + "' and '"
								+ negativadorMovimentoHelper.getDataProcessamentoFinal() + "' ";
			}

			if(negativadorMovimentoHelper.getIndicadorMovimento() == ConstantesSistema.COM_RETORNO){

				restricao = restricao + " and ngmv.dataProcessamentoRetorno is not null ";

			}else if(negativadorMovimentoHelper.getIndicadorMovimento() == ConstantesSistema.SEM_RETORNO){

				restricao = restricao + " and ngmv.dataProcessamentoRetorno is null";
			}

			if(negativadorMovimentoHelper.getIndicadorRegistro() == ConstantesSistema.ACEITO){

				restricao = restricao + "  and nmrg.indicadorAceito = " + ConstantesSistema.ACEITO;

			}else if(negativadorMovimentoHelper.getIndicadorRegistro() == ConstantesSistema.NAO_ACEITO){

				restricao = restricao + " and nmrg.indicadorAceito = " + ConstantesSistema.NAO_ACEITO;

				if(negativadorMovimentoHelper.getIndicadorCorrigido() == ConstantesSistema.CORRIGIDO){

					restricao = restricao + " and nmrg.indicadorCorrecao = " + ConstantesSistema.CORRIGIDO;

				}else if(negativadorMovimentoHelper.getIndicadorCorrigido() == ConstantesSistema.NAO_CORRIGIDO){

					restricao = restricao + " and nmrg.indicadorCorrecao = " + ConstantesSistema.NAO_CORRIGIDO;

				}else{
					restricao = restricao + " and 1=1  ";
				}
			}

			String orderBy = " order by nmrg.numeroRegistro, nrtp.id ";

			Query query = session.createQuery(hql + restricao + orderBy);
			retorno = (List) query.list();

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
	 * Apresenta todos os registros do NegativadorMovimento aceitos. [UC0681] Consultar Movimentos
	 * dos Negativadores.
	 * 
	 * @author Yara Taciane
	 * @date 22/01/2008
	 */
	public Collection pesquisarNegativadorMovimentoRegistroAceito(NegativadorMovimentoHelper negativadorMovimentoHelper,
					Integer numeroPagina) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		Collection retorno = new ArrayList();
		try{

			String hql = " select nmrg " + " from gcom.cobranca.NegativadorMovimentoReg nmrg "
							+ " inner join fetch nmrg.negativadorRegistroTipo as nrtp "
							+ " inner join fetch nmrg.negativadorMovimento as ngmv " + " where nmrg.negativadorMovimento.id = "
							+ negativadorMovimentoHelper.getIdNegativadorMovimento();

			String restricao = "";

			if(negativadorMovimentoHelper.getIdImovel() != null){

				if(negativadorMovimentoHelper.getIdNegativador().equals(Negativador.NEGATIVADOR_SPC_SAO_PAULO)){

					Integer numeroRegistro = this.pesquisarNumeroRegistro(negativadorMovimentoHelper.getIdNegativadorMovimento(),
									negativadorMovimentoHelper.getIdImovel());

					if(numeroRegistro != null){

						Integer numeroRegistroAnterior = numeroRegistro - 1;

						restricao = restricao + " and nmrg.numeroRegistro in (" + numeroRegistro + "," + numeroRegistroAnterior + " )";
					}
				}else{
					restricao = restricao + " and nmrg.imovel.id = " + negativadorMovimentoHelper.getIdImovel();
				}
			}

			if(negativadorMovimentoHelper.getCodigoMovimento() != -1){
				restricao = restricao + " and ngmv.codigoMovimento = " + negativadorMovimentoHelper.getCodigoMovimento();
			}

			if(negativadorMovimentoHelper.getNumeroSequencialArquivo() != null
							&& !"".equals(negativadorMovimentoHelper.getNumeroSequencialArquivo())){
				restricao = restricao + " and ngmv.numeroSequencialEnvio = " + negativadorMovimentoHelper.getNumeroSequencialArquivo();
			}

			if((negativadorMovimentoHelper.getDataProcessamentoInicial() != null && !negativadorMovimentoHelper
							.getDataProcessamentoInicial().equals(""))
							&& (negativadorMovimentoHelper.getDataProcessamentoFinal() != null && !negativadorMovimentoHelper
											.getDataProcessamentoFinal().equals(""))){
				restricao = restricao + " and ngmv.dataProcessamentoEnvio between '"
								+ negativadorMovimentoHelper.getDataProcessamentoInicial() + "' and '"
								+ negativadorMovimentoHelper.getDataProcessamentoFinal() + "' ";
			}

			if(negativadorMovimentoHelper.getIndicadorMovimento() == ConstantesSistema.COM_RETORNO){

				restricao = restricao + " and ngmv.dataProcessamentoRetorno is not null ";

			}else if(negativadorMovimentoHelper.getIndicadorMovimento() == ConstantesSistema.SEM_RETORNO){

				restricao = restricao + " and ngmv.dataProcessamentoRetorno is null";
			}

			if(negativadorMovimentoHelper.getIndicadorRegistro() == ConstantesSistema.ACEITO){

				restricao = restricao + "  and nmrg.indicadorAceito = " + ConstantesSistema.ACEITO;

			}else if(negativadorMovimentoHelper.getIndicadorRegistro() == ConstantesSistema.NAO_ACEITO){

				restricao = restricao + " and nmrg.indicadorAceito = " + ConstantesSistema.NAO_ACEITO;

				if(negativadorMovimentoHelper.getIndicadorCorrigido() == ConstantesSistema.CORRIGIDO){

					restricao = restricao + " and nmrg.indicadorCorrecao = " + ConstantesSistema.CORRIGIDO;

				}else if(negativadorMovimentoHelper.getIndicadorCorrigido() == ConstantesSistema.NAO_CORRIGIDO){

					restricao = restricao + " and nmrg.indicadorCorrecao = " + ConstantesSistema.NAO_CORRIGIDO;

				}else{
					restricao = restricao + " and 1=1  ";
				}
			}

			String orderBy = " order by nmrg.numeroRegistro, nrtp.id ";
			Query query = session.createQuery(hql + restricao + orderBy);

			retorno = (List) query.setFirstResult(10 * numeroPagina).setMaxResults(10).list();

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
	 * Apresenta todos os registros do NegativadorMovimento aceitos. [UC0681] Consultar Movimentos
	 * dos Negativadores.
	 * 
	 * @author Yara Taciane
	 * @date 22/01/2008
	 */
	private Integer pesquisarNumeroRegistro(Integer idNegativadorMovimento, Integer idImovel) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		Integer retorno = null;
		try{

			String consulta = " select nmrg.nmrg_nnregistro as numeroRegistro " + " from negativador_movimento_reg nmrg"
							+ " inner join negativador_movimento ngmv  on nmrg.ngmv_id = ngmv.ngmv_id "
							+ " inner join negativador negt on ngmv.negt_id = negt.negt_id "
							+ " inner join cliente clie on negt.clie_id = clie.clie_id "
							+ " where   nmrg.ngmv_id  = :idNegativadorMovimento" + " and nmrg.nmrg_nnregistro in "
							+ "(select nmrg.nmrg_nnregistro  as registroAnterior " + " from negativador_movimento_reg nmrg "
							+ "  where  nmrg.imov_id =:idImovel and nmrg.ngmv_id  = :idNegativadorMovimento )  ";

			retorno = (Integer) session.createSQLQuery(consulta).addScalar("numeroRegistro", Hibernate.INTEGER)
							.setInteger("idNegativadorMovimento", idNegativadorMovimento).setInteger("idImovel", idImovel).setMaxResults(1)
							.uniqueResult();

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
	 * [UC0694] - Gerar Relatório de Negativações Excluídas
	 * 
	 * @author Yara T. Souza
	 * @date 16/01/2009
	 */
	public List pesquisarNegativadorMovimentoRegItens(Integer idNegativadorMovimentoReg) throws ErroRepositorioException{

		List retorno = new ArrayList();
		Session session = HibernateUtil.getSession();

		try{
			String hql = " select nmri" + " from gcom.cobranca.NegativadorMovimentoRegItem nmri" + " where "
							+ " nmri.negativadorMovimentoReg.id = " + idNegativadorMovimentoReg;

			retorno = (List) session.createQuery(hql).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * Pesquisa a Data da Exclusão da Negativação
	 * 
	 * @author Yara Taciane
	 * @date 9/05/2008
	 * @param
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarNegativadorResultadoSimulacao(Integer idNegativacaoComando) throws ErroRepositorioException{

		Collection retorno = new ArrayList();
		Session session = HibernateUtil.getSession();
		try{

			String hql = " select ngsm " + " from gcom.cobranca.NegativadorResultadoSimulacao as ngsm "
							+ " left join fetch ngsm.negativacaoComando as ngcm " + " where "
							+ " ngsm.negativacaoComando.id = :idNegativacaoComando  " + " order by ngsm.valorDebito desc ";

			Query query = session.createQuery(hql).setInteger("idNegativacaoComando", idNegativacaoComando);
			retorno = (List) query.list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate verificaExistenciaNegativacao");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * Conta a quantidade de Clientes Negativados
	 * [UC0693] Gerar Relatório Acompanhamaneto de Clientes Negativados
	 * 
	 * @author Yara Taciane,Vivianne Sousa
	 * @date 17/03/2008,26/08/2009
	 */
	public Collection pesquisarRelatorioAcompanhamentoClientesNegativador(DadosConsultaNegativacaoHelper helper)
					throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		List retorno = null;
		Collection colecaoHelper = new ArrayList();

		SQLQuery querySQL = null;

		boolean compararDatas = false;

		try{

			String sql = " select "
							+ " nmrg.nmrg_id as idNegativadorMovReg, "// 0
							+ " nmrg.nmrg_icaceito as indicadorAceito, "// 1
							+ " nmrg.nmrg_nncpf as cpf, "// 2
							+ " nmrg.nmrg_nncnpj as cnpj, "// 3
							+ " nmrg.nmrg_vldebito as valorDebitoReg, "// 4
							+ " clieNmrg.clie_nmcliente as nomeClienteReg, "// 5
							+ " loca.loca_id as idLocalidade, "// 6
							+ " loca.loca_nmlocalidade as nomeLocalidade, "// 7
							+ " ngmv.ngmv_id as idNegativadorMov, "// 8
							+ " ngmv.ngmv_dtprocessamentoenvio as dataProcEnvio, "// 9
							+ " negt.negt_id as idNegativador, "// 10
							+ " clie.clie_nmcliente as nomeClienteNegativador, "// 11
							+ " ngim.ngim_icexcluido as indicadorExcluido, "// 12
							+ " nmrg.cbst_id as cobrancaSituacaoReg, "// 13
							+ " cbst.cbst_dscobrancasituacao as descCobrancaSituacaoReg, "// 14
							+ " nmrg.imov_id as idImovel, "// 15
							+ " nmrg.nmrg_nncontrato as numeroContrato "// 16
							+ " from negativador_movimento_reg nmrg "
							+ " inner join negativador_movimento ngmv on nmrg.ngmv_id=ngmv.ngmv_id "
							// +
							// " inner join negativacao_comando ngcm on ngmv.ngcm_id=ngcm.ngcm_id  "
							+ " inner join negativacao_imoveis ngim on ngmv.cacm_id=ngim.cacm_id and nmrg.imov_id=ngim.imov_id "
							+ " inner join negativador negt on ngmv.negt_id=negt.negt_id "
							+ " inner join cliente clie on negt.clie_id=clie.clie_id "
							+ " inner join quadra qdra on nmrg.qdra_id=qdra.qdra_id "
							+ " inner join rota rota on qdra.rota_id=rota.rota_id "
							+ " inner join localidade loca on nmrg.loca_id=loca.loca_id "
							+ " inner join cliente clieNmrg on nmrg.clie_id=clieNmrg.clie_id "
							+ " inner join cliente_tipo cltp on clieNmrg.cltp_id=cltp.cltp_id "
							+ " inner join esfera_poder epod on cltp.epod_id=epod.epod_id "
							+ " inner join cobranca_debito_situacao cdst on nmrg.cdst_id=cdst.cdst_id "
							// + " inner join cadastro.imovel imov on ngim.imov_id=imov.imov_id "
							+ " left join cobranca_situacao cbst on nmrg.cbst_id=cbst.cbst_id ";

			String restricao = " where nmrg.imov_id is not null and ngmv.ngmv_cdmovimento=1 ";

			if(helper.getIdNegativador() != null && helper.getIdNegativador() > 0){
				restricao = restricao + " and negt.negt_id = " + helper.getIdNegativador();
			}
			if(helper.getPeriodoEnvioNegativacaoInicio() != null && helper.getPeriodoEnvioNegativacaoFim() != null){
				// restricao = restricao + " and ngmv.ngmv_dtprocessamentoenvio between ' " +
				// helper.getPeriodoEnvioNegativacaoInicio() + " ' and  ' " +
				// helper.getPeriodoEnvioNegativacaoFim() + " ' ";

				restricao = restricao + " and ngmv.ngmv_dtprocessamentoenvio between :periodoInicial and :periodoFinal ";
				compararDatas = true;
			}
			if(helper.getIdNegativacaoComando() != null && helper.getIdNegativacaoComando() > 0){
				restricao = restricao + " and ngcm.ngcm_id = " + helper.getIdNegativacaoComando();
			}
			if(helper.getIdQuadra() != null && helper.getIdQuadra() > 0){
				restricao = restricao + " and nmrg.qdra_id = " + helper.getIdQuadra();
			}

			if(helper.getColecaoCobrancaGrupo() != null && !helper.getColecaoCobrancaGrupo().isEmpty()){

				boolean consulta = true;

				if(helper.getColecaoCobrancaGrupo().size() == 1){
					Iterator it = helper.getColecaoCobrancaGrupo().iterator();
					while(it.hasNext()){
						CobrancaGrupo obj = (CobrancaGrupo) it.next();
						if(obj != null && obj.getId() == -1){
							consulta = false;
						}
					}
				}

				if(consulta){

					Iterator iterator = helper.getColecaoCobrancaGrupo().iterator();
					CobrancaGrupo cobrancaGrupo = null;

					restricao = restricao + " and rota.cbgr_id in (";
					while(iterator.hasNext()){
						cobrancaGrupo = (CobrancaGrupo) iterator.next();
						restricao = restricao + cobrancaGrupo.getId() + ",";
					}
					restricao = Util.removerUltimosCaracteres(restricao, 1);
					restricao = restricao + ") ";

				}
			}

			if(helper.getColecaoGerenciaRegional() != null && !helper.getColecaoGerenciaRegional().isEmpty()){

				boolean consulta = true;

				if(helper.getColecaoGerenciaRegional().size() == 1){
					Iterator it = helper.getColecaoGerenciaRegional().iterator();
					while(it.hasNext()){
						GerenciaRegional gerReg = (GerenciaRegional) it.next();
						if(gerReg != null && gerReg.getId() == -1){
							consulta = false;
						}
					}
				}

				if(consulta){
					Iterator iterator = helper.getColecaoGerenciaRegional().iterator();
					GerenciaRegional gerenciaRegional = null;

					restricao = restricao + " and loca.greg_id in (";
					while(iterator.hasNext()){
						gerenciaRegional = (GerenciaRegional) iterator.next();
						restricao = restricao + gerenciaRegional.getId() + ",";
					}
					restricao = Util.removerUltimosCaracteres(restricao, 1);
					restricao = restricao + ") ";
				}

			}

			if(helper.getColecaoUnidadeNegocio() != null && !helper.getColecaoUnidadeNegocio().isEmpty()){

				boolean consulta = true;

				if(helper.getColecaoUnidadeNegocio().size() == 1){
					Iterator it = helper.getColecaoUnidadeNegocio().iterator();
					while(it.hasNext()){
						UnidadeNegocio obj = (UnidadeNegocio) it.next();
						if(obj != null && obj.getId() == -1){
							consulta = false;
						}
					}
				}

				if(consulta){

					Iterator iterator = helper.getColecaoUnidadeNegocio().iterator();
					UnidadeNegocio unidadeNegocio = null;

					restricao = restricao + " and loca.uneg_id in (";
					while(iterator.hasNext()){
						unidadeNegocio = (UnidadeNegocio) iterator.next();
						restricao = restricao + unidadeNegocio.getId() + ",";
					}
					restricao = Util.removerUltimosCaracteres(restricao, 1);
					restricao = restricao + ") ";

				}
			}

			if(helper.getColecaoImovelPerfil() != null && !helper.getColecaoImovelPerfil().isEmpty()){

				boolean consulta = true;

				if(helper.getColecaoImovelPerfil().size() == 1){
					Iterator it = helper.getColecaoImovelPerfil().iterator();
					while(it.hasNext()){
						ImovelPerfil obj = (ImovelPerfil) it.next();
						if(obj != null && obj.getId() == -1){
							consulta = false;
						}

					}
				}

				if(consulta){

					Iterator iterator = helper.getColecaoImovelPerfil().iterator();
					ImovelPerfil imovelPerfil = null;
					restricao = restricao + " and nmrg.iper_id in (";
					while(iterator.hasNext()){
						imovelPerfil = (ImovelPerfil) iterator.next();
						restricao = restricao + imovelPerfil.getId() + ",";
					}
					restricao = Util.removerUltimosCaracteres(restricao, 1);
					restricao = restricao + ") ";

				}
			}

			if(helper.getIdEloPolo() != null && helper.getIdEloPolo() > 0){
				restricao = restricao + " and loca.loca_cdelo = " + helper.getIdEloPolo();
			}
			if(helper.getIdLocalidade() != null && helper.getIdLocalidade() > 0){
				restricao = restricao + " and nmrg.loca_id = " + helper.getIdLocalidade();
			}
			if(helper.getIdSetorComercial() != null && helper.getIdSetorComercial() > 0){
				restricao = restricao + " and nmrg.nmrg_cdsetorcomercial = " + helper.getIdSetorComercial();
			}

			if(helper.getColecaoCategoria() != null && !helper.getColecaoCategoria().isEmpty()){

				boolean consulta = true;

				if(helper.getColecaoCategoria().size() == 1){
					Iterator it = helper.getColecaoCategoria().iterator();
					while(it.hasNext()){
						Categoria obj = (Categoria) it.next();
						if(obj != null && obj.getId() == -1){
							consulta = false;
						}
					}
				}

				if(consulta){

					Iterator iterator = helper.getColecaoCategoria().iterator();
					Categoria categoria = null;

					restricao = restricao + " and nmrg.categoria.id in (";
					while(iterator.hasNext()){
						categoria = (Categoria) iterator.next();
						restricao = restricao + categoria.getId() + ",";
					}
					restricao = Util.removerUltimosCaracteres(restricao, 1);
					restricao = restricao + ") ";

				}
			}

			if(helper.getColecaoClienteTipo() != null && !helper.getColecaoClienteTipo().isEmpty()){
				boolean consulta = true;

				if(helper.getColecaoClienteTipo().size() == 1){
					Iterator it = helper.getColecaoClienteTipo().iterator();
					while(it.hasNext()){
						ClienteTipo obj = (ClienteTipo) it.next();
						if(obj != null && obj.getId() == -1){
							consulta = false;
						}
					}
				}

				if(consulta){
					Iterator iterator = helper.getColecaoClienteTipo().iterator();
					ClienteTipo clienteTipo = null;

					restricao = restricao + " and clieNmrg.cltp_id in (";
					while(iterator.hasNext()){
						clienteTipo = (ClienteTipo) iterator.next();
						restricao = restricao + clienteTipo.getId() + ",";
					}
					restricao = Util.removerUltimosCaracteres(restricao, 1);
					restricao = restricao + ") ";

				}
			}

			if(helper.getColecaoEsferaPoder() != null && !helper.getColecaoEsferaPoder().isEmpty()){
				boolean consulta = true;

				if(helper.getColecaoEsferaPoder().size() == 1){
					Iterator it = helper.getColecaoEsferaPoder().iterator();
					while(it.hasNext()){
						EsferaPoder obj = (EsferaPoder) it.next();
						if(obj != null && obj.getId() == -1){
							consulta = false;
						}
					}
				}

				if(consulta){

					Iterator iterator = helper.getColecaoEsferaPoder().iterator();
					EsferaPoder esferaPoder = null;

					restricao = restricao + " and cltp.epod_id  in (";
					while(iterator.hasNext()){
						esferaPoder = (EsferaPoder) iterator.next();
						restricao = restricao + esferaPoder.getId() + ",";
					}
					restricao = Util.removerUltimosCaracteres(restricao, 1);
					restricao = restricao + ") ";

				}
			}

			restricao = restricao
							+ " order by nmrg.nmrg_icaceito,ngim.ngim_icexcluido,nmrg.cbst_id,ngmv.ngmv_dtprocessamentoenvio,loca.loca_id";

			sql = sql + restricao;

			querySQL = session.createSQLQuery(sql);

			if(compararDatas){
				Date periodoInicio = Util.formatarDataSemHora(helper.getPeriodoEnvioNegativacaoInicio());
				Date periodoFinal = Util.adaptarDataFinalComparacaoBetween(helper.getPeriodoEnvioNegativacaoFim());

				querySQL.setDate("periodoInicial", periodoInicio);
				querySQL.setDate("periodoFinal", periodoFinal);
			}
			// query = Util.configuraParamentrosEmQuery(query, parameters);

			// retorno = (Integer) query.uniqueResult();

			retorno = (List) querySQL.addScalar("idNegativadorMovReg", Hibernate.INTEGER).addScalar("indicadorAceito", Hibernate.SHORT)
							.addScalar("cpf", Hibernate.STRING).addScalar("cnpj", Hibernate.STRING)
							.addScalar("valorDebitoReg", Hibernate.BIG_DECIMAL).addScalar("nomeClienteReg", Hibernate.STRING)
							.addScalar("idLocalidade", Hibernate.INTEGER).addScalar("nomeLocalidade", Hibernate.STRING)
							.addScalar("idNegativadorMov", Hibernate.INTEGER).addScalar("dataProcEnvio", Hibernate.DATE)
							.addScalar("idNegativador", Hibernate.INTEGER).addScalar("nomeClienteNegativador", Hibernate.STRING)
							.addScalar("indicadorExcluido", Hibernate.SHORT).addScalar("cobrancaSituacaoReg", Hibernate.INTEGER)
							.addScalar("descCobrancaSituacaoReg", Hibernate.STRING).addScalar("idImovel", Hibernate.INTEGER)
							.addScalar("numeroContrato", Hibernate.STRING).list();

			if(retorno != null){
				colecaoHelper = new ArrayList();
				Iterator iter = retorno.iterator();

				NegativadorMovimentoReg nmrg = null;
				Cliente clienteNmrg = null;
				Localidade loca = null;
				NegativadorMovimento ngmv = null;
				Negativador negt = null;
				Cliente clienteNegt = null;
				Short indicadorExcluidoNgim = null;
				CobrancaSituacao cobrancaSituacao = null;
				Imovel imov = null;
				RelatorioAcompanhamentoClientesNegativadosHelper helperRetorno = null;

				while(iter.hasNext()){
					Object[] objeto = (Object[]) iter.next();

					nmrg = new NegativadorMovimentoReg();

					nmrg.setId((Integer) objeto[0]);

					if(objeto[1] != null){
						nmrg.setIndicadorAceito((Short) objeto[1]);
					}

					if(objeto[2] != null){
						nmrg.setNumeroCpf((String) objeto[2]);
					}

					if(objeto[3] != null){
						nmrg.setNumeroCnpj((String) objeto[3]);
					}

					if(objeto[4] != null){
						nmrg.setValorDebito((BigDecimal) objeto[4]);
					}

					if(objeto[5] != null){
						clienteNmrg = new Cliente();
						clienteNmrg.setNome((String) objeto[5]);
						nmrg.setCliente(clienteNmrg);
					}

					if(objeto[6] != null){
						loca = new Localidade();
						loca.setId((Integer) objeto[6]);
						if(objeto[7] != null){
							loca.setDescricao((String) objeto[7]);
						}
						nmrg.setLocalidade(loca);
					}

					if(objeto[8] != null){
						ngmv = new NegativadorMovimento();
						ngmv.setId((Integer) objeto[8]);
						if(objeto[9] != null){
							ngmv.setDataProcessamentoEnvio((Date) objeto[9]);
						}
						nmrg.setNegativadorMovimento(ngmv);
					}

					if(objeto[10] != null){
						negt = new Negativador();
						negt.setId((Integer) objeto[10]);

						if(objeto[11] != null){
							clienteNegt = new Cliente();
							clienteNegt.setNome((String) objeto[11]);
							negt.setCliente(clienteNegt);
						}
					}

					if(objeto[12] != null){
						indicadorExcluidoNgim = (Short) objeto[12];
					}
					if(objeto[13] != null){
						cobrancaSituacao = new CobrancaSituacao();
						cobrancaSituacao.setId((Integer) objeto[13]);
						if(objeto[14] != null){
							cobrancaSituacao.setDescricao((String) objeto[14]);
						}
						nmrg.setCobrancaSituacao(cobrancaSituacao);
					}
					if(objeto[15] != null){
						imov = new Imovel();
						imov.setId((Integer) objeto[15]);
						nmrg.setImovel(imov);
					}

					if(objeto[16] != null){
						String numeroContrato = (String) objeto[16];
						nmrg.setNumeroContrato(numeroContrato.trim());
					}

					helperRetorno = new RelatorioAcompanhamentoClientesNegativadosHelper(indicadorExcluidoNgim, nmrg);
					colecaoHelper.add(helperRetorno);
				}
			}
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return colecaoHelper;
	}

	/**
	 * Conta a quantidade de Clientes Negativados
	 * [UC0693] Gerar Relatório Acompanhamaneto de Clientes Negativados
	 * 
	 * @author Yara Taciane
	 * @date 17/03/2008
	 */
	public Integer pesquisarRelatorioAcompanhamentoClientesNegativadorCount(DadosConsultaNegativacaoHelper helper)
					throws ErroRepositorioException{

		Integer retorno;
		Session session = HibernateUtil.getSession();
		Query query = null;

		Map parameters = new HashMap();

		String restricao = "";
		try{
			String hql = " select count(*) " + " from gcom.cobranca.NegativadorMovimentoReg nmrg " + " where  1=1 ";

			restricao = restricao + " and nmrg.imovel.id is not null and nmrg.negativadorMovimento.codigoMovimento = 1 ";

			if(helper.getIdNegativador() != null && helper.getIdNegativador() > 0){
				restricao = restricao + " and nmrg.negativadorMovimento.negativador.id = " + helper.getIdNegativador();
			}
			if(helper.getPeriodoEnvioNegativacaoInicio() != null && helper.getPeriodoEnvioNegativacaoFim() != null){
				// restricao = restricao +
				// " and nmrg.negativadorMovimento.dataProcessamentoEnvio between  '" +
				// helper.getPeriodoEnvioNegativacaoInicio() + "' and '" +
				// helper.getPeriodoEnvioNegativacaoFim() + "' ";

				Date periodoInicio = Util.formatarDataSemHora(helper.getPeriodoEnvioNegativacaoInicio());
				Date periodoFinal = Util.adaptarDataFinalComparacaoBetween(helper.getPeriodoEnvioNegativacaoFim());

				restricao = restricao + " and nmrg.negativadorMovimento.dataProcessamentoEnvio between :periodoInicial and :periodoFinal ";

				parameters.put("periodoInicial", periodoInicio);
				parameters.put("periodoFinal", periodoFinal);
			}
			if(helper.getIdNegativacaoComando() != null && helper.getIdNegativacaoComando() > 0){
				restricao = restricao + " and nmrg.negativadorMovimento.negativacaoComando.id = " + helper.getIdNegativacaoComando();
			}
			if(helper.getIdQuadra() != null && helper.getIdQuadra() > 0){
				restricao = restricao + " and nmrg.quadra.id = " + helper.getIdQuadra();
			}

			if(helper.getColecaoCobrancaGrupo() != null && !helper.getColecaoCobrancaGrupo().isEmpty()){

				boolean consulta = true;

				if(helper.getColecaoCobrancaGrupo().size() == 1){
					Iterator it = helper.getColecaoCobrancaGrupo().iterator();
					while(it.hasNext()){
						CobrancaGrupo obj = (CobrancaGrupo) it.next();
						if(obj != null && obj.getId() == -1){
							consulta = false;
						}
					}
				}

				if(consulta){

					Iterator iterator = helper.getColecaoCobrancaGrupo().iterator();
					CobrancaGrupo cobrancaGrupo = null;

					restricao = restricao + " and nmrg.imovel.rota.cobrancaGrupo.id in (";
					while(iterator.hasNext()){
						cobrancaGrupo = (CobrancaGrupo) iterator.next();
						restricao = restricao + cobrancaGrupo.getId() + ",";
					}
					restricao = Util.removerUltimosCaracteres(restricao, 1);
					restricao = restricao + ") ";

				}
			}

			if(helper.getColecaoGerenciaRegional() != null && !helper.getColecaoGerenciaRegional().isEmpty()){

				boolean consulta = true;

				if(helper.getColecaoGerenciaRegional().size() == 1){
					Iterator it = helper.getColecaoGerenciaRegional().iterator();
					while(it.hasNext()){
						GerenciaRegional obj = (GerenciaRegional) it.next();
						if(obj != null && obj.getId() == -1){
							consulta = false;
						}
					}
				}

				if(consulta){
					Iterator iterator = helper.getColecaoGerenciaRegional().iterator();
					GerenciaRegional gerenciaRegional = null;

					restricao = restricao + " and nmrg.localidade.gerenciaRegional.id in (";
					while(iterator.hasNext()){
						gerenciaRegional = (GerenciaRegional) iterator.next();
						restricao = restricao + gerenciaRegional.getId() + ",";
					}
					restricao = Util.removerUltimosCaracteres(restricao, 1);
					restricao = restricao + ") ";
				}

			}

			if(helper.getColecaoUnidadeNegocio() != null && !helper.getColecaoUnidadeNegocio().isEmpty()){

				boolean consulta = true;

				if(helper.getColecaoUnidadeNegocio().size() == 1){
					Iterator it = helper.getColecaoUnidadeNegocio().iterator();
					while(it.hasNext()){
						UnidadeNegocio obj = (UnidadeNegocio) it.next();
						if(obj != null && obj.getId() == -1){
							consulta = false;
						}
					}
				}

				if(consulta){
					Iterator iterator = helper.getColecaoUnidadeNegocio().iterator();
					UnidadeNegocio unidadeNegocio = null;

					restricao = restricao + " and nmrg.localidade.unidadeNegocio.id in (";
					while(iterator.hasNext()){
						unidadeNegocio = (UnidadeNegocio) iterator.next();
						restricao = restricao + unidadeNegocio.getId() + ",";
					}
					restricao = Util.removerUltimosCaracteres(restricao, 1);
					restricao = restricao + ") ";

				}
			}

			if(helper.getColecaoImovelPerfil() != null && !helper.getColecaoImovelPerfil().isEmpty()){

				boolean consulta = true;

				if(helper.getColecaoImovelPerfil().size() == 1){
					Iterator it = helper.getColecaoImovelPerfil().iterator();
					while(it.hasNext()){
						ImovelPerfil obj = (ImovelPerfil) it.next();
						if(obj != null && obj.getId() == -1){
							consulta = false;
						}
					}
				}

				if(consulta){

					Iterator iterator = helper.getColecaoImovelPerfil().iterator();
					ImovelPerfil imovelPerfil = null;

					restricao = restricao + " and nmrg.imovelPerfil.id in (";
					while(iterator.hasNext()){
						imovelPerfil = (ImovelPerfil) iterator.next();
						restricao = restricao + imovelPerfil.getId() + ",";
					}
					restricao = Util.removerUltimosCaracteres(restricao, 1);
					restricao = restricao + ") ";

				}
			}

			if(helper.getIdEloPolo() != null && helper.getIdEloPolo() > 0){
				restricao = restricao + " and nmrg.localidade.localidade = " + helper.getIdEloPolo();
			}
			if(helper.getIdLocalidade() != null && helper.getIdLocalidade() > 0){
				restricao = restricao + " and nmrg.localidade.id = " + helper.getIdLocalidade();
			}
			if(helper.getIdSetorComercial() != null && helper.getIdSetorComercial() > 0){
				restricao = restricao + " and nmrg.codigoSetorComercial = " + helper.getIdSetorComercial();
			}
			if(helper.getColecaoCategoria() != null && !helper.getColecaoCategoria().isEmpty()){

				boolean consulta = true;

				if(helper.getColecaoCategoria().size() == 1){
					Iterator it = helper.getColecaoCategoria().iterator();
					while(it.hasNext()){
						Categoria obj = (Categoria) it.next();
						if(obj != null && obj.getId() == -1){
							consulta = false;
						}
					}
				}

				if(consulta){

					Iterator iterator = helper.getColecaoCategoria().iterator();
					Categoria Categoria = null;

					restricao = restricao + " and nmrg.categoria.id in (";
					while(iterator.hasNext()){
						Categoria = (Categoria) iterator.next();
						restricao = restricao + Categoria.getId() + ",";
					}
					restricao = Util.removerUltimosCaracteres(restricao, 1);
					restricao = restricao + ") ";

				}

			}

			if(helper.getColecaoClienteTipo() != null && !helper.getColecaoClienteTipo().isEmpty()){

				boolean consulta = true;

				if(helper.getColecaoClienteTipo().size() == 1){
					Iterator it = helper.getColecaoClienteTipo().iterator();
					while(it.hasNext()){
						ClienteTipo obj = (ClienteTipo) it.next();
						if(obj != null && obj.getId() == -1){
							consulta = false;
						}
					}
				}

				if(consulta){

					Iterator iterator = helper.getColecaoClienteTipo().iterator();
					ClienteTipo clienteTipo = null;

					restricao = restricao + " and  nmrg.cliente.clienteTipo.id in (";
					while(iterator.hasNext()){
						clienteTipo = (ClienteTipo) iterator.next();
						restricao = restricao + clienteTipo.getId() + ",";
					}
					restricao = Util.removerUltimosCaracteres(restricao, 1);
					restricao = restricao + ") ";

				}
			}

			if(helper.getColecaoEsferaPoder() != null && !helper.getColecaoEsferaPoder().isEmpty()){

				boolean consulta = true;

				if(helper.getColecaoEsferaPoder().size() == 1){
					Iterator it = helper.getColecaoEsferaPoder().iterator();
					while(it.hasNext()){
						EsferaPoder obj = (EsferaPoder) it.next();
						if(obj != null && obj.getId() == -1){
							consulta = false;
						}
					}
				}

				if(consulta){

					Iterator iterator = helper.getColecaoEsferaPoder().iterator();
					EsferaPoder esferaPoder = null;

					restricao = restricao + "and  nmrg.cliente.clienteTipo.esferaPoder.id in (";
					while(iterator.hasNext()){
						esferaPoder = (EsferaPoder) iterator.next();
						restricao = restricao + esferaPoder.getId() + ",";
					}
					restricao = Util.removerUltimosCaracteres(restricao, 1);
					restricao = restricao + ") ";

				}
			}

			query = session.createQuery(hql + restricao);

			query = Util.configuraParamentrosEmQuery(query, parameters);

			retorno = ((Number) query.uniqueResult()).intValue();

			// retorno = (Integer) session.createQuery(hql + restricao).uniqueResult();

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
	 * Conta a quantidade de Clientes Negativados
	 * [UC0693] Gerar Relatório Acompanhamaneto de Clientes Negativados
	 * 
	 * @author Yara Taciane
	 * @date 17/03/2008
	 */
	public Collection pesquisarRelatorioNegativacoesExcluidas(DadosConsultaNegativacaoHelper helper) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		Collection retorno = new ArrayList();

		String restricao = "";
		try{
			String hql = " select nmrg " + " from gcom.cobranca.NegativadorMovimentoReg nmrg "
							+ " left join fetch nmrg.negativadorMovimento as ngmv "
							+ " left join fetch nmrg.negativadorExclusaoMotivo as nemt "
							+ " left join fetch ngmv.negativador as negt "
							+ " left join fetch negt.cliente as clie " + " left join fetch nmrg.quadra as quad "
							+ " left join fetch quad.rota as rota " + " left join fetch rota.cobrancaGrupo as cbgr "
							+ " left join fetch nmrg.localidade as loca " + " left join fetch loca.gerenciaRegional as greg "
							+ " left join fetch loca.unidadeNegocio as uneg " + " left join fetch loca.localidade as lelo "
							+ " left join fetch quad.setorComercial as setc " + " left join fetch nmrg.imovelPerfil as ip "
							+ " left join fetch nmrg.categoria as catg " + " left join fetch nmrg.cliente as clie "
							+ " left join fetch clie.clienteTipo as cltp " + " left join fetch cltp.esferaPoder as epod "
							+ " left join fetch nmrg.cobrancaDebitoSituacao as cbds " + " left join fetch nmrg.imovel as imov "
							+ " where nmrg.codigoExclusaoTipo is not null " + " and nmrg.imovel.id is not null ";

			if(helper.getIdNegativador() != null && helper.getIdNegativador() > 0){
				restricao = restricao + " and ngmv.negativador.id = :idNegativador ";
			}

			if(helper.getPeriodoEnvioNegativacaoInicio() != null && helper.getPeriodoEnvioNegativacaoFim() != null){
				restricao = restricao + " and nmrg.negativadorMovimento.dataProcessamentoEnvio between :periodoEnvioNegativacaoInicio "
								+ " and :periodoEnvioNegativacaoFim";
			}

			if(helper.getPeriodoExclusaoNegativacaoInicio() != null && helper.getPeriodoExclusaoNegativacaoFim() != null){
				restricao = restricao + " and ngmv.cobrancaAcaoAtividadeComando.id in "
								+ "(select cobrancaAcaoAtividadeComando.id from gcom.cobranca.NegativacaoImovei"
								+ " where dataExclusao  between  :periodoExclusaoNegativacaoInicio and :periodoExclusaoNegativacaoFim) ";
			}

			if(helper.getIdNegativadorExclusaoMotivo() != null && helper.getIdNegativadorExclusaoMotivo() > 0){
				restricao = restricao + " and nmrg.negativadorExclusaoMotivo.id = :idNegativadorExclusaoMotivo ";
			}

			if(helper.getIdQuadra() != null && helper.getIdQuadra() > 0){
				restricao = restricao + " and nmrg.quadra.id = :idQuadra";
			}

			if(helper.getColecaoCobrancaGrupo() != null && !helper.getColecaoCobrancaGrupo().isEmpty()){

				boolean consulta = true;

				if(helper.getColecaoCobrancaGrupo().size() == 1){
					Iterator it = helper.getColecaoCobrancaGrupo().iterator();
					while(it.hasNext()){
						CobrancaGrupo obj = (CobrancaGrupo) it.next();
						if(obj != null && obj.getId() == -1){
							consulta = false;
						}
					}
				}

				if(consulta){

					Iterator iterator = helper.getColecaoCobrancaGrupo().iterator();
					CobrancaGrupo cobrancaGrupo = null;

					restricao = restricao + " and nmrg.imovel.rota.cobrancaGrupo.id in (";
					while(iterator.hasNext()){
						cobrancaGrupo = (CobrancaGrupo) iterator.next();
						restricao = restricao + cobrancaGrupo.getId() + ",";
					}
					restricao = Util.removerUltimosCaracteres(restricao, 1);
					restricao = restricao + ") ";

				}
			}

			if(helper.getColecaoGerenciaRegional() != null && !helper.getColecaoGerenciaRegional().isEmpty()){

				boolean consulta = true;

				if(helper.getColecaoGerenciaRegional().size() == 1){
					Iterator it = helper.getColecaoGerenciaRegional().iterator();
					while(it.hasNext()){
						GerenciaRegional gerReg = (GerenciaRegional) it.next();
						if(gerReg != null && gerReg.getId() == -1){
							consulta = false;
						}
					}
				}

				if(consulta){
					Iterator iterator = helper.getColecaoGerenciaRegional().iterator();
					GerenciaRegional gerenciaRegional = null;

					restricao = restricao + " and loca.gerenciaRegional.id in (";
					while(iterator.hasNext()){
						gerenciaRegional = (GerenciaRegional) iterator.next();
						restricao = restricao + gerenciaRegional.getId() + ",";
					}
					restricao = Util.removerUltimosCaracteres(restricao, 1);
					restricao = restricao + ") ";
				}
			}
			if(helper.getColecaoUnidadeNegocio() != null && !helper.getColecaoUnidadeNegocio().isEmpty()){

				boolean consulta = true;

				if(helper.getColecaoUnidadeNegocio().size() == 1){
					Iterator it = helper.getColecaoUnidadeNegocio().iterator();
					while(it.hasNext()){
						UnidadeNegocio obj = (UnidadeNegocio) it.next();
						if(obj != null && obj.getId() == -1){
							consulta = false;
						}
					}
				}

				if(consulta){

					Iterator iterator = helper.getColecaoUnidadeNegocio().iterator();
					UnidadeNegocio unidadeNegocio = null;

					restricao = restricao + " and loca.unidadeNegocio.id in (";
					while(iterator.hasNext()){
						unidadeNegocio = (UnidadeNegocio) iterator.next();
						restricao = restricao + unidadeNegocio.getId() + ",";
					}
					restricao = Util.removerUltimosCaracteres(restricao, 1);
					restricao = restricao + ") ";

				}
			}

			if(helper.getIdEloPolo() != null && helper.getIdEloPolo() > 0){
				restricao = restricao + " and loca.localidade = :idEloPolo ";
			}
			if(helper.getIdLocalidade() != null && helper.getIdLocalidade() > 0){
				restricao = restricao + " and nmrg.localidade.id = :idLocalidade ";
			}
			if(helper.getIdSetorComercial() != null && helper.getIdSetorComercial() > 0){
				restricao = restricao + " and nmrg.codigoSetorComercial = :idSetorComercial ";
			}
			if(helper.getColecaoCategoria() != null && !helper.getColecaoCategoria().isEmpty()){

				boolean consulta = true;

				if(helper.getColecaoCategoria().size() == 1){
					Iterator it = helper.getColecaoCategoria().iterator();
					while(it.hasNext()){
						Categoria obj = (Categoria) it.next();
						if(obj != null && obj.getId() == -1){
							consulta = false;
						}
					}
				}

				if(consulta){

					Iterator iterator = helper.getColecaoCategoria().iterator();
					Categoria categoria = null;

					restricao = restricao + " and nmrg.categoria.id in (";
					while(iterator.hasNext()){
						categoria = (Categoria) iterator.next();
						restricao = restricao + categoria.getId() + ",";
					}
					restricao = Util.removerUltimosCaracteres(restricao, 1);
					restricao = restricao + ") ";

				}
			}

			if(helper.getColecaoClienteTipo() != null && !helper.getColecaoClienteTipo().isEmpty()){
				boolean consulta = true;

				if(helper.getColecaoClienteTipo().size() == 1){
					Iterator it = helper.getColecaoClienteTipo().iterator();
					while(it.hasNext()){
						ClienteTipo obj = (ClienteTipo) it.next();
						if(obj != null && obj.getId() == -1){
							consulta = false;
						}
					}
				}

				if(consulta){
					Iterator iterator = helper.getColecaoClienteTipo().iterator();
					ClienteTipo clienteTipo = null;

					restricao = restricao + " and clie.clienteTipo.id in (";
					while(iterator.hasNext()){
						clienteTipo = (ClienteTipo) iterator.next();
						restricao = restricao + clienteTipo.getId() + ",";
					}
					restricao = Util.removerUltimosCaracteres(restricao, 1);
					restricao = restricao + ") ";

				}
			}

			if(helper.getColecaoEsferaPoder() != null && !helper.getColecaoEsferaPoder().isEmpty()){
				boolean consulta = true;

				if(helper.getColecaoEsferaPoder().size() == 1){
					Iterator it = helper.getColecaoEsferaPoder().iterator();
					while(it.hasNext()){
						EsferaPoder obj = (EsferaPoder) it.next();
						if(obj != null && obj.getId() == -1){
							consulta = false;
						}
					}
				}

				if(consulta){

					Iterator iterator = helper.getColecaoEsferaPoder().iterator();
					EsferaPoder esferaPoder = null;

					restricao = restricao + " and cltp.esferaPoder.id  in (";
					while(iterator.hasNext()){
						esferaPoder = (EsferaPoder) iterator.next();
						restricao = restricao + esferaPoder.getId() + ",";
					}
					restricao = Util.removerUltimosCaracteres(restricao, 1);
					restricao = restricao + ") ";
				}
			}
			restricao = restricao + " order by ngmv.dataProcessamentoEnvio,nmrg.localidade.id ";

			Query query = session.createQuery(hql + restricao);

			if(helper.getIdNegativador() != null && helper.getIdNegativador() > 0){
				query.setInteger("idNegativador", helper.getIdNegativador());
			}
			if(helper.getPeriodoEnvioNegativacaoInicio() != null && helper.getPeriodoEnvioNegativacaoFim() != null){
				query.setDate("periodoEnvioNegativacaoInicio", helper.getPeriodoEnvioNegativacaoInicio());
				query.setDate("periodoEnvioNegativacaoFim", helper.getPeriodoEnvioNegativacaoFim());
			}
			if(helper.getPeriodoExclusaoNegativacaoInicio() != null && helper.getPeriodoExclusaoNegativacaoFim() != null){
				query.setDate("periodoExclusaoNegativacaoInicio", helper.getPeriodoExclusaoNegativacaoInicio());
				query.setDate("periodoExclusaoNegativacaoFim", helper.getPeriodoExclusaoNegativacaoFim());
			}
			if(helper.getIdNegativadorExclusaoMotivo() != null && helper.getIdNegativadorExclusaoMotivo() > 0){
				query.setInteger("idNegativadorExclusaoMotivo", helper.getIdNegativadorExclusaoMotivo());
			}
			if(helper.getIdQuadra() != null && helper.getIdQuadra() > 0){
				query.setInteger("idQuadra", helper.getIdQuadra());
			}
			if(helper.getIdEloPolo() != null && helper.getIdEloPolo() > 0){
				query.setInteger("idEloPolo", helper.getIdEloPolo());
			}
			if(helper.getIdLocalidade() != null && helper.getIdLocalidade() > 0){
				query.setInteger("idLocalidade", helper.getIdLocalidade());
			}
			if(helper.getIdSetorComercial() != null && helper.getIdSetorComercial() > 0){
				query.setInteger("idSetorComercial", helper.getIdSetorComercial());
			}

			retorno = (List) query.list();
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * Conta a quantidade de Clientes Negativados
	 * [UC0693] Gerar Relatório Acompanhamaneto de Clientes Negativados
	 * 
	 * @author Yara Taciane
	 * @date 17/03/2008
	 */
	public Integer pesquisarRelatorioNegativacoesExcluidasCount(DadosConsultaNegativacaoHelper helper) throws ErroRepositorioException{

		Integer retorno;
		Session session = HibernateUtil.getSession();

		String restricao = "";
		try{
			String hql = " select count(*) from NegativadorMovimentoReg nmrg where nmrg.codigoExclusaoTipo is not null "
							+ " and nmrg.imovel.id is not null ";

			if(helper.getIdNegativador() != null && helper.getIdNegativador() > 0){
				restricao = restricao + " and nmrg.negativadorMovimento.negativador.id = :idNegativador ";
			}

			if(helper.getPeriodoEnvioNegativacaoInicio() != null && helper.getPeriodoEnvioNegativacaoFim() != null){
				restricao = restricao
								+ " and nmrg.negativadorMovimento.dataProcessamentoEnvio between  :periodoEnvioNegativacaoInicio and :periodoEnvioNegativacaoFim ";
			}

			if(helper.getPeriodoExclusaoNegativacaoInicio() != null && helper.getPeriodoExclusaoNegativacaoFim() != null){
				restricao = restricao + " and nmrg.negativadorMovimento.cobrancaAcaoAtividadeComando.id in "
								+ "(select cobrancaAcaoAtividadeComando.id from gcom.cobranca.NegativacaoImovei"
								+ " where dataExclusao  between  :periodoExclusaoNegativacaoInicio and :periodoExclusaoNegativacaoFim ) ";
			}

			if(helper.getIdNegativadorExclusaoMotivo() != null && helper.getIdNegativadorExclusaoMotivo() > 0){
				restricao = restricao + " and nmrg.negativadorExclusaoMotivo.id = :idNegativadorExclusaoMotivo ";
			}

			if(helper.getIdQuadra() != null && helper.getIdQuadra() > 0){
				restricao = restricao + " and nmrg.quadra.id = :idQuadra ";
			}
			if(helper.getColecaoCobrancaGrupo() != null && !helper.getColecaoCobrancaGrupo().isEmpty()){

				boolean consulta = true;

				if(helper.getColecaoCobrancaGrupo().size() == 1){
					Iterator it = helper.getColecaoCobrancaGrupo().iterator();
					while(it.hasNext()){
						CobrancaGrupo obj = (CobrancaGrupo) it.next();
						if(obj != null && obj.getId() == -1){
							consulta = false;
						}
					}
				}

				if(consulta){

					Iterator iterator = helper.getColecaoCobrancaGrupo().iterator();
					CobrancaGrupo cobrancaGrupo = null;

					restricao = restricao + " and nmrg.imovel.rota.cobrancaGrupo.id in (";
					while(iterator.hasNext()){
						cobrancaGrupo = (CobrancaGrupo) iterator.next();
						restricao = restricao + cobrancaGrupo.getId() + ",";
					}
					restricao = Util.removerUltimosCaracteres(restricao, 1);
					restricao = restricao + ") ";

				}
			}

			if(helper.getColecaoGerenciaRegional() != null && !helper.getColecaoGerenciaRegional().isEmpty()){

				boolean consulta = true;

				if(helper.getColecaoGerenciaRegional().size() == 1){
					Iterator it = helper.getColecaoGerenciaRegional().iterator();
					while(it.hasNext()){
						GerenciaRegional obj = (GerenciaRegional) it.next();
						if(obj != null && obj.getId() == -1){
							consulta = false;
						}
					}
				}

				if(consulta){
					Iterator iterator = helper.getColecaoGerenciaRegional().iterator();
					GerenciaRegional gerenciaRegional = null;

					restricao = restricao + " and nmrg.localidade.gerenciaRegional.id in (";
					while(iterator.hasNext()){
						gerenciaRegional = (GerenciaRegional) iterator.next();
						restricao = restricao + gerenciaRegional.getId() + ",";
					}
					restricao = Util.removerUltimosCaracteres(restricao, 1);
					restricao = restricao + ") ";
				}
			}

			if(helper.getColecaoUnidadeNegocio() != null && !helper.getColecaoUnidadeNegocio().isEmpty()){

				boolean consulta = true;

				if(helper.getColecaoUnidadeNegocio().size() == 1){
					Iterator it = helper.getColecaoUnidadeNegocio().iterator();
					while(it.hasNext()){
						UnidadeNegocio obj = (UnidadeNegocio) it.next();
						if(obj != null && obj.getId() == -1){
							consulta = false;
						}
					}
				}

				if(consulta){
					Iterator iterator = helper.getColecaoUnidadeNegocio().iterator();
					UnidadeNegocio unidadeNegocio = null;

					restricao = restricao + " and nmrg.localidade.unidadeNegocio.id in (";
					while(iterator.hasNext()){
						unidadeNegocio = (UnidadeNegocio) iterator.next();
						restricao = restricao + unidadeNegocio.getId() + ",";
					}
					restricao = Util.removerUltimosCaracteres(restricao, 1);
					restricao = restricao + ") ";

				}
			}

			if(helper.getIdEloPolo() != null && helper.getIdEloPolo() > 0){
				restricao = restricao + " and nmrg.localidade.localidade = :idEloPolo ";
			}
			if(helper.getIdLocalidade() != null && helper.getIdLocalidade() > 0){
				restricao = restricao + " and nmrg.localidade.id = :idLocalidade ";
			}
			if(helper.getIdSetorComercial() != null && helper.getIdSetorComercial() > 0){
				restricao = restricao + " and nmrg.codigoSetorComercial = :idSetorComercial ";
			}
			if(helper.getColecaoCategoria() != null && !helper.getColecaoCategoria().isEmpty()){

				boolean consulta = true;

				if(helper.getColecaoCategoria().size() == 1){
					Iterator it = helper.getColecaoCategoria().iterator();
					while(it.hasNext()){
						Categoria obj = (Categoria) it.next();
						if(obj != null && obj.getId() == -1){
							consulta = false;
						}
					}
				}

				if(consulta){

					Iterator iterator = helper.getColecaoCategoria().iterator();
					Categoria Categoria = null;

					restricao = restricao + " and nmrg.categoria.id in (";
					while(iterator.hasNext()){
						Categoria = (Categoria) iterator.next();
						restricao = restricao + Categoria.getId() + ",";
					}
					restricao = Util.removerUltimosCaracteres(restricao, 1);
					restricao = restricao + ") ";

				}

			}

			if(helper.getColecaoClienteTipo() != null && !helper.getColecaoClienteTipo().isEmpty()){

				boolean consulta = true;

				if(helper.getColecaoClienteTipo().size() == 1){
					Iterator it = helper.getColecaoClienteTipo().iterator();
					while(it.hasNext()){
						ClienteTipo obj = (ClienteTipo) it.next();
						if(obj != null && obj.getId() == -1){
							consulta = false;
						}
					}
				}

				if(consulta){

					Iterator iterator = helper.getColecaoClienteTipo().iterator();
					ClienteTipo clienteTipo = null;

					restricao = restricao + " and  nmrg.cliente.clienteTipo.id in (";
					while(iterator.hasNext()){
						clienteTipo = (ClienteTipo) iterator.next();
						restricao = restricao + clienteTipo.getId() + ",";
					}
					restricao = Util.removerUltimosCaracteres(restricao, 1);
					restricao = restricao + ") ";

				}
			}

			if(helper.getColecaoEsferaPoder() != null && !helper.getColecaoEsferaPoder().isEmpty()){

				boolean consulta = true;

				if(helper.getColecaoEsferaPoder().size() == 1){
					Iterator it = helper.getColecaoEsferaPoder().iterator();
					while(it.hasNext()){
						EsferaPoder obj = (EsferaPoder) it.next();
						if(obj != null && obj.getId() == -1){
							consulta = false;
						}
					}
				}
				if(consulta){

					Iterator iterator = helper.getColecaoEsferaPoder().iterator();
					EsferaPoder esferaPoder = null;

					restricao = restricao + "and  nmrg.cliente.clienteTipo.esferaPoder.id in (";
					while(iterator.hasNext()){
						esferaPoder = (EsferaPoder) iterator.next();
						restricao = restricao + esferaPoder.getId() + ",";
					}
					restricao = Util.removerUltimosCaracteres(restricao, 1);
					restricao = restricao + ") ";
				}
			}

			Query query = session.createQuery(hql + restricao);

			if(helper.getIdNegativador() != null && helper.getIdNegativador() > 0){
				query.setInteger("idNegativador", helper.getIdNegativador());
			}

			if(helper.getPeriodoEnvioNegativacaoInicio() != null && helper.getPeriodoEnvioNegativacaoFim() != null){
				query.setDate("periodoEnvioNegativacaoInicio", helper.getPeriodoEnvioNegativacaoInicio());
				query.setDate("periodoEnvioNegativacaoFim", helper.getPeriodoEnvioNegativacaoFim());
			}

			if(helper.getPeriodoExclusaoNegativacaoInicio() != null && helper.getPeriodoExclusaoNegativacaoFim() != null){
				query.setDate("periodoExclusaoNegativacaoInicio", helper.getPeriodoExclusaoNegativacaoInicio());
				query.setDate("periodoExclusaoNegativacaoFim", helper.getPeriodoExclusaoNegativacaoFim());
			}

			if(helper.getIdNegativadorExclusaoMotivo() != null && helper.getIdNegativadorExclusaoMotivo() > 0){
				query.setInteger("idNegativadorExclusaoMotivo", helper.getIdNegativadorExclusaoMotivo());
			}

			if(helper.getIdQuadra() != null && helper.getIdQuadra() > 0){
				query.setInteger("idQuadra", helper.getIdQuadra());
			}

			if(helper.getIdEloPolo() != null && helper.getIdEloPolo() > 0){
				query.setInteger("idEloPolo", helper.getIdEloPolo());
			}

			if(helper.getIdLocalidade() != null && helper.getIdLocalidade() > 0){
				query.setInteger("idLocalidade", helper.getIdLocalidade());
			}

			if(helper.getIdSetorComercial() != null && helper.getIdSetorComercial() > 0){
				query.setInteger("idSetorComercial", helper.getIdSetorComercial());
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
	 * [UC0694] - Gerar Relatório de Negativações Excluídas
	 * 
	 * @author Yara T. Souza
	 * @date 16/01/2009
	 */
	public BigDecimal pesquisarSomatorioNegativadorMovimentoRegItens(Integer idNegativadorMovimentoReg, Integer idCobrancaDebitoSituacao)
					throws ErroRepositorioException{

		BigDecimal retorno = null;
		Session session = HibernateUtil.getSession();

		if(!idCobrancaDebitoSituacao.equals(CobrancaDebitoSituacao.PARCELADO)
						&& !idCobrancaDebitoSituacao.equals(CobrancaDebitoSituacao.PAGO)
						&& !idCobrancaDebitoSituacao.equals(CobrancaDebitoSituacao.CANCELADO)){
			throw new IllegalArgumentException("Situacao diferente de parcelado, pago e cancelado");
		}

		try{

			StringBuilder string = new StringBuilder();

			if(idCobrancaDebitoSituacao.equals(CobrancaDebitoSituacao.PARCELADO)){
				string.append(" select sum(nmri.valorDebito) ");
			}else if(idCobrancaDebitoSituacao.equals(CobrancaDebitoSituacao.PAGO)){
				string.append(" select sum(nmri.valorPago) ");
			}else if(idCobrancaDebitoSituacao.equals(CobrancaDebitoSituacao.CANCELADO)){

				string.append(" select sum(nmri.valorCancelado) ");
			}

			string.append(" from gcom.cobranca.NegativadorMovimentoRegItem nmri" + " where " + " nmri.negativadorMovimentoReg.id = "
							+ idNegativadorMovimentoReg);

			if(idCobrancaDebitoSituacao.equals(CobrancaDebitoSituacao.PARCELADO)){
				string.append(" and nmri.cobrancaDebitoSituacao.id = " + idCobrancaDebitoSituacao);
			}

			retorno = (BigDecimal) session.createQuery(string.toString()).uniqueResult();

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
	 * Retorna o somatório do VALOR PARCELADO - ENTRADAdo Débito do NegativadoMovimentoReg pela
	 * CobrancaDebitoSituacao
	 * [UC0693] Gerar Relatório Acompanhamaneto de Clientes Negativados
	 * 
	 * @author Yara Taciane
	 * @date 17/03/2008
	 */
	public BigDecimal pesquisarSomatorioValorDebito(NegativadorMovimentoReg negativadorMovimentoReg,
					CobrancaDebitoSituacao cobrancaDebitoSituacao) throws ErroRepositorioException{

		Integer retorno1 = null;
		BigDecimal retorno = null;
		String consulta_2 = null;

		Session session = HibernateUtil.getSession();

		try{

			String consulta_1 = " select nmrg.codigoExclusaoTipo " + " from gcom.cobranca.NegativadorMovimentoReg as nmrg "
							+ " where nmrg.id = " + negativadorMovimentoReg.getId() + "";

			retorno1 = (Integer) session.createQuery(consulta_1).uniqueResult();

			// Se null não está excluída
			if(retorno1 == null){
				consulta_2 = " select sum(nmri.valorDebito) " + " from gcom.cobranca.NegativadorMovimentoRegItem as nmri "
								+ " where nmri.negativadorMovimentoReg.id = " + negativadorMovimentoReg.getId()
								+ " and  nmri.cobrancaDebitoSituacao.id = " + cobrancaDebitoSituacao.getId() + "";
			}else{
				// Se null está excluída
				consulta_2 = " select sum(nmri.valorDebito) " + " from gcom.cobranca.NegativadorMovimentoRegItem as nmri "
								+ " where nmri.negativadorMovimentoReg.id = " + negativadorMovimentoReg.getId()
								+ " and  nmri.cobrancaDebitoSituacaoAposExclusao.id = " + cobrancaDebitoSituacao.getId() + "";
			}

			retorno = (BigDecimal) session.createQuery(consulta_2).uniqueResult();

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
	 * Retorna o somatório do VALOR PAGO e do VALOR CANCELADO
	 * [UC0693] Gerar Relatório Acompanhamaneto de Clientes Negativados
	 * 
	 * @author Vivianne Sousa
	 * @date 29/04/2009
	 */
	public Object[] pesquisarSomatorioValorPagoEValorCancelado(Integer idNegativadorMovimentoReg) throws ErroRepositorioException{

		Object[] retorno = null;
		Session session = HibernateUtil.getSession();

		try{

			String consulta = " select sum(nmri.valorPago), sum(nmri.valorCancelado) "
							+ " from gcom.cobranca.NegativadorMovimentoRegItem as nmri "
							+ " where nmri.negativadorMovimentoReg.id = :idNegativadorMovimentoReg ";
			// long t1 = System.currentTimeMillis();
			retorno = (Object[]) session.createQuery(consulta).setInteger("idNegativadorMovimentoReg", idNegativadorMovimentoReg)
							.uniqueResult();
			// long t2 = System.currentTimeMillis();
			// System.out.println("[UC0688]pesquisarSomatorioValorPagoEValorCancelado " + ( t2 -
			// t1));
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
	 * [UC0651] Inserir Comando Negativação
	 * [SB0003] Determinar Data Prevista para Execução do Comando
	 * 
	 * @author Ana Maria
	 * @date 11/12/2007
	 * @param idNegativador
	 * @return Date
	 * @throws ErroRepositorioException
	 */
	public Date pesquisarUltimaDataRealizacaoComando(Integer idNegativador, Integer icSimulacao) throws ErroRepositorioException{

		Date retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "select max(ngcm.dataHoraRealizacao) " + "from NegativacaoComando ngcm "
							+ "where ngcm.dataHoraRealizacao is not null and ngcm.negativador.id = :idNegativador "
							+ "and ngcm.indicadorSimulacao = :icSimulacao";

			retorno = (Date) session.createQuery(consulta).setInteger("idNegativador", idNegativador)
							.setInteger("icSimulacao", icSimulacao).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * Conta os registro do NegativadorMovimento aceitos.
	 * [UC0681] Consultar Movimentos dos Negativadores.
	 * 
	 * @author Yara Taciane
	 * @date 22/01/2008
	 */
	public Integer verificarTotalRegistrosAceitos(Integer idNegativadorMovimento) throws ErroRepositorioException{

		Integer retorno;
		Session session = HibernateUtil.getSession();

		try{
			String hql = " select count(*) " + " from gcom.cobranca.NegativadorMovimentoReg nmrg " + " where nmrg.indicadorAceito=1"
							+ " and   nmrg.negativadorMovimento.id = :idNegativadorMovimento";

			retorno = ((Number) session.createQuery(hql).setInteger("idNegativadorMovimento", idNegativadorMovimento).uniqueResult())
							.intValue();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0651] Inserir Comando Negativação
	 * [FS0026] Verificar existência de comando para o negativador na data
	 * 
	 * @author Ana Maria
	 * @date 07/05/2008
	 * @param idNegativador
	 * @param Data
	 * @return boolean
	 */
	public boolean verificarExistenciaComandoNegativador(String idNegativador, Date dataPrevista) throws ErroRepositorioException{

		boolean retorno = false;
		Integer pesquisar = null;
		Session session = HibernateUtil.getSession();

		String data = Util.recuperaDataInvertida(dataPrevista);

		try{
			String hql = " select nc.id" + " from gcom.cobranca.NegativacaoComando nc" + " inner join nc.negativador as negativador "
							+ " where nc.dataPrevista = '" + data + "'" + " and nc.dataHoraRealizacao is null " + " and negativador.id ="
							+ idNegativador;

			pesquisar = (Integer) session.createQuery(hql).uniqueResult();

			if(pesquisar != null && !pesquisar.equals("")){
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
	 * Apresenta todos os registros do NegativadorMovimento aceitos.
	 * [UC0681] Consultar Movimentos dos Negativadores.
	 * 
	 * @author Yara Taciane
	 * @date 22/01/2008
	 */
	public Integer pesquisarNegativadorMovimentoRegRetMot(Integer idNegativadorMovimentoReg, Integer idNegativadorRetornoMotivo)
					throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		Integer retorno = null;
		try{

			String consulta = " select nmrr.nmrr_id as id " + " from negativador_movt_reg_ret_motv nmrr"
							+ " inner join negativador_movimento_reg nmrg  on nmrg.nmrg_id = nmrr.nmrg_id "
							+ " inner join negativador_retorno_motivo nrmt on nrmt.nrmt_id = nmrr.nrmt_id "
							+ " where  nmrr.nmrg_id  = :idNegativadorMovimentoReg " + " and nmrr.nrmt_id = :idNegativadorRetornoMotivo  ";

			retorno = (Integer) session.createSQLQuery(consulta).addScalar("id", Hibernate.INTEGER)
							.setInteger("idNegativadorMovimentoReg", idNegativadorMovimentoReg)
							.setInteger("idNegativadorRetornoMotivo", idNegativadorRetornoMotivo).setMaxResults(1).uniqueResult();

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
	 * Retorna o ImovelCobrancaSituacao pelo imovel do NegativadorMovimentoReg
	 * [UC0693] Gerar Relatório Acompanhamaneto de Clientes Negativados
	 * 
	 * @author Yara Taciane
	 * @date 17/03/2008
	 */
	public ImovelCobrancaSituacao getImovelCobrancaSituacao(Imovel imovel, CobrancaSituacao cobrancaSituacao)
					throws ErroRepositorioException{

		ImovelCobrancaSituacao retorno = null;
		Session session = HibernateUtil.getSession();

		try{

			String hql = " select ics" + " from gcom.cadastro.imovel.ImovelCobrancaSituacao as ics"
							+ " inner join fetch ics.imovel as imov " + " inner join fetch ics.cobrancaSituacao as cbst "
							+ " where ics.imovel.id = " + imovel.getId() + " and ics.dataRetiradaCobranca is null "
							+ " and ics.cobrancaSituacao.id = " + cobrancaSituacao.getId() + " order by ics.ultimaAlteracao desc ";

			// TODO voltar - retorno = (ImovelCobrancaSituacao)
			// session.createQuery(hql).uniqueResult();
			Collection ret = session.createQuery(hql).list();
			if(ret.size() > 0) retorno = (ImovelCobrancaSituacao) ret.iterator().next();

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
	 * Retorna o ImovelCobrancaSituacao pelo imovel do NegativadorMovimentoReg
	 * [UC0693] Gerar Relatório Acompanhamaneto de Clientes Negativados
	 * 
	 * @author Yara Taciane
	 * @date 17/03/2008
	 */
	public NegativadorMovimentoReg getNegativadorMovimentoReg(NegativadorMovimento negativadorMovimento, Integer numeroRegistro)
					throws ErroRepositorioException{

		NegativadorMovimentoReg retorno = null;
		Session session = HibernateUtil.getSession();

		try{

			String hql = " select nmrg" + " from gcom.cobranca.NegativadorMovimentoReg nmrg "
							+ " inner join fetch nmrg.negativadorMovimento nmrv " + " where nmrv.id = :idNegativadorMovimento "
							+ " and   nmrg.numeroRegistro = :numeroRegistro ";

			retorno = (NegativadorMovimentoReg) session.createQuery(hql).setInteger("idNegativadorMovimento", negativadorMovimento.getId())
							.setInteger("numeroRegistro", numeroRegistro).uniqueResult();

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
	 * Retorna o ImovelCobrancaSituacao pelo imovel do NegativadorMovimentoReg
	 * [UC0693] Gerar Relatório Acompanhamaneto de Clientes Negativados
	 * 
	 * @author Yara Taciane
	 * @date 17/03/2008
	 */
	public NegativadorMovimento getNegativadorMovimento(Negativador negativador, Integer numeroRegistrosEnvio, Boolean idRetorno)
					throws ErroRepositorioException{

		NegativadorMovimento retorno = null;
		Session session = HibernateUtil.getSession();

		try{

			String hql = " select ngmv" + " from gcom.cobranca.NegativadorMovimento ngmv " + " left join fetch ngmv.negativador as negt "
							+ " where ngmv.negativador.id = " + negativador.getId() + " and  ngmv.numeroSequencialEnvio = "
							+ numeroRegistrosEnvio;

			if(idRetorno){
				hql = hql + " and  ngmv.dataRetorno is null ";
			}

			retorno = (NegativadorMovimento) session.createQuery(hql).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * [UC0651] Manter Comando de Nagativação Critério
	 * Remove Negativação Comando
	 * 
	 * @author Ana Maria
	 * @date 22/01/2008
	 * @param idNegativacaoCriterio
	 * @return Integer
	 * @throws ErroRepositorioException
	 */
	public void removerNegativacaoComando(Integer idNegativacaoComando) throws ErroRepositorioException{

		String remocao = null;

		Session session = HibernateUtil.getSession();

		try{

			remocao = "delete NegativacaoComando " + "where ngcm_id =:idNegativacaoComando";

			session.createQuery(remocao).setInteger("idNegativacaoComando", idNegativacaoComando).executeUpdate();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}
	}

	/**
	 * Obtem geraRegistroNegativacaoRegTrailler
	 * 
	 * @author Marcio Roberto
	 * @date 30/01/2008
	 * @param int idNegativador, int idNegativacaoMovimento,
	 *        StringBuilder registro, int quantidadeRegistros, int idNegCriterio
	 * @return void
	 * @throws ErroRepositorioException
	 */
	@Deprecated
	public void geraRegistroNegativacaoRegTrailler(int idNegativador, int idNegativacaoMovimento, StringBuilder registro,
					int quantidadeRegistros, Integer idNegCriterio) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		String insert;
		Connection con = null;
		Statement stmt = null;
		try{
			con = session.connection();
			stmt = con.createStatement();
			Integer idNrTipo = NegativadorRegistroTipo.ID_SERASA_TRAILLER;
			if(new Integer(idNegativador).equals(Negativador.NEGATIVADOR_SPC_SAO_PAULO)){
				idNrTipo = NegativadorRegistroTipo.ID_SPC_TRAILLER_SP;
			}else if(new Integer(idNegativador).equals(Negativador.NEGATIVADOR_SPC_BRASIL)){
				idNrTipo = NegativadorRegistroTipo.ID_SPC_TRAILLER_BR;
			}
			insert = "insert into negativador_movimento_reg (" + " nmrg_id, " // 01
							+ " ngmv_id, " // 02
							+ " nmrg_idreginclusao, " // 03
							+ " nrtp_id, " // 04
							+ " nmrg_cnregistro, " // 05
							+ " nmrg_tmultimaalteracao, " // 06
							+ " usur_id, " // 07
							+ " nmrg_cdexclusaotipo, " // 08
							+ " nmrg_icaceito, " // 09
							+ " nmrg_iccorrecao, " // 10
							+ " nmrg_vldebito, " // 11
							+ " cdst_id, " // 12
							+ " nmrg_dtsituacaodebito, " // 13
							+ " imov_id, " // 14
							+ " loca_id, " // 15
							+ " qdra_id, " // 16
							+ " nmrg_cdsetorcomercial, " // 17
							+ " nmrg_nnquadra, " // 18
							+ " iper_id, " // 19
							+ " ngct_id, " // 20
							+ " clie_id, " // 21
							+ " catg_id, " // 22
							+ " cpft_id, " // 23
							+ " nmrg_nncpf, " // 24
							+ " nmrg_nncnpj, " // 25
							+ " nemt_id, " // 26
							+ " nmrg_icsitdefinitiva, " // 27
							+ " nmrg_nnregistro, " // 28
							+ " cbst_id ) " // 29
							+ " values ( " + " nextval('SQ_NEGTVDR_MOVT_REG'), " // 01
							+ idNegativacaoMovimento + ", " // 02
							+ "null, " // 03
							+ idNrTipo + ", " // 04
							+ "'" + registro.toString() + "'" + ", " // 05
							+ "'" + Util.formatarData(new Date()) + "'," // 06
							+ "null," // 07
							+ "null," // 08
							+ "null," // 09
							+ "null," // 10
							+ "null," // 11
							+ "null," // 12
							+ "null," // 13
							+ "null," // 14
							+ "null," // 15
							+ "null," // 16
							+ "null," // 17
							+ "null," // 18
							+ "null," // 19
							+ idNegCriterio + ", " // 20
							+ "null," // 21
							+ "null," // 22
							+ "null," // 23
							+ "null," // 24
							+ "null," // 25
							+ "null," // 26
							+ "2, " // 27
							+ quantidadeRegistros + "," // 28
							+ "null )"; // 29
			stmt.executeUpdate(insert);
			// System.out.print("1 - INSERINDO geraRegistroNegativacaoRegTrailler!! ");
		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate geraRegistroNegativacaoRegTrailler ");
		}catch(SQLException e){
			throw new ErroRepositorioException(e, "Erro no Insert geraRegistroNegativacaoRegTrailler ");
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
	 * Insere RegistroNegativacao
	 * 
	 * @author Marcio Roberto
	 * @date 30/10/2007
	 * @param
	 * @return
	 * @throws ErroRepositorioException
	 */
	@Deprecated
	public Integer geraRegistroNegativacao(int idNegativador, int saenvio, int idNegativadorComando) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		String insert;
		Connection con = null;
		Statement stmt = null;
		int nextValue = 0;
		try{
			con = session.connection();
			stmt = con.createStatement();
			insert = "insert into negativador_movimento (" + " ngmv_id, " // 01
							+ " ngmv_cdmovimento, " // 02
							+ " ngmv_dtenvio, " // 03
							+ " ngmv_dtprocessamentoenvio, " // 04
							+ " ngmv_dtretorno, " // 05
							+ " ngmv_dtprocessamentoretorno, " // 06
							+ " ngmv_nnnsaenvio, " // 07
							+ " ngmv_nnnsaretorno, " // 08
							+ " ngmv_nnregistrosenvio, " // 09
							+ " ngmv_nnregistrosretorno, " // 10
							+ " ngmv_vltotalenvio, " // 11
							+ " ngmv_tmultimaalteracao, " // 12
							+ " negt_id, " // 13
							+ " ngcm_id ) " // 14
							+ " values ( " + " nextval('SQ_NEGTVDR_MOVT') , " // 01
							+ " 1, " // 02
							+ " now(), " // 03
							+ " now(), " // 04
							+ "null," // 05
							+ "null," // 06
							+ (saenvio) + ", " // 07
							+ "null," // 08
							+ "null," // 09
							+ "null," // 10
							+ "null," // 11
							+ " now(), " // 12
							+ idNegativador + ", " // 13
							+ idNegativadorComando + ") "; // 14
			stmt.executeUpdate(insert);
			// System.out.print("1 - INSERINDO RegistroNegativadorMovimento! ");
			nextValue = this.getNegativadorMovimento();
		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate RegistroNegativadorMovimento ");
		}catch(SQLException e){
			throw new ErroRepositorioException(e, "Erro no Insert RegistroNegativadorMovimento ");
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
		return nextValue;
	}

	/**
	 * geraRegistroNegativacaoMovimentoRegItem
	 * 
	 * @author Marcio Roberto
	 * @date 13/11/2007
	 * @param
	 * @return
	 * @throws ErroRepositorioException
	 */
	@Deprecated
	public void geraRegistroNegativacaoMovimentoRegItem(int idDebSit, BigDecimal valorDoc, int idDetalheRegSPC, int idDocTipo,
					Integer idGuiaPagamento, Integer idConta) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		String insert;
		Connection con = null;
		Statement stmt = null;
		try{
			con = session.connection();
			stmt = con.createStatement();

			insert = "insert into negativador_movimento_reg_item (" + " nmri_id , " // 01
							+ " nmrg_id , " // 02
							+ " dotp_id , " // 03
							+ " dbac_id , " // 04
							+ " gpag_id , " // 05
							+ " cnta_id , " // 06
							+ " cdst_id , " // 07
							+ " nmri_vldebito , " // 08
							+ " nmri_dtsituacaodebito , " // 09
							+ " cdst_idaposexclusao , " // 10
							+ " nmri_dtsitdebaposexclusao , " // 11
							+ " nmri_icsitdefinitiva," + " nmri_tmultimaalteracao) " // 12
							+ " values ( " + " SQ_NEGTVDR_MOVT_REG_ITEM.NEXTVAL, " // 01
							+ idDetalheRegSPC + ", " // 02
							+ idDocTipo + ", " // 03
							+ "null, " // 04
							+ idGuiaPagamento + ", " // 05
							+ idConta + ", " // 06
							+ idDebSit + ", " // 07
							+ valorDoc + ", " // 08
							+ " current_timestamp, " // 09
							+ "null, " // 10
							+ "null, " // 11
							+ "2," + " current_timestamp) "; // 12

			stmt.executeUpdate(insert);
			// System.out.print("1 - INSERINDO geraRegistroNegativacaoMovimentoRegItem!! ");
		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate geraRegistroNegativacaoMovimentoRegItem ");
		}catch(SQLException e){
			throw new ErroRepositorioException(e, "Erro no Insert geraRegistroNegativacaoMovimentoRegItem ");
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
	 * Pesquisa a coleção de clientes do imóvel para negativação
	 * sem o cliente empresa do sistema parâmetro
	 * 
	 * @author Ana Maria
	 * @date 17/12/2008
	 * @param idImovel
	 * @return Collection
	 * @exception ErroRepositorioException
	 */

	public Collection pesquisarClienteImovelParaNegativacao(Integer idImovel, String cnpjEmpresa) throws ErroRepositorioException{

		Collection colecaoClientes = null;
		Session session = HibernateUtil.getSession();
		String consulta = null;
		Collection clienteImoveis = null;

		try{

			consulta = "SELECT clienteImovel.id, " // 0
							+ "clienteRelacaoTipo.id, " // 1
							+ "clienteRelacaoTipo.descricao, " // 2
							+ "cliente.id, " // 3
							+ "cliente.nome, " // 4
							+ "cliente.cnpj, " // 5
							+ "cliente.cpf " // 6
							+ "from ClienteImovel clienteImovel "
							+ "left join clienteImovel.cliente cliente "
							+ "left join clienteImovel.clienteRelacaoTipo clienteRelacaoTipo "
							+ "where clienteImovel.imovel.id = :idImovel and clienteImovel.dataFimRelacao is null "
							+ "and clienteImovel.imovel.indicadorExclusao = :indicadorExclusao "
							+ "and (cliente.cnpj is null or cliente.cnpj <>:cnpjEmpresa)" + " and cliente.id not in (14372860, 6548350) ";

			colecaoClientes = session.createQuery(consulta).setInteger("idImovel", idImovel.intValue()).setInteger("indicadorExclusao", 2)
							.setString("cnpjEmpresa", cnpjEmpresa).list();

			if(colecaoClientes != null && !colecaoClientes.isEmpty()){

				clienteImoveis = new ArrayList();
				Iterator iteratorColecaoClientes = colecaoClientes.iterator();
				while(iteratorColecaoClientes.hasNext()){

					ClienteImovel clienteImovel = new ClienteImovel();
					Object[] arrayCliente = (Object[]) iteratorColecaoClientes.next();
					Cliente cliente = new Cliente();
					// 0 - id do cliente imovel
					if(arrayCliente[0] != null){
						clienteImovel.setId((Integer) arrayCliente[0]);
					}
					// 1 - id cliente relação tipo
					// 2 - descricao cliente relação tipo
					if(arrayCliente[1] != null){
						ClienteRelacaoTipo clienteRelacaoTipo = new ClienteRelacaoTipo();
						clienteRelacaoTipo.setId((Integer) arrayCliente[1]);
						clienteRelacaoTipo.setDescricao((String) arrayCliente[2]);
						clienteImovel.setClienteRelacaoTipo(clienteRelacaoTipo);
					}
					// 3 - id do cliente
					if(arrayCliente[3] != null){
						cliente.setId((Integer) arrayCliente[3]);
					}
					// 4 - nome do cliente
					if(arrayCliente[4] != null){
						cliente.setNome((String) arrayCliente[4]);
					}
					// 5 - cnpj
					if(arrayCliente[5] != null){
						cliente.setCnpj((String) arrayCliente[5]);
					}
					// 6 - cpf
					if(arrayCliente[6] != null){
						cliente.setCpf((String) arrayCliente[6]);
					}
					clienteImovel.setCliente(cliente);
					clienteImoveis.add(clienteImovel);
				}
			}
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return clienteImoveis;
	}

	/**
	 * Verificar existência critérios do comando
	 * 
	 * @author Ana Maria
	 * @date 09/06/2008
	 * @param Integer
	 * @return Object[]
	 * @throws ErroRepositorioException
	 */
	public Object[] verificarExistenciaCriterio(Integer idCriterio) throws ErroRepositorioException{

		Integer pesquisarSubcategoriaCriterio = null;
		Integer pesquisarPerfilImovelCriterio = null;
		Integer pesquisarClienteTipo = null;
		Integer pesquisarLigacaoAguaSit = null;
		Integer pesquisarLigacaoEsgotoSit = null;
		Object[] object = new Object[5];

		Session session = HibernateUtil.getSession();

		try{
			String hql = " select count(last_id) as qtdLigacaoAguaSituacao" + " from negativacao_criterio_ligacao_agua ncla"
							+ " where ncla.ngct_id =" + idCriterio;

			pesquisarLigacaoAguaSit = (Integer) session.createSQLQuery(hql).addScalar("qtdLigacaoAguaSituacao", Hibernate.INTEGER)
							.uniqueResult();

			String hql1 = " select count(lest_id) as qtdLigacaoEsgotoSituacao" + " from negativacao_criterio_ligacao_esgoto ncle"
							+ " where ncle.ngct_id =" + idCriterio;

			pesquisarLigacaoEsgotoSit = (Integer) session.createSQLQuery(hql1).addScalar("qtdLigacaoEsgotoSituacao", Hibernate.INTEGER)
							.uniqueResult();

			String hql2 = " select count(scat_id) as qtdSubCrit" + " from negativacao_criterio_subcategoria ncst" + " where ncst.ngct_id ="
							+ idCriterio;

			pesquisarSubcategoriaCriterio = (Integer) session.createSQLQuery(hql2).addScalar("qtdSubCrit", Hibernate.INTEGER)
							.uniqueResult();

			String hql3 = " select count(iper_id) as qtdPerfilImovCrit" + " from negativacao_criterio_imovel_perfil ncip"
							+ " where ncip.ngct_id =" + idCriterio;

			pesquisarPerfilImovelCriterio = (Integer) session.createSQLQuery(hql3).addScalar("qtdPerfilImovCrit", Hibernate.INTEGER)
							.uniqueResult();

			String hql4 = " select count(cltp_id) as qtdClienteTipoCrit" + " from negativacao_criterio_cliente_tipo nccl"
							+ " where nccl.ngct_id =" + idCriterio;

			pesquisarClienteTipo = (Integer) session.createSQLQuery(hql4).addScalar("qtdClienteTipoCrit", Hibernate.INTEGER).uniqueResult();

			object[0] = pesquisarLigacaoAguaSit;
			object[1] = pesquisarLigacaoEsgotoSit;
			object[2] = pesquisarSubcategoriaCriterio;
			object[3] = pesquisarPerfilImovelCriterio;
			object[4] = pesquisarClienteTipo;

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}
		return object;
	}

	/**
	 * Verifica se a situação da ligação de água do imovel corresponde
	 * as situação da ligação de água do criterio da negativacao.
	 * 
	 * @author Ana Maria
	 * @date 12/06/2008
	 * @param int
	 * @param int
	 * @return Integer
	 * @throws ErroRepositorioException
	 */
	public Integer verificaLigacaoAguaImovelNegativacaoCriterio(int idCriterio, int idLigacaoAguaSituacao) throws ErroRepositorioException{

		Integer retorno = null;
		Session session = HibernateUtil.getSession();
		try{

			String hql = " select count(negCriLigAgua.comp_id.ligacaoAguaSituacao) "
							+ " from gcom.cobranca.NegativacaoCriterioLigacaoAgua as negCriLigAgua "
							+ " where negCriLigAgua.comp_id.negativacaoCriterio.id = :idCriterio "
							+ " and negCriLigAgua.comp_id.ligacaoAguaSituacao = :idLigacaoAguaSituacao ";

			retorno = ((Number) session.createQuery(hql).setInteger("idCriterio", idCriterio)
							.setInteger("idLigacaoAguaSituacao", idLigacaoAguaSituacao).uniqueResult()).intValue();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate verificaPerfilImovelNegativacaoCriterio");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * Verifica se a situação da ligação de esgoto do imovel corresponde
	 * as situação da ligação de esgoto do criterio da negativacao.
	 * 
	 * @author Ana Maria
	 * @date 12/06/2008
	 * @param int
	 * @param int
	 * @return Integer
	 * @throws ErroRepositorioException
	 */
	public Integer verificaLigacaoEsgotoImovelNegativacaoCriterio(int idCriterio, int idLigacaoEsgotoSituacao)
					throws ErroRepositorioException{

		Integer retorno = null;

		Session session = HibernateUtil.getSession();
		try{

			String hql = " select count(negCriLigEsgoto.comp_id.ligacaoEsgotoSituacao) "
							+ " from gcom.cobranca.NegativacaoCriterioLigacaoEsgoto as negCriLigEsgoto "
							+ " where negCriLigEsgoto.comp_id.negativacaoCriterio.id = :idCriterio "
							+ " and negCriLigEsgoto.comp_id.ligacaoEsgotoSituacao = :idLigacaoEsgotoSituacao ";

			retorno = ((Number) session.createQuery(hql).setInteger("idCriterio", idCriterio)
							.setInteger("idLigacaoEsgotoSituacao", idLigacaoEsgotoSituacao).uniqueResult()).intValue();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate verificaPerfilImovelNegativacaoCriterio");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	public Object[] pesquisarDadosImovelParaNegativacao(Integer idImovel) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		Object[] retorno = null;
		try{
			String sql = " select imov.cbst_id as idCobracaSituacao," + " imov.iper_id as idImovelPerfil,"
							+ " imov.last_id as idLigacaoAgua," + " imov.lest_id as idLigacaoEsgoto" + " from imovel imov "
							+ " where imov.imov_id = " + idImovel;

			retorno = (Object[]) session.createSQLQuery(sql).addScalar("idCobracaSituacao", Hibernate.INTEGER)
							.addScalar("idImovelPerfil", Hibernate.INTEGER).addScalar("idLigacaoAgua", Hibernate.INTEGER)
							.addScalar("idLigacaoEsgoto", Hibernate.INTEGER).setMaxResults(1).uniqueResult();
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
	 * Consultar o Motivo da Exclusao do Negativador
	 * 
	 * @author Yara Taciane
	 * @date 22/07/2008
	 * @param Integer
	 * @return Object[]
	 * @throws ErroRepositorioException
	 */

	public NegativadorExclusaoMotivo pesquisarMotivoExclusao(Integer idMotivoExclusao) throws ErroRepositorioException{

		NegativadorExclusaoMotivo retorno = null;
		Session session = HibernateUtil.getSession();
		try{

			String hql = " select nemt" + " from NegativadorExclusaoMotivo nemt" + " left join fetch nemt.cobrancaDebitoSituacao as cds "
							+ " where nemt.id = :idMotivoExclusao";

			retorno = (NegativadorExclusaoMotivo) session.createQuery(hql).setInteger("idMotivoExclusao", idMotivoExclusao).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate getDadosContratoNegativador");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * Consulta as rotas para a geracao do resumo diario da negativacao
	 * [UC0688] Gerar Resumo Diário da Negativação
	 * Fluxo principal Item 1.0
	 * 
	 * @author Francisco do Nascimento
	 * @date 03/02/2009
	 */
	public List consultarRotasParaGerarResumoDiarioNegativacao() throws ErroRepositorioException{

		List retorno = new ArrayList();
		Session session = HibernateUtil.getSession();

		try{
			String sql = " SELECT " + "distinct q.rota_id as idRota " + "from " + "setor_comercial sc "
							+ "join quadra q on q.stcm_id = sc.stcm_id " + "where " + "loca_id in "
							+ "(select distinct nmr.loca_id from negativador_movimento_reg nmr "
							+ " 	join negativador_movimento nm on nmr.ngmv_id = nm.ngmv_id " + "	where nm.ngmv_cdmovimento = "
							+ NegativadorMovimento.CODIGO_MOVIMENTO_INCLUSAO + "	and nmr.imov_id is not null "
							+ "	and nmr.nmrg_icaceito = " + ConstantesSistema.SIM + ")";

			retorno = (List) session.createSQLQuery(sql).addScalar("idRota", Hibernate.INTEGER).list();

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
	 * Apaga todos os ResumoNegativacao
	 * [UC0688] Gerar Resumo Diário da Negativação
	 * Fluxo principal Item 2.0
	 * 
	 * @author Yara Taciane
	 * @date 28/07/2008
	 */
	public void apagarResumoNegativacao(Integer numeroPenultimaExecResumoNegat) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		try{

			String hql = " delete from gcom.cobranca.ResumoNegativacao where numeroExecucaoResumoNegativacao = "
							+ numeroPenultimaExecResumoNegat;

			session.createQuery(hql).executeUpdate();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}
	}

	/**
	 * Método que retorna todas NegativacaoComando que ainda nao tenha sido executada
	 * (dataHoraRealizacao == null)
	 * [UC0687] Executar Comando de Negativação
	 * [Fluxo Principal] - Item 2.0
	 * 
	 * @author Thiago Toscano
	 * @date 21/01/2008
	 * @return
	 * @throws ErroRepositorioException
	 */
	public NegativacaoComando consultarNegativacaoComandadoParaExecutar() throws ErroRepositorioException{

		NegativacaoComando retorno = null;
		Session session = HibernateUtil.getSession();

		String data1 = Util.recuperaDataInvertida(new Date());

		try{
			String hql = " select nc" + " from gcom.cobranca.NegativacaoComando nc" + " inner join fetch nc.negativador as negativador"
							+ " where nc.dataPrevista <= '" + data1 + "'" + " and nc.dataHoraRealizacao is null";

			retorno = (NegativacaoComando) session.createQuery(hql).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		return retorno;

	}

	public Object[] pesquisarParametroNegativacaoCriterio(Integer idNegativacaoCriterio) throws ErroRepositorioException{

		Object[] retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = " select nccg.cbgr_id as idNegCriCobGrp, " // 0
							+ " ncgr.greg_id as idNegCriGere, " // 1
							+ " ncun.uneg_id as idNegCriUneg, " // 2
							+ " ncep.loca_id as idNegCriElo, " // 3
							+ " ngct.loca_idinicial as idNegCriLocInic," // 4
							+ " ngct.loca_idfinal as idNegCriLocFinal" // 5
							+ " from cobranca.negativacao_criterio ngct "
							+ " left join cobranca.negativ_crit_cobr_grupo nccg on (nccg.ngct_id = ngct.ngct_id ) "
							+ " left join cobranca.negativ_crit_ger_reg ncgr on (ncgr.ngct_id = ngct.ngct_id) "
							+ " left join cobranca.negativ_crit_und_neg ncun on (ncun.ngct_id = ngct.ngct_id) "
							+ " left join cobranca.negativ_crit_elo ncep on (ncep.ngct_id = ngct.ngct_id) " + " where ngct.ngct_id = :id ";

			retorno = (Object[]) session.createSQLQuery(consulta).addScalar("idNegCriCobGrp", Hibernate.INTEGER)
							.addScalar("idNegCriGere", Hibernate.INTEGER).addScalar("idNegCriUneg", Hibernate.INTEGER)
							.addScalar("idNegCriElo", Hibernate.INTEGER).addScalar("idNegCriLocInic", Hibernate.INTEGER)
							.addScalar("idNegCriLocFinal", Hibernate.INTEGER).setInteger("id", idNegativacaoCriterio.intValue())
							.uniqueResult();

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
	 * Pesquisar as rotas dos Imóveis
	 * 
	 * @author Ana Maria, Francisco do Nascimento
	 * @date 05/06/2008, 14/01/09
	 */
	public Collection pesquisarRotasImoveis() throws ErroRepositorioException{

		Collection retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta = null;
		try{
			consulta = " select distinct im.rota.id " + " from Imovel im " + " inner join im.quadra quadra "
							+ " inner join im.localidade loc";

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
	 * Pesquisar as rotas dos Imóveis que estão no resultado da simulação
	 * 
	 * @author Ana Maria, Francisco do Nascimento
	 * @date 05/06/2008, 14/01/09
	 */
	public Collection pesquisarRotasImoveisComandoSimulacao(Integer idNegativacaoComando) throws ErroRepositorioException{

		Collection retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta = null;
		try{
			consulta = " select distinct imov.rota.id" + " from NegativadorResultadoSimulacao ngsm" + " inner join ngsm.imovel imov"
							+ " inner join imov.quadra quadra " + " where ngsm.negativacaoComando.id =" + idNegativacaoComando;

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
	 * UC0671 - Gerar movimento de inclusão da negativação
	 * Pesquisar rotas por grupo de cobrança para um critério de negativação
	 * 
	 * @author Francisco do Nascimento
	 * @date 14/01/2009
	 * @param nCriterio
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List pesquisarRotasPorCobrancaGrupoParaNegativacao(NegativacaoCriterio nCriterio) throws ErroRepositorioException{

		List retorno = new ArrayList();
		Session session = HibernateUtil.getSession();
		String sql = null;

		try{
			sql = " select distinct rota.rota_id as idRota from cadastro.quadra qdra "
							+ " inner join micromedicao.rota rota on(qdra.rota_id = rota.rota_id)"
							+ " where qdra.qdpf_id <> 2 and rota.cbgr_id in (select nccg.cbgr_id "
							+ "   from cobranca.negativacao_criterio as ngct "
							+ " 	inner join cobranca.negativ_crit_cobr_grupo as nccg on(ngct.ngct_id = nccg.ngct_id) "
							+ " 	where ngct.ngct_id  = " + nCriterio.getId() + " ) ";
			retorno = (List) session.createSQLQuery(sql).addScalar("idRota", Hibernate.INTEGER).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate getImovelCindicao");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * UC0671 - Gerar movimento de inclusão da negativação
	 * Pesquisar rotas por gerencia regional para um critério de negativação
	 * 
	 * @author Francisco do Nascimento
	 * @date 14/01/2009
	 * @param nCriterio
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List pesquisarRotasPorGerenciaRegionalParaNegativacao(NegativacaoCriterio nCriterio) throws ErroRepositorioException{

		List retorno = new ArrayList();
		Session session = HibernateUtil.getSession();
		String sql = null;

		try{
			sql = " select distinct rota.rota_id as idRota from " + " cadastro.quadra qdra "
							+ " inner join micromedicao.rota rota on(qdra.rota_id = rota.rota_id)"
							+ " inner join cadastro.setor_comercial stcm on(rota.stcm_id = stcm.stcm_id)" + " where qdra.qdpf_id <> 2 and "
							+ " stcm.greg_id in (select  ncgr.greg_id" + "             	 from cobranca.negativacao_criterio as ngct "
							+ "             	 inner join cobranca.negativ_crit_ger_reg as ncgr on(ngct.ngct_id = ncgr.ngct_id)"
							+ "					 where ngct.ngct_id  = " + nCriterio.getId() + " ) ";
			retorno = (List) session.createSQLQuery(sql).addScalar("idRota", Hibernate.INTEGER).list();
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate getImovelCindicao");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * UC0671 - Gerar movimento de inclusão da negativação
	 * Pesquisar rotas por localidade para um critério de negativação
	 * 
	 * @author Francisco do Nascimento
	 * @date 14/01/2009
	 * @param nCriterio
	 * @return
	 * @throws ErroRepositorioException
	 */

	public List pesquisarRotasPorLocalidadeParaNegativacao(NegativacaoCriterio nCriterio) throws ErroRepositorioException{

		List retorno = new ArrayList();
		Session session = HibernateUtil.getSession();
		String sql = null;

		try{
			sql = " select distinct rota.rota_id as idRota from " + " cadastro.quadra qdra "
							+ " inner join micromedicao.rota rota on(qdra.rota_id = rota.rota_id)"
							+ " inner join cadastro.setor_comercial stcm on(rota.stcm_id = stcm.stcm_id)" + " where qdra.qdpf_id <> 2 and "
							+ " stcm.loca_id in (select  ncel.loca_id" + "             	 from cobranca.negativacao_criterio as ngct "
							+ "             	 inner join cobranca.negativ_crit_elo as ncel on(ngct.ngct_id = ncel.ngct_id)"
							+ "					 where ngct.ngct_id  = " + nCriterio.getId() + " ) ";
			retorno = (List) session.createSQLQuery(sql).addScalar("idRota", Hibernate.INTEGER).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate getImovelCindicao");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;

	}

	/**
	 * UC0671 - Gerar movimento de inclusão da negativação
	 * Pesquisar rotas por localidade inicial e final para um critério de negativação
	 * 
	 * @author Francisco do Nascimento
	 * @date 14/01/2009
	 * @param nCriterio
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List pesquisarRotasPorLocalidadesParaNegativacao(NegativacaoCriterio nCriterio) throws ErroRepositorioException{

		List retorno = new ArrayList();
		Session session = HibernateUtil.getSession();
		String sql = null;

		try{
			sql = " select distinct rota.rota_id as idRota from " + " cadastro.quadra qdra "
							+ " inner join micromedicao.rota rota on(qdra.rota_id = rota.rota_id)"
							+ " inner join cadastro.setor_comercial stcm on(rota.stcm_id = stcm.stcm_id)" + " where qdra.qdpf_id <> 2 and "
							+ " stcm.loca_id  between " + nCriterio.getLocalidadeInicial().getId() + " and "
							+ nCriterio.getLocalidadeFinal().getId();
			if(nCriterio.getCodigoSetorComercialInicial() != null && nCriterio.getCodigoSetorComercialFinal() != null){
				sql = sql + " and stcm.stcm_cdsetorcomercial  between " + nCriterio.getCodigoSetorComercialInicial() + "" + " and "
								+ nCriterio.getCodigoSetorComercialFinal();
			}
			retorno = (List) session.createSQLQuery(sql).addScalar("idRota", Hibernate.INTEGER).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate getImovelCindicao");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;

	}

	/**
	 * UC0671 - Gerar movimento de inclusão da negativação
	 * Pesquisar rotas por unidade de negócio para um critério de negativação
	 * 
	 * @author Francisco do Nascimento
	 * @date 14/01/2009
	 * @param nCriterio
	 * @return
	 * @throws ErroRepositorioException
	 */

	public List pesquisarRotasPorUnidadeNegocioParaNegativacao(NegativacaoCriterio nCriterio) throws ErroRepositorioException{

		List retorno = new ArrayList();
		Session session = HibernateUtil.getSession();
		String sql = null;

		try{
			sql = " select distinct rota.rota_id as idRota from " + " cadastro.quadra qdra "
							+ " inner join micromedicao.rota rota on(qdra.rota_id = rota.rota_id)"
							+ " inner join cadastro.setor_comercial stcm on(rota.stcm_id = stcm.stcm_id)"
							+ " inner join cadastro.localidade loca on(stcm.loca_id = loca.loca_id)" + " where qdra.qdpf_id <> 2 and "
							+ " loca.uneg_id in (select  ncgr.uneg_id" + "             	 from cobranca.negativacao_criterio as ngct "
							+ "             	 inner join cobranca.negativ_crit_und_neg as ncun on(ngct.ngct_id = ncun.ngct_id)"
							+ "					 where ngct.ngct_id  = " + nCriterio.getId() + " ) ";
			retorno = (List) session.createSQLQuery(sql).addScalar("idRota", Hibernate.INTEGER).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate getImovelCindicao");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;

	}

	/**
	 * 
	 */
	public List consultarNegativadorMovimentoRegItem(Integer idNegativadorMovimentoReg, Integer idDocumentoTipo)
					throws ErroRepositorioException{

		List retorno = new ArrayList();
		Session session = HibernateUtil.getSession();

		try{
			StringBuffer query = new StringBuffer();

			query.append(" select nmri ");
			query.append(" from gcom.cobranca.NegativadorMovimentoRegItem nmri ");
			query.append(" left join fetch nmri.negativadorMovimentoReg nmr ");
			query.append(" left join fetch nmri.cobrancaDebitoSituacao cds ");
			query.append(" left join fetch nmri.contaGeral cg ");
			query.append(" left join fetch cg.conta c ");
			query.append(" left join fetch c.debitoCreditoSituacaoAtual ");
			query.append(" left join fetch c.debitoCreditoSituacaoAnterior ");
			query.append(" left join fetch cg.contaHistorico ch ");
			query.append(" left join fetch ch.debitoCreditoSituacaoAnterior ");
			query.append(" left join fetch ch.debitoCreditoSituacaoAtual ");
			query.append(" where nmri.negativadorMovimentoReg.id = :idNegativadorMovimentoReg");
			query.append("  and nmri.documentoTipo.id = :idDocumentoTipo");

			retorno = (List) session.createQuery(query.toString()).setInteger("idNegativadorMovimentoReg", idNegativadorMovimentoReg)
							.setInteger("idDocumentoTipo", idDocumentoTipo).list();

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
	 * @param idNegativadorMovimentoReg
	 * @param idDocumentoTipo
	 * @return
	 * @throws ErroRepositorioException
	 */

	public Collection pesquisarNegativadorMovimentoRegItem(Integer idNegativadorMovimentoReg, Integer idDocumentoTipo)
					throws ErroRepositorioException{

		Collection retorno = null;
		// Cria uma sessão com o hibernate
		Session session = HibernateUtil.getSession();
		// Cria a variável que vai conter o hql
		StringBuilder consulta = new StringBuilder();
		try{
			consulta.append("select nmri from NegativadorMovimentoRegItem nmri ")
							.append("inner join fetch nmri.negativadorMovimentoReg  nmrg ")
							.append("inner join fetch nmri.documentoTipo  dctp ").append("inner join fetch nmri.contaGeral  cg ")
							.append(" where nmrg.id = :idNegativadorMovimentoReg ").append(" and dctp.id = :idDocumentoTipo ");
			retorno = session.createQuery(consulta.toString()).setInteger("idNegativadorMovimentoReg", idNegativadorMovimentoReg)
							.setInteger("idDocumentoTipo", idDocumentoTipo).list();

			// Erro no hibernate
		}catch(HibernateException e){
			// Levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// Fecha a sessão com o hibernate
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * @param idImovel
	 * @param idCobrancaAcaoAtividadeCronograma
	 * @param idCobrancaAcaoAtividadeComando
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarNegativacaoImovel(Integer idImovel, Integer idCobrancaAcaoAtividadeCronograma,
					Integer idCobrancaAcaoAtividadeComando) throws ErroRepositorioException{

		Collection retorno = new ArrayList();

		StringBuffer hql = new StringBuffer();
		Session session = HibernateUtil.getSession();
		Query query = null;
		try{

			hql.append(" select negImovel ");
			hql.append(" from gcom.cobranca.NegativacaoImovei negImovel ");

			if(idCobrancaAcaoAtividadeComando != null){
				hql.append(" left join negImovel.cobrancaAcaoAtividadeComando  comando");
			}else if(idCobrancaAcaoAtividadeCronograma != null){
				hql.append(" left join negImovel.cobrancaAcaoAtividadeCronograma  cronograma");
			}
			hql.append(" where negImovel.indicadorExcluido = 2 ");
			hql.append(" and negImovel.dataExclusao is null ");
			hql.append(" and negImovel.imovel.id = ");
			hql.append(idImovel);

			if(idCobrancaAcaoAtividadeComando != null){
				hql.append(" and comando.id = ");
				hql.append(idCobrancaAcaoAtividadeComando);
			}else if(idCobrancaAcaoAtividadeCronograma != null){
				hql.append(" and cronograma.id = ");
				hql.append(idCobrancaAcaoAtividadeCronograma);
			}
			query = session.createQuery(hql.toString());
			retorno = query.list();

			// Erro no hibernate
		}catch(HibernateException e){
			// Levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// Fecha a sessão com o hibernate
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * @param imovel
	 * @param negativador
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarNegativadorMovimentoRegIncluidos(Imovel imovel, Negativador negativador) throws ErroRepositorioException{

		Collection retorno = null;
		Session session = HibernateUtil.getSession();

		StringBuilder consulta = new StringBuilder();
		try{
			consulta.append(" select nmrg");
			consulta.append(" from gcom.cobranca.NegativadorMovimentoReg nmrg ");
			consulta.append(" left join nmrg.cobrancaDebitoSituacao as cdst ");
			consulta.append(" left join fetch nmrg.negativadorMovimento as ngmv ");
			consulta.append(" left join ngmv.negativador neg ");
			consulta.append(" left join neg.cliente c ");
			consulta.append(" left join nmrg.imovel as imov ");
			consulta.append(" left join ngmv.cobrancaAcaoAtividadeComando as cacm ");
			consulta.append(" left join ngmv.cobrancaAcaoAtividadeCronograma as caac ");
			consulta.append(" where nmrg.imovel.id = ");
			consulta.append(imovel.getId());
			consulta.append(" and  nmrg.codigoExclusaoTipo is null");
			consulta.append(" and  ngmv.negativador.id = ");
			consulta.append(negativador.getId());
			/*
			 * consulta.append( " and  ngmv.codigoMovimento= ");
			 * consulta.append( NegativadorMovimento.CODIGO_MOVIMENTO_INCLUSAO);
			 * consulta.append( " and  nmrg.indicadorAceito= ");
			 * consulta.append( NegativadorMovimentoReg.INDICADOR_ACEITO);
			 */

			retorno = session.createQuery(consulta.toString()).list();

		}catch(HibernateException ex){
			ex.printStackTrace();
			throw new ErroRepositorioException(ex, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * 
	 */
	public Collection pesquisarNegativadorMovimentoRegPorConta(Integer idConta, Integer idDocumentoTipo, Integer idNegativador)
					throws ErroRepositorioException{

		Collection retorno = null;
		// Cria uma sessão com o hibernate
		Session session = HibernateUtil.getSession();
		// Cria a variável que vai conter o hql
		StringBuilder consulta = new StringBuilder();
		try{

			/*
			 * consulta.append("select nmri.nmrg from NegativadorMovimentoRegItem nmri ")
			 * .append("inner join fetch nmri.negativadorMovimentoReg  nmrg " )
			 * .append("inner join fetch nmrg.negativadorMovimento  ngmv " )
			 * .append("inner join ngmv.negativador  ngtv " )
			 * .append("inner join nmri.documentoTipo  dctp " )
			 * .append("inner join nmri.contaGeral  cg " )
			 * //.append(" left join ngmv.cobrancaAcaoAtividadeComando as cacm ")
			 * //.append(" left join ngmv.cobrancaAcaoAtividadeCronograma as caac ")
			 * .append(" where cg.id = :idConta ")
			 * .append(" and dctp.id = :idDocumentoTipo ")
			 * .append(" and ngtv.id = :idNegativador ");
			 * retorno = (NegativadorMovimentoReg) session.createQuery(consulta.toString())
			 * .setInteger("idConta", idConta)
			 * .setInteger("idDocumentoTipo", idDocumentoTipo)
			 * .setInteger("idNegativador", idNegativador)
			 * .uniqueResult();
			 */

			/*
			 * select * from negativador_movimento_reg nmrg
			 * inner join negativador_movimento ngmv on ngmv.ngmv_id = nmrg.ngmv_id
			 * inner join negativador negt on negt.negt_id = ngmv.negt_id
			 * where nmrg.nmrg_id in (select nmri.nmrg_id from negativador_movimento_reg_item nmri
			 * inner join documento_tipo dotp on dotp.dotp_id = nmri.dotp_id
			 * inner join conta_geral cnta on cnta.cnta_id = nmri.cnta_id
			 * where cnta.cnta_id=24179245 and dotp.dotp_id=1 )
			 * and negt.negt_id=2
			 */

			consulta.append("select nmrg from NegativadorMovimentoReg nmrg ").append("inner join fetch nmrg.negativadorMovimento  ngmv ")
							.append("inner join ngmv.negativador  ngtv ").append(" where nmrg.id in ( ")
							.append(" select nmri.negativadorMovimentoReg.id from NegativadorMovimentoRegItem nmri ")
							.append(" inner join nmri.documentoTipo dctp ").append(" inner join nmri.contaGeral  cg ")
							.append(" where cg.id = :idConta ").append(" and dctp.id = :idDocumentoTipo ").append(" ) ")
							.append(" and ngtv.id = :idNegativador ").append(" and nmrg.indicadorAceito = :indicadorAceito ");
			retorno = session.createQuery(consulta.toString()).setInteger("idConta", idConta)
							.setInteger("idDocumentoTipo", idDocumentoTipo).setInteger("idNegativador", idNegativador)
							.setInteger("indicadorAceito", ConstantesSistema.SIM).list();

			// Erro no hibernate
		}catch(HibernateException e){
			// Levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// Fecha a sessão com o hibernate
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * Consultar o Motivo da Exclusao do Negativador
	 * 
	 * @author Yara Taciane
	 * @date 22/07/2008
	 * @param Integer
	 * @return Object[]
	 * @throws ErroRepositorioException
	 */

	public NegativadorExclusaoMotivo pesquisarNegativadorMotivoExclusao(Integer idCobrancaDebitoSituacao, Integer idNegativador)
					throws ErroRepositorioException{

		NegativadorExclusaoMotivo retorno = null;
		Session session = HibernateUtil.getSession();
		try{

			StringBuffer hql = new StringBuffer();
			hql.append("select nemt ");
			hql.append("from NegativadorExclusaoMotivo nemt ");
			hql.append("where nemt.cobrancaDebitoSituacao.id = :idCobrancaDebitoSituacao ");
			hql.append("  and nemt.negativador.id = :idNegativador ");

			retorno = (NegativadorExclusaoMotivo) session.createQuery(hql.toString())
							.setInteger("idCobrancaDebitoSituacao", idCobrancaDebitoSituacao).setInteger("idNegativador", idNegativador)
							.uniqueResult();
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate getDadosContratoNegativador");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * Consultar Negativador Movimento Retorno Motivo
	 * 
	 * @author Yara Taciane
	 * @date 22/07/2008
	 * @param Integer
	 * @return Object[]
	 * @throws ErroRepositorioException
	 */

	public NegativadorRetornoMotivo pesquisarNegativadorRetornoMotivo(Integer codigoRetorno, Integer idNegativador)
					throws ErroRepositorioException{

		NegativadorRetornoMotivo retorno = null;
		Session session = HibernateUtil.getSession();
		try{

			String hql = " select nrmt" + " from NegativadorRetornoMotivo nrmt"
							+ " where nrmt.codigoRetornoMotivo = :codigoRetorno and nrmt.negativador.id = :idNegativador";

			retorno = (NegativadorRetornoMotivo) session.createQuery(hql).setInteger("codigoRetorno", codigoRetorno)
							.setInteger("idNegativador", idNegativador).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate NegativadorMovimentoRegRetMot");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	public NegativadorMovimentoReg pesquisarRegistroTipoConsumidor(Integer numeroRegistro, Integer idNegMovimento)
					throws ErroRepositorioException{

		NegativadorMovimentoReg retorno = null;
		Session session = HibernateUtil.getSession();
		try{

			String hql = " select nmrg" + " from NegativadorMovimentoReg nmrg" + " where nmrg.numeroRegistro = :numeroRegistro"
							+ " and nmrg.negativadorMovimento.id = :idNegMovimento";

			retorno = (NegativadorMovimentoReg) session.createQuery(hql).setInteger("numeroRegistro", numeroRegistro)
							.setInteger("idNegMovimento", idNegMovimento).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate getDadosContratoNegativador");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * Pesquisar quantidade de negativações ativas
	 * 
	 * @author Hebert Falcão
	 * @date 21/06/2011
	 */
	public Integer pesquisarQuantidadeNegativacoesAtivas(Integer idImovel) throws ErroRepositorioException{

		Integer retorno = null;

		StringBuffer sql = new StringBuffer();

		Session session = HibernateUtil.getSession();

		try{
			sql.append("select count(*) as qtde ");
			sql.append("from negativacao_imoveis ni ");
			sql.append("inner join negativador_movimento_reg nmr on (ni.imov_id = nmr.imov_id and nmr.nmrg_cdexclusaotipo is null) ");
			sql.append("inner join negativador_movimento nm on (nmr.ngmv_id = nm.ngmv_id and ");
			sql.append("				                         (ni.cacm_id = nm.cacm_id or ni.caac_id = nm.caac_id or ni.ngcm_id = nm.ngcm_id)) ");
			sql.append("where ngim_icexcluido = " + ConstantesSistema.NAO);
			sql.append("  and ngim_dtexclusao is null ");
			sql.append("  and ni.imov_id = " + idImovel);
			sql.append("  and nm.ngmv_dtretorno is not null ");
			sql.append("  and exists (select * ");
			sql.append("              from negativador_movimento_reg nmr ");
			sql.append("              inner join negativador_movimento nm on (nmr.ngmv_id = nm.ngmv_id) ");
			sql.append("              where ni.imov_id = nmr.imov_id ");
			sql.append("                and nmrg_icaceito = " + ConstantesSistema.SIM);
			sql.append("                and (ni.cacm_id = nm.cacm_id or ni.caac_id = nm.caac_id or ni.ngcm_id = nm.ngcm_id)) ");

			retorno = (Integer) session.createSQLQuery(sql.toString()).addScalar("qtde", Hibernate.INTEGER).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * @param idImovel
	 * @param idCobrancaAcaoAtividadeCronograma
	 * @param idCobrancaAcaoAtividadeComando
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Boolean verificaNegativacaoImovel(Integer idImovel) throws ErroRepositorioException{

		Boolean retorno = false;
		Integer quantidade = 0;

		StringBuffer hql = new StringBuffer();
		Session session = HibernateUtil.getSession();
		Query query = null;
		try{

			hql.append(" select count(negImovel.id) ");
			hql.append(" from gcom.cobranca.NegativacaoImovei negImovel ");
			hql.append(" left join negImovel.cobrancaAcaoAtividadeComando  comando");
			hql.append(" left join negImovel.cobrancaAcaoAtividadeCronograma  cronograma");
			hql.append(" where negImovel.indicadorExcluido <> 1 ");
			hql.append(" and negImovel.dataExclusao is null ");
			hql.append(" and negImovel.imovel.id = ");
			hql.append(idImovel);

			query = session.createQuery(hql.toString());
			quantidade = ((Number) query.uniqueResult()).intValue();
			if(quantidade > 0){
				retorno = true;
			}

			// Erro no hibernate
		}catch(HibernateException e){
			// Levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// Fecha a sessão com o hibernate
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [FS0019] - Verificar existência de negativação para o cliente-imóvel
	 * Caso o cliente do imóvel esteja em processo de negativação (existe ocorrência na tabela
	 * NEGATIVADOR_MOVIMENTO_REG com IMOV_ID=Id do imóvel informado e CLIE_ID=Id do cliente
	 * selecionado para remoção e NMRG_ICACEITO com o valor 1 ou nulo e NMRG_CDEXCLUSAOTIPO com o
	 * valor nulo e NMRG_IDREGINCLUSAO com o valor nulo), exibir a mensagem “ATENÇÃO: O cliente
	 * <<CLIE_NMCLIENTE com CLIE_ID=Id do cliente selecionado para remoção>>, vinculado ao imóvel
	 * <<IMOV_ID>>.
	 * 
	 * @author Isaac Silva
	 * @date 03/08/2011
	 * @param idImovel
	 * @param idCliente
	 * @return
	 * @throws ErroRepositorioException
	 */
	public boolean verificarExistenciaNegativacaClienteImovel(Integer idImovel, Integer idCliente) throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();
		StringBuffer consulta = new StringBuffer("");

		try{

			consulta.append("select nmrg_id ");
			consulta.append("from negativador_movimento_reg ");
			consulta.append("where  ");
			consulta.append("imov_id = :idImovel ");
			consulta.append("and clie_id = :idCliente ");
			consulta.append("and (nmrg_icaceito = :indicadorAceito ");
			consulta.append("or nmrg_icaceito is null) ");
			consulta.append("and nmrg_cdexclusaotipo is null ");
			consulta.append("and nmrg_idreginclusao is null ");

			retorno = (Collection) session.createSQLQuery(consulta.toString()).addScalar("nmrg_id", Hibernate.INTEGER)
							.setInteger("idImovel", idImovel).setInteger("idCliente", idCliente)
							.setShort("indicadorAceito", ConstantesSistema.SIM).list();

			if(Util.isVazioOrNulo(retorno)){
				return false;
			}else{
				return true;
			}

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
	}

	/**
	 * [UC0937] Obter Itens de Negativação Associados à Conta
	 * pesquisa a partir da tabela NEGATIVADOR_MOVIMENTO_REG_ITEM
	 * com NMRI_ICSITDEFINITIVA=2 E CNTA_ID com o valor diferente de nulo
	 * E ( (CNTA_ID=CNTA_ID da tabela CONTA com IMOV_ID=Id do Imóvel recebido
	 * e CNTA_AMREFERENCIACONTA=Referência recebida)
	 * E NMRG_ID=NMRG_ID da tabela NEGATIVADOR_MOVIMENTO_REG com IMOV_ID=Id do Imóvel recebido
	 * 
	 * @author Vivianne Sousa
	 * @author isilva
	 * @data 10/09/2009
	 * @data 04/08/2011
	 * @param idImovel
	 * @param referencia
	 * @return id do NegativadorMovimentoRegItem
	 */
	public Collection obterNegativadorMovimentoRegItemAssociadosAConta(Integer idImovel, Integer referencia,
					Boolean ignorarSituacaoDefinitiva) throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta = null;

		try{
			consulta = "select nmri.nmri_id as idNmri " + " from negativador_movimento_reg_item nmri "
							+ " inner join negativador_movimento_reg nmrg on nmrg.nmrg_id = nmri.nmrg_id "
							+ " inner join conta_geral cntaGeral on cntaGeral.cnta_id = nmri.cnta_id "
							+ " inner join conta cnta on cntaGeral.cnta_id = cnta.cnta_id " + " where cnta.imov_id = :idImovel "
							+ " and cnta.cnta_amreferenciaconta = :referencia " + " and nmrg.imov_id = :idImovel "
							// alterado por Vivianne Sousa - 27/10/2009 - analista:Fatima Sampaio
							+ " and (nmrg_icaceito = :indicadorAceito or nmrg_icaceito is null)";

			if(ignorarSituacaoDefinitiva){
				retorno = (Collection) session.createSQLQuery(consulta).addScalar("idNmri", Hibernate.INTEGER)
								.setInteger("idImovel", idImovel).setInteger("referencia", referencia)
								.setShort("indicadorAceito", ConstantesSistema.SIM).list();
			}else{
				consulta = consulta + " and nmri_icsitdefinitiva = :indicadorSituacaoDefinitiva ";
				retorno = (Collection) session.createSQLQuery(consulta).addScalar("idNmri", Hibernate.INTEGER)
								.setShort("indicadorSituacaoDefinitiva", ConstantesSistema.NAO).setInteger("idImovel", idImovel)
								.setInteger("referencia", referencia).setShort("indicadorAceito", ConstantesSistema.SIM).list();
			}

			// long t2 = System.currentTimeMillis();
			// System.out.println("[UC0937]obterNegativadorMovimentoRegItemAssociadosAConta " + ( t2
			// - t1));
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0937] Obter Itens de Negativação Associados à Conta
	 * pesquisa a partir da tabela NEGATIVADOR_MOVIMENTO_REG_ITEM
	 * com NMRI_ICSITDEFINITIVA=2 E CNTA_ID com o valor diferente de nulo
	 * E ((CNTA_ID=CNTA_ID da tabela CONTA_HISTORICO com IMOV_ID=Id do Imóvel recebido
	 * e CNHI_AMREFERENCIACONTA=Referência recebida) )
	 * E NMRG_ID=NMRG_ID da tabela NEGATIVADOR_MOVIMENTO_REG com IMOV_ID=Id do Imóvel recebido
	 * 
	 * @author Vivianne Sousa
	 * @author isilva
	 * @data 10/09/2009
	 * @data 04/08/2011
	 * @param idImovel
	 * @param referencia
	 * @return id do NegativadorMovimentoRegItem
	 */
	public Collection obterNegativadorMovimentoRegItemAssociadosAContaHistorico(Integer idImovel, Integer referencia,
					Boolean ignorarSituacaoDefinitiva) throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta = null;

		try{
			consulta = "select nmri.nmri_id as idNmri  " + " from negativador_movimento_reg_item nmri "
							+ " inner join negativador_movimento_reg nmrg on nmrg.nmrg_id = nmri.nmrg_id "
							+ " inner join conta_geral cntaGeral on cntaGeral.cnta_id = nmri.cnta_id "
							+ " inner join conta_historico cntaHist on cntaGeral.cnta_id = cntaHist.cnta_id "
							+ " where cntaHist.imov_id = :idImovel " + " and cntaHist.cnhi_amreferenciaconta = :referencia "
							+ " and nmrg.imov_id = :idImovel "
							// alterado por Vivianne Sousa - 27/10/2009 - analista:Fatima Sampaio
							+ " and (nmrg_icaceito = :indicadorAceito or nmrg_icaceito is null) ";

			if(ignorarSituacaoDefinitiva){
				retorno = (Collection) session.createSQLQuery(consulta).addScalar("idNmri", Hibernate.INTEGER)
								.setInteger("idImovel", idImovel).setInteger("referencia", referencia)
								.setShort("indicadorAceito", ConstantesSistema.SIM).list();
			}else{
				consulta = consulta + " and nmri_icsitdefinitiva = :indicadorSituacaoDefinitiva ";
				retorno = (Collection) session.createSQLQuery(consulta).addScalar("idNmri", Hibernate.INTEGER)
								.setShort("indicadorSituacaoDefinitiva", ConstantesSistema.NAO).setInteger("idImovel", idImovel)
								.setInteger("referencia", referencia).setShort("indicadorAceito", ConstantesSistema.SIM).list();
			}

			// long t2 = System.currentTimeMillis();
			// System.out.println("[UC0937]obterNegativadorMovimentoRegItemAssociadosAContaHistorico "
			// + ( t2 - t1));
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UCXXXX]
	 * 
	 * @author Genival Barbosa
	 * @data 03/11/2011
	 * @param idImovel
	 * @param referencia
	 * @return id do NegativadorMovimentoRegItem
	 */
	public Collection obterNegativadorMovimentoRegItemAssociadosAContaSitDefinit(Integer idImovel, Integer referencia)
					throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta = null;

		try{
			consulta = "select nmri.nmri_id as idNmri " + " from negativador_movimento_reg_item nmri "
							+ " inner join negativador_movimento_reg nmrg on nmrg.nmrg_id = nmri.nmrg_id "
							+ " inner join conta_geral cntaGeral on cntaGeral.cnta_id = nmri.cnta_id "
							+ " inner join conta cnta on cntaGeral.cnta_id = cnta.cnta_id " + " where cnta.imov_id = :idImovel "
							+ " and cnta.cnta_amreferenciaconta = :referencia " + " and nmrg.imov_id = :idImovel "
							+ " and (nmrg_icaceito = :indicadorAceito or nmrg_icaceito is null)";

			retorno = (Collection) session.createSQLQuery(consulta).addScalar("idNmri", Hibernate.INTEGER).setInteger("idImovel", idImovel)
							.setInteger("referencia", referencia).setShort("indicadorAceito", ConstantesSistema.SIM).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UCXXXX]
	 * 
	 * @author Genival Barbosa
	 * @data 03/11/2011
	 * @param idImovel
	 * @param referencia
	 * @return id do NegativadorMovimentoRegItem
	 */
	public Collection obterNegativadorMovimentoRegItemAssociadosAContaHistoricoSitDefinit(Integer idImovel, Integer referencia)
					throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta = null;

		try{
			consulta = "select nmri.nmri_id as idNmri  " + " from negativador_movimento_reg_item nmri "
							+ " inner join negativador_movimento_reg nmrg on nmrg.nmrg_id = nmri.nmrg_id "
							+ " inner join conta_geral cntaGeral on cntaGeral.cnta_id = nmri.cnta_id "
							+ " inner join conta_historico cntaHist on cntaGeral.cnta_id = cntaHist.cnta_id "
							+ " where cntaHist.imov_id = :idImovel " + " and cntaHist.cnhi_amreferenciaconta = :referencia "
							+ " and nmrg.imov_id = :idImovel " + " and (nmrg_icaceito = :indicadorAceito or nmrg_icaceito is null) ";

			retorno = (Collection) session.createSQLQuery(consulta).addScalar("idNmri", Hibernate.INTEGER).setInteger("idImovel", idImovel)
							.setInteger("referencia", referencia).setShort("indicadorAceito", ConstantesSistema.SIM).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * Pesquisar NegativadorMovimentoRegItem filtrando por uma coleção de Ids
	 * 
	 * @author Isaac Silva
	 * @date 09/08/2011
	 * @param listaIdentificadoresNMRI
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<NegativadorMovimentoRegItem> pesquisarNegativadorMovimentoRegItensPorIds(Collection<Integer> listaIdentificadoresNMRI)
					throws ErroRepositorioException{

		Query query = null;
		Session session = HibernateUtil.getSession();
		Collection<NegativadorMovimentoRegItem> retorno = null;
		Map parameters = new HashMap();
		StringBuffer hql = new StringBuffer();

		try{

			hql.append("select nmri ");
			hql.append("from NegativadorMovimentoRegItem nmri ");
			hql.append("inner join fetch nmri.negativadorMovimentoReg nmrg ");
			hql.append("where nmri.id in (:ids) ");

			parameters.put("ids", listaIdentificadoresNMRI);

			query = session.createQuery(hql.toString());

			Set set = parameters.keySet();
			Iterator iterMap = set.iterator();

			while(iterMap.hasNext()){
				String key = (String) iterMap.next();

				if(parameters.get(key) instanceof Integer[]){
					Integer[] collection = (Integer[]) parameters.get(key);
					query.setParameterList(key, collection);
				}else if(parameters.get(key) instanceof Collection){
					Collection collection = (ArrayList) parameters.get(key);
					query.setParameterList(key, collection);
				}else if(parameters.get(key) instanceof List){
					List list = (List) parameters.get(key);
					query.setParameterList(key, list);
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
	 * Valida se o imóvel está negativado
	 * 
	 * @author Isaac Silva
	 * @date 11/08/2011
	 * @param idImovel
	 * @param idCobrancaAcao
	 * @return
	 * @throws ErroRepositorioException
	 */
	public boolean isImovelNegativado(Integer idImovel, Integer idCobrancaAcao) throws ErroRepositorioException{

		boolean retorno = false;

		StringBuffer sql = new StringBuffer();

		Session session = HibernateUtil.getSession();

		try{
			sql.append(" select count(ngm.nmrg_id) as qtde ");
			sql.append(" from negativador_movimento_reg ngm ");
			sql.append(" where ");
			sql.append(" ngm.imov_id = :idImovel ");
			sql.append(" and ( ngm.nmrg_icaceito = :indicadorAceito or ngm.nmrg_icaceito is null ) ");
			sql.append(" and ngm.nmrg_cdexclusaotipo is null ");
			sql.append(" and ngm.nmrg_idreginclusao is null ");
			sql.append(" and (select nm.negt_id from negativador_movimento nm where nm.ngmv_id = ngm.ngmv_id) ");
			sql.append(" = (select ca.negt_id  from cobranca_acao ca where ca.cbac_id = :idCobrancaAcao) ");

			Integer retornoConsulta = (Integer) session.createSQLQuery(sql.toString()).addScalar("qtde", Hibernate.INTEGER)
							.setInteger("idImovel", idImovel).setShort("indicadorAceito", ConstantesSistema.SIM)
							.setInteger("idCobrancaAcao", idCobrancaAcao).uniqueResult();

			if(retornoConsulta.intValue() > 0){
				return true;
			}

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * @autor Bruno Ferreira dos Santos
	 * @date 11/10/2011
	 * @return
	 * @throws ControladorException
	 */
	public Collection consultarNegativacoesDeterminarConfirmacao() throws ErroRepositorioException{

		Collection retorno = new Vector();
		Session session = HibernateUtil.getSession();

		StringBuilder consulta = new StringBuilder();
		try{

			consulta.append(" SELECT NMRG.id,                                                                               ");
			consulta.append("   NMRG.imovel.id,                                                                             ");
			consulta.append("   NMRG.codigoExclusaoTipo,                                                                    ");
			consulta.append("   NGMV.id,                                                                                    ");
			consulta.append("   NGMV.cobrancaAcaoAtividadeComando.id,                                                       ");
			consulta.append("   NGMV.cobrancaAcaoAtividadeCronograma.id,                                                    ");
			consulta.append("   NGMV.dataProcessamentoEnvio,                                                                ");
			consulta.append("   NMRG.codigoExclusaoTipo,                                                                    ");
			consulta.append("   NGIM.id,                                                                                    ");
			consulta.append("   NGIM.dataConfirmacao,                                                                       ");
			consulta.append("   NGIM.dataExclusao,                                                                          ");
			consulta.append("   NGCN.id,                                                                                    ");
			consulta.append("   NGCN.numeroPrazoInclusao,                                                                   ");
			consulta.append("   NGMV.negativador.id,                                                                        ");
			consulta.append("   NMRG.cliente.id,                                                                            ");
			consulta.append("   NMRG.numeroContrato                                                                         ");
			consulta.append(" FROM NegativadorMovimentoReg NMRG,                                                            ");
			consulta.append("   NegativacaoImovei NGIM,                                                                     ");
			consulta.append("   NegativadorContrato NGCN                                                                    ");
			consulta.append(" INNER JOIN NMRG.negativadorMovimento NGMV                                                     ");
			consulta.append(" WHERE NMRG.imovel.id                              IS NOT NULL                                 ");
			consulta.append(" AND NMRG.indicadorAceito                           = 1                                         ");
			consulta.append(" AND ( (NMRG.codigoExclusaoTipo                    IS NULL                                     ");
			consulta.append(" AND cast(extract(day from (:data - NGMV.dataProcessamentoEnvio)) as short) > NGCN.numeroPrazoInclusao) ");
			consulta.append(" OR (NMRG.codigoExclusaoTipo                       IS NOT NULL                                 ");
			consulta.append("  AND CASE ");
			consulta.append("  			WHEN NGIM.dataExclusao is null THEN ");
			consulta.append("  				0 ");
			consulta.append("	    	ELSE ");
			consulta.append("	    		cast(extract(day from (NGIM.dataExclusao - NGMV.dataProcessamentoEnvio)) as short) ");
			consulta.append("	   END > NGCN.numeroPrazoInclusao) ) ");
			consulta.append(" AND NGMV.codigoMovimento                           = 1                                         ");
			consulta.append(" AND (NGIM.cobrancaAcaoAtividadeComando.id          = NGMV.cobrancaAcaoAtividadeComando.id      ");
			consulta.append(" OR NGIM.cobrancaAcaoAtividadeCronograma.id         = NGMV.cobrancaAcaoAtividadeCronograma.id)  ");
			consulta.append(" AND NGIM.imovel.id                                 = NMRG.imovel.id                            ");
			consulta.append(" AND NGIM.dataConfirmacao                          IS NULL                                     ");
			consulta.append(" AND NGCN.negativador.id                            = NGMV.negativador.id                       ");
			consulta.append(" AND (NGCN.dataContratoEncerramento                IS NULL                                     ");
			consulta.append(" OR NGCN.dataContratoFim                           >= :data1)                                  ");

			retorno = session.createQuery(consulta.toString()).setDate("data", new Date()).setDate("data1", new Date()).list();

		}catch(HibernateException ex){
			ex.printStackTrace();
			throw new ErroRepositorioException(ex, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * @param idImovel
	 * @param idNegativador
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarNegativacaoASerExcluidaImovel(Integer idImovel, Integer idNegativador) throws ErroRepositorioException{

		Collection retorno = new ArrayList();

		StringBuffer hql = new StringBuffer();
		Session session = HibernateUtil.getSession();
		Query query = null;
		try{

			hql.append(" select negativadorMovimentoReg ");
			hql.append(" from gcom.cobranca.NegativadorMovimentoReg negativadorMovimentoReg, ");
			hql.append(" gcom.cobranca.NegativadorMovimento negativadorMovimento, ");
			hql.append(" gcom.cobranca.NegativacaoImovei negativacaoImovei ");
			hql.append(" where negativadorMovimentoReg.imovel.id = ");
			hql.append(idImovel);
			hql.append(" and negativadorMovimentoReg.negativadorMovimentoRegInclusao is null ");
			hql.append(" and negativadorMovimentoReg.indicadorAceito = ");
			hql.append(NegativadorMovimentoReg.INDICADOR_ACEITO);
			hql.append(" and negativadorMovimentoReg.codigoExclusaoTipo is null ");
			hql.append(" and negativadorMovimentoReg.negativadorMovimento.id = negativadorMovimento.id ");
			hql.append(" and negativadorMovimento.negativador.id = ");
			hql.append(idNegativador);
			hql.append(" and negativadorMovimentoReg.imovel.id = negativacaoImovei.imovel.id ");
			hql.append(" and negativacaoImovei.indicadorExcluido = ");
			hql.append(ConstantesSistema.NAO);
			hql.append(" and ( ");
			hql.append(" negativacaoImovei.cobrancaAcaoAtividadeComando.id = negativadorMovimento.cobrancaAcaoAtividadeComando.id or ");
			hql.append(" negativacaoImovei.cobrancaAcaoAtividadeCronograma.id = negativadorMovimento.cobrancaAcaoAtividadeCronograma.id ) ");

			query = session.createQuery(hql.toString());
			retorno = query.list();

			// Erro no hibernate
		}catch(HibernateException e){
			// Levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// Fecha a sessão com o hibernate
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * @param idNegativadorMovimentoReg
	 * @param idCobrancaDebitoSituacao
	 * @return
	 * @throws ErroRepositorioException
	 */

	public Collection pesquisarNegativadorMovimentoRegItemPorNegMovimentoRegCobrancaSituacao(Integer idNegativadorMovimentoReg,
					Integer idCobrancaDebitoSituacao) throws ErroRepositorioException{

		Collection retorno = null;
		// Cria uma sessão com o hibernate
		Session session = HibernateUtil.getSession();
		// Cria a variável que vai conter o hql
		StringBuilder consulta = new StringBuilder();
		try{
			consulta.append("select nmri from NegativadorMovimentoRegItem nmri ")
							.append("inner join fetch nmri.negativadorMovimentoReg  nmrg ")
							.append("inner join fetch nmri.cobrancaDebitoSituacao  cdst ")
							.append(" where nmrg.id = :idNegativadorMovimentoReg and cdst.id = :idCobrancaDebitoSituacao");
			retorno = session.createQuery(consulta.toString()).setInteger("idNegativadorMovimentoReg", idNegativadorMovimentoReg)
							.setInteger("idCobrancaDebitoSituacao", idCobrancaDebitoSituacao).list();

			// Erro no hibernate
		}catch(HibernateException e){
			// Levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// Fecha a sessão com o hibernate
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * Consulta usada no fluxoprincipal step 1 do caso de uso [UC0688]-Gerar Resumo Diario de
	 * Negativacao
	 * 
	 * @autor Genival Barbosa
	 * @date 26/10/2011
	 * @return coleção de negativadorMovimentoReg
	 * @throws ControladorException
	 */
	public Collection pesquisarNegativadorMovimentoRegParaGerarResumoDiarioNegativacao() throws ErroRepositorioException{

		Collection retorno = null;
		// Cria uma sessão com o hibernate
		Session session = HibernateUtil.getSession();
		// Cria a variável que vai conter o hql
		StringBuilder consulta = new StringBuilder();
		try{
			consulta.append(" select nmrg ");
			consulta.append(" from gcom.cobranca.NegativadorMovimentoReg nmrg ");
			consulta.append(" inner join fetch nmrg.negativadorMovimento  ngmv ");
			consulta.append(" where ");
			consulta.append(" nmrg.indicadorAceito = ");
			consulta.append(NegativadorMovimentoReg.INDICADOR_ACEITO);
			consulta.append(" and nmrg.imovel is not null ");
			consulta.append(" and nmrg.codigoExclusaoTipo is null ");
			consulta.append(" and nmrg.indicadorItemAtualizado = ");
			consulta.append(ConstantesSistema.SIM);
			consulta.append(" and ngmv.codigoMovimento = ");
			consulta.append(NegativadorMovimento.CODIGO_MOVIMENTO_INCLUSAO);

			retorno = session.createQuery(consulta.toString()).list();

			// Erro no hibernate
		}catch(HibernateException e){
			// Levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// Fecha a sessão com o hibernate
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	public Date pesquisaMaiorDataVencimentoDosDebitos(int idImovel) throws ErroRepositorioException{

		Date retorno;
		Session session = HibernateUtil.getSession();
		String consulta = null;
		try{
			consulta = "select max(cnta_dtvencimentoconta) as cnta_dtvencimentoconta from conta "
							+ "where cnta_id in (select cnta_id from cobranca_documento_item  where cbdo_id = (select cbdo_id from cobranca_documento where imov_id=:idImovel))";

			retorno = ((Date) session.createSQLQuery(consulta.toString()).setInteger("idImovel", idImovel).uniqueResult());

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
	 * Retorna o ImovelCobrancaSituacao pelo imovel do NegativadorMovimentoReg
	 * [UC0693] Gerar Relatório Acompanhamaneto de Clientes Negativados
	 * 
	 * @author Yara Taciane
	 * @date 17/03/2008
	 */
	public Collection getNegativadorMovimentoRegSpcBoaVista(NegativadorMovimento negativadorMovimento, Integer numeroRegistro)
					throws ErroRepositorioException{

		Collection retorno = null;
		Session session = HibernateUtil.getSession();

		try{

			String hql = " select nmrg" + " from gcom.cobranca.NegativadorMovimentoReg nmrg "
							+ " inner join fetch nmrg.negativadorMovimento nmrv " + " where nmrv.id = :idNegativadorMovimento "
							+ " and   nmrg.numeroRegistro = :numeroRegistro ";

			retorno = (Collection) session.createQuery(hql).setInteger("idNegativadorMovimento", negativadorMovimento.getId())
							.setInteger("numeroRegistro", numeroRegistro).list();

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
