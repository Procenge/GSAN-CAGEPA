
package gcom.gerencial.arrecadacao;

import gcom.gui.GcomAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

/**
 * Action que faz a exibi��o da tela para o usu�rio setar os par�metros
 * necess�rio para a gera��o do relat�rio
 */
public class ExibirGerarRelatorioPosicaoDiariaArrecadacaoAction
				extends GcomAction {

	/**
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("exibirGerarRelatorioPosicaoDiariaArrecadacaoAction");

		DynaActionForm dynaForm = (DynaActionForm) actionForm;

		return retorno;

	}
}
