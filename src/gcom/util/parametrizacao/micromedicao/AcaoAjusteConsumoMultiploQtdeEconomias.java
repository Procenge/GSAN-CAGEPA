
package gcom.util.parametrizacao.micromedicao;

/**
 * Enum respons�vel por armazenar os valores do par�metro
 * P_ACAO_AJUSTE_CONSUMO_MULTIPLO_QTDE_ECONOMIAS
 * 
 * @date 24/03/2012
 */
public enum AcaoAjusteConsumoMultiploQtdeEconomias {

	UM("1"), DOIS("2"), TRES("3"), QUATRO("4");

	private String valor;

	private AcaoAjusteConsumoMultiploQtdeEconomias(String valor) {

		this.valor = valor;
	}

	public String getValor(){

		return this.valor;

	}
}
