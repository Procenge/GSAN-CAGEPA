package gcom.gui.cadastro.endereco;

import gcom.cadastro.endereco.Cep;
import gcom.cadastro.endereco.FiltroCep;
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
 * @author Ado Rocha
 * @date jul/2013
 */
public class ExibirManterCepAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirManterCep");

		Fachada fachada = Fachada.getInstancia();

		Collection colecaoCep = null;

		HttpSession sessao = httpServletRequest.getSession(false);

		FiltroCep filtro = null;

		if(sessao.getAttribute("i") != null){
			sessao.removeAttribute("i");
		}

		if(httpServletRequest.getAttribute("filtroCep") != null){
			filtro = (FiltroCep) httpServletRequest.getAttribute("filtroCep");
		}else{

			filtro = new FiltroCep();

			if(fachada.registroMaximo(Cep.class) > ConstantesSistema.NUMERO_MAXIMO_REGISTROS_MANUTENCAO){
				retorno = actionMapping.findForward("filtrarCep");
			}
		}

		if(retorno.getName().equalsIgnoreCase("exibirManterCep")){
			filtro.setCampoOrderBy(FiltroCep.CODIGO, FiltroCep.LOGRADOURO);

			Map resultado = controlarPaginacao(httpServletRequest, retorno, filtro, Cep.class.getName());
			colecaoCep = (Collection) resultado.get("colecaoRetorno");
			retorno = (ActionForward) resultado.get("destinoActionForward");
			if(colecaoCep == null || colecaoCep.isEmpty()){
				throw new ActionServletException("atencao.naocadastrado", null, "o Cep");
			}
			sessao.setAttribute("colecaoCep", colecaoCep);
		}
		return retorno;
	}
}