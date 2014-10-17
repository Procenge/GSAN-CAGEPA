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

package gcom.gui.faturamento.conta;

import gcom.fachada.Fachada;
import gcom.faturamento.conta.Conta;
import gcom.faturamento.conta.FaturaContasPreFaturadasHelper;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Permite consultar as contas Pr�-Faturadas
 * [UC3037] Filtrar Contas Pr�-Faturadas
 * 
 * @author Carlos Chrystian
 * @created 10/02/2012
 *          Exibir Contas Pr�-Faturadas.
 */
public class ExibirResultadoFiltrarContasPreFaturadasAction
				extends GcomAction {

	/**
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("consultaFaturamentoContasPreFaturadas");

		// Obt�m a inst�ncia da fachada
		Fachada fachada = Fachada.getInstancia();

		// Obt�m a sess�o
		HttpSession sessao = httpServletRequest.getSession(false);

		// Total de registros
		Integer totalRegistros = 0;

		// Indicador de atualizar registros
		String atualizar = (String) sessao.getAttribute("indicadorAtualizar");

		String indicadorAtualizar = (String) httpServletRequest.getParameter("indicadorAtualizar");

		if(!Util.isVazioOuBranco(indicadorAtualizar) && indicadorAtualizar.equals(ConstantesSistema.NAO.toString())){
			atualizar = ConstantesSistema.NAO.toString();
		}

		// Recupera as informa��es para enviar na consulta
		FaturaContasPreFaturadasHelper faturaContasPreFaturadasHelper = (FaturaContasPreFaturadasHelper) sessao
						.getAttribute("faturaContasPreFaturadasHelper");

		// Total de registros
		Collection<Integer> colecaoIdConta = fachada.pesquisarQuantidadeContasPreFaturadas(faturaContasPreFaturadasHelper);

		// Armazena os id's das contas pesquisadas
		Collection<Integer> idContas = new ArrayList<Integer>();

		if(!Util.isVazioOrNulo(colecaoIdConta)){
			// Total de registros
			totalRegistros = colecaoIdConta.size();

			for(Integer idConta : colecaoIdConta){
				idContas.add(idConta);
			}
		}

		sessao.setAttribute("colecaoIdcontas", idContas);

		if(totalRegistros == 0){
			// Nenhuma registro encontrado
			throw new ActionServletException("atencao.pesquisa.nenhumresultado", null, "Conta");
		}

		// Pagina��o
		retorno = this.controlarPaginacao(httpServletRequest, retorno, totalRegistros);

		// Consulta as contas pr�-faturadas
		Collection<Conta> colecaoContasPreFaturadas = fachada.pesquisarContasPreFaturadas(faturaContasPreFaturadasHelper,
						(Integer) httpServletRequest.getAttribute("numeroPaginasPesquisa"), false);

		if(!Util.isVazioOrNulo(colecaoContasPreFaturadas)){
			if((!Util.isVazioOuBranco(atualizar) && atualizar.equals(ConstantesSistema.SIM.toString())) && totalRegistros == 1){

				Conta conta = (Conta) Util.retonarObjetoDeColecao(colecaoContasPreFaturadas);
				Integer idConta = conta.getId();

				sessao.setAttribute("idConta", idConta.toString());

				retorno = actionMapping.findForward("atualizarSituacaoConta");

			}else{

				retorno = actionMapping.findForward("consultaFaturamentoContasPreFaturadas");

				// Aciona o controle de pagina��o para que sejam pesquisados apenas os registros que
				// aparecem na p�gina
				retorno = controlarPaginacao(httpServletRequest, retorno, totalRegistros);

				sessao.setAttribute("colecaoContasPreFaturadas", colecaoContasPreFaturadas);
				sessao.removeAttribute("voltar");
			}
		}else{
			ActionServletException actionServletException = new ActionServletException("atencao.pesquisa.nenhumresultado");
			actionServletException.setUrlBotaoVoltar("/gsan/exibirFiltrarContasPreFaturadasAction.do");
			throw actionServletException;
		}

		return retorno;

	}
}
