
package gcom.gui.cobranca.prescricao;

import gcom.cobranca.bean.FiltroImoveisComDebitosPrescritosHelper;
import gcom.cobranca.bean.ImovelComDebitosPrescritosHelper;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;

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

//		if((retorno != null) && (retorno.getName().equalsIgnoreCase("acompanharImovelComDebitosPrescritos"))
//						&& filtroImoveisComDebitosPrescritosHelper != null){
//
//			Integer totalRegistros = 0;
//
//			try{
//
//				totalRegistros = fachada.pesquisarQuantidadeImoveisComDebitosPrescritos(filtroImoveisComDebitosPrescritosHelper);
//			}catch(FachadaException e){
//
//				String[] parametros = new String[e.getParametroMensagem().size()];
//				e.getParametroMensagem().toArray(parametros);
//				ActionServletException ex = new ActionServletException(e.getMessage(), e, parametros);
//				ex.setUrlBotaoVoltar("/gsan/exibirFiltrarImoveisComDebitosPrescritosAction.do");
//				throw ex;
//			}
//
//			if(totalRegistros == 0){
//
//				// Nenhuma registro encontrado
//				ActionServletException ex = new ActionServletException("atencao.pesquisa.nenhumresultado", null,
//								"Imóvel com Débitos Prescritos");
//				ex.setUrlBotaoVoltar("/gsan/exibirFiltrarImoveisComDebitosPrescritosAction.do");
//				throw ex;
//			}
//
//			// Paginação
//			retorno = this.controlarPaginacao(httpServletRequest, retorno, totalRegistros);
//			
//			
//
//			// <<Inclui>> [UC3141 - Filtrar Imóveis com Débitos Prescritos]
//			try{
//
//				colecaoImovelComDebitosPrescritosHelper = fachada
//								.pesquisarImoveisComDebitoPrescrito(filtroImoveisComDebitosPrescritosHelper,
//							(Integer) httpServletRequest.getAttribute("numeroPaginasPesquisa"));
//			}catch(FachadaException e){
//
//				String[] parametros = new String[e.getParametroMensagem().size()];
//				e.getParametroMensagem().toArray(parametros);
//				ActionServletException ex = new ActionServletException(e.getMessage(), e, parametros);
//				ex.setUrlBotaoVoltar("/gsan/exibirFiltrarImoveisComDebitosPrescritosAction.do");
//				throw ex;
//			}
//			
//			
			
			//....................
			
			// Total de registros
			Integer totalRegistros = fachada.pesquisarQuantidadeImoveisComDebitosPrescritos(filtroImoveisComDebitosPrescritosHelper);

			if(totalRegistros == 0){
				// Nenhuma registro encontrado
				throw new ActionServletException("atencao.pesquisa.nenhumresultado", null, "Boleto Bancário");
			}

			// Paginação
			retorno = this.controlarPaginacao(httpServletRequest, retorno, totalRegistros);

			colecaoImovelComDebitosPrescritosHelper = fachada
							.pesquisarImoveisComDebitoPrescrito(filtroImoveisComDebitosPrescritosHelper,
						(Integer) httpServletRequest.getAttribute("numeroPaginasPesquisa"));


			if(colecaoImovelComDebitosPrescritosHelper == null || colecaoImovelComDebitosPrescritosHelper.isEmpty()){
				// Nenhuma cliente cadastrado
				throw new ActionServletException("atencao.pesquisa.nenhumresultado", null, "Boleto Bancário");
			}else if(colecaoImovelComDebitosPrescritosHelper.size() > ConstantesSistema.NUMERO_MAXIMO_REGISTROS_BOLETO_BANCARIO){
				// Muitos registros encontrados
				throw new ActionServletException("atencao.pesquisa.muitosregistros");
			}else{
				// Coloca a coleção na sessão
				sessao.setAttribute("colecaoImovelComDebitosPrescritosHelper", colecaoImovelComDebitosPrescritosHelper);

			}

			
			
			//..................
			
			sessao.setAttribute("caminhoRetornoVoltar", "/gsan/exibirAcompanharImovelComDebitosPrescritosAction.do");




		return retorno;
	}
}
