/**
 * 
 */

package gcom.util;

/**
 * @author isilva
 */
public class CalendarioForm {

	private String dia;

	private String novaLinha = "N";

	private String ativo = "N";

	private String quebraLinha = "N";

	public CalendarioForm() {

	}

	public CalendarioForm(String dia, String novaLinha, String ativo, String quebraLinha) {

		this.dia = dia;
		this.novaLinha = novaLinha;
		this.ativo = ativo;
		this.quebraLinha = quebraLinha;
	}

	/**
	 * @return the dia
	 */
	public String getDia(){

		return dia;
	}

	/**
	 * @param dia
	 *            the dia to set
	 */
	public void setDia(String dia){

		this.dia = dia;
	}

	/**
	 * @return the novaLinha
	 */
	public String getNovaLinha(){

		return novaLinha;
	}

	/**
	 * @param novaLinha
	 *            the novaLinha to set
	 */
	public void setNovaLinha(String novaLinha){

		this.novaLinha = novaLinha;
	}

	/**
	 * @return the ativo
	 */
	public String getAtivo(){

		return ativo;
	}

	/**
	 * @param ativo
	 *            the ativo to set
	 */
	public void setAtivo(String ativo){

		this.ativo = ativo;
	}

	/**
	 * @return the quebraLinha
	 */
	public String getQuebraLinha(){

		return quebraLinha;
	}

	/**
	 * @param quebraLinha
	 *            the quebraLinha to set
	 */
	public void setQuebraLinha(String quebraLinha){

		this.quebraLinha = quebraLinha;
	}
}
