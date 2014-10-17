
package gcom.gui.operacional.abastecimento;

import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.operacional.FiltroSistemaAbastecimento;
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
 * [UCXXXX] Manter Sistema Abastecimento
 * 
 * @author Anderson Italo
 * @date 31/01/2011
 */
public class FiltrarSistemaAbastecimentoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirManterSistemaAbastecimento");

		HttpSession sessao = httpServletRequest.getSession(false);

		FiltrarSistemaAbastecimentoActionForm form = (FiltrarSistemaAbastecimentoActionForm) actionForm;

		// Recupera todos os campos da página para ser colocada no filtro
		// posteriormente
		String codigo = form.getCodigo();
		String descricao = form.getDescricao();
		String descricaoAbreviada = form.getDescricaoAbreviada();
		String indicadorUso = form.getIndicadorUso();
		String tipoPesquisa = form.getTipoPesquisa();

		// Indicador Atualizar
		String indicadorAtualizar = httpServletRequest.getParameter("indicadorAtualizar");

		if(indicadorAtualizar != null && !indicadorAtualizar.equals("")){

			sessao.setAttribute("indicadorAtualizar", indicadorAtualizar);
		}else{

			sessao.removeAttribute("indicadorAtualizar");
		}

		FiltroSistemaAbastecimento filtroSistemaAbastecimento = new FiltroSistemaAbastecimento();

		boolean peloMenosUmParametroInformado = false;

		// Código Sistema Abastecimento
		if(codigo != null && !codigo.trim().equals("")){

			peloMenosUmParametroInformado = true;

			filtroSistemaAbastecimento.adicionarParametro(new ParametroSimples(FiltroSistemaAbastecimento.CODIGO, codigo));

		}

		// Descricao
		if(descricao != null && !descricao.trim().equalsIgnoreCase("")){

			peloMenosUmParametroInformado = true;

			if(tipoPesquisa != null && tipoPesquisa.equals(ConstantesSistema.TIPO_PESQUISA_COMPLETA.toString())){

				filtroSistemaAbastecimento.adicionarParametro(new ComparacaoTextoCompleto(FiltroSistemaAbastecimento.DESCRICAO, descricao));

			}else{

				filtroSistemaAbastecimento.adicionarParametro(new ComparacaoTexto(FiltroSistemaAbastecimento.DESCRICAO, descricao));

			}

		}

		// Descrição Abreviada
		if(descricaoAbreviada != null && !descricaoAbreviada.trim().equals("")){

			peloMenosUmParametroInformado = true;

			filtroSistemaAbastecimento.adicionarParametro(new ComparacaoTexto(FiltroSistemaAbastecimento.DESCRICAO_ABREVIADA,
							descricaoAbreviada));

		}

		if(indicadorUso != null && !indicadorUso.equalsIgnoreCase("")){
			peloMenosUmParametroInformado = true;

			if(indicadorUso.equalsIgnoreCase(String.valueOf(ConstantesSistema.INDICADOR_USO_ATIVO))){

				filtroSistemaAbastecimento.adicionarParametro(new ParametroSimples(FiltroSistemaAbastecimento.INDICADOR_USO,
								ConstantesSistema.INDICADOR_USO_ATIVO));

			}else{

				filtroSistemaAbastecimento.adicionarParametro(new ParametroSimples(FiltroSistemaAbastecimento.INDICADOR_USO,
								ConstantesSistema.INDICADOR_USO_DESATIVO));

			}

		}

		// Erro caso o usuário mandou filtrar sem nenhum parâmetro
		if(!peloMenosUmParametroInformado){

			throw new ActionServletException("atencao.filtro.nenhum_parametro_informado");

		}
		filtroSistemaAbastecimento.setCampoOrderBy(FiltroSistemaAbastecimento.CODIGO);
		filtroSistemaAbastecimento.setCampoOrderBy(FiltroSistemaAbastecimento.DESCRICAO);
		sessao.setAttribute("filtroSistemaAbastecimento", filtroSistemaAbastecimento);

		return retorno;

	}

}
