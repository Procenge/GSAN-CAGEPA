
package gcom.relatorio;

import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

import br.com.procenge.geradorrelatorio.api.ControladorRelatorio;
import br.com.procenge.util.SpringBeanLocator;

/**
 * Classe que implementa a tarefa batch de relatório responsável por gerar todos os relatórios
 * implementados no Crystal Report
 * 
 * @author Luciano Galvão
 * @date 16/05/2012
 */
public class RelatorioGeracaoCrystalReport
				extends TarefaRelatorio {

	public RelatorioGeracaoCrystalReport(Usuario usuario) {

		super(usuario, "");
	}

	public RelatorioGeracaoCrystalReport() {

		super(null, "");
	}

	private static final long serialVersionUID = 1L;

	public static final String RELATORIO_CONTAS_EM_ATRAZO_POR_IDADE_DA_DIVIDA = "RelatorioContasEmAtrasoPorIdadeDaDivida.rpt";

	public Object executar() throws TarefaException{

		br.com.procenge.geradorrelatorio.api.Relatorio relatorio = (br.com.procenge.geradorrelatorio.api.Relatorio) getParametro("relatorio");
		
		

		Map<String, Object> parametros = (Map<String, Object>) getParametro("parametros");

		byte[] retorno = null;

		try{
			// Gera o relatório Crystal Report
			byte[] conteudoRelatorio = null;
			if(parametros.get("formatoRelatorio") != null && parametros.get("formatoRelatorio").equals(ConstantesSistema.XLS)){
				conteudoRelatorio = getBytesFromByteArrayInputStream(getControladorRelatorio().gerarRelatorio(relatorio, parametros,
								ControladorRelatorio.FORMATO_PLANILHA));
			}else{
				conteudoRelatorio = getBytesFromByteArrayInputStream(getControladorRelatorio().gerarRelatorio(relatorio, parametros,
								ControladorRelatorio.FORMATO_PDF));
			}

			// Verifica se o relatorioId é válido
			if((relatorio.getRelatorioId() == null) || (!Util.isNumero(relatorio.getRelatorioId(), false, 0))){
				throw new TarefaException("Este Relatório do Crystal Report não está preparado para executar em Batch");
			}

			// Persiste o relatório gerado para consulta posterior pelo Módulo de Relatórios Batch
			this.persistirRelatorioConcluido(conteudoRelatorio, Integer.valueOf(relatorio.getRelatorioId()),
							this.getIdFuncionalidadeIniciada(), "Relatorio Gerado");

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

	private ControladorRelatorio getControladorRelatorio(){

		ControladorRelatorio controladorRelatorio = (ControladorRelatorio) SpringBeanLocator.getInstancia().getBeanPorID(
						ControladorRelatorio.BEAN_ID);

		return controladorRelatorio;
	}

	private byte[] getBytesFromByteArrayInputStream(ByteArrayInputStream byteArrayInputStream) throws Exception{

		// Create a byte[] the same size as the exported ByteArrayInputStream.
		byte[] buffer = new byte[byteArrayInputStream.available()];
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		int bytesRead = byteArrayInputStream.read(buffer);

		// Stream the byte array to the client.
		while(bytesRead != -1){
			out.write(buffer, 0, bytesRead);
			bytesRead = byteArrayInputStream.read(buffer);
		}

		return out.toByteArray();
	}

	public static void main(String[] args){

		String conteudo = "abcdefghijlmnopqrstuvxz0123456789";

		try{
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(conteudo.getBytes());
			byte[] buff = null;

			// Create a byte[] the same size as the exported ByteArrayInputStream.
			byte[] buffer = new byte[byteArrayInputStream.available()];
			ByteArrayOutputStream out = new ByteArrayOutputStream();

			int bytesRead = byteArrayInputStream.read(buffer);

			// Stream the byte array to the client.
			while(bytesRead != -1){
				out.write(buffer, 0, bytesRead);
				bytesRead = byteArrayInputStream.read(buffer);
			}

			buff = out.toByteArray();

			System.out.println(new String(buff));
		}catch(IOException ioe){
			ioe.printStackTrace();
		}
	}

	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioCrystalReport", this);
	}

	@Override
	public int calcularTotalRegistrosRelatorio(){

		// TODO Auto-generated method stub
		return 0;
	}
}
