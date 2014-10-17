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
import gcom.interceptor.ObjetoTransacao;
import gcom.util.filtro.Filtro;
import gcom.util.filtro.ParametroSimples;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class DebitoACobrarHistorico
				extends ObjetoTransacao
				implements Serializable {

	private static final long serialVersionUID = 1L;

	/** identifier field */
	private Integer id;

	/** persistent field */
	private Date debitoGeradoRealizar;

	/** nullable persistent field */
	private Integer anoMesReferenciaDebito;

	/** nullable persistent field */
	private Integer anoMesCobrancaDebito;

	/** nullable persistent field */
	private Integer anoMesReferenciaContabil;

	/** nullable persistent field */
	private Short indicadorSituacaoDebito;

	/** persistent field */
	private BigDecimal valorDebito;

	/** persistent field */
	private short prestacaoDebito;

	/** persistent field */
	private short prestacaoCobradas;

	/** nullable persistent field */
	private Integer codigoSetorComercial;

	/** nullable persistent field */
	private Integer numeroQuadra;

	/** nullable persistent field */
	private Short lote;

	/** nullable persistent field */
	private Short sublote;

	/** nullable persistent field */
	private Date ultimaAlteracao;

	/** nullable persistent field */
	private BigDecimal percentualTaxaJurosFinanciamento;

	/** persistent field */
	private LancamentoItemContabil lancamentoItemContabil;

	/** persistent field */
	private gcom.faturamento.debito.DebitoCreditoSituacao debitoCreditoSituacaoAtual;

	/** persistent field */
	private gcom.faturamento.debito.DebitoCreditoSituacao debitoCreditoSituacaoAnterior;

	/** persistent field */
	private RegistroAtendimento registroAtendimento;

	/** persistent field */
	private Imovel imovel;

	/** persistent field */
	private FinanciamentoTipo financiamentoTipo;

	/** persistent field */
	private OrdemServico ordemServico;

	/** persistent field */
	private CobrancaForma cobrancaForma;

	/** persistent field */
	private Quadra quadra;

	/** persistent field */
	private Localidade localidade;

	/** persistent field */
	private ParcelamentoGrupo parcelamentoGrupo;

	/** persistent field */
	private DocumentoTipo documentoTipo;

	/** persistent field */
	private Parcelamento parcelamento;

	/** persistent field */
	private gcom.faturamento.debito.DebitoTipo debitoTipo;

	/** persistent field */
	private gcom.faturamento.debito.DebitoACobrarGeral origem;

	private Set<DebitoACobrarCategoriaHistorico> debitoACobrarCategoriasHistorico;

	private Date dataAntecipacao;

	private Integer idOriginal;

	private Integer quantidadeParcelasAntecipadas;

	private Integer numeroParcela;

	private Integer numeroDiasSuspensao;

	private OcorrenciaInfracaoItem ocorrenciaInfracaoItem;

	private Integer numeroMesesEntreParcelas;

	private Integer numeroParcelasALancar;

	private Integer anoMesReferenciaUltimaCobranca;

	private Short indicadorRemuneraCobrancaAdministrativa = 2;

	/** full constructor */
	public DebitoACobrarHistorico(Date debitoGeradoRealizar, Integer anoMesReferenciaDebito, Integer anoMesCobrancaDebito,
									Integer anoMesReferenciaContabil, Short indicadorSituacaoDebito, BigDecimal valorDebito,
									short prestacaoDebito, short prestacaoCobradas, Integer codigoSetorComercial, Integer numeroQuadra,
									Short lote, Short sublote, Date ultimaAlteracao, LancamentoItemContabil lancamentoItemContabil,
									gcom.faturamento.debito.DebitoCreditoSituacao debitoCreditoSituacaoAtual,
									gcom.faturamento.debito.DebitoCreditoSituacao debitoCreditoSituacaoAnterior,
									RegistroAtendimento registroAtendimento, Imovel imovel, FinanciamentoTipo financiamentoTipo,
									OrdemServico ordemServico, CobrancaForma cobrancaForma, Quadra quadra, Localidade localidade,
									ParcelamentoGrupo parcelamentoGrupo, DocumentoTipo documentoTipo, Parcelamento parcelamento,
									gcom.faturamento.debito.DebitoTipo debitoTipo, BigDecimal percentualTaxaJurosFinanciamento) {

		this.debitoGeradoRealizar = debitoGeradoRealizar;
		this.anoMesReferenciaDebito = anoMesReferenciaDebito;
		this.anoMesCobrancaDebito = anoMesCobrancaDebito;
		this.anoMesReferenciaContabil = anoMesReferenciaContabil;
		this.indicadorSituacaoDebito = indicadorSituacaoDebito;
		this.valorDebito = valorDebito;
		this.prestacaoDebito = prestacaoDebito;
		this.prestacaoCobradas = prestacaoCobradas;
		this.codigoSetorComercial = codigoSetorComercial;
		this.numeroQuadra = numeroQuadra;
		this.lote = lote;
		this.sublote = sublote;
		this.ultimaAlteracao = ultimaAlteracao;
		this.lancamentoItemContabil = lancamentoItemContabil;
		this.debitoCreditoSituacaoAtual = debitoCreditoSituacaoAtual;
		this.debitoCreditoSituacaoAnterior = debitoCreditoSituacaoAnterior;
		this.registroAtendimento = registroAtendimento;
		this.imovel = imovel;
		this.financiamentoTipo = financiamentoTipo;
		this.ordemServico = ordemServico;
		this.cobrancaForma = cobrancaForma;
		this.quadra = quadra;
		this.localidade = localidade;
		this.parcelamentoGrupo = parcelamentoGrupo;
		this.documentoTipo = documentoTipo;
		this.parcelamento = parcelamento;
		this.debitoTipo = debitoTipo;
		this.percentualTaxaJurosFinanciamento = percentualTaxaJurosFinanciamento;
	}

	/** default constructor */
	public DebitoACobrarHistorico() {

	}

	/** minimal constructor */
	public DebitoACobrarHistorico(Date debitoGeradoRealizar, BigDecimal valorDebito, short prestacaoDebito, short prestacaoCobradas,
									LancamentoItemContabil lancamentoItemContabil,
									gcom.faturamento.debito.DebitoCreditoSituacao debitoCreditoSituacaoAtual,
									gcom.faturamento.debito.DebitoCreditoSituacao debitoCreditoSituacaoAnterior,
									RegistroAtendimento registroAtendimento, Imovel imovel, FinanciamentoTipo financiamentoTipo,
									OrdemServico ordemServico, CobrancaForma cobrancaForma, Quadra quadra, Localidade localidade,
									ParcelamentoGrupo parcelamentoGrupo, DocumentoTipo documentoTipo, Parcelamento parcelamento,
									gcom.faturamento.debito.DebitoTipo debitoTipo) {

		this.debitoGeradoRealizar = debitoGeradoRealizar;
		this.valorDebito = valorDebito;
		this.prestacaoDebito = prestacaoDebito;
		this.prestacaoCobradas = prestacaoCobradas;
		this.lancamentoItemContabil = lancamentoItemContabil;
		this.debitoCreditoSituacaoAtual = debitoCreditoSituacaoAtual;
		this.debitoCreditoSituacaoAnterior = debitoCreditoSituacaoAnterior;
		this.registroAtendimento = registroAtendimento;
		this.imovel = imovel;
		this.financiamentoTipo = financiamentoTipo;
		this.ordemServico = ordemServico;
		this.cobrancaForma = cobrancaForma;
		this.quadra = quadra;
		this.localidade = localidade;
		this.parcelamentoGrupo = parcelamentoGrupo;
		this.documentoTipo = documentoTipo;
		this.parcelamento = parcelamento;
		this.debitoTipo = debitoTipo;
	}

	public Integer getId(){

		return this.id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public Date getDebitoGeradoRealizar(){

		return this.debitoGeradoRealizar;
	}

	public void setDebitoGeradoRealizar(Date debitoGeradoRealizar){

		this.debitoGeradoRealizar = debitoGeradoRealizar;
	}

	public Integer getAnoMesReferenciaDebito(){

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

	public Integer getAnoMesReferenciaContabil(){

		return this.anoMesReferenciaContabil;
	}

	public void setAnoMesReferenciaContabil(Integer anoMesReferenciaContabil){

		this.anoMesReferenciaContabil = anoMesReferenciaContabil;
	}

	public Short getIndicadorSituacaoDebito(){

		return this.indicadorSituacaoDebito;
	}

	public void setIndicadorSituacaoDebito(Short indicadorSituacaoDebito){

		this.indicadorSituacaoDebito = indicadorSituacaoDebito;
	}

	public BigDecimal getValorDebito(){

		return this.valorDebito;
	}

	public void setValorDebito(BigDecimal valorDebito){

		this.valorDebito = valorDebito;
	}

	public short getPrestacaoDebito(){

		return this.prestacaoDebito;
	}

	public void setPrestacaoDebito(short prestacaoDebito){

		this.prestacaoDebito = prestacaoDebito;
	}

	public short getPrestacaoCobradas(){

		return this.prestacaoCobradas;
	}

	public void setPrestacaoCobradas(short prestacaoCobradas){

		this.prestacaoCobradas = prestacaoCobradas;
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

	public Short getLote(){

		return this.lote;
	}

	public void setLote(Short lote){

		this.lote = lote;
	}

	public Short getSublote(){

		return this.sublote;
	}

	public void setSublote(Short sublote){

		this.sublote = sublote;
	}

	public Date getUltimaAlteracao(){

		return this.ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public LancamentoItemContabil getLancamentoItemContabil(){

		return this.lancamentoItemContabil;
	}

	public void setLancamentoItemContabil(LancamentoItemContabil lancamentoItemContabil){

		this.lancamentoItemContabil = lancamentoItemContabil;
	}

	public gcom.faturamento.debito.DebitoCreditoSituacao getDebitoCreditoSituacaoAtual(){

		return this.debitoCreditoSituacaoAtual;
	}

	public void setDebitoCreditoSituacaoAtual(gcom.faturamento.debito.DebitoCreditoSituacao debitoCreditoSituacaoAtual){

		this.debitoCreditoSituacaoAtual = debitoCreditoSituacaoAtual;
	}

	public gcom.faturamento.debito.DebitoCreditoSituacao getDebitoCreditoSituacaoAnterior(){

		return this.debitoCreditoSituacaoAnterior;
	}

	public void setDebitoCreditoSituacaoAnterior(gcom.faturamento.debito.DebitoCreditoSituacao debitoCreditoSituacaoAnterior){

		this.debitoCreditoSituacaoAnterior = debitoCreditoSituacaoAnterior;
	}

	public RegistroAtendimento getRegistroAtendimento(){

		return this.registroAtendimento;
	}

	public void setRegistroAtendimento(RegistroAtendimento registroAtendimento){

		this.registroAtendimento = registroAtendimento;
	}

	public Imovel getImovel(){

		return this.imovel;
	}

	public void setImovel(Imovel imovel){

		this.imovel = imovel;
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

	public CobrancaForma getCobrancaForma(){

		return this.cobrancaForma;
	}

	public void setCobrancaForma(CobrancaForma cobrancaForma){

		this.cobrancaForma = cobrancaForma;
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

	public ParcelamentoGrupo getParcelamentoGrupo(){

		return this.parcelamentoGrupo;
	}

	public void setParcelamentoGrupo(ParcelamentoGrupo parcelamentoGrupo){

		this.parcelamentoGrupo = parcelamentoGrupo;
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

	public gcom.faturamento.debito.DebitoTipo getDebitoTipo(){

		return this.debitoTipo;
	}

	public void setDebitoTipo(gcom.faturamento.debito.DebitoTipo debitoTipo){

		this.debitoTipo = debitoTipo;
	}

	public String toString(){

		return new ToStringBuilder(this).append("id", getId()).toString();
	}

	public BigDecimal getPercentualTaxaJurosFinanciamento(){

		return percentualTaxaJurosFinanciamento;
	}

	public void setPercentualTaxaJurosFinanciamento(BigDecimal percentualTaxaJurosFinanciamento){

		this.percentualTaxaJurosFinanciamento = percentualTaxaJurosFinanciamento;
	}

	public BigDecimal getValorTotal(){

		BigDecimal retornoDivisao = new BigDecimal("0.00");
		BigDecimal retornoMultiplicacao = new BigDecimal("0.00");
		BigDecimal retorno = new BigDecimal("0.00");

		// retornoDivisao = Util.dividirArredondando(this.valorDebito,new
		// BigDecimal(numeroPrestacaoDebito));
		if(valorDebito != null){
			retornoDivisao = this.valorDebito.divide(new BigDecimal(prestacaoDebito), 2, BigDecimal.ROUND_DOWN);
		}

		retornoDivisao = retornoDivisao.setScale(2, BigDecimal.ROUND_DOWN);

		retornoMultiplicacao = retornoDivisao.multiply(new BigDecimal(prestacaoCobradas));

		retornoMultiplicacao = retornoMultiplicacao.setScale(2, BigDecimal.ROUND_DOWN);

		retorno = this.valorDebito.subtract(retornoMultiplicacao);

		retorno = retorno.setScale(2, BigDecimal.ROUND_DOWN);

		return retorno;
	}

	public gcom.faturamento.debito.DebitoACobrarGeral getOrigem(){

		return origem;
	}

	public void setOrigem(gcom.faturamento.debito.DebitoACobrarGeral origem){

		this.origem = origem;
	}

	public void setDebitoACobrarCategoriasHistorico(Set<DebitoACobrarCategoriaHistorico> debitoACobrarCategoriasHistorico){

		this.debitoACobrarCategoriasHistorico = debitoACobrarCategoriasHistorico;
	}

	public Set<DebitoACobrarCategoriaHistorico> getDebitoACobrarCategoriasHistorico(){

		return debitoACobrarCategoriasHistorico;
	}

	public Filtro retornaFiltro(){

		FiltroDebitoACobrar filtroDebitoACobrar = new FiltroDebitoACobrar();

		filtroDebitoACobrar.adicionarCaminhoParaCarregamentoEntidade("debitoCreditoSituacaoAnterior");
		filtroDebitoACobrar.adicionarCaminhoParaCarregamentoEntidade("debitoCreditoSituacaoAtual");

		filtroDebitoACobrar.adicionarParametro(new ParametroSimples(FiltroDebitoACobrar.ID, this.getId()));
		return filtroDebitoACobrar;
	}

	@Override
	public String[] retornaCamposChavePrimaria(){

		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void initializeLazy(){

		debitoCreditoSituacaoAnterior.initializeLazy();
		debitoCreditoSituacaoAtual.initializeLazy();

	}

	/**
	 * @return the dataAntecipacao
	 */
	public Date getDataAntecipacao(){

		return dataAntecipacao;
	}

	/**
	 * @param dataAntecipacao
	 *            the dataAntecipacao to set
	 */
	public void setDataAntecipacao(Date dataAntecipacao){

		this.dataAntecipacao = dataAntecipacao;
	}

	/**
	 * @return the idOriginal
	 */
	public Integer getIdOriginal(){

		return idOriginal;
	}

	/**
	 * @param idOriginal
	 *            the idOriginal to set
	 */
	public void setIdOriginal(Integer idOriginal){

		this.idOriginal = idOriginal;
	}

	/**
	 * @return the quantidadeParcelasAntecipadas
	 */
	public Integer getQuantidadeParcelasAntecipadas(){

		return quantidadeParcelasAntecipadas;
	}

	/**
	 * @param quantidadeParcelasAntecipadas
	 *            the quantidadeParcelasAntecipadas to set
	 */
	public void setQuantidadeParcelasAntecipadas(Integer quantidadeParcelasAntecipadas){

		this.quantidadeParcelasAntecipadas = quantidadeParcelasAntecipadas;
	}

	/**
	 * @return the numeroParcela
	 */
	public Integer getNumeroParcela(){

		return numeroParcela;
	}

	/**
	 * @param numeroParcela
	 *            the numeroParcela to set
	 */
	public void setNumeroParcela(Integer numeroParcela){

		this.numeroParcela = numeroParcela;
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

	public Short getIndicadorRemuneraCobrancaAdministrativa(){

		return indicadorRemuneraCobrancaAdministrativa;
	}

	public void setIndicadorRemuneraCobrancaAdministrativa(Short indicadorRemuneraCobrancaAdministrativa){

		this.indicadorRemuneraCobrancaAdministrativa = indicadorRemuneraCobrancaAdministrativa;
	}

	public BigDecimal getValorRestanteCobrado(){

		BigDecimal retorno = new BigDecimal("0.00");

		BigDecimal valorDebito = getValorDebito();
		BigDecimal numeroPrestacaoDebito = new BigDecimal(getPrestacaoDebito());
		BigDecimal numeroPrestacaoCobradas = new BigDecimal(getPrestacaoCobradas());
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
}
