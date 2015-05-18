
package gcom.gui.cadastro.cliente.atividadeeconomica;

import gcom.cadastro.cliente.FiltroAtividadeEconomica;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
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
 * [UC3151] Filtrar Atividade Econômica
 * 
 * @author Anderson Italo
 * @date 29/06/2014
 */
public class FiltrarAtividadeEconomicaAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirManterAtividadeEconomica");

		HttpSession sessao = httpServletRequest.getSession(false);

		FiltrarAtividadeEconomicaActionForm form = (FiltrarAtividadeEconomicaActionForm) actionForm;

		// Recupera todos os campos da página para ser colocada no filtro
		// posteriormente
		String codigo = form.getCodigo();
		String descricao = form.getDescricao();
		String indicadorUso = form.getIndicadorUso();
		String tipoPesquisa = form.getTipoPesquisa();

		// Indicador Atualizar
		String indicadorAtualizar = httpServletRequest.getParameter("indicadorAtualizar");

		if(indicadorAtualizar != null && !indicadorAtualizar.equals("")){

			sessao.setAttribute("indicadorAtualizar", indicadorAtualizar);
		}else{

			sessao.removeAttribute("indicadorAtualizar");
		}

		FiltroAtividadeEconomica filtroAtividadeEconomica = new FiltroAtividadeEconomica();

		boolean peloMenosUmParametroInformado = false;

		// Código Sistema Abastecimento
		if(codigo != null && !codigo.trim().equals("")){

			peloMenosUmParametroInformado = true;

			filtroAtividadeEconomica.adicionarParametro(new ComparacaoTexto(FiltroAtividadeEconomica.CODIGO, codigo));

		}

		// Descricao
		if(descricao != null && !descricao.trim().equalsIgnoreCase("")){

			peloMenosUmParametroInformado = true;

			if(tipoPesquisa != null && tipoPesquisa.equals(ConstantesSistema.TIPO_PESQUISA_COMPLETA.toString())){

				filtroAtividadeEconomica.adicionarParametro(new ComparacaoTextoCompleto(FiltroAtividadeEconomica.DESCRICAO, descricao));

			}else{

				filtroAtividadeEconomica.adicionarParametro(new ComparacaoTexto(FiltroAtividadeEconomica.DESCRICAO, descricao));

			}

		}

		if(indicadorUso != null && !indicadorUso.equalsIgnoreCase("")){
			peloMenosUmParametroInformado = true;

			if(indicadorUso.equalsIgnoreCase(String.valueOf(ConstantesSistema.INDICADOR_USO_ATIVO))){

				filtroAtividadeEconomica.adicionarParametro(new ParametroSimples(FiltroAtividadeEconomica.INDICADOR_USO,
								ConstantesSistema.INDICADOR_USO_ATIVO));

			}else{

				filtroAtividadeEconomica.adicionarParametro(new ParametroSimples(FiltroAtividadeEconomica.INDICADOR_USO,
								ConstantesSistema.INDICADOR_USO_DESATIVO));

			}

		}

		// Erro caso o usuário mandou filtrar sem nenhum parâmetro
		if(!peloMenosUmParametroInformado){

			throw new ActionServletException("atencao.filtro.nenhum_parametro_informado");

		}

		filtroAtividadeEconomica.setCampoOrderBy(FiltroAtividadeEconomica.ID);
		filtroAtividadeEconomica.setCampoOrderBy(FiltroAtividadeEconomica.DESCRICAO);
		sessao.setAttribute("filtroAtividadeEconomica", filtroAtividadeEconomica);
		sessao.setAttribute("indicadorAtualizar", httpServletRequest.getParameter("indicadorAtualizar"));

		return retorno;

	}

}
