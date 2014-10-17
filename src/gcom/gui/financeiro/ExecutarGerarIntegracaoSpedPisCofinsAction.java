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
 * 
 * GSANPCG
 * Eduardo Henrique
 */
package gcom.gui.financeiro;

import gcom.batch.Processo;
import gcom.batch.ProcessoIniciado;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.util.Util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;


/**
 * @author mgrb
 *
 */
public class ExecutarGerarIntegracaoSpedPisCofinsAction
				extends GcomAction {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
					throws Exception{

		DynaActionForm dynaForm = (DynaActionForm) form;
		String referencia = (String) dynaForm.get("referenciaBase");
		// referencia = referencia.replace("-", "");
		// referencia = referencia.replace("/", "");
		referencia = Util.formatarMesAnoComBarraParaAnoMes(referencia).toString();

		ProcessoIniciado processoIniciado = new ProcessoIniciado();
		Processo processo = new Processo();
		processo.setId(Processo.ENCERRAR_ARRECADACAO);
		processoIniciado.setDataHoraAgendamento(new Date());
		processoIniciado.setProcesso(processo);
		processoIniciado.setUsuario(this.getUsuarioLogado(request));
		List<Object> colecaoParametros = new ArrayList<Object>();
		colecaoParametros.add(Integer.valueOf(referencia));

		Integer codigoProcessoIniciadoGerado = (Integer) Fachada.getInstancia().inserirProcessoIniciadoOnline(processoIniciado,
						colecaoParametros);

		montarPaginaSucesso(request, "Processo Iniciado de código " + codigoProcessoIniciadoGerado + " inserido com sucesso.",
						"Inserir outro Processo", "exibirExecutarGerarIntegracaoSpedPisCofinsAction.do");

		return mapping.findForward("telaSucesso");
	}

}
