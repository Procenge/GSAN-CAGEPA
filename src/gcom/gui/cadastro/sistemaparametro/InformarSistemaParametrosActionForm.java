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
 * 
 * GSANPCG
 * Eduardo Henrique
 */

package gcom.gui.cadastro.sistemaparametro;

import org.apache.struts.validator.ValidatorActionForm;

/**
 * Descrição da classe
 * 
 * @author Rômulo Aurélio
 * @date 12/01/2007
 * @author eduardo henrique
 * @date 05/06/2008
 *       Adição do Parâmetro de Unidade da Presidência na Aba Atendimento ao Publico
 *       Adição de Parâmetros Obrigatórios, porém ainda não utilizados Roteiro Padrão e Indicador de
 *       Faturamento Antecipado
 */
public class InformarSistemaParametrosActionForm
				extends ValidatorActionForm {

	private static final long serialVersionUID = 1L;

	// 1 aba InserirParametrosSistemaDadosGeraisEmpresa

	private String nomeEstado;

	private String nomeEmpresa;

	private String abreviaturaEmpresa;

	private String cnpj;

	private String logradouro;

	private String nomeLogradouro;

	private String numero;

	private String complemento;

	private String bairro;

	private String cep;

	private String enderecoReferencia;

	private String numeroTelefone;

	private String ramal;

	private String fax;

	private String email;

	private String imagemLogomarca;

	private String imagemRelatorio;

	private String imagemConta;

	private String imagemRelatorioPatch;

	private String imagemContaPatch;

	private String imagemCabecalho;

	private String imagemPrincipal;

	private String imagemSecundaria;

	// 2ª aba InserirParametrosSistemaFaturamentoTarifaSocial

	private String mesAnoReferencia;

	private String menorConsumo;

	private String menorValor;

	private String qtdeEconomias;

	private String mesesCalculoMedio;

	private String diasMinimoVencimento;

	private String diasMinimoVencimentoCorreio;

	private String numeroMesesValidadeConta;

	private String numeroMesesAlteracaoVencimento;

	private String salarioMinimo;

	private String areaMaxima;

	private String consumoMaximo;

	private String titulosRelatorio;

	private String numeroMaximoTiposDebitoEmissaoDocumento;

	private String numeroDiasEsperaExtratoDebito;

	private String numeroDiasEsperaMenor;

	private String numeroDiasEsperaMaior;

	private String indicadorClienteAtualFatura;

	private String anoReferenciaDebitoConta;

	private String numeroMinDebitosAguaParaTodos;

	private String codMotivoExclusaoAguaParaTodos;

	private String numeroConsumoMinAguaParaTodos;

	private String numeroConsumoExcedidoAguaParaTodos;

	private String codMotExclusaoConsumoAguaParaTodos;

	private String codTarifaAguaParaTodos;

	private String numeroMaxDiasVigenciaTarifaAguaParaTodos;

	// 3 aba
	// InserirParametrosSistemaArrecadacaoFinanceiro

	private String mesAnoReferenciaArrecadacao;

	private String codigoEmpresaFebraban;

	private String numeroLayOut;

	private String indentificadorContaDevolucao;

	private String percentualEntradaMinima;

	private String maximoParcelas;

	private String percentualMaximoAbatimento;

	private String percentualTaxaFinanciamento;

	private String numeroMaximoParcelaCredito;

	private String percentualCalculoIndice;

	// 4 aba
	// InserirParametrosSistemaMicromedicaoCobranca

	private String codigoMenorCapacidade;

	private String indicadorGeracaoFaixaFalsa;

	private String indicadorLayoutArquivoLeituraPadrao;

	private String percentualAnormalidadeAceitavel;

	private String codigoLimiteAceitavelAnormalidades;

	private String indicadorPercentualGeracaoFaixaFalsa;

	private String percentualGeracaoFaixaFalsa;

	private String indicadorGeracaoFiscalizacaoLeitura;

	private String indicadorPercentualGeracaoFiscalizacaoLeitura;

	private String percentualGeracaoFiscalizacaoLeitura;

	private String incrementoMaximoConsumo;

	private String decrementoMaximoConsumo;

	private String percentualToleranciaRateioConsumo;

	private String diasVencimentoCobranca;

	// 5 aba
	// InserirParametrosSistemaAtendimentoPublicoSeguranca

	private String indicadorSugestaoTramite;

	private String diasMaximoReativarRA;

	private String diasMaximoAlterarOS;

	private String ultimoIDGeracaoRA;

	private String idUnidadeOrganizacionalPresidencia;

	private String nomeUnidadeOrganizacionalPresidencia;

	private String diasMaximoExpirarAcesso;

	private String diasMensagemExpiracaoSenha;

	private String numeroMaximoTentativasAcesso;

	private String numeroMaximoFavoritosMenu;

	private String ipServidorSmtp;

	private String emailResponsavel;

	private String indicadorFaturamentoAntecipado;

	private String indicadorRoteiroEmpresa;

	private String tipoSolicitacao;

	private String especificacao;

	private String tipoSolicitacaoReiteracao;

	private String especificacaoReiteracao;

	public String getEspecificacaoReiteracao(){

		return especificacaoReiteracao;
	}

	public void setEspecificacaoReiteracao(String especificacaoReiteracao){

		this.especificacaoReiteracao = especificacaoReiteracao;
	}

	public String getTipoSolicitacao(){

		return tipoSolicitacao;
	}

	public void setTipoSolicitacao(String tipoSolicitacao){

		this.tipoSolicitacao = tipoSolicitacao;
	}

	public String getEspecificacao(){

		return especificacao;
	}

	public void setEspecificacao(String especificacao){

		this.especificacao = especificacao;
	}

	/**
	 * @return Returns the emailResponsavel.
	 */
	public String getEmailResponsavel(){

		return emailResponsavel;
	}

	/**
	 * @param emailResponsavel
	 *            The emailResponsavel to set.
	 */
	public void setEmailResponsavel(String emailResponsavel){

		this.emailResponsavel = emailResponsavel;
	}

	/**
	 * @return Returns the ipServidorSmtp.
	 */
	public String getIpServidorSmtp(){

		return ipServidorSmtp;
	}

	/**
	 * @param ipServidorSmtp
	 *            The ipServidorSmtp to set.
	 */
	public void setIpServidorSmtp(String ipServidorSmtp){

		this.ipServidorSmtp = ipServidorSmtp;
	}

	/**
	 * @return Retorna o campo abreviaturaEmpresa.
	 */
	public String getAbreviaturaEmpresa(){

		return abreviaturaEmpresa;
	}

	/**
	 * @param abreviaturaEmpresa
	 *            O abreviaturaEmpresa a ser setado.
	 */
	public void setAbreviaturaEmpresa(String abreviaturaEmpresa){

		this.abreviaturaEmpresa = abreviaturaEmpresa;
	}

	/**
	 * @return Retorna o campo areaMaxima.
	 */
	public String getAreaMaxima(){

		return areaMaxima;
	}

	/**
	 * @param areaMaxima
	 *            O areaMaxima a ser setado.
	 */
	public void setAreaMaxima(String areaMaxima){

		this.areaMaxima = areaMaxima;
	}

	/**
	 * @return Retorna o campo bairro.
	 */
	public String getBairro(){

		return bairro;
	}

	/**
	 * @param bairro
	 *            O bairro a ser setado.
	 */
	public void setBairro(String bairro){

		this.bairro = bairro;
	}

	/**
	 * @return Retorna o campo cep.
	 */
	public String getCep(){

		return cep;
	}

	/**
	 * @param cep
	 *            O cep a ser setado.
	 */
	public void setCep(String cep){

		this.cep = cep;
	}

	/**
	 * @return Retorna o campo cnpj.
	 */
	public String getCnpj(){

		return cnpj;
	}

	/**
	 * @param cnpj
	 *            O cnpj a ser setado.
	 */
	public void setCnpj(String cnpj){

		this.cnpj = cnpj;
	}

	/**
	 * @return Retorna o campo codigoEmpresaFebraban.
	 */
	public String getCodigoEmpresaFebraban(){

		return codigoEmpresaFebraban;
	}

	/**
	 * @param codigoEmpresaFebraban
	 *            O codigoEmpresaFebraban a ser setado.
	 */
	public void setCodigoEmpresaFebraban(String codigoEmpresaFebraban){

		this.codigoEmpresaFebraban = codigoEmpresaFebraban;
	}

	/**
	 * @return Retorna o campo codigoMenorCapacidade.
	 */
	public String getCodigoMenorCapacidade(){

		return codigoMenorCapacidade;
	}

	/**
	 * @param codigoMenorCapacidade
	 *            O codigoMenorCapacidade a ser setado.
	 */
	public void setCodigoMenorCapacidade(String codigoMenorCapacidade){

		this.codigoMenorCapacidade = codigoMenorCapacidade;
	}

	/**
	 * @return Retorna o campo complemento.
	 */
	public String getComplemento(){

		return complemento;
	}

	/**
	 * @param complemento
	 *            O complemento a ser setado.
	 */
	public void setComplemento(String complemento){

		this.complemento = complemento;
	}

	/**
	 * @return Retorna o campo consumoMaximo.
	 */
	public String getConsumoMaximo(){

		return consumoMaximo;
	}

	/**
	 * @param consumoMaximo
	 *            O consumoMaximo a ser setado.
	 */
	public void setConsumoMaximo(String consumoMaximo){

		this.consumoMaximo = consumoMaximo;
	}

	/**
	 * @return Retorna o campo decrementoMaximoConsumo.
	 */
	public String getDecrementoMaximoConsumo(){

		return decrementoMaximoConsumo;
	}

	/**
	 * @param decrementoMaximoConsumo
	 *            O decrementoMaximoConsumo a ser setado.
	 */
	public void setDecrementoMaximoConsumo(String decrementoMaximoConsumo){

		this.decrementoMaximoConsumo = decrementoMaximoConsumo;
	}

	/**
	 * @return Retorna o campo diasMaximoAlterarOS.
	 */
	public String getDiasMaximoAlterarOS(){

		return diasMaximoAlterarOS;
	}

	/**
	 * @param diasMaximoAlterarOS
	 *            O diasMaximoAlterarOS a ser setado.
	 */
	public void setDiasMaximoAlterarOS(String diasMaximoAlterarOS){

		this.diasMaximoAlterarOS = diasMaximoAlterarOS;
	}

	/**
	 * @return Retorna o campo diasMaximoExpirarAcesso.
	 */
	public String getDiasMaximoExpirarAcesso(){

		return diasMaximoExpirarAcesso;
	}

	/**
	 * @param diasMaximoExpirarAcesso
	 *            O diasMaximoExpirarAcesso a ser setado.
	 */
	public void setDiasMaximoExpirarAcesso(String diasMaximoExpirarAcesso){

		this.diasMaximoExpirarAcesso = diasMaximoExpirarAcesso;
	}

	/**
	 * @return Retorna o campo diasMaximoReativarRA.
	 */
	public String getDiasMaximoReativarRA(){

		return diasMaximoReativarRA;
	}

	/**
	 * @param diasMaximoReativarRA
	 *            O diasMaximoReativarRA a ser setado.
	 */
	public void setDiasMaximoReativarRA(String diasMaximoReativarRA){

		this.diasMaximoReativarRA = diasMaximoReativarRA;
	}

	/**
	 * @return Retorna o campo diasMensagemExpiracaoSenha.
	 */
	public String getDiasMensagemExpiracaoSenha(){

		return diasMensagemExpiracaoSenha;
	}

	/**
	 * @param diasMensagemExpiracaoSenha
	 *            O diasMensagemExpiracaoSenha a ser setado.
	 */
	public void setDiasMensagemExpiracaoSenha(String diasMensagemExpiracaoSenha){

		this.diasMensagemExpiracaoSenha = diasMensagemExpiracaoSenha;
	}

	/**
	 * @return Retorna o campo diasMinimoVencimento.
	 */
	public String getDiasMinimoVencimento(){

		return diasMinimoVencimento;
	}

	/**
	 * @param diasMinimoVencimento
	 *            O diasMinimoVencimento a ser setado.
	 */
	public void setDiasMinimoVencimento(String diasMinimoVencimento){

		this.diasMinimoVencimento = diasMinimoVencimento;
	}

	/**
	 * @return Retorna o campo diasMinimoVencimentoCorreio.
	 */
	public String getDiasMinimoVencimentoCorreio(){

		return diasMinimoVencimentoCorreio;
	}

	/**
	 * @param diasMinimoVencimentoCorreio
	 *            O diasMinimoVencimentoCorreio a ser setado.
	 */
	public void setDiasMinimoVencimentoCorreio(String diasMinimoVencimentoCorreio){

		this.diasMinimoVencimentoCorreio = diasMinimoVencimentoCorreio;
	}

	/**
	 * @return Retorna o campo diasVencimentoCobranca.
	 */
	public String getDiasVencimentoCobranca(){

		return diasVencimentoCobranca;
	}

	/**
	 * @param diasVencimentoCobranca
	 *            O diasVencimentoCobranca a ser setado.
	 */
	public void setDiasVencimentoCobranca(String diasVencimentoCobranca){

		this.diasVencimentoCobranca = diasVencimentoCobranca;
	}

	/**
	 * @return Retorna o campo email.
	 */
	public String getEmail(){

		return email;
	}

	/**
	 * @param email
	 *            O email a ser setado.
	 */
	public void setEmail(String email){

		this.email = email;
	}

	/**
	 * @return Retorna o campo enderecoReferencia.
	 */
	public String getEnderecoReferencia(){

		return enderecoReferencia;
	}

	/**
	 * @param enderecoReferencia
	 *            O enderecoReferencia a ser setado.
	 */
	public void setEnderecoReferencia(String enderecoReferencia){

		this.enderecoReferencia = enderecoReferencia;
	}

	/**
	 * @return Retorna o campo fax.
	 */
	public String getFax(){

		return fax;
	}

	/**
	 * @param fax
	 *            O fax a ser setado.
	 */
	public void setFax(String fax){

		this.fax = fax;
	}

	/**
	 * @return Retorna o campo incrementoMaximoConsumo.
	 */
	public String getIncrementoMaximoConsumo(){

		return incrementoMaximoConsumo;
	}

	/**
	 * @param incrementoMaximoConsumo
	 *            O incrementoMaximoConsumo a ser setado.
	 */
	public void setIncrementoMaximoConsumo(String incrementoMaximoConsumo){

		this.incrementoMaximoConsumo = incrementoMaximoConsumo;
	}

	/**
	 * @return Retorna o campo indentificadorContaDevolucao.
	 */
	public String getIndentificadorContaDevolucao(){

		return indentificadorContaDevolucao;
	}

	/**
	 * @param indentificadorContaDevolucao
	 *            O indentificadorContaDevolucao a ser setado.
	 */
	public void setIndentificadorContaDevolucao(String indentificadorContaDevolucao){

		this.indentificadorContaDevolucao = indentificadorContaDevolucao;
	}

	/**
	 * @return Retorna o campo indicadorGeracaoFaixaFalsa.
	 */
	public String getIndicadorGeracaoFaixaFalsa(){

		return indicadorGeracaoFaixaFalsa;
	}

	/**
	 * @param indicadorGeracaoFaixaFalsa
	 *            O indicadorGeracaoFaixaFalsa a ser setado.
	 */
	public void setIndicadorGeracaoFaixaFalsa(String indicadorGeracaoFaixaFalsa){

		this.indicadorGeracaoFaixaFalsa = indicadorGeracaoFaixaFalsa;
	}

	/**
	 * @return Retorna o campo indicadorGeracaoFiscalizacaoLeitura.
	 */
	public String getIndicadorGeracaoFiscalizacaoLeitura(){

		return indicadorGeracaoFiscalizacaoLeitura;
	}

	/**
	 * @param indicadorGeracaoFiscalizacaoLeitura
	 *            O indicadorGeracaoFiscalizacaoLeitura a ser setado.
	 */
	public void setIndicadorGeracaoFiscalizacaoLeitura(String indicadorGeracaoFiscalizacaoLeitura){

		this.indicadorGeracaoFiscalizacaoLeitura = indicadorGeracaoFiscalizacaoLeitura;
	}

	/**
	 * @return Retorna o campo indicadorPercentualGeracaoFaixaFalsa.
	 */
	public String getIndicadorPercentualGeracaoFaixaFalsa(){

		return indicadorPercentualGeracaoFaixaFalsa;
	}

	/**
	 * @param indicadorPercentualGeracaoFaixaFalsa
	 *            O indicadorPercentualGeracaoFaixaFalsa a ser setado.
	 */
	public void setIndicadorPercentualGeracaoFaixaFalsa(String indicadorPercentualGeracaoFaixaFalsa){

		this.indicadorPercentualGeracaoFaixaFalsa = indicadorPercentualGeracaoFaixaFalsa;
	}

	/**
	 * @return Retorna o campo indicadorPercentualGeracaoFiscalizacaoLeitura.
	 */
	public String getIndicadorPercentualGeracaoFiscalizacaoLeitura(){

		return indicadorPercentualGeracaoFiscalizacaoLeitura;
	}

	/**
	 * @param indicadorPercentualGeracaoFiscalizacaoLeitura
	 *            O indicadorPercentualGeracaoFiscalizacaoLeitura a ser setado.
	 */
	public void setIndicadorPercentualGeracaoFiscalizacaoLeitura(String indicadorPercentualGeracaoFiscalizacaoLeitura){

		this.indicadorPercentualGeracaoFiscalizacaoLeitura = indicadorPercentualGeracaoFiscalizacaoLeitura;
	}

	/**
	 * @return Retorna o campo indicadorSugestaoTramite.
	 */
	public String getIndicadorSugestaoTramite(){

		return indicadorSugestaoTramite;
	}

	/**
	 * @param indicadorSugestaoTramite
	 *            O indicadorSugestaoTramite a ser setado.
	 */
	public void setIndicadorSugestaoTramite(String indicadorSugestaoTramite){

		this.indicadorSugestaoTramite = indicadorSugestaoTramite;
	}

	/**
	 * @return Retorna o campo logradouro.
	 */
	public String getLogradouro(){

		return logradouro;
	}

	/**
	 * @param logradouro
	 *            O logradouro a ser setado.
	 */
	public void setLogradouro(String logradouro){

		this.logradouro = logradouro;
	}

	/**
	 * @return Retorna o campo maximoParcelas.
	 */
	public String getMaximoParcelas(){

		return maximoParcelas;
	}

	/**
	 * @param maximoParcelas
	 *            O maximoParcelas a ser setado.
	 */
	public void setMaximoParcelas(String maximoParcelas){

		this.maximoParcelas = maximoParcelas;
	}

	/**
	 * @return Retorna o campo menorConsumo.
	 */
	public String getMenorConsumo(){

		return menorConsumo;
	}

	/**
	 * @param menorConsumo
	 *            O menorConsumo a ser setado.
	 */
	public void setMenorConsumo(String menorConsumo){

		this.menorConsumo = menorConsumo;
	}

	/**
	 * @return Retorna o campo menorValor.
	 */
	public String getMenorValor(){

		return menorValor;
	}

	/**
	 * @param menorValor
	 *            O menorValor a ser setado.
	 */
	public void setMenorValor(String menorValor){

		this.menorValor = menorValor;
	}

	/**
	 * @return Retorna o campo mesAnoReferencia.
	 */
	public String getMesAnoReferencia(){

		return mesAnoReferencia;
	}

	/**
	 * @param mesAnoReferencia
	 *            O mesAnoReferencia a ser setado.
	 */
	public void setMesAnoReferencia(String mesAnoReferencia){

		this.mesAnoReferencia = mesAnoReferencia;
	}

	/**
	 * @return Retorna o campo mesAnoReferenciaArrecadacao.
	 */
	public String getMesAnoReferenciaArrecadacao(){

		return mesAnoReferenciaArrecadacao;
	}

	/**
	 * @param mesAnoReferenciaArrecadacao
	 *            O mesAnoReferenciaArrecadacao a ser setado.
	 */
	public void setMesAnoReferenciaArrecadacao(String mesAnoReferenciaArrecadacao){

		this.mesAnoReferenciaArrecadacao = mesAnoReferenciaArrecadacao;
	}

	/**
	 * @return Retorna o campo mesesCalculoMedio.
	 */
	public String getMesesCalculoMedio(){

		return mesesCalculoMedio;
	}

	/**
	 * @param mesesCalculoMedio
	 *            O mesesCalculoMedio a ser setado.
	 */
	public void setMesesCalculoMedio(String mesesCalculoMedio){

		this.mesesCalculoMedio = mesesCalculoMedio;
	}

	/**
	 * @return Retorna o campo nomeEmpresa.
	 */
	public String getNomeEmpresa(){

		return nomeEmpresa;
	}

	/**
	 * @param nomeEmpresa
	 *            O nomeEmpresa a ser setado.
	 */
	public void setNomeEmpresa(String nomeEmpresa){

		this.nomeEmpresa = nomeEmpresa;
	}

	/**
	 * @return Retorna o campo nomeEstado.
	 */
	public String getNomeEstado(){

		return nomeEstado;
	}

	/**
	 * @param nomeEstado
	 *            O nomeEstado a ser setado.
	 */
	public void setNomeEstado(String nomeEstado){

		this.nomeEstado = nomeEstado;
	}

	/**
	 * @return Retorna o campo nomeLogradouro.
	 */
	public String getNomeLogradouro(){

		return nomeLogradouro;
	}

	/**
	 * @param nomeLogradouro
	 *            O nomeLogradouro a ser setado.
	 */
	public void setNomeLogradouro(String nomeLogradouro){

		this.nomeLogradouro = nomeLogradouro;
	}

	/**
	 * @return Retorna o campo numero.
	 */
	public String getNumero(){

		return numero;
	}

	/**
	 * @param numero
	 *            O numero a ser setado.
	 */
	public void setNumero(String numero){

		this.numero = numero;
	}

	/**
	 * @return Retorna o campo numeroLayOut.
	 */
	public String getNumeroLayOut(){

		return numeroLayOut;
	}

	/**
	 * @param numeroLayOut
	 *            O numeroLayOut a ser setado.
	 */
	public void setNumeroLayOut(String numeroLayOut){

		this.numeroLayOut = numeroLayOut;
	}

	/**
	 * @return Retorna o campo numeroMaximoFavoritosMenu.
	 */
	public String getNumeroMaximoFavoritosMenu(){

		return numeroMaximoFavoritosMenu;
	}

	/**
	 * @param numeroMaximoFavoritosMenu
	 *            O numeroMaximoFavoritosMenu a ser setado.
	 */
	public void setNumeroMaximoFavoritosMenu(String numeroMaximoFavoritosMenu){

		this.numeroMaximoFavoritosMenu = numeroMaximoFavoritosMenu;
	}

	/**
	 * @return Retorna o campo numeroMaximoParcelaCredito.
	 */
	public String getNumeroMaximoParcelaCredito(){

		return numeroMaximoParcelaCredito;
	}

	/**
	 * @param numeroMaximoParcelaCredito
	 *            O numeroMaximoParcelaCredito a ser setado.
	 */
	public void setNumeroMaximoParcelaCredito(String numeroMaximoParcelaCredito){

		this.numeroMaximoParcelaCredito = numeroMaximoParcelaCredito;
	}

	/**
	 * @return Retorna o campo numeroMaximoTentativasAcesso.
	 */
	public String getNumeroMaximoTentativasAcesso(){

		return numeroMaximoTentativasAcesso;
	}

	/**
	 * @param numeroMaximoTentativasAcesso
	 *            O numeroMaximoTentativasAcesso a ser setado.
	 */
	public void setNumeroMaximoTentativasAcesso(String numeroMaximoTentativasAcesso){

		this.numeroMaximoTentativasAcesso = numeroMaximoTentativasAcesso;
	}

	/**
	 * @return Retorna o campo numeroMesesAlteracaoVencimento.
	 */
	public String getNumeroMesesAlteracaoVencimento(){

		return numeroMesesAlteracaoVencimento;
	}

	/**
	 * @param numeroMesesAlteracaoVencimento
	 *            O numeroMesesAlteracaoVencimento a ser setado.
	 */
	public void setNumeroMesesAlteracaoVencimento(String numeroMesesAlteracaoVencimento){

		this.numeroMesesAlteracaoVencimento = numeroMesesAlteracaoVencimento;
	}

	/**
	 * @return Retorna o campo numeroMesesValidadeConta.
	 */
	public String getNumeroMesesValidadeConta(){

		return numeroMesesValidadeConta;
	}

	/**
	 * @param numeroMesesValidadeConta
	 *            O numeroMesesValidadeConta a ser setado.
	 */
	public void setNumeroMesesValidadeConta(String numeroMesesValidadeConta){

		this.numeroMesesValidadeConta = numeroMesesValidadeConta;
	}

	/**
	 * @return Retorna o campo numeroTelefone.
	 */
	public String getNumeroTelefone(){

		return numeroTelefone;
	}

	/**
	 * @param numeroTelefone
	 *            O numeroTelefone a ser setado.
	 */
	public void setNumeroTelefone(String numeroTelefone){

		this.numeroTelefone = numeroTelefone;
	}

	/**
	 * @return Retorna o campo percentualCalculoIndice.
	 */
	public String getPercentualCalculoIndice(){

		return percentualCalculoIndice;
	}

	/**
	 * @param percentualCalculoIndice
	 *            O percentualCalculoIndice a ser setado.
	 */
	public void setPercentualCalculoIndice(String percentualCalculoIndice){

		this.percentualCalculoIndice = percentualCalculoIndice;
	}

	/**
	 * @return Retorna o campo percentualEntradaMinima.
	 */
	public String getPercentualEntradaMinima(){

		return percentualEntradaMinima;
	}

	/**
	 * @param percentualEntradaMinima
	 *            O percentualEntradaMinima a ser setado.
	 */
	public void setPercentualEntradaMinima(String percentualEntradaMinima){

		this.percentualEntradaMinima = percentualEntradaMinima;
	}

	/**
	 * @return Retorna o campo percentualGeracaoFaixaFalsa.
	 */
	public String getPercentualGeracaoFaixaFalsa(){

		return percentualGeracaoFaixaFalsa;
	}

	/**
	 * @param percentualGeracaoFaixaFalsa
	 *            O percentualGeracaoFaixaFalsa a ser setado.
	 */
	public void setPercentualGeracaoFaixaFalsa(String percentualGeracaoFaixaFalsa){

		this.percentualGeracaoFaixaFalsa = percentualGeracaoFaixaFalsa;
	}

	/**
	 * @return Retorna o campo percentualGeracaoFiscalizacaoLeitura.
	 */
	public String getPercentualGeracaoFiscalizacaoLeitura(){

		return percentualGeracaoFiscalizacaoLeitura;
	}

	/**
	 * @param percentualGeracaoFiscalizacaoLeitura
	 *            O percentualGeracaoFiscalizacaoLeitura a ser setado.
	 */
	public void setPercentualGeracaoFiscalizacaoLeitura(String percentualGeracaoFiscalizacaoLeitura){

		this.percentualGeracaoFiscalizacaoLeitura = percentualGeracaoFiscalizacaoLeitura;
	}

	/**
	 * @return Retorna o campo percentualMaximoAbatimento.
	 */
	public String getPercentualMaximoAbatimento(){

		return percentualMaximoAbatimento;
	}

	/**
	 * @param percentualMaximoAbatimento
	 *            O percentualMaximoAbatimento a ser setado.
	 */
	public void setPercentualMaximoAbatimento(String percentualMaximoAbatimento){

		this.percentualMaximoAbatimento = percentualMaximoAbatimento;
	}

	/**
	 * @return Retorna o campo percentualTaxaFinanciamento.
	 */
	public String getPercentualTaxaFinanciamento(){

		return percentualTaxaFinanciamento;
	}

	/**
	 * @param percentualTaxaFinanciamento
	 *            O percentualTaxaFinanciamento a ser setado.
	 */
	public void setPercentualTaxaFinanciamento(String percentualTaxaFinanciamento){

		this.percentualTaxaFinanciamento = percentualTaxaFinanciamento;
	}

	/**
	 * @return Retorna o campo percentualToleranciaRateioConsumo.
	 */
	public String getPercentualToleranciaRateioConsumo(){

		return percentualToleranciaRateioConsumo;
	}

	/**
	 * @param percentualToleranciaRateioConsumo
	 *            O percentualToleranciaRateioConsumo a ser setado.
	 */
	public void setPercentualToleranciaRateioConsumo(String percentualToleranciaRateioConsumo){

		this.percentualToleranciaRateioConsumo = percentualToleranciaRateioConsumo;
	}

	/**
	 * @return Retorna o campo qtdeEconomias.
	 */
	public String getQtdeEconomias(){

		return qtdeEconomias;
	}

	/**
	 * @param qtdeEconomias
	 *            O qtdeEconomias a ser setado.
	 */
	public void setQtdeEconomias(String qtdeEconomias){

		this.qtdeEconomias = qtdeEconomias;
	}

	/**
	 * @return Retorna o campo ramal.
	 */
	public String getRamal(){

		return ramal;
	}

	/**
	 * @param ramal
	 *            O ramal a ser setado.
	 */
	public void setRamal(String ramal){

		this.ramal = ramal;
	}

	/**
	 * @return Retorna o campo salarioMinimo.
	 */
	public String getSalarioMinimo(){

		return salarioMinimo;
	}

	/**
	 * @param salarioMinimo
	 *            O salarioMinimo a ser setado.
	 */
	public void setSalarioMinimo(String salarioMinimo){

		this.salarioMinimo = salarioMinimo;
	}

	/**
	 * @return Retorna o campo ultimoIDGeracaoRA.
	 */
	public String getUltimoIDGeracaoRA(){

		return ultimoIDGeracaoRA;
	}

	/**
	 * @param ultimoIDGeracaoRA
	 *            O ultimoIDGeracaoRA a ser setado.
	 */
	public void setUltimoIDGeracaoRA(String ultimoIDGeracaoRA){

		this.ultimoIDGeracaoRA = ultimoIDGeracaoRA;
	}

	/**
	 * @return Retorna o campo titulosRelatorio.
	 */
	public String getTitulosRelatorio(){

		return titulosRelatorio;
	}

	/**
	 * @param titulosRelatorio
	 *            O titulosRelatorio a ser setado.
	 */
	public void setTitulosRelatorio(String titulosRelatorio){

		this.titulosRelatorio = titulosRelatorio;
	}

	public String getIdUnidadeOrganizacionalPresidencia(){

		return idUnidadeOrganizacionalPresidencia;
	}

	public void setIdUnidadeOrganizacionalPresidencia(String idUnidadeOrganizacionalPresidencia){

		this.idUnidadeOrganizacionalPresidencia = idUnidadeOrganizacionalPresidencia;
	}

	public String getNomeUnidadeOrganizacionalPresidencia(){

		return nomeUnidadeOrganizacionalPresidencia;
	}

	public void setNomeUnidadeOrganizacionalPresidencia(String nomeUnidadeOrganizacionalPresidencia){

		this.nomeUnidadeOrganizacionalPresidencia = nomeUnidadeOrganizacionalPresidencia;
	}

	public String getIndicadorFaturamentoAntecipado(){

		return indicadorFaturamentoAntecipado;
	}

	public void setIndicadorFaturamentoAntecipado(String indicadorFaturamentoAntecipado){

		this.indicadorFaturamentoAntecipado = indicadorFaturamentoAntecipado;
	}

	public String getIndicadorRoteiroEmpresa(){

		return indicadorRoteiroEmpresa;
	}

	public void setIndicadorRoteiroEmpresa(String indicadorRoteiroEmpresa){

		this.indicadorRoteiroEmpresa = indicadorRoteiroEmpresa;
	}

	/**
	 * @return the numeroMaximoTiposDebitoEmissaoDocumento
	 */
	public String getNumeroMaximoTiposDebitoEmissaoDocumento(){

		return numeroMaximoTiposDebitoEmissaoDocumento;
	}

	/**
	 * @param numeroMaximoTiposDebitoEmissaoDocumento
	 *            the numeroMaximoTiposDebitoEmissaoDocumento to set
	 */
	public void setNumeroMaximoTiposDebitoEmissaoDocumento(String numeroMaximoTiposDebitoEmissaoDocumento){

		this.numeroMaximoTiposDebitoEmissaoDocumento = numeroMaximoTiposDebitoEmissaoDocumento;
	}

	public String getNumeroDiasEsperaExtratoDebito(){

		return numeroDiasEsperaExtratoDebito;
	}

	public void setNumeroDiasEsperaExtratoDebito(String numeroDiasEsperaExtratoDebito){

		this.numeroDiasEsperaExtratoDebito = numeroDiasEsperaExtratoDebito;
	}

	public String getNumeroDiasEsperaMenor(){

		return numeroDiasEsperaMenor;
	}

	public void setNumeroDiasEsperaMenor(String numeroDiasEsperaMenor){

		this.numeroDiasEsperaMenor = numeroDiasEsperaMenor;
	}

	public String getNumeroDiasEsperaMaior(){

		return numeroDiasEsperaMaior;
	}

	public void setNumeroDiasEsperaMaior(String numeroDiasEsperaMaior){

		this.numeroDiasEsperaMaior = numeroDiasEsperaMaior;
	}

	public String getTipoSolicitacaoReiteracao(){

		return tipoSolicitacaoReiteracao;
	}

	public void setTipoSolicitacaoReiteracao(String tipoSolicitacaoReiteracao){

		this.tipoSolicitacaoReiteracao = tipoSolicitacaoReiteracao;
	}

	public String getIndicadorClienteAtualFatura(){

		return indicadorClienteAtualFatura;
	}

	public void setIndicadorClienteAtualFatura(String indicadorClienteAtualFatura){

		this.indicadorClienteAtualFatura = indicadorClienteAtualFatura;
	}

	public String getAnoReferenciaDebitoConta(){

		return anoReferenciaDebitoConta;
	}

	public void setAnoReferenciaDebitoConta(String anoReferenciaDebitoConta){

		this.anoReferenciaDebitoConta = anoReferenciaDebitoConta;
	}

	public String getImagemLogomarca(){

		return imagemLogomarca;
	}

	public void setImagemLogomarca(String imagemLogomarca){

		this.imagemLogomarca = imagemLogomarca;
	}

	public String getImagemRelatorio(){

		return imagemRelatorio;
	}

	public void setImagemRelatorio(String imagemRelatorio){

		this.imagemRelatorio = imagemRelatorio;
	}

	public String getImagemRelatorioPatch(){

		return imagemRelatorioPatch;
	}

	public void setImagemRelatorioPatch(String imagemRelatorioPatch){

		this.imagemRelatorioPatch = imagemRelatorioPatch;
	}

	public String getImagemConta(){

		return imagemConta;
	}

	public void setImagemConta(String imagemConta){

		this.imagemConta = imagemConta;
	}

	public String getImagemContaPatch(){

		return imagemContaPatch;
	}

	public void setImagemContaPatch(String imagemContaPatch){

		this.imagemContaPatch = imagemContaPatch;
	}

	public String getImagemCabecalho(){

		return imagemCabecalho;
	}

	public void setImagemCabecalho(String imagemCabecalho){

		this.imagemCabecalho = imagemCabecalho;
	}

	public String getImagemPrincipal(){

		return imagemPrincipal;
	}

	public void setImagemPrincipal(String imagemPrincipal){

		this.imagemPrincipal = imagemPrincipal;
	}

	public String getImagemSecundaria(){

		return imagemSecundaria;
	}

	public void setImagemSecundaria(String imagemSecundaria){

		this.imagemSecundaria = imagemSecundaria;
	}

	public String getNumeroMinDebitosAguaParaTodos(){

		return numeroMinDebitosAguaParaTodos;
	}

	public void setNumeroMinDebitosAguaParaTodos(String numeroMinDebitosAguaParaTodos){

		this.numeroMinDebitosAguaParaTodos = numeroMinDebitosAguaParaTodos;
	}

	public String getCodMotivoExclusaoAguaParaTodos(){

		return codMotivoExclusaoAguaParaTodos;
	}

	public void setCodMotivoExclusaoAguaParaTodos(String codMotivoExclusaoAguaParaTodos){

		this.codMotivoExclusaoAguaParaTodos = codMotivoExclusaoAguaParaTodos;
	}

	public String getNumeroConsumoMinAguaParaTodos(){

		return numeroConsumoMinAguaParaTodos;
	}

	public void setNumeroConsumoMinAguaParaTodos(String numeroConsumoMinAguaParaTodos){

		this.numeroConsumoMinAguaParaTodos = numeroConsumoMinAguaParaTodos;
	}

	public String getNumeroConsumoExcedidoAguaParaTodos(){

		return numeroConsumoExcedidoAguaParaTodos;
	}

	public void setNumeroConsumoExcedidoAguaParaTodos(String numeroConsumoExcedidoAguaParaTodos){

		this.numeroConsumoExcedidoAguaParaTodos = numeroConsumoExcedidoAguaParaTodos;
	}

	public String getCodMotExclusaoConsumoAguaParaTodos(){

		return codMotExclusaoConsumoAguaParaTodos;
	}

	public void setCodMotExclusaoConsumoAguaParaTodos(String codMotExclusaoConsumoAguaParaTodos){

		this.codMotExclusaoConsumoAguaParaTodos = codMotExclusaoConsumoAguaParaTodos;
	}

	public String getCodTarifaAguaParaTodos(){

		return codTarifaAguaParaTodos;
	}

	public void setCodTarifaAguaParaTodos(String codTarifaAguaParaTodos){

		this.codTarifaAguaParaTodos = codTarifaAguaParaTodos;
	}

	public String getNumeroMaxDiasVigenciaTarifaAguaParaTodos(){

		return numeroMaxDiasVigenciaTarifaAguaParaTodos;
	}

	public void setNumeroMaxDiasVigenciaTarifaAguaParaTodos(String numeroMaxDiasVigenciaTarifaAguaParaTodos){

		this.numeroMaxDiasVigenciaTarifaAguaParaTodos = numeroMaxDiasVigenciaTarifaAguaParaTodos;
	}

	public String getIndicadorLayoutArquivoLeituraPadrao(){

		return indicadorLayoutArquivoLeituraPadrao;
	}

	public void setIndicadorLayoutArquivoLeituraPadrao(String indicadorLayoutArquivoLeituraPadrao){

		this.indicadorLayoutArquivoLeituraPadrao = indicadorLayoutArquivoLeituraPadrao;
	}

	public String getPercentualAnormalidadeAceitavel(){

		return percentualAnormalidadeAceitavel;
	}

	public void setPercentualAnormalidadeAceitavel(String percentualAnormalidadeAceitavel){

		this.percentualAnormalidadeAceitavel = percentualAnormalidadeAceitavel;
	}

	public String getCodigoLimiteAceitavelAnormalidades(){

		return codigoLimiteAceitavelAnormalidades;
	}

	public void setCodigoLimiteAceitavelAnormalidades(String codigoLimiteAceitavelAnormalidades){

		this.codigoLimiteAceitavelAnormalidades = codigoLimiteAceitavelAnormalidades;
	}

}