
package gcom.util.parametrizacao.micromedicao;

/**
 * Enum responsável por armazenar os valores do parâmetro
 * P_CRITERIO_ESTOURO_CONSUMO
 * 
 * @date 18/03/2012
 */
public enum CriterioEstouroConsumo {

	UM("1"), DOIS("2"), TRES("3");

	private String valor;

	private CriterioEstouroConsumo(String valor) {

		this.valor = valor;
	}

	public String getValor(){

		return this.valor;

	}
}
