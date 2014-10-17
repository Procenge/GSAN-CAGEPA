
package gcom.gui.atendimentopublico.registroatendimento;

import org.apache.struts.action.ActionForm;

public class MeioSolicitacaoActionForm
				extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String id;

	private String descricao;

	private String descricaoAbreviada;

	private String indicadorUso;

	private String indicadorLiberacaoDocIdent;

	public String getId(){

		return id;
	}

	public void setId(String id){

		this.id = id;
	}

	public String getIndicadorUso(){

		return indicadorUso;
	}

	public void setIndicadorUso(String indicadorUso){

		this.indicadorUso = indicadorUso;
	}

	/**
	 * Retorna o valor de descricao
	 * 
	 * @return O valor de descricao
	 */
	public String getDescricao(){

		return descricao;
	}

	/**
	 * Seta o valor de descricao
	 * 
	 * @param descricao
	 *            O novo valor de descricao
	 */
	public void setDescricao(String descricao){

		this.descricao = descricao;
	}

	/**
	 * Retorna o valor de descricaoAbreviada
	 * 
	 * @return O valor de descricaoAbreviada
	 */
	public String getDescricaoAbreviada(){

		return descricaoAbreviada;
	}

	/**
	 * Seta o valor de descricaoAbreviada
	 * 
	 * @param descricaoAbreviada
	 *            O novo valor de descricaoAbreviada
	 */
	public void setDescricaoAbreviada(String descricaoAbreviada){

		this.descricaoAbreviada = descricaoAbreviada;
	}

	public String getIndicadorLiberacaoDocIdent(){

		return indicadorLiberacaoDocIdent;
	}

	public void setIndicadorLiberacaoDocIdent(String indicadorLiberacaoDocIdent){

		this.indicadorLiberacaoDocIdent = indicadorLiberacaoDocIdent;
	}

}
