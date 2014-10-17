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

import java.util.Date;

import gcom.fachada.Fachada;
import gcom.faturamento.conta.ContaMotivoCancelamento;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.gui.micromedicao.hidrometro.InserirHidrometroDiametroActionForm;
import gcom.micromedicao.hidrometro.HidrometroDiametro;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class InserirContaMotivoCancelamentoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("telaSucesso");

		HttpSession sessao = httpServletRequest.getSession(false);

		Fachada fachada = Fachada.getInstancia();

		InserirContaMotivoCancelamentoActionForm inserirContaMotivoCancelamentoActionForm = (InserirContaMotivoCancelamentoActionForm) actionForm;

		ContaMotivoCancelamento contaMotivoCancelamento = new ContaMotivoCancelamento();

		if(inserirContaMotivoCancelamentoActionForm.getDescricaoMotivoCancelamentoConta() == null
						|| inserirContaMotivoCancelamentoActionForm.getDescricaoMotivoCancelamentoConta().equals("")){
			throw new ActionServletException("atencao.informe_campo_obrigatorio", null, "descrição");
		}
		contaMotivoCancelamento.setDescricaoMotivoCancelamentoConta(inserirContaMotivoCancelamentoActionForm
						.getDescricaoMotivoCancelamentoConta());
		contaMotivoCancelamento.setIndicadorUso(ConstantesSistema.INDICADOR_USO_ATIVO);
		contaMotivoCancelamento.setUltimaAlteracao(new Date());

		Integer idContaMotivoCancelamento = (Integer) fachada.inserir(contaMotivoCancelamento);

		sessao.setAttribute("caminhoRetornoVoltar", "/gsan/exibirInserirHidrometroDiametroAction.do");

		// Montar a página de sucesso
		montarPaginaSucesso(httpServletRequest, "Motivo Cancelamento de Conta de código " + idContaMotivoCancelamento
						+ " inserido com sucesso.", "Inserir outro Motivo Cancelamento de Conta",
						"exibirInserirContaMotivoCancelamentoAction.do?menu=sim",
						"exibirAtualizarContaMotivoCancelamentoAction.do?idRegistroAtualizacao=" + idContaMotivoCancelamento,
						"Atualizar motivo de cancelamento de conta");

		sessao.removeAttribute("InserirContaMotivoCancelamentoActionForm");

		return retorno;
	}
}