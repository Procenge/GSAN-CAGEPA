/**
 * 
 */
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

package gcom.gui.cadastro.atendimento;

import gcom.cadastro.atendimento.AtendimentoProcedimento;
import gcom.cadastro.atendimento.FiltroAtendimentoProcedimento;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;

import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Descri��o da classe
 * 
 * @author Gicevalter Couto
 * @date 23/09/2014
 */
public class ExibirManterAtendimentoProcedimentoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirManterAtendimentoProcedimentoAction");

		// Obt�m a inst�ncia da fachada
		// Fachada fachada = Fachada.getInstancia();

		HttpSession sessao = httpServletRequest.getSession(false);

		// Limpa o atributo se o usu�rio voltou para o manter
		if(sessao.getAttribute("colecaoAtendimentoProcedimentoTela") != null){
			sessao.removeAttribute("colecaoAtendimentoProcedimentoTela");
		}

		sessao.removeAttribute("colecaoAtendProcDocumentoPessoaTipo");
		sessao.removeAttribute("colecaoAtendProcNormaProcedimental");
		sessao.removeAttribute("colecaoEspecificacao");

		// Recupera o filtro passado pelo FiltrarAtendimentoProcedimentoAction para
		// ser efetuada pesquisa
		FiltroAtendimentoProcedimento filtroAtendimentoProcedimento = (FiltroAtendimentoProcedimento) sessao.getAttribute("filtroAtendimentoProcedimento");
		filtroAtendimentoProcedimento.adicionarCaminhoParaCarregamentoEntidade("solicitacaoTipoEspecificacao");
		filtroAtendimentoProcedimento.adicionarCaminhoParaCarregamentoEntidade("solicitacaoTipoEspecificacao.solicitacaoTipo");
		filtroAtendimentoProcedimento.adicionarCaminhoParaCarregamentoEntidade("funcionalidade");

		Map resultado = controlarPaginacao(httpServletRequest, retorno, filtroAtendimentoProcedimento, AtendimentoProcedimento.class.getName());
		Collection colecaoAtendimentoProcedimento = (Collection) resultado.get("colecaoRetorno");
		retorno = (ActionForward) resultado.get("destinoActionForward");

		String apenasAtendimentoProcedimento = (String) httpServletRequest.getAttribute("apenasAtendimentoProcedimento");
		if("true".equals(apenasAtendimentoProcedimento)){
			httpServletRequest.setAttribute("apenasAtendimentoProcedimento", "true");
		}

		if(colecaoAtendimentoProcedimento != null && !colecaoAtendimentoProcedimento.isEmpty()){

			if(colecaoAtendimentoProcedimento.size() == 1
							&& (httpServletRequest.getParameter("page.offset") == null || httpServletRequest.getParameter("page.offset")
											.equals("1"))){

				// Verifica se o usu�rio marcou o checkbox de atualizar no jsp
				// AtendimentoProcedimento_filtrar. Caso todas as condi��es sejam
				// verdadeiras seta o retorno para o
				// ExibirAtualizarAtendimentoProcedimentoAction e em caso negativo
				// manda a cole��o pelo request.

				if(httpServletRequest.getParameter("atualizar") != null){
					retorno = actionMapping.findForward("atualizarAtendimentoProcedimento");
					AtendimentoProcedimento atendimentoProcedimento = (AtendimentoProcedimento) colecaoAtendimentoProcedimento.iterator()
									.next();
					sessao.setAttribute("objetoAtendimentoProcedimento", atendimentoProcedimento);

				}else{
					httpServletRequest.setAttribute("colecaoAtendimentoProcedimento", colecaoAtendimentoProcedimento);
				}
			}else{
				httpServletRequest.setAttribute("colecaoAtendimentoProcedimento", colecaoAtendimentoProcedimento);
			}
		}else{
			// Nenhuma AtendimentoProcedimento cadastrada
			throw new ActionServletException("atencao.pesquisa.nenhumresultado");
		}

		return retorno;
	}
}
