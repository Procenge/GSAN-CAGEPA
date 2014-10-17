
package gcom.util.parametrizacao.micromedicao;

/**
 * Enum responsável por armazenar os valores do parâmetro
 * P_CRITERIO_LEITURA_ATUAL
 * 
 * @date 04/08/2013
 */
public enum CriterioLeituraAtual {

	UM("1"), DOIS("2");

	private String valor;

	private CriterioLeituraAtual(String valor) {

		this.valor = valor;
	}

	public String getValor(){

		return this.valor;

	}
}
