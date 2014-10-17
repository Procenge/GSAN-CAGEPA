/**
 * 
 */

package gcom.cobranca.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author isilva
 */
public class ProdutividadeMensalExecucaoServicoRelatorioHelper
				implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String nomeEmpresa;

	private Collection<DadosPeriodoHelper> dadosPeriodoHelpers;

	private Integer acumuladoEnviadaTotalPorEmpresa;

	private Integer acumuladaExecutadaTotalPorEmpresa;

	/**
	 * Porcentagem
	 */
	private String evolucaoEnviadasTotalPorEmpresa;

	private String evolucaoExecutadasTotalPorEmpresa;

	private String acumuladaEnviadaPorEmpresa;

	private String acumuladaExecutadaPorEmpresa;

	private String sucessoExecutadaPorEnviadaTotalPorEmpresa;

	private String sucessoAcumuladaTotalPorEmpresa;

	// **************** Média ****************
	private BigDecimal mediaPorDiaTotalPorEmpresa;

	public ProdutividadeMensalExecucaoServicoRelatorioHelper() {

	}

	/**
	 * @return the nomeEmpresa
	 */
	public String getNomeEmpresa(){

		return nomeEmpresa;
	}

	/**
	 * @param nomeEmpresa
	 *            the nomeEmpresa to set
	 */
	public void setNomeEmpresa(String nomeEmpresa){

		this.nomeEmpresa = nomeEmpresa;
	}

	/**
	 * @return the dadosPeriodoHelpers
	 */
	public Collection<DadosPeriodoHelper> getDadosPeriodoHelpers(){

		if(this.dadosPeriodoHelpers == null || this.dadosPeriodoHelpers.isEmpty()){
			this.dadosPeriodoHelpers = new ArrayList<DadosPeriodoHelper>();
		}

		return dadosPeriodoHelpers;
	}

	/**
	 * @param dadosPeriodoHelpers
	 *            the dadosPeriodoHelpers to set
	 */
	public void setDadosPeriodoHelpers(Collection<DadosPeriodoHelper> dadosPeriodoHelpers){

		this.dadosPeriodoHelpers = dadosPeriodoHelpers;
	}

	/**
	 * @return the acumuladoEnviadaTotalPorEmpresa
	 */
	public Integer getAcumuladoEnviadaTotalPorEmpresa(){

		return acumuladoEnviadaTotalPorEmpresa;
	}

	/**
	 * @param acumuladoEnviadaTotalPorEmpresa
	 *            the acumuladoEnviadaTotalPorEmpresa to set
	 */
	public void setAcumuladoEnviadaTotalPorEmpresa(Integer acumuladoEnviadaTotalPorEmpresa){

		this.acumuladoEnviadaTotalPorEmpresa = acumuladoEnviadaTotalPorEmpresa;
	}

	/**
	 * @return the acumuladaExecutadaTotalPorEmpresa
	 */
	public Integer getAcumuladaExecutadaTotalPorEmpresa(){

		return acumuladaExecutadaTotalPorEmpresa;
	}

	/**
	 * @param acumuladaExecutadaTotalPorEmpresa
	 *            the acumuladaExecutadaTotalPorEmpresa to set
	 */
	public void setAcumuladaExecutadaTotalPorEmpresa(Integer acumuladaExecutadaTotalPorEmpresa){

		this.acumuladaExecutadaTotalPorEmpresa = acumuladaExecutadaTotalPorEmpresa;
	}

	/**
	 * @return the evolucaoEnviadasTotalPorEmpresa
	 */
	public String getEvolucaoEnviadasTotalPorEmpresa(){

		return evolucaoEnviadasTotalPorEmpresa;
	}

	/**
	 * @param evolucaoEnviadasTotalPorEmpresa
	 *            the evolucaoEnviadasTotalPorEmpresa to set
	 */
	public void setEvolucaoEnviadasTotalPorEmpresa(String evolucaoEnviadasTotalPorEmpresa){

		this.evolucaoEnviadasTotalPorEmpresa = evolucaoEnviadasTotalPorEmpresa;
	}

	/**
	 * @return the evolucaoExecutadasTotalPorEmpresa
	 */
	public String getEvolucaoExecutadasTotalPorEmpresa(){

		return evolucaoExecutadasTotalPorEmpresa;
	}

	/**
	 * @param evolucaoExecutadasTotalPorEmpresa
	 *            the evolucaoExecutadasTotalPorEmpresa to set
	 */
	public void setEvolucaoExecutadasTotalPorEmpresa(String evolucaoExecutadasTotalPorEmpresa){

		this.evolucaoExecutadasTotalPorEmpresa = evolucaoExecutadasTotalPorEmpresa;
	}

	/**
	 * @return the acumuladaEnviadaPorEmpresa
	 */
	public String getAcumuladaEnviadaPorEmpresa(){

		return acumuladaEnviadaPorEmpresa;
	}

	/**
	 * @param acumuladaEnviadaPorEmpresa
	 *            the acumuladaEnviadaPorEmpresa to set
	 */
	public void setAcumuladaEnviadaPorEmpresa(String acumuladaEnviadaPorEmpresa){

		this.acumuladaEnviadaPorEmpresa = acumuladaEnviadaPorEmpresa;
	}

	/**
	 * @return the acumuladaExecutadaPorEmpresa
	 */
	public String getAcumuladaExecutadaPorEmpresa(){

		return acumuladaExecutadaPorEmpresa;
	}

	/**
	 * @param acumuladaExecutadaPorEmpresa
	 *            the acumuladaExecutadaPorEmpresa to set
	 */
	public void setAcumuladaExecutadaPorEmpresa(String acumuladaExecutadaPorEmpresa){

		this.acumuladaExecutadaPorEmpresa = acumuladaExecutadaPorEmpresa;
	}

	/**
	 * @return the sucessoExecutadaPorEnviadaTotalPorEmpresa
	 */
	public String getSucessoExecutadaPorEnviadaTotalPorEmpresa(){

		return sucessoExecutadaPorEnviadaTotalPorEmpresa;
	}

	/**
	 * @param sucessoExecutadaPorEnviadaTotalPorEmpresa
	 *            the sucessoExecutadaPorEnviadaTotalPorEmpresa to set
	 */
	public void setSucessoExecutadaPorEnviadaTotalPorEmpresa(String sucessoExecutadaPorEnviadaTotalPorEmpresa){

		this.sucessoExecutadaPorEnviadaTotalPorEmpresa = sucessoExecutadaPorEnviadaTotalPorEmpresa;
	}

	/**
	 * @return the sucessoAcumuladaTotalPorEmpresa
	 */
	public String getSucessoAcumuladaTotalPorEmpresa(){

		return sucessoAcumuladaTotalPorEmpresa;
	}

	/**
	 * @param sucessoAcumuladaTotalPorEmpresa
	 *            the sucessoAcumuladaTotalPorEmpresa to set
	 */
	public void setSucessoAcumuladaTotalPorEmpresa(String sucessoAcumuladaTotalPorEmpresa){

		this.sucessoAcumuladaTotalPorEmpresa = sucessoAcumuladaTotalPorEmpresa;
	}

	/**
	 * @return the mediaPorDiaTotalPorEmpresa
	 */
	public BigDecimal getMediaPorDiaTotalPorEmpresa(){

		return mediaPorDiaTotalPorEmpresa;
	}

	/**
	 * @param mediaPorDiaTotalPorEmpresa
	 *            the mediaPorDiaTotalPorEmpresa to set
	 */
	public void setMediaPorDiaTotalPorEmpresa(BigDecimal mediaPorDiaTotalPorEmpresa){

		this.mediaPorDiaTotalPorEmpresa = mediaPorDiaTotalPorEmpresa;
	}
}
