
package gcom.gui.operacional;

import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.operacional.FiltroZonaAbastecimento;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
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
 * Filtrar ZonaAbastecimento
 * 
 * @author Bruno Ferreira dos Santos
 * @date 02/02/2011
 */

public class FiltrarZonaAbastecimentoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirManterZonaAbastecimento");

		HttpSession sessao = httpServletRequest.getSession(false);

		FiltrarZonaAbastecimentoActionForm form = (FiltrarZonaAbastecimentoActionForm) actionForm;

		// Recupera todos os campos da página para ser colocada no filtro
		// posteriormente
		String codigo = form.getCodigo();
		String descricao = form.getDescricao();
		String indicadorUso = form.getIndicadorUso();
		String tipoPesquisa = form.getTipoPesquisa();
		String sistemaAbastecimento = form.getIdSistemaAbastecimento();
		String idDistritoOperacional = form.getIdDistritoOperacional();
		String idLocalidade = form.getIdLocalidade();

		// Indicador Atualizar
		String indicadorAtualizar = httpServletRequest.getParameter("indicadorAtualizar");

		if(indicadorAtualizar != null && !indicadorAtualizar.equals("")){

			sessao.setAttribute("indicadorAtualizar", indicadorAtualizar);
		}else{

			sessao.removeAttribute("indicadorAtualizar");
		}

		FiltroZonaAbastecimento filtroZonaAbastecimento = new FiltroZonaAbastecimento(FiltroZonaAbastecimento.CODIGO);

		boolean peloMenosUmParametroInformado = false;

		if(codigo != null && !codigo.equals("")){

			peloMenosUmParametroInformado = true;

			filtroZonaAbastecimento.adicionarParametro(new ParametroSimples(FiltroZonaAbastecimento.CODIGO, codigo));
		}

		// Descrição
		if(descricao != null && !descricao.equalsIgnoreCase("")){

			peloMenosUmParametroInformado = true;

			if(tipoPesquisa != null && tipoPesquisa.equals(ConstantesSistema.TIPO_PESQUISA_COMPLETA.toString())){

				filtroZonaAbastecimento.adicionarParametro(new ComparacaoTextoCompleto(FiltroZonaAbastecimento.DESCRICAO, descricao));
			}else{

				filtroZonaAbastecimento.adicionarParametro(new ComparacaoTexto(FiltroZonaAbastecimento.DESCRICAO, descricao));
			}
		}

		if(!Util.isVazioOuBranco(sistemaAbastecimento)
						&& !sistemaAbastecimento.trim().equalsIgnoreCase(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){

			peloMenosUmParametroInformado = true;

			filtroZonaAbastecimento.adicionarParametro(new ParametroSimples(FiltroZonaAbastecimento.SISTEMA_ABASTECIMENTO_ID,
							sistemaAbastecimento));
		}

		if(!Util.isVazioOuBranco(idDistritoOperacional)
						&& !idDistritoOperacional.trim().equalsIgnoreCase(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){

			peloMenosUmParametroInformado = true;

			filtroZonaAbastecimento.adicionarParametro(new ParametroSimples(FiltroZonaAbastecimento.DISTRITO_OPERACIONAL_ID,
							idDistritoOperacional));
		}

		if(!Util.isVazioOuBranco(idLocalidade)
						&& !idLocalidade.trim().equalsIgnoreCase(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){

			peloMenosUmParametroInformado = true;

			filtroZonaAbastecimento.adicionarParametro(new ParametroSimples(FiltroZonaAbastecimento.LOCALIDADE_ID, idLocalidade));
		}

		// Situacao
		if(indicadorUso != null && !indicadorUso.trim().equalsIgnoreCase("")){
			peloMenosUmParametroInformado = true;

			filtroZonaAbastecimento.adicionarParametro(new ParametroSimples(FiltroZonaAbastecimento.INDICADOR_USO, indicadorUso));
		}

		// Erro caso o usuário mandou filtrar sem nenhum parâmetro
		if(!peloMenosUmParametroInformado){
			throw new ActionServletException("atencao.filtro.nenhum_parametro_informado");
		}

		// Manda o filtro pela sessao para o ExibirManterSubBaciaAction
		sessao.setAttribute("filtroZonaAbastecimentoSessao", filtroZonaAbastecimento);

		return retorno;
	}

}
