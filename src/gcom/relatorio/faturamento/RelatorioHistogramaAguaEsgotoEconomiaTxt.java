package gcom.relatorio.faturamento;

import gcom.batch.Relatorio;
import gcom.fachada.Fachada;
import gcom.faturamento.bean.FiltrarEmitirHistogramaAguaEconomiaHelper;
import gcom.faturamento.bean.FiltrarEmitirHistogramaEsgotoEconomiaHelper;
import gcom.relatorio.ConstantesRelatorios;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ZipUtil;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.zip.ZipOutputStream;


public class RelatorioHistogramaAguaEsgotoEconomiaTxt
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	public RelatorioHistogramaAguaEsgotoEconomiaTxt(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_EMITIR_HISTOGRAMA_AGUA_ESGOTO_ECONOMIA_TXT);

	}

	@Deprecated
	public RelatorioHistogramaAguaEsgotoEconomiaTxt() {

		super(null, "");

	}

	public int calcularTotalRegistrosRelatorio(){

		return 0;
	}

	public Object executar() throws TarefaException{

		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		FiltrarEmitirHistogramaAguaEconomiaHelper filtroAgua = (FiltrarEmitirHistogramaAguaEconomiaHelper) this.getParametro("filtroAgua");
		FiltrarEmitirHistogramaEsgotoEconomiaHelper filtroEsgoto = (FiltrarEmitirHistogramaEsgotoEconomiaHelper) this
						.getParametro("filtroEsgoto");

		StringBuffer dadosHistogramaAguaEsgotoEconomia = Fachada.getInstancia().obterDadosHistogramaAguaEsgotoEconomia(filtroAgua,
						filtroEsgoto);

		byte[] retorno = null;

		try{

			retorno = this.getBytesFromFileZip(dadosHistogramaAguaEsgotoEconomia);

			this.persistirRelatorioConcluido(retorno, Relatorio.GERAR_DADOS_TABELAS_FATURAMENTO_IMEDIATO, idFuncionalidadeIniciada,
							"ARQ. HISTOGRAMA AGUA/ESGOTO POR ECONOMIA");

		}catch(Exception e){

			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relatório no sistema", e);

		}

		return retorno;

	}

	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioHistogramaAguaEsgotoEconomiaTxt", this);

	}

	private byte[] getBytesFromFileZip(StringBuffer conteudoArquivoDados) throws Exception{

		File compactado = new File("DadosHistogramaAguaEsgotoEconomia.zip");
		ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(compactado));

		File arquivoDados = new File("DadosHistogramaAguaEsgotoEconomia.txt");

		BufferedWriter outDados = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(arquivoDados.getAbsolutePath())));
		outDados.write(conteudoArquivoDados.toString());
		outDados.flush();
		outDados.close();

		ZipUtil.adicionarArquivos(zos, arquivoDados);

		zos.close();

		arquivoDados.delete();

		return this.getBytesFromFile(compactado);

	}

	private byte[] getBytesFromFile(File file) throws IOException{

		InputStream is = new FileInputStream(file);

		// Get the size of the file
		long length = file.length();

		if(length > Integer.MAX_VALUE){
			// File is too large
		}

		// Create the byte array to hold the data
		byte[] bytes = new byte[(int) length];

		// Read in the bytes
		int offset = 0;
		int numRead = 0;
		while(offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0){
			offset += numRead;
		}

		// Ensure all the bytes have been read in
		if(offset < bytes.length){
			throw new IOException("Could not completely read file " + file.getName());
		}

		// Close the input stream and return bytes
		is.close();
		return bytes;
	}

}
