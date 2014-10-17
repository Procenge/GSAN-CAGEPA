
package gcom.gui.cobranca.campanhapremiacao;

import gcom.cobranca.campanhapremiacao.CampanhaPremiacao;
import gcom.cobranca.campanhapremiacao.CampanhaPremio;
import gcom.cobranca.campanhapremiacao.FiltroCampanhaPremiacao;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Felipe Rosacruz
 * @date 23/09/2013
 */
public class ExibirManterCampanhaPremiacaoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("ExibirManterCampanhaPremiacao");


		HttpSession sessao = httpServletRequest.getSession(false);

		if(sessao.getAttribute("i") != null){
			sessao.removeAttribute("i");
		}
		Fachada fachada = Fachada.getInstancia();
		FiltroCampanhaPremiacao filtroCampanhaPremiacao = null;

		Collection<CampanhaPremiacao> colecaoCampanhaPremiacao = null;
		Set<CampanhaPremio> setCampanhaPremio = new HashSet<CampanhaPremio>();

		if(sessao.getAttribute("filtroCampanhaPremiacao") != null){
			filtroCampanhaPremiacao = (FiltroCampanhaPremiacao) sessao.getAttribute("filtroCampanhaPremiacao");
			filtroCampanhaPremiacao.adicionarCaminhoParaCarregamentoEntidade(FiltroCampanhaPremiacao.CAMPANHA_PREMIO);
			filtroCampanhaPremiacao.adicionarCaminhoParaCarregamentoEntidade(FiltroCampanhaPremiacao.CAMPANHA_SORTEIO);
			filtroCampanhaPremiacao.adicionarCaminhoParaCarregamentoEntidade(FiltroCampanhaPremiacao.CAMPANHA_CADASTRO);
			filtroCampanhaPremiacao.adicionarCaminhoParaCarregamentoEntidade(FiltroCampanhaPremiacao.USUARIO_ENTREGA_PREMIO);
			filtroCampanhaPremiacao.adicionarCaminhoParaCarregamentoEntidade(FiltroCampanhaPremiacao.USUARIO_CANCELAMENTO_PREMIACAO);
			filtroCampanhaPremiacao.adicionarCaminhoParaCarregamentoEntidade(FiltroCampanhaPremiacao.CAMPANHA_CADASTRO_ORGAO_EXPEDIDOR);
			filtroCampanhaPremiacao.adicionarCaminhoParaCarregamentoEntidade(FiltroCampanhaPremiacao.CAMPANHA_CADASTRO_UF);
			filtroCampanhaPremiacao.adicionarCaminhoParaCarregamentoEntidade(FiltroCampanhaPremiacao.CAMPANHA_PREMIACAO_MOT_CANCEL);
			filtroCampanhaPremiacao.adicionarCaminhoParaCarregamentoEntidade(FiltroCampanhaPremiacao.PREMIO_GERENCIA_REGIONAL);
			filtroCampanhaPremiacao.adicionarCaminhoParaCarregamentoEntidade(FiltroCampanhaPremiacao.PREMIO_UNIDADE_NEGOCIO);
			filtroCampanhaPremiacao.adicionarCaminhoParaCarregamentoEntidade(FiltroCampanhaPremiacao.PREMIO_ELO);
			filtroCampanhaPremiacao.adicionarCaminhoParaCarregamentoEntidade(FiltroCampanhaPremiacao.PREMIO_LOCALIDADE);
			filtroCampanhaPremiacao.setCampoOrderBy("campanhaPremio.gerenciaRegional.id", "campanhaPremio.unidadeNegocio.id",
							"campanhaPremio.eloPremio.id", "campanhaPremio.localidade.id", "campanhaPremio.numeroOrdemPremiacao");

			colecaoCampanhaPremiacao = fachada.pesquisar(filtroCampanhaPremiacao, CampanhaPremiacao.class.getName());

			sessao.setAttribute("colCampanhaPremiacao", colecaoCampanhaPremiacao);

			if(colecaoCampanhaPremiacao == null || colecaoCampanhaPremiacao.isEmpty()){
				throw new ActionServletException("atencao.naocadastrado", null, "Campanha Premiacao");
			}

			for(CampanhaPremiacao campanhaPremiacao : colecaoCampanhaPremiacao){
				setCampanhaPremio.add(campanhaPremiacao.getCampanhaPremio());
			}

		}else{

			filtroCampanhaPremiacao = new FiltroCampanhaPremiacao();

			if(fachada.registroMaximo(CampanhaPremiacao.class) > ConstantesSistema.NUMERO_MAXIMO_REGISTROS_MANUTENCAO){
				retorno = actionMapping.findForward("filtrarCampanhaPremiacao");
			}
		}

		if(retorno.getName().equalsIgnoreCase("ExibirManterCampanhaPremiacao")){

			retorno = controlarPaginacao(httpServletRequest, retorno, setCampanhaPremio.size());

			List<CampanhaPremio> listaCampanhaPremio = new ArrayList<CampanhaPremio>();
			listaCampanhaPremio.addAll(setCampanhaPremio);
			Collections.sort(listaCampanhaPremio);

			int indexInicio;
			int indexFim;

			if(((Integer) httpServletRequest.getAttribute("page.offset")).intValue() < ((Integer) httpServletRequest
							.getAttribute("maximoPaginas")).intValue()){
				indexFim = ((Integer) httpServletRequest.getAttribute("page.offset")) * 10 - 1;
				indexInicio = indexFim - 9;
			}else{
				indexFim = listaCampanhaPremio.size() - 1;
				int quantidadeElementos = listaCampanhaPremio.size() % 10;
				indexInicio = indexFim - quantidadeElementos + 1;
			}
			
			List<CampanhaPremio> listaCampanhaPremioSelecionados = new ArrayList<CampanhaPremio>();
			for(int i = indexInicio; i <= indexFim; i++){
				listaCampanhaPremioSelecionados.add(listaCampanhaPremio.get(i));
			}
			sessao.setAttribute("listaCampanhaPremio", listaCampanhaPremioSelecionados);
		}

		return retorno;
	}
}