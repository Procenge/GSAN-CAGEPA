
package gcom.util.parametrizacao.cobranca;

/**
 * Enum respons�vel por armazenar os valores do par�metro
 * P_MODELO_RELATORIO_CERTIDAO_NEGATIVA_DEBITOS
 * 
 * @date 07/05/2012
 */
public enum ModeloRelatorioCertidaoNegativaDebitos {

	UM("1"), DOIS("2"), TRES("3");

	private String valor;

	private ModeloRelatorioCertidaoNegativaDebitos(String valor) {

		this.valor = valor;
	}

	public String getValor(){

		return this.valor;

	}
}
