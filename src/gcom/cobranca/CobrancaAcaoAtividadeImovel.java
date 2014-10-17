
package gcom.cobranca;

import java.math.BigDecimal;
import java.util.Date;

import gcom.cadastro.imovel.Imovel;

public class CobrancaAcaoAtividadeImovel {

	private Integer id;

	private Imovel imovel;

	private gcom.cobranca.CobrancaAcaoAtividadeCronograma cobrancaAcaoAtividadeCronograma;

	private gcom.cobranca.CobrancaAcaoAtividadeComando cobrancaAcaoAtividadeComando;

	private Date ultimaAlteracao;

	private BigDecimal valorDocumento;

	private Integer quantidadeDocumentoItem;

	public Integer getId(){

		return id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public Date getUltimaAlteracao(){

		return ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public Imovel getImovel(){

		return imovel;
	}

	public void setImovel(Imovel imovel){

		this.imovel = imovel;
	}

	public gcom.cobranca.CobrancaAcaoAtividadeCronograma getCobrancaAcaoAtividadeCronograma(){

		return cobrancaAcaoAtividadeCronograma;
	}

	public void setCobrancaAcaoAtividadeCronograma(gcom.cobranca.CobrancaAcaoAtividadeCronograma cobrancaAcaoAtividadeCronograma){

		this.cobrancaAcaoAtividadeCronograma = cobrancaAcaoAtividadeCronograma;
	}

	public gcom.cobranca.CobrancaAcaoAtividadeComando getCobrancaAcaoAtividadeComando(){

		return cobrancaAcaoAtividadeComando;
	}

	public void setCobrancaAcaoAtividadeComando(gcom.cobranca.CobrancaAcaoAtividadeComando cobrancaAcaoAtividadeComando){

		this.cobrancaAcaoAtividadeComando = cobrancaAcaoAtividadeComando;
	}

	public BigDecimal getValorDocumento(){

		return valorDocumento;
	}

	public void setValorDocumento(BigDecimal valorDocumento){

		this.valorDocumento = valorDocumento;
	}

	public Integer getQuantidadeDocumentoItem(){

		return quantidadeDocumentoItem;
	}

	public void setQuantidadeDocumentoItem(Integer quantidadeDocumentoItem){

		this.quantidadeDocumentoItem = quantidadeDocumentoItem;
	}

}