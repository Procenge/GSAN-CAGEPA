/*
 * Copyright (C) 2007-2007 the GSAN � Sistema Integrado de Gest�o de Servi�os de Saneamento
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
 * Foundation, Inc., 59 Temple Place � Suite 330, Boston, MA 02111-1307, USA
 */

/*
 * GSAN � Sistema Integrado de Gest�o de Servi�os de Saneamento
 * Copyright (C) <2007> 
 * Adriano Britto Siqueira
 * Alexandre Santos Cabral
 * Ana Carolina Alves Breda
 * Ana Maria Andrade Cavalcante
 * Aryed Lins de Ara�jo
 * Bruno Leonardo Rodrigues Barros
 * Carlos Elmano Rodrigues Ferreira
 * Cl�udio de Andrade Lira
 * Denys Guimar�es Guenes Tavares
 * Eduardo Breckenfeld da Rosa Borges
 * Fab�ola Gomes de Ara�jo
 * Fl�vio Leonardo Cavalcanti Cordeiro
 * Francisco do Nascimento J�nior
 * Homero Sampaio Cavalcanti
 * Ivan S�rgio da Silva J�nior
 * Jos� Edmar de Siqueira
 * Jos� Thiago Ten�rio Lopes
 * K�ssia Regina Silvestre de Albuquerque
 * Leonardo Luiz Vieira da Silva
 * M�rcio Roberto Batista da Silva
 * Maria de F�tima Sampaio Leite
 * Micaela Maria Coelho de Ara�jo
 * Nelson Mendon�a de Carvalho
 * Newton Morais e Silva
 * Pedro Alexandre Santos da Silva Filho
 * Rafael Corr�a Lima e Silva
 * Rafael Francisco Pinto
 * Rafael Koury Monteiro
 * Rafael Palermo de Ara�jo
 * Raphael Veras Rossiter
 * Roberto Sobreira Barbalho
 * Rodrigo Avellar Silveira
 * Rosana Carvalho Barbosa
 * S�vio Luiz de Andrade Cavalcante
 * Tai Mu Shih
 * Thiago Augusto Souza do Nascimento
 * Tiago Moreno Rodrigues
 * Vivianne Barbosa Sousa
 *
 * Este programa � software livre; voc� pode redistribu�-lo e/ou
 * modific�-lo sob os termos de Licen�a P�blica Geral GNU, conforme
 * publicada pela Free Software Foundation; vers�o 2 da
 * Licen�a.
 * Este programa � distribu�do na expectativa de ser �til, mas SEM
 * QUALQUER GARANTIA; sem mesmo a garantia impl�cita de
 * COMERCIALIZA��O ou de ADEQUA��O A QUALQUER PROP�SITO EM
 * PARTICULAR. Consulte a Licen�a P�blica Geral GNU para obter mais
 * detalhes.
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU
 * junto com este programa; se n�o, escreva para Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 * 02111-1307, USA.
 */

package gcom.cobranca;

