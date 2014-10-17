
package gcom.util.parametrizacao.spcserasa;

import gcom.spcserasa.IRepositorioSpcSerasa;
import gcom.spcserasa.RepositorioSpcSerasaHBM;
import gcom.util.parametrizacao.ExecutorParametro;

import javax.ejb.CreateException;

public class ExecutorParametrosSpcSerasa
				implements ExecutorParametro {

	private static final ExecutorParametrosSpcSerasa instancia = new ExecutorParametrosSpcSerasa();

	protected IRepositorioSpcSerasa repositorioSpcSerasa;

	public static ExecutorParametrosSpcSerasa getInstancia(){

		return instancia;
	}

	private ExecutorParametrosSpcSerasa() {

	}

	public void ejbCreate() throws CreateException{

		repositorioSpcSerasa = RepositorioSpcSerasaHBM.getInstancia();
	}

}
