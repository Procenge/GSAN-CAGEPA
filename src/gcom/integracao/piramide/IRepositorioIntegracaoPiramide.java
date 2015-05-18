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

package gcom.integracao.piramide;

import gcom.integracao.piramide.bean.LancamentoDeferimentoAnteriorHelper;
import gcom.integracao.piramide.bean.LancamentoDeferimentoHelper;
import gcom.util.ControladorException;
import gcom.util.ErroRepositorioException;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Interface para o reposit�rio de integra��o com as tabela do sistema Pir�mide no GSAN
 * 
 * @author Pitang
 * @created 15 de Agosto de 2012
 */
public interface IRepositorioIntegracaoPiramide {

	Integer SUM_LANCAMENTOS_SINTETICOS = 1;

	Integer SUM_LANCAMENTOS_ANALITICOS = 2;

	/**
	 * [UC3065] Gerar Integra��o Cont�bil
	 * [SB0001] � Obter data inicial de lan�amento para a Integra��o Cont�bil
	 * 
	 * @return dataInicial
	 * @throws ErroRepositorioException
	 */
	public Date obterDataInicialLancamentoIntegracaoContabil() throws ErroRepositorioException;
	
	/**
	 * [UC3066] Gerar Integra��o Reten��o
	 * [SB0001 � Obter data inicial de pagamento para a integra��o de reten��o]
	 * 
	 * @return dataInicial
	 * @throws ControladorException
	 */
	public Date obterDataInicialPagamentoRetencao() throws ErroRepositorioException;


	/**
	 * M�todo obterMaiorReferenciaGerada
	 * <p>
	 * Esse m�todo implementa a busca da maior refer�ncia j� gerada a partir da tabela
	 * TI_DIFER_PERIODO.
	 * </p>
	 * RASTREIO: [OC827869][UC3067][SB0001]
	 * 
	 * @return
	 * @author Marlos Ribeiro
	 * @throws ErroRepositorioException
	 * @since 03/10/2012
	 */
	public String obterMaiorReferenciaGerada() throws ErroRepositorioException;

	/**
	 * M�todo quantaidadeLancamentosDeferimento
	 * <p>
	 * Esse m�todo implementa consulta a quantidade de lan�amentos na tabela TI_DIF_ANT_REC_PER para
	 * a refer�ncia base
	 * </p>
	 * RASTREIO: [OC827869][UC3067][FS0004]
	 * 
	 * @param formatarAnoMesParaMesAno
	 *            Referencia no formato MMYYYY
	 * @return quantidade de lancamentos
	 * @author Marlos Ribeiro
	 * @since 03/10/2012
	 */
	public Integer quantaidadeLancamentosDeferimento(String formatarAnoMesParaMesAno) throws ErroRepositorioException;

	/**
	 * M�todo consultarContasDeferimento
	 * <p>
	 * Esse m�todo implementa consulta de : <br>
	 * contas com refer�ncia igual a refer�ncia base, categoria publica e que o CPF/CGC do
	 * propriet�rio ou inquilino estejam preenchidos a partir da tabela CONTA com
	 * CNTA_AMREFERENCIACONTA igual referencia base; s� exista apenas uma ocorr�ncia na tabela
	 * CONTA_CATEGORIA com CNTA_ID igual a CNTA_ID da tabela CONTA que esta �nica ocorr�ncia seja
	 * com CATG_ID = 4 <br>
	 * <br>
	 * UNIAO com <br>
	 * <br>
	 * contas em hist�rico com refer�ncia igual a refer�ncia base, categoria publica e que o CPF/CGC
	 * do propriet�rio ou inquilino estejam preenchidos a partir da tabela CONTA-HISTORICO com
	 * CNHI_AMREFERENCIACONTA igual referencia base; sejam de situa��o Normal, Inclu�da ou
	 * Retificadas (DCST_IDATUAL igual a 0, 1 ou 2); s� exista apenas uma ocorr�ncia na tabela
	 * CONTA_CATEGORIA_HISTORICO com CNTA_ID igual a CNTA_ID da tabela CONTA_HISTORICO que esta
	 * �nica ocorr�ncia seja com CATG_ID = 4 e que tenham sido pagas depois da data final de
	 * deferimento (PGHI_DTPAGAMENTO > data final deferimento a partir da tabela PAGAMENTO_HISTORICO
	 * com CNTA_ID = CNTA_ID da tabela CONTA_HISTORICO). <br>
	 * <br>
	 * AGRUPAR POR c�digo de regional e CNPJ/CPF
	 * </p>
	 * RASTREIO: [OC827869][UC3067][SB0003.1.1], [SB0003.1.2] e [SB0003.2]
	 * 
	 * @param referenciaBase
	 * @param publico
	 * @param integers
	 * @return
	 * @author Marlos Ribeiro
	 * @since 05/10/2012
	 */
	public List<LancamentoDeferimentoHelper> consultarContasDeferimento(String referenciaBase, Integer categoriaConta,
					Integer[] tiposClienteRelacionamento, Date dataFimDeferimento, Integer[] situacaoConta)
					throws ErroRepositorioException;

