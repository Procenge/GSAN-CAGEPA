
package gcom.util.parametrizacao.micromedicao;

/**
 * Enum respons�vel por armazenar os valores do par�metro
 * P_NUM_DIGITOS_HIDROMETRO_VIRADA
 * 
 * @date 18/03/2012
 */
public enum NumDigitosHidrometroVirada {

	UM("1"), TRES("3");

	private String valor;

	private NumDigitosHidrometroVirada(String valor) {

		this.valor = valor;
	}

	public String getValor(){

		return this.valor;

	}
}
