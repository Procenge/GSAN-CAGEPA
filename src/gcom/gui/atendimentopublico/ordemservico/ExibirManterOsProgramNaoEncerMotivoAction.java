
package gcom.gui.atendimentopublico.ordemservico;

import gcom.atendimentopublico.ordemservico.FiltroOsProgramNaoEncerMotivo;
import gcom.atendimentopublico.ordemservico.OsProgramNaoEncerMotivo;
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

public class ExibirManterOsProgramNaoEncerMotivoAction
				extends GcomAction {

	@Override
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("manterOsProgramNaoEncerMotivo");

		HttpSession sessao = httpServletRequest.getSession(false);

		FiltroOsProgramNaoEncerMotivo filtroOsProgramNaoEncerMotivo = (FiltroOsProgramNaoEncerMotivo) sessao
						.getAttribute("filtroOsProgramNaoEncerMotivo");

		Collection colecaoOsProgramNaoEncerMotivo = new ArrayList();
		if(filtroOsProgramNaoEncerMotivo != null && !filtroOsProgramNaoEncerMotivo.equals("")){
			Map resultado = controlarPaginacao(httpServletRequest, retorno, filtroOsProgramNaoEncerMotivo,
							OsProgramNaoEncerMotivo.class.getName());
			colecaoOsProgramNaoEncerMotivo = (Collection) resultado.get("colecaoRetorno");
			retorno = (ActionForward) resultado.get("destinoActionForward");
		}

		if(colecaoOsProgramNaoEncerMotivo != null && !colecaoOsProgramNaoEncerMotivo.isEmpty()){

			if(colecaoOsProgramNaoEncerMotivo.size() == 1
							&& (httpServletRequest.getParameter("page.offset") == null || httpServletRequest.getParameter("page.offset")
											.equals("1"))){

				if(httpServletRequest.getAttribute("atualizar") != null && httpServletRequest.getAttribute("atualizar").equals("1")){
					retorno = actionMapping.findForward("exibirAtualizarOsProgramNaoEncerMotivo");
					OsProgramNaoEncerMotivo osProgramNaoEncerMotivo = (OsProgramNaoEncerMotivo) colecaoOsProgramNaoEncerMotivo.iterator()
									.next();
					sessao.setAttribute("osProgramNaoEncerMotivo", osProgramNaoEncerMotivo);
					httpServletRequest.setAttribute("filtrar", "sim");
				}else{
					httpServletRequest.setAttribute("colecaoOsProgramNaoEncerMotivo", colecaoOsProgramNaoEncerMotivo);
				}
			}else{
				httpServletRequest.setAttribute("colecaoOsProgramNaoEncerMotivo", colecaoOsProgramNaoEncerMotivo);
			}
		}else{
			// Caso a pesquisa não retorne nenhum objeto comunica ao usuário;
			throw new ActionServletException("atencao.pesquisa.nenhumresultado");
		}

		return retorno;
	}

}
