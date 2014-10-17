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
 * 
 * Vitor Hora
 */

package gcom.util.agendadortarefas;

import gcom.batch.VerificadorProcessosIniciados;
import gcom.fachada.Fachada;
import gcom.tarefa.Tarefa;
import gcom.util.ConstantesConfig;
import gcom.util.SistemaException;
import gcom.util.Util;

import org.jboss.logging.Logger;
import org.quartz.JobDetail;
import org.quartz.ObjectAlreadyExistsException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerUtils;
import org.quartz.impl.StdSchedulerFactory;

/**
 * Essa classe tem o papel de fornecer ao sistema servi�os de agendamento de
 * tarefas utilizando a biblioteca Quartz
 * 
 * @author Rodrigo Silveira
 * @date 03/08/2006
 */
public final class AgendadorTarefas {

	private static final Logger LOGGER = Logger.getLogger(AgendadorTarefas.class);


	private static Scheduler instancia;

	private AgendadorTarefas() {

	}

	public static void initAgendador(){

		// SchedulerFactory schedFact = StdSchedulerFactory.getDefaultScheduler();
		try{
			instancia = StdSchedulerFactory.getDefaultScheduler();
			agendarTarefaVerificadoraProcessosIniciados();
			marcarProcessosInterrompidos();
			instancia.start();
		}catch(SchedulerException e){
			e.printStackTrace();
			throw new SistemaException("Problema ao inicializar o Agendador de Tarefas");
		}
	}

	private static void marcarProcessosInterrompidos(){

		Fachada.getInstancia().marcarProcessosInterrompidos();
	}

	public static Scheduler getAgendador(){

		return instancia;
	}

	/**
	 * Inicializa a tarefa que busca no sistema processosIniciados para ativar a
	 * execu��o de minuto em minuto
	 * 
	 * @author eduardo henrique
	 * @date 20/02/2009
	 *       Altera��o no m�todo para verifica��o de se agendador ficar� ativo ou n�o.
	 * @author Rodrigo Silveira
	 * @date 21/08/2006
	 */
	private static void agendarTarefaVerificadoraProcessosIniciados(){

		String agendadorAtivo = ConstantesConfig.getAgendador();

		String tempoVerificador = "60";
		if(!Util.isVazioOuBranco(ConstantesConfig.getTempoVerificador())){
			tempoVerificador = ConstantesConfig.getTempoVerificador();
		}

		if(agendadorAtivo == null || agendadorAtivo.equals("1")){

			JobDetail jobDetail = new JobDetail("verificador", null, VerificadorProcessosIniciados.class);

			Trigger trigger = TriggerUtils.makeSecondlyTrigger(new Integer(tempoVerificador));

			trigger.setStartTime(TriggerUtils.getNextGivenSecondDate(null, 10));
			trigger.setName(jobDetail.getName());

			try{
				getAgendador().scheduleJob(jobDetail, trigger);
			}catch(ObjectAlreadyExistsException e){
				try{
					getAgendador().rescheduleJob("verificador", null, trigger);
				}catch(SchedulerException se){
					throw new SistemaException(se, "Problema ao agendar uma tarefa");
				}
			}catch(SchedulerException e){
				throw new SistemaException(e, "Problema ao agendar uma tarefa");
			}
		}
	}

	public static void agendarTarefa(String nomeTarefa, Tarefa tarefa){

		JobDetail jobDetail = new JobDetail(nomeTarefa + Util.geradorSenha(13), null, tarefa.getClass());

		jobDetail.getJobDataMap().put("usuario", tarefa.getUsuario());
		jobDetail.getJobDataMap().put("parametroTarefa", tarefa.getParametroTarefa());
		jobDetail.getJobDataMap().put("idFuncionalidadeIniciada", tarefa.getIdFuncionalidadeIniciada());

		Trigger trigger = TriggerUtils.makeImmediateTrigger(0, 0);
		trigger.setStartTime(TriggerUtils.getNextGivenSecondDate(null, 10));
		trigger.setName(jobDetail.getName());

		/*
		 * try {
		 * getAgendador().addJob(jobDetail, true);
		 * getAgendador().triggerJob(jobDetail.getName(), null);
		 * getAgendador().scheduleJob(jobDetail, triggerTarefa);
		 * } catch (SchedulerException e) {
		 * e.printStackTrace();
		 * throw new SistemaException("Problema ao agendar uma tarefa");
		 * }
		 */

		// Verifica se o job j� foi iniciado
		try{
			// getAgendador().addJob(jobDetail, true);
			// getAgendador().triggerJob(jobDetail.getName(), null);
			getAgendador().scheduleJob(jobDetail, trigger);
			LOGGER.info("Inicializando FuncionalidadeIniciada[" + tarefa.getIdFuncionalidadeIniciada() + "][" + jobDetail.getName() + "]");
			// if (jobDetail == null) {
			// getAgendador().scheduleJob(jobDetail, triggerTarefa);
			// } else {
			// getAgendador().rescheduleJob(jobDetail.getName(), jobDetail.getGroup(),
			// triggerTarefa);
			// }

		}catch(SchedulerException e){
			e.printStackTrace();
			throw new SistemaException("Problema ao agendar uma tarefa");
		}
	}

	public static void main(String[] args) throws SchedulerException{

	}

}
