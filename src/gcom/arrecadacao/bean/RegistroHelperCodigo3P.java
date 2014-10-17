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

import java.io.Serializable;

/**
 * [UC0721] - Distribuir dados do Registro de Movimento do Arrecadador da Ficha de Compensação
 * Autor: Anderson Italo
 * Data: 03/10/2011
 */
public class RegistroHelperCodigo3P
				implements Serializable {

	private static final long serialVersionUID = 1L;

	public RegistroHelperCodigo3P() {

	}

	private String codigoBancoCompensacao;

	private String loteServico;

	private String codigoRegistro;

	private String numeroSequencialRegistroLote;

	private String codigoSegmentoRegistroDetalhe;

	private String usoExclusivoCampo6;

	private String codigoMovimento;

	private String agenciaMantedoraConta;

	private String digitoVerificadorAgenciaMantenedoraConta;

	private String numeroContaCorrente;

	private String digitoVerificadorConta;

	private String digitoVerificadorAgenciaConta;

	private String identificacaoTituloBanco;

	private String codigoCarteira;

	private String formaCadastramentoTituloBanco;

	private String tipoDocumento;

	private String identificacaoEmissaoBloqueto;

	private String identificacaoDistribuicao;

	private String numeroDocumentoCobranca;

	private String dataVencimentoTitulo;

	private String valorNominalTitulo;

	private String agenciaEncarregadaCobranca;

	private String valorNominal;

	private String digitoVerificadorAgenciaEncarregadaCobranca;

	private String especieTitulo;

	private String identicadorTituloAceitoOuNaoAceito;

	private String dataEmissaoTitulo;

	private String codigoJurosMora;

	private String dataJurosMora;

	private String jurosMoraDiaTaxa;

	private String codigoDesconto1;

	private String dataDesconto1;

	private String valorPercentualConcedidoDesconto1;

	private String valorIOFConcedido;

	private String valorAbatimento;

	private String identicadorTituloEmpresa;

	private String codigoProtesto;

	private String numeroDiasProtesto;

	private String codigoBaixaDevolucao;

	private String numeroDiasBaixaDevolucao;

	private String codigoMoeda;

	private String numeroContratoOperacaoCredito;

	private String usoExclusivoCampo42;

	public String getCodigoBancoCompensacao(){

		return codigoBancoCompensacao;
	}

	public void setCodigoBancoCompensacao(String codigoBancoCompensacao){

		this.codigoBancoCompensacao = codigoBancoCompensacao;
	}

	public String getLoteServico(){

		return loteServico;
	}

	public void setLoteServico(String loteServico){

		this.loteServico = loteServico;
	}

	public String getCodigoRegistro(){

		return codigoRegistro;
	}

	public void setCodigoRegistro(String codigoRegistro){

		this.codigoRegistro = codigoRegistro;
	}

	public String getNumeroSequencialRegistroLote(){

		return numeroSequencialRegistroLote;
	}

	public void setNumeroSequencialRegistroLote(String numeroSequencialRegistroLote){

		this.numeroSequencialRegistroLote = numeroSequencialRegistroLote;
	}

	public String getCodigoSegmentoRegistroDetalhe(){

		return codigoSegmentoRegistroDetalhe;
	}

	public void setCodigoSegmentoRegistroDetalhe(String codigoSegmentoRegistroDetalhe){

		this.codigoSegmentoRegistroDetalhe = codigoSegmentoRegistroDetalhe;
	}

	public String getCodigoMovimento(){

		return codigoMovimento;
	}

	public void setCodigoMovimento(String codigoMovimento){

		this.codigoMovimento = codigoMovimento;
	}

	public String getAgenciaMantedoraConta(){

		return agenciaMantedoraConta;
	}

	public void setAgenciaMantedoraConta(String agenciaMantedoraConta){

		this.agenciaMantedoraConta = agenciaMantedoraConta;
	}

	public String getDigitoVerificadorAgenciaMantenedoraConta(){

		return digitoVerificadorAgenciaMantenedoraConta;
	}

	public void setDigitoVerificadorAgenciaMantenedoraConta(String digitoVerificadorAgenciaMantenedoraConta){

		this.digitoVerificadorAgenciaMantenedoraConta = digitoVerificadorAgenciaMantenedoraConta;
	}

	public String getNumeroContaCorrente(){

		return numeroContaCorrente;
	}

	public void setNumeroContaCorrente(String numeroContaCorrente){

		this.numeroContaCorrente = numeroContaCorrente;
	}

	public String getDigitoVerificadorConta(){

		return digitoVerificadorConta;
	}

	public void setDigitoVerificadorConta(String digitoVerificadorConta){

		this.digitoVerificadorConta = digitoVerificadorConta;
	}

	public String getDigitoVerificadorAgenciaConta(){

		return digitoVerificadorAgenciaConta;
	}

	public void setDigitoVerificadorAgenciaConta(String digitoVerificadorAgenciaConta){

		this.digitoVerificadorAgenciaConta = digitoVerificadorAgenciaConta;
	}

	public String getIdentificacaoTituloBanco(){

		return identificacaoTituloBanco;
	}

	public void setIdentificacaoTituloBanco(String identificacaoTituloBanco){

		this.identificacaoTituloBanco = identificacaoTituloBanco;
	}

	public String getCodigoCarteira(){

		return codigoCarteira;
	}

	public void setCodigoCarteira(String codigoCarteira){

		this.codigoCarteira = codigoCarteira;
	}

	public String getFormaCadastramentoTituloBanco(){

		return formaCadastramentoTituloBanco;
	}

	public void setFormaCadastramentoTituloBanco(String formaCadastramentoTituloBanco){

		this.formaCadastramentoTituloBanco = formaCadastramentoTituloBanco;
	}

	public String getTipoDocumento(){

		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento){

		this.tipoDocumento = tipoDocumento;
	}

	public String getIdentificacaoEmissaoBloqueto(){

		return identificacaoEmissaoBloqueto;
	}

	public void setIdentificacaoEmissaoBloqueto(String identificacaoEmissaoBloqueto){

		this.identificacaoEmissaoBloqueto = identificacaoEmissaoBloqueto;
	}

	public String getIdentificacaoDistribuicao(){

		return identificacaoDistribuicao;
	}

	public void setIdentificacaoDistribuicao(String identificacaoDistribuicao){

		this.identificacaoDistribuicao = identificacaoDistribuicao;
	}

	public String getNumeroDocumentoCobranca(){

		return numeroDocumentoCobranca;
	}

	public void setNumeroDocumentoCobranca(String numeroDocumentoCobranca){

		this.numeroDocumentoCobranca = numeroDocumentoCobranca;
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

	public String getAgenciaEncarregadaCobranca(){

		return agenciaEncarregadaCobranca;
	}

	public void setAgenciaEncarregadaCobranca(String agenciaEncarregadaCobranca){

		this.agenciaEncarregadaCobranca = agenciaEncarregadaCobranca;
	}

	public String getValorNominal(){

		return valorNominal;
	}

	public void setValorNominal(String valorNominal){

		this.valorNominal = valorNominal;
	}

	public String getDigitoVerificadorAgenciaEncarregadaCobranca(){

		return digitoVerificadorAgenciaEncarregadaCobranca;
	}

	public void setDigitoVerificadorAgenciaEncarregadaCobranca(String digitoVerificadorAgenciaEncarregadaCobranca){

		this.digitoVerificadorAgenciaEncarregadaCobranca = digitoVerificadorAgenciaEncarregadaCobranca;
	}

	public String getEspecieTitulo(){

		return especieTitulo;
	}

	public void setEspecieTitulo(String especieTitulo){

		this.especieTitulo = especieTitulo;
	}

	public String getIdenticadorTituloAceitoOuNaoAceito(){

		return identicadorTituloAceitoOuNaoAceito;
	}

	public void setIdenticadorTituloAceitoOuNaoAceito(String identicadorTituloAceitoOuNaoAceito){

		this.identicadorTituloAceitoOuNaoAceito = identicadorTituloAceitoOuNaoAceito;
	}

	public String getDataEmissaoTitulo(){

		return dataEmissaoTitulo;
	}

	public void setDataEmissaoTitulo(String dataEmissaoTitulo){

		this.dataEmissaoTitulo = dataEmissaoTitulo;
	}

	public String getCodigoJurosMora(){

		return codigoJurosMora;
	}

	public void setCodigoJurosMora(String codigoJurosMora){

		this.codigoJurosMora = codigoJurosMora;
	}

	public String getDataJurosMora(){

		return dataJurosMora;
	}

	public void setDataJurosMora(String dataJurosMora){

		this.dataJurosMora = dataJurosMora;
	}

	public String getJurosMoraDiaTaxa(){

		return jurosMoraDiaTaxa;
	}

	public void setJurosMoraDiaTaxa(String jurosMoraDiaTaxa){

		this.jurosMoraDiaTaxa = jurosMoraDiaTaxa;
	}

	public String getValorAbatimento(){

		return valorAbatimento;
	}

	public void setValorAbatimento(String valorAbatimento){

		this.valorAbatimento = valorAbatimento;
	}

	public String getIdenticadorTituloEmpresa(){

		return identicadorTituloEmpresa;
	}

	public void setIdenticadorTituloEmpresa(String identicadorTituloEmpresa){

		this.identicadorTituloEmpresa = identicadorTituloEmpresa;
	}

	public String getCodigoProtesto(){

		return codigoProtesto;
	}

	public void setCodigoProtesto(String codigoProtesto){

		this.codigoProtesto = codigoProtesto;
	}

	public String getNumeroDiasProtesto(){

		return numeroDiasProtesto;
	}

	public void setNumeroDiasProtesto(String numeroDiasProtesto){

		this.numeroDiasProtesto = numeroDiasProtesto;
	}

	public String getCodigoBaixaDevolucao(){

		return codigoBaixaDevolucao;
	}

	public void setCodigoBaixaDevolucao(String codigoBaixaDevolucao){

		this.codigoBaixaDevolucao = codigoBaixaDevolucao;
	}

	public String getNumeroDiasBaixaDevolucao(){

		return numeroDiasBaixaDevolucao;
	}

	public void setNumeroDiasBaixaDevolucao(String numeroDiasBaixaDevolucao){

		this.numeroDiasBaixaDevolucao = numeroDiasBaixaDevolucao;
	}

	public String getCodigoMoeda(){

		return codigoMoeda;
	}

	public void setCodigoMoeda(String codigoMoeda){

		this.codigoMoeda = codigoMoeda;
	}

	public String getNumeroContratoOperacaoCredito(){

		return numeroContratoOperacaoCredito;
	}

	public void setNumeroContratoOperacaoCredito(String numeroContratoOperacaoCredito){

		this.numeroContratoOperacaoCredito = numeroContratoOperacaoCredito;
	}

	public String getUsoExclusivoCampo6(){

		return usoExclusivoCampo6;
	}

	public void setUsoExclusivoCampo6(String usoExclusivoCampo6){

		this.usoExclusivoCampo6 = usoExclusivoCampo6;
	}

	public String getUsoExclusivoCampo42(){

		return usoExclusivoCampo42;
	}

	public void setUsoExclusivoCampo42(String usoExclusivoCampo42){

		this.usoExclusivoCampo42 = usoExclusivoCampo42;
	}

	public String getCodigoDesconto1(){

		return codigoDesconto1;
	}

	public void setCodigoDesconto1(String codigoDesconto1){

		this.codigoDesconto1 = codigoDesconto1;
	}

	public String getDataDesconto1(){

		return dataDesconto1;
	}

	public void setDataDesconto1(String dataDesconto1){

		this.dataDesconto1 = dataDesconto1;
	}

	public String getValorPercentualConcedidoDesconto1(){

		return valorPercentualConcedidoDesconto1;
	}

	public void setValorPercentualConcedidoDesconto1(String valorPercentualConcedidoDesconto1){

		this.valorPercentualConcedidoDesconto1 = valorPercentualConcedidoDesconto1;
	}

	public String getValorIOFConcedido(){

		return valorIOFConcedido;
	}

	public void setValorIOFConcedido(String valorIOFConcedido){

		this.valorIOFConcedido = valorIOFConcedido;
	}

}
