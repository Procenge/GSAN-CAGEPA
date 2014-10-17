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

package gcom.util;

import gcom.arrecadacao.ControladorArrecadacaoLocal;
import gcom.arrecadacao.ControladorArrecadacaoLocalHome;
import gcom.atendimentopublico.ControladorAtendimentoPublicoLocal;
import gcom.atendimentopublico.ControladorAtendimentoPublicoLocalHome;
import gcom.atendimentopublico.ligacaoagua.ControladorLigacaoAguaLocal;
import gcom.atendimentopublico.ligacaoagua.ControladorLigacaoAguaLocalHome;
import gcom.atendimentopublico.ligacaoesgoto.ControladorLigacaoEsgotoLocal;
import gcom.atendimentopublico.ligacaoesgoto.ControladorLigacaoEsgotoLocalHome;
import gcom.atendimentopublico.ordemservico.ControladorOrdemServicoLocal;
import gcom.atendimentopublico.ordemservico.ControladorOrdemServicoLocalHome;
import gcom.atendimentopublico.registroatendimento.ControladorRegistroAtendimentoLocal;
import gcom.atendimentopublico.registroatendimento.ControladorRegistroAtendimentoLocalHome;
import gcom.batch.ControladorBatchLocal;
import gcom.batch.ControladorBatchLocalHome;
import gcom.cadastro.ControladorCadastroLocal;
import gcom.cadastro.ControladorCadastroLocalHome;
import gcom.cadastro.cliente.ControladorClienteLocal;
import gcom.cadastro.cliente.ControladorClienteLocalHome;
import gcom.cadastro.endereco.ControladorEnderecoLocal;
import gcom.cadastro.endereco.ControladorEnderecoLocalHome;
import gcom.cadastro.geografico.ControladorGeograficoLocal;
import gcom.cadastro.geografico.ControladorGeograficoLocalHome;
import gcom.cadastro.imovel.ControladorImovelLocal;
import gcom.cadastro.imovel.ControladorImovelLocalHome;
import gcom.cadastro.localidade.ControladorLocalidadeLocal;
import gcom.cadastro.localidade.ControladorLocalidadeLocalHome;
import gcom.cadastro.tarifasocial.ControladorTarifaSocialLocal;
import gcom.cadastro.tarifasocial.ControladorTarifaSocialLocalHome;
import gcom.cadastro.unidade.ControladorUnidadeLocal;
import gcom.cadastro.unidade.ControladorUnidadeLocalHome;
import gcom.cobranca.*;
import gcom.cobranca.parcelamento.ControladorParcelamentoLocal;
import gcom.cobranca.parcelamento.ControladorParcelamentoLocalHome;
import gcom.contabil.ControladorContabilLocal;
import gcom.contabil.ControladorContabilLocalHome;
import gcom.faturamento.ControladorFaturamentoLocal;
import gcom.faturamento.ControladorFaturamentoLocalHome;
import gcom.faturamento.histograma.ControladorHistogramaLocal;
import gcom.faturamento.histograma.ControladorHistogramaLocalHome;
import gcom.financeiro.ControladorFinanceiroLocal;
import gcom.financeiro.ControladorFinanceiroLocalHome;
import gcom.gerencial.ControladorGerencialLocal;
import gcom.gerencial.ControladorGerencialLocalHome;
import gcom.gerencial.arrecadacao.ControladorGerencialArrecadacaoLocal;
import gcom.gerencial.arrecadacao.ControladorGerencialArrecadacaoLocalHome;
import gcom.gerencial.cadastro.ControladorGerencialCadastroLocal;
import gcom.gerencial.cadastro.ControladorGerencialCadastroLocalHome;
import gcom.gerencial.cobranca.ControladorGerencialCobrancaLocal;
import gcom.gerencial.cobranca.ControladorGerencialCobrancaLocalHome;
import gcom.gerencial.faturamento.ControladorGerencialFaturamentoLocal;
import gcom.gerencial.faturamento.ControladorGerencialFaturamentoLocalHome;
import gcom.gerencial.micromedicao.ControladorGerencialMicromedicaoLocal;
import gcom.gerencial.micromedicao.ControladorGerencialMicromedicaoLocalHome;
import gcom.integracao.acquagis.ControladorIntegracaoAcquaGisLocal;
import gcom.integracao.acquagis.ControladorIntegracaoAcquaGisLocalHome;
import gcom.integracao.cagepa.faturamento.ControladorIntegracaoCagepaFaturamentoLocal;
import gcom.integracao.cagepa.faturamento.ControladorIntegracaoCagepaFaturamentoLocalHome;
import gcom.integracao.piramide.ControladorIntegracaoPiramideLocal;
import gcom.integracao.piramide.ControladorIntegracaoPiramideLocalHome;
import gcom.micromedicao.ControladorMicromedicaoLocal;
import gcom.micromedicao.ControladorMicromedicaoLocalHome;
import gcom.operacional.ControladorOperacionalLocal;
import gcom.operacional.ControladorOperacionalLocalHome;
import gcom.relatorio.faturamento.ControladorRelatorioFaturamentoLocal;
import gcom.relatorio.faturamento.ControladorRelatorioFaturamentoLocalHome;
import gcom.seguranca.ControladorPermissaoEspecialLocal;
import gcom.seguranca.ControladorPermissaoEspecialLocalHome;
import gcom.seguranca.acesso.ControladorAcessoLocal;
import gcom.seguranca.acesso.ControladorAcessoLocalHome;
import gcom.seguranca.acesso.usuario.ControladorUsuarioLocal;
import gcom.seguranca.acesso.usuario.ControladorUsuarioLocalHome;
import gcom.seguranca.transacao.ControladorTransacaoLocal;
import gcom.seguranca.transacao.ControladorTransacaoLocalHome;
import gcom.spcserasa.ControladorSpcSerasaLocal;
import gcom.spcserasa.ControladorSpcSerasaLocalHome;
import gcom.util.tabelaauxiliar.ControladorTabelaAuxiliarLocal;
import gcom.util.tabelaauxiliar.ControladorTabelaAuxiliarLocalHome;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;
import javax.transaction.UserTransaction;

