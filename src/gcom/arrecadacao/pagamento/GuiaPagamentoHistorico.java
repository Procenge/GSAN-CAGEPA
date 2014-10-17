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

import gcom.atendimentopublico.ordemservico.OrdemServico;
import gcom.atendimentopublico.registroatendimento.RegistroAtendimento;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.cliente.ClienteGuiaPagamentoHistorico;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.SetorComercial;
import gcom.cobranca.DocumentoTipo;
import gcom.cobranca.parcelamento.Parcelamento;
import gcom.cobranca.prescricao.PrescricaoComando;
import gcom.faturamento.GuiaPagamentoGeral;
import gcom.faturamento.debito.DebitoCreditoSituacao;
import gcom.util.Util;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class GuiaPagamentoHistorico
				implements Serializable {

	private static final long serialVersionUID = 1L;

	/** identifier field */
	private Integer id;

	/** persistent field */
	private BigDecimal valorDebito;

	/** persistent field */
	private Date ultimaAlteracao;

	/** persistent field */
	private Short numeroPrestacaoTotal;

	/** nullable persistent field */
	private GuiaPagamentoGeral guiaPagamentoGeral;

	/** persistent field */
	private Cliente cliente;

	/** persistent field */
	private Imovel imovel;

	/** persistent field */
	private Localidade localidade;

	/** persistent field */
	private Parcelamento parcelamento;

	/** persistent field */
	private DebitoCreditoSituacao debitoCreditoSituacaoAtual;

	/** persistent field */
	private RegistroAtendimento registroAtendimento;

	/** persistent field */
	private DocumentoTipo documentoTipo;

	/** persistent field */
	private OrdemServico ordemServico;

	/** persistent field */
	private Set guiaPagamentoCategoriaHistoricos;

	/** persistent field */
	private Set<GuiaPagamentoPrestacaoHistorico> guiasPagamentoPrestacaoHistorico;

	/** persistent field */
	private Set<GuiaPagamentoPrestacao> guiasPagamentoPrestacao;

	/** persistent field */
	private GuiaPagamentoGeral origem;

	/** persistent field */
	private SetorComercial setorComercial;

	private Date dataInclusao;

	/** persistent field */
	private Short indicadorEmissaoObservacaoRA = 2;

	private Integer numeroContratoParcelOrgaoPublico;

	private Set<GuiaPagamentoCategoriaHistorico> guiasPagamentoCategoriaHistorico;

	private Set<ClienteGuiaPagamentoHistorico> clientesGuiaPagamentoHistorico;

	private PrescricaoComando prescricaoComando;

	private DebitoCreditoSituacao debitoCreditoSituacaoAnterior;

	/**
	 * Default Constructor
	 */
	public GuiaPagamentoHistorico() {

	}

	public String toString(){

		return new ToStringBuilder(this).append("gpagId", getId()).toString();
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
	 * @return Retorna o campo guiaPagamentoCategoriaHistoricos.
	 */
	public Set getGuiaPagamentoCategoriaHistoricos(){

		return guiaPagamentoCategoriaHistoricos;
	}

	/**
	 * @param guiaPagamentoCategoriaHistoricos
	 *            O guiaPagamentoCategoriaHistoricos a ser setado.
	 */
	public void setGuiaPagamentoCategoriaHistoricos(Set guiaPagamentoCategoriaHistoricos){

		this.guiaPagamentoCategoriaHistoricos = guiaPagamentoCategoriaHistoricos;
	}

	/**
	 * @return Retorna o campo guiaPagamentoGeral.
	 */
	public GuiaPagamentoGeral getGuiaPagamentoGeral(){

		return guiaPagamentoGeral;
	}

	/**
	 * @param guiaPagamentoGeral
	 *            O guiaPagamentoGeral a ser setado.
	 */
	public void setGuiaPagamentoGeral(GuiaPagamentoGeral guiaPagamentoGeral){

		this.guiaPagamentoGeral = guiaPagamentoGeral;
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
	 * @return Retorna o campo ordemServico.
	 */
	public OrdemServico getOrdemServico(){

		return ordemServico;
	}

	/**
	 * @param ordemServico
	 *            O ordemServico a ser setado.
	 */
	public void setOrdemServico(OrdemServico ordemServico){

		this.ordemServico = ordemServico;
	}

	/**
	 * @return Retorna o campo parcelamento.
	 */
	public Parcelamento getParcelamento(){

		return parcelamento;
	}

	/**
	 * @param parcelamento
	 *            O parcelamento a ser setado.
	 */
	public void setParcelamento(Parcelamento parcelamento){

		this.parcelamento = parcelamento;
	}

	/**
	 * @return Retorna o campo registroAtendimento.
	 */
	public RegistroAtendimento getRegistroAtendimento(){

		return registroAtendimento;
	}

	/**
	 * @param registroAtendimento
	 *            O registroAtendimento a ser setado.
	 */
	public void setRegistroAtendimento(RegistroAtendimento registroAtendimento){

		this.registroAtendimento = registroAtendimento;
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
	 * @return Retorna o campo valorDebito.
	 */
	public BigDecimal getValorDebito(){

		return valorDebito;
	}

	/**
	 * @param valorDebito
	 *            O valorDebito a ser setado.
	 */
	public void setValorDebito(BigDecimal valorDebito){

		this.valorDebito = valorDebito;
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

	/**
	 * @return the debitoCreditoSituacaoAtual
	 */
	public DebitoCreditoSituacao getDebitoCreditoSituacaoAtual(){

		return debitoCreditoSituacaoAtual;
	}

	/**
	 * @param debitoCreditoSituacaoAtual
	 *            the debitoCreditoSituacaoAtual to set
	 */
	public void setDebitoCreditoSituacaoAtual(DebitoCreditoSituacao debitoCreditoSituacaoAtual){

		this.debitoCreditoSituacaoAtual = debitoCreditoSituacaoAtual;
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

	public Set<GuiaPagamentoPrestacaoHistorico> getGuiasPagamentoPrestacaoHistorico(){

		return guiasPagamentoPrestacaoHistorico;
	}

	public void setGuiasPagamentoPrestacaoHistorico(Set<GuiaPagamentoPrestacaoHistorico> guiasPagamentoPrestacaoHistorico){

		this.guiasPagamentoPrestacaoHistorico = guiasPagamentoPrestacaoHistorico;
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

	public Set<GuiaPagamentoPrestacao> getGuiasPagamentoPrestacao(){

		return guiasPagamentoPrestacao;
	}

	public void setGuiasPagamentoPrestacao(Set<GuiaPagamentoPrestacao> guiasPagamentoPrestacao){

		this.guiasPagamentoPrestacao = guiasPagamentoPrestacao;
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

	public void setGuiasPagamentoCategoriaHistorico(Set<GuiaPagamentoCategoriaHistorico> guiasPagamentoCategoriaHistorico){

		this.guiasPagamentoCategoriaHistorico = guiasPagamentoCategoriaHistorico;
	}

	public Set<GuiaPagamentoCategoriaHistorico> getGuiasPagamentoCategoriaHistorico(){

		return guiasPagamentoCategoriaHistorico;
	}

	public Set<ClienteGuiaPagamentoHistorico> getClientesGuiaPagamentoHistorico(){

		return clientesGuiaPagamentoHistorico;
	}

	public void setClientesGuiaPagamentoHistorico(Set<ClienteGuiaPagamentoHistorico> clientesGuiaPagamentoHistorico){

		this.clientesGuiaPagamentoHistorico = clientesGuiaPagamentoHistorico;
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
