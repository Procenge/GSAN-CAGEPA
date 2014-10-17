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
 * Saulo Lima
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

package gcom.gui.atendimentopublico.ordemservico;

import gcom.atendimentopublico.ordemservico.ServicoAssociado;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class PesquisarRemoverServicoTipoAssociadoAction
				extends GcomAction {

	/**
	 * Este caso de uso permite a remoção de um serviço tipo associado.
	 * [UC0410] Inserir Serviço Tipo
	 * 
	 * @author Saulo Lima
	 * @date 15/05/2009
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("inserirServicoTipo");
		HttpSession sessao = httpServletRequest.getSession(false);

		// Pega o ID do Tipo de Serviço selecionado para remover
		String idServicoAssociadoRemover = httpServletRequest.getParameter("idServicoAssociadoRemover");

		// Pegando a coleção de objetos da sessão
		Collection<ServicoAssociado> colecaoServicoAssociado = null;
		if(sessao.getAttribute("colecaoServicoAssociado") != null){
			colecaoServicoAssociado = (ArrayList<ServicoAssociado>) sessao.getAttribute("colecaoServicoAssociado");
		}else{
			throw new ActionServletException("atencao.campo.informado", null, "Serviço Associado");
		}

		if(idServicoAssociadoRemover != null && !idServicoAssociadoRemover.trim().equals("")){
			if(colecaoServicoAssociado.isEmpty()){
				throw new ActionServletException("atencao.campo.informado", null, "Serviço Associado");
			}else{

				Iterator<ServicoAssociado> servicoAssociadoIterator = colecaoServicoAssociado.iterator();
				while(servicoAssociadoIterator.hasNext()){
					ServicoAssociado servicoAssociadoTemp = servicoAssociadoIterator.next();
					if(servicoAssociadoTemp.getServicoTipoAssociado().getId().toString().equals(idServicoAssociadoRemover)){
						servicoAssociadoIterator.remove();
					}
				}
				sessao.setAttribute("colecaoServicoAssociado", colecaoServicoAssociado);
			}

		}else{
			throw new ActionServletException("atencao.campo.informado", null, "Serviço Associado");
		}

		return retorno;
	}

}
