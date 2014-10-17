
package gcom.gui.operacional;

import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.operacional.FiltroSubBacia;
import gcom.operacional.SubBacia;

import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Manter SubBacia
 * 
 * @author Bruno Ferreira dos Santos
 * @date 31/01/2011
 */

public class ExibirManterSubBaciaAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("manterSubBacia");

		HttpSession sessao = httpServletRequest.getSession(false);

		Collection colecaoSubBacia = null;

		// Parte da verificação do filtro
		FiltroSubBacia filtroSubBacia = null;

		// Verifica se o filtro foi informado pela página de filtragem
		if(sessao.getAttribute("filtroSubBaciaSessao") != null){
			filtroSubBacia = (FiltroSubBacia) sessao.getAttribute("filtroSubBaciaSessao");
		}

		if((retorno != null) && (retorno.getName().equalsIgnoreCase("manterSubBacia"))){

			filtroSubBacia.adicionarCaminhoParaCarregamentoEntidade("bacia");

			Map resultado = controlarPaginacao(httpServletRequest, retorno, filtroSubBacia, SubBacia.class.getName());
			colecaoSubBacia = (Collection) resultado.get("colecaoRetorno");
			retorno = (ActionForward) resultado.get("destinoActionForward");

			if(colecaoSubBacia == null || colecaoSubBacia.isEmpty()){
				throw new ActionServletException("atencao.pesquisa.nenhumresultado");
			}

			String identificadorAtualizar = (String) sessao.getAttribute("indicadorAtualizar");

			if(colecaoSubBacia.size() == 1 && identificadorAtualizar != null && !identificadorAtualizar.equals("")){
				// caso o resultado do filtro só retorne um registro
				// e o check box Atualizar estiver selecionado
				// o sistema não exibe a tela de manter, exibe a de atualizar
				retorno = actionMapping.findForward("exibirAtualizarSubBacia");
				SubBacia subBacia = (SubBacia) colecaoSubBacia.iterator().next();
				sessao.setAttribute("idSubBacia", subBacia.getId().toString());
				sessao.setAttribute("caminhoRetornoVoltar", "/gsan/exibirFiltrarSubBaciaAction.do");
			}else{
				sessao.setAttribute("colecaoSubBacia", colecaoSubBacia);
				sessao.setAttribute("caminhoRetornoVoltar", "/gsan/exibirManterSubBaciaAction.do");
			}

		}
		sessao.removeAttribute("tipoPesquisaRetorno");

		return retorno;

	}

}
