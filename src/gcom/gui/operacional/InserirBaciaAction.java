
package gcom.gui.operacional;

import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.operacional.Bacia;
import gcom.seguranca.acesso.usuario.Usuario;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * UC0042 - Inserir Bacia
 * 
 * @author Hebert Falc�o
 * @created 27 de Janeiro de 2011
 */
public class InserirBaciaAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		// Obt�m a inst�ncia da fachada
		Fachada fachada = Fachada.getInstancia();

		// Obt�m a sess�o
		HttpSession sessao = httpServletRequest.getSession(false);
		Collection<Bacia> colecaoEnderecos = (Collection<Bacia>) sessao.getAttribute("colecaoEnderecos");

		InserirBaciaActionForm inserirBaciaActionForm = (InserirBaciaActionForm) actionForm;

		Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");

		// Atualiza a entidade com os valores do formul�rio
		Bacia bacia = new Bacia();

		inserirBaciaActionForm.setFormValues(bacia, colecaoEnderecos);

		Integer idBacia = fachada.inserirBacia(bacia, usuarioLogado);

		montarPaginaSucesso(httpServletRequest, "Bacia de c�digo " + bacia.getCodigo() + " inserida com sucesso.", "Inserir outra Bacia",
						"exibirInserirBaciaAction.do?menu=sim", "exibirAtualizarBaciaAction.do?baciaID=" + idBacia,
						"Atualizar Bacia Inserida");

		sessao.removeAttribute("colecaoSubsistemaEsgoto");
		sessao.removeAttribute("colecaoEnderecos");

		return retorno;
	}
}
