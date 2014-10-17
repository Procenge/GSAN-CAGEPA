
package gcom.atendimentopublico.ordemservico.bean;

import java.io.Serializable;

/**
 * @author Carlos Chrystian
 * @date 10/04/2013
 */
public class CartaHidrometroModelo1Helper
				implements Serializable {

	private static final long serialVersionUID = 1L;

	// Dados Gerais
	private String numeroPagina;

	private String clienteNome;

	private String matricula;

	private String endereco;

	private String ordemServico;

	public String getNumeroPagina(){

		return numeroPagina;
	}

	public void setNumeroPagina(String numeroPagina){

		this.numeroPagina = numeroPagina;
	}

	public String getClienteNome(){

		return clienteNome;
	}

	public void setClienteNome(String clienteNome){

		this.clienteNome = clienteNome;
	}


	public String getMatricula(){

		return matricula;
	}


	public void setMatricula(String matricula){

		this.matricula = matricula;
	}

	public String getEndereco(){

		return endereco;
	}

	public void setEndereco(String endereco){

		this.endereco = endereco;
	}

	public String getOrdemServico(){

		return ordemServico;
	}

	public void setOrdemServico(String ordemServico){

		this.ordemServico = ordemServico;
	}

}
