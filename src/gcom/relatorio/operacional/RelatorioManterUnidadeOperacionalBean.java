/**
 * 
 */

package gcom.relatorio.operacional;

import gcom.relatorio.RelatorioBean;

/**
 * Bean respons�vel de pegar os parametros que ser�o exibidos na parte de detail
 * do relat�rio.
 * 
 * @author P�ricles Tavares
 * @created 02/02/2011
 */
public class RelatorioManterUnidadeOperacionalBean
				implements RelatorioBean {

	private String codigo;

	private String descricao;

	private String indicadorUso;

	public RelatorioManterUnidadeOperacionalBean(String codigo, String descricao, String indicadorUso) {

		this.codigo = codigo;
		this.descricao = descricao;
		this.indicadorUso = indicadorUso;
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

	public String getIndicadorUso(){

		return indicadorUso;
	}

	public void setIndicadorUso(String indicadorUso){

		this.indicadorUso = indicadorUso;
	}

}
