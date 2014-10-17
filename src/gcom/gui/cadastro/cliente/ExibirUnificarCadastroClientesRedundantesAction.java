
package gcom.gui.cadastro.cliente;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import gcom.fachada.Fachada;
import gcom.gui.GcomAction;

public class ExibirUnificarCadastroClientesRedundantesAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirUnificarCadastroClientesRedundantesAction");
		UnificarCadastroClientesRedundantesActionForm form = (UnificarCadastroClientesRedundantesActionForm) actionForm;
		Fachada fachada = Fachada.getInstancia();
		HttpSession sessao = httpServletRequest.getSession();
		String pesquisa = httpServletRequest.getParameter("pesquisa");
		String correcao = httpServletRequest.getParameter("correcao");
		sessao.setMaxInactiveInterval(18000);

		if(pesquisa != null && pesquisa.equals("101")){
			Collection colecaoClientesRedundates = fachada.pesquisarCadastroClientesRedundantes();
			if(colecaoClientesRedundates != null){
				form.setNumeroRegistrosComRedundacia(String.valueOf(colecaoClientesRedundates.size()));
			}
			sessao.setAttribute("colecao", colecaoClientesRedundates);
		}
		if(correcao != null && correcao.equals("101")){
			Collection colecaoClientesRedundates;
			if(sessao.getAttribute("colecao") != null){
				colecaoClientesRedundates = (Collection) sessao.getAttribute("colecao");
				fachada.corrigirCadastroClientesRedundantes(colecaoClientesRedundates);
				retorno = actionMapping.findForward("telaSucesso");
				montarPaginaSucesso(httpServletRequest, "Operacao realizada com sucesso", "", "");
			}
		}
		return retorno;
	}
}
