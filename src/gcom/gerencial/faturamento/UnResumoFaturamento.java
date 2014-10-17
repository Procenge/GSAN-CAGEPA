/*
 * Copyright (C) 2007-2007 the GSAN � Sistema Integrado de Gest�o de Servi�os de Saneamento
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
 * Foundation, Inc., 59 Temple Place � Suite 330, Boston, MA 02111-1307, USA
 */

/*
 * GSAN � Sistema Integrado de Gest�o de Servi�os de Saneamento
 * Copyright (C) <2007> 
 * Adriano Britto Siqueira
 * Alexandre Santos Cabral
 * Ana Carolina Alves Breda
 * Ana Maria Andrade Cavalcante
 * Aryed Lins de Ara�jo
 * Bruno Leonardo Rodrigues Barros
 * Carlos Elmano Rodrigues Ferreira
 * Cl�udio de Andrade Lira
 * Denys Guimar�es Guenes Tavares
 * Eduardo Breckenfeld da Rosa Borges
 * Fab�ola Gomes de Ara�jo
 * Fl�vio Leonardo Cavalcanti Cordeiro
 * Francisco do Nascimento J�nior
 * Homero Sampaio Cavalcanti
 * Ivan S�rgio da Silva J�nior
 * Jos� Edmar de Siqueira
 * Jos� Thiago Ten�rio Lopes
 * K�ssia Regina Silvestre de Albuquerque
 * Leonardo Luiz Vieira da Silva
 * M�rcio Roberto Batista da Silva
 * Maria de F�tima Sampaio Leite
 * Micaela Maria Coelho de Ara�jo
 * Nelson Mendon�a de Carvalho
 * Newton Morais e Silva
 * Pedro Alexandre Santos da Silva Filho
 * Rafael Corr�a Lima e Silva
 * Rafael Francisco Pinto
 * Rafael Koury Monteiro
 * Rafael Palermo de Ara�jo
 * Raphael Veras Rossiter
 * Roberto Sobreira Barbalho
 * Rodrigo Avellar Silveira
 * Rosana Carvalho Barbosa
 * S�vio Luiz de Andrade Cavalcante
 * Tai Mu Shih
 * Thiago Augusto Souza do Nascimento
 * Tiago Moreno Rodrigues
 * Vivianne Barbosa Sousa
 *
 * Este programa � software livre; voc� pode redistribu�-lo e/ou
 * modific�-lo sob os termos de Licen�a P�blica Geral GNU, conforme
 * publicada pela Free Software Foundation; vers�o 2 da
 * Licen�a.
 * Este programa � distribu�do na expectativa de ser �til, mas SEM
 * QUALQUER GARANTIA; sem mesmo a garantia impl�cita de
 * COMERCIALIZA��O ou de ADEQUA��O A QUALQUER PROP�SITO EM
 * PARTICULAR. Consulte a Licen�a P�blica Geral GNU para obter mais
 * detalhes.
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU
 * junto com este programa; se n�o, escreva para Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 * 02111-1307, USA.
 */

package gcom.gerencial.faturamento;

import gcom.gerencial.arrecadacao.pagamento.GEpocaPagamento;
import gcom.gerencial.arrecadacao.pagamento.GPagamentoSituacao;
import gcom.gerencial.atendimentopublico.ligacaoagua.GLigacaoAguaPerfil;
import gcom.gerencial.atendimentopublico.ligacaoagua.GLigacaoAguaSituacao;
import gcom.gerencial.atendimentopublico.ligacaoesgoto.GLigacaoEsgotoPerfil;
import gcom.gerencial.atendimentopublico.ligacaoesgoto.GLigacaoEsgotoSituacao;
import gcom.gerencial.cadastro.GEmpresa;
import gcom.gerencial.cadastro.cliente.GClienteTipo;
import gcom.gerencial.cadastro.cliente.GEsferaPoder;
import gcom.gerencial.cadastro.imovel.GCategoria;
import gcom.gerencial.cadastro.imovel.GImovelPerfil;
import gcom.gerencial.cadastro.imovel.GSubcategoria;
import gcom.gerencial.cadastro.localidade.GGerenciaRegional;
import gcom.gerencial.cadastro.localidade.GLocalidade;
import gcom.gerencial.cadastro.localidade.GQuadra;
import gcom.gerencial.cadastro.localidade.GSetorComercial;
import gcom.gerencial.cadastro.localidade.GUnidadeNegocio;
import gcom.gerencial.cobranca.FaixaValor;
import gcom.gerencial.cobranca.GDocumentoTipo;
import gcom.gerencial.faturamento.credito.GCreditoOrigem;
import gcom.gerencial.faturamento.credito.GCreditoTipo;
import gcom.gerencial.faturamento.debito.GDebitoTipo;
import gcom.gerencial.financeiro.GFinanciamentoTipo;
import gcom.gerencial.financeiro.lancamento.GLancamentoItemContabil;
import gcom.gerencial.micromedicao.GRota;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

/** @author Hibernate CodeGenerator */

