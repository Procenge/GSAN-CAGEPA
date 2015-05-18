
package gcom.gui.cobranca.ajustetarifa;

import org.apache.struts.action.ActionForm;

/**
 * [UC3155] Consultar Histórico CREDTAC (Sorocaba)
 * 
 * @author Anderson Italo
 * @date 20/09/2014
 */
public class ExibirDadosHistoricoCREDTACActionForm
				extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String idImovel;
	private String inscricaoFormatadaImovel;

	private String enderecoImovel;



	public String getIdImovel(){

		return idImovel;
	}

	public void setIdImovel(String idImovel){

		this.idImovel = idImovel;
	}

	public String getInscricaoFormatadaImovel(){

		return inscricaoFormatadaImovel;
	}

	public void setInscricaoFormatadaImovel(String inscricaoFormatadaImovel){

		this.inscricaoFormatadaImovel = inscricaoFormatadaImovel;
	}

	public String getEnderecoImovel(){

		return enderecoImovel;
	}

	public void setEnderecoImovel(String enderecoImovel){

		this.enderecoImovel = enderecoImovel;
	}

	public void limpar(){

		this.idImovel = "";
		this.inscricaoFormatadaImovel = "";
		this.enderecoImovel = "";
	}
}
