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

package gcom.cobranca.parcelamento;

import gcom.gui.cobranca.parcelamento.ParcelamentoPerfilActionForm;

public class ParcelamentoPerfilHelper {

	private static final long serialVersionUID = 1L;

	private String percentualDescontoAcrescimo;

	private String percentualTarifaMinimaPrestacao;

	private String valorDebitoPrestacao;

	private String ultimaAlteracao;

	private String subcategoria;

	private String imovelSituacaoTipo;

	private String imovelPerfil;

	private String resolucaoDiretoria;

	private String qtdeMaximaReparcelamento;

	private String quantidadeMinimaMesesDebito;

	private String percentualDescontoSemRestabelecimentoAntiguidade;

	private String percentualDescontoComRestabelecimentoAntiguidade;

	private String percentualDescontoAtivo;

	private String quantidadeMaximaMesesInatividade;

	private String percentualDescontoSemRestabelecimentoInatividade;

	private String percentualDescontoJurosMoraSemRestabelecimentoInatividade;

	private String percentualDescontoMultaSemRestabelecimentoInatividade;

	private String percentualDescontoComRestabelecimentoInatividade;

	private String percentualDescontoJurosMoraComRestabelecimentoInatividade;

	private String percentualDescontoMultaComRestabelecimentoInatividade;

	private String consumoMinimo;

	private String percentualVariacaoConsumoMedio;

	private String indicadorParcelarChequeDevolvido;

	private String indicadorParcelarSancoesMaisDeUmaConta;

	private String indicadorCobrarAcrescimosImpontualidade;

	private String indicadorPermitirCobrancaParcelamentoPorGuia;

	private String indicadorPermitirInformarNumeroValorParcelas;

	private String indicadorCobrarMultaPorDescumprimentoPrestacao;

	private String numeroPrestacacoesDescumpridasConsecutivas;

	private String percentualMultaPorPrestacacoesDescumpridas;

	/*
	 * Novos Campos
	 */
	private String valorMinimoDebitoAParcelarFaixaDebito;

	private String valorMaximoDebitoAParcelarFaixaDebito;

	private String indicadorDebitoOriginalOuAtualizadoFaixaDebito;

	private String indicadorOpcoesDebitoOriginalOuAtualizadoPrestacaoMinima;

	private String percentualValorDebitoCalculoValorMinimoPrestacao;

	private String numeroConsumoEconomia;

	private String numeroAreaConstruida;

	private String anoMesReferenciaLimiteInferior;

	private String anoMesReferenciaLimiteSuperior;

	private String percentualDescontoAVista;

	private String parcelaQuantidadeMinimaFatura;

	private String percentualDescontoSancao;

	private String quantidadeEconomias;

	private String capacidadeHidrometro;

	/*
	 * Campos do PopUp
	 */
	private String quantidadeMaximaPrestacao;

	private String taxaJuros;

	private String percentualMinimoEntrada;

	private String percentualTarifaMinimaImovel;

	private String percentualValorReparcelado;

	private String valorMaxPercFaixaValor;

	private String percentualPercFaixaValor;

	private String quantidadeMaxPrestacaoEspecial;

	private String valorFixoEntrada;

	private String indicadorOpcoesEntradaParcelamento;

	private String indicadorOpcoesPercentualFaixaValor;

	private String valorMinimoEntrada;

	private String percentualEntradaSugerida;

