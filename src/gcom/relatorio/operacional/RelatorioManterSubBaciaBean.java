/**
 * 
 */

package gcom.relatorio.operacional;

import gcom.relatorio.RelatorioBean;

/**
 * Bean responsável de pegar os parametros que serão exibidos na parte de detail
 * do relatório.
 * 
 * @author Bruno Ferreira dos Santos
 * @created 01 de Fevereiro de 2011
 */
public class RelatorioManterSubBaciaBean
				implements RelatorioBean {

	private String codigo;

	private String descricao;

	private String bacia;

	private String idBacia;

	private String indicadorUso;

	public RelatorioManterSubBaciaBean(String codigo, String descricao, String bacia, String idBacia, String indicadorUso) {

		this.codigo = codigo;
		this.descricao = descricao;
		this.bacia = bacia;
		this.idBacia = idBacia;
		this.indicadorUso = indicadorUso;
	}

	public String getCodigo(){

		return codigo;
	}

	public void setCodigo(String codigo){

		this.codigo = codigo;
	}

	public String getDescricao(){

		return descricao;
	}

	public void setDescricao(String descricao){

		this.descricao = descricao;
	}

	public String getBacia(){

		return bacia;
	}

	public void setBacia(String bacia){

		this.bacia = bacia;
	}

	public String getIndicadorUso(){

		return indicadorUso;
	}

	public void setIndicadorUso(String indicadorUso){

		this.indicadorUso = indicadorUso;
	}

	/**
	 * @param idBacia
	 *            the idBacia to set
	 */
	public void setIdBacia(String idBacia){

		this.idBacia = idBacia;
	}

	/**
	 * @return the idBacia
	 */
	public String getIdBacia(){

		return idBacia;
	}

}
