
package gcom.agenciavirtual;

import java.io.PrintWriter;
import java.util.GregorianCalendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.json.JSONObject;

/**
 * @author Marlos Ribeiro
 */
public abstract class AbstractAgenciaVirtualJSONWebservice
				extends AbstractAgenciaVirtualWebservice {

	/*
	 * (non-Javadoc)
	 * @see
	 * gcom.agenciavirtual.AbstractAgenciaVirtualWebservice#processarRequisicao(org.apache.struts
	 * .action.ActionMapping, org.apache.struts.action.ActionForm,
	 * javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected final void processarRequisicao(ActionMapping mapping, ActionForm form, HttpServletRequest request,
					HttpServletResponse response) throws Exception{

		preencherJSONBody(form, request);
		getJSONResponse().put("body", getJSONBody());
		if(getJSONHeader().length() == 0){
			informarStatus(STATUS_TIPO_SUCESSO, "Requisição processada com sucesso.");
		}
		getJSONHeader().put("dataEnviada", dateFormat.format(GregorianCalendar.getInstance().getTime()));
		getJSONResponse().put("header", getJSONHeader());
		PrintWriter outputStream = response.getWriter();
		outputStream.print(getJSONResponse().toString());
	}

	/**
	 * Preenche o Body do {@link JSONObject} de resposta.
	 * 
	 * @param form
	 * @param request
	 * @throws Exception
	 */
	abstract protected void preencherJSONBody(ActionForm form, HttpServletRequest request) throws Exception;
}
