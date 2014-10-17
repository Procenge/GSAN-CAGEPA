/*
 * Copyright (C) 2007-2007 the GSAN ï¿½ Sistema Integrado de Gestï¿½o de Serviï¿½os de Saneamento
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
 * Foundation, Inc., 59 Temple Place ï¿½ Suite 330, Boston, MA 02111-1307, USA
 */

/*
 * GSAN ï¿½ Sistema Integrado de Gestï¿½o de Serviï¿½os de Saneamento
 * Copyright (C) <2007> 
 * Adriano Britto Siqueira
 * Alexandre Santos Cabral
 * Ana Carolina Alves Breda
 * Ana Maria Andrade Cavalcante
 * Aryed Lins de Araï¿½jo
 * Bruno Leonardo Rodrigues Barros
 * Carlos Elmano Rodrigues Ferreira
 * Clï¿½udio de Andrade Lira
 * Denys Guimarï¿½es Guenes Tavares
 * Eduardo Breckenfeld da Rosa Borges
 * Fabï¿½ola Gomes de Araï¿½jo
 * Flï¿½vio Leonardo Cavalcanti Cordeiro
 * Francisco do Nascimento Jï¿½nior
 * Homero Sampaio Cavalcanti
 * Ivan Sï¿½rgio da Silva Jï¿½nior
 * Josï¿½ Edmar de Siqueira
 * Josï¿½ Thiago Tenï¿½rio Lopes
 * Kï¿½ssia Regina Silvestre de Albuquerque
 * Leonardo Luiz Vieira da Silva
 * Mï¿½rcio Roberto Batista da Silva
 * Maria de Fï¿½tima Sampaio Leite
 * Micaela Maria Coelho de Araï¿½jo
 * Nelson Mendonï¿½a de Carvalho
 * Newton Morais e Silva
 * Pedro Alexandre Santos da Silva Filho
 * Rafael Corrï¿½a Lima e Silva
 * Rafael Francisco Pinto
 * Rafael Koury Monteiro
 * Rafael Palermo de Araï¿½jo
 * Raphael Veras Rossiter
 * Roberto Sobreira Barbalho
 * Rodrigo Avellar Silveira
 * Rosana Carvalho Barbosa
 * Sï¿½vio Luiz de Andrade Cavalcante
 * Tai Mu Shih
 * Thiago Augusto Souza do Nascimento
 * Tiago Moreno Rodrigues
 * Vivianne Barbosa Sousa
 *
 * Este programa ï¿½ software livre; vocï¿½ pode redistribuï¿½-lo e/ou
 * modificï¿½-lo sob os termos de Licenï¿½a Pï¿½blica Geral GNU, conforme
 * publicada pela Free Software Foundation; versï¿½o 2 da
 * Licenï¿½a.
 * Este programa ï¿½ distribuï¿½do na expectativa de ser ï¿½til, mas SEM
 * QUALQUER GARANTIA; sem mesmo a garantia implï¿½cita de
 * COMERCIALIZAï¿½ï¿½O ou de ADEQUAï¿½ï¿½O A QUALQUER PROPï¿½SITO EM
 * PARTICULAR. Consulte a Licenï¿½a Pï¿½blica Geral GNU para obter mais
 * detalhes.
 * Vocï¿½ deve ter recebido uma cï¿½pia da Licenï¿½a Pï¿½blica Geral GNU
 * junto com este programa; se nï¿½o, escreva para Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 * 02111-1307, USA.
 */

package gcom.batch;
import gcom.arrecadacao.ControladorArrecadacaoLocal;
import gcom.arrecadacao.ControladorArrecadacaoLocalHome;
import gcom.batch.arrecadacao.*;
import gcom.batch.cadastro.*;
import gcom.batch.cobranca.*;
import gcom.batch.cobranca.administrativa.TarefaBatchMarcarItensRemuneraveisCobrancaAdministrativa;
import gcom.batch.cobranca.spcserasa.*;
import gcom.batch.contabil.TarefaBatchProvisaoDevedoresDuvidosos;
import gcom.batch.faturamento.*;
import gcom.batch.financeiro.*;
import gcom.batch.gerencial.arrecadacao.TarefaBatchGerarResumoArrecadacao;
import gcom.batch.gerencial.cadastro.*;
import gcom.batch.gerencial.cobranca.TarefaBatchGerarResumoPendencia;
import gcom.batch.gerencial.cobranca.TarefaBatchGerarResumoSituacaoEspecialCobranca;
import gcom.batch.gerencial.micromedicao.TarefaBatchGerarResumoAnormalidadeConsumo;
import gcom.batch.gerencial.micromedicao.TarefaBatchGerarResumoAnormalidades;
import gcom.batch.gerencial.micromedicao.TarefaBatchGerarResumoInstalacoesHidrometros;
import gcom.batch.gerencial.micromedicao.TarefaBatchGerarResumoLeituraAnormalidade;
import gcom.batch.integracao.*;
import gcom.batch.micromedicao.*;
import gcom.cadastro.ControladorCadastroLocal;
import gcom.cadastro.ControladorCadastroLocalHome;
import gcom.cadastro.localidade.*;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cobranca.*;
import gcom.cobranca.bean.ParametrosHelper;
import gcom.fachada.Fachada;
import gcom.faturamento.*;
import gcom.faturamento.faturamentosimulacaocomando.FaturamentoSimulacaoComando;
import gcom.faturamento.faturamentosimulacaocomando.FiltroFaturamentoSimulacaoComando;
import gcom.financeiro.ControladorFinanceiroLocal;
import gcom.financeiro.ControladorFinanceiroLocalHome;
import gcom.gerencial.atendimentopublico.registroatendimento.TarefaBatchGerarResumoRegistroAtendimento;
import gcom.gerencial.faturamento.ControladorGerencialFaturamentoLocal;
import gcom.gerencial.faturamento.ControladorGerencialFaturamentoLocalHome;
import gcom.gerencial.micromedicao.ControladorGerencialMicromedicaoLocal;
import gcom.gerencial.micromedicao.ControladorGerencialMicromedicaoLocalHome;
import gcom.gerencial.micromedicao.TarefaBatchGerarResumoHidrometro;
import gcom.integracao.acquagis.IRepositorioIntegracaoAcquaGis;
import gcom.integracao.acquagis.RepositorioIntegracaoAcquaGisHBM;
import gcom.micromedicao.*;
import gcom.micromedicao.hidrometro.FiltroHidrometroMarca;
import gcom.micromedicao.hidrometro.HidrometroMarca;
import gcom.relatorio.GerenciadorExecucaoTarefaRelatorio;
import gcom.relatorio.cobranca.RelatorioAvisoCorte;
import gcom.relatorio.cobranca.RelatorioAvisoDebito;
import gcom.relatorio.faturamento.RelatorioContaTipo2;
import gcom.seguranca.acesso.Funcionalidade;
import gcom.seguranca.acesso.FuncionalidadeCorrente;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.spcserasa.ControladorSpcSerasaLocal;
import gcom.spcserasa.ControladorSpcSerasaLocalHome;
import gcom.tarefa.Tarefa;
import gcom.tarefa.TarefaBatch;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.*;
import gcom.util.agendadortarefas.VerificadorIP;
import gcom.util.filtro.ParametroSimples;
import gcom.util.filtro.ParametroSimplesColecao;
import gcom.util.parametrizacao.batch.ParametroBatch;
import gcom.util.parametrizacao.cobranca.ParametroCobranca;
import gcom.util.parametrizacao.micromedicao.ParametroMicromedicao;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import org.apache.log4j.Logger;


/**
 * Controlador que possui os metodos de negocio de toda a parte que da suporte
 * ao batch
 * 
 * @author Rodrigo Silveira
 * @date 28/07/2006
 */
