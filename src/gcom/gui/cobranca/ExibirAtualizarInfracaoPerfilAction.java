
package gcom.gui.cobranca;

import java.util.Collection;

import gcom.cadastro.imovel.FiltroSubCategoria;
import gcom.cadastro.imovel.Subcategoria;
import gcom.cobranca.InfracaoPerfil;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.filtro.ParametroSimples;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ExibirAtualizarInfracaoPerfilAction
				extends GcomAction {

	/**
	 * 
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirAtualizarInfracaoPerfil");

		String idRegistroAtualizacao;

		InfracaoPerfilActionForm form = (InfracaoPerfilActionForm) actionForm;

		if(httpServletRequest.getAttribute("idRegistroAtualizacao") != null){
			idRegistroAtualizacao = (String) httpServletRequest.getAttribute("idRegistroAtualizacao");
		}else if(httpServletRequest.getParameter("idRegistroAtualizacao") != null){
			idRegistroAtualizacao = (String) httpServletRequest.getParameter("idRegistroAtualizacao");
		}else{
			idRegistroAtualizacao = form.getId();
		}

		HttpSession sessao = httpServletRequest.getSession();

		final Fachada fachada = Fachada.getInstancia();

		InfracaoPerfil bean = (InfracaoPerfil) fachada.pesquisar(Integer.valueOf(idRegistroAtualizacao), InfracaoPerfil.class);

		if(httpServletRequest.getParameter("pesquisaSubCat") == null || !httpServletRequest.getParameter("pesquisaSubCat").equals("1")){
			form.setId(bean.getId().toString());
			form.setIdCategoria(bean.getCategoria().getId().toString());
			form.setIdSubcategoria(bean.getSubcategoria().getId().toString());
			form.setIdImovelPerfil(bean.getImovelPerfil().getId().toString());
			form.setIdInfracaoTipo(bean.getInfracaoTipo().getId().toString());
			Collection colecaoSubcategoria = pesquisaSubcategoria(form.getIdCategoria());
			sessao.setAttribute("colecaoSubcategoria", colecaoSubcategoria);
		}else{
			Collection colecaoSubcategoria = pesquisaSubcategoria(form.getIdCategoria());
			sessao.setAttribute("colecaoSubcategoria", colecaoSubcategoria);
		}

		return retorno;
	}

	private Collection pesquisaSubcategoria(String idCategoria){

		Fachada fachada = Fachada.getInstancia();
		FiltroSubCategoria filtroSubcategoria = new FiltroSubCategoria();
		filtroSubcategoria
						.adicionarParametro(new ParametroSimples(FiltroSubCategoria.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));
		filtroSubcategoria.adicionarParametro(new ParametroSimples(FiltroSubCategoria.CATEGORIA_ID, idCategoria));
		return fachada.pesquisar(filtroSubcategoria, Subcategoria.class.getName());
	}

}
