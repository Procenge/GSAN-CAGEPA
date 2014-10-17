
package gcom.gui.cobranca;

import gcom.arrecadacao.Arrecadador;
import gcom.arrecadacao.pagamento.GuiaPagamento;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.imovel.Imovel;
import gcom.cobranca.BoletoBancarioMotivoCancelamento;
import gcom.cobranca.BoletoBancarioSituacao;
import gcom.cobranca.CobrancaDocumento;
import gcom.cobranca.DocumentoTipo;
import gcom.cobranca.parcelamento.Parcelamento;
import gcom.faturamento.conta.Conta;
import gcom.faturamento.debito.DebitoACobrar;
import gcom.util.Util;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class BoletoBancarioHelper
				implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private String convenio;

	private Integer numeroSequencial;

	private BoletoBancarioSituacao boletoBancarioSituacao;

	private Integer anoMesReferencia;

	private Arrecadador arrecadador;

	private BigDecimal valorDebito;

	private Imovel imovel;

	private Cliente cliente;

	private DocumentoTipo documentoTipo;

	private CobrancaDocumento documentoCobranca;

	private Conta conta;

	private DebitoACobrar debitoACobrar;

	private GuiaPagamento guiaPagamento;

	private Integer numeroPrestacoes;

	private Integer numeroBoletoCartaCobranca;

	private Date dataGeracaoCarta;

	private BigDecimal valorPago;

	private BigDecimal valorTarifa;

	private BoletoBancarioMotivoCancelamento boletoBancarioMotivoCancelamento;

	private Parcelamento parcelamento;

	private Integer numeroSequencialArquivoMigracao;

	private Date ultimaAlteracao;

	private String[] idsSituacao = {};

	private String[] idsTipoDocumento = {};

	private Date dataInicialEntrada;

	private Date dataFinalEntrada;

	private Date dataInicialVencimento;

	private Date dataFinalVencimento;

	private Date dataInicialCredito;

	private Date dataFinalCredito;

	private Date dataInicialCancelamento;

	private Date dataFinalCancelamento;

	private String[] idsMotivoCancelamento = {};

	private Date dataInicialPagamento;

	private Date dataFinalPagamento;

	public BoletoBancarioHelper() {

	}

	public BoletoBancarioHelper clone(){

		try{
			return (BoletoBancarioHelper) super.clone();
		}catch(CloneNotSupportedException e){
			return null;
		}
	}

	/**
	 * @return the id
	 */
	public Integer getId(){

		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Integer id){

		this.id = id;
	}

	/**
	 * @return the convenio
	 */
	public String getConvenio(){

		return convenio;
	}

	/**
	 * @param convenio
	 *            the convenio to set
	 */
	public void setConvenio(String convenio){

		this.convenio = convenio;
	}

	/**
	 * @return the numeroSequencial
	 */
	public Integer getNumeroSequencial(){

		return numeroSequencial;
	}

	/**
	 * @param numeroSequencial
	 *            the numeroSequencial to set
	 */
	public void setNumeroSequencial(Integer numeroSequencial){

		this.numeroSequencial = numeroSequencial;
	}

	/**
	 * @return the Situacao
	 */
	public BoletoBancarioSituacao getBoletoBancarioSituacao(){

		return boletoBancarioSituacao;
	}

	/**
	 * @param Situacao
	 *            the Situacao to set
	 */
	public void setBoletoBancarioSituacao(BoletoBancarioSituacao Situacao){

		this.boletoBancarioSituacao = Situacao;
	}

	/**
	 * @return the anoMesReferencia
	 */
	public Integer getAnoMesReferencia(){

		return anoMesReferencia;
	}

	/**
	 * @param anoMesReferencia
	 *            the anoMesReferencia to set
	 */
	public void setAnoMesReferencia(Integer anoMesReferencia){

		this.anoMesReferencia = anoMesReferencia;
	}

	/**
	 * @return the arrecadador
	 */
	public Arrecadador getArrecadador(){

		return arrecadador;
	}

	/**
	 * @param arrecadador
	 *            the arrecadador to set
	 */
	public void setArrecadador(Arrecadador arrecadador){

		this.arrecadador = arrecadador;
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
	 * @return the imovel
	 */
	public Imovel getImovel(){

		return imovel;
	}

	/**
	 * @param imovel
	 *            the imovel to set
	 */
	public void setImovel(Imovel imovel){

		this.imovel = imovel;
	}

	/**
	 * @return the cliente
	 */
	public Cliente getCliente(){

		return cliente;
	}

	/**
	 * @param cliente
	 *            the cliente to set
	 */
	public void setCliente(Cliente cliente){

		this.cliente = cliente;
	}

	/**
	 * @return the documentoTipo
	 */
	public DocumentoTipo getDocumentoTipo(){

		return documentoTipo;
	}

	/**
	 * @param documentoTipo
	 *            the documentoTipo to set
	 */
	public void setDocumentoTipo(DocumentoTipo documentoTipo){

		this.documentoTipo = documentoTipo;
	}

	/**
	 * @return the documentoCobranca
	 */
	public CobrancaDocumento getDocumentoCobranca(){

		return documentoCobranca;
	}

	/**
	 * @param documentoCobranca
	 *            the documentoCobranca to set
	 */
	public void setDocumentoCobranca(CobrancaDocumento documentoCobranca){

		this.documentoCobranca = documentoCobranca;
	}

	/**
	 * @return the conta
	 */
	public Conta getConta(){

		return conta;
	}

	/**
	 * @param conta
	 *            the conta to set
	 */
	public void setConta(Conta conta){

		this.conta = conta;
	}

	/**
	 * @return the debitoACobrar
	 */
	public DebitoACobrar getDebitoACobrar(){

		return debitoACobrar;
	}

	/**
	 * @param debitoACobrar
	 *            the debitoACobrar to set
	 */
	public void setDebitoACobrar(DebitoACobrar debitoACobrar){

		this.debitoACobrar = debitoACobrar;
	}

	/**
	 * @return the guiaPagamento
	 */
	public GuiaPagamento getGuiaPagamento(){

		return guiaPagamento;
	}

	/**
	 * @param guiaPagamento
	 *            the guiaPagamento to set
	 */
	public void setGuiaPagamento(GuiaPagamento guiaPagamento){

		this.guiaPagamento = guiaPagamento;
	}

	/**
	 * @return the numeroPrestacoes
	 */
	public Integer getNumeroPrestacoes(){

		return numeroPrestacoes;
	}

	/**
	 * @param numeroPrestacoes
	 *            the numeroPrestacoes to set
	 */
	public void setNumeroPrestacoes(Integer numeroPrestacoes){

		this.numeroPrestacoes = numeroPrestacoes;
	}

	/**
	 * @return the numeroBoletoCartaCobranca
	 */
	public Integer getNumeroBoletoCartaCobranca(){

		return numeroBoletoCartaCobranca;
	}

	/**
	 * @param numeroBoletoCartaCobranca
	 *            the numeroBoletoCartaCobranca to set
	 */
	public void setNumeroBoletoCartaCobranca(Integer numeroBoletoCartaCobranca){

		this.numeroBoletoCartaCobranca = numeroBoletoCartaCobranca;
	}

	/**
	 * @return the dataGeracaoCarta
	 */
	public Date getDataGeracaoCarta(){

		return dataGeracaoCarta;
	}

	/**
	 * @param dataGeracaoCarta
	 *            the dataGeracaoCarta to set
	 */
	public void setDataGeracaoCarta(Date dataGeracaoCarta){

		this.dataGeracaoCarta = dataGeracaoCarta;
	}

	/**
	 * @return the valorPago
	 */
	public BigDecimal getValorPago(){

		return valorPago;
	}

	/**
	 * @param valorPago
	 *            the valorPago to set
	 */
	public void setValorPago(BigDecimal valorPago){

		this.valorPago = valorPago;
	}

	/**
	 * @return the valorTarifa
	 */
	public BigDecimal getValorTarifa(){

		return valorTarifa;
	}

	/**
	 * @param valorTarifa
	 *            the valorTarifa to set
	 */
	public void setValorTarifa(BigDecimal valorTarifa){

		this.valorTarifa = valorTarifa;
	}

	/**
	 * @return the MotivoCancelamento
	 */
	public BoletoBancarioMotivoCancelamento getBoletoBancarioMotivoCancelamento(){

		return boletoBancarioMotivoCancelamento;
	}

	/**
	 * @param MotivoCancelamento
	 *            the MotivoCancelamento to set
	 */
	public void setBoletoBancarioMotivoCancelamento(BoletoBancarioMotivoCancelamento boletoBancarioMotivoCancelamento){

		this.boletoBancarioMotivoCancelamento = boletoBancarioMotivoCancelamento;
	}

	/**
	 * @return the parcelamento
	 */
	public Parcelamento getParcelamento(){

		return parcelamento;
	}

	/**
	 * @param parcelamento
	 *            the parcelamento to set
	 */
	public void setParcelamento(Parcelamento parcelamento){

		this.parcelamento = parcelamento;
	}

	/**
	 * @return the numeroSequencialArquivoMigracao
	 */
	public Integer getNumeroSequencialArquivoMigracao(){

		return numeroSequencialArquivoMigracao;
	}

	/**
	 * @param numeroSequencialArquivoMigracao
	 *            the numeroSequencialArquivoMigracao to set
	 */
	public void setNumeroSequencialArquivoMigracao(Integer numeroSequencialArquivoMigracao){

		this.numeroSequencialArquivoMigracao = numeroSequencialArquivoMigracao;
	}

	/**
	 * @return the ultimaAlteracao
	 */
	public Date getUltimaAlteracao(){

		return ultimaAlteracao;
	}

	/**
	 * @param ultimaAlteracao
	 *            the ultimaAlteracao to set
	 */
	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	/**
	 * @return the idsSituacao
	 */
	public String[] getIdsBoletoBancarioSituacao(){

		return idsSituacao;
	}

	/**
	 * @param idsSituacao
	 *            the idsSituacao to set
	 */
	public void setIdsBoletoBancarioSituacao(String[] idsSituacao){

		this.idsSituacao = idsSituacao;
	}

	/**
	 * @return the idsTipoDocumento
	 */
	public String[] getIdsTipoDocumento(){

		return idsTipoDocumento;
	}

	/**
	 * @param idsTipoDocumento
	 *            the idsTipoDocumento to set
	 */
	public void setIdsTipoDocumento(String[] idsTipoDocumento){

		this.idsTipoDocumento = idsTipoDocumento;
	}

	/**
	 * @return the dataInicialEntrada
	 */
	public Date getDataInicialEntrada(){

		return dataInicialEntrada;
	}

	/**
	 * @param dataInicialEntrada
	 *            the dataInicialEntrada to set
	 */
	public void setDataInicialEntrada(Date dataInicialEntrada){

		this.dataInicialEntrada = dataInicialEntrada;
	}

	/**
	 * @return the dataFinalEntrada
	 */
	public Date getDataFinalEntrada(){

		return dataFinalEntrada;
	}

	/**
	 * @param dataFinalEntrada
	 *            the dataFinalEntrada to set
	 */
	public void setDataFinalEntrada(Date dataFinalEntrada){

		this.dataFinalEntrada = dataFinalEntrada;
	}

	/**
	 * @return the dataInicialVencimento
	 */
	public Date getDataInicialVencimento(){

		return dataInicialVencimento;
	}

	/**
	 * @param dataInicialVencimento
	 *            the dataInicialVencimento to set
	 */
	public void setDataInicialVencimento(Date dataInicialVencimento){

		this.dataInicialVencimento = dataInicialVencimento;
	}

	/**
	 * @return the dataFinalVencimento
	 */
	public Date getDataFinalVencimento(){

		return dataFinalVencimento;
	}

	/**
	 * @param dataFinalVencimento
	 *            the dataFinalVencimento to set
	 */
	public void setDataFinalVencimento(Date dataFinalVencimento){

		this.dataFinalVencimento = dataFinalVencimento;
	}

	/**
	 * @return the dataInicialCredito
	 */
	public Date getDataInicialCredito(){

		return dataInicialCredito;
	}

	/**
	 * @param dataInicialCredito
	 *            the dataInicialCredito to set
	 */
	public void setDataInicialCredito(Date dataInicialCredito){

		this.dataInicialCredito = dataInicialCredito;
	}

	/**
	 * @return the dataFinalCredito
	 */
	public Date getDataFinalCredito(){

		return dataFinalCredito;
	}

	/**
	 * @param dataFinalCredito
	 *            the dataFinalCredito to set
	 */
	public void setDataFinalCredito(Date dataFinalCredito){

		this.dataFinalCredito = dataFinalCredito;
	}

	/**
	 * @return the dataInicialCancelamento
	 */
	public Date getDataInicialCancelamento(){

		return dataInicialCancelamento;
	}

	/**
	 * @param dataInicialCancelamento
	 *            the dataInicialCancelamento to set
	 */
	public void setDataInicialCancelamento(Date dataInicialCancelamento){

		this.dataInicialCancelamento = dataInicialCancelamento;
	}

	/**
	 * @return the dataFinalCancelamento
	 */
	public Date getDataFinalCancelamento(){

		return dataFinalCancelamento;
	}

	/**
	 * @param dataFinalCancelamento
	 *            the dataFinalCancelamento to set
	 */
	public void setDataFinalCancelamento(Date dataFinalCancelamento){

		this.dataFinalCancelamento = dataFinalCancelamento;
	}

	/**
	 * @return the idsMotivoCancelamento
	 */
	public String[] getIdsMotivoCancelamento(){

		return idsMotivoCancelamento;
	}

	/**
	 * @param idsMotivoCancelamento
	 *            the idsMotivoCancelamento to set
	 */
	public void setIdsMotivoCancelamento(String[] idsMotivoCancelamento){

		this.idsMotivoCancelamento = idsMotivoCancelamento;
	}

	public Date getDataInicialPagamento(){

		return dataInicialPagamento;
	}

	public void setDataInicialPagamento(Date dataInicialPagamento){

		this.dataInicialPagamento = dataInicialPagamento;
	}

	public Date getDataFinalPagamento(){

		return dataFinalPagamento;
	}

	public void setDataFinalPagamento(Date dataFinalPagamento){

		this.dataFinalPagamento = dataFinalPagamento;
	}

	/**
	 * Método que retorna o Número do Convénio e o Número do Sequencial, completando com Zeros os
	 * espaços (Código do convênio (BBCO_CDCONVENIO) com 7 caracteres mais Sequencial do boleto no
	 * arrecadador (BBCO_NNSEQUENCIAL) com 10 caracteres).
	 * 
	 * @author Ailton Sousa
	 * @date 22/12/2011
	 * @return
	 */
	public String getNumeroConvenioSequencialFormatado(){

		String numeroConvenioSequencialFormatado = Util.completarStringZeroEsquerda(this.getConvenio(), 7)
						+ Util.completarStringZeroEsquerda(this.getNumeroSequencial().toString(), 10);

		return numeroConvenioSequencialFormatado;
	}

}
