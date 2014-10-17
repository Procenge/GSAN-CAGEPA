
package gcom.gui.cobranca;

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
 * [UC3023] Manter Boleto Bancário
 * 
 * @author Hebert Falcão
 * @date 12/10/2011
 */
public class CancelarBoletoBancarioAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("exibirCancelarBoletoBancarioAction");

		// Obtém a instância da fachada
		Fachada fachada = Fachada.getInstancia();

		// Obtém a sessão
		HttpSession sessao = httpServletRequest.getSession(false);

		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");

		// Boletos Selecionados
		CancelarBoletoBancarioActionForm cancelarBoletoBancarioActionForm = (CancelarBoletoBancarioActionForm) actionForm;

		Integer idMotivoCancelamento = null;

		String idMotivoCancelamentoStr = cancelarBoletoBancarioActionForm.getIdMotivoCancelamento();

		if(!Util.isVazioOuBranco(idMotivoCancelamentoStr)){
			idMotivoCancelamento = Integer.valueOf(idMotivoCancelamentoStr);
		}

		String[] idRegistrosCancelamento = (String[]) sessao.getAttribute("idRegistrosCancelamento");

		if(Util.isVazioOrNulo(idRegistrosCancelamento)){
			throw new ActionServletException("atencao.registros.nao_selecionados");
		}

		fachada.cancelarBoletoBancario(idMotivoCancelamento, idRegistrosCancelamento, usuario);

		sessao.setAttribute("closePage", "S");

		return retorno;
	}

}
