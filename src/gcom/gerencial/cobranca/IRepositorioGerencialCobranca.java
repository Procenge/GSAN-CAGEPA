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

package gcom.gerencial.cobranca;

import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cobranca.ResumoCobrancaSituacaoEspecial;
import gcom.gerencial.bean.InformarDadosGeracaoRelatorioConsultaHelper;
import gcom.gerencial.bean.InformarDadosGeracaoResumoAcaoConsultaEventualHelper;
import gcom.gerencial.bean.InformarDadosGeracaoResumoAcaoConsultaHelper;
import gcom.gerencial.cobranca.bean.ResumoPendenciaContasGerenciaHelper;
import gcom.gerencial.cobranca.bean.ResumoPendenciaCreditoARealizarGerenciaHelper;
import gcom.gerencial.cobranca.bean.ResumoPendenciaDebitosACobrarGerenciaHelper;
import gcom.gerencial.cobranca.bean.ResumoPendenciaGuiasPagamentoGerenciaHelper;
import gcom.util.ErroRepositorioException;

import java.util.Collection;
import java.util.List;

/**
 * @author Thiago Toscano
 * @created 19/04/2006
 */
public interface IRepositorioGerencialCobranca {

	/**
	 * M�todo que consulta os ResumoSituacaoEspecialCobrancaHelper
	 * 
	 * @author Thiago Toscano
	 * @date 15/05/2006
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List getResumoSituacaoEspecialCobrancaHelper(int idLocalidade) throws ErroRepositorioException;

	/**
	 * M�todo que insere o ResumoSituacaoEspecialCobranca em batch
	 * 
	 * @author Thiago Toscano
	 * @date 15/05/2006
	 * @param listResumoFaturamentoSituacaoEspecialHelper
	 * @throws ErroRepositorioException
	 */
	public void inserirResumoSituacaoEspecialCobranca(List<ResumoCobrancaSituacaoEspecial> list) throws ErroRepositorioException;

	/**
	 * M�todo que exclui todos os ResumoFaturamentoSituacaoEspecial
	 * [CU0346] - Gerar Resumo de Situacao Especial de Cobran�a
	 * 
	 * @author Thiago Toscano
	 * @date 15/05/2006
	 * @throws ErroRepositorioException
	 */
	public void excluirTodosResumoCobrancaSituacaoEspecial(Integer idLocalidade) throws ErroRepositorioException;

	/**
	 * Este caso de uso gera o resumo da pend�ncia
	 * [UC0335] Gerar Resumo da Pend�ncia
	 * Gera a lista de conta da pend�ncia das Contas
	 * gerarResumoPendenciaContas
	 * 
	 * @author Roberta Costa
	 * @date 15/05/2006
	 * @param sistemaParametro
	 * @return retorno
	 * @throws ErroRepositorioException
	 */
	public List getResumoPendenciaContas(SistemaParametro sistemaParametro, Integer idLocalidade, Integer idSetorComercial)
					throws ErroRepositorioException;

	/**
	 * Este caso de uso gera o resumo da pend�ncia
	 * [UC0335] Gerar Resumo da Pend�ncia
	 * Gera a lista de guias de pagamento da pend�ncia das Contas
	 * getResumoPendenciaGuiasPagamento
	 * 
	 * @author Roberta Costa
	 * @date 17/05/2006
	 * @param sistemaParametro
	 * @return retorno
	 * @throws ErroRepositorioException
	 */
	public List getResumoPendenciaGuiasPagamento(SistemaParametro sistemaParametro, Integer idLocalidade, Integer idSetorComercial)
					throws ErroRepositorioException;

	/**
	 * Este caso de uso permite consultar o resumo da pend�ncia, com a op��o de
	 * impress�o da consulta. Dependendo da op��o de totaliza��o sempre � gerado
	 * o relat�rio, sem a fera��o da consulta.
	 * [UC0338] Consultar Resumo da Pend�ncia
	 * Gera a lista de pend�ncias das Contas e Guias de Pagamento
	 * consultarResumoPendencia
	 * 
	 * @author Roberta Costa
	 * @date 24/05/2006
	 * @param informarDadosGeracaoRelatorioConsultaHelper
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List consultarResumoPendencia(InformarDadosGeracaoRelatorioConsultaHelper informarDadosGeracaoRelatorioConsultaHelper)
					throws ErroRepositorioException;

	/**
	 * Este caso de uso permite consultar o resumo da pend�ncia, com a op��o de
	 * impress�o da consulta. Dependendo da op��o de totaliza��o sempre � gerado
	 * o relat�rio, sem a fera��o da consulta.
	 * [UC0338] Consultar Resumo da Pend�ncia
	 * Verifica se existe registros para o ano/m�s refr�ncia
	 * verificarExistenciaAnoMesReferenciaResumo
	 * 
	 * @author Roberta Costa
	 * @date 24/05/2006
	 * @param informarDadosGeracaoRelatorioConsultaHelper
	 * @return Integer
	 * @throws ErroRepositorioException
	 */
	public Integer verificarExistenciaAnoMesReferenciaResumo(Integer anoMesReferencia, String resumo) throws ErroRepositorioException;

