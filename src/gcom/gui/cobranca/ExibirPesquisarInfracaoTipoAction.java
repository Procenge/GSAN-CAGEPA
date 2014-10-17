
package gcom.gui.cobranca;

import gcom.gui.GcomAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ExibirPesquisarInfracaoTipoAction
				extends GcomAction {

	/**
	 * 
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("pesquisarInfracaoTipo");

		HttpSession sessao = httpServletRequest.getSession(false);

		if(httpServletRequest.getParameter("caminhoRetornoTelaPesquisaTipoInfracao") != null){
			sessao.setAttribute("caminhoRetornoTelaPesquisaTipoInfracao", httpServletRequest
							.getParameter("caminhoRetornoTelaPesquisaTipoInfracao"));
		}

		if(httpServletRequest.getAttribute("popup") != null){
			sessao.setAttribute("popup", httpServletRequest.getAttribute("popup"));
		}

		return retorno;
	}
}
