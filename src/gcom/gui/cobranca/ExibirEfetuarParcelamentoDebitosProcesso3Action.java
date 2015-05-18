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

import gcom.cadastro.geografico.Municipio;
import gcom.cobranca.FiltroResolucaoDiretoriaParametrosPagamentoAVista;
import gcom.cobranca.ResolucaoDiretoriaParametrosPagamentoAVista;
import gcom.cobranca.bean.*;
import gcom.cobranca.parcelamento.Parcelamento;
import gcom.cobranca.parcelamento.ParcelamentoConfiguracaoPrestacao;
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
import gcom.util.ConstantesAplicacao;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.cobranca.ExecutorParametrosCobranca;
import gcom.util.parametrizacao.cobranca.ParametroCobranca;
import gcom.util.parametrizacao.cobranca.parcelamento.ParametroParcelamento;

import java.math.BigDecimal;
import java.util.*;

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
 * Pré-processamento da terceira página
 * 
 * @author Roberta Costa
 * @date 10/03/2006
 * @author eduardo henrique
 * @date 03/02/2009
 *       Alteração para definição de desconto à vista apenas o valor que foi calculado no
 *       perfil de parcelamento.
 */
public class ExibirEfetuarParcelamentoDebitosProcesso3Action
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("processo3");
		Fachada fachada = Fachada.getInstancia();
		HttpSession sessao = httpServletRequest.getSession(false);
		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");
		DynaActionForm efetuarParcelamentoDebitosActionForm = (DynaActionForm) actionForm;

		String dataVencEntrada = httpServletRequest.getParameter("idCampoEnviarDados");

		BigDecimal valorAtualizarComEstorno = BigDecimal.ZERO;

		if("sim".equalsIgnoreCase((String) httpServletRequest.getAttribute("popupEfetuarParcelamento"))
						|| "sim".equalsIgnoreCase((String) httpServletRequest.getParameter("popupEfetuarParcelamento"))){
			httpServletRequest.setAttribute("popupEfetuarParcelamento", "sim");
		}else if(sessao.getAttribute("popupEfetuarParcelamento") != null && sessao.getAttribute("popupEfetuarParcelamento").equals("sim")){
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

		// Habilita o uso do campo valor de entrada
		httpServletRequest.getSession().setAttribute("somenteLeituraValorEntradaInformado", "false");

		// Faz os cálculos quando a entrada for modificada
		String calculaOpcaoParcelamento = null;
		if(httpServletRequest.getParameter("calculaOpcaoParcelamento") != null){
			calculaOpcaoParcelamento = httpServletRequest.getParameter("calculaOpcaoParcelamento");
		}else if(sessao.getAttribute("calculaOpcaoParcelamento") != null){
			calculaOpcaoParcelamento = (String) sessao.getAttribute("calculaOpcaoParcelamento");
		}

		Integer numeroReparcelamentoConsecutivos = 0;

		// Pega variáveis do formulário
		String codigoImovel = (String) efetuarParcelamentoDebitosActionForm.get("matriculaImovel");
		String codigoImovelAntes = (String) efetuarParcelamentoDebitosActionForm.get("codigoImovelAntes");
		Integer situacaoAguaId = Integer.valueOf((String) efetuarParcelamentoDebitosActionForm.get("situacaoAguaId"));
		Integer situacaoEsgotoId = Integer.valueOf((String) efetuarParcelamentoDebitosActionForm.get("situacaoEsgotoId"));
		Integer perfilImovel = Integer.valueOf((String) efetuarParcelamentoDebitosActionForm.get("perfilImovel"));

		String numeroReparcelamentoConsecutivosForm = (String) efetuarParcelamentoDebitosActionForm.get("numeroReparcelamentoConsecutivos");
		if(numeroReparcelamentoConsecutivosForm != null && !numeroReparcelamentoConsecutivosForm.equals("")){
			numeroReparcelamentoConsecutivos = Integer.valueOf(numeroReparcelamentoConsecutivosForm);
		}

		String dataParcelamento = (String) (efetuarParcelamentoDebitosActionForm.get("dataParcelamento"));

		String resolucaoDiretoria = (String) efetuarParcelamentoDebitosActionForm.get("resolucaoDiretoria");
		efetuarParcelamentoDebitosActionForm.set("resolucaoDiretoria", resolucaoDiretoria);

		String inicioIntervaloParcelamento = (String) efetuarParcelamentoDebitosActionForm.get("inicioIntervaloParcelamento");
		String fimIntervaloParcelamento = (String) efetuarParcelamentoDebitosActionForm.get("fimIntervaloParcelamento");
		String indicadorContasRevisao = (String) efetuarParcelamentoDebitosActionForm.get("indicadorContasRevisao");
		String indicadorGuiasPagamento = (String) efetuarParcelamentoDebitosActionForm.get("indicadorGuiasPagamento");
		String indicadorAcrescimosImpotualidade = (String) efetuarParcelamentoDebitosActionForm.get("indicadorAcrescimosImpotualidade");
		String indicadorDebitosACobrar = (String) efetuarParcelamentoDebitosActionForm.get("indicadorDebitosACobrar");
		String indicadorCreditoARealizar = (String) efetuarParcelamentoDebitosActionForm.get("indicadorCreditoARealizar");
		String indicadorParcelamentoCobrancaBancaria = (String) efetuarParcelamentoDebitosActionForm
						.get("indicadorParcelamentoCobrancaBancaria");
		String valorDebitoACobrarParcelamentoImovel = ((String) efetuarParcelamentoDebitosActionForm
						.get("valorDebitoACobrarParcelamentoImovel"));
		int indicadorCalcularAcrescimosSucumbenciaAnterior = 1;

		String dataVencimentoEntradaParcelamento = null;
		if(httpServletRequest.getParameter("idCampoEnviarDados") != null){
			// caso tenha sido chamado pelo calendario pop-up
			dataVencimentoEntradaParcelamento = httpServletRequest.getParameter("idCampoEnviarDados");
			efetuarParcelamentoDebitosActionForm.set("dataVencimentoEntradaParcelamento", dataVencimentoEntradaParcelamento);

		}else if(efetuarParcelamentoDebitosActionForm.get("dataVencimentoEntradaParcelamento") != null
						&& !efetuarParcelamentoDebitosActionForm.get("dataVencimentoEntradaParcelamento").equals("")){
			dataVencimentoEntradaParcelamento = (String) efetuarParcelamentoDebitosActionForm.get("dataVencimentoEntradaParcelamento");
		}

		String valorDebitoTotalOriginal = ((String) efetuarParcelamentoDebitosActionForm.get("valorDebitoTotalOriginal"));

		String[] idsMotivoRevisao = (String[]) efetuarParcelamentoDebitosActionForm.get("idMotivoRevisao");
		Integer[] idsMotivoRevisaoInt = null;
		if(!Util.isVazioOrNulo(idsMotivoRevisao)){
			idsMotivoRevisaoInt = new Integer[idsMotivoRevisao.length];
			for(int i = 0; i < idsMotivoRevisao.length; i++){
				idsMotivoRevisaoInt[i] = Util.obterInteger(idsMotivoRevisao[i]);
			}
		}

		/*
		 * String contendo o 'Id da guia' e o 'número da prestação' separados por '-' de todos os
		 * itens selecionados em tela e concatenados por '$'
		 * (Ex.: 9988-1$9988-2$7766-1)
		 */
		String chavesPrestacoes = (String) efetuarParcelamentoDebitosActionForm.get("chavesPrestacoes");

		String chavesSucumbenciasConta = (String) efetuarParcelamentoDebitosActionForm.get("chavesSucumbenciasConta");
		String chavesSucumbenciasGuia = (String) efetuarParcelamentoDebitosActionForm.get("chavesSucumbenciasGuia");

		BigDecimal valorDebitoACobrarParcelamentoImovelBigDecimal = BigDecimal.ZERO;

		Boolean indicadorContas = true;

		// se o intervalo de parcelamento estiver igual a null
		// não se deve levar em consideração no parcelamento a coleão de contas
		if((inicioIntervaloParcelamento == null || inicioIntervaloParcelamento.equals(""))
						&& (fimIntervaloParcelamento == null || fimIntervaloParcelamento.equals(""))){
			indicadorContas = false;
		}

		// Caso o periodo inicial do intervalo do parcelamento não seja informado
		if(inicioIntervaloParcelamento == null || inicioIntervaloParcelamento.equals("")){
			inicioIntervaloParcelamento = "01/0001";
		}

		// Caso o periodo final do intervalo do parcelamento não seja informado
		if(fimIntervaloParcelamento == null || fimIntervaloParcelamento.equals("")){
			fimIntervaloParcelamento = "12/9999";
		}

		Integer fimIntervaloParcelamentoFormatado = Util.formatarMesAnoComBarraParaAnoMes(fimIntervaloParcelamento);
		Integer inicioIntervaloParcelamentoFormatado = Util.formatarMesAnoComBarraParaAnoMes(inicioIntervaloParcelamento);

		if(valorDebitoACobrarParcelamentoImovel != null && !valorDebitoACobrarParcelamentoImovel.trim().equalsIgnoreCase("")){
			valorDebitoACobrarParcelamentoImovelBigDecimal = Util.formatarMoedaRealparaBigDecimal(valorDebitoACobrarParcelamentoImovel);
		}

		IndicadoresParcelamentoHelper indicadoresParcelamentoHelper = new IndicadoresParcelamentoHelper();
		indicadoresParcelamentoHelper.setIndicadorContasRevisao(Integer.valueOf(indicadorContasRevisao));
		indicadoresParcelamentoHelper.setIndicadorDebitosACobrar(Integer.valueOf(indicadorDebitosACobrar));
		indicadoresParcelamentoHelper.setIndicadorCreditoARealizar(Integer.valueOf(indicadorCreditoARealizar));
		indicadoresParcelamentoHelper.setIndicadorGuiasPagamento(Integer.valueOf(indicadorGuiasPagamento));
		indicadoresParcelamentoHelper.setIndicadorAcrescimosImpotualidade(Integer.valueOf(indicadorAcrescimosImpotualidade));
		if(!Util.isVazioOuBranco(indicadorParcelamentoCobrancaBancaria)){
			indicadoresParcelamentoHelper.setIndicadorCobrancaBancaria(Integer.valueOf(indicadorParcelamentoCobrancaBancaria));
		}

		BigDecimal valorCreditoARealizar = BigDecimal.ZERO;

		// Valor Entrada Informado
		BigDecimal valorEntradaInformado = null;
		BigDecimal valorEntradaInformadoAntes = BigDecimal.ZERO;

		if(efetuarParcelamentoDebitosActionForm.get("valorEntradaInformado") != null
						&& !efetuarParcelamentoDebitosActionForm.get("valorEntradaInformado").equals("")
						&& !efetuarParcelamentoDebitosActionForm.get("valorEntradaInformado").equals("0.00")){

			if(efetuarParcelamentoDebitosActionForm.get("valorEntradaInformado").equals("0")){
				valorEntradaInformado = BigDecimal.ZERO;
				efetuarParcelamentoDebitosActionForm.set("valorEntradaInformado", Util.formatarMoedaReal(valorEntradaInformado));
				// efetuarParcelamentoDebitosActionForm.set("dataVencimentoEntradaParcelamento",
				// "");
			}else{
				valorEntradaInformado = Util.formatarMoedaRealparaBigDecimal((String) efetuarParcelamentoDebitosActionForm
								.get("valorEntradaInformado"));
				dataVencEntrada = (String) efetuarParcelamentoDebitosActionForm.get("dataVencimentoEntradaParcelamento");
			}

		}
		if(efetuarParcelamentoDebitosActionForm.get("valorEntradaInformadoAntes") != null
						&& !efetuarParcelamentoDebitosActionForm.get("valorEntradaInformadoAntes").equals("")
						&& !efetuarParcelamentoDebitosActionForm.get("valorEntradaInformadoAntes").equals("0.00")){
			valorEntradaInformadoAntes = Util.formatarMoedaRealparaBigDecimal((String) efetuarParcelamentoDebitosActionForm
							.get("valorEntradaInformadoAntes"));
		}

		// O indicador só será usado caso a situação de Água do Imóvel seja
		// SUPRIMIDO, SUPRIMIDO PARCIAL, SUPRIMIDO PARCIAL A PEDIDO
		Integer indicadorRestabelecimento = Integer.valueOf("0");
		if(efetuarParcelamentoDebitosActionForm.get("indicadorRestabelecimento") != null
						&& !efetuarParcelamentoDebitosActionForm.get("indicadorRestabelecimento").equals("")){
			indicadorRestabelecimento = Integer.valueOf(String.valueOf(efetuarParcelamentoDebitosActionForm
							.get("indicadorRestabelecimento")));
		}

		// Verifica se a chamada é pela aba 2(colecaoContaValores) ou pela aba
		// 1(colecaoContaValoresImovel)
		Collection<ContaValoresHelper> colecaoContaValoresNegociacao = null;
		Collection<GuiaPagamentoValoresHelper> colecaoGuiaPagamentoHelper = null;

		BigDecimal valorDebitoTotalAtualizado = BigDecimal.ZERO;
		BigDecimal valorAcrescimosImpontualidade = BigDecimal.ZERO;
		BigDecimal valorAtualizacaoMonetariaSucumbencia = BigDecimal.ZERO;
		BigDecimal valorJurosMoraSucumbencia = BigDecimal.ZERO;
		BigDecimal valorSucumbenciaAnterior = BigDecimal.ZERO;

		if(sessao.getAttribute("colecaoContaValores") != null){
			colecaoContaValoresNegociacao = (Collection<ContaValoresHelper>) sessao.getAttribute("colecaoContaValores");

			colecaoGuiaPagamentoHelper = (Collection<GuiaPagamentoValoresHelper>) sessao
							.getAttribute("colecaoGuiaPagamentoValoresSelecionadas");

			valorDebitoTotalAtualizado = BigDecimal.ZERO;

			if(!((String) efetuarParcelamentoDebitosActionForm.get("valorDebitoTotalAtualizadoSemEstorno")).equals("")){

				valorDebitoTotalAtualizado = Util.formatarMoedaRealparaBigDecimal((String) efetuarParcelamentoDebitosActionForm
								.get("valorDebitoTotalAtualizadoSemEstorno"));

			}else{
				valorDebitoTotalAtualizado = Util.formatarMoedaRealparaBigDecimal((String) efetuarParcelamentoDebitosActionForm
								.get("valorDebitoTotalAtualizado"));
			}

			if(!((String) efetuarParcelamentoDebitosActionForm.get("valorAcrescimosImpontualidade")).equals("")){
				valorAcrescimosImpontualidade = Util.formatarMoedaRealparaBigDecimal((String) efetuarParcelamentoDebitosActionForm
								.get("valorAcrescimosImpontualidade"));
			}

			if(!((String) efetuarParcelamentoDebitosActionForm.get("valorCreditoARealizar")).equals("")){
				valorCreditoARealizar = Util.formatarMoedaRealparaBigDecimal((String) efetuarParcelamentoDebitosActionForm
								.get("valorCreditoARealizar"));
			}

		}else{
			// Pesquisa os débitos do imóvel
			Object[] debitosImovel = fachada.pesquisarDebitosImovel(codigoImovel, codigoImovelAntes, dataParcelamento, resolucaoDiretoria,
							fimIntervaloParcelamento, inicioIntervaloParcelamento, indicadorContasRevisao, indicadorGuiasPagamento,
							indicadorAcrescimosImpotualidade, indicadorDebitosACobrar, indicadorCreditoARealizar, indicadorContas,
							chavesPrestacoes);

			// Valores dos indíces definidos no controlador
			colecaoContaValoresNegociacao = (Collection<ContaValoresHelper>) debitosImovel[0];
			colecaoGuiaPagamentoHelper = (Collection<GuiaPagamentoValoresHelper>) debitosImovel[2];
			// valorDebitoTotalAtualizado = (BigDecimal) debitosImovel[14];

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

			Short indicadorNaoConsiderarPagamentoNaoClassificado = 2;

			// Obter todo o débito do imóvel para exibição na ABA 4(Conclusão)
			// ou para inserir a partir da aba 3
			ObterDebitoImovelOuClienteHelper colecaoDebitoCliente = fachada.obterDebitoImovelOuCliente(
							indicadorDebito, // Indicador
							// de débito do imóvel
							codigoImovel, // Matrícula do imóvel
							idClienteDebito, // Código do cliente
							idRelacaoClienteDebito, // Tipo de relação cliente imóvel
							Util.formatarMesAnoParaAnoMesSemBarra(inicioIntervaloParcelamento), // Referência
							// inicial do débito
							Util.formatarMesAnoParaAnoMesSemBarra(fimIntervaloParcelamento), // Fim
							// do débito
							Util.converteStringParaDate("01/01/0001"), // Inicio vencimento
							Util.converteStringParaDate("31/12/9999"), // Fim vencimento
							1, // Indicador de pagamento
							Integer.valueOf(indicadorContasRevisao), // conta em revisão
							Integer.valueOf(indicadorDebitosACobrar), // Débito a cobrar
							Integer.valueOf(indicadorCreditoARealizar), // crédito a realizar
							1, // Indicador de notas promissórias
							Integer.valueOf(indicadorGuiasPagamento), // guias pagamento
							Integer.valueOf(indicadorAcrescimosImpotualidade), // acréscimos
							// impontualidade
							indicadorContas, null, null, null, indicadorNaoConsiderarPagamentoNaoClassificado, ConstantesSistema.SIM,
							ConstantesSistema.SIM, ConstantesSistema.SIM, indicadorCalcularAcrescimosSucumbenciaAnterior, null);

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

			if(colecaoDebitoCliente.getIdBoletoBancario() != null){
				// sessao.setAttribute("idBoletoBancario",
				// colecaoDebitoCliente.getIdBoletoBancario());
			}
			// Para o cálculo do Débito Total Atualizado
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
			BigDecimal valorRestanteACobrar = BigDecimal.ZERO;
			BigDecimal valorAtualizacaoMonetaria = BigDecimal.ZERO;
			BigDecimal valorJurosMora = BigDecimal.ZERO;
			BigDecimal valorMulta = BigDecimal.ZERO;

			// Dados do Débito do Imóvel - Contas
			Collection<ContaValoresHelper> colecaoContaValores = colecaoDebitoCliente.getColecaoContasValores();

			// [FS0044] Retirar Débitos Não Vencidos
			colecaoContaValores = fachada.retirarContasNaoVencidas(colecaoContaValores);
			colecaoContaValoresNegociacao = fachada.retirarContasNaoVencidas(colecaoContaValoresNegociacao);

			// Retirar as contas com Motivo de Revisão com Imedimento, se for o caso
			colecaoContaValores = fachada.retirarContasMotivoRevisaoComImpedimentoParcelamento(colecaoContaValores, usuario);
			colecaoContaValoresNegociacao = fachada.retirarContasMotivoRevisaoComImpedimentoParcelamento(colecaoContaValoresNegociacao,
							usuario);

			Collection<GuiaPagamentoValoresHelper> colecaoGuiasPagamentoValores = colecaoDebitoCliente.getColecaoGuiasPagamentoValores();

			ParcelamentoPerfil parcelamentoPerfil = (ParcelamentoPerfil) sessao.getAttribute("parcelamentoPerfil");

			// [SB0011] Verificar Única Fatura
			fachada.verificarUnicaFatura(colecaoContaValores, parcelamentoPerfil);

			// [FS0040] - Verificar existência de itens de parcelamento anterior na mesma
			// referência
			Collection<DebitoACobrar> colecaoDebitoACobrarAux = colecaoDebitoCliente.getColecaoDebitoACobrar();

			Collection<CreditoARealizar> colecaoCreditoARealizarAux = colecaoDebitoCliente.getColecaoCreditoARealizar();

			fachada.verificarExistenciaDeItensDeParcelamentoAnteriorNaMesmaReferencia(Integer.valueOf(codigoImovel), colecaoContaValores,
							colecaoGuiasPagamentoValores, colecaoDebitoACobrarAux, colecaoCreditoARealizarAux);

			Integer idBoletoNegociacao = null;

			if(sessao.getAttribute("idBoletoBancario") != null && !sessao.getAttribute("idBoletoBancario").equals("")){
				idBoletoNegociacao = (Integer) sessao.getAttribute("idBoletoBancario");
			}

			// 3.1.2.2. Caso não seja possível o parcelamento de cobrança bancária
			// (PASI_DSPARAMETRO com o valor 2 (dois) na tabela PARAMETRO_SISTEMA para
			// PASI_DSPARAMETRO=”P_PARCELAMENTO_COBRANCA_BANCARIA”) OU caso seja possível o
			// parcelamento de cobrança bancária (PASI_DSPARAMETRO com o valor 1 (sim) na tabela
			// PARAMETRO_SISTEMA para PASI_DSPARAMETRO=”P_PARCELAMENTO_COBRANCA_BANCARIA”) e o
			// campo “Parcelamento de Cobrança Bancária?” esteja com a opção “NÃO” selecionada,
			// após a chamada da rotina de obter débito, as contas que estejam em revisão por
			// cobrança bancária (contas com CMRV_ID com o valor correspondente à “COBRANÇA
			// BANCÁRIA”) deverão ser desprezadas.
			// *********************************************************************************
			// 3.1.2.3. Caso o indicador de conta em revisão seja “SIM” e tenha sido selecionado
			// algum motivo de revisão, após a chamada da rotina de obter débito, as contas com
			// motivo de revisão diferente do(s) motivo(s) selecionado(s) deverão ser
			// desprezadas (contas com CMRV_ID diferente do(s) motivo(s) selecionado(s)).
			// Filtra logo pelos motivos de revisão escolhidos no filtro e depois, se for o
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

					// 3.1.2.1. Caso seja possível o parcelamento de cobrança bancária
					// (PASI_DSPARAMETRO com o valor 1 (sim) na tabela PARAMETRO_SISTEMA para
					// PASI_DSPARAMETRO=”P_PARCELAMENTO_COBRANCA_BANCARIA”) e o campo
					// “Parcelamento de Cobrança Bancária?” esteja com a opção “SIM”
					// selecionada, após a chamada da rotina de obter débito, as contas que não
					// estejam em revisão (contas com CMRV_ID com o valor nulo) e as contas que
					// não estejam associadas ao boleto selecionado para negociação (CNTA_ID
					// diferente de da tabela CONTA com CNTA_ID=CNTA_ID da tabela
					// COBRANCA_DOCUMENTO_ITEM com CBDO_ID=CBDO_ID da tabela BOLETO_BANCARIO com
					// BBCO_ID=Id do boleto bancário selecionado para negociação) deverão ser
					// desprezadas.
					if(indicadorParcelamentoCobrancaBancaria != null && indicadorParcelamentoCobrancaBancaria.equals("1")){

						Conta conta = contaValoresHelper.getConta();

						// Só considera e calcula as contas que estejam em revisão
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

							BigDecimal[] arrayValores = ContaValoresHelper.retornarArrayValores(contaValoresHelper);

							valorTotalContas = valorTotalContas.add(arrayValores[0]);
							valorAtualizacaoMonetaria = valorAtualizacaoMonetaria.add(arrayValores[1]).add(arrayValores[4]);
							valorJurosMora = valorJurosMora.add(arrayValores[2]).add(arrayValores[5]);
							valorMulta = valorMulta.add(arrayValores[3]);
							valorAtualizacaoMonetariaSucumbencia = valorAtualizacaoMonetariaSucumbencia.add(arrayValores[4]);
							valorJurosMoraSucumbencia = valorJurosMoraSucumbencia.add(arrayValores[5]);
							valorSucumbenciaAnterior = valorSucumbenciaAnterior.add(arrayValores[6]);
							valorTotalAcrescimoImpontualidadeContas = valorTotalAcrescimoImpontualidadeContas.add(arrayValores[7]);

						}
					}else if((indicadorParcelamentoCobrancaBancariaParametro != null && indicadorParcelamentoCobrancaBancariaParametro
									.equals(2))
									|| (indicadorParcelamentoCobrancaBancariaParametro != null
													&& indicadorParcelamentoCobrancaBancariaParametro.equals(1)
													&& indicadorParcelamentoCobrancaBancaria != null && indicadorParcelamentoCobrancaBancaria
													.equals("2"))){

						Conta conta = contaValoresHelper.getConta();

						if(conta != null && conta.getContaMotivoRevisao() != null && conta.getContaMotivoRevisao().getId() != null
										&& conta.getContaMotivoRevisao().getId().equals(ContaMotivoRevisao.REVISAO_POR_COBRANCA_BANCARIA)){
							contaValores.remove();

						}else{

							BigDecimal[] arrayValores = ContaValoresHelper.retornarArrayValores(contaValoresHelper);

							valorTotalContas = valorTotalContas.add(arrayValores[0]);
							valorAtualizacaoMonetaria = valorAtualizacaoMonetaria.add(arrayValores[1]).add(arrayValores[4]);
							valorJurosMora = valorJurosMora.add(arrayValores[2]).add(arrayValores[5]);
							valorMulta = valorMulta.add(arrayValores[3]);
							valorAtualizacaoMonetariaSucumbencia = valorAtualizacaoMonetariaSucumbencia.add(arrayValores[4]);
							valorJurosMoraSucumbencia = valorJurosMoraSucumbencia.add(arrayValores[5]);
							valorSucumbenciaAnterior = valorSucumbenciaAnterior.add(arrayValores[6]);
							valorTotalAcrescimoImpontualidadeContas = valorTotalAcrescimoImpontualidadeContas.add(arrayValores[7]);
						}

					}else{
						BigDecimal[] arrayValores = ContaValoresHelper.retornarArrayValores(contaValoresHelper);

						valorTotalContas = valorTotalContas.add(arrayValores[0]);
						valorAtualizacaoMonetaria = valorAtualizacaoMonetaria.add(arrayValores[1]).add(arrayValores[4]);
						valorJurosMora = valorJurosMora.add(arrayValores[2]).add(arrayValores[5]);
						valorMulta = valorMulta.add(arrayValores[3]);
						valorAtualizacaoMonetariaSucumbencia = valorAtualizacaoMonetariaSucumbencia.add(arrayValores[4]);
						valorJurosMoraSucumbencia = valorJurosMoraSucumbencia.add(arrayValores[5]);
						valorSucumbenciaAnterior = valorSucumbenciaAnterior.add(arrayValores[6]);
						valorTotalAcrescimoImpontualidadeContas = valorTotalAcrescimoImpontualidadeContas.add(arrayValores[7]);
					}

				}
				efetuarParcelamentoDebitosActionForm.set("valorTotalContaValores", Util.formatarMoedaReal(valorTotalContas));

				sessao.setAttribute("valorTotalContaValores", valorTotalContas);
				// Pega os dados do Débito do Cliente
				if(sessao.getAttribute("colecaoContaValores") == null){
					sessao.setAttribute("colecaoContaValores", colecaoContaValores);
				}
			}else{
				efetuarParcelamentoDebitosActionForm.set("valorTotalContaValores", "0,00");
				sessao.setAttribute("valorTotalContaValores", valorTotalContas);

				sessao.removeAttribute("colecaoContaValores");
				sessao.removeAttribute("colecaoContaValoresImovel");
			}

			// Guias de Pagamento
			if(indicadorGuiasPagamento.equals("1")){
				Collection<GuiaPagamentoValoresHelper> colecaoGuiaPagamentoValores = colecaoDebitoCliente.getColecaoGuiasPagamentoValores();

				// [FS0037] - Verificar existência de guia de parcelamento de cobrança bancária
				// [FS0041] - Verificar existência de guias correspondentes a prestações de
				// parcelamento com concessão de desconto nos acréscimos
				if(!Util.isVazioOrNulo(colecaoGuiaPagamentoValores)){
					fachada.removerGuiaDePagamentoNaComposicaoDoDebitoParcelamennto(colecaoGuiaPagamentoValores);
				}

				if(colecaoGuiaPagamentoValores != null && !colecaoGuiaPagamentoValores.isEmpty()){

					Collection<GuiaPagamentoValoresHelper> colecaoGuiaPagamentoValoresSelecionadas = fachada
									.retornarGuiaPagamentoValoresSelecionadas(chavesPrestacoes, colecaoGuiaPagamentoValores);

					BigDecimal atualizacaoSucumbencia = fachada.calcularValoresGuia(colecaoGuiaPagamentoValoresSelecionadas,
									ConstantesSistema.PARCELAMENTO_VALOR_GUIA_ATUALIZACAO_MONETARIA_SUCUMBENCIA);
					BigDecimal jurosMoraSucumbencia = fachada.calcularValoresGuia(colecaoGuiaPagamentoValoresSelecionadas,
									ConstantesSistema.PARCELAMENTO_VALOR_GUIA_JUROS_MORA_SUCUMBENCIA);

					valorTotalGuiasPagamento = valorTotalGuiasPagamento.add(fachada.calcularValoresGuia(
									colecaoGuiaPagamentoValoresSelecionadas, ConstantesSistema.PARCELAMENTO_VALOR_GUIA_TOTAL));
					valorAtualizacaoMonetaria = valorAtualizacaoMonetaria.add(
									fachada.calcularValoresGuia(colecaoGuiaPagamentoValoresSelecionadas,
													ConstantesSistema.PARCELAMENTO_VALOR_GUIA_ATUALIZACAO_MONETARIA)).add(
									atualizacaoSucumbencia);
					valorJurosMora = valorJurosMora.add(fachada.calcularValoresGuia(colecaoGuiaPagamentoValoresSelecionadas,
													ConstantesSistema.PARCELAMENTO_VALOR_GUIA_JUROS_MORA)).add(jurosMoraSucumbencia);
					valorMulta = valorMulta.add(fachada.calcularValoresGuia(colecaoGuiaPagamentoValoresSelecionadas,
									ConstantesSistema.PARCELAMENTO_VALOR_GUIA_MULTA));
					valorTotalAcrescimoImpontualidadeGuias = valorTotalAcrescimoImpontualidadeGuias.add(fachada.calcularValoresGuia(
									colecaoGuiaPagamentoValoresSelecionadas,
									ConstantesSistema.PARCELAMENTO_VALOR_GUIA_ACRESCIMO_IMPONTUALIDADE));
					valorAtualizacaoMonetariaSucumbencia = valorAtualizacaoMonetariaSucumbencia.add(atualizacaoSucumbencia);
					valorJurosMoraSucumbencia = valorJurosMoraSucumbencia.add(jurosMoraSucumbencia);
					valorSucumbenciaAnterior = valorSucumbenciaAnterior.add(fachada.calcularValoresGuia(
									colecaoGuiaPagamentoValoresSelecionadas,
									ConstantesSistema.PARCELAMENTO_VALOR_GUIA_SUCUMBENCIA));

					// valorTotalGuiasPagamento =
					// valorTotalGuiasPagamento.add(fachada.calcularValoresGuia(
					// colecaoGuiaPagamentoValoresSelecionadas,
					// ConstantesSistema.PARCELAMENTO_VALOR_GUIA_TOTAL));
					// valorAtualizacaoMonetaria =
					// valorAtualizacaoMonetaria.add(fachada.calcularValoresGuia(
					// colecaoGuiaPagamentoValoresSelecionadas,
					// ConstantesSistema.PARCELAMENTO_VALOR_GUIA_ATUALIZACAO_MONETARIA));
					// valorJurosMora =
					// valorJurosMora.add(fachada.calcularValoresGuia(colecaoGuiaPagamentoValoresSelecionadas,
					// ConstantesSistema.PARCELAMENTO_VALOR_GUIA_JUROS_MORA));
					// valorMulta =
					// valorMulta.add(fachada.calcularValoresGuia(colecaoGuiaPagamentoValoresSelecionadas,
					// ConstantesSistema.PARCELAMENTO_VALOR_GUIA_MULTA));
					// valorTotalAcrescimoImpontualidadeGuias =
					// valorTotalAcrescimoImpontualidadeGuias.add(fachada.calcularValoresGuia(
					// colecaoGuiaPagamentoValoresSelecionadas,
					// ConstantesSistema.PARCELAMENTO_VALOR_GUIA_ACRESCIMO_IMPONTUALIDADE));

					efetuarParcelamentoDebitosActionForm.set("valorGuiasPagamento", Util.formatarMoedaReal(valorTotalGuiasPagamento));

					// Pega as Guias de Pagamento em Débito
					sessao.setAttribute("colecaoGuiaPagamentoValoresSelecionadas", colecaoGuiaPagamentoValoresSelecionadas);
					sessao.removeAttribute("colecaoGuiaPagamentoValoresImovel");
				}else{
					efetuarParcelamentoDebitosActionForm.set("valorGuiasPagamento", "0,00");

					sessao.removeAttribute("colecaoGuiaPagamentoValoresSelecionadas");
					sessao.removeAttribute("colecaoGuiaPagamentoValoresImovel");
				}
			}else{
				efetuarParcelamentoDebitosActionForm.set("valorGuiasPagamento", "0,00");

				sessao.removeAttribute("colecaoGuiaPagamentoValoresSelecionadas");
				sessao.removeAttribute("colecaoGuiaPagamentoValoresImovel");
			}

			// Acrescimos por Impotualidade
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

			// Para o cálculo do Débito Total Atualizado
			valorTotalAcrescimoImpontualidade = retornoSoma;
			valorAcrescimosImpontualidade = valorTotalAcrescimoImpontualidade;

			// Debitos A Cobrar
			if(indicadorDebitosACobrar.equals("1")){
				// [FS0022]-Verificar existência de juros sobre imóvel
				Collection<DebitoACobrar> colecaoDebitoACobrar = colecaoDebitoCliente.getColecaoDebitoACobrar();
				if(colecaoDebitoACobrar != null && !colecaoDebitoACobrar.isEmpty()){
					Iterator<DebitoACobrar> debitoACobrarValores = colecaoDebitoACobrar.iterator();

					final int indiceCurtoPrazo = 0;
					final int indiceLongoPrazo = 1;

					// Parâmetro que contém os tipos de financiamentos de parcelamento
					String[] parametroFinanciamentoTipoParcelamento = null;
					ArrayList<Integer> colecaoFinanciamentosTiposParcelamento = new ArrayList<Integer>();

					try{
						// Recupera os valores do parâmetro
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

						// [FS0022]-Verificar existência de juros sobre imóvel
						if(debitoACobrar.getDebitoTipo().getId() != null
										&& !debitoACobrar.getDebitoTipo().getId().equals(DebitoTipo.JUROS_SOBRE_PARCELAMENTO)){

							if(!Util.isVazioOrNulo(colecaoFinanciamentosTiposParcelamento)){
								// Debitos A Cobrar - Serviço
								// Caso o financiamento tipo não seja de parcelamento
								// Acumula como Serviço
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

							if(debitoACobrar.getDebitoTipo().getId().equals(DebitoTipo.SUCUMBENCIA)){
								valorSucumbenciaAnterior = valorSucumbenciaAnterior.add(debitoACobrar.getValorTotal());
							}
						}
					}

					sessao.setAttribute("colecaoDebitoACobrar", colecaoDebitoACobrar);
					sessao.removeAttribute("colecaoDebitoACobrarImovel");

					// Serviços
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

					// Está no hidden no formulário
					efetuarParcelamentoDebitosActionForm.set("valorDebitoACobrarServicoLongoPrazo", Util
									.formatarMoedaReal(valorTotalRestanteServicosACobrarLongoPrazo));
					// Está no hidden no formulário
					efetuarParcelamentoDebitosActionForm.set("valorDebitoACobrarServicoCurtoPrazo", Util
									.formatarMoedaReal(valorTotalRestanteServicosACobrarCurtoPrazo));

					// Está no hidden no formulário
					efetuarParcelamentoDebitosActionForm.set("valorDebitoACobrarParcelamentoLongoPrazo", Util
									.formatarMoedaReal(valorTotalRestanteParcelamentosACobrarLongoPrazo));
					// Está no hidden no formulário
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

			efetuarParcelamentoDebitosActionForm.set("valorAtualizacaoMonetariaSucumbenciaImovel",
							Util.formatarMoedaReal(valorAtualizacaoMonetariaSucumbencia));
			efetuarParcelamentoDebitosActionForm.set("valorJurosMoraSucumbenciaImovel", Util.formatarMoedaReal(valorJurosMoraSucumbencia));
			efetuarParcelamentoDebitosActionForm.set("valorMultaSucumbenciaImovel", "0,00");
			efetuarParcelamentoDebitosActionForm.set("valorAcrescimosSucumbenciaImovel",
							Util.formatarMoedaReal(valorJurosMoraSucumbencia.add(valorAtualizacaoMonetariaSucumbencia)));
			efetuarParcelamentoDebitosActionForm.set("valorTotalSucumbenciaImovel", Util.formatarMoedaReal(valorSucumbenciaAnterior));

			// Crédito A Realizar
			if(indicadorCreditoARealizar.equals("1")){
				Collection<CreditoARealizar> colecaoCreditoARealizarInicial = colecaoDebitoCliente.getColecaoCreditoARealizar();

				// [FS0042] - Verificar existência de créditos a realizar correspondentes a
				// desconto
				// nos acréscimos concedido no parcelamento
				// ----------------------------------------------------------------------------------------------
				Collection colecaoCreditoARealizar = fachada.verificarCreditoARealiazarComConcessaoDesconto(colecaoCreditoARealizarInicial);

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

			// Débito Total Atualizado
			// BigDecimal debitoTotalAtualizado = BigDecimal.ZERO;
			valorDebitoTotalAtualizado.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
			valorDebitoTotalAtualizado = valorDebitoTotalAtualizado.add(valorTotalContas);
			valorDebitoTotalAtualizado = valorDebitoTotalAtualizado.add(valorTotalGuiasPagamento);
			valorDebitoTotalAtualizado = valorDebitoTotalAtualizado.add(valorTotalAcrescimoImpontualidade);
			valorDebitoTotalAtualizado = valorDebitoTotalAtualizado.add(valorTotalRestanteServicosACobrar);
			valorDebitoTotalAtualizado = valorDebitoTotalAtualizado.add(valorTotalRestanteParcelamentosACobrar);
			valorDebitoTotalAtualizado = valorDebitoTotalAtualizado.subtract(valorCreditoARealizar);

		}

		Short pIndicadorPermissaoEspecialEfetivacaoParcelamento = ConstantesSistema.NUMERO_NAO_INFORMADO;
		try{
			pIndicadorPermissaoEspecialEfetivacaoParcelamento = Util
							.converterStringParaShort((String) ParametroParcelamento.P_INDICADOR_PERMISSAO_ESPECIAL_EFETIVACAO_PARCELAMENTO
											.executar());
		}catch(ControladorException e){
			throw new ActionServletException("atencao.sistemaparametro_inexistente", null,
							"P_INDICADOR_PERMISSAO_ESPECIAL_EFETIVACAO_PARCELAMENTO");
		}

		if(pIndicadorPermissaoEspecialEfetivacaoParcelamento.equals(ConstantesSistema.SIM)){
			if(!fachada.verificarPermissaoEspecial(PermissaoEspecial.EFETIVAR_PARCELAMENTO, usuario)){
				sessao.setAttribute("bloquearEfetivarParcelamento", "S");
			}
		}

		// Variáveis
		BigDecimal valorTotalMultas = BigDecimal.ZERO;
		BigDecimal valorTotalJurosMora = BigDecimal.ZERO;
		BigDecimal valorTotalAtualizacoesMonetarias = BigDecimal.ZERO;
		BigDecimal descontoAcrescimosImpontualidade = BigDecimal.ZERO;
		BigDecimal descontoAntiguidadeDebito = BigDecimal.ZERO;
		BigDecimal descontoInatividadeLigacaoAgua = BigDecimal.ZERO;
		BigDecimal percentualDescontoAcrescimosImpontualidade = BigDecimal.ZERO;
		BigDecimal percentualDescontoAntiguidadeDebito = BigDecimal.ZERO;
		BigDecimal percentualDescontoInatividadeLigacaoAgua = BigDecimal.ZERO;
		BigDecimal valorDescontoPagamentoAVista = BigDecimal.ZERO;
		BigDecimal valorPagamentoAVista = BigDecimal.ZERO;
		ParcelamentoPerfil parcelamentoPerfil = null;
		BigDecimal valorMinimoPrestacao = BigDecimal.ZERO;
		BigDecimal valorTotalImpostosConta = BigDecimal.ZERO;
		BigDecimal descontoSancoesRDEspecial = BigDecimal.ZERO;
		BigDecimal descontoTarifaSocialRDEspecial = BigDecimal.ZERO;
		BigDecimal descontoPagamentoAVistaRDEspecial = BigDecimal.ZERO;
		Integer numeroDiasVencimentoDaEntrada = null;
		boolean existeValorDescontoNaPrestacao = false;
		BigDecimal valorEstornoDescontos = BigDecimal.ZERO;
		
		Map<Integer, BigDecimal> mapProcessos = fachada.determinarValorSucumbenciaAtual(Integer.valueOf(codigoImovel),
						colecaoContaValoresNegociacao, colecaoGuiaPagamentoHelper, chavesSucumbenciasConta, chavesSucumbenciasGuia);
		BigDecimal valorSucumbenciaAtualCalculado = Util.somarMapBigDecimal(mapProcessos);

		BigDecimal valorSucumbenciaAtualCalculadoAnterior = (BigDecimal) sessao.getAttribute("valorSucumbenciaAtualCalculadoAnterior");

		boolean indicadorCalculoAlterado = false;
		if(valorSucumbenciaAtualCalculadoAnterior != null){
			if(valorSucumbenciaAtualCalculado.compareTo(valorSucumbenciaAtualCalculadoAnterior) != 0){
				indicadorCalculoAlterado = true;
			}
		}
		sessao.setAttribute("valorSucumbenciaAtualCalculadoAnterior", valorSucumbenciaAtualCalculado);

		BigDecimal valorSucumbenciaAtualTela = null;
		String valorSucumbenciaAtualStr = (String) efetuarParcelamentoDebitosActionForm.get("valorSucumbenciaAtual");
		if(Util.isVazioOuBranco(valorSucumbenciaAtualStr)){
			valorSucumbenciaAtualTela = valorSucumbenciaAtualCalculado;
		}else{
			valorSucumbenciaAtualTela = Util.formatarMoedaRealparaBigDecimal(valorSucumbenciaAtualStr);
		}

		BigDecimal valorSucumbenciaAtual = null;
		if(indicadorCalculoAlterado){
			valorSucumbenciaAtual = valorSucumbenciaAtualCalculado;
		}else{
			valorSucumbenciaAtual = valorSucumbenciaAtualTela;
		}
		efetuarParcelamentoDebitosActionForm.set("valorSucumbenciaAtual", Util.formatarMoedaReal(valorSucumbenciaAtual));
		
		sessao.setAttribute("mapProcessos", mapProcessos);

		// Map<Integer, BigDecimal> mapProcessosValorSucumbenciaDistribuido =
		// fachada.distribuirValorEntreProcessosExecucaoFiscal(valorSucumbenciaAtual, mapProcessos,
		// Integer.valueOf(codigoImovel));

		BigDecimal valorSucumbenciaTotal = BigDecimal.ZERO;
		String valorAcrescimosSucumbenciaImovelStr = (String) efetuarParcelamentoDebitosActionForm.get("valorAcrescimosSucumbenciaImovel");
		if(!Util.isVazioOuBranco(valorAcrescimosSucumbenciaImovelStr)){
			valorSucumbenciaTotal = valorSucumbenciaTotal.add(Util.formatarMoedaRealparaBigDecimal(valorAcrescimosSucumbenciaImovelStr));
		}
		String valorTotalSucumbenciaImovelStr = (String) efetuarParcelamentoDebitosActionForm.get("valorTotalSucumbenciaImovel");
		if(!Util.isVazioOuBranco(valorTotalSucumbenciaImovelStr)){
			valorSucumbenciaTotal = valorSucumbenciaTotal.add(Util.formatarMoedaRealparaBigDecimal(valorTotalSucumbenciaImovelStr));
		}
		valorSucumbenciaTotal = valorSucumbenciaTotal.add(valorSucumbenciaAtual);

		String quantidadeParcelasSucumbenciaStr = (String) efetuarParcelamentoDebitosActionForm.get("quantidadeParcelasSucumbencia");
		if(valorSucumbenciaTotal.compareTo(BigDecimal.ZERO) == 0){
			quantidadeParcelasSucumbenciaStr = "0";
		}else if(Util.isVazioOuBranco(quantidadeParcelasSucumbenciaStr) || quantidadeParcelasSucumbenciaStr.equals("0")){
			quantidadeParcelasSucumbenciaStr = "1";
		}

		Integer quantidadeParcelasSucumbencia = Integer.valueOf(quantidadeParcelasSucumbenciaStr);
		efetuarParcelamentoDebitosActionForm.set("quantidadeParcelasSucumbencia", quantidadeParcelasSucumbenciaStr);

		if(fachada.verificarPermissaoEspecial(PermissaoEspecial.ALTERAR_VALORES_PADROES_SUCUMBENCIA_EXECUCAO_FISCAL, usuario)
						&& valorSucumbenciaAtual.compareTo(BigDecimal.ZERO) != 0){
			sessao.setAttribute("permitirAlterarValoresSucumbenciaExecucaoFiscal", "S");
		}else{
			sessao.removeAttribute("permitirAlterarValoresSucumbenciaExecucaoFiscal");
		}

		String valorDiligenciasStr = (String) efetuarParcelamentoDebitosActionForm.get("valorDiligencias");
		BigDecimal valorDiligencias = BigDecimal.ZERO;
		if(Util.isVazioOuBranco(valorDiligenciasStr)){
			efetuarParcelamentoDebitosActionForm.set("valorDiligencias", Util.formatarMoedaReal(valorDiligencias));
		}else{
			valorDiligencias = Util.formatarMoedaRealparaBigDecimal(valorDiligenciasStr);
		}

		if(fachada.verificarPermissaoEspecial(PermissaoEspecial.INFORMAR_VL_DILIGENCIAS_PARCELAMENTO_EXECUCAO_FISCAL, usuario)
						&& valorSucumbenciaAtual.compareTo(BigDecimal.ZERO) != 0){
			sessao.setAttribute("permitirInformarValorDiligenciasExecucaoFiscal", "S");
		}else{
			sessao.removeAttribute("permitirInformarValorDiligenciasExecucaoFiscal");
			efetuarParcelamentoDebitosActionForm.set("valorDiligencias", "0,00");
		}

		// Quando não é pelo botão Calcular
		if(calculaOpcaoParcelamento == null){
			// Verifica se alguma EP foi marcada
			boolean marcadaEP = false;
			// boolean marcadaNB = false;
			if(colecaoContaValoresNegociacao != null && !colecaoContaValoresNegociacao.isEmpty()){
				Iterator<ContaValoresHelper> contaValoresNegociacao = colecaoContaValoresNegociacao.iterator();
				while(contaValoresNegociacao.hasNext()){
					ContaValoresHelper contaValoresHelperNegociacao = contaValoresNegociacao.next();
					if(contaValoresHelperNegociacao.getIndicadorContasDebito() != null){
						if(contaValoresHelperNegociacao.getIndicadorContasDebito().equals(Integer.valueOf("1"))){
							marcadaEP = true;
							sessao.removeAttribute("calculaNegociacao");
						}else if(contaValoresHelperNegociacao.getIndicadorContasDebito().equals(Integer.valueOf("2"))){
							// marcadaNB = true;
							sessao.removeAttribute("calculaNegociacao");
						}
					}
				}
			}

			BigDecimal valorEntradaDebitos = BigDecimal.ZERO; // Valor de Entrada da Aba 2
			if(efetuarParcelamentoDebitosActionForm.get("valorEntradaInformado") != null
							&& !efetuarParcelamentoDebitosActionForm.get("valorEntradaInformado").equals("") && !marcadaEP){
				valorEntradaDebitos = Util.formatarMoedaRealparaBigDecimal((String) efetuarParcelamentoDebitosActionForm
								.get("valorEntradaInformado"));

				// Se tem entrada pegar a data de vencimento.
				if(efetuarParcelamentoDebitosActionForm.get("dataVencimentoEntradaParcelamento") != null
								&& !efetuarParcelamentoDebitosActionForm.get("dataVencimentoEntradaParcelamento").equals("")){
					dataVencimentoEntradaParcelamento = (String) efetuarParcelamentoDebitosActionForm
									.get("dataVencimentoEntradaParcelamento");
				}else{
					// Caso não tenha informado considerar a data atual.
					dataVencimentoEntradaParcelamento = Util.formatarData(new Date());
				}

			}else{
				valorEntradaDebitos = (BigDecimal) sessao.getAttribute("valorEntradaMinima");
			}

			// Caso não esteja marcada EP e não tenha sido calculado
			if(!marcadaEP && sessao.getAttribute("calculaNegociacao") == null){
				valorEntradaInformado = null; // BigDecimal.ZERO;
				// Caso tenha EP marcada e seja valor diferente do anterior
			}else if(!(valorEntradaDebitos.compareTo(valorEntradaInformadoAntes) == 0)){
				valorEntradaInformado = (BigDecimal) sessao.getAttribute("valorEntradaMinima");
			}

			if(colecaoContaValoresNegociacao != null && !colecaoContaValoresNegociacao.isEmpty()){
				Iterator<ContaValoresHelper> contaValoresNegociacao = colecaoContaValoresNegociacao.iterator();
				while(contaValoresNegociacao.hasNext()){
					ContaValoresHelper contaValoresHelperNegociacao = contaValoresNegociacao.next();

					// Caso tenha vindo da aba 2
					if(sessao.getAttribute("colecaoContaValores") != null){
						// Caso não tenha marcado a conta
						if(contaValoresHelperNegociacao.getIndicadorContasDebito() == null){
							if(contaValoresHelperNegociacao.getValorMulta() != null){
								valorTotalMultas.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
								valorTotalMultas = valorTotalMultas.add(contaValoresHelperNegociacao.getValorMulta().setScale(
												Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO));
							}
							if(contaValoresHelperNegociacao.getValorJurosMora() != null){
								valorTotalJurosMora.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
								valorTotalJurosMora = valorTotalJurosMora.add(contaValoresHelperNegociacao.getValorJurosMora().setScale(
												Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO));
							}
							if(contaValoresHelperNegociacao.getValorAtualizacaoMonetaria() != null){
								valorTotalAtualizacoesMonetarias.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
								valorTotalAtualizacoesMonetarias = valorTotalAtualizacoesMonetarias.add(contaValoresHelperNegociacao
												.getValorAtualizacaoMonetaria().setScale(Parcelamento.CASAS_DECIMAIS,
																Parcelamento.TIPO_ARREDONDAMENTO));
							}
						}
					}else{
						// Caso tenha vindo direto da aba 1
						if(contaValoresHelperNegociacao.getValorMulta() != null){
							valorTotalMultas.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
							valorTotalMultas = valorTotalMultas.add(contaValoresHelperNegociacao.getValorMulta().setScale(
											Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO));
						}
						if(contaValoresHelperNegociacao.getValorJurosMora() != null){
							valorTotalJurosMora.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
							valorTotalJurosMora = valorTotalJurosMora.add(contaValoresHelperNegociacao.getValorJurosMora().setScale(
											Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO));
						}
						if(contaValoresHelperNegociacao.getValorAtualizacaoMonetaria() != null){
							valorTotalAtualizacoesMonetarias.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
							valorTotalAtualizacoesMonetarias = valorTotalAtualizacoesMonetarias.add(contaValoresHelperNegociacao
											.getValorAtualizacaoMonetaria().setScale(Parcelamento.CASAS_DECIMAIS,
															Parcelamento.TIPO_ARREDONDAMENTO));
						}
					}
				}
			}

			// Caso o início do intervalo seja vazio preencher com o padrão da aba 1
			if(inicioIntervaloParcelamento.equals("")){
				inicioIntervaloParcelamento = "01/0001";
			}

			Collection<OpcoesParcelamentoHelper> colecaoOpcoesParcelamento = null;
			NegociacaoOpcoesParcelamentoHelper opcoesParcelamento = null;
			// [SB0002] - Obter Opções de Parcelamento
			opcoesParcelamento = fachada.obterOpcoesDeParcelamento(Integer.valueOf(resolucaoDiretoria), Integer.valueOf(codigoImovel),
							valorEntradaInformado, situacaoAguaId, situacaoEsgotoId, perfilImovel, inicioIntervaloParcelamento,
							indicadorRestabelecimento, colecaoContaValoresNegociacao, valorDebitoTotalAtualizado, valorTotalMultas,
							valorTotalJurosMora, valorTotalAtualizacoesMonetarias, numeroReparcelamentoConsecutivos,
							colecaoGuiaPagamentoHelper, usuario, valorDebitoACobrarParcelamentoImovelBigDecimal,
							inicioIntervaloParcelamentoFormatado, fimIntervaloParcelamentoFormatado, indicadoresParcelamentoHelper,
							dataVencimentoEntradaParcelamento, valorSucumbenciaTotal, quantidadeParcelasSucumbencia, valorSucumbenciaAtual,
							valorDiligencias);
			colecaoOpcoesParcelamento = opcoesParcelamento.getOpcoesParcelamento();

			if(!Util.isVazioOrNulo(colecaoOpcoesParcelamento)){
				for(OpcoesParcelamentoHelper opcaoParcelamento : colecaoOpcoesParcelamento){
					Integer numeroDiasVencimentoDaEntradaAux = opcaoParcelamento.getNumeroDiasVencimentoDaEntrada();

					if(numeroDiasVencimentoDaEntrada == null
									|| numeroDiasVencimentoDaEntrada.compareTo(numeroDiasVencimentoDaEntradaAux) < 0){
						// Obtem o maior valor
						numeroDiasVencimentoDaEntrada = numeroDiasVencimentoDaEntradaAux;
					}

					if(opcaoParcelamento.getValorDescontoAcrescimosImpontualidadeNaPrestacao() != null){
						existeValorDescontoNaPrestacao = true;
					}
				}
			}

			// Verifica se alguma opção de parcelamento foi marcada
			if(colecaoOpcoesParcelamento != null && !colecaoOpcoesParcelamento.isEmpty()
							&& !Util.isVazioOuBranco(efetuarParcelamentoDebitosActionForm.get("indicadorQuantidadeParcelas"))){

				Iterator<OpcoesParcelamentoHelper> opcoesParcelamentoValores = colecaoOpcoesParcelamento.iterator();
				while(opcoesParcelamentoValores.hasNext()){
					OpcoesParcelamentoHelper opcoesParcelamentoHelper = opcoesParcelamentoValores.next();

					if(((String) efetuarParcelamentoDebitosActionForm.get("indicadorQuantidadeParcelas")).equals(opcoesParcelamentoHelper
									.getQuantidadePrestacao().toString())){
						opcoesParcelamentoHelper.setIndicadorQuantidadeParcelas(Short.valueOf(opcoesParcelamentoHelper
										.getQuantidadePrestacao().toString()));
					}
				}

				sessao.removeAttribute("colecaoParcelamentoConfiguracaoPrestacao");
			}

			// Identifica a sugestão de data de vencimento para a entrada
			if(dataVencEntrada == null || dataVencEntrada.equals("")){
				Date dataVencimentoCalculada = null;

				if(numeroDiasVencimentoDaEntrada != null){
					String idMunicipio = ConstantesAplicacao.get("empresa.municipio");
					Municipio municipio = new Municipio();
					municipio.setId(Util.converterStringParaInteger(idMunicipio));

					Date dataRetorno = Util.adicionarNumeroDiasDeUmaData(new Date(), numeroDiasVencimentoDaEntrada - 1);
					dataVencimentoCalculada = this.getFachada().verificarDataUtilVencimento(dataRetorno, municipio);

				}

				if(dataVencimentoCalculada == null){
					dataVencimentoCalculada = Util.obterDataComDiaExistenteEmTodosMeses(new Date());
				}

				efetuarParcelamentoDebitosActionForm.set("dataVencimentoEntradaParcelamento", Util.formatarData(dataVencimentoCalculada));
			}else{
				efetuarParcelamentoDebitosActionForm.set("dataVencimentoEntradaParcelamento", dataVencEntrada);
			}

			// Colocando na sessão
			sessao.setAttribute("opcoesParcelamento", opcoesParcelamento);
			sessao.setAttribute("colecaoOpcoesParcelamento", colecaoOpcoesParcelamento);

			descontoAcrescimosImpontualidade = opcoesParcelamento.getValorDescontoAcrecismosImpotualidade().setScale(
							Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
			descontoSancoesRDEspecial = opcoesParcelamento.getValorDescontoSancoesRDEspecial();
			descontoTarifaSocialRDEspecial = opcoesParcelamento.getValorDescontoTarifaSocialRDEspecial();
			descontoAntiguidadeDebito = opcoesParcelamento.getValorDescontoAntiguidade();
			descontoInatividadeLigacaoAgua = opcoesParcelamento.getValorDescontoInatividade();
			descontoPagamentoAVistaRDEspecial = opcoesParcelamento.getValorDescontoPagamentoAVistaRDEspecial();
			percentualDescontoAcrescimosImpontualidade = opcoesParcelamento.getPercentualDescontoAcrescimosImpontualidade();
			percentualDescontoAntiguidadeDebito = opcoesParcelamento.getPercentualDescontoAntiguidadeDebito();
			percentualDescontoInatividadeLigacaoAgua = opcoesParcelamento.getPercentualDescontoInatividadeLigacaoAgua();
			parcelamentoPerfil = opcoesParcelamento.getParcelamentoPerfil();
			BigDecimal valorEntrada = opcoesParcelamento.getValorEntrada();
			valorMinimoPrestacao = opcoesParcelamento.getValorMinimoPrestacao() != null ? opcoesParcelamento.getValorMinimoPrestacao()
							: BigDecimal.ZERO;
			valorEstornoDescontos = opcoesParcelamento.getValorEstornoDescontos();

			// Inicia o valor da Entrada com a Miníma caso não tenha marcardo nenhuma conta
			if(marcadaEP){
				efetuarParcelamentoDebitosActionForm.set("valorEntradaInformado", Util.formatarMoedaReal(valorEntradaInformado));
				efetuarParcelamentoDebitosActionForm.set("valorEntradaInformadoAntes", Util.formatarMoedaReal(valorEntradaInformado));

				// Se tem entrada pega a data de vencimento.

			}else{
				if((valorEntradaInformado != null && !(valorEntrada.compareTo(valorEntradaInformado) == 0))
								&& sessao.getAttribute("calculaNegociacao") != null){
					valorEntrada = valorEntradaInformado;
				}
				efetuarParcelamentoDebitosActionForm.set("valorEntradaInformado", Util.formatarMoedaReal(valorEntrada));
				efetuarParcelamentoDebitosActionForm.set("valorEntradaInformadoAntes", Util.formatarMoedaReal(valorEntrada));
			}

			// Caso tenha acionado o botão calcular da aba 3
		}else{

			// Ver uma maneira de guardar o valor da entrada quando acionar o botão calcular
			sessao.setAttribute("calculaNegociacao", "1");

			// Verifica se a entrada informada é menor que a mínima
			if(colecaoContaValoresNegociacao != null && !colecaoContaValoresNegociacao.isEmpty()){
				Iterator<ContaValoresHelper> contaValoresNegociacao = colecaoContaValoresNegociacao.iterator();
				while(contaValoresNegociacao.hasNext()){
					ContaValoresHelper contaValoresHelperNegociacao = contaValoresNegociacao.next();
					if(sessao.getAttribute("colecaoContaValores") != null){
						// Caso não tenha marcado a conta
						if(contaValoresHelperNegociacao.getIndicadorContasDebito() == null){
							if(contaValoresHelperNegociacao.getValorMulta() != null){
								valorTotalMultas.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
								valorTotalMultas = valorTotalMultas.add(contaValoresHelperNegociacao.getValorMulta().setScale(
												Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO));
							}
							if(contaValoresHelperNegociacao.getValorJurosMora() != null){
								valorTotalJurosMora.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
								valorTotalJurosMora = valorTotalJurosMora.add(contaValoresHelperNegociacao.getValorJurosMora().setScale(
												Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO));
							}
							if(contaValoresHelperNegociacao.getValorAtualizacaoMonetaria() != null){
								valorTotalAtualizacoesMonetarias.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
								valorTotalAtualizacoesMonetarias = valorTotalAtualizacoesMonetarias.add(contaValoresHelperNegociacao
												.getValorAtualizacaoMonetaria().setScale(Parcelamento.CASAS_DECIMAIS,
																Parcelamento.TIPO_ARREDONDAMENTO));
							}
						}
					}
				}
			}

			// Limpando as opções da sessão
			sessao.removeAttribute("opcoesParcelamento");
			sessao.removeAttribute("colecaoOpcoesParcelamento");

			// [SB0002] - Obter Opções de Parcelamento de acordo com a entrada informada
			NegociacaoOpcoesParcelamentoHelper opcoesParcelamento = fachada.obterOpcoesDeParcelamento(Integer.valueOf(resolucaoDiretoria),
							Integer.valueOf(codigoImovel), valorEntradaInformado, situacaoAguaId, situacaoEsgotoId, perfilImovel,
							inicioIntervaloParcelamento, indicadorRestabelecimento, colecaoContaValoresNegociacao,
							valorDebitoTotalAtualizado, valorTotalMultas, valorTotalJurosMora, valorTotalAtualizacoesMonetarias,
							numeroReparcelamentoConsecutivos, colecaoGuiaPagamentoHelper, usuario,
							valorDebitoACobrarParcelamentoImovelBigDecimal, inicioIntervaloParcelamentoFormatado,
							fimIntervaloParcelamentoFormatado, indicadoresParcelamentoHelper, dataVencimentoEntradaParcelamento,
							valorSucumbenciaTotal, quantidadeParcelasSucumbencia, valorSucumbenciaAtual, valorDiligencias);

			descontoAcrescimosImpontualidade = opcoesParcelamento.getValorDescontoAcrecismosImpotualidade();
			descontoSancoesRDEspecial = opcoesParcelamento.getValorDescontoSancoesRDEspecial();
			descontoTarifaSocialRDEspecial = opcoesParcelamento.getValorDescontoTarifaSocialRDEspecial();
			descontoAntiguidadeDebito = opcoesParcelamento.getValorDescontoAntiguidade();
			descontoInatividadeLigacaoAgua = opcoesParcelamento.getValorDescontoInatividade();
			descontoPagamentoAVistaRDEspecial = opcoesParcelamento.getValorDescontoPagamentoAVistaRDEspecial();
			percentualDescontoAcrescimosImpontualidade = opcoesParcelamento.getPercentualDescontoAcrescimosImpontualidade();
			percentualDescontoAntiguidadeDebito = opcoesParcelamento.getPercentualDescontoAntiguidadeDebito();
			percentualDescontoInatividadeLigacaoAgua = opcoesParcelamento.getPercentualDescontoInatividadeLigacaoAgua();
			parcelamentoPerfil = opcoesParcelamento.getParcelamentoPerfil();
			BigDecimal valorEntradaMinima = opcoesParcelamento.getValorEntradaMinima();
			valorMinimoPrestacao = opcoesParcelamento.getValorMinimoPrestacao() != null ? opcoesParcelamento.getValorMinimoPrestacao()
							: BigDecimal.ZERO;
			valorEstornoDescontos = opcoesParcelamento.getValorEstornoDescontos();

			Collection<OpcoesParcelamentoHelper> colecaoOpcoesParcelamento = opcoesParcelamento.getOpcoesParcelamento();

			if(!Util.isVazioOrNulo(colecaoOpcoesParcelamento)){
				for(OpcoesParcelamentoHelper opcaoParcelamento : colecaoOpcoesParcelamento){
					Integer numeroDiasVencimentoDaEntradaAux = opcaoParcelamento.getNumeroDiasVencimentoDaEntrada();

					if(numeroDiasVencimentoDaEntrada == null
									|| numeroDiasVencimentoDaEntrada.compareTo(numeroDiasVencimentoDaEntradaAux) < 0){
						// Obtem o maior valor
						numeroDiasVencimentoDaEntrada = numeroDiasVencimentoDaEntradaAux;
					}

					if(opcaoParcelamento.getValorDescontoAcrescimosImpontualidadeNaPrestacao() != null){
						existeValorDescontoNaPrestacao = true;
					}
				}
			}

			if(valorEntradaInformado != null
							&& valorEntradaInformado.compareTo(valorEntradaMinima.setScale(Parcelamento.CASAS_DECIMAIS,
											Parcelamento.TIPO_ARREDONDAMENTO)) == -1){

				boolean temPermissaoValMinimoEntrada = fachada.verificarPermissaoValMinimoEntrada(usuario);

				if(!temPermissaoValMinimoEntrada){
					throw new ActionServletException("atencao.valor.entrada.deve.ser.maior.igual.minima", null, Util
									.formatarMoedaReal(valorEntradaMinima));
				}
			}

			if("".equals(valorDebitoTotalOriginal)){
				valorDebitoTotalOriginal = null;
			}

			sessao.setAttribute("opcoesParcelamento", opcoesParcelamento);
			sessao.setAttribute("colecaoOpcoesParcelamento", colecaoOpcoesParcelamento);

			// Limpa os EP da Coleção de Contas
			if(colecaoContaValoresNegociacao != null && !colecaoContaValoresNegociacao.isEmpty()){
				Iterator<ContaValoresHelper> contaValores = colecaoContaValoresNegociacao.iterator();
				while(contaValores.hasNext()){
					ContaValoresHelper contaValoresHelper = contaValores.next();
					if(contaValoresHelper.getIndicadorContasDebito() != null
									&& !contaValoresHelper.getIndicadorContasDebito().equals(Integer.valueOf("2"))){
						contaValoresHelper.setIndicadorContasDebito(null);
					}
				}
			}

			// Limpando a opção de parcelamento
			if(colecaoOpcoesParcelamento != null && !colecaoOpcoesParcelamento.equals("")){
				Iterator<OpcoesParcelamentoHelper> opcoesParcelamentoValores = colecaoOpcoesParcelamento.iterator();
				while(opcoesParcelamentoValores.hasNext()){
					OpcoesParcelamentoHelper opcoesParcelamentoHelper = opcoesParcelamentoValores.next();
					opcoesParcelamentoHelper.setIndicadorQuantidadeParcelas(null);
					efetuarParcelamentoDebitosActionForm.set("indicadorQuantidadeParcelas", null);
				}
			}

			// Atribui ao hidden o valor da entrada digitado
			efetuarParcelamentoDebitosActionForm.set("valorEntradaInformadoAntes", Util.formatarMoedaReal(valorEntradaInformado));
		}

		// TODO Saulo Lima
		Short pIndicadorExibirDebitosSituacaoDividaAtivaParcelamento = ConstantesSistema.NUMERO_NAO_INFORMADO;
		try{
			pIndicadorExibirDebitosSituacaoDividaAtivaParcelamento = Util
							.converterStringParaShort((String) ParametroParcelamento.P_INDICADOR_EXIBIR_DEBITOS_SITUACAO_DIVIDAATIVA_PARCELAMENTO
											.executar());
		}catch(ControladorException e){
			throw new ActionServletException("atencao.sistemaparametro_inexistente", null,
							"P_INDICADOR_EXIBIR_DEBITOS_SITUACAO_DIVIDAATIVA_PARCELAMENTO");
		}

		// Verifica se o valor do débito menos o valor dos descontos é menor que o valor minimo da
		// parcela
		BigDecimal valorTotalDescontos = BigDecimal.ZERO;
		BigDecimal resultadoDiferenca = BigDecimal.ZERO;
		valorTotalDescontos.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
		valorTotalDescontos = descontoAcrescimosImpontualidade.add(descontoAntiguidadeDebito);
		valorTotalDescontos = valorTotalDescontos.add(descontoInatividadeLigacaoAgua);
		valorTotalDescontos = valorTotalDescontos.add(descontoSancoesRDEspecial);
		valorTotalDescontos = valorTotalDescontos.add(descontoTarifaSocialRDEspecial);

		BigDecimal valorTotalDescontosSemAcrescimos = valorTotalDescontos.add(descontoAcrescimosImpontualidade);
		sessao.setAttribute("valorTotalDescontosSemAcrescimos", valorTotalDescontosSemAcrescimos);

		resultadoDiferenca.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
		resultadoDiferenca = valorDebitoTotalAtualizado.subtract(valorTotalDescontos);

		if(!fachada.verificarQtdeReparcelamentoPerfil(parcelamentoPerfil.getId(), Short.valueOf(numeroReparcelamentoConsecutivos
						.shortValue()))){
			throw new ActionServletException("atencao.nao.existe.condicao.por.quantidade.reparcelamentos.perfil");
		}

		// Coloca os valores no formulário
		efetuarParcelamentoDebitosActionForm.set("descontoAcrescimosImpontualidade", Util
						.formatarMoedaReal(descontoAcrescimosImpontualidade));
		efetuarParcelamentoDebitosActionForm.set("descontoAntiguidadeDebito", Util.formatarMoedaReal(descontoAntiguidadeDebito));
		efetuarParcelamentoDebitosActionForm.set("descontoInatividadeLigacaoAgua", Util.formatarMoedaReal(descontoInatividadeLigacaoAgua));
		efetuarParcelamentoDebitosActionForm.set("percentualDescontoAcrescimosImpontualidade", Util
						.formatarMoedaReal(percentualDescontoAcrescimosImpontualidade));
		efetuarParcelamentoDebitosActionForm.set("valorTotalDescontos", Util.formatarMoedaReal(valorTotalDescontos));
		efetuarParcelamentoDebitosActionForm.set("descontoSancoesRDEspecial", Util.formatarMoedaReal(descontoSancoesRDEspecial));
		efetuarParcelamentoDebitosActionForm.set("descontoTarifaSocialRDEspecial", Util.formatarMoedaReal(descontoTarifaSocialRDEspecial));

		valorDescontoPagamentoAVista = valorTotalDescontos;
		if(parcelamentoPerfil.getPercentualDescontoAVista() != null){
			valorDescontoPagamentoAVista = valorDescontoPagamentoAVista.add(descontoPagamentoAVistaRDEspecial);
		}

		valorTotalImpostosConta = obterValorImpostosDasContasDoParcelamento(colecaoContaValoresNegociacao);

		if(existeValorDescontoNaPrestacao){
			sessao.setAttribute("existeDescontoPorPrestacao", "1");
		}else{
			sessao.setAttribute("existeDescontoPorPrestacao", "2");
		}

		if(!Util.isVazioOuBranco(resolucaoDiretoria)){
			Integer resolucaoDiretoriaInt = Integer.valueOf(resolucaoDiretoria);

			FiltroResolucaoDiretoriaParametrosPagamentoAVista filtroResolucaoDiretoriaParametrosPagamentoAVista = new FiltroResolucaoDiretoriaParametrosPagamentoAVista();
			filtroResolucaoDiretoriaParametrosPagamentoAVista.adicionarParametro(new ParametroSimples(
							FiltroResolucaoDiretoriaParametrosPagamentoAVista.RESOLUCAO_DIRETORIA_ID, resolucaoDiretoriaInt));

			Collection<ResolucaoDiretoriaParametrosPagamentoAVista> colecaoResolucaoDiretoriaParametrosPagamentoAVista = (Collection<ResolucaoDiretoriaParametrosPagamentoAVista>) fachada
							.pesquisar(filtroResolucaoDiretoriaParametrosPagamentoAVista, ResolucaoDiretoriaParametrosPagamentoAVista.class
											.getName());

			if(!Util.isVazioOrNulo(colecaoResolucaoDiretoriaParametrosPagamentoAVista)){
				valorDescontoPagamentoAVista = Util.formatarMoedaRealparaBigDecimal((String) efetuarParcelamentoDebitosActionForm
								.get("valorAcrescimosImpontualidade"));
			}
		}

		valorPagamentoAVista.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
		valorPagamentoAVista = valorDebitoTotalAtualizado.subtract(valorDescontoPagamentoAVista);
		valorPagamentoAVista = valorPagamentoAVista.add(valorSucumbenciaAtual);
		valorPagamentoAVista = valorPagamentoAVista.subtract(valorTotalImpostosConta);

		if(valorEstornoDescontos.compareTo(BigDecimal.ZERO) > 0 && valorAtualizarComEstorno.compareTo(valorDebitoTotalAtualizado) < 0){

			efetuarParcelamentoDebitosActionForm.set("valorDebitoTotalAtualizadoSemEstorno", Util
							.formatarMoedaReal(valorDebitoTotalAtualizado));

			valorAtualizarComEstorno = valorDebitoTotalAtualizado.add(valorEstornoDescontos);

			efetuarParcelamentoDebitosActionForm.set("valorDebitoTotalAtualizado", Util.formatarMoedaReal(valorAtualizarComEstorno));
		}else{
			efetuarParcelamentoDebitosActionForm.set("valorDebitoTotalAtualizado", Util.formatarMoedaReal(valorDebitoTotalAtualizado));
		}

		// Colocando os valores no formulário

		efetuarParcelamentoDebitosActionForm.set("valorDescontoPagamentoAVista", Util.formatarMoedaReal(valorDescontoPagamentoAVista));
		efetuarParcelamentoDebitosActionForm.set("valorPagamentoAVista", Util.formatarMoedaReal(valorPagamentoAVista));
		efetuarParcelamentoDebitosActionForm.set("valorTotalImpostos", Util.formatarMoedaReal(valorTotalImpostosConta));
		efetuarParcelamentoDebitosActionForm.set("valorEstornoDescontos", Util.formatarMoedaReal(valorEstornoDescontos));

		if(percentualDescontoAntiguidadeDebito != null){
			efetuarParcelamentoDebitosActionForm.set("percentualDescontoAntiguidadeDebito", Util
							.formatarMoedaReal(percentualDescontoAntiguidadeDebito));
		}else{
			efetuarParcelamentoDebitosActionForm.set("percentualDescontoAntiguidadeDebito", "0.00");
		}

		if(percentualDescontoInatividadeLigacaoAgua != null){
			efetuarParcelamentoDebitosActionForm.set("percentualDescontoInatividadeLigacaoAgua", Util
							.formatarMoedaReal(percentualDescontoInatividadeLigacaoAgua));
		}else{
			efetuarParcelamentoDebitosActionForm.set("percentualDescontoInatividadeLigacaoAgua", "0.00");
		}

		if(parcelamentoPerfil != null){
			efetuarParcelamentoDebitosActionForm.set("parcelamentoPerfilId", parcelamentoPerfil.getId().toString());
		}else{
			efetuarParcelamentoDebitosActionForm.set("parcelamentoPerfilId", "0");
		}

		// O valor do débito é menor que o valor da parcela mínima permitida.
		// Utilizar a opção Pagamento à Vista.
		if(valorDebitoTotalAtualizado.compareTo(valorMinimoPrestacao) == -1){
			httpServletRequest.setAttribute("vlDebitoMenor", "1");
		}
		efetuarParcelamentoDebitosActionForm.set("valorMinimoPrestacao", Util.formatarMoedaReal(valorMinimoPrestacao));

		String habilitaPagamentoAVista = "1";
		if(colecaoContaValoresNegociacao == null || colecaoContaValoresNegociacao.isEmpty()){
			// habilitaPagamentoAVista = "2";
		}
		sessao.setAttribute("habilitaPagamentoAVista", habilitaPagamentoAVista);

		sessao.setAttribute("colecaoContaValoresNegociacao", colecaoContaValoresNegociacao);
		sessao.setAttribute("colecaoGuiaPagamentoNegociacao", colecaoGuiaPagamentoHelper);
		sessao.setAttribute("valorAcrescimosImpontualidadeNegociacao", valorAcrescimosImpontualidade);

		// Verifica se há EP marcado
		Collection<ContaValoresHelper> colecaoContaValores = null;
		colecaoContaValores = (Collection<ContaValoresHelper>) sessao.getAttribute("colecaoContaValores");
		boolean indicadorEPMarcado = false;
		if(colecaoContaValores != null){
			for(ContaValoresHelper contaValoresHelper : colecaoContaValores){
				if(contaValoresHelper.getIndicadorContasDebito() != null && contaValoresHelper.getIndicadorContasDebito() == 1){
					indicadorEPMarcado = true;
					httpServletRequest.getSession().setAttribute("somenteLeituraValorEntradaInformado", "true");
					break;
				}
			}
		}

		// calcular a opção de datas com base no número de dias vencimento da entrada da RD
		Collection collOpcaoDataVencEntrada = null;
		if(numeroDiasVencimentoDaEntrada != null){
			collOpcaoDataVencEntrada = Util.gerarDatasApartirNumeroDias(new Date(), numeroDiasVencimentoDaEntrada);
		}else{
			collOpcaoDataVencEntrada = Util.gerarDatasApartirNumeroDias(new Date(), 1);
		}

		sessao.setAttribute("collOpcaoDataVencEntrada", collOpcaoDataVencEntrada);
		sessao.setAttribute("numeroDiasVencimentoDaEntrada", numeroDiasVencimentoDaEntrada);

		/*
		 * Caso o campo "Cobrança do Parcelamento" esteja com a opção "Guia de Pagamento"
		 * selecionada e o indicador de "Permitir informar número/valor das parcelas" do perfil de
		 * parcelamento do imóvel esteja Sim, o sistema acrescenta uma opção Outro e a possibilidade
		 * de preencher as opções de parcelamento configurável
		 */
		if(efetuarParcelamentoDebitosActionForm.get("indicadorCobrancaParcelamento").toString()
						.equals(ConstantesSistema.INDICADOR_COBRANCA_PARC_GUIA_PAGAMENTO)
						&& Util.isVazioOuBranco(efetuarParcelamentoDebitosActionForm.get("indicadorQuantidadeParcelas"))
						&& parcelamentoPerfil.getIndicadorPermiteInformarNumeroValorParcela() != null
						&& parcelamentoPerfil.getIndicadorPermiteInformarNumeroValorParcela().equals(ConstantesSistema.SIM)){

			Collection<ParcelamentoConfiguracaoPrestacao> colecaoParcelamentoConfiguracaoPrestacao = new ArrayList<ParcelamentoConfiguracaoPrestacao>();

			if(sessao.getAttribute("colecaoParcelamentoConfiguracaoPrestacao") != null){

				colecaoParcelamentoConfiguracaoPrestacao = (Collection<ParcelamentoConfiguracaoPrestacao>) sessao
								.getAttribute("colecaoParcelamentoConfiguracaoPrestacao");
			}

			if(httpServletRequest.getParameter("adicionarParcelasConfiguravel") != null
							&& httpServletRequest.getParameter("adicionarParcelasConfiguravel").equals("sim")
							&& !Util.isVazioOuBranco(efetuarParcelamentoDebitosActionForm.get("quantidadeParcelasConfiguravel"))
							&& !Util.isVazioOuBranco(efetuarParcelamentoDebitosActionForm.get("valorParcelaConfiguravel"))){

				Short quantidadePrestacaoConfiguravel = Util.obterShort(efetuarParcelamentoDebitosActionForm.get(
								"quantidadeParcelasConfiguravel").toString());
				BigDecimal valorPrestacao = Util.formatarMoedaRealparaBigDecimal(efetuarParcelamentoDebitosActionForm.get(
								"valorParcelaConfiguravel").toString());
				boolean configuracaoPrestacaoJaExiste = false;

				if(!Util.isVazioOrNulo(colecaoParcelamentoConfiguracaoPrestacao)){
					
					for(ParcelamentoConfiguracaoPrestacao parcelamentoConfiguracaoPrestacao : colecaoParcelamentoConfiguracaoPrestacao){
						
						if(parcelamentoConfiguracaoPrestacao.getNumeroPrestacao().equals(quantidadePrestacaoConfiguravel)
										&& (parcelamentoConfiguracaoPrestacao.getValorPrestacao().compareTo(valorPrestacao) == 0)){

							parcelamentoConfiguracaoPrestacao.setValorPrestacao(valorPrestacao);
							parcelamentoConfiguracaoPrestacao.setUltimaAlteracao(new Date());
							configuracaoPrestacaoJaExiste = true;
						}
					}
				}

				if(!configuracaoPrestacaoJaExiste){

					ParcelamentoConfiguracaoPrestacao parcelamentoConfiguracaoPrestacaoAux = new ParcelamentoConfiguracaoPrestacao();
					parcelamentoConfiguracaoPrestacaoAux.setNumeroPrestacao(quantidadePrestacaoConfiguravel);
					parcelamentoConfiguracaoPrestacaoAux.setValorPrestacao(valorPrestacao);
					parcelamentoConfiguracaoPrestacaoAux.setUltimaAlteracao(new Date());
					parcelamentoConfiguracaoPrestacaoAux.setNumeroSequencia(colecaoParcelamentoConfiguracaoPrestacao.size() + 1);
					colecaoParcelamentoConfiguracaoPrestacao.add(parcelamentoConfiguracaoPrestacaoAux);
				}

				efetuarParcelamentoDebitosActionForm.set("quantidadeParcelasConfiguravel", null);
				efetuarParcelamentoDebitosActionForm.set("valorParcelaConfiguravel", null);
			}

			if(httpServletRequest.getParameter("removerParcelasConfiguravel") != null
							&& httpServletRequest.getParameter("removerParcelasConfiguravel").equals("sim")
							&& !Util.isVazioOuBranco(httpServletRequest.getParameter("identificadorParcelaRemocao"))
							&& !Util.isVazioOrNulo(colecaoParcelamentoConfiguracaoPrestacao)){

				ParcelamentoConfiguracaoPrestacao parcelamentoConfiguracaoPrestacaoRemocao = null;

				if(!Util.isVazioOrNulo(colecaoParcelamentoConfiguracaoPrestacao)){

					for(ParcelamentoConfiguracaoPrestacao parcelamentoConfiguracaoPrestacao : colecaoParcelamentoConfiguracaoPrestacao){

						if(String.valueOf(parcelamentoConfiguracaoPrestacao.getUltimaAlteracao().getTime()).equals(
										httpServletRequest.getParameter("identificadorParcelaRemocao"))){

							parcelamentoConfiguracaoPrestacaoRemocao = parcelamentoConfiguracaoPrestacao;
							break;
						}
					}
				}

				if(parcelamentoConfiguracaoPrestacaoRemocao != null){

					colecaoParcelamentoConfiguracaoPrestacao.remove(parcelamentoConfiguracaoPrestacaoRemocao);
				}

				efetuarParcelamentoDebitosActionForm.set("quantidadeParcelasConfiguravel", null);
				efetuarParcelamentoDebitosActionForm.set("valorParcelaConfiguravel", null);
			}

			sessao.setAttribute("colecaoParcelamentoConfiguracaoPrestacao", colecaoParcelamentoConfiguracaoPrestacao);

			BigDecimal valorRestante = valorDebitoTotalAtualizado;

			if(!Util.isVazioOrNulo(colecaoParcelamentoConfiguracaoPrestacao)){

				BigDecimal somatorioProdutoValorVersosQtdPrestacoes = BigDecimal.ZERO;

				for(ParcelamentoConfiguracaoPrestacao parcelamentoConfiguracaoPrestacao : colecaoParcelamentoConfiguracaoPrestacao){

					BigDecimal produtoPrestacaoVersusValor = parcelamentoConfiguracaoPrestacao.getValorPrestacao()
									.multiply(new BigDecimal(parcelamentoConfiguracaoPrestacao.getNumeroPrestacao().toString()))
									.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);

					somatorioProdutoValorVersosQtdPrestacoes = somatorioProdutoValorVersosQtdPrestacoes.add(produtoPrestacaoVersusValor);
				}

				/*
				 * Valor restante: Valor do Débito Atualizado menos o somatório do produto de cada
				 * quantidade de parcelas e valor da parcela da lista de parcelamento configurável
				 */
				valorRestante = valorRestante.subtract(somatorioProdutoValorVersosQtdPrestacoes);
			}

			efetuarParcelamentoDebitosActionForm.set("valorRestante", Util.formatarMoedaReal(valorRestante));
		}else{

			sessao.removeAttribute("colecaoParcelamentoConfiguracaoPrestacao");
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

		if(sessao.getAttribute("colecaoContaValores") != null){
			colecaoContaValoresNegociacao = (Collection<ContaValoresHelper>) sessao.getAttribute("colecaoContaValores");
		}else{
			colecaoContaValoresNegociacao = (Collection<ContaValoresHelper>) sessao.getAttribute("colecaoContaValoresImovel");
		}

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

	private BigDecimal obterValorImpostosDasContasDoParcelamento(Collection<ContaValoresHelper> colecaoContas){

		BigDecimal valorTotalImpostos = BigDecimal.ZERO;

		if(colecaoContas != null && !colecaoContas.isEmpty()){
			Iterator<ContaValoresHelper> contas = colecaoContas.iterator();

			while(contas.hasNext()){
				ContaValoresHelper contaValoresHelper = contas.next();

				if(contaValoresHelper.getConta().getValorImposto() != null){
					valorTotalImpostos = valorTotalImpostos.add(contaValoresHelper.getConta().getValorImposto());
				}
			}
		}
		return valorTotalImpostos;
	}

	/**
	 * Método Usado para verificar se o motivo revisão da conta informada está incluso na lista de
	 * motivos de revisão escolhidos no filtro da primeira aba.
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