/**
 * Efetua a localização, instanciamento e cache de serviços
 * 
 * @author Administrador
 */

public class ServiceLocator {

	private static ServiceLocator instancia = new ServiceLocator();;

	private Context contexto = null;

	private Map mapaHomes = Collections.synchronizedMap(new HashMap());

	private ConnectionFactory connectionFactoryJms = null;

	private UserTransaction userTransaction = null;

	/**
	 * Retorna a única instância do ServiceLocator
	 * 
	 * @return A instância do ServiceLocator
	 * @exception ServiceLocatorException
	 *                Exceção levantada caso ocorra algum erro de localização ou
	 *                instanciamento
	 */
	public static ServiceLocator getInstancia(){

		return instancia;
	}

	/**
	 * Construtor da classe ServiceLocator
	 */
	private ServiceLocator() {

		// try{
		//
		// Hashtable env = new Hashtable(4);
		//
		// env.put(javax.naming.Context.INITIAL_CONTEXT_FACTORY,
		// "org.jnp.interfaces.NamingContextFactory");
		//
		// env.put("java.naming.factory.url.pkgs", "org.jboss.naming:org.jnp.interfaces");
		//
		// env.put(javax.naming.Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
		//
		// env.put(javax.naming.Context.PROVIDER_URL, "localhost:1099");
		//
		// contexto = new InitialContext(env);
		//
		// connectionFactoryJms = (ConnectionFactory) getContexto().lookup("java:JmsXA");
		//
		// userTransaction = (UserTransaction) getContexto().lookup("java:comp/UserTransaction");
		//
		// mapaHomes = Collections.synchronizedMap(new HashMap());
		//
		// }catch(Exception e){
		//
		// throw new SistemaException(e);
		// }
	}