	public Collection pesquisarResumoCobrancaSituacaoEspecialConsultaGerenciaRegionalHelper(Integer[] idSituacaoTipo,
					Integer[] idSituacaoMotivo) throws ErroRepositorioException;

	public Collection pesquisarResumoCobrancaSituacaoEspecialConsultaLocalidadeHelper(Integer idGerencia, Integer[] idSituacaoTipo,
					Integer[] idSituacaoMotivo) throws ErroRepositorioException;

	public Collection<ResumoCobrancaSituacaoEspecialConsultaUnidadeNegocioHelper> pesquisarResumoCobrancaSituacaoEspecialConsultaUnidadeNegocioHelper(
					Integer idGerencia, Integer[] idSituacaoTipo,
					Integer[] idSituacaoMotivo) throws ErroRepositorioException;

	public Collection pesquisarResumoCobrancaSituacaoEspecialConsultaSitTipoHelper(Integer idGerencia, Integer IdLocalidade,
					Integer[] idSituacaoTipo, Integer[] idSituacaoMotivo) throws ErroRepositorioException;

	public Collection pesquisarResumoCobrancaSituacaoEspecialConsultaMotivoHelper(Integer idGerencia, Integer idLocalidade, Integer idTipo,
					Integer[] idSituacaoTipo, Integer[] idSituacaoMotivo) throws ErroRepositorioException;

	public Collection pesquisarResumoCobrancaSituacaoEspecialConsultaFatEstimadoHelper(Integer anoMesReferencia, Integer localidade)
					throws ErroRepositorioException;

	public Collection pesquisarResumoCobrancaSituacaoEspecialConsultaQtLigacoesHelper(Integer anoMesReferencia, Integer localidade)
					throws ErroRepositorioException;

	/**
	 * [UC0489] - Consultar Resumo das A��es de Cobran�a
	 * Pesquisa as a��es de cobran�a
	 * 
	 * @author Ana Maria
	 * @date 06/11/2006
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection consultarCobrancaAcao(InformarDadosGeracaoResumoAcaoConsultaHelper informarDadosGeracaoResumoAcaoConsultaHelper)
					throws ErroRepositorioException;

	/**
	 * Pesquisa as situa��es das a��o de cobran�a
	 */
	public Collection consultarCobrancaAcaoSituacao(
					InformarDadosGeracaoResumoAcaoConsultaHelper informarDadosGeracaoResumoAcaoConsultaHelper, Integer idCobrancaAcao)
					throws ErroRepositorioException;

	/**
	 * Pesquisa as situa��es de d�bito da situa��o da a��o
	 */
	public Collection consultarCobrancaAcaoDebito(
					InformarDadosGeracaoResumoAcaoConsultaHelper informarDadosGeracaoResumoAcaoConsultaHelper, Integer idCobrancaAcao,
					Integer idCobrancaAcaoSituacao) throws ErroRepositorioException;

