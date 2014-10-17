
package gcom.relatorio.operacional.sistemaesgoto;

import gcom.relatorio.RelatorioBean;

public class RelatorioManterSistemaEsgotamentoBean
				implements RelatorioBean {

	private String codigo;

	private String descricao;

	private String descricaoAbreviada;

	private String indicadorUso;

	public RelatorioManterSistemaEsgotamentoBean(String codigo, String descricao, String descricaoAbreviada, String indicadorUso) {

		this.codigo = codigo;
		this.descricao = descricao;
		this.descricaoAbreviada = descricaoAbreviada;
		this.indicadorUso = indicadorUso;
	}

	/**
	 * @return the codigo
	 */
	public String getCodigo(){

		return codigo;
	}

	/**
	 * @param codigo
	 *            the codigo to set
	 */
	public void setCodigo(String codigo){

		this.codigo = codigo;
	}

	/**
	 * @return the descricao
	 */
	public String getDescricao(){

		return descricao;
	}

	/**
	 * @param descricao
	 *            the descricao to set
	 */
	public void setDescricao(String descricao){

		this.descricao = descricao;
	}

	/**
	 * @return the descricaoAbreviada
	 */
	public String getDescricaoAbreviada(){

		return descricaoAbreviada;
	}

	/**
	 * @param descricaoAbreviada
	 *            the descricaoAbreviada to set
	 */
	public void setDescricaoAbreviada(String descricaoAbreviada){

		this.descricaoAbreviada = descricaoAbreviada;
	}

	/**
	 * @return the indicadorUso
	 */
	public String getIndicadorUso(){

		return indicadorUso;
	}

	/**
	 * @param indicadorUso
	 *            the indicadorUso to set
	 */
	public void setIndicadorUso(String indicadorUso){

		this.indicadorUso = indicadorUso;
	}

}