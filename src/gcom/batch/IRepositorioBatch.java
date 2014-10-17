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

package gcom.batch;

import gcom.faturamento.faturamentosimulacaocomando.FaturamentoSimulacaoComando;
import gcom.micromedicao.Rota;
import gcom.tarefa.TarefaBatch;
import gcom.util.ErroRepositorioException;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Interface para o reposit�rio batch
 * 
 * @author Rodrigo Silveira
 * @created 15/08/2006
 */
public interface IRepositorioBatch {

	public Collection pesquisarRotasProcessamentoBatchFaturamentoComandado(Integer idFaturamentoAtividadeCronograma)
					throws ErroRepositorioException;

	/**
	 * Encerra os Processos Iniciados no sistema quando todas as funcionalidades
	 * do mesmo finalizarem a execu��o
	 * 
	 * @author Rodrigo Silveira
	 * @date 22/08/2006
	 */
	public Collection<ProcessoIniciado> pesquisarProcessosIniciadosProntosParaEncerramento() throws ErroRepositorioException;

	/**
	 * Encerra as Funcionalidades Iniciadas no sistema quando todas as unidades
	 * de processamento do mesmo finalizarem a execu��o
	 * 
	 * @author Rodrigo Silveira
	 * @date 22/08/2006
	 */
	public Collection<FuncionalidadeIniciada> pesquisarFuncionaldadesIniciadasProntasParaEncerramento() throws ErroRepositorioException;

	/**
	 * Busca as Funcionalidades Iniciadas no sistema que falharam para marcar o
	 * Processo Iniciado como falho
	 * 
	 * @author Rodrigo Silveira
	 * @date 24/08/2006
	 */
	public Collection<ProcessoIniciado> pesquisarProcessosIniciadosExecucaoFalha() throws ErroRepositorioException;

	/**
	 * Busca as Unidades Iniciadas no sistema que falharam para marcar o
	 * Funcionalidade Iniciada como falha
	 * 
	 * @author Rodrigo Silveira
	 * @date 24/08/2006
	 */
	public Collection<FuncionalidadeIniciada> pesquisarFuncionaldadesIniciadasExecucaoFalha() throws ErroRepositorioException;

	/**
	 * Busca as Funcionalidades Iniciadas no sistema que est�o prontas para
	 * execu��o
	 * 
	 * @author Rodrigo Silveira
	 * @param processosId
	 * @date 29/08/2006
	 */
	public Collection<Object[]> pesquisarFuncionaldadesIniciadasProntasExecucao(Collection<Integer> processosId)
					throws ErroRepositorioException;

	/**
	 * Verifica se a FuncionalidadeIniciada foi concluida com erro para evitar a
	 * execu��o da UnidadeIniciada relacionada
	 * 
	 * @author Rodrigo Silveira
	 * @date 01/09/2006
	 */
	public int pesquisarFuncionaldadesIniciadasConcluidasErro(int idFuncionalidadeIniciada) throws ErroRepositorioException;

	/**
	 * Inseri uma cole��o de objetos gen�ricos na base com um flush para cada 50
	 * registros inseridos.
	 * 
	 * @author Pedro Alexandre
	 * @date 11/09/2006
	 * @param colecaoObjetos
	 * @throws ErroRepositorioException
	 */
	public void inserirColecaoObjetoParaBatch(Collection<Object> colecaoObjetos) throws ErroRepositorioException;

	/**
	 * Inseri uma cole��o de objetos gen�ricos na base com um flush para cada 50
	 * registros inseridos.
	 * 
	 * @author Bruno Barros
	 * @date 27/04/2007
	 * @param colecaoObjetos
	 * @throws ErroRepositorioException
	 */
	public void inserirColecaoObjetoParaBatchGerencial(Collection<Object> colecaoObjetos) throws ErroRepositorioException;

	/**
	 * Atualiza uma cole��o de objetos gen�ricos na base com um flush para cada
	 * 50 registros inseridos.
	 * 
	 * @author Leonardo Vieira
	 * @date 12/10/2006
	 * @param colecaoObjetos
	 * @throws ErroRepositorioException
	 */
	public void atualizarColecaoObjetoParaBatch(Collection<Object> colecaoObjetos) throws ErroRepositorioException;

