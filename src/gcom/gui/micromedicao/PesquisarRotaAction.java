
package gcom.gui.micromedicao;

import gcom.cadastro.localidade.FiltroQuadra;
import gcom.cadastro.localidade.Quadra;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.micromedicao.FiltroRota;
import gcom.micromedicao.Rota;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action que define o pré-processamento da página de pesquisa de Rota
 * 
 * @author Rafael Santos
 * @since 23/08/2006
 */
public class PesquisarRotaAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("pesquisarRota");
		HttpSession sessao = httpServletRequest.getSession(false);
		PesquisarRotaActionForm pesquisarActionForm = (PesquisarRotaActionForm) actionForm;

		String idLocalidade = pesquisarActionForm.getIdLocalidade();
		String codigoSetorComercial = pesquisarActionForm.getCodigoSetorComercial();
		String idQuadra = pesquisarActionForm.getNumeroQuadra();
		String codigoRota = pesquisarActionForm.getCodigoRota();
		String faturamentoGrupo = pesquisarActionForm.getFaturamentoGrupo();
		String indicadorUso = pesquisarActionForm.getIndicadorUso();

		Fachada fachada = Fachada.getInstancia();

		FiltroRota filtroRota = new FiltroRota();

		if(idLocalidade != null && !idLocalidade.trim().equalsIgnoreCase("")){

			filtroRota.adicionarParametro(new ParametroSimples(FiltroRota.LOCALIDADE_ID, new Integer(idLocalidade)));

		}

		if(codigoSetorComercial != null && !codigoSetorComercial.trim().equalsIgnoreCase("")){

			filtroRota.adicionarParametro(new ParametroSimples(FiltroRota.SETOR_COMERCIAL_CODIGO, new Integer(codigoSetorComercial)));

		}

		if(codigoRota != null && !codigoRota.trim().equalsIgnoreCase("")){

			filtroRota.adicionarParametro(new ParametroSimples(FiltroRota.CODIGO_ROTA, new Integer(codigoRota)));

		}

		if(faturamentoGrupo != null && !faturamentoGrupo.trim().equalsIgnoreCase("-1")){

			filtroRota.adicionarParametro(new ParametroSimples(FiltroRota.FATURAMENTO_GRUPO, (faturamentoGrupo)));

		}

		if(indicadorUso != null && !indicadorUso.trim().equalsIgnoreCase("")){

			filtroRota.adicionarParametro(new ParametroSimples(FiltroRota.INDICADOR_USO, new Short(indicadorUso)));
		}

		Collection rotas = new ArrayList<Rota>();

		if(idQuadra != null && !idQuadra.trim().equalsIgnoreCase("")){

			FiltroQuadra filtroQuadra = new FiltroQuadra();
			filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.NUMERO_QUADRA, new Integer(idQuadra)));
			filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.CODIGO_SETORCOMERCIAL, new Integer(codigoSetorComercial)));
			filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.ID_LOCALIDADE, new Integer(idLocalidade)));

			filtroQuadra.adicionarCaminhoParaCarregamentoEntidade(FiltroQuadra.ROTA);
			filtroQuadra.adicionarCaminhoParaCarregamentoEntidade(FiltroQuadra.COBRANCA_GRUPO);
			filtroQuadra.adicionarCaminhoParaCarregamentoEntidade(FiltroQuadra.FATURAMENTO_GRUPO);
			filtroQuadra.adicionarCaminhoParaCarregamentoEntidade(FiltroQuadra.SETOR_COMERCIAL_LOCALIDADE);

			Collection collQuadra = null;
			Quadra quadra = null;

			collQuadra = fachada.pesquisar(filtroQuadra, Quadra.class.getName());

			if(!Util.isVazioOrNulo(collQuadra)){
				quadra = (Quadra) collQuadra.iterator().next();
			}else{
				throw new ActionServletException("atencao.pesquisa.nenhumresultado", null, "quadra");
			}

			rotas.add(quadra.getRota());
			sessao.setAttribute("rotas", rotas);
			httpServletRequest.setAttribute("page.offset", 1);
			httpServletRequest.setAttribute("maximoPaginas", 1);
			sessao.removeAttribute("desabilitarLocalidade");
			sessao.removeAttribute("desabilitarSetor");

		}else{

			filtroRota.adicionarCaminhoParaCarregamentoEntidade(FiltroRota.COBRANCA_GRUPO);
			filtroRota.adicionarCaminhoParaCarregamentoEntidade(FiltroRota.FATURAMENTO_GRUPO);
			filtroRota.adicionarCaminhoParaCarregamentoEntidade(FiltroRota.LOCALIDADE);

			filtroRota.setCampoOrderBy(FiltroRota.LOCALIDADE_ID);
			filtroRota.setCampoOrderBy(FiltroRota.SETOR_COMERCIAL_CODIGO);
			filtroRota.setCampoOrderBy(FiltroRota.CODIGO_ROTA);

			rotas = fachada.pesquisar(filtroRota, Rota.class.getName());

			if(rotas != null && !rotas.isEmpty()){

				// Aciona o controle de paginação para que sejam pesquisados apenas
				// os registros que aparecem na página
				Map resultado = controlarPaginacao(httpServletRequest, retorno, filtroRota, Rota.class.getName());
				rotas = (Collection) resultado.get("colecaoRetorno");
				retorno = (ActionForward) resultado.get("destinoActionForward");

				sessao.setAttribute("rotas", rotas);
				sessao.removeAttribute("desabilitarLocalidade");
				sessao.removeAttribute("desabilitarSetor");
			}else{

				throw new ActionServletException("atencao.pesquisa.nenhumresultado", null, "localidade");
			}
		}

		return retorno;

	}
}
