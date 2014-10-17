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

import gcom.cadastro.cliente.Cliente;
import gcom.fachada.Fachada;
import gcom.faturamento.debito.DebitoTipo;
import gcom.gui.GcomAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action respons�vel pela pre-exibi��o da p�gina de Inserir Empresa
 * 
 * @author Hiroshi Gon�alves
 * @created 03/02/2014
 */
public class ExibirInserirEntidadeBeneficenteAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("inserirEntidadeBeneficente");
		InserirEntidadeBeneficenteActionForm form = (InserirEntidadeBeneficenteActionForm) actionForm;
		Fachada fachada = Fachada.getInstancia();

		String idCliente = (String) form.getIdCliente();
		String idDebitoTipo = (String) form.getIdDebitoTipo();

		// Verifica se o cliente existe e recupera o nome
		form.setNomeCliente("");
		if(idCliente != null && !idCliente.equals("")){
			Cliente cliente = fachada.pesquisarClienteDigitado(Integer.parseInt(idCliente));

			if(cliente != null){
				form.setNomeCliente(cliente.getNome());
				httpServletRequest.setAttribute("corFonteCliente", "#000000");
			} else {
				form.setNomeCliente("CLIENTE INEXISTENTE");
				httpServletRequest.setAttribute("corFonteCliente", "#ff0000");
				// throw new ActionServletException("atencao.pesquisa.cliente.inexistente", null,
				// idCliente);
			}
		}

		// Verifica se o debito tipo existe e recupera a descricao
		form.setDescricaoDebitoTipo("");
		if(idDebitoTipo != null && !idDebitoTipo.equals("")){
			DebitoTipo debitoTipo = fachada.pesquisarTipoDebitoDigitado(Integer.parseInt(idDebitoTipo));

			if(debitoTipo != null){
				form.setDescricaoDebitoTipo(debitoTipo.getDescricao());
				httpServletRequest.setAttribute("corFonteDebitoTipo", "#000000");
			}else{
				form.setDescricaoDebitoTipo("TIPO DE DEBITO INEXISTENTE");
				httpServletRequest.setAttribute("corFonteDebitoTipo", "#ff0000");
				// throw new ActionServletException("atencao.pesquisa.debitoTipo.inexistente", null,
				// idDebitoTipo);
			}

		}

		// if(idDigitadoEnterEmpresa != null && !idDigitadoEnterEmpresa.trim().equals("")){
		// FiltroEntidadeBeneficente filtro = new FiltroEntidadeBeneficente();
		// filtro.adicionarParametro(new ParametroSimples(FiltroEntidadeBeneficente.ID,
		// idDigitadoEnterEmpresa));
		//
		// Collection codigoEmpresaEncontrado = fachada.pesquisar(filtro,
		// FiltroEntidadeBeneficente.class.getName());
		//
		// if(codigoEmpresaEncontrado != null && !codigoEmpresaEncontrado.isEmpty()){
		// form.setCodigoEmpresa("");
		// httpServletRequest.setAttribute("corEmpresa", "exception");
		// form.setMensagemEmpresa(ConstantesSistema.CODIGO_EMPRESA_JA_CADASTRADO);
		// form.setOkEmpresa("false");
		// }else{
		// form.setOkEmpresa("true");
		// }
		// }else{
		// // Buscar o pr�ximo id para cadastrar uma entidade beneficente.
		// int proximoID = fachada.pesquisarProximoIdEmpresa();
		// form.setCodigoEmpresa(Integer.toString(proximoID));
		// }

		return retorno;
	}
}
