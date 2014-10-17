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

package gcom.gui.relatorio.faturamento;

import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Pré-processamento da primeira página de [UC3048] Gerar Relatorio Inclusoes Cancelamentos de
 * Faturamento Original
 * 
 * @author Ricardo Rodrigues.
 */
public class ExibirGerarRelatorioConciliacaoAction
				extends GcomAction {

	private static final String RELATORIO_CONCILIACAO_CONTABIL = "RelatorioConciliacaoContabil.rpt";

	private static final String SIM = "1";

	private static final String NAO = "2";

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		httpServletRequest.setAttribute("limparTela", "s");

		ActionForward retorno = actionMapping.findForward("exibirGerarRelatorioConciliacaoAction");

		String tipoRelatorio = (String) httpServletRequest.getParameter("tipoRelatorio");

		// Indicadores criado para desativar cada tipo de totalizador na tela.
		// Caso queira a tela apenas com o campo de referencia deve-se destivar o
		// indicadorMostraTotalizadores
		Integer indicadorMostraTotalizadores = ConstantesSistema.SIM.intValue();

		Integer indicadorMostraTotalizadorEstado = ConstantesSistema.SIM.intValue();
		Integer indicadorMostraTotalizadorEstadoGerencia = ConstantesSistema.SIM.intValue();
		Integer indicadorMostraTotalizadorEstadoLocalidade = ConstantesSistema.SIM.intValue();

		// String que será exibida como label do campo AMReferencia
		String descricaoAMReferencia = "";

		// if(tipoRelatorio != null && tipoRelatorio.equals("conciliacaoContabil")){// VAI SAIR
			httpServletRequest.setAttribute("relatorio", RELATORIO_CONCILIACAO_CONTABIL);
			httpServletRequest.setAttribute("tituloRelatorio", "Relatório para Conciliação Contábil");
			httpServletRequest.setAttribute("ehRelatorioBatch", SIM);
			descricaoAMReferencia = "Mês/Ano Contábil";

		// }

		httpServletRequest.setAttribute("indicadorMostraTotalizadorEstado", indicadorMostraTotalizadorEstado);
		httpServletRequest.setAttribute("indicadorMostraTotalizadorEstadoGerencia", indicadorMostraTotalizadorEstadoGerencia);
		httpServletRequest.setAttribute("indicadorMostraTotalizadorEstadoLocalidade", indicadorMostraTotalizadorEstadoLocalidade);

		httpServletRequest.setAttribute("indicadorMostraTotalizadores", indicadorMostraTotalizadores);

		httpServletRequest.getSession().setAttribute("descricaoAMReferencia", descricaoAMReferencia);

		httpServletRequest.setAttribute("relatorioTipoReloadPage", tipoRelatorio);

		return retorno;
	}
}
