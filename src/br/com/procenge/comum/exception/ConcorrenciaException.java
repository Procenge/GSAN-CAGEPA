
package br.com.procenge.comum.exception;

import java.util.ResourceBundle;

/**
 * @autor gilberto
 */
public class ConcorrenciaException
				extends PCGException {

	/**
     * 
     */
	public ConcorrenciaException() {

		super();
	}

	/**
	 * @param rb
	 * @param chave
	 * @param rootCause
	 */
	public ConcorrenciaException(ResourceBundle rb, String chave, Exception rootCause) {

		super(rb, chave, rootCause);
	}

	/**
	 * @param rb
	 * @param chave
	 * @param param
	 */
	public ConcorrenciaException(ResourceBundle rb, String chave, Object param) {

		super(rb, chave, param);
	}

	/**
	 * @param rb
	 * @param chave
	 * @param param
	 * @param rootCause
	 */
	public ConcorrenciaException(ResourceBundle rb, String chave, Object[] param, Exception rootCause) {

		super(rb, chave, param, rootCause);
	}

	/**
	 * @param rb
	 * @param chave
	 * @param param
	 */
	public ConcorrenciaException(ResourceBundle rb, String chave, Object[] param) {

		super(rb, chave, param);
	}

	/**
	 * @param rb
	 * @param chave
	 */
	public ConcorrenciaException(ResourceBundle rb, String chave) {

		super(rb, chave);
	}

	/**
	 * @param chave
	 * @param rootCause
	 */
	public ConcorrenciaException(String chave, Exception rootCause) {

		super(chave, rootCause);
	}

	/**
	 * @param chave
	 * @param param
	 */
	public ConcorrenciaException(String chave, Object param) {

		super(chave, param);
	}

	/**
	 * @param chave
	 * @param param
	 * @param rootCause
	 */
	public ConcorrenciaException(String chave, Object[] param, Exception rootCause) {

		super(chave, param, rootCause);
	}

	/**
	 * @param chave
	 * @param param
	 */
	public ConcorrenciaException(String chave, Object[] param) {

		super(chave, param);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ConcorrenciaException(String message, Throwable cause) {

		super(message, cause);
	}

	/**
	 * @param message
	 */
	public ConcorrenciaException(String message) {

		super(message);
	}

	/**
	 * @param cause
	 */
	public ConcorrenciaException(Throwable cause) {

		super(cause);
	}

}
