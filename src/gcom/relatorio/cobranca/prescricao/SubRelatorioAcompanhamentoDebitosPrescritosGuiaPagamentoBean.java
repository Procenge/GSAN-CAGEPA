
package gcom.relatorio.cobranca.prescricao;

import gcom.relatorio.RelatorioBean;

import java.math.BigDecimal;

public class SubRelatorioAcompanhamentoDebitosPrescritosGuiaPagamentoBean
				implements RelatorioBean {

	private String prestacao;

	private String dataEmissao;

	private String dataVencimento;

	private BigDecimal valorPrestacao;

	private String idGuia;

	private String descricaoTipoDebito;

	private String descricaoDebitoCreditoSituacao;

	private String indicadorPaga;

	public String getPrestacao(){

		return prestacao;
	}

	public void setPrestacao(String prestacao){

		this.prestacao = prestacao;
	}

	public String getDataVencimento(){

		return dataVencimento;
	}

	public void setDataVencimento(String dataVencimento){

		this.dataVencimento = dataVencimento;
	}

	public String getIdGuia(){

		return idGuia;
	}

	public void setIdGuia(String idGuia){

		this.idGuia = idGuia;
	}

	public BigDecimal getValorPrestacao(){

		return valorPrestacao;
	}

	public void setValorPrestacao(BigDecimal valorPrestacao){

		this.valorPrestacao = valorPrestacao;
	}

	public String getDescricaoTipoDebito(){

		return descricaoTipoDebito;
	}

	public void setDescricaoTipoDebito(String descricaoTipoDebito){

		this.descricaoTipoDebito = descricaoTipoDebito;
	}

	public String getIndicadorPaga(){

		return indicadorPaga;
	}

	public void setIndicadorPaga(String indicadorPaga){

		this.indicadorPaga = indicadorPaga;
	}

	public String getDataEmissao(){

		return dataEmissao;
	}

	public void setDataEmissao(String dataEmissao){

		this.dataEmissao = dataEmissao;
	}

	public String getDescricaoDebitoCreditoSituacao(){

		return descricaoDebitoCreditoSituacao;
	}

	public void setDescricaoDebitoCreditoSituacao(String descricaoDebitoCreditoSituacao){

		this.descricaoDebitoCreditoSituacao = descricaoDebitoCreditoSituacao;
	}

}