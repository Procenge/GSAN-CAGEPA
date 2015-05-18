
package gcom.gui.cobranca.ajustetarifa;

import org.apache.struts.validator.ValidatorActionForm;

/**
 * [UC3155] Consultar Histórico CREDTAC (Sorocaba)
 * 
 * @author Anderson Italo
 * @date 19/09/2014
 */
public class ConsultarHistoricoCREDTACActionForm
				extends ValidatorActionForm {

	private static final long serialVersionUID = 1L;

	
	private String idImovel;

	private String inscricaoImovel;

	
	public String getIdImovel(){

		return idImovel;
	}


	public void setIdImovel(String idImovel){

		this.idImovel = idImovel;
	}


	public String getInscricaoImovel(){

		return inscricaoImovel;
	}


	public void setInscricaoImovel(String inscricaoImovel){

		this.inscricaoImovel = inscricaoImovel;
	}



}
