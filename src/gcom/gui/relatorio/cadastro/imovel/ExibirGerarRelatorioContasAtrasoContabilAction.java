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

package gcom.gui.relatorio.cadastro.imovel;

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

public class ExibirGerarRelatorioContasAtrasoContabilAction
				extends GcomAction {

	private static final String TITULO_TELA_CONTAS_ATRASO_CONTABIL = "Gerar Relat�rio  de Contas em Atraso Contabil";

	private static final String TITULO_TELA_RECEBIMENTO_VENCIMENTO = "Gerar Relat�rio  de Recebimento por Vencimento";

	private static final String RELATORIO_CONTAS_ATRASO_CONTABIL = "RelatorioContasAtrasoContabil.rpt";

	private static final String RELATORIO_RECEBIMENTO_VENCIMENTO = "RelatorioRecebimentoPorVencimento.rpt";

	private static final String SIM = "1";

	private static final String NAO = "2";

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("exibirGerarRelatorioContasAtrasoContabilAction");

		String tipoRelatorio = httpServletRequest.getParameter("tipoRelatorio");

		if(tipoRelatorio.equals("contaAtrasoContabil")){

			httpServletRequest.setAttribute("relatorio", RELATORIO_CONTAS_ATRASO_CONTABIL);
			httpServletRequest.setAttribute("tituloTela", TITULO_TELA_CONTAS_ATRASO_CONTABIL);
			httpServletRequest.setAttribute("ehRelatorioBatch", SIM);

		}else{

			httpServletRequest.setAttribute("relatorio", RELATORIO_RECEBIMENTO_VENCIMENTO);
			httpServletRequest.setAttribute("tituloTela", TITULO_TELA_RECEBIMENTO_VENCIMENTO);
			httpServletRequest.setAttribute("ehRelatorioBatch", SIM);

		}

		return retorno;
	}
}
