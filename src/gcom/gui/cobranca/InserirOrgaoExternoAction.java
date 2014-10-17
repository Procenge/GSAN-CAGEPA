
package gcom.gui.cobranca;

import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

public class InserirOrgaoExternoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("telaSucesso");

		DynaActionForm inserirImovelActionForm = (DynaActionForm) actionForm;
		Fachada fachada = Fachada.getInstancia();
		if(inserirImovelActionForm.get("descricao") == null || inserirImovelActionForm.get("descricao").equals("")){
			throw new ActionServletException("atencao.informe_campo", "a descrição do orgão externo");
		}

		String descricao = (String) inserirImovelActionForm.get("descricao");

		fachada.inserirOrgaoExterno(descricao);

		montarPaginaSucesso(httpServletRequest, "Orgão Externo  " + descricao + " inserido com sucesso.", "Inserir outro Orgão Externo",
						"exibirInserirOrgaoExternoAction.do?menu=sim", null, null);

		return retorno;
	}
}
