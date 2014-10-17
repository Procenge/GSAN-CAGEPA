
package gcom.gui.atendimentopublico.ordemservico;

import gcom.atendimentopublico.ordemservico.ServicoTipoTramite;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.Util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Remover Serviço Tipo Trâmite
 * [UC0410] Inserir Serviço Tipo
 * [UC0412] Manter Tipo de Serviço
 * 
 * @author Hebert Falcão
 * @date 11/02/2012
 */
public class PesquisarRemoverServicoTipoTramiteAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("inserirServicoTipo");

		// Obtém a sessão
		HttpSession sessao = httpServletRequest.getSession(false);

		String idServicoTipoTramiteRemover = httpServletRequest.getParameter("idServicoTipoTramiteRemover");

		// Pegando a coleção de objetos da sessão
		Collection<ServicoTipoTramite> colecaoServicoTipoTramite = (ArrayList<ServicoTipoTramite>) sessao
						.getAttribute("colecaoServicoTipoTramite");

		if(Util.isVazioOrNulo(colecaoServicoTipoTramite)){
			throw new ActionServletException("atencao.campo.informado", null, "Serviço Tipo Trâmite");
		}

		if(!Util.isVazioOuBranco(idServicoTipoTramiteRemover)){
			if(colecaoServicoTipoTramite.isEmpty()){
				throw new ActionServletException("atencao.campo.informado", null, "Serviço Tipo Trâmite");
			}else{
				ServicoTipoTramite servicoTipoTramiteAux = null;
				Integer idServicoTipoTramiteAux = null;

				Iterator<ServicoTipoTramite> servicoTipoTramiteIterator = colecaoServicoTipoTramite.iterator();

				// Procura o objeto pelo id
				while(servicoTipoTramiteIterator.hasNext()){
					servicoTipoTramiteAux = servicoTipoTramiteIterator.next();
					idServicoTipoTramiteAux = servicoTipoTramiteAux.getId();

					if(idServicoTipoTramiteAux.toString().equals(idServicoTipoTramiteRemover)){
						servicoTipoTramiteIterator.remove();

						break;
					}
				}

				sessao.setAttribute("colecaoServicoTipoTramite", colecaoServicoTipoTramite);
			}

		}else{
			throw new ActionServletException("atencao.campo.informado", null, "Serviço Tipo Trâmite");
		}

		return retorno;
	}

}