public class UnResumoFaturamento
				implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** identifier field */
	private Integer id;

	/** persistent field */

	private int referencia;

	/** persistent field */

	private int codigoSetorComercial;

	/** persistent field */

	private int numeroQuadra;

	/** persistent field */

	private int volumeFaturadoagua;

	/** persistent field */

	private BigDecimal valorFaturadoEsgoto;

	/** persistent field */

	private BigDecimal valorFaturadoAgua;

	/** persistent field */

	private int volumeFaturadoEsgoto;

	/** persistent field */

	private int quantidadeContasEmitidas;

	/** persistent field */

	private Date ultimaAlteracao;

	/** persistent field */

	private GSubcategoria gerSubcategoria;

	/** persistent field */

	private GClienteTipo gerClienteTipo;

	/** persistent field */

	private GLigacaoAguaSituacao gerLigacaoAguaSituacao;

	/** persistent field */

	private GUnidadeNegocio gerUnidadeNegocio;

	/** persistent field */

	private GLocalidade gerLocalidade;

	/** persistent field */

	private GLocalidade gerLocalidadeElo;

	/** persistent field */

	private GQuadra gerQuadra;

	/** persistent field */

	private GLigacaoEsgotoSituacao gerLigacaoEsgotoSituacao;

	/** persistent field */

	private GLigacaoEsgotoPerfil gerLigacaoEsgotoPerfil;

	/** persistent field */

	private GGerenciaRegional gerGerenciaRegional;

	/** persistent field */

	private GSetorComercial gerSetorComercial;

	/** persistent field */

	private GDocumentoTipo gerDocumentoTipo;

	/** persistent field */

	private GPagamentoSituacao gerPagamentoSituacao;

	/** persistent field */

	private GLigacaoAguaPerfil gerLigacaoAguaPerfil;

	/** persistent field */

	private GEpocaPagamento gerEpocaPagamento;

	/** persistent field */

	private GEsferaPoder gerEsferaPoder;

	/** persistent field */

	private GCategoria gerCategoria;

	/** persistent field */

	private GImovelPerfil gerImovelPerfil;

	/** persistent field */

	private GRota gerRota;

	/** persistent field */

	private Set unResumoArrecadacaoCreditos;

	/** persistent field */

	private Set unResumoArrecadacaoOutros;

	/** persistent field */

	private BigDecimal valorDocumentosFaturadosCreditos;

	/** persistent field */

	private Integer quantidadeDocumentosFaturadosCreditos;

	/** persistent field */

	private Short quantidadeDocumentosFaturadosOutros;

	/** persistent field */

	private BigDecimal valorDocumentosFaturadosOutros;

	/** persistent field */

	private GCreditoOrigem gerCreditoOrigem;

	/** persistent field */

	private GLancamentoItemContabil gerLancamentoItemContabil;

	/** persistent field */

	private GFinanciamentoTipo gerFinanciamentoTipo;

	/** persistent field */

	private GEmpresa gerEmpresa;

	/** persistent field */

	private BigDecimal valorFinanciamentoIncluido;

	/** persistent field */

	private BigDecimal valorFinanciamentoCancelado;

	private Integer quantidadeEconomiasFaturadas;

	private Short indicadorHidrometro;

	private GDebitoTipo gerDebitoTipo;

	private GCreditoTipo gerCreditoTipo;

	private BigDecimal valorImposto;

	private GImpostoTipo gerImpostoTipo;

	private GConsumoTarifa gerConsumoTarifa;

	private GFaturamentoGrupo gerFaturamentoGrupo;

	public GFaturamentoGrupo getGerFaturamentoGrupo(){

		return gerFaturamentoGrupo;
	}

	public void setGerFaturamentoGrupo(GFaturamentoGrupo gerFaturamentoGrupo){

		this.gerFaturamentoGrupo = gerFaturamentoGrupo;
	}

	// CONSTRUTOR AGUA ESGOTO NORMAL
	public UnResumoFaturamento(int referencia, GGerenciaRegional gGerenciaRegional, GUnidadeNegocio gUnidadeNegocio,
								GLocalidade gLocalidade, GSetorComercial gSetorComercial, GQuadra gQuadra, int codigoSetorComercial,
								int numeroQuadra, GImovelPerfil gImovelPerfil, GEsferaPoder gEsferaPoder, GClienteTipo gClienteTipo,
								GLigacaoAguaSituacao gLigacaoAguaSituacao, GLigacaoEsgotoSituacao gLigacaoEsgotoSituacao,
								GCategoria gCategoria, GSubcategoria gSubcategoria, GLigacaoAguaPerfil gLigacaoAguaPerfil,
								GLigacaoEsgotoPerfil gLigacaoEsgotoPerfil, int volumeFaturadoagua, int volumeFaturadoEsgoto,
								BigDecimal valorFaturadoAgua, BigDecimal valorFaturadoEsgoto, int quantidadeContasEmitidas,
								int quantidadeEconomiasFaturadas, Date ultimaAlteracao, GLocalidade gerLocalidadeElo, GRota gerRota,
								GEmpresa gerEmpresa, GConsumoTarifa gerConsumoTarifa, GFaturamentoGrupo gerFaturamentoGrupo,
								Short indicadorHidrometro, BigDecimal valorImposto, GDocumentoTipo gerDocumentoTipo) {

		this.referencia = referencia;
		this.gerGerenciaRegional = gGerenciaRegional;
		this.gerUnidadeNegocio = gUnidadeNegocio;
		this.gerLocalidade = gLocalidade;
		this.gerSetorComercial = gSetorComercial;
		this.gerQuadra = gQuadra;
		this.codigoSetorComercial = codigoSetorComercial;
		this.numeroQuadra = numeroQuadra;
		this.gerImovelPerfil = gImovelPerfil;
		this.gerEsferaPoder = gEsferaPoder;
		this.gerClienteTipo = gClienteTipo;
		this.gerLigacaoAguaSituacao = gLigacaoAguaSituacao;
		this.gerLigacaoEsgotoSituacao = gLigacaoEsgotoSituacao;
		this.gerCategoria = gCategoria;
		this.gerSubcategoria = gSubcategoria;
		this.gerLigacaoAguaPerfil = gLigacaoAguaPerfil;
		this.gerLigacaoEsgotoPerfil = gLigacaoEsgotoPerfil;
		this.volumeFaturadoagua = volumeFaturadoagua;
		this.volumeFaturadoEsgoto = volumeFaturadoEsgoto;
		this.valorFaturadoAgua = valorFaturadoAgua;
		this.valorFaturadoEsgoto = valorFaturadoEsgoto;
		this.quantidadeContasEmitidas = quantidadeContasEmitidas;
		this.quantidadeEconomiasFaturadas = quantidadeEconomiasFaturadas;
		this.ultimaAlteracao = ultimaAlteracao;
		this.gerLocalidadeElo = gerLocalidadeElo;
		this.gerRota = gerRota;
		this.gerEmpresa = gerEmpresa;
		this.gerConsumoTarifa = gerConsumoTarifa;
		this.gerFaturamentoGrupo = gerFaturamentoGrupo;
		this.indicadorHidrometro = indicadorHidrometro;
		this.valorImposto = valorImposto;
		this.gerDocumentoTipo = gerDocumentoTipo; //  

	}

	// OUTROS
	public UnResumoFaturamento(int referencia, GGerenciaRegional gGerenciaRegional, GUnidadeNegocio gUnidadeNegocio,
								GLocalidade gLocalidade, GSetorComercial gSetorComercial, GQuadra gQuadra, int codigoSetorComercial,
								int numeroQuadra, GImovelPerfil gImovelPerfil, GEsferaPoder gEsferaPoder, GClienteTipo gClienteTipo,
								GLigacaoAguaSituacao gLigacaoAguaSituacao, GLigacaoEsgotoSituacao gLigacaoEsgotoSituacao,
								GCategoria gCategoria, GSubcategoria gSubcategoria, GLigacaoAguaPerfil gLigacaoAguaPerfil,
								GLigacaoEsgotoPerfil gLigacaoEsgotoPerfil, int volumeFaturadoagua, int volumeFaturadoEsgoto,
								BigDecimal valorFaturadoAgua, BigDecimal valorFaturadoEsgoto, int quantidadeContasEmitidas,
								GDocumentoTipo gerDocumentoTipoOutros, GFinanciamentoTipo gerFinanciamentoTipoOutros,
								GLancamentoItemContabil gerLancamentoItemContabilOutros, BigDecimal valorDocumentosFaturadosOutros,
								Short quantidadeDocumentosFaturadosOutros, Date ultimaAlteracao, GLocalidade gerLocalidadeElo,
								GRota gerRota, GEmpresa gerEmpresa, Short indicadorHidrometro, BigDecimal valorImposto,
								GConsumoTarifa gerConsumoTarifa, GFaturamentoGrupo gerFaturamentoGrupo) {

		this.referencia = referencia;
		this.gerGerenciaRegional = gGerenciaRegional;
		this.gerUnidadeNegocio = gUnidadeNegocio;
		this.gerLocalidade = gLocalidade;
		this.gerSetorComercial = gSetorComercial;
		this.gerQuadra = gQuadra;
		this.codigoSetorComercial = codigoSetorComercial;
		this.numeroQuadra = numeroQuadra;
		this.gerImovelPerfil = gImovelPerfil;
		this.gerEsferaPoder = gEsferaPoder;
		this.gerClienteTipo = gClienteTipo;
		this.gerLigacaoAguaSituacao = gLigacaoAguaSituacao;
		this.gerLigacaoEsgotoSituacao = gLigacaoEsgotoSituacao;
		this.gerCategoria = gCategoria;
		this.gerSubcategoria = gSubcategoria;
		this.gerLigacaoAguaPerfil = gLigacaoAguaPerfil;
		this.gerLigacaoEsgotoPerfil = gLigacaoEsgotoPerfil;
		this.volumeFaturadoagua = volumeFaturadoagua;
		this.volumeFaturadoEsgoto = volumeFaturadoEsgoto;
		this.valorFaturadoAgua = valorFaturadoAgua;
		this.valorFaturadoEsgoto = valorFaturadoEsgoto;
		this.quantidadeContasEmitidas = quantidadeContasEmitidas;
		// Campos referente a outros //////////////////////////////////////////////////////
		this.gerDocumentoTipo = gerDocumentoTipoOutros; //  
		this.gerFinanciamentoTipo = gerFinanciamentoTipoOutros; // 
		this.gerLancamentoItemContabil = gerLancamentoItemContabilOutros; //
		this.valorDocumentosFaturadosOutros = valorDocumentosFaturadosOutros; //
		this.quantidadeDocumentosFaturadosOutros = quantidadeDocumentosFaturadosOutros; //
		// /////////////////////////////////////////////////////////////////////////////////
		this.ultimaAlteracao = ultimaAlteracao;
		this.gerLocalidadeElo = gerLocalidadeElo;
		this.gerRota = gerRota;
		this.gerEmpresa = gerEmpresa;
		this.indicadorHidrometro = indicadorHidrometro;
		this.valorImposto = valorImposto;
		this.gerConsumoTarifa = gerConsumoTarifa;
		this.gerFaturamentoGrupo = gerFaturamentoGrupo;
	}

	// CREDITOS
	public UnResumoFaturamento(int referencia, GGerenciaRegional gGerenciaRegional, GUnidadeNegocio gUnidadeNegocio,
								GLocalidade gLocalidade, GSetorComercial gSetorComercial, GQuadra gQuadra, int codigoSetorComercial,
								int numeroQuadra, GImovelPerfil gImovelPerfil, GEsferaPoder gEsferaPoder, GClienteTipo gClienteTipo,
								GLigacaoAguaSituacao gLigacaoAguaSituacao, GLigacaoEsgotoSituacao gLigacaoEsgotoSituacao,
								GCategoria gCategoria, GSubcategoria gSubcategoria, GLigacaoAguaPerfil gLigacaoAguaPerfil,
								GLigacaoEsgotoPerfil gLigacaoEsgotoPerfil, int volumeFaturadoagua, int volumeFaturadoEsgoto,
								BigDecimal valorFaturadoAgua, BigDecimal valorFaturadoEsgoto, int quantidadeContasEmitidas,
								GCreditoOrigem gerCreditoOrigem, GLancamentoItemContabil gerLancamentoItemContabilCredito,
								BigDecimal valorDocumentosFaturadosCreditos, Integer quantidadeDocumentosFaturadosCreditos,
								Date ultimaAlteracao, GLocalidade gerLocalidadeElo, GRota gerRota, GEmpresa gerEmpresa,
								GCreditoTipo gerCreditoTipo, Short indicadorHidrometro, BigDecimal valorImposto,
								GConsumoTarifa gerConsumoTarifa, GFaturamentoGrupo gerFaturamentoGrupo,
								GDocumentoTipo gerDocumentoTipoOutros) {

		this.referencia = referencia;
		this.gerGerenciaRegional = gGerenciaRegional;
		this.gerUnidadeNegocio = gUnidadeNegocio;
		this.gerLocalidade = gLocalidade;
		this.gerSetorComercial = gSetorComercial;
		this.gerQuadra = gQuadra;
		this.codigoSetorComercial = codigoSetorComercial;
		this.numeroQuadra = numeroQuadra;
		this.gerImovelPerfil = gImovelPerfil;
		this.gerEsferaPoder = gEsferaPoder;
		this.gerClienteTipo = gClienteTipo;
		this.gerLigacaoAguaSituacao = gLigacaoAguaSituacao;
		this.gerLigacaoEsgotoSituacao = gLigacaoEsgotoSituacao;
		this.gerCategoria = gCategoria;
		this.gerSubcategoria = gSubcategoria;
		this.gerLigacaoAguaPerfil = gLigacaoAguaPerfil;
		this.gerLigacaoEsgotoPerfil = gLigacaoEsgotoPerfil;
		this.volumeFaturadoagua = volumeFaturadoagua;
		this.volumeFaturadoEsgoto = volumeFaturadoEsgoto;
		this.valorFaturadoAgua = valorFaturadoAgua;
		this.valorFaturadoEsgoto = valorFaturadoEsgoto;
		this.quantidadeContasEmitidas = quantidadeContasEmitidas;
		// Campos referente a creditos ///////////////////////////////////////////////////////
		this.gerCreditoOrigem = gerCreditoOrigem; //  
		this.gerLancamentoItemContabil = gerLancamentoItemContabilCredito; // 
		this.valorDocumentosFaturadosCreditos = valorDocumentosFaturadosCreditos; //
		this.quantidadeDocumentosFaturadosCreditos = quantidadeDocumentosFaturadosCreditos; //
		// ////////////////////////////////////////////////////////////////////////////////////
		this.ultimaAlteracao = ultimaAlteracao;
		this.gerLocalidadeElo = gerLocalidadeElo;
		this.gerRota = gerRota;
		this.gerEmpresa = gerEmpresa;
		this.gerCreditoTipo = gerCreditoTipo;
		this.indicadorHidrometro = indicadorHidrometro;
		this.valorImposto = valorImposto;
		this.gerConsumoTarifa = gerConsumoTarifa;
		this.gerFaturamentoGrupo = gerFaturamentoGrupo;
		this.gerDocumentoTipo = gerDocumentoTipoOutros; //  

	}

	// DEBITO A COBRAR
	public UnResumoFaturamento(int referencia, GGerenciaRegional gGerenciaRegional, GUnidadeNegocio gUnidadeNegocio,
								GLocalidade gLocalidade, GSetorComercial gSetorComercial, GQuadra gQuadra, int codigoSetorComercial,
								int numeroQuadra, GImovelPerfil gImovelPerfil, GEsferaPoder gEsferaPoder, GClienteTipo gClienteTipo,
								GLigacaoAguaSituacao gLigacaoAguaSituacao, GLigacaoEsgotoSituacao gLigacaoEsgotoSituacao,
								GCategoria gCategoria, GSubcategoria gSubcategoria, GLigacaoAguaPerfil gLigacaoAguaPerfil,
								GLigacaoEsgotoPerfil gLigacaoEsgotoPerfil, int volumeFaturadoagua, int volumeFaturadoEsgoto,
								BigDecimal valorFaturadoAgua, BigDecimal valorFaturadoEsgoto, int quantidadeContasEmitidas,
								GDocumentoTipo gerDocumentoTipoOutros, GFinanciamentoTipo gerFinanciamentoTipoOutros,
								GLancamentoItemContabil gerLancamentoItemContabilOutros, BigDecimal valorDocumentosFaturadosOutros,
								Short quantidadeDocumentosFaturadosOutros, Date ultimaAlteracao, GLocalidade gerLocalidadeElo,
								GRota gerRota, GEmpresa gerEmpresa, BigDecimal valorFinanciamentoIncluido,
								BigDecimal valorFinanciamentoCancelado, GDebitoTipo gerDebitoTipo, Short indicadorHidrometro,
								BigDecimal valorImposto, GConsumoTarifa gerConsumoTarifa, GFaturamentoGrupo gerFaturamentoGrupo) {

		this.referencia = referencia;
		this.gerGerenciaRegional = gGerenciaRegional;
		this.gerUnidadeNegocio = gUnidadeNegocio;
		this.gerLocalidade = gLocalidade;
		this.gerSetorComercial = gSetorComercial;
		this.gerQuadra = gQuadra;
		this.codigoSetorComercial = codigoSetorComercial;
		this.numeroQuadra = numeroQuadra;
		this.gerImovelPerfil = gImovelPerfil;
		this.gerEsferaPoder = gEsferaPoder;
		this.gerClienteTipo = gClienteTipo;
		this.gerLigacaoAguaSituacao = gLigacaoAguaSituacao;
		this.gerLigacaoEsgotoSituacao = gLigacaoEsgotoSituacao;
		this.gerCategoria = gCategoria;
		this.gerSubcategoria = gSubcategoria;
		this.gerLigacaoAguaPerfil = gLigacaoAguaPerfil;
		this.gerLigacaoEsgotoPerfil = gLigacaoEsgotoPerfil;
		this.volumeFaturadoagua = volumeFaturadoagua;
		this.volumeFaturadoEsgoto = volumeFaturadoEsgoto;
		this.valorFaturadoAgua = valorFaturadoAgua;
		this.valorFaturadoEsgoto = valorFaturadoEsgoto;
		this.quantidadeContasEmitidas = quantidadeContasEmitidas;
		// Campos referente a outros //////////////////////////////////////////////////////
		this.gerDocumentoTipo = gerDocumentoTipoOutros; //  
		this.gerFinanciamentoTipo = gerFinanciamentoTipoOutros; // 
		this.gerLancamentoItemContabil = gerLancamentoItemContabilOutros; //
		this.valorDocumentosFaturadosOutros = valorDocumentosFaturadosOutros; //
		this.quantidadeDocumentosFaturadosOutros = quantidadeDocumentosFaturadosOutros; //
		// /////////////////////////////////////////////////////////////////////////////////
		this.ultimaAlteracao = ultimaAlteracao;
		this.gerLocalidadeElo = gerLocalidadeElo;
		this.gerRota = gerRota;
		this.gerEmpresa = gerEmpresa;
		this.valorFinanciamentoIncluido = valorFinanciamentoIncluido;
		this.valorFinanciamentoCancelado = valorFinanciamentoCancelado;
		this.gerDebitoTipo = gerDebitoTipo;
		this.indicadorHidrometro = indicadorHidrometro;
		this.valorImposto = valorImposto;
		this.gerConsumoTarifa = gerConsumoTarifa;
		this.gerFaturamentoGrupo = gerFaturamentoGrupo;
	}

	// IMPOSTOS
	public UnResumoFaturamento(int referencia, GGerenciaRegional gGerenciaRegional, GUnidadeNegocio gUnidadeNegocio,
								GLocalidade gLocalidade, GSetorComercial gSetorComercial, GQuadra gQuadra, int codigoSetorComercial,
								int numeroQuadra, GImovelPerfil gImovelPerfil, GEsferaPoder gEsferaPoder, GClienteTipo gClienteTipo,
								GLigacaoAguaSituacao gLigacaoAguaSituacao, GLigacaoEsgotoSituacao gLigacaoEsgotoSituacao,
								GCategoria gCategoria, GSubcategoria gSubcategoria, GLigacaoAguaPerfil gLigacaoAguaPerfil,
								GLigacaoEsgotoPerfil gLigacaoEsgotoPerfil, int volumeFaturadoagua, int volumeFaturadoEsgoto,
								BigDecimal valorFaturadoAgua, BigDecimal valorFaturadoEsgoto, int quantidadeContasEmitidas,
								BigDecimal valorDocumentosFaturadosOutros, Short quantidadeDocumentosFaturadosOutros, Date ultimaAlteracao,
								GLocalidade gerLocalidadeElo, GRota gerRota, GEmpresa gerEmpresa, GImpostoTipo gerImpostoTipo,
								BigDecimal valorImposto, Short indicadorHidrometro, GConsumoTarifa gerConsumoTarifa,
								GFaturamentoGrupo gerFaturamentoGrupo, GDocumentoTipo gerDocumentoTipo) {

		this.referencia = referencia;
		this.gerGerenciaRegional = gGerenciaRegional;
		this.gerUnidadeNegocio = gUnidadeNegocio;
		this.gerLocalidade = gLocalidade;
		this.gerSetorComercial = gSetorComercial;
		this.gerQuadra = gQuadra;
		this.codigoSetorComercial = codigoSetorComercial;
		this.numeroQuadra = numeroQuadra;
		this.gerImovelPerfil = gImovelPerfil;
		this.gerEsferaPoder = gEsferaPoder;
		this.gerClienteTipo = gClienteTipo;
		this.gerLigacaoAguaSituacao = gLigacaoAguaSituacao;
		this.gerLigacaoEsgotoSituacao = gLigacaoEsgotoSituacao;
		this.gerCategoria = gCategoria;
		this.gerSubcategoria = gSubcategoria;
		this.gerLigacaoAguaPerfil = gLigacaoAguaPerfil;
		this.gerLigacaoEsgotoPerfil = gLigacaoEsgotoPerfil;
		this.volumeFaturadoagua = volumeFaturadoagua;
		this.volumeFaturadoEsgoto = volumeFaturadoEsgoto;
		this.valorFaturadoAgua = valorFaturadoAgua;
		this.valorFaturadoEsgoto = valorFaturadoEsgoto;
		this.quantidadeContasEmitidas = quantidadeContasEmitidas;
		this.valorDocumentosFaturadosOutros = valorDocumentosFaturadosOutros;
		this.quantidadeDocumentosFaturadosOutros = quantidadeDocumentosFaturadosOutros;
		this.ultimaAlteracao = ultimaAlteracao;
		this.gerLocalidadeElo = gerLocalidadeElo;
		this.gerRota = gerRota;
		this.gerEmpresa = gerEmpresa;
		this.gerImpostoTipo = gerImpostoTipo;
		this.valorImposto = valorImposto;
		this.indicadorHidrometro = indicadorHidrometro;
		this.gerConsumoTarifa = gerConsumoTarifa;
		this.gerFaturamentoGrupo = gerFaturamentoGrupo;
		this.gerDocumentoTipo = gerDocumentoTipo;

	}

	// CONSTRUTOR FULL
	public UnResumoFaturamento(int referencia, int codigoSetorComercial, int numeroQuadra, int volumeFaturadoagua,
								BigDecimal valorFaturadoEsgoto, BigDecimal valorFaturadoAgua, int volumeFaturadoEsgoto,
								int quantidadeContasEmitidas, Date ultimaAlteracao, GSubcategoria gSubcategoria, GClienteTipo gClienteTipo,
								GLigacaoAguaSituacao gLigacaoAguaSituacao, GUnidadeNegocio gUnidadeNegocio, GLocalidade gLocalidade,
								GLocalidade gLocalidadeElo, GQuadra gQuadra, GLigacaoEsgotoSituacao gLigacaoEsgotoSituacao,
								GLigacaoEsgotoPerfil gLigacaoEsgotoPerfil, GGerenciaRegional gGerenciaRegional,
								GSetorComercial gSetorComercial, GDocumentoTipo gDocumentoTipo, GPagamentoSituacao gPagamentoSituacao,
								GLigacaoAguaPerfil gLigacaoAguaPerfil, GEpocaPagamento gEpocaPagamento, GEsferaPoder gEsferaPoder,
								GCategoria gCategoria, GImovelPerfil gImovelPerfil, GRota gRota, Set unResumoArrecadacaoCreditos,
								Set unResumoArrecadacaoOutros) {

		this.referencia = referencia;
		this.codigoSetorComercial = codigoSetorComercial;
		this.numeroQuadra = numeroQuadra;
		this.volumeFaturadoagua = volumeFaturadoagua;
		this.valorFaturadoEsgoto = valorFaturadoEsgoto;
		this.valorFaturadoAgua = valorFaturadoAgua;
		this.volumeFaturadoEsgoto = volumeFaturadoEsgoto;
		this.quantidadeContasEmitidas = quantidadeContasEmitidas;
		this.ultimaAlteracao = ultimaAlteracao;
		this.gerSubcategoria = gSubcategoria;
		this.gerClienteTipo = gClienteTipo;
		this.gerLigacaoAguaSituacao = gLigacaoAguaSituacao;
		this.gerUnidadeNegocio = gUnidadeNegocio;
		this.gerLocalidade = gLocalidade;
		this.gerLocalidadeElo = gLocalidadeElo;
		this.gerQuadra = gQuadra;
		this.gerLigacaoEsgotoSituacao = gLigacaoEsgotoSituacao;
		this.gerLigacaoEsgotoPerfil = gLigacaoEsgotoPerfil;
		this.gerGerenciaRegional = gGerenciaRegional;
		this.gerSetorComercial = gSetorComercial;
		this.gerDocumentoTipo = gDocumentoTipo;
		this.gerPagamentoSituacao = gPagamentoSituacao;
		this.gerLigacaoAguaPerfil = gLigacaoAguaPerfil;
		this.gerEpocaPagamento = gEpocaPagamento;
		this.gerEsferaPoder = gEsferaPoder;
		this.gerCategoria = gCategoria;
		this.gerImovelPerfil = gImovelPerfil;
		this.gerRota = gRota;
		this.unResumoArrecadacaoCreditos = unResumoArrecadacaoCreditos;
		this.unResumoArrecadacaoOutros = unResumoArrecadacaoOutros;
	}

	/** default constructor */
	public UnResumoFaturamento() {

	}

	public int getCodigoSetorComercial(){

		return codigoSetorComercial;

	}

	public void setCodigoSetorComercial(int codigoSetorComercial){

		this.codigoSetorComercial = codigoSetorComercial;

	}

	public GLigacaoAguaPerfil getGerLigacaoAguaPerfil(){

		return gerLigacaoAguaPerfil;

	}

	public void setGerLigacaoAguaPerfil(GLigacaoAguaPerfil gerLigacaoAguaPerfil){

		this.gerLigacaoAguaPerfil = gerLigacaoAguaPerfil;

	}

	public GCategoria getGerCategoria(){

		return gerCategoria;

	}

	public void setGerCategoria(GCategoria gerCategoria){

		this.gerCategoria = gerCategoria;

	}

	public GClienteTipo getGerClienteTipo(){

		return gerClienteTipo;

	}

	public void setGerClienteTipo(GClienteTipo gerClienteTipo){

		this.gerClienteTipo = gerClienteTipo;

	}

	public GDocumentoTipo getGerDocumentoTipo(){

		return gerDocumentoTipo;

	}

	public void setGerDocumentoTipo(GDocumentoTipo gerDocumentoTipo){

		this.gerDocumentoTipo = gerDocumentoTipo;

	}

	public GEpocaPagamento getGerEpocaPagamento(){

		return gerEpocaPagamento;

	}

	public void setGerEpocaPagamento(GEpocaPagamento gerEpocaPagamento){

		this.gerEpocaPagamento = gerEpocaPagamento;

	}

	public GEsferaPoder getGerEsferaPoder(){

		return gerEsferaPoder;

	}

	public void setGerEsferaPoder(GEsferaPoder gerEsferaPoder){

		this.gerEsferaPoder = gerEsferaPoder;

	}

	public GGerenciaRegional getGerGerenciaRegional(){

		return gerGerenciaRegional;

	}

	public void setGerGerenciaRegional(GGerenciaRegional gerGerenciaRegional){

		this.gerGerenciaRegional = gerGerenciaRegional;

	}

	public GImovelPerfil getGerImovelPerfil(){

		return gerImovelPerfil;

	}

	public void setGerImovelPerfil(GImovelPerfil gerImovelPerfil){

		this.gerImovelPerfil = gerImovelPerfil;

	}

	public GLigacaoAguaSituacao getGerLigacaoAguaSituacao(){

		return gerLigacaoAguaSituacao;

	}

	public void setGerLigacaoAguaSituacao(

	GLigacaoAguaSituacao gerLigacaoAguaSituacao){

		this.gerLigacaoAguaSituacao = gerLigacaoAguaSituacao;

	}

	public GLigacaoEsgotoPerfil getGerLigacaoEsgotoPerfil(){

		return gerLigacaoEsgotoPerfil;

	}

	public void setGerLigacaoEsgotoPerfil(

	GLigacaoEsgotoPerfil gerLigacaoEsgotoPerfil){

		this.gerLigacaoEsgotoPerfil = gerLigacaoEsgotoPerfil;

	}

	public GLigacaoEsgotoSituacao getGerLigacaoEsgotoSituacao(){

		return gerLigacaoEsgotoSituacao;

	}

	public void setGerLigacaoEsgotoSituacao(

	GLigacaoEsgotoSituacao gerLigacaoEsgotoSituacao){

		this.gerLigacaoEsgotoSituacao = gerLigacaoEsgotoSituacao;

	}

	public GLocalidade getGerLocalidade(){

		return gerLocalidade;

	}

	public void setGerLocalidade(GLocalidade gerLocalidade){

		this.gerLocalidade = gerLocalidade;

	}

	public GLocalidade getGerLocalidadeElo(){

		return gerLocalidadeElo;

	}

	public void setGerLocalidadeElo(GLocalidade gerLocalidadeElo){

		this.gerLocalidadeElo = gerLocalidadeElo;

	}

	public GPagamentoSituacao getGerPagamentoSituacao(){

		return gerPagamentoSituacao;

	}

	public void setGerPagamentoSituacao(GPagamentoSituacao gerPagamentoSituacao){

		this.gerPagamentoSituacao = gerPagamentoSituacao;

	}

	public GQuadra getGerQuadra(){

		return gerQuadra;

	}

	public void setGerQuadra(GQuadra gerQuadra){

		this.gerQuadra = gerQuadra;

	}

	public GRota getGerRota(){

		return gerRota;

	}

	public void setGerRota(GRota gerRota){

		this.gerRota = gerRota;

	}

	public GSetorComercial getGerSetorComercial(){

		return gerSetorComercial;

	}

	public void setGerSetorComercial(GSetorComercial gerSetorComercial){

		this.gerSetorComercial = gerSetorComercial;

	}

	public GSubcategoria getGerSubcategoria(){

		return gerSubcategoria;

	}

	public void setGerSubcategoria(GSubcategoria gerSubcategoria){

		this.gerSubcategoria = gerSubcategoria;

	}

	public GUnidadeNegocio getGerUnidadeNegocio(){

		return gerUnidadeNegocio;

	}

	public void setGerUnidadeNegocio(GUnidadeNegocio gerUnidadeNegocio){

		this.gerUnidadeNegocio = gerUnidadeNegocio;

	}

	public Integer getId(){

		return id;

	}

	public void setId(Integer id){

		this.id = id;

	}

	public int getNumeroQuadra(){

		return numeroQuadra;

	}

	public void setNumeroQuadra(int numeroQuadra){

		this.numeroQuadra = numeroQuadra;

	}

	public int getQuantidadeContasEmitidas(){

		return quantidadeContasEmitidas;

	}

	public void setQuantidadeContasEmitidas(int quantidadeContasEmitidas){

		this.quantidadeContasEmitidas = quantidadeContasEmitidas;

	}

	public int getReferencia(){

		return referencia;

	}

	public void setReferencia(int referencia){

		this.referencia = referencia;

	}

	public Date getUltimaAlteracao(){

		return ultimaAlteracao;

	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;

	}

	public Set getUnResumoArrecadacaoCreditos(){

		return unResumoArrecadacaoCreditos;

	}

	public void setUnResumoArrecadacaoCreditos(Set unResumoArrecadacaoCreditos){

		this.unResumoArrecadacaoCreditos = unResumoArrecadacaoCreditos;

	}

	public Set getUnResumoArrecadacaoOutros(){

		return unResumoArrecadacaoOutros;

	}

	public void setUnResumoArrecadacaoOutros(Set unResumoArrecadacaoOutros){

		this.unResumoArrecadacaoOutros = unResumoArrecadacaoOutros;

	}

	public BigDecimal getValorFaturadoAgua(){

		return valorFaturadoAgua;

	}

	public void setValorFaturadoAgua(BigDecimal valorFaturadoAgua){

		this.valorFaturadoAgua = valorFaturadoAgua;

	}

	public int getVolumeFaturadoagua(){

		return volumeFaturadoagua;

	}

	public void setVolumeFaturadoagua(int volumeFaturadoagua){

		this.volumeFaturadoagua = volumeFaturadoagua;

	}

	public int getVolumeFaturadoEsgoto(){

		return volumeFaturadoEsgoto;

	}

	public void setVolumeFaturadoEsgoto(int volumeFaturadoEsgoto){

		this.volumeFaturadoEsgoto = volumeFaturadoEsgoto;

	}

	public BigDecimal getValorFaturadoEsgoto(){

		return valorFaturadoEsgoto;
	}

	public void setValorFaturadoEsgoto(BigDecimal valorFaturadoEsgoto){

		this.valorFaturadoEsgoto = valorFaturadoEsgoto;
	}

	public GCreditoOrigem getGerCreditoOrigem(){

		return gerCreditoOrigem;
	}

	public void setGerCreditoOrigem(GCreditoOrigem gerCreditoOrigem){

		this.gerCreditoOrigem = gerCreditoOrigem;
	}

	public GFinanciamentoTipo getGerFinanciamentoTipo(){

		return gerFinanciamentoTipo;
	}

	public void setGerFinanciamentoTipo(GFinanciamentoTipo gerFinanciamentoTipo){

		this.gerFinanciamentoTipo = gerFinanciamentoTipo;
	}

	public GLancamentoItemContabil getGerLancamentoItemContabil(){

		return gerLancamentoItemContabil;
	}

	public void setGerLancamentoItemContabil(GLancamentoItemContabil gerLancamentoItemContabil){

		this.gerLancamentoItemContabil = gerLancamentoItemContabil;
	}

	public Integer getQuantidadeDocumentosFaturadosCreditos(){

		return quantidadeDocumentosFaturadosCreditos;
	}

	public void setQuantidadeDocumentosFaturadosCreditos(Integer quantidadeDocumentosFaturadosCreditos){

		this.quantidadeDocumentosFaturadosCreditos = quantidadeDocumentosFaturadosCreditos;
	}

	public BigDecimal getValorDocumentosFaturadosCreditos(){

		return valorDocumentosFaturadosCreditos;
	}

	public void setValorDocumentosFaturadosCreditos(BigDecimal valorDocumentosFaturadosCreditos){

		this.valorDocumentosFaturadosCreditos = valorDocumentosFaturadosCreditos;
	}

	public BigDecimal getValorDocumentosFaturadosOutros(){

		return valorDocumentosFaturadosOutros;
	}

	public void setValorDocumentosFaturadosOutros(BigDecimal valorDocumentosFaturadosOutros){

		this.valorDocumentosFaturadosOutros = valorDocumentosFaturadosOutros;
	}

	public Short getQuantidadeDocumentosFaturadosOutros(){

		return quantidadeDocumentosFaturadosOutros;
	}

	public void setQuantidadeDocumentosFaturadosOutros(Short quantidadeDocumentosFaturadosOutros){

		this.quantidadeDocumentosFaturadosOutros = quantidadeDocumentosFaturadosOutros;
	}

	public GEmpresa getGerEmpresa(){

		return gerEmpresa;
	}

	public void setGerEmpresa(GEmpresa gerEmpresa){

		this.gerEmpresa = gerEmpresa;
	}

	public BigDecimal getValorFinanciamentoCancelado(){

		return valorFinanciamentoCancelado;
	}

	public void setValorFinanciamentoCancelado(BigDecimal valorFinanciamentoCancelado){

		this.valorFinanciamentoCancelado = valorFinanciamentoCancelado;
	}

	public BigDecimal getValorFinanciamentoIncluido(){

		return valorFinanciamentoIncluido;
	}

	public void setValorFinanciamentoIncluido(BigDecimal valorFinanciamentoIncluido){

		this.valorFinanciamentoIncluido = valorFinanciamentoIncluido;
	}

	public Integer getQuantidadeEconomiasFaturadas(){

		return quantidadeEconomiasFaturadas;
	}

	public void setQuantidadeEconomiasFaturadas(Integer quantidadeEconomiasFaturadas){

		this.quantidadeEconomiasFaturadas = quantidadeEconomiasFaturadas;
	}

	public GCreditoTipo getGerCreditoTipo(){

		return gerCreditoTipo;
	}

	public void setGerCreditoTipo(GCreditoTipo gerCreditoTipo){

		this.gerCreditoTipo = gerCreditoTipo;
	}

	public GDebitoTipo getGerDebitoTipo(){

		return gerDebitoTipo;
	}

	public void setGerDebitoTipo(GDebitoTipo gerDebitoTipo){

		this.gerDebitoTipo = gerDebitoTipo;
	}

	public GImpostoTipo getGerImpostoTipo(){

		return gerImpostoTipo;
	}

	public void setGerImpostoTipo(GImpostoTipo gerImpostoTipo){

		this.gerImpostoTipo = gerImpostoTipo;
	}

	public BigDecimal getValorImposto(){

		return valorImposto;
	}

	public void setValorImposto(BigDecimal valorImposto){

		this.valorImposto = valorImposto;
	}

	public Short getIndicadorHidrometro(){

		return indicadorHidrometro;
	}

	public void setIndicadorHidrometro(Short indicadorHidrometro){

		this.indicadorHidrometro = indicadorHidrometro;
	}

	public GConsumoTarifa getGerConsumoTarifa(){

		return gerConsumoTarifa;
	}

	public void setGerConsumoTarifa(GConsumoTarifa gerConsumoTarifa){

		this.gerConsumoTarifa = gerConsumoTarifa;
	}
}