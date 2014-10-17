
package gcom.gerencial.bean;

import java.math.BigDecimal;


public class QuadroComparativoFaturamentoArrecadacaoHelper {

	private Integer idCategoria;

	private String descricaoCategoria;

	private BigDecimal valorContaFaturada;

	private BigDecimal valorContaIncluida;

	private BigDecimal valorContaCancelada;

	private BigDecimal valorContaCanceladaPorParcelamento;

	private BigDecimal valorContaPaga;

	private BigDecimal valorContaPagaEmDia;

	private BigDecimal valorParcelamentoCobradoContaFaturada;

	// Valores calculados

	private BigDecimal valorArrecadavel;

	private BigDecimal valorPendencia;

	private BigDecimal percentualRecebimentos;

	private BigDecimal percentualRecebimentoEmDia;

	public Integer getIdCategoria(){

		return idCategoria;
	}

	public void setIdCategoria(Integer idCategoria){

		this.idCategoria = idCategoria;
	}

	public String getDescricaoCategoria(){

		return descricaoCategoria;
	}

	public void setDescricaoCategoria(String descricaoCategoria){

		this.descricaoCategoria = descricaoCategoria;
	}

	public BigDecimal getValorContaFaturada(){

		return valorContaFaturada;
	}

	public void setValorContaFaturada(BigDecimal valorContaFaturada){

		this.valorContaFaturada = valorContaFaturada;
	}

	public BigDecimal getValorContaIncluida(){

		return valorContaIncluida;
	}

	public void setValorContaIncluida(BigDecimal valorContaIncluida){

		this.valorContaIncluida = valorContaIncluida;
	}

	public BigDecimal getValorContaCancelada(){

		return valorContaCancelada;
	}

	public void setValorContaCancelada(BigDecimal valorContaCancelada){

		this.valorContaCancelada = valorContaCancelada;
	}

	public BigDecimal getValorContaCanceladaPorParcelamento(){

		return valorContaCanceladaPorParcelamento;
	}

	public void setValorContaCanceladaPorParcelamento(BigDecimal valorContaCanceladaPorParcelamento){

		this.valorContaCanceladaPorParcelamento = valorContaCanceladaPorParcelamento;
	}

	public BigDecimal getValorContaPaga(){

		return valorContaPaga;
	}

	public void setValorContaPaga(BigDecimal valorContaPaga){

		this.valorContaPaga = valorContaPaga;
	}

	public BigDecimal getValorContaPagaEmDia(){

		return valorContaPagaEmDia;
	}

	public void setValorContaPagaEmDia(BigDecimal valorContaPagaEmDia){

		this.valorContaPagaEmDia = valorContaPagaEmDia;
	}

	public BigDecimal getValorParcelamentoCobradoContaFaturada(){

		return valorParcelamentoCobradoContaFaturada;
	}

	public void setValorParcelamentoCobradoContaFaturada(BigDecimal valorParcelamentoCobradoContaFaturada){

		this.valorParcelamentoCobradoContaFaturada = valorParcelamentoCobradoContaFaturada;
	}

	public BigDecimal getValorArrecadavel(){

		return valorArrecadavel;
	}

	public void setValorArrecadavel(BigDecimal valorArrecadavel){

		this.valorArrecadavel = valorArrecadavel;
	}

	public BigDecimal getValorPendencia(){

		return valorPendencia;
	}

	public void setValorPendencia(BigDecimal valorPendencia){

		this.valorPendencia = valorPendencia;
	}

	public BigDecimal getPercentualRecebimentos(){

		return percentualRecebimentos;
	}

	public void setPercentualRecebimentos(BigDecimal percentualRecebimentos){

		this.percentualRecebimentos = percentualRecebimentos;
	}

	public BigDecimal getPercentualRecebimentoEmDia(){

		return percentualRecebimentoEmDia;
	}

	public void setPercentualRecebimentoEmDia(BigDecimal percentualRecebimentoEmDia){

		this.percentualRecebimentoEmDia = percentualRecebimentoEmDia;
	}

}
