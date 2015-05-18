/* This file is part of GSAN, an integrated service management system for Sanitation
 *
 * GSAN is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License.
 *
 * GSAN is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place – Suite 330, Boston, MA 02111-1307, USA
 */

/*
 * GSAN – Sistema Integrado de Gestão de Serviços de Saneamento
 * Copyright (C) <2007> 
 *
 * GSANPCG
 * Eduardo Henrique
 * 
 *
 * Este programa é software livre; você pode redistribuí-lo e/ou
 * modificá-lo sob os termos de Licença Pública Geral GNU, conforme
 * publicada pela Free Software Foundation; versão 2 da
 * Licença.
 * Este programa é distribuído na expectativa de ser útil, mas SEM
 * QUALQUER GARANTIA; sem mesmo a garantia implícita de
 * COMERCIALIZAÇÃO ou de ADEQUAÇÃO A QUALQUER PROPÓSITO EM
 * PARTICULAR. Consulte a Licença Pública Geral GNU para obter mais
 * detalhes.
 * Você deve ter recebido uma cópia da Licença Pública Geral GNU
 * junto com este programa; se não, escreva para Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 * 02111-1307, USA.
 */

package gcom.arrecadacao.pagamento;

import gcom.cobranca.prescricao.PrescricaoComando;
import gcom.faturamento.debito.DebitoCreditoSituacao;
import gcom.faturamento.debito.DebitoTipo;
import gcom.financeiro.FinanciamentoTipo;
import gcom.financeiro.lancamento.LancamentoItemContabil;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author eduardo henrique
 * @date 11/08/2008
 * @since v0.04
 */
public class GuiaPagamentoPrestacaoHistorico {

	/** identifier field */
	private GuiaPagamentoPrestacaoHistoricoPK comp_id;

	private DebitoCreditoSituacao debitoCreditoSituacao;

	private FinanciamentoTipo financiamentoTipo;

	private BigDecimal valorPrestacao;

	private Date dataVencimento;

	private Short indicadorPagamentoPendente;

	private Short indicadorCobrancaMulta;

	private Integer anoMesReferenciaFaturamento;

	private Integer anoMesReferenciaArrecadacao;

	private Date dataEmissao;

	/** nullable persistent field */
	private Date ultimaAlteracao;

	private GuiaPagamento guiaPagamento;

	private DebitoTipo debitoTipo;

	private LancamentoItemContabil lancamentoItemContabil;

	private Short indicadorCobrancaAdministrativa = 2;

	private PrescricaoComando prescricaoComando;

	private DebitoCreditoSituacao debitoCreditoSituacaoAnterior;

	// Usado para exibição na tela.
	private String indicadorPagamentoHint;

	private Short indicadorRemuneraCobrancaAdministrativa = 2;

	private Short indicadorDividaAtiva = 2;

	private Date dataDividaAtiva;

	private Short indicadorExecucaoFiscal = 2;

	private Date dataExecucaoFiscal;

	public GuiaPagamentoPrestacaoHistorico() {

	}

	public GuiaPagamentoPrestacaoHistorico(GuiaPagamentoPrestacaoHistoricoPK comp_id, DebitoCreditoSituacao debitoCreditoSituacao,
											FinanciamentoTipo financiamentoTipo, BigDecimal valorPrestacao, Date dataVencimento,
											Short indicadorPagamentoPendente, Short indicadorCobrancaMulta,
											Integer anoMesReferenciaFaturamento, Integer anoMesReferenciaArrecadacao, Date dataEmissao,
											Date ultimaAlteracao, GuiaPagamento guiaPagamento, DebitoTipo debitoTipo,
											LancamentoItemContabil lancamentoItemContabil, Short indicadorDividaAtiva,
											Date dataDividaAtiva, Short indicadorExecucaoFiscal, Date dataExecucaoFiscal) {

		this.comp_id = comp_id;
		this.debitoCreditoSituacao = debitoCreditoSituacao;
		this.financiamentoTipo = financiamentoTipo;
		this.valorPrestacao = valorPrestacao;
		this.dataVencimento = dataVencimento;
		this.indicadorPagamentoPendente = indicadorPagamentoPendente;
		this.indicadorCobrancaMulta = indicadorCobrancaMulta;
		this.anoMesReferenciaFaturamento = anoMesReferenciaFaturamento;
		this.anoMesReferenciaArrecadacao = anoMesReferenciaArrecadacao;
		this.dataEmissao = dataEmissao;
		this.ultimaAlteracao = ultimaAlteracao;
		this.guiaPagamento = guiaPagamento;
		this.debitoTipo = debitoTipo;
		this.lancamentoItemContabil = lancamentoItemContabil;
		this.indicadorDividaAtiva = indicadorDividaAtiva;
		this.dataDividaAtiva = dataDividaAtiva;
		this.indicadorExecucaoFiscal = indicadorExecucaoFiscal;
		this.dataExecucaoFiscal = dataExecucaoFiscal;
	}

