
package gcom.gui.cobranca;

import org.apache.struts.action.ActionForm;

/**
 * [UC3023] Manter Boleto Bancário
 * 
 * @author Hebert Falcão
 * @date 12/10/2011
 */
public class CancelarBoletoBancarioActionForm
				extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String idMotivoCancelamento;

	private String[] idRegistrosCancelamento;

	public String getIdMotivoCancelamento(){

		return idMotivoCancelamento;
	}

	public void setIdMotivoCancelamento(String idMotivoCancelamento){

		this.idMotivoCancelamento = idMotivoCancelamento;
	}

	public String[] getIdRegistrosCancelamento(){

		return idRegistrosCancelamento;
	}

	public void setIdRegistrosCancelamento(String[] idRegistrosCancelamento){

		this.idRegistrosCancelamento = idRegistrosCancelamento;
	}

}
