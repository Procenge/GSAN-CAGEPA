
package gcom.relatorio.cobranca.cobrancaadministrativa;

import gcom.relatorio.RelatorioBean;

import java.math.BigDecimal;

/**
 * [UC3060] Manter Imóvel Cobrança Administrativa
 * 
 * @author Carlos Chrystian Ramos
 * @date 22/02/2013
 */
public class RelatorioImoveisEmCobrancaAdministrativaDetalheContaBean
				implements RelatorioBean {

	// 1.3.11.1.2.1.1. Referência da Conta
	private String referenciaConta;

	// 1.3.11.1.2.1.2. Data de Vencimento
	private String dataVencimentoConta;

	// 1.3.11.1.2.1.3. Valor da Conta
	private BigDecimal valorConta;

	// 1.3.11.1.2.1.4. Situação do Débito do Item
	private String descricaoSituacaoDebito;

	// 1.3.11.1.2.1.5. Data da Situação do Débito do Item
	private String dataSituacaoDebito;

	public String getReferenciaConta(){

		return referenciaConta;
	}

	public void setReferenciaConta(String referenciaConta){

		this.referenciaConta = referenciaConta;
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

	public String getDescricaoSituacaoDebito(){

		return descricaoSituacaoDebito;
	}

	public void setDescricaoSituacaoDebito(String descricaoSituacaoDebito){

		this.descricaoSituacaoDebito = descricaoSituacaoDebito;
	}

	public String getDataSituacaoDebito(){

		return dataSituacaoDebito;
	}

	public void setDataSituacaoDebito(String dataSituacaoDebito){

		this.dataSituacaoDebito = dataSituacaoDebito;
	}

}
