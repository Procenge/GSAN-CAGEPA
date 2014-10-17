
package gcom.util.parametrizacao.micromedicao;

/**
 * Enum respons�vel por armazenar os valores do par�metro
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
