
package gcom.util.parametrizacao.micromedicao;

/**
 * Enum respons�vel por armazenar os valores do par�metro
 * P_CRITERIO_DATA_LEITURA_ANTERIOR
 * 
 * @author Luciano Galvao
 * @date 05/08/2013
 */
public enum CriterioDataLeituraAnterior {

	UM("1"), DOIS("2");

	private String valor;

	private CriterioDataLeituraAnterior(String valor) {

		this.valor = valor;
	}

	public String getValor(){

		return this.valor;

	}
}
