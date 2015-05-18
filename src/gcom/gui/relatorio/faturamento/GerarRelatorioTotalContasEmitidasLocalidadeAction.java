
package gcom.gui.relatorio.faturamento;

import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.faturamento.RelatorioTotalContasEmitidasLocalidade;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class GerarRelatorioTotalContasEmitidasLocalidadeAction
				extends ExibidorProcessamentoTarefaRelatorio {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirFiltroRelatorioTotalContasEmitidasLocalidade");

		RelatorioTotalContasEmitidasLocalidadeForm form = (RelatorioTotalContasEmitidasLocalidadeForm) actionForm;

		String referenciaConta = form.getReferencia();

		Fachada fachada = Fachada.getInstancia();

		if(referenciaConta == null || referenciaConta.trim().equals("")){
			throw new ActionServletException("atencao.referencia.obrigatorio");

		}

		Integer referencia = null;
		referenciaConta = referenciaConta.substring(3) + referenciaConta.substring(0, 2);
		referencia = Integer.valueOf(referenciaConta);

		fachada.criarTabelaTemporariaRelatorioTotalContasEmitidasLocalidade();
		fachada.inserirRegistrosTabelaTemporariaRelatorioTotalContasEmitidasLocalidade(referencia);

		Long quantidade = fachada.gerarQuantidadeRelatorioTotalContasEmitidasLocalidade(referencia);

		if(quantidade == null || quantidade.equals(0L)){
			throw new ActionServletException("atencao.relatorio.vazio");

		}

		Usuario usuario = getUsuarioLogado(httpServletRequest);

		RelatorioTotalContasEmitidasLocalidade relatorio = new RelatorioTotalContasEmitidasLocalidade(usuario,
						ConstantesRelatorios.RELATORIO_TOTAL_CONTAS_EMITIDAS_LOCALIDADE);

		relatorio.addParametro("referencia", referencia);

		String tipoRelatorio = httpServletRequest.getParameter("tipoRelatorio");

		if(tipoRelatorio == null){
			tipoRelatorio = TarefaRelatorio.TIPO_PDF + "";
		}

		relatorio.addParametro("tipoFormatoRelatorio", Integer.parseInt(tipoRelatorio));

		relatorio.addParametro("referenciaRelatorio", form.getReferencia());

		retorno = processarExibicaoRelatorio(relatorio, tipoRelatorio, httpServletRequest, httpServletResponse, actionMapping);

		return retorno;
	}
}
