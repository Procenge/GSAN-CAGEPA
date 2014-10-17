
package gcom.relatorio.cobranca;

import gcom.relatorio.RelatorioBean;

public class SubRelatorioOrdemCorteModelo5
				implements RelatorioBean {

	private String conta;

	private String valorConta;

	private String situacao;

	public SubRelatorioOrdemCorteModelo5(String[] linhaMesAno, String[] linhaValor, String[] linhaSituacao) {

		this.conta = linhaMesAno[0];
		this.valorConta = linhaValor[0];
		this.situacao = linhaSituacao[0];
	}

	public String getConta(){

		return conta;
	}

	public void setConta(String conta){

		this.conta = conta;
	}

	public String getValorConta(){

		return valorConta;
	}

	public void setValorConta(String valorConta){

		this.valorConta = valorConta;
	}

	public String getSituacao(){

		return situacao;
	}

	public void setSituacao(String situacao){

		this.situacao = situacao;
	}


}
