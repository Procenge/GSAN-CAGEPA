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
 * Thiago Silva Toscano de Brito
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

package gcom.gui.cobranca.spcserasa;

import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Thiago Silva Toscano de Brito,
 *         Yara Taciane de Souza.
 * @date 22/12/2007
 */
public class ExcluirNegativacaoOnLineAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		HttpSession sessao = httpServletRequest.getSession(false);

		Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");

		ActionForward retorno = actionMapping.findForward("telaSucesso");

		ExcluirNegativacaoOnLineActionForm form = (ExcluirNegativacaoOnLineActionForm) actionForm;
		Integer idNegativador = null;
		if(form.getNegativador() != null && !form.getNegativador().equals("")){
			idNegativador = Integer.parseInt(form.getNegativador());
		}else{
			// caso nao tenha passado o negativador
			// levanta excecao pq eh obrigatorio
			throw new ActionServletException("atencao.negativador.nao.selecionado");
		}

		Collection colecaoImoveis = (Collection) getSessao(httpServletRequest).getAttribute("colecaoImoveis");

		String idImovel = (String) form.getIdImovel();
		if(idImovel != null && !idImovel.trim().equals("")){
			if(colecaoImoveis == null){
				colecaoImoveis = new ArrayList<Integer>();
			}
			colecaoImoveis.add(Integer.parseInt(idImovel));
		}

		if(idNegativador != null){
			if(colecaoImoveis != null && colecaoImoveis.size() > 0){

				Integer quantidade = Fachada.getInstancia().excluirNegativacaoOnLineEmLote(colecaoImoveis, idNegativador, usuarioLogado);

				if(quantidade == 0){
					montarPaginaSucesso(httpServletRequest, "Nenhuma exclusão foi realizada. Esse(s) imóvel(is) foi(ram) excluído(s) "
									+ " da negativação por outro usuário. ", "Excluir outra Negativação",
									"exibirExcluirNegativacaoOnLineAction.do?menu=sim", "", "");
				}else{
					montarPaginaSucesso(httpServletRequest, quantidade + " imóvel(is) foi(ram) preparado(s) "
									+ " para ser(em) excluído(s) da negativação pelo processo diário de exclusão.",
									"Excluir outra Negativação", "exibirExcluirNegativacaoOnLineAction.do?menu=sim", "", "");
				}

			}else{
				throw new ActionServletException("atencao.nenhum_imovel_encontrado_arquivo", httpServletRequest.getRequestURI(), "");
			}

		}

		return retorno;
	}
}