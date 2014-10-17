
package gcom.relatorio.cobranca.prescricao;

import gcom.relatorio.RelatorioBean;

import java.math.BigDecimal;

public class SubRelatorioDebitosPrescritosContaBean
				implements RelatorioBean {

	private String referencia;

	private String dataVencimento;

	private BigDecimal valor;

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

	public BigDecimal getValor(){

		return valor;
	}

	public void setValor(BigDecimal valor){

		this.valor = valor;
	}


}