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

package gcom.cobranca.bean;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Rafael Corrêa
 * @since 25/09/2006
 * @author Saulo Lima
 * @date 24/07/2009
 *       Novos campos para exibir numeração das Guias
 */
public class ParcelamentoRelatorioHelper {

	/**
	 * Id do Imóvel
	 */
	private Integer idImovel;

	/**
	 * Nome do Cliente
	 */
	private String nomeCliente;

	/**
	 * Endereço
	 */
	private String endereco;

	/**
	 * Endereço abreviado
	 */
	private String enderecoAbreviado;

	/**
	 * CEP
	 */
	private String cep;

	/**
	 * CPF/CNPJ
	 */
	private String cpfCnpj;

	/**
	 * Telefone
	 */
	private String telefone;

	/**
	 * Telefone
	 */
	private String fax;

	/**
	 * Data do Parcelamento
	 */
	private Date dataParcelamento;

	/**
	 * Valor das Faturas em Aberto
	 */
	private BigDecimal valorFaturasEmAberto;

	/**
	 * Valor dos Serviços a Cobrar
	 */
	private BigDecimal valorServicosACobrar;

	/**
	 * Valor de Atualizacao Monetaria
	 */
	private BigDecimal valorAtualizacaoMonetaria;

	/**
	 * Valor de Juros/Mora
	 */
	private BigDecimal valorJurosMora;

	/**
	 * Valor de Juros/Mora
	 */
	private BigDecimal valorJurosMoraExtenso;

	/**
	 * Valor das Multas
	 */
	private BigDecimal valorMultas;

	/**
	 * Valor das Guias de Pagamento
	 */
	private BigDecimal valorGuiaPagamento;

	/**
	 * Valor do Parcelamento a Cobrar
	 */
	private BigDecimal valorParcelamentoACobrar;

	/**
	 * Valor Total dos Débitos
	 */
	private BigDecimal valorTotalDebitos;

	/**
	 * Valor Total dos Débitos por extenso
	 */
	private BigDecimal valorTotalDebitosExtenso;

	/**
	 * Valor dos Descontos de Acréscimos
	 */
	private BigDecimal valorDescontoAcrescimo;

	/**
	 * Valor dos Descontos em Antiguidade
	 */
	private BigDecimal valorDescontoAntiguidade;

	/**
	 * Valor dos Descontos de Inatividade
	 */
	private BigDecimal valorDescontoInatividade;

	/**
	 * Valor dos Créditos a Realizar
	 */
	private BigDecimal valorCreditosRealizar;

	/**
	 * Valor Total dos Descontos
	 */
	private BigDecimal valorTotalDescontos;

	/**
	 * Valor a Ser Negociado
	 */
	private BigDecimal valorASerNegociado;

	/**
	 * Valor da Entrada
	 */
	private BigDecimal valorEntrada;

	/**
	 * Valor da Entrada
	 */
	private String valorEntradaExtenso;

	/**
	 * Número de Parcelas
	 */
	private Integer numeroParcelas;

	/**
	 * Número de Parcelas
	 */
	private String numeroParcelasExtenso;

	/**
	 * Valor da Parcela
	 */
	private BigDecimal valorParcela;

	/**
	 * Valor a Ser Parcelado
	 */
	private BigDecimal valorASerParcelado;

	/**
	 * Solicitação de Restabelecimento
	 */
	private String solicitacaoRestabelecimento;

	/**
	 * Nome do Munícipio
	 */
	private String nomeMunicipio;

	/**
	 * Id do Funcionário
	 */
	private Integer idFuncionario;

	/**
	 * Id do Funcionário
	 */
	private String descricaoLocalidade;

	private Short imovelDiaVencimento;

	private Integer idCliente;

	private String colecaoAnoMesReferencia;

	private String colecaoAnoMesReferenciaSobra;

	private Integer numeroContas;

	private String inicioPeriodoFornecimento;

	private String fimPeriodoFornecimento;

	private String detalhamentoGuiasPrestacoes;

	private String detalhamentoGuiasPrestacoesSobra;

	/**
	 * Unidade do Usuário
	 */
	private BigDecimal unidadeUsuario;

