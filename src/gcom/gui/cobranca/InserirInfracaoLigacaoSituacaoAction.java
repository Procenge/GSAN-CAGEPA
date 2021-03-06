
package gcom.gui.cobranca;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;

/**
 * @author anishimura
 * @date fevereiro/2011
 */
public class InserirInfracaoLigacaoSituacaoAction
				extends GcomAction {

	/**
	 * 
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("telaSucesso");

		Fachada fachada = Fachada.getInstancia();

		InfracaoLigacaoSituacaoActionForm form = (InfracaoLigacaoSituacaoActionForm) actionForm;

		if(form.getDescricao() == null || form.getDescricao().equals("")){
			throw new ActionServletException("atencao.informe_campo", "a descri��o do tipo de infra��o");
		}

		if(form.getDescricaoAbreviada() == null || form.getDescricaoAbreviada().equals("")){
			throw new ActionServletException("atencao.informe_campo", "a descri��o abreviada do tipo de infra��o");
		}

		fachada.inserirInfracaoLigacaoSituacao(form.getDescricao(), form.getDescricaoAbreviada());

		montarPaginaSucesso(httpServletRequest, "Situacao da infra�ao da liga��o  " + form.getDescricao() + " inserido com sucesso.",
						"Inserir outra situa��o da infra�ao da ligacao", "exibirInserirInfracaoLigacaoSituacaoAction.do?menu=sim", null,
						null);

		return retorno;
	}
}
