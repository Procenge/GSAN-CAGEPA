
package gcom.gui.faturamento.conta.contamotivo;

import gcom.fachada.Fachada;
import gcom.faturamento.conta.ContaMotivoRetificacao;
import gcom.faturamento.conta.FiltroContaMotivoRetificacao;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.Util;

import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ExibirManterContaMotivoRetificacaoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		Fachada fachada = Fachada.getInstancia();

		ActionForward retorno = actionMapping.findForward("exibirManterContaMotivoRetificacao");
		HttpSession sessao = httpServletRequest.getSession(false);

		String atualizar = (String) sessao.getAttribute("indicadorAtualizar");

		FiltroContaMotivoRetificacao filtroContaMotivoRetificacao = (FiltroContaMotivoRetificacao) sessao
						.getAttribute("filtroContaMotivoRetificacao");

		Collection colecaoPesquisa = fachada.pesquisar(filtroContaMotivoRetificacao, ContaMotivoRetificacao.class.getName());

		if(colecaoPesquisa != null && !colecaoPesquisa.isEmpty()){
			if(atualizar != null && colecaoPesquisa.size() == 1){
				ContaMotivoRetificacao contaMotivoRetificacao = (ContaMotivoRetificacao) Util.retonarObjetoDeColecao(colecaoPesquisa);
				httpServletRequest.setAttribute("idRegistroAtualizacao", contaMotivoRetificacao.getId().toString());
				httpServletRequest.setAttribute("inserir", "S");
				retorno = actionMapping.findForward("exibirAtualizarContaMotivoRetificacao");

			}else{
				// Componente de Paginação
				Map resultado = controlarPaginacao(httpServletRequest, retorno, filtroContaMotivoRetificacao,
								ContaMotivoRetificacao.class.getName());

				Collection colecao = (Collection) resultado.get("colecaoRetorno");

				sessao.setAttribute("colecao", colecao);

				retorno = (ActionForward) resultado.get("destinoActionForward");

				// [FS0001] Verificar existência de dados
				if(colecao == null || colecao.isEmpty()){
					throw new ActionServletException("atencao.pesquisa.nenhumresultado");
				}
			}
		}else{
			throw new ActionServletException("atencao.pesquisa.nenhumresultado");
		}

		return retorno;
	}
}
