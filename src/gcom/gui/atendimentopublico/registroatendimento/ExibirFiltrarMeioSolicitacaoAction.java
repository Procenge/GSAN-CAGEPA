
package gcom.gui.atendimentopublico.registroatendimento;

import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ExibirFiltrarMeioSolicitacaoAction
				extends GcomAction {

	@Override
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		MeioSolicitacaoActionForm meioSolicitacaoActionForm = (MeioSolicitacaoActionForm) actionForm;

		meioSolicitacaoActionForm.setDescricao("");

		meioSolicitacaoActionForm.setDescricaoAbreviada("");

		meioSolicitacaoActionForm.setIndicadorLiberacaoDocIdent(String.valueOf(ConstantesSistema.SIM));

		meioSolicitacaoActionForm.setIndicadorUso(String.valueOf(ConstantesSistema.SIM));

		ActionForward retorno = actionMapping.findForward("filtrarMeioSolicitacao");

		return retorno;
	}

}
