
package gcom.relatorio.cobranca.cobrancaadministrativa;

import gcom.relatorio.RelatorioBean;

import java.math.BigDecimal;

/**
 * [UC3060] Manter Imóvel Cobrança Administrativa
 * 
 * @author Carlos Chrystian Ramos
 * @date 22/02/2013
 */
public class RelatorioImoveisEmCobrancaAdministrativaDetalheDebitoBean
				implements RelatorioBean {

	// 1.3.13.1.2.3.1. Referência do Débito
	private String referenciaDebito;

	// 1.3.13.1.2.3.2. Tipo de Débito
	private String tipoDebito;

	// 1.3.13.1.2.3.3. Parcela
	private String parcela;

	// 1.3.13.1.2.3.4. Valor do Débito
	private BigDecimal valorDebito;

	// 1.3.13.1.2.3.5. Valor restante a ser cobrado
	private BigDecimal valorRestanteASerCobrado;

	// 1.3.11.1.2.2.6. Situação do Débito do Item
	private String descricaoSituacaoDebito;

	// 1.3.11.1.2.2.7. Data da Situação do Débito do Item
	private String dataSituacaoDebito;

	/**
	 * @return the referenciaDebito
	 */
	public String getReferenciaDebito(){

		return referenciaDebito;
	}

	/**
	 * @param referenciaDebito
	 *            the referenciaDebito to set
	 */
	public void setReferenciaDebito(String referenciaDebito){

		this.referenciaDebito = referenciaDebito;
	}

	/**
	 * @return the tipoDebito
	 */
	public String getTipoDebito(){

		return tipoDebito;
	}

	/**
	 * @param tipoDebito
	 *            the tipoDebito to set
	 */
	public void setTipoDebito(String tipoDebito){

		this.tipoDebito = tipoDebito;
	}

	/**
	 * @return the parcela
	 */
	public String getParcela(){

		return parcela;
	}

	/**
	 * @param parcela
	 *            the dataEmissao to set
	 */
	public void setParcela(String parcela){

		this.parcela = parcela;
	}

	/**
	 * @return the valorDebito
	 */
	public BigDecimal getValorDebito(){

		return valorDebito;
	}

	/**
	 * @param valorDebito
	 *            the valorDebito to set
	 */
	public void setValorDebito(BigDecimal valorDebito){

		this.valorDebito = valorDebito;
	}

	/**
	 * @return the valorRestanteASerCobrado
	 */
	public BigDecimal getValorRestanteASerCobrado(){

		return valorRestanteASerCobrado;
	}

	/**
	 * @param valorRestanteASerCobrado
	 *            the valorRestanteASerCobrado to set
	 */
	public void setValorRestanteASerCobrado(BigDecimal valorRestanteASerCobrado){

		this.valorRestanteASerCobrado = valorRestanteASerCobrado;
	}

	/**
	 * @return the descricaoSituacaoDebito
	 */
	public String getDescricaoSituacaoDebito(){

		return descricaoSituacaoDebito;
	}

	/**
	 * @param descricaoSituacaoDebito
	 *            the descricaoSituacaoDebito to set
	 */
	public void setDescricaoSituacaoDebito(String descricaoSituacaoDebito){

		this.descricaoSituacaoDebito = descricaoSituacaoDebito;
	}

	/**
	 * @return the dataSituacaoDebito
	 */
	public String getDataSituacaoDebito(){

		return dataSituacaoDebito;
	}

	/**
	 * @param dataSituacaoDebito
	 *            the dataSituacaoDebito to set
	 */
	public void setDataSituacaoDebito(String dataSituacaoDebito){

		this.dataSituacaoDebito = dataSituacaoDebito;
	}

}
