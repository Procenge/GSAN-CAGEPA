package gcom.faturamento.bean;

import gcom.faturamento.ConsumoFaixaCategoria;

import java.io.Serializable;
import java.math.BigDecimal;

public class EmitirHistogramaAguaEsgotoEconomiaDetalheHelper
				implements Serializable {

	private static final long serialVersionUID = 1L;

	private ConsumoFaixaCategoria faixa;

	private String descricaoFaixa = null;

	private long economiasMedido = 0;

	private BigDecimal consumoMedioMedido;

	private BigDecimal consumoExcedenteMedido;

	private long volumeConsumoFaixaMedido = 0;

	private long volumeFaturadoFaixaMedido = 0;

	private BigDecimal receitaMedido = new BigDecimal(0.0);

	private Long economiasNaoMedido = new Long("0");

	private BigDecimal consumoMedioNaoMedido;

	private BigDecimal consumoExcedenteNaoMedido;

	private long volumeConsumoFaixaNaoMedido = 0;

	private long volumeFaturadoFaixaNaoMedido = 0;

	private BigDecimal receitaNaoMedido = new BigDecimal(0.0);

	private boolean existeHistograma = true;

	private Long ligacoesMedido = new Long("0");

	private Long ligacoesNaoMedido = new Long("0");

	private short percentualEsgoto = 0;

	public ConsumoFaixaCategoria getFaixa(){

		return faixa;
	}

	public void setFaixa(ConsumoFaixaCategoria faixa){

		this.faixa = faixa;
	}

	public String getDescricaoFaixa(){

		return descricaoFaixa;
	}

	public void setDescricaoFaixa(String descricaoFaixa){

		this.descricaoFaixa = descricaoFaixa;
	}

	public long getEconomiasMedido(){

		return economiasMedido;
	}

	public void setEconomiasMedido(long economiasMedido){

		this.economiasMedido = economiasMedido;
	}

	public BigDecimal getConsumoMedioMedido(){

		return consumoMedioMedido;
	}

	public void setConsumoMedioMedido(BigDecimal consumoMedioMedido){

		this.consumoMedioMedido = consumoMedioMedido;
	}

	public BigDecimal getConsumoExcedenteMedido(){

		return consumoExcedenteMedido;
	}

	public void setConsumoExcedenteMedido(BigDecimal consumoExcedenteMedido){

		this.consumoExcedenteMedido = consumoExcedenteMedido;
	}

	public long getVolumeConsumoFaixaMedido(){

		return volumeConsumoFaixaMedido;
	}

	public void setVolumeConsumoFaixaMedido(long volumeConsumoFaixaMedido){

		this.volumeConsumoFaixaMedido = volumeConsumoFaixaMedido;
	}

	public long getVolumeFaturadoFaixaMedido(){

		return volumeFaturadoFaixaMedido;
	}

	public void setVolumeFaturadoFaixaMedido(long volumeFaturadoFaixaMedido){

		this.volumeFaturadoFaixaMedido = volumeFaturadoFaixaMedido;
	}

	public BigDecimal getReceitaMedido(){

		return receitaMedido;
	}

	public void setReceitaMedido(BigDecimal receitaMedido){

		this.receitaMedido = receitaMedido;
	}

	public Long getEconomiasNaoMedido(){

		return economiasNaoMedido;
	}

	public void setEconomiasNaoMedido(Long economiasNaoMedido){

		this.economiasNaoMedido = economiasNaoMedido;
	}

	public BigDecimal getConsumoMedioNaoMedido(){

		return consumoMedioNaoMedido;
	}

	public void setConsumoMedioNaoMedido(BigDecimal consumoMedioNaoMedido){

		this.consumoMedioNaoMedido = consumoMedioNaoMedido;
	}

	public BigDecimal getConsumoExcedenteNaoMedido(){

		return consumoExcedenteNaoMedido;
	}

	public void setConsumoExcedenteNaoMedido(BigDecimal consumoExcedenteNaoMedido){

		this.consumoExcedenteNaoMedido = consumoExcedenteNaoMedido;
	}

	public long getVolumeConsumoFaixaNaoMedido(){

		return volumeConsumoFaixaNaoMedido;
	}

	public void setVolumeConsumoFaixaNaoMedido(long volumeConsumoFaixaNaoMedido){

		this.volumeConsumoFaixaNaoMedido = volumeConsumoFaixaNaoMedido;
	}

	public long getVolumeFaturadoFaixaNaoMedido(){

		return volumeFaturadoFaixaNaoMedido;
	}

	public void setVolumeFaturadoFaixaNaoMedido(long volumeFaturadoFaixaNaoMedido){

		this.volumeFaturadoFaixaNaoMedido = volumeFaturadoFaixaNaoMedido;
	}

	public BigDecimal getReceitaNaoMedido(){

		return receitaNaoMedido;
	}

	public void setReceitaNaoMedido(BigDecimal receitaNaoMedido){

		this.receitaNaoMedido = receitaNaoMedido;
	}

	public boolean isExisteHistograma(){

		return existeHistograma;
	}

	public void setExisteHistograma(boolean existeHistograma){

		this.existeHistograma = existeHistograma;
	}

	public Long getLigacoesMedido(){

		return ligacoesMedido;
	}

	public void setLigacoesMedido(Long ligacoesMedido){

		this.ligacoesMedido = ligacoesMedido;
	}

	public Long getLigacoesNaoMedido(){

		return ligacoesNaoMedido;
	}

	public void setLigacoesNaoMedido(Long ligacoesNaoMedido){

		this.ligacoesNaoMedido = ligacoesNaoMedido;
	}

	public short getPercentualEsgoto(){

		return percentualEsgoto;
	}

	public void setPercentualEsgoto(short percentualEsgoto){

		this.percentualEsgoto = percentualEsgoto;
	}

}
