
package gcom.util.parametrizacao.micromedicao;

/**
 * Enum responsável por armazenar os valores do parâmetro
 * P_COBRANCA_CONSUMO_MINIMO
 * 
 * @date 17/03/2012
 */
public enum CobrancaConsumoMinimo {

	UM("1"), DOIS("2");

	private String valor;

	private CobrancaConsumoMinimo(String valor) {

		this.valor = valor;
	}

	public String getValor(){

		return this.valor;

	}
}
