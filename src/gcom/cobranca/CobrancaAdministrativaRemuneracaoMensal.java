
package gcom.cobranca;

import gcom.cadastro.empresa.Empresa;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Cobrança Administrativa Remuneração Mensal
 * 
 * @author Hebert Falcão
 * @date 09/11/2013
 */
public class CobrancaAdministrativaRemuneracaoMensal
				implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private Integer referenciaArrecadacao;

	private BigDecimal percentualDesempenho;

	private BigDecimal percentualRemuneracao;

	private BigDecimal valorArrecadadoLote;

	private BigDecimal valorArrecadadoLoteAcumulado;

	private BigDecimal valorArrecadado;

	private BigDecimal valorRemuneracao;

	private Date ultimaAlteracao;

	private Date dataPagamentoRemuneracao;

	private Short indicadorRemuneracaoPaga;

	public Short getIndicadorRemuneracaoPaga(){

		return indicadorRemuneracaoPaga;
	}

	public void setIndicadorRemuneracaoPaga(Short indicadorRemuneracaoPaga){

		this.indicadorRemuneracaoPaga = indicadorRemuneracaoPaga;
	}

	private Empresa empresa;

	private CobrancaAcaoAtividadeComando cobrancaAcaoAtividadeComando;

	public Integer getId(){

		return id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public Integer getReferenciaArrecadacao(){

		return referenciaArrecadacao;
	}

	public void setReferenciaArrecadacao(Integer referenciaArrecadacao){

		this.referenciaArrecadacao = referenciaArrecadacao;
	}

	public BigDecimal getPercentualDesempenho(){

		return percentualDesempenho;
	}

	public void setPercentualDesempenho(BigDecimal percentualDesempenho){

		this.percentualDesempenho = percentualDesempenho;
	}

	public BigDecimal getPercentualRemuneracao(){

		return percentualRemuneracao;
	}

	public void setPercentualRemuneracao(BigDecimal percentualRemuneracao){

		this.percentualRemuneracao = percentualRemuneracao;
	}

	public BigDecimal getValorArrecadadoLote(){

		return valorArrecadadoLote;
	}

	public void setValorArrecadadoLote(BigDecimal valorArrecadadoLote){

		this.valorArrecadadoLote = valorArrecadadoLote;
	}

	public BigDecimal getValorArrecadadoLoteAcumulado(){

		return valorArrecadadoLoteAcumulado;
	}

	public void setValorArrecadadoLoteAcumulado(BigDecimal valorArrecadadoLoteAcumulado){

		this.valorArrecadadoLoteAcumulado = valorArrecadadoLoteAcumulado;
	}

	public BigDecimal getValorArrecadado(){

		return valorArrecadado;
	}

	public void setValorArrecadado(BigDecimal valorArrecadado){

		this.valorArrecadado = valorArrecadado;
	}

	public BigDecimal getValorRemuneracao(){

		return valorRemuneracao;
	}

	public void setValorRemuneracao(BigDecimal valorRemuneracao){

		this.valorRemuneracao = valorRemuneracao;
	}

	public Date getUltimaAlteracao(){

		return ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public Empresa getEmpresa(){

		return empresa;
	}

	public void setEmpresa(Empresa empresa){

		this.empresa = empresa;
	}

	public CobrancaAcaoAtividadeComando getCobrancaAcaoAtividadeComando(){

		return cobrancaAcaoAtividadeComando;
	}

	public void setCobrancaAcaoAtividadeComando(CobrancaAcaoAtividadeComando cobrancaAcaoAtividadeComando){

		this.cobrancaAcaoAtividadeComando = cobrancaAcaoAtividadeComando;
	}

	public Date getDataPagamentoRemuneracao(){

		return dataPagamentoRemuneracao;
	}

	public void setDataPagamentoRemuneracao(Date dataPagamentoRemuneracao){

		this.dataPagamentoRemuneracao = dataPagamentoRemuneracao;
	}

}
