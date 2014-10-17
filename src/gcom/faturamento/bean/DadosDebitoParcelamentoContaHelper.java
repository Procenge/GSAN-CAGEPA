package gcom.faturamento.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class DadosDebitoParcelamentoContaHelper
				implements Serializable {

	private static final long serialVersionUID = 1L;

	private Short prestacao;

	private Short prestacaoDebito;

	private BigDecimal valorDebito;

	public DadosDebitoParcelamentoContaHelper() {

	}

	public Short getPrestacao(){

		return prestacao;
	}

	public void setPrestacao(Short prestacao){

		this.prestacao = prestacao;
	}

	public Short getPrestacaoDebito(){

		return prestacaoDebito;
	}

	public void setPrestacaoDebito(Short prestacaoDebito){

		this.prestacaoDebito = prestacaoDebito;
	}

	public BigDecimal getValorDebito(){

		return valorDebito;
	}

	public void setValorDebito(BigDecimal valorDebito){

		this.valorDebito = valorDebito;
	}

}
