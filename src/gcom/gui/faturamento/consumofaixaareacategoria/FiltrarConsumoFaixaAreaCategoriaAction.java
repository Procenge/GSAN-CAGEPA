
package gcom.gui.faturamento.consumofaixaareacategoria;

import gcom.faturamento.consumofaixaareacategoria.FiltroConsumoFaixaAreaCategoria;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.filtro.ParametroSimples;

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
public class FiltrarConsumoFaixaAreaCategoriaAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirManterConsumoFaixaAreaCategoria");

		HttpSession sessao = httpServletRequest.getSession(false);

		FiltrarConsumoFaixaAreaCategoriaActionForm form = (FiltrarConsumoFaixaAreaCategoriaActionForm) actionForm;

		// Recupera todos os campos da página para ser colocada no filtro posteriormente
		String categoria = form.getCategoria();
		String indicadorUso = form.getIndicadorUso();

		// Indicador Atualizar
		String indicadorAtualizar = httpServletRequest.getParameter("indicadorAtualizar");

		if(indicadorAtualizar != null && !indicadorAtualizar.equals("")){
			sessao.setAttribute("indicadorAtualizar", indicadorAtualizar);
		}else{
			sessao.removeAttribute("indicadorAtualizar");
		}

		FiltroConsumoFaixaAreaCategoria filtroConsumoFaixaAreaCategoria = new FiltroConsumoFaixaAreaCategoria(
						FiltroConsumoFaixaAreaCategoria.CATEGORIA_ID);

		boolean peloMenosUmParametroInformado = false;

		// Categoria
		if(categoria != null && !categoria.trim().equalsIgnoreCase(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){

			peloMenosUmParametroInformado = true;

			filtroConsumoFaixaAreaCategoria
							.adicionarParametro(new ParametroSimples(FiltroConsumoFaixaAreaCategoria.CATEGORIA_ID, categoria));
		}

		// Situacao do Grupo Usuario
		if(indicadorUso != null && !indicadorUso.trim().equalsIgnoreCase("")){
			peloMenosUmParametroInformado = true;

			filtroConsumoFaixaAreaCategoria.adicionarParametro(new ParametroSimples(FiltroConsumoFaixaAreaCategoria.INDICADOR_USO,
							indicadorUso));
		}

		// Erro caso o usuário mandou filtrar sem nenhum parâmetro
		if(!peloMenosUmParametroInformado){
			throw new ActionServletException("atencao.filtro.nenhum_parametro_informado");
		}

		// Manda o filtro pela sessao para o ExibirManterConsumoFaixaAreaCategoriaAction
		sessao.setAttribute("filtroConsumoFaixaAreaCategoriaSessao", filtroConsumoFaixaAreaCategoria);

		return retorno;
	}

}