	public ParcelamentoPerfilHelper(ParcelamentoPerfilActionForm parcelamentoPerfilActionForm) {

		this.percentualDescontoAcrescimo = parcelamentoPerfilActionForm.getPercentualDescontoAcrescimo();

		this.percentualTarifaMinimaPrestacao = parcelamentoPerfilActionForm.getPercentualTarifaMinimaPrestacao();

		this.valorDebitoPrestacao = parcelamentoPerfilActionForm.getValorDebitoPrestacao();

		this.ultimaAlteracao = parcelamentoPerfilActionForm.getUltimaAlteracao();

		this.subcategoria = parcelamentoPerfilActionForm.getSubcategoria();

		this.imovelSituacaoTipo = parcelamentoPerfilActionForm.getImovelSituacaoTipo();

		this.imovelPerfil = parcelamentoPerfilActionForm.getImovelPerfil();

		this.resolucaoDiretoria = parcelamentoPerfilActionForm.getResolucaoDiretoria();

		this.qtdeMaximaReparcelamento = parcelamentoPerfilActionForm.getQtdeMaximaReparcelamento();

		this.quantidadeMinimaMesesDebito = parcelamentoPerfilActionForm.getQuantidadeMinimaMesesDebito();

		this.percentualDescontoSemRestabelecimentoAntiguidade = parcelamentoPerfilActionForm
						.getPercentualDescontoSemRestabelecimentoAntiguidade();

		this.percentualDescontoComRestabelecimentoAntiguidade = parcelamentoPerfilActionForm
						.getPercentualDescontoComRestabelecimentoAntiguidade();

		this.percentualDescontoAtivo = parcelamentoPerfilActionForm.getPercentualDescontoAtivo();

		this.quantidadeMaximaMesesInatividade = parcelamentoPerfilActionForm.getQuantidadeMaximaMesesInatividade();

		this.percentualDescontoSemRestabelecimentoInatividade = parcelamentoPerfilActionForm
						.getPercentualDescontoSemRestabelecimentoInatividade();

		this.percentualDescontoJurosMoraSemRestabelecimentoInatividade = parcelamentoPerfilActionForm
						.getPercentualDescontoJurosMoraSemRestabelecimentoInatividade();

		this.percentualDescontoMultaSemRestabelecimentoInatividade = parcelamentoPerfilActionForm
						.getPercentualDescontoMultaSemRestabelecimentoInatividade();

		this.percentualDescontoComRestabelecimentoInatividade = parcelamentoPerfilActionForm
						.getPercentualDescontoComRestabelecimentoInatividade();

		this.percentualDescontoJurosMoraComRestabelecimentoInatividade = parcelamentoPerfilActionForm
						.getPercentualDescontoJurosMoraComRestabelecimentoInatividade();

		this.percentualDescontoMultaComRestabelecimentoInatividade = parcelamentoPerfilActionForm
						.getPercentualDescontoMultaComRestabelecimentoInatividade();

		this.consumoMinimo = parcelamentoPerfilActionForm.getConsumoMinimo();

		this.percentualVariacaoConsumoMedio = parcelamentoPerfilActionForm.getPercentualVariacaoConsumoMedio();

		this.indicadorParcelarChequeDevolvido = parcelamentoPerfilActionForm.getIndicadorParcelarChequeDevolvido();

		this.indicadorParcelarSancoesMaisDeUmaConta = parcelamentoPerfilActionForm.getIndicadorParcelarSancoesMaisDeUmaConta();

		this.indicadorCobrarAcrescimosImpontualidade = parcelamentoPerfilActionForm.getIndicadorCobrarAcrescimosImpontualidade();

		this.indicadorPermitirCobrancaParcelamentoPorGuia = parcelamentoPerfilActionForm.getIndicadorPermitirCobrancaParcelamentoPorGuia();

		this.indicadorPermitirInformarNumeroValorParcelas = parcelamentoPerfilActionForm.getIndicadorPermitirInformarNumeroValorParcelas();

		this.indicadorCobrarMultaPorDescumprimentoPrestacao = parcelamentoPerfilActionForm
						.getIndicadorCobrarMultaPorDescumprimentoPrestacao();

		this.numeroPrestacacoesDescumpridasConsecutivas = parcelamentoPerfilActionForm.getNumeroPrestacacoesDescumpridasConsecutivas();

		this.percentualMultaPorPrestacacoesDescumpridas = parcelamentoPerfilActionForm.getPercentualMultaPorPrestacacoesDescumpridas();

		this.valorMinimoDebitoAParcelarFaixaDebito = parcelamentoPerfilActionForm.getValorMinimoDebitoAParcelarFaixaDebito();

		this.valorMaximoDebitoAParcelarFaixaDebito = parcelamentoPerfilActionForm.getValorMaximoDebitoAParcelarFaixaDebito();

		this.indicadorDebitoOriginalOuAtualizadoFaixaDebito = parcelamentoPerfilActionForm
						.getIndicadorDebitoOriginalOuAtualizadoFaixaDebito();

		this.indicadorOpcoesDebitoOriginalOuAtualizadoPrestacaoMinima = parcelamentoPerfilActionForm
						.getIndicadorOpcoesDebitoOriginalOuAtualizadoPrestacaoMinima();

		this.percentualValorDebitoCalculoValorMinimoPrestacao = parcelamentoPerfilActionForm
						.getPercentualValorDebitoCalculoValorMinimoPrestacao();

		this.numeroConsumoEconomia = parcelamentoPerfilActionForm.getNumeroConsumoEconomia();

		this.numeroAreaConstruida = parcelamentoPerfilActionForm.getNumeroAreaConstruida();

		this.anoMesReferenciaLimiteInferior = parcelamentoPerfilActionForm.getAnoMesReferenciaLimiteInferior();

		this.anoMesReferenciaLimiteSuperior = parcelamentoPerfilActionForm.getAnoMesReferenciaLimiteSuperior();

		this.percentualDescontoAVista = parcelamentoPerfilActionForm.getPercentualDescontoAVista();

		this.parcelaQuantidadeMinimaFatura = parcelamentoPerfilActionForm.getParcelaQuantidadeMinimaFatura();

		this.percentualDescontoSancao = parcelamentoPerfilActionForm.getPercentualDescontoSancao();

		this.quantidadeEconomias = parcelamentoPerfilActionForm.getQuantidadeEconomias();

		this.capacidadeHidrometro = parcelamentoPerfilActionForm.getCapacidadeHidrometro();

		this.quantidadeMaximaPrestacao = parcelamentoPerfilActionForm.getQuantidadeMaximaPrestacao();

		this.taxaJuros = parcelamentoPerfilActionForm.getTaxaJuros();

		this.percentualMinimoEntrada = parcelamentoPerfilActionForm.getPercentualMinimoEntrada();

		this.percentualTarifaMinimaImovel = parcelamentoPerfilActionForm.getPercentualTarifaMinimaImovel();

		this.percentualValorReparcelado = parcelamentoPerfilActionForm.getPercentualValorReparcelado();

		this.valorMaxPercFaixaValor = parcelamentoPerfilActionForm.getValorMaxPercFaixaValor();

		this.percentualPercFaixaValor = parcelamentoPerfilActionForm.getPercentualPercFaixaValor();

		this.quantidadeMaxPrestacaoEspecial = parcelamentoPerfilActionForm.getQuantidadeMaxPrestacaoEspecial();

		this.valorFixoEntrada = parcelamentoPerfilActionForm.getValorFixoEntrada();

		this.indicadorOpcoesEntradaParcelamento = parcelamentoPerfilActionForm.getIndicadorOpcoesEntradaParcelamento();

		this.indicadorOpcoesPercentualFaixaValor = parcelamentoPerfilActionForm.getIndicadorOpcoesPercentualFaixaValor();

		this.valorMinimoEntrada = parcelamentoPerfilActionForm.getValorMinimoEntrada();

		this.percentualEntradaSugerida = parcelamentoPerfilActionForm.getPercentualEntradaSugerida();

	}

