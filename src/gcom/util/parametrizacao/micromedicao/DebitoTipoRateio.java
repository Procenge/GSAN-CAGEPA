
package gcom.util.parametrizacao.micromedicao;

/**
 * Enum responsável por armazenar os valores do parâmetro
 * P_DEBITO_TIPO_RATEIO
 * 
 * @date 21/05/2012
 */
public enum DebitoTipoRateio {

	SESSENTA_E_QUATRO("64"), ZERO("0");

	private String valor;

	private DebitoTipoRateio(String valor) {

		this.valor = valor;
	}

	public String getValor(){

		return this.valor;

	}
}
