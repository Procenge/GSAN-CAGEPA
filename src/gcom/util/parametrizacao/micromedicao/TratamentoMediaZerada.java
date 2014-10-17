
package gcom.util.parametrizacao.micromedicao;

/**
 * Enum respons�vel por armazenar os valores do par�metro
 * P_TRATAMENTO_MEDIA_ZERADA
 * 
 * @date 23/03/2012
 */
public enum TratamentoMediaZerada {

	UM("1"), DOIS("2");

	private String valor;

	private TratamentoMediaZerada(String valor) {

		this.valor = valor;
	}

	public String getValor(){

		return this.valor;

	}
}
