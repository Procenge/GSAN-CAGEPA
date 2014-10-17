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
 * Thiago Silva Toscano de Brito
 * Vivianne Barbosa Sousa
 * 
 * GSANPCG
 * Andr� Rangel Nishimura
 * Eduardo Henrique Bandeira Carneiro da Silva
 * Lucas Daniel Souza de Medeiros
 * Saulo Vasconcelos de Lima
 * Thiago Toscano
 * Virginia Santos de Melo
 *
 * Este programa � software livre; voc� pode redistribu�-lo e/ou
 * modific�-lo sob os termos de Licen�a P�blica Geral GNU, conforme
 * publicada pela Free Software Foundation; vers�o 2 da Licen�a.
 * Este programa � distribu�do na expectativa de ser �til, mas SEM
 * QUALQUER GARANTIA; sem mesmo a garantia impl�cita de
 * COMERCIALIZA��O ou de ADEQUA��O A QUALQUER PROP�SITO EM
 * PARTICULAR. Consulte a Licen�a P�blica Geral GNU para obter mais detalhes.
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU
 * junto com este programa; se n�o, escreva para Free Software
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
 *       Conclus�o do funcionalidade
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
		// Mudar isso quando tiver esquema de seguran�a
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