
package gcom.cobranca.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * [UC3140] Acompanhar Imóveis com Débitos Prescritos
 * [SB0002] - Exibir Dados dos Itens de Débito Prescritos do Imóvel
 * 
 * @author Anderson Italo
 * @date 04/04/2014
 */
public class PrescricaoGuiaPrestacaoHelper
				implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer idGuiaPagamento;

	private Integer numeroPrestacao;

	private Date dataVencimento;

	private Date dataEmissao;

	private Integer idTipoDebito;

	private String descricaoTipoDebito;

	private BigDecimal valorPrestacao;

	private Integer idDebitoCreditoSituacao;

	private String descricaoDebitoCreditoSituacao;

	private String indicadorPaga;

	private Short indicadorHistorico;



	
	public Integer getIdGuiaPagamento(){

		return idGuiaPagamento;
	}

	public void setIdGuiaPagamento(Integer idGuiaPagamento){

		this.idGuiaPagamento = idGuiaPagamento;
	}

	public Integer getNumeroPrestacao(){

		return numeroPrestacao;
	}

	public void setNumeroPrestacao(Integer numeroPrestacao){

		this.numeroPrestacao = numeroPrestacao;
	}

	public Date getDataVencimento(){

		return dataVencimento;
	}

	public void setDataVencimento(Date dataVencimento){

		this.dataVencimento = dataVencimento;
	}

	public Date getDataEmissao(){

		return dataEmissao;
	}

	public void setDataEmissao(Date dataEmissao){

		this.dataEmissao = dataEmissao;
	}

	public Integer getIdTipoDebito(){

		return idTipoDebito;
	}

	public void setIdTipoDebito(Integer idTipoDebito){

		this.idTipoDebito = idTipoDebito;
	}

	public String getDescricaoTipoDebito(){

		return descricaoTipoDebito;
	}

	public void setDescricaoTipoDebito(String descricaoTipoDebito){

		this.descricaoTipoDebito = descricaoTipoDebito;
	}

	public BigDecimal getValorPrestacao(){

		return valorPrestacao;
	}

	public void setValorPrestacao(BigDecimal valorPrestacao){

		this.valorPrestacao = valorPrestacao;
	}

	public String getIdComPrestacao(){

		String idComPrestacao = "";

		if(this.getIdGuiaPagamento() != null && this.getNumeroPrestacao() != null){
			idComPrestacao = this.getIdGuiaPagamento() + "/" + this.getNumeroPrestacao();
		}

		return idComPrestacao;
	}

	public Integer getIdDebitoCreditoSituacao(){

		return idDebitoCreditoSituacao;
	}

	public void setIdDebitoCreditoSituacao(Integer idDebitoCreditoSituacao){

		this.idDebitoCreditoSituacao = idDebitoCreditoSituacao;
	}

	public String getDescricaoDebitoCreditoSituacao(){

		return descricaoDebitoCreditoSituacao;
	}

	public void setDescricaoDebitoCreditoSituacao(String descricaoDebitoCreditoSituacao){

		this.descricaoDebitoCreditoSituacao = descricaoDebitoCreditoSituacao;
	}

	public String getIndicadorPaga(){

		return indicadorPaga;
	}

	public void setIndicadorPaga(String indicadorPaga){

		this.indicadorPaga = indicadorPaga;
	}

	public Short getIndicadorHistorico(){

		return indicadorHistorico;
	}

	public void setIndicadorHistorico(Short indicadorHistorico){

		this.indicadorHistorico = indicadorHistorico;
	}

}
