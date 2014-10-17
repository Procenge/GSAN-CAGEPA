
package gcom.relatorio.cobranca;

import gcom.batch.Relatorio;
import gcom.relatorio.ConstantesRelatorios;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.sf.jxls.transformer.XLSTransformer;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

public class RelatorioAcompanhamentoExecucaoServicoCobranca
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	public RelatorioAcompanhamentoExecucaoServicoCobranca(Usuario usuario, String nomeRelatorio) {

		super(usuario, nomeRelatorio);
	}

	public RelatorioAcompanhamentoExecucaoServicoCobranca(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_ACOMPANHAMENTO_EXECUCAO_SERVICO_COBRANCA);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object executar() throws TarefaException{

		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();

		ByteArrayOutputStream retorno = new ByteArrayOutputStream();

		/* Coleção de Comandos Cronograma */
		Collection colecaoRetorno = (Collection) getParametro("colecaoRetorno");

		File template = (File) getParametro("template");

		Map beans = new HashMap();
		beans.put("colecaoRetorno", colecaoRetorno);

		if(!Util.isVazioOuBranco(getParametro("periodoInicial")) && !Util.isVazioOuBranco(getParametro("periodoFim"))){
			beans.put("periodo", getParametro("periodoInicial") + " à " + getParametro("periodoFim"));
		}else{
			beans.put("periodo", "");
		}

		beans.put("dataGeracao", getParametro("dataGeracao"));

		try{
			FileInputStream fileInput = new FileInputStream(template);

			XLSTransformer transformer = new XLSTransformer();
			HSSFWorkbook documentoXLS = transformer.transformXLS(fileInput, beans);
			this.novaPlanilhaTipoHSSF(documentoXLS, "relatorioAcompanhamentoExecucaoServicoCobranca.xls");

			FileInputStream inputStream = new FileInputStream("relatorioAcompanhamentoExecucaoServicoCobranca.xls");
			byte[] temp = new byte[1024];
			int numBytesRead = 0;

			while((numBytesRead = inputStream.read(temp, 0, 1024)) != -1){
				retorno.write(temp, 0, numBytesRead);
			}

			inputStream.close();
			inputStream = null;

			// retorno = documentoXLS.getBytes();
		}catch(FileNotFoundException e){
			throw new TarefaException("atencao.planilha_template_nao_encontrado ", e);
		}catch(IOException e1){
			throw new TarefaException("", e1);
		}
		try{
			persistirRelatorioConcluido(retorno.toByteArray(), Relatorio.RELATORIO_ACOMPANHAMENTO_EXECUCAO_SERVICO_COBRANCA,
							idFuncionalidadeIniciada, null);
		}catch(ControladorException e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relatório no sistema", e);
		}
		return retorno.toByteArray();
	}

	@Override
	public int calcularTotalRegistrosRelatorio(){

		return 0;
	}

	@Override
	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioAcompanhamentoExecucaoServicoCobranca", this);
	}

	public void novaPlanilhaTipoHSSF(HSSFWorkbook hSSFWorkbook, String destFileName) throws IOException{

		Workbook wb = (Workbook) hSSFWorkbook;
		FileOutputStream fileOut = new FileOutputStream(destFileName);
		wb.write(fileOut);
		fileOut.close();
	}

}
