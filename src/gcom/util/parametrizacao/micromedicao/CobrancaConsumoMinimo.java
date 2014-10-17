
package gcom.util.parametrizacao.micromedicao;

/**
 * Enum respons�vel por armazenar os valores do par�metro
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
