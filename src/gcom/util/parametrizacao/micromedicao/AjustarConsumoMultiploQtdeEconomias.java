
package gcom.util.parametrizacao.micromedicao;

/**
 * Enum responsável por armazenar os valores do parâmetro
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
