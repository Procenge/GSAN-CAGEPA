
package gcom.gui.relatorio.cobranca.prescricao;

import gcom.cobranca.bean.FiltroImoveisComDebitosPrescritosHelper;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.cobranca.prescricao.RelatorioAcompanhamentoDebitosPrescritos;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC3140] Acompanhar Imóveis com Débitos Prescritos
 * [SB0006] - Imprimir Relação dos Imóveis com Itens de Débitos Prescritos
 * 
 * @author Anderson Italo
 * @date 30/03/2014
 */
public class GerarRelatorioAcompanhamentoDebitosPrescritosAction
				extends ExibidorProcessamentoTarefaRelatorio {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = null;
		HttpSession sessao = httpServletRequest.getSession(false);
		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");

		FiltroImoveisComDebitosPrescritosHelper filtroImoveisComDebitosPrescritosHelper = null;

		if(sessao.getAttribute("filtroImoveisComDebitosPrescritosHelper") != null){

			filtroImoveisComDebitosPrescritosHelper = (FiltroImoveisComDebitosPrescritosHelper) sessao
							.getAttribute("filtroImoveisComDebitosPrescritosHelper");
		}

		RelatorioAcompanhamentoDebitosPrescritos relatorio = new RelatorioAcompanhamentoDebitosPrescritos(usuario);
		relatorio.addParametro("filtroImoveisComDebitosPrescritosHelper", filtroImoveisComDebitosPrescritosHelper);
		relatorio.addParametro("parametrosFiltro", sessao.getAttribute("parametrosFiltroRelatorioAcompanhamentoDebitosPrescritos"));


		int tipoRelatorio = TarefaRelatorio.TIPO_PDF;
		relatorio.addParametro("tipoFormatoRelatorio", tipoRelatorio);

		retorno = processarExibicaoRelatorio(relatorio, tipoRelatorio, httpServletRequest, httpServletResponse, actionMapping);

		return retorno;
	}

}
