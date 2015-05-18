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
 * Adriano Britto Siqueira
 * Alexandre Santos Cabral
 * Ana Carolina Alves Breda
 * Ana Maria Andrade Cavalcante
 * Aryed Lins de Araújo
 * Bruno Leonardo Rodrigues Barros
 * Carlos Elmano Rodrigues Ferreira
 * Cláudio de Andrade Lira
 * Denys Guimarães Guenes Tavares
 * Eduardo Breckenfeld da Rosa Borges
 * Fabíola Gomes de Araújo
 * Flávio Leonardo Cavalcanti Cordeiro
 * Francisco do Nascimento Júnior
 * Homero Sampaio Cavalcanti
 * Ivan Sérgio da Silva Júnior
 * José Edmar de Siqueira
 * José Thiago Tenório Lopes
 * Kássia Regina Silvestre de Albuquerque
 * Leonardo Luiz Vieira da Silva
 * Márcio Roberto Batista da Silva
 * Maria de Fátima Sampaio Leite
 * Micaela Maria Coelho de Araújo
 * Nelson Mendonça de Carvalho
 * Newton Morais e Silva
 * Pedro Alexandre Santos da Silva Filho
 * Rafael Corrêa Lima e Silva
 * Rafael Francisco Pinto
 * Rafael Koury Monteiro
 * Rafael Palermo de Araújo
 * Raphael Veras Rossiter
 * Roberto Sobreira Barbalho
 * Rodrigo Avellar Silveira
 * Rosana Carvalho Barbosa
 * Sávio Luiz de Andrade Cavalcante
 * Tai Mu Shih
 * Thiago Augusto Souza do Nascimento
 * Tiago Moreno Rodrigues
 * Vivianne Barbosa Sousa
 * 
 * GSANPCG
 * Eduardo Henrique
 * Saulo Lima
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

package gcom.cobranca.bean;

import gcom.arrecadacao.pagamento.GuiaPagamentoPrestacao;
import gcom.cobranca.parcelamento.Parcelamento;
import gcom.faturamento.debito.DebitoTipo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

/**
 * Guias de Pagamento com os valores:
 * 
 * @author Rafael Santos
 * @since 04/01/2006
 * @author eduardo henrique
 * @date 13/08/2008
 *       Adição de Novos Atributos
 * @author Saulo Lima
 * @date 26/06/2009
 *       Mudança na estrutura do helper
 */
