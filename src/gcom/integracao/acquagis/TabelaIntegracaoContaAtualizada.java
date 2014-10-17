package gcom.integracao.acquagis;

import gcom.cadastro.imovel.Imovel;
import gcom.faturamento.conta.ContaGeral;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class TabelaIntegracaoContaAtualizada
				implements Serializable {

	private static final long serialVersionUID = 1L;

	/** identifier field */
	private Integer id;

	/** identifier field */
	private BigDecimal contaAtualizadaValor;

	/** identifier field */
	private ContaGeral contaGeral;

	/** identifier field */
	private Imovel imovel;

	/** identifier field */
	private Date ultimaAlteracao;

	/**
	 * Construtor padrão
	 */
	public TabelaIntegracaoContaAtualizada() {

		super();
	}

	/**
	 * @param id
	 * @param contaAtualizadaValor
	 * @param conta
	 * @param imovel
	 */
	public TabelaIntegracaoContaAtualizada(Integer id, BigDecimal contaAtualizadaValor, ContaGeral contaGeral, Imovel imovel) {

		super();
		this.id = id;
		this.contaAtualizadaValor = contaAtualizadaValor;
		this.contaGeral = contaGeral;
		this.imovel = imovel;
	}


	/**
	 * @return the id
	 */
	public Integer getId(){

		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Integer id){

		this.id = id;
	}

	/**
	 * @return the contaAtualizadaValor
	 */
	public BigDecimal getContaAtualizadaValor(){

		return contaAtualizadaValor;
	}

	/**
	 * @param contaAtualizadaValor
	 *            the contaAtualizadaValor to set
	 */
	public void setContaAtualizadaValor(BigDecimal contaAtualizadaValor){

		this.contaAtualizadaValor = contaAtualizadaValor;
	}

	/**
	 * @return the conta
	 */
	public ContaGeral getContaGeral(){

		return contaGeral;
	}

	/**
	 * @param conta
	 *            the conta to set
	 */
	public void setContaGeral(ContaGeral contaGeral){

		this.contaGeral = contaGeral;
	}

	/**
	 * @return the imovel
	 */
	public Imovel getImovel(){

		return imovel;
	}

	/**
	 * @param imovel
	 *            the imovel to set
	 */
	public void setImovel(Imovel imovel){

		this.imovel = imovel;
	}

	/**
	 * @return the ultimaAlteracao
	 */
	public Date getUltimaAlteracao(){

		return ultimaAlteracao;
	}

	/**
	 * @param ultimaAlteracao
	 *            the ultimaAlteracao to set
	 */
	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode(){

		final int prime = 31;
		int result = 1;
		result = prime * result + ((contaGeral == null) ? 0 : contaGeral.hashCode());
		result = prime * result + ((contaAtualizadaValor == null) ? 0 : contaAtualizadaValor.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((imovel == null) ? 0 : imovel.hashCode());
		result = prime * result + ((ultimaAlteracao == null) ? 0 : ultimaAlteracao.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj){

		if(this == obj) return true;
		if(obj == null) return false;
		if(getClass() != obj.getClass()) return false;
		TabelaIntegracaoContaAtualizada other = (TabelaIntegracaoContaAtualizada) obj;
		if(contaGeral == null){
			if(other.contaGeral != null) return false;
		}else if(!contaGeral.equals(other.contaGeral)) return false;
		if(contaAtualizadaValor == null){
			if(other.contaAtualizadaValor != null) return false;
		}else if(!contaAtualizadaValor.equals(other.contaAtualizadaValor)) return false;
		if(id == null){
			if(other.id != null) return false;
		}else if(!id.equals(other.id)) return false;
		if(imovel == null){
			if(other.imovel != null) return false;
		}else if(!imovel.equals(other.imovel)) return false;
		if(ultimaAlteracao == null){
			if(other.ultimaAlteracao != null) return false;
		}else if(!ultimaAlteracao.equals(other.ultimaAlteracao)) return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){

		return "TabelaIntegracaoContaAtualizada [id=" + id + ", contaAtualizadaValor=" + contaAtualizadaValor + ", contaGeral="
						+ contaGeral
						+ ", imovel=" + imovel + ", ultimaAlteracao=" + ultimaAlteracao + "]";
	}
}
