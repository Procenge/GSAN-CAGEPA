
package br.com.procenge.comum.exception;

import java.util.Map;
import java.util.ResourceBundle;

/**
 * @autor gilberto
 */
public class NegocioException
				extends PCGException {

	private static final long serialVersionUID = 1L;

	private Map erros;

	/**
	 * Construtor padrão.
	 */
	public NegocioException() {

		super();
	}

	/**
	 * @param message
	 * @param cause
	 */
	public NegocioException(String message, Throwable cause) {

		super(message, cause);
	}

	/**
	 * @param message
	 */
	public NegocioException(String message) {

		super(message);
	}

	/**
	 * @param cause
	 */
	public NegocioException(Throwable cause) {

		super(cause);
	}

	/**
	 * @param erros
	 */
	public NegocioException(Map erros) {

		super();
		this.erros = erros;
	}

	/**
	 * @param rb
	 * @param chave
	 * @param param
	 */
	public NegocioException(ResourceBundle rb, String chave, Object[] param) {

		super(rb, chave, param);
	}

	/**
	 * @param rb
	 * @param chave
	 */
	public NegocioException(ResourceBundle rb, String chave) {

		super(rb, chave);
	}

	/**
	 * @param rb
	 * @param chave
	 * @param rootCause
	 */
	public NegocioException(ResourceBundle rb, String chave, Exception rootCause) {

		super(rb, chave, rootCause);
	}

	/**
	 * @param rb
	 * @param chave
	 * @param param
	 */
	public NegocioException(ResourceBundle rb, String chave, Object param) {

		super(rb, chave, param);
	}

	/**
	 * @param rb
	 * @param chave
	 * @param param
	 * @param rootCause
	 */
	public NegocioException(ResourceBundle rb, String chave, Object[] param, Exception rootCause) {

		super(rb, chave, param, rootCause);
	}

	/**
	 * @param chave
	 * @param rootCause
	 */
	public NegocioException(String chave, Exception rootCause) {

		super(chave, rootCause);
	}

	/**
	 * @param chave
	 * @param param
	 */
	public NegocioException(String chave, Object param) {

		super(chave, param);
	}

	/**
	 * @param chave
	 * @param param
	 * @param rootCause
	 */
	public NegocioException(String chave, Object[] param, Exception rootCause) {

		super(chave, param, rootCause);
	}

	/**
	 * @param chave
	 * @param param
	 */
	public NegocioException(String chave, Object[] param) {

		super(chave, param);
	}

	/**
	 * @return Retorna o atributo erros.
	 */
	public Map getErros(){

		return erros;
	}

	/**
	 * @param erros
	 *            O valor a ser atribuído ao atributo erros.
	 */
	public void setErros(Map erros){

		this.erros = erros;
	}

}