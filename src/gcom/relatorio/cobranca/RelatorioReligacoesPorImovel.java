
package gcom.relatorio.cobranca;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.sf.jxls.transformer.XLSTransformer;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

import br.com.procenge.util.Constantes;

import gcom.batch.Relatorio;
import gcom.cobranca.RelatorioReligacoesPorImovelHelper;
import gcom.fachada.Fachada;
import gcom.relatorio.ConstantesRelatorios;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ControladorException;
import gcom.util.agendadortarefas.AgendadorTarefas;

public class RelatorioReligacoesPorImovel
				extends TarefaRelatorio {

	public RelatorioReligacoesPorImovel(Usuario usuario, String nomeRelatorio) {

		super(usuario, nomeRelatorio);
	}

	public RelatorioReligacoesPorImovel(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_RELIGACAO_POR_IMOVEL);
	}

	public RelatorioReligacoesPorImovel() {

		super(null, "");
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Object executar() throws TarefaException{

		ByteArrayOutputStream retorno = new ByteArrayOutputStream();

		Boolean comandoCronograma = this.getParametro("comandoCronograma") != null ? (Boolean) this.getParametro("comandoCronograma")
						: false;
		Boolean comandoEventual = this.getParametro("comandoEventual") != null ? (Boolean) this.getParametro("comandoEventual") : false;
		Date dataInicio = this.getParametro("dataInicio") != null ? (Date) this.getParametro("dataInicio") : null;
		Date dataFim = this.getParametro("dataFim") != null ? (Date) this.getParametro("dataFim") : null;
		Collection<Integer> idsAcaoCobranca = this.getParametro("idsAcaoCobranca") != null ? (Collection<Integer>) this
						.getParametro("idsAcaoCobranca") : null;
		Integer idGrupoCobranca = this.getParametro("idGrupoCobranca") != null ? (Integer) this.getParametro("idGrupoCobranca") : null;
		Collection<Integer> idsSetorComercial = this.getParametro("idsSetorComercial") != null ? (Collection<Integer>) this
						.getParametro("idsSetorComercial") : null;
		Collection<Integer> idsBairro = this.getParametro("idsBairro") != null ? (Collection<Integer>) this.getParametro("idsBairro")
						: null;
		Collection<Integer> idsCategoria = this.getParametro("idsCategoria") != null ? (Collection<Integer>) this
						.getParametro("idsCategoria") : null;
		Collection<Integer> idsServicoTipo = this.getParametro("idsServicoTipo") != null ? (Collection<Integer>) this
						.getParametro("idsServicoTipo") : null;
		// File template = (File) getParametro("template");
		Integer idComandoCronograma = this.getParametro("idComandoCronograma") != null ? (Integer) this.getParametro("idComandoCronograma")
						: null;
		Integer idComandoEventual = this.getParametro("idComandoEventual") != null ? (Integer) this.getParametro("idComandoEventual")
						: null;

		Collection<RelatorioReligacoesPorImovelHelper> helpers = Fachada.getInstancia().obterRegistrosRelatorioReligacoesPorImovel(
						comandoCronograma, comandoEventual, dataInicio, dataFim, idsAcaoCobranca, idGrupoCobranca, idsSetorComercial,
						idsBairro, idsCategoria, idsServicoTipo, idComandoCronograma, idComandoEventual);

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Constantes.LOCALE_PT_BR);

		Map beans = new HashMap();
		beans.put("colecaoRetorno", helpers);
		beans.put("periodo", simpleDateFormat.format(dataInicio) + " à " + simpleDateFormat.format(dataFim));

		try{

			File template = (File) getParametro("template");

			FileInputStream fileInput = new FileInputStream(template);

			XLSTransformer transformer = new XLSTransformer();
			HSSFWorkbook documentoXLS = transformer.transformXLS(fileInput, beans);
			// transformer.transformXLS(templateFileName, beans, destFileName);
			this.novaPlanilhaTipoHSSF(documentoXLS, "relatorioReligacaoPorImovel.xls");

			FileInputStream inputStream = new FileInputStream("relatorioReligacaoPorImovel.xls");
			byte[] temp = new byte[1024];
			int numBytesRead = 0;

			while((numBytesRead = inputStream.read(temp, 0, 1024)) != -1){
				retorno.write(temp, 0, numBytesRead);
			}

			inputStream.close();

		}catch(FileNotFoundException e){
			throw new TarefaException("atencao.planilha_template_nao_encontrado", e);
		}catch(IOException e1){
			throw new TarefaException("", e1);
		}

		// Grava o relatório no sistema
		try{
			this.persistirRelatorioConcluido(retorno.toByteArray(), Relatorio.RELATORIO_RELIGACAO_POR_IMOVEL, this
							.getIdFuncionalidadeIniciada(), "");
		}catch(ControladorException e){
			throw new TarefaException("Erro ao gravar relatório no sistema", e);
		}

		return retorno.toByteArray();
	}

	@Override
	public int calcularTotalRegistrosRelatorio(){

		Boolean comandoCronograma = this.getParametro("comandoCronograma") != null ? (Boolean) this.getParametro("comandoCronograma")
						: false;
		Boolean comandoEventual = this.getParametro("comandoEventual") != null ? (Boolean) this.getParametro("comandoEventual") : false;
		Date dataInicio = this.getParametro("dataInicio") != null ? (Date) this.getParametro("dataInicio") : null;
		Date dataFim = this.getParametro("dataFim") != null ? (Date) this.getParametro("dataFim") : null;
		Collection<Integer> idsAcaoCobranca = this.getParametro("idsAcaoCobranca") != null ? (Collection<Integer>) this
						.getParametro("idsAcaoCobranca") : null;
		Integer idGrupoCobranca = this.getParametro("idGrupoCobranca") != null ? (Integer) this.getParametro("idGrupoCobranca") : null;
		Collection<Integer> idsSetorComercial = this.getParametro("idsSetorComercial") != null ? (Collection<Integer>) this
						.getParametro("idsSetorComercial") : null;
		Collection<Integer> idsBairro = this.getParametro("idsBairro") != null ? (Collection<Integer>) this.getParametro("idsBairro")
						: null;
		Collection<Integer> idsCategoria = this.getParametro("idsCategoria") != null ? (Collection<Integer>) this
						.getParametro("idsCategoria") : null;
		Collection<Integer> idsServicoTipo = this.getParametro("idsServicoTipo") != null ? (Collection<Integer>) this
						.getParametro("idsServicoTipo") : null;
		Integer idComandoCronograma = this.getParametro("idComandoCronograma") != null ? (Integer) this.getParametro("idComandoCronograma")
						: null;
		Integer idComandoEventual = this.getParametro("idComandoEventual") != null ? (Integer) this.getParametro("idComandoEventual")
						: null;

		int retorno = Fachada.getInstancia().consultarQuantidadeRegistrosRelatorioReligacoesPorImovel(comandoCronograma, comandoEventual,
						dataInicio, dataFim, idsAcaoCobranca, idGrupoCobranca, idsSetorComercial, idsBairro, idsCategoria, idsServicoTipo,
						idComandoCronograma, idComandoEventual);

		return retorno;
	}

	@Override
	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioReligacoesPorImovel", this);
	}

	public void novaPlanilhaTipoHSSF(final HSSFWorkbook hSSFWorkbook, final String destFileName) throws IOException{

		Workbook workbook = (Workbook) hSSFWorkbook;
		FileOutputStream fileOut = new FileOutputStream(destFileName);
		workbook.write(fileOut);
		fileOut.close();
	}
}
