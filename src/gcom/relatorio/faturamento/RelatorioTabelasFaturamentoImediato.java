
package gcom.relatorio.faturamento;

import gcom.batch.Relatorio;
import gcom.batch.RelatorioGerado;
import gcom.cadastro.EnvioEmail;
import gcom.fachada.Fachada;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.FiltroRelatorioGerado;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.ZipUtil;
import gcom.util.agendadortarefas.AgendadorTarefas;
import gcom.util.email.ServicosEmail;
import gcom.util.filtro.ComparacaoTextoCompleto;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.Collection;
import java.util.zip.ZipOutputStream;

/**
 * @author Ailton Sousa
 * @date 23/08/2011
 */
public class RelatorioTabelasFaturamentoImediato
				extends TarefaRelatorio {

	public RelatorioTabelasFaturamentoImediato(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_TABELAS_FATURAMENTO_IMEDIATO);
	}

	public RelatorioTabelasFaturamentoImediato() {

		super(null, "");
	}

	private static final long serialVersionUID = 1L;

	public Object executar() throws TarefaException{

		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		StringBuffer sb = (StringBuffer) getParametro("arquivoTexto");
		String nomeArquivo = (String) getParametro("nomeArquivo");
		EnvioEmail envioEmail = (EnvioEmail) getParametro("envioEmail");
		byte[] retorno = null;
		Collection colecaoRelatorioGerado = null;

		Fachada fachada = Fachada.getInstancia();

		try{
			FiltroRelatorioGerado filtroRelatorioGerado = new FiltroRelatorioGerado();
			filtroRelatorioGerado.adicionarParametro(new ComparacaoTextoCompleto(FiltroRelatorioGerado.DESCRICAO_ARQUIVO, nomeArquivo));

			colecaoRelatorioGerado = fachada.pesquisar(filtroRelatorioGerado, RelatorioGerado.class.getName());

			if(colecaoRelatorioGerado != null && !colecaoRelatorioGerado.isEmpty()){
				RelatorioGerado relatorioGerado = (RelatorioGerado) Util.retonarObjetoDeColecao(colecaoRelatorioGerado);
				fachada.remover(relatorioGerado);
			}

			retorno = this.getBytesFromFileZip(sb, nomeArquivo, envioEmail);
			this.persistirRelatorioConcluido(retorno, Relatorio.GERAR_TABELAS_FATURAMENTO_IMEDIATO, idFuncionalidadeIniciada, nomeArquivo);

		}catch(ControladorException e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relatório no sistema", e);
		}catch(IOException e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relatório no sistema", e);
		}catch(Exception e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relatório no sistema", e);
		}

		return retorno;
	}

	private byte[] getBytesFromFileZip(StringBuffer sb, String nomeArquivo, EnvioEmail envioEmail) throws IOException, Exception{

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

		ServicosEmail.enviarMensagemArquivoAnexado(envioEmail.getEmailReceptor(), envioEmail.getEmailRemetente(), envioEmail
						.getTituloMensagem(), envioEmail.getCorpoMensagem(), compactado);

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

	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioTabelasFaturamentoImediato", this);
	}
}
