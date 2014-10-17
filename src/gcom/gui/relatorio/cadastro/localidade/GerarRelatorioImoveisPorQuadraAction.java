
package gcom.gui.relatorio.cadastro.localidade;

import gcom.cadastro.localidade.Quadra;
import gcom.gui.cadastro.localidade.AtualizarQuadraActionForm;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.cadastro.localidade.RelatorioImoveisPorQuadra;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class GerarRelatorioImoveisPorQuadraAction
				extends ExibidorProcessamentoTarefaRelatorio {

	/**
	 * [UC0025] Manter Quadra
	 * [SB0004] - Emitir Relatório dos Imóveis da Quadra
	 * 
	 * @author Anderson Italo
	 * @date 26/01/2012
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = null;

		HttpSession sessao = httpServletRequest.getSession(false);

		AtualizarQuadraActionForm formulario = (AtualizarQuadraActionForm) actionForm;

		Quadra quadra = null;
		if(sessao.getAttribute("quadraRelatorioImoveis") != null){

			quadra = (Quadra) sessao.getAttribute("quadraRelatorioImoveis");
		}

		/*
		 * O sistema exibe a relação dos imóveis da quadra [SB0004 – Emitir Relatório dos Imóveis da
		 * Quadra]
		 */
		RelatorioImoveisPorQuadra relatorio = new RelatorioImoveisPorQuadra((Usuario) (httpServletRequest.getSession(false))
						.getAttribute("usuarioLogado"));

		relatorio.addParametro("quadraAlterada", quadra);
		relatorio.addParametro("totalImoveisRotaIgualAnterior", formulario.getQuantidadeImoveisRotaIgualAnterior());
		relatorio.addParametro("totalImoveisRotaDiferenteAnterior", formulario.getQuantidadeImoveisRotaDiferenteAnterior());
		relatorio.addParametro("totalImoveisDistritoOperacionalIgualAnterior",
						formulario.getQuantidadeImoveisDistritoOperacionalIgualAnterior());
		relatorio.addParametro("totalImoveisDistritoOperacionalDiferenteAnterior",
						formulario.getQuantidadeImoveisDistritoOperacionalDiferenteAnterior());

		relatorio.addParametro("tipoFormatoRelatorio", TarefaRelatorio.TIPO_PDF);
		retorno = processarExibicaoRelatorio(relatorio, String.valueOf(TarefaRelatorio.TIPO_PDF), httpServletRequest, httpServletResponse,
						actionMapping);
		return retorno;
	}
}
