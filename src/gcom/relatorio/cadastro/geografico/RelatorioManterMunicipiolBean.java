/**
 * 
 */

package gcom.relatorio.cadastro.geografico;

import gcom.relatorio.RelatorioBean;

/**
 * Bean responsável de pegar os parametros que serão exibidos na parte de detail
 * do relatório.
 * 
 * @author Péricles Tavares
 * @created 02/02/2011
 */
public class RelatorioManterMunicipiolBean
				implements RelatorioBean {

	private String codigo;

	private String nome;

	private String indicadorUso;

	private String uf;

	private Integer cepInicial;

	private Integer cepFim;

	public RelatorioManterMunicipiolBean(String codigo, String nome, String indicadorUso, String uf, Integer cepInicial, Integer cepFim) {

		this.codigo = codigo;
		this.nome = nome;
		this.indicadorUso = indicadorUso;
		this.uf = uf;
		this.cepInicial = cepInicial;
		this.cepFim = cepFim;
	}

	public String getCodigo(){

		return codigo;
	}

	public void setCodigo(String codigo){

		this.codigo = codigo;
	}

	public String getNome(){

		return nome;
	}

	public void setNome(String nome){

		this.nome = nome;
	}

	public String getIndicadorUso(){

		return indicadorUso;
	}

	public void setIndicadorUso(String indicadorUso){

		this.indicadorUso = indicadorUso;
	}

	public String getUf(){

		return uf;
	}

	public void setUf(String uf){

		this.uf = uf;
	}

	public Integer getCepInicial(){

		return cepInicial;
	}

	public void setCepInicial(Integer cepInicial){

		this.cepInicial = cepInicial;
	}

	public Integer getCepFim(){

		return cepFim;
	}

	public void setCepFim(Integer cepFim){

		this.cepFim = cepFim;
	}

}
