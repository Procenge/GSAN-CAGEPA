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
 * Adriano Britto Siqueira
 * Alexandre Santos Cabral
 * Ana Carolina Alves Breda
 * Ana Maria Andrade Cavalcante
 * Aryed Lins de Ara�jo
 * Bruno Leonardo Rodrigues Barros
 * Carlos Elmano Rodrigues Ferreira
 * Cl�udio de Andrade Lira
 * Denys Guimar�es Guenes Tavares
 * Eduardo Breckenfeld da Rosa Borges
 * Fab�ola Gomes de Ara�jo
 * Fl�vio Leonardo Cavalcanti Cordeiro
 * Francisco do Nascimento J�nior
 * Homero Sampaio Cavalcanti
 * Ivan S�rgio da Silva J�nior
 * Jos� Edmar de Siqueira
 * Jos� Thiago Ten�rio Lopes
 * K�ssia Regina Silvestre de Albuquerque
 * Leonardo Luiz Vieira da Silva
 * M�rcio Roberto Batista da Silva
 * Maria de F�tima Sampaio Leite
 * Micaela Maria Coelho de Ara�jo
 * Nelson Mendon�a de Carvalho
 * Newton Morais e Silva
 * Pedro Alexandre Santos da Silva Filho
 * Rafael Corr�a Lima e Silva
 * Rafael Francisco Pinto
 * Rafael Koury Monteiro
 * Rafael Palermo de Ara�jo
 * Raphael Veras Rossiter
 * Roberto Sobreira Barbalho
 * Rodrigo Avellar Silveira
 * Rosana Carvalho Barbosa
 * S�vio Luiz de Andrade Cavalcante
 * Tai Mu Shih
 * Thiago Augusto Souza do Nascimento
 * Tiago Moreno Rodrigues
 * Vivianne Barbosa Sousa
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

		// Obt�m a inst�ncia da fachada
		Fachada fachada = Fachada.getInstancia();

		// Obt�m a sess�o
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

		// Caso n�o tenha altera��o da inscri��o o indicador do tipo de altera��o da
		// rota ser� passado como nulo
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

			retorno = montarPaginaConfirmacao(e.getMessage(), "Dados do Im�vel",
							"/gsan/exibirImovelAlteracoesInscricaoAgendamentoAction.do", httpServletRequest, actionMapping, "");

			return retorno;
		}

		int quantidadeImoveisAlterados = getQuantidadeImoveisAlterados(imoveisAlteracao);

		// Mensagem de retorno para o usu�rio
		montarPaginaSucesso(
						httpServletRequest,
						quantidadeImoveisAlterados
										+ " Inscri��es de Im�vel alteradas com sucesso. O Relat�rio de Im�veis com Inscri��o Alterada foi encaminhado para batch.",
						"Atualizar outra Inscri��o de Im�vel", "exibirAlterarImovelInscricaoAction.do");

		// devolve o mapeamento de retorno
		return retorno;
	}

	/**
	 * Retorna a quantidade de im�veis alterados
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
