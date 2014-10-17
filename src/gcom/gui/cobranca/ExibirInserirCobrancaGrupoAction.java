
package gcom.gui.cobranca;

import gcom.gui.GcomAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ExibirInserirCobrancaGrupoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("inserirCobrancaGrupo");

		CobrancaGrupoActionForm cobrancaGrupoActionForm = (CobrancaGrupoActionForm) actionForm;
		cobrancaGrupoActionForm.setAnoMesReferencia("");
		cobrancaGrupoActionForm.setId("");
		cobrancaGrupoActionForm.setDescricao("");
		cobrancaGrupoActionForm.setDescAbreviada("");
		cobrancaGrupoActionForm.setIndicadorUso("");

		return retorno;
	}
}
