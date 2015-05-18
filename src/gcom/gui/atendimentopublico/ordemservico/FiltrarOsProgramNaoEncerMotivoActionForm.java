
package gcom.gui.atendimentopublico.ordemservico;

import org.apache.struts.validator.ValidatorActionForm;

public class FiltrarOsProgramNaoEncerMotivoActionForm
				extends ValidatorActionForm {

	private String id;

	private String descricao;

	private String descricaoAbreviada;

	private String indicadorUso;

	private String atualizar;

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

	public void setAtualizar(String atualizar){

		this.atualizar = atualizar;
	}

	public String getAtualizar(){

		return atualizar;
	}
}