	/**
	 * Nome do Cliente do Parcelamento
	 */
	private String nomeClienteParcelamento;

	/**
	 * CPF do Cliente do Parcelamento
	 */
	private String cpfClienteParcelamento;

	/**
	 * RG do Cliente
	 */
	private String rgCliente;

	/**
	 * orgao expedidor RG do Cliente
	 */
	private String descOrgaoExpRgCliente;

	/**
	 * unidade federacao RG do Cliente
	 */
	private String siglaUnidadeFederacaoRgCliente;

	private String mesAnoInicioParcelamento;

	private String mesAnoFinalParcelamento;

	/**
	 * RG do Cliente do Parcelamento
	 */
	private String rgClienteParcelamento;

	private String taxaJuros;

	/**
	 * Valor dos Descontos de Sanções Regulamentares
	 */
	private BigDecimal valorDescontoSancoesRegulamentares;

	private BigDecimal valorDescontoTarifaSocial;

	/**
	 * Data da Entrada
	 */
	private Date dataEntradaParcelamento;

	private BigDecimal valorJurosParcelamento;

	private Integer idResolucaoDiretoria;

	private String nomeProprietarioImovel;

	private String cpfProprietarioImovel;

	private Integer anoMesReferenciaDebitoInicial;

	private Integer anoMesReferenciaDebitoFinal;

	private Short indicadorPessoaFisicaJuridica;

	private BigDecimal totalNegociado;

	private String colecaoDatasParcelamento;

	private String colecaoDatasParcelamentoSobra;

	private String colecaoServicosDebitoACobrar;

	private String colecaoServicosParcelamento;

	private String colecaoServicosDebitoACobrarSobra;

	private String colecaoServicosParcelamentoSobra;

	private String colecaoProcessosExecucaoFiscal;

	private BigDecimal valorAtualizacaoMonetariaSucumbenciaAnterior;

	private BigDecimal valorJurosMoraSucumbenciaAnterior;

	private BigDecimal valorSucumbenciaAnterior;

	private BigDecimal valorSucumbenciaAtual;

	private Short numeroParcelasSucumbencia;

	private Integer idCobrancaForma;

	private Date dataVencimentoPrimeiraGuiaPagamento;

	private Date dataVencimentoPrimeiraGuiaPagamentoSucumbencia;

	private Date dataVencimentoSegundaGuiaPagamento;

	public Date getDataVencimentoSegundaGuiaPagamento(){

		return dataVencimentoSegundaGuiaPagamento;
	}

	public void setDataVencimentoSegundaGuiaPagamento(Date dataVencimentoSegundaGuiaPagamento){

		this.dataVencimentoSegundaGuiaPagamento = dataVencimentoSegundaGuiaPagamento;
	}

	public Date getDataVencimentoPrimeiraGuiaPagamentoSucumbencia(){

		return dataVencimentoPrimeiraGuiaPagamentoSucumbencia;
	}

	public void setDataVencimentoPrimeiraGuiaPagamentoSucumbencia(Date dataVencimentoPrimeiraGuiaPagamentoSucumbencia){

		this.dataVencimentoPrimeiraGuiaPagamentoSucumbencia = dataVencimentoPrimeiraGuiaPagamentoSucumbencia;
	}

	public Date getDataVencimentoPrimeiraGuiaPagamento(){

		return dataVencimentoPrimeiraGuiaPagamento;
	}

	public void setDataVencimentoPrimeiraGuiaPagamento(Date dataVencimentoPrimeiraGuiaPagamento){

		this.dataVencimentoPrimeiraGuiaPagamento = dataVencimentoPrimeiraGuiaPagamento;
	}

	public Integer getIdCobrancaForma(){

		return idCobrancaForma;
	}

	public void setIdCobrancaForma(Integer idCobrancaForma){

		this.idCobrancaForma = idCobrancaForma;
	}

	public Short getNumeroParcelasSucumbencia(){

		return numeroParcelasSucumbencia;
	}

	public void setNumeroParcelasSucumbencia(Short numeroParcelasSucumbencia){

		this.numeroParcelasSucumbencia = numeroParcelasSucumbencia;
	}

