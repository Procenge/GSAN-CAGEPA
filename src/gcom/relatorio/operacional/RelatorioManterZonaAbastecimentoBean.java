
package gcom.relatorio.operacional;

import gcom.relatorio.RelatorioBean;

public class RelatorioManterZonaAbastecimentoBean
				implements RelatorioBean {

	private String codigo;

	private String descricao;

	private String distritoOperacional;

	private String idDistritoOperacional;

	private String descricaoComIdLocalidade;

	private String indicadorUso;

	public RelatorioManterZonaAbastecimentoBean(String codigo, String descricao, String distritoOperacional, String idDistritoOperacional,
												String descricaoComIdLocalidade, String indicadorUso) {

		this.codigo = codigo;
		this.descricao = descricao;
		this.distritoOperacional = distritoOperacional;
		this.idDistritoOperacional = idDistritoOperacional;
		this.descricaoComIdLocalidade = descricaoComIdLocalidade;
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
	 * @return the distritoOperacional
	 */
	public String getDistritoOperacional(){

		return distritoOperacional;
	}

	/**
	 * @param distritoOperacional
	 *            the distritoOperacional to set
	 */
	public void setDistritoOperacional(String distritoOperacional){

		this.distritoOperacional = distritoOperacional;
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
	 * @return the descricaoComIdLocalidade
	 */
	public String getDescricaoComIdLocalidade(){

		return descricaoComIdLocalidade;
	}

	/**
	 * @param descricaoComIdLocalidade
	 *            the descricaoComIdLocalidade to set
	 */
	public void setDescricaoComIdLocalidade(String descricaoComIdLocalidade){

		this.descricaoComIdLocalidade = descricaoComIdLocalidade;
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