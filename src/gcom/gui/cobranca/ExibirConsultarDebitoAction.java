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
 * Adriano Britto Siqueira
 * Alexandre Santos Cabral
 * Ana Carolina Alves Breda
 * Ana Maria Andrade Cavalcante
 * Aryed Lins de Araújo
 * Bruno Leonardo Rodrigues Barros
 * Carlos Elmano Rodrigues Ferreira
 * Cláudio de Andrade Lira
 * Denys Guimarães Guenes Tavares
 * Eduardo Breckenfeld da Rosa Borges
 * Fabíola Gomes de Araújo
 * Flávio Leonardo Cavalcanti Cordeiro
 * Francisco do Nascimento Júnior
 * Homero Sampaio Cavalcanti
 * Ivan Sérgio da Silva Júnior
 * José Edmar de Siqueira
 * José Thiago Tenório Lopes
 * Kássia Regina Silvestre de Albuquerque
 * Leonardo Luiz Vieira da Silva
 * Márcio Roberto Batista da Silva
 * Maria de Fátima Sampaio Leite
 * Micaela Maria Coelho de Araújo
 * Nelson Mendonça de Carvalho
 * Newton Morais e Silva
 * Pedro Alexandre Santos da Silva Filho
 * Rafael Corrêa Lima e Silva
 * Rafael Francisco Pinto
 * Rafael Koury Monteiro
 * Rafael Palermo de Araújo
 * Raphael Veras Rossiter
 * Roberto Sobreira Barbalho
 * Rodrigo Avellar Silveira
 * Rosana Carvalho Barbosa
 * Sávio Luiz de Andrade Cavalcante
 * Tai Mu Shih
 * Thiago Augusto Souza do Nascimento
 * Tiago Moreno Rodrigues
 * Vivianne Barbosa Sousa
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

package gcom.gui.cobranca;

import gcom.cadastro.cliente.*;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.faturamento.ParametroFaturamento;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Description of the Class
 * 
 * @author Fernanda Paiva
 * @created 10 de Janeiro de 2006
 */
