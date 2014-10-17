package gcom.cobranca.bean;

import gcom.faturamento.GuiaPagamentoGeral;
import gcom.faturamento.conta.ContaGeral;
import gcom.faturamento.debito.DebitoACobrarGeral;
import gcom.util.ConstantesSistema;
import gcom.util.Util;

import java.math.BigDecimal;

public class ItensRemuradosHelper {

	private Short indicadorRemuneracaoRealizada;

	private Integer idDocumentoTipo;

	private ContaGeral contaGeral;

	private GuiaPagamentoGeral guiaPagamentoGeral;

	private Integer numeroPrestacaoImovelCobrancaAdmItem;

	private DebitoACobrarGeral debitoACobrarGeral;

	private Integer idGuiaPagamento;

	private String identificacaoItem;

	private BigDecimal valorBaseRemuneracao;

	private BigDecimal valorBaseRemuneracaoParcelado;

	private BigDecimal valorBaseRemuneracaoReincidente;

	private BigDecimal valorBaseRemuneracaoEspecial;

	private BigDecimal valorDaBaseRemuneracao;

	private BigDecimal valorRemuneracao;

	private BigDecimal valorRemuneracaoParcelado;

	private BigDecimal valorRemuneracaoReincidente;

	private BigDecimal valorRemuneracaoEspecial;

	private BigDecimal valorDaRemuneracao;

	private boolean contaHistorico;

	/**
	 * @return the indicadorRemuneracaoRealizada
	 */
	public Short getIndicadorRemuneracaoRealizada(){

		return indicadorRemuneracaoRealizada;
	}

	/**
	 * @param indicadorRemuneracaoRealizada
	 *            the indicadorRemuneracaoRealizada to set
	 */
	public void setIndicadorRemuneracaoRealizada(Short indicadorRemuneracaoRealizada){

		this.indicadorRemuneracaoRealizada = indicadorRemuneracaoRealizada;
	}

	/**
	 * @return the idDocumentoTipo
	 */
	public Integer getIdDocumentoTipo(){

		return idDocumentoTipo;
	}

	/**
	 * @param idDocumentoTipo
	 *            the idDocumentoTipo to set
	 */
	public void setIdDocumentoTipo(Integer idDocumentoTipo){

		this.idDocumentoTipo = idDocumentoTipo;
	}

	/**
	 * @param idConta
	 *            the idConta to set
	 */
	public void setContaGeral(ContaGeral contaGeral){

		if(Util.isNaoNuloBrancoZero(contaGeral)){
			if(contaGeral.getIndicadorHistorico() == ConstantesSistema.SIM){
				this.contaHistorico = true;
			}
		}
		this.contaGeral = contaGeral;
	}

	/**
	 * @param idGuiaPagamento
	 *            the idGuiaPagamento to set
	 */
	public void setGuiaPagamentoGeral(GuiaPagamentoGeral guiaPagamentoGeral){

		this.guiaPagamentoGeral = guiaPagamentoGeral;
	}

	/**
	 * @return the numeroPrestacaoImovelCobrancaAdmItem
	 */
	public Integer getNumeroPrestacaoImovelCobrancaAdmItem(){

		return numeroPrestacaoImovelCobrancaAdmItem;
	}

	/**
	 * @param numeroPrestacaoImovelCobrancaAdmItem
	 *            the numeroPrestacaoImovelCobrancaAdmItem to set
	 */
	public void setNumeroPrestacaoImovelCobrancaAdmItem(Integer numeroPrestacaoImovelCobrancaAdmItem){

		this.numeroPrestacaoImovelCobrancaAdmItem = numeroPrestacaoImovelCobrancaAdmItem;
	}

	/**
	 * @param idDebitoACobrar
	 *            the idDebitoACobrar to set
	 */
	public void setDebitoACobrarGeral(DebitoACobrarGeral debitoACobrarGeral){

		this.debitoACobrarGeral = debitoACobrarGeral;
	}

	/**
	 * @return the identificacaoItem
	 */
	public String getIdentificacaoItem(){

		String identificacaoItem = "";
		if(Util.isNaoNuloBrancoZero(this.contaGeral)){
			identificacaoItem = this.contaGeral.getId().toString();
			if(this.contaGeral.getIndicadorHistorico() == ConstantesSistema.SIM){
				this.contaHistorico = true;
			}
		}else if(Util.isNaoNuloBrancoZero(this.guiaPagamentoGeral)){
			identificacaoItem = this.guiaPagamentoGeral.getId() + "/" + this.numeroPrestacaoImovelCobrancaAdmItem;
			idGuiaPagamento = this.guiaPagamentoGeral.getId();
		}else if(Util.isNaoNuloBrancoZero(this.debitoACobrarGeral)){
			identificacaoItem = this.debitoACobrarGeral.getId().toString();
		}else{
			identificacaoItem = this.identificacaoItem;
		}

		return identificacaoItem;
	}

	/**
	 * @param identificacaoItem
	 *            the identificacaoItem to set
	 */
	public void setIdentificacaoItem(String identificacaoItem){

		this.identificacaoItem = identificacaoItem;
	}

	/**
	 * @param valorBaseRemuneracao
	 *            the valorBaseRemuneracao to set
	 */
	public void setValorBaseRemuneracao(BigDecimal valorBaseRemuneracao){

		this.valorBaseRemuneracao = valorBaseRemuneracao;
	}

	/**
	 * @param valorRemuneracao
	 *            the valorRemuneracao to set
	 */
	public void setValorRemuneracao(BigDecimal valorRemuneracao){

		this.valorRemuneracao = valorRemuneracao;
	}

