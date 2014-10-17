
package gcom.gui.atendimentopublico.registroatendimento;

import gcom.atendimentopublico.registroatendimento.FiltroMeioSolicitacao;
import gcom.atendimentopublico.registroatendimento.MeioSolicitacao;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ExibirManterMeioSolicitacaoAction
				extends GcomAction {

	@Override
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("manterMeioSolicitacao");

		HttpSession sessao = httpServletRequest.getSession(false);

		FiltroMeioSolicitacao filtroMeioSolicitacao = (FiltroMeioSolicitacao) sessao.getAttribute("filtroMeioSolicitacao");

		Collection colecaoMeioSolicitacao = new ArrayList();
		if(filtroMeioSolicitacao != null && !filtroMeioSolicitacao.equals("")){
			Map resultado = controlarPaginacao(httpServletRequest, retorno, filtroMeioSolicitacao, MeioSolicitacao.class.getName());
			colecaoMeioSolicitacao = (Collection) resultado.get("colecaoRetorno");
			retorno = (ActionForward) resultado.get("destinoActionForward");
		}

		if(colecaoMeioSolicitacao != null && !colecaoMeioSolicitacao.isEmpty()){

			if(colecaoMeioSolicitacao.size() == 1
							&& (httpServletRequest.getParameter("page.offset") == null || httpServletRequest.getParameter("page.offset")
											.equals("1"))){

				if(httpServletRequest.getAttribute("atualizar") != null && httpServletRequest.getAttribute("atualizar").equals("1")){
					retorno = actionMapping.findForward("exibirAtualizarMeioSolicitacao");
					MeioSolicitacao meioSolicitacao = (MeioSolicitacao) colecaoMeioSolicitacao.iterator().next();
					sessao.setAttribute("meioSolicitacao", meioSolicitacao);
					httpServletRequest.setAttribute("filtrar", "sim");
				}else{
					httpServletRequest.setAttribute("colecaoMeioSolicitacao", colecaoMeioSolicitacao);
				}
			}else{
				httpServletRequest.setAttribute("colecaoMeioSolicitacao", colecaoMeioSolicitacao);
			}
		}else{
			// Caso a pesquisa não retorne nenhum objeto comunica ao usuário;
			throw new ActionServletException("atencao.pesquisa.nenhumresultado");
		}

		return retorno;
	}

}
