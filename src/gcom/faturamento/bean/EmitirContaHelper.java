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
 * GSANPCG
 * Virgínia Melo
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

package gcom.faturamento.bean;

import gcom.util.Util;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import br.com.procenge.util.Constantes;

/**
 * [UC ]
 * 
 * @author Vivianne Sousa
 * @date 28/06/2007
 * @author Virgínia Melo
 * @date 06/01/2009
 *       Adicionados campos de qualidade da água
 */
public class EmitirContaHelper
				implements Serializable {

	private static final long serialVersionUID = 1L;

	public final static BigDecimal VALOR_LIMITE_FICHA_COMPENSACAO = new BigDecimal("1000");

	private Integer idConta;

	private Integer idCliente;

	private String nomeCliente;

	private String tipoDocCliente;

	private String cpfCnpjCliente;

	private Date dataVencimentoConta;

	private int amReferencia;

	private short digitoVerificadorConta;

	private Integer codigoSetorComercialConta;

	private Integer idQuadraConta;

	private Short loteConta;

	private Short subLoteConta;

	private Integer consumoAgua;

	private Integer consumoEsgoto;

	private BigDecimal valorAgua;

	private BigDecimal valorEsgoto;

	private BigDecimal debitos;

	private BigDecimal valorCreditos;

	private BigDecimal valorImpostos;

	private Date dataValidadeConta;

	private Integer idImovel;

	private Integer idLocalidade;

	private String descricaoLocalidade;

	private Integer idGerenciaRegional;

	private String nomeGerenciaRegional;

	private Integer idLigacaoAguaSituacao;

	private Integer idLigacaoEsgotoSituacao;

	private String descricaoLigacaoAguaSituacao;

	private String descricaoLigacaoEsgotoSituacao;

	private Integer idImovelPerfil;

	private Integer idSetorComercial;

	private Integer idFaturamentoGrupo;

	private Integer idEmpresa;

	private BigDecimal percentualEsgotoConta;

	private BigDecimal valorConta;

	private String enderecoImovel;

	private String inscricaoImovel;

	private String idClienteResponsavel;

	private String enderecoClienteResponsavel;

	private String dadosConsumoMes1;

	private String dadosConsumoMes2;

	private String dadosConsumoMes3;

	private String dadosConsumoMes4;

	private String dadosConsumoMes5;

	private String dadosConsumoMes6;

	private String consumoFaturamento;

	private String consumoMedioDiario;

	private String leituraAnterior;

	private String leituraAtual;

	private String diasConsumo;

	private String quantidadeEconomiaConta;

	private String consumoEconomia;

	private String codigoAuxiliarString;

	private String mensagemConsumoString;

	private Collection colecaoContaLinhasDescricaoServicosTarifasTotalHelper;

	private String valorContaString;

	private String primeiraParte;

	private String segundaParte;

	private String terceiraParte;

	private String mesAnoFormatado;

	private String numeroIndiceTurbidez;

	private String numeroCloroResidual;

	private String representacaoNumericaCodBarraFormatada;

	private String representacaoNumericaCodBarraSemDigito;

	private String dataValidade;

	private String dataLeituraAnterior;

	private String dataLeituraAtual;

	private Short codigoRota;

	private Integer numeroSequencialRota;

	private Integer idImovelContaEnvio;

	private String consumoMedio;

	private String categoriaImovel;

	private String descricaoAnormalidadeConsumo;

	private String descricaoTipoConsumo;

	private String numeroHidrometro;

	private String nomeImovel;

	private String contaSemCodigoBarras;

	private Integer debitoCreditoSituacaoAtualConta;

	private String contaPaga;

	private Integer idRota;

	private Integer idOrigem;

	private Integer idFuncionario;

	private String nomeFuncionario;

	private Integer contaTipo;

	private Integer idConsumoTarifa;

	// ---------------------------------------------------------
	// utilizado no Emitir Segunda Via de Conta Tipo 2 (CAER)
	// Vivianne Sousa
	// 20/08/2007
	private String msgConta;

	private String msgLinha1Conta;

	private String msgLinha2Conta;

	private String msgLinha3Conta;

	private String msgLinha4Conta;

	private String msgLinha5Conta;

	private String valorMedioTurbidez;

	private String padraoTurbidez;

	private String valorMedioPh;

	private String valorMedioCor;

	private String valorMedioCloro;

	private String valorMedioFluor;

	private String valorMedioFerro;

	private String valorMedioColiformesTotais;

	private String valorMedioColiformesfecais;

	private String padraoPh;

	private String padraoCor;

	private String padraoCloro;

	private String padraoFluor;

	private String padraoFerro;

	private String padraoColiformesTotais;

	private String padraoColiformesfecais;

	private String enderecoLinha2;

	private String enderecoLinha3;

	private String datasVencimento;

	// ---------------------------------------------------------

	// Outras informações qualidade água
	private String numeroAmostrasMediaTurbidez;

	private String numeroAmostrasMediaCloro;

	private String numeroAmostrasMediaCor;

	private String numeroAmostrasMediaPH;

	private String numeroAmostrasMediaBacteriasHeterotroficas;

	private String numeroAmostrasMediaColiformesTermotolerantes;

	private String numeroAmostrasMediaColiformesTotais;

	// ---------------------------------------------------------
	// Alterado por: Yara Souza
	// Data : 13/05/2010
	private Integer idLogradouroEnderecoClienteEntrega;

	private String numeroImovelEnderecoClienteEntrega;

	private Short indicadorCodigoBarras; // Utilizado para exibição ou não na impressão;

	private Short indicadorDebitoAutomatico; // Utilizado para exibição ou não na impressão;

	private Short indicadorFaturaInformativa; // nao exibir o label de conta demonstrativa

	private String enderecoClienteEntrega;

	private Integer contaMotivoRetificacao;

	private Integer funcionario;

	private Short indicadorFaturamentoLigacaoAguaSituacao;

	private Short indicadorFaturamentoLigacaoEsgotoSituacao;

	private Date dataEmissaoConta;

	private Short indicadorPagamento;

	private Short indicadorCobrancaMulta;

	private String indicadorExibirAcrescimos;

	private String consumoMedido;

	private String descricaoContaMotivoRetificacao;

	private BigDecimal valorImpostoPisCofins;

	private Integer anoQuitacaoDebitoAnual;

	private String mensagemSubstitutaCodigoBarras;

	public EmitirContaHelper() {

	}

	// utilizado no Emitir Segunda Via de Conta Compesa
	// repositorioFaturamento.pesquisarConta
	// Vivianne Sousa
	public EmitirContaHelper(Integer idConta, String nomeCliente, Date dataVencimentoConta, int amReferencia, short digitoVerificadorConta,
								Integer codigoSetorComercialConta, Integer idQuadraConta, Short loteConta, Short subLoteConta,
								Integer consumoAgua, Integer consumoEsgoto, BigDecimal valorAgua, BigDecimal valorEsgoto,
								BigDecimal debitos, BigDecimal valorCreditos, BigDecimal valorImpostos, Date dataValidadeConta,
								Integer idImovel, Integer idLocalidade, Integer idGerenciaRegional, String nomeGerenciaRegional,
								Integer idLigacaoAguaSituacao, Integer idLigacaoEsgotoSituacao, Integer idImovelPerfil,
								Integer idSetorComercial, Integer idFaturamentoGrupo, Integer idEmpresa, String descricaoLocalidade,
								String descricaoLigacaoAguaSituacao, String descricaoLigacaoEsgotoSituacao, Integer idImovelContaEnvio,
								BigDecimal percentualEsgotoConta, String nomeImovel) {

		this.idConta = idConta;
		this.nomeCliente = nomeCliente;
		this.dataVencimentoConta = dataVencimentoConta;
		this.amReferencia = amReferencia;
		this.digitoVerificadorConta = digitoVerificadorConta;
		this.codigoSetorComercialConta = codigoSetorComercialConta;
		this.idQuadraConta = idQuadraConta;
		this.loteConta = loteConta;
		this.subLoteConta = subLoteConta;
		this.consumoAgua = consumoAgua;
		this.consumoEsgoto = consumoEsgoto;
		this.valorAgua = valorAgua;
		this.valorEsgoto = valorEsgoto;
		this.debitos = debitos;
		this.valorCreditos = valorCreditos;
		this.valorImpostos = valorImpostos;
		this.dataValidadeConta = dataValidadeConta;
		this.idImovel = idImovel;
		this.idLocalidade = idLocalidade;
		this.idGerenciaRegional = idGerenciaRegional;
		this.nomeGerenciaRegional = nomeGerenciaRegional;
		this.idLigacaoAguaSituacao = idLigacaoAguaSituacao;
		this.idLigacaoEsgotoSituacao = idLigacaoEsgotoSituacao;
		this.idImovelPerfil = idImovelPerfil;
		this.idSetorComercial = idSetorComercial;
		this.idFaturamentoGrupo = idFaturamentoGrupo;
		this.idEmpresa = idEmpresa;
		this.descricaoLocalidade = descricaoLocalidade;
		this.descricaoLigacaoAguaSituacao = descricaoLigacaoAguaSituacao;
		this.descricaoLigacaoEsgotoSituacao = descricaoLigacaoEsgotoSituacao;
		this.percentualEsgotoConta = percentualEsgotoConta;
		this.idImovelContaEnvio = idImovelContaEnvio;
		this.nomeImovel = nomeImovel;
		this.valorImpostoPisCofins = valorImpostoPisCofins;
	}

	// utilizado no Emitir Segunda Via de Conta Tipo 2 (CAER e CAERN)
	// repositorioFaturamento.pesquisarContaERota
	// Vivianne Sousa
	public EmitirContaHelper(Integer idConta, String nomeCliente, Date dataVencimentoConta, int amReferencia, short digitoVerificadorConta,
								Integer codigoSetorComercialConta, Integer idQuadraConta, Short loteConta, Short subLoteConta,
								Integer consumoAgua, Integer consumoEsgoto, BigDecimal valorAgua, BigDecimal valorEsgoto,
								BigDecimal debitos, BigDecimal valorCreditos, BigDecimal valorImpostos, Date dataValidadeConta,
								Integer idImovel, Integer idLocalidade, Integer idGerenciaRegional, String nomeGerenciaRegional,
								Integer idLigacaoAguaSituacao, Integer idLigacaoEsgotoSituacao, Integer idImovelPerfil,
								Integer idSetorComercial, Integer idFaturamentoGrupo, Integer idEmpresa, String descricaoLocalidade,
								String descricaoLigacaoAguaSituacao, String descricaoLigacaoEsgotoSituacao,
								BigDecimal percentualEsgotoConta, Short codigoRota, Integer numeroSequencialRota, String numeroHidrometro,
								Integer debitoCreditoSituacaoAtualConta, String nomeImovel) {

		this.idConta = idConta;
		this.nomeCliente = nomeCliente;
		this.dataVencimentoConta = dataVencimentoConta;
		this.amReferencia = amReferencia;
		this.digitoVerificadorConta = digitoVerificadorConta;
		this.codigoSetorComercialConta = codigoSetorComercialConta;
		this.idQuadraConta = idQuadraConta;
		this.loteConta = loteConta;
		this.subLoteConta = subLoteConta;
		this.consumoAgua = consumoAgua;
		this.consumoEsgoto = consumoEsgoto;
		this.valorAgua = valorAgua;
		this.valorEsgoto = valorEsgoto;
		this.debitos = debitos;
		this.valorCreditos = valorCreditos;
		this.valorImpostos = valorImpostos;
		this.dataValidadeConta = dataValidadeConta;
		this.idImovel = idImovel;
		this.idLocalidade = idLocalidade;
		this.idGerenciaRegional = idGerenciaRegional;
		this.nomeGerenciaRegional = nomeGerenciaRegional;
		this.idLigacaoAguaSituacao = idLigacaoAguaSituacao;
		this.idLigacaoEsgotoSituacao = idLigacaoEsgotoSituacao;
		this.idImovelPerfil = idImovelPerfil;
		this.idSetorComercial = idSetorComercial;
		this.idFaturamentoGrupo = idFaturamentoGrupo;
		this.idEmpresa = idEmpresa;
		this.descricaoLocalidade = descricaoLocalidade;
		this.descricaoLigacaoAguaSituacao = descricaoLigacaoAguaSituacao;
		this.descricaoLigacaoEsgotoSituacao = descricaoLigacaoEsgotoSituacao;
		this.percentualEsgotoConta = percentualEsgotoConta;
		this.codigoRota = codigoRota;
		this.numeroSequencialRota = numeroSequencialRota;
		this.numeroHidrometro = numeroHidrometro;
		this.debitoCreditoSituacaoAtualConta = debitoCreditoSituacaoAtualConta;
		this.nomeImovel = nomeImovel;
	}

	// repositorioFaturamento.pesquisarContasDebitoAutomatico
	// repositorioFaturamento.pesquisarContasNormais
	public EmitirContaHelper(Integer idConta, String nomeCliente, Date dataVencimentoConta, int amReferencia, short digitoVerificadorConta,
								Integer codigoSetorComercialConta, Integer idQuadraConta, Short loteConta, Short subLoteConta,
								Integer consumoAgua, Integer consumoEsgoto, BigDecimal valorAgua, BigDecimal valorEsgoto,
								BigDecimal debitos, BigDecimal valorCreditos, BigDecimal valorImpostos, Date dataValidadeConta,
								Integer idImovel, Integer idLocalidade, Integer idGerenciaRegional, String nomeGerenciaRegional,
								Integer idLigacaoAguaSituacao, Integer idLigacaoEsgotoSituacao, Integer idImovelPerfil,
								Integer idSetorComercial, Integer idFaturamentoGrupo, Integer idEmpresa, String descricaoLocalidade,
								String descricaoLigacaoAguaSituacao, String descricaoLigacaoEsgotoSituacao, BigDecimal percentualEsgotoConta) {

		this.idConta = idConta;
		this.nomeCliente = nomeCliente;
		this.dataVencimentoConta = dataVencimentoConta;
		this.amReferencia = amReferencia;
		this.digitoVerificadorConta = digitoVerificadorConta;
		this.codigoSetorComercialConta = codigoSetorComercialConta;
		this.idQuadraConta = idQuadraConta;
		this.loteConta = loteConta;
		this.subLoteConta = subLoteConta;
		this.consumoAgua = consumoAgua;
		this.consumoEsgoto = consumoEsgoto;
		this.valorAgua = valorAgua;
		this.valorEsgoto = valorEsgoto;
		this.debitos = debitos;
		this.valorCreditos = valorCreditos;
		this.valorImpostos = valorImpostos;
		this.dataValidadeConta = dataValidadeConta;
		this.idImovel = idImovel;
		this.idLocalidade = idLocalidade;
		this.idGerenciaRegional = idGerenciaRegional;
		this.nomeGerenciaRegional = nomeGerenciaRegional;
		this.idLigacaoAguaSituacao = idLigacaoAguaSituacao;
		this.idLigacaoEsgotoSituacao = idLigacaoEsgotoSituacao;
		this.idImovelPerfil = idImovelPerfil;
		this.idSetorComercial = idSetorComercial;
		this.idFaturamentoGrupo = idFaturamentoGrupo;
		this.idEmpresa = idEmpresa;
		this.descricaoLocalidade = descricaoLocalidade;
		this.descricaoLigacaoAguaSituacao = descricaoLigacaoAguaSituacao;
		this.descricaoLigacaoEsgotoSituacao = descricaoLigacaoEsgotoSituacao;
		this.percentualEsgotoConta = percentualEsgotoConta;
	}

	public EmitirContaHelper(Integer idConta, String nomeCliente, Date dataVencimentoConta, int amReferencia, short digitoVerificadorConta,
								Integer codigoSetorComercialConta, Integer idQuadraConta, Short loteConta, Short subLoteConta,
								Integer consumoAgua, Integer consumoEsgoto, BigDecimal valorAgua, BigDecimal valorEsgoto,
								BigDecimal debitos, BigDecimal valorCreditos, BigDecimal valorImpostos, Date dataValidadeConta,
								Integer idImovel, Integer idLocalidade, Integer idGerenciaRegional, String nomeGerenciaRegional,
								Integer idLigacaoAguaSituacao, Integer idLigacaoEsgotoSituacao, Integer idImovelPerfil,
								Integer idSetorComercial, Integer idFaturamentoGrupo, Integer idEmpresa, String descricaoLocalidade,
								String descricaoLigacaoAguaSituacao, String descricaoLigacaoEsgotoSituacao, Integer idImovelContaEnvio,
								BigDecimal percentualEsgotoConta, String nomeImovel, Integer debitoCreditoSituacaoAtualConta,
								Integer contaMotivoRetificacao, Integer funcionario, String descricaoContaMotivoRetificacao) {

		this.idConta = idConta;
		this.nomeCliente = nomeCliente;
		this.dataVencimentoConta = dataVencimentoConta;
		this.amReferencia = amReferencia;
		this.digitoVerificadorConta = digitoVerificadorConta;
		this.codigoSetorComercialConta = codigoSetorComercialConta;
		this.idQuadraConta = idQuadraConta;
		this.loteConta = loteConta;
		this.subLoteConta = subLoteConta;
		this.consumoAgua = consumoAgua;
		this.consumoEsgoto = consumoEsgoto;
		this.valorAgua = valorAgua;
		this.valorEsgoto = valorEsgoto;
		this.debitos = debitos;
		this.valorCreditos = valorCreditos;
		this.valorImpostos = valorImpostos;
		this.dataValidadeConta = dataValidadeConta;
		this.idImovel = idImovel;
		this.idLocalidade = idLocalidade;
		this.idGerenciaRegional = idGerenciaRegional;
		this.nomeGerenciaRegional = nomeGerenciaRegional;
		this.idLigacaoAguaSituacao = idLigacaoAguaSituacao;
		this.idLigacaoEsgotoSituacao = idLigacaoEsgotoSituacao;
		this.idImovelPerfil = idImovelPerfil;
		this.idSetorComercial = idSetorComercial;
		this.idFaturamentoGrupo = idFaturamentoGrupo;
		this.idEmpresa = idEmpresa;
		this.descricaoLocalidade = descricaoLocalidade;
		this.descricaoLigacaoAguaSituacao = descricaoLigacaoAguaSituacao;
		this.descricaoLigacaoEsgotoSituacao = descricaoLigacaoEsgotoSituacao;
		this.percentualEsgotoConta = percentualEsgotoConta;
		this.idImovelContaEnvio = idImovelContaEnvio;
		this.nomeImovel = nomeImovel;
		this.debitoCreditoSituacaoAtualConta = debitoCreditoSituacaoAtualConta;
		this.contaMotivoRetificacao = contaMotivoRetificacao;
		this.funcionario = funcionario;
		this.descricaoContaMotivoRetificacao = descricaoContaMotivoRetificacao;
	}

	public EmitirContaHelper(Integer idConta, String nomeCliente, Date dataVencimentoConta, int amReferencia, short digitoVerificadorConta,
								Integer codigoSetorComercialConta, Integer idQuadraConta, Short loteConta, Short subLoteConta,
								Integer consumoAgua, Integer consumoEsgoto, BigDecimal valorAgua, BigDecimal valorEsgoto,
								BigDecimal debitos, BigDecimal valorCreditos, BigDecimal valorImpostos, Date dataValidadeConta,
								Integer idImovel, Integer idLocalidade, Integer idGerenciaRegional, String nomeGerenciaRegional,
								Integer idLigacaoAguaSituacao, Integer idLigacaoEsgotoSituacao, Integer idImovelPerfil,
								Integer idSetorComercial, Integer idFaturamentoGrupo, Integer idEmpresa, String descricaoLocalidade,
								String descricaoLigacaoAguaSituacao, String descricaoLigacaoEsgotoSituacao, Integer idImovelContaEnvio,
								BigDecimal percentualEsgotoConta, String nomeImovel, Integer debitoCreditoSituacaoAtualConta,
								Integer contaMotivoRetificacao, Integer funcionario, Date dataEmissaoConta) {

		this.idConta = idConta;
		this.nomeCliente = nomeCliente;
		this.dataVencimentoConta = dataVencimentoConta;
		this.amReferencia = amReferencia;
		this.digitoVerificadorConta = digitoVerificadorConta;
		this.codigoSetorComercialConta = codigoSetorComercialConta;
		this.idQuadraConta = idQuadraConta;
		this.loteConta = loteConta;
		this.subLoteConta = subLoteConta;
		this.consumoAgua = consumoAgua;
		this.consumoEsgoto = consumoEsgoto;
		this.valorAgua = valorAgua;
		this.valorEsgoto = valorEsgoto;
		this.debitos = debitos;
		this.valorCreditos = valorCreditos;
		this.valorImpostos = valorImpostos;
		this.dataValidadeConta = dataValidadeConta;
		this.idImovel = idImovel;
		this.idLocalidade = idLocalidade;
		this.idGerenciaRegional = idGerenciaRegional;
		this.nomeGerenciaRegional = nomeGerenciaRegional;
		this.idLigacaoAguaSituacao = idLigacaoAguaSituacao;
		this.idLigacaoEsgotoSituacao = idLigacaoEsgotoSituacao;
		this.idImovelPerfil = idImovelPerfil;
		this.idSetorComercial = idSetorComercial;
		this.idFaturamentoGrupo = idFaturamentoGrupo;
		this.idEmpresa = idEmpresa;
		this.descricaoLocalidade = descricaoLocalidade;
		this.descricaoLigacaoAguaSituacao = descricaoLigacaoAguaSituacao;
		this.descricaoLigacaoEsgotoSituacao = descricaoLigacaoEsgotoSituacao;
		this.percentualEsgotoConta = percentualEsgotoConta;
		this.idImovelContaEnvio = idImovelContaEnvio;
		this.nomeImovel = nomeImovel;
		this.debitoCreditoSituacaoAtualConta = debitoCreditoSituacaoAtualConta;
		this.contaMotivoRetificacao = contaMotivoRetificacao;
		this.funcionario = funcionario;
		this.dataEmissaoConta = dataEmissaoConta;

	}

	public EmitirContaHelper(Integer idConta, String nomeCliente, Date dataVencimentoConta, int amReferencia, short digitoVerificadorConta,
								Integer codigoSetorComercialConta, Integer idQuadraConta, Short loteConta, Short subLoteConta,
								Integer consumoAgua, Integer consumoEsgoto, BigDecimal valorAgua, BigDecimal valorEsgoto,
								BigDecimal debitos, BigDecimal valorCreditos, BigDecimal valorImpostos, Date dataValidadeConta,
								Integer idImovel, Integer idLocalidade, Integer idGerenciaRegional, String nomeGerenciaRegional,
								Integer idLigacaoAguaSituacao, Integer idLigacaoEsgotoSituacao, Integer idImovelPerfil,
								Integer idSetorComercial, Integer idFaturamentoGrupo, Integer idEmpresa, String descricaoLocalidade,
								String descricaoLigacaoAguaSituacao, String descricaoLigacaoEsgotoSituacao, Integer idImovelContaEnvio,
								BigDecimal percentualEsgotoConta, String nomeImovel, Integer debitoCreditoSituacaoAtualConta,
								Integer contaMotivoRetificacao, Integer funcionario, Date dataEmissaoConta, Short indicadorPagamento,
								Short indicadorCobrancaMulta, String descricaoContaMotivoRetificacao) {

		this.idConta = idConta;
		this.nomeCliente = nomeCliente;
		this.dataVencimentoConta = dataVencimentoConta;
		this.amReferencia = amReferencia;
		this.digitoVerificadorConta = digitoVerificadorConta;
		this.codigoSetorComercialConta = codigoSetorComercialConta;
		this.idQuadraConta = idQuadraConta;
		this.loteConta = loteConta;
		this.subLoteConta = subLoteConta;
		this.consumoAgua = consumoAgua;
		this.consumoEsgoto = consumoEsgoto;
		this.valorAgua = valorAgua;
		this.valorEsgoto = valorEsgoto;
		this.debitos = debitos;
		this.valorCreditos = valorCreditos;
		this.valorImpostos = valorImpostos;
		this.dataValidadeConta = dataValidadeConta;
		this.idImovel = idImovel;
		this.idLocalidade = idLocalidade;
		this.idGerenciaRegional = idGerenciaRegional;
		this.nomeGerenciaRegional = nomeGerenciaRegional;
		this.idLigacaoAguaSituacao = idLigacaoAguaSituacao;
		this.idLigacaoEsgotoSituacao = idLigacaoEsgotoSituacao;
		this.idImovelPerfil = idImovelPerfil;
		this.idSetorComercial = idSetorComercial;
		this.idFaturamentoGrupo = idFaturamentoGrupo;
		this.idEmpresa = idEmpresa;
		this.descricaoLocalidade = descricaoLocalidade;
		this.descricaoLigacaoAguaSituacao = descricaoLigacaoAguaSituacao;
		this.descricaoLigacaoEsgotoSituacao = descricaoLigacaoEsgotoSituacao;
		this.percentualEsgotoConta = percentualEsgotoConta;
		this.idImovelContaEnvio = idImovelContaEnvio;
		this.nomeImovel = nomeImovel;
		this.debitoCreditoSituacaoAtualConta = debitoCreditoSituacaoAtualConta;
		this.contaMotivoRetificacao = contaMotivoRetificacao;
		this.funcionario = funcionario;
		this.dataEmissaoConta = dataEmissaoConta;
		this.indicadorPagamento = indicadorPagamento;
		this.indicadorCobrancaMulta = indicadorCobrancaMulta;
		this.descricaoContaMotivoRetificacao = descricaoContaMotivoRetificacao;
	}

	public EmitirContaHelper(Integer idConta, String nomeCliente, Date dataVencimentoConta, int amReferencia, short digitoVerificadorConta,
								Integer codigoSetorComercialConta, Integer idQuadraConta, Short loteConta, Short subLoteConta,
								Integer consumoAgua, Integer consumoEsgoto, BigDecimal valorAgua, BigDecimal valorEsgoto,
								BigDecimal debitos, BigDecimal valorCreditos, BigDecimal valorImpostos, Date dataValidadeConta,
								Integer idImovel, Integer idLocalidade, Integer idGerenciaRegional, String nomeGerenciaRegional,
								Integer idLigacaoAguaSituacao, Integer idLigacaoEsgotoSituacao, Integer idImovelPerfil,
								Integer idSetorComercial, Integer idFaturamentoGrupo, Integer idEmpresa, String descricaoLocalidade,
								String descricaoLigacaoAguaSituacao, String descricaoLigacaoEsgotoSituacao, Integer idImovelContaEnvio,
								BigDecimal percentualEsgotoConta, String nomeImovel, Integer debitoCreditoSituacaoAtualConta,
								Integer contaMotivoRetificacao, Integer funcionario, Date dataEmissaoConta, Short indicadorPagamento,
								Short indicadorCobrancaMulta, Integer idConsumoTarifa, Short indicadorFaturamentoLigacaoAguaSituacao,
								Short indicadorFaturamentoLigacaoEsgotoSituacao) {

		this.idConta = idConta;
		this.nomeCliente = nomeCliente;
		this.dataVencimentoConta = dataVencimentoConta;
		this.amReferencia = amReferencia;
		this.digitoVerificadorConta = digitoVerificadorConta;
		this.codigoSetorComercialConta = codigoSetorComercialConta;
		this.idQuadraConta = idQuadraConta;
		this.loteConta = loteConta;
		this.subLoteConta = subLoteConta;
		this.consumoAgua = consumoAgua;
		this.consumoEsgoto = consumoEsgoto;
		this.valorAgua = valorAgua;
		this.valorEsgoto = valorEsgoto;
		this.debitos = debitos;
		this.valorCreditos = valorCreditos;
		this.valorImpostos = valorImpostos;
		this.dataValidadeConta = dataValidadeConta;
		this.idImovel = idImovel;
		this.idLocalidade = idLocalidade;
		this.idGerenciaRegional = idGerenciaRegional;
		this.nomeGerenciaRegional = nomeGerenciaRegional;
		this.idLigacaoAguaSituacao = idLigacaoAguaSituacao;
		this.idLigacaoEsgotoSituacao = idLigacaoEsgotoSituacao;
		this.idImovelPerfil = idImovelPerfil;
		this.idSetorComercial = idSetorComercial;
		this.idFaturamentoGrupo = idFaturamentoGrupo;
		this.idEmpresa = idEmpresa;
		this.descricaoLocalidade = descricaoLocalidade;
		this.descricaoLigacaoAguaSituacao = descricaoLigacaoAguaSituacao;
		this.descricaoLigacaoEsgotoSituacao = descricaoLigacaoEsgotoSituacao;
		this.percentualEsgotoConta = percentualEsgotoConta;
		this.idImovelContaEnvio = idImovelContaEnvio;
		this.nomeImovel = nomeImovel;
		this.debitoCreditoSituacaoAtualConta = debitoCreditoSituacaoAtualConta;
		this.contaMotivoRetificacao = contaMotivoRetificacao;
		this.funcionario = funcionario;
		this.dataEmissaoConta = dataEmissaoConta;
		this.indicadorPagamento = indicadorPagamento;
		this.indicadorCobrancaMulta = indicadorCobrancaMulta;
		this.idConsumoTarifa = idConsumoTarifa;
		this.indicadorFaturamentoLigacaoAguaSituacao = indicadorFaturamentoLigacaoAguaSituacao;
		this.indicadorFaturamentoLigacaoEsgotoSituacao = indicadorFaturamentoLigacaoEsgotoSituacao;
	}

	public EmitirContaHelper(Integer idConta, int amReferencia, Integer consumoAgua, Integer consumoEsgoto, BigDecimal valorAgua,
								BigDecimal valorEsgoto, BigDecimal debitos, BigDecimal valorCreditos, BigDecimal valorImpostos,
								Integer idImovel, Integer idLigacaoAguaSituacao, Integer idLigacaoEsgotoSituacao,
								Short indicadorFaturamentoLigacaoAguaSituacao, Short indicadorFaturamentoLigacaoEsgotoSituacao,
								Integer idConsumoTarifa, BigDecimal percentualEsgoto) {

		this.idConta = idConta;
		this.amReferencia = amReferencia;
		this.consumoAgua = consumoAgua;
		this.consumoEsgoto = consumoEsgoto;
		this.valorAgua = valorAgua;
		this.valorEsgoto = valorEsgoto;
		this.debitos = debitos;
		this.valorCreditos = valorCreditos;
		this.valorImpostos = valorImpostos;
		this.idImovel = idImovel;
		this.idLigacaoAguaSituacao = idLigacaoAguaSituacao;
		this.idLigacaoEsgotoSituacao = idLigacaoEsgotoSituacao;
		this.indicadorFaturamentoLigacaoAguaSituacao = indicadorFaturamentoLigacaoAguaSituacao;
		this.indicadorFaturamentoLigacaoEsgotoSituacao = indicadorFaturamentoLigacaoEsgotoSituacao;
		this.idConsumoTarifa = idConsumoTarifa;
		this.percentualEsgotoConta = percentualEsgoto;

	}

	public EmitirContaHelper(Integer idConta, String nomeCliente, String cpfCliente, String cnpjCliente, Date dataVencimentoConta, int amReferencia, short digitoVerificadorConta,
								Integer codigoSetorComercialConta, Integer idQuadraConta, Short loteConta, Short subLoteConta,
								Integer consumoAgua, Integer consumoEsgoto, BigDecimal valorAgua, BigDecimal valorEsgoto,
								BigDecimal debitos, BigDecimal valorCreditos, BigDecimal valorImpostos, Date dataValidadeConta,
								Integer idImovel, Integer idLocalidade, Integer idGerenciaRegional, String nomeGerenciaRegional,
								Integer idLigacaoAguaSituacao, Integer idLigacaoEsgotoSituacao, Integer idImovelPerfil,
								Integer idSetorComercial, Integer idFaturamentoGrupo, Integer idEmpresa, String descricaoLocalidade,
								String descricaoLigacaoAguaSituacao, String descricaoLigacaoEsgotoSituacao, Integer idImovelContaEnvio,
								BigDecimal percentualEsgotoConta, String nomeImovel, Integer debitoCreditoSituacaoAtualConta,
								Integer contaMotivoRetificacao, Integer funcionario, Date dataEmissaoConta, Short indicadorPagamento,
								Short indicadorCobrancaMulta, String descricaoContaMotivoRetificacao) {

		this.idConta = idConta;
		this.nomeCliente = nomeCliente;
		
		if(cpfCliente != null){
			this.setTipoDocCliente("CPF");
			this.setCpfCnpjCliente(cpfCliente);

		}else if(cnpjCliente != null){
			this.setTipoDocCliente("CNPJ");
			this.setCpfCnpjCliente(cnpjCliente);
		}
		
		this.dataVencimentoConta = dataVencimentoConta;
		this.amReferencia = amReferencia;
		this.digitoVerificadorConta = digitoVerificadorConta;
		this.codigoSetorComercialConta = codigoSetorComercialConta;
		this.idQuadraConta = idQuadraConta;
		this.loteConta = loteConta;
		this.subLoteConta = subLoteConta;
		this.consumoAgua = consumoAgua;
		this.consumoEsgoto = consumoEsgoto;
		this.valorAgua = valorAgua;
		this.valorEsgoto = valorEsgoto;
		this.debitos = debitos;
		this.valorCreditos = valorCreditos;
		this.valorImpostos = valorImpostos;
		this.dataValidadeConta = dataValidadeConta;
		this.idImovel = idImovel;
		this.idLocalidade = idLocalidade;
		this.idGerenciaRegional = idGerenciaRegional;
		this.nomeGerenciaRegional = nomeGerenciaRegional;
		this.idLigacaoAguaSituacao = idLigacaoAguaSituacao;
		this.idLigacaoEsgotoSituacao = idLigacaoEsgotoSituacao;
		this.idImovelPerfil = idImovelPerfil;
		this.idSetorComercial = idSetorComercial;
		this.idFaturamentoGrupo = idFaturamentoGrupo;
		this.idEmpresa = idEmpresa;
		this.descricaoLocalidade = descricaoLocalidade;
		this.descricaoLigacaoAguaSituacao = descricaoLigacaoAguaSituacao;
		this.descricaoLigacaoEsgotoSituacao = descricaoLigacaoEsgotoSituacao;
		this.percentualEsgotoConta = percentualEsgotoConta;
		this.idImovelContaEnvio = idImovelContaEnvio;
		this.nomeImovel = nomeImovel;
		this.debitoCreditoSituacaoAtualConta = debitoCreditoSituacaoAtualConta;
		this.contaMotivoRetificacao = contaMotivoRetificacao;
		this.funcionario = funcionario;
		this.dataEmissaoConta = dataEmissaoConta;
		this.indicadorPagamento = indicadorPagamento;
		this.indicadorCobrancaMulta = indicadorCobrancaMulta;
		this.descricaoContaMotivoRetificacao = descricaoContaMotivoRetificacao;
	}

	/**
	 * @return the anoQuitacaoDebitoAnual
	 */
	public Integer getAnoQuitacaoDebitoAnual(){

		return anoQuitacaoDebitoAnual;
	}

	/**
	 * @param anoQuitacaoDebitoAnual
	 *            the anoQuitacaoDebitoAnual to set
	 */
	public void setAnoQuitacaoDebitoAnual(Integer anoQuitacaoDebitoAnual){

		this.anoQuitacaoDebitoAnual = anoQuitacaoDebitoAnual;
	}

	public Integer getIdOrigem(){

		return idOrigem;
	}

	public void setIdOrigem(Integer idOrigem){

		this.idOrigem = idOrigem;
	}

	public Integer getIdRota(){

		return idRota;
	}

	public void setIdRota(Integer idRota){

		this.idRota = idRota;
	}

	public int getAmReferencia(){

		return amReferencia;
	}

	public void setAmReferencia(int amReferencia){

		this.amReferencia = amReferencia;
	}

	public Integer getCodigoSetorComercialConta(){

		return codigoSetorComercialConta;
	}

	public void setCodigoSetorComercialConta(Integer codigoSetorComercialConta){

		this.codigoSetorComercialConta = codigoSetorComercialConta;
	}

	public Integer getConsumoAgua(){

		return consumoAgua;
	}

	public void setConsumoAgua(Integer consumoAgua){

		this.consumoAgua = consumoAgua;
	}

	public Integer getConsumoEsgoto(){

		return consumoEsgoto;
	}

	public void setConsumoEsgoto(Integer consumoEsgoto){

		this.consumoEsgoto = consumoEsgoto;
	}

	public Date getDataValidadeConta(){

		return dataValidadeConta;
	}

	public void setDataValidadeConta(Date dataValidadeConta){

		this.dataValidadeConta = dataValidadeConta;
	}

	public Date getDataVencimentoConta(){

		return dataVencimentoConta;
	}

	public void setDataVencimentoConta(Date dataVencimentoConta){

		this.dataVencimentoConta = dataVencimentoConta;
	}

	public BigDecimal getDebitos(){

		return debitos;
	}

	public void setDebitos(BigDecimal debitos){

		this.debitos = debitos;
	}

	public short getDigitoVerificadorConta(){

		return digitoVerificadorConta;
	}

	public void setDigitoVerificadorConta(short digitoVerificadorConta){

		this.digitoVerificadorConta = digitoVerificadorConta;
	}

	public Integer getIdEmpresa(){

		return idEmpresa;
	}

	public void setIdEmpresa(Integer idEmpresa){

		this.idEmpresa = idEmpresa;
	}

	public Integer getIdFaturamentoGrupo(){

		return idFaturamentoGrupo;
	}

	public void setIdFaturamentoGrupo(Integer idFaturamentoGrupo){

		this.idFaturamentoGrupo = idFaturamentoGrupo;
	}

	public Integer getIdGerenciaRegional(){

		return idGerenciaRegional;
	}

	public void setIdGerenciaRegional(Integer idGerenciaRegional){

		this.idGerenciaRegional = idGerenciaRegional;
	}

	public Integer getIdImovel(){

		return idImovel;
	}

	public void setIdImovel(Integer idImovel){

		this.idImovel = idImovel;
	}

	public Integer getIdImovelPerfil(){

		return idImovelPerfil;
	}

	public void setIdImovelPerfil(Integer idImovelPerfil){

		this.idImovelPerfil = idImovelPerfil;
	}

	public Integer getIdLigacaoAguaSituacao(){

		return idLigacaoAguaSituacao;
	}

	public void setIdLigacaoAguaSituacao(Integer idLigacaoAguaSituacao){

		this.idLigacaoAguaSituacao = idLigacaoAguaSituacao;
	}

	public Integer getIdLigacaoEsgotoSituacao(){

		return idLigacaoEsgotoSituacao;
	}

	public void setIdLigacaoEsgotoSituacao(Integer idLigacaoEsgotoSituacao){

		this.idLigacaoEsgotoSituacao = idLigacaoEsgotoSituacao;
	}

	public Integer getIdLocalidade(){

		return idLocalidade;
	}

	public void setIdLocalidade(Integer idLocalidade){

		this.idLocalidade = idLocalidade;
	}

	public Integer getIdQuadraConta(){

		return idQuadraConta;
	}

	public void setIdQuadraConta(Integer idQuadraConta){

		this.idQuadraConta = idQuadraConta;
	}

	public Integer getIdSetorComercial(){

		return idSetorComercial;
	}

	public void setIdSetorComercial(Integer idSetorComercial){

		this.idSetorComercial = idSetorComercial;
	}

	public Short getLoteConta(){

		return loteConta;
	}

	public void setLoteConta(Short loteConta){

		this.loteConta = loteConta;
	}

	public String getNomeCliente(){

		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente){

		this.nomeCliente = nomeCliente;
	}

	public String getNomeGerenciaRegional(){

		return nomeGerenciaRegional;
	}

	public void setNomeGerenciaRegional(String nomeGerenciaRegional){

		this.nomeGerenciaRegional = nomeGerenciaRegional;
	}

	public Short getSubLoteConta(){

		return subLoteConta;
	}

	public void setSubLoteConta(Short subLoteConta){

		this.subLoteConta = subLoteConta;
	}

	public BigDecimal getValorAgua(){

		return valorAgua;
	}

	public void setValorAgua(BigDecimal valorAgua){

		this.valorAgua = valorAgua;
	}

	public BigDecimal getValorCreditos(){

		return valorCreditos;
	}

	public void setValorCreditos(BigDecimal valorCreditos){

		this.valorCreditos = valorCreditos;
	}

	public BigDecimal getValorEsgoto(){

		return valorEsgoto;
	}

	public void setValorEsgoto(BigDecimal valorEsgoto){

		this.valorEsgoto = valorEsgoto;
	}

	public BigDecimal getValorImpostos(){

		return valorImpostos;
	}

	public void setValorImpostos(BigDecimal valorImpostos){

		this.valorImpostos = valorImpostos;
	}

	public Integer getIdConta(){

		return idConta;
	}

	public void setIdConta(Integer idConta){

		this.idConta = idConta;
	}

	public String getDescricaoLocalidade(){

		return descricaoLocalidade;
	}

	public void setDescricaoLocalidade(String descricaoLocalidade){

		this.descricaoLocalidade = descricaoLocalidade;
	}

	public String getDescricaoLigacaoAguaSituacao(){

		return descricaoLigacaoAguaSituacao;
	}

	public void setDescricaoLigacaoAguaSituacao(String descricaoLigacaoAguaSituacao){

		this.descricaoLigacaoAguaSituacao = descricaoLigacaoAguaSituacao;
	}

	public String getDescricaoLigacaoEsgotoSituacao(){

		return descricaoLigacaoEsgotoSituacao;
	}

	public void setDescricaoLigacaoEsgotoSituacao(String descricaoLigacaoEsgotoSituacao){

		this.descricaoLigacaoEsgotoSituacao = descricaoLigacaoEsgotoSituacao;
	}

	public BigDecimal getPercentualEsgotoConta(){

		return percentualEsgotoConta;
	}

	public void setPercentualEsgotoConta(BigDecimal percentualEsgotoConta){

		this.percentualEsgotoConta = percentualEsgotoConta;
	}

	public String getMatriculaImovelFormatada(){

		String matriculaImovelFormatada = "";

		// Caso a matrícula tenha a quantidade de dígitos menor do que a cadastrada no parâmetro
		// P_NUMERO_DIGITOS_MATRICULA_IMOVEL adiciona zeros a esquerda do número
		matriculaImovelFormatada = Util.retornaMatriculaImovelFormatadaParametrizada(getIdImovel());



		return matriculaImovelFormatada;
	}

	public String getFatura(){

		// Mês/Ano referência da conta digito verificador
		String mesAnoReferencia = Util.formatarAnoMesParaMesAno(getAmReferencia());
		// Dígito verificador da conta
		String digitoVerificador = "" + getDigitoVerificadorConta();
		String fatura = mesAnoReferencia + "-" + digitoVerificador;
		return fatura;
	}

	public String getEnderecoImovel(){

		return enderecoImovel;
	}

	public void setEnderecoImovel(String enderecoImovel){

		this.enderecoImovel = enderecoImovel;
	}

	public String getInscricaoImovel(){

		return inscricaoImovel;
	}

	public void setInscricaoImovel(String inscricaoImovel){

		this.inscricaoImovel = inscricaoImovel;
	}

	public String getEnderecoClienteResponsavel(){

		return enderecoClienteResponsavel;
	}

	public void setEnderecoClienteResponsavel(String enderecoClienteResponsavel){

		this.enderecoClienteResponsavel = enderecoClienteResponsavel;
	}

	public String getIdClienteResponsavel(){

		return idClienteResponsavel;
	}

	public void setIdClienteResponsavel(String idClienteResponsavel){

		this.idClienteResponsavel = idClienteResponsavel;
	}

	public String getDadosConsumoMes1(){

		return dadosConsumoMes1;
	}

	public void setDadosConsumoMes1(String dadosConsumoMes1){

		this.dadosConsumoMes1 = dadosConsumoMes1;
	}

	public String getDadosConsumoMes2(){

		return dadosConsumoMes2;
	}

	public void setDadosConsumoMes2(String dadosConsumoMes2){

		this.dadosConsumoMes2 = dadosConsumoMes2;
	}

	public String getDadosConsumoMes3(){

		return dadosConsumoMes3;
	}

	public void setDadosConsumoMes3(String dadosConsumoMes3){

		this.dadosConsumoMes3 = dadosConsumoMes3;
	}

	public String getDadosConsumoMes4(){

		return dadosConsumoMes4;
	}

	public void setDadosConsumoMes4(String dadosConsumoMes4){

		this.dadosConsumoMes4 = dadosConsumoMes4;
	}

	public String getDadosConsumoMes5(){

		return dadosConsumoMes5;
	}

	public void setDadosConsumoMes5(String dadosConsumoMes5){

		this.dadosConsumoMes5 = dadosConsumoMes5;
	}

	public String getDadosConsumoMes6(){

		return dadosConsumoMes6;
	}

	public void setDadosConsumoMes6(String dadosConsumoMes6){

		this.dadosConsumoMes6 = dadosConsumoMes6;
	}

	public String getConsumoFaturamento(){

		return consumoFaturamento;
	}

	public void setConsumoFaturamento(String consumoFaturamento){

		this.consumoFaturamento = consumoFaturamento;
	}

	public String getConsumoMedioDiario(){

		return consumoMedioDiario;
	}

	public void setConsumoMedioDiario(String consumoMedioDiario){

		this.consumoMedioDiario = consumoMedioDiario;
	}

	public String getDiasConsumo(){

		return diasConsumo;
	}

	public void setDiasConsumo(String diasConsumo){

		this.diasConsumo = diasConsumo;
	}

	public String getLeituraAnterior(){

		return leituraAnterior;
	}

	public void setLeituraAnterior(String leituraAnterior){

		this.leituraAnterior = leituraAnterior;
	}

	public String getLeituraAtual(){

		return leituraAtual;
	}

	public void setLeituraAtual(String leituraAtual){

		this.leituraAtual = leituraAtual;
	}

	public String getDescricaoAnormalidadeConsumo(){

		return descricaoAnormalidadeConsumo;
	}

	public void setDescricaoAnormalidadeConsumo(String descricaoAnormalidadeConsumo){

		this.descricaoAnormalidadeConsumo = descricaoAnormalidadeConsumo;
	}

	public String getDescricaoTipoConsumo(){

		return descricaoTipoConsumo;
	}

	public void setDescricaoTipoConsumo(String descricaoTipoConsumo){

		this.descricaoTipoConsumo = descricaoTipoConsumo;
	}

	public String getCodigoAuxiliarString(){

		return codigoAuxiliarString;
	}

	public void setCodigoAuxiliarString(String codigoAuxiliarString){

		this.codigoAuxiliarString = codigoAuxiliarString;
	}

	public String getConsumoEconomia(){

		return consumoEconomia;
	}

	public void setConsumoEconomia(String consumoEconomia){

		this.consumoEconomia = consumoEconomia;
	}

	public String getDataValidade(){

		return dataValidade;
	}

	public void setDataValidade(String dataValidade){

		this.dataValidade = dataValidade;
	}

	public Collection getColecaoContaLinhasDescricaoServicosTarifasTotalHelper(){

		return colecaoContaLinhasDescricaoServicosTarifasTotalHelper;
	}

	public void setColecaoContaLinhasDescricaoServicosTarifasTotalHelper(Collection colecaoContaLinhasDescricaoServicosTarifasTotalHelper){

		this.colecaoContaLinhasDescricaoServicosTarifasTotalHelper = colecaoContaLinhasDescricaoServicosTarifasTotalHelper;
	}

	public String getMensagemConsumoString(){

		return mensagemConsumoString;
	}

	public void setMensagemConsumoString(String mensagemConsumoString){

		this.mensagemConsumoString = mensagemConsumoString;
	}

	public String getMesAnoFormatado(){

		return mesAnoFormatado;
	}

	public void setMesAnoFormatado(String mesAnoFormatado){

		this.mesAnoFormatado = mesAnoFormatado;
	}

	public String getNumeroCloroResidual(){

		return numeroCloroResidual;
	}

	public void setNumeroCloroResidual(String numeroCloroResidual){

		this.numeroCloroResidual = numeroCloroResidual;
	}

	public String getNumeroIndiceTurbidez(){

		return numeroIndiceTurbidez;
	}

	public void setNumeroIndiceTurbidez(String numeroIndiceTurbidez){

		this.numeroIndiceTurbidez = numeroIndiceTurbidez;
	}

	public String getPrimeiraParte(){

		return primeiraParte;
	}

	public void setPrimeiraParte(String primeiraParte){

		this.primeiraParte = primeiraParte;
	}

	public String getQuantidadeEconomiaConta(){

		return quantidadeEconomiaConta;
	}

	public void setQuantidadeEconomiaConta(String quantidadeEconomiaConta){

		this.quantidadeEconomiaConta = quantidadeEconomiaConta;
	}

	public String getRepresentacaoNumericaCodBarraFormatada(){

		return representacaoNumericaCodBarraFormatada;
	}

	public void setRepresentacaoNumericaCodBarraFormatada(String representacaoNumericaCodBarraFormatada){

		this.representacaoNumericaCodBarraFormatada = representacaoNumericaCodBarraFormatada;
	}

	public String getRepresentacaoNumericaCodBarraSemDigito(){

		return representacaoNumericaCodBarraSemDigito;
	}

	public void setRepresentacaoNumericaCodBarraSemDigito(String representacaoNumericaCodBarraSemDigito){

		this.representacaoNumericaCodBarraSemDigito = representacaoNumericaCodBarraSemDigito;
	}

	public String getSegundaParte(){

		return segundaParte;
	}

	public void setSegundaParte(String segundaParte){

		this.segundaParte = segundaParte;
	}

	public String getTerceiraParte(){

		return terceiraParte;
	}

	public void setTerceiraParte(String terceiraParte){

		this.terceiraParte = terceiraParte;
	}

	public String getValorContaString(){

		return valorContaString;
	}

	public void setValorContaString(String valorContaString){

		this.valorContaString = valorContaString;
	}

	public String getDataLeituraAnterior(){

		return dataLeituraAnterior;
	}

	public void setDataLeituraAnterior(String dataLeituraAnterior){

		this.dataLeituraAnterior = dataLeituraAnterior;
	}

	public String getDataLeituraAtual(){

		return dataLeituraAtual;
	}

	public void setDataLeituraAtual(String dataLeituraAtual){

		this.dataLeituraAtual = dataLeituraAtual;
	}

	public Integer getIdCliente(){

		return idCliente;
	}

	public void setIdCliente(Integer idCliente){

		this.idCliente = idCliente;
	}

	public Short getCodigoRota(){

		return codigoRota;
	}

	public void setCodigoRota(Short codigoRota){

		this.codigoRota = codigoRota;
	}

	public Integer getNumeroSequencialRota(){

		return numeroSequencialRota;
	}

	public void setNumeroSequencialRota(Integer numeroSequencialRota){

		this.numeroSequencialRota = numeroSequencialRota;
	}

	public String getRotaEntrega(){

		String rotaEntrega = "";

		if(getCodigoRota() != null){
			rotaEntrega = Util.adicionarZerosEsquedaNumero(2, getCodigoRota().toString());
		}

		if(getNumeroSequencialRota() != null){
			rotaEntrega = rotaEntrega + "." + Util.adicionarZerosEsquedaNumero(4, getNumeroSequencialRota().toString());
		}

		return rotaEntrega;
	}

	public Integer getIdImovelContaEnvio(){

		return idImovelContaEnvio;
	}

	public void setIdImovelContaEnvio(Integer idImovelContaEnvio){

		this.idImovelContaEnvio = idImovelContaEnvio;
	}

	public String getNomeImovel(){

		return nomeImovel;
	}

	public void setNomeImovel(String nomeImovel){

		this.nomeImovel = nomeImovel;
	}

	// utilizado no tipo de conta 2
	// public String getDatasVencimento(){
	// String datasVencimento = "";
	//		
	// Integer mesAnoReferencia = getAmReferencia();
	// mesAnoReferencia = Util.somaUmMesAnoMesReferencia(mesAnoReferencia);
	//		
	// String mesAnoFormatado = Util.formatarAnoMesParaMesAno(mesAnoReferencia);
	//		
	// datasVencimento = "04/" + mesAnoFormatado;
	// datasVencimento = datasVencimento + " - " + "05/" + mesAnoFormatado;
	// datasVencimento = datasVencimento + " - " + "08/" + mesAnoFormatado;
	// datasVencimento = datasVencimento + " - " + "10/" + mesAnoFormatado;
	// datasVencimento = datasVencimento + " - " + "12/" + mesAnoFormatado;
	// datasVencimento = datasVencimento + " -" + "15/" + mesAnoFormatado;
	//		
	// return datasVencimento;
	// }
	public String getConsumoMedio(){

		return consumoMedio;
	}

	public void setConsumoMedio(String consumoMedio){

		this.consumoMedio = consumoMedio;
	}

	public String get1DataVencimento(){

		String dataVencimento = "";
		Integer mesAnoReferencia = getAmReferencia();
		mesAnoReferencia = Util.somaUmMesAnoMesReferencia(mesAnoReferencia);

		String mesAnoFormatado = Util.formatarAnoMesParaMesAno(mesAnoReferencia);

		dataVencimento = "04/" + mesAnoFormatado;

		return dataVencimento;
	}

	public String getNumeroHidrometro(){

		if(numeroHidrometro != null){
			return numeroHidrometro;
		}else{
			return "";
		}

	}

	public void setNumeroHidrometro(String numeroHidrometro){

		this.numeroHidrometro = numeroHidrometro;
	}

	public String getCategoriaImovel(){

		return categoriaImovel;
	}

	public void setCategoriaImovel(String categoriaImovel){

		this.categoriaImovel = categoriaImovel;
	}

	public String getContaSemCodigoBarras(){

		return contaSemCodigoBarras;
	}

	public void setContaSemCodigoBarras(String contaSemCodigoBarras){

		this.contaSemCodigoBarras = contaSemCodigoBarras;
	}

	public Integer getDebitoCreditoSituacaoAtualConta(){

		return debitoCreditoSituacaoAtualConta;
	}

	public void setDebitoCreditoSituacaoAtualConta(Integer debitoCreditoSituacaoAtualConta){

		this.debitoCreditoSituacaoAtualConta = debitoCreditoSituacaoAtualConta;
	}

	public String getContaPaga(){

		return contaPaga;
	}

	public void setContaPaga(String contaPaga){

		this.contaPaga = contaPaga;
	}

	public String getMsgConta(){

		return msgConta;
	}

	public void setMsgConta(String msgConta){

		this.msgConta = msgConta;
	}

	public String getMsgLinha1Conta(){

		return msgLinha1Conta;
	}

	public void setMsgLinha1Conta(String msgLinha1Conta){

		this.msgLinha1Conta = msgLinha1Conta;
	}

	public String getMsgLinha2Conta(){

		return msgLinha2Conta;
	}

	public void setMsgLinha2Conta(String msgLinha2Conta){

		this.msgLinha2Conta = msgLinha2Conta;
	}

	public String getMsgLinha3Conta(){

		return msgLinha3Conta;
	}

	public void setMsgLinha3Conta(String msgLinha3Conta){

		this.msgLinha3Conta = msgLinha3Conta;
	}

	public String getMsgLinha4Conta(){

		return msgLinha4Conta;
	}

	public void setMsgLinha4Conta(String msgLinha4Conta){

		this.msgLinha4Conta = msgLinha4Conta;
	}

	public String getMsgLinha5Conta(){

		return msgLinha5Conta;
	}

	public void setMsgLinha5Conta(String msgLinha5Conta){

		this.msgLinha5Conta = msgLinha5Conta;
	}

	public String getPadraoCloro(){

		return padraoCloro;
	}

	public void setPadraoCloro(String padraoCloro){

		this.padraoCloro = padraoCloro;
	}

	public String getPadraoColiformesfecais(){

		return padraoColiformesfecais;
	}

	public void setPadraoColiformesfecais(String padraoColiformesfecais){

		this.padraoColiformesfecais = padraoColiformesfecais;
	}

	public String getPadraoColiformesTotais(){

		return padraoColiformesTotais;
	}

	public void setPadraoColiformesTotais(String padraoColiformesTotais){

		this.padraoColiformesTotais = padraoColiformesTotais;
	}

	public String getPadraoCor(){

		return padraoCor;
	}

	public void setPadraoCor(String padraoCor){

		this.padraoCor = padraoCor;
	}

	public String getPadraoFerro(){

		return padraoFerro;
	}

	public void setPadraoFerro(String padraoFerro){

		this.padraoFerro = padraoFerro;
	}

	public String getPadraoFluor(){

		return padraoFluor;
	}

	public void setPadraoFluor(String padraoFluor){

		this.padraoFluor = padraoFluor;
	}

	public String getPadraoPh(){

		return padraoPh;
	}

	public void setPadraoPh(String padraoPh){

		this.padraoPh = padraoPh;
	}

	public String getPadraoTurbidez(){

		return padraoTurbidez;
	}

	public void setPadraoTurbidez(String padraoTurbidez){

		this.padraoTurbidez = padraoTurbidez;
	}

	public String getValorMedioCloro(){

		return valorMedioCloro;
	}

	public void setValorMedioCloro(String valorMedioCloro){

		this.valorMedioCloro = valorMedioCloro;
	}

	public String getValorMedioColiformesfecais(){

		return valorMedioColiformesfecais;
	}

	public void setValorMedioColiformesfecais(String valorMedioColiformesfecais){

		this.valorMedioColiformesfecais = valorMedioColiformesfecais;
	}

	public String getValorMedioColiformesTotais(){

		return valorMedioColiformesTotais;
	}

	public void setValorMedioColiformesTotais(String valorMedioColiformesTotais){

		this.valorMedioColiformesTotais = valorMedioColiformesTotais;
	}

	public String getValorMedioCor(){

		return valorMedioCor;
	}

	public void setValorMedioCor(String valorMedioCor){

		this.valorMedioCor = valorMedioCor;
	}

	public String getValorMedioFerro(){

		return valorMedioFerro;
	}

	public void setValorMedioFerro(String valorMedioFerro){

		this.valorMedioFerro = valorMedioFerro;
	}

	public String getValorMedioFluor(){

		return valorMedioFluor;
	}

	public void setValorMedioFluor(String valorMedioFluor){

		this.valorMedioFluor = valorMedioFluor;
	}

	public String getValorMedioPh(){

		return valorMedioPh;
	}

	public void setValorMedioPh(String valorMedioPh){

		this.valorMedioPh = valorMedioPh;
	}

	public String getValorMedioTurbidez(){

		return valorMedioTurbidez;
	}

	public void setValorMedioTurbidez(String valorMedioTurbidez){

		this.valorMedioTurbidez = valorMedioTurbidez;
	}

	public String getEnderecoLinha2(){

		return enderecoLinha2;
	}

	public void setEnderecoLinha2(String enderecoLinha2){

		this.enderecoLinha2 = enderecoLinha2;
	}

	public String getEnderecoLinha3(){

		return enderecoLinha3;
	}

	public void setEnderecoLinha3(String enderecoLinha3){

		this.enderecoLinha3 = enderecoLinha3;
	}

	public String getDatasVencimento(){

		return datasVencimento;
	}

	public void setDatasVencimento(String datasVencimento){

		this.datasVencimento = datasVencimento;
	}

	public Integer getIdFuncionario(){

		return idFuncionario;
	}

	public void setIdFuncionario(Integer idFuncionario){

		this.idFuncionario = idFuncionario;
	}

	public String getNomeFuncionario(){

		return nomeFuncionario;
	}

	public void setNomeFuncionario(String nomeFuncionario){

		this.nomeFuncionario = nomeFuncionario;
	}

	public BigDecimal getValorConta(){

		return valorConta;
	}

	public void setValorConta(BigDecimal valorConta){

		this.valorConta = valorConta;
	}

	public Integer getContaTipo(){

		return contaTipo;
	}

	public void setContaTipo(Integer contaTipo){

		this.contaTipo = contaTipo;
	}

	public String getNumeroAmostrasMediaTurbidez(){

		return numeroAmostrasMediaTurbidez;
	}

	public void setNumeroAmostrasMediaTurbidez(String numeroAmostrasMediaTurbidez){

		this.numeroAmostrasMediaTurbidez = numeroAmostrasMediaTurbidez;
	}

	public String getNumeroAmostrasMediaCloro(){

		return numeroAmostrasMediaCloro;
	}

	public void setNumeroAmostrasMediaCloro(String numeroAmostrasMediaCloro){

		this.numeroAmostrasMediaCloro = numeroAmostrasMediaCloro;
	}

	public String getNumeroAmostrasMediaCor(){

		return numeroAmostrasMediaCor;
	}

	public void setNumeroAmostrasMediaCor(String numeroAmostrasMediaCor){

		this.numeroAmostrasMediaCor = numeroAmostrasMediaCor;
	}

	public String getNumeroAmostrasMediaPH(){

		return numeroAmostrasMediaPH;
	}

	public void setNumeroAmostrasMediaPH(String numeroAmostrasMediaPH){

		this.numeroAmostrasMediaPH = numeroAmostrasMediaPH;
	}

	public String getNumeroAmostrasMediaBacteriasHeterotroficas(){

		return numeroAmostrasMediaBacteriasHeterotroficas;
	}

	public void setNumeroAmostrasMediaBacteriasHeterotroficas(String numeroAmostrasMediaBacteriasHeterotroficas){

		this.numeroAmostrasMediaBacteriasHeterotroficas = numeroAmostrasMediaBacteriasHeterotroficas;
	}

	public String getNumeroAmostrasMediaColiformesTermotolerantes(){

		return numeroAmostrasMediaColiformesTermotolerantes;
	}

	public void setNumeroAmostrasMediaColiformesTermotolerantes(String numeroAmostrasMediaColiformesTermotolerantes){

		this.numeroAmostrasMediaColiformesTermotolerantes = numeroAmostrasMediaColiformesTermotolerantes;
	}

	public String getNumeroAmostrasMediaColiformesTotais(){

		return numeroAmostrasMediaColiformesTotais;
	}

	public void setNumeroAmostrasMediaColiformesTotais(String numeroAmostrasMediaColiformesTotais){

		this.numeroAmostrasMediaColiformesTotais = numeroAmostrasMediaColiformesTotais;
	}

	public Integer getIdLogradouroEnderecoClienteEntrega(){

		return idLogradouroEnderecoClienteEntrega;
	}

	public void setIdLogradouroEnderecoClienteEntrega(Integer idLogradouroEnderecoClienteEntrega){

		this.idLogradouroEnderecoClienteEntrega = idLogradouroEnderecoClienteEntrega;
	}

	public String getNumeroImovelEnderecoClienteEntrega(){

		return numeroImovelEnderecoClienteEntrega;
	}

	public void setNumeroImovelEnderecoClienteEntrega(String numeroImovelEnderecoClienteEntrega){

		this.numeroImovelEnderecoClienteEntrega = numeroImovelEnderecoClienteEntrega;
	}

	public Short getIndicadorCodigoBarras(){

		return indicadorCodigoBarras;
	}

	public void setIndicadorCodigoBarras(Short indicadorCodigoBarras){

		this.indicadorCodigoBarras = indicadorCodigoBarras;
	}

	public Short getIndicadorDebitoAutomatico(){

		return indicadorDebitoAutomatico;
	}

	public void setIndicadorDebitoAutomatico(Short indicadorDebitoAutomatico){

		this.indicadorDebitoAutomatico = indicadorDebitoAutomatico;
	}

	public Short getIndicadorFaturaInformativa(){

		return indicadorFaturaInformativa;
	}

	public void setIndicadorFaturaInformativa(Short indicadorFaturaInformativa){

		this.indicadorFaturaInformativa = indicadorFaturaInformativa;
	}

	public String getEnderecoClienteEntrega(){

		return enderecoClienteEntrega;
	}

	public void setEnderecoClienteEntrega(String enderecoClienteEntrega){

		this.enderecoClienteEntrega = enderecoClienteEntrega;
	}

	public Integer getContaMotivoRetificacao(){

		return contaMotivoRetificacao;
	}

	public void setContaMotivoRetificacao(Integer contaMotivoRetificacao){

		this.contaMotivoRetificacao = contaMotivoRetificacao;
	}

	public Integer getFuncionario(){

		return funcionario;
	}

	public void setFuncionario(Integer funcionario){

		this.funcionario = funcionario;
	}

	public Short getIndicadorFaturamentoLigacaoAguaSituacao(){

		return indicadorFaturamentoLigacaoAguaSituacao;
	}

	public void setIndicadorFaturamentoLigacaoAguaSituacao(Short indicadorFaturamentoLigacaoAguaSituacao){

		this.indicadorFaturamentoLigacaoAguaSituacao = indicadorFaturamentoLigacaoAguaSituacao;
	}

	public Short getIndicadorFaturamentoLigacaoEsgotoSituacao(){

		return indicadorFaturamentoLigacaoEsgotoSituacao;
	}

	public void setIndicadorFaturamentoLigacaoEsgotoSituacao(Short indicadorFaturamentoLigacaoEsgotoSituacao){

		this.indicadorFaturamentoLigacaoEsgotoSituacao = indicadorFaturamentoLigacaoEsgotoSituacao;
	}

	public String getTipoDocCliente(){

		return tipoDocCliente;
	}

	public void setTipoDocCliente(String tipoDocCliente){

		this.tipoDocCliente = tipoDocCliente;
	}

	public String getCpfCnpjCliente(){

		return cpfCnpjCliente;
	}

	public void setCpfCnpjCliente(String cpfCnpjCliente){

		this.cpfCnpjCliente = cpfCnpjCliente;
	}

	public Date getDataEmissaoConta(){

		return dataEmissaoConta;
	}

	public void setDataEmissaoConta(Date dataEmissaoConta){

		this.dataEmissaoConta = dataEmissaoConta;
	}

	public String getDataEmissaoContaFormatada(){

		String retorno = "";

		if(dataEmissaoConta != null){

			SimpleDateFormat df = new SimpleDateFormat(Constantes.FORMATO_DATA_BR);

			retorno = df.format(dataEmissaoConta);

		}

		return retorno;

	}

	public Short getIndicadorPagamento(){

		return indicadorPagamento;
	}

	public void setIndicadorPagamento(Short indicadorPagamento){

		this.indicadorPagamento = indicadorPagamento;
	}

	public Short getIndicadorCobrancaMulta(){

		return indicadorCobrancaMulta;
	}

	public void setIndicadorCobrancaMulta(Short indicadorCobrancaMulta){

		this.indicadorCobrancaMulta = indicadorCobrancaMulta;
	}

	public String getIndicadorExibirAcrescimos(){

		return indicadorExibirAcrescimos;
	}

	public void setIndicadorExibirAcrescimos(String indicadorExibirAcrescimos){

		this.indicadorExibirAcrescimos = indicadorExibirAcrescimos;
	}

	public Integer getIdConsumoTarifa(){

		return idConsumoTarifa;
	}

	public void setIdConsumoTarifa(Integer idConsumoTarifa){

		this.idConsumoTarifa = idConsumoTarifa;
	}

	public String getConsumoMedido(){

		return consumoMedido;
	}

	public void setConsumoMedido(String consumoMedido){

		this.consumoMedido = consumoMedido;
	}

	public String getDescricaoContaMotivoRetificacao(){

		return descricaoContaMotivoRetificacao;
	}

	public void setDescricaoContaMotivoRetificacao(String descricaoContaMotivoRetificacao){

		this.descricaoContaMotivoRetificacao = descricaoContaMotivoRetificacao;
	}


	/**
	 * @return the valorImpostoPisCofins
	 */
	public BigDecimal getValorImpostoPisCofins(){

		return valorImpostoPisCofins;
	}


	/**
	 * @param valorImpostoPisCofins
	 *            the valorImpostoPisCofins to set
	 */
	public void setValorImpostoPisCofins(BigDecimal valorImpostoPisCofins){

		this.valorImpostoPisCofins = valorImpostoPisCofins;
	}

	public String getMensagemSubstitutaCodigoBarras(){

		return mensagemSubstitutaCodigoBarras;
	}

	public void setMensagemSubstitutaCodigoBarras(String mensagemSubstitutaCodigoBarras){

		this.mensagemSubstitutaCodigoBarras = mensagemSubstitutaCodigoBarras;
	}
}
