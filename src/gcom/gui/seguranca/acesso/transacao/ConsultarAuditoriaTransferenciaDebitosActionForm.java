
package gcom.gui.seguranca.acesso.transacao;

import org.apache.struts.action.ActionForm;

public class ConsultarAuditoriaTransferenciaDebitosActionForm
				extends ActionForm {

	private String idFuncionario;

	private String nomeFuncionario;

	private String idUsuarioOrigem;

	private String nomeUsuarioOrigem;

	private String idUsuarioDestino;

	private String nomeUsuarioDestino;

	private String dataInicial;

	private String dataFinal;

	public String getIdFuncionario(){

		return idFuncionario;
	}

	public void setIdFuncionario(String idFuncionario){

		this.idFuncionario = idFuncionario;
	}

	public String getIdUsuarioOrigem(){

		return idUsuarioOrigem;
	}

	public String getNomeFuncionario(){

		return nomeFuncionario;
	}

	public void setNomeFuncionario(String nomeFuncionario){

		this.nomeFuncionario = nomeFuncionario;
	}

	public void setIdUsuarioOrigem(String idUsuarioOrigem){

		this.idUsuarioOrigem = idUsuarioOrigem;
	}

	public String getIdUsuarioDestino(){

		return idUsuarioDestino;
	}

	public void setIdUsuarioDestino(String idUsuarioDestino){

		this.idUsuarioDestino = idUsuarioDestino;
	}

	public String getDataInicial(){

		return dataInicial;
	}

	public void setDataInicial(String dataInicial){

		this.dataInicial = dataInicial;
	}

	public String getDataFinal(){

		return dataFinal;
	}

	public void setDataFinal(String dataFinal){

		this.dataFinal = dataFinal;
	}

	public String getNomeUsuarioOrigem(){

		return nomeUsuarioOrigem;
	}

	public void setNomeUsuarioOrigem(String nomeUsuarioOrigem){

		this.nomeUsuarioOrigem = nomeUsuarioOrigem;
	}

	public String getNomeUsuarioDestino(){

		return nomeUsuarioDestino;
	}

	public void setNomeUsuarioDestino(String nomeUsuarioDestino){

		this.nomeUsuarioDestino = nomeUsuarioDestino;
	}

}