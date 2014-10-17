
package gcom.gui.micromedicao;

import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * action respons�vel pela exibi��o do relat�rio de anormalidades leituras
 * 
 * @author P�ricles Tavares
 * @created 03/08/2011
 */

public class GerarRelatorioAnormalidadesLeiturasAction
				extends ExibidorProcessamentoTarefaRelatorio {

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param actionMapping
	 *            Descri��o do par�metro
	 * @param actionForm
	 *            Descri��o do par�metro
	 * @param httpServletRequest
	 *            Descri��o do par�metro
	 * @param httpServletResponse
	 *            Descri��o do par�metro
	 * @return Descri��o do retorno
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// cria a vari�vel de retorno
		ActionForward retorno = null;

		// Mudar isso quando tiver esquema de seguran�a
		HttpSession sessao = httpServletRequest.getSession(false);

		Collection<Object[]> movimentosRoteiroEmpresa = (Collection<Object[]>) sessao.getAttribute("movimentosRoteiroEmpresaAnormalidade");

		// Fim da parte que vai mandar os parametros para o relat�rio
		String tipoRelatorio = httpServletRequest.getParameter("tipoRelatorio");

		RelatorioAnormalidadesLeituras relatorio = new RelatorioAnormalidadesLeituras((Usuario) (httpServletRequest.getSession(false))
						.getAttribute("usuarioLogado"), "/relatorioAnormalidadesLeituras.jasper");
		relatorio.addParametro("movimentosRoteiroEmpresa", movimentosRoteiroEmpresa);
		relatorio.addParametro("totalRegistrosRelatorio", movimentosRoteiroEmpresa.size());

		if(tipoRelatorio == null){
			tipoRelatorio = TarefaRelatorio.TIPO_PDF + "";
		}

		relatorio.addParametro("tipoFormatoRelatorio", Integer.parseInt(tipoRelatorio));
		retorno = processarExibicaoRelatorio(relatorio, tipoRelatorio, httpServletRequest, httpServletResponse, actionMapping);

		// devolve o mapeamento contido na vari�vel retorno
		return retorno;
	}
}
