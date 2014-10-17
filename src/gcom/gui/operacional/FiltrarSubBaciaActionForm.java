
package gcom.gui.operacional;

import org.apache.struts.validator.ValidatorActionForm;

/**
 * Filtrar SubBacia
 * 
 * @author Bruno Ferreira dos Santos
 * @date 31/01/2011
 */

public class FiltrarSubBaciaActionForm
				extends ValidatorActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String codigo;

	private String descricao;

	private String tipoPesquisa;

	private String bacia;

	private String idSistema;

	private String idSubSistema;

	private String indicadorUso;

	public String getIdSistema(){

		return idSistema;
	}

	public void setIdSistema(String idSistema){

		this.idSistema = idSistema;
	}

	public String getIdSubSistema(){

		return idSubSistema;
	}

	public void setIdSubSistema(String idSubSistema){

		this.idSubSistema = idSubSistema;
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

	public String getTipoPesquisa(){

		return tipoPesquisa;
	}

	public void setTipoPesquisa(String tipoPesquisa){

		this.tipoPesquisa = tipoPesquisa;
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

}
