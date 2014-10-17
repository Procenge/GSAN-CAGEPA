/**
 * 
 */

package gcom.gui.util;

import gcom.arrecadacao.pagamento.FiltroPagamento;
import gcom.arrecadacao.pagamento.Pagamento;
import gcom.cobranca.DocumentoTipo;
import gcom.cobranca.parcelamento.FiltroParcelamentoItem;
import gcom.cobranca.parcelamento.ParcelamentoItem;
import gcom.fachada.Fachada;
import gcom.faturamento.conta.Conta;
import gcom.faturamento.conta.ContaHistorico;
import gcom.faturamento.conta.FiltroConta;
import gcom.faturamento.conta.FiltroContaHistorico;
import gcom.faturamento.debito.DebitoCreditoSituacao;
import gcom.util.filtro.MenorQue;
import gcom.util.filtro.ParametroNaoNulo;
import gcom.util.filtro.ParametroSimples;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import br.com.procenge.util.StrutsDispatchAction;

/**
 * @author ebandeira
 */
public class ServicosManutencaoAction
				extends StrutsDispatchAction {

	public ActionForward correcaoAcrescimos(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Cria a variável de retorno e seta o mapeamento para a tela de sucesso
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		httpServletRequest.setAttribute("labelPaginaSucesso", "OK.");
		httpServletRequest.setAttribute("mensagemPaginaSucesso", "Acrescimos Gerados.");
		httpServletRequest.setAttribute("caminhoFuncionalidade", "efetuarLoginAction.do");

		return retorno;
	}

	public ActionForward classificarPagamentos(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Cria a variável de retorno e seta o mapeamento para a tela de sucesso
		ActionForward retorno = actionMapping.findForward("telaSucesso");
		Fachada fachada = Fachada.getInstancia();

		Date inicio = new Date();

		// Consulta todos os pagamentos
		FiltroPagamento filtroPagamento = new FiltroPagamento();
		filtroPagamento.adicionarCaminhoParaCarregamentoEntidade(FiltroPagamento.DOCUMENTO_TIPO);
		filtroPagamento.adicionarCaminhoParaCarregamentoEntidade(FiltroPagamento.GUIA_PAGAMENTO_GERAL);
		filtroPagamento.adicionarCaminhoParaCarregamentoEntidade(FiltroPagamento.GUIA_PAGAMENTO);
		filtroPagamento.adicionarCaminhoParaCarregamentoEntidade(FiltroPagamento.DOCUMENTO_TIPO);
		filtroPagamento.adicionarParametro(new ParametroSimples("guiaPagamentoGeral.guiaPagamento.debitoCreditoSituacaoAtual.id",
						DebitoCreditoSituacao.ENTRADA_DE_PARCELAMENTO));
		filtroPagamento.adicionarParametro(new ParametroSimples(FiltroPagamento.DOCUMENTO_TIPO_ID, DocumentoTipo.GUIA_PAGAMENTO));
		Collection<Pagamento> colecaoPagamentos = fachada.pesquisar(filtroPagamento, Pagamento.class.getName());

		int qtdInicio = 0;
		int qtdFim = 0;

		// Tenta classificar todos os pagamentos
		if(colecaoPagamentos != null && !colecaoPagamentos.isEmpty()){

			qtdInicio = colecaoPagamentos.size();
			System.out.println("$$$$$$$$$$$$ Quantidade de Pagamentos Encontrados Inicio: " + qtdInicio);

			for(Pagamento pagamento : colecaoPagamentos){
				// fachada.classificarPagamentos(pagamento);
			}
		}

		// Consulta todos os pagamentos após classificação
		Collection<Pagamento> colecaoPagamentosDepois = fachada.pesquisar(filtroPagamento, Pagamento.class.getName());
		if(colecaoPagamentosDepois != null && !colecaoPagamentosDepois.isEmpty()){
			qtdFim = colecaoPagamentosDepois.size();
		}

		System.out.println("$$$$$$$$$$$$ Quantidade de Pagamentos Encontrados: " + qtdInicio);
		System.out.println("$$$$$$$$$$$$ Quantidade de Pagamentos Classificados: " + (qtdInicio - qtdFim));

		Date fim = new Date();
		long diferenca = (fim.getTime() - inicio.getTime()) / 1000;
		System.out.println(String.format("$$$$$$$$$$$$ Tempo total de execução: %02d:%02d", diferenca / 60, diferenca % 60));

		httpServletRequest.setAttribute("labelPaginaSucesso", "OK.");
		httpServletRequest.setAttribute("mensagemPaginaSucesso", "Qtd classificados: " + (qtdInicio - qtdFim));
		httpServletRequest.setAttribute("caminhoFuncionalidade", "efetuarLoginAction.do");

		return retorno;
	}

	public ActionForward corrigirParcelamento(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Cria a variável de retorno e seta o mapeamento para a tela de sucesso
		ActionForward retorno = actionMapping.findForward("telaSucesso");
		Fachada fachada = Fachada.getInstancia();

		String idParcelamento = httpServletRequest.getParameter("parcelamento");
		String idImovel = httpServletRequest.getParameter("imovel");
		String referencia = httpServletRequest.getParameter("referencia");

		FiltroParcelamentoItem filtroParcelamento = new FiltroParcelamentoItem();
		filtroParcelamento
						.adicionarParametro(new ParametroSimples(FiltroParcelamentoItem.PARCELAMENTO_ID, Integer.valueOf(idParcelamento)));
		filtroParcelamento.adicionarParametro(new ParametroNaoNulo(FiltroParcelamentoItem.CONTA_GERAL_ID));

		Collection<ContaHistorico> contaHistorico = new ArrayList();
		Collection<ParcelamentoItem> colecaoParcelamento = fachada.pesquisar(filtroParcelamento, ParcelamentoItem.class.getName());
		for(ParcelamentoItem parcelamentoItem : colecaoParcelamento){

			FiltroContaHistorico filtroContaHistorico = new FiltroContaHistorico();
			filtroContaHistorico
							.adicionarParametro(new ParametroSimples(FiltroContaHistorico.ID, parcelamentoItem.getContaGeral().getId()));

			Collection<ContaHistorico> colecaoContaHistorico = fachada.pesquisar(filtroContaHistorico, ContaHistorico.class.getName());
			contaHistorico.addAll(colecaoContaHistorico);
		}

		for(ContaHistorico contaHist : contaHistorico){
			System.out.println("Conta Hist:" + contaHist.getId());
		}

		FiltroConta filtroConta = new FiltroConta();
		filtroConta.adicionarParametro(new ParametroSimples(FiltroConta.IMOVEL_ID, Integer.valueOf(idImovel)));
		filtroConta.adicionarParametro(new MenorQue(FiltroConta.REFERENCIA, Integer.valueOf(referencia)));

		Collection<Conta> colecaoConta = fachada.pesquisar(filtroConta, Conta.class.getName());
		for(Conta conta : colecaoConta){
			System.out.println(conta.getId());
		}

		fachada.transferirContasHistoricoParaConta(contaHistorico, Integer.valueOf(referencia), DebitoCreditoSituacao.NORMAL);
		fachada.transferirContasParaHistorico(colecaoConta, Integer.valueOf(referencia));

		httpServletRequest.setAttribute("labelPaginaSucesso", "OK.");
		httpServletRequest.setAttribute("mensagemPaginaSucesso", "Parcelamento Corrigido: " + idParcelamento);
		httpServletRequest.setAttribute("caminhoFuncionalidade", "efetuarLoginAction.do");

		return retorno;
	}
}
