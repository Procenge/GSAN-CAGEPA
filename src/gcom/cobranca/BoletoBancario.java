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

package gcom.cobranca;

import gcom.arrecadacao.Arrecadador;
import gcom.arrecadacao.pagamento.GuiaPagamento;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.imovel.Imovel;
import gcom.cobranca.parcelamento.Parcelamento;
import gcom.faturamento.conta.Conta;
import gcom.faturamento.debito.DebitoACobrar;
import gcom.interceptor.ControleAlteracao;
import gcom.interceptor.ObjetoTransacao;
import gcom.util.Util;
import gcom.util.filtro.Filtro;
import gcom.util.filtro.ParametroSimples;

import java.math.BigDecimal;
import java.util.Date;

@ControleAlteracao()
public class BoletoBancario
				extends ObjetoTransacao {

	public static final int OPERACAO_BOLETO_BANCARIO_ATUALIZAR = 302462;

	public static final int OPERACAO_BOLETO_BANCARIO_CANCELAR = 302466;

	private static final long serialVersionUID = 1L;

	private Integer id;

	private String convenio;

	private Integer numeroSequencial;

	@ControleAlteracao(value = FiltroBoletoBancario.SITUACAO, funcionalidade = {OPERACAO_BOLETO_BANCARIO_CANCELAR})
	private BoletoBancarioSituacao boletoBancarioSituacao;

	private Integer anoMesReferencia;

	private Arrecadador arrecadador;

	@ControleAlteracao(funcionalidade = {OPERACAO_BOLETO_BANCARIO_ATUALIZAR})
	private Date dataVencimento;

	private BigDecimal valorDebito;

	private Date dataEmissao;

	private Imovel imovel;

	private Cliente cliente;

	private DocumentoTipo documentoTipo;

	private CobrancaDocumento documentoCobranca;

	private Conta conta;

	private DebitoACobrar debitoACobrar;

	private GuiaPagamento guiaPagamento;

	private Integer numeroPrestacoes;

	private Integer idOriginal;

	private Date dataPagamento;

	private Date dataCredito;

	private Date dataGeracaoCarta;

	private BigDecimal valorPago;

	private BigDecimal valorTarifa;

	@ControleAlteracao(value = FiltroBoletoBancario.MOTIVO_CANCELAMENTO, funcionalidade = {OPERACAO_BOLETO_BANCARIO_CANCELAR})
	private BoletoBancarioMotivoCancelamento boletoBancarioMotivoCancelamento;

	@ControleAlteracao(funcionalidade = {OPERACAO_BOLETO_BANCARIO_CANCELAR})
	private Date dataCancelamento;

	private Parcelamento parcelamento;

	private Integer numeroSequencialArquivoMigracao;

	private Date ultimaAlteracao;

	private Date dataEntrada;

	private String ocorrenciaMigracao;

	/**
	 * @param id
	 * @param convenio
	 * @param numeroSequencial
	 * @param boletoBancarioSituacao
	 * @param anoMesReferencia
	 * @param arrecadador
	 * @param dataVencimento
	 * @param valorDebito
	 * @param dataEmissao
	 * @param imovel
	 * @param cliente
	 * @param documentoTipo
	 * @param documentoCobranca
	 * @param conta
	 * @param debitoACobrar
	 * @param guiaPagamento
	 * @param numeroPrestacoes
	 * @param idBoletoOriginal
	 * @param dataPagamento
	 * @param dataCredito
	 * @param dataGeracaoCarta
	 * @param valorPago
	 * @param valorTarifa
	 * @param boletoBancarioMotivoCancelamento
	 * @param dataCancelamento
	 * @param parcelamento
	 * @param numeroSequenciaMigracao
	 * @param ultimaAlteracao
	 */
	public BoletoBancario(Integer id, String convenio, Integer numeroSequencial, BoletoBancarioSituacao boletoBancarioSituacao,
							Integer anoMesReferencia, Arrecadador arrecadador, Date dataVencimento, BigDecimal valorDebito,
							Date dataEmissao, Imovel imovel, Cliente cliente, DocumentoTipo documentoTipo,
							CobrancaDocumento documentoCobranca, Conta conta, DebitoACobrar debitoACobrar, GuiaPagamento guiaPagamento,
							Integer numeroPrestacoes, Integer idOriginal, Date dataPagamento, Date dataCredito, Date dataGeracaoCarta,
							BigDecimal valorPago, BigDecimal valorTarifa,
							BoletoBancarioMotivoCancelamento boletoBancarioMotivoCancelamento, Date dataCancelamento,
							Parcelamento parcelamento, Integer numeroSequencialArquivoMigracao, Date ultimaAlteracao) {

		this.id = id;
		this.convenio = convenio;
		this.numeroSequencial = numeroSequencial;
		this.boletoBancarioSituacao = boletoBancarioSituacao;
		this.anoMesReferencia = anoMesReferencia;
		this.arrecadador = arrecadador;
		this.dataVencimento = dataVencimento;
		this.valorDebito = valorDebito;
		this.dataEmissao = dataEmissao;
		this.imovel = imovel;
		this.cliente = cliente;
		this.documentoTipo = documentoTipo;
		this.documentoCobranca = documentoCobranca;
		this.conta = conta;
		this.debitoACobrar = debitoACobrar;
		this.guiaPagamento = guiaPagamento;
		this.numeroPrestacoes = numeroPrestacoes;
		this.idOriginal = idOriginal;
		this.dataPagamento = dataPagamento;
		this.dataCredito = dataCredito;
		this.dataGeracaoCarta = dataGeracaoCarta;
		this.valorPago = valorPago;
		this.valorTarifa = valorTarifa;
		this.boletoBancarioMotivoCancelamento = boletoBancarioMotivoCancelamento;
		this.dataCancelamento = dataCancelamento;
		this.parcelamento = parcelamento;
		this.numeroSequencialArquivoMigracao = numeroSequencialArquivoMigracao;
		this.ultimaAlteracao = ultimaAlteracao;
	}

	public BoletoBancario(Integer id) {

		this.id = id;
	}

	/** default constructor */
	public BoletoBancario() {

	}

	/**
	 * @return the id
	 */
	public Integer getId(){

		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Integer id){

		this.id = id;
	}

	/**
	 * @return the convenio
	 */
	public String getConvenio(){

		return convenio;
	}

	/**
	 * @param convenio
	 *            the convenio to set
	 */
	public void setConvenio(String convenio){

		this.convenio = convenio;
	}

	/**
	 * @return the numeroSequencial
	 */
	public Integer getNumeroSequencial(){

		return numeroSequencial;
	}

	/**
	 * @param numeroSequencial
	 *            the numeroSequencial to set
	 */
	public void setNumeroSequencial(Integer numeroSequencial){

		this.numeroSequencial = numeroSequencial;
	}

	/**
	 * @return the boletoBancarioSituacao
	 */
	public BoletoBancarioSituacao getBoletoBancarioSituacao(){

		return boletoBancarioSituacao;
	}

	/**
	 * @param boletoBancarioSituacao
	 *            the boletoBancarioSituacao to set
	 */
	public void setBoletoBancarioSituacao(BoletoBancarioSituacao boletoBancarioSituacao){

		this.boletoBancarioSituacao = boletoBancarioSituacao;
	}

	/**
	 * @return the anoMesReferencia
	 */
	public Integer getAnoMesReferencia(){

		return anoMesReferencia;
	}

	/**
	 * @param anoMesReferencia
	 *            the anoMesReferencia to set
	 */
	public void setAnoMesReferencia(Integer anoMesReferencia){

		this.anoMesReferencia = anoMesReferencia;
	}

	/**
	 * @return the arrecadador
	 */
	public Arrecadador getArrecadador(){

		return arrecadador;
	}

	/**
	 * @param arrecadador
	 *            the arrecadador to set
	 */
	public void setArrecadador(Arrecadador arrecadador){

		this.arrecadador = arrecadador;
	}

	/**
	 * @return the dataVencimento
	 */
	public Date getDataVencimento(){

		return dataVencimento;
	}

	/**
	 * @param dataVencimento
	 *            the dataVencimento to set
	 */
	public void setDataVencimento(Date dataVencimento){

		this.dataVencimento = dataVencimento;
	}

	/**
	 * @return the valorDebito
	 */
	public BigDecimal getValorDebito(){

		return valorDebito;
	}

	/**
	 * @param valorDebito
	 *            the valorDebito to set
	 */
	public void setValorDebito(BigDecimal valorDebito){

		this.valorDebito = valorDebito;
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

	/**
	 * @return the imovel
	 */
	public Imovel getImovel(){

		return imovel;
	}

	/**
	 * @param imovel
	 *            the imovel to set
	 */
	public void setImovel(Imovel imovel){

		this.imovel = imovel;
	}

	/**
	 * @return the cliente
	 */
	public Cliente getCliente(){

		return cliente;
	}

	/**
	 * @param cliente
	 *            the cliente to set
	 */
	public void setCliente(Cliente cliente){

		this.cliente = cliente;
	}

	/**
	 * @return the documentoTipo
	 */
	public DocumentoTipo getDocumentoTipo(){

		return documentoTipo;
	}

	/**
	 * @param documentoTipo
	 *            the documentoTipo to set
	 */
	public void setDocumentoTipo(DocumentoTipo documentoTipo){

		this.documentoTipo = documentoTipo;
	}

	/**
	 * @return the documentoCobranca
	 */
	public CobrancaDocumento getDocumentoCobranca(){

		return documentoCobranca;
	}

	/**
	 * @param documentoCobranca
	 *            the documentoCobranca to set
	 */
	public void setDocumentoCobranca(CobrancaDocumento documentoCobranca){

		this.documentoCobranca = documentoCobranca;
	}

	/**
	 * @return the conta
	 */
	public Conta getConta(){

		return conta;
	}

	/**
	 * @param conta
	 *            the conta to set
	 */
	public void setConta(Conta conta){

		this.conta = conta;
	}

	/**
	 * @return the debitoACobrar
	 */
	public DebitoACobrar getDebitoACobrar(){

		return debitoACobrar;
	}

	/**
	 * @param debitoACobrar
	 *            the debitoACobrar to set
	 */
	public void setDebitoACobrar(DebitoACobrar debitoACobrar){

		this.debitoACobrar = debitoACobrar;
	}

	/**
	 * @return the guiaPagamento
	 */
	public GuiaPagamento getGuiaPagamento(){

		return guiaPagamento;
	}

	/**
	 * @param guiaPagamento
	 *            the guiaPagamento to set
	 */
	public void setGuiaPagamento(GuiaPagamento guiaPagamento){

		this.guiaPagamento = guiaPagamento;
	}

	/**
	 * @return the numeroPrestacoes
	 */
	public Integer getNumeroPrestacoes(){

		return numeroPrestacoes;
	}

	/**
	 * @param numeroPrestacoes
	 *            the numeroPrestacoes to set
	 */
	public void setNumeroPrestacoes(Integer numeroPrestacoes){

		this.numeroPrestacoes = numeroPrestacoes;
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
	 * @return the dataPagamento
	 */
	public Date getDataPagamento(){

		return dataPagamento;
	}

	/**
	 * @param dataPagamento
	 *            the dataPagamento to set
	 */
	public void setDataPagamento(Date dataPagamento){

		this.dataPagamento = dataPagamento;
	}

	/**
	 * @return the dataCredito
	 */
	public Date getDataCredito(){

		return dataCredito;
	}

	/**
	 * @param dataCredito
	 *            the dataCredito to set
	 */
	public void setDataCredito(Date dataCredito){

		this.dataCredito = dataCredito;
	}

	/**
	 * @return the dataGeracaoCarta
	 */
	public Date getDataGeracaoCarta(){

		return dataGeracaoCarta;
	}

	/**
	 * @param dataGeracaoCarta
	 *            the dataGeracaoCarta to set
	 */
	public void setDataGeracaoCarta(Date dataGeracaoCarta){

		this.dataGeracaoCarta = dataGeracaoCarta;
	}

	/**
	 * @return the valorPago
	 */
	public BigDecimal getValorPago(){

		return valorPago;
	}

	/**
	 * @param valorPago
	 *            the valorPago to set
	 */
	public void setValorPago(BigDecimal valorPago){

		this.valorPago = valorPago;
	}

	/**
	 * @return the valorTarifa
	 */
	public BigDecimal getValorTarifa(){

		return valorTarifa;
	}

	/**
	 * @param valorTarifa
	 *            the valorTarifa to set
	 */
	public void setValorTarifa(BigDecimal valorTarifa){

		this.valorTarifa = valorTarifa;
	}

	/**
	 * @return the boletoBancarioMotivoCancelamento
	 */
	public BoletoBancarioMotivoCancelamento getBoletoBancarioMotivoCancelamento(){

		return boletoBancarioMotivoCancelamento;
	}

	/**
	 * @param boletoBancarioMotivoCancelamento
	 *            the boletoBancarioMotivoCancelamento to set
	 */
	public void setBoletoBancarioMotivoCancelamento(BoletoBancarioMotivoCancelamento boletoBancarioMotivoCancelamento){

		this.boletoBancarioMotivoCancelamento = boletoBancarioMotivoCancelamento;
	}

	/**
	 * @return the dataCancelamento
	 */
	public Date getDataCancelamento(){

		return dataCancelamento;
	}

	/**
	 * @param dataCancelamento
	 *            the dataCancelamento to set
	 */
	public void setDataCancelamento(Date dataCancelamento){

		this.dataCancelamento = dataCancelamento;
	}

	/**
	 * @return the parcelamento
	 */
	public Parcelamento getParcelamento(){

		return parcelamento;
	}

	/**
	 * @param parcelamento
	 *            the parcelamento to set
	 */
	public void setParcelamento(Parcelamento parcelamento){

		this.parcelamento = parcelamento;
	}

	/**
	 * @return the numeroSequencialArquivoMigracao
	 */
	public Integer getNumeroSequencialArquivoMigracao(){

		return numeroSequencialArquivoMigracao;
	}

	/**
	 * @param numeroSequencialArquivoMigracao
	 *            the numeroSequencialArquivoMigracao to set
	 */
	public void setNumeroSequencialArquivoMigracao(Integer numeroSequencialArquivoMigracao){

		this.numeroSequencialArquivoMigracao = numeroSequencialArquivoMigracao;
	}

	/**
	 * @return the ultimaAlteracao
	 */
	public Date getUltimaAlteracao(){

		return ultimaAlteracao;
	}

	/**
	 * @param ultimaAlteracao
	 *            the ultimaAlteracao to set
	 */
	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	/**
	 * @return the dataEntrada
	 */
	public Date getDataEntrada(){

		return dataEntrada;
	}

	/**
	 * @param dataEntrada
	 *            the dataEntrada to set
	 */
	public void setDataEntrada(Date dataEntrada){

		this.dataEntrada = dataEntrada;
	}

	public String getOcorrenciaMigracao(){

		return ocorrenciaMigracao;
	}

	public void setOcorrenciaMigracao(String ocorrenciaMigracao){

		this.ocorrenciaMigracao = ocorrenciaMigracao;
	}

	public String getNossoNumero(){

		String nossoNumero = "";

		String convenioAux = this.getConvenio();

		if(convenioAux == null){
			convenioAux = String.valueOf(0);
		}

		Integer numeroSequencialAux = this.getNumeroSequencial();

		if(numeroSequencialAux == null){
			numeroSequencialAux = 0;
		}

		nossoNumero = Util.completarStringZeroEsquerda(convenioAux, 7)
						+ Util.completarStringZeroEsquerda(String.valueOf(numeroSequencialAux), 10);

		return nossoNumero;
	}

	public Filtro retornaFiltro(){

		FiltroBoletoBancario filtroBoletoBancario = new FiltroBoletoBancario();
		filtroBoletoBancario.adicionarParametro(new ParametroSimples(FiltroBoletoBancario.ID, this.getId()));

		return filtroBoletoBancario;
	}

	public String[] retornaCamposChavePrimaria(){

		String[] retorno = new String[1];
		retorno[0] = "id";
		return retorno;
	}

	public String getDescricaoParaRegistroTransacao(){

		return getId() + "";
	}

}