	/**
	 * M�todo consultarPercentualAliquota
	 * <p>
	 * Esse m�todo implementa consulta da Percentual mais recente da Al�quota (IMTA_PCALIQUOTA da
	 * tabela IMPOSTO_TIPO_ALIQUOTA com IMTP_ID = tipo e MAX (IMTA_AMREFERENCIA)
	 * </p>
	 * 
	 * @param i
	 * @return
	 * @author Marlos Ribeiro
	 * @throws ErroRepositorioException
	 * @since 05/10/2012
	 */
	public BigDecimal consultarPercentualAliquota(int imtpID) throws ErroRepositorioException;

	/**
	 * M�todo consultaValorNaoRecebido
	 * <p>
	 * Esse m�todo implementa Somat�rio dos Valores das contas para a mesma regional e mesmo CNPJ /
	 * CPF (CNTA_VLAGUA + CNTA_VLESGOTO + CNTA_VLDEBITOS + CNTA_VLCREDITOS a partir da tabela CONTA
	 * com LOCA_ID = LOCA_ID da tabela LOCALIDADE com GERE_ID = COD_FILIAL_ORIGEM e CNTA_ID =
	 * CNTA_ID da tabela CLIENTE_CONTA com CLIE_ID = CLIE_ID da tabela CLIENTE com CLIE_NNCNPJ ou
	 * CLIE_NNCPF = COD_CNPJ)
	 * </p>
	 * RASTREIO: [OC827869][UC3067][SB0003.2][ITEM 16]
	 * 
	 * @param ultimoCodReg
	 * @param ultimoCliDoc
	 * @return
	 * @author Marlos Ribeiro
	 * @since 05/10/2012
	 * @author Josenildo Neves
	 * @since 19/10/2012
	 */
	public BigDecimal consultaValorNaoRecebido(Integer gerenciaRegionalID, String clienteDoc, String referenciaBase,
					Integer[] tiposClienteRelacionamento)
					throws ErroRepositorioException;

	/**
	 * M�todo consultarPagamentosParaDeferimento
	 * RASTREIO: [OC827869][UC3067][SB0004]
	 * 
	 * @param referenciaBase
	 * @param categoriaConta
	 * @param tipoClienteRelacao
	 * @param contaSituacao
	 * @param dataInicioPeriodo
	 * @param dataFimPeriodo
	 * @return
	 * @throws ErroRepositorioException
	 * @author Marlos Ribeiro
	 * @since 08/10/2012
	 */
	public List<LancamentoDeferimentoAnteriorHelper> consultarPagamentosParaDeferimento(Integer referenciaBase, Integer categoriaConta,
					Integer[] tipoClienteRelacao, Integer[] contaSituacao, Date dataInicioPeriodo, Date dataFimPeriodo)
					throws ErroRepositorioException;

	/**
	 * M�todo consultarUltimoSequencialConslDiaNfcl
	 * <p>
	 * Esse m�todo seleciona o �ltimo sequencial dispon�vel da tabela de integra��o
	 * TI_CONSL_DIA_NFCL
	 * </p>
	 * RASTREIO: [OC777360][UC3078][SB002.1]
	 * 
	 * @return COD_SEQ_ORIGEM {@link Integer}.
	 * @author Marlos Ribeiro
	 * @since 14/03/2013
	 */
	public Integer consultarUltimoSequencialConslDiaNfcl() throws ErroRepositorioException;

	/**
	 * M�todo consultarLancamentosContabeis
	 * <p>
	 * Esse m�todo seleciona a rela��o de lan�amentos cont�beis a partir da tabela
	 * LANCAMENTO_CONTABIL_SINTETICO cuja data cont�bil esteja no intervalo das datas iniciais e
	 * finais do Sped .
	 * </p>
	 * RASTREIO: [OC777360][UC3078][SB002.2]
	 * 
	 * @param dtInicio
	 * @param dtFim
	 * @return {@link Collection} de {@link Object[]} seguiondo a defini��o:
	 *         <ol>
	 *         <li>0.Data Cont�bil</li>
	 *         <li>1.ID Localidade</li>
	 *         <li>2.M�dulo Cont�bil</li>
	 *         </ol>
	 * @author Marlos Ribeiro
	 * @param idMunicipio
	 * @since 14/03/2013
	 */
	public Collection<Object[]> consultarModulosPorDiaPorMunicipio(Date dtInicio, Date dtFim, Integer idMunicipio)
					throws ErroRepositorioException;

