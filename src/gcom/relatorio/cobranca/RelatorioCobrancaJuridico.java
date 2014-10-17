
package gcom.relatorio.cobranca;

import gcom.batch.Relatorio;
import gcom.cobranca.bean.DocumentoCobrancaJuridicoHelper;
import gcom.relatorio.ConstantesRelatorios;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ControladorException;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class RelatorioCobrancaJuridico
				extends TarefaRelatorio {

	public RelatorioCobrancaJuridico(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_JURIDICO);
	}

	public RelatorioCobrancaJuridico() {

		super(null, "");
	}

	private static final long serialVersionUID = 1L;

	public Object executar() throws TarefaException{

		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		List<DocumentoCobrancaJuridicoHelper> colecaoDocumentoCobrancaJuridicoHelper = (ArrayList) getParametro("colecaoCobrancaJuridico");
		byte[] retorno = null;

		try{

			StringBuilder stringBuilder = new StringBuilder();

			for(DocumentoCobrancaJuridicoHelper helper : colecaoDocumentoCobrancaJuridicoHelper){
				stringBuilder.append("\"" + helper.getIdImovel() + "\";");
				stringBuilder.append("\"" + helper.getInscricao() + "\";");
				stringBuilder.append("\"" + helper.getQtdDebito() + "\";");
				stringBuilder.append("\"" + helper.getValorDebito() + "\";");
				stringBuilder.append("\"" + helper.getNomeCliente() + "\";");
				stringBuilder.append("\"" + helper.getGrupoCobranca() + "\";");
				stringBuilder.append("\"" + helper.getSetorComercial() + "\";");
				stringBuilder.append("\"" + helper.getRoteiro() + "\";");
				stringBuilder.append("\"" + helper.getEnderecoImovel() + "\";");
				stringBuilder.append("\"" + helper.getBairro() + "\";");
				stringBuilder.append("\"" + helper.getCep() + "\";");
				stringBuilder.append("\"" + helper.getEnderecoEntrega() + "\";");
				stringBuilder.append("\"" + helper.getLigacaoSituacao() + "\";");
				stringBuilder.append("\"" + helper.getImovelCategoria() + "\";");
				stringBuilder.append("\"" + helper.getFoneRecado() + "\";");
				stringBuilder.append("\"" + helper.getFoneComercial() + "\";");
				stringBuilder.append("\"" + helper.getFoneResidencial() + "\";");
				stringBuilder.append("\"" + helper.getDocumentoTipo1() + "\";");
				stringBuilder.append("\"" + helper.getDocumentoNumero1() + "\";");
				stringBuilder.append("\"" + helper.getDocumentoTipo2() + "\";");
				if(helper.getDocumentoNumero2() != null && !helper.getDocumentoNumero2().equals("")){
					stringBuilder.append("\"" + helper.getDocumentoNumero2() + "\";");
				}else{
					stringBuilder.append(" ;");
				}
				stringBuilder.append("\"" + helper.getDebitoTipo() + "\";");
				stringBuilder.append("\"" + helper.getDescricaoImovelPerfil() + "\";");
				stringBuilder.append("\"" + helper.getLigacaoData() + "\";");
				stringBuilder.append("\"" + helper.getLigacaoTipo() + "\";");
				stringBuilder.append("\"" + helper.getMedidorNumero() + "\";");
				stringBuilder.append("\"" + helper.getCodigoBarras() + "\";");
				stringBuilder.append("\"" + helper.getIndicadorDocumentoEmitido() + "\";");
				stringBuilder.append("\"" + helper.getDataEmissaoDocumento() + "\";");
				stringBuilder.append("\"" + helper.getExecucaoOSSituacao() + "\";");
				stringBuilder.append("\"" + helper.getDataExecucao() + "\";");
				stringBuilder.append("\"" + helper.getUltimaLeitura() + "\";");
				stringBuilder.append("\"" + helper.getLinhaSequencial() + "\";");
				stringBuilder.append("\"" + helper.getImovelNumero() + "\"");

				stringBuilder.append(System.getProperty("line.separator"));
			}

			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(stringBuilder.length());
			BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(byteArrayOutputStream));

			bufferedWriter.write(stringBuilder.toString());
			bufferedWriter.close();
			byteArrayOutputStream.close();
			retorno = byteArrayOutputStream.toByteArray();

			// Grava o relatório no sistema
			try{
				this.persistirRelatorioConcluido(retorno, Relatorio.RELATORIO_COBRANCA_JURIDICO, idFuncionalidadeIniciada, "");
			}catch(ControladorException e){
				e.printStackTrace();
				throw new TarefaException("Erro ao gravar relatório no sistema", e);
			}

		}catch(IOException e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relatório no sistema", e);
		}

		return retorno;
	}

	public int calcularTotalRegistrosRelatorio(){

		return 0;
	}

	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioCobrancaJuridico", this);
	}
}