public class ExibirConsultarDebitoAction
				extends GcomAction {

	/**
	 * Description of the Method
	 * 
	 * @param actionMapping
	 *            Description of the Parameter
	 * @param actionForm
	 *            Description of the Parameter
	 * @param httpServletRequest
	 *            Description of the Parameter
	 * @param httpServletResponse
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("consultarDebito");

		// Mudar isso quando tiver esquema de segurança
		HttpSession sessao = httpServletRequest.getSession(false);

		ConsultarDebitoActionForm consultarDebitoActionForm = (ConsultarDebitoActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		Boolean indicadorFauramentoTitularDebito = false;
		try{
			if(ParametroFaturamento.P_INDICADOR_FATURAMENTO_ATUAL_TITULAR_DEBITO_IMOVEL.executar().equals("1")){
				indicadorFauramentoTitularDebito = true;
			}
		}catch(ControladorException e){
			e.printStackTrace();
		}

		Collection<ClienteImovel> colecaoRelacaoImovel = new ArrayList<ClienteImovel>();
		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");

		// Remove as coleções e os valores da sessão
		sessao.removeAttribute("colecaoContaValores");
		sessao.removeAttribute("valorConta");
		sessao.removeAttribute("valorAcrescimo");
		sessao.removeAttribute("valorAgua");
		sessao.removeAttribute("valorEsgoto");
		sessao.removeAttribute("valorDebito");
		sessao.removeAttribute("valorCredito");
		sessao.removeAttribute("valorContaAcrescimo");
		sessao.removeAttribute("colecaoDebitoACobrar");
		sessao.removeAttribute("valorDebitoACobrar");
		sessao.removeAttribute("colecaoCreditoARealizar");
		sessao.removeAttribute("valorCreditoARealizar");
		sessao.removeAttribute("colecaoGuiaPagamentoValores");
		sessao.removeAttribute("valorGuiaPagamento");
		sessao.removeAttribute("valorTotalSemAcrescimo");
		sessao.removeAttribute("valorTotalComAcrescimo");
		sessao.removeAttribute("colecaoDebitoCliente");
		sessao.removeAttribute("tipoRelacao");
		sessao.removeAttribute("valorImposto");

		String idDigitadoEnterCliente = (String) consultarDebitoActionForm.getCodigoCliente();
		String idDigitadoEnterImovel = (String) consultarDebitoActionForm.getCodigoImovel();
		String idDigitadoEnterClienteSuperior = (String) consultarDebitoActionForm.getCodigoClienteSuperior();

		// verifica se o codigo do cliente foi digitado
		if(!Util.isVazioOuBranco(idDigitadoEnterCliente) && Integer.parseInt(idDigitadoEnterCliente) > 0){

			FiltroCliente filtroCliente = new FiltroCliente();

			filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.ID, idDigitadoEnterCliente));
			filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));

			Collection clienteEncontrado = fachada.pesquisar(filtroCliente, Cliente.class.getName());

			if(!Util.isVazioOrNulo(clienteEncontrado)){
				// O Cliente foi encontrado
				if(((Cliente) ((List) clienteEncontrado).get(0)).getIndicadorUso().equals(ConstantesSistema.INDICADOR_USO_DESATIVO)){
					throw new ActionServletException("atencao.cliente.inativo", null, ""
									+ ((Cliente) ((List) clienteEncontrado).get(0)).getId());
				}

				consultarDebitoActionForm.setCodigoCliente(((Cliente) ((List) clienteEncontrado).get(0)).getId().toString());
				consultarDebitoActionForm.setCodigoClienteClone(((Cliente) ((List) clienteEncontrado).get(0)).getId().toString());
				consultarDebitoActionForm.setNomeCliente(((Cliente) ((List) clienteEncontrado).get(0)).getNome());

			}else{
				httpServletRequest.setAttribute("corCliente", "exception");
				consultarDebitoActionForm.setNomeCliente(ConstantesSistema.CODIGO_CLIENTE_INEXISTENTE);

			}
		}

		// verifica se o codigo do cliente superior foi digitado
		if(!Util.isVazioOuBranco(idDigitadoEnterClienteSuperior) && Integer.parseInt(idDigitadoEnterClienteSuperior) > 0){

			FiltroCliente filtroCliente = new FiltroCliente();

			filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.ID, idDigitadoEnterClienteSuperior));
			filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));

			Collection clienteEncontrado = fachada.pesquisar(filtroCliente, Cliente.class.getName());

			if(!Util.isVazioOrNulo(clienteEncontrado)){
				// O Cliente foi encontrado
				if(((Cliente) ((List) clienteEncontrado).get(0)).getIndicadorUso().equals(ConstantesSistema.INDICADOR_USO_DESATIVO)){
					throw new ActionServletException("atencao.cliente.inativo", null, ""
									+ ((Cliente) ((List) clienteEncontrado).get(0)).getId());
				}

				consultarDebitoActionForm.setCodigoClienteSuperior(((Cliente) ((List) clienteEncontrado).get(0)).getId().toString());
				consultarDebitoActionForm.setCodigoClienteSuperiorClone(((Cliente) ((List) clienteEncontrado).get(0)).getId().toString());
				consultarDebitoActionForm.setNomeClienteSuperior(((Cliente) ((List) clienteEncontrado).get(0)).getNome());

			}else{
				httpServletRequest.setAttribute("corClienteSuperior", "exception");
				consultarDebitoActionForm.setNomeClienteSuperior(ConstantesSistema.CODIGO_CLIENTE_INEXISTENTE);

			}
		}

		if(!Util.isVazioOuBranco(idDigitadoEnterImovel)){
			// Pesquisa o imovel na base
			String imovelEncontrado = fachada.pesquisarInscricaoImovel(Integer.valueOf(idDigitadoEnterImovel), true);

			if(!Util.isVazioOuBranco(imovelEncontrado)){

				// O imovel foi encontrado
				consultarDebitoActionForm.setCodigoImovel(idDigitadoEnterImovel);
				consultarDebitoActionForm.setCodigoImovelClone(idDigitadoEnterImovel);
				consultarDebitoActionForm.setInscricaoImovel(imovelEncontrado);

				colecaoRelacaoImovel = fachada.obterListaClientesRelacaoDevedor(Integer.valueOf(idDigitadoEnterImovel),
								Integer.valueOf("000101"), Integer.valueOf("999912"), 1, 1, 1, 1, 1, 1, 1, null, ConstantesSistema.SIM,
								ConstantesSistema.SIM, ConstantesSistema.SIM, 2, null, null);
			}else{
				httpServletRequest.setAttribute("corImovel", "exception");
				consultarDebitoActionForm.setInscricaoImovel(ConstantesSistema.CODIGO_IMOVEL_INEXISTENTE);
			}
		}

		FiltroClienteRelacaoTipo filtroClienteRelacaoTipo = new FiltroClienteRelacaoTipo();

		filtroClienteRelacaoTipo.adicionarParametro(new ParametroSimples(FiltroClienteRelacaoTipo.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));

		Collection<ClienteRelacaoTipo> collectionClienteRelacaoTipo = fachada.pesquisar(filtroClienteRelacaoTipo, ClienteRelacaoTipo.class
						.getName());

		if(!Util.isVazioOrNulo(collectionClienteRelacaoTipo)){
			httpServletRequest.setAttribute("collectionClienteRelacaoTipo", collectionClienteRelacaoTipo);
		}else{
			throw new ActionServletException("atencao.collectionClienteRelacaoTipo_inexistente", null, "id");
		}

		if(indicadorFauramentoTitularDebito){
			httpServletRequest.setAttribute("indicadorFauramentoTitularDebito", "S");
		}else{
			httpServletRequest.removeAttribute("indicadorFauramentoTitularDebito");
		}

		sessao.setAttribute("colecaoRelacaoImovel", colecaoRelacaoImovel);

		return retorno;
	}
}