	/**
	 * M�todo consultarLancamentosContabeisAnaliticos
	 * <p>
	 * Esse m�todo implementa [mgrb] descrever o que implemntar
	 * </p>
	 * RASTREIO: [OCXXXXX][UCXXXX][SBXXXX]
	 * 
	 * @param dtContabil
	 * @param idMunicipio
	 * @param isFinanceiro
	 * @param idsEventos
	 * @return
	 * @throws ErroRepositorioException
	 * @author Marlos Ribeiro
	 * @param tipoLancamentos
	 * @since 15/03/2013
	 */
	public Collection<Object[]> consultarLancamentosContabeis(Date dtContabil, Integer idMunicipio, boolean isFinanceiro, int tipoAcumulo,
					Integer... idsEventos) throws ErroRepositorioException;

	/**
	 * M�todo consultarClassificacaoConta
	 * <p>
	 * Esse m�todo implementa [mgrb] descrever o que implemntar
	 * </p>
	 * RASTREIO: [OCXXXXX][UCXXXX][SBXXXX]
	 * 
	 * @param dtVigencia
	 * @param idCategoria
	 * @param vlConta
	 * @return
	 * @throws ErroRepositorioException
	 * @author Marlos Ribeiro
	 * @since 15/03/2013
	 */
	public Integer consultarClassificacaoLancamento(Date dtVigencia, Integer idCategoria, BigDecimal vlConta) throws ErroRepositorioException;

	/**
	 * M�todo consultarLancamentosContas
	 * <p>
	 * Esse m�todo implementa [mgrb] descrever o que implemntar
	 * </p>
	 * RASTREIO: [OCXXXXX][UCXXXX][SBXXXX]
	 * 
	 * @param idMunicipio
	 * @param dataContabil
	 * @param idsEventosContabil
	 * @return
	 * @author Marlos Ribeiro
	 * @since 21/03/2013
	 */
	public Collection<Object[]> consultarLancamentosContas(Integer idMunicipio, Date dataContabil, Integer[] idsEventosContabil)
					throws ErroRepositorioException;

	/**
	 * M�todo atualizarParaZeroValoresCmDiaNfcl
	 * <p>
	 * Esse m�todo implementa [mgrb] descrever o que implemntar
	 * </p>
	 * RASTREIO: [OCXXXXX][UCXXXX][SBXXXX]
	 * 
	 * @param codigoSequenciaOrigem
	 * @param string
	 * @author Marlos Ribeiro
	 * @param valCofins
	 * @param valBaseCofins
	 * @param valPis
	 * @param valBasePis
	 * @param valItem
	 * @since 22/03/2013
	 */
	public void atualizarValoresCmDiaNfcl(Integer codigoSequenciaOrigem, String string, BigDecimal valItem, BigDecimal valBasePis,
					BigDecimal valPis, BigDecimal valBaseCofins, BigDecimal valCofins) throws ErroRepositorioException;

	/**
	 * M�todo recuperarValorItemTICmConDiaNFCL
	 * <p>
	 * Esse m�todo implementa [mgrb] descrever o que implemntar
	 * </p>
	 * RASTREIO: [OCXXXXX][UCXXXX][SBXXXX]
	 * 
	 * @param codigoSequenciaOrigem
	 * @param string
	 * @return
	 * @author Marlos Ribeiro
	 * @since 22/03/2013
	 */
	public BigDecimal recuperarValorItemTICmConDiaNFCL(Integer codigoSequenciaOrigem, String string) throws ErroRepositorioException;

	public Collection<TabelaIntegracaoConsCanNtfisc> consultaTIConsCanNTFISC(TabelaIntegracaoConsCanNtfiscPk comp_id)
					throws ErroRepositorioException;

	public Object[] consultarDataValorCategoriaOrigemCancelamento(Date dtGeracao, Integer idLocalidade, Integer idObjetoContabil,
					Integer idItemContabil, Integer idCategoria, Integer... idEventosContabeis) throws ErroRepositorioException;
	
	public Collection<Object[]> consultarLancamentosContabeisPorEventoComercial(Date dataContabil, Integer idMunicipio)
					throws ErroRepositorioException;
}
