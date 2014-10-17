
package gcom.relatorio.cobranca;

import gcom.batch.Relatorio;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cobranca.bean.EmitirCartaOpcaoParcelamentoDetailHelper;
import gcom.cobranca.bean.EmitirCartaOpcaoParcelamentoHelper;
import gcom.fachada.Fachada;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.ZipUtil;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

import org.apache.commons.validator.GenericValidator;

//UC01101 - Emitir Carta com Opção de parcelamento
public class RelatorioCartaOpcaoParcelamento
				extends TarefaRelatorio {

	public RelatorioCartaOpcaoParcelamento(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_CARTA_OPCAO_PARCELAMENTO);
	}

	public RelatorioCartaOpcaoParcelamento() {

		super(null, "");
	}

	private static final long serialVersionUID = 1L;

	public Object executar() throws TarefaException{

		byte[] retorno = null;

		String descricaoArquivo = null;
		if(getParametro("descricaoArquivo") != null){
			descricaoArquivo = (String) getParametro("descricaoArquivo");
		}

		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");
		int formatoArquivo = (Integer) getParametro("formatoArquivo");

		List<EmitirCartaOpcaoParcelamentoHelper> colecaoCartaOpcaoParcelamentoHelper = (ArrayList) getParametro("colecaoEmitirCartaOpcaoParcelamentoHelper");

		if(formatoArquivo == TarefaRelatorio.TIPO_CSV){
			StringBuffer sb = new StringBuffer();

			if(!Util.isVazioOrNulo(colecaoCartaOpcaoParcelamentoHelper)){
				for(EmitirCartaOpcaoParcelamentoHelper emitirCartaOpcaoParcelamentoHelper : colecaoCartaOpcaoParcelamentoHelper){
					sb.append(emitirCartaOpcaoParcelamentoHelper.getMatricula());
					sb.append(";");
					sb.append(emitirCartaOpcaoParcelamentoHelper.getNomeCliente());
					sb.append(";");
					sb.append(emitirCartaOpcaoParcelamentoHelper.getEndereco());
					sb.append(";");
					sb.append(emitirCartaOpcaoParcelamentoHelper.getBairro());
					sb.append(";");
					sb.append(emitirCartaOpcaoParcelamentoHelper.getCep());
					sb.append(";");
					sb.append(emitirCartaOpcaoParcelamentoHelper.getSaldoPrincipal());
					sb.append(";");
					sb.append(emitirCartaOpcaoParcelamentoHelper.getJuros());
					sb.append(";");
					sb.append(emitirCartaOpcaoParcelamentoHelper.getMulta());
					sb.append(";");
					sb.append(emitirCartaOpcaoParcelamentoHelper.getDividaTotal());

					List<EmitirCartaOpcaoParcelamentoDetailHelper> opcoesDeParcelamento = emitirCartaOpcaoParcelamentoHelper
									.getOpcoesDeParcelamento();

					if(!Util.isVazioOrNulo(opcoesDeParcelamento)){
						for(EmitirCartaOpcaoParcelamentoDetailHelper emitirCartaOpcaoParcelamentoDetailHelper : opcoesDeParcelamento){
							sb.append(";");
							sb.append("Opção " + emitirCartaOpcaoParcelamentoDetailHelper.getPosicaoOpcao());
							sb.append(";");
							sb.append(emitirCartaOpcaoParcelamentoDetailHelper.getDesconto());
							sb.append(";");
							sb.append(emitirCartaOpcaoParcelamentoDetailHelper.getaPagar());
							sb.append(";");
							sb.append(emitirCartaOpcaoParcelamentoDetailHelper.getValorEntrada());
							sb.append(";");
							sb.append(emitirCartaOpcaoParcelamentoDetailHelper.getNumeroPrestacoes());
							sb.append(";");
							sb.append(emitirCartaOpcaoParcelamentoDetailHelper.getValorPrestacao());
							sb.append(";");
							sb.append(emitirCartaOpcaoParcelamentoDetailHelper.getRepresentacaoNumericaCodBarraFormatada());
						}
					}

					sb.append(System.getProperty("line.separator"));
				}
			}

			try{
				retorno = this.getBytesFromFileZip(sb, "relatorioCartaOpcoesParcelamento");
			}catch(IOException e){
				e.printStackTrace();
				throw new TarefaException("Erro ao gravar relatório no sistema", e);
			}
		}else{
			List<RelatorioCartaOpcaoParcelamentoBean> colecaoCartaOpcaoParcelamentoBean = new ArrayList<RelatorioCartaOpcaoParcelamentoBean>();

			for(Iterator iterator = colecaoCartaOpcaoParcelamentoHelper.iterator(); iterator.hasNext();){
				EmitirCartaOpcaoParcelamentoHelper emitirCartaOpcaoParcelamentoHelper = (EmitirCartaOpcaoParcelamentoHelper) iterator
								.next();

				RelatorioCartaOpcaoParcelamentoBean novoBean = new RelatorioCartaOpcaoParcelamentoBean(emitirCartaOpcaoParcelamentoHelper);
				colecaoCartaOpcaoParcelamentoBean.add(novoBean);
			}

			Map parametros = new HashMap();

			SistemaParametro sistemaParametro = Fachada.getInstancia().pesquisarParametrosDoSistema();

			// MensagemCartaOpcaoParcelamento
			String mensagemCartaOpcaoParcelamento = sistemaParametro.getMensagemCartaOpcaoParcelamento();
			if(GenericValidator.isBlankOrNull(mensagemCartaOpcaoParcelamento)){
				parametros.put("mensagemCartaOpcaoParcelamento", "");
			}else{
				parametros.put("mensagemCartaOpcaoParcelamento", mensagemCartaOpcaoParcelamento);
			}

			Boolean exibirMensagemComprovante = (Boolean) getParametro("exibirMensagemComprovante");
			if(exibirMensagemComprovante == null){
				exibirMensagemComprovante = true;
			}
			parametros.put("exibirMensagemComprovante", exibirMensagemComprovante);

			parametros.put("imagem", sistemaParametro.getImagemRelatorio());

			RelatorioDataSource ds = new RelatorioDataSource(colecaoCartaOpcaoParcelamentoBean);

			retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_CARTA_OPCAO_PARCELAMENTO, parametros, ds, tipoFormatoRelatorio);
		}

		// Grava o relatório no sistema
		try{
			this.persistirRelatorioConcluido(retorno, Relatorio.RELATORIO_CARTA_OPCAO_PARCELAMENTO, idFuncionalidadeIniciada,
							descricaoArquivo);
		}catch(ControladorException e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relatório no sistema", e);
		}

		return retorno;
	}

	public int calcularTotalRegistrosRelatorio(){

		int retorno = 0;

		return retorno;
	}

	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioCartaOpçãoParcelamento", this);
	}

	private byte[] getBytesFromFileZip(StringBuffer sb, String nomeArquivo) throws IOException{

		byte[] retorno = null;
		ZipOutputStream zos = null;
		BufferedWriter out = null;
		File leituraTipo = new File(nomeArquivo + ".csv");
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

}
