
package gcom.gui.atendimentopublico.ordemservico;

/**
 * Consumo Médio do Imóvel
 * 
 * @author Hebert Falcão
 * @date 12/05/2011
 */
public class ConsumoMedioImovelHelper {

	private Long id;

	private Integer consumoMedioInicial;

	private Integer consumoMedioFinal;

	public Long getId(){

		return id;
	}

	public void setId(Long id){

		this.id = id;
	}

	public Integer getConsumoMedioInicial(){

		return consumoMedioInicial;
	}

	public void setConsumoMedioInicial(Integer consumoMedioInicial){

		this.consumoMedioInicial = consumoMedioInicial;
	}

	public Integer getConsumoMedioFinal(){

		return consumoMedioFinal;
	}

	public void setConsumoMedioFinal(Integer consumoMedioFinal){

		this.consumoMedioFinal = consumoMedioFinal;
	}

}
