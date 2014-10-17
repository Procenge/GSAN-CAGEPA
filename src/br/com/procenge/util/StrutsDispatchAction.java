/**
 * 
 */

package br.com.procenge.util;

import gcom.fachada.Fachada;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

import br.com.procenge.comum.exception.PCGException;

/**
 * @author gmatos
 */
public class StrutsDispatchAction
				extends DispatchAction {

	protected static final String FORWARD_SUCESSO = "sucesso";

	/**
	 * Fachada do sistema
	 */
	protected Fachada fachada = Fachada.getInstancia();

	/**
	 * Método responsável por salvar as mensagens.
	 * 
	 * @autor gilberto
	 * @param request
	 *            O request
	 * @param messageKey
	 *            A chave da mensagem
	 * @param param
	 *            O parametro
	 */
	protected void exibirMensagem(HttpServletRequest request, String messageKey, Object param){

		ActionMessages mensagens = new ActionMessages();
		mensagens.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(messageKey, param));
		saveMessages(request, mensagens);
	}

	/**
	 * Método responsável por identificar se a ação é um postback.
	 * 
	 * @autor gilberto
	 * @param request
	 *            O request
	 * @return True ou false
	 */
	protected boolean isPostBack(HttpServletRequest request){

		String postBack = (String) request.getParameter("postBack");
		Object objEx = request.getAttribute("javax.servlet.jsp.jspException");
		return (Boolean.valueOf(postBack) || objEx != null);
	}

	/**
	 * Método responsável em validar o token do form submetido.
	 * 
	 * @param request
	 *            O request
	 * @throws Exception
	 *             Casso ocorra algum erro
	 */
	protected void validarToken(HttpServletRequest request) throws Exception{

		if(!super.isTokenValid(request, true)){
			throw new PCGException(Constantes.RESOURCE_BUNDLE, Constantes.ERRO_DUPLA_SUBMISSAO);
		}
	}

	public ActionForward execute(ActionMapping arg0, ActionForm arg1, HttpServletRequest arg2, HttpServletResponse arg3) throws Exception{

		// Código removido porque estava gerando problema de horário nos servidores de AdA
		// TimeZone.setDefault(TimeZone.getTimeZone("GMT-03:00"));
		return super.execute(arg0, arg1, arg2, arg3);
	}
}
