package gcom.gui.relatorio.faturamento.conta;

import gcom.faturamento.bean.FiltroContaSimularCalculoHelper;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.faturamento.conta.RelatorioContasRecalculadas;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC3156] Simular Cálculo da Conta Dados Reais
 * 
 * @author Anderson Italo
 * @date 26/09/2014
 */
public class GerarRelatorioContasRecalculadasAction
				extends ExibidorProcessamentoTarefaRelatorio {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = null;
		HttpSession sessao = httpServletRequest.getSession(false);
		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");

		FiltroContaSimularCalculoHelper filtroContaSimularCalculoHelper = null;

		if(sessao.getAttribute("filtroContaSimularCalculoHelper") != null){

			filtroContaSimularCalculoHelper = (FiltroContaSimularCalculoHelper) sessao.getAttribute("filtroContaSimularCalculoHelper");
		}

		RelatorioContasRecalculadas relatorio = new RelatorioContasRecalculadas(usuario);
		relatorio.addParametro("filtroContaSimularCalculoHelper", filtroContaSimularCalculoHelper);


		int tipoRelatorio = TarefaRelatorio.TIPO_PDF;
		relatorio.addParametro("tipoFormatoRelatorio", tipoRelatorio);

		retorno = processarExibicaoRelatorio(relatorio, tipoRelatorio, httpServletRequest, httpServletResponse, actionMapping);

		return retorno;
	}

}
