package gcom.relatorio.faturamento.conta;

import gcom.relatorio.RelatorioBean;

public class RelatorioContaModelo2SubVersoBean
				implements RelatorioBean {

	private String nomeClienteEntrega;

	private String enderecoClienteEntrega;

	public String getNomeClienteEntrega(){

		return nomeClienteEntrega;
	}

	public void setNomeClienteEntrega(String nomeClienteEntrega){

		this.nomeClienteEntrega = nomeClienteEntrega;
	}

	public String getEnderecoClienteEntrega(){

		return enderecoClienteEntrega;
	}

	public void setEnderecoClienteEntrega(String enderecoClienteEntrega){

		this.enderecoClienteEntrega = enderecoClienteEntrega;
	}



}
