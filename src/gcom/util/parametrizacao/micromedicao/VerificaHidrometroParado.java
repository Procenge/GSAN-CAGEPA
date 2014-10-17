
package gcom.util.parametrizacao.micromedicao;

/**
 * Enum responsável por armazenar os valores do parâmetro
 * P_VERIFICA_HIDROMETRO_PARADO
 * 
 * @date 18/03/2012
 */
public enum VerificaHidrometroParado {

	UM("1"), DOIS("2");

	private String valor;

	private VerificaHidrometroParado(String valor) {

		this.valor = valor;
	}

	public String getValor(){

		return this.valor;

	}
}
