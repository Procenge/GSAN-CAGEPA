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

		// Se o usu�rio n�o informar a hora inicial, ela ficar� com o valor
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

		// Se o usu�rio n�o informar a hora final, ela ficar� com o valor
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

		// Filtrando a situa��o do processo.
		if(!checarCampoVazioNulo(filtrarProcessoActionForm.getIdSituacaoProcesso())){
			int idSituacaoProcesso = Integer.parseInt(filtrarProcessoActionForm.getIdSituacaoProcesso());

			if(idSituacaoProcesso != ConstantesSistema.NUMERO_NAO_INFORMADO){
				filtroProcessoIniciado.adicionarParametro(new ParametroSimples(FiltroProcessoIniciado.PROCESSO_SITUACAO_ID,
								idSituacaoProcesso));

			}

		}

		// Filtrando o usu�rio.
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

		// Filtrando data per�odo in�cio inicial.
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

		// Filtrando data per�odo in�cio final.
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

		// Filtrando data conclus�o inicial.
		if(!checarCampoVazioNulo(dataConclusaoInicial)){
			if(checarCampoVazioNulo(dataConclusaoFinal)){
				dataConclusaoFinal = dataConclusaoInicial;
			}

			this.validaComparaDatas(Util.converteStringParaDate(dataConclusaoInicial), Util.converteStringParaDate(dataConclusaoFinal));

			filtroProcessoIniciado.adicionarParametro(new Intervalo(FiltroProcessoIniciado.DATA_HORA_TERMINO, converterDataHora(
							dataConclusaoInicial, horaConclusaoInicial), converterDataHora(dataConclusaoFinal, horaConclusaoInicial)));

		}

		// Filtrando data conclus�o final.
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
	 * Fun��o que valida e compara as datas.
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
	 * Fun��o que verifica se o campo � vazio ou se est� nulo
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
	 * Converte a data e a hora informada pelo usu�rio para um Date
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