	/**
	 * @return the comp_id
	 */
	public GuiaPagamentoPrestacaoHistoricoPK getComp_id(){

		return comp_id;
	}

	/**
	 * @param comp_id
	 *            the comp_id to set
	 */
	public void setComp_id(GuiaPagamentoPrestacaoHistoricoPK comp_id){

		this.comp_id = comp_id;
	}

	/**
	 * @return the debitoCreditoSituacao
	 */
	public DebitoCreditoSituacao getDebitoCreditoSituacao(){

		return debitoCreditoSituacao;
	}

	/**
	 * @param debitoCreditoSituacao
	 *            the debitoCreditoSituacao to set
	 */
	public void setDebitoCreditoSituacao(DebitoCreditoSituacao debitoCreditoSituacao){

		this.debitoCreditoSituacao = debitoCreditoSituacao;
	}

	/**
	 * @return the financiamentoTipo
	 */
	public FinanciamentoTipo getFinanciamentoTipo(){

		return financiamentoTipo;
	}

	/**
	 * @param financiamentoTipo
	 *            the financiamentoTipo to set
	 */
	public void setFinanciamentoTipo(FinanciamentoTipo financiamentoTipo){

		this.financiamentoTipo = financiamentoTipo;
	}

	/**
	 * @return the valorPrestacao
	 */
	public BigDecimal getValorPrestacao(){

		return valorPrestacao;
	}

	/**
	 * @param valorPrestacao
	 *            the valorPrestacao to set
	 */
	public void setValorPrestacao(BigDecimal valorPrestacao){

		this.valorPrestacao = valorPrestacao;
	}

	/**
	 * @return the dataVencimento
	 */
	public Date getDataVencimento(){

		return dataVencimento;
	}

	/**
	 * @param dataVencimento
	 *            the dataVencimento to set
	 */
	public void setDataVencimento(Date dataVencimento){

		this.dataVencimento = dataVencimento;
	}

	/**
	 * @return the indicadorPagamentoPendente
	 */
	public Short getIndicadorPagamentoPendente(){

		return indicadorPagamentoPendente;
	}

	/**
	 * @return the indicadorPagamentoPendente
	 */
	public String getIndicadorPagamentoPendenteStr(){

		return indicadorPagamentoPendente == 1 ? "S" : "";
	}

	/**
	 * @return the indicadorPagamentoHint
	 */
	public String getIndicadorPagamentoHint(){

		return indicadorPagamentoHint;
	}

	/**
	 * @param indicadorPagamentoHint
	 *            the indicadorPagamentoHint to set
	 */
	public void setIndicadorPagamentoHint(String indicadorPagamentoHint){

		this.indicadorPagamentoHint = indicadorPagamentoHint;
	}

	/**
	 * @param indicadorPagamentoPendente
	 *            the indicadorPagamentoPendente to set
	 */
	public void setIndicadorPagamentoPendente(Short indicadorPagamentoPendente){

		this.indicadorPagamentoPendente = indicadorPagamentoPendente;
	}

	/**
	 * @return the indicadorCobrancaMulta
	 */
	public Short getIndicadorCobrancaMulta(){

		return indicadorCobrancaMulta;
	}

	/**
	 * @param indicadorCobrancaMulta
	 *            the indicadorCobrancaMulta to set
	 */
	public void setIndicadorCobrancaMulta(Short indicadorCobrancaMulta){

		this.indicadorCobrancaMulta = indicadorCobrancaMulta;
	}

