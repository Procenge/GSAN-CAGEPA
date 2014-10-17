package gcom.relatorio.cobranca;

import gcom.relatorio.RelatorioBean;

public class RelatorioAvisoDebitoModelo2SubVersoBean
				implements RelatorioBean {

	private String nomeClienteEntrega;

	private String enderecoClienteEntrega;

	private String inscricao;

	private String matricula;

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

	public String getInscricao(){

		return inscricao;
	}

	public void setInscricao(String inscricao){

		this.inscricao = inscricao;
	}

	public String getMatricula(){

		return matricula;
	}

	public void setMatricula(String matricula){

		this.matricula = matricula;
	}


}
