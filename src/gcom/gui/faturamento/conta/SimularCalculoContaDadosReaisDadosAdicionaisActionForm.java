
package gcom.gui.faturamento.conta;

import org.apache.struts.action.ActionForm;

/**
 * [UC3156] Simular Cálculo da Conta Dados Reais
 * 
 * @author Anderson Italo
 * @date 21/09/2014
 */
public class SimularCalculoContaDadosReaisDadosAdicionaisActionForm
				extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String quantidadeContas;

	private String idConsumoTarifa;

	private String idConsumoTarifaVigencia;

	public String getQuantidadeContas(){

		return quantidadeContas;
	}

	public void setQuantidadeContas(String quantidadeContas){

		this.quantidadeContas = quantidadeContas;
	}

	public String getIdConsumoTarifa(){

		return idConsumoTarifa;
	}

	public void setIdConsumoTarifa(String idConsumoTarifa){

		this.idConsumoTarifa = idConsumoTarifa;
	}

	public String getIdConsumoTarifaVigencia(){

		return idConsumoTarifaVigencia;
	}

	public void setIdConsumoTarifaVigencia(String idConsumoTarifaVigencia){

		this.idConsumoTarifaVigencia = idConsumoTarifaVigencia;
	}

	
}
