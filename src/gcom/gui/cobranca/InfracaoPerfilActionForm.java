
package gcom.gui.cobranca;

import org.apache.struts.action.ActionForm;

public class InfracaoPerfilActionForm
				extends ActionForm {

	private String idInfracaoTipo;

	private String idCategoria;

	private String idSubcategoria;

	private String idImovelPerfil;

	private String id;

	private String descricaoInfracaoTipo;

	public String getDescricaoInfracaoTipo(){

		return descricaoInfracaoTipo;
	}

	public void setDescricaoInfracaoTipo(String descricaoInfracaoTipo){

		this.descricaoInfracaoTipo = descricaoInfracaoTipo;
	}

	public String getId(){

		return id;
	}

	public void setId(String id){

		this.id = id;
	}

	public String getIdInfracaoTipo(){

		return idInfracaoTipo;
	}

	public void setIdInfracaoTipo(String idInfracaoTipo){

		this.idInfracaoTipo = idInfracaoTipo;
	}

	public String getIdCategoria(){

		return idCategoria;
	}

	public void setIdCategoria(String idCategoria){

		this.idCategoria = idCategoria;
	}

	public String getIdSubcategoria(){

		return idSubcategoria;
	}

	public void setIdSubcategoria(String idSubcategoria){

		this.idSubcategoria = idSubcategoria;
	}

	public String getIdImovelPerfil(){

		return idImovelPerfil;
	}

	public void setIdImovelPerfil(String idImovelPerfil){

		this.idImovelPerfil = idImovelPerfil;
	}

}
