
package gcom.gui.cobranca;

import gcom.gui.ActionServletException;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.RelatorioVazioException;
import gcom.relatorio.cobranca.RelatorioProdutividadeMensalExecucaoServico;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.SistemaException;
import gcom.util.Util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

/**
 * @author isilva
 */
public class GerarRelatorioProdutividadeMensalExecucaoServicoAction
				extends ExibidorProcessamentoTarefaRelatorio {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse) throws Exception{

		ActionForward retorno = null;

		/**
		 * TODO Alterar para o form que Josué criou
		 */
		DynaActionForm dynaForm = (DynaActionForm) actionForm;

		// this.validaroFormulario(form);

		// Adicionar paramentros para pesquisa
		// Collection colecaoRetorno =
		// Fachada.getInstancia().pesquisarProdutividadeMensalExecucaoServico();

		Collection colecaoRetorno = new ArrayList();

		if(colecaoRetorno != null && !colecaoRetorno.isEmpty()){
			RelatorioProdutividadeMensalExecucaoServico relatorio = new RelatorioProdutividadeMensalExecucaoServico(
							(Usuario) (httpServletRequest.getSession(false)).getAttribute("usuarioLogado"));

			relatorio.addParametro("colecaoRetorno", colecaoRetorno);

			relatorio.addParametro("tipoFormatoRelatorio", Integer.valueOf(TarefaRelatorio.TIPO_XLS));

			try{
				retorno = processarExibicaoRelatorio(relatorio, TarefaRelatorio.TIPO_XLS, httpServletRequest, httpServletResponse,
								actionMapping);
			}catch(SistemaException ex){
				reportarErros(httpServletRequest, "erro.sistema");
				retorno = actionMapping.findForward("telaErroPopup");
			}catch(RelatorioVazioException ex1){
				reportarErros(httpServletRequest, "atencao.relatorio.vazio");
				retorno = actionMapping.findForward("telaAtencaoPopup");
			}
		}else{
			throw new ActionServletException("atencao.pesquisa.nenhumresultado");
		}
		return actionMapping.findForward("mesmaPagina");
	}

	/**
	 * @author isilva
	 * @param form
	 */
	// private void validaroFormulario(FiltroEficienciaCobrancaRelatorioActionForm form) {
	//		
	// if (form.getComando() == null || form.getComando().equalsIgnoreCase("") ||
	// form.getComando().length() < 1) {
	// throw new ActionServletException("atencao.informe_campo", null, "Tipo de Comando");
	// }
	//		
	// // Ambos
	// if (form.getComando().equalsIgnoreCase("3")){
	// this.validarDatas(form);
	// }
	//		
	// }

	/**
	 * @author isilva
	 * @param form
	 */
	// private void validarDatas(FiltroEficienciaCobrancaRelatorioActionForm form) {
	// if (Util.isVazioOuBranco(form.getPeriodoInicio()) &&
	// Util.isVazioOuBranco(form.getPeriodoInicio())) {
	// throw new ActionServletException("atencao.informe_campo", null, "Período");
	// }
	//		
	// Date periodoInicio = null;
	// Date periodoFim = null;
	//		
	// // Data Inicial
	// if (Util.isVazioOuBranco(form.getPeriodoInicio())) {
	// throw new ActionServletException("atencao.required", null, "Período Inicial");
	// } else {
	//		
	// try {
	// periodoInicio = Util.converteStringParaDate(form.getPeriodoInicio());
	// } catch (Exception e) {
	// throw new ActionServletException("atencao.campo.invalido", null, "Período Inicial");
	// }
	//			
	// }
	//
	// // Data Final
	// if (Util.isVazioOuBranco(form.getPeriodoFim())) {
	// throw new ActionServletException("atencao.required", null, "Período Final");
	// } else {
	//			
	// try {
	// periodoFim = Util.converteStringParaDate(form.getPeriodoFim());
	// } catch (Exception e) {
	// throw new ActionServletException("atencao.campo.invalido", null, "Período Final");
	// }
	//			
	// }
	//		
	// //Se data inicio maior que data fim
	// if(Util.compararData(periodoInicio, periodoFim) == 1){
	// throw new ActionServletException("atencao.data_inicial_maior_data_final",
	// form.getPeriodoInicio(), form.getPeriodoFim());
	// }
	//		
	// }
}