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

import gcom.cobranca.bean.ParametrosHelper;
import gcom.faturamento.faturamentosimulacaocomando.FaturamentoSimulacaoComando;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ControladorException;
import gcom.util.ErroRepositorioException;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Declara��o p�blica de servi�os do Session Bean de ControladorCliente
 * 
 * @author S�vio Luiz
 * @created 25 de Abril de 2005
 */
public interface ControladorBatchLocal
				extends javax.ejb.EJBLocalObject {

	/**
	 * Insere um processo iniciado no sistema e suas funcionalidades iniciadas
	 * 
	 * @author Rodrigo Silveira
	 * @date 28/07/2006
	 * @param processoIniciado
	 * @throws ControladorException
	 * @throws ControladorException
	 */
	public Integer inserirProcessoIniciado(ProcessoIniciado processoIniciado) throws ControladorException;

	public Integer inserirProcessoIniciadoFaturamentoComandado(Collection<Integer> idsFaturamentoAtividadeCronograma,
					Collection<Integer> idsFaturamentoSimulacaoComando, Date dataHoraAgendamento, Usuario usuario)
					throws ControladorException;

	/**
	 * Verifica no sistema a presenca de ProcessosIniciados nao agendados para
	 * iniciar a execucao
	 * 
	 * @author Rodrigo Silveira
	 * @date 22/08/2006
	 */
	public void verificarProcessosIniciados(Collection<Integer> processosId) throws ControladorException;

	/**
	 * Encerra os Processos Iniciados no sistema quando todas as funcionalidades
	 * do mesmo finalizarem a execu��o
	 * 
	 * @author Rodrigo Silveira
	 * @date 22/08/2006
	 */
	public void encerrarProcessosIniciados() throws ControladorException;

	/**
	 * Encerra as Funcionalidades Iniciadas no sistema quando todas as unidades
	 * de processamento do mesmo finalizarem a execu��o
	 * 
	 * @author Rodrigo Silveira
	 * @date 22/08/2006
	 */
	public void encerrarFuncionalidadesIniciadas() throws ControladorException;

	/**
	 * Inicia a Unidade de Processamento de um processo Batch
	 * 
	 * @author Rodrigo Silveira
	 * @date 22/08/2006
	 * @param idFuncionalidadeIniciada
	 * @param idUnidadeProcessamento
	 * @return
	 */
	public int iniciarUnidadeProcessamentoBatch(int idFuncionalidadeIniciada, int idUnidadeProcessamento, int codigoRealUnidadeProcessamento)
					throws ControladorException;

	/**
	 * Encerra a Unidade de Processamento associada a um processamento batch
	 * 
	 * @author Rodrigo Silveira
	 * @date 24/08/2006
	 * @param idUnidadeIniciada
	 * @param executouComErro
	 */
	public void encerrarUnidadeProcessamentoBatch(int idUnidadeIniciada, boolean executouComErro) throws ControladorException;

	/**
	 * Encerra a Unidade de Processamento associada a um processamento batch
	 * 
	 * @author Rodrigo Silveira
	 * @date 24/08/2006
	 * @param idUnidadeIniciada
	 * @param executouComErro
	 */
	public void encerrarUnidadeProcessamentoBatch(Throwable ex, int idUnidadeIniciada, boolean executouComErro) throws ControladorException;

	/**
	 * Inseri uma cole��o de objetos gen�ricos na base com um flush para cada 50
	 * registros inseridos.
	 * 
	 * @author Pedro Alexandre
	 * @date 11/09/2006
	 * @param colecaoObjetos
	 * @throws ControladorException
	 */
	public void inserirColecaoObjetoParaBatch(Collection<Object> colecaoObjetos) throws ControladorException;

	/**
	 * Inseri uma cole��o de objetos gen�ricos na base com um flush para cada 50
	 * registros inseridos.
	 * 
	 * @author Bruno Barros
	 * @date 26/04/2007
	 * @param colecaoObjetos
	 * @throws ControladorException
	 */
	public void inserirColecaoObjetoParaBatchGerencial(Collection<Object> colecaoObjetos) throws ControladorException;

	/**
	 * Atualiza uma cole��o de objetos gen�ricos na base com um flush para cada
	 * 50 registros inseridos.
	 * 
	 * @author Leonardo Vieira
	 * @date 12/10/2006
	 * @param colecaoObjetos
	 * @throws ControladorException
	 */
	public void atualizarColecaoObjetoParaBatch(Collection<Object> colecaoObjetos) throws ControladorException;

	public void verificadorProcessosSistema() throws ControladorException;

	/**
	 * Inicia uma funcionalidade iniciada do relatorio
	 * 
	 * @author Rodrigo Silveira
	 * @date 26/09/2006
	 * @param idFuncionalidadeIniciada
	 * @throws ControladorException
	 */
	public void iniciarFuncionalidadeIniciadaRelatorio(int idFuncionalidadeIniciada) throws ControladorException;

	/**
	 * Encerra uma funcionalidade iniciada do relatorio
	 * 
	 * @author Rodrigo Silveira
	 * @date 26/09/2006
	 * @param idFuncionalidadeIniciada
	 * @throws ControladorException
	 */
	public void encerrarFuncionalidadeIniciadaRelatorio(int idFuncionalidadeIniciada, boolean concluiuComErro) throws ControladorException;

	/**
	 * Pesquisa todos as funcionalidades iniciadas que representam os relat�rios
	 * batch do sistema
	 * 
	 * @author Rodrigo Silveira
	 * @date 09/10/2006
	 */
	public Collection<Object[]> pesquisarRelatoriosBatchSistema() throws ControladorException;

	/**
	 * Remove uma cole��o de objetos gen�ricos na base com um flush para cada 50
	 * registros removidos.
	 * 
	 * @author Pedro Alexandre
	 * @date 09/10/2006
	 * @param colecaoObjetos
	 * @throws ControladorException
	 */
	public void removerColecaoObjetoParaBatch(Collection<Object> colecaoObjetos) throws ControladorException;

	/**
	 * Inicia um processo relacionado com um relat�rio que ser� processado em
	 * batch
	 * 
	 * @author Rodrigo Silveira
	 * @date 23/10/2006
	 * @throws ControladorException
	 */

	public void iniciarProcessoRelatorio(TarefaRelatorio tarefaRelatorio) throws ControladorException;

	/**
	 * Pesquisa todos as funcionalidades iniciadas que representam os relat�rios
	 * batch do sistema por Usu�rio
	 * 
	 * @author Rodrigo Silveira
	 * @date 25/10/2006
	 */
	public Collection<Object[]> pesquisarRelatoriosBatchPorUsuarioSistema(int idProcesso, int pageOffset) throws ControladorException;

	public Long pesquisarQuantidadeRelatoriosBatchPorUsuarioSistema(int idProcesso) throws ControladorException;

	/**
	 * Remove do sistema todos os relat�rios batch que est�o na data de
	 * expira��o
	 * 
	 * @author Rodrigo Silveira
	 * @date 26/10/2006
	 */
	public void deletarRelatoriosBatchDataExpiracao() throws ControladorException;

	public Integer inserirProcessoIniciadoCobrancaComandado(Collection<Integer> idsCronograma, Collection<Integer> idsEventuais,
					Date dataHoraAgendamento, Usuario usuario, Integer idProcessoIniciadoVinculado,
					Integer idFaturamentoGrupoCronogramaMensal) throws ControladorException;

	/**
	 * Pesquisa no sistema todos os processos que pararam na metade
	 * devido a uma falha no servidor e marca com 'EXECU��O INTERROMPIDA'
	 * 
	 * @author Rodrigo Silveira
	 * @date 27/01/2007
	 */
	public void marcarProcessosInterrompidos() throws ControladorException;

	/**
	 * Inseri uma objeto gen�rico na base
	 * 
	 * @author Marcio Roberto
	 * @date 18/05/2007
	 * @param Objeto
	 * @throws ErroRepositorioException
	 */
	public Object inserirObjetoParaBatchGerencial(Object objeto) throws ControladorException;

	/**
	 * <Breve descri��o sobre o caso de uso>
	 * <Identificador e nome do caso de uso>
	 * 
	 * @author Pedro Alexandre
	 * @date 18/06/2007
	 * @param processoIniciado
	 * @param dadosProcessamento
	 * @return
	 * @throws ControladorException
	 */
	public Integer inserirProcessoIniciado(ProcessoIniciado processoIniciado, Map<String, Object> dadosProcessamento)
					throws ControladorException;

	/**
	 * Reinicia uma funcionalidade iniciada
	 * <Identificador e nome do caso de uso>
	 * 
	 * @author Rafael Corr�a
	 * @date 06/10/2007
	 * @param idsFuncionalidadesIniciadas
	 * @param idProcessoIniciado
	 * @return
	 * @throws ControladorException
	 */
	public void reiniciarFuncionalidadesIniciadas(String[] idsFuncionalidadesIniciadas, Integer idProcessoIniciado)
					throws ControladorException;

	/**
	 * M�todo que retorna o usu�rio que inicializou uma atividade Batch. Normalmente ser� utilizado
	 * por processos comandados que precisam do
	 * usu�rio que 'comandou' para a gera��o de um relat�rio , por exemplo.
	 * 
	 * @author Eduardo Henrique
	 * @date 17/10/2008
	 * @param idFuncionalidadesIniciada
	 *            (id do Processo em Funcionalidade_Iniciada)
	 * @return Usuario
	 * @throws ControladorException
	 */
	public Usuario obterUsuarioFuncionalidadeIniciada(Integer idFuncionalidadeIniciada) throws ControladorException;

	/**
	 * Verifica se o processo est� em execu��o
	 * 
	 * @author Ana Maria
	 * @date 18/12/2008
	 */
	public boolean verificarProcessoEmExecucao(Integer idProcesso) throws ControladorException;

	/**
	 * Inicia um processo relatico a uma funcionalidade.
	 * 
	 * @author wpereira
	 * @date 22/07/2010
	 * @param arquivo
	 * @param usuario
	 * @throws ControladorException
	 */
	public void iniciarProcesso(Integer idFuncionalidade, Object[] parametros) throws ControladorException;

	/**
	 * Encerra um processo relativo a uma funcionalidade
	 * 
	 * @author wpereira
	 * @date 22/07/2010
	 * @param idFuncionalidadeIniciada
	 * @param concluiuComErro
	 * @throws ControladorException
	 */
	public void encerrarProcesso(int idFuncionalidadeIniciada, boolean concluiuComErro) throws ControladorException;

	public void encerrarUnidadeProcessamentoBatchPorFuncionalidadeIniciada(int idUnidadeIniciada, boolean executouComErro,
					int idFuncionalidadeIniciada, ParametrosHelper helper) throws ControladorException;

	/**
	 * [UC0111] Iniciar Processo
	 * [FS0011] � Verificar restri��o de execu��o simult�nea de processos
	 * 
	 * @author Hugo Lima
	 * @date 28/02/2012
	 * @throws ControladorException
	 */
	public boolean isProcessoComRestricaoExecucaoSimultanea(Integer idProcesso) throws ControladorException;

	/**
	 * Insere um processo iniciado no sistema a partir de uma funcionalidade do Online e suas
	 * funcionalidades iniciadas
	 * 
	 * @author Anderson Italo
	 * @date 11/03/2012
	 * @throws ControladorException
	 */
	public Integer inserirProcessoIniciadoOnline(ProcessoIniciado processoIniciado, List<Object> colecaoParametros)
					throws ControladorException;

	/**
	 * Pesquisar Funcionalidade Iniciada filtrando pelo Id do Processo Vinculado
	 * 
	 * @author Hebert Falc�o
	 * @date 30/08/2012
	 */
	public Collection<FuncionalidadeIniciada> pesquisarFuncionalidadeIniciadaPeloProcessoVinculado(Integer idProcessoIniciadoVinculado)
					throws ControladorException;

	/**
	 * [UC0111] - Iniciar Processo
	 * [SB0001] - Iniciar Processo de Faturamento Comandado
	 * 
	 * @author Anderson Italo
	 * @date 28/12/2013
	 */
	public int pesquisarTotalRegistrosFaturamentoSimulacaoComandoNaoRealizados() throws ControladorException;

	/**
	 * [UC0111] - Iniciar Processo
	 * [SB0001] - Iniciar Processo de Faturamento Comandado
	 * 
	 * @author Anderson Italo
	 * @date 28/12/2013
	 */
	public Collection<FaturamentoSimulacaoComando> pesquisarFaturamentoSimulacaoComandoNaoRealizados(Integer numeroPagina)
					throws ControladorException;

}