public class GuiaPagamentoValoresHelper
				implements Serializable {

	private static final long serialVersionUID = -6642202957811019727L;

	private Integer idGuiaPagamento;

	private Short numeroPrestacao;

	private Date dataVencimento;

	private Date dataEmissao;

	private Integer anoMesReferenciaFaturamento;

	private Set<GuiaPagamentoPrestacao> guiaPagamentoPrestacoes;

	private BigDecimal valorPago;

	private BigDecimal valorMulta;

	private BigDecimal valorJurosMora;

	private BigDecimal valorAtualizacaoMonetaria;

	private Short desativarSelecao;

	private Short indicadorCobrancaAdministrativa;

	private Integer idDebitoCreditoSituacaoAtual;

	private String descricaoDebitoCreditoSituacaoAtual;

	private BigDecimal valorSucumbencia;

	private BigDecimal valorJurosMoraSucumbencia;

	private BigDecimal valorAtualizacaoMonetariaSucumbencia;

	private Short indicadorDividaAtiva;

	private Short indicadorExecucaoFiscal;

	private Integer idParcelamento;

	public GuiaPagamentoValoresHelper() {

	}

	/**
	 * @param guiaPagamentoPrestacao
	 * @param valorPago
	 * @param valorMulta
	 * @param valorJurosMora
	 * @param valoratualizacaoMonetaria
	 */
	public GuiaPagamentoValoresHelper(BigDecimal valorPago, BigDecimal valorMulta, BigDecimal valorJurosMora,
										BigDecimal valorAtualizacaoMonetaria) {

		this.valorPago = valorPago;
		this.valorMulta = valorMulta;
		this.valorJurosMora = valorJurosMora;
		this.valorAtualizacaoMonetaria = valorAtualizacaoMonetaria;
	}

	/**
	 * Recupera o valor total da prestação, somando todos os valores dos Tipos Débitos.
	 * 
	 * @author Saulo Lima
	 * @date 26/06/2009
	 * @return ValorTotalPrestacao
	 */
	public BigDecimal getValorTotalPrestacao(){

		BigDecimal retorno = new BigDecimal("0.00");
		if(this.guiaPagamentoPrestacoes != null && !this.guiaPagamentoPrestacoes.isEmpty()){
			Iterator<GuiaPagamentoPrestacao> iterator = this.guiaPagamentoPrestacoes.iterator();
			while(iterator.hasNext()){
				GuiaPagamentoPrestacao prestacao = iterator.next();
				retorno = retorno
								.add(prestacao.getValorPrestacao().setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO));
			}
		}
		return retorno;
	}

	/**
	 * Recupera o valor total da prestação, somando os valores dos Tipos Débitos exceto Sucumbência.
	 * 
	 * @author Saulo Lima
	 * @date 16/06/2014
	 * @return ValorPrestacaoSemSucumbencia
	 */
	public BigDecimal getValorTotalPrestacaoSemSucumbencia(){

		BigDecimal retorno = new BigDecimal("0.00");
		if(this.guiaPagamentoPrestacoes != null && !this.guiaPagamentoPrestacoes.isEmpty()){
			Iterator<GuiaPagamentoPrestacao> iterator = this.guiaPagamentoPrestacoes.iterator();
			while(iterator.hasNext()){
				GuiaPagamentoPrestacao prestacao = iterator.next();
				if(!prestacao.getDebitoTipo().getId().equals(DebitoTipo.SUCUMBENCIA)){
					retorno = retorno.add(prestacao.getValorPrestacao().setScale(Parcelamento.CASAS_DECIMAIS,
									Parcelamento.TIPO_ARREDONDAMENTO));
				}
			}
		}
		return retorno;
	}

	/**
	 * Recupera o valor total da prestação, somando os valores dos Tipos Débitos de Sucumbência.
	 * 
	 * @author Saulo Lima
	 * @date 16/06/2014
	 * @return ValorPrestacaoSucumbencia
	 */
	public BigDecimal getValorTotalPrestacaoSucumbencia(){

		BigDecimal retorno = BigDecimal.ZERO;
		if(this.guiaPagamentoPrestacoes != null && !this.guiaPagamentoPrestacoes.isEmpty()){
			Iterator<GuiaPagamentoPrestacao> iterator = this.guiaPagamentoPrestacoes.iterator();
			while(iterator.hasNext()){
				GuiaPagamentoPrestacao prestacao = iterator.next();
				if(prestacao.getDebitoTipo().getId().equals(DebitoTipo.SUCUMBENCIA)){
					retorno = retorno.add(prestacao.getValorPrestacao().setScale(Parcelamento.CASAS_DECIMAIS,
									Parcelamento.TIPO_ARREDONDAMENTO));
				}
			}
		}
		return retorno;
	}

	public BigDecimal getValorTotalAcrescimoSucumbencia(){

		BigDecimal retorno = BigDecimal.ZERO;

		// Valor de JurosMora
		if(this.getValorJurosMoraSucumbencia() != null){
			retorno = retorno.add(this.getValorJurosMoraSucumbencia().setScale(Parcelamento.CASAS_DECIMAIS,
							Parcelamento.TIPO_ARREDONDAMENTO));
		}
		// Valor de AtualizacaoMonetaria
		if(this.getValorAtualizacaoMonetariaSucumbencia() != null){
			retorno = retorno.add(this.getValorAtualizacaoMonetariaSucumbencia().setScale(Parcelamento.CASAS_DECIMAIS,
							Parcelamento.TIPO_ARREDONDAMENTO));
		}

		retorno = retorno.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);

		return retorno;
	}

	/**
	 * @return Returns the idParcelamento.
	 */
	public Integer getIdParcelamento(){

		return idParcelamento;
	}

	/**
	 * @param idParcelamento
	 *            The idParcelamento to set.
	 */
	public void setIdParcelamento(Integer idParcelamento){

		this.idParcelamento = idParcelamento;
	}

	/**
	 * @return Returns the valorJurosMora.
	 */
	public BigDecimal getValorJurosMora(){

		return valorJurosMora;
	}

	/**
	 * @param valorJurosMora
	 *            The valorJurosMora to set.
	 */
	public void setValorJurosMora(BigDecimal valorJurosMora){

		this.valorJurosMora = valorJurosMora;
	}

	/**
	 * @return Returns the valorMulta.
	 */
	public BigDecimal getValorMulta(){

		return valorMulta;
	}

	/**
	 * @param valorMulta
	 *            The valorMulta to set.
	 */
	public void setValorMulta(BigDecimal valorMulta){

		this.valorMulta = valorMulta;
	}

	/**
	 * @return Returns the valorPago.
	 */
	public BigDecimal getValorPago(){

		return valorPago;
	}

	/**
	 * @param valorPago
	 *            The valorPago to set.
	 */
	public void setValorPago(BigDecimal valorPago){

		this.valorPago = valorPago;
	}

	/**
	 * @return Returns the valorAtualizacaoMonetaria.
	 */
	public BigDecimal getValorAtualizacaoMonetaria(){

		return valorAtualizacaoMonetaria;
	}

	/**
	 * @param valorAtualizacaoMonetaria
	 *            The valorAtualizacaoMonetaria to set.
	 */
	public void setValorAtualizacaoMonetaria(BigDecimal valorAtualizacaoMonetaria){

		this.valorAtualizacaoMonetaria = valorAtualizacaoMonetaria;
	}

	/**
	 * [UC0214] - Efetuar Parcelamento de Débitos
	 * Cálcula o valor dos acrescimos por impontualidade da conta (multa + juros de mora +
	 * atualização monetária )
	 * 
	 * @author Roberta Costa
	 * @created 03/03/2006
	 */
	public BigDecimal getValorAcrescimosImpontualidade(){

		BigDecimal retorno = new BigDecimal("0.00");

		// Valor de Multa
		if(this.getValorMulta() != null){
			retorno = retorno.add(this.getValorMulta().setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO));
		}
		// Valor de JurosMora
		if(this.getValorJurosMora() != null){
			retorno = retorno.add(this.getValorJurosMora().setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO));
		}
		// Valor de AtualizacaoMonetaria
		if(this.getValorAtualizacaoMonetaria() != null){
			retorno = retorno.add(this.getValorAtualizacaoMonetaria().setScale(Parcelamento.CASAS_DECIMAIS,
							Parcelamento.TIPO_ARREDONDAMENTO));
		}
		// Valor de JurosMora Sucumbência
		if(this.getValorJurosMoraSucumbencia() != null){
			retorno = retorno.add(this.getValorJurosMoraSucumbencia().setScale(Parcelamento.CASAS_DECIMAIS,
							Parcelamento.TIPO_ARREDONDAMENTO));
		}
		// Valor de AtualizacaoMonetaria Sucumbência
		if(this.getValorAtualizacaoMonetariaSucumbencia() != null){
			retorno = retorno.add(this.getValorAtualizacaoMonetariaSucumbencia().setScale(Parcelamento.CASAS_DECIMAIS,
							Parcelamento.TIPO_ARREDONDAMENTO));
		}

		retorno = retorno.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);

		return retorno;
	}

	public BigDecimal getValorAcrescimosImpontualidadeSemSucumbencia(){

		BigDecimal retorno = BigDecimal.ZERO;

		// Valor de Multa
		if(this.getValorMulta() != null){
			retorno = retorno.add(this.getValorMulta().setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO));
		}
		// Valor de JurosMora
		if(this.getValorJurosMora() != null){
			retorno = retorno.add(this.getValorJurosMora().setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO));
		}
		// Valor de AtualizacaoMonetaria
		if(this.getValorAtualizacaoMonetaria() != null){
			retorno = retorno.add(this.getValorAtualizacaoMonetaria().setScale(Parcelamento.CASAS_DECIMAIS,
							Parcelamento.TIPO_ARREDONDAMENTO));
		}

		retorno = retorno.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);

		return retorno;
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

	public Integer getIdGuiaPagamento(){

		return idGuiaPagamento;
	}

	public void setIdGuiaPagamento(Integer idGuiaPagamento){

		this.idGuiaPagamento = idGuiaPagamento;
	}

	public Short getNumeroPrestacao(){

		return numeroPrestacao;
	}

	public void setNumeroPrestacao(Short numeroPrestacao){

		this.numeroPrestacao = numeroPrestacao;
	}

	public Set<GuiaPagamentoPrestacao> getGuiaPagamentoPrestacoes(){

		return guiaPagamentoPrestacoes;
	}

	public void setGuiaPagamentoPrestacoes(Set<GuiaPagamentoPrestacao> guiaPagamentoPrestacoes){

		this.guiaPagamentoPrestacoes = guiaPagamentoPrestacoes;
	}

	public Date getDataVencimento(){

		return dataVencimento;
	}

	public void setDataVencimento(Date dataVencimento){

		this.dataVencimento = dataVencimento;
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

	// public BigDecimal getValorTotalContaValores(){
	//
	// BigDecimal retorno = BigDecimal.ZERO;
	//
	// // Valor de Multa
	// if(this.getValorMulta() != null){
	// retorno = retorno.add(this.getValorMulta().setScale(Parcelamento.CASAS_DECIMAIS,
	// Parcelamento.TIPO_ARREDONDAMENTO));
	// }
	// // Valor de JurosMora
	// if(this.getValorJurosMora() != null){
	// retorno = retorno.add(this.getValorJurosMora().setScale(Parcelamento.CASAS_DECIMAIS,
	// Parcelamento.TIPO_ARREDONDAMENTO));
	// }
	// // Valor de AtualizacaoMonetaria
	// if(this.getValorAtualizacaoMonetaria() != null){
	// retorno =
	// retorno.add(this.getValorAtualizacaoMonetaria().setScale(Parcelamento.CASAS_DECIMAIS,
	// Parcelamento.TIPO_ARREDONDAMENTO));
	// }
	//
	// retorno = retorno.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
	//
	// return retorno;
	// }

	public Short getDesativarSelecao(){

		return desativarSelecao;
	}

	public void setDesativarSelecao(Short desativarSelecao){

		this.desativarSelecao = desativarSelecao;
	}

	public Short getIndicadorCobrancaAdministrativa(){

		return indicadorCobrancaAdministrativa;
	}

	public void setIndicadorCobrancaAdministrativa(Short indicadorCobrancaAdministrativa){

		this.indicadorCobrancaAdministrativa = indicadorCobrancaAdministrativa;
	}

	public Integer getIdDebitoCreditoSituacaoAtual(){

		return idDebitoCreditoSituacaoAtual;
	}

	public void setIdDebitoCreditoSituacaoAtual(Integer idDebitoCreditoSituacaoAtual){

		this.idDebitoCreditoSituacaoAtual = idDebitoCreditoSituacaoAtual;
	}

	public String getDescricaoDebitoCreditoSituacaoAtual(){

		return descricaoDebitoCreditoSituacaoAtual;
	}

	public void setDescricaoDebitoCreditoSituacaoAtual(String descricaoDebitoCreditoSituacaoAtual){

		this.descricaoDebitoCreditoSituacaoAtual = descricaoDebitoCreditoSituacaoAtual;
	}

	public BigDecimal getValorSucumbencia(){

		return valorSucumbencia;
	}

	public void setValorSucumbencia(BigDecimal valorSucumbencia){

		this.valorSucumbencia = valorSucumbencia;
	}

	public BigDecimal getValorJurosMoraSucumbencia(){

		return valorJurosMoraSucumbencia;
	}

	public void setValorJurosMoraSucumbencia(BigDecimal valorJurosMoraSucumbencia){

		this.valorJurosMoraSucumbencia = valorJurosMoraSucumbencia;
	}

	public BigDecimal getValorAtualizacaoMonetariaSucumbencia(){

		return valorAtualizacaoMonetariaSucumbencia;
	}

	public void setValorAtualizacaoMonetariaSucumbencia(BigDecimal valorAtualizacaoMonetariaSucumbencia){

		this.valorAtualizacaoMonetariaSucumbencia = valorAtualizacaoMonetariaSucumbencia;
	}

	public Short getIndicadorDividaAtiva(){

		return indicadorDividaAtiva;
	}

	public void setIndicadorDividaAtiva(Short indicadorDividaAtiva){

		this.indicadorDividaAtiva = indicadorDividaAtiva;
	}

	public Short getIndicadorExecucaoFiscal(){

		return indicadorExecucaoFiscal;
	}

	public void setIndicadorExecucaoFiscal(Short indicadorExecucaoFiscal){

		this.indicadorExecucaoFiscal = indicadorExecucaoFiscal;
	}

}