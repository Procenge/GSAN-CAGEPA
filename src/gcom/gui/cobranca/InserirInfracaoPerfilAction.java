
package gcom.gui.cobranca;

import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;

import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class InserirInfracaoPerfilAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("telaSucesso");

		Fachada fachada = Fachada.getInstancia();

		HttpSession sessao = httpServletRequest.getSession();

		Collection colecaoInfracaoPerfil = (Collection) sessao.getAttribute("colecaoInfracaoPerfil");
		Map mapInfracaoPerfilDebitoTipo = (Map) sessao.getAttribute("mapInfracaoPerfilDebitoTipo");

		if(colecaoInfracaoPerfil == null || colecaoInfracaoPerfil.isEmpty()){
			throw new ActionServletException("atencao.sem_dados_para_inserir");
		}
		if(mapInfracaoPerfilDebitoTipo == null || mapInfracaoPerfilDebitoTipo.isEmpty()){
			throw new ActionServletException("atencao.informe_campo", "pelo menos um tipo de debito para o perfil de infração");
		}

		fachada.inserirInfracaoPerfil(colecaoInfracaoPerfil, mapInfracaoPerfilDebitoTipo);

		montarPaginaSucesso(httpServletRequest, "Perfil de infracao inserido com sucesso.", "Inserir outro perfil de infracao",
						"exibirInserirInfracaoPerfilAction.do?menu=sim", null, null);
		return retorno;
	}
}
