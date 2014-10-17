
package gcom.cobranca.bean;

import gcom.faturamento.credito.CreditoARealizar;
import gcom.faturamento.debito.DebitoACobrar;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;

/**
 * [UC3031] Efetuar Negociação de Débitos
 * 
 * @author Hebert Falcão
 * @date 01/12/2011
 */
public class NegociacaoDebitoImovelHelper
				implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer idImovel;

	private Integer idLigacaoAguaSituacao;

	private Integer idLigacaoEsgotoSituacao;

	private Integer idPerfilImovel;

	private Collection<ContaValoresHelper> colecaoContaValores;

	private Collection<GuiaPagamentoValoresHelper> colecaoGuiaPagamentoValores;

	private Collection<DebitoACobrar> colecaoDebitoACobrar;

	private Collection<CreditoARealizar> colecaoCreditoARealizar;

	private BigDecimal valorTotalMulta;

	private BigDecimal valorTotalJurosMora;

	private BigDecimal valorTotalAtualizacaoMonetaria;

	private BigDecimal valorTotalRestanteParcelamentosACobrar;

	public Integer getIdImovel(){

		return idImovel;
	}

	public void setIdImovel(Integer idImovel){

		this.idImovel = idImovel;
	}

	public Integer getIdLigacaoAguaSituacao(){

		return idLigacaoAguaSituacao;
	}

	public void setIdLigacaoAguaSituacao(Integer idLigacaoAguaSituacao){

		this.idLigacaoAguaSituacao = idLigacaoAguaSituacao;
	}

	public Integer getIdLigacaoEsgotoSituacao(){

		return idLigacaoEsgotoSituacao;
	}

	public void setIdLigacaoEsgotoSituacao(Integer idLigacaoEsgotoSituacao){

		this.idLigacaoEsgotoSituacao = idLigacaoEsgotoSituacao;
	}

	public Integer getIdPerfilImovel(){

		return idPerfilImovel;
	}

	public void setIdPerfilImovel(Integer idPerfilImovel){

		this.idPerfilImovel = idPerfilImovel;
	}

	public Collection<ContaValoresHelper> getColecaoContaValores(){

		return colecaoContaValores;
	}

	public void setColecaoContaValores(Collection<ContaValoresHelper> colecaoContaValores){

		this.colecaoContaValores = colecaoContaValores;
	}

	public Collection<GuiaPagamentoValoresHelper> getColecaoGuiaPagamentoValores(){

		return colecaoGuiaPagamentoValores;
	}

	public void setColecaoGuiaPagamentoValores(Collection<GuiaPagamentoValoresHelper> colecaoGuiaPagamentoValores){

		this.colecaoGuiaPagamentoValores = colecaoGuiaPagamentoValores;
	}

	public BigDecimal getValorTotalMulta(){

		return valorTotalMulta;
	}

	public void setValorTotalMulta(BigDecimal valorTotalMulta){

		this.valorTotalMulta = valorTotalMulta;
	}

	public BigDecimal getValorTotalJurosMora(){

		return valorTotalJurosMora;
	}

	public void setValorTotalJurosMora(BigDecimal valorTotalJurosMora){

		this.valorTotalJurosMora = valorTotalJurosMora;
	}

	public BigDecimal getValorTotalAtualizacaoMonetaria(){

		return valorTotalAtualizacaoMonetaria;
	}

	public void setValorTotalAtualizacaoMonetaria(BigDecimal valorTotalAtualizacaoMonetaria){

		this.valorTotalAtualizacaoMonetaria = valorTotalAtualizacaoMonetaria;
	}

	public BigDecimal getValorTotalRestanteParcelamentosACobrar(){

		return valorTotalRestanteParcelamentosACobrar;
	}

	public void setValorTotalRestanteParcelamentosACobrar(BigDecimal valorTotalRestanteParcelamentosACobrar){

		this.valorTotalRestanteParcelamentosACobrar = valorTotalRestanteParcelamentosACobrar;
	}

	public Collection<DebitoACobrar> getColecaoDebitoACobrar(){

		return colecaoDebitoACobrar;
	}

	public void setColecaoDebitoACobrar(Collection<DebitoACobrar> colecaoDebitoACobrar){

		this.colecaoDebitoACobrar = colecaoDebitoACobrar;
	}

	public Collection<CreditoARealizar> getColecaoCreditoARealizar(){

		return colecaoCreditoARealizar;
	}

	public void setColecaoCreditoARealizar(Collection<CreditoARealizar> colecaoCreditoARealizar){

		this.colecaoCreditoARealizar = colecaoCreditoARealizar;
	}

}