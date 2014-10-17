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
 * Permite consultar as contas Pré-Faturadas
 * [UC3037] Filtrar Contas Pré-Faturadas
 * 
 * @author Carlos Chrystian
 * @created 10/02/2012
 *          Exibir Contas Pré-Faturadas.
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

		// Obtém a instância da fachada
		Fachada fachada = Fachada.getInstancia();

		// Obtém a sessão
		HttpSession sessao = httpServletRequest.getSession(false);

		// Total de registros
		Integer totalRegistros = 0;

		// Indicador de atualizar registros
		String atualizar = (String) sessao.getAttribute("indicadorAtualizar");

		String indicadorAtualizar = (String) httpServletRequest.getParameter("indicadorAtualizar");

		if(!Util.isVazioOuBranco(indicadorAtualizar) && indicadorAtualizar.equals(ConstantesSistema.NAO.toString())){
			atualizar = ConstantesSistema.NAO.toString();
		}

		// Recupera as informações para enviar na consulta
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

		// Paginação
		retorno = this.controlarPaginacao(httpServletRequest, retorno, totalRegistros);

		// Consulta as contas pré-faturadas
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

				// Aciona o controle de paginação para que sejam pesquisados apenas os registros que
				// aparecem na página
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
