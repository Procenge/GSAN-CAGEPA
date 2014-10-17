
package gcom.gui.cobranca;

import gcom.gui.GcomAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Exibir Filtrar Relatório Líquidos Recebíveis Action.
 */
public class ExibirFiltrarRelatorioLiquidosRecebiveisAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward findForward = actionMapping.findForward("exibirFiltrarRelatorioLiquidosRecebiveisAction");

		FiltroRelatorioLiquidosRecebiveisActionForm form = (FiltroRelatorioLiquidosRecebiveisActionForm) actionForm;

		form.setComando("3");

		return findForward;
	}
}