	public BigDecimal getValorAtualizacaoMonetariaSucumbenciaAnterior(){

		return valorAtualizacaoMonetariaSucumbenciaAnterior;
	}

	public void setValorAtualizacaoMonetariaSucumbenciaAnterior(BigDecimal valorAtualizacaoMonetariaSucumbenciaAnterior){

		this.valorAtualizacaoMonetariaSucumbenciaAnterior = valorAtualizacaoMonetariaSucumbenciaAnterior;
	}

	public BigDecimal getValorJurosMoraSucumbenciaAnterior(){

		return valorJurosMoraSucumbenciaAnterior;
	}

	public void setValorJurosMoraSucumbenciaAnterior(BigDecimal valorJurosMoraSucumbenciaAnterior){

		this.valorJurosMoraSucumbenciaAnterior = valorJurosMoraSucumbenciaAnterior;
	}

	public BigDecimal getValorSucumbenciaAnterior(){

		return valorSucumbenciaAnterior;
	}

	public void setValorSucumbenciaAnterior(BigDecimal valorSucumbenciaAnterior){

		this.valorSucumbenciaAnterior = valorSucumbenciaAnterior;
	}

	public BigDecimal getValorSucumbenciaAtual(){

		return valorSucumbenciaAtual;
	}

	public void setValorSucumbenciaAtual(BigDecimal valorSucumbenciaAtual){

		this.valorSucumbenciaAtual = valorSucumbenciaAtual;
	}

	public String getColecaoProcessosExecucaoFiscal(){

		return colecaoProcessosExecucaoFiscal;
	}

	public void setColecaoProcessosExecucaoFiscal(String colecaoProcessosExecucaoFiscal){

		this.colecaoProcessosExecucaoFiscal = colecaoProcessosExecucaoFiscal;
	}

	public String getColecaoDatasParcelamento(){

		return colecaoDatasParcelamento;
	}

	public void setColecaoDatasParcelamento(String colecaoDatasParcelamento){

		this.colecaoDatasParcelamento = colecaoDatasParcelamento;
	}

	public String getColecaoDatasParcelamentoSobra(){

		return colecaoDatasParcelamentoSobra;
	}

	public void setColecaoDatasParcelamentoSobra(String colecaoDatasParcelamentoSobra){

		this.colecaoDatasParcelamentoSobra = colecaoDatasParcelamentoSobra;
	}

	public String getColecaoServicosDebitoACobrar(){

		return colecaoServicosDebitoACobrar;
	}

	public void setColecaoServicosDebitoACobrar(String colecaoServicosDebitoACobrar){

		this.colecaoServicosDebitoACobrar = colecaoServicosDebitoACobrar;
	}

	public String getColecaoServicosDebitoACobrarSobra(){

		return colecaoServicosDebitoACobrarSobra;
	}

	public void setColecaoServicosDebitoACobrarSobra(String colecaoServicosDebitoACobrarSobra){

		this.colecaoServicosDebitoACobrarSobra = colecaoServicosDebitoACobrarSobra;
	}

	public String getColecaoServicosParcelamentoSobra(){

		return colecaoServicosParcelamentoSobra;
	}

	public void setColecaoServicosParcelamentoSobra(String colecaoServicosParcelamentoSobra){

		this.colecaoServicosParcelamentoSobra = colecaoServicosParcelamentoSobra;
	}

	public String getColecaoServicosParcelamento(){

		return colecaoServicosParcelamento;
	}

	public void setColecaoServicosParcelamento(String colecaoServicosParcelamento){

		this.colecaoServicosParcelamento = colecaoServicosParcelamento;
	}

	public String getCpfCnpj(){

		return cpfCnpj;
	}

	public void setCpfCnpj(String cpfCnpj){

		this.cpfCnpj = cpfCnpj;
	}

	public Date getDataParcelamento(){

		return dataParcelamento;
	}

	public void setDataParcelamento(Date dataParcelamento){

		this.dataParcelamento = dataParcelamento;
	}

	public String getEndereco(){

		return endereco;
	}

	public void setEndereco(String endereco){

		this.endereco = endereco;
	}

	public Integer getIdFuncionario(){

		return idFuncionario;
	}

	public void setIdFuncionario(Integer idFuncionario){

		this.idFuncionario = idFuncionario;
	}

