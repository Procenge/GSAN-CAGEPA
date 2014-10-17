
package gcom.gui.cobranca;

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
public class CobrancaAdministrativaGuiaHelper
				implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private Integer numeroPrestacao;

	private Date dataEmissao;

	private Integer idTipoDebito;

	private String descricaoTipoDebito;

	private BigDecimal valorPrestacao;

	private Integer idCliente;

	private String nomeCliente;

	private Integer idSituacaoDebito;

	private String descricaoSituacaoDebito;

	private String tipoRemuneracao = null;

	private BigDecimal percentualRemuneracao = null;

	private BigDecimal valorRemuneracao = null;

	private Date dataSituacaoDebito = null;

	private BigDecimal valorItemCobrado;

	private BigDecimal valorAcrescimos;

	public Integer getId(){

		return id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public Integer getNumeroPrestacao(){

		return numeroPrestacao;
	}

	public void setNumeroPrestacao(Integer numeroPrestacao){

		this.numeroPrestacao = numeroPrestacao;
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

	public Integer getIdCliente(){

		return idCliente;
	}

	public void setIdCliente(Integer idCliente){

		this.idCliente = idCliente;
	}

	public String getNomeCliente(){

		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente){

		this.nomeCliente = nomeCliente;
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

	public String getIdComPrestacao(){

		String idComPrestacao = "";

		if(this.getId() != null && this.getNumeroPrestacao() != null){
			idComPrestacao = this.getId() + " / " + this.getNumeroPrestacao();
		}

		return idComPrestacao;
	}

	public Date getDataSituacaoDebito(){

		return dataSituacaoDebito;
	}

	public void setDataSituacaoDebito(Date dataSituacaoDebito){

		this.dataSituacaoDebito = dataSituacaoDebito;
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
