
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
 * Inserir SubBacia
 * 
 * @author Bruno Ferreira dos Santos
 * @created 31 de Janeiro de 2011
 */

public class InserirSubBaciaAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		// Obt�m a inst�ncia da fachada
		Fachada fachada = Fachada.getInstancia();

		// Obt�m a sess�o
		HttpSession sessao = httpServletRequest.getSession(false);

		InserirSubBaciaActionForm inserirSubBaciaActionForm = (InserirSubBaciaActionForm) actionForm;

		Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");

		// Atualiza a entidade com os valores do formul�rio
		SubBacia subBacia = new SubBacia();

		inserirSubBaciaActionForm.setFormValues(subBacia);

		Integer idSubBacia = fachada.inserirSubBacia(subBacia, usuarioLogado);

		montarPaginaSucesso(httpServletRequest, "Sub-Bacia de c�digo  " + subBacia.getCodigo() + " inserida com sucesso.",
						"Inserir outra Sub-Bacia", "exibirInserirSubBaciaAction.do?menu=sim",
						"exibirAtualizarSubBaciaAction.do?idRegistroInseridoAtualizar=" + idSubBacia, "Atualizar Sub-Bacia Inserida");

		sessao.removeAttribute("colecaoBacia");

		return retorno;
	}

}
