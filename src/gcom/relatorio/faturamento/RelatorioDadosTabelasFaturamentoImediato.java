
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

import java.io.*;
import java.util.Collection;
import java.util.zip.ZipOutputStream;

/**
 * [UC3012] – Gerar Arquivo com Dados e Tabelas do Faturamento Imediado
 * 
 * @author Hebert Falcão
 * @date 12/04/2012
 */
public class RelatorioDadosTabelasFaturamentoImediato
				extends TarefaRelatorio {

	public RelatorioDadosTabelasFaturamentoImediato(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_DADOS_TABELAS_FATURAMENTO_IMEDIATO);
	}

	public RelatorioDadosTabelasFaturamentoImediato() {

		super(null, "");
	}

	private static final long serialVersionUID = 1L;

	public Object executar() throws TarefaException{

		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		StringBuffer conteudoArquivoDados = (StringBuffer) getParametro("conteudoArquivoDados");
		StringBuffer conteudoArquivoTabelas = (StringBuffer) getParametro("conteudoArquivoTabelas");
		String nomeArquivoDados = (String) getParametro("nomeArquivoDados");
		String nomeArquivoTabelas = (String) getParametro("nomeArquivoTabelas");

		StringBuffer conteudoArquivoMetLigacao = (StringBuffer) getParametro("conteudoArquivoMetLigacao");
		String nomeArquivoMetLigacao = (String) getParametro("nomeArquivoMetLigacao");
		String nomeArquivoZipado = (String) getParametro("nomeArquivoZipado");

		EnvioEmail envioEmail = (EnvioEmail) getParametro("envioEmail");
		byte[] retorno = null;
		Collection<RelatorioGerado> colecaoRelatorioGerado = null;

		Fachada fachada = Fachada.getInstancia();

		try{
			String nomeArquivo = "";
			FiltroRelatorioGerado filtroRelatorioGerado = null;

			if(!Util.isVazioOuBranco(nomeArquivoDados)){
				filtroRelatorioGerado = new FiltroRelatorioGerado();
				filtroRelatorioGerado.adicionarParametro(new ComparacaoTextoCompleto(FiltroRelatorioGerado.DESCRICAO_ARQUIVO,
								nomeArquivoDados));

				colecaoRelatorioGerado = fachada.pesquisar(filtroRelatorioGerado, RelatorioGerado.class.getName());

				if(!Util.isVazioOrNulo(colecaoRelatorioGerado)){
					for(RelatorioGerado relatorioGerado : colecaoRelatorioGerado){
						fachada.remover(relatorioGerado);
					}
				}

				nomeArquivo = nomeArquivoDados + "; ";
			}

			if(!Util.isVazioOuBranco(nomeArquivoTabelas)){

				filtroRelatorioGerado = new FiltroRelatorioGerado();
				filtroRelatorioGerado.adicionarParametro(new ComparacaoTextoCompleto(FiltroRelatorioGerado.DESCRICAO_ARQUIVO,
								nomeArquivoTabelas));

				colecaoRelatorioGerado = fachada.pesquisar(filtroRelatorioGerado, RelatorioGerado.class.getName());

				if(!Util.isVazioOrNulo(colecaoRelatorioGerado)){
					for(RelatorioGerado relatorioGerado : colecaoRelatorioGerado){
						fachada.remover(relatorioGerado);
					}
				}

				nomeArquivo = nomeArquivo + nomeArquivoTabelas;

				retorno = this.getBytesFromFileZip(conteudoArquivoDados, nomeArquivoDados, conteudoArquivoTabelas, nomeArquivoTabelas,
								envioEmail);
			}else if(conteudoArquivoDados != null){

				retorno = this.getBytesFromFileZip(conteudoArquivoDados, nomeArquivoDados, envioEmail);
			}else{

				filtroRelatorioGerado = new FiltroRelatorioGerado();
				filtroRelatorioGerado.adicionarParametro(new ComparacaoTextoCompleto(FiltroRelatorioGerado.DESCRICAO_ARQUIVO,
								nomeArquivoZipado));

				colecaoRelatorioGerado = fachada.pesquisar(filtroRelatorioGerado, RelatorioGerado.class.getName());

				if(!Util.isVazioOrNulo(colecaoRelatorioGerado)){
					for(RelatorioGerado relatorioGerado : colecaoRelatorioGerado){
						fachada.remover(relatorioGerado);
					}
				}

				nomeArquivo = nomeArquivoZipado;

				retorno = this.getBytesFromFileZipParametrizado(conteudoArquivoMetLigacao, nomeArquivoMetLigacao, envioEmail);
			}

			this.persistirRelatorioConcluido(retorno, Relatorio.GERAR_DADOS_TABELAS_FATURAMENTO_IMEDIATO, idFuncionalidadeIniciada,
							nomeArquivo);
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

	private byte[] getBytesFromFileZip(StringBuffer conteudoArquivoDados, String nomeArquivoDados, StringBuffer conteudoArquivoTabelas,
					String nomeArquivoTabelas, EnvioEmail envioEmail) throws IOException, Exception{

		File compactado = new File("FaturamentoImediato.zip");
		ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(compactado));

		File arquivoDados = new File(nomeArquivoDados);

		BufferedWriter outDados = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(arquivoDados.getAbsolutePath())));
		outDados.write(conteudoArquivoDados.toString());
		outDados.flush();
		outDados.close();

		File arquivoTabelas = new File(nomeArquivoTabelas);

		BufferedWriter outTabelas = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(arquivoTabelas.getAbsolutePath())));
		outTabelas.write(conteudoArquivoTabelas.toString());
		outTabelas.flush();
		outTabelas.close();

		ZipUtil.adicionarArquivos(zos, arquivoDados, arquivoTabelas);

		zos.close();
		arquivoDados.delete();
		arquivoTabelas.delete();
		byte[] retorno = this.getBytesFromFile(compactado);

		ServicosEmail.enviarMensagemArquivoAnexado(envioEmail.getEmailReceptor(), envioEmail.getEmailRemetente(), envioEmail
						.getTituloMensagem(), envioEmail.getCorpoMensagem(), compactado);

		compactado.delete();

		return retorno;
	}

	private byte[] getBytesFromFileZip(StringBuffer conteudoArquivoDados, String nomeArquivoDados, EnvioEmail envioEmail)
					throws IOException, Exception{

		File compactado = new File("FaturamentoImediato.zip");
		ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(compactado));

		File arquivoDados = new File(nomeArquivoDados);

		BufferedWriter outDados = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(arquivoDados.getAbsolutePath())));
		outDados.write(conteudoArquivoDados.toString());
		outDados.flush();
		outDados.close();

		ZipUtil.adicionarArquivo(zos, arquivoDados);

		zos.close();
		arquivoDados.delete();
		byte[] retorno = this.getBytesFromFile(compactado);

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

		AgendadorTarefas.agendarTarefa("RelatorioDadosTabelasFaturamentoImediato", this);
	}

	/**
	 * Método que transforma um arquivo em um array de bytes
	 * 
	 * @author Hebert Falcão
	 * @date 26/05/2014
	 */
	private byte[] getBytesFromFileZipParametrizado(StringBuffer conteudoArquivo, String nomeArquivo, EnvioEmail envioEmail)
					throws IOException, Exception{

		File compactado = new File(nomeArquivo + ".zip");
		ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(compactado));

		File arquivo = new File(nomeArquivo + ".dat");

		BufferedWriter outMetLigacao = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(arquivo.getAbsolutePath())));
		outMetLigacao.write(conteudoArquivo.toString());
		outMetLigacao.flush();
		outMetLigacao.close();

		ZipUtil.adicionarArquivos(zos, arquivo);

		zos.close();

		arquivo.delete();

		byte[] retorno = this.getBytesFromFile(compactado);

		ServicosEmail.enviarMensagemArquivoAnexado(envioEmail.getEmailReceptor(), envioEmail.getEmailRemetente(),
						envioEmail.getTituloMensagem(), envioEmail.getCorpoMensagem(), compactado);

		compactado.delete();

		return retorno;
	}

}
