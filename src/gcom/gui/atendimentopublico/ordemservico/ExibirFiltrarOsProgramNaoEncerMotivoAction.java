
package gcom.gui.atendimentopublico.ordemservico;

import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ExibirFiltrarOsProgramNaoEncerMotivoAction
				extends GcomAction {

	@Override
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		FiltrarOsProgramNaoEncerMotivoActionForm form = (FiltrarOsProgramNaoEncerMotivoActionForm) actionForm;

		form.setDescricao("");
		form.setDescricaoAbreviada("");
		form.setIndicadorUso(String.valueOf(ConstantesSistema.TODOS));

		ActionForward retorno = actionMapping.findForward("filtrarOsProgramNaoEncerMotivo");

		return retorno;
	}
}
