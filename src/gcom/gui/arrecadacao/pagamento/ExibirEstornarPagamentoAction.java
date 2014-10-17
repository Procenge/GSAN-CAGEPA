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
 * GSANPCG
 * André Rangel Nishimura
 * Eduardo Henrique Bandeira Carneiro da Silva
 * Lucas Daniel Souza de Medeiros
 * Saulo Vasconcelos de Lima
 * Thiago Toscano
 * Virginia Santos de Melo
 *
 * Este programa é software livre; você pode redistribuí-lo e/ou
 * modificá-lo sob os termos de Licença Pública Geral GNU, conforme
 * publicada pela Free Software Foundation; versão 2 da Licença.
 * Este programa é distribuído na expectativa de ser útil, mas SEM
 * QUALQUER GARANTIA; sem mesmo a garantia implícita de
 * COMERCIALIZAÇÃO ou de ADEQUAÇÃO A QUALQUER PROPÓSITO EM
 * PARTICULAR. Consulte a Licença Pública Geral GNU para obter mais detalhes.
 * Você deve ter recebido uma cópia da Licença Pública Geral GNU
 * junto com este programa; se não, escreva para Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 * 02111-1307, USA.
 */

package gcom.gui.arrecadacao.pagamento;

import gcom.arrecadacao.pagamento.PagamentoHistorico;
import gcom.arrecadacao.pagamento.bean.PagamentosHistoricoHelper;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.faturamento.credito.CreditoARealizar;
import gcom.faturamento.credito.CreditoARealizarHistorico;
import gcom.faturamento.credito.FiltroCreditoARealizar;
import gcom.faturamento.credito.FiltroCreditoARealizarHistorico;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC01016] Estorno Pagamento
 * 
 * @author Thiago Toscano
 * @date 12/08/2009
 * @author Saulo Lima
 * @date 20/08/2009
 *       Conclusão do funcionalidade
 */
public class ExibirEstornarPagamentoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("consultarPagamentosParaEstorno");

		// Instacia a fachada
		Fachada fachada = Fachada.getInstancia();

		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();
		// Mudar isso quando tiver esquema de segurança
		HttpSession sessao = httpServletRequest.getSession(false);

		Collection<PagamentoHistorico> pagamentosHistorico = new ArrayList<PagamentoHistorico>();

		Collection<PagamentoHistorico> colecaoImoveisPagamentosHistorico2 = (Collection<PagamentoHistorico>) sessao
						.getAttribute("colecaoImoveisPagamentosHistorico");
		Collection<PagamentoHistorico> colecaoPagamentosHistoricoClienteConta2 = (Collection<PagamentoHistorico>) sessao
						.getAttribute("colecaoPagamentosHistoricoClienteConta");
		Collection<PagamentoHistorico> colecaoPagamentosHistoricoClienteGuiaPagamento2 = (Collection<PagamentoHistorico>) sessao
						.getAttribute("colecaoPagamentosHistoricoClienteGuiaPagamento");
		Collection<PagamentoHistorico> colecaoPagamentosHistoricoClienteDebitoACobrar2 = (Collection<PagamentoHistorico>) sessao
						.getAttribute("colecaoPagamentosHistoricoClienteDebitoACobrar");
		Collection<PagamentoHistorico> colecaoPagamentosHistoricoLocalidade2 = (Collection<PagamentoHistorico>) sessao
						.getAttribute("colecaoPagamentosHistoricoLocalidade");
		Collection<PagamentoHistorico> colecaoPagamentosHistoricoAvisoBancario2 = (Collection<PagamentoHistorico>) sessao
						.getAttribute("colecaoPagamentosHistoricoAvisoBancario");
		Collection<PagamentoHistorico> colecaoPagamentosHistoricoMovimentoArrecadador2 = (Collection<PagamentoHistorico>) sessao
						.getAttribute("colecaoPagamentosHistoricoMovimentoArrecadador");
		Collection<PagamentoHistorico> colecaoPagamentosHistoricoClientes2 = (Collection<PagamentoHistorico>) sessao
						.getAttribute("colecaoPagamentosHistoricoClientes");

		sessao.removeAttribute("colecaoImoveisPagamentosHistorico");
		sessao.removeAttribute("colecaoPagamentosHistoricoClienteConta");
		sessao.removeAttribute("colecaoPagamentosHistoricoClienteGuiaPagamento");
		sessao.removeAttribute("colecaoPagamentosHistoricoClienteDebitoACobrar");
		sessao.removeAttribute("colecaoPagamentosHistoricoLocalidade");
		sessao.removeAttribute("colecaoPagamentosHistoricoAvisoBancario");
		sessao.removeAttribute("colecaoPagamentosHistoricoMovimentoArrecadador");
		sessao.removeAttribute("colecaoPagamentosHistoricoClientes");

		if(colecaoImoveisPagamentosHistorico2 != null) pagamentosHistorico.addAll(colecaoImoveisPagamentosHistorico2);
		if(colecaoPagamentosHistoricoClienteConta2 != null) pagamentosHistorico.addAll(colecaoPagamentosHistoricoClienteConta2);
		if(colecaoPagamentosHistoricoClienteGuiaPagamento2 != null) pagamentosHistorico
						.addAll(colecaoPagamentosHistoricoClienteGuiaPagamento2);
		if(colecaoPagamentosHistoricoClienteDebitoACobrar2 != null) pagamentosHistorico
						.addAll(colecaoPagamentosHistoricoClienteDebitoACobrar2);
		if(colecaoPagamentosHistoricoLocalidade2 != null) pagamentosHistorico.addAll(colecaoPagamentosHistoricoLocalidade2);
		if(colecaoPagamentosHistoricoAvisoBancario2 != null) pagamentosHistorico.addAll(colecaoPagamentosHistoricoAvisoBancario2);
		if(colecaoPagamentosHistoricoMovimentoArrecadador2 != null) pagamentosHistorico
						.addAll(colecaoPagamentosHistoricoMovimentoArrecadador2);
		if(colecaoPagamentosHistoricoClientes2 != null) pagamentosHistorico.addAll(colecaoPagamentosHistoricoClientes2);
		if(colecaoPagamentosHistoricoClientes2 != null) pagamentosHistorico.addAll(colecaoPagamentosHistoricoClientes2);

		sessao.setAttribute("colecaoPagamentosHistorico",
						this.verificaCreditoARealizarHistoricoCreditoARealizar(fachada, pagamentosHistorico));
		sessao.setAttribute("parametroAnoMesArrecadacao", sistemaParametro.getAnoMesArrecadacao());

		return retorno;
	}

	/**
	 * [UC1016] Estornar Pagamentos
	 * Item 3.1
	 * Author: Josenildo Neves.
	 * Date: 14/03/2013
	 * 
	 * @param fachada
	 * @param pagamentosHistorico
	 */
	private Collection<PagamentosHistoricoHelper> verificaCreditoARealizarHistoricoCreditoARealizar(Fachada fachada,
					Collection<PagamentoHistorico> pagamentosHistorico){

		CreditoARealizar aRealizar = null;
		Collection<PagamentosHistoricoHelper> colecaoPagamentosHistorico = new ArrayList<PagamentosHistoricoHelper>();
		PagamentosHistoricoHelper pagamentosHistoricoHelper = null;

		for(PagamentoHistorico pagamentoHistorico : pagamentosHistorico){

			pagamentosHistoricoHelper = new PagamentosHistoricoHelper();

			FiltroCreditoARealizarHistorico filtroCreditoARealizarHistorico = new FiltroCreditoARealizarHistorico();
			filtroCreditoARealizarHistorico.adicionarParametro(new ParametroSimples(FiltroCreditoARealizarHistorico.PAGAMENTO_HISTORICO,
							pagamentoHistorico));

			Collection colecaoCreditoARealizarHistorico = fachada.pesquisar(filtroCreditoARealizarHistorico,
							CreditoARealizarHistorico.class.getName());

			if(!Util.isVazioOrNulo(colecaoCreditoARealizarHistorico)){
				pagamentosHistoricoHelper.setCreditoARealizarHistorico(true);
			}

			FiltroCreditoARealizar filtroCreditoARealizar = new FiltroCreditoARealizar();
			filtroCreditoARealizar.adicionarParametro(new ParametroSimples(FiltroCreditoARealizar.PAGAMENTO_HISTORICO, pagamentoHistorico));

			Collection colecaoCreditoARealizar = fachada.pesquisar(filtroCreditoARealizar, CreditoARealizar.class.getName());

			if(!Util.isVazioOrNulo(colecaoCreditoARealizar)){
				aRealizar = (CreditoARealizar) Util.retonarObjetoDeColecao(colecaoCreditoARealizar);

				if(aRealizar.getValorResidualMesAnterior().compareTo(BigDecimal.ZERO) != ConstantesSistema.ZERO){
					pagamentosHistoricoHelper.setCreditoARealizar(true);
				}
			}

			pagamentosHistoricoHelper.setPagamentoHistorico(pagamentoHistorico);
			colecaoPagamentosHistorico.add(pagamentosHistoricoHelper);
			aRealizar = null;

		}

		return colecaoPagamentosHistorico;
	}
}