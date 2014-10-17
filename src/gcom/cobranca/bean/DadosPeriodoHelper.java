/**
 * 
 */

package gcom.cobranca.bean;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author isilva
 */
public class DadosPeriodoHelper
				implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String periodo;

	private Integer quantidadeEnviada;

	private Integer quantidadePendente;

	private Integer quantidadeCancelada;

	private Integer quantidadeExecutada;

	// *********** Porcentagem *************

	/**
	 * Porcentagem
	 */
	private String evolucaoEnviada;

	/**
	 * Porcentagem
	 */
	private String evolucaoExecutada;

	// ************* Acumulado **************

	private Integer acumuladoEnviada;

	private Integer acumuladaExecutada;

	/**
	 * Porcentagem
	 */
	private String sucessoExecutadaPorEnviada;

	private String sucessoAcumulada;

	// **************** Média ****************
	private BigDecimal mediaPorDia;

	public DadosPeriodoHelper() {

	}

	/**
	 * @return the periodo
	 */
	public String getPeriodo(){

		return periodo;
	}

	/**
	 * @param periodo
	 *            the periodo to set
	 */
	public void setPeriodo(String periodo){

		this.periodo = periodo;
	}

	/**
	 * @return the quantidadeEnviada
	 */
	public Integer getQuantidadeEnviada(){

		return quantidadeEnviada;
	}

	/**
	 * @param quantidadeEnviada
	 *            the quantidadeEnviada to set
	 */
	public void setQuantidadeEnviada(Integer quantidadeEnviada){

		this.quantidadeEnviada = quantidadeEnviada;
	}

	/**
	 * @return the quantidadePendente
	 */
	public Integer getQuantidadePendente(){

		return quantidadePendente;
	}

	/**
	 * @param quantidadePendente
	 *            the quantidadePendente to set
	 */
	public void setQuantidadePendente(Integer quantidadePendente){

		this.quantidadePendente = quantidadePendente;
	}

	/**
	 * @return the quantidadeCancelada
	 */
	public Integer getQuantidadeCancelada(){

		return quantidadeCancelada;
	}

	/**
	 * @param quantidadeCancelada
	 *            the quantidadeCancelada to set
	 */
	public void setQuantidadeCancelada(Integer quantidadeCancelada){

		this.quantidadeCancelada = quantidadeCancelada;
	}

	/**
	 * @return the quantidadeExecutada
	 */
	public Integer getQuantidadeExecutada(){

		return quantidadeExecutada;
	}

	/**
	 * @param quantidadeExecutada
	 *            the quantidadeExecutada to set
	 */
	public void setQuantidadeExecutada(Integer quantidadeExecutada){

		this.quantidadeExecutada = quantidadeExecutada;
	}

	/**
	 * @return the evolucaoEnviada
	 */
	public String getEvolucaoEnviada(){

		return evolucaoEnviada;
	}

	/**
	 * @param evolucaoEnviada
	 *            the evolucaoEnviada to set
	 */
	public void setEvolucaoEnviada(String evolucaoEnviada){

		this.evolucaoEnviada = evolucaoEnviada;
	}

	/**
	 * @return the evolucaoExecutada
	 */
	public String getEvolucaoExecutada(){

		return evolucaoExecutada;
	}

	/**
	 * @param evolucaoExecutada
	 *            the evolucaoExecutada to set
	 */
	public void setEvolucaoExecutada(String evolucaoExecutada){

		this.evolucaoExecutada = evolucaoExecutada;
	}

	/**
	 * @return the acumuladoEnviada
	 */
	public Integer getAcumuladoEnviada(){

		return acumuladoEnviada;
	}

	/**
	 * @param acumuladoEnviada
	 *            the acumuladoEnviada to set
	 */
	public void setAcumuladoEnviada(Integer acumuladoEnviada){

		this.acumuladoEnviada = acumuladoEnviada;
	}

	/**
	 * @return the acumuladaExecutada
	 */
	public Integer getAcumuladaExecutada(){

		return acumuladaExecutada;
	}

	/**
	 * @param acumuladaExecutada
	 *            the acumuladaExecutada to set
	 */
	public void setAcumuladaExecutada(Integer acumuladaExecutada){

		this.acumuladaExecutada = acumuladaExecutada;
	}

	/**
	 * @return the sucessoExecutadaPorEnviada
	 */
	public String getSucessoExecutadaPorEnviada(){

		return sucessoExecutadaPorEnviada;
	}

	/**
	 * @param sucessoExecutadaPorEnviada
	 *            the sucessoExecutadaPorEnviada to set
	 */
	public void setSucessoExecutadaPorEnviada(String sucessoExecutadaPorEnviada){

		this.sucessoExecutadaPorEnviada = sucessoExecutadaPorEnviada;
	}

	/**
	 * @return the sucessoAcumulada
	 */
	public String getSucessoAcumulada(){

		return sucessoAcumulada;
	}

	/**
	 * @param sucessoAcumulada
	 *            the sucessoAcumulada to set
	 */
	public void setSucessoAcumulada(String sucessoAcumulada){

		this.sucessoAcumulada = sucessoAcumulada;
	}

	/**
	 * @return the mediaPorDia
	 */
	public BigDecimal getMediaPorDia(){

		return mediaPorDia;
	}

	/**
	 * @param mediaPorDia
	 *            the mediaPorDia to set
	 */
	public void setMediaPorDia(BigDecimal mediaPorDia){

		this.mediaPorDia = mediaPorDia;
	}
}
