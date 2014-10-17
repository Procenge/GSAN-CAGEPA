
package gcom.gui.atendimentopublico.ordemservico;

import org.apache.struts.validator.ValidatorActionForm;

/**
 * Consumo Médio do Imóvel
 * 
 * @author Hebert Falcão
 * @date 12/05/2011
 */
public class ConsumoMedioImovelPopupActionForm
				extends ValidatorActionForm {

	private static final long serialVersionUID = 1L;

	private String consumoMedioInicial;

	private String consumoMedioFinal;

	public String getConsumoMedioInicial(){

		return consumoMedioInicial;
	}

	public void setConsumoMedioInicial(String consumoMedioInicial){

		this.consumoMedioInicial = consumoMedioInicial;
	}

	public String getConsumoMedioFinal(){

		return consumoMedioFinal;
	}

	public void setConsumoMedioFinal(String consumoMedioFinal){

		this.consumoMedioFinal = consumoMedioFinal;
	}

}