	/**
	 * Verifica se a Funcionalidade Iniciada no sistema que est� na ordem
	 * correta de execu��o dentro do processoFuncionalidade, as funcionalidades
	 * s� podem iniciar se estiverem na ordem correta do sequencial de execu��o
	 * 
	 * @author Rodrigo Silveira
	 * @date 19/09/2006
	 */
	public Integer pesquisarQuantidadeFuncionaldadesIniciadasForaOrdemExecucao(int idSequencialExecucao, int idProcessoIniciado)
					throws ErroRepositorioException;

	/**
	 * Inicia uma funcionalidade iniciada de um relat�rio
	 * 
	 * @author Rodrigo Silveira
	 * @date 26/09/2006
	 */
	public void iniciarFuncionalidadeIniciadaRelatorio(int idFuncionalidadeIniciada) throws ErroRepositorioException;

	/**
	 * Inicia uma processo iniciado de um relat�rio
	 * 
	 * @author Rodrigo Silveira
	 * @date 26/09/2006
	 */
	public void iniciarProcessoIniciadoRelatorio(int idFuncionalidadeIniciada) throws ErroRepositorioException;

	/**
	 * Encerra uma funcionalidade iniciada de um relat�rio
	 * 
	 * @author Rodrigo Silveira
	 * @date 26/09/2006
	 */
	public void encerrarFuncionalidadeIniciadaRelatorio(int idFuncionalidadeIniciada, int situacaoConclusaoFuncionalidade)
					throws ErroRepositorioException;

	/**
	 * Inicia uma processo iniciado de um relat�rio
	 * 
	 * @author Rodrigo Silveira
	 * @date 26/09/2006
	 */
	public void encerrarProcessoIniciadoRelatorio(int idFuncionalidadeIniciada, int situacaoConclusaoFuncionalidade)
					throws ErroRepositorioException;

	/**
	 * Inicia todos os relat�rios agendados
	 * 
	 * @author Rodrigo Silveira
	 * @date 26/09/2006
	 */
	public Collection<byte[]> iniciarRelatoriosAgendados() throws ErroRepositorioException;

	/**
	 * Pesquisa todos as funcionalidades iniciadas que representam os relat�rios
	 * batch do sistema
	 * 
	 * @author Rodrigo Silveira
	 * @date 09/10/2006
	 */
	public Collection<Object[]> pesquisarRelatoriosBatchSistema() throws ErroRepositorioException;

	/**
	 * Remove uma cole��o de objetos gen�ricos na base com um flush para cada 50
	 * registros removidos.
	 * 
	 * @author Pedro Alexandre
	 * @date 09/10/2006
	 * @param colecaoObjetos
	 * @throws ErroRepositorioException
	 */
	public void removerColecaoObjetoParaBatch(Collection<Object> colecaoObjetos) throws ErroRepositorioException;

	/**
	 * Pesquisa todos as funcionalidades iniciadas que representam os relat�rios
	 * batch do sistema por Usu�rio
	 * 
	 * @author Rodrigo Silveira
	 * @date 25/10/2006
	 */
	public Collection<Object[]> pesquisarRelatoriosBatchPorUsuarioSistema(int idProcesso, int pageOffset) throws ErroRepositorioException;

	public Long pesquisarQuantidadeRelatoriosBatchPorUsuarioSistema(int idProcesso) throws ErroRepositorioException;

	/**
	 * Remove do sistema todos os relat�rios batch que est�o na data de
	 * expira��o
	 * 
	 * @author Rodrigo Silveira
	 * @date 26/10/2006
	 */
	public void deletarRelatoriosBatchDataExpiracao(Date dataDeExpiracao) throws ErroRepositorioException;

	public Collection<Rota> pesquisarRotasProcessamentoBatchCobrancaGrupoNaoInformado(Integer idCobrancaAcaoAtividadeComando)
					throws ErroRepositorioException;

	/**
	 * Inseri uma objeto gen�rico na base
	 * 
	 * @author Marcio Roberto
	 * @date 18/05/2007
	 * @param Objeto
	 * @throws ErroRepositorioException
	 */
	public Object inserirObjetoParaBatchGerencial(Object objeto) throws ErroRepositorioException;

