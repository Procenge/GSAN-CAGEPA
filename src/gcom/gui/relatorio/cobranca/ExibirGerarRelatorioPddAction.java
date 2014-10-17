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

package gcom.gui.relatorio.cobranca;

import gcom.gui.GcomAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Gerar Relat�rio Resumo Contabil das Contas em Atraso ate uma referencia Informada.
 * 
 * @author Diogo Monteiro
 * @date 22/03/2012
 */

public class ExibirGerarRelatorioPddAction
				extends GcomAction {

	private static final String TITULO_TELA_RELACAO_CONTAS_ADD_PDD = "Gerar Relat�rio  de Contas Adicionadas ao PDD";

	private static final String TITULO_TELA_MOV_BAIXA_RENEG_CANCEL_PDD = "Gerar Relat�rio  de Movimento de Baixa/Reneg./Cancelamento PDD";

	private static final String TITULO_TELA_PERDAS_PROVAVEIS_RECEBIMENTO = "Gerar Relat�rio  de Perdas Prov�veis de Recebimento PDD";

	private static final String MSG_TELA_RELACAO_CONTAS_ADD_PDD = "Deseja Gerar Relat�rio  de Contas Adicionadas ao PDD?";

	private static final String MSG_TELA_MOV_BAIXA_RENEG_CANCEL_PDD = "Deseja Gerar Relat�rio  de Movimento de Baixa/Reneg./Cancelamento PDD?";

	private static final String MSG_TELA_PERDAS_PROVAVEIS_RECEBIMENTO = "Deseja Gerar Relat�rio  de Perdas Prov�veis de Recebimento PDD?";

	private static final String RELATORIO_RELACAO_CONTAS_ADD_PDD = "RelatorioContasAdicionadasPDD.rpt";

	private static final String RELATORIO_MOV_BAIXA_RENEG_CANCEL_PDD = "RelatorioMovBaixaRenegCancelamentoPDD.rpt";

	private static final String RELATORIO_PERDAS_PROVAVEIS_RECEBIMENTO = "RelatorioPerdasProvaveisRecebimentoPDD.rpt";

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("exibirGerarRelatorioPddAction");

		String tipoRelatorio = httpServletRequest.getParameter("tipoRelatorio");

		if(tipoRelatorio.equals("contaAddPdd")){

			httpServletRequest.setAttribute("relatorio", RELATORIO_RELACAO_CONTAS_ADD_PDD);
			httpServletRequest.setAttribute("tituloTela", TITULO_TELA_RELACAO_CONTAS_ADD_PDD);
			httpServletRequest.setAttribute("msg", MSG_TELA_RELACAO_CONTAS_ADD_PDD);

		}else if(tipoRelatorio.equals("movBaixaRenegCancelPdd")){

			httpServletRequest.setAttribute("relatorio", RELATORIO_MOV_BAIXA_RENEG_CANCEL_PDD);
			httpServletRequest.setAttribute("tituloTela", TITULO_TELA_MOV_BAIXA_RENEG_CANCEL_PDD);
			httpServletRequest.setAttribute("msg", MSG_TELA_MOV_BAIXA_RENEG_CANCEL_PDD);

		}else{

			httpServletRequest.setAttribute("relatorio", RELATORIO_PERDAS_PROVAVEIS_RECEBIMENTO);
			httpServletRequest.setAttribute("tituloTela", TITULO_TELA_PERDAS_PROVAVEIS_RECEBIMENTO);
			httpServletRequest.setAttribute("msg", MSG_TELA_PERDAS_PROVAVEIS_RECEBIMENTO);

		}

		return retorno;
	}

}
