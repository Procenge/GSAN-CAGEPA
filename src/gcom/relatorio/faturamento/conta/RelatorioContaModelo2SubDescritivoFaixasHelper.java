package gcom.relatorio.faturamento.conta;

import gcom.relatorio.RelatorioBean;

import java.math.BigDecimal;

public class RelatorioContaModelo2SubDescritivoFaixasHelper
				implements RelatorioBean {

	private String primeiraColuna;

	private String segundaColuna;

	private String terceiraColuna;

	private String quartaColuna;

	private String quintaColuna;

	private String sextaColuna;

	private BigDecimal subTotal;

	public String getPrimeiraColuna(){

		return primeiraColuna;
	}

	public void setPrimeiraColuna(String primeiraColuna){

		this.primeiraColuna = primeiraColuna;
	}

	public String getSegundaColuna(){

		return segundaColuna;
	}

	public void setSegundaColuna(String segundaColuna){

		this.segundaColuna = segundaColuna;
	}

	public String getTerceiraColuna(){

		return terceiraColuna;
	}

	public void setTerceiraColuna(String terceiraColuna){

		this.terceiraColuna = terceiraColuna;
	}

	public String getQuartaColuna(){

		return quartaColuna;
	}

	public void setQuartaColuna(String quartaColuna){

		this.quartaColuna = quartaColuna;
	}

	public String getQuintaColuna(){

		return quintaColuna;
	}

	public void setQuintaColuna(String quintaColuna){

		this.quintaColuna = quintaColuna;
	}

	public String getSextaColuna(){

		return sextaColuna;
	}

	public void setSextaColuna(String sextaColuna){

		this.sextaColuna = sextaColuna;
	}

	public BigDecimal getSubTotal(){

		return subTotal;
	}

	public void setSubTotal(BigDecimal subTotal){

		this.subTotal = subTotal;
	}

}
