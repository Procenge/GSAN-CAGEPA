
package gcom.agenciavirtual;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * @author Marlos Ribeiro
 */
public abstract class AbstractAgenciaVirtualBinarioWebservice
				extends AbstractAgenciaVirtualWebservice {

	/*
	 * (non-Javadoc)
	 * @see
	 * gcom.agenciavirtual.AbstractAgenciaVirtualWebservice#processarRequisicao(org.apache.struts
	 * .action.ActionMapping, org.apache.struts.action.ActionForm,
	 * javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void processarRequisicao(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
					throws Exception{

		processarArquivo(mapping, form, request, response);

	}

	/**
	 * Processar a criação do binario que será retornado no {@link HttpServletResponse}
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	protected abstract void processarArquivo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
					HttpServletResponse response) throws Exception;
}
