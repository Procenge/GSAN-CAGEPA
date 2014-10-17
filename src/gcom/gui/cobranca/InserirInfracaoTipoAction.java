
package gcom.gui.cobranca;

import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class InserirInfracaoTipoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("telaSucesso");

		Fachada fachada = Fachada.getInstancia();

		InfracaoTipoActionForm form = (InfracaoTipoActionForm) actionForm;

		if(form.getDescricao() == null || form.getDescricao().equals("")){
			throw new ActionServletException("atencao.informe_campo", "a descri��o do tipo de infra��o");
		}

		if(form.getDescricaoAbreviada() == null || form.getDescricaoAbreviada().equals("")){
			throw new ActionServletException("atencao.informe_campo", "a descri��o abreviada do tipo de infra��o");
		}

		fachada.inserirInfracaoTipo(form.getDescricao(), form.getDescricaoAbreviada());

		montarPaginaSucesso(httpServletRequest, "Tipo de infra��o  " + form.getDescricao() + " inserido com sucesso.",
						"Inserir outro Tipo de Infracao", "exibirInserirInfracaoTipoAction.do?menu=sim", null, null);

		return retorno;
	}
}
