
package gcom.gui.cobranca;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import gcom.fachada.Fachada;
import gcom.gui.GcomAction;

public class ExibirPesquisarCronogramaAcaoCobrancaAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirPesquisarCronogramaAcaoCobrancaAction");

		Fachada fachada = Fachada.getInstancia();
		HttpSession sessao = httpServletRequest.getSession(false);

		// CARREGAR AS COBRANÇAS GRUPO
		if(sessao.getAttribute("colecaoGrupoCobranca") == null){
			sessao.setAttribute("colecaoGrupoCobranca", fachada.obterColecaoCobrancaGrupo());
		}

		// CARREGAR AS COBRANÇAS ATIVIDADE
		if(sessao.getAttribute("colecaoAtividadeCobranca") == null){
			sessao.setAttribute("colecaoAtividadeCobranca", fachada.obterColecaoCobrancaAtividade());
		}

		// CARREGAR AS COBRANÇAS ACAO
		if(sessao.getAttribute("colecaoAcaoCobranca") == null){
			sessao.setAttribute("colecaoAcaoCobranca", fachada.obterColecaoCobrancaAcao());
		}
		return retorno;

	}
}
