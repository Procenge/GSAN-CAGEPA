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

package gcom.gui.relatorio.cobranca.parcelamento;

import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.cliente.ClienteImovel;
import gcom.cadastro.cliente.ClienteRelacaoTipo;
import gcom.cadastro.cliente.FiltroClienteImovel;
import gcom.cadastro.imovel.Imovel;
import gcom.cobranca.DocumentoTipo;
import gcom.cobranca.ResolucaoDiretoriaParametrosPagamentoAVista;
import gcom.cobranca.ajustetarifa.AjusteTarifa;
import gcom.cobranca.ajustetarifa.FiltroAjusteTarifa;
import gcom.cobranca.bean.ContaValoresHelper;
import gcom.cobranca.bean.GuiaPagamentoValoresHelper;
import gcom.cobranca.bean.NegociacaoOpcoesParcelamentoHelper;
import gcom.fachada.Fachada;
import gcom.faturamento.credito.CreditoARealizar;
import gcom.faturamento.credito.CreditoTipo;
import gcom.faturamento.credito.FiltroCreditoARealizar;
import gcom.faturamento.debito.DebitoACobrar;
import gcom.faturamento.debito.DebitoTipo;
import gcom.gui.ActionServletException;
import gcom.interceptor.RegistradorOperacao;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.RelatorioVazioException;
import gcom.relatorio.cobranca.parcelamento.ExtratoDebitoRelatorioHelper;
import gcom.relatorio.cobranca.parcelamento.RelatorioExtratoDebito;
import gcom.seguranca.acesso.Argumento;
import gcom.seguranca.acesso.DocumentoEmissaoEfetuada;
import gcom.seguranca.acesso.Operacao;
import gcom.seguranca.acesso.PermissaoEspecial;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.seguranca.acesso.usuario.UsuarioAcao;
import gcom.seguranca.acesso.usuario.UsuarioAcaoUsuarioHelper;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.filtro.ParametroNulo;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.arrecadacao.ParametroArrecadacao;
import gcom.util.parametrizacao.atendimentopublico.ParametroAtendimentoPublico;
import gcom.util.parametrizacao.cobranca.ParametroCobranca;

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
 * [UC0444] Gerar e Emitir Extrato de Débito
 * 
 * @author Vivianne Sousa
 * @date 07/09/2006
 * @author Virgínia melo
 * @date 20/10/2008
 *       Customização v0.06
 */

