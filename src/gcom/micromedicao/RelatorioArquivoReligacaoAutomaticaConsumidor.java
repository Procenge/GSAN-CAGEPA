
package gcom.micromedicao;

import gcom.batch.Relatorio;
import gcom.relatorio.ConstantesRelatorios;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ControladorException;
import gcom.util.ZipUtil;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.io.*;
import java.util.zip.ZipOutputStream;

/**
 * [UC0948] Informar Dados de Leituras e Anormalidades
 * Religa��o Autom�tica de Consumidor
 * 
 * @author Hebert Falc�o
 * @created 04/08/2013
 */
public class RelatorioArquivoReligacaoAutomaticaConsumidor
				extends TarefaRelatorio {

	public RelatorioArquivoReligacaoAutomaticaConsumidor(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_ARQUIVO_RELIGACAO_AUTOMATICA_CONSUMIDOR);
	}

	public RelatorioArquivoReligacaoAutomaticaConsumidor() {

		super(null, "");
	}

	private static final long serialVersionUID = 1L;

	@Override
	public Object executar() throws TarefaException{

		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();

		Integer idFaturamentoGrupo = (Integer) getParametro("idFaturamentoGrupo");
		Integer anoMesReferencia = (Integer) getParametro("anoMesReferencia");
		StringBuffer arquivoReligacaoAutomaticaConsumidor = (StringBuffer) getParametro("arquivoReligacaoAutomaticaConsumidor");
		String nomeArquivo = "RAC_" + anoMesReferencia + "_" + idFaturamentoGrupo;

		byte[] retorno = null;

		try{
			retorno = this.getBytesFromFileZip(arquivoReligacaoAutomaticaConsumidor, nomeArquivo);
			this.persistirRelatorioConcluido(retorno, Relatorio.RELATORIO_ARQUIVO_RELIGACAO_AUTOMATICA_CONSUMIDOR,
							idFuncionalidadeIniciada, nomeArquivo);

		}catch(ControladorException e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relat�rio no sistema", e);
		}catch(IOException e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relat�rio no sistema", e);
		}catch(Exception e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relat�rio no sistema", e);
		}

		return retorno;
	}

	private byte[] getBytesFromFileZip(StringBuffer sb, String nomeArquivo) throws IOException, Exception{

		byte[] retorno = null;
		ZipOutputStream zos = null;
		BufferedWriter out = null;
		File leituraTipo = new File(nomeArquivo);
		File compactado = new File(nomeArquivo + ".zip");

		zos = new ZipOutputStream(new FileOutputStream(compactado));
		out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(leituraTipo.getAbsolutePath())));

		out.write(sb.toString());
		out.flush();
		out.close();

		ZipUtil.adicionarArquivo(zos, leituraTipo);

		zos.close();
		leituraTipo.delete();
		retorno = this.getBytesFromFile(compactado);

		compactado.delete();

		return retorno;
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

	public int calcularTotalRegistrosRelatorio(){

		return 0;
	}

	@Override
	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioArquivoReligacaoAutomaticaConsumidor", this);
	}

}