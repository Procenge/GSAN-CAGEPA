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
import gcom.interceptor.ControleAlteracao;
import gcom.interceptor.ObjetoTransacao;
import gcom.util.filtro.Filtro;
import gcom.util.filtro.ParametroSimples;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author eduardo henrique
 * @since v0.03
 * @date 24/07/2008
 */
@ControleAlteracao
public class GuiaPagamentoPrestacao
				extends ObjetoTransacao {

	public static final int ATRIBUTOS_GUIA_PAGAMENTO_CANCELAR = 445;

	public static final int ATRIBUTOS_DESMARCAR_PRESCRICAO_DEBITOS = 900204;

	/** identifier field */
	@ControleAlteracao(value = FiltroGuiaPagamentoPrestacao.ID, funcionalidade = {ATRIBUTOS_GUIA_PAGAMENTO_CANCELAR})
	private GuiaPagamentoPrestacaoPK comp_id;

	private BigDecimal valorPrestacao;

	private Date dataVencimento;

	private Short indicadorPagamentoPendente;

	private Short indicadorCobrancaMulta;

	private Integer anoMesReferenciaFaturamento;

	private Date dataEmissao;

	private Short numeroPrestacao;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_GUIA_PAGAMENTO_CANCELAR})
	private Date ultimaAlteracao;

	private GuiaPagamento guiaPagamento;

	private DebitoTipo debitoTipo;

	private LancamentoItemContabil lancamentoItemContabil;

	private FinanciamentoTipo financiamentoTipo;

	@ControleAlteracao(value = FiltroGuiaPagamentoPrestacao.DEBITO_CREDITO_SITUACAO_ATUAL, funcionalidade = {ATRIBUTOS_DESMARCAR_PRESCRICAO_DEBITOS})
	private DebitoCreditoSituacao debitoCreditoSituacao;

	private Short indicadorCobrancaAdministrativa = 2;

	// Usado para exibição na tela.
	private String indicadorPagamentoHint;

	private Short indicadorRemuneraCobrancaAdministrativa = 2;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_DESMARCAR_PRESCRICAO_DEBITOS})
	private PrescricaoComando prescricaoComando;

	@ControleAlteracao(value = FiltroGuiaPagamentoPrestacao.DEBITO_CREDITO_SITUACAO_ANTERIOR, funcionalidade = {ATRIBUTOS_DESMARCAR_PRESCRICAO_DEBITOS})
	private DebitoCreditoSituacao debitoCreditoSituacaoAnterior;

	/**
	 * Default Constructor
	 */
	public GuiaPagamentoPrestacao() {

	}

	/**
	 * Full Constructor
	 * 
	 * @param comp_id
	 * @param valorPrestacao
	 * @param dataVencimento
	 * @param indicadorPagamentoPendente
	 * @param indicadorCobrancaMulta
	 * @param anoMesReferenciaFaturamento
	 * @param dataEmissao
	 * @param ultimaAlteracao
	 * @param guiaPagamento
	 * @param debitoTipo
	 * @param lancamentoItemContabil
	 * @param financiamentoTipo
	 * @param debitoCreditoSituacao
	 */
	public GuiaPagamentoPrestacao(GuiaPagamentoPrestacaoPK comp_id, BigDecimal valorPrestacao, Date dataVencimento,
									Short indicadorPagamentoPendente, Short indicadorCobrancaMulta, Integer anoMesReferenciaFaturamento,
									Date dataEmissao, Date ultimaAlteracao, GuiaPagamento guiaPagamento, DebitoTipo debitoTipo,
									LancamentoItemContabil lancamentoItemContabil, FinanciamentoTipo financiamentoTipo,
									DebitoCreditoSituacao debitoCreditoSituacao) {

		this.comp_id = comp_id;
		this.valorPrestacao = valorPrestacao;
		this.dataVencimento = dataVencimento;
		this.indicadorPagamentoPendente = indicadorPagamentoPendente;
		this.indicadorCobrancaMulta = indicadorCobrancaMulta;
		this.anoMesReferenciaFaturamento = anoMesReferenciaFaturamento;
		this.dataEmissao = dataEmissao;
		this.ultimaAlteracao = ultimaAlteracao;
		this.guiaPagamento = guiaPagamento;
		this.debitoTipo = debitoTipo;
		this.lancamentoItemContabil = lancamentoItemContabil;
		this.financiamentoTipo = financiamentoTipo;
		this.debitoCreditoSituacao = debitoCreditoSituacao;
	}

	@Override
	public String[] retornaCamposChavePrimaria(){

		String[] retorno = new String[1];

		retorno[0] = "comp_id";

		return retorno;
	}

	@Override
	public Filtro retornaFiltro(){

		FiltroGuiaPagamentoPrestacao filtroGuiaPagamentoPrestacao = new FiltroGuiaPagamentoPrestacao();

		filtroGuiaPagamentoPrestacao.adicionarCaminhoParaCarregamentoEntidade("guiaPagamento");
		filtroGuiaPagamentoPrestacao.adicionarCaminhoParaCarregamentoEntidade("debitoTipo");
		filtroGuiaPagamentoPrestacao.adicionarCaminhoParaCarregamentoEntidade("lancamentoItemContabil");
		filtroGuiaPagamentoPrestacao.adicionarCaminhoParaCarregamentoEntidade("debitoCreditoSituacao");
		filtroGuiaPagamentoPrestacao.adicionarCaminhoParaCarregamentoEntidade("debitoCreditoSituacaoAnterior");
		filtroGuiaPagamentoPrestacao.adicionarCaminhoParaCarregamentoEntidade("prescricaoComando");
		filtroGuiaPagamentoPrestacao.adicionarCaminhoParaCarregamentoEntidade("comp_id");

		Integer guiaPagamentoId = null;
		if(comp_id != null && comp_id.getGuiaPagamentoId() != null){
			guiaPagamentoId = comp_id.getGuiaPagamentoId();
		}else if(guiaPagamento != null && guiaPagamento.getId() != null){
			guiaPagamentoId = guiaPagamento.getId();
		}

		Integer debitoTipoId = null;
		if(comp_id != null && comp_id.getDebitoTipoId() != null){
			debitoTipoId = comp_id.getDebitoTipoId();
		}else if(debitoTipo != null && debitoTipo.getId() != null){
			debitoTipoId = debitoTipo.getId();
		}

		Integer lancamentoItemContabilId = null;
		if(comp_id != null && comp_id.getItemLancamentoContabilId() != null){
			lancamentoItemContabilId = comp_id.getItemLancamentoContabilId();
		}else if(lancamentoItemContabil != null && lancamentoItemContabil.getId() != null){
			lancamentoItemContabilId = lancamentoItemContabil.getId();
		}

		filtroGuiaPagamentoPrestacao.adicionarParametro(new ParametroSimples(FiltroGuiaPagamentoPrestacao.GUIA_PAGAMENTO_ID,
						guiaPagamentoId));
		filtroGuiaPagamentoPrestacao.adicionarParametro(new ParametroSimples(FiltroGuiaPagamentoPrestacao.NUMERO_PRESTACAO, comp_id
						.getNumeroPrestacao()));
		filtroGuiaPagamentoPrestacao.adicionarParametro(new ParametroSimples(FiltroGuiaPagamentoPrestacao.DEBITO_TIPO_ID, debitoTipoId));
		filtroGuiaPagamentoPrestacao.adicionarParametro(new ParametroSimples(FiltroGuiaPagamentoPrestacao.ITEM_LANCAMENTO_CONTABIL_ID,
						lancamentoItemContabilId));
		return filtroGuiaPagamentoPrestacao;
	}

	/**
	 * @return the comp_id
	 */
	public GuiaPagamentoPrestacaoPK getComp_id(){

		return comp_id;
	}

	/**
	 * @param comp_id
	 *            the comp_id to set
	 */
	public void setComp_id(GuiaPagamentoPrestacaoPK comp_id){

		this.comp_id = comp_id;
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
	 * @return the numeroPrestacao
	 */
	public Short getNumeroPrestacao(){

		return numeroPrestacao;
	}

	/**
	 * @param numeroPrestacao
	 *            the numeroPrestacao to set
	 */
	public void setNumeroPrestacao(Short numeroPrestacao){

		this.numeroPrestacao = numeroPrestacao;
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

	@Override
	public void initializeLazy(){

		retornaCamposChavePrimaria();
	}

	@Override
	public int hashCode(){

		final int prime = 31;
		int result = 1;
		result = prime * result + ((comp_id == null) ? 0 : comp_id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj){

		if(this == obj) return true;
		if(obj == null) return false;
		if(getClass() != obj.getClass()) return false;
		final GuiaPagamentoPrestacao other = (GuiaPagamentoPrestacao) obj;
		if(comp_id == null){
			if(other.comp_id != null) return false;
		}else if(!comp_id.equals(other.comp_id)) return false;
		return true;
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

	public String[] retornarAtributosInformacoesOperacaoEfetuada(){

		String[] atributos = {"comp_id.guiaPagamentoId", "comp_id.numeroPrestacao"};
		return atributos;
	}

	public String[] retornarLabelsInformacoesOperacaoEfetuada(){

		String[] labels = {"id da Guia de Pagamento", "Numero da Prestacao"};
		return labels;
	}

	@Override
	public String getDescricaoParaRegistroTransacao(){

		return getComp_id().getGuiaPagamentoId().toString() + "-" + getComp_id().getNumeroPrestacao().toString();
	}
}
