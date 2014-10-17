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

package gcom.faturamento.debito;

import gcom.atendimentopublico.ordemservico.OcorrenciaInfracaoItem;
import gcom.atendimentopublico.ordemservico.OrdemServico;
import gcom.atendimentopublico.registroatendimento.RegistroAtendimento;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.Quadra;
import gcom.cobranca.CobrancaForma;
import gcom.cobranca.DocumentoTipo;
import gcom.cobranca.ParcelamentoGrupo;
import gcom.cobranca.parcelamento.Parcelamento;
import gcom.financeiro.FinanciamentoTipo;
import gcom.financeiro.lancamento.LancamentoItemContabil;
import gcom.interceptor.ControleAlteracao;
import gcom.interceptor.ObjetoTransacao;
import gcom.util.filtro.Filtro;
import gcom.util.filtro.ParametroSimples;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */

@ControleAlteracao()
public class DebitoACobrar
				extends ObjetoTransacao {

	private static final long serialVersionUID = 1L;

	/** INSERIR DEBITO A COBRAR */
	// Operacao.OPERACAO_DEBITO_A_COBRAR_INSERIR;
	public static final int ATRIBUTOS_DEBITO_A_COBRAR_INSERIR = 70;

	/** CANCELAR DEBITO A COBRAR */
	// Operacao.OPERACAO_DEBITO_A_COBRAR_CANCELAR;
	public static final int ATRIBUTOS_DEBITO_A_COBRAR_CANCELAR = 387;

	/** identifier field */
	private Integer id;

	/** persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_DEBITO_A_COBRAR_INSERIR, ATRIBUTOS_DEBITO_A_COBRAR_CANCELAR})
	private Date geracaoDebito;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_DEBITO_A_COBRAR_CANCELAR})
	private Integer anoMesReferenciaDebito;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_DEBITO_A_COBRAR_CANCELAR})
	private Integer anoMesCobrancaDebito;

	/** persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_DEBITO_A_COBRAR_INSERIR, ATRIBUTOS_DEBITO_A_COBRAR_CANCELAR})
	private BigDecimal valorDebito;

	/** persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_DEBITO_A_COBRAR_CANCELAR})
	private short numeroPrestacaoDebito;

	/** persistent field */
	private short numeroPrestacaoCobradas;

	/** nullable persistent field */
	private Integer codigoSetorComercial;

	/** nullable persistent field */
	private Integer numeroQuadra;

	/** nullable persistent field */
	private Short numeroLote;

	/** nullable persistent field */
	private Short numeroSubLote;

	/** nullable persistent field */
	private Date ultimaAlteracao;

	/** nullable persistent field */
	private Integer anoMesReferenciaContabil;

	/** nullable persistent field */
	private BigDecimal percentualTaxaJurosFinanciamento;

	/** persistent field */
	@ControleAlteracao(value = FiltroDebitoACobrar.IMOVEL, funcionalidade = {ATRIBUTOS_DEBITO_A_COBRAR_INSERIR, ATRIBUTOS_DEBITO_A_COBRAR_CANCELAR})
	private Imovel imovel;

	/** persistent field */
	@ControleAlteracao(value = FiltroDebitoACobrar.DOCUMENTO_TIPO, funcionalidade = {ATRIBUTOS_DEBITO_A_COBRAR_CANCELAR})
	private DocumentoTipo documentoTipo;

	/** persistent field */
	@ControleAlteracao(value = FiltroDebitoACobrar.PARCELAMENTO, funcionalidade = {ATRIBUTOS_DEBITO_A_COBRAR_CANCELAR})
	private Parcelamento parcelamento;

	/** persistent field */
	private FinanciamentoTipo financiamentoTipo;

	/** persistent field */
	@ControleAlteracao(value = FiltroDebitoACobrar.ORDEM_SERVICO, funcionalidade = {ATRIBUTOS_DEBITO_A_COBRAR_CANCELAR})
	private OrdemServico ordemServico;

	/** persistent field */
	private Quadra quadra;

	/** persistent field */
	@ControleAlteracao(value = FiltroDebitoACobrar.LOCALIDADE, funcionalidade = {ATRIBUTOS_DEBITO_A_COBRAR_CANCELAR})
	private Localidade localidade;

	/** persistent field */
	@ControleAlteracao(value = FiltroDebitoACobrar.DEBITO_TIPO, funcionalidade = {ATRIBUTOS_DEBITO_A_COBRAR_INSERIR, ATRIBUTOS_DEBITO_A_COBRAR_CANCELAR})
	private gcom.faturamento.debito.DebitoTipo debitoTipo;

	/** persistent field */
	private RegistroAtendimento registroAtendimento;

	/** persistent field */
	private LancamentoItemContabil lancamentoItemContabil;

	/** persistent field */
	private gcom.faturamento.debito.DebitoCreditoSituacao debitoCreditoSituacaoAnterior;

	/** persistent field */
	@ControleAlteracao(value = FiltroDebitoACobrar.DEBITO_CREDITO_SITUACAO, funcionalidade = {ATRIBUTOS_DEBITO_A_COBRAR_CANCELAR})
	private gcom.faturamento.debito.DebitoCreditoSituacao debitoCreditoSituacaoAtual;

	/** persistent field */
	private ParcelamentoGrupo parcelamentoGrupo;

	/** persistent field */
	private CobrancaForma cobrancaForma;

	/** persistent field */
	private Set debitoACobrarCategorias;

	/** este campo foi criado só para o [UC 0155], e não está mapeado */
	private BigDecimal valorDebitoPorCategoria;

	private DebitoACobrarGeral debitoACobrarGeralOrigem;

	private DebitoACobrarGeral debitoACobrarGeral;

	private Short numeroParcelaBonus;

	private Date dataAntecipacao;

	private java.lang.Integer quantidadeParcelasAntecipadas;

	private Integer numeroDiasSuspensao;

	private OcorrenciaInfracaoItem ocorrenciaInfracaoItem;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_DEBITO_A_COBRAR_CANCELAR})
	private Integer numeroMesesEntreParcelas;

	private Integer numeroParcelasALancar;

	private Integer anoMesReferenciaUltimaCobranca;

	private Short numeroPrestacaoDebitoTemp;

	private Short numeroPrestacaoCobradasTemp;

	private Collection<Integer> idsDebitosACobrarAgrupados;

	private Short indicadorRemuneraCobrancaAdministrativa = 2;

	public String[] retornaCamposChavePrimaria(){

		String[] retorno = new String[1];
		retorno[0] = "id";
		return retorno;
	}

	public Filtro retornaFiltro(){

		FiltroDebitoACobrar filtroDebitoACobrar = new FiltroDebitoACobrar();

		filtroDebitoACobrar.adicionarCaminhoParaCarregamentoEntidade("imovel");
		filtroDebitoACobrar.adicionarCaminhoParaCarregamentoEntidade("documentoTipo");
		filtroDebitoACobrar.adicionarCaminhoParaCarregamentoEntidade("parcelamento");
		filtroDebitoACobrar.adicionarCaminhoParaCarregamentoEntidade("financiamentoTipo");
		filtroDebitoACobrar.adicionarCaminhoParaCarregamentoEntidade("ordemServico");
		filtroDebitoACobrar.adicionarCaminhoParaCarregamentoEntidade("quadra");
		filtroDebitoACobrar.adicionarCaminhoParaCarregamentoEntidade("localidade");
		filtroDebitoACobrar.adicionarCaminhoParaCarregamentoEntidade("debitoTipo");
		filtroDebitoACobrar.adicionarCaminhoParaCarregamentoEntidade("registroAtendimento");
		filtroDebitoACobrar.adicionarCaminhoParaCarregamentoEntidade("lancamentoItemContabil");
		filtroDebitoACobrar.adicionarCaminhoParaCarregamentoEntidade("debitoCreditoSituacaoAnterior");
		filtroDebitoACobrar.adicionarCaminhoParaCarregamentoEntidade("debitoCreditoSituacaoAtual");
		filtroDebitoACobrar.adicionarCaminhoParaCarregamentoEntidade("parcelamentoGrupo");
		filtroDebitoACobrar.adicionarCaminhoParaCarregamentoEntidade("cobrancaForma");

		filtroDebitoACobrar.adicionarParametro(new ParametroSimples(FiltroDebitoACobrar.ID, this.getId()));
		return filtroDebitoACobrar;
	}

	/** full constructor */
	public DebitoACobrar(Date geracaoDebito, Integer anoMesReferenciaDebito, Integer anoMesCobrancaDebito, BigDecimal valorDebito,
							short numeroPrestacaoDebito, short numeroPrestacaoCobradas, Integer codigoSetorComercial, Integer numeroQuadra,
							Short numeroLote, Short numeroSubLote, Date ultimaAlteracao, Integer anoMesReferenciaContabil,
							BigDecimal percentualTaxaJurosFinanciamento, Imovel imovel, DocumentoTipo documentoTipo,
							Parcelamento parcelamento, FinanciamentoTipo financiamentoTipo, OrdemServico ordemServico, Quadra quadra,
							Localidade localidade, gcom.faturamento.debito.DebitoTipo debitoTipo, RegistroAtendimento registroAtendimento,
							LancamentoItemContabil lancamentoItemContabil,
							gcom.faturamento.debito.DebitoCreditoSituacao debitoCreditoSituacaoAnterior,
							gcom.faturamento.debito.DebitoCreditoSituacao debitoCreditoSituacaoAtual, ParcelamentoGrupo parcelamentoGrupo,
							CobrancaForma cobrancaForma, Set debitoACobrarCategorias) {

		this.geracaoDebito = geracaoDebito;
		this.anoMesReferenciaDebito = anoMesReferenciaDebito;
		this.anoMesCobrancaDebito = anoMesCobrancaDebito;
		this.valorDebito = valorDebito;
		this.numeroPrestacaoDebito = numeroPrestacaoDebito;
		this.numeroPrestacaoCobradas = numeroPrestacaoCobradas;
		this.codigoSetorComercial = codigoSetorComercial;
		this.numeroQuadra = numeroQuadra;
		this.numeroLote = numeroLote;
		this.numeroSubLote = numeroSubLote;
		this.ultimaAlteracao = ultimaAlteracao;
		this.anoMesReferenciaContabil = anoMesReferenciaContabil;
		this.percentualTaxaJurosFinanciamento = percentualTaxaJurosFinanciamento;
		this.imovel = imovel;
		this.documentoTipo = documentoTipo;
		this.parcelamento = parcelamento;
		this.financiamentoTipo = financiamentoTipo;
		this.ordemServico = ordemServico;
		this.quadra = quadra;
		this.localidade = localidade;
		this.debitoTipo = debitoTipo;
		this.registroAtendimento = registroAtendimento;
		this.lancamentoItemContabil = lancamentoItemContabil;
		this.debitoCreditoSituacaoAnterior = debitoCreditoSituacaoAnterior;
		this.debitoCreditoSituacaoAtual = debitoCreditoSituacaoAtual;
		this.parcelamentoGrupo = parcelamentoGrupo;
		this.cobrancaForma = cobrancaForma;
		this.debitoACobrarCategorias = debitoACobrarCategorias;
	}

	/** default constructor */
	public DebitoACobrar() {

	}

	/** minimal constructor */
	public DebitoACobrar(Date geracaoDebito, BigDecimal valorDebito, short numeroPrestacaoDebito, short numeroPrestacaoCobradas,
							Imovel imovel, DocumentoTipo documentoTipo, Parcelamento parcelamento, FinanciamentoTipo financiamentoTipo,
							OrdemServico ordemServico, Quadra quadra, Localidade localidade, gcom.faturamento.debito.DebitoTipo debitoTipo,
							RegistroAtendimento registroAtendimento, LancamentoItemContabil lancamentoItemContabil,
							gcom.faturamento.debito.DebitoCreditoSituacao debitoCreditoSituacaoAnterior,
							gcom.faturamento.debito.DebitoCreditoSituacao debitoCreditoSituacaoAtual, ParcelamentoGrupo parcelamentoGrupo,
							CobrancaForma cobrancaForma, Set debitoACobrarCategorias) {

		this.geracaoDebito = geracaoDebito;
		this.valorDebito = valorDebito;
		this.numeroPrestacaoDebito = numeroPrestacaoDebito;
		this.numeroPrestacaoCobradas = numeroPrestacaoCobradas;
		this.imovel = imovel;
		this.documentoTipo = documentoTipo;
		this.parcelamento = parcelamento;
		this.financiamentoTipo = financiamentoTipo;
		this.ordemServico = ordemServico;
		this.quadra = quadra;
		this.localidade = localidade;
		this.debitoTipo = debitoTipo;
		this.registroAtendimento = registroAtendimento;
		this.lancamentoItemContabil = lancamentoItemContabil;
		this.debitoCreditoSituacaoAnterior = debitoCreditoSituacaoAnterior;
		this.debitoCreditoSituacaoAtual = debitoCreditoSituacaoAtual;
		this.parcelamentoGrupo = parcelamentoGrupo;
		this.cobrancaForma = cobrancaForma;
		this.debitoACobrarCategorias = debitoACobrarCategorias;
	}

	public DebitoACobrar(BigDecimal valorDebito, short numeroPrestacaoDebito, short numeroPrestacaoCobradas,
							LancamentoItemContabil lancamentoItemContabil) {

		this.valorDebito = valorDebito;
		this.numeroPrestacaoDebito = numeroPrestacaoDebito;
		this.numeroPrestacaoCobradas = numeroPrestacaoCobradas;
		this.lancamentoItemContabil = lancamentoItemContabil;
	}

	public Integer getId(){

		return this.id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public Date getGeracaoDebito(){

		return this.geracaoDebito;
	}

	public void setGeracaoDebito(Date geracaoDebito){

		this.geracaoDebito = geracaoDebito;
	}

	public Integer getAnoMesReferenciaDebito(){

		if(this.anoMesReferenciaDebito == null){
			return 0;
		}

		return this.anoMesReferenciaDebito;
	}

	public void setAnoMesReferenciaDebito(Integer anoMesReferenciaDebito){

		this.anoMesReferenciaDebito = anoMesReferenciaDebito;
	}

	public Integer getAnoMesCobrancaDebito(){

		return this.anoMesCobrancaDebito;
	}

	public void setAnoMesCobrancaDebito(Integer anoMesCobrancaDebito){

		this.anoMesCobrancaDebito = anoMesCobrancaDebito;
	}

	public BigDecimal getValorDebito(){

		return this.valorDebito;
	}

	public void setValorDebito(BigDecimal valorDebito){

		this.valorDebito = valorDebito;
	}

	public short getNumeroPrestacaoDebito(){

		return this.numeroPrestacaoDebito;
	}

	public void setNumeroPrestacaoDebito(short numeroPrestacaoDebito){

		this.numeroPrestacaoDebito = numeroPrestacaoDebito;
	}

	public short getNumeroPrestacaoCobradas(){

		return this.numeroPrestacaoCobradas;
	}

	public void setNumeroPrestacaoCobradas(short numeroPrestacaoCobradas){

		this.numeroPrestacaoCobradas = numeroPrestacaoCobradas;
	}

	public Integer getCodigoSetorComercial(){

		return this.codigoSetorComercial;
	}

	public void setCodigoSetorComercial(Integer codigoSetorComercial){

		this.codigoSetorComercial = codigoSetorComercial;
	}

	public Integer getNumeroQuadra(){

		return this.numeroQuadra;
	}

	public void setNumeroQuadra(Integer numeroQuadra){

		this.numeroQuadra = numeroQuadra;
	}

	public Short getNumeroLote(){

		return this.numeroLote;
	}

	public void setNumeroLote(Short numeroLote){

		this.numeroLote = numeroLote;
	}

	public Short getNumeroSubLote(){

		return this.numeroSubLote;
	}

	public void setNumeroSubLote(Short numeroSubLote){

		this.numeroSubLote = numeroSubLote;
	}

	public Date getUltimaAlteracao(){

		return this.ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public Integer getAnoMesReferenciaContabil(){

		return this.anoMesReferenciaContabil;
	}

	public void setAnoMesReferenciaContabil(Integer anoMesReferenciaContabil){

		this.anoMesReferenciaContabil = anoMesReferenciaContabil;
	}

	public BigDecimal getPercentualTaxaJurosFinanciamento(){

		return this.percentualTaxaJurosFinanciamento;
	}

	public void setPercentualTaxaJurosFinanciamento(BigDecimal percentualTaxaJurosFinanciamento){

		this.percentualTaxaJurosFinanciamento = percentualTaxaJurosFinanciamento;
	}

	public Imovel getImovel(){

		return this.imovel;
	}

	public void setImovel(Imovel imovel){

		this.imovel = imovel;
	}

	public DocumentoTipo getDocumentoTipo(){

		return this.documentoTipo;
	}

	public void setDocumentoTipo(DocumentoTipo documentoTipo){

		this.documentoTipo = documentoTipo;
	}

	public Parcelamento getParcelamento(){

		return this.parcelamento;
	}

	public void setParcelamento(Parcelamento parcelamento){

		this.parcelamento = parcelamento;
	}

	public FinanciamentoTipo getFinanciamentoTipo(){

		return this.financiamentoTipo;
	}

	public void setFinanciamentoTipo(FinanciamentoTipo financiamentoTipo){

		this.financiamentoTipo = financiamentoTipo;
	}

	public OrdemServico getOrdemServico(){

		return this.ordemServico;
	}

	public void setOrdemServico(OrdemServico ordemServico){

		this.ordemServico = ordemServico;
	}

	public Quadra getQuadra(){

		return this.quadra;
	}

	public void setQuadra(Quadra quadra){

		this.quadra = quadra;
	}

	public Localidade getLocalidade(){

		return this.localidade;
	}

	public void setLocalidade(Localidade localidade){

		this.localidade = localidade;
	}

	public gcom.faturamento.debito.DebitoTipo getDebitoTipo(){

		return this.debitoTipo;
	}

	public void setDebitoTipo(gcom.faturamento.debito.DebitoTipo debitoTipo){

		this.debitoTipo = debitoTipo;
	}

	public RegistroAtendimento getRegistroAtendimento(){

		return this.registroAtendimento;
	}

	public void setRegistroAtendimento(RegistroAtendimento registroAtendimento){

		this.registroAtendimento = registroAtendimento;
	}

	public LancamentoItemContabil getLancamentoItemContabil(){

		return this.lancamentoItemContabil;
	}

	public void setLancamentoItemContabil(LancamentoItemContabil lancamentoItemContabil){

		this.lancamentoItemContabil = lancamentoItemContabil;
	}

	public gcom.faturamento.debito.DebitoCreditoSituacao getDebitoCreditoSituacaoAnterior(){

		return this.debitoCreditoSituacaoAnterior;
	}

	public void setDebitoCreditoSituacaoAnterior(gcom.faturamento.debito.DebitoCreditoSituacao debitoCreditoSituacaoAnterior){

		this.debitoCreditoSituacaoAnterior = debitoCreditoSituacaoAnterior;
	}

	public gcom.faturamento.debito.DebitoCreditoSituacao getDebitoCreditoSituacaoAtual(){

		return this.debitoCreditoSituacaoAtual;
	}

	public void setDebitoCreditoSituacaoAtual(gcom.faturamento.debito.DebitoCreditoSituacao debitoCreditoSituacaoAtual){

		this.debitoCreditoSituacaoAtual = debitoCreditoSituacaoAtual;
	}

	public ParcelamentoGrupo getParcelamentoGrupo(){

		return this.parcelamentoGrupo;
	}

	public void setParcelamentoGrupo(ParcelamentoGrupo parcelamentoGrupo){

		this.parcelamentoGrupo = parcelamentoGrupo;
	}

	public CobrancaForma getCobrancaForma(){

		return this.cobrancaForma;
	}

	public void setCobrancaForma(CobrancaForma cobrancaForma){

		this.cobrancaForma = cobrancaForma;
	}

	public Set getDebitoACobrarCategorias(){

		return this.debitoACobrarCategorias;
	}

	public void setDebitoACobrarCategorias(Set debitoACobrarCategorias){

		this.debitoACobrarCategorias = debitoACobrarCategorias;
	}

	public String toString(){

		return new ToStringBuilder(this).append("id", getId()).toString();
	}

	/**
	 * TODO Este metodo aparentemente é identico ao getValorRestanteCobrado. verificar se é possivel
	 * substituir um pelo outro.
	 * o metodo getValorRestanteCobrado é utilizado em varias paginas JSPs.
	 */
	public BigDecimal getValorTotal(){

		BigDecimal retornoDivisao = new BigDecimal("0.00");
		BigDecimal retornoMultiplicacao = new BigDecimal("0.00");
		BigDecimal retorno = new BigDecimal("0.00");

		// retornoDivisao = Util.dividirArredondando(this.valorDebito,new
		// BigDecimal(numeroPrestacaoDebito));
		if(valorDebito != null){
			retornoDivisao = this.valorDebito.divide(new BigDecimal(numeroPrestacaoDebito), Parcelamento.CASAS_DECIMAIS,
							Parcelamento.TIPO_ARREDONDAMENTO);
		}

		retornoDivisao = retornoDivisao.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);

		retornoMultiplicacao = retornoDivisao.multiply(new BigDecimal(numeroPrestacaoCobradas));

		retornoMultiplicacao = retornoMultiplicacao.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);

		retorno = this.valorDebito.subtract(retornoMultiplicacao);

		retorno = retorno.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);

		return retorno;
	}

	public BigDecimal getValorDebitoPorCategoria(){

		return valorDebitoPorCategoria;
	}

	public void setValorDebitoPorCategoria(BigDecimal valorDebitoPorCategoria){

		this.valorDebitoPorCategoria = valorDebitoPorCategoria;
	}

	/**
	 * Realiza o calculo do valor restante a ser pago do débito
	 * 
	 * @author Pedro Alexandre
	 * @created 7 de Março de 2006
	 */
	public BigDecimal getValorRestanteCobrado(){

		BigDecimal retorno = new BigDecimal("0.00");

		BigDecimal valorDebito = getValorDebito();
		BigDecimal numeroPrestacaoDebito = new BigDecimal(getNumeroPrestacaoDebito());
		BigDecimal numeroPrestacaoCobradas = new BigDecimal(getNumeroPrestacaoCobradas());
		numeroPrestacaoDebito = numeroPrestacaoDebito.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
		numeroPrestacaoCobradas = numeroPrestacaoCobradas.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);

		retorno = valorDebito.divide(numeroPrestacaoDebito, Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
		retorno = retorno.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
		retorno = retorno.multiply(numeroPrestacaoCobradas);
		retorno = retorno.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
		retorno = valorDebito.subtract(retorno).setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);

		// retorno =
		// valorDebito.subtract(((valorDebito.divide(numeroPrestacaoDebito)).multiply(numeroPrestacaoCobradas)));

		return retorno;
	}

	public String getFormatarAnoMesCobrancaDebito(){

		String anoMesRecebido = "" + this.getAnoMesCobrancaDebito();
		String mes = anoMesRecebido.substring(4, 6);
		String ano = anoMesRecebido.substring(0, 4);
		String anoMesFormatado = mes + "/" + ano;

		return anoMesFormatado.toString();
	}

	public String getFormatarAnoMesReferenciaDebito(){

		String anoMesRecebido = "" + this.getAnoMesReferenciaDebito();
		String mes = anoMesRecebido.substring(4, 6);
		String ano = anoMesRecebido.substring(0, 4);
		String anoMesFormatado = mes + "/" + ano;

		return anoMesFormatado.toString();
	}

	public String getFormatarAnoMesReferenciaContabil(){

		String anoMesRecebido = "" + this.getAnoMesReferenciaContabil();
		String mes = anoMesRecebido.substring(4, 6);
		String ano = anoMesRecebido.substring(0, 4);
		String anoMesFormatado = mes + "/" + ano;

		return anoMesFormatado.toString();
	}

	/**
	 * Realiza o calculo de quantas parcelas falta para cobrar
	 * 
	 * @author Fernanda Paiva
	 * @created 7 de Abril de 2006
	 */
	public short getParcelasRestante(){

		short retorno = 0;

		if(numeroPrestacaoDebitoTemp == null || numeroPrestacaoCobradasTemp == null){

			retorno = Short.parseShort("" + (getNumeroPrestacaoDebito() - getNumeroPrestacaoCobradas()));

		}else{

			retorno = Short.valueOf("" + (numeroPrestacaoDebitoTemp - numeroPrestacaoCobradasTemp));

		}

		return retorno;
	}

	/**
	 * @return Retorna o campo debitoACobrarGeral.
	 */
	public DebitoACobrarGeral getDebitoACobrarGeral(){

		return debitoACobrarGeral;
	}

	/**
	 * @param debitoACobrarGeral
	 *            O debitoACobrarGeral a ser setado.
	 */
	public void setDebitoACobrarGeral(DebitoACobrarGeral debitoACobrarGeral){

		this.debitoACobrarGeral = debitoACobrarGeral;
	}

	public DebitoACobrarGeral getDebitoACobrarGeralOrigem(){

		return debitoACobrarGeralOrigem;
	}

	public void setDebitoACobrarGeralOrigem(DebitoACobrarGeral debitoACobrarGeralOrigem){

		this.debitoACobrarGeralOrigem = debitoACobrarGeralOrigem;
	}

	public boolean equals(Object other){

		if((this == other)) return true;
		if(!(other instanceof DebitoACobrar)) return false;
		DebitoACobrar castOther = (DebitoACobrar) other;
		return new EqualsBuilder().append(this.getId(), castOther.getId()).isEquals();
	}

	@Override
	public String getDescricaoParaRegistroTransacao(){

		return getId() + "";
	}

	public Date getDataAntecipacao(){

		return dataAntecipacao;
	}

	public void setDataAntecipacao(Date dataAntecipacao){

		this.dataAntecipacao = dataAntecipacao;
	}

	public java.lang.Integer getQuantidadeParcelasAntecipadas(){

		return quantidadeParcelasAntecipadas;
	}

	public void setQuantidadeParcelasAntecipadas(java.lang.Integer quantidadeParcelasAntecipadas){

		this.quantidadeParcelasAntecipadas = quantidadeParcelasAntecipadas;
	}

	/**
	 * @author Vivianne Sousa
	 * @created 21/02/2008
	 */
	public BigDecimal getValorTotalComBonus(){

		// caso o número de parcelas já cobradas seja igual
		// ao número total de parcelas menos o número de parcelas bonus
		// (DBAC_NNPRESTACAOCOBRADAS = DBAC_NNPRESTACAODEBITO -coalesce(DBAC_NNPARCELABONUS,0))
		// atribuir o valor zero ao valor restante a ser cobrado,
		// caso contrário
		// (DBAC_VLDEBITO ((DBAC_VLDEBITO/ DBAC_NNPRESTACAODEBITO) *
		// (DBAC_NNPRESTACAOCOBRADAS + coalesce (DBAC_NNPARCELABONUS,0))))

		BigDecimal retornoDivisao = new BigDecimal("0.00");
		BigDecimal retornoMultiplicacao = new BigDecimal("0.00");
		BigDecimal retorno = new BigDecimal("0.00");

		Short bonus = 0;
		// if (numeroParcelaBonus != null){
		// bonus = numeroParcelaBonus;
		// }

		if(numeroPrestacaoCobradas == numeroPrestacaoDebito - bonus){
			retorno = new BigDecimal("0.00");

		}else{
			if(valorDebito != null){
				retornoDivisao = getValorPrestacao();
			}

			// if (numeroParcelaBonus != null){
			// retornoMultiplicacao = retornoDivisao.multiply(new BigDecimal(numeroPrestacaoCobradas
			// + numeroParcelaBonus));
			// }else{
			// retornoMultiplicacao = retornoDivisao.multiply(new
			// BigDecimal(numeroPrestacaoCobradas));
			// }

			retornoMultiplicacao = retornoDivisao.multiply(new BigDecimal(numeroPrestacaoCobradas + bonus));

			retorno = this.valorDebito.subtract(retornoMultiplicacao);
		}

		return retorno;
	}

	/**
	 * @author Vivianne Sousa
	 * @created 15/04/2008
	 */
	public BigDecimal getValorPrestacao(){

		// truncando o resultado com 2 casas decimais
		BigDecimal retornoDivisao = this.valorDebito.divide(new BigDecimal(numeroPrestacaoDebito), 2, BigDecimal.ROUND_DOWN);

		return retornoDivisao;
	}

	public short getNumeroPrestacaoDebitoMenosBonus(){

		short retorno = getNumeroPrestacaoDebito();

		if(getNumeroParcelaBonus() != null){
			retorno = Short.parseShort("" + (retorno - getNumeroParcelaBonus().shortValue()));
		}

		return retorno;
	}

	public Short getNumeroParcelaBonus(){

		return numeroParcelaBonus;
	}

	public void setNumeroParcelaBonus(Short numeroParcelaBonus){

		this.numeroParcelaBonus = numeroParcelaBonus;
	}

	public Integer getNumeroDiasSuspensao(){

		return numeroDiasSuspensao;
	}

	public void setNumeroDiasSuspensao(Integer numeroDiasSuspensao){

		this.numeroDiasSuspensao = numeroDiasSuspensao;
	}

	public OcorrenciaInfracaoItem getOcorrenciaInfracaoItem(){

		return ocorrenciaInfracaoItem;
	}

	public void setOcorrenciaInfracaoItem(OcorrenciaInfracaoItem ocorrenciaInfracaoItem){

		this.ocorrenciaInfracaoItem = ocorrenciaInfracaoItem;
	}

	public Integer getNumeroMesesEntreParcelas(){

		return numeroMesesEntreParcelas;
	}

	public void setNumeroMesesEntreParcelas(Integer numeroMesesEntreParcelas){

		this.numeroMesesEntreParcelas = numeroMesesEntreParcelas;
	}

	public Integer getNumeroParcelasALancar(){

		return numeroParcelasALancar;
	}

	public void setNumeroParcelasALancar(Integer numeroParcelasALancar){

		this.numeroParcelasALancar = numeroParcelasALancar;
	}

	public Integer getAnoMesReferenciaUltimaCobranca(){

		return anoMesReferenciaUltimaCobranca;
	}

	public void setAnoMesReferenciaUltimaCobranca(Integer anoMesReferenciaUltimaCobranca){

		this.anoMesReferenciaUltimaCobranca = anoMesReferenciaUltimaCobranca;
	}

	public Short getNumeroPrestacaoDebitoTemp(){

		return numeroPrestacaoDebitoTemp;
	}

	public void setNumeroPrestacaoDebitoTemp(Short numeroPrestacaoDebitoTemp){

		this.numeroPrestacaoDebitoTemp = numeroPrestacaoDebitoTemp;
	}

	public Short getNumeroPrestacaoCobradasTemp(){

		return numeroPrestacaoCobradasTemp;
	}

	public void setNumeroPrestacaoCobradasTemp(Short numeroPrestacaoCobradasTemp){

		this.numeroPrestacaoCobradasTemp = numeroPrestacaoCobradasTemp;
	}

	public Collection<Integer> getIdsDebitosACobrarAgrupados(){

		return idsDebitosACobrarAgrupados;
	}

	public void setIdsDebitosACobrarAgrupados(Collection<Integer> idsDebitosACobrarAgrupados){

		this.idsDebitosACobrarAgrupados = idsDebitosACobrarAgrupados;
	}

	public void addIdDebitoACobrarAgrupado(Integer idDebitoACobrar){

		if(id != null && !id.equals(idDebitoACobrar)){

			if(idsDebitosACobrarAgrupados != null){

				idsDebitosACobrarAgrupados.add(idDebitoACobrar);

			}else{

				idsDebitosACobrarAgrupados = new ArrayList<Integer>();
				idsDebitosACobrarAgrupados.add(idDebitoACobrar);

			}

		}

	}

	/**
	 * TODO Este metodo aparentemente é identico ao getValorRestanteCobrado. verificar se é possivel
	 * substituir um pelo outro.
	 * o metodo getValorRestanteCobrado é utilizado em varias paginas JSPs.
	 */
	public BigDecimal verificarTratamentoRealizadoUltimaParcela(){

		BigDecimal valorPrestacao = BigDecimal.ZERO;

		// Calcula o valor da prestação
		if(valorDebito != null){
			valorPrestacao = valorDebito.divide(new BigDecimal(numeroPrestacaoDebito), Parcelamento.CASAS_DECIMAIS, BigDecimal.ROUND_DOWN);
		}

		// Caso seja a última prestação
		if(numeroPrestacaoCobradas == (numeroPrestacaoDebito - 1)){

			// Obtém o número de prestação débito
			BigDecimal nPrestacaoDebito = new BigDecimal(numeroPrestacaoDebito);

			// Mutiplica o valor da prestação * número da prestação debito
			BigDecimal multiplicacao = valorPrestacao.multiply(nPrestacaoDebito).setScale(Parcelamento.CASAS_DECIMAIS);

			// Subtrai o valor do débito pelo resultado da multiplicação
			BigDecimal parte1 = valorDebito.subtract(multiplicacao).setScale(2);

			// Calcula o valor da prestação
			valorPrestacao = valorPrestacao.add(parte1).setScale(Parcelamento.CASAS_DECIMAIS);
		}

		return valorPrestacao;

	}

	/**
	 * TODO
	 * Os proximos dois métodos são responsáveis por montar as informações
	 * dos "Dados Adicionais", que será informado no registro da OPERAÇÃO EFETUADA
	 */
	public String[] retornarAtributosInformacoesOperacaoEfetuada(){

		String[] atributos = {"imovel.id", "id", "valorDebito"};
		return atributos;
	}

	public String[] retornarLabelsInformacoesOperacaoEfetuada(){

		String[] labels = {"Imovel", "DebitoACobr", "Valor"};
		return labels;
	}

	public Short getIndicadorRemuneraCobrancaAdministrativa(){

		return indicadorRemuneraCobrancaAdministrativa;
	}

	public void setIndicadorRemuneraCobrancaAdministrativa(Short indicadorRemuneraCobrancaAdministrativa){

		this.indicadorRemuneraCobrancaAdministrativa = indicadorRemuneraCobrancaAdministrativa;
	}

}