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

package gcom.gui.cobranca;

import gcom.arrecadacao.pagamento.GuiaPagamentoPrestacao;
import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoSituacao;
import gcom.cadastro.cliente.*;
import gcom.cadastro.imovel.*;
import gcom.cobranca.BoletoBancario;
import gcom.cobranca.ResolucaoDiretoria;
import gcom.cobranca.bean.ContaValoresHelper;
import gcom.cobranca.bean.GuiaPagamentoValoresHelper;
import gcom.cobranca.bean.ObterDebitoImovelOuClienteHelper;
import gcom.cobranca.parcelamento.*;
import gcom.fachada.Fachada;
import gcom.faturamento.conta.Conta;
import gcom.faturamento.conta.ContaMotivoRevisao;
import gcom.faturamento.conta.FiltroMotivoRevisaoConta;
import gcom.faturamento.credito.CreditoARealizar;
import gcom.faturamento.debito.DebitoACobrar;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.Grupo;
import gcom.seguranca.acesso.PermissaoEspecial;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.filtro.ParametroNulo;
import gcom.util.filtro.ParametroSimples;
import gcom.util.filtro.ParametroSimplesDiferenteDe;
import gcom.util.parametrizacao.cobranca.ExecutorParametrosCobranca;
import gcom.util.parametrizacao.cobranca.ParametroCobranca;
import gcom.util.parametrizacao.cobranca.parcelamento.ParametroParcelamento;
import gcom.util.parametrizacao.faturamento.ExecutorParametrosFaturamento;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

/**
 * Permite efetuar o parcelamento dos d�bitos de um im�vel
 * [UC0214] Efetuar Parcelamento de D�bitos
 * Pr�-processamento da primeira p�gina
 * 
 * @author Rodrigo Avellar/Roberta Costa
 * @date 11/02/2006
 * @author Saulo Lima
 * @date 04/02/2009
 *       Corre��o de identa��o + consultar/exibir cliente do im�vel automaticamente + n�o apagar CPF
 *       ao retornar.
 */
