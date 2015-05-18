
package gcom.gui.cobranca.cobrancaadministrativa;

import org.apache.struts.action.ActionForm;

/**
 * [UC3060] Consultar Imóvel Cobrança Administrativa
 * [SB0003] - Retirar Imóvel da Cobrança Administrativa
 * 
 * @author Anderson Italo
 * @date 17/09/2012
 */
public class RetirarImovelCobrancaAdministrativaActionForm
				extends ActionForm {

	private String formatoRelatorio;

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

	public String getFormatoRelatorio(){

		return formatoRelatorio;
	}

	public void setFormatoRelatorio(String formatoRelatorio){

		this.formatoRelatorio = formatoRelatorio;
	}

}
