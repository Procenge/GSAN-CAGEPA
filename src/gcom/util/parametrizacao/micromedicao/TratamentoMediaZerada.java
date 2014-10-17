
package gcom.util.parametrizacao.micromedicao;

/**
 * Enum responsável por armazenar os valores do parâmetro
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
