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

import gcom.atendimentopublico.registroatendimento.AtendimentoIncompleto;
import gcom.atendimentopublico.registroatendimento.FiltroSolicitacaoTipoEspecificacao;
import gcom.atendimentopublico.registroatendimento.SolicitacaoTipoEspecificacao;
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
 * Action que define o pré-processamento da página de consultar RA
 * 
 * @author Rafael Pinto
 * @created 25/07/2006
 */
public class ExibirConsultarRegistroAtendimentoIncompletoAction
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
		ActionForward retorno = actionMapping.findForward("atualizarRegistroAtendimentoIncompleto");

		Fachada fachada = Fachada.getInstancia();

		HttpSession sessao = httpServletRequest.getSession(false);

		ConsultarRegistroAtendimentoIncompletoActionForm consultarRAIncompletoActionForm = (ConsultarRegistroAtendimentoIncompletoActionForm) actionForm;
		String ra = httpServletRequest.getParameter("numeroRA");

		if(ra != null && !ra.equals("")){
			AtendimentoIncompleto atendimentoIncompleto = fachada.pesquisarRAIncompleta(Integer.valueOf(ra));

			if(atendimentoIncompleto != null){
				consultarRAIncompletoActionForm.setId(String.valueOf(atendimentoIncompleto.getId()));
				if(atendimentoIncompleto.getNumeroTelefone() != null){
					consultarRAIncompletoActionForm.setNumeroFoneChamada(String.valueOf(atendimentoIncompleto.getNumeroTelefone()));
				}else{
					consultarRAIncompletoActionForm.setNumeroFoneChamada("");
				}
				if(atendimentoIncompleto.getNomeContato() != null){
					consultarRAIncompletoActionForm.setNomeContato(atendimentoIncompleto.getNomeContato());
				}else{

				}
				consultarRAIncompletoActionForm.setIndicadorRetornoChamada(String.valueOf(atendimentoIncompleto
								.getIndicadorRetornoChamada()));
				if(atendimentoIncompleto.getRegistroAtendimento() != null){
					consultarRAIncompletoActionForm.setRAEfetiva(String.valueOf(atendimentoIncompleto.getRegistroAtendimento().getId()));
				}
				
				if(atendimentoIncompleto.getSolicitacaoTipoEspecificacao() != null){

					SolicitacaoTipoEspecificacao solicitacaoTipoEspecificacaoNaBase;
					SolicitacaoTipoEspecificacao solicitacaoTipoEspecificacao = atendimentoIncompleto.getSolicitacaoTipoEspecificacao();
					FiltroSolicitacaoTipoEspecificacao filtroSolicitacaoTipoEspecificacao = new FiltroSolicitacaoTipoEspecificacao();
					filtroSolicitacaoTipoEspecificacao.adicionarParametro(new ParametroSimples(FiltroSolicitacaoTipoEspecificacao.ID,
									solicitacaoTipoEspecificacao.getId()));
					filtroSolicitacaoTipoEspecificacao
									.adicionarCaminhoParaCarregamentoEntidade(FiltroSolicitacaoTipoEspecificacao.SOLICITACAO_TIPO);

					Collection<SolicitacaoTipoEspecificacao> colecaoSolicitacaoTipoEspecificacao = fachada.pesquisar(
									filtroSolicitacaoTipoEspecificacao, SolicitacaoTipoEspecificacao.class.getName());

					solicitacaoTipoEspecificacaoNaBase = (SolicitacaoTipoEspecificacao) Util
									.retonarObjetoDeColecao(colecaoSolicitacaoTipoEspecificacao);

					if(solicitacaoTipoEspecificacaoNaBase.getSolicitacaoTipo() != null){
						consultarRAIncompletoActionForm.setSolicitacaoTipo(solicitacaoTipoEspecificacaoNaBase.getSolicitacaoTipo()
										.getDescricao());
					}else{
						consultarRAIncompletoActionForm.setSolicitacaoTipo("");
					}

				}
				

				if(atendimentoIncompleto.getSolicitacaoTipoEspecificacao() != null){
					consultarRAIncompletoActionForm.setSolicitacaoTipoEspecificacao(atendimentoIncompleto.getSolicitacaoTipoEspecificacao()
									.getDescricaoParaRegistroTransacao());
				}else{
					consultarRAIncompletoActionForm.setSolicitacaoTipoEspecificacao("");
				}

				if(atendimentoIncompleto.getDescricaoObservacao() != null){
					consultarRAIncompletoActionForm.setObservacao(atendimentoIncompleto.getDescricaoObservacao());
				}else{
					consultarRAIncompletoActionForm.setObservacao("");
				}
				if(atendimentoIncompleto.getAtendimentoIncompletoMotivo() != null){
					consultarRAIncompletoActionForm.setAtendimentoIncompletoMotivo(atendimentoIncompleto.getAtendimentoIncompletoMotivo()
									.getDescricao());
				}else{
					consultarRAIncompletoActionForm.setAtendimentoIncompletoMotivo("");
				}
				if(atendimentoIncompleto.getImovel() != null){
					consultarRAIncompletoActionForm.setImovel(atendimentoIncompleto.getImovel().getId().toString());
				}else{
					consultarRAIncompletoActionForm.setImovel("");
				}
			}
		}
		// Colocado por Raphael Rossiter em 26/10/2006
		httpServletRequest.setAttribute("nomeCampo", "id");
		return retorno;
	}
}