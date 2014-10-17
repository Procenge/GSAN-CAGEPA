
package gcom.gui.relatorio.arrecadacao;

import org.apache.struts.validator.ValidatorActionForm;

/**
 * Comprovantes da Arrecadação por Recebedor
 * 
 * @author Hebert Falcão
 * @since 28/09/2013
 */
public class GerarRelatorioComprovantesDaArrecadacaoPorRecebedorActionForm
				extends ValidatorActionForm {

	private static final long serialVersionUID = 1L;

	private String mesAnoReferencia;

	private String indicadorTipoRelatorio;

	public final static String INDICADOR_ANALITICO = "1";

	public final static String INDICADOR_SINTETICO = "2";

	public String getMesAnoReferencia(){

		return mesAnoReferencia;
	}

	public void setMesAnoReferencia(String mesAnoReferencia){

		this.mesAnoReferencia = mesAnoReferencia;
	}

	public String getIndicadorTipoRelatorio(){

		return indicadorTipoRelatorio;
	}

	public void setIndicadorTipoRelatorio(String indicadorTipoRelatorio){

		this.indicadorTipoRelatorio = indicadorTipoRelatorio;
	}

}