public class ExibirEfetuarParcelamentoDebitosProcesso1Action
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("processo1");

		Fachada fachada = Fachada.getInstancia();

		HttpSession sessao = httpServletRequest.getSession(false);

		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");

		// -----------------------------------------------------------
		// Verificar as permiss�es especiais da p�gina
		this.verificarPermissoes(httpServletRequest, fachada, usuario);
		// -----------------------------------------------------------

		// -----------------------------------------------------------
		// 1.1. Caso o Indicador de Altera��o do Per�odo do Parcelamento esteja com o valor 1 (sim),
		// habilitar o campo �Intervalo do Parcelamento�.
		// -----------------------------------------------------------
		Short indicadorAlteracaoPeriodoParcelamento = ConstantesSistema.SIM;
		sessao.setAttribute("indicadorAlteracaoPeriodoParcelamento", indicadorAlteracaoPeriodoParcelamento);

		// -----------------------------------------------------------

		// Remove atributo antes da verifica��o para adicion�-lo na sess�o
		sessao.removeAttribute("reload");

		if(httpServletRequest.getParameter("verificarRDComRestricao") != null){
			Short indReload = Short.valueOf(httpServletRequest.getParameter("verificarRDComRestricao"));
			if(indReload.equals(ConstantesSistema.SIM)){
				sessao.setAttribute("reload", ConstantesSistema.SIM);
			}
		}

		// D�bito Total Atualizado
		BigDecimal debitoTotalAtualizado = BigDecimal.ZERO;
		Collection<ContaValoresHelper> colecaoContasImovel = null;

		DynaActionForm efetuarParcelamentoDebitosActionForm = (DynaActionForm) actionForm;

		if(httpServletRequest.getParameter("confirmado") != null && httpServletRequest.getParameter("confirmado").equalsIgnoreCase("ok")){
			if(!Util.isVazioOuBranco(efetuarParcelamentoDebitosActionForm.get("inicioIntervaloParcelamentoOriginal"))
							&& !Util.isVazioOuBranco(efetuarParcelamentoDebitosActionForm.get("fimIntervaloParcelamentoOriginal"))){
				efetuarParcelamentoDebitosActionForm.set("inicioIntervaloParcelamento", efetuarParcelamentoDebitosActionForm
								.get("inicioIntervaloParcelamentoOriginal"));
				efetuarParcelamentoDebitosActionForm.set("fimIntervaloParcelamento", efetuarParcelamentoDebitosActionForm
								.get("fimIntervaloParcelamentoOriginal"));
			}
		}

		if("sim".equalsIgnoreCase((String) httpServletRequest.getAttribute("popupEfetuarParcelamento"))
						|| "sim".equalsIgnoreCase((String) httpServletRequest.getParameter("popupEfetuarParcelamento"))){
			httpServletRequest.setAttribute("popupEfetuarParcelamento", "sim");
			sessao.setAttribute("popupEfetuarParcelamento", "sim");
		}

		// Pega o codigo que o usuario digitou para a pesquisa direta de imovel
		String codigoImovel = null;
		// if(httpServletRequest.getParameter("confirmado") != null &&
		// httpServletRequest.getParameter("confirmado").equalsIgnoreCase("ok")){
		codigoImovel = (String) efetuarParcelamentoDebitosActionForm.get("matriculaImovel");
		// }else{
		// codigoImovel = (String) httpServletRequest.getParameter("matriculaImovel");
		// }
		String codigoImovelAntes = (String) efetuarParcelamentoDebitosActionForm.get("codigoImovelAntes");

		
		String fimIntervaloParcelamento = (String) efetuarParcelamentoDebitosActionForm.get("fimIntervaloParcelamento");
		String inicioIntervaloParcelamento = (String) efetuarParcelamentoDebitosActionForm.get("inicioIntervaloParcelamento");
		String inicioIntervaloParcelamentoOriginal = (String) efetuarParcelamentoDebitosActionForm
						.get("inicioIntervaloParcelamentoOriginal");
		String fimIntervaloParcelamentoOriginal = (String) efetuarParcelamentoDebitosActionForm.get("fimIntervaloParcelamentoOriginal");
		
		// Pega dados do formul�rio
		String cpfClienteParcelamento = (String) httpServletRequest.getParameter("cpfClienteParcelamentoDigitado");

		String inscricaoImovel = null;
		if(httpServletRequest.getParameter("inscricaoImovel") != null && !httpServletRequest.getParameter("inscricaoImovel").equals("")){
			inscricaoImovel = (String) httpServletRequest.getParameter("inscricaoImovel");
		}

		Integer indicadorParcelamentoCobrancaBancaria = null;

		if(codigoImovel != null && !codigoImovel.trim().equals("")){

			try{
				indicadorParcelamentoCobrancaBancaria = Util.obterInteger((String) ParametroCobranca.P_PARCELAMENTO_COBRANCA_BANCARIA
								.executar(ExecutorParametrosCobranca.getInstancia()));
			}catch(ControladorException e){
				e.printStackTrace();
			}

			// Pesquisa os dados do cliente e do im�vel
			boolean existeImovel = this.pesquisarImovel(codigoImovel, actionForm, httpServletRequest, sessao, usuario);

			if(existeImovel){

				Short pVerificaParcelamentoMesFatCorrente;
				try{
					pVerificaParcelamentoMesFatCorrente = Short.valueOf(ParametroCobranca.P_VERIFICA_PARCELAMENTO_MES_FATURAMENTO_CORRENTE
									.executar());

					if(pVerificaParcelamentoMesFatCorrente.equals(ConstantesSistema.SIM)){
						// [FS0012] Verificar exist�ncia de parcelamento no m�s
						Collection<Parcelamento> colecaoParcelamento = fachada
										.verificarParcelamentoMesImovel(Integer.valueOf(codigoImovel));

						if(colecaoParcelamento != null && !colecaoParcelamento.isEmpty()){
							throw new ActionServletException("atencao.debito.ja.parcelado.mes.faturamento.corrente");
						}
					}

				}catch(NumberFormatException e1){
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}catch(ControladorException e1){
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				Short indicadorConsiderarPagamentoNaoClassificado = 2;
				ObterDebitoImovelOuClienteHelper colecaoDebitoImovel = null;
				if((!inicioIntervaloParcelamento.equals(inicioIntervaloParcelamentoOriginal))
								|| (!fimIntervaloParcelamento.equals(fimIntervaloParcelamentoOriginal))){
					// [UC0067] Obter D�bito do Im�vel ou Cliente
					colecaoDebitoImovel = fachada.obterDebitoImovelOuCliente(
									1, // Indicador
									// d�bito
									// im�vel
									codigoImovel, // Matr�cula do im�vel
									null, // C�digo do cliente
									null, // Tipo de rela��o do cliento com o
									// im�vel
									!Util.isVazioOuBranco(inicioIntervaloParcelamento) ? Util
													.formatarMesAnoParaAnoMesSemBarra(inicioIntervaloParcelamento) : "000101",
									// Refer�ncia inicial do d�bito
									!Util.isVazioOuBranco(fimIntervaloParcelamento) ? Util
													.formatarMesAnoParaAnoMesSemBarra(fimIntervaloParcelamento) : "999912",
									// Refer�ncia final do d�bito
									Util.converteStringParaDate("01/01/0001"), // Inicio
									// Vencimento
									Util.converteStringParaDate("31/12/9999"), // Final
									// Vencimento
									1, // Indicador pagamento
									1, // Indicador conta em revis�o
									1, // Indicador d�bito a cobrar
									1, // Indicador cr�dito a realizar
									1, // Indicador notas promiss�rias
									1, // Indicador guias de pagamento
									1, // Indicador acr�scimos por impontualidade
									null, // Indicador Contas
									null, null, null, indicadorConsiderarPagamentoNaoClassificado, ConstantesSistema.SIM,
									ConstantesSistema.SIM, ConstantesSistema.SIM); // Sistema
				}else{
					// [UC0067] Obter D�bito do Im�vel ou Cliente
					colecaoDebitoImovel = fachada.obterDebitoImovelOuCliente(
									1, // Indicador
									// d�bito
									// im�vel
									codigoImovel, // Matr�cula do im�vel
									null, // C�digo do cliente
									null, // Tipo de rela��o do cliento com o
									// im�vel
									"000101", // Refer�ncia inicial do d�bito
									"999912", // Refer�ncia final do d�bito
									Util.converteStringParaDate("01/01/0001"), // Inicio
									// Vencimento
									Util.converteStringParaDate("31/12/9999"), // Final
									// Vencimento
									1, // Indicador pagamento
									1, // Indicador conta em revis�o
									1, // Indicador d�bito a cobrar
									1, // Indicador cr�dito a realizar
									1, // Indicador notas promiss�rias
									1, // Indicador guias de pagamento
									1, // Indicador acr�scimos por impontualidade
									null, // Indicador Contas
									null, null, null, indicadorConsiderarPagamentoNaoClassificado, ConstantesSistema.SIM,
									ConstantesSistema.SIM, ConstantesSistema.SIM); // Sistema
				}
				
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
								colecaoDebitoImovel);

				// [SB0024] � Validar autoriza��o de acesso ao im�vel pelos usu�rios das empresas de
				// cobran�a administrativa
				Integer resposta = fachada.validarAcessoImovelUsuarioEmpresaCobrancaAdministrativa(usuario, Integer.valueOf(codigoImovel),
								colecaoDebitoImovel);

				if(resposta.equals(Integer.valueOf(1)) || resposta.equals(Integer.valueOf(3))){
					throw new ActionServletException("atencao.usuario_sem_acesso_dados_cobranca_administrativa", usuario.getLogin(),
									codigoImovel);
				}

				// [SB0025] � Validar autoriza��o de acesso ao im�vel em cobran�a administrativa
				// pelos usu�rios da empresa contratante
				Integer permiteAcessoImovelEmCobrancaAdministrativaUsuarioEmpresaContratante = fachada
								.validarAcessoImovelEmCobrancaAdministrativaUsuarioEmpresaContratante(usuario,
												Integer.valueOf(codigoImovel), colecaoDebitoImovel);

				if(permiteAcessoImovelEmCobrancaAdministrativaUsuarioEmpresaContratante != null){

					if(permiteAcessoImovelEmCobrancaAdministrativaUsuarioEmpresaContratante.intValue() == 1){

						throw new ActionServletException("atencao.usuario_sem_acesso_dados_cobranca_administrativa_imovel_1", codigoImovel,
										usuario.getLogin());

					}else if(permiteAcessoImovelEmCobrancaAdministrativaUsuarioEmpresaContratante.intValue() == 2){

						throw new ActionServletException("atencao.usuario_sem_acesso_dados_cobranca_administrativa_imovel_2", codigoImovel,
										usuario.getLogin());
					}
				}

				// Dados do D�bito do Im�vel - Contas
				colecaoContasImovel = colecaoDebitoImovel.getColecaoContasValoresImovel();
				// Collection<ContaValoresHelper> colecaoContasValoresImovel =
				// colecaoDebitoImovel.getColecaoContasValoresImovel();

				Collection<GuiaPagamentoValoresHelper> colecaoGuiaPagamentoValoresImovel = colecaoDebitoImovel
								.getColecaoGuiasPagamentoValores();

				// [SB0026] � Verificar D�bito em Cobran�a Administrativa
				if(fachada.existeEmpresaCobrancaContrato(usuario.getEmpresa().getId())){
					fachada.removerContaCobrancaAdministrativaDebitoImovel(usuario, Integer.valueOf(codigoImovel), colecaoContasImovel);
					fachada.removerGuiaPagamentoCobrancaAdministrativaDebitoImovel(usuario, Integer.valueOf(codigoImovel),
									colecaoGuiaPagamentoValoresImovel);
				}

				// Verificar Situa��es Especiais de Coban�a do im�vel
				this.verificarImovelSituacaoEspecialCobrancaParcelamento(efetuarParcelamentoDebitosActionForm,
								Integer.valueOf(codigoImovel), usuario);

				// [FS0044] Retirar D�bitos N�o Vencidos
				colecaoContasImovel = fachada.retirarContasNaoVencidas(colecaoContasImovel);

				// Retirar as contas com Motivo de Revis�o com Imedimento, se for o caso
				colecaoContasImovel = fachada.retirarContasMotivoRevisaoComImpedimentoParcelamento(colecaoContasImovel, usuario);

				// [FS0037] - Verificar exist�ncia de guia de parcelamento de cobran�a banc�ria

				// [FS0041] - Verificar exist�ncia de guias correspondentes a presta��es de
				// parcelamento com concess�o de desconto nos acr�scimos
				// ----------------------------------------------------------------------------------------------
				if(!Util.isVazioOrNulo(colecaoGuiaPagamentoValoresImovel)){
					fachada.removerGuiaDePagamentoNaComposicaoDoDebitoParcelamennto(colecaoGuiaPagamentoValoresImovel);
				}
				// ----------------------------------------------------------------------------------------------

				// [FS0040] - Verificar exist�ncia de itens de parcelamento anterior na mesma
				// refer�ncia
				Collection<DebitoACobrar> colecaoDebitoACobrarAux = colecaoDebitoImovel.getColecaoDebitoACobrar();

				Collection<CreditoARealizar> colecaoCreditoARealizarAux = colecaoDebitoImovel.getColecaoCreditoARealizar();

				fachada.verificarExistenciaDeItensDeParcelamentoAnteriorNaMesmaReferencia(Integer.valueOf(codigoImovel),
								colecaoContasImovel, colecaoGuiaPagamentoValoresImovel, colecaoDebitoACobrarAux,
								colecaoCreditoARealizarAux);

				// [FS0042] - Verificar exist�ncia de cr�ditos a realizar correspondentes a desconto
				// nos acr�scimos concedido no parcelamento
				// ----------------------------------------------------------------------------------------------
				Collection colecaoCreditoARealizarRemover = fachada
								.verificarCreditoARealiazarComConcessaoDesconto(colecaoCreditoARealizarAux);

				colecaoDebitoImovel.setColecaoCreditoARealizar(colecaoCreditoARealizarRemover);

				// ----------------------------------------------------------------------------------------------

				// [FS0014] Verificar exist�ncia de d�bitos para o im�vel
				// Caso n�o exista d�bito
				if((colecaoContasImovel == null || colecaoContasImovel.size() == 0)
								&& (colecaoGuiaPagamentoValoresImovel == null || colecaoGuiaPagamentoValoresImovel.size() == 0)
								&& (colecaoDebitoImovel.getColecaoDebitoACobrar() == null || colecaoDebitoImovel.getColecaoDebitoACobrar()
												.size() == 0)){

					ActionServletException actionServletException = new ActionServletException("atencao.imovel.sem.debitos", null, codigoImovel);
					actionServletException.setUrlBotaoVoltar("exibirEfetuarParcelamentoDebitosAction.do");
					throw actionServletException;
				}

				// Para o c�lculo do D�bito Total Atualizado
				BigDecimal valorTotalContas = new BigDecimal("0.00");
				BigDecimal valorTotalAcrescimoImpontualidade = new BigDecimal("0.00");
				BigDecimal valorTotalRestanteServicosACobrar = new BigDecimal("0.00");
				BigDecimal valorTotalRestanteServicosACobrarCurtoPrazo = new BigDecimal("0.00");
				BigDecimal valorTotalRestanteServicosACobrarLongoPrazo = new BigDecimal("0.00");
				BigDecimal valorTotalRestanteParcelamentosACobrar = new BigDecimal("0.00");
				BigDecimal valorTotalRestanteParcelamentosACobrarCurtoPrazo = new BigDecimal("0.00");
				BigDecimal valorTotalRestanteParcelamentosACobrarLongoPrazo = new BigDecimal("0.00");
				BigDecimal valorTotalGuiasPagamento = new BigDecimal("0.00");
				BigDecimal valorTotalAcrescimoImpontualidadeContas = new BigDecimal("0.00");
				BigDecimal valorTotalAcrescimoImpontualidadeGuias = new BigDecimal("0.00");
				BigDecimal valorCreditoARealizar = new BigDecimal("0.00");
				BigDecimal valorRestanteACobrar = new BigDecimal("0.00");
				BigDecimal valorAtualizacaoMonetaria = new BigDecimal("0.00");
				BigDecimal valorJurosMora = new BigDecimal("0.00");
				BigDecimal valorMulta = new BigDecimal("0.00");

				// Dados do D�bito do Im�vel - Contas
				// colecaoContasImovel = colecaoDebitoImovel.getColecaoContasValoresImovel();

				if(colecaoContasImovel != null && !colecaoContasImovel.isEmpty()){
					Iterator contaValores = colecaoContasImovel.iterator();
					while(contaValores.hasNext()){
						ContaValoresHelper contaValoresHelper = (ContaValoresHelper) contaValores.next();
						valorTotalContas.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
						valorTotalContas = valorTotalContas.add(contaValoresHelper.getValorTotalConta());
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
										.getValorTotalContaValoresParcelamento());
					}

					sessao.setAttribute("colecaoContaValoresImovel", colecaoContasImovel);

					efetuarParcelamentoDebitosActionForm.set("valorTotalContasImovel", Util.formatarMoedaReal(valorTotalContas));
				}else{
					efetuarParcelamentoDebitosActionForm.set("valorTotalContasImovel", "0,00");
				}

				// Guias de Pagamento
				Set<String> chavesPrestacoesSet = new HashSet<String>();
				if(colecaoGuiaPagamentoValoresImovel != null && !colecaoGuiaPagamentoValoresImovel.isEmpty()){

					Iterator<GuiaPagamentoValoresHelper> guiaPagamentoValores = colecaoGuiaPagamentoValoresImovel.iterator();

					while(guiaPagamentoValores.hasNext()){
						GuiaPagamentoValoresHelper guiaPagamentoValoresHelper = guiaPagamentoValores.next();
						if(guiaPagamentoValoresHelper.getValorTotalPrestacao() != null){
							valorTotalGuiasPagamento.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
							valorTotalGuiasPagamento = valorTotalGuiasPagamento.add(guiaPagamentoValoresHelper.getValorTotalPrestacao()
											.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO));
						}
						if(guiaPagamentoValoresHelper.getValorAtualizacaoMonetaria() != null
										&& !guiaPagamentoValoresHelper.getValorAtualizacaoMonetaria().equals("")){
							valorAtualizacaoMonetaria.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
							valorAtualizacaoMonetaria = valorAtualizacaoMonetaria.add(guiaPagamentoValoresHelper
											.getValorAtualizacaoMonetaria().setScale(Parcelamento.CASAS_DECIMAIS,
															Parcelamento.TIPO_ARREDONDAMENTO));
						}
						if(guiaPagamentoValoresHelper.getValorJurosMora() != null
										&& !guiaPagamentoValoresHelper.getValorJurosMora().equals("")){
							valorJurosMora.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
							valorJurosMora = valorJurosMora.add(guiaPagamentoValoresHelper.getValorJurosMora().setScale(
											Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO));
						}
						if(guiaPagamentoValoresHelper.getValorMulta() != null && !guiaPagamentoValoresHelper.getValorMulta().equals("")){
							valorMulta.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
							valorMulta = valorMulta.add(guiaPagamentoValoresHelper.getValorMulta());
						}

						// Para c�lculo do Acrescimo de Impontualidade
						if(guiaPagamentoValoresHelper.getValorAcrescimosImpontualidade() != null
										&& !guiaPagamentoValoresHelper.getValorAcrescimosImpontualidade().equals("")){
							valorTotalAcrescimoImpontualidadeGuias.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
							valorTotalAcrescimoImpontualidadeGuias = valorTotalAcrescimoImpontualidadeGuias.add(guiaPagamentoValoresHelper
											.getValorAcrescimosImpontualidade());
						}
						chavesPrestacoesSet.add(guiaPagamentoValoresHelper.getIdGuiaPagamento() + "-"
										+ guiaPagamentoValoresHelper.getNumeroPrestacao());
					}

					/*
					 * String contendo o 'Id da guia' e o 'n�mero da presta��o' separados por '-' de
					 * todos os itens selecionados em tela e concatenados por '$'
					 * (Ex.: 9988-1$9988-2$7766-1)
					 */
					String chavesPrestacoesString = "";
					if(!chavesPrestacoesSet.isEmpty()){
						for(String chave : chavesPrestacoesSet){
							chavesPrestacoesString += chave + "$";
						}
						chavesPrestacoesString = chavesPrestacoesString.substring(0, chavesPrestacoesString.length() - 1);
					}
					efetuarParcelamentoDebitosActionForm.set("chavesPrestacoes", chavesPrestacoesString);

					sessao.setAttribute("colecaoGuiaPagamentoValoresImovel", colecaoGuiaPagamentoValoresImovel);

					efetuarParcelamentoDebitosActionForm.set("valorGuiasPagamentoImovel", Util.formatarMoedaReal(valorTotalGuiasPagamento));
				}else{
					efetuarParcelamentoDebitosActionForm.set("valorGuiasPagamentoImovel", "0,00");
				}

				// Acrescimos por Impotualidade
				BigDecimal retornoSoma = new BigDecimal("0.00");
				retornoSoma.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
				retornoSoma = retornoSoma.add(valorTotalAcrescimoImpontualidadeContas);
				retornoSoma = retornoSoma.add(valorTotalAcrescimoImpontualidadeGuias);

				sessao.setAttribute("valorAcrescimosImpontualidadeImovel", retornoSoma);
				efetuarParcelamentoDebitosActionForm.set("valorAcrescimosImpontualidadeImovel", Util.formatarMoedaReal(retornoSoma));

				// Para o c�lculo do D�bito Total Atualizado
				valorTotalAcrescimoImpontualidade = retornoSoma;

				// Debitos A Cobrar
				Collection colecaoDebitoACobrar = colecaoDebitoImovel.getColecaoDebitoACobrar();

				if(colecaoDebitoACobrar != null && !colecaoDebitoACobrar.isEmpty()){
					Iterator debitoACobrarValores = colecaoDebitoACobrar.iterator();

					final int indiceCurtoPrazo = 0;
					final int indiceLongoPrazo = 1;

					// Par�metro que cont�m os tipos de financiamentos de parcelamento
					String[] parametroFinanciamentoTipoParcelamento = null;
					ArrayList<Integer> colecaoFinanciamentosTiposParcelamento = new ArrayList<Integer>();

					try{
						// Recupera os valores do par�metro
						parametroFinanciamentoTipoParcelamento = ((String) ParametroParcelamento.P_FINANCIAMENTO_TIPO_PARCELAMENTO
										.executar())
										.split(",");

						// carrega valores dos tipos de finaciamentos para parcelamento
						for(String parametroFinanciamentoTipo : parametroFinanciamentoTipoParcelamento){

							colecaoFinanciamentosTiposParcelamento.add(Integer.valueOf(parametroFinanciamentoTipo));
						}

						Integer tipoDebitoJurosParcelamento = Integer
										.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_JUROS_PARCELAMENTO.executar());
						Integer tipoDebitoJurosParcelamentoCobrancaAdministrativa = Integer
										.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_JUROS_PARCELAMENTO_COBRANCA_ADMINISTRATIVA
														.executar());

						while(debitoACobrarValores.hasNext()){
							DebitoACobrar debitoACobrar = (DebitoACobrar) debitoACobrarValores.next();

							// [FS0022]-Verificar exist�ncia de juros sobre im�vel
							if(debitoACobrar.getDebitoTipo().getId() != null
											&& !debitoACobrar.getDebitoTipo().getId().equals(tipoDebitoJurosParcelamento)
											&& !debitoACobrar.getDebitoTipo().getId().equals(
															tipoDebitoJurosParcelamentoCobrancaAdministrativa)){

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
					}catch(ControladorException e){
						e.printStackTrace();
					}

					sessao.setAttribute("colecaoDebitoACobrarImovel", colecaoDebitoACobrar);

					// Servi�os
					valorTotalRestanteServicosACobrar.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
					valorTotalRestanteServicosACobrar = valorTotalRestanteServicosACobrarCurtoPrazo
									.add(valorTotalRestanteServicosACobrarLongoPrazo);
					// Est� no hidden no formul�rio
					efetuarParcelamentoDebitosActionForm.set("valorDebitoACobrarServicoLongoPrazo", Util
									.formatarMoedaReal(valorTotalRestanteServicosACobrarLongoPrazo));
					// Est� no hidden no formul�rio
					efetuarParcelamentoDebitosActionForm.set("valorDebitoACobrarServicoCurtoPrazo", Util
									.formatarMoedaReal(valorTotalRestanteServicosACobrarCurtoPrazo));
					// Est� no TEXT
					efetuarParcelamentoDebitosActionForm.set("valorDebitoACobrarServicoImovel", Util
									.formatarMoedaReal(valorTotalRestanteServicosACobrar));
					// Parcelamentos
					valorTotalRestanteParcelamentosACobrar = valorTotalRestanteParcelamentosACobrarCurtoPrazo
									.add(valorTotalRestanteParcelamentosACobrarLongoPrazo);
					// Est� no hidden no formul�rio
					efetuarParcelamentoDebitosActionForm.set("valorDebitoACobrarParcelamentoLongoPrazo", Util
									.formatarMoedaReal(valorTotalRestanteParcelamentosACobrarLongoPrazo));
					// Est� no hidden no formul�rio
					efetuarParcelamentoDebitosActionForm.set("valorDebitoACobrarParcelamentoCurtoPrazo", Util
									.formatarMoedaReal(valorTotalRestanteParcelamentosACobrarCurtoPrazo));
					// Est� no TEXT
					efetuarParcelamentoDebitosActionForm.set("valorDebitoACobrarParcelamentoImovel", Util
									.formatarMoedaReal(valorTotalRestanteParcelamentosACobrar));
				}else{
					efetuarParcelamentoDebitosActionForm.set("valorDebitoACobrarServicoImovel", "0,00");
					efetuarParcelamentoDebitosActionForm.set("valorDebitoACobrarParcelamentoImovel", "0,00");
				}

				// Cr�dito A Realizar
				Collection<CreditoARealizar> colecaoCreditoARealizar = colecaoDebitoImovel.getColecaoCreditoARealizar();

				if(colecaoCreditoARealizar != null && !colecaoCreditoARealizar.isEmpty()){

					Iterator creditoARealizarValores = colecaoCreditoARealizar.iterator();

					while(creditoARealizarValores.hasNext()){
						CreditoARealizar creditoARealizar = (CreditoARealizar) creditoARealizarValores.next();
						valorCreditoARealizar.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
						valorCreditoARealizar = valorCreditoARealizar.add(creditoARealizar.getValorTotal());
					}

					sessao.setAttribute("colecaoCreditoARealizarImovel", colecaoCreditoARealizar);

					efetuarParcelamentoDebitosActionForm.set("valorCreditoARealizarImovel", Util.formatarMoedaReal(valorCreditoARealizar));
				}else{
					efetuarParcelamentoDebitosActionForm.set("valorCreditoARealizarImovel", "0,00");
				}

				// // D�bito Total Atualizado
				// BigDecimal debitoTotalAtualizado = new BigDecimal("0.00");

				debitoTotalAtualizado.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
				debitoTotalAtualizado = debitoTotalAtualizado.add(valorTotalContas);
				debitoTotalAtualizado = debitoTotalAtualizado.add(valorTotalGuiasPagamento);
				debitoTotalAtualizado = debitoTotalAtualizado.add(valorTotalAcrescimoImpontualidade);
				debitoTotalAtualizado = debitoTotalAtualizado.add(valorTotalRestanteServicosACobrar);
				debitoTotalAtualizado = debitoTotalAtualizado.add(valorTotalRestanteParcelamentosACobrar);
				debitoTotalAtualizado = debitoTotalAtualizado.subtract(valorCreditoARealizar);
				debitoTotalAtualizado.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);

				sessao.setAttribute("valorDebitoTotalAtualizadoImovel", debitoTotalAtualizado);
				efetuarParcelamentoDebitosActionForm.set("valorDebitoTotalAtualizadoImovel", Util.formatarMoedaReal(debitoTotalAtualizado));

				// Quando mudar de im�vel iniciar a data do parcelamento com a
				// data atual, limpar a resolu��o de diretoria
				if(!codigoImovelAntes.equals(codigoImovel)){

					// Reinicia a Data do Parcelamento
					SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
					Calendar dataCorrente = new GregorianCalendar();

					efetuarParcelamentoDebitosActionForm.set("dataParcelamento", "" + formatoData.format(dataCorrente.getTime()));

					// Limpa Resolu��o de Diretoria
					efetuarParcelamentoDebitosActionForm.set("resolucaoDiretoria", "");

					efetuarParcelamentoDebitosActionForm.set("inicioIntervaloParcelamento", "");
					// Limpa fim do Intervalo do Parcelamento
					efetuarParcelamentoDebitosActionForm.set("fimIntervaloParcelamento", "");

					// Limpa as perguntas
					// efetuarParcelamentoDebitosActionForm.set("indicadorContasRevisao", "2");
					efetuarParcelamentoDebitosActionForm.set("indicadorContasRevisao", "");
					efetuarParcelamentoDebitosActionForm.set("indicadorGuiasPagamento", "");
					efetuarParcelamentoDebitosActionForm.set("indicadorAcrescimosImpotualidade", "");
					efetuarParcelamentoDebitosActionForm.set("indicadorDebitosACobrar", "");
					efetuarParcelamentoDebitosActionForm.set("indicadorCreditoARealizar", "");
					efetuarParcelamentoDebitosActionForm.set("indicadorParcelamentoCobrancaBancaria", "2");

				}

				// Intervalo do Parcelamento
				if(colecaoContasImovel != null && !colecaoContasImovel.isEmpty()){
					Iterator contaValores = colecaoContasImovel.iterator();
					int anoMesReferenciaColecao = 0;
					int menorAnoMesReferencia = 999999;
					int maiorAnoMesReferencia = 0;

					while(contaValores.hasNext()){
						ContaValoresHelper contaValoresHelper = (ContaValoresHelper) contaValores.next();
						anoMesReferenciaColecao = contaValoresHelper.getConta().getReferencia();
						if(anoMesReferenciaColecao < menorAnoMesReferencia){
							menorAnoMesReferencia = anoMesReferenciaColecao;
						}
						if(anoMesReferenciaColecao > maiorAnoMesReferencia){
							maiorAnoMesReferencia = anoMesReferenciaColecao;
						}
					}

				}else{
					// [FS0015] Verificar exist�ncia de contas
					// Caso n�o existam contas para o im�vel deixar indispon�vel
					// o campo m�s/ano de refer�ncia inicial e m�s/ano de refer�ncia final
					efetuarParcelamentoDebitosActionForm.set("inicioIntervaloParcelamento", "");
					efetuarParcelamentoDebitosActionForm.set("fimIntervaloParcelamento", "");
				}

				String exibirParcelamentoCobrancaBancaria = null;

				// 2.5.2. Caso seja poss�vel o parcelamento de cobran�a banc�ria
				if(indicadorParcelamentoCobrancaBancaria != null && indicadorParcelamentoCobrancaBancaria.equals(1)){
					exibirParcelamentoCobrancaBancaria = "S";

					// [SB0015] � Tratar Op��o de Parcelamento de Cobran�a Banc�ria.
					if(httpServletRequest.getParameter("tratarOpcaoParcelamento") != null
									&& !httpServletRequest.getParameter("tratarOpcaoParcelamento").equals("")){
						String tratarOpcaoParcelamento = httpServletRequest.getParameter("tratarOpcaoParcelamento");

						this.tratarOpcaoParcelamentoCobrancaBancaria(tratarOpcaoParcelamento, efetuarParcelamentoDebitosActionForm,
										usuario, Util.converterStringParaInteger(codigoImovel), fachada, sessao, httpServletRequest);

						if(tratarOpcaoParcelamento.equals("1")){
							Collection colecaoBoletoHelper = (Collection) sessao.getAttribute("colecaoBoletoHelper");
							if(colecaoBoletoHelper == null || colecaoBoletoHelper.isEmpty()){
								ActionServletException actionServletException = new ActionServletException(
												"atencao.nao_ha_boleto_negociacao", codigoImovel);
								actionServletException
												.setUrlBotaoVoltar("exibirEfetuarParcelamentoDebitosAction.do?tratarOpcaoParcelamento=2&confirmado=ok");

								throw actionServletException;
							}

						}

					}else{
						if((httpServletRequest.getParameter("tratarOpcaoMeioCobrancaParcelamento") == null || httpServletRequest
										.getParameter("tratarOpcaoMeioCobrancaParcelamento").equals(""))
										&& (httpServletRequest.getParameter("idBoletoNegociacao") == null || httpServletRequest
														.getParameter("idBoletoNegociacao").equals(""))){

							this.tratarOpcaoParcelamentoCobrancaBancaria("2", efetuarParcelamentoDebitosActionForm, usuario, Util
											.converterStringParaInteger(codigoImovel), fachada, sessao, httpServletRequest);
						}
					}
				}else{
					exibirParcelamentoCobrancaBancaria = "N";

					Integer indicadorParcelamentoCobrancaGuia = null;
					try{
						indicadorParcelamentoCobrancaGuia = Util.obterInteger((String) ParametroCobranca.P_COBRANCA_PARCELAMENTO_GUIA
										.executar(ExecutorParametrosCobranca.getInstancia()));
					}catch(ControladorException e){
						e.printStackTrace();
					}

					// 2.5.3.1. Caso seja poss�vel a cobran�a do parcelamento por meio de guia de
					// pagamento.
					if(indicadorParcelamentoCobrancaGuia != null && indicadorParcelamentoCobrancaGuia.equals(1)){
						if(efetuarParcelamentoDebitosActionForm.get("indicadorCobrancaParcelamento") == null
										|| efetuarParcelamentoDebitosActionForm.get("indicadorCobrancaParcelamento").equals("")){
							efetuarParcelamentoDebitosActionForm.set("indicadorCobrancaParcelamento", "1");
						}
						// [SB0018 � Tratar Op��o do Meio de Cobran�a do Parcelamento]
						this.tratarOpcaoMeioDeCobrancaParcelamento(efetuarParcelamentoDebitosActionForm, usuario, Util
										.converterStringParaInteger(codigoImovel), fachada, sessao, httpServletRequest);
					}else{
						// // [SB0021 � Determinar Parametriza��o do Parcelamento Padr�o].
						this.determinarParametrizacaoParcelamentoPadrao(efetuarParcelamentoDebitosActionForm, sessao, httpServletRequest);
					}
				}

				httpServletRequest.setAttribute("exibirParcelamentoCobrancaBancaria", exibirParcelamentoCobrancaBancaria);
				sessao.setAttribute("exibirParcelamentoCobrancaBancaria", exibirParcelamentoCobrancaBancaria);

				efetuarParcelamentoDebitosActionForm.set("valorAtualizacaoMonetariaImovel", Util
								.formatarMoedaReal(valorAtualizacaoMonetaria));
				efetuarParcelamentoDebitosActionForm.set("valorJurosMoraImovel", Util.formatarMoedaReal(valorJurosMora));
				efetuarParcelamentoDebitosActionForm.set("valorMultaImovel", Util.formatarMoedaReal(valorMulta));
				efetuarParcelamentoDebitosActionForm.set("matriculaImovel", codigoImovel);

				if(httpServletRequest.getParameter("idBoletoNegociacao") != null
								&& !httpServletRequest.getParameter("idBoletoNegociacao").equals("")){
					Integer idBoletoNegociacao = Util.converterStringParaInteger(httpServletRequest.getParameter("idBoletoNegociacao"));

					sessao.setAttribute("idBoletoBancario", idBoletoNegociacao);

					// [SB0017] � Determinar Parametriza��o do Parcelamento de Cobran�a Banc�ria.
					this.determinarParametrizacaoParcelamentoCobrancaBancaria(idBoletoNegociacao, efetuarParcelamentoDebitosActionForm,
									sessao, httpServletRequest, fachada);
				}else{
					sessao.removeAttribute("idBoletoBancario");
				}

				if(httpServletRequest.getParameter("tratarOpcaoMeioCobrancaParcelamento") != null
								&& !httpServletRequest.getParameter("tratarOpcaoMeioCobrancaParcelamento").equals("")){
					// [SB0018 � Tratar Op��o do Meio de Cobran�a do Parcelamento]
					this.tratarOpcaoMeioDeCobrancaParcelamento(efetuarParcelamentoDebitosActionForm, usuario, Util
									.converterStringParaInteger(codigoImovel), fachada, sessao, httpServletRequest);
				}
			}

			// Atualizando o c�digo do im�vel na var�avel hidden do formul�rio
			codigoImovelAntes = codigoImovel;
			efetuarParcelamentoDebitosActionForm.set("codigoImovelAntes", codigoImovelAntes);

			FiltroClienteImovel filtroClienteImovel = new FiltroClienteImovel();

			filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.IMOVEL_ID, codigoImovel));
			filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.CLIENTE_RELACAO_TIPO_ID,
							ClienteRelacaoTipo.USUARIO));
			filtroClienteImovel.adicionarParametro(new ParametroNulo(FiltroClienteImovel.DATA_FIM_RELACAO));
			filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteImovel.CLIENTE);
			filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteImovel.CLIENTE_CLIENTE_TIPO);
			Collection<ClienteImovel> clientesImoveis = fachada.pesquisar(filtroClienteImovel, ClienteImovel.class.getName());

			if(clientesImoveis != null && !clientesImoveis.isEmpty()){

				Cliente cliente = clientesImoveis.iterator().next().getCliente();

				efetuarParcelamentoDebitosActionForm.set("idClienteParcelamento", "" + cliente.getId());
				efetuarParcelamentoDebitosActionForm.set("nomeClienteParcelamento", cliente.getNome());
				efetuarParcelamentoDebitosActionForm.set("foneClienteParcelamento", fachada.pesquisarClienteFonePrincipal(cliente.getId()));
				efetuarParcelamentoDebitosActionForm.set("cpfClienteParcelamentoDigitado", httpServletRequest
								.getParameter("cpfClienteParcelamentoDigitado"));

				String cpfCnpjCliente = "";
				String indicadorPessoaFisicaJuridicaStr = "";

				ClienteTipo clienteTipo = cliente.getClienteTipo();

				if(clienteTipo != null){
					Short indicadorPessoaFisicaJuridica = clienteTipo.getIndicadorPessoaFisicaJuridica();
					indicadorPessoaFisicaJuridicaStr = Short.toString(indicadorPessoaFisicaJuridica);

					if(ClienteTipo.INDICADOR_PESSOA_FISICA.equals(indicadorPessoaFisicaJuridica)){
						cpfCnpjCliente = cliente.getCpfFormatado();
					}else{
						cpfCnpjCliente = cliente.getCnpjFormatado();
					}
				}

				String indicadorPermitirCpfCnpjManual = null;
				try{
					indicadorPermitirCpfCnpjManual = ParametroCobranca.P_PERMITIR_CPFCNPJ_MANUAL_PARC_DEBITOS.executar();
				}catch(ControladorException e){
					e.printStackTrace();
				}

				if(indicadorPermitirCpfCnpjManual.equals("2") && (cliente.getCpf() == null && cliente.getCnpj() == null)){

					httpServletRequest.setAttribute("caminhoActionConclusao", "/gsan/filtrarClienteAction.do");
					httpServletRequest.setAttribute("nomeBotao1", "Sim");
					httpServletRequest.setAttribute("urlBotaoVoltar", "/gsan/exibirEfetuarParcelamentoDebitosAction.do");
					sessao.setAttribute("idClienteAtualizacaoCpfCnpj", cliente.getId().toString());

					return montarPaginaConfirmacao("atencao.parcelamento.cliente_sem_cpf_cnpj", httpServletRequest, actionMapping,
									new String[] {cliente.getId().toString()});

				}

				efetuarParcelamentoDebitosActionForm.set("indicadorPessoaFisicaJuridica", indicadorPessoaFisicaJuridicaStr);
				efetuarParcelamentoDebitosActionForm.set("cpfClienteParcelamento", cpfCnpjCliente);
				efetuarParcelamentoDebitosActionForm.set("cpfClienteParcelamentoDigitado", "");

				if(!Util.isVazioOuBranco(cpfCnpjCliente)){
					httpServletRequest.setAttribute("cpfCliente", "true");
				}

			}else{

				efetuarParcelamentoDebitosActionForm.set("nomeClienteParcelamento", "");

				if(cpfClienteParcelamento != null && !cpfClienteParcelamento.equals("")){
					efetuarParcelamentoDebitosActionForm.set("cpfClienteParcelamento", cpfClienteParcelamento);
					Cliente clienteParcelamento = fachada.obterIdENomeCliente(cpfClienteParcelamento);

					if(clienteParcelamento != null){
						httpServletRequest.setAttribute("cpfCliente", "true");
						efetuarParcelamentoDebitosActionForm.set("idClienteParcelamento", clienteParcelamento.getId().toString());
						efetuarParcelamentoDebitosActionForm.set("nomeClienteParcelamento", clienteParcelamento.getNome());
						efetuarParcelamentoDebitosActionForm.set("foneClienteParcelamento", fachada
										.pesquisarClienteFonePrincipal(clienteParcelamento.getId()));
						efetuarParcelamentoDebitosActionForm.set("cpfClienteParcelamentoDigitado", httpServletRequest
										.getParameter("cpfClienteParcelamentoDigitado"));
					}else{
						efetuarParcelamentoDebitosActionForm.set("cpfClienteParcelamento", "");
						efetuarParcelamentoDebitosActionForm.set("cpfClienteParcelamentoDigitado", "");
						httpServletRequest.setAttribute("cpfInexistente", "CPF INEXISTENTE");
					}
				}
			}

		}else if(inscricaoImovel != null){
			httpServletRequest.setAttribute("cpfCliente", "true");
		}

		boolean permissaoRevisaoImpedimento = fachada.verificarPermissaoEspecial(
						PermissaoEspecial.PARCELAR_CONTAS_EM_REVISAO_COM_IMPEDIMENTO, usuario);

		FiltroMotivoRevisaoConta filtroMotivoRevisaoConta = new FiltroMotivoRevisaoConta(
						FiltroMotivoRevisaoConta.DESCRICAO_MOTIVO_REVISAO_CONTA);

		if((efetuarParcelamentoDebitosActionForm.get("indicadorParcelamentoCobrancaBancaria") != null && efetuarParcelamentoDebitosActionForm
						.get("indicadorParcelamentoCobrancaBancaria").equals("2"))
						|| (indicadorParcelamentoCobrancaBancaria != null && indicadorParcelamentoCobrancaBancaria.equals(2))){

			filtroMotivoRevisaoConta.adicionarParametro(new ParametroSimplesDiferenteDe(FiltroMotivoRevisaoConta.ID,
							ContaMotivoRevisao.REVISAO_POR_COBRANCA_BANCARIA));
		}

		/*
		 * Caso o indicador de conta em revis�o seja SIM e o usu�rio possuir permiss�o especial
		 * "PARCELAR CONTAS EM REVISAO COM IMPEDIMENTO", disponibilizar tamb�m os motivos de revis�o
		 * com inibi��o para parcelamento; caso contr�rio n�o apresentar esses motivos de revis�o na
		 * lista e/ou n�o selecionar os d�bitos com estes motivos
		 */
		if(!permissaoRevisaoImpedimento){
			filtroMotivoRevisaoConta.adicionarParametro(new ParametroSimples(
							FiltroMotivoRevisaoConta.INDICADOR_INIBE_PARCELAMENTO, ConstantesSistema.NAO));
		}

		Collection<ContaMotivoRevisao> colecaoMotivoRevisao = getFachada().pesquisar(filtroMotivoRevisaoConta,
						ContaMotivoRevisao.class.getName());

		sessao.setAttribute("colecaoMotivoRevisao", colecaoMotivoRevisao);

		// Coloca a data Atual na data de Parecelamento
		if(efetuarParcelamentoDebitosActionForm.get("dataParcelamento").equals("")
						|| efetuarParcelamentoDebitosActionForm.get("dataParcelamento") == null){

			SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
			Calendar dataCorrente = new GregorianCalendar();

			efetuarParcelamentoDebitosActionForm.set("dataParcelamento", "" + formatoData.format(dataCorrente.getTime()));
		}

		// [SB0022] � Selecionar Resolu��es de Diretoria.
		this.selecionarResolucoesDiretoria(efetuarParcelamentoDebitosActionForm, httpServletRequest, fachada, usuario, codigoImovel);

		// ---------------------------------------------------------------------------------------

		if(colecaoContasImovel != null && !colecaoContasImovel.isEmpty() && debitoTotalAtualizado.compareTo(BigDecimal.ZERO) != 0){
			String idResolucaoDiretoria = (String) efetuarParcelamentoDebitosActionForm.get("resolucaoDiretoria");
			if(!Util.isVazioOuBranco(idResolucaoDiretoria)){

				ResolucaoDiretoria resolucaoDiretoria = EfetuarParcelamentoDebitosWizardAction.obterResolucaoDiretoria(fachada,
								efetuarParcelamentoDebitosActionForm);

				EfetuarParcelamentoDebitosWizardAction.verificarRDComRestricao(resolucaoDiretoria, codigoImovel,
								(String) efetuarParcelamentoDebitosActionForm.get("inicioIntervaloParcelamentoOriginal"),
								(String) efetuarParcelamentoDebitosActionForm.get("fimIntervaloParcelamentoOriginal"), fachada, sessao,
								efetuarParcelamentoDebitosActionForm);

				ParcelamentoPerfil parcelamentoPerfil = EfetuarParcelamentoDebitosWizardAction.obterPerfilParcelamentoImovel(
								resolucaoDiretoria, codigoImovel, fachada, sessao, efetuarParcelamentoDebitosActionForm);

				httpServletRequest.setAttribute("parcelamentoPerfil", parcelamentoPerfil);

				boolean temPermissaoAcrescimoImpontualidade = false;
				if(parcelamentoPerfil.getIndicadorCobrarAcrescimosPorImpontualidades() != null
								&& parcelamentoPerfil.getIndicadorCobrarAcrescimosPorImpontualidades().equals(ConstantesSistema.SIM)){

					if(fachada.verificarPermissaoEspecial(PermissaoEspecial.PARCELAR_SEM_INCLUIR_ACRESCIMOS_POR_IMPONTUALIDADE, usuario)){
						temPermissaoAcrescimoImpontualidade = true;
						efetuarParcelamentoDebitosActionForm.set("indicadorAcrescimosImpotualidade", ConstantesSistema.SIM.toString());
					}else{
						temPermissaoAcrescimoImpontualidade = false;
						efetuarParcelamentoDebitosActionForm.set("indicadorAcrescimosImpotualidade", ConstantesSistema.SIM.toString());
					}
				}else{
					temPermissaoAcrescimoImpontualidade = false;
					efetuarParcelamentoDebitosActionForm.set("indicadorAcrescimosImpotualidade", ConstantesSistema.NAO.toString());
				}
				httpServletRequest.setAttribute("temPermissaoAcrescimoImpontualidade", temPermissaoAcrescimoImpontualidade);

			}
		}

		// ---------------------------------------------------------------------------------------

		return retorno;
	}

	/**
	 * Verifica as permiss�es especiais para a primeira p�gina de Efetuar
	 * Parcelamento D�bitos
	 * 
	 * @author Rodrigo Silveira
	 * @date 07/11/2006
	 * @param httpServletRequest
	 * @param fachada
	 * @param usuario
	 */
	private void verificarPermissoes(HttpServletRequest httpServletRequest, Fachada fachada, Usuario usuario){


		boolean temPermissaoResolucaoDiretoria = fachada.verificarPermissaoEspecial(PermissaoEspecial.USAR_PLANO_PAI_PARA_ORGAO_PUBLICO,
						usuario);

		httpServletRequest.setAttribute("temPermissaoResolucaoDiretoria", temPermissaoResolucaoDiretoria);
	}

	/**
	 * Pesquisa um imovel informado e prepara os dados para exibi��o na tela
	 * 
	 * @author Rodrigo Avellar/Roberta Costa
	 * @created 11/02/2006
	 */
	private boolean pesquisarImovel(String idImovel, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpSession sessao,
					Usuario usuario){

		boolean existeImovel = true;

		Fachada fachada = Fachada.getInstancia();

		DynaActionForm efetuarParcelamentoDebitosActionForm = (DynaActionForm) actionForm;

		// Pesquisa o imovel na base
		FiltroImovel filtroImovel = new FiltroImovel();

		filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, idImovel));
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade("localidade");
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade("setorComercial");
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade("quadra");
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade("logradouroBairro.bairro.municipio.unidadeFederacao");
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade("logradouroCep.cep");
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade("logradouroCep.logradouro.logradouroTipo");
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade("logradouroCep.logradouro.logradouroTitulo");
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade("enderecoReferencia");
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade("ligacaoAguaSituacao");
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade("ligacaoEsgotoSituacao");
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade("imovelPerfil");
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade("cobrancaSituacao");
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade("areaConstruidaFaixa");

		filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.CONSUMO_TARIFA);

		/*
		 * alterado por pedro alexandre
		 * dia 27/11/2006
		 * altera��o realizada para acoplar o esquema de abrang�ncia
		 */
		Usuario usuarioLogado = (Usuario) sessao.getAttribute(Usuario.USUARIO_LOGADO);
		Collection<Imovel> imovelPesquisado = fachada.pesquisarImovelEfetuarParcelamento(filtroImovel, usuarioLogado);

		// Verificar exist�ncioa da matr�cula do im�vel
		if(imovelPesquisado == null || imovelPesquisado.isEmpty()){
			efetuarParcelamentoDebitosActionForm.set("matriculaImovel", "");
			efetuarParcelamentoDebitosActionForm.set("inscricaoImovel", "MATR�CULA INEXISTENTE");
			httpServletRequest.setAttribute("nomeCampo", "matriculaImovel");
			httpServletRequest.setAttribute("idImovelNaoEncontrado", "exception");
			existeImovel = false;
		}else{
			// Verifica se o Imovel est� Exclu�do
			Imovel imovel = imovelPesquisado.iterator().next();
			if(imovel.getIndicadorExclusao() == Imovel.IMOVEL_EXCLUIDO){

				efetuarParcelamentoDebitosActionForm.set("matriculaImovel", "");
				efetuarParcelamentoDebitosActionForm.set("inscricaoImovel", "MATR�CULA INEXISTENTE");
				httpServletRequest.setAttribute("nomeCampo", "matriculaImovel");
				httpServletRequest.setAttribute("idImovelNaoEncontrado", "exception");
				existeImovel = false;

			}else{
				httpServletRequest.setAttribute("nomeCampo", "resolucaoDiretoria");

				// Verifica se Usu�rio est� com d�bito em cobran�a administrativa
				FiltroImovelCobrancaSituacao filtroImovelCobrancaSituacao = new FiltroImovelCobrancaSituacao();

				filtroImovelCobrancaSituacao.adicionarCaminhoParaCarregamentoEntidade("cobrancaSituacao");
				filtroImovelCobrancaSituacao
								.adicionarParametro(new ParametroSimples(FiltroImovelCobrancaSituacao.IMOVEL_ID, imovel.getId()));

				Collection imovelCobrancaSituacaoEncontrada = fachada.pesquisar(filtroImovelCobrancaSituacao, ImovelCobrancaSituacao.class
								.getName());

				// Verifica se o Im�vel tem d�bito em cobran�a administrativa
				// if(imovelCobrancaSituacaoEncontrada != null &&
				// !imovelCobrancaSituacaoEncontrada.isEmpty()){
				//
				// if(((ImovelCobrancaSituacao) ((List)
				// imovelCobrancaSituacaoEncontrada).get(0)).getCobrancaSituacao() != null){
				//
				// if(((ImovelCobrancaSituacao) ((List)
				// imovelCobrancaSituacaoEncontrada).get(0)).getCobrancaSituacao().getId()
				// .equals(CobrancaSituacao.COBRANCA_ADMINISTRATIVA)
				// && ((ImovelCobrancaSituacao) ((List) imovelCobrancaSituacaoEncontrada).get(0))
				// .getDataRetiradaCobranca() == null){
				// throw new
				// ActionServletException("atencao.pesquisa.imovel.cobranca_administrativa");
				// }
				// }
				// }

				// Verifica situa��o liga��o de �gua e esgoto
				if((imovel.getLigacaoAguaSituacao() != null)
								&& ((imovel.getLigacaoAguaSituacao().getId() == LigacaoAguaSituacao.POTENCIAL) || (imovel
												.getLigacaoEsgotoSituacao().getId() == LigacaoAguaSituacao.FACTIVEL))
								&& (imovel.getLigacaoEsgotoSituacao().getId() != LigacaoEsgotoSituacao.LIGADO)){
					throw new ActionServletException("atencao.pesquisa.imovel.inativo");
				}

				Collection collIdsSitLigacaoAguaRestabelecimento;
				try{
					collIdsSitLigacaoAguaRestabelecimento = EfetuarParcelamentoDebitosWizardAction.getSitLigacaoAguaRestabelecimento();
					if(collIdsSitLigacaoAguaRestabelecimento != null && collIdsSitLigacaoAguaRestabelecimento.size() > 0){
						if(collIdsSitLigacaoAguaRestabelecimento.contains(imovel.getLigacaoAguaSituacao().getId())){
							sessao.setAttribute("parametrizacaoParcelamentoRestabelecimento", "S");
						}else{
							sessao.removeAttribute("parametrizacaoParcelamentoRestabelecimento");
						}
					}
				}catch(ControladorException e1){
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}


				// Pega a descri��o da situa��o de �gua
				if(imovel.getLigacaoAguaSituacao() != null){
					efetuarParcelamentoDebitosActionForm.set("situacaoAgua", imovel.getLigacaoAguaSituacao().getDescricao());
					efetuarParcelamentoDebitosActionForm.set("situacaoAguaId", "" + imovel.getLigacaoAguaSituacao().getId());
				}

				// Pega a descri��o da situa��o de esgoto
				if(imovel.getLigacaoEsgotoSituacao() != null){
					efetuarParcelamentoDebitosActionForm.set("situacaoEsgoto", imovel.getLigacaoEsgotoSituacao().getDescricao());
					efetuarParcelamentoDebitosActionForm.set("situacaoEsgotoId", "" + imovel.getLigacaoEsgotoSituacao().getId());
				}

				efetuarParcelamentoDebitosActionForm.set("perfilImovel", "" + imovel.getImovelPerfil().getId());

				efetuarParcelamentoDebitosActionForm.set("descricaoPerfilImovel", "" + imovel.getImovelPerfil().getDescricao());

				sessao.setAttribute("imovel", imovel);

				// Pega o endere�o do Imovel
				String enderecoFormatado = "";
				try{
					enderecoFormatado = fachada.pesquisarEnderecoFormatado(Integer.valueOf(idImovel));
					efetuarParcelamentoDebitosActionForm.set("endereco", enderecoFormatado);
				}catch(NumberFormatException e){
					// TODO Auto-generated catch block
					e.printStackTrace();
				}catch(ControladorException e){
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// Pega a inscri��o do Imovel
				efetuarParcelamentoDebitosActionForm.set("inscricaoImovel", imovel.getInscricaoFormatada());

				// Pega as Quantidades de Parcelamentos
				if(imovel.getNumeroParcelamento() != null){
					efetuarParcelamentoDebitosActionForm.set("numeroParcelamento", "" + imovel.getNumeroParcelamento());
				}else{
					efetuarParcelamentoDebitosActionForm.set("numeroParcelamento", "0");
				}

				// Pega as Quantidades de Reparcelamentos
				if(imovel.getNumeroReparcelamento() != null){
					efetuarParcelamentoDebitosActionForm.set("numeroReparcelamento", "" + imovel.getNumeroReparcelamento());
				}else{
					efetuarParcelamentoDebitosActionForm.set("numeroReparcelamento", "0");
				}

				// Pega as Quantidades de Reparcelamentos Consecutivos
				if(imovel.getNumeroReparcelamentoConsecutivos() != null){
					efetuarParcelamentoDebitosActionForm.set("numeroReparcelamentoConsecutivos", ""
									+ imovel.getNumeroReparcelamentoConsecutivos());
				}else{
					efetuarParcelamentoDebitosActionForm.set("numeroReparcelamentoConsecutivos", "0");
				}

				// Filtro para recuperar informa��o do cliente relacionado com o
				// im�vel
				FiltroClienteImovel filtroClienteImovel = new FiltroClienteImovel();

				filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.IMOVEL_ID, idImovel));
				filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.CLIENTE_RELACAO_TIPO,
								ClienteRelacaoTipo.USUARIO));
				filtroClienteImovel.adicionarParametro(new ParametroNulo(FiltroClienteImovel.DATA_FIM_RELACAO));
				filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("cliente.clienteTipo");

				Collection<ClienteImovel> clientesImovel = fachada.pesquisar(filtroClienteImovel, ClienteImovel.class.getName());

				if(Util.isVazioOrNulo(clientesImovel)){
					throw new ActionServletException("atencao.nao_existe_cliente_usuario_imovel", idImovel);
				}
				Cliente cliente = clientesImovel.iterator().next().getCliente();

				// Manda os dados do cliente para a p�gina
				if(cliente != null){
					sessao.setAttribute("idClienteImovel", cliente.getId());
					efetuarParcelamentoDebitosActionForm.set("nomeCliente", cliente.getNome());
					if(cliente.getCpf() != null && !cliente.getCpf().equals("")){
						efetuarParcelamentoDebitosActionForm.set("cpfCnpj", cliente.getCpfFormatado());
					}else if(cliente.getCnpj() != null && !cliente.getCnpj().equals("")){
						efetuarParcelamentoDebitosActionForm.set("cpfCnpj", cliente.getCnpjFormatado());
					}else if(httpServletRequest.getParameter("cpfClienteParcelamentoDigitado") != null
									&& !httpServletRequest.getParameter("cpfClienteParcelamentoDigitado").equals("")){
						efetuarParcelamentoDebitosActionForm.set("cpfCnpj", httpServletRequest
										.getParameter("cpfClienteParcelamentoDigitado"));
					}else{
						efetuarParcelamentoDebitosActionForm.set("cpfCnpj", "N�O INFORMADO");
					}
				}
			}
		}

		if(existeImovel){
			Imovel imovel = imovelPesquisado.iterator().next();

			if(imovel.getAreaConstruida() != null){
				efetuarParcelamentoDebitosActionForm.set("areaConstruidaImovel", imovel.getAreaConstruida().toString());
			}else if(imovel.getAreaConstruidaFaixa() != null && imovel.getAreaConstruidaFaixa().getVolumeMaiorFaixa() != null){
				efetuarParcelamentoDebitosActionForm
.set("areaConstruidaImovel", imovel.getAreaConstruidaFaixa().getVolumeMaiorFaixa()
								.toString());
			}

			// efetuar o parcelamento de um imovel com cobrancaSituacao
			fachada.verificarPermissaoEspecial(PermissaoEspecial.IMOVEL_EM_SITUACAO_COBRANCA, usuario, imovel);
		}

		return existeImovel;
	}

	/**
	 * [SB0015] � Tratar Op��o de Parcelamento de Cobran�a Banc�ria
	 * 
	 * @author Ailton Sousa
	 * @date 20/12/2011
	 * @param tratarOpcaoParcelamento
	 * @param form
	 * @param usuario
	 * @param idImovel
	 * @param fachada
	 * @param sessao
	 */
	private void tratarOpcaoParcelamentoCobrancaBancaria(String tratarOpcaoParcelamento, DynaActionForm form, Usuario usuario,
					Integer idImovel, Fachada fachada, HttpSession sessao, HttpServletRequest httpServletRequest){

		if(tratarOpcaoParcelamento.equals("1")){
			// [FS0027 � Verificar se usu�rio possui autoriza��o para parcelar contas em cobran�a
			// banc�ria].
			if(!getFachada().verificarPermissaoEspecial(PermissaoEspecial.PARCELAMENTO_COBRANCA_BANCARIA, usuario)){
				throw new ActionServletException("atencao.usuario_sem_permisao_parcelar_cobranca_bancaria");
			}

			String dataParcelamento = (String) form.get("dataParcelamento");

			// [FS0025 � Validar data do parcelamento de contas em cobran�a banc�ria].
			if(dataParcelamento != null && !dataParcelamento.equals("")){
				this.validarDataParcelamentoCobrancaBancaria(dataParcelamento, form);
			}

			// [SB0016] � Obter Boletos Banc�rios para Negocia��o
			Collection<BoletoBancarioHelper> colecaoBoletoHelper = this.obterBoletosBancariosParaNegociacao(idImovel, fachada);

			sessao.setAttribute("colecaoBoletoHelper", colecaoBoletoHelper);

			Integer idBoletoNegociacao = null;

			if(httpServletRequest.getParameter("idBoletoNegociacao") != null
							&& !httpServletRequest.getParameter("idBoletoNegociacao").equals("")){
				idBoletoNegociacao = Util.converterStringParaInteger(httpServletRequest.getParameter("idBoletoNegociacao"));
			}else{
				sessao.removeAttribute("idBoletoBancario");
			}

			this.determinarParametrizacaoParcelamentoCobrancaBancaria(idBoletoNegociacao, form, sessao, httpServletRequest, fachada);

		}else if(tratarOpcaoParcelamento.equals("2")){
			if(sessao.getAttribute("colecaoBoletoHelper") != null){
				sessao.removeAttribute("colecaoBoletoHelper");
				sessao.removeAttribute("idBoletoBancario");
			}

			form.set("indicadorParcelamentoCobrancaBancaria", "2");

			Integer cobrancaParcelamentoGuia = null;
			try{
				cobrancaParcelamentoGuia = Util.obterInteger((String) ParametroCobranca.P_COBRANCA_PARCELAMENTO_GUIA
								.executar(ExecutorParametrosCobranca.getInstancia()));
			}catch(ControladorException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// 2.2. Caso seja poss�vel a cobran�a do parcelamento por meio de guia de pagamento.
			if(cobrancaParcelamentoGuia != null && cobrancaParcelamentoGuia.equals(1)){
				sessao.removeAttribute("parametrizacaoParcelamentoCobrancaBancaria");
				
				if(Util.isVazioOuBranco(form.get("indicadorCobrancaParcelamento"))){
					form.set("indicadorCobrancaParcelamento", "1");
				}
				// [SB0018 � Tratar Op��o do Meio de Cobran�a do Parcelamento]
				this.tratarOpcaoMeioDeCobrancaParcelamento(form, usuario, idImovel, fachada, sessao, httpServletRequest);
			}else{
				sessao.removeAttribute("parametrizacaoParcelamentoCobrancaBancaria");
				sessao.removeAttribute("parametrizacaoParcelamentoGuia");
				sessao.removeAttribute("parametrizacaoParcelamentoDebitoACobrar");
				// [SB0021 � Determinar Parametriza��o do Parcelamento Padr�o].
				this.determinarParametrizacaoParcelamentoPadrao(form, sessao, httpServletRequest);
			}
		}
	}

	/**
	 * [FS0025 � Validar data do parcelamento de contas em cobran�a banc�ria]
	 * 
	 * @author Ailton Sousa
	 * @date 20/12/2011
	 * @param dataParcelamento
	 */
	private void validarDataParcelamentoCobrancaBancaria(String dataParcelamento, DynaActionForm form){

		// Caso o campo �Parcelamento de Cobran�a Banc�ria?� esteja com a op��o �Sim� selecionada
		// (1).
		if(form.get("indicadorParcelamentoCobrancaBancaria") != null && form.get("indicadorParcelamentoCobrancaBancaria").equals("1")){
			if(Util.compararData(Util.converteStringParaDate(dataParcelamento), new Date()) > 0){
				throw new ActionServletException("atencao.data_parcelamento_nao_pode_maior_hoje");
			}
			Integer qtdDias = null;
			try{
				qtdDias = Util.obterInteger((String) ParametroCobranca.P_QUANTIDADE_DIAS_PARCELAR_RETROATIVO
								.executar(ExecutorParametrosCobranca.getInstancia()));
			}catch(ControladorException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(qtdDias != null){
				Long dias = Util.diferencaDias(Util.converteStringParaDate(dataParcelamento), new Date());
				if(dias.intValue() > qtdDias.intValue()){
					throw new ActionServletException("atencao.data_parcelamento_nao_pode_menor_X_dias_atras", qtdDias.toString());
				}
			}
		}
	}

	/**
	 * [SB0016] � Obter Boletos Banc�rios para Negocia��o
	 * 
	 * @author Ailton Sousa
	 * @date 20/12/2011
	 * @param idImovel
	 * @param fachada
	 * @return
	 */
	private Collection<BoletoBancarioHelper> obterBoletosBancariosParaNegociacao(Integer idImovel, Fachada fachada){

		Collection<BoletoBancarioHelper> retorno = null;
		Collection<BoletoBancario> colecaoBoletoBancario = fachada.obterBoletosBancariosParaNegociacao(idImovel);

		if(colecaoBoletoBancario == null || colecaoBoletoBancario.isEmpty()){
			ActionServletException actionServletException = new ActionServletException("atencao.nao_ha_boleto_negociacao", idImovel
							.toString());
			actionServletException.setUrlBotaoVoltar("exibirEfetuarParcelamentoDebitosAction.do?tratarOpcaoParcelamento=2&confirmado=ok");

			throw actionServletException;

		}else{
			retorno = new ArrayList<BoletoBancarioHelper>();

			Iterator<BoletoBancario> itBoletoBancario = colecaoBoletoBancario.iterator();
			BoletoBancario boletoBancario = null;
			BoletoBancarioHelper boletoHelper = null;

			while(itBoletoBancario.hasNext()){

				boletoBancario = itBoletoBancario.next();

				boletoHelper = new BoletoBancarioHelper();

				boletoHelper.setId(boletoBancario.getId());
				boletoHelper.setArrecadador(boletoBancario.getArrecadador());
				boletoHelper.setConvenio(boletoBancario.getConvenio());
				boletoHelper.setNumeroSequencial(boletoBancario.getNumeroSequencial());
				boletoHelper.setImovel(boletoBancario.getImovel());
				boletoHelper.setDataInicialVencimento(boletoBancario.getDataVencimento());
				boletoHelper.setValorDebito(boletoBancario.getValorDebito());

				Date dataEntrada = fachada.obterDataEntradaBoletoBancarioSituacaoHistorico(boletoBancario.getId());

				if(dataEntrada != null){
					boletoHelper.setDataInicialEntrada(dataEntrada);
				}

				boletoHelper.setBoletoBancarioSituacao(boletoBancario.getBoletoBancarioSituacao());

				retorno.add(boletoHelper);
			}

		}

		return retorno;
	}

	/**
	 * [SB0017] � Determinar Parametriza��o do Parcelamento de Cobran�a Banc�ria.
	 * 
	 * @author Ailton Sousa
	 * @date 22/12/2011
	 * @param idBoletoNegociacao
	 * @param form
	 * @param sessao
	 * @param httpServletRequest
	 * @param fachada
	 */
	private void determinarParametrizacaoParcelamentoCobrancaBancaria(Integer idBoletoNegociacao, DynaActionForm form, HttpSession sessao,
					HttpServletRequest httpServletRequest, Fachada fachada){

		// Desabilitar o campo �Intervalo do parcelamento�.
		Short indicadorAlteracaoPeriodoParcelamento = ConstantesSistema.NAO;
		sessao.setAttribute("indicadorAlteracaoPeriodoParcelamento", indicadorAlteracaoPeriodoParcelamento);

		if(sessao.getAttribute("colecaoBoletoHelper") != null){
			if(idBoletoNegociacao != null){
				Integer menorReferencia = fachada.obterMenorReferenciaContaBoletoBancario(idBoletoNegociacao);
				Integer maiorReferencia = fachada.obterMaiorReferenciaContaBoletoBancario(idBoletoNegociacao);

				if(menorReferencia != null){
					form.set("inicioIntervaloParcelamento", Util.formatarAnoMesParaMesAno(menorReferencia));
				}
				if(maiorReferencia != null){
					form.set("fimIntervaloParcelamento", Util.formatarAnoMesParaMesAno(maiorReferencia));
				}

				form.set("idBoletoNegociacao", idBoletoNegociacao.toString());
			}

			String[] idsMotivoRevisao = new String[1];
			idsMotivoRevisao[0] = ContaMotivoRevisao.REVISAO_POR_COBRANCA_BANCARIA.toString();

			sessao.setAttribute("parametrizacaoParcelamentoCobrancaBancaria", "S");

			form.set("indicadorCobrancaParcelamento", "2");
			form.set("indicadorContasRevisao", "1");
			form.set("idMotivoRevisao", idsMotivoRevisao);
			form.set("indicadorDebitosACobrar", ConstantesSistema.NAO.toString());
			form.set("indicadorCreditoARealizar", ConstantesSistema.NAO.toString());
			form.set("indicadorGuiasPagamento", "2");
		}else{
			sessao.removeAttribute("idBoletoBancario");
		}
	}

	/**
	 * [SB0018] � Tratar Op��o do Meio de Cobran�a do Parcelamento
	 * 
	 * @author Ailton Sousa
	 * @date 22/12/2011
	 * @param form
	 * @param usuario
	 * @param idImovel
	 * @param fachada
	 * @param sessao
	 */
	private void tratarOpcaoMeioDeCobrancaParcelamento(DynaActionForm form, Usuario usuario, Integer idImovel, Fachada fachada,
					HttpSession sessao, HttpServletRequest httpServletRequest){

		Integer indicadorCobrancaParcelamento = null;

		if(form.get("indicadorCobrancaParcelamento") != null && !form.get("indicadorCobrancaParcelamento").equals("")){
			indicadorCobrancaParcelamento = Util.converterStringParaInteger((String) form.get("indicadorCobrancaParcelamento"));
		}

		// D�bito a Cobrar
		if(indicadorCobrancaParcelamento != null && indicadorCobrancaParcelamento.equals(1)){
			sessao.removeAttribute("parametrizacaoParcelamentoGuia");
			// [SB0020] � Determinar Parametriza��o do Parcelamento Cobrado por D�bito A Cobrar
			this.determinarParametrizacaoParcelamentoDebitoACobrar(form, sessao, httpServletRequest);
		}// Guia de Pagamento
		else{
			sessao.removeAttribute("parametrizacaoParcelamentoDebitoACobrar");
			// [SB0019] � Determinar Parametriza��o do Parcelamento Cobrado por Guia
			this.determinarParametrizacaoParcelamentoGuia(form, sessao, httpServletRequest);
		}
	}

	/**
	 * [SB0019] � Determinar Parametriza��o do Parcelamento Cobrado por Guia
	 * 
	 * @author Ailton Sousa
	 * @date 22/12/2011
	 * @param form
	 * @param sessao
	 */
	private void determinarParametrizacaoParcelamentoGuia(DynaActionForm form, HttpSession sessao, HttpServletRequest httpServletRequest){

		sessao.setAttribute("parametrizacaoParcelamentoGuia", "S");

		// Dados do D�bito do Im�vel - Contas
		if(sessao.getAttribute("colecaoContaValoresImovel") != null){
			Collection<ContaValoresHelper> colecaoContasImovel = (Collection<ContaValoresHelper>) sessao
							.getAttribute("colecaoContaValoresImovel");

			Iterator contaValores = colecaoContasImovel.iterator();
			Integer menorReferencia = null;
			Integer maiorReferencia = null;

			while(contaValores.hasNext()){
				Conta conta = ((ContaValoresHelper) contaValores.next()).getConta();

				if(menorReferencia == null){
					menorReferencia = conta.getReferencia();
				}else{
					if(menorReferencia.intValue() > conta.getReferencia()){
						menorReferencia = conta.getReferencia();
					}
				}

				if(maiorReferencia == null){
					maiorReferencia = conta.getReferencia();
				}else{
					if(maiorReferencia.intValue() < conta.getReferencia()){
						maiorReferencia = conta.getReferencia();
					}
				}
			}

			if(sessao.getAttribute("colecaoDebitoACobrarImovel") != null){
				Collection<DebitoACobrar> colecaoDebitoACobrarImovel = (Collection<DebitoACobrar>) sessao
								.getAttribute("colecaoDebitoACobrarImovel");

				if(!Util.isVazioOrNulo(colecaoDebitoACobrarImovel)){
					for(DebitoACobrar debitoACobrar : colecaoDebitoACobrarImovel){
						if(menorReferencia == null){
							menorReferencia = debitoACobrar.getAnoMesReferenciaDebito();
						}else{
							if(menorReferencia.intValue() > debitoACobrar.getAnoMesReferenciaDebito()){
								menorReferencia = debitoACobrar.getAnoMesReferenciaDebito();
							}
						}

						if(maiorReferencia == null){
							maiorReferencia = debitoACobrar.getAnoMesReferenciaDebito();
						}else{
							if(maiorReferencia.intValue() < debitoACobrar.getAnoMesReferenciaDebito()){
								maiorReferencia = debitoACobrar.getAnoMesReferenciaDebito();
							}
						}
					}
				}
			}

			if(sessao.getAttribute("colecaoGuiaPagamentoValoresImovel") != null){
				Collection<GuiaPagamentoValoresHelper> colecaoGuiPagamentoImovel = (Collection<GuiaPagamentoValoresHelper>) sessao
								.getAttribute("colecaoGuiaPagamentoValoresImovel");

				if(!Util.isVazioOrNulo(colecaoGuiPagamentoImovel)){
					for(GuiaPagamentoValoresHelper guiaPagamentoValoresHelper : colecaoGuiPagamentoImovel){

						GuiaPagamentoPrestacao guiaPagamentoPrestacao = (GuiaPagamentoPrestacao) Util
										.retonarObjetoDeColecao(guiaPagamentoValoresHelper
										.getGuiaPagamentoPrestacoes());

						if(menorReferencia == null){
							menorReferencia = guiaPagamentoPrestacao.getAnoMesReferenciaFaturamento();
						}else{
							if(menorReferencia.intValue() > guiaPagamentoPrestacao.getAnoMesReferenciaFaturamento()){
								menorReferencia = guiaPagamentoPrestacao.getAnoMesReferenciaFaturamento();
							}
						}

						if(maiorReferencia == null){
							maiorReferencia = guiaPagamentoPrestacao.getAnoMesReferenciaFaturamento();
						}else{
							if(maiorReferencia.intValue() < guiaPagamentoPrestacao.getAnoMesReferenciaFaturamento()){
								maiorReferencia = guiaPagamentoPrestacao.getAnoMesReferenciaFaturamento();
							}
						}
					}
				}
			}

			if(menorReferencia != null){
				form.set("inicioIntervaloParcelamento", Util.formatarAnoMesParaMesAno(menorReferencia));
			}
			if(maiorReferencia != null){
				form.set("fimIntervaloParcelamento", Util.formatarAnoMesParaMesAno(maiorReferencia));
			}
		}

		form.set("indicadorContasRevisao", "2");
		form.set("indicadorGuiasPagamento", "1");

		this.prepararIndicadorDebitoACobrar(form, sessao, httpServletRequest);
		this.prepararIndicadorCreditoARealizar(form, sessao, httpServletRequest);
	}

	/**
	 * [SB0020] � Determinar Parametriza��o do Parcelamento Cobrado por D�bito A Cobrar
	 * 
	 * @author Ailton Sousa
	 * @date 22/12/2011
	 * @param form
	 * @param sessao
	 */
	private void determinarParametrizacaoParcelamentoDebitoACobrar(DynaActionForm form, HttpSession sessao,
					HttpServletRequest httpServletRequest){

		sessao.setAttribute("parametrizacaoParcelamentoDebitoACobrar", "S");

		// Dados do D�bito do Im�vel - Contas
		if(sessao.getAttribute("colecaoContaValoresImovel") != null){
			Collection<ContaValoresHelper> colecaoContasImovel = (Collection<ContaValoresHelper>) sessao
							.getAttribute("colecaoContaValoresImovel");

			Iterator contaValores = colecaoContasImovel.iterator();
			Integer menorReferencia = null;
			Integer maiorReferencia = null;

			while(contaValores.hasNext()){
				Conta conta = ((ContaValoresHelper) contaValores.next()).getConta();

				if(menorReferencia == null){
					menorReferencia = conta.getReferencia();
				}else{
					if(menorReferencia.intValue() > conta.getReferencia()){
						menorReferencia = conta.getReferencia();
					}
				}

				if(maiorReferencia == null){
					maiorReferencia = conta.getReferencia();
				}else{
					if(maiorReferencia.intValue() < conta.getReferencia()){
						maiorReferencia = conta.getReferencia();
					}
				}
			}

			if(sessao.getAttribute("colecaoDebitoACobrarImovel") != null){
				Collection<DebitoACobrar> colecaoDebitoACobrarImovel = (Collection<DebitoACobrar>) sessao
								.getAttribute("colecaoDebitoACobrarImovel");

				if(!Util.isVazioOrNulo(colecaoDebitoACobrarImovel)){
					for(DebitoACobrar debitoACobrar : colecaoDebitoACobrarImovel){
						if(menorReferencia == null){
							menorReferencia = debitoACobrar.getAnoMesReferenciaDebito();
						}else{
							if(menorReferencia.intValue() > debitoACobrar.getAnoMesReferenciaDebito()){
								menorReferencia = debitoACobrar.getAnoMesReferenciaDebito();
							}
						}

						if(maiorReferencia == null){
							maiorReferencia = debitoACobrar.getAnoMesReferenciaDebito();
						}else{
							if(maiorReferencia.intValue() < debitoACobrar.getAnoMesReferenciaDebito()){
								maiorReferencia = debitoACobrar.getAnoMesReferenciaDebito();
							}
						}
					}
				}
			}

			if(sessao.getAttribute("colecaoGuiaPagamentoValoresImovel") != null){
				Collection<GuiaPagamentoValoresHelper> colecaoGuiPagamentoImovel = (Collection<GuiaPagamentoValoresHelper>) sessao
								.getAttribute("colecaoGuiaPagamentoValoresImovel");

				if(!Util.isVazioOrNulo(colecaoGuiPagamentoImovel)){
					for(GuiaPagamentoValoresHelper guiaPagamentoValoresHelper : colecaoGuiPagamentoImovel){

						GuiaPagamentoPrestacao guiaPagamentoPrestacao = (GuiaPagamentoPrestacao) Util
										.retonarObjetoDeColecao(guiaPagamentoValoresHelper
										.getGuiaPagamentoPrestacoes());

						if(menorReferencia == null){
							menorReferencia = guiaPagamentoPrestacao.getAnoMesReferenciaFaturamento();
						}else{
							if(menorReferencia.intValue() > guiaPagamentoPrestacao.getAnoMesReferenciaFaturamento()){
								menorReferencia = guiaPagamentoPrestacao.getAnoMesReferenciaFaturamento();
							}
						}

						if(maiorReferencia == null){
							maiorReferencia = guiaPagamentoPrestacao.getAnoMesReferenciaFaturamento();
						}else{
							if(maiorReferencia.intValue() < guiaPagamentoPrestacao.getAnoMesReferenciaFaturamento()){
								maiorReferencia = guiaPagamentoPrestacao.getAnoMesReferenciaFaturamento();
							}
						}
					}
				}
			}

			if(menorReferencia != null){
				form.set("inicioIntervaloParcelamento", Util.formatarAnoMesParaMesAno(menorReferencia));
			}

			if(maiorReferencia != null){
				form.set("fimIntervaloParcelamento", Util.formatarAnoMesParaMesAno(maiorReferencia));
			}

			if(menorReferencia != null){
				form.set("inicioIntervaloParcelamento", Util.formatarAnoMesParaMesAno(menorReferencia));
			}
			if(maiorReferencia != null){
				form.set("fimIntervaloParcelamento", Util.formatarAnoMesParaMesAno(maiorReferencia));
			}
		}

		form.set("indicadorContasRevisao", "2");
		form.set("indicadorGuiasPagamento", "1");

		this.prepararIndicadorDebitoACobrar(form, sessao, httpServletRequest);
		this.prepararIndicadorCreditoARealizar(form, sessao, httpServletRequest);
	}

	/**
	 * [SB0021] � Determinar Parametriza��o do Parcelamento Padr�o
	 * 
	 * @author Ailton Sousa
	 * @date 22/12/2011
	 * @param form
	 * @param sessao
	 */
	private void determinarParametrizacaoParcelamentoPadrao(DynaActionForm form, HttpSession sessao, HttpServletRequest httpServletRequest){

		sessao.setAttribute("parametrizacaoParcelamentoPadrao", "S");

		form.set("indicadorCobrancaParcelamento", "1");

		Integer menorReferencia = null;
		Integer maiorReferencia = null;

		// Dados do D�bito do Im�vel - Contas
		if(sessao.getAttribute("colecaoContaValoresImovel") != null){
			Collection<ContaValoresHelper> colecaoContasImovel = (Collection<ContaValoresHelper>) sessao
							.getAttribute("colecaoContaValoresImovel");

			Iterator contaValores = colecaoContasImovel.iterator();

			while(contaValores.hasNext()){
				Conta conta = ((ContaValoresHelper) contaValores.next()).getConta();

				if(menorReferencia == null){
					menorReferencia = conta.getReferencia();
				}else{
					if(menorReferencia.intValue() > conta.getReferencia()){
						menorReferencia = conta.getReferencia();
					}
				}

				if(maiorReferencia == null){
					maiorReferencia = conta.getReferencia();
				}else{
					if(maiorReferencia.intValue() < conta.getReferencia()){
						maiorReferencia = conta.getReferencia();
					}
				}
			}
		}

		if(sessao.getAttribute("colecaoDebitoACobrarImovel") != null){
			Collection<DebitoACobrar> colecaoDebitoACobrarImovel = (Collection<DebitoACobrar>) sessao
							.getAttribute("colecaoDebitoACobrarImovel");

			if(!Util.isVazioOrNulo(colecaoDebitoACobrarImovel)){
				for(DebitoACobrar debitoACobrar : colecaoDebitoACobrarImovel){
					if(menorReferencia == null){
						menorReferencia = debitoACobrar.getAnoMesReferenciaDebito();
					}else{
						if(menorReferencia.intValue() > debitoACobrar.getAnoMesReferenciaDebito()){
							menorReferencia = debitoACobrar.getAnoMesReferenciaDebito();
						}
					}

					if(maiorReferencia == null){
						maiorReferencia = debitoACobrar.getAnoMesReferenciaDebito();
					}else{
						if(maiorReferencia.intValue() < debitoACobrar.getAnoMesReferenciaDebito()){
							maiorReferencia = debitoACobrar.getAnoMesReferenciaDebito();
						}
					}
				}
			}
		}

		if(sessao.getAttribute("colecaoGuiaPagamentoValoresImovel") != null){
			Collection<GuiaPagamentoValoresHelper> colecaoGuiPagamentoImovel = (Collection<GuiaPagamentoValoresHelper>) sessao
							.getAttribute("colecaoGuiaPagamentoValoresImovel");

			if(!Util.isVazioOrNulo(colecaoGuiPagamentoImovel)){
				for(GuiaPagamentoValoresHelper guiaPagamentoValoresHelper : colecaoGuiPagamentoImovel){

					GuiaPagamentoPrestacao guiaPagamentoPrestacao = (GuiaPagamentoPrestacao) Util
									.retonarObjetoDeColecao(guiaPagamentoValoresHelper
									.getGuiaPagamentoPrestacoes());

					if(menorReferencia == null){
						menorReferencia = guiaPagamentoPrestacao.getAnoMesReferenciaFaturamento();
					}else{
						if(menorReferencia.intValue() > guiaPagamentoPrestacao.getAnoMesReferenciaFaturamento()){
							menorReferencia = guiaPagamentoPrestacao.getAnoMesReferenciaFaturamento();
						}
					}

					if(maiorReferencia == null){
						maiorReferencia = guiaPagamentoPrestacao.getAnoMesReferenciaFaturamento();
					}else{
						if(maiorReferencia.intValue() < guiaPagamentoPrestacao.getAnoMesReferenciaFaturamento()){
							maiorReferencia = guiaPagamentoPrestacao.getAnoMesReferenciaFaturamento();
						}
					}
				}
			}
		}

		if(menorReferencia != null){
			form.set("inicioIntervaloParcelamento", Util.formatarAnoMesParaMesAno(menorReferencia));
		}

		if(maiorReferencia != null){
			form.set("fimIntervaloParcelamento", Util.formatarAnoMesParaMesAno(maiorReferencia));
		}

		form.set("indicadorContasRevisao", "2");
		form.set("indicadorGuiasPagamento", "1");

		this.prepararIndicadorDebitoACobrar(form, sessao, httpServletRequest);
		this.prepararIndicadorCreditoARealizar(form, sessao, httpServletRequest);
	}

	/**
	 * [SB0022] � Selecionar Resolu��es de Diretoria
	 * 
	 * @author Ailton Sousa
	 * @date 27/12/2011
	 * @param form
	 * @param httpServletRequest
	 * @param fachada
	 * @return Collection<ResolucaoDiretoria>
	 */
	private Collection<ResolucaoDiretoria> selecionarResolucoesDiretoria(DynaActionForm form, HttpServletRequest httpServletRequest,
					Fachada fachada, Usuario usuario, String idImovel){

		Collection<ResolucaoDiretoria> colecaoResolucaoDiretoria = new ArrayList<ResolucaoDiretoria>();

		// [FS0016] - Verificar se o usu�rio possui autoriza��o para utilizar a RD
		if((Boolean) httpServletRequest.getAttribute("temPermissaoResolucaoDiretoria")){

			colecaoResolucaoDiretoria = fachada.pesquisarResolucaoDiretoriaDataVigenciaFimMaiorIgualDataAtual();
		}else{

			// colecaoResolucaoDiretoria =
			// fachada.pesquisarResolucaoDiretoriaMaiorDataVigenciaInicio(null);

			// RD's filtrando pela grupo do usu�rio e com o o indicador de livre
			Integer idUsuarioLogado = usuario.getId();
			Collection<Grupo> colecaoGrupoUsuario = fachada.pesquisarGruposUsuario(idUsuarioLogado);

			Collection<Integer> idsGrupoUsuario = new ArrayList();

			if(!Util.isVazioOrNulo(colecaoGrupoUsuario)){
				Integer idGrupo = null;

				for(Grupo grupo : colecaoGrupoUsuario){
					idGrupo = grupo.getId();
					idsGrupoUsuario.add(idGrupo);
				}
			}
			colecaoResolucaoDiretoria = fachada.pesquisarResolucaoDiretoriaMaiorDataVigenciaInicio(idsGrupoUsuario);

			Collection<ResolucaoDiretoria> colecaoResolucaoDiretoriaAux = new ArrayList<ResolucaoDiretoria>();
			colecaoResolucaoDiretoriaAux = fachada.pesquisarResolucaoDiretoriaPermitidaAoUsuario(usuario.getId());

			if(colecaoResolucaoDiretoriaAux != null && !colecaoResolucaoDiretoriaAux.isEmpty()){
				colecaoResolucaoDiretoria.addAll(colecaoResolucaoDiretoriaAux);
			}
		}

		// Verifica se existe Resolu��o de Diretoria
		if(colecaoResolucaoDiretoria == null || colecaoResolucaoDiretoria.isEmpty()){
			throw new ActionServletException("atencao.resolucao_diretoria.inexistente");
		}else{
			// 1.2. Caso o campo �Cobran�a do Parcelamento� esteja com a op��o �D�bito A Cobrar�
			// selecionada, o sistema retorna toda a lista de RD�s para o passo que chamou este
			// subfluxo.
			if(form.get("indicadorCobrancaParcelamento") != null && form.get("indicadorCobrancaParcelamento").equals("1")){
				httpServletRequest.setAttribute("colecaoResolucaoDiretoria", colecaoResolucaoDiretoria);
			}else if(form.get("indicadorCobrancaParcelamento") != null && form.get("indicadorCobrancaParcelamento").equals("2")){
				if(idImovel != null && !idImovel.trim().equals("")){
					FiltroImovel filtroImovel = new FiltroImovel();
					filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, Util.converterStringParaInteger(idImovel)));
					filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.LIGACAO_AGUA_SITUACAO);
					filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.LIGACAO_ESGOTO_SITUACAO);
					filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.IMOVEL_PERFIL);

					Collection<Imovel> colecaoImovel = fachada.pesquisar(filtroImovel, Imovel.class.getName());

					Imovel imovel = null;
					if(colecaoImovel != null && !colecaoImovel.isEmpty()){
						imovel = colecaoImovel.iterator().next();

						// Condi��o 1.
						FiltroImovelSituacao filtroImovelSituacao = new FiltroImovelSituacao();
						filtroImovelSituacao.adicionarParametro(new ParametroSimples(FiltroImovelSituacao.LIGACAO_AGUA_SITUACAO_ID, imovel
										.getLigacaoAguaSituacao().getId()));
						filtroImovelSituacao.adicionarParametro(new ParametroSimples(FiltroImovelSituacao.LIGACAO_ESGOTO_SITUACAO_ID,
										imovel.getLigacaoEsgotoSituacao().getId()));
						filtroImovelSituacao.adicionarCaminhoParaCarregamentoEntidade("imovelSituacaoTipo");

						Collection<ImovelSituacao> colecaoImovelSituacao = fachada.pesquisar(filtroImovelSituacao, ImovelSituacao.class
										.getName());

						ImovelSituacao imovelSituacao = null;
						if(colecaoImovelSituacao != null && !colecaoImovelSituacao.isEmpty()){
							imovelSituacao = colecaoImovelSituacao.iterator().next();
						}else{
							filtroImovelSituacao.limparListaParametros();

							// Condi��o 2.
							filtroImovelSituacao.adicionarParametro(new ParametroSimples(FiltroImovelSituacao.LIGACAO_AGUA_SITUACAO_ID,
											imovel.getLigacaoAguaSituacao().getId()));
							filtroImovelSituacao.adicionarParametro(new ParametroNulo(FiltroImovelSituacao.LIGACAO_ESGOTO_SITUACAO_ID));
							filtroImovelSituacao.adicionarCaminhoParaCarregamentoEntidade("imovelSituacaoTipo");

							colecaoImovelSituacao = fachada.pesquisar(filtroImovelSituacao, ImovelSituacao.class.getName());
							if(colecaoImovelSituacao != null && !colecaoImovelSituacao.isEmpty()){
								imovelSituacao = colecaoImovelSituacao.iterator().next();
							}else{
								// [FS0004] � Verificar exist�ncia da situa��o do im�vel
								throw new ActionServletException("atencao.nao.existe.situacao.imovel.correspondente.situacao.agua.esgoto");
							}
						}

						if(imovelSituacao != null){

							ImovelSubcategoria imovelSubcategoria = fachada.pesquisarImovelSubcategoriaComMaiorQuantidadeEconomia(imovel
											.getId());

							Iterator<ResolucaoDiretoria> itRD = colecaoResolucaoDiretoria.iterator();
							ResolucaoDiretoria resolucaoDiretoria = null;
							Collection<ParcelamentoPerfil> colecaoParcelamentoPerfil = new ArrayList<ParcelamentoPerfil>();
							ParcelamentoPerfil parcelamentoPerfil = null;

							while(itRD.hasNext()){

								resolucaoDiretoria = itRD.next();

								FiltroParcelamentoPerfil filtroParcelamentoPerfil = new FiltroParcelamentoPerfil();

								// Condi��o 1
								if(imovelSubcategoria != null){
									filtroParcelamentoPerfil.adicionarParametro(new ParametroSimples(
													FiltroParcelamentoPerfil.IMOVEL_SITUACAO_TIPO_ID, imovelSituacao
																	.getImovelSituacaoTipo().getId()));
									filtroParcelamentoPerfil.adicionarParametro(new ParametroSimples(
													FiltroParcelamentoPerfil.RESOLUCAO_DIRETORIA_ID, resolucaoDiretoria.getId()));
									filtroParcelamentoPerfil.adicionarParametro(new ParametroSimples(
													FiltroParcelamentoPerfil.IMOVEL_PERFIL_ID, imovel.getImovelPerfil().getId()));
									filtroParcelamentoPerfil.adicionarParametro(new ParametroSimples(
													FiltroParcelamentoPerfil.SUBCATEGORIA_ID, imovelSubcategoria.getComp_id()
																	.getSubcategoria().getId()));

									colecaoParcelamentoPerfil = fachada.pesquisar(filtroParcelamentoPerfil, ParcelamentoPerfil.class
													.getName());
								}

								if(colecaoParcelamentoPerfil != null && !colecaoParcelamentoPerfil.isEmpty()){
									parcelamentoPerfil = colecaoParcelamentoPerfil.iterator().next();
								}else{
									// Condi��o 2.
									filtroParcelamentoPerfil.limparListaParametros();

									filtroParcelamentoPerfil.adicionarParametro(new ParametroSimples(
													FiltroParcelamentoPerfil.IMOVEL_SITUACAO_TIPO_ID, imovelSituacao
																	.getImovelSituacaoTipo().getId()));
									filtroParcelamentoPerfil.adicionarParametro(new ParametroSimples(
													FiltroParcelamentoPerfil.RESOLUCAO_DIRETORIA_ID, resolucaoDiretoria.getId()));
									filtroParcelamentoPerfil.adicionarParametro(new ParametroSimples(
													FiltroParcelamentoPerfil.IMOVEL_PERFIL_ID, imovel.getImovelPerfil().getId()));
									filtroParcelamentoPerfil
													.adicionarParametro(new ParametroNulo(FiltroParcelamentoPerfil.SUBCATEGORIA_ID));

									colecaoParcelamentoPerfil = fachada.pesquisar(filtroParcelamentoPerfil, ParcelamentoPerfil.class
													.getName());

									if(colecaoParcelamentoPerfil != null && !colecaoParcelamentoPerfil.isEmpty()){
										parcelamentoPerfil = colecaoParcelamentoPerfil.iterator().next();
									}else{
										// Condi��o 3.
										if(imovelSubcategoria != null){
											filtroParcelamentoPerfil.limparListaParametros();

											filtroParcelamentoPerfil.adicionarParametro(new ParametroSimples(
															FiltroParcelamentoPerfil.IMOVEL_SITUACAO_TIPO_ID, imovelSituacao
																			.getImovelSituacaoTipo().getId()));
											filtroParcelamentoPerfil.adicionarParametro(new ParametroSimples(
															FiltroParcelamentoPerfil.RESOLUCAO_DIRETORIA_ID, resolucaoDiretoria.getId()));
											filtroParcelamentoPerfil.adicionarParametro(new ParametroNulo(
															FiltroParcelamentoPerfil.IMOVEL_PERFIL_ID));
											filtroParcelamentoPerfil.adicionarParametro(new ParametroSimples(
															FiltroParcelamentoPerfil.SUBCATEGORIA_ID, imovelSubcategoria.getComp_id()
																			.getSubcategoria().getId()));

											colecaoParcelamentoPerfil = fachada.pesquisar(filtroParcelamentoPerfil,
															ParcelamentoPerfil.class.getName());
										}

										if(colecaoParcelamentoPerfil != null && !colecaoParcelamentoPerfil.isEmpty()){
											parcelamentoPerfil = colecaoParcelamentoPerfil.iterator().next();
										}else{
											// Condi��o 4.
											filtroParcelamentoPerfil.limparListaParametros();

											filtroParcelamentoPerfil.adicionarParametro(new ParametroSimples(
															FiltroParcelamentoPerfil.IMOVEL_SITUACAO_TIPO_ID, imovelSituacao
																			.getImovelSituacaoTipo().getId()));
											filtroParcelamentoPerfil.adicionarParametro(new ParametroSimples(
															FiltroParcelamentoPerfil.RESOLUCAO_DIRETORIA_ID, resolucaoDiretoria.getId()));
											filtroParcelamentoPerfil.adicionarParametro(new ParametroNulo(
															FiltroParcelamentoPerfil.IMOVEL_PERFIL_ID));
											filtroParcelamentoPerfil.adicionarParametro(new ParametroNulo(
															FiltroParcelamentoPerfil.SUBCATEGORIA_ID));

											colecaoParcelamentoPerfil = fachada.pesquisar(filtroParcelamentoPerfil,
															ParcelamentoPerfil.class.getName());

											if(colecaoParcelamentoPerfil != null && !colecaoParcelamentoPerfil.isEmpty()){
												parcelamentoPerfil = colecaoParcelamentoPerfil.iterator().next();
											}
										}
									}
								}// Fim das Condi��es

								if(parcelamentoPerfil != null){

									httpServletRequest.setAttribute("parcelamentoPerfil", parcelamentoPerfil);

									FiltroParcelamentoDescontoAntiguidade filtroParcelamentoDescontoAntiguidade = new FiltroParcelamentoDescontoAntiguidade();
									filtroParcelamentoDescontoAntiguidade.adicionarParametro(new ParametroSimples(
													FiltroParcelamentoDescontoAntiguidade.PARCELAMENTO_PERFIL, parcelamentoPerfil.getId()));

									Collection colecaoParcelamentoDescAntiguidade = fachada.pesquisar(
													filtroParcelamentoDescontoAntiguidade, ParcelamentoDescontoAntiguidade.class.getName());

									FiltroParcelamentoDescontoInatividade filtroParcelamentoDescontoInatividade = new FiltroParcelamentoDescontoInatividade();
									filtroParcelamentoDescontoInatividade.adicionarParametro(new ParametroSimples(
													FiltroParcelamentoDescontoInatividade.PARCELAMENTO_PERFIL, parcelamentoPerfil.getId()));

									Collection colecaoParcelamentoDescInatividade = fachada.pesquisar(
													filtroParcelamentoDescontoInatividade, ParcelamentoDescontoInatividade.class.getName());

									if(((parcelamentoPerfil.getPercentualDescontoAcrescimo() != null
													&& !parcelamentoPerfil.getPercentualDescontoAcrescimo().equals(BigDecimal.ZERO) && !parcelamentoPerfil
													.getPercentualDescontoAcrescimo().equals(new BigDecimal("0.00")))
													|| (parcelamentoPerfil.getPercentualDescontoSancao() != null
																	&& !parcelamentoPerfil.getPercentualDescontoSancao().equals(
																					BigDecimal.ZERO) && !parcelamentoPerfil
																	.getPercentualDescontoSancao().equals(new BigDecimal("0.00"))) || (parcelamentoPerfil
													.getPercentualDescontoAVista() != null
													&& !parcelamentoPerfil.getPercentualDescontoAVista().equals(BigDecimal.ZERO) && !parcelamentoPerfil
													.getPercentualDescontoAVista().equals(new BigDecimal("0.00"))))
													|| (colecaoParcelamentoDescAntiguidade != null && !colecaoParcelamentoDescAntiguidade
																	.isEmpty())
													|| (colecaoParcelamentoDescInatividade != null && !colecaoParcelamentoDescInatividade
																	.isEmpty())){
										itRD.remove();
									}
								}
								// else{
								// // [FS0005 � Verificar exist�ncia do perfil do parcelamento]
								// throw new ActionServletException(
								// "atencao.nao.existe.perfil.parcelamento.correspondente.situacao.imovel");
								// }
							}

							if(parcelamentoPerfil == null){
								// [FS0005 � Verificar exist�ncia do perfil do parcelamento]
								throw new ActionServletException("atencao.nao.existe.perfil.parcelamento.correspondente.situacao.imovel");
							}

						}
					}else{
						throw new ActionServletException("atencao.imovel.inexistente");
					}
				}else{
					throw new ActionServletException("atencao.imovel.nao_informado");
				}

				httpServletRequest.setAttribute("colecaoResolucaoDiretoria", colecaoResolucaoDiretoria);

			}else{
				httpServletRequest.setAttribute("colecaoResolucaoDiretoria", colecaoResolucaoDiretoria);
			}
		}

		return colecaoResolucaoDiretoria;
	}

	/**
	 * [SB0023] � Preparar Indicador de D�bito A Cobrar
	 * 
	 * @author Ailton Sousa
	 * @date 09/01/2012
	 * @param form
	 * @param sessao
	 * @param httpServletRequest
	 */
	private void prepararIndicadorDebitoACobrar(DynaActionForm form, HttpSession sessao, HttpServletRequest httpServletRequest){

		Fachada fachada = Fachada.getInstancia();

		boolean temPermissaoParcelarSemIncluirDebitoACobrar = fachada.verificarPermissaoEspecial(
						PermissaoEspecial.PARCELAR_SEM_INCLUIR_DEBITO_A_COBRAR, this.getUsuarioLogado(httpServletRequest));

		boolean temPermissaoParcelarDebitoACobrar = fachada.verificarPermissaoEspecial(PermissaoEspecial.PARCELAR_DEBITO_A_COBRAR,
						this.getUsuarioLogado(httpServletRequest));

		Short parametroConsiderarDebitoACobrarParcelamento = null;

		try{

			parametroConsiderarDebitoACobrarParcelamento = Util
							.obterShort((String) ParametroCobranca.P_CONSIDERAR_DEBITOS_A_COBRAR_PARCELAMENTO
							.executar(ExecutorParametrosCobranca.getInstancia()));
		}catch(ControladorException e){

			throw new ActionServletException(e.getMessage(), e.getParametroMensagem().toArray(new String[e.getParametroMensagem().size()]));
		}

		// Caso o padr�o para a empresa seja considerar d�bitos a cobrar na composi��o do d�bito a
		// ser parcelado.
		if(parametroConsiderarDebitoACobrarParcelamento.equals(ConstantesSistema.SIM)){

			// Exibir com a op��o �SIM� selecionada.
			form.set("indicadorDebitosACobrar", ConstantesSistema.SIM.toString());

			// Caso o usu�rio n�o possua permiss�o para efetuar o parcelamento sem considerar os
			// d�bitos a cobrar
			if(!temPermissaoParcelarSemIncluirDebitoACobrar){

				// N�o permitir que o usu�rio selecione a op��o "N�O".
				sessao.setAttribute("desabilitarDebitoACobrar", "S");
			}else{
				
				// Caso contr�rio, ou seja, caso o usu�rio possua permiss�o para efetuar o
				// parcelamento sem considerar os d�bitos a cobrar
				// permitir que o usu�rio selecione entre as op��es "SIM" e "N�O"
				sessao.removeAttribute("desabilitarDebitoACobrar");
			}
		}else{
			
			// Exibir com a op��o �N�O� selecionada.
			form.set("indicadorDebitosACobrar", ConstantesSistema.NAO.toString());

			// Caso o usu�rio n�o possua permiss�o para efetuar o parcelamento de d�bitos a cobrar
			if(!temPermissaoParcelarDebitoACobrar){

				// N�o permitir que o usu�rio selecione a op��o "SIM"
				sessao.setAttribute("desabilitarDebitoACobrar", "S");
			}else{

				// Caso contr�rio, ou seja, caso o usu�rio possua permiss�o para efetuar o
				// parcelamento de d�bitos a cobrar
				// permitir que o usu�rio selecione entre as op��es "SIM" e "N�O"
				sessao.removeAttribute("desabilitarDebitoACobrar");
			}
		}

	}

	/**
	 * [SB0023] � Preparar Indicador de Credito a Realizar
	 * 
	 * @author Yara Souza
	 * @date 21/11/2012
	 * @param form
	 * @param sessao
	 * @param httpServletRequest
	 */
	private void prepararIndicadorCreditoARealizar(DynaActionForm form, HttpSession sessao, HttpServletRequest httpServletRequest){

		Fachada fachada = Fachada.getInstancia();

		boolean temPermissaoParcelarSemIncluirCreditoARealizar = fachada.verificarPermissaoEspecial(
						PermissaoEspecial.PARCELAR_SEM_INCLUIR_CREDITO_A_REALIZAR, this.getUsuarioLogado(httpServletRequest));

		boolean temPermissaoParcelarCreditoARealizar = fachada.verificarPermissaoEspecial(PermissaoEspecial.PARCELAR_CREDITO_A_REALIZAR,
						this.getUsuarioLogado(httpServletRequest));

		Short parametroConsiderarCreaditoARealizarParcelamento = null;

		try{

			parametroConsiderarCreaditoARealizarParcelamento = Util
							.obterShort((String) ParametroCobranca.P_CONSIDERAR_CREDITO_A_REALIZAR_PARCELAMENTO
											.executar(ExecutorParametrosCobranca.getInstancia()));
		}catch(ControladorException e){

			throw new ActionServletException(e.getMessage(), e.getParametroMensagem().toArray(new String[e.getParametroMensagem().size()]));
		}

		// Caso o padr�o para a empresa seja considerar cr�ditos a realizar na composi��o do d�bito
		// a
		// ser parcelado.
		if(parametroConsiderarCreaditoARealizarParcelamento.equals(ConstantesSistema.SIM)){

			// Exibir com a op��o �SIM� selecionada.
			form.set("indicadorCreditoARealizar", ConstantesSistema.SIM.toString());

			// Caso o usu�rio n�o possua permiss�o para efetuar o parcelamento sem considerar os
			// cr�ditos a realizar
			if(!temPermissaoParcelarSemIncluirCreditoARealizar){

				// N�o permitir que o usu�rio selecione a op��o "N�O".
				sessao.setAttribute("desabilitarCreditoARealizar", "S");
			}else{

				// Caso contr�rio, ou seja, caso o usu�rio possua permiss�o para efetuar o
				// parcelamento sem considerar os cr�ditos a realizar
				// permitir que o usu�rio selecione entre as op��es "SIM" e "N�O"
				sessao.removeAttribute("desabilitarCreditoARealizar");
			}
		}else{

			// Exibir com a op��o �N�O� selecionada.
			form.set("indicadorCreditoARealizar", ConstantesSistema.NAO.toString());

			// Caso o usu�rio n�o possua permiss�o para efetuar o parcelamento de cr�ditos a
			// realizar
			if(!temPermissaoParcelarCreditoARealizar){

				// N�o permitir que o usu�rio selecione a op��o "SIM"
				sessao.setAttribute("desabilitarCreditoARealizar", "S");
			}else{

				// Caso contr�rio, ou seja, caso o usu�rio possua permiss�o para efetuar o
				// parcelamento de cr�ditos a realizar
				// permitir que o usu�rio selecione entre as op��es "SIM" e "N�O"
				sessao.removeAttribute("desabilitarCreditoARealizar");
			}
		}

	}

	/**
	 * [UC0214] Efetuar Parcelamento de D�bitos
	 * Verificar Situa��es Especiais de Coban�a do im�vel
	 * 
	 * @author Saulo Lima
	 * @date 11/09/2013
	 * @param form
	 * @param idImovel
	 * @param usuario
	 */
	private void verificarImovelSituacaoEspecialCobrancaParcelamento(DynaActionForm form, Integer idImovel, Usuario usuario){

		Fachada fachada = Fachada.getInstancia();
		String situacoesCobrancaDescricoes = "";

		// System.out.println("### Chamou o m�todo verificarImovelSituacaoEspecialCobrancaParcelamento(...) ###");

		String indicadorMensagem = null;
		try{
			indicadorMensagem = (String) ParametroCobranca.P_EXIBIR_MSG_ADVERTENCIA_SITUACAO_COBRANCA_PARCELAMENTO
							.executar(ExecutorParametrosFaturamento.getInstancia());
		}catch(ControladorException e){
			throw new ActionServletException(e.getMessage(), e.getParametroMensagem().toArray(new String[e.getParametroMensagem().size()]));
		}

		/*
		 * Caso o sistema indique a exibi��o de mensagens de advert�ncia relativas a situa��o de
		 * cobran�a do im�vel e o im�vel esteja em situa��o de cobran�a, exibir mensagem
		 */
		if(indicadorMensagem != null && indicadorMensagem.equals(ConstantesSistema.SIM.toString())){

			Collection<ImovelCobrancaSituacao> colecaoImovelCobrancaSituacao = fachada.pesquisarImovelCobrancaSituacaoAtivas(idImovel);

			if(!Util.isVazioOrNulo(colecaoImovelCobrancaSituacao)){
				situacoesCobrancaDescricoes += System.getProperty("line.separator");

				// verificar permissao especial
				boolean temPermissaoEspecial = fachada.verificarPermissaoEspecial(
								PermissaoEspecial.PARCELAR_IMOVEL_EM_SITUACAO_COBRANCA_COM_IMPEDIMENTO, usuario);

				for(ImovelCobrancaSituacao imovelCobrancaSituacao : colecaoImovelCobrancaSituacao){

					situacoesCobrancaDescricoes += "  - " + imovelCobrancaSituacao.getCobrancaSituacao().getDescricao()
									+ System.getProperty("line.separator");

					/*
					 * Caso alguma das situa��es de cobran�a do im�vel iniba parcelamento e o
					 * usu�rio n�o possua permiss�o especial pata tal, o parcelamento n�o poder�
					 * prosseguir para este im�vel
					 */
					if(imovelCobrancaSituacao.getCobrancaSituacao().getIndicadorInibeParcelamento().equals(ConstantesSistema.SIM)
									&& !temPermissaoEspecial){
						throw new ActionServletException("atencao.parcelamento.imovel_situacao_cobranca_impedimento", null,
										imovelCobrancaSituacao.getCobrancaSituacao().getDescricao());
					}
				}
			}
		}

		form.set("situacoesCobrancaDescricoes", situacoesCobrancaDescricoes);
	}

}