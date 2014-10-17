
package gcom.relatorio.cadastro.endereco;

import gcom.relatorio.RelatorioBean;


public class RelatorioManterCepBean
				implements RelatorioBean {

	private String logradouro;

	private String bairro;

	private String municipio;

	private String uf;

	private String cep;

	private String faixa;


	public RelatorioManterCepBean(String logradouro, String bairro, String municipio, String uf, String cep, String faixa) {

		this.logradouro = logradouro;
		this.bairro = bairro;
		this.municipio = municipio;
		this.uf = uf;
		this.cep = cep;
		this.faixa = faixa;
	}

	public String getLogradouro(){

		return logradouro;
	}

	public void setLogradouro(String logradouro){

		this.logradouro = logradouro;
	}

	public String getBairro(){

		return bairro;
	}

	public void setBairro(String bairro){

		this.bairro = bairro;
	}

	public String getMunicipio(){

		return municipio;
	}

	public void setMunicipio(String municipio){

		this.municipio = municipio;
	}

	public String getUf(){

		return uf;
	}

	public void setUf(String uf){

		this.uf = uf;
	}

	public String getCep(){

		return cep;
	}

	public void setCep(String cep){

		this.cep = cep;
	}

	public String getFaixa(){

		return faixa;
	}

	public void setFaixa(String faixa){

		this.faixa = faixa;
	}

}
