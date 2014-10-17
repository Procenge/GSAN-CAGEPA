
package gcom.gui.cobranca;

import gcom.cobranca.FiltroInfracaoPerfil;
import gcom.cobranca.InfracaoPerfil;
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

public class ExibirManterInfracaoPerfilAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirManterInfracaoPerfil");

		Fachada fachada = Fachada.getInstancia();

		HttpSession sessao = httpServletRequest.getSession(false);

		FiltroInfracaoPerfil filtro = null;

		Collection colecaoInfracaoPerfil = null;

		if(httpServletRequest.getAttribute("filtro") != null){
			filtro = (FiltroInfracaoPerfil) httpServletRequest.getAttribute("filtro");
		}else{
			filtro = new FiltroInfracaoPerfil();

			if(fachada.registroMaximo(InfracaoPerfil.class) > ConstantesSistema.NUMERO_MAXIMO_REGISTROS_MANUTENCAO){
				retorno = actionMapping.findForward("filtrarInfracaoPerfil");
			}
		}

		if(retorno.getName().equalsIgnoreCase("exibirManterInfracaoPerfil")){
			filtro.setCampoOrderBy(FiltroInfracaoPerfil.ID);

			Map resultado = controlarPaginacao(httpServletRequest, retorno, filtro, InfracaoPerfil.class.getName());
			colecaoInfracaoPerfil = (Collection) resultado.get("colecaoRetorno");
			retorno = (ActionForward) resultado.get("destinoActionForward");
			if(colecaoInfracaoPerfil == null || colecaoInfracaoPerfil.isEmpty()){
				throw new ActionServletException("atencao.naocadastrado", null, "Perfil de Infração");
			}

			sessao.setAttribute("colecaoInfracaoPerfil", colecaoInfracaoPerfil);

		}
		return retorno;
	}
}
