package gcom.relatorio.micromedicao;

import gcom.relatorio.RelatorioBean;

/**
 * 
 * @author Ado Rocha
 * @created 06/12/2013
 */
public class RelatorioQuadroHidrometrosBean
				implements RelatorioBean {

	private String gerenciaRegional;

	private Integer idGerenciaRegional;

	private String localidade;

	private Integer idLocalidade;

	private String descricao;

	private String marca;

	private Integer quantidade;

	public RelatorioQuadroHidrometrosBean(String gerenciaRegional, Integer idGerenciaRegional, String localidade, Integer idLocalidade,
											String descricao, String marca, Integer quantidade) {

		super();
		this.gerenciaRegional = gerenciaRegional;
		this.localidade = localidade;
		this.descricao = descricao;
		this.marca = marca;
		this.quantidade = quantidade;
		this.idGerenciaRegional = idGerenciaRegional;
		this.idLocalidade = idLocalidade;

	}

	public String getGerenciaRegional(){

		return gerenciaRegional;
	}


	public void setGerenciaRegional(String gerenciaRegional){

		this.gerenciaRegional = gerenciaRegional;
	}

	public String getLocalidade(){

		return localidade;
	}

	public void setLocalidade(String localidade){

		this.localidade = localidade;
	}

	public String getDescricao(){

		return descricao;
	}

	public void setDescricao(String descricao){

		this.descricao = descricao;
	}

	public String getMarca(){

		return marca;
	}

	public void setMarca(String marca){

		this.marca = marca;
	}

	public Integer getQuantidade(){

		return quantidade;
	}

	public void setQuantidade(Integer quantidade){

		this.quantidade = quantidade;
	}

	public Integer getIdGerenciaRegional(){

		return idGerenciaRegional;
	}

	public void setIdGerenciaRegional(Integer idGerenciaRegional){

		this.idGerenciaRegional = idGerenciaRegional;
	}

	public Integer getIdLocalidade(){

		return idLocalidade;
	}

	public void setIdLocalidade(Integer idLocalidade){

		this.idLocalidade = idLocalidade;
	}

}
