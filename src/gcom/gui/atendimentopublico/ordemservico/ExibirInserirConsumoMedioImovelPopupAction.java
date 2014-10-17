
package gcom.gui.atendimentopublico.ordemservico;

import gcom.gui.GcomAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC0711] Filtro para Emiss�o de Ordens Seletivas - Consumo M�dio do Im�vel
 * 
 * @author Hebert Falc�o
 * @date 13/05/2011
 */
public class ExibirInserirConsumoMedioImovelPopupAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirInserirConsumoMedioImovelPopup");

		// Obt�m a inst�ncia da sess�o
		HttpSession sessao = httpServletRequest.getSession(false);

		ConsumoMedioImovelPopupActionForm consumoMedioImovelPopupActionForm = (ConsumoMedioImovelPopupActionForm) actionForm;
		consumoMedioImovelPopupActionForm.setConsumoMedioInicial("");
		consumoMedioImovelPopupActionForm.setConsumoMedioFinal("");

		sessao.removeAttribute("closePage");

		return retorno;
	}

}
