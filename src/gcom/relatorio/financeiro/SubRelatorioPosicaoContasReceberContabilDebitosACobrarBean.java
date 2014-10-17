
package gcom.relatorio.financeiro;

import gcom.relatorio.RelatorioBean;

import java.math.BigDecimal;

public class SubRelatorioPosicaoContasReceberContabilDebitosACobrarBean
				implements RelatorioBean {

	private String idCategoria;

	private String descricaoCategoria;

	private BigDecimal valorFinanciamento;

	private BigDecimal valorParcelamento;

	private BigDecimal valorTotal;

	private BigDecimal totalGeralContas;

	private BigDecimal totalGeralDebitos;

	private BigDecimal totalGeral;

	public String getIdCategoria(){

		return idCategoria;
	}

	public void setIdCategoria(String idCategoria){

		this.idCategoria = idCategoria;
	}

	public String getDescricaoCategoria(){

		return descricaoCategoria;
	}

	public void setDescricaoCategoria(String descricaoCategoria){

		this.descricaoCategoria = descricaoCategoria;
	}

	public BigDecimal getValorFinanciamento(){

		return valorFinanciamento;
	}

	public void setValorFinanciamento(BigDecimal valorFinanciamento){

		this.valorFinanciamento = valorFinanciamento;
	}

	public BigDecimal getValorParcelamento(){

		return valorParcelamento;
	}

	public void setValorParcelamento(BigDecimal valorParcelamento){

		this.valorParcelamento = valorParcelamento;
	}

	public BigDecimal getValorTotal(){

		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal){

		this.valorTotal = valorTotal;
	}

	public BigDecimal getTotalGeralContas(){

		return totalGeralContas;
	}

	public void setTotalGeralContas(BigDecimal totalGeralContas){

		this.totalGeralContas = totalGeralContas;
	}

	public BigDecimal getTotalGeralDebitos(){

		return totalGeralDebitos;
	}

	public void setTotalGeralDebitos(BigDecimal totalGeralDebitos){

		this.totalGeralDebitos = totalGeralDebitos;
	}

	public BigDecimal getTotalGeral(){

		return totalGeral;
	}

	public void setTotalGeral(BigDecimal totalGeral){

		this.totalGeral = totalGeral;
	}

}
