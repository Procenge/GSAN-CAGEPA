/**
 * 
 */

package gcom.util.parametrizacao.batch;

import gcom.util.parametrizacao.Parametro;

/**
 * @author Hugo Lima
 * @date 01/03/2012
 */
public class ParametroBatch
				extends Parametro {

	public static final Parametro P_REGISTRO_LOG_PROCESSO = new ParametroBatch("P_REGISTRO_LOG_PROCESSO");

	public static final Parametro P_ANO_BASE = new ParametroBatch("P_ANO_BASE");

	public static final Parametro P_DIAS_DOWNLOAD_RELATORIO = new ParametroBatch("P_DIAS_DOWNLOAD_RELATORIO");

	/**
	 * @param parametro
	 */
	public ParametroBatch(String parametro) {

		super(parametro);
	}

}
