
package gcom.relatorio.faturamento;

import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.relatorio.RelatorioGeracaoCrystalReport;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ServiceLocator;
import gcom.util.Util;
import gcom.util.parametrizacao.ParametroContabil;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Iterator;
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

public class GerarRelatorioConciliacaoAction
				extends StrutsDispatchAction {
	private static final Logger LOGGER = Logger.getLogger(GerarRelatorioConciliacaoAction.class);
	private static final String MIME_TYPE_APPLICATION_PDF = "application/pdf";

	private static SpringBeanLocator springBeanLocator = SpringBeanLocator.getInstancia();

	public ActionForward gerarRelatorio(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
					throws Exception{

		ActionForward retorno = null;

		ByteArrayInputStream byteArrayInputStream = null;
		ControladorRelatorio controladorRelatorio = null;
		HttpSession sessao = request.getSession(false);
		Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");
		DynaActionForm dynaForm = (DynaActionForm) form;
		
		String parametroAnoMesReferenciaContabil = ParametroContabil.P_REFERENCIA_CONTABIL.executar();

		String mesAno = Util.formatarMesAnoParaAnoMes((String) dynaForm.get("mesAno"));

		if(Util.compararAnoMesReferencia(Util.obterInteger(mesAno), getFachada().pesquisarParametrosDoSistema().getAnoMesArrecadacao(), ">")){
			throw new ActionServletException("atencao.data_inferior.ano_mes", null, Util.formatarAnoMesSemBarraParaMesAnoComBarra(Util
							.obterInteger(parametroAnoMesReferenciaContabil)));
		}

		controladorRelatorio = (ControladorRelatorio) springBeanLocator.getBeanPorID(ControladorRelatorio.BEAN_ID);

		Relatorio relatorio = controladorRelatorio.obterRelatorioPorNome(String.valueOf(dynaForm.get("relatorio")));

		if(relatorio != null){

			Map<String, Object> parametrosConvertidos = controladorRelatorio.converterParametros(relatorio, dynaForm.getMap());
			if(parametrosConvertidos == null || parametrosConvertidos.isEmpty()){
				parametrosConvertidos = dynaForm.getMap();
			}
			Map<String, Object> parametrosFinais = new HashMap<String, Object>();
			Iterator param = parametrosConvertidos.entrySet().iterator();
			while(param.hasNext()){
				Map.Entry entidade = (Map.Entry) param.next();
				if(entidade.getValue() != null){
					parametrosFinais.put(entidade.getKey().toString(), entidade.getValue());
				}
			}
			// imprimir parâmetros no log - investigação
			LOGGER.info(dynaForm.getMap().toString());
			// Envia uma requisição para gerar relatório em batch
			if(!Util.isVazioOuBranco(relatorio.getProcessoId())){
				RelatorioGeracaoCrystalReport relatorioBatch = new RelatorioGeracaoCrystalReport(usuarioLogado);
				relatorioBatch.addParametro("relatorio", relatorio);
				relatorioBatch.addParametro("parametros", parametrosFinais);
				relatorioBatch.addParametro("tipoFormatoRelatorio", TarefaRelatorio.TIPO_PDF);
				ServiceLocator.getInstancia().getControladorBatch().iniciarProcessoRelatorio(relatorioBatch);

				retorno = mapping.findForward("telaApresentacaoBatch");

			}else{
				byteArrayInputStream = controladorRelatorio.gerarRelatorio(relatorio, parametrosFinais,
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
