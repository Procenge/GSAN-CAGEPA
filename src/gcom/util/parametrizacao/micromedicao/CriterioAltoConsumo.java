
package gcom.util.parametrizacao.micromedicao;

/**
 * Enum respons�vel por armazenar os valores do par�metro
 * P_CRITERIO_ALTO_CONSUMO
 * 
 * @date 18/03/2012
 */
public enum CriterioAltoConsumo {

	UM("1"), DOIS("2"), TRES("3");

	private String valor;

	private CriterioAltoConsumo(String valor) {

		this.valor = valor;
	}

	public String getValor(){

		return this.valor;

	}
}