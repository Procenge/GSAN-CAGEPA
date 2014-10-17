
package gcom.gui.atendimentopublico.ordemservico;

import org.apache.struts.validator.ValidatorActionForm;

public class AtividadeActionForm
				extends ValidatorActionForm {

	private String id;

	private String descricao;

	private String descricaoAbreviada;

	private String indicadorUso;

	private String ultimaAlteracao;

	private String indicadorAtividadeUnica;

	private String atualizar;

	private String verificadorAtividadeUnica;

	public String getVerificadorAtividadeUnica(){

		return verificadorAtividadeUnica;
	}

	public void setVerificadorAtividadeUnica(String verificadorAtividadeUnica){

		this.verificadorAtividadeUnica = verificadorAtividadeUnica;
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

	public String getIndicadorAtividadeUnica(){

		return indicadorAtividadeUnica;
	}

	public void setIndicadorAtividadeUnica(String indicadorAtividadeUnica){

		this.indicadorAtividadeUnica = indicadorAtividadeUnica;
	}

	public void setAtualizar(String atualizar){

		this.atualizar = atualizar;
	}

	public String getAtualizar(){

		return atualizar;
	}

}
