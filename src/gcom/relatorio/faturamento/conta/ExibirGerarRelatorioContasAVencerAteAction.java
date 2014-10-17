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

package gcom.relatorio.faturamento.conta;

import gcom.gui.GcomAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Gerar Relatório de Contas A Vencer Ate
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
		 * // Flag indicando que o usuário fez uma consulta a partir da tecla Enter
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
