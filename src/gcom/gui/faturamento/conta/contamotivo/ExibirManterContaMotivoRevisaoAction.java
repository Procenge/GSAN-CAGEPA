
package gcom.gui.faturamento.conta.contamotivo;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import gcom.faturamento.conta.ContaMotivoRevisao;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;

public class ExibirManterContaMotivoRevisaoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirManterContaMotivoRevisao");
		HttpSession sessao = httpServletRequest.getSession(false);
		Collection<ContaMotivoRevisao> colecaoContaMotivoRevisao = (Collection<ContaMotivoRevisao>) sessao
						.getAttribute("contasMotivoRevisao");
		if(colecaoContaMotivoRevisao == null || colecaoContaMotivoRevisao.size() == 0){
			throw new ActionServletException("atencao.pesquisa.nenhumresultado");
		}
		return retorno;
	}
}
