
package gcom.util.filtro;

/**
 * < <Descri��o da Classe>>
 * 
 * @author Josenildo Neves
 */
public class Maior
				extends FiltroParametro {

	private static final long serialVersionUID = 1L;

	private Object numero;

	/**
	 * Construtor da classe Maior
	 * 
	 * @param nomeAtributoIntervalo
	 *            Descri��o do par�metro
	 * @param numero
	 *            Descri��o do par�metro
	 */
	public Maior(String nomeAtributoIntervalo, Object numero) {

		super(nomeAtributoIntervalo);
		this.numero = numero;
	}

	/**
	 * Construtor da classe ParametroNulo
	 * 
	 * @param nomeAtributoIntervalo
	 *            Descri��o do par�metro
	 * @param numero
	 *            Descri��o do par�metro
	 * @param conector
	 *            Conector da query
	 */
	public Maior(String nomePrimeiroAtributo, Object numero, String conector) {

		super(nomePrimeiroAtributo, conector);
		this.numero = numero;
	}

	/**
	 * Construtor da classe Maior
	 * 
	 * @param nomeAtributo
	 *            Descri��o do par�metro
	 * @param numero
	 *            Descri��o do par�metro
	 * @param conector
	 *            Descri��o do par�metro
	 * @param numeroParametrosIsoladosConector
	 *            Descri��o do par�metro
	 */
	public Maior(String nomeAtributo, Object numero, String conector, int numeroParametrosIsoladosConector) {

		super(nomeAtributo, conector, numeroParametrosIsoladosConector);
		this.numero = numero;
	}

	/**
	 * Retorna o valor de numero
	 * 
	 * @return O valor de numero
	 */
	public Object getNomeSegundoAtributo(){

		return numero;
	}

	/**
	 * Seta o valor de numero
	 * 
	 * @param numero
	 *            O novo valor de numero
	 */
	public void setNumero(Object numero){

		this.numero = numero;
	}

}
