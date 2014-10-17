
package br.com.procenge.comum.exception;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ResourceBundle;

import br.com.procenge.util.MensagemUtil;

/**
 * @autor gilberto
 */
public class PCGException
				extends Exception {

	protected static ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("gcom.properties.application");

	private Exception rootCause;

	private String chaveErro;

	private Object[] parametrosErro;

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Construtor padrão.
	 */
	public PCGException() {

		super();
	}

	/**
	 * @param message
	 */
	public PCGException(String message) {

		super(message);
	}

	/**
	 * @param cause
	 */
	public PCGException(Throwable cause) {

		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public PCGException(String message, Throwable cause) {

		super(message, cause);
	}

	/**
	 * @param chave
	 * @param param
	 * @param rootCause
	 */
	public PCGException(String chave, Object param[], Exception rootCause) {

		this(RESOURCE_BUNDLE, chave, param, rootCause);
	}

	/**
	 * @param chave
	 * @param rootCause
	 */
	public PCGException(String chave, Exception rootCause) {

		this(RESOURCE_BUNDLE, chave, rootCause);
	}

	/**
	 * @param chave
	 * @param param
	 */
	public PCGException(String chave, Object param) {

		this(RESOURCE_BUNDLE, chave, param);
	}

	/**
	 * @param chave
	 * @param param
	 */
	public PCGException(String chave, Object param[]) {

		this(RESOURCE_BUNDLE, chave, param);
	}

	/**
	 * @param rb
	 * @param chave
	 * @param param
	 */
	public PCGException(ResourceBundle rb, String chave, Object param) {

		this(rb, chave, new Object[] {param});
	}

	/**
	 * @param rb
	 * @param chave
	 * @param param
	 * @param rootCause
	 */
	public PCGException(ResourceBundle rb, String chave, Object param[], Exception rootCause) {

		this(rb, chave, param);
		this.rootCause = rootCause;
		this.chaveErro = chave;
		this.parametrosErro = param;
	}

	/**
	 * @param rb
	 * @param chave
	 * @param rootCause
	 */
	public PCGException(ResourceBundle rb, String chave, Exception rootCause) {

		this(rb, chave);
		this.rootCause = rootCause;
		this.chaveErro = chave;
	}

	/**
	 * @param rb
	 * @param chave
	 * @param param
	 */
	public PCGException(ResourceBundle rb, String chave, Object param[]) {

		super(MensagemUtil.obterMensagem(rb, chave, param));
		this.chaveErro = chave;
		this.parametrosErro = param;
	}

	/**
	 * @param rb
	 * @param chave
	 */
	public PCGException(ResourceBundle rb, String chave) {

		super(MensagemUtil.obterMensagem(rb, chave));
		this.chaveErro = chave;
	}

	/**
     * 
     */
	public void printStackTrace(PrintWriter s){

		super.printStackTrace(s);
		if(getRootCause() != null){
			getRootCause().printStackTrace(s);
		}
	}

	/**
     * 
     */
	public void printStackTrace(PrintStream s){

		super.printStackTrace(s);
		if(getRootCause() != null){
			getRootCause().printStackTrace(s);
		}
	}

	/**
     * 
     */
	public void printStackTrace(){

		super.printStackTrace();
		if(getRootCause() != null){
			getRootCause().printStackTrace();
		}
	}

	/**
	 * @return Retorna o atributo rootCause.
	 */
	public Exception getRootCause(){

		return rootCause;
	}

	/**
	 * @param rootCause
	 *            O valor a ser atribuído ao atributo rootCause.
	 */
	public void setRootCause(Exception rootCause){

		this.rootCause = rootCause;
	}

	/**
	 * @return Retorna o atributo chaveErro.
	 */
	public String getChaveErro(){

		return chaveErro;
	}

	/**
	 * @param chaveErro
	 *            O valor a ser atribuído ao atributo chaveErro.
	 */
	public void setChaveErro(String chaveErro){

		this.chaveErro = chaveErro;
	}

	/**
	 * @return Retorna o atributo parametrosErro.
	 */
	public Object[] getParametrosErro(){

		return parametrosErro;
	}

	/**
	 * @param parametrosErro
	 *            O valor a ser atribuído ao atributo parametrosErro.
	 */
	public void setParametrosErro(Object[] parametrosErro){

		this.parametrosErro = parametrosErro;
	}

}