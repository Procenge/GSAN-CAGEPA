
package gcom.gui.relatorio.arrecadacao;

import gcom.gui.ActionServletException;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.RelatorioVazioException;
import gcom.relatorio.arrecadacao.RelatorioComprovantesDaArrecadacaoPorRecebedorAnalitico;
import gcom.relatorio.arrecadacao.RelatorioComprovantesDaArrecadacaoPorRecebedorSintetico;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.SistemaException;
import gcom.util.Util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Comprovantes da Arrecadação por Recebedor
 * 
 * @author Hebert Falcão
 * @since 28/09/2013
 */
public class GerarRelatorioComprovantesDaArrecadacaoPorRecebedorAction
				extends ExibidorProcessamentoTarefaRelatorio {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = null;

		GerarRelatorioComprovantesDaArrecadacaoPorRecebedorActionForm gerarRelatorioComprovantesDaArrecadacaoPorRecebedorActionForm = (GerarRelatorioComprovantesDaArrecadacaoPorRecebedorActionForm) actionForm;

		String mesAnoReferencia = gerarRelatorioComprovantesDaArrecadacaoPorRecebedorActionForm.getMesAnoReferencia();
		String indicadorTipoRelatorio = gerarRelatorioComprovantesDaArrecadacaoPorRecebedorActionForm.getIndicadorTipoRelatorio();

		Usuario usuarioLogado = (Usuario) (httpServletRequest.getSession(false)).getAttribute("usuarioLogado");

		String tipoFormatoRelatorio = httpServletRequest.getParameter("tipoRelatorio");

		if(tipoFormatoRelatorio == null){
			tipoFormatoRelatorio = Integer.toString(TarefaRelatorio.TIPO_PDF);
		}

		TarefaRelatorio tarefaRelatorio = null;

		if(indicadorTipoRelatorio.equals(GerarRelatorioComprovantesDaArrecadacaoPorRecebedorActionForm.INDICADOR_ANALITICO)){
			tarefaRelatorio = new RelatorioComprovantesDaArrecadacaoPorRecebedorAnalitico(usuarioLogado);
		}else{
			tarefaRelatorio = new RelatorioComprovantesDaArrecadacaoPorRecebedorSintetico(usuarioLogado);
		}

		Integer anoMesReferencia = Util.formatarMesAnoComBarraParaAnoMes(mesAnoReferencia);

		tarefaRelatorio.addParametro("anoMesReferencia", anoMesReferencia);
		tarefaRelatorio.addParametro("tipoFormatoRelatorio", Integer.parseInt(tipoFormatoRelatorio));

		try{
			retorno = processarExibicaoRelatorio(tarefaRelatorio, tipoFormatoRelatorio, httpServletRequest, httpServletResponse,
							actionMapping);
		}catch(SistemaException ex){
			reportarErros(httpServletRequest, "erro.sistema");

			retorno = actionMapping.findForward("telaErroPopup");
		}catch(RelatorioVazioException ex1){
			throw new ActionServletException("atencao.relatorio.vazio");
		}

		return retorno;
	}

}
