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

package gcom.gui.relatorio.cobranca;

import gcom.gui.GcomAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Gerar Relatório Resumo Contabil das Contas em Atraso ate uma referencia Informada.
 * 
 * @author Diogo Monteiro
 * @date 22/03/2012
 */

public class ExibirGerarRelatorioPddAction
				extends GcomAction {

	private static final String TITULO_TELA_RELACAO_CONTAS_ADD_PDD = "Gerar Relatório  de Contas Adicionadas ao PDD";

	private static final String TITULO_TELA_MOV_BAIXA_RENEG_CANCEL_PDD = "Gerar Relatório  de Movimento de Baixa/Reneg./Cancelamento PDD";

	private static final String TITULO_TELA_PERDAS_PROVAVEIS_RECEBIMENTO = "Gerar Relatório  de Perdas Prováveis de Recebimento PDD";

	private static final String MSG_TELA_RELACAO_CONTAS_ADD_PDD = "Deseja Gerar Relatório  de Contas Adicionadas ao PDD?";

	private static final String MSG_TELA_MOV_BAIXA_RENEG_CANCEL_PDD = "Deseja Gerar Relatório  de Movimento de Baixa/Reneg./Cancelamento PDD?";

	private static final String MSG_TELA_PERDAS_PROVAVEIS_RECEBIMENTO = "Deseja Gerar Relatório  de Perdas Prováveis de Recebimento PDD?";

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
