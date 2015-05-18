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

import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.cliente.ClienteTipo;
import gcom.cadastro.cliente.FiltroCliente;
import gcom.cadastro.imovel.*;
import gcom.cobranca.CobrancaSituacao;
import gcom.cobranca.ResolucaoDiretoria;
import gcom.cobranca.bean.ContaHelper;
import gcom.cobranca.bean.ContaValoresHelper;
import gcom.cobranca.bean.GuiaPagamentoValoresHelper;
import gcom.cobranca.bean.ObterDebitoImovelOuClienteHelper;
import gcom.cobranca.parcelamento.ParcelamentoPerfil;
import gcom.fachada.Fachada;
import gcom.faturamento.conta.Conta;
import gcom.faturamento.conta.FiltroConta;
import gcom.faturamento.debito.DebitoACobrar;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.micromedicao.consumo.LigacaoTipo;
import gcom.micromedicao.hidrometro.HidrometroCapacidade;
import gcom.seguranca.acesso.PermissaoEspecial;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.filtro.ConectorOr;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.cobranca.ExecutorParametrosCobranca;
import gcom.util.parametrizacao.cobranca.ParametroCobranca;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

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
 * 
 * @author Roberta Costa
 * @date 24/01/2006
 */
public class ProcessarEfetuarParcelamentoDebitosProcesso1Action
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("processo1");

		Fachada fachada = Fachada.getInstancia();

		HttpSession sessao = httpServletRequest.getSession(false);

		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");

		DynaActionForm efetuarParcelamentoDebitosActionForm = (DynaActionForm) actionForm;
		if("sim".equalsIgnoreCase((String) httpServletRequest.getAttribute("popupEfetuarParcelamento"))
						|| "sim".equalsIgnoreCase(httpServletRequest.getParameter("popupEfetuarParcelamento"))){
			httpServletRequest.setAttribute("popupEfetuarParcelamento", "sim");
		}

		// Pega os codigos que o usuario digitou para a pesquisa direta de imovel
		String codigoImovel = httpServletRequest.getParameter("matriculaImovel");
		// String codigoImovelAntes = (String)
		// efetuarParcelamentoDebitosActionForm.get("codigoImovelAntes");
		String inscricaoImovel = (String) efetuarParcelamentoDebitosActionForm.get("inscricaoImovel");
		// Boolean indicadorContas = true;
		String dataParcelamento = (String) (efetuarParcelamentoDebitosActionForm.get("dataParcelamento"));
		String resolucaoDiretoria = (String) (efetuarParcelamentoDebitosActionForm.get("resolucaoDiretoria"));
		String inicioIntervaloParcelamento = (String) efetuarParcelamentoDebitosActionForm.get("inicioIntervaloParcelamento");
		String fimIntervaloParcelamento = (String) efetuarParcelamentoDebitosActionForm.get("fimIntervaloParcelamento");
		String inicioIntervaloParcelamentoOriginal = (String) efetuarParcelamentoDebitosActionForm
						.get("inicioIntervaloParcelamentoOriginal");
		String fimIntervaloParcelamentoOriginal = (String) efetuarParcelamentoDebitosActionForm.get("fimIntervaloParcelamentoOriginal");
		String indicadorContasRevisao = (String) efetuarParcelamentoDebitosActionForm.get("indicadorContasRevisao");
		String indicadorGuiasPagamento = (String) efetuarParcelamentoDebitosActionForm.get("indicadorGuiasPagamento");
		String indicadorAcrescimosImpotualidade = (String) efetuarParcelamentoDebitosActionForm.get("indicadorAcrescimosImpotualidade");
		String indicadorDebitosACobrar = (String) efetuarParcelamentoDebitosActionForm.get("indicadorDebitosACobrar");
		String indicadorCreditoARealizar = (String) efetuarParcelamentoDebitosActionForm.get("indicadorCreditoARealizar");
		String indicadorRestabelecimento = (String) efetuarParcelamentoDebitosActionForm.get("indicadorRestabelecimento");
		String indicadorParcelamentoCobrancaBancaria = (String) efetuarParcelamentoDebitosActionForm
						.get("indicadorParcelamentoCobrancaBancaria");
		int indicadorCalcularAcrescimosSucumbenciaAnterior = 2;

		String idClienteParcelamento = (String) efetuarParcelamentoDebitosActionForm.get("idClienteParcelamento");
		String cpfClienteParcelamentoDigitado = (String) efetuarParcelamentoDebitosActionForm.get("cpfClienteParcelamentoDigitado");
		String cpfClienteParcelamento = (String) efetuarParcelamentoDebitosActionForm.get("cpfClienteParcelamento");
		String indicadorPessoaFisicaJuridica = (String) efetuarParcelamentoDebitosActionForm.get("indicadorPessoaFisicaJuridica");
		String[] idsMotivoRevisao = (String[]) efetuarParcelamentoDebitosActionForm.get("idMotivoRevisao");
		Integer[] idsMotivoRevisaoInt = null;
		if(!Util.isVazioOrNulo(idsMotivoRevisao)){
			idsMotivoRevisaoInt = new Integer[idsMotivoRevisao.length];
			for(int i = 0; i < idsMotivoRevisao.length; i++){
				idsMotivoRevisaoInt[i] = Util.obterInteger(idsMotivoRevisao[i]);
			}
		}

		String indicadorCobrancaParcelamento = (String) efetuarParcelamentoDebitosActionForm.get("indicadorCobrancaParcelamento");

		this.validarDataParcelamentoCobrancaBancaria(dataParcelamento, efetuarParcelamentoDebitosActionForm);



		// [FS0032] � Verificar sele��o de boleto banc�rio para negocia��o
		if(sessao.getAttribute("colecaoBoletoHelper") != null){
			Collection<BoletoBancarioHelper> colecaoBoletoHelper = (Collection<BoletoBancarioHelper>) sessao
							.getAttribute("colecaoBoletoHelper");

			// Verifica se algum boleto foi marcado.
			if(!colecaoBoletoHelper.isEmpty()){
				Iterator<BoletoBancarioHelper> opcoesBoletosBancarios = colecaoBoletoHelper.iterator();
				boolean opcaoMarcada = false;
				while(opcoesBoletosBancarios.hasNext()){
					BoletoBancarioHelper boletoBancarioHelper = opcoesBoletosBancarios.next();
					if(efetuarParcelamentoDebitosActionForm.get("idBoletoNegociacao") != null){
						if(((String) efetuarParcelamentoDebitosActionForm.get("idBoletoNegociacao")).equals(boletoBancarioHelper.getId()
										.toString())){
							opcaoMarcada = true;
						}
					}
				}
				if(!opcaoMarcada){
					throw new ActionServletException("atencao.selecione_um_boleto");
				}
			}
		}

		BigDecimal valorDebitoTotalAtualizadoImovel = BigDecimal.ZERO;
		if(efetuarParcelamentoDebitosActionForm.get("valorDebitoTotalAtualizadoImovel") != null
						&& !efetuarParcelamentoDebitosActionForm.get("valorDebitoTotalAtualizadoImovel").equals("")){
			valorDebitoTotalAtualizadoImovel = Util.formatarMoedaRealparaBigDecimal((String) efetuarParcelamentoDebitosActionForm
							.get("valorDebitoTotalAtualizadoImovel"));
		}

		if(valorDebitoTotalAtualizadoImovel.compareTo(BigDecimal.ZERO) == 0){
			throw new ActionServletException("atencao.imovel.sem.debitos", null, codigoImovel);
		}

		// 1. Obtem a situa��o do imovel
		Integer situacaoAguaId = Integer.valueOf((String) (efetuarParcelamentoDebitosActionForm.get("situacaoAguaId")));
		Integer situacaoEsgotoId = Integer.valueOf((String) (efetuarParcelamentoDebitosActionForm.get("situacaoEsgotoId")));

		if(inscricaoImovel.equals("")){
			throw new ActionServletException("atencao.imovel.parcelamento.pesquisar");
		}

		if(cpfClienteParcelamentoDigitado != null && !cpfClienteParcelamentoDigitado.equals("")){
			this.validarCpfCnpj(cpfClienteParcelamentoDigitado, idClienteParcelamento, fachada, indicadorPessoaFisicaJuridica);
		}

		// Data Parcelamento obrigat�rio
		if(dataParcelamento == null || dataParcelamento.equals("")){
			throw new ActionServletException("atencao.data.parcelamento.obrigatorio");
		}else{
			FiltroConta filtroConta = new FiltroConta();
			filtroConta.adicionarParametro(new ParametroSimples(FiltroConta.IMOVEL_ID, Util.obterInteger(codigoImovel)));

			String[] idsCobrancaBancaria = null;

			try{
				idsCobrancaBancaria = ((String) ParametroCobranca.P_MOTIVO_REVISAO_COBRANCA_BANCARIA.executar(ExecutorParametrosCobranca
								.getInstancia())).split(",");
			}catch(ControladorException e){

			}
			if(idsCobrancaBancaria != null){
				if(idsCobrancaBancaria.length > 1){
					for(int i = 0; i < idsCobrancaBancaria.length; i++){
						if(i == 0){
							filtroConta.adicionarParametro(new ParametroSimples(FiltroConta.CONTA_MOTIVO_REVISAO_ID, Util
											.obterInteger(idsCobrancaBancaria[i]), ConectorOr.CONECTOR_OR, idsCobrancaBancaria.length - 1));
						}else if(i == idsCobrancaBancaria.length - 1){
							filtroConta.adicionarParametro(new ParametroSimples(FiltroConta.CONTA_MOTIVO_REVISAO_ID, Util
											.obterInteger(idsCobrancaBancaria[i])));
						}else{
							filtroConta.adicionarParametro(new ParametroSimples(FiltroConta.CONTA_MOTIVO_REVISAO_ID, Util
											.obterInteger(idsCobrancaBancaria[i])));
						}
					}
				}else{
					filtroConta.adicionarParametro(new ParametroSimples(FiltroConta.CONTA_MOTIVO_REVISAO_ID, Util
									.obterInteger(idsCobrancaBancaria[0])));
				}
			}

			Collection<Conta> contas = getFachada().pesquisar(filtroConta, Conta.class.getName());

			// [FS0025] � Validar data do parcelamento de contas em cobran�a banc�ria
			if(!Util.isVazioOrNulo(contas) && indicadorParcelamentoCobrancaBancaria != null
							&& indicadorParcelamentoCobrancaBancaria.equals("1")){
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
						throw new ActionServletException("atencao.data_parcelamento_nao_pode_menor_X_dias_atras", null, qtdDias.toString());
					}
				}
			}

		}

		// Resolu��o de diretoria obrigat�rio
		if(resolucaoDiretoria == null || resolucaoDiretoria.equals("")){
			throw new ActionServletException("atencao.resolucao.diretoria.obrigatorio");
		}

		// [FS0002] Validar m�s e ano de refer�ncia
		if((fimIntervaloParcelamento != null && !fimIntervaloParcelamento.equals(""))
						&& (inicioIntervaloParcelamento != null && !inicioIntervaloParcelamento.equals(""))){

			// Caso o m�s/ano esteja inv�lido
			if(!Util.validarMesAno(fimIntervaloParcelamento)){
				throw new ActionServletException("atencao.mes.ano.referencia.final.invalido");
			}

			// Caso m�s/ano refer�ncia final seja anterior ao de refer�ncia incial
			Integer fimIntervaloParcelamentoFormatado = Util.formatarMesAnoComBarraParaAnoMes(fimIntervaloParcelamento);
			Integer inicioIntervaloParcelamentoFormatado = Util.formatarMesAnoComBarraParaAnoMes(inicioIntervaloParcelamento);

			if(fimIntervaloParcelamentoFormatado < inicioIntervaloParcelamentoFormatado){
				throw new ActionServletException("atencao.mes.ano.referencia.final.anterior.referencia.inicial");
			}

			String idClienteDebito = null;
			Integer idRelacaoClienteDebito = null;
			int indicadorDebito = 1;
			if(efetuarParcelamentoDebitosActionForm.get("idClienteRelacaoImovelSelecionado") != null
							&& !((String) efetuarParcelamentoDebitosActionForm.get("idClienteRelacaoImovelSelecionado")).equals("")){
				String[] dados = ((String) efetuarParcelamentoDebitosActionForm.get("idClienteRelacaoImovelSelecionado")).split("\\.");

				idClienteDebito = (dados[1]).toString();
				idRelacaoClienteDebito = Integer.valueOf(dados[0]);

				indicadorDebito = 2;
			}

			if((!inicioIntervaloParcelamento.equals(inicioIntervaloParcelamentoOriginal))
							|| (!fimIntervaloParcelamento.equals(fimIntervaloParcelamentoOriginal))){

				Short indicadorNaoConsiderarPagamentoNaoClassificado = 2;

				// Obter todo o d�bito do im�vel para exibi��o
				ObterDebitoImovelOuClienteHelper colecaoDebitoCliente = fachada
								.obterDebitoImovelOuCliente(
												indicadorDebito, // Indicador
												// de d�bito do im�vel
												codigoImovel, // Matr�cula do im�vel
												idClienteDebito, // C�digo do cliente
												idRelacaoClienteDebito, // Tipo de rela��o cliente
																		// im�vel
												Util
																.formatarMesAnoParaAnoMesSemBarra((inicioIntervaloParcelamentoOriginal
																				.length() != 0 && !inicioIntervaloParcelamentoOriginal
																				.equals("")) ? inicioIntervaloParcelamentoOriginal
																				: inicioIntervaloParcelamento), // Refer�ncia
												// inicial do d�bito.
												Util
																.formatarMesAnoParaAnoMesSemBarra((fimIntervaloParcelamentoOriginal
																				.length() != 0 && !fimIntervaloParcelamentoOriginal
																				.equals("")) ? fimIntervaloParcelamentoOriginal
																				: fimIntervaloParcelamento), // Fim
												// do d�bito.
												Util.converteStringParaDate("01/01/0001"), // Inicio
												// vencimento
												Util.converteStringParaDate("31/12/9999"), // Fim
												// vencimento
												1, // Indicador de pagamento
												Integer.valueOf(indicadorContasRevisao), // conta em
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
												ConstantesSistema.SIM, ConstantesSistema.SIM, ConstantesSistema.SIM,
												indicadorCalcularAcrescimosSucumbenciaAnterior, null);

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
							httpServletRequest.setAttribute("caminhoActionConclusao", "exibirEfetuarParcelamentoDebitosAction.do");
							return montarPaginaConfirmacao("atencao.periodo_informado_nao_contempla_todas_contas_cobranca_bancaria",
											httpServletRequest, actionMapping, "");

						}
					}
				}
			}
		}

		// if(situacaoAguaId.equals(LigacaoAguaSituacao.SUPRIMIDO) ||
		// situacaoAguaId.equals(LigacaoAguaSituacao.SUPR_PARC)
		// || situacaoAguaId.equals(LigacaoAguaSituacao.SUPR_PARC_PEDIDO)){
		//
		// if(indicadorRestabelecimento == null || indicadorRestabelecimento.trim().equals("")){
		// throw new ActionServletException("atencao.campo_selecionado.obrigatorio", null,
		// "Com Restabelecimento?");
		// }
		// }
		//

		Collection collIdsSitLigacaoAguaRestabelecimento;
		try{
			collIdsSitLigacaoAguaRestabelecimento = EfetuarParcelamentoDebitosWizardAction.getSitLigacaoAguaRestabelecimento();
			if(collIdsSitLigacaoAguaRestabelecimento != null && collIdsSitLigacaoAguaRestabelecimento.size() > 0){

				if(collIdsSitLigacaoAguaRestabelecimento.contains(situacaoAguaId)){
					if(indicadorRestabelecimento == null || indicadorRestabelecimento.trim().equals("")){
						throw new ActionServletException("atencao.campo_selecionado.obrigatorio", null, "Com Restabelecimento?");
					}
				}

			}
		}catch(ControladorException e){
			throw new ActionServletException("atencao.sistemaparametro_inexistente", "P_SIT_LIGACAO_AGUA_RESTABELECIMENTO");
		}


		// Verifica se as perguntas sobre o parcelamento foram respondidas
		if(indicadorContasRevisao == null || indicadorContasRevisao.equals("")){
			throw new ActionServletException("atencao.campo_selecionado.obrigatorio", null, "'Considerar Contas em Revis�o?'");
		}
		if(indicadorGuiasPagamento == null || indicadorGuiasPagamento.equals("")){
			throw new ActionServletException("atencao.campo_selecionado.obrigatorio", null, "'Considerar Guias de Pagamento?'");
		}
		if(indicadorAcrescimosImpotualidade == null || indicadorAcrescimosImpotualidade.equals("")){
			throw new ActionServletException("atencao.campo_selecionado.obrigatorio", null, "'Considerar Acr�scimos por Impontualidade?'");
		}
		if(indicadorDebitosACobrar == null || indicadorDebitosACobrar.equals("")){
			throw new ActionServletException("atencao.campo_selecionado.obrigatorio", null, "'Considerar D�bitos a Cobrar?'");
		}
		if(indicadorCreditoARealizar == null || indicadorCreditoARealizar.equals("")){
			throw new ActionServletException("atencao.campo_selecionado.obrigatorio", null, "'Considerar Cr�ditos a Realizar?'");
		}

		String exibirParcelamentoCobrancaBancaria = (String) sessao.getAttribute("exibirParcelamentoCobrancaBancaria");

		if(exibirParcelamentoCobrancaBancaria != null && exibirParcelamentoCobrancaBancaria.equals("S")){
			if(indicadorParcelamentoCobrancaBancaria == null || indicadorParcelamentoCobrancaBancaria.equals("")){
				throw new ActionServletException("atencao.campo_selecionado.obrigatorio", null, "'Parcelamento de Cobran�a Banc�ria?'");
			}

		}

		// Caso o valor Total do D�bito seja negativo n�o h� d�bitos para parcelar
		if(valorDebitoTotalAtualizadoImovel.compareTo(BigDecimal.ZERO) == -1){
			throw new ActionServletException("atencao.imovel.sem.debitos", null, codigoImovel);
		}

		// Caso a data do parcelamento seja maior que a data atual
		Date dataParcelamentoFormatado = Util.converteStringParaDate((String) efetuarParcelamentoDebitosActionForm.get("dataParcelamento"));
		if(dataParcelamentoFormatado.after(new Date())){
			throw new ActionServletException("atencao.data.parcelamento.menor.igual.data.corrente");
		}

		if(fachada.verificarRDUtilizadaPeloImovel(Integer.valueOf(resolucaoDiretoria), Integer.valueOf(codigoImovel)) != null){
			throw new ActionServletException("atencao.rd.ja.utilizada.parcelamento");
		}

		// ---------------------------------------------------------------------------------------
		ResolucaoDiretoria rd = null;
		ParcelamentoPerfil parcelamentoPerfil = null;
		if(!Util.isVazioOuBranco(resolucaoDiretoria)){

			rd = EfetuarParcelamentoDebitosWizardAction.obterResolucaoDiretoria(fachada, efetuarParcelamentoDebitosActionForm);

			Collection colecaoContasImovel = (Collection) sessao.getAttribute("colecaoContaValoresImovel");

			if(colecaoContasImovel != null && !colecaoContasImovel.isEmpty()){
				EfetuarParcelamentoDebitosWizardAction.verificarRDComRestricao(rd, codigoImovel, inicioIntervaloParcelamento,
								fimIntervaloParcelamento, fachada, sessao, efetuarParcelamentoDebitosActionForm);

			}

			parcelamentoPerfil = EfetuarParcelamentoDebitosWizardAction.obterPerfilParcelamentoImovel(rd, codigoImovel, fachada, sessao,
							efetuarParcelamentoDebitosActionForm);

		}

		// ---------------------------------------------------------------------------------------

		Integer idImovel = Integer.valueOf(codigoImovel);

		if(parcelamentoPerfil == null){
			throw new ActionServletException("atencao.nao.existe.perfil.parcelamento.correspondente.situacao.imovel");
		}

		if(efetuarParcelamentoDebitosActionForm.get("areaConstruidaImovel") != null
						&& !efetuarParcelamentoDebitosActionForm.get("areaConstruidaImovel").equals("")){
			BigDecimal areaConstruidaImovel = new BigDecimal((String) efetuarParcelamentoDebitosActionForm.get("areaConstruidaImovel"));

			if(parcelamentoPerfil.getNumeroAreaConstruida() != null
							&& areaConstruidaImovel.compareTo(parcelamentoPerfil.getNumeroAreaConstruida()) > 0){
				// area contruida do imovel > a area contruida do perfil do parcelamento
				throw new ActionServletException("atencao.nao.existe.perfil.parcelamento.correspondente.situacao.imovel");
			}
		}

		if(parcelamentoPerfil.getCategoria() != null){
			Imovel imovel = new Imovel();
			imovel.setId(idImovel);
			Collection colecaoCategoria = fachada.pesquisarCategoriasImovel(imovel);

			Iterator iter = colecaoCategoria.iterator();
			while(iter.hasNext()){
				Categoria categoria = (Categoria) iter.next();

				if(!categoria.getId().equals(parcelamentoPerfil.getCategoria().getId())){
					// categoria principal do imovel != categoria do perfl do parcelamento
					throw new ActionServletException("atencao.nao.existe.perfil.parcelamento.correspondente.situacao.imovel");
				}
			}
		}

		Imovel imovel = new Imovel();
		imovel.setId(idImovel);
		int qtdeEconomiasImovel = fachada.obterQuantidadeEconomias(imovel);

		ImovelSituacao imovelSituacao = (ImovelSituacao) sessao.getAttribute("imovelSituacao");

		if(parcelamentoPerfil.getNumeroConsumoEconomia() != null){

			Integer idLigacaoTipo = LigacaoTipo.LIGACAO_AGUA;

			if(imovelSituacao.getImovelSituacaoTipo().getId().equals(ImovelSituacaoTipo.LIGADO_SO_ESGOTO)){
				idLigacaoTipo = LigacaoTipo.LIGACAO_ESGOTO;
			}

			Integer consumoMedio = fachada.obterConsumoMedioEmConsumoHistorico(idImovel, idLigacaoTipo);

			Integer consumoMedioPorEconomia = 0;
			if(consumoMedio != null && consumoMedio.intValue() != 0){
				consumoMedioPorEconomia = Util.dividirArredondarResultado(consumoMedio, qtdeEconomiasImovel);
			}

			if(consumoMedioPorEconomia.compareTo(parcelamentoPerfil.getNumeroConsumoEconomia()) > 0){
				throw new ActionServletException("atencao.nao.existe.perfil.parcelamento.correspondente.situacao.imovel");
			}

			// if(fachada.verificarExistenciaHidrometroEmLigacaoAgua(idImovel)){
			// Integer consumoMedio6meses = fachada.obterValorConsumoMedio6meses(idImovel);
			// Imovel imovel = new Imovel();
			// imovel.setId(idImovel);
			//				
			// int qtdeEconomiasImovel = fachada.obterQuantidadeEconomias(imovel);
			// Integer consumoMedio6mesesEconomia =
			// Util.dividirArredondarResultado(consumoMedio6meses,qtdeEconomiasImovel);
			//				
			// if(consumoMedio6mesesEconomia.compareTo(parcelamentoPerfil.getNumeroConsumoEconomia())
			// > 0){
			// throw new
			// ActionServletException("atencao.nao.existe.perfil.parcelamento.correspondente.situacao.imovel");
			// }
			// }
		}

		if(parcelamentoPerfil.getQuantidadeEconomias() != null){
			if(qtdeEconomiasImovel > parcelamentoPerfil.getQuantidadeEconomias().intValue()){
				throw new ActionServletException("atencao.nao.existe.perfil.parcelamento.correspondente.situacao.imovel");
			}
		}

		if(parcelamentoPerfil.getCapacidadeHidrometro() != null){

			HidrometroCapacidade hidrometroCapacidade = null;
			hidrometroCapacidade = fachada.obterHidrometroCapacidadeEmLigacaoAgua(idImovel);

			if(hidrometroCapacidade != null){
				if(!hidrometroCapacidade.getId().equals(1) && !hidrometroCapacidade.getId().equals(2)
								&& !hidrometroCapacidade.getId().equals(8)){
					throw new ActionServletException("atencao.nao.existe.perfil.parcelamento.correspondente.situacao.imovel");
				}
			}
		}

		// String descSituacaoCobranca =
		// fachada.obterSituacaoCobrancaImovel(Integer.valueOf(codigoImovel));

		// [FS0021] - Verificar situa��o de cobran�a
		if(parcelamentoPerfil.getIndicadorChequeDevolvido().equals(ConstantesSistema.SIM)){

			FiltroImovel filtroImovel = new FiltroImovel();
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("cobrancaSituacao");
			filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, codigoImovel));

			Collection imovelCobrancaSituacaoEncontrada = fachada.pesquisar(filtroImovel, Imovel.class.getName());

			// Verifica se o Im�vel tem situa��o de cobran�a
			if(imovelCobrancaSituacaoEncontrada != null && !imovelCobrancaSituacaoEncontrada.isEmpty()){

				CobrancaSituacao cobrancaSituacao = ((Imovel) ((List) imovelCobrancaSituacaoEncontrada).get(0)).getCobrancaSituacao();

				if(cobrancaSituacao != null){
					throw new ActionServletException("atencao.imovel.situacao.cobranca", null, cobrancaSituacao.getDescricao());
				}
			}
		}

		// // [SB0024] � Validar autoriza��o de acesso ao im�vel pelos usu�rios das empresas de
		// // cobran�a administrativa
		// // [UC3062] Validar Autoriza��o Acesso Im�vel - Cobran�a Administrativa
		// if(fachada.obterValidacaoAutorizacaoAcessoImovelCobrancaAdministrativa(usuario, idImovel,
		// ConstantesSistema.CODIGO_VALIDACAO_USUARIO_EMPRESA_COBRANCA_ADMINISTRATIVA).equals(ConstantesSistema.NAO)){
		// throw new
		// ActionServletException("atencao.usuario_sem_acesso_dados_cobranca_administrativa",
		// usuario.getLogin(), idImovel
		// .toString());
		// }
		//
		// // [SB0025] � Validar autoriza��o de acesso ao im�vel em cobran�a administrativa
		// // pelos usu�rios da empresa contratante
		// Collection<ContaValoresHelper> colecaoContaValores = (Collection<ContaValoresHelper>)
		// sessao
		// .getAttribute("colecaoContaValoresImovel");
		// Collection<GuiaPagamentoValoresHelper> colecaoGuiaPagamentoValores =
		// (Collection<GuiaPagamentoValoresHelper>) sessao
		// .getAttribute("colecaoGuiaPagamentoValoresImovel");
		// if(fachada.existeContaOuGuiaPagamentoDebitoImovelOuCliente(colecaoContaValores,
		// colecaoGuiaPagamentoValores)){
		// // 1.1. Caso existam, na lista de contas retornada pelo [UC0067], contas em
		// // cobran�a administrativa ou caso existam, na lista de guias de pagamento
		// // retornada pelo [UC0067], guias de pagamento em cobran�a administrativa
		// // [UC3062] - Validar Autoriza��o Acesso Im�vel Pela Empresa Cobran�a
		// // Administrativa
		// if(fachada.obterValidacaoAutorizacaoAcessoImovelCobrancaAdministrativa(usuario,
		// Integer.valueOf(codigoImovel),
		// ConstantesSistema.CODIGO_VALIDACAO_USUARIO_EMPRESA_CONTRATANTE).equals(ConstantesSistema.NAO)){
		// throw new
		// ActionServletException("atencao.usuario_sem_acesso_dados_cobranca_administrativa",
		// usuario.getLogin(), Integer
		// .valueOf(codigoImovel).toString());
		// }
		// }

		Collection<ContaValoresHelper> colecaoContaValores = (Collection<ContaValoresHelper>) sessao
						.getAttribute("colecaoContaValoresImovel");

		Collection<GuiaPagamentoValoresHelper> colecaoGuiaPagamentoValores = null;
		if(indicadorGuiasPagamento.equals(ConstantesSistema.SIM.toString())){
			colecaoGuiaPagamentoValores = (Collection<GuiaPagamentoValoresHelper>) sessao.getAttribute("colecaoGuiaPagamentoValoresImovel");
		}

		Collection<DebitoACobrar> colecaoDebitoACobrar = null;
		if(indicadorDebitosACobrar.equals(ConstantesSistema.SIM.toString())){
			colecaoDebitoACobrar = (Collection<DebitoACobrar>) sessao.getAttribute("colecaoDebitoACobrarImovel");
		}


		ObterDebitoImovelOuClienteHelper colecaoDebitoImovel = new ObterDebitoImovelOuClienteHelper(colecaoContaValores,
						colecaoDebitoACobrar, null, colecaoGuiaPagamentoValores);

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

		// [FS0050] Verificar im�vel em execu��o fiscal
		Short indicadorImovelEmExecucaoFiscal = fachada.verificarImovelEmExecucaoFiscal(Integer.valueOf(codigoImovel));
		boolean temPermissaoParcelarEmExecucaoFiscal = fachada.verificarPermissaoEspecial(
						PermissaoEspecial.PARCELAR_IMOVEL_EM_EXECUCAO_FISCAL, usuario);

		if(indicadorImovelEmExecucaoFiscal.equals(Short.valueOf("1")) && !temPermissaoParcelarEmExecucaoFiscal){
			throw new ActionServletException("atencao.usuario_sem_permissao_parcelar_em_execucao_fiscal", codigoImovel,
							usuario.getLogin());
		}

		efetuarParcelamentoDebitosActionForm.set("indicadorImovelEmExecucaoFiscal", Short.toString(indicadorImovelEmExecucaoFiscal));

		// [SB0024] � Validar autoriza��o de acesso ao im�vel pelos usu�rios das empresas de
		// cobran�a administrativa
		Integer resposta = fachada.validarAcessoImovelUsuarioEmpresaCobrancaAdministrativa(usuario, Integer.valueOf(codigoImovel),
						colecaoDebitoImovel);

		if(resposta.equals(Integer.valueOf(1)) || resposta.equals(Integer.valueOf(3))){
			throw new ActionServletException("atencao.usuario_sem_acesso_dados_cobranca_administrativa", usuario.getLogin(), codigoImovel);
		}

		// [SB0025] � Validar autoriza��o de acesso ao im�vel em cobran�a administrativa
		// pelos usu�rios da empresa contratante
		Integer permiteAcessoImovelEmCobrancaAdministrativaUsuarioEmpresaContratante = fachada
						.validarAcessoImovelEmCobrancaAdministrativaUsuarioEmpresaContratante(usuario, Integer.valueOf(codigoImovel),
										colecaoDebitoImovel);

		if(permiteAcessoImovelEmCobrancaAdministrativaUsuarioEmpresaContratante != null){

			if(permiteAcessoImovelEmCobrancaAdministrativaUsuarioEmpresaContratante.intValue() == 1){

				throw new ActionServletException("atencao.usuario_sem_acesso_dados_cobranca_administrativa_imovel_1", codigoImovel,
								usuario.getLogin());

			}else if(permiteAcessoImovelEmCobrancaAdministrativaUsuarioEmpresaContratante.intValue() == 2){

				throw new ActionServletException("atencao.usuario_sem_acesso_dados_cobranca_administrativa_imovel_2", codigoImovel,
								usuario.getLogin());
			}
		}

		// [FS0036] - Verificar permiss�o para cobran�a do parcelamento por guia
		// . Caso o campo �Cobran�a do Parcelamento� esteja com a op��o �Guia de Pagamento�
		// selecionada.
		if(indicadorCobrancaParcelamento != null
						&& indicadorCobrancaParcelamento.equals(ConstantesSistema.INDICADOR_COBRANCA_PARC_GUIA_PAGAMENTO)){

			// . Caso n�o seja parcelamento de contas em cobran�a banc�ria (campo �Parcelamento de
			// Cobran�a Banc�ria?� com a op��o �N�o� selecionada)
			if(indicadorParcelamentoCobrancaBancaria != null && indicadorParcelamentoCobrancaBancaria.equals("2")){
				// . Caso o indicador de permiss�o de cobran�a do parcelamento por meio de guia de
				// pagamento do perfil de parcelamento esteja desativado.
				String descricaoAssuntoRD = "";
				if(rd != null){
					descricaoAssuntoRD = rd.getDescricaoAssunto();
				}

				if(parcelamentoPerfil.getIndicadorPermiteParcelamentoPorGuiaPagamento().equals(ConstantesSistema.NAO)){
					throw new ActionServletException("atencao.parcelamento.perfil.rd.cobranca.guia", null, descricaoAssuntoRD);

				}
			}

		}

		sessao.setAttribute("parcelamentoPerfil", parcelamentoPerfil);

		// Verifica se os campos obrigat�rios continuam iguais quando houve navega��o nas abas
		if(sessao.getAttribute("codigoImovelEscolhida") == null && sessao.getAttribute("dataParcelamentoEscolhida") == null
						&& sessao.getAttribute("resolucaoDiretoriaEscolhida") == null
						&& sessao.getAttribute("fimIntervaloParcelamentoEscolhida") == null
						&& sessao.getAttribute("inicioIntervaloParcelamentoEscolhida") == null
						&& sessao.getAttribute("indicadorContasRevisaoEscolhida") == null
						&& sessao.getAttribute("indicadorGuiasPagamentoEscolhida") == null
						&& sessao.getAttribute("indicadorAcrescimosImpotualidadeEscolhida") == null
						&& sessao.getAttribute("indicadorDebitosACobrarEscolhida") == null
						&& sessao.getAttribute("indicadorCreditoARealizarEscolhida") == null
						&& sessao.getAttribute("idsMotivoRevisaoEscolhido") == null){

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

		}else{
			// Caso algum campo tenha mudado limpar a sess�o
			if(!codigoImovel.equals("" + sessao.getAttribute("codigoImovelEscolhida"))
							|| !dataParcelamento.equals("" + sessao.getAttribute("dataParcelamentoEscolhida"))
							|| !resolucaoDiretoria.equals("" + sessao.getAttribute("resolucaoDiretoriaEscolhida"))
							|| !fimIntervaloParcelamento.equals("" + sessao.getAttribute("fimIntervaloParcelamentoEscolhida"))
							|| !inicioIntervaloParcelamento.equals("" + sessao.getAttribute("inicioIntervaloParcelamentoEscolhida"))
							|| !indicadorContasRevisao.equals("" + sessao.getAttribute("indicadorContasRevisaoEscolhida"))
							|| !indicadorGuiasPagamento.equals("" + sessao.getAttribute("indicadorGuiasPagamentoEscolhida"))
							|| !indicadorAcrescimosImpotualidade.equals(""
											+ sessao.getAttribute("indicadorAcrescimosImpotualidadeEscolhida"))
							|| !indicadorDebitosACobrar.equals("" + sessao.getAttribute("indicadorDebitosACobrarEscolhida"))
							|| !indicadorCreditoARealizar.equals("" + sessao.getAttribute("indicadorCreditoARealizarEscolhida"))
							|| this.isMotivoRevisaoAlterado(idsMotivoRevisaoInt, (Integer[]) sessao
											.getAttribute("idsMotivoRevisaoEscolhido"))){

				sessao.removeAttribute("colecaoContaValores");

				// Monta a p�gina de confirma��o do wizard para perguntar se o usu�rio quer
				retorno = montarPaginaConfirmacaoWizard("atencao.parametros.modificados.parcelamento",
								ConstantesSistema.NOME_WIZARD_EFETUAR_PARCELAMENTO, httpServletRequest, actionMapping, codigoImovel,
								dataParcelamento);
			}
		}
		return retorno;
	}

	private void validarCpfCnpj(String cpfCnpj, String idClienteParcelamento, Fachada fachada, String indicadorPessoaFisicaJuridica){

		// Validar CPF de cliente
		if(cpfCnpj != null && !cpfCnpj.equals("")){

			FiltroCliente filtroCliente = new FiltroCliente();

			if(ClienteTipo.INDICADOR_PESSOA_FISICA.toString().equals(indicadorPessoaFisicaJuridica)){
				filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.CPF, cpfCnpj));
			}else{
				filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.CNPJ, cpfCnpj));
			}

			Collection clienteComCpfExistente = fachada.pesquisar(filtroCliente, Cliente.class.getName());

			if(clienteComCpfExistente != null && !clienteComCpfExistente.isEmpty()){
				Cliente clienteComCpfCnpj = (Cliente) clienteComCpfExistente.iterator().next();

				if(!clienteComCpfCnpj.getId().equals(Integer.valueOf(idClienteParcelamento))){
					if(ClienteTipo.INDICADOR_PESSOA_FISICA.toString().equals(indicadorPessoaFisicaJuridica)){
						throw new ActionServletException("atencao.cpf.cliente.ja_cadastrado", null, "" + clienteComCpfCnpj.getId());
					}else{
						throw new ActionServletException("atencao.cnpj.cliente.ja_cadastrado", null, "" + clienteComCpfCnpj.getId());
					}
				}
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
	 * M�todo que valida se houve altera��o nos motivos de revis�o na navega��o entre as abas 1 e as
	 * outras. Caso tenha altera��o, leva pra p�gina de confirma��oe recalcula o parcelamento.
	 * 
	 * @author Ailton Sousa
	 * @date 06/01/2012
	 * @param idsMotivoRevisaoInt
	 * @param idsMotivoRevisaoIntAnterior
	 * @return
	 */
	private boolean isMotivoRevisaoAlterado(Integer[] idsMotivoRevisaoInt, Integer[] idsMotivoRevisaoIntAnterior){

		if(idsMotivoRevisaoInt != null){
			if(idsMotivoRevisaoIntAnterior != null && (idsMotivoRevisaoInt.length == idsMotivoRevisaoIntAnterior.length)){
				for(int i = 0; i < idsMotivoRevisaoInt.length; i++){
					if(!idsMotivoRevisaoInt[i].equals(idsMotivoRevisaoIntAnterior[i])){
						return true;
					}
				}
			}else{
				return true;
			}
		}else{
			if(idsMotivoRevisaoIntAnterior != null){
				return true;
			}
		}

		return false;
	}

}