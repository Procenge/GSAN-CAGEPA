/*
 * Copyright (C) 2007-2007 the GSAN ? Sistema Integrado de Gestão de Serviços de Saneamento
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
 * Foundation, Inc., 59 Temple Place ? Suite 330, Boston, MA 02111-1307, USA
 */

/*
 * GSAN ? Sistema Integrado de Gestão de Serviços de Saneamento
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

package gcom.relatorio.cobranca.parcelamento;

import gcom.relatorio.RelatorioBean;

/**
 * Bean do [UC0214] Efetuar Parcelamento de Débitos
 * 
 * @author Cinthya
 * @date 19/10/2011
 */
public class RelatorioParcelamentoResolucaoDiretoriaLayoutBean
				implements RelatorioBean {

	private String matriculaImovel;

	private String inscricaoEstadual;

	private String nomeCliente;

	private String endereco;

	private String enderecoAbreviado;

	private String cep;

	private String telefone;

	private String cpfCnpj;

	private String taxaJurosMora;

	private String taxaJurosMoraExtenso;

	private String taxaMulta;

	private String taxaMultaExtenso;

	private String totalDebitos;

	private String totalDebitosExtenso;

	private String totalDescontos;

	private String valorEntrada;

	private String valorEntradaExtenso;

	private String numeroParcelas;

	private String numeroParcelasExtenso;

	private String valorParcela;

	private String valorParcelaExtenso;

	private String descricaoLocalidade;

	private String colecaoAnoMesReferencia;

	private String colecaoAnoMesReferenciaSobra;

	private String detalhamentoGuiasPrestacoes;

	private String detalhamentoGuiasPrestacoesSobra;

	private String imovelDiaVencimento;

	private String tipoCliente;

	private String rg;

	private String valorMultas;

	private String valorJurosMora;

	private String valorJurosParcelamento;

	private String funcionario;

	private String intervaloPeriodoDebito;

	private String inicioPeriodoFornecimento;

	private String fimPeriodoFornecimento;

	private String numeroContas;

	private String nomeProprietarioImovel;

	private String cpfProprietarioImovel;

	private String anoMesReferenciaDebitoInicial;

	private String anoMesReferenciaDebitoFinal;

	private String nomeEmpresa;

	private String nomeEmpresaAbreviado;

	private String enderecoEmpresa;

	private String cnpjEmpresa;

	private String nomeRepresentante;

	private String dataNomeacaoRepresentante;

	private String matriculaRepresentante;

	private String nomeSignatario;

	private String dataNomeacaoSignatario;

	private String matriculaSignatario;

	private String cepEmpresa;

	private String emailEmpresa;

	private String siteEmpresa;

	private String cargoRepresentante;

	private String cargoSignatario;

	private String nomeUsuarioLogado;

	private String cpfUsuarioLogado;

	private String inicioCobranca;

	private String terminioCobranca;

	private String descOrgaoExpRgCliente;

	private String dataParcelamento;

	private String indicadorPessoaFisicaJuridica;

	private String nomeTestemunha1;

	private String cpfTestemunha1;

	private String nomeTestemunha2;

	private String cpfTestemunha2;

	private String inscricaoEstadualEmpresa;

	private String profissaoCliente;

	private String juntaComercialEmpresa;

	private String cidadeImovelCadastrado;

	private String rgProprietarioImovel;

	private String indicadorPessoaFisicaJuridicaProprietarioImovel;

	private String totalNegociado;

	private String totalNegociadoExtenso;

	/**
	 * Construtor de RelatorioGerarRelacaoDebitosBean
	 */
	public RelatorioParcelamentoResolucaoDiretoriaLayoutBean(String matriculaImovel, String nomeCliente, String endereco, String cpfCnpj,
																String taxaJurosMora, String taxaJurosMoraExtenso, String taxaMulta,
																String taxaMultaExtenso, String totalDebitos, String totalDebitosExtenso,
																String totalDescontos, String valorEntrada, String numeroParcelas,
																String numeroParcelasExtenso, String valorParcela,
																String descricaoLocalidade, String colecaoAnoMesReferencia,
																String colecaoAnoMesReferenciaSobra, String detalhamentoGuiasPrestacoes,
																String detalhamentoGuiasPrestacoesSobra, String imovelDiaVencimento,
																String tipoCliente, String rg, String valorMultas, String valorJurosMora,
																String valorJurosParcelamento, String inscricaoEstadual,
																String funcionario, String intervaloPeriodoDebito, String telefone,
																String valorParcelaExtenso, String valorEntradaExtenso, String cep,
																String inicioPeriodoFornecimento, String fimPeriodoFornecimento,
																String numeroContas, String enderecoAbreviado, String nomeEmpresa,
																String nomeEmpresaAbreviado, String enderecoEmpresa, String cnpjEmpresa,
																String nomeRepresentante, String dataNomeacaoRepresentante,
																String matriculaRepresentante, String nomeSignatario,
																String dataNomeacaoSignatario, String matriculaSignatario,
																String cepEmpresa, String emailEmpresa, String siteEmpresa,
																String cargoRepresentante, String cargoSignatario,
																String nomeUsuarioLogado, String cpfUsuarioLogado, String inicioCobranca,
																String terminioCobranca, String descOrgaoExpRgCliente,
																String dataParcelamento, String indicadorPessoaFisicaJuridica,
																String nomeTestemunha1, String cpfTestemunha1, String nomeTestemunha2,
																String cpfTestemunha2, String profissaoCliente,
																String juntaComercialEmpresa, String inscricaoEstadualEmpresa,
																String cidadeImovelCadastrado, String totalNegociado,
																String totalNegociadoExtenso) {

		super();
		this.matriculaImovel = matriculaImovel;
		this.nomeCliente = nomeCliente;
		this.endereco = endereco;
		this.cpfCnpj = cpfCnpj;
		this.taxaJurosMora = taxaJurosMora;
		this.taxaJurosMoraExtenso = taxaJurosMoraExtenso;
		this.taxaMulta = taxaMulta;
		this.taxaMultaExtenso = taxaMultaExtenso;
		this.totalDebitos = totalDebitos;
		this.totalDebitosExtenso = totalDebitosExtenso;
		this.totalDescontos = totalDescontos;
		this.valorEntrada = valorEntrada;
		this.numeroParcelas = numeroParcelas;
		this.numeroParcelasExtenso = numeroParcelasExtenso;
		this.valorParcela = valorParcela;
		this.descricaoLocalidade = descricaoLocalidade;
		this.colecaoAnoMesReferencia = colecaoAnoMesReferencia;
		this.colecaoAnoMesReferenciaSobra = colecaoAnoMesReferenciaSobra;
		this.detalhamentoGuiasPrestacoes = detalhamentoGuiasPrestacoes;
		this.detalhamentoGuiasPrestacoesSobra = detalhamentoGuiasPrestacoesSobra;
		this.imovelDiaVencimento = imovelDiaVencimento;
		this.tipoCliente = tipoCliente;
		this.rg = rg;
		this.valorMultas = valorMultas;
		this.valorJurosMora = valorJurosMora;
		this.valorJurosParcelamento = valorJurosParcelamento;
		this.inscricaoEstadual = inscricaoEstadual;
		this.funcionario = funcionario;
		this.intervaloPeriodoDebito = intervaloPeriodoDebito;
		this.telefone = telefone;
		this.valorParcelaExtenso = valorParcelaExtenso;
		this.valorEntradaExtenso = valorEntradaExtenso;
		this.cep = cep;
		this.inicioPeriodoFornecimento = inicioPeriodoFornecimento;
		this.fimPeriodoFornecimento = fimPeriodoFornecimento;
		this.numeroContas = numeroContas;
		this.enderecoAbreviado = enderecoAbreviado;
		this.nomeEmpresa = nomeEmpresa;
		this.nomeEmpresaAbreviado = nomeEmpresaAbreviado;
		this.enderecoEmpresa = enderecoEmpresa;
		this.cnpjEmpresa = cnpjEmpresa;
		this.nomeRepresentante = nomeRepresentante;
		this.dataNomeacaoRepresentante = dataNomeacaoRepresentante;
		this.matriculaRepresentante = matriculaRepresentante;
		this.nomeSignatario = nomeSignatario;
		this.dataNomeacaoSignatario = dataNomeacaoSignatario;
		this.matriculaSignatario = matriculaSignatario;
		this.cepEmpresa = cepEmpresa;
		this.emailEmpresa = emailEmpresa;
		this.siteEmpresa = siteEmpresa;
		this.cargoRepresentante = cargoRepresentante;
		this.cargoSignatario = cargoSignatario;
		this.nomeUsuarioLogado = nomeUsuarioLogado;
		this.cpfUsuarioLogado = cpfUsuarioLogado;
		this.inicioCobranca = inicioCobranca;
		this.terminioCobranca = terminioCobranca;
		this.descOrgaoExpRgCliente = descOrgaoExpRgCliente;
		this.dataParcelamento = dataParcelamento;
		this.indicadorPessoaFisicaJuridica = indicadorPessoaFisicaJuridica;
		this.nomeTestemunha1 = nomeTestemunha1;
		this.cpfTestemunha1 = cpfTestemunha1;
		this.nomeTestemunha2 = nomeTestemunha2;
		this.cpfTestemunha2 = cpfTestemunha2;
		this.inscricaoEstadualEmpresa = inscricaoEstadualEmpresa;
		this.profissaoCliente = profissaoCliente;
		this.juntaComercialEmpresa = juntaComercialEmpresa;
		this.cidadeImovelCadastrado = cidadeImovelCadastrado;
		this.totalNegociado = totalNegociado;
		this.totalNegociadoExtenso = totalNegociadoExtenso;

	}


	public String getCpfCnpj(){

		return cpfCnpj;
	}

	public void setCpfCnpj(String cpfCnpj){

		this.cpfCnpj = cpfCnpj;
	}

	public String getEndereco(){

		return endereco;
	}

	public void setEndereco(String endereco){

		this.endereco = endereco;
	}

	public String getTaxaJurosMora(){

		return taxaJurosMora;
	}

	public void setTaxaJurosMora(String taxaJurosMora){

		this.taxaJurosMora = taxaJurosMora;
	}

	public String getMatriculaImovel(){

		return matriculaImovel;
	}

	public void setMatriculaImovel(String matriculaImovel){

		this.matriculaImovel = matriculaImovel;
	}

	public String getTaxaMulta(){

		return taxaMulta;
	}

	public void setTaxaMulta(String multa){

		this.taxaMulta = multa;
	}

	public String getTaxaMultaExtenso(){

		return taxaMultaExtenso;
	}

	public void setTaxaMultaExtenso(String multaExtenso){

		this.taxaMultaExtenso = multaExtenso;
	}

	public String getNomeCliente(){

		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente){

		this.nomeCliente = nomeCliente;
	}

	public String getNumeroParcelas(){

		return numeroParcelas;
	}

	public void setNumeroParcelas(String numeroParcelas){

		this.numeroParcelas = numeroParcelas;
	}

	public String getTotalDebitos(){

		return totalDebitos;
	}

	public void setTotalDebitos(String totalDebitos){

		this.totalDebitos = totalDebitos;
	}

	public String getTotalDescontos(){

		return totalDescontos;
	}

	public void setTotalDescontos(String totalDescontos){

		this.totalDescontos = totalDescontos;
	}

	public String getValorEntrada(){

		return valorEntrada;
	}

	public void setValorEntrada(String valorEntrada){

		this.valorEntrada = valorEntrada;
	}

	public String getValorParcela(){

		return valorParcela;
	}

	public void setValorParcela(String valorParcela){

		this.valorParcela = valorParcela;
	}

	public String getTotalDebitosExtenso(){

		return totalDebitosExtenso;
	}

	public void setTotalDebitosExtenso(String totalDebitosExtenso){

		this.totalDebitosExtenso = totalDebitosExtenso;
	}

	public String getTaxaJurosMoraExtenso(){

		return taxaJurosMoraExtenso;
	}

	public void setTaxaJurosMoraExtenso(String taxaJurosMoraExtenso){

		this.taxaJurosMoraExtenso = taxaJurosMoraExtenso;
	}

	public String getDescricaoLocalidade(){

		return descricaoLocalidade;
	}

	public void setDescricaoLocalidade(String descricaoLocalidade){

		this.descricaoLocalidade = descricaoLocalidade;
	}

	public String getColecaoAnoMesReferencia(){

		return colecaoAnoMesReferencia;
	}

	public void setColecaoAnoMesReferencia(String colecaoAnoMesReferencia){

		this.colecaoAnoMesReferencia = colecaoAnoMesReferencia;
	}

	public String getColecaoAnoMesReferenciaSobra(){

		return colecaoAnoMesReferenciaSobra;
	}

	public void setColecaoAnoMesReferenciaSobra(String colecaoAnoMesReferenciaSobra){

		this.colecaoAnoMesReferenciaSobra = colecaoAnoMesReferenciaSobra;
	}

	public String getImovelDiaVencimento(){

		return imovelDiaVencimento;
	}

	public void setImovelDiaVencimento(String imovelDiaVencimento){

		this.imovelDiaVencimento = imovelDiaVencimento;
	}

	public String getTipoCliente(){

		return tipoCliente;
	}

	public void setTipoCliente(String tipoCliente){

		this.tipoCliente = tipoCliente;
	}

	public String getDetalhamentoGuiasPrestacoes(){

		return detalhamentoGuiasPrestacoes;
	}

	public void setDetalhamentoGuiasPrestacoes(String detalhamentoGuiasPrestacoes){

		this.detalhamentoGuiasPrestacoes = detalhamentoGuiasPrestacoes;
	}

	public String getDetalhamentoGuiasPrestacoesSobra(){

		return detalhamentoGuiasPrestacoesSobra;
	}

	public void setDetalhamentoGuiasPrestacoesSobra(String detalhamentoGuiasPrestacoesSobra){

		this.detalhamentoGuiasPrestacoesSobra = detalhamentoGuiasPrestacoesSobra;
	}

	public void setRg(String rg){

		this.rg = rg;
	}

	public String getRg(){

		return rg;
	}

	public String getValorMultas(){

		return valorMultas;
	}

	public void setValorMultas(String valorMultas){

		this.valorMultas = valorMultas;
	}

	public String getValorJurosMora(){

		return valorJurosMora;
	}

	public void setValorJurosMora(String valorJurosMora){

		this.valorJurosMora = valorJurosMora;
	}

	public String getNumeroParcelasExtenso(){

		return numeroParcelasExtenso;
	}

	public void setNumeroParcelasExtenso(String numeroParcelasExtenso){

		this.numeroParcelasExtenso = numeroParcelasExtenso;
	}

	public String getValorJurosParcelamento(){

		return valorJurosParcelamento;
	}

	public void setValorJurosParcelamento(String valorJurosParcelamento){

		this.valorJurosParcelamento = valorJurosParcelamento;
	}

	public String getInscricaoEstadual(){

		return inscricaoEstadual;
	}

	public void setInscricaoEstadual(String inscricaoEstadual){

		this.inscricaoEstadual = inscricaoEstadual;
	}

	public String getFuncionario(){

		return funcionario;
	}

	public void setFuncionario(String funcionario){

		this.funcionario = funcionario;
	}

	public String getIntervaloPeriodoDebito(){

		return intervaloPeriodoDebito;
	}

	public void setIntervaloPeriodoDebito(String intervaloPeriodoDebito){

		this.intervaloPeriodoDebito = intervaloPeriodoDebito;
	}

	public String getTelefone(){

		return telefone;
	}

	public void setTelefone(String telefone){

		this.telefone = telefone;
	}

	public String getValorParcelaExtenso(){

		return valorParcelaExtenso;
	}

	public void setValorParcelaExtenso(String valorParcelaExtenso){

		this.valorParcelaExtenso = valorParcelaExtenso;
	}

	public String getValorEntradaExtenso(){

		return valorEntradaExtenso;
	}

	public void setValorEntradaExtenso(String valorEntradaExtenso){

		this.valorEntradaExtenso = valorEntradaExtenso;
	}

	public String getCep(){

		return cep;
	}

	public void setCep(String cep){

		this.cep = cep;
	}

	public String getInicioPeriodoFornecimento(){

		return inicioPeriodoFornecimento;
	}

	public void setInicioPeriodoFornecimento(String inicioPeriodoFornecimento){

		this.inicioPeriodoFornecimento = inicioPeriodoFornecimento;
	}

	public String getFimPeriodoFornecimento(){

		return fimPeriodoFornecimento;
	}

	public void setFimPeriodoFornecimento(String fimPeriodoFornecimento){

		this.fimPeriodoFornecimento = fimPeriodoFornecimento;
	}

	public String getNumeroContas(){

		return numeroContas;
	}

	public void setNumeroContas(String numeroContas){

		this.numeroContas = numeroContas;
	}

	public String getEnderecoAbreviado(){

		return enderecoAbreviado;
	}

	public void setEnderecoAbreviado(String enderecoAbreviado){

		this.enderecoAbreviado = enderecoAbreviado;
	}

	public String getNomeProprietarioImovel(){

		return nomeProprietarioImovel;
	}

	public void setNomeProprietarioImovel(String nomeProprietarioImovel){

		this.nomeProprietarioImovel = nomeProprietarioImovel;
	}

	public String getCpfProprietarioImovel(){

		return cpfProprietarioImovel;
	}

	public void setCpfProprietarioImovel(String cpfProprietarioImovel){

		this.cpfProprietarioImovel = cpfProprietarioImovel;
	}

	public String getAnoMesReferenciaDebitoInicial(){

		return anoMesReferenciaDebitoInicial;
	}

	public void setAnoMesReferenciaDebitoInicial(String anoMesReferenciaDebitoInicial){

		this.anoMesReferenciaDebitoInicial = anoMesReferenciaDebitoInicial;
	}

	public String getAnoMesReferenciaDebitoFinal(){

		return anoMesReferenciaDebitoFinal;
	}

	public void setAnoMesReferenciaDebitoFinal(String anoMesReferenciaDebitoFinal){

		this.anoMesReferenciaDebitoFinal = anoMesReferenciaDebitoFinal;
	}

	public String getNomeEmpresa(){

		return nomeEmpresa;
	}

	public void setNomeEmpresa(String nomeEmpresa){

		this.nomeEmpresa = nomeEmpresa;
	}

	public String getNomeEmpresaAbreviado(){

		return nomeEmpresaAbreviado;
	}

	public void setNomeEmpresaAbreviado(String nomeEmpresaAbreviado){

		this.nomeEmpresaAbreviado = nomeEmpresaAbreviado;
	}

	public String getEnderecoEmpresa(){

		return enderecoEmpresa;
	}

	public void setEnderecoEmpresa(String enderecoEmpresa){

		this.enderecoEmpresa = enderecoEmpresa;
	}

	public String getCnpjEmpresa(){

		return cnpjEmpresa;
	}

	public void setCnpjEmpresa(String cnpjEmpresa){

		this.cnpjEmpresa = cnpjEmpresa;
	}

	public String getNomeRepresentante(){

		return nomeRepresentante;
	}

	public void setNomeRepresentante(String nomeRepresentante){

		this.nomeRepresentante = nomeRepresentante;
	}

	public String getDataNomeacaoRepresentante(){

		return dataNomeacaoRepresentante;
	}

	public void setDataNomeacaoRepresentante(String dataNomeacaoRepresentante){

		this.dataNomeacaoRepresentante = dataNomeacaoRepresentante;
	}

	public String getMatriculaRepresentante(){

		return matriculaRepresentante;
	}

	public void setMatriculaRepresentante(String matriculaRepresentante){

		this.matriculaRepresentante = matriculaRepresentante;
	}

	public String getCargoRepresentante(){

		return cargoRepresentante;
	}

	public void setCargoRepresentante(String cargoRepresentante){

		this.cargoRepresentante = cargoRepresentante;
	}

	public String getCargoSignatario(){

		return cargoSignatario;
	}

	public void setCargoSignatario(String cargoSignatario){

		this.cargoSignatario = cargoSignatario;
	}

	public String getNomeSignatario(){

		return nomeSignatario;
	}

	public void setNomeSignatario(String nomeSignatario){

		this.nomeSignatario = nomeSignatario;
	}

	public String getDataNomeacaoSignatario(){

		return dataNomeacaoSignatario;
	}

	public void setDataNomeacaoSignatario(String dataNomeacaoSignatario){

		this.dataNomeacaoSignatario = dataNomeacaoSignatario;
	}

	public String getMatriculaSignatario(){

		return matriculaSignatario;
	}

	public void setMatriculaSignatario(String matriculaSignatario){

		this.matriculaSignatario = matriculaSignatario;
	}

	public String getCepEmpresa(){

		return cepEmpresa;
	}

	public void setCepEmpresa(String cepEmpresa){

		this.cepEmpresa = cepEmpresa;
	}

	public String getEmailEmpresa(){

		return emailEmpresa;
	}

	public void setEmailEmpresa(String emailEmpresa){

		this.emailEmpresa = emailEmpresa;
	}

	public String getSiteEmpresa(){

		return siteEmpresa;
	}

	public void setSiteEmpresa(String siteEmpresa){

		this.siteEmpresa = siteEmpresa;
	}

	public String getNomeUsuarioLogado(){

		return nomeUsuarioLogado;
	}

	public void setNomeUsuarioLogado(String nomeUsuarioLogado){

		this.nomeUsuarioLogado = nomeUsuarioLogado;
	}

	public String getCpfUsuarioLogado(){

		return cpfUsuarioLogado;
	}

	public void setCpfUsuarioLogado(String cpfUsuarioLogado){

		this.cpfUsuarioLogado = cpfUsuarioLogado;
	}

	public String getInicioCobranca(){

		return inicioCobranca;
	}

	public void setInicioCobranca(String inicioCobranca){

		this.inicioCobranca = inicioCobranca;
	}

	public String getTerminioCobranca(){

		return terminioCobranca;
	}

	public void setTerminioCobranca(String terminioCobranca){

		this.terminioCobranca = terminioCobranca;
	}

	public String getDescOrgaoExpRgCliente(){

		return descOrgaoExpRgCliente;
	}

	public void setDescOrgaoExpRgCliente(String descOrgaoExpRgCliente){

		this.descOrgaoExpRgCliente = descOrgaoExpRgCliente;
	}

	public String getDataParcelamento(){

		return dataParcelamento;
	}

	public void setDataParcelamento(String dataParcelamento){

		this.dataParcelamento = dataParcelamento;
	}

	public String getIndicadorPessoaFisicaJuridica(){

		return indicadorPessoaFisicaJuridica;
	}

	public void setIndicadorPessoaFisicaJuridica(String indicadorPessoaFisicaJuridica){

		this.indicadorPessoaFisicaJuridica = indicadorPessoaFisicaJuridica;
	}

	public String getNomeTestemunha1(){

		return nomeTestemunha1;
	}

	public void setNomeTestemunha1(String nomeTestemunha1){

		this.nomeTestemunha1 = nomeTestemunha1;
	}

	public String getCpfTestemunha1(){

		return cpfTestemunha1;
	}

	public void setCpfTestemunha1(String cpfTestemunha1){

		this.cpfTestemunha1 = cpfTestemunha1;
	}

	public String getNomeTestemunha2(){

		return nomeTestemunha2;
	}

	public void setNomeTestemunha2(String nomeTestemunha2){

		this.nomeTestemunha2 = nomeTestemunha2;
	}

	public String getCpfTestemunha2(){

		return cpfTestemunha2;
	}

	public void setCpfTestemunha2(String cpfTestemunha2){

		this.cpfTestemunha2 = cpfTestemunha2;
	}

	public String getProfissaoCliente(){

		return profissaoCliente;
	}

	public void setProfissaoCliente(String profissaoCliente){

		this.profissaoCliente = profissaoCliente;
	}

	public String getJuntaComercialEmpresa(){

		return juntaComercialEmpresa;
	}

	public void setJuntaComercialEmpresa(String juntaComercialEmpresa){

		this.juntaComercialEmpresa = juntaComercialEmpresa;
	}

	public String getInscricaoEstadualEmpresa(){

		return inscricaoEstadualEmpresa;
	}

	public void setInscricaoEstadualEmpresa(String inscricaoEstadualEmpresa){

		this.inscricaoEstadualEmpresa = inscricaoEstadualEmpresa;
	}

	public String getCidadeImovelCadastrado(){

		return cidadeImovelCadastrado;
	}

	public void setCidadeImovelCadastrado(String cidadeImovelCadastrado){

		this.cidadeImovelCadastrado = cidadeImovelCadastrado;
	}

	public String getRgProprietarioImovel(){

		return rgProprietarioImovel;
	}

	public void setRgProprietarioImovel(String rgProprietarioImovel){

		this.rgProprietarioImovel = rgProprietarioImovel;
	}

	public String getIndicadorPessoaFisicaJuridicaProprietarioImovel(){

		return indicadorPessoaFisicaJuridicaProprietarioImovel;
	}

	public void setIndicadorPessoaFisicaJuridicaProprietarioImovel(String indicadorPessoaFisicaJuridicaProprietarioImovel){

		this.indicadorPessoaFisicaJuridicaProprietarioImovel = indicadorPessoaFisicaJuridicaProprietarioImovel;
	}

	public String getTotalNegociado(){

		return totalNegociado;
	}

	public void setTotalNegociado(String totalNegociado){

		this.totalNegociado = totalNegociado;
	}

	public String getTotalNegociadoExtenso(){

		return totalNegociadoExtenso;
	}

	public void setTotalNegociadoExtenso(String totalNegociadoExtenso){

		this.totalNegociadoExtenso = totalNegociadoExtenso;
	}

}
