
package gcom.gui.cobranca;

import org.apache.struts.validator.ValidatorActionForm;

/**
 * Action form respons�vel pelas propiedades do caso de uso de informar indices
 * de acrescimos por impontualidade.
 * 
 * @author S�vio Luiz
 * @date 26/09/2006
 */
public class IndiceAcrescimosImpontualidadeForm
				extends ValidatorActionForm {

	private static final long serialVersionUID = 1L;

	private String mesAnoReferencia;

	private String percentualMulta;

	private String percentualJurosMora;

	private String percentualAtualizacaoMonetaria;

	private String fatorCorrecao;

	private String camposDesabilitados;

	public String getFatorCorrecao(){

		return fatorCorrecao;
	}

	public void setFatorCorrecao(String fatorCorrecao){

		this.fatorCorrecao = fatorCorrecao;
	}

	public String getMesAnoReferencia(){

		return mesAnoReferencia;
	}

	public void setMesAnoReferencia(String mesAnoReferencia){

		this.mesAnoReferencia = mesAnoReferencia;
	}

	public String getPercentualAtualizacaoMonetaria(){

		return percentualAtualizacaoMonetaria;
	}

	public void setPercentualAtualizacaoMonetaria(String percentualAtualizacaoMonetaria){

		this.percentualAtualizacaoMonetaria = percentualAtualizacaoMonetaria;
	}

	public String getPercentualJurosMora(){

		return percentualJurosMora;
	}

	public void setPercentualJurosMora(String percentualJurosMora){

		this.percentualJurosMora = percentualJurosMora;
	}

	public String getPercentualMulta(){

		return percentualMulta;
	}

	public void setPercentualMulta(String percentualMulta){

		this.percentualMulta = percentualMulta;
	}

	public String getCamposDesabilitados(){

		return camposDesabilitados;
	}

	public void setCamposDesabilitados(String camposDesabilitados){

		this.camposDesabilitados = camposDesabilitados;
	}

}