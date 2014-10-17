
package gcom.relatorio.cobranca;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import gcom.batch.Relatorio;
import gcom.cobranca.bean.DocumentoTeleCobrancaHelper;
import gcom.relatorio.ConstantesRelatorios;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ControladorException;
import gcom.util.agendadortarefas.AgendadorTarefas;

public class RelatorioTeleCobranca
				extends TarefaRelatorio {

	public RelatorioTeleCobranca(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_TELECOBRANCA);
	}

	public RelatorioTeleCobranca() {

		super(null, "");
	}

	private static final long serialVersionUID = 1L;

	public Object executar() throws TarefaException{

		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		List<DocumentoTeleCobrancaHelper> collDocumentoTeleCobrancaHelper = (ArrayList) getParametro("collDocumentoTeleCobrancaHelper");
		byte[] retorno = null;

		try{
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(baos));

			for(DocumentoTeleCobrancaHelper helper : collDocumentoTeleCobrancaHelper){
				bw.write(helper.getTipoTelefoneString() + ";");
				bw.write(helper.getTelefonePrincipal() + ";");
				bw.write(helper.getEmpresa() + ";");
				bw.write(helper.getNumeroImovel() + ";");
				bw.write(helper.getNomeCliente() + ";");
				bw.write(helper.getEnderecoImovel() + ";");
				bw.write(helper.getNomeBairro() + ";");
				bw.write(helper.getTelefoneSecundario() + ";");
				bw.write(helper.getValorDebitoFormatado() + ";");
				bw.write(helper.getCep());
				bw.write(System.getProperty("line.separator"));
			}

			bw.close();
			baos.close();

			retorno = baos.toByteArray();

			// Grava o relatório no sistema
			this.persistirRelatorioConcluido(retorno, Relatorio.RELATORIO_TELECOBRANCA, idFuncionalidadeIniciada, null);

		}catch(IOException e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relatório no sistema", e);
		}catch(ControladorException e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relatório no sistema", e);
		}

		return retorno;
	}

	public int calcularTotalRegistrosRelatorio(){

		return 0;
	}

	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioTeleCobranca", this);
	}
}