	/**
	 * Remove do sistema as unidades iniciadas de uma funcionalidade
	 * 
	 * @author Rafael Corr�a
	 * @date 06/11/2006
	 */
	public void removerUnidadesIniciadas(Integer idFuncionalidadeIniciada) throws ErroRepositorioException;

	public void inserirLogExcecaoFuncionalidadeIniciada(UnidadeIniciada unidadeIniciada, Throwable excecao) throws ErroRepositorioException;

	/**
	 * Verifica se o processo est� em execu��o
	 * 
	 * @author Ana Maria
	 * @date 18/12/2008
	 */
	public boolean verificarProcessoEmExecucao(Integer idProcesso) throws ErroRepositorioException;

	public Collection pesquisarSetorComercialProcessamentoBatchCobrancaGrupoNaoInformado(Integer idCobrancaAcaoAtividadeComando)
					throws ErroRepositorioException;

	public Collection pesquisarSetorComercialProcessamentoBatchGrupoInformado(Integer idGrupo) throws ErroRepositorioException;

	public int pesquisarQuantidadeUnidadesIniciadasPorFuncionalidadeIniciada(int idFuncionalidadeIniciada) throws ErroRepositorioException;

	/**
	 * [UC0111] Iniciar Processo
	 * [FS0011] � Verificar restri��o de execu��o simult�nea de processos
	 * 
	 * @author Hugo Lima
	 * @date 28/02/2012
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarRestricaoExecucaoSimultaneaProcessos(Integer idProcessoIniciar) throws ErroRepositorioException;

	/***
	 * @param idLocalidadeInicial
	 * @param idLocalidadeFinal
	 * @param setorInicial
	 * @param setorFinal
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection obterTodosIDsSetorComercial(Integer idLocalidadeInicial, Integer idLocalidadeFinal, Integer setorInicial,
					Integer setorFinal) throws ErroRepositorioException;

	/**
	 * Pesquisar Funcionalidade Iniciada filtrando pelo Id do Processo Vinculado
	 * 
	 * @author Hebert Falc�o
	 * @date 30/08/2012
	 */
	public Collection<FuncionalidadeIniciada> pesquisarFuncionalidadeIniciadaPeloProcessoVinculado(Integer idProcessoIniciadoVinculado)
					throws ErroRepositorioException;

	/**
	 * M�todo consultarProcessoDepencias
	 * 
	 * @param processoCorrente
	 * @author Marlos Ribeiro
	 * @return
	 * @throws ErroRepositorioException
	 * @since 14/01/2013
	 */
	public List<Object[]> consultarProcessoDepencias(ProcessoIniciado processoCorrente) throws ErroRepositorioException;

	/**
	 * M�todo existeProcessoPendeteEmExecucao
	 * 
	 * @param processoCorrente
	 * @param situacoesProcessoPendente
	 * @return
	 * @author Marlos Ribeiro
	 * @throws ErroRepositorioException
	 * @since 14/01/2013
	 */
	public int consultarQtdProcessoAntecedenteEmSituacao(ProcessoIniciado processoCorrente, List<Integer> situacoesProcessoPendente)
					throws ErroRepositorioException;

	/**
	 * M�todo preencherTarefaBatch
	 * RASTREIO: [OC1000594]
	 * 
	 * @param funcionalidadeIniciadaid
	 * @param tarefa
	 * @author Marlos Ribeiro
	 * @return
	 * @throws ErroRepositorioException
	 * @throws IOException
	 * @since 01/03/2013
	 */
	public int preencherTarefaBatch(Integer funcionalidadeIniciadaid, TarefaBatch tarefa) throws ErroRepositorioException, IOException;

	/**
	 * [UC0111] - Iniciar Processo
	 * [SB0001] - Iniciar Processo de Faturamento Comandado
	 * 
	 * @author Anderson Italo
	 * @date 28/12/2013
	 */
	public int pesquisarTotalRegistrosFaturamentoSimulacaoComandoNaoRealizados() throws ErroRepositorioException;

	/**
	 * [UC0111] - Iniciar Processo
	 * [SB0001] - Iniciar Processo de Faturamento Comandado
	 * 
	 * @author Anderson Italo
	 * @date 28/12/2013
	 */
	public Collection<FaturamentoSimulacaoComando> pesquisarFaturamentoSimulacaoComandoNaoRealizados(Integer numeroPagina)
					throws ErroRepositorioException;
}
