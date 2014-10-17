
package gcom.gui.cobranca;

import org.apache.struts.action.ActionForm;

public class PesquisarCronogramaAcaoCobrancaActionForm
				extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String periodoReferenciaCobrancaInicial;

	private String periodoReferenciaCobrancaFinal;

	private String[] grupoCobranca;

	private String[] acaoCobranca;

	private String[] atividadeCobranca;

	public String[] getAtividadeCobranca(){

		return atividadeCobranca;
	}

	public void setAtividadeCobranca(String[] atividadeCobranca){

		this.atividadeCobranca = atividadeCobranca;
	}

	public String[] getAcaoCobranca(){

		return acaoCobranca;
	}

	public void setAcaoCobranca(String[] acaoCobranca){

		this.acaoCobranca = acaoCobranca;
	}

	public String[] getGrupoCobranca(){

		return grupoCobranca;
	}

	public void setGrupoCobranca(String[] grupoCobranca){

		this.grupoCobranca = grupoCobranca;
	}

	public String getPeriodoReferenciaCobrancaFinal(){

		return periodoReferenciaCobrancaFinal;
	}

	public void setPeriodoReferenciaCobrancaFinal(String periodoReferenciaCobrancaFinal){

		this.periodoReferenciaCobrancaFinal = periodoReferenciaCobrancaFinal;
	}

	public String getPeriodoReferenciaCobrancaInicial(){

		return periodoReferenciaCobrancaInicial;
	}

	public void setPeriodoReferenciaCobrancaInicial(String periodoReferenciaCobrancaInicial){

		this.periodoReferenciaCobrancaInicial = periodoReferenciaCobrancaInicial;
	}

}
