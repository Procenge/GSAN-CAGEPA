
package gcom.gui.operacional.abastecimento;

import org.apache.struts.action.ActionForm;

/**
 * [UCXXXX - Filtrar Setor de Abastecimento]
 * 
 * @author Péricles Tavares
 * @date 08/02/2011
 */

public class FiltrarSetorAbastecimentoActionForm
				extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String indicadorUso;

	private String codigo;

	private String descricao;

	private String descricaoAbreviada;

	private String distritoOperacional;

	private String sistemaAbastecimento;

	private String zonaAbastecimento;

	private String tipoPesquisa;

	private String indicadorAtualizar;

	public String getCodigo(){

		return codigo;
	}

	public void setCodigo(String codigo){

		this.codigo = codigo;
	}

	public String getDescricao(){

		return descricao;
	}

	public void setDescricao(String descricao){

		this.descricao = descricao;
	}

	public String getDescricaoAbreviada(){

		return descricaoAbreviada;
	}

	public void setDescricaoAbreviada(String descricaoAbreviada){

		this.descricaoAbreviada = descricaoAbreviada;
	}

	public String getIndicadorUso(){

		return indicadorUso;
	}

	public void setIndicadorUso(String indicadorUso){

		this.indicadorUso = indicadorUso;
	}

	public String getDistritoOperacional(){

		return distritoOperacional;
	}

	public void setDistritoOperacional(String distritoOperacional){

		this.distritoOperacional = distritoOperacional;
	}

	public String getSistemaAbastecimento(){

		return sistemaAbastecimento;
	}

	public void setSistemaAbastecimento(String sistemaAbastecimento){

		this.sistemaAbastecimento = sistemaAbastecimento;
	}

	public String getZonaAbastecimento(){

		return zonaAbastecimento;
	}

	public void setZonaAbastecimento(String zonaAbastecimento){

		this.zonaAbastecimento = zonaAbastecimento;
	}

	public String getTipoPesquisa(){

		return tipoPesquisa;
	}

	public void setTipoPesquisa(String tipoPesquisa){

		this.tipoPesquisa = tipoPesquisa;
	}

	public String getIndicadorAtualizar(){

		return indicadorAtualizar;
	}

	public void setIndicadorAtualizar(String indicadorAtualizar){

		this.indicadorAtualizar = indicadorAtualizar;
	}

}
