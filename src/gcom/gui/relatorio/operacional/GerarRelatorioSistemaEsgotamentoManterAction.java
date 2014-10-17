
package gcom.gui.relatorio.operacional;

import gcom.gui.operacional.abastecimento.FiltrarSistemaEsgotoActionForm;
import gcom.operacional.FiltroSistemaEsgoto;
import gcom.operacional.SistemaEsgoto;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.RelatorioVazioException;
import gcom.relatorio.operacional.sistemaesgoto.RelatorioManterSistemaEsgotamento;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.SistemaException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class GerarRelatorioSistemaEsgotamentoManterAction
				extends ExibidorProcessamentoTarefaRelatorio {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Cria a variável de retorno
		ActionForward retorno = null;

		// Mudar isso quando tiver esquema de segurança
		HttpSession sessao = httpServletRequest.getSession(false);

		FiltrarSistemaEsgotoActionForm filtrarSistemaEsgotoActionForm = (FiltrarSistemaEsgotoActionForm) actionForm;

		FiltroSistemaEsgoto filtroLocalidadeSistemaEsgoto = (FiltroSistemaEsgoto) sessao.getAttribute("filtroSistemaEsgotoSessao");

		// Inicio da parte que vai mandar os parametros para o relatório
		SistemaEsgoto sistemaEsgotoParamentros = new SistemaEsgoto();

		Short indicadorDeUso = null;
		if(filtrarSistemaEsgotoActionForm.getIndicadorUso() != null && !filtrarSistemaEsgotoActionForm.getIndicadorUso().equals("")){
			indicadorDeUso = new Short("" + filtrarSistemaEsgotoActionForm.getIndicadorUso());
		}

		sistemaEsgotoParamentros.setDescricao("" + filtrarSistemaEsgotoActionForm.getDescricaoSistemaEsgoto());
		sistemaEsgotoParamentros.setIndicadorUso(indicadorDeUso);
		// Fim da parte que vai mandar os parametros para o relatório

		// Cria uma instância da classe do relatório
		RelatorioManterSistemaEsgotamento relatorioManterSistemaEsgotamento = new RelatorioManterSistemaEsgotamento(
						(Usuario) (httpServletRequest.getSession(false)).getAttribute("usuarioLogado"));
		relatorioManterSistemaEsgotamento.addParametro("filtroSistemaEsgotoSessao", filtroLocalidadeSistemaEsgoto);
		relatorioManterSistemaEsgotamento.addParametro("sistemaEsgotoParamentros", sistemaEsgotoParamentros);

		// Chama o metódo de gerar relatório passando o código da analise como parâmetro
		String tipoRelatorio = httpServletRequest.getParameter("tipoRelatorio");
		if(tipoRelatorio == null){
			tipoRelatorio = TarefaRelatorio.TIPO_PDF + "";
		}

		relatorioManterSistemaEsgotamento.addParametro("tipoFormatoRelatorio", Integer.parseInt(tipoRelatorio));

		try{
			retorno = processarExibicaoRelatorio(relatorioManterSistemaEsgotamento, tipoRelatorio, httpServletRequest, httpServletResponse,
							actionMapping);
		}catch(SistemaException ex){
			// Manda o erro para a página no request atual
			reportarErros(httpServletRequest, "erro.sistema");

			// Seta o mapeamento de retorno para a tela de erro de popup
			retorno = actionMapping.findForward("telaErroPopup");

		}catch(RelatorioVazioException ex1){
			// Manda o erro para a página no request atual
			reportarErros(httpServletRequest, "erro.relatorio.vazio");

			// Seta o mapeamento de retorno para a tela de atenção de popup
			retorno = actionMapping.findForward("telaAtencaoPopup");
		}

		// Devolve o mapeamento contido na variável retorno
		return retorno;
	}

}