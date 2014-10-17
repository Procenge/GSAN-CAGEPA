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

import gcom.cadastro.cliente.Cliente;
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

/**
 * Action responsável por Inserir Empresa.
 * 
 * @author Hiroshi Gonçalves
 */
public class InserirEntidadeBeneficenteAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("telaSucesso");
		HttpSession sessao = httpServletRequest.getSession(false);
		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");
		InserirEntidadeBeneficenteActionForm form = (InserirEntidadeBeneficenteActionForm) actionForm;
		Fachada fachada = Fachada.getInstancia();

		String idCliente = form.getIdCliente();
		String idDebitoTipo = form.getIdDebitoTipo();

		// Verifica se a Entidade Beneficente já está cadastrada
		FiltroEntidadeBeneficente filtroEntidadeBeneficente = new FiltroEntidadeBeneficente();
		filtroEntidadeBeneficente.adicionarParametro(new ParametroSimples(FiltroEntidadeBeneficente.ID_CLIENTE, idCliente));
		filtroEntidadeBeneficente
						.adicionarParametro(new ParametroSimples(FiltroEntidadeBeneficente.INDICADOR_USO, ConstantesSistema.ATIVO));
		Collection colEntBeneficente = fachada.pesquisar(filtroEntidadeBeneficente, EntidadeBeneficente.class.getName());

		if(colEntBeneficente != null && !colEntBeneficente.isEmpty()){

			throw new ActionServletException("atencao.entidade_beneficente_ja_cadastrada");
		}

		// Verifica se o Cliente Existe
		Cliente cliente = (Cliente) fachada.pesquisar(Integer.parseInt(idCliente), Cliente.class);
		if(cliente == null){
			throw new ActionServletException("atencao.cliente.inexistente");
		}

		// Verifica se o Tipo de Débito existe
		DebitoTipo debitoTipo = (DebitoTipo) fachada.pesquisar(Integer.parseInt(idDebitoTipo), DebitoTipo.class);
		if(debitoTipo == null){
			throw new ActionServletException("atencao.pesquisa.debitoTipo.inexistente", idDebitoTipo);
		}

		EntidadeBeneficente entidadeBeneficente = new EntidadeBeneficente();
		entidadeBeneficente.setCliente(new Cliente(Integer.parseInt(idCliente)));
		entidadeBeneficente.setDebitoTipo(new DebitoTipo(Integer.parseInt(idDebitoTipo)));
		entidadeBeneficente.setIndicadorUso(ConstantesSistema.INDICADOR_USO_ATIVO);
		entidadeBeneficente.setUltimaAlteracao(new Date());

		Integer idEntidadeBeneficente = (Integer) fachada.inserir(entidadeBeneficente);

		montarPaginaSucesso(httpServletRequest, "Entidade Beneficente " + idCliente + " inserida com sucesso.",
						"Inserir outra Entidade Beneficente", "exibirInserirEntidadeBeneficenteAction.do?menu=sim",
						"exibirAtualizarEntidadeBeneficenteAction.do?idEntidadeBeneficente=" + idEntidadeBeneficente,
						"Atualizar Entidade Beneficente inserida");

		return retorno;

	}
}