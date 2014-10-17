
package gcom.gui.atendimentopublico.ordemservico;

import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ExibirFiltrarAtividadeAction
				extends GcomAction {

	@Override
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		AtividadeActionForm atividadeActionForm = (AtividadeActionForm) actionForm;

		atividadeActionForm.setDescricao("");

		atividadeActionForm.setDescricaoAbreviada("");

		atividadeActionForm.setIndicadorAtividadeUnica(String.valueOf(ConstantesSistema.NAO));

		atividadeActionForm.setIndicadorUso(String.valueOf(ConstantesSistema.SIM));

		ActionForward retorno = actionMapping.findForward("filtrarAtividade");

		return retorno;
	}
}
