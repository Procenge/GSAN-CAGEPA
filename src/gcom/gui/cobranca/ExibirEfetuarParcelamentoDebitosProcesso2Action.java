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
 * GSANPCG
 * Andr� Nishimura
 * Eduardo Henrique Bandeira Carneiro da Silva
 * Saulo Vasconcelos de Lima
 * Virginia Santos de Melo
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

package gcom.gui.cobranca;

import gcom.cobranca.bean.ContaHelper;
import gcom.cobranca.bean.ContaValoresHelper;
import gcom.cobranca.bean.GuiaPagamentoValoresHelper;
import gcom.cobranca.bean.ObterDebitoImovelOuClienteHelper;
import gcom.cobranca.parcelamento.Parcelamento;
import gcom.cobranca.parcelamento.ParcelamentoPerfil;
import gcom.fachada.Fachada;
import gcom.faturamento.conta.Conta;
import gcom.faturamento.conta.ContaMotivoRevisao;
import gcom.faturamento.credito.CreditoARealizar;
import gcom.faturamento.debito.DebitoACobrar;
import gcom.faturamento.debito.DebitoTipo;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.PermissaoEspecial;
import gcom.seguranca.acesso.usuario.Usuario;
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
 * Permite efetuar o parcelamento dos d�bitos de um im�vel
 * UC0214] - Efetuar Parcelamento de D�bitos
 * Pr�-processamento da segunda p�gina
 * 
 * @author Roberta Costa
 * @date 11/02/2006
 */
