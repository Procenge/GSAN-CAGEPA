
package gcom.gui.atendimentopublico.ordemservico;

import gcom.gui.GcomAction;
import gcom.util.Util;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC0711] Filtro para Emiss�o de Ordens Seletivas - Consumo M�dio do Im�vel
 * 
 * @author Hebert Falc�o
 * @date 13/05/2011
 */
public class RemoverConsumoMedioImovelAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("atualizarConsumoMedioImovel");

		String idStr = httpServletRequest.getParameter("id");

		ImovelEmissaoOrdensSeletivasActionForm imovelEmissaoOrdensSeletivas = (ImovelEmissaoOrdensSeletivasActionForm) actionForm;

		Collection<ConsumoMedioImovelHelper> colecaoConsumoMedioImovel = imovelEmissaoOrdensSeletivas.getColecaoConsumoMedioImovel();

		if(!Util.isVazioOrNulo(colecaoConsumoMedioImovel) && !Util.isVazioOuBranco(idStr)){
			Long id = Long.parseLong(idStr);

			for(ConsumoMedioImovelHelper obj : colecaoConsumoMedioImovel){
				Long idConsumoMedioImovel = obj.getId();

				if(idConsumoMedioImovel.equals(id)){
					colecaoConsumoMedioImovel.remove(obj);
					break;
				}
			}
		}

		return retorno;
	}

}