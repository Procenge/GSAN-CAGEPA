/**
 * 
 */

package br.com.procenge.comum.exception;

/**
 * @author gmatos
 */
public class InfraestruturaException
				extends RuntimeException {

	/**
	 * 
	 */
	public InfraestruturaException() {

		super();
	}

	/**
	 * @param message
	 */
	public InfraestruturaException(String message) {

		super(message);
	}

	/**
	 * @param cause
	 */
	public InfraestruturaException(Throwable cause) {

		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public InfraestruturaException(String message, Throwable cause) {

		super(message, cause);
	}

}
