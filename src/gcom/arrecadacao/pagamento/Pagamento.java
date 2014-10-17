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

package gcom.arrecadacao.pagamento;

import gcom.arrecadacao.ArrecadacaoForma;
import gcom.arrecadacao.ArrecadadorMovimentoItem;
import gcom.arrecadacao.aviso.AvisoBancario;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.localidade.Localidade;
import gcom.cobranca.DocumentoTipo;
import gcom.cobranca.opcaoparcelamento.PreParcelamentoOpcao;
import gcom.faturamento.GuiaPagamentoGeral;
import gcom.faturamento.conta.Conta;
import gcom.faturamento.debito.DebitoACobrar;
import gcom.faturamento.debito.DebitoTipo;
import gcom.interceptor.ControleAlteracao;
import gcom.interceptor.ObjetoTransacao;
import gcom.util.Util;
import gcom.util.filtro.Filtro;
import gcom.util.filtro.ParametroSimples;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
@ControleAlteracao()
public class Pagamento
				extends ObjetoTransacao {

	private static final long serialVersionUID = 1L;

	public static final int ATRIBUTOS_INSERIR_PAGAMENTO_MANUAL = 165;

	public static final int ATRIBUTOS_INSERIR_PAGAMENTO_CODIGOBARRAS = 167;

	public static final int ATRIBUTOS_ATUALIZAR_PAGAMENTO = 178;

	/** identifier field */
	private Integer id;

	/** persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_ATUALIZAR_PAGAMENTO, ATRIBUTOS_INSERIR_PAGAMENTO_MANUAL, ATRIBUTOS_INSERIR_PAGAMENTO_CODIGOBARRAS})
	private Date dataPagamento;

	/** nullable persistent field */
	private Date ultimaAlteracao;

	/** persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_ATUALIZAR_PAGAMENTO, ATRIBUTOS_INSERIR_PAGAMENTO_MANUAL, ATRIBUTOS_INSERIR_PAGAMENTO_CODIGOBARRAS})
	private int anoMesReferenciaArrecadacao;

	/** persistent field */
	@ControleAlteracao(value = FiltroPagamento.CONTA, funcionalidade = {ATRIBUTOS_ATUALIZAR_PAGAMENTO, ATRIBUTOS_INSERIR_PAGAMENTO_MANUAL, ATRIBUTOS_INSERIR_PAGAMENTO_CODIGOBARRAS})
	private Conta conta;

	/** persistent field */
	private Localidade localidade;

	/** persistent field */
	@ControleAlteracao(value = FiltroPagamento.DEBITO_TIPO, funcionalidade = {ATRIBUTOS_ATUALIZAR_PAGAMENTO, ATRIBUTOS_INSERIR_PAGAMENTO_MANUAL, ATRIBUTOS_INSERIR_PAGAMENTO_CODIGOBARRAS})
	private DebitoTipo debitoTipo;

	/** persistent field */
	@ControleAlteracao(value = FiltroPagamento.DEBITO_A_COBRAR, funcionalidade = {ATRIBUTOS_ATUALIZAR_PAGAMENTO, ATRIBUTOS_INSERIR_PAGAMENTO_MANUAL, ATRIBUTOS_INSERIR_PAGAMENTO_CODIGOBARRAS})
	private DebitoACobrar debitoACobrar;

	/** persistent field */
	private Short indicadorExpurgado;

	/** persistent field */
	@ControleAlteracao(value = FiltroPagamento.PAGAMENTO_SITUACAO_ATUAL, funcionalidade = {ATRIBUTOS_ATUALIZAR_PAGAMENTO, ATRIBUTOS_INSERIR_PAGAMENTO_MANUAL, ATRIBUTOS_INSERIR_PAGAMENTO_CODIGOBARRAS})
	private gcom.arrecadacao.pagamento.PagamentoSituacao pagamentoSituacaoAtual;

	/** persistent field */
	private gcom.arrecadacao.pagamento.PagamentoSituacao pagamentoSituacaoAnterior;

	/** persistent field */
	@ControleAlteracao(value = FiltroPagamento.GUIA_PAGAMENTO_GERAL, funcionalidade = {ATRIBUTOS_ATUALIZAR_PAGAMENTO, ATRIBUTOS_INSERIR_PAGAMENTO_MANUAL, ATRIBUTOS_INSERIR_PAGAMENTO_CODIGOBARRAS})
	private gcom.faturamento.GuiaPagamentoGeral guiaPagamentoGeral;

	/** persistent field */
	@ControleAlteracao(value = FiltroPagamento.DOCUMENTO_TIPO, funcionalidade = {ATRIBUTOS_ATUALIZAR_PAGAMENTO, ATRIBUTOS_INSERIR_PAGAMENTO_MANUAL, ATRIBUTOS_INSERIR_PAGAMENTO_CODIGOBARRAS})
	private DocumentoTipo documentoTipo;

	/** persistent field */
	@ControleAlteracao(value = FiltroPagamento.AVISO_BANCARIO, funcionalidade = {ATRIBUTOS_ATUALIZAR_PAGAMENTO, ATRIBUTOS_INSERIR_PAGAMENTO_MANUAL, ATRIBUTOS_INSERIR_PAGAMENTO_CODIGOBARRAS})
	private AvisoBancario avisoBancario;

	/** persistent field */
	@ControleAlteracao(value = FiltroPagamento.IMOVEL, funcionalidade = {ATRIBUTOS_ATUALIZAR_PAGAMENTO, ATRIBUTOS_INSERIR_PAGAMENTO_MANUAL, ATRIBUTOS_INSERIR_PAGAMENTO_CODIGOBARRAS})
	private Imovel imovel;

	/** persistent field */
	@ControleAlteracao(value = FiltroPagamento.ARRECADADOR_MOVIMENTO_ITEM, funcionalidade = {ATRIBUTOS_ATUALIZAR_PAGAMENTO, ATRIBUTOS_INSERIR_PAGAMENTO_MANUAL, ATRIBUTOS_INSERIR_PAGAMENTO_CODIGOBARRAS})
	private ArrecadadorMovimentoItem arrecadadorMovimentoItem;

	/** persistent field */
	@ControleAlteracao(value = FiltroPagamento.ARRECADACAO_FORMA, funcionalidade = {ATRIBUTOS_ATUALIZAR_PAGAMENTO, ATRIBUTOS_INSERIR_PAGAMENTO_MANUAL, ATRIBUTOS_INSERIR_PAGAMENTO_CODIGOBARRAS})
	private ArrecadacaoForma arrecadacaoForma;

	/** persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_ATUALIZAR_PAGAMENTO, ATRIBUTOS_INSERIR_PAGAMENTO_MANUAL, ATRIBUTOS_INSERIR_PAGAMENTO_CODIGOBARRAS})
	private BigDecimal valorPagamento;

	/** persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_ATUALIZAR_PAGAMENTO, ATRIBUTOS_INSERIR_PAGAMENTO_MANUAL, ATRIBUTOS_INSERIR_PAGAMENTO_CODIGOBARRAS})
	private BigDecimal valorExcedente;

	/** persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_ATUALIZAR_PAGAMENTO, ATRIBUTOS_INSERIR_PAGAMENTO_MANUAL, ATRIBUTOS_INSERIR_PAGAMENTO_CODIGOBARRAS})
	private Integer anoMesReferenciaPagamento;

	/** persistent field */
	@ControleAlteracao(value = FiltroPagamento.CLIENTE, funcionalidade = {ATRIBUTOS_ATUALIZAR_PAGAMENTO, ATRIBUTOS_INSERIR_PAGAMENTO_MANUAL, ATRIBUTOS_INSERIR_PAGAMENTO_CODIGOBARRAS})
	private Cliente cliente;

	/** persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_ATUALIZAR_PAGAMENTO, ATRIBUTOS_INSERIR_PAGAMENTO_MANUAL, ATRIBUTOS_INSERIR_PAGAMENTO_CODIGOBARRAS})
	private Integer numeroPrestacao;

	/** persistent field */
	@ControleAlteracao(value = FiltroPagamento.DOCUMENTO_TIPO, funcionalidade = {ATRIBUTOS_ATUALIZAR_PAGAMENTO, ATRIBUTOS_INSERIR_PAGAMENTO_MANUAL, ATRIBUTOS_INSERIR_PAGAMENTO_CODIGOBARRAS})
	private PreParcelamentoOpcao preParcelamentoOpcao;

	public final static Short INDICADOR_EXPURGADO_SIM = Short.valueOf("1");

	public final static Short INDICADOR_EXPURGADO_NAO = Short.valueOf("2");

	private long linhaArrecadacao;

	/**
	 * [UC0242] - Registrar Movimento dos Arrecadadores [SF0004] - Processar
	 * Registro Código F Autor: Sávio Luiz Data: 15/02/2006
	 */

	private Date dataPrevistaCreditoHelper;

	/** full constructor */
	public Pagamento(/* int anoMesReferencia, */Date dataPagamento, Date ultimaAlteracao, int anoMesReferenciaArrecadacao, Conta conta,
						Localidade localidade, DebitoTipo debitoTipo, gcom.arrecadacao.pagamento.PagamentoSituacao pagamentoSituacaoAtual,
						gcom.arrecadacao.pagamento.PagamentoSituacao pagamentoSituacaoAnterior, GuiaPagamentoGeral guiaPagamentoGeral,
						DocumentoTipo documentoTipo, AvisoBancario avisoBancario, Imovel imovel,
						ArrecadadorMovimentoItem arrecadadorMovimentoItem, ArrecadacaoForma arrecadacaoForma, BigDecimal valorPagamento,
						Integer anoMesReferenciaPagamento, Cliente cliente, Integer numeroPrestacao) {

		// this.anoMesReferencia = anoMesReferencia;
		this.dataPagamento = dataPagamento;
		this.ultimaAlteracao = ultimaAlteracao;
		this.anoMesReferenciaArrecadacao = anoMesReferenciaArrecadacao;
		this.conta = conta;
		this.localidade = localidade;
		this.debitoTipo = debitoTipo;
		this.pagamentoSituacaoAtual = pagamentoSituacaoAtual;
		this.pagamentoSituacaoAnterior = pagamentoSituacaoAnterior;
		this.guiaPagamentoGeral = guiaPagamentoGeral;
		this.documentoTipo = documentoTipo;
		this.avisoBancario = avisoBancario;
		this.imovel = imovel;
		this.arrecadadorMovimentoItem = arrecadadorMovimentoItem;
		this.arrecadacaoForma = arrecadacaoForma;
		this.valorPagamento = valorPagamento;
		this.anoMesReferenciaPagamento = anoMesReferenciaPagamento;
		this.cliente = cliente;
		this.numeroPrestacao = numeroPrestacao;
	}

	/** default constructor */
	public Pagamento() {

	}

	/** minimal constructor */
	public Pagamento(/* int anoMesReferencia, */Date dataPagamento, int anoMesReferenciaArrecadacao, Conta conta, Localidade localidade,
						DebitoTipo debitoTipo, gcom.arrecadacao.pagamento.PagamentoSituacao pagamentoSituacaoAtual,
						gcom.arrecadacao.pagamento.PagamentoSituacao pagamentoSituacaoAnterior, GuiaPagamentoGeral guiaPagamentoGeral,
						DocumentoTipo documentoTipo, AvisoBancario avisoBancario, Imovel imovel,
						ArrecadadorMovimentoItem arrecadadorMovimentoItem, ArrecadacaoForma arrecadacaoForma, BigDecimal valorPagamento,
						Integer anoMesReferenciaPagamento, Cliente cliente, Integer numeroPrestacao) {

		// this.anoMesReferencia = anoMesReferencia;
		this.dataPagamento = dataPagamento;
		this.anoMesReferenciaArrecadacao = anoMesReferenciaArrecadacao;
		this.conta = conta;
		this.localidade = localidade;
		this.debitoTipo = debitoTipo;
		this.pagamentoSituacaoAtual = pagamentoSituacaoAtual;
		this.pagamentoSituacaoAnterior = pagamentoSituacaoAnterior;
		this.guiaPagamentoGeral = guiaPagamentoGeral;
		this.documentoTipo = documentoTipo;
		this.avisoBancario = avisoBancario;
		this.imovel = imovel;
		this.arrecadadorMovimentoItem = arrecadadorMovimentoItem;
		this.arrecadacaoForma = arrecadacaoForma;
		this.valorPagamento = valorPagamento;
		this.anoMesReferenciaPagamento = anoMesReferenciaPagamento;
		this.cliente = cliente;
		this.numeroPrestacao = numeroPrestacao;
	}

	/** constructor para o caso de uso [UC0301] Gerar Dados Diários da Arrecadação */
	public Pagamento(Imovel imovel, BigDecimal valorPagamento, Integer numeroPrestacao, AvisoBancario avisoBancario, Localidade localidade,
						DocumentoTipo documentoTipo, ArrecadacaoForma arrecadacaoForma, Date dataPagamento) {

		this.imovel = imovel;
		this.valorPagamento = valorPagamento;
		this.numeroPrestacao = numeroPrestacao;
		this.avisoBancario = avisoBancario;
		this.localidade = localidade;
		this.documentoTipo = documentoTipo;
		this.arrecadacaoForma = arrecadacaoForma;
		this.dataPagamento = dataPagamento;
	}

	public Integer getId(){

		return this.id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	// public Integer getAnoMesReferencia() {
	// return this.anoMesReferencia;
	// }
	//
	// public void setAnoMesReferencia(int anoMesReferencia) {
	// this.anoMesReferencia = anoMesReferencia;
	// }

	public Date getDataPagamento(){

		return this.dataPagamento;
	}

	public void setDataPagamento(Date dataPagamento){

		this.dataPagamento = dataPagamento;
	}

	public Date getUltimaAlteracao(){

		return this.ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public int getAnoMesReferenciaArrecadacao(){

		return this.anoMesReferenciaArrecadacao;
	}

	public void setAnoMesReferenciaArrecadacao(int anoMesReferenciaArrecadacao){

		this.anoMesReferenciaArrecadacao = anoMesReferenciaArrecadacao;
	}

	public Conta getConta(){

		return this.conta;
	}

	public void setConta(Conta conta){

		this.conta = conta;
	}

	public Localidade getLocalidade(){

		return this.localidade;
	}

	public void setLocalidade(Localidade localidade){

		this.localidade = localidade;
	}

	public DebitoTipo getDebitoTipo(){

		return this.debitoTipo;
	}

	public void setDebitoTipo(DebitoTipo debitoTipo){

		this.debitoTipo = debitoTipo;
	}

	public gcom.arrecadacao.pagamento.PagamentoSituacao getPagamentoSituacaoAtual(){

		return this.pagamentoSituacaoAtual;
	}

	public void setPagamentoSituacaoAtual(gcom.arrecadacao.pagamento.PagamentoSituacao pagamentoSituacaoAtual){

		this.pagamentoSituacaoAtual = pagamentoSituacaoAtual;
	}

	public gcom.arrecadacao.pagamento.PagamentoSituacao getPagamentoSituacaoAnterior(){

		return this.pagamentoSituacaoAnterior;
	}

	public void setPagamentoSituacaoAnterior(gcom.arrecadacao.pagamento.PagamentoSituacao pagamentoSituacaoAnterior){

		this.pagamentoSituacaoAnterior = pagamentoSituacaoAnterior;
	}

	public DocumentoTipo getDocumentoTipo(){

		return this.documentoTipo;
	}

	public void setDocumentoTipo(DocumentoTipo documentoTipo){

		this.documentoTipo = documentoTipo;
	}

	public AvisoBancario getAvisoBancario(){

		return this.avisoBancario;
	}

	public void setAvisoBancario(AvisoBancario avisoBancario){

		this.avisoBancario = avisoBancario;
	}

	public Imovel getImovel(){

		return this.imovel;
	}

	public void setImovel(Imovel imovel){

		this.imovel = imovel;
	}

	public ArrecadadorMovimentoItem getArrecadadorMovimentoItem(){

		return this.arrecadadorMovimentoItem;
	}

	public void setArrecadadorMovimentoItem(ArrecadadorMovimentoItem arrecadadorMovimentoItem){

		this.arrecadadorMovimentoItem = arrecadadorMovimentoItem;
	}

	public ArrecadacaoForma getArrecadacaoForma(){

		return this.arrecadacaoForma;
	}

	public void setArrecadacaoForma(ArrecadacaoForma arrecadacaoForma){

		this.arrecadacaoForma = arrecadacaoForma;
	}

	public String toString(){

		return new ToStringBuilder(this).append("id", getId()).toString();
	}

	/**
	 * @return Returns the valorPagamento.
	 */
	public BigDecimal getValorPagamento(){

		return valorPagamento;
	}

	/**
	 * @param valorPagamento
	 *            The valorPagamento to set.
	 */
	public void setValorPagamento(BigDecimal valorPagamento){

		this.valorPagamento = valorPagamento;
	}

	/**
	 * @return Returns the anoMesReferenciaPagamento.
	 */
	public Integer getAnoMesReferenciaPagamento(){

		return anoMesReferenciaPagamento;
	}

	/**
	 * @param anoMesReferenciaPagamento
	 *            The anoMesReferenciaPagamento to set.
	 */
	public void setAnoMesReferenciaPagamento(Integer anoMesReferenciaPagamento){

		this.anoMesReferenciaPagamento = anoMesReferenciaPagamento;
	}

	/**
	 * @return Returns the cliente.
	 */
	public Cliente getCliente(){

		return cliente;
	}

	/**
	 * @param cliente
	 *            The cliente to set.
	 */
	public void setCliente(Cliente cliente){

		this.cliente = cliente;
	}

	public Date getDataPrevistaCreditoHelper(){

		return dataPrevistaCreditoHelper;
	}

	public void setDataPrevistaCreditoHelper(Date dataPrevistaCreditoHelper){

		this.dataPrevistaCreditoHelper = dataPrevistaCreditoHelper;
	}

	/**
	 * @return Returns the debitoACobrar.
	 */
	public DebitoACobrar getDebitoACobrar(){

		return debitoACobrar;
	}

	/**
	 * @param debitoACobrar
	 *            The debitoACobrar to set.
	 */
	public void setDebitoACobrar(DebitoACobrar debitoACobrar){

		this.debitoACobrar = debitoACobrar;
	}

	public Integer getNumeroPrestacao(){

		return numeroPrestacao;
	}

	public void setNumeroPrestacao(Integer numeroPrestacao){

		this.numeroPrestacao = numeroPrestacao;
	}

	public BigDecimal getValorExcedente(){

		return valorExcedente;
	}

	public void setValorExcedente(BigDecimal valorExcedente){

		this.valorExcedente = valorExcedente;
	}

	public String getFormatarAnoMesPagamentoParaMesAno(){

		String anoMesFormatado = "";
		String anoMesRecebido = "" + this.getAnoMesReferenciaPagamento();
		if(!anoMesRecebido.equals("") && anoMesRecebido.length() >= 6){
			String mes = anoMesRecebido.substring(4, 6);
			String ano = anoMesRecebido.substring(0, 4);
			anoMesFormatado = mes + "/" + ano;
		}

		return anoMesFormatado;
	}

	public boolean equals(Object other){

		if((this == other)){
			return true;
		}
		if(!(other instanceof Pagamento)){
			return false;
		}
		Pagamento castOther = (Pagamento) other;

		return (this.getId().equals(castOther.getId()));
	}

	public String[] retornaCamposChavePrimaria(){

		String[] retorno = new String[1];
		retorno[0] = "id";
		return retorno;
	}

	public Filtro retornaFiltro(){

		FiltroPagamento filtroPagamento = new FiltroPagamento();

		filtroPagamento.adicionarCaminhoParaCarregamentoEntidade("conta");
		filtroPagamento.adicionarCaminhoParaCarregamentoEntidade("localidade");
		filtroPagamento.adicionarCaminhoParaCarregamentoEntidade("debitoTipo");
		filtroPagamento.adicionarCaminhoParaCarregamentoEntidade("debitoACobrar");
		filtroPagamento.adicionarCaminhoParaCarregamentoEntidade("pagamentoSituacaoAtual");
		filtroPagamento.adicionarCaminhoParaCarregamentoEntidade("guiaPagamentoGeral");
		filtroPagamento.adicionarCaminhoParaCarregamentoEntidade("documentoTipo");
		filtroPagamento.adicionarCaminhoParaCarregamentoEntidade("avisoBancario");
		filtroPagamento.adicionarCaminhoParaCarregamentoEntidade("imovel");
		filtroPagamento.adicionarCaminhoParaCarregamentoEntidade("arrecadadorMovimentoItem");
		filtroPagamento.adicionarCaminhoParaCarregamentoEntidade("arrecadacaoForma");
		filtroPagamento.adicionarCaminhoParaCarregamentoEntidade("cliente");
		filtroPagamento.adicionarParametro(new ParametroSimples(FiltroPagamento.ID, this.getId()));
		return filtroPagamento;
	}

	public Short getIndicadorExpurgado(){

		return indicadorExpurgado;
	}

	public void setIndicadorExpurgado(Short indicadorExpurgado){

		this.indicadorExpurgado = indicadorExpurgado;
	}

	public gcom.faturamento.GuiaPagamentoGeral getGuiaPagamentoGeral(){

		return guiaPagamentoGeral;
	}

	public void setGuiaPagamentoGeral(gcom.faturamento.GuiaPagamentoGeral guiaPagamentoGeral){

		this.guiaPagamentoGeral = guiaPagamentoGeral;
	}

	@Override
	public String[] retornarAtributosInformacoesOperacaoEfetuada(){

		String[] labels = {"imovel.id", "dataPagamentoFormatada", "valorPagamento", "anoMesReferenciaPagamento", "conta.id", "guiaPagamentoGeral.id", "numeroPrestacao", "debitoACobrar.id"};
		return labels;
	}

	@Override
	public String[] retornarLabelsInformacoesOperacaoEfetuada(){

		String[] labels = {"Mat:", "Pgto:", "Vlr:", "Ref:", "Conta:", "Guia:", "Pr.:", "Dbt:"};
		return labels;
	}

	public String getDataPagamentoFormatada(){

		String dataFormatada = null;
		if(this.dataPagamento != null){
			dataFormatada = Util.formatarData(this.dataPagamento);
		}
		return dataFormatada;
	}

	public void setLinhaArrecadacao(long linhaArrecadacao){

		this.linhaArrecadacao = linhaArrecadacao;
	}

	public long getLinhaArrecadacao(){

		return linhaArrecadacao;
	}

	/**
	 * @param preParcelamentoOpcao
	 *            the preParcelamentoOpcao to set
	 */
	public void setPreParcelamentoOpcao(PreParcelamentoOpcao preParcelamentoOpcao){

		this.preParcelamentoOpcao = preParcelamentoOpcao;
	}

	/**
	 * @return the preParcelamentoOpcao
	 */
	public PreParcelamentoOpcao getPreParcelamentoOpcao(){

		return preParcelamentoOpcao;
	}

	public String getValorPgt(){

		String retorno = "";

		if(this.getNumeroPrestacao() != null && this.getGuiaPagamentoGeral() != null){

			if(this.getGuiaPagamentoGeral().getGuiaPagamentoHistorico() != null){

				retorno = this.getGuiaPagamentoGeral().getGuiaPagamentoHistorico().obterValorFormatadoPrestacaoGuiaHistorico(
								this.numeroPrestacao.shortValue());

			}else if(this.getGuiaPagamentoGeral().getGuiaPagamento() != null){

				retorno = this.getGuiaPagamentoGeral().getGuiaPagamento().obterValorFormatadoPrestacaoGuia(
								this.numeroPrestacao.shortValue());

			}

		}

		return retorno;

	}

}
