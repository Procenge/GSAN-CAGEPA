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

package gcom.relatorio.arrecadacao;

import gcom.relatorio.RelatorioBean;
import gcom.util.Util;

import java.math.BigDecimal;

/**
 * Bean responsável de pegar os parametros que serão exibidos na parte de detail
 * do relatório.
 * 
 * @author Sávio Luiz
 * @created 8 de Julho de 2005
 */
public class RelatorioPagamentoBean
				implements RelatorioBean {

	private String gerenciaRegional;

	private String localidade;

	private String codigoSetorComercial;

	private String matricula;

	private String inscricao;

	private String arrecadador;

	private String dataPagamento;

	private String dataPagamentoAux;

	private String mesAno;

	private String tipoDebito;

	private String valorGuia;

	private String valorPagamento;

	private String situacaoAtual;

	private String situacaoAnterior;

	private String tipoDocumento;

	private String cliente;

	private String referenciaPagamento;

	private String debito;

	private boolean indicadorHistorico;

	private String enderecoImovel;

	private String dataVencimento;

	private String nomeCliente;

	private String idGuiaPagamento;

	private String idDebitoACobrar;

	private String numeroPrestacao;

	private String numeroPagamento;

	private String mesAnoAux;

	private BigDecimal valorRemuneracao;

	private boolean exibirValorRemuneracao;

	/**
	 * Construtor da classe RelatorioAnaliseFisicoQuimicaAguaBean
	 * 
	 * @param codigo
	 *            Description of the Parameter
	 * @param nome
	 *            Description of the Parameter
	 * @param municipio
	 *            Description of the Parameter
	 * @param codPref
	 *            Description of the Parameter
	 * @param indicadorUso
	 *            Description of the Parameter
	 */
	public RelatorioPagamentoBean(String gerenciaRegional, String localidade, String matricula, String inscricao, String arrecadador,
									String dataPagamento, String mesAno, String tipoDebito, String valorGuia, String valorPagamento,
									String situacaoAtual, String numeroPrestacao, String idDebitoACobrar, String idGuiaPagamento) {

		this.gerenciaRegional = gerenciaRegional;
		this.localidade = localidade;
		this.matricula = matricula;
		this.inscricao = inscricao;
		this.arrecadador = arrecadador;
		this.dataPagamento = dataPagamento;
		this.mesAno = mesAno;
		this.tipoDebito = tipoDebito;
		this.valorGuia = valorGuia;
		this.valorPagamento = valorPagamento;
		this.situacaoAtual = situacaoAtual;
		this.numeroPrestacao = numeroPrestacao;
		this.idDebitoACobrar = idDebitoACobrar;
		this.idGuiaPagamento = idGuiaPagamento;
	}

	public RelatorioPagamentoBean(String matricula, String inscricao, String dataPagamento, String mesAno, String tipoDebito,
									String valorGuia, String valorPagamento, String situacaoAtual, String situacaoAnterior,
									String tipoDocumento, String enderecoImovel, String dataVencimento, String nomeCliente,
									String idDebitoACobrar, String idGuiaPagamento, String numeroPrestacao) {

		this.matricula = matricula;
		this.inscricao = inscricao;
		this.dataPagamento = dataPagamento;
		this.mesAno = mesAno;
		this.tipoDebito = tipoDebito;
		this.valorGuia = valorGuia;
		this.valorPagamento = valorPagamento;
		this.situacaoAtual = situacaoAtual;
		this.situacaoAnterior = situacaoAnterior;
		this.tipoDocumento = tipoDocumento;
		this.enderecoImovel = enderecoImovel;
		this.dataVencimento = dataVencimento;
		this.nomeCliente = nomeCliente;
		this.idDebitoACobrar = idDebitoACobrar;
		this.idGuiaPagamento = idGuiaPagamento;
		this.numeroPrestacao = numeroPrestacao;
	}

	public RelatorioPagamentoBean(String tipoDocumento, String dataPagamento, String localidade, String matricula, String cliente,
									String referenciaPagamento, String debito, String valorPagamento, String situacaoAtual,
									boolean indicadorHistorico) {

		this.tipoDocumento = tipoDocumento;
		this.dataPagamento = dataPagamento;
		this.localidade = localidade;
		this.matricula = matricula;
		this.cliente = cliente;
		this.referenciaPagamento = referenciaPagamento;
		this.debito = debito;
		this.valorPagamento = valorPagamento;
		this.situacaoAtual = situacaoAtual;
		this.indicadorHistorico = indicadorHistorico;
	}

	public RelatorioPagamentoBean(String tipoDocumento, String dataPagamento, String localidade, String matricula, String cliente,
									String referenciaPagamento, String debito, String valorPagamento, String situacaoAtual,
									boolean indicadorHistorico, String numeroPrestacao, String numeroPagamento) {

		this.tipoDocumento = tipoDocumento;
		this.dataPagamento = dataPagamento;
		this.localidade = localidade;
		this.matricula = matricula;
		this.cliente = cliente;
		this.referenciaPagamento = referenciaPagamento;
		this.debito = debito;
		this.valorPagamento = valorPagamento;
		this.situacaoAtual = situacaoAtual;
		this.indicadorHistorico = indicadorHistorico;
		this.numeroPrestacao = numeroPrestacao;
		this.numeroPagamento = numeroPagamento;
	}

	/**
	 * @return Retorna o campo indicadorHistorico.
	 */
	public boolean getIndicadorHistorico(){

		return indicadorHistorico;
	}

	/**
	 * @param indicadorHistorico
	 *            O indicadorHistorico a ser setado.
	 */
	public void setIndicadorHistorico(boolean indicadorHistorico){

		this.indicadorHistorico = indicadorHistorico;
	}

	public String getArrecadador(){

		return arrecadador;
	}

	public void setArrecadador(String arrecadador){

		this.arrecadador = arrecadador;
	}

	public String getDataPagamento(){

		return dataPagamento;
	}

	public void setDataPagamento(String dataPagamento){

		this.dataPagamento = dataPagamento;
	}

	public String getInscricao(){

		return inscricao;
	}

	public void setInscricao(String inscricao){

		this.inscricao = inscricao;
	}

	public String getLocalidade(){

		return localidade;
	}

	public void setLocalidade(String localidade){

		this.localidade = localidade;
	}

	public String getMatricula(){

		return matricula;
	}

	public void setMatricula(String matricula){

		this.matricula = matricula;
	}

	public String getMesAno(){

		return mesAno;
	}

	public void setMesAno(String mesAno){

		this.mesAno = mesAno;
	}

	public String getGerenciaRegional(){

		return gerenciaRegional;
	}

	public void setGerenciaRegional(String gerenciaRegional){

		this.gerenciaRegional = gerenciaRegional;
	}

	public String getSituacaoAtual(){

		return situacaoAtual;
	}

	public void setSituacaoAtual(String situacaoAtual){

		this.situacaoAtual = situacaoAtual;
	}

	public String getTipoDebito(){

		return tipoDebito;
	}

	public void setTipoDebito(String tipoDebito){

		this.tipoDebito = tipoDebito;
	}

	public String getValorPagamento(){

		return valorPagamento;
	}

	public void setValorPagamento(String valorPagamento){

		this.valorPagamento = valorPagamento;
	}

	public String getValorGuia(){

		return valorGuia;
	}

	public void setValorGuia(String valorGuia){

		this.valorGuia = valorGuia;
	}

	/**
	 * @return Retorna o campo situacaoAnterior.
	 */
	public String getSituacaoAnterior(){

		return situacaoAnterior;
	}

	/**
	 * @param situacaoAnterior
	 *            O situacaoAnterior a ser setado.
	 */
	public void setSituacaoAnterior(String situacaoAnterior){

		this.situacaoAnterior = situacaoAnterior;
	}

	/**
	 * @return Retorna o campo tipoDocumento.
	 */
	public String getTipoDocumento(){

		return tipoDocumento;
	}

	/**
	 * @param tipoDocumento
	 *            O tipoDocumento a ser setado.
	 */
	public void setTipoDocumento(String tipoDocumento){

		this.tipoDocumento = tipoDocumento;
	}

	/**
	 * @return Retorna o campo cliente.
	 */
	public String getCliente(){

		return cliente;
	}

	/**
	 * @param cliente
	 *            O cliente a ser setado.
	 */
	public void setCliente(String cliente){

		this.cliente = cliente;
	}

	/**
	 * @return Retorna o campo debito.
	 */
	public String getDebito(){

		return debito;
	}

	/**
	 * @param debito
	 *            O debito a ser setado.
	 */
	public void setDebito(String debito){

		this.debito = debito;
	}

	/**
	 * @return Retorna o campo referenciaPagamento.
	 */
	public String getReferenciaPagamento(){

		return referenciaPagamento;
	}

	/**
	 * @param referenciaPagamento
	 *            O referenciaPagamento a ser setado.
	 */
	public void setReferenciaPagamento(String referenciaPagamento){

		this.referenciaPagamento = referenciaPagamento;
	}

	/**
	 * @return the enderecoImovel
	 */
	public String getEnderecoImovel(){

		return enderecoImovel;
	}

	/**
	 * @param enderecoImovel
	 *            the enderecoImovel to set
	 */
	public void setEnderecoImovel(String enderecoImovel){

		this.enderecoImovel = enderecoImovel;
	}

	/**
	 * @return the dataVencimento
	 */
	public String getDataVencimento(){

		return dataVencimento;
	}

	/**
	 * @param dataVencimento
	 *            the dataVencimento to set
	 */
	public void setDataVencimento(String dataVencimento){

		this.dataVencimento = dataVencimento;
	}

	/**
	 * @return the nomeCliente
	 */
	public String getNomeCliente(){

		return nomeCliente;
	}

	/**
	 * @param nomeCliente
	 *            the nomeCliente to set
	 */
	public void setNomeCliente(String nomeCliente){

		this.nomeCliente = nomeCliente;
	}

	/**
	 * @return the idGuiaPagamento
	 */
	public String getIdGuiaPagamento(){

		return idGuiaPagamento;
	}

	/**
	 * @param idGuiaPagamento
	 *            the idGuiaPagamento to set
	 */
	public void setIdGuiaPagamento(String idGuiaPagamento){

		this.idGuiaPagamento = idGuiaPagamento;
	}

	/**
	 * @return the idDebitoACobrar
	 */
	public String getIdDebitoACobrar(){

		return idDebitoACobrar;
	}

	/**
	 * @param idDebitoACobrar
	 *            the idDebitoACobrar to set
	 */
	public void setIdDebitoACobrar(String idDebitoACobrar){

		this.idDebitoACobrar = idDebitoACobrar;
	}

	/**
	 * @return the numeroPrestacao
	 */
	public String getNumeroPrestacao(){

		return numeroPrestacao;
	}

	/**
	 * @param numeroPrestacao
	 *            the numeroPrestacao to set
	 */
	public void setNumeroPrestacao(String numeroPrestacao){

		this.numeroPrestacao = numeroPrestacao;
	}

	/**
	 * @return the numeroPagamento
	 */

	public String getNumeroPagamento(){

		return numeroPagamento;
	}

	/**
	 * @param numeroPagamento
	 *            the numeroPagamento to set
	 */

	public void setNumeroPagamento(String numeroPagamento){

		this.numeroPagamento = numeroPagamento;
	}

	public String getCodigoSetorComercial(){

		return codigoSetorComercial;
	}

	public void setCodigoSetorComercial(String codigoSetorComercial){

		this.codigoSetorComercial = codigoSetorComercial;
	}

	public String getDataPagamentoAux(){

		return dataPagamentoAux;
	}

	public void setDataPagamentoAux(String dataPagamentoAux){

		this.dataPagamentoAux = dataPagamentoAux;
	}

	public String getMesAnoAux(){

		return mesAnoAux;
	}

	public void setMesAnoAux(String mesAnoAux){

		this.mesAnoAux = mesAnoAux;
	}

	public String getGuiaPagamentoComPrestacao(){

		String retorno = "";

		String idGuiaPagamento = this.getIdGuiaPagamento();
		String numeroPrestacao = this.getNumeroPrestacao();

		if(!Util.isVazioOuBranco(idGuiaPagamento)){
			retorno = idGuiaPagamento + " / ";
		}

		if(!Util.isVazioOuBranco(numeroPrestacao)){
			retorno = retorno + numeroPrestacao;
		}

		return retorno;
	}

	public BigDecimal getValorRemuneracao(){

		return valorRemuneracao;
	}

	public void setValorRemuneracao(BigDecimal valorRemuneracao){

		this.valorRemuneracao = valorRemuneracao;
	}

	public boolean getExibirValorRemuneracao(){

		return exibirValorRemuneracao;
	}

	public void setExibirValorRemuneracao(boolean exibirValorRemuneracao){

		this.exibirValorRemuneracao = exibirValorRemuneracao;
	}

	public BigDecimal getValorGuiaBD(){

		String valorGuia = this.getValorGuia();

		return Util.formatarMoedaRealparaBigDecimal(valorGuia);
	}

	public BigDecimal getValorPagamentoBD(){

		String valorPagamento = this.getValorPagamento();

		return Util.formatarMoedaRealparaBigDecimal(valorPagamento);
	}

}
