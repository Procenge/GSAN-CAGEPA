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
 * 
 * GSANPCG
 * Eduardo Henrique
 */
package gcom.integracao.piramide;

import gcom.integracao.piramide.bean.LancamentoDeferimentoAnteriorHelper;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author mgrb
 *         Representa a tabela: TI_DIF_ANT_REC_PER
 */
public class TabelaIntegracaoDeferimentoAnteriorReferenciaBase {

	private TabelaIntegracaoDeferimentoAnteriorReferenciaBasePK chavePrimaria;

	// SOMENTO PARA O SOMATORIO
	private BigDecimal valorTotalPagoAcumulado;

	// SOMENTE PARA USO NO SOMATORIO
	private BigDecimal valorTotalContaAcumulado;

	private BigDecimal valorRecebido;

	private BigDecimal valorTotalVendas;

	private String codigoOperacaoRegistro;

	private Integer numeroApuracaoPIS;

	private String codigoApuracaoPIS;

	private String codigoStatusRegistro;

	private String descricaoErroRegistro;

	private BigDecimal valorPIS;

	private Integer numeroApuracaoCOFINS;

	private String codigoApuracaoCOFINS;

	private BigDecimal valorCOFINS;

	private String codigoSituacaoTributoPIS;

	private String codigoSituacaoTributoCOFIN;

	private BigDecimal valorBasePIS;

	private BigDecimal percentualAliquotaPIS;

	private BigDecimal valorBaseCOFINS;

	private BigDecimal percentualAliquotaCOFINS;

	/**
	 * TabelaIntegracaoDeferimentoAnteriorReferenciaBase
	 * <p>
	 * Esse método Constroi uma instância de TabelaIntegracaoDeferimentoAnteriorReferenciaBase.
	 * </p>
	 * 
	 * @author Marlos Ribeiro
	 * @since 09/10/2012
	 */
	public TabelaIntegracaoDeferimentoAnteriorReferenciaBase() {

		super();
	}

	/**
	 * TabelaIntegracaoDeferimentoAnteriorReferenciaBase
	 * <p>
	 * Esse método Constroi uma instância de TabelaIntegracaoDeferimentoAnteriorReferenciaBase.
	 * </p>
	 * 
	 * @author Marlos Ribeiro
	 * @param codigoGerenciaRegional
	 * @param mesAnoReferencia
	 *            FORMATO MMAAAA
	 * @param codCliente
	 * @param dataRecebimento
	 * @param percentualAliquotaPIS
	 * @param percentualAliquotaCOFINS
	 * @since 08/10/2012
	 */
	public TabelaIntegracaoDeferimentoAnteriorReferenciaBase(String codigoGerenciaRegional, String mesAnoReferencia, String codCliente,
																Date dataRecebimento, BigDecimal percentualAliquotaPIS,
																BigDecimal percentualAliquotaCOFINS) {

		valorTotalPagoAcumulado = BigDecimal.ZERO;
		valorTotalContaAcumulado = BigDecimal.ZERO;
		chavePrimaria = new TabelaIntegracaoDeferimentoAnteriorReferenciaBasePK(codigoGerenciaRegional, "GSA", mesAnoReferencia,
						codCliente, dataRecebimento);
		valorRecebido = BigDecimal.ZERO;
		valorTotalVendas = BigDecimal.ZERO;
		codigoOperacaoRegistro = "I";
		numeroApuracaoPIS = null;
		codigoApuracaoPIS = "01";
		codigoStatusRegistro = "NP";
		descricaoErroRegistro = null;
		valorPIS = BigDecimal.ZERO;
		numeroApuracaoCOFINS = null;
		codigoApuracaoCOFINS = "01";
		valorCOFINS = BigDecimal.ZERO;
		codigoSituacaoTributoPIS = "01";
		codigoSituacaoTributoCOFIN = "01";
		valorBasePIS = BigDecimal.ZERO;
		this.percentualAliquotaPIS = percentualAliquotaPIS;
		valorBaseCOFINS = BigDecimal.ZERO;
		this.percentualAliquotaCOFINS = percentualAliquotaCOFINS;
	}

	public BigDecimal getValorTotalVendas(){

		return valorTotalVendas;
	}

	public void setValorTotalVendas(BigDecimal valorTotalVendas){

		this.valorTotalVendas = valorTotalVendas;
	}

	public String getCodigoOperacaoRegistro(){

		return codigoOperacaoRegistro;
	}

	public void setCodigoOperacaoRegistro(String codigoOperacaoRegistro){

		this.codigoOperacaoRegistro = codigoOperacaoRegistro;
	}

	public Integer getNumeroApuracaoPIS(){

		return numeroApuracaoPIS;
	}

	public void setNumeroApuracaoPIS(Integer numeroApuracaoPIS){

		this.numeroApuracaoPIS = numeroApuracaoPIS;
	}

	public String getCodigoApuracaoPIS(){

		return codigoApuracaoPIS;
	}

