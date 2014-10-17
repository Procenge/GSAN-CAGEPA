
package gcom.cadastro.imovel;

import gcom.cobranca.CobrancaAdministrativaRemuneracaoMensal;
import gcom.cobranca.DocumentoTipo;
import gcom.faturamento.GuiaPagamentoGeral;
import gcom.faturamento.conta.ContaGeral;
import gcom.faturamento.debito.DebitoACobrarGeral;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author Hugo Lima
 */
public class ImovelCobrancaAdministrivaItem
				implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private Integer numeroPrestacao;

	private BigDecimal percentualRemuneracao;

	private BigDecimal valorRemuneracao;

	private BigDecimal percentualRemuneracaoReincidente;

	private BigDecimal valorRemuneracaoReincidente;

	private BigDecimal percentualRemuneracaoEspecial;

	private BigDecimal valorRemuneracaoEspecial;

	private BigDecimal percentualRemuneracaoParcelado;

	private BigDecimal valorRemuneracaoParcelado;

	private ContaGeral contaGeral;

	private DocumentoTipo documentoTipo;

	private GuiaPagamentoGeral guiaPagamentoGeral;

	private ImovelCobrancaSituacao imovelCobrancaSituacao;

	private Date ultimaAlteracao;

	private DebitoACobrarGeral debitoACobrarGeral;

	private Date dataPagamentoDocumento;

	private Short indicadorRemuneracaoPaga = 2;

	private Date dataPagamentoRemuneracao;

	private BigDecimal valorBaseRemuneracao;

	private BigDecimal valorBaseRemuneracaoParcelado;

	private BigDecimal valorBaseRemuneracaoReincidente;

	private BigDecimal valorBaseRemuneracaoEspecial;

	private Integer anoMesReferenciaArrecadacao;

	private BigDecimal valorArrecadadoLote;

	private CobrancaAdministrativaRemuneracaoMensal cobrancaAdministrativaRemuneracaoMensal;

	public static String PADRAO = "PADRÃO";

	public static String PARCELAMENTO = "PARCELAMENTO";

	public static String ESPECIAL = "ESPECIAL";

	public static String REINCIDENCIA = "REINCIDÊNCIA";

	public ImovelCobrancaAdministrivaItem() {

	}

	public ImovelCobrancaAdministrivaItem(Integer id, Integer numeroPrestacao, BigDecimal percentualRemuneracao,
											BigDecimal valorRemuneracao, BigDecimal percentualRemuneracaoReincidente,
											BigDecimal valorRemuneracaoReincidente, BigDecimal percentualRemuneracaoEspecial,
											BigDecimal valorRemuneracaoEspecial, BigDecimal percentualRemuneracaoParcelado,
											BigDecimal valorRemuneracaoParcelado, ContaGeral contaGeral, DocumentoTipo documentoTipo,
											GuiaPagamentoGeral guiaPagamentoGeral, ImovelCobrancaSituacao imovelCobrancaSituacao,
											Date ultimaAlteracao) {

		this.id = id;
		this.numeroPrestacao = numeroPrestacao;
		this.percentualRemuneracao = percentualRemuneracao;
		this.valorRemuneracao = valorRemuneracao;
		this.percentualRemuneracaoReincidente = percentualRemuneracaoReincidente;
		this.valorRemuneracaoReincidente = valorRemuneracaoReincidente;
		this.percentualRemuneracaoEspecial = percentualRemuneracaoEspecial;
		this.valorRemuneracaoEspecial = valorRemuneracaoEspecial;
		this.percentualRemuneracaoParcelado = percentualRemuneracaoParcelado;
		this.valorRemuneracaoParcelado = valorRemuneracaoParcelado;
		this.contaGeral = contaGeral;
		this.documentoTipo = documentoTipo;
		this.guiaPagamentoGeral = guiaPagamentoGeral;
		this.imovelCobrancaSituacao = imovelCobrancaSituacao;
		this.ultimaAlteracao = ultimaAlteracao;
	}

	public Integer getId(){

		return this.id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public Integer getNumeroPrestacao(){

		return numeroPrestacao;
	}

	public void setNumeroPrestacao(Integer numeroPrestacao){

		this.numeroPrestacao = numeroPrestacao;
	}

	public BigDecimal getPercentualRemuneracao(){

		return percentualRemuneracao;
	}

	public void setPercentualRemuneracao(BigDecimal percentualRemuneracao){

		this.percentualRemuneracao = percentualRemuneracao;
	}

	public BigDecimal getValorRemuneracao(){

		return valorRemuneracao;
	}

	public void setValorRemuneracao(BigDecimal valorRemuneracao){

		this.valorRemuneracao = valorRemuneracao;
	}

	public BigDecimal getPercentualRemuneracaoReincidente(){

		return percentualRemuneracaoReincidente;
	}

	public void setPercentualRemuneracaoReincidente(BigDecimal percentualRemuneracaoReincidente){

		this.percentualRemuneracaoReincidente = percentualRemuneracaoReincidente;
	}

	public BigDecimal getValorRemuneracaoReincidente(){

		return valorRemuneracaoReincidente;
	}

	public void setValorRemuneracaoReincidente(BigDecimal valorRemuneracaoReincidente){

		this.valorRemuneracaoReincidente = valorRemuneracaoReincidente;
	}

	public BigDecimal getPercentualRemuneracaoEspecial(){

		return percentualRemuneracaoEspecial;
	}

	public void setPercentualRemuneracaoEspecial(BigDecimal percentualRemuneracaoEspecial){

		this.percentualRemuneracaoEspecial = percentualRemuneracaoEspecial;
	}

	public BigDecimal getValorRemuneracaoEspecial(){

		return valorRemuneracaoEspecial;
	}

	public void setValorRemuneracaoEspecial(BigDecimal valorRemuneracaoEspecial){

		this.valorRemuneracaoEspecial = valorRemuneracaoEspecial;
	}

	public BigDecimal getPercentualRemuneracaoParcelado(){

		return percentualRemuneracaoParcelado;
	}

	public void setPercentualRemuneracaoParcelado(BigDecimal percentualRemuneracaoParcelado){

		this.percentualRemuneracaoParcelado = percentualRemuneracaoParcelado;
	}

	public BigDecimal getValorRemuneracaoParcelado(){

		return valorRemuneracaoParcelado;
	}

	public void setValorRemuneracaoParcelado(BigDecimal valorRemuneracaoParcelado){

		this.valorRemuneracaoParcelado = valorRemuneracaoParcelado;
	}

	public ContaGeral getContaGeral(){

		return contaGeral;
	}

	public void setContaGeral(ContaGeral contaGeral){

		this.contaGeral = contaGeral;
	}

	public DocumentoTipo getDocumentoTipo(){

		return documentoTipo;
	}

	public void setDocumentoTipo(DocumentoTipo documentoTipo){

		this.documentoTipo = documentoTipo;
	}

	public GuiaPagamentoGeral getGuiaPagamentoGeral(){

		return guiaPagamentoGeral;
	}

	public void setGuiaPagamentoGeral(GuiaPagamentoGeral guiaPagamentoGeral){

		this.guiaPagamentoGeral = guiaPagamentoGeral;
	}

	public ImovelCobrancaSituacao getImovelCobrancaSituacao(){

		return imovelCobrancaSituacao;
	}

	public void setImovelCobrancaSituacao(ImovelCobrancaSituacao imovelCobrancaSituacao){

		this.imovelCobrancaSituacao = imovelCobrancaSituacao;
	}

	public Date getUltimaAlteracao(){

		return ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public String toString(){

		return new ToStringBuilder(this).append("id", getId()).toString();
	}

	public DebitoACobrarGeral getDebitoACobrarGeral(){

		return debitoACobrarGeral;
	}

	public void setDebitoACobrarGeral(DebitoACobrarGeral debitoACobrarGeral){

		this.debitoACobrarGeral = debitoACobrarGeral;
	}

	public Date getDataPagamentoDocumento(){

		return dataPagamentoDocumento;
	}

	public void setDataPagamentoDocumento(Date dataPagamentoDocumento){

		this.dataPagamentoDocumento = dataPagamentoDocumento;
	}

	public Short getIndicadorRemuneracaoPaga(){

		return indicadorRemuneracaoPaga;
	}

	public void setIndicadorRemuneracaoPaga(Short indicadorRemuneracaoPaga){

		this.indicadorRemuneracaoPaga = indicadorRemuneracaoPaga;
	}

	public Date getDataPagamentoRemuneracao(){

		return dataPagamentoRemuneracao;
	}

	public void setDataPagamentoRemuneracao(Date dataPagamentoRemuneracao){

		this.dataPagamentoRemuneracao = dataPagamentoRemuneracao;
	}

	public BigDecimal getValorBaseRemuneracao(){

		return valorBaseRemuneracao;
	}

	public void setValorBaseRemuneracao(BigDecimal valorBaseRemuneracao){

		this.valorBaseRemuneracao = valorBaseRemuneracao;
	}

	public BigDecimal getValorBaseRemuneracaoParcelado(){

		return valorBaseRemuneracaoParcelado;
	}

	public void setValorBaseRemuneracaoParcelado(BigDecimal valorBaseRemuneracaoParcelado){

		this.valorBaseRemuneracaoParcelado = valorBaseRemuneracaoParcelado;
	}

	public BigDecimal getValorBaseRemuneracaoReincidente(){

		return valorBaseRemuneracaoReincidente;
	}

	public void setValorBaseRemuneracaoReincidente(BigDecimal valorBaseRemuneracaoReincidente){

		this.valorBaseRemuneracaoReincidente = valorBaseRemuneracaoReincidente;
	}

	public BigDecimal getValorBaseRemuneracaoEspecial(){

		return valorBaseRemuneracaoEspecial;
	}

	public void setValorBaseRemuneracaoEspecial(BigDecimal valorBaseRemuneracaoEspecial){

		this.valorBaseRemuneracaoEspecial = valorBaseRemuneracaoEspecial;
	}

	public Integer getAnoMesReferenciaArrecadacao(){

		return anoMesReferenciaArrecadacao;
	}

	public void setAnoMesReferenciaArrecadacao(Integer anoMesReferenciaArrecadacao){

		this.anoMesReferenciaArrecadacao = anoMesReferenciaArrecadacao;
	}

	public BigDecimal getValorArrecadadoLote(){

		return valorArrecadadoLote;
	}

	public void setValorArrecadadoLote(BigDecimal valorArrecadadoLote){

		this.valorArrecadadoLote = valorArrecadadoLote;
	}

	public CobrancaAdministrativaRemuneracaoMensal getCobrancaAdministrativaRemuneracaoMensal(){

		return cobrancaAdministrativaRemuneracaoMensal;
	}

	public void setCobrancaAdministrativaRemuneracaoMensal(CobrancaAdministrativaRemuneracaoMensal cobrancaAdministrativaRemuneracaoMensal){

		this.cobrancaAdministrativaRemuneracaoMensal = cobrancaAdministrativaRemuneracaoMensal;
	}

	@Override
	public boolean equals(Object obj){

		return getId().equals(((ImovelCobrancaAdministrivaItem) obj).getId());
	}
}
