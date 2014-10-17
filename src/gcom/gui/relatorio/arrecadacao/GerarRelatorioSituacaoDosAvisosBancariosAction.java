
package gcom.gui.relatorio.arrecadacao;

import gcom.gui.ActionServletException;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.RelatorioVazioException;
import gcom.relatorio.arrecadacao.RelatorioSituacaoDosAvisosBancarios;
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
 * Situação dos avisos bancários
 * 
 * @author Hebert Falcão
 * @since 04/10/2013
 */
public class GerarRelatorioSituacaoDosAvisosBancariosAction
				extends ExibidorProcessamentoTarefaRelatorio {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = null;

		GerarRelatorioSituacaoDosAvisosBancariosActionForm gerarRelatorioSituacaoDosAvisosBancariosActionForm = (GerarRelatorioSituacaoDosAvisosBancariosActionForm) actionForm;

		String mesAnoReferencia = gerarRelatorioSituacaoDosAvisosBancariosActionForm.getMesAnoReferencia();

		Usuario usuarioLogado = (Usuario) (httpServletRequest.getSession(false)).getAttribute("usuarioLogado");

		String tipoFormatoRelatorio = httpServletRequest.getParameter("tipoRelatorio");

		if(tipoFormatoRelatorio == null){
			tipoFormatoRelatorio = Integer.toString(TarefaRelatorio.TIPO_PDF);
		}

		RelatorioSituacaoDosAvisosBancarios relatorioSituacaoDosAvisosBancarios = new RelatorioSituacaoDosAvisosBancarios(usuarioLogado);

		Integer anoMesReferencia = Util.formatarMesAnoComBarraParaAnoMes(mesAnoReferencia);

		relatorioSituacaoDosAvisosBancarios.addParametro("anoMesReferencia", anoMesReferencia);
		relatorioSituacaoDosAvisosBancarios.addParametro("tipoFormatoRelatorio", Integer.parseInt(tipoFormatoRelatorio));

		try{
			retorno = processarExibicaoRelatorio(relatorioSituacaoDosAvisosBancarios, tipoFormatoRelatorio, httpServletRequest,
							httpServletResponse, actionMapping);
		}catch(SistemaException ex){
			reportarErros(httpServletRequest, "erro.sistema");

			retorno = actionMapping.findForward("telaErroPopup");
		}catch(RelatorioVazioException ex1){
			throw new ActionServletException("atencao.relatorio.vazio");
		}

		return retorno;
	}

}
