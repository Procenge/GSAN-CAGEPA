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

package gcom.arrecadacao.pagamento;

import gcom.atendimentopublico.ordemservico.OrdemServico;
import gcom.atendimentopublico.registroatendimento.RegistroAtendimento;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.SetorComercial;
import gcom.cobranca.DocumentoTipo;
import gcom.cobranca.parcelamento.Parcelamento;
import gcom.cobranca.prescricao.PrescricaoComando;
import gcom.faturamento.GuiaPagamentoGeral;
import gcom.faturamento.debito.DebitoCreditoSituacao;
import gcom.interceptor.ControleAlteracao;
import gcom.interceptor.ObjetoTransacao;
import gcom.util.Util;
import gcom.util.filtro.Filtro;
import gcom.util.filtro.ParametroSimples;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
@ControleAlteracao()
public class GuiaPagamento
				extends ObjetoTransacao {


	private static final long serialVersionUID = 1L;

	public static final int ATRIBUTOS_GUIA_PAGAMENTO_CANCELAR = 445;

	public String[] retornaCamposChavePrimaria(){

		String[] retorno = {"id"};
		return retorno;
	}

	public Filtro retornaFiltro(){

		FiltroGuiaPagamento filtroGuiaPagamento = new FiltroGuiaPagamento();

		filtroGuiaPagamento.adicionarParametro(new ParametroSimples(FiltroGuiaPagamento.ID, this.getId()));
		filtroGuiaPagamento.adicionarCaminhoParaCarregamentoEntidade("cliente");
		filtroGuiaPagamento.adicionarCaminhoParaCarregamentoEntidade("parcelamento");
		filtroGuiaPagamento.adicionarCaminhoParaCarregamentoEntidade("documentoTipo");
		filtroGuiaPagamento.adicionarCaminhoParaCarregamentoEntidade("registroAtendimento");
		filtroGuiaPagamento.adicionarCaminhoParaCarregamentoEntidade("imovel");
		filtroGuiaPagamento.adicionarCaminhoParaCarregamentoEntidade("localidade");
		filtroGuiaPagamento.adicionarCaminhoParaCarregamentoEntidade("ordemServico");
		filtroGuiaPagamento.adicionarCaminhoParaCarregamentoEntidade("debitoCreditoSituacaoAtual");
		filtroGuiaPagamento.adicionarCaminhoParaCarregamentoEntidade("setorComercial");

		// Alteração v0.03
		/*
		 * //filtroGuiaPagamento
		 * .adicionarCaminhoParaCarregamentoEntidade("debitoCreditoSituacaoAnterior");
		 * filtroGuiaPagamento
		 * .adicionarCaminhoParaCarregamentoEntidade("lancamentoItemContabil");
		 * filtroGuiaPagamento
		 * .adicionarCaminhoParaCarregamentoEntidade("financiamentoTipo");
		 * filtroGuiaPagamento
		 * .adicionarCaminhoParaCarregamentoEntidade("debitoTipo");
		 */

		return filtroGuiaPagamento;
	}

	/** identifier field */
	private Integer id;

	/** nullable persistent field */
	private BigDecimal valorDebito;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_GUIA_PAGAMENTO_CANCELAR})
	private Date ultimaAlteracao;

	/** persistent field */
	private Cliente cliente;

	/** persistent field */
	private Parcelamento parcelamento;

	/** persistent field */
	private DocumentoTipo documentoTipo;

	/** persistent field */
	private RegistroAtendimento registroAtendimento;

	/** persistent field */
	private Imovel imovel;

	/** persistent field */
	private Localidade localidade;

	/** persistent field */
	private SetorComercial setorComercial;

	/** persistent field */
	private OrdemServico ordemServico;

	/** persistent field */
	private DebitoCreditoSituacao debitoCreditoSituacaoAtual;

	/** persistent field */
	private Short numeroPrestacaoTotal;

	/** persistent Set */
	private Set clientesGuiaPagamento;

	private Set<Pagamento> pagamentos;

	/** persistent Set */
	private Set<GuiaPagamentoPrestacao> guiasPagamentoPrestacao;

	private Set<GuiaPagamentoCategoria> guiasPagamentoCategoria;

	// Guias históricos copyProperties

	private Set<GuiaPagamentoPrestacaoHistorico> guiasPagamentoPrestacaoHistorico;

	private Set<GuiaPagamentoCategoriaHistorico> guiasPagamentoCategoriaHistorico;

	/** persistent field */
	private GuiaPagamentoGeral guiaPagamentoGeral;

	private GuiaPagamentoGeral origem;

	private Date dataInclusao;

	/** persistent field */
	private Short indicadorEmissaoObservacaoRA = 2;

	private Integer numeroContratoParcelOrgaoPublico;

	private PrescricaoComando prescricaoComando;

	private DebitoCreditoSituacao debitoCreditoSituacaoAnterior;

	// Alteração v0.03
	/*
	 * private Integer anoMesReferenciaContabil;
	 * private Date dataEmissao;
	 * private Date dataVencimento;
	 * private FinanciamentoTipo financiamentoTipo;
	 * private LancamentoItemContabil lancamentoItemContabil;
	 * private DebitoCreditoSituacao debitoCreditoSituacaoAnterior;
	 * private Short indicadoCobrancaMulta;
	 * private Short numeroPrestacaoDebito;
	 * private DebitoTipo debitoTipo;
	 */

	/**
	 * full constructor
	 * 
	 * @param setorComercial
	 *            TODO
	 */
	public GuiaPagamento(BigDecimal valorDebito, Date ultimaAlteracao, Cliente cliente, Parcelamento parcelamento,
							DocumentoTipo documentoTipo, RegistroAtendimento registroAtendimento, Imovel imovel, Localidade localidade,
							OrdemServico ordemServico, DebitoCreditoSituacao debitoCreditoSituacaoAtual, SetorComercial setorComercial) {

		this.valorDebito = valorDebito;
		this.ultimaAlteracao = ultimaAlteracao;
		this.cliente = cliente;
		this.parcelamento = parcelamento;
		this.documentoTipo = documentoTipo;
		this.registroAtendimento = registroAtendimento;
		this.imovel = imovel;
		this.localidade = localidade;
		this.ordemServico = ordemServico;
		this.debitoCreditoSituacaoAtual = debitoCreditoSituacaoAtual;
		this.setorComercial = setorComercial;
	}

	/** default constructor */
	public GuiaPagamento() {

	}

	/** minimal constructor */
	public GuiaPagamento(Cliente cliente, Parcelamento parcelamento, DocumentoTipo documentoTipo, RegistroAtendimento registroAtendimento,
							Imovel imovel, Localidade localidade, OrdemServico ordemServico,
							DebitoCreditoSituacao debitoCreditoSituacaoAtual) {

		this.cliente = cliente;
		this.parcelamento = parcelamento;
		this.documentoTipo = documentoTipo;
		this.registroAtendimento = registroAtendimento;
		this.imovel = imovel;
		this.localidade = localidade;
		this.ordemServico = ordemServico;
		this.debitoCreditoSituacaoAtual = debitoCreditoSituacaoAtual;
	}

	public GuiaPagamento(Integer id) {

		this.id = id;
	}

	public Integer getId(){

		return this.id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public BigDecimal getValorDebito(){

		return this.valorDebito;
	}

	public void setValorDebito(BigDecimal valorDebito){

		this.valorDebito = valorDebito;
	}

	public Date getUltimaAlteracao(){

		return this.ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public Cliente getCliente(){

		return this.cliente;
	}

	public void setCliente(Cliente cliente){

		this.cliente = cliente;
	}

	public Parcelamento getParcelamento(){

		return this.parcelamento;
	}

	public void setParcelamento(Parcelamento parcelamento){

		this.parcelamento = parcelamento;
	}

	public DocumentoTipo getDocumentoTipo(){

		return this.documentoTipo;
	}

	public void setDocumentoTipo(DocumentoTipo documentoTipo){

		this.documentoTipo = documentoTipo;
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

	public Localidade getLocalidade(){

		return this.localidade;
	}

	public void setLocalidade(Localidade localidade){

		this.localidade = localidade;
	}

	public OrdemServico getOrdemServico(){

		return this.ordemServico;
	}

	public void setOrdemServico(OrdemServico ordemServico){

		this.ordemServico = ordemServico;
	}

	public DebitoCreditoSituacao getDebitoCreditoSituacaoAtual(){

		return this.debitoCreditoSituacaoAtual;
	}

	public void setDebitoCreditoSituacaoAtual(DebitoCreditoSituacao debitoCreditoSituacaoAtual){

		this.debitoCreditoSituacaoAtual = debitoCreditoSituacaoAtual;
	}

	public Set getClientesGuiaPagamento(){

		return clientesGuiaPagamento;
	}

	public void setClientesGuiaPagamento(Set clientesGuiaPagamento){

		this.clientesGuiaPagamento = clientesGuiaPagamento;
	}

	/**
	 * @return Retorna o campo debitoACobrarGeral.
	 */
	public GuiaPagamentoGeral getGuiaPagamentoGeral(){

		return guiaPagamentoGeral;
	}

	/**
	 * @param debitoACobrarGeral
	 *            O debitoACobrarGeral a ser setado.
	 */
	public void setGuiaPagamentoGeral(GuiaPagamentoGeral guiaPagamentoGeral){

		this.guiaPagamentoGeral = guiaPagamentoGeral;
	}

	public String toString(){

		return new ToStringBuilder(this).append("id", getId()).toString();
	}

	public GuiaPagamentoGeral getOrigem(){

		return origem;
	}

	public void setOrigem(GuiaPagamentoGeral origem){

		this.origem = origem;
	}

	public Short getNumeroPrestacaoTotal(){

		return numeroPrestacaoTotal;
	}

	public void setNumeroPrestacaoTotal(Short numeroPrestacaoTotal){

		this.numeroPrestacaoTotal = numeroPrestacaoTotal;
	}

	public String getPrestacaoFormatada(){

		String prestacaoFormatada = "";

		/*
		 * if(getNumeroPrestacaoDebito() != null &&
		 * getNumeroPrestacaoTotal() != null){
		 * prestacaoFormatada = prestacaoFormatada + getNumeroPrestacaoDebito() + "/" +
		 * getNumeroPrestacaoTotal();
		 * }
		 */

		if(getNumeroPrestacaoTotal() != null){
			prestacaoFormatada = getNumeroPrestacaoTotal().toString();
		}

		return prestacaoFormatada;
	}

	/**
	 * @return the setorComercial
	 */
	public SetorComercial getSetorComercial(){

		return setorComercial;
	}

	/**
	 * @param setorComercial
	 *            the setorComercial to set
	 */
	public void setSetorComercial(SetorComercial setorComercial){

		this.setorComercial = setorComercial;
	}

	/**
	 * @return the guiasPagamentoPrestacao
	 */
	public Set getGuiasPagamentoPrestacao(){

		return guiasPagamentoPrestacao;
	}

	/**
	 * @param guiasPagamentoPrestacao
	 *            the guiasPagamentoPrestacao to set
	 */
	public void setGuiasPagamentoPrestacao(Set guiasPagamentoPrestacao){

		this.guiasPagamentoPrestacao = guiasPagamentoPrestacao;
	}

	/**
	 * @return the guiasPagamentoCategoria
	 */
	public Set getGuiasPagamentoCategoria(){

		return guiasPagamentoCategoria;
	}

	/**
	 * @param guiasPagamentoCategoria
	 *            the guiasPagamentoCategoria to set
	 */
	public void setGuiasPagamentoCategoria(Set<GuiaPagamentoCategoria> guiasPagamentoCategoria){

		this.guiasPagamentoCategoria = guiasPagamentoCategoria;
	}

	@Override
	public void initializeLazy(){

		initilizarCollectionLazy(this.getGuiasPagamentoPrestacao());

	}

	public Set<Pagamento> getPagamentos(){

		return pagamentos;
	}

	public void setPagamentos(Set<Pagamento> pagamentos){

		this.pagamentos = pagamentos;
	}

	public Set<GuiaPagamentoPrestacaoHistorico> getGuiasPagamentoPrestacaoHistorico(){

		return guiasPagamentoPrestacaoHistorico;
	}

	public void setGuiasPagamentoPrestacaoHistorico(Set<GuiaPagamentoPrestacaoHistorico> guiasPagamentoPrestacaoHistorico){

		this.guiasPagamentoPrestacaoHistorico = guiasPagamentoPrestacaoHistorico;
	}

	public Set<GuiaPagamentoCategoriaHistorico> getGuiasPagamentoCategoriaHistorico(){

		return guiasPagamentoCategoriaHistorico;
	}

	public void setGuiasPagamentoCategoriaHistorico(Set<GuiaPagamentoCategoriaHistorico> guiasPagamentoCategoriaHistorico){

		this.guiasPagamentoCategoriaHistorico = guiasPagamentoCategoriaHistorico;
	}

	public Date getDataInclusao(){

		return dataInclusao;
	}

	public void setDataInclusao(Date dataInclusao){

		this.dataInclusao = dataInclusao;
	}

	public Short getIndicadorEmissaoObservacaoRA(){

		return indicadorEmissaoObservacaoRA;
	}

	public void setIndicadorEmissaoObservacaoRA(Short indicadorEmissaoObservacaoRA){

		this.indicadorEmissaoObservacaoRA = indicadorEmissaoObservacaoRA;
	}

	public Integer getNumeroContratoParcelOrgaoPublico(){

		return numeroContratoParcelOrgaoPublico;
	}

	public void setNumeroContratoParcelOrgaoPublico(Integer numeroContratoParcelOrgaoPublico){

		this.numeroContratoParcelOrgaoPublico = numeroContratoParcelOrgaoPublico;
	}

	public String obterValorFormatadoPrestacaoGuia(Short numeroPrestacao){

		BigDecimal retorno = BigDecimal.ZERO;
		if(numeroPrestacao != null && this.guiasPagamentoPrestacao != null){
			for(GuiaPagamentoPrestacao guiaPrestacao : this.guiasPagamentoPrestacao){
				if(guiaPrestacao.getComp_id().getNumeroPrestacao().equals(numeroPrestacao)){
					retorno = retorno.add(guiaPrestacao.getValorPrestacao());
				}
			}
		}
		return Util.formataBigDecimal(retorno, 2, true);
	}

	public String obterValorFormatadoPrestacaoGuiaHistorico(Short numeroPrestacao){

		BigDecimal retorno = BigDecimal.ZERO;
		if(numeroPrestacao != null && this.guiasPagamentoPrestacaoHistorico != null){
			for(GuiaPagamentoPrestacaoHistorico guiaPrestacaoHistorico : this.guiasPagamentoPrestacaoHistorico){
				if(guiaPrestacaoHistorico.getComp_id().getNumeroPrestacao().equals(numeroPrestacao)){
					retorno = retorno.add(guiaPrestacaoHistorico.getValorPrestacao());
				}
			}
		}
		return Util.formataBigDecimal(retorno, 2, true);
	}

	public PrescricaoComando getPrescricaoComando(){

		return prescricaoComando;
	}

	public void setPrescricaoComando(PrescricaoComando prescricaoComando){

		this.prescricaoComando = prescricaoComando;
	}

	public DebitoCreditoSituacao getDebitoCreditoSituacaoAnterior(){

		return debitoCreditoSituacaoAnterior;
	}

	public void setDebitoCreditoSituacaoAnterior(DebitoCreditoSituacao debitoCreditoSituacaoAnterior){

		this.debitoCreditoSituacaoAnterior = debitoCreditoSituacaoAnterior;
	}

}