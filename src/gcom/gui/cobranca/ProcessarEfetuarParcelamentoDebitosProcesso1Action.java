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
 * Permite efetuar o parcelamento dos débitos de um imóvel
 * [UC0214] Efetuar Parcelamento de Débitos
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



		// [FS0032] – Verificar seleção de boleto bancário para negociação
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

		// 1. Obtem a situação do imovel
		Integer situacaoAguaId = Integer.valueOf((String) (efetuarParcelamentoDebitosActionForm.get("situacaoAguaId")));
		Integer situacaoEsgotoId = Integer.valueOf((String) (efetuarParcelamentoDebitosActionForm.get("situacaoEsgotoId")));

		if(inscricaoImovel.equals("")){
			throw new ActionServletException("atencao.imovel.parcelamento.pesquisar");
		}

		if(cpfClienteParcelamentoDigitado != null && !cpfClienteParcelamentoDigitado.equals("")){
			this.validarCpfCnpj(cpfClienteParcelamentoDigitado, idClienteParcelamento, fachada, indicadorPessoaFisicaJuridica);
		}

		// Data Parcelamento obrigatório
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

			// [FS0025] – Validar data do parcelamento de contas em cobrança bancária
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

		// Resolução de diretoria obrigatório
		if(resolucaoDiretoria == null || resolucaoDiretoria.equals("")){
			throw new ActionServletException("atencao.resolucao.diretoria.obrigatorio");
		}

		// [FS0002] Validar mês e ano de referência
		if((fimIntervaloParcelamento != null && !fimIntervaloParcelamento.equals(""))
						&& (inicioIntervaloParcelamento != null && !inicioIntervaloParcelamento.equals(""))){

			// Caso o mês/ano esteja inválido
			if(!Util.validarMesAno(fimIntervaloParcelamento)){
				throw new ActionServletException("atencao.mes.ano.referencia.final.invalido");
			}

			// Caso mês/ano referência final seja anterior ao de referência incial
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

				// Obter todo o débito do imóvel para exibição
				ObterDebitoImovelOuClienteHelper colecaoDebitoCliente = fachada
								.obterDebitoImovelOuCliente(
												indicadorDebito, // Indicador
												// de débito do imóvel
												codigoImovel, // Matrícula do imóvel
												idClienteDebito, // Código do cliente
												idRelacaoClienteDebito, // Tipo de relação cliente
																		// imóvel
												Util
																.formatarMesAnoParaAnoMesSemBarra((inicioIntervaloParcelamentoOriginal
																				.length() != 0 && !inicioIntervaloParcelamentoOriginal
																				.equals("")) ? inicioIntervaloParcelamentoOriginal
																				: inicioIntervaloParcelamento), // Referência
												// inicial do débito.
												Util
																.formatarMesAnoParaAnoMesSemBarra((fimIntervaloParcelamentoOriginal
																				.length() != 0 && !fimIntervaloParcelamentoOriginal
																				.equals("")) ? fimIntervaloParcelamentoOriginal
																				: fimIntervaloParcelamento), // Fim
												// do débito.
												Util.converteStringParaDate("01/01/0001"), // Inicio
												// vencimento
												Util.converteStringParaDate("31/12/9999"), // Fim
												// vencimento
												1, // Indicador de pagamento
												Integer.valueOf(indicadorContasRevisao), // conta em
												// revisão
												Integer.valueOf(indicadorDebitosACobrar), // Débito
												// a
												// cobrar
												Integer.valueOf(indicadorCreditoARealizar), // crédito
												// a
												// realizar
												1, // Indicador de notas promissórias
												Integer.valueOf(indicadorGuiasPagamento), // guias
												// pagamento
												Integer.valueOf(indicadorAcrescimosImpotualidade), // acréscimos
												// impontualidade
												true, null, null, null, indicadorNaoConsiderarPagamentoNaoClassificado,
												ConstantesSistema.SIM, ConstantesSistema.SIM, ConstantesSistema.SIM,
												indicadorCalcularAcrescimosSucumbenciaAnterior, null);

				// [FS0049] - Verificar retirada de débitos prescritos do débito do parcelamento
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
			throw new ActionServletException("atencao.campo_selecionado.obrigatorio", null, "'Considerar Contas em Revisão?'");
		}
		if(indicadorGuiasPagamento == null || indicadorGuiasPagamento.equals("")){
			throw new ActionServletException("atencao.campo_selecionado.obrigatorio", null, "'Considerar Guias de Pagamento?'");
		}
		if(indicadorAcrescimosImpotualidade == null || indicadorAcrescimosImpotualidade.equals("")){
			throw new ActionServletException("atencao.campo_selecionado.obrigatorio", null, "'Considerar Acréscimos por Impontualidade?'");
		}
		if(indicadorDebitosACobrar == null || indicadorDebitosACobrar.equals("")){
			throw new ActionServletException("atencao.campo_selecionado.obrigatorio", null, "'Considerar Débitos a Cobrar?'");
		}
		if(indicadorCreditoARealizar == null || indicadorCreditoARealizar.equals("")){
			throw new ActionServletException("atencao.campo_selecionado.obrigatorio", null, "'Considerar Créditos a Realizar?'");
		}

		String exibirParcelamentoCobrancaBancaria = (String) sessao.getAttribute("exibirParcelamentoCobrancaBancaria");

		if(exibirParcelamentoCobrancaBancaria != null && exibirParcelamentoCobrancaBancaria.equals("S")){
			if(indicadorParcelamentoCobrancaBancaria == null || indicadorParcelamentoCobrancaBancaria.equals("")){
				throw new ActionServletException("atencao.campo_selecionado.obrigatorio", null, "'Parcelamento de Cobrança Bancária?'");
			}

		}

		// Caso o valor Total do Débito seja negativo não há débitos para parcelar
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

		// [FS0021] - Verificar situação de cobrança
		if(parcelamentoPerfil.getIndicadorChequeDevolvido().equals(ConstantesSistema.SIM)){

			FiltroImovel filtroImovel = new FiltroImovel();
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("cobrancaSituacao");
			filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, codigoImovel));

			Collection imovelCobrancaSituacaoEncontrada = fachada.pesquisar(filtroImovel, Imovel.class.getName());

			// Verifica se o Imóvel tem situação de cobrança
			if(imovelCobrancaSituacaoEncontrada != null && !imovelCobrancaSituacaoEncontrada.isEmpty()){

				CobrancaSituacao cobrancaSituacao = ((Imovel) ((List) imovelCobrancaSituacaoEncontrada).get(0)).getCobrancaSituacao();

				if(cobrancaSituacao != null){
					throw new ActionServletException("atencao.imovel.situacao.cobranca", null, cobrancaSituacao.getDescricao());
				}
			}
		}

		// // [SB0024] – Validar autorização de acesso ao imóvel pelos usuários das empresas de
		// // cobrança administrativa
		// // [UC3062] Validar Autorização Acesso Imóvel - Cobrança Administrativa
		// if(fachada.obterValidacaoAutorizacaoAcessoImovelCobrancaAdministrativa(usuario, idImovel,
		// ConstantesSistema.CODIGO_VALIDACAO_USUARIO_EMPRESA_COBRANCA_ADMINISTRATIVA).equals(ConstantesSistema.NAO)){
		// throw new
		// ActionServletException("atencao.usuario_sem_acesso_dados_cobranca_administrativa",
		// usuario.getLogin(), idImovel
		// .toString());
		// }
		//
		// // [SB0025] – Validar autorização de acesso ao imóvel em cobrança administrativa
		// // pelos usuários da empresa contratante
		// Collection<ContaValoresHelper> colecaoContaValores = (Collection<ContaValoresHelper>)
		// sessao
		// .getAttribute("colecaoContaValoresImovel");
		// Collection<GuiaPagamentoValoresHelper> colecaoGuiaPagamentoValores =
		// (Collection<GuiaPagamentoValoresHelper>) sessao
		// .getAttribute("colecaoGuiaPagamentoValoresImovel");
		// if(fachada.existeContaOuGuiaPagamentoDebitoImovelOuCliente(colecaoContaValores,
		// colecaoGuiaPagamentoValores)){
		// // 1.1. Caso existam, na lista de contas retornada pelo [UC0067], contas em
		// // cobrança administrativa ou caso existam, na lista de guias de pagamento
		// // retornada pelo [UC0067], guias de pagamento em cobrança administrativa
		// // [UC3062] - Validar Autorização Acesso Imóvel Pela Empresa Cobrança
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

		// [FS0049] - Verificar retirada de débitos prescritos do débito do parcelamento
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

		// [FS0050] Verificar imóvel em execução fiscal
		Short indicadorImovelEmExecucaoFiscal = fachada.verificarImovelEmExecucaoFiscal(Integer.valueOf(codigoImovel));
		boolean temPermissaoParcelarEmExecucaoFiscal = fachada.verificarPermissaoEspecial(
						PermissaoEspecial.PARCELAR_IMOVEL_EM_EXECUCAO_FISCAL, usuario);

		if(indicadorImovelEmExecucaoFiscal.equals(Short.valueOf("1")) && !temPermissaoParcelarEmExecucaoFiscal){
			throw new ActionServletException("atencao.usuario_sem_permissao_parcelar_em_execucao_fiscal", codigoImovel,
							usuario.getLogin());
		}

		efetuarParcelamentoDebitosActionForm.set("indicadorImovelEmExecucaoFiscal", Short.toString(indicadorImovelEmExecucaoFiscal));

		// [SB0024] – Validar autorização de acesso ao imóvel pelos usuários das empresas de
		// cobrança administrativa
		Integer resposta = fachada.validarAcessoImovelUsuarioEmpresaCobrancaAdministrativa(usuario, Integer.valueOf(codigoImovel),
						colecaoDebitoImovel);

		if(resposta.equals(Integer.valueOf(1)) || resposta.equals(Integer.valueOf(3))){
			throw new ActionServletException("atencao.usuario_sem_acesso_dados_cobranca_administrativa", usuario.getLogin(), codigoImovel);
		}

		// [SB0025] – Validar autorização de acesso ao imóvel em cobrança administrativa
		// pelos usuários da empresa contratante
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

		// [FS0036] - Verificar permissão para cobrança do parcelamento por guia
		// . Caso o campo “Cobrança do Parcelamento” esteja com a opção “Guia de Pagamento”
		// selecionada.
		if(indicadorCobrancaParcelamento != null
						&& indicadorCobrancaParcelamento.equals(ConstantesSistema.INDICADOR_COBRANCA_PARC_GUIA_PAGAMENTO)){

			// . Caso não seja parcelamento de contas em cobrança bancária (campo “Parcelamento de
			// Cobrança Bancária?” com a opção “Não” selecionada)
			if(indicadorParcelamentoCobrancaBancaria != null && indicadorParcelamentoCobrancaBancaria.equals("2")){
				// . Caso o indicador de permissão de cobrança do parcelamento por meio de guia de
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

		// Verifica se os campos obrigatórios continuam iguais quando houve navegação nas abas
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
			// Caso algum campo tenha mudado limpar a sessão
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

				// Monta a página de confirmação do wizard para perguntar se o usuário quer
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
	 * [FS0025 – Validar data do parcelamento de contas em cobrança bancária]
	 * 
	 * @author Ailton Sousa
	 * @date 20/12/2011
	 * @param dataParcelamento
	 */
	private void validarDataParcelamentoCobrancaBancaria(String dataParcelamento, DynaActionForm form){

		// Caso o campo “Parcelamento de Cobrança Bancária?” esteja com a opção “Sim” selecionada
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
	 * Método que valida se houve alteração nos motivos de revisão na navegação entre as abas 1 e as
	 * outras. Caso tenha alteração, leva pra página de confirmaçãoe recalcula o parcelamento.
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