package gcom.relatorio.cobranca;

import gcom.relatorio.RelatorioBean;

import java.math.BigDecimal;

public class RelatorioRemuneracaoCobrancaAdministrativaBean
				implements RelatorioBean {

	private String dataPagamento;

	private Integer localidadeId;

	private String matricula;

	private Integer setor;

	private String referencia;

	private BigDecimal valorTotalDebito;

	private BigDecimal valorTotalServicosEspeciais;

	private BigDecimal valorTotalServicosParcelamento;

	private BigDecimal valorTotalDebitoSemServicosEspeciaisParcelamento;

	private BigDecimal padraoValor;

	private BigDecimal padraoPercentual;

	private BigDecimal servicosEspeciaisValor;

	private BigDecimal servicosEspeciaisPercentual;

	private BigDecimal parcelamentoValor;

	private BigDecimal parcelamentoPercentual;

	private BigDecimal reincidenciaValor;

	private BigDecimal reincidenciaPercentual;

	private BigDecimal valorTotalRemuneracao;

	private String situacaoRemuneracao;

	public String getDataPagamento(){

		return dataPagamento;
	}

	public void setDataPagamento(String dataPagamento){

		this.dataPagamento = dataPagamento;
	}

	public Integer getLocalidadeId(){

		return localidadeId;
	}

	public void setLocalidadeId(Integer localidadeId){

		this.localidadeId = localidadeId;
	}

	public String getMatricula(){

		return matricula;
	}

	public void setMatricula(String matricula){

		this.matricula = matricula;
	}

	public Integer getSetor(){

		return setor;
	}

	public void setSetor(Integer setor){

		this.setor = setor;
	}

	public String getReferencia(){

		return referencia;
	}

	public void setReferencia(String referencia){

		this.referencia = referencia;
	}

	public BigDecimal getValorTotalDebito(){

		return valorTotalDebito;
	}

	public void setValorTotalDebito(BigDecimal valorTotalDebito){

		this.valorTotalDebito = valorTotalDebito;
	}

	public BigDecimal getValorTotalServicosEspeciais(){

		return valorTotalServicosEspeciais;
	}

	public void setValorTotalServicosEspeciais(BigDecimal valorTotalServicosEspeciais){

		this.valorTotalServicosEspeciais = valorTotalServicosEspeciais;
	}

	public BigDecimal getValorTotalServicosParcelamento(){

		return valorTotalServicosParcelamento;
	}

	public void setValorTotalServicosParcelamento(BigDecimal valorTotalServicosParcelamento){

		this.valorTotalServicosParcelamento = valorTotalServicosParcelamento;
	}

	public BigDecimal getValorTotalDebitoSemServicosEspeciaisParcelamento(){

		return valorTotalDebitoSemServicosEspeciaisParcelamento;
	}

	public void setValorTotalDebitoSemServicosEspeciaisParcelamento(BigDecimal valorTotalDebitoSemServicosEspeciaisParcelamento){

		this.valorTotalDebitoSemServicosEspeciaisParcelamento = valorTotalDebitoSemServicosEspeciaisParcelamento;
	}

	public BigDecimal getPadraoValor(){

		return padraoValor;
	}

	public void setPadraoValor(BigDecimal padraoValor){

		this.padraoValor = padraoValor;
	}

	public BigDecimal getPadraoPercentual(){

		return padraoPercentual;
	}

	public void setPadraoPercentual(BigDecimal padraoPercentual){

		this.padraoPercentual = padraoPercentual;
	}

	public BigDecimal getServicosEspeciaisValor(){

		return servicosEspeciaisValor;
	}

	public void setServicosEspeciaisValor(BigDecimal servicosEspeciaisValor){

		this.servicosEspeciaisValor = servicosEspeciaisValor;
	}

	public BigDecimal getServicosEspeciaisPercentual(){

		return servicosEspeciaisPercentual;
	}

	public void setServicosEspeciaisPercentual(BigDecimal servicosEspeciaisPercentual){

		this.servicosEspeciaisPercentual = servicosEspeciaisPercentual;
	}

	public BigDecimal getParcelamentoValor(){

		return parcelamentoValor;
	}

	public void setParcelamentoValor(BigDecimal parcelamentoValor){

		this.parcelamentoValor = parcelamentoValor;
	}

	public BigDecimal getParcelamentoPercentual(){

		return parcelamentoPercentual;
	}

	public void setParcelamentoPercentual(BigDecimal parcelamentoPercentual){

		this.parcelamentoPercentual = parcelamentoPercentual;
	}

	public BigDecimal getReincidenciaValor(){

		return reincidenciaValor;
	}

	public void setReincidenciaValor(BigDecimal reincidenciaValor){

		this.reincidenciaValor = reincidenciaValor;
	}

	public BigDecimal getReincidenciaPercentual(){

		return reincidenciaPercentual;
	}

	public void setReincidenciaPercentual(BigDecimal reincidenciaPercentual){

		this.reincidenciaPercentual = reincidenciaPercentual;
	}

	public BigDecimal getValorTotalRemuneracao(){

		return valorTotalRemuneracao;
	}

	public void setValorTotalRemuneracao(BigDecimal valorTotalRemuneracao){

		this.valorTotalRemuneracao = valorTotalRemuneracao;
	}

	public String getSituacaoRemuneracao(){

		return situacaoRemuneracao;
	}

	public void setSituacaoRemuneracao(String situacaoRemuneracao){

		this.situacaoRemuneracao = situacaoRemuneracao;
	}

}
