
package gcom.cobranca.opcaoparcelamento;

import gcom.cobranca.parcelamento.ParcelamentoPerfil;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class PreParcelamentoOpcao
				implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	PreParcelamento preParcelamento;

	private Short posicaoOpcao;

	private BigDecimal valorEntrada;

	private BigDecimal valorJurosParcelamento;

	private Integer numeroPrestacoes;

	private BigDecimal valorPrestacao;

	private BigDecimal percentualDescontoAcrescimoImpontualidade;

	private BigDecimal percentualDescontoAntiguidade;

	private BigDecimal percentualDescontoInatividade;

	private BigDecimal valorAtualizacaoMonetaria;

	private BigDecimal valorJurosMora;

	private BigDecimal valorMulta;

	private BigDecimal valorDebitoAtualizado;

	private BigDecimal valorDescontosAcrescimosImpontualidade;

	private BigDecimal valorDescontosAcrescimosAntiguidade;

	private BigDecimal valorDescontosAcrescimosInatividade;

	private ParcelamentoPerfil parcelamentoPerfil;

	private BigDecimal valorDescontoSancoesRDEspecial;

	private BigDecimal valorDescontoTarifaSocialRDEspecial;

	private Date ultimaAlteracao;

	private Integer numeroMesesEntreParcelas;

	private Integer numeroParcelasALancar;

	private Integer numeroMesesInicioCobranca;

	private Integer numeroDiasVencimentoDaEntrada;

	public Integer getId(){

		return id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public PreParcelamento getPreParcelamento(){

		return preParcelamento;
	}

	public void setPreParcelamento(PreParcelamento preParcelamento){

		this.preParcelamento = preParcelamento;
	}

	public Short getPosicaoOpcao(){

		return posicaoOpcao;
	}

	public void setPosicaoOpcao(Short posicaoOpcao){

		this.posicaoOpcao = posicaoOpcao;
	}

	public BigDecimal getValorEntrada(){

		return valorEntrada;
	}

	public void setValorEntrada(BigDecimal valorEntrada){

		this.valorEntrada = valorEntrada;
	}

	public BigDecimal getValorJurosParcelamento(){

		return valorJurosParcelamento;
	}

	public void setValorJurosParcelamento(BigDecimal valorJurosParcelamento){

		this.valorJurosParcelamento = valorJurosParcelamento;
	}

	public Integer getNumeroPrestacoes(){

		return numeroPrestacoes;
	}

	public void setNumeroPrestacoes(Integer numeroPrestacoes){

		this.numeroPrestacoes = numeroPrestacoes;
	}

	public BigDecimal getValorPrestacao(){

		return valorPrestacao;
	}

	public void setValorPrestacao(BigDecimal valorPrestacao){

		this.valorPrestacao = valorPrestacao;
	}

	public BigDecimal getPercentualDescontoAcrescimoImpontualidade(){

		return percentualDescontoAcrescimoImpontualidade;
	}

	public void setPercentualDescontoAcrescimoImpontualidade(BigDecimal percentualDescontoAcrescimoImpontualidade){

		this.percentualDescontoAcrescimoImpontualidade = percentualDescontoAcrescimoImpontualidade;
	}

	public BigDecimal getPercentualDescontoAntiguidade(){

		return percentualDescontoAntiguidade;
	}

	public void setPercentualDescontoAntiguidade(BigDecimal percentualDescontoAntiguidade){

		this.percentualDescontoAntiguidade = percentualDescontoAntiguidade;
	}

	public BigDecimal getPercentualDescontoInatividade(){

		return percentualDescontoInatividade;
	}

	public void setPercentualDescontoInatividade(BigDecimal percentualDescontoInatividade){

		this.percentualDescontoInatividade = percentualDescontoInatividade;
	}

	public BigDecimal getValorAtualizacaoMonetaria(){

		return valorAtualizacaoMonetaria;
	}

	public void setValorAtualizacaoMonetaria(BigDecimal valorAtualizacaoMonetaria){

		this.valorAtualizacaoMonetaria = valorAtualizacaoMonetaria;
	}

	public BigDecimal getValorJurosMora(){

		return valorJurosMora;
	}

	public void setValorJurosMora(BigDecimal valorJurosMora){

		this.valorJurosMora = valorJurosMora;
	}

	public BigDecimal getValorMulta(){

		return valorMulta;
	}

	public void setValorMulta(BigDecimal valorMulta){

		this.valorMulta = valorMulta;
	}

	public BigDecimal getValorDebitoAtualizado(){

		return valorDebitoAtualizado;
	}

	public void setValorDebitoAtualizado(BigDecimal valorDebitoAtualizado){

		this.valorDebitoAtualizado = valorDebitoAtualizado;
	}

	public BigDecimal getValorDescontosAcrescimosImpontualidade(){

		return valorDescontosAcrescimosImpontualidade;
	}

	public void setValorDescontosAcrescimosImpontualidade(BigDecimal valorDescontosAcrescimosImpontualidade){

		this.valorDescontosAcrescimosImpontualidade = valorDescontosAcrescimosImpontualidade;
	}

	public BigDecimal getValorDescontosAcrescimosAntiguidade(){

		return valorDescontosAcrescimosAntiguidade;
	}

	public void setValorDescontosAcrescimosAntiguidade(BigDecimal valorDescontosAcrescimosAntiguidade){

		this.valorDescontosAcrescimosAntiguidade = valorDescontosAcrescimosAntiguidade;
	}

	public BigDecimal getValorDescontosAcrescimosInatividade(){

		return valorDescontosAcrescimosInatividade;
	}

	public void setValorDescontosAcrescimosInatividade(BigDecimal valorDescontosAcrescimosInatividade){

		this.valorDescontosAcrescimosInatividade = valorDescontosAcrescimosInatividade;
	}

	public Date getUltimaAlteracao(){

		return ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	/**
	 * @param parcelamentoPerfil
	 *            the parcelamentoPerfil to set
	 */
	public void setParcelamentoPerfil(ParcelamentoPerfil parcelamentoPerfil){

		this.parcelamentoPerfil = parcelamentoPerfil;
	}

	/**
	 * @return the parcelamentoPerfil
	 */
	public ParcelamentoPerfil getParcelamentoPerfil(){

		return parcelamentoPerfil;
	}

	public BigDecimal getValorDescontoSancoesRDEspecial(){

		return valorDescontoSancoesRDEspecial;
	}

	public void setValorDescontoSancoesRDEspecial(BigDecimal valorDescontoSancoesRDEspecial){

		this.valorDescontoSancoesRDEspecial = valorDescontoSancoesRDEspecial;
	}

	public BigDecimal getValorDescontoTarifaSocialRDEspecial(){

		return valorDescontoTarifaSocialRDEspecial;
	}

	public void setValorDescontoTarifaSocialRDEspecial(BigDecimal valorDescontoTarifaSocialRDEspecial){

		this.valorDescontoTarifaSocialRDEspecial = valorDescontoTarifaSocialRDEspecial;
	}

	public Integer getNumeroMesesEntreParcelas(){

		return numeroMesesEntreParcelas;
	}

	public void setNumeroMesesEntreParcelas(Integer numeroMesesEntreParcelas){

		this.numeroMesesEntreParcelas = numeroMesesEntreParcelas;
	}

	public Integer getNumeroParcelasALancar(){

		return numeroParcelasALancar;
	}

	public void setNumeroParcelasALancar(Integer numeroParcelasALancar){

		this.numeroParcelasALancar = numeroParcelasALancar;
	}

	public Integer getNumeroMesesInicioCobranca(){

		return numeroMesesInicioCobranca;
	}

	public void setNumeroMesesInicioCobranca(Integer numeroMesesInicioCobranca){

		this.numeroMesesInicioCobranca = numeroMesesInicioCobranca;
	}

	public Integer getNumeroDiasVencimentoDaEntrada(){

		return numeroDiasVencimentoDaEntrada;
	}

	public void setNumeroDiasVencimentoDaEntrada(Integer numeroDiasVencimentoDaEntrada){

		this.numeroDiasVencimentoDaEntrada = numeroDiasVencimentoDaEntrada;
	}

}