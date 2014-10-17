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
import gcom.faturamento.GuiaPagamentoGeral;
import gcom.faturamento.conta.ContaHistorico;
import gcom.faturamento.debito.DebitoACobrar;
import gcom.faturamento.debito.DebitoTipo;
import gcom.util.Util;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/** @author Hibernate CodeGenerator */
public class PagamentoHistorico
				implements Serializable {

	private static final long serialVersionUID = 1L;

	/** identifier field */
	private Integer id;

	/** persistent field */
	private BigDecimal valorPagamento;

	/** persistent field */
	private BigDecimal valorExcedente;

	/** persistent field */
	private Integer anoMesReferenciaPagamento;

	/** persistent field */
	private Date dataPagamento;

	/** persistent field */
	private Integer anoMesReferenciaArrecadacao;

	/** nullable persistent field */
	private Date ultimaAlteracao;

	/** nullable persistent field */
	private Integer codigoAgente;

	/** persistent field */
	private ArrecadacaoForma arrecadacaoForma;

	/** persistent field */
	private Imovel imovel;

	/** persistent field */
	private DocumentoTipo documentoTipo;

	/** persistent field */
	private ContaHistorico conta;

	/** persistent field */
	private Localidade localidade;

	/** persistent field */
	private Short indicadorExpurgado;

	/** persistent field */
	private gcom.arrecadacao.pagamento.PagamentoSituacao pagamentoSituacaoAtual;

	/** persistent field */
	private gcom.arrecadacao.pagamento.PagamentoSituacao pagamentoSituacaoAnterior;

	/** persistent field */
	private ArrecadadorMovimentoItem arrecadadorMovimentoItem;

	/** persistent field */
	private gcom.faturamento.GuiaPagamentoGeral guiaPagamentoGeral;

	/** persistent field */
	private DebitoACobrar debitoACobrar;

	/** persistent field */
	private AvisoBancario avisoBancario;

	/** persistent field */
	private DebitoTipo debitoTipo;

	/** persistent field */
	private Cliente cliente;

	/** persistent field */
	private Integer numeroPrestacao;

	public final static Short INDICADOR_EXPURGADO_SIM = Short.valueOf("1");

	public final static Short INDICADOR_EXPURGADO_NAO = Short.valueOf("2");

	/** full constructor */
	public PagamentoHistorico(BigDecimal valorPagamento, Integer anoMesReferenciaPagamento, Date dataPagamento,
								Integer anoMesReferenciaArrecadacao, Date ultimaAlteracao, Integer codigoAgente,
								ArrecadacaoForma arrecadacaoForma, Imovel imovel, DocumentoTipo documentoTipo, ContaHistorico conta,
								Localidade localidade, gcom.arrecadacao.pagamento.PagamentoSituacao pagamentoSituacaoAtual,
								gcom.arrecadacao.pagamento.PagamentoSituacao pagamentoSituacaoAnterior,
								ArrecadadorMovimentoItem arrecadadorMovimentoItem, GuiaPagamentoGeral guiaPagamentoGeral,
								AvisoBancario avisoBancario, DebitoTipo debitoTipo, Cliente cliente, Integer numeroPrestacao) {

		this.valorPagamento = valorPagamento;
		this.anoMesReferenciaPagamento = anoMesReferenciaPagamento;
		this.dataPagamento = dataPagamento;
		this.anoMesReferenciaArrecadacao = anoMesReferenciaArrecadacao;
		this.ultimaAlteracao = ultimaAlteracao;
		this.codigoAgente = codigoAgente;
		this.arrecadacaoForma = arrecadacaoForma;
		this.imovel = imovel;
		this.documentoTipo = documentoTipo;
		this.conta = conta;
		this.localidade = localidade;
		this.pagamentoSituacaoAtual = pagamentoSituacaoAtual;
		this.pagamentoSituacaoAnterior = pagamentoSituacaoAnterior;
		this.arrecadadorMovimentoItem = arrecadadorMovimentoItem;
		this.guiaPagamentoGeral = guiaPagamentoGeral;
		this.avisoBancario = avisoBancario;
		this.debitoTipo = debitoTipo;
		this.cliente = cliente;
		this.numeroPrestacao = numeroPrestacao;
	}

	/** default constructor */
	public PagamentoHistorico() {

	}

	/** minimal constructor */
	public PagamentoHistorico(BigDecimal valorPagamento, Integer anoMesReferenciaPagamento, Date dataPagamento,
								Integer anoMesReferenciaArrecadacao, ArrecadacaoForma arrecadacaoForma, Imovel imovel,
								DocumentoTipo documentoTipo, ContaHistorico conta, Localidade localidade,
								gcom.arrecadacao.pagamento.PagamentoSituacao pagamentoSituacaoAtual,
								gcom.arrecadacao.pagamento.PagamentoSituacao pagamentoSituacaoAnterior,
								ArrecadadorMovimentoItem arrecadadorMovimentoItem, GuiaPagamentoGeral guiaPagamentoGeral,
								AvisoBancario avisoBancario, DebitoTipo debitoTipo, Cliente cliente, Integer numeroPrestacao) {

		this.valorPagamento = valorPagamento;
		this.anoMesReferenciaPagamento = anoMesReferenciaPagamento;
		this.dataPagamento = dataPagamento;
		this.anoMesReferenciaArrecadacao = anoMesReferenciaArrecadacao;
		this.arrecadacaoForma = arrecadacaoForma;
		this.imovel = imovel;
		this.documentoTipo = documentoTipo;
		this.conta = conta;
		this.localidade = localidade;
		this.pagamentoSituacaoAtual = pagamentoSituacaoAtual;
		this.pagamentoSituacaoAnterior = pagamentoSituacaoAnterior;
		this.arrecadadorMovimentoItem = arrecadadorMovimentoItem;
		this.guiaPagamentoGeral = guiaPagamentoGeral;
		this.avisoBancario = avisoBancario;
		this.debitoTipo = debitoTipo;
		this.cliente = cliente;
		this.numeroPrestacao = numeroPrestacao;
	}

	public String getFormatarAnoMesParaMesAnoArrecadacao(){

		String mesAnoReferencia = Util.formatarAnoMesParaMesAno(this.getAnoMesReferenciaArrecadacao());

		return mesAnoReferencia;
	}

	public String getFormatarAnoMesReferenciaPagamento(){

		String mesAnoReferencia = Util.formatarAnoMesParaMesAno(this.getAnoMesReferenciaPagamento());

		return mesAnoReferencia;
	}

	/**
	 * @return Retorna o campo anoMesReferenciaArrecadacao.
	 */
	public Integer getAnoMesReferenciaArrecadacao(){

		return anoMesReferenciaArrecadacao;
	}

	/**
	 * @param anoMesReferenciaArrecadacao
	 *            O anoMesReferenciaArrecadacao a ser setado.
	 */
	public void setAnoMesReferenciaArrecadacao(Integer anoMesReferenciaArrecadacao){

		this.anoMesReferenciaArrecadacao = anoMesReferenciaArrecadacao;
	}

	/**
	 * @return Retorna o campo anoMesReferenciaPagamento.
	 */
	public Integer getAnoMesReferenciaPagamento(){

		return anoMesReferenciaPagamento;
	}

	/**
	 * @param anoMesReferenciaPagamento
	 *            O anoMesReferenciaPagamento a ser setado.
	 */
	public void setAnoMesReferenciaPagamento(Integer anoMesReferenciaPagamento){

		this.anoMesReferenciaPagamento = anoMesReferenciaPagamento;
	}

	/**
	 * @return Retorna o campo arrecadacaoForma.
	 */
	public ArrecadacaoForma getArrecadacaoForma(){

		return arrecadacaoForma;
	}

	/**
	 * @param arrecadacaoForma
	 *            O arrecadacaoForma a ser setado.
	 */
	public void setArrecadacaoForma(ArrecadacaoForma arrecadacaoForma){

		this.arrecadacaoForma = arrecadacaoForma;
	}

	/**
	 * @return Retorna o campo arrecadadorMovimentoItem.
	 */
	public ArrecadadorMovimentoItem getArrecadadorMovimentoItem(){

		return arrecadadorMovimentoItem;
	}

	/**
	 * @param arrecadadorMovimentoItem
	 *            O arrecadadorMovimentoItem a ser setado.
	 */
	public void setArrecadadorMovimentoItem(ArrecadadorMovimentoItem arrecadadorMovimentoItem){

		this.arrecadadorMovimentoItem = arrecadadorMovimentoItem;
	}

	/**
	 * @return Retorna o campo avisoBancario.
	 */
	public AvisoBancario getAvisoBancario(){

		return avisoBancario;
	}

	/**
	 * @param avisoBancario
	 *            O avisoBancario a ser setado.
	 */
	public void setAvisoBancario(AvisoBancario avisoBancario){

		this.avisoBancario = avisoBancario;
	}

	/**
	 * @return Retorna o campo cliente.
	 */
	public Cliente getCliente(){

		return cliente;
	}

	/**
	 * @param cliente
	 *            O cliente a ser setado.
	 */
	public void setCliente(Cliente cliente){

		this.cliente = cliente;
	}

	/**
	 * @return Retorna o campo codigoAgente.
	 */
	public Integer getCodigoAgente(){

		return codigoAgente;
	}

	/**
	 * @param codigoAgente
	 *            O codigoAgente a ser setado.
	 */
	public void setCodigoAgente(Integer codigoAgente){

		this.codigoAgente = codigoAgente;
	}

	/**
	 * @return Retorna o campo conta.
	 */
	public ContaHistorico getConta(){

		return this.conta;
	}

	/**
	 * @param conta
	 *            O conta a ser setado.
	 */
	public void setConta(ContaHistorico conta){

		this.conta = conta;
	}

	/**
	 * @return Retorna o campo dataPagamento.
	 */
	public Date getDataPagamento(){

		return dataPagamento;
	}

	/**
	 * @param dataPagamento
	 *            O dataPagamento a ser setado.
	 */
	public void setDataPagamento(Date dataPagamento){

		this.dataPagamento = dataPagamento;
	}

	/**
	 * @return Retorna o campo debitoACobrar.
	 */
	public DebitoACobrar getDebitoACobrar(){

		return debitoACobrar;
	}

	/**
	 * @param debitoACobrar
	 *            O debitoACobrar a ser setado.
	 */
	public void setDebitoACobrar(DebitoACobrar debitoACobrar){

		this.debitoACobrar = debitoACobrar;
	}

	/**
	 * @return Retorna o campo debitoTipo.
	 */
	public DebitoTipo getDebitoTipo(){

		return debitoTipo;
	}

	/**
	 * @param debitoTipo
	 *            O debitoTipo a ser setado.
	 */
	public void setDebitoTipo(DebitoTipo debitoTipo){

		this.debitoTipo = debitoTipo;
	}

	/**
	 * @return Retorna o campo documentoTipo.
	 */
	public DocumentoTipo getDocumentoTipo(){

		return documentoTipo;
	}

	/**
	 * @param documentoTipo
	 *            O documentoTipo a ser setado.
	 */
	public void setDocumentoTipo(DocumentoTipo documentoTipo){

		this.documentoTipo = documentoTipo;
	}

	/**
	 * @return Retorna o campo id.
	 */
	public Integer getId(){

		return id;
	}

	/**
	 * @param id
	 *            O id a ser setado.
	 */
	public void setId(Integer id){

		this.id = id;
	}

	/**
	 * @return Retorna o campo imovel.
	 */
	public Imovel getImovel(){

		return imovel;
	}

	/**
	 * @param imovel
	 *            O imovel a ser setado.
	 */
	public void setImovel(Imovel imovel){

		this.imovel = imovel;
	}

	/**
	 * @return Retorna o campo localidade.
	 */
	public Localidade getLocalidade(){

		return localidade;
	}

	/**
	 * @param localidade
	 *            O localidade a ser setado.
	 */
	public void setLocalidade(Localidade localidade){

		this.localidade = localidade;
	}

	/**
	 * @return Retorna o campo pagamentoSituacaoAnterior.
	 */
	public gcom.arrecadacao.pagamento.PagamentoSituacao getPagamentoSituacaoAnterior(){

		return pagamentoSituacaoAnterior;
	}

	/**
	 * @param pagamentoSituacaoAnterior
	 *            O pagamentoSituacaoAnterior a ser setado.
	 */
	public void setPagamentoSituacaoAnterior(gcom.arrecadacao.pagamento.PagamentoSituacao pagamentoSituacaoAnterior){

		this.pagamentoSituacaoAnterior = pagamentoSituacaoAnterior;
	}

	/**
	 * @return Retorna o campo pagamentoSituacaoAtual.
	 */
	public gcom.arrecadacao.pagamento.PagamentoSituacao getPagamentoSituacaoAtual(){

		return this.pagamentoSituacaoAtual;
	}

	/**
	 * @param pagamentoSituacaoAtual
	 *            O pagamentoSituacaoAtual a ser setado.
	 */
	public void setPagamentoSituacaoAtual(gcom.arrecadacao.pagamento.PagamentoSituacao pagamentoSituacaoAtual){

		this.pagamentoSituacaoAtual = pagamentoSituacaoAtual;
	}

	/**
	 * @return Retorna o campo ultimaAlteracao.
	 */
	public Date getUltimaAlteracao(){

		return ultimaAlteracao;
	}

	/**
	 * @param ultimaAlteracao
	 *            O ultimaAlteracao a ser setado.
	 */
	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	/**
	 * @return Retorna o campo valorExcedente.
	 */
	public BigDecimal getValorExcedente(){

		return valorExcedente;
	}

	/**
	 * @param valorExcedente
	 *            O valorExcedente a ser setado.
	 */
	public void setValorExcedente(BigDecimal valorExcedente){

		this.valorExcedente = valorExcedente;
	}

	/**
	 * @return Retorna o campo valorPagamento.
	 */
	public BigDecimal getValorPagamento(){

		return valorPagamento;
	}

	/**
	 * @param valorPagamento
	 *            O valorPagamento a ser setado.
	 */
	public void setValorPagamento(BigDecimal valorPagamento){

		this.valorPagamento = valorPagamento;
	}

	public Short getIndicadorExpurgado(){

		return indicadorExpurgado;
	}

	public void setIndicadorExpurgado(Short indicadorExpurgado){

		this.indicadorExpurgado = indicadorExpurgado;
	}

	public Integer getNumeroPrestacao(){

		return numeroPrestacao;
	}

	public void setNumeroPrestacao(Integer numeroPrestacao){

		this.numeroPrestacao = numeroPrestacao;
	}

	public gcom.faturamento.GuiaPagamentoGeral getGuiaPagamentoGeral(){

		return guiaPagamentoGeral;
	}

	public void setGuiaPagamentoGeral(gcom.faturamento.GuiaPagamentoGeral guiaPagamentoGeral){

		this.guiaPagamentoGeral = guiaPagamentoGeral;
	}

	public String getValorPgt(){

		String retorno = "";

		if(this.getNumeroPrestacao() != null && this.getGuiaPagamentoGeral() != null){

			if(this.getGuiaPagamentoGeral().getGuiaPagamentoHistorico() != null){

				retorno = this.getGuiaPagamentoGeral().getGuiaPagamentoHistorico().obterValorFormatadoPrestacaoGuiaHistorico(
								this.numeroPrestacao.shortValue());

			}else if(this.getGuiaPagamentoGeral().getGuiaPagamento() != null){

				retorno = this.getGuiaPagamentoGeral().getGuiaPagamento().obterValorFormatadoPrestacaoGuiaHistorico(
								this.numeroPrestacao.shortValue());

			}

		}

		return retorno;

	}

}
