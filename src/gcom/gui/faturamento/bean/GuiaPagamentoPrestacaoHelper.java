/*
 * Copyright (C) 2007-2007 the GSAN – Sistema Integrado de Gestão de Serviços de Saneamento
 *
 * This file is part of GSAN, an integrated service management system for Sanitation
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

package gcom.gui.faturamento.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author eduardo henrique
 * @date 04/08/2008
 *       Classe Helper utilizada na visualização das Prestações de uma Guia de Pagamento
 */
public class GuiaPagamentoPrestacaoHelper
				implements Serializable {

	private Long id;

	private String descricaoTipoDebito;

	private BigDecimal valorTipoDebito;

	private BigDecimal valorPrestacaoTipoDebito;

	private Date ultimaAlteracao;

	private BigDecimal valorTotalPorPrestacao;

	private Integer idDocumentoTipo;

	private String descricaoDocumentoTipo;

	private Short numeroPrestacaoTotal;

	private Date dataEmissao;

	private Date dataVencimento;

	private Short indicadorPagamento;

	private String dsIndicadorPagamento;

	private Integer idGuiaPagamento;

	private Integer idTipoDebito;

	private Short numeroPrestacao;

	private Integer idLancamentoItemContabil;

	private String descricaoDebitoCreditoSituacao;

	private Integer idCliente;

	private String nomeCliente;

	private Date dataPagamento;

	private BigDecimal valorPagamento;

	private String debitoTipoHint;

	private String indicadorPagamentoHint;

	private Short idOcorrenciaHistorico;

	private Integer idImovel;

	private Short indicadorCobrancaAdministrativa;

	private String valorPagamentoStr;

	private Integer numeroContratoParcelOrgaoPublico;

	private String idDebitoCreditoSituacao;

	private Short indicadorDividaAtiva;

	private Short indicadorExecucaoFiscal;

	private Integer numeroProcessoAdministrativoExecucaoFiscal;

	/**
	 * @return the numeroProcessoAdministrativoExecucaoFiscal
	 */
	public Integer getNumeroProcessoAdministrativoExecucaoFiscal(){

		return numeroProcessoAdministrativoExecucaoFiscal;
	}

	/**
	 * @param numeroProcessoAdministrativoExecucaoFiscal
	 *            the numeroProcessoAdministrativoExecucaoFiscal to set
	 */
	public void setNumeroProcessoAdministrativoExecucaoFiscal(Integer numeroProcessoAdministrativoExecucaoFiscal){

		this.numeroProcessoAdministrativoExecucaoFiscal = numeroProcessoAdministrativoExecucaoFiscal;
	}

	/**
	 * @return the indicadorDividaAtiva
	 */
	public Short getIndicadorDividaAtiva(){

		return indicadorDividaAtiva;
	}

	/**
	 * @param indicadorDividaAtiva
	 *            the indicadorDividaAtiva to set
	 */
	public void setIndicadorDividaAtiva(Short indicadorDividaAtiva){

		this.indicadorDividaAtiva = indicadorDividaAtiva;
	}

	/**
	 * @return the indicadorExecucaoFiscal
	 */
	public Short getIndicadorExecucaoFiscal(){

		return indicadorExecucaoFiscal;
	}

	/**
	 * @param indicadorExecucaoFiscal
	 *            the indicadorExecucaoFiscal to set
	 */
	public void setIndicadorExecucaoFiscal(Short indicadorExecucaoFiscal){

		this.indicadorExecucaoFiscal = indicadorExecucaoFiscal;
	}

	/**
	 * @return the descricaoTipoDebito
	 */
	public String getDescricaoTipoDebito(){

		return descricaoTipoDebito;
	}

	/**
	 * @param descricaoTipoDebito
	 *            the descricaoTipoDebito to set
	 */
	public void setDescricaoTipoDebito(String descricaoTipoDebito){

		this.descricaoTipoDebito = descricaoTipoDebito;
	}

	/**
	 * @return the valorTipoDebito
	 */
	public BigDecimal getValorTipoDebito(){

		return valorTipoDebito;
	}

	/**
	 * @param valorTipoDebito
	 *            the valorTipoDebito to set
	 */
	public void setValorTipoDebito(BigDecimal valorTipoDebito){

		this.valorTipoDebito = valorTipoDebito;
	}

	/**
	 * @return the valorPrestacaoTipoDebito
	 */
	public BigDecimal getValorPrestacaoTipoDebito(){

		return valorPrestacaoTipoDebito;
	}

	/**
	 * @param valorPrestacaoTipoDebito
	 *            the valorPrestacaoTipoDebito to set
	 */
	public void setValorPrestacaoTipoDebito(BigDecimal valorPrestacaoTipoDebito){

		this.valorPrestacaoTipoDebito = valorPrestacaoTipoDebito;
	}

	/**
	 * @param idGuiaPagamento
	 *            TODO
	 * @param idTipoDebito
	 *            TODO
	 * @param idTipoDebito
	 * @param idLancamentoItemContabil
	 *            TODO
	 * @param descricaoDebitoCreditoSituacao
	 *            TODO
	 * @param descricaoTipoDebito
	 * @param valorTipoDebito
	 * @param valorPrestacaoTipoDebito
	 */
	public GuiaPagamentoPrestacaoHelper(Integer idGuiaPagamento, Short numeroPrestacao, Integer idTipoDebito,
										Integer idLancamentoItemContabil, String descricaoDocumentoTipo, Short numeroPrestacaoTotal,
										Date dataEmissao, Date dataVencimento, BigDecimal valorPrestacao, Short indicadorPagamentoPendente,
										String descricaoDebitoCreditoSituacao) {

		this.idGuiaPagamento = idGuiaPagamento;
		this.numeroPrestacao = numeroPrestacao;
		this.idTipoDebito = idTipoDebito;
		this.idLancamentoItemContabil = idLancamentoItemContabil;
		this.descricaoDocumentoTipo = descricaoDocumentoTipo;
		this.numeroPrestacaoTotal = numeroPrestacaoTotal;
		this.dataEmissao = dataEmissao;
		this.dataVencimento = dataVencimento;
		this.valorPrestacaoTipoDebito = valorPrestacao;
		this.indicadorPagamento = indicadorPagamentoPendente;
		this.descricaoDebitoCreditoSituacao = descricaoDebitoCreditoSituacao;
	}

	/**
	 * @param idGuiaPagamento
	 *            TODO
	 * @param idTipoDebito
	 *            TODO
	 * @param idTipoDebito
	 * @param idLancamentoItemContabil
	 *            TODO
	 * @param descricaoDebitoCreditoSituacao
	 *            TODO
	 * @param descricaoTipoDebito
	 * @param valorTipoDebito
	 * @param valorPrestacaoTipoDebito
	 */
	public GuiaPagamentoPrestacaoHelper(Integer idGuiaPagamento, Short numeroPrestacao, Integer idTipoDebito,
										Integer idLancamentoItemContabil, String descricaoDocumentoTipo, Short numeroPrestacaoTotal,
										Date dataEmissao, Date dataVencimento, BigDecimal valorPrestacao, Short indicadorPagamentoPendente,
										String descricaoDebitoCreditoSituacao, Short indicadorExecucaoFiscal) {

		this.idGuiaPagamento = idGuiaPagamento;
		this.numeroPrestacao = numeroPrestacao;
		this.idTipoDebito = idTipoDebito;
		this.idLancamentoItemContabil = idLancamentoItemContabil;
		this.descricaoDocumentoTipo = descricaoDocumentoTipo;
		this.numeroPrestacaoTotal = numeroPrestacaoTotal;
		this.dataEmissao = dataEmissao;
		this.dataVencimento = dataVencimento;
		this.valorPrestacaoTipoDebito = valorPrestacao;
		this.indicadorPagamento = indicadorPagamentoPendente;
		this.descricaoDebitoCreditoSituacao = descricaoDebitoCreditoSituacao;
		this.indicadorExecucaoFiscal = indicadorExecucaoFiscal;
	}

	public GuiaPagamentoPrestacaoHelper(Integer idGuiaPagamento, Short numeroPrestacao, Integer idTipoDebito,
										Integer idLancamentoItemContabil, String descricaoDocumentoTipo, Short numeroPrestacaoTotal,
										Date dataEmissao, Date dataVencimento, BigDecimal valorPrestacao, Short indicadorPagamentoPendente,
										String descricaoDebitoCreditoSituacao, Short indicadorExecucaoFiscal, Short indicadorDividaAtiva) {

		this.idGuiaPagamento = idGuiaPagamento;
		this.numeroPrestacao = numeroPrestacao;
		this.idTipoDebito = idTipoDebito;
		this.idLancamentoItemContabil = idLancamentoItemContabil;
		this.descricaoDocumentoTipo = descricaoDocumentoTipo;
		this.numeroPrestacaoTotal = numeroPrestacaoTotal;
		this.dataEmissao = dataEmissao;
		this.dataVencimento = dataVencimento;
		this.valorPrestacaoTipoDebito = valorPrestacao;
		this.indicadorPagamento = indicadorPagamentoPendente;
		this.descricaoDebitoCreditoSituacao = descricaoDebitoCreditoSituacao;
		this.indicadorExecucaoFiscal = indicadorExecucaoFiscal;
		this.indicadorDividaAtiva = indicadorDividaAtiva;
	}

	public GuiaPagamentoPrestacaoHelper(Long id, String descricaoTipoDebito, BigDecimal valorTipoDebito, BigDecimal valorPrestacaoTipoDebito) {

		this.id = id;
		this.descricaoTipoDebito = descricaoTipoDebito;
		this.valorTipoDebito = valorTipoDebito;
		this.valorPrestacaoTipoDebito = valorPrestacaoTipoDebito;
	}

	public GuiaPagamentoPrestacaoHelper(String descricaoTipoDebito, BigDecimal valorPrestacaoTipoDebito, Date dataEmissao) {

		this.descricaoTipoDebito = descricaoTipoDebito;
		this.valorPrestacaoTipoDebito = valorPrestacaoTipoDebito;
		this.dataEmissao = dataEmissao;
	}

	public GuiaPagamentoPrestacaoHelper(Integer idGuiaPagamento, Short numeroPrestacao, String descricaoTipoDebito,
										BigDecimal valorPrestacaoTipoDebito, Date dataEmissao, String descricaoDebitoCreditoSituacao) {

		this.idGuiaPagamento = idGuiaPagamento;
		this.numeroPrestacao = numeroPrestacao;
		this.descricaoTipoDebito = descricaoTipoDebito;
		this.valorPrestacaoTipoDebito = valorPrestacaoTipoDebito;
		this.dataEmissao = dataEmissao;
		this.descricaoDebitoCreditoSituacao = descricaoDebitoCreditoSituacao;
	}

	/**
	 * @param valorTotalPorPrestacao
	 * @param descricaoDocumentoTipo
	 * @param numeroPrestacaoTotal
	 * @param dataEmissao
	 * @param dataVencimento
	 * @param indicadorPagamento
	 * @param idGuiaPagamento
	 * @param numeroPrestacao
	 * @param descricaoDebitoCreditoSituacao
	 */
	public GuiaPagamentoPrestacaoHelper(String descricaoDocumentoTipo, Integer idGuiaPagamento, Short numeroPrestacao,
										Short numeroPrestacaoTotal, BigDecimal valorTotalPorPrestacao, Date dataEmissao,
										Date dataVencimento, Short indicadorPagamento, String descricaoDebitoCreditoSituacao) {

		this.valorTotalPorPrestacao = valorTotalPorPrestacao;
		this.descricaoDocumentoTipo = descricaoDocumentoTipo;
		this.numeroPrestacaoTotal = numeroPrestacaoTotal;
		this.dataEmissao = dataEmissao;
		this.dataVencimento = dataVencimento;
		this.indicadorPagamento = indicadorPagamento;
		this.id = Long.valueOf(idGuiaPagamento.toString() + numeroPrestacao.toString());
		this.idGuiaPagamento = idGuiaPagamento;
		this.numeroPrestacao = numeroPrestacao;
		this.descricaoDebitoCreditoSituacao = descricaoDebitoCreditoSituacao;
		this.dsIndicadorPagamento = (indicadorPagamento == 1) ? "S" : "";
	}

	/**
	 * @param valorTotalPorPrestacao
	 * @param descricaoDocumentoTipo
	 * @param numeroPrestacaoTotal
	 * @param dataEmissao
	 * @param dataVencimento
	 * @param indicadorPagamento
	 * @param idGuiaPagamento
	 * @param numeroPrestacao
	 * @param descricaoDebitoCreditoSituacao
	 */
	public GuiaPagamentoPrestacaoHelper(String descricaoDocumentoTipo, Integer idGuiaPagamento, Short numeroPrestacao,
										Short numeroPrestacaoTotal, BigDecimal valorTotalPorPrestacao, Date dataEmissao,
										Date dataVencimento, Short indicadorPagamento, Integer idImovel,
										String descricaoDebitoCreditoSituacao) {

		this.valorTotalPorPrestacao = valorTotalPorPrestacao;
		this.descricaoDocumentoTipo = descricaoDocumentoTipo;
		this.numeroPrestacaoTotal = numeroPrestacaoTotal;
		this.dataEmissao = dataEmissao;
		this.dataVencimento = dataVencimento;
		this.indicadorPagamento = indicadorPagamento;
		this.id = Long.valueOf(idGuiaPagamento.toString() + numeroPrestacao.toString());
		this.idGuiaPagamento = idGuiaPagamento;
		this.numeroPrestacao = numeroPrestacao;
		this.descricaoDebitoCreditoSituacao = descricaoDebitoCreditoSituacao;
		this.dsIndicadorPagamento = (indicadorPagamento == 1) ? "S" : "";
		this.idImovel = idImovel;
	}

	/**
	 * @param valorTotalPorPrestacao
	 * @param descricaoDocumentoTipo
	 * @param numeroPrestacaoTotal
	 * @param dataEmissao
	 * @param dataVencimento
	 * @param indicadorPagamento
	 * @param idGuiaPagamento
	 * @param numeroPrestacao
	 * @param descricaoDebitoCreditoSituacao
	 */
	public GuiaPagamentoPrestacaoHelper(String descricaoDocumentoTipo, Integer idGuiaPagamento, Short numeroPrestacao,
										Short numeroPrestacaoTotal, BigDecimal valorTotalPorPrestacao, Date dataEmissao,
										Date dataVencimento, Short indicadorPagamento, Integer idDebitoTipo, String descricaoDebitoTipo,
										String descricaoDebitoCreditoSituacao) {

		this.valorTotalPorPrestacao = valorTotalPorPrestacao;
		this.descricaoDocumentoTipo = descricaoDocumentoTipo;
		this.numeroPrestacaoTotal = numeroPrestacaoTotal;
		this.dataEmissao = dataEmissao;
		this.dataVencimento = dataVencimento;
		this.indicadorPagamento = indicadorPagamento;
		this.id = Long.valueOf(idGuiaPagamento.toString() + numeroPrestacao.toString());
		this.idGuiaPagamento = idGuiaPagamento;
		this.numeroPrestacao = numeroPrestacao;
		this.descricaoDebitoCreditoSituacao = descricaoDebitoCreditoSituacao;
		this.dsIndicadorPagamento = (indicadorPagamento == 1) ? "S" : "";
		this.descricaoTipoDebito = descricaoDebitoTipo;
	}
	
	public GuiaPagamentoPrestacaoHelper(String descricaoDocumentoTipo, Integer idGuiaPagamento, Short numeroPrestacao,
			Short numeroPrestacaoTotal, BigDecimal valorTotalPorPrestacao, Date dataEmissao,
			Date dataVencimento, Short indicadorPagamento, Integer idDebitoTipo, String descricaoDebitoTipo,
			String descricaoDebitoCreditoSituacao, Short indicadorExecucaoFiscal, Short indicadorDividaAtiva) {

		this.valorTotalPorPrestacao = valorTotalPorPrestacao;
		this.descricaoDocumentoTipo = descricaoDocumentoTipo;
		this.numeroPrestacaoTotal = numeroPrestacaoTotal;
		this.dataEmissao = dataEmissao;
		this.dataVencimento = dataVencimento;
		this.indicadorPagamento = indicadorPagamento;
		this.id = Long.valueOf(idGuiaPagamento.toString() + numeroPrestacao.toString());
		this.idGuiaPagamento = idGuiaPagamento;
		this.numeroPrestacao = numeroPrestacao;
		this.descricaoDebitoCreditoSituacao = descricaoDebitoCreditoSituacao;
		this.dsIndicadorPagamento = (indicadorPagamento == 1) ? "S" : "";
		this.descricaoTipoDebito = descricaoDebitoTipo;
		this.indicadorExecucaoFiscal = indicadorExecucaoFiscal;
		this.indicadorDividaAtiva = indicadorDividaAtiva;
	}

	public GuiaPagamentoPrestacaoHelper(String descricaoDocumentoTipo, Integer idGuiaPagamento, Short numeroPrestacao,
										Short numeroPrestacaoTotal, BigDecimal valorTotalPorPrestacao, Date dataEmissao,
										Date dataVencimento, Short indicadorPagamento, Integer idDebitoTipo, String descricaoDebitoTipo,
										String descricaoDebitoCreditoSituacao, Short indicadorExecucaoFiscal, Short indicadorDividaAtiva,
										Integer numeroProcessoAdministrativoExecucaoFiscal) {

		this.valorTotalPorPrestacao = valorTotalPorPrestacao;
		this.descricaoDocumentoTipo = descricaoDocumentoTipo;
		this.numeroPrestacaoTotal = numeroPrestacaoTotal;
		this.dataEmissao = dataEmissao;
		this.dataVencimento = dataVencimento;
		this.indicadorPagamento = indicadorPagamento;
		this.id = Long.valueOf(idGuiaPagamento.toString() + numeroPrestacao.toString());
		this.idGuiaPagamento = idGuiaPagamento;
		this.numeroPrestacao = numeroPrestacao;
		this.descricaoDebitoCreditoSituacao = descricaoDebitoCreditoSituacao;
		this.dsIndicadorPagamento = (indicadorPagamento == 1) ? "S" : "";
		this.descricaoTipoDebito = descricaoDebitoTipo;
		this.indicadorExecucaoFiscal = indicadorExecucaoFiscal;
		this.indicadorDividaAtiva = indicadorDividaAtiva;
		this.numeroProcessoAdministrativoExecucaoFiscal = numeroProcessoAdministrativoExecucaoFiscal;
	}

	/**
	 * Default Constructor
	 */
	public GuiaPagamentoPrestacaoHelper() {

	}

	/**
	 * @param valorTotalPorPrestacao
	 * @param descricaoDocumentoTipo
	 * @param numeroPrestacaoTotal
	 * @param dataEmissao
	 * @param dataVencimento
	 * @param indicadorPagamento
	 * @param idGuiaPagamento
	 * @param numeroPrestacao
	 * @param descricaoDebitoCreditoSituacao
	 */
	public GuiaPagamentoPrestacaoHelper(String descricaoDocumentoTipo, Integer idGuiaPagamento, Short numeroPrestacao,
										Short numeroPrestacaoTotal, BigDecimal valorTotalPorPrestacao, Date dataEmissao,
										Date dataVencimento, Short indicadorPagamento, Integer idImovel,
										String descricaoDebitoCreditoSituacao, Short indicadorCobrancaAdministrativa) {

		this.valorTotalPorPrestacao = valorTotalPorPrestacao;
		this.descricaoDocumentoTipo = descricaoDocumentoTipo;
		this.numeroPrestacaoTotal = numeroPrestacaoTotal;
		this.dataEmissao = dataEmissao;
		this.dataVencimento = dataVencimento;
		this.indicadorPagamento = indicadorPagamento;
		this.id = Long.valueOf(idGuiaPagamento.toString() + numeroPrestacao.toString());
		this.idGuiaPagamento = idGuiaPagamento;
		this.numeroPrestacao = numeroPrestacao;
		this.descricaoDebitoCreditoSituacao = descricaoDebitoCreditoSituacao;
		this.dsIndicadorPagamento = (indicadorPagamento == 1) ? "S" : "";
		this.idImovel = idImovel;
		this.indicadorCobrancaAdministrativa = indicadorCobrancaAdministrativa;
	}

	/**
	 * @param valorTotalPorPrestacao
	 * @param descricaoDocumentoTipo
	 * @param numeroPrestacaoTotal
	 * @param dataEmissao
	 * @param dataVencimento
	 * @param indicadorPagamento
	 * @param idGuiaPagamento
	 * @param numeroPrestacao
	 * @param descricaoDebitoCreditoSituacao
	 */
	public GuiaPagamentoPrestacaoHelper(Short numeroPrestacao, Date dataVencimento, BigDecimal valorTotalPorPrestacao,
										String descricaoDocumentoTipo, Integer idGuiaPagamento, Short numeroPrestacaoTotal,
										Date dataEmissao, Short indicadorPagamento, Integer idImovel,
										String descricaoDebitoCreditoSituacao, Integer numeroContratoParcelOrgaoPublico,
										Short indicadorCobrancaAdministrativa) {

		this.valorTotalPorPrestacao = valorTotalPorPrestacao;
		this.descricaoDocumentoTipo = descricaoDocumentoTipo;
		this.numeroPrestacaoTotal = numeroPrestacaoTotal;
		this.dataEmissao = dataEmissao;
		this.dataVencimento = dataVencimento;
		this.indicadorPagamento = indicadorPagamento;
		this.id = Long.valueOf(idGuiaPagamento.toString() + numeroPrestacao.toString());
		this.idGuiaPagamento = idGuiaPagamento;
		this.numeroPrestacao = numeroPrestacao;
		this.descricaoDebitoCreditoSituacao = descricaoDebitoCreditoSituacao;
		this.dsIndicadorPagamento = (indicadorPagamento == 1) ? "S" : "";
		this.idImovel = idImovel;
		this.numeroContratoParcelOrgaoPublico = numeroContratoParcelOrgaoPublico;
		this.indicadorCobrancaAdministrativa = indicadorCobrancaAdministrativa;
	}

	/**
	 * @param valorTotalPorPrestacao
	 * @param descricaoDocumentoTipo
	 * @param numeroPrestacaoTotal
	 * @param dataEmissao
	 * @param dataVencimento
	 * @param indicadorPagamento
	 * @param idGuiaPagamento
	 * @param numeroPrestacao
	 * @param descricaoDebitoCreditoSituacao
	 */
	public GuiaPagamentoPrestacaoHelper(Short numeroPrestacao, Date dataVencimento, BigDecimal valorTotalPorPrestacao,
										String descricaoDocumentoTipo, Integer idGuiaPagamento, Short numeroPrestacaoTotal,
										Date dataEmissao, Short indicadorPagamento, Integer idImovel,
										String descricaoDebitoCreditoSituacao, Integer numeroContratoParcelOrgaoPublico,
										Short indicadorCobrancaAdministrativa, Short indicadorExecucaoFiscal) {

		this.valorTotalPorPrestacao = valorTotalPorPrestacao;
		this.descricaoDocumentoTipo = descricaoDocumentoTipo;
		this.numeroPrestacaoTotal = numeroPrestacaoTotal;
		this.dataEmissao = dataEmissao;
		this.dataVencimento = dataVencimento;
		this.indicadorPagamento = indicadorPagamento;
		this.id = Long.valueOf(idGuiaPagamento.toString() + numeroPrestacao.toString());
		this.idGuiaPagamento = idGuiaPagamento;
		this.numeroPrestacao = numeroPrestacao;
		this.descricaoDebitoCreditoSituacao = descricaoDebitoCreditoSituacao;
		this.dsIndicadorPagamento = (indicadorPagamento == 1) ? "S" : "";
		this.idImovel = idImovel;
		this.numeroContratoParcelOrgaoPublico = numeroContratoParcelOrgaoPublico;
		this.indicadorCobrancaAdministrativa = indicadorCobrancaAdministrativa;
		this.indicadorExecucaoFiscal = indicadorExecucaoFiscal;
	}

	public GuiaPagamentoPrestacaoHelper(Short numeroPrestacao, Date dataVencimento, BigDecimal valorTotalPorPrestacao,
										String descricaoDocumentoTipo, Integer idGuiaPagamento, Short numeroPrestacaoTotal,
										Date dataEmissao, Short indicadorPagamento, Integer idImovel,
										String descricaoDebitoCreditoSituacao, Integer numeroContratoParcelOrgaoPublico,
										Short indicadorCobrancaAdministrativa, Short indicadorExecucaoFiscal, Short indicadorDividaAtiva) {

		this.valorTotalPorPrestacao = valorTotalPorPrestacao;
		this.descricaoDocumentoTipo = descricaoDocumentoTipo;
		this.numeroPrestacaoTotal = numeroPrestacaoTotal;
		this.dataEmissao = dataEmissao;
		this.dataVencimento = dataVencimento;
		this.indicadorPagamento = indicadorPagamento;
		this.id = Long.valueOf(idGuiaPagamento.toString() + numeroPrestacao.toString());
		this.idGuiaPagamento = idGuiaPagamento;
		this.numeroPrestacao = numeroPrestacao;
		this.descricaoDebitoCreditoSituacao = descricaoDebitoCreditoSituacao;
		this.dsIndicadorPagamento = (indicadorPagamento == 1) ? "S" : "";
		this.idImovel = idImovel;
		this.numeroContratoParcelOrgaoPublico = numeroContratoParcelOrgaoPublico;
		this.indicadorCobrancaAdministrativa = indicadorCobrancaAdministrativa;
		this.indicadorExecucaoFiscal = indicadorExecucaoFiscal;
		this.indicadorDividaAtiva = indicadorDividaAtiva;
	}

	public GuiaPagamentoPrestacaoHelper(Short numeroPrestacao, Date dataVencimento, BigDecimal valorTotalPorPrestacao,
										String descricaoDocumentoTipo, Integer idGuiaPagamento, Short numeroPrestacaoTotal,
										Date dataEmissao, Short indicadorPagamento, Integer idImovel,
										String descricaoDebitoCreditoSituacao, Integer numeroContratoParcelOrgaoPublico,
										Short indicadorCobrancaAdministrativa, Short indicadorExecucaoFiscal, Short indicadorDividaAtiva,
										Integer numeroProcessoAdministrativoExecucaoFiscal) {

		this.valorTotalPorPrestacao = valorTotalPorPrestacao;
		this.descricaoDocumentoTipo = descricaoDocumentoTipo;
		this.numeroPrestacaoTotal = numeroPrestacaoTotal;
		this.dataEmissao = dataEmissao;
		this.dataVencimento = dataVencimento;
		this.indicadorPagamento = indicadorPagamento;
		this.id = Long.valueOf(idGuiaPagamento.toString() + numeroPrestacao.toString());
		this.idGuiaPagamento = idGuiaPagamento;
		this.numeroPrestacao = numeroPrestacao;
		this.descricaoDebitoCreditoSituacao = descricaoDebitoCreditoSituacao;
		this.dsIndicadorPagamento = (indicadorPagamento == 1) ? "S" : "";
		this.idImovel = idImovel;
		this.numeroContratoParcelOrgaoPublico = numeroContratoParcelOrgaoPublico;
		this.indicadorCobrancaAdministrativa = indicadorCobrancaAdministrativa;
		this.indicadorExecucaoFiscal = indicadorExecucaoFiscal;
		this.indicadorDividaAtiva = indicadorDividaAtiva;
		this.numeroProcessoAdministrativoExecucaoFiscal = numeroProcessoAdministrativoExecucaoFiscal;
	}

	public GuiaPagamentoPrestacaoHelper(Short numeroPrestacao, Date dataVencimento, BigDecimal valorTotalPorPrestacao) {

		this.valorTotalPorPrestacao = valorTotalPorPrestacao;
		this.dataVencimento = dataVencimento;
		this.numeroPrestacao = numeroPrestacao;

	}

	public GuiaPagamentoPrestacaoHelper(Integer idGuiaPagamento, Short numeroPrestacao, Date dataVencimento,
										BigDecimal valorTotalPorPrestacao) {

		this.idGuiaPagamento = idGuiaPagamento;
		this.valorTotalPorPrestacao = valorTotalPorPrestacao;
		this.dataVencimento = dataVencimento;
		this.numeroPrestacao = numeroPrestacao;

	}

	/**
	 * @return the id
	 */
	public Long getId(){

		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id){

		this.id = id;
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
	 * @return the idItemLancamentoContabil
	 */
	public Integer getIdItemLancamentoContabil(){

		return idLancamentoItemContabil;
	}

	/**
	 * @param idItemLancamentoContabil
	 *            the idItemLancamentoContabil to set
	 */
	public void setIdItemLancamentoContabil(Integer idItemLancamentoContabil){

		this.idLancamentoItemContabil = idItemLancamentoContabil;
	}

	/**
	 * @return the descricaoDocumentoTipo
	 */
	public String getDescricaoDocumentoTipo(){

		return descricaoDocumentoTipo;
	}

	/**
	 * @param descricaoDocumentoTipo
	 *            the descricaoDocumentoTipo to set
	 */
	public void setDescricaoDocumentoTipo(String descricaoDocumentoTipo){

		this.descricaoDocumentoTipo = descricaoDocumentoTipo;
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
	 * @return the numeroPrestacaoTotal
	 */
	public Short getNumeroPrestacaoTotal(){

		return numeroPrestacaoTotal;
	}

	/**
	 * @param numeroPrestacaoTotal
	 *            the numeroPrestacaoTotal to set
	 */
	public void setNumeroPrestacaoTotal(Short numeroPrestacaoTotal){

		this.numeroPrestacaoTotal = numeroPrestacaoTotal;
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
	 * @return the indicadorPagamento
	 */
	public Short getIndicadorPagamento(){

		return indicadorPagamento;
	}

	/**
	 * @param indicadorPagamento
	 *            the indicadorPagamento to set
	 */
	public void setIndicadorPagamento(Short indicadorPagamento){

		this.indicadorPagamento = indicadorPagamento;
	}

	/**
	 * @return the idGuiaPagamento
	 */
	public Integer getIdGuiaPagamento(){

		return idGuiaPagamento;
	}

	/**
	 * @param idGuiaPagamento
	 *            the idGuiaPagamento to set
	 */
	public void setIdGuiaPagamento(Integer idGuiaPagamento){

		this.idGuiaPagamento = idGuiaPagamento;
	}

	/**
	 * @return the idTipoDebito
	 */
	public Integer getIdTipoDebito(){

		return idTipoDebito;
	}

	/**
	 * @param idTipoDebito
	 *            the idTipoDebito to set
	 */
	public void setIdTipoDebito(Integer idTipoDebito){

		this.idTipoDebito = idTipoDebito;
	}

	/**
	 * @return the idLancamentoItemContabil
	 */
	public Integer getIdLancamentoItemContabil(){

		return idLancamentoItemContabil;
	}

	/**
	 * @param idLancamentoItemContabil
	 *            the idLancamentoItemContabil to set
	 */
	public void setIdLancamentoItemContabil(Integer idLancamentoItemContabil){

		this.idLancamentoItemContabil = idLancamentoItemContabil;
	}

	/**
	 * @return the descricaoDebitoCreditoSituacao
	 */
	public String getDescricaoDebitoCreditoSituacao(){

		return descricaoDebitoCreditoSituacao;
	}

	/**
	 * @param descricaoDebitoCreditoSituacao
	 *            the descricaoDebitoCreditoSituacao to set
	 */
	public void setDescricaoDebitoCreditoSituacao(String descricaoDebitoCreditoSituacao){

		this.descricaoDebitoCreditoSituacao = descricaoDebitoCreditoSituacao;
	}

	/**
	 * @return the valorTotalPorPrestacao
	 */
	public BigDecimal getValorTotalPorPrestacao(){

		return valorTotalPorPrestacao;
	}

	/**
	 * @param valorTotalPorPrestacao
	 *            the valorTotalPorPrestacao to set
	 */
	public void setValorTotalPorPrestacao(BigDecimal valorTotalPorPrestacao){

		this.valorTotalPorPrestacao = valorTotalPorPrestacao;
	}

	@Override
	public int hashCode(){

		final int prime = 31;
		int result = 1;
		result = prime * result + ((idGuiaPagamento == null) ? 0 : idGuiaPagamento.hashCode());
		result = prime * result + ((idLancamentoItemContabil == null) ? 0 : idLancamentoItemContabil.hashCode());
		result = prime * result + ((idTipoDebito == null) ? 0 : idTipoDebito.hashCode());
		result = prime * result + ((numeroPrestacao == null) ? 0 : numeroPrestacao.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj){

		if(this == obj) return true;
		if(obj == null) return false;
		if(!(obj instanceof GuiaPagamentoPrestacaoHelper)) return false;
		final GuiaPagamentoPrestacaoHelper other = (GuiaPagamentoPrestacaoHelper) obj;
		if(idGuiaPagamento == null){
			if(other.idGuiaPagamento != null) return false;
		}else if(!idGuiaPagamento.equals(other.idGuiaPagamento)) return false;
		if(idLancamentoItemContabil == null){
			if(other.idLancamentoItemContabil != null) return false;
		}else if(!idLancamentoItemContabil.equals(other.idLancamentoItemContabil)) return false;
		if(idTipoDebito == null){
			if(other.idTipoDebito != null) return false;
		}else if(!idTipoDebito.equals(other.idTipoDebito)) return false;
		if(numeroPrestacao == null){
			if(other.numeroPrestacao != null) return false;
		}else if(!numeroPrestacao.equals(other.numeroPrestacao)) return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){

		return new ToStringBuilder(this).append("idGuiaPagamento", getIdGuiaPagamento()).append("idLancamentoItemContabil",
						getIdItemLancamentoContabil()).append("idTipoDebito", getIdTipoDebito()).append("numeroPrestacao",
						getNumeroPrestacao()).toString();
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

	public Integer getIdCliente(){

		return idCliente;
	}

	public void setIdCliente(Integer idCliente){

		this.idCliente = idCliente;
	}

	public Date getDataPagamento(){

		return dataPagamento;
	}

	public void setDataPagamento(Date dataPagamento){

		this.dataPagamento = dataPagamento;
	}

	public BigDecimal getValorPagamento(){

		return valorPagamento;
	}

	public void setValorPagamento(BigDecimal valorPagamento){

		this.valorPagamento = valorPagamento;
	}

	public String getDsIndicadorPagamento(){

		return dsIndicadorPagamento;
	}

	public void setDsIndicadorPagamento(String dsIndicadorPagamento){

		this.dsIndicadorPagamento = dsIndicadorPagamento;
	}

	public String getDebitoTipoHint(){

		return debitoTipoHint;
	}

	public void setDebitoTipoHint(String debitoTipoHint){

		this.debitoTipoHint = debitoTipoHint;
	}

	public Short getIdOcorrenciaHistorico(){

		return idOcorrenciaHistorico;
	}

	public void setIdOcorrenciaHistorico(Short idOcorrenciaHistorico){

		this.idOcorrenciaHistorico = idOcorrenciaHistorico;
	}

	public String getIndicadorPagamentoHint(){

		return indicadorPagamentoHint;
	}

	public void setIndicadorPagamentoHint(String indicadorPagamentoHint){

		this.indicadorPagamentoHint = indicadorPagamentoHint;
	}

	public Integer getIdImovel(){

		return idImovel;
	}

	public void setIdImovel(Integer idImovel){

		this.idImovel = idImovel;
	}

	public String getNomeCliente(){

		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente){

		this.nomeCliente = nomeCliente;
	}

	public String getIdGuiaComNumeroPrestacao(){

		return this.getIdGuiaPagamento().toString() + "," + this.getNumeroPrestacao().toString();
	}

	public Short getIndicadorCobrancaAdministrativa(){

		return indicadorCobrancaAdministrativa;
	}

	public void setIndicadorCobrancaAdministrativa(Short indicadorCobrancaAdministrativa){

		this.indicadorCobrancaAdministrativa = indicadorCobrancaAdministrativa;
	}

	public String getValorPagamentoStr(){

		return valorPagamentoStr;
	}

	public void setValorPagamentoStr(String valorPagamentoStr){

		this.valorPagamentoStr = valorPagamentoStr;
	}

	public Integer getNumeroContratoParcelOrgaoPublico(){

		return numeroContratoParcelOrgaoPublico;
	}

	public void setNumeroContratoParcelOrgaoPublico(Integer numeroContratoParcelOrgaoPublico){

		this.numeroContratoParcelOrgaoPublico = numeroContratoParcelOrgaoPublico;
	}

	public String getIdDebitoCreditoSituacao(){

		return idDebitoCreditoSituacao;
	}

	public void setIdDebitoCreditoSituacao(String idDebitoCreditoSituacao){

		this.idDebitoCreditoSituacao = idDebitoCreditoSituacao;
	}

}
