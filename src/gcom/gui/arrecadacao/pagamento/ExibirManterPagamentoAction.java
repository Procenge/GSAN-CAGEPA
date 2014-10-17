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

package gcom.gui.arrecadacao.pagamento;

import gcom.arrecadacao.pagamento.Pagamento;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.Util;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action respons�vel por exibir a p�gina de manter pagamentos
 * 
 * @author Pedro Alexandre
 * @date 21/03/2006
 */
public class ExibirManterPagamentoAction
				extends GcomAction {

	/**
	 * @author Pedro Alexandre
	 * @date 21/03/2006
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = null;// actionMapping.findForward("manterPagamento");

		// Fachada fachada = Fachada.getInstancia();

		Collection colecaoPagamentos = null;

		HttpSession sessao = httpServletRequest.getSession(false);

		if(sessao.getAttribute("colecaoImoveisPagamentos") != null){
			colecaoPagamentos = (Collection) sessao.getAttribute("colecaoImoveisPagamentos");

		}else if(sessao.getAttribute("colecaoClientesPagamentos") != null){
			colecaoPagamentos = (Collection) sessao.getAttribute("colecaoClientesPagamentos");

		}else if(sessao.getAttribute("colecaoPagamentosAvisoBancario") != null){
			colecaoPagamentos = (Collection) sessao.getAttribute("colecaoPagamentosAvisoBancario");

		}else if(sessao.getAttribute("colecaoPagamentos") != null){
			colecaoPagamentos = (Collection) sessao.getAttribute("colecaoPagamentos");

		}else if(sessao.getAttribute("colecaoPagamentosLocalidade") != null){
			colecaoPagamentos = (Collection) sessao.getAttribute("colecaoPagamentosLocalidade");

		}else if(sessao.getAttribute("colecaoPagamentosMovimentoArrecadador") != null){
			colecaoPagamentos = (Collection) sessao.getAttribute("colecaoPagamentosMovimentoArrecadador");
		}

		String identificadorAtualizar = (String) sessao.getAttribute("indicadorAtualizar");

		if(colecaoPagamentos != null && colecaoPagamentos.size() == 1 && identificadorAtualizar != null){
			retorno = actionMapping.findForward("atualizarPagamentoFiltrar");

			Pagamento pagamento = (Pagamento) Util.retonarObjetoDeColecao(colecaoPagamentos);

			sessao.setAttribute("idRegistroAtualizacao", pagamento.getId().toString());
		}else{
			retorno = actionMapping.findForward("manterPagamento");
		}

		// FiltroPagamento filtroPagamento = null;
		//
		// if (httpServletRequest.getAttribute("filtroPagamento") != null) {
		// filtroPagamento = (FiltroPagamento) httpServletRequest.getAttribute("filtroPagamento");
		// } else {
		// // Caso o exibirManterPagamento n�o tenha passado por algum esquema de
		// // filtro,a quantidade de registros � verificada para avaliar a necessidade
		// // de filtragem
		// filtroPagamento = new FiltroPagamento();
		//
		// if (fachada.registroMaximo(Pagamento.class) >
		// ConstantesSistema.NUMERO_MAXIMO_REGISTROS_MANUTENCAO) {
		// // Se o limite de registros foi atingido, a p�gina de filtragem
		// // � chamada
		// retorno = actionMapping.findForward("filtrarPagamento");
		// }
		// }
		//
		// // A pesquisa de pagamentos s� ser� feita se o forward estiver direcionado
		// // para a p�gina de manterPagamento
		// if (retorno.getName().equalsIgnoreCase("manterPagamento")) {
		// // Seta a ordena��o desejada do filtro
		// filtroPagamento.setCampoOrderBy(FiltroPagamento.ID);
		//
		// filtroPagamento.adicionarCaminhoParaCarregamentoEntidade("debitoTipo");
		// filtroPagamento.adicionarCaminhoParaCarregamentoEntidade("imovel");
		// filtroPagamento.adicionarCaminhoParaCarregamentoEntidade("cliente");
		// filtroPagamento.adicionarCaminhoParaCarregamentoEntidade("avisoBancario");
		// filtroPagamento.adicionarCaminhoParaCarregamentoEntidade("pagamentoSituacaoAtual");
		// filtroPagamento.adicionarCaminhoParaCarregamentoEntidade("pagamentoSituacaoAnterior");
		// filtroPagamento.adicionarCaminhoParaCarregamentoEntidade("avisoBancario.arrecadador");
		//			
		//
		// pagamentos = fachada.pesquisar(filtroPagamento, Pagamento.class.getName());

		if(colecaoPagamentos == null || colecaoPagamentos.isEmpty()){
			// Nenhum pagamento cadastrado
			throw new ActionServletException("atencao.pesquisa.nenhumresultado");
		}

		sessao.setAttribute("colecaoPagamentos", colecaoPagamentos);

		sessao.removeAttribute("colecaoImoveisPagamentos");
		sessao.removeAttribute("colecaoClientesPagamentos");
		sessao.removeAttribute("colecaoAvisosBancariosPagamentos");
		// }

		return retorno;
	}

}