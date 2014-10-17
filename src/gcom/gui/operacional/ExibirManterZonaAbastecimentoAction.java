
package gcom.gui.operacional;

import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.operacional.FiltroZonaAbastecimento;
import gcom.operacional.ZonaAbastecimento;

import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Manter ZonaAbastecimento
 * 
 * @author Bruno Ferreira dos Santos
 * @date 02/02/2011
 */

public class ExibirManterZonaAbastecimentoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("manterZonaAbastecimento");

		HttpSession sessao = httpServletRequest.getSession(false);

		Collection colecaoZonaAbastecimento = null;

		// Parte da verificação do filtro
		FiltroZonaAbastecimento filtroZonaAbastecimento = null;

		// Verifica se o filtro foi informado pela página de filtragem
		if(sessao.getAttribute("filtroZonaAbastecimentoSessao") != null){
			filtroZonaAbastecimento = (FiltroZonaAbastecimento) sessao.getAttribute("filtroZonaAbastecimentoSessao");
		}

		if((retorno != null) && (retorno.getName().equalsIgnoreCase("manterZonaAbastecimento"))){

			filtroZonaAbastecimento.adicionarCaminhoParaCarregamentoEntidade(FiltroZonaAbastecimento.SISTEMA_ABASTECIMENTO);
			filtroZonaAbastecimento.adicionarCaminhoParaCarregamentoEntidade(FiltroZonaAbastecimento.DISTRITO_OPERACIONAL);
			filtroZonaAbastecimento.adicionarCaminhoParaCarregamentoEntidade(FiltroZonaAbastecimento.LOCALIDADE);

			Map resultado = controlarPaginacao(httpServletRequest, retorno, filtroZonaAbastecimento, ZonaAbastecimento.class.getName());
			colecaoZonaAbastecimento = (Collection) resultado.get("colecaoRetorno");
			retorno = (ActionForward) resultado.get("destinoActionForward");

			if(colecaoZonaAbastecimento == null || colecaoZonaAbastecimento.isEmpty()){
				throw new ActionServletException("atencao.pesquisa.nenhumresultado");
			}

			String identificadorAtualizar = (String) sessao.getAttribute("indicadorAtualizar");

			if(colecaoZonaAbastecimento.size() == 1 && identificadorAtualizar != null && !identificadorAtualizar.equals("")){
				// caso o resultado do filtro só retorne um registro
				// e o check box Atualizar estiver selecionado
				// o sistema não exibe a tela de manter, exibe a de atualizar
				retorno = actionMapping.findForward("exibirAtualizarZonaAbastecimento");
				ZonaAbastecimento zonaAbastecimento = (ZonaAbastecimento) colecaoZonaAbastecimento.iterator().next();
				sessao.setAttribute("idZonaAbastecimento", zonaAbastecimento.getId().toString());
				sessao.setAttribute("caminhoRetornoVoltar", "/gsan/exibirFiltrarZonaAbastecimentoAction.do");
			}else{
				sessao.setAttribute("colecaoZonaAbastecimento", colecaoZonaAbastecimento);
				sessao.setAttribute("caminhoRetornoVoltar", "/gsan/exibirFiltarZonaAbastecimentoAction.do");
			}

		}
		sessao.removeAttribute("tipoPesquisaRetorno");

		return retorno;

	}

}
