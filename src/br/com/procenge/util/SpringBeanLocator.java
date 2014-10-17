/**
 * 
 */

package br.com.procenge.util;

/**
 * @author gmatos
 *
 */

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public final class SpringBeanLocator
				implements ApplicationContextAware {

	public static String BEAN_ID_SPRING_BEAN_LOCATOR = "springBeanLocator";

	private static SpringBeanLocator instancia = new SpringBeanLocator();

	private ApplicationContext applicationContext;

	/**
	 * Construtor padrão
	 */
	private SpringBeanLocator() {

		super();
	}

	/**
	 * @return the applicationContext
	 */
	public ApplicationContext getApplicationContext(){

		return applicationContext;
	}

	/**
	 * Obter a instância do service locator
	 * 
	 * @return
	 */
	public static SpringBeanLocator getInstancia(){

		return instancia;
	}

	/**
	 * Obtêm o bean do contexto do spring
	 * 
	 * @param nome
	 *            O ID configurado no contexo do spring
	 * @return O Objeto
	 */
	public Object getBeanPorID(String nome){

		return applicationContext.getBean(nome);
	}

	/**
	 * Seta o contexto da aplicação
	 */
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException{

		this.applicationContext = applicationContext;
	}
}
