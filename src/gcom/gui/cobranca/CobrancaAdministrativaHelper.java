
package gcom.gui.cobranca;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * [UC3060] Consultar Imóvel Cobrança Administrativa
 * [SB0010] Obter Dados Conta.
 * 
 * @author Josenildo Neves
 * @date 07/08/2013
 */
public class CobrancaAdministrativaHelper
				implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer referenciaConta;

	private Date dataVencimentoConta;

	private BigDecimal valorDebito;

	private Integer clienteUsuario;

	private String situacaoItem;

	private Date dataSituacao;

	private Integer idGuiaPamento;

	private Short prestacao;

	private Date dataEmissao;

	private String tipoDebito;

	private Integer referenciaDebito;

	private Short prestacaoCobrada;

	private Short prestacaoDebito;

	private BigDecimal valorRestanteASerCobrado;

	private Integer idDebitoACobrar;

	private BigDecimal valorRemuneracao;

	private BigDecimal valorRemuneracaoReincidencia;

	private BigDecimal valorRemuneracaoEspecial;

	private BigDecimal valorRemuneracaoParcelado;

	private String tipoRemuneracao;

	private BigDecimal percentualRemuneracao;

	private BigDecimal percentualRemuneracaoReincidencia;

	private BigDecimal percentualRemuneracaoEspecial;

	private BigDecimal percentualRemuneracaoParcelado;

	private String nomeClienteUsuario;

	private BigDecimal valorAgua;

	private BigDecimal valorEsgoto;

	private BigDecimal valorDebitos;

	private BigDecimal valorCreditos;

	private BigDecimal valorImpostos;

	/**
	 * @return the referenciaConta
	 */
	public Integer getReferenciaConta(){

		return referenciaConta;
	}

	/**
	 * @param referenciaConta
	 *            the referenciaConta to set
	 */
	public void setReferenciaConta(Integer referenciaConta){

		this.referenciaConta = referenciaConta;
	}

	/**
	 * @return the dataVencimentoConta
	 */
	public Date getDataVencimentoConta(){

		return dataVencimentoConta;
	}

	/**
	 * @param dataVencimentoConta
	 *            the dataVencimentoConta to set
	 */
	public void setDataVencimentoConta(Date dataVencimentoConta){

		this.dataVencimentoConta = dataVencimentoConta;
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
	 * @return the clienteUsuario
	 */
	public Integer getClienteUsuario(){

		return clienteUsuario;
	}

	/**
	 * @param clienteUsuario
	 *            the clienteUsuario to set
	 */
	public void setClienteUsuario(Integer clienteUsuario){

		this.clienteUsuario = clienteUsuario;
	}

	/**
	 * @return the situacaoItem
	 */
	public String getSituacaoItem(){

		return situacaoItem;
	}

	/**
	 * @param situacaoItem
	 *            the situacaoItem to set
	 */
	public void setSituacaoItem(String situacaoItem){

		this.situacaoItem = situacaoItem;
	}

	/**
	 * @return the dataSituacao
	 */
	public Date getDataSituacao(){

		return dataSituacao;
	}


	/**
	 * @param dataSituacao
	 *            the dataSituacao to set
	 */
	public void setDataSituacao(Date dataSituacao){

		this.dataSituacao = dataSituacao;
	}

	/**
	 * @return the idGuiaPamento
	 */
	public Integer getIdGuiaPamento(){

		return idGuiaPamento;
	}

	/**
	 * @param idGuiaPamento
	 *            the idGuiaPamento to set
	 */
	public void setIdGuiaPamento(Integer idGuiaPamento){

		this.idGuiaPamento = idGuiaPamento;
	}

	/**
	 * @return the prestacao
	 */
	public Short getPrestacao(){

		return prestacao;
	}

	/**
	 * @param prestacao
	 *            the prestacao to set
	 */
	public void setPrestacao(Short prestacao){

		this.prestacao = prestacao;
	}

	/**
	 * @return the dataEmissao
	 */
	public Date getDataEmissao(){

		return dataEmissao;
	}

	/**
	 * @param dataEmissao
	 *            the dataEmissao to set
	 */
	public void setDataEmissao(Date dataEmissao){

		this.dataEmissao = dataEmissao;
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
	 * @return the referenciaDebito
	 */
	public Integer getReferenciaDebito(){

		return referenciaDebito;
	}

	/**
	 * @param referenciaDebito
	 *            the referenciaDebito to set
	 */
	public void setReferenciaDebito(Integer referenciaDebito){

		this.referenciaDebito = referenciaDebito;
	}

	/**
	 * @return the prestacaoCobrada
	 */
	public Short getPrestacaoCobrada(){

		return prestacaoCobrada;
	}

	/**
	 * @param prestacaoCobrada
	 *            the prestacaoCobrada to set
	 */
	public void setPrestacaoCobrada(Short prestacaoCobrada){

		this.prestacaoCobrada = prestacaoCobrada;
	}

	/**
	 * @return the prestacaoDebito
	 */
	public Short getPrestacaoDebito(){

		return prestacaoDebito;
	}

	/**
	 * @param prestacaoDebito
	 *            the prestacaoDebito to set
	 */
	public void setPrestacaoDebito(Short prestacaoDebito){

		this.prestacaoDebito = prestacaoDebito;
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
	 * @return the valorRemuneracao
	 */
	public BigDecimal getValorRemuneracao(){

		return valorRemuneracao;
	}

	/**
	 * @param valorRemuneracao
	 *            the valorRemuneracao to set
	 */
	public void setValorRemuneracao(BigDecimal valorRemuneracao){

		this.valorRemuneracao = valorRemuneracao;
	}

	/**
	 * @return the valorRemuneracaoReincidencia
	 */
	public BigDecimal getValorRemuneracaoReincidencia(){

		return valorRemuneracaoReincidencia;
	}

	/**
	 * @param valorRemuneracaoReincidencia
	 *            the valorRemuneracaoReincidencia to set
	 */
	public void setValorRemuneracaoReincidencia(BigDecimal valorRemuneracaoReincidencia){

		this.valorRemuneracaoReincidencia = valorRemuneracaoReincidencia;
	}

	/**
	 * @return the valorRemuneracaoEspecial
	 */
	public BigDecimal getValorRemuneracaoEspecial(){

		return valorRemuneracaoEspecial;
	}

	/**
	 * @param valorRemuneracaoEspecial
	 *            the valorRemuneracaoEspecial to set
	 */
	public void setValorRemuneracaoEspecial(BigDecimal valorRemuneracaoEspecial){

		this.valorRemuneracaoEspecial = valorRemuneracaoEspecial;
	}

	/**
	 * @return the valorRemuneracaoParcelado
	 */
	public BigDecimal getValorRemuneracaoParcelado(){

		return valorRemuneracaoParcelado;
	}

	/**
	 * @param valorRemuneracaoParcelado
	 *            the valorRemuneracaoParcelado to set
	 */
	public void setValorRemuneracaoParcelado(BigDecimal valorRemuneracaoParcelado){

		this.valorRemuneracaoParcelado = valorRemuneracaoParcelado;
	}

	/**
	 * @return the tipoRemuneracao
	 */
	public String getTipoRemuneracao(){

		return tipoRemuneracao;
	}

	/**
	 * @param tipoRemuneracao
	 *            the tipoRemuneracao to set
	 */
	public void setTipoRemuneracao(String tipoRemuneracao){

		this.tipoRemuneracao = tipoRemuneracao;
	}

	/**
	 * @return the percentualRemuneracao
	 */
	public BigDecimal getPercentualRemuneracao(){

		return percentualRemuneracao;
	}

	/**
	 * @param percentualRemuneracao
	 *            the percentualRemuneracao to set
	 */
	public void setPercentualRemuneracao(BigDecimal percentualRemuneracao){

		this.percentualRemuneracao = percentualRemuneracao;
	}

	/**
	 * @return the percentualRemuneracaoReincidencia
	 */
	public BigDecimal getPercentualRemuneracaoReincidencia(){

		return percentualRemuneracaoReincidencia;
	}

	/**
	 * @param percentualRemuneracaoReincidencia
	 *            the percentualRemuneracaoReincidencia to set
	 */
	public void setPercentualRemuneracaoReincidencia(BigDecimal percentualRemuneracaoReincidencia){

		this.percentualRemuneracaoReincidencia = percentualRemuneracaoReincidencia;
	}

	/**
	 * @return the percentualRemuneracaoEspecial
	 */
	public BigDecimal getPercentualRemuneracaoEspecial(){

		return percentualRemuneracaoEspecial;
	}

	/**
	 * @param percentualRemuneracaoEspecial
	 *            the percentualRemuneracaoEspecial to set
	 */
	public void setPercentualRemuneracaoEspecial(BigDecimal percentualRemuneracaoEspecial){

		this.percentualRemuneracaoEspecial = percentualRemuneracaoEspecial;
	}

	/**
	 * @return the percentualRemuneracaoParcelado
	 */
	public BigDecimal getPercentualRemuneracaoParcelado(){

		return percentualRemuneracaoParcelado;
	}

	/**
	 * @param percentualRemuneracaoParcelado
	 *            the percentualRemuneracaoParcelado to set
	 */
	public void setPercentualRemuneracaoParcelado(BigDecimal percentualRemuneracaoParcelado){

		this.percentualRemuneracaoParcelado = percentualRemuneracaoParcelado;
	}

	/**
	 * @return the nomeClienteUsuario
	 */
	public String getNomeClienteUsuario(){

		return nomeClienteUsuario;
	}

	/**
	 * @param nomeClienteUsuario
	 *            the nomeClienteUsuario to set
	 */
	public void setNomeClienteUsuario(String nomeClienteUsuario){

		this.nomeClienteUsuario = nomeClienteUsuario;
	}

	/**
	 * @return the valorAgua
	 */
	public BigDecimal getValorAgua(){

		return valorAgua;
	}

	/**
	 * @param valorAgua
	 *            the valorAgua to set
	 */
	public void setValorAgua(BigDecimal valorAgua){

		this.valorAgua = valorAgua;
	}

	/**
	 * @return the valorEsgoto
	 */
	public BigDecimal getValorEsgoto(){

		return valorEsgoto;
	}

	/**
	 * @param valorEsgoto
	 *            the valorEsgoto to set
	 */
	public void setValorEsgoto(BigDecimal valorEsgoto){

		this.valorEsgoto = valorEsgoto;
	}

	/**
	 * @return the valorDebitos
	 */
	public BigDecimal getValorDebitos(){

		return valorDebitos;
	}

	/**
	 * @param valorDebitos
	 *            the valorDebitos to set
	 */
	public void setValorDebitos(BigDecimal valorDebitos){

		this.valorDebitos = valorDebitos;
	}

	/**
	 * @return the valorCreditos
	 */
	public BigDecimal getValorCreditos(){

		return valorCreditos;
	}

	/**
	 * @param valorCreditos
	 *            the valorCreditos to set
	 */
	public void setValorCreditos(BigDecimal valorCreditos){

		this.valorCreditos = valorCreditos;
	}

	/**
	 * @return the valorImpostos
	 */
	public BigDecimal getValorImpostos(){

		return valorImpostos;
	}

	/**
	 * @param valorImpostos
	 *            the valorImpostos to set
	 */
	public void setValorImpostos(BigDecimal valorImpostos){

		this.valorImpostos = valorImpostos;
	}

	public boolean equals(Object other){

		if((this == other)) return true;
		if(!(other instanceof CobrancaAdministrativaHelper)) return false;
		CobrancaAdministrativaHelper castOther = (CobrancaAdministrativaHelper) other;
		return new EqualsBuilder().append(this.getIdDebitoACobrar(), castOther.getIdDebitoACobrar()).isEquals();
	}

}
