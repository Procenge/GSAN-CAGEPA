
package gcom.relatorio.cobranca;

import gcom.batch.Relatorio;
import gcom.cobranca.bean.ProdutividadeMensalExecucaoServicoRelatorioHelper;
import gcom.fachada.Fachada;
import gcom.relatorio.ConstantesRelatorios;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ControladorException;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.sf.jxls.transformer.XLSTransformer;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * @author isilva
 */
public class RelatorioProdutividadeMensalExecucaoServico
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	public RelatorioProdutividadeMensalExecucaoServico(Usuario usuario, String nomeRelatorio) {

		super(usuario, nomeRelatorio);
	}

	public RelatorioProdutividadeMensalExecucaoServico(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_PRODUTIVIDADE_MENSAL_EXECUCAO_SERVICO);
	}

	public RelatorioProdutividadeMensalExecucaoServico() {

		super(null, "");
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object executar() throws TarefaException{

		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();

		ByteArrayOutputStream retorno = new ByteArrayOutputStream();

		// *********
		String tipoComando = (String) getParametro("tipoComando");
		String idCobrancaAcaoComando = (String) getParametro("idCobrancaAcaoComando");
		String idCobrancaAcaoCronograma = (String) getParametro("idCobrancaAcaoCronograma");
		String padraoPeriodo = (String) getParametro("padraoPeriodo");
		String periodoInicio = (String) getParametro("periodoInicio");
		String periodoFim = (String) getParametro("periodoFim");
		String periodoMesInicio = (String) getParametro("periodoMesInicio");
		String periodoMesFim = (String) getParametro("periodoMesFim");
		String periodoAnoInicio = (String) getParametro("periodoAnoInicio");
		String periodoAnoFim = (String) getParametro("periodoAnoFim");
		String localidade = (String) getParametro("localidade");
		Integer acaoSelecionada = (Integer) getParametro("acaoSelecionada");
		Integer[] empresas = (Integer[]) getParametro("empresas");
		Integer[] grupos = (Integer[]) getParametro("grupos");
		Integer[] setores = (Integer[]) getParametro("setores");
		Integer[] bairros = (Integer[]) getParametro("bairros");
		Integer[] grupoServicos = (Integer[]) getParametro("grupoServicos");
		Integer[] subGrupoServicos = (Integer[]) getParametro("subGrupoServicos");
		Integer[] servicos = (Integer[]) getParametro("servicos");
		Integer[] tiposCorte = (Integer[]) getParametro("tiposCorte");
		Integer[] tiposSupressao = (Integer[]) getParametro("tiposSupressao");

		Collection colecaoRetorno = new ArrayList<ProdutividadeMensalExecucaoServicoRelatorioHelper>();
		// Adicionar paramentros para pesquisa
		/**
		 * [0] - colecaoProdutividadeMensalExecucaoServicoRelatorioHelper
		 * [1] - evolucaoEnviadaTotal
		 * [2] - evolucaoExecutadaTotal
		 * [3] - acumuladaEnviadaTotal
		 * [4] - acumuladaExecutadaTotal
		 * [5] - sucessoTotal
		 * [6] - sucessoAcumuladaTotal
		 * [7] - mediaTotal
		 */
		Object[] retornoHelper = Fachada.getInstancia().pesquisarProdutividadeMensalExecucaoServico(tipoComando, idCobrancaAcaoComando,
						idCobrancaAcaoCronograma, padraoPeriodo, periodoInicio, periodoFim, periodoMesInicio, periodoMesFim,
						periodoAnoInicio, periodoAnoFim, localidade, acaoSelecionada, empresas, grupos, setores, bairros, grupoServicos,
						subGrupoServicos, servicos, tiposCorte, tiposSupressao);

		if(retornoHelper != null && retornoHelper.length != 0){
			if(retornoHelper[0] != null && !((Collection<ProdutividadeMensalExecucaoServicoRelatorioHelper>) retornoHelper[0]).isEmpty()){
				colecaoRetorno = (Collection<ProdutividadeMensalExecucaoServicoRelatorioHelper>) retornoHelper[0];
			}
		}

		/**
		 * [0] - colecaoProdutividadeMensalExecucaoServicoRelatorioHelper
		 * [1] - evolucaoEnviadaTotal
		 * [2] - evolucaoExecutadaTotal
		 * [3] - acumuladaEnviadaTotal
		 * [4] - acumuladaExecutadaTotal
		 * [5] - sucessoTotal
		 * [6] - sucessoAcumuladaTotal
		 * [7] - mediaTotal
		 */
		Map beans = new HashMap();
		beans.put("colecaoRetorno", colecaoRetorno);
		beans.put("evolucaoEnviadaTotal", (String) retornoHelper[1]);
		beans.put("evolucaoExecutadaTotal", (String) retornoHelper[2]);
		beans.put("acumuladaEnviadaTotal", (Integer) retornoHelper[3]);
		beans.put("acumuladaExecutadaTotal", (Integer) retornoHelper[4]);
		beans.put("sucessoTotal", (String) retornoHelper[5]);
		beans.put("sucessoAcumuladaTotal", (String) retornoHelper[6]);
		beans.put("mediaTotal", (BigDecimal) retornoHelper[7]);

		try{

			File template = (File) getParametro("template");

			FileInputStream fileInput = new FileInputStream(template);

			XLSTransformer transformer = new XLSTransformer();
			HSSFWorkbook documentoXLS = transformer.transformXLS(fileInput, beans);
			this.novaPlanilhaTipoHSSF(documentoXLS, "relatorioProdutividadeMensalExecucaoServico.xls");

			FileInputStream inputStream = new FileInputStream("relatorioProdutividadeMensalExecucaoServico.xls");
			byte[] temp = new byte[1024];
			int numBytesRead = 0;

			while((numBytesRead = inputStream.read(temp, 0, 1024)) != -1){
				retorno.write(temp, 0, numBytesRead);
			}

			inputStream.close();
			inputStream = null;

		}catch(IOException e1){
			e1.printStackTrace();
			throw new TarefaException("", e1);
		}

		try{
			persistirRelatorioConcluido(retorno.toByteArray(), Relatorio.RELATORIO_PRODUTIVIDADE_MENSAL_EXECUCAO_SERVICO,
							idFuncionalidadeIniciada, null);
		}catch(ControladorException e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relatório no sistema", e);
		}
		return retorno.toByteArray();
	}

	@Override
	public int calcularTotalRegistrosRelatorio(){

		return -1;
	}

	@Override
	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioProdutividadeMensalExecucaoServico", this);
	}

	public void novaPlanilhaTipoHSSF(HSSFWorkbook hSSFWorkbook, String destFileName) throws IOException{

		Workbook wb = (Workbook) hSSFWorkbook;
		FileOutputStream fileOut = new FileOutputStream(destFileName);
		wb.write(fileOut);
		fileOut.close();
	}
}