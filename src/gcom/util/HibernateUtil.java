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

import gcom.interceptor.Interceptador;

import java.net.URL;
import java.util.Iterator;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.cfg.Configuration;
import org.hibernate.mapping.Column;
import org.hibernate.mapping.Component;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Property;
import org.hibernate.mapping.SimpleValue;

/**
 * Classe responsável pela instanciação do Hibernate e serviços específicos da
 * tecnologia
 * 
 * @author rodrigo
 */
public class HibernateUtil {

	private static SessionFactory sessionFactoryComercial;

	private static SessionFactory sessionFactoryGerencial;

	private static Configuration configurationComercial;

	private static Configuration configurationGerencial;

	public static void inicializarSessionFactory(){

		try{
			// -------------------Configuração do servidor Comercial------------------//
			configurationComercial = new Configuration();
			configurationComercial.configure("hibernate-comercial.cfg.xml");
			configurationComercial.setInterceptor(Interceptador.getInstancia());
			sessionFactoryComercial = configurationComercial.buildSessionFactory();

			// -------------------Configuração do servidor Gerencial------------------//
			configurationGerencial = new Configuration();
			configurationGerencial.configure("hibernate-gerencial.cfg.xml");
			configurationGerencial.setProperty("hibernate.connection.datasource", "java:/GsanGerencialDS");
			sessionFactoryGerencial = configurationGerencial.buildSessionFactory();

		}catch(HibernateException ex){
			ex.printStackTrace();
			throw new SistemaException("Hibernate - Erro ao criar a SessionFactory");
		}

	}

	/**
	 * Retorna o valor de session
	 * 
	 * @return O valor de session
	 */
	public static Session getSession(){

		Session retorno = null;

		try{
			retorno = sessionFactoryComercial.openSession();
			retorno.enableFilter("filterSistemaParametroPorEmpresa").setParameter("parmId",
							Integer.valueOf(ConstantesConfig.getIdEmpresa()));
		}catch(HibernateException ex){
			ex.printStackTrace();
			throw new SistemaException("Hibernate - Erro ao criar a Session");
		}

		return retorno;
	}

	/**
	 * Retorna o valor de session
	 * 
	 * @return O valor de session
	 */
	public static StatelessSession getStatelessSession(){

		StatelessSession retorno = null;

		try{
			retorno = sessionFactoryComercial.openStatelessSession();
		}catch(HibernateException ex){
			ex.printStackTrace();
			throw new SistemaException("Hibernate - Erro ao criar a Session");
		}

		return retorno;
	}

	/**
	 * Retorna o valor de session
	 * 
	 * @return O valor de session
	 */
	public static StatelessSession getStatelessSessionGerencial(){

		StatelessSession retorno = null;

		try{
			retorno = sessionFactoryGerencial.openStatelessSession();
		}catch(HibernateException ex){
			ex.printStackTrace();
			throw new SistemaException("Hibernate - Erro ao criar a Session");
		}

		return retorno;
	}

	/**
	 * Retorna o valor de session
	 * 
	 * @return O valor de session
	 */
	public static Session getSessionGerencial(){

		Session retorno = null;

		try{
			retorno = sessionFactoryGerencial.openSession();
		}catch(HibernateException ex){
			ex.printStackTrace();
			throw new SistemaException("Hibernate - Erro ao criar a Session Gerencial");
		}

		return retorno;
	}

	/**
	 * Retorna o valor de session
	 * 
	 * @return O valor de session
	 */
	/*
	 * public static Session getSession(Interceptador interceptador) { Session
	 * retorno = null;
	 * try { //retorno = sessionFactory.openSession(interceptador); retorno =
	 * sessionFactory.openSession(interceptador); } catch (HibernateException
	 * ex) { throw new SistemaException("Hibernate - Erro ao criar a Session"); }
	 * return retorno; }
	 */

	/**
	 * Fecha a session
	 * 
	 * @param session
	 *            Descrição do parâmetro
	 */
	public static void closeSession(Session session){

		if(session != null){
			try{
				// session.clear();
				session.close();
			}catch(HibernateException ex){
				throw new SistemaException("Hibernate - Erro ao fechar a Session");
			}

		}
	}

	/**
	 * Fecha a session
	 * 
	 * @param session
	 *            Descrição do parâmetro
	 */
	public static void closeSession(StatelessSession session){

		if(session != null){
			try{
				session.close();
			}catch(HibernateException ex){
				throw new SistemaException("Hibernate - Erro ao fechar a Session");
			}

		}
	}

	/**
	 * Método que obtém o tamanho da propriedade da classe
	 * 
	 * @param mappedClass
	 *            Nome da classe
	 * @param propertyName
	 *            Nome da propriedade da classe
	 * @return O valor de columnSize
	 */
	public static int getColumnSize(Class mappedClass, String propertyName){

		Configuration cfg = HibernateUtil.getConfig();
		PersistentClass pClass = cfg.getClassMapping(mappedClass.getName());
		Column col = null;
		Property hibProp = null;

		try{
			hibProp = pClass.getProperty(propertyName);

			Iterator it = hibProp.getColumnIterator();

			while(it.hasNext()){
				col = (Column) hibProp.getColumnIterator().next();
				break;
			}

		}catch(MappingException ex){
			throw new SistemaException("Hibernate - Erro no mapeamento");
		}

		return col.getLength();
	}

