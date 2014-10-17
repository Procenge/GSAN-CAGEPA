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

package gcom.cadastro.cliente;

import gcom.cadastro.cliente.bean.ClienteEnderecoArquivoAgenciaVirtualWebHelper;
import gcom.cadastro.endereco.EnderecoTipo;
import gcom.cadastro.endereco.LogradouroBairro;
import gcom.cadastro.endereco.LogradouroCep;
import gcom.util.ErroRepositorioException;
import gcom.util.HibernateUtil;
import gcom.util.filtro.GeradorHQLCondicional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;

import org.hibernate.*;
import org.hibernate.criterion.Expression;

public class RepositorioClienteEnderecoHBM
				implements IRepositorioClienteEndereco {

	private static IRepositorioClienteEndereco instancia;

	/**
	 * Constructor for the RepositorioClienteTipoHBM object
	 */
	public RepositorioClienteEnderecoHBM() {

	}

	/**
	 * Retorna o valor de instancia
	 * 
	 * @return O valor de instancia
	 */
	public static IRepositorioClienteEndereco getInstancia(){

		if(instancia == null){
			instancia = new RepositorioClienteEnderecoHBM();
		}

		return instancia;
	}

	/**
	 * Pesquisa uma cole��o de cliente endereco com uma query especifica
	 * 
	 * @param filtroClienteImovel
	 *            parametros para a consulta
	 * @return Description of the Return Value
	 * @exception ErroRepositorioException
	 *                Description of the Exception
	 */

	public Collection pesquisarClienteEndereco(FiltroClienteEndereco filtroClienteEndereco) throws ErroRepositorioException{

		// cria a cole��o de retorno
		Collection retorno = null;
		// obt�m a sess�o
		Session session = HibernateUtil.getSession();

		try{
			// pesquisa a cole��o de atividades e atribui a vari�vel "retorno"
			retorno = new ArrayList(
							new CopyOnWriteArraySet(
											GeradorHQLCondicional
															.gerarCondicionalQuery(
																			filtroClienteEndereco,
																			"gcom.cadastro.cliente.ClienteEndereco",
																			"clienteEndereco",
																			"select distinct clienteEndereco.cliente.id,clienteEndereco.cliente.nome,clienteEndereco.cliente.clienteTipo.descricao,clienteEndereco.cliente.cpf,clienteEndereco.cliente.cnpj, clienteEndereco.cliente.indicadorUso "
																							+ "from gcom.cadastro.cliente.ClienteEndereco clienteEndereco "
																							+ "left join clienteEndereco.cliente.clienteTipo "
																							+ "left join clienteEndereco.logradouroCep ",
																			session).list()));
			// erro no hibernate
		}catch(HibernateException e){
			// levanta a exce��o para a pr�xima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sess�o
			HibernateUtil.closeSession(session);
		}
		// retorna a cole��o de atividades pesquisada(s)
		return retorno;
	}

	/**
	 * Pesquisa ClienteEndereco percorrendo o ClienteImovel
	 * 
	 * @param filtroClienteEndereco
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<Cliente> pesquisarClienteEnderecoClienteImovel(FiltroClienteEndereco filtroClienteEndereco)
					throws ErroRepositorioException{

		Collection<Cliente> retorno = null;

		Session session = HibernateUtil.getSession();

		try{

			retorno = new ArrayList(new CopyOnWriteArraySet<Cliente>(GeradorHQLCondicional.gerarCondicionalQuery(
							filtroClienteEndereco,
							"gcom.cadastro.cliente.ClienteEndereco",
							"clienteEndereco",
							"select distinct clienteEndereco.cliente from gcom.cadastro.cliente.ClienteEndereco clienteEndereco "
											+ "inner join clienteEndereco.cliente.clienteImoveis clienteImovel", session).list()));

		}catch(HibernateException e){

			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * 
	 * @author Raphael Rossiter
	 * @date 21/08/2006
	 * @param idCliente
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarEnderecosClienteAbreviado(Integer idCliente) throws ErroRepositorioException{

		Collection retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta = null;
		try{
			consulta = "select logradouro.nome,"
							+ // 0
							" logradouroTipo.descricaoAbreviada,"
							+ // 1
							" logradouroTitulo.descricaoAbreviada,"
							+ // 2
							" bairro.id,"
							+ // 3
							" bairro.nome,"
							+ // 4
							" municipio.id,"
							+ // 5
							" municipio.nome,"
							+ // 6
							" unidadeFederacao.id,"
							+ // 7
							" unidadeFederacao.sigla,"
							+ // 8
							" enderecoReferencia.descricaoAbreviada,"
							+ // 9
							" cep.cepId,"
							+ // 10
							" cep.logradouro,"
							+ // 11
							" cep.descricaoTipoLogradouro,"
							+ // 12
							" cep.bairro,"
							+ // 13
							" cep.municipio,"
							+ // 14
							" cep.sigla, "
							+ // 15
							" cep.codigo, "
							+ // 16
							" clienteEndereco.numero,"
							+ // 17
							" clienteEndereco.complemento,"
							+ // 18
							" logradouro.id,"
							+ // 19
							" logradouroCep.id,"
							+ // 20
							" logradouroBairro.id,"
							+ // 21
							" logradouroTipo.descricao,"
							+ // 22
							" logradouroTitulo.descricao,"
							+ // 23
							" enderecoReferencia.descricao, "
							+ // 24
							" clienteEndereco.indicadorEnderecoCorrespondencia, "
							+ // 25
							" clienteEndereco.id, "
							+ // 26
							" clienteEndereco.ultimaAlteracao " // 27
							+ "from ClienteEndereco clienteEndereco " + "left join clienteEndereco.logradouroCep logradouroCep "
							+ "left join logradouroCep.cep cep " + "left join logradouroCep.logradouro logradouro "
							+ "left join logradouro.logradouroTipo logradouroTipo "
							+ "left join logradouro.logradouroTitulo logradouroTitulo "
							+ "left join clienteEndereco.logradouroBairro logradouroBairro " + "left join logradouroBairro.bairro bairro "
							+ "left join bairro.municipio municipio " + "left join municipio.unidadeFederacao unidadeFederacao "
							+ "left join clienteEndereco.enderecoReferencia enderecoReferencia "
							+ "inner join clienteEndereco.cliente cliente " + "where cliente.id = :idCliente";

			retorno = session.createQuery(consulta).setInteger("idCliente", idCliente.intValue()).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

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
	public void atualizarLogradouroBairro(LogradouroBairro logradouroBairroAntigo, LogradouroBairro logradouroBairroNovo)
					throws ErroRepositorioException{

		String consulta = "";

		Session session = HibernateUtil.getSession();

		try{

			consulta = "UPDATE gcom.cadastro.cliente.ClienteEndereco SET "
							+ "lgbr_id = :idLogradouroBairroNovo, cled_tmultimaalteracao = :ultimaAlteracao "
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

			consulta = "UPDATE gcom.cadastro.cliente.ClienteEndereco SET "
							+ "lgcp_id = :idLogradouroCepNovo, cled_tmultimaalteracao = :ultimaAlteracao "
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
	 * M�todo repons�vel por retornar um ClienteEndereco do tipo residencial de acordo com o id
	 * informado.
	 * 
	 * @author Virg�nia Melo
	 * @date 20/08/2009
	 * @param idCliente
	 * @return
	 * @throws ErroRepositorioException
	 */
	public ClienteEndereco pesquisarClienteEnderecoResidencial(Integer idCliente) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		ClienteEndereco retorno = null;

		try{

			Criteria criteria = session.createCriteria(ClienteEndereco.class).add(Expression.eq("cliente.id", idCliente))
							.add(
							Expression.eq("enderecoTipo.id", EnderecoTipo.ENDERECO_TIPO_RESIDENCIAL)).setFetchMode("logradouroCep",
							FetchMode.JOIN).setFetchMode("enderecoReferencia", FetchMode.JOIN).setFetchMode("logradouroCep.cep",
							FetchMode.JOIN).setFetchMode("logradouroCep.logradouro", FetchMode.JOIN).setFetchMode(
							"logradouroCep.logradouro.logradouroTitulo", FetchMode.JOIN).setFetchMode(
							"logradouroCep.logradouro.logradouroTipo", FetchMode.JOIN).setFetchMode("enderecoReferencia", FetchMode.JOIN)
							.setFetchMode("logradouroBairro", FetchMode.JOIN).setFetchMode("logradouroBairro.bairro", FetchMode.JOIN)
							.setFetchMode("logradouroBairro.bairro.municipio", FetchMode.JOIN).setFetchMode(
											"logradouroBairro.bairro.municipio.unidadeFederacao", FetchMode.JOIN).setFetchMode(
											"logradouroBairro.logradouro", FetchMode.JOIN).setFetchMode(
											"logradouroBairro.logradouro.logradouroTitulo", FetchMode.JOIN).setFetchMode(
											"logradouroBairro.logradouro.logradouroTipo", FetchMode.JOIN);

			List<ClienteEndereco> lista = criteria.list();
			if(lista != null && !lista.isEmpty()){
				retorno = lista.get(0);
			}

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;

	}

	/**
	 * Consulta o logradouro do cliente
	 * 
	 * @author Josenildo Neves
	 * @date 07/05/2012
	 */
	public Collection<ClienteEnderecoArquivoAgenciaVirtualWebHelper> consultarLogradouroClienteAgenciaVirtual(Integer idCliente)
					throws ErroRepositorioException{

		Collection<ClienteEnderecoArquivoAgenciaVirtualWebHelper> colecaoEnderecoCliente = null;
		List<Object[]> retorno = null;

		Session session = HibernateUtil.getSession();

		StringBuffer consulta = new StringBuffer();

		try{
			consulta.append("SELECT ");
			consulta.append("   ce.CLED_ID as idClienteEndereco, ");
			consulta.append("   ce.LOGR_ID as idLogradouro, ");
			consulta.append("   ce.LGBR_ID as idLogradouroBairro, ");
			consulta.append("   ce.LGCP_ID as idLogradouroCep, ");
			consulta.append("   ce.CLED_NNIMOVEL as numeroImovel ");
			consulta.append("FROM CLIENTE_ENDERECO ce ");
			consulta.append("WHERE ");
			consulta.append("   ce.EDTP_ID = :idEnderecoTipo AND ");
			consulta.append("   ce.CLIE_ID = :idCliente ");

			retorno = session.createSQLQuery(consulta.toString()).addScalar("idClienteEndereco", Hibernate.INTEGER)
							.addScalar("idLogradouro", Hibernate.INTEGER).addScalar("idLogradouroBairro", Hibernate.INTEGER)
							.addScalar("idLogradouroCep", Hibernate.INTEGER).addScalar("numeroImovel", Hibernate.STRING)
							.setInteger("idEnderecoTipo", 1).setInteger("idCliente", idCliente).list();

			if((retorno != null) && (!retorno.isEmpty())){

				colecaoEnderecoCliente = new ArrayList<ClienteEnderecoArquivoAgenciaVirtualWebHelper>();

				for(Object[] i : retorno){

					ClienteEnderecoArquivoAgenciaVirtualWebHelper helper = new ClienteEnderecoArquivoAgenciaVirtualWebHelper(
									(Integer) i[0], (Integer) i[1], (Integer) i[2], (Integer) i[3], (String) i[4]);

					colecaoEnderecoCliente.add(helper);
				}
			}

		}catch(HibernateException e){
			// levanta a exce��o para a pr�xima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sess�o
			HibernateUtil.closeSession(session);
		}

		return colecaoEnderecoCliente;
	}

}