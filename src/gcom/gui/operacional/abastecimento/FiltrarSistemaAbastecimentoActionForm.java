
package gcom.gui.operacional.abastecimento;

import org.apache.struts.action.ActionForm;

/**
 * [UCXXXX - Filtrar Sistema de Abastecimento]
 * 
 * @author Anderson Italo
 * @date 28/01/2011
 */

public class FiltrarSistemaAbastecimentoActionForm
				extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String codigo;

	private String descricao;

	private String descricaoAbreviada;

	private String indicadorUso;

	private String tipoPesquisa;

	private String indicadorAtualizar;

	public String getMesAno(Integer anoMes){

		String anoMesStr = anoMes + "";
		String mesAno = "";

		mesAno = anoMesStr.substring(0, 4) + "/" + anoMesStr.substring(4, 6);

		return mesAno;
	}

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
