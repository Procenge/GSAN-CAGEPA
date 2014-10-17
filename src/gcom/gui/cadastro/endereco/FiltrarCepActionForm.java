package gcom.gui.cadastro.endereco;

import org.apache.struts.action.ActionForm;

public class FiltrarCepActionForm
				extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String codigo;

	private String municipio;
	
	private String codigoMunicipio;

	private String bairro;
	
	private String codigoBairro;

	private String idBairro;

	private String logradouro;
	
	private String codigoLogradouro;

	private String atualizar;

	private String indicadorUso;

	public String getCodigo(){

		return codigo;
	}

	public void setCodigo(String codigo){

		this.codigo = codigo;
	}

	public String getMunicipio(){

		return municipio;
	}

	public void setMunicipio(String municipio){

		this.municipio = municipio;
	}
	
	public String getCodigoMunicipio() {
		return codigoMunicipio;
	}

	public void setCodigoMunicipio(String codigoMunicipio) {
		this.codigoMunicipio = codigoMunicipio;
	}

	public String getBairro(){

		return bairro;
	}

	public void setBairro(String bairro){

		this.bairro = bairro;
	}
	
	public String getCodigoBairro() {
		return codigoBairro;
	}

	public void setCodigoBairro(String codigoBairro) {
		this.codigoBairro = codigoBairro;
	}

	public String getIdBairro() {
		return idBairro;
	}

	public void setIdBairro(String idBairro) {
		this.idBairro = idBairro;
	}


	public String getLogradouro(){

		return logradouro;
	}

	public void setLogradouro(String logradouro){

		this.logradouro = logradouro;
	}
	
	public String getCodigoLogradouro() {
		return codigoLogradouro;
	}

	public void setCodigoLogradouro(String codigoLogradouro) {
		this.codigoLogradouro = codigoLogradouro;
	}


	public String getAtualizar(){

		return atualizar;
	}

	public void setAtualizar(String atualizar){

		this.atualizar = atualizar;
	}

	public String getIndicadorUso(){

		return indicadorUso;
	}

	public void setIndicadorUso(String indicadorUso){

		this.indicadorUso = indicadorUso;
	}

}
