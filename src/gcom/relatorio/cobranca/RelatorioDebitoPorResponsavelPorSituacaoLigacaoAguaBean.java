
package gcom.relatorio.cobranca;

import gcom.relatorio.RelatorioBean;

import java.math.BigDecimal;

public class RelatorioDebitoPorResponsavelPorSituacaoLigacaoAguaBean
				implements RelatorioBean {

	private Integer idLigacaoAguaSituacao;

	private String descricaoSituacao;

	private String quantidadeConsumidores;

	private BigDecimal valorNominalConsumidor;

	private BigDecimal valorCorrecaoConsumidor;

	private BigDecimal valorJurosConsumidor;

	private BigDecimal valorMultaConsumidor;

	private BigDecimal valorTotalConsumidor;

	public Integer getIdLigacaoAguaSituacao(){

		return idLigacaoAguaSituacao;
	}

	public void setIdLigacaoAguaSituacao(Integer idLigacaoAguaSituacao){

		this.idLigacaoAguaSituacao = idLigacaoAguaSituacao;
	}

	public String getDescricaoSituacao(){

		return descricaoSituacao;
	}

	public void setDescricaoSituacao(String descricaoSituacao){

		this.descricaoSituacao = descricaoSituacao;
	}

	public String getQuantidadeConsumidores(){

		return quantidadeConsumidores;
	}

	public void setQuantidadeConsumidores(String quantidadeConsumidores){

		this.quantidadeConsumidores = quantidadeConsumidores;
	}

	public BigDecimal getValorNominalConsumidor(){

		return valorNominalConsumidor;
	}

	public void setValorNominalConsumidor(BigDecimal valorNominalConsumidor){

		this.valorNominalConsumidor = valorNominalConsumidor;
	}

	public BigDecimal getValorCorrecaoConsumidor(){

		return valorCorrecaoConsumidor;
	}

	public void setValorCorrecaoConsumidor(BigDecimal valorCorrecaoConsumidor){

		this.valorCorrecaoConsumidor = valorCorrecaoConsumidor;
	}

	public BigDecimal getValorJurosConsumidor(){

		return valorJurosConsumidor;
	}

	public void setValorJurosConsumidor(BigDecimal valorJurosConsumidor){

		this.valorJurosConsumidor = valorJurosConsumidor;
	}

	public BigDecimal getValorMultaConsumidor(){

		return valorMultaConsumidor;
	}

	public void setValorMultaConsumidor(BigDecimal valorMultaConsumidor){

		this.valorMultaConsumidor = valorMultaConsumidor;
	}

	public BigDecimal getValorTotalConsumidor(){

		return valorTotalConsumidor;
	}

	public void setValorTotalConsumidor(BigDecimal valorTotalConsumidor){

		this.valorTotalConsumidor = valorTotalConsumidor;
	}

}