	public Context getContexto(){

		try{
			if(contexto == null) contexto = new InitialContext();
		}catch(NamingException e){
			throw new SistemaException(e);
		}
		return contexto;
	}

	public ConnectionFactory getConnectionFactoryJms(){

		try{
			if(connectionFactoryJms == null) connectionFactoryJms = (ConnectionFactory) getContexto().lookup("java:JmsXA");
		}catch(NamingException e){
			throw new SistemaException(e);
		}
		return connectionFactoryJms;
	}

	public UserTransaction getUserTransaction(){

		try{
			if(userTransaction == null) userTransaction = (UserTransaction) getContexto().lookup("java:comp/UserTransaction");
		}catch(NamingException e){
			throw new SistemaException(e);
		}
		return userTransaction;
	}

	/**
	 * Retorna a interface Home de um Entreprise Java Bean
	 * 
	 * @param jndiName
	 *            O jndiName do EJB
	 * @param classe
	 *            O nome do .class da interface home
	 * @return A interface Home de um Entreprise Java Bean
	 * @exception ServiceLocatorException
	 *                Exceção levantada caso ocorra algum erro de localização ou
	 *                instanciamento
	 */

	public Object getHome(String jndiName, Class classe) throws ServiceLocatorException{

		Object home = mapaHomes.get(jndiName);

		if(home == null){

			try{

				Object referencia = getContexto().lookup(jndiName);

				home = PortableRemoteObject.narrow(referencia, classe);

				mapaHomes.put(jndiName, home);

			}catch(NamingException e){

				throw new ServiceLocatorException(e, e.getMessage());
			}

		}

		return home;
	}

	/**
	 * will get the ejb Local home factory. clients need to cast to the type of
	 * EJBHome they desire
	 * 
	 * @param jndiHomeName
	 *            Descrição do parâmetro
	 * @return the Local EJB Home corresponding to the homeName
	 * @exception ServiceLocatorException
	 *                Descrição da exceção
	 */
	public EJBLocalHome getLocalHome(String jndiHomeName) throws ServiceLocatorException{

		EJBLocalHome home = null;

		try{

			if(mapaHomes.containsKey(jndiHomeName)){
				home = (EJBLocalHome) mapaHomes.get(jndiHomeName);
			}else{
				home = (EJBLocalHome) getContexto().lookup(jndiHomeName);
				mapaHomes.put(jndiHomeName, home);
			}

		}catch(NamingException e){

			throw new ServiceLocatorException(e, e.getMessage());
		}

		return home;

	}

	/**
	 * @deprecated
	 */
	public EJBLocalHome getLocalHomePorEmpresa(String jndiHomeName) throws ServiceLocatorException{

		return getLocalHome(jndiHomeName);

		// empresa =
		// Fachada.getInstancia().pesquisarParametrosDoSistema().getNomeAbreviadoEmpresa();
		//
		// if(empresa == null){
		// return (EJBLocalHome) getLocalHome(jndiHomeName.replace("GCOM", ""));
		// }else{
		// try{
		// return (EJBLocalHome) getLocalHome(jndiHomeName.replace("GCOM", empresa));
		// }catch(ServiceLocatorException e){
		// return (EJBLocalHome) getLocalHome(jndiHomeName.replace("GCOM", ""));
		// }
		// }
	}

	/**
	 * Retorna a instância do serviço de componente solicitado.
	 * 
	 * @param nome
	 *            Nome do serviço de componente necessário.
	 * @return Object Uma instância do serviço de componente solicitado
	 * @exception ServiceLocatorException
	 *                Exceção levantada caso ocorra algum erro de localização ou
	 *                instanciamento.
	 */

	public Object getComponente(String nome) throws ServiceLocatorException{

		return this.carregaClasse(nome);
	}

	/**
	 * Realiza a carga e instanciamento de uma classe
	 * 
	 * @param nome
	 *            O nome da classe a ser carregada
	 * @return Uma instância da classe solicitada
	 * @exception ServiceLocatorException
	 *                Exceção levantada caso ocorra algum erro de localização ou
	 *                instanciamento
	 */

