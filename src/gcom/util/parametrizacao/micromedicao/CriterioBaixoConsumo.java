
package gcom.util.parametrizacao.micromedicao;

/**
 * Enum respons�vel por armazenar os valores do par�metro
 * P_CRITERIO_BAIXO_CONSUMO
 * 
 * @date 18/03/2012
 */
public enum CriterioBaixoConsumo {

	UM("1"), DOIS("2");

	private String valor;

	private CriterioBaixoConsumo(String valor) {

		this.valor = valor;
	}

	public String getValor(){

		return this.valor;

	}
}
