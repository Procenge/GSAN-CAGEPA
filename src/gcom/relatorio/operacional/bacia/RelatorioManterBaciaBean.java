
package gcom.relatorio.operacional.bacia;

import gcom.relatorio.RelatorioBean;

/**
 * @author isilva
 * @date 03/02/2011
 */
public class RelatorioManterBaciaBean
				implements RelatorioBean {

	private String codigoBacia;

	private String descricaoBacia;

	private String descricaoAbreviadaBacia;

	private String descricaoCodigoSistemaEsgoto;

	private String descricaoCodigoSubsistemaEsgoto;

	private String indicadorUso;

	public RelatorioManterBaciaBean() {

	}

	public RelatorioManterBaciaBean(String codigoBacia, String descricaoBacia, String descricaoAbreviadaBacia,
									String descricaoCodigoSistemaEsgoto, String descricaoCodigoSubsistemaEsgoto, String indicadorUso) {

		this.codigoBacia = codigoBacia;
		this.descricaoBacia = descricaoBacia;
		this.descricaoAbreviadaBacia = descricaoAbreviadaBacia;
		this.descricaoCodigoSistemaEsgoto = descricaoCodigoSistemaEsgoto;
		this.descricaoCodigoSubsistemaEsgoto = descricaoCodigoSubsistemaEsgoto;
		this.indicadorUso = indicadorUso;
	}

	public RelatorioManterBaciaBean(String codigoBacia, String descricaoBacia, String descricaoAbreviadaBacia,
									String descricaoCodigoSistemaEsgoto, String descricaoCodigoSubsistemaEsgoto) {

		this.codigoBacia = codigoBacia;
		this.descricaoBacia = descricaoBacia;
		this.descricaoAbreviadaBacia = descricaoAbreviadaBacia;
		this.descricaoCodigoSistemaEsgoto = descricaoCodigoSistemaEsgoto;
		this.descricaoCodigoSubsistemaEsgoto = descricaoCodigoSubsistemaEsgoto;
	}

	/**
	 * @return the codigoBacia
	 */
	public String getCodigoBacia(){

		return codigoBacia;
	}

	/**
	 * @param codigoBacia
	 *            the codigoBacia to set
	 */
	public void setCodigoBacia(String codigoBacia){

		this.codigoBacia = codigoBacia;
	}

	/**
	 * @return the descricaoBacia
	 */
	public String getDescricaoBacia(){

		return descricaoBacia;
	}

	/**
	 * @param descricaoBacia
	 *            the descricaoBacia to set
	 */
	public void setDescricaoBacia(String descricaoBacia){

		this.descricaoBacia = descricaoBacia;
	}

	/**
	 * @return the descricaoAbreviadaBacia
	 */
	public String getDescricaoAbreviadaBacia(){

		return descricaoAbreviadaBacia;
	}

	/**
	 * @param descricaoAbreviadaBacia
	 *            the descricaoAbreviadaBacia to set
	 */
	public void setDescricaoAbreviadaBacia(String descricaoAbreviadaBacia){

		this.descricaoAbreviadaBacia = descricaoAbreviadaBacia;
	}

	/**
	 * @return the descricaoCodigoSistemaEsgoto
	 */
	public String getDescricaoCodigoSistemaEsgoto(){

		return descricaoCodigoSistemaEsgoto;
	}

	/**
	 * @param descricaoCodigoSistemaEsgoto
	 *            the descricaoCodigoSistemaEsgoto to set
	 */
	public void setDescricaoCodigoSistemaEsgoto(String descricaoCodigoSistemaEsgoto){

		this.descricaoCodigoSistemaEsgoto = descricaoCodigoSistemaEsgoto;
	}

	/**
	 * @return the descricaoCodigoSubsistemaEsgoto
	 */
	public String getDescricaoCodigoSubsistemaEsgoto(){

		return descricaoCodigoSubsistemaEsgoto;
	}

	/**
	 * @param descricaoCodigoSubsistemaEsgoto
	 *            the descricaoCodigoSubsistemaEsgoto to set
	 */
	public void setDescricaoCodigoSubsistemaEsgoto(String descricaoCodigoSubsistemaEsgoto){

		this.descricaoCodigoSubsistemaEsgoto = descricaoCodigoSubsistemaEsgoto;
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