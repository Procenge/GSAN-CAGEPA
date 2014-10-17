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
 * Saulo Lima
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
	 * Este caso de uso permite a remo��o de um servi�o tipo associado.
	 * [UC0410] Inserir Servi�o Tipo
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

		// Pega o ID do Tipo de Servi�o selecionado para remover
		String idServicoAssociadoRemover = httpServletRequest.getParameter("idServicoAssociadoRemover");

		// Pegando a cole��o de objetos da sess�o
		Collection<ServicoAssociado> colecaoServicoAssociado = null;
		if(sessao.getAttribute("colecaoServicoAssociado") != null){
			colecaoServicoAssociado = (ArrayList<ServicoAssociado>) sessao.getAttribute("colecaoServicoAssociado");
		}else{
			throw new ActionServletException("atencao.campo.informado", null, "Servi�o Associado");
		}

		if(idServicoAssociadoRemover != null && !idServicoAssociadoRemover.trim().equals("")){
			if(colecaoServicoAssociado.isEmpty()){
				throw new ActionServletException("atencao.campo.informado", null, "Servi�o Associado");
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
			throw new ActionServletException("atencao.campo.informado", null, "Servi�o Associado");
		}

		return retorno;
	}

}
