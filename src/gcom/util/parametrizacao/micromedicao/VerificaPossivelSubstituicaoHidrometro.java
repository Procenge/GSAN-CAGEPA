
package gcom.util.parametrizacao.micromedicao;

/**
 * Enum responsável por armazenar os valores do parâmetro
 * P_VERIFICA_POSSIVEL_SUBSTITUICAO_HIDROMETRO
 * 
 * @date 18/03/2012
 */
public enum VerificaPossivelSubstituicaoHidrometro {

	UM("1"), DOIS("2");

	private String valor;

	private VerificaPossivelSubstituicaoHidrometro(String valor) {

		this.valor = valor;
	}

	public String getValor(){

		return this.valor;

	}
}
