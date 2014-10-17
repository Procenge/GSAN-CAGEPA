
package gcom.gui.atendimentopublico.registroatendimento;

import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ExibirInserirMeioSolicitacaoAction
				extends GcomAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
					throws Exception{

		ActionForward retorno = mapping.findForward("exibirInserirMeioSolicitacao");

		MeioSolicitacaoActionForm meioSolicitacaoActionForm = (MeioSolicitacaoActionForm) form;

		meioSolicitacaoActionForm.setDescricao("");
		meioSolicitacaoActionForm.setDescricaoAbreviada("");
		meioSolicitacaoActionForm.setIndicadorLiberacaoDocIdent(String.valueOf(ConstantesSistema.SIM));

		return retorno;
	}

}
