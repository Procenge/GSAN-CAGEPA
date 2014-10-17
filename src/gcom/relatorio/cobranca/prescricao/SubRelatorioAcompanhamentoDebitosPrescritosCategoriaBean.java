
package gcom.relatorio.cobranca.prescricao;

import gcom.relatorio.RelatorioBean;

public class SubRelatorioAcompanhamentoDebitosPrescritosCategoriaBean
				implements RelatorioBean {

	private String descricaoCategoria;

	private String descricaoSubcategoria;

	private String quantidadeEconomias;

	public String getDescricaoCategoria(){

		return descricaoCategoria;
	}

	public void setDescricaoCategoria(String descricaoCategoria){

		this.descricaoCategoria = descricaoCategoria;
	}

	public String getDescricaoSubcategoria(){

		return descricaoSubcategoria;
	}

	public void setDescricaoSubcategoria(String descricaoSubcategoria){

		this.descricaoSubcategoria = descricaoSubcategoria;
	}

	public String getQuantidadeEconomias(){

		return quantidadeEconomias;
	}

	public void setQuantidadeEconomias(String quantidadeEconomias){

		this.quantidadeEconomias = quantidadeEconomias;
	}


}