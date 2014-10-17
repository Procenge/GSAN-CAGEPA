/**
 * 
 */

package br.com.procenge.util;

import gcom.util.FachadaException;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.config.ExceptionConfig;

import br.com.procenge.comum.exception.NegocioException;
import br.com.procenge.comum.exception.PCGException;

/**
 * @author gmatos
 */
public class StrutsExceptionHandler
				extends org.apache.struts.action.ExceptionHandler {

	public ActionForward execute(Exception ex, ExceptionConfig config, ActionMapping mapping, ActionForm form, HttpServletRequest request,
					HttpServletResponse response) throws ServletException{

		ActionForward forward = null;
		ActionErrors actionErros = new ActionErrors();
		Map errosDeNegocio = null;
		Object[] parametrosErro = null;
		String chaveErro = null;
		String mensagemErro = null;
		Entry erro = null;
		String path = null;

		request.setAttribute("javax.servlet.jsp.jspException", ex);

		if(config.getPath() != null){
			path = config.getPath();
		}else{
			path = mapping.getInput();
		}

		forward = new ActionForward(path);
		mensagemErro = ex.getMessage();

		if(ex instanceof NegocioException){
			chaveErro = ((NegocioException) ex).getChaveErro();
			parametrosErro = ((NegocioException) ex).getParametrosErro();

			errosDeNegocio = ((NegocioException) ex).getErros();
			if(errosDeNegocio != null && errosDeNegocio.size() > 0){
				for(Iterator it = errosDeNegocio.entrySet().iterator(); it.hasNext();){
					erro = (Entry) it.next();
					actionErros.add(ActionErrors.GLOBAL_MESSAGE, new ActionError((String) erro.getKey(), (String) erro.getValue()));
				}
			}else{
				this.adicionarErro(actionErros, parametrosErro, chaveErro, mensagemErro);
			}
		}else if(ex instanceof PCGException){
			chaveErro = ((PCGException) ex).getChaveErro();
			parametrosErro = ((PCGException) ex).getParametrosErro();
			this.adicionarErro(actionErros, parametrosErro, chaveErro, mensagemErro);
		}else if(ex instanceof FachadaException){
			chaveErro = ((FachadaException) ex).getMessage();
			List<String> parametroMensagens = ((FachadaException) ex).getParametroMensagem();
			this.adicionarErro(actionErros, parametroMensagens.toArray(), chaveErro, ex.getCause().getMessage());

		}else{
			this.adicionarErro(actionErros, new String[] {mensagemErro}, config.getKey(), mensagemErro);
		}

		salvarErros(request, actionErros);
		this.printException(ex);

		return forward;
	}

	/**
	 * Adiciona as mensgens de erro.
	 * 
	 * @autor gilberto
	 * @param actionErros
	 * @param parametrosErro
	 * @param chaveErro
	 * @param mensagemErro
	 */
	private void adicionarErro(ActionErrors actionErros, Object[] parametrosErro, String chaveErro, String mensagemErro){

		if(chaveErro != null && chaveErro.length() > 0){
			if(parametrosErro != null && parametrosErro.length > 0){
				actionErros.add(ActionErrors.GLOBAL_MESSAGE, new ActionError(chaveErro, parametrosErro));
			}else{
				actionErros.add(ActionErrors.GLOBAL_MESSAGE, new ActionError(chaveErro));
			}
		}else{
			actionErros.add(ActionErrors.GLOBAL_MESSAGE, new ActionError(mensagemErro, false));
		}
	}

	/**
	 * Salva os erros da excecão.
	 * 
	 * @autor gilberto
	 * @param request
	 *            O request
	 * @param mensagens
	 *            As mensagens de erro
	 */
	private void salvarErros(HttpServletRequest request, ActionErrors erros){

		request.removeAttribute(Globals.ERROR_KEY);
		request.setAttribute(Globals.ERROR_KEY, erros);
	}

	/**
	 * Imprime a pilha de exception.
	 * 
	 * @autor gilberto
	 * @param e
	 *            A exceção
	 */
	private void printException(Exception e){

		while(e != null){
			e.printStackTrace();
			e = null;
		}
	}

}