	/**
	 * Método que obtém o nome da coluna no banco da propriedade passada Caso
	 * nao tenha, retorna null
	 * 
	 * @param mappedClass
	 *            Nome da classe
	 * @param propertyName
	 *            Nome da propriedade da classe
	 * @return nome da coluna
	 */
	public static String getNameColumn(Class mappedClass, String propertyName){

		String retorno = null;
		Configuration cfg = HibernateUtil.getConfig();
		PersistentClass pClass = cfg.getClassMapping(mappedClass.getName());
		Column col = null;
		Property hibProp = null;

		try{
			hibProp = pClass.getProperty(propertyName);

			Iterator it = hibProp.getColumnIterator();

			while(it.hasNext()){
				col = (Column) hibProp.getColumnIterator().next();
				break;
			}

			// retorno = col.getComment();
			// if (retorno == null || "".equals(retorno)) {
			if(col == null){
				retorno = ConstantesDescricaoBanco.get(pClass.getTable().getName() + "." + propertyName);
			}else{
				retorno = ConstantesDescricaoBanco.get(pClass.getTable().getName() + "." + col.getName());
			}
			if(retorno == null && col != null){

				retorno = col.getName();
			}

			if(col == null){
				retorno = null;
			}
			// }

		}catch(MappingException ex){
			try{

				hibProp = pClass.getIdentifierProperty();
				if(hibProp.getName().equalsIgnoreCase(propertyName)){

					Iterator it = hibProp.getColumnIterator();

					while(it.hasNext()){
						col = (Column) hibProp.getColumnIterator().next();
						break;
					}

					// retorno = col.getComment();
					// if (retorno == null || "".equals(retorno)) {
					// retorno = col.getName();
					// }

					retorno = ConstantesDescricaoBanco.get(pClass.getTable().getName() + "." + col.getName());
					if(retorno == null){
						retorno = col.getName();
					}
				}

			}catch(MappingException eex){
				eex.printStackTrace();
			}
		}

		return retorno;
	}

	/**
	 * Método que obtém o nome da tabela da classe passada
	 * 
	 * @param mappedClass
	 *            Nome da classe
	 * @return O String nome da tablea
	 */
	public static String getNameTable(Class mappedClass){

		Configuration cfg = HibernateUtil.getConfig();
		PersistentClass pClass = cfg.getClassMapping(mappedClass.getName());

		String retorno = pClass.getTable().getComment();
		if(retorno == null || "".equals(retorno)){
			retorno = ConstantesDescricaoBanco.get(pClass.getTable().getName());
			if(retorno == null){
				retorno = pClass.getTable().getName();
			}

		}

		return retorno;
	}

	/**
	 * Retorna a que classe está mapeada a tabela passada
	 * 
	 * @param tableName
	 *            caminho da tabela
	 * @return caminho da classe
	 */
	public static String getClassName(String tableName){

		Configuration cfg = HibernateUtil.getConfig();
		if(cfg != null){
			Iterator iter = cfg.getClassMappings();
			while(iter.hasNext()){
				PersistentClass classe = (PersistentClass) iter.next();
				if(classe.getTable().getName().equals(tableName)){
					return classe.getClassName();
				}
			}
		}
		return null;
	}

	public static String getTypeNameProperClassMappings(String className, String nomeAtributo){

		PersistentClass mapping = HibernateUtil.getConfig().getClassMapping(className);
		org.hibernate.mapping.Property property = null;
		try{
			property = mapping.getProperty(nomeAtributo);
		}catch(MappingException mex){
			property = ((Component) mapping.getIdentifier()).getProperty(nomeAtributo);
		}
		return ((SimpleValue) property.getValue()).getTypeName();
	}

	/**
	 * Retorna o valor de config
	 * 
	 * @return O valor de config
	 */
	public static Configuration getConfig(){

		return configurationComercial;
	}

	/**
	 * The main program for the HibernateUtil class
	 * 
	 * @param args
	 *            The command line arguments
	 */
	public static void main(String[] args){

		inicializarSessionFactory();
		Session session = getSession();
		System.out.println(session);

	}

	public static Session getCurrentSession(){

		Session retorno = null;

		try{
			retorno = sessionFactoryComercial.getCurrentSession();
		}catch(HibernateException ex){
			ex.printStackTrace();
			throw new SistemaException("Hibernate - Erro ao criar a Session");
		}

		return retorno;
	}

	/**
	 * @author Saulo Lima
	 * @date 11/05/2012
	 *       Método utilizado para verificar se o objeto está inicializado antes de incializá-lo.
	 *       Caso verdadeiro, irá economizar consulta no BD e melhorar performance.
	 * @param objeto
	 */
	public static void initialize(Object objeto){

		if(!Hibernate.isInitialized(objeto)){
			Hibernate.initialize(objeto);
		}
	}

	/**
	 * Carrega o arquivo de properties do sistema
	 */
	private static URL carregaURLGerencial(){

		final String NOME_ARQUIVO_PROPRIEDADES = "/hibernateGerencial.properties";
		// Properties propriedades = new Properties();

		URL url;

		ClassLoader classLoader = ClassLoader.getSystemClassLoader();

		url = classLoader.getResource(NOME_ARQUIVO_PROPRIEDADES);

		// if system class loader not found try the this class classLoader
		if(url == null){
			url = HibernateUtil.class.getClassLoader().getResource(NOME_ARQUIVO_PROPRIEDADES);
		}

		if(url == null){
			url = HibernateUtil.class.getResource(NOME_ARQUIVO_PROPRIEDADES);
		}

		// propriedades.load(url);

		return url;
	}

}