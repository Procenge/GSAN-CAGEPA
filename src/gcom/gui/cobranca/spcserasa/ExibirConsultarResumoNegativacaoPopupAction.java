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

package gcom.gui.cobranca.spcserasa;

import gcom.gui.GcomAction;

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
 * @author Ana Maria
 * @date 09/11/2006
 */
public class ExibirConsultarResumoNegativacaoPopupAction
				extends GcomAction {

	/**
	 * < <Descrição do método>>
	 * 
	 * @param actionMapping
	 *            Descrição do parâmetro
	 * @param actionForm
	 *            Descrição do parâmetro
	 * @param httpServletRequest
	 *            Descrição do parâmetro
	 * @param httpServletResponse
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta a ação de retorno
		ActionForward retorno = actionMapping.findForward("consultarResumoNegativacaoPopup");

		HttpSession sessao = getSessao(httpServletRequest);

		BigDecimal somatorioValorDebito = new BigDecimal(0);
		if(httpServletRequest.getParameter("valorTotal") != null){
			somatorioValorDebito = new BigDecimal(httpServletRequest.getParameter("valorTotal"));
		}
		sessao.setAttribute("somatorioValorDebito", somatorioValorDebito);

		String descricaoIncluidas = (String) httpServletRequest.getParameter("descricao");
		sessao.setAttribute("descricaoIncluidas", descricaoIncluidas);

		String stNegativacao = (String) httpServletRequest.getParameter("stNegociacao");
		String situacaoNegativacao = "";
		if(stNegativacao.equals("1")){
			situacaoNegativacao = "NEGATIVAÇÕES INCLUÍDAS";
			sessao.setAttribute("situacaoNegativacao", situacaoNegativacao);

		}else if(stNegativacao.equals("2")){
			situacaoNegativacao = "NEGATIVAÇÕES INCLUÍDAS E CONFORMADAS";
			sessao.setAttribute("situacaoNegativacao", situacaoNegativacao);

		}else if(stNegativacao.equals("3")){
			situacaoNegativacao = "NEGATIVAÇÕES INCLUÍDAS E NÃO CONFORMADAS";
			sessao.setAttribute("situacaoNegativacao", situacaoNegativacao);

		}
		// Montagem Valores Detalhe PopUp
		// ////////////// *** VALOR PENDENTE *** ////////////////
		Collection<NegativacaoHelper> colecaoResumoNegativacaoDetalhePopUp = new ArrayList();
		NegativacaoHelper retumoTotaisDetalhePopUp = new NegativacaoHelper();
		retumoTotaisDetalhePopUp.setDescricao("PENDENTE");
		BigDecimal valorPendente = new BigDecimal(0);
		if(httpServletRequest.getParameter("valorPendente") != null){
			valorPendente = new BigDecimal(httpServletRequest.getParameter("valorPendente"));
		}
		// Valor
		retumoTotaisDetalhePopUp.setValorDinamico(valorPendente);
		// Percentual
		retumoTotaisDetalhePopUp
						.setPercentualValor(new BigDecimal((valorPendente.doubleValue() * 100) / somatorioValorDebito.doubleValue()));
		colecaoResumoNegativacaoDetalhePopUp.add(retumoTotaisDetalhePopUp);

		// //////////////*** VALOR PAGO *** ////////////////
		retumoTotaisDetalhePopUp = new NegativacaoHelper();
		retumoTotaisDetalhePopUp.setDescricao("PAGO");
		BigDecimal valorPago = new BigDecimal(0);
		if(httpServletRequest.getParameter("valorPago") != null){
			valorPago = new BigDecimal(httpServletRequest.getParameter("valorPago"));
		}
		// Valor
		retumoTotaisDetalhePopUp.setValorDinamico(valorPago);
		// Percentual
		retumoTotaisDetalhePopUp.setPercentualValor(new BigDecimal((valorPago.doubleValue() * 100) / somatorioValorDebito.doubleValue()));
		colecaoResumoNegativacaoDetalhePopUp.add(retumoTotaisDetalhePopUp);

		// //////////////*** VALOR PARCELADO *** ////////////////
		retumoTotaisDetalhePopUp = new NegativacaoHelper();
		retumoTotaisDetalhePopUp.setDescricao("PARCELADO");
		BigDecimal valorParcelado = new BigDecimal(0);
		if(httpServletRequest.getParameter("valorParcelado") != null){
			valorParcelado = new BigDecimal(httpServletRequest.getParameter("valorParcelado"));
		}
		// Valor
		retumoTotaisDetalhePopUp.setValorDinamico(valorParcelado);
		// Percentual
		retumoTotaisDetalhePopUp.setPercentualValor(new BigDecimal((valorParcelado.doubleValue() * 100)
						/ somatorioValorDebito.doubleValue()));
		colecaoResumoNegativacaoDetalhePopUp.add(retumoTotaisDetalhePopUp);

		// //////////////*** VALOR CANCELADO *** ////////////////
		retumoTotaisDetalhePopUp = new NegativacaoHelper();
		retumoTotaisDetalhePopUp.setDescricao("CANCELADO");
		BigDecimal valorCancelado = new BigDecimal(0);
		if(httpServletRequest.getParameter("valorCancelado") != null){
			valorCancelado = new BigDecimal(httpServletRequest.getParameter("valorCancelado"));
		}
		// Valor
		retumoTotaisDetalhePopUp.setValorDinamico(valorCancelado);
		// Percentual
		retumoTotaisDetalhePopUp.setPercentualValor(new BigDecimal((valorCancelado.doubleValue() * 100)
						/ somatorioValorDebito.doubleValue()));
		colecaoResumoNegativacaoDetalhePopUp.add(retumoTotaisDetalhePopUp);

		sessao.setAttribute("colecaoResumoNegativacaoDetalhePopUp", colecaoResumoNegativacaoDetalhePopUp);

		// // Pesquisa os dados do resumo da Negociacao agrupando por cobrancaDebitoSituacao
		// Collection<NegativacaoHelper>colecaoResumoNegativacao =
		// fachada.consultarResumoNegativacao(dadosConsultaNegativacaoHelper,indicadorSQL);
		//		
		// Iterator itcolecaoResumoNegativacao = colecaoResumoNegativacao.iterator();
		// BigDecimal valorDebito = new BigDecimal(0);
		// while(itcolecaoResumoNegativacao.hasNext()){
		// NegativacaoHelper retumoTotais = (NegativacaoHelper)itcolecaoResumoNegativacao.next();
		// valorDebito = retumoTotais.getSomatorioValorDebito();
		//			
		// if(retumoTotais.getDescricao().equals("PENDENTE")){
		// // Valor
		// retumoTotais.setValorDinamico(retumoTotais.getSomatorioValorPendente());
		// // Calculo dos Percentuais
		// BigDecimal percentValores = new
		// BigDecimal((retumoTotais.getSomatorioValorPendente().doubleValue() *100) /
		// valorDebito.doubleValue());
		// retumoTotais.setPercentualValor(percentValores);
		// }else if(retumoTotais.getDescricao().equals("PAGO")){
		// // Valor
		// retumoTotais.setValorDinamico(retumoTotais.getSomatorioValorPago());
		// // Calculo dos Percentuais
		// BigDecimal percentValores = new
		// BigDecimal((retumoTotais.getSomatorioValorPago().doubleValue() *100) /
		// valorDebito.doubleValue());
		// retumoTotais.setPercentualValor(percentValores);
		// }else if(retumoTotais.getDescricao().equals("PARCELADO")){
		// // Valor
		// retumoTotais.setValorDinamico(retumoTotais.getSomatorioValorParcelado());
		// // Calculo dos Percentuais
		// BigDecimal percentValores = new
		// BigDecimal((retumoTotais.getSomatorioValorParcelado().doubleValue() *100) /
		// valorDebito.doubleValue());
		// retumoTotais.setPercentualValor(percentValores);
		// }else if(retumoTotais.getDescricao().equals("CANCELADO")){
		// // Valor
		// retumoTotais.setValorDinamico(retumoTotais.getSomatoriovalorCancelado());
		// // Calculo dos Percentuais
		// BigDecimal percentValores = new
		// BigDecimal((retumoTotais.getSomatoriovalorCancelado().doubleValue() *100) /
		// valorDebito.doubleValue());
		// retumoTotais.setPercentualValor(percentValores);
		// }
		// }
		// sessao.setAttribute("colecaoResumoNegativacao", colecaoResumoNegativacao);

		return retorno;
	}
}
