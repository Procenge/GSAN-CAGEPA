
package gcom.gui.seguranca.sistema;

import org.apache.struts.action.ActionForm;

public class ParametroSistemaActionForm
				extends ActionForm {

	private String codigo;

	private String valor;

	private String uso;

	private String tipoPesquisaValor;

	private String tipoPesquisaCodigo;

	public String getCodigo(){

		return codigo;
	}

	public void setCodigo(String codigo){

		this.codigo = codigo;
	}

	public String getValor(){

		return valor;
	}

	public void setValor(String valor){

		this.valor = valor;
	}

	public String getUso(){

		return uso;
	}

	public void setUso(String uso){

		this.uso = uso;
	}

	public String getTipoPesquisaValor(){

		return tipoPesquisaValor;
	}

	public void setTipoPesquisaValor(String tipoPesquisa){

		this.tipoPesquisaValor = tipoPesquisa;
	}

	public String getTipoPesquisaCodigo(){

		return tipoPesquisaCodigo;
	}

	public void setTipoPesquisaCodigo(String tipoPesquisaCodigo){

		this.tipoPesquisaCodigo = tipoPesquisaCodigo;
	}

}
