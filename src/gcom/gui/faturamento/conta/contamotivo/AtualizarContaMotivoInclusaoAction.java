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
 * GSANPCG
 * Andr� Nishimura
 * Eduardo Henrique Bandeira Carneiro da Silva
 * Jos� Gilberto de Fran�a Matos
 * Lucas Daniel Medeiros
 * Saulo Vasconcelos de Lima
 * Virginia Santos de Melo
 *
 * Este programa � software livre; voc� pode redistribu�-lo e/ou
 * modific�-lo sob os termos de Licen�a P�blica Geral GNU, conforme
 * publicada pela Free Software Foundation; vers�o 2 da Licen�a.
 * Este programa � distribu�do na expectativa de ser �til, mas SEM
 * QUALQUER GARANTIA; sem mesmo a garantia impl�cita de
 * COMERCIALIZA��O ou de ADEQUA��O A QUALQUER PROP�SITO EM
 * PARTICULAR. 
 * Consulte a Licen�a P�blica Geral GNU para obter mais detalhes.
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU
 * junto com este programa; se n�o, escreva para Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 * 02111-1307, USA.
 */

package gcom.gui.faturamento.conta.contamotivo;

import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import gcom.fachada.Fachada;
import gcom.faturamento.conta.ContaMotivoInclusao;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;

public class AtualizarContaMotivoInclusaoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("telaSucesso");

		AtualizarContaMotivoInclusaoActionForm atualizarContaMotivoInclusaoActionForm = (AtualizarContaMotivoInclusaoActionForm) actionForm;

		ContaMotivoInclusao contaMotivoInclusao = new ContaMotivoInclusao();

		contaMotivoInclusao.setId(Integer.valueOf(atualizarContaMotivoInclusaoActionForm.getId()));
		contaMotivoInclusao.setDescricaoMotivoInclusaoConta(atualizarContaMotivoInclusaoActionForm.getDescricao());
		contaMotivoInclusao.setUltimaAlteracao(new Date());

		if(contaMotivoInclusao.getDescricaoMotivoInclusaoConta() == null
						|| "".equals(contaMotivoInclusao.getDescricaoMotivoInclusaoConta())){
			throw new ActionServletException("");
		}

		Fachada fachada = Fachada.getInstancia();
		fachada.atualizar(contaMotivoInclusao);

		montarPaginaSucesso(httpServletRequest, "Motivo de Inclusao de Conta de c�digo " + contaMotivoInclusao.getId().toString()
						+ " atualizado com sucesso.", "Realizar outra Manuten��o de Motivo de inclusao de Conta",
						"exibirFiltrarContaMotivoInclusaoAction.do?menu=sim");

		return retorno;
	}
}
