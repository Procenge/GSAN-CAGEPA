package gcom.faturamento.credito;

import java.math.BigDecimal;

public class CreditoARealizarHelper {

	private BigDecimal valorParcelaCredito;

	private CreditoARealizar creditoARealizar;

	public CreditoARealizarHelper() {

	}

	public CreditoARealizarHelper(BigDecimal valorParcelaCredito, CreditoARealizar creditoARealizar) {

		super();
		this.valorParcelaCredito = valorParcelaCredito;
		this.creditoARealizar = creditoARealizar;
	}

	public BigDecimal getValorParcelaCredito(){

		return valorParcelaCredito;
	}

	public void setValorParcelaCredito(BigDecimal valorParcelaCredito){

		this.valorParcelaCredito = valorParcelaCredito;
	}

	public CreditoARealizar getCreditoARealizar(){

		return creditoARealizar;
	}

	public void setCreditoARealizar(CreditoARealizar creditoARealizar){

		this.creditoARealizar = creditoARealizar;
	}

}
