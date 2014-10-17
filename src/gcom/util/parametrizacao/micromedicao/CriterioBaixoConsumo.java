
package gcom.util.parametrizacao.micromedicao;

/**
 * Enum responsável por armazenar os valores do parâmetro
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
