package gcom.relatorio.micromedicao;

import gcom.relatorio.RelatorioBean;

/**
 * 
 * @author Ado Rocha
 * @created 06/12/2013
 */
public class RelatorioQuadroHidrometrosSituacaoBean
				implements RelatorioBean {

	private String capacidade;

	private String diametro;

	private String marca;

	private Integer idLocalidade;

	private String descricaoLocalidade;

	private Integer idGerencia;

	private String descricaoGerencia;

	private Integer quantidade;

	private Integer idLeituraAnormalidade;

	private String descricaoLeituraAnormalidade;

	public RelatorioQuadroHidrometrosSituacaoBean(String capacidade, String diametro, String marca, Integer idLocalidade,
													String descricaoLocalidade, Integer idGerencia, String descricaoGerencia,
													Integer quantidade, Integer idLeituraAnormalidade, String descricaoLeituraAnormalidade) {

		super();
		this.capacidade = capacidade;
		this.diametro = diametro;
		this.marca = marca;
		this.idLocalidade = idLocalidade;
		this.descricaoLocalidade = descricaoLocalidade;
		this.idGerencia = idGerencia;
		this.descricaoGerencia = descricaoGerencia;
		this.quantidade = quantidade;
		this.idLeituraAnormalidade = idLeituraAnormalidade;
		this.descricaoLeituraAnormalidade = descricaoLeituraAnormalidade;
	}

	public RelatorioQuadroHidrometrosSituacaoBean() {

	}


	public String getCapacidade(){

		return capacidade;
	}


	public void setCapacidade(String capacidade){

		this.capacidade = capacidade;
	}

	public String getDiametro(){

		return diametro;
	}

	public void setDiametro(String diametro){

		this.diametro = diametro;
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

	public String getDescricaoLocalidade(){

		return descricaoLocalidade;
	}

	public void setDescricaoLocalidade(String descricaoLocalidade){

		this.descricaoLocalidade = descricaoLocalidade;
	}


	public Integer getIdGerencia(){

		return idGerencia;
	}


	public void setIdGerencia(Integer idGerencia){

		this.idGerencia = idGerencia;
	}

	public String getDescricaoGerencia(){

		return descricaoGerencia;
	}

	public void setDescricaoGerencia(String descricaoGerencia){

		this.descricaoGerencia = descricaoGerencia;
	}


	public Integer getQuantidade(){

		return quantidade;
	}


	public void setQuantidade(Integer quantidade){

		this.quantidade = quantidade;
	}

	public Integer getIdLeituraAnormalidade(){

		return idLeituraAnormalidade;
	}

	public void setIdLeituraAnormalidade(Integer idLeituraAnormalidade){

		this.idLeituraAnormalidade = idLeituraAnormalidade;
	}

	public String getDescricaoLeituraAnormalidade(){

		return descricaoLeituraAnormalidade;
	}

	public void setDescricaoLeituraAnormalidade(String descricaoLeituraAnormalidade){

		this.descricaoLeituraAnormalidade = descricaoLeituraAnormalidade;
	}



}
