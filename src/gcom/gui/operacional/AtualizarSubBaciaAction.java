
package gcom.gui.operacional;

import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.operacional.SubBacia;
import gcom.seguranca.acesso.usuario.Usuario;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Atualizar SubBacia
 * 
 * @author Bruno Ferreira dos Santos
 * @date 31/01/2011
 */

public class AtualizarSubBaciaAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("telaSucesso");

		AtualizarSubBaciaActionForm atualizarSubBaciaActionForm = (AtualizarSubBaciaActionForm) actionForm;
		Fachada fachada = Fachada.getInstancia();

		HttpSession sessao = httpServletRequest.getSession(false);

		Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");

		SubBacia subBacia = (SubBacia) sessao.getAttribute("subBacia");

		// Atualiza a entidade com os valores do formulário
		atualizarSubBaciaActionForm.setFormValues(subBacia);

		// atualiza na base de dados de Sistema Esgoto
		fachada.atualizarSubBacia(subBacia, usuarioLogado);

		// Monta a página de sucesso
		montarPaginaSucesso(httpServletRequest, "Sub-Bacia de código " + subBacia.getCodigo() + " alterado com sucesso",
						"Realizar outra Manutenção de Sub-Bacia", "exibirFiltrarSubBaciaAction.do?menu=sim");

		return retorno;
	}

}