	/**
	 * [UC0489] - Consultar Resumo das A��es de Cobran�a
	 * 
	 * @author Ana Maria
	 * @date 06/11/2006
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection consultarCobrancaAcaoSituacaoPerfilImovel(int anoMesReferencia, Integer idCobrancaAcao,
					Integer idCobrancaAcaoSituacao,
					InformarDadosGeracaoResumoAcaoConsultaHelper informarDadosGeracaoResumoAcaoConsultaHelper)
					throws ErroRepositorioException;

	public Collection consultarCobrancaAcaoDebitoPerfilImovel(int anoMesReferencia, Integer idCobrancaAcao, Integer idCobrancaAcaoSituacao,
					Integer idCobrancaAcaoDebito, Short idIndicador,
					InformarDadosGeracaoResumoAcaoConsultaHelper informarDadosGeracaoResumoAcaoConsultaHelper)
					throws ErroRepositorioException;

	public Collection consultarCobrancaAcaoSituacaoPerfilImovelIndicador(int anoMesReferencia, Integer idCobrancaAcao,
					Integer idCobrancaAcaoSituacao, Integer idPerfil,
					InformarDadosGeracaoResumoAcaoConsultaHelper informarDadosGeracaoResumoAcaoConsultaHelper)
					throws ErroRepositorioException;

	public Collection consultarCobrancaAcaoDebitoPerfilImovelIndicador(int anoMesReferencia, Integer idCobrancaAcao,
					Integer idCobrancaAcaoSituacao, Integer idCobrancaAcaoDebito, Integer idPerfil, Short idIndicador,
					InformarDadosGeracaoResumoAcaoConsultaHelper informarDadosGeracaoResumoAcaoConsultaHelper)
					throws ErroRepositorioException;

	/**
	 * Exclui os dados Resumo de pend�ncia por ano/m�s e localidade
	 * [UC0335] Gerar Resumo da Pend�ncia
	 * 
	 * @author Ana Maria
	 * @date 30/01/2007
	 * @param anoMesReferenciaArrecadacao
	 * @param idLocalidade
	 * @throws ErroRepositorioException
	 */
	public void excluirResumoPendenciaPorAnoMesLocalidade(int anoMesReferencia, Integer idLocalidade) throws ErroRepositorioException;

	/**
	 * [UC0489] - Consultar Resumo das A��es de Cobran�a
	 * Pesquisa as situa��es de d�bito da situa��o da a��o de acordo com o indicador antesApos
	 * 
	 * @author S�vio Luiz
	 * @date 06/11/2006
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection consultarCobrancaAcaoDebitoComIndicador(
					InformarDadosGeracaoResumoAcaoConsultaHelper informarDadosGeracaoResumoAcaoConsultaHelper, Integer idCobrancaAcao,
					Integer idCobrancaAcaoSituacao, short indicadorAntesApos, Integer idCobrancaAcaoDebito) throws ErroRepositorioException;

	/**
	 * [UC0489] - Consultar Resumo das A��es de Cobran�a
	 * Pesquisa as a��es de cobran�a
	 * 
	 * @author S�vio Luiz
	 * @date 21/05/2007
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Integer consultarCobrancaAcaoQuantidadeDocumentos(
					InformarDadosGeracaoResumoAcaoConsultaHelper informarDadosGeracaoResumoAcaoConsultaHelper, Integer idCobrancaAcao)
					throws ErroRepositorioException;

	/**
	 * [UC0617] Consultar Resumo das A��es de Cobran�a Eventuais
	 * 
	 * @author S�vio Luiz
	 * @date 25/06/2007
	 * @param informarDadosGeracaoRelatorioConsultaHelper
	 * @return Integer
	 * @throws ErroRepositorioException
	 */
	public Integer verificarExistenciaResumoEventual(
					InformarDadosGeracaoResumoAcaoConsultaEventualHelper informarDadosGeracaoResumoAcaoConsultaEventualHelper)
					throws ErroRepositorioException;

	/**
	 * [UC0617] Consultar Resumo das A��es de Cobran�a Eventuais
	 * 
	 * @author S�vio Luiz
	 * @date 25/06/2007
	 * @param informarDadosGeracaoRelatorioConsultaHelper
	 * @return Integer
	 * @throws ErroRepositorioException
	 */
	public Collection consultarCobrancaAcaoEventual(
					InformarDadosGeracaoResumoAcaoConsultaEventualHelper informarDadosGeracaoResumoAcaoConsultaEventualHelper)
					throws ErroRepositorioException;

