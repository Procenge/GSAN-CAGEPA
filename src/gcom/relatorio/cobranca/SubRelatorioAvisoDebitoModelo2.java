
package gcom.relatorio.cobranca;

import gcom.relatorio.RelatorioBean;

public class SubRelatorioAvisoDebitoModelo2
				implements RelatorioBean {

	private String fatura;

	private String valor;

	private String dataVencimento;

	private String numeroSequencialDocumento;

	public SubRelatorioAvisoDebitoModelo2(String fatura, String dataVencimento, String valor, String numeroSequencialDocumento) {

		this.fatura = fatura;
		this.valor = valor;
		this.dataVencimento = dataVencimento;
		this.numeroSequencialDocumento = numeroSequencialDocumento;
	}

	public SubRelatorioAvisoDebitoModelo2(String[] linhaFatura, String[] linhaDataVencimento, String[] linhaValor) {

		this.fatura = linhaFatura[0];
		this.valor = linhaValor[0];
		this.dataVencimento = linhaDataVencimento[0];
	}

	/**
	 * @return the fatura
	 */
	public String getFatura(){

		return fatura;
	}

	/**
	 * @param fatura
	 *            the fatura to set
	 */
	public void setFatura(String fatura){

		this.fatura = fatura;
	}

	/**
	 * @return the valor
	 */
	public String getValor(){

		return valor;
	}

	/**
	 * @param valor
	 *            the valor to set
	 */
	public void setValor(String valor){

		this.valor = valor;
	}

	/**
	 * @return the dataVencimento
	 */
	public String getDataVencimento(){

		return dataVencimento;
	}

	/**
	 * @param dataVencimento
	 *            the dataVencimento to set
	 */
	public void setDataVencimento(String dataVencimento){

		this.dataVencimento = dataVencimento;
	}

	public String getNumeroSequencialDocumento(){

		return numeroSequencialDocumento;
	}

	public void setNumeroSequencialDocumento(String numeroSequencialDocumento){

		this.numeroSequencialDocumento = numeroSequencialDocumento;
	}

}
