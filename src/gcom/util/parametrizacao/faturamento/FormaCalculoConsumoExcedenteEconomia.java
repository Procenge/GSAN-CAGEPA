
package gcom.util.parametrizacao.faturamento;

/**
 * Enum respons�vel por armazenar os valores do par�metro
 * P_FORMA_CALCULO_CONSUMO_EXCEDENTE_ECONOMIA
 * 
 * @date 09/10/2013
 */
public enum FormaCalculoConsumoExcedenteEconomia {

	UM("1"), DOIS("2");

	private String valor;

	private FormaCalculoConsumoExcedenteEconomia(String valor) {

		this.valor = valor;
	}

	public String getValor(){

		return this.valor;

	}
}
