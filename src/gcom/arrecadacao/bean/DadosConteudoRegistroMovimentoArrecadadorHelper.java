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

package gcom.arrecadacao.bean;

import gcom.arrecadacao.pagamento.Pagamento;
import gcom.arrecadacao.pagamento.PagamentoHistorico;

import java.util.Collection;

/**
 * Classe que irá auxiliar no formato do retorno da pesquisa dos itens de um
 * determinado movimento do arrecadador
 * 
 * @author Raphael Rossiter
 * @date 20/03/2006
 */
public class DadosConteudoRegistroMovimentoArrecadadorHelper {

	private String codigoRegistro;

	private String identificacaoClienteEmpresa;

	private String agenciaDebito;

	private String identificacaoClienteBanco;

	private String dataOpcaoExclusao;

	private String descricaoMovimento;

	private String ocorrencia;

	private String indicadorAceitacao;

	private String dataVencimentoDebito;

	private String valorDebito;

	private String codigoMoeda;

	private String codigoRetorno;

	private String mesAnoReferenciaConta;

	private String digitoVerificadorConta;

	private String identificacaoAgenciaContaDigitoCreditada;

	private String dataPagamento;

	private String dataPrevistaCredito;

	private String valorRecebido;

	private String valorTarifa;

	private String nsr;

	private String codigoAgenciaArrecadadora;

	private String formaArrecadacaoCaptura;

	private String numeroAutenticacaoCaixaOUCodigoTransacao;

	private String formaPagamento;

	private String codigoAgencia;

	private String nomeAgencia;

	private String nomeLogradouro;

	private String numero;

	private String codigoCep;

	private String sufixoCep;

	private String nomeCidade;

	private String siglaUnidadeFederacao;

	private String situacaoAgencia;

	private String dataEnvio;

	private String dataRetorno;

	private String codigoMovimentoRetorno;

	private DadosConteudoCodigoBarrasHelper dadosConteudoCodigoBarrasHelper;

	private Collection<Pagamento> colecaoPagamentos;

	private Collection<String> ocorrencias;

	private String agenciaContaDigito;

	private String codigoMovimentoRemessa;

	private String dataVencimentoTitulo;

	private String valorNominalTitulo;

	private String dataDesconto1;

	private String valorPercentualDesconto1;

	private String nomeSacado;

	private String enderecoSacado;

	private String bairro;

	private String CEP;

	private String dataDesconto2;

	private String valorPercentualDesconto2;

	private String dataDesconto3;

	private String valorPercentualDesconto3;

	private Collection<PagamentoHistorico> colecaoPagamentoHistorico;

	private String possuiPagamentos;

	public Collection<Pagamento> getColecaoPagamentos(){

		return colecaoPagamentos;
	}

	public void setColecaoPagamentos(Collection<Pagamento> colecaoPagamentos){

		this.colecaoPagamentos = colecaoPagamentos;
	}

	public String getCodigoMoeda(){

		return codigoMoeda;
	}

	public void setCodigoMoeda(String codigoMoeda){

		this.codigoMoeda = codigoMoeda;
	}

	public String getCodigoRetorno(){

		return codigoRetorno;
	}

	public void setCodigoRetorno(String codigoRetorno){

		this.codigoRetorno = codigoRetorno;
	}

	public String getDataVencimentoDebito(){

		return dataVencimentoDebito;
	}

	public void setDataVencimentoDebito(String dataVencimentoDebito){

		this.dataVencimentoDebito = dataVencimentoDebito;
	}

	public String getDigitoVerificadorConta(){

		return digitoVerificadorConta;
	}

	public void setDigitoVerificadorConta(String digitoVerificadorConta){

		this.digitoVerificadorConta = digitoVerificadorConta;
	}

	public String getMesAnoReferenciaConta(){

		return mesAnoReferenciaConta;
	}

	public void setMesAnoReferenciaConta(String mesAnoReferenciaConta){

		this.mesAnoReferenciaConta = mesAnoReferenciaConta;
	}

	public String getValorDebito(){

		return valorDebito;
	}

	public void setValorDebito(String valorDebito){

		this.valorDebito = valorDebito;
	}

	public DadosConteudoRegistroMovimentoArrecadadorHelper() {

	}

	public String getAgenciaDebito(){

		return agenciaDebito;
	}

	public void setAgenciaDebito(String agenciaDebito){

		this.agenciaDebito = agenciaDebito;
	}

	public String getCodigoRegistro(){

		return codigoRegistro;
	}

	public void setCodigoRegistro(String codigoRegistro){

		this.codigoRegistro = codigoRegistro;
	}

	public String getDataOpcaoExclusao(){

		return dataOpcaoExclusao;
	}

	public void setDataOpcaoExclusao(String dataOpcaoExclusao){

		this.dataOpcaoExclusao = dataOpcaoExclusao;
	}

	public String getDescricaoMovimento(){

		return descricaoMovimento;
	}

