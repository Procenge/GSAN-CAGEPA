
package gcom.gui.operacional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.gui.ManutencaoRegistroActionForm;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.FachadaException;

/**
 * Remover SubBacia
 * 
 * @author Bruno Ferreira dos Santos
 * @date 31/01/2011
 */

public class RemoverSubBaciaAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ManutencaoRegistroActionForm manutencaoRegistroActionForm = (ManutencaoRegistroActionForm) actionForm;

		String[] ids = manutencaoRegistroActionForm.getIdRegistrosRemocao();

		ActionForward retorno = actionMapping.findForward("telaSucesso");

		Fachada fachada = Fachada.getInstancia();

		HttpSession sessao = httpServletRequest.getSession(false);

		Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");

		// mensagem de erro quando o usuário tenta excluir sem ter selecionado
		// nenhum registro
		if(ids == null || ids.length == 0){
			throw new ActionServletException("atencao.registros.nao_selecionados");
		}

		// Captura a Exceção para que, no caso de ser por dependência na exclusão, lança mensagem
		// explicativa.
		try{
			fachada.removerSubBacia(ids, usuarioLogado);
		}catch(FachadaException ex){
			if(ex.getMessage() != null && ex.getMessage().toString().equals("atencao.dependencias.existentes")){
				throw new ActionServletException("atencao.entidade.possui.dependentes", null, "Bacia");
			}else{
				throw new FachadaException(ex.getMessage(), ex, ex.getParametroMensagem());
			}
		}

		// Monta a página de sucesso
		if(retorno.getName().equalsIgnoreCase("telaSucesso")){
			montarPaginaSucesso(httpServletRequest, ids.length + " Sub-Bacia(s) removido(s) com sucesso.",
							"Realizar outra Manutenção de Sub-Bacia", "exibirFiltrarSubBaciaAction.do?menu=sim");
		}

		return retorno;

	}

}
