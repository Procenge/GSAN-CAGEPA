
package gcom.gui.cobranca.cobrancaadministrativa;

import org.apache.struts.action.ActionForm;

/**
 * [UC3060] Consultar Im�vel Cobran�a Administrativa
 * [SB0003] - Retirar Im�vel da Cobran�a Administrativa
 * 
 * @author Anderson Italo
 * @date 17/09/2012
 */
public class RetirarImovelCobrancaAdministrativaActionForm
				extends ActionForm {

	private String idMotivoRetirada;

	private String[] idRegistrosRetirada;

	public String getIdMotivoRetirada(){

		return idMotivoRetirada;
	}

	public void setIdMotivoRetirada(String idMotivoRetirada){

		this.idMotivoRetirada = idMotivoRetirada;
	}

	public String[] getIdRegistrosRetirada(){

		return idRegistrosRetirada;
	}

	public void setIdRegistrosRetirada(String[] idRegistrosRetirada){

		this.idRegistrosRetirada = idRegistrosRetirada;
	}

}
