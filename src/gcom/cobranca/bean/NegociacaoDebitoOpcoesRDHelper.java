
package gcom.cobranca.bean;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * [UC3031] Efetuar Negociação de Débitos
 * 
 * @author Hebert Falcão
 * @date 01/12/2011
 */
public class NegociacaoDebitoOpcoesRDHelper
				implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer idResolucaoDiretoria;

	private String numeroResolucaoDiretoria;

	private BigDecimal valorDebitoTotalAtualizado;

	private BigDecimal percentualTotalDesconto;

	private BigDecimal valorTotalDesconto;

	private BigDecimal valorDebitoAposDesconto;

	private BigDecimal valorMinimoEntrada;

	private BigDecimal valorTotalJurosFinanciamento;

	private Short quantidadeParcelas;

	private BigDecimal valorParcela;

	// private BigDecimal percentualEntradaSugerida;

	private String dataVencimentoEntrada;

	private Integer numeroReparcelamentoConsecutivos;

	public Integer getIdResolucaoDiretoria(){

		return idResolucaoDiretoria;
	}

	public void setIdResolucaoDiretoria(Integer idResolucaoDiretoria){

		this.idResolucaoDiretoria = idResolucaoDiretoria;
	}

	public String getNumeroResolucaoDiretoria(){

		return numeroResolucaoDiretoria;
	}

	public void setNumeroResolucaoDiretoria(String numeroResolucaoDiretoria){

		this.numeroResolucaoDiretoria = numeroResolucaoDiretoria;
	}

	public BigDecimal getValorDebitoTotalAtualizado(){

		return valorDebitoTotalAtualizado;
	}

	public void setValorDebitoTotalAtualizado(BigDecimal valorDebitoTotalAtualizado){

		this.valorDebitoTotalAtualizado = valorDebitoTotalAtualizado;
	}

	public BigDecimal getPercentualTotalDesconto(){

		return percentualTotalDesconto;
	}

	public void setPercentualTotalDesconto(BigDecimal percentualTotalDesconto){

		this.percentualTotalDesconto = percentualTotalDesconto;
	}

	public BigDecimal getValorTotalDesconto(){

		return valorTotalDesconto;
	}

	public void setValorTotalDesconto(BigDecimal valorTotalDesconto){

		this.valorTotalDesconto = valorTotalDesconto;
	}

	public BigDecimal getValorDebitoAposDesconto(){

		return valorDebitoAposDesconto;
	}

	public void setValorDebitoAposDesconto(BigDecimal valorDebitoAposDesconto){

		this.valorDebitoAposDesconto = valorDebitoAposDesconto;
	}

	public BigDecimal getValorMinimoEntrada(){

		return valorMinimoEntrada;
	}

	public void setValorMinimoEntrada(BigDecimal valorMinimoEntrada){

		this.valorMinimoEntrada = valorMinimoEntrada;
	}

	public BigDecimal getValorTotalJurosFinanciamento(){

		return valorTotalJurosFinanciamento;
	}

	public void setValorTotalJurosFinanciamento(BigDecimal valorTotalJurosFinanciamento){

		this.valorTotalJurosFinanciamento = valorTotalJurosFinanciamento;
	}

	public Short getQuantidadeParcelas(){

		return quantidadeParcelas;
	}

	public void setQuantidadeParcelas(Short quantidadeParcelas){

		this.quantidadeParcelas = quantidadeParcelas;
	}

	public BigDecimal getValorParcela(){

		return valorParcela;
	}

	public void setValorParcela(BigDecimal valorParcela){

		this.valorParcela = valorParcela;
	}

	// public BigDecimal getPercentualEntradaSugerida(){
	// return percentualEntradaSugerida;
	// }

	// public void setPercentualEntradaSugerida(BigDecimal percentualEntradaSugerida){
	// this.percentualEntradaSugerida = percentualEntradaSugerida;
	// }

	public String getDataVencimentoEntrada(){

		return dataVencimentoEntrada;
	}

	public void setDataVencimentoEntrada(String dataVencimentoEntrada){

		this.dataVencimentoEntrada = dataVencimentoEntrada;
	}

	public Integer getNumeroReparcelamentoConsecutivos(){

		return numeroReparcelamentoConsecutivos;
	}

	public void setNumeroReparcelamentoConsecutivos(Integer numeroReparcelamentoConsecutivos){

		this.numeroReparcelamentoConsecutivos = numeroReparcelamentoConsecutivos;
	}

}