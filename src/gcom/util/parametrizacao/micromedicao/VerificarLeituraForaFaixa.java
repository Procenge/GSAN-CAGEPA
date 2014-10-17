
package gcom.util.parametrizacao.micromedicao;

/**
 * Enum respons�vel por armazenar os valores do par�metro
 * P_VERIFICA_LEITURA_FORA_FAIXA
 * 
 * @date 17/03/2012
 */
public enum VerificarLeituraForaFaixa {

	UM("1"), DOIS("2");

	private String valor;

	private VerificarLeituraForaFaixa(String valor) {

		this.valor = valor;
	}

	public String getValor(){

		return this.valor;

	}
}
