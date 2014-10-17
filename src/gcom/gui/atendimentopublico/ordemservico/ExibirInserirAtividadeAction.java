
package gcom.gui.atendimentopublico.ordemservico;

import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ExibirInserirAtividadeAction
				extends GcomAction {

	@Override
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("atividadeInserir");

		AtividadeActionForm atividadeActionForm = (AtividadeActionForm) actionForm;

		atividadeActionForm.setDescricao("");
		atividadeActionForm.setDescricaoAbreviada("");
		atividadeActionForm.setIndicadorAtividadeUnica(String.valueOf(ConstantesSistema.NAO));
		if(Fachada.getInstancia().verificarExistenciaAtividadeUnica(ConstantesSistema.SIM.toString()) != null){
			atividadeActionForm.setVerificadorAtividadeUnica("Existe");
		}

		return retorno;
	}

}
