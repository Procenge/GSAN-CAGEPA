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

package gcom.cobranca;

import gcom.faturamento.GuiaPagamentoGeral;
import gcom.faturamento.conta.ContaGeral;
import gcom.faturamento.debito.DebitoCreditoSituacao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Carlos Chrystian */
public class QuitacaoDebitoAnualItem
				implements Serializable {

	private static final long serialVersionUID = 1L;

	/** identifier field */
	private Integer id;

	/** persistent field */
	private Integer anoReferenciaItem;

	/** persistent field */
	private BigDecimal valorItem;

	/** persistent field */
	private Date dataPagamento;

	/** persistent field */
	private Date ultimaAlteracao;

	/** persistent field */
	private QuitacaoDebitoAnual quitacaoDebitoAnual;

	/** persistent field */
	private DocumentoTipo documentoTipo;

	/** persistent field */
	private DebitoCreditoSituacao debitoCreditoSituacao;

	/** persistent field */
	private ContaGeral contaGeral;

	private GuiaPagamentoGeral guiaPagamentoGeral;

	private Short numeroPrestacao;

	/** default constructor */
	public QuitacaoDebitoAnualItem() {

	}

	public QuitacaoDebitoAnualItem(Integer id) {

		this.id = id;
	}

	public String toString(){

		return new ToStringBuilder(this).append("cdstId", getId()).toString();
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
	 * @return the anoReferenciaItem
	 */
	public Integer getAnoReferenciaItem(){

		return anoReferenciaItem;
	}

	/**
	 * @param anoReferencia
	 *            the anoReferenciaItem to set
	 */
	public void setAnoReferenciaItem(Integer anoReferenciaItem){

		this.anoReferenciaItem = anoReferenciaItem;
	}

	/**
	 * @return the valorItem
	 */
	public BigDecimal getValorItem(){

		return valorItem;
	}

	/**
	 * @param id
	 *            the valorItem to set
	 */
	public void setValorItem(BigDecimal valorItem){

		this.valorItem = valorItem;
	}

	/**
	 * @return the dataPagamento
	 */
	public Date getDataPagamento(){

		return dataPagamento;
	}

	/**
	 * @param id
	 *            the dataPagamento to set
	 */
	public void setDataPagamento(Date dataPagamento){

		this.dataPagamento = dataPagamento;
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
	 * @return the quitacaoDebitoAnual
	 */
	public QuitacaoDebitoAnual getQuitacaoDebitoAnual(){

		return quitacaoDebitoAnual;
	}

	/**
	 * @param quitacaoDebitoAnual
	 *            the quitacaoDebitoAnual to set
	 */
	public void setQuitacaoDebitoAnual(QuitacaoDebitoAnual quitacaoDebitoAnual){

		this.quitacaoDebitoAnual = quitacaoDebitoAnual;
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
	 * @return the contaGeral
	 */
	public ContaGeral getContaGeral(){

		return contaGeral;
	}

	/**
	 * @param contaGeral
	 *            the contaGeral to set
	 */
	public void setContaGeral(ContaGeral contaGeral){

		this.contaGeral = contaGeral;
	}

	public GuiaPagamentoGeral getGuiaPagamentoGeral(){

		return guiaPagamentoGeral;
	}

	public void setGuiaPagamentoGeral(GuiaPagamentoGeral guiaPagamentoGeral){

		this.guiaPagamentoGeral = guiaPagamentoGeral;
	}

	public Short getNumeroPrestacao(){

		return numeroPrestacao;
	}

	public void setNumeroPrestacao(Short numeroPrestacao){

		this.numeroPrestacao = numeroPrestacao;
	}

}
