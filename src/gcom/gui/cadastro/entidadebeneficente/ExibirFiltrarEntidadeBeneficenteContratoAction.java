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
 * Virgínia Melo
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

package gcom.gui.cadastro.entidadebeneficente;

import gcom.cadastro.imovel.EntidadeBeneficente;
import gcom.cadastro.imovel.FiltroEntidadeBeneficente;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ExibirFiltrarEntidadeBeneficenteContratoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("filtrarEntidadeBeneficenteContrato");

		FiltrarEntidadeBeneficenteContratoActionForm form = (FiltrarEntidadeBeneficenteContratoActionForm) actionForm;
		HttpSession sessao = httpServletRequest.getSession(false);
		Fachada fachada = Fachada.getInstancia();

		// Se não tiver nada setado em atualizar, o check começa marcado.
		if(form.getCheckAtualizar() == null){
			form.setCheckAtualizar("1");
		}

		/*** Define filtro pra pesquisar e alimenta a colecao de entidade beneficente ***/
		FiltroEntidadeBeneficente filtroEntidadeBeneficente = new FiltroEntidadeBeneficente(FiltroEntidadeBeneficente.CLIENTE_NOME);
		filtroEntidadeBeneficente.adicionarCaminhoParaCarregamentoEntidade("cliente");
		filtroEntidadeBeneficente
						.adicionarParametro(new ParametroSimples(FiltroEntidadeBeneficente.INDICADOR_USO, ConstantesSistema.ATIVO));
		Collection<EntidadeBeneficente> colecaoEntidadeBeneficente = fachada.pesquisar(filtroEntidadeBeneficente,
						EntidadeBeneficente.class.getName());

		/*** Valida se a coleção está preenchida ***/
		if(colecaoEntidadeBeneficente != null && colecaoEntidadeBeneficente.size() == 0){
			throw new ActionServletException("atencao.naocadastrado", null, "Entidade Beneficente");
		}

		/*** Seta colecao de entidade beneficente na sessão ***/
		sessao.setAttribute("colecaoEntidadeBeneficente", colecaoEntidadeBeneficente);

		// Caso o usuário tenha clicado no botão limpar.
		if(httpServletRequest.getParameter("desfazer") != null && httpServletRequest.getParameter("desfazer").equalsIgnoreCase("S")){

			form.setIdEntidadeBeneficente("");
			form.setNumeroContrato("");
			form.setDataInicialInicioContrato("");
			form.setDataFinalInicioContrato("");
			form.setDataInicioFimContrato("");
			form.setDataFinalFimContrato("");
		}

		return retorno;
	}

}
