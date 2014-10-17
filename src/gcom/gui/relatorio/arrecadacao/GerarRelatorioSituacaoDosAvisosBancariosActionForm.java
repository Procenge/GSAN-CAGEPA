
package gcom.gui.relatorio.arrecadacao;

import org.apache.struts.validator.ValidatorActionForm;

/**
 * Situa��o dos avisos banc�rios
 * 
 * @author Hebert Falc�o
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
