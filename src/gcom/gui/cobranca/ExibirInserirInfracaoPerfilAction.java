
package gcom.gui.cobranca;

import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.imovel.FiltroCategoria;
import gcom.cadastro.imovel.FiltroImovelPerfil;
import gcom.cadastro.imovel.FiltroSubCategoria;
import gcom.cadastro.imovel.ImovelPerfil;
import gcom.cadastro.imovel.Subcategoria;
import gcom.cobranca.InfracaoPerfil;
import gcom.cobranca.InfracaoTipo;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ExibirInserirInfracaoPerfilAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirInserirInfracaoPerfil");

		Fachada fachada = Fachada.getInstancia();

		HttpSession sessao = httpServletRequest.getSession();

		InfracaoPerfilActionForm form = (InfracaoPerfilActionForm) actionForm;

		FiltroCategoria filtroCategoria = new FiltroCategoria();
		filtroCategoria.adicionarParametro(new ParametroSimples(FiltroCategoria.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));
		Collection colecaoCategoria = fachada.pesquisar(filtroCategoria, Categoria.class.getName());
		sessao.setAttribute("colecaoCategoria", colecaoCategoria);

		if(httpServletRequest.getParameter("pesquisaSubCat") != null && httpServletRequest.getParameter("pesquisaSubCat").equals("1")){
			Collection colecaoSubcategoria = pesquisaSubcategoria(form.getIdCategoria());
			sessao.setAttribute("colecaoSubcategoria", colecaoSubcategoria);
		}

		if(httpServletRequest.getParameter("pesquisarInfracaoTipo") != null
						&& !httpServletRequest.getParameter("pesquisarInfracaoTipo").equals("")){
			pesquisarInfracaoTipo(httpServletRequest, fachada, form);
		}

		if(httpServletRequest.getParameter("remover") != null && !httpServletRequest.getParameter("remover").equals("")){
			removerItem(httpServletRequest, sessao);
		}

		FiltroImovelPerfil filtroImovelPerfil = new FiltroImovelPerfil();
		filtroImovelPerfil
						.adicionarParametro(new ParametroSimples(FiltroImovelPerfil.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));
		Collection colecaoImovelPerfil = fachada.pesquisar(filtroImovelPerfil, ImovelPerfil.class.getName());
		sessao.setAttribute("colecaoImovelPerfil", colecaoImovelPerfil);

		return retorno;
	}

	private void pesquisarInfracaoTipo(HttpServletRequest httpServletRequest, Fachada fachada, InfracaoPerfilActionForm form){

		InfracaoTipo infracaoTipo = (InfracaoTipo) fachada.pesquisar(Integer.valueOf(form.getIdInfracaoTipo()), InfracaoTipo.class);
		if(infracaoTipo == null){
			form.setDescricaoInfracaoTipo("INFRACAO TIPO NÃO CADASTRADO");
		}else{
			form.setDescricaoInfracaoTipo(infracaoTipo.getDescricao());
			httpServletRequest.setAttribute("infracaoTipoEncontrado", 1);
		}
	}

	private void removerItem(HttpServletRequest httpServletRequest, HttpSession sessao){

		Collection colecaoInfracaoPerfil = (Collection) sessao.getAttribute("colecaoInfracaoPerfil");
		for(InfracaoPerfil i : (Collection<InfracaoPerfil>) colecaoInfracaoPerfil){
			if(i.getId().equals(Integer.valueOf(httpServletRequest.getParameter("remover")))){
				colecaoInfracaoPerfil.remove(i);
				break;
			}
		}
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
