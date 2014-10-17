
package gcom.gui.operacional;

import org.apache.struts.validator.ValidatorActionForm;

/**
 * Filtrar ZonaAbastecimento
 * 
 * @author Bruno Ferreira dos Santos
 * @date 02/02/2011
 */

public class FiltrarZonaAbastecimentoActionForm
				extends ValidatorActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String codigo;

	private String descricao;

	private String tipoPesquisa;

	private String idSistemaAbastecimento;

	private String indicadorUso;

	private String idDistritoOperacional;

	private String idLocalidade;

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

	public String getIdSistemaAbastecimento(){

		return idSistemaAbastecimento;
	}

	public void setIdSistemaAbastecimento(String idSistemaAbastecimento){

		this.idSistemaAbastecimento = idSistemaAbastecimento;
	}

	public String getIndicadorUso(){

		return indicadorUso;
	}

	public void setIndicadorUso(String indicadorUso){

		this.indicadorUso = indicadorUso;
	}

	/**
	 * @return the idDistritoOperacional
	 */
	public String getIdDistritoOperacional(){

		return idDistritoOperacional;
	}

	/**
	 * @param idDistritoOperacional
	 *            the idDistritoOperacional to set
	 */
	public void setIdDistritoOperacional(String idDistritoOperacional){

		this.idDistritoOperacional = idDistritoOperacional;
	}

	/**
	 * @return the idLocalidade
	 */
	public String getIdLocalidade(){

		return idLocalidade;
	}

	/**
	 * @param idLocalidade
	 *            the idLocalidade to set
	 */
	public void setIdLocalidade(String idLocalidade){

		this.idLocalidade = idLocalidade;
	}
}
