
package gcom.gui.faturamento.consumofaixaareacategoria;

import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.imovel.FiltroCategoria;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
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

/**
 * [UC3007] FILTRAR Consumo por Faixa de Área e Categoria
 * 
 * @author Ailton Sousa
 * @date 02/03/2011
 */
public class ExibirFiltrarConsumoFaixaAreaCategoriaAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("filtrarConsumoFaixaAreaCategoria");

		Fachada fachada = Fachada.getInstancia();

		HttpSession sessao = httpServletRequest.getSession(false);

		// Código para checar ou não o ATUALIZAR

		String primeiraVez = httpServletRequest.getParameter("menu");
		if(primeiraVez != null && !primeiraVez.equals("")){
			sessao.setAttribute("indicadorAtualizar", "1");

		}

		// Verificar a existência de dados

		// CATEGORIA

		FiltroCategoria filtroCategoria = new FiltroCategoria();

		filtroCategoria.adicionarParametro(new ParametroSimples(FiltroCategoria.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));

		filtroCategoria.setCampoOrderBy(FiltroCategoria.DESCRICAO);

		Collection<Categoria> colecaoCategoria = fachada.pesquisar(filtroCategoria, Categoria.class.getName());

		if(colecaoCategoria == null || colecaoCategoria.isEmpty()){
			throw new ActionServletException("atencao.entidade_sem_dados_para_selecao", null, "Categoria");
		}

		httpServletRequest.setAttribute("colecaoCategoria", colecaoCategoria);

		// Se voltou da tela de Atualizar Consumo por Faixa de Área e Categoria
		if(sessao.getAttribute("voltar") != null && sessao.getAttribute("voltar").equals("filtrar")){
			sessao.setAttribute("indicadorAtualizar", "1");
		}
		sessao.removeAttribute("voltar");

		return retorno;
	}

}
