
package gcom.faturamento.bean;

import java.math.BigDecimal;

public class GuiaPagamentoHelper {

	private Integer numeroGuia;

	private Integer numeroContratoParcelOrgaoPublico;

	private Integer idImovel;

	private Integer idClienteResponsavel;

	private String nomeClienteResponsavel;

	private Integer idClienteGuia;

	private String nomeClienteGuia;

	private BigDecimal valorDebitos;

	private BigDecimal valorPagamentos;

	private BigDecimal totalPendente;

	private Integer numeroPrestacaoTotal;

	private Integer numeroPrestacaoPaga;

	private Integer numeroPrestacaoPendente;

	private String enderecoImovel;

	private String dataPagamento;

	public GuiaPagamentoHelper() {

	}

	public Integer getNumeroGuia(){

		return numeroGuia;
	}

	public void setNumeroGuia(Integer numeroGuia){

		this.numeroGuia = numeroGuia;
	}

	public Integer getNumeroContratoParcelOrgaoPublico(){

		return numeroContratoParcelOrgaoPublico;
	}

	public void setNumeroContratoParcelOrgaoPublico(Integer numeroContratoParcelOrgaoPublico){

		this.numeroContratoParcelOrgaoPublico = numeroContratoParcelOrgaoPublico;
	}

	public Integer getIdImovel(){

		return idImovel;
	}

	public void setIdImovel(Integer idImovel){

		this.idImovel = idImovel;
	}

	public Integer getIdClienteResponsavel(){

		return idClienteResponsavel;
	}

	public void setIdClienteResponsavel(Integer idClienteResponsavel){

		this.idClienteResponsavel = idClienteResponsavel;
	}

	public String getNomeClienteResponsavel(){

		return nomeClienteResponsavel;
	}

	public void setNomeClienteResponsavel(String nomeClienteResponsavel){

		this.nomeClienteResponsavel = nomeClienteResponsavel;
	}

	public Integer getIdClienteGuia(){

		return idClienteGuia;
	}

	public void setIdClienteGuia(Integer idClienteGuia){

		this.idClienteGuia = idClienteGuia;
	}

	public String getNomeClienteGuia(){

		return nomeClienteGuia;
	}

	public void setNomeClienteGuia(String nomeClienteGuia){

		this.nomeClienteGuia = nomeClienteGuia;
	}

	public BigDecimal getValorDebitos(){

		return valorDebitos;
	}

	public void setValorDebitos(BigDecimal valorDebitos){

		this.valorDebitos = valorDebitos;
	}

	public BigDecimal getValorPagamentos(){

		return valorPagamentos;
	}

	public void setValorPagamentos(BigDecimal valorPagamentos){

		this.valorPagamentos = valorPagamentos;
	}

	public BigDecimal getTotalPendente(){

		return totalPendente;
	}

	public void setTotalPendente(BigDecimal totalPendente){

		this.totalPendente = totalPendente;
	}

	public Integer getNumeroPrestacaoTotal(){

		return numeroPrestacaoTotal;
	}

	public void setNumeroPrestacaoTotal(Integer numeroPrestacaoTotal){

		this.numeroPrestacaoTotal = numeroPrestacaoTotal;
	}

	public Integer getNumeroPrestacaoPaga(){

		return numeroPrestacaoPaga;
	}

	public void setNumeroPrestacaoPaga(Integer numeroPrestacaoPaga){

		this.numeroPrestacaoPaga = numeroPrestacaoPaga;
	}

	public Integer getNumeroPrestacaoPendente(){

		return numeroPrestacaoPendente;
	}

	public void setNumeroPrestacaoPendente(Integer numeroPrestacaoPendente){

		this.numeroPrestacaoPendente = numeroPrestacaoPendente;
	}

	public String getEnderecoImovel(){

		return enderecoImovel;
	}

	public void setEnderecoImovel(String enderecoImovel){

		this.enderecoImovel = enderecoImovel;
	}

	public String getDataPagamento(){

		return dataPagamento;
	}

	public void setDataPagamento(String dataPagamento){

		this.dataPagamento = dataPagamento;
	}

}
