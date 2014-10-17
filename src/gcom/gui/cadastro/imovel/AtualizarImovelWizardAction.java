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

package gcom.gui.cadastro.imovel;

import gcom.gui.WizardAction;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Description of the Class
 * 
 * @author Rossiter
 */
public class AtualizarImovelWizardAction
				extends WizardAction {

	// MÉTODOS DE EXIBIÇÃO
	// =======================================================

	/**
	 * < <Descrição do método>>
	 * 
	 * @param actionMapping
	 *            Descrição do parâmetro
	 * @param actionForm
	 *            Descrição do parâmetro
	 * @param httpServletRequest
	 *            Descrição do parâmetro
	 * @param httpServletResponse
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 */
	public ActionForward adicionarAtualizarImovelClienteAction(ActionMapping actionMapping, ActionForm actionForm,
					HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){

		// recebe o parametros e consulta o objeto da sessao para chamar outro
		// metodo desta classe
		// Seta um atributo na sessao para ser utilizado nas abas
		httpServletRequest.getSession().setAttribute("nomeUnicoWizard", ConstantesSistema.NOME_WIZARD_ALTERAR_IMOVEL);
		return new AdicionarAtualizarImovelClienteAction().execute(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}

	/**
	 * < <Descrição do método>>
	 * 
	 * @param actionMapping
	 *            Descrição do parâmetro
	 * @param actionForm
	 *            Descrição do parâmetro
	 * @param httpServletRequest
	 *            Descrição do parâmetro
	 * @param httpServletResponse
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 */
	public ActionForward exibirAtualizarImovelLocalidadeAction(ActionMapping actionMapping, ActionForm actionForm,
					HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){


		// HttpSession sessao = httpServletRequest.getSession(false);
		// Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");
		// Fachada fachada = Fachada.getInstancia();

		// // desvia para a aba de endereço caso o usuário não tenha permissão de acessar a aba de
		// // localidade
		// if(!fachada.verificarPermissaoFuncionalidadeUsuario(usuario.getId(),
		// "atualizarImovelLocalidadeAction.do",
		// "exibirFiltrarImovelAction.do")){
		// return new ExibirAtualizarImovelEnderecoAction().execute(actionMapping, actionForm,
		// httpServletRequest, httpServletResponse);
		// }

		// recebe o parametros e consulta o objeto da sessao para chamar outro
		// metodo desta classe
		// Seta um atributo na sessao para ser utilizado nas abas
		httpServletRequest.getSession().setAttribute("nomeUnicoWizard", ConstantesSistema.NOME_WIZARD_ALTERAR_IMOVEL);
		// AdicionarAtualizarImovelClienteAction.atualizarColecaoClienteImovelSessao(httpServletRequest);
		return new ExibirAtualizarImovelLocalidadeAction().execute(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}

	/**
	 * < <Descrição do método>>
	 * 
	 * @param actionMapping
	 *            Descrição do parâmetro
	 * @param actionForm
	 *            Descrição do parâmetro
	 * @param httpServletRequest
	 *            Descrição do parâmetro
	 * @param httpServletResponse
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 */
	public ActionForward exibirAtualizarImovelEnderecoAction(ActionMapping actionMapping, ActionForm actionForm,
					HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){

		// Seta um atributo na sessao para ser utilizado nas abas
		httpServletRequest.getSession().setAttribute("nomeUnicoWizard", ConstantesSistema.NOME_WIZARD_ALTERAR_IMOVEL);
		// AdicionarAtualizarImovelClienteAction.atualizarColecaoClienteImovelSessao(httpServletRequest);
		return new ExibirAtualizarImovelEnderecoAction().execute(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}

	/**
	 * Description of the Method
	 */
	/**
	 * < <Descrição do método>>
	 * 
	 * @param actionMapping
	 *            Descrição do parâmetro
	 * @param actionForm
	 *            Descrição do parâmetro
	 * @param httpServletRequest
	 *            Descrição do parâmetro
	 * @param httpServletResponse
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 */
	public ActionForward exibirAtualizarImovelPrincipalAction(ActionMapping actionMapping, ActionForm actionForm,
					HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){

		// recebe o parametros e consulta o objeto da sessao para chamar outro
		// metodo desta classe
		// Seta um atributo na sessao para ser utilizado nas abas
		httpServletRequest.getSession().setAttribute("nomeUnicoWizard", ConstantesSistema.NOME_WIZARD_ALTERAR_IMOVEL);

		return new ExibirAtualizarImovelPrincipalAction().execute(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}

	/**
	 * < <Descrição do método>>
	 * 
	 * @param actionMapping
	 *            Descrição do parâmetro
	 * @param actionForm
	 *            Descrição do parâmetro
	 * @param httpServletRequest
	 *            Descrição do parâmetro
	 * @param httpServletResponse
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 */
	public ActionForward exibirAtualizarImovelClienteAction(ActionMapping actionMapping, ActionForm actionForm,
					HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){

		// Seta um atributo na sessao para ser utilizado nas abas
		httpServletRequest.getSession().setAttribute("nomeUnicoWizard", ConstantesSistema.NOME_WIZARD_ALTERAR_IMOVEL);

		return new ExibirAtualizarImovelClienteAction().execute(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}

	/**
	 * < <Descrição do método>>
	 * 
	 * @param actionMapping
	 *            Descrição do parâmetro
	 * @param actionForm
	 *            Descrição do parâmetro
	 * @param httpServletRequest
	 *            Descrição do parâmetro
	 * @param httpServletResponse
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 * @throws ControladorException
	 */
	public ActionForward exibirAtualizarImovelSubCategoriaAction(ActionMapping actionMapping, ActionForm actionForm,
					HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ControladorException{

		// recebe o parametros e consulta o objeto da sessao para chamar outro
		// metodo desta classe

		// Seta um atributo na sessao para ser utilizado nas abas
		httpServletRequest.getSession().setAttribute("nomeUnicoWizard", ConstantesSistema.NOME_WIZARD_ALTERAR_IMOVEL);

		return new ExibirAtualizarImovelSubCategoriaAction().execute(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}

	/**
	 * < <Descrição do método>>
	 * 
	 * @param actionMapping
	 *            Descrição do parâmetro
	 * @param actionForm
	 *            Descrição do parâmetro
	 * @param httpServletRequest
	 *            Descrição do parâmetro
	 * @param httpServletResponse
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 */
	public ActionForward exibirAtualizarImovelCaracteristicasAction(ActionMapping actionMapping, ActionForm actionForm,
					HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){

		// Seta um atributo na sessao para ser utilizado nas abas
		httpServletRequest.getSession().setAttribute("nomeUnicoWizard", ConstantesSistema.NOME_WIZARD_ALTERAR_IMOVEL);

		return new ExibirAtualizarImovelCaracteristicasAction().execute(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}

	/**
	 * < <Descrição do método>>
	 * 
	 * @param actionMapping
	 *            Descrição do parâmetro
	 * @param actionForm
	 *            Descrição do parâmetro
	 * @param httpServletRequest
	 *            Descrição do parâmetro
	 * @param httpServletResponse
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 */
	public ActionForward exibirAtualizarImovelConclusaoAction(ActionMapping actionMapping, ActionForm actionForm,
					HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){

		// Seta um atributo na sessao para ser utilizado nas abas
		httpServletRequest.getSession().setAttribute("nomeUnicoWizard", ConstantesSistema.NOME_WIZARD_ALTERAR_IMOVEL);

		return new ExibirAtualizarImovelConclusaoAction().execute(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}

	// FIM DOS MÉTODOS DE EXIBIÇÃO =============================================
	// ==========================================================================

	// MÉTODOS DE INSERÇÃO =====================================================

	/**
	 * < <Descrição do método>>
	 * 
	 * @param actionMapping
	 *            Descrição do parâmetro
	 * @param actionForm
	 *            Descrição do parâmetro
	 * @param httpServletRequest
	 *            Descrição do parâmetro
	 * @param httpServletResponse
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 */
	public ActionForward atualizarImovelLocalidadeAction(ActionMapping actionMapping, ActionForm actionForm,
					HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){

		// Seta um atributo na sessao para ser utilizado nas abas
		httpServletRequest.getSession().setAttribute("nomeUnicoWizard", ConstantesSistema.NOME_WIZARD_ALTERAR_IMOVEL);

		new AtualizarImovelLocalidadeAction().execute(actionMapping, actionForm, httpServletRequest, httpServletResponse);
		return this.redirecionadorWizard(actionMapping, actionForm, httpServletRequest, httpServletResponse,
						ConstantesSistema.NOME_WIZARD_ALTERAR_IMOVEL);
	}

	/**
	 * < <Descrição do método>>
	 * 
	 * @param actionMapping
	 *            Descrição do parâmetro
	 * @param actionForm
	 *            Descrição do parâmetro
	 * @param httpServletRequest
	 *            Descrição do parâmetro
	 * @param httpServletResponse
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 */
	public ActionForward atualizarImovelEnderecoAction(ActionMapping actionMapping, ActionForm actionForm,
					HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){

		// Seta um atributo na sessao para ser utilizado nas abas
		httpServletRequest.getSession().setAttribute("nomeUnicoWizard", ConstantesSistema.NOME_WIZARD_ALTERAR_IMOVEL);

		new AtualizarImovelEnderecoAction().execute(actionMapping, actionForm, httpServletRequest, httpServletResponse);
		return this.redirecionadorWizard(actionMapping, actionForm, httpServletRequest, httpServletResponse,
						ConstantesSistema.NOME_WIZARD_ALTERAR_IMOVEL);
	}

	/**
	 * < <Descrição do método>>
	 * 
	 * @param actionMapping
	 *            Descrição do parâmetro
	 * @param actionForm
	 *            Descrição do parâmetro
	 * @param httpServletRequest
	 *            Descrição do parâmetro
	 * @param httpServletResponse
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 */
	public ActionForward atualizarImovelPrincipalAction(ActionMapping actionMapping, ActionForm actionForm,
					HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){

		// Seta um atributo na sessao para ser utilizado nas abas
		httpServletRequest.getSession().setAttribute("nomeUnicoWizard", ConstantesSistema.NOME_WIZARD_ALTERAR_IMOVEL);

		new AtualizarImovelPrincipalAction().execute(actionMapping, actionForm, httpServletRequest, httpServletResponse);
		return this.redirecionadorWizard(actionMapping, actionForm, httpServletRequest, httpServletResponse,
						ConstantesSistema.NOME_WIZARD_ALTERAR_IMOVEL);
	}

	/**
	 * < <Descrição do método>>
	 * 
	 * @param actionMapping
	 *            Descrição do parâmetro
	 * @param actionForm
	 *            Descrição do parâmetro
	 * @param httpServletRequest
	 *            Descrição do parâmetro
	 * @param httpServletResponse
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 */
	public ActionForward atualizarImovelClienteAction(ActionMapping actionMapping, ActionForm actionForm,
					HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){

		// Seta um atributo na sessao para ser utilizado nas abas
		httpServletRequest.getSession().setAttribute("nomeUnicoWizard", ConstantesSistema.NOME_WIZARD_ALTERAR_IMOVEL);
		// AdicionarAtualizarImovelClienteAction.atualizarColecaoClienteImovelSessao(httpServletRequest);
		new AtualizarImovelClienteAction().execute(actionMapping, actionForm, httpServletRequest, httpServletResponse);
		return this.redirecionadorWizard(actionMapping, actionForm, httpServletRequest, httpServletResponse,
						ConstantesSistema.NOME_WIZARD_ALTERAR_IMOVEL);
	}

	/**
	 * < <Descrição do método>>
	 * 
	 * @param actionMapping
	 *            Descrição do parâmetro
	 * @param actionForm
	 *            Descrição do parâmetro
	 * @param httpServletRequest
	 *            Descrição do parâmetro
	 * @param httpServletResponse
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 * @throws ControladorException
	 */
	public ActionForward atualizarImovelSubCategoriaAction(ActionMapping actionMapping, ActionForm actionForm,
					HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ControladorException{

		// Seta um atributo na sessao para ser utilizado nas abas
		httpServletRequest.getSession().setAttribute("nomeUnicoWizard", ConstantesSistema.NOME_WIZARD_ALTERAR_IMOVEL);

		new AtualizarImovelSubCategoriaAction().execute(actionMapping, actionForm, httpServletRequest, httpServletResponse);
		return this.redirecionadorWizard(actionMapping, actionForm, httpServletRequest, httpServletResponse,
						ConstantesSistema.NOME_WIZARD_ALTERAR_IMOVEL);
	}

	/**
	 * < <Descrição do método>>
	 * 
	 * @param actionMapping
	 *            Descrição do parâmetro
	 * @param actionForm
	 *            Descrição do parâmetro
	 * @param httpServletRequest
	 *            Descrição do parâmetro
	 * @param httpServletResponse
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 */
	public ActionForward atualizarImovelCaracteristicasAction(ActionMapping actionMapping, ActionForm actionForm,
					HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){

		// Seta um atributo na sessao para ser utilizado nas abas
		httpServletRequest.getSession().setAttribute("nomeUnicoWizard", ConstantesSistema.NOME_WIZARD_ALTERAR_IMOVEL);

		new AtualizarImovelCaracteristicasAction().execute(actionMapping, actionForm, httpServletRequest, httpServletResponse);
		return this.redirecionadorWizard(actionMapping, actionForm, httpServletRequest, httpServletResponse,
						ConstantesSistema.NOME_WIZARD_ALTERAR_IMOVEL);
	}

	/**
	 * < <Descrição do método>>
	 * 
	 * @param actionMapping
	 *            Descrição do parâmetro
	 * @param actionForm
	 *            Descrição do parâmetro
	 * @param httpServletRequest
	 *            Descrição do parâmetro
	 * @param httpServletResponse
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 */
	public ActionForward atualizarImovelConclusaoAction(ActionMapping actionMapping, ActionForm actionForm,
					HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){

		new AtualizarImovelConclusaoAction().execute(actionMapping, actionForm, httpServletRequest, httpServletResponse);
		return this.redirecionadorWizard(actionMapping, actionForm, httpServletRequest, httpServletResponse,
						ConstantesSistema.NOME_WIZARD_ALTERAR_IMOVEL);
	}

	// FIM DOS MÉTODOS DE EXIBIÇÃO =============================================
	// ==========================================================================

	// MÉTODO PARA CONCLUIR O PROCESSO =========================================

	/**
	 * < <Descrição do método>>
	 * 
	 * @param actionMapping
	 *            Descrição do parâmetro
	 * @param actionForm
	 *            Descrição do parâmetro
	 * @param httpServletRequest
	 *            Descrição do parâmetro
	 * @param httpServletResponse
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 * @throws ControladorException
	 */
	public ActionForward atualizarImovelAction(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse) throws ControladorException{

		// recebe o parametros e consulta o objeto da sessao para chamar outro
		// metodo desta classe
		return new AtualizarImovelAction().execute(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}

}
