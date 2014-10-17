
package gcom.gui.cadastro.cliente;

import org.apache.struts.action.ActionForm;

public class UnificarCadastroClientesRedundantesActionForm
				extends ActionForm {

	private String numeroRegistrosComRedundacia;

	public String getNumeroRegistrosComRedundacia(){

		return numeroRegistrosComRedundacia;
	}

	public void setNumeroRegistrosComRedundacia(String numeroRegistrosComRedundacia){

		this.numeroRegistrosComRedundacia = numeroRegistrosComRedundacia;
	}

}
