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

import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.DateUtil;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action respons�vel pela pre-exibi��o da pagina de inserir processo cobran�a
 * 
 * @author Rodrigo Silveira
 * @created 11/08/2006
 */
public class InserirProcessoCobrancaComandadoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		InserirProcessoActionForm inserirProcessoActionForm = (InserirProcessoActionForm) actionForm;

		String[] idsProcessosCobrancaCronogramaPagina = httpServletRequest.getParameterValues("idsCronograma");

		String[] idsProcessosCobrancaEventualPagina = httpServletRequest.getParameterValues("idsEventuais");

		Collection<Integer> idsProcessosCobrancaEventual = new ArrayList();
		Collection<Integer> idsProcessosCobrancaCronograma = new ArrayList();

		if(idsProcessosCobrancaCronogramaPagina != null){
			for(int i = 0; i < idsProcessosCobrancaCronogramaPagina.length; i++){
				idsProcessosCobrancaCronograma.add(Integer.parseInt(idsProcessosCobrancaCronogramaPagina[i]));

			}
		}

		if(idsProcessosCobrancaEventualPagina != null){
			for(int i = 0; i < idsProcessosCobrancaEventualPagina.length; i++){
				idsProcessosCobrancaEventual.add(Integer.parseInt(idsProcessosCobrancaEventualPagina[i]));

			}
		}

		Date dataHoraAgendamento = null;
		String dataAgendamento = inserirProcessoActionForm.getDataAgendamento();
		String horaAgendamento = inserirProcessoActionForm.getHoraAgendamento();

		try{
			if(StringUtils.isNotEmpty(dataAgendamento) && StringUtils.isNotEmpty(horaAgendamento)){
				dataHoraAgendamento = DateUtil.FORMATO_DIA_MES_ANO_HORA_MIN_SEG.parse(dataAgendamento + " " + horaAgendamento);
			}else if(StringUtils.isNotEmpty(dataAgendamento)){
				dataHoraAgendamento = DateUtil.FORMATO_DIA_MES_ANO_HORA_MIN_SEG.parse(dataAgendamento + " 00:00:00");
			}else if(StringUtils.isNotEmpty(horaAgendamento)){
				dataHoraAgendamento = DateUtil.FORMATO_HORA_MIN_SEG.parse(horaAgendamento);
				Calendar calendarAgendamento = Calendar.getInstance();
				calendarAgendamento.setTime(dataHoraAgendamento);
				Calendar calendarAgora = Calendar.getInstance();
				calendarAgendamento.set(calendarAgora.get(Calendar.YEAR), calendarAgora.get(Calendar.MONTH), calendarAgora
								.get(Calendar.DAY_OF_MONTH));
				dataHoraAgendamento = calendarAgendamento.getTime();
			}
		}catch(ParseException e){
			throw new ActionServletException("atencao.sistema", e);
		}

		Fachada fachada = Fachada.getInstancia();
		fachada.inserirProcessoIniciadoCobrancaComandado(idsProcessosCobrancaCronograma, idsProcessosCobrancaEventual, dataHoraAgendamento,
						(Usuario) (httpServletRequest.getSession(false)).getAttribute("usuarioLogado"), null, null);

		montarPaginaSucesso(httpServletRequest, "Processo(s) Iniciado(s) inserido(s) com sucesso.", "Inserir outro Processo",
						"exibirInserirProcessoAction.do");

		return retorno;
	}

}