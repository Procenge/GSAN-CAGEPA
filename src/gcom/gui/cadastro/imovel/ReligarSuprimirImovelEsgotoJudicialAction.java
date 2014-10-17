
package gcom.gui.cadastro.imovel;

import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.Util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * UC - Religar ImovelEsgotoJudicial
 * 
 * @author isilva
 * @date 09/02/2011
 */
public class ReligarSuprimirImovelEsgotoJudicialAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		// Obt�m a sess�o
		HttpSession sessao = httpServletRequest.getSession(false);
		Usuario usuarioLogado = (Usuario) sessao.getAttribute(Usuario.USUARIO_LOGADO);

		ReligarSuprimirImovelEsgotoJudicialActionForm religarSuprimirImovelEsgotoJudicialActionForm = (ReligarSuprimirImovelEsgotoJudicialActionForm) actionForm;

		String idImovel = religarSuprimirImovelEsgotoJudicialActionForm.getIdImovel();

		if(!Util.isVazioOuBranco(idImovel)){
			Fachada.getInstancia().efetuarReligacaoSuprimirImovelEsgotoJudicial(Integer.valueOf(idImovel), usuarioLogado);
		}else{
			throw new ActionServletException("atencao.required", null, "Matr�cula do Im�vel");
		}

		montarPaginaSucesso(httpServletRequest, "Religa��o de Esgoto Suprimido Judicialmente efetuada com sucesso.",
						"Religar Esgoto Suprimido Judicialmente de outro Im�vel",
						"exibirReligarSuprimidoImovelEsgotoJudicialAction.do?menu=sim");

		return retorno;
	}

}
