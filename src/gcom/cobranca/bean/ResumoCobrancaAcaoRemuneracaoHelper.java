
package gcom.cobranca.bean;

import java.math.BigDecimal;

public class ResumoCobrancaAcaoRemuneracaoHelper {

	private String descricaoDocumentoTipo;

	private BigDecimal percentualRemuneracao;

	private BigDecimal valorRemuneracao;

	/**
	 * 
	 */
	public ResumoCobrancaAcaoRemuneracaoHelper() {

		super();
	}


	/**
	 * @param descricaoDocumentoTipo
	 * @param percentualRemuneracao
	 * @param valorRemuneracao
	 */
	public ResumoCobrancaAcaoRemuneracaoHelper(String descricaoDocumentoTipo, BigDecimal percentualRemuneracao, BigDecimal valorRemuneracao) {

		super();
		this.descricaoDocumentoTipo = descricaoDocumentoTipo;
		this.percentualRemuneracao = percentualRemuneracao;
		this.valorRemuneracao = valorRemuneracao;
	}

	/**
	 * @return the descricaoDocumentoTipo
	 */
	public String getDescricaoDocumentoTipo(){

		return descricaoDocumentoTipo;
	}

	/**
	 * @param descricaoDocumentoTipo the descricaoDocumentoTipo to set
	 */
	public void setDescricaoDocumentoTipo(String descricaoDocumentoTipo){

		this.descricaoDocumentoTipo = descricaoDocumentoTipo;
	}

	/**
	 * @return the percentualRemuneracao
	 */
	public BigDecimal getPercentualRemuneracao(){

		return percentualRemuneracao;
	}

	/**
	 * @param percentualRemuneracao the percentualRemuneracao to set
	 */
	public void setPercentualRemuneracao(BigDecimal percentualRemuneracao){

		this.percentualRemuneracao = percentualRemuneracao;
	}

	/**
	 * @return the valorRemuneracao
	 */
	public BigDecimal getValorRemuneracao(){

		return valorRemuneracao;
	}

	/**
	 * @param valorRemuneracao the valorRemuneracao to set
	 */
	public void setValorRemuneracao(BigDecimal valorRemuneracao){

		this.valorRemuneracao = valorRemuneracao;
	}

}
