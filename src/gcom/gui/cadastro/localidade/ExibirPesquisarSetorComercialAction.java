
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
 * Action que define o pr�-processamento da p�gina de pesquisa do Setor Comercial
 * 
 * @author Saulo Lima
 * @created 20/06/2008
 */

public class ExibirPesquisarSetorComercialAction
				extends GcomAction {

	/**
	 * <<Descri��o do m�todo>>
	 * 
	 * @param actionMapping
	 *            Descri��o do par�metro
	 * @param actionForm
	 *            Descri��o do par�metro
	 * @param httpServletRequest
	 *            Descri��o do par�metro
	 * @param httpServletResponse
	 *            Descri��o do par�metro
	 * @return Descri��o do retorno
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("pesquisarSetorComercial");

		// Obtendo uma instancia da sessao
		HttpSession sessao = httpServletRequest.getSession(false);

		DynaValidatorForm pesquisarActionForm = (DynaValidatorForm) actionForm;

		// Verifica se o pesquisar SetorComercial foi chamado a partir do inserir SetorComercial
		// e em caso afirmativo recebe o par�metro e manda-o pela sess�o para
		// ser verificado no setor_comercial_resultado_pesquisa e depois retirado da
		// sess�o no ExibirFiltrarSetorComercialAction
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

		// Envia uma flag que ser� verificado no
		// setor_comercial_resultado_pesquisa.jsp
		// para saber se ir� usar o enviar dados ou o enviar dados parametros
		if(httpServletRequest.getParameter("caminhoRetornoTelaPesquisa") != null){
			sessao.setAttribute("caminhoRetornoTelaPesquisaSetorComercial", httpServletRequest.getParameter("caminhoRetornoTelaPesquisa"));
		}

		return retorno;
	}

}
