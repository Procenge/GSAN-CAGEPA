
package gcom.gui.cobranca;

import gcom.cobranca.bean.ContaValoresHelper;
import gcom.cobranca.bean.GuiaPagamentoValoresHelper;
import gcom.cobranca.parcelamento.Parcelamento;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.util.ConstantesSistema;
import gcom.util.Util;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.DynaActionForm;

/**
 * Classe Auxiliar que contém a lógica de cálculos do Parcelamento,
 * baseada no funcionamento da tela de Efetuar Parcelamentos
 */
class CalculadorParcelamentoHelper {

	public static void recalcularParcelamento(HttpServletRequest httpServletRequest, Fachada fachada, HttpSession sessao,
					DynaActionForm efetuarParcelamentoDebitosActionForm, String verificaCalcula){

		// String chavesSucumbenciasConta = (String)
		// efetuarParcelamentoDebitosActionForm.get("chavesSucumbenciasConta");
		// String chavesSucumbenciasGuia = (String)
		// efetuarParcelamentoDebitosActionForm.get("chavesSucumbenciasGuia");

		String indicadorAcrescimosImpotualidade = (String) efetuarParcelamentoDebitosActionForm.get("indicadorAcrescimosImpotualidade");
		String chavesPrestacoes = (String) efetuarParcelamentoDebitosActionForm.get("chavesPrestacoes");
		// Pega variáveis da sessão
		BigDecimal valorTotalContaValores = (BigDecimal) sessao.getAttribute("valorTotalContaValores");
		BigDecimal valorAcrescimosImpontualidade = BigDecimal.ZERO;
		BigDecimal valorDebitoTotalAtualizado = BigDecimal.ZERO;
		if(!"".equals(efetuarParcelamentoDebitosActionForm.get("valorDebitoTotalAtualizado"))
						&& efetuarParcelamentoDebitosActionForm.get("valorDebitoTotalAtualizado") != null){
			valorDebitoTotalAtualizado = Util.formatarMoedaRealparaBigDecimal((String) efetuarParcelamentoDebitosActionForm
							.get("valorDebitoTotalAtualizado"));
		}

		// Atribui 1 a calcula na sessão
		sessao.setAttribute("calcula", "1");

		Collection<ContaValoresHelper> colecaoContaValores = (Collection<ContaValoresHelper>) sessao.getAttribute("colecaoContaValores");

		BigDecimal valorContaNB = BigDecimal.ZERO;
		BigDecimal valorAcrescimosNB = BigDecimal.ZERO;
		BigDecimal valorAcrescimosEN = BigDecimal.ZERO;
		BigDecimal valorEntradaParcelamento = BigDecimal.ZERO;

		BigDecimal valorAtualizacaoMonetaria = BigDecimal.ZERO;
		BigDecimal valorJurosMora = BigDecimal.ZERO;
		BigDecimal valorMulta = BigDecimal.ZERO;
		BigDecimal valorAtualizacaoMonetariaSucumbencia = BigDecimal.ZERO;
		BigDecimal valorJurosMoraSucumbencia = BigDecimal.ZERO;
		BigDecimal valorSucumbencia = BigDecimal.ZERO;

		if(colecaoContaValores != null && !colecaoContaValores.isEmpty()){
			Iterator<ContaValoresHelper> contaValores = colecaoContaValores.iterator();
			while(contaValores.hasNext()){

				ContaValoresHelper contaValoresHelper = contaValores.next();
				if(indicadorAcrescimosImpotualidade.equals("1")){
					valorAcrescimosImpontualidade = valorAcrescimosImpontualidade.add(contaValoresHelper
									.getValorTotalContaValoresParcelamento().setScale(Parcelamento.CASAS_DECIMAIS,
													Parcelamento.TIPO_ARREDONDAMENTO));
				}

				if(verificaCalcula != null && verificaCalcula.equals("request")){
					if(httpServletRequest.getParameter("indicadorContasDebito" + contaValoresHelper.getConta().getId().toString()) != null){

						String indice = httpServletRequest.getParameter("indicadorContasDebito"
										+ contaValoresHelper.getConta().getId().toString());

						contaValoresHelper.setIndicadorContasDebito(Integer.valueOf(indice));

						// Caso as contas sejam não baixadas(NB)
						if(indice.equals("2")){

							// Verifica se existe conta em revisão
							if(contaValoresHelper.getConta().getDataRevisao() != null){
								throw new ActionServletException("atencao.conta.em.revisao");
							}
							valorContaNB.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
							valorContaNB = valorContaNB.add(contaValoresHelper.getConta().getValorTotal());
							if(indicadorAcrescimosImpotualidade.equals("1")){
								valorAcrescimosNB.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
								valorAcrescimosNB = valorAcrescimosNB.add(contaValoresHelper.getValorTotalContaValoresParcelamento());
							}
						}else if("1".equals(indice)){
							if(indicadorAcrescimosImpotualidade.equals("1")){
								valorAcrescimosEN.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
								valorAcrescimosEN = valorAcrescimosEN.add(contaValoresHelper.getValorTotalContaValoresParcelamento());
							}

							valorAtualizacaoMonetaria.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
							valorAtualizacaoMonetaria = valorAtualizacaoMonetaria.add(contaValoresHelper.getValorAtualizacaoMonetaria()
											.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO));

							valorJurosMora.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
							valorJurosMora = valorJurosMora.add(contaValoresHelper.getValorJurosMora().setScale(
											Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO));

							valorMulta.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
							valorMulta = valorMulta.add(contaValoresHelper.getValorMulta().setScale(Parcelamento.CASAS_DECIMAIS,
											Parcelamento.TIPO_ARREDONDAMENTO));

							valorEntradaParcelamento.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
							valorEntradaParcelamento = valorEntradaParcelamento.add(contaValoresHelper.getValorTotalConta());
							sessao.setAttribute("marcadaEP", "true");
						}else{

							valorAtualizacaoMonetaria.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
							valorAtualizacaoMonetaria = valorAtualizacaoMonetaria.add(contaValoresHelper.getValorAtualizacaoMonetaria()
											.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO));

							valorJurosMora.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
							valorJurosMora = valorJurosMora.add(contaValoresHelper.getValorJurosMora().setScale(
											Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO));

							valorMulta.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
							valorMulta = valorMulta.add(contaValoresHelper.getValorMulta().setScale(Parcelamento.CASAS_DECIMAIS,
											Parcelamento.TIPO_ARREDONDAMENTO));

						}
					}else{

						valorAtualizacaoMonetaria.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
						if(contaValoresHelper.getValorAtualizacaoMonetaria() != null){
							valorAtualizacaoMonetaria = valorAtualizacaoMonetaria.add(contaValoresHelper.getValorAtualizacaoMonetaria()
											.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO));
						}

						valorJurosMora.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
						if(contaValoresHelper.getValorJurosMora() != null){
							valorJurosMora = valorJurosMora.add(contaValoresHelper.getValorJurosMora().setScale(
											Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO));
						}

						valorMulta.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
						if(contaValoresHelper.getValorMulta() != null){
							valorMulta = valorMulta.add(contaValoresHelper.getValorMulta().setScale(Parcelamento.CASAS_DECIMAIS,
											Parcelamento.TIPO_ARREDONDAMENTO));
						}
					}
				}else{
					// Se a conta está marcada como NB
					if(contaValoresHelper.getIndicadorContasDebito() != null && contaValoresHelper.getIndicadorContasDebito().equals(2)){
						valorContaNB.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
						valorContaNB = valorContaNB.add(contaValoresHelper.getConta().getValorTotal());
						valorAcrescimosNB.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
						valorAcrescimosNB = valorAcrescimosNB.add(contaValoresHelper.getValorTotalContaValoresParcelamento());

						// Se a conta está marcada como EN
					}else if(contaValoresHelper.getIndicadorContasDebito() != null
									&& contaValoresHelper.getIndicadorContasDebito().equals(1)){
						valorAcrescimosEN.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
						valorAcrescimosEN = valorAcrescimosEN.add(contaValoresHelper.getValorTotalContaValoresParcelamento());

						valorEntradaParcelamento.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
						valorEntradaParcelamento = valorEntradaParcelamento.add(contaValoresHelper.getValorTotalConta());

						valorAtualizacaoMonetaria.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
						valorAtualizacaoMonetaria = valorAtualizacaoMonetaria.add(contaValoresHelper.getValorAtualizacaoMonetaria()
										.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO));

						valorJurosMora.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
						valorJurosMora = valorJurosMora.add(contaValoresHelper.getValorJurosMora().setScale(Parcelamento.CASAS_DECIMAIS,
										Parcelamento.TIPO_ARREDONDAMENTO));

						valorMulta.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
						valorMulta = valorMulta.add(contaValoresHelper.getValorMulta().setScale(Parcelamento.CASAS_DECIMAIS,
										Parcelamento.TIPO_ARREDONDAMENTO));

						sessao.setAttribute("marcadaEP", "true");

						// Caso contrário, ou seja, a conta não está marcada nem como NB nem como EN
					}else{

						valorAtualizacaoMonetaria.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
						valorAtualizacaoMonetaria = valorAtualizacaoMonetaria.add(contaValoresHelper.getValorAtualizacaoMonetaria()
										.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO));

