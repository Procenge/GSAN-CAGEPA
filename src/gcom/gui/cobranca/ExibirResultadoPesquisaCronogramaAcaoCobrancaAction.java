
package gcom.gui.cobranca;

import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import gcom.cobranca.CobrancaAcaoAtividadeCronograma;
import gcom.cobranca.FiltroCobrancaAcaoAtividadeCronograma;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;

public class ExibirResultadoPesquisaCronogramaAcaoCobrancaAction
				extends GcomAction {

	/**
	 * @author Andre Nishimura
	 * @date 22/06/2010
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirResultadoPesquisaCronogramaAcaoCobrancaAction");
		HttpSession sessao = httpServletRequest.getSession(false);

		FiltroCobrancaAcaoAtividadeCronograma filtroCobrancaAcaoAtividadeCronograma;
		if(sessao.getAttribute("filtroCobrancaAcaoAtividadeCronograma") != null){
			filtroCobrancaAcaoAtividadeCronograma = (FiltroCobrancaAcaoAtividadeCronograma) sessao
							.getAttribute("filtroCobrancaAcaoAtividadeCronograma");

			Collection colecaoCobrancaAcaoAtividadeCronograma;

			Map resultado = controlarPaginacao(httpServletRequest, retorno, filtroCobrancaAcaoAtividadeCronograma,
							CobrancaAcaoAtividadeCronograma.class.getName());
			colecaoCobrancaAcaoAtividadeCronograma = (Collection) resultado.get("colecaoRetorno");
			retorno = (ActionForward) resultado.get("destinoActionForward");

			if(colecaoCobrancaAcaoAtividadeCronograma == null || colecaoCobrancaAcaoAtividadeCronograma.isEmpty()){
				throw new ActionServletException("atencao.pesquisa.nenhumresultado", null, "Comando Ação de Cobrança - Cronograma");
			}
			sessao.setAttribute("colecaoCobrancaAcaoAtividadeCronograma", colecaoCobrancaAcaoAtividadeCronograma);
		}
		return retorno;
	}
}
