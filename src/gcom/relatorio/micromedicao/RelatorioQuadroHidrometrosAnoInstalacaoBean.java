package gcom.relatorio.micromedicao;

import gcom.relatorio.RelatorioBean;

/**
 * 
 * @author Ado Rocha
 * @created 06/12/2013
 */
public class RelatorioQuadroHidrometrosAnoInstalacaoBean
				implements RelatorioBean {

	private String descricaoLocalidade;

	private Integer idLocalidade;

	private Integer ano;

	private Integer quantidade;

	private String marca;

	public RelatorioQuadroHidrometrosAnoInstalacaoBean() {

	}

	public RelatorioQuadroHidrometrosAnoInstalacaoBean(String descricaoLocalidade, Integer idLocalidade, Integer ano, Integer quantidade,
														String marca) {

		super();
		this.descricaoLocalidade = descricaoLocalidade;
		this.idLocalidade = idLocalidade;
		this.ano = ano;
		this.quantidade = quantidade;
		this.marca = marca;

	}

	public String getDescricaoLocalidade(){

		return descricaoLocalidade;
	}

	public void setDescricaoLocalidade(String descricaoLocalidade){

		this.descricaoLocalidade = descricaoLocalidade;
	}

	public Integer getAno(){

		return ano;
	}

	public void setAno(Integer ano){

		this.ano = ano;
	}

	public Integer getQuantidade(){

		return quantidade;
	}

	public void setQuantidade(Integer quantidade){

		this.quantidade = quantidade;
	}

	public String getMarca(){

		return marca;
	}

	public void setMarca(String marca){

		this.marca = marca;
	}

	public Integer getIdLocalidade(){

		return idLocalidade;
	}

	public void setIdLocalidade(Integer idLocalidade){

		this.idLocalidade = idLocalidade;
	}

}
