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

package gcom.cadastro.cliente;

import gcom.util.*;
import gcom.util.filtro.GeradorHQLCondicional;

import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * O repositório faz a comunicação com a base de dados através do hibernate. O
 * cliente usa o repositório como fonte de dados.
 * 
 * @author Sávio Luiz
 */
public class RepositorioClienteHBM
				implements IRepositorioCliente {

	private static IRepositorioCliente instancia;

	/**
	 * Construtor da classe RepositorioCargaFuncionalidadesHBM
	 */
	private RepositorioClienteHBM() {

	}

	/**
	 * Retorna o valor de instancia
	 * 
	 * @return O valor de instancia
	 */
	public static IRepositorioCliente getInstancia(){

		if(instancia == null){
			instancia = new RepositorioClienteHBM();
		}

		return instancia;
	}

	/**
	 * pesquisa uma coleção de responsavel(is) de acordo com critérios
	 * existentes no filtro
	 * 
	 * @param filtroCliente
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 * @exception ErroRepositorioException
	 *                Description of the Exception
	 */
	public Collection pesquisarResponsavelSuperior(FiltroCliente filtroCliente) throws ErroRepositorioException{

		Collection retorno = null;
		Session session = HibernateUtil.getSession();

		try{

			retorno = new ArrayList(new CopyOnWriteArraySet(GeradorHQLCondicional.gerarCondicionalQuery(filtroCliente,
							"gcom.cadastro.cliente.Cliente", "cliente", "from gcom.cadastro.cliente.Cliente cliente", session).list()));

		}catch(HibernateException e){

			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * Remove todos os telefones de um determinado cliente
	 * 
	 * @param idCliente
	 *            Código do cliente que terá seus telefones apagados
	 * @exception ErroRepositorioException
	 *                Erro no BD
	 */
	public void removerTodosTelefonesPorCliente(Integer idCliente) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();

		try{
			Iterator iterator = session.createQuery("from gcom.cadastro.cliente.ClienteFone where cliente = :cliente").setInteger(
							"cliente", idCliente.intValue()).iterate();

			while(iterator.hasNext()){
				iterator.next();
				iterator.remove();

			}

			session.flush();
		}catch(HibernateException e){

			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

	}

	/**
	 * Remove todos os endereços de um determinado cliente
	 * 
	 * @param idCliente
	 *            Código do cliente que terá seus endereços apagados
	 * @exception ErroRepositorioException
	 *                Erro no BD
	 */
	public void removerTodosEnderecosPorCliente(Integer idCliente) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();

		try{
			Iterator iterator = session.createQuery("from gcom.cadastro.cliente.ClienteEndereco where cliente = :cliente").setInteger(
							"cliente", idCliente.intValue()).iterate();

			while(iterator.hasNext()){
				iterator.next();
				iterator.remove();

			}

			session.flush();
		}catch(HibernateException e){

			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

	}

	public Collection<Cliente> pesquisarClienteDadosClienteEndereco(FiltroCliente filtroCliente, Integer numeroPagina)
					throws ErroRepositorioException{

		Collection<Cliente> retorno = null;

		Session session = HibernateUtil.getSession();

		try{
			retorno = new ArrayList(new CopyOnWriteArraySet<Cliente>(GeradorHQLCondicional.gerarCondicionalQuery(
							filtroCliente,
							"gcom.cadastro.cliente.Cliente",
							"cliente",
							"select distinct cliente from gcom.cadastro.cliente.Cliente cliente "
											+ "left join cliente.clienteEnderecos clienteEndereco",
							// 						
							session).setFirstResult(10 * numeroPagina).setMaxResults(10).list()));

			// Carrega os objetos informados no filtro
			/*
			 * if (!filtroCliente.getColecaoCaminhosParaCarregamentoEntidades()
			 * .isEmpty()) { PersistenciaUtil
			 * .processaObjetosParaCarregamento(filtroCliente
			 * .getColecaoCaminhosParaCarregamentoEntidades(), retorno); }
			 */
		}catch(HibernateException e){

			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	public Collection<Cliente> pesquisarClienteDadosClienteEnderecoRelatorio(FiltroCliente filtroCliente) throws ErroRepositorioException{

		Collection<Cliente> retorno = null;

		Session session = HibernateUtil.getSession();

		try{

			retorno = new ArrayList(new CopyOnWriteArraySet<Cliente>(GeradorHQLCondicional.gerarCondicionalQuery(
							filtroCliente,
							"gcom.cadastro.cliente.Cliente",
							"cliente",
							"select distinct cliente from gcom.cadastro.cliente.Cliente cliente "
											+ "left join cliente.clienteEnderecos clienteEndereco "
											+ "left join cliente.clienteImoveis clienteImovel", session).list()));

			// Carrega os objetos informados no filtro
			/*
			 * if (!filtroCliente.getColecaoCaminhosParaCarregamentoEntidades()
			 * .isEmpty()) { PersistenciaUtil
			 * .processaObjetosParaCarregamento(filtroCliente
			 * .getColecaoCaminhosParaCarregamentoEntidades(), retorno); }
			 */
		}catch(HibernateException e){

			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * <Breve descrição sobre o caso de uso>
	 * <Identificador e nome do caso de uso>
	 * Retrona a quantidade de endereços que existem para o Cliente
	 * pesquisarClienteDadosClienteEnderecoCount
	 * 
	 * @author Roberta Costa
	 * @date 29/06/2006
	 * @param filtroCliente
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarClienteDadosClienteEnderecoCount(FiltroCliente filtroCliente) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();

		ArrayList retornoColecao = null;
		Integer retorno = null;

		try{
			List camposOrderBy = new ArrayList();

			camposOrderBy = filtroCliente.getCamposOrderBy();

			// filtroCliente.limparCamposOrderBy();

			// filtroCliente.getColecaoCaminhosParaCarregamentoEntidades().clear();

			retornoColecao = new ArrayList(new CopyOnWriteArraySet<Cliente>(GeradorHQLCondicional.gerarCondicionalQuery(
							filtroCliente,
							"gcom.cadastro.cliente.Cliente",
							"cliente",
							"select distinct cliente from gcom.cadastro.cliente.Cliente cliente "
											+ "left join cliente.clienteEnderecos clienteEndereco "
											+ "left join cliente.clienteImoveis clienteImovel", session).list()));

			retorno = retornoColecao.size();

			filtroCliente.setCampoOrderBy((String[]) camposOrderBy.toArray(new String[camposOrderBy.size()]));
		}catch(HibernateException e){

			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	// Filtrar Cliente Outros Critérios

	public Collection<Cliente> pesquisarClienteOutrosCriterios(FiltroCliente filtroCliente) throws ErroRepositorioException{

		Collection<Cliente> retorno = null;

		Session session = HibernateUtil.getSession();

		try{

			retorno = new ArrayList(new CopyOnWriteArraySet<Cliente>(GeradorHQLCondicional.gerarCondicionalQuery(
							filtroCliente,
							"gcom.cadastro.cliente.Cliente",
							"cliente",
							"select distinct cliente from gcom.cadastro.cliente.Cliente cliente "
											+ "left join cliente.clienteEnderecos clienteEndereco "
											+ "left join cliente.clienteImoveis clienteImovel", session).list()));

			// Carrega os objetos informados no filtro
			/*
			 * if (!filtroCliente.getColecaoCaminhosParaCarregamentoEntidades()
			 * .isEmpty()) { PersistenciaUtil
			 * .processaObjetosParaCarregamento(filtroCliente
			 * .getColecaoCaminhosParaCarregamentoEntidades(), retorno); }
			 */
		}catch(HibernateException e){

			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0242] - Registrar Movimento de Arrecadadores Author: Sávio Luiz Data:
	 * 01/02/2006
	 * 
	 * @param imovel
	 *            Descrição do parâmetro
	 * @param anoMesReferencia
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 * @exception ErroRepositorioException
	 *                Descrição da exceção
	 */

	public Integer verificarExistenciaCliente(Integer idCliente) throws ErroRepositorioException{

		Integer retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta = null;
		try{
			consulta = "select cliente.id " + "from Cliente cliente " + "where cliente.id = :idCliente";

			retorno = (Integer) session.createQuery(consulta).setInteger("idCliente", idCliente.intValue()).setMaxResults(1).uniqueResult();

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
	 * Pesquisa um cliente carregando os dados do relacionamento com ClienteTipo
	 * [UC0321] Emitir Fatura de Cliente Responsável
	 * 
	 * @author Pedro Alexandre
	 * @date 02/05/2006
	 * @param idCliente
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Cliente pesquisarCliente(Integer idCliente) throws ErroRepositorioException{

		// Cria uma sessão com ohibernate
		Session session = HibernateUtil.getSession();

		// Cria a variável que vai armazenar o cliente responsável
		Cliente cliente = new Cliente();
		String consulta = null;

		try{

			// Monta o HQL
			consulta = "select cliente from Cliente cliente " 
							+ "INNER JOIN FETCH cliente.clienteTipo " 
							+ "LEFT JOIN FETCH cliente.orgaoExpedidorRg " 
							+ "LEFT JOIN FETCH cliente.unidadeFederacao " 
							+ "where cliente.id = :idCliente ";

			// Executa o HQL
			cliente = (Cliente) session.createQuery(consulta).setInteger("idCliente", idCliente).uniqueResult();

			/*
			 * if (cliente != null){ // Carrega o relacionamento entre cliente e
			 * tipo Hibernate.initialize(cliente.getClienteTipo()); }
			 */

			// Erro no hibernate
		}catch(HibernateException e){
			// Levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// Fecha a sessão
			HibernateUtil.closeSession(session);
		}

		// Retorna o cliente pesquisado
		return cliente;
	}

	/**
	 * Pesquisa um cliente pelo id
	 * 
	 * @author Rafael Corrêa
	 * @date 01/08/2006
	 * @return Cliente
	 * @exception ErroRepositorioException
	 *                Erro no hibernate
	 */
	public Object[] pesquisarObjetoClienteRelatorio(Integer idCliente) throws ErroRepositorioException{

		// cria a variável que vai armazenar a coleção pesquisada

		Object[] retorno = null;

		// cria a sessão com o hibernate
		Session session = HibernateUtil.getSession();

		try{
			// cria o HQL para consulta
			String consulta = "select c.clie_id as id, " + "c.clie_nmcliente as nome " + "from cliente c " + "where c.clie_id = "
							+ idCliente.toString();

			// pesquisa a coleção de acordo com o parâmetro informado
			Collection colecaoClientes = session.createSQLQuery(consulta).addScalar("id", Hibernate.INTEGER).addScalar("nome",
							Hibernate.STRING).list();

			retorno = Util.retonarObjetoDeColecaoArray(colecaoClientes);

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
	 * Pesquisa as quantidades de imóveis e as quantidades de economias
	 * associadas a um cliente
	 * 
	 * @author Rafael Corrêa
	 * @date 23/08/2007
	 * @return Object[]
	 * @exception ErroRepositorioException
	 *                Erro no hibernate
	 */
	public Object[] pesquisarQtdeImoveisEEconomiasCliente(Integer idCliente) throws ErroRepositorioException{

		// cria a variável que vai armazenar a coleção pesquisada

		Object[] retorno = null;

		// cria a sessão com o hibernate
		Session session = HibernateUtil.getSession();

		try{
			// cria o HQL para consulta
			String consulta = "SELECT count(imov.imov_id) as qtdeImoveis, " + " sum(imov.imov_qteconomia) as qtdeEconomias "
							+ " FROM imovel imov " + " INNER JOIN cliente_imovel clieImov " + " on imov.imov_id = clieImov.imov_id "
							+ " where clieImov.clie_id = " + idCliente.toString();

			// pesquisa a coleção de acordo com o parâmetro informado
			Collection colecaoClientes = session.createSQLQuery(consulta).addScalar("qtdeImoveis", Hibernate.INTEGER).addScalar(
							"qtdeEconomias", Hibernate.INTEGER).list();

			retorno = Util.retonarObjetoDeColecaoArray(colecaoClientes);

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
	 * Pesquisa todos os telefones de um cliente
	 * 
	 * @author Raphael Rossiter
	 * @date 23/08/2006
	 * @param idCliente
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarClienteFone(Integer idCliente) throws ErroRepositorioException{

		Collection retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta = null;

		try{
			consulta = "SELECT cfon.id,"
							+ // 0
							" cfon.ddd, "
							+ // 1
							" cfon.telefone, "
							+ // 2
							" cfon.indicadorTelefonePadrao, "
							+ // 3
							" fnet.descricao, "
							+ // 4
							" cfon.ultimaAlteracao " // 5
							+ "from ClienteFone cfon " + "left join cfon.cliente clie " + "left join cfon.foneTipo fnet "
							+ "where clie.id = :idCliente ";

			retorno = session.createQuery(consulta).setInteger("idCliente", idCliente.intValue()).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;

	}

	/**
	 * Pesquisa os dados do Cliente
	 * 
	 * @author Rafael Santos
	 * @date 13/09/2006
	 * @author eduardo henrique
	 * @date 04/06/2008
	 *       Adição da Coluna Inscrição Estadual
	 * @param idCliente
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection consultarCliente(Integer idCliente) throws ErroRepositorioException{

		Collection retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta = null;

		try{
			consulta = "SELECT cliente.nome,"
							+ // 0
							" cliente.nomeAbreviado, "
							+ // 1
							" cliente.dataVencimento, "
							+ // 2
							" clienteTipo.descricao,"
							+ // 3
							" clienteTipo.indicadorPessoaFisicaJuridica,"
							+ // 4
							" cliente.email, "
							+ // 5
							" cliente.indicadorAcaoCobranca, "
							+ // 6
							" cliente.cpf, "
							+ // 7
							" cliente.rg, "
							+ // 8
							" cliente.dataEmissaoRg, "
							+ // 9
							" orgaoExpedidorRg.descricaoAbreviada, "
							+ // 10
							" unidadeFederacao.sigla, "
							+ // 11
							" cliente.dataNascimento, "
							+ // 12
							" profissao.descricao, "
							+ // 13
							" pessoaSexo.descricao, "
							+ // 14
							" cliente.cnpj, "
							+ // 15
							" ramoAtividade.descricao, "
							+ // 16
							" clienteResponsavel.id, "
							+ // 17
							" clienteResponsavel.nome, "
							+ // 18
							" cliente.inscricaoEstadual, "
							+ // 19
							" atec.codigo, "
							+ // 20
							" atec.descricao, "
							+ // 21
							" cliente.numeroBeneficio "
							+ // 22
							"from Cliente cliente " + "left join cliente.clienteTipo clienteTipo "
							+ "left join cliente.orgaoExpedidorRg orgaoExpedidorRg "
							+ "left join cliente.unidadeFederacao unidadeFederacao " + "left join cliente.profissao profissao "
							+ "left join cliente.pessoaSexo pessoaSexo " + "left join cliente.ramoAtividade ramoAtividade "
							+ "left join cliente.cliente clienteResponsavel " + "left join cliente.atividadeEconomica atec "
							+ "where cliente.id = :idCliente ";

			retorno = session.createQuery(consulta).setInteger("idCliente", idCliente.intValue()).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;

	}

	/**
	 * Pesquisa todos os endereços do cliente
	 * 
	 * @author Rafael Santos
	 * @date 13/09/2006
	 * @param idCliente
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarEnderecosCliente(Integer idCliente) throws ErroRepositorioException{

		Collection retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta = null;

		try{
			consulta = "SELECT enderecoTipo.descricao,"
							+ // 0
							" clienteEndereco.indicadorEnderecoCorrespondencia "
							+ // 1
							"from ClienteEndereco clienteEndereco " + "left join clienteEndereco.enderecoTipo enderecoTipo "
							+ "where clienteEndereco.cliente.id = :idCliente ";

			retorno = session.createQuery(consulta).setInteger("idCliente", idCliente.intValue()).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;

	}

	/**
	 * [UC0458] - Imprimir Ordem de Serviço
	 * Pesquisa o telefone principal do Cliente para o Relatório de OS
	 * 
	 * @author Rafael Corrêa
	 * @date 17/10/2006
	 * @param idRegistroAtendimento
	 * @throws ErroRepositorioException
	 */

	public Object[] pesquisarClienteFonePrincipal(Integer idCliente) throws ErroRepositorioException{

		Object[] retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = "SELECT cf.ddd, cf.telefone, cf.ramal " + "FROM ClienteFone cf " + "INNER JOIN cf.cliente cli "
							+ "WHERE cli.id = :idCliente AND " + "cf.indicadorTelefonePadrao = " + ClienteFone.INDICADOR_FONE_PADRAO;

			retorno = (Object[]) session.createQuery(consulta).setInteger("idCliente", idCliente).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0582] - Emitir Boletim de Cadastro
	 * Pesquisa os dados do cliente para a emissão do boletim
	 * 
	 * @author Rafael Corrêa
	 * @date 16/05/2007
	 * @param idImovel
	 *            ,
	 *            clienteRelacaoTipo
	 * @throws ErroRepositorioException
	 */

	public Collection pesquisarClienteEmitirBoletimCadastro(Integer idImovel, Integer clienteRelacaoTipo) throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = "SELECT clie.clie_id as id, clie.clie_nmcliente as nome, clie.cltp_id as tipo, " // 0,
							// 1, 2
							+ " clie.clie_nncpf as cpf, clie.clie_nncnpj as cnpj, clie.clie_nnrg as rg, " // 3,
							// 4, 5
							+ " clie.clie_dtrgemissao as dataEmissaoRg, oerg.oerg_dsabreviado as orgaoExpedidorRg, " // 6,
							// 7
							+ " unfe.unfe_dsufsigla as uf, clie.clie_dtnascimento as dataNascimento, " // 8,
							// 9
							+ " profissao.prof_dsprofissao as profissao, clie.psex_id as sexo, clie.clie_nnmae as nomeMae, " // 10,
							// 11,
							// 12
							+ " clie.clie_icuso as icUso, ce.edtp_id as tipoEndereco, ce.logr_id as idLogradouro, " // 13,
							// 14,
							// 15
							+ " cep.cep_cdcep as cep, bairro.bair_cdbairro as bairro, ce.edrf_id as idEnderecoReferencia, " // 16,
							// 17,
							// 18
							+ " ce.cled_nnimovel as numeroImovel, ce.cled_dscomplementoendereco as complemento, " // 19,
							// 20
							+ " cf.fnet_id as idFoneTipo, cf.cfon_cdddd as ddd, cf.cfon_nnfone as numeroTelefone, " // 21,
							// 22,
							// 23
							+ " cf.cfon_nnfoneramal as ramal, ratv.ratv_dsramoatividade as dsRamoAtividade, cltp.cltp_dsclientetipo as dsClienteTipo, " // 24,
																																						// 25,
																																						// 26
							+ " clie.clie_nninscricaoestadual as nuInscricaoEstadual, clie.clie_dsemail as email " // 27
							+ " FROM cliente_imovel ci "
							+ " INNER JOIN cliente clie "
							+ " on clie.clie_id = ci.clie_id "
							+ " LEFT OUTER JOIN profissao profissao "
							+ " on profissao.prof_id = clie.prof_id "
							+ " LEFT OUTER JOIN orgao_expedidor_rg oerg "
							+ " on oerg.oerg_id = clie.oerg_id "
							+ " LEFT OUTER JOIN unidade_federacao unfe "
							+ " on unfe.unfe_id = clie.unfe_id "
							+ " LEFT OUTER JOIN cliente_endereco ce "
							+ " on ce.clie_id = clie.clie_id and ce.cled_icenderecocorrespondencia = "
							+ ClienteEndereco.INDICADOR_ENDERECO_CORRESPONDENCIA
							+ " LEFT OUTER JOIN cep cep "
							+ " on cep.cep_id = ce.cep_id "
							+ " LEFT OUTER JOIN bairro bairro "
							+ " on bairro.bair_id = ce.bair_id "
							+ " LEFT OUTER JOIN cliente_fone cf "
							+ " on cf.clie_id = clie.clie_id "
							+ " LEFT OUTER JOIN ramo_atividade ratv "
							+ " on ratv.ratv_id = clie.ratv_id "
							+ " LEFT OUTER JOIN cliente_tipo cltp "
							+ " on cltp.cltp_id = clie.cltp_id "
							+ " WHERE ci.imov_id = :idImovel and ci.crtp_id = :clienteRelacaoTipo"
							+ " and clim_dtrelacaofim is null "
							+ "ORDER BY cf.cfon_icfonepadrao ";

			retorno = (Collection) session.createSQLQuery(consulta).addScalar("id", Hibernate.INTEGER).addScalar("nome", Hibernate.STRING)
							.addScalar("tipo", Hibernate.INTEGER).addScalar("cpf", Hibernate.STRING).addScalar("cnpj", Hibernate.STRING)
							.addScalar("rg", Hibernate.STRING).addScalar("dataEmissaoRg", Hibernate.DATE).addScalar("orgaoExpedidorRg",
											Hibernate.STRING).addScalar("uf", Hibernate.STRING).addScalar("dataNascimento", Hibernate.DATE)
							.addScalar("profissao", Hibernate.STRING).addScalar("sexo", Hibernate.INTEGER).addScalar("nomeMae",
											Hibernate.STRING).addScalar("icUso", Hibernate.SHORT).addScalar("tipoEndereco",
											Hibernate.INTEGER).addScalar("idLogradouro", Hibernate.INTEGER).addScalar("cep",
											Hibernate.INTEGER).addScalar("bairro", Hibernate.INTEGER).addScalar("idEnderecoReferencia",
											Hibernate.INTEGER).addScalar("numeroImovel", Hibernate.STRING).addScalar("complemento",
											Hibernate.STRING).addScalar("idFoneTipo", Hibernate.INTEGER).addScalar("ddd", Hibernate.STRING)
							.addScalar("numeroTelefone", Hibernate.STRING).addScalar("ramal", Hibernate.STRING)
							.addScalar("dsRamoAtividade", Hibernate.STRING).addScalar("dsClienteTipo", Hibernate.STRING)
							.addScalar("nuInscricaoEstadual", Hibernate.STRING).addScalar("email", Hibernate.STRING)
							.setInteger("idImovel", idImovel)
							.setInteger("clienteRelacaoTipo", clienteRelacaoTipo).setMaxResults(2).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * Usado pelo Filtrar Cliente Filtra o Cliente usando os paramentos
	 * informados
	 * 
	 * @author Rafael Santos
	 * @date 27/11/2006
	 * @author eduardo henrique
	 * @date 04/06/2008
	 *       parâmetro inscricaoEstadual adicionado à consulta
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection filtrarCliente(String codigo, String cpf, String rg, String cnpj, String nome, String nomeMae, String cep,
					String idMunicipio, String idBairro, String idLogradouro, String indicadorUso, String tipoPesquisa,
					String tipoPesquisaNomeMae, String clienteTipo, Integer numeroPagina, String inscricaoEstadual,
					String indicadorContaBraille, String documentoValidado, String numeroBeneficio)
					throws ErroRepositorioException{

		Collection retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta = null;
		try{

			consulta = "select distinct cliente.id,"
							+ // 0
							" cliente.nome,"
							+ // 1
							" cliente.rg,"
							+ // 2
							" cliente.cpf,"
							+ // 3
							" cliente.cnpj, "
							+ // 4
							" clienteTipo.indicadorPessoaFisicaJuridica, "
							+ // 5
							" orgaoExpedidorRg.descricao, "
							+ // 6
							" unidadeFederacao.sigla, "
							+ // 7
							" clienteTipo.descricao, "
							+ // 8
							" cliente.indicadorUso, "
							+ // 9
							" cliente.indicadorContaBraille, "
							+ // 10
							" cliente.documentoValidado "

							+ "from Cliente cliente " + "left join cliente.clienteTipo clienteTipo "
							+ "left join cliente.orgaoExpedidorRg orgaoExpedidorRg "
							+ "left join cliente.unidadeFederacao unidadeFederacao ";

			/*
			 * ## JOIN ##
			 */
			// join necessários
			// join facultativos
			// cep
			if(cep != null && !cep.equals("")
							&& !cep.trim().equalsIgnoreCase(new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO).toString())){
				consulta = consulta + " left join cliente.clienteEnderecos clienteEnderecos "
								+ " left join clienteEnderecos.logradouroCep logradouroCep " + "left join logradouroCep.cep cep ";
			}
			// bairro
			if(idBairro != null && !idBairro.equals("")
							&& !idBairro.trim().equalsIgnoreCase(new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO).toString())){
				consulta = consulta + " left join cliente.clienteEnderecos clienteEnderecos "
								+ " left join clienteEnderecos.logradouroBairro logradouroBairro "
								+ " left join logradouroBairro.bairro bairro ";
			}

			// logradouro
			if(idLogradouro != null && !idLogradouro.equals("")
							&& !idLogradouro.trim().equalsIgnoreCase(new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO).toString())){
				consulta = consulta + " left join cliente.clienteEnderecos clienteEnderecos "
								+ " left join clienteEnderecos.logradouroCep logradouroCep "
								+ " left join logradouroCep.logradouro logradouro ";
			}

			// municipio
			if(idMunicipio != null && !idMunicipio.equals("")
							&& !idMunicipio.trim().equalsIgnoreCase(new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO).toString())){
				consulta = consulta + " left join cliente.clienteEnderecos clienteEnderecos "
								+ " left join clienteEnderecos.logradouroBairro logradouroBairro "
								+ " left join logradouroBairro.bairro bairro " + "left join bairro.municipio municipio ";
			}

			/*
			 * ## CONDIÇÕES ##
			 */
			consulta = consulta + " where   ";

			// codigo
			if(codigo != null && !codigo.equals("")
							&& !codigo.trim().equalsIgnoreCase(new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO).toString())){
				consulta = consulta + " cliente.id = :codigo  and  ";
			}
			// cpf
			if((cpf != null && !cpf.equals("") && !cpf.trim().equalsIgnoreCase(
							new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO).toString()))){
				consulta = consulta + " cliente.cpf = :cpf  and  ";
			}
			// rg
			if((rg != null && !rg.equals("") && !rg.trim().equalsIgnoreCase(new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO).toString()))){
				consulta = consulta + " cliente.rg = :rg  and  ";
			}
			// cnpj
			if((cnpj != null && !cnpj.equals("") && !cnpj.trim().equalsIgnoreCase(
							new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO).toString()))){
				consulta = consulta + " cliente.cnpj = :cnpj and  ";
			}
			// nome
			if(nome != null && !nome.equals("")){
				if(tipoPesquisa != null && tipoPesquisa.equals(ConstantesSistema.TIPO_PESQUISA_COMPLETA.toString())){
					consulta = consulta + " upper(cliente.nome) like '%" + nome.toUpperCase() + "%'  and  ";
				}else{
					consulta = consulta + " upper(cliente.nome) like '" + nome.toUpperCase() + "%'  and  ";
				}
			}

			// nomeMae
			if(nomeMae != null && !nomeMae.equals("")){
				if(tipoPesquisaNomeMae != null && tipoPesquisaNomeMae.equals(ConstantesSistema.TIPO_PESQUISA_COMPLETA.toString())){
					consulta = consulta + " upper(cliente.nomeMae) like '%" + nomeMae.toUpperCase() + "%'  and  ";
				}else{
					consulta = consulta + " upper(cliente.nomeMae) like '" + nomeMae.toUpperCase() + "%'  and  ";
				}
			}

			// municipio
			if(idMunicipio != null && !idMunicipio.equals("")
							&& !idMunicipio.trim().equalsIgnoreCase(new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO).toString())){
				consulta = consulta + " municipio.id = :idMunicipio  and  ";
			}

			// cep
			if(cep != null && !cep.equals("")
							&& !cep.trim().equalsIgnoreCase(new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO).toString())){
				consulta = consulta + " cep.codigo = :cep  and  ";
			}

			// bairro
			if(idBairro != null && !idBairro.equals("")
							&& !idBairro.trim().equalsIgnoreCase(new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO).toString())){
				consulta = consulta + " bairro.codigo = :idBairro  and  ";
			}

			// logradouro
			if(idLogradouro != null && !idLogradouro.equals("")
							&& !idLogradouro.trim().equalsIgnoreCase(new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO).toString())){
				consulta = consulta + " logradouro.id = :idLogradouro  and  ";
			}

			// indicador de uso
			if((indicadorUso != null && !indicadorUso.equals("") && !indicadorUso.trim().equalsIgnoreCase(
							new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO).toString()))){
				consulta = consulta + " cliente.indicadorUso = :indicadorUso  and  ";
			}

			// cliente Tipo
			if((clienteTipo != null && !clienteTipo.equals("") && !clienteTipo.trim().equalsIgnoreCase(
							new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO).toString()))){
				consulta = consulta + " clienteTipo.id = :clienteTipo  and  ";
			}

			// inscricao Estadual
			if((inscricaoEstadual != null && !inscricaoEstadual.equals("") && !inscricaoEstadual.trim().equalsIgnoreCase(
							new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO).toString()))){
				consulta = consulta + " cliente.inscricaoEstadual = :inscricaoEstadual and  ";
			}

			// indicador conta braille
			if((indicadorContaBraille != null && !indicadorContaBraille.equals("") && !indicadorContaBraille.equals(ConstantesSistema.TODOS.toString()))){
				consulta = consulta + " cliente.indicadorContaBraille = :indicadorContaBraille and  ";
			}

			// Documento Validado
			if((documentoValidado != null && !documentoValidado.equals(""))){
				consulta = consulta + " cliente.documentoValidado = :documentoValidado and  ";
			}

			// Número do Benefíco
			if(!Util.isVazioOuBranco(numeroBeneficio)){

				consulta = consulta + " cliente.numeroBeneficio = :numeroBeneficio and  ";
			}

			consulta = consulta.substring(0, (consulta.length() - 5));

			consulta = consulta + " order by cliente.nome ";

			Query query = session.createQuery(consulta);

			// seta os valores na condição where

			// codigo
			if(codigo != null && !codigo.equals("")
							&& !codigo.trim().equalsIgnoreCase(new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO).toString())){
				query.setInteger("codigo", new Integer(codigo).intValue());
			}
			// cpf
			if((cpf != null && !cpf.equals("") && !cpf.trim().equalsIgnoreCase(
							new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO).toString()))){
				query.setString("cpf", cpf);
			}
			// rg
			if((rg != null && !rg.equals("") && !rg.trim().equalsIgnoreCase(new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO).toString()))){
				query.setString("rg", rg);
			}
			// cnpj
			if((cnpj != null && !cnpj.equals("") && !cnpj.trim().equalsIgnoreCase(
							new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO).toString()))){
				query.setString("cnpj", cnpj);
			}
			// nome
			// if (nome != null && !nome.equals("")) {
			// query.setString("nome",nome);
			// }

			// / nomeMae
			// if (nomeMae != null
			// && !nomeMae.equals("")) {
			// query.setString("nomeMae",nomeMae);
			// }

			// municipio
			if(idMunicipio != null && !idMunicipio.equals("")
							&& !idMunicipio.trim().equalsIgnoreCase(new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO).toString())){
				query.setInteger("idMunicipio", new Integer(idMunicipio).intValue());
			}

			// cep
			if(cep != null && !cep.equals("")
							&& !cep.trim().equalsIgnoreCase(new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO).toString())){
				query.setInteger("cep", new Integer(cep).intValue());
			}

			// bairro
			if(idBairro != null && !idBairro.equals("")
							&& !idBairro.trim().equalsIgnoreCase(new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO).toString())){
				query.setInteger("idBairro", new Integer(idBairro).intValue());
			}

			// logradouro
			if(idLogradouro != null && !idLogradouro.equals("")
							&& !idLogradouro.trim().equalsIgnoreCase(new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO).toString())){
				query.setInteger("idLogradouro", new Integer(idLogradouro).intValue());
			}

			// indicador de uso
			if((indicadorUso != null && !indicadorUso.equals("") && !indicadorUso.trim().equalsIgnoreCase(
							new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO).toString()))){
				query.setShort("indicadorUso", new Short(indicadorUso).shortValue());
			}

			// cliente Tipo
			if((clienteTipo != null && !clienteTipo.equals("") && !clienteTipo.trim().equalsIgnoreCase(
							new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO).toString()))){
				query.setInteger("clienteTipo", new Integer(clienteTipo).shortValue());
			}

			// inscricao Estadual
			if((inscricaoEstadual != null && !inscricaoEstadual.equals("") && !inscricaoEstadual.trim().equalsIgnoreCase(
							new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO).toString()))){
				query.setString("inscricaoEstadual", inscricaoEstadual);
			}

			// inscricao Estadual
			if((indicadorContaBraille != null && !indicadorContaBraille.equals("") && !indicadorContaBraille.equals(ConstantesSistema.TODOS
							.toString()))){
				query.setString("indicadorContaBraille", indicadorContaBraille);
			}

			// Documento Validado
			if((documentoValidado != null && !documentoValidado.equals(""))){
				query.setString("documentoValidado", documentoValidado);
			}

			// Número do Benefíco
			if(!Util.isVazioOuBranco(numeroBeneficio)){

				query.setString("numeroBeneficio", numeroBeneficio);
			}

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
	 * Usado pelo Filtrar Cliente Filtra a quantidade de Clientes usando os
	 * paramentos informados
	 * 
	 * @author Rafael Santos
	 * @date 27/11/2006
	 * @author eduardo henrique
	 * @date 04/06/2008
	 *       Adição do parâmetro de Inscrição Estadual para Consulta
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Object filtrarQuantidadeCliente(String codigo, String cpf, String rg, String cnpj, String nome, String nomeMae, String cep,
					String idMunicipio, String idBairro, String idLogradouro, String indicadorUso, String tipoPesquisa,

					String tipoPesquisaNomeMae, String clienteTipo, String inscricaoEstadual, String indicadorContaBraille,
					String documentoValidado, String numeroBeneficio)
					throws ErroRepositorioException{

		Object retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta = null;
		try{

			consulta = "select count(distinct cliente.id) " + "from Cliente cliente ";

			/*
			 * ## JOIN ##
			 */
			// join necessários
			// join facultativos
			// cliente Tipo
			if((clienteTipo != null && !clienteTipo.equals("") && !clienteTipo.trim().equalsIgnoreCase(
							new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO).toString()))){
				consulta = consulta + " left join cliente.clienteTipo clienteTipo ";
			}
			// cep
			if(cep != null && !cep.equals("")
							&& !cep.trim().equalsIgnoreCase(new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO).toString())){
				consulta = consulta + " left join cliente.clienteEnderecos clienteEnderecos "
								+ " left join clienteEnderecos.logradouroCep logradouroCep " + "left join logradouroCep.cep cep ";
			}
			// bairro
			if(idBairro != null && !idBairro.equals("")
							&& !idBairro.trim().equalsIgnoreCase(new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO).toString())){
				consulta = consulta + " left join cliente.clienteEnderecos clienteEnderecos "
								+ " left join clienteEnderecos.logradouroBairro logradouroBairro "
								+ " left join logradouroBairro.bairro bairro ";
			}

			// logradouro
			if(idLogradouro != null && !idLogradouro.equals("")
							&& !idLogradouro.trim().equalsIgnoreCase(new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO).toString())){
				consulta = consulta + " left join cliente.clienteEnderecos clienteEnderecos "
								+ " left join clienteEnderecos.logradouroCep logradouroCep "
								+ " left join logradouroCep.logradouro logradouro ";
			}

			// municipio
			if(idMunicipio != null && !idMunicipio.equals("")
							&& !idMunicipio.trim().equalsIgnoreCase(new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO).toString())){
				consulta = consulta + " left join cliente.clienteEnderecos clienteEnderecos "
								+ " left join clienteEnderecos.logradouroBairro logradouroBairro "
								+ " left join logradouroBairro.bairro bairro " + "left join bairro.municipio municipio ";
			}

			/*
			 * ## CONDIÇÕES ##
			 */
			consulta = consulta + " where   ";

			// codigo
			if(codigo != null && !codigo.equals("")
							&& !codigo.trim().equalsIgnoreCase(new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO).toString())){
				consulta = consulta + " cliente.id = :codigo  and  ";
			}
			// cpf
			if((cpf != null && !cpf.equals("") && !cpf.trim().equalsIgnoreCase(
							new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO).toString()))){
				consulta = consulta + " cliente.cpf = :cpf  and  ";
			}
			// rg
			if((rg != null && !rg.equals("") && !rg.trim().equalsIgnoreCase(new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO).toString()))){
				consulta = consulta + " cliente.rg = :rg  and  ";
			}
			// cnpj
			if((cnpj != null && !cnpj.equals("") && !cnpj.trim().equalsIgnoreCase(
							new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO).toString()))){
				consulta = consulta + " cliente.cnpj = :cnpj and  ";
			}
			// nome
			if(nome != null && !nome.equals("")){
				if(tipoPesquisa != null && tipoPesquisa.equals(ConstantesSistema.TIPO_PESQUISA_COMPLETA.toString())){
					consulta = consulta + " upper(cliente.nome) like '%" + nome.toUpperCase() + "%'  and  ";
				}else{
					consulta = consulta + " upper(cliente.nome) like '" + nome.toUpperCase() + "%'  and  ";
				}
			}

			// nomeMae
			if(nomeMae != null && !nomeMae.equals("")){
				if(tipoPesquisa != null && tipoPesquisa.equals(ConstantesSistema.TIPO_PESQUISA_COMPLETA.toString())){
					consulta = consulta + " upper(cliente.nomeMae) like '%" + nomeMae.toUpperCase() + "%'  and  ";
				}else{
					consulta = consulta + " upper(cliente.nomeMae) like '" + nomeMae.toUpperCase() + "%'  and  ";
				}
			}

			// municipio
			if(idMunicipio != null && !idMunicipio.equals("")
							&& !idMunicipio.trim().equalsIgnoreCase(new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO).toString())){
				consulta = consulta + " municipio.id = :idMunicipio  and  ";
			}

			// cep
			if(cep != null && !cep.equals("")
							&& !cep.trim().equalsIgnoreCase(new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO).toString())){
				consulta = consulta + " cep.codigo = :cep  and  ";
			}

			// bairro
			if(idBairro != null && !idBairro.equals("")
							&& !idBairro.trim().equalsIgnoreCase(new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO).toString())){
				consulta = consulta + " bairro.codigo = :idBairro  and  ";
			}

			// logradouro
			if(idLogradouro != null && !idLogradouro.equals("")
							&& !idLogradouro.trim().equalsIgnoreCase(new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO).toString())){
				consulta = consulta + " logradouro.id = :idLogradouro  and  ";
			}

			// indicador de uso
			if((indicadorUso != null && !indicadorUso.equals("") && !indicadorUso.trim().equalsIgnoreCase(
							new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO).toString()))){
				consulta = consulta + " cliente.indicadorUso = :indicadorUso  and  ";
			}

			// cliente Tipo
			if((clienteTipo != null && !clienteTipo.equals("") && !clienteTipo.trim().equalsIgnoreCase(
							new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO).toString()))){
				consulta = consulta + " clienteTipo.id = :clienteTipo  and  ";
			}

			// inscricao estadual
			if((inscricaoEstadual != null && !inscricaoEstadual.equals("") && !inscricaoEstadual.trim().equalsIgnoreCase(
							new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO).toString()))){
				consulta = consulta + " cliente.inscricaoEstadual = :inscricaoEstadual and  ";
			}

			// indicador conta braille
			if((!Util.isVazioOuBranco(indicadorContaBraille) && !indicadorContaBraille.equals(ConstantesSistema.TODOS.toString()))){
				consulta = consulta + " cliente.indicadorContaBraille = :indicadorContaBraille and  ";
			}

			// Documento Validado
			if((documentoValidado != null && !documentoValidado.equals(""))){

				consulta = consulta + " cliente.documentoValidado = :documentoValidado and  ";
			}

			// Número do Benefíco
			if(!Util.isVazioOuBranco(numeroBeneficio)){

				consulta = consulta + " cliente.numeroBeneficio = :numeroBeneficio and  ";
			}

			Query query = session.createQuery(consulta.substring(0, (consulta.length() - 5)));

			// seta os valores na condição where

			// codigo
			if(codigo != null && !codigo.equals("")
							&& !codigo.trim().equalsIgnoreCase(new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO).toString())){
				query.setInteger("codigo", new Integer(codigo).intValue());
			}
			// cpf
			if((cpf != null && !cpf.equals("") && !cpf.trim().equalsIgnoreCase(
							new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO).toString()))){
				query.setString("cpf", cpf);
			}
			// rg
			if((rg != null && !rg.equals("") && !rg.trim().equalsIgnoreCase(new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO).toString()))){
				query.setString("rg", rg);
			}
			// cnpj
			if((cnpj != null && !cnpj.equals("") && !cnpj.trim().equalsIgnoreCase(
							new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO).toString()))){
				query.setString("cnpj", cnpj);
			}
			// nome
			// if (nome != null && !nome.equals("")) {
			// query.setString("nome",nome);
			// }

			// / nomeMae
			// if (nomeMae != null
			// && !nomeMae.equals("")) {
			// query.setString("nomeMae",nomeMae);
			// }

			// municipio
			if(idMunicipio != null && !idMunicipio.equals("")
							&& !idMunicipio.trim().equalsIgnoreCase(new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO).toString())){
				query.setInteger("idMunicipio", new Integer(idMunicipio).intValue());
			}

			// cep
			if(cep != null && !cep.equals("")
							&& !cep.trim().equalsIgnoreCase(new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO).toString())){
				query.setInteger("cep", new Integer(cep).intValue());
			}

			// bairro
			if(idBairro != null && !idBairro.equals("")
							&& !idBairro.trim().equalsIgnoreCase(new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO).toString())){
				query.setInteger("idBairro", new Integer(idBairro).intValue());
			}

			// logradouro
			if(idLogradouro != null && !idLogradouro.equals("")
							&& !idLogradouro.trim().equalsIgnoreCase(new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO).toString())){
				query.setInteger("idLogradouro", new Integer(idLogradouro).intValue());
			}

			// indicador de uso
			if((indicadorUso != null && !indicadorUso.equals("") && !indicadorUso.trim().equalsIgnoreCase(
							new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO).toString()))){
				query.setShort("indicadorUso", new Short(indicadorUso).shortValue());
			}
			// cliente Tipo
			if((clienteTipo != null && !clienteTipo.equals("") && !clienteTipo.trim().equalsIgnoreCase(
							new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO).toString()))){
				query.setInteger("clienteTipo", new Integer(clienteTipo).shortValue());
			}
			// inscricao Estadual
			if((inscricaoEstadual != null && !inscricaoEstadual.equals("") && !inscricaoEstadual.trim().equalsIgnoreCase(
							new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO).toString()))){
				query.setString("inscricaoEstadual", inscricaoEstadual);
			}
			// indicador conta braille
			if((!Util.isVazioOuBranco(indicadorContaBraille) && !indicadorContaBraille.equals(ConstantesSistema.TODOS.toString()))){
				query.setString("indicadorContaBraille", indicadorContaBraille);
			}

			// Documento Validado
			if((documentoValidado != null && !documentoValidado.equals(""))){

				query.setString("documentoValidado", documentoValidado);
			}

			// Número do Benefíco
			if(!Util.isVazioOuBranco(numeroBeneficio)){

				query.setString("numeroBeneficio", numeroBeneficio);
			}

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
	 * [UC0214] Efetuar Parcelamento de Débitos
	 * 
	 * @author Vivianne Sousa
	 * @date 27/07/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Object[] obterIdENomeCliente(String cpf) throws ErroRepositorioException{

		Object[] retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta = null;
		try{

			consulta = "select distinct cliente.id," // 0
							+ " cliente.nome " // 1
							+ "from Cliente cliente " + "where cliente.cpf = :cpf ";

			retorno = (Object[]) session.createQuery(consulta).setString("cpf", cpf).setMaxResults(1).uniqueResult();

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
	 * [UC0214] Efetuar Parcelamento de Débitos
	 * 
	 * @author Vivianne Sousa
	 * @date 30/07/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public void atualizarCpfCnpjCliente(String cpfCnpj, Integer idCliente, String indicadorPessoaFisicaJuridica)
					throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		StringBuffer update = new StringBuffer();
		try{
			String complemento = "";

			if(ClienteTipo.INDICADOR_PESSOA_FISICA.toString().equals(indicadorPessoaFisicaJuridica)){
				complemento = " clie_nncpf = :cpfCnpj ";
			}else{
				complemento = " clie_nncnpj = :cpfCnpj ";
			}

			update.append(" update gcom.cadastro.cliente.Cliente set clie_tmultimaalteracao = :ultimaAlteracao, ");
			update.append(complemento);
			update.append(" where clie_id = :idCliente ");

			session.createQuery(update.toString()).setString("cpfCnpj", cpfCnpj).setInteger("idCliente", idCliente).setTimestamp(
							"ultimaAlteracao", new Date()).executeUpdate();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

	}

	/**
	 * [UC0864] Gerar Certidão Negativa por Cliente
	 * 
	 * @author Rafael Corrêa
	 * @date 25/09/2008
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<Integer> pesquisarClientesAssociadosResponsavel(Integer idCliente) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		Collection<Integer> retorno = null;

		String consulta = null;

		try{

			consulta = "SELECT DISTINCT cliente.id " + "FROM Cliente cliente " + "INNER JOIN cliente.cliente clienteResponsavel "
							+ "WHERE clienteResponsavel.id = :idCliente and cliente.id <> :idCliente ";

			retorno = session.createQuery(consulta).setInteger("idCliente", idCliente).list();

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
	 * @author Andre Nishimura
	 * @date 27/01/2011
	 */
	public Collection pesquisarCadastroClientesRedundantes() throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		String hql;
		Collection retorno;
		try{
			hql = "select c.id, c.cpf,c.cnpj, c.ultimaAlteracao, c.nome from Cliente c where (c.cpf is not null or c.cnpj is not null) order by c.cpf,c.cnpj, c.nome, c.ultimaAlteracao desc";
			retorno = session.createQuery(hql).list();
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	public Integer atualizaClientesRedundantesArrecadador(Cliente clienteASerMantido, Collection clientesASeremSubstituidos)
					throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		Integer retorno;
		try{
			String hql = "update Arrecadador a set a.cliente = :idClienteASerMantido where a.cliente in (:idsClientesASeremSubstituidos)";

			retorno = session.createQuery(hql).setParameter("idClienteASerMantido", clienteASerMantido).setParameterList(
							"idsClientesASeremSubstituidos", clientesASeremSubstituidos).executeUpdate();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	public Integer atualizaClientesRedundantesArrecadadorContrato(Cliente clienteASerMantido, Collection clientesASeremSubstituidos)
					throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		Integer retorno;
		try{
			String hql = "update ArrecadadorContrato a set a.cliente = :idClienteASerMantido where a.cliente in (:idsClientesASeremSubstituidos)";

			retorno = session.createQuery(hql).setParameter("idClienteASerMantido", clienteASerMantido).setParameterList(
							"idsClientesASeremSubstituidos", clientesASeremSubstituidos).executeUpdate();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	public Integer atualizaClientesRedundantesAtendimento(Cliente clienteASerMantido, Collection clientesASeremSubstituidos)
					throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		Integer retorno;
		try{
			String hql = "update AtendimentoIncompleto a set a.cliente = :idClienteASerMantido where a.cliente in (:idsClientesASeremSubstituidos)";

			retorno = session.createQuery(hql).setParameter("idClienteASerMantido", clienteASerMantido).setParameterList(
							"idsClientesASeremSubstituidos", clientesASeremSubstituidos).executeUpdate();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	public Integer atualizaClientesRedundantesClienteConta(Cliente clienteASerMantido, Collection clientesASeremSubstituidos)
					throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		Integer retorno;
		try{
			String hql = "update ClienteConta a set a.cliente = :idClienteASerMantido where a.cliente in (:idsClientesASeremSubstituidos)";

			retorno = session.createQuery(hql).setParameter("idClienteASerMantido", clienteASerMantido).setParameterList(
							"idsClientesASeremSubstituidos", clientesASeremSubstituidos).executeUpdate();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	public Integer atualizaClientesRedundantesClienteContaHistorico(Cliente clienteASerMantido, Collection clientesASeremSubstituidos)
					throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		Integer retorno;
		try{
			String hql = "update ClienteContaHistorico a set a.cliente = :idClienteASerMantido where a.cliente in (:idsClientesASeremSubstituidos)";

			retorno = session.createQuery(hql).setParameter("idClienteASerMantido", clienteASerMantido).setParameterList(
							"idsClientesASeremSubstituidos", clientesASeremSubstituidos).executeUpdate();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	public Integer atualizaClientesRedundantesClienteEndereco(Cliente clienteASerMantido, Collection clientesASeremSubstituidos)
					throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		Integer retorno;
		try{
			String hql = "update ClienteEndereco a set a.cliente = :idClienteASerMantido where a.cliente in (:idsClientesASeremSubstituidos)";

			retorno = session.createQuery(hql).setParameter("idClienteASerMantido", clienteASerMantido).setParameterList(
							"idsClientesASeremSubstituidos", clientesASeremSubstituidos).executeUpdate();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	public Integer atualizaClientesRedundantesClienteFone(Cliente clienteASerMantido, Collection clientesASeremSubstituidos)
					throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		Integer retorno;
		try{
			String hql = "update ClienteFone a set a.cliente = :idClienteASerMantido where a.cliente in (:idsClientesASeremSubstituidos)";

			retorno = session.createQuery(hql).setParameter("idClienteASerMantido", clienteASerMantido).setParameterList(
							"idsClientesASeremSubstituidos", clientesASeremSubstituidos).executeUpdate();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	public Integer atualizaClientesRedundantesClienteGuiaPagamento(Cliente clienteASerMantido, Collection clientesASeremSubstituidos)
					throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		Integer retorno = 0;
		try{

			Collection temp = new ArrayList();

			String hql1 = "SELECT a.guiaPagamento.id,a.clienteRelacaoTipo.id " + "FROM ClienteGuiaPagamento a "
							+ "WHERE a.cliente in( :clientes) "
							+ "GROUP BY (a.guiaPagamento.id,a.clienteRelacaoTipo.id) HAVING count(*) > 1";
			temp.addAll(clientesASeremSubstituidos);
			temp.add(clienteASerMantido);
			List listTemp = session.createQuery(hql1).setParameterList("clientes", temp).list();

			if(listTemp != null && !listTemp.isEmpty()){
				for(Object[] linha : (List<Object[]>) listTemp){

					// verifica se o cliente a ser mantido ja possui referencia para a guia
					hql1 = "select a from ClienteGuiaPagamento a "
									+ "where a.cliente = (:clienteASerMantido) and a.guiaPagamento.id =:idGuiaPag and a.clienteRelacaoTipo = :idCltp";

					Collection temp2 = session.createQuery(hql1).setParameter("clienteASerMantido", clienteASerMantido).setParameter(
									"idGuiaPag", linha[0]).setParameter("idCltp", linha[1]).list();
					if(temp2 == null || temp2.isEmpty()){
						Cliente c1 = (Cliente) Util.retonarObjetoDeColecao(clientesASeremSubstituidos);
						String consulta = "select a from ClienteGuiaPagamento a "
										+ "where a.cliente in(:clientesASeremSubstituidos) and a.guiaPagamento.id =:idGuiaPag and a.clienteRelacaoTipo = :idCltp";
						Collection temp3 = session.createQuery(consulta).setParameterList("clientesASeremSubstituidos",
										clientesASeremSubstituidos).setParameter("idGuiaPag", linha[0]).setParameter("idCltp", linha[1])
										.list();
						boolean atualizouUm = false;
						for(ClienteGuiaPagamento item : (Collection<ClienteGuiaPagamento>) temp3){
							if(!atualizouUm){
								item.setCliente(clienteASerMantido);
								session.update(item);
								atualizouUm = true;
							}else{
								session.delete(item);
							}
						}
						retorno = temp3.size();
					}else{
						String delete = "delete from ClienteGuiaPagamento a where a.cliente in (:clientesASeremSubstituidos) and a.guiaPagamento.id =:idGuiaPag and a.clienteRelacaoTipo = :idCltp";
						session.createQuery(delete).setParameterList("clientesASeremSubstituidos", clientesASeremSubstituidos)
										.setParameter("idGuiaPag", linha[0]).setParameter("idCltp", linha[1]).executeUpdate();
					}

				}
				//
				// hql1 =
				// "delete from ClienteGuiaPagamento a where a.guiaPagamento.id in (:guiasPagamento) and a.cliente in (:clientesASeremSubstituidos)";
				// session.createQuery(hql1).setParameterList("guiasPagamento",
				// temp).setParameterList("clientesASeremSubstituidos",
				// clientesASeremSubstituidos).executeUpdate();
			}

			String hql = "update ClienteGuiaPagamento a set a.cliente = :idClienteASerMantido where a.cliente in (:idsClientesASeremSubstituidos)";

			retorno = session.createQuery(hql).setParameter("idClienteASerMantido", clienteASerMantido).setParameterList(
							"idsClientesASeremSubstituidos", clientesASeremSubstituidos).executeUpdate();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	public Integer atualizaClientesRedundantesClienteGuiaPagamentoHist(Cliente clienteASerMantido, Collection clientesASeremSubstituidos)
					throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		Integer retorno;
		try{
			String hql = "update ClienteGuiaPagamentoHistorico a set a.cliente = :idClienteASerMantido where a.cliente in (:idsClientesASeremSubstituidos)";

			retorno = session.createQuery(hql).setParameter("idClienteASerMantido", clienteASerMantido).setParameterList(
							"idsClientesASeremSubstituidos", clientesASeremSubstituidos).executeUpdate();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	public Integer atualizaClientesRedundantesClienteImovel(Cliente clienteASerMantido, Collection clientesASeremSubstituidos)
					throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		Integer retorno;
		try{
			// String consulta = "select a from ClienteImovel a where a.cliente in (:cliente)";
			// Collection t = new ArrayList();
			// t.addAll(clientesASeremSubstituidos);
			// t.add(clienteASerMantido);
			// Collection r = session.createQuery(consulta).setParameter("cliente",
			// clienteASerMantido).list();

			String hql = "update ClienteImovel a set a.cliente = :idClienteASerMantido where a.cliente in (:idsClientesASeremSubstituidos)";

			retorno = session.createQuery(hql).setParameter("idClienteASerMantido", clienteASerMantido).setParameterList(
							"idsClientesASeremSubstituidos", clientesASeremSubstituidos).executeUpdate();

		}catch(HibernateException e){
			System.out.print("idCliente: " + clienteASerMantido.getId());
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	public Integer atualizaClientesRedundantesClienteImovelEconomia(Cliente clienteASerMantido, Collection clientesASeremSubstituidos)
					throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		Integer retorno;
		try{
			String hql = "update ClienteImovelEconomia a set a.cliente = :idClienteASerMantido where a.cliente in (:idsClientesASeremSubstituidos)";

			retorno = session.createQuery(hql).setParameter("idClienteASerMantido", clienteASerMantido).setParameterList(
							"idsClientesASeremSubstituidos", clientesASeremSubstituidos).executeUpdate();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	public Integer atualizaClientesRedundantesCobrancaAcaoAtividadeComando(Cliente clienteASerMantido, Collection clientesASeremSubstituidos)
					throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		Integer retorno;
		try{
			String hql1 = "update CobrancaAcaoAtividadeComando a set a.cliente = :idClienteASerMantido where a.cliente in (:idsClientesASeremSubstituidos)";

			retorno = session.createQuery(hql1).setParameter("idClienteASerMantido", clienteASerMantido).setParameterList(
							"idsClientesASeremSubstituidos", clientesASeremSubstituidos).executeUpdate();

			String hql2 = "update CobrancaAcaoAtividadeComando a set a.superior = :idClienteASerMantido where a.superior in (:idsClientesASeremSubstituidos)";

			retorno = retorno
							+ session.createQuery(hql2).setParameter("idClienteASerMantido", clienteASerMantido).setParameterList(
											"idsClientesASeremSubstituidos", clientesASeremSubstituidos).executeUpdate();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	public Integer atualizaClientesRedundantesCobrancaDocumento(Cliente clienteASerMantido, Collection clientesASeremSubstituidos)
					throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		Integer retorno;
		try{
			String hql = "update CobrancaDocumento a set a.cliente = :idClienteASerMantido where a.cliente in (:idsClientesASeremSubstituidos)";

			retorno = session.createQuery(hql).setParameter("idClienteASerMantido", clienteASerMantido).setParameterList(
							"idsClientesASeremSubstituidos", clientesASeremSubstituidos).executeUpdate();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	public Integer atualizaClientesRedundantesContaImpressao(Cliente clienteASerMantido, Collection clientesASeremSubstituidos)
					throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		Integer retorno;
		try{
			String hql = "update ContaImpressao a set a.clienteResponsavel = :idClienteASerMantido where a.clienteResponsavel in (:idsClientesASeremSubstituidos)";

			retorno = session.createQuery(hql).setParameter("idClienteASerMantido", clienteASerMantido).setParameterList(
							"idsClientesASeremSubstituidos", clientesASeremSubstituidos).executeUpdate();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	public Integer atualizaClientesRedundantesDevolucao(Cliente clienteASerMantido, Collection clientesASeremSubstituidos)
					throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		Integer retorno;
		try{
			String hql = "update Devolucao a set a.cliente = :idClienteASerMantido where a.cliente in (:idsClientesASeremSubstituidos)";

			retorno = session.createQuery(hql).setParameter("idClienteASerMantido", clienteASerMantido).setParameterList(
							"idsClientesASeremSubstituidos", clientesASeremSubstituidos).executeUpdate();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	public Integer atualizaClientesRedundantesDevolucaoHistorico(Cliente clienteASerMantido, Collection clientesASeremSubstituidos)
					throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		Integer retorno;
		try{
			String hql = "update DevolucaoHistorico a set a.cliente = :idClienteASerMantido where a.cliente in (:idsClientesASeremSubstituidos)";

			retorno = session.createQuery(hql).setParameter("idClienteASerMantido", clienteASerMantido).setParameterList(
							"idsClientesASeremSubstituidos", clientesASeremSubstituidos).executeUpdate();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	public Integer atualizaClientesRedundantesDocumentoNaoEntregue(Cliente clienteASerMantido, Collection clientesASeremSubstituidos)
					throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		Integer retorno;
		try{
			String hql = "update DocumentoNaoEntregue a set a.cliente = :idClienteASerMantido where a.cliente in (:idsClientesASeremSubstituidos)";

			retorno = session.createQuery(hql).setParameter("idClienteASerMantido", clienteASerMantido).setParameterList(
							"idsClientesASeremSubstituidos", clientesASeremSubstituidos).executeUpdate();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	public Integer atualizaClientesRedundantesEntidadeBeneficente(Cliente clienteASerMantido, Collection clientesASeremSubstituidos)
					throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		Integer retorno;
		try{
			String hql = "update EntidadeBeneficente a set a.cliente = :idClienteASerMantido where a.cliente in (:idsClientesASeremSubstituidos)";

			retorno = session.createQuery(hql).setParameter("idClienteASerMantido", clienteASerMantido).setParameterList(
							"idsClientesASeremSubstituidos", clientesASeremSubstituidos).executeUpdate();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	public Integer atualizaClientesRedundantesFatura(Cliente clienteASerMantido, Collection clientesASeremSubstituidos)
					throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		Integer retorno;
		try{
			String hql = "update Fatura a set a.cliente = :idClienteASerMantido where a.cliente in (:idsClientesASeremSubstituidos)";

			retorno = session.createQuery(hql).setParameter("idClienteASerMantido", clienteASerMantido).setParameterList(
							"idsClientesASeremSubstituidos", clientesASeremSubstituidos).executeUpdate();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	public Integer atualizaClientesRedundantesGuiaDevolucao(Cliente clienteASerMantido, Collection clientesASeremSubstituidos)
					throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		Integer retorno;
		try{
			String hql = "update GuiaDevolucao a set a.cliente = :idClienteASerMantido where a.cliente in (:idsClientesASeremSubstituidos)";

			retorno = session.createQuery(hql).setParameter("idClienteASerMantido", clienteASerMantido).setParameterList(
							"idsClientesASeremSubstituidos", clientesASeremSubstituidos).executeUpdate();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	public Integer atualizaClientesRedundantesGuiaPagamento(Cliente clienteASerMantido, Collection clientesASeremSubstituidos)
					throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		Integer retorno;
		try{
			String hql = "update GuiaPagamento a set a.cliente = :idClienteASerMantido where a.cliente in (:idsClientesASeremSubstituidos)";

			retorno = session.createQuery(hql).setParameter("idClienteASerMantido", clienteASerMantido).setParameterList(
							"idsClientesASeremSubstituidos", clientesASeremSubstituidos).executeUpdate();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	public Integer atualizaClientesRedundantesGuiaPagamentoHistorico(Cliente clienteASerMantido, Collection clientesASeremSubstituidos)
					throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		Integer retorno;
		try{
			String hql = "update GuiaPagamentoHistorico a set a.cliente = :idClienteASerMantido where a.cliente in (:idsClientesASeremSubstituidos)";

			retorno = session.createQuery(hql).setParameter("idClienteASerMantido", clienteASerMantido).setParameterList(
							"idsClientesASeremSubstituidos", clientesASeremSubstituidos).executeUpdate();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	public Integer atualizaClientesRedundantesImovelCobrancaSituacao(Cliente clienteASerMantido, Collection clientesASeremSubstituidos)
					throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		Integer retorno;
		try{
			String hql1 = "update ImovelCobrancaSituacao a set a.cliente = :idClienteASerMantido where a.cliente in (:idsClientesASeremSubstituidos)";

			retorno = session.createQuery(hql1).setParameter("idClienteASerMantido", clienteASerMantido).setParameterList(
							"idsClientesASeremSubstituidos", clientesASeremSubstituidos).executeUpdate();

			String hql2 = "update ImovelCobrancaSituacao a set a.escritorio = :idClienteASerMantido where a.escritorio in (:idsClientesASeremSubstituidos)";

			retorno = retorno
							+ session.createQuery(hql2).setParameter("idClienteASerMantido", clienteASerMantido).setParameterList(
											"idsClientesASeremSubstituidos", clientesASeremSubstituidos).executeUpdate();

			String hql3 = "update ImovelCobrancaSituacao a set a.advogado = :idClienteASerMantido where a.advogado in (:idsClientesASeremSubstituidos)";

			retorno = retorno
							+ session.createQuery(hql3).setParameter("idClienteASerMantido", clienteASerMantido).setParameterList(
											"idsClientesASeremSubstituidos", clientesASeremSubstituidos).executeUpdate();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	public Integer atualizaClientesRedundantesLeiturista(Cliente clienteASerMantido, Collection clientesASeremSubstituidos)
					throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		Integer retorno;
		try{
			String hql = "update Leiturista a set a.cliente = :idClienteASerMantido where a.cliente in (:idsClientesASeremSubstituidos)";

			retorno = session.createQuery(hql).setParameter("idClienteASerMantido", clienteASerMantido).setParameterList(
							"idsClientesASeremSubstituidos", clientesASeremSubstituidos).executeUpdate();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	public Integer atualizaClientesRedundantesLocalidade(Cliente clienteASerMantido, Collection clientesASeremSubstituidos)
					throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		Integer retorno;
		try{
			String hql = "update Localidade a set a.cliente = :idClienteASerMantido where a.cliente in (:idsClientesASeremSubstituidos)";

			retorno = session.createQuery(hql).setParameter("idClienteASerMantido", clienteASerMantido).setParameterList(
							"idsClientesASeremSubstituidos", clientesASeremSubstituidos).executeUpdate();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	public Integer atualizaClientesRedundantesNegativacaoCriterio(Cliente clienteASerMantido, Collection clientesASeremSubstituidos)
					throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		Integer retorno;
		try{
			String hql = "update NegativacaoCriterio a set a.cliente = :idClienteASerMantido where a.cliente in (:idsClientesASeremSubstituidos)";

			retorno = session.createQuery(hql).setParameter("idClienteASerMantido", clienteASerMantido).setParameterList(
							"idsClientesASeremSubstituidos", clientesASeremSubstituidos).executeUpdate();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	public Integer atualizaClientesRedundantesNegativador(Cliente clienteASerMantido, Collection clientesASeremSubstituidos)
					throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		Integer retorno;
		try{
			String hql = "update Negativador a set a.cliente = :idClienteASerMantido where a.cliente in (:idsClientesASeremSubstituidos)";

			retorno = session.createQuery(hql).setParameter("idClienteASerMantido", clienteASerMantido).setParameterList(
							"idsClientesASeremSubstituidos", clientesASeremSubstituidos).executeUpdate();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	public Integer atualizaClientesRedundantesNegativadorMovimentoReg(Cliente clienteASerMantido, Collection clientesASeremSubstituidos)
					throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		Integer retorno;
		try{
			String hql = "update NegativadorMovimentoReg a set a.cliente = :idClienteASerMantido where a.cliente in (:idsClientesASeremSubstituidos)";

			retorno = session.createQuery(hql).setParameter("idClienteASerMantido", clienteASerMantido).setParameterList(
							"idsClientesASeremSubstituidos", clientesASeremSubstituidos).executeUpdate();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	public Integer atualizaClientesRedundantesPagamento(Cliente clienteASerMantido, Collection clientesASeremSubstituidos)
					throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		Integer retorno;
		try{
			String hql = "update Pagamento a set a.cliente = :idClienteASerMantido where a.cliente in (:idsClientesASeremSubstituidos)";

			retorno = session.createQuery(hql).setParameter("idClienteASerMantido", clienteASerMantido).setParameterList(
							"idsClientesASeremSubstituidos", clientesASeremSubstituidos).executeUpdate();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;

	}

	public Integer atualizaClientesRedundantesPagamentoHistorico(Cliente clienteASerMantido, Collection clientesASeremSubstituidos)
					throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		Integer retorno;
		try{
			String hql = "update PagamentoHistorico a set a.cliente = :idClienteASerMantido where a.cliente in (:idsClientesASeremSubstituidos)";

			retorno = session.createQuery(hql).setParameter("idClienteASerMantido", clienteASerMantido).setParameterList(
							"idsClientesASeremSubstituidos", clientesASeremSubstituidos).executeUpdate();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	public Integer atualizaClientesRedundantesParcelamento(Cliente clienteASerMantido, Collection clientesASeremSubstituidos)
					throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		Integer retorno;
		try{
			String hql = "update Parcelamento a set a.cliente = :idClienteASerMantido where a.cliente in (:idsClientesASeremSubstituidos)";

			retorno = session.createQuery(hql).setParameter("idClienteASerMantido", clienteASerMantido).setParameterList(
							"idsClientesASeremSubstituidos", clientesASeremSubstituidos).executeUpdate();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	public Integer atualizaClientesRedundantesRegistroAtendimentoSolicitante(Cliente clienteASerMantido,
					Collection clientesASeremSubstituidos) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		Integer retorno;
		try{
			String hql = "update RegistroAtendimentoSolicitante a set a.cliente = :idClienteASerMantido where a.cliente in (:idsClientesASeremSubstituidos)";

			retorno = session.createQuery(hql).setParameter("idClienteASerMantido", clienteASerMantido).setParameterList(
							"idsClientesASeremSubstituidos", clientesASeremSubstituidos).executeUpdate();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	public Integer atualizaClientesRedundantesSistemaParametro(Cliente clienteASerMantido, Collection clientesASeremSubstituidos)
					throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		Integer retorno;
		try{
			String hql1 = "update SistemaParametro a set a.clientePresidenteCompesa = :idClienteASerMantido "
							+ "where a.clientePresidenteCompesa in (:idsClientesASeremSubstituidos) and a.parmId = "
							+ ConstantesConfig.getIdEmpresa();

			retorno = session.createQuery(hql1).setParameter("idClienteASerMantido", clienteASerMantido).setParameterList(
							"idsClientesASeremSubstituidos", clientesASeremSubstituidos).executeUpdate();

			String hql2 = "update SistemaParametro a set a.clienteDiretorComercialCompesa = :idClienteASerMantido "
							+ "where a.clienteDiretorComercialCompesa in (:idsClientesASeremSubstituidos) and a.parmId = "
							+ ConstantesConfig.getIdEmpresa();

			retorno = retorno
							+ session.createQuery(hql2).setParameter("idClienteASerMantido", clienteASerMantido).setParameterList(
											"idsClientesASeremSubstituidos", clientesASeremSubstituidos).executeUpdate();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	public Integer atualizaClientesRedundantesVencimentoAlternativo(Cliente clienteASerMantido, Collection clientesASeremSubstituidos)
					throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		Integer retorno;
		try{
			String hql = "update VencimentoAlternativo a set a.cliente = :idClienteASerMantido where a.cliente in (:idsClientesASeremSubstituidos)";

			retorno = session.createQuery(hql).setParameter("idClienteASerMantido", clienteASerMantido).setParameterList(
							"idsClientesASeremSubstituidos", clientesASeremSubstituidos).executeUpdate();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	public Integer atualizaClientesRedundantesCliente(Cliente clienteASerMantido, Collection clientesASeremSubstituidos)
					throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		Integer retorno;
		try{
			String update = "update Cliente a set a.cliente = :clienteASerMantido where a.cliente in (:idsClientesASeremSubstituidos) ";
			session.createQuery(update).setParameter("clienteASerMantido", clienteASerMantido).setParameterList(
							"idsClientesASeremSubstituidos", clientesASeremSubstituidos).executeUpdate();

			String hql = "delete from Cliente a where a in (:clientesASeremRemovidos) ";

			retorno = session.createQuery(hql).setParameterList("clientesASeremRemovidos", clientesASeremSubstituidos).executeUpdate();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * Pesquisa todos os telefones de um cliente
	 * 
	 * @param idCliente
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarClienteFonePorCliente(Integer idCliente) throws ErroRepositorioException{

		Collection retorno = new ArrayList();
		Collection colecaoClienteFone = new ArrayList();
		Session session = HibernateUtil.getSession();
		String consulta = null;
		ClienteFone clienteFone = new ClienteFone();

		Object[] objetoClienteFone = null;

		try{
			consulta = "SELECT " + " cfon.telefone, " + // 0
							" cfon.foneTipo.id " // 1
							+ "from ClienteFone cfon " + "where cfon.cliente = :idCliente ";

			colecaoClienteFone = session.createQuery(consulta).setInteger("idCliente", idCliente.intValue()).list();

			if(!colecaoClienteFone.isEmpty()){

				objetoClienteFone = (Object[]) colecaoClienteFone.iterator().next();

				if(objetoClienteFone[0] != null){
					clienteFone.setTelefone((String) objetoClienteFone[0]);
				}
				if(objetoClienteFone[1] != null){
					FoneTipo foneTipo = new FoneTipo();
					foneTipo.setId((Integer) objetoClienteFone[1]);
					clienteFone.setFoneTipo(foneTipo);
				}

				retorno.add(clienteFone);
			}

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;

	}

	/**
	 * [UC0204]
	 * 
	 * @author Hebert Falcão
	 * @date 20/05/2011
	 */
	public Collection pesquisarClientesContaPeloImovelEConta(Integer idImovel, Integer idConta) throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();
		StringBuffer consulta = new StringBuffer();

		try{
			consulta.append("select clie.clie_nmcliente as nomeCliente, ");
			consulta.append("       clie.clie_nncpf as cpf, ");
			consulta.append("       clie.clie_nncnpj as cnpj, ");
			consulta.append("       crtp.crtp_dsclienterelacaotipo as tipoRelacao, ");
			consulta.append("       cfon.cfon_cdddd as ddd, ");
			consulta.append("       cfon.cfon_nnfone as fone, ");
			consulta.append("       clim.clim_dtrelacaoinicio as dtRelacaoInicio ");
			consulta.append("from cliente clie ");
			consulta.append("left join cliente_conta clct on clct.clie_id = clie.clie_id ");
			consulta.append("left join cliente_relacao_tipo crtp on crtp.crtp_id = clct.crtp_id ");
			consulta.append("left join cliente_fone cfon on cfon.clie_id = clie.clie_id and cfon_icfonepadrao = 1");
			consulta.append("left join cliente_imovel clim on (clim.clie_id = clie.clie_id and clim.crtp_id = clct.crtp_id) ");
			consulta.append(" where clim.imov_id = :idImovel ");
			consulta.append("  and clct.cnta_id = :idConta ");
			consulta.append("  and clim.clim_tmultimaalteracao = (select max(climaux.clim_tmultimaalteracao) ");
			consulta.append("                                     from cliente_imovel climaux ");
			consulta.append("                                     where climaux.clie_id = clie.clie_id ");
			consulta.append("                                       and climaux.crtp_id = clct.crtp_id ");
			consulta.append("                                       and climaux.imov_id = :idImovel) ");
			consulta.append("union ");
			consulta.append("select clie.clie_nmcliente as nomeCliente, ");
			consulta.append("       clie.clie_nncpf as cpf, ");
			consulta.append("       clie.clie_nncnpj as cnpj, ");
			consulta.append("       crtp.crtp_dsclienterelacaotipo as tipoRelacao, ");
			consulta.append("       cfon.cfon_cdddd as ddd, ");
			consulta.append("       cfon.cfon_nnfone as fone, ");
			consulta.append("       clim.clim_dtrelacaoinicio as dtRelacaoInicio ");
			consulta.append("from cliente clie ");
			consulta.append("left join cliente_conta_historico clch on clch.clie_id = clie.clie_id ");
			consulta.append("left join cliente_relacao_tipo crtp on crtp.crtp_id = clch.crtp_id ");
			consulta.append("left join cliente_fone cfon on cfon.clie_id = clie.clie_id and cfon_icfonepadrao = 1");
			consulta.append("left join cliente_imovel clim on (clim.clie_id = clie.clie_id and clim.crtp_id = clch.crtp_id) ");
			consulta.append(" where clim.imov_id = :idImovel ");
			consulta.append("  and clch.cnta_id = :idConta ");
			consulta.append("  and clim.clim_tmultimaalteracao = (select max(climaux.clim_tmultimaalteracao) ");
			consulta.append("                                     from cliente_imovel climaux ");
			consulta.append("                                     where climaux.clie_id = clie.clie_id ");
			consulta.append("                                       and climaux.crtp_id = clch.crtp_id ");
			consulta.append("                                       and climaux.imov_id = :idImovel) ");

			retorno = session.createSQLQuery(consulta.toString()).addScalar("nomeCliente", Hibernate.STRING).addScalar("cpf",
							Hibernate.STRING).addScalar("cnpj", Hibernate.STRING).addScalar("tipoRelacao", Hibernate.STRING).addScalar(
							"ddd", Hibernate.STRING).addScalar("fone", Hibernate.STRING).addScalar("dtRelacaoInicio", Hibernate.DATE)
							.setInteger("idImovel", idImovel).setInteger("idConta", idConta).list();
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	// /**
	// * [UC0582] - Emitir Boletim de Cadastro
	// * Pesquisa os dados do cliente para a emissão do boletim
	// *
	// * @author Rafael Corrêa
	// * @date 16/05/2007
	// * @param idImovel
	// * ,
	// * clienteRelacaoTipo
	// * @throws ErroRepositorioException
	// */
	//
	// public Collection pesquisarClienteEmitirBoletimCadastro(Integer idImovel, Short
	// clienteRelacaoTipo) throws ErroRepositorioException{
	//
	// Collection retorno = null;
	//
	// Session session = HibernateUtil.getSession();
	//
	// String consulta = "";
	//
	// try{
	// consulta = "SELECT clie.clie_id as id, clie.clie_nmcliente as nome, clie.cltp_id as tipo, "
	// // 0,
	// // 1, 2
	// + " clie.clie_nncpf as cpf, clie.clie_nncnpj as cnpj, clie.clie_nnrg as rg, " // 3,
	// // 4, 5
	// + " clie.clie_dtrgemissao as dataEmissaoRg, oerg.oerg_dsabreviado as orgaoExpedidorRg, " //
	// 6,
	// // 7
	// + " unfe.unfe_dsufsigla as uf, clie.clie_dtnascimento as dataNascimento, " // 8,
	// // 9
	// +
	// " profissao.prof_dsprofissao as profissao, clie.psex_id as sexo, clie.clie_nnmae as nomeMae, "
	// // 10,
	// // 11,
	// // 12
	// + " clie.clie_icuso as icUso, ce.edtp_id as tipoEndereco, ce.logr_id as idLogradouro, " //
	// 13,
	// // 14,
	// // 15
	// +
	// " cep.cep_cdcep as cep, bairro.bair_cdbairro as bairro, ce.edrf_id as idEnderecoReferencia, "
	// // 16,
	// // 17,
	// // 18
	// + " ce.cled_nnimovel as numeroImovel, ce.cled_dscomplementoendereco as complemento, " // 19,
	// // 20
	// + " cf.fnet_id as idFoneTipo, cf.cfon_cdddd as ddd, cf.cfon_nnfone as numeroTelefone, " //
	// 21,
	// // 22,
	// // 23
	// + " cf.cfon_nnfoneramal as ramal " // 24
	// + " FROM cliente_imovel ci "
	// + " INNER JOIN cliente clie "
	// + " on clie.clie_id = ci.clie_id "
	// + " LEFT OUTER JOIN profissao profissao "
	// + " on profissao.prof_id = clie.prof_id "
	// + " LEFT OUTER JOIN orgao_expedidor_rg oerg "
	// + " on oerg.oerg_id = clie.oerg_id "
	// + " LEFT OUTER JOIN unidade_federacao unfe "
	// + " on unfe.unfe_id = clie.unfe_id "
	// + " LEFT OUTER JOIN cliente_endereco ce "
	// + " on ce.clie_id = clie.clie_id and ce.cled_icenderecocorrespondencia = "
	// + ClienteEndereco.INDICADOR_ENDERECO_CORRESPONDENCIA
	// + " LEFT OUTER JOIN cep cep "
	// + " on cep.cep_id = ce.cep_id "
	// + " LEFT OUTER JOIN bairro bairro "
	// + " on bairro.bair_id = ce.bair_id "
	// + " LEFT OUTER JOIN cliente_fone cf "
	// + " on cf.clie_id = clie.clie_id "
	// + " WHERE ci.imov_id = :idImovel and ci.crtp_id = :clienteRelacaoTipo"
	// + " and clim_dtrelacaofim is null "
	// + "ORDER BY cf.cfon_icfonepadrao ";
	//
	// retorno = (Collection) session.createSQLQuery(consulta).addScalar("id",
	// Hibernate.INTEGER).addScalar("nome", Hibernate.STRING)
	// .addScalar("tipo", Hibernate.INTEGER).addScalar("cpf", Hibernate.STRING).addScalar("cnpj",
	// Hibernate.STRING)
	// .addScalar("rg", Hibernate.STRING).addScalar("dataEmissaoRg",
	// Hibernate.DATE).addScalar("orgaoExpedidorRg",
	// Hibernate.STRING).addScalar("uf", Hibernate.STRING).addScalar("dataNascimento",
	// Hibernate.DATE)
	// .addScalar("profissao", Hibernate.STRING).addScalar("sexo",
	// Hibernate.INTEGER).addScalar("nomeMae",
	// Hibernate.STRING).addScalar("icUso", Hibernate.SHORT).addScalar("tipoEndereco",
	// Hibernate.INTEGER).addScalar("idLogradouro", Hibernate.INTEGER).addScalar("cep",
	// Hibernate.INTEGER).addScalar("bairro", Hibernate.INTEGER).addScalar("idEnderecoReferencia",
	// Hibernate.INTEGER).addScalar("numeroImovel", Hibernate.STRING).addScalar("complemento",
	// Hibernate.STRING).addScalar("idFoneTipo", Hibernate.INTEGER).addScalar("ddd",
	// Hibernate.STRING)
	// .addScalar("numeroTelefone", Hibernate.STRING).addScalar("ramal",
	// Hibernate.STRING).setInteger("idImovel",
	// idImovel).setShort("clienteRelacaoTipo", clienteRelacaoTipo).setMaxResults(2).list();
	//
	// }catch(HibernateException e){
	// throw new ErroRepositorioException(e, "Erro no Hibernate");
	// }finally{
	// HibernateUtil.closeSession(session);
	// }
	//
	// return retorno;
	// }
}
