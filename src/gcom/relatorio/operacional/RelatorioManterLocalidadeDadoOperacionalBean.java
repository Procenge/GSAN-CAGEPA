
package gcom.relatorio.operacional;

import gcom.relatorio.RelatorioBean;

/**
 * @author isilva
 * @date 02/02/2011
 */
public class RelatorioManterLocalidadeDadoOperacionalBean
				implements RelatorioBean {

	private String codigoLocalidade;

	private String descricaoLocalidade;

	private String mesAnoReferencia;

	private String indicadorUso;

	public RelatorioManterLocalidadeDadoOperacionalBean(String codigoLocalidade, String descricaoLocalidade, String mesAnoReferencia,
														String indicadorUso) {

		this.codigoLocalidade = codigoLocalidade;
		this.descricaoLocalidade = descricaoLocalidade;
		this.mesAnoReferencia = mesAnoReferencia;
		this.indicadorUso = indicadorUso;
	}

	public RelatorioManterLocalidadeDadoOperacionalBean(String codigoLocalidade, String descricaoLocalidade, String mesAnoReferencia) {

		this.codigoLocalidade = codigoLocalidade;
		this.descricaoLocalidade = descricaoLocalidade;
		this.mesAnoReferencia = mesAnoReferencia;
	}

	/**
	 * @return the codigoLocalidade
	 */
	public String getCodigoLocalidade(){

		return codigoLocalidade;
	}

	/**
	 * @param codigoLocalidade
	 *            the codigoLocalidade to set
	 */
	public void setCodigoLocalidade(String codigoLocalidade){

		this.codigoLocalidade = codigoLocalidade;
	}

	/**
	 * @return the descricaoLocalidade
	 */
	public String getDescricaoLocalidade(){

		return descricaoLocalidade;
	}

	/**
	 * @param descricaoLocalidade
	 *            the descricaoLocalidade to set
	 */
	public void setDescricaoLocalidade(String descricaoLocalidade){

		this.descricaoLocalidade = descricaoLocalidade;
	}

	/**
	 * @return the mesAnoReferencia
	 */
	public String getMesAnoReferencia(){

		return mesAnoReferencia;
	}

	/**
	 * @param mesAnoReferencia
	 *            the mesAnoReferencia to set
	 */
	public void setMesAnoReferencia(String mesAnoReferencia){

		this.mesAnoReferencia = mesAnoReferencia;
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