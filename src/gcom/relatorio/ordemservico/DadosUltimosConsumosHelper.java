
package gcom.relatorio.ordemservico;

import java.io.Serializable;

/**
 * @author Carlos Chrystian
 * @date 10/04/2013
 */
public class DadosUltimosConsumosHelper
				implements Serializable {

	private static final long serialVersionUID = 1L;

	// 1.1. Referência do Consumo
	private String referenciaConsumo;

	// 1.2. Consumo Faturado
	private String consumoFaturado;

	// 1.3. Leitura Faturada
	private String leituraFaturada;

	public String getReferenciaConsumo(){

		return referenciaConsumo;
	}

	public void setReferenciaConsumo(String referenciaConsumo){

		this.referenciaConsumo = referenciaConsumo;
	}

	public String getConsumoFaturado(){

		return consumoFaturado;
	}

	public void setConsumoFaturado(String consumoFaturado){

		this.consumoFaturado = consumoFaturado;
	}

	public String getLeituraFaturada(){

		return leituraFaturada;
	}

	public void setLeituraFaturada(String leituraFaturada){

		this.leituraFaturada = leituraFaturada;
	}

}
