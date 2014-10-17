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
 * Vitor Hora
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

package gcom.batch;

import gcom.faturamento.conta.Conta;
import gcom.faturamento.faturamentosimulacaocomando.FaturamentoSimulacaoComando;
import gcom.micromedicao.Rota;
import gcom.tarefa.TarefaBatch;
import gcom.util.ErroRepositorioException;
import gcom.util.HibernateUtil;
import gcom.util.IoUtil;
import gcom.util.Util;
import gcom.util.agendadortarefas.VerificadorIP;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.*;

import org.apache.log4j.Logger;
import org.hibernate.*;

/**
 * O repositório faz a comunicação com a base de dados através do hibernate. O
 * cliente usa o repositório como fonte de dados.
 * 
 * @author Rodrigo Silveira
 * @date 15/08/2006
 */
public class RepositorioBatchHBM
				implements IRepositorioBatch {
	
	private static final Logger LOGGER = Logger.getLogger(RepositorioBatchHBM.class);

	private static final String PREFIXO_VALORES_DADOS_COMPLEMENTARES = "V";

	private static IRepositorioBatch instancia;

	private RepositorioBatchHBM() {

	}

	/**
	 * Retorna o valor de instancia
	 * 
	 * @return O valor de instancia
	 */
	public static IRepositorioBatch getInstancia(){

		if(instancia == null){
			instancia = new RepositorioBatchHBM();
		}

		return instancia;
	}

	public Collection pesquisarRotasProcessamentoBatchFaturamentoComandado(Integer idFaturamentoAtividadeCronograma)
					throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "SELECT fatAtivCronRota, fatAtivCronRota.rota " + "FROM FaturamentoAtivCronRota fatAtivCronRota "
							+ "left join fatAtivCronRota.faturamentoAtividadeCronograma fatAtividadeCronograma "
							+ "left join fetch fatAtivCronRota.rota.faturamentoGrupo fatGrupo "
							+ "where fatAtividadeCronograma.id in (:ids)" + " ORDER BY fatAtivCronRota.rota.id ";

			retorno = session.createQuery(consulta).setInteger("ids", idFaturamentoAtividadeCronograma).list();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		return retorno;

	}

	/**
	 * Encerra os Processos Iniciados no sistema quando todas as funcionalidades
	 * do mesmo finalizarem a execução
	 * 
	 * @author Rodrigo Silveira
	 * @date 22/08/2006
	 */
	public Collection<ProcessoIniciado> pesquisarProcessosIniciadosProntosParaEncerramento() throws ErroRepositorioException{

		Collection<ProcessoIniciado> retorno = null;

		Session session = HibernateUtil.getSession();
		StringBuilder consulta = new StringBuilder();

		try{
			String ip = VerificadorIP.getInstancia().getIpAtivo();

			consulta.append("select processo1 from ProcessoIniciado processo1 ");
			consulta.append("inner join processo1.funcionalidadesIniciadas iniciada ");
			consulta.append("where iniciada.id is not null ");
			if(ip != null) consulta.append(" and processo1.ip = :ip ");
			consulta.append("and processo1.processoSituacao.id <> :situacaoProcessoConcluida ");
			consulta.append("and processo1.id not in ( ");
			consulta.append("select distinct processo.id from ProcessoIniciado processo ");
			consulta.append("inner join processo.funcionalidadesIniciadas iniciada ");
			consulta.append("inner join iniciada.funcionalidadeSituacao situacao ");
			consulta.append("where situacao.id <> :situacaoConcluida ");
			if(ip != null) consulta.append(" and processo.ip = :ip ");
			consulta.append(")");

			Query query = session.createQuery(consulta.toString()).setInteger("situacaoProcessoConcluida", ProcessoSituacao.CONCLUIDO)
							.setInteger("situacaoConcluida", FuncionalidadeSituacao.CONCLUIDA);

			if(ip != null) query.setString("ip", ip);

			retorno = query.list();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		return retorno;

	}

	/**
	 * Busca as Funcionalidades Iniciadas no sistema que falharam para marcar o
	 * Processo Iniciado como falho
	 * 
	 * @author Rodrigo Silveira
	 * @date 24/08/2006
	 */
	public Collection<ProcessoIniciado> pesquisarProcessosIniciadosExecucaoFalha() throws ErroRepositorioException{

		Collection<ProcessoIniciado> retorno = null;

		Session session = HibernateUtil.getSession();
		StringBuilder consulta = new StringBuilder();

		try{
			String ip = VerificadorIP.getInstancia().getIpAtivo();

			consulta.append("select processo1 from ProcessoIniciado processo1 ");
			consulta.append("inner join processo1.funcionalidadesIniciadas iniciada ");
			consulta.append("inner join iniciada.funcionalidadeSituacao situacao ");
			consulta.append("where processo1.processoSituacao <> :processoIniciadoConcluidoComErro ");
			consulta.append("and situacao = :situacaoConcluidaComErro ");
			if(ip != null) consulta.append(" and processo1.ip = :ip ");
			consulta.append("and iniciada.id is not null ");

			Query query = session.createQuery(consulta.toString()).setInteger("situacaoConcluidaComErro",
							FuncionalidadeSituacao.CONCLUIDA_COM_ERRO).setInteger("processoIniciadoConcluidoComErro",
							ProcessoSituacao.CONCLUIDO_COM_ERRO);

			if(ip != null) query.setString("ip", ip);

			retorno = query.list();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		return retorno;

	}

	/**
	 * Encerra as Funcionalidades Iniciadas no sistema quando todas as unidades
	 * de processamento do mesmo finalizarem a execução
	 * 
	 * @author Rodrigo Silveira
	 * @date 22/08/2006
	 */
	public Collection<FuncionalidadeIniciada> pesquisarFuncionaldadesIniciadasProntasParaEncerramento() throws ErroRepositorioException{

		Collection<FuncionalidadeIniciada> retorno = null;

		Session session = HibernateUtil.getSession();
		StringBuilder consulta = new StringBuilder();

		try{
			String ip = VerificadorIP.getInstancia().getIpAtivo();

			consulta.append("select func ");
			consulta.append("from FuncionalidadeIniciada func ");
			// consulta.append("inner join func.unidadesIniciadas iniciada ");

			if(ip != null){
				consulta.append("inner join func.processoIniciado procInic ");
			}

			consulta.append("inner join fetch func.processoFuncionalidade procFunc ");
			consulta.append("inner join fetch procFunc.funcionalidade ");
			consulta.append("where exists (select 1 ");
			consulta.append("              from UnidadeIniciada undi ");
			consulta.append("              where undi.funcionalidadeIniciada.id = func.id) ");
			// iniciada.id is not null
			consulta.append("  and func.funcionalidadeSituacao.id <> :situacaoFuncionalidadeConcluida ");
			consulta.append("  and func.id not in (select distinct func.id ");
			consulta.append("                      from FuncionalidadeIniciada func ");
			consulta.append("                      inner join func.unidadesIniciadas iniciada ");
			consulta.append("                      inner join iniciada.unidadeSituacao situacao ");

			if(ip != null){
				consulta.append("                  inner join func.processoIniciado procInic ");
			}

			consulta.append("                      where situacao.id <> :situacaoConcluida ");

			if(ip != null){
				consulta.append("                    and procInic.ip = :ip ");
			}

			consulta.append("                     )");

			if(ip != null){
				consulta.append("  and procInic.ip = :ip ");
			}

			Query query = session.createQuery(consulta.toString()).setInteger("situacaoConcluida", UnidadeSituacao.CONCLUIDA).setInteger(
							"situacaoFuncionalidadeConcluida", FuncionalidadeSituacao.CONCLUIDA);

			if(ip != null) query.setString("ip", ip);

			retorno = query.list();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		return retorno;

	}

	/**
	 * Busca as Unidades Iniciadas no sistema que falharam para marcar o
	 * Funcionalidade Iniciada como falha
	 * 
	 * @author Rodrigo Silveira
	 * @date 24/08/2006
	 */
	public Collection<FuncionalidadeIniciada> pesquisarFuncionaldadesIniciadasExecucaoFalha() throws ErroRepositorioException{

		Collection<FuncionalidadeIniciada> retorno = null;

		Session session = HibernateUtil.getSession();
		StringBuilder consulta = new StringBuilder();

		try{
			String ip = VerificadorIP.getInstancia().getIpAtivo();

			consulta.append("select func1 from FuncionalidadeIniciada func1 ");
			consulta.append("inner join func1.unidadesIniciadas iniciada1 ");
			if(ip != null) consulta.append("inner join func1.processoIniciado procInic ");
			consulta.append("inner join iniciada1.unidadeSituacao situacao ");
			consulta.append("where func1.funcionalidadeSituacao <> :funcionalidadeSituacaoConcluidaComErro and ");
			consulta.append("iniciada1 is not null and ");
			consulta.append("situacao = :situacaoConcluidaComErro ");
			if(ip != null) consulta.append(" and procInic.ip = :ip ");

			Query query = session.createQuery(consulta.toString()).setInteger("situacaoConcluidaComErro",
							UnidadeSituacao.CONCLUIDA_COM_ERRO).setInteger("funcionalidadeSituacaoConcluidaComErro",
							FuncionalidadeSituacao.CONCLUIDA_COM_ERRO);

			if(ip != null) query.setString("ip", ip);

			retorno = query.list();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		return retorno;

	}

	/**
	 * Busca as Funcionalidades Iniciadas no sistema que estão prontas para
	 * execução
	 * 
	 * @author Rodrigo Silveira
	 * @date 29/08/2006
	 */
	public Collection<Object[]> pesquisarFuncionaldadesIniciadasProntasExecucao(Collection<Integer> processosId)
					throws ErroRepositorioException{

		Collection<Object[]> retorno = null;

		Session session = HibernateUtil.getSession();
		StringBuilder consulta = new StringBuilder();

		try{
			String ip = VerificadorIP.getInstancia().getIpAtivo();

			consulta.append("select func, func.processoFuncionalidade.sequencialExecucao ");
			consulta.append("from FuncionalidadeIniciada func ");
			consulta.append("inner join fetch func.processoIniciado procIniciado ");
			consulta.append("inner join procIniciado.processoSituacao procSituacao ");
			consulta.append("left join procIniciado.processoIniciadoPrecedente procPrecedente ");
			consulta.append("left join procPrecedente.processoSituacao procPrecedenteSituacao ");
			consulta.append("left join func.unidadesIniciadas unidIniciada ");
			consulta.append("where unidIniciada is null and func.funcionalidadeSituacao <> :funcionalidadeEmProcessamento ");
			consulta.append("and (procSituacao.id = :emEspera or procSituacao.id = :processoEmProcessamento) ");
			consulta.append("and (procPrecedente is null or procPrecedenteSituacao.id = :situacaoProcessoConcluido) ");
			consulta.append("and (procIniciado.dataHoraAgendamento is null or procIniciado.dataHoraAgendamento <= current_timestamp()) ");
			consulta.append("and (procIniciado.id in (:processosProntos)) ");

			if(ip != null) consulta.append(" and procIniciado.ip = :ip ");
			consulta.append("order by func.processoFuncionalidade.sequencialExecucao");

			Query query = session.createQuery(consulta.toString())//
							.setInteger("emEspera", ProcessoSituacao.EM_ESPERA)//
							.setInteger("processoEmProcessamento", ProcessoSituacao.EM_PROCESSAMENTO)//
							.setInteger("situacaoProcessoConcluido", ProcessoSituacao.CONCLUIDO)//
							.setInteger("funcionalidadeEmProcessamento", FuncionalidadeSituacao.EM_PROCESSAMENTO)//
							.setParameterList("processosProntos", processosId);

			if(ip != null) query.setString("ip", ip);

			retorno = query.list();
			if(!Util.isVazioOrNulo(retorno)){
				LOGGER.debug("QTD Funcionalidades candidatas a execucao: " + retorno.size());
			}
		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		return retorno;

	}

	/**
	 * Verifica se a Funcionalidade Iniciada no sistema que está na ordem
	 * correta de execução dentro do processoFuncionalidade, as funcionalidades
	 * só podem iniciar se estiverem na ordem correta do sequencial de execução
	 * 
	 * @author Rodrigo Silveira
	 * @date 19/09/2006
	 */
	public Integer pesquisarQuantidadeFuncionaldadesIniciadasForaOrdemExecucao(int idSequencialExecucao, int idProcessoIniciado)
					throws ErroRepositorioException{

		Integer retorno = null;

		Session session = HibernateUtil.getSession();
		StringBuilder consulta = new StringBuilder();

		try{
			String ip = VerificadorIP.getInstancia().getIpAtivo();

			consulta.append("select count(distinct func.id) from FuncionalidadeIniciada func ");
			consulta.append("inner join func.processoFuncionalidade procFunc ");
			if(ip != null) consulta.append("inner join func.processoIniciado procInic ");
			consulta.append("where func.processoIniciado = :idProcessoIniciado");
			consulta.append(" and func.funcionalidadeSituacao <> :situacaoFuncionalidadeConcluida ");
			consulta.append(" and procFunc.sequencialExecucao < :sequencialExecucao ");
			if(ip != null) consulta.append(" and procInic.ip = :ip ");

			Query query = session.createQuery(consulta.toString()).setInteger("sequencialExecucao", idSequencialExecucao).setInteger(
							"situacaoFuncionalidadeConcluida", FuncionalidadeSituacao.CONCLUIDA).setInteger("idProcessoIniciado",
							idProcessoIniciado);

			if(ip != null) query.setString("ip", ip);

			retorno = ((Number) query.uniqueResult()).intValue();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		return retorno;

	}

	/**
	 * Verifica se a FuncionalidadeIniciada foi concluida com erro para evitar a
	 * execução da UnidadeIniciada relacionada
	 * 
	 * @author Rodrigo Silveira
	 * @date 01/09/2006
	 */
	public int pesquisarFuncionaldadesIniciadasConcluidasErro(int idFuncionalidadeIniciada) throws ErroRepositorioException{

		Number retorno = 0;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{

			consulta = "select count(unid.id) from UnidadeIniciada unid "
							+ "where unid.funcionalidadeIniciada.id = :idFuncionalidadeIniciada and "
							+ "unid.unidadeSituacao.id = :unidadeSituacaoConcluidaComErro";

			retorno = (Number) session.createQuery(consulta).setInteger("idFuncionalidadeIniciada", idFuncionalidadeIniciada).setInteger(
							"unidadeSituacaoConcluidaComErro", UnidadeSituacao.CONCLUIDA_COM_ERRO).uniqueResult();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		return retorno.intValue();

	}

	/**
	 * Inseri uma coleção de objetos genéricos na base com um flush para cada 50
	 * registros inseridos.
	 * 
	 * @author Pedro Alexandre
	 * @date 11/09/2006
	 * @param colecaoObjetos
	 * @throws ErroRepositorioException
	 */
	public void inserirColecaoObjetoParaBatch(Collection<Object> colecaoObjetos) throws ErroRepositorioException{

		// obtém uma instância com o hibernate
		Session session = HibernateUtil.getSession();

		try{
			Iterator iteratorObjetos = colecaoObjetos.iterator();

			int i = 1;
			while(iteratorObjetos.hasNext()){
				Object objetoParaInserir = iteratorObjetos.next();

				if(objetoParaInserir instanceof Conta){
					Conta conta = (Conta) objetoParaInserir;
					System.out.println("INSERINDO: " + conta.getId());
				}



				session.save(objetoParaInserir);

				/*
				 * if (i % 50 == 0) {
				 * session.flush();
				 * session.clear();
				 * }
				 * i++;
				 */
			}
			session.flush();
			session.clear();
		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			e.printStackTrace();
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão com o hibernate
			HibernateUtil.closeSession(session);
		}
	}

	/**
	 * Inseri uma coleção de objetos genéricos na base com um flush para cada 50
	 * registros inseridos.
	 * 
	 * @author Pedro Alexandre
	 * @date 11/09/2006
	 * @param colecaoObjetos
	 * @throws ErroRepositorioException
	 */
	public void inserirColecaoObjetoParaBatchGerencial(Collection<Object> colecaoObjetos) throws ErroRepositorioException{

		// obtém uma instância com o hibernate
		StatelessSession session = HibernateUtil.getStatelessSessionGerencial();

		try{
			Iterator iteratorObjetos = colecaoObjetos.iterator();

			int i = 1;
			while(iteratorObjetos.hasNext()){
				Object objetoParaInserir = iteratorObjetos.next();

				System.out.println("INSERINDO: " + i + "-" + objetoParaInserir.getClass().getName());

				session.insert(objetoParaInserir);
				/*
				 * if (i % 50 == 0) {
				 * session.flush();
				 * session.clear();
				 * }
				 * i++;
				 */
			}
			// session.flush();
			// session.clear();
		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão com o hibernate
			HibernateUtil.closeSession(session);
		}
	}

	/**
	 * Atualiza uma coleção de objetos genéricos na base com um flush para cada
	 * 50 registros inseridos.
	 * 
	 * @author Leonardo Vieira
	 * @date 12/10/2006
	 * @param colecaoObjetos
	 * @throws ErroRepositorioException
	 */
	public void atualizarColecaoObjetoParaBatch(Collection<Object> colecaoObjetos) throws ErroRepositorioException{

		// obtém uma instância com o hibernate
		Session session = HibernateUtil.getSession();

		try{
			Iterator iteratorObjetos = colecaoObjetos.iterator();
			int i = 1;
			while(iteratorObjetos.hasNext()){
				Object objetoParaAtualizar = iteratorObjetos.next();
				session.update(objetoParaAtualizar);
				if(i % 50 == 0){
					session.flush();
					session.clear();
				}
				i++;
			}
			session.flush();
			session.clear();
		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão com o hibernate
			HibernateUtil.closeSession(session);
		}
	}

	/**
	 * Inicia uma funcionalidade iniciada de um relatório
	 * 
	 * @author Rodrigo Silveira
	 * @date 26/09/2006
	 */
	public void iniciarFuncionalidadeIniciadaRelatorio(int idFuncionalidadeIniciada) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		String consulta;

		try{

			consulta = "update FuncionalidadeIniciada " + "set funcionalidadeSituacao.id = :emProcessamento, dataHoraInicio = :dataAtual  "
							+ "where id = :idFuncionalidadeIniciada";

			session.createQuery(consulta).setInteger("idFuncionalidadeIniciada", idFuncionalidadeIniciada).setInteger("emProcessamento",
							FuncionalidadeSituacao.EM_PROCESSAMENTO).setTimestamp("dataAtual", new Date()).executeUpdate();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}
	}

	/**
	 * Inicia uma processo iniciado de um relatório
	 * 
	 * @author Rodrigo Silveira
	 * @date 26/09/2006
	 * @author eduardo henrique
	 * @date 20/11/2008
	 *       Alteração para informar a Data e Hora da dataInicio do Processo de Relatório
	 */
	public void iniciarProcessoIniciadoRelatorio(int idFuncionalidadeIniciada) throws ErroRepositorioException{

		StringBuilder consulta = new StringBuilder();
		Session session = HibernateUtil.getSession();
		String ip = VerificadorIP.getInstancia().getIpAtivo();

		try{

			consulta.append("update ProcessoIniciado ");
			consulta.append("set processoSituacao.id = :emProcessamento, dataHoraInicio = :dataAtual ");
			consulta.append("where id IN (select func.processoIniciado.id from FuncionalidadeIniciada func ");
			consulta.append("where func.id = :idFuncionalidadeIniciada ) ");
			if(ip != null) consulta.append("and ip = :ip ");

			Query query = session.createQuery(consulta.toString()).setInteger("idFuncionalidadeIniciada", idFuncionalidadeIniciada)
							.setInteger("emProcessamento", FuncionalidadeSituacao.EM_PROCESSAMENTO).setTimestamp("dataAtual", new Date());

			if(ip != null) query.setString("ip", ip);

			query.executeUpdate();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}
	}

	/**
	 * Encerra uma funcionalidade iniciada de um relatório
	 * 
	 * @author Rodrigo Silveira
	 * @date 26/09/2006
	 * @author eduardo henrique
	 * @date 20/11/2008
	 *       Alteração para informar a Data e Hora da dataTermino do Processo de Relatório
	 */
	public void encerrarFuncionalidadeIniciadaRelatorio(int idFuncionalidadeIniciada, int situacaoConclusaoFuncionalidade)
					throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		String consulta;

		try{

			consulta = "update FuncionalidadeIniciada " + "set funcionalidadeSituacao.id = :situacao, dataHoraTermino = :dataAtual  "
							+ "where id = :idFuncionalidadeIniciada";

			session.createQuery(consulta).setInteger("idFuncionalidadeIniciada", idFuncionalidadeIniciada).setInteger("situacao",
							situacaoConclusaoFuncionalidade).setTimestamp("dataAtual", new Date()).executeUpdate();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}
	}

	/**
	 * Inicia uma processo iniciado de um relatório
	 * 
	 * @author Rodrigo Silveira
	 * @date 26/09/2006
	 * @author eduardo henrique
	 * @date 20/11/2008
	 *       Alteração para informar a Data e Hora da dataTermino do Processo de Relatório
	 */
	public void encerrarProcessoIniciadoRelatorio(int idFuncionalidadeIniciada, int situacaoConclusaoFuncionalidade)
					throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		String consulta;

		try{

			consulta = "update ProcessoIniciado " + "set processoSituacao.id = :situacao, dataHoraTermino = :dataAtual  "
							+ "where id IN (select func.processoIniciado.id from FuncionalidadeIniciada as func "
							+ "where func.id = :idFuncionalidadeIniciada) ";

			session.createQuery(consulta).setInteger("idFuncionalidadeIniciada", idFuncionalidadeIniciada).setInteger("situacao",
							situacaoConclusaoFuncionalidade).setTimestamp("dataAtual", new Date()).executeUpdate();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}
	}

	/**
	 * Inicia todos os relatórios agendados
	 * 
	 * @author Rodrigo Silveira
	 * @date 26/09/2006
	 * @author eduardo henrique
	 * @date 11/11/2008
	 *       Alteração na forma de recuperação das tarefas agendadas, para adequação aos campos Blob
	 *       do Oracle.
	 */
	public Collection<byte[]> iniciarRelatoriosAgendados() throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		StringBuilder consulta = new StringBuilder();

		Collection<byte[]> retorno = new ArrayList();

		try{
			String ip = VerificadorIP.getInstancia().getIpAtivo();

			consulta.append("select func.tarefaBatchBlob from FuncionalidadeIniciada func ");
			consulta.append("inner join func.processoIniciado proc ");
			consulta.append("where func.funcionalidadeSituacao = :agendada ");
			consulta.append(" and (proc.dataHoraAgendamento is null ");
			consulta.append("   or proc.dataHoraAgendamento <= current_timestamp()) ");
			if(ip != null) consulta.append(" and proc.ip = :ip ");

			Query query = session.createQuery(consulta.toString()).setInteger("agendada", FuncionalidadeSituacao.AGENDADA);

			if(ip != null) query.setString("ip", ip);

			Collection<byte[]> consultaTarefas = query.list();

			// "Converte" o blob obtido da tarefa, como o array de bytes correspondete
			for(Iterator iterator = consultaTarefas.iterator(); iterator.hasNext();){
				byte[] tarefaBatchBlob = (byte[]) iterator.next();
				retorno.add(tarefaBatchBlob);
			}

			return retorno;

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}
	}

	/**
	 * Pesquisa todos as funcionalidades iniciadas que representam os relatórios
	 * batch do sistema
	 * 
	 * @author Rodrigo Silveira
	 * @date 09/10/2006
	 * @author eduardo henrique
	 * @date 13/01/2008
	 *       Alteracao no método para verificar-se Tipos Relatório e
	 *       Relatório_Resultado_Processamento
	 */
	public Collection<Object[]> pesquisarRelatoriosBatchSistema() throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		String consulta;

		Collection<Object[]> retorno = null;

		try{

			consulta = "select COALESCE(q1.col_1_0_,q2.col_1_0_)  as nomeRelatorio , "
							+ "COALESCE(q1.col_0_0_,0) as quantidadeDisponivel, "
							+ "COALESCE(q2.col_0_0_,0)  as quantidadeEmProcessamento, "
							+ "COALESCE(q1.processoId,q2.processoId) as processo "
							+ "from (select count(funcionali0_.fuin_id) as col_0_0_, "
							+ "processo4_.proc_dsprocesso as col_1_0_ , processoin3_.proc_id as processoId "
							+ "from  funcionalidade_iniciada funcionali0_, "
							+ "processo_funcionalidade processofu1_, funcionalidade funcionali2_, "
							+ "processo_iniciado processoin3_,  processo processo4_ where "
							+ "processoin3_.proc_id=processo4_.proc_id "
							+ "and funcionali0_.proi_id=processoin3_.proi_id "
							+ "and processofu1_.fncd_id=funcionali2_.fncd_id "
							+ "and funcionali0_.prfn_id=processofu1_.prfn_id "
							+ "and (processo4_.prtp_id=:relatorio or processo4_.prtp_id=:relatorioResultadoProcesso) and (funcionali0_.fnst_id = :situacaoConcluida) "
							+ "group by processo4_.proc_dsprocesso, processoin3_.proc_id ) q1 full join "
							+ "(select count(funcionali0_.fuin_id) as col_0_0_, "
							+ "processo4_.proc_dsprocesso as col_1_0_, processoin3_.proc_id as processoId "
							+ "from funcionalidade_iniciada funcionali0_, " + "processo_funcionalidade processofu1_, "
							+ "funcionalidade funcionali2_,  processo_iniciado processoin3_, "
							+ "processo processo4_ where  processoin3_.proc_id=processo4_.proc_id "
							+ "and funcionali0_.proi_id=processoin3_.proi_id " + "and processofu1_.fncd_id=funcionali2_.fncd_id "
							+ "and funcionali0_.prfn_id=processofu1_.prfn_id " + "and processo4_.prtp_id=:relatorio and "
							+ "(funcionali0_.fnst_id = :situacaoEmProcessamento) "
							+ "group by processo4_.proc_dsprocesso, processoin3_.proc_id ) q2 on q1.processoid = q2.processoid "
							+ "order by 1 ";

			retorno = (Collection<Object[]>) session.createSQLQuery(consulta).addScalar("nomeRelatorio", Hibernate.STRING).addScalar(
							"quantidadeDisponivel", Hibernate.INTEGER).addScalar("quantidadeEmProcessamento", Hibernate.INTEGER).addScalar(
							"processo", Hibernate.INTEGER).setInteger("relatorio", ProcessoTipo.RELATORIO).setInteger(
							"relatorioResultadoProcesso", ProcessoTipo.RELATORIO_RESULTADO_PROCESSAMENTO).setInteger("situacaoConcluida",
							FuncionalidadeSituacao.CONCLUIDA)
							.setInteger("situacaoEmProcessamento", FuncionalidadeSituacao.EM_PROCESSAMENTO).list();

			return retorno;

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}
	}

	/**
	 * Remove uma coleção de objetos genéricos na base com um flush para cada 50
	 * registros removidos.
	 * 
	 * @author Pedro Alexandre
	 * @date 09/10/2006
	 * @param colecaoObjetos
	 * @throws ErroRepositorioException
	 */
	public void removerColecaoObjetoParaBatch(Collection<Object> colecaoObjetos) throws ErroRepositorioException{

		if(colecaoObjetos != null && !colecaoObjetos.isEmpty()){

			// obtém uma instância com o hibernate
			StatelessSession session = HibernateUtil.getStatelessSession();

			try{
				Iterator iteratorObjetos = colecaoObjetos.iterator();

				while(iteratorObjetos.hasNext()){
					Object objetoParaRemover = iteratorObjetos.next();
					session.delete(objetoParaRemover);
				}
			}catch(HibernateException e){
				// levanta a exceção para a próxima camada
				throw new ErroRepositorioException(e, "Erro no Hibernate");
			}finally{
				// fecha a sessão com o hibernate
				HibernateUtil.closeSession(session);
			}
		}
	}

	/**
	 * Pesquisa todos as funcionalidades iniciadas que representam os relatórios
	 * batch do sistema por Usuário
	 * 
	 * @author Rodrigo Silveira
	 * @date 25/10/2006
	 */
	public Collection<Object[]> pesquisarRelatoriosBatchPorUsuarioSistema(int idProcesso, int pageOffset) throws ErroRepositorioException{

		String consulta = null;
		Session session = HibernateUtil.getSession();

		Collection<Object[]> retorno = new ArrayList();

		try{

			consulta = "select distinct usuario.nomeUsuario, " + "unidadeOrganizacional.descricao, "
							+ "funcIni.funcionalidadeSituacao.id, " + "funcIni.dataHoraTermino, funcIni.id, "
							+ "funcIni.funcionalidadeSituacao.descricaoOperacaoSituacao, procIni.dataHoraInicio, "
							+ "relatorios.descricaoArquivoGerado, " + "relatorios.id " + "from ProcessoIniciado procIni "
							+ "inner join procIni.processo proc with proc.id = :idProcesso "
							+ "inner join procIni.funcionalidadesIniciadas funcIni " + "inner join funcIni.processoIniciado procIni "
							+ "inner join procIni.usuario usuario " + "inner join usuario.unidadeOrganizacional unidadeOrganizacional "
							+ "left join funcIni.relatoriosGerados relatorios "
							+ "order by procIni.dataHoraInicio desc, relatorios.descricaoArquivoGerado, usuario.nomeUsuario ";

			Query query = session.createQuery(consulta).setInteger("idProcesso", idProcesso);

			if(pageOffset < 0){
				retorno = (Collection<Object[]>) query.list();
			}else{
				retorno = (Collection<Object[]>) query.setFirstResult(10 * pageOffset).setMaxResults(10).list();
			}

			return retorno;

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
			consulta = null;
		}

	}

	public Long pesquisarQuantidadeRelatoriosBatchPorUsuarioSistema(int idProcesso) throws ErroRepositorioException{

		String consulta = null;
		Session session = HibernateUtil.getSession();

		try{
			consulta = " select count(*) " + " from ProcessoIniciado procIni "
							+ " inner join procIni.processo proc with proc.id = :idProcesso ";

			return (Long) session.createQuery(consulta).setInteger("idProcesso", idProcesso).uniqueResult();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
			consulta = null;
		}

	}

	/**
	 * Remove do sistema todos os relatórios batch que estão na data de
	 * expiração
	 * 
	 * @author Rodrigo Silveira
	 * @date 26/10/2006
	 */
	public void deletarRelatoriosBatchDataExpiracao(Date dataDeExpiracao) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		String consulta;

		try{

			consulta = "select distinct procIni from RelatorioGerado rel " + "inner join rel.funcionalidadeIniciada funcIni "
							+ "inner join funcIni.processoIniciado procIni "
							+ "where date(funcIni.dataHoraTermino) <= date(:dataExpiracao)";

			Iterator<ProcessoIniciado> iterator = (Iterator<ProcessoIniciado>) session.createQuery(consulta).setDate("dataExpiracao",
							dataDeExpiracao).iterate();

			while(iterator.hasNext()){
				iterator.next();
				iterator.remove();

			}

			session.flush();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

	}

	/**
	 * Remove do sistema as unidades iniciadas de uma funcionalidade
	 * 
	 * @author Rafael Corrêa
	 * @date 06/11/2006
	 */
	public void removerUnidadesIniciadas(Integer idFuncionalidadeIniciada) throws ErroRepositorioException{

		StringBuilder consulta = new StringBuilder();
		Session session = HibernateUtil.getSession();
		String ip = VerificadorIP.getInstancia().getIpAtivo();

		try{
			consulta.append(" DELETE FROM UnidadeIniciada AS u01 WHERE u01.id IN (");
			consulta.append(" SELECT u02.id FROM UnidadeIniciada AS u02 ");
			consulta.append(" WHERE u02.funcionalidadeIniciada.id = :idFuncionalidadeIniciada ");
			if(ip != null) consulta.append(" AND u02.funcionalidadeIniciada.processoIniciado.ip = :ip ");
			consulta.append(")");

			Query query = session.createQuery(consulta.toString());
			query.setInteger("idFuncionalidadeIniciada", idFuncionalidadeIniciada);
			if(ip != null) query.setString("ip", ip);
			query.executeUpdate();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

	}

	public Collection<Rota> pesquisarRotasProcessamentoBatchCobrancaGrupoNaoInformado(Integer idCobrancaAcaoAtividadeComando)
					throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "SELECT comandoRota.rota " + "FROM CobrancaAtividadeComandoRota comandoRota "
							+ "inner join comandoRota.cobrancaAcaoAtividadeComando "
							+ "where comandoRota.cobrancaAcaoAtividadeComando = :id";

			retorno = session.createQuery(consulta).setInteger("id", idCobrancaAcaoAtividadeComando).list();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		return retorno;

	}

	/**
	 * Inseri uma objeto genérico na base
	 * 
	 * @author Marcio Roberto
	 * @date 18/05/2007
	 * @param Objeto
	 * @throws ErroRepositorioException
	 */
	public Object inserirObjetoParaBatchGerencial(Object objeto) throws ErroRepositorioException{

		// obtém uma instância com o hibernate
		Session session = HibernateUtil.getSessionGerencial();
		Object retorno = null;

		try{
			System.out.println("INSERINDO: " + "-" + objeto.getClass().getName());

			retorno = session.save(objeto);
			session.flush();

			return retorno;
		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão com o hibernate
			HibernateUtil.closeSession(session);
		}
	}

	public void inserirLogExcecaoFuncionalidadeIniciada(UnidadeIniciada unidadeIniciada, Throwable excecao) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		String consulta;

		try{

			consulta = "update FuncionalidadeIniciada " + "set descricaoExcecao = :excecao " + "where descricaoExcecao is null and"
							+ " id IN (select unid.funcionalidadeIniciada.id from UnidadeIniciada unid where unid.id =:unidadeId)";

			// Preparando o stacktrace da exceção para atualização na tabela
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			excecao.printStackTrace(new PrintStream(baos));

			session.createQuery(consulta).setInteger("unidadeId", unidadeIniciada.getId()).setString("excecao", baos.toString())
							.executeUpdate();
			baos.close();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}catch(IOException e){

			e.printStackTrace();
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

	}

	/**
	 * Verifica se o processo está em execução
	 * 
	 * @author Ana Maria
	 * @date 18/12/2008
	 */
	public boolean verificarProcessoEmExecucao(Integer idProcesso) throws ErroRepositorioException{

		boolean retorno = false;

		Session session = HibernateUtil.getSession();
		String consulta;
		Integer retornoHQL = null;

		try{
			consulta = " select proi.id" + " from ProcessoIniciado proi" + " where proi.processo.id = :idProcesso"
							+ " and proi.processoSituacao.id = :idSituacaoProcesso";
			retornoHQL = (Integer) session.createQuery(consulta).setInteger("idProcesso", idProcesso).setInteger("idSituacaoProcesso",
							ProcessoSituacao.EM_PROCESSAMENTO).setMaxResults(1).uniqueResult();

			if(retornoHQL != null){
				retorno = true;
			}

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;

	}

	// ATENÇÃO EXISTEM INCONSSITENCIAS NA BASE COM RELAÇÃO A GRUPOS E ROTAS SÓ DESCOMENTAR QUANDO
	// HOUVER CORREÇÃO.
	//
	// public Collection pesquisarSetorComercialProcessamentoBatchGrupoInformado(
	// Integer idGrupo)
	// throws ErroRepositorioException {
	//
	// Collection retorno = null;
	//
	// Session session = HibernateUtil.getSession();
	// String consulta;
	//
	// try {
	// consulta = "SELECT distinct rota.setorComercial "
	// + "FROM Rota rota "
	// + "where rota.cobrancaGrupo.id = :id ";
	//
	// retorno = session.createQuery(consulta).setInteger("id",
	// idGrupo).list();
	//
	// } catch (HibernateException e) {
	// // levanta a exceção para a próxima camada
	// throw new ErroRepositorioException(e, "Erro no Hibernate");
	// } finally {
	// // fecha a sessão
	// HibernateUtil.closeSession(session);
	// }
	//
	// return retorno;
	//
	// }

	public Collection pesquisarSetorComercialProcessamentoBatchGrupoInformado(Integer idGrupo) throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "SELECT rota.setorComercial.id " + "FROM Imovel imovel " + "inner join  imovel.rota rota "
							+ "where rota.cobrancaGrupo = :id " + "group by rota.setorComercial.id " + "order by count(*) desc";

			retorno = session.createQuery(consulta).setInteger("id", idGrupo).list();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		return retorno;

	}

	public Collection pesquisarSetorComercialProcessamentoBatchCobrancaGrupoNaoInformado(Integer idCobrancaAcaoAtividadeComando)
					throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "SELECT distinct(comandoRota.rota.setorComercial.id) " + "FROM CobrancaAtividadeComandoRota comandoRota "
							+ "inner join comandoRota.cobrancaAcaoAtividadeComando "
							+ "where comandoRota.cobrancaAcaoAtividadeComando = :id";

			retorno = session.createQuery(consulta).setInteger("id", idCobrancaAcaoAtividadeComando).list();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		return retorno;

	}

	/**
	 * Verifica se a FuncionalidadeIniciada foi concluida com erro para evitar a
	 * execução da UnidadeIniciada relacionada
	 * 
	 * @author Rodrigo Silveira
	 * @date 01/09/2006
	 */
	public int pesquisarQuantidadeUnidadesIniciadasPorFuncionalidadeIniciada(int idFuncionalidadeIniciada) throws ErroRepositorioException{

		int retorno = 0;
		Session session = HibernateUtil.getSession();
		StringBuffer consulta = new StringBuffer();

		try{
			consulta
							.append(" select count(*) as quantidade from unidade_iniciada ui ")
							.append(" inner join funcionalidade_iniciada fi on ui.fuin_id = fi.fuin_id     ")
							.append(" inner join processo_iniciado pi on pi.proi_id = fi.proi_id ")
							.append(" inner join processo_funcionalidade pf on pf.proc_id = pi.proc_id ")
							.append(
											" where pf.fncd_id = :idFuncionalidadeIniciada and pi.prst_id = :processoSituacaoProcessamento and ui.unst_id = :unidadeSituacaoProcessamento ");

			retorno = (Integer) session.createSQLQuery(consulta.toString()).addScalar("quantidade", Hibernate.INTEGER).setInteger(
							"idFuncionalidadeIniciada", idFuncionalidadeIniciada).setInteger("processoSituacaoProcessamento",
							UnidadeSituacao.EM_PROCESSAMENTO).setInteger("unidadeSituacaoProcessamento", UnidadeSituacao.EM_PROCESSAMENTO)
							.uniqueResult();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		return retorno;

	}

	/**
	 * [UC0111] Iniciar Processo
	 * [FS0011] – Verificar restrição de execução simultânea de processos
	 * 
	 * @author Hugo Lima
	 * @date 28/02/2012
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarRestricaoExecucaoSimultaneaProcessos(Integer idProcessoIniciar) throws ErroRepositorioException{

		Collection retorno = null;
		Session session = HibernateUtil.getSession();
		StringBuilder consulta = new StringBuilder();
		String listaSituacoesFormatada = ProcessoSituacao.EM_PROCESSAMENTO + ", " + ProcessoSituacao.EM_ESPERA + ", "
						+ ProcessoSituacao.AGENDADO;

		try{
			consulta.append(" select processoRestricaoExecucao.id ");
			consulta.append(" from ProcessoRestricaoExecucao processoRestricaoExecucao ");
			consulta.append(" inner join processoRestricaoExecucao.processoIniciar ");
			consulta.append(" inner join processoRestricaoExecucao.processoExecucao ");
			consulta.append(" where processoRestricaoExecucao.processoIniciar.id = :idProcessoIniciar ");
			consulta.append(" and processoRestricaoExecucao.processoExecucao.id in ( ");
			consulta.append("    select processoIniciado.processo.id from ProcessoIniciado processoIniciado ");
			consulta.append("    inner join processoIniciado.processoSituacao ");
			consulta.append("    where processoIniciado.processoSituacao.id in (" + listaSituacoesFormatada + ") ");
			consulta.append("   ) ");

			retorno = session.createQuery(consulta.toString()).setInteger("idProcessoIniciar", idProcessoIniciar).list();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	public Collection obterTodosIDsSetorComercial(Integer idLocalidadeInicial, Integer idLocalidadeFinal, Integer setorInicial,
					Integer setorFinal) throws ErroRepositorioException{

		// cria a coleção de retorno
		Collection retorno = null;
		// Query
		String consulta;
		// obtém a sessão
		Session session = HibernateUtil.getSession();

		try{
			// pesquisa a coleção de atividades e atribui a variável "retorno"
			consulta = "SELECT setor.id FROM gcom.cadastro.localidade.SetorComercial setor WHERE setor.indicadorUso  = 1 ";
			if(idLocalidadeInicial != null){
				consulta += " and setor.localidade.id between " + idLocalidadeInicial + " and " + idLocalidadeFinal;
			}
			if(setorInicial != null && setorFinal != null){
				consulta += " and setor.codigo between " + setorInicial + " and " + setorFinal;
			}

			retorno = session.createQuery(consulta).list();

			// erro no hibernate
		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}
		// retorna a coleção de atividades pesquisada(s)
		return retorno;
	}

	/**
	 * Pesquisar Funcionalidade Iniciada filtrando pelo Id do Processo Vinculado
	 * 
	 * @author Hebert Falcão
	 * @date 30/08/2012
	 */
	public Collection<FuncionalidadeIniciada> pesquisarFuncionalidadeIniciadaPeloProcessoVinculado(Integer idProcessoIniciadoVinculado)
					throws ErroRepositorioException{

		Collection<FuncionalidadeIniciada> retorno = null;

		Session session = HibernateUtil.getSession();
		StringBuilder consulta = new StringBuilder();

		try{
			consulta.append("select fuin ");
			consulta.append("from FuncionalidadeIniciada fuin ");
			consulta.append("inner join fuin.processoIniciado proi ");
			consulta.append("inner join fuin.processoFuncionalidade prfn ");
			consulta.append("where proi.processoIniciadoVinculado.id = :idProcessoIniciadoVinculado ");
			consulta.append("order by prfn.sequencialExecucao ");

			Query query = session.createQuery(consulta.toString());

			query.setInteger("idProcessoIniciadoVinculado", idProcessoIniciadoVinculado);

			retorno = query.list();
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List<Object[]> consultarProcessoDepencias(ProcessoIniciado processoCorrente) throws ErroRepositorioException{

		List list;
		String sql = "SELECT pd.PROC_IDDEPENDENTE as processoDependenteId, pd.PRDE_DSDADOSCOMPLEMENTARES as dadosComplementares FROM PROCESSO_DEPENDENCIA pd WHERE pd.PRDE_ICUSO = 1 AND pd.PROC_ID = :processoCorrenteId";
		Session session = HibernateUtil.getSession();
		try{
		SQLQuery query = session.createSQLQuery(sql);
		query.addScalar("processoDependenteId", Hibernate.INTEGER);
		query.addScalar("dadosComplementares", Hibernate.STRING);
		query.setInteger("processoCorrenteId", processoCorrente.getProcesso().getId());
			list = query.list();
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return list;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws ErroRepositorioException
	 */
	public int consultarQtdProcessoAntecedenteEmSituacao(ProcessoIniciado processoCorrente, List<Integer> situacoesProcessoPendente)
					throws ErroRepositorioException{

		int qtdDependencias = 0;
		LOGGER.debug("Verificando pendencias de Proncesso iniciado[" + processoCorrente.getId() + "]");
		Session session = null;
		try{
			List<Object[]> dependencias = consultarProcessoDepencias(processoCorrente);
			if(!Util.isVazioOrNulo(dependencias)){

				ProcessoIniciadoDadoComplementarHelper helper;
				Integer processoPendenteId;
				String chavesDadosComplementares;
				String clausulaDadoComplementar = " AND UPPER(pi_.PROI_DSDADOSCOMPLEMENTARES) LIKE :#dadoComplementar# ";
				String clausulaDadoComplementarUltimoProcesso = " AND UPPER(last_pi.PROI_DSDADOSCOMPLEMENTARES) LIKE :#dadoComplementar# ";
				StringBuilder sqlBuilder;
				for(Object[] objects : dependencias){
					processoPendenteId = (Integer) objects[0];
					chavesDadosComplementares = (String) objects[1];
					boolean processoCorrentePossueDadosComplementares = Util.isNaoNuloBrancoZero(processoCorrente
									.getDescricaoDadosComplementares());
					String[] chaveDadosComplementaresArray = chavesDadosComplementares
									.split(ProcessoIniciadoDadoComplementarHelper.SEPARADOR_CHAVES_METADADO);

					// MONTANDO SQL
					sqlBuilder = new StringBuilder();
					// sqlBuilder.append(" SELECT COUNT (*) qtdPendencias ");
					sqlBuilder.append(" SELECT pi_.proi_id processo_iniciado, pi_.proc_id pi_processso, ");
					sqlBuilder.append(" 	pi_.prst_id pi_status, pi_.proi_dsdadoscomplementares dados_complementares");
					sqlBuilder.append(" FROM PROCESSO_INICIADO pi_");
					// JOIN PARA DETECTAR SOMENTE A ULTIMA OCORRENCIA DO PROCESSO
					sqlBuilder.append(" INNER JOIN ( ");
					sqlBuilder.append(" SELECT * FROM (SELECT last_pi.proi_id proi_id FROM PROCESSO_INICIADO last_pi ");
					sqlBuilder.append(" WHERE last_pi.PROC_ID    = :processoPendenteId ");
					sqlBuilder.append(" AND last_pi.proi_tmtermino IS NOT NULL ");
					if(processoCorrente.getCodigoGrupoProcesso() == null){

						sqlBuilder.append(" AND last_pi.PROI_NNGRUPO IS NULL ");


					}else{
						sqlBuilder.append(" AND last_pi.PROI_NNGRUPO = :codigoGrupo ");
					}
					if(processoCorrentePossueDadosComplementares){
						for(int i = 0; i < chaveDadosComplementaresArray.length; i++){
							sqlBuilder.append(clausulaDadoComplementarUltimoProcesso.replaceAll("#dadoComplementar#",
											PREFIXO_VALORES_DADOS_COMPLEMENTARES + i));
						}
					}
					sqlBuilder.append(" ORDER BY last_pi.proi_tmtermino DESC  ");
					sqlBuilder.append(" ) WHERE ROWNUM <=1 ) ultimo_pi ON pi_.proi_id = ultimo_pi.proi_id ");
					// FIM DO JOIN
					sqlBuilder.append(" WHERE ");
					sqlBuilder.append(" 	pi_.PROC_ID = :processoPendenteId ");
					sqlBuilder.append(" 	AND pi_.PRST_ID IN (:processoSituacoesPendente) ");
					if(processoCorrente.getCodigoGrupoProcesso() == null){
						sqlBuilder.append(" 	AND pi_.PROI_NNGRUPO IS NULL ");


					}else{
						sqlBuilder.append(" 	AND pi_.PROI_NNGRUPO = :codigoGrupo ");

					}
					if(processoCorrentePossueDadosComplementares){
						for(int i = 0; i < chaveDadosComplementaresArray.length; i++){
							sqlBuilder.append(clausulaDadoComplementar.replaceAll("#dadoComplementar#",
											PREFIXO_VALORES_DADOS_COMPLEMENTARES + i));
						}
					}
					session = HibernateUtil.getSession();
					SQLQuery query = session.createSQLQuery(sqlBuilder.toString());
					// query.addScalar("qtdPendencias", Hibernate.INTEGER);
					query.addScalar("processo_iniciado", Hibernate.INTEGER);
					query.addScalar("pi_processso", Hibernate.INTEGER);
					query.addScalar("pi_status", Hibernate.INTEGER);
					query.addScalar("dados_complementares", Hibernate.STRING);

					// PREENCHENDO VALORES
					query.setInteger("processoPendenteId", processoPendenteId);



					query.setParameterList("processoSituacoesPendente", situacoesProcessoPendente, Hibernate.INTEGER);

					if(processoCorrente.getCodigoGrupoProcesso() != null){
						query.setInteger("codigoGrupo", processoCorrente.getCodigoGrupoProcesso());
					}

					if(processoCorrentePossueDadosComplementares){
						helper = new ProcessoIniciadoDadoComplementarHelper(processoCorrente.getDescricaoDadosComplementares());
						for(int i = 0; i < chaveDadosComplementaresArray.length; i++){
							LOGGER.debug("PARAMETRO " + PREFIXO_VALORES_DADOS_COMPLEMENTARES + i + " = %"
											+ helper.getChaveValor(DadoComplementarEnumerator.get(chaveDadosComplementaresArray[i])) + "%");
							query.setString(PREFIXO_VALORES_DADOS_COMPLEMENTARES + i,//
											"%" + helper.getChaveValor(DadoComplementarEnumerator.get(chaveDadosComplementaresArray[i]))
															+ "%");
						}
					}

					Collection<Object[]> processosPrecedentesEmExecucao = query.list();
					if(processosPrecedentesEmExecucao.size() > 0){
						StringBuilder builder = new StringBuilder();
						builder.append("Existem [" + processosPrecedentesEmExecucao.size() + "] pendencias para o processo iniciado ["
										+ processoCorrente.getId() + "] \n");
						for(Object[] dadosProcesso : processosPrecedentesEmExecucao){
							builder.append("\t PROI_ID[" + dadosProcesso[0] + "] bloqueando PROI_ID[" + processoCorrente.getId() + "]\n");
						}
						qtdDependencias++;
					}else{
						LOGGER.debug("PROI_ID[" + processoCorrente.getId() + "] Não possue ocorrencias de suas dependencias com PROC_ID["
										+ processoPendenteId + "] Dados[" + chavesDadosComplementares + "] Situacao"
										+ situacoesProcessoPendente);
					}
				}

			}
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return qtdDependencias;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return
	 * @throws ErroRepositorioException
	 * @throws IOException
	 */
	public int preencherTarefaBatch(Integer funcionalidadeIniciadaid, TarefaBatch tarefa) throws ErroRepositorioException, IOException{

		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE ");
		sql.append(FuncionalidadeIniciada.class.getName());
		sql.append(" fi SET fi.tarefaBatchBlob = :tarefaBlob WHERE fi.id = :funcionalidadeId");
		Session session = HibernateUtil.getSession();
		try{
			Query query = session.createQuery(sql.toString());
			query.setBinary("tarefaBlob", IoUtil.transformarObjetoParaBytes(tarefa));
			query.setInteger("funcionalidadeId", funcionalidadeIniciadaid);
			return query.executeUpdate();
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

	}

	/**
	 * [UC0111] - Iniciar Processo
	 * [SB0001] - Iniciar Processo de Faturamento Comandado
	 * 
	 * @author Anderson Italo
	 * @date 28/12/2013
	 */
	public int pesquisarTotalRegistrosFaturamentoSimulacaoComandoNaoRealizados() throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		String consulta;
		int retorno = 0;

		try{

			consulta = "select count(fsc.id) from FaturamentoSimulacaoComando fsc where fsc.dataRealizacao is null ";

			retorno = ((Number) session.createQuery(consulta).uniqueResult()).intValue();

		}catch(HibernateException e){

			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{

			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0111] - Iniciar Processo
	 * [SB0001] - Iniciar Processo de Faturamento Comandado
	 * 
	 * @author Anderson Italo
	 * @date 28/12/2013
	 */
	public Collection<FaturamentoSimulacaoComando> pesquisarFaturamentoSimulacaoComandoNaoRealizados(Integer numeroPagina)
					throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		String consulta;
		Collection<FaturamentoSimulacaoComando> retorno = null;

		try{

			consulta = "select fsc from FaturamentoSimulacaoComando fsc where fsc.dataRealizacao is null ";

			retorno = session.createQuery(consulta).setFirstResult(10 * numeroPagina).setMaxResults(10).list();

		}catch(HibernateException e){

			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{

			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

}