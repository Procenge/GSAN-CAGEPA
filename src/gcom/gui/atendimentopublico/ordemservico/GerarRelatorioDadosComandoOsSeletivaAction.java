package gcom.gui.atendimentopublico.ordemservico;

import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.RelatorioVazioException;
import gcom.relatorio.atendimentopublico.RelatorioDadosComandoOsSeletiva;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.SistemaException;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class GerarRelatorioDadosComandoOsSeletivaAction
				extends ExibidorProcessamentoTarefaRelatorio {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// cria a variável de retorno
		ActionForward retorno = null;

		// Mudar isso quando tiver esquema de segurança
		HttpSession sessao = httpServletRequest.getSession(false);

		ConsultarComandoOsSeletivaActionForm consultarComandoOsSeletivaActionForm = (ConsultarComandoOsSeletivaActionForm) sessao
						.getAttribute("consultarComandoOsSeletivaActionForm");

		ArrayList<DadosOrdemServicoHelper> relacaoDadosOrdemServico = (ArrayList<DadosOrdemServicoHelper>) sessao
						.getAttribute("colecaoDadosOrdemServico");

		ArrayList<Integer> idImoveis = (ArrayList<Integer>) sessao.getAttribute("colecaoImovel");

		// cria uma instância da classe do relatório
		RelatorioDadosComandoOsSeletiva relatorioDadosComandoOsSeletiva = new RelatorioDadosComandoOsSeletiva(
						(Usuario) (httpServletRequest.getSession(false)).getAttribute("usuarioLogado"));

		String tipoRelatorio = httpServletRequest.getParameter("tipoRelatorio");
		if(tipoRelatorio == null){
			tipoRelatorio = TarefaRelatorio.TIPO_PDF + "";
		}

		relatorioDadosComandoOsSeletiva.addParametro("tipoFormatoRelatorio", Integer.parseInt(tipoRelatorio));

		relatorioDadosComandoOsSeletiva.addParametro("consultarComandoOsSeletivaActionForm", consultarComandoOsSeletivaActionForm);
		relatorioDadosComandoOsSeletiva.addParametro("relacaoDadosOrdemServico", relacaoDadosOrdemServico);
		relatorioDadosComandoOsSeletiva.addParametro("colecaoImoveis", idImoveis);

		try{
			retorno = processarExibicaoRelatorio(relatorioDadosComandoOsSeletiva, tipoRelatorio, httpServletRequest, httpServletResponse,
							actionMapping);

		}catch(SistemaException ex){
			// manda o erro para a página no request atual
			reportarErros(httpServletRequest, "erro.sistema");

			// seta o mapeamento de retorno para a tela de erro de popup
			retorno = actionMapping.findForward("telaErroPopup");

		}catch(RelatorioVazioException ex1){
			// manda o erro para a página no request atual
			reportarErros(httpServletRequest, "atencao.relatorio.vazio");

			// seta o mapeamento de retorno para a tela de atenção de popup
			retorno = actionMapping.findForward("telaAtencaoPopup");
		}

		// devolve o mapeamento contido na variável retorno
		return retorno;
	}

}
