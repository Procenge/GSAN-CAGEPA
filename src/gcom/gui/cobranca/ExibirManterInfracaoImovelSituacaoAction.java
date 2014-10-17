
package gcom.gui.cobranca;

import gcom.cobranca.FiltroInfracaoImovelSituacao;
import gcom.cobranca.InfracaoImovelSituacao;
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
 * @date fevereiro/2011
 */
public class ExibirManterInfracaoImovelSituacaoAction
				extends GcomAction {

	/**
	 * @date fevereiro/2011
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirManterInfracaoImovelSituacao");

		Fachada fachada = Fachada.getInstancia();

		Collection colecaoInfracaoImovelSituacao = null;

		HttpSession sessao = httpServletRequest.getSession(false);

		FiltroInfracaoImovelSituacao filtro = null;

		if(sessao.getAttribute("i") != null){
			sessao.removeAttribute("i");
		}

		if(httpServletRequest.getAttribute("filtro") != null){
			filtro = (FiltroInfracaoImovelSituacao) httpServletRequest.getAttribute("filtro");
		}else{

			filtro = new FiltroInfracaoImovelSituacao();

			if(fachada.registroMaximo(InfracaoImovelSituacao.class) > ConstantesSistema.NUMERO_MAXIMO_REGISTROS_MANUTENCAO){
				retorno = actionMapping.findForward("filtrarInfracaoImovelSituacao");
			}
		}

		if(retorno.getName().equalsIgnoreCase("exibirManterInfracaoImovelSituacao")){
			filtro.setCampoOrderBy(FiltroInfracaoImovelSituacao.DESCRICAO);

			Map resultado = controlarPaginacao(httpServletRequest, retorno, filtro, InfracaoImovelSituacao.class.getName());
			colecaoInfracaoImovelSituacao = (Collection) resultado.get("colecaoRetorno");
			retorno = (ActionForward) resultado.get("destinoActionForward");
			if(colecaoInfracaoImovelSituacao == null || colecaoInfracaoImovelSituacao.isEmpty()){
				throw new ActionServletException("atencao.naocadastrado", null, "situacao da infraçao do imovel");
			}
			sessao.setAttribute("colecaoInfracaoImovelSituacao", colecaoInfracaoImovelSituacao);
		}
		return retorno;
	}
}