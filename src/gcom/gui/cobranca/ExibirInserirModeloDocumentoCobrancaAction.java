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

import gcom.cadastro.funcionario.FiltroFuncionario;
import gcom.cadastro.funcionario.Funcionario;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * InformarModeloDocumentoCobrancaActionForm
 * [UC3170] Informar Entrega/Devolução de Documentos de Cobrança
 * 
 * @author Gicevalter Couto
 * @created 13/03/2015
 */

public class ExibirInserirModeloDocumentoCobrancaAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("exibirInserirModeloDocumentoCobranca");

		InserirModeloDocumentoCobrancaActionForm form = (InserirModeloDocumentoCobrancaActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		// Cria uma instância da sessão
		HttpSession sessao = httpServletRequest.getSession(false);

		// Recupera a flag para indicar se o usuário apertou o botão de desfazer
		String desfazer = httpServletRequest.getParameter("desfazer");

		if(form.getDocumentoTipoId() != null && form.getDocumentoTipoDescricao() != null && form.getIndicadorModificacao() != null
						&& form.getIndicadorModificacao().equals("S")){

			form.setIndicadorModificacao("N");
			desfazer = "S";

		}

		// Caso o usuário não tenha apertado o botão de desfazer na tela
		// verifica todas as validações e pesquisas
		// Caso contrário limpa o form de inserir operação
		if(desfazer != null){
			// Caso o usuário tenha apertado o botão de desfazer na tela
			// volta o form ao estado inicial
			form.setDescricaoLayout("");
			form.setDescricaoCI("");
			form.setIndicadorLayoutPadrao("");
			form.setConteudoLayout("");
			form.setDescricaoCargoDocumentoLayoutAssinatura("");

			sessao.removeAttribute("colecaoDocumentoLayoutAssinatura");
			sessao.removeAttribute("colecaoCargoFuncionario");
		}

		if(sessao.getAttribute("colecaoCargoFuncionario") == null){
			FiltroFuncionario filtroFuncionario = new FiltroFuncionario();
			filtroFuncionario.setCampoDistinct(FiltroFuncionario.DESCRICAO_CARGO);
			filtroFuncionario.setCampoOrderBy(FiltroFuncionario.DESCRICAO_CARGO);

			Collection colecaoCargoFuncionario = fachada.pesquisar(filtroFuncionario, Funcionario.class.getName());

			sessao.setAttribute("colecaoCargoFuncionario", colecaoCargoFuncionario);
		}

		// Retorna o mapeamento contido na variável retorno
		return retorno;
	}

}
