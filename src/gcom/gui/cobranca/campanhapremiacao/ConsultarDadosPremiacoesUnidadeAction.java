
package gcom.gui.cobranca.campanhapremiacao;

import gcom.cobranca.campanhapremiacao.CampanhaPremiacao;
import gcom.cobranca.campanhapremiacao.CampanhaPremio;
import gcom.cobranca.campanhapremiacao.FiltroCampanhaPremiacao;
import gcom.cobranca.campanhapremiacao.FiltroCampanhaPremio;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.Util;
import gcom.util.filtro.ParametroNulo;
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
 * @author Felipe Rosacruz
 * @date 24/09/2013
 */
public class ConsultarDadosPremiacoesUnidadeAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("ConsultarDadosPremiacoesUnidade");

		HttpSession sessao = httpServletRequest.getSession(false);

		Integer idRegistroConsultar = null;

		if(httpServletRequest.getParameter("idRegistroConsultar") != null){
			idRegistroConsultar = Integer.parseInt(httpServletRequest.getParameter("idRegistroConsultar"));

			FiltroCampanhaPremio filtroCampanhaPremio = new FiltroCampanhaPremio();

			filtroCampanhaPremio.adicionarCaminhoParaCarregamentoEntidade("gerenciaRegional");
			filtroCampanhaPremio.adicionarCaminhoParaCarregamentoEntidade("unidadeNegocio");
			filtroCampanhaPremio.adicionarCaminhoParaCarregamentoEntidade("eloPremio");
			filtroCampanhaPremio.adicionarCaminhoParaCarregamentoEntidade("localidade");

			filtroCampanhaPremio.adicionarParametro(new ParametroSimples(FiltroCampanhaPremio.ID, idRegistroConsultar));

			CampanhaPremio campanhaPremioSelecionado = (CampanhaPremio) Util.retonarObjetoDeColecao(Fachada.getInstancia().pesquisar(
							filtroCampanhaPremio, CampanhaPremio.class.getName()));
			
			String unidadePremiacao;
			
			FiltroCampanhaPremiacao filtroCampanhaPremiacao = new FiltroCampanhaPremiacao();

			if(campanhaPremioSelecionado.getGerenciaRegional() != null){
				unidadePremiacao = "da Gerência Regional";
				filtroCampanhaPremiacao.adicionarParametro(new ParametroSimples("campanhaPremio.gerenciaRegional.id",
								campanhaPremioSelecionado.getGerenciaRegional().getId()));

			}else if(campanhaPremioSelecionado.getUnidadeNegocio() != null){
				unidadePremiacao = "da Unidade de Negócio";
				filtroCampanhaPremiacao.adicionarParametro(new ParametroSimples("campanhaPremio.unidadeNegocio.id",
								campanhaPremioSelecionado.getUnidadeNegocio().getId()));

			}else if(campanhaPremioSelecionado.getEloPremio() != null){
				unidadePremiacao = "do Elo";
				filtroCampanhaPremiacao.adicionarParametro(new ParametroSimples("campanhaPremio.eloPremio.id", campanhaPremioSelecionado
								.getEloPremio().getId()));

			}else if(campanhaPremioSelecionado.getLocalidade() != null){
				unidadePremiacao = "da Localidade";
				filtroCampanhaPremiacao.adicionarParametro(new ParametroSimples("campanhaPremio.localidade.id", campanhaPremioSelecionado
								.getLocalidade().getId()));

			}else{
				unidadePremiacao = "Global";
				filtroCampanhaPremiacao.adicionarParametro(new ParametroNulo("campanhaPremio.gerenciaRegional"));
				filtroCampanhaPremiacao.adicionarParametro(new ParametroNulo("campanhaPremio.unidadeNegocio"));
				filtroCampanhaPremiacao.adicionarParametro(new ParametroNulo("campanhaPremio.eloPremio"));
				filtroCampanhaPremiacao.adicionarParametro(new ParametroNulo("campanhaPremio.localidade"));
			}
			sessao.setAttribute("unidadePremiacao", unidadePremiacao);
			
			Collection<CampanhaPremiacao> colecaoCampanhaPremiacao = null;

			filtroCampanhaPremiacao.adicionarCaminhoParaCarregamentoEntidade("campanhaPremio.gerenciaRegional");
			filtroCampanhaPremiacao.adicionarCaminhoParaCarregamentoEntidade("campanhaPremio.unidadeNegocio");
			filtroCampanhaPremiacao.adicionarCaminhoParaCarregamentoEntidade("campanhaPremio.eloPremio");
			filtroCampanhaPremiacao.adicionarCaminhoParaCarregamentoEntidade("campanhaPremio.localidade");
			filtroCampanhaPremiacao.adicionarCaminhoParaCarregamentoEntidade("campanhaCadastro");
			filtroCampanhaPremiacao.adicionarCaminhoParaCarregamentoEntidade("campanhaPremio");
			filtroCampanhaPremiacao.adicionarCaminhoParaCarregamentoEntidade("campanhaSorteio");
			filtroCampanhaPremiacao.adicionarCaminhoParaCarregamentoEntidade("campanhaCadastro.imovel");

			Fachada.getInstancia().pesquisar(filtroCampanhaPremiacao, CampanhaPremiacao.class.getName());

			Map resultado = controlarPaginacao(httpServletRequest, retorno, filtroCampanhaPremiacao, CampanhaPremiacao.class.getName());
			colecaoCampanhaPremiacao = (Collection) resultado.get("colecaoRetorno");
			retorno = (ActionForward) resultado.get("destinoActionForward");
			if(colecaoCampanhaPremiacao == null || colecaoCampanhaPremiacao.isEmpty()){
				throw new ActionServletException("atencao.naocadastrado", null, "Campanha Premiacao");
			}
			sessao.setAttribute("filtroCampanhaPremiac", filtroCampanhaPremiacao);
			sessao.setAttribute("colecaoCampanhaPremiacao", colecaoCampanhaPremiacao);

		}else{
			FiltroCampanhaPremiacao filtroCampanhaPremiacao = (FiltroCampanhaPremiacao) sessao.getAttribute("filtroCampanhaPremiac");
			filtroCampanhaPremiacao.adicionarCaminhoParaCarregamentoEntidade("campanhaPremio.gerenciaRegional");
			filtroCampanhaPremiacao.adicionarCaminhoParaCarregamentoEntidade("campanhaPremio.unidadeNegocio");
			filtroCampanhaPremiacao.adicionarCaminhoParaCarregamentoEntidade("campanhaPremio.eloPremio");
			filtroCampanhaPremiacao.adicionarCaminhoParaCarregamentoEntidade("campanhaPremio.localidade");
			filtroCampanhaPremiacao.adicionarCaminhoParaCarregamentoEntidade("campanhaCadastro");
			filtroCampanhaPremiacao.adicionarCaminhoParaCarregamentoEntidade("campanhaPremio");
			filtroCampanhaPremiacao.adicionarCaminhoParaCarregamentoEntidade("campanhaSorteio");
			filtroCampanhaPremiacao.adicionarCaminhoParaCarregamentoEntidade("campanhaCadastro.imovel");
			Collection<CampanhaPremiacao> colecaoCampanhaPremiacao = null;

			if(filtroCampanhaPremiacao != null){
				Map resultado = controlarPaginacao(httpServletRequest, retorno, filtroCampanhaPremiacao, CampanhaPremiacao.class.getName());
				colecaoCampanhaPremiacao = (Collection) resultado.get("colecaoRetorno");
				retorno = (ActionForward) resultado.get("destinoActionForward");
				if(colecaoCampanhaPremiacao == null || colecaoCampanhaPremiacao.isEmpty()){
					throw new ActionServletException("atencao.naocadastrado", null, "Campanha Premiacao");
				}

				sessao.setAttribute("colecaoCampanhaPremiacao", colecaoCampanhaPremiacao);
			}
		}


			
		return retorno;
	}
}