	private Object carregaClasse(String nome) throws ServiceLocatorException{

		Object classe = null;
		Class classAuxiliar = null;
		Method metodo = null;

		try{
			classAuxiliar = Class.forName(nome);
			metodo = classAuxiliar.getMethod("getInstancia", (Class[]) null);

		}catch(ClassNotFoundException e){
			e.printStackTrace();
			throw new ServiceLocatorException(e, "Classe não encontrada");
		}catch(NoSuchMethodException e){

			try{
				metodo = classAuxiliar.getMethod("getInstance", (Class[]) null);

			}catch(NoSuchMethodException ex){
				ex.printStackTrace();
				throw new ServiceLocatorException(ex);
			}

		}catch(Throwable e){
			e.printStackTrace();
		}

		try{
			classe = metodo.invoke(null, (Object[]) null);

		}catch(IllegalAccessException e){
			e.printStackTrace();
			throw new ServiceLocatorException(e);
		}catch(InvocationTargetException e){
			e.printStackTrace();
			throw new ServiceLocatorException(e);
		}

		return classe;
	}

	public void enviarMensagemJms(int metodo, String queueMDB, Object[] parametros) throws ServiceLocatorException{

		Connection connection = null;
		Session session = null;

		try{
			// Direciona a fila para o controlador
			Queue destination = (Queue) getContexto().lookup(queueMDB);

			connection = getConnectionFactoryJms().createConnection();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			MessageProducer messageProducer = session.createProducer(destination);
			ObjectMessage message = session.createObjectMessage();

			// Monta a mensagem
			message.setIntProperty("nomeMetodo", metodo);

			// Passa os parametros

			message.setObject(parametros);

			// Manda a mensagem
			messageProducer.send(message);

		}catch(NamingException e){
			throw new ServiceLocatorException(e, e.getMessage());
		}catch(JMSException e){
			throw new ServiceLocatorException(e, e.getMessage());
		}finally{
			try{

				session.close();
				connection.close();

			}catch(JMSException e){
				throw new ServiceLocatorException(e, e.getMessage());
			}

		}

	}

	public void enviarMensagemJms(String queueMDB, Object[] parametros) throws ServiceLocatorException{

		Connection connection = null;
		Session session = null;

		try{
			// Direciona a fila para o controlador
			Queue destination = (Queue) getContexto().lookup(queueMDB);

			connection = getConnectionFactoryJms().createConnection();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			MessageProducer messageProducer = session.createProducer(destination);
			ObjectMessage message = session.createObjectMessage();

			// Passa os parametros
			message.setObject(parametros);

			// Manda a mensagem
			messageProducer.send(message);

		}catch(NamingException e){
			throw new ServiceLocatorException(e, e.getMessage());
		}catch(JMSException e){
			throw new ServiceLocatorException(e, e.getMessage());
		}finally{
			try{
				if(session != null){
					session.close();
				}
				if(connection != null){
					connection.close();
				}

			}catch(JMSException e){
				throw new ServiceLocatorException(e, e.getMessage());
			}

		}

	}

	public ControladorAcessoLocal getControladorAcesso(){

		ControladorAcessoLocalHome localHome = null;
		ControladorAcessoLocal local = null;

		try{

			localHome = (ControladorAcessoLocalHome) getLocalHome(ConstantesJNDI.CONTROLADOR_ACESSO_SEJB);

			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}
	}

	public ControladorCadastroLocal getControladorCadastro(){

		ControladorCadastroLocalHome localHome = null;
		ControladorCadastroLocal local = null;

		try{

			localHome = (ControladorCadastroLocalHome) getLocalHome(ConstantesJNDI.CONTROLADOR_CADASTRO_SEJB);

			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}
	}

	public ControladorUtilLocal getControladorUtil(){

		ControladorUtilLocalHome localHome = null;
		ControladorUtilLocal local = null;

		try{

			localHome = (ControladorUtilLocalHome) getLocalHome(ConstantesJNDI.CONTROLADOR_UTIL_SEJB);

			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}

	}

