
package gcom.gui.cobranca;

import gcom.cobranca.FiltroInfracaoLigacaoSituacao;
import gcom.cobranca.InfracaoLigacaoSituacao;
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
public class ExibirManterInfracaoLigacaoSituacaoAction
				extends GcomAction {

	/**
	 * @date fevereiro/2011
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirManterInfracaoLigacaoSituacao");
		Fachada fachada = Fachada.getInstancia();

		Collection colecaoInfracaoLigacaoSituacao = null;

		HttpSession sessao = httpServletRequest.getSession(false);

		FiltroInfracaoLigacaoSituacao filtro = null;

		if(sessao.getAttribute("i") != null){
			sessao.removeAttribute("i");
		}

		if(httpServletRequest.getAttribute("filtro") != null){
			filtro = (FiltroInfracaoLigacaoSituacao) httpServletRequest.getAttribute("filtro");
		}else{

			filtro = new FiltroInfracaoLigacaoSituacao();

			if(fachada.registroMaximo(InfracaoLigacaoSituacao.class) > ConstantesSistema.NUMERO_MAXIMO_REGISTROS_MANUTENCAO){
				retorno = actionMapping.findForward("filtrarInfracaoLigacaoSituacao");
			}
		}

		if(retorno.getName().equalsIgnoreCase("exibirManterInfracaoLigacaoSituacao")){
			filtro.setCampoOrderBy(FiltroInfracaoLigacaoSituacao.DESCRICAO);

			Map resultado = controlarPaginacao(httpServletRequest, retorno, filtro, InfracaoLigacaoSituacao.class.getName());
			colecaoInfracaoLigacaoSituacao = (Collection) resultado.get("colecaoRetorno");
			retorno = (ActionForward) resultado.get("destinoActionForward");
			if(colecaoInfracaoLigacaoSituacao == null || colecaoInfracaoLigacaoSituacao.isEmpty()){
				throw new ActionServletException("atencao.naocadastrado", null, "situacao da infração da ligação");
			}
			sessao.setAttribute("colecaoInfracaoLigacaoSituacao", colecaoInfracaoLigacaoSituacao);
		}
		return retorno;
	}
}
