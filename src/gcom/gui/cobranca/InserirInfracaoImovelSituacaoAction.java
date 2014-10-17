
package gcom.gui.cobranca;

import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class InserirInfracaoImovelSituacaoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("telaSucesso");

		Fachada fachada = Fachada.getInstancia();

		InfracaoImovelSituacaoActionForm form = (InfracaoImovelSituacaoActionForm) actionForm;

		if(form.getDescricao() == null || form.getDescricao().equals("")){
			throw new ActionServletException("atencao.informe_campo", "a descrição do tipo de infração");
		}

		if(form.getDescricaoAbreviada() == null || form.getDescricaoAbreviada().equals("")){
			throw new ActionServletException("atencao.informe_campo", "a descrição abreviada do tipo de infração");
		}

		fachada.inserirInfracaoImovelSituacao(form.getDescricao(), form.getDescricaoAbreviada());

		montarPaginaSucesso(httpServletRequest, "Situacao da infração do imóvel  " + form.getDescricao() + " inserido com sucesso.",
						"Inserir outra situação da infração do imovel", "exibirInserirInfracaoImovelSituacaoAction.do?menu=sim", null, null);

		return retorno;
	}
}
