package gcom.gui.cadastro.imovel;

import gcom.arrecadacao.ContratoDemandaConsumo;
import gcom.fachada.Fachada;
import gcom.faturamento.FiltroContratoDemandaConsumo;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;

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
 * @date 01/02/2014
 */
public class ExibirManterContratoDemandaConsumoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirManterContratoDemandaConsumo");

		Fachada fachada = Fachada.getInstancia();

		Collection colecaoContratoDemandaConsumo = null;

		HttpSession sessao = httpServletRequest.getSession(false);

		FiltroContratoDemandaConsumo filtro = null;

		if(sessao.getAttribute("i") != null){
			sessao.removeAttribute("i");
		}

		if(sessao.getAttribute("filtroContratoDemandaConsumo") != null){
			filtro = (FiltroContratoDemandaConsumo) sessao.getAttribute("filtroContratoDemandaConsumo");
		}else{

			filtro = new FiltroContratoDemandaConsumo();

			if(fachada.registroMaximo(ContratoDemandaConsumo.class) > ConstantesSistema.NUMERO_MAXIMO_REGISTROS_MANUTENCAO){
				retorno = actionMapping.findForward("filtroContratoDemandaConsumo");
			}
		}
		filtro.adicionarCaminhoParaCarregamentoEntidade("consumoTarifa");
		filtro.adicionarCaminhoParaCarregamentoEntidade("imovel");

		if(retorno.getName().equalsIgnoreCase("exibirManterContratoDemandaConsumo")){
			filtro.setCampoOrderBy(FiltroContratoDemandaConsumo.MUMEROCONTRATO, FiltroContratoDemandaConsumo.IMOVEL_ID);

			Map resultado = controlarPaginacao(httpServletRequest, retorno, filtro, ContratoDemandaConsumo.class.getName());
			colecaoContratoDemandaConsumo = (Collection) resultado.get("colecaoRetorno");
			retorno = (ActionForward) resultado.get("destinoActionForward");
			if(colecaoContratoDemandaConsumo == null || colecaoContratoDemandaConsumo.isEmpty()){
				throw new ActionServletException("atencao.naocadastrado", null, "o Contrato de Demanda de Consumo");
			}
			sessao.setAttribute("colecaoContratoDemandaConsumo", colecaoContratoDemandaConsumo);
		}
		return retorno;
	}
}