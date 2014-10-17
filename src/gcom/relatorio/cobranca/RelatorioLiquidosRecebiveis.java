
package gcom.relatorio.cobranca;

import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cobranca.RelatorioLiquidosRecebiveisHelper;
import gcom.fachada.Fachada;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.relatorio.ordemservico.GeradorRelatorioException;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.Util;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.lowagie.text.Document;
import com.lowagie.text.pdf.PRAcroForm;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;

public class RelatorioLiquidosRecebiveis
				extends TarefaRelatorio {

	private static final String TIPO_RELATORIO_AMBOS = "3";

	private static final String TIPO_RELATORIO_SINTETICO = "2";

	private static final String TIPO_RELATORIO_ANALITICO = "1";
	private static final String TOTAL_GERAL_PAGO = " TOTAL GERAL PAGO : ";
	private static final String TOTAL_DO_DIA = "TOTAL DO DIA ";

	public RelatorioLiquidosRecebiveis(Usuario usuario, String nomeRelatorio) {

		super(usuario, nomeRelatorio);
	}

	public RelatorioLiquidosRecebiveis(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_LIQUIDOS_RECEBIVEIS_ANALITICO);
	}

	public RelatorioLiquidosRecebiveis() {

		super(null, "");
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Object executar() throws TarefaException{

		byte[] retorno = null;
		Map parametros = new HashMap();
		RelatorioDataSource relatorioDataSource = null;
		Date dataInicio = (Date) this.getParametro("dataInicio");
		Date dataFim = (Date) this.getParametro("dataFim");
		String comando = (String) this.getParametro("comando");

		SistemaParametro sistemaParametro = Fachada.getInstancia().pesquisarParametrosDoSistema();
		parametros.put("imagem", sistemaParametro.getImagemRelatorio());
		parametros.put("P_NM_ESTADO", sistemaParametro.getNomeEstado());
		parametros.put("periodo", Util.formatarData(dataInicio) + " ATE " + Util.formatarData(dataFim));

		if(TIPO_RELATORIO_ANALITICO.equals(comando)){

			List<RelatorioLiquidosRecebiveisHelper> helpersAnalitico = Fachada.getInstancia()
							.consultarRegistrosDeLiquidosRecebiveisAnalitico(dataInicio, dataFim);

			List<RelatorioLiquidosRecebiveisHelper> helpersAnaliticoComTotais = preencherAnalitico(helpersAnalitico);

			relatorioDataSource = new RelatorioDataSource(helpersAnaliticoComTotais);
			retorno = gerarRelatorio(getNomeRelatorio(), parametros, relatorioDataSource, TarefaRelatorio.TIPO_PDF);
		}


		// Caso seja sintético ou ambos
		if(TIPO_RELATORIO_SINTETICO.equals(comando)){

			Collection<RelatorioLiquidosRecebiveisHelper> helpersSintetico = Fachada.getInstancia()
							.consultarRegistrosDeLiquidosRecebiveisSintetico(dataInicio, dataFim);

			List<RelatorioLiquidosRecebiveisHelper> helpersSinteticoComTotais = preencherSintetico(helpersSintetico);

			relatorioDataSource = new RelatorioDataSource(helpersSinteticoComTotais);
			retorno = gerarRelatorio(getNomeRelatorio(), parametros, relatorioDataSource, TarefaRelatorio.TIPO_PDF);
		}


		if(TIPO_RELATORIO_AMBOS.equals(comando)){
			List<byte[]> pdfsGerados = new ArrayList<byte[]>();
			byte[] novoPdf = null;

			// Analítico
			List<RelatorioLiquidosRecebiveisHelper> helpersAnalitico = Fachada.getInstancia()
							.consultarRegistrosDeLiquidosRecebiveisAnalitico(dataInicio, dataFim);

			List<RelatorioLiquidosRecebiveisHelper> helpersAnaliticoComTotais = preencherAnalitico(helpersAnalitico);

			relatorioDataSource = new RelatorioDataSource(helpersAnaliticoComTotais);
			novoPdf = gerarRelatorio(ConstantesRelatorios.RELATORIO_LIQUIDOS_RECEBIVEIS_ANALITICO, parametros, relatorioDataSource,
							TarefaRelatorio.TIPO_PDF);
			pdfsGerados.add(novoPdf);


			// Sintético
			Collection<RelatorioLiquidosRecebiveisHelper> helpersSintetico = Fachada.getInstancia()
							.consultarRegistrosDeLiquidosRecebiveisSintetico(dataInicio, dataFim);

			List<RelatorioLiquidosRecebiveisHelper> helpersSinteticoComTotais = preencherSintetico(helpersSintetico);

			relatorioDataSource = new RelatorioDataSource(helpersSinteticoComTotais);
			novoPdf = gerarRelatorio(ConstantesRelatorios.RELATORIO_LIQUIDOS_RECEBIVEIS_SINTETICO, parametros, relatorioDataSource,
							TarefaRelatorio.TIPO_PDF);
			pdfsGerados.add(novoPdf);

			try{
				retorno = this.concatenarPFDs(pdfsGerados);
			}catch(GeradorRelatorioException e){
				e.printStackTrace();
			}

		}
		
		return retorno;
	}

	private List<RelatorioLiquidosRecebiveisHelper> preencherAnalitico(Collection<RelatorioLiquidosRecebiveisHelper> helpersAnalitico){

		List<RelatorioLiquidosRecebiveisHelper> helpersAnaliticoComTotais = new ArrayList<RelatorioLiquidosRecebiveisHelper>();

		BigDecimal totalDiaPgtoAgua = BigDecimal.ZERO;
		BigDecimal totalDiaPgtoEsgoto = BigDecimal.ZERO;
		BigDecimal totalDiaPgtoServico = BigDecimal.ZERO;
		BigDecimal totalDiaPgtoConta = BigDecimal.ZERO;

		BigDecimal totalGeralPgtoAgua = BigDecimal.ZERO;
		BigDecimal totalGeralPgtoEsgoto = BigDecimal.ZERO;
		BigDecimal totalGeralPgtoServico = BigDecimal.ZERO;
		BigDecimal totalGeralPgtoConta = BigDecimal.ZERO;
		String diaAnterior = null;

		boolean addDataQuebra = true;
		if(helpersAnalitico != null && !helpersAnalitico.isEmpty()){
			for(RelatorioLiquidosRecebiveisHelper item : helpersAnalitico){

				if(Util.isNaoNuloBrancoZero(diaAnterior) && !diaAnterior.equals(item.getDataPagamento())){
					RelatorioLiquidosRecebiveisHelper totalDia = preencherHelper(totalDiaPgtoAgua, totalDiaPgtoEsgoto, totalDiaPgtoServico,
									totalDiaPgtoConta, TOTAL_DO_DIA + diaAnterior + " : ");

					helpersAnaliticoComTotais.add(totalDia);

					// Zerar Total do Dia
					totalDiaPgtoAgua = BigDecimal.ZERO;
					totalDiaPgtoEsgoto = BigDecimal.ZERO;
					totalDiaPgtoServico = BigDecimal.ZERO;
					totalDiaPgtoConta = BigDecimal.ZERO;
					addDataQuebra = true;
				}

				if(addDataQuebra){
					RelatorioLiquidosRecebiveisHelper itemData = new RelatorioLiquidosRecebiveisHelper();
					itemData.setDataPagamentoQuebra("DATA PAGTO: " + item.getDataPagamento());
					addDataQuebra = false;
					helpersAnaliticoComTotais.add(itemData);
				}
				// ADD na lista
				helpersAnaliticoComTotais.add(item);

				// TOTAL DIA
				totalDiaPgtoAgua = totalDiaPgtoAgua.add(Util.formatarMoedaRealparaBigDecimal(item.getValorAgua()));
				totalDiaPgtoEsgoto = totalDiaPgtoEsgoto.add(Util.formatarMoedaRealparaBigDecimal(item.getValorEsgoto()));
				totalDiaPgtoServico = totalDiaPgtoServico.add(Util.formatarMoedaRealparaBigDecimal(item.getValorServico()));
				totalDiaPgtoConta = totalDiaPgtoConta.add(Util.formatarMoedaRealparaBigDecimal(item.getValorConta()));

				// TOTAL GERAL
				totalGeralPgtoAgua = totalGeralPgtoAgua.add(Util.formatarMoedaRealparaBigDecimal(item.getValorAgua()));
				totalGeralPgtoEsgoto = totalGeralPgtoEsgoto.add(Util.formatarMoedaRealparaBigDecimal(item.getValorEsgoto()));
				totalGeralPgtoServico = totalGeralPgtoServico.add(Util.formatarMoedaRealparaBigDecimal(item.getValorServico()));
				totalGeralPgtoConta = totalGeralPgtoConta.add(Util.formatarMoedaRealparaBigDecimal(item.getValorConta()));

				// DIA ANTERIOR
				diaAnterior = item.getDataPagamento();
			}

			// Add total Ultimo dia e total geral!

			RelatorioLiquidosRecebiveisHelper totalUltimoDiaForaFor = preencherHelper(totalDiaPgtoAgua, totalDiaPgtoEsgoto,
							totalDiaPgtoServico, totalDiaPgtoConta, TOTAL_DO_DIA + diaAnterior + " : ");

			helpersAnaliticoComTotais.add(totalUltimoDiaForaFor);

			RelatorioLiquidosRecebiveisHelper totalGeral = preencherHelper(totalGeralPgtoAgua, totalGeralPgtoEsgoto, totalGeralPgtoServico,
							totalGeralPgtoConta, TOTAL_GERAL_PAGO);

			helpersAnaliticoComTotais.add(totalGeral);
		}


		return helpersAnaliticoComTotais;
	}

	private RelatorioLiquidosRecebiveisHelper preencherHelper(BigDecimal totalDiaPgtoAgua, BigDecimal totalDiaPgtoEsgoto,
					BigDecimal totalDiaPgtoServico, BigDecimal totalDiaPgtoConta, String nome){

		RelatorioLiquidosRecebiveisHelper totalDia = new RelatorioLiquidosRecebiveisHelper();
		totalDia.setNome(nome);
		totalDia.setQuantidadesContas(nome);
		totalDia.setValorAgua(Util.formataBigDecimal(totalDiaPgtoAgua, 2, false));
		totalDia.setValorEsgoto(Util.formataBigDecimal(totalDiaPgtoEsgoto, 2, false));
		totalDia.setValorServico(Util.formataBigDecimal(totalDiaPgtoServico, 2, false));
		totalDia.setValorConta(Util.formataBigDecimal(totalDiaPgtoConta, 2, false));
		return totalDia;
	}

	private List<RelatorioLiquidosRecebiveisHelper> preencherSintetico(Collection<RelatorioLiquidosRecebiveisHelper> helpersSintetico){

		List<RelatorioLiquidosRecebiveisHelper> helpersSinteticoComTotais = new ArrayList<RelatorioLiquidosRecebiveisHelper>();

		BigDecimal totalGeralPgtoAgua = BigDecimal.ZERO;
		BigDecimal totalGeralPgtoEsgoto = BigDecimal.ZERO;
		BigDecimal totalGeralPgtoServico = BigDecimal.ZERO;
		BigDecimal totalGeralPgtoConta = BigDecimal.ZERO;

		if(helpersSintetico != null && !helpersSintetico.isEmpty()){
			for(RelatorioLiquidosRecebiveisHelper item : helpersSintetico){

				// ADD na lista
				helpersSinteticoComTotais.add(item);

				// TOTAL GERAL
				totalGeralPgtoAgua = totalGeralPgtoAgua.add(Util.formatarMoedaRealparaBigDecimal(item.getValorAgua()));
				totalGeralPgtoEsgoto = totalGeralPgtoEsgoto.add(Util.formatarMoedaRealparaBigDecimal(item.getValorEsgoto()));
				totalGeralPgtoServico = totalGeralPgtoServico.add(Util.formatarMoedaRealparaBigDecimal(item.getValorServico()));
				totalGeralPgtoConta = totalGeralPgtoConta.add(Util.formatarMoedaRealparaBigDecimal(item.getValorConta()));

			}

			RelatorioLiquidosRecebiveisHelper totalGeral = preencherHelper(totalGeralPgtoAgua, totalGeralPgtoEsgoto, totalGeralPgtoServico,
							totalGeralPgtoConta, TOTAL_GERAL_PAGO);

			helpersSinteticoComTotais.add(totalGeral);
		}

		return helpersSinteticoComTotais;
	}

	@Override
	public int calcularTotalRegistrosRelatorio(){

		Date dataInicio = (Date) this.getParametro("dataInicio");
		Date dataFim = (Date) this.getParametro("dataFim");

		int retorno = Fachada.getInstancia().consultarQuantidadeRegistrosDeLiquidosRecebiveis(dataInicio, dataFim);

		return retorno;
	}

	@Override
	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioLiquidosRecebiveisAnalitico", this);
	}

	private byte[] concatenarPFDs(List<byte[]> pdfsGerados) throws GeradorRelatorioException{

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
			throw new GeradorRelatorioException(e.getMessage(), e);
		}

		novoPDF = baos.toByteArray();

		return novoPDF;
	}
}
