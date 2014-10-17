/**
 * 
 */
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

package gcom.gui.atendimentopublico.registroatendimento;

import gcom.atendimentopublico.registroatendimento.FiltroSolicitacaoTipoEspecificacaoMensagem;
import gcom.atendimentopublico.registroatendimento.SolicitacaoTipoEspecificacaoMensagem;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Descrição da classe
 * 
 * @author Ailton Sousa
 * @date 24/02/2011
 */
public class ExibirAtualizarMensagemTipoSolicitacaoEspecificacaoAction
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

		// Mudar isso quando tiver esquema de segurança
		HttpSession sessao = httpServletRequest.getSession(false);

		Fachada fachada = Fachada.getInstancia();

		// Set no mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("atualizarMensagemTipoSolicitacaoEspecificacao");

		AtualizarMensagemTipoSolicitacaoEspecificacaoActionForm atualizarMensagemTipoSolicitacaoEspecificacaoActionForm = (AtualizarMensagemTipoSolicitacaoEspecificacaoActionForm) actionForm;

		SolicitacaoTipoEspecificacaoMensagem solicitacaoTipoEspecificacaoMensagem = null;

		String idMensagemTipoSolicitacao = null;

		if(httpServletRequest.getParameter("idMensagemTipoSolicitacao") != null){
			// tela do manter
			idMensagemTipoSolicitacao = (String) httpServletRequest.getParameter("idMensagemTipoSolicitacao");
			sessao.setAttribute("idMensagemTipoSolicitacao", idMensagemTipoSolicitacao);
			sessao.setAttribute("caminhoRetornoVoltar", "/gsan/exibirManterMensagemTipoSolicitacaoEspecificacaoAction.do");
		}else if(sessao.getAttribute("idMensagemTipoSolicitacao") != null){
			// tela do filtrar
			idMensagemTipoSolicitacao = (String) sessao.getAttribute("idMensagemTipoSolicitacao");
			sessao.setAttribute("caminhoRetornoVoltar", "/gsan/exibirFiltrarMensagemTipoSolicitacaoEspecificacaoAction.do");
		}else if(httpServletRequest.getParameter("idRegistroInseridoAtualizar") != null){
			// link na tela de sucesso do inserir mensagem
			idMensagemTipoSolicitacao = (String) httpServletRequest.getParameter("idRegistroInseridoAtualizar");
			sessao.setAttribute("caminhoRetornoVoltar", "/gsan/exibirFiltrarMensagemTipoSolicitacaoEspecificacaoAction.do?menu=sim");
		}

		if(idMensagemTipoSolicitacao == null){

			if(sessao.getAttribute("idRegistroAtualizar") != null){
				solicitacaoTipoEspecificacaoMensagem = (SolicitacaoTipoEspecificacaoMensagem) sessao.getAttribute("idRegistroAtualizar");
			}else{
				idMensagemTipoSolicitacao = (String) httpServletRequest.getParameter("idMensagemTipoSolicitacao").toString();
			}
		}

		// ------ Inicio da parte que verifica se vem da página de
		// solicitacao_mensagem_tipo_manter.jsp

		if(solicitacaoTipoEspecificacaoMensagem == null){

			if(idMensagemTipoSolicitacao != null && !idMensagemTipoSolicitacao.equals("")){

				FiltroSolicitacaoTipoEspecificacaoMensagem filtroSolicitacaoTipoEspecificacaoMensagem = new FiltroSolicitacaoTipoEspecificacaoMensagem();

				filtroSolicitacaoTipoEspecificacaoMensagem.adicionarParametro(new ParametroSimples(
								FiltroSolicitacaoTipoEspecificacaoMensagem.ID, idMensagemTipoSolicitacao));

				Collection colecaoMensagemTpEspecificacao = fachada.pesquisar(filtroSolicitacaoTipoEspecificacaoMensagem,
								SolicitacaoTipoEspecificacaoMensagem.class.getName());

				if(colecaoMensagemTpEspecificacao != null && !colecaoMensagemTpEspecificacao.isEmpty()){

					solicitacaoTipoEspecificacaoMensagem = (SolicitacaoTipoEspecificacaoMensagem) Util
									.retonarObjetoDeColecao(colecaoMensagemTpEspecificacao);

				}
			}
		}

		// ------ A mensagem foi encontrada

		atualizarMensagemTipoSolicitacaoEspecificacaoActionForm.setDescricaoMensagem(solicitacaoTipoEspecificacaoMensagem.getDescricao());

		atualizarMensagemTipoSolicitacaoEspecificacaoActionForm.setDescricaoAbreviada(solicitacaoTipoEspecificacaoMensagem
						.getDescricaoAbreviada());

		atualizarMensagemTipoSolicitacaoEspecificacaoActionForm
						.setIndicadorUso("" + solicitacaoTipoEspecificacaoMensagem.getIndicadorUso());

		atualizarMensagemTipoSolicitacaoEspecificacaoActionForm.setUltimaAlteracao(Util
						.formatarDataComHora(solicitacaoTipoEspecificacaoMensagem.getUltimaAlteracao()));

		sessao.setAttribute("solicitacaoTipoEspecificacaoMensagem", solicitacaoTipoEspecificacaoMensagem);

		httpServletRequest.setAttribute("idMensagemTipoSolicitacao", idMensagemTipoSolicitacao);

		// ------ Fim da parte que verifica se vem da página de solicitacao_mensagem_tipo_manter.jsp

		return retorno;
	}

}