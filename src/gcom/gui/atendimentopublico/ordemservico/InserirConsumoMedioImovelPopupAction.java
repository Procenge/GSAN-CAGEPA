
package gcom.gui.atendimentopublico.ordemservico;

import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.Util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC0711] Filtro para Emiss�o de Ordens Seletivas - Consumo M�dio do Im�vel
 * 
 * @author Hebert Falc�o
 * @date 13/05/2011
 */
public class InserirConsumoMedioImovelPopupAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("inserirConsumoMedioImovelPopup");

		ConsumoMedioImovelPopupActionForm consumoMedioImovelPopupActionForm = (ConsumoMedioImovelPopupActionForm) actionForm;

		// Obt�m a inst�ncia da sess�o
		HttpSession sessao = httpServletRequest.getSession(false);

		ImovelEmissaoOrdensSeletivasActionForm imovelEmissaoOrdensSeletivasActionForm = (ImovelEmissaoOrdensSeletivasActionForm) sessao
						.getAttribute("ImovelEmissaoOrdensSeletivasActionForm");

		Collection<ConsumoMedioImovelHelper> colecaoConsumoMedioImovel = imovelEmissaoOrdensSeletivasActionForm
						.getColecaoConsumoMedioImovel();

		if(colecaoConsumoMedioImovel == null){
			colecaoConsumoMedioImovel = new ArrayList<ConsumoMedioImovelHelper>();
		}

		ConsumoMedioImovelHelper consumoMedioImovelHelper = new ConsumoMedioImovelHelper();

		// Id
		Date dataAtual = new Date();
		long id = dataAtual.getTime();
		consumoMedioImovelHelper.setId(id);

		// Consumo M�dio Inicial
		Integer consumoMedioInicial = null;
		String consumoMedioInicialStr = consumoMedioImovelPopupActionForm.getConsumoMedioInicial();

		if(!Util.isVazioOuBranco(consumoMedioInicialStr)){
			consumoMedioInicial = Util.converterStringParaInteger(consumoMedioInicialStr);
		}

		consumoMedioImovelHelper.setConsumoMedioInicial(consumoMedioInicial);

		// Consumo M�dio Final
		Integer consumoMedioFinal = null;
		String consumoMedioFinalStr = consumoMedioImovelPopupActionForm.getConsumoMedioFinal();

		if(!Util.isVazioOuBranco(consumoMedioFinalStr)){
			consumoMedioFinal = Util.converterStringParaInteger(consumoMedioFinalStr);
		}

		consumoMedioImovelHelper.setConsumoMedioFinal(consumoMedioFinal);

		if(Util.isVazioOuBranco(consumoMedioInicialStr) || Util.isVazioOuBranco(consumoMedioFinalStr)){
			throw new ActionServletException("atencao.required", null, "Intervalo de M�dia do Im�vel Inicial e Final");
		}

		if(!Util.isVazioOrNulo(colecaoConsumoMedioImovel)){
			for(ConsumoMedioImovelHelper obj : colecaoConsumoMedioImovel){
				Integer consumoMedioInicialAux = obj.getConsumoMedioInicial();
				Integer consumoMedioFinalAux = obj.getConsumoMedioFinal();

				if(consumoMedioInicialAux == consumoMedioInicial && consumoMedioFinalAux == consumoMedioFinal){
					throw new ActionServletException("atencao.dados.existente.consumo_medio_imovel");
				}
			}
		}

		colecaoConsumoMedioImovel.add(consumoMedioImovelHelper);

		imovelEmissaoOrdensSeletivasActionForm.setColecaoConsumoMedioImovel(colecaoConsumoMedioImovel);

		sessao.setAttribute("closePage", "S");

		return retorno;
	}

}
