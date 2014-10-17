
package gcom.gui.atendimentopublico.registroatendimento;

import gcom.atendimentopublico.registroatendimento.EspecificacaoTramite;
import gcom.atendimentopublico.registroatendimento.FiltroTramiteEspecificacao;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;

import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Manter Trâmite Especificação
 * 
 * @author Hebert Falcão
 * @date 25/03/2011
 */
public class ExibirManterTramiteEspecificacaoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("exibirManterTramiteEspecificacaoAction");

		// Obtém a instância da fachada
		Fachada fachada = Fachada.getInstancia();

		// Obtém a sessão
		HttpSession sessao = httpServletRequest.getSession(false);

		Boolean atualizar = (Boolean) sessao.getAttribute("indicadorAtualizar");

		FiltroTramiteEspecificacao filtroTramiteEspecificacao = (FiltroTramiteEspecificacao) sessao
						.getAttribute("filtroTramiteEspecificacao");

		Collection<EspecificacaoTramite> colecaoPesquisa = fachada.pesquisar(filtroTramiteEspecificacao, EspecificacaoTramite.class
						.getName());

		if(colecaoPesquisa != null && !colecaoPesquisa.isEmpty()){
			if(atualizar != null && atualizar && colecaoPesquisa.size() == 1){
				retorno = actionMapping.findForward("atualizarTramiteEspecificacao");

				EspecificacaoTramite especificacaoTramite = (EspecificacaoTramite) colecaoPesquisa.iterator().next();

				Integer idEspecificacaoTramite = especificacaoTramite.getId();
				sessao.setAttribute("idEspecificacaoTramite", String.valueOf(idEspecificacaoTramite));

				sessao.setAttribute("especificacaoTramite", especificacaoTramite);
				sessao.setAttribute("voltar", "filtrar");
			}else{
				retorno = actionMapping.findForward("exibirManterTramiteEspecificacaoAction");

				// Aciona o controle de paginação para que sejam pesquisados apenas os registros que
				// aparecem na página
				Map resultado = controlarPaginacao(httpServletRequest, retorno, filtroTramiteEspecificacao, EspecificacaoTramite.class
								.getName());
				Collection colecaoTramiteEspecificacao = (Collection) resultado.get("colecaoRetorno");

				retorno = (ActionForward) resultado.get("destinoActionForward");

				if(colecaoTramiteEspecificacao == null || colecaoTramiteEspecificacao.isEmpty()){
					ActionServletException actionServletException = new ActionServletException("atencao.pesquisa.nenhumresultado");
					actionServletException.setUrlBotaoVoltar("/gsan/exibirFiltrarTramiteEspecificacaoAction.do");
					throw actionServletException;
				}

				sessao.setAttribute("colecaoTramiteEspecificacao", colecaoTramiteEspecificacao);
				sessao.removeAttribute("voltar");
			}
		}else{
			ActionServletException actionServletException = new ActionServletException("atencao.pesquisa.nenhumresultado");
			actionServletException.setUrlBotaoVoltar("/gsan/exibirFiltrarTramiteEspecificacaoAction.do");
			throw actionServletException;
		}

		sessao.removeAttribute("AtualizarTramiteEspecificacaoActionForm");

		return retorno;
	}

}