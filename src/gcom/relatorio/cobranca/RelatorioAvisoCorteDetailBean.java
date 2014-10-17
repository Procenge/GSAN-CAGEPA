
package gcom.relatorio.cobranca;

import gcom.relatorio.RelatorioBean;

public class RelatorioAvisoCorteDetailBean
				implements RelatorioBean {

	private String mesAno1;

	private String vencimento1;

	private String valor1;

	private String mesAno2;

	private String vencimento2;

	private String valor2;

	private String mesAno3;

	private String vencimento3;

	private String valor3;

	public RelatorioAvisoCorteDetailBean(String[] linhaMesAno, String[] linhaVencimento, String[] linhaValor) {

		this.mesAno1 = linhaMesAno[0];
		this.vencimento1 = linhaVencimento[0];
		this.valor1 = linhaValor[0];

		this.mesAno2 = linhaMesAno[1];
		this.vencimento2 = linhaVencimento[1];
		this.valor2 = linhaValor[1];

		this.mesAno3 = linhaMesAno[2];
		this.vencimento3 = linhaVencimento[2];
		this.valor3 = linhaValor[2];

	}

	public String getMesAno1(){

		return mesAno1;
	}

	public void setMesAno1(String mesAno1){

		this.mesAno1 = mesAno1;
	}

	public String getVencimento1(){

		return vencimento1;
	}

	public void setVencimento1(String vencimento1){

		this.vencimento1 = vencimento1;
	}

	public String getValor1(){

		return valor1;
	}

	public void setValor1(String valor1){

		this.valor1 = valor1;
	}

	public String getMesAno2(){

		return mesAno2;
	}

	public void setMesAno2(String mesAno2){

		this.mesAno2 = mesAno2;
	}

	public String getVencimento2(){

		return vencimento2;
	}

	public void setVencimento2(String vencimento2){

		this.vencimento2 = vencimento2;
	}

	public String getValor2(){

		return valor2;
	}

	public void setValor2(String valor2){

		this.valor2 = valor2;
	}

	public String getMesAno3(){

		return mesAno3;
	}

	public void setMesAno3(String mesAno3){

		this.mesAno3 = mesAno3;
	}

	public String getVencimento3(){

		return vencimento3;
	}

	public void setVencimento3(String vencimento3){

		this.vencimento3 = vencimento3;
	}

	public String getValor3(){

		return valor3;
	}

	public void setValor3(String valor3){

		this.valor3 = valor3;
	}

}
