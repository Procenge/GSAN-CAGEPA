
package gcom.util.parametrizacao.batch;

import gcom.batch.ControladorBatchLocal;
import gcom.batch.ControladorBatchLocalHome;
import gcom.batch.IRepositorioBatch;
import gcom.batch.RepositorioBatchHBM;
import gcom.cadastro.ControladorCadastroLocal;
import gcom.cadastro.ControladorCadastroLocalHome;
import gcom.micromedicao.ControladorMicromedicaoLocal;
import gcom.micromedicao.ControladorMicromedicaoLocalHome;
import gcom.seguranca.acesso.ControladorAcessoLocal;
import gcom.seguranca.acesso.ControladorAcessoLocalHome;
import gcom.util.ConstantesJNDI;
import gcom.util.ControladorUtilLocal;
import gcom.util.ControladorUtilLocalHome;
import gcom.util.ServiceLocator;
import gcom.util.ServiceLocatorException;
import gcom.util.SistemaException;
import gcom.util.parametrizacao.ExecutorParametro;

import javax.ejb.CreateException;

/**
 * @author Hugo Lima
 * @date 01/03/2012
 */
public class ExecutorParametrosBatch
				implements ExecutorParametro {

	private static final ExecutorParametrosBatch instancia = new ExecutorParametrosBatch();

	protected IRepositorioBatch repositorioBatch;

	public static ExecutorParametrosBatch getInstancia(){

		return instancia;
	}

	private ExecutorParametrosBatch() {

	}

	public void ejbCreate() throws CreateException{

		repositorioBatch = RepositorioBatchHBM.getInstancia();
	}

	/**
	 * Retorna a instância de controladorMicromedicao
	 * 
	 * @return O valor de controladorMicromedicao
	 */
	protected ControladorMicromedicaoLocal getControladorMicromedicao(){

		ControladorMicromedicaoLocalHome localHome = null;
		ControladorMicromedicaoLocal local = null;
		ServiceLocator locator = null;

		try{

			locator = ServiceLocator.getInstancia();
			localHome = (ControladorMicromedicaoLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_MICROMEDICAO_SEJB);
			local = localHome.create();

			return local;
		}catch(CreateException e){

			throw new SistemaException(e);
		}catch(ServiceLocatorException e){

			throw new SistemaException(e);
		}
	}

	/**
	 * Retorna a instância de controladorUtil
	 * 
	 * @return O valor de controladorUtil
	 */
	protected ControladorUtilLocal getControladorUtil(){

		ControladorUtilLocalHome localHome = null;
		ControladorUtilLocal local = null;
		ServiceLocator locator = null;

		try{

			locator = ServiceLocator.getInstancia();
			localHome = (ControladorUtilLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_UTIL_SEJB);
			local = localHome.create();

			return local;
		}catch(CreateException e){

			throw new SistemaException(e);
		}catch(ServiceLocatorException e){

			throw new SistemaException(e);
		}

	}

	/**
	 * Retorna a instância de controladorCadastro
	 * 
	 * @return O valor de controladorCadastro
	 */
	protected ControladorCadastroLocal getControladorCadastro(){

		ControladorCadastroLocalHome localHome = null;
		ControladorCadastroLocal local = null;
		ServiceLocator locator = null;

		try{

			locator = ServiceLocator.getInstancia();
			localHome = (ControladorCadastroLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_CADASTRO_SEJB);
			local = localHome.create();

			return local;
		}catch(CreateException e){

			throw new SistemaException(e);
		}catch(ServiceLocatorException e){

			throw new SistemaException(e);
		}
	}

	protected ControladorBatchLocal getControladorBatch(){

		ControladorBatchLocalHome localHome = null;
		ControladorBatchLocal local = null;

		// pega a instância do ServiceLocator.
		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();
			localHome = (ControladorBatchLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_BATCH_SEJB);
			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}

	}

	protected ControladorAcessoLocal getControladorAcesso(){

		ControladorAcessoLocalHome localHome = null;
		ControladorAcessoLocal local = null;

		// pega a instância do ServiceLocator.
		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();
			localHome = (ControladorAcessoLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_ACESSO_SEJB);
			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}

	}

}
