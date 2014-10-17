
package gcom.relatorio.cobranca;

import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ControladorException;
import gcom.util.agendadortarefas.AgendadorTarefas;
import gcom.util.parametrizacao.atendimentopublico.ParametroAtendimentoPublico;

import java.io.ByteArrayOutputStream;
import java.util.*;

import com.lowagie.text.Document;
import com.lowagie.text.pdf.PRAcroForm;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;

/**
 * [UC3052] Gerar e Emitir Aviso de Corte e Ordem de Corte Individual
 * 
 * @author Hebert Falcão
 * @date 16/03/2012
 */
public class RelatorioAvisoEOrdemCorteIndividual
				extends TarefaRelatorio {

	public RelatorioAvisoEOrdemCorteIndividual(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_AVISO_E_ORDEM_CORTE_INDIVIDUAL);
	}

	@Deprecated
	public RelatorioAvisoEOrdemCorteIndividual() {

		super(null, "");
	}

	/**
	 * Método que executa a tarefa
	 * 
	 * @return Object
	 */
	public Object executar() throws TarefaException{

		// Obtém a instância da fachada
		Fachada fachada = Fachada.getInstancia();

		Map<String, String> parametros = new HashMap();

		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();
		parametros.put("imagem", sistemaParametro.getImagemRelatorio());


		Collection<RelatorioAvisoEOrdemCorteIndividualBean> colecaoAux = (Collection<RelatorioAvisoEOrdemCorteIndividualBean>) getParametro("relatorioAvisoEOrdemCorteIndividualBean");

		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		List<byte[]> pdfsGerados = new ArrayList<byte[]>();

		Iterator<RelatorioAvisoEOrdemCorteIndividualBean> iterator = colecaoAux.iterator();

		Collection<RelatorioAvisoEOrdemCorteIndividualBean> colecaoAviso = Arrays.asList(iterator.next());
		Collection<RelatorioAvisoEOrdemCorteIndividualBean> colecaoCorte = Arrays.asList(iterator.next());

		RelatorioDataSource dsAviso1 = new RelatorioDataSource((List) colecaoAviso);
		RelatorioDataSource dsAviso2 = new RelatorioDataSource((List) colecaoAviso);
		RelatorioDataSource dsCorte = new RelatorioDataSource((List) colecaoCorte);

		try{
			// Caso o valor do parâmetro seja igual 2, imprime o texto específica para a DESO.
			String parametroTextoAvisoCorte = ParametroAtendimentoPublico.P_TEXTO_AVISO_CORTE.executar();

			if(parametroTextoAvisoCorte == null){
				throw new ControladorException("erro.parametro.sistema.inexistente");
			}
			if(parametroTextoAvisoCorte.trim().equals("2")){

				// Página 01
				byte[] retorno = this.gerarRelatorio(ConstantesRelatorios.RELATORIO_AVISO_E_ORDEM_CORTE_INDIVIDUAL_1_DESO, parametros,
								dsAviso1, tipoFormatoRelatorio);
				pdfsGerados.add(retorno);

				// Página 02
				retorno = this.gerarRelatorio(ConstantesRelatorios.RELATORIO_AVISO_E_ORDEM_CORTE_INDIVIDUAL_2_DESO, parametros, dsAviso2,
								tipoFormatoRelatorio);
				pdfsGerados.add(retorno);

				// Página 03
				retorno = this.gerarRelatorio(ConstantesRelatorios.RELATORIO_AVISO_E_ORDEM_CORTE_INDIVIDUAL_3, parametros, dsCorte,
								tipoFormatoRelatorio);
				pdfsGerados.add(retorno);

			}else{
				// Página 01
				byte[] retorno = this.gerarRelatorio(ConstantesRelatorios.RELATORIO_AVISO_E_ORDEM_CORTE_INDIVIDUAL_1_E_2, parametros,
								dsAviso1,
								tipoFormatoRelatorio);
				pdfsGerados.add(retorno);

				// Página 02
				// retorno =
				// this.gerarRelatorio(ConstantesRelatorios.RELATORIO_AVISO_E_ORDEM_CORTE_INDIVIDUAL_2,
				// parametros, dsAviso2,
				// tipoFormatoRelatorio);
				// pdfsGerados.add(retorno);

				// Página 03
				retorno = this.gerarRelatorio(ConstantesRelatorios.RELATORIO_AVISO_E_ORDEM_CORTE_INDIVIDUAL_3, parametros, dsCorte,
								tipoFormatoRelatorio);
				pdfsGerados.add(retorno);
			}
		}catch(Exception e){

		}
		return concatenarPFDs(pdfsGerados);
	}

	@Override
	public int calcularTotalRegistrosRelatorio(){

		return 0;
	}

	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioAvisoEOrdemCorteIndividual", this);
	}

	private byte[] concatenarPFDs(List<byte[]> pdfsGerados) throws TarefaException{

		int pageOffset = 0;
		int f = 0;
		Document document = null;
		PdfCopy writer = null;
		byte[] pdfGerado = null;
		byte[] novoPDF = null;
		ByteArrayOutputStream baos = null;

		try{
			if(pdfsGerados != null && !pdfsGerados.isEmpty()){
				Iterator<byte[]> iterator = pdfsGerados.iterator();
				baos = new ByteArrayOutputStream();
				while(iterator.hasNext()){
					pdfGerado = (byte[]) iterator.next();
					PdfReader reader = new PdfReader(pdfGerado);
					int n = reader.getNumberOfPages();
					pageOffset += n;
					if(f == 0){
						document = new Document(reader.getPageSizeWithRotation(1));
						writer = new PdfCopy(document, baos);
						document.open();
					}
					PdfImportedPage page;
					for(int i = 0; i < n;){
						++i;
						page = writer.getImportedPage(reader, i);
						writer.addPage(page);
					}
					PRAcroForm form = reader.getAcroForm();
					if(form != null){
						writer.copyAcroForm(reader);
					}
					f++;
				}
				document.close();
			}
		}catch(Exception e){
			throw new TarefaException(e.getMessage(), e);
		}

		novoPDF = baos.toByteArray();

		return novoPDF;
	}

}
