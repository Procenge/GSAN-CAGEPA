
package gcom.gui.atendimentopublico.ordemservico;

import org.apache.struts.validator.ValidatorActionForm;

public class InserirOsProgramNaoEncerMotivoActionForm
				extends ValidatorActionForm {

	private String id;

	private String descricao;

	private String descricaoAbreviada;

	private String indicadorUso;

	private String ultimaAlteracao;

	private String indicadorVisitaImprodutiva;

	private String indicadorCobraVisitaImprodutiva;

	public String getIndicadorCobraVisitaImprodutiva(){

		return indicadorCobraVisitaImprodutiva;
	}

	public void setIndicadorCobraVisitaImprodutiva(String indicadorCobraVisitaImprodutiva){

		this.indicadorCobraVisitaImprodutiva = indicadorCobraVisitaImprodutiva;
	}

	public String getId(){

		return id;
	}

	public void setId(String id){

		this.id = id;
	}

	public String getDescricao(){

		return descricao;
	}

	public void setDescricao(String descricao){

		this.descricao = descricao;
	}

	public String getDescricaoAbreviada(){

		return descricaoAbreviada;
	}

	public void setDescricaoAbreviada(String descricaoAbreviada){

		this.descricaoAbreviada = descricaoAbreviada;
	}

	public String getIndicadorUso(){

		return indicadorUso;
	}

	public void setIndicadorUso(String indicadorUso){

		this.indicadorUso = indicadorUso;
	}

	public String getUltimaAlteracao(){

		return ultimaAlteracao;
	}

	public void setUltimaAlteracao(String ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public String getIndicadorVisitaImprodutiva(){

		return indicadorVisitaImprodutiva;
	}

	public void setIndicadorVisitaImprodutiva(String indicadorVisitaImprodutiva){

		this.indicadorVisitaImprodutiva = indicadorVisitaImprodutiva;
	}
}