						valorJurosMora.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
						valorJurosMora = valorJurosMora.add(contaValoresHelper.getValorJurosMora().setScale(Parcelamento.CASAS_DECIMAIS,
										Parcelamento.TIPO_ARREDONDAMENTO));

						valorMulta.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
						valorMulta = valorMulta.add(contaValoresHelper.getValorMulta().setScale(Parcelamento.CASAS_DECIMAIS,
										Parcelamento.TIPO_ARREDONDAMENTO));

					}
				} // FIM ELSE

			}
			valorTotalContaValores.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
			valorTotalContaValores = valorTotalContaValores.subtract(valorContaNB);
			if(indicadorAcrescimosImpotualidade.equals("1")){
				valorAcrescimosImpontualidade.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
				// valorAcrescimosImpontualidade =
				// valorAcrescimosImpontualidade.subtract(valorAcrescimosNB).subtract(valorAcrescimosEN);
				valorAcrescimosImpontualidade = valorAcrescimosImpontualidade.subtract(valorAcrescimosNB);
			}

			// Calcula sempre em cima do valor do debito
			valorDebitoTotalAtualizado.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
			valorDebitoTotalAtualizado = valorDebitoTotalAtualizado.subtract(valorContaNB);
			valorDebitoTotalAtualizado.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
			valorDebitoTotalAtualizado = valorDebitoTotalAtualizado.subtract(valorAcrescimosNB);

			efetuarParcelamentoDebitosActionForm.set("valorTotalContaValores", Util.formatarMoedaReal(valorTotalContaValores));
		}

		BigDecimal valorGuiasForm = BigDecimal.ZERO;
		String valorGuiasPagamento = (String) efetuarParcelamentoDebitosActionForm.get("valorGuiasPagamento");
		if(!"".equals(valorGuiasPagamento) && valorGuiasPagamento != null){

			if(valorGuiasPagamento != null){
				valorGuiasForm = Util.formatarMoedaRealparaBigDecimal(valorGuiasPagamento);
			}
		}


		Collection<GuiaPagamentoValoresHelper> colecaoGuiasHelper = (Collection<GuiaPagamentoValoresHelper>) sessao
						.getAttribute("colecaoGuiaPagamentoValores");
		if(colecaoGuiasHelper != null && !colecaoGuiasHelper.isEmpty()){

			if(valorGuiasPagamento != null){

				Collection<GuiaPagamentoValoresHelper> colecaoGuiaHelperSelecionadas = fachada.retornarGuiaPagamentoValoresSelecionadas(
								chavesPrestacoes, colecaoGuiasHelper);

				BigDecimal atualizacaoSucumbencia = fachada.calcularValoresGuia(colecaoGuiaHelperSelecionadas,
								ConstantesSistema.PARCELAMENTO_VALOR_GUIA_ATUALIZACAO_MONETARIA_SUCUMBENCIA);
				BigDecimal jurosMoraSucumbencia = fachada.calcularValoresGuia(colecaoGuiaHelperSelecionadas,
								ConstantesSistema.PARCELAMENTO_VALOR_GUIA_JUROS_MORA_SUCUMBENCIA);

				BigDecimal valorTotalGuias = fachada.calcularValoresGuia(colecaoGuiaHelperSelecionadas,
								ConstantesSistema.PARCELAMENTO_VALOR_GUIA_TOTAL);
				BigDecimal valorTotalAcrescimoImpontualidadeGuias = fachada.calcularValoresGuia(colecaoGuiaHelperSelecionadas,
								ConstantesSistema.PARCELAMENTO_VALOR_GUIA_ACRESCIMO_IMPONTUALIDADE);
				if(indicadorAcrescimosImpotualidade.equals("1")){
					// valorDebitoTotalAtualizado =
					// valorDebitoTotalAtualizado.add(valorTotalAcrescimoImpontualidadeGuias);
					valorAcrescimosImpontualidade = valorAcrescimosImpontualidade.add(valorTotalAcrescimoImpontualidadeGuias);
				}

				valorAtualizacaoMonetaria = valorAtualizacaoMonetaria.add(
								fachada.calcularValoresGuia(colecaoGuiaHelperSelecionadas,
												ConstantesSistema.PARCELAMENTO_VALOR_GUIA_ATUALIZACAO_MONETARIA)).add(
								atualizacaoSucumbencia);
				valorJurosMora = valorJurosMora.add(
								fachada.calcularValoresGuia(colecaoGuiaHelperSelecionadas,
												ConstantesSistema.PARCELAMENTO_VALOR_GUIA_JUROS_MORA)).add(jurosMoraSucumbencia);
				valorMulta = valorMulta.add(fachada.calcularValoresGuia(colecaoGuiaHelperSelecionadas,
								ConstantesSistema.PARCELAMENTO_VALOR_GUIA_MULTA));

				valorAtualizacaoMonetariaSucumbencia = valorAtualizacaoMonetariaSucumbencia.add(atualizacaoSucumbencia);
				valorJurosMoraSucumbencia = valorJurosMoraSucumbencia.add(jurosMoraSucumbencia);
				valorSucumbencia = valorSucumbencia.add(fachada.calcularValoresGuia(colecaoGuiaHelperSelecionadas,
								ConstantesSistema.PARCELAMENTO_VALOR_GUIA_SUCUMBENCIA));

				valorDebitoTotalAtualizado = valorDebitoTotalAtualizado.subtract(valorGuiasForm);
				valorDebitoTotalAtualizado = valorDebitoTotalAtualizado.add(valorTotalGuias);
				efetuarParcelamentoDebitosActionForm.set("valorGuiasPagamento", Util.formatarMoedaReal(valorTotalGuias));
				sessao.setAttribute("colecaoGuiaPagamentoValoresSelecionadas", colecaoGuiaHelperSelecionadas);
			}
		}

		BigDecimal valorTotalGuias = BigDecimal.ZERO;
		if(!"".equals(efetuarParcelamentoDebitosActionForm.get("valorGuiasPagamento"))
						&& efetuarParcelamentoDebitosActionForm.get("valorGuiasPagamento") != null){
			valorTotalGuias = Util
							.formatarMoedaRealparaBigDecimal((String) efetuarParcelamentoDebitosActionForm.get("valorGuiasPagamento"));
		}

		BigDecimal valorDebitoACobrarServico = BigDecimal.ZERO;
		if(!"".equals(efetuarParcelamentoDebitosActionForm.get("valorDebitoACobrarServico"))
						&& efetuarParcelamentoDebitosActionForm.get("valorDebitoACobrarServico") != null){
			valorDebitoACobrarServico = Util.formatarMoedaRealparaBigDecimal((String) efetuarParcelamentoDebitosActionForm
							.get("valorDebitoACobrarServico"));
		}
		BigDecimal valorDebitoACobrarParcelamento = BigDecimal.ZERO;
		if(!"".equals(efetuarParcelamentoDebitosActionForm.get("valorDebitoACobrarParcelamento"))
						&& efetuarParcelamentoDebitosActionForm.get("valorDebitoACobrarParcelamento") != null){
			valorDebitoACobrarParcelamento = Util.formatarMoedaRealparaBigDecimal((String) efetuarParcelamentoDebitosActionForm
							.get("valorDebitoACobrarParcelamento"));
		}
		BigDecimal valorCreditoARealizar = BigDecimal.ZERO;
		if(!"".equals(efetuarParcelamentoDebitosActionForm.get("valorCreditoARealizar"))
						&& efetuarParcelamentoDebitosActionForm.get("valorCreditoARealizar") != null){
			valorCreditoARealizar = Util.formatarMoedaRealparaBigDecimal((String) efetuarParcelamentoDebitosActionForm
							.get("valorCreditoARealizar"));
		}

		BigDecimal debitoTotalAtualizado = BigDecimal.ZERO;
		debitoTotalAtualizado.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
		debitoTotalAtualizado = debitoTotalAtualizado.add(valorTotalContaValores);
		debitoTotalAtualizado = debitoTotalAtualizado.add(valorTotalGuias);
		debitoTotalAtualizado = debitoTotalAtualizado.add(valorAcrescimosImpontualidade);
		debitoTotalAtualizado = debitoTotalAtualizado.add(valorDebitoACobrarServico);
		debitoTotalAtualizado = debitoTotalAtualizado.add(valorDebitoACobrarParcelamento);
		debitoTotalAtualizado = debitoTotalAtualizado.subtract(valorCreditoARealizar);

		if(debitoTotalAtualizado.compareTo(BigDecimal.ZERO) == -1 || debitoTotalAtualizado.equals(BigDecimal.ZERO)){
			throw new ActionServletException("atencao.nao.existe.debito.a.parcelar");
		}
		efetuarParcelamentoDebitosActionForm.set("valorAcrescimosImpontualidade", Util.formatarMoedaReal(valorAcrescimosImpontualidade));
		efetuarParcelamentoDebitosActionForm.set("valorDebitoTotalAtualizado", Util.formatarMoedaReal(debitoTotalAtualizado));
		efetuarParcelamentoDebitosActionForm.set("valorJurosMora", Util.formatarMoedaReal(valorJurosMora));
		efetuarParcelamentoDebitosActionForm.set("valorMulta", Util.formatarMoedaReal(valorMulta));
		efetuarParcelamentoDebitosActionForm.set("valorAtualizacaoMonetaria", Util.formatarMoedaReal(valorAtualizacaoMonetaria));
		sessao.setAttribute("valorEntradaParcelamento", valorEntradaParcelamento);
	}
}
