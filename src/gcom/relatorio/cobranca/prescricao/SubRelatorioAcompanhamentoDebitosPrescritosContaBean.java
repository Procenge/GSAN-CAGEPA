
package gcom.relatorio.cobranca.prescricao;

import gcom.relatorio.RelatorioBean;

import java.math.BigDecimal;

public class SubRelatorioAcompanhamentoDebitosPrescritosContaBean
				implements RelatorioBean {

	private String referencia;

	private String dataVencimento;

	private BigDecimal valorAgua;

	private BigDecimal valorEsgoto;

	private BigDecimal valorDebitos;

	private BigDecimal valorCreditos;

	private BigDecimal valorImpostos;

	private BigDecimal valorTotalConta;

	private String descricaoDebitoCreditoSituacao;

	private String indicadorPaga;

	public String getReferencia(){

		return referencia;
	}

	public void setReferencia(String referencia){

		this.referencia = referencia;
	}

	public String getDataVencimento(){

		return dataVencimento;
	}

	public void setDataVencimento(String dataVencimento){

		this.dataVencimento = dataVencimento;
	}

	public BigDecimal getValorAgua(){

		return valorAgua;
	}

	public void setValorAgua(BigDecimal valorAgua){

		this.valorAgua = valorAgua;
	}

	public BigDecimal getValorEsgoto(){

		return valorEsgoto;
	}

	public void setValorEsgoto(BigDecimal valorEsgoto){

		this.valorEsgoto = valorEsgoto;
	}

	public BigDecimal getValorDebitos(){

		return valorDebitos;
	}

	public void setValorDebitos(BigDecimal valorDebitos){

		this.valorDebitos = valorDebitos;
	}

	public BigDecimal getValorCreditos(){

		return valorCreditos;
	}

	public void setValorCreditos(BigDecimal valorCreditos){

		this.valorCreditos = valorCreditos;
	}

	public BigDecimal getValorImpostos(){

		return valorImpostos;
	}

	public void setValorImpostos(BigDecimal valorImpostos){

		this.valorImpostos = valorImpostos;
	}

	public BigDecimal getValorTotalConta(){

		return valorTotalConta;
	}

	public void setValorTotalConta(BigDecimal valorTotalConta){

		this.valorTotalConta = valorTotalConta;
	}

	public String getDescricaoDebitoCreditoSituacao(){

		return descricaoDebitoCreditoSituacao;
	}

	public void setDescricaoDebitoCreditoSituacao(String descricaoDebitoCreditoSituacao){

		this.descricaoDebitoCreditoSituacao = descricaoDebitoCreditoSituacao;
	}

	public String getIndicadorPaga(){

		return indicadorPaga;
	}

	public void setIndicadorPaga(String indicadorPaga){

		this.indicadorPaga = indicadorPaga;
	}



}