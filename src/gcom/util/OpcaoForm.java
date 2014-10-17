/**
 * 
 */

package gcom.util;

/**
 * @author isilva
 */
public class OpcaoForm {

	private String chave;

	private String valor;

	public OpcaoForm() {

	}

	public OpcaoForm(String chave, String valor) {

		this.chave = chave;
		this.valor = valor;
	}

	/**
	 * @return the chave
	 */
	public String getChave(){

		return chave;
	}

	/**
	 * @param chave
	 *            the chave to set
	 */
	public void setChave(String chave){

		this.chave = chave;
	}

	/**
	 * @return the valor
	 */
	public String getValor(){

		return valor;
	}

	/**
	 * @param valor
	 *            the valor to set
	 */
	public void setValor(String valor){

		this.valor = valor;
	}

}
