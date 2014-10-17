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

package gcom.gui.relatorio.arrecadacao.pagamento;

import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.cliente.ClienteTipo;
import gcom.cadastro.cliente.FiltroCliente;
import gcom.cadastro.cliente.FiltroClienteTipo;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import br.com.procenge.geradorrelatorio.impl.validador.DebitoResponsavelValidadorImpl;

/**
 * Classe na qual ir� exibir a tela para gerar o relat�rio de D�bito por Respons�vel.
 * 
 * @author Carlos Chrystian.
 * @date 16/09/2012
 */
public class ExibirGerarRelatorioTabulacaoPagamentosPorResponsavelAction
				extends GcomAction {

	private static final String NOME_CLIENTE_INICIAL = "nomeClienteInicial";

	private static final String NOME_CLIENTE_FINAL = "nomeClienteFinal";

	private static final Integer CLIENTE_INICIAL = Integer.valueOf(1);

	private static final Integer CLIENTE_FINAL = Integer.valueOf(2);

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("exibirGerarRelatorioTabulacaoPagamentosPorResponsavelAction");

		DynaActionForm dynaForm = (DynaActionForm) actionForm;

		// Pesquisa todos os tipos dos clientes
		this.pesquisarTiposClientes(httpServletRequest);

		// Flag indicando que o usu�rio fez uma consulta a partir da tecla Enter
		String objetoConsultaStr = httpServletRequest.getParameter("objetoConsulta");

		Integer objetoConsulta = null;
		if(objetoConsultaStr != null){
			objetoConsulta = Integer.valueOf(objetoConsultaStr);
		}

		if(objetoConsulta != null){
			if((objetoConsulta.equals(CLIENTE_INICIAL)) || (objetoConsulta.equals(CLIENTE_FINAL))){
				pesquisarCliente(dynaForm, objetoConsulta);
			}
		}

		// Habilita ou Desabilita o combo de Cliente Inicial
		String clienteInicial = (String) dynaForm.get(DebitoResponsavelValidadorImpl.P_CLIENTE_INICIAL);
		String disabledClienteInicial = "false";

		if(!Util.isVazioOuBranco(clienteInicial)){
			disabledClienteInicial = "true";
		}
		httpServletRequest.setAttribute("disabledClienteInicial", disabledClienteInicial);

		// Habilita ou Desabilita o combo de Cliente Final
		String clienteFinal = (String) dynaForm.get(DebitoResponsavelValidadorImpl.P_CLIENTE_FINAL);
		String disabledClienteFinal = "false";

		if(!Util.isVazioOuBranco(clienteFinal)){
			disabledClienteFinal = "true";
		}
		httpServletRequest.setAttribute("disabledClienteFinal", disabledClienteFinal);
		return retorno;
	}

	/**
	 * M�todo que pesquisa todos os tipos de clientes.
	 * 
	 * @author Carlos Chrystian.
	 * @date 16/09/2012
	 * @param request
	 */
	private void pesquisarTiposClientes(HttpServletRequest request){

		// Fachada
		Fachada fachada = Fachada.getInstancia();

		// Pesquisa os Tipos de Clientes para a p�gina
		FiltroClienteTipo filtroClienteTipo = new FiltroClienteTipo();
		filtroClienteTipo.adicionarParametro(new ParametroSimples(FiltroClienteTipo.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));
		filtroClienteTipo.setCampoOrderBy(FiltroClienteTipo.DESCRICAO);

		// Seta a cole��o para View.
		request.setAttribute("colecaoTiposClientes", fachada.pesquisar(filtroClienteTipo, ClienteTipo.class.getName()));

	}

	/**
	 * M�todo que pesquisa o cliente e replica o valor do inicial para o final tamb�m.
	 * 
	 * @author Carlos Chrystian.
	 * @date 16/09/2012
	 * @param form
	 * @param objetoConsulta
	 */
	private void pesquisarCliente(DynaActionForm form, Integer objetoConsulta){

		Integer clienteId = null;
		String clienteIdStr = null;
		Cliente cliente = null;

		// Recupera o id do cliente de acordo com a vari�vel objetoConsulta, que indica qual
		// campo do formul�rio foi informado para pesquisa
		if(objetoConsulta.equals(CLIENTE_INICIAL)){
			clienteIdStr = (String) form.get(DebitoResponsavelValidadorImpl.P_CLIENTE_INICIAL);
		}else if(objetoConsulta.equals(CLIENTE_FINAL)){
			clienteIdStr = (String) form.get(DebitoResponsavelValidadorImpl.P_CLIENTE_FINAL);
		}

		if(!Util.isVazioOuBranco(clienteIdStr)){
			clienteId = Integer.valueOf(clienteIdStr);

			FiltroCliente filtroCliente = new FiltroCliente();
			filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.ID, clienteId));

			// Identifica quais as propriedades do formul�rio que ser�o atualizadas
			String campoClienteId = DebitoResponsavelValidadorImpl.P_CLIENTE_INICIAL;
			String campoClienteNome = NOME_CLIENTE_INICIAL;

			if(objetoConsulta.equals(CLIENTE_FINAL)){
				campoClienteId = DebitoResponsavelValidadorImpl.P_CLIENTE_FINAL;
				campoClienteNome = NOME_CLIENTE_FINAL;
			}

			// Recupera Cliente para preencher o formul�rio
			Collection colecaoCliente = this.getFachada().pesquisar(filtroCliente, Cliente.class.getName());

			if(colecaoCliente != null && !colecaoCliente.isEmpty()){
				cliente = (Cliente) Util.retonarObjetoDeColecao(colecaoCliente);
			}

			// Se encontrou a cliente, preenche as propriedades do formul�rio
			if(cliente != null){
				form.set(campoClienteId, cliente.getId().toString());
				form.set(campoClienteNome, cliente.getDescricao());
			}else{
				form.set(campoClienteId, null);
				form.set(campoClienteNome, "Cliente inexistente");
			}

			// Replica para a localidade final
			if(objetoConsulta.equals(CLIENTE_INICIAL)){
				if(cliente != null){
					form.set(DebitoResponsavelValidadorImpl.P_CLIENTE_FINAL, cliente.getId().toString());
					form.set(NOME_CLIENTE_FINAL, cliente.getDescricao());
				}else{
					form.set(DebitoResponsavelValidadorImpl.P_CLIENTE_FINAL, null);
					form.set(NOME_CLIENTE_FINAL, "Cliente inexistente");
				}
			}

		}
	}

}
