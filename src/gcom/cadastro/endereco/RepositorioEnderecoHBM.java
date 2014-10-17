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
 * Saulo Vasconcelos de Lima
 */

package gcom.cadastro.endereco;

import gcom.agenciavirtual.cadastro.endereco.LogradouroJSONHelper;
import gcom.arrecadacao.pagamento.bean.PagamentoHistoricoAdmiteDevolucaoHelper;
import gcom.cadastro.cliente.ClienteEndereco;
import gcom.cadastro.cliente.ClienteRelacaoTipo;
import gcom.cadastro.geografico.Bairro;
import gcom.faturamento.debito.DebitoCreditoSituacao;
import gcom.util.*;
import gcom.util.filtro.GeradorHQLCondicional;

import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;

import org.hibernate.*;
import org.hibernate.criterion.Restrictions;

/**
 * O repositório faz a comunicação com a base de dados através do hibernate. O
 * cliente usa o repositório como fonte de dados.
 * 
 * @author Sávio Luiz
 * @created 25 de Agosto de 2005
 */

public class RepositorioEnderecoHBM
				implements IRepositorioEndereco {

	private static IRepositorioEndereco instancia;

	/**
	 * Construtor da classe RepositorioEnderecoHBM
	 */
	private RepositorioEnderecoHBM() {

	}

	/**
	 * Retorna o valor de instancia
	 * 
	 * @return O valor de instancia
	 */
	public static IRepositorioEndereco getInstancia(){

		if(instancia == null){
			instancia = new RepositorioEnderecoHBM();
		}

		return instancia;
	}

	/**
	 * pesquisa uma coleção de cep(s) de acordo com o código
	 * 
	 * @return Description of the Return Value
	 * @exception ErroRepositorioException
	 *                Description of the Exception
	 */
	public Collection pesquisarCep() throws ErroRepositorioException{

		Collection retorno = null;
		Session session = HibernateUtil.getSession();

		try{
			retorno = session
							.createQuery(
											"select new gcom.cadastro.endereco.Cep(cep.cepId,cep.codigo,cep.cepTipo) from gcom.cadastro.endereco.Cep cep left join cep.cepTipo")
							.list();

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
	 * Description of the Method
	 * 
	 * @param cepsImportados
	 *            Coleção contendo os CEPs lidos no arquivo que não estavam cadastrados
	 * @exception ErroRepositorioException
	 *                Description of the Exception
	 */
	public void inserirImportacaoCep(Collection cepsImportados) throws ErroRepositorioException{

		StatelessSession session = HibernateUtil.getStatelessSession();

		Iterator iteratorCepImportado = cepsImportados.iterator();

		try{
			// int i = 1;
			while(iteratorCepImportado.hasNext()){
				Cep cep = (Cep) iteratorCepImportado.next();

				session.insert(cep);
				/*
				 * if (i % 50 == 0) { // 20, same as the JDBC batch size //
				 * flush a batch of inserts and release memory: session.flush();
				 * session.clear(); }
				 * i++;
				 */

			}
			// session.flush();
			// session.clear();
		}catch(HibernateException e){
			e.printStackTrace();
			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{

			// session.clear();
			HibernateUtil.closeSession(session);
			// session.close();
		}

	}

	/**
	 * Description of the Method
	 * 
	 * @param cepsImportados
	 *            Coleção contendo os CEPs lidos no arquivo que já estavam cadastrados
	 * @exception ErroRepositorioException
	 *                Description of the Exception
	 */
	public void atualizarImportacaoCep(Collection cepsImportados) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();

		Iterator iteratorCepImportado = cepsImportados.iterator();

		try{
			int i = 1;
			while(iteratorCepImportado.hasNext()){
				Cep cep = (Cep) iteratorCepImportado.next();

				// session.setFlushMode(FlushMode.COMMIT);
				session.update(cep);

				if(i % 50 == 0){
					// 20, same as the JDBC batch size
					// flush a batch of inserts and release memory:
					session.flush();
					session.clear();
				}

			}
			session.flush();
			session.clear();
		}catch(HibernateException e){
			e.printStackTrace();
			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{

			HibernateUtil.closeSession(session);
			// session.close();
		}

	}

	public Collection pesquisarEndereco(Integer idImovel) throws ErroRepositorioException{

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
							" imovel.numeroImovel,"
							+ // 17
							" imovel.complementoEndereco,"
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
							" enderecoReferencia.descricao " // 24
							+ "from Imovel imovel " + "left join imovel.logradouroCep logradouroCep " + "left join logradouroCep.cep cep "
							+ "left join logradouroCep.logradouro logradouro " + "left join logradouro.logradouroTipo logradouroTipo "
							+ "left join logradouro.logradouroTitulo logradouroTitulo "
							+ "left join imovel.logradouroBairro logradouroBairro " + "left join logradouroBairro.bairro bairro "
							+ "left join bairro.municipio municipio " + "left join municipio.unidadeFederacao unidadeFederacao "
							+ "left join imovel.enderecoReferencia enderecoReferencia " + "where imovel.id = :idImovel";
			retorno = session.createQuery(consulta).setInteger("idImovel", idImovel.intValue()).list();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		return retorno;

	}

	public Collection pesquisarEnderecoClienteAbreviado(Integer idCliente) throws ErroRepositorioException{

		Collection retorno = null;
		StatelessSession session = HibernateUtil.getStatelessSession();
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
							// 23
							+ " enderecoReferencia.descricao, " // 24
							+ " municipioBairro.id,"
							// 25
							+ " municipioBairro.nome, " // 26
							+ // 27
							" unidadeFederacaoBairro.id,"
							+ // 28
							" unidadeFederacaoBairro.sigla " + "from ClienteEndereco clienteEndereco "
							+ "left join clienteEndereco.logradouroCep logradouroCep " + "left join logradouroCep.cep cep "
							+ "left join logradouroCep.logradouro logradouro " + "left join logradouro.logradouroTipo logradouroTipo "
							+ "left join logradouro.logradouroTitulo logradouroTitulo "
							+ "left join clienteEndereco.logradouroBairro logradouroBairro " + "left join logradouroBairro.bairro bairro "
							+ "left join logradouro.municipio municipio " + "left join bairro.municipio municipioBairro "
							+ "left join municipio.unidadeFederacao unidadeFederacao "
							+ "left join clienteEndereco.enderecoReferencia enderecoReferencia "
							+ "left join municipioBairro.unidadeFederacao unidadeFederacaoBairro "
							+ "inner join clienteEndereco.cliente cliente " + "where cliente.id = :idCliente AND "
							+ "clienteEndereco.indicadorEnderecoCorrespondencia = :indicadorEnderecoCorrespondencia";

			retorno = session.createQuery(consulta).setInteger("idCliente", idCliente.intValue()).setShort(
							"indicadorEnderecoCorrespondencia", ConstantesSistema.INDICADOR_ENDERECO_CORRESPONDENCIA).list();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		return retorno;

	}

	public Collection pesquisarEnderecoFormatado(Integer idImovel) throws ErroRepositorioException{

		Collection retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta = null;
		try{
			consulta = "select logradouro.nome,"
							+ // 0
							" logradouroTipo.descricao,"
							+ // 1
							" logradouroTitulo.descricao,"
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
							" enderecoReferencia.descricao,"
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
							" imovel.numeroImovel,"
							+ // 17
							" imovel.complementoEndereco ,"
							+ // 18
							" logradouro.id, "
							+ // 19
							" logradouroCep.id,"
							+ // 20
							" logradouroBairro.id,"
							+ // 21
							" logradouroTipo.descricaoAbreviada,"
							+ // 22
							" logradouroTitulo.descricaoAbreviada,"
							+ // 23
							" enderecoReferencia.descricaoAbreviada, "
							+ // 24
							" enderecoReferencia.id, "
							+ // 25
							" regiao.nome " // 26
							+ "from Imovel imovel " + "left join imovel.logradouroCep logradouroCep "
							+ "left join logradouroCep.logradouro logradouro " + "left join logradouro.logradouroTipo logradouroTipo "
							+ "left join logradouro.logradouroTitulo logradouroTitulo "
							+ "left join imovel.logradouroBairro logradouroBairro " + "left join logradouroBairro.bairro bairro "
							+ "left join imovel.setorComercial setorComercial " + "left join logradouro.municipio municipio "
							+ "left join municipio.unidadeFederacao unidadeFederacao " + "left join municipio.microrregiao.regiao regiao "
							+ "left join imovel.enderecoReferencia enderecoReferencia " + "left join logradouroCep.cep cep "
							+ "where imovel.id = :idImovel";
			retorno = session.createQuery(consulta).setInteger("idImovel", idImovel.intValue()).list();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		return retorno;

	}

	public Collection pesquisarEnderecoAbreviadoCAER(Integer idImovel) throws ErroRepositorioException{

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
							" enderecoReferencia.descricao,"
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
							" imovel.numeroImovel,"
							+ // 17
							" imovel.complementoEndereco ,"
							+ // 18
							" logradouro.id, "
							+ // 19
							" logradouroCep.id,"
							+ // 20
							" logradouroBairro.id,"
							+ // 21
							" logradouroTipo.descricaoAbreviada,"
							+ // 22
							" logradouroTitulo.descricaoAbreviada,"
							+ // 23
							" enderecoReferencia.descricaoAbreviada, "
							+ // 24
							" enderecoReferencia.id " // 25
							+ "from Imovel imovel " + "left join imovel.logradouroCep logradouroCep "
							+ "left join logradouroCep.logradouro logradouro " + "left join logradouro.logradouroTipo logradouroTipo "
							+ "left join logradouro.logradouroTitulo logradouroTitulo "
							+ "left join imovel.logradouroBairro logradouroBairro " + "left join logradouroBairro.bairro bairro "
							+ "left join imovel.setorComercial setorComercial " + "left join logradouro.municipio municipio "
							+ "left join municipio.unidadeFederacao unidadeFederacao "
							+ "left join imovel.enderecoReferencia enderecoReferencia " + "left join logradouroCep.cep cep "
							+ "where imovel.id = :idImovel";
			retorno = session.createQuery(consulta).setInteger("idImovel", idImovel.intValue()).list();

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
	 * [UC0348] Emitir Contas por cliente responsavel CAERN
	 * Pesquisar endereco formatado para cliente
	 * 
	 * @author Raphael Rossiter
	 * @data 22/05/2007
	 * @param idCliente
	 *            ,
	 * @return Collection
	 */
	public Collection pesquisarEnderecoFormatadoCliente(Integer idCliente) throws ErroRepositorioException{

		Collection retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta = null;

		try{
			consulta = "select logradouro.nome,"
							+ // 0
							" logradouroTipo.descricao,"
							+ // 1
							" logradouroTitulo.descricao,"
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
							" enderecoReferencia.descricao,"
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
							" clienteEndereco.complemento ,"
							+ // 18
							" logradouro.id, "
							+ // 19
							" logradouroCep.id,"
							+ // 20
							" logradouroBairro.id,"
							+ // 21
							" logradouroTipo.descricaoAbreviada,"
							+ // 22
							" logradouroTitulo.descricaoAbreviada,"
							+ // 23
							" enderecoReferencia.descricaoAbreviada, "
							+ // 24
							" enderecoReferencia.id " // 25
							+ "from ClienteEndereco clienteEndereco " + "inner join clienteEndereco.cliente cliente "
							+ "left join clienteEndereco.logradouroCep logradouroCep " + "left join logradouroCep.logradouro logradouro "
							+ "left join logradouro.logradouroTipo logradouroTipo "
							+ "left join logradouro.logradouroTitulo logradouroTitulo "
							+ "left join clienteEndereco.logradouroBairro logradouroBairro " + "left join logradouroBairro.bairro bairro "
							+ "left join logradouro.municipio municipio " + "left join municipio.unidadeFederacao unidadeFederacao "
							+ "left join clienteEndereco.enderecoReferencia enderecoReferencia " + "left join logradouroCep.cep cep "
							+ "where cliente.id = :idCliente";

			retorno = session.createQuery(consulta).setInteger("idCliente", idCliente.intValue()).list();

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
	 * [UC0003] Informar Endereço
	 * Pesquisar associação de LogradouroCep já existente
	 * 
	 * @author Raphael Rossiter
	 * @data 12/05/2006
	 * @param idCep
	 *            ,
	 *            idLogradouro
	 * @return LogradouroCep
	 */
	public LogradouroCep pesquisarAssociacaoLogradouroCep(Integer idCep, Integer idLogradouro) throws ErroRepositorioException{

		Collection<LogradouroCep> colecaoLogradouroCep = new ArrayList();
		LogradouroCep retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta = null;

		try{
			consulta = "SELECT lgcp " + "FROM LogradouroCep lgcp " + "INNER JOIN FETCH lgcp.cep cep "
							+ "INNER JOIN FETCH lgcp.logradouro logr " + "LEFT JOIN FETCH logr.logradouroTipo lgtp "
							+ "LEFT JOIN FETCH logr.logradouroTitulo lgti "
							+ "WHERE lgcp.cep.cepId = :idCep AND lgcp.logradouro.id = :idLogradouro ";

			colecaoLogradouroCep = session.createQuery(consulta).setInteger("idCep", idCep).setInteger("idLogradouro", idLogradouro).list();

			/*
			 * Iterator<LogradouroCep> iterator = session.createQuery(consulta)
			 * .setInteger("idCep", idCep).setInteger("idLogradouro",
			 * idLogradouro).iterate();
			 * LogradouroCep logradouroCepColecao = null;
			 * while (iterator.hasNext()) {
			 * logradouroCepColecao = iterator.next(); // carrega todos os
			 * objetos Hibernate.initialize(logradouroCepColecao.getCep());
			 * Hibernate.initialize(logradouroCepColecao.getLogradouro());
			 * Hibernate.initialize(logradouroCepColecao.getLogradouro()
			 * .getLogradouroTipo());
			 * Hibernate.initialize(logradouroCepColecao.getLogradouro()
			 * .getLogradouroTitulo());
			 * colecaoLogradouroCep.add(logradouroCepColecao); }
			 */

			if(colecaoLogradouroCep != null && !colecaoLogradouroCep.isEmpty()){
				retorno = (LogradouroCep) Util.retonarObjetoDeColecao(colecaoLogradouroCep);
			}

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0003] Informar Endereço
	 * Pesquisar associação de LogradouroBairro já existente
	 * 
	 * @author Raphael Rossiter
	 * @data 24/05/2006
	 * @param idBairro
	 *            ,
	 *            idLogradouro
	 * @return LogradouroBairro
	 */
	public LogradouroBairro pesquisarAssociacaoLogradouroBairro(Integer idBairro, Integer idLogradouro) throws ErroRepositorioException{

		Collection<LogradouroBairro> colecaoLogradouroBairro = new ArrayList();
		LogradouroBairro retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta = null;

		try{
			consulta = "SELECT lgbr " + "FROM LogradouroBairro lgbr " 
									  + "INNER JOIN FETCH lgbr.bairro bairro "
									  + "INNER JOIN FETCH lgbr.logradouro logr " 
									  + "LEFT JOIN FETCH bairro.municipio muni "
									  + "LEFT JOIN FETCH muni.unidadeFederacao unfe "
									  + "WHERE lgbr.bairro.id = :idBairro AND lgbr.logradouro.id = :idLogradouro ";

			colecaoLogradouroBairro = session.createQuery(consulta).setInteger("idBairro", idBairro).setInteger("idLogradouro",
							idLogradouro).list();

			/*
			 * Iterator<LogradouroBairro> iterator =
			 * session.createQuery(consulta) .setInteger("idBairro",
			 * idBairro).setInteger( "idLogradouro", idLogradouro).iterate();
			 * LogradouroBairro logradouroBairroColecao = null;
			 * while (iterator.hasNext()) {
			 * logradouroBairroColecao = iterator.next(); // carrega todos os
			 * objetos
			 * Hibernate.initialize(logradouroBairroColecao.getBairro());
			 * Hibernate.initialize(logradouroBairroColecao.getBairro()
			 * .getMunicipio());
			 * Hibernate.initialize(logradouroBairroColecao.getBairro()
			 * .getMunicipio().getUnidadeFederacao());
			 * Hibernate.initialize(logradouroBairroColecao.getLogradouro());
			 * colecaoLogradouroBairro.add(logradouroBairroColecao); }
			 */

			if(colecaoLogradouroBairro != null && !colecaoLogradouroBairro.isEmpty()){
				retorno = (LogradouroBairro) Util.retonarObjetoDeColecao(colecaoLogradouroBairro);
			}

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0003] Informar Endereço
	 * Pesquisar associação de LogradouroCep apenas por logradouro
	 * 
	 * @author Raphael Rossiter
	 * @data 12/05/2006
	 * @param idLogradouro
	 * @return LogradouroCep
	 */
	public Collection<LogradouroCep> pesquisarAssociacaoLogradouroCepPorLogradouro(Integer idLogradouro) throws ErroRepositorioException{

		Collection<LogradouroCep> retorno = new ArrayList();

		Session session = HibernateUtil.getSession();
		String consulta = null;

		try{
			consulta = "SELECT lgcp " + "FROM LogradouroCep lgcp " + "INNER JOIN FETCH lgcp.cep cep "
							+ "INNER JOIN FETCH lgcp.logradouro logr " + "LEFT JOIN FETCH cep.cepTipo cptp "
							+ "WHERE lgcp.logradouro.id = :idLogradouro AND lgcp.indicadorUso = :indicadorAtivo ";

			retorno = session.createQuery(consulta).setInteger("idLogradouro", idLogradouro).setInteger("indicadorAtivo",
							ConstantesSistema.INDICADOR_USO_ATIVO).list();

			/*
			 * Iterator<LogradouroCep> iterator = session.createQuery(consulta)
			 * .setInteger("idLogradouro", idLogradouro).setInteger(
			 * "indicadorAtivo",
			 * ConstantesSistema.INDICADOR_USO_ATIVO).iterate();
			 * LogradouroCep logradouroCepColecao = null;
			 * while (iterator.hasNext()) {
			 * logradouroCepColecao = iterator.next(); // carrega todos os
			 * objetos Hibernate.initialize(logradouroCepColecao.getCep());
			 * Hibernate
			 * .initialize(logradouroCepColecao.getCep().getCepTipo());
			 * Hibernate.initialize(logradouroCepColecao.getLogradouro());
			 * retorno.add(logradouroCepColecao); }
			 */

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0003] Informar Endereço
	 * Atualiza a situação de uma associação de LogradouroCep (Motivo: CEP
	 * Inicial de Município)
	 * 
	 * @author Raphael Rossiter
	 * @data 12/05/2006
	 * @param idCep
	 *            ,
	 *            idLogradouro, indicadorUso
	 * @return void
	 */
	public void atualizarIndicadorUsoLogradouroCep(Integer idCep, Integer idLogradouro, Short indicadorUso) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();

		String atualizarIndicadorUsoLogradouroCep;

		try{

			atualizarIndicadorUsoLogradouroCep = "UPDATE LogradouroCep "
							+ "SET lgcp_icuso = :indicadorUso, lgcp_tmultimaalteracao = :dataAlteracao "
							+ "WHERE cep_id = :idCep AND logr_id = :idLogradouro";

			session.createQuery(atualizarIndicadorUsoLogradouroCep).setInteger("indicadorUso", indicadorUso).setTimestamp("dataAlteracao",
							new Date()).setInteger("idCep", idCep).setInteger("idLogradouro", idLogradouro).executeUpdate();

		}catch(HibernateException e){

			throw new ErroRepositorioException(e, "Erro no Hibernate");

		}finally{
			HibernateUtil.closeSession(session);
		}
	}

	public Integer pesquisarLogradouroCount(FiltroLogradouro filtroLogradouro) throws ErroRepositorioException{

		// cria a coleção de retorno
		Integer retorno = null;
		// obtém a sessão
		Session session = HibernateUtil.getSession();

		try{

			List camposOrderBy = new ArrayList();

			camposOrderBy = filtroLogradouro.getCamposOrderBy();

			filtroLogradouro.limparCamposOrderBy();

			// pesquisa a coleção de atividades e atribui a variável "retorno"
			retorno = ((Number) GeradorHQLCondicional.gerarCondicionalQuery(
							filtroLogradouro,
							"gcom.cadastro.endereco.Logradouro",
							"logr",
							"select count(logr.id) " + "from gcom.cadastro.endereco.Logradouro logr "
											+ "left join logr.logradouroTipo logradouroTipo "
											+ "left join logr.logradouroTitulo logradouroTitulo ", session).uniqueResult()).intValue();

			filtroLogradouro.setCampoOrderBy((String[]) camposOrderBy.toArray(new String[camposOrderBy.size()]));

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

	public Collection<Logradouro> pesquisarLogradouro(FiltroLogradouro filtroLogradouro, Integer numeroPaginas)
					throws ErroRepositorioException{

		// cria a coleção de retorno
		Collection retorno = null;
		// obtém a sessão
		Session session = HibernateUtil.getSession();

		try{
			// pesquisa a coleção de atividades e atribui a variável "retorno"
			retorno = new ArrayList(new CopyOnWriteArraySet<Logradouro>(GeradorHQLCondicional.gerarCondicionalQuery(
							filtroLogradouro,
							"gcom.cadastro.endereco.Logradouro",
							"logr",
							"select logr " + "from gcom.cadastro.endereco.Logradouro logr "
											+ "left join logr.logradouroTipo logradouroTipo "
											+ "left join logr.logradouroTitulo logradouroTitulo ", session).setFirstResult(
							10 * numeroPaginas).setMaxResults(10).list()));

			// Carrega os objetos informados no filtro
			/*
			 * if
			 * (!filtroLogradouro.getColecaoCaminhosParaCarregamentoEntidades()
			 * .isEmpty()) { PersistenciaUtil.processaObjetosParaCarregamento(
			 * filtroLogradouro .getColecaoCaminhosParaCarregamentoEntidades(),
			 * retorno); }
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

	// metodo que serve para fazer a pesquisa do logradouro
	// apartir dos parametros informados

	public Collection pesquisarLogradouroCompleto(String codigoMunicipio, String codigoBairro, String nome, String nomePopular,
					String logradouroTipo, String logradouroTitulo, String cep, String codigoLogradouro, String indicadorUso,
					String tipoPesquisa, String tipoPesquisaPopular, Integer numeroPaginas) throws ErroRepositorioException{

		// cria a coleção de retorno
		Collection retorno = null;
		// Collection resultadoFinal = new ArrayList();
		// obtém a sessão
		Session session = HibernateUtil.getSession();

		try{
			StringBuffer sql = new StringBuffer();
			sql.append("select distinct(logr) ").append(" from gcom.cadastro.endereco.Logradouro logr ").append(
							" left join FETCH logr.logradouroTipo logradouroTipo ").append(
							" left join FETCH logr.logradouroTitulo logradouroTitulo ").append(
							" left join logr.logradouroBairros logradouroBairro ").append(" left join logr.logradouroCeps logradouroCep ")
							.append(" inner join FETCH logr.municipio municipio ").append(" left join logradouroBairro.bairro bairro ")
							.append(" left join logradouroCep.cep cep ");

			// construindo as condicionais do select
			StringBuffer condicional = new StringBuffer();
			// se foi informado o codigo do municipio
			if(codigoMunicipio != null && !codigoMunicipio.trim().equals("")){
				condicional.append(" municipio.id = ").append(codigoMunicipio).append(" and ");
			}
			// se foi informado o codigo do bairro
			if(codigoBairro != null && !codigoBairro.trim().equals("")){
				condicional.append(" bairro.codigo = ").append(codigoBairro).append(" and ");
			}
			// se foi informado o nome do logradouro
			if(nome != null && !nome.trim().equals("")){
				if(tipoPesquisa != null && tipoPesquisa.equals(ConstantesSistema.TIPO_PESQUISA_COMPLETA.toString())){
					condicional.append(" logr.nome like '%").append(nome.toUpperCase()).append("%' and ");
				}else{
					condicional.append(" logr.nome like '").append(nome.toUpperCase()).append("%' and ");
				}
			}
			// se foi informado o nome popular do logradouro
			if(nomePopular != null && !nomePopular.trim().equals("")){
				if(tipoPesquisaPopular != null && tipoPesquisaPopular.equals(ConstantesSistema.TIPO_PESQUISA_COMPLETA.toString())){
					condicional.append(" logr.nomePopular like '%").append(nomePopular.toUpperCase()).append("%' and ");
				}else{
					condicional.append(" logr.nomePopular like '").append(nomePopular.toUpperCase()).append("%' and ");
				}
			}
			// se foi informado o tipo do logradouro
			if(logradouroTipo != null && !logradouroTipo.trim().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
				condicional.append(" logradouroTipo.id = ").append(logradouroTipo).append(" and ");
			}
			// se foi informado o titulo do logradouro
			if(logradouroTitulo != null && !logradouroTitulo.trim().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
				condicional.append(" logradouroTitulo.id = ").append(logradouroTitulo).append(" and ");
			}
			// se foi informado o cep
			if(cep != null && !cep.trim().equals("")){
				condicional.append(" cep.codigo = ").append(cep).append(" and ");
			}
			// se foi informado codigo do logradouro
			if(codigoLogradouro != null && !codigoLogradouro.trim().equals("")){
				condicional.append(" logr.id = ").append(codigoLogradouro).append(" and ");
			}
			// se foi informado codigo do logradouro
			if(indicadorUso != null && !indicadorUso.trim().equals("")){
				condicional.append(" logr.indicadorUso = ").append(indicadorUso).append(" and ");
			}

			// testa se vai haver condicional ou não no hql
			if(!condicional.toString().trim().equals("")){
				sql.append(" where ").append(condicional);
			}else{
				sql.append(" and ");
			}

			// formata o hql
			StringBuffer hqlFormatado = new StringBuffer();
			hqlFormatado.append(Util.formatarHQL(sql.toString(), 4));

			// ordena
			hqlFormatado.append(" order by logr.nome ");

			// pesquisa a coleção de atividades e atribui a variável "retorno"
			retorno = session.createQuery(hqlFormatado.toString()).setFirstResult(10 * numeroPaginas).setMaxResults(10).list();

			/*
			 * Iterator iterator = retorno.iterator(); Logradouro logradouro =
			 * null;
			 * while (iterator.hasNext()) {
			 * logradouro = (Logradouro) iterator.next(); // carrega todos os
			 * objetos Hibernate.initialize(logradouro.getMunicipio());
			 * Hibernate.initialize(logradouro.getLogradouroTipo());
			 * Hibernate.initialize(logradouro.getLogradouroTitulo());
			 * resultadoFinal.add(logradouro); }
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
		// return resultadoFinal;
		return retorno;
	}

	public Collection pesquisarLogradouroCompletoRelatorio(String codigoMunicipio, String codigoBairro, String nome, String nomePopular,
					String logradouroTipo, String logradouroTitulo, String cep, String codigoLogradouro, String indicadorUso,
					String tipoPesquisa, String tipoPesquisaPopular) throws ErroRepositorioException{

		// cria a coleção de retorno
		Collection retorno = null;
		// Collection resultadoFinal = new ArrayList();
		// obtém a sessão
		Session session = HibernateUtil.getSession();

		try{
			String sql = "select distinct(logr) " + " from gcom.cadastro.endereco.Logradouro logr "
							+ " left join FETCH logr.logradouroTipo logradouroTipo "
							+ " left join FETCH logr.logradouroTitulo logradouroTitulo "
							+ " left join logr.logradouroBairros logradouroBairro " + " left join logr.logradouroCeps logradouroCep "
							+ " inner join FETCH logr.municipio municipio " + " left join logradouroBairro.bairro bairro "
							+ " left join logradouroCep.cep cep ";

			// construindo as condicionais do select
			String condicional = "";
			// se foi informado o codigo do municipio
			if(codigoMunicipio != null && !codigoMunicipio.trim().equals("")){
				condicional = " municipio.id = " + codigoMunicipio + " and ";
			}
			// se foi informado o codigo do bairro
			if(codigoBairro != null && !codigoBairro.trim().equals("")){
				condicional = condicional + " bairro.codigo = " + codigoBairro + " and ";
			}
			// se foi informado o nome do logradouro
			if(nome != null && !nome.trim().equals("")){
				if(tipoPesquisa != null && tipoPesquisa.equals(ConstantesSistema.TIPO_PESQUISA_COMPLETA.toString())){
					condicional = condicional + " logr.nome like '%" + nome.toUpperCase() + "%' and ";
				}else{
					condicional = condicional + " logr.nome like '" + nome.toUpperCase() + "%' and ";
				}
			}
			// se foi informado o nome popular do logradouro
			if(nomePopular != null && !nomePopular.trim().equals("")){
				if(tipoPesquisa != null && tipoPesquisa.equals(ConstantesSistema.TIPO_PESQUISA_COMPLETA.toString())){
					condicional = condicional + " logr.nomePopular like '%" + nomePopular.toUpperCase() + "%' and ";
				}else{
					condicional = condicional + " logr.nomePopular like '" + nomePopular.toUpperCase() + "%' and ";
				}
			}
			// se foi informado o tipo do logradouro
			if(logradouroTipo != null && !logradouroTipo.trim().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
				condicional = condicional + " logradouroTipo.id = " + logradouroTipo + " and ";
			}
			// se foi informado o titulo do logradouro
			if(logradouroTitulo != null && !logradouroTitulo.trim().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
				condicional = condicional + " logradouroTitulo.id = " + logradouroTitulo + " and ";
			}
			// se foi informado o cep
			if(cep != null && !cep.trim().equals("")){
				condicional = condicional + " cep.codigo = " + cep + " and ";
			}
			// se foi informado codigo do logradouro
			if(codigoLogradouro != null && !codigoLogradouro.trim().equals("")){
				condicional = condicional + " logr.id = " + codigoLogradouro + " and ";
			}
			// se foi informado codigo do logradouro
			if(indicadorUso != null && !indicadorUso.trim().equals("")){
				condicional = condicional + " logr.indicadorUso = " + indicadorUso + " and ";
			}

			// testa se vai haver condicional ou não no hql
			if(!condicional.trim().equals("")){
				sql = sql + " where " + condicional;
			}else{
				sql = sql + " and ";
			}

			// pesquisa a coleção de atividades e atribui a variável "retorno"
			retorno = session.createQuery(Util.formatarHQL(sql, 4)).list();

			/*
			 * Iterator iterator = retorno.iterator(); Logradouro logradouro =
			 * null;
			 * while (iterator.hasNext()) {
			 * logradouro = (Logradouro) iterator.next(); // carrega todos os
			 * objetos Hibernate.initialize(logradouro.getMunicipio());
			 * Hibernate.initialize(logradouro.getLogradouroTipo());
			 * Hibernate.initialize(logradouro.getLogradouroTitulo());
			 * resultadoFinal.add(logradouro); }
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
		// return resultadoFinal;
		return retorno;
	}

	// metodo que serve para fazer a pesquisa do logradouro
	// apartir dos parametros informados

	public Integer pesquisarLogradouroCompletoCount(String codigoMunicipio, String codigoBairro, String nome, String nomePopular,
					String logradouroTipo, String logradouroTitulo, String cep, String codigoLogradouro, String indicadorUso,
					String tipoPesquisa, String tipoPesquisaPopular) throws ErroRepositorioException{

		// cria a coleção de retorno
		Integer retorno = null;
		// obtém a sessão
		Session session = HibernateUtil.getSession();

		try{
			String sql = "select count(distinct logr) " + " from gcom.cadastro.endereco.Logradouro logr "
							+ " left join logr.logradouroTipo logradouroTipo " + " left join logr.logradouroTitulo logradouroTitulo "
							+ " left join logr.logradouroBairros logradouroBairro " + " left join logr.logradouroCeps logradouroCep "
							+ " left join logr.municipio municipio " + " left join logradouroBairro.bairro bairro"
							+ " left join logradouroCep.cep cep ";

			// construindo as condicionais do select
			String condicional = "";
			// se foi informado o codigo do municipio
			if(codigoMunicipio != null && !codigoMunicipio.trim().equals("")){
				condicional = " municipio.id = " + codigoMunicipio + " and ";
			}
			// se foi informado o codigo do bairro
			if(codigoBairro != null && !codigoBairro.trim().equals("")){
				condicional = condicional + " bairro.codigo = " + codigoBairro + " and ";
			}
			// se foi informado o nome do logradouro
			if(nome != null && !nome.trim().equals("")){
				if(tipoPesquisa != null && tipoPesquisa.equals(ConstantesSistema.TIPO_PESQUISA_COMPLETA.toString())){
					condicional = condicional + " logr.nome like '%" + nome.toUpperCase() + "%' and ";
				}else{
					condicional = condicional + " logr.nome like '" + nome.toUpperCase() + "%' and ";
				}
			}
			// se foi informado o nome popular do logradouro
			if(nomePopular != null && !nomePopular.trim().equals("")){
				if(tipoPesquisaPopular != null && tipoPesquisaPopular.equals(ConstantesSistema.TIPO_PESQUISA_COMPLETA.toString())){
					condicional = condicional + " logr.nomePopular like '%" + nomePopular.toUpperCase() + "%' and ";
				}else{
					condicional = condicional + " logr.nomePopular like '" + nomePopular.toUpperCase() + "%' and ";
				}
			}
			// se foi informado o tipo do logradouro
			if(logradouroTipo != null && !logradouroTipo.trim().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
				condicional = condicional + " logradouroTipo.id = " + logradouroTipo + " and ";
			}
			// se foi informado o titulo do logradouro
			if(logradouroTitulo != null && !logradouroTitulo.trim().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
				condicional = condicional + " logradouroTitulo.id = " + logradouroTitulo + " and ";
			}
			// se foi informado o cep
			if(cep != null && !cep.trim().equals("")){
				condicional = condicional + " cep.codigo = " + cep + " and ";
			}
			// se foi informado codigo do logradouro
			if(codigoLogradouro != null && !codigoLogradouro.trim().equals("")){
				condicional = condicional + " logr.id = " + codigoLogradouro + " and ";
			}
			// se foi informado codigo do logradouro
			if(indicadorUso != null && !indicadorUso.trim().equals("")){
				condicional = condicional + " logr.indicadorUso = " + indicadorUso + " and ";
			}

			// testa se vai haver condicional ou não no hql
			if(!condicional.trim().equals("")){
				sql = sql + " where " + condicional;
			}else{
				sql = sql + " and ";
			}

			// pesquisa a coleção de atividades e atribui a variável "retorno"
			retorno = ((Number) session.createQuery(Util.formatarHQL(sql, 4)).uniqueResult()).intValue();

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
	 * [UC0085] Obter Endereço
	 * 
	 * @author Ana Maria
	 * @data 07/08/2006
	 * @param idRegistroAtendimento
	 * @return Collection
	 */
	public Collection pesquisarEnderecoRegistroAtendimentoFormatado(Integer idRegistroAtendimento) throws ErroRepositorioException{

		Collection retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta = null;
		try{
			consulta = " select logradouro.nome," // 0
							+ " logradouroTipo.descricao," // 1
							+ " logradouroTitulo.descricao," // 2
							+ " bairro.id," // 3
							+ " bairro.nome," // 4
							+ " municipio.id," // 5
							+ " municipio.nome," // 6
							+ " unidadeFederacao.id," // 7
							+ " unidadeFederacao.sigla," // 8
							+ " cep.cepId," // 9
							+ " cep.logradouro," // 10
							+ " cep.descricaoTipoLogradouro," // 11
							+ " cep.bairro," // 12
							+ " cep.municipio," // 13
							+ " cep.sigla," // 14
							+ " cep.codigo," // 15
							+ " registroAtendimento.complementoEndereco ," // 16
							+ " logradouro.id, " // 17
							+ " logradouroCep.id," // 18
							+ " logradouroBairro.id," // 19
							+ " logradouroTipo.descricaoAbreviada," // 20
							+ " logradouroTitulo.descricaoAbreviada," // 21
							+ " registroAtendimento.numeroImovel" // 22
							+ " from RegistroAtendimento registroAtendimento "
							+ " left join registroAtendimento.logradouroCep logradouroCep "
							+ " left join logradouroCep.cep cep "
							+ " left join logradouroCep.logradouro logradouro "
							+ " left join logradouro.logradouroTipo logradouroTipo "
							+ " left join logradouro.logradouroTitulo logradouroTitulo "
							+ " left join registroAtendimento.logradouroBairro logradouroBairro "
							+ " left join logradouroBairro.bairro bairro "
							+ " left join bairro.municipio municipio "
							+ " left join municipio.unidadeFederacao unidadeFederacao "
							+ " where registroAtendimento.id = :idRegistroAtendimento";
			retorno = session.createQuery(consulta).setInteger("idRegistroAtendimento", idRegistroAtendimento.intValue()).list();

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
	 * [UC0085] Obter Endereço
	 * 
	 * @author Ana Maria
	 * @data 07/08/2006
	 * @param idRegistroAtendimento
	 * @return Collection
	 */
	public Collection pesquisarEnderecoRegistroAtendimentoSolicitanteFormatado(Integer idRegistroAtendimentoSolicitante)
					throws ErroRepositorioException{

		Collection retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta = null;
		try{
			consulta = " select logradouro.nome," // 0
							+ " logradouroTipo.descricao," // 1
							+ " logradouroTitulo.descricao," // 2
							+ " bairro.id," // 3
							+ " bairro.nome," // 4
							+ " municipio.id," // 5
							+ " municipio.nome," // 6
							+ " unidadeFederacao.id," // 7
							+ " unidadeFederacao.sigla," // 8
							+ " cep.cepId," // 9
							+ " cep.logradouro," // 10
							+ " cep.descricaoTipoLogradouro," // 11
							+ " cep.bairro," // 12
							+ " cep.municipio," // 13
							+ " cep.sigla," // 14
							+ " cep.codigo," // 15
							+ " registroAtendimentoSolicitante.complementoEndereco ," // 16
							+ " logradouro.id, " // 17
							+ " logradouroCep.id," // 18
							+ " logradouroBairro.id," // 19
							+ " logradouroTipo.descricao," // 20
							+ " logradouroTitulo.descricao," // 21
							+ " registroAtendimentoSolicitante.numeroImovel" // 22
							+ " from RegistroAtendimentoSolicitante registroAtendimentoSolicitante "
							+ " left join registroAtendimentoSolicitante.logradouroCep logradouroCep "
							+ " left join logradouroCep.cep cep "
							+ " left join logradouroCep.logradouro logradouro "
							+ " left join logradouro.logradouroTipo logradouroTipo "
							+ " left join logradouro.logradouroTitulo logradouroTitulo "
							+ " left join registroAtendimentoSolicitante.logradouroBairro logradouroBairro "
							+ " left join logradouroBairro.bairro bairro "
							+ " left join bairro.municipio municipio "
							+ " left join municipio.unidadeFederacao unidadeFederacao "
							+ " where registroAtendimentoSolicitante.id = :idRegistroAtendimentoSolicitante";
			retorno = session.createQuery(consulta).setInteger("idRegistroAtendimentoSolicitante",
							idRegistroAtendimentoSolicitante.intValue()).list();

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
	 * Obter dados do Logradouro cep para o endereço
	 * 
	 * @author Sávio Luiz
	 * @data 05/09/2006
	 * @param idLogradouroCep
	 * @return Collection
	 */
	public Collection obterDadosLogradouroCepEndereco(Integer idLogradouroCep) throws ErroRepositorioException{

		Collection retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta = null;
		try{
			consulta = " select logradouro.id, " // 0
							+ " logradouro.nome," // 1
							+ " logradouroTipo.descricaoAbreviada," // 2
							+ " logradouroTitulo.descricaoAbreviada," // 3
							+ " cep.cepId," // 4
							+ " cep.codigo" // 5
							+ " from LogradouroCep logradouroCep "
							+ " left join logradouroCep.cep cep "
							+ " left join logradouroCep.logradouro logradouro "
							+ " left join logradouro.logradouroTipo logradouroTipo "
							+ " left join logradouro.logradouroTitulo logradouroTitulo " + " where logradouroCep.id = :idLogradouroCep";
			retorno = session.createQuery(consulta).setInteger("idLogradouroCep", idLogradouroCep.intValue()).list();

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
	 * Obter dados do Logradouro bairro para o endereço
	 * 
	 * @author Sávio Luiz
	 * @data 05/09/2006
	 * @param idLogradouroCep
	 * @return Collection
	 */
	public Collection obterDadosLogradouroBairroEndereco(Integer idLogradouroBairro) throws ErroRepositorioException{

		Collection retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta = null;
		try{
			consulta = " select bairro.id," // 0
							+ " bairro.nome," // 1
							+ " municipio.id," // 2
							+ " municipio.nome," // 3
							+ " unidadeFederacao.id," // 4
							+ " unidadeFederacao.sigla" // 5
							+ " from LogradouroBairro logradouroBairro "
							+ " left join logradouroBairro.bairro bairro "
							+ " left join bairro.municipio municipio "
							+ " left join municipio.unidadeFederacao unidadeFederacao "
							+ " where logradouroBairro.id = :idLogradouroBairro";

			retorno = session.createQuery(consulta).setInteger("idLogradouroBairro", idLogradouroBairro.intValue()).list();

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
	 * Pesquisar os Endereços do Cliente
	 * [UC0474] Consultar Imóvel
	 * 
	 * @author Rafael Santos
	 * @date 19/09/2006
	 * @param idCliente
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarClientesEnderecosAbreviado(Integer idCliente) throws ErroRepositorioException{

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
							" enderecoReferencia.descricao " // 24
							+ "from ClienteEndereco clienteEndereco " + "left join clienteEndereco.logradouroCep logradouroCep "
							+ "left join logradouroCep.cep cep " + "left join logradouroCep.logradouro logradouro "
							+ "left join logradouro.logradouroTipo logradouroTipo "
							+ "left join logradouro.logradouroTitulo logradouroTitulo "
							+ "left join clienteEndereco.logradouroBairro logradouroBairro " + "left join logradouroBairro.bairro bairro "
							+ "left join bairro.municipio municipio " + "left join municipio.unidadeFederacao unidadeFederacao "
							+ "left join clienteEndereco.enderecoReferencia enderecoReferencia "
							+ "inner join clienteEndereco.cliente cliente " + "where cliente.id = :idCliente  ";
			retorno = session.createQuery(consulta).setInteger("idCliente", idCliente.intValue()).list();

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
	 * Pesquisar o endereço abreviado a partir do id do imóvel
	 * [UC0483] - Obter Endereço Abreviado
	 * 
	 * @author Rafael Corrêa
	 * @date 18/10/2006
	 * @param idImovel
	 * @return Object[]
	 * @throws ErroRepositorioException
	 */

	public Object[] obterEnderecoAbreviadoImovel(Integer idImovel) throws ErroRepositorioException{

		Object[] retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta = null;
		try{
			consulta = "select logradouro.nome, " // 0
							+ "imovel.numeroImovel, " // 1
							+ "bairro.nome " // 2
							+ "from Imovel imovel "
							+ "left join imovel.logradouroCep logradouroCep "
							+ "left join logradouroCep.logradouro logradouro "
							+ "left join imovel.logradouroBairro logradouroBairro "
							+ "left join logradouroBairro.bairro bairro " + "where imovel.id = :idImovel";

			retorno = (Object[]) session.createQuery(consulta).setInteger("idImovel", idImovel.intValue()).setMaxResults(1).uniqueResult();

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
	 * Pesquisar o endereço abreviado a partir do id do ra
	 * [UC0483] - Obter Endereço Abreviado
	 * 
	 * @author Rafael Corrêa
	 * @date 18/10/2006
	 * @param idRA
	 * @return Object[]
	 * @throws ErroRepositorioException
	 */

	public Object[] obterEnderecoAbreviadoRA(Integer idRA) throws ErroRepositorioException{

		Object[] retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta = null;
		try{
			consulta = "select logradouro.nome, " // 0 Antes estava logradouro.nome
							+ "ra.numeroImovel, " // 1
							+ "bairro.nome " // 2
							+ "from RegistroAtendimento ra "
							+ "left join ra.logradouroBairro logradouroBairro "
							+ "left join logradouroBairro.logradouro logradouro "
							+ "left join logradouroBairro.bairro bairro "
							+ "where ra.id = :idRA";

			retorno = (Object[]) session.createQuery(consulta).setInteger("idRA", idRA.intValue()).setMaxResults(1).uniqueResult();

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
	 * Pesquisar o cep pelo codigo do cep
	 * 
	 * @author Sávio Luiz
	 * @date 05/11/2007
	 */

	public Cep pesquisarCep(Integer codigoCep) throws ErroRepositorioException{

		Cep retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta = null;
		try{
			consulta = "select cep " + "from Cep cep " + "where cep.codigo = :codigoCep";
			retorno = (Cep) session.createQuery(consulta).setInteger("codigoCep", codigoCep).setMaxResults(1).uniqueResult();

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
	 * Verifica a existência do endereço de correspondência do cliente pelo seu id
	 */
	public boolean verificarExistenciaClienteEndereco(Integer idCliente) throws ErroRepositorioException{

		Integer idClienteEndereco = null;

		boolean existeClienteEndereco = false;

		String hql = "";

		Session session = HibernateUtil.getSession();

		try{

			hql = "select cliEnd.id " + " from ClienteEndereco cliEnd " + " inner JOIN cliEnd.cliente cli " + " where cli.id = :idCliente "
							+ " and cliEnd.indicadorEnderecoCorrespondencia = "
							+ ClienteEndereco.INDICADOR_ENDERECO_CORRESPONDENCIA.toString();

			idClienteEndereco = (Integer) session.createQuery(hql).setInteger("idCliente", idCliente).setMaxResults(1).uniqueResult();

			if(idClienteEndereco != null && !idClienteEndereco.equals("")){
				existeClienteEndereco = true;
			}

		}catch(HibernateException e){

			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return existeClienteEndereco;

	}

	/**
	 * Pesquisar Logradouro Bairro
	 * 
	 * @author Virgínia Melo
	 * @date 24/07/2009
	 * @param idLogradouroBairro
	 * @return LogradouroBairro
	 * @throws ControladorException
	 */
	public LogradouroBairro pesquisarLogradouroBairro(Integer idLogradouroBairro) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		LogradouroBairro retorno = null;

		try{

			Criteria criteria = session.createCriteria(LogradouroBairro.class).add(Restrictions.eq("id", idLogradouroBairro)).setFetchMode(
							"logradouro", FetchMode.JOIN).setFetchMode("logradouro.municipio", FetchMode.JOIN).setFetchMode(
							"logradouro.logradouroTitulo", FetchMode.JOIN).setFetchMode("logradouro.logradouroTipo", FetchMode.JOIN)
							.setFetchMode("bairro", FetchMode.JOIN).setFetchMode("bairro.municipio", FetchMode.JOIN).setFetchMode(
											"bairro.municipio.unidadeFederacao", FetchMode.JOIN);

			List<LogradouroBairro> lista = criteria.list();
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
	 * [UC0XXX] Gerar Relatório Logradouro Geral
	 * 
	 * @author Anderson Italo
	 * @date 26/01/2011
	 *       Obter dados dos Logradouros pelos parametros informados
	 */
	public Collection pesquisarLogradourosPorMunicipio(Integer idMunicipio) throws ErroRepositorioException{

		Collection retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta = null;

		try{
			consulta = "select logradouro.nome," // 0
							+ " logradouroTipo.descricao," // 1
							+ " logradouroTitulo.descricao," // 2
							+ " logradouro.id " // 3
							+ "from Logradouro logradouro "
							+ "left join logradouro.logradouroTipo logradouroTipo "
							+ "left join logradouro.logradouroTitulo logradouroTitulo "
							+ "left join logradouro.municipio municipio "
							+ "where municipio.id = :idMunicipio";
			retorno = session.createQuery(consulta).setInteger("idMunicipio", idMunicipio.intValue()).list();

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
	 * [UC0XXX] Gerar Relatório Logradouro Geral
	 * 
	 * @author Anderson Italo
	 * @date 26/01/2011
	 *       Obter total dos Logradouros pelos por Município
	 */
	public Integer calcularTotalLogradourosPorMunicipio(Integer idMunicipio) throws ErroRepositorioException{

		Integer retorno = 0;
		Session session = HibernateUtil.getSession();
		String consulta = null;

		try{
			consulta = "select count(logradouro.id) " + "from Logradouro logradouro " + "where logradouro.municipio.id = :idMunicipio";
			Object total = session.createQuery(consulta).setInteger("idMunicipio", idMunicipio.intValue()).uniqueResult();

			if(total != null && !total.toString().equals("")){
				retorno = ((Number) total).intValue();
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
	 * Obtem o Bairro pelo id do imóvel (se informado), caso não informado o id do imóvel ou não
	 * encontrado bairro para aquele imóvel, então procura o bairro pelo id do RA
	 * 
	 * @author isilva
	 * @param idImovel
	 * @param idRegistroAtendimento
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Bairro obterBairroPorImovelOuRA(Integer idImovel, Integer idRegistroAtendimento) throws ErroRepositorioException{

		Bairro retorno = null;
		Session session = HibernateUtil.getSession();
		Query query = null;
		try{

			if(idRegistroAtendimento != null){
				StringBuffer consulta = new StringBuffer();
				consulta.append("select ");
				consulta.append("bairro ");
				consulta.append("from RegistroAtendimentoSolicitante raSolicitante ");
				consulta.append("inner join raSolicitante.registroAtendimento ra ");
				consulta.append("left join ra.logradouroBairro logradouroBairro ");
				consulta.append("left join logradouroBairro.bairro bairro ");
				consulta.append("where ");
				consulta.append("raSolicitante.indicadorSolicitantePrincipal = :indicadorSolicitantePrincipal and ");
				consulta.append("ra.id = :idRegistroAtendimento ");

				query = session.createQuery(consulta.toString());

				retorno = (Bairro) query.setInteger("idRegistroAtendimento", idRegistroAtendimento.intValue()).setInteger(
								"indicadorSolicitantePrincipal", ConstantesSistema.SIM.intValue()).setMaxResults(1).uniqueResult();
			}

			if(retorno == null && idImovel != null){
				StringBuffer consulta = new StringBuffer();
				consulta.append("select ");
				consulta.append("bairro ");
				consulta.append("from Imovel imovel ");
				consulta.append("left join imovel.logradouroBairro logradouroBairro ");
				consulta.append("left join logradouroBairro.bairro bairro ");
				consulta.append("where ");
				consulta.append("imovel.id = :idImovel ");

				query = session.createQuery(consulta.toString());

				retorno = (Bairro) query.setInteger("idImovel", idImovel.intValue()).setMaxResults(1).uniqueResult();
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
	 * Consulta o logradouro bairro e retorna o logradouro
	 * 
	 * @author Josenildo Neves
	 * @date 07/05/2012
	 */
	public Integer consultarLogradouroEmLogradouroBairro(Integer idLogradouroBairro) throws ErroRepositorioException{

		Integer idLogradouro = null;
		Object retorno = null;

		Session session = HibernateUtil.getSession();

		StringBuffer consulta = new StringBuffer();

		try{
			consulta.append("SELECT ");
			consulta.append("   lgbr.LOGR_ID as idLogradouro ");
			consulta.append("FROM LOGRADOURO_BAIRRO lgbr ");
			consulta.append("WHERE ");
			consulta.append("   lgbr.LGBR_ID = :idLogradouroBairro ");

			retorno = session.createSQLQuery(consulta.toString()).addScalar("idLogradouro", Hibernate.INTEGER).setInteger(
							"idLogradouroBairro", idLogradouroBairro).setMaxResults(1).uniqueResult();

			if(retorno != null){

				idLogradouro = (Integer) retorno;
			}

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		return idLogradouro;
	}

	/**
	 * Consulta o logradouro cep e retorna o logradouro
	 * 
	 * @author Josenildo Neves
	 * @date 07/05/2012
	 */
	public Integer consultarLogradouroEmLogradouroCep(Integer idLogradouroCep) throws ErroRepositorioException{

		Integer idLogradouro = null;
		Object retorno = null;

		Session session = HibernateUtil.getSession();

		StringBuffer consulta = new StringBuffer();

		try{
			consulta.append("SELECT ");
			consulta.append("   lgcp.LOGR_ID as idLogradouro ");
			consulta.append("FROM LOGRADOURO_CEP lgcp ");
			consulta.append("WHERE ");
			consulta.append("   lgbr.LGCP_ID = :idLogradouroCep ");

			retorno = session.createSQLQuery(consulta.toString()).addScalar("idLogradouro", Hibernate.INTEGER).setInteger(
							"idLogradouroCep", idLogradouroCep).setMaxResults(1).uniqueResult();

			if(retorno != null){

				idLogradouro = (Integer) retorno;
			}

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		return idLogradouro;
	}

	/**
	 * Pesquisa na View logradouro por munícipio, bairro, noemLogradouro e retorna uma lista de
	 * logradouros
	 * 
	 * @author Josenildo Neves
	 * @date 08/08/2012
	 */
	public List<LogradouroJSONHelper> pesquisarViewLogradouro(Integer idMunicipio, Integer idBairro, String nomeLogradouro)
					throws ErroRepositorioException{

		List retorno = null;
		List<LogradouroJSONHelper> listaLogradouroJSONHelper = null;

		Session session = HibernateUtil.getSession();

		StringBuffer consulta = new StringBuffer();

		try{
			consulta.append("SELECT ");
			consulta.append("   vLogradouro.LOGR_ID AS idLogradouro, ");
			// consulta.append("   vLogradouro.MUNI_ID AS idMunicipio, ");
			// consulta.append("   vLogradouro.BAIR_ID AS idBairro, ");
			consulta.append("   vLogradouro.LOGR_NMLOGRADOURO_COMPLETO AS nomeLogradouro ");
			consulta.append("FROM VW_LOGRADOURO vLogradouro ");
			consulta.append("WHERE ");
			consulta.append("   vLogradouro.MUNI_ID = :idMunicipio ");
			consulta.append("   AND vLogradouro.BAIR_ID = :idBairro ");
			if(!Util.isVazioOuBranco(nomeLogradouro)){
				consulta.append("   AND vLogradouro.LOGR_NMLOGRADOURO_COMPLETO LIKE '%" + nomeLogradouro + "%' ");
			}
			consulta.append("ORDER BY ");
			consulta.append("vLogradouro.LOGR_NMLOGRADOURO_COMPLETO ");

			retorno = session.createSQLQuery(consulta.toString())//
							.addScalar("idLogradouro", Hibernate.INTEGER)
							/*
							 * .addScalar("idMunicipio", Hibernate.INTEGER).addScalar("idBairro",
							 * Hibernate.INTEGER)
							 */
							.addScalar("nomeLogradouro", Hibernate.STRING)//
							.setInteger("idMunicipio", idMunicipio)//
							.setInteger("idBairro", idBairro)//
							.list();

			if(retorno != null){

				listaLogradouroJSONHelper = new ArrayList<LogradouroJSONHelper>();

				Iterator icolecaoLogradouros = retorno.iterator();

				while(icolecaoLogradouros.hasNext()){

					Object[] LogradourosArray = (Object[]) icolecaoLogradouros.next();

					LogradouroJSONHelper logradouroJSONHelper = new LogradouroJSONHelper();

					logradouroJSONHelper.setId((Integer) LogradourosArray[0]);
					logradouroJSONHelper.setNomeLogradouro(String.valueOf(LogradourosArray[1]));

					listaLogradouroJSONHelper.add(logradouroJSONHelper);
				}

			}

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		return listaLogradouroJSONHelper;
	}

	/**
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer obterMunicipio(Integer idImovel) throws ErroRepositorioException{

		Integer idLogradouro = null;
		Object retorno = null;

		Session session = HibernateUtil.getSession();

		StringBuffer consulta = new StringBuffer();

		try{
			consulta.append("SELECT ");
			consulta.append("   l.muni_id as idLogradouro ");
			consulta.append("FROM cliente_endereco ce ");
			consulta.append("INNER JOIN cliente_imovel ci on ci.clie_id = ce.clie_id ");
			consulta.append("INNER JOIN logradouro l on l.logr_id = ce.logr_id ");
			consulta.append("WHERE ");
			consulta.append("   ci.crtp_id = :idClienteRelacaoTipo ");
			consulta.append("  and ci.imov_id = :idImovel ");

			retorno = session.createSQLQuery(consulta.toString()).addScalar("idLogradouro", Hibernate.INTEGER).setInteger(
							"idClienteRelacaoTipo", ClienteRelacaoTipo.RESPONSAVEL).setInteger("idImovel", idImovel).setMaxResults(1)
							.uniqueResult();

			if(retorno != null){

				idLogradouro = (Integer) retorno;
			}

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		return idLogradouro;
	}

	public Integer pesquisarCepFiltroCount(String nomeLogradouro, String codigoLado, String faixa, String nomeMunicipio)
					throws ErroRepositorioException{

		Integer retorno = null;

		Session session = HibernateUtil.getSession();

		try{

			String sql = "select count(distinct cep) from gcom.cadastro.endereco.Cep cep ";

			String condicional = "";

			condicional = " cep.indicadorUso= " + ConstantesSistema.INDICADOR_USO_ATIVO + " and ";

			if(nomeLogradouro != null && !nomeLogradouro.equals("")){

				condicional = condicional + " cep.logradouro like '%" + nomeLogradouro.trim() + "%' and ";

			}

			if(!Util.isVazioOuBranco(nomeMunicipio)){
				condicional = condicional + " cep.municipio like '%" + nomeMunicipio.trim() + "%' and ";
			}

			if(codigoLado != null && !codigoLado.equals(ConstantesSistema.INVALIDO_ID.toString())){

				condicional = condicional + " cep.codigoLadoCep= '" + codigoLado + "' and ";

			}
			if(faixa != null && !faixa.equals("")){

				condicional = condicional + " cep.numeroFaixaIncial <= " + faixa + " and " + " cep.numeroFaixaFinal >= " + faixa + " and ";

			}
			if(!condicional.trim().equals("")){

				sql = sql + " Where " + condicional;
			}else{

				sql = sql + " and ";
			}

			retorno = ((Number) session.createQuery(Util.formatarHQL(sql, 4)).uniqueResult()).intValue();

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

	public Collection pesquisarCepFiltro(String nomeLogradouro, String codigoLado, String faixa, Integer numeroPaginas, String nomeMunicipio)
					throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();

		try{

			String sql = "select distinct cep from gcom.cadastro.endereco.Cep cep ";

			String condicional = "";

			condicional = " cep.indicadorUso= " + ConstantesSistema.INDICADOR_USO_ATIVO + " and ";

			if(nomeLogradouro != null && !nomeLogradouro.equals("")){

				condicional = condicional + " cep.logradouro like '%" + nomeLogradouro.trim() + "%' and ";

			}

			if(!Util.isVazioOuBranco(nomeMunicipio)){
				condicional = condicional + " cep.municipio like '%" + nomeMunicipio.trim() + "%' and ";
			}

			if(codigoLado != null && !codigoLado.equals(ConstantesSistema.INVALIDO_ID.toString())){

				condicional = condicional + " cep.codigoLadoCep= '" + codigoLado + "' and ";

			}
			if(faixa != null && !faixa.equals("")){

				condicional = condicional + " cep.numeroFaixaIncial<= " + faixa + " and " + " cep.numeroFaixaFinal>= " + faixa + " and ";

			}
			if(!condicional.trim().equals("")){

				sql = sql + " Where " + condicional;
			}else{

				sql = sql + " and ";
			}

			retorno = session.createQuery(Util.formatarHQL(sql, 4)).setFirstResult(10 * numeroPaginas).setMaxResults(10).list();

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
	 * FIXME - MOVER PARA CONTROLADOR FATURAMENTO APOS IMPLEMENTAÇÃO
	 * 
	 * @return
	 * @throws ControladorException
	 */
	public Collection<Object[]> consultarPagamentosHistoricoAdmiteDevolucao(Integer idImovel, boolean creditoARealizar) throws ErroRepositorioException{

		Collection<Object[]> retorno = null;

		Session session = HibernateUtil.getSession();

		StringBuffer consulta = new StringBuffer();

		try{
			consulta.append(" Select ph.pghi_id as idPgtoHist,  ");
			consulta.append("        ph.pghi_dtpagamento as dataPagamento,  ");
			consulta.append("        ph.pghi_vlpagamento as valorPagamento,  ");
			consulta.append("        ph.PGST_IDATUAL as pagamentoSituacao,  ");
			consulta.append("        ps.pgst_dspagamentosituacao as situacaoDSPgto, ");
			consulta.append("        ph.PGHI_AMREFERENCIAPAGAMENTO as anoMesReferencia, ");
			consulta.append("        ph.GPAG_ID as guiaPagamentoGeral, ");
			consulta.append("        ph.DBAC_ID as debitoACobrar,  ");
			consulta.append("        ph.PGHI_NNPRESTACAO as numeroPrestacao,  ");
			consulta.append("        (NVL(ch.CNHI_VLAGUA,0) + NVL(ch.CNHI_VLESGOTO,0) + NVL(ch.CNHI_VLDEBITOS,0)) - (NVL(ch.CNHI_VLCREDITOS,0) + NVL(ch.CNHI_VLIMPOSTOS,0)) as valorTotalContaHistorico, ");
			consulta.append("        gpph.GPPH_VLPRESTACAO as valorPrestacaoGuiaPgtoHist, ");
			consulta.append("        (dach.DAHI_VLDEBITO / NVL(dach.dahi_nnprestacaodebito, 1) * (NVL(dach.dahi_nnprestacaodebito, 0) - NVL(dach.dahi_nnprestacaocobradas, 0) )) as valorDebitoACobrarPrestacoes, ");
			consulta.append("        ch.DCST_IDATUAL as idAtualConta, ");
			consulta.append("        gpph.DCST_ID as idAtualGuia, ");
			consulta.append("        dach.DCST_IDATUAL as idAtualDebito ");
			consulta.append("  from pagamento_historico ph ");
			consulta.append("  inner join IMOVEL i on ph.imov_id = i.imov_id ");
			consulta.append("  inner join pagamento_situacao ps on ps.pgst_id = ph.pgst_idatual ");
			// Na Migração pode não ter relacionado o ID da conta e com isso é mais seguro relacionar ao anoMes e idImovel
			consulta.append("  left join conta_historico ch on (ch.CNHI_AMREFERENCIACONTA = ph.PGHI_AMREFERENCIAPAGAMENTO ");
			consulta.append("                                    AND ch.IMOV_ID = i.IMOV_ID ");
			consulta.append("                                    AND ch.DCST_IDATUAL <> " + DebitoCreditoSituacao.CANCELADA_POR_RETIFICACAO.intValue() + " ) ");
			consulta.append("  left join guia_pagamento_prestacao_hist gpph on ( gpph.gpag_id = ph.GPAG_ID and gpph.gpph_nnprestacao = ph.PGHI_NNPRESTACAO and gpph.dbtp_id = ph.dbtp_id) ");
			consulta.append("  left join debito_a_cobrar_historico dach on dach.dbac_id = ph.dbac_id ");
			consulta.append("  ");
			consulta.append(" where i.imov_id = " + idImovel);
			consulta.append(" and ps.PGST_ICADMITEDEVOLUCAO = 1 "); // 1 = SIM
			
			if(creditoARealizar){
				consulta.append(" and not exists (select car.crar_id  from CREDITO_A_REALIZAR car where car.pghi_id = ph.pghi_id and car.DCST_IDATUAL <> "
								+ DebitoCreditoSituacao.CANCELADA.intValue() + ") "); // CANCELADA = 3
			}
			
			consulta.append(" and not exists (select carh.crar_id from CREDITO_A_REALIZAR_HISTORICO carh where carh.pghi_id = ph.pghi_id and carh.DCST_IDATUAL <> "
							+ DebitoCreditoSituacao.CANCELADA.intValue() + ") "); // CANCELADA = 3
			consulta.append(" and not exists (select cr.crrz_id   from CREDITO_REALIZADO cr where cr.pghi_id = ph.pghi_id) ");
			consulta.append(" and not exists (select crh.crar_id  from CREDITO_REALIZADO_HISTORICO crh where crh.pghi_id = ph.pghi_id) ");

			consulta.append("  order by ph.pghi_dtpagamento desc ");
			SQLQuery sqlQuery = session.createSQLQuery(consulta.toString());

			sqlQuery.addScalar("idPgtoHist", Hibernate.INTEGER);
			sqlQuery.addScalar("dataPagamento", Hibernate.DATE);
			sqlQuery.addScalar("valorPagamento", Hibernate.BIG_DECIMAL);
			sqlQuery.addScalar("pagamentoSituacao", Hibernate.SHORT);
			sqlQuery.addScalar("situacaoDSPgto", Hibernate.STRING);
			sqlQuery.addScalar("anoMesReferencia", Hibernate.INTEGER);
			sqlQuery.addScalar("guiaPagamentoGeral", Hibernate.INTEGER);
			sqlQuery.addScalar("debitoACobrar", Hibernate.INTEGER);
			sqlQuery.addScalar("numeroPrestacao", Hibernate.INTEGER);
			sqlQuery.addScalar("valorTotalContaHistorico", Hibernate.BIG_DECIMAL);
			sqlQuery.addScalar("valorPrestacaoGuiaPgtoHist", Hibernate.BIG_DECIMAL);
			sqlQuery.addScalar("valorDebitoACobrarPrestacoes", Hibernate.BIG_DECIMAL);
			sqlQuery.addScalar("idAtualConta", Hibernate.INTEGER);
			sqlQuery.addScalar("idAtualGuia", Hibernate.INTEGER);
			sqlQuery.addScalar("idAtualDebito", Hibernate.INTEGER);

			retorno = sqlQuery.list();

		}catch(Exception e){
			e.printStackTrace();
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	public Integer pesquisarQuantidadePagamentosHistoricoCount(PagamentoHistoricoAdmiteDevolucaoHelper pagamento, String matriculaImovel)
					throws ErroRepositorioException{

		Integer retorno = null;

		Session session = HibernateUtil.getSession();

		StringBuffer consulta = new StringBuffer();

		try{
			consulta.append(" Select count(ph.pghi_id) as qtde ");
			consulta.append("  from pagamento_historico ph ");

			if(PagamentoHistoricoAdmiteDevolucaoHelper.TIPO_CONTA.equals(pagamento.getTipoIdentificaçãoDocumento())){
				consulta.append("  INNER JOIN conta_historico ch on  ch.CNHI_AMREFERENCIACONTA = ph.PGHI_AMREFERENCIAPAGAMENTO AND ch.IMOV_ID = ph.imov_id ");

			}else if(PagamentoHistoricoAdmiteDevolucaoHelper.TIPO_GUIA_PGTO.equals(pagamento.getTipoIdentificaçãoDocumento())){
				consulta.append("  INNER JOIN guia_pagamento_prestacao_hist gpph on ( gpph.gpag_id = ph.GPAG_ID and gpph.gpph_nnprestacao = ph.PGHI_NNPRESTACAO and gpph.dbtp_id = ph.dbtp_id) ");

			}else if(PagamentoHistoricoAdmiteDevolucaoHelper.TIPO_DEBITO_A_COBRAR.equals(pagamento.getTipoIdentificaçãoDocumento())){
				consulta.append("  INNER JOIN debito_a_cobrar_historico dach on dach.dbac_id = ph.dbac_id ");

			}

			consulta.append(" where ph.imov_id = " + matriculaImovel);

			if(PagamentoHistoricoAdmiteDevolucaoHelper.TIPO_CONTA.equals(pagamento.getTipoIdentificaçãoDocumento())){
				String anoMesRefConta = Util.formatarMesAnoParaAnoMes(pagamento.getIndentificacaoDocumento());
				consulta.append(" AND ph.PGHI_AMREFERENCIAPAGAMENTO = " + anoMesRefConta);

			}else if(PagamentoHistoricoAdmiteDevolucaoHelper.TIPO_GUIA_PGTO.equals(pagamento.getTipoIdentificaçãoDocumento())){
				String[] split = pagamento.getIndentificacaoDocumento().split(PagamentoHistoricoAdmiteDevolucaoHelper.CONCATENAR_ID_NNPRESTACAO);
				String idGuiaPagamento = split[0];
				String numeroPrestacao = split[1];

				consulta.append(" AND ph.GPAG_ID = " + idGuiaPagamento);
				consulta.append(" AND ph.PGHI_NNPRESTACAO = " + numeroPrestacao);

			}else if(PagamentoHistoricoAdmiteDevolucaoHelper.TIPO_DEBITO_A_COBRAR.equals(pagamento.getTipoIdentificaçãoDocumento())){
				String[] split = pagamento.getIndentificacaoDocumento().split(PagamentoHistoricoAdmiteDevolucaoHelper.CONCATENAR_ID_NNPRESTACAO);
				String idDebitoACobrar = split[0];
				consulta.append(" AND ph.dbac_id = " + idDebitoACobrar);

			}else{
				throw new ErroRepositorioException("Nenhum tipo compativel para executar a consulta.");
			}
			SQLQuery sqlQuery = session.createSQLQuery(consulta.toString());
			sqlQuery.addScalar("qtde", Hibernate.INTEGER);

			retorno = (Integer) sqlQuery.uniqueResult();

		}catch(Exception e){
			e.printStackTrace();
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}
	
	/**
	 * Pesquisar o Logradouro Tipo a partir do id do imóvel
	 * Obter Logradouro Tipo
	 * 
	 * @author Eduardo Oliveira
	 * @param idImovel
	 * @return Object[]
	 * @throws ErroRepositorioException
	 */

	public Object[] obterLogradouroTipoImovel(Integer idImovel) throws ErroRepositorioException{

		Object[] retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta = null;
		try{
			consulta = "select logradouroTipo.descricao, " // 0
							+ "logradouro.nome "
							+ "from Imovel imovel "
							+ "left join imovel.logradouroCep logradouroCep "
							+ "left join logradouroCep.logradouro logradouro "
							+ "left join logradouro.logradouroTipo logradouroTipo "
							+ "left join imovel.logradouroBairro logradouroBairro "
							+ "left join logradouroBairro.bairro bairro "
							+ "where imovel.id = :idImovel";

			retorno = (Object[]) session.createQuery(consulta).setInteger("idImovel", idImovel.intValue()).setMaxResults(1).uniqueResult();

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
	 * Agencia Virtual - Consultar Cep Por Logradouro e Bairro
	 * 
	 * @author Anderson Italo
	 * @date 16/03/2014
	 */
	public List<Object[]> pesquisarCepPorLogradouroEBairro(Integer idLogradouro, Integer idBairro) throws ErroRepositorioException{

		List<Object[]> retorno = null;
		Session session = HibernateUtil.getSession();
		StringBuffer consulta = new StringBuffer();

		try{

			consulta.append(" select distinct(ce.cep_id) as idCep, ce.cep_cdcep as codigoCep ");
			consulta.append(" from cep ce ");
			consulta.append(" inner join logradouro_cep lc on lc.cep_id = ce.cep_id ");
			consulta.append(" where ");
			consulta.append(" lc.logr_id = :idLogradouro ");
			consulta.append(" and exists (select 1 from logradouro_bairro lb ");
			consulta.append(" where lb.logr_id = :idLogradouro and lb.bair_id = :idBairro) ");
			consulta.append(" order by ce.cep_cdcep ");

			retorno = session.createSQLQuery(consulta.toString()).addScalar("idCep", Hibernate.INTEGER)
							.addScalar("codigoCep", Hibernate.INTEGER)
							.setInteger("idLogradouro", idLogradouro.intValue())
							.setInteger("idBairro", idBairro.intValue()).list();

		}catch(HibernateException e){

			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{

			HibernateUtil.closeSession(session);
		}

		return retorno;
	}
}

