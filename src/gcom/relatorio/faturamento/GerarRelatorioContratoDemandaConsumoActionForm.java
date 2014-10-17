
package gcom.relatorio.faturamento;

import org.apache.struts.validator.ValidatorActionForm;

/**
 * Contrato Demanda Consumo
 * 
 * @author Vicente Zarga
 * @since 11/01/2014
 */
public class GerarRelatorioContratoDemandaConsumoActionForm
				extends ValidatorActionForm {

	private static final long serialVersionUID = 1L;

	private String faturamentoGrupo;

	private String[] localidade;

	private String mesAnoFaturamentoInicial;

	private String mesAnoFaturamentoFinal;

	private String tipoContrato;

	private String tarifaConsumo;

	private String encerrado;

	public String getFaturamentoGrupo(){

		return faturamentoGrupo;
	}

	public void setFaturamentoGrupo(String faturamentoGrupo){

		this.faturamentoGrupo = faturamentoGrupo;
	}

	public String[] getLocalidade(){

		return localidade;
	}

	public void setLocalidade(String[] localidade){

		this.localidade = localidade;
	}

	public String getMesAnoFaturamentoInicial(){

		return mesAnoFaturamentoInicial;
	}

	public void setMesAnoFaturamentoInicial(String mesAnoFaturamentoInicial){

		this.mesAnoFaturamentoInicial = mesAnoFaturamentoInicial;
	}

	public String getMesAnoFaturamentoFinal(){

		return mesAnoFaturamentoFinal;
	}

	public void setMesAnoFaturamentoFinal(String mesAnoFaturamentoFinal){

		this.mesAnoFaturamentoFinal = mesAnoFaturamentoFinal;
	}

	public String getTipoContrato(){

		return tipoContrato;
	}

	public void setTipoContrato(String tipoContrato){

		this.tipoContrato = tipoContrato;
	}

	public String getTarifaConsumo(){

		return tarifaConsumo;
	}

	public void setTarifaConsumo(String tarifaConsumo){

		this.tarifaConsumo = tarifaConsumo;
	}

	public String getEncerrado(){

		return encerrado;
	}

	public void setEncerrado(String encerrado){

		this.encerrado = encerrado;
	}

}
