
package gcom.gui.cobranca;

import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.imovel.ImovelPerfil;
import gcom.cadastro.imovel.Subcategoria;
import gcom.cobranca.InfracaoPerfil;
import gcom.cobranca.InfracaoTipo;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class AdicionarInfracaoPerfilAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirInserirInfracaoPerfil");
		HttpSession sessao = httpServletRequest.getSession(false);

		Fachada fachada = Fachada.getInstancia();

		InfracaoPerfilActionForm form = (InfracaoPerfilActionForm) actionForm;

		Collection colecaoInfracaoPerfil;
		InfracaoPerfil infracaoPerfil = new InfracaoPerfil();

		Categoria categoria = new Categoria();
		Subcategoria subcategoria = new Subcategoria();
		ImovelPerfil imovelPerfil = new ImovelPerfil();
		InfracaoTipo infracaoTipo = new InfracaoTipo();

		if(StringUtils.isEmpty(form.getIdInfracaoTipo())){
			throw new ActionServletException("atencao.informe_campo", "Tipo de Infração");
		}
		if(form.getIdCategoria() != null && !form.getIdCategoria().equals("-1")){
			categoria = (Categoria) fachada.pesquisar(Integer.valueOf(form.getIdCategoria()), Categoria.class);
			if(categoria == null){
				throw new ActionServletException("atencao.pesquisa_inexistente", "Categoria");
			}
		}
		if(form.getIdSubcategoria() != null && !form.getIdSubcategoria().equals("-1")){
			subcategoria = (Subcategoria) fachada.pesquisar(Integer.valueOf(form.getIdSubcategoria()), Subcategoria.class);
			if(subcategoria == null){
				throw new ActionServletException("atencao.pesquisa_inexistente", "Subcategoria");
			}
		}
		if(form.getIdImovelPerfil() != null && !form.getIdImovelPerfil().equals("-1")){
			imovelPerfil = (ImovelPerfil) fachada.pesquisar(Integer.valueOf(form.getIdImovelPerfil()), ImovelPerfil.class);
			if(imovelPerfil == null){
				throw new ActionServletException("atencao.pesquisa_inexistente", "Perfil Imovel");
			}
		}
		if(form.getIdInfracaoTipo() != null){
			infracaoTipo = (InfracaoTipo) fachada.pesquisar(Integer.valueOf(form.getIdInfracaoTipo()), InfracaoTipo.class);
			if(infracaoTipo == null){
				throw new ActionServletException("atencao.pesquisa_inexistente", "Tipo de Infraçao");
			}
		}
		infracaoPerfil.setId(fachada.obterSequenceInfracaoPerfil());
		infracaoPerfil.setCategoria(categoria);
		infracaoPerfil.setSubcategoria(subcategoria);
		infracaoPerfil.setInfracaoTipo(infracaoTipo);
		infracaoPerfil.setImovelPerfil(imovelPerfil);

		colecaoInfracaoPerfil = (Collection) sessao.getAttribute("colecaoInfracaoPerfil");

		if(colecaoInfracaoPerfil == null || colecaoInfracaoPerfil.isEmpty()){
			colecaoInfracaoPerfil = new ArrayList();
		}

		for(InfracaoPerfil i : (Collection<InfracaoPerfil>) colecaoInfracaoPerfil){
			if(i.getCategoria().getId().equals(infracaoPerfil.getCategoria().getId())
							&& i.getSubcategoria().getId().equals(infracaoPerfil.getSubcategoria().getId())
							&& i.getImovelPerfil().getId().equals(infracaoPerfil.getImovelPerfil().getId())
							&& i.getInfracaoTipo().getId().equals(infracaoPerfil.getInfracaoTipo().getId())){
				throw new ActionServletException("atencao.infracao_perfil_ja_informado");
			}
		}

		form.setDescricaoInfracaoTipo("");
		form.setIdInfracaoTipo("");
		form.setId("");
		form.setIdCategoria("");
		form.setIdImovelPerfil("");
		form.setIdSubcategoria("");

		colecaoInfracaoPerfil.add(infracaoPerfil);
		sessao.setAttribute("colecaoInfracaoPerfil", colecaoInfracaoPerfil);

		return retorno;
	}
}