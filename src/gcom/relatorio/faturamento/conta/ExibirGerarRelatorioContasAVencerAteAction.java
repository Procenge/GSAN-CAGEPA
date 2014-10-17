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

package gcom.relatorio.faturamento.conta;

import gcom.gui.GcomAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Gerar Relat�rio de Contas A Vencer Ate
 * 
 * @author Diogo Monteiro
 * @date 31/08/2012
 */

public class ExibirGerarRelatorioContasAVencerAteAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("exibirGerarRelatorioContasAVencerAteAction");

		/**
		 * Fachada fachada = Fachada.getInstancia();
		 * // Flag indicando que o usu�rio fez uma consulta a partir da tecla Enter
		 * String anoMesReferenciaStr = httpServletRequest.getParameter("p_anoreferencia");
		 * Integer anoMesReferencia = null;
		 * if(anoMesReferenciaStr != null){
		 * anoMesReferencia = Integer.valueOf(anoMesReferenciaStr);
		 * SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();
		 * if(anoMesReferencia > sistemaParametro.getAnoMesFaturamento()){
		 * throw new ActionServletException(
		 * "atencao.ano_mes_referencia_anterior_que_ano_mes_faturamento_corrente", null, Util
		 * .formatarAnoMesParaMesAno(sistemaParametro.getAnoMesFaturamento()));
		 * }
		 * }
		 */

		return retorno;
	}
}
