
package gcom.gui.cobranca;

import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.imovel.ImovelPerfil;
import gcom.cadastro.imovel.Subcategoria;
import gcom.cobranca.FiltroInfracaoPerfil;
import gcom.cobranca.InfracaoPerfil;
import gcom.cobranca.InfracaoTipo;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class FiltrarInfracaoPerfilAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("");
		HttpSession sessao = httpServletRequest.getSession();
		InfracaoPerfilActionForm form = (InfracaoPerfilActionForm) actionForm;
		String idCategoria = form.getIdCategoria();
		String idSubcategoria = form.getIdSubcategoria();
		String idImovelPerfil = form.getIdImovelPerfil();
		String idInfracaoTipo = form.getIdInfracaoTipo();
		String atualizar = httpServletRequest.getParameter("indicadorAtualizar");

		Fachada fachada = Fachada.getInstancia();
		boolean peloMenosUmParametroInformado = false;

		FiltroInfracaoPerfil filtro = new FiltroInfracaoPerfil();

		if(idCategoria != null && !idCategoria.equals("-1")){
			peloMenosUmParametroInformado = true;
			Categoria categoria = (Categoria) fachada.pesquisar(Integer.valueOf(idCategoria), Categoria.class);
			filtro.adicionarParametro(new ParametroSimples(FiltroInfracaoPerfil.CATEGORIA, categoria));
		}

		if(idSubcategoria != null && !idSubcategoria.equals("-1")){
			peloMenosUmParametroInformado = true;
			Subcategoria categoria = (Subcategoria) fachada.pesquisar(Integer.valueOf(idSubcategoria), Subcategoria.class);
			filtro.adicionarParametro(new ParametroSimples(FiltroInfracaoPerfil.SUBCATEGORIA, categoria));
		}

		if(idImovelPerfil != null && !idImovelPerfil.equals("-1")){
			peloMenosUmParametroInformado = true;
			ImovelPerfil categoria = (ImovelPerfil) fachada.pesquisar(Integer.valueOf(idImovelPerfil), ImovelPerfil.class);
			filtro.adicionarParametro(new ParametroSimples(FiltroInfracaoPerfil.IMOVEL_PERFIL, categoria));
		}

		if(idInfracaoTipo != null && !idInfracaoTipo.equals("")){
			peloMenosUmParametroInformado = true;
			InfracaoTipo categoria = (InfracaoTipo) fachada.pesquisar(Integer.valueOf(idInfracaoTipo), InfracaoTipo.class);
			filtro.adicionarParametro(new ParametroSimples(FiltroInfracaoPerfil.INFRACAO_TIPO, categoria));
		}

		if(!peloMenosUmParametroInformado){
			throw new ActionServletException("atencao.filtro.nenhum_parametro_informado");
		}

		filtro.adicionarCaminhoParaCarregamentoEntidade("categoria");
		// filtro.adicionarCaminhoParaCarregamentoEntidade("categoria.descricao");
		filtro.adicionarCaminhoParaCarregamentoEntidade(FiltroInfracaoPerfil.SUBCATEGORIA);
		filtro.adicionarCaminhoParaCarregamentoEntidade(FiltroInfracaoPerfil.IMOVEL_PERFIL);
		filtro.adicionarCaminhoParaCarregamentoEntidade(FiltroInfracaoPerfil.INFRACAO_TIPO);

		Collection colecaoInfracaoPerfil = (Collection) fachada.pesquisar(filtro, InfracaoPerfil.class.getName());

		if(colecaoInfracaoPerfil != null && !colecaoInfracaoPerfil.isEmpty()){
			if(atualizar != null && colecaoInfracaoPerfil.size() == 1){
				InfracaoPerfil orgao = (InfracaoPerfil) colecaoInfracaoPerfil.iterator().next();
				httpServletRequest.setAttribute("idRegistroAtualizacao", orgao.getId().toString());

				retorno = actionMapping.findForward("exibirAtualizarInfracaoPerfil");

			}else{
				retorno = actionMapping.findForward("retornarFiltroInfracaoPerfil");
			}
		}else{
			throw new ActionServletException("atencao.pesquisa.nenhumresultado");
		}

		httpServletRequest.setAttribute("filtro", filtro);

		sessao.setAttribute("filtro", filtro);

		return retorno;
	}
}
