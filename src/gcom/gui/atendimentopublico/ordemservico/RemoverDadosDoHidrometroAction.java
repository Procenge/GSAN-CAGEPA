
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
 * [UC0711] Filtro para Emiss�o de Ordens Seletivas - Dados do Hidr�metro
 * 
 * @author Hebert Falc�o
 * @date 13/05/2011
 */
public class RemoverDadosDoHidrometroAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("atualizarDadosDoHidrometro");

		String idStr = httpServletRequest.getParameter("id");

		ImovelEmissaoOrdensSeletivasActionForm imovelEmissaoOrdensSeletivas = (ImovelEmissaoOrdensSeletivasActionForm) actionForm;

		Collection<DadosDoHidrometroHelper> colecaoDadosDoHidrometro = imovelEmissaoOrdensSeletivas.getColecaoDadosDoHidrometro();

		if(!Util.isVazioOrNulo(colecaoDadosDoHidrometro) && !Util.isVazioOuBranco(idStr)){
			Long id = Long.parseLong(idStr);

			for(DadosDoHidrometroHelper obj : colecaoDadosDoHidrometro){
				Long idDadosDoHidrometro = obj.getId();

				if(idDadosDoHidrometro.equals(id)){
					colecaoDadosDoHidrometro.remove(obj);
					break;
				}
			}
		}

		return retorno;
	}

}