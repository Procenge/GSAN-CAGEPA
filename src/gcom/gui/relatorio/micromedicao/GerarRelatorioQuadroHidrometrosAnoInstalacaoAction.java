package gcom.gui.relatorio.micromedicao;

import gcom.fachada.Fachada;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.micromedicao.RelatorioQuadroHidrometrosAnoInstalacao;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class GerarRelatorioQuadroHidrometrosAnoInstalacaoAction
				extends ExibidorProcessamentoTarefaRelatorio {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = null;

		Fachada fachada = Fachada.getInstancia();

		String tipoRelatorio = httpServletRequest.getParameter("tipoRelatorio");

		Integer qtdeRegistros = fachada.pesquisarQuadroHidrometrosAnoInstalacaoCount();

		RelatorioQuadroHidrometrosAnoInstalacao relatorioQuadroHidrometros = new RelatorioQuadroHidrometrosAnoInstalacao(
						(Usuario) (httpServletRequest.getSession(false)).getAttribute("usuarioLogado"));

		if(tipoRelatorio == null){
			tipoRelatorio = TarefaRelatorio.TIPO_PDF + "";
		}

		relatorioQuadroHidrometros.addParametro("tipoFormatoRelatorio", Integer.parseInt(tipoRelatorio));
		relatorioQuadroHidrometros.addParametro("qtdeRegistros", qtdeRegistros);

		retorno = processarExibicaoRelatorio(relatorioQuadroHidrometros, tipoRelatorio, httpServletRequest, httpServletResponse,
						actionMapping);

		return retorno;
	}

}
