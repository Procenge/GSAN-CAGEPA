
package gcom.gui.relatorio.arrecadacao;

import gcom.gui.GcomAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Comprovantes da Arrecadação por Recebedor
 * 
 * @author Hebert Falcão
 * @since 28/09/2013
 */
public class ExibirGerarRelatorioComprovantesDaArrecadacaoPorRecebedorAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirGerarRelatorioComprovantesDaArrecadacaoPorRecebedor");

		GerarRelatorioComprovantesDaArrecadacaoPorRecebedorActionForm relatorioForm = (GerarRelatorioComprovantesDaArrecadacaoPorRecebedorActionForm) actionForm;

		relatorioForm.setIndicadorTipoRelatorio(GerarRelatorioComprovantesDaArrecadacaoPorRecebedorActionForm.INDICADOR_ANALITICO);

		return retorno;

	}

}
