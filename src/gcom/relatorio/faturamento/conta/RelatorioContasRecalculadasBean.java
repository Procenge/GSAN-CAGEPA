
package gcom.relatorio.faturamento.conta;

import gcom.relatorio.RelatorioBean;

import java.math.BigDecimal;

public class RelatorioContasRecalculadasBean
				implements RelatorioBean {


	private String matricula;

	private String inscricao;

	private String endereco;

	private String referenciaConta;

	private String situacaoConta;

	private BigDecimal valorOriginalAgua;

	private BigDecimal valorOriginalEsgoto;

	private BigDecimal valorOriginalTotal;

	private BigDecimal valorRecalculadoAgua;

	private BigDecimal valorRecalculadoEsgoto;

	private BigDecimal valorRecalculadoTotal;
	
	private BigDecimal valorDiferenca;

	private Integer idImovel;

	private Integer idConta;

	private String clienteUsuario;

	public String getMatricula(){

		return matricula;
	}

	public void setMatricula(String matricula){

		this.matricula = matricula;
	}

	public String getInscricao(){

		return inscricao;
	}

	public void setInscricao(String inscricao){

		this.inscricao = inscricao;
	}

	public String getEndereco(){

		return endereco;
	}

	public void setEndereco(String endereco){

		this.endereco = endereco;
	}

	public String getReferenciaConta(){

		return referenciaConta;
	}

	public void setReferenciaConta(String referenciaConta){

		this.referenciaConta = referenciaConta;
	}

	public String getSituacaoConta(){

		return situacaoConta;
	}

	public void setSituacaoConta(String situacaoConta){

		this.situacaoConta = situacaoConta;
	}

	public BigDecimal getValorOriginalAgua(){

		return valorOriginalAgua;
	}

	public void setValorOriginalAgua(BigDecimal valorOriginalAgua){

		this.valorOriginalAgua = valorOriginalAgua;
	}

	public BigDecimal getValorOriginalEsgoto(){

		return valorOriginalEsgoto;
	}

	public void setValorOriginalEsgoto(BigDecimal valorOriginalEsgoto){

		this.valorOriginalEsgoto = valorOriginalEsgoto;
	}

	public BigDecimal getValorOriginalTotal(){

		return valorOriginalTotal;
	}

	public void setValorOriginalTotal(BigDecimal valorOriginalTotal){

		this.valorOriginalTotal = valorOriginalTotal;
	}

	public BigDecimal getValorRecalculadoAgua(){

		return valorRecalculadoAgua;
	}

	public void setValorRecalculadoAgua(BigDecimal valorRecalculadoAgua){

		this.valorRecalculadoAgua = valorRecalculadoAgua;
	}

	public BigDecimal getValorRecalculadoEsgoto(){

		return valorRecalculadoEsgoto;
	}

	public void setValorRecalculadoEsgoto(BigDecimal valorRecalculadoEsgoto){

		this.valorRecalculadoEsgoto = valorRecalculadoEsgoto;
	}

	public BigDecimal getValorRecalculadoTotal(){

		return valorRecalculadoTotal;
	}

	public void setValorRecalculadoTotal(BigDecimal valorRecalculadoTotal){

		this.valorRecalculadoTotal = valorRecalculadoTotal;
	}

	public BigDecimal getValorDiferenca(){

		return valorDiferenca;
	}

	public void setValorDiferenca(BigDecimal valorDiferenca){

		this.valorDiferenca = valorDiferenca;
	}

	public Integer getIdImovel(){

		return idImovel;
	}

	public void setIdImovel(Integer idImovel){

		this.idImovel = idImovel;
	}

	public Integer getIdConta(){

		return idConta;
	}

	public void setIdConta(Integer idConta){

		this.idConta = idConta;
	}

	public String getClienteUsuario(){

		return clienteUsuario;
	}

	public void setClienteUsuario(String clienteUsuario){

		this.clienteUsuario = clienteUsuario;
	}

}