
package gcom.gui.relatorio.cobranca;

import gcom.gui.cobranca.BoletoBancarioHelper;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.cobranca.RelatorioBoletosBancarios;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;

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
public class GerarRelatorioBoletosBancariosAction
				extends ExibidorProcessamentoTarefaRelatorio {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o retorno
		ActionForward retorno = null;

		// Obtém a sessão
		HttpSession sessao = httpServletRequest.getSession(false);

		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");

		BoletoBancarioHelper boletoBancarioHelper = (BoletoBancarioHelper) sessao.getAttribute("boletoBancarioHelper");
		boolean verificarNumeroBoletoCartaCobranca = (Boolean) sessao.getAttribute("verificarNumeroBoletoCartaCobranca");
		boolean desconsiderarParametros = (Boolean) sessao.getAttribute("desconsiderarParametros");

		RelatorioBoletosBancarios relatorio = new RelatorioBoletosBancarios(usuario);
		relatorio.addParametro("boletoBancarioHelper", boletoBancarioHelper);
		relatorio.addParametro("verificarNumeroBoletoCartaCobranca", verificarNumeroBoletoCartaCobranca);
		relatorio.addParametro("desconsiderarParametros", desconsiderarParametros);

		String tipoRelatorio = httpServletRequest.getParameter("tipoRelatorio");

		if(tipoRelatorio == null){
			tipoRelatorio = TarefaRelatorio.TIPO_PDF + "";
		}

		relatorio.addParametro("tipoFormatoRelatorio", Integer.parseInt(tipoRelatorio));

		retorno = processarExibicaoRelatorio(relatorio, tipoRelatorio, httpServletRequest, httpServletResponse, actionMapping);

		return retorno;
	}

}
