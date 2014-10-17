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

package gcom.gui.arrecadacao.pagamento;

import gcom.arrecadacao.pagamento.FiltroPagamentoSituacao;
import gcom.arrecadacao.pagamento.PagamentoSituacao;
import gcom.arrecadacao.pagamento.bean.ClassificarLotePagamentosNaoClassificadosHelper;
import gcom.arrecadacao.pagamento.bean.ClassificarPagamentosNaoClassificadosHelper;
import gcom.batch.Processo;
import gcom.batch.ProcessoIniciado;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action responsável por exibir a página de Classificar em Lote Pagamentos Não Classificados.
 * [UC3080] Classificar em Lote Pagamentos Não Classificados.
 * 
 * @author Josenildo Neves
 * @date 29/11/2012
 */
public class ClassificarLotePagamentosNaoClassificadosAction
				extends ExibidorProcessamentoTarefaRelatorio {

	/**
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Cria a variável de retorno e seta o mapeamento para a tela de sucesso
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		// Cria uma instância da fachada
		Fachada fachada = Fachada.getInstancia();

		// Cria uma instância da sessão
		HttpSession sessao = httpServletRequest.getSession(false);

		Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");

		// Recupera o form
		ClassificarLotePagamentosNaoClassificadosActionForm form = (ClassificarLotePagamentosNaoClassificadosActionForm) actionForm;

		// [FS0027] – Validar referência;
		this.ValidarReferencias(form);

		// [FS0019] – Validar data do pagamento
		this.ValidarDatas(form);
		
		ClassificarLotePagamentosNaoClassificadosHelper helper = this.converterFormularioParaHelper(form);

		Integer quantidadePagamentos = fachada.pesquisarQuantidadePagamentos(helper);

		if(quantidadePagamentos > ConstantesSistema.QUANTIDADE_LIMITE_ITEM_CONSULTA){

			ProcessoIniciado processoIniciado = new ProcessoIniciado();

			Processo processo = new Processo();
			processo.setId(Processo.CLASSIFICAR_LOTE_PAGAMENTOS_NAO_CLASSIFICADOS);
			processoIniciado.setDataHoraAgendamento(new Date());
			// Verificar restrição de execução simultânea de processos
			if(fachada.isProcessoComRestricaoExecucaoSimultanea(processo.getId())){

				throw new ActionServletException("atencao.processo_restricao_execucao");
			}

			processoIniciado.setProcesso(processo);
			processoIniciado.setUsuario(this.getUsuarioLogado(httpServletRequest));

			String descricaoDadosComplementares = "";

			if(descricaoDadosComplementares.length() > 120){
				descricaoDadosComplementares = descricaoDadosComplementares.substring(0, 120);
			}

			processoIniciado.setDescricaoDadosComplementares(descricaoDadosComplementares);

			List<Object> colecaoParametros = new ArrayList<Object>();
			colecaoParametros.add(helper);

			Integer codigoProcessoIniciadoGerado = (Integer) fachada.inserirProcessoIniciadoOnline(processoIniciado, colecaoParametros);

			if(codigoProcessoIniciadoGerado > 0){

				// montando página de sucesso
				montarPaginaSucesso(httpServletRequest, "Os pagamentos não classificados foram enviado para processamento.", "Voltar",
								"exibirClassificarLotePagamentosNaoClassificadosAction.do");
			}else{

				ActionServletException actionServletException = new ActionServletException("atencao.erro_iniciar_processo_online",
								String.valueOf(Processo.CLASSIFICAR_LOTE_PAGAMENTOS_NAO_CLASSIFICADOS)
												+ " - Classificar Lote Pagamentos Não classificados.");

				actionServletException.setUrlBotaoVoltar("/gsan/exibirClassificarLotePagamentosNaoClassificadosAction.do");
				throw actionServletException;
			}

		}else{

			if(!Util.isVazioOuBranco(quantidadePagamentos) && quantidadePagamentos > ConstantesSistema.ZERO){

				Collection<ClassificarPagamentosNaoClassificadosHelper> colecaoClassificarPagamentosNaoClassificadosHelper = fachada
								.classificarLotePagamentosNaoClassificados(helper, usuarioLogado);

				if(!Util.isVazioOrNulo(colecaoClassificarPagamentosNaoClassificadosHelper)){

					String tipoRelatorio = TarefaRelatorio.TIPO_PDF + "";

					RelatorioClassificarLotePagamentosNaoClassificados relatorio = new RelatorioClassificarLotePagamentosNaoClassificados(
									usuarioLogado);
					relatorio.addParametro("colecaoClassificarPagamentosNaoClassificadosHelper",
									colecaoClassificarPagamentosNaoClassificadosHelper);
					relatorio.addParametro("totalRegistrosRelatorio", colecaoClassificarPagamentosNaoClassificadosHelper.size());
					relatorio.addParametro("classificarLotePagamentosNaoClassificadosHelper", helper);

					relatorio.addParametro("tipoFormatoRelatorio", Integer.parseInt(tipoRelatorio));

					// Caso o retorno seja para a tela de sucesso, Monta a tela de sucesso
					if(retorno.getName().equalsIgnoreCase("telaSucesso")){
						montarPaginaSucesso(httpServletRequest, "Pagamento atualizado com sucesso.", "Classificar outro(s) Pagamento(s)",
										"exibirClassificarLotePagamentosNaoClassificadosAction.do?menu=sim");
					}

					retorno = processarExibicaoRelatorio(relatorio, tipoRelatorio, httpServletRequest, httpServletResponse, actionMapping);

				}else{

					ActionServletException actionServletException = new ActionServletException(
									"atencao.erro_iniciar_processo_online",
									String.valueOf(Processo.RELATORIO_CLASSIFICAR_LOTE_PAGAMENTOS_NAO_CLASSIFICADOS)
													+ " - Relatório Classificar Lote Pagamentos Não classificados. Coleção de pagamentos vazia.");

					actionServletException.setUrlBotaoVoltar("/gsan/exibirClassificarLotePagamentosNaoClassificadosAction.do");
					throw actionServletException;
				}
			}else{
				ActionServletException actionServletException = new ActionServletException("atencao.erro_lista_pagamento_vazia");

				actionServletException.setUrlBotaoVoltar("/gsan/exibirClassificarLotePagamentosNaoClassificadosAction.do");
				throw actionServletException;
			}
		}

		return retorno;
	}

	/**
	 * [UC3080] Classificar em Lote Pagamentos Não Classificados.
	 * [FS0027] – Validar referência;
	 * 
	 * @param form
	 */
	private void ValidarReferencias(ClassificarLotePagamentosNaoClassificadosActionForm form){

		if(!Util.isVazioOuBranco(form.getReferenciaArrecadacaoInicial())){
			if(!Util.validarMesAno(form.getReferenciaArrecadacaoInicial())){
				throw new ActionServletException("atencao.data_pagamento_invalida");
			}
		}

		if(!Util.isVazioOuBranco(form.getReferenciaArrecadacaoFinal())){
			if(!Util.validarMesAno(form.getReferenciaArrecadacaoFinal())){
				throw new ActionServletException("atencao.data_pagamento_invalida");
			}
		}
		if(!Util.isVazioOuBranco(form.getReferenciaPagamentoInicial())){
			if(!Util.validarMesAno(form.getReferenciaPagamentoInicial())){
				throw new ActionServletException("atencao.data_pagamento_invalida");
			}
		}

		if(!Util.isVazioOuBranco(form.getReferenciaPagamentoFinal())){
			if(!Util.validarMesAno(form.getReferenciaPagamentoFinal())){
				throw new ActionServletException("atencao.data_pagamento_invalida");
			}
		}
	}

	/**
	 * [UC3080] Classificar em Lote Pagamentos Não Classificados.
	 * // [FS0019] – Validar data do pagamento
	 * 
	 * @param form
	 */
	private void ValidarDatas(ClassificarLotePagamentosNaoClassificadosActionForm form){

		if(!Util.isVazioOuBranco(form.getDataPagamentoInicial())){
			if(Util.validarDiaMesAno(form.getDataPagamentoInicial())){
				throw new ActionServletException("atencao.data_pagamento_invalida");
			}
		}

		if(!Util.isVazioOuBranco(form.getDataPagamentoFinal())){
			if(Util.validarDiaMesAno(form.getDataPagamentoFinal())){
				throw new ActionServletException("atencao.data_pagamento_invalida");
			}
		}
	}

	private ClassificarLotePagamentosNaoClassificadosHelper converterFormularioParaHelper(
					ClassificarLotePagamentosNaoClassificadosActionForm form){

		ClassificarLotePagamentosNaoClassificadosHelper helper = new ClassificarLotePagamentosNaoClassificadosHelper();

		// Este campo (opcaoGeracao) existia na tela para que o usuário escolhesse entre as opções
		// "Classificar" e "Simular" (radiobutton). Foi solicitado por Fátima, na OC1084348, que
		// este campo fosse retirado da tela. Isto foi feito no JSP desta funcionalidade, mas a
		// partir daqui, foi mantido este atributo "opcaoGeracao" no Helper. Caso seja solicitado o
		// retorno deste campo, basta acrescentá-lo no JSP e capturar o seu valor neste Action,
		// neste ponto
		// @author Luciano Galvão (OC1084348)
		// @date 19/09/2013
		helper.setOpcaoGeracao('C');

		if(!form.getLocalidadeInicial().equals(Integer.parseInt(ConstantesSistema.ZERO.toString()))){
			helper.setLocalidadeInicial(form.getLocalidadeInicial());
		}

		if(!form.getLocalidadeFinal().equals(Integer.parseInt(ConstantesSistema.ZERO.toString()))){
			helper.setLocalidadeFinal(form.getLocalidadeFinal());
		}

		if(!Util.isVazioOuBranco(form.getReferenciaArrecadacaoInicial())){
			helper.setReferenciaArrecadacaoInicial(Util.formatarMesAnoComBarraParaAnoMes(form.getReferenciaArrecadacaoInicial()));
		}

		if(!Util.isVazioOuBranco(form.getReferenciaArrecadacaoFinal())){
			helper.setReferenciaArrecadacaoFinal(Util.formatarMesAnoComBarraParaAnoMes(form.getReferenciaArrecadacaoFinal()));
		}

		if(!Util.isVazioOuBranco(form.getReferenciaPagamentoInicial())){
			helper.setReferenciaPagamentoInicial(Util.formatarMesAnoComBarraParaAnoMes(form.getReferenciaPagamentoInicial()));
		}

		if(!Util.isVazioOuBranco(form.getReferenciaPagamentoFinal())){
			helper.setReferenciaPagamentoFinal(Util.formatarMesAnoComBarraParaAnoMes(form.getReferenciaPagamentoFinal()));
		}

		if(!Util.isVazioOuBranco(form.getDataPagamentoInicial())){
			helper.setDataPagamentoInicial(Util.converterStringParaData(form.getDataPagamentoInicial()));
		}

		if(!Util.isVazioOuBranco(form.getDataPagamentoFinal())){
			helper.setDataPagamentoFinal(Util.converterStringParaData(form.getDataPagamentoFinal()));
		}

		if(!Util.isVazioOuBranco(form.getSituacaoPagamento())){

			FiltroPagamentoSituacao filtroPagamentoSituacao = new FiltroPagamentoSituacao();
			filtroPagamentoSituacao.adicionarParametro(new ParametroSimples(FiltroPagamentoSituacao.CODIGO, form.getSituacaoPagamento()));
			Collection colecaoPagamentoSitucao = Fachada.getInstancia().pesquisar(filtroPagamentoSituacao,
							PagamentoSituacao.class.getName());

			if(!Util.isVazioOrNulo(colecaoPagamentoSitucao)){

				PagamentoSituacao pagamentoSituacao = (PagamentoSituacao) Util.retonarObjetoDeColecao(colecaoPagamentoSitucao);
				helper.setSituacaoPagamento(pagamentoSituacao.getId());
				helper.setDescricaoSitucaoPagamento(pagamentoSituacao.getDescricao());
			}
		}

		helper.setLimiteMaximoDiferenca(Util.formatarStringParaBigDecimal(form.getLimiteMaximoDiferenca(), 2, false));

		return helper;
	}
}
