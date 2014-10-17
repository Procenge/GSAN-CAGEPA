/**
 * 
 */

package gcom.gui.cadastro.cliente;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

import gcom.fachada.Fachada;
import gcom.gui.GcomAction;

/**
 * @author Bruno Ferreira dos Santos
 */
public class InserirClienteResponsavelAction
				extends GcomAction {

	/**
	 * 
	 */
	public InserirClienteResponsavelAction() {

		// TODO Auto-generated constructor stub
	}

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// localiza o action no objeto actionmapping
		ActionForward retorno = actionMapping.findForward("gerenciadorProcesso");

		// Pega o actionform da sessão
		HttpSession sessao = httpServletRequest.getSession(false);
		DynaValidatorForm form = (DynaValidatorForm) sessao.getAttribute("ClienteActionForm");

		Fachada fachada = Fachada.getInstancia();

		return retorno;
	}

}