public class GerarRelatorioExtratoDebitoAction
				extends ExibidorProcessamentoTarefaRelatorio {

	private static final String EXTRATO_DEBITO = "extratoDebito";

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = null;
		Fachada fachada = Fachada.getInstancia();

		HttpSession sessao = httpServletRequest.getSession(false);
		Usuario usuarioLogado = this.getUsuarioLogado(httpServletRequest);

		/**
		 * [UC0203] Consultar Débitos
		 * [FS0015] Verificar Existência de Conta em Execução Fiscal
		 * 
		 * @author Gicevalter Couto
		 * @date 06/08/2014
		 */
		boolean temPermissaoAtualizarDebitosExecFiscal = fachada.verificarPermissaoEspecial(
						PermissaoEspecial.ATUALIZAR_DEBITOS_EXECUCAO_FISCAL, this.getUsuarioLogado(httpServletRequest));

		Short indicadorExecFiscal;
		indicadorExecFiscal = fachada.verificarImovelDebitoExecucaoFiscal(
						(Collection<DebitoACobrar>) sessao.getAttribute("colecaoDebitoACobrar"),
						(Collection<GuiaPagamentoValoresHelper>) sessao.getAttribute("colecaoGuiaPagamentoValores"),
						(Collection<ContaValoresHelper>) sessao.getAttribute("colecaoContaValores"));

		if(!temPermissaoAtualizarDebitosExecFiscal && indicadorExecFiscal.equals(Short.valueOf("1"))){
			throw new ActionServletException("atencao.imprimir.imovel.possui.debito.execucao.fiscal");
		}

		if(httpServletRequest.getParameter("parcelamento") != null){
			sessao.setAttribute("parcelamento", 1);
		}
		
		// Linha 2
		String inscricao = "";
		String nomeUsuario = "";
		String matricula = "";
		String inPagamentoCartaoCredito = "";

		// Linha 3
		String enderecoImovel = "";

		Integer idResolucaoDiretoria = null;
		String mensagemPagamentoAVista = "";

		Imovel imovel = null;
		Collection<ContaValoresHelper> colecaoContas = null;
		Collection<ContaValoresHelper> colecaoContasTEMP = new ArrayList();
		Collection<GuiaPagamentoValoresHelper> colecaoGuiasPagamento = null;
		Collection<DebitoACobrar> colecaoDebitosACobrar = null;
		Collection<CreditoARealizar> colecaoCreditoARealizar = null;
		BigDecimal valorAcrescimosImpontualidade = BigDecimal.ZERO;
		BigDecimal valorAcrescimosImpontualidadeTemp = BigDecimal.ZERO;
		BigDecimal valorDocumento = BigDecimal.ZERO;
		BigDecimal valorDesconto = BigDecimal.ZERO;
		BigDecimal valorTotalSucumbencia = BigDecimal.ZERO;		
		BigDecimal valorGuiaParcelamentoCobrancaBancaria = BigDecimal.ZERO;
		Short indicadorGeracaoTaxaCobranca = Short.valueOf("2"); // no caso do parcelamento sempre 2
		String quantidadeParcelasDebitos = "";
		String quantidadeParcelas = "";
		BigDecimal valorImpostoDeduzidoParcelamento = BigDecimal.ZERO;
		BigDecimal valorTotalContas = BigDecimal.ZERO;
		BigDecimal valorTotalDebitoACobrar = BigDecimal.ZERO;
		BigDecimal valorTotalGuiaPagamento = BigDecimal.ZERO;
		BigDecimal valorTotalCreditoARealizar = BigDecimal.ZERO;
		Integer quantidadeDebitoACobrar = null;
		Integer quantidadeParcelamento = null;
		BigDecimal valorTotalImpostos = BigDecimal.ZERO;
		DocumentoTipo documentoTipo = new DocumentoTipo();

		String confirmadoTaxas = httpServletRequest.getParameter("confirmado");
		String textoMensagemPopUP = "atencao.confirmacao.extrato_debito.gerar_debito";
		Boolean gerarDebitoACobrarTaxa = false;
		if(confirmadoTaxas != null && confirmadoTaxas.equals("ok")){
			gerarDebitoACobrarTaxa = true;
		}

		String valorHiddenConfirmacao = httpServletRequest.getParameter("campoHidden");
		String valorHidden = null;
		String botaoVoltar = "FALSE";
		boolean isExtratoDebito = httpServletRequest.getParameter(EXTRATO_DEBITO) != null;
		if(Util.isNaoNuloBrancoZero(valorHiddenConfirmacao) && valorHiddenConfirmacao.equals(EXTRATO_DEBITO)){
			isExtratoDebito = true;
		}

		Map<Integer, BigDecimal> mapProcessosExecFiscal = null;

		if(isExtratoDebito){

			textoMensagemPopUP = "atencao.confirmacao.extrato_debito_selecao.gerar_debito";
			valorHidden = EXTRATO_DEBITO;
			botaoVoltar = "TRUE";
			// Relatório chamado a partir da tela de Extrato de Débitos
			Integer idImovel = Integer.valueOf((String) sessao.getAttribute("idImovelExtrato"));
			imovel = fachada.pesquisarImovel(idImovel);

			colecaoContas = (Collection<ContaValoresHelper>) sessao.getAttribute("colecaoContasExtrato");
			colecaoGuiasPagamento = (Collection<GuiaPagamentoValoresHelper>) sessao.getAttribute("colecaoGuiasPagamentoExtrato");

			// Débitos a cobrar
			colecaoDebitosACobrar = (Collection<DebitoACobrar>) sessao.getAttribute("colecaoDebitosACobrarExtratoSelecao");

			if(!Util.isVazioOrNulo(colecaoDebitosACobrar) && sessao.getAttribute("qtdeParcelasAntecipadasDebitos") != null){

				quantidadeParcelasDebitos = (String) sessao.getAttribute("qtdeParcelasAntecipadasDebitos");
			}

			if(!Util.isVazioOrNulo(colecaoDebitosACobrar) && sessao.getAttribute("qtdeParcelasAntecipadas") != null){

				quantidadeParcelas = (String) sessao.getAttribute("qtdeParcelasAntecipadas");
			}

			if(sessao.getAttribute("quantidadeDebitoACobrar") != null){

				quantidadeDebitoACobrar = (Integer) sessao.getAttribute("quantidadeDebitoACobrar");
			}

			if(sessao.getAttribute("quantidadeParcelamento") != null){

				quantidadeParcelamento = (Integer) sessao.getAttribute("quantidadeParcelamento");
			}

			if(sessao.getAttribute("valorTotalImpostos") != null){

				valorTotalImpostos = (BigDecimal) sessao.getAttribute("valorTotalImpostos");
			}

			// Créditos a realizar
			colecaoCreditoARealizar = (Collection<CreditoARealizar>) sessao.getAttribute("colecaoCreditoARealizarExtratoSelecao");
			valorAcrescimosImpontualidade = (BigDecimal) sessao.getAttribute("valorAcrescimosImpontualidadeExtrato");
			valorTotalDebitoACobrar = (BigDecimal) sessao.getAttribute("valorDebitosExtratoSelecao");

			if(sessao.getAttribute("valorDescontoExtrato") != null){

				valorDesconto = (BigDecimal) sessao.getAttribute("valorDescontoExtrato");
			}

			valorDesconto = valorDesconto.add((BigDecimal) sessao.getAttribute("valorIncluirAcrescimoComoDesconto"));

			valorDocumento = valorDocumento.add(valorTotalDebitoACobrar).subtract(valorDesconto);


			// Linha 2
			inscricao = imovel.getInscricaoFormatada();
			nomeUsuario = (String) sessao.getAttribute("nomeClienteExtrato");
			matricula = imovel.getId().toString();

			// Linha 3
			try{
				enderecoImovel = fachada.pesquisarEnderecoFormatado(idImovel);
			}catch(ControladorException e){
				e.printStackTrace();
			}
			if(colecaoContas != null){
				colecaoContasTEMP.addAll(colecaoContas);
			}

			verificarPermissaoInclusaoContaCobrancaBancaria(colecaoContasTEMP, httpServletRequest);

		}else if(sessao.getAttribute("parcelamento") != null){
			// relatorio chamado a partir da tela de efetuar parcelamento
			documentoTipo.setId(DocumentoTipo.EXTRATO_DE_DEBITO_PARCELAMENTO);

			// Verifica se a aba 3 é chamada pela aba 2(colecaoContaValores) ou pela aba
			// 1(colecaoContaValoresImovel)
			if(sessao.getAttribute("colecaoContaValoresSemContasNB") != null || sessao.getAttribute("colecaoGuiasPagamento") != null){
				// efetuar parcelamento (Pagamento a vista)
				colecaoContas = (Collection<ContaValoresHelper>) sessao.getAttribute("colecaoContaValoresSemContasNB");
				if(colecaoContas != null){
					colecaoContasTEMP.addAll(colecaoContas);
				}

				verificarPermissaoInclusaoContaCobrancaBancaria(colecaoContasTEMP, httpServletRequest);

				colecaoGuiasPagamento = (Collection<GuiaPagamentoValoresHelper>) sessao.getAttribute("colecaoGuiaPagamentoValores");
				valorAcrescimosImpontualidade = (BigDecimal) sessao.getAttribute("valorAcrescimosImpontualidade");
				colecaoDebitosACobrar = (Collection<DebitoACobrar>) sessao.getAttribute("colecaoDebitoACobrar");

			}else if(sessao.getAttribute("colecaoContaValoresNegociacao") != null
							|| (sessao.getAttribute("colecaoContaValoresNegociacao") != null)){// TODO:
				// if
				// estranho...

				// efetuar parcelamento (Pagamento a vista)
				colecaoContas = (Collection<ContaValoresHelper>) sessao.getAttribute("colecaoContaValoresNegociacao");
				if(colecaoContas != null){
					colecaoContasTEMP.addAll(colecaoContas);
				}

				verificarPermissaoInclusaoContaCobrancaBancaria(colecaoContasTEMP, httpServletRequest);

				colecaoGuiasPagamento = (Collection<GuiaPagamentoValoresHelper>) sessao.getAttribute("colecaoGuiaPagamentoNegociacao");
				valorAcrescimosImpontualidade = (BigDecimal) sessao.getAttribute("valorAcrescimosImpontualidadeNegociacao");
				colecaoDebitosACobrar = (Collection<DebitoACobrar>) sessao.getAttribute("colecaoDebitoACobrar");

				// removendo as contas NB
				Collection<ContaValoresHelper> remover = new ArrayList();
				for(ContaValoresHelper contaValoresHelper : colecaoContasTEMP){
					if(contaValoresHelper.getIndicadorContasDebito() != null && contaValoresHelper.getIndicadorContasDebito().equals(2)){
						remover.add(contaValoresHelper);
					}
				}
				colecaoContasTEMP.removeAll(remover);

			}
			colecaoCreditoARealizar = (Collection<CreditoARealizar>) sessao.getAttribute("colecaoCreditoARealizar");

			// Obtém apenas débitos que não são juros de parcelamento
			Object[] debitosSemJurosParcelamento = obterColecaoDebitosACobrarSemJurosParcelamento(colecaoDebitosACobrar);
			BigDecimal valorDescontoAntecipacao = BigDecimal.ZERO;

			if(!Util.isVazioOrNulo(debitosSemJurosParcelamento)){

				valorDescontoAntecipacao = (BigDecimal) debitosSemJurosParcelamento[1];
			}

			// [SB0005] – Verificar débitos de parcelamento com descontos nos acréscimos por
			// impontualidade
			colecaoDebitosACobrar = this.tratarDebitosParcelamentoComDescontosNosAcrescimosPorImpontualidade(colecaoDebitosACobrar);

			DynaActionForm efetuarParcelamentoDebitosActionForm = (DynaActionForm) actionForm;
			valorDesconto = Util.formatarMoedaRealparaBigDecimal(efetuarParcelamentoDebitosActionForm.get("valorDescontoPagamentoAVista")
							.toString());
			valorImpostoDeduzidoParcelamento = Util.formatarMoedaRealparaBigDecimal(efetuarParcelamentoDebitosActionForm.get(
							"valorTotalImpostos").toString());

			// Abate os descontos concedidos no parcelamento
			valorDocumento = valorDocumento.subtract(valorDesconto).subtract(valorImpostoDeduzidoParcelamento);
			valorDesconto = valorDesconto.add(valorDescontoAntecipacao);

			if(!Util.isVazioOrNulo(colecaoDebitosACobrar)){

				for(DebitoACobrar debitoACobrar : colecaoDebitosACobrar){

					valorTotalDebitoACobrar = valorTotalDebitoACobrar.add(debitoACobrar.getValorTotal());
				}
			}

			valorDocumento = valorDocumento.add(valorTotalDebitoACobrar);

			valorTotalDebitoACobrar = valorTotalDebitoACobrar.add(valorDescontoAntecipacao);
			
			imovel = (Imovel) sessao.getAttribute("imovel");

			mapProcessosExecFiscal = (Map<Integer, BigDecimal>) sessao.getAttribute("mapProcessos");
			if(mapProcessosExecFiscal != null){
				valorTotalSucumbencia = Util.somarMapBigDecimal(mapProcessosExecFiscal);

				String valorTotalSucumbenciaString = (String) efetuarParcelamentoDebitosActionForm.get("valorSucumbenciaAtual");
				if(valorTotalSucumbenciaString != null){
					BigDecimal valorTotalSucumbenciaAlterado = Util.formatarMoedaRealparaBigDecimal(valorTotalSucumbenciaString);
					if(!valorTotalSucumbencia.equals(valorTotalSucumbenciaAlterado)){
						mapProcessosExecFiscal = fachada.distribuirValorEntreProcessosExecucaoFiscal(valorTotalSucumbenciaAlterado,
										mapProcessosExecFiscal, Integer.valueOf(imovel.getId()));

						valorTotalSucumbencia = Util.somarMapBigDecimal(mapProcessosExecFiscal);
					}
				}
			}
			
			valorDocumento = valorDocumento.add(valorTotalSucumbencia);
			
			// inPagamentoCartaoCredito =
			// efetuarParcelamentoDebitosActionForm.get("inPagamentoCartaoCredito").toString();
			if(httpServletRequest.getParameter("inPagamentoCartaoCredito") != null){
				inPagamentoCartaoCredito = httpServletRequest.getParameter("inPagamentoCartaoCredito");
				sessao.setAttribute("inPagamentoCartaoCredito", inPagamentoCartaoCredito);
				efetuarParcelamentoDebitosActionForm.set("inPagamentoCartaoCredito", inPagamentoCartaoCredito);

				documentoTipo.setId(DocumentoTipo.EXTRATO_DE_DEBITO_PARCELAMENTO_CARTAO);
			}

			// Linha 2
			inscricao = (String) efetuarParcelamentoDebitosActionForm.get("inscricaoImovel");
			nomeUsuario = (String) efetuarParcelamentoDebitosActionForm.get("nomeCliente");
			matricula = (String) efetuarParcelamentoDebitosActionForm.get("matriculaImovel");

			// Linha 3
			enderecoImovel = (String) efetuarParcelamentoDebitosActionForm.get("endereco");

			String idResolucaoDiretoriaStr = (String) efetuarParcelamentoDebitosActionForm.get("resolucaoDiretoria");

			if(!Util.isVazioOuBranco(idResolucaoDiretoriaStr)){
				idResolucaoDiretoria = Integer.valueOf(idResolucaoDiretoriaStr);

				// Necessário para trazer a mensagem com os caracteres especiais
				ResolucaoDiretoriaParametrosPagamentoAVista rdParametrosPagamentoAVista = fachada
								.pesquisarMensagemExtratoParcelamentoPagamentoAVista(idResolucaoDiretoria, new Date());

				if(rdParametrosPagamentoAVista != null){
					mensagemPagamentoAVista = rdParametrosPagamentoAVista.getDescricaoMensagemExtrato();
				}else{
					String pMensagemExtratoPagamanetoAVistaRd = null;

					try{
						pMensagemExtratoPagamanetoAVistaRd = ((String) ParametroCobranca.P_MENSAGEM_EXTRATO_PAGAMENTO_A_VISTA_RD.executar());
					}catch(ControladorException ex){
						throw new ActionServletException("atencao.sistemaparametro_inexistente", "P_MENSAGEM_EXTRATO_PAGAMENTO_A_VISTA_RD");
					}

					String pMensagemExtratoPagamanetoAVistaTexto = null;

					try{
						pMensagemExtratoPagamanetoAVistaTexto = ((String) ParametroCobranca.P_MENSAGEM_EXTRATO_PAGAMENTO_A_VISTA_TEXTO
										.executar());
					}catch(ControladorException ex){
						throw new ActionServletException("atencao.sistemaparametro_inexistente",
										"P_MENSAGEM_EXTRATO_PAGAMENTO_A_VISTA_TEXTO");
					}

					if(!pMensagemExtratoPagamanetoAVistaRd.equals(ConstantesSistema.NUMERO_NAO_INFORMADO_STRING)
									&& !pMensagemExtratoPagamanetoAVistaTexto.equals(ConstantesSistema.NUMERO_NAO_INFORMADO_STRING)){

						Integer idResolucaoDiretoriaAux = Integer.valueOf(pMensagemExtratoPagamanetoAVistaRd);

						if(idResolucaoDiretoriaAux != null && idResolucaoDiretoria != null
										&& idResolucaoDiretoriaAux.intValue() == idResolucaoDiretoria.intValue()){
							mensagemPagamentoAVista = pMensagemExtratoPagamanetoAVistaTexto;
						}
					}
				}
			}

			// [SB0004] – Verificar créditos de descontos nos acréscimos por impontualidade
			colecaoCreditoARealizar = this.tratarCreditosDescontoAcrescimoPorImpontualidade(colecaoCreditoARealizar);
		}else{

			documentoTipo.setId(DocumentoTipo.EXTRATO_DE_DEBITO);

			// Aba Consultar Débitos do Imóvel
			colecaoContas = (Collection<ContaValoresHelper>) sessao.getAttribute("colecaoContaValores");
			if(colecaoContas != null){

				// P_EMITE_EXTRATO_CONTA_PAGA
				String pEmiteExtratoContaPaga = null;

				try{
					pEmiteExtratoContaPaga = ((String) ParametroCobranca.P_EMITE_EXTRATO_CONTA_PAGA.executar());
				}catch(ControladorException ex){
					throw new ActionServletException("atencao.sistemaparametro_inexistente", "P_EMITE_EXTRATO_CONTA_PAGA");
				}

				for(ContaValoresHelper contaValoresHelper : colecaoContas){

					// [FS0014] - Verifica conta paga
					if(pEmiteExtratoContaPaga.equals(ConstantesSistema.SIM.toString())
									|| (contaValoresHelper.getConta().getIndicadorPagamento() == null || !contaValoresHelper.getConta()
													.getIndicadorPagamento().equals(ConstantesSistema.SIM))){

						colecaoContasTEMP.add(contaValoresHelper);

						// Valor de Multa
						if(contaValoresHelper.getValorMulta() != null){
							valorAcrescimosImpontualidadeTemp = valorAcrescimosImpontualidadeTemp.add(contaValoresHelper.getValorMulta());
						}
						// Valor de JurosMora
						if(contaValoresHelper.getValorJurosMora() != null){
							valorAcrescimosImpontualidadeTemp = valorAcrescimosImpontualidadeTemp.add(contaValoresHelper
											.getValorJurosMora());
						}
						// Valor de AtualizacaoMonetaria
						if(contaValoresHelper.getValorAtualizacaoMonetaria() != null){
							valorAcrescimosImpontualidadeTemp = valorAcrescimosImpontualidadeTemp.add(contaValoresHelper
											.getValorAtualizacaoMonetaria());
						}
					}
				}
			}

			verificarPermissaoInclusaoContaCobrancaBancaria(colecaoContasTEMP, httpServletRequest);

			colecaoDebitosACobrar = (Collection<DebitoACobrar>) sessao.getAttribute("colecaoDebitoACobrar");

			Collection<GuiaPagamentoValoresHelper> colecaoGuiasPagamentoAux = (Collection<GuiaPagamentoValoresHelper>) sessao
							.getAttribute("colecaoGuiaPagamentoValores");

			// [FS0013 - Verifica existência de débito prescrito]:
			fachada.verificarExistenciaDebitoPrescrito(usuarioLogado, colecaoContasTEMP, colecaoGuiasPagamentoAux);

			// [FS0011] - Verificar existência de guia de parcelamento de cobrança bancária
			if(!Util.isVazioOrNulo(colecaoGuiasPagamentoAux)){
				colecaoGuiasPagamento = new ArrayList();

				Integer idGuiaPagamento = null;
				Short numeroPrestacao = null;

				boolean existeGuiaParcelamento = false;

				for(GuiaPagamentoValoresHelper guiaPagamentoValoresHelper : colecaoGuiasPagamentoAux){

					idGuiaPagamento = guiaPagamentoValoresHelper.getIdGuiaPagamento();
					numeroPrestacao = guiaPagamentoValoresHelper.getNumeroPrestacao();

					existeGuiaParcelamento = fachada.verificarGuiaPagamentoParcelamentoCobrancaBancaria(
									idGuiaPagamento, numeroPrestacao);

					// existeGuiaParcelamento =
					// fachada.verificarGuiaPagamentoParcelamentoCobrancaBancariaComBoletoGeradoValido(
					// idGuiaPagamento, numeroPrestacao);
					//
					// if(!existeGuiaParcelamento){
					// existeGuiaParcelamento =
					// fachada.verificarGuiaPagamentoParcelamentoCobrancaBancariaPendentesGeracaoBoleto(
					// idGuiaPagamento, numeroPrestacao);
					// }

					if(existeGuiaParcelamento){
						// Valor a ser removido do documento
						valorGuiaParcelamentoCobrancaBancaria = valorGuiaParcelamentoCobrancaBancaria.add(guiaPagamentoValoresHelper
										.getValorTotalPrestacao());

					}else{

						colecaoGuiasPagamento.add(guiaPagamentoValoresHelper);

					}

				}

			}

			//colecaoCreditoARealizar = (Collection<CreditoARealizar>) sessao.getAttribute("colecaoCreditoARealizar");

			Integer idImovel = Integer.valueOf((String) sessao.getAttribute("idImovelPrincipalAba"));
			imovel = fachada.pesquisarImovel(idImovel);

			inscricao = imovel.getInscricaoFormatada();
			matricula = idImovel.toString();

			// [SB0005] – Verificar débitos de parcelamento com descontos nos acréscimos por
			// impontualidade
			colecaoDebitosACobrar = this.tratarDebitosParcelamentoComDescontosNosAcrescimosPorImpontualidade(colecaoDebitosACobrar);

			// Obtém apenas débitos que não são juros de parcelamento
			Object[] debitosSemJurosParcelamento = obterColecaoDebitosACobrarSemJurosParcelamento(colecaoDebitosACobrar);

			if(!Util.isVazioOrNulo(debitosSemJurosParcelamento)){

				colecaoDebitosACobrar = (Collection<DebitoACobrar>) debitosSemJurosParcelamento[0];
				valorDesconto = (BigDecimal) debitosSemJurosParcelamento[1];
			}

			if(!Util.isVazioOrNulo(colecaoDebitosACobrar)){

				for(DebitoACobrar debitoACobrar : colecaoDebitosACobrar){

					valorTotalDebitoACobrar = valorTotalDebitoACobrar.add(debitoACobrar.getValorTotal());
				}
			}

			valorDocumento = valorDocumento.add(valorTotalDebitoACobrar);

			// Adiciona o valor de desconto ao valor de débitos apenas para efeito de exibição no
			// pdf pois já foi deduzido no calculo do valor do documento anteriormente
			valorTotalDebitoACobrar = valorTotalDebitoACobrar.add(valorDesconto);

			// Filtro para recuperar informação do cliente relacionado com o imóvel
			FiltroClienteImovel filtroClienteImovel = new FiltroClienteImovel();
			filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.IMOVEL_ID, idImovel));
			filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.CLIENTE_RELACAO_TIPO,
							ClienteRelacaoTipo.USUARIO));
			filtroClienteImovel.adicionarParametro(new ParametroNulo(FiltroClienteImovel.DATA_FIM_RELACAO));
			filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("cliente.clienteTipo");

			Collection<ClienteImovel> clientesImovel = fachada.pesquisar(filtroClienteImovel, ClienteImovel.class.getName());

			Cliente cliente = clientesImovel.iterator().next().getCliente();

			if(cliente != null){
				nomeUsuario = cliente.getNome();
			}

			try{
				enderecoImovel = fachada.pesquisarEnderecoFormatado(idImovel);
			}catch(ControladorException e){
				e.printStackTrace();
			}

			/* Calcula qntde de parcelas a pagar */
			Integer qtdParcelas = 0;
			HashMap<Integer, Integer> parcelamentos = new HashMap();
			for(DebitoACobrar debitoACobrar : colecaoDebitosACobrar){
				if(debitoACobrar != null && debitoACobrar.getParcelamento() != null
								&& parcelamentos.get(debitoACobrar.getParcelamento().getId()) == null){

					parcelamentos.put(debitoACobrar.getParcelamento().getId(), Integer.valueOf(0));
					qtdParcelas = qtdParcelas + (debitoACobrar.getNumeroPrestacaoDebito() - debitoACobrar.getNumeroPrestacaoCobradas());
				}
			}

			if(qtdParcelas.intValue() > 0){

				quantidadeParcelas = String.valueOf(qtdParcelas);
			}

			// Parâmetro que identifica se a empresa emite o documento com acrescimos
			String parametroTratarAcrescimosEmissaoDocumento = "";

			try{
				parametroTratarAcrescimosEmissaoDocumento = ParametroArrecadacao.P_TRATAR_ACRESCIMOS_EMISSAO_DOCUMENTO.executar()
								.toString();
			}catch(ControladorException ex){
				throw new ActionServletException("erro.parametro.sistema", ex, ex.getMessage());
			}

			if(parametroTratarAcrescimosEmissaoDocumento.equals(Short.toString(ConstantesSistema.SIM))){

				valorAcrescimosImpontualidade = valorAcrescimosImpontualidadeTemp;
			}

			// [SB0004] – Verificar créditos de descontos nos acréscimos por impontualidade
			colecaoCreditoARealizar = this.tratarCreditosDescontoAcrescimoPorImpontualidade(colecaoCreditoARealizar);
		}

		// Ver se o parametroSistema permite
		String exibirPerguntaCobrancaDeTaxa = null;
		try{
			exibirPerguntaCobrancaDeTaxa = ParametroAtendimentoPublico.P_INDICADOR_COBRAR_TAXA_EXTRATO_RELACAO_DEBITO.executar();
		}catch(ControladorException e1){
			throw new ActionServletException("atencao.sistemaparametro_inexistente", "P_INDICADOR_COBRAR_TAXA_EXTRATO_RELACAO_DEBITO");
		}
		
		// Contas
				Collection idsConta = new ArrayList();
				if(!Util.isVazioOrNulo(colecaoContasTEMP)){

					for(ContaValoresHelper contaValorHelper : colecaoContasTEMP){

						valorTotalContas = valorTotalContas.add(contaValorHelper.getValorTotalConta());
						idsConta.add(contaValorHelper.getConta().getId());
					}
				}
		
		String enderecoURL = httpServletRequest.getServletPath();
		if(!Util.isVazioOrNulo(idsConta)){
			fachada.verificarBloqueioFuncionalidadeMotivoRetificacaoRevisao(idsConta, usuarioLogado, false, false, enderecoURL);
		}

		if(Util.isVazioOuBranco(confirmadoTaxas) && Util.isNaoNuloBrancoZero(exibirPerguntaCobrancaDeTaxa)
						&& ConstantesSistema.SIM.equals(Short.valueOf(exibirPerguntaCobrancaDeTaxa))){
			httpServletRequest.setAttribute("caminhoActionConclusao", "/gsan/gerarRelatorioExtratoDebitoAction.do");
			httpServletRequest.setAttribute("cancelamento", "TRUE");
			httpServletRequest.setAttribute("nomeBotao1", "Sim");
			httpServletRequest.setAttribute("nomeBotao2", "Não");
			httpServletRequest.setAttribute("valorHiddenConfirmacao", valorHidden);

			boolean permissaoNaoCobrarTaxaExtratoDebito = fachada
							.verificarPermissaoNaoCobrarTaxaExtratoDebito(getUsuarioLogado(httpServletRequest));

			if(!permissaoNaoCobrarTaxaExtratoDebito){
				httpServletRequest.setAttribute("disableBotao2", "disable");
				httpServletRequest.setAttribute("titleBotao2", "Acesso negado. Você não tem permissão de acesso a esta funcionalidade.");
			}

			httpServletRequest.setAttribute("voltar", botaoVoltar);

			if(httpServletRequest.getParameter("idConta") != null && !httpServletRequest.getParameter("idConta").equals("")){
				sessao.setAttribute("idConta", httpServletRequest.getParameter("idConta"));
			}

			return montarPaginaConfirmacao(textoMensagemPopUP, httpServletRequest, actionMapping);
		}

		if(valorAcrescimosImpontualidade == null){

			valorAcrescimosImpontualidade = BigDecimal.ZERO;
		}

		

		// Creditos A Realizar
		if(!Util.isVazioOrNulo(colecaoCreditoARealizar)){

			for(CreditoARealizar creditoARealizar : colecaoCreditoARealizar){

				valorTotalCreditoARealizar = valorTotalCreditoARealizar.add(creditoARealizar.getValorTotal());
			}
		}

		// Guias de Pagamento
		if(!Util.isVazioOrNulo(colecaoGuiasPagamento)){

			for(GuiaPagamentoValoresHelper guiaValorHelper : colecaoGuiasPagamento){

				valorTotalGuiaPagamento = valorTotalGuiaPagamento.add(guiaValorHelper.getValorTotalPrestacao());
			}
		}

		// Totaliza o valor do documento
		valorDocumento = valorDocumento.add(valorTotalContas);
		valorDocumento = valorDocumento.add(valorTotalGuiaPagamento);

		if(valorAcrescimosImpontualidade.compareTo(BigDecimal.ZERO) == 1){

			valorDocumento = valorDocumento.add(valorAcrescimosImpontualidade);
		}

		valorDocumento = valorDocumento.subtract(valorTotalCreditoARealizar);

		if(valorDocumento.compareTo(BigDecimal.ZERO) <= 0){
			throw new ActionServletException("atencao.resultado.extrato.zero_negativo");
		}

		// [SB0008] - Verificar CREDTAC
		verificarCREDTAC(fachada, usuarioLogado, imovel, colecaoCreditoARealizar, valorDocumento);

		NegociacaoOpcoesParcelamentoHelper opcaoParcelamento = (NegociacaoOpcoesParcelamentoHelper) sessao
						.getAttribute("opcoesParcelamento");

		if(inPagamentoCartaoCredito != null && !inPagamentoCartaoCredito.equals("")){
			opcaoParcelamento.setInPagamentoCartaoCredito(inPagamentoCartaoCredito);
			opcaoParcelamento.setUsuarioLogado(usuarioLogado);
		}

		valorDocumento = valorDocumento.subtract(valorTotalImpostos);

		ExtratoDebitoRelatorioHelper extratoDebitoRelatorioHelper = fachada.gerarEmitirExtratoDebito(imovel, indicadorGeracaoTaxaCobranca,
						colecaoContasTEMP, colecaoGuiasPagamento, colecaoDebitosACobrar, valorAcrescimosImpontualidade, valorDesconto,
						valorDocumento, colecaoCreditoARealizar, null, opcaoParcelamento, idResolucaoDiretoria, mapProcessosExecFiscal);

		Usuario usuario = (Usuario) (httpServletRequest.getSession(false)).getAttribute("usuarioLogado");

		RelatorioExtratoDebito relatorioExtratoDebito = fachada.obterRelatorioExtratoDebito(extratoDebitoRelatorioHelper, imovel,
						valorTotalDebitoACobrar, valorAcrescimosImpontualidade, valorTotalCreditoARealizar, valorDesconto, valorDocumento,
						usuario, inscricao, nomeUsuario, matricula, enderecoImovel, quantidadeParcelas, mensagemPagamentoAVista,
						quantidadeParcelasDebitos, quantidadeDebitoACobrar, quantidadeParcelamento, valorTotalSucumbencia);

		try{
			if(gerarDebitoACobrarTaxa){
				relatorioExtratoDebito.addParametro("gerarDebitoACobrarTaxa", gerarDebitoACobrarTaxa);
				relatorioExtratoDebito.addParametro("cobrarTaxaDebitoTipo", DebitoTipo.TAXA_EXTRATO_DE_DEBITO);
			}
			retorno = processarExibicaoRelatorio(relatorioExtratoDebito, Integer.toString(TarefaRelatorio.TIPO_PDF), httpServletRequest,
							httpServletResponse, actionMapping);

			DocumentoEmissaoEfetuada documentoEmissaoEfetuada = new DocumentoEmissaoEfetuada();

			// ------------ REGISTRAR TRANSação----------------------------

			documentoEmissaoEfetuada.setImovel(imovel);
			documentoEmissaoEfetuada.setUsuario(usuario);
			documentoEmissaoEfetuada.setDocumentoTipo(documentoTipo);
			documentoEmissaoEfetuada.setUltimaAlteracao(new Date());
			Argumento argumento = new Argumento();
			argumento.setId(Argumento.IMOVEL);

			RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_EMITIR_DOCUMENTO_EXTRATO_DEBITO,
							imovel.getId(), argumento, imovel.getId(), new UsuarioAcaoUsuarioHelper(usuario,
											UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));
			RegistradorOperacao.set(registradorOperacao);
			documentoEmissaoEfetuada.adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
			registradorOperacao.registrarOperacao(documentoEmissaoEfetuada);
			this.getFachada().inserir(documentoEmissaoEfetuada);

			// ------------ REGISTRAR TRANSAÇÃO ----------------

		}catch(RelatorioVazioException ex){
			// manda o erro para a página no request atual
			reportarErros(httpServletRequest, "atencao.relatorio.vazio");

			// seta o mapeamento de retorno para a tela de atenção de popup
			retorno = actionMapping.findForward("telaAtencaoPopup");
		}

		return retorno;
	}

	/**
	 * [UC04444][SB0004] – Verificar créditos de descontos nos acréscimos por impontualidade
	 * 
	 * @param collCreditoARealizar
	 * @return
	 */
	private Collection<CreditoARealizar> tratarCreditosDescontoAcrescimoPorImpontualidade(Collection<CreditoARealizar> collCreditoARealizar){

		Collection<CreditoARealizar> novaColecao = new ArrayList<CreditoARealizar>();

		if(collCreditoARealizar != null){

			for(CreditoARealizar creditoARealizar : collCreditoARealizar){

				if(!creditoARealizar.getCreditoTipo().getId().equals(CreditoTipo.DESCONTO_ACRESCIMOS_IMPONTUALIDADE)){

					novaColecao.add(creditoARealizar);

				}

			}

		}

		return novaColecao;

	}

	/**
	 * [UC04444][SB0005] – Verificar débitos de parcelamento com descontos nos acréscimos por
	 * impontualidade
	 * 
	 * @param collDebitosACobrar
	 * @return
	 */
	private Collection<DebitoACobrar> tratarDebitosParcelamentoComDescontosNosAcrescimosPorImpontualidade(
					Collection<DebitoACobrar> collDebitosACobrar){

		Collection<DebitoACobrar> novaColecao = new ArrayList<DebitoACobrar>();
		Map<Integer, Collection<DebitoACobrar>> mapDebitosParcelamento = new HashMap<Integer, Collection<DebitoACobrar>>();

		if(collDebitosACobrar != null){

			for(DebitoACobrar debitoACobrar : collDebitosACobrar){

				if(debitoACobrar.getParcelamento() != null){

					if(mapDebitosParcelamento.containsKey(debitoACobrar.getParcelamento().getId())){

						mapDebitosParcelamento.get(debitoACobrar.getParcelamento().getId()).add(debitoACobrar);

					}else{

						Collection<DebitoACobrar> collDebitoACobrar = new ArrayList<DebitoACobrar>();
						collDebitoACobrar.add(debitoACobrar);

						mapDebitosParcelamento.put(debitoACobrar.getParcelamento().getId(), collDebitoACobrar);

					}

				}else{

					novaColecao.add(debitoACobrar);

				}

			}

		}

		for(Integer key : mapDebitosParcelamento.keySet()){

			FiltroCreditoARealizar filtro = new FiltroCreditoARealizar();
			filtro.adicionarParametro(new ParametroSimples(FiltroCreditoARealizar.PARCELAMENTO_ID, key));
			filtro.adicionarParametro(new ParametroSimples(FiltroCreditoARealizar.ID_CREDITO_TIPO,
							CreditoTipo.DESCONTO_ACRESCIMOS_IMPONTUALIDADE));

			Collection<CreditoARealizar> coll = Fachada.getInstancia().pesquisar(filtro, CreditoARealizar.class.getName());

			if(coll.isEmpty()){

				novaColecao.addAll(mapDebitosParcelamento.get(key));

			}

		}

		return novaColecao;

	}

	private Object[] obterColecaoDebitosACobrarSemJurosParcelamento(Collection colecaoDebitosACobrar){

		Object[] retorno = new Object[2];
		Collection<DebitoACobrar> colecaoDebitosACobrarParcelamento = new ArrayList();
		BigDecimal valorTotalDescontoPagamentoAVista = BigDecimal.ZERO;

		if(colecaoDebitosACobrar != null && !colecaoDebitosACobrar.isEmpty()){
			Iterator debitoACobrarValores = colecaoDebitosACobrar.iterator();

			while(debitoACobrarValores.hasNext()){
				
				DebitoACobrar debitoACobrar = (DebitoACobrar) debitoACobrarValores.next();

				// Verificar existência de juros sobre imóvel
				if(debitoACobrar.getDebitoTipo().getId() != null
								&& !debitoACobrar.getDebitoTipo().getId().equals(DebitoTipo.JUROS_SOBRE_PARCELAMENTO)){
					colecaoDebitosACobrarParcelamento.add(debitoACobrar);
				}else{
					
					valorTotalDescontoPagamentoAVista = valorTotalDescontoPagamentoAVista.add(debitoACobrar.getValorTotal());
				}
			}
		}

		retorno[0] = colecaoDebitosACobrarParcelamento;
		retorno[1] = valorTotalDescontoPagamentoAVista;
						
		return retorno;
	}



	/**
	 * [UC0630] Solicitar Emissão do Extrato de Débitos
	 * [FS0003] - Verificar permissão para inclusão de contas em revisão por Cobrança Bancária
	 * 
	 * @author Anderson Italo
	 * @date 24/04/2012
	 */
	private BigDecimal verificarPermissaoInclusaoContaCobrancaBancaria(Collection<ContaValoresHelper> colecaoConta,
					HttpServletRequest request){

		BigDecimal valorContasSemPermissao = BigDecimal.ZERO;
		BigDecimal valorTotalContas = BigDecimal.ZERO;
		if(!Util.isVazioOrNulo(colecaoConta)){

			Iterator iteratorColecaoContas = colecaoConta.iterator();
			Collection idsContasPermitidas = new ArrayList();
			Collection<ContaValoresHelper> colecaoContasPermitidas = new ArrayList<ContaValoresHelper>();

			while(iteratorColecaoContas.hasNext()){

				ContaValoresHelper contaValoresHelper = (ContaValoresHelper) iteratorColecaoContas.next();
				idsContasPermitidas.add(contaValoresHelper.getConta().getId());
				valorTotalContas = valorTotalContas.add(contaValoresHelper.getConta().getValorTotalContaBigDecimal());
			}

			/*
			 * [FS0003 – Verificar permissão para inclusão de contas em revisão por Cobrança
			 * Bancária]
			 */
			Fachada.getInstancia().verificarPermissaoInclusaoContasRevisaoCobrancaBancaria(idsContasPermitidas, getUsuarioLogado(request));

			if(!Util.isVazioOrNulo(idsContasPermitidas)){

				for(Iterator iterator = colecaoConta.iterator(); iterator.hasNext();){
					ContaValoresHelper contaValoresHelper = (ContaValoresHelper) iterator.next();

					if(idsContasPermitidas.contains(contaValoresHelper.getConta().getId())){

						colecaoContasPermitidas.add(contaValoresHelper);
					}else{

						valorContasSemPermissao = valorContasSemPermissao.add(contaValoresHelper.getConta().getValorTotalContaBigDecimal());
					}
				}
			}else{

				valorContasSemPermissao = valorTotalContas;
			}

			colecaoConta.clear();

			if(!Util.isVazioOrNulo(colecaoContasPermitidas)){

				colecaoConta.addAll(colecaoContasPermitidas);
			}
		}

		return valorContasSemPermissao;
	}

	/**
	 * [UC0444] Gerar e Emitir Extrato de Débito
	 * [SB0008] - Verificar CREDTAC
	 * 
	 * @author Anderson Italo
	 * @date 26/09/2014
	 */
	private void verificarCREDTAC(Fachada fachada, Usuario usuarioLogado, Imovel imovel,
					Collection<CreditoARealizar> colecaoCreditoARealizar, BigDecimal valorDocumento){

		if(!Util.isVazioOrNulo(colecaoCreditoARealizar)){

			for(CreditoARealizar creditoARealizar : colecaoCreditoARealizar){

				/*
				 * Caso o crédito (CRTI_CDCONSTANTE = "CREDTAC") corresponda ao crédito gerado pelo
				 * termo de ajuste de conduta de realinhamento de tarifas (CREDTAC)
				 */
				if(creditoARealizar.getCreditoTipo().getId().equals(CreditoTipo.CREDTAC)){

					// Atualiza o log de processamento na tabela AJUSTE_TARIFA para o imóvel e
					// crédito a realizar
					FiltroAjusteTarifa filtroAjusteTarifa = new FiltroAjusteTarifa();
					filtroAjusteTarifa.adicionarParametro(new ParametroSimples(FiltroAjusteTarifa.IMOVEL_ID, imovel.getId()));
					filtroAjusteTarifa.adicionarParametro(new ParametroSimples(FiltroAjusteTarifa.CREDITO_A_REALIZAR_ID, creditoARealizar
									.getId()));
					filtroAjusteTarifa.setCampoOrderByDesc(FiltroAjusteTarifa.DATA_CALCULO);

					Collection<AjusteTarifa> colecaoAjusteTarifa = fachada.pesquisar(filtroAjusteTarifa, AjusteTarifa.class.getName());

					AjusteTarifa ajusteTarifa = (AjusteTarifa) Util.retonarObjetoDeColecao(colecaoAjusteTarifa);

					if(ajusteTarifa != null){

						StringBuilder builderLog = new StringBuilder();

						if(ajusteTarifa.getDescricaoLog() != null){

							builderLog.append(ajusteTarifa.getDescricaoLog());
						}

						builderLog.append(System.getProperty("line.separator"));
						builderLog.append("******************************************************************************************************************");
						builderLog.append(System.getProperty("line.separator"));
						builderLog.append("Emissão de Extrato de Débitos no valor de R$ ");
						builderLog.append(Util.formatarMoedaReal(valorDocumento, 2));
						builderLog.append(System.getProperty("line.separator"));
						builderLog.append("Efetuado por " + usuarioLogado.getNomeUsuario());
						builderLog.append(" em " + Util.formatarDataComHoraSemSegundos(new Date()));

						ajusteTarifa.setDescricaoLog(builderLog.toString());

						fachada.atualizar(ajusteTarifa);
					}
				}
			}
		}
	}
}