	/**
	 * [UC0489] - Consultar Resumo das A��es de Cobran�a
	 * Pesquisa as a��es de cobran�a
	 * 
	 * @author S�vio Luiz
	 * @date 21/05/2007
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Integer consultarCobrancaAcaoEventualQuantidadeDocumentos(
					InformarDadosGeracaoResumoAcaoConsultaEventualHelper informarDadosGeracaoResumoAcaoConsultaEventualHelper,
					Integer idCobrancaAcao) throws ErroRepositorioException;

	/**
	 * [UC0489] - Consultar Resumo das A��es de Cobran�a
	 * Pesquisa as a��es de cobran�a
	 * 
	 * @author S�vio Luiz
	 * @date 26/06/2007
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection consultarCobrancaAcaoSituacaoEventual(
					InformarDadosGeracaoResumoAcaoConsultaEventualHelper informarDadosGeracaoResumoAcaoConsultaEventualHelper,
					Integer idCobrancaAcao) throws ErroRepositorioException;

	/**
	 * [UC0489] - Consultar Resumo das A��es de Cobran�a
	 * Pesquisa as a��es de cobran�a
	 * 
	 * @author S�vio Luiz
	 * @date 26/06/2007
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection consultarCobrancaAcaoDebitoEventual(
					InformarDadosGeracaoResumoAcaoConsultaEventualHelper informarDadosGeracaoResumoAcaoConsultaEventualHelper,
					Integer idCobrancaAcao, Integer idCobrancaAcaoSituacao) throws ErroRepositorioException;

	/**
	 * [UC0617] Consultar Resumo das A��es de Cobran�a Eventuais
	 * Pesquisa as a��es de cobran�a
	 * 
	 * @author S�vio Luiz
	 * @date 26/06/2007
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection consultarCobrancaAcaoEventualSituacaoPerfilImovel(Integer idCobrancaAcao, Integer idCobrancaAcaoSituacao,
					InformarDadosGeracaoResumoAcaoConsultaEventualHelper informarDadosGeracaoResumoAcaoConsultaEventualHelper)
					throws ErroRepositorioException;

	/**
	 * [UC0617] Consultar Resumo das A��es de Cobran�a Eventuais
	 * Pesquisa as a��es de cobran�a
	 * 
	 * @author S�vio Luiz
	 * @date 26/06/2007
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection consultarCobrancaAcaoEventualSituacaoPerfilImovelIndicador(Integer idCobrancaAcao, Integer idCobrancaAcaoSituacao,
					Integer idPerfil,
					InformarDadosGeracaoResumoAcaoConsultaEventualHelper informarDadosGeracaoResumoAcaoConsultaEventualHelper)
					throws ErroRepositorioException;

	/**
	 * [UC0617] Consultar Resumo das A��es de Cobran�a Eventuais
	 * Pesquisa as a��es de cobran�a
	 * 
	 * @author S�vio Luiz
	 * @date 26/06/2007
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection consultarCobrancaAcaoEventualDebitoPerfilImovel(Integer idCobrancaAcao, Integer idCobrancaAcaoSituacao,
					Integer idCobrancaAcaoDebito, Short idIndicador,
					InformarDadosGeracaoResumoAcaoConsultaEventualHelper informarDadosGeracaoResumoAcaoConsultaEventualHelper)
					throws ErroRepositorioException;

	/**
	 * [UC0617] Consultar Resumo das A��es de Cobran�a Eventuais
	 * Pesquisa as a��es de cobran�a
	 * 
	 * @author S�vio Luiz
	 * @date 26/06/2007
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection consultarCobrancaAcaoEventualDebitoPerfilImovelIndicador(Integer idCobrancaAcao, Integer idCobrancaAcaoSituacao,
					Integer idCobrancaAcaoDebito, Integer idPerfil, Short idIndicador,
					InformarDadosGeracaoResumoAcaoConsultaEventualHelper informarDadosGeracaoResumoAcaoConsultaEventualHelper)
					throws ErroRepositorioException;

	/**
	 * [UC0617] Consultar Resumo das A��es de Cobran�a Eventuais
	 * Pesquisa as a��es de cobran�a
	 * 
	 * @author S�vio Luiz
	 * @date 26/06/2007
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection consultarCobrancaAcaoEventualDebitoComIndicador(
					InformarDadosGeracaoResumoAcaoConsultaEventualHelper informarDadosGeracaoResumoAcaoConsultaEventualHelper,
					Integer idCobrancaAcao, Integer idCobrancaAcaoSituacao, short indicadorAntesApos, Integer idCobrancaAcaoDebito)
					throws ErroRepositorioException;

	/**
	 * O sistema seleciona as contas pendentes ( a partir
	 * da tabela CONTA com CNTA_AMREFERENCIACONTA <
	 * PARM_AMREFERENCIAFATURAMENTO da tabela SISTEMA_PARAMETROS
	 * e ( DCST_IDATUAL = 0 ou (DCST_IDATUAL = (1,2) e
	 * CNTA_AMREFERENCIACONTABIL < PARM_AMREFENRECIAFATURAMENTO
	 * ou (DCST_IDATUAL = (3,4,5) e CNTA_AMREFERENCIACONTABIL >
	 * PARM_AMREFERENCIAFATURAMENTO
	 * 
	 * @author Bruno Barrros
	 * @date 19/07/2007
	 * @param idLocalidade
	 *            id da localidade a ser pesquisado
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List getContasPendentes(int idSetor) throws ErroRepositorioException;

	/**
	 * Seleciona as faixas m�nima e m�xima para a pesquisa de contas pendentes
	 * 
	 * @author Bruno Barros
	 * @date 03/09/2007
	 * @param idLocalidade
	 *            id da localidade a ser pesquisada
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Object[] getFaixaContasPendentes(int idSetor) throws ErroRepositorioException;

	/**
	 * Insere uma linha na tabela un_resumo_pendencia
	 * Metodo criado devido ao aumento de performace
	 * consideravel para o gerencial
	 * 
	 * @param anoMesReferencia
	 * @param helper
	 * @throws ErroRepositorioException
	 */
	public void inserirPendenciaContasGerencia(Integer anoMesReferencia, ResumoPendenciaContasGerenciaHelper helper)
					throws ErroRepositorioException;