	public Integer getIdImovel(){

		return idImovel;
	}

	public void setIdImovel(Integer idImovel){

		this.idImovel = idImovel;
	}

	public String getNomeCliente(){

		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente){

		this.nomeCliente = nomeCliente;
	}

	public String getNomeMunicipio(){

		return nomeMunicipio;
	}

	public void setNomeMunicipio(String nomeMunicipio){

		this.nomeMunicipio = nomeMunicipio;
	}

	public Integer getNumeroParcelas(){

		return numeroParcelas;
	}

	public void setNumeroParcelas(Integer numeroParcelas){

		this.numeroParcelas = numeroParcelas;
	}

	public String getSolicitacaoRestabelecimento(){

		return solicitacaoRestabelecimento;
	}

	public void setSolicitacaoRestabelecimento(String solicitacaoRestabelecimento){

		this.solicitacaoRestabelecimento = solicitacaoRestabelecimento;
	}

	public String getTelefone(){

		return telefone;
	}

	public void setTelefone(String telefone){

		this.telefone = telefone;
	}

	public BigDecimal getUnidadeUsuario(){

		return unidadeUsuario;
	}

	public void setUnidadeUsuario(BigDecimal unidadeUsuario){

		this.unidadeUsuario = unidadeUsuario;
	}

	public BigDecimal getValorASerNegociado(){

		return valorASerNegociado;
	}

	public void setValorASerNegociado(BigDecimal valorASerNegociado){

		this.valorASerNegociado = valorASerNegociado;
	}

	public BigDecimal getValorASerParcelado(){

		return valorASerParcelado;
	}

	public void setValorASerParcelado(BigDecimal valorASerParcelado){

		this.valorASerParcelado = valorASerParcelado;
	}

	public BigDecimal getValorAtualizacaoMonetaria(){

		return valorAtualizacaoMonetaria;
	}

	public void setValorAtualizacaoMonetaria(BigDecimal valorAtualizacaoMonetaria){

		this.valorAtualizacaoMonetaria = valorAtualizacaoMonetaria;
	}

	public BigDecimal getValorCreditosRealizar(){

		return valorCreditosRealizar;
	}

	public void setValorCreditosRealizar(BigDecimal valorCreditosRealizar){

		this.valorCreditosRealizar = valorCreditosRealizar;
	}

	public BigDecimal getValorDescontoAcrescimo(){

		return valorDescontoAcrescimo;
	}

	public void setValorDescontoAcrescimo(BigDecimal valorDescontoAcrescimo){

		this.valorDescontoAcrescimo = valorDescontoAcrescimo;
	}

	public BigDecimal getValorDescontoAntiguidade(){

		return valorDescontoAntiguidade;
	}

	public void setValorDescontoAntiguidade(BigDecimal valorDescontoAntiguidade){

		this.valorDescontoAntiguidade = valorDescontoAntiguidade;
	}

	public BigDecimal getValorDescontoInatividade(){

		return valorDescontoInatividade;
	}

	public void setValorDescontoInatividade(BigDecimal valorDescontoInatividade){

		this.valorDescontoInatividade = valorDescontoInatividade;
	}

	public BigDecimal getValorEntrada(){

		return valorEntrada;
	}

	public void setValorEntrada(BigDecimal valorEntrada){

		this.valorEntrada = valorEntrada;
	}

	public BigDecimal getValorFaturasEmAberto(){

		return valorFaturasEmAberto;
	}

	public void setValorFaturasEmAberto(BigDecimal valorFaturasEmAberto){

		this.valorFaturasEmAberto = valorFaturasEmAberto;
	}

	public BigDecimal getValorGuiaPagamento(){

		return valorGuiaPagamento;
	}

	public void setValorGuiaPagamento(BigDecimal valorGuiaPagamento){

		this.valorGuiaPagamento = valorGuiaPagamento;
	}

	public BigDecimal getValorJurosMora(){

		return valorJurosMora;
	}

	public void setValorJurosMora(BigDecimal valorJurosMora){

		this.valorJurosMora = valorJurosMora;
	}

	public BigDecimal getValorMultas(){

		return valorMultas;
	}