public class ControladorBatchSEJB
				implements SessionBean {
	private static final Logger LOGGER = Logger.getLogger(ControladorBatchSEJB.class);

	private static final List<Integer> SITUACOES_PROCESSO_PENDENTE = Arrays.asList(new Integer[] {//
					ProcessoSituacao.AGENDADO, //
					ProcessoSituacao.EM_ESPERA, //
					ProcessoSituacao.EM_PROCESSAMENTO, //
					ProcessoSituacao.CONCLUIDO_COM_ERRO, //
					ProcessoSituacao.INICIO_A_COMANDAR});

	SessionContext sessionContext;

	private IRepositorioBatch repositorioBatch = null;

	private IRepositorioMicromedicao repositorioMicromedicao = null;

	private IRepositorioIntegracaoAcquaGis repositorioIntegracaoAcquaGis = null;

	public void ejbCreate() throws CreateException{

		repositorioBatch = RepositorioBatchHBM.getInstancia();
		repositorioMicromedicao = RepositorioMicromedicaoHBM.getInstancia();
		repositorioIntegracaoAcquaGis = RepositorioIntegracaoAcquaGisHBM.getInstancia();
	}

	/**
	 * < <Descriï¿½ï¿½o do mï¿½todo>>
	 */
	public void ejbRemove(){

	}

	/**
	 * < <Descriï¿½ï¿½o do mï¿½todo>>
	 */
	public void ejbActivate(){

	}

	/**
	 * < <Descriï¿½ï¿½o do mï¿½todo>>
	 */
	public void ejbPassivate(){

	}

	/**
	 * Seta o valor de sessionContext
	 * 
	 * @param sessionContext
	 *            O novo valor de sessionContext
	 */
	public void setSessionContext(SessionContext sessionContext){

		this.sessionContext = sessionContext;
	}

	/**
	 * Insere um processo iniciado no sistema e suas funcionalidades iniciadas
	 * 
	 * @author Rodrigo Silveira
	 * @date 28/07/2006
	 * @param processoIniciado
	 * @throws ControladorException
	 * @throws ControladorException
	 */
	public Integer inserirProcessoIniciado(ProcessoIniciado processoIniciado) throws ControladorException{

		Date agora = new Date();

		Integer codigoProcessoIniciadoGerado = null;

		SistemaParametro sistemaParametros = getControladorUtil().pesquisarParametrosDoSistema();

		Integer anoMesFaturamentoSistemaParametro = sistemaParametros.getAnoMesFaturamento();

		/** Coleï¿½ï¿½o de ids de localidades para encerrar a arrecadaï¿½ï¿½o do mï¿½s */
		Collection<Integer> colecaoIdsLocalidadesEncerrarArrecadacaoMes = getControladorArrecadacao()
						.pesquisarIdsLocalidadeComPagamentosOuDevolucoes();

		try{

			// Todos os processo serï¿½o iniciados com a situaï¿½ï¿½o EM_ESPERA para q
			// sejam executados o mais cedo possï¿½vel
			ProcessoSituacao processoSituacao = new ProcessoSituacao();
			processoSituacao.setId(ProcessoSituacao.EM_ESPERA);
			processoIniciado.setProcessoSituacao(processoSituacao);
			// processoIniciado.setDataHoraInicio(agora);
			processoIniciado.setDataHoraComando(agora);
			// processoIniciado.setDataHoraAgendamento(agora);

			codigoProcessoIniciadoGerado = (Integer) getControladorUtil().inserir(processoIniciado);

			// Este trecho pesquisa todos do processoFuncionalidade relacionados
			// com o processo do objeto a ser inserido
			FiltroProcessoFuncionalidade filtroProcessoFuncionalidade = new FiltroProcessoFuncionalidade();

			filtroProcessoFuncionalidade.adicionarParametro(new ParametroSimples(FiltroProcessoFuncionalidade.ID_PROCESSO, processoIniciado
							.getProcesso().getId()));
			filtroProcessoFuncionalidade.adicionarParametro(new ParametroSimples(FiltroProcessoFuncionalidade.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			filtroProcessoFuncionalidade.setCampoOrderBy(FiltroProcessoFuncionalidade.SEQUENCIAL_EXECUCAO);

			Collection processosFuncionaliadade = getControladorUtil().pesquisar(filtroProcessoFuncionalidade,
							ProcessoFuncionalidade.class.getName());

			FuncionalidadeSituacao funcionalidadeSituacao = new FuncionalidadeSituacao();
			funcionalidadeSituacao.setId(FuncionalidadeSituacao.EM_ESPERA);

			Iterator iterator = processosFuncionaliadade.iterator();
			while(iterator.hasNext()){
				ProcessoFuncionalidade processoFuncionalidade = (ProcessoFuncionalidade) iterator.next();
				FuncionalidadeIniciada funcionalidadeIniciada = new FuncionalidadeIniciada();

				funcionalidadeIniciada.setFuncionalidadeSituacao(funcionalidadeSituacao);

				funcionalidadeIniciada.setProcessoIniciado(processoIniciado);

				funcionalidadeIniciada.setProcessoFuncionalidade(processoFuncionalidade);

				funcionalidadeIniciada.setId((Integer) getControladorUtil().inserir(funcionalidadeIniciada));

				// Seta os parametros da funcionalidadeIniciada

				// ----------------------------------------------------------
				// Lista dos possï¿½veis processos eventuais ou mensais
				// ----------------------------------------------------------
				try{

					switch(funcionalidadeIniciada.getProcessoFuncionalidade().getFuncionalidade().getId()){

						case Funcionalidade.MARCAR_ITENS_REMUNERAVEIS_COBRANCA_ADMINISTRATIVA:
							TarefaBatchMarcarItensRemuneraveisCobrancaAdministrativa marcarItensRemuneraveis = new TarefaBatchMarcarItensRemuneraveisCobrancaAdministrativa(
											processoIniciado.getUsuario(), funcionalidadeIniciada.getId());

							funcionalidadeIniciada.setTarefaBatch(IoUtil.transformarObjetoParaBytes(marcarItensRemuneraveis));

							getControladorUtil().atualizar(funcionalidadeIniciada);

							break;
						case Funcionalidade.GERAR_PROVISAO_DEVEDORES_DUVIDOSOS:
							TarefaBatchProvisaoDevedoresDuvidosos provisaoDevedoresDuvidosos = new TarefaBatchProvisaoDevedoresDuvidosos(
											processoIniciado.getUsuario(), funcionalidadeIniciada.getId());

							funcionalidadeIniciada.setTarefaBatch(IoUtil.transformarObjetoParaBytes(provisaoDevedoresDuvidosos));

							getControladorUtil().atualizar(funcionalidadeIniciada);

							break;
						case Funcionalidade.GERAR_RESUMO_DIARIO_NEGATIVACAO:
							TarefaBatchGerarResumoDiarioNegativacao gerarResumoDiarioNegativacao = new TarefaBatchGerarResumoDiarioNegativacao(
											processoIniciado.getUsuario(), funcionalidadeIniciada.getId());

							// Collection colRotas =
							// getControladorSpcSerasa().consultarRotasParaGerarResumoDiarioNegativacao();

							// [UC0688] Gerar Resumo Diário da Negativação.
							// -------------------------------------------------------------------------------------------
							// Alterado por : Yara Taciane - data : 08/07/2008
							// Analista : Fátima Sampaio
							// -------------------------------------------------------------------------------------------

							// 2.0 O sistema exclui o resumo diário das negativações da penúltima
							// execução
							// (exclui as linhas da tabela RESUMO_NEGATIVACAO com
							// RNEG_NNEXECUCAORESUMONEGATIVACAO=PARM_NNEXECUCAORESUMONEGATIVACAO
							// da tabela SISTEMA_PARAMETROS menos um.
							// comantado por Vivianne Sousa 03/11/2009 - adicionado no Gerar Resumo
							// Negativação
							// Integer penultimaExecucaoResumo =
							// sistemaParametros.getNumeroExecucaoResumoNegativacao() - 1;
							// getControladorSpcSerasa().apagarResumoNegativacao(penultimaExecucaoResumo);

							// gerarResumoDiarioNegativacao.addParametro(ConstantesSistema.COLECAO_UNIDADES_PROCESSAMENTO_BATCH,
							// colRotas);

							// Seta o objeto para ser serializado no banco, onde
							// depois serï¿? executado por uma thread
							funcionalidadeIniciada.setTarefaBatch(IoUtil.transformarObjetoParaBytes(gerarResumoDiarioNegativacao));

							getControladorUtil().atualizar(funcionalidadeIniciada);

							break;
						case Funcionalidade.DETERMINAR_CONFIRMACAO_NEGATIVACAO:
							TarefaBatchDeterminarConfirmacaoNegativacao determinarConfirmacaoNegativacao = new TarefaBatchDeterminarConfirmacaoNegativacao(
											processoIniciado.getUsuario(), funcionalidadeIniciada.getId());

							// determinarConfirmacaoNegativacao.addParametro(ConstantesSistema.COLECAO_UNIDADES_PROCESSAMENTO_BATCH);

							// Seta o objeto para ser serializado no banco, onde
							// depois será executado por uma thread
							funcionalidadeIniciada.setTarefaBatch(IoUtil.transformarObjetoParaBytes(determinarConfirmacaoNegativacao));

							getControladorUtil().atualizar(funcionalidadeIniciada);

							break;

						/** Pedro Alexandre */
						case Funcionalidade.GERAR_DADOS_DIARIOS_ARRECADACAO:
							TarefaBatchGerarDadosDiariosArrecadacao dadosArrecadacao = new TarefaBatchGerarDadosDiariosArrecadacao(
											processoIniciado.getUsuario(), funcionalidadeIniciada.getId());

							// recupera o ano/mês de referência da arrecadação int
							int anoMesArrecadacaoSistemaParametro = sistemaParametros.getAnoMesArrecadacao();
							Collection<Integer> colecaoIdsLocalidadesGerarDadosDiariosArrecadacao = getControladorArrecadacao()
											.pesquisarIdsLocalidadeComPagamentosOuPagamentosHistoricos(anoMesArrecadacaoSistemaParametro);

							// Seta os parametros para rodar a funcionalidade
							dadosArrecadacao.addParametro(ConstantesSistema.COLECAO_UNIDADES_PROCESSAMENTO_BATCH,
											colecaoIdsLocalidadesGerarDadosDiariosArrecadacao);

							// Seta o objeto para ser serializado no banco, onde
							// depois serï¿½ executado por uma thread
							funcionalidadeIniciada.setTarefaBatch(IoUtil.transformarObjetoParaBytes(dadosArrecadacao));

							getControladorUtil().atualizar(funcionalidadeIniciada);

							break;

						/** Pedro Alexandre */
						case Funcionalidade.GERAR_DEBITOS_A_COBRAR_ACRESCIMOS_IMPONTUALIDADE:
							TarefaBatchGerarDebitosACobrarAcrescimosImpontualidade impontualidade = new TarefaBatchGerarDebitosACobrarAcrescimosImpontualidade(
											processoIniciado.getUsuario(), funcionalidadeIniciada.getId());

							LOGGER.info("ENCERRAR ARRECADACAO DO MÊS");
							// ENCERRAR ARRECADACAO DO Mï¿½S
							Collection colecaoTodasRotas = getControladorMicromedicao().pesquisarListaRotasCarregadas();

							impontualidade.addParametro(ConstantesSistema.COLECAO_UNIDADES_PROCESSAMENTO_BATCH, colecaoTodasRotas);

							impontualidade.addParametro("indicadorGeracaoMulta", ConstantesSistema.SIM);

							impontualidade.addParametro("indicadorGeracaoJuros", ConstantesSistema.SIM);

							impontualidade.addParametro("indicadorGeracaoAtualizacao", ConstantesSistema.SIM);

							impontualidade.addParametro("indicadorEncerrandoArrecadacao", true);

							// Seta o objeto para ser serializado no banco, onde
							// depois serï¿½ executado por uma thread
							funcionalidadeIniciada.setTarefaBatch(IoUtil.transformarObjetoParaBytes(impontualidade));

							getControladorUtil().atualizar(funcionalidadeIniciada);

							break;

						/** ****** INICIO ENCERRAR ARRECADAï¿½ï¿½O DO Mï¿½S ** */
						/** Pedro Alexandre */
						case Funcionalidade.ENCERRAR_ARRECADACAO_MES:
							TarefaBatchEncerrarArrecadacaoMes dadosEncerrarArrecadacaoMes = new TarefaBatchEncerrarArrecadacaoMes(
											processoIniciado.getUsuario(), funcionalidadeIniciada.getId());

							// Seta os parametros para rodar a funcionalidade
							dadosEncerrarArrecadacaoMes.addParametro(ConstantesSistema.COLECAO_UNIDADES_PROCESSAMENTO_BATCH,
											colecaoIdsLocalidadesEncerrarArrecadacaoMes);

							// Seta o objeto para ser serializado no banco, onde
							// depois serï¿½ executado por uma thread
							funcionalidadeIniciada.setTarefaBatch(IoUtil.transformarObjetoParaBytes(dadosEncerrarArrecadacaoMes));

							getControladorUtil().atualizar(funcionalidadeIniciada);

							break;

						/** Pedro Alexandre */
						case Funcionalidade.GERAR_HISTORICO_PARA_ENCERRAR_ARRECADACAO_MES:
							TarefaBatchGerarHistoricoParaEncerrarArrecadacaoMes dadosGerarHistoricoEncerrarArrecadacaoMes = new TarefaBatchGerarHistoricoParaEncerrarArrecadacaoMes(
											processoIniciado.getUsuario(), funcionalidadeIniciada.getId());

							/**
							 * Coleï¿½ï¿½o de ids de localidades para encerrar a arrecadaï¿½ï¿½o do
							 * mï¿½s
							 */
							/*
							 * Collection<Integer> colecaoIdsSetoresEncerrarArrecadacaoMes =
							 * 
							 * getControladorArrecadacao().pesquisarIdsSetoresComPagamentosOuDevolucoes
							 * ();
							 */

							// Seta os parametros para rodar a funcionalidade
							dadosGerarHistoricoEncerrarArrecadacaoMes.addParametro(ConstantesSistema.COLECAO_UNIDADES_PROCESSAMENTO_BATCH,
											colecaoIdsLocalidadesEncerrarArrecadacaoMes);

							dadosGerarHistoricoEncerrarArrecadacaoMes.addParametro("anoMesReferenciaArrecadacao",
											anoMesFaturamentoSistemaParametro);

							// Seta o objeto para ser serializado no banco, onde
							// depois serï¿½ executado por uma thread
							funcionalidadeIniciada.setTarefaBatch(IoUtil
											.transformarObjetoParaBytes(dadosGerarHistoricoEncerrarArrecadacaoMes));

							getControladorUtil().atualizar(funcionalidadeIniciada);

							break;

						/** Pedro Alexandre */
						case Funcionalidade.GERAR_HISTORICO_CONTA:
							TarefaBatchGerarHistoricoConta dadosGerarHistoricoConta = new TarefaBatchGerarHistoricoConta(processoIniciado
											.getUsuario(), funcionalidadeIniciada.getId());

							/**
							 * Coleï¿½ï¿½o de ids de localidades para encerrar a arrecadaï¿½ï¿½o do
							 * mï¿½s
							 */
							Collection<Integer> colecaoIdsSetoresEncerrarArrecadacaoMes = getControladorArrecadacao()
											.pesquisarIdsSetoresComPagamentosOuDevolucoes();

							// Seta os parametros para rodar a funcionalidade
							dadosGerarHistoricoConta.addParametro(ConstantesSistema.COLECAO_UNIDADES_PROCESSAMENTO_BATCH,
											colecaoIdsSetoresEncerrarArrecadacaoMes);

							dadosGerarHistoricoConta.addParametro("anoMesReferenciaArrecadacao", anoMesFaturamentoSistemaParametro);

							// Seta o objeto para ser serializado no banco, onde
							// depois serï¿½ executado por uma thread
							funcionalidadeIniciada.setTarefaBatch(IoUtil.transformarObjetoParaBytes(dadosGerarHistoricoConta));

							getControladorUtil().atualizar(funcionalidadeIniciada);

							break;
						/** ****** FIM ENCERRAR ARRECADAï¿½ï¿½O DO Mï¿½S ***** */

						/** ****** INICIO ENCERRAR FATURAMENTO DO Mï¿½S ***** */
						/** Pedro Alexandre */
						case Funcionalidade.ENCERRAR_FATURAMENTO_MES:
							TarefaBatchEncerrarFaturamentoMes dadosEncerrarFaturamentoMes = new TarefaBatchEncerrarFaturamentoMes(
											processoIniciado.getUsuario(), funcionalidadeIniciada.getId());

							Collection<Integer> colecaoIdsLocalidadesEncerrarFaturamentoMes = getControladorFaturamento()
											.pesquisarIdsLocalidadeParaEncerrarFaturamento();

							// Seta os parametros para rodar a funcionalidade
							dadosEncerrarFaturamentoMes.addParametro(ConstantesSistema.COLECAO_UNIDADES_PROCESSAMENTO_BATCH,
											colecaoIdsLocalidadesEncerrarFaturamentoMes);

							// Seta o objeto para ser serializado no banco, onde
							// depois serï¿½ executado por uma thread
							funcionalidadeIniciada.setTarefaBatch(IoUtil.transformarObjetoParaBytes(dadosEncerrarFaturamentoMes));

							getControladorUtil().atualizar(funcionalidadeIniciada);

							break;

						/** Pedro Alexandre */
						case Funcionalidade.GERAR_HISTORICO_PARA_ENCERRAR_FATURAMENTO_MES:
							TarefaBatchGerarHistoricoParaEncerrarFaturamentoMes dadosGerarHistoricoParaEncerrarFaturamentoMes = new TarefaBatchGerarHistoricoParaEncerrarFaturamentoMes(
											processoIniciado.getUsuario(), funcionalidadeIniciada.getId());

							Collection<Integer> colecaoIdsSetoresEncerrarFaturamentoMes = getControladorArrecadacao()
											.pesquisarIdsSetoresComPagamentosOuDevolucoes();

							dadosGerarHistoricoParaEncerrarFaturamentoMes
											.addParametro(ConstantesSistema.COLECAO_UNIDADES_PROCESSAMENTO_BATCH,
															colecaoIdsSetoresEncerrarFaturamentoMes);

							dadosGerarHistoricoParaEncerrarFaturamentoMes.addParametro("anoMesFaturamentoSistemaParametro",
											anoMesFaturamentoSistemaParametro);

							// Seta o objeto para ser serializado no banco, onde
							// depois serï¿½ executado por uma thread
							funcionalidadeIniciada.setTarefaBatch(IoUtil
											.transformarObjetoParaBytes(dadosGerarHistoricoParaEncerrarFaturamentoMes));

							getControladorUtil().atualizar(funcionalidadeIniciada);

							break;

						case Funcionalidade.BATCH_GERAR_RESUMO_LIGACOES_ECONOMIAS:
							TarefaBatchGerarResumoLigacoesEconomias gerarResumoLigacoesEconomias = new TarefaBatchGerarResumoLigacoesEconomias(
											processoIniciado.getUsuario(), funcionalidadeIniciada.getId());

							String listaReferencias = ParametroMicromedicao.P_GERAR_RESUMO_LIGACOES_ECONOMIA_REFERENCIAS.executar();

							if(Util.isVazioOuBranco(listaReferencias)){
								LOGGER.error("Parametro 'P_GERAR_RESUMO_LIGACOES_ECONOMIA_REFERENCIAS' não está definido na base de dados.");
							}

							FiltroRota filtroRotaResumo = new FiltroRota();
							filtroRotaResumo.adicionarParametro(new ParametroSimples(FiltroRota.INDICADOR_USO, ConstantesSistema.SIM));
							Collection colecaoRotaResumo = getControladorUtil().pesquisar(filtroRotaResumo, Rota.class.getName());

							gerarResumoLigacoesEconomias.addParametro("listaReferencias", listaReferencias);
							gerarResumoLigacoesEconomias.addParametro(ConstantesSistema.COLECAO_UNIDADES_PROCESSAMENTO_BATCH,
											colecaoRotaResumo);

							// Seta o objeto para ser serializado no banco, onde
							// depois serï¿½ executado por uma thread
							funcionalidadeIniciada.setTarefaBatch(IoUtil.transformarObjetoParaBytes(gerarResumoLigacoesEconomias));

							getControladorUtil().atualizar(funcionalidadeIniciada);

							break;

						case Funcionalidade.GERAR_RESUMO_HIDROMETRO:
							TarefaBatchGerarResumoHidrometro gerarResumoHidrometro = new TarefaBatchGerarResumoHidrometro(processoIniciado
											.getUsuario(), funcionalidadeIniciada.getId());

							FiltroHidrometroMarca filtroHidrometro = new FiltroHidrometroMarca();
							Collection colMarca = getControladorUtil().pesquisar(filtroHidrometro, HidrometroMarca.class.getName());

							gerarResumoHidrometro.addParametro(ConstantesSistema.COLECAO_UNIDADES_PROCESSAMENTO_BATCH, colMarca);

							// Seta o objeto para ser serializado no banco, onde
							// depois serï¿½ executado por uma thread
							funcionalidadeIniciada.setTarefaBatch(IoUtil.transformarObjetoParaBytes(gerarResumoHidrometro));

							getControladorUtil().atualizar(funcionalidadeIniciada);

							break;

						case Funcionalidade.GERAR_RESUMO_REGISTRO_ATENDIMENTO:
							TarefaBatchGerarResumoRegistroAtendimento gerarResumoRegistroAtendimento = new TarefaBatchGerarResumoRegistroAtendimento(
											processoIniciado.getUsuario(), funcionalidadeIniciada.getId());

							Collection<Integer> colecaoIdsLocalidadesGerarResumoRegistroAtendimentoMes = getControladorLocalidade()
											.pesquisarTodosIdsLocalidade();

							gerarResumoRegistroAtendimento.addParametro(ConstantesSistema.COLECAO_UNIDADES_PROCESSAMENTO_BATCH,
											colecaoIdsLocalidadesGerarResumoRegistroAtendimentoMes);

							gerarResumoRegistroAtendimento.addParametro("anoMesFaturamentoSistemaParametro", sistemaParametros
											.getAnoMesFaturamento());

							// Seta o objeto para ser serializado no banco, onde
							// depois serï¿½ executado por uma thread
							funcionalidadeIniciada.setTarefaBatch(IoUtil.transformarObjetoParaBytes(gerarResumoRegistroAtendimento));

							getControladorUtil().atualizar(funcionalidadeIniciada);

							break;

						case Funcionalidade.GERAR_RESUMO_CONSUMO_AGUA:
							TarefaBatchGerarResumoConsumoAgua gerarResumoConsumoAgua = new TarefaBatchGerarResumoConsumoAgua(
											processoIniciado.getUsuario(), funcionalidadeIniciada.getId());

							FiltroSetorComercial filtro = new FiltroSetorComercial();
							Collection<SetorComercial> colSetor = getControladorUtil().pesquisar(filtro, SetorComercial.class.getName());

							gerarResumoConsumoAgua.addParametro(ConstantesSistema.COLECAO_UNIDADES_PROCESSAMENTO_BATCH, colSetor);

							// Seta o objeto para ser serializado no banco, onde
							// depois serï¿½ executado por uma thread
							funcionalidadeIniciada.setTarefaBatch(IoUtil.transformarObjetoParaBytes(gerarResumoConsumoAgua));

							getControladorUtil().atualizar(funcionalidadeIniciada);

							break;

						/** Pedro Alexandre */
						case Funcionalidade.GERAR_LANCAMENTOS_CONTABEIS_ARRECADACAO:
							TarefaBatchGerarLancamentosContabeisArrecadacao dadosGerarLancamentosContabeisArrecadacao = new TarefaBatchGerarLancamentosContabeisArrecadacao(
											processoIniciado.getUsuario(), funcionalidadeIniciada.getId());

							Collection<Integer> colecaoIdsLocalidadesGerarLancamentosContabeisArrecadacao = getControladorFinanceiro()
											.pesquisarIdsLocalidadesParaGerarLancamentosContabeisArrecadacao(
															sistemaParametros.getAnoMesArrecadacao());

							dadosGerarLancamentosContabeisArrecadacao.addParametro("anoMesArrecadacao", sistemaParametros
											.getAnoMesArrecadacao());

							// Seta os parametros para rodar a funcionalidade
							dadosGerarLancamentosContabeisArrecadacao.addParametro(ConstantesSistema.COLECAO_UNIDADES_PROCESSAMENTO_BATCH,
											colecaoIdsLocalidadesGerarLancamentosContabeisArrecadacao);

							// Seta o objeto para ser serializado no banco, onde
							// depois serï¿½ executado por uma thread
							funcionalidadeIniciada.setTarefaBatch(IoUtil
											.transformarObjetoParaBytes(dadosGerarLancamentosContabeisArrecadacao));

							getControladorUtil().atualizar(funcionalidadeIniciada);

							break;

						/** ****** FIM ENCERRAR FATURAMENTO DO Mï¿½S ***** */

						case Funcionalidade.GERAR_RESUMO_SITUACAO_ESPECIAL_FATURAMENTO:
							TarefaBatchGerarResumoSituacaoEspecialFaturamento gerarResumoSituacaoEspecialFaturamento = new TarefaBatchGerarResumoSituacaoEspecialFaturamento(
											processoIniciado.getUsuario(), funcionalidadeIniciada.getId());

							Collection<Integer> colecaoLocalidadeFaturamento = getControladorFaturamento()
											.pesquisarIdsLocalidadeParaGerarResumoLigacoesEconomias();

							gerarResumoSituacaoEspecialFaturamento.addParametro(ConstantesSistema.COLECAO_UNIDADES_PROCESSAMENTO_BATCH,
											colecaoLocalidadeFaturamento);

							// Seta o objeto para ser serializado no banco, onde
							// depois serï¿½ executado por uma thread
							funcionalidadeIniciada
											.setTarefaBatch(IoUtil.transformarObjetoParaBytes(gerarResumoSituacaoEspecialFaturamento));

							getControladorUtil().atualizar(funcionalidadeIniciada);

							break;

						case Funcionalidade.GERAR_RESUMO_HISTOGRAMA_AGUA_ESGOTO:
							TarefaBatchGerarResumoHistogramaAguaEsgoto gerarResumoAguaEsgoto = new TarefaBatchGerarResumoHistogramaAguaEsgoto(
											processoIniciado.getUsuario(), funcionalidadeIniciada.getId());

							FiltroSetorComercial filtroHistograma = new FiltroSetorComercial();

							Collection<SetorComercial> colSetorHistograma = getControladorUtil().pesquisar(filtroHistograma,
											SetorComercial.class.getName());

							gerarResumoAguaEsgoto.addParametro(ConstantesSistema.COLECAO_UNIDADES_PROCESSAMENTO_BATCH, colSetorHistograma);

							gerarResumoAguaEsgoto.addParametro("anoMesFaturamentoSistemaParametro", anoMesFaturamentoSistemaParametro);

							// Seta o objeto para ser serializado no banco, onde
							// depois serï¿½ executado por uma thread
							funcionalidadeIniciada.setTarefaBatch(IoUtil.transformarObjetoParaBytes(gerarResumoAguaEsgoto));

							getControladorUtil().atualizar(funcionalidadeIniciada);

							break;

						case Funcionalidade.GERAR_RESUMO_ACOES_COBRANCA_CRONOGRAMA:
							TarefaBatchGerarResumoAcoesCobrancaCronograma dadosGerarResumoAcoesCobrancaCronograma = new TarefaBatchGerarResumoAcoesCobrancaCronograma(
											processoIniciado.getUsuario(), funcionalidadeIniciada.getId());

							Collection colecaoCobrancaGrupoCronogramaMes = getControladorCobranca().pesquisarCobrancaGrupoCronogramaMes();

							// posiï¿½ï¿½es do array com os dados que serï¿½o atualizados
							int POSICAO_ID_COB_ACAO_ATIV_CRON_EMITIR = 0;
							int POSICAO_ID_COB_ACAO_ATIV_CRON_ENCERRAR = 1;
							int POSICAO_DATA_COM_ATIV_ENCERRAR = 2;
							int POSICAO_DATA_PREV_ATIV_ENCERRAR = 3;
							int POSICAO_DATA_PREV_ATIV_EMITIR = 4;
							int POSICAO_ANO_MES_REF_COB_GRUP_CRON_MES = 5;
							int POSICAO_ID_COB_ACAO_CRONOG = 6;
							int POSICAO_ID_COB_GRUPO = 7;
							int POSICAO_ID_COB_ACAO = 8;
							int POSICAO_DATA_REA_ATIV_EMITIR = 9;

							Collection colecaoDadosCobrancaAcaoAtividadeCronograma = new ArrayList();

							if(colecaoCobrancaGrupoCronogramaMes != null){
								Iterator iteratorColecaoCobrancaGrupoCronogramaMes = colecaoCobrancaGrupoCronogramaMes.iterator();

								while(iteratorColecaoCobrancaGrupoCronogramaMes.hasNext()){

									Object[] dadosCobrancaGrupoCronogramaMes = (Object[]) iteratorColecaoCobrancaGrupoCronogramaMes.next();

									Integer anoMesReferencia = null;
									Integer idGrupo = null;

									// coleï¿½ï¿½o de aï¿½ï¿½es de cobranï¿½a do cronograma
									Collection colecaoCobrancaAcaoCronograma = null;

									// id do cobranca grupo conograma mes
									int idCobrancaGrupoCronogramaMes = -1;

									// id co cobranca grupo cronograma mes
									if(dadosCobrancaGrupoCronogramaMes[0] != null){
										idCobrancaGrupoCronogramaMes = ((Integer) dadosCobrancaGrupoCronogramaMes[0]).intValue();
									}

									// ano mes referencia
									if(dadosCobrancaGrupoCronogramaMes[1] != null){
										anoMesReferencia = (Integer) dadosCobrancaGrupoCronogramaMes[1];

									}

									// id do cobranca grupo
									if(dadosCobrancaGrupoCronogramaMes[2] != null){
										idGrupo = (Integer) dadosCobrancaGrupoCronogramaMes[2];

									}

									// Item 2
									// a partir da tabela COBRANCA_ACAO_CRONOGRAMA
									// com CBCM_ID
									// da tabela COBRANCA_GRUPO_CRONOGRAMA_MES
									colecaoCobrancaAcaoCronograma = getControladorCobranca().pesquisarCobrancaAcaoCronograma(
													idCobrancaGrupoCronogramaMes);

									// para cada aï¿½ï¿½o de cobranï¿½a do conograma
									// verifica:
									if(colecaoCobrancaAcaoCronograma != null && !colecaoCobrancaAcaoCronograma.isEmpty()){

										Iterator iteratorColecaoCobrancaAcaoCronograma = colecaoCobrancaAcaoCronograma.iterator();

										// id do cobranca acao cronograma
										int idCobrancaAcaoCronograma = -1;

										Object[] dadosCobrancaAcaoCronograma = null;
										Object[] dadosCobrancaAcaoAtividadeCronograma = null;

										while(iteratorColecaoCobrancaAcaoCronograma.hasNext()){
											dadosCobrancaAcaoCronograma = (Object[]) iteratorColecaoCobrancaAcaoCronograma.next();

											dadosCobrancaAcaoAtividadeCronograma = new Object[10];

											dadosCobrancaAcaoAtividadeCronograma[POSICAO_ANO_MES_REF_COB_GRUP_CRON_MES] = anoMesReferencia;
											dadosCobrancaAcaoAtividadeCronograma[POSICAO_ID_COB_GRUPO] = idGrupo;

											// id do cobranca acao cronograma
											if(dadosCobrancaAcaoCronograma[0] != null){
												idCobrancaAcaoCronograma = ((Integer) dadosCobrancaAcaoCronograma[0]).intValue();
												dadosCobrancaAcaoAtividadeCronograma[POSICAO_ID_COB_ACAO_CRONOG] = dadosCobrancaAcaoCronograma[0];
											}

											LOGGER.info("COBRANCA ACAO CRONOGRAMA = " + idCobrancaAcaoCronograma);
											// id de Cobranca Acao do Cobranca Acao
											// Cronograma(serï¿½
											// usada para pesquisar o cobranca acao)
											if(dadosCobrancaAcaoCronograma[1] != null){
												dadosCobrancaAcaoAtividadeCronograma[POSICAO_ID_COB_ACAO] = dadosCobrancaAcaoCronograma[1];
											}

											boolean primeiraCondicao = true;
											boolean segundaCondicao = true;

											Collection colecaoCobrancaAtividadeAcaoCronogramaEmitir = null;
											Collection colecaoCobrancaAtividadeAcaoCronogramaEncerrar = null;

											// Item 3.1
											// O sistema seleciona a atividade da
											// aï¿½ï¿½o de
											// cobranï¿½a correspondete a EMITIR(
											// apatir da tabela
											// COBRANCA_ATIVIDADE_ACAO_CONOGRAMA
											// com CBCR_ID da tabela
											// COBRANCA_ACAO_CRONOGRAMA e
											// CBAT_ID com o valor correspondente a
											// EMITIR da
											// tabela
											// COBRANCA_ATIVIDADE
											colecaoCobrancaAtividadeAcaoCronogramaEmitir = getControladorCobranca()
															.pesquisarDataRelizacaoCobrancaAtividadeAcaoConograma(idCobrancaAcaoCronograma,
																			CobrancaAtividade.EMITIR);

											// se existir cobranca atividade acao
											// cronograma,
											// EMITIR
											if(colecaoCobrancaAtividadeAcaoCronogramaEmitir != null
															&& !colecaoCobrancaAtividadeAcaoCronogramaEmitir.isEmpty()){

												Object[] dadosCobrancaAtividade = (Object[]) colecaoCobrancaAtividadeAcaoCronogramaEmitir
																.iterator().next();

												// id cobranca atividade acao
												// cronograma
												if(dadosCobrancaAtividade[0] != null){
													dadosCobrancaAcaoAtividadeCronograma[POSICAO_ID_COB_ACAO_ATIV_CRON_EMITIR] = dadosCobrancaAtividade[0];
												}

												// data realizacao
												// [FS0004] - Verificar Realizaï¿½ï¿½o
												// da Atividade
												// Emitir da Aï¿½ï¿½o de Cobranï¿½a
												if(dadosCobrancaAtividade[1] == null){
													primeiraCondicao = false;
												}else{
													dadosCobrancaAcaoAtividadeCronograma[POSICAO_DATA_REA_ATIV_EMITIR] = dadosCobrancaAtividade[1];
												}

												// data prevista
												if(dadosCobrancaAtividade[2] != null){
													dadosCobrancaAcaoAtividadeCronograma[POSICAO_DATA_PREV_ATIV_EMITIR] = dadosCobrancaAtividade[2];
												}

											}else{
												// [FS0003] - Verificar Existï¿½ncia
												// da Atividade
												// Emitir da Aï¿½ï¿½o de Cobranï¿½a
												primeiraCondicao = false;
											}

											// Item 3.2
											// o sistema seleciona a atividade da
											// aï¿½ï¿½o de
											// cobrana correspondente a ENCERRAR( a
											// partir da
											// tabela
											// COBRANCA_ATIVIDADE_ACAO_CRONOGRAMA
											// com CBCR_ID da tabela
											// COBRANCA_ACAO_CONOGRAMA e
											// CBAT_ID com o valor correspondente a
											// ENCERRAR da
											// tebal COBRANA_ATIVIDADE
											colecaoCobrancaAtividadeAcaoCronogramaEncerrar = getControladorCobranca()
															.pesquisarDataRelizacaoCobrancaAtividadeAcaoConograma(idCobrancaAcaoCronograma,
																			CobrancaAtividade.ENCERRAR);

											// se existir acobranca atividade acao
											// cronograma,
											// ENCERRAR
											if(colecaoCobrancaAtividadeAcaoCronogramaEncerrar != null
															&& !colecaoCobrancaAtividadeAcaoCronogramaEncerrar.isEmpty()){

												Object[] dadosCobrancaAtividade = (Object[]) colecaoCobrancaAtividadeAcaoCronogramaEncerrar
																.iterator().next();

												// id cobranca atividade acao
												// cronograma
												if(dadosCobrancaAtividade[0] != null){
													dadosCobrancaAcaoAtividadeCronograma[POSICAO_ID_COB_ACAO_ATIV_CRON_ENCERRAR] = dadosCobrancaAtividade[0];
												}

												// data realizacao
												// [FS0006] - Verificar Realizaï¿½ï¿½o
												// da Atividade
												// Encerrar da Aï¿½ï¿½o de Cobranï¿½a
												if(dadosCobrancaAtividade[1] != null){
													segundaCondicao = false;

												}

												// data prevista
												if(dadosCobrancaAtividade[2] != null){
													dadosCobrancaAcaoAtividadeCronograma[POSICAO_DATA_PREV_ATIV_ENCERRAR] = dadosCobrancaAtividade[2];
												}

												// data comando
												if(dadosCobrancaAtividade[3] != null){
													dadosCobrancaAcaoAtividadeCronograma[POSICAO_DATA_COM_ATIV_ENCERRAR] = dadosCobrancaAtividade[3];
												}

												// seta null para o GC liberar
												dadosCobrancaAtividade = null;

											}else{
												// [FS0005] - Verificar Existï¿½ncia
												// da Atividade
												// Encerrar da Aï¿½ï¿½o de Cobranï¿½a
												// segundaCondicao = false;
											}

											if(primeiraCondicao && segundaCondicao){
												colecaoDadosCobrancaAcaoAtividadeCronograma.add(dadosCobrancaAcaoAtividadeCronograma);
											}
											dadosCobrancaAcaoAtividadeCronograma = null;
										}
									}

								}
								dadosGerarResumoAcoesCobrancaCronograma.addParametro(
												ConstantesSistema.COLECAO_UNIDADES_PROCESSAMENTO_BATCH,
												colecaoDadosCobrancaAcaoAtividadeCronograma);

								// Seta o objeto para ser serializado no banco, onde
								// depois serï¿½ executado por uma thread
								funcionalidadeIniciada.setTarefaBatch(IoUtil
												.transformarObjetoParaBytes(dadosGerarResumoAcoesCobrancaCronograma));

								getControladorUtil().atualizar(funcionalidadeIniciada);
							}else{
								throw new ControladorException("atencao.nao.existe.dados.tabela.cronograma");
							}

							break;

						/** Yara T. Souza */
						case Funcionalidade.ATUALIZAR_NUMERO_EXECUCAO_RESUMO_NEGATIVACAO:

							TarefaBatchAtualizarNumeroExecucaoResumoNegativacao atualizarNumeroExecucaoResumoNegativacao = new TarefaBatchAtualizarNumeroExecucaoResumoNegativacao(
											processoIniciado.getUsuario(), funcionalidadeIniciada.getId());

							// Seta o objeto para ser serializado no banco,
							// onde depois seria executado por uma thread
							funcionalidadeIniciada.setTarefaBatch(IoUtil
											.transformarObjetoParaBytes(atualizarNumeroExecucaoResumoNegativacao));

							getControladorUtil().atualizar(funcionalidadeIniciada);

							break;

						/** Yara T. Souza */
						case Funcionalidade.GERAR_MOVIMENTO_EXCLUSAO_NEGATIVACAO:

							TarefaBatchGerarMovimentoExclusaoNegativacao gerarMovimentoExclusaoNegativacao = new TarefaBatchGerarMovimentoExclusaoNegativacao(
											processoIniciado.getUsuario(), funcionalidadeIniciada.getId());

							// Seta o objeto para ser serializado no banco,
							// onde depois seria executado por uma thread
							funcionalidadeIniciada.setTarefaBatch(IoUtil.transformarObjetoParaBytes(gerarMovimentoExclusaoNegativacao));

							getControladorUtil().atualizar(funcionalidadeIniciada);

							break;

						/** Yara T. Souza */
						case Funcionalidade.GERAR_MOVIMENTO_RETORNO_NEGATIVACAO:

							TarefaBatchGerarMovimentoRetornoNegativacao gerarMovimentoRetornoNegativacao = new TarefaBatchGerarMovimentoRetornoNegativacao(
											processoIniciado.getUsuario(), funcionalidadeIniciada.getId());

							// Seta o objeto para ser serializado no banco,
							// onde depois seria executado por uma thread
							funcionalidadeIniciada.setTarefaBatch(IoUtil.transformarObjetoParaBytes(gerarMovimentoRetornoNegativacao));

							getControladorUtil().atualizar(funcionalidadeIniciada);

							break;

						case Funcionalidade.GERAR_RESUMO_ACOES_COBRANCA_EVENTUAL:
							TarefaBatchGerarResumoAcoesCobrancaEventual tarefaBatchGerarResumoAcoesCobrancaEventual = new TarefaBatchGerarResumoAcoesCobrancaEventual(
											processoIniciado.getUsuario(), funcionalidadeIniciada.getId());

							String idsAcoesSemPrazoValidade = ParametroCobranca.P_LISTA_ACOES_COBRANCA_SEM_PRAZO_VALIDADE.executar();

							Collection colecaoAcaoCobrancaEventual = getControladorCobranca()
											.pesquisarCobrancaAcaoAtividadeComandoSemRealizacao(idsAcoesSemPrazoValidade);

							if(colecaoAcaoCobrancaEventual != null && !colecaoAcaoCobrancaEventual.isEmpty()){
								tarefaBatchGerarResumoAcoesCobrancaEventual.addParametro(
												ConstantesSistema.COLECAO_UNIDADES_PROCESSAMENTO_BATCH, colecaoAcaoCobrancaEventual);

								// Seta o objeto para ser serializado no banco, onde
								// depois serï¿½ executado por uma thread
								funcionalidadeIniciada.setTarefaBatch(IoUtil
												.transformarObjetoParaBytes(tarefaBatchGerarResumoAcoesCobrancaEventual));

								getControladorUtil().atualizar(funcionalidadeIniciada);
							}else{
								throw new ControladorException("atencao.nao.existe.dados.tabela.comando");
							}

							break;

						case Funcionalidade.INSERIR_RESUMO_ACOES_COBRANCA_CRONOGRAMA:
							TarefaBatchInserirResumoAcoesCobrancaCronograma dadosInserirResumoAcoesCobrancaCronograma = new TarefaBatchInserirResumoAcoesCobrancaCronograma(
											processoIniciado.getUsuario(), funcionalidadeIniciada.getId());

							POSICAO_ID_COB_ACAO_ATIV_CRON_EMITIR = 0;
							POSICAO_ID_COB_ACAO_ATIV_CRON_ENCERRAR = 1;
							POSICAO_DATA_COM_ATIV_ENCERRAR = 2;
							POSICAO_DATA_PREV_ATIV_ENCERRAR = 3;
							POSICAO_DATA_PREV_ATIV_EMITIR = 4;
							POSICAO_ANO_MES_REF_COB_GRUP_CRON_MES = 5;
							POSICAO_ID_COB_ACAO_CRONOG = 6;
							POSICAO_ID_COB_GRUPO = 7;
							POSICAO_ID_COB_ACAO = 8;
							POSICAO_DATA_REA_ATIV_EMITIR = 9;

							Collection colecaoCobrancaGrupoCronogramaMesInserir = getControladorCobranca()
											.pesquisarCobrancaGrupoCronogramaMes();

							Collection colecaoDadosCobrancaAcaoAtividadeCronogramaParaInserir = new ArrayList();

							if(colecaoCobrancaGrupoCronogramaMesInserir != null){
								Iterator iteratorColecaoCobrancaGrupoCronogramaMes = colecaoCobrancaGrupoCronogramaMesInserir.iterator();

								while(iteratorColecaoCobrancaGrupoCronogramaMes.hasNext()){

									Object[] dadosCobrancaGrupoCronogramaMes = (Object[]) iteratorColecaoCobrancaGrupoCronogramaMes.next();

									Integer anoMesReferencia = null;
									Integer idGrupo = null;

									// coleï¿½ï¿½o de aï¿½ï¿½es de cobranï¿½a do cronograma
									Collection colecaoCobrancaAcaoCronograma = null;

									// id do cobranca grupo conograma mes
									int idCobrancaGrupoCronogramaMes = -1;

									// id co cobranca grupo cronograma mes
									if(dadosCobrancaGrupoCronogramaMes[0] != null){
										idCobrancaGrupoCronogramaMes = ((Integer) dadosCobrancaGrupoCronogramaMes[0]).intValue();
									}

									// ano mes referencia
									if(dadosCobrancaGrupoCronogramaMes[1] != null){
										anoMesReferencia = (Integer) dadosCobrancaGrupoCronogramaMes[1];

									}

									// id do cobranca grupo
									if(dadosCobrancaGrupoCronogramaMes[2] != null){
										idGrupo = (Integer) dadosCobrancaGrupoCronogramaMes[2];

									}

									// Item 2
									// a partir da tabela COBRANCA_ACAO_CRONOGRAMA
									// com CBCM_ID
									// da tabela COBRANCA_GRUPO_CRONOGRAMA_MES
									colecaoCobrancaAcaoCronograma = getControladorCobranca().pesquisarCobrancaAcaoCronograma(
													idCobrancaGrupoCronogramaMes);

									// para cada aï¿½ï¿½o de cobranï¿½a do conograma
									// verifica:
									if(colecaoCobrancaAcaoCronograma != null && !colecaoCobrancaAcaoCronograma.isEmpty()){

										Iterator iteratorColecaoCobrancaAcaoCronograma = colecaoCobrancaAcaoCronograma.iterator();

										// id do cobranca acao cronograma
										int idCobrancaAcaoCronograma = -1;

										Object[] dadosCobrancaAcaoCronograma = null;
										Object[] dadosCobrancaAcaoAtividadeCronograma = null;

										while(iteratorColecaoCobrancaAcaoCronograma.hasNext()){
											dadosCobrancaAcaoCronograma = (Object[]) iteratorColecaoCobrancaAcaoCronograma.next();

											dadosCobrancaAcaoAtividadeCronograma = new Object[10];

											dadosCobrancaAcaoAtividadeCronograma[POSICAO_ANO_MES_REF_COB_GRUP_CRON_MES] = anoMesReferencia;
											dadosCobrancaAcaoAtividadeCronograma[POSICAO_ID_COB_GRUPO] = idGrupo;

											// id do cobranca acao cronograma
											if(dadosCobrancaAcaoCronograma[0] != null){
												idCobrancaAcaoCronograma = ((Integer) dadosCobrancaAcaoCronograma[0]).intValue();
												dadosCobrancaAcaoAtividadeCronograma[POSICAO_ID_COB_ACAO_CRONOG] = dadosCobrancaAcaoCronograma[0];
											}

											LOGGER.info("COBRANCA ACAO CRONOGRAMA = " + idCobrancaAcaoCronograma);
											// id de Cobranca Acao do Cobranca Acao
											// Cronograma(serï¿½
											// usada para pesquisar o cobranca acao)
											if(dadosCobrancaAcaoCronograma[1] != null){
												dadosCobrancaAcaoAtividadeCronograma[POSICAO_ID_COB_ACAO] = dadosCobrancaAcaoCronograma[1];
											}

											boolean primeiraCondicao = true;
											boolean segundaCondicao = true;

											Collection colecaoCobrancaAtividadeAcaoCronogramaEmitir = null;
											Collection colecaoCobrancaAtividadeAcaoCronogramaEncerrar = null;

											// Item 3.1
											// O sistema seleciona a atividade da
											// aï¿½ï¿½o de
											// cobranï¿½a correspondete a EMITIR(
											// apatir da tabela
											// COBRANCA_ATIVIDADE_ACAO_CONOGRAMA
											// com CBCR_ID da tabela
											// COBRANCA_ACAO_CRONOGRAMA e
											// CBAT_ID com o valor correspondente a
											// EMITIR da
											// tabela
											// COBRANCA_ATIVIDADE
											colecaoCobrancaAtividadeAcaoCronogramaEmitir = getControladorCobranca()
															.pesquisarDataRelizacaoCobrancaAtividadeAcaoConograma(idCobrancaAcaoCronograma,
																			CobrancaAtividade.EMITIR);

											// se existir cobranca atividade acao
											// cronograma,
											// EMITIR
											if(colecaoCobrancaAtividadeAcaoCronogramaEmitir != null
															&& !colecaoCobrancaAtividadeAcaoCronogramaEmitir.isEmpty()){

												Object[] dadosCobrancaAtividade = (Object[]) colecaoCobrancaAtividadeAcaoCronogramaEmitir
																.iterator().next();

												// id cobranca atividade acao
												// cronograma
												if(dadosCobrancaAtividade[0] != null){
													dadosCobrancaAcaoAtividadeCronograma[POSICAO_ID_COB_ACAO_ATIV_CRON_EMITIR] = dadosCobrancaAtividade[0];
												}

												// data realizacao
												// [FS0004] - Verificar Realizaï¿½ï¿½o
												// da Atividade
												// Emitir da Aï¿½ï¿½o de Cobranï¿½a
												if(dadosCobrancaAtividade[1] == null){
													primeiraCondicao = false;
												}else{
													dadosCobrancaAcaoAtividadeCronograma[POSICAO_DATA_REA_ATIV_EMITIR] = dadosCobrancaAtividade[1];
												}

												// data prevista
												if(dadosCobrancaAtividade[2] != null){
													dadosCobrancaAcaoAtividadeCronograma[POSICAO_DATA_PREV_ATIV_EMITIR] = dadosCobrancaAtividade[2];
												}

											}else{
												// [FS0003] - Verificar Existï¿½ncia
												// da Atividade
												// Emitir da Aï¿½ï¿½o de Cobranï¿½a
												primeiraCondicao = false;
											}

											// Item 3.2
											// o sistema seleciona a atividade da
											// aï¿½ï¿½o de
											// cobrana correspondente a ENCERRAR( a
											// partir da
											// tabela
											// COBRANCA_ATIVIDADE_ACAO_CRONOGRAMA
											// com CBCR_ID da tabela
											// COBRANCA_ACAO_CONOGRAMA e
											// CBAT_ID com o valor correspondente a
											// ENCERRAR da
											// tebal COBRANA_ATIVIDADE
											colecaoCobrancaAtividadeAcaoCronogramaEncerrar = getControladorCobranca()
															.pesquisarDataRelizacaoCobrancaAtividadeAcaoConograma(idCobrancaAcaoCronograma,
																			CobrancaAtividade.ENCERRAR);

											// se existir acobranca atividade acao
											// cronograma,
											// ENCERRAR
											if(colecaoCobrancaAtividadeAcaoCronogramaEncerrar != null
															&& !colecaoCobrancaAtividadeAcaoCronogramaEncerrar.isEmpty()){

												Object[] dadosCobrancaAtividade = (Object[]) colecaoCobrancaAtividadeAcaoCronogramaEncerrar
																.iterator().next();

												// id cobranca atividade acao
												// cronograma
												if(dadosCobrancaAtividade[0] != null){
													dadosCobrancaAcaoAtividadeCronograma[POSICAO_ID_COB_ACAO_ATIV_CRON_ENCERRAR] = dadosCobrancaAtividade[0];
												}

												// data realizacao
												// [FS0006] - Verificar Realizaï¿½ï¿½o
												// da Atividade
												// Encerrar da Aï¿½ï¿½o de Cobranï¿½a
												if(dadosCobrancaAtividade[1] != null){
													segundaCondicao = false;
												}

												// data prevista
												if(dadosCobrancaAtividade[2] != null){
													dadosCobrancaAcaoAtividadeCronograma[POSICAO_DATA_PREV_ATIV_ENCERRAR] = dadosCobrancaAtividade[2];
												}

												// data comando
												if(dadosCobrancaAtividade[3] != null){
													dadosCobrancaAcaoAtividadeCronograma[POSICAO_DATA_COM_ATIV_ENCERRAR] = dadosCobrancaAtividade[3];
												}

												// seta null para o GC liberar
												dadosCobrancaAtividade = null;

											}else{
												// [FS0005] - Verificar Existï¿½ncia
												// da Atividade
												// Encerrar da Aï¿½ï¿½o de Cobranï¿½a
												// segundaCondicao = false;
											}

											if(primeiraCondicao && segundaCondicao){
												colecaoDadosCobrancaAcaoAtividadeCronogramaParaInserir
																.add(dadosCobrancaAcaoAtividadeCronograma);
											}
											dadosCobrancaAcaoAtividadeCronograma = null;
										}
									}

								}
								dadosInserirResumoAcoesCobrancaCronograma.addParametro(
												ConstantesSistema.COLECAO_UNIDADES_PROCESSAMENTO_BATCH,
												colecaoDadosCobrancaAcaoAtividadeCronogramaParaInserir);

								// Seta o objeto para ser serializado no banco, onde
								// depois serï¿½ executado por uma thread
								funcionalidadeIniciada.setTarefaBatch(IoUtil
												.transformarObjetoParaBytes(dadosInserirResumoAcoesCobrancaCronograma));

								getControladorUtil().atualizar(funcionalidadeIniciada);
							}else{
								throw new ControladorException("atencao.nao.existe.dados.tabela.cronograma");
							}
							break;

						case Funcionalidade.INSERIR_RESUMO_ACOES_COBRANCA_EVENTUAL:
							// TarefaBatchInserirResumoAcoesCobrancaEventual
							// tarefaBatchInserirResumoAcoesCobrancaEventual = new
							// TarefaBatchInserirResumoAcoesCobrancaEventual(
							// processoIniciado.getUsuario(), funcionalidadeIniciada.getId());
							//
							// Collection colecaoAcaoCobrancaEventualParaInserir =
							// getControladorCobranca()
							// .pesquisarCobrancaAcaoAtividadeComandoSemRealizacao();
							//
							// if(colecaoAcaoCobrancaEventualParaInserir != null &&
							// !colecaoAcaoCobrancaEventualParaInserir.isEmpty()){
							// tarefaBatchInserirResumoAcoesCobrancaEventual.addParametro(
							// ConstantesSistema.COLECAO_UNIDADES_PROCESSAMENTO_BATCH,
							// colecaoAcaoCobrancaEventualParaInserir);
							//
							// // Seta o objeto para ser serializado no banco, onde
							// // depois serï¿½ executado por uma thread
							// funcionalidadeIniciada.setTarefaBatch(IoUtil
							// .transformarObjetoParaBytes(tarefaBatchInserirResumoAcoesCobrancaEventual));
							//
							// getControladorUtil().atualizar(funcionalidadeIniciada);
							// }else{
							// throw new
							// ControladorException("atencao.nao.existe.dados.tabela.comando");
							// }

							break;

						case Funcionalidade.GERAR_RESUMO_PENDENCIA:
							TarefaBatchGerarResumoPendencia dadosGerarResumoPendencia = new TarefaBatchGerarResumoPendencia(
											processoIniciado.getUsuario(), funcionalidadeIniciada.getId());

							FiltroSetorComercial novoFiltroPendencia = new FiltroSetorComercial();
							Collection<SetorComercial> colecaoSetorComercialPendencia = getControladorUtil().pesquisar(novoFiltroPendencia,
											SetorComercial.class.getName());

							// Seta os parametros para rodar a funcionalidade
							dadosGerarResumoPendencia.addParametro(ConstantesSistema.COLECAO_UNIDADES_PROCESSAMENTO_BATCH,
											colecaoSetorComercialPendencia);

							// Seta o objeto para ser serializado no banco, onde
							// depois serï¿½ executado por uma thread
							funcionalidadeIniciada.setTarefaBatch(IoUtil.transformarObjetoParaBytes(dadosGerarResumoPendencia));

							getControladorUtil().atualizar(funcionalidadeIniciada);

							break;

						case Funcionalidade.GERAR_RESUMO_ANORMALIDADES:
							TarefaBatchGerarResumoAnormalidades tarefaBatchGerarResumoAnormalidades = new TarefaBatchGerarResumoAnormalidades(
											processoIniciado.getUsuario(), funcionalidadeIniciada.getId());

							// Collection<Integer> colecaoIdsLocalidadesAnormalidades =
							// getControladorFaturamento()
							// .pesquisarIdsLocalidadeParaGerarResumoLigacoesEconomias();

							// Seta os parametros para rodar a funcionalidade
							// tarefaBatchGerarResumoAnormalidades.addParametro(ConstantesSistema.COLECAO_UNIDADES_PROCESSAMENTO_BATCH,
							// colecaoIdsLocalidadesAnormalidades);

							// Seta o objeto para ser serializado no banco, onde
							// depois serï¿½ executado por uma thread
							funcionalidadeIniciada.setTarefaBatch(IoUtil.transformarObjetoParaBytes(tarefaBatchGerarResumoAnormalidades));

							getControladorUtil().atualizar(funcionalidadeIniciada);

							break;

						case Funcionalidade.GERAR_RESUMO_ANORMALIDADE_CONSUMO:
							TarefaBatchGerarResumoAnormalidadeConsumo tarefaBatchGerarResumoAnormalidadeConsumo = new TarefaBatchGerarResumoAnormalidadeConsumo(
											processoIniciado.getUsuario(), funcionalidadeIniciada.getId());

							// Seta o objeto para ser serializado no banco, onde
							// depois serï¿½ executado por uma thread
							funcionalidadeIniciada.setTarefaBatch(IoUtil
											.transformarObjetoParaBytes(tarefaBatchGerarResumoAnormalidadeConsumo));

							getControladorUtil().atualizar(funcionalidadeIniciada);

							break;

						case Funcionalidade.GERAR_RESUMO_SITUACAO_ESPECIAL_COBRANCA:
							TarefaBatchGerarResumoSituacaoEspecialCobranca tarefaBatchGerarResumoSituacaoEspecialCobranca = new TarefaBatchGerarResumoSituacaoEspecialCobranca(
											processoIniciado.getUsuario(), funcionalidadeIniciada.getId());

							Collection<Integer> colecaoIdsLocalidadesCobranca = getControladorFaturamento()
											.pesquisarIdsLocalidadeParaGerarResumoLigacoesEconomias();

							// Seta os parametros para rodar a funcionalidade
							tarefaBatchGerarResumoSituacaoEspecialCobranca.addParametro(
											ConstantesSistema.COLECAO_UNIDADES_PROCESSAMENTO_BATCH, colecaoIdsLocalidadesCobranca);

							// Seta o objeto para ser serializado no banco, onde
							// depois serï¿½ executado por uma thread
							funcionalidadeIniciada.setTarefaBatch(IoUtil
											.transformarObjetoParaBytes(tarefaBatchGerarResumoSituacaoEspecialCobranca));

							getControladorUtil().atualizar(funcionalidadeIniciada);

							break;

						/** processo de gerar fatura de cliente responsï¿½vel */
						/** Pedro Alexandre */
						case Funcionalidade.GERAR_FATURA_CLIENTE_RESPONSAVEL:
							TarefaBatchGerarFaturaClienteResponsavel dadosGerarFaturaClienteResponsavel = new TarefaBatchGerarFaturaClienteResponsavel(
											processoIniciado.getUsuario(), funcionalidadeIniciada.getId());

							funcionalidadeIniciada.setTarefaBatch(IoUtil.transformarObjetoParaBytes(dadosGerarFaturaClienteResponsavel));

							getControladorUtil().atualizar(funcionalidadeIniciada);

							break;

						case Funcionalidade.EMITIR_CONTAS:
							TarefaBatchEmitirContas emitirContas = new TarefaBatchEmitirContas(processoIniciado.getUsuario(),
											funcionalidadeIniciada.getId());

							// Seta os parametros para rodar a funcionalidade
							emitirContas.addParametro("anoMesFaturamentoGrupo", anoMesFaturamentoSistemaParametro);

							emitirContas.addParametro("faturamentoGrupo", null);

							Collection colecaoIdsEmpresas = getControladorCadastro().pesquisarIdsEmpresa();

							emitirContas.addParametro(ConstantesSistema.COLECAO_UNIDADES_PROCESSAMENTO_BATCH, colecaoIdsEmpresas);

							funcionalidadeIniciada.setTarefaBatch(IoUtil.transformarObjetoParaBytes(emitirContas));

							getControladorUtil().atualizar(funcionalidadeIniciada);

							break;

						/** fim do processo de gerar fatura de cliente responsï¿½vel */

						case Funcionalidade.DESFAZER_PARCELAMENTO_POR_ENTRADA_NAO_PAGA:
							TarefaBatchDesfazerParcelamentoPorEntradaNaoPaga dadosDesfazerParcelamentoPorEntradaNaoPaga = new TarefaBatchDesfazerParcelamentoPorEntradaNaoPaga(
											processoIniciado.getUsuario(), funcionalidadeIniciada.getId());

							funcionalidadeIniciada.setTarefaBatch(IoUtil
											.transformarObjetoParaBytes(dadosDesfazerParcelamentoPorEntradaNaoPaga));

							getControladorUtil().atualizar(funcionalidadeIniciada);

							break;

						/** Pedro Alexandre */
						case Funcionalidade.GERAR_RESUMO_INSTALACOES_HIDROMETROS:
							TarefaBatchGerarResumoInstalacoesHidrometros dadosGerarResumoInstalacoesHidrometros = new TarefaBatchGerarResumoInstalacoesHidrometros(
											processoIniciado.getUsuario(), funcionalidadeIniciada.getId());

							/**
							 * Coleï¿½ï¿½o de todos ids de setor comercial para os imï¿½veis que tem
							 * hidrometro instalado no historico para o ano/mï¿½s de
							 * referï¿½ncia do faturamento.
							 */
							Collection<Integer> colecaoIdsSetoresComercial = getControladorGerencialMicromedicao()
											.pesquisarIdsSetorComercialParaGerarResumoInstalacaoHidrometro(
															anoMesFaturamentoSistemaParametro);
							// Collection<Integer> colecaoIdsSetoresComercial =
							// getControladorCadastro().pesquisarTodosIdsSetorComercial();

							// Seta os parametros para rodar a funcionalidade
							dadosGerarResumoInstalacoesHidrometros.addParametro(ConstantesSistema.COLECAO_UNIDADES_PROCESSAMENTO_BATCH,
											colecaoIdsSetoresComercial);

							dadosGerarResumoInstalacoesHidrometros.addParametro("anoMesReferenciaFaturamento",
											anoMesFaturamentoSistemaParametro);

							// Seta o objeto para ser serializado no banco, onde
							// depois serï¿½ executado por uma thread
							funcionalidadeIniciada
											.setTarefaBatch(IoUtil.transformarObjetoParaBytes(dadosGerarResumoInstalacoesHidrometros));

							getControladorUtil().atualizar(funcionalidadeIniciada);

							break;

						case Funcionalidade.GERAR_RESUMO_LEITURA_ANORMALIDADE:
							TarefaBatchGerarResumoLeituraAnormalidade dadosGerarResumoLeituraAnormalidade = new TarefaBatchGerarResumoLeituraAnormalidade(
											processoIniciado.getUsuario(), funcionalidadeIniciada.getId());

							/*
							 * Collection<Integer> colecaoLocalidades = getControladorFaturamento()
							 * .pesquisarIdsLocalidadeParaGerarResumoLigacoesEconomias();
							 */
							FiltroSetorComercial filtroSetor = new FiltroSetorComercial();
							Collection<SetorComercial> colSetorComercial = getControladorUtil().pesquisar(filtroSetor,
											SetorComercial.class.getName());

							dadosGerarResumoLeituraAnormalidade.addParametro(ConstantesSistema.COLECAO_UNIDADES_PROCESSAMENTO_BATCH,
											colSetorComercial);

							dadosGerarResumoLeituraAnormalidade.addParametro("anoMesReferenciaFaturamento", sistemaParametros
											.getAnoMesFaturamento());

							// Seta o objeto para ser serializado no banco, onde
							// depois serï¿½ executado por uma thread
							funcionalidadeIniciada.setTarefaBatch(IoUtil.transformarObjetoParaBytes(dadosGerarResumoLeituraAnormalidade));

							getControladorUtil().atualizar(funcionalidadeIniciada);

							break;

						case Funcionalidade.GERAR_RESUMO_ARRECADACAO:
							TarefaBatchGerarResumoArrecadacao dadosGerarResumoArrecadacao = new TarefaBatchGerarResumoArrecadacao(
											processoIniciado.getUsuario(), funcionalidadeIniciada.getId());

							FiltroSetorComercial gra_filtroSetor = new FiltroSetorComercial();
							Collection<SetorComercial> gra_colSetorComercial = getControladorUtil().pesquisar(gra_filtroSetor,
											SetorComercial.class.getName());

							dadosGerarResumoArrecadacao.addParametro(ConstantesSistema.COLECAO_UNIDADES_PROCESSAMENTO_BATCH,
											gra_colSetorComercial);

							dadosGerarResumoArrecadacao.addParametro("anoMesReferenciaArrecadacao", sistemaParametros
											.getAnoMesArrecadacao());

							// Seta o objeto para ser serializado no banco, onde
							// depois serï¿½ executado por uma thread
							funcionalidadeIniciada.setTarefaBatch(IoUtil.transformarObjetoParaBytes(dadosGerarResumoArrecadacao));

							getControladorUtil().atualizar(funcionalidadeIniciada);

							break;

						case Funcionalidade.GERAR_RESUMO_PARCELAMENTO:
							TarefaBatchGerarResumoParcelamento dadosGerarResumoParcelamento = new TarefaBatchGerarResumoParcelamento(
											processoIniciado.getUsuario(), funcionalidadeIniciada.getId());
							// 2222
							Collection<Integer> colecaoLocalidadesParcelamento = getControladorFaturamento()
											.pesquisarIdsLocalidadeParaGerarResumoLigacoesEconomias();

							dadosGerarResumoParcelamento.addParametro(ConstantesSistema.COLECAO_UNIDADES_PROCESSAMENTO_BATCH,
											colecaoLocalidadesParcelamento);

							dadosGerarResumoParcelamento.addParametro("anoMesFaturamento", sistemaParametros.getAnoMesFaturamento());

							// Seta o objeto para ser serializado no banco, onde
							// depois serï¿½ executado por uma thread
							funcionalidadeIniciada.setTarefaBatch(IoUtil.transformarObjetoParaBytes(dadosGerarResumoParcelamento));

							getControladorUtil().atualizar(funcionalidadeIniciada);

							break;

						case Funcionalidade.GERAR_RESUMO_FATURAMENTO_AGUA_ESGOTO:
							TarefaBatchGerarResumoFaturamentoAguaEsgoto dadosGerarResumoFaturamentoAguaEsgoto = new TarefaBatchGerarResumoFaturamentoAguaEsgoto(
											processoIniciado.getUsuario(), funcionalidadeIniciada.getId());

							FiltroSetorComercial filtroFaturamento = new FiltroSetorComercial();

							Collection<SetorComercial> colSetorFaturamento = getControladorUtil() // getControladorGerencialFaturamento().pesquisarIdsSetores();
											.pesquisar(filtroFaturamento, SetorComercial.class.getName());

							dadosGerarResumoFaturamentoAguaEsgoto.addParametro(ConstantesSistema.COLECAO_UNIDADES_PROCESSAMENTO_BATCH,
											colSetorFaturamento);

							dadosGerarResumoFaturamentoAguaEsgoto.addParametro("anoMesFaturamento", sistemaParametros
											.getAnoMesFaturamento());

							// Seta o objeto para ser serializado no banco, onde
							// depois serï¿½ executado por uma thread
							funcionalidadeIniciada.setTarefaBatch(IoUtil.transformarObjetoParaBytes(dadosGerarResumoFaturamentoAguaEsgoto));

							getControladorUtil().atualizar(funcionalidadeIniciada);

							break;

						/** Pedro Alexandre */
						case Funcionalidade.GERAR_LANCAMENTOS_CONTABEIS_FATURAMENTO:
							TarefaBatchGerarLancamentosContabeisFaturamento dadosGerarLancamentosContabeisFaturamento = new TarefaBatchGerarLancamentosContabeisFaturamento(
											processoIniciado.getUsuario(), funcionalidadeIniciada.getId());

							Collection<Integer> colecaoIdsLocalidadesGerarLancamentosContabeisFaturamento = getControladorFinanceiro()
											.pesquisarIdsLocalidadesParaGerarLancamentosContabeisFaturamento(
															sistemaParametros.getAnoMesFaturamento());

							dadosGerarLancamentosContabeisFaturamento.addParametro("anoMesFaturamento", sistemaParametros
											.getAnoMesFaturamento());

							// Seta os parametros para rodar a funcionalidade
							dadosGerarLancamentosContabeisFaturamento.addParametro(ConstantesSistema.COLECAO_UNIDADES_PROCESSAMENTO_BATCH,
											colecaoIdsLocalidadesGerarLancamentosContabeisFaturamento);

							// Seta o objeto para ser serializado no banco, onde
							// depois serï¿½ executado por uma thread
							funcionalidadeIniciada.setTarefaBatch(IoUtil
											.transformarObjetoParaBytes(dadosGerarLancamentosContabeisFaturamento));

							getControladorUtil().atualizar(funcionalidadeIniciada);

							break;

						/** Pedro Alexandre */
						case Funcionalidade.GERAR_RESUMO_DEVEDORES_DUVIDOSOS:
							TarefaBatchGerarResumoDevedoresDuvidosos dadosGerarResumoDevedoresDuvidosos = new TarefaBatchGerarResumoDevedoresDuvidosos(
											processoIniciado.getUsuario(), funcionalidadeIniciada.getId());

							Collection<Integer> colecaoIdsLocalidadesGerarResumoDevedoresDuvidosos = getControladorFinanceiro()
											.pesquisarIdsLocalidadesParaGerarResumoDevedoresDuvidosos(
															sistemaParametros.getAnoMesFaturamento());

							dadosGerarResumoDevedoresDuvidosos.addParametro("anoMesFaturamento", sistemaParametros.getAnoMesFaturamento());

							// Seta os parametros para rodar a funcionalidade
							dadosGerarResumoDevedoresDuvidosos.addParametro(ConstantesSistema.COLECAO_UNIDADES_PROCESSAMENTO_BATCH,
											colecaoIdsLocalidadesGerarResumoDevedoresDuvidosos);

							// Seta o objeto para ser serializado no banco, onde
							// depois serï¿½ executado por uma thread
							funcionalidadeIniciada.setTarefaBatch(IoUtil.transformarObjetoParaBytes(dadosGerarResumoDevedoresDuvidosos));

							getControladorUtil().atualizar(funcionalidadeIniciada);

							break;
						case Funcionalidade.GERAR_RESUMO_METAS:
							TarefaBatchGerarResumoMetas tarefaBatchGerarResumoMetas = new TarefaBatchGerarResumoMetas(processoIniciado
											.getUsuario(), funcionalidadeIniciada.getId());

							FiltroSetorComercial filtroSetorC = new FiltroSetorComercial();
							Collection<SetorComercial> colSetorC = getControladorUtil().pesquisar(filtroSetorC,
											SetorComercial.class.getName());

							Collection colecaoResumoMetas = getControladorFaturamento().pesquisarResumoMetas(
											sistemaParametros.getAnoMesArrecadacao());

							if(colecaoResumoMetas != null && !colecaoResumoMetas.isEmpty()){
								throw new ControladorException("atencao.dados.existente.resumo.metas", null, ""
												+ sistemaParametros.getAnoMesArrecadacao());
							}

							tarefaBatchGerarResumoMetas.addParametro(ConstantesSistema.COLECAO_UNIDADES_PROCESSAMENTO_BATCH, colSetorC);

							Date dataInicial = Util.gerarDataInicialApartirAnoMesRefencia(sistemaParametros.getAnoMesArrecadacao());
							Date dataFinal = Util.gerarDataApartirAnoMesRefencia(sistemaParametros.getAnoMesArrecadacao());
							tarefaBatchGerarResumoMetas.addParametro("dataInicial", dataInicial);

							tarefaBatchGerarResumoMetas.addParametro("dataFinal", dataFinal);

							// Seta o objeto para ser serializado no banco, onde
							// depois serï¿½ executado por uma thread
							funcionalidadeIniciada.setTarefaBatch(IoUtil.transformarObjetoParaBytes(tarefaBatchGerarResumoMetas));

							getControladorUtil().atualizar(funcionalidadeIniciada);

							break;

						case Funcionalidade.GERAR_RESUMO_METAS_ACUMULADO:
							TarefaBatchGerarResumoMetasAcumulado tarefaBatchGerarResumoMetasAcumulado = new TarefaBatchGerarResumoMetasAcumulado(
											processoIniciado.getUsuario(), funcionalidadeIniciada.getId());

							FiltroSetorComercial filtroSetorCA = new FiltroSetorComercial();
							Collection<SetorComercial> colSetorCA = getControladorUtil().pesquisar(filtroSetorCA,
											SetorComercial.class.getName());

							Collection colecaoResumoMetasA = getControladorFaturamento().pesquisarResumoMetasAcumulado(
											sistemaParametros.getAnoMesArrecadacao());

							if(colecaoResumoMetasA != null && !colecaoResumoMetasA.isEmpty()){
								throw new ControladorException("atencao.dados.existente.resumo.metas", null, ""
												+ sistemaParametros.getAnoMesArrecadacao());
							}

							tarefaBatchGerarResumoMetasAcumulado.addParametro(ConstantesSistema.COLECAO_UNIDADES_PROCESSAMENTO_BATCH,
											colSetorCA);

							tarefaBatchGerarResumoMetasAcumulado
											.addParametro("anoMesArrecadacao", sistemaParametros.getAnoMesFaturamento());

							// Seta o objeto para ser serializado no banco, onde
							// depois serï¿½ executado por uma thread
							funcionalidadeIniciada.setTarefaBatch(IoUtil.transformarObjetoParaBytes(tarefaBatchGerarResumoMetasAcumulado));

							getControladorUtil().atualizar(funcionalidadeIniciada);

							break;

						case Funcionalidade.EMITIR_CONTAS_ORGAO_PUBLICO:
							TarefaBatchEmitirContasOrgaoPublico tarefaBatchEmitirContasOrgaoPublico = new TarefaBatchEmitirContasOrgaoPublico(
											processoIniciado.getUsuario(), funcionalidadeIniciada.getId());

							Integer anoMesFaturamento = sistemaParametros.getAnoMesFaturamento();
							FaturamentoGrupo faturamentoGrupo = new FaturamentoGrupo();
							faturamentoGrupo.setId(0);

							tarefaBatchEmitirContasOrgaoPublico.addParametro("anoMesFaturamento", anoMesFaturamento);
							tarefaBatchEmitirContasOrgaoPublico.addParametro("faturamentoGrupo", faturamentoGrupo);

							// Seta o objeto para ser serializado no banco, onde
							// depois serï¿½ executado por uma thread
							funcionalidadeIniciada.setTarefaBatch(IoUtil.transformarObjetoParaBytes(tarefaBatchEmitirContasOrgaoPublico));

							getControladorUtil().atualizar(funcionalidadeIniciada);

							break;

						case Funcionalidade.GERAR_RESUMO_COLETA_ESGOTO:
							TarefaBatchGerarResumoColetaEsgoto gerarResumoColetaEsgoto = new TarefaBatchGerarResumoColetaEsgoto(
											processoIniciado.getUsuario(), funcionalidadeIniciada.getId());

							FiltroSetorComercial filtroSetorCom = new FiltroSetorComercial();
							Collection<SetorComercial> colSetorCom = getControladorUtil().pesquisar(filtroSetorCom,
											SetorComercial.class.getName());

							gerarResumoColetaEsgoto.addParametro(ConstantesSistema.COLECAO_UNIDADES_PROCESSAMENTO_BATCH, colSetorCom);

							// Seta o objeto para ser serializado no banco, onde
							// depois serï¿½ executado por uma thread marcio
							funcionalidadeIniciada.setTarefaBatch(IoUtil.transformarObjetoParaBytes(gerarResumoColetaEsgoto));

							getControladorUtil().atualizar(funcionalidadeIniciada);

						case Funcionalidade.GERAR_CONTAS_A_RECEBER_CONTABIL:
							TarefaBatchGerarContasAReceberContabil gerarContaAReceberContabil = new TarefaBatchGerarContasAReceberContabil(
											processoIniciado.getUsuario(), funcionalidadeIniciada.getId());

							FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
							Collection<Localidade> colLocalidade = getControladorUtil().pesquisar(filtroLocalidade,
											Localidade.class.getName());

							gerarContaAReceberContabil.addParametro(ConstantesSistema.COLECAO_UNIDADES_PROCESSAMENTO_BATCH, colLocalidade);

							// Seta o objeto para ser serializado no banco, onde
							// depois serï¿½ executado por uma thread marcio
							funcionalidadeIniciada.setTarefaBatch(IoUtil.transformarObjetoParaBytes(gerarContaAReceberContabil));

							getControladorUtil().atualizar(funcionalidadeIniciada);

							break;

						case Funcionalidade.BAIXA_ORDEM_SERVICO_COBRANCA:

							TarefaBatchEfetuarBaixaOrdensServicoCobranca efetuarBaixaOrdensServicoCobranca = new TarefaBatchEfetuarBaixaOrdensServicoCobranca(
											processoIniciado.getUsuario(), funcionalidadeIniciada.getId());
							funcionalidadeIniciada.setTarefaBatch(IoUtil.transformarObjetoParaBytes(efetuarBaixaOrdensServicoCobranca));
							this.getControladorUtil().atualizar(funcionalidadeIniciada);

							break;

						case Funcionalidade.INDICAR_COBRANCA_BANCARIA_COM_NEGOCIACAO:

							TarefaBatchIdentificarCobrancaBancariaComNegociacao identificarCobrancaBancariaComNegociacao = new TarefaBatchIdentificarCobrancaBancariaComNegociacao(
											processoIniciado.getUsuario(), funcionalidadeIniciada.getId());
							identificarCobrancaBancariaComNegociacao.addParametro("idProcessoIniciado", codigoProcessoIniciadoGerado);
							funcionalidadeIniciada.setTarefaBatch(IoUtil
											.transformarObjetoParaBytes(identificarCobrancaBancariaComNegociacao));
							this.getControladorUtil().atualizar(funcionalidadeIniciada);

							break;

						case Funcionalidade.GERAR_ARQUIVO_AGENCIA_VIRTUAL_WEB:

							String anoBase = (String) ParametroBatch.P_ANO_BASE.executar();

							TarefaBatchGerarArquivoAgenciaVirtualWeb arquivoAgenciaVirtualWeb = new TarefaBatchGerarArquivoAgenciaVirtualWeb(
											processoIniciado.getUsuario(), funcionalidadeIniciada.getId());

							// Seta os parametros para rodar a funcionalidade

							arquivoAgenciaVirtualWeb.addParametro("anoBase", anoBase);

							// Seta o objeto para ser serializado no banco, onde
							// depois serï¿½ executado por uma thread
							funcionalidadeIniciada.setTarefaBatch(IoUtil.transformarObjetoParaBytes(arquivoAgenciaVirtualWeb));

							getControladorUtil().atualizar(funcionalidadeIniciada);

							break;
						case Funcionalidade.GERAR_ARQUIVO_AGENCIA_VIRTUAL_LOGRADOUROS:

							TarefaBatchGerarArquivoAgenciaVirtualLogradouros arquivoAgenciaVirtualLogradouros = new TarefaBatchGerarArquivoAgenciaVirtualLogradouros(
											processoIniciado.getUsuario(), funcionalidadeIniciada.getId());

							// Seta o objeto para ser serializado no banco, onde
							// depois serï¿½ executado por uma thread
							funcionalidadeIniciada.setTarefaBatch(IoUtil.transformarObjetoParaBytes(arquivoAgenciaVirtualLogradouros));

							getControladorUtil().atualizar(funcionalidadeIniciada);

							break;
						case Funcionalidade.GERAR_ARQUIVO_AGENCIA_VIRTUAL_QUITACAO_DEBITOS:

							String anoBaseQuitacaoDebito = (String) ParametroBatch.P_ANO_BASE.executar();

							TarefaBatchGerarArquivoAgenciaVirtualQuitacaoDebitos arquivoAgenciaVirtualQuitacaoDebitos = new TarefaBatchGerarArquivoAgenciaVirtualQuitacaoDebitos(
											processoIniciado.getUsuario(), funcionalidadeIniciada.getId());

							// Seta os parametros para rodar a funcionalidade

							arquivoAgenciaVirtualQuitacaoDebitos.addParametro("anoBaseQuitacaoDebito", anoBaseQuitacaoDebito);

							// Seta o objeto para ser serializado no banco, onde
							// depois serï¿½ executado por uma thread
							funcionalidadeIniciada.setTarefaBatch(IoUtil.transformarObjetoParaBytes(arquivoAgenciaVirtualQuitacaoDebitos));

							getControladorUtil().atualizar(funcionalidadeIniciada);

							break;
						case Funcionalidade.GERAR_ARQUIVO_AGENCIA_VIRTUAL_IMOVEIS:

							TarefaBatchGerarArquivoAgenciaVirtualImoveis arquivoAgenciaVirtualImoveis = new TarefaBatchGerarArquivoAgenciaVirtualImoveis(
											processoIniciado.getUsuario(), funcionalidadeIniciada.getId());

							FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();
							Collection<SetorComercial> colSetorComercialImoveis = getControladorUtil().pesquisar(filtroSetorComercial,
											SetorComercial.class.getName());

							// FiltroLocalidade filtroLocalidadeImoveis = new FiltroLocalidade();
							// Collection<Localidade> colLocalidadeImoveis =
							// getControladorUtil().pesquisar(filtroLocalidadeImoveis,
							// Localidade.class.getName());

							arquivoAgenciaVirtualImoveis.addParametro(ConstantesSistema.COLECAO_UNIDADES_PROCESSAMENTO_BATCH,
											colSetorComercialImoveis);

							// Seta o objeto para ser serializado no banco, onde
							// depois serï¿½ executado por uma thread
							funcionalidadeIniciada.setTarefaBatch(IoUtil.transformarObjetoParaBytes(arquivoAgenciaVirtualImoveis));

							getControladorUtil().atualizar(funcionalidadeIniciada);

							break;
						case Funcionalidade.GERAR_ARQUIVO_AGENCIA_VIRTUAL_DEBITOS:

							TarefaBatchGerarArquivoAgenciaVirtualDebitos arquivoAgenciaVirtualDebitos = new TarefaBatchGerarArquivoAgenciaVirtualDebitos(
											processoIniciado.getUsuario(), funcionalidadeIniciada.getId());

							FiltroSetorComercial filtroSetorComercialDebitos = new FiltroSetorComercial();
							Collection<SetorComercial> colSetorComercialDebitos = getControladorUtil().pesquisar(
											filtroSetorComercialDebitos, SetorComercial.class.getName());

							arquivoAgenciaVirtualDebitos.addParametro(ConstantesSistema.COLECAO_UNIDADES_PROCESSAMENTO_BATCH,
											colSetorComercialDebitos);

							// Seta o objeto para ser serializado no banco, onde
							// depois serï¿½ executado por uma thread
							funcionalidadeIniciada.setTarefaBatch(IoUtil.transformarObjetoParaBytes(arquivoAgenciaVirtualDebitos));

							getControladorUtil().atualizar(funcionalidadeIniciada);

							break;
						case Funcionalidade.CONCATENAR_ARQUIVOS_AGENCIA_VIRTUAL:

							TarefaBatchConcatenarArquivosAgenciaVirtual concatenarArquivoSAgenciaVirtual = new TarefaBatchConcatenarArquivosAgenciaVirtual(
											processoIniciado.getUsuario(), funcionalidadeIniciada.getId());

							// Seta o objeto para ser serializado no banco, onde
							// depois serï¿½ executado por uma thread
							funcionalidadeIniciada.setTarefaBatch(IoUtil.transformarObjetoParaBytes(concatenarArquivoSAgenciaVirtual));

							getControladorUtil().atualizar(funcionalidadeIniciada);

							break;
						case Funcionalidade.RETIRAR_CONTA_REVISAO_PRAZO_VENCIDO:

							TarefaBatchRetirarContaRevisaoPrazoVencido tarefaBatchRetirarContaRevisaoPrazoVencido = new TarefaBatchRetirarContaRevisaoPrazoVencido(
											processoIniciado.getUsuario(), funcionalidadeIniciada.getId());

							// Seta os parametros para rodar a funcionalidade
							tarefaBatchRetirarContaRevisaoPrazoVencido.addParametro("usuario", processoIniciado.getUsuario());

							// Seta o objeto para ser serializado no banco, onde
							// depois será executado por uma thread
							funcionalidadeIniciada.setTarefaBatch(IoUtil
											.transformarObjetoParaBytes(tarefaBatchRetirarContaRevisaoPrazoVencido));

							getControladorUtil().atualizar(funcionalidadeIniciada);

							break;
						case Funcionalidade.GERAR_INTEGRACAO_CONTABIL:

							TarefaBatchGerarIntegracaoContabil tarefaBatchGerarIntegracaoContabil = new TarefaBatchGerarIntegracaoContabil(
											processoIniciado.getUsuario(), funcionalidadeIniciada.getId());

							// Seta o objeto para ser serializado no banco, onde
							// depois serï¿½ executado por uma thread
							funcionalidadeIniciada.setTarefaBatch(IoUtil.transformarObjetoParaBytes(tarefaBatchGerarIntegracaoContabil));

							getControladorUtil().atualizar(funcionalidadeIniciada);

							break;

						case Funcionalidade.GERAR_INTEGRACAO_CONTABIL_AJUSTE_TEMP:

							TarefaBatchGerarIntegracaoContabilAjusteTemp tarefaBatchGerarIntegracaoContabilAjusteTemp = new TarefaBatchGerarIntegracaoContabilAjusteTemp(
											processoIniciado.getUsuario(), funcionalidadeIniciada.getId());

							// Seta o objeto para ser serializado no banco, onde
							// depois serï¿½ executado por uma thread
							funcionalidadeIniciada.setTarefaBatch(IoUtil
											.transformarObjetoParaBytes(tarefaBatchGerarIntegracaoContabilAjusteTemp));

							getControladorUtil().atualizar(funcionalidadeIniciada);

							break;
						case Funcionalidade.GERAR_INTEGRACAO_RETENCAO:

							TarefaBatchGerarIntegracaoRetencao tarefaBatchGerarIntegracaoRetencao = new TarefaBatchGerarIntegracaoRetencao(
											processoIniciado.getUsuario(), funcionalidadeIniciada.getId());

							// Seta o objeto para ser serializado no banco, onde
							// depois serï¿½ executado por uma thread
							funcionalidadeIniciada.setTarefaBatch(IoUtil.transformarObjetoParaBytes(tarefaBatchGerarIntegracaoRetencao));

							getControladorUtil().atualizar(funcionalidadeIniciada);

							break;
						case Funcionalidade.GERAR_INTEGRACAO_DEFERIMENTO:
							TarefaBatchGerarIntegracaoDeferimento tarefaBatchGerarIntegracaoDeferimento = new TarefaBatchGerarIntegracaoDeferimento(
											processoIniciado.getUsuario(), funcionalidadeIniciada.getId());
							funcionalidadeIniciada.setTarefaBatch(IoUtil.transformarObjetoParaBytes(tarefaBatchGerarIntegracaoDeferimento));
							getControladorUtil().atualizar(funcionalidadeIniciada);

							break;

						case Funcionalidade.ATUALIZAR_SITUACAO_DEBITO_E_DA_ACAO_DOS_AVISOS_CORTE_E_CORTE_INDIVIDUAL:
							TarefaBatchAtualizarSituacaoDebitoEDaAcaoDosAvisosCorteECorteIndividual atualizarSituacaoDebitoEDaAcaoDosAvisosCorteECorteIndividual = new TarefaBatchAtualizarSituacaoDebitoEDaAcaoDosAvisosCorteECorteIndividual(
											processoIniciado.getUsuario(), funcionalidadeIniciada.getId());
							atualizarSituacaoDebitoEDaAcaoDosAvisosCorteECorteIndividual.addParametro("idProcessoIniciado",
											codigoProcessoIniciadoGerado);
							funcionalidadeIniciada.setTarefaBatch(IoUtil
											.transformarObjetoParaBytes(atualizarSituacaoDebitoEDaAcaoDosAvisosCorteECorteIndividual));
							this.getControladorUtil().atualizar(funcionalidadeIniciada);

							break;

						case Funcionalidade.GERAR_PROVISAO_RECEITA:
							TarefaBatchGerarProvisaoReceita gerarProvisaoReceita = new TarefaBatchGerarProvisaoReceita(processoIniciado
											.getUsuario(), funcionalidadeIniciada.getId());

							FiltroSetorComercial novoFiltroSetor = new FiltroSetorComercial();
							Collection<SetorComercial> colecaoSetorComercialNova = getControladorUtil().pesquisar(novoFiltroSetor,
											SetorComercial.class.getName());

							gerarProvisaoReceita.addParametro(ConstantesSistema.COLECAO_UNIDADES_PROCESSAMENTO_BATCH,
											colecaoSetorComercialNova);

							funcionalidadeIniciada.setTarefaBatch(IoUtil.transformarObjetoParaBytes(gerarProvisaoReceita));

							getControladorUtil().atualizar(funcionalidadeIniciada);

							break;

						case Funcionalidade.ATUALIZAR_PDD_PARA_ENCERRAR_ARRECADACAO:
							TarefaBatchAtualizarPDDParaEncerrarArrecadacao dadosAtualizarPDDParaEncerrarArrecadacao = new TarefaBatchAtualizarPDDParaEncerrarArrecadacao(
											processoIniciado.getUsuario(), funcionalidadeIniciada.getId());

							// Seta o objeto para ser serializado no banco, onde
							// depois seria executado por uma thread
							funcionalidadeIniciada.setTarefaBatch(IoUtil
											.transformarObjetoParaBytes(dadosAtualizarPDDParaEncerrarArrecadacao));

							getControladorUtil().atualizar(funcionalidadeIniciada);

							break;

						case Funcionalidade.ENCERRAR_ARRECADACAO:
							TarefaBatchEncerrarArrecadacao dadosEncerrarArrecadacao = new TarefaBatchEncerrarArrecadacao(processoIniciado
											.getUsuario(), funcionalidadeIniciada.getId());

							// Seta o objeto para ser serializado no banco, onde
							// depois seria executado por uma thread
							funcionalidadeIniciada.setTarefaBatch(IoUtil.transformarObjetoParaBytes(dadosEncerrarArrecadacao));

							getControladorUtil().atualizar(funcionalidadeIniciada);

							break;
							
						case Funcionalidade.GERAR_INTEGRACAO_SPED_PIS_COFINS: // [OC777360][UC3078]
							criarTarefaBatchGerarIntegracaoSpedPisCofins(processoIniciado, funcionalidadeIniciada);
							break;

						case Funcionalidade.GERAR_INTEGRACAO_ACQUAGIS_CONTA_ATUALIZADA:

							LOGGER.info("=====> EXCLUINDO DADOS DA TABELA CONTA ATUALIZADA: <=====");
							// 1. Apagar toda a tabela CONTA_ATUALIZADA (ela precisa ter o valor
							// atualizado);
							try{
								repositorioIntegracaoAcquaGis.excluirContaAtualizada();
							}catch(ErroRepositorioException e){
								throw new ControladorException("erro.sistema", e);
							}

							TarefaBatchGerarIntegracaoAcquaGisContaAtualizada tarefaBatchGerarIntegracaoAcquaGisContaAtualizada = new TarefaBatchGerarIntegracaoAcquaGisContaAtualizada(
											processoIniciado.getUsuario(), funcionalidadeIniciada.getId());

							tarefaBatchGerarIntegracaoAcquaGisContaAtualizada.addParametro(
											TarefaBatchGerarIntegracaoSpedPisCofins.PARAM_IDS_LOCALIDADE, getControladorLocalidade()
															.pesquisarTodosIdsLocalidade());

							// Seta o objeto para ser serializado no banco, onde
							// depois serï¿½ executado por uma thread
							funcionalidadeIniciada.setTarefaBatch(IoUtil
											.transformarObjetoParaBytes(tarefaBatchGerarIntegracaoAcquaGisContaAtualizada));

							getControladorUtil().atualizar(funcionalidadeIniciada);

							break;

						case Funcionalidade.EFETIVAR_ATUALIZACAO_INSCRICAO_IMOVEL:
							TarefaBatchEfetivarAlteracaoInscricaoImovel efetivarAlteracaoInscricaoImovel = new TarefaBatchEfetivarAlteracaoInscricaoImovel(
											processoIniciado.getUsuario(), funcionalidadeIniciada.getId());

							funcionalidadeIniciada.setTarefaBatch(IoUtil.transformarObjetoParaBytes(efetivarAlteracaoInscricaoImovel));
							this.getControladorUtil().atualizar(funcionalidadeIniciada);

							break;

						case Funcionalidade.AJUSTAR_ARRECADADOR_MOVIMENTO_ITEM:
							TarefaBatchAjustarArrecadadorMovimentoItem tarefa = new TarefaBatchAjustarArrecadadorMovimentoItem(
											processoIniciado.getUsuario(), funcionalidadeIniciada.getId());

							funcionalidadeIniciada.setTarefaBatch(IoUtil.transformarObjetoParaBytes(tarefa));
							this.getControladorUtil().atualizar(funcionalidadeIniciada);

							break;
						case Funcionalidade.COMANDAR_PRESCRICAO_DEBITOS_AUTOMATICA:

							TarefaBatchComandarPrescricaoAutomaticaDebitos tarefaBatchComandarPrescricaoDebitosAutomatica = new TarefaBatchComandarPrescricaoAutomaticaDebitos(
											processoIniciado.getUsuario(), funcionalidadeIniciada.getId());

							funcionalidadeIniciada.setTarefaBatch(IoUtil
											.transformarObjetoParaBytes(tarefaBatchComandarPrescricaoDebitosAutomatica));

							this.getControladorUtil().atualizar(funcionalidadeIniciada);

							break;

						case Funcionalidade.GERAR_QUADRO_COMPARATIVO_FATURAMENTO_ARRECADACAO:
							TarefaBatchGerarQuadroComparativoFatEArrec tarefaQuadroComparativoFatEArrec = new TarefaBatchGerarQuadroComparativoFatEArrec(
											processoIniciado.getUsuario(), funcionalidadeIniciada.getId());

							// Seta o objeto para ser serializado no banco, onde
							// depois serï¿½ executado por uma thread
							funcionalidadeIniciada.setTarefaBatch(IoUtil.transformarObjetoParaBytes(tarefaQuadroComparativoFatEArrec));

							getControladorUtil().atualizar(funcionalidadeIniciada);

							break;

						default:
							LOGGER.warn("default");
					}

				}catch(IOException e){
					sessionContext.setRollbackOnly();
					throw new ControladorException("erro.sistema", e);

				}

			}

		}catch(ControladorException e){
			sessionContext.setRollbackOnly();
			throw e;

		}

		return codigoProcessoIniciadoGerado;

	}

	private void criarTarefaBatchGerarIntegracaoSpedPisCofins(ProcessoIniciado processoIniciado,
					FuncionalidadeIniciada funcionalidadeIniciada) throws ControladorException, IOException{

		TarefaBatchGerarIntegracaoSpedPisCofins tarefaIntegracao = new TarefaBatchGerarIntegracaoSpedPisCofins(processoIniciado
						.getUsuario(), funcionalidadeIniciada.getId());

		// tarefaIntegracao.addParametro(TarefaBatchGerarIntegracaoSpedPisCofins.PARAM_IDS_LOCALIDADE,
		// getControladorLocalidade()
		// .pesquisarTodosIdsLocalidade());

		funcionalidadeIniciada.setTarefaBatch(IoUtil.transformarObjetoParaBytes(tarefaIntegracao));

		this.getControladorUtil().atualizar(funcionalidadeIniciada);
	}

	/**
	 * Método preencherTarefaBatch
	 * <p>
	 * Esse método implementa a funcionalidade de preencher o campo blob da {@link TarefaBatch}.
	 * </p>
	 * RASTREIO: [OC1000594]
	 * 
	 * @param id
	 * @param dadosEncerrarArrecadacao
	 * @author Marlos Ribeiro
	 * @return
	 * @throws ControladorException
	 * @throws IOException
	 * @since 01/03/2013
	 */
	private int preencherTarefaBatch(Integer funcionalidadeIniciadaid, TarefaBatch tarefa) throws ControladorException, IOException{

		try{
			return repositorioBatch.preencherTarefaBatch(funcionalidadeIniciadaid, tarefa);
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}
	}

	/**
	 * @author eduardo henrique
	 * @date 26/08/2008 Alteração para passagem da Data Prevista de Execução da Atividade, na
	 *       geração de dados para Leitura.
	 * @author eduardo henrique
	 * @date 17/09/2008 Alteração para inclusão da Atividade de Faturamento
	 *       "Gerar Faturamento Imediato", para a v0.05
	 * @param idsFaturamentoAtividadeCronograma
	 * @param usuario
	 * @return
	 * @throws ControladorException
	 */
	public Integer inserirProcessoIniciadoFaturamentoComandado(Collection<Integer> idsFaturamentoAtividadeCronograma,
					Collection<Integer> idsFaturamentoSimulacaoComando,
					Date dataHoraAgendamento, Usuario usuario) throws ControladorException{

		Integer codigoProcessoIniciadoGerado = null;
		Date agora = new Date();
		try{


			ProcessoSituacao processoSituacao = new ProcessoSituacao();
			processoSituacao.setId(ProcessoSituacao.EM_ESPERA); // Ver

			FuncionalidadeSituacao funcionalidadeSituacao = new FuncionalidadeSituacao();
			funcionalidadeSituacao.setId(FuncionalidadeSituacao.EM_ESPERA);

			if(!Util.isVazioOrNulo(idsFaturamentoAtividadeCronograma)){

				Iterator<Integer> iteratorFaturamentoAtividadeCronograma = idsFaturamentoAtividadeCronograma.iterator();

				// Este trecho insere um processoIniciado e as
				// funcionalidadesIniciadas para cada FaturamentoAtividadeCronograma
				// informado pelo usuï¿½rio
				while(iteratorFaturamentoAtividadeCronograma.hasNext()){

					Integer codigoFaturamentoAtividadeCronograma = iteratorFaturamentoAtividadeCronograma.next();

					// Pesquisa as rotas associadas ao
					// FaturamentoAtividadeCronograma
					Collection colecaoFaturamentoAtivCronRota = repositorioBatch
									.pesquisarRotasProcessamentoBatchFaturamentoComandado(codigoFaturamentoAtividadeCronograma);

					// [FS0001] - Verificar existï¿½ncia de dados
					if(colecaoFaturamentoAtivCronRota == null || colecaoFaturamentoAtivCronRota.isEmpty()){
						throw new ControladorException("atencao.faturamento_nenhuma_rota");

					}

					// Busca o FaturamentoAtividade para obter os dados para montar
					// o processoIniciado
					FiltroFaturamentoAtividadeCronograma filtroFaturamentoAtividadeCronograma = new FiltroFaturamentoAtividadeCronograma();
					filtroFaturamentoAtividadeCronograma.adicionarParametro(new ParametroSimples(FiltroFaturamentoAtividadeCronograma.ID,
									codigoFaturamentoAtividadeCronograma));

					filtroFaturamentoAtividadeCronograma.adicionarCaminhoParaCarregamentoEntidade("faturamentoAtividade.processo");

					filtroFaturamentoAtividadeCronograma
									.adicionarCaminhoParaCarregamentoEntidade("faturamentoGrupoCronogramaMensal.faturamentoGrupo");

					FaturamentoAtividadeCronograma faturamentoAtividadeCronograma = (FaturamentoAtividadeCronograma) Util
									.retonarObjetoDeColecao(getControladorUtil().pesquisar(filtroFaturamentoAtividadeCronograma,
													FaturamentoAtividadeCronograma.class.getName()));

					// FS0011 – Verificar restrição de execução simultânea de processos
					if(this.isProcessoComRestricaoExecucaoSimultanea(faturamentoAtividadeCronograma.getFaturamentoAtividade().getProcesso()
									.getId())){
						sessionContext.setRollbackOnly();
						throw new ControladorException("atencao.processo_restricao_execucao");
					}

					// Constrï¿½i um processoIniciado para cada
					// FaturamentoAtividadeCronograma
					ProcessoIniciado processoIniciado = new ProcessoIniciado();
					processoIniciado.setUsuario(usuario);

					processoIniciado.setCodigoGrupoProcesso(faturamentoAtividadeCronograma.getFaturamentoGrupoCronogramaMensal()
									.getFaturamentoGrupo().getId());

					// isso
					processoIniciado.setProcessoSituacao(processoSituacao);

					processoIniciado.setProcesso(faturamentoAtividadeCronograma.getFaturamentoAtividade().getProcesso());
					processoIniciado.setDataHoraAgendamento(dataHoraAgendamento == null ? agora : dataHoraAgendamento);
					// processoIniciado.setDataHoraInicio(agora);
					processoIniciado.setDataHoraComando(faturamentoAtividadeCronograma.getComando());

					String descricaoDadosComplementares = this.getControladorFaturamento().obterDadosComplementaresComandoFaturamento(
									codigoFaturamentoAtividadeCronograma);
					processoIniciado.setDescricaoDadosComplementares(descricaoDadosComplementares);

					codigoProcessoIniciadoGerado = (Integer) getControladorUtil().inserir(processoIniciado);
					LOGGER.info("INCLUINDO PROCESSO INICIADO[" + codigoProcessoIniciadoGerado + "] - DADOS COMPLEMENTARES ["
									+ descricaoDadosComplementares + "]");

					// Este trecho pesquisa todos do processoFuncionalidade
					// relacionados
					// com o processo do objeto a ser inserido
					FiltroProcessoFuncionalidade filtroProcessoFuncionalidade = new FiltroProcessoFuncionalidade();

					filtroProcessoFuncionalidade.adicionarParametro(new ParametroSimples(FiltroProcessoFuncionalidade.ID_PROCESSO,
									processoIniciado.getProcesso().getId()));
					filtroProcessoFuncionalidade.adicionarParametro(new ParametroSimples(FiltroProcessoFuncionalidade.INDICADOR_USO,
									ConstantesSistema.INDICADOR_USO_ATIVO));

					Collection processosFuncionaliadade = getControladorUtil().pesquisar(filtroProcessoFuncionalidade,
									ProcessoFuncionalidade.class.getName());

					Iterator iterator = processosFuncionaliadade.iterator();
					while(iterator.hasNext()){
						ProcessoFuncionalidade processoFuncionalidade = (ProcessoFuncionalidade) iterator.next();
						FuncionalidadeIniciada funcionalidadeIniciada = new FuncionalidadeIniciada();

						funcionalidadeIniciada.setFuncionalidadeSituacao(funcionalidadeSituacao);

						funcionalidadeIniciada.setProcessoIniciado(processoIniciado);

						funcionalidadeIniciada.setProcessoFuncionalidade(processoFuncionalidade);

						funcionalidadeIniciada.setId((Integer) getControladorUtil().inserir(funcionalidadeIniciada));

						SistemaParametro sistemaParametro = getControladorUtil().pesquisarParametrosDoSistema();

						// Seta os parametros da funcionalidadeIniciada

						switch(funcionalidadeIniciada.getProcessoFuncionalidade().getFuncionalidade().getId()){
							case Funcionalidade.GERAR_DADOS_PARA_LEITURA:
								TarefaBatchGerarDadosParaLeitura dadosParaLeitura = new TarefaBatchGerarDadosParaLeitura(
												processoIniciado.getUsuario(), funcionalidadeIniciada.getId());

								// Seta os parametros para rodar a funcionalidade
								dadosParaLeitura.addParametro("dataPrevistaAtividade", faturamentoAtividadeCronograma.getDataPrevista());

								dadosParaLeitura.addParametro("anoMesFaturamentoGrupo", faturamentoAtividadeCronograma
												.getFaturamentoGrupoCronogramaMensal().getFaturamentoGrupo().getAnoMesReferencia());
								dadosParaLeitura.addParametro("idFaturamentoGrupo", faturamentoAtividadeCronograma
												.getFaturamentoGrupoCronogramaMensal().getFaturamentoGrupo().getId());

								dadosParaLeitura.addParametro("sistemaParametro", sistemaParametro);

								dadosParaLeitura.addParametro("rotas", colecaoFaturamentoAtivCronRota);

								// Seta o objeto para ser serializado no banco, onde
								// depois serï¿½ executado por uma thread
								funcionalidadeIniciada.setTarefaBatch(IoUtil.transformarObjetoParaBytes(dadosParaLeitura));

								getControladorUtil().atualizar(funcionalidadeIniciada);

								break;

							case Funcionalidade.GERAR_ARQUIVO_TEXTO_FATURAMENTO_IMEDIATO:
								TarefaBatchGerarArquivoTextoFaturamentoImediato dadosParaArquivoTextoFaturamentoImediato = new TarefaBatchGerarArquivoTextoFaturamentoImediato(
												processoIniciado.getUsuario(), funcionalidadeIniciada.getId());

								// Seta os parametros para rodar a funcionalidade
								dadosParaArquivoTextoFaturamentoImediato.addParametro("dataPrevistaAtividade",
												faturamentoAtividadeCronograma.getDataPrevista());

								dadosParaArquivoTextoFaturamentoImediato.addParametro("anoMesFaturamentoGrupo",
												faturamentoAtividadeCronograma.getFaturamentoGrupoCronogramaMensal().getFaturamentoGrupo()
																.getAnoMesReferencia());
								dadosParaArquivoTextoFaturamentoImediato.addParametro("idFaturamentoGrupo", faturamentoAtividadeCronograma
												.getFaturamentoGrupoCronogramaMensal().getFaturamentoGrupo().getId());

								dadosParaArquivoTextoFaturamentoImediato.addParametro("sistemaParametro", sistemaParametro);

								dadosParaArquivoTextoFaturamentoImediato.addParametro("rotas", colecaoFaturamentoAtivCronRota);

								// Seta o objeto para ser serializado no banco, onde
								// depois serï¿½ executado por uma thread
								funcionalidadeIniciada.setTarefaBatch(IoUtil
												.transformarObjetoParaBytes(dadosParaArquivoTextoFaturamentoImediato));

								if(colecaoFaturamentoAtivCronRota == null || colecaoFaturamentoAtivCronRota.isEmpty()){
									funcionalidadeSituacao.setId(FuncionalidadeSituacao.CONCLUIDA);
								}

								getControladorUtil().atualizar(funcionalidadeIniciada);

								break;

							// pedro alexandre dia 13/09/2007
							case Funcionalidade.GERAR_ARQUIVO_TEXTO_PARA_LEITURISTA:
								TarefaBatchGerarArquivoTextoParaLeiturista dadosGerarArquivoTextoParaLeiturista = new TarefaBatchGerarArquivoTextoParaLeiturista(
												processoIniciado.getUsuario(), funcionalidadeIniciada.getId());

								Collection colecaoRoteirosEmpresa = new ArrayList();

								Collection colecaoRotas = new ArrayList();

								Short indicadorRoteiroEmpresa = sistemaParametro.getIndicadorRoteiroEmpresa();

								/*
								 * Caso o indicador de roteiro empresa esteja com valor igual a 1
								 * (SIM)
								 * processar todos que estï¿½o na lista de roteiro empresa
								 * Caso contrario, processar todas as rotas que estï¿½o na lista de
								 * rotas.
								 */
								if(indicadorRoteiroEmpresa.equals(ConstantesSistema.SIM)){
									colecaoRoteirosEmpresa = getControladorMicromedicao().pesquisarRoteiroEmpresaPorGrupoFaturamento(
													faturamentoAtividadeCronograma.getFaturamentoGrupoCronogramaMensal()
																	.getFaturamentoGrupo().getId());
								}else{
									colecaoRotas = getControladorMicromedicao().pesquisarRotaPorGrupoFaturamento(
													faturamentoAtividadeCronograma.getFaturamentoGrupoCronogramaMensal()
																	.getFaturamentoGrupo().getId());
								}

								// Seta os parametros para rodar a funcionalidade
								dadosGerarArquivoTextoParaLeiturista.addParametro(ConstantesSistema.COLECAO_UNIDADES_PROCESSAMENTO_BATCH,
												colecaoRoteirosEmpresa);

								dadosGerarArquivoTextoParaLeiturista.addParametro("colecaoRotas", colecaoRotas);

								dadosGerarArquivoTextoParaLeiturista.addParametro("anoMesFaturamento",
												sistemaParametro.getAnoMesFaturamento());

								dadosGerarArquivoTextoParaLeiturista.addParametro("faturamentoGrupo", faturamentoAtividadeCronograma
												.getFaturamentoGrupoCronogramaMensal().getFaturamentoGrupo());

								// Seta o objeto para ser serializado no banco, onde
								// depois serï¿½ executado por uma thread
								funcionalidadeIniciada.setTarefaBatch(IoUtil
												.transformarObjetoParaBytes(dadosGerarArquivoTextoParaLeiturista));

								getControladorUtil().atualizar(funcionalidadeIniciada);

								break;

							case Funcionalidade.CONSISTIR_LEITURAS_E_CALCULAR_CONSUMOS:
								TarefaBatchConsistirLeiturasCalcularConsumos calcularConsumos = new TarefaBatchConsistirLeiturasCalcularConsumos(
												processoIniciado.getUsuario(), funcionalidadeIniciada.getId());

								// Seta os parametros para rodar a funcionalidade
								calcularConsumos.addParametro("faturamentoGrupo", faturamentoAtividadeCronograma
												.getFaturamentoGrupoCronogramaMensal().getFaturamentoGrupo());
								calcularConsumos.addParametro(ConstantesSistema.COLECAO_UNIDADES_PROCESSAMENTO_BATCH,
												colecaoFaturamentoAtivCronRota);

								sistemaParametro.setAnoMesFaturamento(faturamentoAtividadeCronograma.getFaturamentoGrupoCronogramaMensal()
												.getAnoMesReferencia());

								calcularConsumos.addParametro("sistemaParametros", sistemaParametro);

								// Seta o objeto para ser serializado no banco, onde
								// depois serï¿½ executado por uma thread
								funcionalidadeIniciada.setTarefaBatch(IoUtil.transformarObjetoParaBytes(calcularConsumos));

								getControladorUtil().atualizar(funcionalidadeIniciada);

								break;
							case Funcionalidade.GERAR_FATURAMENTO_IMEDIATO:

								/*
								 * TarefaBatchGerarFaturamentoImediato faturamentoImediato = new
								 * TarefaBatchGerarFaturamentoImediato(
								 * processoIniciado.getUsuario(), funcionalidadeIniciada.getId());
								 * // Seta os parametros para rodar a funcionalidade
								 * faturamentoImediato.addParametro("anoMesFaturamentoGrupo",
								 * faturamentoAtividadeCronograma
								 * .getFaturamentoGrupoCronogramaMensal().getFaturamentoGrupo().
								 * getAnoMesReferencia());
								 * faturamentoImediato.addParametro("faturamentoGrupo",
								 * faturamentoAtividadeCronograma
								 * .getFaturamentoGrupoCronogramaMensal().getFaturamentoGrupo());
								 * faturamentoImediato.addParametro("rotas",
								 * colecaoFaturamentoAtivCronRota);
								 * faturamentoImediato.addParametro("atividade",
								 * faturamentoAtividadeCronograma.getFaturamentoAtividade().getId());
								 * // Seta o objeto para ser serializado no banco, onde
								 * // depois serï¿½ executado por uma thread
								 * funcionalidadeIniciada.setTarefaBatch(IoUtil.
								 * transformarObjetoParaBytes
								 * (faturamentoImediato));
								 * getControladorUtil().atualizar(funcionalidadeIniciada);
								 * break;
								 */
							case Funcionalidade.REGISTRAR_FATURAMENTO_IMEDIATO:
								TarefaBatchRegistrarFaturamentoImediato registrarFaturamentoImediato = new TarefaBatchRegistrarFaturamentoImediato(
												processoIniciado.getUsuario(), funcionalidadeIniciada.getId());

								// Seta os parametros para rodar a funcionalidade
								registrarFaturamentoImediato.addParametro("anoMesFaturamentoGrupo", faturamentoAtividadeCronograma
												.getFaturamentoGrupoCronogramaMensal().getFaturamentoGrupo().getAnoMesReferencia());
								registrarFaturamentoImediato.addParametro("faturamentoGrupo", faturamentoAtividadeCronograma
												.getFaturamentoGrupoCronogramaMensal().getFaturamentoGrupo());

								registrarFaturamentoImediato.addParametro("rotas", colecaoFaturamentoAtivCronRota);

								registrarFaturamentoImediato.addParametro("atividade", faturamentoAtividadeCronograma
												.getFaturamentoAtividade().getId());

								// Seta o objeto para ser serializado no banco, onde
								// depois serï¿½ executado por uma thread
								funcionalidadeIniciada.setTarefaBatch(IoUtil.transformarObjetoParaBytes(registrarFaturamentoImediato));

								getControladorUtil().atualizar(funcionalidadeIniciada);

								break;
							case Funcionalidade.EFETUAR_RATEIO_CONSUMO:
								TarefaBatchEfetuarRateioConsumo rateioConsumo = new TarefaBatchEfetuarRateioConsumo(
												processoIniciado.getUsuario(), funcionalidadeIniciada.getId());

								// Seta os parametros para rodar a funcionalidade
								rateioConsumo.addParametro("anoMesFaturamentoGrupo", faturamentoAtividadeCronograma
												.getFaturamentoGrupoCronogramaMensal().getFaturamentoGrupo().getAnoMesReferencia());
								rateioConsumo.addParametro(ConstantesSistema.COLECAO_UNIDADES_PROCESSAMENTO_BATCH,
												colecaoFaturamentoAtivCronRota);

								// Seta o objeto para ser serializado no banco, onde
								// depois serï¿½ executado por uma thread
								funcionalidadeIniciada.setTarefaBatch(IoUtil.transformarObjetoParaBytes(rateioConsumo));

								getControladorUtil().atualizar(funcionalidadeIniciada);

								break;
							case Funcionalidade.VERIFICAR_ANORMALIDADES_CONSUMO:
								TarefaBatchVerificarAnormalidadesConsumo verificarAnormalidadesConsumo = new TarefaBatchVerificarAnormalidadesConsumo(
												processoIniciado.getUsuario(), funcionalidadeIniciada.getId());

								// Seta os parametros para rodar a funcionalidade
								verificarAnormalidadesConsumo.addParametro("anoMesFaturamentoGrupo", faturamentoAtividadeCronograma
												.getFaturamentoGrupoCronogramaMensal().getFaturamentoGrupo().getAnoMesReferencia());
								verificarAnormalidadesConsumo.addParametro("faturamentoGrupo", faturamentoAtividadeCronograma
												.getFaturamentoGrupoCronogramaMensal().getFaturamentoGrupo());
								verificarAnormalidadesConsumo.addParametro(ConstantesSistema.COLECAO_UNIDADES_PROCESSAMENTO_BATCH,
												colecaoFaturamentoAtivCronRota);

								// Seta o objeto para ser serializado no banco, onde
								// depois serï¿½ executado por uma thread
								funcionalidadeIniciada.setTarefaBatch(IoUtil.transformarObjetoParaBytes(verificarAnormalidadesConsumo));

								getControladorUtil().atualizar(funcionalidadeIniciada);

								break;
							case Funcionalidade.FATURAR_GRUPO_FATURAMENTO:
								TarefaBatchFaturarGrupoFaturamento faturarGrupoFaturamento = new TarefaBatchFaturarGrupoFaturamento(
												processoIniciado.getUsuario(), funcionalidadeIniciada.getId());

								// Seta os parametros para rodar a funcionalidade
								faturarGrupoFaturamento.addParametro("faturamentoGrupo", faturamentoAtividadeCronograma
												.getFaturamentoGrupoCronogramaMensal().getFaturamentoGrupo());
								faturarGrupoFaturamento.addParametro("anoMesFaturamentoGrupo", faturamentoAtividadeCronograma
												.getFaturamentoGrupoCronogramaMensal().getFaturamentoGrupo().getAnoMesReferencia());

								faturarGrupoFaturamento.addParametro("atividade", faturamentoAtividadeCronograma.getFaturamentoAtividade()
												.getId());

								faturarGrupoFaturamento.addParametro(ConstantesSistema.COLECAO_UNIDADES_PROCESSAMENTO_BATCH,
												colecaoFaturamentoAtivCronRota);

								// Seta o objeto para ser serializado no banco, onde
								// depois serï¿½ executado por uma thread
								funcionalidadeIniciada.setTarefaBatch(IoUtil.transformarObjetoParaBytes(faturarGrupoFaturamento));

								getControladorUtil().atualizar(funcionalidadeIniciada);

								break;
							case Funcionalidade.GERAR_TAXA_ENTREGA_CONTA_OUTRO_ENDERECO:
								TarefaBatchGerarTaxaEntregaContaOutroEndereco taxaEntrega = new TarefaBatchGerarTaxaEntregaContaOutroEndereco(
												processoIniciado.getUsuario(), funcionalidadeIniciada.getId());

								// Seta os parametros para rodar a funcionalidade
								taxaEntrega.addParametro("anoMesFaturamentoGrupo", faturamentoAtividadeCronograma
												.getFaturamentoGrupoCronogramaMensal().getFaturamentoGrupo().getAnoMesReferencia());
								taxaEntrega.addParametro(ConstantesSistema.COLECAO_UNIDADES_PROCESSAMENTO_BATCH,
												colecaoFaturamentoAtivCronRota);

								// Seta o objeto para ser serializado no banco, onde
								// depois serï¿½ executado por uma thread
								funcionalidadeIniciada.setTarefaBatch(IoUtil.transformarObjetoParaBytes(taxaEntrega));

								getControladorUtil().atualizar(funcionalidadeIniciada);

								break;

							case Funcionalidade.GERAR_DEBITOS_A_COBRAR_ACRESCIMOS_IMPONTUALIDADE:
								TarefaBatchGerarDebitosACobrarAcrescimosImpontualidade impontualidade = new TarefaBatchGerarDebitosACobrarAcrescimosImpontualidade(
												processoIniciado.getUsuario(), funcionalidadeIniciada.getId());

								/** Caso for o processo de encerrar arrecadaï¿½ï¿½o do mï¿½s */
								if(processoIniciado.getProcesso().getId().equals(Processo.ENCERRAR_ARRECADACAO_MES)){

									LOGGER.info("ENCERRAR ARRECADACAO DO MÊS");
									// ENCERRAR ARRECADACAO DO Mï¿½S
									Collection colecaoTodasRotas = getControladorMicromedicao().pesquisarListaRotasCarregadas();

									impontualidade.addParametro(ConstantesSistema.COLECAO_UNIDADES_PROCESSAMENTO_BATCH, colecaoTodasRotas);

									impontualidade.addParametro("indicadorGeracaoMulta", ConstantesSistema.SIM);

									impontualidade.addParametro("indicadorGeracaoJuros", ConstantesSistema.SIM);

									impontualidade.addParametro("indicadorGeracaoAtualizacao", ConstantesSistema.SIM);

									impontualidade.addParametro("indicadorEncerrandoArrecadacao", true);

								}else{
									LOGGER.info("FATURAR GRUPO DE FATURAMENTO");
									// FATURAR GRUPO DE FATURAMENTO
									impontualidade.addParametro(ConstantesSistema.COLECAO_UNIDADES_PROCESSAMENTO_BATCH,
													colecaoFaturamentoAtivCronRota);

									impontualidade.addParametro("indicadorGeracaoMulta", ConstantesSistema.SIM);

									impontualidade.addParametro("indicadorGeracaoJuros", ConstantesSistema.NAO);

									impontualidade.addParametro("indicadorGeracaoAtualizacao", ConstantesSistema.NAO);

									impontualidade.addParametro("indicadorEncerrandoArrecadacao", false);

								}

								// Seta o objeto para ser serializado no banco, onde
								// depois serï¿½ executado por uma thread
								funcionalidadeIniciada.setTarefaBatch(IoUtil.transformarObjetoParaBytes(impontualidade));

								getControladorUtil().atualizar(funcionalidadeIniciada);

								break;

							case Funcionalidade.EMITIR_CONTAS:
								TarefaBatchEmitirContas emitirContas = new TarefaBatchEmitirContas(processoIniciado.getUsuario(),
												funcionalidadeIniciada.getId());

								// Seta os parametros para rodar a funcionalidade
								emitirContas.addParametro("anoMesFaturamentoGrupo", faturamentoAtividadeCronograma
												.getFaturamentoGrupoCronogramaMensal().getFaturamentoGrupo().getAnoMesReferencia());

								emitirContas.addParametro("faturamentoGrupo", faturamentoAtividadeCronograma
												.getFaturamentoGrupoCronogramaMensal().getFaturamentoGrupo());

								Collection colecaoIdsEmpresas = getControladorCadastro().pesquisarIdsEmpresa();

								emitirContas.addParametro(ConstantesSistema.COLECAO_UNIDADES_PROCESSAMENTO_BATCH, colecaoIdsEmpresas);

								// Seta o objeto para ser serializado no banco, onde
								// depois serï¿½ executado por uma thread
								funcionalidadeIniciada.setTarefaBatch(IoUtil.transformarObjetoParaBytes(emitirContas));

								getControladorUtil().atualizar(funcionalidadeIniciada);

								break;

							case Funcionalidade.GERAR_DEBITO_COBRAR_DOACAO:
								TarefaBatchGerarDebitosACobrarDoacao gerarDebitoACobrarDoacao = new TarefaBatchGerarDebitosACobrarDoacao(
												processoIniciado.getUsuario(), funcionalidadeIniciada.getId());

								gerarDebitoACobrarDoacao.addParametro(ConstantesSistema.COLECAO_UNIDADES_PROCESSAMENTO_BATCH,
												colecaoFaturamentoAtivCronRota);

								// Seta o objeto para ser serializado no banco, onde
								// depois serï¿½ executado por uma thread
								funcionalidadeIniciada.setTarefaBatch(IoUtil.transformarObjetoParaBytes(gerarDebitoACobrarDoacao));

								getControladorUtil().atualizar(funcionalidadeIniciada);

								break;

							case Funcionalidade.EMITIR_EXTRATO_CONSUMO_IMOVEL_CONDOMINIO:
								TarefaBatchEmitirExtratoConsumoImovelCondominio emitirExtratoConsumoImovelCondominio = new TarefaBatchEmitirExtratoConsumoImovelCondominio(
												processoIniciado.getUsuario(), funcionalidadeIniciada.getId());

								// Seta os parametros para rodar a funcionalidade
								emitirExtratoConsumoImovelCondominio.addParametro("anoMesFaturamentoGrupo", faturamentoAtividadeCronograma
												.getFaturamentoGrupoCronogramaMensal().getFaturamentoGrupo().getAnoMesReferencia());

								emitirExtratoConsumoImovelCondominio.addParametro("faturamentoGrupo", faturamentoAtividadeCronograma
												.getFaturamentoGrupoCronogramaMensal().getFaturamentoGrupo());

								// Seta o objeto para ser serializado no banco, onde
								// depois serï¿½ executado por uma thread
								funcionalidadeIniciada.setTarefaBatch(IoUtil
												.transformarObjetoParaBytes(emitirExtratoConsumoImovelCondominio));

								getControladorUtil().atualizar(funcionalidadeIniciada);

								break;

							case Funcionalidade.GERAR_MULTA_POR_DESCUMPRIMENTO_PARCELAMENTO:
								TarefaBatchGerarMultaPorDescumprimentoParcelamento gerarMultaPorDescumprimentoParcelamento = new TarefaBatchGerarMultaPorDescumprimentoParcelamento(
												processoIniciado.getUsuario(), funcionalidadeIniciada.getId());

								// Seta os parametros para rodar a funcionalidade
								gerarMultaPorDescumprimentoParcelamento.addParametro("anoMesFaturamentoGrupo",
												faturamentoAtividadeCronograma.getFaturamentoGrupoCronogramaMensal().getFaturamentoGrupo()
																.getAnoMesReferencia());
								gerarMultaPorDescumprimentoParcelamento.addParametro("faturamentoGrupo", faturamentoAtividadeCronograma
												.getFaturamentoGrupoCronogramaMensal().getFaturamentoGrupo());
								gerarMultaPorDescumprimentoParcelamento.addParametro(
												ConstantesSistema.COLECAO_UNIDADES_PROCESSAMENTO_BATCH, colecaoFaturamentoAtivCronRota);

								// Seta o objeto para ser serializado no banco, onde
								// depois será executado por uma thread
								funcionalidadeIniciada.setTarefaBatch(IoUtil
												.transformarObjetoParaBytes(gerarMultaPorDescumprimentoParcelamento));

								getControladorUtil().atualizar(funcionalidadeIniciada);

								break;

							case Funcionalidade.GERAR_AVISO_CORTE_FATURAMENTO:
								TarefaBatchGerarAvisoCorte tarefaBatchGerarAvisoCorte = new TarefaBatchGerarAvisoCorte(
												processoIniciado.getUsuario(), funcionalidadeIniciada.getId());

								// Seta os parametros para rodar a funcionalidade
								tarefaBatchGerarAvisoCorte.addParametro(ConstantesSistema.COLECAO_UNIDADES_PROCESSAMENTO_BATCH,
												colecaoFaturamentoAtivCronRota);
								tarefaBatchGerarAvisoCorte.addParametro("anoMesFaturamentoGrupo", faturamentoAtividadeCronograma
												.getFaturamentoGrupoCronogramaMensal().getFaturamentoGrupo().getAnoMesReferencia());
								tarefaBatchGerarAvisoCorte.addParametro("faturamentoGrupo", faturamentoAtividadeCronograma
												.getFaturamentoGrupoCronogramaMensal().getFaturamentoGrupo());
								tarefaBatchGerarAvisoCorte.addParametro("faturamentoAtividade",
												faturamentoAtividadeCronograma.getFaturamentoAtividade());
								tarefaBatchGerarAvisoCorte.addParametro("faturamentoGrupoCronogramaMensal",
												faturamentoAtividadeCronograma.getFaturamentoGrupoCronogramaMensal());
								tarefaBatchGerarAvisoCorte.addParametro("idProcessoIniciado", codigoProcessoIniciadoGerado);

								// Seta o objeto para ser serializado no banco, onde
								// depois será executado por uma thread
								funcionalidadeIniciada.setTarefaBatch(IoUtil.transformarObjetoParaBytes(tarefaBatchGerarAvisoCorte));

								getControladorUtil().atualizar(funcionalidadeIniciada);

								break;

							case Funcionalidade.GERAR_DECLARACAO_ANUAL_QUITACAO_DEBITOS:
								TarefaBatchGerarDeclaracaoAnualQuitacaoDebitos tarefaBatchGerarDeclaracaoAnualQuitacaoDebitos = new TarefaBatchGerarDeclaracaoAnualQuitacaoDebitos(
												processoIniciado.getUsuario(), funcionalidadeIniciada.getId());

								// Seta os parametros para rodar a funcionalidade
								tarefaBatchGerarDeclaracaoAnualQuitacaoDebitos.addParametro("faturamentoGrupo",
												faturamentoAtividadeCronograma.getFaturamentoGrupoCronogramaMensal().getFaturamentoGrupo());

								// Seta o objeto para ser serializado no banco, onde
								// depois será executado por uma thread
								funcionalidadeIniciada.setTarefaBatch(IoUtil
												.transformarObjetoParaBytes(tarefaBatchGerarDeclaracaoAnualQuitacaoDebitos));

								getControladorUtil().atualizar(funcionalidadeIniciada);

								break;

							case Funcionalidade.CANCELAR_AVISO_DE_CORTE_PENDENTE:
								TarefaBatchCancelarAvisoDeCortePendente tarefaBatchCancelarAvisoDeCortePendente = new TarefaBatchCancelarAvisoDeCortePendente(
												processoIniciado.getUsuario(), funcionalidadeIniciada.getId());

								tarefaBatchCancelarAvisoDeCortePendente.addParametro("faturamentoGrupo", faturamentoAtividadeCronograma
												.getFaturamentoGrupoCronogramaMensal().getFaturamentoGrupo());

								funcionalidadeIniciada.setTarefaBatch(IoUtil
												.transformarObjetoParaBytes(tarefaBatchCancelarAvisoDeCortePendente));

								this.getControladorUtil().atualizar(funcionalidadeIniciada);

								break;

							default:

						}
					}
				}
			}else if(!Util.isVazioOrNulo(idsFaturamentoSimulacaoComando)){

				for(Integer idFaturamentoSimulacaoComando : idsFaturamentoSimulacaoComando){

					ProcessoIniciado processoIniciado = new ProcessoIniciado();
					processoIniciado.setUsuario(usuario);
					processoIniciado.setProcessoSituacao(processoSituacao);

					FiltroFaturamentoAtividade filtroFaturamentoAtividade = new FiltroFaturamentoAtividade();
					filtroFaturamentoAtividade.adicionarParametro(new ParametroSimples(FiltroFaturamentoAtividade.ID,
									FaturamentoAtividade.SIMULAR_FATURAMENTO));
					filtroFaturamentoAtividade.adicionarCaminhoParaCarregamentoEntidade(FiltroFaturamentoAtividade.PROCESSO);

					Collection<FaturamentoAtividade> colecaoFaturamentoAtividade = getControladorUtil().pesquisar(
									filtroFaturamentoAtividade, FaturamentoAtividade.class.getName());
					FaturamentoAtividade faturamentoAtividade = (FaturamentoAtividade) Util
									.retonarObjetoDeColecao(colecaoFaturamentoAtividade);

					processoIniciado.setProcesso(faturamentoAtividade.getProcesso());
					processoIniciado.setDataHoraAgendamento(agora);

					FiltroFaturamentoSimulacaoComando filtroFaturamentoSimulacaoComando = new FiltroFaturamentoSimulacaoComando();
					filtroFaturamentoSimulacaoComando.adicionarParametro(new ParametroSimples(FiltroFaturamentoSimulacaoComando.ID,
									idFaturamentoSimulacaoComando));
					filtroFaturamentoSimulacaoComando
									.adicionarCaminhoParaCarregamentoEntidade(FiltroFaturamentoSimulacaoComando.FATURAMENTO_GRUPO);
					filtroFaturamentoSimulacaoComando
									.adicionarCaminhoParaCarregamentoEntidade(FiltroFaturamentoSimulacaoComando.CONSUMO_TARIFA);

					Collection<FaturamentoSimulacaoComando> colecaoFaturamentoSimulacaoComando = getControladorUtil().pesquisar(
									filtroFaturamentoSimulacaoComando, FaturamentoSimulacaoComando.class.getName());
					FaturamentoSimulacaoComando faturamentoSimulacaoComando = (FaturamentoSimulacaoComando) Util
									.retonarObjetoDeColecao(colecaoFaturamentoSimulacaoComando);

					processoIniciado.setDataHoraComando(faturamentoSimulacaoComando.getDataComando());

					if(faturamentoSimulacaoComando.getFaturamentoGrupo() != null){

						processoIniciado.setCodigoGrupoProcesso(faturamentoSimulacaoComando.getFaturamentoGrupo().getId());
					}

					// [SB0009 - Obter Dados Complementares do Comando de Simulação de Faturamento]
					String descricaoDadosComplementares = this.getControladorFaturamento()
									.obterDadosComplementaresComandoSimulacaoFaturamento(faturamentoSimulacaoComando);
					processoIniciado.setDescricaoDadosComplementares(descricaoDadosComplementares);

					codigoProcessoIniciadoGerado = (Integer) getControladorUtil().inserir(processoIniciado);
					LOGGER.info("INCLUINDO PROCESSO INICIADO[" + codigoProcessoIniciadoGerado + "] - DADOS COMPLEMENTARES ["
									+ descricaoDadosComplementares + "]");

					// Este trecho pesquisa todos do processoFuncionalidade
					// relacionados
					// com o processo do objeto a ser inserido
					FiltroProcessoFuncionalidade filtroProcessoFuncionalidade = new FiltroProcessoFuncionalidade();

					filtroProcessoFuncionalidade.adicionarParametro(new ParametroSimples(FiltroProcessoFuncionalidade.ID_PROCESSO,
									processoIniciado.getProcesso().getId()));
					filtroProcessoFuncionalidade.adicionarParametro(new ParametroSimples(FiltroProcessoFuncionalidade.INDICADOR_USO,
									ConstantesSistema.INDICADOR_USO_ATIVO));

					Collection processosFuncionaliadade = getControladorUtil().pesquisar(filtroProcessoFuncionalidade,
									ProcessoFuncionalidade.class.getName());

					SistemaParametro sistemaParametro = getControladorUtil().pesquisarParametrosDoSistema();

					Iterator iteratorProcessosFuncionalidade = processosFuncionaliadade.iterator();
					while(iteratorProcessosFuncionalidade.hasNext()){

						ProcessoFuncionalidade processoFuncionalidade = (ProcessoFuncionalidade) iteratorProcessosFuncionalidade.next();

						FuncionalidadeIniciada funcionalidadeIniciada = new FuncionalidadeIniciada();

						funcionalidadeIniciada.setFuncionalidadeSituacao(funcionalidadeSituacao);

						funcionalidadeIniciada.setProcessoIniciado(processoIniciado);

						funcionalidadeIniciada.setProcessoFuncionalidade(processoFuncionalidade);

						funcionalidadeIniciada.setId((Integer) getControladorUtil().inserir(funcionalidadeIniciada));

						if(processoFuncionalidade.getFuncionalidade().getId().equals(Funcionalidade.SIMULAR_FATURAMENTO)){

							TarefaBatchSimularFaturamento tarefaSimularFaturamento = new TarefaBatchSimularFaturamento(
											processoIniciado.getUsuario(), funcionalidadeIniciada.getId());

							tarefaSimularFaturamento.addParametro("anoMesFaturamentoCorrente", sistemaParametro.getAnoMesFaturamento());

							tarefaSimularFaturamento.addParametro("idFaturamentoAtividade", faturamentoAtividade.getId());

							tarefaSimularFaturamento.addParametro("faturamentoSimulacaoComando", faturamentoSimulacaoComando);

							// Seta o objeto para ser serializado no banco, onde depois será
							// executado por uma thread
							funcionalidadeIniciada.setTarefaBatch(IoUtil.transformarObjetoParaBytes(tarefaSimularFaturamento));

							getControladorUtil().atualizar(funcionalidadeIniciada);
						}
					}
				}
			}
		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}catch(IOException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

		return codigoProcessoIniciadoGerado;
	}

	/**
	 * Monta um objeto ParametrosHelper com as informações necessárias para o processo de emissão de
	 * documentos de cobrança quando gerados a partir do processo de Faturamento
	 * 
	 * @param faturamentoAtividadeCronograma
	 * @param usuario
	 * @return ParametrosHelper
	 */
	private ParametrosHelper montarParametrosHelperFaturamento(FaturamentoAtividadeCronograma faturamentoAtividadeCronograma,
					Usuario usuario){

		ParametrosHelper helper = new ParametrosHelper();

		FiltroCobrancaAcao filtroAcaoCobranca = new FiltroCobrancaAcao();
		filtroAcaoCobranca.adicionarParametro(new ParametroSimples(FiltroCobrancaAcao.ID, CobrancaAcao.AVISO_CORTE));
		filtroAcaoCobranca.adicionarCaminhoParaCarregamentoEntidade(FiltroCobrancaAcao.DOCUMENTO_TIPO);

		CobrancaAcao acaoCobranca = (CobrancaAcao) Util.retonarObjetoDeColecao(Fachada.getInstancia().pesquisar(filtroAcaoCobranca,
						CobrancaAcao.class.getName()));

		// Parâmetro utilizado quando o aviso de corte é gerado a partir do faturamento
		if((faturamentoAtividadeCronograma != null) && (faturamentoAtividadeCronograma.getFaturamentoGrupoCronogramaMensal() != null)){
			helper.setFaturamentoGrupoCronogramaMensalId(faturamentoAtividadeCronograma.getFaturamentoGrupoCronogramaMensal().getId());
		}

		helper.setAcaoCobranca(acaoCobranca);
		helper.setCobrancaAcaoAtividadeComando(null);
		helper.setCobrancaAcaoAtividadeCronograma(null);
		helper.setCriterioCobranca(null);
		helper.setDataAtual(new Date());
		helper.setGrupoCobranca(null);
		helper.setUsuario(usuario);

		return helper;
	}

	public Integer inserirProcessoIniciadoCobrancaComandado(Collection<Integer> idsCronograma, Collection<Integer> idsEventuais,
					Date dataHoraAgendamento, Usuario usuario, Integer idProcessoIniciadoVinculado,
					Integer idFaturamentoGrupoCronogramaMensal) throws ControladorException{

		Integer codigoProcessoIniciadoGerado = null;
		try{

			Iterator<Integer> iteratorCronograma = idsCronograma.iterator();

			Iterator<Integer> iteratorAtividade = idsEventuais.iterator();

			if(iteratorCronograma.hasNext()){
				codigoProcessoIniciadoGerado = this.inserirProcessoCobrancaAtividadeCronograma(usuario, codigoProcessoIniciadoGerado,
								iteratorCronograma, dataHoraAgendamento, idProcessoIniciadoVinculado, idFaturamentoGrupoCronogramaMensal);
			}

			if(iteratorAtividade.hasNext()){
				codigoProcessoIniciadoGerado = this.inserirProcessoCobrancaAtividadeEventual(usuario, codigoProcessoIniciadoGerado,
								iteratorAtividade, dataHoraAgendamento, idProcessoIniciadoVinculado, idFaturamentoGrupoCronogramaMensal);
			}

		}catch(IOException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}catch(ParseException e){
		}

		return codigoProcessoIniciadoGerado;

	}

	private Integer inserirProcessoCobrancaAtividadeCronograma(Usuario usuario, Integer codigoProcessoIniciadoGerado,
					Iterator<Integer> iteratorCronograma, Date dataHoraAgendamento, Integer idProcessoIniciadoVinculado,
					Integer idFaturamentoGrupoCronogramaMensal) throws ControladorException, ParseException, IOException{

		final ParametrosHelper helper = new ParametrosHelper();
		Date agora = new Date();

		ProcessoSituacao processoSituacao = new ProcessoSituacao();
		processoSituacao.setId(ProcessoSituacao.EM_ESPERA);

		FuncionalidadeSituacao funcionalidadeSituacao = new FuncionalidadeSituacao();
		funcionalidadeSituacao.setId(FuncionalidadeSituacao.EM_ESPERA);

		// Este trecho insere um processoIniciado e as
		// funcionalidadesIniciadas para cada
		// CobrancaAcaoAtividadeCronograma
		// informado pelo usuï¿½rio
		while(iteratorCronograma.hasNext()){

			Integer codigoCobrancaAcaoAtividadeCronograma = iteratorCronograma.next();

			// Busca o CobrancaAcaoAtividadeCronograma para obter os dados
			// para montar
			// o processoIniciado
			CobrancaAcaoAtividadeCronograma cobrancaAcaoAtividadeCronograma = getControladorCobranca()
							.pesquisarCobrancaAcaoAtividadeCronogramaId(codigoCobrancaAcaoAtividadeCronograma);

			// FS0011 – Verificar restrição de execução simultânea de processos
			if(this.isProcessoComRestricaoExecucaoSimultanea(cobrancaAcaoAtividadeCronograma.getCobrancaAtividade().getProcesso().getId())){
				sessionContext.setRollbackOnly();
				throw new ControladorException("atencao.processo_restricao_execucao");
			}

			// Constrï¿½i um processoIniciado para cada
			// FaturamentoAtividadeCronograma
			ProcessoIniciado processoIniciado = new ProcessoIniciado();
			processoIniciado.setUsuario(usuario);

			processoIniciado.setCodigoGrupoProcesso(cobrancaAcaoAtividadeCronograma.getCobrancaAcaoCronograma()
							.getCobrancaGrupoCronogramaMes().getCobrancaGrupo().getId());

			processoIniciado.setProcessoSituacao(processoSituacao);

			processoIniciado.setProcesso(cobrancaAcaoAtividadeCronograma.getCobrancaAtividade().getProcesso());
			processoIniciado.setDataHoraAgendamento(dataHoraAgendamento == null ? agora : dataHoraAgendamento);
			// processoIniciado.setDataHoraInicio(agora);
			processoIniciado.setDataHoraComando(cobrancaAcaoAtividadeCronograma.getComando());

			String descricaoDadosComplementares = this.getControladorCobranca().obterDadosComplementaresComandoCronogramaCobranca(
							codigoCobrancaAcaoAtividadeCronograma);
			processoIniciado.setDescricaoDadosComplementares(descricaoDadosComplementares);

			if(idProcessoIniciadoVinculado != null){
				ProcessoIniciado processoIniciadoVinculado = new ProcessoIniciado();
				processoIniciadoVinculado.setId(idProcessoIniciadoVinculado);

				processoIniciado.setProcessoIniciadoVinculado(processoIniciadoVinculado);
			}

			codigoProcessoIniciadoGerado = (Integer) getControladorUtil().inserir(processoIniciado);
			LOGGER.info("INCLUINDO PROCESSO INICIADO[" + codigoProcessoIniciadoGerado + "] - DADOS COMPLEMENTARES ["
							+ descricaoDadosComplementares + "]");

			// Este trecho pesquisa todos do processoFuncionalidade
			// relacionados
			// com o processo do objeto a ser inserido
			FiltroProcessoFuncionalidade filtroProcessoFuncionalidade = new FiltroProcessoFuncionalidade();

			filtroProcessoFuncionalidade.adicionarParametro(new ParametroSimples(FiltroProcessoFuncionalidade.ID_PROCESSO, processoIniciado
							.getProcesso().getId()));
			filtroProcessoFuncionalidade.adicionarParametro(new ParametroSimples(FiltroProcessoFuncionalidade.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			Collection processosFuncionaliadade = getControladorUtil().pesquisar(filtroProcessoFuncionalidade,
							ProcessoFuncionalidade.class.getName());

			Date dataAtual = agora;
			// -----------------------------------------------------------------------------------------
			helper.setAcaoCobranca(cobrancaAcaoAtividadeCronograma.getCobrancaAcaoCronograma().getCobrancaAcao());
			helper.setCobrancaAcaoAtividadeComando(null);
			helper.setCobrancaAcaoAtividadeCronograma(cobrancaAcaoAtividadeCronograma);
			helper.setCriterioCobranca(null);
			helper.setDataAtual(dataAtual);
			helper.setGrupoCobranca(cobrancaAcaoAtividadeCronograma.getCobrancaAcaoCronograma().getCobrancaGrupoCronogramaMes()
							.getCobrancaGrupo());
			helper.setUsuario(usuario);
			// -----------------------------------------------------------------------------------------

			Iterator iterator = processosFuncionaliadade.iterator();
			while(iterator.hasNext()){

				ProcessoFuncionalidade processoFuncionalidade = (ProcessoFuncionalidade) iterator.next();
				FuncionalidadeIniciada funcionalidadeIniciada = new FuncionalidadeIniciada();
				funcionalidadeIniciada.setFuncionalidadeSituacao(funcionalidadeSituacao);
				Boolean emitirRelatorio = false;
				funcionalidadeIniciada.setProcessoIniciado(processoIniciado);
				funcionalidadeIniciada.setProcessoFuncionalidade(processoFuncionalidade);

				if(funcionalidadeIniciada.getProcessoFuncionalidade().getFuncionalidade().getId().equals(Funcionalidade.EMITIR_RELATORIO)){
					funcionalidadeIniciada.setId((Integer) getControladorUtil().inserir(funcionalidadeIniciada));
					emitirRelatorio = true;
				}else{
					funcionalidadeIniciada.setId((Integer) getControladorUtil().inserir(funcionalidadeIniciada));
				}

				// Seta os parametros da funcionalidadeIniciada
				// cria o formato da data
				SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

				switch(funcionalidadeIniciada.getProcessoFuncionalidade().getFuncionalidade().getId()){

					case Funcionalidade.GERAR_ATIVIDADE_ACAO_COBRANCA:
						TarefaBatchGerarAtividadeAcaoCobranca acaoCobranca = new TarefaBatchGerarAtividadeAcaoCobranca(processoIniciado
										.getUsuario(), funcionalidadeIniciada.getId());
						acaoCobranca.addParametro("usuario", processoIniciado.getUsuario());

						// --------------------------------------------------------------------------------------------------------------
						// CASO BATCH EM PARALELO POR SETOR COMERCIAL
						// --------------------------------------------------------------------------------------------------------------
						Collection colecaoSetorComercail = null;
						if(cobrancaAcaoAtividadeCronograma.getCobrancaAcaoCronograma().getCobrancaGrupoCronogramaMes().getCobrancaGrupo()
										.getId() != null){
							try{
								colecaoSetorComercail = repositorioBatch
												.pesquisarSetorComercialProcessamentoBatchGrupoInformado(cobrancaAcaoAtividadeCronograma
																.getCobrancaAcaoCronograma().getCobrancaGrupoCronogramaMes()
																.getCobrancaGrupo().getId());
							}catch(ErroRepositorioException e){
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}

						// Seta os parametros para rodar a funcionalidade
						acaoCobranca.addParametro("grupoCobranca", cobrancaAcaoAtividadeCronograma.getCobrancaAcaoCronograma()
										.getCobrancaGrupoCronogramaMes().getCobrancaGrupo());
						acaoCobranca.addParametro("anoMesReferenciaCicloCobranca", cobrancaAcaoAtividadeCronograma
										.getCobrancaAcaoCronograma().getCobrancaGrupoCronogramaMes().getAnoMesReferencia());
						acaoCobranca.addParametro("comandoAtividadeAcaoCobranca", cobrancaAcaoAtividadeCronograma);

						// --------------------------------------------------------------------------------------------------------------
						// CASO BATCH EM PARALELO POR SETOR COMERCIAL
						// --------------------------------------------------------------------------------------------------------------
						acaoCobranca.addParametro(ConstantesSistema.COLECAO_UNIDADES_PROCESSAMENTO_BATCH, colecaoSetorComercail);
						// --------------------------------------------------------------------------------------------------------------

						acaoCobranca.addParametro("acaoCobranca", cobrancaAcaoAtividadeCronograma.getCobrancaAcaoCronograma()
										.getCobrancaAcao());
						acaoCobranca.addParametro("atividadeCobranca", cobrancaAcaoAtividadeCronograma.getCobrancaAtividade());

						// -----------------------------------------------------------------------------------------------------
						acaoCobranca.addParametro("indicadorCriterio", CobrancaAtividade.INDICADOR_CRITERIO_POR_CRONOGRAMA.shortValue());
						// -----------------------------------------------------------------------------------------------------

						acaoCobranca.addParametro("anoMesReferenciaInicial", 000101);
						acaoCobranca.addParametro("anoMesReferenciaFinal", (new Integer(Util.subtrairMesDoAnoMes(getControladorUtil()
										.pesquisarParametrosDoSistema().getAnoMesFaturamento(), 1))));
						acaoCobranca.addParametro("dataVencimentoInicial", formato.parse("01/01/0001"));
						acaoCobranca.addParametro("dataAtual", dataAtual);

						acaoCobranca.addParametro("dataVencimentoFinal", Util.subtrairNumeroDiasDeUmaData(new Date(), getControladorUtil()
										.pesquisarParametrosDoSistema().getNumeroDiasVencimentoCobranca()));

						acaoCobranca.addParametro("idFaturamentoGrupoCronogramaMensal", idFaturamentoGrupoCronogramaMensal);

						SistemaParametro sistemaParametros = getControladorUtil().pesquisarParametrosDoSistema();
						acaoCobranca.addParametro("sistemaParametros", sistemaParametros);

						// Seta o objeto para ser serializado no banco, onde
						// depois serï¿½ executado por uma thread
						funcionalidadeIniciada.setTarefaBatch(IoUtil.transformarObjetoParaBytes(acaoCobranca));

						getControladorUtil().atualizar(funcionalidadeIniciada);

						break;

					case Funcionalidade.EMITIR_RELATORIO:
						if(emitirRelatorio){

							TarefaBatchEmitirDocumentoCobranca tarefaBatchEmitirDocumentoCobranca = new TarefaBatchEmitirDocumentoCobranca(
											processoIniciado.getUsuario(), funcionalidadeIniciada.getId());

							// Seta os parametros para rodar a funcionalidade
							tarefaBatchEmitirDocumentoCobranca.addParametro("parametrosHelper", helper);

							// Seta o objeto para ser serializado no banco, onde
							// depois serï¿½ executado por uma thread
							funcionalidadeIniciada.setTarefaBatch(IoUtil.transformarObjetoParaBytes(tarefaBatchEmitirDocumentoCobranca));
							//
							getControladorUtil().atualizar(funcionalidadeIniciada);
						}
						break;

				}
			}
		}
		return codigoProcessoIniciadoGerado;
	}

	private Integer inserirProcessoCobrancaAtividadeEventual(Usuario usuario, Integer codigoProcessoIniciadoGerado,
					Iterator<Integer> iteratorAtividade, Date dataHoraAgendamento, Integer idProcessoIniciadoVinculado,
					Integer idFaturamentoGrupoCronogramaMensal) throws ControladorException, ParseException, IOException{

		final ParametrosHelper helper = new ParametrosHelper();

		ProcessoSituacao processoSituacao = new ProcessoSituacao();
		processoSituacao.setId(ProcessoSituacao.EM_ESPERA);

		FuncionalidadeSituacao funcionalidadeSituacao = new FuncionalidadeSituacao();
		funcionalidadeSituacao.setId(FuncionalidadeSituacao.EM_ESPERA);

		// Este trecho insere um processoIniciado e as funcionalidades Iniciadas para cada
		// CobrancaAcaoAtividadeComando informado pelo usuï¿½rio
		while(iteratorAtividade.hasNext()){

			Integer codigoCobrancaAcaoAtividadeComando = iteratorAtividade.next();

			// Busca o CobrancaAcaoAtividadeComando para obter os dados para montar o
			// processoIniciado
			FiltroCobrancaAcaoAtividadeComando filtroCobrancaAcaoAtividadeComando = new FiltroCobrancaAcaoAtividadeComando();
			filtroCobrancaAcaoAtividadeComando.adicionarParametro(new ParametroSimples(FiltroCobrancaAcaoAtividadeComando.ID,
							codigoCobrancaAcaoAtividadeComando));

			filtroCobrancaAcaoAtividadeComando.adicionarCaminhoParaCarregamentoEntidade("cobrancaAtividade.processo");
			filtroCobrancaAcaoAtividadeComando.adicionarCaminhoParaCarregamentoEntidade("cobrancaGrupo");
			filtroCobrancaAcaoAtividadeComando.adicionarCaminhoParaCarregamentoEntidade("cobrancaAcao");
			filtroCobrancaAcaoAtividadeComando.adicionarCaminhoParaCarregamentoEntidade("cobrancaAcao.cobrancaCriterio");
			filtroCobrancaAcaoAtividadeComando.adicionarCaminhoParaCarregamentoEntidade("cobrancaAcao.cobrancaAcaoPredecessora");
			filtroCobrancaAcaoAtividadeComando.adicionarCaminhoParaCarregamentoEntidade("cobrancaAcao.documentoTipo");
			filtroCobrancaAcaoAtividadeComando.adicionarCaminhoParaCarregamentoEntidade("cobrancaCriterio");
			filtroCobrancaAcaoAtividadeComando.adicionarCaminhoParaCarregamentoEntidade("cliente");
			filtroCobrancaAcaoAtividadeComando.adicionarCaminhoParaCarregamentoEntidade("superior");
			filtroCobrancaAcaoAtividadeComando.adicionarCaminhoParaCarregamentoEntidade("clienteRelacaoTipo");
			filtroCobrancaAcaoAtividadeComando.adicionarCaminhoParaCarregamentoEntidade("localidadeInicial");
			filtroCobrancaAcaoAtividadeComando.adicionarCaminhoParaCarregamentoEntidade("rotaInicial");
			filtroCobrancaAcaoAtividadeComando.adicionarCaminhoParaCarregamentoEntidade("rotaFinal");

			CobrancaAcaoAtividadeComando cobrancaAcaoAtividadeComando = (CobrancaAcaoAtividadeComando) Util
							.retonarObjetoDeColecao(getControladorUtil().pesquisar(filtroCobrancaAcaoAtividadeComando,
											CobrancaAcaoAtividadeComando.class.getName()));

			Date dataAtual = new Date();
			// -----------------------------------------------------------------------------------------
			helper.setAcaoCobranca(cobrancaAcaoAtividadeComando.getCobrancaAcao());
			helper.setCobrancaAcaoAtividadeComando(cobrancaAcaoAtividadeComando);
			helper.setCobrancaAcaoAtividadeCronograma(null);
			helper.setCriterioCobranca(cobrancaAcaoAtividadeComando.getCobrancaCriterio());
			helper.setDataAtual(dataAtual);
			helper.setGrupoCobranca(cobrancaAcaoAtividadeComando.getCobrancaGrupo());
			helper.setUsuario(usuario);
			// -----------------------------------------------------------------------------------------

			// FS0011 – Verificar restrição de execução simultânea de processos
			if(this.isProcessoComRestricaoExecucaoSimultanea(cobrancaAcaoAtividadeComando.getCobrancaAtividade().getProcesso().getId())){
				sessionContext.setRollbackOnly();
				throw new ControladorException("atencao.processo_restricao_execucao");
			}

			// Constroi um processoIniciado para cada FaturamentoAtividadeCronograma
			ProcessoIniciado processoIniciado = new ProcessoIniciado();
			processoIniciado.setUsuario(usuario);

			if(cobrancaAcaoAtividadeComando.getCobrancaGrupo() != null && !cobrancaAcaoAtividadeComando.getCobrancaGrupo().equals("")){
				processoIniciado.setCodigoGrupoProcesso(cobrancaAcaoAtividadeComando.getCobrancaGrupo().getId());
			}

			processoIniciado.setProcessoSituacao(processoSituacao);
			processoIniciado.setProcesso(cobrancaAcaoAtividadeComando.getCobrancaAtividade().getProcesso());
			processoIniciado.setDataHoraAgendamento(dataHoraAgendamento == null ? dataAtual : dataHoraAgendamento);
			// processoIniciado.setDataHoraInicio(dataAtual);
			processoIniciado.setDataHoraComando(cobrancaAcaoAtividadeComando.getComando());

			String descricaoDadosComplementares = this.getControladorCobranca().obterDadosComplementaresComandoEventualCobranca(
							codigoCobrancaAcaoAtividadeComando);
			processoIniciado.setDescricaoDadosComplementares(descricaoDadosComplementares);

			if(idProcessoIniciadoVinculado != null){
				ProcessoIniciado processoIniciadoVinculado = new ProcessoIniciado();
				processoIniciadoVinculado.setId(idProcessoIniciadoVinculado);

				processoIniciado.setProcessoIniciadoVinculado(processoIniciadoVinculado);
			}

			codigoProcessoIniciadoGerado = (Integer) getControladorUtil().inserir(processoIniciado);
			LOGGER.info("INCLUINDO PROCESSO INICIADO[" + codigoProcessoIniciadoGerado + "] - DADOS COMPLEMENTARES ["
							+ descricaoDadosComplementares + "]");
			
			// Este trecho pesquisa todos do processoFuncionalidade relacionados com o processo do
			// objeto a ser inserido
			FiltroProcessoFuncionalidade filtroProcessoFuncionalidade = new FiltroProcessoFuncionalidade();

			filtroProcessoFuncionalidade.adicionarParametro(new ParametroSimples(FiltroProcessoFuncionalidade.ID_PROCESSO, processoIniciado
							.getProcesso().getId()));

			filtroProcessoFuncionalidade.adicionarParametro(new ParametroSimples(FiltroProcessoFuncionalidade.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			filtroProcessoFuncionalidade.setCampoOrderBy(FiltroProcessoFuncionalidade.SEQUENCIAL_EXECUCAO);

			Collection processosFuncionaliadade = getControladorUtil().pesquisar(filtroProcessoFuncionalidade,
							ProcessoFuncionalidade.class.getName());

			Iterator iterator = processosFuncionaliadade.iterator();
			while(iterator.hasNext()){
				ProcessoFuncionalidade processoFuncionalidade = (ProcessoFuncionalidade) iterator.next();
				FuncionalidadeIniciada funcionalidadeIniciada = new FuncionalidadeIniciada();

				funcionalidadeIniciada.setFuncionalidadeSituacao(funcionalidadeSituacao);
				funcionalidadeIniciada.setProcessoIniciado(processoIniciado);
				Boolean emitirRelatorio = false;
				funcionalidadeIniciada.setProcessoFuncionalidade(processoFuncionalidade);
				if(funcionalidadeIniciada.getProcessoFuncionalidade().getFuncionalidade().getId().equals(Funcionalidade.EMITIR_RELATORIO)){
					funcionalidadeIniciada.setId((Integer) getControladorUtil().inserir(funcionalidadeIniciada));
					emitirRelatorio = true;

				}else{
					funcionalidadeIniciada.setId((Integer) getControladorUtil().inserir(funcionalidadeIniciada));
				}

				// Seta os parametros da funcionalidade Iniciada
				switch(funcionalidadeIniciada.getProcessoFuncionalidade().getFuncionalidade().getId()){
					case Funcionalidade.GERAR_ATIVIDADE_ACAO_COBRANCA:
						TarefaBatchGerarAtividadeAcaoCobranca acaoCobranca = new TarefaBatchGerarAtividadeAcaoCobranca(processoIniciado
										.getUsuario(), funcionalidadeIniciada.getId());
						acaoCobranca.addParametro("usuario", processoIniciado.getUsuario());

						// --------------------------------------------------------------------------------------------------------------
						// CASO BATCH EM PARALELO POR SETOR COMERCIAL
						// --------------------------------------------------------------------------------------------------------------
						Collection colecaoSetorComercial = null;
						try{
							if(cobrancaAcaoAtividadeComando.getCobrancaGrupo() != null){
								acaoCobranca.addParametro("grupoCobranca", cobrancaAcaoAtividadeComando.getCobrancaGrupo());
								colecaoSetorComercial = repositorioBatch
												.pesquisarSetorComercialProcessamentoBatchGrupoInformado(cobrancaAcaoAtividadeComando
																.getCobrancaGrupo().getId());
							}else{
								colecaoSetorComercial = repositorioBatch
												.pesquisarSetorComercialProcessamentoBatchCobrancaGrupoNaoInformado(cobrancaAcaoAtividadeComando
																.getId());
							}

							if(colecaoSetorComercial == null || colecaoSetorComercial.isEmpty()){

								colecaoSetorComercial = getControladorLocalidade().obterTodosIDsSetorComercial();

							}

						}catch(ErroRepositorioException e){
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						// Seta os parametros para rodar a funcionalidade
						acaoCobranca.addParametro("anoMesReferenciaCicloCobranca", getControladorUtil().pesquisarParametrosDoSistema()
										.getAnoMesFaturamento());
						acaoCobranca.addParametro("comandoAtividadeAcaoComando", cobrancaAcaoAtividadeComando);

						// --------------------------------------------------------------------------------------------------------------
						// CASO BATCH EM PARALELO POR SETOR COMERCIAL
						// --------------------------------------------------------------------------------------------------------------
						acaoCobranca.addParametro(ConstantesSistema.COLECAO_UNIDADES_PROCESSAMENTO_BATCH, colecaoSetorComercial);
						// --------------------------------------------------------------------------------------------------------------
						acaoCobranca.addParametro("acaoCobranca", cobrancaAcaoAtividadeComando.getCobrancaAcao());
						acaoCobranca.addParametro("atividadeCobranca", cobrancaAcaoAtividadeComando.getCobrancaAtividade());
						// indicador de critério por comando = 2
						acaoCobranca.addParametro("indicadorCriterio", cobrancaAcaoAtividadeComando.getIndicadorCriterio());
						acaoCobranca.addParametro("criterioCobranca", cobrancaAcaoAtividadeComando.getCobrancaCriterio());
						acaoCobranca.addParametro("cliente", cobrancaAcaoAtividadeComando.getCliente());
						acaoCobranca.addParametro("clienteSuperior", cobrancaAcaoAtividadeComando.getSuperior());
						acaoCobranca.addParametro("clienteRelacaoTipo", cobrancaAcaoAtividadeComando.getClienteRelacaoTipo());
						acaoCobranca
										.addParametro("anoMesReferenciaInicial", cobrancaAcaoAtividadeComando
														.getAnoMesReferenciaContaInicial());
						acaoCobranca.addParametro("anoMesReferenciaFinal", cobrancaAcaoAtividadeComando.getAnoMesReferenciaContaFinal());
						acaoCobranca.addParametro("dataVencimentoInicial", cobrancaAcaoAtividadeComando.getDataVencimentoContaInicial());
						acaoCobranca.addParametro("dataVencimentoFinal", cobrancaAcaoAtividadeComando.getDataVencimentoContaFinal());
						acaoCobranca.addParametro("dataAtual", dataAtual);
						acaoCobranca.addParametro("idFaturamentoGrupoCronogramaMensal", idFaturamentoGrupoCronogramaMensal);

						SistemaParametro sistemaParametros = getControladorUtil().pesquisarParametrosDoSistema();
						acaoCobranca.addParametro("sistemaParametros", sistemaParametros);

						// Seta o objeto para ser serializado no banco, onde
						// depois serï¿½ executado por uma thread
						preencherTarefaBatch(funcionalidadeIniciada.getId(), acaoCobranca);

						break;

					case Funcionalidade.EMITIR_RELATORIO:
						if(emitirRelatorio){

							TarefaBatchEmitirDocumentoCobranca tarefaBatchEmitirDocumentoCobranca = new TarefaBatchEmitirDocumentoCobranca(
											processoIniciado.getUsuario(), funcionalidadeIniciada.getId());

							// Seta os parametros para rodar a funcionalidade
							tarefaBatchEmitirDocumentoCobranca.addParametro("parametrosHelper", helper);

							// Seta o objeto para ser serializado no banco, onde
							// depois serï¿½ executado por uma thread
							preencherTarefaBatch(funcionalidadeIniciada.getId(), tarefaBatchEmitirDocumentoCobranca);
						}
						break;

					case Funcionalidade.EMITIR_RELACAO_DOCUMENTOS_COBRANCA:
						if(cobrancaAcaoAtividadeComando.getIndicadorGerarRelacaoDocumento().equals(ConstantesSistema.SIM)
										&& (cobrancaAcaoAtividadeComando.getCobrancaAtividade() != null && cobrancaAcaoAtividadeComando
														.getCobrancaAtividade().getId().equals(CobrancaAtividade.EMITIR))){

							LOGGER.info("Indicador de geração de relatório: "
											+ cobrancaAcaoAtividadeComando.getIndicadorGerarRelacaoDocumento());

							TarefaBatchEmitirRelacaoDocumentosCobranca tarefaBatchEmitirRelacaoDocumentosCobranca = new TarefaBatchEmitirRelacaoDocumentosCobranca(
											processoIniciado.getUsuario(), funcionalidadeIniciada.getId());

							// Seta os parametros para rodar a funcionalidade
							tarefaBatchEmitirRelacaoDocumentosCobranca.addParametro("comandoAtividadeAcaoComando",
											cobrancaAcaoAtividadeComando);

							tarefaBatchEmitirRelacaoDocumentosCobranca.addParametro("usuario", processoIniciado.getUsuario());

							// Seta o objeto para ser serializado no banco, onde
							// depois será executado por uma thread
							preencherTarefaBatch(funcionalidadeIniciada.getId(), tarefaBatchEmitirRelacaoDocumentosCobranca);
						}else{
							funcionalidadeIniciada.getFuncionalidadeSituacao().setId(FuncionalidadeSituacao.CONCLUIDA);
							getControladorUtil().atualizar(funcionalidadeIniciada);

						}
						break;
				}
			}
		}
		return codigoProcessoIniciadoGerado;
	}

	/**
	 * Retorna a interface remota de ControladorSpcSerasa
	 * 
	 * @return A interface remota do controlador de parâmetro
	 */
	private ControladorSpcSerasaLocal getControladorSpcSerasa(){

		ControladorSpcSerasaLocalHome localHome = null;
		ControladorSpcSerasaLocal local = null;

		// pega a instância do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorSpcSerasaLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_SPC_SERASA_SEJB);
			// guarda a referencia de um objeto capaz de fazer chamadas
			// objetos remotamente
			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}
	}

	private ControladorUtilLocal getControladorUtil(){

		ControladorUtilLocalHome localHome = null;
		ControladorUtilLocal local = null;

		// pega a instï¿½ncia do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorUtilLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_UTIL_SEJB);
			// guarda a referencia de um objeto capaz de fazer chamadas ï¿½
			// objetos remotamente
			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}

	}

	/**
	 * Verifica no sistema a presenca de ProcessosIniciados nao agendados para
	 * iniciar a execucao
	 * 
	 * @author Rodrigo Silveira
	 * @param processosId
	 * @date 22/08/2006
	 */
	public void verificarProcessosIniciados(Collection<Integer> processosId) throws ControladorException{

		try{
			Date agora = new Date();

			// Procurar as funcionalidadesIniciadas de processosIniciados em
			// Situacao de Inicio - Agendado ou em espera
			// ---------------------------------------------------------------
			// Sï¿½ vai procurar as funcionalidades Iniciadas para iniciar
			// execuï¿½ï¿½o em que o processo Iniciado nï¿½o tem precedente ou que o
			// precedente tenha sido finalizado

			Collection<FuncionalidadeIniciada> colecaoFuncionalidadesIniciadasParaExecucao = verificarFuncionalidadesIniciadasProntasParaExecucao(processosId);

			Iterator<FuncionalidadeIniciada> iterator = colecaoFuncionalidadesIniciadasParaExecucao.iterator();

			FuncionalidadeSituacao funcionalidadeSituacao = new FuncionalidadeSituacao();
			funcionalidadeSituacao.setId(FuncionalidadeSituacao.EM_PROCESSAMENTO);

			ProcessoSituacao processoSituacao = new ProcessoSituacao();
			processoSituacao.setId(ProcessoSituacao.EM_PROCESSAMENTO);

			while(iterator.hasNext()){
				// Recuperar a tarefa batch armazenada
				FuncionalidadeIniciada funcionalidadeIniciada = iterator.next();

				// TarefaBatch tarefaBatch = (TarefaBatch)
				// IoUtil.transformarBytesParaObjeto(funcionalidadeIniciada.getTarefaBatch());
				Tarefa tarefa = (Tarefa) IoUtil.transformarBytesParaObjeto(funcionalidadeIniciada.getTarefaBatch());

				// Atualizar a FuncionalidadeSituacao para EM_PROCESSAMENTO
				funcionalidadeIniciada.setFuncionalidadeSituacao(funcionalidadeSituacao);
				funcionalidadeIniciada.setDataHoraInicio(agora);

				// Atualiza o Processo Iniciado para EM_PROCESSAMENTO
				ProcessoIniciado processoIniciado = funcionalidadeIniciada.getProcessoIniciado();

				if(tarefa == null){
					LOGGER.info("Atenção: tarefa igual a NULL. (verificarProcessosIniciados())");
					LOGGER.info("funcionalidadeIniciada: " + funcionalidadeIniciada.getId());
					if(processoIniciado != null){
						LOGGER.info("processoIniciado: " + processoIniciado.getId());
					}
					continue;
				}

				if(!processoIniciado.getProcessoSituacao().getId().equals(ProcessoSituacao.EM_PROCESSAMENTO)){

					if(processoIniciado.getDataHoraInicio() == null){
						processoIniciado.setDataHoraInicio(agora);
					}
					processoIniciado.setProcessoSituacao(processoSituacao);
					getControladorUtil().atualizar(processoIniciado);
				}

				getControladorUtil().atualizar(funcionalidadeIniciada);

				// Agendar a tarefa Batch
				if(tarefa != null){
					tarefa.agendarTarefaBatch();
				}

			}

		}catch(IOException e){
			e.printStackTrace();

			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);

		}catch(ClassNotFoundException e){
			e.printStackTrace();

			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);

		}

	}


	/**
	 * Encerra os Processos Iniciados no sistema quando todas as funcionalidades do mesmo
	 * finalizarem a execuï¿½ï¿½o
	 * 
	 * @author Rodrigo Silveira
	 * @date 22/08/2006
	 */
	public void encerrarProcessosIniciados() throws ControladorException{

		try{
			Date agora = new Date();

			// Busca todos os Processos Iniciados que podem ser concluï¿½dos -
			// eles possuem apenas FuncionalidadesIniciadas no estado CONCLUIDO
			Collection<ProcessoIniciado> colecaoProcessosIniciadosParaEncerramento = repositorioBatch
							.pesquisarProcessosIniciadosProntosParaEncerramento();
			Iterator<ProcessoIniciado> iterator = colecaoProcessosIniciadosParaEncerramento.iterator();

			ProcessoSituacao processoSituacaoConcluido = new ProcessoSituacao();
			processoSituacaoConcluido.setId(ProcessoSituacao.CONCLUIDO);

			while(iterator.hasNext()){
				ProcessoIniciado processoIniciado = iterator.next();
				processoIniciado.setDataHoraTermino(agora);
				processoIniciado.setProcessoSituacao(processoSituacaoConcluido);
				getControladorUtil().atualizar(processoIniciado);
			}

			// Busca todos os Processos Iniciados que podem ser concluï¿½dos
			// indicando erro -
			// eles possuem apenas FuncionalidadesIniciadas no estado CONCLUIDO
			// e pelo menos uma no estado CONCLUIDO_COM_ERRO
			Collection<ProcessoIniciado> colecaoProcessosIniciadosFalha = repositorioBatch.pesquisarProcessosIniciadosExecucaoFalha();
			Iterator<ProcessoIniciado> iteratorFalha = colecaoProcessosIniciadosFalha.iterator();

			ProcessoSituacao processoSituacaoConcluidoComErro = new ProcessoSituacao();
			processoSituacaoConcluidoComErro.setId(ProcessoSituacao.CONCLUIDO_COM_ERRO);

			while(iteratorFalha.hasNext()){
				ProcessoIniciado processoIniciado = iteratorFalha.next();
				processoIniciado.setDataHoraTermino(agora);
				processoIniciado.setProcessoSituacao(processoSituacaoConcluidoComErro);
				getControladorUtil().atualizar(processoIniciado);
			}

		}catch(ErroRepositorioException e){
			e.printStackTrace();
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}
	}

	/**
	 * Encerra as Funcionalidades Iniciadas no sistema quando todas as unidades
	 * de processamento do mesmo finalizarem a execuï¿½ï¿½o
	 * 
	 * @author Rodrigo Silveira
	 * @date 22/08/2006
	 */
	public void encerrarFuncionalidadesIniciadas() throws ControladorException{

		try{
			Date agora = new Date();

			// Pesquisa no sistema para ver se existe alguma unidadeIniciada que
			// foi concluida com falha para
			// marcar a funcionalidadeIniciada como 'concluida com erro'
			Collection<FuncionalidadeIniciada> colecaoFuncionaldadesIniciadasFalha = repositorioBatch
							.pesquisarFuncionaldadesIniciadasExecucaoFalha();

			if(colecaoFuncionaldadesIniciadasFalha != null && !colecaoFuncionaldadesIniciadasFalha.isEmpty()){

				Iterator<FuncionalidadeIniciada> iterator = colecaoFuncionaldadesIniciadasFalha.iterator();

				FuncionalidadeSituacao funcionalidadeSituacaoConcluidaComErro = new FuncionalidadeSituacao();
				funcionalidadeSituacaoConcluidaComErro.setId(FuncionalidadeSituacao.CONCLUIDA_COM_ERRO);

				while(iterator.hasNext()){
					FuncionalidadeIniciada funcionalidadeIniciada = iterator.next();
					funcionalidadeIniciada.setDataHoraTermino(agora);
					funcionalidadeIniciada.setFuncionalidadeSituacao(funcionalidadeSituacaoConcluidaComErro);
					getControladorUtil().atualizar(funcionalidadeIniciada);

				}

			}else{

				Collection<FuncionalidadeIniciada> colecaoFuncionaldadesIniciadasParaEncerramento = repositorioBatch
								.pesquisarFuncionaldadesIniciadasProntasParaEncerramento();

				Iterator<FuncionalidadeIniciada> iterator = colecaoFuncionaldadesIniciadasParaEncerramento.iterator();

				FuncionalidadeSituacao funcionalidadeSituacaoConcluida = new FuncionalidadeSituacao();
				funcionalidadeSituacaoConcluida.setId(FuncionalidadeSituacao.CONCLUIDA);

				while(iterator.hasNext()){
					FuncionalidadeIniciada funcionalidadeIniciada = iterator.next();
					funcionalidadeIniciada.setDataHoraTermino(agora);
					funcionalidadeIniciada.setFuncionalidadeSituacao(funcionalidadeSituacaoConcluida);
					// Finaliza com alguma lï¿½gica de negï¿½cio depois que todas as
					// UnidadesIniciadas terminarem de executar
					funcionalidadeIniciada.finalizador();
					getControladorUtil().atualizar(funcionalidadeIniciada);

				}
			}
		}catch(ErroRepositorioException e){
			e.printStackTrace();
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}
	}

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
					throws ControladorException{

		int retorno = 0;

		try{

			int funcionalidadesIniciadasComErro = repositorioBatch.pesquisarFuncionaldadesIniciadasConcluidasErro(idFuncionalidadeIniciada);

			if(funcionalidadesIniciadasComErro > 0){
				throw new ControladorException("Esta Unidade não será executada porque o processo está concluído com erro");
			}else{

				UnidadeIniciada unidadeIniciada = new UnidadeIniciada();

				FuncionalidadeIniciada funcionalidadeIniciada = new FuncionalidadeIniciada();
				funcionalidadeIniciada.setId(idFuncionalidadeIniciada);

				unidadeIniciada.setFuncionalidadeIniciada(funcionalidadeIniciada);

				UnidadeProcessamento unidadeProcessamento = new UnidadeProcessamento();
				unidadeProcessamento.setId(idUnidadeProcessamento);

				unidadeIniciada.setUnidadeProcessamento(unidadeProcessamento);
				unidadeIniciada.setCodigoRealUnidadeProcessamento(codigoRealUnidadeProcessamento);

				UnidadeSituacao unidadeSituacao = new UnidadeSituacao();
				unidadeSituacao.setId(UnidadeSituacao.EM_PROCESSAMENTO);
				unidadeIniciada.setUnidadeSituacao(unidadeSituacao);

				unidadeIniciada.setDataHoraInicio(new Date());

				retorno = (Integer) getControladorUtil().inserir(unidadeIniciada);

				FiltroFuncionalidadeIniciada filtroFuncionalidadeIniciada = new FiltroFuncionalidadeIniciada();
				filtroFuncionalidadeIniciada.adicionarCaminhoParaCarregamentoEntidade("processoFuncionalidade.funcionalidade");
				filtroFuncionalidadeIniciada.adicionarParametro(new ParametroSimples(FiltroFuncionalidadeIniciada.ID,
								idFuncionalidadeIniciada));

				funcionalidadeIniciada = (FuncionalidadeIniciada) Util.retonarObjetoDeColecao(getControladorUtil().pesquisar(
								filtroFuncionalidadeIniciada, FuncionalidadeIniciada.class.getName()));

				FuncionalidadeCorrente.set(null);
				if(funcionalidadeIniciada != null && funcionalidadeIniciada.getProcessoFuncionalidade() != null){
					FuncionalidadeCorrente.set(funcionalidadeIniciada.getProcessoFuncionalidade().getFuncionalidade());
				}

			}

		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

		return retorno;

	}

	/**
	 * Encerra a Unidade de Processamento associada a um processamento batch
	 * 
	 * @author Rodrigo Silveira
	 * @date 24/08/2006
	 * @param idUnidadeIniciada
	 * @param executouComErro
	 */
	public void encerrarUnidadeProcessamentoBatch(int idUnidadeIniciada, boolean executouComErro) throws ControladorException{

		if(idUnidadeIniciada != 0){

			FiltroUnidadeIniciada filtroUnidadeIniciada = new FiltroUnidadeIniciada();
			filtroUnidadeIniciada.adicionarParametro(new ParametroSimples(FiltroFuncionalidadeIniciada.ID, idUnidadeIniciada));

			UnidadeIniciada unidadeIniciada = (UnidadeIniciada) Util.retonarObjetoDeColecao(getControladorUtil().pesquisar(
							filtroUnidadeIniciada, UnidadeIniciada.class.getName()));

			if(unidadeIniciada != null){
				unidadeIniciada.setDataHoraTermino(new Date());

				if(!executouComErro){
					UnidadeSituacao unidadeSituacao = new UnidadeSituacao();
					unidadeSituacao.setId(UnidadeSituacao.CONCLUIDA);
					unidadeIniciada.setUnidadeSituacao(unidadeSituacao);
				}else{
					UnidadeSituacao unidadeSituacao = new UnidadeSituacao();
					unidadeSituacao.setId(UnidadeSituacao.CONCLUIDA_COM_ERRO);
					unidadeIniciada.setUnidadeSituacao(unidadeSituacao);
				}

				getControladorUtil().atualizar(unidadeIniciada);
			}
		}else{

			LOGGER.info("ATENCAO --- UNIDADE INICIADA POSSUI ID : !" + idUnidadeIniciada + "!");

		}

	}

	/**
	 * Insere uma coleï¿½ï¿½o de objetos genï¿½ricos na base com um flush para cada 50
	 * registros inseridos.
	 * 
	 * @author Pedro Alexandre
	 * @date 11/09/2006
	 * @param colecaoObjetos
	 * @throws ControladorException
	 */
	public void inserirColecaoObjetoParaBatch(Collection<Object> colecaoObjetos) throws ControladorException{

		try{
			if(colecaoObjetos != null && !colecaoObjetos.isEmpty()){
				repositorioBatch.inserirColecaoObjetoParaBatch(colecaoObjetos);
			}
		}catch(ErroRepositorioException e){
			e.printStackTrace();
			throw new ControladorException("erro.sistema", e);
		}
	}

	/**
	 * Insere uma coleï¿½ï¿½o de objetos genï¿½ricos na base com um flush para cada 50
	 * registros inseridos.
	 * 
	 * @author Pedro Alexandre
	 * @date 11/09/2006
	 * @param colecaoObjetos
	 * @throws ControladorException
	 */
	public void inserirColecaoObjetoParaBatchGerencial(Collection<Object> colecaoObjetos) throws ControladorException{

		try{
			repositorioBatch.inserirColecaoObjetoParaBatchGerencial(colecaoObjetos);
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}
	}

	/**
	 * Atualiza uma coleï¿½ï¿½o de objetos genï¿½ricos na base com um flush para cada
	 * 50 registros inseridos.
	 * 
	 * @author Pedro Alexandre
	 * @date 11/09/2006
	 * @param colecaoObjetos
	 * @throws ControladorException
	 */
	public void atualizarColecaoObjetoParaBatch(Collection<Object> colecaoObjetos) throws ControladorException{

		try{
			repositorioBatch.atualizarColecaoObjetoParaBatch(colecaoObjetos);
		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}
	}

	/**
	 * Funï¿½ï¿½o que executa as rotinas de execuï¿½ï¿½o e fechamento das tarefas batch
	 * do sistema
	 * 
	 * @author Rodrigo Silveira
	 * @date 11/09/2006
	 */
	public void verificadorProcessosSistema() throws ControladorException{

		Collection<Integer> processosId = consultarProcessosProntosParaExecucao();
		if(!Util.isVazioOrNulo(processosId)){
			this.verificarProcessosIniciados(processosId);
		}
		this.iniciarRelatoriosAgendados();
		this.encerrarFuncionalidadesIniciadas();
		this.encerrarProcessosIniciados();
	}


	/**
	 * Método consultarProcessosProntosParaExecucao
	 * <p>
	 * Esse método implementa a consulta de {@link ProcessoIniciado} que estao em
	 * {@link ProcessoSituacao} EM_ESPERA ou EM_PROCESSAMENTO.
	 * </p>
	 * RASTREIO: [OC1006224]
	 * 
	 * @return {@link Collection} de ID de {@link ProcessoIniciado}.
	 * @author Marlos Ribeiro
	 * @throws ControladorException
	 * @since 08/03/2013
	 */
	private Collection<Integer> consultarProcessosProntosParaExecucao() throws ControladorException{

		FiltroProcessoIniciado filtro = new FiltroProcessoIniciado(FiltroProcessoIniciado.DATA_HORA_AGENDAMENTO);
		filtro.adicionarParametro(new ParametroSimplesColecao(FiltroProcessoIniciado.PROCESSO_SITUACAO_ID, //
						Arrays.asList(ProcessoSituacao.EM_ESPERA, ProcessoSituacao.EM_PROCESSAMENTO)));
		String ip = VerificadorIP.getInstancia().getIpAtivo();
		if(ip != null) filtro.adicionarParametro(new ParametroSimples(FiltroProcessoIniciado.IP, ip));

		 Collection processosAguardando = getControladorUtil().pesquisar(filtro, ProcessoIniciado.class.getName());
		 List<Integer> processosContinuamEmEspera = new ArrayList<Integer>();
		 Collection<Integer> processosProntosParaExecutar = new ArrayList<Integer>();
		Collection processosProntosParaExecutarLista = new ArrayList();
		try{
			for(Object object : processosAguardando){
				ProcessoIniciado processoEmEspera = (ProcessoIniciado) object;
				// SE EM_PROCESSAMENTO OU EM_ESPERA e NÃO TEM PENDENCIAS
				if(isProcessoProntoParaExecutar(processoEmEspera)){

					// ..........................................................................................................
					boolean existe = false;
					Iterator it = processosProntosParaExecutarLista.iterator();
					while(it.hasNext()){
						ProcessoIniciado pi = (ProcessoIniciado) it.next();
						
						if(pi.getProcesso().getId().equals(processoEmEspera.getProcesso().getId())
										&& ((pi.getDescricaoDadosComplementares() != null
														&& processoEmEspera.getDescricaoDadosComplementares() != null
										&& pi.getDescricaoDadosComplementares().compareTo(
																		processoEmEspera.getDescricaoDadosComplementares()) == 0) || (pi
														.getDescricaoDadosComplementares() == null && processoEmEspera
														.getDescricaoDadosComplementares() == null))){
							existe = true;
							break;
						}
					}
					if(!existe){
						processosProntosParaExecutarLista.add(processoEmEspera);
						processosProntosParaExecutar.add(processoEmEspera.getId());
					}


				}else{

					// ..........................................................................................................
					processosContinuamEmEspera.add(processoEmEspera.getId());
				}
			}
			LOGGER.debug(processosContinuamEmEspera.size() + " PROCESSOS EM ESPERA devido a dependencia com outros processos. "
							+ Arrays.toString(processosContinuamEmEspera.toArray()));
			if(processosProntosParaExecutar.size() > 0) LOGGER.info(processosProntosParaExecutar.size()
							+ " PROCESSOS EM ESPERA OU EM PROCESSAMENTO que podem ter steps para serem executados. "
							+ Arrays.toString(processosProntosParaExecutar.toArray()));
		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}
		return processosProntosParaExecutar;
	}

	private boolean isProcessoProntoParaExecutar(ProcessoIniciado processoEmEspera) throws ErroRepositorioException{

		boolean estaPronto = Arrays.asList(ProcessoSituacao.EM_ESPERA, ProcessoSituacao.EM_PROCESSAMENTO)//
						.contains(processoEmEspera.getProcessoSituacao().getId())//
						&& repositorioBatch.consultarQtdProcessoAntecedenteEmSituacao(processoEmEspera, SITUACOES_PROCESSO_PENDENTE) == 0;

		LOGGER.debug("PROCESSO[" + processoEmEspera.getId() + "] NÃO POSSUI pendencias no momento e esta pronto para rodar? " + estaPronto);

		int qtdPendenciasEsperadasConcluidas = repositorioBatch.consultarProcessoDepencias(processoEmEspera).size();

		if(estaPronto && qtdPendenciasEsperadasConcluidas > 0){

			int qtdPrecedentesConcluidos = repositorioBatch.consultarQtdProcessoAntecedenteEmSituacao(processoEmEspera,
							Arrays.asList(ProcessoSituacao.CONCLUIDO));

			// ..........................................................................................................

			FiltroFuncionalidadeIniciada filtro = new FiltroFuncionalidadeIniciada();
			filtro.adicionarParametro(new ParametroSimples(FiltroFuncionalidadeIniciada.PROCESSO_PROCESSO_ID, //
							processoEmEspera.getProcesso().getId()));

			String ip = VerificadorIP.getInstancia().getIpAtivo();
			if(ip != null) filtro.adicionarParametro(new ParametroSimples(FiltroFuncionalidadeIniciada.PROCESSO_PROCESSO_IP, ip));

			filtro.adicionarParametro(new ParametroSimples(FiltroFuncionalidadeIniciada.FUNCIONALIDADE_SITUACAO, //
							FuncionalidadeSituacao.EM_PROCESSAMENTO));

			Collection processoIgualEmExecucao;
			try{
				processoIgualEmExecucao = getControladorUtil().pesquisar(filtro, FuncionalidadeIniciada.class.getName());

				if(Util.isVazioOrNulo(processoIgualEmExecucao)){

					estaPronto = true;

					LOGGER.debug("PROCESSO[" + processoEmEspera.getId() + "] possue(" + qtdPrecedentesConcluidos + "/"
									+ qtdPendenciasEsperadasConcluidas + ") precedentes concluidos. isPronto?" + estaPronto);

				}else{
					// ..........................................................................................................
					estaPronto = qtdPrecedentesConcluidos == qtdPendenciasEsperadasConcluidas;
					// ..........................................................................................................
				}

			}catch(ControladorException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// ..........................................................................................................
			
		}
		return estaPronto;
	}

	/**
	 * @author Rodrigo Silveira
	 * @throws ControladorException
	 * @throws ErroRepositorioException
	 * @date 26/09/2006
	 */
	public void iniciarRelatoriosAgendados() throws ControladorException{

		// Consulta todas as configuraï¿½ï¿½es de relatï¿½rio gravadas na base dentro
		// das funcionalidades iniciadas com status de AGENDADA

		try{
			Iterator<byte[]> iterator = repositorioBatch.iniciarRelatoriosAgendados().iterator();

			boolean faturaMensalAgendada = false;
			boolean avisoCorteAgendado = false;
			boolean avisoDebitoAgendado = false;
			String ip = VerificadorIP.getInstancia().getIpAtivo();

			while(iterator.hasNext()){
				TarefaRelatorio tarefaRelatorio = (TarefaRelatorio) IoUtil.transformarBytesParaObjeto(iterator.next());

				if(tarefaRelatorio == null){
					LOGGER.info("Atenção: tarefa igual a NULL. (iniciarRelatoriosAgendados())");
					continue;
				}

				if(tarefaRelatorio instanceof RelatorioContaTipo2){
					// Workaround até identificar o problema de troca do código de barras de Faturas
					// Pesquisa se outra tarefa deste tipo está em execução, se sim, 'adia' a
					// execução. Além disso, agenda apenas 1 tarefa por ciclo verificador
					FiltroProcessoIniciado filtroProcessoFatura = new FiltroProcessoIniciado();
					filtroProcessoFatura.adicionarParametro(new ParametroSimples(FiltroProcessoIniciado.ID,
									Processo.GERAR_RELATORIO_CONTA_TIPO_2));
					filtroProcessoFatura.adicionarParametro(new ParametroSimples(FiltroProcessoIniciado.PROCESSO_SITUACAO_ID,
									ProcessoSituacao.EM_PROCESSAMENTO));
					if(ip != null) filtroProcessoFatura.adicionarParametro(new ParametroSimples(FiltroProcessoIniciado.IP, ip));
					Collection<ProcessoIniciado> colecaoProcessoFaturaAtivo = getControladorUtil().pesquisar(filtroProcessoFatura,
									ProcessoIniciado.class.getName());
					if(colecaoProcessoFaturaAtivo != null && !colecaoProcessoFaturaAtivo.isEmpty()){
						continue;
					}
					if(!faturaMensalAgendada){
						tarefaRelatorio.agendarTarefaBatch();
						faturaMensalAgendada = true;
					}
					continue;
				}else if(tarefaRelatorio instanceof RelatorioAvisoCorte){
					// Workaround até identificar o problema de troca do código de barras de Faturas
					// Pesquisa se outra tarefa deste tipo está em execução, se sim, 'adia' a
					// execução. Além disso, agenda apenas 1 tarefa por ciclo verificador
					FiltroProcessoIniciado avisoCorteFiltro = new FiltroProcessoIniciado();
					avisoCorteFiltro.adicionarParametro(new ParametroSimples(FiltroProcessoIniciado.ID,
									Processo.GERAR_RELATORIO_AVISO_CORTE));
					avisoCorteFiltro.adicionarParametro(new ParametroSimples(FiltroProcessoIniciado.PROCESSO_SITUACAO_ID,
									ProcessoSituacao.EM_PROCESSAMENTO));
					if(ip != null) avisoCorteFiltro.adicionarParametro(new ParametroSimples(FiltroProcessoIniciado.IP, ip));
					Collection<ProcessoIniciado> colecaoProcessoFaturaAtivo = getControladorUtil().pesquisar(avisoCorteFiltro,
									ProcessoIniciado.class.getName());
					if(colecaoProcessoFaturaAtivo != null && !colecaoProcessoFaturaAtivo.isEmpty()){
						continue;
					}
					if(!avisoCorteAgendado){
						tarefaRelatorio.agendarTarefaBatch();
						avisoCorteAgendado = true;
					}
					continue;
				}else if(tarefaRelatorio instanceof RelatorioAvisoDebito){
					// Workaround até identificar o problema de troca do código de barras de Faturas
					// Pesquisa se outra tarefa deste tipo está em execução, se sim, 'adia' a
					// execução. Além disso, agenda apenas 1 tarefa por ciclo verificador
					FiltroProcessoIniciado avisoDebitoFiltro = new FiltroProcessoIniciado();
					avisoDebitoFiltro.adicionarParametro(new ParametroSimples(FiltroProcessoIniciado.ID,
									Processo.GERAR_RELATORIO_AVISO_DEBITO));
					avisoDebitoFiltro.adicionarParametro(new ParametroSimples(FiltroProcessoIniciado.PROCESSO_SITUACAO_ID,
									ProcessoSituacao.EM_PROCESSAMENTO));
					Collection<ProcessoIniciado> colecaoProcessoFaturaAtivo = getControladorUtil().pesquisar(avisoDebitoFiltro,
									ProcessoIniciado.class.getName());
					if(ip != null) avisoDebitoFiltro.adicionarParametro(new ParametroSimples(FiltroProcessoIniciado.IP, ip));
					if(colecaoProcessoFaturaAtivo != null && !colecaoProcessoFaturaAtivo.isEmpty()){
						continue;
					}
					if(!avisoDebitoAgendado){
						tarefaRelatorio.agendarTarefaBatch();
						avisoDebitoAgendado = true;
					}
					continue;
				}
				tarefaRelatorio.agendarTarefaBatch();
			}
		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);

		}catch(IOException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);

		}catch(ClassNotFoundException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

	}

	/**
	 * Retorna todas as Funcionalidades Iniciadas que estï¿½o em situaï¿½ï¿½o de
	 * iniciar o processamento
	 * 
	 * @author Rodrigo Silveira
	 * @param processosId
	 * @throws ControladorException
	 * @date 11/09/2006
	 */
	public Collection<FuncionalidadeIniciada> verificarFuncionalidadesIniciadasProntasParaExecucao(Collection<Integer> processosId)
					throws ControladorException{

		Collection<FuncionalidadeIniciada> retorno = new ArrayList<FuncionalidadeIniciada>();

		try{
			// Pesquisa todas as funcionalidades Iniciadas prontas para execuï¿½ï¿½o
			// independente do sequencialExecucao

			Collection<Object[]> funcionalidadesIniciadasExecucao = repositorioBatch
							.pesquisarFuncionaldadesIniciadasProntasExecucao(processosId);
			List<Integer> funcionalidadesAguardando = new ArrayList<Integer>();
			// Para cada Funcionalidade Iniciada, verificar se todas as
			// funcionalidades iniciadas com sequencialExecucao anterior do
			// mesmo processo estï¿½o
			// concluï¿½das
			for(Object[] objects : funcionalidadesIniciadasExecucao){
				FuncionalidadeIniciada funcionalidadeIniciada = (FuncionalidadeIniciada) objects[0];

				int quantidadeFuncionalidadesForaOrdem = repositorioBatch.pesquisarQuantidadeFuncionaldadesIniciadasForaOrdemExecucao(
								(int) ((Short) objects[1]), funcionalidadeIniciada.getProcessoIniciado().getId());

				if(isFuncionalidadeProntaParaIniciar(funcionalidadeIniciada, quantidadeFuncionalidadesForaOrdem)){
					// Se todas estiverem concluï¿½das significa que ï¿½ a vez de
					// executar esta funcionalidade iniciada
					LOGGER.info("INICIANDO FuncionalidadeIniciada[" + funcionalidadeIniciada.getId() + //
									"] do ProcessoIniciado[" + funcionalidadeIniciada.getProcessoIniciado().getId() + //
									"] do Processo[" + funcionalidadeIniciada.getProcessoIniciado().getProcesso().getId() + //
									"].");
					retorno.add(funcionalidadeIniciada);
					// A prï¿½xima funcionalidade Iniciada vai ficar para a
					// prï¿½xima verificaï¿½ï¿½o
					// break;

				}else{
					if(FuncionalidadeSituacao.CONCLUIDA != funcionalidadeIniciada.getFuncionalidadeSituacao().getId()){
						funcionalidadesAguardando.add(funcionalidadeIniciada.getId());
						LOGGER.debug("AGUARDANDO funcionalidadeIniciada[" + funcionalidadeIniciada.getId() + //
										"] do ProcessoIniciado[" + funcionalidadeIniciada.getProcessoIniciado().getId() + //
										"] do Processo[" + funcionalidadeIniciada.getProcessoIniciado().getProcesso().getId() + //
										"].");
					}
				}
			}

			if(!Util.isVazioOrNulo(funcionalidadesAguardando)){
				LOGGER.info("Funcionalidades aguardando o fim de outros processos: QTD:[" + funcionalidadesAguardando.size()
								+ "] - FUIN_ID: " + Arrays.toString(funcionalidadesAguardando.toArray()));
			}

		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);

		}
		return retorno;
	}

	private boolean isFuncionalidadeProntaParaIniciar(FuncionalidadeIniciada funcionalidadeIniciada, int quantidadeFuncionalidadesForaOrdem){

		return quantidadeFuncionalidadesForaOrdem == 0 //
						&& (!funcionalidadeIniciada.getFuncionalidadeSituacao().getId().equals(FuncionalidadeSituacao.CONCLUIDA));
	}

	/**
	 * Inicia uma funcionalidade iniciada do relatorio
	 * 
	 * @author Rodrigo Silveira
	 * @date 26/09/2006
	 * @param idFuncionalidadeIniciada
	 * @throws ControladorException
	 */
	public void iniciarFuncionalidadeIniciadaRelatorio(int idFuncionalidadeIniciada) throws ControladorException{

		try{
			repositorioBatch.iniciarFuncionalidadeIniciadaRelatorio(idFuncionalidadeIniciada);
			repositorioBatch.iniciarProcessoIniciadoRelatorio(idFuncionalidadeIniciada);
		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);

		}
	}

	/**
	 * Encerra uma funcionalidade iniciada do relatorio
	 * 
	 * @author Rodrigo Silveira
	 * @date 26/09/2006
	 * @param idFuncionalidadeIniciada
	 * @throws ControladorException
	 */
	public void encerrarFuncionalidadeIniciadaRelatorio(int idFuncionalidadeIniciada, boolean concluiuComErro) throws ControladorException{

		try{
			if(concluiuComErro){

				repositorioBatch.encerrarFuncionalidadeIniciadaRelatorio(idFuncionalidadeIniciada,
								FuncionalidadeSituacao.CONCLUIDA_COM_ERRO);
				repositorioBatch.encerrarProcessoIniciadoRelatorio(idFuncionalidadeIniciada, ProcessoSituacao.CONCLUIDO_COM_ERRO);
			}else{

				repositorioBatch.encerrarFuncionalidadeIniciadaRelatorio(idFuncionalidadeIniciada, FuncionalidadeSituacao.CONCLUIDA);
				repositorioBatch.encerrarProcessoIniciadoRelatorio(idFuncionalidadeIniciada, ProcessoSituacao.CONCLUIDO);

			}

		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

	}

	/**
	 * Pesquisa todos as funcionalidades iniciadas que representam os relatórios
	 * batch do sistema
	 * 
	 * @author Rodrigo Silveira
	 * @date 09/10/2006
	 */
	public Collection<Object[]> pesquisarRelatoriosBatchSistema() throws ControladorException{

		try{
			return repositorioBatch.pesquisarRelatoriosBatchSistema();
		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

	}

	/**
	 * Remove uma coleção de objetos genéricos na base com um flush para cada 50
	 * registros removidos.
	 * 
	 * @author Pedro Alexandre
	 * @date 09/10/2006
	 * @param colecaoObjetos
	 * @throws ControladorException
	 */
	public void removerColecaoObjetoParaBatch(Collection<Object> colecaoObjetos) throws ControladorException{

		try{
			if(colecaoObjetos != null && !colecaoObjetos.isEmpty()){
				repositorioBatch.removerColecaoObjetoParaBatch(colecaoObjetos);
			}
		}catch(ErroRepositorioException e){
			LOGGER.error(e);
			throw new ControladorException("erro.sistema", e);
		}
	}

	/**
	 * Inicia um processo relacionado com um relatório que será processado em batch
	 * 
	 * @author Rodrigo Silveira
	 * @date 23/10/2006
	 * @throws ControladorException
	 */

	public void iniciarProcessoRelatorio(TarefaRelatorio tarefaRelatorio) throws ControladorException{

		try{
			Date agora = new Date();

			// Constrói um processoIniciado para o Relatório
			ProcessoIniciado processoIniciado = new ProcessoIniciado();
			processoIniciado.setUsuario(tarefaRelatorio.getUsuario());

			ProcessoSituacao processoSituacao = new ProcessoSituacao();
			processoSituacao.setId(ProcessoSituacao.AGENDADO);
			processoIniciado.setProcessoSituacao(processoSituacao);

			Processo processo = new Processo();

			// Alteração realizada em 17/05/2012 por Luciano Galvão - Início
			Integer processoId = obterProcessoId(tarefaRelatorio);
			// Alteração realizada em 17/05/2012 por Luciano Galvão - Fim

			processo.setId(processoId);

			processoIniciado.setProcesso(processo);
			processoIniciado.setDataHoraAgendamento(agora);

			// Ver isso em outro lugar
			// processoIniciado.setDataHoraInicio(agora);
			processoIniciado.setDataHoraComando(agora);

			getControladorUtil().inserir(processoIniciado);

			// Este trecho pesquisa todos do processoFuncionalidade relacionados
			// com o processo do objeto a ser inserido
			FiltroProcessoFuncionalidade filtroProcessoFuncionalidade = new FiltroProcessoFuncionalidade();

			filtroProcessoFuncionalidade.adicionarParametro(new ParametroSimples(FiltroProcessoFuncionalidade.ID_PROCESSO, processoIniciado
							.getProcesso().getId()));

			filtroProcessoFuncionalidade.adicionarParametro(new ParametroSimples(FiltroProcessoFuncionalidade.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			Collection processosFuncionaliadade = getControladorUtil().pesquisar(filtroProcessoFuncionalidade,
							ProcessoFuncionalidade.class.getName());

			FuncionalidadeSituacao funcionalidadeSituacao = new FuncionalidadeSituacao();
			funcionalidadeSituacao.setId(FuncionalidadeSituacao.AGENDADA);

			Iterator iterator = processosFuncionaliadade.iterator();
			while(iterator.hasNext()){
				ProcessoFuncionalidade processoFuncionalidade = (ProcessoFuncionalidade) iterator.next();
				FuncionalidadeIniciada funcionalidadeIniciada = new FuncionalidadeIniciada();

				// AGENDADA NAO ENTRA EM EXECUCAO
				// SOH EXECUTA QUANDO ENTRA EM_ESPERA
				funcionalidadeIniciada.setFuncionalidadeSituacao(funcionalidadeSituacao);

				funcionalidadeIniciada.setProcessoIniciado(processoIniciado);

				funcionalidadeIniciada.setProcessoFuncionalidade(processoFuncionalidade);

				// funcionalidadeIniciada.setDataHoraInicio(agora);
				// funcionalidadeIniciada.setDataHoraTermino(agora);

				funcionalidadeIniciada.setId((Integer) getControladorUtil().inserir(funcionalidadeIniciada));

				// Seta os parametros da funcionalidadeIniciada
				tarefaRelatorio.setIdFuncionalidadeIniciada(funcionalidadeIniciada.getId());

				// Seta o objeto para ser serializado no banco, onde
				// depois será executado por uma thread
				funcionalidadeIniciada.setTarefaBatch(IoUtil.transformarObjetoParaBytes(tarefaRelatorio));

				getControladorUtil().atualizar(funcionalidadeIniciada);

			}
		}catch(IOException e){
			throw new SistemaException(e, "Erro Batch Relatório");
		}

	}

	/**
	 * Pesquisa todos as funcionalidades iniciadas que representam os relatï¿½rios batch do sistema
	 * por Usuï¿½rio
	 * 
	 * @author Rodrigo Silveira
	 * @date 25/10/2006
	 */
	public Collection<Object[]> pesquisarRelatoriosBatchPorUsuarioSistema(int idProcesso, int pageOffset) throws ControladorException{

		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat formatoCompletoData = new SimpleDateFormat("dd/MM/yyyy k:mm:ss");
		try{

			Collection<Object[]> retornoMetodo = repositorioBatch.pesquisarRelatoriosBatchPorUsuarioSistema(idProcesso, pageOffset);

			Date dataCorrente = new Date();
			Calendar calendarCorrente = Calendar.getInstance();
			calendarCorrente.setTime((dataCorrente));
			calendarCorrente.set(Calendar.SECOND, 0);
			calendarCorrente.set(Calendar.MINUTE, 0);
			calendarCorrente.set(Calendar.HOUR_OF_DAY, 0);

			String dataCorrenteS = formato.format(calendarCorrente.getTime());

			for(Object[] array : retornoMetodo){
				// Adiciona a quantidade de dias para indicar
				// até quando o relatório ficará disponível para download
				if(array[3] != null){
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(((Timestamp) array[3]));
					calendar.set(Calendar.SECOND, 0);
					calendar.set(Calendar.MINUTE, 0);
					calendar.set(Calendar.HOUR_OF_DAY, 0);

					// Quantidade de dias independente do parâmetro
					int qtdDiasDownload = 3;

					// Quantidade de dias para ficar disponível o download do relatório
					String qtdDias = (String) ParametroBatch.P_DIAS_DOWNLOAD_RELATORIO.executar();

					if(!Util.isVazioOuBranco(qtdDias)){
						// Quantidade de dias do parâmetro
						qtdDiasDownload = Integer.valueOf(qtdDias).intValue();
					}

					calendar.add(Calendar.DAY_OF_MONTH, qtdDiasDownload);

					array[3] = formato.format(calendar.getTime());
				}

				Date dataArray = null;
				if(array[3] != null){
					dataArray = (Date) DateUtil.parse(((String) array[3]));
				}

				dataCorrente = (Date) DateUtil.parse(((String) dataCorrenteS));
				if(((Integer) array[2]).equals(FuncionalidadeSituacao.CONCLUIDA) && (dataArray != null)
								&& dataCorrente.compareTo(dataArray) <= 0){
					array[2] = Boolean.TRUE;
				}else{
					array[2] = Boolean.FALSE;
				}

				if(array[6] != null){

					array[6] = formatoCompletoData.format((Date) array[6]);
				}
			}
			return retornoMetodo;

		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}catch(ParseException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

	}

	public Long pesquisarQuantidadeRelatoriosBatchPorUsuarioSistema(int idProcesso) throws ControladorException{

		try{
			return repositorioBatch.pesquisarQuantidadeRelatoriosBatchPorUsuarioSistema(idProcesso);

		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

	}

	/**
	 * Remove do sistema todos os relatï¿½rios batch que estï¿½o na data de
	 * expiraï¿½ï¿½o
	 * 
	 * @author Rodrigo Silveira
	 * @date 26/10/2006
	 */
	public void deletarRelatoriosBatchDataExpiracao() throws ControladorException{

		try{
			Calendar dataExpiracao = Calendar.getInstance();
			dataExpiracao.set(Calendar.SECOND, 59);
			dataExpiracao.set(Calendar.MINUTE, 59);
			dataExpiracao.set(Calendar.HOUR_OF_DAY, 23);
			dataExpiracao.add(Calendar.DAY_OF_MONTH, -3);

			repositorioBatch.deletarRelatoriosBatchDataExpiracao(dataExpiracao.getTime());

		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

	}

	private ControladorArrecadacaoLocal getControladorArrecadacao(){

		ControladorArrecadacaoLocalHome localHome = null;
		ControladorArrecadacaoLocal local = null;

		// pega a instï¿½ncia do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorArrecadacaoLocalHome) locator.getLocalHomePorEmpresa(ConstantesJNDI.CONTROLADOR_ARRECADACAO_SEJB);
			// guarda a referencia de um objeto capaz de fazer chamadas ï¿½
			// objetos remotamente
			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}
	}

	/**
	 * Retorna o valor de controladorLocalidade
	 * 
	 * @return O valor de controladorLocalidade
	 */
	private ControladorFaturamentoLocal getControladorFaturamento(){

		ControladorFaturamentoLocalHome localHome = null;
		ControladorFaturamentoLocal local = null;

		// pega a instï¿½ncia do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorFaturamentoLocalHome) locator.getLocalHomePorEmpresa(ConstantesJNDI.CONTROLADOR_FATURAMENTO_SEJB);
			// guarda a referencia de um objeto capaz de fazer chamadas ï¿½
			// objetos remotamente
			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}
	}

	/**
	 * Author: Rafael Santos Data: 04/01/2006
	 * Retorna o valor do Controlador de Cobranca
	 * 
	 * @return O valor de controladorCobrancaLocal
	 */
	private ControladorCobrancaLocal getControladorCobranca(){

		ControladorCobrancaLocalHome localHome = null;
		ControladorCobrancaLocal local = null;

		// pega a instï¿½ncia do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorCobrancaLocalHome) locator.getLocalHomePorEmpresa(ConstantesJNDI.CONTROLADOR_COBRANCA_SEJB);
			// guarda a referencia de um objeto capaz de fazer chamadas ï¿½
			// objetos remotamente
			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}

	}

	/**
	 * Pesquisa no sistema todos os processos que pararam na metade devido a uma
	 * falha no servidor e marca com 'EXECUï¿½ï¿½O INTERROMPIDA'
	 * 
	 * @author Rodrigo Silveira
	 * @date 27/01/2007
	 */
	public void marcarProcessosInterrompidos() throws ControladorException{

		String ip = VerificadorIP.getInstancia().getIpAtivo();

		FiltroProcessoIniciado filtroProcessoIniciado = new FiltroProcessoIniciado();

		filtroProcessoIniciado.adicionarParametro(new ParametroSimples(FiltroProcessoIniciado.PROCESSO_SITUACAO_ID,
						ProcessoSituacao.EM_PROCESSAMENTO));
		if(ip != null){
			filtroProcessoIniciado.adicionarParametro(new ParametroSimples(FiltroProcessoIniciado.IP, ip));
		}
		Collection processosIniciadosParaMarcacao = getControladorUtil()
						.pesquisar(filtroProcessoIniciado, ProcessoIniciado.class.getName());

		Iterator iteratorProcessos = processosIniciadosParaMarcacao.iterator();

		ProcessoSituacao processoSituacao = new ProcessoSituacao();
		processoSituacao.setId(ProcessoSituacao.EXECUCAO_CANCELADA);
		while(iteratorProcessos.hasNext()){
			ProcessoIniciado processoIniciado = (ProcessoIniciado) iteratorProcessos.next();

			processoIniciado.setProcessoSituacao(processoSituacao);

			getControladorUtil().atualizar(processoIniciado);

		}

		FiltroFuncionalidadeIniciada filtroFuncionalidadeIniciada = new FiltroFuncionalidadeIniciada();
		filtroFuncionalidadeIniciada.adicionarParametro(new ParametroSimples(FiltroFuncionalidadeIniciada.FUNCIONALIDADE_SITUACAO,
						FuncionalidadeSituacao.EM_PROCESSAMENTO));
		if(ip != null){
			filtroFuncionalidadeIniciada.adicionarParametro(new ParametroSimples(FiltroFuncionalidadeIniciada.PROCESSO_INICIADO_IP, ip));
		}

		Collection funcionalidadesIniciadasParaMarcacao = getControladorUtil().pesquisar(filtroFuncionalidadeIniciada,
						FuncionalidadeIniciada.class.getName());

		Iterator iteratorFuncionalidades = funcionalidadesIniciadasParaMarcacao.iterator();

		FuncionalidadeSituacao funcionalidadeSituacao = new FuncionalidadeSituacao();
		funcionalidadeSituacao.setId(FuncionalidadeSituacao.EXECUCAO_CANCELADA);
		while(iteratorFuncionalidades.hasNext()){
			FuncionalidadeIniciada funcionalidadeIniciada = (FuncionalidadeIniciada) iteratorFuncionalidades.next();

			funcionalidadeIniciada.setFuncionalidadeSituacao(funcionalidadeSituacao);

			getControladorUtil().atualizar(funcionalidadeIniciada);

		}

	}

	private ControladorCadastroLocal getControladorCadastro(){

		ControladorCadastroLocalHome localHome = null;
		ControladorCadastroLocal local = null;

		ServiceLocator locator = null;
		try{
			locator = ServiceLocator.getInstancia();
			localHome = (ControladorCadastroLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_CADASTRO_SEJB);

			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}
	}

	/**
	 * Retorna a interface remota de ControladorParametro
	 * 
	 * @return A interface remota do controlador de parï¿½metro
	 */
	private ControladorLocalidadeLocal getControladorLocalidade(){

		ControladorLocalidadeLocalHome localHome = null;
		ControladorLocalidadeLocal local = null;

		// pega a instï¿½ncia do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorLocalidadeLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_LOCALIDADE_SEJB);
			// guarda a referencia de um objeto capaz de fazer chamadas
			// objetos remotamente
			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}
	}

	/**
	 * Retorna o valor de controladorMicromedicao
	 * 
	 * @return O valor de controladorMicromedicao
	 */
	private ControladorMicromedicaoLocal getControladorMicromedicao(){

		ControladorMicromedicaoLocalHome localHome = null;
		ControladorMicromedicaoLocal local = null;

		// pega a instï¿½ncia do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorMicromedicaoLocalHome) locator.getLocalHomePorEmpresa(ConstantesJNDI.CONTROLADOR_MICROMEDICAO_SEJB);
			// guarda a referencia de um objeto capaz de fazer chamadas
			// objetos remotamente
			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}
	}

	/**
	 * Retorna o valor de controladorMicromedicao
	 * 
	 * @return O valor de controladorMicromedicao
	 */
	private ControladorGerencialMicromedicaoLocal getControladorGerencialMicromedicao(){

		ControladorGerencialMicromedicaoLocalHome localHome = null;
		ControladorGerencialMicromedicaoLocal local = null;

		// pega a instï¿½ncia do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorGerencialMicromedicaoLocalHome) locator
							.getLocalHome(ConstantesJNDI.CONTROLADOR_GERENCIAL_MICROMEDICAO_SEJB);
			// guarda a referencia de um objeto capaz de fazer chamadas
			// objetos remotamente
			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}

	}

	private ControladorGerencialFaturamentoLocal getControladorGerencialFaturamento(){

		ControladorGerencialFaturamentoLocalHome localHome = null;
		ControladorGerencialFaturamentoLocal local = null;

		// pega a instï¿½ncia do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorGerencialFaturamentoLocalHome) locator
							.getLocalHome(ConstantesJNDI.CONTROLADOR_GERENCIAL_FATURAMENTO_SEJB);
			// guarda a referencia de um objeto capaz de fazer chamadas ï¿½
			// objetos remotamente
			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}

	}

	/**
	 * Retorna o valor de controladorFinanceiro
	 * 
	 * @return O valor de controladorLocalidade
	 */
	private ControladorFinanceiroLocal getControladorFinanceiro(){

		ControladorFinanceiroLocalHome localHome = null;
		ControladorFinanceiroLocal local = null;

		// pega a instï¿½ncia do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorFinanceiroLocalHome) locator.getLocalHomePorEmpresa(ConstantesJNDI.CONTROLADOR_FINANCEIRO_SEJB);
			// guarda a referencia de um objeto capaz de fazer chamadas
			// objetos remotamente
			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}
	}

	/**
	 * Insere objeto genï¿½rico na base
	 * 
	 * @author Marcio Roberto
	 * @date 18/05/2007
	 * @param Objeto
	 * @throws ControladorException
	 */
	public Object inserirObjetoParaBatchGerencial(Object objeto) throws ControladorException{

		try{
			repositorioBatch.inserirObjetoParaBatchGerencial(objeto);
			return objeto;
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}
	}

	/**
	 * <Breve descriï¿½ï¿½o sobre o caso de uso>
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
					throws ControladorException{

		Date agora = new Date();

		Integer codigoProcessoIniciadoGerado = null;

		// SistemaParametro sistemaParametros = getControladorUtil().pesquisarParametrosDoSistema();

		// Integer anoMesFaturamentoSistemaParametro = sistemaParametros.getAnoMesFaturamento();

		try{

			// Todos os processo serï¿½o iniciados com a situaï¿½ï¿½o EM_ESPERA para q
			// sejam executados o mais cedo possï¿½vel
			ProcessoSituacao processoSituacao = new ProcessoSituacao();
			processoSituacao.setId(ProcessoSituacao.EM_ESPERA);
			processoIniciado.setProcessoSituacao(processoSituacao);
			// processoIniciado.setDataHoraInicio(agora);
			processoIniciado.setDataHoraComando(agora);
			// processoIniciado.setDataHoraAgendamento(agora);

			codigoProcessoIniciadoGerado = (Integer) getControladorUtil().inserir(processoIniciado);

			// Este trecho pesquisa todos do processoFuncionalidade relacionados
			// com o processo do objeto a ser inserido
			FiltroProcessoFuncionalidade filtroProcessoFuncionalidade = new FiltroProcessoFuncionalidade();

			filtroProcessoFuncionalidade.adicionarParametro(new ParametroSimples(FiltroProcessoFuncionalidade.ID_PROCESSO, processoIniciado
							.getProcesso().getId()));
			filtroProcessoFuncionalidade.adicionarParametro(new ParametroSimples(FiltroProcessoFuncionalidade.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			Collection processosFuncionaliadade = getControladorUtil().pesquisar(filtroProcessoFuncionalidade,
							ProcessoFuncionalidade.class.getName());

			FuncionalidadeSituacao funcionalidadeSituacao = new FuncionalidadeSituacao();
			funcionalidadeSituacao.setId(FuncionalidadeSituacao.EM_ESPERA);

			Iterator iterator = processosFuncionaliadade.iterator();
			while(iterator.hasNext()){
				ProcessoFuncionalidade processoFuncionalidade = (ProcessoFuncionalidade) iterator.next();
				FuncionalidadeIniciada funcionalidadeIniciada = new FuncionalidadeIniciada();

				funcionalidadeIniciada.setFuncionalidadeSituacao(funcionalidadeSituacao);
				funcionalidadeIniciada.setProcessoIniciado(processoIniciado);
				funcionalidadeIniciada.setProcessoFuncionalidade(processoFuncionalidade);
				funcionalidadeIniciada.setId((Integer) getControladorUtil().inserir(funcionalidadeIniciada));

				// Seta os parametros da funcionalidadeIniciada

				// ----------------------------------------------------------
				// Lista dos possï¿½veis processos eventuais ou mensais
				// ----------------------------------------------------------
				try{

					switch(funcionalidadeIniciada.getProcessoFuncionalidade().getFuncionalidade().getId()){

						/** Pedro Alexandre 18-06-2007 */
						case Funcionalidade.GERAR_RESUMO_DEVEDORES_DUVIDOSOS:
							TarefaBatchGerarResumoDevedoresDuvidosos dadosGerarResumoDevedoresDuvidosos = new TarefaBatchGerarResumoDevedoresDuvidosos(
											processoIniciado.getUsuario(), funcionalidadeIniciada.getId());

							Integer anoMesReferenciaContabil = new Integer((String) dadosProcessamento.get("anoMesReferenciaContabil"));

							Collection<Integer> colecaoIdsLocalidadesGerarResumoDevedoresDuvidosos = getControladorFinanceiro()
											.pesquisarIdsLocalidadesParaGerarResumoDevedoresDuvidosos(anoMesReferenciaContabil);

							dadosGerarResumoDevedoresDuvidosos.addParametro("anoMesReferenciaContabil", anoMesReferenciaContabil);

							// Seta os parametros para rodar a funcionalidade
							dadosGerarResumoDevedoresDuvidosos.addParametro(ConstantesSistema.COLECAO_UNIDADES_PROCESSAMENTO_BATCH,
											colecaoIdsLocalidadesGerarResumoDevedoresDuvidosos);

							// Seta o objeto para ser serializado no banco, onde
							// depois serï¿½ executado por uma thread
							funcionalidadeIniciada.setTarefaBatch(IoUtil.transformarObjetoParaBytes(dadosGerarResumoDevedoresDuvidosos));

							getControladorUtil().atualizar(funcionalidadeIniciada);

							break;

						/** Pedro Alexandre 25-06-2007 */
						case Funcionalidade.GERAR_LANCAMENTOS_CONTABEIS_DEVEDORES_DUVIDOSOS:
							TarefaBatchGerarLancamentosContabeisDevedoresDuvidosos dadosGerarLancamentosContabeisDevedoresDuvidosos = new TarefaBatchGerarLancamentosContabeisDevedoresDuvidosos(
											processoIniciado.getUsuario(), funcionalidadeIniciada.getId());

							Integer anoMesReferenciaContabilLancamentosContabeis = new Integer((String) dadosProcessamento
											.get("anoMesReferenciaContabil"));

							Collection<Integer> colecaoIdsLocalidadesGerarLancamentosContabeisDevedoresDuvidosos = getControladorFinanceiro()
											.pesquisarIdsLocalidadesParaGerarResumoDevedoresDuvidosos(
															anoMesReferenciaContabilLancamentosContabeis);

							dadosGerarLancamentosContabeisDevedoresDuvidosos.addParametro("anoMesReferenciaContabil",
											anoMesReferenciaContabilLancamentosContabeis);

							// Seta os parametros para rodar a funcionalidade
							dadosGerarLancamentosContabeisDevedoresDuvidosos.addParametro(
											ConstantesSistema.COLECAO_UNIDADES_PROCESSAMENTO_BATCH,
											colecaoIdsLocalidadesGerarLancamentosContabeisDevedoresDuvidosos);

							// Seta o objeto para ser serializado no banco, onde
							// depois serï¿½ executado por uma thread
							funcionalidadeIniciada.setTarefaBatch(IoUtil
											.transformarObjetoParaBytes(dadosGerarLancamentosContabeisDevedoresDuvidosos));

							getControladorUtil().atualizar(funcionalidadeIniciada);

							break;

						default:

					}

				}catch(IOException e){
					sessionContext.setRollbackOnly();
					throw new ControladorException("erro.sistema", e);

				}

			}

		}catch(ControladorException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

		return codigoProcessoIniciadoGerado;

	}

	/**
	 * Reinicia uma funcionalidade iniciada
	 * <Identificador e nome do caso de uso>
	 * 
	 * @author Rafael Corrï¿½a
	 * @date 06/10/2007
	 * @param idsFuncionalidadesIniciadas
	 * @param idProcessoIniciado
	 * @return
	 * @throws ControladorException
	 */
	public void reiniciarFuncionalidadesIniciadas(String[] idsFuncionalidadesIniciadas, Integer idProcessoIniciado)
					throws ControladorException{

		int i = 0;
		String ip = VerificadorIP.getInstancia().getIpAtivo();

		FuncionalidadeSituacao funcionalidadeSituacao = new FuncionalidadeSituacao();
		funcionalidadeSituacao.setId(FuncionalidadeSituacao.EM_ESPERA);

		while(i < idsFuncionalidadesIniciadas.length){

			String idFuncionalidadeIniciada = idsFuncionalidadesIniciadas[i];

			try{
				repositorioBatch.removerUnidadesIniciadas(new Integer(idFuncionalidadeIniciada));
			}catch(ErroRepositorioException ex){
				sessionContext.setRollbackOnly();
				throw new ControladorException("erro.sistema", ex);
			}

			FiltroFuncionalidadeIniciada filtroFuncionalidadeIniciada = new FiltroFuncionalidadeIniciada();
			filtroFuncionalidadeIniciada
							.adicionarParametro(new ParametroSimples(FiltroFuncionalidadeIniciada.ID, idFuncionalidadeIniciada));
			if(ip != null) filtroFuncionalidadeIniciada.adicionarParametro(new ParametroSimples("processoIniciado.ip", ip));

			Collection colecaoFuncionalidadesIniciadas = getControladorUtil().pesquisar(filtroFuncionalidadeIniciada,
							FuncionalidadeIniciada.class.getName());

			FuncionalidadeIniciada funcionalidadeIniciada = (FuncionalidadeIniciada) Util
							.retonarObjetoDeColecao(colecaoFuncionalidadesIniciadas);

			if(funcionalidadeIniciada == null && ip != null){

				throw new ControladorException("atencao.processo.ip.diferente");

			}else{

				Object tarefa = null;
				try{
					tarefa = IoUtil.transformarBytesParaObjeto(funcionalidadeIniciada.getTarefaBatch());
				}catch(Exception e){
					throw new SistemaException(e, e.getMessage());
				}

				if(tarefa == null){
					LOGGER.info("Atenção: tarefa igual a NULL. (reiniciarFuncionalidadesIniciadas(...))");
					LOGGER.info("funcionalidadeIniciada: " + funcionalidadeIniciada.getId());
					i = i + 1;
					continue;
				}

				if(tarefa instanceof TarefaRelatorio){
					funcionalidadeSituacao.setId(FuncionalidadeSituacao.AGENDADA);
				}

				funcionalidadeIniciada.setFuncionalidadeSituacao(funcionalidadeSituacao);
				funcionalidadeIniciada.setDataHoraTermino(null);
				funcionalidadeIniciada.setDataHoraInicio(null);

				getControladorUtil().atualizar(funcionalidadeIniciada);
			}

			i = i + 1;
		}

		FiltroProcessoIniciado filtroProcessoIniciado = new FiltroProcessoIniciado();
		filtroProcessoIniciado.adicionarParametro(new ParametroSimples(FiltroProcessoIniciado.ID, idProcessoIniciado));
		if(ip != null) filtroProcessoIniciado.adicionarParametro(new ParametroSimples(FiltroProcessoIniciado.IP, ip));

		Collection colecaoProcessosIniciados = getControladorUtil().pesquisar(filtroProcessoIniciado, ProcessoIniciado.class.getName());

		ProcessoIniciado processoIniciado = (ProcessoIniciado) Util.retonarObjetoDeColecao(colecaoProcessosIniciados);

		if(processoIniciado == null && ip != null){

			throw new ControladorException("atencao.processo.ip.diferente");

		}else{

			ProcessoSituacao processoSituacao = new ProcessoSituacao();
			processoSituacao.setId(ProcessoSituacao.EM_ESPERA);
			processoIniciado.setProcessoSituacao(processoSituacao);

			getControladorUtil().atualizar(processoIniciado);
		}
	}

	/**
	 * Método que retorna o usuário que inicializou uma atividade Batch. Normalmente será utilizado
	 * por processos comandados que precisam do
	 * usuário que 'comandou' para a geração de um relatório , por exemplo.
	 * 
	 * @author Eduardo Henrique
	 * @date 17/10/2008
	 * @param idFuncionalidadesIniciada
	 *            (id do Processo em Funcionalidade_Iniciada)
	 * @return Usuario
	 * @throws ControladorException
	 */
	public Usuario obterUsuarioFuncionalidadeIniciada(Integer idFuncionalidadeIniciada) throws ControladorException{

		Usuario usuarioFuncionalidadeIniciada = null;
		if(idFuncionalidadeIniciada != null){
			FiltroFuncionalidadeIniciada filtroFuncionalidade = new FiltroFuncionalidadeIniciada();
			filtroFuncionalidade.adicionarCaminhoParaCarregamentoEntidade("processoIniciado");
			filtroFuncionalidade.adicionarCaminhoParaCarregamentoEntidade("processoIniciado.usuario");
			filtroFuncionalidade.adicionarParametro(new ParametroSimples(FiltroFuncionalidadeIniciada.ID, idFuncionalidadeIniciada));
			Collection colecaoFuncionalidade = getControladorUtil().pesquisar(filtroFuncionalidade, FuncionalidadeIniciada.class.getName());
			FuncionalidadeIniciada funcionalidade = (FuncionalidadeIniciada) Util.retonarObjetoDeColecao(colecaoFuncionalidade);

			usuarioFuncionalidadeIniciada = funcionalidade.getProcessoIniciado().getUsuario();
		}
		return usuarioFuncionalidadeIniciada;
	}

	/**
	 * Encerra a Unidade de Processamento associada a um processamento batch
	 * 
	 * @author Rodrigo Silveira
	 * @date 24/08/2006
	 * @param excecao
	 * @param idUnidadeIniciada
	 * @param executouComErro
	 */

	public void encerrarUnidadeProcessamentoBatch(Throwable excecao, int idUnidadeIniciada, boolean executouComErro)
					throws ControladorException{

		if(idUnidadeIniciada != 0){

			FiltroUnidadeIniciada filtroUnidadeIniciada = new FiltroUnidadeIniciada();
			filtroUnidadeIniciada.adicionarParametro(new ParametroSimples(FiltroFuncionalidadeIniciada.ID, idUnidadeIniciada));

			UnidadeIniciada unidadeIniciada = (UnidadeIniciada) Util.retonarObjetoDeColecao(getControladorUtil().pesquisar(
							filtroUnidadeIniciada, UnidadeIniciada.class.getName()));

			unidadeIniciada.setDataHoraTermino(new Date());

			// verifica se a unidade encerrou com sucesso ou se nao foi executada por causa de uma
			// continuação no processo
			if((!executouComErro)
							|| (excecao != null && excecao.getMessage() != null && excecao.getMessage().equals("Unidade já executada"))){

				UnidadeSituacao unidadeSituacao = new UnidadeSituacao();
				unidadeSituacao.setId(UnidadeSituacao.CONCLUIDA);
				unidadeIniciada.setUnidadeSituacao(unidadeSituacao);

			}else{

				UnidadeSituacao unidadeSituacao = new UnidadeSituacao();
				unidadeSituacao.setId(UnidadeSituacao.CONCLUIDA_COM_ERRO);
				unidadeIniciada.setUnidadeSituacao(unidadeSituacao);

				// inserirLogExcecaoFuncionalidadeIniciada(unidadeIniciada, excecao);
			}

			getControladorUtil().atualizar(unidadeIniciada);

		}else{
			LOGGER.info("ATENCAO --- UNIDADE INICIADA POSSUI ID : !" + idUnidadeIniciada + "!");
		}
	}

	private void inserirLogExcecaoFuncionalidadeIniciada(UnidadeIniciada unidadeIniciada, Throwable excecao){

		// Verificar se a unidade já possui o registro do log da exceção
		// Só o primeiro log deverá ser gravado

		try{
			repositorioBatch.inserirLogExcecaoFuncionalidadeIniciada(unidadeIniciada, excecao);
		}catch(ErroRepositorioException e){
			e.printStackTrace();
		}

	}

	/**
	 * Verifica se o processo está em execução
	 * 
	 * @author Ana Maria
	 * @date 18/12/2008
	 */
	public boolean verificarProcessoEmExecucao(Integer idProcesso) throws ControladorException{

		try{
			return repositorioBatch.verificarProcessoEmExecucao(idProcesso);
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}
	}

	/**
	 * Inicia um processo relatico a uma funcionalidade.
	 * 
	 * @author wpereira
	 * @date 22/07/2010
	 * @param arquivo
	 * @param usuario
	 * @throws ControladorException
	 */
	public void iniciarProcesso(Integer idFuncionalidade, Object[] parametros) throws ControladorException{

		Date agora = new Date();

		switch(idFuncionalidade.intValue()){

			case Funcionalidade.BAIXA_ORDEM_SERVICO_COBRANCA:

				Usuario usuario = (Usuario) parametros[0];
				String arquivo = (String) parametros[1];

				Processo processo = new Processo();
				processo.setId(Processo.GERAR_BAIXA_ORDENS_SERVICO_COBRANCA);

				ProcessoSituacao processoSituacao = new ProcessoSituacao();
				processoSituacao.setId(ProcessoSituacao.EM_ESPERA);

				ProcessoIniciado processoIniciado = new ProcessoIniciado();
				processoIniciado.setProcessoSituacao(processoSituacao);
				processoIniciado.setProcesso(processo);
				processoIniciado.setDataHoraAgendamento(agora);
				// processoIniciado.setDataHoraInicio(agora);
				processoIniciado.setDataHoraComando(agora);
				processoIniciado.setUsuario(usuario);

				this.getControladorUtil().inserir(processoIniciado);

				FiltroProcessoFuncionalidade filtroProcessoFuncionalidade = new FiltroProcessoFuncionalidade();
				filtroProcessoFuncionalidade.adicionarParametro(new ParametroSimples(FiltroProcessoFuncionalidade.ID_PROCESSO,
								processoIniciado.getProcesso().getId()));
				filtroProcessoFuncionalidade.adicionarParametro(new ParametroSimples(FiltroProcessoFuncionalidade.INDICADOR_USO,
								ConstantesSistema.INDICADOR_USO_ATIVO));

				ProcessoFuncionalidade processoFuncionalidade = (ProcessoFuncionalidade) this.getControladorUtil().pesquisar(
								filtroProcessoFuncionalidade, ProcessoFuncionalidade.class.getName()).iterator().next();

				FuncionalidadeSituacao funcionalidadeSituacao = new FuncionalidadeSituacao();
				funcionalidadeSituacao.setId(FuncionalidadeSituacao.EM_ESPERA);

				FuncionalidadeIniciada funcionalidadeIniciada = new FuncionalidadeIniciada();
				funcionalidadeIniciada.setFuncionalidadeSituacao(funcionalidadeSituacao);
				funcionalidadeIniciada.setProcessoIniciado(processoIniciado);
				funcionalidadeIniciada.setProcessoFuncionalidade(processoFuncionalidade);
				funcionalidadeIniciada.setId((Integer) this.getControladorUtil().inserir(funcionalidadeIniciada));

				TarefaBatchEfetuarBaixaOrdensServicoCobranca tarefaBatch = new TarefaBatchEfetuarBaixaOrdensServicoCobranca(usuario,
								funcionalidadeIniciada.getId());
				tarefaBatch.addParametro("usuario", usuario);
				tarefaBatch.addParametro("arquivo", arquivo);

				try{
					funcionalidadeIniciada.setTarefaBatch(IoUtil.transformarObjetoParaBytes(tarefaBatch));
				}catch(IOException e){
					sessionContext.setRollbackOnly();
					throw new ControladorException("erro.sistema", e);
				}

				this.getControladorUtil().atualizar(funcionalidadeIniciada);

				break;
			case Funcionalidade.INFORMAR_DADOS_LEITURA_E_ANORMALIDADE:
				Integer idFaturamentoGrupo = (Integer) parametros[0];
				File arquivoLeitura = (File) parametros[1];
				Usuario usuarioLeitura = (Usuario) parametros[2];

				Processo processoLeitura = new Processo();
				processoLeitura.setId(Processo.PROCESSAR_ARQUIVO_DADOS_LEITURA_ANORMALIDADE);

				ProcessoSituacao processoSituacaoLeitura = new ProcessoSituacao();
				processoSituacaoLeitura.setId(ProcessoSituacao.EM_ESPERA);

				ProcessoIniciado processoIniciadoLeitura = new ProcessoIniciado();
				processoIniciadoLeitura.setProcessoSituacao(processoSituacaoLeitura);
				processoIniciadoLeitura.setProcesso(processoLeitura);
				processoIniciadoLeitura.setDataHoraAgendamento(agora);
				// processoIniciado.setDataHoraInicio(agora);
				processoIniciadoLeitura.setDataHoraComando(agora);
				processoIniciadoLeitura.setUsuario(usuarioLeitura);
				processoIniciadoLeitura.setCodigoGrupoProcesso(idFaturamentoGrupo);

				FiltroFaturamentoGrupo filtroFaturamentoGrupo = new FiltroFaturamentoGrupo();
				filtroFaturamentoGrupo.adicionarParametro(new ParametroSimples(FiltroFaturamentoGrupo.ID, idFaturamentoGrupo));

				FaturamentoGrupo grupo = (FaturamentoGrupo) getControladorUtil()
								.pesquisar(filtroFaturamentoGrupo, FaturamentoGrupo.class.getName()).iterator().next();

				ProcessoIniciadoDadoComplementarHelper helper = new ProcessoIniciadoDadoComplementarHelper();
				helper.adcionarDadoComplementar(DadoComplementarEnumerator.GRUPO_DESCRICAO, grupo.getDescricao());
				helper.adcionarDadoComplementar(DadoComplementarEnumerator.ANO_MES_REFERENCIA,
								Util.formatarAnoMesParaMesAno(grupo.getAnoMesReferencia()));
				helper.adcionarDadoComplementar(DadoComplementarEnumerator.ARQUIVO_LEITURA_ANORMALIDADE_PATH, arquivoLeitura.getPath());
				helper.adcionarDadoComplementar(DadoComplementarEnumerator.USUARIO_ID, usuarioLeitura.getId());
				helper.adcionarDadoComplementar(DadoComplementarEnumerator.USUARIO_NOME, usuarioLeitura.getNomeUsuario());
				String descricaoDadosComplementares = helper.getStringFormatoPesistencia();
				processoIniciadoLeitura.setDescricaoDadosComplementares(descricaoDadosComplementares);

				Object codigoProcessoIniciadoGerado = this.getControladorUtil().inserir(processoIniciadoLeitura);
				LOGGER.info("INCLUINDO PROCESSO INICIADO[" + codigoProcessoIniciadoGerado + "] - DADOS COMPLEMENTARES ["
								+ descricaoDadosComplementares + "]");
				
				FiltroProcessoFuncionalidade filtroProcessoFuncionalidadeLeitura = new FiltroProcessoFuncionalidade();
				filtroProcessoFuncionalidadeLeitura.adicionarParametro(new ParametroSimples(FiltroProcessoFuncionalidade.ID_PROCESSO,
								processoIniciadoLeitura.getProcesso().getId()));
				filtroProcessoFuncionalidadeLeitura.adicionarParametro(new ParametroSimples(FiltroProcessoFuncionalidade.INDICADOR_USO,
								ConstantesSistema.INDICADOR_USO_ATIVO));

				ProcessoFuncionalidade processoFuncionalidadeLeitura = (ProcessoFuncionalidade) this.getControladorUtil().pesquisar(
								filtroProcessoFuncionalidadeLeitura, ProcessoFuncionalidade.class.getName()).iterator().next();

				FuncionalidadeSituacao funcionalidadeSituacaoLeitura = new FuncionalidadeSituacao();
				funcionalidadeSituacaoLeitura.setId(FuncionalidadeSituacao.EM_ESPERA);

				FuncionalidadeIniciada funcionalidadeIniciadaLeitura = new FuncionalidadeIniciada();
				funcionalidadeIniciadaLeitura.setFuncionalidadeSituacao(funcionalidadeSituacaoLeitura);
				funcionalidadeIniciadaLeitura.setProcessoIniciado(processoIniciadoLeitura);
				funcionalidadeIniciadaLeitura.setProcessoFuncionalidade(processoFuncionalidadeLeitura);
				funcionalidadeIniciadaLeitura.setId((Integer) this.getControladorUtil().inserir(funcionalidadeIniciadaLeitura));

				TarefaBatchProcessarArquivoDadosLeituraAnormalidade tarefaBatchLeitura = new TarefaBatchProcessarArquivoDadosLeituraAnormalidade(
								usuarioLeitura, funcionalidadeIniciadaLeitura.getId());
				tarefaBatchLeitura.addParametro("idFaturamentoGrupo", idFaturamentoGrupo);
				tarefaBatchLeitura.addParametro("arquivoLeitura", arquivoLeitura);
				tarefaBatchLeitura.addParametro("usuario", usuarioLeitura);
				tarefaBatchLeitura.addParametro("idFuncionalidadeIniciada", funcionalidadeIniciadaLeitura.getId());

				try{
					funcionalidadeIniciadaLeitura.setTarefaBatch(IoUtil.transformarObjetoParaBytes(tarefaBatchLeitura));
				}catch(IOException e){
					sessionContext.setRollbackOnly();
					throw new ControladorException("erro.sistema", e);
				}

				this.getControladorUtil().atualizar(funcionalidadeIniciadaLeitura);
				break;
			case Funcionalidade.BATCH_ENCERRAR_FATURAMENTO_GERAR_RESUMO_LIGACOES_ECONOMIAS:

				Integer referenciaFaturamento = (Integer) parametros[0];
				Usuario usuarioResumo = (Usuario) parametros[1];

				Processo processoResumo = new Processo();
				processoResumo.setId(Processo.BATCH_ENCERRAR_FATURAMENTO_GERAR_RESUMO_LIGACOES_ECONOMIAS);

				ProcessoSituacao processoSituacaoResumo = new ProcessoSituacao();
				processoSituacaoResumo.setId(ProcessoSituacao.EM_ESPERA);

				ProcessoIniciado processoIniciadoResumo = new ProcessoIniciado();
				processoIniciadoResumo.setProcessoSituacao(processoSituacaoResumo);
				processoIniciadoResumo.setProcesso(processoResumo);
				processoIniciadoResumo.setDataHoraAgendamento(agora);
				// processoIniciado.setDataHoraInicio(agora);
				processoIniciadoResumo.setDataHoraComando(agora);
				processoIniciadoResumo.setUsuario(usuarioResumo);

				this.getControladorUtil().inserir(processoIniciadoResumo);

				FiltroProcessoFuncionalidade filtroProcessoFuncionalidadeResumo = new FiltroProcessoFuncionalidade();
				filtroProcessoFuncionalidadeResumo.adicionarParametro(new ParametroSimples(FiltroProcessoFuncionalidade.ID_PROCESSO,
								processoIniciadoResumo.getProcesso().getId()));
				filtroProcessoFuncionalidadeResumo.adicionarParametro(new ParametroSimples(FiltroProcessoFuncionalidade.INDICADOR_USO,
								ConstantesSistema.INDICADOR_USO_ATIVO));

				ProcessoFuncionalidade processoFuncionalidadeResumo = (ProcessoFuncionalidade) this.getControladorUtil().pesquisar(
								filtroProcessoFuncionalidadeResumo, ProcessoFuncionalidade.class.getName()).iterator().next();

				FuncionalidadeSituacao funcionalidadeSituacaoResumo = new FuncionalidadeSituacao();
				funcionalidadeSituacaoResumo.setId(FuncionalidadeSituacao.EM_ESPERA);

				FuncionalidadeIniciada funcionalidadeIniciadaResumo = new FuncionalidadeIniciada();
				funcionalidadeIniciadaResumo.setFuncionalidadeSituacao(funcionalidadeSituacaoResumo);
				funcionalidadeIniciadaResumo.setProcessoIniciado(processoIniciadoResumo);
				funcionalidadeIniciadaResumo.setProcessoFuncionalidade(processoFuncionalidadeResumo);
				funcionalidadeIniciadaResumo.setId((Integer) this.getControladorUtil().inserir(funcionalidadeIniciadaResumo));

				TarefaBatchEncerrarFaturamentoGerarResumoLigacoesEconomias tarefaBatchResumo = new TarefaBatchEncerrarFaturamentoGerarResumoLigacoesEconomias(
								usuarioResumo, funcionalidadeIniciadaResumo.getId());

				List<Integer> colecaoRotas = new ArrayList<Integer>();
				try{
					colecaoRotas = repositorioMicromedicao
									.pesquisarRotasComAlteracaoNasLigacoesEconomiasPorReferencia(referenciaFaturamento);
				}catch(ErroRepositorioException ex){
					throw new ControladorException("erro.sistema", ex);
				}

				tarefaBatchResumo.addParametro("referenciaFaturamento", referenciaFaturamento);
				tarefaBatchResumo.addParametro(ConstantesSistema.COLECAO_UNIDADES_PROCESSAMENTO_BATCH, colecaoRotas);

				try{
					funcionalidadeIniciadaResumo.setTarefaBatch(IoUtil.transformarObjetoParaBytes(tarefaBatchResumo));
				}catch(IOException e){
					sessionContext.setRollbackOnly();
					throw new ControladorException("erro.sistema", e);
				}

				this.getControladorUtil().atualizar(funcionalidadeIniciadaResumo);

				break;
			case Funcionalidade.GERAR_RESUMO_ANORMALIDADES:

				Integer pReferenciaFaturamento = (Integer) parametros[0];
				Usuario pUsuario = (Usuario) parametros[1];

				Processo proc = new Processo();
				proc.setId(Processo.GERAR_RESUMO_ANORMALIDADES);

				ProcessoSituacao procSituacao = new ProcessoSituacao();
				procSituacao.setId(ProcessoSituacao.EM_ESPERA);

				ProcessoIniciado procIniciado = new ProcessoIniciado();
				procIniciado.setProcessoSituacao(procSituacao);
				procIniciado.setProcesso(proc);
				procIniciado.setDataHoraAgendamento(agora);
				// processoIniciado.setDataHoraInicio(agora);
				procIniciado.setDataHoraComando(agora);
				procIniciado.setUsuario(pUsuario);

				this.getControladorUtil().inserir(procIniciado);

				FiltroProcessoFuncionalidade filtroProcFuncionalidade = new FiltroProcessoFuncionalidade();
				filtroProcFuncionalidade.adicionarParametro(new ParametroSimples(FiltroProcessoFuncionalidade.ID_PROCESSO,
								procIniciado.getProcesso().getId()));
				filtroProcFuncionalidade.adicionarParametro(new ParametroSimples(FiltroProcessoFuncionalidade.INDICADOR_USO,
								ConstantesSistema.INDICADOR_USO_ATIVO));

				filtroProcFuncionalidade.setCampoOrderBy(FiltroProcessoFuncionalidade.SEQUENCIAL_EXECUCAO);

				Collection colProcFuncionalidade = this.getControladorUtil().pesquisar(
								filtroProcFuncionalidade, ProcessoFuncionalidade.class.getName());

				FuncionalidadeSituacao funcSituacaoResumo = new FuncionalidadeSituacao();
				funcSituacaoResumo.setId(FuncionalidadeSituacao.EM_ESPERA);

				Iterator itColProcFuncionalidade = colProcFuncionalidade.iterator();

				while(itColProcFuncionalidade.hasNext()){
					ProcessoFuncionalidade procFuncionalidade = (ProcessoFuncionalidade) itColProcFuncionalidade.next();

					FuncionalidadeIniciada funcIniciada = new FuncionalidadeIniciada();
					funcIniciada.setFuncionalidadeSituacao(funcSituacaoResumo);
					funcIniciada.setProcessoIniciado(procIniciado);
					funcIniciada.setProcessoFuncionalidade(procFuncionalidade);
					funcIniciada.setId((Integer) this.getControladorUtil().inserir(funcIniciada));

					if(funcIniciada.getProcessoFuncionalidade().getFuncionalidade().getId()
									.equals(Funcionalidade.GERAR_RESUMO_ANORMALIDADES)){
						TarefaBatchGerarResumoAnormalidades tarefaBatchGerarResumoAnormalidades = new TarefaBatchGerarResumoAnormalidades(
										pUsuario, funcIniciada.getId());

						tarefaBatchGerarResumoAnormalidades.addParametro("pReferenciaFaturamento", pReferenciaFaturamento);

						try{
							funcIniciada.setTarefaBatch(IoUtil.transformarObjetoParaBytes(tarefaBatchGerarResumoAnormalidades));
						}catch(IOException e){
							sessionContext.setRollbackOnly();
							throw new ControladorException("erro.sistema", e);
						}

						this.getControladorUtil().atualizar(funcIniciada);

					}else if(funcIniciada.getProcessoFuncionalidade().getFuncionalidade().getId()
									.equals(Funcionalidade.GERAR_RESUMO_ANORMALIDADE_CONSUMO)){

						TarefaBatchGerarResumoAnormalidadeConsumo tarefaBatchGerarResumoAnormalidadeConsumo = new TarefaBatchGerarResumoAnormalidadeConsumo(
										pUsuario, funcIniciada.getId());

						tarefaBatchGerarResumoAnormalidadeConsumo.addParametro("pReferenciaFaturamento", pReferenciaFaturamento);

						try{
							funcIniciada.setTarefaBatch(IoUtil.transformarObjetoParaBytes(tarefaBatchGerarResumoAnormalidadeConsumo));
						}catch(IOException e){
							sessionContext.setRollbackOnly();
							throw new ControladorException("erro.sistema", e);
						}

						getControladorUtil().atualizar(funcIniciada);
					}

				}

				break;
			default:

				break;

		}
	}

	/**
	 * Encerra um processo relativo a uma funcionalidade
	 * 
	 * @author wpereira
	 * @date 22/07/2010
	 * @param idFuncionalidadeIniciada
	 * @param concluiuComErro
	 * @throws ControladorException
	 */
	public void encerrarProcesso(int idFuncionalidadeIniciada, boolean concluiuComErro) throws ControladorException{

		try{

			if(concluiuComErro){

				repositorioBatch.encerrarFuncionalidadeIniciadaRelatorio(idFuncionalidadeIniciada,
								FuncionalidadeSituacao.CONCLUIDA_COM_ERRO);
				repositorioBatch.encerrarProcessoIniciadoRelatorio(idFuncionalidadeIniciada, ProcessoSituacao.CONCLUIDO_COM_ERRO);

			}else{

				repositorioBatch.encerrarFuncionalidadeIniciadaRelatorio(idFuncionalidadeIniciada, FuncionalidadeSituacao.CONCLUIDA);
				repositorioBatch.encerrarProcessoIniciadoRelatorio(idFuncionalidadeIniciada, ProcessoSituacao.CONCLUIDO);

			}

		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

	}

	/**
	 * Encerra a Unidade de Processamento associada a um processamento batch
	 * 
	 * @author Rodrigo Silveira
	 * @date 24/08/2006
	 * @param idUnidadeIniciada
	 * @param executouComErro
	 */
	public void encerrarUnidadeProcessamentoBatchPorFuncionalidadeIniciada(int idUnidadeIniciada, boolean executouComErro,
					int idFuncionalidadeIniciada, ParametrosHelper helper) throws ControladorException{

		if(idUnidadeIniciada != 0){

			FiltroUnidadeIniciada filtroUnidadeIniciada = new FiltroUnidadeIniciada();
			filtroUnidadeIniciada.adicionarParametro(new ParametroSimples(FiltroFuncionalidadeIniciada.ID, idUnidadeIniciada));

			UnidadeIniciada unidadeIniciada = (UnidadeIniciada) Util.retonarObjetoDeColecao(getControladorUtil().pesquisar(
							filtroUnidadeIniciada, UnidadeIniciada.class.getName()));

			if(unidadeIniciada != null){
				unidadeIniciada.setDataHoraTermino(new Date());

				if(!executouComErro){
					UnidadeSituacao unidadeSituacao = new UnidadeSituacao();
					unidadeSituacao.setId(UnidadeSituacao.CONCLUIDA);
					unidadeIniciada.setUnidadeSituacao(unidadeSituacao);

				}else{
					UnidadeSituacao unidadeSituacao = new UnidadeSituacao();
					unidadeSituacao.setId(UnidadeSituacao.CONCLUIDA_COM_ERRO);
					unidadeIniciada.setUnidadeSituacao(unidadeSituacao);
				}

				getControladorUtil().atualizar(unidadeIniciada);
			}

		}else{
			LOGGER.info("ATENCAO --- UNIDADE INICIADA POSSUI ID : !" + idUnidadeIniciada + "!");
		}

	}

	/**
	 * [UC0111] Iniciar Processo
	 * [FS0011] – Verificar restrição de execução simultânea de processos
	 * 
	 * @author Hugo Lima
	 * @date 28/02/2012
	 * @throws ControladorException
	 */
	public boolean isProcessoComRestricaoExecucaoSimultanea(Integer idProcesso) throws ControladorException{

		boolean retorno = false;

		Collection retornoConsulta = null;

		try{
			retornoConsulta = this.repositorioBatch.pesquisarRestricaoExecucaoSimultaneaProcessos(idProcesso);

			// Caso exista algum processo que restrinja a execução do processo a ser iniciado
			// retorna true
			if(!Util.isVazioOrNulo(retornoConsulta)){
				retorno = true;
			}

		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

		return retorno;
	}

	/**
	 * Insere um processo iniciado no sistema a partir de uma funcionalidade do Online e suas
	 * funcionalidades iniciadas
	 * 
	 * @author Anderson Italo
	 * @date 11/03/2012
	 * @throws ControladorException
	 */
	public Integer inserirProcessoIniciadoOnline(ProcessoIniciado processoIniciado, List<Object> colecaoParametros)
					throws ControladorException{

		Date agora = new Date();
		Integer codigoProcessoIniciadoGerado = 0;

		try{

			// Todos os processo serão iniciados com a situação EM_ESPERA
			ProcessoSituacao processoSituacao = new ProcessoSituacao();
			processoSituacao.setId(ProcessoSituacao.EM_ESPERA);
			processoIniciado.setProcessoSituacao(processoSituacao);
			processoIniciado.setDataHoraComando(agora);

			codigoProcessoIniciadoGerado = (Integer) getControladorUtil().inserir(processoIniciado);

			// Este trecho pesquisa todos os processoFuncionalidades relacionados
			// com o processoIniciado a ser inserido
			FiltroProcessoFuncionalidade filtroProcessoFuncionalidade = new FiltroProcessoFuncionalidade();

			filtroProcessoFuncionalidade.adicionarParametro(new ParametroSimples(FiltroProcessoFuncionalidade.ID_PROCESSO, processoIniciado
							.getProcesso().getId()));
			filtroProcessoFuncionalidade.adicionarParametro(new ParametroSimples(FiltroProcessoFuncionalidade.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			filtroProcessoFuncionalidade.setCampoOrderBy(FiltroProcessoFuncionalidade.SEQUENCIAL_EXECUCAO);

			Collection colecaoProcessosFuncionalidade = getControladorUtil().pesquisar(filtroProcessoFuncionalidade,
							ProcessoFuncionalidade.class.getName());

			FuncionalidadeSituacao funcionalidadeSituacao = new FuncionalidadeSituacao();
			funcionalidadeSituacao.setId(FuncionalidadeSituacao.EM_ESPERA);

			Iterator iterator = colecaoProcessosFuncionalidade.iterator();

			while(iterator.hasNext()){

				ProcessoFuncionalidade processoFuncionalidade = (ProcessoFuncionalidade) iterator.next();

				FuncionalidadeIniciada funcionalidadeIniciada = new FuncionalidadeIniciada();
				funcionalidadeIniciada.setFuncionalidadeSituacao(funcionalidadeSituacao);
				funcionalidadeIniciada.setProcessoIniciado(processoIniciado);
				funcionalidadeIniciada.setProcessoFuncionalidade(processoFuncionalidade);

				// Inserir a funcionalidade iniciada
				funcionalidadeIniciada.setId((Integer) getControladorUtil().inserir(funcionalidadeIniciada));

				// Verifica qual funcionalidade(processo) foi iniciada
				switch(funcionalidadeIniciada.getProcessoFuncionalidade().getFuncionalidade().getId()){

					case Funcionalidade.REGISTRAR_MOVIMENTO_ARRECADADORES:

						TarefaBatchRegistrarMovimentoArrecadadores registrarMovimentoArrecadadoresBatch = new TarefaBatchRegistrarMovimentoArrecadadores(
										processoIniciado.getUsuario(), funcionalidadeIniciada.getId());

						registrarMovimentoArrecadadoresBatch.addParametro("linhasArquivo", colecaoParametros.get(0));
						registrarMovimentoArrecadadoresBatch.addParametro("arrecadador", colecaoParametros.get(1));
						registrarMovimentoArrecadadoresBatch.addParametro("idTipoMovimento", colecaoParametros.get(2));
						registrarMovimentoArrecadadoresBatch.addParametro("quantidadeRegistros", colecaoParametros.get(3));

						funcionalidadeIniciada.setTarefaBatch(IoUtil.transformarObjetoParaBytes(registrarMovimentoArrecadadoresBatch));

						getControladorUtil().atualizar(funcionalidadeIniciada);

						break;
					case Funcionalidade.GERAR_ENVIAR_RELATORIO_RESUMO_MOVIMENTO_ARRECADACAO:

						TarefaBatchGerarEnviarRelatorioResumoMovimentoArrecadacao gerarEnviarResumo = new TarefaBatchGerarEnviarRelatorioResumoMovimentoArrecadacao(
										processoIniciado.getUsuario(), funcionalidadeIniciada.getId());

						funcionalidadeIniciada.setTarefaBatch(IoUtil.transformarObjetoParaBytes(gerarEnviarResumo));

						getControladorUtil().atualizar(funcionalidadeIniciada);

						break;

					case Funcionalidade.CLASSIFICAR_LOTE_PAGAMENTOS_NAO_CLASSIFICADOS:

						TarefaBatchClassificarLotePagamentosNaoClassificados gerarPagamentosNaoClassificados = new TarefaBatchClassificarLotePagamentosNaoClassificados(
										processoIniciado.getUsuario(), funcionalidadeIniciada.getId());

						gerarPagamentosNaoClassificados.addParametro("classificarLotePagamentosNaoClassificadosHelper",
										colecaoParametros.get(0));
						funcionalidadeIniciada.setTarefaBatch(IoUtil.transformarObjetoParaBytes(gerarPagamentosNaoClassificados));

						getControladorUtil().atualizar(funcionalidadeIniciada);

						break;

					case Funcionalidade.GERAR_INTEGRACAO_SPED_PIS_COFINS: // [OC777360][UC3078]
						criarTarefaBatchGerarIntegracaoSpedPisCofins(processoIniciado, funcionalidadeIniciada);
						break;

					case Funcionalidade.EMITIR_DECLARACAO_ANUAL_QUITACAO_DEBITOS:
						TarefaBatchEmitirDeclaracaoAnualQuitacaoDebitos tarefaBatchEmitirDeclaracaoAnualQuitacaoDebitos = new TarefaBatchEmitirDeclaracaoAnualQuitacaoDebitos(
										processoIniciado.getUsuario(), funcionalidadeIniciada.getId());

						tarefaBatchEmitirDeclaracaoAnualQuitacaoDebitos.addParametro("idFaturamentoGrupo", colecaoParametros.get(0));
						tarefaBatchEmitirDeclaracaoAnualQuitacaoDebitos
										.addParametro("anoBaseDeclaracaoInformado", colecaoParametros.get(1));

						funcionalidadeIniciada.setTarefaBatch(IoUtil
										.transformarObjetoParaBytes(tarefaBatchEmitirDeclaracaoAnualQuitacaoDebitos));

						this.getControladorUtil().atualizar(funcionalidadeIniciada);

						break;

					case Funcionalidade.GERAR_DECLARACAO_ANUAL_QUITACAO_DEBITOS:
						TarefaBatchGerarDeclaracaoAnualQuitacaoDebitos tarefaBatchGerarDeclaracaoAnualQuitacaoDebitos = new TarefaBatchGerarDeclaracaoAnualQuitacaoDebitos(
										processoIniciado.getUsuario(), funcionalidadeIniciada.getId());

						tarefaBatchGerarDeclaracaoAnualQuitacaoDebitos.addParametro("faturamentoGrupo", colecaoParametros.get(0));

						funcionalidadeIniciada.setTarefaBatch(IoUtil
										.transformarObjetoParaBytes(tarefaBatchGerarDeclaracaoAnualQuitacaoDebitos));

						this.getControladorUtil().atualizar(funcionalidadeIniciada);

						break;

					case Funcionalidade.EFETIVAR_ATUALIZACAO_INSCRICAO_IMOVEL:
						TarefaBatchEfetivarAlteracaoInscricaoImovel efetivarAlteracaoInscricaoImovel = new TarefaBatchEfetivarAlteracaoInscricaoImovel(
										processoIniciado.getUsuario(), funcionalidadeIniciada.getId());

						funcionalidadeIniciada.setTarefaBatch(IoUtil.transformarObjetoParaBytes(efetivarAlteracaoInscricaoImovel));
						this.getControladorUtil().atualizar(funcionalidadeIniciada);

						break;

					case Funcionalidade.BATCH_RELATORIO_FATURAMENTO_LIGACOES_MEDICAO_INDIVIDUALIZADA_MDB:
						TarefaBatchRelatorioFaturamentoLigacoesMedicaoIndividualizada tarefaBatchRelatorioFaturamentoLigacoesMedicaoIndividualizada = new TarefaBatchRelatorioFaturamentoLigacoesMedicaoIndividualizada(
										processoIniciado.getUsuario(), funcionalidadeIniciada.getId());

						funcionalidadeIniciada.setTarefaBatch(IoUtil
										.transformarObjetoParaBytes(tarefaBatchRelatorioFaturamentoLigacoesMedicaoIndividualizada));

						this.getControladorUtil().atualizar(funcionalidadeIniciada);

						break;

					case Funcionalidade.BATCH_RELATORIO_ACOMP_MOV_ARRECADADORES_MDB:
						TarefaBatchGerarRelatorioAcompanhamentoMovimentoArrecadadores tarefaBatchGerarRelatorioAcompanhamentoMovimentoArrecadadores = new TarefaBatchGerarRelatorioAcompanhamentoMovimentoArrecadadores(
										processoIniciado.getUsuario(), funcionalidadeIniciada.getId());

						funcionalidadeIniciada.setTarefaBatch(IoUtil
										.transformarObjetoParaBytes(tarefaBatchGerarRelatorioAcompanhamentoMovimentoArrecadadores));

						this.getControladorUtil().atualizar(funcionalidadeIniciada);

						break;
					case Funcionalidade.COMANDAR_PRESCRICAO_DEBITOS_USUARIO:
						TarefaBatchComandarPrescricaoDebitos tarefaBatchComandarPrescricaoDebitosUsuario = new TarefaBatchComandarPrescricaoDebitos(
										processoIniciado.getUsuario(), funcionalidadeIniciada.getId());

						tarefaBatchComandarPrescricaoDebitosUsuario.addParametro("dadosImoveisMarcacaoDebitoPrescrito",
										colecaoParametros.get(0));

						funcionalidadeIniciada.setTarefaBatch(IoUtil
										.transformarObjetoParaBytes(tarefaBatchComandarPrescricaoDebitosUsuario));

						this.getControladorUtil().atualizar(funcionalidadeIniciada);

						break;

					default:
						break;
				}
			}

		}catch(Exception e){

			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

		return codigoProcessoIniciadoGerado;
	}

	/**
	 * Retorna o Id do Processo relacionado ao Relatório.
	 * Contempla a geração de relatórios Crystal Report como rotina batch
	 * Neste caso, o id do processo está encapsulado na entidade Relatorio. Esta informação
	 * é configurada no applicationContext.xml
	 * 
	 * @author lgalvao (Luciano Galvão)
	 * @date 17/05/2012
	 * @param tarefaRelatorio
	 * @return processoId
	 */
	private Integer obterProcessoId(TarefaRelatorio tarefaRelatorio){

		Integer processoId;
		br.com.procenge.geradorrelatorio.api.Relatorio relatorio = (br.com.procenge.geradorrelatorio.api.Relatorio) tarefaRelatorio
						.getParametro("relatorio");
		if((relatorio != null) && (relatorio.getProcessoId() != null) && Util.isNumero(relatorio.getProcessoId(), false, 0)){
			processoId = Integer.valueOf(relatorio.getProcessoId());
		}else{
			if(tarefaRelatorio.getProcessoId() == null){
				processoId = Integer.valueOf(GerenciadorExecucaoTarefaRelatorio.obterProcessoRelatorio(tarefaRelatorio.getNomeRelatorio()));
			}else{
				processoId = tarefaRelatorio.getProcessoId();
			}
		}
		return processoId;
	}

	/**
	 * Pesquisar Funcionalidade Iniciada filtrando pelo Id do Processo Vinculado
	 * 
	 * @author Hebert Falcão
	 * @date 30/08/2012
	 */
	public Collection<FuncionalidadeIniciada> pesquisarFuncionalidadeIniciadaPeloProcessoVinculado(Integer idProcessoIniciadoVinculado)
					throws ControladorException{
		try{
			return this.repositorioBatch.pesquisarFuncionalidadeIniciadaPeloProcessoVinculado(idProcessoIniciadoVinculado);
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}
	}

	/**
	 * [UC0111] - Iniciar Processo
	 * [SB0001] - Iniciar Processo de Faturamento Comandado
	 * 
	 * @author Anderson Italo
	 * @date 28/12/2013
	 */
	public int pesquisarTotalRegistrosFaturamentoSimulacaoComandoNaoRealizados() throws ControladorException{

		try{

			return this.repositorioBatch.pesquisarTotalRegistrosFaturamentoSimulacaoComandoNaoRealizados();
		}catch(ErroRepositorioException e){

			throw new ControladorException("erro.sistema", e);
		}
	}

	/**
	 * [UC0111] - Iniciar Processo
	 * [SB0001] - Iniciar Processo de Faturamento Comandado
	 * 
	 * @author Anderson Italo
	 * @date 28/12/2013
	 */
	public Collection<FaturamentoSimulacaoComando> pesquisarFaturamentoSimulacaoComandoNaoRealizados(Integer numeroPagina)
					throws ControladorException{

		try{

			return this.repositorioBatch.pesquisarFaturamentoSimulacaoComandoNaoRealizados(numeroPagina);
		}catch(ErroRepositorioException e){

			throw new ControladorException("erro.sistema", e);
		}
	}

}