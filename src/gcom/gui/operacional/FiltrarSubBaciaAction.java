
package gcom.gui.operacional;

import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.operacional.FiltroSubBacia;
import gcom.util.ConstantesSistema;
import gcom.util.filtro.ComparacaoTexto;
import gcom.util.filtro.ComparacaoTextoCompleto;
import gcom.util.filtro.ParametroSimples;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Filtrar SubBacia
 * 
 * @author Bruno Ferreira dos Santos
 * @date 31/01/2011
 */

public class FiltrarSubBaciaAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirManterSubBacia");

		HttpSession sessao = httpServletRequest.getSession(false);

		FiltrarSubBaciaActionForm form = (FiltrarSubBaciaActionForm) actionForm;

		// Recupera todos os campos da página para ser colocada no filtro
		// posteriormente
		String codigo = form.getCodigo();
		String descricao = form.getDescricao();
		String indicadorUso = form.getIndicadorUso();
		String tipoPesquisa = form.getTipoPesquisa();
		String bacia = form.getBacia();
		String subSistema = form.getIdSubSistema();
		String sistema = form.getIdSistema();

		// Indicador Atualizar
		String indicadorAtualizar = httpServletRequest.getParameter("indicadorAtualizar");

		if(indicadorAtualizar != null && !indicadorAtualizar.equals("")){

			sessao.setAttribute("indicadorAtualizar", indicadorAtualizar);
		}else{

			sessao.removeAttribute("indicadorAtualizar");
		}

		FiltroSubBacia filtroSubBacia = new FiltroSubBacia(FiltroSubBacia.CODIGO);

		boolean peloMenosUmParametroInformado = false;

		if(codigo != null && !codigo.equals("")){

			peloMenosUmParametroInformado = true;

			filtroSubBacia.adicionarParametro(new ParametroSimples(FiltroSubBacia.CODIGO, codigo));
		}

		// Descrição
		if(descricao != null && !descricao.equalsIgnoreCase("")){

			peloMenosUmParametroInformado = true;

			if(tipoPesquisa != null && tipoPesquisa.equals(ConstantesSistema.TIPO_PESQUISA_COMPLETA.toString())){

				filtroSubBacia.adicionarParametro(new ComparacaoTextoCompleto(FiltroSubBacia.DESCRICAO, descricao));
			}else{

				filtroSubBacia.adicionarParametro(new ComparacaoTexto(FiltroSubBacia.DESCRICAO, descricao));
			}
		}

		if(bacia != null && !bacia.trim().equalsIgnoreCase(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){

			peloMenosUmParametroInformado = true;

			filtroSubBacia.adicionarParametro(new ParametroSimples(FiltroSubBacia.BACIA_ID, bacia));
		}else{

			if(subSistema != null && !subSistema.trim().equalsIgnoreCase(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){

				peloMenosUmParametroInformado = true;

				filtroSubBacia.adicionarParametro(new ParametroSimples(FiltroSubBacia.SUBSISTEMA_ESGOTO_ID, subSistema));
			}else{
				if(sistema != null && !sistema.trim().equalsIgnoreCase(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){

					peloMenosUmParametroInformado = true;

					filtroSubBacia.adicionarParametro(new ParametroSimples(FiltroSubBacia.SISTEMA_ESGOTO_ID, sistema));
				}
			}

		}

		// Situacao
		if(indicadorUso != null && !indicadorUso.trim().equalsIgnoreCase("")){
			peloMenosUmParametroInformado = true;

			filtroSubBacia.adicionarParametro(new ParametroSimples(FiltroSubBacia.INDICADOR_USO, indicadorUso));
		}

		// Erro caso o usuário mandou filtrar sem nenhum parâmetro
		if(!peloMenosUmParametroInformado){
			throw new ActionServletException("atencao.filtro.nenhum_parametro_informado");
		}

		// Manda o filtro pela sessao para o ExibirManterSubBaciaAction
		sessao.setAttribute("filtroSubBaciaSessao", filtroSubBacia);

		return retorno;
	}

}