	public ControladorEnderecoLocal getControladorEndereco(){

		ControladorEnderecoLocalHome localHome = null;
		ControladorEnderecoLocal local = null;

		try{

			localHome = (ControladorEnderecoLocalHome) getLocalHome(ConstantesJNDI.CONTROLADOR_ENDERECO_SEJB);

			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}
	}

	public ControladorPermissaoEspecialLocal getControladorPermissaoEspecial(){

		ControladorPermissaoEspecialLocalHome localHome = null;
		ControladorPermissaoEspecialLocal local = null;

		try{

			localHome = (ControladorPermissaoEspecialLocalHome) getLocalHome(ConstantesJNDI.CONTROLADOR_PERMISSAO_ESPECIAL_SEJB);

			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}
	}

	public ControladorArrecadacaoLocal getControladorArrecadacao(){

		ControladorArrecadacaoLocalHome localHome = null;
		ControladorArrecadacaoLocal local = null;

		try{

			localHome = (ControladorArrecadacaoLocalHome) getLocalHomePorEmpresa(ConstantesJNDI.CONTROLADOR_ARRECADACAO_SEJB);

			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}
	}

	public ControladorMicromedicaoLocal getControladorMicromedicao(){

		ControladorMicromedicaoLocalHome localHome = null;
		ControladorMicromedicaoLocal local = null;

		try{

			localHome = (ControladorMicromedicaoLocalHome) getLocalHomePorEmpresa(ConstantesJNDI.CONTROLADOR_MICROMEDICAO_SEJB);

			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}
	}

	public ControladorOrdemServicoLocal getControladorOrdemServico(){

		ControladorOrdemServicoLocalHome localHome = null;
		ControladorOrdemServicoLocal local = null;

		try{

			localHome = (ControladorOrdemServicoLocalHome) getLocalHome(ConstantesJNDI.CONTROLADOR_ORDEM_SERVICO_SEJB);

			local = localHome.create();
			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}
	}

	public ControladorImovelLocal getControladorImovel(){

		ControladorImovelLocalHome localHome = null;
		ControladorImovelLocal local = null;

		try{

			localHome = (ControladorImovelLocalHome) getLocalHome(ConstantesJNDI.CONTROLADOR_IMOVEL_SEJB);

			local = localHome.create();
			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}
	}

	public ControladorTransacaoLocal getControladorTransacao(){

		ControladorTransacaoLocalHome localHome = null;
		ControladorTransacaoLocal local = null;

		try{

			localHome = (ControladorTransacaoLocalHome) getLocalHome(ConstantesJNDI.CONTROLADOR_TRANSACAO_SEJB);
			local = localHome.create();
			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}
	}

	public ControladorFinanceiroLocal getControladorFinanceiro(){

		ControladorFinanceiroLocalHome localHome = null;
		ControladorFinanceiroLocal local = null;

		try{

			localHome = (ControladorFinanceiroLocalHome) getLocalHomePorEmpresa(ConstantesJNDI.CONTROLADOR_FINANCEIRO_SEJB);

			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}
	}

	public ControladorLigacaoEsgotoLocal getControladorLigacaoEsgoto(){

		ControladorLigacaoEsgotoLocalHome localHome = null;
		ControladorLigacaoEsgotoLocal local = null;

		try{

			localHome = (ControladorLigacaoEsgotoLocalHome) getLocalHome(ConstantesJNDI.CONTROLADOR_LIGACAO_ESGOTO_SEJB);
			local = localHome.create();
			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}
	}

	public ControladorLigacaoAguaLocal getControladorLigacaoAgua(){

		ControladorLigacaoAguaLocalHome localHome = null;
		ControladorLigacaoAguaLocal local = null;

		try{

			localHome = (ControladorLigacaoAguaLocalHome) getLocalHome(ConstantesJNDI.CONTROLADOR_LIGACAO_AGUA_SEJB);
			local = localHome.create();
			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}
	}

