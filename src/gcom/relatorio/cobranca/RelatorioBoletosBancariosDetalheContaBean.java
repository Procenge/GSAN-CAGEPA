
package gcom.relatorio.cobranca;

import gcom.relatorio.RelatorioBean;

import java.math.BigDecimal;

/**
 * [UC3023] Manter Boleto Bancário
 * 
 * @author Hebert Falcão
 * @date 12/10/2011
 */
public class RelatorioBoletosBancariosDetalheContaBean
				implements RelatorioBean {

	private String referencia;

	private String dataVencimentoConta;

	private BigDecimal valorConta;

	public String getReferencia(){

		return referencia;
	}

	public void setReferencia(String referencia){

		this.referencia = referencia;
	}

	public String getDataVencimentoConta(){

		return dataVencimentoConta;
	}

	public void setDataVencimentoConta(String dataVencimentoConta){

		this.dataVencimentoConta = dataVencimentoConta;
	}

	public BigDecimal getValorConta(){

		return valorConta;
	}

	public void setValorConta(BigDecimal valorConta){

		this.valorConta = valorConta;
	}

}
