
package gcom.gui.cobranca;

import gcom.util.Util;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * [UC3060] Consultar Imóvel Cobrança Administrativa
 * [SB0001] Consultar Dados da Cobrança Administrativa do Imóvel
 * 
 * @author Hebert Falcão
 * @date 15/09/2012
 */
public class CobrancaAdministrativaContaHelper
				implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer idConta;

	private Integer referencia;

	private Date dataVencimentoConta;

	private BigDecimal valorAgua;

	private BigDecimal valorEsgoto;

	private BigDecimal valorDebitos;

	private BigDecimal valorCreditos;

	private BigDecimal valorImposto;

	private Integer idCliente;

	private Integer idSituacaoDebito;

	private String descricaoSituacaoDebito;

	private Date dataSituacaoDebito;

	private String nomeCliente;

	private String tipoRemuneracao = null;

	private BigDecimal percentualRemuneracao = null;

	private BigDecimal valorRemuneracao = null;

	private BigDecimal valorItemCobrado;

	private BigDecimal valorAcrescimos;

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

	public BigDecimal getValorImposto(){

		return valorImposto;
	}

	public void setValorImposto(BigDecimal valorImposto){

		this.valorImposto = valorImposto;
	}

	public Integer getIdCliente(){

		return idCliente;
	}

	public void setIdCliente(Integer idCliente){

		this.idCliente = idCliente;
	}

	public BigDecimal getValorConta(){

		BigDecimal valorConta = BigDecimal.ZERO;
		BigDecimal valorAgua = this.getValorAgua();
		BigDecimal valorEsgoto = this.getValorEsgoto();
		BigDecimal valorDebitos = this.getValorDebitos();
		BigDecimal valorCreditos = this.getValorCreditos();
		BigDecimal valorImposto = this.getValorImposto();

		// Valor de água
		if(valorAgua != null){
			valorConta = valorConta.add(valorAgua);
		}

		// Valor de esgoto
		if(valorEsgoto != null){
			valorConta = valorConta.add(valorEsgoto);
		}

		// Valor dos débitos
		if(valorDebitos != null){
			valorConta = valorConta.add(valorDebitos);
		}

		// Valor dos créditos
		if(valorCreditos != null){
			valorConta = valorConta.subtract(valorCreditos);
		}

		// Valor dos impostos
		if(valorImposto != null){
			valorConta = valorConta.subtract(valorImposto);
		}

		return valorConta;
	}

	public Integer getIdSituacaoDebito(){

		return idSituacaoDebito;
	}

	public void setIdSituacaoDebito(Integer idSituacaoDebito){

		this.idSituacaoDebito = idSituacaoDebito;
	}

	public String getDescricaoSituacaoDebito(){

		return descricaoSituacaoDebito;
	}

	public void setDescricaoSituacaoDebito(String descricaoSituacaoDebito){

		this.descricaoSituacaoDebito = descricaoSituacaoDebito;
	}

	public Date getDataSituacaoDebito(){

		return dataSituacaoDebito;
	}

	public void setDataSituacaoDebito(Date dataSituacaoDebito){

		this.dataSituacaoDebito = dataSituacaoDebito;
	}

	public String getNomeCliente(){

		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente){

		this.nomeCliente = nomeCliente;
	}

	public Integer getIdConta(){

		return idConta;
	}

	public void setIdConta(Integer idConta){

		this.idConta = idConta;
	}

	public String getTipoRemuneracao(){

		return tipoRemuneracao;
	}

	public void setTipoRemuneracao(String tipoRemuneracao){

		this.tipoRemuneracao = tipoRemuneracao;
	}

	public BigDecimal getPercentualRemuneracao(){

		return percentualRemuneracao;
	}

	public void setPercentualRemuneracao(BigDecimal percentualRemuneracao){

		this.percentualRemuneracao = percentualRemuneracao;
	}

	public BigDecimal getValorRemuneracao(){

		return valorRemuneracao;
	}

	public void setValorRemuneracao(BigDecimal valorRemuneracao){

		this.valorRemuneracao = valorRemuneracao;
	}

	public BigDecimal getValorItemCobrado(){

		return valorItemCobrado;
	}

	public void setValorItemCobrado(BigDecimal valorItemCobrado){

		this.valorItemCobrado = valorItemCobrado;
	}

	public BigDecimal getValorAcrescimos(){

		return valorAcrescimos;
	}

	public void setValorAcrescimos(BigDecimal valorAcrescimos){

		this.valorAcrescimos = valorAcrescimos;
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

		BigDecimal valorImposto = this.getValorImposto();
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

	public BigDecimal getValorDebito(){

		BigDecimal valorDebito = BigDecimal.ZERO;
		BigDecimal valorItemCobrado = this.getValorItemCobrado();
		BigDecimal valorAcrescimos = this.getValorAcrescimos();

		if(valorItemCobrado != null){
			valorDebito = valorDebito.add(valorItemCobrado);
		}

		if(valorAcrescimos != null){
			valorDebito = valorDebito.add(valorAcrescimos);
		}

		return valorDebito;
	}

}
