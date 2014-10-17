
package gcom.batch.cobranca;

import gcom.batch.Relatorio;
import gcom.fachada.Fachada;
import gcom.gui.cobranca.RelatorioAcompReligacaoEspecialHelper;
import gcom.relatorio.ConstantesRelatorios;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ControladorException;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.sf.jxls.transformer.XLSTransformer;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

import br.com.procenge.util.Constantes;

public class RelatorioRelacaoImovelReligacaoEspecialDia
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	public RelatorioRelacaoImovelReligacaoEspecialDia(Usuario usuario, String nomeRelatorio) {

		super(usuario, nomeRelatorio);
	}

	public RelatorioRelacaoImovelReligacaoEspecialDia(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_ACOMPANHAMENTO_RELIGACOES_ESPECIAIS);
	}

	public RelatorioRelacaoImovelReligacaoEspecialDia() {

		super(null, "");
	}

	@Override
	public int calcularTotalRegistrosRelatorio(){

		Integer idUnidade = getParametro("idUnidade") != null ? (Integer) getParametro("idUnidade") : null;
		Date periodoInicio = getParametro("periodoInicio") != null ? (Date) getParametro("periodoInicio") : null;
		Date periodoFim = getParametro("periodoFim") != null ? (Date) getParametro("periodoFim") : null;
		Integer idGrupo = getParametro("grupo") != null ? (Integer) getParametro("grupo") : null;
		String[] setores = getParametro("setor") != null ? (String[]) getParametro("setor") : null;
		String[] bairros = getParametro("bairro") != null ? (String[]) getParametro("bairro") : null;
		String[] servicosTipo = getParametro("servicoTipo") != null ? (String[]) getParametro("servicoTipo") : null;

		return Fachada.getInstancia().relacaoImovelReligacaoEspecialDiaCount(idUnidade, periodoInicio, periodoFim, idGrupo, setores,
						bairros, servicosTipo);

	}

	@Override
	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioRelacaoImovelReligacaoEspecialDia", this);

	}

	@Override
	public Object executar() throws TarefaException{

		ByteArrayOutputStream retorno = new ByteArrayOutputStream();

		Integer idUnidade = getParametro("idUnidade") != null ? (Integer) getParametro("idUnidade") : null;
		Date periodoInicio = getParametro("periodoInicio") != null ? (Date) getParametro("periodoInicio") : null;
		Date periodoFim = getParametro("periodoFim") != null ? (Date) getParametro("periodoFim") : null;
		Integer idGrupo = getParametro("grupo") != null ? (Integer) getParametro("grupo") : null;
		String[] setores = getParametro("setor") != null ? (String[]) getParametro("setor") : null;
		String[] bairros = getParametro("bairro") != null ? (String[]) getParametro("bairro") : null;
		String[] servicosTipo = getParametro("servicoTipo") != null ? (String[]) getParametro("servicoTipo") : null;

		Collection<RelatorioAcompReligacaoEspecialHelper> retornoHelper = Fachada.getInstancia()
						.relacaoRelatorioImovelReligacaoEspecialDia(idUnidade, periodoInicio, periodoFim, idGrupo, setores, bairros,
										servicosTipo);

		Integer total = 0;

		if(retornoHelper != null && !retornoHelper.isEmpty()){
			total = retornoHelper.size();
		}else{
			retornoHelper = new ArrayList<RelatorioAcompReligacaoEspecialHelper>();
		}

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Constantes.LOCALE_PT_BR);

		Map beans = new HashMap();
		beans.put("colecaoRetorno", retornoHelper);
		beans.put("periodo", simpleDateFormat.format(periodoInicio) + " à " + simpleDateFormat.format(periodoFim));
		beans.put("total", total);

		try{

			File template = (File) getParametro("template");

			FileInputStream fileInput = new FileInputStream(template);

			XLSTransformer transformer = new XLSTransformer();
			HSSFWorkbook documentoXLS = transformer.transformXLS(fileInput, beans);
			this.novaPlanilhaTipoHSSF(documentoXLS, "relatorioRelacaoImoveisReligacaoEspecialDia.xls");

			FileInputStream inputStream = new FileInputStream("relatorioRelacaoImoveisReligacaoEspecialDia.xls");
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
			this.persistirRelatorioConcluido(retorno.toByteArray(), Relatorio.RELATORIO_ACOMPANHAMENTO_RELIGACOES_ESPECIAIS, this
							.getIdFuncionalidadeIniciada(), "");
		}catch(ControladorException e){
			throw new TarefaException("Erro ao gravar relatório no sistema", e);
		}

		return retorno.toByteArray();
	}

	public void novaPlanilhaTipoHSSF(final HSSFWorkbook hSSFWorkbook, final String destFileName) throws IOException{

		Workbook workbook = (Workbook) hSSFWorkbook;
		FileOutputStream fileOut = new FileOutputStream(destFileName);
		workbook.write(fileOut);
		fileOut.close();
	}

}
