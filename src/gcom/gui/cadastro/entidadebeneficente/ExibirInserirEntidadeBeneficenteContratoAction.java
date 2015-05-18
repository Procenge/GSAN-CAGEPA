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
 *
 * GSAN � Sistema Integrado de Gest�o de Servi�os de Saneamento
 * Copyright (C) <2007> 
 *
 * GSANPCG
 * Virg�nia Melo
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

/**
 * Action respons�vel pela pre-exibi��o da p�gina de Inserir Empresa
 * 
 * @author Hiroshi Gon�alves
 * @created 03/02/2014
 */
public class ExibirInserirEntidadeBeneficenteContratoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("inserirEntidadeBeneficenteContrato");
		InserirEntidadeBeneficenteContratoActionForm form = (InserirEntidadeBeneficenteContratoActionForm) actionForm;
		Fachada fachada = Fachada.getInstancia();
		HttpSession sessao = httpServletRequest.getSession(false);

		/*** Define filtro pra pesquisar e alimenta a colecao de entidade beneficente ***/
		FiltroEntidadeBeneficente filtroEntidadeBeneficente = new FiltroEntidadeBeneficente(FiltroEntidadeBeneficente.CLIENTE_NOME);
		filtroEntidadeBeneficente.adicionarCaminhoParaCarregamentoEntidade("cliente");
		filtroEntidadeBeneficente
						.adicionarParametro(new ParametroSimples(FiltroEntidadeBeneficente.INDICADOR_USO, ConstantesSistema.ATIVO));
		Collection<EntidadeBeneficente> colecaoEntidadeBeneficente = fachada.pesquisar(filtroEntidadeBeneficente,
						EntidadeBeneficente.class.getName());

		/*** Valida se a cole��o est� preenchida ***/
		if(colecaoEntidadeBeneficente != null && colecaoEntidadeBeneficente.size() == 0){
			throw new ActionServletException("atencao.naocadastrado", null, "Entidade Beneficente");
		}

		/*** Seta colecao de entidade beneficente na sess�o ***/
		sessao.setAttribute("colecaoEntidadeBeneficente", colecaoEntidadeBeneficente);

		if(httpServletRequest.getParameter("desfazer") != null && httpServletRequest.getParameter("desfazer").equalsIgnoreCase("S")){
			form.setIdEntidadeBeneficente("");
			form.setNomeEntidadeBeneficente("");
			form.setDataInicioContrato("");
			form.setDataFimContrato("");
			form.setDataEncerramento("");
			form.setPercentualRemuneracao("");
			form.setValorMinimoDoacao("");
			form.setValorRemuneracao("");
			form.setValorMaximoDoacao("");
			form.setNumeroContrato("");
		}

		return retorno;
	}
}
