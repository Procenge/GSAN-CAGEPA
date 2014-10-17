
package gcom.util.parametrizacao.micromedicao;

/**
 * Enum respons�vel por armazenar os valores do par�metro
 * P_DEFINICAO_MES_AJUSTE_CONSUMO
 * 
 * @date 18/03/2012
 */
public enum DefinicaoMesAjusteConsumo {

	UM("1"), DOIS("2");

	private String valor;

	private DefinicaoMesAjusteConsumo(String valor) {

		this.valor = valor;
	}

	public String getValor(){

		return this.valor;

	}
}
