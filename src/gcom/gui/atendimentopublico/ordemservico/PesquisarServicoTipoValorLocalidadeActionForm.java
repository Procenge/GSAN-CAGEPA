package gcom.gui.atendimentopublico.ordemservico;

import org.apache.struts.validator.ValidatorActionForm;

/**
 * @author Ado Rocha
 * @date 29/04/2014
 */
public class PesquisarServicoTipoValorLocalidadeActionForm
				extends ValidatorActionForm {

	private static final long serialVersionUID = 1L;

	private String idLocalidadeFiltro;

	private String localidadeDescricaoFiltro;

	private String valor;

	private String method;

	public void reset(){

		this.idLocalidadeFiltro = null;
		this.localidadeDescricaoFiltro = null;
		this.valor = null;
		this.method = null;
	}


	public String getIdLocalidadeFiltro(){

		return idLocalidadeFiltro;
	}

	public void setIdLocalidadeFiltro(String idLocalidadeFiltro){

		this.idLocalidadeFiltro = idLocalidadeFiltro;
	}

	public String getLocalidadeDescricaoFiltro(){

		return localidadeDescricaoFiltro;
	}

	public void setLocalidadeDescricaoFiltro(String localidadeDescricaoFiltro){

		this.localidadeDescricaoFiltro = localidadeDescricaoFiltro;
	}

	public String getValor(){

		return valor;
	}



	public void setValor(String valor){

		this.valor = valor;
	}



	public String getMethod(){

		return method;
	}

	public void setMethod(String method){

		this.method = method;
	}

}