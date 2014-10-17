package gcom.micromedicao.bean;

import gcom.faturamento.conta.Conta;

import java.math.BigDecimal;

public class DebitoAnteriorHelper {

	private Conta conta;

	private BigDecimal valorAcrescimos;

	public Conta getConta(){

		return conta;
	}

	public void setConta(Conta conta){

		this.conta = conta;
	}

	public BigDecimal getValorAcrescimos(){

		return valorAcrescimos;
	}

	public void setValorAcrescimos(BigDecimal valorAcrescimos){

		this.valorAcrescimos = valorAcrescimos;
	}

}
