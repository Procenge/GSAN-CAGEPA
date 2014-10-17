
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

		// Cria a vari�vel de retorno
		ActionForward retorno = null;

		// Mudar isso quando tiver esquema de seguran�a
		HttpSession sessao = httpServletRequest.getSession(false);

		FiltrarSistemaEsgotoActionForm filtrarSistemaEsgotoActionForm = (FiltrarSistemaEsgotoActionForm) actionForm;

		FiltroSistemaEsgoto filtroLocalidadeSistemaEsgoto = (FiltroSistemaEsgoto) sessao.getAttribute("filtroSistemaEsgotoSessao");

		// Inicio da parte que vai mandar os parametros para o relat�rio
		SistemaEsgoto sistemaEsgotoParamentros = new SistemaEsgoto();

		Short indicadorDeUso = null;
		if(filtrarSistemaEsgotoActionForm.getIndicadorUso() != null && !filtrarSistemaEsgotoActionForm.getIndicadorUso().equals("")){
			indicadorDeUso = new Short("" + filtrarSistemaEsgotoActionForm.getIndicadorUso());
		}

		sistemaEsgotoParamentros.setDescricao("" + filtrarSistemaEsgotoActionForm.getDescricaoSistemaEsgoto());
		sistemaEsgotoParamentros.setIndicadorUso(indicadorDeUso);
		// Fim da parte que vai mandar os parametros para o relat�rio

		// Cria uma inst�ncia da classe do relat�rio
		RelatorioManterSistemaEsgotamento relatorioManterSistemaEsgotamento = new RelatorioManterSistemaEsgotamento(
						(Usuario) (httpServletRequest.getSession(false)).getAttribute("usuarioLogado"));
		relatorioManterSistemaEsgotamento.addParametro("filtroSistemaEsgotoSessao", filtroLocalidadeSistemaEsgoto);
		relatorioManterSistemaEsgotamento.addParametro("sistemaEsgotoParamentros", sistemaEsgotoParamentros);

		// Chama o met�do de gerar relat�rio passando o c�digo da analise como par�metro
		String tipoRelatorio = httpServletRequest.getParameter("tipoRelatorio");
		if(tipoRelatorio == null){
			tipoRelatorio = TarefaRelatorio.TIPO_PDF + "";
		}

		relatorioManterSistemaEsgotamento.addParametro("tipoFormatoRelatorio", Integer.parseInt(tipoRelatorio));

		try{
			retorno = processarExibicaoRelatorio(relatorioManterSistemaEsgotamento, tipoRelatorio, httpServletRequest, httpServletResponse,
							actionMapping);
		}catch(SistemaException ex){
			// Manda o erro para a p�gina no request atual
			reportarErros(httpServletRequest, "erro.sistema");

			// Seta o mapeamento de retorno para a tela de erro de popup
			retorno = actionMapping.findForward("telaErroPopup");

		}catch(RelatorioVazioException ex1){
			// Manda o erro para a p�gina no request atual
			reportarErros(httpServletRequest, "erro.relatorio.vazio");

			// Seta o mapeamento de retorno para a tela de aten��o de popup
			retorno = actionMapping.findForward("telaAtencaoPopup");
		}

		// Devolve o mapeamento contido na vari�vel retorno
		return retorno;
	}

}