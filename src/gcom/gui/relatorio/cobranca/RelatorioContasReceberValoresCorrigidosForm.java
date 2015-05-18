
package gcom.gui.relatorio.cobranca;

import org.apache.struts.action.ActionForm;

public class RelatorioContasReceberValoresCorrigidosForm
				extends ActionForm {

	private String matriculaImovel;

	private String inscricaoImovel;

	private String referencia;

	private String tipoRelatorio;

	public String getMatriculaImovel(){

		return matriculaImovel;
	}

	public void setMatriculaImovel(String matriculaImovel){

		this.matriculaImovel = matriculaImovel;
	}

	public String getInscricaoImovel(){

		return inscricaoImovel;
	}

	public void setInscricaoImovel(String inscricaoImovel){

		this.inscricaoImovel = inscricaoImovel;
	}

	public String getReferencia(){

		return referencia;
	}

	public void setReferencia(String referencia){

		this.referencia = referencia;
	}

	public String getTipoRelatorio(){

		return tipoRelatorio;
	}

	public void setTipoRelatorio(String tipoRelatorio){

		this.tipoRelatorio = tipoRelatorio;
	}
}
