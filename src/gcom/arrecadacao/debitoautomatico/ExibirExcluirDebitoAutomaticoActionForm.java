
package gcom.arrecadacao.debitoautomatico;

import org.apache.struts.action.ActionForm;

public class ExibirExcluirDebitoAutomaticoActionForm
				extends ActionForm {

	private String idImovel;

	private String inscricaoImovel;

	private String codigoBanco;

	private String codigoAgencia;

	private String identificacaoClienteBanco;

	private String dataOpcao;

	private String dataInclusao;

	public String getCodigoBanco(){

		return codigoBanco;
	}

	public void setCodigoBanco(String codigoBanco){

		this.codigoBanco = codigoBanco;
	}

	public String getCodigoAgencia(){

		return codigoAgencia;
	}

	public void setCodigoAgencia(String codigoAgencia){

		this.codigoAgencia = codigoAgencia;
	}

	public String getIdentificacaoClienteBanco(){

		return identificacaoClienteBanco;
	}

	public void setIdentificacaoClienteBanco(String identificacaoClienteBanco){

		this.identificacaoClienteBanco = identificacaoClienteBanco;
	}

	public String getDataOpcao(){

		return dataOpcao;
	}

	public void setDataOpcao(String dataOpcao){

		this.dataOpcao = dataOpcao;
	}

	public String getIdImovel(){

		return idImovel;
	}

	public void setIdImovel(String idImovel){

		this.idImovel = idImovel;
	}

	public String getInscricaoImovel(){

		return inscricaoImovel;
	}

	public void setInscricaoImovel(String inscricaoImovel){

		this.inscricaoImovel = inscricaoImovel;
	}

	public String getDataInclusao(){

		return dataInclusao;
	}

	public void setDataInclusao(String dataInclusao){

		this.dataInclusao = dataInclusao;
	}

}
