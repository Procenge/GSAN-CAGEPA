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

package gcom.cadastro.cliente;

import gcom.cadastro.cliente.bean.ClienteImovelRelacaoHelper;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.imovel.ImovelContaEnvio;
import gcom.util.ConstantesSistema;
import gcom.util.ErroRepositorioException;
import gcom.util.HibernateUtil;
import gcom.util.filtro.GeradorHQLCondicional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.concurrent.CopyOnWriteArraySet;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 * < <Descrição da Classe>>
 * 
 * @author Administrador
 * @created 22 de Julho de 2005
 */
public class RepositorioClienteImovelHBM
				implements IRepositorioClienteImovel {

	private static IRepositorioClienteImovel instancia;

	/**
	 * Constructor for the RepositorioClienteTipoHBM object
	 */
	public RepositorioClienteImovelHBM() {

	}

	/**
	 * Retorna o valor de instancia
	 * 
	 * @return O valor de instancia
	 */
	public static IRepositorioClienteImovel getInstancia(){

		if(instancia == null){
			instancia = new RepositorioClienteImovelHBM();
		}

		return instancia;
	}

	/**
	 * < <Descrição do método>>
	 * 
	 * @param clienteImovel
	 *            Descrição do parâmetro
	 * @exception ErroRepositorioException
	 *                Descrição da exceção
	 */
	public void inserirClienteImovel(ClienteImovel clienteImovel) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();

		try{
			session.save(clienteImovel);
			session.flush();

		}catch(HibernateException e){
			e.printStackTrace();
			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
			// session.close();
		}

	}

	/**
	 * Pesquisa uma coleção de cliente imovel com uma query especifica
	 * 
	 * @param filtroClienteImovel
	 *            parametros para a consulta
	 * @return Description of the Return Value
	 * @exception ErroRepositorioException
	 *                Description of the Exception
	 */

	public Collection pesquisarClienteImovel(FiltroClienteImovel filtroClienteImovel, Integer numeroPagina) throws ErroRepositorioException{

		// cria a coleção de retorno
		Collection retorno = null;
		// obtém a sessão
		Session session = HibernateUtil.getSession();

		try{
			// pesquisa a coleção de atividades e atribui a variável "retorno"
			retorno = new ArrayList(
							new CopyOnWriteArraySet(
											GeradorHQLCondicional
															.gerarCondicionalQuery(
																			filtroClienteImovel,
																			"gcom.cadastro.cliente.ClienteImovel",
																			"clienteImovel",
																			"select distinct new gcom.cadastro.cliente.ClienteImovelSimplificado(clienteImovel.imovel.id,clienteImovel.imovel.numeroImovel,clienteImovel.imovel.logradouroCep, clienteImovel.imovel.logradouroBairro,"
																							+ "clienteImovel.imovel.quadra,clienteImovel.imovel.enderecoReferencia,clienteImovel.imovel.complementoEndereco,clienteImovel.cliente.nome,clienteImovel.cliente.id,clienteImovel.imovel.setorComercial,clienteImovel.imovel.localidade,clienteImovel.dataFimRelacao,clienteImovel.imovel.ultimaAlteracao) "
																							+ "from gcom.cadastro.cliente.ClienteImovel clienteImovel "
																							+ "left join clienteImovel.imovel.logradouroCep "
																							+ "left join clienteImovel.imovel.logradouroBairro "
																							+ "left join clienteImovel.cliente "
																							+ "left join clienteImovel.imovel.setorComercial "
																							+ "left join clienteImovel.imovel.enderecoReferencia "
																							+ "left join clienteImovel.imovel.quadra "
																							+ "left join clienteImovel.imovel.localidade ",

																			session).setFirstResult(10 * numeroPagina).setMaxResults(10)
															.list()));

			// Carrega os objetos informados no filtro
			/*
			 * if (!filtroClienteImovel
			 * .getColecaoCaminhosParaCarregamentoEntidades().isEmpty()) {
			 * PersistenciaUtil.processaObjetosParaCarregamento(
			 * filtroClienteImovel
			 * .getColecaoCaminhosParaCarregamentoEntidades(),
			 * retorno);
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

	/**
	 * Pesquisa uma a quantidade de cliente imovel com uma query especifica
	 * [UC0015] Filtrar Imovel
	 * 
	 * @param filtroClienteImovel
	 *            parametros para a consulta
	 * @author Rafael Santos
	 * @since 26/06/2006
	 * @return Description of the Return Value
	 * @exception ErroRepositorioException
	 *                Description of the Exception
	 */

	public int pesquisarQuantidadeClienteImovel(FiltroClienteImovel filtroClienteImovel) throws ErroRepositorioException{

		// cria a coleção de retorno
		Integer quantidade = null;
		// obtém a sessão
		Session session = HibernateUtil.getSession();

		filtroClienteImovel.getColecaoCaminhosParaCarregamentoEntidades().clear();

		try{
			// pesquisa a coleção de atividades e atribui a variável "retorno"
			quantidade = ((Number) GeradorHQLCondicional.gerarCondicionalQuery(filtroClienteImovel, "gcom.cadastro.cliente.ClienteImovel",
							"clienteImovel",
							"select count(distinct clienteImovel.imovel.id) " + "from gcom.cadastro.cliente.ClienteImovel clienteImovel ",
							session).uniqueResult()).intValue();

			// erro no hibernate
		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}
		// retorna a coleção de atividades pesquisada(s)

		quantidade.intValue();

		return quantidade;
	}

	/**
	 * < <Descrição do método>>
	 * 
	 * @param imovel
	 *            Descrição do parâmetro
	 * @param clienteRelacaoTipo
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 * @exception ErroRepositorioException
	 *                Descrição da exceção
	 */
	public Collection pesquisarClienteImovelResponsavelEsferaPoder(Imovel imovel, ClienteRelacaoTipo clienteRelacaoTipo)
					throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "select ci.id, c.id, " + "c.nome, ci.dataFimRelacao from " + "ClienteImovel ci " + "inner join ci.cliente c "
							+ "inner join c.clienteTipo.esferaPoder ep " + "where ci.imovel.id = :imovel ep.id is not null "
							+ "and ci.clienteRelacaoTipo.id = :clienteRelacaoTipo and ci.dataFimRelacao is null ";

			retorno = session.createQuery(consulta).setInteger("imovel", imovel.getId().intValue()).setInteger("clienteRelacaoTipo",
							clienteRelacaoTipo.getId().intValue()).list();

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
	 * Pesquisa o nome do cliente a partir do imóvel Autor: Sávio Luiz Data:
	 * 21/12/2005
	 */
	public String pesquisarNomeClientePorImovel(Integer idImovel) throws ErroRepositorioException{

		String retorno = null;
		Session session = HibernateUtil.getSession();
		StringBuffer consulta = new StringBuffer();
		try{
			consulta.append("select clienteImovel.cliente.nome ") //
							.append("from ClienteImovel clienteImovel ") //
							.append("left join clienteImovel.cliente cliente ") //
							.append("left join clienteImovel.imovel imovel ") //
							.append("where clienteImovel.clienteRelacaoTipo.id = :tipoUsuario  and ") //
							.append("clienteImovel.dataFimRelacao = null and ") //
							.append("imovel.id = :idImovel");

			retorno = (String) session.createQuery(consulta.toString()).setInteger("idImovel", idImovel).setInteger("tipoUsuario",
							ClienteRelacaoTipo.USUARIO).setMaxResults(1).uniqueResult();

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
	 * Pesquisa uma coleção de cliente imovel com uma query especifica
	 * 
	 * @param filtroClienteImovel
	 *            parametros para a consulta
	 * @return Description of the Return Value
	 * @exception ErroRepositorioException
	 *                Description of the Exception
	 */

	public Collection pesquisarClienteImovel(FiltroClienteImovel filtroClienteImovel) throws ErroRepositorioException{

		// cria a coleção de retorno
		Collection retorno = null;
		// obtém a sessão
		Session session = HibernateUtil.getSession();

		try{
			// pesquisa a coleção de atividades e atribui a variável "retorno"
			retorno = new ArrayList(
							new CopyOnWriteArraySet(
											GeradorHQLCondicional
															.gerarCondicionalQuery(
																			filtroClienteImovel,
																			"gcom.cadastro.cliente.ClienteImovel",
																			"clienteImovel",
																			"select distinct new gcom.cadastro.cliente.ClienteImovelSimplificado(clienteImovel.imovel.id,clienteImovel.imovel.numeroImovel,clienteImovel.imovel.logradouroCep, clienteImovel.imovel.logradouroBairro,"
																							+ "clienteImovel.imovel.quadra,clienteImovel.imovel.enderecoReferencia,clienteImovel.imovel.complementoEndereco,clienteImovel.cliente.nome,clienteImovel.cliente.id,clienteImovel.imovel.setorComercial,clienteImovel.imovel.localidade,clienteImovel.dataFimRelacao,clienteImovel.imovel.ultimaAlteracao) "
																							+ "from gcom.cadastro.cliente.ClienteImovel clienteImovel "
																							+ "left join clienteImovel.imovel.logradouroCep "
																							+ "left join clienteImovel.imovel.logradouroBairro "
																							+ "left join clienteImovel.cliente "
																							+ "left join clienteImovel.imovel.setorComercial "
																							+ "left join clienteImovel.imovel.enderecoReferencia "
																							+ "left join clienteImovel.imovel.quadra "
																							+ "left join clienteImovel.imovel.localidade ",

																			session).list()));

			// Carrega os objetos informados no filtro
			/*
			 * if (!filtroClienteImovel
			 * .getColecaoCaminhosParaCarregamentoEntidades().isEmpty()) {
			 * PersistenciaUtil.processaObjetosParaCarregamento(
			 * filtroClienteImovel
			 * .getColecaoCaminhosParaCarregamentoEntidades(),
			 * retorno);
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

	/**
	 * Pesquisa uma coleção de cliente imovel com uma query especifica
	 * 
	 * @param filtroClienteImovel
	 *            parametros para a consulta
	 * @return Description of the Return Value
	 * @exception ErroRepositorioException
	 *                Description of the Exception
	 */

	public Collection<Imovel> pesquisarClienteImovelRelatorio(FiltroClienteImovel filtroClienteImovel) throws ErroRepositorioException{

		// cria a coleção de retorno
		Collection retorno = new HashSet<Imovel>();
		// obtém a sessão
		Session session = HibernateUtil.getSession();

		try{
			// pesquisa a coleção de atividades e atribui a variável "retorno"
			retorno.addAll((GeradorHQLCondicional.gerarCondicionalQuery(filtroClienteImovel, "gcom.cadastro.cliente.ClienteImovel",
							"clienteImovel", "select imovel " + "from gcom.cadastro.cliente.ClienteImovel clienteImovel "
											+ "inner join clienteImovel.imovel imovel " + "inner join fetch imovel.imovelPerfil "
											+ "inner join fetch imovel.ligacaoAguaSituacao "
											+ "inner join fetch imovel.ligacaoEsgotoSituacao " + "inner join fetch imovel.setorComercial "
											+ "left join fetch imovel.enderecoReferencia " + "inner join fetch imovel.quadra "
											+ "inner join fetch imovel.rota " + "inner join fetch imovel.localidade loca "
											+ "inner join fetch loca.gerenciaRegional " + "inner join fetch loca.unidadeNegocio "
											+ "left join fetch imovel.hidrometroInstalacaoHistorico hidroid "
											+ "left join fetch hidroid.hidrometroProtecao "
											+ "left join fetch hidroid.hidrometroLocalInstalacao " + "left join imovel.imovelPrincipal "
											+ "left join fetch imovel.pavimentoCalcada " + "left join fetch imovel.pavimentoRua "
											+ "left join fetch imovel.despejo " + "left join fetch imovel.pocoTipo "
											+ "left join fetch imovel.ligacaoEsgoto ligaEsgoto "
											+ "left join fetch ligaEsgoto.ligacaoEsgotoDiametro "
											+ "left join fetch ligaEsgoto.ligacaoEsgotoMaterial " + "left join fetch hidroid.hidrometro "
											+ "left join fetch hidroid.hidrometro.hidrometroCapacidade "
											+ "left join fetch hidroid.hidrometro.hidrometroMarca "
											+ "left join fetch hidroid.hidrometro.hidrometroDiametro "
											+ "left join fetch hidroid.hidrometro.hidrometroTipo "
											+ "left join fetch imovel.reservatorioVolumeFaixaSuperior "
											+ "left join fetch imovel.reservatorioVolumeFaixaInferior "
											+ "left join fetch imovel.piscinaVolumeFaixa " + "left join fetch imovel.areaConstruidaFaixa "
											+ "left join fetch imovel.ligacaoAgua lagu " + "left join fetch lagu.ligacaoAguaDiametro "
											+ "left join fetch lagu.ligacaoAguaMaterial "
											+ "left join fetch lagu.hidrometroInstalacaoHistorico laguHidro "
											+ "left join fetch laguHidro.hidrometro "
											+ "left join fetch laguHidro.hidrometro.hidrometroCapacidade "
											+ "left join fetch laguHidro.hidrometro.hidrometroMarca "
											+ "left join fetch laguHidro.hidrometro.hidrometroDiametro "
											+ "left join fetch laguHidro.hidrometro.hidrometroTipo "
											+ "left join fetch laguHidro.hidrometroLocalInstalacao "
											+ "left join fetch laguHidro.hidrometroProtecao "
											+ "left join fetch imovel.logradouroCep lgcp " + "left join fetch lgcp.logradouro lgrd "
											+ "left join fetch lgrd.logradouroTipo " + "left join fetch lgrd.logradouroTitulo "
											+ "left join fetch lgcp.cep " + "left join fetch imovel.logradouroBairro lgbr "
											+ "left join fetch lgbr.logradouro " + "left join fetch lgbr.bairro bair "
											+ "left join fetch bair.municipio munc " + "left join fetch munc.unidadeFederacao "
											+ "left join clienteImovel.cliente ",

							session)).list());

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

	private String gerarHqlFiltroClienteImovel(FiltroClienteImovel filtroClienteImovel){

		String hqlFiltro = "";

		if(filtroClienteImovel != null){

		}
		return hqlFiltro;
	}

	/**
	 * [UC0544] Gerar Arwuivo Texto do Faturamento
	 * Pesquisar ClienteImovel
	 * 
	 * @author Flávio Cordeiro
	 * @date 4/04/2006
	 * @return Colletion
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarClienteImovelGerarArquivoFaturamento() throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = " select distinct(clim.cliente.id), clim.cliente.nome" + " from ClienteImovel clim" + " left join clim.cliente clie"
							+ " left join clim.imovel imov" + " where clim.cliente.indicadorGeraArquivoTexto =:indicadorGera "
							+ " and clim.dataFimRelacao IS NULL " + " and clim.clienteRelacaoTipo.id =:idRelacao";

			retorno = (Collection) session.createQuery(consulta).setInteger("indicadorGera", Cliente.GERA_ARQUIVO_TEXTO_SIM).setInteger(
							"idRelacao", ClienteRelacaoTipo.RESPONSAVEL).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0320] Gerar Fatura de Cliente Responsável
	 * 
	 * @author Saulo Lima
	 * @date 16/12/2008
	 * @return Colletion<Object>
	 * @throws ErroRepositorioException
	 */
	public Collection<Object> pesquisarClienteImovelGerarFaturaClienteResponsavel() throws ErroRepositorioException{

		Collection<Object> retorno = null;

		Session session = HibernateUtil.getSession();

		try{
			String consulta = "SELECT DISTINCT(clim.cliente.id), clim.cliente.nome" + " FROM ClienteImovel clim"
							+ " LEFT JOIN clim.cliente clie" + " LEFT JOIN clim.imovel imov"
							+ " WHERE clim.imovel.indicadorEmissaoExtratoFaturamento = :indicadorGera "
							+ " AND clim.dataFimRelacao IS NULL AND clim.clienteRelacaoTipo.id = :idRelacao ORDER BY clie.nome";

			retorno = (Collection<Object>) session.createQuery(consulta).setInteger("indicadorGera", ConstantesSistema.SIM).setInteger(
							"idRelacao", ClienteRelacaoTipo.RESPONSAVEL).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * Pesquisar ClienteImovel
	 * 
	 * @author Raphael Rossiter
	 * @date 21/08/2006
	 * @param idCliente
	 *            , idImovel
	 * @return Colletion
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarClienteImovel(Integer idCliente, Integer idImovel) throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = " select clim.cliente.id, clim.cliente.nome" + " from ClienteImovel clim" + " left join clim.cliente clie"
							+ " left join clim.imovel imov"
							+ " where clie.id  = :idCliente AND imov.id = :idImovel AND clim.dataFimRelacao IS NULL ";

			retorno = (Collection) session.createQuery(consulta).setInteger("idCliente", idCliente).setInteger("idImovel", idImovel).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * Pesquisa o nome, cnpj e id do cliente a partir do imóvel Autor: Rafael Corrêa Data:
	 * 25/09/2006
	 */
	public Object[] pesquisarDadosClienteRelatorioParcelamentoPorImovel(Integer idImovel) throws ErroRepositorioException{

		Object[] retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta = null;
		try{
			consulta = "select cliente.id, cliente.nome, "
							+ "cliente.cpf, cliente.cnpj, orgaoExpedidorRg.descricao, unidadeFederacao.sigla, cliente.rg "
							+ "from ClienteImovel clienteImovel " + "left join clienteImovel.cliente cliente "
							+ "left join clienteImovel.imovel imovel " + "left join cliente.orgaoExpedidorRg orgaoExpedidorRg "
							+ "left join cliente.unidadeFederacao unidadeFederacao "
							+ "where clienteImovel.clienteRelacaoTipo.id = :tipoUsuario  and " + "clienteImovel.dataFimRelacao = null and "
							+ "imovel.id = :idImovel";
			retorno = (Object[]) session.createQuery(consulta).setInteger("idImovel", idImovel).setInteger("tipoUsuario",
							ClienteRelacaoTipo.USUARIO).uniqueResult();

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
	 * Autor: Sávio Luiz Data:
	 * 27/11/2006
	 */
	public Collection pesquisarParmsClienteImovel(Integer idImovel) throws ErroRepositorioException{

		Collection retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta = null;
		try{
			consulta = "select cliente.id, " + "clienteImovel.clienteRelacaoTipo.id " + "from ClienteImovel clienteImovel "
							+ "left join clienteImovel.cliente cliente " + "left join clienteImovel.imovel imovel "
							+ "where imovel.id = :idImovel and clienteImovel.dataFimRelacao = null ";
			retorno = session.createQuery(consulta).setInteger("idImovel", idImovel).list();

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
	 * [UC0054] - Inserir Dados Tarifa Social
	 * Pesquisa os Clientes Imóveis pelo id do Cliente, indicador de uso, motivo
	 * do fim da relação, pelo perfil do imóvel e pelo tipo da relação do cliente carregando o
	 * imóvel
	 * Autor: Rafael Corrêa
	 * Data: 27/12/2006
	 */
	public Collection pesquisarClienteImovelPeloClienteTarifaSocial(Integer idCliente) throws ErroRepositorioException{

		Collection retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta = null;

		try{

			consulta = "SELECT clienteImovel " + " FROM ClienteImovel clienteImovel " + " INNER JOIN FETCH clienteImovel.cliente cliente "
							+ " INNER JOIN FETCH clienteImovel.imovel imov " + " INNER JOIN FETCH imov.setorComercial sc "
							+ " INNER JOIN FETCH sc.municipio muni "
							+ " INNER JOIN imov.imovelPerfil imovPerfil "
							+ " LEFT JOIN clienteImovel.clienteRelacaoTipo crtp "
							+ " LEFT JOIN clienteImovel.clienteImovelFimRelacaoMotivo cliImovFimRelMotivo "
							+ " WHERE cliente.id = :idCliente "
							// + " AND imovPerfil.id = " + ImovelPerfil.TARIFA_SOCIAL.toString()
							+ " AND cliente.indicadorUso = " + ConstantesSistema.INDICADOR_USO_ATIVO.toString() + " AND crtp.id = "
							+ ClienteRelacaoTipo.USUARIO.toString() + " AND cliImovFimRelMotivo.id is null "
							+ " AND imov.quantidadeEconomias = 1 ";

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
	 * [UC0054] - Inserir Dados Tarifa Social
	 * Pesquisa os Clientes Imóveis pelo id do Imóvel carregando o imóvel, o cliente, o perfil do
	 * imóvel,
	 * o orgão expedidor do RG e a unidade da federação
	 * Autor: Rafael Corrêa
	 * Data: 27/12/2006
	 */
	public Collection pesquisarClienteImovelPeloImovelTarifaSocial(Integer idImovel) throws ErroRepositorioException{

		Collection retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta = null;

		try{

			consulta = "SELECT clienteImovel " + " FROM ClienteImovel clienteImovel " + " INNER JOIN FETCH clienteImovel.imovel imov "
							+ " INNER JOIN FETCH imov.setorComercial sc " + " INNER JOIN FETCH sc.municipio muni "
							+ " INNER JOIN FETCH clienteImovel.cliente cliente " + " INNER JOIN FETCH imov.imovelPerfil imovPerfil "
							+ " LEFT JOIN FETCH cliente.orgaoExpedidorRg orgaoRg " + " LEFT JOIN FETCH cliente.unidadeFederacao unidFed "
							+ " WHERE imov.id = :idImovel " + " AND clienteImovel.dataFimRelacao is null ";

			retorno = session.createQuery(consulta).setInteger("idImovel", idImovel).list();

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
	 * [UC0054] - Inserir Dados Tarifa Social
	 * Pesquisa os Clientes Imóveis pelo id do Imóvel carregando os dados necessários para retornar
	 * o seu endereço
	 * Autor: Rafael Corrêa
	 * Data: 27/12/2006
	 */
	public Collection pesquisarClienteImovelPeloImovelParaEndereco(Integer idImovel) throws ErroRepositorioException{

		Collection retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta = null;

		try{

			consulta = "SELECT clienteImovel " + " FROM ClienteImovel clienteImovel " + " INNER JOIN FETCH clienteImovel.cliente cliente "
							+ " INNER JOIN FETCH clienteImovel.imovel imov " + " INNER JOIN FETCH imov.localidade loc "
							+ " INNER JOIN FETCH imov.setorComercial sc " + " INNER JOIN FETCH sc.municipio muniSc "
							+ " INNER JOIN FETCH muniSc.unidadeFederacao ufSc " + " INNER JOIN FETCH imov.imovelPerfil imovPerfil "
							+ " INNER JOIN FETCH imov.quadra quadra " + " LEFT JOIN FETCH imov.enderecoReferencia endRef "
							+ " LEFT JOIN FETCH imov.logradouroBairro logrBairro " + " LEFT JOIN FETCH logrBairro.bairro bairro "
							+ " LEFT JOIN FETCH bairro.municipio muniBairro " + " LEFT JOIN FETCH muniBairro.unidadeFederacao ufBairro "
							+ " LEFT JOIN FETCH imov.logradouroCep logrCep " + " LEFT JOIN FETCH logrCep.cep cep "
							+ " LEFT JOIN FETCH logrCep.logradouro logr " + " LEFT JOIN FETCH logr.logradouroTipo logrTp "
							+ " LEFT JOIN FETCH logr.logradouroTitulo logrTitulo " + " WHERE imov.id = :idImovel ";

			retorno = session.createQuery(consulta).setInteger("idImovel", idImovel).list();

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
	 * Retorna o cliente usuario apartir do id do imovel
	 * 
	 * @author Flávio Cordeiro
	 * @date 08/01/2007
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Cliente retornaClienteUsuario(Integer idImovel) throws ErroRepositorioException{

		Cliente cliente = new Cliente();

		Session session = HibernateUtil.getSession();
		String consulta = null;
		Object[] objetoCliente = null;
		Collection colecaoCliente = null;

		try{

			consulta = "select cliente.clie_id as idCliente," + " cliente.clie_nmcliente as nomeCliente"
							+ " from cliente_imovel clienteImovel"
							+ " inner join cliente cliente on cliente.clie_id = clienteImovel.clie_id" + " where clienteImovel.imov_id ="
							+ idImovel + " and clienteImovel.crtp_id = " + ClienteRelacaoTipo.USUARIO
							+ " and clienteImovel.clim_dtrelacaofim is null";

			colecaoCliente = session.createSQLQuery(consulta).addScalar("idCliente", Hibernate.INTEGER).addScalar("nomeCliente",
							Hibernate.STRING).list();

			objetoCliente = (Object[]) colecaoCliente.iterator().next();

			if(objetoCliente[0] != null){
				cliente.setId((Integer) objetoCliente[0]);
			}
			if(objetoCliente[1] != null){
				cliente.setNome((String) objetoCliente[1]);
			}

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		return cliente;
	}

	/**
	 * Retorna os clientes e suas relações tipos a partir do id do imovel
	 * 
	 * @author Sávio Luiz
	 * @date 22/01/2007
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection retornaClientesRelacao(Integer idImovel) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		StringBuffer consulta = new StringBuffer();
		Collection colecao = null;

		try{

			consulta.append("select cliente.clie_id as idCliente ").append(" from cliente_imovel clienteImovel").append(
							" inner join cliente cliente on cliente.clie_id = clienteImovel.clie_id").append(
							" where clienteImovel.imov_id =").append(idImovel).append(" and clienteImovel.crtp_id = ").append(
							ClienteRelacaoTipo.PROPRIETARIO).append(" and clienteImovel.clim_dtrelacaofim is null");

			colecao = session.createSQLQuery(consulta.toString()).addScalar("idCliente", Hibernate.INTEGER).list();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		return colecao;
	}

	/**
	 * Retorna o cliente usuário
	 * 
	 * @author Sávio Luiz
	 * @date 22/01/2007
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer retornaIdClienteUsuario(Integer idImovel) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		StringBuffer consulta = new StringBuffer();
		Integer id = null;

		try{

			consulta.append("select cliente.clie_id as idCliente ").append(" from cliente_imovel clienteImovel").append(
							" inner join cliente cliente on cliente.clie_id = clienteImovel.clie_id").append(
							" where clienteImovel.imov_id =").append(idImovel).append(" and clienteImovel.crtp_id = ").append(
							ClienteRelacaoTipo.USUARIO).append(" and clienteImovel.clim_dtrelacaofim is null");

			id = (Integer) session.createSQLQuery(consulta.toString()).addScalar("idCliente", Hibernate.INTEGER).setMaxResults(1)
							.uniqueResult();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		return id;
	}

	/**
	 * Retorna o cliente usuário
	 * 
	 * @author Sávio Luiz
	 * @date 04/04/2007
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer retornaIdClienteResponsavel(Integer idImovel) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		StringBuffer consulta = new StringBuffer();
		Integer id = null;

		try{

			consulta.append("select cliente.clie_id as idCliente ").append(" from cliente_imovel clienteImovel").append(
							" inner join cliente cliente on cliente.clie_id = clienteImovel.clie_id").append(
							" where clienteImovel.imov_id =").append(idImovel).append(" and clienteImovel.crtp_id = ").append(
							ClienteRelacaoTipo.RESPONSAVEL).append(" and clienteImovel.clim_dtrelacaofim is null");

			id = (Integer) session.createSQLQuery(consulta.toString()).addScalar("idCliente", Hibernate.INTEGER).setMaxResults(1)
							.uniqueResult();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		return id;
	}

	/**
	 * Retorna o tipo da relação do cliente com indicador nome conta
	 * 
	 * @author Rafael Corrêa
	 * @date 17/05/2007
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer retornaTipoRelacaoClienteImovelNomeConta(Integer idImovel) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		String consulta = null;
		Integer idRelacaoTipo = null;

		try{

			consulta = "select crtp.id " + " from ClienteImovel clienteImovel" + " inner join clienteImovel.clienteRelacaoTipo crtp "
							+ " where clienteImovel.imovel.id = :idImovel" + " and clienteImovel.indicadorNomeConta = 1 "
							+ " and clienteImovel.dataFimRelacao is null";

			idRelacaoTipo = (Integer) session.createQuery(consulta).setInteger("idImovel", idImovel).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		return idRelacaoTipo;
	}

	/**
	 * Retorna o cliente com indicador de nome conta
	 * 
	 * @author Eduardo Henrique
	 * @date 25/09/2008
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Cliente pesquisarClienteImovelNomeConta(Integer idImovel) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		String consulta = null;
		Cliente clienteNomeConta = null;

		try{

			consulta = "select clienteImovel.cliente " + " from ClienteImovel clienteImovel" + " where clienteImovel.imovel.id = :idImovel"
							+ " and clienteImovel.indicadorNomeConta = 1 " + " and clienteImovel.dataFimRelacao is null";

			clienteNomeConta = (Cliente) session.createQuery(consulta).setInteger("idImovel", idImovel).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		return clienteNomeConta;
	}

	/**
	 * Pesquisa o rg do cliente do parcelamento a partir do idParcelamento
	 * Autor: Vivianne Sousa
	 * Data: 20/06/2007
	 */
	public Object[] pesquisarDadosClienteDoParcelamentoRelatorioParcelamento(Integer idParcelamento) throws ErroRepositorioException{

		Object[] retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta = null;
		try{
			consulta = "select orgaoExpedidorRg.descricaoAbreviada, unidadeFederacao.sigla, cliente.rg "
							+ "from Parcelamento parcelamento "
							+ "left join parcelamento.cliente cliente " + "left join cliente.orgaoExpedidorRg orgaoExpedidorRg "
							+ "left join cliente.unidadeFederacao unidadeFederacao " + "where parcelamento.id = :idParcelamento";
			retorno = (Object[]) session.createQuery(consulta).setInteger("idParcelamento", idParcelamento).uniqueResult();

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
	 * Retorna o cliente usuário
	 * 
	 * @author Sávio Luiz
	 * @date 04/04/2007
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer retornaIdClienteResponsavelIndicadorEnvioConta(Integer idImovel) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		String consulta = null;
		Integer id = null;

		try{

			consulta = "select cliente.clie_id as idCliente "
							+ " from cliente_imovel clienteImovel"
							+ " inner join cliente cliente on cliente.clie_id = clienteImovel.clie_id"
							+ " inner join imovel imovel on imovel.imov_id = clienteImovel.imov_id"
							+ " where imovel.imov_id ="
							+ idImovel
							+ " and (imovel.icte_id = :indicadorEnvioResponsavel or imovel.icte_id = :indicadorEnvioResponsavelNaoPagavel) "
							+ "and clienteImovel.crtp_id = " + ClienteRelacaoTipo.RESPONSAVEL
							+ " and clienteImovel.clim_dtrelacaofim is null";

			id = (Integer) session.createSQLQuery(consulta).addScalar("idCliente", Hibernate.INTEGER).setInteger(
							"indicadorEnvioResponsavel", ImovelContaEnvio.ENVIAR_CLIENTE_RESPONSAVEL).setInteger(
							"indicadorEnvioResponsavelNaoPagavel", ImovelContaEnvio.NAO_PAGAVEL_IMOVEL_PAGAVEL_RESPONSAVEL)
							.setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		return id;
	}

	/**
	 * Pesquisa o codigo da rota e o sequencia da rota
	 * apartir do codigo do cliente
	 * 
	 * @author Rafael Pinto
	 * @date 19/09/2007
	 * @throws ErroRepositorioException
	 * @return Object[3] onde :
	 *         Object[0]=id localidade
	 *         Object[1]=codigo do setor
	 *         Object[2]=codigo da rota
	 *         Object[2]=sequencial
	 */
	public Object[] pesquisarCodigoSequencialRotaPorUsuario(Integer idCliente) throws ErroRepositorioException{

		Object[] retorno = null;

		String consulta = "";

		// obtém a sessão
		Session session = HibernateUtil.getSession();

		try{

			consulta = "SELECT local.id," + "setor.codigo," + "rota.codigo," + "imov.numeroSequencialRota "
							+ "FROM ClienteImovel clienteImovel " + "LEFT JOIN  clienteImovel.cliente cliente "
							+ "LEFT JOIN clienteImovel.clienteRelacaoTipo clienteRelacao " + "LEFT JOIN clienteImovel.imovel imov "
							+ "LEFT JOIN imov.quadra qu " + "LEFT JOIN imov.setorComercial setor " + "LEFT JOIN imov.localidade local "
							+ "LEFT JOIN qu.rota rota " + "WHERE cliente.id = :idCliente " + "AND clienteRelacao.id = :relacao "
							+ "AND clienteImovel.dataFimRelacao is null";

			retorno = (Object[]) session.createQuery(consulta).setInteger("idCliente", idCliente).setInteger("relacao",
							ClienteRelacaoTipo.USUARIO).setMaxResults(1).uniqueResult();

			// erro no hibernate
		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		// retorna
		return retorno;
	}

	/**
	 * Retorna o nome do cliente
	 * 
	 * @author isilva
	 * @date 13/09/2010
	 * @param idImovel
	 * @param clienteRelacaoTipo
	 * @return
	 * @throws ErroRepositorioException
	 */
	public String retornaNomeCliente(Integer idImovel, Integer clienteRelacaoTipo) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		StringBuffer consulta = new StringBuffer();
		String nomeCliente = null;

		try{

			consulta.append("select cliente.clie_nmcliente as nomeCliente ").append(" from cliente_imovel clienteImovel").append(
							" inner join cliente cliente on cliente.clie_id = clienteImovel.clie_id").append(
							" where clienteImovel.imov_id = ").append(idImovel).append(
							" and clienteImovel.crtp_id = " + clienteRelacaoTipo.intValue()).append(
							" and clienteImovel.clim_dtrelacaofim is null");

			nomeCliente = (String) session.createSQLQuery(consulta.toString()).addScalar("nomeCliente", Hibernate.STRING).setMaxResults(1)
							.uniqueResult();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		return nomeCliente;
	}

	/**
	 * Retorna o cliente usuario apartir do id do imovel
	 * 
	 * @author
	 * @date
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Cliente retornarDadosClienteUsuario(Integer idImovel) throws ErroRepositorioException{

		Cliente cliente = new Cliente();
		ClienteTipo clienteTipo = new ClienteTipo();
		cliente.setClienteTipo(clienteTipo);

		Session session = HibernateUtil.getSession();
		String consulta = null;
		Object[] objetoCliente = null;
		Collection colecaoCliente = null;

		try{

			consulta = "select cliente.clie_id as idCliente," // 0
							+ " cliente.clie_nmcliente as nomeCliente," // 1
							+ " cliente.clie_nncpf as cpf," // 2
							+ " cliente.clie_nncnpj as cnpj," // 3
							+ " cliente.clie_nnrg as rg," // 4
							+ " clienteTipo.cltp_icpessoafisicajuridica as indicadorPessoaFisicaJuridica" // 5
							+ " from cliente_imovel clienteImovel"
							+ " inner join cliente cliente on cliente.clie_id = clienteImovel.clie_id"
							+ " inner join cliente_tipo clienteTipo on clienteTipo.cltp_id = cliente.cltp_id"
							+ " where clienteImovel.imov_id =" + idImovel + " and clienteImovel.crtp_id = "
							+ ClienteRelacaoTipo.USUARIO
							+ " and clienteImovel.clim_dtrelacaofim is null";

			colecaoCliente = session.createSQLQuery(consulta).addScalar("idCliente", Hibernate.INTEGER).addScalar("nomeCliente",
							Hibernate.STRING).addScalar("cpf", Hibernate.STRING).addScalar("cnpj", Hibernate.STRING).addScalar("rg",
							Hibernate.STRING).addScalar("indicadorPessoaFisicaJuridica", Hibernate.SHORT).list();

			objetoCliente = (Object[]) colecaoCliente.iterator().next();

			if(objetoCliente[0] != null){
				cliente.setId((Integer) objetoCliente[0]);
			}
			if(objetoCliente[1] != null){
				cliente.setNome((String) objetoCliente[1]);
			}
			if(objetoCliente[2] != null){
				cliente.setCpf((String) objetoCliente[2]);
			}
			if(objetoCliente[3] != null){
				cliente.setCnpj((String) objetoCliente[3]);
			}
			if(objetoCliente[4] != null){
				cliente.setRg((String) objetoCliente[4]);
			}
			if(objetoCliente[5] != null){
				cliente.getClienteTipo().setIndicadorPessoaFisicaJuridica((Short) objetoCliente[5]);
			}

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		return cliente;
	}

	/**
	 * Retorna o cliente proprietario apartir do id do imovel
	 * 
	 * @author Hebert Falcão
	 * @date 26/05/2011
	 */
	public Object[] retornarDadosClienteProprietario(Integer idImovel) throws ErroRepositorioException{

		Object[] retorno = null;
		StringBuffer consulta = new StringBuffer();
		Session session = HibernateUtil.getSession();

		try{
			consulta.append("select clie.id, ");
			consulta.append("       clie.nome, ");
			consulta.append("       clie.cpf,   ");
			consulta.append("       clie.cnpj,   ");
			consulta.append("       clie.rg,  ");
			// consulta.append("       oerg.descricao,   ");
			consulta.append("       oerg.descricaoAbreviada,   ");
			consulta.append("       unfe.sigla,   ");
			consulta.append("       cltp.indicadorPessoaFisicaJuridica   ");
			consulta.append("from ClienteImovel clim ");
			consulta.append("left join clim.cliente clie ");
			consulta.append("left join clie.orgaoExpedidorRg oerg ");
			consulta.append("left join clie.unidadeFederacao unfe ");
			consulta.append("left join clie.clienteTipo cltp ");
			consulta.append("where clim.imovel = :idImovel ");
			consulta.append("  and clim.clienteRelacaoTipo = :relacao ");
			consulta.append("  and clim.dataFimRelacao is null ");

			retorno = (Object[]) session.createQuery(consulta.toString()).setInteger("idImovel", idImovel).setInteger("relacao",
							ClienteRelacaoTipo.PROPRIETARIO).setMaxResults(1).uniqueResult();

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
	 * Retorna a Coleção de ClienteImovel que será setada no objeto Imovel.
	 * 
	 * @author Ailton Sousa
	 * @date 06/10/2011
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Imovel obterClienteImovelResponsavel(Integer idImovel) throws ErroRepositorioException{

		Imovel retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "select i from Imovel i " + " left join fetch i.clienteImoveis ci " + "left join fetch ci.cliente c "
							+ "left join fetch c.clienteTipo ct " + "left join fetch ct.esferaPoder ep "
							+ "inner join ci.clienteRelacaoTipo crt " + "where "
							+ "crt.id = :clienteResponsavel and ci.dataFimRelacao is null and i.id =:idImovel";

			retorno = (Imovel) session.createQuery(consulta).setInteger("clienteResponsavel", ClienteRelacaoTipo.RESPONSAVEL).setInteger(
							"idImovel", idImovel).setMaxResults(1).uniqueResult();

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
	 * [UC0203] Consultar Débitos
	 * [SB0004] – Apresenta Relacionamento do Cliente com os Imóveis
	 * 
	 * @author Hugo Lima
	 * @date 10/08/2012
	 * @param idCliente
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<ClienteImovelRelacaoHelper> obterDadosImoveisClienteRelacao(Integer idCliente) throws ErroRepositorioException{

		Collection retorno = null;
		StringBuffer consulta = new StringBuffer();
		Session session = HibernateUtil.getSession();

		try{
			consulta.append(" select ");
			consulta.append(" new ");
			consulta.append(ClienteImovelRelacaoHelper.class.getName());
			consulta.append(" ( ");
			consulta.append(" clie.id, ");
			consulta.append(" imov.id, ");
			consulta.append(" clim.dataInicioRelacao, ");
			consulta.append(" clim.dataFimRelacao, ");
			consulta.append(" crtp.id, ");
			consulta.append(" crtp.descricao, ");
			consulta.append(" cifr.id, ");
			consulta.append(" cifr.descricao");
			consulta.append(" ) ");
			consulta.append(" from ClienteImovel clim ");
			consulta.append(" inner join clim.cliente clie ");
			consulta.append(" inner join clim.imovel imov ");
			consulta.append(" left join clim.clienteRelacaoTipo crtp ");
			consulta.append(" left join clim.clienteImovelFimRelacaoMotivo cifr ");
			consulta.append(" where clie.id = :idCliente ");
			consulta.append(" order by clim.dataInicioRelacao desc, clim.dataFimRelacao desc  ");

			retorno = session.createQuery(consulta.toString()).setInteger("idCliente", idCliente).list();

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
	 * Obter o Tipo do Responsável GCS a partir do Tipo do Cliente GSAN
	 * (CLTP_ID da tabela CLIENTE com CLIE_ID=CLIE_ID da tabela CLIENTE_IMOVEL com IMOV_ID=IMOV_ID
	 * da tabela IMOVEL
	 * e CRTP_ID=CRTP_ID da tabela CLIENTE_RELACAO_TIPO com CRTP_DSCLIENTERELACAOTIPO=”RESPONSAVEL”
	 * e CLIM_DTRELACAOFIM com o valor nulo) de acordo com a seguinte correspondência
	 * 
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */

	public Integer retornaTipoClienteImovel(Integer idImovel) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		String consulta = null;
		Integer idRelacaoTipo = null;

		try{

			consulta = "select cltp.id " + " from ClienteImovel clienteImovel" + " inner join clienteImovel.clienteRelacaoTipo crtp "
							+ " inner join clienteImovel.cliente clie " + " inner join clie.clienteTipo cltp "
							+ " where clienteImovel.imovel.id = :idImovel"
							+ " and clienteImovel.clienteRelacaoTipo.id = :idClienteRelacaoTipo "
							+ " and clienteImovel.dataFimRelacao is null";

			idRelacaoTipo = (Integer) session.createQuery(consulta).setInteger("idImovel", idImovel).setInteger("idClienteRelacaoTipo",
							ClienteRelacaoTipo.RESPONSAVEL).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		return idRelacaoTipo;
	}

}
