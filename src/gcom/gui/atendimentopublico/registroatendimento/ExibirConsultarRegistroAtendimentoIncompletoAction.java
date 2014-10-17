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
 * Action que define o pr�-processamento da p�gina de consultar RA
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