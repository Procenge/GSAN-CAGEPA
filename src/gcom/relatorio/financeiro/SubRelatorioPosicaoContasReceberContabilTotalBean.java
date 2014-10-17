
package gcom.relatorio.financeiro;

import gcom.relatorio.RelatorioBean;

import java.math.BigDecimal;

public class SubRelatorioPosicaoContasReceberContabilTotalBean
				implements RelatorioBean {

	private String descricao;

	private BigDecimal valorContasAVencer;

	private BigDecimal valorContasAtrasoAte30Dias;

	private BigDecimal valorContasAtraso30A60Dias;

	private BigDecimal valorContasAtraso60A90Dias;

	private BigDecimal valorContasAtrasoMaisDe90Dias;

	private BigDecimal valorContasTotal;

	public String getDescricao(){

		return descricao;
	}

	public void setDescricao(String descricao){

		this.descricao = descricao;
	}

	public BigDecimal getValorContasAVencer(){

		return valorContasAVencer;
	}

	public void setValorContasAVencer(BigDecimal valorContasAVencer){

		this.valorContasAVencer = valorContasAVencer;
	}

	public BigDecimal getValorContasAtrasoAte30Dias(){

		return valorContasAtrasoAte30Dias;
	}

	public void setValorContasAtrasoAte30Dias(BigDecimal valorContasAtrasoAte30Dias){

		this.valorContasAtrasoAte30Dias = valorContasAtrasoAte30Dias;
	}

	public BigDecimal getValorContasAtraso30A60Dias(){

		return valorContasAtraso30A60Dias;
	}

	public void setValorContasAtraso30A60Dias(BigDecimal valorContasAtraso30A60Dias){

		this.valorContasAtraso30A60Dias = valorContasAtraso30A60Dias;
	}

	public BigDecimal getValorContasAtraso60A90Dias(){

		return valorContasAtraso60A90Dias;
	}

	public void setValorContasAtraso60A90Dias(BigDecimal valorContasAtraso60A90Dias){

		this.valorContasAtraso60A90Dias = valorContasAtraso60A90Dias;
	}

	public BigDecimal getValorContasAtrasoMaisDe90Dias(){

		return valorContasAtrasoMaisDe90Dias;
	}

	public void setValorContasAtrasoMaisDe90Dias(BigDecimal valorContasAtrasoMaisDe90Dias){

		this.valorContasAtrasoMaisDe90Dias = valorContasAtrasoMaisDe90Dias;
	}

	public BigDecimal getValorContasTotal(){

		return valorContasTotal;
	}

	public void setValorContasTotal(BigDecimal valorContasTotal){

		this.valorContasTotal = valorContasTotal;
	}

}
