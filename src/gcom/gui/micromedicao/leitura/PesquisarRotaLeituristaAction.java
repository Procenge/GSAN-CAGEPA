/**
 * 
 */

package gcom.gui.micromedicao.leitura;

import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.micromedicao.FiltroRota;
import gcom.micromedicao.Rota;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Exibi a tela para efetuar a pesquisa das rotas de um determinado leiturista.
 * 
 * @author Péricles Tavares
 * @date 09/08/2011
 */
public class PesquisarRotaLeituristaAction
				extends GcomAction {

	/**
	 * Exibi a tela para efetuar a pesquisa das rotas de um determinado leiturista.
	 * 
	 * @author Péricles Tavares
	 * @date 09/08/2011
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("resultadoPesquisarRotaLeituristaAction");

		PesquisarRotaLeituristaActionForm form = (PesquisarRotaLeituristaActionForm) actionForm;

		HttpSession sessao = httpServletRequest.getSession(false);

		FiltroRota filtroRota = new FiltroRota(FiltroRota.ID_ROTA);
		filtroRota.adicionarCaminhoParaCarregamentoEntidade("faturamentoGrupo");
		filtroRota.adicionarCaminhoParaCarregamentoEntidade("setorComercial.localidade");

		filtroRota.adicionarParametro(new ParametroSimples(FiltroRota.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));
		filtroRota.adicionarParametro(new ParametroSimples(FiltroRota.EMPRESA_ID, Util.obterInteger(form.getIdEmpresa())));
		filtroRota.adicionarParametro(new ParametroSimples(FiltroRota.CODIGO_LEITURISTA_ID, Util.obterInteger(form.getIdLeiturista())));

		filtroRota.setCampoOrderBy(FiltroRota.FATURAMENTO_GRUPO_ID, FiltroRota.SETOR_COMERCIAL_CODIGO, FiltroRota.ID_ROTA);

		Collection rotas = null;
		// Aciona o controle de paginação para que sejam pesquisados apenas
		// os registros que aparecem na página
		Map resultado = controlarPaginacao(httpServletRequest, retorno, filtroRota, Rota.class.getName());
		rotas = (Collection) resultado.get("colecaoRetorno");
		retorno = (ActionForward) resultado.get("destinoActionForward");

		if(rotas == null || rotas.isEmpty()){
			throw new ActionServletException("atencao.pesquisa.nenhumresultado", null, "rota");
		}
		sessao.setAttribute("rotas", rotas);

		return retorno;
	}

}