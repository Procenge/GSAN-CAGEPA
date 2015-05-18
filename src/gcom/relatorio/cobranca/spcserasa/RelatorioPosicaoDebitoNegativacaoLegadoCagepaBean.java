
package gcom.relatorio.cobranca.spcserasa;

import gcom.relatorio.RelatorioBean;

import java.math.BigDecimal;

/**
 * [UC3165] Gerar Relatório Posição do Débito da Negativação - Legado CAGEPA
 * 
 * @author Luciano Galvão
 * @date 07/03/2015
 */
public class RelatorioPosicaoDebitoNegativacaoLegadoCagepaBean
				implements RelatorioBean {

	private String idImovel;

	private String idImovelAnterior;

	private String referenciaConta;

	private BigDecimal valorConta;

	private String situacao;

	private String dataPagamento;

	private BigDecimal valorPagamento;

	private String dataCancelamento;

	private String motivoCancelamento;

	private String dataParcelamento;

	public String getIdImovelAnterior(){

		return idImovelAnterior;
	}

	public void setIdImovelAnterior(String idImovelAnterior){

		this.idImovelAnterior = idImovelAnterior;
	}

	public String getIdImovel(){

		return idImovel;
	}

	public void setIdImovel(String idImovel){

		this.idImovel = idImovel;
	}

	public String getReferenciaConta(){

		return referenciaConta;
	}

	public void setReferenciaConta(String referenciaConta){

		this.referenciaConta = referenciaConta;
	}

	public BigDecimal getValorConta(){

		return valorConta;
	}

	public void setValorConta(BigDecimal valorConta){

		this.valorConta = valorConta;
	}

	public String getSituacao(){

		return situacao;
	}

	public void setSituacao(String situacao){

		this.situacao = situacao;
	}

	public String getDataPagamento(){

		return dataPagamento;
	}

	public void setDataPagamento(String dataPagamento){

		this.dataPagamento = dataPagamento;
	}

	public BigDecimal getValorPagamento(){

		return valorPagamento;
	}

	public void setValorPagamento(BigDecimal valorPagamento){

		this.valorPagamento = valorPagamento;
	}

	public String getDataCancelamento(){

		return dataCancelamento;
	}

	public void setDataCancelamento(String dataCancelamento){

		this.dataCancelamento = dataCancelamento;
	}

	public String getMotivoCancelamento(){

		return motivoCancelamento;
	}

	public void setMotivoCancelamento(String motivoCancelamento){

		this.motivoCancelamento = motivoCancelamento;
	}

	public String getDataParcelamento(){

		return dataParcelamento;
	}

	public void setDataParcelamento(String dataParcelamento){

		this.dataParcelamento = dataParcelamento;
	}

}
