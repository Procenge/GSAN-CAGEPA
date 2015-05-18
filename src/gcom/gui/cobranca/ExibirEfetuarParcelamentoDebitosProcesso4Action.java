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

import gcom.cobranca.bean.ContaValoresHelper;
import gcom.cobranca.bean.DebitoACobrarValoresHelper;
import gcom.cobranca.bean.GuiaPagamentoValoresHelper;
import gcom.cobranca.bean.OpcoesParcelamentoHelper;
import gcom.cobranca.parcelamento.Parcelamento;
import gcom.cobranca.parcelamento.ParcelamentoConfiguracaoPrestacao;
import gcom.fachada.Fachada;
import gcom.faturamento.debito.DebitoTipo;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.parametrizacao.cobranca.ExecutorParametrosCobranca;
import gcom.util.parametrizacao.cobranca.ParametroCobranca;
import gcom.util.parametrizacao.cobranca.parcelamento.ParametroParcelamento;

import java.math.BigDecimal;
import java.util.ArrayList;
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
		Fachada fachada = Fachada.getInstancia();
		HttpSession sessao = httpServletRequest.getSession(false);

		if("sim".equalsIgnoreCase((String) httpServletRequest.getAttribute("popupEfetuarParcelamento"))
						|| "sim".equalsIgnoreCase((String) httpServletRequest.getParameter("popupEfetuarParcelamento"))){
			httpServletRequest.setAttribute("popupEfetuarParcelamento", "sim");
		}

		// Carrega parametro do sistema para visuaizar o botão de Imprimir Termo
		// antes da conclusão do parcelamento.
		try{
			if(ParametroParcelamento.P_INDICADOR_EMISSAO_TERMO_PARCELAMENTO_ANTES_CONCLUSAO.executar().equals("1")){
				httpServletRequest.setAttribute("HabilitarEmissaoTermoParcelamento", "sim");
			}else{
				httpServletRequest.setAttribute("HabilitarEmissaoTermoParcelamento", "nao");
			}
		}catch(ControladorException e1){
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Short pIndicadorPossuiDividaAtiva = ConstantesSistema.NAO;
		try{

			pIndicadorPossuiDividaAtiva = Util.converterStringParaShort(ParametroCobranca.P_INDICADOR_POSSUI_DIVIDA_ATIVA.executar()
							.toString());

		}catch(ControladorException e){

			throw new ActionServletException("atencao.sistemaparametro_inexistente", null, "P_INDICADOR_POSSUI_DIVIDA_ATIVA");
		}

		sessao.setAttribute("pIndicadorPossuiDividaAtiva", pIndicadorPossuiDividaAtiva);

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
		BigDecimal valorTotalDescontos = Util.formatarMoedaRealparaBigDecimal((String) efetuarParcelamentoDebitosActionForm
						.get("valorTotalDescontos"));
		BigDecimal valorFinalFinanciamento = new BigDecimal("0.00");

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

					efetuarParcelamentoDebitosActionForm.set("valorASerParcelado",
									Util.formatarMoedaReal(opcoesParcelamento.getValorAParcelar()));

					efetuarParcelamentoDebitosActionForm.set("valorNegociado",
									Util.formatarMoedaReal(opcoesParcelamento.getValorDebitoComDescontoNaPrestacao()));

					efetuarParcelamentoDebitosActionForm.set("valorSucumbenciaAtualCobradoEntradaParc",
									Util.formatarMoedaReal(opcoesParcelamento.getValorSucumbenciaAtualCobradoEntradaParc()));
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

		/**
		 * [UC0214] Efetuar Parcelamento de Débitos
		 * [SB0052] Exibir Valores dos Débitos por Situação de Dívida Ativa
		 */
		try{
			if(ParametroParcelamento.P_INDICADOR_EXIBIR_DEBITOS_SITUACAO_DIVIDAATIVA_PARCELAMENTO.executar().equals("1")){
				httpServletRequest.setAttribute("exibirDebitosSituacaoDividaAtivaParc", "S");
			}else{
				httpServletRequest.removeAttribute("exibirDebitosSituacaoDividaAtivaParc");
			}
		}catch(ControladorException e1){
			e1.printStackTrace();
		}

		// Habilita o uso do campo valor de entrada
		httpServletRequest.getSession().setAttribute("somenteLeituraValorEntradaInformado", "false");

		BigDecimal valorTotalContaNormal = BigDecimal.ZERO;
		BigDecimal valorTotalContaDividaAtiva = BigDecimal.ZERO;
		BigDecimal valorTotalContaExecFiscal = BigDecimal.ZERO;
		BigDecimal valorTotalContaAcresNormal = BigDecimal.ZERO;
		BigDecimal valorTotalContaAcresDividaAtiva = BigDecimal.ZERO;
		BigDecimal valorTotalContaAcresExecFiscal = BigDecimal.ZERO;

		BigDecimal valorTotalGuiaNormal = BigDecimal.ZERO;
		BigDecimal valorTotalGuiaDividaAtiva = BigDecimal.ZERO;
		BigDecimal valorTotalGuiaExecFiscal = BigDecimal.ZERO;
		BigDecimal valorTotalGuiaAcresNormal = BigDecimal.ZERO;
		BigDecimal valorTotalGuiaAcresDividaAtiva = BigDecimal.ZERO;
		BigDecimal valorTotalGuiaAcresExecFiscal = BigDecimal.ZERO;

		BigDecimal valorTotalDebitoACobrarServicoNormal = BigDecimal.ZERO;
		BigDecimal valorTotalDebitoACobrarParcelamentoNormal = BigDecimal.ZERO;

		Collection<ContaValoresHelper> colecaoContaValoresNegociacao = null;
		if(sessao.getAttribute("colecaoContaValores") != null){
			colecaoContaValoresNegociacao = (Collection<ContaValoresHelper>) sessao.getAttribute("colecaoContaValores");
		}else{
			colecaoContaValoresNegociacao = (Collection<ContaValoresHelper>) sessao.getAttribute("colecaoContaValoresImovel");
		}

		Collection<GuiaPagamentoValoresHelper> colecaoGuiaPagamentoHelper = null;
		String indicadorGuiasPagamento = (String) efetuarParcelamentoDebitosActionForm.get("indicadorGuiasPagamento");
		if(sessao.getAttribute("colecaoGuiaPagamentoValoresSelecionadas") != null || indicadorGuiasPagamento.equals("2")){
			colecaoGuiaPagamentoHelper = (Collection<GuiaPagamentoValoresHelper>) sessao
							.getAttribute("colecaoGuiaPagamentoValoresSelecionadas");
		}else{
			colecaoGuiaPagamentoHelper = (Collection<GuiaPagamentoValoresHelper>) sessao.getAttribute("colecaoGuiaPagamentoValoresImovel");
		}

		Collection<DebitoACobrarValoresHelper> colecaoDebitoACobrarValores = new ArrayList();
		colecaoDebitoACobrarValores = (Collection<DebitoACobrarValoresHelper>) sessao.getAttribute("colecaoDebitoACobrarValores");

		if(colecaoContaValoresNegociacao != null && !colecaoContaValoresNegociacao.isEmpty()){
			Iterator<ContaValoresHelper> contaValoresNegociacao = colecaoContaValoresNegociacao.iterator();
			while(contaValoresNegociacao.hasNext()){
				ContaValoresHelper contaValoresHelperNegociacao = contaValoresNegociacao.next();
				if(sessao.getAttribute("colecaoContaValores") != null){
					// Caso não tenha marcado a conta
					if(contaValoresHelperNegociacao.getIndicadorContasDebito() == null){
						BigDecimal valoTotalAcrescimentoAux = BigDecimal.ZERO;

						if(contaValoresHelperNegociacao.getValorMulta() != null){
							valoTotalAcrescimentoAux = valoTotalAcrescimentoAux.add(contaValoresHelperNegociacao.getValorMulta());
						}

						if(contaValoresHelperNegociacao.getValorJurosMora() != null){
							valoTotalAcrescimentoAux = valoTotalAcrescimentoAux.add(contaValoresHelperNegociacao.getValorJurosMora());
						}

						if(contaValoresHelperNegociacao.getValorAtualizacaoMonetaria() != null){
							valoTotalAcrescimentoAux = valoTotalAcrescimentoAux.add(contaValoresHelperNegociacao
											.getValorAtualizacaoMonetaria());
						}

						// Valores dos Débitos do Imóvel por Situação de Dívida Ativa
						if(contaValoresHelperNegociacao.getConta().getIndicadorExecucaoFiscal().equals(Short.valueOf("1"))){
							valorTotalContaExecFiscal = valorTotalContaExecFiscal.add(contaValoresHelperNegociacao.getValorTotalConta());
							valorTotalContaAcresExecFiscal = valorTotalContaAcresExecFiscal.add(valoTotalAcrescimentoAux);
						}else if(contaValoresHelperNegociacao.getConta().getIndicadorDividaAtiva().equals(Short.valueOf("1"))){
							valorTotalContaDividaAtiva = valorTotalContaDividaAtiva.add(contaValoresHelperNegociacao.getValorTotalConta());
							valorTotalContaAcresDividaAtiva = valorTotalContaAcresDividaAtiva.add(valoTotalAcrescimentoAux);
						}else{
							valorTotalContaNormal = valorTotalContaNormal.add(contaValoresHelperNegociacao.getValorTotalConta());
							valorTotalContaAcresNormal = valorTotalContaAcresNormal.add(valoTotalAcrescimentoAux);
						}
					}
				}
			}
		}

		if(colecaoGuiaPagamentoHelper != null && !colecaoGuiaPagamentoHelper.isEmpty()){
			Iterator<GuiaPagamentoValoresHelper> guiaPagamentoValoresNegociacao = colecaoGuiaPagamentoHelper.iterator();
			while(guiaPagamentoValoresNegociacao.hasNext()){
				GuiaPagamentoValoresHelper guiaPagamentoValoresHelperNegociacao = guiaPagamentoValoresNegociacao.next();

				BigDecimal valoTotalAcrescimentoAux = BigDecimal.ZERO;

				if(guiaPagamentoValoresHelperNegociacao.getValorMulta() != null){
					valoTotalAcrescimentoAux = valoTotalAcrescimentoAux.add(guiaPagamentoValoresHelperNegociacao.getValorMulta());
				}

				if(guiaPagamentoValoresHelperNegociacao.getValorJurosMora() != null){
					valoTotalAcrescimentoAux = valoTotalAcrescimentoAux.add(guiaPagamentoValoresHelperNegociacao.getValorJurosMora());
				}

				if(guiaPagamentoValoresHelperNegociacao.getValorAtualizacaoMonetaria() != null){
					valoTotalAcrescimentoAux = valoTotalAcrescimentoAux.add(guiaPagamentoValoresHelperNegociacao
									.getValorAtualizacaoMonetaria());
				}

				if(guiaPagamentoValoresHelperNegociacao.getIndicadorExecucaoFiscal().equals(Short.valueOf("1"))){
					valorTotalGuiaExecFiscal = valorTotalGuiaExecFiscal.add(guiaPagamentoValoresHelperNegociacao.getValorTotalPrestacao());
					valorTotalGuiaAcresExecFiscal = valorTotalGuiaAcresExecFiscal.add(valoTotalAcrescimentoAux);
				}else if(guiaPagamentoValoresHelperNegociacao.getIndicadorDividaAtiva().equals(Short.valueOf("1"))){
					valorTotalGuiaDividaAtiva = valorTotalGuiaDividaAtiva
									.add(guiaPagamentoValoresHelperNegociacao.getValorTotalPrestacao());
					valorTotalGuiaAcresDividaAtiva = valorTotalGuiaAcresDividaAtiva.add(valoTotalAcrescimentoAux);
				}else{
					valorTotalGuiaNormal = valorTotalGuiaNormal.add(guiaPagamentoValoresHelperNegociacao.getValorTotalPrestacao());
					valorTotalGuiaAcresNormal = valorTotalGuiaAcresNormal.add(valoTotalAcrescimentoAux);
				}
			}
		}

		String chavesDebitosACobrar = (String) efetuarParcelamentoDebitosActionForm.get("chavesDebitosACobrar");
		Collection<DebitoACobrarValoresHelper> colecaoDebitoACobrarSelecionados = fachada.retornarDebitoACobrarValoresSelecionadas(
						chavesDebitosACobrar, colecaoDebitoACobrarValores);

		if(colecaoDebitoACobrarSelecionados != null && !colecaoDebitoACobrarSelecionados.isEmpty()){
			// Parâmetro que contém os tipos de financiamentos de parcelamento
			String[] parametroFinanciamentoTipoParcelamento = null;
			ArrayList<Integer> colecaoFinanciamentosTiposParcelamento = new ArrayList<Integer>();

			try{
				// Recupera os valores do parâmetro
				parametroFinanciamentoTipoParcelamento = ((String) ParametroParcelamento.P_FINANCIAMENTO_TIPO_PARCELAMENTO.executar())
								.split(",");

				// carrega valores dos tipos de finaciamentos para parcelamento
				for(String parametroFinanciamentoTipo : parametroFinanciamentoTipoParcelamento){

					colecaoFinanciamentosTiposParcelamento.add(Integer.valueOf(parametroFinanciamentoTipo));
				}
			}catch(ControladorException e){
				e.printStackTrace();
			}

			String chavesDebitoACobrarString = (String) efetuarParcelamentoDebitosActionForm.get("chavesDebitosACobrar");
			Iterator<DebitoACobrarValoresHelper> debitoACobrarValoresNegociacao = colecaoDebitoACobrarSelecionados.iterator();
			while(debitoACobrarValoresNegociacao.hasNext()){
				DebitoACobrarValoresHelper debitoACobrarNegociacao = debitoACobrarValoresNegociacao.next();

				// [FS0022]-Verificar existência de juros sobre imóvel
				if(debitoACobrarNegociacao.getIdDebitoTipo() != null
								&& !debitoACobrarNegociacao.getIdDebitoTipo().equals(DebitoTipo.JUROS_SOBRE_PARCELAMENTO)
								&& chavesDebitoACobrarString.toUpperCase().contains(debitoACobrarNegociacao.getIdRegistro().toUpperCase())){
					if(!Util.isVazioOrNulo(colecaoFinanciamentosTiposParcelamento)){

						// Debitos A Cobrar - Serviço
						if(!colecaoFinanciamentosTiposParcelamento.contains(debitoACobrarNegociacao.getIdFinanciamentoTipo())){
							valorTotalDebitoACobrarServicoNormal = valorTotalDebitoACobrarServicoNormal.add(debitoACobrarNegociacao
											.getValorRestanteASerCobrado());
						}

						// Debitos A Cobrar - Parcelamento
						if(colecaoFinanciamentosTiposParcelamento.contains(debitoACobrarNegociacao.getIdFinanciamentoTipo())){
							// [SB0001] Obter Valores de Curto e Longo Prazo
							valorTotalDebitoACobrarParcelamentoNormal = valorTotalDebitoACobrarParcelamentoNormal
											.add(debitoACobrarNegociacao.getValorRestanteASerCobrado());
						}

					}
				}
			}
		}

		efetuarParcelamentoDebitosActionForm.set("valorTotalContaNormal", Util.formatarMoedaReal(valorTotalContaNormal));
		efetuarParcelamentoDebitosActionForm.set("valorTotalContaDividaAtiva", Util.formatarMoedaReal(valorTotalContaDividaAtiva));
		efetuarParcelamentoDebitosActionForm.set("valorTotalContaExecFiscal", Util.formatarMoedaReal(valorTotalContaExecFiscal));
		efetuarParcelamentoDebitosActionForm.set("valorTotalContaAcresNormal", Util.formatarMoedaReal(valorTotalContaAcresNormal));
		efetuarParcelamentoDebitosActionForm
						.set("valorTotalContaAcresDividaAtiva", Util.formatarMoedaReal(valorTotalContaAcresDividaAtiva));
		efetuarParcelamentoDebitosActionForm.set("valorTotalContaAcresExecFiscal", Util.formatarMoedaReal(valorTotalContaAcresExecFiscal));

		efetuarParcelamentoDebitosActionForm.set("valorTotalGuiaNormal", Util.formatarMoedaReal(valorTotalGuiaNormal));
		efetuarParcelamentoDebitosActionForm.set("valorTotalGuiaDividaAtiva", Util.formatarMoedaReal(valorTotalGuiaDividaAtiva));
		efetuarParcelamentoDebitosActionForm.set("valorTotalGuiaExecFiscal", Util.formatarMoedaReal(valorTotalGuiaExecFiscal));
		efetuarParcelamentoDebitosActionForm.set("valorTotalGuiaAcresNormal", Util.formatarMoedaReal(valorTotalGuiaAcresNormal));
		efetuarParcelamentoDebitosActionForm.set("valorTotalGuiaAcresDividaAtiva", Util.formatarMoedaReal(valorTotalGuiaAcresDividaAtiva));
		efetuarParcelamentoDebitosActionForm.set("valorTotalGuiaAcresExecFiscal", Util.formatarMoedaReal(valorTotalGuiaAcresExecFiscal));

		efetuarParcelamentoDebitosActionForm.set("valorTotalDebitoACobrarServicoNormal",
						Util.formatarMoedaReal(valorTotalDebitoACobrarServicoNormal));
		efetuarParcelamentoDebitosActionForm.set("valorTotalDebitoACobrarParcelamentoNormal",
						Util.formatarMoedaReal(valorTotalDebitoACobrarParcelamentoNormal));

		// FIM

		return retorno;
	}
}