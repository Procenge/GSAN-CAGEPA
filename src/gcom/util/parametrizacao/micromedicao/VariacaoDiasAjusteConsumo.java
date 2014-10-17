
package gcom.util.parametrizacao.micromedicao;

/**
 * Enum respons�vel por armazenar os valores do par�metro
 * P_VARIACAO_DIAS_AJUSTE_CONSUMO
 * 
 * @date 18/03/2012
 */
public enum VariacaoDiasAjusteConsumo {

	DOIS("2"), TRES("3");

	private String valor;

	private VariacaoDiasAjusteConsumo(String valor) {

		this.valor = valor;
	}

	public String getValor(){

		return this.valor;

	}
}
