
package gcom.relatorio.cobranca;

import gcom.batch.Relatorio;
import gcom.fachada.Fachada;
import gcom.gui.cobranca.RelatorioImovelPorAcaoCobrancaHelper;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.io.*;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.sf.jxls.transformer.XLSTransformer;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

public class RelatorioImoveisPorAcaoCobranca
				extends TarefaRelatorio {

	public RelatorioImoveisPorAcaoCobranca(final Usuario usuario, final String nomeRelatorio) {

		super(usuario, nomeRelatorio);
	}

	public RelatorioImoveisPorAcaoCobranca() {

		super(null, "");
	}

	private static final long serialVersionUID = 1L;

	public int calcularTotalRegistrosRelatorio(){

		final String comando = (String) getParametro("comando");
		final String idComando = (String) getParametro("idComando");
		final String idCronograma = (String) getParametro("idCronograma");
		final String[] acao = (String[]) getParametro("acao");
		final Date dataInicial = (Date) getParametro("dataInicial");
		final Date dataFinal = (Date) getParametro("dataFinal");
		final String grupo = (String) getParametro("grupo");
		final String[] bairro = (String[]) getParametro("bairro");
		final String[] setorComercial = (String[]) getParametro("setorComercial");
		final String[] categoria = (String[]) getParametro("categoria");
		final String localidade = (String) getParametro("localidade");

		Integer retorno = Fachada.getInstancia().filtrarRelatorioImovelPorAcaoCobrancaCount(comando, idComando, idCronograma, acao,
						dataInicial, dataFinal, grupo, setorComercial, bairro, categoria, localidade);
		return retorno;
	}

	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioImoveisPorAcaoCobranca", this);
	}

	public Object executar() throws TarefaException{

		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();

		final ByteArrayOutputStream retorno = new ByteArrayOutputStream();

		/* Coleção de Comandos Cronograma */

		final String comando = (String) getParametro("comando");
		final String idComando = (String) getParametro("idComando");
		final String idCronograma = (String) getParametro("idCronograma");
		final String[] acao = (String[]) getParametro("acao");
		final Date dataInicial = (Date) getParametro("dataInicial");
		final Date dataFinal = (Date) getParametro("dataFinal");
		final String grupo = (String) getParametro("grupo");
		final String[] bairro = (String[]) getParametro("bairro");
		final String[] setorComercial = (String[]) getParametro("setorComercial");
		final String[] categoria = (String[]) getParametro("categoria");
		final String nomeEmpresa = (String) getParametro("nomeEmpresa");
		final String localidade = (String) getParametro("localidade");

		// beans.put("periodoComando", getParametro("periodoComando").toString());
		final Collection<RelatorioImovelPorAcaoCobrancaHelper> colecaoRelatorio = Fachada.getInstancia()
						.filtrarRelatorioImovelPorAcaoCobranca(comando, idComando, idCronograma, acao, dataInicial, dataFinal, grupo,
										setorComercial, bairro, categoria, localidade);
		if(colecaoRelatorio == null || colecaoRelatorio.isEmpty()){
			throw new TarefaException("atencao.pesquisa.nenhumresultado");
		}
		final Map beans = new HashMap();
		beans.put("colecaoRetorno", colecaoRelatorio);
		beans.put("nomeEmpresa", nomeEmpresa);
		if(dataInicial != null && dataFinal != null){
			beans.put("periodoComando", Util.formatarData(dataInicial) + " a " + Util.formatarData(dataFinal));
		}else{
			beans.put("periodoComando", " não informado ");
		}
		try{
			/*
			 * System.out.print(System.getProperty("user.dir"));
			 * FileReader reader = new FileReader(new
			 * File("relatorioEficienciaCobrancaTemplate.xls"));
			 * BufferedReader leitor = new BufferedReader(reader);
			 * String linha = null;
			 * linha = leitor.readLine();
			 * System.out.println("Linha: " + linha);
			 * leitor.close();
			 * reader.close();
			 */
			final FileInputStream fileInput = new FileInputStream((File) getParametro("template"));

			final XLSTransformer transformer = new XLSTransformer();
			final HSSFWorkbook documentoXLS = transformer.transformXLS(fileInput, beans);
			// transformer.transformXLS(templateFileName, beans, destFileName);
			this.novaPlanilhaTipoHSSF(documentoXLS, "relatorioImovelPorAcaoCobranca.xls");

			final FileInputStream inputStream = new FileInputStream("relatorioImovelPorAcaoCobranca.xls");
			final byte[] temp = new byte[1024];
			int numBytesRead = 0;

			while((numBytesRead = inputStream.read(temp, 0, 1024)) != -1){
				retorno.write(temp, 0, numBytesRead);
			}

			inputStream.close();

			// retorno = documentoXLS.getBytes();
		}catch(FileNotFoundException e){
			throw new TarefaException("atencao.planilha_template_nao_encontrado", e);
		}catch(IOException e1){
			throw new TarefaException("", e1);
		}

		try{
			persistirRelatorioConcluido(retorno.toByteArray(), Relatorio.RELATORIO_IMOVEL_ACAO_COBRANCA, idFuncionalidadeIniciada, "");
		}catch(ControladorException e){
			throw new TarefaException("atencao.relatorio.erro", e);
		}
		return retorno.toByteArray();
	}

	public void novaPlanilhaTipoHSSF(HSSFWorkbook hSSFWorkbook, String destFileName) throws IOException{

		final Workbook workBook = (Workbook) hSSFWorkbook;
		final FileOutputStream fileOut = new FileOutputStream(destFileName);
		workBook.write(fileOut);
		fileOut.close();
	}
}
