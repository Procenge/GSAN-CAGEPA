
package gcom.relatorio.ordemservico;

import gcom.atendimentopublico.ordemservico.OrdemServico;
import gcom.atendimentopublico.ordemservico.OrdemServicoLayout;
import gcom.atendimentopublico.ordemservico.ServicoTipo;
import gcom.batch.Relatorio;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.ParametroTarefa;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.ZipUtil;
import gcom.util.agendadortarefas.AgendadorTarefas;
import gcom.util.parametrizacao.cadastro.ParametroCadastro;
import gcom.util.parametrizacao.ordemservico.ParametroOrdemServico;

import java.io.*;
import java.util.*;
import java.util.zip.ZipOutputStream;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

import com.lowagie.text.Document;
import com.lowagie.text.pdf.PRAcroForm;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;

/**
 * Essa classe deve ser utilizada para gerar os relatórios de ordem de serviço
 * 
 * @author gmatos
 */
public class GeradorRelatorioOrdemServico
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	private static GeradorRelatorioOrdemServico instancia = new GeradorRelatorioOrdemServico();

	public GeradorRelatorioOrdemServico(Usuario usuario, String nomeRelatorio) {

		super(usuario, nomeRelatorio);
	}

	public GeradorRelatorioOrdemServico(Usuario usuario) {

		super(usuario, "");
	}

	public GeradorRelatorioOrdemServico() {

		super(null, "");
	}

	/**
	 * Método reponsável por obter uma instância do GeradorRelatorioOrdemServico.
	 * 
	 * @return Um GeradorRelatorioOrdemServico
	 */
	public static GeradorRelatorioOrdemServico getInstancia(){

		return instancia;
	}

	/**
	 * Método responsável por gerar os relatórios de ordem de servico
	 * 
	 * @param listaOs
	 *            A lista de OS
	 * @return Um PDF.
	 * @throws GeradorRelatorioOrdemServicoException
	 *             Casso ocorra algum erro na geração do relatório
	 */
	public byte[] gerarRelatorioOrdemServico(List<OrdemServico> listaOs) throws GeradorRelatorioOrdemServicoException{

		String arquivoLayout = null;
		String nomeClasse = null;
		List<DadosRelatorioOrdemServico> dados = null;
		byte[] novoPdf = null;
		byte[] novoPdfComCartaHidrometro = null;
		byte[] retorno = null;
		List<byte[]> pdfsGerados = new ArrayList<byte[]>();
		List<byte[]> pdfsGeradosComCartaHidrometro = new ArrayList<byte[]>();
		OrdemServico os = null;

		Map<Integer, Collection<OrdemServico>> mapLayoutOrdens = new TreeMap<Integer, Collection<OrdemServico>>();

		if(listaOs != null && !listaOs.isEmpty()){
			SistemaParametro sistemaParametro = Fachada.getInstancia().pesquisarParametrosDoSistema();

			if(sistemaParametro != null){
				addParametro("imagem", sistemaParametro.getImagemRelatorio());
				addParametro("P_NM_ESTADO", sistemaParametro.getNomeEstado());
				addParametro("P_NM_EMPRESA", sistemaParametro.getNomeEmpresa());
				addParametro("P_NN_FONE", sistemaParametro.getNumeroTelefone());
			}

			Iterator<OrdemServico> itOrdemServico = listaOs.iterator();
			while(itOrdemServico.hasNext()){
				OrdemServico ordemS = itOrdemServico.next();
				this.addValorMapLayoutOrdens(mapLayoutOrdens, ordemS.getServicoTipo().getOrdemServicoLayout().getId(), ordemS);
			}

			Set chaveLayouts = mapLayoutOrdens.keySet();
			Iterator iterMap = chaveLayouts.iterator();

			// Verifica se a chamada está vindo da tela de Emitir Ordem de Serviço Seletiva
			Boolean telaOSSeletiva = Boolean.FALSE;
			if(!Util.isVazioOuBranco(getParametro("telaOSSeletiva"))){
				telaOSSeletiva = (Boolean) getParametro("telaOSSeletiva");
			}

			// PDF = 1 e TXT = 2
			String tipoArquivo = "";
			try{
				tipoArquivo = ParametroOrdemServico.P_COD_TIPO_ARQUIVO_EMISSAO_OS_SELETIVA.executar();
			}catch(ControladorException e){
				e.printStackTrace();
				throw new GeradorRelatorioOrdemServicoException(e.getMessage(), e);
			}

			while(iterMap.hasNext()){
				// Para cada Layout
				Integer chaveLayout = (Integer) iterMap.next();
				Collection<OrdemServico> listaOrdens = mapLayoutOrdens.get(chaveLayout);

				OrdemServico ordem = (OrdemServico) Util.retonarObjetoDeColecao(listaOrdens);
				ServicoTipo servicoTipo = ordem.getServicoTipo();
				OrdemServicoLayout ordemServicoLayout = servicoTipo.getOrdemServicoLayout();
				Integer indicadorDoisPorPagina = ordemServicoLayout.getIndicadorDoisPorPagina();

				// PDF = 1 e TXT = 2
				if(tipoArquivo.equals(ConstantesSistema.PDF)){
					if(indicadorDoisPorPagina.intValue() == ConstantesSistema.SIM.intValue()){
						nomeClasse = ordemServicoLayout.getNomeClasse();
						arquivoLayout = ordemServicoLayout.getNomeRelatorio();
						dados = this.getGeradorDados(nomeClasse, listaOrdens);
						novoPdf = this.gerarRelatorioPDF(arquivoLayout, this.getParametros(), dados);
						pdfsGerados.add(novoPdf);
						retorno = this.concatenarPFDs(pdfsGerados);
					}else{
						// [SB0100] - Verificar Emissão Carta
						// 1. Caso este caso de uso tenha sido chamado pelo caso de uso [UC0713 -
						// Emitir Ordem de Serviço Seletiva]:
						if(telaOSSeletiva){
							// 1.1. Caso o tipo de serviço da ordem de serviço indique a emissão de
							// carta anexa à ordem de serviço
							if(!Util.isVazioOuBranco(ordemServicoLayout.getNomeRelatorioCarta())){
								for(Iterator<OrdemServico> iterator = listaOrdens.iterator(); iterator.hasNext();){
									os = (OrdemServico) iterator.next();

									pdfsGeradosComCartaHidrometro = new ArrayList<byte[]>();

									nomeClasse = os.getServicoTipo().getOrdemServicoLayout().getNomeClasseCarta();
									arquivoLayout = os.getServicoTipo().getOrdemServicoLayout().getNomeRelatorioCarta();
									dados = this.getGeradorDados(nomeClasse, os);
									novoPdf = this.gerarRelatorioPDF(arquivoLayout, this.getParametros(), dados);

									pdfsGeradosComCartaHidrometro.add(novoPdf);

									nomeClasse = os.getServicoTipo().getOrdemServicoLayout().getNomeClasse();
									arquivoLayout = os.getServicoTipo().getOrdemServicoLayout().getNomeRelatorio();
									dados = this.getGeradorDados(nomeClasse, os);
									novoPdf = this.gerarRelatorioPDF(arquivoLayout, this.getParametros(), dados);

									pdfsGeradosComCartaHidrometro.add(novoPdf);

									novoPdfComCartaHidrometro = this.concatenarPFDs(pdfsGeradosComCartaHidrometro);

									pdfsGerados.add(novoPdfComCartaHidrometro);
								}

								retorno = this.concatenarPFDs(pdfsGerados);
							}else{
								for(Iterator<OrdemServico> iterator = listaOrdens.iterator(); iterator.hasNext();){
									os = (OrdemServico) iterator.next();

									nomeClasse = os.getServicoTipo().getOrdemServicoLayout().getNomeClasse();
									arquivoLayout = os.getServicoTipo().getOrdemServicoLayout().getNomeRelatorio();
									dados = this.getGeradorDados(nomeClasse, os);
									novoPdf = this.gerarRelatorioPDF(arquivoLayout, this.getParametros(), dados);
									pdfsGerados.add(novoPdf);
								}

								retorno = this.concatenarPFDs(pdfsGerados);
							}
						}else{
							for(Iterator<OrdemServico> iterator = listaOrdens.iterator(); iterator.hasNext();){
								os = (OrdemServico) iterator.next();

								nomeClasse = os.getServicoTipo().getOrdemServicoLayout().getNomeClasse();
								arquivoLayout = os.getServicoTipo().getOrdemServicoLayout().getNomeRelatorio();
								dados = this.getGeradorDados(nomeClasse, os);
								novoPdf = this.gerarRelatorioPDF(arquivoLayout, this.getParametros(), dados);
								pdfsGerados.add(novoPdf);
							}

							retorno = this.concatenarPFDs(pdfsGerados);
						}
					}
				}

			}
		}

		return retorno;
	}

	public byte[] gerarTxtOrdemServico(List<OrdemServico> listaOs) throws GeradorRelatorioOrdemServicoException{

		byte[] retorno = null;

		Map<Integer, Collection<OrdemServico>> mapLayoutOrdens = new TreeMap<Integer, Collection<OrdemServico>>();

		if(listaOs != null && !listaOs.isEmpty()){

			Iterator<OrdemServico> itOrdemServico = listaOs.iterator();
			while(itOrdemServico.hasNext()){
				OrdemServico ordemS = itOrdemServico.next();
				this.addValorMapLayoutOrdens(mapLayoutOrdens, ordemS.getServicoTipo().getOrdemServicoLayout().getId(), ordemS);
			}

			Set chaveLayouts = mapLayoutOrdens.keySet();
			Iterator iterMap = chaveLayouts.iterator();

			while(iterMap.hasNext()){
				// Para cada Layout
				Integer chaveLayout = (Integer) iterMap.next();
				Collection<OrdemServico> listaOrdens = mapLayoutOrdens.get(chaveLayout);

				OrdemServico ordem = (OrdemServico) Util.retonarObjetoDeColecao(listaOrdens);
				ServicoTipo servicoTipo = ordem.getServicoTipo();
				OrdemServicoLayout ordemServicoLayout = servicoTipo.getOrdemServicoLayout();

				// if(tipoArquivo.equals(ConstantesSistema.TXT)){
				String nomeMetodoGeracaoArquivoTxt = ordemServicoLayout.getNomeMetodoGeracaoArquivoTxt();

				// Para cada layout vai ser gerado um arquivo TXT de ordem de serviço! onde deverá
				// ser ordenado caso tenha parâmetro.
				StringBuilder arquivoTxt = Fachada.getInstancia().imprimirOrdemServicoTxt(listaOrdens, nomeMetodoGeracaoArquivoTxt);

				retorno = this.criarArquivoTxtOrdemServicoSeletiva(servicoTipo, arquivoTxt);
				// }
			}
		}

		return retorno;
	}

	/**
	 * Método responsável por gerar os relatórios de ordem de servico
	 * 
	 * @param listaOs
	 *            A lista de OS
	 * @return Um PDF.
	 * @throws GeradorRelatorioOrdemServicoException
	 *             Casso ocorra algum erro na geração do relatório
	 */
	public byte[] gerarRelatorioOrdemServico(Collection<Integer> idsOrdens) throws GeradorRelatorioOrdemServicoException{

		String arquivoLayout = null;
		String nomeClasse = null;
		List<DadosRelatorioOrdemServico> dados = null;
		byte[] novoPdf = null;
		byte[] novoPdfComCartaHidrometro = null;
		byte[] retorno = null;
		List<byte[]> pdfsGerados = new ArrayList<byte[]>();
		List<byte[]> pdfsGeradosComCartaHidrometro = new ArrayList<byte[]>();
		OrdemServico os = null;

		List<OrdemServico> listaOrdemServico = (List<OrdemServico>) Fachada.getInstancia().pesquisarOrdensServicos(
						(List<Integer>) idsOrdens);

		Map<Integer, Collection<OrdemServico>> mapLayoutOrdens = new TreeMap<Integer, Collection<OrdemServico>>();

		if(listaOrdemServico != null && !listaOrdemServico.isEmpty()){
			SistemaParametro sistemaParametro = Fachada.getInstancia().pesquisarParametrosDoSistema();

			if(sistemaParametro != null){
				addParametro("imagem", sistemaParametro.getImagemRelatorio());
				addParametro("nomeEmpresa", sistemaParametro.getNomeAbreviadoEmpresa());
				addParametro("nomeCompletoEmpresa", sistemaParametro.getNomeEmpresa());
				addParametro("telefoneEmpresa", sistemaParametro.getNumeroTelefone());
				addParametro("municipioEmpresa", sistemaParametro.getBairro().getMunicipio().getNome());
				addParametro("P_NM_ESTADO", sistemaParametro.getBairro().getMunicipio().getUnidadeFederacao().getDescricao());
				addParametro("P_NM_EMPRESA", sistemaParametro.getNomeEmpresa());
				addParametro("P_NN_FONE", sistemaParametro.getNumeroTelefone());

				try{
					addParametro("siteEmpresa", ParametroCadastro.P_SITE_EMPRESA.executar());
				}catch(ControladorException e){
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			Iterator<OrdemServico> itOrdemServico = listaOrdemServico.iterator();
			while(itOrdemServico.hasNext()){
				OrdemServico ordemS = itOrdemServico.next();
				this.addValorMapLayoutOrdens(mapLayoutOrdens, ordemS.getServicoTipo().getOrdemServicoLayout().getId(), ordemS);
			}

			// PDF = 1 e TXT = 2
			String tipoArquivo = "";
			Integer tipoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

			// Verifica se a chamada está vindo da tela de Emitir Ordem de Serviço Seletiva
			Boolean telaOSSeletiva = Boolean.FALSE;
			if(!Util.isVazioOuBranco(getParametro("telaOSSeletiva"))){
				telaOSSeletiva = (Boolean) getParametro("telaOSSeletiva");
			}

			System.out.println("tipoRelatorio 3= " + tipoRelatorio);

			try{
				// if(tipoRelatorio != null && tipoRelatorio.intValue() !=
				// TarefaRelatorio.TIPO_PDF){
				tipoArquivo = ParametroOrdemServico.P_COD_TIPO_ARQUIVO_EMISSAO_OS_SELETIVA.executar();
				// }else{
				// / tipoArquivo = "1";
				// }
			}catch(ControladorException ex){
				throw new GeradorRelatorioOrdemServicoException(ex.getMessage(), ex);
			}

			Set chaveLayouts = mapLayoutOrdens.keySet();
			Iterator iterMap = chaveLayouts.iterator();

			while(iterMap.hasNext()){
				// Para cada Layout

				Integer chaveLayout = (Integer) iterMap.next();
				Collection<OrdemServico> listaOrdens = mapLayoutOrdens.get(chaveLayout);

				OrdemServico ordem = (OrdemServico) Util.retonarObjetoDeColecao(listaOrdens);
				ServicoTipo servicoTipo = ordem.getServicoTipo();
				OrdemServicoLayout ordemServicoLayout = servicoTipo.getOrdemServicoLayout();
				Integer indicadorDoisPorPagina = ordemServicoLayout.getIndicadorDoisPorPagina();

				// PDF = 1 e TXT = 2
				if(tipoArquivo.equals(ConstantesSistema.PDF)){
					if(indicadorDoisPorPagina.intValue() == ConstantesSistema.SIM.intValue()){
						nomeClasse = ordemServicoLayout.getNomeClasse();
						arquivoLayout = ordemServicoLayout.getNomeRelatorio();
						dados = this.getGeradorDados(nomeClasse, listaOrdens);
						novoPdf = this.gerarRelatorioPDF(arquivoLayout, this.getParametros(), dados);
						pdfsGerados.add(novoPdf);
						retorno = this.concatenarPFDs(pdfsGerados);
					}else{
						// [SB0100] - Verificar Emissão Carta
						// 1. Caso este caso de uso tenha sido chamado pelo caso de uso [UC0713 -
						// Emitir Ordem de Serviço Seletiva]:
						if(telaOSSeletiva){
							// 1.1. Caso o tipo de serviço da ordem de serviço indique a emissão de
							// carta anexa à ordem de serviço
							if(!Util.isVazioOuBranco(ordemServicoLayout.getNomeRelatorioCarta())){
								for(Iterator<OrdemServico> iterator = listaOrdens.iterator(); iterator.hasNext();){
									os = (OrdemServico) iterator.next();

									pdfsGeradosComCartaHidrometro = new ArrayList<byte[]>();

									nomeClasse = os.getServicoTipo().getOrdemServicoLayout().getNomeClasseCarta();
									arquivoLayout = os.getServicoTipo().getOrdemServicoLayout().getNomeRelatorioCarta();
									dados = this.getGeradorDados(nomeClasse, os);
									novoPdf = this.gerarRelatorioPDF(arquivoLayout, this.getParametros(), dados);

									pdfsGeradosComCartaHidrometro.add(novoPdf);

									nomeClasse = os.getServicoTipo().getOrdemServicoLayout().getNomeClasse();
									arquivoLayout = os.getServicoTipo().getOrdemServicoLayout().getNomeRelatorio();
									dados = this.getGeradorDados(nomeClasse, os);
									novoPdf = this.gerarRelatorioPDF(arquivoLayout, this.getParametros(), dados);

									pdfsGeradosComCartaHidrometro.add(novoPdf);

									novoPdfComCartaHidrometro = this.concatenarPFDs(pdfsGeradosComCartaHidrometro);

									pdfsGerados.add(novoPdfComCartaHidrometro);
								}

								retorno = this.concatenarPFDs(pdfsGerados);
							}else{
								for(Iterator<OrdemServico> iterator = listaOrdens.iterator(); iterator.hasNext();){
									os = (OrdemServico) iterator.next();

									nomeClasse = os.getServicoTipo().getOrdemServicoLayout().getNomeClasse();
									arquivoLayout = os.getServicoTipo().getOrdemServicoLayout().getNomeRelatorio();
									dados = this.getGeradorDados(nomeClasse, os);
									novoPdf = this.gerarRelatorioPDF(arquivoLayout, this.getParametros(), dados);
									pdfsGerados.add(novoPdf);
								}

								retorno = this.concatenarPFDs(pdfsGerados);
							}
						}else{
							for(Iterator<OrdemServico> iterator = listaOrdens.iterator(); iterator.hasNext();){
								os = (OrdemServico) iterator.next();

								nomeClasse = os.getServicoTipo().getOrdemServicoLayout().getNomeClasse();
								arquivoLayout = os.getServicoTipo().getOrdemServicoLayout().getNomeRelatorio();
								dados = this.getGeradorDados(nomeClasse, os);
								novoPdf = this.gerarRelatorioPDF(arquivoLayout, this.getParametros(), dados);
								pdfsGerados.add(novoPdf);
							}

							retorno = this.concatenarPFDs(pdfsGerados);
						}
					}
				}else if(tipoArquivo.equals(ConstantesSistema.TXT)){
					String nomeMetodoGeracaoArquivoTxt = ordemServicoLayout.getNomeMetodoGeracaoArquivoTxt();

					StringBuilder arquivoTxt = Fachada.getInstancia().imprimirOrdemServicoTxt(listaOrdens, nomeMetodoGeracaoArquivoTxt);

					retorno = this.criarArquivoTxtOrdemServicoSeletiva(servicoTipo, arquivoTxt);
				}
			}

		}

		return retorno;
	}

	private List<DadosRelatorioOrdemServico> getGeradorDados(String nomeClasse, OrdemServico os)
					throws GeradorRelatorioOrdemServicoException{

		List<DadosRelatorioOrdemServico> dados = null;
		try{
			Class clazz = Class.forName(nomeClasse);
			GeradorDadosRelatorioOrdemServico geradorDados = (GeradorDadosRelatorioOrdemServico) clazz.newInstance();
			dados = geradorDados.gerarDados(os);

			if(geradorDados.getParametroTarefa() != null && !geradorDados.getParametroTarefa().isEmpty()){
				adicionarParametrosTarefa(geradorDados.getParametroTarefa());
			}

		}catch(ClassNotFoundException e){
			throw new GeradorRelatorioOrdemServicoException(e.getMessage(), e);
		}catch(SecurityException e){
			throw new GeradorRelatorioOrdemServicoException(e.getMessage(), e);
		}catch(InstantiationException e){
			throw new GeradorRelatorioOrdemServicoException(e.getMessage(), e);
		}catch(IllegalAccessException e){
			throw new GeradorRelatorioOrdemServicoException(e.getMessage(), e);
		}

		return dados;
	}

	/**
	 * 2 OS por páginas
	 * 
	 * @author isilva
	 * @param nomeClasse
	 * @param ordensServicos
	 * @return
	 * @throws GeradorRelatorioOrdemServicoException
	 */
	private List<DadosRelatorioOrdemServico> getGeradorDados(String nomeClasse, Collection<OrdemServico> ordensServicos)
					throws GeradorRelatorioOrdemServicoException{

		List<DadosRelatorioOrdemServico> dados = null;
		try{
			Class clazz = Class.forName(nomeClasse);
			GeradorDadosRelatorioOrdemServico geradorDados = (GeradorDadosRelatorioOrdemServico) clazz.newInstance();
			dados = geradorDados.gerarDados(ordensServicos);

			if(geradorDados.getParametroTarefa() != null && !geradorDados.getParametroTarefa().isEmpty()){
				adicionarParametrosTarefa(geradorDados.getParametroTarefa());
			}

		}catch(ClassNotFoundException e){
			throw new GeradorRelatorioOrdemServicoException(e.getMessage(), e);
		}catch(SecurityException e){
			throw new GeradorRelatorioOrdemServicoException(e.getMessage(), e);
		}catch(InstantiationException e){
			throw new GeradorRelatorioOrdemServicoException(e.getMessage(), e);
		}catch(IllegalAccessException e){
			throw new GeradorRelatorioOrdemServicoException(e.getMessage(), e);
		}

		return dados;
	}

	private byte[] gerarRelatorioPDF(String arquivoLayout, Map<String, Object> parametros, Collection<DadosRelatorioOrdemServico> dados)
					throws GeradorRelatorioOrdemServicoException{

		byte[] bytes = null;

		try{
			JasperReport relatorioJasper = (JasperReport) JRLoader.loadObject(this.getClass().getClassLoader().getResourceAsStream(
							arquivoLayout));
			bytes = JasperRunManager.runReportToPdf(relatorioJasper, parametros, new JRBeanCollectionDataSource(dados));
		}catch(JRException e){
			throw new GeradorRelatorioOrdemServicoException(e.getMessage(), e);
		}

		return bytes;
	}

	private byte[] concatenarPFDs(List<byte[]> pdfsGerados) throws GeradorRelatorioOrdemServicoException{

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
			throw new GeradorRelatorioOrdemServicoException(e.getMessage(), e);
		}

		novoPDF = baos.toByteArray();

		return novoPDF;
	}

	/**
	 * @author isilva
	 * @param mapa
	 * @param chave
	 * @param ordemServico
	 */
	private void addValorMapLayoutOrdens(Map<Integer, Collection<OrdemServico>> mapa, Integer chave, OrdemServico ordemServico){

		Collection<OrdemServico> ordensServicos = null;

		if(mapa.get(chave) != null){
			// Existe
			ordensServicos = mapa.get(chave);
			ordensServicos.add(ordemServico);
			mapa.remove(chave);
		}else{
			// Não existe
			ordensServicos = new ArrayList<OrdemServico>();
			ordensServicos.add(ordemServico);
		}

		mapa.put(chave, ordensServicos);
	}

	@SuppressWarnings("unchecked")
	@Override
	public int calcularTotalRegistrosRelatorio(){

		return ((Collection<Integer>) getParametro("idsOrdemServico")).size();
	}

	@Override
	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("GeradorRelatorioOrdemServico", this);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object executar() throws TarefaException{

		byte[] retorno = null;

		try{
			// PDF = 1 e TXT = 2
			String tipoArquivo = ParametroOrdemServico.P_COD_TIPO_ARQUIVO_EMISSAO_OS_SELETIVA.executar();

			if(tipoArquivo.equals(ConstantesSistema.PDF)){
				if(getParametro("listaOrdemServico") != null){
					retorno = this.gerarRelatorioOrdemServico((List<OrdemServico>) getParametro("listaOrdemServico"));
				}else if(getParametro("idsOrdemServico") != null){
					retorno = this.gerarRelatorioOrdemServico((Collection<Integer>) getParametro("idsOrdemServico"));
				}
			}else if(tipoArquivo.equals(ConstantesSistema.TXT)){
				if(getParametro("listaOrdemServico") != null){
					retorno = this.gerarArquivoTxtOrdemServico((List<OrdemServico>) getParametro("listaOrdemServico"));
				}else if(getParametro("idsOrdemServico") != null){
					retorno = this.gerarArquivoTxtOrdemServico((Collection<Integer>) getParametro("idsOrdemServico"));
				}
			}
			persistirRelatorioConcluido(retorno, Relatorio.RELATORIO_ORDEM_SERVICO_COBRANCA, getIdFuncionalidadeIniciada(), null);
		}catch(GeradorRelatorioOrdemServicoException e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relatório no sistema", e);
		}catch(ControladorException e){
			e.printStackTrace();
		}

		return retorno;
	}

	public void adicionarParametrosTarefa(Set parametroTarefaRelatorio){

		if(parametroTarefaRelatorio != null && !parametroTarefaRelatorio.isEmpty()){

			Iterator it = parametroTarefaRelatorio.iterator();

			while(it.hasNext()){
				ParametroTarefa parametroTarefa = (ParametroTarefa) it.next();
				addParametro(parametroTarefa.getNome(), parametroTarefa.getValor());
			}
		}
	}

	private Map<String, Object> getParametros(){

		Iterator it = this.getParametroTarefa().iterator();

		Map<String, Object> parametros = null;

		while(it.hasNext()){
			ParametroTarefa parametroTarefa = (ParametroTarefa) it.next();

			if(parametros == null || parametros.isEmpty()){
				parametros = new HashMap<String, Object>();
			}

			parametros.put(parametroTarefa.getNome(), parametroTarefa.getValor());
		}

		return parametros;
	}

	private byte[] gerarArquivoTxtOrdemServico(List<OrdemServico> listaOrdemServico) throws TarefaException{

		byte[] retorno = null;

		if(!Util.isVazioOrNulo(listaOrdemServico)){
			Map<Integer, Collection<OrdemServico>> mapLayoutOrdens = new TreeMap<Integer, Collection<OrdemServico>>();

			ServicoTipo servicoTipo = null;
			OrdemServicoLayout ordemServicoLayout = null;
			Integer idOrdemServicoLayout = null;

			for(OrdemServico ordemServico : listaOrdemServico){
				servicoTipo = ordemServico.getServicoTipo();
				ordemServicoLayout = servicoTipo.getOrdemServicoLayout();
				idOrdemServicoLayout = ordemServicoLayout.getId();

				this.addValorMapLayoutOrdens(mapLayoutOrdens, idOrdemServicoLayout, ordemServico);
			}

			Collection<OrdemServico> colecaoOrdemServico = null;
			OrdemServico ordemServicoAux = null;
			String nomeMetodoGeracaoArquivoTxt = null;
			StringBuilder arquivoTxt = null;

			for(Integer chaveLayout : mapLayoutOrdens.keySet()){
				colecaoOrdemServico = mapLayoutOrdens.get(chaveLayout);

				ordemServicoAux = (OrdemServico) Util.retonarObjetoDeColecao(colecaoOrdemServico);

				servicoTipo = ordemServicoAux.getServicoTipo();
				ordemServicoLayout = servicoTipo.getOrdemServicoLayout();
				nomeMetodoGeracaoArquivoTxt = ordemServicoLayout.getNomeMetodoGeracaoArquivoTxt();

				arquivoTxt = Fachada.getInstancia().imprimirOrdemServicoTxt(colecaoOrdemServico, nomeMetodoGeracaoArquivoTxt);

				retorno = this.criarArquivoTxtOrdemServicoSeletiva(servicoTipo, arquivoTxt);
			}
		}

		return retorno;
	}

	private byte[] gerarArquivoTxtOrdemServico(Collection<Integer> idsOrdemServico) throws TarefaException{

		List<OrdemServico> listaOrdemServico = (List<OrdemServico>) Fachada.getInstancia().pesquisarOrdensServicos(
						(List<Integer>) idsOrdemServico);

		return this.gerarArquivoTxtOrdemServico(listaOrdemServico);
	}

	private byte[] criarArquivoTxtOrdemServicoSeletiva(ServicoTipo servicoTipo, StringBuilder arquivoTxt){

		byte[] retorno = null;

		// Nome do arquivo
		String descricaoServicoTipo = servicoTipo.getDescricao();
		descricaoServicoTipo = descricaoServicoTipo.replace(' ', '_');

		String dataCorrente = Util.formatarDataSemBarraDDMMAAAA(new Date());
		String nomeArquivo = "OS_SELETIVA_" + descricaoServicoTipo + "_" + dataCorrente + ".TXT";
		nomeArquivo = nomeArquivo.replace('/', '_');

		try{
			retorno = this.getBytesFromFileZip(arquivoTxt, nomeArquivo);
		}catch(IOException ex){
			throw new TarefaException(ex.getMessage(), ex);
		}
		return retorno;
	}

	private byte[] getBytesFromFileZip(StringBuilder sb, String nomeArquivo) throws IOException{

		byte[] retorno = null;
		ZipOutputStream zos = null;
		BufferedWriter out = null;
		File leituraTipo = new File(nomeArquivo);
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
		// compactado.delete();

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
