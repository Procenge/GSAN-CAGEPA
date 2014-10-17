package gcom.gui.cadastro.imovel;

import gcom.cadastro.imovel.Imovel;
import gcom.fachada.Fachada;
import gcom.faturamento.consumotarifa.ConsumoTarifa;
import gcom.faturamento.consumotarifa.FiltroConsumoTarifa;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Manter Contrato Demanda Consumo
 * 
 * @author Felipe Rosacruz
 * @date 12/01/2014
 */
public class ExibirFiltrarContratoDemandaConsumoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirFiltrarContratoDemandaConsumo");
		ManterContratoDemandaConsumoActionForm form = (ManterContratoDemandaConsumoActionForm) actionForm;
		Fachada fachada = Fachada.getInstancia();
		HttpSession sessao = httpServletRequest.getSession(false);

		if(httpServletRequest.getParameter("consultarImovel") != null){
			Integer idImovel = Integer.valueOf(form.getIdImovel());
			Imovel imovel = fachada.consultarImovel(idImovel);
			if(imovel == null){
				throw new ActionServletException("atencao.matricula.imovel.inexistente");
			}
			form.setInscricaoImovel(imovel.getInscricaoFormatada());
		}

		Collection<ConsumoTarifa> colecaoTarifaConsumo = null;

		FiltroConsumoTarifa filtroConsumoTarifa = new FiltroConsumoTarifa(FiltroConsumoTarifa.DESCRICAO);
		filtroConsumoTarifa.adicionarParametro(new ParametroSimples(FiltroConsumoTarifa.INDICADOR_USO, ConstantesSistema.SIM));
		colecaoTarifaConsumo = fachada.pesquisar(filtroConsumoTarifa, ConsumoTarifa.class.getName());

		sessao.setAttribute("colecaoTarifaConsumo", colecaoTarifaConsumo);



		return retorno;
	}

}
