package gcom.faturamento.debito;

import java.math.BigDecimal;

/**
 * [UC3103] Cancelar Débito a Cobrar de Rateio por Macromedidor
 * 7. O sistema seleciona os débitos a cobrar de rateio dos imóveis vinculados para o mês informado.
 * 
 * @author Josenildo Neves
 *         Date 02/09/2013
 */
public class DebitosACobrarRateioImoveisVinculadosHelper {
	
	// id Debito A Cobrar
	private Integer idDebitoACobrar;

	// 7.2. Matrícula (IMOV_ID)
	private Integer idImovel;
	
	// id cliente
	private Integer idCliente;

	// 7.3. Nome do cliente usuário
	private String nomeCliente;

	// 7.4. Tipo de Consumo
	private String dsTipoConsumo;

	// 7.5. Consumo de Água Medido
	private Integer consumoMedidoAgua;

	// 7.6. Consumo Rateado
	private Integer consumoRateado;

	// 7.7. Consumo de Água Faturado
	private Integer consumoAguaFaturado;

	// 7.8. Tipo de Débito
	private Integer idDebitoTipo;

	// 7.8.1. Descrição do Tipo de Débito
	private String dsDebitoTipo;

	// 7.9. Valor do Rateio
	private BigDecimal valorRateado;

	/**
	 * @param idImovel
	 * @param idCliente
	 * @param nomeCliente
	 * @param dsTipoConsumo
	 * @param consumoMedidoAgua
	 * @param consumoRateado
	 * @param consumoAguaFaturado
	 * @param idDebitoTipo
	 * @param dsDebitoTipo
	 * @param valorRateado
	 */
	public DebitosACobrarRateioImoveisVinculadosHelper(Integer idDebitoACobrar, Integer idImovel, Integer idCliente, String nomeCliente,
														String dsTipoConsumo,
														Integer consumoMedidoAgua, Integer consumoRateado, Integer consumoAguaFaturado,
														Integer idDebitoTipo, String dsDebitoTipo, BigDecimal valorRateado) {

		super();
		this.idDebitoACobrar = idDebitoACobrar;
		this.idImovel = idImovel;
		this.idCliente = idCliente;
		this.nomeCliente = nomeCliente;
		this.dsTipoConsumo = dsTipoConsumo;
		this.consumoMedidoAgua = consumoMedidoAgua;
		this.consumoRateado = consumoRateado;
		this.consumoAguaFaturado = consumoAguaFaturado;
		this.idDebitoTipo = idDebitoTipo;
		this.dsDebitoTipo = dsDebitoTipo;
		this.valorRateado = valorRateado;
	}

	/**
	 * @return the idDebitoACobrar
	 */
	public Integer getIdDebitoACobrar(){

		return idDebitoACobrar;
	}

	/**
	 * @param idDebitoACobrar
	 *            the idDebitoACobrar to set
	 */
	public void setIdDebitoACobrar(Integer idDebitoACobrar){

		this.idDebitoACobrar = idDebitoACobrar;
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
	 * @return the idCliente
	 */
	public Integer getIdCliente(){

		return idCliente;
	}

	/**
	 * @param idCliente
	 *            the idCliente to set
	 */
	public void setIdCliente(Integer idCliente){

		this.idCliente = idCliente;
	}

	/**
	 * @return the nomeCliente
	 */
	public String getNomeCliente(){

		return nomeCliente;
	}

	/**
	 * @param nomeCliente
	 *            the nomeCliente to set
	 */
	public void setNomeCliente(String nomeCliente){

		this.nomeCliente = nomeCliente;
	}

	/**
	 * @return the dsTipoConsumo
	 */
	public String getDsTipoConsumo(){

		return dsTipoConsumo;
	}

	/**
	 * @param dsTipoConsumo
	 *            the dsTipoConsumo to set
	 */
	public void setDsTipoConsumo(String dsTipoConsumo){

		this.dsTipoConsumo = dsTipoConsumo;
	}

	/**
	 * @return the consumoMedidoAgua
	 */
	public Integer getConsumoMedidoAgua(){

		return consumoMedidoAgua;
	}

	/**
	 * @param consumoMedidoAgua
	 *            the consumoMedidoAgua to set
	 */
	public void setConsumoMedidoAgua(Integer consumoMedidoAgua){

		this.consumoMedidoAgua = consumoMedidoAgua;
	}

	/**
	 * @return the consumoRateado
	 */
	public Integer getConsumoRateado(){

		return consumoRateado;
	}

	/**
	 * @param consumoRateado
	 *            the consumoRateado to set
	 */
	public void setConsumoRateado(Integer consumoRateado){

		this.consumoRateado = consumoRateado;
	}

	/**
	 * @return the consumoAguaFaturado
	 */
	public Integer getConsumoAguaFaturado(){

		return consumoAguaFaturado;
	}

	/**
	 * @param consumoAguaFaturado
	 *            the consumoAguaFaturado to set
	 */
	public void setConsumoAguaFaturado(Integer consumoAguaFaturado){

		this.consumoAguaFaturado = consumoAguaFaturado;
	}

	/**
	 * @return the idDebitoTipo
	 */
	public Integer getIdDebitoTipo(){

		return idDebitoTipo;
	}

	/**
	 * @param idDebitoTipo
	 *            the idDebitoTipo to set
	 */
	public void setIdDebitoTipo(Integer idDebitoTipo){

		this.idDebitoTipo = idDebitoTipo;
	}

	/**
	 * @return the dsDebitoTipo
	 */
	public String getDsDebitoTipo(){

		return dsDebitoTipo;
	}

	/**
	 * @param dsDebitoTipo
	 *            the dsDebitoTipo to set
	 */
	public void setDsDebitoTipo(String dsDebitoTipo){

		this.dsDebitoTipo = dsDebitoTipo;
	}

	/**
	 * @return the valorRateado
	 */
	public BigDecimal getValorRateado(){

		return valorRateado;
	}

	/**
	 * @param valorRateado
	 *            the valorRateado to set
	 */
	public void setValorRateado(BigDecimal valorRateado){

		this.valorRateado = valorRateado;
	}

}
