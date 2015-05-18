
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

import java.math.BigDecimal;
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
 * @author Magno Silveira (magno.silveira@procenge.com.br)
 * @since 10/12/2014
 */
public class ClassificarLotePagamentosNaoClassificadosAjusteDesoAction
				extends ExibidorProcessamentoTarefaRelatorio {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Cria a variável de retorno e seta o mapeamento para a tela de sucesso
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		// Cria uma instância da fachada
		Fachada fachada = Fachada.getInstancia();

		// Cria uma instância da sessão
		HttpSession sessao = httpServletRequest.getSession(false);

		Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");

		boolean gerarDebitoACobrar = false;

		// Recupera o form
		ClassificarLotePagamentosNaoClassificadosAjusteDesoActionForm form = (ClassificarLotePagamentosNaoClassificadosAjusteDesoActionForm) actionForm;

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
			colecaoParametros.add(gerarDebitoACobrar);

			Integer codigoProcessoIniciadoGerado = (Integer) fachada.inserirProcessoIniciadoOnline(processoIniciado, colecaoParametros);

			if(codigoProcessoIniciadoGerado > 0){

				// montando página de sucesso
				montarPaginaSucesso(httpServletRequest, "Os pagamentos não classificados foram enviado para processamento.", "Voltar",
								"exibirClassificarLotePagamentosNaoClassificadosAjusteDesoAction.do");
			}else{

				ActionServletException actionServletException = new ActionServletException("atencao.erro_iniciar_processo_online",
								String.valueOf(Processo.CLASSIFICAR_LOTE_PAGAMENTOS_NAO_CLASSIFICADOS)
												+ " - Classificar Lote Pagamentos Não classificados.");

				actionServletException.setUrlBotaoVoltar("/gsan/exibirClassificarLotePagamentosNaoClassificadosAjusteDesoAction.do");
				throw actionServletException;
			}

		}else{

			if(!Util.isVazioOuBranco(quantidadePagamentos) && quantidadePagamentos > ConstantesSistema.ZERO){

				Collection<ClassificarPagamentosNaoClassificadosHelper> colecaoClassificarPagamentosNaoClassificadosHelper = fachada
								.classificarLotePagamentosNaoClassificados(helper, usuarioLogado, gerarDebitoACobrar);

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
										"exibirClassificarLotePagamentosNaoClassificadosAjusteDesoAction.do?menu=sim");
					}

					retorno = processarExibicaoRelatorio(relatorio, tipoRelatorio, httpServletRequest, httpServletResponse, actionMapping);

				}else{

					ActionServletException actionServletException = new ActionServletException(
									"atencao.erro_iniciar_processo_online",
									String.valueOf(Processo.RELATORIO_CLASSIFICAR_LOTE_PAGAMENTOS_NAO_CLASSIFICADOS)
													+ " - Relatório Classificar Lote Pagamentos Não classificados. Coleção de pagamentos vazia.");

					actionServletException.setUrlBotaoVoltar("/gsan/exibirClassificarLotePagamentosNaoClassificadosAjusteDesoAction.do");
					throw actionServletException;
				}
			}else{
				ActionServletException actionServletException = new ActionServletException("atencao.erro_lista_pagamento_vazia");

				actionServletException.setUrlBotaoVoltar("/gsan/exibirClassificarLotePagamentosNaoClassificadosAjusteDesoAction.do");
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
	private void ValidarReferencias(ClassificarLotePagamentosNaoClassificadosAjusteDesoActionForm form){

		if(!Util.isVazioOuBranco(form.getReferenciaPagamentoInicial())){
			if(!Util.validarMesAno(form.getReferenciaPagamentoInicial())
							|| this.referenciaPagamentoEmIntervaloPermitido(form.getReferenciaPagamentoInicial())){
				throw new ActionServletException("atencao.data_pagamento_invalida");
			}
		}

		if(!Util.isVazioOuBranco(form.getReferenciaPagamentoFinal())){
			if(!Util.validarMesAno(form.getReferenciaPagamentoFinal())
							|| this.referenciaPagamentoEmIntervaloPermitido(form.getReferenciaPagamentoFinal())){
				throw new ActionServletException("atencao.data_pagamento_invalida");
			}
		}
	}

	/**
	 * <p>
	 * [OC1402662]
	 * </p>
	 * 
	 * @author Magno Silveira (magno.silveira@procenge.com.br)
	 * @since 12/12/2014
	 * @param referenciaPagamentoInicial
	 * @return
	 */
	private boolean referenciaPagamentoEmIntervaloPermitido(String referenciaPagamento){

		Integer mesAno = Util.formatarMesAnoComBarraParaAnoMes(referenciaPagamento);
		Integer mesAnoMinimo = 200108;
		Integer mesAnoMaximo = 201112;

		return (Util.compararAnoMesReferencia(mesAno, mesAnoMinimo, "<") && Util.compararAnoMesReferencia(mesAno, mesAnoMaximo, ">"));
	}

	/**
	 * [UC3080] Classificar em Lote Pagamentos Não Classificados.
	 * [FS0019] – Validar data do pagamento
	 * 
	 * @param form
	 */
	private void ValidarDatas(ClassificarLotePagamentosNaoClassificadosAjusteDesoActionForm form){

		Date dataPagamentoMinima = Util.converteStringParaDate("25/10/2011", true);

		if(!Util.isVazioOuBranco(form.getDataPagamentoInicial())){
			Date dataPagamentoInicial = Util.converteStringParaDate(form.getDataPagamentoInicial(), true);

			if(Util.validarDiaMesAno(form.getDataPagamentoInicial())
							|| Util.compararDiaMesAno(dataPagamentoInicial, dataPagamentoMinima) == 1){
				throw new ActionServletException("atencao.data_pagamento_invalida");
			}
		}

		if(!Util.isVazioOuBranco(form.getDataPagamentoFinal())){
			Date dataPagamentoFinal = Util.converteStringParaDate(form.getDataPagamentoFinal(), true);

			if(Util.validarDiaMesAno(form.getDataPagamentoFinal()) || Util.compararDiaMesAno(dataPagamentoFinal, dataPagamentoMinima) == 1){
				throw new ActionServletException("atencao.data_pagamento_invalida");
			}
		}
	}

	private ClassificarLotePagamentosNaoClassificadosHelper converterFormularioParaHelper(
					ClassificarLotePagamentosNaoClassificadosAjusteDesoActionForm form){

		ClassificarLotePagamentosNaoClassificadosHelper helper = new ClassificarLotePagamentosNaoClassificadosHelper();

		helper.setOpcaoGeracao('C');
		helper.setLimiteMaximoDiferenca(new BigDecimal(999999999));

		if(!form.getLocalidade().equals(Integer.parseInt(ConstantesSistema.ZERO.toString()))){
			helper.setLocalidadeInicial(form.getLocalidade().getId());
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

		if(!Util.isVazioOuBranco(form.getPagamentoSituacao())){

			FiltroPagamentoSituacao filtroPagamentoSituacao = new FiltroPagamentoSituacao();
			filtroPagamentoSituacao.adicionarParametro(new ParametroSimples(FiltroPagamentoSituacao.CODIGO,
							PagamentoSituacao.PAGAMENTO_A_MENOR));
			Collection colecaoPagamentoSitucao = Fachada.getInstancia().pesquisar(filtroPagamentoSituacao,
							PagamentoSituacao.class.getName());

			if(!Util.isVazioOrNulo(colecaoPagamentoSitucao)){

				PagamentoSituacao pagamentoSituacao = (PagamentoSituacao) Util.retonarObjetoDeColecao(colecaoPagamentoSitucao);
				helper.setSituacaoPagamento(pagamentoSituacao.getId());
				helper.setDescricaoSitucaoPagamento(pagamentoSituacao.getDescricao());
			}
		}

		return helper;
	}
}
