
package gcom.gui.arrecadacao.pagamento;

import gcom.arrecadacao.pagamento.FiltroPagamentoSituacao;
import gcom.arrecadacao.pagamento.PagamentoSituacao;
import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.Localidade;
import gcom.gui.GcomAction;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [OC1402662][NF] Tobias Barreto - Baixa de pagamento
 * <p>
 * Action responsável por exibir a página de Classificar em Lote Pagamentos Não Classificados para
 * Tobias Barreto.
 * </p>
 * [UC3080] Classificar em Lote Pagamentos Não Classificados.
 * 
 * @author Magno Silveira (magno.silveira@procenge.com.br)
 * @since 10/12/2014
 */
public class ExibirClassificarLotePagamentosNaoClassificadosAjusteDesoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("exibirClassificarLotePagamentosNaoClassificadosAjusteDesoAction");

		this.pesquisarLocalidadeTobiasBarreto(actionForm);

		// 2.6. Situação do Pagamento
		this.pesquisarPagamentoSituacaoAMenor(actionForm);

		return retorno;

	}

	private void pesquisarLocalidadeTobiasBarreto(ActionForm actionForm){

		ClassificarLotePagamentosNaoClassificadosAjusteDesoActionForm form = (ClassificarLotePagamentosNaoClassificadosAjusteDesoActionForm) actionForm;

		FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
		filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, 71));

		// Recupera Localidade
		Collection colecaoLocalidade = this.getFachada().pesquisar(filtroLocalidade, Localidade.class.getName());

		if(!Util.isVazioOrNulo(colecaoLocalidade)){

			Localidade localidade = (Localidade) Util.retonarObjetoDeColecao(colecaoLocalidade);
			form.setLocalidade(localidade);
		}
	}

	private void pesquisarPagamentoSituacaoAMenor(ActionForm actionForm){

		ClassificarLotePagamentosNaoClassificadosAjusteDesoActionForm form = (ClassificarLotePagamentosNaoClassificadosAjusteDesoActionForm) actionForm;

		FiltroPagamentoSituacao filtroPagamentoSituacao = new FiltroPagamentoSituacao();
		filtroPagamentoSituacao
						.adicionarParametro(new ParametroSimples(FiltroPagamentoSituacao.CODIGO, PagamentoSituacao.PAGAMENTO_A_MENOR));
		Collection colecaoPagamentoSituacao = this.getFachada().pesquisar(filtroPagamentoSituacao, PagamentoSituacao.class.getName());

		if(!Util.isVazioOrNulo(colecaoPagamentoSituacao)){
			PagamentoSituacao pagamentoSituacao = (PagamentoSituacao) Util.retonarObjetoDeColecao(colecaoPagamentoSituacao);
			form.setPagamentoSituacao(pagamentoSituacao);
		}
	}

}