	public ControladorBatchLocal getControladorBatch(){

		ControladorBatchLocalHome localHome = null;
		ControladorBatchLocal local = null;

		try{

			localHome = (ControladorBatchLocalHome) getLocalHome(ConstantesJNDI.CONTROLADOR_BATCH_SEJB);
			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}

	}

	public ControladorUnidadeLocal getControladorUnidade(){

		ControladorUnidadeLocalHome localHome = null;
		ControladorUnidadeLocal local = null;

		try{

			localHome = (ControladorUnidadeLocalHome) getLocalHome(ConstantesJNDI.CONTROLADOR_UNIDADE_SEJB);

			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}
	}

	public ControladorRegistroAtendimentoLocal getControladorRegistroAtendimento(){

		ControladorRegistroAtendimentoLocalHome localHome = null;
		ControladorRegistroAtendimentoLocal local = null;

		try{

			localHome = (ControladorRegistroAtendimentoLocalHome) getLocalHome(ConstantesJNDI.CONTROLADOR_REGISTRO_ATENDIMENTO_SEJB);

			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}
	}

	public ControladorAtendimentoPublicoLocal getControladorAtendimentoPublico(){

		ControladorAtendimentoPublicoLocalHome localHome = null;
		ControladorAtendimentoPublicoLocal local = null;

		try{

			localHome = (ControladorAtendimentoPublicoLocalHome) getLocalHome(ConstantesJNDI.CONTROLADOR_ATENDIMENTO_PUBLICO_SEJB);

			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}
	}

	public ControladorCobrancaLocal getControladorCobranca(){

		ControladorCobrancaLocalHome localHome = null;
		ControladorCobrancaLocal local = null;

		try{

			localHome = (ControladorCobrancaLocalHome) getLocalHomePorEmpresa(ConstantesJNDI.CONTROLADOR_COBRANCA_SEJB);

			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}
	}

	public ControladorParcelamentoLocal getControladorParcelamento(){

		ControladorParcelamentoLocalHome localHome = null;
		ControladorParcelamentoLocal local = null;

		try{

			localHome = (ControladorParcelamentoLocalHome) getLocalHome(ConstantesJNDI.CONTROLADOR_PARCELAMENTO_SEJB);

			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}
	}

	public ControladorCobrancaOrdemCorteLocal getControladorCobrancaOrdemCorte(){

		ControladorCobrancaOrdemCorteLocalHome localHome = null;
		ControladorCobrancaOrdemCorteLocal local = null;

		try{

			localHome = (ControladorCobrancaOrdemCorteLocalHome) getLocalHome(ConstantesJNDI.CONTROLADOR_COBRANCA_ORDEM_CORTE_SEJB);

			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}
	}

	public ControladorCobrancaAvisoDebitoLocal getControladorCobrancaAvisoDebito(){

		ControladorCobrancaAvisoDebitoLocalHome localHome = null;
		ControladorCobrancaAvisoDebitoLocal local = null;

		try{

			localHome = (ControladorCobrancaAvisoDebitoLocalHome) getLocalHome(ConstantesJNDI.CONTROLADOR_COBRANCA_AVISO_DEBITO_SEJB);

			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}
	}

	public ControladorLocalidadeLocal getControladorLocalidade(){

		ControladorLocalidadeLocalHome localHome = null;
		ControladorLocalidadeLocal local = null;

		try{

			localHome = (ControladorLocalidadeLocalHome) getLocalHome(ConstantesJNDI.CONTROLADOR_LOCALIDADE_SEJB);

			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}
	}

	public ControladorSpcSerasaLocal getControladorSpcSerasa(){

		ControladorSpcSerasaLocalHome localHome = null;
		ControladorSpcSerasaLocal local = null;

		try{

			localHome = (ControladorSpcSerasaLocalHome) getLocalHome(ConstantesJNDI.CONTROLADOR_SPC_SERASA_SEJB);

			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}
	}