	public void setDescricaoMovimento(String descricaoMovimento){

		this.descricaoMovimento = descricaoMovimento;
	}

	public String getIdentificacaoClienteBanco(){

		return identificacaoClienteBanco;
	}

	public void setIdentificacaoClienteBanco(String identificacaoClienteBanco){

		this.identificacaoClienteBanco = identificacaoClienteBanco;
	}

	public String getIdentificacaoClienteEmpresa(){

		return identificacaoClienteEmpresa;
	}

	public void setIdentificacaoClienteEmpresa(String identificacaoClienteEmpresa){

		this.identificacaoClienteEmpresa = identificacaoClienteEmpresa;
	}

	public String getIndicadorAceitacao(){

		return indicadorAceitacao;
	}

	public void setIndicadorAceitacao(String indicadorAceitacao){

		this.indicadorAceitacao = indicadorAceitacao;
	}

	public String getOcorrencia(){

		return ocorrencia;
	}

	public void setOcorrencia(String ocorrencia){

		this.ocorrencia = ocorrencia;
	}

	public String getCodigoAgenciaArrecadadora(){

		return codigoAgenciaArrecadadora;
	}

	public void setCodigoAgenciaArrecadadora(String codigoAgenciaArrecadadora){

		this.codigoAgenciaArrecadadora = codigoAgenciaArrecadadora;
	}

	public String getDataPagamento(){

		return dataPagamento;
	}

	public void setDataPagamento(String dataPagamento){

		this.dataPagamento = dataPagamento;
	}

	public String getDataPrevistaCredito(){

		return dataPrevistaCredito;
	}

	public void setDataPrevistaCredito(String dataPrevistaCredito){

		this.dataPrevistaCredito = dataPrevistaCredito;
	}

	public String getFormaArrecadacaoCaptura(){

		return formaArrecadacaoCaptura;
	}

	public void setFormaArrecadacaoCaptura(String formaArrecadacaoCaptura){

		this.formaArrecadacaoCaptura = formaArrecadacaoCaptura;
	}

	public String getFormaPagamento(){

		return formaPagamento;
	}

	public void setFormaPagamento(String formaPagamento){

		this.formaPagamento = formaPagamento;
	}

	public String getIdentificacaoAgenciaContaDigitoCreditada(){

		return identificacaoAgenciaContaDigitoCreditada;
	}

	public void setIdentificacaoAgenciaContaDigitoCreditada(String identificacaoAgenciaContaDigitoCreditada){

		this.identificacaoAgenciaContaDigitoCreditada = identificacaoAgenciaContaDigitoCreditada;
	}

	public String getNsr(){

		return nsr;
	}

	public void setNsr(String nsr){

		this.nsr = nsr;
	}

	public String getNumeroAutenticacaoCaixaOUCodigoTransacao(){

		return numeroAutenticacaoCaixaOUCodigoTransacao;
	}

	public void setNumeroAutenticacaoCaixaOUCodigoTransacao(String numeroAutenticacaoCaixaOUCodigoTransacao){

		this.numeroAutenticacaoCaixaOUCodigoTransacao = numeroAutenticacaoCaixaOUCodigoTransacao;
	}

	public String getValorRecebido(){

		return valorRecebido;
	}

	public void setValorRecebido(String valorRecebido){

		this.valorRecebido = valorRecebido;
	}

	public String getValorTarifa(){

		return valorTarifa;
	}

	public void setValorTarifa(String valorTarifa){

		this.valorTarifa = valorTarifa;
	}

	public String getCodigoAgencia(){

		return codigoAgencia;
	}

	public void setCodigoAgencia(String codigoAgencia){

		this.codigoAgencia = codigoAgencia;
	}

	public String getCodigoCep(){

		return codigoCep;
	}

	public void setCodigoCep(String codigoCep){

		this.codigoCep = codigoCep;
	}

	public String getNomeAgencia(){

		return nomeAgencia;
	}

	public void setNomeAgencia(String nomeAgencia){

		this.nomeAgencia = nomeAgencia;
	}

	public String getNomeCidade(){

		return nomeCidade;
	}

	public void setNomeCidade(String nomeCidade){

		this.nomeCidade = nomeCidade;
	}

	public String getNomeLogradouro(){

		return nomeLogradouro;
	}

	public void setNomeLogradouro(String nomeLogradouro){

		this.nomeLogradouro = nomeLogradouro;
	}

	public String getNumero(){

		return numero;
	}

	public void setNumero(String numero){

		this.numero = numero;
	}

	public String getSiglaUnidadeFederacao(){

		return siglaUnidadeFederacao;
	}

	public void setSiglaUnidadeFederacao(String siglaUnidadeFederacao){

		this.siglaUnidadeFederacao = siglaUnidadeFederacao;
	}

	public String getSituacaoAgencia(){

		return situacaoAgencia;
	}

