
package gcom.gui.atendimentopublico.ordemservico;

import gcom.atendimentopublico.ordemservico.Atividade;
import gcom.atendimentopublico.ordemservico.FiltroAtividade;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ExibirManterAtividadeAction
				extends GcomAction {

	@Override
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("manterAtividade");

		HttpSession sessao = httpServletRequest.getSession(false);

		FiltroAtividade filtroAtividade = (FiltroAtividade) sessao.getAttribute("filtroAtividade");

		Collection colecaoAtividade = new ArrayList();
		if(filtroAtividade != null && !filtroAtividade.equals("")){
			Map resultado = controlarPaginacao(httpServletRequest, retorno, filtroAtividade, Atividade.class.getName());
			colecaoAtividade = (Collection) resultado.get("colecaoRetorno");
			retorno = (ActionForward) resultado.get("destinoActionForward");
		}

		if(colecaoAtividade != null && !colecaoAtividade.isEmpty()){

			if(colecaoAtividade.size() == 1
							&& (httpServletRequest.getParameter("page.offset") == null || httpServletRequest.getParameter("page.offset")
											.equals("1"))){

				if(httpServletRequest.getAttribute("atualizar") != null && httpServletRequest.getAttribute("atualizar").equals("1")){
					retorno = actionMapping.findForward("exibirAtualizarAtividade");
					Atividade atividade = (Atividade) colecaoAtividade.iterator().next();
					sessao.setAttribute("atividade", atividade);
					httpServletRequest.setAttribute("filtrar", "sim");
				}else{
					httpServletRequest.setAttribute("colecaoAtividade", colecaoAtividade);
				}
			}else{
				httpServletRequest.setAttribute("colecaoAtividade", colecaoAtividade);
			}
		}else{
			// Caso a pesquisa não retorne nenhum objeto comunica ao usuário;
			throw new ActionServletException("atencao.pesquisa.nenhumresultado");
		}

		return retorno;
	}

}
