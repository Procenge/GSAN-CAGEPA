
package gcom.relatorio.faturamento;

import gcom.relatorio.RelatorioBean;

/**
 * [UC0188] Manter Guia de Pagamento
 * 
 * @author Hugo Lima
 * @date 12/10/2011
 */
public class RelatorioManterGuiaPagamentoPrestacoesBean
				implements RelatorioBean {

	private String nuPrestacao;

	private String dsTipoDebito;

	private String nuValorPrestacao;

	private String dataEmissao;

	private String dataVencimento;

	private String dsSituacaoPagamento;

	private String dsSituacao;

	private String dataPagamento;

	private String nuValorPago;
	
	private String statusDividaAtiva;
	
	private String numeroProcessoAdministrativoExecucaoFiscal;

	public String getNumeroProcessoAdministrativoExecucaoFiscal(){

		return numeroProcessoAdministrativoExecucaoFiscal;
	}

	public void setNumeroProcessoAdministrativoExecucaoFiscal(String numeroProcessoAdministrativoExecucaoFiscal){

		this.numeroProcessoAdministrativoExecucaoFiscal = numeroProcessoAdministrativoExecucaoFiscal;
	}

	public String getStatusDividaAtiva(){

		return statusDividaAtiva;
	}

	public void setStatusDividaAtiva(String statusDividaAtiva){

		this.statusDividaAtiva = statusDividaAtiva;
	}	

	public String getNuPrestacao(){

		return nuPrestacao;
	}

	public void setNuPrestacao(String nuPrestacao){

		this.nuPrestacao = nuPrestacao;
	}

	public String getDsTipoDebito(){

		return dsTipoDebito;
	}

	public void setDsTipoDebito(String dsTipoDebito){

		this.dsTipoDebito = dsTipoDebito;
	}

	public String getNuValorPrestacao(){

		return nuValorPrestacao;
	}

	public void setNuValorPrestacao(String nuValorPrestacao){

		this.nuValorPrestacao = nuValorPrestacao;
	}

	public String getDataEmissao(){

		return dataEmissao;
	}

	public void setDataEmissao(String dataEmissao){

		this.dataEmissao = dataEmissao;
	}

	public String getDsSituacaoPagamento(){

		return dsSituacaoPagamento;
	}

	public void setDsSituacaoPagamento(String dsSituacaoPagamento){

		this.dsSituacaoPagamento = dsSituacaoPagamento;
	}

	public String getDsSituacao(){

		return dsSituacao;
	}

	public void setDsSituacao(String dsSituacao){

		this.dsSituacao = dsSituacao;
	}

	public String getDataVencimento(){

		return dataVencimento;
	}

	public void setDataVencimento(String dataVencimento){

		this.dataVencimento = dataVencimento;
	}

	public String getDataPagamento(){

		return dataPagamento;
	}

	public void setDataPagamento(String dataPagamento){

		this.dataPagamento = dataPagamento;
	}

	public String getNuValorPago(){

		return nuValorPago;
	}

	public void setNuValorPago(String nuValorPago){

		this.nuValorPago = nuValorPago;
	}

}