	/**
	 * O sistema seleciona as contas pendentes (a partir
	 * da tabela CONTA com CNTA_AMREFERENCIACONTA < PARM_AMREFERENCIAFATURAMENTO
	 * da tabela SISTEMA_PARAMENTOS e DCST_IDATUAL com valor correspondente a normal
	 * ou incluida ou retificada
	 * 
	 * @author Bruno Barrros
	 * @date 01/08/2007
	 * @param idSetor
	 *            id do Setor a ser pesquisado
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List getContasPendentesPorRegiao(int idSetor) throws ErroRepositorioException;

	/**
	 * @author Bruno Barrros
	 * @date 01/08/2007
	 * @param idLocalidade
	 *            id da localidade a ser pesquisada
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List getGuiasPagamentoGerencia(int idLocalidade) throws ErroRepositorioException;

	/**
	 * Retorna as presta��es de um guia de pagamento
	 * 
	 * @author Luciano Galv�o
	 * @date 07/03/2012
	 * @param idGuiaPagamento
	 *            id da guia de pagamento
	 * @throws ErroRepositorioException
	 */
	public List getPrestacoesDeGuiaPagamento(int idGuiaPagamento) throws ErroRepositorioException;

	public void inserirGuiasPagamentoGerencia(Integer anoMesReferencia, ResumoPendenciaGuiasPagamentoGerenciaHelper helper)
					throws ErroRepositorioException;

	/**
	 * M�todo que retorna os valores de presta��es em GUIA_PAGAMENTO_PRESTACAO
	 * 
	 * @author Luciano Galv�o
	 * @date 05/03/2012
	 * @throws ErroRepositorioException
	 */
	public List obterValoresPrestacoesPendentes(Integer idGuiaPagamento) throws ErroRepositorioException;

	/**
	 * @author Bruno Barrros
	 * @date 06/08/2007
	 * @param idLocalidade
	 *            id da localidade a ser pesquisada
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List getDebitosACobrarGerencia(int idLocalidade) throws ErroRepositorioException;

	public void inserirPendendiciaDebitosACobrarGerencia(Integer anoMesReferencia, ResumoPendenciaDebitosACobrarGerenciaHelper helper)
					throws ErroRepositorioException;

	/**
	 * @author Bruno Barrros
	 * @date 07/08/2007
	 * @param idLocalidade
	 *            id da localidade a ser pesquisada
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List getCreditoARealizarGerencia(int idLocalidade) throws ErroRepositorioException;

	public void inserirPendendiciaCreditosARealizerGerencia(Integer anoMesReferencia, ResumoPendenciaCreditoARealizarGerenciaHelper helper)
					throws ErroRepositorioException;

	// /**
	// * @author Marcio Roberto
	// * @date 07/08/2007
	// *
	// * @return
	// * @throws ErroRepositorioException
	// */
	// public BigDecimal getPesquisaDebitoACobrar(int idParc) throws ErroRepositorioException;
	//	
	//	
	// /**
	// * @author Marcio Roberto
	// * @date 08/08/2007
	// *
	// * @return
	// * @throws ErroRepositorioException
	// */
	// public BigDecimal getPesquisaDebitoACobrarTipos(int idConta, int tipoLancamentoItemContabil)
	// throws ErroRepositorioException;

	public Collection consultarResumoCobrancaAcaoEventual(Integer idCobrancaAcao,
					InformarDadosGeracaoResumoAcaoConsultaEventualHelper informarDadosGeracaoResumoAcaoConsultaEventualHelper)
					throws ErroRepositorioException;

}
