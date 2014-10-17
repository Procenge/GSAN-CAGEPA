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
 */

package gcom.gui.batch;

import gcom.batch.FiltroProcessoIniciado;
import gcom.batch.ProcessoIniciado;
import gcom.batch.ProcessoTipo;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesConfig;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.agendadortarefas.VerificadorIP;
import gcom.util.filtro.Intervalo;
import gcom.util.filtro.ParametroSimples;
import gcom.util.filtro.ParametroSimplesDiferenteDe;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action que filtra um ProcessoIniciado no sistema
 * 
 * @author Rodrigo Silveira
 * @created 24/07/2006
 */
public class FiltrarProcessoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("resultadoFiltrarProcesso");

		FiltrarProcessoActionForm filtrarProcessoActionForm = (FiltrarProcessoActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		String dataAgendamentoInicial = filtrarProcessoActionForm.getDataAgendamentoInicial();
		String horaAgendamentoInicial = filtrarProcessoActionForm.getHoraAgendamentoInicial();
		String dataAgendamentoFinal = filtrarProcessoActionForm.getDataAgendamentoFinal();
		String horaAgendamentoFinal = filtrarProcessoActionForm.getHoraAgendamentoFinal();
		String dataPeriodoInicioInicial = filtrarProcessoActionForm.getDataPeriodoInicioInicial();
		String horaPeriodoInicioInicial = filtrarProcessoActionForm.getHoraPeriodoInicioInicial();
		String dataPeriodoInicioFinal = filtrarProcessoActionForm.getDataPeriodoInicioFinal();
		String horaPeriodoInicioFinal = filtrarProcessoActionForm.getHoraPeriodoInicioFinal();
		String dataConclusaoInicial = filtrarProcessoActionForm.getDataConclusaoInicial();
		String horaConclusaoInicial = filtrarProcessoActionForm.getHoraConclusaoInicial();
		String dataConclusaoFinal = filtrarProcessoActionForm.getDataConclusaoFinal();
		String horaConclusaoFinal = filtrarProcessoActionForm.getHoraConclusaoFinal();
		String dataComandoInicial = filtrarProcessoActionForm.getDataComandoInicial();
		String horaComandoInicial = filtrarProcessoActionForm.getHoraComandoInicial();
		String dataComandoFinal = filtrarProcessoActionForm.getDataComandoFinal();
		String horaComandoFinal = filtrarProcessoActionForm.getHoraComandoFinal();
		String filtrarPorIP = filtrarProcessoActionForm.getFiltrarPorIP();

		// Se o usuário não informar a hora inicial, ela ficará com o valor
		// "00:00:00"
		if(checarCampoVazioNulo(horaAgendamentoInicial)){
			horaAgendamentoInicial = "00:00:00";

		}

		if(checarCampoVazioNulo(horaPeriodoInicioInicial)){
			horaPeriodoInicioInicial = "00:00:00";

		}

		if(checarCampoVazioNulo(horaConclusaoInicial)){
			horaConclusaoInicial = "00:00:00";

		}

		if(checarCampoVazioNulo(horaComandoInicial)){
			horaComandoInicial = "00:00:00";

		}

		// Se o usuário não informar a hora final, ela ficará com o valor
		// "23:59:59"
		if(checarCampoVazioNulo(horaAgendamentoFinal)){
			horaAgendamentoFinal = "23:59:59";

		}

		if(checarCampoVazioNulo(horaPeriodoInicioFinal)){
			horaPeriodoInicioFinal = "23:59:59";

		}

		if(checarCampoVazioNulo(horaConclusaoFinal)){
			horaConclusaoFinal = "23:59:59";

		}

		if(checarCampoVazioNulo(horaComandoFinal)){
			horaComandoFinal = "23:59:59";

		}

		FiltroProcessoIniciado filtroProcessoIniciado = new FiltroProcessoIniciado(FiltroProcessoIniciado.DATA_HORA_AGENDAMENTO);

		// Filtrando o processo.
		if(!checarCampoVazioNulo(filtrarProcessoActionForm.getIdProcesso())){
			int idProcesso = Integer.parseInt(filtrarProcessoActionForm.getIdProcesso());

			if(idProcesso != ConstantesSistema.NUMERO_NAO_INFORMADO){
				filtroProcessoIniciado.adicionarParametro(new ParametroSimples(FiltroProcessoIniciado.ID_PROCESSO, idProcesso));

			}

		}

		// Filtrando o tipo do processo.
		if(!checarCampoVazioNulo(filtrarProcessoActionForm.getIdTipoProcesso())){
			int idTipoProcesso = Integer.parseInt(filtrarProcessoActionForm.getIdTipoProcesso());
			if(idTipoProcesso != ConstantesSistema.NUMERO_NAO_INFORMADO) filtroProcessoIniciado.adicionarParametro(new ParametroSimples(
							FiltroProcessoIniciado.TIPO_PROCESSO, idTipoProcesso));
		}

		// Filtrando a situação do processo.
		if(!checarCampoVazioNulo(filtrarProcessoActionForm.getIdSituacaoProcesso())){
			int idSituacaoProcesso = Integer.parseInt(filtrarProcessoActionForm.getIdSituacaoProcesso());

			if(idSituacaoProcesso != ConstantesSistema.NUMERO_NAO_INFORMADO){
				filtroProcessoIniciado.adicionarParametro(new ParametroSimples(FiltroProcessoIniciado.PROCESSO_SITUACAO_ID,
								idSituacaoProcesso));

			}

		}

		// Filtrando o usuário.
		if(!checarCampoVazioNulo(filtrarProcessoActionForm.getIdUsuario())){
			int idUsuario = Integer.parseInt(filtrarProcessoActionForm.getIdUsuario());
			if(idUsuario != ConstantesSistema.NUMERO_NAO_INFORMADO) filtroProcessoIniciado.adicionarParametro(new ParametroSimples(
							FiltroProcessoIniciado.ID_USUARIO, idUsuario));
		}

		// Filtrando data agendamento inicial.
		if(!checarCampoVazioNulo(dataAgendamentoInicial)){
			if(checarCampoVazioNulo(dataAgendamentoFinal)){
				dataAgendamentoFinal = dataAgendamentoInicial;
			}

			this.validaComparaDatas(Util.converteStringParaDate(dataAgendamentoInicial), Util.converteStringParaDate(dataAgendamentoFinal));

			filtroProcessoIniciado
							.adicionarParametro(new Intervalo(FiltroProcessoIniciado.DATA_HORA_AGENDAMENTO, converterDataHora(
											dataAgendamentoInicial, horaAgendamentoInicial), converterDataHora(dataAgendamentoFinal,
											horaAgendamentoFinal)));

		}

		// Filtrando data agendamento final.
		if(!checarCampoVazioNulo(dataAgendamentoFinal)){
			if(checarCampoVazioNulo(dataAgendamentoInicial)){
				dataAgendamentoInicial = dataAgendamentoFinal;
			}

			this.validaComparaDatas(Util.converteStringParaDate(dataAgendamentoInicial), Util.converteStringParaDate(dataAgendamentoFinal));

			filtroProcessoIniciado
							.adicionarParametro(new Intervalo(FiltroProcessoIniciado.DATA_HORA_AGENDAMENTO, converterDataHora(
											dataAgendamentoInicial, horaAgendamentoInicial), converterDataHora(dataAgendamentoFinal,
											horaAgendamentoFinal)));

		}

		// Filtrando data período início inicial.
		if(!checarCampoVazioNulo(dataPeriodoInicioInicial)){
			if(checarCampoVazioNulo(dataPeriodoInicioFinal)){
				dataPeriodoInicioFinal = dataPeriodoInicioInicial;
			}

			this.validaComparaDatas(Util.converteStringParaDate(dataPeriodoInicioInicial), Util
							.converteStringParaDate(dataPeriodoInicioFinal));

			filtroProcessoIniciado.adicionarParametro(new Intervalo(FiltroProcessoIniciado.DATA_HORA_INICIO, converterDataHora(
							dataPeriodoInicioInicial, horaPeriodoInicioInicial), converterDataHora(dataPeriodoInicioFinal,
							horaPeriodoInicioFinal)));

		}

		// Filtrando data período início final.
		if(!checarCampoVazioNulo(dataPeriodoInicioFinal)){
			if(checarCampoVazioNulo(dataPeriodoInicioInicial)){
				dataPeriodoInicioInicial = dataPeriodoInicioFinal;
			}

			this.validaComparaDatas(Util.converteStringParaDate(dataPeriodoInicioInicial), Util
							.converteStringParaDate(dataPeriodoInicioFinal));

			filtroProcessoIniciado.adicionarParametro(new Intervalo(FiltroProcessoIniciado.DATA_HORA_INICIO, converterDataHora(
							dataPeriodoInicioInicial, horaPeriodoInicioInicial), converterDataHora(dataPeriodoInicioFinal,
							horaPeriodoInicioFinal)));

		}

		// Filtrando data conclusão inicial.
		if(!checarCampoVazioNulo(dataConclusaoInicial)){
			if(checarCampoVazioNulo(dataConclusaoFinal)){
				dataConclusaoFinal = dataConclusaoInicial;
			}

			this.validaComparaDatas(Util.converteStringParaDate(dataConclusaoInicial), Util.converteStringParaDate(dataConclusaoFinal));

			filtroProcessoIniciado.adicionarParametro(new Intervalo(FiltroProcessoIniciado.DATA_HORA_TERMINO, converterDataHora(
							dataConclusaoInicial, horaConclusaoInicial), converterDataHora(dataConclusaoFinal, horaConclusaoInicial)));

		}

		// Filtrando data conclusão final.
		if(!checarCampoVazioNulo(dataConclusaoFinal)){
			if(checarCampoVazioNulo(dataConclusaoInicial)){
				dataConclusaoInicial = dataConclusaoFinal;
			}

			this.validaComparaDatas(Util.converteStringParaDate(dataConclusaoInicial), Util.converteStringParaDate(dataConclusaoFinal));

			filtroProcessoIniciado.adicionarParametro(new Intervalo(FiltroProcessoIniciado.DATA_HORA_TERMINO, converterDataHora(
							dataConclusaoInicial, horaConclusaoInicial), converterDataHora(dataConclusaoFinal, horaConclusaoInicial)));

		}

		// Filtrando data comando inicial.
		if(!checarCampoVazioNulo(dataComandoInicial)){
			if(checarCampoVazioNulo(dataComandoFinal)){
				dataComandoFinal = dataComandoInicial;
			}

			this.validaComparaDatas(Util.converteStringParaDate(dataComandoInicial), Util.converteStringParaDate(dataComandoFinal));

			filtroProcessoIniciado.adicionarParametro(new Intervalo(FiltroProcessoIniciado.DATA_HORA_COMANDO, converterDataHora(
							dataComandoInicial, horaComandoInicial), converterDataHora(dataComandoFinal, horaComandoFinal)));

		}

		// Filtrando data comando final.
		if(!checarCampoVazioNulo(dataComandoFinal)){
			if(checarCampoVazioNulo(dataComandoInicial)){
				dataComandoInicial = dataComandoFinal;
			}

			this.validaComparaDatas(Util.converteStringParaDate(dataComandoInicial), Util.converteStringParaDate(dataComandoFinal));

			filtroProcessoIniciado.adicionarParametro(new Intervalo(FiltroProcessoIniciado.DATA_HORA_COMANDO, converterDataHora(
							dataComandoInicial, horaComandoInicial), converterDataHora(dataComandoFinal, horaComandoFinal)));

		}

		filtroProcessoIniciado.adicionarCaminhoParaCarregamentoEntidade("processo");

		filtroProcessoIniciado.adicionarCaminhoParaCarregamentoEntidade("usuario");

		filtroProcessoIniciado.adicionarCaminhoParaCarregamentoEntidade("processoSituacao");

		filtroProcessoIniciado.adicionarParametro(new ParametroSimplesDiferenteDe("processo.processoTipo.id", new Integer(
						ProcessoTipo.RELATORIO_RESULTADO_PROCESSAMENTO)));

		// Filtrando o Grupo.
		if(!checarCampoVazioNulo(filtrarProcessoActionForm.getCodigoGrupoProcesso())){
			int codigoGrupo = Integer.parseInt(filtrarProcessoActionForm.getCodigoGrupoProcesso());

			if(codigoGrupo != ConstantesSistema.NUMERO_NAO_INFORMADO){
				filtroProcessoIniciado.adicionarParametro(new ParametroSimples(FiltroProcessoIniciado.CODIGO_GRUPO, codigoGrupo));

			}

		}

		if(ConstantesSistema.SIM.equals(Short.valueOf(filtrarPorIP))){
			// Filtrar Processo por IP
			filtroProcessoIniciado
							.adicionarParametro(new ParametroSimples(FiltroProcessoIniciado.IP, VerificadorIP.getInstancia().getIp()));
		}

		Map resultado = controlarPaginacao(httpServletRequest, retorno, filtroProcessoIniciado, ProcessoIniciado.class.getName());
		Collection<ProcessoIniciado> colecaoProcessosIniciados = (Collection) resultado.get("colecaoRetorno");
		retorno = (ActionForward) resultado.get("destinoActionForward");

		httpServletRequest.setAttribute("colecaoProcessosIniciados", colecaoProcessosIniciados);

		httpServletRequest.setAttribute("mesAnoReferencia", Util.formatarAnoMesParaMesAno(fachada.pesquisarParametrosDoSistema()
						.getAnoMesFaturamento()));

		httpServletRequest.setAttribute("dataCorrente", new Date());
		httpServletRequest.setAttribute("ipLocal", VerificadorIP.getInstancia().getIp());
		httpServletRequest.setAttribute("exibirIpProcesso", ConstantesConfig.getExibirIpProcesso());

		return retorno;
	}

	/**
	 * Função que valida e compara as datas.
	 * 
	 * @author Ricardo Rodrigues
	 * @date 15/03/2012
	 * @param dataInicial
	 * @param dataFinal
	 */
	private void validaComparaDatas(Date dataInicial, Date dataFinal){

		String dataInicio = Util.formatarData(dataInicial);
		String dataFim = Util.formatarData(dataFinal);

		// Se data inicio maior que data fim
		if(Util.compararData(dataInicial, dataFinal) == 1){
			throw new ActionServletException("atencao.data_inicial_maior_data_final", dataInicio, dataFim);
		}

	}

	/**
	 * Função que verifica se o campo é vazio ou se está nulo
	 * 
	 * @author Rodrigo Silveira
	 * @date 26/07/2006
	 * @return
	 */
	private boolean checarCampoVazioNulo(String campo){

		boolean retorno = false;
		if(campo == null || campo.trim().equals("") || campo.trim().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			retorno = true;

		}
		return retorno;

	}

	/**
	 * Converte a data e a hora informada pelo usuário para um Date
	 * 
	 * @author Rodrigo Silveira
	 * @date 26/07/2006
	 * @param data
	 * @param hora
	 * @return
	 */
	private Date converterDataHora(String data, String hora){

		SimpleDateFormat formatoDataHora = new SimpleDateFormat("dd/MM/yyyy k:mm:ss");
		try{
			return formatoDataHora.parse(data + " " + hora);
		}catch(ParseException e){
			throw new ActionServletException("erro.sistema");

		}

	}
}
