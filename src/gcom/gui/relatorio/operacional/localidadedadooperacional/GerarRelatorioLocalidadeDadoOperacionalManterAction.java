
package gcom.gui.relatorio.operacional.localidadedadooperacional;

import gcom.gui.operacional.localidadedadooperacional.FiltrarLocalidadeDadoOperacionalActionForm;
import gcom.operacional.FiltroLocalidadeDadoOperacional;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.RelatorioVazioException;
import gcom.relatorio.operacional.RelatorioLocalidadeDadoOperacional;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.SistemaException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author isilva
 * @date 02/02/2011
 */
public class GerarRelatorioLocalidadeDadoOperacionalManterAction
				extends ExibidorProcessamentoTarefaRelatorio {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Cria a vari�vel de retorno
		ActionForward retorno = null;

		// Mudar isso quando tiver esquema de seguran�a
		HttpSession sessao = httpServletRequest.getSession(false);

		FiltrarLocalidadeDadoOperacionalActionForm filtrarLocalidadeDadoOperacionalActionForm = (FiltrarLocalidadeDadoOperacionalActionForm) actionForm;

		FiltroLocalidadeDadoOperacional filtroLocalidadeDadoOperacional = (FiltroLocalidadeDadoOperacional) sessao
						.getAttribute("filtroLocalidadeDadoOperacional");

		// Cria uma inst�ncia da classe do relat�rio
		RelatorioLocalidadeDadoOperacional relatorioManterLocalidadeDadoOperacional = new RelatorioLocalidadeDadoOperacional(
						(Usuario) (httpServletRequest.getSession(false)).getAttribute("usuarioLogado"));

		// Inicio da parte que vai mandar os parametros para o relat�rio
		relatorioManterLocalidadeDadoOperacional.addParametro("filtroSistemaEsgotoSessao", filtroLocalidadeDadoOperacional);
		relatorioManterLocalidadeDadoOperacional.addParametro("idLocalidadeParamentro", filtrarLocalidadeDadoOperacionalActionForm
						.getIdLocalidade());
		relatorioManterLocalidadeDadoOperacional.addParametro("descricaoLocalidadeParamentro", filtrarLocalidadeDadoOperacionalActionForm
						.getDescricaoLocalidade());
		relatorioManterLocalidadeDadoOperacional.addParametro("mesAnoReferenciaInicial", filtrarLocalidadeDadoOperacionalActionForm
						.getMesAnoReferenciaInicial());
		relatorioManterLocalidadeDadoOperacional.addParametro("mesAnoReferenciaFinal", filtrarLocalidadeDadoOperacionalActionForm
						.getMesAnoReferenciaFinal());
		relatorioManterLocalidadeDadoOperacional.addParametro("indicadorUso", filtrarLocalidadeDadoOperacionalActionForm.getIndicadorUso());
		// Fim da parte que vai mandar os parametros para o relat�rio

		// Chama o met�do de gerar relat�rio passando o c�digo da analise como par�metro
		String tipoRelatorio = httpServletRequest.getParameter("tipoRelatorio");
		if(tipoRelatorio == null){
			tipoRelatorio = TarefaRelatorio.TIPO_PDF + "";
		}

		relatorioManterLocalidadeDadoOperacional.addParametro("tipoFormatoRelatorio", Integer.parseInt(tipoRelatorio));

		try{
			retorno = processarExibicaoRelatorio(relatorioManterLocalidadeDadoOperacional, tipoRelatorio, httpServletRequest,
							httpServletResponse, actionMapping);
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