	public void setValorMultas(BigDecimal valorMultas){

		this.valorMultas = valorMultas;
	}

	public BigDecimal getValorParcela(){

		return valorParcela;
	}

	public void setValorParcela(BigDecimal valorParcela){

		this.valorParcela = valorParcela;
	}

	public BigDecimal getValorParcelamentoACobrar(){

		return valorParcelamentoACobrar;
	}

	public void setValorParcelamentoACobrar(BigDecimal valorParcelamentoACobrar){

		this.valorParcelamentoACobrar = valorParcelamentoACobrar;
	}

	public BigDecimal getValorServicosACobrar(){

		return valorServicosACobrar;
	}

	public void setValorServicosACobrar(BigDecimal valorServicosACobrar){

		this.valorServicosACobrar = valorServicosACobrar;
	}

	public BigDecimal getValorTotalDebitos(){

		return valorTotalDebitos;
	}

	public void setValorTotalDebitos(BigDecimal valorTotalDebitos){

		this.valorTotalDebitos = valorTotalDebitos;
	}

	public BigDecimal getValorTotalDescontos(){

		return valorTotalDescontos;
	}

	public void setValorTotalDescontos(BigDecimal valorTotalDescontos){

		this.valorTotalDescontos = valorTotalDescontos;
	}

	/**
	 * @return Retorna o campo cpfClienteParcelamento.
	 */
	public String getCpfClienteParcelamento(){

		return cpfClienteParcelamento;
	}

	/**
	 * @param cpfClienteParcelamento
	 *            O cpfClienteParcelamento a ser setado.
	 */
	public void setCpfClienteParcelamento(String cpfClienteParcelamento){

		this.cpfClienteParcelamento = cpfClienteParcelamento;
	}

	/**
	 * @return Retorna o campo nomeClienteParcelamento.
	 */
	public String getNomeClienteParcelamento(){

		return nomeClienteParcelamento;
	}

	/**
	 * @param nomeClienteParcelamento
	 *            O nomeClienteParcelamento a ser setado.
	 */
	public void setNomeClienteParcelamento(String nomeClienteParcelamento){

		this.nomeClienteParcelamento = nomeClienteParcelamento;
	}

	public String getRgCliente(){

		return rgCliente;
	}

	public void setRgCliente(String rgCliente){

		this.rgCliente = rgCliente;
	}

	public String getDescOrgaoExpRgCliente(){

		return descOrgaoExpRgCliente;
	}

	public void setDescOrgaoExpRgCliente(String descOrgaoExpRgCliente){

		this.descOrgaoExpRgCliente = descOrgaoExpRgCliente;
	}

	public String getSiglaUnidadeFederacaoRgCliente(){

		return siglaUnidadeFederacaoRgCliente;
	}

	public void setSiglaUnidadeFederacaoRgCliente(String siglaUnidadeFederacaoRgCliente){

		this.siglaUnidadeFederacaoRgCliente = siglaUnidadeFederacaoRgCliente;
	}

	public String getMesAnoFinalParcelamento(){

		return mesAnoFinalParcelamento;
	}

	public void setMesAnoFinalParcelamento(String mesAnoFinalParcelamento){

		this.mesAnoFinalParcelamento = mesAnoFinalParcelamento;
	}

	public String getMesAnoInicioParcelamento(){

		return mesAnoInicioParcelamento;
	}

	public void setMesAnoInicioParcelamento(String mesAnoInicioParcelamento){

		this.mesAnoInicioParcelamento = mesAnoInicioParcelamento;
	}

	public String getRgClienteParcelamento(){

		return rgClienteParcelamento;
	}

	public void setRgClienteParcelamento(String rgClienteParcelamento){

		this.rgClienteParcelamento = rgClienteParcelamento;
	}

	public String getTaxaJuros(){

		return taxaJuros;
	}

	public void setTaxaJuros(String taxaJuros){

		this.taxaJuros = taxaJuros;
	}

	public BigDecimal getValorDescontoSancoesRegulamentares(){

		return valorDescontoSancoesRegulamentares;
	}

	public void setValorDescontoSancoesRegulamentares(BigDecimal valorDescontoSancoesRegulamentares){

		this.valorDescontoSancoesRegulamentares = valorDescontoSancoesRegulamentares;
	}

