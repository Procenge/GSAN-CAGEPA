
package gcom.relatorio.cobranca;

import gcom.batch.Relatorio;
import gcom.cadastro.empresa.Empresa;
import gcom.cadastro.empresa.FiltroEmpresa;
import gcom.cobranca.bean.CobrancaAcaoTituloHelper;
import gcom.cobranca.bean.FaixaHelper;
import gcom.fachada.Fachada;
import gcom.relatorio.ConstantesRelatorios;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.agendadortarefas.AgendadorTarefas;
import gcom.util.filtro.ParametroSimples;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.sf.jxls.transformer.XLSTransformer;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * @author isilva
 * @date 05/08/2010
 */
public class RelatorioAcompanhamentoAcao
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	public RelatorioAcompanhamentoAcao(Usuario usuario, String nomeRelatorio) {

		super(usuario, nomeRelatorio);
	}

	public RelatorioAcompanhamentoAcao(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_ACOMPANHAMENTO_ACAO);
	}

	public RelatorioAcompanhamentoAcao() {

		super(null, "");
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object executar() throws TarefaException{

		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();

		ByteArrayOutputStream retorno = new ByteArrayOutputStream();

		// *********
		String tipoComando = (String) getParametro("tipoComando");
		String acaoSelecionada = (String) getParametro("acaoSelecionada");
		String empresa = (String) getParametro("empresa");
		String periodoInicio = (String) getParametro("periodoInicio");
		String periodoFim = (String) getParametro("periodoFim");
		String idCobrancaAcaoAtividadeComando = (String) getParametro("idCobrancaAcaoAtividadeComando");
		String idCobrancaAcaoAtividadeCronograma = (String) getParametro("idCobrancaAcaoAtividadeCronograma");
		// ******

		/**
		 * [0] - tituloAcoes
		 * [1] - faixas
		 * [2] - parcelamentos
		 * [3] - subTotalFaixas
		 * [4] - totalGeral
		 */
		Object[] retornoHelper = Fachada.getInstancia().pesquisarRelatorioAcompanhamentoAcao(tipoComando, acaoSelecionada, empresa,
						periodoInicio, periodoFim, idCobrancaAcaoAtividadeComando, idCobrancaAcaoAtividadeCronograma);

		ArrayList<CobrancaAcaoTituloHelper> tituloAcoes = new ArrayList<CobrancaAcaoTituloHelper>();
		ArrayList<FaixaHelper> faixas = new ArrayList<FaixaHelper>();
		ArrayList<FaixaHelper> parcelamentos = new ArrayList<FaixaHelper>();
		ArrayList<FaixaHelper> subTotalFaixas = new ArrayList<FaixaHelper>();
		ArrayList<FaixaHelper> totalGeral = new ArrayList<FaixaHelper>();

		if(retornoHelper != null && retornoHelper.length != 0){

			// Títulos
			if(retornoHelper[0] != null && !((ArrayList<CobrancaAcaoTituloHelper>) retornoHelper[0]).isEmpty()){
				tituloAcoes = (ArrayList<CobrancaAcaoTituloHelper>) retornoHelper[0];
			}

			// Faixas
			if(retornoHelper[1] != null && !((ArrayList<FaixaHelper>) retornoHelper[1]).isEmpty()){
				faixas = (ArrayList<FaixaHelper>) retornoHelper[1];
			}

			// Parcelamentos
			if(retornoHelper[2] != null && !((ArrayList<FaixaHelper>) retornoHelper[2]).isEmpty()){
				parcelamentos = (ArrayList<FaixaHelper>) retornoHelper[2];
			}

			// SubTotal
			if(retornoHelper[3] != null && !((ArrayList<FaixaHelper>) retornoHelper[3]).isEmpty()){
				subTotalFaixas = (ArrayList<FaixaHelper>) retornoHelper[3];
			}

			// Total
			if(retornoHelper[4] != null && !((ArrayList<FaixaHelper>) retornoHelper[4]).isEmpty()){
				totalGeral = (ArrayList<FaixaHelper>) retornoHelper[4];
			}

		}

		Map beans = new HashMap();
		beans.put("tituloAcoes", tituloAcoes);
		beans.put("faixas", faixas);
		beans.put("parcelamentos", parcelamentos);
		beans.put("subTotalFaixas", subTotalFaixas);
		beans.put("totalGeral", totalGeral);
		beans.put("periodo", periodoInicio + " à " + periodoFim);

		if(!Util.isVazioOuBranco(empresa)){
			FiltroEmpresa filtroEmpresa = new FiltroEmpresa();
			filtroEmpresa.adicionarParametro(new ParametroSimples(FiltroEmpresa.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));
			filtroEmpresa.adicionarParametro(new ParametroSimples(FiltroEmpresa.ID, empresa));

			Empresa empresaRetorno = (Empresa) Util.retonarObjetoDeColecao(Fachada.getInstancia().pesquisar(filtroEmpresa,
							Empresa.class.getName()));

			if(empresaRetorno != null){
				beans.put("nomeEmpresa", empresaRetorno.getDescricao());
			}else{
				beans.put("nomeEmpresa", "");
			}
		}else{
			beans.put("nomeEmpresa", "");
		}

		try{

			File template = (File) getParametro("template");

			FileInputStream fileInput = new FileInputStream(template);

			XLSTransformer transformer = new XLSTransformer();
			HSSFWorkbook documentoXLS = transformer.transformXLS(fileInput, beans);
			this.novaPlanilhaTipoHSSF(documentoXLS, "relatorioAcompanhamentoAcao.xls");

			FileInputStream inputStream = new FileInputStream("relatorioAcompanhamentoAcao.xls");
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
			persistirRelatorioConcluido(retorno.toByteArray(), Relatorio.RELATORIO_ACOMPANHAMENTO_ACAO, idFuncionalidadeIniciada, null);
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

		AgendadorTarefas.agendarTarefa("RelatorioAcompanhamentoAcao", this);
	}

	public void novaPlanilhaTipoHSSF(HSSFWorkbook hSSFWorkbook, String destFileName) throws IOException{

		Workbook wb = (Workbook) hSSFWorkbook;
		FileOutputStream fileOut = new FileOutputStream(destFileName);
		wb.write(fileOut);
		fileOut.close();
	}
}