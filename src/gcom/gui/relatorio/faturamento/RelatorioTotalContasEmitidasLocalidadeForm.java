
package gcom.gui.relatorio.faturamento;

import org.apache.struts.action.ActionForm;

public class RelatorioTotalContasEmitidasLocalidadeForm
				extends ActionForm {

	private String referencia;

	public String getReferencia(){

		return referencia;
	}

	public void setReferencia(String referencia){

		this.referencia = referencia;
	}

}
