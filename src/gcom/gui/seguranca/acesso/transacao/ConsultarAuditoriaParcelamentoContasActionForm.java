package gcom.gui.seguranca.acesso.transacao;

import org.apache.struts.action.ActionForm;

public class ConsultarAuditoriaParcelamentoContasActionForm
				extends ActionForm {

	private String idFuncionario;

	private String nomeFuncionario;

	private String idUsuario;

	private String dataInicial;

	private String dataFinal;


	public String getIdFuncionario(){

		return idFuncionario;
	}

	public void setIdFuncionario(String idFuncionario){

		this.idFuncionario = idFuncionario;
	}

	public String getNomeFuncionario(){

		return nomeFuncionario;
	}

	public void setNomeFuncionario(String nomeFuncionario){

		this.nomeFuncionario = nomeFuncionario;
	}

	public String getIdUsuario(){

		return idUsuario;
	}

	public void setIdUsuario(String idUsuario){

		this.idUsuario = idUsuario;
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
}