	/**
	 * @return the anoMesReferenciaFaturamento
	 */
	public Integer getAnoMesReferenciaFaturamento(){

		return anoMesReferenciaFaturamento;
	}

	/**
	 * @param anoMesReferenciaFaturamento
	 *            the anoMesReferenciaFaturamento to set
	 */
	public void setAnoMesReferenciaFaturamento(Integer anoMesReferenciaFaturamento){

		this.anoMesReferenciaFaturamento = anoMesReferenciaFaturamento;
	}

	/**
	 * @return the anoMesReferenciaArrecadacao
	 */
	public Integer getAnoMesReferenciaArrecadacao(){

		return anoMesReferenciaArrecadacao;
	}

	/**
	 * @param anoMesReferenciaArrecadacao
	 *            the anoMesReferenciaArrecadacao to set
	 */
	public void setAnoMesReferenciaArrecadacao(Integer anoMesReferenciaArrecadacao){

		this.anoMesReferenciaArrecadacao = anoMesReferenciaArrecadacao;
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
	 * @return the debitoTipo
	 */
	public DebitoTipo getDebitoTipo(){

		return debitoTipo;
	}

	/**
	 * @param debitoTipo
	 *            the debitoTipo to set
	 */
	public void setDebitoTipo(DebitoTipo debitoTipo){

		this.debitoTipo = debitoTipo;
	}

	/**
	 * @return the lancamentoItemContabil
	 */
	public LancamentoItemContabil getLancamentoItemContabil(){

		return lancamentoItemContabil;
	}

	/**
	 * @param lancamentoItemContabil
	 *            the lancamentoItemContabil to set
	 */
	public void setLancamentoItemContabil(LancamentoItemContabil lancamentoItemContabil){

		this.lancamentoItemContabil = lancamentoItemContabil;
	}

	public Short getIndicadorCobrancaAdministrativa(){

		return indicadorCobrancaAdministrativa;
	}

	public void setIndicadorCobrancaAdministrativa(Short indicadorCobrancaAdministrativa){

		this.indicadorCobrancaAdministrativa = indicadorCobrancaAdministrativa;
	}

	public Short getIndicadorRemuneraCobrancaAdministrativa(){

		return indicadorRemuneraCobrancaAdministrativa;
	}

	public void setIndicadorRemuneraCobrancaAdministrativa(Short indicadorRemuneraCobrancaAdministrativa){

		this.indicadorRemuneraCobrancaAdministrativa = indicadorRemuneraCobrancaAdministrativa;
	}

	public PrescricaoComando getPrescricaoComando(){

		return prescricaoComando;
	}

	public void setPrescricaoComando(PrescricaoComando prescricaoComando){

		this.prescricaoComando = prescricaoComando;
	}

	public DebitoCreditoSituacao getDebitoCreditoSituacaoAnterior(){

		return debitoCreditoSituacaoAnterior;
	}

	public void setDebitoCreditoSituacaoAnterior(DebitoCreditoSituacao debitoCreditoSituacaoAnterior){

		this.debitoCreditoSituacaoAnterior = debitoCreditoSituacaoAnterior;
	}

	public Short getIndicadorDividaAtiva(){

		return indicadorDividaAtiva;
	}

	public void setIndicadorDividaAtiva(Short indicadorDividaAtiva){

		this.indicadorDividaAtiva = indicadorDividaAtiva;
	}

	public Date getDataDividaAtiva(){

		return dataDividaAtiva;
	}

	public void setDataDividaAtiva(Date dataDividaAtiva){

		this.dataDividaAtiva = dataDividaAtiva;
	}

	public Short getIndicadorExecucaoFiscal(){

		return indicadorExecucaoFiscal;
	}

	public void setIndicadorExecucaoFiscal(Short indicadorExecucaoFiscal){

		this.indicadorExecucaoFiscal = indicadorExecucaoFiscal;
	}

	public Date getDataExecucaoFiscal(){

		return dataExecucaoFiscal;
	}

	public void setDataExecucaoFiscal(Date dataExecucaoFiscal){

		this.dataExecucaoFiscal = dataExecucaoFiscal;
	}

}
