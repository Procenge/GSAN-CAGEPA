package gcom.micromedicao.bean;

import java.math.BigDecimal;


public class DebitoCreditoEnvioArquivoHelper {

	private Integer idTipo;

	private Integer anoMesReferencia;

	private Short numeroPrestacaoDebitoCredito;

	private Short numeroPrestacao;

	private BigDecimal valorPrestacao;

	private String descricaoTipoRubrica;

	private BigDecimal valorAgua;

	private BigDecimal valorEsgoto;

	public Integer getIdTipo(){

		return idTipo;
	}

	public void setIdTipo(Integer idTipo){

		this.idTipo = idTipo;
	}

	public Integer getAnoMesReferencia(){

		return anoMesReferencia;
	}

	public void setAnoMesReferencia(Integer anoMesReferencia){

		this.anoMesReferencia = anoMesReferencia;
	}

	public Short getNumeroPrestacaoDebitoCredito(){

		return numeroPrestacaoDebitoCredito;
	}

	public void setNumeroPrestacaoDebitoCredito(Short numeroPrestacaoDebitoCredito){

		this.numeroPrestacaoDebitoCredito = numeroPrestacaoDebitoCredito;
	}

	public Short getNumeroPrestacao(){

		return numeroPrestacao;
	}

	public void setNumeroPrestacao(Short numeroPrestacao){

		this.numeroPrestacao = numeroPrestacao;
	}

	public BigDecimal getValorPrestacao(){

		return valorPrestacao;
	}

	public void setValorPrestacao(BigDecimal valorPrestacao){

		this.valorPrestacao = valorPrestacao;
	}

	public String getDescricaoTipoRubrica(){

		return descricaoTipoRubrica;
	}

	public void setDescricaoTipoRubrica(String descricaoTipoRubrica){

		this.descricaoTipoRubrica = descricaoTipoRubrica;
	}

	public BigDecimal getValorAgua(){

		return valorAgua;
	}

	public void setValorAgua(BigDecimal valorAgua){

		this.valorAgua = valorAgua;
	}

	public BigDecimal getValorEsgoto(){

		return valorEsgoto;
	}

	public void setValorEsgoto(BigDecimal valorEsgoto){

		this.valorEsgoto = valorEsgoto;
	}

}
