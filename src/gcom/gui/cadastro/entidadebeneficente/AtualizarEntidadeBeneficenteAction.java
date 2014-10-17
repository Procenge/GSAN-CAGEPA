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
import gcom.faturamento.debito.DebitoTipo;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

/**
 * Classe respons�vel por atualizar uma Empresa.
 * 
 * @author Virg�nia Melo
 * @date: 06/08/08
 */
public class AtualizarEntidadeBeneficenteAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("telaSucesso");

		DynaActionForm form = (DynaActionForm) actionForm;
		Fachada fachada = Fachada.getInstancia();
		HttpSession sessao = httpServletRequest.getSession(false);

		// Usuario logado no sistema
		Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");

		EntidadeBeneficente entidadeBeneficente = (EntidadeBeneficente) sessao.getAttribute("entidadeBeneficenteAtualizar");
		String indicadorUsoAux = (String) httpServletRequest.getParameter("indicadorUsoAux");

		// Verifica se j� existe uma Entidade Beneficente Ativa, para os casos em que o indicador de
		// uso foi alterado de Inativo para Ativo
		if(indicadorUsoAux.equals(ConstantesSistema.INATIVO) && form.get("indicadorUso").toString().equals(ConstantesSistema.ATIVO)){

			FiltroEntidadeBeneficente filtroEntidadeBeneficente = new FiltroEntidadeBeneficente();
			filtroEntidadeBeneficente.adicionarParametro(new ParametroSimples(FiltroEntidadeBeneficente.ID_CLIENTE, entidadeBeneficente
							.getCliente().getId()));
			filtroEntidadeBeneficente.adicionarParametro(new ParametroSimples(FiltroEntidadeBeneficente.INDICADOR_USO,
							ConstantesSistema.ATIVO));
			Collection colEntBeneficente = fachada.pesquisar(filtroEntidadeBeneficente, EntidadeBeneficente.class.getName());

			if(colEntBeneficente != null && !colEntBeneficente.isEmpty()){
				throw new ActionServletException("atencao.entidade_beneficente_ja_cadastrada");
			}
		}

		entidadeBeneficente.setDebitoTipo(new DebitoTipo(Integer.parseInt(form.get("idDebitoTipo").toString())));
		entidadeBeneficente.setUltimaAlteracao(new Date());
		entidadeBeneficente.setIndicadorUso(Short.parseShort(form.get("indicadorUso").toString()));

		fachada.atualizar(entidadeBeneficente);

		sessao.removeAttribute("idEntidadeBeneficente");

		sessao.removeAttribute("entidadeBeneficenteAtualizar");

		montarPaginaSucesso(httpServletRequest, "Entidade Beneficente " + entidadeBeneficente.getCliente().getNome()
						+ " atualizada com sucesso.", "Manter outra Entidade Beneficente",
						"exibirFiltrarEntidadeBeneficenteAction.do?menu=sim");

		return retorno;

	}
}