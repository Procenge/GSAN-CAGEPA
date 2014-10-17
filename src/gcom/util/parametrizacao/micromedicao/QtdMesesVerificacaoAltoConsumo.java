
package gcom.util.parametrizacao.micromedicao;

/**
 * Enum responsável por armazenar os valores do parâmetro
 * P_QTD_MESES_VERIFICACAO_ALTO_CONSUMO
 * 
 * @date 18/03/2012
 */
public enum QtdMesesVerificacaoAltoConsumo {

	ZERO("0"), CINCO("5");

	private String valor;

	private QtdMesesVerificacaoAltoConsumo(String valor) {

		this.valor = valor;
	}

	public String getValor(){

		return this.valor;

	}
}