	public BigDecimal getValorDescontoTarifaSocial(){

		return valorDescontoTarifaSocial;
	}

	public void setValorDescontoTarifaSocial(BigDecimal valorDescontoTarifaSocial){

		this.valorDescontoTarifaSocial = valorDescontoTarifaSocial;
	}

	public BigDecimal getValorTotalDebitosExtenso(){

		return valorTotalDebitosExtenso;
	}

	public void setValorTotalDebitosExtenso(BigDecimal valorTotalDebitosExtenso){

		this.valorTotalDebitosExtenso = valorTotalDebitosExtenso;
	}

	public String getValorEntradaExtenso(){

		return valorEntradaExtenso;
	}

	public void setValorEntradaExtenso(String valorEntradaExtenso){

		this.valorEntradaExtenso = valorEntradaExtenso;
	}

	public String getNumeroParcelasExtenso(){

		return numeroParcelasExtenso;
	}

	public void setNumeroParcelasExtenso(String numeroParcelasExtenso){

		this.numeroParcelasExtenso = numeroParcelasExtenso;
	}

	public BigDecimal getValorJurosMoraExtenso(){

		return valorJurosMoraExtenso;
	}

	public void setValorJurosMoraExtenso(BigDecimal valorJurosMoraExtenso){

		this.valorJurosMoraExtenso = valorJurosMoraExtenso;
	}

	public String getFax(){

		return fax;
	}

	public void setFax(String fax){

		this.fax = fax;
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

	public Short getImovelDiaVencimento(){

		return imovelDiaVencimento;
	}

	public void setImovelDiaVencimento(Short imovelDiaVencimento){

		this.imovelDiaVencimento = imovelDiaVencimento;
	}

	public Integer getIdCliente(){

		return idCliente;
	}

	public void setIdCliente(Integer idCliente){

		this.idCliente = idCliente;
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

	public Date getDataEntradaParcelamento(){

		return dataEntradaParcelamento;
	}

	public void setDataEntradaParcelamento(Date dataEntradaParcelamento){

		this.dataEntradaParcelamento = dataEntradaParcelamento;
	}

	public BigDecimal getValorJurosParcelamento(){

		return valorJurosParcelamento;
	}

	public void setValorJurosParcelamento(BigDecimal valorJurosParcelamento){

		this.valorJurosParcelamento = valorJurosParcelamento;
	}

	public Integer getIdResolucaoDiretoria(){

		return idResolucaoDiretoria;
	}

	public void setIdResolucaoDiretoria(Integer idResolucaoDiretoria){

		this.idResolucaoDiretoria = idResolucaoDiretoria;
	}

	public String getCep(){

		return cep;
	}

	public void setCep(String cep){

		this.cep = cep;
	}

	public Integer getNumeroContas(){

		return numeroContas;
	}

	public void setNumeroContas(Integer numeroContas){

		this.numeroContas = numeroContas;
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

	public Integer getAnoMesReferenciaDebitoInicial(){

		return anoMesReferenciaDebitoInicial;
	}

	public void setAnoMesReferenciaDebitoInicial(Integer anoMesReferenciaDebitoInicial){

		this.anoMesReferenciaDebitoInicial = anoMesReferenciaDebitoInicial;
	}

	public Integer getAnoMesReferenciaDebitoFinal(){

		return anoMesReferenciaDebitoFinal;
	}

	public void setAnoMesReferenciaDebitoFinal(Integer anoMesReferenciaDebitoFinal){

		this.anoMesReferenciaDebitoFinal = anoMesReferenciaDebitoFinal;
	}

	public Short getIndicadorPessoaFisicaJuridica(){

		return indicadorPessoaFisicaJuridica;
	}

	public void setIndicadorPessoaFisicaJuridica(Short indicadorPessoaFisicaJuridica){

		this.indicadorPessoaFisicaJuridica = indicadorPessoaFisicaJuridica;
	}

	public BigDecimal getTotalNegociado(){

		return totalNegociado;
	}

	public void setTotalNegociado(BigDecimal totalNegociado){

		this.totalNegociado = totalNegociado;
	}

}
