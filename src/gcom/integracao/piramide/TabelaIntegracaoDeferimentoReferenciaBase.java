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
 * 
 * GSANPCG
 * Eduardo Henrique
 */
package gcom.integracao.piramide;

import gcom.integracao.piramide.bean.LancamentoDeferimentoHelper;
import gcom.util.Util;

import java.math.BigDecimal;


/**
 * @author mgrb
 *         Representa a tabela: TI_DIFER_PERIODO
 */
public class TabelaIntegracaoDeferimentoReferenciaBase {

	private TabelaIntegracaoDeferimentoReferenciaBasePK chavePrimaria;

	// SOMENTE PARA USO NO SOMATORIO
	private BigDecimal valorTotalContaAcumulado;

	private BigDecimal valorVendasPeriodo;

	private String codigoApuracaoPIS;

	private Integer numeroApuracaoPIS;

	private BigDecimal valorBasePIS;

	private BigDecimal percentualAliquotaPIS;

	private BigDecimal valorPIS;

	private String codigoApuracaoCOFINS;

	private BigDecimal valorBaseCOFINS;

	private Integer numeroApuracaoCOFINS;

	private BigDecimal percentualAliquotaCOFINS;

	private BigDecimal valorCOFINS;

	private BigDecimal valorNaoRecebido;

	private String codigoOperacaoRegistro;

	private String codigoStatusRegistro;

	private String descricaoErroRegistro;

	private String codigoSituacaoTributoPIS;

	private String codigoSituacaoTributoCOFIN;

	/**
	 * TabelaIntegracaoDeferimentoReferenciaBase
	 * <p>
	 * Esse m�todo Constroi uma inst�ncia de TabelaIntegracaoDeferimentoReferenciaBase.
	 * </p>
	 * 
	 * @author Marlos Ribeiro
	 * @since 05/10/2012
	 */
	public TabelaIntegracaoDeferimentoReferenciaBase() {

	}

	/**
	 * TabelaIntegracaoDeferimentoReferenciaBase
	 * <p>
	 * Esse m�todo Constroi uma inst�ncia de TabelaIntegracaoDeferimentoReferenciaBase.
	 * </p>
	 * 
	 * @author Marlos Ribeiro
	 * @param gerenciaRegionalID
	 * @param mesAnoReferencia
	 * @param clienteDoc
	 * @param percentualAliquotaPIS
	 * @param percentualAliquotaCOFINS
	 * @since 05/10/2012
	 */
	public TabelaIntegracaoDeferimentoReferenciaBase(String gerenciaRegionalID, String mesAnoReferencia, String clienteDoc,
														BigDecimal percentualAliquotaPIS, BigDecimal percentualAliquotaCOFINS) {

		chavePrimaria = new TabelaIntegracaoDeferimentoReferenciaBasePK(gerenciaRegionalID, "GSA", mesAnoReferencia, clienteDoc);
		valorVendasPeriodo = BigDecimal.ZERO;
		codigoApuracaoPIS = "01";
		numeroApuracaoPIS = null;
		valorBasePIS = BigDecimal.ZERO;
		this.percentualAliquotaPIS = percentualAliquotaPIS;
		valorPIS = BigDecimal.ZERO;
		codigoApuracaoCOFINS = "01";
		valorBaseCOFINS = BigDecimal.ZERO;
		numeroApuracaoCOFINS = null;
		this.percentualAliquotaCOFINS = percentualAliquotaCOFINS;
		valorCOFINS = BigDecimal.ZERO;
		valorNaoRecebido = BigDecimal.ZERO;
		codigoOperacaoRegistro = "I";
		codigoStatusRegistro = "NP";
		descricaoErroRegistro = null;
		codigoSituacaoTributoPIS = "01";
		codigoSituacaoTributoCOFIN = "01";

		valorTotalContaAcumulado = BigDecimal.ZERO;
	}


	public BigDecimal getValorVendasPeriodo(){

		return valorVendasPeriodo;
	}

	public void setValorVendasPeriodo(BigDecimal valorVendasPeriodo){

		this.valorVendasPeriodo = valorVendasPeriodo;
	}

