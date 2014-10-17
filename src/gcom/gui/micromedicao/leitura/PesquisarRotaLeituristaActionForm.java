/**
 * 
 */

package gcom.gui.micromedicao.leitura;

import org.apache.struts.validator.ValidatorActionForm;

/**
 * @author Péricles Tavares
 * @date 09/08/2011
 */
public class PesquisarRotaLeituristaActionForm
				extends ValidatorActionForm {

	private String idEmpresa;

	public static String EMPRESA = "empresa";

	private Boolean editarEmpresa;

	private String idLeiturista;

	public static String LEITURISTA = "leiturista";

	private Boolean editarLeiturista;

	public String getIdEmpresa(){

		return idEmpresa;
	}

	public void setIdEmpresa(String idEmpresa){

		this.idEmpresa = idEmpresa;
	}

	public String getIdLeiturista(){

		return idLeiturista;
	}

	public void setIdLeiturista(String idLeiturista){

		this.idLeiturista = idLeiturista;
	}

	public Boolean getEditarEmpresa(){

		return editarEmpresa;
	}

	public void setEditarEmpresa(Boolean editarEmpresa){

		this.editarEmpresa = editarEmpresa;
	}

	public Boolean getEditarLeiturista(){

		return editarLeiturista;
	}

	public void setEditarLeiturista(Boolean editarLeiturista){

		this.editarLeiturista = editarLeiturista;
	}

}
