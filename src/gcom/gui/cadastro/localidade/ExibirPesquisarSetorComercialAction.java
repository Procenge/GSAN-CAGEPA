
package gcom.gui.cadastro.localidade;

import gcom.gui.GcomAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

/**
 * Action que define o pré-processamento da página de pesquisa do Setor Comercial
 * 
 * @author Saulo Lima
 * @created 20/06/2008
 */

public class ExibirPesquisarSetorComercialAction
				extends GcomAction {

	/**
	 * <<Descrição do método>>
	 * 
	 * @param actionMapping
	 *            Descrição do parâmetro
	 * @param actionForm
	 *            Descrição do parâmetro
	 * @param httpServletRequest
	 *            Descrição do parâmetro
	 * @param httpServletResponse
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("pesquisarSetorComercial");

		// Obtendo uma instancia da sessao
		HttpSession sessao = httpServletRequest.getSession(false);

		DynaValidatorForm pesquisarActionForm = (DynaValidatorForm) actionForm;

		// Verifica se o pesquisar SetorComercial foi chamado a partir do inserir SetorComercial
		// e em caso afirmativo recebe o parâmetro e manda-o pela sessão para
		// ser verificado no setor_comercial_resultado_pesquisa e depois retirado da
		// sessão no ExibirFiltrarSetorComercialAction
		if(httpServletRequest.getParameter("consulta") != null){
			String consulta = httpServletRequest.getParameter("consulta");
			sessao.setAttribute("consulta", consulta);
		}else{
			sessao.removeAttribute("consulta");
		}

		if(sessao.getAttribute("caminhoRetornoTelaPesquisaSetorComercial") != null){
			sessao.removeAttribute("caminhoRetornoTelaPesquisaSetorComercial");
		}

		if(httpServletRequest.getParameter("objetoConsulta") == null){
			pesquisarActionForm.set("indicadorUso", "");
			pesquisarActionForm.set("descricaoLocalidade", "");
			pesquisarActionForm.set("idMunicipio", "");
			pesquisarActionForm.set("nomeMunicipio", "");
			pesquisarActionForm.set("codigoSetorComercial", "");
			pesquisarActionForm.set("idSetorComercial", "");
			pesquisarActionForm.set("descricaoSetorComercial", "");
			pesquisarActionForm.set("tipoPesquisaDescricao", "");
			pesquisarActionForm.set("tipoPesquisaMunicipio", "");
		}

		String tipo = (String) httpServletRequest.getParameter("tipo");

		String idElo = (String) httpServletRequest.getParameter("idElo");

		sessao.setAttribute("tipoPesquisa", tipo);
		sessao.setAttribute("idElo", idElo);
		if(httpServletRequest.getParameter("menu") != null && !httpServletRequest.getParameter("menu").equals("")){
			sessao.removeAttribute("idElo");
		}

		String idLocalidade = httpServletRequest.getParameter("idLocalidade");
		String descricaoLocalidade = httpServletRequest.getParameter("descricaoLocalidade");
		if(idLocalidade != null && !idLocalidade.trim().equals("")){
			sessao.setAttribute("idLocalidade", idLocalidade);
			sessao.setAttribute("descricaoLocalidade", descricaoLocalidade);
		}

		// Envia uma flag que será verificado no
		// setor_comercial_resultado_pesquisa.jsp
		// para saber se irá usar o enviar dados ou o enviar dados parametros
		if(httpServletRequest.getParameter("caminhoRetornoTelaPesquisa") != null){
			sessao.setAttribute("caminhoRetornoTelaPesquisaSetorComercial", httpServletRequest.getParameter("caminhoRetornoTelaPesquisa"));
		}

		return retorno;
	}

}
