/**
 * 
 */
package gcom.gui.relatorio.gerencial.cadastro;

import gcom.gerencial.bean.InformarDadosGeracaoRelatorioConsultaHelper;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.gerencial.cadastro.RelatorioResumoLigacoesEconomia;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * @author mribeiro
 *
 */
public class GerarRelatorioResumoLigacoesEconomiaAction
				extends ExibidorProcessamentoTarefaRelatorio {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
					throws Exception{

		// Mudar isso quando tiver esquema de segurança
		HttpSession sessao = request.getSession(false);

		InformarDadosGeracaoRelatorioConsultaHelper informarDadosGeracaoRelatorioConsultaHelper = (InformarDadosGeracaoRelatorioConsultaHelper) sessao
						.getAttribute("informarDadosGeracaoRelatorioConsultaHelper");

		RelatorioResumoLigacoesEconomia relatorioResumoLigacoesEconomia = new RelatorioResumoLigacoesEconomia(
						(Usuario) (request.getSession(false)).getAttribute("usuarioLogado"), informarDadosGeracaoRelatorioConsultaHelper);

		String tipoRelatorio = request.getParameter("tipoRelatorio");
		if(tipoRelatorio == null){
			tipoRelatorio = TarefaRelatorio.TIPO_PDF + "";
		}

		return processarExibicaoRelatorio(relatorioResumoLigacoesEconomia, tipoRelatorio, request, response, mapping);
	}

}
