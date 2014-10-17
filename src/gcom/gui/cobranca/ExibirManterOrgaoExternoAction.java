
package gcom.gui.cobranca;

import gcom.cobranca.FiltroOrgaoExterno;
import gcom.cobranca.OrgaoExterno;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;

import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

public class ExibirManterOrgaoExternoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("manterOrgaoExterno");
		Fachada fachada = Fachada.getInstancia();

		DynaActionForm pesquisarActionForm = (DynaActionForm) actionForm;

		Collection colecaoOrgaoExterno = null;

		HttpSession sessao = httpServletRequest.getSession(false);

		FiltroOrgaoExterno filtro = null;

		if(sessao.getAttribute("i") != null){
			sessao.removeAttribute("i");
		}

		if(httpServletRequest.getAttribute("filtro") != null){
			filtro = (FiltroOrgaoExterno) httpServletRequest.getAttribute("filtro");
		}else{

			filtro = new FiltroOrgaoExterno();

			if(fachada.registroMaximo(OrgaoExterno.class) > ConstantesSistema.NUMERO_MAXIMO_REGISTROS_MANUTENCAO){
				retorno = actionMapping.findForward("filtrarOrgaoExterno");

				// limpa os parametros do form pesquisar
				pesquisarActionForm.set("codigo", "");
				pesquisarActionForm.set("descricao", "");
				pesquisarActionForm.set("indicadorUso", "");

			}
		}

		if(retorno.getName().equalsIgnoreCase("manterOrgaoExterno")){
			// Seta a ordenação desejada do filtro
			filtro.setCampoOrderBy(FiltroOrgaoExterno.DESCRICAO);

			Map resultado = controlarPaginacao(httpServletRequest, retorno, filtro, OrgaoExterno.class.getName());
			colecaoOrgaoExterno = (Collection) resultado.get("colecaoRetorno");
			retorno = (ActionForward) resultado.get("destinoActionForward");

			if(colecaoOrgaoExterno == null || colecaoOrgaoExterno.isEmpty()){
				throw new ActionServletException("atencao.naocadastrado", null, "bairro");
			}

			sessao.setAttribute("colecaoOrgaoExterno", colecaoOrgaoExterno);

		}
		return retorno;

	}

}
