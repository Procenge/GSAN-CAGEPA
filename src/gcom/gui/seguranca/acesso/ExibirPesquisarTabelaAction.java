
package gcom.gui.seguranca.acesso;

import gcom.gui.GcomAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ExibirPesquisarTabelaAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("tabelaPesquisar");

		HttpSession sessao = httpServletRequest.getSession(false);

		PesquisarTabelaActionForm form = (PesquisarTabelaActionForm) actionForm;

		if(httpServletRequest.getParameter("caminhoRetornoTelaPesquisa") != null){
			sessao.setAttribute("caminhoRetornoTelaPesquisa", httpServletRequest.getParameter("caminhoRetornoTelaPesquisa"));
		}

		return retorno;

	}
}
