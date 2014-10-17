
package gcom.util.parametrizacao.micromedicao;

/**
 * Enum responsável por armazenar os valores do parâmetro
 * P_ACAO_AJUSTE_CONSUMO
 * 
 * @date 18/03/2012
 */
public enum AcaoAjusteConsumo {

	UM("1"), DOIS("2"), TRES("3");

	private String valor;

	private AcaoAjusteConsumo(String valor) {

		this.valor = valor;
	}

	public String getValor(){

		return this.valor;

	}
}