public class ExibirEfetuarParcelamentoDebitosProcesso2Action
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("processo2");

		Fachada fachada = Fachada.getInstancia();

		if("sim".equalsIgnoreCase((String) httpServletRequest.getAttribute("popupEfetuarParcelamento"))
						|| "sim".equalsIgnoreCase(httpServletRequest.getParameter("popupEfetuarParcelamento"))){
			httpServletRequest.setAttribute("popupEfetuarParcelamento", "sim");
		}

		HttpSession sessao = httpServletRequest.getSession(false);

		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");

		DynaActionForm efetuarParcelamentoDebitosActionForm = (DynaActionForm) actionForm;

		// Pega dados do formul�rio
		String codigoImovel = (String) (efetuarParcelamentoDebitosActionForm.get("matriculaImovel"));
		String dataParcelamento = (String) (efetuarParcelamentoDebitosActionForm.get("dataParcelamento"));
		String resolucaoDiretoria = (String) (efetuarParcelamentoDebitosActionForm.get("resolucaoDiretoria"));
		String fimIntervaloParcelamento = (String) efetuarParcelamentoDebitosActionForm.get("fimIntervaloParcelamento");
		String inicioIntervaloParcelamento = (String) efetuarParcelamentoDebitosActionForm.get("inicioIntervaloParcelamento");
		String inicioIntervaloParcelamentoOriginal = (String) efetuarParcelamentoDebitosActionForm
						.get("inicioIntervaloParcelamentoOriginal");
		String fimIntervaloParcelamentoOriginal = (String) efetuarParcelamentoDebitosActionForm.get("fimIntervaloParcelamentoOriginal");
		String indicadorContasRevisao = (String) efetuarParcelamentoDebitosActionForm.get("indicadorContasRevisao");
		String indicadorGuiasPagamento = (String) efetuarParcelamentoDebitosActionForm.get("indicadorGuiasPagamento");
		String indicadorAcrescimosImpotualidade = (String) efetuarParcelamentoDebitosActionForm.get("indicadorAcrescimosImpotualidade");
		String indicadorDebitosACobrar = (String) efetuarParcelamentoDebitosActionForm.get("indicadorDebitosACobrar");
		String indicadorCreditoARealizar = (String) efetuarParcelamentoDebitosActionForm.get("indicadorCreditoARealizar");
		String indicadorParcelamentoCobrancaBancaria = (String) efetuarParcelamentoDebitosActionForm
						.get("indicadorParcelamentoCobrancaBancaria");
		String[] idsMotivoRevisao = (String[]) efetuarParcelamentoDebitosActionForm.get("idMotivoRevisao");
		Integer[] idsMotivoRevisaoInt = null;
		if(!Util.isVazioOrNulo(idsMotivoRevisao)){
			idsMotivoRevisaoInt = new Integer[idsMotivoRevisao.length];
			for(int i = 0; i < idsMotivoRevisao.length; i++){
				idsMotivoRevisaoInt[i] = Util.obterInteger(idsMotivoRevisao[i]);
			}
		}

		/*
		 * String contendo o 'Id da guia' e o 'n�mero da presta��o' separados por '-' de todos os
		 * itens selecionados em tela e concatenados por '$'
		 * (Ex.: 9988-1$9988-2$7766-1)
		 */

		Boolean indicadorContas = true;

		// se o intervalo de parcelamento estiver igual a null
		// n�o se deve levar em considera��o no parcelamento a cole�o de contas
		if((inicioIntervaloParcelamento == null || inicioIntervaloParcelamento.equals(""))
						&& (fimIntervaloParcelamento == null || fimIntervaloParcelamento.equals(""))){
			indicadorContas = false;
		}

		// Atualiza os valores da primeira aba na se��o caso seja confirmado a altera��o
		// de alguma informa��o do primeiro formul�rio
		if(httpServletRequest.getParameter("confirmado") != null && httpServletRequest.getParameter("confirmado").equals("ok")){

			sessao.setAttribute("codigoImovelEscolhida", codigoImovel);
			sessao.setAttribute("dataParcelamentoEscolhida", dataParcelamento);
			sessao.setAttribute("resolucaoDiretoriaEscolhida", resolucaoDiretoria);
			sessao.setAttribute("fimIntervaloParcelamentoEscolhida", fimIntervaloParcelamento);
			sessao.setAttribute("inicioIntervaloParcelamentoEscolhida", inicioIntervaloParcelamento);
			sessao.setAttribute("indicadorContasRevisaoEscolhida", indicadorContasRevisao);
			sessao.setAttribute("indicadorGuiasPagamentoEscolhida", indicadorGuiasPagamento);
			sessao.setAttribute("indicadorAcrescimosImpotualidadeEscolhida", indicadorAcrescimosImpotualidade);
			sessao.setAttribute("indicadorDebitosACobrarEscolhida", indicadorDebitosACobrar);
			sessao.setAttribute("indicadorCreditoARealizarEscolhida", indicadorCreditoARealizar);
			sessao.setAttribute("idsMotivoRevisaoEscolhido", idsMotivoRevisaoInt);

			// Limpa a sess�o
			sessao.removeAttribute("calcula");
			sessao.removeAttribute("colecaoContaValores");
			sessao.removeAttribute("colecaoGuiaPagamentoValores");
			sessao.removeAttribute("opcoesParcelamento");
			sessao.removeAttribute("colecaoOpcoesParcelamento");
			sessao.removeAttribute("valorTotalContaValores");
			sessao.removeAttribute("valorAcrescimosImpontualidade");
			sessao.removeAttribute("idsContaEP");
			sessao.removeAttribute("colecaoCreditoARealizar");
		}

		// Verifica de onde est� vindo a chamada para fazer o c�lculo das contas EP e NB
		// Se � do bot�o do Calcular ou do Avan�ar ou Aba
		String calcula = null;
		String verificaCalcula = null;
		if(httpServletRequest.getParameter("calcula") != null && !httpServletRequest.getParameter("calcula").equals("")){
			calcula = httpServletRequest.getParameter("calcula");
			verificaCalcula = "request";
		}else if(sessao.getAttribute("calcula") != null && !sessao.getAttribute("calcula").equals("")){
			if(httpServletRequest.getParameter("limpaCombo") == null || httpServletRequest.getParameter("limpaCombo").equals("")){
				calcula = (String) sessao.getAttribute("calcula");
				verificaCalcula = "session";
			}
		}

		if(calcula == null){
			if(codigoImovel != null && !codigoImovel.trim().equals("")){
				String confirmado = httpServletRequest.getParameter("confirmado");

				if(confirmado != null && confirmado.trim().equalsIgnoreCase("ok")){
					// Limpa o formul�rio
					efetuarParcelamentoDebitosActionForm.reset(actionMapping, httpServletRequest);

					// Limpa os bot�es de r�dio da EP e NB da lista de contas
					if(sessao.getAttribute("colecaoContaValores") != null && !sessao.getAttribute("colecaoContaValores").equals("")){
						Collection<ContaValoresHelper> colecaoContas = (Collection<ContaValoresHelper>) sessao
										.getAttribute("colecaoContaValores");
						Iterator<ContaValoresHelper> colecaoContasIterator = colecaoContas.iterator();
						colecaoContasIterator = colecaoContas.iterator();
						while(colecaoContasIterator.hasNext()){
							ContaValoresHelper contaValoresHelper = colecaoContasIterator.next();
							contaValoresHelper.setIndicadorContasDebito(null);
						}
					}

					// Limpa a op��o de parcelamento
					if(sessao.getAttribute("colecaoContaValoresNegociacao") != null
									&& !sessao.getAttribute("colecaoContaValoresNegociacao").equals("")){
						Collection<ContaValoresHelper> colecaoContaValoresNegociacao = (Collection) sessao
										.getAttribute("colecaoContaValoresNegociacao");
						Iterator<ContaValoresHelper> contaValores = colecaoContaValoresNegociacao.iterator();
						while(contaValores.hasNext()){
							ContaValoresHelper contaValoresHelper = contaValores.next();
							if(contaValoresHelper.getIndicadorContasDebito() != null
											&& contaValoresHelper.getIndicadorContasDebito().equals(1)){
								contaValoresHelper.setIndicadorContasDebito(null);
							}
						}
					}

					// Limpando a sess�o
					sessao.removeAttribute("colecaoGuiaPagamentoValores");
					sessao.removeAttribute("colecaoDebitoACobrar");
					sessao.removeAttribute("colecaoCreditoARealizar");
					sessao.removeAttribute("calcula");
					sessao.removeAttribute("colecaoContaValores");
					sessao.removeAttribute("colecaoOpcoesParcelamento");
				}

				// Caso o periodo inicial do intervalo do parcelamento n�o seja informado
				if(inicioIntervaloParcelamento == null || inicioIntervaloParcelamento.equals("")){
					inicioIntervaloParcelamento = "01/0001";
				}

				// Caso o periodo final do intervalo do parcelamento n�o seja informado
				if(fimIntervaloParcelamento == null || fimIntervaloParcelamento.equals("")){
					fimIntervaloParcelamento = "12/9999";
				}

				if((!inicioIntervaloParcelamento.equals(inicioIntervaloParcelamentoOriginal))
								|| (!fimIntervaloParcelamento.equals(fimIntervaloParcelamentoOriginal))){

					Short indicadorNaoConsiderarPagamentoNaoClassificado = 2;

					// Obter todo o d�bito do im�vel para exibi��o
					ObterDebitoImovelOuClienteHelper colecaoDebitoCliente = fachada
									.obterDebitoImovelOuCliente(1, // Indicador
													// de d�bito do im�vel
													codigoImovel, // Matr�cula do im�vel
													null, // C�digo do cliente
													null, // Tipo de rela��o cliente im�vel
													Util
																	.formatarMesAnoParaAnoMesSemBarra((inicioIntervaloParcelamentoOriginal
																					.length() != 0 && !inicioIntervaloParcelamentoOriginal
																					.equals("")) ? inicioIntervaloParcelamentoOriginal
																					: inicioIntervaloParcelamento), // Refer�ncia
													// inicial do d�bito
													Util
																	.formatarMesAnoParaAnoMesSemBarra((fimIntervaloParcelamentoOriginal
																					.length() != 0 && !fimIntervaloParcelamentoOriginal
																					.equals("")) ? fimIntervaloParcelamentoOriginal
																					: fimIntervaloParcelamento), // Fim
													// do d�bito
													Util.converteStringParaDate("01/01/0001"), // Inicio
													// vencimento
													Util.converteStringParaDate("31/12/9999"), // Fim
													// vencimento
													1, // Indicador de pagamento
													Integer.valueOf(indicadorContasRevisao), // conta
													// em
													// revis�o
													Integer.valueOf(indicadorDebitosACobrar), // D�bito
													// a
													// cobrar
													Integer.valueOf(indicadorCreditoARealizar), // cr�dito
													// a
													// realizar
													1, // Indicador de notas promiss�rias
													Integer.valueOf(indicadorGuiasPagamento), // guias
													// pagamento
													Integer.valueOf(indicadorAcrescimosImpotualidade), // acr�scimos
													// impontualidade
													true, null, null, null, indicadorNaoConsiderarPagamentoNaoClassificado,
													ConstantesSistema.SIM, ConstantesSistema.SIM, ConstantesSistema.SIM);
					if(colecaoDebitoCliente.getIdBoletoBancario() != null){
						// sessao.setAttribute("idBoletoBancario",
						// colecaoDebitoCliente.getIdBoletoBancario());
					}
					Iterator it = colecaoDebitoCliente.getColecaoContasValores().iterator();
					while(it.hasNext()){
						ContaValoresHelper contaValoresHelper = (ContaValoresHelper) it.next();
						if(((ContaHelper) contaValoresHelper.getConta()).getIdContaMotivoRevisao() != null
										&& ParametroCobranca.isCobrancaBancaria(((ContaHelper) contaValoresHelper.getConta())
														.getIdContaMotivoRevisao().toString())){
							if((((ContaHelper) contaValoresHelper.getConta()).getReferencia() < Util.obterInteger(Util
											.formatarMesAnoParaAnoMesSemBarra(inicioIntervaloParcelamento)))
											|| (((ContaHelper) contaValoresHelper.getConta()).getReferencia() > Util.obterInteger(Util
															.formatarMesAnoParaAnoMesSemBarra(fimIntervaloParcelamento)))){
								return montarPaginaConfirmacao("atencao.periodo_informado_nao_contempla_todas_contas_cobranca_bancaria",
												httpServletRequest, actionMapping, "");

							}
						}
					}
				}

				// Obter todo o d�bito do im�vel para exibi��o
				ObterDebitoImovelOuClienteHelper colecaoDebitoCliente = fachada.obterDebitoImovelOuCliente(1, // Indicador
								// de
								// d�bito
								// do
								// im�vel
								codigoImovel, // Matr�cula do im�vel
								null, // C�digo do cliente
								null, // Tipo de rela��o cliente im�vel
								Util.formatarMesAnoParaAnoMesSemBarra(inicioIntervaloParcelamento), // Refer�ncia
								// inicial
								// do
								// d�bito
								Util.formatarMesAnoParaAnoMesSemBarra(fimIntervaloParcelamento), // Fim
								// do
								// d�bito
								Util.converteStringParaDate("01/01/0001"), // Inicio vencimento
								Util.converteStringParaDate("31/12/9999"), // Fim vencimento
								1, // Indicador de pagamento
								Integer.valueOf(indicadorContasRevisao), // conta em revis�o
								Integer.valueOf(indicadorDebitosACobrar), // D�bito a cobrar
								Integer.valueOf(indicadorCreditoARealizar), // cr�dito a realizar
								1, // Indicador de notas promiss�rias
								Integer.valueOf(indicadorGuiasPagamento), // guias pagamento
								Integer.valueOf(indicadorAcrescimosImpotualidade), // acr�scimos
								// impontualidade
								indicadorContas, null, null, null, null, ConstantesSistema.SIM, ConstantesSistema.SIM,
								ConstantesSistema.SIM);

				// [FS0049] - Verificar retirada de d�bitos prescritos do d�bito do parcelamento
				Short pIndicadorConsiderarDebitoPrescrito = ConstantesSistema.NUMERO_NAO_INFORMADO;

				try{

					pIndicadorConsiderarDebitoPrescrito = Util
									.converterStringParaShort((String) ParametroCobranca.P_INDICADOR_CONSIDERAR_DEBITO_PRESCRITO_NO_PARCELAMENTO
													.executar());

				}catch(ControladorException e){

					throw new ActionServletException("atencao.sistemaparametro_inexistente", null,
									"P_INDICADOR_CONSIDERAR_DEBITO_PRESCRITO_NO_PARCELAMENTO");
				}

				fachada.verificarRetirarContaOuGuiaPrescrita(Util.obterInteger(codigoImovel), pIndicadorConsiderarDebitoPrescrito,
								colecaoDebitoCliente);

				if(!Util.isVazioOrNulo(colecaoDebitoCliente.getColecaoContasValores())){

					Iterator<ContaValoresHelper> it = colecaoDebitoCliente.getColecaoContasValores().iterator();

					while(it.hasNext()){
						ContaHelper conta = (ContaHelper) it.next().getConta();
						if(conta.getIdContaMotivoRevisao() != null){
							if(ParametroCobranca.isCobrancaBancaria(conta.getIdContaMotivoRevisao().toString())){
								if(!getFachada().verificarPermissaoEspecial(PermissaoEspecial.PARCELAMENTO_COBRANCA_BANCARIA,
												getUsuarioLogado(httpServletRequest))){
									throw new ActionServletException("atencao.usuario_sem_permisao_parcelar_cobranca_bancaria");
								}
							}
						}
					}

				}

				// Para o c�lculo do D�bito Total Atualizado
				BigDecimal valorTotalContas = BigDecimal.ZERO;
				BigDecimal valorTotalAcrescimoImpontualidade = BigDecimal.ZERO;
				BigDecimal valorTotalRestanteServicosACobrar = BigDecimal.ZERO;
				BigDecimal valorTotalRestanteServicosACobrarCurtoPrazo = BigDecimal.ZERO;
				BigDecimal valorTotalRestanteServicosACobrarLongoPrazo = BigDecimal.ZERO;
				BigDecimal valorTotalRestanteParcelamentosACobrar = BigDecimal.ZERO;
				BigDecimal valorTotalRestanteParcelamentosACobrarCurtoPrazo = BigDecimal.ZERO;
				BigDecimal valorTotalRestanteParcelamentosACobrarLongoPrazo = BigDecimal.ZERO;
				BigDecimal valorTotalGuiasPagamento = BigDecimal.ZERO;
				BigDecimal valorTotalAcrescimoImpontualidadeContas = BigDecimal.ZERO;
				BigDecimal valorTotalAcrescimoImpontualidadeGuias = BigDecimal.ZERO;
				BigDecimal valorCreditoARealizar = BigDecimal.ZERO;
				BigDecimal valorRestanteACobrar = BigDecimal.ZERO;
				BigDecimal valorAtualizacaoMonetaria = BigDecimal.ZERO;
				BigDecimal valorJurosMora = BigDecimal.ZERO;
				BigDecimal valorMulta = BigDecimal.ZERO;

				// Dados do D�bito do Im�vel - Contas
				Collection<ContaValoresHelper> colecaoContaValores = colecaoDebitoCliente.getColecaoContasValores();

				Collection<GuiaPagamentoValoresHelper> colecaoGuiasPagamentoValores = colecaoDebitoCliente
								.getColecaoGuiasPagamentoValores();

				ParcelamentoPerfil parcelamentoPerfil = (ParcelamentoPerfil) sessao.getAttribute("parcelamentoPerfil");

				// [SB0011] Verificar �nica Fatura
				fachada.verificarUnicaFatura(colecaoContaValores, parcelamentoPerfil);

				// [SB0026] � Verificar D�bito em Cobran�a Administrativa
				if(fachada.existeEmpresaCobrancaContrato(usuario.getEmpresa().getId())){
					fachada.removerContaCobrancaAdministrativaDebitoImovel(usuario, Integer.valueOf(codigoImovel), colecaoContaValores);
					fachada.removerGuiaPagamentoCobrancaAdministrativaDebitoImovel(usuario, Integer.valueOf(codigoImovel),
									colecaoGuiasPagamentoValores);
				}

				// [FS0044] Retirar D�bitos N�o Vencidos
				colecaoContaValores = fachada.retirarContasNaoVencidas(colecaoContaValores);

				// Retirar as contas com Motivo de Revis�o com Imedimento, se for o caso
				colecaoContaValores = fachada.retirarContasMotivoRevisaoComImpedimentoParcelamento(colecaoContaValores, usuario);

				// [FS0040] - Verificar exist�ncia de itens de parcelamento anterior na mesma
				// refer�ncia
				Collection<DebitoACobrar> colecaoDebitoACobrarAux = colecaoDebitoCliente.getColecaoDebitoACobrar();

				Collection<CreditoARealizar> colecaoCreditoARealizarAux = colecaoDebitoCliente.getColecaoCreditoARealizar();

				fachada.verificarExistenciaDeItensDeParcelamentoAnteriorNaMesmaReferencia(Integer.valueOf(codigoImovel),
								colecaoContaValores, colecaoGuiasPagamentoValores, colecaoDebitoACobrarAux, colecaoCreditoARealizarAux);

				Integer idBoletoNegociacao = null;

				if(sessao.getAttribute("idBoletoBancario") != null && !sessao.getAttribute("idBoletoBancario").equals("")){
					idBoletoNegociacao = (Integer) sessao.getAttribute("idBoletoBancario");
				}

				// 3.1.2.2. Caso n�o seja poss�vel o parcelamento de cobran�a banc�ria
				// (PASI_DSPARAMETRO com o valor 2 (dois) na tabela PARAMETRO_SISTEMA para
				// PASI_DSPARAMETRO=�P_PARCELAMENTO_COBRANCA_BANCARIA�) OU caso seja poss�vel o
				// parcelamento de cobran�a banc�ria (PASI_DSPARAMETRO com o valor 1 (sim) na tabela
				// PARAMETRO_SISTEMA para PASI_DSPARAMETRO=�P_PARCELAMENTO_COBRANCA_BANCARIA�) e o
				// campo �Parcelamento de Cobran�a Banc�ria?� esteja com a op��o �N�O� selecionada,
				// ap�s a chamada da rotina de obter d�bito, as contas que estejam em revis�o por
				// cobran�a banc�ria (contas com CMRV_ID com o valor correspondente � �COBRAN�A
				// BANC�RIA�) dever�o ser desprezadas.
				// *********************************************************************************
				// 3.1.2.3. Caso o indicador de conta em revis�o seja �SIM� e tenha sido selecionado
				// algum motivo de revis�o, ap�s a chamada da rotina de obter d�bito, as contas com
				// motivo de revis�o diferente do(s) motivo(s) selecionado(s) dever�o ser
				// desprezadas (contas com CMRV_ID diferente do(s) motivo(s) selecionado(s)).
				// Filtra logo pelos motivos de revis�o escolhidos no filtro e depois, se for o
				// caso, trata o passo 3.1.2.1. do Caso de Uso (logo abaixo).
				if(idsMotivoRevisaoInt != null){
					if(colecaoContaValores != null && !colecaoContaValores.isEmpty()){

						Iterator<ContaValoresHelper> contaValores = colecaoContaValores.iterator();
						while(contaValores.hasNext()){
							ContaValoresHelper contaValoresHelper = contaValores.next();
							Conta conta = contaValoresHelper.getConta();

							if(conta != null && conta.getContaMotivoRevisao() != null && conta.getContaMotivoRevisao().getId() != null){
								if(!this.isMotivoRevisaoFiltrado(conta.getContaMotivoRevisao().getId(), idsMotivoRevisaoInt)){
									contaValores.remove();
								}
							}
						}
					}
				}

				if(colecaoContaValores != null && !colecaoContaValores.isEmpty()){

					Integer indicadorParcelamentoCobrancaBancariaParametro = null;

					try{
						indicadorParcelamentoCobrancaBancariaParametro = Util
										.obterInteger((String) ParametroCobranca.P_PARCELAMENTO_COBRANCA_BANCARIA
														.executar(ExecutorParametrosCobranca.getInstancia()));
					}catch(ControladorException e){
						e.printStackTrace();
					}

					Iterator<ContaValoresHelper> contaValores = colecaoContaValores.iterator();
					while(contaValores.hasNext()){
						ContaValoresHelper contaValoresHelper = contaValores.next();

						// 3.1.2.1. Caso seja poss�vel o parcelamento de cobran�a banc�ria
						// (PASI_DSPARAMETRO com o valor 1 (sim) na tabela PARAMETRO_SISTEMA para
						// PASI_DSPARAMETRO=�P_PARCELAMENTO_COBRANCA_BANCARIA�) e o campo
						// �Parcelamento de Cobran�a Banc�ria?� esteja com a op��o �SIM�
						// selecionada, ap�s a chamada da rotina de obter d�bito, as contas que n�o
						// estejam em revis�o (contas com CMRV_ID com o valor nulo) e as contas que
						// n�o estejam associadas ao boleto selecionado para negocia��o (CNTA_ID
						// diferente de da tabela CONTA com CNTA_ID=CNTA_ID da tabela
						// COBRANCA_DOCUMENTO_ITEM com CBDO_ID=CBDO_ID da tabela BOLETO_BANCARIO com
						// BBCO_ID=Id do boleto banc�rio selecionado para negocia��o) dever�o ser
						// desprezadas.
						if(indicadorParcelamentoCobrancaBancaria != null && indicadorParcelamentoCobrancaBancaria.equals("1")){

							Conta conta = contaValoresHelper.getConta();

							// S� considera e calcula as contas que estejam em revis�o
							// if((conta != null && conta.getContaMotivoRevisao() != null &&
							// conta.getContaMotivoRevisao().getId() != null)
							// || (idBoletoNegociacao != null &&
							// fachada.isContaAssociadaAoBoletoBancario(idBoletoNegociacao,
							// conta.getId()))){
							if((conta != null && (conta.getContaMotivoRevisao() == null || conta.getContaMotivoRevisao().getId() == null))
											|| (idBoletoNegociacao != null && !fachada.isContaAssociadaAoBoletoBancario(idBoletoNegociacao,
															conta.getId()))){

								contaValores.remove();

							}else{

								valorTotalContas.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
								valorTotalContas = valorTotalContas.add(contaValoresHelper.getValorTotalConta().setScale(
												Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO));

								if(contaValoresHelper.getValorAtualizacaoMonetaria() != null
												&& !contaValoresHelper.getValorAtualizacaoMonetaria().equals("")){
									valorAtualizacaoMonetaria.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
									valorAtualizacaoMonetaria = valorAtualizacaoMonetaria.add(contaValoresHelper
													.getValorAtualizacaoMonetaria().setScale(Parcelamento.CASAS_DECIMAIS,
																	Parcelamento.TIPO_ARREDONDAMENTO));
								}
								if(contaValoresHelper.getValorJurosMora() != null && !contaValoresHelper.getValorJurosMora().equals("")){
									valorJurosMora.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
									valorJurosMora = valorJurosMora.add(contaValoresHelper.getValorJurosMora().setScale(
													Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO));
								}
								if(contaValoresHelper.getValorMulta() != null && !contaValoresHelper.getValorMulta().equals("")){
									valorMulta.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
									valorMulta = valorMulta.add(contaValoresHelper.getValorMulta().setScale(Parcelamento.CASAS_DECIMAIS,
													Parcelamento.TIPO_ARREDONDAMENTO));
								}

								// Para c�lculo do Acrescimo de Impontualidade
								valorTotalAcrescimoImpontualidadeContas.setScale(Parcelamento.CASAS_DECIMAIS,
												Parcelamento.TIPO_ARREDONDAMENTO);
								valorTotalAcrescimoImpontualidadeContas = valorTotalAcrescimoImpontualidadeContas.add(contaValoresHelper
												.getValorTotalContaValoresParcelamento().setScale(Parcelamento.CASAS_DECIMAIS,
																Parcelamento.TIPO_ARREDONDAMENTO));
							}
						}else if((indicadorParcelamentoCobrancaBancariaParametro != null && indicadorParcelamentoCobrancaBancariaParametro
										.equals(2))
										|| (indicadorParcelamentoCobrancaBancariaParametro != null
														&& indicadorParcelamentoCobrancaBancariaParametro.equals(1)
														&& indicadorParcelamentoCobrancaBancaria != null && indicadorParcelamentoCobrancaBancaria
														.equals("2"))){

							Conta conta = contaValoresHelper.getConta();

							if(conta != null
											&& conta.getContaMotivoRevisao() != null
											&& conta.getContaMotivoRevisao().getId() != null
											&& conta.getContaMotivoRevisao().getId().equals(
															ContaMotivoRevisao.REVISAO_POR_COBRANCA_BANCARIA)){
								contaValores.remove();

							}else{
								valorTotalContas.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
								valorTotalContas = valorTotalContas.add(contaValoresHelper.getValorTotalConta().setScale(
												Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO));

								if(contaValoresHelper.getValorAtualizacaoMonetaria() != null
												&& !contaValoresHelper.getValorAtualizacaoMonetaria().equals("")){
									valorAtualizacaoMonetaria.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
									valorAtualizacaoMonetaria = valorAtualizacaoMonetaria.add(contaValoresHelper
													.getValorAtualizacaoMonetaria().setScale(Parcelamento.CASAS_DECIMAIS,
																	Parcelamento.TIPO_ARREDONDAMENTO));
								}
								if(contaValoresHelper.getValorJurosMora() != null && !contaValoresHelper.getValorJurosMora().equals("")){
									valorJurosMora.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
									valorJurosMora = valorJurosMora.add(contaValoresHelper.getValorJurosMora().setScale(
													Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO));
								}
								if(contaValoresHelper.getValorMulta() != null && !contaValoresHelper.getValorMulta().equals("")){
									valorMulta.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
									valorMulta = valorMulta.add(contaValoresHelper.getValorMulta().setScale(Parcelamento.CASAS_DECIMAIS,
													Parcelamento.TIPO_ARREDONDAMENTO));
								}

								// Para c�lculo do Acrescimo de Impontualidade
								valorTotalAcrescimoImpontualidadeContas.setScale(Parcelamento.CASAS_DECIMAIS,
												Parcelamento.TIPO_ARREDONDAMENTO);
								valorTotalAcrescimoImpontualidadeContas = valorTotalAcrescimoImpontualidadeContas.add(contaValoresHelper
												.getValorTotalContaValoresParcelamento().setScale(Parcelamento.CASAS_DECIMAIS,
																Parcelamento.TIPO_ARREDONDAMENTO));
							}

						}else{
							valorTotalContas.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
							valorTotalContas = valorTotalContas.add(contaValoresHelper.getValorTotalConta().setScale(
											Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO));

							if(contaValoresHelper.getValorAtualizacaoMonetaria() != null
											&& !contaValoresHelper.getValorAtualizacaoMonetaria().equals("")){
								valorAtualizacaoMonetaria.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
								valorAtualizacaoMonetaria = valorAtualizacaoMonetaria.add(contaValoresHelper.getValorAtualizacaoMonetaria()
												.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO));
							}
							if(contaValoresHelper.getValorJurosMora() != null && !contaValoresHelper.getValorJurosMora().equals("")){
								valorJurosMora.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
								valorJurosMora = valorJurosMora.add(contaValoresHelper.getValorJurosMora().setScale(
												Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO));
							}
							if(contaValoresHelper.getValorMulta() != null && !contaValoresHelper.getValorMulta().equals("")){
								valorMulta.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
								valorMulta = valorMulta.add(contaValoresHelper.getValorMulta().setScale(Parcelamento.CASAS_DECIMAIS,
												Parcelamento.TIPO_ARREDONDAMENTO));
							}

							// Para c�lculo do Acrescimo de Impontualidade
							valorTotalAcrescimoImpontualidadeContas.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
							valorTotalAcrescimoImpontualidadeContas = valorTotalAcrescimoImpontualidadeContas.add(contaValoresHelper
											.getValorTotalContaValoresParcelamento().setScale(Parcelamento.CASAS_DECIMAIS,
															Parcelamento.TIPO_ARREDONDAMENTO));
						}
					}

					efetuarParcelamentoDebitosActionForm.set("valorTotalContaValores", Util.formatarMoedaReal(valorTotalContas));
					sessao.setAttribute("valorTotalContaValores", valorTotalContas);
					sessao.setAttribute("colecaoContaValores", colecaoContaValores);
				}else{

					efetuarParcelamentoDebitosActionForm.set("valorTotalContaValores", "0,00");
					sessao.setAttribute("valorTotalContaValores", valorTotalContas);
					colecaoContaValores.clear();
					sessao.setAttribute("colecaoContaValores", colecaoContaValores);

					sessao.removeAttribute("colecaoContaValoresImovel");
				}

				String chavesPrestacoes = (String) efetuarParcelamentoDebitosActionForm.get("chavesPrestacoes");

				// Guias de Pagamento
				Collection<GuiaPagamentoValoresHelper> colecaoGuiaPagamentoValores = null;
				if(indicadorGuiasPagamento.equals("1")){

					colecaoGuiaPagamentoValores = colecaoDebitoCliente.getColecaoGuiasPagamentoValores();

					// [FS0037] - Verificar exist�ncia de guia de parcelamento de cobran�a banc�ria
					// [FS0041] - Verificar exist�ncia de guias correspondentes a presta��es de
					// parcelamento com concess�o de desconto nos acr�scimos
					if(!Util.isVazioOrNulo(colecaoGuiaPagamentoValores)){
						fachada.removerGuiaDePagamentoNaComposicaoDoDebitoParcelamennto(colecaoGuiaPagamentoValores);
					}

					if(colecaoGuiaPagamentoValores != null && !colecaoGuiaPagamentoValores.isEmpty()){

						Collection<GuiaPagamentoValoresHelper> colecaoGuiaPagamentoValoresSelecionadas = fachada
										.retornarGuiaPagamentoValoresSelecionadas(chavesPrestacoes, colecaoGuiaPagamentoValores);

						valorTotalGuiasPagamento = valorTotalGuiasPagamento.add(fachada.calcularValoresGuia(
										colecaoGuiaPagamentoValoresSelecionadas, ConstantesSistema.PARCELAMENTO_VALOR_GUIA_TOTAL));
						valorAtualizacaoMonetaria = valorAtualizacaoMonetaria.add(fachada.calcularValoresGuia(
										colecaoGuiaPagamentoValoresSelecionadas,
										ConstantesSistema.PARCELAMENTO_VALOR_GUIA_ATUALIZACAO_MONETARIA));
						valorJurosMora = valorJurosMora.add(fachada.calcularValoresGuia(colecaoGuiaPagamentoValoresSelecionadas,
										ConstantesSistema.PARCELAMENTO_VALOR_GUIA_JUROS_MORA));
						valorMulta = valorMulta.add(fachada.calcularValoresGuia(colecaoGuiaPagamentoValoresSelecionadas,
										ConstantesSistema.PARCELAMENTO_VALOR_GUIA_MULTA));
						valorTotalAcrescimoImpontualidadeGuias = valorTotalAcrescimoImpontualidadeGuias.add(fachada.calcularValoresGuia(
										colecaoGuiaPagamentoValoresSelecionadas,
										ConstantesSistema.PARCELAMENTO_VALOR_GUIA_ACRESCIMO_IMPONTUALIDADE));

						efetuarParcelamentoDebitosActionForm.set("valorGuiasPagamento", Util.formatarMoedaReal(valorTotalGuiasPagamento));

						// Pega as Guias de Pagamento em D�bito
						sessao.setAttribute("colecaoGuiaPagamentoValores", colecaoGuiaPagamentoValores);
						sessao.removeAttribute("colecaoGuiaPagamentoValoresImovel");
					}else{
						efetuarParcelamentoDebitosActionForm.set("valorGuiasPagamento", "0,00");

						sessao.removeAttribute("colecaoGuiaPagamentoValoresSelecionadas");
						sessao.removeAttribute("colecaoGuiaPagamentoValoresImovel");
					}
				}else{
					efetuarParcelamentoDebitosActionForm.set("valorGuiasPagamento", "0,00");
					efetuarParcelamentoDebitosActionForm.set("chavesPrestacoes", "");

					sessao.removeAttribute("colecaoGuiaPagamentoValoresSelecionadas");
					sessao.removeAttribute("colecaoGuiaPagamentoValoresImovel");
				}

				// Acrescimos por Impontualidade
				BigDecimal retornoSoma = BigDecimal.ZERO;
				if(indicadorAcrescimosImpotualidade.equals("1")){
					retornoSoma.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
					retornoSoma = retornoSoma.add(valorTotalAcrescimoImpontualidadeContas);
					retornoSoma = retornoSoma.add(valorTotalAcrescimoImpontualidadeGuias);

					efetuarParcelamentoDebitosActionForm.set("valorAcrescimosImpontualidade", Util.formatarMoedaReal(retornoSoma));
					sessao.setAttribute("valorAcrescimosImpontualidade", retornoSoma);

				}else{
					efetuarParcelamentoDebitosActionForm.set("valorAcrescimosImpontualidade", "0,00");
					sessao.setAttribute("valorAcrescimosImpontualidade", BigDecimal.ZERO);
				}

				efetuarParcelamentoDebitosActionForm.set("valorAtualizacaoMonetaria", Util.formatarMoedaReal(valorAtualizacaoMonetaria));
				efetuarParcelamentoDebitosActionForm.set("valorJurosMora", Util.formatarMoedaReal(valorJurosMora));
				efetuarParcelamentoDebitosActionForm.set("valorMulta", Util.formatarMoedaReal(valorMulta));

				// Para o c�lculo do D�bito Total Atualizado
				valorTotalAcrescimoImpontualidade = retornoSoma;

				// Debitos A Cobrar
				if(indicadorDebitosACobrar.equals("1")){

					// [FS0022]-Verificar exist�ncia de juros sobre im�vel
					Collection<DebitoACobrar> colecaoDebitoACobrar = colecaoDebitoCliente.getColecaoDebitoACobrar();

					if(colecaoDebitoACobrar != null && !colecaoDebitoACobrar.isEmpty()){
						Iterator<DebitoACobrar> debitoACobrarValores = colecaoDebitoACobrar.iterator();

						final int indiceCurtoPrazo = 0;
						final int indiceLongoPrazo = 1;

						// Par�metro que cont�m os tipos de financiamentos de parcelamento
						String[] parametroFinanciamentoTipoParcelamento = null;
						ArrayList<Integer> colecaoFinanciamentosTiposParcelamento = new ArrayList<Integer>();

						try{
							// Recupera os valores do par�metro
							parametroFinanciamentoTipoParcelamento = ((String) ParametroParcelamento.P_FINANCIAMENTO_TIPO_PARCELAMENTO
											.executar()).split(",");

							// carrega valores dos tipos de finaciamentos para parcelamento
							for(String parametroFinanciamentoTipo : parametroFinanciamentoTipoParcelamento){

								colecaoFinanciamentosTiposParcelamento.add(Integer.valueOf(parametroFinanciamentoTipo));
							}
						}catch(ControladorException e){
							e.printStackTrace();
						}

						while(debitoACobrarValores.hasNext()){
							DebitoACobrar debitoACobrar = debitoACobrarValores.next();

							// [FS0022]-Verificar exist�ncia de juros sobre im�vel
							if(debitoACobrar.getDebitoTipo().getId() != null
											&& !debitoACobrar.getDebitoTipo().getId().equals(DebitoTipo.JUROS_SOBRE_PARCELAMENTO)){

								if(!Util.isVazioOrNulo(colecaoFinanciamentosTiposParcelamento)){
									// Debitos A Cobrar - Servi�o
									// Caso o financiamento tipo n�o seja de parcelamento
									// Acumula como Servi�o
									if(!colecaoFinanciamentosTiposParcelamento.contains(debitoACobrar.getFinanciamentoTipo().getId())){
										// [SB0001] Obter Valores de Curto e Longo Prazo
										valorRestanteACobrar = debitoACobrar.getValorTotal();

										BigDecimal[] valoresDeCurtoELongoPrazo = fachada.obterValorACobrarDeCurtoELongoPrazo(debitoACobrar
														.getNumeroPrestacaoDebito(), debitoACobrar.getNumeroPrestacaoCobradas(),
														valorRestanteACobrar);
										valorTotalRestanteServicosACobrarCurtoPrazo.setScale(Parcelamento.CASAS_DECIMAIS,
														Parcelamento.TIPO_ARREDONDAMENTO);
										valorTotalRestanteServicosACobrarCurtoPrazo = valorTotalRestanteServicosACobrarCurtoPrazo
														.add(valoresDeCurtoELongoPrazo[indiceCurtoPrazo]);
										valorTotalRestanteServicosACobrarLongoPrazo.setScale(Parcelamento.CASAS_DECIMAIS,
														Parcelamento.TIPO_ARREDONDAMENTO);
										valorTotalRestanteServicosACobrarLongoPrazo = valorTotalRestanteServicosACobrarLongoPrazo
														.add(valoresDeCurtoELongoPrazo[indiceLongoPrazo]);
									}

									// Debitos A Cobrar - Parcelamento
									// Caso o financiamento tipo seja de parcelamento
									// Acumula como Parcelamento
									if(colecaoFinanciamentosTiposParcelamento.contains(debitoACobrar.getFinanciamentoTipo().getId())){
										// [SB0001] Obter Valores de Curto e Longo Prazo
										valorRestanteACobrar = debitoACobrar.getValorTotal();

										BigDecimal[] valoresDeCurtoELongoPrazo = fachada.obterValorACobrarDeCurtoELongoPrazo(debitoACobrar
														.getNumeroPrestacaoDebito(), debitoACobrar.getNumeroPrestacaoCobradas(),
														valorRestanteACobrar);
										valorTotalRestanteParcelamentosACobrarCurtoPrazo.setScale(Parcelamento.CASAS_DECIMAIS,
														Parcelamento.TIPO_ARREDONDAMENTO);
										valorTotalRestanteParcelamentosACobrarCurtoPrazo = valorTotalRestanteParcelamentosACobrarCurtoPrazo
														.add(valoresDeCurtoELongoPrazo[indiceCurtoPrazo]);
										valorTotalRestanteParcelamentosACobrarLongoPrazo.setScale(Parcelamento.CASAS_DECIMAIS,
														Parcelamento.TIPO_ARREDONDAMENTO);
										valorTotalRestanteParcelamentosACobrarLongoPrazo = valorTotalRestanteParcelamentosACobrarLongoPrazo
														.add(valoresDeCurtoELongoPrazo[indiceLongoPrazo]);
									}
								}
							}
						}
						sessao.setAttribute("colecaoDebitoACobrar", colecaoDebitoACobrar);
						sessao.removeAttribute("colecaoDebitoACobrarImovel");

						// Servi�os
						valorTotalRestanteServicosACobrar.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
						valorTotalRestanteServicosACobrar = valorTotalRestanteServicosACobrarCurtoPrazo
										.add(valorTotalRestanteServicosACobrarLongoPrazo);
						efetuarParcelamentoDebitosActionForm.set("valorDebitoACobrarServico", Util
										.formatarMoedaReal(valorTotalRestanteServicosACobrar));

						// Parcelamentos
						valorTotalRestanteParcelamentosACobrar.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
						valorTotalRestanteParcelamentosACobrar = valorTotalRestanteParcelamentosACobrarCurtoPrazo
										.add(valorTotalRestanteParcelamentosACobrarLongoPrazo);
						efetuarParcelamentoDebitosActionForm.set("valorDebitoACobrarParcelamento", Util
										.formatarMoedaReal(valorTotalRestanteParcelamentosACobrar));

						// Est� no hidden no formul�rio
						efetuarParcelamentoDebitosActionForm.set("valorDebitoACobrarServicoLongoPrazo", Util
										.formatarMoedaReal(valorTotalRestanteServicosACobrarLongoPrazo));
						// Est� no hidden no formul�rio
						efetuarParcelamentoDebitosActionForm.set("valorDebitoACobrarServicoCurtoPrazo", Util
										.formatarMoedaReal(valorTotalRestanteServicosACobrarCurtoPrazo));

						// Est� no hidden no formul�rio
						efetuarParcelamentoDebitosActionForm.set("valorDebitoACobrarParcelamentoLongoPrazo", Util
										.formatarMoedaReal(valorTotalRestanteParcelamentosACobrarLongoPrazo));
						// Est� no hidden no formul�rio
						efetuarParcelamentoDebitosActionForm.set("valorDebitoACobrarParcelamentoCurtoPrazo", Util
										.formatarMoedaReal(valorTotalRestanteParcelamentosACobrarCurtoPrazo));
					}else{
						efetuarParcelamentoDebitosActionForm.set("valorDebitoACobrarServico", "0,00");
						efetuarParcelamentoDebitosActionForm.set("valorDebitoACobrarServicoLongoPrazo", "0,00");
						efetuarParcelamentoDebitosActionForm.set("valorDebitoACobrarServicoCurtoPrazo", "0,00");

						efetuarParcelamentoDebitosActionForm.set("valorDebitoACobrarParcelamento", "0,00");
						efetuarParcelamentoDebitosActionForm.set("valorDebitoACobrarParcelamentoLongoPrazo", "0,00");
						efetuarParcelamentoDebitosActionForm.set("valorDebitoACobrarParcelamentoCurtoPrazo", "0,00");

						sessao.removeAttribute("colecaoDebitoACobrar");
						sessao.removeAttribute("colecaoDebitoACobrarImovel");
					}
				}else{
					efetuarParcelamentoDebitosActionForm.set("valorDebitoACobrarServico", "0,00");
					efetuarParcelamentoDebitosActionForm.set("valorDebitoACobrarServicoLongoPrazo", "0,00");
					efetuarParcelamentoDebitosActionForm.set("valorDebitoACobrarServicoCurtoPrazo", "0,00");

					efetuarParcelamentoDebitosActionForm.set("valorDebitoACobrarParcelamento", "0,00");
					efetuarParcelamentoDebitosActionForm.set("valorDebitoACobrarParcelamentoLongoPrazo", "0,00");
					efetuarParcelamentoDebitosActionForm.set("valorDebitoACobrarParcelamentoCurtoPrazo", "0,00");

					sessao.removeAttribute("colecaoDebitoACobrar");
					sessao.removeAttribute("colecaoDebitoACobrarImovel");
				}

				// Cr�dito A Realizar
				if(indicadorCreditoARealizar.equals("1")){
					Collection<CreditoARealizar> colecaoCreditoARealizarInicial = colecaoDebitoCliente.getColecaoCreditoARealizar();

					// [FS0042] - Verificar exist�ncia de cr�ditos a realizar correspondentes a
					// desconto
					// nos acr�scimos concedido no parcelamento
					// ----------------------------------------------------------------------------------------------
					Collection colecaoCreditoARealizar = fachada
									.verificarCreditoARealiazarComConcessaoDesconto(colecaoCreditoARealizarInicial);

					// ----------------------------------------------------------------------------------------------

					if(colecaoCreditoARealizar != null && !colecaoCreditoARealizar.isEmpty()){
						Iterator<CreditoARealizar> creditoARealizarValores = colecaoCreditoARealizar.iterator();
						while(creditoARealizarValores.hasNext()){
							CreditoARealizar creditoARealizar = creditoARealizarValores.next();
							valorCreditoARealizar.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
							valorCreditoARealizar = valorCreditoARealizar.add(creditoARealizar.getValorTotal());
						}

						sessao.setAttribute("colecaoCreditoARealizar", colecaoCreditoARealizar);
						sessao.removeAttribute("colecaoCreditoARealizarImovel");

						efetuarParcelamentoDebitosActionForm.set("valorCreditoARealizar", Util.formatarMoedaReal(valorCreditoARealizar));
					}else{
						efetuarParcelamentoDebitosActionForm.set("valorCreditoARealizar", "0,00");

						sessao.removeAttribute("colecaoCreditoARealizar");
						sessao.removeAttribute("colecaoCreditoARealizarImovel");
					}
				}else{
					efetuarParcelamentoDebitosActionForm.set("valorCreditoARealizar", "0,00");

					sessao.removeAttribute("colecaoCreditoARealizar");
					sessao.removeAttribute("colecaoCreditoARealizarImovel");
				}

				// D�bito Total Atualizado
				BigDecimal debitoTotalAtualizado = BigDecimal.ZERO;
				BigDecimal debitoTotalOriginal = BigDecimal.ZERO;

				debitoTotalAtualizado.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
				debitoTotalOriginal.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);

				debitoTotalOriginal = debitoTotalOriginal.add(valorTotalContas);
				debitoTotalOriginal = debitoTotalOriginal.add(valorTotalGuiasPagamento);
				debitoTotalOriginal = debitoTotalOriginal.add(valorTotalRestanteServicosACobrar);
				debitoTotalOriginal = debitoTotalOriginal.add(valorTotalRestanteParcelamentosACobrar);
				debitoTotalOriginal = debitoTotalOriginal.subtract(valorCreditoARealizar);

				if(debitoTotalOriginal != null){
					efetuarParcelamentoDebitosActionForm.set("valorDebitoTotalOriginal", Util.formatarMoedaReal(debitoTotalOriginal));
				}

				debitoTotalAtualizado = debitoTotalOriginal.add(valorTotalAcrescimoImpontualidade);

				if((debitoTotalAtualizado.compareTo(BigDecimal.ZERO) != 1) && (colecaoGuiaPagamentoValores != null)
								&& colecaoGuiaPagamentoValores.isEmpty()){
					throw new ActionServletException("atencao.nao.existe.debito.a.parcelar");
				}

				efetuarParcelamentoDebitosActionForm.set("valorDebitoTotalAtualizado", Util.formatarMoedaReal(debitoTotalAtualizado));

				// Caso o valor Total do D�bito seja negativo n�o h� d�bitos para parcelar
				if((debitoTotalAtualizado.compareTo(BigDecimal.ZERO) == -1) && (colecaoGuiaPagamentoValores != null)
								&& (colecaoGuiaPagamentoValores.isEmpty())){
					throw new ActionServletException("atencao.imovel.sem.debitos", null, codigoImovel);
				}

				// Limpa os bot�es de r�dio da EP e NB da lista de contas
				if(httpServletRequest.getParameter("limpaCombo") != null && !httpServletRequest.getParameter("limpaCombo").equals("")){
					Collection<ContaValoresHelper> colecaoContas = (Collection) sessao.getAttribute("colecaoContaValores");

					if(colecaoContas != null && !colecaoContas.isEmpty()){
						Iterator<ContaValoresHelper> colecaoContasIterator = colecaoContas.iterator();
						while(colecaoContasIterator.hasNext()){
							ContaValoresHelper contaValoresHelper = colecaoContasIterator.next();
							contaValoresHelper.setIndicadorContasDebito(null);
						}
					}
				}

				// Avaliar valor do debito abaixo ou acima da faixa
				if(parcelamentoPerfil.getValorMaximoDebitoAParcelarFaixaDebito() != null){
					if(parcelamentoPerfil.getIndicadorDebitoOriginalOuAtualizadoFaixaDebito().equals(
									ParcelamentoPerfil.INDICADOR_DEBITO_ATUALIZADO)){
						if(debitoTotalAtualizado.compareTo(parcelamentoPerfil.getValorMaximoDebitoAParcelarFaixaDebito()) > 0){
							throw new ActionServletException("atencao.valor.debito.fora.intervalo.para.rd");
						}
					}else if(parcelamentoPerfil.getIndicadorDebitoOriginalOuAtualizadoFaixaDebito().equals(
									ParcelamentoPerfil.INDICADOR_DEBITO_ORIGINAL)){
						if(debitoTotalOriginal.compareTo(parcelamentoPerfil.getValorMaximoDebitoAParcelarFaixaDebito()) > 0){
							throw new ActionServletException("atencao.valor.debito.fora.intervalo.para.rd");
						}
					}
				}
				if(parcelamentoPerfil.getValorMinimoDebitoAParcelarFaixaDebito() != null){
					if(parcelamentoPerfil.getIndicadorDebitoOriginalOuAtualizadoFaixaDebito().equals(
									ParcelamentoPerfil.INDICADOR_DEBITO_ATUALIZADO)){
						if(debitoTotalAtualizado.compareTo(parcelamentoPerfil.getValorMinimoDebitoAParcelarFaixaDebito()) < 0){
							throw new ActionServletException("atencao.valor.debito.fora.intervalo.para.rd");
						}
					}else if(parcelamentoPerfil.getIndicadorDebitoOriginalOuAtualizadoFaixaDebito().equals(
									ParcelamentoPerfil.INDICADOR_DEBITO_ORIGINAL)){
						if(debitoTotalOriginal.compareTo(parcelamentoPerfil.getValorMinimoDebitoAParcelarFaixaDebito()) < 0){
							throw new ActionServletException("atencao.valor.debito.fora.intervalo.para.rd");
						}
					}
				}

				// [SB0025] � Validar autoriza��o de acesso ao im�vel em cobran�a administrativa
				// pelos usu�rios da empresa contratante
				if(fachada.existeContaOuGuiaPagamentoDebitoImovelOuCliente(colecaoContaValores, colecaoGuiaPagamentoValores)){
					// 1.1. Caso existam, na lista de contas retornada pelo [UC0067], contas em
					// cobran�a administrativa ou caso existam, na lista de guias de pagamento
					// retornada pelo [UC0067], guias de pagamento em cobran�a administrativa
					// [UC3062] - Validar Autoriza��o Acesso Im�vel Pela Empresa Cobran�a
					// Administrativa
					if(fachada.obterValidacaoAutorizacaoAcessoImovelCobrancaAdministrativa(usuario, Integer.valueOf(codigoImovel),
									ConstantesSistema.CODIGO_VALIDACAO_USUARIO_EMPRESA_CONTRATANTE).equals(ConstantesSistema.NAO)){
						throw new ActionServletException("atencao.usuario_sem_acesso_dados_cobranca_administrativa", usuario.getLogin(),
										Integer.valueOf(codigoImovel).toString());
					}
				}
			}
		}else if(calcula.equals("1")){
			CalculadorParcelamentoHelper.recalcularParcelamento(httpServletRequest, fachada, sessao, efetuarParcelamentoDebitosActionForm,
							verificaCalcula);
		}

		return retorno;
	}

	/**
	 * M�todo Usado para verificar se o motivo revis�o da conta informada est� incluso na lista de
	 * motivos de revis�o escolhidos no filtro da primeira aba.
	 * 
	 * @author Ailton Sousa
	 * @date 05/01/2011
	 * @param idMotivoRevisao
	 * @param idsMotivoRevisaoInt
	 * @return
	 */
	public static boolean isMotivoRevisaoFiltrado(Integer idMotivoRevisao, Integer[] idsMotivoRevisaoInt){

		if(idMotivoRevisao != null){
			for(int i = 0; i < idsMotivoRevisaoInt.length; i++){
				if(idsMotivoRevisaoInt[i].equals(idMotivoRevisao)){
					return true;
				}
			}
		}

		return false;
	}

}