
package gcom.gui.cobranca;

import org.apache.struts.validator.ValidatorForm;

/**
 * UC3022] Filtrar Boleto Bancário
 * 
 * @author Cinthya Cavalcanti
 * @date 20/09/2011
 */
public class FiltrarBoletoBancarioActionForm
				extends ValidatorForm {

	private static final long serialVersionUID = 1L;

	private String atualizarFiltro;

	private String bancoBoletoBancarioFiltro;

	private String descricaoBancoBoletoBancarioFiltro;

	private String numeroBoletoBancarioFiltro;

	private String numeroBoletoCartaCobrancaBoletoBancarioFiltro;

	private String imovelBoletoBancarioFiltro;

	private String inscricaoImovelBoletoBancarioFiltro;

	private String clienteBoletoBancarioFiltro;

	private String nomeClienteBoletoBancarioFiltro;

	private String[] idsSituacaoBoletoBancarioFiltro = {};

	private String[] idsTipoDocumentoBoletoBancarioFiltro = {};

	private String dataInicialEntradaBoletoBancarioFiltro;

	private String dataFinalEntradaBoletoBancarioFiltro;

	private String dataInicialVencimentoBoletoBancarioFiltro;

	private String dataFinalVencimentoBoletoBancarioFiltro;

	private String dataInicialCreditoBoletoBancarioFiltro;

	private String dataFinalCreditoBoletoBancarioFiltro;

	private String dataInicialCancelamentoBoletoBancarioFiltro;

	private String dataFinalCancelamentoBoletoBancarioFiltro;

	private String[] idsMotivoCancelamentoBoletoBancarioFiltro = {};

	private String dataInicialPagamentoBoletoBancarioFiltro;

	private String dataFinalPagamentoBoletoBancarioFiltro;

	private String indicadorTotalizarImovel = "2";

	/**
	 * @return the atualizarFiltro
	 */
	public String getAtualizarFiltro(){

		return atualizarFiltro;
	}

	/**
	 * @param atualizarFiltro
	 *            the atualizarFiltro to set
	 */
	public void setAtualizarFiltro(String atualizarFiltro){

		this.atualizarFiltro = atualizarFiltro;
	}

	/**
	 * @return the bancoBoletoBancarioFiltro
	 */
	public String getBancoBoletoBancarioFiltro(){

		return bancoBoletoBancarioFiltro;
	}

	/**
	 * @param bancoBoletoBancarioFiltro
	 *            the bancoBoletoBancarioFiltro to set
	 */
	public void setBancoBoletoBancarioFiltro(String bancoBoletoBancarioFiltro){

		this.bancoBoletoBancarioFiltro = bancoBoletoBancarioFiltro;
	}

	/**
	 * @return the descricaoBanco
	 */
	public String getDescricaoBancoBoletoBancarioFiltro(){

		return descricaoBancoBoletoBancarioFiltro;
	}

	/**
	 * @param descricaoBanco
	 *            the descricaoBanco to set
	 */
	public void setDescricaoBancoBoletoBancarioFiltro(String descricaoBancoBoletoBancarioFiltro){

		this.descricaoBancoBoletoBancarioFiltro = descricaoBancoBoletoBancarioFiltro;
	}

	/**
	 * @return the numeroBoletoBancarioFiltro
	 */
	public String getNumeroBoletoBancarioFiltro(){

		return numeroBoletoBancarioFiltro;
	}

	/**
	 * @param numeroBoletoBancarioFiltro
	 *            the numeroBoletoBancarioFiltro to set
	 */
	public void setNumeroBoletoBancarioFiltro(String numeroBoletoBancarioFiltro){

		this.numeroBoletoBancarioFiltro = numeroBoletoBancarioFiltro;
	}

	/**
	 * @return the numeroBoletoCartaCobrancaBoletoBancarioFiltro
	 */
	public String getNumeroBoletoCartaCobrancaBoletoBancarioFiltro(){

		return numeroBoletoCartaCobrancaBoletoBancarioFiltro;
	}

	/**
	 * @param numeroBoletoCartaCobrancaBoletoBancarioFiltro
	 *            the numeroBoletoCartaCobrancaBoletoBancarioFiltro to set
	 */
	public void setNumeroBoletoCartaCobrancaBoletoBancarioFiltro(String numeroBoletoCartaCobrancaBoletoBancarioFiltro){

		this.numeroBoletoCartaCobrancaBoletoBancarioFiltro = numeroBoletoCartaCobrancaBoletoBancarioFiltro;
	}

	/**
	 * @return the imovelBoletoBancarioFiltro
	 */
	public String getImovelBoletoBancarioFiltro(){

		return imovelBoletoBancarioFiltro;
	}

	/**
	 * @param imovelBoletoBancarioFiltro
	 *            the imovelBoletoBancarioFiltro to set
	 */
	public void setImovelBoletoBancarioFiltro(String imovelBoletoBancarioFiltro){

		this.imovelBoletoBancarioFiltro = imovelBoletoBancarioFiltro;
	}

	/**
	 * @return the inscricaoImovelBoletoBancarioFiltro
	 */
	public String getInscricaoImovelBoletoBancarioFiltro(){

		return inscricaoImovelBoletoBancarioFiltro;
	}

	/**
	 * @param inscricaoImovelBoletoBancarioFiltro
	 *            the inscricaoImovelBoletoBancarioFiltro to set
	 */
	public void setInscricaoImovelBoletoBancarioFiltro(String inscricaoImovelBoletoBancarioFiltro){

		this.inscricaoImovelBoletoBancarioFiltro = inscricaoImovelBoletoBancarioFiltro;
	}

	/**
	 * @return the clienteBoletoBancarioFiltro
	 */
	public String getClienteBoletoBancarioFiltro(){

		return clienteBoletoBancarioFiltro;
	}

	/**
	 * @param clienteBoletoBancarioFiltro
	 *            the clienteBoletoBancarioFiltro to set
	 */
	public void setClienteBoletoBancarioFiltro(String clienteBoletoBancarioFiltro){

		this.clienteBoletoBancarioFiltro = clienteBoletoBancarioFiltro;
	}

	/**
	 * @return the nomeClienteBoletoBancarioFiltro
	 */
	public String getNomeClienteBoletoBancarioFiltro(){

		return nomeClienteBoletoBancarioFiltro;
	}

	/**
	 * @param nomeClienteBoletoBancarioFiltro
	 *            the nomeClienteBoletoBancarioFiltro to set
	 */
	public void setNomeClienteBoletoBancarioFiltro(String nomeClienteBoletoBancarioFiltro){

		this.nomeClienteBoletoBancarioFiltro = nomeClienteBoletoBancarioFiltro;
	}

	/**
	 * @return the idsSituacaoBoletoBancarioFiltro
	 */

	public String[] getIdsSituacaoBoletoBancarioFiltro(){

		return idsSituacaoBoletoBancarioFiltro;
	}

	/**
	 * @param idsSituacaoBoletoBancarioFiltro
	 *            the idsSituacaoBoletoBancarioFiltro to set
	 */

	public void setIdsSituacaoBoletoBancarioFiltro(String[] idsSituacaoBoletoBancarioFiltro){

		this.idsSituacaoBoletoBancarioFiltro = idsSituacaoBoletoBancarioFiltro;
	}

	/**
	 * @return the idsTipoDocumentoBoletoBancarioFiltro
	 */
	public String[] getIdsTipoDocumentoBoletoBancarioFiltro(){

		return idsTipoDocumentoBoletoBancarioFiltro;
	}

	/**
	 * @param idsTipoDocumentoBoletoBancarioFiltro
	 *            the idsTipoDocumentoBoletoBancarioFiltro to set
	 */
	public void setIdsTipoDocumentoBoletoBancarioFiltro(String[] idsTipoDocumentoBoletoBancarioFiltro){

		this.idsTipoDocumentoBoletoBancarioFiltro = idsTipoDocumentoBoletoBancarioFiltro;
	}

	/**
	 * @return the dataInicialEntradaBoletoBancarioFiltro
	 */
	public String getDataInicialEntradaBoletoBancarioFiltro(){

		return dataInicialEntradaBoletoBancarioFiltro;
	}

	/**
	 * @param dataInicialEntradaBoletoBancarioFiltro
	 *            the periodoEntradaBoletoBancarioFiltro to set
	 */
	public void setDataInicialEntradaBoletoBancarioFiltro(String dataInicialEntradaBoletoBancarioFiltro){

		this.dataInicialEntradaBoletoBancarioFiltro = dataInicialEntradaBoletoBancarioFiltro;
	}

	/**
	 * @return the dataFinalEntradaBoletoBancarioFiltro
	 */
	public String getDataFinalEntradaBoletoBancarioFiltro(){

		return dataFinalEntradaBoletoBancarioFiltro;
	}

	/**
	 * @param dataFinalEntradaBoletoBancarioFiltro
	 *            the periodoEntradaBoletoBancarioFiltro to set
	 */
	public void setDataFinalEntradaBoletoBancarioFiltro(String dataFinalEntradaBoletoBancarioFiltro){

		this.dataFinalEntradaBoletoBancarioFiltro = dataFinalEntradaBoletoBancarioFiltro;
	}

	/**
	 * @return the dataInicialVencimentoBoletoBancarioFiltro
	 */
	public String getDataInicialVencimentoBoletoBancarioFiltro(){

		return dataInicialVencimentoBoletoBancarioFiltro;
	}

	/**
	 * @param dataInicialVencimentoBoletoBancarioFiltro
	 *            the dataInicialVencimentoBoletoBancarioFiltro to set
	 */
	public void setDataInicialVencimentoBoletoBancarioFiltro(String dataInicialVencimentoBoletoBancarioFiltro){

		this.dataInicialVencimentoBoletoBancarioFiltro = dataInicialVencimentoBoletoBancarioFiltro;
	}

	/**
	 * @return the dataFinalVencimentoBoletoBancarioFiltro
	 */
	public String getDataFinalVencimentoBoletoBancarioFiltro(){

		return dataFinalVencimentoBoletoBancarioFiltro;
	}

	/**
	 * @param dataFinalVencimentoBoletoBancarioFiltro
	 *            the dataFinalVencimentoBoletoBancarioFiltro to set
	 */
	public void setDataFinalVencimentoBoletoBancarioFiltro(String dataFinalVencimentoBoletoBancarioFiltro){

		this.dataFinalVencimentoBoletoBancarioFiltro = dataFinalVencimentoBoletoBancarioFiltro;
	}

	/**
	 * @return the dataInicialCreditoBoletoBancarioFiltro
	 */
	public String getDataInicialCreditoBoletoBancarioFiltro(){

		return dataInicialCreditoBoletoBancarioFiltro;
	}

	/**
	 * @param dataInicialCreditoBoletoBancarioFiltro
	 *            the periodoCreditoBoletoBancarioFiltro to set
	 */
	public void setDataInicialCreditoBoletoBancarioFiltro(String dataInicialCreditoBoletoBancarioFiltro){

		this.dataInicialCreditoBoletoBancarioFiltro = dataInicialCreditoBoletoBancarioFiltro;
	}

	/**
	 * @return the dataFinalCreditoBoletoBancarioFiltro
	 */
	public String getDataFinalCreditoBoletoBancarioFiltro(){

		return dataFinalCreditoBoletoBancarioFiltro;
	}

	/**
	 * @param dataFinalCreditoBoletoBancarioFiltro
	 *            the dataFinalCreditoBoletoBancarioFiltro to set
	 */
	public void setDataFinalCreditoBoletoBancarioFiltro(String dataFinalCreditoBoletoBancarioFiltro){

		this.dataFinalCreditoBoletoBancarioFiltro = dataFinalCreditoBoletoBancarioFiltro;
	}

	/**
	 * @return the dataInicialCancelamentoBoletoBancarioFiltro
	 */
	public String getDataInicialCancelamentoBoletoBancarioFiltro(){

		return dataInicialCancelamentoBoletoBancarioFiltro;
	}

	/**
	 * @param dataInicialCancelamentoBoletoBancarioFiltro
	 *            the dataInicialCancelamentoBoletoBancarioFiltro to set
	 */
	public void setDataInicialCancelamentoBoletoBancarioFiltro(String dataInicialCancelamentoBoletoBancarioFiltro){

		this.dataInicialCancelamentoBoletoBancarioFiltro = dataInicialCancelamentoBoletoBancarioFiltro;
	}

	/**
	 * @return the dataFinalCancelamentoBoletoBancarioFiltro
	 */
	public String getDataFinalCancelamentoBoletoBancarioFiltro(){

		return dataFinalCancelamentoBoletoBancarioFiltro;
	}

	/**
	 * @param dataFinalCancelamentoBoletoBancarioFiltro
	 *            the dataFinalCancelamentoBoletoBancarioFiltro to set
	 */
	public void setDataFinalCancelamentoBoletoBancarioFiltro(String dataFinalCancelamentoBoletoBancarioFiltro){

		this.dataFinalCancelamentoBoletoBancarioFiltro = dataFinalCancelamentoBoletoBancarioFiltro;
	}

	/**
	 * @return the idsMotivoCancelamentoBoletoBancarioFiltro
	 */
	public String[] getIdsMotivoCancelamentoBoletoBancarioFiltro(){

		return idsMotivoCancelamentoBoletoBancarioFiltro;
	}

	/**
	 * @param idsMotivoCancelamentoBoletoBancarioFiltro
	 *            the idsMotivoCancelamentoBoletoBancarioFiltro to set
	 */
	public void setIdsMotivoCancelamentoBoletoBancarioFiltro(String[] idsMotivoCancelamentoBoletoBancarioFiltro){

		this.idsMotivoCancelamentoBoletoBancarioFiltro = idsMotivoCancelamentoBoletoBancarioFiltro;
	}

	public String getDataInicialPagamentoBoletoBancarioFiltro(){

		return dataInicialPagamentoBoletoBancarioFiltro;
	}

	public void setDataInicialPagamentoBoletoBancarioFiltro(String dataInicialPagamentoBoletoBancarioFiltro){

		this.dataInicialPagamentoBoletoBancarioFiltro = dataInicialPagamentoBoletoBancarioFiltro;
	}

	public String getDataFinalPagamentoBoletoBancarioFiltro(){

		return dataFinalPagamentoBoletoBancarioFiltro;
	}

	public void setDataFinalPagamentoBoletoBancarioFiltro(String dataFinalPagamentoBoletoBancarioFiltro){

		this.dataFinalPagamentoBoletoBancarioFiltro = dataFinalPagamentoBoletoBancarioFiltro;
	}

	public String getIndicadorTotalizarImovel(){

		return indicadorTotalizarImovel;
	}

	public void setIndicadorTotalizarImovel(String indicadorTotalizarImovel){

		this.indicadorTotalizarImovel = indicadorTotalizarImovel;
	}

}
