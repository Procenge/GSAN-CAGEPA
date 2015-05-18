
package gcom.gui.cobranca.cobrancaadministrativa;

import gcom.cadastro.imovel.ImovelCobrancaSituacao;
import gcom.cobranca.bean.FiltroImovelCobrancaAdministrativaHelper;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.Util;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC3060] Consultar Im�vel Cobran�a Administrativa
 * 
 * @author Anderson Italo
 * @date 16/09/2012
 */
public class ExibirManterImovelCobrancaAdministrativaAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("manterImovelCobrancaAdministrativa");
		HttpSession sessao = httpServletRequest.getSession(false);
		String action = httpServletRequest.getParameter("action");
		RetirarImovelCobrancaAdministrativaActionForm form = (RetirarImovelCobrancaAdministrativaActionForm) actionForm;

		// Formato do Relat�rio
		if(Util.isVazioOuBranco(form.getFormatoRelatorio())){
			form.setFormatoRelatorio("1");
		}

		if(!Util.isVazioOuBranco(action)){

			// Tela de sucesso da opera��o de retirada
			String[] idRegistrosRetirada = (String[]) sessao.getAttribute("idRegistrosRetirada");

			retorno = actionMapping.findForward("telaSucesso");

			montarPaginaSucesso(httpServletRequest, idRegistrosRetirada.length
							+ " Im�veis retirados da cobran�a administrativa com sucesso.",
							"Realizar outra Manuten��o de Im�vel em Cobran�a Administrativa",
							"exibirFiltrarImovelCobrancaAdministrativaAction.do?menu=sim");
		}else{

			Collection<ImovelCobrancaSituacao> colecaoImovelCobrancaSituacao = null;
			Fachada fachada = Fachada.getInstancia();
			FiltroImovelCobrancaAdministrativaHelper filtroImovelCobrancaAdministrativaHelper = null;

			if(sessao.getAttribute("filtroImovelCobrancaAdministrativaHelper") != null){

				filtroImovelCobrancaAdministrativaHelper = (FiltroImovelCobrancaAdministrativaHelper) sessao
								.getAttribute("filtroImovelCobrancaAdministrativaHelper");
			}

			// Remove da sess�o o indicador que fecha popup de retirada
			sessao.removeAttribute("closePage");
			sessao.removeAttribute("idRegistrosCancelamento");

			if((retorno != null) && (retorno.getName().equalsIgnoreCase("manterImovelCobrancaAdministrativa"))
							&& filtroImovelCobrancaAdministrativaHelper != null){

				Integer totalRegistros = fachada.pesquisarQuantidadeImovelCobrancaAdministrativa(filtroImovelCobrancaAdministrativaHelper);

				if(totalRegistros == 0){

					// Nenhuma registro encontrado
					ActionServletException ex = new ActionServletException("atencao.pesquisa.nenhumresultado", null,
									"Im�vel em Cobran�a Administrativa");
					ex.setUrlBotaoVoltar("/gsan/exibirFiltrarImovelCobrancaAdministrativaAction.do");
					throw ex;
				}

				// Pagina��o
				retorno = this.controlarPaginacao(httpServletRequest, retorno, totalRegistros);

				if(sessao.getAttribute("idsImoveisArquivo") != null){
					Collection<ImovelCobrancaSituacao> colecaoImovelCobrancaSituacaoTodos = fachada.pesquisarImovelCobrancaAdministrativa(
									filtroImovelCobrancaAdministrativaHelper, -1);
					sessao.removeAttribute("colecaoImovelCobrancaSituacaoTodos");
					sessao.setAttribute("colecaoImovelCobrancaSituacaoTodos", colecaoImovelCobrancaSituacaoTodos);
				}

				// <<Inclui>> [UC3070 - Filtrar Im�vel Cobran�a Administrativa]
				colecaoImovelCobrancaSituacao = fachada.pesquisarImovelCobrancaAdministrativa(filtroImovelCobrancaAdministrativaHelper,
								(Integer) httpServletRequest.getAttribute("numeroPaginasPesquisa"));

				sessao.removeAttribute("colecaoImovelCobrancaSituacao");
				sessao.setAttribute("colecaoImovelCobrancaSituacao", colecaoImovelCobrancaSituacao);
				sessao.setAttribute("caminhoRetornoVoltar", "/gsan/exibirManterImovelCobrancaAdministrativaAction.do");

			}
		}

		return retorno;
	}
}
