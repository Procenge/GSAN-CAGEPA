package gcom.gui.relatorio.arrecadacao;

import gcom.gui.GcomAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class ExibirGerarRelatorioRecebimentoContaPubArrecadacaoAction
				extends GcomAction {

	private static final String RELATORIO_RECEBIMENTO_CONTAS_PUBLICO_ARRECADACAO = "RelatorioRecebimentoContaPublicoArrecadacao.rpt";

	private static final String TITULO_TELA_RECEBIMENTO_CONTAS_PUBLICO_ARRECADACAO = "Gerar Relatório de Recebimento de Contas do Público da Arrecadação";

	private static final String MSG_TELA_RECEBIMENTO_CONTAS_PUBLICO_ARRECADACAO = "Deseja Gerar Relatório de Recebimento de Contas do Público da Arrecadação?";


	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("exibirGerarRelatorioRecebimentoContaPubArrecadacaoAction");

		httpServletRequest.setAttribute("relatorio", RELATORIO_RECEBIMENTO_CONTAS_PUBLICO_ARRECADACAO);
		httpServletRequest.setAttribute("tituloTela", TITULO_TELA_RECEBIMENTO_CONTAS_PUBLICO_ARRECADACAO);
		httpServletRequest.setAttribute("msg", MSG_TELA_RECEBIMENTO_CONTAS_PUBLICO_ARRECADACAO);

		return retorno;
	}

}