	/**
	 * @return the valorDaBaseRemuneracao
	 */
	public BigDecimal getValorDaBaseRemuneracao(){

		BigDecimal valorDaBaseRemuneracao = BigDecimal.ZERO;

		if(Util.isNaoNuloBrancoZero(this.valorDaBaseRemuneracao)){
			valorDaBaseRemuneracao = this.valorDaBaseRemuneracao;
		}
		if(Util.isNaoNuloBrancoZero(this.valorBaseRemuneracao)){
			valorDaBaseRemuneracao = valorDaBaseRemuneracao.add(this.valorBaseRemuneracao);
		}
		if(Util.isNaoNuloBrancoZero(this.valorBaseRemuneracaoParcelado)){
			valorDaBaseRemuneracao = valorDaBaseRemuneracao.add(this.valorBaseRemuneracaoParcelado);
		}
		if(Util.isNaoNuloBrancoZero(this.valorBaseRemuneracaoReincidente)){
			valorDaBaseRemuneracao = valorDaBaseRemuneracao.add(this.valorBaseRemuneracaoReincidente);
		}
		if(Util.isNaoNuloBrancoZero(this.valorBaseRemuneracaoEspecial)){
			valorDaBaseRemuneracao = valorDaBaseRemuneracao.add(this.valorBaseRemuneracaoEspecial);
		}

		return valorDaBaseRemuneracao;
	}

	/**
	 * @param valorDaBaseRemuneracao
	 *            the valorDaBaseRemuneracao to set
	 */
	public void setValorDaBaseRemuneracao(BigDecimal valorDaBaseRemuneracao){

		this.valorDaBaseRemuneracao = valorDaBaseRemuneracao;
	}

	/**
	 * @return the valorDaRemuneracao
	 */
	public BigDecimal getValorDaRemuneracao(){

		BigDecimal valorDaRemuneracao = BigDecimal.ZERO;

		if(Util.isNaoNuloBrancoZero(this.valorDaRemuneracao)){
			valorDaRemuneracao = this.valorDaRemuneracao;
		}
		if(Util.isNaoNuloBrancoZero(this.valorRemuneracao)){
			valorDaRemuneracao = valorDaRemuneracao.add(this.valorRemuneracao);
		}
		if(Util.isNaoNuloBrancoZero(this.valorRemuneracaoParcelado)){
			valorDaRemuneracao = valorDaRemuneracao.add(this.valorRemuneracaoParcelado);
		}
		if(Util.isNaoNuloBrancoZero(this.valorRemuneracaoReincidente)){
			valorDaRemuneracao = valorDaRemuneracao.add(this.valorRemuneracaoReincidente);
		}
		if(Util.isNaoNuloBrancoZero(this.valorRemuneracaoEspecial)){
			valorDaRemuneracao = valorDaRemuneracao.add(this.valorRemuneracaoEspecial);
		}

		return valorDaRemuneracao;
	}

	/**
	 * @param valorDaRemuneracao
	 *            the valorDaRemuneracao to set
	 */
	public void setValorDaRemuneracao(BigDecimal valorDaRemuneracao){

		this.valorDaRemuneracao = valorDaRemuneracao;
	}


	/**
	 * @param valorBaseRemuneracaoParcelado
	 *            the valorBaseRemuneracaoParcelado to set
	 */
	public void setValorBaseRemuneracaoParcelado(BigDecimal valorBaseRemuneracaoParcelado){

		this.valorBaseRemuneracaoParcelado = valorBaseRemuneracaoParcelado;
	}

	/**
	 * @param valorBaseRemuneracaoReincidente
	 *            the valorBaseRemuneracaoReincidente to set
	 */
	public void setValorBaseRemuneracaoReincidente(BigDecimal valorBaseRemuneracaoReincidente){

		this.valorBaseRemuneracaoReincidente = valorBaseRemuneracaoReincidente;
	}


	/**
	 * @param valorBaseRemuneracaoEspecial
	 *            the valorBaseRemuneracaoEspecial to set
	 */
	public void setValorBaseRemuneracaoEspecial(BigDecimal valorBaseRemuneracaoEspecial){

		this.valorBaseRemuneracaoEspecial = valorBaseRemuneracaoEspecial;
	}

	/**
	 * @param valorRemuneracaoParcelado
	 *            the valorRemuneracaoParcelado to set
	 */
	public void setValorRemuneracaoParcelado(BigDecimal valorRemuneracaoParcelado){

		this.valorRemuneracaoParcelado = valorRemuneracaoParcelado;
	}


	/**
	 * @param valorRemuneracaoReincidente
	 *            the valorRemuneracaoReincidente to set
	 */
	public void setValorRemuneracaoReincidente(BigDecimal valorRemuneracaoReincidente){

		this.valorRemuneracaoReincidente = valorRemuneracaoReincidente;
	}

	/**
	 * @param valorRemuneracaoEspecial
	 *            the valorRemuneracaoEspecial to set
	 */
	public void setValorRemuneracaoEspecial(BigDecimal valorRemuneracaoEspecial){

		this.valorRemuneracaoEspecial = valorRemuneracaoEspecial;
	}

	/**
	 * @return the contaHistorico
	 */
	public boolean isContaHistorico(){

		return contaHistorico;
	}

	/**
	 * @param contaHistorico
	 *            the contaHistorico to set
	 */
	public void setContaHistorico(boolean contaHistorico){

		this.contaHistorico = contaHistorico;
	}

	/**
	 * @return the idGuiaPagamento
	 */
	public Integer getIdGuiaPagamento(){

		if(Util.isVazioOuBranco(idGuiaPagamento)){
			getIdentificacaoItem();
		}

		return idGuiaPagamento;
	}

	/**
	 * @param idGuiaPagamento
	 *            the idGuiaPagamento to set
	 */
	public void setIdGuiaPagamento(Integer idGuiaPagamento){

		this.idGuiaPagamento = idGuiaPagamento;
	}

}
