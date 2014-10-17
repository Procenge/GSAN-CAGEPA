
package gcom.gui.relatorio.arrecadacao;

import gcom.gui.GcomAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Situa��o dos avisos banc�rios
 * 
 * @author Hebert Falc�o
 * @since 04/10/2013
 */
public class ExibirGerarRelatorioSituacaoDosAvisosBancariosAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirGerarRelatorioSituacaoDosAvisosBancarios");

		return retorno;

	}

}