	public void setSituacaoAgencia(String situacaoAgencia){

		this.situacaoAgencia = situacaoAgencia;
	}

	public String getSufixoCep(){

		return sufixoCep;
	}

	public void setSufixoCep(String sufixoCep){

		this.sufixoCep = sufixoCep;
	}

	public DadosConteudoCodigoBarrasHelper getDadosConteudoCodigoBarrasHelper(){

		return dadosConteudoCodigoBarrasHelper;
	}

	public void setDadosConteudoCodigoBarrasHelper(DadosConteudoCodigoBarrasHelper dadosConteudoCodigoBarrasHelper){

		this.dadosConteudoCodigoBarrasHelper = dadosConteudoCodigoBarrasHelper;
	}

	public void setDataEnvio(String dataEnvio){

		this.dataEnvio = dataEnvio;
	}

	public String getDataEnvio(){

		return dataEnvio;
	}

	public void setDataRetorno(String dataRetorno){

		this.dataRetorno = dataRetorno;
	}

	public String getDataRetorno(){

		return dataRetorno;
	}

	public String getCodigoMovimentoRetorno(){

		return codigoMovimentoRetorno;
	}

	public void setCodigoMovimentoRetorno(String codigoMovimentoRetorno){

		this.codigoMovimentoRetorno = codigoMovimentoRetorno;
	}

	public Collection<String> getOcorrencias(){

		return ocorrencias;
	}

	public void setOcorrencias(Collection<String> ocorrencias){

		this.ocorrencias = ocorrencias;
	}

	public String getAgenciaContaDigito(){

		return agenciaContaDigito;
	}

	public void setAgenciaContaDigito(String agenciaContaDigito){

		this.agenciaContaDigito = agenciaContaDigito;
	}

	public String getCodigoMovimentoRemessa(){

		return codigoMovimentoRemessa;
	}

	public void setCodigoMovimentoRemessa(String codigoMovimentoRemessa){

		this.codigoMovimentoRemessa = codigoMovimentoRemessa;
	}

	public String getDataVencimentoTitulo(){

		return dataVencimentoTitulo;
	}

	public void setDataVencimentoTitulo(String dataVencimentoTitulo){

		this.dataVencimentoTitulo = dataVencimentoTitulo;
	}

	public String getValorNominalTitulo(){

		return valorNominalTitulo;
	}

	public void setValorNominalTitulo(String valorNominalTitulo){

		this.valorNominalTitulo = valorNominalTitulo;
	}

	public String getDataDesconto1(){

		return dataDesconto1;
	}

	public void setDataDesconto1(String dataDesconto1){

		this.dataDesconto1 = dataDesconto1;
	}

	public String getValorPercentualDesconto1(){

		return valorPercentualDesconto1;
	}

	public void setValorPercentualDesconto1(String valorPercentualDesconto1){

		this.valorPercentualDesconto1 = valorPercentualDesconto1;
	}

	public String getNomeSacado(){

		return nomeSacado;
	}

	public void setNomeSacado(String nomeSacado){

		this.nomeSacado = nomeSacado;
	}

	public String getEnderecoSacado(){

		return enderecoSacado;
	}

	public void setEnderecoSacado(String enderecoSacado){

		this.enderecoSacado = enderecoSacado;
	}

	public String getBairro(){

		return bairro;
	}

	public void setBairro(String bairro){

		this.bairro = bairro;
	}

	public String getCEP(){

		return CEP;
	}

	public void setCEP(String cEP){

		CEP = cEP;
	}

	public String getDataDesconto2(){

		return dataDesconto2;
	}

	public void setDataDesconto2(String dataDesconto2){

		this.dataDesconto2 = dataDesconto2;
	}

	public String getValorPercentualDesconto2(){

		return valorPercentualDesconto2;
	}

	public void setValorPercentualDesconto2(String valorPercentualDesconto2){

		this.valorPercentualDesconto2 = valorPercentualDesconto2;
	}

	public String getDataDesconto3(){

		return dataDesconto3;
	}

	public void setDataDesconto3(String dataDesconto3){

		this.dataDesconto3 = dataDesconto3;
	}

	public String getValorPercentualDesconto3(){

		return valorPercentualDesconto3;
	}

	public void setValorPercentualDesconto3(String valorPercentualDesconto3){

		this.valorPercentualDesconto3 = valorPercentualDesconto3;
	}

	public Collection<PagamentoHistorico> getColecaoPagamentoHistorico(){

		return colecaoPagamentoHistorico;
	}

	public void setColecaoPagamentoHistorico(Collection<PagamentoHistorico> colecaoPagamentoHistorico){

		this.colecaoPagamentoHistorico = colecaoPagamentoHistorico;
	}

	public String getPossuiPagamentos(){

		return possuiPagamentos;
	}

	public void setPossuiPagamentos(String possuiPagamentos){

		this.possuiPagamentos = possuiPagamentos;
	}

}
