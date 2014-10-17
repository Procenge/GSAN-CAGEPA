
package gcom.cobranca.bean;

import gcom.util.Util;

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
public class PrescricaoContaHelper
				implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer idConta;

	private Integer referencia;

	private Date dataVencimentoConta;

	private BigDecimal valorAgua;

	private BigDecimal valorEsgoto;

	private BigDecimal valorDebitos;

	private BigDecimal valorCreditos;

	private BigDecimal valorImpostos;

	private BigDecimal valorConta;

	private Integer idDebitoCreditoSituacao;

	private String descricaoDebitoCreditoSituacao;

	private String indicadorPaga;

	private Short indicadorHistorico;

	public Integer getReferencia(){

		return referencia;
	}

	public void setReferencia(Integer referencia){

		this.referencia = referencia;
	}

	public Date getDataVencimentoConta(){

		return dataVencimentoConta;
	}

	public void setDataVencimentoConta(Date dataVencimentoConta){

		this.dataVencimentoConta = dataVencimentoConta;
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

	public BigDecimal getValorDebitos(){

		return valorDebitos;
	}

	public void setValorDebitos(BigDecimal valorDebitos){

		this.valorDebitos = valorDebitos;
	}

	public BigDecimal getValorCreditos(){

		return valorCreditos;
	}

	public void setValorCreditos(BigDecimal valorCreditos){

		this.valorCreditos = valorCreditos;
	}

	public BigDecimal getValorImpostos(){

		return valorImpostos;
	}

	public void setValorImpostos(BigDecimal valorImpostos){

		this.valorImpostos = valorImpostos;
	}

	public Integer getIdConta(){

		return idConta;
	}

	public void setIdConta(Integer idConta){

		this.idConta = idConta;
	}

	public String getDetalheValorConta(){

		StringBuffer retorno = new StringBuffer();

		BigDecimal valorAgua = this.getValorAgua();
		String valorAguaStr = "";

		BigDecimal valorEsgoto = this.getValorEsgoto();
		String valorEsgotoStr = "";

		BigDecimal valorDebitos = this.getValorDebitos();
		String valorDebitosStr = "";

		BigDecimal valorCreditos = this.getValorCreditos();
		String valorCreditosStr = "";

		BigDecimal valorImposto = this.getValorImpostos();
		String valorImpostoStr = "";

		// Valor de água
		if(valorAgua != null){
			valorAguaStr = Util.formatarMoedaReal(valorAgua);
		}

		// Valor de esgoto
		if(valorEsgoto != null){
			valorEsgotoStr = Util.formatarMoedaReal(valorEsgoto);
		}

		// Valor dos débitos
		if(valorDebitos != null){
			valorDebitosStr = Util.formatarMoedaReal(valorDebitos);
		}

		// Valor dos créditos
		if(valorCreditos != null){
			valorCreditosStr = Util.formatarMoedaReal(valorCreditos);
		}

		// Valor dos impostos
		if(valorImposto != null){
			valorImpostoStr = Util.formatarMoedaReal(valorImposto);
		}

		retorno.append("Valor de Água: " + valorAguaStr);
		retorno.append("\n");
		retorno.append("Valor de Esgoto: " + valorEsgotoStr);
		retorno.append("\n");
		retorno.append("Valor dos Débitos: " + valorDebitosStr);
		retorno.append("\n");
		retorno.append("Valor dos Créditos: " + valorCreditosStr);
		retorno.append("\n");
		retorno.append("Valor dos Impostos: " + valorImpostoStr);

		return retorno.toString();
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

	public BigDecimal getValorConta(){

		return valorConta;
	}

	public void setValorConta(BigDecimal valorConta){

		this.valorConta = valorConta;
	}

	public String getReferenciaFormatada(){

		return Util.formatarAnoMesParaMesAno(this.referencia);
	}

}
