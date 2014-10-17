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

import gcom.cadastro.imovel.Imovel;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.AlteracaoInscricaoImovelException;
import gcom.util.Util;

import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

public class AlterarImovelInscricaoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		// Obtém a instância da fachada
		Fachada fachada = Fachada.getInstancia();

		// Obtém a sessão
		HttpSession sessao = httpServletRequest.getSession(false);

		Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");

		DynaValidatorForm alterarImovelInscricaoActionForm = (DynaValidatorForm) sessao.getAttribute("AlterarImovelInscricaoActionForm");

		String indicadorAlteracaoRota = (String) alterarImovelInscricaoActionForm.get("indicadorAlteracaoRota");

		String provocaMudancaQuadra = (String) sessao.getAttribute("provocaMudancaQuadra");

		Map<Integer, Collection<Imovel>> imoveisAlteracao = (Map<Integer, Collection<Imovel>>) sessao.getAttribute("imoveisAlteracao");

		boolean usuarioConfirmou = false;

		if(httpServletRequest.getParameter("confirmado") != null && httpServletRequest.getParameter("confirmado").equalsIgnoreCase("ok")){
			usuarioConfirmou = true;
		}

		// Caso não tenha alteração da inscrição o indicador do tipo de alteração da
		// rota será passado como nulo
		if(provocaMudancaQuadra != null && !provocaMudancaQuadra.equals("") && provocaMudancaQuadra.equals("N")){
			indicadorAlteracaoRota = null;
		}

		try{

			fachada.atualizarImovelInscricao(imoveisAlteracao, usuarioLogado, indicadorAlteracaoRota, usuarioConfirmou);
			sessao.removeAttribute("listaImoveisComAlteracaoAgendada");

		}catch(AlteracaoInscricaoImovelException e){

			if(!Util.isVazioOrNulo(e.getListaImoveisComAlteracaoAgendada())){
				sessao.setAttribute("listaImoveisComAlteracaoAgendada", e.getListaImoveisComAlteracaoAgendada());
			}

			httpServletRequest.setAttribute("caminhoActionConclusao", "/gsan/alterarImovelInscricaoAction.do");

			retorno = montarPaginaConfirmacao(e.getMessage(), "Dados do Imóvel",
							"/gsan/exibirImovelAlteracoesInscricaoAgendamentoAction.do", httpServletRequest, actionMapping, "");

			return retorno;
		}

		int quantidadeImoveisAlterados = getQuantidadeImoveisAlterados(imoveisAlteracao);

		// Mensagem de retorno para o usuário
		montarPaginaSucesso(
						httpServletRequest,
						quantidadeImoveisAlterados
										+ " Inscrições de Imóvel alteradas com sucesso. O Relatório de Imóveis com Inscrição Alterada foi encaminhado para batch.",
						"Atualizar outra Inscrição de Imóvel", "exibirAlterarImovelInscricaoAction.do");

		// devolve o mapeamento de retorno
		return retorno;
	}

	/**
	 * Retorna a quantidade de imóveis alterados
	 * 
	 * @author Luciano Galvao
	 * @date 29/01/2013
	 */
	private int getQuantidadeImoveisAlterados(Map<Integer, Collection<Imovel>> imoveisAlterados){

		int quantidade = 0;

		if(imoveisAlterados != null && !imoveisAlterados.isEmpty()){
			for(Integer rotaId : imoveisAlterados.keySet()){
				quantidade = quantidade + imoveisAlterados.get(rotaId).size();
			}
		}

		return quantidade;
	}
}