	public ControladorGerencialFaturamentoLocal getControladorGerencialFaturamento(){

		ControladorGerencialFaturamentoLocalHome localHome = null;
		ControladorGerencialFaturamentoLocal local = null;

		try{

			localHome = (ControladorGerencialFaturamentoLocalHome) getLocalHome(ConstantesJNDI.CONTROLADOR_GERENCIAL_FATURAMENTO_SEJB);

			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}

	}

	public ControladorGerencialArrecadacaoLocal getControladorGerencialArrecadacao(){

		ControladorGerencialArrecadacaoLocalHome localHome = null;
		ControladorGerencialArrecadacaoLocal local = null;

		try{

			localHome = (ControladorGerencialArrecadacaoLocalHome) getLocalHome(ConstantesJNDI.CONTROLADOR_GERENCIAL_ARRECADACAO_SEJB);
			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}

	}

	public ControladorGerencialCadastroLocal getControladorGerencialCadastro(){

		ControladorGerencialCadastroLocalHome localHome = null;
		ControladorGerencialCadastroLocal local = null;

		try{

			localHome = (ControladorGerencialCadastroLocalHome) getLocalHome(ConstantesJNDI.CONTROLADOR_GERENCIAL_CADASTRO_SEJB);

			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}

	}

	public ControladorGerencialMicromedicaoLocal getControladorGerencialMicromedicao(){

		ControladorGerencialMicromedicaoLocalHome localHome = null;
		ControladorGerencialMicromedicaoLocal local = null;

		try{

			localHome = (ControladorGerencialMicromedicaoLocalHome) getLocalHome(ConstantesJNDI.CONTROLADOR_GERENCIAL_MICROMEDICAO_SEJB);

			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}

	}

	public ControladorGerencialCobrancaLocal getControladorGerencialCobranca(){

		ControladorGerencialCobrancaLocalHome localHome = null;
		ControladorGerencialCobrancaLocal local = null;

		try{

			localHome = (ControladorGerencialCobrancaLocalHome) getLocalHome(ConstantesJNDI.CONTROLADOR_GERENCIAL_COBRANCA_SEJB);

			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}

	}

	public ControladorTabelaAuxiliarLocal getControladorTabelaAuxiliar(){

		ControladorTabelaAuxiliarLocalHome localHome = null;
		ControladorTabelaAuxiliarLocal local = null;

		try{

			localHome = (ControladorTabelaAuxiliarLocalHome) getLocalHome(ConstantesJNDI.CONTROLADOR_TABELA_AUXILIAR_SEJB);

			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}

	}

	public ControladorTarifaSocialLocal getControladorTarifaSocial(){

		ControladorTarifaSocialLocalHome localHome = null;
		ControladorTarifaSocialLocal local = null;

		try{

			localHome = (ControladorTarifaSocialLocalHome) getLocalHome(ConstantesJNDI.CONTROLADOR_TARIFA_SOCIAL_SEJB);

			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}

	}

	public ControladorGeograficoLocal getControladorGeografico(){

		ControladorGeograficoLocalHome localHome = null;
		ControladorGeograficoLocal local = null;

		try{

			localHome = (ControladorGeograficoLocalHome) getLocalHome(ConstantesJNDI.CONTROLADOR_GEOGRAFICO_SEJB);

			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}
	}

	public ControladorRelatorioFaturamentoLocal getControladorRelatorioFaturamento(){

		ControladorRelatorioFaturamentoLocalHome localHome = null;
		ControladorRelatorioFaturamentoLocal local = null;

		try{

			localHome = (ControladorRelatorioFaturamentoLocalHome) getLocalHome(ConstantesJNDI.CONTROLADOR_RELATORIO_FATURAMENTO_SEJB);

			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}
	}

	public ControladorHistogramaLocal getControladorHistograma(){

		ControladorHistogramaLocalHome localHome = null;
		ControladorHistogramaLocal local = null;

		try{

			localHome = (ControladorHistogramaLocalHome) getLocalHomePorEmpresa(ConstantesJNDI.CONTROLADOR_HISTOGRAMA_SEJB);

			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}
	}