	public void setCodigoApuracaoPIS(String codigoApuracaoPIS){

		this.codigoApuracaoPIS = codigoApuracaoPIS;
	}

	public String getCodigoStatusRegistro(){

		return codigoStatusRegistro;
	}

	public void setCodigoStatusRegistro(String codigoStatusRegistro){

		this.codigoStatusRegistro = codigoStatusRegistro;
	}

	public String getDescricaoErroRegistro(){

		return descricaoErroRegistro;
	}

	public void setDescricaoErroRegistro(String descricaoErroRegistro){

		this.descricaoErroRegistro = descricaoErroRegistro;
	}

	public BigDecimal getValorPIS(){

		return valorPIS;
	}

	public void setValorPIS(BigDecimal valorPIS){

		this.valorPIS = valorPIS;
	}

	public Integer getNumeroApuracaoCOFINS(){

		return numeroApuracaoCOFINS;
	}

	public void setNumeroApuracaoCOFINS(Integer numeroApuracaoCOFINS){

		this.numeroApuracaoCOFINS = numeroApuracaoCOFINS;
	}

	public String getCodigoApuracaoCOFINS(){

		return codigoApuracaoCOFINS;
	}

	public void setCodigoApuracaoCOFINS(String codigoApuracaoCOFINS){

		this.codigoApuracaoCOFINS = codigoApuracaoCOFINS;
	}

	public BigDecimal getValorCOFINS(){

		return valorCOFINS;
	}

	public void setValorCOFINS(BigDecimal valorCOFINS){

		this.valorCOFINS = valorCOFINS;
	}

	public String getCodigoSituacaoTributoPIS(){

		return codigoSituacaoTributoPIS;
	}

	public void setCodigoSituacaoTributoPIS(String codigoSituacaoTributoPIS){

		this.codigoSituacaoTributoPIS = codigoSituacaoTributoPIS;
	}

	public String getCodigoSituacaoTributoCOFIN(){

		return codigoSituacaoTributoCOFIN;
	}

	public void setCodigoSituacaoTributoCOFIN(String codigoSituacaoTributoCOFIN){

		this.codigoSituacaoTributoCOFIN = codigoSituacaoTributoCOFIN;
	}

	public BigDecimal getValorBasePIS(){

		return valorBasePIS;
	}

	public void setValorBasePIS(BigDecimal valorBasePIS){

		this.valorBasePIS = valorBasePIS;
	}

	public BigDecimal getPercentualAliquotaPIS(){

		return percentualAliquotaPIS;
	}

	public void setPercentualAliquotaPIS(BigDecimal percentualAliquotaPIS){

		this.percentualAliquotaPIS = percentualAliquotaPIS;
	}

	public BigDecimal getValorBaseCOFINS(){

		return valorBaseCOFINS;
	}

	public void setValorBaseCOFINS(BigDecimal valorBaseCOFINS){

		this.valorBaseCOFINS = valorBaseCOFINS;
	}

	public BigDecimal getPercentualAliquotaCOFINS(){

		return percentualAliquotaCOFINS;
	}

	public void setPercentualAliquotaCOFINS(BigDecimal percentualAliquotaCOFINS){

		this.percentualAliquotaCOFINS = percentualAliquotaCOFINS;
	}

	public TabelaIntegracaoDeferimentoAnteriorReferenciaBasePK getChavePrimaria(){

		return chavePrimaria;
	}

	public void setChavePrimaria(TabelaIntegracaoDeferimentoAnteriorReferenciaBasePK chavePrimaria){

		this.chavePrimaria = chavePrimaria;
	}

	public BigDecimal getValorRecebido(){

		return valorRecebido;
	}

	public void setValorRecebido(BigDecimal valorRecebido){

		this.valorRecebido = valorRecebido;
	}

	/**
	 * Método add
	 * 
	 * @param lancamento
	 * @author Marlos Ribeiro
	 * @since 08/10/2012
	 */
	public void add(LancamentoDeferimentoAnteriorHelper lancamento){

		valorTotalPagoAcumulado = valorTotalPagoAcumulado.add(lancamento.getValorPagamento());
		valorTotalContaAcumulado = valorTotalContaAcumulado.add(lancamento.getValorLancamento());
		valorRecebido = valorTotalPagoAcumulado;
		valorTotalVendas = valorTotalContaAcumulado;
		valorPIS = valorTotalPagoAcumulado.multiply(percentualAliquotaPIS.divide(BigDecimal.valueOf(100), 5, BigDecimal.ROUND_HALF_DOWN));
		valorCOFINS = valorTotalPagoAcumulado.multiply(percentualAliquotaCOFINS.divide(BigDecimal.valueOf(100), 5,
						BigDecimal.ROUND_HALF_DOWN));
		valorBasePIS = valorTotalPagoAcumulado;
		valorBaseCOFINS = valorTotalPagoAcumulado;

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString(){

		return chavePrimaria.toString();
	}
}
