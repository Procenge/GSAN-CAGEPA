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
 * GSANPCG
 * André Nishimura
 * Eduardo Henrique Bandeira Carneiro da Silva
 * Saulo Vasconcelos de Lima
 * Virginia Santos de Melo
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

package gcom.gui.cobranca;

import gcom.cobranca.bean.OpcoesParcelamentoHelper;
import gcom.cobranca.parcelamento.Parcelamento;
import gcom.cobranca.parcelamento.ParcelamentoConfiguracaoPrestacao;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.parametrizacao.cobranca.ExecutorParametrosCobranca;
import gcom.util.parametrizacao.cobranca.ParametroCobranca;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

/**
 * Permite efetuar o parcelamento dos débitos de um imóvel
 * [UC0214] Efetuar Parcelamento de Débitos
 * 
 * @author Roberta Costa
 * @date 24/01/2006
 */
public class ExibirEfetuarParcelamentoDebitosProcesso4Action
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("processo4");

		HttpSession sessao = httpServletRequest.getSession(false);

		if("sim".equalsIgnoreCase((String) httpServletRequest.getAttribute("popupEfetuarParcelamento"))
						|| "sim".equalsIgnoreCase((String) httpServletRequest.getParameter("popupEfetuarParcelamento"))){
			httpServletRequest.setAttribute("popupEfetuarParcelamento", "sim");
		}

		DynaActionForm efetuarParcelamentoDebitosActionForm = (DynaActionForm) actionForm;

		// Verifica se entrou na aba de Negociação
		Collection<OpcoesParcelamentoHelper> colecaoOpcoesParcelamento = (Collection<OpcoesParcelamentoHelper>) sessao
						.getAttribute("colecaoOpcoesParcelamento");
		Collection<ParcelamentoConfiguracaoPrestacao> colecaoParcelamentoConfiguracaoPrestacao = (Collection<ParcelamentoConfiguracaoPrestacao>) sessao
						.getAttribute("colecaoParcelamentoConfiguracaoPrestacao");

		if(Util.isVazioOrNulo(colecaoOpcoesParcelamento) && Util.isVazioOrNulo(colecaoParcelamentoConfiguracaoPrestacao)){

			throw new ActionServletException("atencao.parametros.obrigatorios.nao.selecionados");
		}

		// Pega dados do formulário
		BigDecimal valorDebitoTotalAtualizado = Util.formatarMoedaRealparaBigDecimal((String) efetuarParcelamentoDebitosActionForm
						.get("valorDebitoTotalAtualizado"));

		// 5.1.4 Valor do desconto
		BigDecimal valorASerNegociado = new BigDecimal("0.00");
		BigDecimal valorTotalDescontos = Util.formatarMoedaRealparaBigDecimal((String) efetuarParcelamentoDebitosActionForm
						.get("valorTotalDescontos"));
		BigDecimal valorFinalFinanciamento = new BigDecimal("0.00");
		BigDecimal valorASerParcelado = new BigDecimal("0.00");

		efetuarParcelamentoDebitosActionForm.set("indicadorConfirmacaoParcelamento", "1");

		// 5.1.6 Condições da Negociação
		if(colecaoOpcoesParcelamento != null && !colecaoOpcoesParcelamento.isEmpty()
						&& !Util.isVazioOuBranco(efetuarParcelamentoDebitosActionForm.get("indicadorQuantidadeParcelas"))){

			Iterator<OpcoesParcelamentoHelper> opcoesParcelamentoValores = colecaoOpcoesParcelamento.iterator();
			while(opcoesParcelamentoValores.hasNext()){
				OpcoesParcelamentoHelper opcoesParcelamento = opcoesParcelamentoValores.next();
				if(((String) efetuarParcelamentoDebitosActionForm.get("indicadorQuantidadeParcelas")).equals(opcoesParcelamento
								.getQuantidadePrestacao().toString())){
					efetuarParcelamentoDebitosActionForm.set("parcelaEscolhida", opcoesParcelamento.getQuantidadePrestacao().toString());

					efetuarParcelamentoDebitosActionForm.set("valorParcelaEscolhida", Util.formatarMoedaReal(opcoesParcelamento
									.getValorPrestacao()));

					efetuarParcelamentoDebitosActionForm.set("valorEntradaInformado", Util.formatarMoedaReal(opcoesParcelamento
									.getValorEntradaMinima()));
		
					valorFinalFinanciamento = opcoesParcelamento.getValorPrestacao().setScale(Parcelamento.CASAS_DECIMAIS,
									Parcelamento.TIPO_ARREDONDAMENTO).multiply(new BigDecimal(opcoesParcelamento.getQuantidadePrestacao()));

					efetuarParcelamentoDebitosActionForm.set("taxaJurosEscolhida", Util
									.formatarMoedaReal(opcoesParcelamento.getTaxaJuros()));

					BigDecimal valorTotalDescontosSemAcrescimos = (BigDecimal) sessao.getAttribute("valorTotalDescontosSemAcrescimos");

					BigDecimal valorDescontoAcrescimosImpontualidadeNaPrestacao = opcoesParcelamento
									.getValorDescontoAcrescimosImpontualidadeNaPrestacao();

					if(valorDescontoAcrescimosImpontualidadeNaPrestacao != null
									&& valorDescontoAcrescimosImpontualidadeNaPrestacao.compareTo(BigDecimal.ZERO) > 0){

						valorTotalDescontos = valorDescontoAcrescimosImpontualidadeNaPrestacao;
					}
					valorASerParcelado = valorDebitoTotalAtualizado.subtract(opcoesParcelamento.getValorEntradaMinima().add(
									valorTotalDescontos));

					efetuarParcelamentoDebitosActionForm.set("valorASerParcelado", Util.formatarMoedaReal(valorASerParcelado));
				}
			}
		}else if(!Util.isVazioOrNulo(colecaoParcelamentoConfiguracaoPrestacao)){

			BigDecimal somatorioProdutoValorVersosQtdPrestacoes = BigDecimal.ZERO;

			for(ParcelamentoConfiguracaoPrestacao parcelamentoConfiguracaoPrestacao : colecaoParcelamentoConfiguracaoPrestacao){

				BigDecimal produtoPrestacaoVersusValor = parcelamentoConfiguracaoPrestacao.getValorPrestacao()
								.multiply(new BigDecimal(parcelamentoConfiguracaoPrestacao.getNumeroPrestacao().toString()))
								.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);

				somatorioProdutoValorVersosQtdPrestacoes = somatorioProdutoValorVersosQtdPrestacoes.add(produtoPrestacaoVersusValor);
			}

			efetuarParcelamentoDebitosActionForm
							.set("valorASerParcelado", Util.formatarMoedaReal(somatorioProdutoValorVersosQtdPrestacoes));

		}

		valorASerNegociado.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
		valorASerNegociado = valorDebitoTotalAtualizado.subtract(valorTotalDescontos);
		efetuarParcelamentoDebitosActionForm.set("valorNegociado", Util.formatarMoedaReal(valorASerNegociado));

		efetuarParcelamentoDebitosActionForm.set("valorFinalFinanciamento", Util.formatarMoedaReal(valorFinalFinanciamento));

		efetuarParcelamentoDebitosActionForm.set("valorTotalDescontos", Util.formatarMoedaReal(valorTotalDescontos));

		// verifica se vai exibir as opções de Geração de Débito
		// de acordo com o valor do parâmetro
		String[] opcoesGeracaoDebito = null;
		try{
			opcoesGeracaoDebito = ((String) ParametroCobranca.P_COBRANCA_PARCELAMENTO_GUIA.executar(ExecutorParametrosCobranca
							.getInstancia())).split(",");
		}catch(ControladorException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(opcoesGeracaoDebito != null){
			for(int i = 0; i < opcoesGeracaoDebito.length; i++){
				if(opcoesGeracaoDebito[i].equals("1")){
					efetuarParcelamentoDebitosActionForm.set("opcoesGeracaoDebito", "1");
				}else{
					efetuarParcelamentoDebitosActionForm.set("opcoesGeracaoDebito", "2");
				}
			}
		}

		return retorno;
	}
}