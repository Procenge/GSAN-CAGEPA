
package gcom.util.parametrizacao.micromedicao;

/**
 * Enum respons�vel por armazenar os valores do par�metro
 * P_AJUSTAR_CONSUMO
 * 
 * @date 24/03/2012
 */
public enum AjustarConsumoMultiploQtdeEconomias {

	UM("1"), DOIS("2");

	private String valor;

	private AjustarConsumoMultiploQtdeEconomias(String valor) {

		this.valor = valor;
	}

	public String getValor(){

		return this.valor;

	}
}
