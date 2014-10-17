
package gcom.gui.relatorio.arrecadacao;

import org.apache.struts.validator.ValidatorActionForm;

/**
 * Situação dos avisos bancários
 * 
 * @author Hebert Falcão
 * @since 04/10/2013
 */
public class GerarRelatorioSituacaoDosAvisosBancariosActionForm
				extends ValidatorActionForm {

	private static final long serialVersionUID = 1L;

	private String mesAnoReferencia;

	public String getMesAnoReferencia(){

		return mesAnoReferencia;
	}

	public void setMesAnoReferencia(String mesAnoReferencia){

		this.mesAnoReferencia = mesAnoReferencia;
	}

}