	public String getQtdeMaximaReparcelamento(){

		return qtdeMaximaReparcelamento;
	}

	public void setQtdeMaximaReparcelamento(String qtdeMaximaReparcelamento){

		this.qtdeMaximaReparcelamento = qtdeMaximaReparcelamento;
	}

	public String getImovelPerfil(){

		return imovelPerfil;
	}

	public void setImovelPerfil(String imovelPerfil){

		this.imovelPerfil = imovelPerfil;
	}

	public String getImovelSituacaoTipo(){

		return imovelSituacaoTipo;
	}

	public void setImovelSituacaoTipo(String imovelSituacaoTipo){

		this.imovelSituacaoTipo = imovelSituacaoTipo;
	}

	public String getSubcategoria(){

		return subcategoria;
	}

	public void setSubcategoria(String subcategoria){

		this.subcategoria = subcategoria;
	}

	public String getResolucaoDiretoria(){

		return resolucaoDiretoria;
	}

	public void setResolucaoDiretoria(String resolucaoDiretoria){

		this.resolucaoDiretoria = resolucaoDiretoria;
	}

	public String getPercentualDescontoAcrescimo(){

		return percentualDescontoAcrescimo;
	}

	public void setPercentualDescontoAcrescimo(String percentualDescontoAcrescimo){

		this.percentualDescontoAcrescimo = percentualDescontoAcrescimo;
	}