import gcom.faturamento.GuiaPagamentoGeral;
import gcom.faturamento.conta.ContaGeral;
import gcom.faturamento.credito.CreditoARealizarGeral;
import gcom.faturamento.debito.DebitoACobrarGeral;
import gcom.util.ConstantesSistema;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class CobrancaDocumentoItem
				implements Serializable, Comparable {

	private static final long serialVersionUID = 1L;

	/** identifier field */
	private Integer id;

	/** nullable persistent field */
	private BigDecimal valorItemCobrado;

	/** nullable persistent field */
	private Date ultimaAlteracao;

	/** persistent field */
	private DebitoACobrarGeral debitoACobrarGeral;

	/** persistent field */
	private gcom.cobranca.CobrancaDocumento cobrancaDocumento;

	/** persistent field */
	private gcom.cobranca.DocumentoTipo documentoTipo;

	/** persistent field */
	private ContaGeral contaGeral;

	/** persistent field */
	private GuiaPagamentoGeral guiaPagamentoGeral;

	/** nullable persistent field */
	private Date dataSituacaoDebito;

	private BigDecimal valorAcrescimos;

	/** persistent field */
	private CobrancaDebitoSituacao cobrancaDebitoSituacao;

	/** persistent field */
	private CreditoARealizarGeral creditoARealizarGeral;

	/** persistent field */
	private Short numeroDaPrestacao;

	/** persistent field */
	private BigDecimal valorDescontoMulta = BigDecimal.ZERO;

	/** persistent field */
	private BigDecimal valorDescontoValor = BigDecimal.ZERO;

	/** persistent field */
	private BigDecimal valorDescontoMora = BigDecimal.ZERO;

	/** persistent field */
	private BigDecimal valorDescontoCorrecaoMonetaria = BigDecimal.ZERO;

	/** persistent field */
	private BigDecimal valorDescontoDocumento = BigDecimal.ZERO;

	private BigDecimal valorMulta = BigDecimal.ZERO;

	private BigDecimal valorMora = BigDecimal.ZERO;

	private BigDecimal valorCorrecaoMonetaria = BigDecimal.ZERO;

	private java.lang.Integer numeroParcelaAntecipada;

	private java.math.BigDecimal valorJurosParcelaAntecipada;

	private Short indicadorAtualizado = ConstantesSistema.NAO;

	/** full constructor */
	public CobrancaDocumentoItem(BigDecimal valorItemCobrado, Date ultimaAlteracao, DebitoACobrarGeral debitoACobrarGeral,
									gcom.cobranca.CobrancaDocumento cobrancaDocumento, gcom.cobranca.DocumentoTipo documentoTipo,
									ContaGeral contaGeral, GuiaPagamentoGeral guiaPagamentoGeral) {

		this.valorItemCobrado = valorItemCobrado;
		this.ultimaAlteracao = ultimaAlteracao;
		this.debitoACobrarGeral = debitoACobrarGeral;
		this.cobrancaDocumento = cobrancaDocumento;
		this.documentoTipo = documentoTipo;
		this.contaGeral = contaGeral;
		this.guiaPagamentoGeral = guiaPagamentoGeral;
		this.indicadorAtualizado = ConstantesSistema.NAO;
	}

	/** default constructor */
	public CobrancaDocumentoItem() {

		this.indicadorAtualizado = ConstantesSistema.NAO;
	}

	/** minimal constructor */
	public CobrancaDocumentoItem(DebitoACobrarGeral debitoACobrarGeral, gcom.cobranca.CobrancaDocumento cobrancaDocumento,
									gcom.cobranca.DocumentoTipo documentoTipo, ContaGeral contaGeral, GuiaPagamentoGeral guiaPagamentoGeral) {

		this.debitoACobrarGeral = debitoACobrarGeral;
		this.cobrancaDocumento = cobrancaDocumento;
		this.documentoTipo = documentoTipo;
		this.contaGeral = contaGeral;
		this.guiaPagamentoGeral = guiaPagamentoGeral;
		this.indicadorAtualizado = ConstantesSistema.NAO;
	}

	public java.lang.Integer getNumeroParcelaAntecipada(){

		return numeroParcelaAntecipada;
	}

	public void setNumeroParcelaAntecipada(java.lang.Integer numeroParcelaAntecipada){

		this.numeroParcelaAntecipada = numeroParcelaAntecipada;
	}

	public java.math.BigDecimal getValorJurosParcelaAntecipada(){

		return valorJurosParcelaAntecipada;
	}

	public void setValorJurosParcelaAntecipada(java.math.BigDecimal valorJurosParcelaAntecipada){

		this.valorJurosParcelaAntecipada = valorJurosParcelaAntecipada;
	}

	public BigDecimal getValorDescontoMulta(){

		return valorDescontoMulta;
	}

	public void setValorDescontoMulta(BigDecimal valorDescontoMulta){

		this.valorDescontoMulta = valorDescontoMulta;
	}

	public BigDecimal getValorDescontoValor(){

		return valorDescontoValor;
	}

	public void setValorDescontoValor(BigDecimal valorDescontoValor){

		this.valorDescontoValor = valorDescontoValor;
	}

	public BigDecimal getValorDescontoMora(){

		return valorDescontoMora;
	}

	public void setValorDescontoMora(BigDecimal valorDescontoMora){

		this.valorDescontoMora = valorDescontoMora;
	}

	public BigDecimal getValorDescontoCorrecaoMonetaria(){

		return valorDescontoCorrecaoMonetaria;
	}

	public void setValorDescontoCorrecaoMonetaria(BigDecimal valorDescontoCorrecaoMonetaria){

		this.valorDescontoCorrecaoMonetaria = valorDescontoCorrecaoMonetaria;
	}

	public BigDecimal getValorDescontoDocumento(){

		return valorDescontoDocumento;
	}

	public void setValorDescontoDocumento(BigDecimal valorDescontoDocumento){

		this.valorDescontoDocumento = valorDescontoDocumento;
	}

	public Integer getId(){

		return this.id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public BigDecimal getValorItemCobrado(){

		return this.valorItemCobrado;
	}

	public void setValorItemCobrado(BigDecimal valorItemCobrado){

		this.valorItemCobrado = valorItemCobrado;
	}

	public Date getUltimaAlteracao(){

		return this.ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public gcom.cobranca.CobrancaDocumento getCobrancaDocumento(){

		return this.cobrancaDocumento;
	}

	public void setCobrancaDocumento(gcom.cobranca.CobrancaDocumento cobrancaDocumento){

		this.cobrancaDocumento = cobrancaDocumento;
	}

	public ContaGeral getContaGeral(){

		return contaGeral;
	}

	public void setContaGeral(ContaGeral contaGeral){

		this.contaGeral = contaGeral;
	}

	public DebitoACobrarGeral getDebitoACobrarGeral(){

		return debitoACobrarGeral;
	}

	public void setDebitoACobrarGeral(DebitoACobrarGeral debitoACobrarGeral){

		this.debitoACobrarGeral = debitoACobrarGeral;
	}

	public GuiaPagamentoGeral getGuiaPagamentoGeral(){

		return guiaPagamentoGeral;
	}

	public void setGuiaPagamentoGeral(GuiaPagamentoGeral guiaPagamentoGeral){

		this.guiaPagamentoGeral = guiaPagamentoGeral;
	}

	public gcom.cobranca.DocumentoTipo getDocumentoTipo(){

		return this.documentoTipo;
	}

	public void setDocumentoTipo(gcom.cobranca.DocumentoTipo documentoTipo){

		this.documentoTipo = documentoTipo;
	}

	public String toString(){

		return new ToStringBuilder(this).append("id", getId()).toString();
	}

	/**
	 * @return Retorna o campo cobrancaDebitoSituacao.
	 */
	public CobrancaDebitoSituacao getCobrancaDebitoSituacao(){

		return cobrancaDebitoSituacao;
	}

	/**
	 * @param cobrancaDebitoSituacao
	 *            O cobrancaDebitoSituacao a ser setado.
	 */
	public void setCobrancaDebitoSituacao(CobrancaDebitoSituacao cobrancaDebitoSituacao){

		this.cobrancaDebitoSituacao = cobrancaDebitoSituacao;
	}

	/**
	 * @return Retorna o campo dataSituacaoDebito.
	 */
	public Date getDataSituacaoDebito(){

		return dataSituacaoDebito;
	}

	/**
	 * @param dataSituacaoDebito
	 *            O dataSituacaoDebito a ser setado.
	 */
	public void setDataSituacaoDebito(Date dataSituacaoDebito){

		this.dataSituacaoDebito = dataSituacaoDebito;
	}

	public CreditoARealizarGeral getCreditoARealizarGeral(){

		return creditoARealizarGeral;
	}

	public void setCreditoARealizarGeral(CreditoARealizarGeral creditoARealizarGeral){

		this.creditoARealizarGeral = creditoARealizarGeral;
	}

	public BigDecimal getValorAcrescimos(){

		return valorAcrescimos;
	}

	public void setValorAcrescimos(BigDecimal valorAcrescimos){

		this.valorAcrescimos = valorAcrescimos;
	}

	public Short getNumeroDaPrestacao(){

		return numeroDaPrestacao;
	}

	public void setNumeroDaPrestacao(Short numeroDaPrestacao){

		this.numeroDaPrestacao = numeroDaPrestacao;
	}

	public BigDecimal getValorMulta(){

		return valorMulta;
	}

	public void setValorMulta(BigDecimal valorMulta){

		this.valorMulta = valorMulta;
	}

	public BigDecimal getValorMora(){

		return valorMora;
	}

	public void setValorMora(BigDecimal valorMora){

		this.valorMora = valorMora;
	}

	public BigDecimal getValorCorrecaoMonetaria(){

		return valorCorrecaoMonetaria;
	}

	public void setValorCorrecaoMonetaria(BigDecimal valorCorrecaoMonetaria){

		this.valorCorrecaoMonetaria = valorCorrecaoMonetaria;
	}

	public Short getIndicadorAtualizado(){

		return indicadorAtualizado;
	}

	public void setIndicadorAtualizado(Short indicadorAtualizado){

		this.indicadorAtualizado = indicadorAtualizado;
	}

	public int compareTo(Object other){

		if((this == other)){
			return 0;
		}
		if(!(other instanceof CobrancaDocumentoItem)){
			return -1;
		}
		CobrancaDocumentoItem castOther = (CobrancaDocumentoItem) other;

		return new CompareToBuilder().append(this.getId(), castOther.getId()).toComparison();
	}

}