	public ControladorGerencialLocal getControladorGerencial(){

		ControladorGerencialLocalHome localHome = null;
		ControladorGerencialLocal local = null;

		try{

			localHome = (ControladorGerencialLocalHome) getLocalHome(ConstantesJNDI.CONTROLADOR_GERENCIAL_SEJB);

			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}

	}

	public ControladorOperacionalLocal getControladorOperacional(){

		ControladorOperacionalLocalHome localHome = null;
		ControladorOperacionalLocal local = null;

		try{

			localHome = (ControladorOperacionalLocalHome) getLocalHome(ConstantesJNDI.CONTROLADOR_OPERACIONAL_SEJB);

			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}

	}

	public ControladorClienteLocal getControladorCliente(){

		ControladorClienteLocalHome localHome = null;
		ControladorClienteLocal local = null;

		try{

			localHome = (ControladorClienteLocalHome) getLocalHome(ConstantesJNDI.CONTROLADOR_CLIENTE_SEJB);

			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}

	}

	public ControladorContabilLocal getControladorContabil(){

		ControladorContabilLocalHome localHome = null;
		ControladorContabilLocal local = null;

		try{

			localHome = (ControladorContabilLocalHome) getLocalHome(ConstantesJNDI.CONTROLADOR_CONTABIL_SEJB);

			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}

	}

	public ControladorFaturamentoLocal getControladorFaturamento(){

		ControladorFaturamentoLocalHome localHome = null;
		ControladorFaturamentoLocal local = null;

		try{
			localHome = (ControladorFaturamentoLocalHome) getLocalHomePorEmpresa(ConstantesJNDI.CONTROLADOR_FATURAMENTO_SEJB);

			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}
	}

	public ControladorUsuarioLocal getControladorUsuario(){

		ControladorUsuarioLocalHome localHome = null;
		ControladorUsuarioLocal local = null;

		try{
			localHome = (ControladorUsuarioLocalHome) getLocalHome(ConstantesJNDI.CONTROLADOR_USUARIO_SEJB);

			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}

	}

	public ControladorIntegracaoPiramideLocal getControladorIntegracao(){

		ControladorIntegracaoPiramideLocalHome localHome = null;
		ControladorIntegracaoPiramideLocal local = null;

		try{

			localHome = (ControladorIntegracaoPiramideLocalHome) getLocalHome(ConstantesJNDI.CONTROLADOR_INTEGRACAO_PIRAMIDE_SEJB);

			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}
	}

	public ControladorIntegracaoCagepaFaturamentoLocal getControladorIntegracaoCagepaFaturamento(){

		ControladorIntegracaoCagepaFaturamentoLocalHome localHome = null;
		ControladorIntegracaoCagepaFaturamentoLocal local = null;

		try{

			localHome = (ControladorIntegracaoCagepaFaturamentoLocalHome) getLocalHomePorEmpresa(ConstantesJNDI.CONTROLADOR_INTEGRACAO_CAGEPA_FATURAMENTO_SEJB);

			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}
	}

	/**
	 * Método getControladorIntegracaoPiramide
	 * 
	 * @return
	 * @author Marlos Ribeiro
	 * @since 14/03/2013
	 */
	public ControladorIntegracaoPiramideLocal getControladorIntegracaoPiramide(){

		ControladorIntegracaoPiramideLocalHome localHome = null;
		ControladorIntegracaoPiramideLocal local = null;

		// pega a instância do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorIntegracaoPiramideLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_INTEGRACAO_PIRAMIDE_SEJB);

			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}
	}

	/**
	 * Método getControladorIntegracaoPiramide
	 * 
	 * @return
	 * @author Josenildo Neves
	 * @since 04/07/2013
	 */
	public ControladorIntegracaoAcquaGisLocal getControladorIntegracaoAcquaGis(){

		ControladorIntegracaoAcquaGisLocalHome localHome = null;
		ControladorIntegracaoAcquaGisLocal local = null;

		// pega a instância do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorIntegracaoAcquaGisLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_INTEGRACAO_ACQUAGIS_SEJB);

			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}
	}

}
