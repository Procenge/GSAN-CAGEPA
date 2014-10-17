package gcom.faturamento.debito;


/**
 * [UC3103] Cancelar Débito a Cobrar de Rateio por Macromedidor
 * 6.4.7. Histórico de Medição Individualizada
 * 
 * @author Josenildo Neves
 *         Date 02/09/2013
 */
public class HistoricoMedicaoIndividualizadaHelper {
	
	// id do Imóvel
	private Integer idImovel;
	
	// 6.4.7.1. Tipo de Rateio
	private String dsRateioTipo;

	// 6.4.7.2. Quantidade de Imóveis vinculados
	private Integer quantidadeImoveisVinculados;

	// 6.4.7.3. Tipo de Consumo
	private String dsConsumoTipo;

	// 6.4.7.4. Consumo de Água Medido
	private Integer consumoMedidoMes;

	// //6.4.7.5. Consumo Rateado
	private Integer consumoRateio;

	// 6.4.7.6. Consumo de Água Faturado
	private Integer consumoFaturadoMes;

	/**
	 * @param idImovel
	 * @param dsRateioTipo
	 * @param dsConsumoTipo
	 * @param consumoMedidoMes
	 * @param consumoRateio
	 * @param consumoFaturadoMes
	 */
	public HistoricoMedicaoIndividualizadaHelper(Integer idImovel, String dsRateioTipo, String dsConsumoTipo, Integer consumoMedidoMes,
													Integer consumoRateio, Integer consumoFaturadoMes) {

		super();
		this.idImovel = idImovel;
		this.dsRateioTipo = dsRateioTipo;
		this.dsConsumoTipo = dsConsumoTipo;
		this.consumoMedidoMes = consumoMedidoMes;
		this.consumoRateio = consumoRateio;
		this.consumoFaturadoMes = consumoFaturadoMes;
	}


	/**
	 * @return the idImovel
	 */
	public Integer getIdImovel(){

		return idImovel;
	}


	/**
	 * @param idImovel
	 *            the idImovel to set
	 */
	public void setIdImovel(Integer idImovel){

		this.idImovel = idImovel;
	}


	/**
	 * @return the dsRateioTipo
	 */
	public String getDsRateioTipo(){

		return dsRateioTipo;
	}


	/**
	 * @param dsRateioTipo
	 *            the dsRateioTipo to set
	 */
	public void setDsRateioTipo(String dsRateioTipo){

		this.dsRateioTipo = dsRateioTipo;
	}


	/**
	 * @return the quantidadeImoveisVinculados
	 */
	public Integer getQuantidadeImoveisVinculados(){

		return quantidadeImoveisVinculados;
	}


	/**
	 * @param quantidadeImoveisVinculados
	 *            the quantidadeImoveisVinculados to set
	 */
	public void setQuantidadeImoveisVinculados(Integer quantidadeImoveisVinculados){

		this.quantidadeImoveisVinculados = quantidadeImoveisVinculados;
	}


	/**
	 * @return the dsConsumoTipo
	 */
	public String getDsConsumoTipo(){

		return dsConsumoTipo;
	}


	/**
	 * @param dsConsumoTipo
	 *            the dsConsumoTipo to set
	 */
	public void setDsConsumoTipo(String dsConsumoTipo){

		this.dsConsumoTipo = dsConsumoTipo;
	}


	/**
	 * @return the consumoMedidoMes
	 */
	public Integer getConsumoMedidoMes(){

		return consumoMedidoMes;
	}


	/**
	 * @param consumoMedidoMes
	 *            the consumoMedidoMes to set
	 */
	public void setConsumoMedidoMes(Integer consumoMedidoMes){

		this.consumoMedidoMes = consumoMedidoMes;
	}


	/**
	 * @return the consumoRateio
	 */
	public Integer getConsumoRateio(){

		return consumoRateio;
	}


	/**
	 * @param consumoRateio
	 *            the consumoRateio to set
	 */
	public void setConsumoRateio(Integer consumoRateio){

		this.consumoRateio = consumoRateio;
	}


	/**
	 * @return the consumoFaturadoMes
	 */
	public Integer getConsumoFaturadoMes(){

		return consumoFaturadoMes;
	}


	/**
	 * @param consumoFaturadoMes
	 *            the consumoFaturadoMes to set
	 */
	public void setConsumoFaturadoMes(Integer consumoFaturadoMes){

		this.consumoFaturadoMes = consumoFaturadoMes;
	}


}
