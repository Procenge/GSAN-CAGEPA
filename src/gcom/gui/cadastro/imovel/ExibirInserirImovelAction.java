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

import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.gui.StatusWizard;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.parametrizacao.cadastro.ParametroCadastro;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Description of the Class
 * 
 * @author compesa
 * @created 17 de Maio de 2004
 */
public class ExibirInserirImovelAction
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

		// localiza o action no objeto actionmapping
		// ActionForward retorno =
		// actionMapping.findForward("gerenciadorProcesso");
		ActionForward retorno = actionMapping.findForward("exibirInserirImovelAction");

		// obtém a instância da sessão
		HttpSession sessao = httpServletRequest.getSession(false);

		// GerenciadorPaginas gerenciadorPaginas = null;

		// limpa a sessão
		sessao.removeAttribute("InserirImovelActionForm");
		sessao.removeAttribute("colecaoEnderecos");
		sessao.removeAttribute("imovelClientesNovos");
		sessao.removeAttribute("imoveisPrincipal");
		sessao.removeAttribute("colecaoImovelSubCategorias");

		// manda o gerenciador de paginas para a sessao
		StatusWizard statusWizard = new StatusWizard("inserirImovelWizardAction", "inserirImovelAction", "cancelarInserirImovelAction",
						"exibirInserirImovelAction.do");
		statusWizard.inserirNumeroPaginaCaminho(statusWizard.new StatusWizardItem(1, "LocalidadeA.gif", "LocalidadeD.gif",
						"exibirInserirImovelLocalidadeAction", "inserirImovelLocalidadeAction"));
		statusWizard.inserirNumeroPaginaCaminho(statusWizard.new StatusWizardItem(2, "EnderecoA.gif", "EnderecoD.gif",
						"exibirInserirImovelEnderecoAction", "inserirImovelEnderecoAction"));
		statusWizard.inserirNumeroPaginaCaminho(statusWizard.new StatusWizardItem(3, "ClienteA.gif", "ClienteD.gif",
						"exibirInserirImovelClienteAction", "inserirImovelClienteAction"));
		statusWizard.inserirNumeroPaginaCaminho(statusWizard.new StatusWizardItem(4, "SubcategoriaA.gif", "SubcategoriaD.gif",
						"exibirInserirImovelSubCategoriaAction", "inserirImovelSubCategoriaAction"));
		statusWizard.inserirNumeroPaginaCaminho(statusWizard.new StatusWizardItem(5, "CaracteristicaA.gif", "CaracteristicaD.gif",
						"exibirInserirImovelCaracteristicasAction", "inserirImovelCaracteristicasAction"));
		statusWizard.inserirNumeroPaginaCaminho(statusWizard.new StatusWizardItem(6, "ConclusaoA.gif", "ConclusaoD.gif",
						"exibirInserirImovelConclusaoAction", "inserirImovelConclusaoAction"));

		// manda o statusWizard para a sessao
		sessao.setAttribute("statusWizard", statusWizard);

		// Parâmetro para ativar ou não a obrigatoriedade de alguns campos
		try{
			if(ParametroCadastro.P_CAMPOS_OBRIGATORIOS_IMOVEL.executar().equals(ConstantesSistema.SIM.toString())){
				sessao.setAttribute("indicadorCamposObrigatorios", true);
			}else{
				sessao.removeAttribute("indicadorCamposObrigatorios");
			}
		}catch(ControladorException e){
			throw new ActionServletException(e.getMessage(), e);
		}

		return retorno;
	}

}
