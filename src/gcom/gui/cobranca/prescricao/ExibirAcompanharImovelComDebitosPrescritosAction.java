
package gcom.gui.cobranca.prescricao;

import gcom.cobranca.bean.FiltroImoveisComDebitosPrescritosHelper;
import gcom.cobranca.bean.ImovelComDebitosPrescritosHelper;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC3140] Acompanhar Imóveis com Débitos Prescritos
 * 
 * @author Anderson Italo
 * @date 01/04/2014
 */
public class ExibirAcompanharImovelComDebitosPrescritosAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("acompanharImovelComDebitosPrescritos");
		HttpSession sessao = httpServletRequest.getSession(false);

		Collection<ImovelComDebitosPrescritosHelper> colecaoImovelComDebitosPrescritosHelper = null;
		Fachada fachada = Fachada.getInstancia();
		FiltroImoveisComDebitosPrescritosHelper filtroImoveisComDebitosPrescritosHelper = null;

		if(sessao.getAttribute("filtroImoveisComDebitosPrescritosHelper") != null){

			filtroImoveisComDebitosPrescritosHelper = (FiltroImoveisComDebitosPrescritosHelper) sessao
							.getAttribute("filtroImoveisComDebitosPrescritosHelper");
		}

		// Total de registros
		Integer totalRegistros = fachada.pesquisarQuantidadeImoveisComDebitosPrescritos(filtroImoveisComDebitosPrescritosHelper);

		if(totalRegistros == null || totalRegistros.intValue() == 0){
			
			// Nenhum registro encontrado
			ActionServletException ex = new ActionServletException("atencao.pesquisa.nenhumresultado", null,
							"Imóvel com Débitos Prescritos");
			ex.setUrlBotaoVoltar("/gsan/exibirFiltrarImoveisComDebitosPrescritosAction.do");
			throw ex;
		}

		// Paginação
		retorno = this.controlarPaginacao(httpServletRequest, retorno, totalRegistros);

		colecaoImovelComDebitosPrescritosHelper = fachada.pesquisarImoveisComDebitoPrescrito(filtroImoveisComDebitosPrescritosHelper,
						(Integer) httpServletRequest.getAttribute("numeroPaginasPesquisa"));
		sessao.setAttribute("colecaoImovelComDebitosPrescritosHelper", colecaoImovelComDebitosPrescritosHelper);
		sessao.setAttribute("caminhoRetornoVoltar", "/gsan/exibirAcompanharImovelComDebitosPrescritosAction.do");


		return retorno;
	}
}