	public String getCodigoApuracaoPIS(){

		return codigoApuracaoPIS;
	}

	public void setCodigoApuracaoPIS(String codigoApuracaoPIS){

		this.codigoApuracaoPIS = codigoApuracaoPIS;
	}

	public Integer getNumeroApuracaoPIS(){

		return numeroApuracaoPIS;
	}

	public void setNumeroApuracaoPIS(Integer numeroApuracaoPIS){

		this.numeroApuracaoPIS = numeroApuracaoPIS;
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

	public BigDecimal getValorPIS(){

		return valorPIS;
	}

	public void setValorPIS(BigDecimal valorPIS){

		this.valorPIS = valorPIS;
	}

	public String getCodigoApuracaoCOFINS(){

		return codigoApuracaoCOFINS;
	}

	public void setCodigoApuracaoCOFINS(String codigoApuracaoCOFINS){

		this.codigoApuracaoCOFINS = codigoApuracaoCOFINS;
	}

	public BigDecimal getValorBaseCOFINS(){

		return valorBaseCOFINS;
	}

	public void setValorBaseCOFINS(BigDecimal valorBaseCOFINS){

		this.valorBaseCOFINS = valorBaseCOFINS;
	}

	public Integer getNumeroApuracaoCOFINS(){

		return numeroApuracaoCOFINS;
	}

	public void setNumeroApuracaoCOFINS(Integer numeroApuracaoCOFINS){

		this.numeroApuracaoCOFINS = numeroApuracaoCOFINS;
	}

	public BigDecimal getPercentualAliquotaCOFINS(){

		return percentualAliquotaCOFINS;
	}

	public void setPercentualAliquotaCOFINS(BigDecimal percentualAliquotaCOFINS){

		this.percentualAliquotaCOFINS = percentualAliquotaCOFINS;
	}

	public BigDecimal getValorCOFINS(){

		return valorCOFINS;
	}

	public void setValorCOFINS(BigDecimal valorCOFINS){

		this.valorCOFINS = valorCOFINS;
	}

	public BigDecimal getValorNaoRecebido(){

		return valorNaoRecebido;
	}

	public void setValorNaoRecebido(BigDecimal valorNaoRecebido){

		this.valorNaoRecebido = valorNaoRecebido;
	}

	public String getCodigoOperacaoRegistro(){

		return codigoOperacaoRegistro;
	}

	public void setCodigoOperacaoRegistro(String codigoOperacaoRegistro){

		this.codigoOperacaoRegistro = codigoOperacaoRegistro;
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

	public TabelaIntegracaoDeferimentoReferenciaBasePK getChavePrimaria(){

		return chavePrimaria;
	}

	public void setChavePrimaria(TabelaIntegracaoDeferimentoReferenciaBasePK chavePrimaria){

		this.chavePrimaria = chavePrimaria;
	}

	/**
	 * M�todo add
	 * <p>
	 * Esse m�todo implementa a agregacao de lancamentos semelhantes (Gerencia Regional / Cliente
	 * Documento)
	 * </p>
	 * 
	 * @param lancamento
	 * @author Marlos Ribeiro
	 * @since 05/10/2012
	 */
	public void add(LancamentoDeferimentoHelper lancamento){

		valorTotalContaAcumulado = valorTotalContaAcumulado.add(lancamento.getValorLancamento());
		valorVendasPeriodo = valorTotalContaAcumulado;
		valorBasePIS = valorTotalContaAcumulado;
		valorPIS = Util.truncar(valorTotalContaAcumulado.multiply(percentualAliquotaPIS.divide(BigDecimal.valueOf(100), 5,
						BigDecimal.ROUND_HALF_DOWN)), 2);
		valorBaseCOFINS = valorTotalContaAcumulado;
		valorCOFINS = Util.truncar(valorTotalContaAcumulado.multiply(percentualAliquotaCOFINS.divide(BigDecimal.valueOf(100), 5,
						BigDecimal.ROUND_HALF_DOWN)), 2);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString(){
		return chavePrimaria.toString();
	}
}
