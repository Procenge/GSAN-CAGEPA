
package gcom.gui.cobranca;

import org.apache.struts.action.ActionForm;

public class FiltroRelatorioLiquidosRecebiveisActionForm
				extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String comando;

	private String periodoInicial;

	private String periodoFim;

	public String getComando(){

		return comando;
	}

	public void setComando(String comando){

		this.comando = comando;
	}

	public String getPeriodoInicial(){

		return periodoInicial;
	}

	public void setPeriodoInicial(String periodoInicial){

		this.periodoInicial = periodoInicial;
	}

	public String getPeriodoFim(){

		return periodoFim;
	}

	public void setPeriodoFim(String periodoFim){

		this.periodoFim = periodoFim;
	}

}
