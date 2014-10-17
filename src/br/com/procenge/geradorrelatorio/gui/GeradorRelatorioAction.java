
package br.com.procenge.geradorrelatorio.gui;

import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.relatorio.RelatorioGeracaoCrystalReport;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ServiceLocator;
import gcom.util.Util;
import gcom.util.parametrizacao.ParametroContabil;

import java.io.ByteArrayInputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import br.com.procenge.geradorrelatorio.api.ControladorRelatorio;
import br.com.procenge.geradorrelatorio.api.Relatorio;
import br.com.procenge.util.SpringBeanLocator;
import br.com.procenge.util.StrutsDispatchAction;

public class GeradorRelatorioAction
				extends StrutsDispatchAction {
	private static final Logger LOGGER = Logger.getLogger(GeradorRelatorioAction.class);
	private static final String MIME_TYPE_APPLICATION_PDF = "application/pdf";

	public static final String RELATORIO_RELACAO_CONTAS_ADD_PDD = "RelatorioContasAdicionadasPDD.rpt";

	public static final String RELATORIO_MOV_BAIXA_RENEG_CANCEL_PDD = "RelatorioMovBaixaRenegCancelamentoPDD.rpt";

	public static final String RELATORIO_IMPOSTO_FEDERAL = "RelatorioUsuariosComRetencaoImpostoFederal.rpt";

	public static final String RELATORIO_RESUMO_DE_FATURAMENTO = "RelatorioResumoDeFaturamento.rpt";

	public static final String RELATORIO_RESUMO_RECEBIMENTO_ARRECADADOR = "RelatorioResumoRecebimentoArrecadador.rpt";

	public static final String RELATORIO_CONTAS_RECEBER_CONTABIL = "RelatorioContasReceberContabil.rpt";

	private static SpringBeanLocator springBeanLocator = SpringBeanLocator.getInstancia();

	public ActionForward gerarRelatorio(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
					throws Exception{

		ActionForward retorno = null;

		Fachada.getInstancia();

		ByteArrayInputStream byteArrayInputStream = null;
		ControladorRelatorio controladorRelatorio = null;

		DynaActionForm dynaForm = (DynaActionForm) form;

		HttpSession sessao = request.getSession(false);
		Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");

		controladorRelatorio = (ControladorRelatorio) springBeanLocator.getBeanPorID(ControladorRelatorio.BEAN_ID);

		Relatorio relatorio = controladorRelatorio.obterRelatorioPorNome(String.valueOf(dynaForm.get("relatorio")));

		if(relatorio != null){

			Map<String, Object> parametrosConvertidos = controladorRelatorio.converterParametros(relatorio, dynaForm.getMap());
			if(parametrosConvertidos == null || parametrosConvertidos.isEmpty()){
				parametrosConvertidos = dynaForm.getMap();
			}
			String mesAno;
			String nomeRelatorio = (String) dynaForm.get("relatorio");

			String parametroAnoMesReferenciaContabil = ParametroContabil.P_REFERENCIA_CONTABIL.executar();

			if(nomeRelatorio.equals(RELATORIO_RELACAO_CONTAS_ADD_PDD) || nomeRelatorio.equals(RELATORIO_MOV_BAIXA_RENEG_CANCEL_PDD)){

				mesAno = (String) dynaForm.get("mesAnoRelatorioPdd");
				if(Util.compararAnoMesReferencia(Util.obterInteger(mesAno), Util.obterInteger(parametroAnoMesReferenciaContabil), ">")){
					throw new ActionServletException("atencao.data_inferior.ano_mes", null,
									Util.formatarAnoMesSemBarraParaMesAnoComBarra(Util.obterInteger(parametroAnoMesReferenciaContabil)));
				}
			}else if(nomeRelatorio.equals(RELATORIO_IMPOSTO_FEDERAL)){

				mesAno = Util.formatarMesAnoParaAnoMes((String) dynaForm.get("P_AM_REF"));

				if(Util.compararAnoMesReferencia(Util.obterInteger(mesAno), Util.obterInteger(parametroAnoMesReferenciaContabil), ">")){
					throw new ActionServletException("atencao.data_inferior.ano_mes", null,
									Util.formatarAnoMesSemBarraParaMesAnoComBarra(Util.obterInteger(parametroAnoMesReferenciaContabil)));
				}

			}else if(nomeRelatorio.equals(RELATORIO_RESUMO_DE_FATURAMENTO)
							|| nomeRelatorio.equals(RELATORIO_RESUMO_RECEBIMENTO_ARRECADADOR)
							|| nomeRelatorio.equals(RELATORIO_CONTAS_RECEBER_CONTABIL)){

				mesAno = Util.formatarMesAnoParaAnoMes((String) dynaForm.get("mesAno"));

				if(Util.compararAnoMesReferencia(Util.obterInteger(mesAno), Util.obterInteger(parametroAnoMesReferenciaContabil), ">")){
					throw new ActionServletException("atencao.data_inferior.ano_mes", null,
									Util.formatarAnoMesSemBarraParaMesAnoComBarra(Util.obterInteger(parametroAnoMesReferenciaContabil)));
				}
			}
			
			// imprimir parâmetros no log - investigação
			LOGGER.info(dynaForm.getMap().toString());
			// Envia uma requisição para gerar relatório em batch
			if(!Util.isVazioOuBranco(relatorio.getProcessoId())){
				RelatorioGeracaoCrystalReport relatorioBatch = new RelatorioGeracaoCrystalReport(usuarioLogado);
				relatorioBatch.addParametro("relatorio", relatorio);
				relatorioBatch.addParametro("parametros", parametrosConvertidos);
				relatorioBatch.addParametro("tipoFormatoRelatorio", TarefaRelatorio.TIPO_PDF);
				ServiceLocator.getInstancia().getControladorBatch().iniciarProcessoRelatorio(relatorioBatch);

				retorno = mapping.findForward("telaApresentacaoBatch");

			}else{
				byteArrayInputStream = controladorRelatorio.gerarRelatorio(relatorio, parametrosConvertidos,
								ControladorRelatorio.FORMATO_PDF);
				downloadRelatorio(byteArrayInputStream, response, MIME_TYPE_APPLICATION_PDF);
			}
		}else{
			throw new ActionServletException("atencao.relatorio.erro");
		}

		return retorno;
	}

	protected Fachada getFachada(){

		return Fachada.getInstancia();
	}

	private void downloadRelatorio(ByteArrayInputStream byteArrayInputStream, HttpServletResponse response, String mimetype)
					throws Exception{

		// Create a byte[] the same size as the exported ByteArrayInputStream.
		byte[] buffer = new byte[byteArrayInputStream.available()];
		int bytesRead = 0;

		// Set response headers to indicate mime type and inline file.
		response.reset();
		response.setHeader("Content-disposition", "inline;filename=report.pdf");
		response.setContentType(mimetype);

		// Stream the byte array to the client.
		while((bytesRead = byteArrayInputStream.read(buffer)) != -1){
			response.getOutputStream().write(buffer, 0, bytesRead);
		}

		// Flush and close the output stream.
		response.getOutputStream().flush();
		response.getOutputStream().close();

	}

}
