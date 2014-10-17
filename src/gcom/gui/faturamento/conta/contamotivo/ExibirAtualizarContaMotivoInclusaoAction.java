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
 * GSANPCG
 * André Nishimura
 * Eduardo Henrique Bandeira Carneiro da Silva
 * José Gilberto de França Matos
 * Lucas Daniel Medeiros
 * Saulo Vasconcelos de Lima
 * Virginia Santos de Melo
 *
 * Este programa é software livre; você pode redistribuí-lo e/ou
 * modificá-lo sob os termos de Licença Pública Geral GNU, conforme
 * publicada pela Free Software Foundation; versão 2 da Licença.
 * Este programa é distribuído na expectativa de ser útil, mas SEM
 * QUALQUER GARANTIA; sem mesmo a garantia implícita de
 * COMERCIALIZAÇÃO ou de ADEQUAÇÃO A QUALQUER PROPÓSITO EM
 * PARTICULAR. 
 * Consulte a Licença Pública Geral GNU para obter mais detalhes.
 * Você deve ter recebido uma cópia da Licença Pública Geral GNU
 * junto com este programa; se não, escreva para Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 * 02111-1307, USA.
 */

package gcom.gui.faturamento.conta.contamotivo;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import gcom.fachada.Fachada;
import gcom.faturamento.conta.ContaMotivoCancelamento;
import gcom.faturamento.conta.ContaMotivoInclusao;
import gcom.faturamento.conta.FiltroContaMotivoCancelamento;
import gcom.faturamento.conta.FiltroContaMotivoInclusao;
import gcom.gui.GcomAction;
import gcom.util.filtro.ParametroSimples;

public class ExibirAtualizarContaMotivoInclusaoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		final ActionForward retorno = actionMapping.findForward("exibirAtualizarContaMotivoInclusao");

		final HttpSession sessao = httpServletRequest.getSession(false);

		final AtualizarContaMotivoInclusaoActionForm atualizarContaMotivoInclusaoActionForm = (AtualizarContaMotivoInclusaoActionForm) actionForm;

		final Fachada fachada = Fachada.getInstancia();

		String idRegistroAtualizacao = (String) httpServletRequest.getParameter("idRegistroAtualizacao");

		FiltroContaMotivoInclusao filtroContaMotivoInclusao = new FiltroContaMotivoInclusao();

		filtroContaMotivoInclusao.adicionarParametro(new ParametroSimples(FiltroContaMotivoInclusao.CODIGO, idRegistroAtualizacao));

		Collection colecao = fachada.pesquisar(filtroContaMotivoInclusao, ContaMotivoInclusao.class.getName());

		ContaMotivoInclusao contaMotivoInclusao = (ContaMotivoInclusao) colecao.iterator().next();

		atualizarContaMotivoInclusaoActionForm.setId(String.valueOf(contaMotivoInclusao.getId()));
		atualizarContaMotivoInclusaoActionForm.setDescricao(contaMotivoInclusao.getDescricaoMotivoInclusaoConta());
		sessao.setAttribute("atualizarContaMotivoInclusaoActionForm", atualizarContaMotivoInclusaoActionForm);
		return retorno;
	}
}
