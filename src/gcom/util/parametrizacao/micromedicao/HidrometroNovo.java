
package gcom.util.parametrizacao.micromedicao;

/**
 * Enum responsável por armazenar os valores do parâmetro
 * P_HIROMETRO_NOVO
 * 
 * @date 17/03/2012
 */
public enum HidrometroNovo {

	UM("1"), DOIS("2");

	private String valor;

	private HidrometroNovo(String valor) {

		this.valor = valor;
	}

	public String getValor(){

		return this.valor;

	}
}
