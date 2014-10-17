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

import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.imovel.ImovelPerfil;
import gcom.cadastro.imovel.ImovelSituacaoTipo;
import gcom.cadastro.imovel.Subcategoria;
import gcom.cobranca.ResolucaoDiretoria;
import gcom.interceptor.ControleAlteracao;
import gcom.interceptor.ObjetoTransacao;
import gcom.util.filtro.Filtro;
import gcom.util.filtro.ParametroSimples;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
@ControleAlteracao()
public class ParcelamentoPerfil
				extends ObjetoTransacao {

	public static final int ATRIBUTOS_PERFIL_PARCELAMENTO_ATUALIZAR = 83; // Operacao.OPERACAO_PERFIL_PARCELAMENTO_ATUALIZAR

	public static final int ATRIBUTOS_PERFIL_PARCELAMENTO_REMOVER = 84; // Operacao.OPERACAO_PERFIL_PARCELAMENTO_REMOVER

	private static final long serialVersionUID = 1L;

	public static final Short INDICADOR_DEBITO_ORIGINAL = 1;

	public static final Short INDICADOR_DEBITO_ATUALIZADO = 2;

	/** identifier field */
	private Integer id;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_PERFIL_PARCELAMENTO_ATUALIZAR, ATRIBUTOS_PERFIL_PARCELAMENTO_REMOVER})
	private BigDecimal percentualDescontoAcrescimo;

	/** nullable persistent field */
	private Date ultimaAlteracao;

	/** persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_PERFIL_PARCELAMENTO_ATUALIZAR, ATRIBUTOS_PERFIL_PARCELAMENTO_REMOVER})
	private Subcategoria subcategoria;

	/** persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_PERFIL_PARCELAMENTO_REMOVER})
	private ImovelSituacaoTipo imovelSituacaoTipo;

	/** persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_PERFIL_PARCELAMENTO_ATUALIZAR, ATRIBUTOS_PERFIL_PARCELAMENTO_REMOVER})
	private ImovelPerfil imovelPerfil;

	/** persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_PERFIL_PARCELAMENTO_ATUALIZAR, ATRIBUTOS_PERFIL_PARCELAMENTO_REMOVER})
	private ResolucaoDiretoria resolucaoDiretoria;

	/** persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_PERFIL_PARCELAMENTO_ATUALIZAR, ATRIBUTOS_PERFIL_PARCELAMENTO_REMOVER})
	private BigDecimal percentualTarifaMinimaPrestacao;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_PERFIL_PARCELAMENTO_ATUALIZAR, ATRIBUTOS_PERFIL_PARCELAMENTO_REMOVER})
	private BigDecimal valorDebitoPrestacao;

	/** persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_PERFIL_PARCELAMENTO_ATUALIZAR, ATRIBUTOS_PERFIL_PARCELAMENTO_REMOVER})
	private Integer numeroConsumoMinimo;

	/** persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_PERFIL_PARCELAMENTO_ATUALIZAR, ATRIBUTOS_PERFIL_PARCELAMENTO_REMOVER})
	private BigDecimal percentualVariacaoConsumoMedio;

	/** persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_PERFIL_PARCELAMENTO_ATUALIZAR, ATRIBUTOS_PERFIL_PARCELAMENTO_REMOVER})
	private Short indicadorChequeDevolvido;

	/** persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_PERFIL_PARCELAMENTO_ATUALIZAR, ATRIBUTOS_PERFIL_PARCELAMENTO_REMOVER})
	private Short indicadorSancoesUnicaConta;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_PERFIL_PARCELAMENTO_ATUALIZAR, ATRIBUTOS_PERFIL_PARCELAMENTO_REMOVER})
	private Short indicadorRetroativoTarifaSocial;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_PERFIL_PARCELAMENTO_ATUALIZAR, ATRIBUTOS_PERFIL_PARCELAMENTO_REMOVER})
	private Integer anoMesReferenciaLimiteInferior;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_PERFIL_PARCELAMENTO_ATUALIZAR, ATRIBUTOS_PERFIL_PARCELAMENTO_REMOVER})
	private Integer anoMesReferenciaLimiteSuperior;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_PERFIL_PARCELAMENTO_ATUALIZAR, ATRIBUTOS_PERFIL_PARCELAMENTO_REMOVER})
	private BigDecimal percentualDescontoAVista;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_PERFIL_PARCELAMENTO_ATUALIZAR, ATRIBUTOS_PERFIL_PARCELAMENTO_REMOVER})
	private Integer parcelaQuantidadeMinimaFatura;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_PERFIL_PARCELAMENTO_ATUALIZAR, ATRIBUTOS_PERFIL_PARCELAMENTO_REMOVER})
	private Short indicadorAlertaParcelaMinima;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_PERFIL_PARCELAMENTO_ATUALIZAR, ATRIBUTOS_PERFIL_PARCELAMENTO_REMOVER})
	private Integer numeroConsumoEconomia;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_PERFIL_PARCELAMENTO_ATUALIZAR, ATRIBUTOS_PERFIL_PARCELAMENTO_REMOVER})
	private BigDecimal numeroAreaConstruida;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_PERFIL_PARCELAMENTO_ATUALIZAR, ATRIBUTOS_PERFIL_PARCELAMENTO_REMOVER})
	private Categoria categoria;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_PERFIL_PARCELAMENTO_ATUALIZAR, ATRIBUTOS_PERFIL_PARCELAMENTO_REMOVER})
	private BigDecimal percentualDescontoSancao;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_PERFIL_PARCELAMENTO_ATUALIZAR, ATRIBUTOS_PERFIL_PARCELAMENTO_REMOVER})
	private Integer quantidadeEconomias;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_PERFIL_PARCELAMENTO_ATUALIZAR, ATRIBUTOS_PERFIL_PARCELAMENTO_REMOVER})
	private Integer capacidadeHidrometro;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_PERFIL_PARCELAMENTO_ATUALIZAR, ATRIBUTOS_PERFIL_PARCELAMENTO_REMOVER})
	private BigDecimal valorMinimoDebitoAParcelarFaixaDebito;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_PERFIL_PARCELAMENTO_ATUALIZAR, ATRIBUTOS_PERFIL_PARCELAMENTO_REMOVER})
	private BigDecimal valorMaximoDebitoAParcelarFaixaDebito;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_PERFIL_PARCELAMENTO_ATUALIZAR, ATRIBUTOS_PERFIL_PARCELAMENTO_REMOVER})
	private Short indicadorDebitoOriginalOuAtualizadoFaixaDebito;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_PERFIL_PARCELAMENTO_ATUALIZAR, ATRIBUTOS_PERFIL_PARCELAMENTO_REMOVER})
	private Short indicadorOpcoesDebitoOriginalOuAtualizadoPrestacaoMinima;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_PERFIL_PARCELAMENTO_ATUALIZAR, ATRIBUTOS_PERFIL_PARCELAMENTO_REMOVER})
	private BigDecimal percentualValorDebitoCalculoValorMinimoPrestacao;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_PERFIL_PARCELAMENTO_ATUALIZAR, ATRIBUTOS_PERFIL_PARCELAMENTO_REMOVER})
	private Short indicadorCobrarAcrescimosPorImpontualidades;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_PERFIL_PARCELAMENTO_ATUALIZAR, ATRIBUTOS_PERFIL_PARCELAMENTO_REMOVER})
	private Short indicadorPermiteParcelamentoPorGuiaPagamento;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_PERFIL_PARCELAMENTO_ATUALIZAR, ATRIBUTOS_PERFIL_PARCELAMENTO_REMOVER})
	private Integer numeroPretacoesDescumpridasParaCobrancaMulta;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_PERFIL_PARCELAMENTO_ATUALIZAR, ATRIBUTOS_PERFIL_PARCELAMENTO_REMOVER})
	private BigDecimal percentualMultaPrestacaoDescumprida;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_PERFIL_PARCELAMENTO_ATUALIZAR, ATRIBUTOS_PERFIL_PARCELAMENTO_REMOVER})
	private Short indicadorPermiteInformarNumeroValorParcela;

	/**
	 * @return Retorna o campo capacidadeHidrometro.
	 */
	public Integer getCapacidadeHidrometro(){

		return capacidadeHidrometro;
	}

	/**
	 * @param capacidadeHidrometro
	 *            O capacidadeHidrometro a ser setado.
	 */
	public void setCapacidadeHidrometro(Integer capacidadeHidrometro){

		this.capacidadeHidrometro = capacidadeHidrometro;
	}

	/**
	 * @return Retorna o campo quantidadeEconomias.
	 */
	public Integer getQuantidadeEconomias(){

		return quantidadeEconomias;
	}

	/**
	 * @param quantidadeEconomias
	 *            O quantidadeEconomias a ser setado.
	 */
	public void setQuantidadeEconomias(Integer quantidadeEconomias){

		this.quantidadeEconomias = quantidadeEconomias;
	}

	/**
	 * @return Retorna o campo percentualTarifaMinimaPrestacao.
	 */
	public BigDecimal getPercentualTarifaMinimaPrestacao(){

		return percentualTarifaMinimaPrestacao;
	}

	/**
	 * @param percentualTarifaMinimaPrestacao
	 *            O percentualTarifaMinimaPrestacao a ser setado.
	 */
	public void setPercentualTarifaMinimaPrestacao(BigDecimal percentualTarifaMinimaPrestacao){

		this.percentualTarifaMinimaPrestacao = percentualTarifaMinimaPrestacao;
	}

	public BigDecimal getValorDebitoPrestacao(){

		return valorDebitoPrestacao;
	}

	public void setValorDebitoPrestacao(BigDecimal valorDebitoPrestacao){

		this.valorDebitoPrestacao = valorDebitoPrestacao;
	}

	public String[] retornaCamposChavePrimaria(){

		String[] retorno = new String[1];
		retorno[0] = "id";
		return retorno;
	}

	public Filtro retornaFiltro(){

		FiltroParcelamentoPerfil filtroParcelamentoPerfil = new FiltroParcelamentoPerfil();

		filtroParcelamentoPerfil.adicionarCaminhoParaCarregamentoEntidade("subcategoria");
		filtroParcelamentoPerfil.adicionarCaminhoParaCarregamentoEntidade("imovelSituacaoTipo");
		filtroParcelamentoPerfil.adicionarCaminhoParaCarregamentoEntidade("imovelPerfil");
		filtroParcelamentoPerfil.adicionarCaminhoParaCarregamentoEntidade("resolucaoDiretoria");
		filtroParcelamentoPerfil.adicionarCaminhoParaCarregamentoEntidade("resolucaoDiretoria.resolucaoDiretoriaLayout");
		filtroParcelamentoPerfil.adicionarCaminhoParaCarregamentoEntidade("categoria");
		filtroParcelamentoPerfil.adicionarParametro(new ParametroSimples(FiltroParcelamentoPerfil.ID, this.getId()));
		return filtroParcelamentoPerfil;
	}

	/** full constructor */
	public ParcelamentoPerfil(BigDecimal percentualDescontoAcrescimo, Date ultimaAlteracao, Subcategoria subcategoria,
								ImovelSituacaoTipo imovelSituacaoTipo, ImovelPerfil imovelPerfil, ResolucaoDiretoria resolucaoDiretoria) {

		this.percentualDescontoAcrescimo = percentualDescontoAcrescimo;
		this.ultimaAlteracao = ultimaAlteracao;
		this.subcategoria = subcategoria;
		this.imovelSituacaoTipo = imovelSituacaoTipo;
		this.imovelPerfil = imovelPerfil;
		this.resolucaoDiretoria = resolucaoDiretoria;
	}

	/** default constructor */
	public ParcelamentoPerfil() {

	}

	/** minimal constructor */
	public ParcelamentoPerfil(Subcategoria subcategoria, ImovelSituacaoTipo imovelSituacaoTipo, ImovelPerfil imovelPerfil,
								ResolucaoDiretoria resolucaoDiretoria) {

		this.subcategoria = subcategoria;
		this.imovelSituacaoTipo = imovelSituacaoTipo;
		this.imovelPerfil = imovelPerfil;
		this.resolucaoDiretoria = resolucaoDiretoria;
	}

	public Integer getId(){

		return this.id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public BigDecimal getPercentualDescontoAcrescimo(){

		return this.percentualDescontoAcrescimo;
	}

	public void setPercentualDescontoAcrescimo(BigDecimal percentualDescontoAcrescimo){

		this.percentualDescontoAcrescimo = percentualDescontoAcrescimo;
	}

	public Date getUltimaAlteracao(){

		return this.ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public Subcategoria getSubcategoria(){

		return this.subcategoria;
	}

	public void setSubcategoria(Subcategoria subcategoria){

		this.subcategoria = subcategoria;
	}

	public ImovelSituacaoTipo getImovelSituacaoTipo(){

		return this.imovelSituacaoTipo;
	}

	public void setImovelSituacaoTipo(ImovelSituacaoTipo imovelSituacaoTipo){

		this.imovelSituacaoTipo = imovelSituacaoTipo;
	}

	public ImovelPerfil getImovelPerfil(){

		return this.imovelPerfil;
	}

	public void setImovelPerfil(ImovelPerfil imovelPerfil){

		this.imovelPerfil = imovelPerfil;
	}

	public ResolucaoDiretoria getResolucaoDiretoria(){

		return this.resolucaoDiretoria;
	}

	public void setResolucaoDiretoria(ResolucaoDiretoria resolucaoDiretoria){

		this.resolucaoDiretoria = resolucaoDiretoria;
	}

	public String toString(){

		return new ToStringBuilder(this).append("id", getId()).toString();
	}

	public Short getIndicadorChequeDevolvido(){

		return indicadorChequeDevolvido;
	}

	public void setIndicadorChequeDevolvido(Short indicadorChequeDevolvido){

		this.indicadorChequeDevolvido = indicadorChequeDevolvido;
	}

	public Short getIndicadorSancoesUnicaConta(){

		return indicadorSancoesUnicaConta;
	}

	public void setIndicadorSancoesUnicaConta(Short indicadorSancoesUnicaConta){

		this.indicadorSancoesUnicaConta = indicadorSancoesUnicaConta;
	}

	public Integer getNumeroConsumoMinimo(){

		return numeroConsumoMinimo;
	}

	public void setNumeroConsumoMinimo(Integer numeroConsumoMinimo){

		this.numeroConsumoMinimo = numeroConsumoMinimo;
	}

	public BigDecimal getPercentualVariacaoConsumoMedio(){

		return percentualVariacaoConsumoMedio;
	}

	public void setPercentualVariacaoConsumoMedio(BigDecimal percentualVariacaoConsumoMedio){

		this.percentualVariacaoConsumoMedio = percentualVariacaoConsumoMedio;
	}

	public Categoria getCategoria(){

		return categoria;
	}

	public void setCategoria(Categoria categoria){

		this.categoria = categoria;
	}

	public BigDecimal getNumeroAreaConstruida(){

		return numeroAreaConstruida;
	}

	public void setNumeroAreaConstruida(BigDecimal numeroAreaConstruida){

		this.numeroAreaConstruida = numeroAreaConstruida;
	}

	public Integer getNumeroConsumoEconomia(){

		return numeroConsumoEconomia;
	}

	public void setNumeroConsumoEconomia(Integer numeroConsumoEconomia){

		this.numeroConsumoEconomia = numeroConsumoEconomia;
	}

	public Integer getAnoMesReferenciaLimiteInferior(){

		return anoMesReferenciaLimiteInferior;
	}

	public void setAnoMesReferenciaLimiteInferior(Integer anoMesReferenciaLimiteInferior){

		this.anoMesReferenciaLimiteInferior = anoMesReferenciaLimiteInferior;
	}

	public Integer getAnoMesReferenciaLimiteSuperior(){

		return anoMesReferenciaLimiteSuperior;
	}

	public void setAnoMesReferenciaLimiteSuperior(Integer anoMesReferenciaLimiteSuperior){

		this.anoMesReferenciaLimiteSuperior = anoMesReferenciaLimiteSuperior;
	}

	public Short getIndicadorAlertaParcelaMinima(){

		return indicadorAlertaParcelaMinima;
	}

	public void setIndicadorAlertaParcelaMinima(Short indicadorAlertaParcelaMinima){

		this.indicadorAlertaParcelaMinima = indicadorAlertaParcelaMinima;
	}

	public Short getIndicadorRetroativoTarifaSocial(){

		return indicadorRetroativoTarifaSocial;
	}

	public void setIndicadorRetroativoTarifaSocial(Short indicadorRetroativoTarifaSocial){

		this.indicadorRetroativoTarifaSocial = indicadorRetroativoTarifaSocial;
	}

	public Integer getParcelaQuantidadeMinimaFatura(){

		return parcelaQuantidadeMinimaFatura;
	}

	public void setParcelaQuantidadeMinimaFatura(Integer parcelaQuantidadeMinimaFatura){

		this.parcelaQuantidadeMinimaFatura = parcelaQuantidadeMinimaFatura;
	}

	/**
	 * @return Retorna o campo percentualDescontoAVista.
	 */
	public BigDecimal getPercentualDescontoAVista(){

		return percentualDescontoAVista;
	}

	/**
	 * @param percentualDescontoAVista
	 *            O percentualDescontoAVista a ser setado.
	 */
	public void setPercentualDescontoAVista(BigDecimal percentualDescontoAVista){

		this.percentualDescontoAVista = percentualDescontoAVista;
	}

	/**
	 * @return Retorna o campo percentualDescontoSancao.
	 */
	public BigDecimal getPercentualDescontoSancao(){

		return percentualDescontoSancao;
	}

	/**
	 * @param percentualDescontoSancao
	 *            O percentualDescontoSancao a ser setado.
	 */
	public void setPercentualDescontoSancao(BigDecimal percentualDescontoSancao){

		this.percentualDescontoSancao = percentualDescontoSancao;
	}

	/**
	 * @return the valorMinimoDebitoAParcelarFaixaDebito
	 */
	public BigDecimal getValorMinimoDebitoAParcelarFaixaDebito(){

		return valorMinimoDebitoAParcelarFaixaDebito;
	}

	/**
	 * @param valorMinimoDebitoAParcelarFaixaDebito
	 *            the valorMinimoDebitoAParcelarFaixaDebito to set
	 */
	public void setValorMinimoDebitoAParcelarFaixaDebito(BigDecimal valorMinimoDebitoAParcelarFaixaDebito){

		this.valorMinimoDebitoAParcelarFaixaDebito = valorMinimoDebitoAParcelarFaixaDebito;
	}

	/**
	 * @return the valorMaximoDebitoAParcelarFaixaDebito
	 */
	public BigDecimal getValorMaximoDebitoAParcelarFaixaDebito(){

		return valorMaximoDebitoAParcelarFaixaDebito;
	}

	/**
	 * @param valorMaximoDebitoAParcelarFaixaDebito
	 *            the valorMaximoDebitoAParcelarFaixaDebito to set
	 */
	public void setValorMaximoDebitoAParcelarFaixaDebito(BigDecimal valorMaximoDebitoAParcelarFaixaDebito){

		this.valorMaximoDebitoAParcelarFaixaDebito = valorMaximoDebitoAParcelarFaixaDebito;
	}

	/**
	 * @return the indicadorDebitoOriginalOuAtualizadoFaixaDebito
	 */
	public Short getIndicadorDebitoOriginalOuAtualizadoFaixaDebito(){

		return indicadorDebitoOriginalOuAtualizadoFaixaDebito;
	}

	/**
	 * @param indicadorDebitoOriginalOuAtualizadoFaixaDebito
	 *            the indicadorDebitoOriginalOuAtualizadoFaixaDebito to set
	 */
	public void setIndicadorDebitoOriginalOuAtualizadoFaixaDebito(Short indicadorDebitoOriginalOuAtualizadoFaixaDebito){

		this.indicadorDebitoOriginalOuAtualizadoFaixaDebito = indicadorDebitoOriginalOuAtualizadoFaixaDebito;
	}

	/**
	 * @return the indicadorOpcoesDebitoOriginalOuAtualizadoPrestacaoMinima
	 */
	public Short getIndicadorOpcoesDebitoOriginalOuAtualizadoPrestacaoMinima(){

		return indicadorOpcoesDebitoOriginalOuAtualizadoPrestacaoMinima;
	}

	/**
	 * @param indicadorOpcoesDebitoOriginalOuAtualizadoPrestacaoMinima
	 *            the indicadorOpcoesDebitoOriginalOuAtualizadoPrestacaoMinima to set
	 */
	public void setIndicadorOpcoesDebitoOriginalOuAtualizadoPrestacaoMinima(Short indicadorOpcoesDebitoOriginalOuAtualizadoPrestacaoMinima){

		this.indicadorOpcoesDebitoOriginalOuAtualizadoPrestacaoMinima = indicadorOpcoesDebitoOriginalOuAtualizadoPrestacaoMinima;
	}

	/**
	 * @return the percentualValorDebitoCalculoValorMinimoPrestacao
	 */
	public BigDecimal getPercentualValorDebitoCalculoValorMinimoPrestacao(){

		return percentualValorDebitoCalculoValorMinimoPrestacao;
	}

	/**
	 * @param percentualValorDebitoCalculoValorMinimoPrestacao
	 *            the percentualValorDebitoCalculoValorMinimoPrestacao to set
	 */
	public void setPercentualValorDebitoCalculoValorMinimoPrestacao(BigDecimal percentualValorDebitoCalculoValorMinimoPrestacao){

		this.percentualValorDebitoCalculoValorMinimoPrestacao = percentualValorDebitoCalculoValorMinimoPrestacao;
	}

	public Short getIndicadorCobrarAcrescimosPorImpontualidades(){

		return indicadorCobrarAcrescimosPorImpontualidades;
	}

	public void setIndicadorCobrarAcrescimosPorImpontualidades(Short indicadorCobrarAcrescimosPorImpontualidades){

		this.indicadorCobrarAcrescimosPorImpontualidades = indicadorCobrarAcrescimosPorImpontualidades;
	}

	public Short getIndicadorPermiteParcelamentoPorGuiaPagamento(){

		return indicadorPermiteParcelamentoPorGuiaPagamento;
	}

	public void setIndicadorPermiteParcelamentoPorGuiaPagamento(Short indicadorPermiteParcelamentoPorGuiaPagamento){

		this.indicadorPermiteParcelamentoPorGuiaPagamento = indicadorPermiteParcelamentoPorGuiaPagamento;
	}

	public Integer getNumeroPretacoesDescumpridasParaCobrancaMulta(){

		return numeroPretacoesDescumpridasParaCobrancaMulta;
	}

	public void setNumeroPretacoesDescumpridasParaCobrancaMulta(Integer numeroPretacoesDescumpridasParaCobrancaMulta){

		this.numeroPretacoesDescumpridasParaCobrancaMulta = numeroPretacoesDescumpridasParaCobrancaMulta;
	}

	public BigDecimal getPercentualMultaPrestacaoDescumprida(){

		return percentualMultaPrestacaoDescumprida;
	}

	public void setPercentualMultaPrestacaoDescumprida(BigDecimal percentualMultaPrestacaoDescumprida){

		this.percentualMultaPrestacaoDescumprida = percentualMultaPrestacaoDescumprida;
	}

	public Short getIndicadorPermiteInformarNumeroValorParcela(){

		return indicadorPermiteInformarNumeroValorParcela;
	}

	public void setIndicadorPermiteInformarNumeroValorParcela(Short indicadorPermiteInformarNumeroValorParcela){

		this.indicadorPermiteInformarNumeroValorParcela = indicadorPermiteInformarNumeroValorParcela;
	}

	public String getIndicadorPermiteInformarNumeroValorParcelaString(){

		String retorno = "";

		if(indicadorPermiteInformarNumeroValorParcela != null){

			retorno = indicadorPermiteInformarNumeroValorParcela.toString();
		}

		return retorno;
	}


}
