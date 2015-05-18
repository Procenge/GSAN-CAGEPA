
package gcom.gui.atendimentopublico.ordemservico;

import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ExibirInserirOsProgramNaoEncerMotivoAction
				extends GcomAction {

	@Override
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("osProgramNaoEncerMotivoInserir");

		InserirOsProgramNaoEncerMotivoActionForm form = (InserirOsProgramNaoEncerMotivoActionForm) actionForm;

		form.setDescricao("");
		form.setDescricaoAbreviada("");
		form.setIndicadorCobraVisitaImprodutiva(String.valueOf(ConstantesSistema.NAO));
		form.setIndicadorVisitaImprodutiva(String.valueOf(ConstantesSistema.NAO));
		form.setIndicadorUso(String.valueOf(ConstantesSistema.NAO));

		return retorno;
	}

}
