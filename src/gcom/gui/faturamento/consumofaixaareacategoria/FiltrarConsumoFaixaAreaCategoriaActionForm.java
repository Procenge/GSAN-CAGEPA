
package gcom.gui.faturamento.consumofaixaareacategoria;

import org.apache.struts.validator.ValidatorActionForm;

/**
 * [UC3007] FILTRAR Consumo por Faixa de Área e Categoria
 * 
 * @author Ailton Sousa
 * @date 02/03/2011
 */
public class FiltrarConsumoFaixaAreaCategoriaActionForm
				extends ValidatorActionForm {

	private static final long serialVersionUID = 1L;

	private String categoria;

	private String faixaInicialArea;

	private String faixaFinalArea;

	private String consumoEstimadoArea;

	private String indicadorUso;

	public String getCategoria(){

		return categoria;
	}

	public void setCategoria(String categoria){

		this.categoria = categoria;
	}

	public String getFaixaInicialArea(){

		return faixaInicialArea;
	}

	public void setFaixaInicialArea(String faixaInicialArea){

		this.faixaInicialArea = faixaInicialArea;
	}

	public String getFaixaFinalArea(){

		return faixaFinalArea;
	}

	public void setFaixaFinalArea(String faixaFinalArea){

		this.faixaFinalArea = faixaFinalArea;
	}

	public String getConsumoEstimadoArea(){

		return consumoEstimadoArea;
	}

	public void setConsumoEstimadoArea(String consumoEstimadoArea){

		this.consumoEstimadoArea = consumoEstimadoArea;
	}

	public String getIndicadorUso(){

		return indicadorUso;
	}

	public void setIndicadorUso(String indicadorUso){

		this.indicadorUso = indicadorUso;
	}

}
