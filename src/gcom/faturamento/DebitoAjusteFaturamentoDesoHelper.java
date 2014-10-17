
package gcom.faturamento;

import gcom.faturamento.debito.DebitoTipo;

import java.math.BigDecimal;

public class DebitoAjusteFaturamentoDesoHelper {

	private DebitoTipo debitoTipo;

	private BigDecimal valorEnviado;

	private Short numeroPrestacao;

	private Short numeroPrestacaoDebito;

	private BigDecimal valorRestanteSerCobrado;

	private BigDecimal valorResidualAnteriorCobrado;

	private BigDecimal valorFaturado;

	public DebitoAjusteFaturamentoDesoHelper() {

	}

	public DebitoTipo getDebitoTipo(){

		return debitoTipo;
	}

	public void setDebitoTipo(DebitoTipo debitoTipo){

		this.debitoTipo = debitoTipo;
	}

	public BigDecimal getValorEnviado(){

		return valorEnviado;
	}

	public void setValorEnviado(BigDecimal valorEnviado){

		this.valorEnviado = valorEnviado;
	}

	public Short getNumeroPrestacao(){

		return numeroPrestacao;
	}

	public void setNumeroPrestacao(Short numeroPrestacao){

		this.numeroPrestacao = numeroPrestacao;
	}

	public Short getNumeroPrestacaoDebito(){

		return numeroPrestacaoDebito;
	}

	public void setNumeroPrestacaoDebito(Short numeroPrestacaoDebito){

		this.numeroPrestacaoDebito = numeroPrestacaoDebito;
	}

	public BigDecimal getValorRestanteSerCobrado(){

		return valorRestanteSerCobrado;
	}

	public void setValorRestanteSerCobrado(BigDecimal valorRestanteSerCobrado){

		this.valorRestanteSerCobrado = valorRestanteSerCobrado;
	}

	public BigDecimal getValorResidualAnteriorCobrado(){

		return valorResidualAnteriorCobrado;
	}

	public void setValorResidualAnteriorCobrado(BigDecimal valorResidualAnteriorCobrado){

		this.valorResidualAnteriorCobrado = valorResidualAnteriorCobrado;
	}

	public BigDecimal getValorFaturado(){

		return valorFaturado;
	}

	public void setValorFaturado(BigDecimal valorFaturado){

		this.valorFaturado = valorFaturado;
	}

}
