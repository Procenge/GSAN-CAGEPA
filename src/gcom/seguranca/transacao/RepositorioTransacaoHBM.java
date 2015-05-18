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

package gcom.seguranca.transacao;

import gcom.seguranca.acesso.FiltroOperacaoOrdemExibicao;
import gcom.seguranca.acesso.OperacaoOrdemExibicao;
import gcom.util.ControladorException;
import gcom.util.ErroRepositorioException;
import gcom.util.HibernateUtil;
import gcom.util.Util;
import gcom.util.filtro.PersistenciaUtil;

import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;

import org.hibernate.*;
import org.hibernate.exception.GenericJDBCException;

/**
 * < <Descrição da Classe>>
 * 
 * @author Administrador
 * @created 22 de Julho de 2005
 */
public class RepositorioTransacaoHBM
				implements IRepositorioTransacao {

	private static IRepositorioTransacao instancia;

	/**
	 * Constructor for the RepositorioClienteTipoHBM object
	 */
	public RepositorioTransacaoHBM() {

	}

	/**
	 * Retorna o valor de instancia
	 * 
	 * @return O valor de instancia
	 */
	public static IRepositorioTransacao getInstancia(){

		if(instancia == null){
			instancia = new RepositorioTransacaoHBM();
		}

		return instancia;
	}

	/**
	 * Método que consulta os usuario alteracao de uma determinada operacao com
	 * as restricoes passadas
	 * 
	 * @param idUsuarioAcao
	 * @param idOperacao
	 * @param idUsuario
	 * @param dataInicial
	 * @param dataFinal
	 * @param horaInicial
	 * @param horaFinal
	 * @param idTabela
	 * @param idTabelaColuna
	 * @param id1
	 * @return
	 * @throws ErroRepositorioException
	 * @author Rômulo Aurélio / Rafael Correa
	 */
	public Integer pesquisarUsuarioAlteracaoDasOperacoesEfetuadasHqlCount(Integer idUsuarioAcao, String[] idOperacoes, String idUsuario,
					Date dataInicial, Date dataFinal, Date horaInicial, Date horaFinal, Hashtable<Integer, String> argumentos, Integer id1)
					throws ErroRepositorioException{

		// cria a coleção de retorno
		Integer retorno = null;
		String consulta;
		// obtém a sessão
		Session session = HibernateUtil.getSession();

		try{

			// pesquisa a coleção de atividades e atribui a variável "retorno"
			consulta = " select count(distinct operacaoEfetuada.id)  "
							+ " from OperacaoEfetuada as operacaoEfetuada "
							+ " LEFT join operacaoEfetuada.argumento as argu " //
							+ " LEFT join operacaoEfetuada.usuarioAlteracoes as usAlt "
							+ " LEFT join operacaoEfetuada.tabelaLinhaAlteracoes as tabLinAlt "
							+ " LEFT join tabLinAlt.tabelaLinhaColunaAlteracao as tabLinColAlt "
							+ " LEFT join tabLinColAlt.tabelaColuna as tabCol " //
							+ " LEFT join tabCol.tabela as tab " //
							+ " LEFT join usAlt.usuario as usuario " //
							+ " LEFT join operacaoEfetuada.operacao as operacao " //
							+ " LEFT OUTER join operacao.argumentoPesquisa as argumento " //
							+ " LEFT OUTER join argumento.tabela as argTab ";

			consulta += criarCondicionaisUsuarioAlteracaoDasOperacoesEfetuadas(idUsuarioAcao, idOperacoes, idUsuario, dataInicial,
							dataFinal, horaInicial, horaFinal, argumentos, id1);

			Query query = session.createQuery(consulta).setMaxResults(1);

			this.ajustarCondicoesUsuarioAlteracaoDasOperacoesEfetuadas(query, dataInicial, dataFinal, horaInicial, horaFinal);

			retorno = ((Number) query.uniqueResult()).intValue();

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

	public Collection pesquisarUsuarioAlteracaoDasOperacoesEfetuadasHql(Integer idUsuarioAcao, String[] idOperacoes, String idUsuario,
					Date dataInicial, Date dataFinal, Date horaInicial, Date horaFinal, Hashtable<Integer, String> argumentos, Integer id1,
					Integer numeroPagina) throws ErroRepositorioException{

		// cria a coleção de retorno
		Collection retorno = null;
		String consulta;
		// obtém a sessão
		Session session = HibernateUtil.getSession();

		try{

			// pesquisa a coleção de atividades e atribui a variável "retorno"
			consulta = " select distinct operacaoEfetuada " //
							+ " from OperacaoEfetuada as operacaoEfetuada "
							+ " LEFT join fetch operacaoEfetuada.argumento as argu "
							+ " LEFT join operacaoEfetuada.usuarioAlteracoes as usAlt "
							+ " LEFT join operacaoEfetuada.tabelaLinhaAlteracoes as tabLinAlt "
							+ " LEFT join tabLinAlt.tabelaLinhaColunaAlteracao as tabLinColAlt "
							+ " LEFT join tabLinColAlt.tabelaColuna as tabCol " //
							+ " LEFT join tabCol.tabela as tab " //
							+ " LEFT join usAlt.usuario as usuario " //
							+ " LEFT join fetch operacaoEfetuada.operacao as operacao " //
							+ " LEFT OUTER join operacao.argumentoPesquisa as argumento " //
							+ " LEFT OUTER join argumento.tabela as argTab ";

			consulta += criarCondicionaisUsuarioAlteracaoDasOperacoesEfetuadas(idUsuarioAcao, idOperacoes, idUsuario, dataInicial,
							dataFinal, horaInicial, horaFinal, argumentos, id1);

			Query query = session.createQuery(consulta).setFirstResult(10 * numeroPagina).setMaxResults(10);

			this.ajustarCondicoesUsuarioAlteracaoDasOperacoesEfetuadas(query, dataInicial, dataFinal, horaInicial, horaFinal);

			retorno = query.list();

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

	public Collection pesquisarEntidadeOperacoesEfetuadasHql(Integer chaveEntidade, Integer numeroPagina) throws ErroRepositorioException{

		// cria a coleção de retorno
		Collection retorno = null;
		String consulta;
		// obtém a sessão
		Session session = HibernateUtil.getSession();

		try{
			consulta = " select  tbLinhaColunaAlteracao.id, "
							+ // 0

							" operacaoEfetuada.ultimaAlteracao, "
							+ // 1

							" operacaoEfetuada.operacao.descricao, "
							+ // 2

							" usuarioAlteracao.usuario.nomeUsuario, "
							+ // 3

							" tbLinhaColunaAlteracao.tabelaLinhaAlteracao.tabela.descricaoTabela, "
							+ // 4

							" tbLinhaColunaAlteracao.tabelaLinhaAlteracao.alteracaoTipo.descricao, "
							+ // 5

							" tbLinhaColunaAlteracao.tabelaColuna.descricaoColuna, "
							+ // 6

							" tbLinhaColunaAlteracao.conteudoColunaAnterior, "
							+ // 7

							" tbLinhaColunaAlteracao.conteudoColunaAtual "
							+ // 8

							" from   OperacaoEfetuada operacaoEfetuada, " + " UsuarioAlteracao usuarioAlteracao, "
							+ " TabelaLinhaColunaAlteracao tbLinhaColunaAlteracao " +

							" where  usuarioAlteracao.operacaoEfetuada.id = operacaoEfetuada.id "
							+ " and tbLinhaColunaAlteracao.tabelaLinhaAlteracao.operacaoEfetuada.id = operacaoEfetuada.id ";

			consulta += criarCondicionalEntidadeOperacoesEfetuadas(chaveEntidade);

			consulta += " order by operacaoEfetuada.ultimaAlteracao desc ";

			retorno = session.createQuery(consulta)
			/* .setFirstResult(10 * numeroPagina) */
			/* .setMaxResults(10) */.list();

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

	// Verifica se há chave e adiciona a condicao
	private String criarCondicionalEntidadeOperacoesEfetuadas(Integer chaveEntidade){

		String condicao = "";

		if(chaveEntidade != null){
			condicao = " and operacaoEfetuada.argumentoValor = " + chaveEntidade + " ";
		}

		return condicao;
	}

	public Collection pesquisarUsuarioAlteracaoDasOperacoesEfetuadasHqlRelatorio(Integer idUsuarioAcao, String[] idOperacoes,
					String idUsuario, Date dataInicial, Date dataFinal, Date horaInicial, Date horaFinal,
					Hashtable<Integer, String> argumentos, Integer id1) throws ErroRepositorioException{

		// cria a coleção de retorno
		Collection retorno = null;
		String consulta;
		// obtém a sessão
		Session session = HibernateUtil.getSession();

		try{

			// pesquisa a coleção de atividades e atribui a variável "retorno"
			consulta = " select distinct operacaoEfetuada " //
							+ " from OperacaoEfetuada as operacaoEfetuada "
							+ " LEFT join fetch operacaoEfetuada.argumento as argu "
							+ " LEFT join operacaoEfetuada.usuarioAlteracoes as usAlt "
							+ " LEFT join operacaoEfetuada.tabelaLinhaAlteracoes as tabLinAlt "
							+ " LEFT join tabLinAlt.tabelaLinhaColunaAlteracao as tabLinColAlt "
							+ " LEFT join tabLinColAlt.tabelaColuna as tabCol " //
							+ " LEFT join tabCol.tabela as tab " //
							+ " LEFT join usAlt.usuario as usuario " //
							+ " inner join fetch operacaoEfetuada.operacao as operacao " //
							+ " LEFT OUTER join operacao.argumentoPesquisa as argumento " //
							+ " LEFT OUTER join argumento.tabela as argTab ";

			consulta += criarCondicionaisUsuarioAlteracaoDasOperacoesEfetuadas(idUsuarioAcao, idOperacoes, idUsuario, dataInicial,
							dataFinal, horaInicial, horaFinal, argumentos, id1);

			Query query = session.createQuery(consulta);

			this.ajustarCondicoesUsuarioAlteracaoDasOperacoesEfetuadas(query, dataInicial, dataFinal, horaInicial, horaFinal);

			retorno = query.list();

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
	 * Método que cria condicionais para o metodo
	 * 
	 * @param idUsuarioAcao
	 * @param idOperacao
	 * @param idUsuario
	 * @param dataInicial
	 * @param dataFinal
	 * @param horaInicial
	 * @param horaFinal
	 * @param idTabela
	 * @param idTabelaColuna
	 * @param id1
	 * @return
	 * @throws ErroRepositorioException
	 * @author Rômulo Aurélio / Rafael Correa
	 */
	private String criarCondicionaisUsuarioAlteracaoDasOperacoesEfetuadas(Integer idUsuarioAcao, String[] idOperacoes, String idUsuario,
					Date dataInicial, Date dataFinal, Date horaInicial, Date horaFinal, Hashtable<Integer, String> argumentos, Integer id1){

		String condicoes = " where ";

		if(idOperacoes != null && idOperacoes.length > 0){
			condicoes += " operacao.id in (";
			String valoresIn = "";
			for(int i = 0; i < idOperacoes.length; i++){
				valoresIn += idOperacoes[i] + ", ";
			}
			if(valoresIn.length() > 0){
				valoresIn = valoresIn.substring(0, valoresIn.length() - 2);
			}
			condicoes += valoresIn + ") and ";
		}

		if(idUsuario != null && !idUsuario.equals("")){
			condicoes += " usuario.id = '" + idUsuario + "' and ";
		}

		if(idUsuarioAcao != null && !idUsuarioAcao.equals("")){
			condicoes += " usAlt.usuarioAcao.id = " + idUsuarioAcao + " and ";
		}

		if(dataInicial != null && dataFinal != null){
			condicoes += "(operacaoEfetuada.ultimaAlteracao between :dataInicial and :dataFinal) and ";
		}else if(dataInicial != null){
			condicoes += "(operacaoEfetuada.ultimaAlteracao >= :dataInicial) and ";
		}else if(dataFinal != null){
			condicoes += "(operacaoEfetuada.ultimaAlteracao <= :dataFinal) and ";
		}

		if(horaInicial != null && horaFinal != null){
			condicoes += "(operacaoEfetuada.ultimaAlteracao between :horaInicial and :horaFinal) and ";
		}else if(horaInicial != null){
			condicoes += "(operacaoEfetuada.ultimaAlteracao >= :horaInicial) and ";
		}else if(horaFinal != null){
			condicoes += "(operacaoEfetuada.ultimaAlteracao <= :horaFinal) and ";
		}

		if(argumentos != null && argumentos.size() > 0){
			condicoes += " ( ";
			for(Enumeration e = argumentos.keys(); e.hasMoreElements();){
				Integer idArgumento = (Integer) e.nextElement();
				String valorArgumento = argumentos.get(idArgumento);
				/*
				 * condicoes += " operacaoEfetuada.operacao.argumentoPesquisa.id = " + idArgumento +
				 * " and "+
				 */

				condicoes += " ( operacaoEfetuada.argumentoValor = " + valorArgumento + " and argu.id = " + idArgumento + " ) OR ";

			}
			// retira o " OR " q fica sobrando no final da query
			condicoes = Util.formatarHQL(condicoes, 3);
			condicoes += " ) AND ";
		}else{
			condicoes += " operacaoEfetuada.argumentoValor is not null and ";
		}

		if(id1 != null){
			condicoes += " tabLinAlt.id1 = " + id1 + " and ";
		}

		// retira o " and " q fica sobrando no final da query
		condicoes = Util.formatarHQL(condicoes, 4);

		condicoes += " ORDER BY operacaoEfetuada.ultimaAlteracao DESC ";

		return condicoes;

	}

	/**
	 * Método que adapta as datas, horas e query condicionais para alguns métodos
	 * 
	 * @param query
	 * @param dataInicial
	 * @param dataFinal
	 * @param horaInicial
	 * @param horaFinal
	 * @return
	 * @throws ErroRepositorioException
	 * @author Saulo Lima
	 */
	private void ajustarCondicoesUsuarioAlteracaoDasOperacoesEfetuadas(Query query, Date dataInicial, Date dataFinal, Date horaInicial,
					Date horaFinal){

		if(dataInicial != null && dataFinal != null){

			dataInicial = Util.zerarHoraMinutoSegundo(dataInicial);
			dataFinal = Util.adaptarDataFinalComparacaoBetween(dataFinal);
			query.setTimestamp("dataInicial", dataInicial).setTimestamp("dataFinal", dataFinal);
		}else if(dataInicial != null){

			dataInicial = Util.zerarHoraMinutoSegundo(dataInicial);
			query.setTimestamp("dataInicial", dataInicial);
		}else if(dataFinal != null){

			dataFinal = Util.adaptarDataFinalComparacaoBetween(dataFinal);
			query.setTimestamp("dataFinal", dataFinal);

		}

		if(horaInicial != null && horaFinal != null){

			if(dataInicial != null){
				dataInicial = Util.ajustarHoraMinutoSegundo(dataInicial, Util.obterHoraDeDate(horaInicial),
								Util.obterMinutoDeDate(horaInicial), 0, 0);
			}else{
				dataInicial = Util.ajustarHoraMinutoSegundo(new Date(), Util.obterHoraDeDate(horaInicial),
								Util.obterMinutoDeDate(horaInicial), 0, 0);
			}

			if(dataFinal != null){
				dataFinal = Util.ajustarHoraMinutoSegundo(dataFinal, Util.obterHoraDeDate(horaFinal), Util.obterMinutoDeDate(horaFinal), 0,
								0);
			}else{
				dataFinal = Util.ajustarHoraMinutoSegundo(new Date(), Util.obterHoraDeDate(horaFinal), Util.obterMinutoDeDate(horaFinal),
								0, 0);
			}

			query.setTimestamp("horaInicial", dataInicial).setTimestamp("horaFinal", dataFinal);
		}else if(horaInicial != null){

			if(dataInicial != null){
				dataInicial = Util.ajustarHoraMinutoSegundo(dataInicial, Util.obterHoraDeDate(horaInicial),
								Util.obterMinutoDeDate(horaInicial), 0, 0);
			}else{
				dataInicial = Util.ajustarHoraMinutoSegundo(new Date(), Util.obterHoraDeDate(horaInicial),
								Util.obterMinutoDeDate(horaInicial), 0, 0);
			}

			query.setTimestamp("horaInicial", dataInicial);
		}else if(horaFinal != null){

			if(dataFinal != null){
				dataFinal = Util.ajustarHoraMinutoSegundo(dataFinal, Util.obterHoraDeDate(horaFinal), Util.obterMinutoDeDate(horaFinal), 0,
								0);
			}else{
				dataFinal = Util.ajustarHoraMinutoSegundo(new Date(), Util.obterHoraDeDate(horaFinal), Util.obterMinutoDeDate(horaFinal),
								0, 0);
			}

			query.setTimestamp("horaFinal", dataFinal);
		}

	}

	public Collection pesquisarOperacaoOrdemExibicao(int[] idTabelaColuna, int idOperacao) throws ErroRepositorioException{

		// cria a coleção de retorno
		Collection retorno = null;
		String consulta;
		// obtém a sessão
		Session session = HibernateUtil.getSession();

		try{
			String ids = "";
			for(int i = 0; i < idTabelaColuna.length; i++){
				ids += idTabelaColuna[i] + ", ";
			}

			if(!Util.isVazioOuBranco(ids)){
				ids = ids.substring(0, ids.length() - 2);

				FiltroOperacaoOrdemExibicao filtroOperacaoOrdem = new FiltroOperacaoOrdemExibicao();
				filtroOperacaoOrdem.adicionarCaminhoParaCarregamentoEntidade(FiltroOperacaoOrdemExibicao.OPERACAO);
				// filtroOperacaoOrdem
				// .adicionarCaminhoParaCarregamentoEntidade(FiltroOperacaoOrdemExibicao.TABELA_COLUNA);
				filtroOperacaoOrdem.adicionarCaminhoParaCarregamentoEntidade(FiltroOperacaoOrdemExibicao.TABELA_COLUNA_TABELA);

				consulta = "select operacaoOrdem from gcom.seguranca.acesso.OperacaoOrdemExibicao as operacaoOrdem "
								+ PersistenciaUtil.processaObjetosParaCarregamentoJoinFetch("operacaoOrdem",
												filtroOperacaoOrdem.getColecaoCaminhosParaCarregamentoEntidades())
								+ " where operacaoOrdem.tabelaColuna.id in (" + ids + ") and " + "operacaoOrdem.operacao.id = "
								+ idOperacao;

				retorno = new ArrayList(new CopyOnWriteArraySet<OperacaoOrdemExibicao>(session.createQuery(consulta).list()));
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
	 * Pesquisar Argumento
	 * 
	 * @author Saulo Lima
	 * @date 25/10/2012
	 * @param idOperacao
	 * @param nomeObjeto
	 * @return idArgumento
	 * @exception ErroRepositorioException
	 */
	public Integer pesquisarArgumento(Integer idOperacao, String nomeObjeto) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		Integer retorno = null;

		try{

			StringBuffer consulta = new StringBuffer();
			consulta.append("SELECT fnar.argu_id AS idArgumento FROM funcionalidade_argumento fnar   ");
			consulta.append("INNER JOIN funcionalidade fncd ON fncd.fncd_Id = fnar.fncd_Id ");
			consulta.append("INNER JOIN operacao oper ON oper.fncd_Id = fncd.fncd_Id ");
			// consulta.append("INNER JOIN argumento argu ON argu.argu_Id = fnar.argu_Id ");
			consulta.append("WHERE oper.oper_id = :idOperacao ");
			// consulta.append("AND argu.argu_dsabreviado = :nomeObjeto");

			SQLQuery query = session.createSQLQuery(consulta.toString());

			retorno = (Integer) query.addScalar("idArgumento", Hibernate.INTEGER).setInteger("idOperacao", idOperacao)/*
																													 * .
																													 * setString
																													 * (
																													 * "nomeObjeto"
																													 * ,
																													 * nomeObjeto
																													 * )
																													 */.setMaxResults(1)
							.uniqueResult();

			// retorno = (Integer) query.uniqueResult();

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
	/**
	 * [UC3124] - Auditoria de Cancelamento/Revisão de Contas
	 * 
	 * @author Ado Rocha
	 * @date 20/01/2014
	 */
	public Collection pesquisarAuditoriaRevisaoCancelamentoContaFuncionario(Integer idFuncionario, Integer dataInicial, Integer dataFinal)
					throws ErroRepositorioException{

		Collection retorno = null;
		Session session = HibernateUtil.getSession();
		StringBuffer consulta = new StringBuffer();

		try{
			consulta.append("SELECT MCTCDOPER AS mctcdoper, ");
			consulta.append("       MCTIAMREF AS mctiamref, ");
			consulta.append("       MCTICDMOTIVO AS mcticdmotivo, ");
			consulta.append("       MCTINNMATUSU AS mctinnmatusu, ");
			consulta.append("       MCTDATA AS mctdata, ");
			consulta.append("       MCTHORA AS mcthora ");
			consulta.append("FROM SCITMCT ");
			consulta.append("WHERE MCTCDFUN = :idFuncionario ");
			consulta.append("AND MCTDATA BETWEEN :dataInicial AND :dataFinal ");
			consulta.append("ORDER BY MCTDATA, MCTHORA, MCTCDFUN");

			SQLQuery query = session.createSQLQuery(consulta.toString());

			// RETORNO
			query.addScalar("mctcdoper", Hibernate.INTEGER);
			query.addScalar("mctiamref", Hibernate.INTEGER);
			query.addScalar("mcticdmotivo", Hibernate.STRING);
			query.addScalar("mctinnmatusu", Hibernate.INTEGER);
			query.addScalar("mctdata", Hibernate.INTEGER);
			query.addScalar("mcthora", Hibernate.INTEGER);

			// PARAMETROS
			query.setInteger("idFuncionario", idFuncionario);
			query.setInteger("dataInicial", dataInicial);
			query.setInteger("dataFinal", dataFinal);

			retorno = query.list();

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
	 * [UC3124] - Auditoria de Cancelamento/Revisão de Contas
	 * 
	 * @author Ado Rocha
	 * @date 20/01/2014
	 */
	public Collection pesquisarAuditoriaRevisaoCancelamentoContaUsuario(Integer idUsuario, Integer dataInicial, Integer dataFinal)
					throws ErroRepositorioException{

		Collection retorno = null;
		Session session = HibernateUtil.getSession();
		StringBuffer consulta = new StringBuffer();

		try{
			consulta.append("SELECT MCTCDOPER AS mctcdoper, ");
			consulta.append("       MCTIAMREF AS mctiamref, ");
			consulta.append("       MCTICDMOTIVO AS mcticdmotivo, ");
			consulta.append("       MCTCDFUN AS mctcdfun, ");
			consulta.append("       MCTDATA AS mctdata, ");
			consulta.append("       MCTHORA AS mcthora ");
			consulta.append("FROM SCITMCT ");
			consulta.append("WHERE MCTINNMATUSU = :idUsuario ");
			consulta.append("AND MCTDATA BETWEEN :dataInicial AND :dataFinal ");
			consulta.append("ORDER BY MCTDATA, MCTHORA, MCTINNMATUSU ");

			SQLQuery query = session.createSQLQuery(consulta.toString());

			// RETORNO
			query.addScalar("mctcdoper", Hibernate.INTEGER);
			query.addScalar("mctiamref", Hibernate.INTEGER);
			query.addScalar("mcticdmotivo", Hibernate.STRING);
			query.addScalar("mctcdfun", Hibernate.INTEGER);
			query.addScalar("mctdata", Hibernate.INTEGER);
			query.addScalar("mcthora", Hibernate.INTEGER);

			// PARAMETROS
			query.setInteger("idUsuario", idUsuario);
			query.setInteger("dataInicial", dataInicial);
			query.setInteger("dataFinal", dataFinal);

			retorno = query.list();

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
	 * [UC3123] - Auditoria de Retificação/Implantação de Contas
	 * 
	 * @author Ado Rocha
	 * @date 20/01/2014
	 */
	public Collection pesquisarRetificacaoImplantacaoContaFuncionario(Integer idFuncionario, Integer dataInicial, Integer dataFinal)
					throws ErroRepositorioException{

		Collection retorno = null;
		Session session = HibernateUtil.getSession();
		StringBuffer consulta = new StringBuffer();

		try{
			consulta.append("SELECT MMCDATA AS mmcdata, ");
			consulta.append("       MMCHORA AS mmchora, ");
			consulta.append("       MMCCDFUN AS mmccdfun, ");
			consulta.append("       MMCOPID AS mmcopid, ");
			consulta.append("       MMCCDLOC AS mmccdloc, ");
			consulta.append("       MMCCDOPER AS mmccdoper, ");
			consulta.append("       MMCINNMATUSU AS mmcinnmatusu, ");
			consulta.append("       MMCIICIMPRES AS mmciicimpres, ");
			consulta.append("       MMCIAMREF AS mmciamref, ");
			consulta.append("       MMCICDMOTIVO AS mmcicdmotivo, ");
			consulta.append("       MMCIDTVENCON AS mmcidtvencon, ");
			consulta.append("       MMCICDSITAGU AS mmcicdsitagu, ");
			consulta.append("       MMCICDSITESG AS mmcicdsitesg, ");
			consulta.append("       MMCINNCONMES AS mmcinnconmes, ");
			consulta.append("       MMCINNPERESG AS mmcinnperesg, ");
			consulta.append("       MMCIQTFIXESG AS mmciqtfixesg, ");
			consulta.append("       MMCICDRSP AS mmcicdrsp, ");
			consulta.append("       MMCICDTARIFA AS mmcicdtarifa, ");
			consulta.append("       MMCIQTCAT1 AS mmciqtcat1, ");
			consulta.append("       MMCIQTCAT2 AS mmciqtcat2, ");
			consulta.append("       MMCIQTCAT3 AS mmciqtcat3, ");
			consulta.append("       MMCIQTCAT4 AS mmciqtcat4, ");
			consulta.append("       MMCICDSER1 AS mmcicdser1, ");
			consulta.append("       MMCIAMREFANT1 AS mmciamrefant1, ");
			consulta.append("       MMCIVLSERV1 AS mmcivlserv1, ");
			consulta.append("       MMCICDSER2 AS mmcicdser2, ");
			consulta.append("       MMCIAMREFANT2 AS mmciamrefant2, ");
			consulta.append("       MMCIVLSERV2 AS mmcivlserv2, ");
			consulta.append("       MMCICDSER3 AS mmcicdser3, ");
			consulta.append("       MMCIAMREFANT3 AS mmciamrefant3, ");
			consulta.append("       MMCIVLSERV3 AS mmcivlserv3, ");
			consulta.append("       MMCICDSER4 AS mmcicdser4, ");
			consulta.append("       MMCIAMREFANT4 AS mmciamrefant4, ");
			consulta.append("       MMCIVLSERV4 AS mmcivlserv4, ");
			consulta.append("       MMCICDSER5 AS mmcicdser5, ");
			consulta.append("       MMCIAMREFANT5 AS mmciamrefant5, ");
			consulta.append("       MMCIVLSERV5 AS mmcivlserv5, ");
			consulta.append("       MMCICDSER6 AS mmcicdser6, ");
			consulta.append("       MMCIAMREFANT6 AS mmciamrefant6, ");
			consulta.append("       MMCIVLSERV6 AS mmcivlserv6, ");
			consulta.append("       MMCICDSER7 AS mmcicdser7, ");
			consulta.append("       MMCIAMREFANT7 AS mmciamrefant7, ");
			consulta.append("       MMCIVLSERV7 AS mmcivlserv7, ");
			consulta.append("       MMCADTVENCON AS mmcadtvencon, ");
			consulta.append("       MMCACDSITAGU AS mmcacdsitagu, ");
			consulta.append("       MMCACDSITESG AS mmcacdsitesg, ");
			consulta.append("       MMCANNCONMES AS mmcannconmes, ");
			consulta.append("       MMCANNPERESG AS mmcannperesg, ");
			consulta.append("       MMCAQTFIXESG AS mmcaqtfixesg, ");
			consulta.append("       MMCACDRSP AS mmcacdrsp, ");
			consulta.append("       MMCACDTARIFA AS mmcacdtarifa, ");
			consulta.append("       MMCAQTCAT1 AS mmcaqtcat1, ");
			consulta.append("       MMCAQTCAT2 AS mmcaqtcat2, ");
			consulta.append("       MMCAQTCAT3 AS mmcaqtcat3, ");
			consulta.append("       MMCAQTCAT4 AS mmcaqtcat4, ");
			consulta.append("       MMCACDSER1 AS mmcacdser1, ");
			consulta.append("       MMCAAMREFANT1 AS mmcaamrefant1, ");
			consulta.append("       MMCAVLSERV1 AS mmcavlserv1, ");
			consulta.append("       MMCACDSER2 AS mmcacdser2, ");
			consulta.append("       MMCAAMREFANT2 AS mmcaamrefant2, ");
			consulta.append("       MMCAVLSERV2 AS mmcavlserv2, ");
			consulta.append("       MMCACDSER3 AS mmcacdser3, ");
			consulta.append("       MMCAAMREFANT3 AS mmcaamrefant3, ");
			consulta.append("       MMCAVLSERV3 AS mmcavlserv3, ");
			consulta.append("       MMCACDSER4 AS mmcacdser4, ");
			consulta.append("       MMCAAMREFANT4 AS mmcaamrefant4, ");
			consulta.append("       MMCAVLSERV4 AS mmcavlserv4, ");
			consulta.append("       MMCACDSER5 AS mmcacdser5, ");
			consulta.append("       MMCAAMREFANT5 AS mmcaamrefant5, ");
			consulta.append("       MMCAVLSERV5 AS mmcavlserv5, ");
			consulta.append("       MMCACDSER6 AS mmcacdser6, ");
			consulta.append("       MMCAAMREFANT6 AS mmcaamrefant6, ");
			consulta.append("       MMCAVLSERV6 AS mmcavlserv6, ");
			consulta.append("       MMCACDSER7 AS mmcacdser7, ");
			consulta.append("       MMCAAMREFANT7 AS mmcaamrefant7, ");
			consulta.append("       MMCAVLSERV7 AS mmcavlserv7 ");
			consulta.append("FROM SCITMMC ");
			consulta.append("WHERE MMCCDFUN = :idFuncionario ");
			consulta.append("AND MMCDATA BETWEEN :dataInicial AND :dataFinal ");
			consulta.append("ORDER BY MMCDATA, MMCHORA, MMCCDFUN");

			SQLQuery query = session.createSQLQuery(consulta.toString());

			// RETORNO
			query.addScalar("mmcdata", Hibernate.INTEGER);
			query.addScalar("mmchora", Hibernate.INTEGER);
			query.addScalar("mmccdfun", Hibernate.INTEGER);
			query.addScalar("mmcopid", Hibernate.STRING);
			query.addScalar("mmccdloc", Hibernate.INTEGER);
			query.addScalar("mmccdoper", Hibernate.INTEGER);
			query.addScalar("mmcinnmatusu", Hibernate.INTEGER);
			query.addScalar("mmciicimpres", Hibernate.STRING);
			query.addScalar("mmciamref", Hibernate.INTEGER);
			query.addScalar("mmcicdmotivo", Hibernate.STRING);
			query.addScalar("mmcidtvencon", Hibernate.DATE);
			query.addScalar("mmcicdsitagu", Hibernate.INTEGER);
			query.addScalar("mmcicdsitesg", Hibernate.INTEGER);
			query.addScalar("mmcinnconmes", Hibernate.INTEGER);
			query.addScalar("mmcinnperesg", Hibernate.INTEGER);
			query.addScalar("mmciqtfixesg", Hibernate.INTEGER);
			query.addScalar("mmcicdrsp", Hibernate.INTEGER);
			query.addScalar("mmcicdtarifa", Hibernate.INTEGER);
			query.addScalar("mmciqtcat1", Hibernate.INTEGER);
			query.addScalar("mmciqtcat2", Hibernate.INTEGER);
			query.addScalar("mmciqtcat3", Hibernate.INTEGER);
			query.addScalar("mmciqtcat4", Hibernate.INTEGER);
			query.addScalar("mmcicdser1", Hibernate.INTEGER);
			query.addScalar("mmciamrefant1", Hibernate.INTEGER);
			query.addScalar("mmcivlserv1", Hibernate.INTEGER);
			query.addScalar("mmcicdser2", Hibernate.INTEGER);
			query.addScalar("mmciamrefant2", Hibernate.INTEGER);
			query.addScalar("mmcivlserv2", Hibernate.INTEGER);
			query.addScalar("mmcicdser3", Hibernate.INTEGER);
			query.addScalar("mmciamrefant3", Hibernate.INTEGER);
			query.addScalar("mmcivlserv3", Hibernate.INTEGER);
			query.addScalar("mmcicdser4", Hibernate.INTEGER);
			query.addScalar("mmciamrefant4", Hibernate.INTEGER);
			query.addScalar("mmcivlserv4", Hibernate.INTEGER);
			query.addScalar("mmcicdser5", Hibernate.INTEGER);
			query.addScalar("mmciamrefant5", Hibernate.INTEGER);
			query.addScalar("mmcivlserv5", Hibernate.INTEGER);
			query.addScalar("mmcicdser6", Hibernate.INTEGER);
			query.addScalar("mmciamrefant6", Hibernate.INTEGER);
			query.addScalar("mmcivlserv6", Hibernate.INTEGER);
			query.addScalar("mmcicdser7", Hibernate.INTEGER);
			query.addScalar("mmciamrefant7", Hibernate.INTEGER);
			query.addScalar("mmcivlserv7", Hibernate.INTEGER);
			query.addScalar("mmcadtvencon", Hibernate.DATE);
			query.addScalar("mmcacdsitagu", Hibernate.INTEGER);
			query.addScalar("mmcacdsitesg", Hibernate.INTEGER);
			query.addScalar("mmcannconmes", Hibernate.INTEGER);
			query.addScalar("mmcannperesg", Hibernate.INTEGER);
			query.addScalar("mmcaqtfixesg", Hibernate.INTEGER);
			query.addScalar("mmcacdrsp", Hibernate.INTEGER);
			query.addScalar("mmcacdtarifa", Hibernate.INTEGER);
			query.addScalar("mmcaqtcat1", Hibernate.INTEGER);
			query.addScalar("mmcaqtcat2", Hibernate.INTEGER);
			query.addScalar("mmcaqtcat3", Hibernate.INTEGER);
			query.addScalar("mmcaqtcat4", Hibernate.INTEGER);
			query.addScalar("mmcacdser1", Hibernate.INTEGER);
			query.addScalar("mmcaamrefant1", Hibernate.INTEGER);
			query.addScalar("mmcavlserv1", Hibernate.INTEGER);
			query.addScalar("mmcacdser2", Hibernate.INTEGER);
			query.addScalar("mmcaamrefant2", Hibernate.INTEGER);
			query.addScalar("mmcavlserv2", Hibernate.INTEGER);
			query.addScalar("mmcacdser3", Hibernate.INTEGER);
			query.addScalar("mmcaamrefant3", Hibernate.INTEGER);
			query.addScalar("mmcavlserv3", Hibernate.INTEGER);
			query.addScalar("mmcacdser4", Hibernate.INTEGER);
			query.addScalar("mmcaamrefant4", Hibernate.INTEGER);
			query.addScalar("mmcavlserv4", Hibernate.INTEGER);
			query.addScalar("mmcacdser5", Hibernate.INTEGER);
			query.addScalar("mmcaamrefant5", Hibernate.INTEGER);
			query.addScalar("mmcavlserv5", Hibernate.INTEGER);
			query.addScalar("mmcacdser6", Hibernate.INTEGER);
			query.addScalar("mmcaamrefant6", Hibernate.INTEGER);
			query.addScalar("mmcavlserv6", Hibernate.INTEGER);
			query.addScalar("mmcacdser7", Hibernate.INTEGER);
			query.addScalar("mmcaamrefant7", Hibernate.INTEGER);
			query.addScalar("mmcavlserv7", Hibernate.INTEGER);

			// PARAMETROS
			query.setInteger("idFuncionario", idFuncionario);
			query.setInteger("dataInicial", dataInicial);
			query.setInteger("dataFinal", dataFinal);

			retorno = query.list();

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
	 * [UC3123] - Auditoria de Retificação/Implantação de Contas
	 * 
	 * @author Ado Rocha
	 * @date 20/01/2014
	 */
	public Collection pesquisarRetificacaoImplantacaoContaUsuario(Integer idUsuario, Integer dataInicial, Integer dataFinal)
					throws ErroRepositorioException{

		Collection retorno = null;
		Session session = HibernateUtil.getSession();
		StringBuffer consulta = new StringBuffer();

		try{
			consulta.append("SELECT MMCDATA AS mmcdata, ");
			consulta.append("       MMCHORA AS mmchora, ");
			consulta.append("       MMCCDFUN AS mmccdfun, ");
			consulta.append("       MMCOPID AS mmcopid, ");
			consulta.append("       MMCCDLOC AS mmccdloc, ");
			consulta.append("       MMCCDOPER AS mmccdoper, ");
			consulta.append("       MMCINNMATUSU AS mmcinnmatusu, ");
			consulta.append("       MMCIICIMPRES AS mmciicimpres, ");
			consulta.append("       MMCIAMREF AS mmciamref, ");
			consulta.append("       MMCICDMOTIVO AS mmcicdmotivo, ");
			consulta.append("       MMCIDTVENCON AS mmcidtvencon, ");
			consulta.append("       MMCICDSITAGU AS mmcicdsitagu, ");
			consulta.append("       MMCICDSITESG AS mmcicdsitesg, ");
			consulta.append("       MMCINNCONMES AS mmcinnconmes, ");
			consulta.append("       MMCINNPERESG AS mmcinnperesg, ");
			consulta.append("       MMCIQTFIXESG AS mmciqtfixesg, ");
			consulta.append("       MMCICDRSP AS mmcicdrsp, ");
			consulta.append("       MMCICDTARIFA AS mmcicdtarifa, ");
			consulta.append("       MMCIQTCAT1 AS mmciqtcat1, ");
			consulta.append("       MMCIQTCAT2 AS mmciqtcat2, ");
			consulta.append("       MMCIQTCAT3 AS mmciqtcat3, ");
			consulta.append("       MMCIQTCAT4 AS mmciqtcat4, ");
			consulta.append("       MMCICDSER1 AS mmcicdser1, ");
			consulta.append("       MMCIAMREFANT1 AS mmciamrefant1, ");
			consulta.append("       MMCIVLSERV1 AS mmcivlserv1, ");
			consulta.append("       MMCICDSER2 AS mmcicdser2, ");
			consulta.append("       MMCIAMREFANT2 AS mmciamrefant2, ");
			consulta.append("       MMCIVLSERV2 AS mmcivlserv2, ");
			consulta.append("       MMCICDSER3 AS mmcicdser3, ");
			consulta.append("       MMCIAMREFANT3 AS mmciamrefant3, ");
			consulta.append("       MMCIVLSERV3 AS mmcivlserv3, ");
			consulta.append("       MMCICDSER4 AS mmcicdser4, ");
			consulta.append("       MMCIAMREFANT4 AS mmciamrefant4, ");
			consulta.append("       MMCIVLSERV4 AS mmcivlserv4, ");
			consulta.append("       MMCICDSER5 AS mmcicdser5, ");
			consulta.append("       MMCIAMREFANT5 AS mmciamrefant5, ");
			consulta.append("       MMCIVLSERV5 AS mmcivlserv5, ");
			consulta.append("       MMCICDSER6 AS mmcicdser6, ");
			consulta.append("       MMCIAMREFANT6 AS mmciamrefant6, ");
			consulta.append("       MMCIVLSERV6 AS mmcivlserv6, ");
			consulta.append("       MMCICDSER7 AS mmcicdser7, ");
			consulta.append("       MMCIAMREFANT7 AS mmciamrefant7, ");
			consulta.append("       MMCIVLSERV7 AS mmcivlserv7, ");
			consulta.append("       MMCADTVENCON AS mmcadtvencon, ");
			consulta.append("       MMCACDSITAGU AS mmcacdsitagu, ");
			consulta.append("       MMCACDSITESG AS mmcacdsitesg, ");
			consulta.append("       MMCANNCONMES AS mmcannconmes, ");
			consulta.append("       MMCANNPERESG AS mmcannperesg, ");
			consulta.append("       MMCAQTFIXESG AS mmcaqtfixesg, ");
			consulta.append("       MMCACDRSP AS mmcacdrsp, ");
			consulta.append("       MMCACDTARIFA AS mmcacdtarifa, ");
			consulta.append("       MMCAQTCAT1 AS mmcaqtcat1, ");
			consulta.append("       MMCAQTCAT2 AS mmcaqtcat2, ");
			consulta.append("       MMCAQTCAT3 AS mmcaqtcat3, ");
			consulta.append("       MMCAQTCAT4 AS mmcaqtcat4, ");
			consulta.append("       MMCACDSER1 AS mmcacdser1, ");
			consulta.append("       MMCAAMREFANT1 AS mmcaamrefant1, ");
			consulta.append("       MMCAVLSERV1 AS mmcavlserv1, ");
			consulta.append("       MMCACDSER2 AS mmcacdser2, ");
			consulta.append("       MMCAAMREFANT2 AS mmcaamrefant2, ");
			consulta.append("       MMCAVLSERV2 AS mmcavlserv2, ");
			consulta.append("       MMCACDSER3 AS mmcacdser3, ");
			consulta.append("       MMCAAMREFANT3 AS mmcaamrefant3, ");
			consulta.append("       MMCAVLSERV3 AS mmcavlserv3, ");
			consulta.append("       MMCACDSER4 AS mmcacdser4, ");
			consulta.append("       MMCAAMREFANT4 AS mmcaamrefant4, ");
			consulta.append("       MMCAVLSERV4 AS mmcavlserv4, ");
			consulta.append("       MMCACDSER5 AS mmcacdser5, ");
			consulta.append("       MMCAAMREFANT5 AS mmcaamrefant5, ");
			consulta.append("       MMCAVLSERV5 AS mmcavlserv5, ");
			consulta.append("       MMCACDSER6 AS mmcacdser6, ");
			consulta.append("       MMCAAMREFANT6 AS mmcaamrefant6, ");
			consulta.append("       MMCAVLSERV6 AS mmcavlserv6, ");
			consulta.append("       MMCACDSER7 AS mmcacdser7, ");
			consulta.append("       MMCAAMREFANT7 AS mmcaamrefant7, ");
			consulta.append("       MMCAVLSERV7 AS mmcavlserv7 ");
			consulta.append("FROM SCITMMC ");
			consulta.append("WHERE MMCINNMATUSU = :idUsuario ");
			consulta.append("AND MMCDATA BETWEEN :dataInicial AND :dataFinal ");
			consulta.append("ORDER BY MMCDATA, MMCHORA, MMCCDFUN");

			SQLQuery query = session.createSQLQuery(consulta.toString());

			// RETORNO
			query.addScalar("mmcdata", Hibernate.INTEGER);
			query.addScalar("mmchora", Hibernate.INTEGER);
			query.addScalar("mmccdfun", Hibernate.INTEGER);
			query.addScalar("mmcopid", Hibernate.STRING);
			query.addScalar("mmccdloc", Hibernate.INTEGER);
			query.addScalar("mmccdoper", Hibernate.INTEGER);
			query.addScalar("mmcinnmatusu", Hibernate.INTEGER);
			query.addScalar("mmciicimpres", Hibernate.STRING);
			query.addScalar("mmciamref", Hibernate.INTEGER);
			query.addScalar("mmcicdmotivo", Hibernate.STRING);
			query.addScalar("mmcidtvencon", Hibernate.DATE);
			query.addScalar("mmcicdsitagu", Hibernate.INTEGER);
			query.addScalar("mmcicdsitesg", Hibernate.INTEGER);
			query.addScalar("mmcinnconmes", Hibernate.INTEGER);
			query.addScalar("mmcinnperesg", Hibernate.INTEGER);
			query.addScalar("mmciqtfixesg", Hibernate.INTEGER);
			query.addScalar("mmcicdrsp", Hibernate.INTEGER);
			query.addScalar("mmcicdtarifa", Hibernate.INTEGER);
			query.addScalar("mmciqtcat1", Hibernate.INTEGER);
			query.addScalar("mmciqtcat2", Hibernate.INTEGER);
			query.addScalar("mmciqtcat3", Hibernate.INTEGER);
			query.addScalar("mmciqtcat4", Hibernate.INTEGER);
			query.addScalar("mmcicdser1", Hibernate.INTEGER);
			query.addScalar("mmciamrefant1", Hibernate.INTEGER);
			query.addScalar("mmcivlserv1", Hibernate.INTEGER);
			query.addScalar("mmcicdser2", Hibernate.INTEGER);
			query.addScalar("mmciamrefant2", Hibernate.INTEGER);
			query.addScalar("mmcivlserv2", Hibernate.INTEGER);
			query.addScalar("mmcicdser3", Hibernate.INTEGER);
			query.addScalar("mmciamrefant3", Hibernate.INTEGER);
			query.addScalar("mmcivlserv3", Hibernate.INTEGER);
			query.addScalar("mmcicdser4", Hibernate.INTEGER);
			query.addScalar("mmciamrefant4", Hibernate.INTEGER);
			query.addScalar("mmcivlserv4", Hibernate.INTEGER);
			query.addScalar("mmcicdser5", Hibernate.INTEGER);
			query.addScalar("mmciamrefant5", Hibernate.INTEGER);
			query.addScalar("mmcivlserv5", Hibernate.INTEGER);
			query.addScalar("mmcicdser6", Hibernate.INTEGER);
			query.addScalar("mmciamrefant6", Hibernate.INTEGER);
			query.addScalar("mmcivlserv6", Hibernate.INTEGER);
			query.addScalar("mmcicdser7", Hibernate.INTEGER);
			query.addScalar("mmciamrefant7", Hibernate.INTEGER);
			query.addScalar("mmcivlserv7", Hibernate.INTEGER);
			query.addScalar("mmcadtvencon", Hibernate.DATE);
			query.addScalar("mmcacdsitagu", Hibernate.INTEGER);
			query.addScalar("mmcacdsitesg", Hibernate.INTEGER);
			query.addScalar("mmcannconmes", Hibernate.INTEGER);
			query.addScalar("mmcannperesg", Hibernate.INTEGER);
			query.addScalar("mmcaqtfixesg", Hibernate.INTEGER);
			query.addScalar("mmcacdrsp", Hibernate.INTEGER);
			query.addScalar("mmcacdtarifa", Hibernate.INTEGER);
			query.addScalar("mmcaqtcat1", Hibernate.INTEGER);
			query.addScalar("mmcaqtcat2", Hibernate.INTEGER);
			query.addScalar("mmcaqtcat3", Hibernate.INTEGER);
			query.addScalar("mmcaqtcat4", Hibernate.INTEGER);
			query.addScalar("mmcacdser1", Hibernate.INTEGER);
			query.addScalar("mmcaamrefant1", Hibernate.INTEGER);
			query.addScalar("mmcavlserv1", Hibernate.INTEGER);
			query.addScalar("mmcacdser2", Hibernate.INTEGER);
			query.addScalar("mmcaamrefant2", Hibernate.INTEGER);
			query.addScalar("mmcavlserv2", Hibernate.INTEGER);
			query.addScalar("mmcacdser3", Hibernate.INTEGER);
			query.addScalar("mmcaamrefant3", Hibernate.INTEGER);
			query.addScalar("mmcavlserv3", Hibernate.INTEGER);
			query.addScalar("mmcacdser4", Hibernate.INTEGER);
			query.addScalar("mmcaamrefant4", Hibernate.INTEGER);
			query.addScalar("mmcavlserv4", Hibernate.INTEGER);
			query.addScalar("mmcacdser5", Hibernate.INTEGER);
			query.addScalar("mmcaamrefant5", Hibernate.INTEGER);
			query.addScalar("mmcavlserv5", Hibernate.INTEGER);
			query.addScalar("mmcacdser6", Hibernate.INTEGER);
			query.addScalar("mmcaamrefant6", Hibernate.INTEGER);
			query.addScalar("mmcavlserv6", Hibernate.INTEGER);
			query.addScalar("mmcacdser7", Hibernate.INTEGER);
			query.addScalar("mmcaamrefant7", Hibernate.INTEGER);
			query.addScalar("mmcavlserv7", Hibernate.INTEGER);

			// PARAMETROS
			query.setInteger("idUsuario", idUsuario);
			query.setInteger("dataInicial", dataInicial);
			query.setInteger("dataFinal", dataFinal);

			retorno = query.list();

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
	 * [UC3125] - Auditoria de Parcelamento de Contas
	 * 
	 * @author Ado Rocha
	 * @date 20/01/2014
	 */
	public Collection pesquisarParcelamentoContaFuncionario(Integer idFuncionario, Date dataInicial, Date dataFinal)
					throws ErroRepositorioException{

		Collection retorno = null;
		Session session = HibernateUtil.getSession();
		StringBuffer consulta = new StringBuffer();

		try{
			consulta.append("SELECT MPCCDFUN AS mpccdfun, ");
			consulta.append("       MPCDTMPC AS mpcdtmpc, ");
			consulta.append("       MPCHRMPC AS mpchrmpc, ");
			consulta.append("       MPCAMINI AS mpcamini, ");
			consulta.append("       MPCAMFIN AS mpcamfin, ");
			consulta.append("       MPCNNMATUSU AS mpcnnatusu, ");
			consulta.append("       MPCNNMATUSUD AS mpcnnmatusud, ");
			consulta.append("       MPCNNPREST AS mpcnnprest, ");
			consulta.append("       MPCVLENTR AS mpcvlentr, ");
			consulta.append("       MPCVLPREST AS mpcvlprest, ");
			consulta.append("       MPCVLDEBHIST AS mpcvldebhist, ");
			consulta.append("       MPCVLDEBCORR AS mpcvldebcorr, ");
			consulta.append("       MPCVLTOTSACINCL AS mpcvltotsacincl, ");
			consulta.append("       MPCVLPARCEL AS mpcvlparcel, ");
			consulta.append("       MPCNNMATGER AS mpcnnmatger ");
			consulta.append("FROM SCITMPC ");
			consulta.append("WHERE MPCCDFUN = :idFuncionario ");
			consulta.append("AND MPCDTMPC BETWEEN :dataInicial AND :dataFinal ");
			consulta.append("ORDER BY MPCDTMPC, MPCHRMPC, MPCCDFUN ");

			SQLQuery query = session.createSQLQuery(consulta.toString());

			// RETORNO
			query.addScalar("mpccdfun", Hibernate.INTEGER);
			query.addScalar("mpcdtmpc", Hibernate.DATE);
			query.addScalar("mpchrmpc", Hibernate.INTEGER);
			query.addScalar("mpcamini", Hibernate.INTEGER);
			query.addScalar("mpcamfin", Hibernate.INTEGER);
			query.addScalar("mpcnnatusu", Hibernate.INTEGER);
			query.addScalar("mpcnnmatusud", Hibernate.INTEGER);
			query.addScalar("mpcnnprest", Hibernate.INTEGER);
			query.addScalar("mpcvlentr", Hibernate.BIG_DECIMAL);
			query.addScalar("mpcvlprest", Hibernate.BIG_DECIMAL);
			query.addScalar("mpcvldebhist", Hibernate.BIG_DECIMAL);
			query.addScalar("mpcvldebcorr", Hibernate.BIG_DECIMAL);
			query.addScalar("mpcvltotsacincl", Hibernate.BIG_DECIMAL);
			query.addScalar("mpcvlparcel", Hibernate.BIG_DECIMAL);
			query.addScalar("mpcnnmatger", Hibernate.INTEGER);

			// PARAMETROS
			query.setInteger("idFuncionario", idFuncionario);
			query.setDate("dataInicial", dataInicial);
			query.setDate("dataFinal", dataFinal);

			retorno = query.list();

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
	 * [UC3125] - Auditoria de Parcelamento de Contas
	 * 
	 * @author Ado Rocha
	 * @date 20/01/2014
	 */
	public Collection pesquisarParcelamentoContaUsuario(Integer idUsuario, Date dataInicial, Date dataFinal)
					throws ErroRepositorioException{

		Collection retorno = null;
		Session session = HibernateUtil.getSession();
		StringBuffer consulta = new StringBuffer();

		try{
			consulta.append("SELECT MPCCDFUN AS mpccdfun, ");
			consulta.append("       MPCDTMPC AS mpcdtmpc, ");
			consulta.append("       MPCHRMPC AS mpchrmpc, ");
			consulta.append("       MPCAMINI AS mpcamini, ");
			consulta.append("       MPCAMFIN AS mpcamfin, ");
			consulta.append("       MPCNNMATUSU AS mpcnnatusu, ");
			consulta.append("       MPCNNMATUSUD AS mpcnnmatusud, ");
			consulta.append("       MPCNNPREST AS mpcnnprest, ");
			consulta.append("       MPCVLENTR AS mpcvlentr, ");
			consulta.append("       MPCVLPREST AS mpcvlprest, ");
			consulta.append("       MPCVLDEBHIST AS mpcvldebhist, ");
			consulta.append("       MPCVLDEBCORR AS mpcvldebcorr, ");
			consulta.append("       MPCVLTOTSACINCL AS mpcvltotsacincl, ");
			consulta.append("       MPCVLPARCEL AS mpcvlparcel, ");
			consulta.append("       MPCNNMATGER AS mpcnnmatger ");
			consulta.append("FROM SCITMPC ");
			consulta.append("WHERE MPCNNMATUSU = :idUsuario ");
			consulta.append("AND MPCDTMPC BETWEEN :dataInicial AND :dataFinal ");
			consulta.append("ORDER BY MPCDTMPC, MPCHRMPC, MPCNNMATUSU ");

			SQLQuery query = session.createSQLQuery(consulta.toString());

			// RETORNO
			query.addScalar("mpccdfun", Hibernate.INTEGER);
			query.addScalar("mpcdtmpc", Hibernate.DATE);
			query.addScalar("mpchrmpc", Hibernate.INTEGER);
			query.addScalar("mpcamini", Hibernate.INTEGER);
			query.addScalar("mpcamfin", Hibernate.INTEGER);
			query.addScalar("mpcnnatusu", Hibernate.INTEGER);
			query.addScalar("mpcnnmatusud", Hibernate.INTEGER);
			query.addScalar("mpcnnprest", Hibernate.INTEGER);
			query.addScalar("mpcvlentr", Hibernate.BIG_DECIMAL);
			query.addScalar("mpcvlprest", Hibernate.BIG_DECIMAL);
			query.addScalar("mpcvldebhist", Hibernate.BIG_DECIMAL);
			query.addScalar("mpcvldebcorr", Hibernate.BIG_DECIMAL);
			query.addScalar("mpcvltotsacincl", Hibernate.BIG_DECIMAL);
			query.addScalar("mpcvlparcel", Hibernate.BIG_DECIMAL);
			query.addScalar("mpcnnmatger", Hibernate.INTEGER);

			// PARAMETROS
			query.setInteger("idUsuario", idUsuario);
			query.setDate("dataInicial", dataInicial);
			query.setDate("dataFinal", dataFinal);

			retorno = query.list();

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
	 * [UC3122] Auditoria de Alterações de Clientes
	 * 
	 * @author Eduardo Oliveira
	 * @throws ControladorException
	 * @date 01/03/2014
	 */
	public Collection consultarAlteracoesClientesFuncionario(Integer idFuncionario, Date dataInicial, Date dataFinal)
					throws ErroRepositorioException{

		Collection retorno = null;
		Session session = HibernateUtil.getSession();
		StringBuffer consulta = new StringBuffer();

		try{
			consulta.append("SELECT SCITCOL.COLDCCOL  AS codigoOperacao, ");
			consulta.append("       SCITALC.ALCDCINFATL   AS conteudoAtual, ");
			consulta.append("       SCITALC.ALCDCINFANT   AS conteudoAnterior, ");
			consulta.append("       SCITALC.ALCNNMATUSU   AS codigo, ");
			consulta.append("       SCITALC.ALCTMALTCAD   AS dataHora, ");
			consulta.append("       SCITALC.ALCICOPER     AS codigoOperacaoUsuario ");
			consulta.append("FROM SCITALC SCITALC ");
			consulta.append("INNER JOIN SCITCOL SCITCOL ");
			consulta.append("ON  SCITALC.ALCCDCOL = SCITCOL.COLCDCOL ");
			consulta.append("WHERE SCITALC.ALCNNMATFUN =  :idFuncionario ");
			consulta.append("AND SCITALC.ALCTMALTCAD BETWEEN :dataInicial AND :dataFinal ");
			consulta.append("ORDER BY SCITALC.ALCTMALTCAD,  SCITALC.ALCCDCOL,  SCITALC.ALCNNMATFUN ");

			SQLQuery query = session.createSQLQuery(consulta.toString());

			// RETORNO
			query.addScalar("codigoOperacao", Hibernate.STRING);
			query.addScalar("conteudoAtual", Hibernate.STRING);
			query.addScalar("conteudoAnterior", Hibernate.STRING);
			query.addScalar("codigo", Hibernate.INTEGER);
			query.addScalar("dataHora", Hibernate.TIMESTAMP);
			query.addScalar("codigoOperacaoUsuario", Hibernate.STRING);

			// PARAMETROS
			query.setInteger("idFuncionario", idFuncionario);
			query.setDate("dataInicial", Util.formatarDataInicial(dataInicial));
			query.setDate("dataFinal", Util.formatarDataFinal(dataFinal));

			retorno = query.list();

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
	 * [UC3122] Auditoria de Alterações de Clientes
	 * 
	 * @author Eduardo Oliveira
	 * @throws ControladorException
	 * @date 01/03/2014
	 */
	public Collection consultarAlteracoesClientesUsuario(Integer idUsuario, Date dataInicial, Date dataFinal)
					throws ErroRepositorioException{

		Collection retorno = null;
		Session session = HibernateUtil.getSession();
		StringBuffer consulta = new StringBuffer();

		try{
			consulta.append("SELECT SCITCOL.COLDCCOL  AS codigoOperacao, ");
			consulta.append("       SCITALC.ALCDCINFATL   AS conteudoAtual, ");
			consulta.append("       SCITALC.ALCDCINFANT   AS conteudoAnterior, ");
			consulta.append("       SCITALC.ALCNNMATFUN   AS codigo, ");
			consulta.append("       SCITALC.ALCTMALTCAD   AS dataHora, ");
			consulta.append("       SCITALC.ALCICOPER     AS codigoOperacaoUsuario ");
			consulta.append("FROM SCITALC SCITALC ");
			consulta.append("INNER JOIN SCITCOL SCITCOL ");
			consulta.append("ON  SCITALC.ALCCDCOL = SCITCOL.COLCDCOL ");
			consulta.append("WHERE SCITALC.ALCNNMATUSU =  :idUsuario ");
			consulta.append("AND SCITALC.ALCTMALTCAD BETWEEN :dataInicial AND :dataFinal ");
			consulta.append("ORDER BY SCITALC.ALCTMALTCAD,  SCITALC.ALCCDCOL,  SCITALC.ALCNNMATUSU ");

			SQLQuery query = session.createSQLQuery(consulta.toString());

			// RETORNO
			query.addScalar("codigoOperacao", Hibernate.STRING);
			query.addScalar("conteudoAtual", Hibernate.STRING);
			query.addScalar("conteudoAnterior", Hibernate.STRING);
			query.addScalar("codigo", Hibernate.INTEGER);
			query.addScalar("dataHora", Hibernate.TIMESTAMP);
			query.addScalar("codigoOperacaoUsuario", Hibernate.STRING);

			// PARAMETROS
			query.setInteger("idUsuario", idUsuario);
			query.setDate("dataInicial", Util.formatarDataInicial(dataInicial));
			query.setDate("dataFinal", Util.formatarDataFinal(dataFinal));

			retorno = query.list();

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
	 * [UC3127] - Auditoria de Transferencia de Debitos
	 * [SB0001] -€“ Apresenta Dados da Auditoria
	 * Consulta por Funcionario
	 * 
	 * @author Eduardo Oliveira
	 * @date 01/02/2014
	 */
	public Collection consultarAuditoriaTransferenciaDebitosFuncionario(Integer idFuncionario, Date dataInicial, Date dataFinal)
					throws ErroRepositorioException{

		Collection retorno = null;
		Session session = HibernateUtil.getSession();
		StringBuffer consulta = new StringBuffer();

		try{
			// RETORNO LINHA 1
			consulta.append("SELECT MPCCDFUN AS idFuncionario, ");
			consulta.append("       MPCDTMPC AS dataT, ");
			consulta.append("       MPCHRMPC  AS hora, ");
			consulta.append("       MPCAMINI  AS referenciaInicial, ");
			consulta.append("       MPCAMFIN  AS referenciaFinal, ");
			consulta.append("       MPCNNMATUSU AS usuarioOrigem, ");
			consulta.append("       MPCNNMATUSUD AS usuarioDestino, ");
			consulta.append("       MPCNNPREST AS totalPrestacao, ");
			// RETORNO LINHA 2
			consulta.append("       MPCVLENTR  AS valorEntrada, ");
			consulta.append("       MPCVLPREST  AS valorPrestacao, ");
			consulta.append("       MPCVLDEBHIST  AS valorDebitoHistorico, ");
			consulta.append("       MPCVLDEBCORR AS valorDebitoAtual, ");
			consulta.append("       MPCVLTOTSACINCL AS valorSacadoIncluso, ");
			consulta.append("       MPCVLPARCEL AS valorParcelado ");
			consulta.append("FROM SCITMPC ");
			consulta.append("WHERE MPCCDFUN = :idFuncionario ");
			consulta.append("AND MPCDTMPC BETWEEN :dataInicial AND :dataFinal ");
			consulta.append("ORDER BY MPCCDFUN, MPCDTMPC, MPCHRMPC");

			SQLQuery query = session.createSQLQuery(consulta.toString());

			// RETORNO LINHA 1
			query.addScalar("idFuncionario", Hibernate.INTEGER);// 0
			query.addScalar("dataT", Hibernate.DATE);// 1
			query.addScalar("hora", Hibernate.INTEGER);// 2
			query.addScalar("referenciaInicial", Hibernate.INTEGER);// 3
			query.addScalar("referenciaFinal", Hibernate.INTEGER);// 4
			query.addScalar("usuarioOrigem", Hibernate.INTEGER);// 5
			query.addScalar("usuarioDestino", Hibernate.INTEGER);// 6
			query.addScalar("totalPrestacao", Hibernate.INTEGER);// 7

			// RETORNO LINHA 2
			query.addScalar("valorEntrada", Hibernate.DOUBLE);// 8
			query.addScalar("valorPrestacao", Hibernate.DOUBLE);// 9
			query.addScalar("valorDebitoHistorico", Hibernate.DOUBLE);// 10
			query.addScalar("valorDebitoAtual", Hibernate.DOUBLE);// 11
			query.addScalar("valorSacadoIncluso", Hibernate.DOUBLE);// 12
			query.addScalar("valorParcelado", Hibernate.DOUBLE);// 13

			// PARAMETROS
			query.setInteger("idFuncionario", idFuncionario);
			query.setDate("dataInicial", dataInicial);
			query.setDate("dataFinal", dataFinal);

			retorno = query.list();

		}catch(HibernateException e){

			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{

			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC3127] - Auditoria de Transferencia de Debitos
	 * [SB0001] €- Apresenta Dados da Auditoria
	 * Consulta por Usuario
	 * 
	 * @author Eduardo Oliveira
	 * @date 01/02/2014
	 */
	public Collection consultarAuditoriaTransferenciaDebitosUsuario(Integer idUsuarioOrigem, Integer idUsuarioDestino, Date dataInicial,
					Date dataFinal)
					throws ErroRepositorioException{

		Collection retorno = null;
		Session session = HibernateUtil.getSession();
		StringBuffer consulta = new StringBuffer();

		try{
			// RETORNO LINHA 1
			consulta.append("SELECT MPCCDFUN AS idFuncionario, ");
			consulta.append("       MPCDTMPC AS dataT, ");
			consulta.append("       MPCHRMPC  AS hora, ");
			consulta.append("       MPCAMINI  AS referenciaInicial, ");
			consulta.append("       MPCAMFIN  AS referenciaFinal, ");
			consulta.append("       MPCNNMATUSU AS usuarioOrigem, ");
			consulta.append("       MPCNNMATUSUD AS usuarioDestino, ");
			consulta.append("       MPCNNPREST AS totalPrestacao, ");
			// RETORNO LINHA 2
			consulta.append("       MPCVLENTR  AS valorEntrada, ");
			consulta.append("       MPCVLPREST  AS valorPrestacao, ");
			consulta.append("       MPCVLDEBHIST  AS valorDebitoHistorico, ");
			consulta.append("       MPCVLDEBCORR AS valorDebitoAtual, ");
			consulta.append("       MPCVLTOTSACINCL AS valorSacadoIncluso, ");
			consulta.append("       MPCVLPARCEL AS valorParcelado ");
			consulta.append("FROM SCITMPC ");
			consulta.append("WHERE MPCDTMPC BETWEEN :dataInicial AND :dataFinal ");
			if(idUsuarioOrigem != null){
				consulta.append("AND MPCNNMATUSU = :idUsuarioOrigem ");
			}

			if(idUsuarioDestino != null){
				consulta.append("AND MPCNNMATUSUD = :idUsuarioDestino ");
			}
			consulta.append("ORDER BY MPCDTMPC, MPCHRMPC, MPCCDFUN ");

			SQLQuery query = session.createSQLQuery(consulta.toString());

			// RETORNO LINHA 1
			query.addScalar("idFuncionario", Hibernate.INTEGER);// 0
			query.addScalar("dataT", Hibernate.DATE);// 1
			query.addScalar("hora", Hibernate.INTEGER);// 2
			query.addScalar("referenciaInicial", Hibernate.INTEGER);// 3
			query.addScalar("referenciaFinal", Hibernate.INTEGER);// 4
			query.addScalar("usuarioOrigem", Hibernate.INTEGER);// 5
			query.addScalar("usuarioDestino", Hibernate.INTEGER);// 6
			query.addScalar("totalPrestacao", Hibernate.INTEGER);// 7

			// RETORNO LINHA 2
			query.addScalar("valorEntrada", Hibernate.DOUBLE);// 8
			query.addScalar("valorPrestacao", Hibernate.DOUBLE);// 9
			query.addScalar("valorDebitoHistorico", Hibernate.DOUBLE);// 10
			query.addScalar("valorDebitoAtual", Hibernate.DOUBLE);// 11
			query.addScalar("valorSacadoIncluso", Hibernate.DOUBLE);// 12
			query.addScalar("valorParcelado", Hibernate.DOUBLE);// 13

			// PARAMETROS
			if(idUsuarioOrigem != null){
				query.setInteger("idUsuarioOrigem", idUsuarioOrigem);
			}

			if(idUsuarioDestino != null){
				query.setInteger("idUsuarioDestino", idUsuarioDestino);
			}

			query.setDate("dataInicial", dataInicial);
			query.setDate("dataFinal", dataFinal);

			retorno = query.list();

		}catch(HibernateException e){

			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessao
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC3126] Auditoria de Serviços a Cobrar
	 * 
	 * @author Eduardo Oliveira
	 * @throws ControladorException
	 * @date 08/03/2014
	 */
	public Collection consultarServicosACobrarFuncionario(Integer idFuncionario, Date dataInicial, Date dataFinal)
					throws ErroRepositorioException{

		Collection retorno = null;
		Session session = HibernateUtil.getSession();
		StringBuffer consulta = new StringBuffer();

		try{
			consulta.append("SELECT SCITCOL.COLDCCOL  AS codigoOperacao, ");
			consulta.append("       SCITAUD.AUDDADOSATU   AS conteudoAtual, ");
			consulta.append("       SCITAUD.AUDDADOSANT   AS conteudoAnterior, ");
			consulta.append("       SCITAUD.AUDNNMATUSU   AS codigo, ");
			consulta.append("       SCITAUD.AUDTMATU   AS dataHora, ");
			consulta.append("       SCITAUD.AUDTRANSACAO     AS codigoOperacaoTransacao ");
			consulta.append("FROM SCITAUD SCITAUD ");
			consulta.append("INNER JOIN SCITCOL SCITCOL ");
			consulta.append("ON  SCITAUD.AUDCDCOL = SCITCOL.COLCDCOL ");
			consulta.append("WHERE SCITAUD.AUDCDFUN =  :idFuncionario ");
			consulta.append("AND SCITAUD.AUDTMATU BETWEEN :dataInicial AND :dataFinal ");
			consulta.append("ORDER BY SCITAUD.AUDTMATU,  SCITAUD.AUDCDCOL,  SCITAUD.AUDCDFUN ");

			SQLQuery query = session.createSQLQuery(consulta.toString());

			// RETORNO
			query.addScalar("codigoOperacao", Hibernate.STRING);
			query.addScalar("conteudoAtual", Hibernate.STRING);
			query.addScalar("conteudoAnterior", Hibernate.STRING);
			query.addScalar("codigo", Hibernate.INTEGER);
			query.addScalar("dataHora", Hibernate.TIMESTAMP);
			query.addScalar("codigoOperacaoTransacao", Hibernate.STRING);

			// PARAMETROS
			query.setInteger("idFuncionario", idFuncionario);
			query.setDate("dataInicial", Util.formatarDataInicial(dataInicial));
			query.setDate("dataFinal", Util.formatarDataFinal(dataFinal));

			retorno = query.list();

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
	 * [UC3126] Auditoria de Serviços a Cobrar
	 * 
	 * @author Eduardo Oliveira
	 * @throws ControladorException
	 * @date 08/03/2014
	 */
	public Collection consultarServicosACobrarUsuario(Integer idUsuario, Date dataInicial, Date dataFinal) throws ErroRepositorioException{

		Collection retorno = null;
		Session session = HibernateUtil.getSession();
		StringBuffer consulta = new StringBuffer();

		try{
			consulta.append("SELECT SCITCOL.COLDCCOL  AS codigoOperacaoUsuario, ");
			consulta.append("       SCITAUD.AUDDADOSATU   AS conteudoAtual, ");
			consulta.append("       SCITAUD.AUDDADOSANT   AS conteudoAnterior, ");
			consulta.append("       SCITAUD.AUDCDFUN   AS codigo, ");
			consulta.append("       SCITAUD.AUDTMATU   AS dataHora, ");
			consulta.append("       SCITAUD.AUDTRANSACAO     AS codigoOperacaoTransacao ");
			consulta.append("FROM SCITAUD SCITAUD ");
			consulta.append("INNER JOIN SCITCOL SCITCOL ");
			consulta.append("ON  SCITAUD.AUDCDCOL = SCITCOL.COLCDCOL ");
			consulta.append("WHERE SCITAUD.AUDNNMATUSU =  :idUsuario ");
			consulta.append("AND SCITAUD.AUDTMATU BETWEEN :dataInicial AND :dataFinal ");
			consulta.append("ORDER BY SCITAUD.AUDTMATU,  SCITAUD.AUDCDCOL,  SCITAUD.AUDNNMATUSU ");

			SQLQuery query = session.createSQLQuery(consulta.toString());

			// RETORNO
			query.addScalar("codigoOperacaoUsuario", Hibernate.STRING);
			query.addScalar("conteudoAtual", Hibernate.STRING);
			query.addScalar("conteudoAnterior", Hibernate.STRING);
			query.addScalar("codigo", Hibernate.INTEGER);
			query.addScalar("dataHora", Hibernate.TIMESTAMP);
			query.addScalar("codigoOperacaoTransacao", Hibernate.STRING);

			// PARAMETROS
			query.setInteger("idUsuario", idUsuario);
			query.setTimestamp("dataInicial", Util.formatarDataInicial(dataInicial));
			query.setTimestamp("dataFinal", Util.formatarDataFinal(dataFinal));

			retorno = query.list();

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