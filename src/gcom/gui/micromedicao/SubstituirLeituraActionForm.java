
package gcom.gui.micromedicao;

import org.apache.struts.validator.ValidatorForm;

/**
 * [UC3009] - Substituir Leituras Anteriores
 * 
 * @author Hebert Falcão
 * @date 09/06/2011
 */
public class SubstituirLeituraActionForm
				extends ValidatorForm {

	private static final long serialVersionUID = 1L;

	private String idImovelSubstituirLeitura;

	private String inscricaoImovel;

	public String getIdImovelSubstituirLeitura(){

		return idImovelSubstituirLeitura;
	}

	public void setIdImovelSubstituirLeitura(String idImovelSubstituirLeitura){

		this.idImovelSubstituirLeitura = idImovelSubstituirLeitura;
	}

	public String getInscricaoImovel(){

		return inscricaoImovel;
	}

	public void setInscricaoImovel(String inscricaoImovel){

		this.inscricaoImovel = inscricaoImovel;
	}

}
