
package gcom.gui.cobranca;

import gcom.cobranca.FiltroInfracaoTipo;
import gcom.cobranca.InfracaoTipo;
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

/**
 * @author anishimura
 */
public class ExibirManterInfracaoTipoAction
				extends GcomAction {

	/**
	 * @author Andre Nishimura
	 * @date 18/02/2011
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirManterInfracaoTipo");
		Fachada fachada = Fachada.getInstancia();

		Collection colecaoInfracaoTipo = null;

		HttpSession sessao = httpServletRequest.getSession(false);

		FiltroInfracaoTipo filtro = null;

		if(sessao.getAttribute("i") != null){
			sessao.removeAttribute("i");
		}

		if(httpServletRequest.getAttribute("filtro") != null){
			filtro = (FiltroInfracaoTipo) httpServletRequest.getAttribute("filtro");
		}else{

			filtro = new FiltroInfracaoTipo();

			if(fachada.registroMaximo(InfracaoTipo.class) > ConstantesSistema.NUMERO_MAXIMO_REGISTROS_MANUTENCAO){
				retorno = actionMapping.findForward("filtrarInfracaoTipo");
			}
		}

		if(retorno.getName().equalsIgnoreCase("exibirManterInfracaoTipo")){
			filtro.setCampoOrderBy(FiltroInfracaoTipo.DESCRICAO);
			Map resultado = controlarPaginacao(httpServletRequest, retorno, filtro, InfracaoTipo.class.getName());
			colecaoInfracaoTipo = (Collection) resultado.get("colecaoRetorno");
			retorno = (ActionForward) resultado.get("destinoActionForward");
			if(colecaoInfracaoTipo == null || colecaoInfracaoTipo.isEmpty()){
				throw new ActionServletException("atencao.naocadastrado", null, "Tipo Infração");
			}
			sessao.setAttribute("colecaoInfracaoTipo", colecaoInfracaoTipo);
		}
		return retorno;
	}
}