	public String getUltimaAlteracao(){

		return ultimaAlteracao;
	}

	public void setUltimaAlteracao(String ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public String getPercentualDescontoComRestabelecimentoAntiguidade(){

		return percentualDescontoComRestabelecimentoAntiguidade;
	}

	public void setPercentualDescontoComRestabelecimentoAntiguidade(String percentualDescontoComRestabelecimentoAntiguidade){

		this.percentualDescontoComRestabelecimentoAntiguidade = percentualDescontoComRestabelecimentoAntiguidade;
	}

	public String getPercentualDescontoComRestabelecimentoInatividade(){

		return percentualDescontoComRestabelecimentoInatividade;
	}

	public void setPercentualDescontoComRestabelecimentoInatividade(String percentualDescontoComRestabelecimentoInatividade){

		this.percentualDescontoComRestabelecimentoInatividade = percentualDescontoComRestabelecimentoInatividade;
	}

	public String getPercentualDescontoSemRestabelecimentoAntiguidade(){

		return percentualDescontoSemRestabelecimentoAntiguidade;
	}

	public void setPercentualDescontoSemRestabelecimentoAntiguidade(String percentualDescontoSemRestabelecimentoAntiguidade){

		this.percentualDescontoSemRestabelecimentoAntiguidade = percentualDescontoSemRestabelecimentoAntiguidade;
	}

	public String getPercentualDescontoSemRestabelecimentoInatividade(){

		return percentualDescontoSemRestabelecimentoInatividade;
	}

	public void setPercentualDescontoSemRestabelecimentoInatividade(String percentualDescontoSemRestabelecimentoInatividade){

		this.percentualDescontoSemRestabelecimentoInatividade = percentualDescontoSemRestabelecimentoInatividade;
	}

	public String getQuantidadeMaximaMesesInatividade(){

		return quantidadeMaximaMesesInatividade;
	}

	public void setQuantidadeMaximaMesesInatividade(String quantidadeMaximaMesesInatividade){

		this.quantidadeMaximaMesesInatividade = quantidadeMaximaMesesInatividade;
	}

	public String getQuantidadeMinimaMesesDebito(){

		return quantidadeMinimaMesesDebito;
	}

	public void setQuantidadeMinimaMesesDebito(String quantidadeMinimaMesesDebito){

		this.quantidadeMinimaMesesDebito = quantidadeMinimaMesesDebito;
	}

	public String getPercentualDescontoAtivo(){

		return percentualDescontoAtivo;
	}

	public void setPercentualDescontoAtivo(String percentualDescontoAtivo){

		this.percentualDescontoAtivo = percentualDescontoAtivo;
	}

	public String getPercentualTarifaMinimaPrestacao(){

		return percentualTarifaMinimaPrestacao;
	}

	public void setPercentualTarifaMinimaPrestacao(String percentualTarifaMinimaPrestacao){

		this.percentualTarifaMinimaPrestacao = percentualTarifaMinimaPrestacao;
	}

	public String getConsumoMinimo(){

		return consumoMinimo;
	}

	public void setConsumoMinimo(String consumoMinimo){

		this.consumoMinimo = consumoMinimo;
	}

	public String getPercentualVariacaoConsumoMedio(){

		return percentualVariacaoConsumoMedio;
	}

	public void setPercentualVariacaoConsumoMedio(String percentualVariacaoConsumoMedio){

		this.percentualVariacaoConsumoMedio = percentualVariacaoConsumoMedio;
	}

	public String getIndicadorParcelarChequeDevolvido(){

		return indicadorParcelarChequeDevolvido;
	}

	public void setIndicadorParcelarChequeDevolvido(String indicadorParcelarChequeDevolvido){

		this.indicadorParcelarChequeDevolvido = indicadorParcelarChequeDevolvido;
	}

	public String getIndicadorParcelarSancoesMaisDeUmaConta(){

		return indicadorParcelarSancoesMaisDeUmaConta;
	}

	public void setIndicadorParcelarSancoesMaisDeUmaConta(String indicadorParcelarSancoesMaisDeUmaConta){

		this.indicadorParcelarSancoesMaisDeUmaConta = indicadorParcelarSancoesMaisDeUmaConta;
	}

	public String getValorDebitoPrestacao(){

		return valorDebitoPrestacao;
	}

	public void setValorDebitoPrestacao(String valorDebitoPrestacao){

		this.valorDebitoPrestacao = valorDebitoPrestacao;
	}

	/**
	 * @return the valorMinimoDebitoAParcelarFaixaDebito
	 */
	public String getValorMinimoDebitoAParcelarFaixaDebito(){

		return valorMinimoDebitoAParcelarFaixaDebito;
	}

	/**
	 * @param valorMinimoDebitoAParcelarFaixaDebito
	 *            the valorMinimoDebitoAParcelarFaixaDebito to set
	 */
	public void setValorMinimoDebitoAParcelarFaixaDebito(String valorMinimoDebitoAParcelarFaixaDebito){

		this.valorMinimoDebitoAParcelarFaixaDebito = valorMinimoDebitoAParcelarFaixaDebito;
	}

	/**
	 * @return the valorMaximoDebitoAParcelarFaixaDebito
	 */
	public String getValorMaximoDebitoAParcelarFaixaDebito(){

		return valorMaximoDebitoAParcelarFaixaDebito;
	}

	/**
	 * @param valorMaximoDebitoAParcelarFaixaDebito
	 *            the valorMaximoDebitoAParcelarFaixaDebito to set
	 */
	public void setValorMaximoDebitoAParcelarFaixaDebito(String valorMaximoDebitoAParcelarFaixaDebito){

		this.valorMaximoDebitoAParcelarFaixaDebito = valorMaximoDebitoAParcelarFaixaDebito;
	}

	/**
	 * @return the indicadorDebitoOriginalOuAtualizadoFaixaDebito
	 */
	public String getIndicadorDebitoOriginalOuAtualizadoFaixaDebito(){

		return indicadorDebitoOriginalOuAtualizadoFaixaDebito;
	}

	/**
	 * @param indicadorDebitoOriginalOuAtualizadoFaixaDebito
	 *            the indicadorDebitoOriginalOuAtualizadoFaixaDebito to set
	 */
	public void setIndicadorDebitoOriginalOuAtualizadoFaixaDebito(String indicadorDebitoOriginalOuAtualizadoFaixaDebito){

		this.indicadorDebitoOriginalOuAtualizadoFaixaDebito = indicadorDebitoOriginalOuAtualizadoFaixaDebito;
	}

	/**
	 * @return the indicadorOpcoesDebitoOriginalOuAtualizadoPrestacaoMinima
	 */
	public String getIndicadorOpcoesDebitoOriginalOuAtualizadoPrestacaoMinima(){

		return indicadorOpcoesDebitoOriginalOuAtualizadoPrestacaoMinima;
	}

	/**
	 * @param indicadorOpcoesDebitoOriginalOuAtualizadoPrestacaoMinima
	 *            the indicadorOpcoesDebitoOriginalOuAtualizadoPrestacaoMinima to set
	 */
	public void setIndicadorOpcoesDebitoOriginalOuAtualizadoPrestacaoMinima(String indicadorOpcoesDebitoOriginalOuAtualizadoPrestacaoMinima){

		this.indicadorOpcoesDebitoOriginalOuAtualizadoPrestacaoMinima = indicadorOpcoesDebitoOriginalOuAtualizadoPrestacaoMinima;
	}

	/**
	 * @return the percentualValorDebitoCalculoValorMinimoPrestacao
	 */
	public String getPercentualValorDebitoCalculoValorMinimoPrestacao(){

		return percentualValorDebitoCalculoValorMinimoPrestacao;
	}

	/**
	 * @param percentualValorDebitoCalculoValorMinimoPrestacao
	 *            the percentualValorDebitoCalculoValorMinimoPrestacao to set
	 */
	public void setPercentualValorDebitoCalculoValorMinimoPrestacao(String percentualValorDebitoCalculoValorMinimoPrestacao){

		this.percentualValorDebitoCalculoValorMinimoPrestacao = percentualValorDebitoCalculoValorMinimoPrestacao;
	}

	/**
	 * @return the quantidadeMaximaPrestacao
	 */
	public String getQuantidadeMaximaPrestacao(){

		return quantidadeMaximaPrestacao;
	}

	/**
	 * @param quantidadeMaximaPrestacao
	 *            the quantidadeMaximaPrestacao to set
	 */
	public void setQuantidadeMaximaPrestacao(String quantidadeMaximaPrestacao){

		this.quantidadeMaximaPrestacao = quantidadeMaximaPrestacao;
	}

	/**
	 * @return the taxaJuros
	 */
	public String getTaxaJuros(){

		return taxaJuros;
	}

	/**
	 * @param taxaJuros
	 *            the taxaJuros to set
	 */
	public void setTaxaJuros(String taxaJuros){

		this.taxaJuros = taxaJuros;
	}

	/**
	 * @return the percentualMinimoEntrada
	 */
	public String getPercentualMinimoEntrada(){

		return percentualMinimoEntrada;
	}

	/**
	 * @param percentualMinimoEntrada
	 *            the percentualMinimoEntrada to set
	 */
	public void setPercentualMinimoEntrada(String percentualMinimoEntrada){

		this.percentualMinimoEntrada = percentualMinimoEntrada;
	}

	/**
	 * @return the percentualTarifaMinimaImovel
	 */
	public String getPercentualTarifaMinimaImovel(){

		return percentualTarifaMinimaImovel;
	}

	/**
	 * @param percentualTarifaMinimaImovel
	 *            the percentualTarifaMinimaImovel to set
	 */
	public void setPercentualTarifaMinimaImovel(String percentualTarifaMinimaImovel){

		this.percentualTarifaMinimaImovel = percentualTarifaMinimaImovel;
	}

	/**
	 * @return the percentualValorReparcelado
	 */
	public String getPercentualValorReparcelado(){

		return percentualValorReparcelado;
	}

	/**
	 * @param percentualValorReparcelado
	 *            the percentualValorReparcelado to set
	 */
	public void setPercentualValorReparcelado(String percentualValorReparcelado){

		this.percentualValorReparcelado = percentualValorReparcelado;
	}

	/**
	 * @return the valorMaxPercFaixaValor
	 */
	public String getValorMaxPercFaixaValor(){

		return valorMaxPercFaixaValor;
	}

	/**
	 * @param valorMaxPercFaixaValor
	 *            the valorMaxPercFaixaValor to set
	 */
	public void setValorMaxPercFaixaValor(String valorMaxPercFaixaValor){

		this.valorMaxPercFaixaValor = valorMaxPercFaixaValor;
	}

	/**
	 * @return the percentualPercFaixaValor
	 */
	public String getPercentualPercFaixaValor(){

		return percentualPercFaixaValor;
	}

	/**
	 * @param percentualPercFaixaValor
	 *            the percentualPercFaixaValor to set
	 */
	public void setPercentualPercFaixaValor(String percentualPercFaixaValor){

		this.percentualPercFaixaValor = percentualPercFaixaValor;
	}

	/**
	 * @return the quantidadeMaxPrestacaoEspecial
	 */
	public String getQuantidadeMaxPrestacaoEspecial(){

		return quantidadeMaxPrestacaoEspecial;
	}

	/**
	 * @param quantidadeMaxPrestacaoEspecial
	 *            the quantidadeMaxPrestacaoEspecial to set
	 */
	public void setQuantidadeMaxPrestacaoEspecial(String quantidadeMaxPrestacaoEspecial){

		this.quantidadeMaxPrestacaoEspecial = quantidadeMaxPrestacaoEspecial;
	}

	/**
	 * @return the valorFixoEntrada
	 */
	public String getValorFixoEntrada(){

		return valorFixoEntrada;
	}

	/**
	 * @param valorFixoEntrada
	 *            the valorFixoEntrada to set
	 */
	public void setValorFixoEntrada(String valorFixoEntrada){

		this.valorFixoEntrada = valorFixoEntrada;
	}

	/**
	 * @return the indicadorOpcoesEntradaParcelamento
	 */
	public String getIndicadorOpcoesEntradaParcelamento(){

		return indicadorOpcoesEntradaParcelamento;
	}

	/**
	 * @param indicadorOpcoesEntradaParcelamento
	 *            the indicadorOpcoesEntradaParcelamento to set
	 */
	public void setIndicadorOpcoesEntradaParcelamento(String indicadorOpcoesEntradaParcelamento){

		this.indicadorOpcoesEntradaParcelamento = indicadorOpcoesEntradaParcelamento;
	}

	/**
	 * @return the indicadorOpcoesPercentualFaixaValor
	 */
	public String getIndicadorOpcoesPercentualFaixaValor(){

		return indicadorOpcoesPercentualFaixaValor;
	}

	/**
	 * @param indicadorOpcoesPercentualFaixaValor
	 *            the indicadorOpcoesPercentualFaixaValor to set
	 */
	public void setIndicadorOpcoesPercentualFaixaValor(String indicadorOpcoesPercentualFaixaValor){

		this.indicadorOpcoesPercentualFaixaValor = indicadorOpcoesPercentualFaixaValor;
	}

	/**
	 * @return the valorMinimoEntrada
	 */
	public String getValorMinimoEntrada(){

		return valorMinimoEntrada;
	}

	/**
	 * @param valorMinimoEntrada
	 *            the valorMinimoEntrada to set
	 */
	public void setValorMinimoEntrada(String valorMinimoEntrada){

		this.valorMinimoEntrada = valorMinimoEntrada;
	}

	public String getIndicadorCobrarAcrescimosImpontualidade(){

		return indicadorCobrarAcrescimosImpontualidade;
	}

	public void setIndicadorCobrarAcrescimosImpontualidade(String indicadorCobrarAcrescimosImpontualidade){

		this.indicadorCobrarAcrescimosImpontualidade = indicadorCobrarAcrescimosImpontualidade;
	}

	public String getIndicadorPermitirCobrancaParcelamentoPorGuia(){

		return indicadorPermitirCobrancaParcelamentoPorGuia;
	}

	public void setIndicadorPermitirCobrancaParcelamentoPorGuia(String indicadorPermitirCobrancaParcelamentoPorGuia){

		this.indicadorPermitirCobrancaParcelamentoPorGuia = indicadorPermitirCobrancaParcelamentoPorGuia;
	}

	public String getIndicadorPermitirInformarNumeroValorParcelas(){

		return indicadorPermitirInformarNumeroValorParcelas;
	}

	public void setIndicadorPermitirInformarNumeroValorParcelas(String indicadorPermitirInformarNumeroValorParcelas){

		this.indicadorPermitirInformarNumeroValorParcelas = indicadorPermitirInformarNumeroValorParcelas;
	}

	public String getIndicadorCobrarMultaPorDescumprimentoPrestacao(){

		return indicadorCobrarMultaPorDescumprimentoPrestacao;
	}

	public void setIndicadorCobrarMultaPorDescumprimentoPrestacao(String indicadorCobrarMultaPorDescumprimentoPrestacao){

		this.indicadorCobrarMultaPorDescumprimentoPrestacao = indicadorCobrarMultaPorDescumprimentoPrestacao;
	}

	public String getNumeroPrestacacoesDescumpridasConsecutivas(){

		return numeroPrestacacoesDescumpridasConsecutivas;
	}

	public void setNumeroPrestacacoesDescumpridasConsecutivas(String numeroPrestacacoesDescumpridasConsecutivas){

		this.numeroPrestacacoesDescumpridasConsecutivas = numeroPrestacacoesDescumpridasConsecutivas;
	}

	public String getPercentualMultaPorPrestacacoesDescumpridas(){

		return percentualMultaPorPrestacacoesDescumpridas;
	}

	public void setPercentualMultaPorPrestacacoesDescumpridas(String percentualMultaPorPrestacacoesDescumpridas){

		this.percentualMultaPorPrestacacoesDescumpridas = percentualMultaPorPrestacacoesDescumpridas;
	}

	public String getPercentualDescontoJurosMoraSemRestabelecimentoInatividade(){

		return percentualDescontoJurosMoraSemRestabelecimentoInatividade;
	}

	public void setPercentualDescontoJurosMoraSemRestabelecimentoInatividade(
					String percentualDescontoJurosMoraSemRestabelecimentoInatividade){

		this.percentualDescontoJurosMoraSemRestabelecimentoInatividade = percentualDescontoJurosMoraSemRestabelecimentoInatividade;
	}

	public String getPercentualDescontoMultaSemRestabelecimentoInatividade(){

		return percentualDescontoMultaSemRestabelecimentoInatividade;
	}

	public void setPercentualDescontoMultaSemRestabelecimentoInatividade(String percentualDescontoMultaSemRestabelecimentoInatividade){

		this.percentualDescontoMultaSemRestabelecimentoInatividade = percentualDescontoMultaSemRestabelecimentoInatividade;
	}

	public String getPercentualDescontoJurosMoraComRestabelecimentoInatividade(){

		return percentualDescontoJurosMoraComRestabelecimentoInatividade;
	}

	public void setPercentualDescontoJurosMoraComRestabelecimentoInatividade(
					String percentualDescontoJurosMoraComRestabelecimentoInatividade){

		this.percentualDescontoJurosMoraComRestabelecimentoInatividade = percentualDescontoJurosMoraComRestabelecimentoInatividade;
	}

	public String getPercentualDescontoMultaComRestabelecimentoInatividade(){

		return percentualDescontoMultaComRestabelecimentoInatividade;
	}

	public void setPercentualDescontoMultaComRestabelecimentoInatividade(String percentualDescontoMultaComRestabelecimentoInatividade){

		this.percentualDescontoMultaComRestabelecimentoInatividade = percentualDescontoMultaComRestabelecimentoInatividade;
	}

	public String getNumeroConsumoEconomia(){

		return numeroConsumoEconomia;
	}

	public void setNumeroConsumoEconomia(String numeroConsumoEconomia){

		this.numeroConsumoEconomia = numeroConsumoEconomia;
	}

	public String getNumeroAreaConstruida(){

		return numeroAreaConstruida;
	}

	public void setNumeroAreaConstruida(String numeroAreaConstruida){

		this.numeroAreaConstruida = numeroAreaConstruida;
	}

	public String getAnoMesReferenciaLimiteInferior(){

		return anoMesReferenciaLimiteInferior;
	}

	public void setAnoMesReferenciaLimiteInferior(String anoMesReferenciaLimiteInferior){

		this.anoMesReferenciaLimiteInferior = anoMesReferenciaLimiteInferior;
	}

	public String getAnoMesReferenciaLimiteSuperior(){

		return anoMesReferenciaLimiteSuperior;
	}

	public void setAnoMesReferenciaLimiteSuperior(String anoMesReferenciaLimiteSuperior){

		this.anoMesReferenciaLimiteSuperior = anoMesReferenciaLimiteSuperior;
	}

	public String getPercentualDescontoAVista(){

		return percentualDescontoAVista;
	}

	public void setPercentualDescontoAVista(String percentualDescontoAVista){

		this.percentualDescontoAVista = percentualDescontoAVista;
	}

	public String getParcelaQuantidadeMinimaFatura(){

		return parcelaQuantidadeMinimaFatura;
	}

	public void setParcelaQuantidadeMinimaFatura(String parcelaQuantidadeMinimaFatura){

		this.parcelaQuantidadeMinimaFatura = parcelaQuantidadeMinimaFatura;
	}

	public String getPercentualDescontoSancao(){

		return percentualDescontoSancao;
	}

	public void setPercentualDescontoSancao(String percentualDescontoSancao){

		this.percentualDescontoSancao = percentualDescontoSancao;
	}

	public String getQuantidadeEconomias(){

		return quantidadeEconomias;
	}

	public void setQuantidadeEconomias(String quantidadeEconomias){

		this.quantidadeEconomias = quantidadeEconomias;
	}

	public String getCapacidadeHidrometro(){

		return capacidadeHidrometro;
	}

	public void setCapacidadeHidrometro(String capacidadeHidrometro){

		this.capacidadeHidrometro = capacidadeHidrometro;
	}

	public String getPercentualEntradaSugerida(){

		return percentualEntradaSugerida;
	}

	public void setPercentualEntradaSugerida(String percentualEntradaSugerida){

		this.percentualEntradaSugerida = percentualEntradaSugerida;
	}

}
