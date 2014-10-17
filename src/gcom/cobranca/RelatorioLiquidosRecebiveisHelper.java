
package gcom.cobranca;

import gcom.relatorio.RelatorioBean;

public class RelatorioLiquidosRecebiveisHelper
				implements RelatorioBean {

	public static final String REFERENCIA_LIQUIDO_RECEBIVEIS = "201006";

	private static final long serialVersionUID = 1L;

	private String dataPagamento; // Usado para agrupamento Analítico.

	private String dataPagamentoQuebra;

	private String matricula;

	private String referencia;

	private String nome;

	private String valorAgua;

	private String valorEsgoto;

	private String valorServico;

	private String valorConta;

	// Sintético.
	private String referenciaFaturamento;

	private String quantidadesContas;

	/**
	 * @return the dataPagamento
	 */
	public String getDataPagamento(){

		return dataPagamento;
	}

	/**
	 * @param dataPagamento
	 *            the dataPagamento to set
	 */
	public void setDataPagamento(String dataPagamento){

		this.dataPagamento = dataPagamento;
	}

	/**
	 * @return the matricula
	 */
	public String getMatricula(){

		return matricula;
	}

	/**
	 * @param matricula
	 *            the matricula to set
	 */
	public void setMatricula(String matricula){

		this.matricula = matricula;
	}

	/**
	 * @return the referencia
	 */
	public String getReferencia(){

		return referencia;
	}

	/**
	 * @param referencia
	 *            the referencia to set
	 */
	public void setReferencia(String referencia){

		this.referencia = referencia;
	}

	/**
	 * @return the nome
	 */
	public String getNome(){

		return nome;
	}

	/**
	 * @param nome
	 *            the nome to set
	 */
	public void setNome(String nome){

		this.nome = nome;
	}

	/**
	 * @return the valorAgua
	 */
	public String getValorAgua(){

		return valorAgua;
	}

	/**
	 * @param valorAgua
	 *            the valorAgua to set
	 */
	public void setValorAgua(String valorAgua){

		this.valorAgua = valorAgua;
	}

	/**
	 * @return the valorEsgoto
	 */
	public String getValorEsgoto(){

		return valorEsgoto;
	}

	/**
	 * @param valorEsgoto
	 *            the valorEsgoto to set
	 */
	public void setValorEsgoto(String valorEsgoto){

		this.valorEsgoto = valorEsgoto;
	}

	/**
	 * @return the valorServico
	 */
	public String getValorServico(){

		return valorServico;
	}

	/**
	 * @param valorServico
	 *            the valorServico to set
	 */
	public void setValorServico(String valorServico){

		this.valorServico = valorServico;
	}

	/**
	 * @return the valorConta
	 */
	public String getValorConta(){

		return valorConta;
	}

	/**
	 * @param valorConta
	 *            the valorConta to set
	 */
	public void setValorConta(String valorConta){

		this.valorConta = valorConta;
	}

	/**
	 * @return the referenciaFaturamento
	 */
	public String getReferenciaFaturamento(){

		return referenciaFaturamento;
	}

	/**
	 * @param referenciaFaturamento
	 *            the referenciaFaturamento to set
	 */
	public void setReferenciaFaturamento(String referenciaFaturamento){

		this.referenciaFaturamento = referenciaFaturamento;
	}

	/**
	 * @return the quantidadesContas
	 */
	public String getQuantidadesContas(){

		return quantidadesContas;
	}

	/**
	 * @param quantidadesContas
	 *            the quantidadesContas to set
	 */
	public void setQuantidadesContas(String quantidadesContas){

		this.quantidadesContas = quantidadesContas;
	}

	/**
	 * @return the dataPagamentoQuebra
	 */
	public String getDataPagamentoQuebra(){

		return dataPagamentoQuebra;
	}

	/**
	 * @param dataPagamentoQuebra
	 *            the dataPagamentoQuebra to set
	 */
	public void setDataPagamentoQuebra(String dataPagamentoQuebra){

		this.dataPagamentoQuebra = dataPagamentoQuebra;
	}

}
