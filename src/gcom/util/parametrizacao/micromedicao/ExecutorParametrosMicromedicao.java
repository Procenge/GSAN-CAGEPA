
package gcom.util.parametrizacao.micromedicao;

import gcom.arrecadacao.ControladorArrecadacaoLocal;
import gcom.arrecadacao.ControladorArrecadacaoLocalHome;
import gcom.arrecadacao.PagamentoTipo;
import gcom.arrecadacao.debitoautomatico.DebitoAutomatico;
import gcom.atendimentopublico.ligacaoagua.LigacaoAgua;
import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgoto;
import gcom.atendimentopublico.ordemservico.ServicoTipo;
import gcom.batch.ControladorBatchLocal;
import gcom.batch.ControladorBatchLocalHome;
import gcom.cadastro.*;
import gcom.cadastro.cliente.*;
import gcom.cadastro.endereco.ControladorEnderecoLocal;
import gcom.cadastro.endereco.ControladorEnderecoLocalHome;
import gcom.cadastro.imovel.*;
import gcom.cadastro.localidade.IRepositorioLocalidade;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.RepositorioLocalidadeHBM;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cobranca.*;
import gcom.cobranca.bean.CalcularAcrescimoPorImpontualidadeHelper;
import gcom.faturamento.*;
import gcom.faturamento.consumofaixaareacategoria.ConsumoFaixaAreaCategoria;
import gcom.faturamento.consumofaixaareacategoria.FiltroConsumoFaixaAreaCategoria;
import gcom.faturamento.conta.Conta;
import gcom.faturamento.conta.ContaMotivoRevisao;
import gcom.faturamento.debito.DebitoTipo;
import gcom.micromedicao.*;
import gcom.micromedicao.bean.DebitoAnteriorHelper;
import gcom.micromedicao.bean.HidrometroRelatorioOSHelper;
import gcom.micromedicao.consumo.*;
import gcom.micromedicao.hidrometro.Hidrometro;
import gcom.micromedicao.hidrometro.HidrometroLocalInstalacao;
import gcom.micromedicao.leitura.LeituraAnormalidade;
import gcom.micromedicao.leitura.LeituraSituacao;
import gcom.micromedicao.medicao.FiltroMedicaoHistorico;
import gcom.micromedicao.medicao.FiltroMedicaoTipo;
import gcom.micromedicao.medicao.MedicaoHistorico;
import gcom.micromedicao.medicao.MedicaoTipo;
import gcom.relatorio.faturamento.RelatorioDadosTabelasFaturamentoImediato;
import gcom.relatorio.faturamento.RelatorioOcorrenciaGeracaoPreFatResumo;
import gcom.relatorio.faturamento.RelatorioOcorrenciaGeracaoPreFatResumoHelper;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.*;
import gcom.util.filtro.ComparacaoTextoCompleto;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.ExecutorParametro;
import gcom.util.parametrizacao.cobranca.ParametroCobranca;
import gcom.util.parametrizacao.faturamento.ParametroFaturamento;

import java.io.*;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.ejb.CreateException;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.comparators.ComparatorChain;
import org.apache.log4j.Logger;

import br.com.procenge.parametrosistema.api.ParametroSistema;

public class ExecutorParametrosMicromedicao
				implements ExecutorParametro {

	private static Logger LOGGER = Logger.getLogger(ExecutorParametrosMicromedicao.class);

	private static final ExecutorParametrosMicromedicao instancia = new ExecutorParametrosMicromedicao();

	protected IRepositorioFaturamento repositorioFaturamento;

	protected IRepositorioMicromedicao repositorioMicromedicao;

	protected IRepositorioCadastro repositorioCadastro;

	protected IRepositorioImovel repositorioImovel;

	protected IRepositorioClienteImovel repositorioClienteImovel;

	protected IRepositorioCliente repositorioCliente;

	protected IRepositorioLocalidade repositorioLocalidade;

	private ExecutorParametrosMicromedicao() {

	}

	public static ExecutorParametrosMicromedicao getInstancia(){

		return instancia;
	}

	public void ejbCreate() throws CreateException{

		repositorioFaturamento = RepositorioFaturamentoHBM.getInstancia();
		repositorioMicromedicao = RepositorioMicromedicaoHBM.getInstancia();
		repositorioCadastro = RepositorioCadastroHBM.getInstancia();
		repositorioImovel = RepositorioImovelHBM.getInstancia();
		repositorioClienteImovel = RepositorioClienteImovelHBM.getInstancia();
		repositorioCliente = RepositorioClienteHBM.getInstancia();
		repositorioLocalidade = RepositorioLocalidadeHBM.getInstancia();
	}

	protected ControladorArrecadacaoLocal getControladorArrecadacao(){

		ControladorArrecadacaoLocalHome localHome = null;
		ControladorArrecadacaoLocal local = null;

		// pega a instância do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorArrecadacaoLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_ARRECADACAO_SEJB);

			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}
	}

	protected ControladorEnderecoLocal getControladorEndereco(){

		ControladorEnderecoLocalHome localHome = null;
		ControladorEnderecoLocal local = null;

		ServiceLocator locator = null;

		try{

			locator = ServiceLocator.getInstancia();
			localHome = (ControladorEnderecoLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_ENDERECO_SEJB);
			local = localHome.create();

			return local;
		}catch(CreateException e){

			throw new SistemaException(e);
		}catch(ServiceLocatorException e){

			throw new SistemaException(e);
		}
	}

	protected ControladorCadastroLocal getControladorCadastro(){

		ControladorCadastroLocalHome localHome = null;
		ControladorCadastroLocal local = null;
		ServiceLocator locator = null;

		try{

			locator = ServiceLocator.getInstancia();
			localHome = (ControladorCadastroLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_CADASTRO_SEJB);
			local = localHome.create();

			return local;
		}catch(CreateException e){

			throw new SistemaException(e);
		}catch(ServiceLocatorException e){

			throw new SistemaException(e);
		}
	}

	protected ControladorBatchLocal getControladorBatch(){

		ControladorBatchLocalHome localHome = null;
		ControladorBatchLocal local = null;
		ServiceLocator locator = null;

		try{

			locator = ServiceLocator.getInstancia();
			localHome = (ControladorBatchLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_BATCH_SEJB);
			local = localHome.create();

			return local;
		}catch(CreateException e){

			throw new SistemaException(e);
		}catch(ServiceLocatorException e){

			throw new SistemaException(e);
		}
	}

	protected ControladorMicromedicaoLocal getControladorMicromedicao(){

		ControladorMicromedicaoLocalHome localHome = null;
		ControladorMicromedicaoLocal local = null;
		ServiceLocator locator = null;

		try{

			locator = ServiceLocator.getInstancia();
			localHome = (ControladorMicromedicaoLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_MICROMEDICAO_SEJB);
			local = localHome.create();

			return local;
		}catch(CreateException e){

			throw new SistemaException(e);
		}catch(ServiceLocatorException e){

			throw new SistemaException(e);
		}
	}

	protected ControladorImovelLocal getControladorImovel(){

		ControladorImovelLocalHome localHome = null;
		ControladorImovelLocal local = null;
		ServiceLocator locator = null;

		try{

			locator = ServiceLocator.getInstancia();
			localHome = (ControladorImovelLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_IMOVEL_SEJB);
			local = localHome.create();

			return local;
		}catch(CreateException e){

			throw new SistemaException(e);
		}catch(ServiceLocatorException e){

			throw new SistemaException(e);
		}
	}

	protected ControladorFaturamentoLocal getControladorFaturamento(){

		ControladorFaturamentoLocalHome localHome = null;
		ControladorFaturamentoLocal local = null;
		ServiceLocator locator = null;

		try{

			locator = ServiceLocator.getInstancia();
			localHome = (ControladorFaturamentoLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_FATURAMENTO_SEJB);
			local = localHome.create();

			return local;
		}catch(CreateException e){

			throw new SistemaException(e);

		}catch(ServiceLocatorException e){

			throw new SistemaException(e);
		}
	}

	public File execParamConverterArquivoLeituraFormato1(ParametroSistema parametroSistema, File arquivoLeitura,
					FaturamentoGrupo faturamentoGrupo) throws FileNotFoundException, ControladorException, IOException, ParseException{

		Collection<String> linhas = new ArrayList<String>();
		FileReader fileReader = new FileReader(arquivoLeitura);
		BufferedReader br = new BufferedReader(fileReader);
		String nomeArquivo = arquivoLeitura.getName();
		validarNomeArquivoFormato1(nomeArquivo);
		String header = br.readLine();
		validarHeaderFormato1(faturamentoGrupo, header, nomeArquivo);
		validarTrailerETipoRegistroFormato1(linhas, br);
		File arquivoDefinitivo = new File(arquivoLeitura.getName() + "-DEFINITIVO.txt");
		FileOutputStream fout = new FileOutputStream(arquivoDefinitivo);
		PrintWriter pw = new PrintWriter(fout);
		gerarHeaderArquivoDefinitivoFormato1(linhas, nomeArquivo, header, pw);
		gerarRegistrosArquivoDefinitivoFormato1(linhas, header, pw);
		gerarTraillerArquivoDefinitivoFormato1(linhas, pw);
		pw.flush();
		pw.close();
		return arquivoDefinitivo;
	}

	private void gerarTraillerArquivoDefinitivoFormato1(Collection<String> linhas, PrintWriter pw){

		StringBuilder traillerArquivoDefinitivo = new StringBuilder();
		traillerArquivoDefinitivo.append("9");
		traillerArquivoDefinitivo.append(Util.completarStringZeroEsquerda(String.valueOf(linhas.size() + 2), 6));
		traillerArquivoDefinitivo.append(Util.completarStringZeroEsquerda(String.valueOf(linhas.size()), 6));
		pw.println(traillerArquivoDefinitivo.toString());
	}

	private void gerarRegistrosArquivoDefinitivoFormato1(Collection<String> linhas, String header, PrintWriter pw)
					throws ControladorException, ParseException{

		StringBuilder registro = null;
		String dataGeracaoArquivo = recuperarDataHeader(header);
		FiltroMedicaoTipo filtroMedicaoTipo = new FiltroMedicaoTipo();
		filtroMedicaoTipo.adicionarParametro(new ParametroSimples(FiltroMedicaoTipo.DESCRICAO, MedicaoTipo.DESC_LIGACAO_AGUA));
		MedicaoTipo medicaoTipo = (MedicaoTipo) Util.retonarObjetoDeColecao(getControladorUtil().pesquisar(filtroMedicaoTipo,
						MedicaoTipo.class.getName()));
		String matriculaImovel = null;
		String leituraRealizada = null;
		String leituraFaturada = null;
		String consumoFaturado = null;
		String[] codigoAnormalidadeLeituraEConsumo = null;
		Map<String, Integer> mapaTipoConsumo = montarMapaTipoConsumoValidosFormato1();
		String diasConsumo = null;
		String valorAgua = null;
		String valorEsgoto = null;
		String valorDebitos = null;
		String indicadorGrandeConsumidor = null;
		Map<String, Integer> mapaIndicadorEmissao = montarMapaIndicadorEmissaoValidosFormato1();
		String indicadorConfirmacaoLeitura = null;
		String idLeiturista = (String) ParametroMicromedicao.P_AGENTE_COMERCIAL_PADRAO.executar(this);
		Integer i = 1;
		for(String linha : linhas){
			registro = new StringBuilder();

			registro.append("1");

			registro.append(Util.completarStringZeroEsquerda(idLeiturista, 6));

			registro.append(dataGeracaoArquivo);

			matriculaImovel = recuperarValorLinha(linha, 65, 7);
			if(Util.isInteger(matriculaImovel)){
				registro.append(Util.completarStringZeroEsquerda(matriculaImovel, 8));
			}else{
				throw new ControladorException("atencao.arquivo_linha_invalido", null, i.toString(), linha);
			}

			registro.append(Util.completarStringZeroEsquerda("0", 3));
			registro.append(Util.completarStringZeroEsquerda("0", 3));
			registro.append(Util.completarStringZeroEsquerda("0", 4));
			registro.append(Util.completarStringZeroEsquerda("0", 4));
			registro.append(Util.completarStringZeroEsquerda("0", 3));
			registro.append(medicaoTipo.getId().toString());
			leituraRealizada = recuperarValorLinha(linha, 1, 6);

			if(Util.isInteger(leituraRealizada)){
				registro.append(Util.completarStringZeroEsquerda(leituraRealizada, 6));
				indicadorConfirmacaoLeitura = null;
			}else{
				registro.append(Util.completarStringZeroEsquerda("0", 6));
				indicadorConfirmacaoLeitura = "0";
			}

			leituraFaturada = recuperarValorLinha(linha, 7, 6);
			if(Util.isInteger(leituraFaturada)){
				registro.append(Util.completarStringZeroEsquerda(leituraFaturada, 6));
			}else{
				throw new ControladorException("atencao.arquivo_linha_invalido", null, i.toString(), linha);
			}

			consumoFaturado = recuperarValorLinha(linha, 13, 6);
			if(Util.isInteger(consumoFaturado)){
				registro.append(Util.completarStringZeroEsquerda(consumoFaturado, 6));
			}else{
				throw new ControladorException("atencao.arquivo_linha_invalido", null, i.toString(), linha);
			}

			codigoAnormalidadeLeituraEConsumo = converterAnormalidadeLeituraParaAnormalidadeLeituraConsumoGsan(recuperarValorLinha(linha,
							22, 2));

			registro.append(Util.completarStringZeroEsquerda(codigoAnormalidadeLeituraEConsumo[0], 3));

			registro.append(Util.completarStringZeroEsquerda(codigoAnormalidadeLeituraEConsumo[1], 3));

			if(mapaTipoConsumo.containsKey(recuperarValorLinha(linha, 20, 1))){
				registro.append(Util.completarStringZeroEsquerda(mapaTipoConsumo.get(recuperarValorLinha(linha, 20, 1)).toString(), 2));
			}else{
				throw new ControladorException("atencao.arquivo_linha_tipo_consumo_invalido", null, i.toString());
			}

			diasConsumo = recuperarValorLinha(linha, 59, 2);
			if(Util.isInteger(diasConsumo)){
				registro.append(Util.completarStringZeroEsquerda(diasConsumo, 2));
			}else{
				throw new ControladorException("atencao.arquivo_linha_invalido", null, i.toString(), linha);
			}

			valorAgua = recuperarValorLinha(linha, 25, 11);
			if(Util.isNumero(valorAgua, false, 0)){
				registro.append(Util.completarStringZeroEsquerda(valorAgua, 11));
			}else{
				throw new ControladorException("atencao.arquivo_linha_invalido", null, i.toString(), linha);
			}

			valorEsgoto = recuperarValorLinha(linha, 36, 11);
			if(Util.isNumero(valorEsgoto, false, 0)){
				registro.append(Util.completarStringZeroEsquerda(valorEsgoto, 11));
			}else{
				throw new ControladorException("atencao.arquivo_linha_invalido", null, i.toString(), linha);
			}

			valorDebitos = recuperarValorLinha(linha, 47, 10);
			if(Util.isNumero(valorDebitos, false, 0)){
				registro.append(Util.completarStringZeroEsquerda(valorDebitos, 11));
			}else{
				throw new ControladorException("atencao.arquivo_linha_invalido", null, i.toString(), linha);
			}

			registro.append(Util.completarStringZeroEsquerda("0", 11));

			indicadorGrandeConsumidor = recuperarValorLinha(linha, 24, 1);
			if(Util.isInteger(indicadorGrandeConsumidor)){
				registro.append(Util.completarStringZeroEsquerda(indicadorGrandeConsumidor, 1));
			}else{
				throw new ControladorException("atencao.arquivo_linha_invalido", null, i.toString(), linha);
			}

			if(mapaIndicadorEmissao.get(recuperarValorLinha(linha, 61, 1)) != null){
				registro.append(mapaIndicadorEmissao.get(recuperarValorLinha(linha, 61, 1)));
			}else{
				throw new ControladorException("atencao.arquivo_linha_invalido", null, i.toString(), linha);
			}

			if(indicadorConfirmacaoLeitura == null){
				indicadorConfirmacaoLeitura = recuperarValorLinha(linha, 19, 1);
			}

			if(indicadorConfirmacaoLeitura != null && Util.isInteger(indicadorConfirmacaoLeitura)){

				if(indicadorConfirmacaoLeitura.equals("0")){

					registro.append(Util.completarStringZeroEsquerda(LeituraSituacao.NAO_REALIZADA.toString(), 1));
				}else if(indicadorConfirmacaoLeitura.equals("1")){

					registro.append(Util.completarStringZeroEsquerda(LeituraSituacao.REALIZADA.toString(), 1));
				}else if(indicadorConfirmacaoLeitura.equals("2")){

					registro.append(Util.completarStringZeroEsquerda(LeituraSituacao.CONFIRMADA.toString(), 1));
				}else if(indicadorConfirmacaoLeitura.equals("3")){

					registro.append(Util.completarStringZeroEsquerda(LeituraSituacao.RECONFIRMADA.toString(), 1));
				}else{

					registro.append(Util.completarStringZeroEsquerda(LeituraSituacao.INDEFINIDO.toString(), 1));
				}

			}else{

				registro.append(Util.completarStringZeroEsquerda(LeituraSituacao.NAO_REALIZADA.toString(), 1));
			}

			// Completa com espaços em branco para campos não utilizados por esse modelo
			registro.append(Util.completaString("", 39));

			i++;
			pw.println(registro.toString());
		}
	}

	private Map<String, Integer> montarMapaTipoConsumoValidosFormato1(){

		Map<String, Integer> mapaTipoConsumo = new HashMap<String, Integer>();
		mapaTipoConsumo.put("A", 1);
		mapaTipoConsumo.put("E", 2);
		mapaTipoConsumo.put("I", 3);
		mapaTipoConsumo.put("M", 4);
		mapaTipoConsumo.put("N", 5);
		mapaTipoConsumo.put("R", 6);
		mapaTipoConsumo.put("Z", 7);
		mapaTipoConsumo.put(" ", 0);

		return mapaTipoConsumo;
	}

	private void gerarHeaderArquivoDefinitivoFormato1(Collection<String> linhas, String nomeArquivo, String header, PrintWriter pw)
					throws ControladorException{

		StringBuilder headerArquivoDefinitivo = new StringBuilder();
		headerArquivoDefinitivo.append("0");
		headerArquivoDefinitivo.append("      ");
		headerArquivoDefinitivo.append("R");
		String grupoFaturamentoHeader = recuperarValorLinha(header, 7, 3);
		headerArquivoDefinitivo.append(Util.completarStringZeroEsquerda(Util.obterInteger(grupoFaturamentoHeader).toString(), 2));
		String anoMesReferenciaHeader = recuperarValorLinha(header, 1, 6);
		headerArquivoDefinitivo.append(Util.completarStringZeroEsquerda(anoMesReferenciaHeader, 6));
		String dataGeracaoArquivo = recuperarDataCorrente();
		headerArquivoDefinitivo.append(Util.completarStringZeroEsquerda(dataGeracaoArquivo, 8));
		headerArquivoDefinitivo.append(Util.completarStringZeroEsquerda(String.valueOf(linhas.size() + 2), 6));
		pw.println(headerArquivoDefinitivo.toString());
	}

	private void validarTrailerETipoRegistroFormato1(Collection<String> linhas, BufferedReader br) throws IOException, ControladorException{

		Boolean achouTrailler = Boolean.FALSE;
		Integer contadorLinhas = 2;
		String linha;
		String tipoRegistro;
		while((linha = br.readLine()) != null){
			tipoRegistro = linha.substring(0, 1);
			if("9".equals(tipoRegistro)){
				achouTrailler = Boolean.TRUE;
				break;
			}else if("0".equals(tipoRegistro)){
				throw new ControladorException("atencao.arquivo_retorno_nao_possui_trailler");
			}else if("1".equals(tipoRegistro)){
				linhas.add(linha);
				contadorLinhas++;
			}else{
				throw new ControladorException("atencao.arquivo_retorno_registro_tipo_invalido");
			}
		}
		if(!achouTrailler){
			throw new ControladorException("atencao.arquivo_retorno_nao_possui_trailler");
		}
		String qtidadeTotalRegistros = recuperarValorLinha(linha, 31, 5);
		if(!Util.obterInteger(qtidadeTotalRegistros).equals(contadorLinhas)){
			throw new ControladorException("atencao.arquivo_retorno_quantidade_total_diferente_quantidade_total_trailler", null,
							contadorLinhas.toString(), qtidadeTotalRegistros);
		}

	}

	private void validarNomeArquivoFormato1(String nomeArquivo) throws ControladorException{

		if(!"RET".equalsIgnoreCase(recuperarValorLinha(nomeArquivo, 0, 3))){
			throw new ControladorException("atencao.nome_arquivo_conversao_invalido", null, nomeArquivo);
		}
		String idFaturamentoGrupo = recuperarValorLinha(nomeArquivo, 3, 3);
		FaturamentoGrupo faturamentoGrupoNomeArquivo = (FaturamentoGrupo) getControladorUtil().pesquisar(
						Util.obterInteger(idFaturamentoGrupo), FaturamentoGrupo.class, false);
		if(faturamentoGrupoNomeArquivo == null){
			throw new ControladorException("atencao.nome_arquivo_conversao_invalido", null, nomeArquivo);
		}
		String referencia = recuperarValorLinha(nomeArquivo, 6, 6);
		if(Util.validarMesAno(referencia)){
			throw new ControladorException("atencao.nome_arquivo_conversao_invalido", null, nomeArquivo);
		}

	}

	private String recuperarDataCorrente() throws ControladorException{

		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
		return sdf.format(new Date());
	}

	private String recuperarDataHeader(String header) throws ControladorException, ParseException{

		String dataLeituraString = recuperarValorLinha(header, 10, 8);
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("ddMMyyyy");
		return sdf2.format(sdf1.parse(dataLeituraString));
	}

	private void validarHeaderFormato1(FaturamentoGrupo faturamentoGrupo, String header, String nomeArquivo) throws ControladorException{

		int posicaoAnterior = 0;
		int qtidadeCaracteres = 0;

		String tipoRegistro = recuperarValorLinha(header, posicaoAnterior += qtidadeCaracteres, qtidadeCaracteres = 1);
		if(!"0".equals(tipoRegistro)){
			throw new ControladorException("atencao.arquivo_sem_header", null, nomeArquivo);
		}

		String referenciaFaturamentoString = recuperarValorLinha(header, posicaoAnterior += qtidadeCaracteres, qtidadeCaracteres = 6);
		Integer referenciaFaturamento = Util.obterInteger(referenciaFaturamentoString);
		if(referenciaFaturamento == null){
			throw new ControladorException("atencao.arquivo_header_ano_mes_nao_numerico", null, referenciaFaturamentoString);
		}
		if(Util.validarAnoMesSemBarra(referenciaFaturamentoString)){
			throw new ControladorException("atencao.arquivo_header_ano_mes_invalido", null, referenciaFaturamentoString);
		}
		if(!faturamentoGrupo.getAnoMesReferencia().equals(referenciaFaturamento)){
			throw new ControladorException("atencao.arquivo_header_ano_mes_diferente_ano_mes_faturamento_grupo", null,
							referenciaFaturamentoString, faturamentoGrupo.getAnoMesReferencia().toString());
		}

		String grupoFaturamentoString = recuperarValorLinha(header, posicaoAnterior += qtidadeCaracteres, qtidadeCaracteres = 3);
		Integer idGrupoFaturamento = Util.obterInteger(grupoFaturamentoString);
		if(idGrupoFaturamento == null){
			throw new ControladorException("atencao.arquivo_header_grupo_faturamento_nao_numerico", null, grupoFaturamentoString);
		}
		if(!faturamentoGrupo.getId().equals(idGrupoFaturamento)){
			throw new ControladorException("atencao.arquivo_header_faturamento_grupo_arquivo_diferente_faturamento_grupo_tela", null,
							grupoFaturamentoString, faturamentoGrupo.getId().toString());
		}

		String dataLeituraString = recuperarValorLinha(header, posicaoAnterior += qtidadeCaracteres, qtidadeCaracteres = 8);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Integer dataLeituraNumerica = Util.obterInteger(dataLeituraString);
		if(dataLeituraNumerica == null){
			throw new ControladorException("atencao.arquivo_header_data_leitura_nao_numerico", null, dataLeituraString);
		}
		Date dataLeitura = null;
		if(Util.validarAnoMesDiaSemBarra(dataLeituraString)){
			throw new ControladorException("atencao.arquivo_header_data_leitura_invalida", null, dataLeituraString);
		}
		try{
			dataLeitura = sdf.parse(dataLeituraString);
		}catch(ParseException e){
			throw new ControladorException("atencao.arquivo_header_data_leitura_invalida", null, dataLeituraString);
		}
		if(dataLeitura.after(new Date())){
			throw new ControladorException("atencao.arquivo_header_data_leitura_invalida", null, dataLeituraString);
		}

		if(Util.getAnoMesComoInt(dataLeitura) != faturamentoGrupo.getAnoMesReferencia().intValue()
						&& Util.getAnoMesComoInt(dataLeitura) != Util.somarData(faturamentoGrupo.getAnoMesReferencia().intValue())
						&& Util.getAnoMesComoInt(dataLeitura) != Util.subtrairData(faturamentoGrupo.getAnoMesReferencia().intValue())){
			throw new ControladorException("atencao.arquivo_header_data_leitura_diferente_data_leitura_faturamento_grupo_tela", null,
							dataLeituraString, faturamentoGrupo.getAnoMesReferencia().toString());
		}
	}

	private static String recuperarValorLinha(String linha, int posicaoAnterior, int qtidadeCaracteres){

		return linha.substring(posicaoAnterior, posicaoAnterior + qtidadeCaracteres);
	}

	/**
	 * Retorna o valor de controladorUtil
	 * 
	 * @return O valor de controladorUtil
	 */
	protected ControladorUtilLocal getControladorUtil(){

		ControladorUtilLocalHome localHome = null;
		ControladorUtilLocal local = null;

		// pega a instância do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorUtilLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_UTIL_SEJB);
			// guarda a referencia de um objeto capaz de fazer chamadas à
			// objetos remotamente
			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}

	}

	private String[] converterAnormalidadeLeituraParaAnormalidadeLeituraConsumoGsan(String d08) throws ControladorException{

		String codigoAnormalidadeLeitura = null;
		String codigoAnormalidadeConsumo = null;
		if(!Util.isInteger(d08) && d08.trim().length() > 0){
			codigoAnormalidadeLeitura = "0";
			FiltroConsumoAnormalidade filtroConsumoAnormalidade = new FiltroConsumoAnormalidade();
			filtroConsumoAnormalidade.adicionarParametro(new ComparacaoTextoCompleto(FiltroConsumoAnormalidade.DESCRICAO_ABREVIADA, d08));

			ConsumoAnormalidade consumoAnormalidade = (ConsumoAnormalidade) Util.retonarObjetoDeColecao(getControladorUtil().pesquisar(
							filtroConsumoAnormalidade, ConsumoAnormalidade.class.getName()));

			if(consumoAnormalidade == null){
				throw new ControladorException("atencao.arquivo_linha_anormalidade_consumo_inexistente", null, d08);
			}
			codigoAnormalidadeConsumo = consumoAnormalidade.getId().toString();
		}else if(!Util.isInteger(d08) && d08.trim().length() == 0){
			codigoAnormalidadeLeitura = "0";
			codigoAnormalidadeConsumo = "0";
		}else if(Util.isInteger(d08) && Util.obterInteger(d08).intValue() != 0){
			codigoAnormalidadeLeitura = d08;
			codigoAnormalidadeConsumo = "0";
		}else if(Util.isInteger(d08) && Util.obterInteger(d08).intValue() == 0){
			codigoAnormalidadeLeitura = "0";
			codigoAnormalidadeConsumo = "0";
		}
		return new String[] {codigoAnormalidadeLeitura, codigoAnormalidadeConsumo};

	}

	/**
	 * [UC3012] Gerar Arquivo Texto Faturamento Imediato
	 * [SB0001] – Gerar Arquivo Texto para Faturamento Imediato – Modelo 1
	 * Método responsável pela geração de um arquivo texto para envio do faturamento imediato
	 * 
	 * @author Anderson Italo
	 * @date 31/05/2012
	 * @throws ErroRepositorioException
	 * @throws ControladorException
	 * @throws IOException
	 * @throws CreateException
	 */
	public void execParamGerarArquivoTextoFaturamentoImediatoModelo1(ParametroSistema parametroSistema, Integer anoMesFaturamento,
					Integer idFaturamentoGrupo, Integer idFuncionalidadeIniciada, Integer idEmpresa, String idsRotas,
					Collection colecaoMovimentoRoteiroEmpresa) throws ControladorException, ErroRepositorioException, IOException,
					CreateException{

		this.ejbCreate();

		// [SB0001] – Gerar Arquivo Texto para Faturamento Imediato.
		// Gera os arquivos de Tabelas.
		Object[] arquivoTabelas = this.gerarDadosTabelaParaFaturamentoImediatoTxt(anoMesFaturamento, idFaturamentoGrupo, idEmpresa);

		// Gera os arquivos de Faturamento Imediato.
		Object[] arquivoDados = null;

		arquivoDados = this.gerarDadosDoFaturamentoImediatoTxt(anoMesFaturamento, idFaturamentoGrupo, idEmpresa,
						colecaoMovimentoRoteiroEmpresa, idsRotas, idFuncionalidadeIniciada);

		this.enviarArquivosFaturamentoImediatoParaBatch(arquivoTabelas, arquivoDados, idFuncionalidadeIniciada);
	}

	/**
	 * [UC3012] – Gerar Arquivo com Dados e Tabelas do Faturamento Imediado
	 * 
	 * @author Hebert Falcão
	 * @date 12/04/2012
	 */
	private void enviarArquivosFaturamentoImediatoParaBatch(Object[] arquivoTabelas, Object[] arquivoDados, Integer idFuncionalidadeIniciada)
					throws ControladorException{

		StringBuffer conteudoArquivoTabelas = null;
		String nomeArquivoTabelas = null;

		if(arquivoTabelas != null){
			conteudoArquivoTabelas = (StringBuffer) arquivoTabelas[0];
			nomeArquivoTabelas = (String) arquivoTabelas[1];
		}

		StringBuffer conteudoArquivoDados = null;
		String nomeArquivoDados = null;

		if(arquivoDados != null){
			conteudoArquivoDados = (StringBuffer) arquivoDados[0];
			nomeArquivoDados = (String) arquivoDados[1];
		}

		// Usuário
		Usuario usuario = null;

		// Recupera o usuário logado
		if(idFuncionalidadeIniciada != null){
			usuario = this.getControladorBatch().obterUsuarioFuncionalidadeIniciada(idFuncionalidadeIniciada);
		}else{
			// Insere o usario Batch
			usuario = Usuario.USUARIO_BATCH;
		}

		EnvioEmail envioEmail = getControladorCadastro().pesquisarEnvioEmail(EnvioEmail.ARQUIVO_DADOS_TABELAS_FATURAMENTO_IMEDIATO);

		RelatorioDadosTabelasFaturamentoImediato relatorio = new RelatorioDadosTabelasFaturamentoImediato(usuario);

		relatorio.addParametro("conteudoArquivoDados", conteudoArquivoDados);
		relatorio.addParametro("conteudoArquivoTabelas", conteudoArquivoTabelas);
		relatorio.addParametro("nomeArquivoDados", nomeArquivoDados);
		relatorio.addParametro("nomeArquivoTabelas", nomeArquivoTabelas);
		relatorio.addParametro("tipoFormatoRelatorio", TarefaRelatorio.TIPO_ZIP);
		relatorio.addParametro("envioEmail", envioEmail);

		this.getControladorBatch().iniciarProcessoRelatorio(relatorio);
	}

	/**
	 * [UC3012 – Gerar Arquivo Texto Faturamento Imediado]
	 * [SB0001] – Gerar Arquivo Texto para Faturamento Imediato.
	 * 1. Gera os dados do arquivo de tabelas.
	 * 
	 * @author Ailton Sousa
	 * @date 18/08/2011
	 * @throws ControladorException
	 */
	private Object[] gerarDadosTabelaParaFaturamentoImediatoTxt(Integer anoMesFaturamento, Integer idFaturamentoGrupo, Integer idEmpresa)
					throws ControladorException{

		Object[] retornoArquivo = new Object[2];

		StringBuffer arquivoTxtLinha = new StringBuffer();
		List colecaoLeituraAnormalidade = null;
		List colecaoDebitoTipo = null;

		LeituraAnormalidade leituraAnormalidade = null;
		DebitoTipo debitoTipo = null;
		Object[] retorno;

		Object[] descricaoContaMensagem = null;
		String nomeArquivo = "";
		String mes = null;
		String dia = null;

		Calendar dataCalendar = new GregorianCalendar();

		mes = "" + (dataCalendar.get(Calendar.MONTH) + 1);
		dia = "" + dataCalendar.get(Calendar.DAY_OF_MONTH);

		mes = Util.adicionarZerosEsquedaNumero(2, mes);
		dia = Util.adicionarZerosEsquedaNumero(2, dia);

		nomeArquivo = "TABELAS" + Util.adicionarZerosEsqueda(3, idFaturamentoGrupo.toString()) + ".DAT";

		// 1.1. Grava a tabela de anormalidades (a partir da tabela LEITURA_ANORMALIDADE ordenando
		// pelo código da anormalidade (LTAN_ID))
		try{
			colecaoLeituraAnormalidade = repositorioMicromedicao.pesquisarTodasAnormalidadesLeitura();
		}catch(ErroRepositorioException e){
			e.printStackTrace();
		}

		if(colecaoLeituraAnormalidade != null && !colecaoLeituraAnormalidade.isEmpty()){

			for(int i = 0; i < colecaoLeituraAnormalidade.size(); i++){
				retorno = (Object[]) colecaoLeituraAnormalidade.get(i);

				leituraAnormalidade = new LeituraAnormalidade();

				leituraAnormalidade.setId((Integer) retorno[0]);
				leituraAnormalidade.setDescricao((String) retorno[1]);

				// #################### Linha LeituraAnormalidade ####################

				arquivoTxtLinha.append("1");

				arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(3, leituraAnormalidade.getId().toString()));

				arquivoTxtLinha.append(Util.completaString(leituraAnormalidade.getDescricao(), 25));

				arquivoTxtLinha.append(Util.completaString("", 62));

				arquivoTxtLinha.append(System.getProperty("line.separator"));
			}
		}

		// 1.2. Grava a tabela de tipo de débitos (a partir da tabela DEBITO_TIPO ordenando pelo
		// tipo do débito (DBTP_ID))
		colecaoDebitoTipo = this.pesquisarTodosDebitoTipos();

		if(colecaoDebitoTipo != null && !colecaoDebitoTipo.isEmpty()){

			for(int i = 0; i < colecaoDebitoTipo.size(); i++){
				retorno = (Object[]) colecaoDebitoTipo.get(i);

				debitoTipo = new DebitoTipo();

				debitoTipo.setId((Integer) retorno[0]);
				debitoTipo.setDescricao((String) retorno[1]);

				// #################### Linha DebitoTipo ####################

				arquivoTxtLinha.append("2");

				arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(3, debitoTipo.getId().toString()));

				arquivoTxtLinha.append(Util.completaString(debitoTipo.getDescricao(), 25));

				arquivoTxtLinha.append(Util.completaString("", 62));

				arquivoTxtLinha.append(System.getProperty("line.separator"));
			}
		}

		// 1.3. Grava a tabela CONTA_MENSAGEM
		// Caso exista a mensagem para o grupo (existe ocorrência na tabela CONTA_MENSAGEM com
		// CTMS_AMREFERENCIAFATURAMENTO menor ou igual ao ano/mês do faturamento recebido e mais
		// recente e FTGR_ID=FTGR_ID da tabela ROTA com ROTA_ID contida em algumas das rotas do
		// grupo de rotas da empresa em processamento), atribuir a mensagem do grupo
		// (CTMS_DSCONTAMENSAGEM01); caso contrário, atribuir o valor branco.

		descricaoContaMensagem = this.pesquisarDescricaoContaMensagemMaisRecente(anoMesFaturamento, idFaturamentoGrupo);

		// #################### Linha ContaMensagens ####################

		if(descricaoContaMensagem == null){
			addMensagemOubrancoTamanho80(arquivoTxtLinha, "", true);
			addMensagemOubrancoTamanho80(arquivoTxtLinha, "", true);
			addMensagemOubrancoTamanho80(arquivoTxtLinha, "", false);
		}else{
			String mensagem1 = (String) descricaoContaMensagem[0];
			String mensagem2 = (String) descricaoContaMensagem[1];
			String mensagem3 = (String) descricaoContaMensagem[2];
			addMensagemOubrancoTamanho80(arquivoTxtLinha, mensagem1, true);
			addMensagemOubrancoTamanho80(arquivoTxtLinha, mensagem2, true);
			addMensagemOubrancoTamanho80(arquivoTxtLinha, mensagem3, false);
		}

		retornoArquivo[0] = arquivoTxtLinha;
		retornoArquivo[1] = nomeArquivo;

		return retornoArquivo;
	}

	private void addMensagemOubrancoTamanho80(StringBuffer arquivoTxtLinha, String mensagem, boolean pularLinha){

		arquivoTxtLinha.append("3");

		if(Util.isNaoNuloBrancoZero(mensagem)){
			arquivoTxtLinha.append(Util.completaString(mensagem, 80));
		}else{
			arquivoTxtLinha.append(Util.completaString("", 80));
		}

		if(pularLinha){
			arquivoTxtLinha.append(System.getProperty("line.separator"));
		}
	}

	/**
	 * [UC3012 – Gerar Arquivo Texto Faturamento Imediado]
	 * [SB0001] – Gerar Arquivo Texto para Faturamento Imediato.
	 * 1. Gera os dados do Faturamento Imediato.
	 * 
	 * @author Ailton Sousa
	 * @date 19/08/2011
	 * @param colecaoMovimentoRoteiroEmpresa
	 * @throws ControladorException
	 * @throws ErroRepositorioException
	 */
	private Object[] gerarDadosDoFaturamentoImediatoTxt(Integer anoMesFaturamento, Integer idFaturamentoGrupo, Integer idEmpresa,
					Collection colecaoMovimentoRoteiroEmpresa, String idsRotas, Integer idFuncionalidadeIniciada)
					throws ControladorException, ErroRepositorioException{

		Object[] retornoArquivo = new Object[2];

		StringBuffer arquivoTxtLinha = new StringBuffer();
		Iterator itMovimentoRoteiroEmpresa = colecaoMovimentoRoteiroEmpresa.iterator();

		QualidadeAgua qualidadeAgua = null;
		MovimentoRoteiroEmpresa movimentoRoteiroEmpresa = null;
		SistemaParametro sistemaParametro = this.getControladorUtil().pesquisarParametrosDoSistema();

		Collection colecaoGrandesConsumidores = null;

		String nomeArquivo = "";
		String inscricaoNaoFormatada = null;
		Date dtVencimentoHeader;
		Integer consumoGrandeUsuario;
		boolean gerarHeader = true;

		Integer codigoSetorComercial = null;

		Collection<Integer> colecaoSetorComercial = new ArrayList<Integer>();

		int quantidadeFaturados = 0;
		int quantidadeMedidos = 0;
		int quantidadeNaoMedidos = 0;

		BigDecimal valorDebitos = BigDecimal.ZERO;
		BigDecimal valorCredito = BigDecimal.ZERO;

		BigDecimal valorTotalDebitos = BigDecimal.ZERO;
		BigDecimal valorTotalCreditos = BigDecimal.ZERO;

		HashMap<Integer, RelatorioOcorrenciaGeracaoPreFatResumoHelper> geracaoPreFatResumoHelperMap = new HashMap<Integer, RelatorioOcorrenciaGeracaoPreFatResumoHelper>();

		RelatorioOcorrenciaGeracaoPreFatResumoHelper geracaoPreFatResumoHelper = null;

		Localidade localidade = null;

		Integer idLocalidade = null;

		int quantidadeTipo1Aux = 0;
		int quantidadeTipo2Aux = 0;
		int quantidadeTipo3Aux = 0;
		int quantidadeTipo4Aux = 0;
		int quantidadeTipo5Aux = 0;
		int quantidadeTipo6Aux = 0;

		int quantidadeTotalTipo1 = 0;
		int quantidadeTotalTipo2 = 0;
		int quantidadeTotalTipo3 = 0;
		int quantidadeTotalTipo4 = 0;
		int quantidadeTotalTipo5 = 0;
		int quantidadeTotalTipo6 = 0;

		int quantidadeTipo1 = 0;
		int quantidadeTipo2 = 0;
		int quantidadeTipo3 = 0;
		int quantidadeTipo4 = 0;
		int quantidadeTipo5 = 0;
		int quantidadeTipo6 = 0;

		nomeArquivo = "Rem" + Util.adicionarZerosEsquedaNumero(3, idFaturamentoGrupo.toString())
						+ Util.formatarAnoMesParaMesAnoSemBarra(anoMesFaturamento) + ".dat";

		dtVencimentoHeader = this.retornarMaiorDtContaVencimentoPorRotaIdFaturamentoAnoMesFaturamento(idsRotas, idFaturamentoGrupo,
						anoMesFaturamento);

		if(dtVencimentoHeader == null){
			throw new ControladorException("atencao.faturamento_atividade_cronograma_rota_sem_data_vencimento");
		}

		consumoGrandeUsuario = pesquisarMaiorConsumoGrandeUsuarioComLimiteRotas(anoMesFaturamento, idFaturamentoGrupo, idEmpresa, idsRotas);

		if(consumoGrandeUsuario == null){
			consumoGrandeUsuario = sistemaParametro.getMenorConsumoGrandeUsuario();
		}
		while(itMovimentoRoteiroEmpresa.hasNext()){
			quantidadeTipo1Aux = 0;
			quantidadeTipo2Aux = 0;
			quantidadeTipo3Aux = 0;
			quantidadeTipo4Aux = 0;
			quantidadeTipo5Aux = 0;
			quantidadeTipo6Aux = 0;

			movimentoRoteiroEmpresa = (MovimentoRoteiroEmpresa) itMovimentoRoteiroEmpresa.next();
			movimentoRoteiroEmpresa.setImovel(repositorioImovel.pesquisarImovelPorId(movimentoRoteiroEmpresa.getImovel().getId()));

			inscricaoNaoFormatada = movimentoRoteiroEmpresa.getInscricaoNaoFormatadaArquivoModelo1();

			if(gerarHeader){
				// Seta para false porque o header so é gerado uma vez.
				gerarHeader = false;

				// [SB0002 – Obter Dados da Qualidade Água]
				qualidadeAgua = pesquisarDadosQualidadeAgua(anoMesFaturamento, movimentoRoteiroEmpresa.getLocalidade().getId(), //
								(movimentoRoteiroEmpresa.getLocalidade().getLocalidade() == null) ? null
												: movimentoRoteiroEmpresa.getLocalidade().getLocalidade().getId());

				// 2.1. O sistema grava o registro tipo header (tipo 0)

				// #################### HEADER ####################

				arquivoTxtLinha.append("0");

				String fone = "";
				if(!Util.isVazioOuBranco(movimentoRoteiroEmpresa.getLocalidade())
								&& !Util.isVazioOuBrancoOuZero(movimentoRoteiroEmpresa.getLocalidade().getFone())){
					fone = "(79)" + Util.formatarFone(movimentoRoteiroEmpresa.getLocalidade().getFone());
				}else{
					if(!Util.isVazioOuBranco(movimentoRoteiroEmpresa.getLocalidade())
									&& !Util.isVazioOuBranco(movimentoRoteiroEmpresa.getLocalidade().getLocalidade())
									&& !Util.isVazioOuBrancoOuZero(movimentoRoteiroEmpresa.getLocalidade().getLocalidade().getFone())){
						fone = "(79)" + Util.formatarFone(movimentoRoteiroEmpresa.getLocalidade().getLocalidade().getFone());
					}
				}

				arquivoTxtLinha.append(Util.completaString(fone, 15));

				arquivoTxtLinha.append(Util.completaString(anoMesFaturamento.toString(), 6));

				arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(3, idFaturamentoGrupo.toString()));

				arquivoTxtLinha.append(Util.completaString(Util.formatarDataAAAAMMDD(dtVencimentoHeader), 8));

				arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(6, consumoGrandeUsuario.toString()));

				// Dados Qualidade Água
				if(qualidadeAgua != null){

					if(qualidadeAgua.getNumeroAmostrasExigidasTurbidez() != null){
						arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(4, qualidadeAgua.getNumeroAmostrasExigidasTurbidez()
										.toString()));
					}else{
						arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(4, "0"));
					}

					if(qualidadeAgua.getNumeroAmostrasExigidasCor() != null){
						arquivoTxtLinha
										.append(Util
														.adicionarZerosEsquedaNumero(4, qualidadeAgua.getNumeroAmostrasExigidasCor()
																		.toString()));
					}else{
						arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(4, "0"));
					}

					if(qualidadeAgua.getNumeroAmostrasExigidasCloro() != null){
						arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(4, qualidadeAgua.getNumeroAmostrasExigidasCloro()
										.toString()));
					}else{
						arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(4, "0"));
					}

					if(qualidadeAgua.getNumeroAmostrasExigidasFluor() != null){
						arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(4, qualidadeAgua.getNumeroAmostrasExigidasFluor()
										.toString()));
					}else{
						arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(4, "0"));
					}

					if(qualidadeAgua.getNumeroAmostrasExigidasColiformesTotais() != null){
						arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(4, qualidadeAgua
										.getNumeroAmostrasExigidasColiformesTotais().toString()));
					}else{
						arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(4, "0"));
					}

				}else{
					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(4, "0"));

					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(4, "0"));

					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(4, "0"));

					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(4, "0"));

					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(4, "0"));
				}
				if(qualidadeAgua != null){

					if(qualidadeAgua.getNumeroAmostrasRealizadasTurbidez() != null){
						arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(4, qualidadeAgua.getNumeroAmostrasRealizadasTurbidez()
										.toString()));
					}else{
						arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(4, "0"));
					}

					if(qualidadeAgua.getNumeroAmostrasRealizadasCor() != null){
						arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(4, qualidadeAgua.getNumeroAmostrasRealizadasCor()
										.toString()));
					}else{
						arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(4, "0"));
					}

					if(qualidadeAgua.getNumeroAmostrasRealizadasCloro() != null){
						arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(4, qualidadeAgua.getNumeroAmostrasRealizadasCloro()
										.toString()));
					}else{
						arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(4, "0"));
					}

					if(qualidadeAgua.getNumeroAmostrasRealizadasFluor() != null){
						arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(4, qualidadeAgua.getNumeroAmostrasRealizadasFluor()
										.toString()));
					}else{
						arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(4, "0"));
					}

					if(qualidadeAgua.getNumeroAmostrasRealizadasColiformesTotais() != null){
						arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(4, qualidadeAgua
										.getNumeroAmostrasRealizadasColiformesTotais().toString()));

					}else{
						arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(4, "0"));
					}

					if(qualidadeAgua.getNumeroAmostrasRealizadasColiformesTermotolerantes() != null){
						arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(4, qualidadeAgua
										.getNumeroAmostrasRealizadasColiformesTermotolerantes().toString()));
					}else{
						arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(4, "0"));
					}

					if(qualidadeAgua.getNumeroAmostrasConformesTurbidez() != null){
						arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(4, qualidadeAgua.getNumeroAmostrasConformesTurbidez()
										.toString()));
					}else{
						arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(4, "0"));
					}

					if(qualidadeAgua.getNumeroAmostrasConformesCor() != null){
						arquivoTxtLinha.append(Util
										.adicionarZerosEsquedaNumero(4, qualidadeAgua.getNumeroAmostrasConformesCor().toString()));
					}else{
						arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(4, "0"));
					}

					if(qualidadeAgua.getNumeroAmostrasConformesCloro() != null){
						arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(4, qualidadeAgua.getNumeroAmostrasConformesCloro()
										.toString()));
					}else{
						arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(4, "0"));
					}

					if(qualidadeAgua.getNumeroAmostrasConformesFluor() != null){
						arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(4, qualidadeAgua.getNumeroAmostrasConformesFluor()
										.toString()));
					}else{
						arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(4, "0"));
					}

					if(qualidadeAgua.getNumeroAmostrasConformesColiformesTotais() != null){
						arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(4, qualidadeAgua
										.getNumeroAmostrasConformesColiformesTotais().toString()));
					}else{
						arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(4, "0"));
					}

					if(qualidadeAgua.getNumeroAmostrasConformesColiformesTermotolerantes() != null){
						arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(4, qualidadeAgua
										.getNumeroAmostrasConformesColiformesTermotolerantes().toString()));
					}else{
						arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(4, "0"));
					}

				}else{
					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(4, "0"));

					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(4, "0"));

					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(4, "0"));

					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(4, "0"));

					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(4, "0"));

					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(4, "0"));

					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(4, "0"));

					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(4, "0"));

					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(4, "0"));

					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(4, "0"));

					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(4, "0"));

					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(4, "0"));
				}

				arquivoTxtLinha.append(System.getProperty("line.separator"));

				// 2.2. O sistema grava o registro tipo conclusão dos dados da qualidade da água.

				// #################### CONCLUSAO ####################

				arquivoTxtLinha.append("7");
				if(qualidadeAgua != null && qualidadeAgua.getDescricaoConclusaoAnalisesRealizadas() != null
								&& !qualidadeAgua.getDescricaoConclusaoAnalisesRealizadas().equals("")){
					arquivoTxtLinha.append(Util.completaString(qualidadeAgua.getDescricaoConclusaoAnalisesRealizadas(), 110));
				}else{
					arquivoTxtLinha.append(Util.completaString("", 110));
				}
				arquivoTxtLinha.append(System.getProperty("line.separator"));

				// Grava registro tipo 1
				List<Object[]> colecaoTarifas = repositorioFaturamento.pesquisarTarifasArquivoTextoFaturamentoImediato(anoMesFaturamento);
				BigDecimal limiteSuperiorFaixa = null;
				Integer limiteInferiorFaixa = null;

				// [FS0004 – Verificar existência de tarifas]
				if(!Util.isVazioOrNulo(colecaoTarifas)){

					for(int i = 0; i < colecaoTarifas.size(); i++){

						Object[] linhaRetornada = colecaoTarifas.get(i);
						limiteSuperiorFaixa = BigDecimal.ZERO;
						limiteInferiorFaixa = 0;

						// Incrementa o Contador de linhas do tipo 1
						quantidadeTipo1 = quantidadeTipo1 + 1;
						quantidadeTipo1Aux = quantidadeTipo1Aux + 1;

						arquivoTxtLinha.append("1");

						arquivoTxtLinha.append(Util.completaString("", 15));

						// Código da Tarifa
						arquivoTxtLinha.append(Util.completarStringComValorEsquerda(linhaRetornada[0].toString(), "0", 2));

						// Código da Categoria
						arquivoTxtLinha.append(linhaRetornada[1].toString());

						// Limite Superior da Faixa
						if(linhaRetornada[2] != null && !linhaRetornada[2].toString().equals("")){

							arquivoTxtLinha.append(Util.completarStringComValorEsquerda(linhaRetornada[2].toString(), "0", 7));
							limiteSuperiorFaixa = new BigDecimal(linhaRetornada[2].toString());
						}else{

							arquivoTxtLinha.append(Util.completarStringComValorEsquerda("", "0", 7));
						}

						// Limite Inferior da Faixa
						if(linhaRetornada[4] != null && !linhaRetornada[4].toString().equals("")){

							limiteInferiorFaixa = Util.obterInteger(linhaRetornada[4].toString());
						}

						// Caso seja a primeira faixa
						if(limiteInferiorFaixa.intValue() == 0){

							// Valor da Tarifa = Valor da Tarifa X Limite Superior da Faixa
							if(linhaRetornada[3] != null && !linhaRetornada[3].toString().equals("")){

								arquivoTxtLinha.append(Util.completarStringComValorEsquerda((new BigDecimal(linhaRetornada[3].toString())
												.multiply(limiteSuperiorFaixa)).setScale(2, BigDecimal.ROUND_DOWN).toString().replace(".",
												""), "0", 7));
							}else{

								arquivoTxtLinha.append(Util.completarStringComValorEsquerda("", "0", 7));
							}
						}else{

							// Valor da Tarifa
							if(linhaRetornada[3] != null && !linhaRetornada[3].toString().equals("")){

								arquivoTxtLinha.append(Util.completarStringComValorEsquerda(new BigDecimal(linhaRetornada[3].toString())
												.setScale(2, BigDecimal.ROUND_DOWN).toString().replace(".", ""), "0", 7));
							}else{

								arquivoTxtLinha.append(Util.completarStringComValorEsquerda("", "0", 7));
							}
						}

						arquivoTxtLinha.append(System.getProperty("line.separator"));
					}
				}else{

					throw new ControladorException("atencao.tarifas_vigentes_inexistentes");
				}

				colecaoGrandesConsumidores = this.obterDadosGrandesConsumidores();

				// [FS0005 – Verificar existência de grandes consumidores].
				if(colecaoGrandesConsumidores != null & !colecaoGrandesConsumidores.isEmpty()){
					Iterator itGrandesConsumidores = colecaoGrandesConsumidores.iterator();
					ConsumoFaixaAreaCategoria consumoFaixaAreaCategoria = null;
					Integer idCategoriaAtual = null;
					Integer idCategoriaAnterior = null;
					int sequencialOcorrenciaCategoria = 0;

					while(itGrandesConsumidores.hasNext()){
						consumoFaixaAreaCategoria = (ConsumoFaixaAreaCategoria) itGrandesConsumidores.next();

						idCategoriaAtual = consumoFaixaAreaCategoria.getCategoria().getId();

						if(idCategoriaAnterior == null || !idCategoriaAnterior.equals(idCategoriaAtual)){
							idCategoriaAnterior = idCategoriaAtual;
							sequencialOcorrenciaCategoria = 1;
						}else if(idCategoriaAnterior.equals(idCategoriaAtual)){
							sequencialOcorrenciaCategoria = sequencialOcorrenciaCategoria + 1;
						}

						// Incrementa o Contador de linhas do tipo 2
						quantidadeTipo2 = quantidadeTipo2 + 1;
						quantidadeTipo2Aux = quantidadeTipo2Aux + 1;

						// #################### GRANDES CONSUMIDORES ####################

						arquivoTxtLinha.append("2");

						arquivoTxtLinha.append(Util.completaString("", 15));

						arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(2, idCategoriaAtual.toString()
										+ sequencialOcorrenciaCategoria));

						arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(7, consumoFaixaAreaCategoria.getConsumoEstimadoArea()
										.toString()));

						arquivoTxtLinha.append(System.getProperty("line.separator"));
					}
				}else{
					throw new ControladorException("atencao.grandes_consumidores_inexistentes");
				}

			}// If do gerarHeader

			// Incrementa o Contador de linhas do tipo 3
			quantidadeTipo3 = quantidadeTipo3 + 1;
			quantidadeTipo3Aux = quantidadeTipo3Aux + 1;

			// #################### MOVIMENTO ROTEIRO EMPRESA - TIPO 3 ####################

			arquivoTxtLinha.append("3");

			arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(16, inscricaoNaoFormatada));

			arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(10, movimentoRoteiroEmpresa.getImovel().getId().toString()));

			arquivoTxtLinha.append(Util.completaString(movimentoRoteiroEmpresa.getNomeCliente(), 28));

			// [SB0003 – Obter Grupo do Imóvel]
			arquivoTxtLinha.append(this.obterGrupoImovel(movimentoRoteiroEmpresa.getImovel().getId()));

			// arquivoTxtLinha.append(this.obterEsferaPoderClienteResponsavel(movimentoRoteiroEmpresa.getImovel().getId()));
			// [SB0004] – Obter Tipo do Responsável GCS
			arquivoTxtLinha.append(this.obterTipoResponsavel(movimentoRoteiroEmpresa.getImovel().getId()));

			if(movimentoRoteiroEmpresa.getIndicadorEmissao().equals(ConstantesSistema.SIM)){
				if(this.getControladorImovel().isIndicadorDebitoAutomaticoImovel(movimentoRoteiroEmpresa.getImovel().getId())){
					arquivoTxtLinha.append("7");
				}else{
					arquivoTxtLinha.append("2");
				}
			}else{
				// Verifica se o grupo de faturamento é para exibir o indicador de débito
				// automático(7)
				// quando for gerado o arquivo de pré-faturamento através de parâmetro
				// P_GRUPO_EXIBIR_INDICADOR_DEBITO_AUTOMATICO_PRE_FATURAMENTO
				boolean isGrupoDebitoAutomaticoPreFaturamento = false;

				String parmGruposIndicadorDebitoAutomatico = (String) ParametroFaturamento.P_GRUPO_EXIBIR_INDICADOR_DEBITO_AUTOMATICO_PRE_FATURAMENTO
								.executar();

				if(!Util.isVazioOuBranco(parmGruposIndicadorDebitoAutomatico)){
					Integer[] idAtualInt = null;
					String[] idGrupoAtual = parmGruposIndicadorDebitoAutomatico.split(",");

					idAtualInt = new Integer[idGrupoAtual.length];

					for(int i = 0; i < idGrupoAtual.length; i++){
						idAtualInt[i] = Util.obterInteger(idGrupoAtual[i]);

						if(idAtualInt[i].intValue() == idFaturamentoGrupo.intValue()){
							isGrupoDebitoAutomaticoPreFaturamento = true;
						}
					}
				}

				// Caso o grupo tenha que exibir o indicador de débito automático no arquivo
				if(isGrupoDebitoAutomaticoPreFaturamento
								&& this.getControladorImovel().isIndicadorDebitoAutomaticoImovel(
												movimentoRoteiroEmpresa.getImovel().getId())){
					arquivoTxtLinha.append("7");
				}else{
					arquivoTxtLinha.append("1");
				}
			}

			arquivoTxtLinha.append(Util.completarStringComValorEsquerda(this.obterIdClienteResponsavel(movimentoRoteiroEmpresa.getImovel()
							.getId()), "0", 9));

			arquivoTxtLinha.append(movimentoRoteiroEmpresa.getLigacaoAguaSituacao().getId().toString());

			arquivoTxtLinha.append(movimentoRoteiroEmpresa.getLigacaoEsgotoSituacao().getId().toString());

			if(this.isImovelGrandeConsumidor(movimentoRoteiroEmpresa.getImovel().getId())){
				arquivoTxtLinha.append("1");
			}else{
				arquivoTxtLinha.append("0");
			}

			if(movimentoRoteiroEmpresa.getLigacaoAguaSituacao().getId().equals(LigacaoAguaSituacao.CORTADO)){
				arquivoTxtLinha.append("1");
			}else{
				arquivoTxtLinha.append("0");
			}

			if(!Util.isVazioOuBranco(movimentoRoteiroEmpresa.getIdAnormalidadeConsumo2())
							&& !Util.isVazioOuBranco(movimentoRoteiroEmpresa.getIdAnormalidadeConsumo1())){
				if(!movimentoRoteiroEmpresa.getIdAnormalidadeConsumo2().equals(ConsumoAnormalidade.ESTOURO_CONSUMO)
								&& movimentoRoteiroEmpresa.getIdAnormalidadeConsumo1().equals(ConsumoAnormalidade.ESTOURO_CONSUMO)){

					arquivoTxtLinha.append(Util.completarStringComValorEsquerda("1", "0", 2));
				}else if(movimentoRoteiroEmpresa.getIdAnormalidadeConsumo2().equals(ConsumoAnormalidade.ESTOURO_CONSUMO)
								&& movimentoRoteiroEmpresa.getIdAnormalidadeConsumo1().equals(ConsumoAnormalidade.ESTOURO_CONSUMO)){

					arquivoTxtLinha.append(Util.completarStringComValorEsquerda("2", "0", 2));
				}else{

					arquivoTxtLinha.append(Util.completarStringComValorEsquerda("0", "0", 2));
				}
			}else{

				arquivoTxtLinha.append(Util.completarStringComValorEsquerda("0", "0", 2));
			}

			if(!Util.isVazioOuBranco(movimentoRoteiroEmpresa.getLocalInstalacaoHidrometro())){
				HidrometroLocalInstalacao hli = repositorioMicromedicao
								.pesquisarHidrometroLocalInstalacaoPorDescricaoAbreviada(movimentoRoteiroEmpresa
												.getLocalInstalacaoHidrometro());
				if(hli != null){
					arquivoTxtLinha.append(hli.getId().toString());
				}else{
					arquivoTxtLinha.append("0");
				}
			}else{
				arquivoTxtLinha.append("0");
			}

			if(movimentoRoteiroEmpresa.getNumeroConsumo1() != null){
				arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(5, movimentoRoteiroEmpresa.getNumeroConsumo1().toString()));
			}else{
				arquivoTxtLinha.append("00000");
			}
			if(movimentoRoteiroEmpresa.getNumeroConsumo2() != null){
				arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(5, movimentoRoteiroEmpresa.getNumeroConsumo2().toString()));
			}else{
				arquivoTxtLinha.append("00000");
			}
			if(movimentoRoteiroEmpresa.getNumeroConsumo3() != null){
				arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(5, movimentoRoteiroEmpresa.getNumeroConsumo3().toString()));
			}else{
				arquivoTxtLinha.append("00000");
			}
			if(movimentoRoteiroEmpresa.getNumeroConsumo4() != null){
				arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(5, movimentoRoteiroEmpresa.getNumeroConsumo4().toString()));
			}else{
				arquivoTxtLinha.append("00000");
			}
			if(movimentoRoteiroEmpresa.getNumeroConsumo5() != null){
				arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(5, movimentoRoteiroEmpresa.getNumeroConsumo5().toString()));
			}else{
				arquivoTxtLinha.append("00000");
			}
			if(movimentoRoteiroEmpresa.getNumeroConsumo6() != null){
				arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(5, movimentoRoteiroEmpresa.getNumeroConsumo6().toString()));
			}else{
				arquivoTxtLinha.append("00000");
			}

			arquivoTxtLinha.append(movimentoRoteiroEmpresa.getCepEnderecoImovel());

			arquivoTxtLinha.append(System.getProperty("line.separator"));

			// Incrementa o Contador de linhas do tipo 4
			quantidadeTipo4 = quantidadeTipo4 + 1;
			quantidadeTipo4Aux = quantidadeTipo4Aux + 1;

			// #################### MOVIMENTO ROTEIRO EMPRESA - TIPO 4 ####################

			arquivoTxtLinha.append("4");

			arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(16, inscricaoNaoFormatada));

			arquivoTxtLinha.append(Util.completaString(movimentoRoteiroEmpresa.getEnderecoImovel(), 50));

			arquivoTxtLinha.append(Util.completaString(movimentoRoteiroEmpresa.getComplementoEnderecoImovel(), 13));

			arquivoTxtLinha.append(Util.completaString(this.obterMaiorMenorAnoAnteriorReferenciaComContaVencidaFaturamentoImediato(
							movimentoRoteiroEmpresa.getImovel().getId(), anoMesFaturamento), 8));

			arquivoTxtLinha.append(this.obterUltimosDozeMesesAnterioresReferenciaComContaVencida(movimentoRoteiroEmpresa.getImovel()
							.getId(), anoMesFaturamento));

			if(movimentoRoteiroEmpresa.getDataVencimento() != null){

				arquivoTxtLinha.append(Util.completarStringComValorEsquerda(String.valueOf(Util.getDiaMes(movimentoRoteiroEmpresa
								.getDataVencimento())), "0", 2));
			}else{

				arquivoTxtLinha.append("00");
			}

			// Indica que deverá ser calculado Imposto Federal para o Imóvel. (Obter a partir de
			// MREM_ICIPF em MOVIMENTO_ROTEIRO_EMPRESA). Caso nulo, atribui 2.
			if(movimentoRoteiroEmpresa.getIndicadorImpostoFederal() != null){
				arquivoTxtLinha.append(movimentoRoteiroEmpresa.getIndicadorImpostoFederal());
			}else{
				arquivoTxtLinha.append("2");
			}

			// Indicador de Declaração Quitação Anual de Débitos
			// [FS0012 - Obter Indicador de Quitação Anual de Débitos Arquivo Modelo 1]
			arquivoTxtLinha.append(obterIndicadorQuitacaoAnualDebitosModelo1(movimentoRoteiroEmpresa));

			arquivoTxtLinha.append(System.getProperty("line.separator"));

			// Incrementa o Contador de linhas do tipo 5
			quantidadeTipo5 = quantidadeTipo5 + 1;
			quantidadeTipo5Aux = quantidadeTipo5Aux + 1;

			// #################### MOVIMENTO ROTEIRO EMPRESA - TIPO 5 ####################

			arquivoTxtLinha.append("5");

			arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(16, inscricaoNaoFormatada));

			// Quantidade de Economias
			if(!Util.isVazioOuBranco(movimentoRoteiroEmpresa.getQuantidadeEconomiasResidencial())){
				arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(3, movimentoRoteiroEmpresa.getQuantidadeEconomiasResidencial()
								.toString()));
			}else{
				arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(3, "0"));
			}

			if(!Util.isVazioOuBranco(movimentoRoteiroEmpresa.getQuantidadeEconomiasComercial())){
				arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(3, movimentoRoteiroEmpresa.getQuantidadeEconomiasComercial()
								.toString()));
			}else{
				arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(3, "0"));
			}

			if(!Util.isVazioOuBranco(movimentoRoteiroEmpresa.getQuantidadeEconomiasIndustrial())){
				arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(3, movimentoRoteiroEmpresa.getQuantidadeEconomiasIndustrial()
								.toString()));
			}else{
				arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(3, "0"));
			}

			if(!Util.isVazioOuBranco(movimentoRoteiroEmpresa.getQuantidadeEconomiasPublica())){
				arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(3, movimentoRoteiroEmpresa.getQuantidadeEconomiasPublica()
								.toString()));
			}else{
				arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(3, "0"));
			}

			// ------------------------------------------------------------
			if(!Util.isVazioOuBranco(movimentoRoteiroEmpresa.getNumeroHidrometro())){
				arquivoTxtLinha.append(Util.completaString(movimentoRoteiroEmpresa.getNumeroHidrometro(), 12));
			}else{
				arquivoTxtLinha.append(Util.completaString("", 12));
			}
			// ------------------------------------------------------------

			if(!Util.isVazioOuBranco(movimentoRoteiroEmpresa.getNumeroConsumoMedio())){
				arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(6, movimentoRoteiroEmpresa.getNumeroConsumoMedio().toString()));
			}else{
				arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(6, "0"));
			}

			if(!Util.isVazioOuBranco(movimentoRoteiroEmpresa.getNumeroLeituraAnterior())){
				arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(6, movimentoRoteiroEmpresa.getNumeroLeituraAnterior().toString()));
			}else{
				arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(6, "0"));
			}

			if(!Util.isVazioOuBranco(movimentoRoteiroEmpresa.getDataLeituraAnterior())){
				arquivoTxtLinha.append(Util.completaString(Util.formatarDataAAAAMMDD(movimentoRoteiroEmpresa.getDataLeituraAnterior()), 8));
			}else{
				arquivoTxtLinha.append(Util.completaString("", 8));
			}

			if(!Util.isVazioOuBranco(movimentoRoteiroEmpresa.getPercentualEsgoto())){
				arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(3, Util.formatarBigDecimalParaString(movimentoRoteiroEmpresa
								.getPercentualEsgoto(), 0)));
			}else{
				arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(3, "0"));
			}

			if(!Util.isVazioOuBranco(movimentoRoteiroEmpresa.getIdLeituraSituacaoAnterior())){

				if(montarMapaLeituraSituacaoAnteriorModelo1()
								.containsKey(movimentoRoteiroEmpresa.getIdLeituraSituacaoAnterior().toString())){

					System.out.println(movimentoRoteiroEmpresa.getIdLeituraSituacaoAnterior()
									+ "-"
									+ montarMapaLeituraSituacaoAnteriorModelo1().get(
													movimentoRoteiroEmpresa.getIdLeituraSituacaoAnterior().toString()));

					arquivoTxtLinha.append(montarMapaLeituraSituacaoAnteriorModelo1().get(
									movimentoRoteiroEmpresa.getIdLeituraSituacaoAnterior().toString()));
				}else{
					arquivoTxtLinha.append("0");
				}

			}else{
				arquivoTxtLinha.append("0");
			}

			if(!Util.isVazioOuBranco(movimentoRoteiroEmpresa.getNumeroConsumoFixoEsgoto())){
				arquivoTxtLinha
								.append(Util
												.adicionarZerosEsquedaNumero(5, movimentoRoteiroEmpresa.getNumeroConsumoFixoEsgoto()
																.toString()));
			}else{
				arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(5, "0"));
			}

			arquivoTxtLinha.append("100");

			if(!Util.isVazioOuBranco(movimentoRoteiroEmpresa.getNumeroConsumoMinimo())){
				arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(5, movimentoRoteiroEmpresa.getNumeroConsumoMinimo().toString()));
			}else{
				arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(5, "0"));
			}

			if(!Util.isVazioOuBranco(movimentoRoteiroEmpresa.getIdAnormalidadeConsumo2())
							&& !Util.isVazioOuBranco(movimentoRoteiroEmpresa.getIdAnormalidadeConsumo1())
							&& movimentoRoteiroEmpresa.getIdAnormalidadeConsumo2().equals(ConsumoAnormalidade.ESTOURO_CONSUMO)
							&& movimentoRoteiroEmpresa.getIdAnormalidadeConsumo1().equals(ConsumoAnormalidade.ESTOURO_CONSUMO)){
				arquivoTxtLinha.append("1");
			}else{
				arquivoTxtLinha.append("0");
			}

			if(movimentoRoteiroEmpresa.getNumeroLeituraAnterior() != null && movimentoRoteiroEmpresa.getLeituraFaturada1() != null
							&& movimentoRoteiroEmpresa.getLeituraFaturada2() != null
							&& movimentoRoteiroEmpresa.getNumeroLeituraAnterior().equals(movimentoRoteiroEmpresa.getLeituraFaturada1())
							&& movimentoRoteiroEmpresa.getNumeroLeituraAnterior().equals(movimentoRoteiroEmpresa.getLeituraFaturada2())){
				arquivoTxtLinha.append("X");
			}else{
				arquivoTxtLinha.append(Util.completaString("", 1));
			}

			if(!Util.isVazioOuBranco(movimentoRoteiroEmpresa.getConsumoTarifa())){
				// arquivoTxtLinha.append(Util.completaString(movimentoRoteiroEmpresa.getConsumoTarifa().getId().toString(),
				// 2));
				arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(2, movimentoRoteiroEmpresa.getConsumoTarifa().getId().toString()));
			}else{
				// arquivoTxtLinha.append(Util.completaString("", 2));
				arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(2, "0"));
			}

			arquivoTxtLinha.append(System.getProperty("line.separator"));

			// #################### MOVIMENTO ROTEIRO EMPRESA - TIPO 6 ####################

			/*
			 * Trecho alterado pois a especificação estava errada. Não era pra gerar uma linha para
			 * cada grupo de 3 e sim caso apenas tivesse um débito já seria gerado uma linha,
			 * agrupando a cada três débitos.
			 */
			if((movimentoRoteiroEmpresa.getDescricaoRubrica1() != null && !movimentoRoteiroEmpresa.getDescricaoRubrica1().equals(""))
							|| (movimentoRoteiroEmpresa.getDescricaoRubrica2() != null && !movimentoRoteiroEmpresa.getDescricaoRubrica2()
											.equals(""))
							|| (movimentoRoteiroEmpresa.getDescricaoRubrica3() != null && !movimentoRoteiroEmpresa.getDescricaoRubrica3()
											.equals(""))){

				// Incrementa o Contador de linhas do tipo 6
				quantidadeTipo6 = quantidadeTipo6 + 1;
				quantidadeTipo6Aux = quantidadeTipo6Aux + 1;

				arquivoTxtLinha.append("6");

				arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(16, inscricaoNaoFormatada));

				if(movimentoRoteiroEmpresa.getDescricaoRubrica1() != null && !movimentoRoteiroEmpresa.getDescricaoRubrica1().equals("")){

					// 1. Conjunto 01
					arquivoTxtLinha.append(Util.completaString(movimentoRoteiroEmpresa.getDescricaoRubrica1(), 3));
					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(2, movimentoRoteiroEmpresa.getNumeroPrestacaoRubrica1() + ""));
					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(2, movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica1()
									+ ""));
					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(11, Util.formatarBigDecimalParaString(movimentoRoteiroEmpresa
									.getValorRubrica1(), 2)));
					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(6, movimentoRoteiroEmpresa.getReferenciaRubrica1().toString()));
					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(6, "0"));
				}else{

					// Completa com zeros
					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(30, ""));
				}

				if(movimentoRoteiroEmpresa.getDescricaoRubrica2() != null && !movimentoRoteiroEmpresa.getDescricaoRubrica2().equals("")){

					// 2. Conjunto 02
					arquivoTxtLinha.append(Util.completaString(movimentoRoteiroEmpresa.getDescricaoRubrica2(), 3));
					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(2, movimentoRoteiroEmpresa.getNumeroPrestacaoRubrica2() + ""));
					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(2, movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica2()
									+ ""));
					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(11, Util.formatarBigDecimalParaString(movimentoRoteiroEmpresa
									.getValorRubrica2(), 2)));
					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(6, movimentoRoteiroEmpresa.getReferenciaRubrica2().toString()));
					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(6, "0"));
				}else{

					// Completa com zeros
					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(30, ""));
				}

				if(movimentoRoteiroEmpresa.getDescricaoRubrica3() != null && !movimentoRoteiroEmpresa.getDescricaoRubrica3().equals("")){

					// 3. Conjunto 03
					arquivoTxtLinha.append(Util.completaString(movimentoRoteiroEmpresa.getDescricaoRubrica3(), 3));
					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(2, movimentoRoteiroEmpresa.getNumeroPrestacaoRubrica3() + ""));
					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(2, movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica3()
									+ ""));
					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(11, Util.formatarBigDecimalParaString(movimentoRoteiroEmpresa
									.getValorRubrica3(), 2)));
					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(6, movimentoRoteiroEmpresa.getReferenciaRubrica3().toString()));
					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(6, "0"));

				}else{

					// Completa com zeros
					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(30, ""));
				}

				arquivoTxtLinha.append(System.getProperty("line.separator"));
			}

			if((movimentoRoteiroEmpresa.getDescricaoRubrica4() != null && !movimentoRoteiroEmpresa.getDescricaoRubrica4().equals(""))
							|| (movimentoRoteiroEmpresa.getDescricaoRubrica5() != null && !movimentoRoteiroEmpresa.getDescricaoRubrica5()
											.equals(""))
							|| (movimentoRoteiroEmpresa.getDescricaoRubrica6() != null && !movimentoRoteiroEmpresa.getDescricaoRubrica6()
											.equals(""))){

				// Incrementa o Contador de linhas do tipo 6
				quantidadeTipo6 = quantidadeTipo6 + 1;
				quantidadeTipo6Aux = quantidadeTipo6Aux + 1;

				arquivoTxtLinha.append("6");

				arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(16, inscricaoNaoFormatada));

				if(movimentoRoteiroEmpresa.getDescricaoRubrica4() != null && !movimentoRoteiroEmpresa.getDescricaoRubrica4().equals("")){

					// 1. Conjunto 04
					arquivoTxtLinha.append(Util.completaString(movimentoRoteiroEmpresa.getDescricaoRubrica4(), 3));
					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(2, movimentoRoteiroEmpresa.getNumeroPrestacaoRubrica4() + ""));
					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(2, movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica4()
									+ ""));
					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(11, Util.formatarBigDecimalParaString(movimentoRoteiroEmpresa
									.getValorRubrica4(), 2)));
					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(6, movimentoRoteiroEmpresa.getReferenciaRubrica4().toString()));
					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(6, "0"));
				}else{

					// Completa com zeros
					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(30, ""));
				}

				if(movimentoRoteiroEmpresa.getDescricaoRubrica5() != null && !movimentoRoteiroEmpresa.getDescricaoRubrica5().equals("")){

					// 2. Conjunto 05
					arquivoTxtLinha.append(Util.completaString(movimentoRoteiroEmpresa.getDescricaoRubrica5(), 3));
					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(2, movimentoRoteiroEmpresa.getNumeroPrestacaoRubrica5() + ""));
					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(2, movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica5()
									+ ""));
					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(11, Util.formatarBigDecimalParaString(movimentoRoteiroEmpresa
									.getValorRubrica5(), 2)));
					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(6, movimentoRoteiroEmpresa.getReferenciaRubrica5().toString()));
					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(6, "0"));
				}else{

					// Completa com zeros
					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(30, ""));
				}

				if(movimentoRoteiroEmpresa.getDescricaoRubrica6() != null && !movimentoRoteiroEmpresa.getDescricaoRubrica6().equals("")){

					// 3. Conjunto 06
					arquivoTxtLinha.append(Util.completaString(movimentoRoteiroEmpresa.getDescricaoRubrica6(), 3));
					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(2, movimentoRoteiroEmpresa.getNumeroPrestacaoRubrica6() + ""));
					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(2, movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica6()
									+ ""));
					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(11, Util.formatarBigDecimalParaString(movimentoRoteiroEmpresa
									.getValorRubrica6(), 2)));
					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(6, movimentoRoteiroEmpresa.getReferenciaRubrica6().toString()));
					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(6, "0"));
				}else{

					// Completa com zeros
					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(30, ""));
				}

				arquivoTxtLinha.append(System.getProperty("line.separator"));
			}

			if((movimentoRoteiroEmpresa.getDescricaoRubrica7() != null && !movimentoRoteiroEmpresa.getDescricaoRubrica7().equals(""))
							|| (movimentoRoteiroEmpresa.getDescricaoRubrica8() != null && !movimentoRoteiroEmpresa.getDescricaoRubrica8()
											.equals(""))
							|| (movimentoRoteiroEmpresa.getDescricaoRubrica9() != null && !movimentoRoteiroEmpresa.getDescricaoRubrica9()
											.equals(""))){

				// Incrementa o Contador de linhas do tipo 6
				quantidadeTipo6 = quantidadeTipo6 + 1;
				quantidadeTipo6Aux = quantidadeTipo6Aux + 1;

				arquivoTxtLinha.append("6");

				arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(16, inscricaoNaoFormatada));

				if(movimentoRoteiroEmpresa.getDescricaoRubrica7() != null && !movimentoRoteiroEmpresa.getDescricaoRubrica7().equals("")){

					// 01. Conjunto 07
					arquivoTxtLinha.append(Util.completaString(movimentoRoteiroEmpresa.getDescricaoRubrica7(), 3));
					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(2, movimentoRoteiroEmpresa.getNumeroPrestacaoRubrica7() + ""));
					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(2, movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica7()
									+ ""));
					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(11, Util.formatarBigDecimalParaString(movimentoRoteiroEmpresa
									.getValorRubrica7(), 2)));
					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(6, movimentoRoteiroEmpresa.getReferenciaRubrica7().toString()));
					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(6, "0"));
				}else{

					// Completa com zeros
					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(30, ""));
				}

				if(movimentoRoteiroEmpresa.getDescricaoRubrica8() != null && !movimentoRoteiroEmpresa.getDescricaoRubrica8().equals("")){

					// 02. Conjunto 08
					arquivoTxtLinha.append(Util.completaString(movimentoRoteiroEmpresa.getDescricaoRubrica8(), 3));
					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(2, movimentoRoteiroEmpresa.getNumeroPrestacaoRubrica8() + ""));
					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(2, movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica8()
									+ ""));
					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(11, Util.formatarBigDecimalParaString(movimentoRoteiroEmpresa
									.getValorRubrica8(), 2)));
					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(6, movimentoRoteiroEmpresa.getReferenciaRubrica8().toString()));
					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(6, "0"));
				}else{

					// Completa com zeros
					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(30, ""));
				}

				if(movimentoRoteiroEmpresa.getDescricaoRubrica9() != null && !movimentoRoteiroEmpresa.getDescricaoRubrica9().equals("")){

					// 03. Conjunto 09
					arquivoTxtLinha.append(Util.completaString(movimentoRoteiroEmpresa.getDescricaoRubrica9(), 3));
					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(2, movimentoRoteiroEmpresa.getNumeroPrestacaoRubrica9() + ""));
					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(2, movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica9()
									+ ""));
					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(11, Util.formatarBigDecimalParaString(movimentoRoteiroEmpresa
									.getValorRubrica9(), 2)));
					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(6, movimentoRoteiroEmpresa.getReferenciaRubrica9().toString()));
					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(6, "0"));
				}else{

					// Completa com zeros
					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(30, ""));
				}

				arquivoTxtLinha.append(System.getProperty("line.separator"));

			}

			if((movimentoRoteiroEmpresa.getDescricaoRubrica10() != null && !movimentoRoteiroEmpresa.getDescricaoRubrica10().equals(""))
							|| (movimentoRoteiroEmpresa.getDescricaoRubrica11() != null && !movimentoRoteiroEmpresa.getDescricaoRubrica11()
											.equals(""))
							|| (movimentoRoteiroEmpresa.getDescricaoRubrica12() != null && !movimentoRoteiroEmpresa.getDescricaoRubrica12()
											.equals(""))){

				// Incrementa o Contador de linhas do tipo 6
				quantidadeTipo6 = quantidadeTipo6 + 1;
				quantidadeTipo6Aux = quantidadeTipo6Aux + 1;

				arquivoTxtLinha.append("6");

				arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(16, inscricaoNaoFormatada));

				if(movimentoRoteiroEmpresa.getDescricaoRubrica10() != null && !movimentoRoteiroEmpresa.getDescricaoRubrica10().equals("")){

					// 01. Conjunto 10
					arquivoTxtLinha.append(Util.completaString(movimentoRoteiroEmpresa.getDescricaoRubrica10(), 3));
					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(2, movimentoRoteiroEmpresa.getNumeroPrestacaoRubrica10() + ""));
					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(2, movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica10()
									+ ""));
					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(11, Util.formatarBigDecimalParaString(movimentoRoteiroEmpresa
									.getValorRubrica10(), 2)));
					arquivoTxtLinha
									.append(Util
													.adicionarZerosEsquedaNumero(6, movimentoRoteiroEmpresa.getReferenciaRubrica10()
																	.toString()));
					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(6, "0"));
				}else{

					// Completa com zeros
					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(30, ""));
				}

				if(movimentoRoteiroEmpresa.getDescricaoRubrica11() != null && !movimentoRoteiroEmpresa.getDescricaoRubrica11().equals("")){

					// 02. Conjunto 11
					arquivoTxtLinha.append(Util.completaString(movimentoRoteiroEmpresa.getDescricaoRubrica11(), 3));
					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(2, movimentoRoteiroEmpresa.getNumeroPrestacaoRubrica11() + ""));
					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(2, movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica11()
									+ ""));
					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(11, Util.formatarBigDecimalParaString(movimentoRoteiroEmpresa
									.getValorRubrica11(), 2)));
					arquivoTxtLinha
									.append(Util
													.adicionarZerosEsquedaNumero(6, movimentoRoteiroEmpresa.getReferenciaRubrica11()
																	.toString()));
					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(6, "0"));
				}else{

					// Completa com zeros
					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(30, ""));
				}

				if(movimentoRoteiroEmpresa.getDescricaoRubrica12() != null && !movimentoRoteiroEmpresa.getDescricaoRubrica12().equals("")){

					// 03. Conjunto 12
					arquivoTxtLinha.append(Util.completaString(movimentoRoteiroEmpresa.getDescricaoRubrica12(), 3));
					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(2, movimentoRoteiroEmpresa.getNumeroPrestacaoRubrica12() + ""));
					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(2, movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica12()
									+ ""));
					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(11, Util.formatarBigDecimalParaString(movimentoRoteiroEmpresa
									.getValorRubrica12(), 2)));
					arquivoTxtLinha
									.append(Util
													.adicionarZerosEsquedaNumero(6, movimentoRoteiroEmpresa.getReferenciaRubrica12()
																	.toString()));
					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(6, "0"));
				}else{

					// Completa com zeros
					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(30, ""));
				}

				arquivoTxtLinha.append(System.getProperty("line.separator"));
			}

			if((movimentoRoteiroEmpresa.getDescricaoRubrica13() != null && !movimentoRoteiroEmpresa.getDescricaoRubrica13().equals(""))
							|| (movimentoRoteiroEmpresa.getDescricaoRubrica14() != null && !movimentoRoteiroEmpresa.getDescricaoRubrica14()
											.equals(""))
							|| (movimentoRoteiroEmpresa.getDescricaoRubrica15() != null && !movimentoRoteiroEmpresa.getDescricaoRubrica15()
											.equals(""))){

				// Incrementa o Contador de linhas do tipo 6
				quantidadeTipo6 = quantidadeTipo6 + 1;
				quantidadeTipo6Aux = quantidadeTipo6Aux + 1;

				arquivoTxtLinha.append("6");

				arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(16, inscricaoNaoFormatada));

				if(movimentoRoteiroEmpresa.getDescricaoRubrica13() != null && !movimentoRoteiroEmpresa.getDescricaoRubrica13().equals("")){

					// 01. Conjunto 13
					arquivoTxtLinha.append(Util.completaString(movimentoRoteiroEmpresa.getDescricaoRubrica13(), 3));
					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(2, movimentoRoteiroEmpresa.getNumeroPrestacaoRubrica13() + ""));
					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(2, movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica13()
									+ ""));
					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(11, Util.formatarBigDecimalParaString(movimentoRoteiroEmpresa
									.getValorRubrica13(), 2)));
					arquivoTxtLinha
									.append(Util
													.adicionarZerosEsquedaNumero(6, movimentoRoteiroEmpresa.getReferenciaRubrica13()
																	.toString()));
					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(6, "0"));
				}else{

					// Completa com zeros
					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(30, ""));
				}

				if(movimentoRoteiroEmpresa.getDescricaoRubrica14() != null && !movimentoRoteiroEmpresa.getDescricaoRubrica14().equals("")){

					// 02. Conjunto 14
					arquivoTxtLinha.append(Util.completaString(movimentoRoteiroEmpresa.getDescricaoRubrica14(), 3));
					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(2, movimentoRoteiroEmpresa.getNumeroPrestacaoRubrica14() + ""));
					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(2, movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica14()
									+ ""));
					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(11, Util.formatarBigDecimalParaString(movimentoRoteiroEmpresa
									.getValorRubrica14(), 2)));
					arquivoTxtLinha
									.append(Util
													.adicionarZerosEsquedaNumero(6, movimentoRoteiroEmpresa.getReferenciaRubrica14()
																	.toString()));
					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(6, "0"));
				}else{

					// Completa com zeros
					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(30, ""));
				}

				if(movimentoRoteiroEmpresa.getDescricaoRubrica15() != null && !movimentoRoteiroEmpresa.getDescricaoRubrica15().equals("")){

					// 03. Conjunto 15
					arquivoTxtLinha.append(Util.completaString(movimentoRoteiroEmpresa.getDescricaoRubrica15(), 3));
					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(2, movimentoRoteiroEmpresa.getNumeroPrestacaoRubrica15() + ""));
					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(2, movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica15()
									+ ""));
					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(11, Util.formatarBigDecimalParaString(movimentoRoteiroEmpresa
									.getValorRubrica15(), 2)));
					arquivoTxtLinha
									.append(Util
													.adicionarZerosEsquedaNumero(6, movimentoRoteiroEmpresa.getReferenciaRubrica15()
																	.toString()));
					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(6, "0"));
				}else{

					// Completa com zeros
					arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(30, ""));
				}

				arquivoTxtLinha.append(System.getProperty("line.separator"));
			}

			movimentoRoteiroEmpresa.setIndicadorGeracaoArquivoTexto(ConstantesSistema.SIM);
			movimentoRoteiroEmpresa.setIdUsuarioGeracaoArquivoTexto(Usuario.USUARIO_BATCH.getId());
			movimentoRoteiroEmpresa.setTempoGeracaoArquivoTexto(new Date());

			this.getControladorUtil().atualizar(movimentoRoteiroEmpresa);

			// Dados para o Relatório da Geração do Pré-faturamento (Resumo)

			// Setor Comercial
			codigoSetorComercial = movimentoRoteiroEmpresa.getCodigoSetorComercial();

			// Débitos
			valorDebitos = movimentoRoteiroEmpresa.getValorDebitos();

			// Créditos
			valorCredito = movimentoRoteiroEmpresa.getValorCreditos();

			// Localidade
			localidade = movimentoRoteiroEmpresa.getLocalidade();

			idLocalidade = localidade.getId();

			if(geracaoPreFatResumoHelperMap.containsKey(idLocalidade)){
				geracaoPreFatResumoHelper = geracaoPreFatResumoHelperMap.get(idLocalidade);

				colecaoSetorComercial = geracaoPreFatResumoHelper.getColecaoSetorComercial();

				if(codigoSetorComercial != null && !colecaoSetorComercial.contains(codigoSetorComercial)){
					colecaoSetorComercial.add(codigoSetorComercial);
				}

				quantidadeFaturados = geracaoPreFatResumoHelper.getQuantidadeFaturados();
				quantidadeMedidos = geracaoPreFatResumoHelper.getQuantidadeMedidos();
				quantidadeNaoMedidos = geracaoPreFatResumoHelper.getQuantidadeNaoMedidos();

				// Consumidores Faturados
				quantidadeFaturados = quantidadeFaturados + 1;

				if(!Util.isVazioOuBranco(movimentoRoteiroEmpresa.getNumeroHidrometro())){
					// Consumidores Medidos
					quantidadeMedidos = quantidadeMedidos + 1;
				}else{
					// Consumidores Não Medidos
					quantidadeNaoMedidos = quantidadeNaoMedidos + 1;
				}

				quantidadeTotalTipo1 = geracaoPreFatResumoHelper.getQuantidadeTipo1();
				quantidadeTotalTipo2 = geracaoPreFatResumoHelper.getQuantidadeTipo2();
				quantidadeTotalTipo3 = geracaoPreFatResumoHelper.getQuantidadeTipo3();
				quantidadeTotalTipo4 = geracaoPreFatResumoHelper.getQuantidadeTipo4();
				quantidadeTotalTipo5 = geracaoPreFatResumoHelper.getQuantidadeTipo5();
				quantidadeTotalTipo6 = geracaoPreFatResumoHelper.getQuantidadeTipo6();

				quantidadeTotalTipo1 = quantidadeTotalTipo1 + quantidadeTipo1Aux;
				quantidadeTotalTipo2 = quantidadeTotalTipo2 + quantidadeTipo2Aux;
				quantidadeTotalTipo3 = quantidadeTotalTipo3 + quantidadeTipo3Aux;
				quantidadeTotalTipo4 = quantidadeTotalTipo4 + quantidadeTipo4Aux;
				quantidadeTotalTipo5 = quantidadeTotalTipo5 + quantidadeTipo5Aux;
				quantidadeTotalTipo6 = quantidadeTotalTipo6 + quantidadeTipo6Aux;

				valorTotalDebitos = geracaoPreFatResumoHelper.getValorTotalDebitos();

				if(valorDebitos != null){
					valorTotalDebitos = valorTotalDebitos.add(valorDebitos);
				}

				valorTotalCreditos = geracaoPreFatResumoHelper.getValorTotalCreditos();

				if(valorCredito != null){
					valorTotalCreditos = valorTotalCreditos.add(valorCredito);
				}
			}else{
				geracaoPreFatResumoHelper = new RelatorioOcorrenciaGeracaoPreFatResumoHelper();

				colecaoSetorComercial = new ArrayList<Integer>();
				colecaoSetorComercial.add(codigoSetorComercial);

				// Consumidores Faturados
				quantidadeFaturados = 1;

				if(!Util.isVazioOuBranco(movimentoRoteiroEmpresa.getNumeroHidrometro())){
					// Consumidores Medidos
					quantidadeMedidos = 1;
					quantidadeNaoMedidos = 0;
				}else{
					// Consumidores Não Medidos
					quantidadeMedidos = 0;
					quantidadeNaoMedidos = 1;
				}

				quantidadeTotalTipo1 = quantidadeTipo1Aux;
				quantidadeTotalTipo2 = quantidadeTipo2Aux;
				quantidadeTotalTipo3 = quantidadeTipo3Aux;
				quantidadeTotalTipo4 = quantidadeTipo4Aux;
				quantidadeTotalTipo5 = quantidadeTipo5Aux;
				quantidadeTotalTipo6 = quantidadeTipo6Aux;

				if(valorDebitos == null){
					valorDebitos = BigDecimal.ZERO;
				}

				valorTotalDebitos = valorDebitos;

				if(valorCredito == null){
					valorCredito = BigDecimal.ZERO;
				}

				valorTotalCreditos = valorCredito;
			}

			geracaoPreFatResumoHelper.setColecaoSetorComercial(colecaoSetorComercial);

			geracaoPreFatResumoHelper.setQuantidadeFaturados(quantidadeFaturados);
			geracaoPreFatResumoHelper.setQuantidadeMedidos(quantidadeMedidos);
			geracaoPreFatResumoHelper.setQuantidadeNaoMedidos(quantidadeNaoMedidos);

			geracaoPreFatResumoHelper.setQuantidadeTipo1(quantidadeTotalTipo1);
			geracaoPreFatResumoHelper.setQuantidadeTipo2(quantidadeTotalTipo2);
			geracaoPreFatResumoHelper.setQuantidadeTipo3(quantidadeTotalTipo3);
			geracaoPreFatResumoHelper.setQuantidadeTipo4(quantidadeTotalTipo4);
			geracaoPreFatResumoHelper.setQuantidadeTipo5(quantidadeTotalTipo5);
			geracaoPreFatResumoHelper.setQuantidadeTipo6(quantidadeTotalTipo6);

			geracaoPreFatResumoHelper.setValorTotalDebitos(valorTotalDebitos);
			geracaoPreFatResumoHelper.setValorTotalCreditos(valorTotalCreditos);

			geracaoPreFatResumoHelperMap.put(idLocalidade, geracaoPreFatResumoHelper);
		}// Fim do while(itMovimentoRoteiroEmpresa.hasNext())

		// Dados para o Relatório da Geração do Pré-faturamento (Resumo)
		this.iniciarProcessamentoRelatorioOcorrenciaGeracaoPreFatResumo(idFaturamentoGrupo, anoMesFaturamento, idFuncionalidadeIniciada,
						geracaoPreFatResumoHelperMap, dtVencimentoHeader);

		// #################### TRAILLER ####################

		arquivoTxtLinha.append("9");

		arquivoTxtLinha.append("999999999999999");

		arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(6, quantidadeTipo1 + ""));

		arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(6, quantidadeTipo2 + ""));

		arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(6, quantidadeTipo3 + ""));

		arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(6, quantidadeTipo4 + ""));

		arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(6, quantidadeTipo5 + ""));

		arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(6, quantidadeTipo6 + ""));

		arquivoTxtLinha.append(System.getProperty("line.separator"));

		retornoArquivo[0] = arquivoTxtLinha;
		retornoArquivo[1] = nomeArquivo;

		return retornoArquivo;
	}

	/**
	 * [UC3012] Gerar Arquivo Texto Faturamento Imediato
	 * [FS0012] - Obter Indicador de Quitação Anual de Débitos Arquivo Modelo 1
	 * 
	 * @author Anderson Italo
	 * @since 16/04/2014
	 */
	private String obterIndicadorQuitacaoAnualDebitosModelo1(MovimentoRoteiroEmpresa movimentoRoteiroEmpresa) throws ControladorException{

		// Atribuir o valor 0 (zero) ao Indicador de Mensagem de Quitação do Débito
		String informarQuitacaoDebitosAnual = ConstantesSistema.ZERO.toString();

		String parametroAnoBaseQuitacao = ParametroFaturamento.P_ANO_BASE_DECLARACAO_QUITACAO_DEBITO_ANUAL.executar();

		if(!Util.isVazioOuBranco(parametroAnoBaseQuitacao)
						&& !parametroAnoBaseQuitacao.equals(ConstantesSistema.SEM_ANO_BASE_DECLARACAO_QUITACAO_DEBITO_ANUAL)){

			FiltroQuitacaoDebitoAnual filtroQuitacaoDebitoAnual = new FiltroQuitacaoDebitoAnual();
			filtroQuitacaoDebitoAnual.adicionarParametro(new ParametroSimples(FiltroQuitacaoDebitoAnual.IMOVEL_ID,
							movimentoRoteiroEmpresa.getImovel().getId()));
			filtroQuitacaoDebitoAnual.adicionarParametro(new ParametroSimples(FiltroQuitacaoDebitoAnual.ANO_REFERENCIA,
							parametroAnoBaseQuitacao));

			Collection<QuitacaoDebitoAnual> colecaoQuitacaoDebitosAnual = getControladorUtil().pesquisar(filtroQuitacaoDebitoAnual,
							QuitacaoDebitoAnual.class.getName());

			if(!Util.isVazioOrNulo(colecaoQuitacaoDebitosAnual)){


				Cliente clienteResponsavel = getControladorImovel().pesquisarClienteResponsavelImovel(
								movimentoRoteiroEmpresa.getImovel().getId());

				// Caso o imóvel possua cliente responsável com CPF ou CNPJ
				if(clienteResponsavel != null
								&& (!Util.isVazioOuBranco(clienteResponsavel.getCpf()) || !Util.isVazioOuBranco(clienteResponsavel
												.getCnpj()))){

					informarQuitacaoDebitosAnual = ConstantesSistema.UM.toString();
				}else{

					Cliente clienteUsuario = getControladorImovel().pesquisarClienteUsuarioImovel(
									movimentoRoteiroEmpresa.getImovel().getId());

					// Caso contrário, Caso o imóvel possua cliente usuário com CPF ou CNPJ
					if(clienteUsuario != null
									&& (!Util.isVazioOuBranco(clienteUsuario.getCpf()) || !Util.isVazioOuBranco(clienteUsuario
													.getCnpj()))){

						informarQuitacaoDebitosAnual = ConstantesSistema.UM.toString();
					}else{

						Cliente clientePropietario = getControladorImovel().pesquisarClientePropietarioImovel(
										movimentoRoteiroEmpresa.getImovel().getId());

						// Caso contrário, Caso o imóvel possua cliente propietário com CPF ou CNPJ
						if(clientePropietario != null
										&& (!Util.isVazioOuBranco(clientePropietario.getCpf()) || !Util
														.isVazioOuBranco(clientePropietario.getCnpj()))){

							informarQuitacaoDebitosAnual = ConstantesSistema.UM.toString();
						}
					}
				}

				if(informarQuitacaoDebitosAnual.equals(ConstantesSistema.SIM.toString())){

					// O sistema indica a emissão da declaração anual de quitação de débitos
					QuitacaoDebitoAnual quitacaoDebitoAnual = (QuitacaoDebitoAnual) Util
									.retonarObjetoDeColecao(colecaoQuitacaoDebitosAnual);
					quitacaoDebitoAnual.setIndicadorImpressao(ConstantesSistema.SIM);
					quitacaoDebitoAnual.setUltimaAlteracao(new Date());

					getControladorUtil().atualizar(quitacaoDebitoAnual);
				}
			}
		}

		return informarQuitacaoDebitosAnual;
	}

	/**
	 * Método pesquisarDadosQualidadeAgua
	 * <p>
	 * Esse método implementa SB0002 – Obter Dados da Qualidade Água
	 * </p>
	 * RASTREIO: [UC3012][SB0002]
	 * 
	 * @param anoMesFaturamento
	 * @param movimentoRoteiroEmpresa
	 * @return
	 * @throws ControladorException
	 * @author Marlos Ribeiro
	 * @since 16/04/2013
	 */
	private QualidadeAgua pesquisarDadosQualidadeAgua(Integer anoMesFaturamento, Integer localidadeId, Integer localidadeEloId)
					throws ControladorException{

		QualidadeAgua qualidadeAgua;
		qualidadeAgua = this.pesquisarQualidadeAguaPorLocalidadeAnoMesFaturamento(anoMesFaturamento, localidadeId);
		if(qualidadeAgua == null && localidadeEloId != null){
			qualidadeAgua = this.pesquisarQualidadeAguaPorLocalidadeAnoMesFaturamento(anoMesFaturamento, localidadeEloId);
		}
		return qualidadeAgua;
	}

	/**
	 * Relatório de Ocorrência da Geração do Pré-Faturamento (Resumo)
	 * 
	 * @author Hebert Falcão
	 * @date 06/04/2012
	 */
	private void iniciarProcessamentoRelatorioOcorrenciaGeracaoPreFatResumo(Integer idFaturamentoGrupo, Integer anoMesReferencia,
					Integer idFuncionalidadeIniciada,
					Map<Integer, RelatorioOcorrenciaGeracaoPreFatResumoHelper> geracaoPreFatResumoHelperMap, Date dataVencimento)
					throws ControladorException{

		String idFaturamentoGrupoStr = Integer.toString(idFaturamentoGrupo);

		String anoMesReferenciaStr = "";

		if(anoMesReferencia != null){
			anoMesReferenciaStr = Util.formatarAnoMesSemBarraParaMesAnoComBarra(anoMesReferencia);
		}

		Usuario usuarioProcessamento = null;

		if(idFuncionalidadeIniciada != null){
			usuarioProcessamento = this.getControladorBatch().obterUsuarioFuncionalidadeIniciada(idFuncionalidadeIniciada);
		}else{
			usuarioProcessamento = Usuario.USUARIO_BATCH;
		}

		String dataVencimentoStr = "";

		if(dataVencimento != null){
			dataVencimentoStr = Util.formatarData(dataVencimento);
		}

		RelatorioOcorrenciaGeracaoPreFatResumo relatorio = new RelatorioOcorrenciaGeracaoPreFatResumo(usuarioProcessamento);
		relatorio.addParametro("tipoFormatoRelatorio", TarefaRelatorio.TIPO_PDF);
		relatorio.addParametro("anoMesReferencia", anoMesReferenciaStr);
		relatorio.addParametro("idGrupoFaturamento", idFaturamentoGrupoStr);
		relatorio.addParametro("geracaoPreFatResumoHelperMap", geracaoPreFatResumoHelperMap);
		relatorio.addParametro("dataVencimento", dataVencimentoStr);
		relatorio.addParametro("referenciaTarifa", anoMesReferenciaStr);

		this.getControladorBatch().iniciarProcessoRelatorio(relatorio);
	}

	/**
	 * [UC3012 – Gerar Arquivo Texto Faturamento Imediado]
	 * [SB0001] – Gerar Arquivo Texto para Faturamento Imediato.
	 * 
	 * @author Ailton Sousa
	 * @date 19/08/2011
	 */
	private List pesquisarTodosDebitoTipos() throws ControladorException{

		List retorno;

		try{
			retorno = this.repositorioFaturamento.pesquisarTodosDebitoTipos();
		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}

		return retorno;
	}

	/**
	 * [UC3012 – Gerar Arquivo Texto Faturamento Imediado]
	 * [SB0001] – Gerar Arquivo Texto para Faturamento Imediato.
	 * Retorna a Descrição da ContaMensagem mais recente de acordo com o AnoMesFaturamento e com o
	 * GrupoFaturamento.
	 * 
	 * @author Ailton Sousa
	 * @date 19/08/2011
	 */
	private Object[] pesquisarDescricaoContaMensagemMaisRecente(Integer anoMesFaturamento, Integer idFaturamentoGrupo)
					throws ControladorException{

		Object[] retorno;

		try{
			retorno = this.repositorioFaturamento.pesquisarDescricaoContaMensagemMaisRecente(anoMesFaturamento, idFaturamentoGrupo);

			if(Util.isVazioOrNulo(retorno)){
				retorno = this.repositorioFaturamento.pesquisarDescricaoContaMensagemMaisRecente(anoMesFaturamento, null);
			}
		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}

		return retorno;
	}

	/**
	 * [UC3012 – Gerar Arquivo Texto Faturamento Imediado]
	 * [SB0001] – Gerar Arquivo Texto para Faturamento Imediato.
	 * Retorna a Maior Data de Vencimento da Conta de FaturamentoAtividadeCronogramaRota.
	 * 
	 * @author Ailton Sousa
	 * @date 19/08/2011
	 */
	private Date retornarMaiorDtContaVencimentoPorRotaIdFaturamentoAnoMesFaturamento(String idsRotas, Integer idFaturamentoGrupo,
					Integer anoMesFaturamento) throws ControladorException{

		Date retorno = null;
		List<Date> listaData = new ArrayList<Date>();

		try{
			for(String idsRotasComLimite : Util.retornaListaStringComLimiteItemConsulta(idsRotas)){
				listaData.add(this.repositorioFaturamento.retornarMaiorDtContaVencimentoPorRotaIdFaturamentoAnoMesFaturamento(
								idsRotasComLimite, idFaturamentoGrupo, anoMesFaturamento));
			}
		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}

		// Verifica se é a maior data
		if(Util.isNaoNuloBrancoZero(listaData)){
			retorno = listaData.get(ConstantesSistema.ZERO.intValue());
			listaData.remove(ConstantesSistema.ZERO.intValue());

			for(Date data : listaData){
				if(Util.compararData(retorno, data) != ConstantesSistema.SIM.intValue()){
					retorno = data;
				}
			}
		}

		return retorno;
	}

	/**
	 * [UC3012 – Gerar Arquivo Texto Faturamento Imediado]
	 * [SB0001] – Gerar Arquivo Texto para Faturamento Imediato.
	 * [SB0002] – Obter Dados da Qualidade Água
	 * Retorna a entidade QualidadeAgua de acordo com o AnoMesFaturamento e com a Localidade.
	 * 
	 * @author Ailton Sousa
	 * @date 19/08/2011
	 */
	private QualidadeAgua pesquisarQualidadeAguaPorLocalidadeAnoMesFaturamento(Integer anoMesFaturamento, Integer idLocalidade)
					throws ControladorException{

		QualidadeAgua retorno;

		try{
			retorno = this.repositorioFaturamento.pesquisarQualidadeAguaPorLocalidadeAnoMesFaturamento(anoMesFaturamento, idLocalidade);
		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}

		return retorno;
	}

	/**
	 * [UC3012 – Gerar Arquivo Texto Faturamento Imediado]
	 * [SB0001] – Gerar Arquivo Texto para Faturamento Imediato.
	 * Retorna os Dados Dos Grandes Conumidores.
	 * 
	 * @author Ailton Sousa
	 * @date 20/08/2011
	 */
	private Collection obterDadosGrandesConsumidores() throws ControladorException{

		Collection retorno;

		try{
			retorno = this.repositorioFaturamento.obterDadosGrandesConsumidores();
		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}

		return retorno;
	}

	/**
	 * [UC3012 – Gerar Arquivo Texto Faturamento Imediado]
	 * Obter o maior e o menor ano anterior ao ano de referencia do faturamento, com conta vencida.
	 * 
	 * @author Ailton Sousa
	 * @date 22/08/2011
	 * @param idImovel
	 * @param anoFaturamento
	 * @throws ErroRepositorioException
	 */
	private Object[] obterMaiorMenorAnoAnteriorReferenciaComContaVencida(Integer idImovel, Integer anoFaturamento)
					throws ControladorException{

		Object[] retorno;

		try{
			retorno = this.repositorioFaturamento.obterMaiorMenorAnoAnteriorReferenciaComContaVencida(idImovel, anoFaturamento);
		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}

		return retorno;
	}

	/**
	 * [UC3012 – Gerar Arquivo Texto Faturamento Imediado]
	 * Obter o maior e o menor ano anterior ao ano de referencia do faturamento, com conta vencida.
	 * 
	 * @author Ailton Sousa
	 * @date 23/08/2011
	 * @param idImovel
	 * @param anoFaturamento
	 * @return
	 * @throws ControladorException
	 */
	private String obterMaiorMenorAnoAnteriorReferenciaComContaVencidaFaturamentoImediato(Integer idImovel, Integer anoMesFaturamento)
					throws ControladorException{

		String retorno = "";
		String maiorAno = null;
		String menorAno = null;
		Integer anoFaturamento = Util.converterStringParaInteger(anoMesFaturamento.toString().substring(0, 4));

		Object[] maiorMenorAno = this.obterMaiorMenorAnoAnteriorReferenciaComContaVencida(idImovel, anoFaturamento);

		if(maiorMenorAno != null){
			maiorAno = (String) maiorMenorAno[0];
			menorAno = (String) maiorMenorAno[1];

			if(maiorAno != null && menorAno != null && maiorAno.equals(menorAno)){
				retorno = menorAno;
			}else if(maiorAno != null && menorAno != null && !maiorAno.equals(menorAno)){
				retorno = menorAno + maiorAno;
			}
		}

		return retorno;
	}

	/**
	 * [UC3012 – Gerar Arquivo Texto Faturamento Imediado]
	 * Obtem a Esfera de Poder do Cliente Responsável.
	 * 
	 * @author Ailton Sousa
	 * @date 22/08/2011
	 * @param idImovel
	 * @return
	 * @throws ControladorException
	 */
	private String obterIdClienteResponsavel(Integer idImovel) throws ControladorException{

		Integer idClienteResponsavel = null;

		try{
			idClienteResponsavel = repositorioClienteImovel.retornaIdClienteResponsavel(idImovel);
		}catch(ErroRepositorioException e){
			e.printStackTrace();
			throw new ControladorException("erro.sistema", e);
		}

		if(idClienteResponsavel == null){
			idClienteResponsavel = Util.converterStringParaInteger("0");
		}

		return idClienteResponsavel.toString();
	}

	/**
	 * [UC3012 – Gerar Arquivo Texto Faturamento Imediado]
	 * Verifica se o Imovel é um grande consumidor
	 * 
	 * @author Ailton Sousa
	 * @date 22/08/2011
	 * @param idImovel
	 * @return
	 * @throws ControladorException
	 */
	private boolean isImovelGrandeConsumidor(Integer idImovel) throws ControladorException{

		boolean retorno = false;
		ImovelPerfil imovelPerfil = null;

		imovelPerfil = this.getControladorImovel().obterImovelPerfil(idImovel);

		if(imovelPerfil != null && imovelPerfil.getId().equals(ImovelPerfil.GRANDE)){
			retorno = true;
		}

		return retorno;
	}

	/**
	 * [UC3012] Gerar Arquivo Texto Faturamento Imediato
	 * [SB0001] – Gerar Arquivo Texto para Faturamento Imediato – Modelo 2
	 * Método responsável pela geração de um arquivo texto para envio do faturamento imediato
	 * 
	 * @author Anderson Italo
	 * @throws Exception
	 * @date 31/05/2012
	 */
	public void execParamGerarArquivoTextoFaturamentoImediatoModelo2(ParametroSistema parametroSistema,
					Integer anoMesReferenciaFaturamento, Integer idFaturamentoGrupo, Integer idFuncionalidadeIniciada, Integer idEmpresa,
					String idsRotas, Collection colecaoMovimentoRoteiroEmpresa) throws Exception{

		this.ejbCreate();
		StringBuffer arquivoEnvio = new StringBuffer();
		SistemaParametro sistemaParametro = getControladorUtil().pesquisarParametrosDoSistema();
		int quantidadeTipo1 = 0;
		int quantidadeTipo2 = 0;
		int quantidadeTipo3 = 0;

		/*
		 * O sistema gera o arquivo de dados do faturamento imediato (g<<FTGR_ID>><<ano/mês do
		 * faturamento recebido no formato MMAAAA>>.CIPFT, onde FTGR_ID deve ter 3 dígitos e zeros à
		 * esquerda de acordo com as seguintes regras
		 */
		String nomeArquivo = "g" + Util.adicionarZerosEsquedaNumero(3, idFaturamentoGrupo.toString())
						+ Util.formatarAnoMesParaMesAnoSemBarra(anoMesReferenciaFaturamento) + ".CIPFT";

		// Grava registro tipo 0 (Header)
		arquivoEnvio.append("0");

		// Completa com espaços em branco
		arquivoEnvio.append(Util.completaString("", 18));

		// Referência do Faturamento
		arquivoEnvio.append(anoMesReferenciaFaturamento.toString());

		// Grupo de Faturamento
		arquivoEnvio.append(Util.completarStringComValorEsquerda(idFaturamentoGrupo.toString(), "0", 3));

		// Percentual do Imposto Federal
		arquivoEnvio.append(Util.completarStringComValorEsquerda(Util.formatarMoedaReal(
						repositorioFaturamento.obterPercentualAliquotaImpostoFederal(anoMesReferenciaFaturamento), 3).replace(",", "")
						.replace(".", ""), "0", 6));

		// Completa com espaços em branco
		arquivoEnvio.append(Util.completaString("", 475));

		// Caractere de controle
		arquivoEnvio.append("#");
		arquivoEnvio.append(System.getProperty("line.separator"));

		// Grava registro tipo 1
		List<Object[]> colecaoTarifas = repositorioFaturamento.pesquisarTarifasArquivoTextoFaturamentoImediato(anoMesReferenciaFaturamento);

		// [FS0004 – Verificar existência de tarifas]
		if(!Util.isVazioOrNulo(colecaoTarifas)){

			// Para cada 30 registros selecionados, o sistema grava um registro de tarifa
			StringBuffer tarifas = new StringBuffer("");
			String tarifasNaoPreenchidas = "";
			int indexTarifas = 0;

			for(int i = 0; i < colecaoTarifas.size(); i++){

				Object[] linhaRetornada = colecaoTarifas.get(i);
				indexTarifas++;

				// Código da Tarifa
				tarifas.append(Util.completarStringComValorEsquerda(linhaRetornada[0].toString(), "0", 2));

				// Código da Categoria
				tarifas.append(Util.completarStringComValorEsquerda(linhaRetornada[1].toString(), "0", 2));

				// Limite Superior da Faixa
				if(linhaRetornada[2] != null && !linhaRetornada[2].toString().equals("")){

					tarifas.append(Util.completarStringComValorEsquerda(linhaRetornada[2].toString(), "0", 5));
				}else{

					tarifas.append(Util.completarStringComValorEsquerda("", "0", 5));
				}

				// Valor da Tarifa
				if(linhaRetornada[3] != null && !linhaRetornada[3].toString().equals("")){

					tarifas.append(Util.completarStringComValorEsquerda(new BigDecimal(linhaRetornada[3].toString()).setScale(2,
									BigDecimal.ROUND_DOWN).toString().replace(".", ""), "0", 7));
				}else{

					tarifas.append(Util.completarStringComValorEsquerda("", "0", 7));
				}

				// Dados das tarifas, ocorrendo 30 vezes
				if((i != 0) && ((indexTarifas % 30) == 0)){

					// Grava registro tipo 1
					arquivoEnvio.append("1");

					// Completa com espaços em branco
					arquivoEnvio.append(Util.completaString("", 18));

					// Grava um registro de tarifas
					arquivoEnvio.append(tarifas.toString());

					// Completa com espaços em branco
					arquivoEnvio.append(Util.completaString("", 10));

					// Caractere de controle
					arquivoEnvio.append("#");
					arquivoEnvio.append(System.getProperty("line.separator"));
					quantidadeTipo1 += 1;

					// Limpa o buffer
					tarifas.delete(0, tarifas.length());

					// Zera o index
					indexTarifas = 0;
				}else if(i == (colecaoTarifas.size() - 1)){

					// Caso não sejam preenchidas as 30 ocorrências, preencher as demais com zeros.
					tarifasNaoPreenchidas = tarifas.toString();
					tarifas = new StringBuffer(Util.completarStringZeroDireita(tarifasNaoPreenchidas.toString(), 480));

					// Grava registro tipo 1
					arquivoEnvio.append("1");

					// Completa com espaços em branco
					arquivoEnvio.append(Util.completaString("", 18));

					// Grava um registro de tarifas
					arquivoEnvio.append(tarifas.toString());

					// Completa com espaços em branco
					arquivoEnvio.append(Util.completaString("", 10));

					// Caractere de controle
					arquivoEnvio.append("#");
					arquivoEnvio.append(System.getProperty("line.separator"));
					quantidadeTipo1 += 1;
				}
			}
		}else{

			throw new ControladorException("atencao.tarifas_vigentes_inexistentes");
		}

		// Grava registro tipo 2 (Anormalidade de Leitura)
		List<LeituraAnormalidade> colecaoLeituraAnormalidade = repositorioFaturamento.pesquisarLeituraAnormalidadeFaturamentoImediato();

		// Para cada 13 registros selecionados, o sistema grava um registro de anormalidades
		StringBuffer anormalidades = new StringBuffer("");
		String anormalidadesNaoPreenchidas = "";
		LeituraAnormalidade leituraAnormalidade = null;
		int indexAnormalidades = 0;

		for(int i = 0; i < colecaoLeituraAnormalidade.size(); i++){

			leituraAnormalidade = colecaoLeituraAnormalidade.get(i);
			indexAnormalidades++;

			// Código
			anormalidades.append(Util.completarStringComValorEsquerda(leituraAnormalidade.getId().toString(), "0", 2));

			// Descrição
			anormalidades.append(Util.completaString(leituraAnormalidade.getDescricao(), 30));

			// Ação consumo sem leitura
			anormalidades.append(Util.completarStringComValorEsquerda(leituraAnormalidade.getLeituraAnormalidadeConsumoSemleitura().getId()
							.toString(), "0", 1));

			// Ação consumo com leitura
			anormalidades.append(Util.completarStringComValorEsquerda(leituraAnormalidade.getLeituraAnormalidadeConsumoComleitura().getId()
							.toString(), "0", 1));

			// Ação leitura sem leitura
			anormalidades.append(Util.completarStringComValorEsquerda(leituraAnormalidade.getLeituraAnormalidadeLeituraSemleitura().getId()
							.toString(), "0", 1));

			// Ação leitura com leitura
			anormalidades.append(Util.completarStringComValorEsquerda(leituraAnormalidade.getLeituraAnormalidadeLeituraComleitura().getId()
							.toString(), "0", 1));

			// Indicador de emissão na exceção de leitura
			if(leituraAnormalidade.getIndicadorListagemAnormalidadeRelatorios() != null
							&& leituraAnormalidade.getIndicadorListagemAnormalidadeRelatorios().equals(ConstantesSistema.SIM)){

				anormalidades.append(leituraAnormalidade.getIndicadorListagemAnormalidadeRelatorios().toString());
			}else{

				anormalidades.append(ConstantesSistema.ZERO.toString());
			}

			// Dados das anormalidades, ocorrendo 30 vezes
			if((i != 0) && ((indexAnormalidades % 13) == 0)){

				// Grava registro tipo 2
				arquivoEnvio.append("2");

				// Completa com espaços em branco
				arquivoEnvio.append(Util.completaString("", 18));

				// Grava um registro de anormalidades
				arquivoEnvio.append(anormalidades.toString());

				// Completa espaço vazio com zero
				arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 9));

				// Caractere de controle
				arquivoEnvio.append("#");
				arquivoEnvio.append(System.getProperty("line.separator"));
				quantidadeTipo2 += 1;

				// Limpa o buffer
				anormalidades.delete(0, anormalidades.length());

				// Zera o index de anormalidades
				indexAnormalidades = 0;
			}else if(i == (colecaoLeituraAnormalidade.size() - 1)){

				// Caso não sejam preenchidas as 13 ocorrências, preencher as demais com zeros.
				anormalidadesNaoPreenchidas = anormalidades.toString();
				anormalidades = new StringBuffer(Util.completarStringZeroDireita(anormalidadesNaoPreenchidas.toString(), 481));

				// Grava registro tipo 2
				arquivoEnvio.append("2");

				// Completa com espaços em branco
				arquivoEnvio.append(Util.completaString("", 18));

				// Grava um registro de anoramalidades
				arquivoEnvio.append(anormalidades.toString());

				// Completa com espaços em branco
				arquivoEnvio.append(Util.completaString("", 9));

				// Caractere de controle
				arquivoEnvio.append("#");
				arquivoEnvio.append(System.getProperty("line.separator"));
				quantidadeTipo2 += 1;
			}
		}

		// Grava registro tipo 3 (Capa de Setores)
		List<Object[]> colecaoSetorComercial = pesquisarSetorComercialFaturamentoImediatoComLimiteRotas(idsRotas);

		Object[] setorComercial = null;
		Rota rota = (Rota) getControladorUtil().pesquisar(new Integer(idsRotas.split(",")[0].toString()), Rota.class, false);
		Integer codigoEmpresaFebraban = null;

		for(int i = 0; i < colecaoSetorComercial.size(); i++){

			setorComercial = colecaoSetorComercial.get(i);

			// Grava registro tipo 3
			arquivoEnvio.append("3");

			// Completa com espaços em branco
			arquivoEnvio.append(Util.completaString("", 18));

			// Código da localidade
			arquivoEnvio.append(Util.completarStringComValorEsquerda(setorComercial[0].toString(), "0", 3));

			// Descrição da localidade
			arquivoEnvio.append(Util.completaString(setorComercial[1].toString(), 25));

			// Código do setor comercial
			arquivoEnvio.append(Util.completarStringComValorEsquerda(setorComercial[2].toString(), "0", 3));

			// Limite consumo grande consumidor
			if(setorComercial[3] != null){

				arquivoEnvio.append(Util.completarStringComValorEsquerda(setorComercial[3].toString(), "0", 3));
			}else{

				arquivoEnvio.append(Util
								.completarStringComValorEsquerda(sistemaParametro.getMenorConsumoGrandeUsuario().toString(), "0", 3));
			}

			// Índice da correção
			arquivoEnvio.append("000");

			// Pesquisa Mensagem da Conta por Setor Comercial
			Object[] contaMensagem = getControladorFaturamento().pesquisarContaMensagem(anoMesReferenciaFaturamento, idFaturamentoGrupo,
							Util.obterInteger(setorComercial[6].toString()), Util.obterInteger(setorComercial[0].toString()),
							Util.obterInteger(setorComercial[5].toString()));

			// Mensagem da Conta
			if(!Util.isVazioOrNulo(contaMensagem)){

				// Mensagem 1
				if(contaMensagem[0] != null && !contaMensagem[0].equals("")){

					arquivoEnvio.append(Util.completaString(contaMensagem[0].toString(), 64));
				}else{

					arquivoEnvio.append(Util.completaString("", 64));
				}

				// Mensagem 2
				if(contaMensagem[1] != null && !contaMensagem[1].equals("")){

					arquivoEnvio.append(Util.completaString(contaMensagem[1].toString(), 64));
				}else{

					arquivoEnvio.append(Util.completaString("", 64));
				}
			}else{

				// Mensagem 1
				arquivoEnvio.append(Util.completaString("", 64));

				// Mensagem 2
				arquivoEnvio.append(Util.completaString("", 64));
			}

			// Indicador de ajuste
			if(rota.getIndicadorAjusteConsumo() != null){

				// Caso seja igual a 1 atribuir "S"
				if(rota.getIndicadorAjusteConsumo().equals(ConstantesSistema.SIM)){

					arquivoEnvio.append("S");
				}else{

					// caso contrário atribuir "N"
					arquivoEnvio.append("N");
				}
			}else{

				// caso contrário atribuir "N"
				arquivoEnvio.append("N");
			}

			// Indicador de colapso
			arquivoEnvio.append("N");

			// Mensagem 3
			// OC881539 - Mensagem na conta fixo - Remover mensagem, colocar
			// descricaoContaMensagem03.
			// arquivoEnvio.append(Util.completaString("PREVENIR A DENGUE E UMA ACAO DE TODOS. PARTICIPE",
			// 64));
			// Mensagem 2
			if(contaMensagem[2] != null && !contaMensagem[2].equals("")){

				arquivoEnvio.append(Util.completaString(contaMensagem[2].toString(), 64));
			}else{

				arquivoEnvio.append(Util.completaString("", 64));
			}

			// [SB0002 – Obter Dados da Qualidade Água]
			QualidadeAgua qualidadeAgua = repositorioFaturamento.pesquisarQualidadeAguaPorLocalidadeAnoMesFaturamento(
							anoMesReferenciaFaturamento, Util.obterInteger(setorComercial[0].toString()));

			if(qualidadeAgua == null){

				qualidadeAgua = repositorioFaturamento.pesquisarQualidadeAguaPorLocalidadeAnoMesFaturamento(anoMesReferenciaFaturamento,
								Util.obterInteger(setorComercial[5].toString()));
			}

			// if(qualidadeAgua == null && (setorComercial[4] != null &&
			// !setorComercial[4].toString().equals(""))){
			//
			// qualidadeAgua =
			// repositorioFaturamento.pesquisarQualidadeAguaPorLocalidadeAnoMesFaturamento(anoMesReferenciaFaturamento,
			// Util.obterInteger(setorComercial[4].toString()));
			// }

			// [FS0003 – Verificar existência de dados da qualidade de água]
			if(qualidadeAgua == null){

				// Caso não encontre dados da qualidade de água, atribuir o valor zero aos dados da
				// dados da qualidade de água
				arquivoEnvio.append(Util.completarStringZeroDireita("", 54));

				// Conclusão
				arquivoEnvio.append(Util.completaString("", 60));
			}else{

				// Turbidez Exigida
				if(qualidadeAgua.getNumeroAmostrasExigidasTurbidez() != null){

					arquivoEnvio.append(Util.completarStringComValorEsquerda(qualidadeAgua.getNumeroAmostrasExigidasTurbidez().toString(),
									"0", 3));
				}else{

					arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 3));
				}

				// Turbidez Realizado
				if(qualidadeAgua != null && qualidadeAgua.getNumeroAmostrasRealizadasTurbidez() != null){

					arquivoEnvio.append(Util.completarStringComValorEsquerda(
									qualidadeAgua.getNumeroAmostrasRealizadasTurbidez().toString(), "0", 3));
				}else{

					arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 3));
				}

				// Turbidez Atendido
				if(qualidadeAgua != null && qualidadeAgua.getNumeroAmostrasConformesTurbidez() != null){

					arquivoEnvio.append(Util.completarStringComValorEsquerda(qualidadeAgua.getNumeroAmostrasConformesTurbidez().toString(),
									"0", 3));
				}else{

					arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 3));
				}

				// Cor Exigida
				if(qualidadeAgua.getNumeroAmostrasExigidasCor() != null){

					arquivoEnvio.append(Util.completarStringComValorEsquerda(qualidadeAgua.getNumeroAmostrasExigidasCor().toString(), "0",
									3));
				}else{

					arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 3));
				}

				// Cor Realizado
				if(qualidadeAgua != null && qualidadeAgua.getNumeroAmostrasRealizadasCor() != null){

					arquivoEnvio.append(Util.completarStringComValorEsquerda(qualidadeAgua.getNumeroAmostrasRealizadasCor().toString(),
									"0", 3));
				}else{

					arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 3));
				}

				// Cor Atendido
				if(qualidadeAgua != null && qualidadeAgua.getNumeroAmostrasConformesCor() != null){

					arquivoEnvio.append(Util.completarStringComValorEsquerda(qualidadeAgua.getNumeroAmostrasConformesCor().toString(), "0",
									3));
				}else{

					arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 3));
				}

				// Cloro Exigida
				if(qualidadeAgua.getNumeroAmostrasExigidasCloro() != null){

					arquivoEnvio.append(Util.completarStringComValorEsquerda(qualidadeAgua.getNumeroAmostrasExigidasCloro().toString(),
									"0", 3));
				}else{

					arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 3));
				}

				// Cloro Realizado
				if(qualidadeAgua != null && qualidadeAgua.getNumeroAmostrasRealizadasCloro() != null){

					arquivoEnvio.append(Util.completarStringComValorEsquerda(qualidadeAgua.getNumeroAmostrasRealizadasCloro().toString(),
									"0", 3));
				}else{

					arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 3));
				}

				// Cloro Atendido
				if(qualidadeAgua != null && qualidadeAgua.getNumeroAmostrasConformesCloro() != null){

					arquivoEnvio.append(Util.completarStringComValorEsquerda(qualidadeAgua.getNumeroAmostrasConformesCloro().toString(),
									"0", 3));
				}else{

					arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 3));
				}

				// PH Exigido
				if(qualidadeAgua.getNumeroAmostrasExigidasPh() != null){

					arquivoEnvio.append(Util
									.completarStringComValorEsquerda(qualidadeAgua.getNumeroAmostrasExigidasPh().toString(), "0", 3));
				}else{

					arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 3));
				}

				// PH Realizado
				if(qualidadeAgua != null && qualidadeAgua.getNumeroAmostrasRealizadasPH() != null){

					arquivoEnvio.append(Util.completarStringComValorEsquerda(qualidadeAgua.getNumeroAmostrasRealizadasPH().toString(), "0",
									3));
				}else{

					arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 3));
				}

				// PH Atendido
				if(qualidadeAgua != null && qualidadeAgua.getNumeroAmostrasConformesPH() != null){

					arquivoEnvio.append(Util.completarStringComValorEsquerda(qualidadeAgua.getNumeroAmostrasConformesPH().toString(), "0",
									3));
				}else{

					arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 3));
				}

				// Coliformes Totais Exigida
				if(qualidadeAgua.getNumeroAmostrasExigidasColiformesTotais() != null){

					arquivoEnvio.append(Util.completarStringComValorEsquerda(qualidadeAgua.getNumeroAmostrasExigidasColiformesTotais()
									.toString(), "0", 3));
				}else{

					arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 3));
				}

				// Coliformes Totais Realizado
				if(qualidadeAgua != null && qualidadeAgua.getNumeroAmostrasRealizadasColiformesTotais() != null){

					arquivoEnvio.append(Util.completarStringComValorEsquerda(qualidadeAgua.getNumeroAmostrasRealizadasColiformesTotais()
									.toString(), "0", 3));
				}else{

					arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 3));
				}

				// Coliformes Totais Atendido
				if(qualidadeAgua != null && qualidadeAgua.getNumeroAmostrasConformesColiformesTotais() != null){

					arquivoEnvio.append(Util.completarStringComValorEsquerda(qualidadeAgua.getNumeroAmostrasConformesColiformesTotais()
									.toString(), "0", 3));
				}else{

					arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 3));
				}

				// Coliformes Termo Exigido
				if(qualidadeAgua.getNumeroAmostrasExigidasColiformesTermotolerantes() != null){

					arquivoEnvio.append(Util.completarStringComValorEsquerda(qualidadeAgua
									.getNumeroAmostrasExigidasColiformesTermotolerantes().toString(), "0", 3));
				}else{

					arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 3));
				}

				// Coliformes Termo Realizado
				if(qualidadeAgua != null && qualidadeAgua.getNumeroAmostrasRealizadasColiformesTermotolerantes() != null){

					arquivoEnvio.append(Util.completarStringComValorEsquerda(qualidadeAgua
									.getNumeroAmostrasRealizadasColiformesTermotolerantes().toString(), "0", 3));
				}else{

					arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 3));
				}

				// Coliformes Termo Atendido
				if(qualidadeAgua != null && qualidadeAgua.getNumeroAmostrasConformesColiformesTermotolerantes() != null){

					arquivoEnvio.append(Util.completarStringComValorEsquerda(qualidadeAgua
									.getNumeroAmostrasConformesColiformesTermotolerantes().toString(), "0", 3));
				}else{

					arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 3));
				}

				// Qualidade Água - Conclusão
				if(qualidadeAgua != null && qualidadeAgua.getDescricaoConclusaoAnalisesRealizadas() != null){

					arquivoEnvio.append(Util.completaString(qualidadeAgua.getDescricaoConclusaoAnalisesRealizadas(), 60));
				}else{

					arquivoEnvio.append(Util.completaString("", 60));
				}
			}

			codigoEmpresaFebraban = repositorioMicromedicao.obterCodigoEmpresaConcessionariaLocalidadeVigente(Util
							.obterInteger(setorComercial[0].toString()));

			// [FS0009] – Verificar não existência da associação concessionária x localidade
			if(codigoEmpresaFebraban != null){

				arquivoEnvio.append(Util.completarStringComValorEsquerda(codigoEmpresaFebraban.toString(), "0", 4));
			}else if(sistemaParametro.getCodigoEmpresaFebraban() != null){

				arquivoEnvio.append(Util.completarStringComValorEsquerda(sistemaParametro.getCodigoEmpresaFebraban().toString(), "0", 4));
			}else{

				arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 4));
			}

			// Completa com espaços em branco
			arquivoEnvio.append(Util.completaString("", 141));

			// Caractere de controle
			arquivoEnvio.append("#");
			arquivoEnvio.append(System.getProperty("line.separator"));
			quantidadeTipo3 += 1;
		}

		/*
		 * Para cada movimento de leitura selecionado, grava os registros tipos 4, 5, 6 e 7 (Dados
		 * do imóvel/cliente)
		 */
		MovimentoRoteiroEmpresa movimentoRoteiroEmpresa = null;
		Object[] dadosClienteResponsavel = null;
		DebitoAutomatico debitoAutomatico = null;
		LigacaoAgua ligacaoAgua = null;
		LigacaoEsgoto ligacaoEsgoto = null;
		String endereco = null;
		Cliente clienteUsuario = null;
		HidrometroRelatorioOSHelper hidrometroRelatorioOSHelper = null;
		int[] faixaLeituraEsperada = null;
		Hidrometro hidrometro = null;
		String debitosAnteriores = "";
		BigDecimal valorDebitosAcumulado = BigDecimal.ZERO;
		BigDecimal valorTotalDebitos = BigDecimal.ZERO;
		int mediaConsumo = 0;
		Integer numeroLeituraAnterior = null;
		String parametroQuantidadeDiasVencimentoContaAvisoCorte = ParametroCobranca.P_QUANTIDADE_DIAS_VENCIMENTO_CONTA_AVISO_CORTE
						.executar();
		HashMap<Integer, RelatorioOcorrenciaGeracaoPreFatResumoHelper> geracaoPreFatResumoHelperMap = new HashMap<Integer, RelatorioOcorrenciaGeracaoPreFatResumoHelper>();
		RelatorioOcorrenciaGeracaoPreFatResumoHelper geracaoPreFatResumoHelper = null;
		int quantidadeTipo4 = 0;
		int quantidadeTipo5 = 0;
		int quantidadeTipo6 = 0;
		int quantidadeTipo7 = 0;

		// Usuário
		Usuario usuario = null;

		// Recupera o usuário logado
		if(idFuncionalidadeIniciada != null){
			usuario = this.getControladorBatch().obterUsuarioFuncionalidadeIniciada(idFuncionalidadeIniciada);
		}else{
			// Insere o usario Batch
			usuario = Usuario.USUARIO_BATCH;
		}

		// Ordenar a coleção por mais de um campo (local, setor, quadra, lote e sublote)
		List sortFields = new ArrayList();
		sortFields.add(new BeanComparator("localidade.id"));
		sortFields.add(new BeanComparator("codigoSetorComercial"));
		sortFields.add(new BeanComparator("numeroQuadra"));
		sortFields.add(new BeanComparator("numeroLoteImovel"));
		sortFields.add(new BeanComparator("numeroSubLoteImovel"));

		ComparatorChain multiSort = new ComparatorChain(sortFields);
		Collections.sort((List) colecaoMovimentoRoteiroEmpresa, multiSort);

		Iterator colecaoMovimentoRoteiroEmpresaIterator = colecaoMovimentoRoteiroEmpresa.iterator();

		while(colecaoMovimentoRoteiroEmpresaIterator.hasNext()){

			movimentoRoteiroEmpresa = (MovimentoRoteiroEmpresa) colecaoMovimentoRoteiroEmpresaIterator.next();

			// Limpa as variáveis utilizadas no loop
			dadosClienteResponsavel = null;
			debitoAutomatico = null;
			ligacaoAgua = null;
			endereco = null;
			clienteUsuario = null;
			hidrometroRelatorioOSHelper = null;
			faixaLeituraEsperada = null;
			hidrometro = null;
			debitosAnteriores = "";
			valorDebitosAcumulado = BigDecimal.ZERO;
			valorTotalDebitos = BigDecimal.ZERO;
			mediaConsumo = 0;
			numeroLeituraAnterior = 0;
			ligacaoAgua = (LigacaoAgua) getControladorUtil().pesquisar(movimentoRoteiroEmpresa.getImovel().getId(), LigacaoAgua.class,
							false);
			ligacaoEsgoto = (LigacaoEsgoto) getControladorUtil().pesquisar(movimentoRoteiroEmpresa.getImovel().getId(),
							LigacaoEsgoto.class, false);

			// Grava registro tipo 4
			arquivoEnvio.append("4");

			// Completa com espaços em branco
			arquivoEnvio.append(Util.completaString("", 5));

			// Inscrição
			arquivoEnvio.append(movimentoRoteiroEmpresa.getInscricaoNaoFormatadaArquivoModelo2());

			// Sublote
			arquivoEnvio.append(Util.completarStringComValorEsquerda(movimentoRoteiroEmpresa.getNumeroSubLoteImovel().toString(), "0", 3));

			// Matrícula
			arquivoEnvio.append(Util.completarStringComValorEsquerda(movimentoRoteiroEmpresa.getImovel().getId().toString(), "0", 10));

			// Nome consumidor
			if(movimentoRoteiroEmpresa.getNomeCliente() != null){

				arquivoEnvio.append(Util.completaString(movimentoRoteiroEmpresa.getNomeCliente(), 30));
			}else{

				// Completa com espaços em branco
				arquivoEnvio.append(Util.completaString("", 30));
			}

			// Indicador de Imposto retido
			if(movimentoRoteiroEmpresa.getIndicadorImpostoFederal() != null){

				// Caso seja igual a 1 atribuir "S"
				if(movimentoRoteiroEmpresa.getIndicadorImpostoFederal().equals(ConstantesSistema.SIM)){

					arquivoEnvio.append("S");
				}else{

					// caso contrário atribuir "N"
					arquivoEnvio.append("N");
				}
			}else{

				// caso contrário atribuir "N"
				arquivoEnvio.append("N");
			}

			// Indicador de Emissão
			if(movimentoRoteiroEmpresa.getIndicadorEmissao() != null){

				// Caso seja igual a 1 atribuir "S"
				if(movimentoRoteiroEmpresa.getIndicadorEmissao().equals(ConstantesSistema.SIM)){

					arquivoEnvio.append("S");
				}else{

					// caso contrário atribuir "N"
					arquivoEnvio.append("N");
				}
			}else{

				// caso contrário atribuir "N"
				arquivoEnvio.append("N");
			}

			dadosClienteResponsavel = repositorioImovel.pesquisarInformacoesClienteResponsavel(movimentoRoteiroEmpresa.getImovel().getId());

			if(!Util.isVazioOrNulo(dadosClienteResponsavel)){

				// Código do Responsável
				arquivoEnvio.append(Util.completarStringComValorEsquerda(dadosClienteResponsavel[0].toString(), "0", 6));

				// Nome do Responsável
				arquivoEnvio.append(Util.completaString(dadosClienteResponsavel[1].toString(), 25));
			}else{

				arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 6));
				arquivoEnvio.append(Util.completaString("", 25));
			}

			// Tarifa de Consumo
			arquivoEnvio
							.append(Util.completarStringComValorEsquerda(movimentoRoteiroEmpresa.getConsumoTarifa().getId().toString(),
											"0", 2));

			// Situação da Ligação de Água
			arquivoEnvio.append(Util.completarStringComValorEsquerda(movimentoRoteiroEmpresa.getLigacaoAguaSituacao().getId().toString(),
							"0", 1));

			// Situação da Ligação de Esgoto
			arquivoEnvio.append(Util.completarStringComValorEsquerda(movimentoRoteiroEmpresa.getLigacaoEsgotoSituacao().getId().toString(),
							"0", 1));

			// Indicador de Grande Consumidor
			if(movimentoRoteiroEmpresa.getImovel().getImovelPerfil().getId().equals(ImovelPerfil.GRANDE)
							|| movimentoRoteiroEmpresa.getImovel().getImovelPerfil().getId().equals(ImovelPerfil.GRANDE_NO_MES)){

				arquivoEnvio.append(ConstantesSistema.SIM.toString());
			}else{

				arquivoEnvio.append(ConstantesSistema.ZERO.toString());
			}

			// Consumo Fixo de Água
			if(movimentoRoteiroEmpresa.getNumeroConsumoFixoAgua() != null
							&& movimentoRoteiroEmpresa.getNumeroConsumoFixoAgua().intValue() > 0){

				arquivoEnvio.append(Util.completarStringComValorEsquerda(movimentoRoteiroEmpresa.getNumeroConsumoFixoAgua().toString(),
								"0", 5));
			}else if(ligacaoAgua != null && ligacaoAgua.getNumeroConsumoMinimoAgua() != null){

				arquivoEnvio.append(Util.completarStringComValorEsquerda(ligacaoAgua.getNumeroConsumoMinimoAgua().toString(), "0", 5));
			}else{

				arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 5));
			}

			// Consumo Fixo de Esgoto
			if(movimentoRoteiroEmpresa.getNumeroConsumoFixoEsgoto() != null
							&& movimentoRoteiroEmpresa.getNumeroConsumoFixoEsgoto().intValue() > 0){

				arquivoEnvio.append(Util.completarStringComValorEsquerda(movimentoRoteiroEmpresa.getNumeroConsumoFixoEsgoto().toString(),
								"0", 5));
			}else if(ligacaoEsgoto != null && ligacaoEsgoto.getConsumoMinimo() != null){

				arquivoEnvio.append(Util.completarStringComValorEsquerda(ligacaoEsgoto.getConsumoMinimo().toString(), "0", 5));
			}else{

				arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 5));
			}

			if(movimentoRoteiroEmpresa.getImovel().getIndicadorDebitoConta().equals(ConstantesSistema.SIM)){

				debitoAutomatico = repositorioFaturamento.pesquisarDebitoAutomaticoImovel(movimentoRoteiroEmpresa.getImovel().getId());

				// Código do banco
				arquivoEnvio.append(Util.completarStringComValorEsquerda(debitoAutomatico.getAgencia().getBanco().getId().toString(), "0",
								4));

				// Descrição do banco
				arquivoEnvio.append(Util.completaString(debitoAutomatico.getAgencia().getBanco().getDescricao(), 20));

				// Código da Agência
				arquivoEnvio.append(Util.completarStringComValorEsquerda(debitoAutomatico.getAgencia().getCodigoAgencia(), "0", 5));
			}else{

				// Código do banco
				arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 4));

				// Descrição do banco
				arquivoEnvio.append(Util.completaString("", 20));

				// Código da Agência
				arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 5));
			}

			// <<Inclui>> [UC0085 – Obter Endereço] do imóvel
			endereco = getControladorEndereco().pesquisarEnderecoSemReferencia(movimentoRoteiroEmpresa.getImovel().getId());

			if(endereco.length() <= 45){

				// Endereço1
				arquivoEnvio.append(Util.completaString(endereco, 45));

				// Endereço de Entrega 2
				arquivoEnvio.append(Util.completaString("", 36));
			}else{

				// Endereço1
				arquivoEnvio.append(Util.completaString(endereco.substring(0, 45), 45));

				// Endereço2
				arquivoEnvio.append(Util.completaString(endereco.substring(45), 36));
			}

			// Bairro
			if(movimentoRoteiroEmpresa.getBairroEnderecoImovel() != null){

				arquivoEnvio.append(Util.completaString(movimentoRoteiroEmpresa.getBairroEnderecoImovel(), 20));
			}else{

				arquivoEnvio.append(Util.completaString("", 20));
			}

			// CEP
			if(movimentoRoteiroEmpresa.getCepEnderecoImovel() != null){

				arquivoEnvio.append(Util.completarStringComValorEsquerda(movimentoRoteiroEmpresa.getCepEnderecoImovel(), "0", 8));
			}else{

				arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 8));
			}

			// Local de entrega
			// Verificar se existe endereço alternativo , caso exista obter o municipio.
			if(!movimentoRoteiroEmpresa.getImovel().getImovelContaEnvio().getId().equals(ImovelContaEnvio.ENVIAR_IMOVEL)){

				Integer idMunicipio = this.getControladorEndereco().obterMunicipio(movimentoRoteiroEmpresa.getImovel().getId());

				if(idMunicipio != null){
					arquivoEnvio.append(Util.completarStringComValorEsquerda(idMunicipio.toString(), "0", 3));
				}else{
					arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 3));
				}

			}else{
				arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 3));
			}

			// Setor de entrega
			arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 2));

			// Quadra de entrega
			arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 4));

			// Lote de entrega
			arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 4));

			if(movimentoRoteiroEmpresa.getImovel().getImovelContaEnvio().getId().equals(ImovelContaEnvio.ENVIAR_CLIENTE_RESPONSAVEL)
							|| movimentoRoteiroEmpresa.getImovel().getImovelContaEnvio().getId().equals(
											ImovelContaEnvio.NAO_PAGAVEL_IMOVEL_PAGAVEL_RESPONSAVEL)){

				// Obter o endereço do cliente responsável <<Inclui>> [UC0085 – Obter Endereço]
				String enderecoEntrega = getControladorEndereco().pesquisarEnderecoClienteAbreviado(
								Util.obterInteger(dadosClienteResponsavel[0].toString()), false);

				if(enderecoEntrega.length() <= 45){

					// Endereço Entrega 1
					arquivoEnvio.append(Util.completaString(enderecoEntrega, 45));

					// Endereço de Entrega 2
					arquivoEnvio.append(Util.completaString("", 36));
				}else{

					// Endereço Entrega 1
					arquivoEnvio.append(Util.completaString(enderecoEntrega.substring(0, 45), 45));

					// Endereço Entrega 2
					arquivoEnvio.append(Util.completaString(enderecoEntrega.substring(45), 36));
				}

			}else{

				// Endereço de Entrega1
				arquivoEnvio.append(Util.completaString("", 45));

				// Endereço de Entrega2
				arquivoEnvio.append(Util.completaString("", 36));
			}

			// Obter o endereço do cliente responsável <<Inclui>> [UC0085 – Obter Endereço]
			if(!Util.isVazioOrNulo(dadosClienteResponsavel) && !Util.isVazioOuBranco(dadosClienteResponsavel[0])){

				Object[] enderecoEntregaDividido = getControladorEndereco().pesquisarEnderecoClienteAbreviadoLista(
								Util.obterInteger(dadosClienteResponsavel[0].toString()), false);

				// Bairro de Entrega
				if(enderecoEntregaDividido[3] != null){

					arquivoEnvio.append(Util.completaString(enderecoEntregaDividido[3].toString(), 20));
				}else{

					arquivoEnvio.append(Util.completaString("", 20));
				}

				// CEP de Entrega
				if(enderecoEntregaDividido[4] != null){

					arquivoEnvio.append(Util.completaString(enderecoEntregaDividido[4].toString(), 8));
				}else{

					arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 8));
				}

				// Nome Local de Entrega
				if(enderecoEntregaDividido[5] != null){

					arquivoEnvio.append(Util.completaString(enderecoEntregaDividido[5].toString(), 25));
				}else{

					arquivoEnvio.append(Util.completaString("", 25));
				}
			}else{

				// Bairro de Entrega
				arquivoEnvio.append(Util.completaString("", 20));

				// CEP de Entrega
				arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 8));

				// Nome Local de Entrega
				arquivoEnvio.append(Util.completaString("", 25));
			}

			// Data de Vencimento
			if(movimentoRoteiroEmpresa.getDataVencimento() != null){

				arquivoEnvio.append(Util.formatarDataSemBarra(movimentoRoteiroEmpresa.getDataVencimento()));
			}else{

				arquivoEnvio.append(Util.completaString("", 8));
			}

			// Quantidade de Economias – 4 ocorrências com 3 dígitos cada
			if(movimentoRoteiroEmpresa.getQuantidadeEconomiasResidencial() != null){

				arquivoEnvio.append(Util.completarStringComValorEsquerda(movimentoRoteiroEmpresa.getQuantidadeEconomiasResidencial()
								.toString(), "0", 3));
			}else{

				arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 3));
			}

			if(movimentoRoteiroEmpresa.getQuantidadeEconomiasComercial() != null){

				arquivoEnvio.append(Util.completarStringComValorEsquerda(movimentoRoteiroEmpresa.getQuantidadeEconomiasComercial()
								.toString(), "0", 3));
			}else{

				arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 3));
			}

			if(movimentoRoteiroEmpresa.getQuantidadeEconomiasIndustrial() != null){

				arquivoEnvio.append(Util.completarStringComValorEsquerda(movimentoRoteiroEmpresa.getQuantidadeEconomiasIndustrial()
								.toString(), "0", 3));
			}else{

				arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 3));
			}

			if(movimentoRoteiroEmpresa.getQuantidadeEconomiasPublica() != null){

				arquivoEnvio.append(Util.completarStringComValorEsquerda(
								movimentoRoteiroEmpresa.getQuantidadeEconomiasPublica().toString(), "0", 3));
			}else{

				arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 3));
			}

			// Percentual Tarifa
			arquivoEnvio.append(ConstantesSistema.CEM.toString());

			// Percentual de Esgoto
			if(movimentoRoteiroEmpresa.getPercentualEsgoto() != null){

				arquivoEnvio.append(Util.completarStringComValorEsquerda(movimentoRoteiroEmpresa.getPercentualEsgoto().toString(), "0", 3));
			}else{

				arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 3));
			}

			// Indicador de Emissão Cód. Barras
			if(movimentoRoteiroEmpresa.getImovel().getIndicadorDebitoConta().equals(ConstantesSistema.SIM)){

				arquivoEnvio.append("N");
			}else{

				arquivoEnvio.append("S");
			}

			// Indicador de Medido
			if(movimentoRoteiroEmpresa.getNumeroHidrometro() != null){

				arquivoEnvio.append("S");
			}else{

				arquivoEnvio.append("N");
			}

			boolean dataCorteMaiorIgualDataLeituraAnterior = false;
			int quantidadeDiasEntreDatas = 0;

			if((ligacaoAgua != null && ligacaoAgua.getDataCorte() != null && movimentoRoteiroEmpresa.getDataLeituraAnterior() != null)
							&& ((Util.compararData(ligacaoAgua.getDataCorte(), movimentoRoteiroEmpresa.getDataLeituraAnterior()) == 0) || (Util
											.compararData(ligacaoAgua.getDataCorte(), movimentoRoteiroEmpresa.getDataLeituraAnterior()) == 1))){

				dataCorteMaiorIgualDataLeituraAnterior = true;
				quantidadeDiasEntreDatas = Util.obterQuantidadeDiasEntreDuasDatas(movimentoRoteiroEmpresa.getDataLeituraAnterior(),
								ligacaoAgua.getDataCorte());
			}

			// Indicador de resíduo de corte
			if((movimentoRoteiroEmpresa.getImovel().getLigacaoAguaSituacao().getId().equals(LigacaoAguaSituacao.CORTADO) || movimentoRoteiroEmpresa
							.getImovel().getLigacaoAguaSituacao().getId().equals(LigacaoAguaSituacao.CORTADO_PEDIDO))
							&& (dataCorteMaiorIgualDataLeituraAnterior && quantidadeDiasEntreDatas <= 30)){

				arquivoEnvio.append("S");
			}else{

				arquivoEnvio.append("N");
			}

			// Indicador de boleto
			arquivoEnvio.append("N");

			// Matrícula do macromedidor
			if(movimentoRoteiroEmpresa.getImovel().getIndicadorImovelCondominio() != null
							&& movimentoRoteiroEmpresa.getImovel().getIndicadorImovelCondominio().equals(ConstantesSistema.SIM)){

				arquivoEnvio.append(Util.completarStringComValorEsquerda(movimentoRoteiroEmpresa.getImovel().getId().toString(), "0", 10));
			}else if(movimentoRoteiroEmpresa.getImovel().getImovelCondominio() != null){

				arquivoEnvio.append(Util.completarStringComValorEsquerda(movimentoRoteiroEmpresa.getImovel().getImovelCondominio().getId()
								.toString(), "0", 10));
			}else{

				// Completa espaço vazio com zero
				arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 10));
			}

			// Dados do Usuário/Inquilino
			clienteUsuario = repositorioClienteImovel.retornarDadosClienteUsuario(movimentoRoteiroEmpresa.getImovel().getId());

			if(clienteUsuario != null){

				if(clienteUsuario.getClienteTipo().getIndicadorPessoaFisicaJuridica() != null){

					if(clienteUsuario.getClienteTipo().getIndicadorPessoaFisicaJuridica().equals(ConstantesSistema.SIM)){

						// Tipo de Pessoa (inquilino)
						arquivoEnvio.append("F");

						// CPF/CNPJ (inquilino)
						if(clienteUsuario.getCpf() != null){

							arquivoEnvio.append(Util.completarStringComValorEsquerda(clienteUsuario.getCpf(), "0", 15));
						}else{

							arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 15));
						}
					}else{

						// Tipo de Pessoa (inquilino)
						arquivoEnvio.append("J");

						// CPF/CNPJ (inquilino)
						if(clienteUsuario.getCnpj() != null){

							arquivoEnvio.append(Util.completarStringComValorEsquerda(clienteUsuario.getCnpj(), "0", 15));
						}else{

							arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 15));
						}
					}
				}else{

					// Tipo de Pessoa (inquilino)
					arquivoEnvio.append(Util.completaString("", 1));

					// CPF/CNPJ (inquilino)
					arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 15));
				}
			}else{

				// Tipo de Pessoa (inquilino)
				arquivoEnvio.append(Util.completaString("", 1));

				// CPF/CNPJ (inquilino)
				arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 15));
			}

			// Tarefa de entrega
			arquivoEnvio.append("00");

			// Seqüência de entrega
			arquivoEnvio.append("0000");

			// Indicador Declaração de Quitação
			// [FS0010 – Verificar indicador de mensagem de quitação de débito]

			// Verificar indicador de mensagem de quitação de débito

			// O sistema obtém o ano de referência para a geração da
			// declaração anual de quitação de débitos
			Integer anoBaseDeclaracaoQuitacaoDebitoAnual = Integer
							.valueOf((String) ParametroFaturamento.P_ANO_BASE_DECLARACAO_QUITACAO_DEBITO_ANUAL.executar());

			// 1. Caso o ano de referência seja diferente de “-1” e exista registro de quitação
			// pendente associado ao imóvel
			if(!Util.isVazioOuBranco(anoBaseDeclaracaoQuitacaoDebitoAnual)
							&& !anoBaseDeclaracaoQuitacaoDebitoAnual
											.equals(ConstantesSistema.SEM_ANO_BASE_DECLARACAO_QUITACAO_DEBITO_ANUAL)){

				// Verifica geração da declaração para o grupo no ano de referência
				FiltroQuitacaoDebitoAnual filtroQuitacaoDebitoAnual = new FiltroQuitacaoDebitoAnual();

				filtroQuitacaoDebitoAnual.adicionarParametro(new ParametroSimples(FiltroQuitacaoDebitoAnual.IMOVEL_ID,
								movimentoRoteiroEmpresa.getImovel().getId()));
				filtroQuitacaoDebitoAnual.adicionarParametro(new ParametroSimples(FiltroQuitacaoDebitoAnual.ANO_REFERENCIA,
								anoBaseDeclaracaoQuitacaoDebitoAnual));
				filtroQuitacaoDebitoAnual.adicionarParametro(new ParametroSimples(FiltroQuitacaoDebitoAnual.INDICADOR_IMPRESSAO,
								ConstantesSistema.NAO));

				// Obtém registros na tabela QUITACAO_ANUAL_DEBITO
				Collection<QuitacaoDebitoAnual> colecaoQuitacaoDebitoAnual = (Collection<QuitacaoDebitoAnual>) this.getControladorUtil()
								.pesquisar(filtroQuitacaoDebitoAnual, QuitacaoDebitoAnual.class.getName());

				// 1. Caso o ano de referência seja diferente de “-1” e exista registro de quitação
				// pendente associado ao imóvel
				if(!Util.isVazioOrNulo(colecaoQuitacaoDebitoAnual)){
					// 1.1. Atribuir o valor 1 (um) ao Indicador de Mensagem de Quitação do Débito.
					arquivoEnvio.append("1");

					for(QuitacaoDebitoAnual quitacaoDebitoAnual : colecaoQuitacaoDebitoAnual){
						// 1.2. O sistema indica a emissão da declaração anual de quitação de
						// débitos
						// atualiza a tabela QUITACAO_DEBITO_ANUAL com os seguintes valores:

						// QADB_ICIMPRESSAO >> 1 (sim)
						quitacaoDebitoAnual.setIndicadorImpressao(ConstantesSistema.SIM);

						// QADB_TMULTIMAALTERACAO >> data e hora correntes
						quitacaoDebitoAnual.setUltimaAlteracao(new Date());

						// *********************************************************
						// Inclui o registro na tabela de quitação de débito anual
						this.getControladorUtil().atualizar(quitacaoDebitoAnual);
						// *********************************************************
					}
				}else{
					arquivoEnvio.append("0");
				}
			}
			// 2. Caso contrário, atribuir o valor 0 (zero) ao Indicador de Mensagem de Quitação do
			// Débito.
			else{
				arquivoEnvio.append("0");
			}

			// Código do Responsável
			if(!Util.isVazioOrNulo(dadosClienteResponsavel)){

				arquivoEnvio.append(Util.completarStringComValorEsquerda(dadosClienteResponsavel[0].toString(), "0", 9));
			}else{

				arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 9));
			}

			// Completa com espaços em branco
			arquivoEnvio.append(Util.completaString("", 42));

			// Caractere de controle
			arquivoEnvio.append("#");
			arquivoEnvio.append(System.getProperty("line.separator"));
			quantidadeTipo4 += 1;

			// Caso o imóvel seja medido
			if(!Util.isVazioOuBranco(movimentoRoteiroEmpresa.getNumeroHidrometro())){

				// Grava registro tipo 5
				arquivoEnvio.append("5");

				// Completa com espaços em branco
				arquivoEnvio.append(Util.completaString("", 5));

				// Inscrição
				arquivoEnvio.append(movimentoRoteiroEmpresa.getInscricaoNaoFormatadaArquivoModelo2());

				// Sublote
				arquivoEnvio.append(Util.completarStringComValorEsquerda(movimentoRoteiroEmpresa.getNumeroSubLoteImovel().toString(), "0",
								3));

				// Número do Hidrômetro
				if(movimentoRoteiroEmpresa.getNumeroHidrometro() != null){

					arquivoEnvio.append(Util.completaString(movimentoRoteiroEmpresa.getNumeroHidrometro(), 12));
				}else{

					// Completa com espaços em branco
					arquivoEnvio.append(Util.completaString("", 12));
				}

				// Local de Instalação do Hidrômetro
				if(movimentoRoteiroEmpresa.getLocalInstalacaoHidrometro() != null){

					arquivoEnvio.append(Util.completaString(movimentoRoteiroEmpresa.getLocalInstalacaoHidrometro(), 3));
				}else{

					// Completa com espaços em branco
					arquivoEnvio.append(Util.completaString("", 3));
				}

				if(movimentoRoteiroEmpresa.getMedicaoTipo() != null){

					if(movimentoRoteiroEmpresa.getMedicaoTipo().getId().equals(MedicaoTipo.LIGACAO_AGUA)){

						hidrometroRelatorioOSHelper = getControladorMicromedicao().obterDadosHidrometroPorTipoMedicao(
										movimentoRoteiroEmpresa.getImovel().getId(), MedicaoTipo.LIGACAO_AGUA);

					}else{

						hidrometroRelatorioOSHelper = getControladorMicromedicao().obterDadosHidrometroPorTipoMedicao(
										movimentoRoteiroEmpresa.getImovel().getId(), MedicaoTipo.POCO);
					}

					// Marca do Hidrômetro
					arquivoEnvio.append(Util.completaString(hidrometroRelatorioOSHelper.getHidrometroMarca(), 3));

					// Capacidade do Hidrômetro
					arquivoEnvio.append(Util.completaString(hidrometroRelatorioOSHelper.getHidrometroCapacidade(), 6));

					hidrometro = new Hidrometro();
					hidrometro.setNumeroDigitosLeitura(Util.obterShort(hidrometroRelatorioOSHelper.getHidrometroNumeroDigitos()));
				}else{

					// Marca do Hidrômetro
					arquivoEnvio.append(Util.completaString("", 3));

					// Capacidade do Hidrômetro
					arquivoEnvio.append(Util.completaString("", 6));
				}

				// NPL
				arquivoEnvio.append(ConstantesSistema.ZERO.toString());

				// Consumo Médio
				if(movimentoRoteiroEmpresa.getNumeroConsumoMedio() != null){

					arquivoEnvio.append(Util.completarStringComValorEsquerda(movimentoRoteiroEmpresa.getNumeroConsumoMedio().toString(),
									"0", 6));
				}else{

					arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 6));
				}

				// Leitura Anterior
				if(movimentoRoteiroEmpresa.getNumeroLeituraAnterior() != null){

					arquivoEnvio.append(Util.completarStringComValorEsquerda(movimentoRoteiroEmpresa.getNumeroLeituraAnterior().toString(),
									"0", 8));
				}else{

					arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 8));
				}

				// Caso a data leitura anterior esteja preenchida
				if(movimentoRoteiroEmpresa.getDataLeituraAnterior() != null){

					// Caso a data leitura anterior seja maior ou igual a 01/01/1950
					if(Util.compararData(movimentoRoteiroEmpresa.getDataLeituraAnterior(), Util.criarData(1, 1, 1950)) > -1){

						// Data Leitura Anterior
						arquivoEnvio.append(Util.formatarDataAAAAMMDD(movimentoRoteiroEmpresa.getDataLeituraAnterior()));
					}else{

						// Caso contrário, atribui a data de fundação da CASAL
						arquivoEnvio.append(Util.formatarDataAAAAMMDD(Util.criarData(1, 12, 1962)));
					}
				}else{

					// Caso a data de instalação do hidrômetro esteja preenchida
					if(movimentoRoteiroEmpresa.getDataInstalacaoHidrometro() != null){

						// Caso a data de instalação do hidrômetro seja maior ou igual a 01/01/1950
						if(Util.compararData(movimentoRoteiroEmpresa.getDataInstalacaoHidrometro(), Util.criarData(1, 1, 1950)) > -1){

							// Data Leitura Anterior = Data de instalação do Hidrômetro
							arquivoEnvio.append(Util.formatarDataAAAAMMDD(movimentoRoteiroEmpresa.getDataInstalacaoHidrometro()));
						}else{

							// Caso contrário, atribui a data de fundação da CASAL
							arquivoEnvio.append(Util.formatarDataAAAAMMDD(Util.criarData(1, 12, 1962)));
						}
					}else{

						// Caso contrário, atribui a data de fundação da CASAL
						arquivoEnvio.append(Util.formatarDataAAAAMMDD(Util.criarData(1, 12, 1962)));
					}
				}

				// Situação da Leitura
				if(movimentoRoteiroEmpresa.getIdLeituraSituacaoAnterior() != null){

					if(movimentoRoteiroEmpresa.getIdLeituraSituacaoAnterior().equals(LeituraSituacao.REALIZADA)
									|| movimentoRoteiroEmpresa.getIdLeituraSituacaoAnterior().equals(LeituraSituacao.CONFIRMADA)
									|| movimentoRoteiroEmpresa.getIdLeituraSituacaoAnterior().equals(LeituraSituacao.LEITURA_ALTERADA)
									|| movimentoRoteiroEmpresa.getIdLeituraSituacaoAnterior().equals(LeituraSituacao.RECONFIRMADA)){

						arquivoEnvio.append(ConstantesSistema.INDICADOR_LIDO);
					}else{

						arquivoEnvio.append(ConstantesSistema.INDICADOR_NAO_LIDO);
					}
				}else{

					arquivoEnvio.append(ConstantesSistema.INDICADOR_NAO_LIDO);
				}

				// Consumo Mínimo Contrato especial
				if(movimentoRoteiroEmpresa.getNumeroConsumoMinimo() != null){

					arquivoEnvio.append(Util.completarStringComValorEsquerda(movimentoRoteiroEmpresa.getNumeroConsumoMinimo().toString(),
									"0", 5));
				}else{

					arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 5));
				}

				// Consumo Hidrômetro Anterior
				arquivoEnvio.append(Util.completarStringComValorEsquerda(ConstantesSistema.ZERO.toString(), "0", 6));

				// Caso a data de instalação do hidrômetro esteja preenchida
				if(movimentoRoteiroEmpresa.getDataInstalacaoHidrometro() != null){

					// Caso a data de instalação do hidrômetro seja maior ou igual a 01/01/1950
					if(Util.compararData(movimentoRoteiroEmpresa.getDataInstalacaoHidrometro(), Util.criarData(1, 1, 1950)) > -1){

						// Data de instalação do Hidrômetro
						arquivoEnvio.append(Util.formatarDataAAAAMMDD(movimentoRoteiroEmpresa.getDataInstalacaoHidrometro()));
					}else{

						// Caso contrário, atribui a data de fundação da CASAL
						arquivoEnvio.append(Util.formatarDataAAAAMMDD(Util.criarData(1, 12, 1962)));
					}
				}else{

					// Caso contrário, atribui a data de fundação da CASAL
					arquivoEnvio.append(Util.formatarDataAAAAMMDD(Util.criarData(1, 12, 1962)));
				}

				if(hidrometro != null){

					if(movimentoRoteiroEmpresa.getNumeroConsumoMedio() != null){

						mediaConsumo = movimentoRoteiroEmpresa.getNumeroConsumoMedio();
					}

					if(movimentoRoteiroEmpresa.getNumeroLeituraAnterior() != null){

						numeroLeituraAnterior = movimentoRoteiroEmpresa.getNumeroLeituraAnterior();
					}

					// <<Inclui>> [UC0086] Calcular Faixa de Leitura Esperada
					faixaLeituraEsperada = getControladorMicromedicao().calcularFaixaLeituraEsperada(mediaConsumo, null, hidrometro,
									numeroLeituraAnterior);

					// Leitura Inferior Esperada
					arquivoEnvio.append(Util.completarStringComValorEsquerda(String.valueOf(faixaLeituraEsperada[0]), "0", 8));

					// Leitura Superior Esperada
					arquivoEnvio.append(Util.completarStringComValorEsquerda(String.valueOf(faixaLeituraEsperada[1]), "0", 8));
				}else{

					// Leitura Inferior Esperada
					arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 8));

					// Leitura Superior Esperada
					arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 8));
				}

				// Últimos consumos, ocorrendo 12 vezes

				// Referência da Leitura anterior 1
				if(movimentoRoteiroEmpresa.getReferenciaConsumo1() != null){

					arquivoEnvio.append(Util.completarStringComValorEsquerda(movimentoRoteiroEmpresa.getReferenciaConsumo1().toString(),
									"0", 6));
				}else{

					arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 6));
				}

				// Anormalidade de Leitura 1
				if(movimentoRoteiroEmpresa.getIdAnormalidadeLeitura1() != null){

					arquivoEnvio.append(Util.completarStringComValorEsquerda(
									movimentoRoteiroEmpresa.getIdAnormalidadeLeitura1().toString(), "0", 2));
				}else{

					arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 2));
				}

				ConsumoAnormalidade consumoAnormalidade = null;

				// Anormalidade de Consumo 1
				if(movimentoRoteiroEmpresa.getIdAnormalidadeConsumo1() != null){

					consumoAnormalidade = (ConsumoAnormalidade) getControladorUtil().pesquisar(
									movimentoRoteiroEmpresa.getIdAnormalidadeConsumo1(), ConsumoAnormalidade.class, false);
					arquivoEnvio.append(Util.completaString(consumoAnormalidade.getDescricaoAbreviada(), 2));
				}else{

					arquivoEnvio.append(Util.completaString("", 2));
				}

				// Consumo Anterior 1
				if(movimentoRoteiroEmpresa.getNumeroConsumo1() != null){

					arquivoEnvio.append(Util
									.completarStringComValorEsquerda(movimentoRoteiroEmpresa.getNumeroConsumo1().toString(), "0", 7));
				}else{

					arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 7));
				}

				// Leitura Anterior 1
				if(movimentoRoteiroEmpresa.getLeituraFaturada1() != null){

					arquivoEnvio.append(Util.completarStringComValorEsquerda(movimentoRoteiroEmpresa.getLeituraFaturada1().toString(), "0",
									8));
				}else{

					arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 8));
				}

				// Referência da Leitura anterior 2
				if(movimentoRoteiroEmpresa.getReferenciaConsumo2() != null){

					arquivoEnvio.append(Util.completarStringComValorEsquerda(movimentoRoteiroEmpresa.getReferenciaConsumo2().toString(),
									"0", 6));
				}else{

					arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 6));
				}

				// Anormalidade de Leitura 2
				if(movimentoRoteiroEmpresa.getIdAnormalidadeLeitura2() != null){

					arquivoEnvio.append(Util.completarStringComValorEsquerda(
									movimentoRoteiroEmpresa.getIdAnormalidadeLeitura2().toString(), "0", 2));
				}else{

					arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 2));
				}

				// Anormalidade de Consumo 2
				if(movimentoRoteiroEmpresa.getIdAnormalidadeConsumo2() != null){

					consumoAnormalidade = (ConsumoAnormalidade) getControladorUtil().pesquisar(
									movimentoRoteiroEmpresa.getIdAnormalidadeConsumo2(), ConsumoAnormalidade.class, false);
					arquivoEnvio.append(Util.completaString(consumoAnormalidade.getDescricaoAbreviada(), 2));
				}else{

					arquivoEnvio.append(Util.completaString("", 2));
				}

				// Consumo Anterior 2
				if(movimentoRoteiroEmpresa.getNumeroConsumo2() != null){

					arquivoEnvio.append(Util
									.completarStringComValorEsquerda(movimentoRoteiroEmpresa.getNumeroConsumo2().toString(), "0", 7));
				}else{

					arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 7));
				}

				// Leitura Anterior 2
				if(movimentoRoteiroEmpresa.getLeituraFaturada2() != null){

					arquivoEnvio.append(Util.completarStringComValorEsquerda(movimentoRoteiroEmpresa.getLeituraFaturada2().toString(), "0",
									8));
				}else{

					arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 8));
				}

				// Referência da Leitura anterior 3
				if(movimentoRoteiroEmpresa.getReferenciaConsumo3() != null){

					arquivoEnvio.append(Util.completarStringComValorEsquerda(movimentoRoteiroEmpresa.getReferenciaConsumo3().toString(),
									"0", 6));
				}else{

					arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 6));
				}

				// Anormalidade de Leitura 3
				if(movimentoRoteiroEmpresa.getIdAnormalidadeLeitura3() != null){

					arquivoEnvio.append(Util.completarStringComValorEsquerda(
									movimentoRoteiroEmpresa.getIdAnormalidadeLeitura3().toString(), "0", 2));
				}else{

					arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 2));
				}

				// Anormalidade de Consumo 3
				if(movimentoRoteiroEmpresa.getIdAnormalidadeConsumo3() != null){

					consumoAnormalidade = (ConsumoAnormalidade) getControladorUtil().pesquisar(
									movimentoRoteiroEmpresa.getIdAnormalidadeConsumo3(), ConsumoAnormalidade.class, false);
					arquivoEnvio.append(Util.completaString(consumoAnormalidade.getDescricaoAbreviada(), 2));
				}else{

					arquivoEnvio.append(Util.completaString("", 2));
				}

				// Consumo Anterior 3
				if(movimentoRoteiroEmpresa.getNumeroConsumo3() != null){

					arquivoEnvio.append(Util
									.completarStringComValorEsquerda(movimentoRoteiroEmpresa.getNumeroConsumo3().toString(), "0", 7));
				}else{

					arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 7));
				}

				// Leitura Anterior 3
				if(movimentoRoteiroEmpresa.getLeituraFaturada3() != null){

					arquivoEnvio.append(Util.completarStringComValorEsquerda(movimentoRoteiroEmpresa.getLeituraFaturada3().toString(), "0",
									8));
				}else{

					arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 8));
				}

				// Referência da Leitura anterior 4
				if(movimentoRoteiroEmpresa.getReferenciaConsumo4() != null){

					arquivoEnvio.append(Util.completarStringComValorEsquerda(movimentoRoteiroEmpresa.getReferenciaConsumo4().toString(),
									"0", 6));
				}else{

					arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 6));
				}

				// Anormalidade de Leitura 4
				if(movimentoRoteiroEmpresa.getIdAnormalidadeLeitura4() != null){

					arquivoEnvio.append(Util.completarStringComValorEsquerda(
									movimentoRoteiroEmpresa.getIdAnormalidadeLeitura4().toString(), "0", 2));
				}else{

					arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 2));
				}

				// Anormalidade de Consumo 4
				if(movimentoRoteiroEmpresa.getIdAnormalidadeConsumo4() != null){

					consumoAnormalidade = (ConsumoAnormalidade) getControladorUtil().pesquisar(
									movimentoRoteiroEmpresa.getIdAnormalidadeConsumo4(), ConsumoAnormalidade.class, false);
					arquivoEnvio.append(Util.completaString(consumoAnormalidade.getDescricaoAbreviada(), 2));
				}else{

					arquivoEnvio.append(Util.completaString("", 2));
				}

				// Consumo Anterior 4
				if(movimentoRoteiroEmpresa.getNumeroConsumo4() != null){

					arquivoEnvio.append(Util
									.completarStringComValorEsquerda(movimentoRoteiroEmpresa.getNumeroConsumo4().toString(), "0", 7));
				}else{

					arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 7));
				}

				// Leitura Anterior 4
				if(movimentoRoteiroEmpresa.getLeituraFaturada4() != null){

					arquivoEnvio.append(Util.completarStringComValorEsquerda(movimentoRoteiroEmpresa.getLeituraFaturada4().toString(), "0",
									8));
				}else{

					arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 8));
				}

				// Referência da Leitura anterior 5
				if(movimentoRoteiroEmpresa.getReferenciaConsumo5() != null){

					arquivoEnvio.append(Util.completarStringComValorEsquerda(movimentoRoteiroEmpresa.getReferenciaConsumo5().toString(),
									"0", 6));
				}else{

					arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 6));
				}

				// Anormalidade de Leitura 5
				if(movimentoRoteiroEmpresa.getIdAnormalidadeLeitura5() != null){

					arquivoEnvio.append(Util.completarStringComValorEsquerda(
									movimentoRoteiroEmpresa.getIdAnormalidadeLeitura5().toString(), "0", 2));
				}else{

					arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 2));
				}

				// Anormalidade de Consumo 5
				if(movimentoRoteiroEmpresa.getIdAnormalidadeConsumo5() != null){

					consumoAnormalidade = (ConsumoAnormalidade) getControladorUtil().pesquisar(
									movimentoRoteiroEmpresa.getIdAnormalidadeConsumo5(), ConsumoAnormalidade.class, false);
					arquivoEnvio.append(Util.completaString(consumoAnormalidade.getDescricaoAbreviada(), 2));
				}else{

					arquivoEnvio.append(Util.completaString("", 2));
				}

				// Consumo Anterior 5
				if(movimentoRoteiroEmpresa.getNumeroConsumo5() != null){

					arquivoEnvio.append(Util
									.completarStringComValorEsquerda(movimentoRoteiroEmpresa.getNumeroConsumo5().toString(), "0", 7));
				}else{

					arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 7));
				}

				// Leitura Anterior 5
				if(movimentoRoteiroEmpresa.getLeituraFaturada5() != null){

					arquivoEnvio.append(Util.completarStringComValorEsquerda(movimentoRoteiroEmpresa.getLeituraFaturada5().toString(), "0",
									8));
				}else{

					arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 8));
				}

				// Referência da Leitura anterior 6
				if(movimentoRoteiroEmpresa.getReferenciaConsumo6() != null){

					arquivoEnvio.append(Util.completarStringComValorEsquerda(movimentoRoteiroEmpresa.getReferenciaConsumo6().toString(),
									"0", 6));
				}else{

					arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 6));
				}

				// Anormalidade de Leitura 6
				if(movimentoRoteiroEmpresa.getIdAnormalidadeLeitura6() != null){

					arquivoEnvio.append(Util.completarStringComValorEsquerda(
									movimentoRoteiroEmpresa.getIdAnormalidadeLeitura6().toString(), "0", 2));
				}else{

					arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 2));
				}

				// Anormalidade de Consumo 6
				if(movimentoRoteiroEmpresa.getIdAnormalidadeConsumo6() != null){

					consumoAnormalidade = (ConsumoAnormalidade) getControladorUtil().pesquisar(
									movimentoRoteiroEmpresa.getIdAnormalidadeConsumo6(), ConsumoAnormalidade.class, false);
					arquivoEnvio.append(Util.completaString(consumoAnormalidade.getDescricaoAbreviada(), 2));
				}else{

					arquivoEnvio.append(Util.completaString("", 2));
				}

				// Consumo Anterior 6
				if(movimentoRoteiroEmpresa.getNumeroConsumo6() != null){

					arquivoEnvio.append(Util
									.completarStringComValorEsquerda(movimentoRoteiroEmpresa.getNumeroConsumo6().toString(), "0", 7));
				}else{

					arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 7));
				}

				// Leitura Anterior 6
				if(movimentoRoteiroEmpresa.getLeituraFaturada6() != null){

					arquivoEnvio.append(Util.completarStringComValorEsquerda(movimentoRoteiroEmpresa.getLeituraFaturada6().toString(), "0",
									8));
				}else{

					arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 8));
				}

				// Referência da Leitura anterior 7
				if(movimentoRoteiroEmpresa.getReferenciaConsumo7() != null){

					arquivoEnvio.append(Util.completarStringComValorEsquerda(movimentoRoteiroEmpresa.getReferenciaConsumo7().toString(),
									"0", 6));
				}else{

					arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 6));
				}

				// Anormalidade de Leitura 7
				if(movimentoRoteiroEmpresa.getIdAnormalidadeLeitura7() != null){

					arquivoEnvio.append(Util.completarStringComValorEsquerda(
									movimentoRoteiroEmpresa.getIdAnormalidadeLeitura7().toString(), "0", 2));
				}else{

					arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 2));
				}

				// Anormalidade de Consumo 7
				if(movimentoRoteiroEmpresa.getIdAnormalidadeConsumo7() != null){

					consumoAnormalidade = (ConsumoAnormalidade) getControladorUtil().pesquisar(
									movimentoRoteiroEmpresa.getIdAnormalidadeConsumo7(), ConsumoAnormalidade.class, false);
					arquivoEnvio.append(Util.completaString(consumoAnormalidade.getDescricaoAbreviada(), 2));
				}else{

					arquivoEnvio.append(Util.completaString("", 2));
				}

				// Consumo Anterior 7
				if(movimentoRoteiroEmpresa.getNumeroConsumo7() != null){

					arquivoEnvio.append(Util
									.completarStringComValorEsquerda(movimentoRoteiroEmpresa.getNumeroConsumo7().toString(), "0", 7));
				}else{

					arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 7));
				}

				// Leitura Anterior 7
				if(movimentoRoteiroEmpresa.getLeituraFaturada7() != null){

					arquivoEnvio.append(Util.completarStringComValorEsquerda(movimentoRoteiroEmpresa.getLeituraFaturada7().toString(), "0",
									8));
				}else{

					arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 8));
				}

				// Referência da Leitura anterior 8
				if(movimentoRoteiroEmpresa.getReferenciaConsumo8() != null){

					arquivoEnvio.append(Util.completarStringComValorEsquerda(movimentoRoteiroEmpresa.getReferenciaConsumo8().toString(),
									"0", 6));
				}else{

					arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 6));
				}

				// Anormalidade de Leitura 8
				if(movimentoRoteiroEmpresa.getIdAnormalidadeLeitura8() != null){

					arquivoEnvio.append(Util.completarStringComValorEsquerda(
									movimentoRoteiroEmpresa.getIdAnormalidadeLeitura8().toString(), "0", 2));
				}else{

					arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 2));
				}

				// Anormalidade de Consumo 8
				if(movimentoRoteiroEmpresa.getIdAnormalidadeConsumo8() != null){

					consumoAnormalidade = (ConsumoAnormalidade) getControladorUtil().pesquisar(
									movimentoRoteiroEmpresa.getIdAnormalidadeConsumo8(), ConsumoAnormalidade.class, false);
					arquivoEnvio.append(Util.completaString(consumoAnormalidade.getDescricaoAbreviada(), 2));
				}else{

					arquivoEnvio.append(Util.completaString("", 2));
				}

				// Consumo Anterior 8
				if(movimentoRoteiroEmpresa.getNumeroConsumo8() != null){

					arquivoEnvio.append(Util
									.completarStringComValorEsquerda(movimentoRoteiroEmpresa.getNumeroConsumo8().toString(), "0", 7));
				}else{

					arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 7));
				}

				// Leitura Anterior 8
				if(movimentoRoteiroEmpresa.getLeituraFaturada8() != null){

					arquivoEnvio.append(Util.completarStringComValorEsquerda(movimentoRoteiroEmpresa.getLeituraFaturada8().toString(), "0",
									8));
				}else{

					arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 8));
				}

				// Referência da Leitura anterior 9
				if(movimentoRoteiroEmpresa.getReferenciaConsumo9() != null){

					arquivoEnvio.append(Util.completarStringComValorEsquerda(movimentoRoteiroEmpresa.getReferenciaConsumo9().toString(),
									"0", 6));
				}else{

					arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 6));
				}

				// Anormalidade de Leitura 9
				if(movimentoRoteiroEmpresa.getIdAnormalidadeLeitura9() != null){

					arquivoEnvio.append(Util.completarStringComValorEsquerda(
									movimentoRoteiroEmpresa.getIdAnormalidadeLeitura9().toString(), "0", 2));
				}else{

					arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 2));
				}

				// Anormalidade de Consumo 9
				if(movimentoRoteiroEmpresa.getIdAnormalidadeConsumo9() != null){

					consumoAnormalidade = (ConsumoAnormalidade) getControladorUtil().pesquisar(
									movimentoRoteiroEmpresa.getIdAnormalidadeConsumo9(), ConsumoAnormalidade.class, false);
					arquivoEnvio.append(Util.completaString(consumoAnormalidade.getDescricaoAbreviada(), 2));
				}else{

					arquivoEnvio.append(Util.completaString("", 2));
				}

				// Consumo Anterior 9
				if(movimentoRoteiroEmpresa.getNumeroConsumo9() != null){

					arquivoEnvio.append(Util
									.completarStringComValorEsquerda(movimentoRoteiroEmpresa.getNumeroConsumo9().toString(), "0", 7));
				}else{

					arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 7));
				}

				// Leitura Anterior 9
				if(movimentoRoteiroEmpresa.getLeituraFaturada9() != null){

					arquivoEnvio.append(Util.completarStringComValorEsquerda(movimentoRoteiroEmpresa.getLeituraFaturada9().toString(), "0",
									8));
				}else{

					arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 8));
				}

				// Referência da Leitura anterior 10
				if(movimentoRoteiroEmpresa.getReferenciaConsumo10() != null){

					arquivoEnvio.append(Util.completarStringComValorEsquerda(movimentoRoteiroEmpresa.getReferenciaConsumo10().toString(),
									"0", 6));
				}else{

					arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 6));
				}

				// Anormalidade de Leitura 10
				if(movimentoRoteiroEmpresa.getIdAnormalidadeLeitura10() != null){

					arquivoEnvio.append(Util.completarStringComValorEsquerda(movimentoRoteiroEmpresa.getIdAnormalidadeLeitura10()
									.toString(), "0", 2));
				}else{

					arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 2));
				}

				// Anormalidade de Consumo 10
				if(movimentoRoteiroEmpresa.getIdAnormalidadeConsumo10() != null){

					consumoAnormalidade = (ConsumoAnormalidade) getControladorUtil().pesquisar(
									movimentoRoteiroEmpresa.getIdAnormalidadeConsumo10(), ConsumoAnormalidade.class, false);
					arquivoEnvio.append(Util.completaString(consumoAnormalidade.getDescricaoAbreviada(), 2));
				}else{

					arquivoEnvio.append(Util.completaString("", 2));
				}

				// Consumo Anterior 10
				if(movimentoRoteiroEmpresa.getNumeroConsumo10() != null){

					arquivoEnvio.append(Util.completarStringComValorEsquerda(movimentoRoteiroEmpresa.getNumeroConsumo10().toString(), "0",
									7));
				}else{

					arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 7));
				}

				// Leitura Anterior 10
				if(movimentoRoteiroEmpresa.getLeituraFaturada10() != null){

					arquivoEnvio.append(Util.completarStringComValorEsquerda(movimentoRoteiroEmpresa.getLeituraFaturada10().toString(),
									"0", 8));
				}else{

					arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 8));
				}

				// Referência da Leitura anterior 11
				if(movimentoRoteiroEmpresa.getReferenciaConsumo11() != null){

					arquivoEnvio.append(Util.completarStringComValorEsquerda(movimentoRoteiroEmpresa.getReferenciaConsumo11().toString(),
									"0", 6));
				}else{

					arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 6));
				}

				// Anormalidade de Leitura 11
				if(movimentoRoteiroEmpresa.getIdAnormalidadeLeitura11() != null){

					arquivoEnvio.append(Util.completarStringComValorEsquerda(movimentoRoteiroEmpresa.getIdAnormalidadeLeitura11()
									.toString(), "0", 2));
				}else{

					arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 2));
				}

				// Anormalidade de Consumo 11
				if(movimentoRoteiroEmpresa.getIdAnormalidadeConsumo11() != null){

					consumoAnormalidade = (ConsumoAnormalidade) getControladorUtil().pesquisar(
									movimentoRoteiroEmpresa.getIdAnormalidadeConsumo11(), ConsumoAnormalidade.class, false);
					arquivoEnvio.append(Util.completaString(consumoAnormalidade.getDescricaoAbreviada(), 2));
				}else{

					arquivoEnvio.append(Util.completaString("", 2));
				}

				// Consumo Anterior 11
				if(movimentoRoteiroEmpresa.getNumeroConsumo11() != null){

					arquivoEnvio.append(Util.completarStringComValorEsquerda(movimentoRoteiroEmpresa.getNumeroConsumo11().toString(), "0",
									7));
				}else{

					arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 7));
				}

				// Leitura Anterior 11
				if(movimentoRoteiroEmpresa.getLeituraFaturada11() != null){

					arquivoEnvio.append(Util.completarStringComValorEsquerda(movimentoRoteiroEmpresa.getLeituraFaturada11().toString(),
									"0", 8));
				}else{

					arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 8));
				}

				// Referência da Leitura anterior 12
				if(movimentoRoteiroEmpresa.getReferenciaConsumo12() != null){

					arquivoEnvio.append(Util.completarStringComValorEsquerda(movimentoRoteiroEmpresa.getReferenciaConsumo12().toString(),
									"0", 6));
				}else{

					arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 6));
				}

				// Anormalidade de Leitura 12
				if(movimentoRoteiroEmpresa.getIdAnormalidadeLeitura12() != null){

					arquivoEnvio.append(Util.completarStringComValorEsquerda(movimentoRoteiroEmpresa.getIdAnormalidadeLeitura12()
									.toString(), "0", 2));
				}else{

					arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 2));
				}

				// Anormalidade de Consumo 12
				if(movimentoRoteiroEmpresa.getIdAnormalidadeConsumo12() != null){

					consumoAnormalidade = (ConsumoAnormalidade) getControladorUtil().pesquisar(
									movimentoRoteiroEmpresa.getIdAnormalidadeConsumo12(), ConsumoAnormalidade.class, false);
					arquivoEnvio.append(Util.completaString(consumoAnormalidade.getDescricaoAbreviada(), 2));
				}else{

					arquivoEnvio.append(Util.completaString("", 2));
				}

				// Consumo Anterior 12
				if(movimentoRoteiroEmpresa.getNumeroConsumo12() != null){

					arquivoEnvio.append(Util.completarStringComValorEsquerda(movimentoRoteiroEmpresa.getNumeroConsumo12().toString(), "0",
									7));
				}else{

					arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 7));
				}

				// Leitura Anterior 12
				if(movimentoRoteiroEmpresa.getLeituraFaturada12() != null){

					arquivoEnvio.append(Util.completarStringComValorEsquerda(movimentoRoteiroEmpresa.getLeituraFaturada12().toString(),
									"0", 8));
				}else{

					arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 8));
				}

				// Completa com espaços em branco
				arquivoEnvio.append(Util.completaString("", 104));

				// Caractere de controle
				arquivoEnvio.append("#");
				arquivoEnvio.append(System.getProperty("line.separator"));
				quantidadeTipo5 += 1;
			}

			// [SB0006 – Obter Débitos Anteriores]
			List<DebitoAnteriorHelper> collDebitosAnteriores = this.obterDebitosAnteriores(movimentoRoteiroEmpresa);

			if(!Util.isVazioOrNulo(collDebitosAnteriores) && collDebitosAnteriores.size() > 0){

				// Grava registro tipo 6
				arquivoEnvio.append("6");

				// Completa com espaços em branco
				arquivoEnvio.append(Util.completaString("", 5));

				// Inscrição
				arquivoEnvio.append(movimentoRoteiroEmpresa.getInscricaoNaoFormatadaArquivoModelo2());

				// Sublote
				arquivoEnvio.append(Util.completarStringComValorEsquerda(movimentoRoteiroEmpresa.getNumeroSubLoteImovel().toString(), "0",
								3));

				// Caso o imóvel possua débito e a quantidade de contas for maior que 8 (oito)
				if(collDebitosAnteriores.size() > 8){

					// Indicador de débito Acumulado
					arquivoEnvio.append("S");
				}else{

					// Indicador de débito Acumulado
					arquivoEnvio.append("N");
				}

				if(!Util.isVazioOrNulo(collDebitosAnteriores)){

					BigDecimal valorDebito = BigDecimal.ZERO;

					// Débitos anteriores, ocorrendo 8 vezes
					for(int i = 0; i < collDebitosAnteriores.size(); i++){

						// Referência do débito
						debitosAnteriores += String.valueOf(collDebitosAnteriores.get(i).getConta().getReferencia());

						// Valor do débito = Valor total da conta + Valor dos Acréscimos
						valorDebito = collDebitosAnteriores.get(i).getConta().getValorTotalContaBigDecimal();

						if(valorDebito != null && collDebitosAnteriores.get(i).getValorAcrescimos() != null){
							valorDebito = valorDebito.add(collDebitosAnteriores.get(i).getValorAcrescimos());
						}

						// Valor do débito
						debitosAnteriores += Util.completarStringComValorEsquerda(Util.formatarMoedaReal(valorDebito, 2).replace(".", "")
										.replace(",", ""), "0", 11);

						if(collDebitosAnteriores.size() > 8){

							if(i == 7){

								arquivoEnvio.append(debitosAnteriores);

							}else if(i > 7){

								// Acumula o total dos débitos restantes
								valorDebitosAcumulado = valorDebitosAcumulado.add(valorDebito);
							}
						}else{

							if(i == (collDebitosAnteriores.size() - 1)){

								arquivoEnvio.append(Util.completarStringZeroDireita(debitosAnteriores, 136));
							}
						}

						// Acumula o total dos débitos
						valorTotalDebitos = valorTotalDebitos.add(valorDebito);
					}

					if(collDebitosAnteriores.size() > 8){

						// Quantidade de débitos acumulados
						arquivoEnvio.append(Util
										.completarStringComValorEsquerda(String.valueOf((collDebitosAnteriores.size() - 8)), "0", 3));

						// Valor dos débitos acumulados
						arquivoEnvio.append(Util.completarStringComValorEsquerda(Util.formatarMoedaReal(valorDebitosAcumulado, 2).replace(
										".", "").replace(",", ""), "0", 11));

					}else{

						// Quantidade de débitos acumulados
						arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 3));

						// Valor dos débitos acumulados
						arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 11));
					}

				}else{

					// Débitos anteriores
					arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 136));

					// Quantidade de débitos acumulados
					arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 3));

					// Valor dos débitos acumulados
					arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 11));
				}

				// Valor total do débito
				arquivoEnvio.append(Util.completarStringComValorEsquerda(Util.formatarMoedaReal(valorTotalDebitos, 2).replace(".", "")
								.replace(",", ""), "0", 11));

				// Data base para o débito
				arquivoEnvio.append(Util.formatarDataAAAAMMDD(Util.subtrairNumeroDiasDeUmaData(new Date(), Util
								.obterInteger(parametroQuantidadeDiasVencimentoContaAvisoCorte))));

				// Mensagem do débito
				if(movimentoRoteiroEmpresa.getQuantidadeEconomiasPublica() == null
								|| movimentoRoteiroEmpresa.getQuantidadeEconomiasPublica().shortValue() == 0){

					if(!Util.isVazioOrNulo(collDebitosAnteriores)){

						if(collDebitosAnteriores.size() < 20){

							arquivoEnvio.append(Util.completaString("IMOVEL COM DEBITO, EVITE O CORTE. PROCURE A CASAL.", 60));
						}else if(collDebitosAnteriores.size() < 25){

							arquivoEnvio.append(Util.completaString("EVITE COBRANCA JUDICIAL DO DEBITO. PROCURE CASAL.", 60));
						}else{

							arquivoEnvio.append(Util.completaString("DEBITO SUJEITO A COBRANCA JUDICIAL.", 60));
						}
					}else{

						// Completa com espaços em branco
						arquivoEnvio.append(Util.completaString("", 60));
					}
				}else{

					// Completa com espaços em branco
					arquivoEnvio.append(Util.completaString("", 60));
				}

				// Número do aviso de débito
				if(movimentoRoteiroEmpresa.getNumeroDocumentoCobranca() != null){

					arquivoEnvio.append(Util.completarStringComValorEsquerda(movimentoRoteiroEmpresa.getNumeroDocumentoCobranca()
									.toString(), "0", 9));
				}else{

					arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 9));
				}

				// Completa espaço vazio com zero
				arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 248));

				// Caractere de controle
				arquivoEnvio.append("#");
				arquivoEnvio.append(System.getProperty("line.separator"));
				quantidadeTipo6 += 1;

			}

			if(!Util.isVazioOuBranco(movimentoRoteiroEmpresa.getDescricaoRubrica1())){

				// Grava registro tipo 7
				arquivoEnvio.append("7");

				// Completa com espaços em branco
				arquivoEnvio.append(Util.completaString("", 5));

				// Inscrição
				arquivoEnvio.append(movimentoRoteiroEmpresa.getInscricaoNaoFormatadaArquivoModelo2());

				// Sublote
				arquivoEnvio.append(Util.completarStringComValorEsquerda(movimentoRoteiroEmpresa.getNumeroSubLoteImovel().toString(), "0",
								3));

				/*
				 * Débitos Cobrados, ocorrendo 6 (seis) vezes, preencher as 5 (cinco) primeiras
				 * ocorrências com os primeiros 5 débitos e acumular demais débitos na sexta
				 * ocorrência
				 */

				// Código e descrição do Serviço 1
				if(!Util.isVazioOuBranco(movimentoRoteiroEmpresa.getDescricaoRubrica1())){

					arquivoEnvio.append(Util.completaString(movimentoRoteiroEmpresa.getDescricaoRubrica1(), 28));
				}else{

					// Completa espaço vazio com zero
					arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 28));
				}

				// Número de Prestações 1
				if(movimentoRoteiroEmpresa.getNumeroPrestacaoRubrica1() != null){

					arquivoEnvio.append(Util.completarStringComValorEsquerda(movimentoRoteiroEmpresa.getNumeroPrestacaoRubrica1()
									.toString(), "0", 2));
				}else{

					// Completa com zeros
					arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 2));
				}

				// Número Prestação Cobrada 1
				if(movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica1() != null){

					arquivoEnvio.append(Util.completarStringComValorEsquerda(movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica1()
									.toString(), "0", 2));
				}else{

					// Completa com zeros
					arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 2));
				}

				// Valor da Prestação 1
				if(movimentoRoteiroEmpresa.getValorRubrica1() != null){

					arquivoEnvio.append(Util.completarStringComValorEsquerda(Util.formatarMoedaReal(
									movimentoRoteiroEmpresa.getValorRubrica1(), 2).replace(",", ""), "0", 11));
				}else{

					// Completa com zeros
					arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 11));
				}

				// Referência do Débito Cobrado 1
				if(movimentoRoteiroEmpresa.getReferenciaRubrica1() != null){

					arquivoEnvio.append(Util.completarStringComValorEsquerda(movimentoRoteiroEmpresa.getReferenciaRubrica1().toString(),
									"0", 6));
				}else{

					// Completa com zeros
					arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 6));
				}

				// Indicador de débito ou crédito 1
				if(!Util.isVazioOuBranco(movimentoRoteiroEmpresa.getDescricaoRubrica1())){

					arquivoEnvio.append("D");
				}else{

					// Completa com zero
					arquivoEnvio.append(ConstantesSistema.ZERO.toString());
				}

				// Código e descrição do Serviço 2
				if(!Util.isVazioOuBranco(movimentoRoteiroEmpresa.getDescricaoRubrica2())){

					arquivoEnvio.append(Util.completaString(movimentoRoteiroEmpresa.getDescricaoRubrica2(), 28));
				}else{

					// Completa espaço vazio com zero
					arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 28));
				}

				// Número de Prestações 2
				if(movimentoRoteiroEmpresa.getNumeroPrestacaoRubrica2() != null){

					arquivoEnvio.append(Util.completarStringComValorEsquerda(movimentoRoteiroEmpresa.getNumeroPrestacaoRubrica2()
									.toString(), "0", 2));
				}else{

					// Completa com zeros
					arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 2));
				}

				// Número Prestação Cobrada 2
				if(movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica2() != null){

					arquivoEnvio.append(Util.completarStringComValorEsquerda(movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica2()
									.toString(), "0", 2));
				}else{

					// Completa com zeros
					arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 2));
				}

				// Valor da Prestação 2
				if(movimentoRoteiroEmpresa.getValorRubrica2() != null){

					arquivoEnvio.append(Util.completarStringComValorEsquerda(Util.formatarMoedaReal(
									movimentoRoteiroEmpresa.getValorRubrica2(), 2).replace(",", ""), "0", 11));
				}else{

					// Completa com zeros
					arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 11));
				}

				// Referência do Débito Cobrado 2
				if(movimentoRoteiroEmpresa.getReferenciaRubrica2() != null){

					arquivoEnvio.append(Util.completarStringComValorEsquerda(movimentoRoteiroEmpresa.getReferenciaRubrica2().toString(),
									"0", 6));
				}else{

					// Completa com zeros
					arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 6));
				}

				// Indicador de débito ou crédito 2
				if(!Util.isVazioOuBranco(movimentoRoteiroEmpresa.getDescricaoRubrica2())){

					arquivoEnvio.append("D");
				}else{

					// Completa com zero
					arquivoEnvio.append(ConstantesSistema.ZERO.toString());
				}

				// Código e descrição do Serviço 3
				if(!Util.isVazioOuBranco(movimentoRoteiroEmpresa.getDescricaoRubrica3())){

					arquivoEnvio.append(Util.completaString(movimentoRoteiroEmpresa.getDescricaoRubrica3(), 28));
				}else{

					// Completa espaço vazio com zero
					arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 28));
				}

				// Número de Prestações 3
				if(movimentoRoteiroEmpresa.getNumeroPrestacaoRubrica3() != null){

					arquivoEnvio.append(Util.completarStringComValorEsquerda(movimentoRoteiroEmpresa.getNumeroPrestacaoRubrica3()
									.toString(), "0", 2));
				}else{

					// Completa com zeros
					arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 2));
				}

				// Número Prestação Cobrada 3
				if(movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica3() != null){

					arquivoEnvio.append(Util.completarStringComValorEsquerda(movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica3()
									.toString(), "0", 2));
				}else{

					// Completa com zeros
					arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 2));
				}

				// Valor da Prestação 3
				if(movimentoRoteiroEmpresa.getValorRubrica3() != null){

					arquivoEnvio.append(Util.completarStringComValorEsquerda(Util.formatarMoedaReal(
									movimentoRoteiroEmpresa.getValorRubrica3(), 2).replace(",", ""), "0", 11));
				}else{

					// Completa com zeros
					arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 11));
				}

				// Referência do Débito Cobrado 3
				if(movimentoRoteiroEmpresa.getReferenciaRubrica3() != null){

					arquivoEnvio.append(Util.completarStringComValorEsquerda(movimentoRoteiroEmpresa.getReferenciaRubrica3().toString(),
									"0", 6));
				}else{

					// Completa com zeros
					arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 6));
				}

				// Indicador de débito ou crédito 3
				if(!Util.isVazioOuBranco(movimentoRoteiroEmpresa.getDescricaoRubrica3())){

					arquivoEnvio.append("D");
				}else{

					// Completa com zero
					arquivoEnvio.append(ConstantesSistema.ZERO.toString());
				}

				// Código e descrição do Serviço 4
				if(!Util.isVazioOuBranco(movimentoRoteiroEmpresa.getDescricaoRubrica4())){

					arquivoEnvio.append(Util.completaString(movimentoRoteiroEmpresa.getDescricaoRubrica4(), 28));
				}else{

					// Completa espaço vazio com zero
					arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 28));
				}

				// Número de Prestações 4
				if(movimentoRoteiroEmpresa.getNumeroPrestacaoRubrica4() != null){

					arquivoEnvio.append(Util.completarStringComValorEsquerda(movimentoRoteiroEmpresa.getNumeroPrestacaoRubrica4()
									.toString(), "0", 2));
				}else{

					// Completa com zeros
					arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 2));
				}

				// Número Prestação Cobrada 4
				if(movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica4() != null){

					arquivoEnvio.append(Util.completarStringComValorEsquerda(movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica4()
									.toString(), "0", 2));
				}else{

					// Completa com zeros
					arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 2));
				}

				// Valor da Prestação 4
				if(movimentoRoteiroEmpresa.getValorRubrica4() != null){

					arquivoEnvio.append(Util.completarStringComValorEsquerda(Util.formatarMoedaReal(
									movimentoRoteiroEmpresa.getValorRubrica4(), 2).replace(",", ""), "0", 11));
				}else{

					// Completa com zeros
					arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 11));
				}

				// Referência do Débito Cobrado 4
				if(movimentoRoteiroEmpresa.getReferenciaRubrica4() != null){

					arquivoEnvio.append(Util.completarStringComValorEsquerda(movimentoRoteiroEmpresa.getReferenciaRubrica4().toString(),
									"0", 6));
				}else{

					// Completa com zeros
					arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 6));
				}

				// Indicador de débito ou crédito 4
				if(!Util.isVazioOuBranco(movimentoRoteiroEmpresa.getDescricaoRubrica4())){

					arquivoEnvio.append("D");
				}else{

					// Completa com zero
					arquivoEnvio.append(ConstantesSistema.ZERO.toString());
				}

				// Código e descrição do Serviço 5
				if(!Util.isVazioOuBranco(movimentoRoteiroEmpresa.getDescricaoRubrica5())){

					arquivoEnvio.append(Util.completaString(movimentoRoteiroEmpresa.getDescricaoRubrica5(), 28));
				}else{

					// Completa espaço vazio com zero
					arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 28));
				}

				// Número de Prestações 5
				if(movimentoRoteiroEmpresa.getNumeroPrestacaoRubrica5() != null){

					arquivoEnvio.append(Util.completarStringComValorEsquerda(movimentoRoteiroEmpresa.getNumeroPrestacaoRubrica5()
									.toString(), "0", 2));
				}else{

					// Completa com zeros
					arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 2));
				}

				// Número Prestação Cobrada 5
				if(movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica5() != null){

					arquivoEnvio.append(Util.completarStringComValorEsquerda(movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica5()
									.toString(), "0", 2));
				}else{

					// Completa com zeros
					arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 2));
				}

				// Valor da Prestação 5
				if(movimentoRoteiroEmpresa.getValorRubrica5() != null){

					arquivoEnvio.append(Util.completarStringComValorEsquerda(Util.formatarMoedaReal(
									movimentoRoteiroEmpresa.getValorRubrica5(), 2).replace(",", ""), "0", 11));
				}else{

					// Completa com zeros
					arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 11));
				}

				// Referência do Débito Cobrado 5
				if(movimentoRoteiroEmpresa.getReferenciaRubrica5() != null){

					arquivoEnvio.append(Util.completarStringComValorEsquerda(movimentoRoteiroEmpresa.getReferenciaRubrica5().toString(),
									"0", 6));
				}else{

					// Completa com zeros
					arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 6));
				}

				// Indicador de débito ou crédito 5
				if(!Util.isVazioOuBranco(movimentoRoteiroEmpresa.getDescricaoRubrica5())){

					arquivoEnvio.append("D");
				}else{

					// Completa com zero
					arquivoEnvio.append(ConstantesSistema.ZERO.toString());
				}

				// Obtém valor das demais prestações
				BigDecimal totalRestante = repositorioMicromedicao.obterTotalValorRubricaRestante(movimentoRoteiroEmpresa.getId());

				if(totalRestante == null){

					totalRestante = BigDecimal.ZERO;
				}

				if(totalRestante.compareTo(BigDecimal.ZERO) > 0){

					// Código e descrição do Serviço 6
					arquivoEnvio.append(Util.completaString("999 - OUTRAS COBRANÇAS", 28));

					// Número de Prestações 6
					arquivoEnvio.append("00");

					// Número Prestação Cobrada 6
					arquivoEnvio.append("00");

					// Valor da Prestação 6
					arquivoEnvio.append(Util.completarStringComValorEsquerda(Util.formatarMoedaReal(totalRestante, 2).replace(",", ""),
									"0", 11));

					// Referência do Débito Cobrado
					arquivoEnvio.append("000000");

					// Indicador de débito ou crédito 6
					arquivoEnvio.append("D");
				}else{

					// Código e descrição do Serviço 6
					arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 28));

					// Número de Prestações 6
					arquivoEnvio.append("00");

					// Número Prestação Cobrada 6
					arquivoEnvio.append("00");

					// Valor da Prestação 6
					arquivoEnvio.append(Util.completarStringComValorEsquerda("0", "0", 11));

					// Referência do Débito Cobrado
					arquivoEnvio.append("000000");

					// Indicador de débito ou crédito 6
					arquivoEnvio.append(ConstantesSistema.ZERO.toString());
				}

				// Completa espaço vazio com zero
				arquivoEnvio.append(Util.completarStringComValorEsquerda("", "0", 187));

				// Caractere de controle
				arquivoEnvio.append("#");
				arquivoEnvio.append(System.getProperty("line.separator"));
				quantidadeTipo7 += 1;
			}

			// Acumula valores para geração do relatório de resumo do processamento
			if(geracaoPreFatResumoHelperMap.containsKey(movimentoRoteiroEmpresa.getLocalidade().getId())){

				geracaoPreFatResumoHelper = geracaoPreFatResumoHelperMap.get(movimentoRoteiroEmpresa.getLocalidade().getId());

				// Identifica os diferentes Códigos do Setor de Faturamento
				if(movimentoRoteiroEmpresa.getCodigoSetorComercial() != null
								&& !geracaoPreFatResumoHelper.getColecaoSetorComercial().contains(
												movimentoRoteiroEmpresa.getCodigoSetorComercial())){

					geracaoPreFatResumoHelper.getColecaoSetorComercial().add(movimentoRoteiroEmpresa.getCodigoSetorComercial());
				}

				// Soma 1(um) ao total de Consumidores
				geracaoPreFatResumoHelper.setQuantidadeFaturados(geracaoPreFatResumoHelper.getQuantidadeFaturados() + 1);

				if(!Util.isVazioOuBranco(movimentoRoteiroEmpresa.getNumeroHidrometro())){

					// Soma 1 ao Total de Consumidores Medidos
					geracaoPreFatResumoHelper.setQuantidadeMedidos(geracaoPreFatResumoHelper.getQuantidadeMedidos() + 1);
				}else{

					// Soma 1 ao Total de Consumidores Não Medidos
					geracaoPreFatResumoHelper.setQuantidadeNaoMedidos(geracaoPreFatResumoHelper.getQuantidadeNaoMedidos() + 1);
				}

				geracaoPreFatResumoHelper.setQuantidadeTipo4(geracaoPreFatResumoHelper.getQuantidadeTipo4() + 1);
				geracaoPreFatResumoHelper.setQuantidadeTipo5(geracaoPreFatResumoHelper.getQuantidadeTipo5() + 1);

				// Caso tenha gerado a linha do tipo 6
				if(movimentoRoteiroEmpresa.getNumeroDocumentoCobranca() != null){

					geracaoPreFatResumoHelper.setQuantidadeTipo6(geracaoPreFatResumoHelper.getQuantidadeTipo6() + 1);
				}

				// Caso tenha gerado a linha do tipo 7
				if(!Util.isVazioOuBranco(movimentoRoteiroEmpresa.getDescricaoRubrica1())){

					geracaoPreFatResumoHelper.setQuantidadeTipo7(geracaoPreFatResumoHelper.getQuantidadeTipo7() + 1);
				}

				// Somar MREM_VLDEBITOS ao Valor Total de Serviços
				if(movimentoRoteiroEmpresa.getValorDebitos() != null){

					geracaoPreFatResumoHelper.setValorTotalDebitos(geracaoPreFatResumoHelper.getValorTotalDebitos().add(
									movimentoRoteiroEmpresa.getValorDebitos()));
				}

			}else{

				geracaoPreFatResumoHelper = new RelatorioOcorrenciaGeracaoPreFatResumoHelper();

				// Identifica os diferentes Códigos do Setor de Faturamento
				geracaoPreFatResumoHelper.setColecaoSetorComercial(new ArrayList<Integer>());
				geracaoPreFatResumoHelper.getColecaoSetorComercial().add(movimentoRoteiroEmpresa.getCodigoSetorComercial());

				// Total de Consumidores
				geracaoPreFatResumoHelper.setQuantidadeFaturados(1);

				if(!Util.isVazioOuBranco(movimentoRoteiroEmpresa.getNumeroHidrometro())){

					// Total de Consumidores Medidos
					geracaoPreFatResumoHelper.setQuantidadeMedidos(1);
					geracaoPreFatResumoHelper.setQuantidadeNaoMedidos(0);
				}else{

					// Total de Consumidores Não Medidos
					geracaoPreFatResumoHelper.setQuantidadeMedidos(0);
					geracaoPreFatResumoHelper.setQuantidadeNaoMedidos(1);
				}

				geracaoPreFatResumoHelper.setQuantidadeTipo1(quantidadeTipo1);
				geracaoPreFatResumoHelper.setQuantidadeTipo2(quantidadeTipo2);
				geracaoPreFatResumoHelper.setQuantidadeTipo3(quantidadeTipo3);
				geracaoPreFatResumoHelper.setQuantidadeTipo4(1);
				geracaoPreFatResumoHelper.setQuantidadeTipo5(1);

				// Caso tenha gerado a linha do tipo 6
				if(movimentoRoteiroEmpresa.getNumeroDocumentoCobranca() != null){

					geracaoPreFatResumoHelper.setQuantidadeTipo6(1);
				}

				// Caso tenha gerado a linha do tipo 7
				if(!Util.isVazioOuBranco(movimentoRoteiroEmpresa.getDescricaoRubrica1())){

					geracaoPreFatResumoHelper.setQuantidadeTipo7(1);
				}

				// Valor Total dos Serviços
				if(movimentoRoteiroEmpresa.getValorDebitos() != null){

					geracaoPreFatResumoHelper.setValorTotalDebitos(movimentoRoteiroEmpresa.getValorDebitos());
				}else{

					geracaoPreFatResumoHelper.setValorTotalDebitos(BigDecimal.ZERO);
				}

				// Valor Total de Créditos
				if(movimentoRoteiroEmpresa.getValorCreditos() != null){

					geracaoPreFatResumoHelper.setValorTotalCreditos(movimentoRoteiroEmpresa.getValorCreditos());
				}else{

					geracaoPreFatResumoHelper.setValorTotalCreditos(BigDecimal.ZERO);
				}
			}

			geracaoPreFatResumoHelperMap.put(movimentoRoteiroEmpresa.getLocalidade().getId(), geracaoPreFatResumoHelper);

			/*
			 * O sistema indica a geração do arquivo texto para o movimento de leitura atualizando a
			 * tabela MOVIMENTO_ROTEIRO_EMPRESA com os seguintes valores
			 */
			movimentoRoteiroEmpresa.setIndicadorGeracaoArquivoTexto(ConstantesSistema.SIM);
			movimentoRoteiroEmpresa.setIdUsuarioGeracaoArquivoTexto(usuario.getId());
			movimentoRoteiroEmpresa.setTempoGeracaoArquivoTexto(new Date());
		}

		// Grava registro tipo 9 (Trailler) com os seguintes dados
		arquivoEnvio.append("9");

		// Completa com espaços em branco
		arquivoEnvio.append(Util.completaString("", 18));

		// Quantidade de Registros do Tipo 3
		arquivoEnvio.append(Util.completarStringComValorEsquerda(String.valueOf(quantidadeTipo3), "0", 6));

		// Quantidade de Registros do Tipo 4
		arquivoEnvio.append(Util.completarStringComValorEsquerda(String.valueOf(quantidadeTipo4), "0", 6));

		// Quantidade de Registros do Tipo 5
		arquivoEnvio.append(Util.completarStringComValorEsquerda(String.valueOf(quantidadeTipo5), "0", 6));

		// Quantidade de Registros do Tipo 6
		arquivoEnvio.append(Util.completarStringComValorEsquerda(String.valueOf(quantidadeTipo6), "0", 6));

		// Quantidade de Registros do Tipo 7
		arquivoEnvio.append(Util.completarStringComValorEsquerda(String.valueOf(quantidadeTipo7), "0", 6));

		// Completa com espaços em branco
		arquivoEnvio.append(Util.completaString("", 6));

		// Completa com espaços em branco
		arquivoEnvio.append(Util.completaString("", 454));

		// Caractere de controle
		arquivoEnvio.append("#");
		arquivoEnvio.append(System.getProperty("line.separator"));

		Date dataVencimentoHeader = obterDataVencimentoRelatorio(anoMesReferenciaFaturamento, idFaturamentoGrupo, idsRotas);

		// Atualiza os dados de Movimento Roteiro Empresa
		getControladorBatch().atualizarColecaoObjetoParaBatch(colecaoMovimentoRoteiroEmpresa);

		// Gerar Relatório de Totais. [SB0006 – Gerar Resumo do Processamento]
		this.iniciarProcessamentoRelatorioOcorrenciaGeracaoPreFatResumo(idFaturamentoGrupo, anoMesReferenciaFaturamento,
						idFuncionalidadeIniciada, geracaoPreFatResumoHelperMap, dataVencimentoHeader);

		/*
		 * Envia o arquivo gerado para o email especificado e disponibiliza pra download no GSAN
		 */
		EnvioEmail envioEmail = getControladorCadastro().pesquisarEnvioEmail(EnvioEmail.ARQUIVO_DADOS_TABELAS_FATURAMENTO_IMEDIATO);
		this.enviarEmailArquivoFaturamentoImediato(arquivoEnvio, nomeArquivo, envioEmail, idFuncionalidadeIniciada);
	}

	private Date obterDataVencimentoRelatorio(Integer anoMesReferenciaFaturamento, Integer idFaturamentoGrupo, String idsRotas)
					throws ControladorException{

		// Trecho que define a data de vencimento a ser passada pro relatório
		Date dataVencimentoHeader = this.retornarMaiorDtContaVencimentoPorRotaIdFaturamentoAnoMesFaturamento(idsRotas, idFaturamentoGrupo,
						anoMesReferenciaFaturamento);

		if(dataVencimentoHeader == null){
			throw new ControladorException("atencao.faturamento_atividade_cronograma_rota_sem_data_vencimento");
		}
		return dataVencimentoHeader;
	}

	/**
	 * [UC3012] [SB0006 – Obter Débitos Anteriores]
	 * Método reponsável por obter os débitos anteriores
	 * 
	 * @param movimentoRoteiroEmpresa
	 * @return
	 * @throws ControladorException
	 * @throws ErroRepositorioException
	 */
	private List<DebitoAnteriorHelper> obterDebitosAnteriores(MovimentoRoteiroEmpresa movimentoRoteiroEmpresa) throws ControladorException,
					ErroRepositorioException{

		// 1. Caso exista MREM_NNDOCUMENTOCOBRANCA na tabela de MOVIMENTO_ROTEIRO_EMPRESA e exista
		// documento de cobrança gerado para COBRANCA_DOCUMENTO onde CBDO_ID =
		// MREM_NNDOCUMENTOCOBRANCA.
		// O sistema obtêm os itens de débito do cliente a partir da tabela COBRANCA_DOCUMENTO_ITEM
		// com CBDO_ID = MREM_NNDOCUMENTOCOBRANCA.
		// Para cada item encontrado, obtêm a conta relacionada (CONTA com CNTA_ID = CNTA_ID da
		// tabela COBRANCA_DOCUMENTO_ITEM).

		// 2. Caso contrário obter as contas do imóvel onde exista CNTA_ID na tabela de CONTA com
		// IMOV_ID = IMOV_ID da tabela MOVIMENTO_ROTEIRO_EMPRESA e
		// CNTA_DTVENCIMENTOCONTA menor que a data corrente menos (PASI_VLPARAMETRO na tabela
		// PARAMETRO_SISTEMA para PASI_DSPARAMETRO=”P_QUANTIDADE_DIAS_VENCIMENTO_CONTA_AVISO_CORTE”)
		// e não exista pagamento pendente para a conta (NOT EXISTS PAGAMENTO WHERE CNTA_ID =
		// CNTA_ID da tabela CONTA onde IMOV_ID = IMOV_ID da tabela MOVIMENTO_ROTEIRO_EMPRESA) e a
		// conta não esteja em revisão ou esteja em revisão com o motivo de negativação (CMRV_ID IS
		// NULL OR CMRV_ID = CMRV_ID da tabela CONTA_MOTIVO_REVISAO onde CMRV_CDCONSTANTE = ‘
		// REVISAO_SPC ‘) e o valor dos débitos seja maior que (PASI_VLPARAMETRO na tabela
		// PARAMETRO_SISTEMA para PASI_DSPARAMETRO=”P_VALOR_MINIMO_DEBITOS_ANTERIORES”) .

		List<DebitoAnteriorHelper> collDebitoAnterior = new ArrayList<DebitoAnteriorHelper>();
		List<CobrancaDocumentoItem> collCobrancaDocItem = null;
		List<Conta> collConta = null;

		try{

			if(movimentoRoteiroEmpresa.getNumeroDocumentoCobranca() != null){

				collCobrancaDocItem = repositorioFaturamento.obterTotalDebitosContaAnteriores(movimentoRoteiroEmpresa
								.getNumeroDocumentoCobranca());

			}else{

				Integer qtdDiasVencContaAvisoCorte = Integer
								.parseInt((String) ParametroCobranca.P_QUANTIDADE_DIAS_VENCIMENTO_CONTA_AVISO_CORTE.executar(this));
				BigDecimal vlMinDebitosAnteriores = Util
								.formatarStringParaBigDecimal((String) ParametroCobranca.P_VALOR_MINIMO_DEBITOS_ANTERIORES.executar(this));

				collConta = repositorioFaturamento.obterDebitosAnteriores(movimentoRoteiroEmpresa.getImovel().getId(),
								qtdDiasVencContaAvisoCorte, vlMinDebitosAnteriores, ContaMotivoRevisao.REVISAO_SPC);

			}

		}catch(Exception e){

			throw new ControladorException("erro.sistema", e);

		}

		if(collCobrancaDocItem != null){

			for(CobrancaDocumentoItem cobrancaDocumentoItem : collCobrancaDocItem){

				DebitoAnteriorHelper helper = new DebitoAnteriorHelper();
				helper.setValorAcrescimos(cobrancaDocumentoItem.getValorAcrescimos());
				helper.setConta(cobrancaDocumentoItem.getContaGeral().getConta());

				collDebitoAnterior.add(helper);

			}

		}else if(collConta != null){

			SistemaParametro sistemaParametro = this.getControladorUtil().pesquisarParametrosDoSistema();

			for(Conta conta : collConta){

				DebitoAnteriorHelper helper = new DebitoAnteriorHelper();

				Date pagamentoContasMenorData = repositorioFaturamento.obterPagamentoContasMenorData(conta.getId(), conta.getImovel()
								.getId(), conta.getReferencia());
				BigDecimal valorMultasCobradas = repositorioFaturamento.pesquisarValorMultasCobradas(conta.getId());

				CalcularAcrescimoPorImpontualidadeHelper calcularAcrescimoPorImpontualidade = this.getControladorCobranca()
								.calcularAcrescimoPorImpontualidadeBancoDeDados(conta.getReferencia(), conta.getDataVencimentoConta(),
												pagamentoContasMenorData, conta.getValorTotalContaBigDecimal(), valorMultasCobradas,
												conta.getIndicadorCobrancaMulta(), "" + sistemaParametro.getAnoMesArrecadacao(),
												conta.getId(), new Date(), ConstantesSistema.SIM, ConstantesSistema.SIM,
												ConstantesSistema.SIM, ConstantesSistema.SIM);
				helper.setValorAcrescimos(calcularAcrescimoPorImpontualidade.getValorAtualizacaoMonetaria().add(
								calcularAcrescimoPorImpontualidade.getValorJurosMora()).add(
								calcularAcrescimoPorImpontualidade.getValorMulta()));
				helper.setConta(conta);

				collDebitoAnterior.add(helper);

			}

		}

		return collDebitoAnterior;

	}

	/**
	 * [UC3012] Gerar Arquivo Texto Faturamento Imediato
	 * [SB0001] – Gerar Arquivo Texto para Faturamento Imediato – Modelo 2
	 * 
	 * @author Anderson Italo
	 * @date 05/06/2012
	 */
	private void enviarEmailArquivoFaturamentoImediato(StringBuffer conteudoArquivoDados, String nomeArquivoDados, EnvioEmail envioEmail,
					Integer idFuncionalidadeIniciada)
					throws IOException, Exception{

		// Usuário
		Usuario usuario = null;

		// Recupera o usuário logado
		if(idFuncionalidadeIniciada != null){
			usuario = this.getControladorBatch().obterUsuarioFuncionalidadeIniciada(idFuncionalidadeIniciada);
		}else{
			// Insere o usario Batch
			usuario = Usuario.USUARIO_BATCH;
		}

		RelatorioDadosTabelasFaturamentoImediato relatorio = new RelatorioDadosTabelasFaturamentoImediato(usuario);

		relatorio.addParametro("conteudoArquivoDados", conteudoArquivoDados);
		relatorio.addParametro("nomeArquivoDados", nomeArquivoDados);
		relatorio.addParametro("tipoFormatoRelatorio", TarefaRelatorio.TIPO_ZIP);
		relatorio.addParametro("envioEmail", envioEmail);

		this.getControladorBatch().iniciarProcessoRelatorio(relatorio);
	}

	/**
	 * [UC3012] Gerar Arquivo Texto Faturamento Imediato
	 * Obtém o grupo do imóvel
	 * 
	 * @author Anderson Italo
	 * @data 06/06/2012
	 * @param idImovel
	 * @throws ControladorException
	 */
	private String obterGrupoImovel(Integer idImovel) throws ControladorException{

		String retorno = "";
		Integer sequencialOcorrencia = 0;

		/*
		 * Obter a principal categoria do imóvel - (<<Inclui>> [UC0306] - Obter Principal
		 * Categoria do Imóvel, passando como parâmetro o imóvel
		 */
		Categoria categoria = getControladorImovel().obterPrincipalCategoriaImovel(idImovel);

		// Obter o consumo do imóvel em razão da área e da categoria
		FiltroImovelConsumoFaixaAreaCategoria filtroImovelConsumoFaixaAreaCategoria = new FiltroImovelConsumoFaixaAreaCategoria();
		filtroImovelConsumoFaixaAreaCategoria.adicionarParametro(new ParametroSimples(FiltroImovelConsumoFaixaAreaCategoria.IMOVEL_ID,
						idImovel));
		filtroImovelConsumoFaixaAreaCategoria.adicionarParametro(new ParametroSimples(FiltroImovelConsumoFaixaAreaCategoria.CATEGORIA_ID,
						categoria.getId()));

		Collection colecaoImovelConsumoFaixaAreaCategoria = getControladorUtil().pesquisar(filtroImovelConsumoFaixaAreaCategoria,
						ImovelConsumoFaixaAreaCategoria.class.getName());

		// [FS0010] – Verificar existência de consumo do imóvel em razão da área e da categoria
		if(colecaoImovelConsumoFaixaAreaCategoria == null || colecaoImovelConsumoFaixaAreaCategoria.isEmpty()){

			return "0000";
		}

		ImovelConsumoFaixaAreaCategoria imovelConsumoFaixaAreaCategoria = (ImovelConsumoFaixaAreaCategoria) Util
						.retonarObjetoDeColecao(colecaoImovelConsumoFaixaAreaCategoria);

		// Obter as faixas de área x consumo para a categoria do imóvel
		FiltroConsumoFaixaAreaCategoria filtroConsumoFaixaAreaCategoria = new FiltroConsumoFaixaAreaCategoria();
		filtroConsumoFaixaAreaCategoria.adicionarParametro(new ParametroSimples(FiltroConsumoFaixaAreaCategoria.CATEGORIA_ID, categoria
						.getId()));
		filtroConsumoFaixaAreaCategoria.setCampoOrderBy(FiltroConsumoFaixaAreaCategoria.FAIXA_INICIAL_AREA);

		Collection colecaoConsumoFaixaAreaCategoria = getControladorUtil().pesquisar(filtroConsumoFaixaAreaCategoria,
						ConsumoFaixaAreaCategoria.class.getName());

		if(colecaoConsumoFaixaAreaCategoria != null && !colecaoConsumoFaixaAreaCategoria.isEmpty()){

			Iterator itColecaoConsumoFaixaAreaCategoria = colecaoConsumoFaixaAreaCategoria.iterator();
			Integer idConsumoFaixaAreaCategoria = imovelConsumoFaixaAreaCategoria.getComp_id().getConsumoFaixaAreaCategoria().getId();

			// 4. Obter o seqüencial da ocorrência do consumo do imóvel em razão da área e da
			// categoria (item 2) na tabela das faixas de área x consumo para a categoria do
			// imóvel (item 3) - ordem em que CFAC_ID obtido no item 2 ocorre nas ocorrências
			// obtidas no item 3.
			while(itColecaoConsumoFaixaAreaCategoria.hasNext()){

				ConsumoFaixaAreaCategoria consumoFaixaAtual = (ConsumoFaixaAreaCategoria) itColecaoConsumoFaixaAreaCategoria.next();
				sequencialOcorrencia = sequencialOcorrencia.intValue() + 1;

				if(consumoFaixaAtual.getId().equals(idConsumoFaixaAreaCategoria)){
					break;
				}
			}
		}

		// Formatar o Grupo do Imóvel
		if(categoria.getId().equals(Util.converterStringParaInteger("1"))){

			retorno = sequencialOcorrencia.toString() + "000";
		}else if(categoria.getId().equals(Util.converterStringParaInteger("2"))){

			retorno = "0" + sequencialOcorrencia.toString() + "00";
		}else if(categoria.getId().equals(Util.converterStringParaInteger("3"))){

			retorno = "00" + sequencialOcorrencia.toString() + "0";
		}else if(categoria.getId().equals(Util.converterStringParaInteger("4"))){

			retorno = "000" + sequencialOcorrencia.toString();
		}

		return retorno;
	}

	/**
	 * [UC3012 – Gerar Arquivo Texto Faturamento Imediado]
	 * Obter o maior e o menor ano anterior ao ano de referencia do faturamento, com conta vencida.
	 * 
	 * @author Ailton Sousa
	 * @date 23/08/2011
	 * @param idImovel
	 * @param anoFaturamento
	 * @return
	 * @throws ControladorException
	 *             , ErroRepositorioException
	 */
	private String obterUltimosDozeMesesAnterioresReferenciaComContaVencida(Integer idImovel, Integer anoMesFaturamento)
					throws ControladorException, ErroRepositorioException{

		String retorno = "";
		Integer anoFaturamento = Util.converterStringParaInteger(anoMesFaturamento.toString().substring(0, 4));

		Collection colecaoMeses = repositorioFaturamento.obterUltimosDozeMesesAnterioresReferenciaComContaVencida(idImovel, anoFaturamento);

		if(colecaoMeses != null){

			String mesAtual;
			Iterator itMeses = colecaoMeses.iterator();

			while(itMeses.hasNext()){
				mesAtual = (String) itMeses.next();

				if(mesAtual.equals("01") || mesAtual.equals("1")){

					retorno = retorno + "X ";
				}else if(mesAtual.equals("02") || mesAtual.equals("2")){

					if(retorno.equals("") && retorno.length() == 0){

						retorno = retorno + Util.completaString("", 2) + "X ";
					}else{

						retorno = retorno + "X ";
					}
				}else if(mesAtual.equals("03") || mesAtual.equals("3")){

					if(retorno.equals("") && retorno.length() == 0){

						retorno = retorno + Util.completaString("", 4) + "X ";
					}else{

						if(retorno.length() == 4){

							retorno = retorno + "X ";
						}else{

							retorno = retorno + Util.completaString("", 4 - retorno.length()) + "X ";
						}
					}
				}else if(mesAtual.equals("04") || mesAtual.equals("4")){

					if(retorno.equals("") && retorno.length() == 0){

						retorno = retorno + Util.completaString("", 6) + "X ";
					}else{

						if(retorno.length() == 6){

							retorno = retorno + "X ";
						}else{

							retorno = retorno + Util.completaString("", 6 - retorno.length()) + "X ";
						}
					}
				}else if(mesAtual.equals("05") || mesAtual.equals("5")){

					if(retorno.equals("") && retorno.length() == 0){

						retorno = retorno + Util.completaString("", 8) + "X ";
					}else{

						if(retorno.length() == 8){

							retorno = retorno + "X ";
						}else{

							retorno = retorno + Util.completaString("", 8 - retorno.length()) + "X ";
						}
					}
				}else if(mesAtual.equals("06") || mesAtual.equals("6")){

					if(retorno.equals("") && retorno.length() == 0){

						retorno = retorno + Util.completaString("", 10) + "X ";
					}else{

						if(retorno.length() == 10){

							retorno = retorno + "X ";
						}else{

							retorno = retorno + Util.completaString("", 10 - retorno.length()) + "X ";
						}
					}
				}else if(mesAtual.equals("07") || mesAtual.equals("7")){

					if(retorno.equals("") && retorno.length() == 0){

						retorno = retorno + Util.completaString("", 12) + "X ";
					}else{

						if(retorno.length() == 12){

							retorno = retorno + "X ";
						}else{

							retorno = retorno + Util.completaString("", 12 - retorno.length()) + "X ";
						}
					}
				}else if(mesAtual.equals("08") || mesAtual.equals("8")){

					if(retorno.equals("") && retorno.length() == 0){

						retorno = retorno + Util.completaString("", 14) + "X ";
					}else{

						if(retorno.length() == 14){

							retorno = retorno + "X ";
						}else{

							retorno = retorno + Util.completaString("", 14 - retorno.length()) + "X ";
						}
					}
				}else if(mesAtual.equals("09") || mesAtual.equals("9")){

					if(retorno.equals("") && retorno.length() == 0){

						retorno = retorno + Util.completaString("", 16) + "X ";
					}else{

						if(retorno.length() == 16){

							retorno = retorno + "X ";
						}else{

							retorno = retorno + Util.completaString("", 16 - retorno.length()) + "X ";
						}
					}
				}else if(mesAtual.equals("10")){

					if(retorno.equals("") && retorno.length() == 0){

						retorno = retorno + Util.completaString("", 18) + "X ";
					}else{

						if(retorno.length() == 18){

							retorno = retorno + "X ";
						}else{

							retorno = retorno + Util.completaString("", 18 - retorno.length()) + "X ";
						}
					}
				}else if(mesAtual.equals("11")){

					if(retorno.equals("") && retorno.length() == 0){

						retorno = retorno + Util.completaString("", 20) + "X ";
					}else{

						if(retorno.length() == 20){

							retorno = retorno + "X ";
						}else{

							retorno = retorno + Util.completaString("", 20 - retorno.length()) + "X ";
						}
					}
				}else if(mesAtual.equals("12")){

					if(retorno.equals("") && retorno.length() == 0){

						retorno = retorno + Util.completaString("", 22) + "X ";
					}else{

						if(retorno.length() == 22){

							retorno = retorno + "X ";
						}else{

							retorno = retorno + Util.completaString("", 22 - retorno.length()) + "X ";
						}
					}
				}
			}

			if(retorno.length() != 24){

				retorno = retorno + Util.completaString("", 24 - retorno.length());
			}

			String marcacaoMeses = "";

			for(int i = 0; i < retorno.length(); i++){

				if((i % 2) == 0){

					marcacaoMeses += String.valueOf(retorno.charAt(i));
				}
			}

			retorno = marcacaoMeses;

		}else{

			retorno = Util.completaString("", 24);
		}

		return retorno;
	}

	/**
	 * [UC0948] Informar Dados de Leituras e Anormalidades *
	 * 
	 * @param faturamentoGrupo
	 * @param header
	 * @param nomeArquivo
	 * @throws ControladorException
	 */

	public File execParamConverterArquivoLeituraFormato2(ParametroSistema parametroSistema, File arquivoLeitura,
					FaturamentoGrupo faturamentoGrupo) throws FileNotFoundException, ControladorException, IOException, ParseException{

		Collection<String> linhas = new ArrayList<String>();
		FileReader fileReader = new FileReader(arquivoLeitura);
		BufferedReader br = new BufferedReader(fileReader);
		String nomeArquivo = arquivoLeitura.getName();
		validarNomeArquivoFormato2(nomeArquivo);
		String header = br.readLine();
		validarHeaderFormato2(faturamentoGrupo, header, nomeArquivo);
		validarTrailerETipoRegistroFormato2(linhas, br);
		File arquivoDefinitivo = new File(arquivoLeitura.getName() + "-DEFINITIVO.txt");
		FileOutputStream fout = new FileOutputStream(arquivoDefinitivo);
		PrintWriter pw = new PrintWriter(fout);
		gerarHeaderArquivoDefinitivoFormato2(linhas, nomeArquivo, header, pw);
		gerarRegistrosArquivoDefinitivoFormato2(linhas, header, pw);
		gerarTraillerArquivoDefinitivoFormato2(linhas, pw);
		pw.flush();
		pw.close();
		return arquivoDefinitivo;
	}

	/**
	 * [UC0948] Informar Dados de Leituras e Anormalidades
	 * 
	 * @param faturamentoGrupo
	 * @param header
	 * @param nomeArquivo
	 * @throws ControladorException
	 */

	private void validarNomeArquivoFormato2(String nomeArquivo) throws ControladorException{

		if(!"RET".equalsIgnoreCase(recuperarValorLinha(nomeArquivo, 0, 3))){
			throw new ControladorException("atencao.nome_arquivo_conversao_invalido", null, nomeArquivo);
		}
		String idFaturamentoGrupo = recuperarValorLinha(nomeArquivo, 3, 3);
		FaturamentoGrupo faturamentoGrupoNomeArquivo = (FaturamentoGrupo) getControladorUtil().pesquisar(
						Util.obterInteger(idFaturamentoGrupo), FaturamentoGrupo.class, false);
		if(faturamentoGrupoNomeArquivo == null){
			throw new ControladorException("atencao.nome_arquivo_conversao_invalido", null, nomeArquivo);
		}
		String referencia = recuperarValorLinha(nomeArquivo, 6, 6);
		if(Util.validarAnoMesSemBarra(referencia)){
			throw new ControladorException("atencao.nome_arquivo_conversao_invalido", null, nomeArquivo);
		}

	}

	/**
	 * [UC0948] Informar Dados de Leituras e Anormalidades
	 * [SB0101A] – Validar Header do Arquivo Leitura Formato 2
	 * 
	 * @param faturamentoGrupo
	 * @param header
	 * @param nomeArquivo
	 * @throws ControladorException
	 */

	private void validarHeaderFormato2(FaturamentoGrupo faturamentoGrupo, String header, String nomeArquivo) throws ControladorException{

		int posicaoAnterior = 0;
		int qtidadeCaracteres = 0;

		String tipoRegistro = recuperarValorLinha(header, posicaoAnterior += qtidadeCaracteres, qtidadeCaracteres = 1);
		if(!"A".equals(tipoRegistro)){
			throw new ControladorException("atencao.arquivo_sem_header", null, nomeArquivo);
		}

		String referenciaFaturamentoString = recuperarValorLinha(header, posicaoAnterior += qtidadeCaracteres, qtidadeCaracteres = 6);
		Integer referenciaFaturamento = Util.obterInteger(referenciaFaturamentoString);
		if(referenciaFaturamento == null){
			throw new ControladorException("atencao.arquivo_header_ano_mes_nao_numerico", null, referenciaFaturamentoString);
		}
		if(Util.validarAnoMesSemBarra(referenciaFaturamentoString)){
			throw new ControladorException("atencao.arquivo_header_ano_mes_invalido", null, referenciaFaturamentoString);
		}
		if(!faturamentoGrupo.getAnoMesReferencia().equals(referenciaFaturamento)){
			throw new ControladorException("atencao.arquivo_header_ano_mes_diferente_ano_mes_faturamento_grupo", null,
							referenciaFaturamentoString, faturamentoGrupo.getAnoMesReferencia().toString());
		}

		recuperarValorLinha(header, posicaoAnterior += qtidadeCaracteres, qtidadeCaracteres = 3);

		String dataLeituraString = recuperarValorLinha(header, posicaoAnterior += qtidadeCaracteres, qtidadeCaracteres = 8);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Integer dataLeituraNumerica = Util.obterInteger(dataLeituraString);
		if(dataLeituraNumerica == null){
			throw new ControladorException("atencao.arquivo_header_data_leitura_nao_numerico", null, dataLeituraString);
		}
		Date dataLeitura = null;
		if(Util.validarAnoMesDiaSemBarra(dataLeituraString)){
			throw new ControladorException("atencao.arquivo_header_data_leitura_invalida", null, dataLeituraString);
		}
		try{
			dataLeitura = sdf.parse(dataLeituraString);
		}catch(ParseException e){
			throw new ControladorException("atencao.arquivo_header_data_leitura_invalida", null, dataLeituraString);
		}
		if(dataLeitura.after(new Date())){
			throw new ControladorException("atencao.arquivo_header_data_leitura_invalida", null, dataLeituraString);
		}

		if(Util.getAnoMesComoInt(dataLeitura) != faturamentoGrupo.getAnoMesReferencia().intValue()
						&& Util.getAnoMesComoInt(dataLeitura) != Util.somarData(faturamentoGrupo.getAnoMesReferencia().intValue())
						&& Util.getAnoMesComoInt(dataLeitura) != Util.subtrairData(faturamentoGrupo.getAnoMesReferencia().intValue())){
			throw new ControladorException("atencao.arquivo_header_data_leitura_diferente_data_leitura_faturamento_grupo_tela", null,
							dataLeituraString, faturamentoGrupo.getAnoMesReferencia().toString());
		}
	}

	/**
	 * [UC0948] Informar Dados de Leituras e Anormalidades
	 * [SB0101] – Converter Arquivo Leitura Formato 2
	 * 
	 * @param linhas
	 * @param br
	 * @throws IOException
	 * @throws ControladorException
	 */
	private void validarTrailerETipoRegistroFormato2(Collection<String> linhas, BufferedReader br) throws IOException, ControladorException{

		Boolean achouTrailler = Boolean.FALSE;
		Integer contadorLinhas = 0;
		String linha;
		String tipoRegistro;
		while((linha = br.readLine()) != null){
			tipoRegistro = linha.substring(0, 1);
			if("Z".equals(tipoRegistro)){
				achouTrailler = Boolean.TRUE;
				break;
			}else if("A".equals(tipoRegistro)){
				throw new ControladorException("atencao.arquivo_retorno_nao_possui_trailler");
			}else if("B".equals(tipoRegistro)){
				linhas.add(linha);
				contadorLinhas++;
			}else{
				throw new ControladorException("atencao.arquivo_retorno_registro_tipo_invalido");
			}
		}
		if(!achouTrailler){
			throw new ControladorException("atencao.arquivo_retorno_nao_possui_trailler");
		}
		String qtidadeTotalRegistros = recuperarValorLinha(linha, 1, 5);
		if(!Util.obterInteger(qtidadeTotalRegistros).equals(contadorLinhas)){
			throw new ControladorException("atencao.arquivo_retorno_quantidade_total_diferente_quantidade_total_trailler", null,
							contadorLinhas.toString(), qtidadeTotalRegistros);
		}

	}

	/**
	 * [UC0948] Informar Dados de Leituras e Anormalidades
	 * [SB0101] – Converter Arquivo Leitura Formato 2
	 * 
	 * @param linhas
	 * @param nomeArquivo
	 * @param header
	 * @param pw
	 * @throws ControladorException
	 */
	private void gerarHeaderArquivoDefinitivoFormato2(Collection<String> linhas, String nomeArquivo, String header, PrintWriter pw)
					throws ControladorException{

		StringBuilder headerArquivoDefinitivo = new StringBuilder();
		// 0
		headerArquivoDefinitivo.append("0");
		// empresa
		headerArquivoDefinitivo.append(Util.completaStringComEspacoAEsquerda("", 6));
		// Indicador de transmissão ou recepção
		headerArquivoDefinitivo.append("R");
		// Grupo Faturamento - A.03
		String grupoFaturamentoHeader = recuperarValorLinha(header, 7, 3);
		headerArquivoDefinitivo.append(Util.completarStringZeroEsquerda(Util.obterInteger(grupoFaturamentoHeader).toString(), 2));
		// Ano e mês ref. faturamento
		String anoMesReferenciaHeader = recuperarValorLinha(header, 1, 6);
		headerArquivoDefinitivo.append(Util.completarStringZeroEsquerda(anoMesReferenciaHeader, 6));
		// Data geração arquivo
		String dataGeracaoArquivo = recuperarDataCorrente();
		headerArquivoDefinitivo.append(Util.completarStringZeroEsquerda(dataGeracaoArquivo, 8));
		// Quantidade de registros
		headerArquivoDefinitivo.append(Util.completarStringZeroEsquerda(String.valueOf(linhas.size() + 2), 6));

		pw.println(headerArquivoDefinitivo.toString());
	}

	/**
	 * [UC0948] Informar Dados de Leituras e Anormalidades
	 * [SB0101A] – Validar Header do Arquivo Leitura Formato 2
	 * 
	 * @param linhas
	 * @param header
	 * @param pw
	 * @throws ControladorException
	 * @throws ParseException
	 */
	private void gerarRegistrosArquivoDefinitivoFormato2(Collection<String> linhas, String header, PrintWriter pw)
					throws ControladorException, ParseException{

		StringBuilder registro = null;

		String matriculaImovel = null;
		String leituraAtualReal = null;
		String leituraFaturada = null;
		String consumoFaturado = null;
		String indicadorConfirmacaoLeitura = null;
		String codigoAnormalidadeLeitura = null;
		String codigoAnormalidadeConsumo = null;
		Map<String, Integer> mapaTipoConsumo = montarMapaTipoConsumoValidosFormato2();
		String diasConsumo = null;
		String valorAgua = null;
		String valorEsgoto = null;
		String valorDebitos = null;
		String valorImpostoFederal = null;
		String dataVencimento = null;
		String dataLeituraAtual = null;
		String dataLeituraMes = null;
		String matriculaFuncionario = null;
		String valorRateio = null;
		String indicadorFatResiduoRateio = null;
		Map<String, Integer> mapaIndicadorEmissao = montarMapaIndicadorEmissaoValidosFormato2();
		BigDecimal valorConta = null;
		
		FiltroMedicaoTipo filtroMedicaoTipo = new FiltroMedicaoTipo();
		filtroMedicaoTipo.adicionarParametro(new ParametroSimples(FiltroMedicaoTipo.DESCRICAO, MedicaoTipo.DESC_LIGACAO_AGUA));
		MedicaoTipo medicaoTipo = (MedicaoTipo) Util.retonarObjetoDeColecao(getControladorUtil().pesquisar(filtroMedicaoTipo,
						MedicaoTipo.class.getName()));

		Integer i = 1;

		for(String linha : linhas){

			registro = new StringBuilder();

			// Tipo do Registro
			registro.append("1");

			// Identificação do Agente Comercial - B.32
			matriculaFuncionario = recuperarValorLinha(linha, 166, 8);
			if(Util.isInteger(matriculaFuncionario)){
				registro.append(recuperarValorLinha(matriculaFuncionario, 2, 6));
			}else{
				throw new ControladorException("atencao.arquivo_linha_invalido", null, i.toString(), linha);
			}

			// Data de Leitura (DDMMAAAA) - B.26
			dataLeituraMes = recuperarValorLinha(linha, 150, 8);
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat sdf2 = new SimpleDateFormat("ddMMyyyy");
			dataLeituraMes = sdf2.format(sdf1.parse(dataLeituraMes));

			if(Util.isInteger(dataLeituraMes)){

				registro.append(Util.completarStringZeroEsquerda(dataLeituraMes, 8));
			}else{

				throw new ControladorException("atencao.arquivo_linha_invalido", null, i.toString(), linha);
			}

			// Matrícula do Imóvel - B.02
			matriculaImovel = recuperarValorLinha(linha, 1, 10);
			if(Util.isInteger(matriculaImovel)){
				registro.append(recuperarValorLinha(matriculaImovel, 2, 8));
			}else{
				throw new ControladorException("atencao.arquivo_linha_invalido", null, i.toString(), linha);
			}

			// Código da Localidade
			registro.append(Util.completarStringZeroEsquerda("0", 3));

			// Código do Setor Comercial
			registro.append(Util.completarStringZeroEsquerda("0", 3));

			// Número da Quadra
			registro.append(Util.completarStringZeroEsquerda("0", 4));

			// Número do Lote
			registro.append(Util.completarStringZeroEsquerda("0", 4));

			// Número do Sublote
			registro.append(Util.completarStringZeroEsquerda("0", 3));

			// Tipo de Medição
			registro.append(medicaoTipo.getId().toString());

			// Leitura do Hidrômetro - B.03
			leituraAtualReal = recuperarValorLinha(linha, 11, 8);
			if(Util.isInteger(leituraAtualReal)){
				registro.append(Util.completarStringZeroEsquerda(Util.obterInteger(leituraAtualReal).toString(), 6));
			}else{
				registro.append(Util.completarStringZeroEsquerda("0", 6));
			}
			// Leitura Faturada - B.04 se não for 0(zero), caso contrário B.03
			leituraFaturada = recuperarValorLinha(linha, 19, 8);
			if(Util.converterStringParaInteger(leituraFaturada) > 0){
				registro.append(Util.completarStringZeroEsquerda(Util.obterInteger(leituraFaturada).toString(), 6));
			}else{
				registro.append(Util.completarStringZeroEsquerda(Util.obterInteger(leituraAtualReal).toString(), 6));
			}

			// Consumo Faturado - Caso B.08 seja igual a “R” B.05, caso contrario B.06
			String consumoTipo = recuperarValorLinha(linha, 40, 1);
			if(consumoTipo.equals("R")){
				consumoFaturado = recuperarValorLinha(linha, 27, 6);
			}else{
				consumoFaturado = recuperarValorLinha(linha, 33, 6);
			}
			if(Util.isInteger(consumoFaturado)){
				registro.append(Util.completarStringZeroEsquerda(consumoFaturado, 6));
			}else{
				throw new ControladorException("atencao.arquivo_linha_invalido", null, i.toString(), linha);
			}

			// Código da Anormalidade de Leitura - B.10
			codigoAnormalidadeLeitura = recuperarValorLinha(linha, 42, 2);
			registro.append(Util.completarStringZeroEsquerda(codigoAnormalidadeLeitura, 3));

			// Código da Anormalidade de Consumo - B.11 [SB0101C – Converter Anormalidade Consumo
			// GCS para Anormalidade de Consumo GSAN]
			codigoAnormalidadeConsumo = converterAnormalidadeConsumoParaGsan(recuperarValorLinha(linha, 44, 2));
			registro.append(Util.completarStringZeroEsquerda(codigoAnormalidadeConsumo, 3));

			// Tipo do Consumo - B.08 [SB0101B – Converter Tipo Consumo para Tipo Consumo GSAN]
			if(mapaTipoConsumo.containsKey(recuperarValorLinha(linha, 40, 1))){
				registro.append(Util.completarStringZeroEsquerda(mapaTipoConsumo.get(recuperarValorLinha(linha, 40, 1)).toString(), 2));
			}else{
				throw new ControladorException("atencao.arquivo_linha_tipo_consumo_invalido", null, i.toString());
			}

			// Dias de Consumo - B.19
			diasConsumo = recuperarValorLinha(linha, 121, 2);
			if(Util.isInteger(diasConsumo)){
				registro.append(Util.completarStringZeroEsquerda(diasConsumo, 2));
			}else{
				throw new ControladorException("atencao.arquivo_linha_invalido", null, i.toString(), linha);
			}

			// Valor de Água - B.12
			valorAgua = recuperarValorLinha(linha, 46, 11);
			if(Util.isNumero(valorAgua, false, 0)){
				registro.append(Util.completarStringZeroEsquerda(valorAgua, 11));
			}else{
				throw new ControladorException("atencao.arquivo_linha_invalido", null, i.toString(), linha);
			}

			// Valor de Esgoto - B.13
			valorEsgoto = recuperarValorLinha(linha, 57, 11);
			if(Util.isNumero(valorEsgoto, false, 0)){
				registro.append(Util.completarStringZeroEsquerda(valorEsgoto, 11));
			}else{
				throw new ControladorException("atencao.arquivo_linha_invalido", null, i.toString(), linha);
			}

			// Valor Débitos - B.14
			valorDebitos = recuperarValorLinha(linha, 68, 11);
			if(Util.isNumero(valorDebitos, false, 0)){
				registro.append(Util.completarStringZeroEsquerda(valorDebitos, 11));
			}else{
				throw new ControladorException("atencao.arquivo_linha_invalido", null, i.toString(), linha);
			}

			// Valor Créditos
			registro.append(Util.completarStringZeroEsquerda("0", 11));

			// Indicador de Grande Consumidor
			registro.append(Util.completarStringZeroEsquerda("0", 1));

			// Indicador de Emissão
			valorConta = new BigDecimal(valorAgua).add(new BigDecimal(valorEsgoto)).add(new BigDecimal(valorDebitos));
			
			if(valorConta.compareTo(BigDecimal.ZERO) == 1 && recuperarValorLinha(linha, 123, 1).equals("N")){
				
				registro.append(MovimentoRoteiroEmpresa.INDICADOR_EMISSAO_CAMPO_CONTA_IMPRESSA.toString());
			}else if(mapaIndicadorEmissao.containsKey(recuperarValorLinha(linha, 123, 1))){

				registro.append(Util
								.completarStringZeroEsquerda(mapaIndicadorEmissao.get(recuperarValorLinha(linha, 123, 1)).toString(), 1));
			}else{

				throw new ControladorException("atencao.arquivo_linha_invalido", null, i.toString());
			}

			// Indicador de Confirmação de Leitura - B.07
			indicadorConfirmacaoLeitura = recuperarValorLinha(linha, 39, 1);

			if(indicadorConfirmacaoLeitura != null && Util.isInteger(indicadorConfirmacaoLeitura)){

				if(indicadorConfirmacaoLeitura.equals("0")){

					registro.append(Util.completarStringZeroEsquerda(LeituraSituacao.NAO_REALIZADA.toString(), 1));
				}else if(indicadorConfirmacaoLeitura.equals("1")){

					registro.append(Util.completarStringZeroEsquerda(LeituraSituacao.REALIZADA.toString(), 1));
				}else if(indicadorConfirmacaoLeitura.equals("2")){

					registro.append(Util.completarStringZeroEsquerda(LeituraSituacao.CONFIRMADA.toString(), 1));
				}else if(indicadorConfirmacaoLeitura.equals("3")){

					registro.append(Util.completarStringZeroEsquerda(LeituraSituacao.RECONFIRMADA.toString(), 1));
				}else{

					registro.append(Util.completarStringZeroEsquerda(LeituraSituacao.INDEFINIDO.toString(), 1));
				}

			}else{

				registro.append(Util.completarStringZeroEsquerda(LeituraSituacao.NAO_REALIZADA.toString(), 1));
			}

			// Valor Imposto Federal - B.16
			valorImpostoFederal = recuperarValorLinha(linha, 90, 9);
			if(Util.isNumero(valorImpostoFederal, false, 0)){
				registro.append(Util.completarStringZeroEsquerda(valorImpostoFederal, 11));
			}else{
				throw new ControladorException("atencao.arquivo_linha_invalido", null, i.toString(), linha);
			}

			// Data Vencimento Retornada - B.24
			dataVencimento = recuperarValorLinha(linha, 134, 8);
			SimpleDateFormat sdf3 = new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat sdf4 = new SimpleDateFormat("ddMMyyyy");
			dataVencimento = sdf4.format(sdf3.parse(dataVencimento));

			if(!Util.isVazioOuBranco(dataVencimento)){
				registro.append(Util.completarStringZeroEsquerda(dataVencimento, 8));
			}else{
				throw new ControladorException("atencao.arquivo_linha_invalido", null, i.toString());
			}

			// Data de Leitura - B.26 se não for 0 (zero), caso contrário B.25
			dataLeituraAtual = recuperarValorLinha(linha, 150, 8);
			SimpleDateFormat sdf5 = new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat sdf6 = new SimpleDateFormat("ddMMyyyy");
			dataLeituraAtual = sdf6.format(sdf5.parse(dataLeituraAtual));

			if(!Util.isVazioOuBranco(dataLeituraAtual)){
				registro.append(Util.completarStringZeroEsquerda(dataLeituraAtual, 8));
			}else{

				dataLeituraAtual = recuperarValorLinha(linha, 142, 8);
				SimpleDateFormat sdf7 = new SimpleDateFormat("yyyyMMdd");
				SimpleDateFormat sdf8 = new SimpleDateFormat("ddMMyyyy");
				dataLeituraAtual = sdf8.format(sdf7.parse(dataLeituraAtual));

				registro.append(Util.completarStringZeroEsquerda(dataLeituraAtual, 8));
			}

			// Valor do Rateio - B.37
			valorRateio = recuperarValorLinha(linha, 246, 11);
			if(!Util.isVazioOuBranco(valorRateio)){
				registro.append(Util.completarStringZeroEsquerda(valorRateio, 11));
			}else{
				throw new ControladorException("atencao.arquivo_linha_invalido", null, i.toString());
			}

			// Indicador Faturamento Residuo Rateio - B.38
			indicadorFatResiduoRateio = recuperarValorLinha(linha, 257, 1);
			if(!Util.isVazioOuBranco(indicadorFatResiduoRateio)){
				registro.append(Util.completarStringZeroEsquerda(indicadorFatResiduoRateio, 1));
			}else{
				throw new ControladorException("atencao.arquivo_linha_invalido", null, i.toString());
			}

			i++;
			pw.println(registro.toString());
		}
	}

	/**
	 * [UC0948] Informar Dados de Leituras e Anormalidades
	 * [SB0101B] – Validar Header do Arquivo Leitura Formato 2
	 * 
	 * @param linhas
	 * @param header
	 * @param pw
	 * @throws ControladorException
	 * @throws ParseException
	 */

	private Map<String, Integer> montarMapaTipoConsumoValidosFormato2(){

		Map<String, Integer> mapaTipoConsumo = new HashMap<String, Integer>();
		mapaTipoConsumo.put("A", 1);
		mapaTipoConsumo.put("E", 2);
		mapaTipoConsumo.put("F", 3);
		mapaTipoConsumo.put("I", 4);
		mapaTipoConsumo.put("M", 5);
		mapaTipoConsumo.put("N", 6);
		mapaTipoConsumo.put("R", 7);

		return mapaTipoConsumo;

	}

	/**
	 * [UC0948] Informar Dados de Leituras e Anormalidades
	 * [SB0102B] – Validar Header do Arquivo Leitura Formato 3
	 * 
	 * @param linhas
	 * @param header
	 * @param pw
	 * @throws ControladorException
	 * @throws ParseException
	 */

	private Map<String, Integer> montarMapaTipoConsumoValidosFormato3(){

		Map<String, Integer> mapaTipoConsumo = new HashMap<String, Integer>();
		mapaTipoConsumo.put("A", 1);
		mapaTipoConsumo.put("E", 2);
		mapaTipoConsumo.put("I", 4);
		mapaTipoConsumo.put("M", 5);
		mapaTipoConsumo.put("N", 6);
		mapaTipoConsumo.put("0", 7);
		mapaTipoConsumo.put("R", 7);

		return mapaTipoConsumo;

	}

	/**
	 * [UC0948] Informar Dados de Leituras e Anormalidades
	 * [SB0101D] – Validar Header do Arquivo Leitura Formato 2
	 * 
	 * @param linhas
	 * @param header
	 * @param pw
	 * @throws ControladorException
	 * @throws ParseException
	 */
	private Map<String, Integer> montarMapaIndicadorEmissaoValidosFormato2(){

		Map<String, Integer> mapaIndicadorEmissao = new HashMap<String, Integer>();
		mapaIndicadorEmissao.put("0", 2); // 0 – Despreza registro
		mapaIndicadorEmissao.put("N", 2); // N – Sem leitura
		mapaIndicadorEmissao.put("L", 2); // L – Só leitura
		mapaIndicadorEmissao.put("I", 1); // I – Conta Normal
		mapaIndicadorEmissao.put("3", 2); // 0 – Quem é 3?
		mapaIndicadorEmissao.put("1", 2); // 0 – Quem é 1?
		mapaIndicadorEmissao.put("6", 2); // 0 – Quem é 6?
		mapaIndicadorEmissao.put("4", 2); // 0 – Quem é 4?

		return mapaIndicadorEmissao;
	}

	/**
	 * [UC0948] Informar Dados de Leituras e Anormalidades
	 * [SB0101A] – Validar Header do Arquivo Leitura Formato 2
	 * 
	 * @param linhas
	 * @param header
	 * @param pw
	 * @throws ControladorException
	 * @throws ParseException
	 */
	private void gerarTraillerArquivoDefinitivoFormato2(Collection<String> linhas, PrintWriter pw){

		StringBuilder traillerArquivoDefinitivo = new StringBuilder();
		// Tipo do Registro
		traillerArquivoDefinitivo.append("9");
		// Quantidade Registros
		traillerArquivoDefinitivo.append(Util.completarStringZeroEsquerda(String.valueOf(linhas.size() + 2), 6));
		// Quantidade de Registros Tipo Detalhe
		traillerArquivoDefinitivo.append(Util.completarStringZeroEsquerda(String.valueOf(linhas.size()), 6));

		pw.println(traillerArquivoDefinitivo.toString());
	}

	/**
	 * [UC0948] Informar Dados de Leituras e Anormalidades
	 * [SB0101C] – Converter Anormalidade Consumo para Anormalidade de Consumo Gsan
	 * [SB0103C] – Converter Anormalidade Consumo para Anormalidade de Consumo Gsan
	 */
	private String converterAnormalidadeConsumoParaGsan(String campo) throws ControladorException{

		String codigoAnormalidadeConsumo = null;
		if(!Util.isInteger(campo) && campo.trim().length() > 0){
			FiltroConsumoAnormalidade filtroConsumoAnormalidade = new FiltroConsumoAnormalidade();
			filtroConsumoAnormalidade.adicionarParametro(new ComparacaoTextoCompleto(FiltroConsumoAnormalidade.DESCRICAO_ABREVIADA, campo));

			ConsumoAnormalidade consumoAnormalidade = (ConsumoAnormalidade) Util.retonarObjetoDeColecao(getControladorUtil().pesquisar(
							filtroConsumoAnormalidade, ConsumoAnormalidade.class.getName()));

			if(consumoAnormalidade == null){
				throw new ControladorException("atencao.arquivo_linha_anormalidade_consumo_inexistente", null, campo);
			}
			codigoAnormalidadeConsumo = consumoAnormalidade.getId().toString();
		}else if(!Util.isInteger(campo) && campo.trim().length() == 0){
			codigoAnormalidadeConsumo = "0";
		}else if(Util.isInteger(campo) && Util.obterInteger(campo).intValue() != 0){
			codigoAnormalidadeConsumo = "0";
		}else if(Util.isInteger(campo) && Util.obterInteger(campo).intValue() == 0){
			codigoAnormalidadeConsumo = "0";
		}
		return codigoAnormalidadeConsumo;

	}

	private Map<String, Integer> montarMapaTipoResponsavelFormato1(){

		Map<String, Integer> mapaTipoResponsavel = new HashMap<String, Integer>();
		mapaTipoResponsavel.put("28", 1);
		mapaTipoResponsavel.put("27", 2);
		mapaTipoResponsavel.put("29", 3);
		mapaTipoResponsavel.put("26", 4);
		mapaTipoResponsavel.put("3", 4);
		mapaTipoResponsavel.put("4", 4);
		mapaTipoResponsavel.put("58", 4);
		mapaTipoResponsavel.put("54", 5);
		mapaTipoResponsavel.put("55", 6);
		mapaTipoResponsavel.put("25", 7);
		mapaTipoResponsavel.put("1", 7);
		mapaTipoResponsavel.put("2", 7);
		mapaTipoResponsavel.put("16", 7);
		mapaTipoResponsavel.put("24", 7);
		mapaTipoResponsavel.put("57", 8);
		mapaTipoResponsavel.put("56", 9);

		return mapaTipoResponsavel;
	}

	/**
	 * [UC3012 – Gerar Arquivo Texto Faturamento Imediado]
	 * [SB0004] – Obter Tipo do Responsável GCS
	 * 
	 * @author Yara Souza
	 * @date 17/12/2011
	 * @param idImovel
	 * @return
	 * @throws ControladorException
	 */
	private Integer obterTipoResponsavel(Integer idImovel) throws ControladorException{

		// [FS0008] – Verificar não existência do tipo do responsável GCS correspondente
		// Caso não exista Tipo do Responsável GCS correspondente ao Tipo do Cliente GSAN,
		// atribuir o valor 0 (zero) ao Tipo do Responsável GCS.

		Integer idClienteRelacaoTipoConvertido = 0;

		try{

			Integer idClienteRelacaoTipo = repositorioClienteImovel.retornaTipoClienteImovel(idImovel);

			if(idClienteRelacaoTipo != null){
				Map<String, Integer> map = montarMapaTipoResponsavelFormato1();

				if(map.get(idClienteRelacaoTipo.toString()) != null){
					idClienteRelacaoTipoConvertido = map.get(idClienteRelacaoTipo.toString());
				}
			}

		}catch(ErroRepositorioException e){

			e.printStackTrace();
			throw new ControladorException("erro.sistema", e);
		}

		return idClienteRelacaoTipoConvertido;
	}

	private Map<String, Integer> montarMapaLeituraSituacaoAnteriorModelo1(){

		Map<String, Integer> mapaLeituraSituacao = new HashMap<String, Integer>();
		mapaLeituraSituacao.put("2", 0);
		mapaLeituraSituacao.put("1", 1);
		mapaLeituraSituacao.put("3", 2);

		return mapaLeituraSituacao;
	}

	private ControladorCobrancaLocal controladorCobranca;

	private ControladorCobrancaLocal getControladorCobranca(){

		if(controladorCobranca == null){

			ControladorCobrancaLocalHome localHome = null;
			ServiceLocator locator = null;

			try{
				locator = ServiceLocator.getInstancia();

				localHome = (ControladorCobrancaLocalHome) locator.getLocalHomePorEmpresa(ConstantesJNDI.CONTROLADOR_COBRANCA_SEJB);
				controladorCobranca = localHome.create();

				return controladorCobranca;
			}catch(CreateException e){

				throw new SistemaException(e);
			}catch(ServiceLocatorException e){

				throw new SistemaException(e);
			}
		}else{

			return controladorCobranca;
		}
	}

	/**
	 * [UC3012] Gerar Arquivo Texto Faturamento Imediato
	 * [SB0101] – Gerar Integração para Faturamento Imediato – Modelo 3
	 * Método responsável pela geração dos dados para integração com o faturamento imediato da
	 * CAGEPA
	 * 
	 * @author Anderson Italo
	 * @date 27/03/2013
	 */
	public void execParamGerarArquivoTextoFaturamentoImediatoModelo3(ParametroSistema parametroSistema,
					Integer anoMesReferenciaFaturamento,
					Integer idFaturamentoGrupo, Integer idFuncionalidadeIniciada, Integer idEmpresa, String idsRotas,
					Collection colecaoMovimentoRoteiroEmpresa) throws Exception{

		LOGGER.info("...............Início Geração do Arquivo Texto Faturamento Imediato Envio");
		this.ejbCreate();

		/*
		 * O sistema gera os dados do faturamento imediato em arquivo texto(METLIGACAO) com campos
		 * delimitados por vírgula, utilizando aspas duplas(“ ”) para campos CHAR
		 */
		StringBuffer arquivoMetLigacao = gerarArquivoMetLigacao(anoMesReferenciaFaturamento, colecaoMovimentoRoteiroEmpresa,
						idFuncionalidadeIniciada);

		/*
		 * MEVREAVISO - Quando MREM_NNDOCUMENTOCOBRANCA diferente de nulo, gerar uma linha nesse
		 * arquivo texto para cada documento(conta) vinculada ao documento de cobrança
		 */
		StringBuffer arquivoMevReaviso = gerarArquivoMevReaviso(idFaturamentoGrupo, anoMesReferenciaFaturamento);

		/*
		 * MENSAGEM_FATURA_ME - Gerar arquivo texto delimitado a partir de CONTA_MENSAGEM para
		 * CTMS_AMREFERENCIAFATURAMENTO menor ou igual à PARM_AMREFERENCIAFATURAMENTO a mais recente
		 */
		StringBuffer arquivoMensagemFaturaMe = gerarArquivoMensagemFaturaMe(anoMesReferenciaFaturamento, idFaturamentoGrupo, idsRotas);

		// METAPOIO - Gerar arquivo texto delimitado
		StringBuffer arquivoMetApoio = gerarArquivoMetApoio();

		/*
		 * MEVTARIFA - Gerar arquivo texto delimitado a partir de CONSUMO_TARIFA(e filhas) para cada
		 * tarifa existente, considerando a maior vigência(CSTV_DTVIGENCIA de
		 * CONSUMO_TARIFA_VIGENCIA pata CSTF_ID = CSTF_ID de CONSUMO_TARIFA) menor ou igual a
		 * referencia de faturamento(PARM_AMREFERENCIAFATURAMENTO)
		 */
		StringBuffer arquivoMevTarifa = gerarArquivoMevTarifa(anoMesReferenciaFaturamento);

		// MEVANORMALIDADE - Gerar arquivo texto delimitado a partir de LEITURA_AMORMALIDADE
		StringBuffer arquivoMevAnormalidade = gerarArquivoMevAnormalidade();

		/*
		 * MEVQUALIDADEAGUA - Gerar arquivo texto delimitado a partir de QUALIDADE_AGUA para
		 * QLAG_AMREFERENCIA igual ou anterior mais próxima a referencia de
		 * faturamento(PARM_AMREFERENCIAFATURAMENTO)
		 */
		StringBuffer arquivoMevQualidadeAgua = gerarArquivoMevQualidadeAgua(idsRotas, anoMesReferenciaFaturamento);

		// Atualiza os dados de Movimento Roteiro Empresa
		getControladorBatch().atualizarColecaoObjetoParaBatch(colecaoMovimentoRoteiroEmpresa);

		// Envia o arquivo gerado para o email especificado e disponibiliza pra download no GSAN
		String nomeArquivoMetLigacao = "METLIGACAO" + Util.completarStringZeroEsquerda(idFaturamentoGrupo.toString(), 3)
						+ anoMesReferenciaFaturamento.toString();
		String nomeArquivoZipado = nomeArquivoMetLigacao + ".zip";

		EnvioEmail envioEmail = getControladorCadastro().pesquisarEnvioEmail(EnvioEmail.ARQUIVO_DADOS_TABELAS_FATURAMENTO_IMEDIATO);
		this.enviarEmailArquivoFaturamentoImediato(arquivoMetLigacao, arquivoMevReaviso, arquivoMensagemFaturaMe, arquivoMetApoio,
						arquivoMevTarifa, arquivoMevAnormalidade, arquivoMevQualidadeAgua, nomeArquivoMetLigacao, "MEVREAVISO",
						"MENSAGEM_FATURA_ME", "METAPOIO", "MEVTARIFA", "MEVANORMALIDADE", "MEVQUALIDADEAGUA", envioEmail,
						idFuncionalidadeIniciada, nomeArquivoZipado);

		LOGGER.info("...............Fim Geração do Arquivo Texto Faturamento Imediato Envio");
	}

	/**
	 * [UC3012] Gerar Arquivo Texto Faturamento Imediato
	 * [SB0101] – Gerar Integração para Faturamento Imediato – Modelo 3
	 * Método responsável pela geração dos dados para integração com o faturamento imediato da
	 * CAGEPA
	 * 
	 * @author Anderson Italo
	 * @throws ErroRepositorioException
	 * @throws ControladorException
	 * @date 02/04/2013
	 */
	private StringBuffer gerarArquivoMevQualidadeAgua(String idsRotas, Integer anoMesReferenciaFaturamento) throws ErroRepositorioException{

		LOGGER.info("...............Início Geração MEVQUALIDADEAGUA");
		StringBuffer arquivoMevQualidadeAgua = new StringBuffer();
		String virgula = ",";
		String aspaDupla = "\"";

		Collection<QualidadeAgua> colecaoQualidadeAgua = new ArrayList<QualidadeAgua>();
		Collection<Integer> colecaoIdsLocalidadeQualidadeAgua = new ArrayList<Integer>();

		List<Object[]> colecaoSetorComercial = pesquisarSetorComercialFaturamentoImediatoComLimiteRotas(idsRotas);

		for(Object[] setorComercial : colecaoSetorComercial){

			QualidadeAgua qualidadeAgua = repositorioFaturamento.pesquisarQualidadeAguaPorLocalidadeAnoMesFaturamento(
							anoMesReferenciaFaturamento, Util.obterInteger(setorComercial[0].toString()));

			if(qualidadeAgua != null && !colecaoIdsLocalidadeQualidadeAgua.contains(qualidadeAgua.getLocalidade().getId())){

				colecaoIdsLocalidadeQualidadeAgua.add(qualidadeAgua.getLocalidade().getId());
				colecaoQualidadeAgua.add(qualidadeAgua);
			}
		}

		int[] indicesQualidadeAgua = new int[] {1, 2, 3, 4, 5};
		for(QualidadeAgua qualidadeAgua : colecaoQualidadeAgua){

			for(int i = 1; i <= indicesQualidadeAgua.length; i++){

				// LOCALIDADE
				arquivoMevQualidadeAgua.append(qualidadeAgua.getLocalidade().getId().toString() + virgula);

				// MES_REFERENCIA
				arquivoMevQualidadeAgua.append(qualidadeAgua.getAnoMesReferencia().toString().substring(4) + virgula);

				// ANO_REFERENCIA
				arquivoMevQualidadeAgua.append(qualidadeAgua.getAnoMesReferencia().toString().substring(0, 4) + virgula);

				if(i == 1){

					// PK_PARAMETRO
					arquivoMevQualidadeAgua.append(i + virgula);

					// DS_PARAMETRO
					arquivoMevQualidadeAgua.append(aspaDupla + "TURBIDEZ" + aspaDupla + virgula);

					// AMOSTRAS_EXIGIDAS
					if(qualidadeAgua.getNumeroAmostrasExigidasTurbidez() != null){

						arquivoMevQualidadeAgua.append(qualidadeAgua.getNumeroAmostrasExigidasTurbidez().toString() + virgula);
					}else{

						arquivoMevQualidadeAgua.append(ConstantesSistema.ZERO.toString() + virgula);
					}

					// AMOSTRAS_CONFORMES
					if(qualidadeAgua.getNumeroAmostrasConformesTurbidez() != null){

						arquivoMevQualidadeAgua.append(qualidadeAgua.getNumeroAmostrasConformesTurbidez().toString() + virgula);
					}else{

						arquivoMevQualidadeAgua.append(ConstantesSistema.ZERO.toString() + virgula);
					}

					// AMOSTRAS_ANALISADA
					if(qualidadeAgua.getNumeroAmostrasRealizadasTurbidez() != null){

						arquivoMevQualidadeAgua.append(qualidadeAgua.getNumeroAmostrasRealizadasTurbidez().toString() + virgula);
					}else{

						arquivoMevQualidadeAgua.append(ConstantesSistema.ZERO.toString() + virgula);
					}

					arquivoMevQualidadeAgua.append(System.getProperty("line.separator"));
				}else if(i == 2){

					// PK_PARAMETRO
					arquivoMevQualidadeAgua.append(i + virgula);

					// DS_PARAMETRO
					arquivoMevQualidadeAgua.append(aspaDupla + "COR" + aspaDupla + virgula);

					// AMOSTRAS_EXIGIDAS
					if(qualidadeAgua.getNumeroAmostrasExigidasCor() != null){

						arquivoMevQualidadeAgua.append(qualidadeAgua.getNumeroAmostrasExigidasCor().toString() + virgula);
					}else{

						arquivoMevQualidadeAgua.append(ConstantesSistema.ZERO.toString() + virgula);
					}

					// AMOSTRAS_CONFORMES
					if(qualidadeAgua.getNumeroAmostrasConformesCor() != null){

						arquivoMevQualidadeAgua.append(qualidadeAgua.getNumeroAmostrasConformesCor().toString() + virgula);
					}else{

						arquivoMevQualidadeAgua.append(ConstantesSistema.ZERO.toString() + virgula);
					}

					// AMOSTRAS_ANALISADA
					if(qualidadeAgua.getNumeroAmostrasRealizadasCor() != null){

						arquivoMevQualidadeAgua.append(qualidadeAgua.getNumeroAmostrasRealizadasCor().toString() + virgula);
					}else{

						arquivoMevQualidadeAgua.append(ConstantesSistema.ZERO.toString() + virgula);
					}

					arquivoMevQualidadeAgua.append(System.getProperty("line.separator"));
				}else if(i == 3){

					// PK_PARAMETRO
					arquivoMevQualidadeAgua.append(i + virgula);

					// DS_PARAMETRO
					arquivoMevQualidadeAgua.append(aspaDupla + "CLORO" + aspaDupla + virgula);

					// AMOSTRAS_EXIGIDAS
					if(qualidadeAgua.getNumeroAmostrasExigidasCloro() != null){

						arquivoMevQualidadeAgua.append(qualidadeAgua.getNumeroAmostrasExigidasCloro().toString() + virgula);
					}else{

						arquivoMevQualidadeAgua.append(ConstantesSistema.ZERO.toString() + virgula);
					}

					// AMOSTRAS_CONFORMES
					if(qualidadeAgua.getNumeroAmostrasConformesCloro() != null){

						arquivoMevQualidadeAgua.append(qualidadeAgua.getNumeroAmostrasConformesCloro().toString() + virgula);
					}else{

						arquivoMevQualidadeAgua.append(ConstantesSistema.ZERO.toString() + virgula);
					}

					// AMOSTRAS_ANALISADA
					if(qualidadeAgua.getNumeroAmostrasRealizadasCloro() != null){

						arquivoMevQualidadeAgua.append(qualidadeAgua.getNumeroAmostrasRealizadasCloro().toString() + virgula);
					}else{

						arquivoMevQualidadeAgua.append(ConstantesSistema.ZERO.toString() + virgula);
					}

					arquivoMevQualidadeAgua.append(System.getProperty("line.separator"));
				}else if(i == 4){

					// PK_PARAMETRO
					arquivoMevQualidadeAgua.append(i + virgula);

					// DS_PARAMETRO
					arquivoMevQualidadeAgua.append(aspaDupla + "COLIFORMES TOTAIS" + aspaDupla + virgula);

					// AMOSTRAS_EXIGIDAS
					if(qualidadeAgua.getNumeroAmostrasExigidasColiformesTotais() != null){

						arquivoMevQualidadeAgua.append(qualidadeAgua.getNumeroAmostrasExigidasColiformesTotais().toString() + virgula);
					}else{

						arquivoMevQualidadeAgua.append(ConstantesSistema.ZERO.toString() + virgula);
					}

					// AMOSTRAS_CONFORMES
					if(qualidadeAgua.getNumeroAmostrasConformesColiformesTotais() != null){

						arquivoMevQualidadeAgua.append(qualidadeAgua.getNumeroAmostrasConformesColiformesTotais().toString() + virgula);
					}else{

						arquivoMevQualidadeAgua.append(ConstantesSistema.ZERO.toString() + virgula);
					}

					// AMOSTRAS_ANALISADA
					if(qualidadeAgua.getNumeroAmostrasRealizadasColiformesTotais() != null){

						arquivoMevQualidadeAgua.append(qualidadeAgua.getNumeroAmostrasRealizadasColiformesTotais().toString() + virgula);
					}else{

						arquivoMevQualidadeAgua.append(ConstantesSistema.ZERO.toString() + virgula);
					}

					arquivoMevQualidadeAgua.append(System.getProperty("line.separator"));
				}else{

					// PK_PARAMETRO
					arquivoMevQualidadeAgua.append(i + virgula);

					// DS_PARAMETRO
					arquivoMevQualidadeAgua.append(aspaDupla + "COLIFORMES TERMOTOLERANTES" + aspaDupla + virgula);

					// AMOSTRAS_EXIGIDAS
					if(qualidadeAgua.getNumeroAmostrasExigidasColiformesTermotolerantes() != null){

						arquivoMevQualidadeAgua.append(qualidadeAgua.getNumeroAmostrasExigidasColiformesTermotolerantes().toString()
										+ virgula);
					}else{

						arquivoMevQualidadeAgua.append(ConstantesSistema.ZERO.toString() + virgula);
					}

					// AMOSTRAS_CONFORMES
					if(qualidadeAgua.getNumeroAmostrasConformesColiformesTermotolerantes() != null){

						arquivoMevQualidadeAgua.append(qualidadeAgua.getNumeroAmostrasConformesColiformesTermotolerantes().toString()
										+ virgula);
					}else{

						arquivoMevQualidadeAgua.append(ConstantesSistema.ZERO.toString() + virgula);
					}

					// AMOSTRAS_ANALISADA
					if(qualidadeAgua.getNumeroAmostrasRealizadasColiformesTermotolerantes() != null){

						arquivoMevQualidadeAgua.append(qualidadeAgua.getNumeroAmostrasRealizadasColiformesTermotolerantes().toString());
					}else{

						arquivoMevQualidadeAgua.append(ConstantesSistema.ZERO.toString());
					}

					arquivoMevQualidadeAgua.append(System.getProperty("line.separator"));
				}

			}

		}

		LOGGER.info("...............Fim Geração MEVQUALIDADEAGUA");

		return arquivoMevQualidadeAgua;
	}

	/**
	 * [UC3012] Gerar Arquivo Texto Faturamento Imediato
	 * [SB0101] – Gerar Integração para Faturamento Imediato – Modelo 3
	 * Método responsável pela geração dos dados para integração com o faturamento imediato da
	 * CAGEPA
	 * 
	 * @author Anderson Italo
	 * @throws ControladorException
	 * @date 02/04/2013
	 */
	private StringBuffer gerarArquivoMevAnormalidade() throws ErroRepositorioException,
					ControladorException{

		LOGGER.info("...............Início Geração MEVANORMALIDADE");
		StringBuffer arquivoMevAnormalidade = new StringBuffer();
		String virgula = ",";
		String aspaDupla = "\"";

		List<LeituraAnormalidade> colecaoLeituraAnormalidade = repositorioFaturamento.pesquisarLeituraAnormalidadeFaturamentoImediato();
		LeituraAnormalidade leituraAnormalidade = null;

		for(int i = 0; i < colecaoLeituraAnormalidade.size(); i++){

			leituraAnormalidade = colecaoLeituraAnormalidade.get(i);

			// PK_OCORRENCIA_LEI
			arquivoMevAnormalidade.append(leituraAnormalidade.getId().toString() + virgula);

			// DS_OCORRENCIA
			arquivoMevAnormalidade.append(aspaDupla + leituraAnormalidade.getDescricao() + aspaDupla + virgula);

			// PK_CON_S_LEI
			arquivoMevAnormalidade.append(leituraAnormalidade.getLeituraAnormalidadeConsumoSemleitura().getId().toString() + virgula);

			// PK_CON_C_LEI
			arquivoMevAnormalidade.append(leituraAnormalidade.getLeituraAnormalidadeConsumoComleitura().getId().toString() + virgula);

			// PK_LEI_S_LEI
			arquivoMevAnormalidade.append(leituraAnormalidade.getLeituraAnormalidadeLeituraSemleitura().getId().toString() + virgula);

			// PK_LEI_C_LEI
			arquivoMevAnormalidade.append(leituraAnormalidade.getLeituraAnormalidadeLeituraComleitura().getId().toString() + virgula);

			// DT_ATUALIZACAO
			arquivoMevAnormalidade.append(aspaDupla + Util.formatarDataParaTimesTampDB2(new Date()) + aspaDupla);

			arquivoMevAnormalidade.append(System.getProperty("line.separator"));
		}

		LOGGER.info("...............Fim Geração MEVANORMALIDADE");

		return arquivoMevAnormalidade;
	}

	/**
	 * [UC3012] Gerar Arquivo Texto Faturamento Imediato
	 * [SB0101] – Gerar Integração para Faturamento Imediato – Modelo 3
	 * Método responsável pela geração dos dados para integração com o faturamento imediato da
	 * CAGEPA
	 * 
	 * @author Anderson Italo
	 * @throws ControladorException
	 * @date 02/04/2013
	 */
	private StringBuffer gerarArquivoMevTarifa(Integer anoMesReferenciaFaturamento) throws ErroRepositorioException, ControladorException{

		LOGGER.info("...............Início Geração MEVTARIFA");
		StringBuffer arquivoMevTarifa = new StringBuffer();
		String virgula = ",";
		List<Object[]> colecaoTarifas = repositorioFaturamento.pesquisarTarifasArquivoTextoFaturamentoImediato(anoMesReferenciaFaturamento);

		if(!Util.isVazioOrNulo(colecaoTarifas)){

			for(int i = 0; i < colecaoTarifas.size(); i++){

				Object[] linhaRetornada = colecaoTarifas.get(i);

				// PK_TARIFA
				arquivoMevTarifa.append(linhaRetornada[0].toString() + virgula);

				// PK_CATEGORIA
				arquivoMevTarifa.append(linhaRetornada[1].toString() + virgula);

				// INTERVALO_FIM
				if(linhaRetornada[2] != null && !linhaRetornada[2].toString().equals("")){

					arquivoMevTarifa.append(linhaRetornada[2].toString() + virgula);
				}else{

					arquivoMevTarifa.append(Util.completarStringComValorEsquerda("", "0", 5));
				}

				// VL_FAIXA_AGUA
				if(linhaRetornada[3] != null && !linhaRetornada[3].toString().equals("")){

					arquivoMevTarifa.append(new BigDecimal(linhaRetornada[3].toString()).setScale(2, BigDecimal.ROUND_DOWN).toString()
									+ virgula);
				}else{

					arquivoMevTarifa.append(BigDecimal.ZERO.setScale(2).toString() + virgula);
				}

				// VL_FAIXA_ESGOTO
				if(linhaRetornada[6] != null && !linhaRetornada[6].toString().equals("")){

					arquivoMevTarifa.append(new BigDecimal(linhaRetornada[6].toString()).setScale(2, BigDecimal.ROUND_DOWN).toString()
									+ virgula);
				}else{

					arquivoMevTarifa.append(BigDecimal.ZERO.setScale(2).toString() + virgula);
				}

				// DT_VIGENCIA
				arquivoMevTarifa.append(Util.formatarDataAAAAMMDD((Date) linhaRetornada[5]));

				arquivoMevTarifa.append(System.getProperty("line.separator"));
			}
		}else{

			throw new ControladorException("atencao.tarifas_vigentes_inexistentes");
		}

		LOGGER.info("...............Fim Geração MEVTARIFA");
		return arquivoMevTarifa;
	}

	/**
	 * [UC3012] Gerar Arquivo Texto Faturamento Imediato
	 * [SB0101] – Gerar Integração para Faturamento Imediato – Modelo 3
	 * Método responsável pela geração dos dados para integração com o faturamento imediato da
	 * CAGEPA
	 * 
	 * @author Anderson Italo
	 * @throws ControladorException
	 * @date 02/04/2013
	 */
	private StringBuffer gerarArquivoMetApoio() throws ControladorException{

		LOGGER.info("...............Início Geração METAPOIO");
		StringBuffer arquivoMetApoio = new StringBuffer();
		String virgula = ",";
		String aspaDupla = "\"";
		String caracterVazio = " ";
		SistemaParametro sistemaParametro = getControladorUtil().pesquisarParametrosDoSistema();

		// DT_GERACAO
		arquivoMetApoio.append(Util.formatarDataAAAAMMDD(new Date()) + virgula);

		ServicoTipo servicoTipo = null;
		int[] servicos = new int[] {15, 27, 27, 59, 57, 84, 85, 84};

		for(int i = 0; i < servicos.length; i++){

			servicoTipo = (ServicoTipo) getControladorUtil().pesquisar(servicos[i], ServicoTipo.class, false);

			if(servicoTipo != null){

				// COD_SERV_REL_RES, COD_SERV_REL_COM, COD_SERV_REL_IND, COD_SERV_REL_PUB
				arquivoMetApoio.append(servicoTipo.getId().toString() + virgula);

				// DESC_SER_REL_RES, DESC_SER_REL_COM, DESC_SER_REL_IND, DESC_SER_REL_PUB
				arquivoMetApoio.append(aspaDupla + servicoTipo.getDescricao() + aspaDupla + virgula);

				// VLR_SERV_REL_RES, DESC_SER_REL_COM, DESC_SER_REL_IND, DESC_SER_REL_PUB
				arquivoMetApoio.append(servicoTipo.getValor().setScale(2, BigDecimal.ROUND_DOWN).toString() + virgula);
			}else{

				arquivoMetApoio.append(ConstantesSistema.ZERO.toString() + virgula);
				arquivoMetApoio.append(aspaDupla + caracterVazio + aspaDupla + virgula);
				arquivoMetApoio.append(BigDecimal.ZERO.setScale(2).toString() + virgula);
			}
		}

		// CONS_LIM_GRD_EST
		if(sistemaParametro.getMenorConsumoGrandeUsuario() != null){

			arquivoMetApoio.append(sistemaParametro.getMenorConsumoGrandeUsuario().toString());
		}else{

			arquivoMetApoio.append(ConstantesSistema.ZERO.toString());
		}

		LOGGER.info("...............Fim Geração METAPOIO");

		return arquivoMetApoio;
	}

	/**
	 * [UC3012] Gerar Arquivo Texto Faturamento Imediato
	 * [SB0101] – Gerar Integração para Faturamento Imediato – Modelo 3
	 * Método responsável pela geração dos dados para integração com o faturamento imediato da
	 * CAGEPA
	 * 
	 * @author Anderson Italo
	 * @throws ControladorException
	 * @date 02/04/2013
	 */
	private StringBuffer gerarArquivoMensagemFaturaMe(Integer anoMesReferenciaFaturamento, Integer idFaturamentoGrupo, String idsRotas)
					throws ErroRepositorioException, ControladorException{

		LOGGER.info("...............Início Geração MENSAGEMFATURAME");
		StringBuffer arquivoMensagemFaturaMe = new StringBuffer();
		String virgula = ",";
		String aspaDupla = "\"";
		String mensagem = "";
		String caracterVazio = " ";

		List<Object[]> colecaoSetorComercial = pesquisarSetorComercialFaturamentoImediatoComLimiteRotas(idsRotas);

		Object[] setorComercial = null;

		for(int i = 0; i < colecaoSetorComercial.size(); i++){

			setorComercial = colecaoSetorComercial.get(i);

			// Pesquisa Mensagem da Conta por Setor Comercial
			Object[] contaMensagem = getControladorFaturamento().pesquisarContaMensagem(anoMesReferenciaFaturamento, idFaturamentoGrupo,
							Util.obterInteger(setorComercial[6].toString()), Util.obterInteger(setorComercial[0].toString()),
							Util.obterInteger(setorComercial[5].toString()));

			if(!Util.isVazioOrNulo(contaMensagem)){

				// PK_LOCALIDADE_MSG
				if(contaMensagem[4] != null){

					arquivoMensagemFaturaMe.append(contaMensagem[4].toString() + virgula);
				}else{

					arquivoMensagemFaturaMe.append(ConstantesSistema.ZERO.toString() + virgula);
				}

				// PK_SETOR_MSG
				if(contaMensagem[3] != null){

					arquivoMensagemFaturaMe.append(contaMensagem[3].toString() + virgula);
				}else{

					arquivoMensagemFaturaMe.append(ConstantesSistema.ZERO.toString() + virgula);
				}

				// MENSAGEM_MSG
				mensagem = contaMensagem[0].toString();

				if(contaMensagem[1] != null){

					mensagem += caracterVazio + contaMensagem[1].toString();
				}

				if(contaMensagem[2] != null){

					mensagem += caracterVazio + contaMensagem[2].toString();
				}

				arquivoMensagemFaturaMe.append(aspaDupla + mensagem + aspaDupla);
			}
		}

		LOGGER.info("...............Fim Geração MENSAGEMFATURAME");

		return arquivoMensagemFaturaMe;
	}

	/**
	 * [UC3012] Gerar Arquivo Texto Faturamento Imediato
	 * [SB0101] – Gerar Integração para Faturamento Imediato – Modelo 3
	 * Método responsável pela geração dos dados para integração com o faturamento imediato da
	 * CAGEPA
	 * 
	 * @author Anderson Italo
	 * @date 02/04/2013
	 */
	private StringBuffer gerarArquivoMevReaviso(Integer idFaturamentoGrupo, Integer anoMesReferencia) throws ErroRepositorioException{

		LOGGER.info("...............Início Geração MEVREAVISO");
		StringBuffer arquivoMevReaviso = new StringBuffer();
		Collection<Object[]> colecaoDadosContas = null;
		String virgula = ",";

		colecaoDadosContas = repositorioFaturamento.pesquisarDadosContasVinculadasDocumentoReaviso(idFaturamentoGrupo, anoMesReferencia);

		if(!Util.isVazioOrNulo(colecaoDadosContas)){
			
			for(Object[] dadosConta : colecaoDadosContas){

				// PK_REAVISO
				arquivoMevReaviso.append(dadosConta[4].toString() + virgula);

				// PK_LIGACAO_REAV
				arquivoMevReaviso.append(dadosConta[0].toString() + virgula);

				// MÊS
				arquivoMevReaviso.append(dadosConta[1].toString().substring(4) + virgula);

				// ANO
				arquivoMevReaviso.append(dadosConta[1].toString().substring(0, 4) + virgula);

				// DT_VENC
				arquivoMevReaviso.append(Util.formatarDataAAAAMMDD((Date) dadosConta[2]) + virgula);

				// VL_REAVISO
				arquivoMevReaviso.append(new BigDecimal(dadosConta[3].toString()).setScale(2).toString());

				arquivoMevReaviso.append(System.getProperty("line.separator"));
			}
		}

		LOGGER.info("...............Fim Geração MEVREAVISO");

		return arquivoMevReaviso;
	}

	/**
	 * [UC3012] Gerar Arquivo Texto Faturamento Imediato
	 * [SB0101] – Gerar Integração para Faturamento Imediato – Modelo 3
	 * Método responsável pela geração dos dados para integração com o faturamento imediato da
	 * CAGEPA
	 * 
	 * @author Anderson Italo
	 * @date 28/04/2013
	 */
	private StringBuffer gerarArquivoMetLigacao(Integer anoMesReferenciaFaturamento, Collection colecaoMovimentoRoteiroEmpresa,
					Integer idFuncionalidadeIniciada)
					throws ErroRepositorioException, ControladorException{

		LOGGER.info("...............Início Geração METLIGACAO");

		MovimentoRoteiroEmpresa movimentoRoteiroEmpresa = null;
		StringBuffer arquivoMetLigacao = new StringBuffer();
		String virgula = ",";
		String aspaDupla = "\"";
		String caracterVazio = " ";
		Cliente clienteResponsavel = null;
		Cliente clienteUsuario = null;
		Integer idBanco = null;
		LigacaoAgua ligacaoAgua = null;
		Object[] clienteFonePrincipal = null;
		Object[] dadosHidrometro = null;
		int[] faixaLeituraEsperada = null;
		int mediaConsumo = 0;
		Integer numeroLeituraAnterior = null;
		Hidrometro hidrometro = null;
		Collection<Object[]> colecaoDadosConsumoHistorico = null;
		int qtdConsumoRealDoisPrimeirosMeses = 0;
		boolean houveAnormalidadeACAFAA = false;
		boolean houveAnormalidadeBC = false;
		int indexValidarConsumoTipo = 0;
		int indexConsumoHistorico = 0;
		Cliente clientePropietario = null;
		Integer consumoMinimo = 0;
		BigDecimal percentualImposto = repositorioFaturamento.obterPercentualAliquotaImpostoFederal(anoMesReferenciaFaturamento);
		Collection<FaturamentoSituacaoHistorico> colecaoFaturamentoSituacaoHistorico = null;
		boolean paralisarEmissaoContas = false;
		Date dataProximaLeitura = null;
		boolean houveFaltaLeituraAnterior = false;
		Object[] dadosUltimaLeituraReal = null;
		String dataCorrenteTimesTampDB2 = Util.formatarDataParaTimesTampDB2(new Date());

		// Usuário
		Usuario usuario = null;

		// Recupera o usuário logado
		if(idFuncionalidadeIniciada != null){

			usuario = this.getControladorBatch().obterUsuarioFuncionalidadeIniciada(idFuncionalidadeIniciada);
		}else{

			usuario = Usuario.USUARIO_BATCH;
		}

		// Ordenar a coleção por mais de um campo (local, setor, quadra, lote e sublote)
		List sortFields = new ArrayList();
		sortFields.add(new BeanComparator("localidade.id"));
		sortFields.add(new BeanComparator("codigoSetorComercial"));
		sortFields.add(new BeanComparator("numeroQuadra"));
		sortFields.add(new BeanComparator("numeroLoteImovel"));
		sortFields.add(new BeanComparator("numeroSubLoteImovel"));

		ComparatorChain multiSort = new ComparatorChain(sortFields);
		Collections.sort((List) colecaoMovimentoRoteiroEmpresa, multiSort);

		Iterator colecaoMovimentoRoteiroEmpresaIterator = colecaoMovimentoRoteiroEmpresa.iterator();

		while(colecaoMovimentoRoteiroEmpresaIterator.hasNext()){

			movimentoRoteiroEmpresa = (MovimentoRoteiroEmpresa) colecaoMovimentoRoteiroEmpresaIterator.next();

			LOGGER.info("Gerando METLIGACAO imóvel: " + movimentoRoteiroEmpresa.getImovel().getId().toString());

			// MES_FATURA
			arquivoMetLigacao.append(movimentoRoteiroEmpresa.getAnoMesMovimento().toString().substring(4) + virgula);

			// ANO_FATURA
			arquivoMetLigacao.append(movimentoRoteiroEmpresa.getAnoMesMovimento().toString().substring(0, 4) + virgula);

			// LOCALIDADE
			arquivoMetLigacao.append(movimentoRoteiroEmpresa.getLocalidade().getId().toString() + virgula);

			// SETOR
			arquivoMetLigacao.append(movimentoRoteiroEmpresa.getCodigoSetorComercial().toString() + virgula);

			// QUADRA
			arquivoMetLigacao.append(movimentoRoteiroEmpresa.getNumeroQuadra().toString() + virgula);

			// LOTE
			arquivoMetLigacao.append(movimentoRoteiroEmpresa.getNumeroLoteImovel().toString() + virgula);

			// SEQUENCIA_MACRO
			arquivoMetLigacao.append(movimentoRoteiroEmpresa.getNumeroSubLoteImovel().toString() + virgula);

			// PK_LIGACAO
			arquivoMetLigacao.append(movimentoRoteiroEmpresa.getImovel().getId().toString() + virgula);

			// NOME_CONSUMIDOR
			if(!Util.isVazioOuBranco(movimentoRoteiroEmpresa.getNomeCliente())){

				arquivoMetLigacao.append(aspaDupla + movimentoRoteiroEmpresa.getNomeCliente() + aspaDupla + virgula);
			}else{

				arquivoMetLigacao.append(aspaDupla + caracterVazio + aspaDupla + virgula);
			}

			// PK_TP_RESPONSAVEL
			if(movimentoRoteiroEmpresa.getIndicadorImpostoFederal() == null
							|| movimentoRoteiroEmpresa.getIndicadorImpostoFederal().equals(ConstantesSistema.NAO)){

				arquivoMetLigacao.append(aspaDupla + ConstantesSistema.ZERO.toString() + aspaDupla + virgula);

			}else{
				arquivoMetLigacao.append(aspaDupla + movimentoRoteiroEmpresa.getIndicadorImpostoFederal().toString() + aspaDupla + virgula);
			}

			// EMITEFATURA
			if(movimentoRoteiroEmpresa.getIndicadorEmissao() != null
							&& movimentoRoteiroEmpresa.getIndicadorEmissao().equals(ConstantesSistema.NAO)){

				arquivoMetLigacao.append(ConstantesSistema.SIM.toString() + virgula);
			}else{

				arquivoMetLigacao.append(ConstantesSistema.ZERO.toString() + virgula);
			}

			// PK_RESPONSAVEL
			clienteResponsavel = getControladorImovel().pesquisarClienteResponsavelImovel(movimentoRoteiroEmpresa.getImovel().getId());

			if(clienteResponsavel != null){

				arquivoMetLigacao.append(clienteResponsavel.getId().toString() + virgula);
			}else{

				arquivoMetLigacao.append(ConstantesSistema.ZERO.toString() + virgula);
			}

			// PK_TARIFA_USUARIO
			arquivoMetLigacao.append(movimentoRoteiroEmpresa.getConsumoTarifa().getId().toString() + virgula);

			// PK_SIT_AGUA
			arquivoMetLigacao.append(movimentoRoteiroEmpresa.getLigacaoAguaSituacao().getId().toString() + virgula);

			// PK_SIT_ESGOTO
			arquivoMetLigacao.append(movimentoRoteiroEmpresa.getLigacaoEsgotoSituacao().getId().toString() + virgula);

			// GRANDE_CLIENTE
			arquivoMetLigacao.append(ConstantesSistema.ZERO.toString() + virgula);

			// CEP
			if(!Util.isVazioOuBranco(movimentoRoteiroEmpresa.getCepEnderecoImovel())){

				arquivoMetLigacao.append(movimentoRoteiroEmpresa.getCepEnderecoImovel().trim() + virgula);
			}else{

				arquivoMetLigacao.append(ConstantesSistema.ZERO.toString() + virgula);
			}

			// CONSUMO_FIXO_AGUA
			if(movimentoRoteiroEmpresa.getNumeroConsumoFixoAgua() != null){

				arquivoMetLigacao.append(movimentoRoteiroEmpresa.getNumeroConsumoFixoAgua().toString() + virgula);
			}else{

				arquivoMetLigacao.append(ConstantesSistema.ZERO.toString() + virgula);
			}

			// CONSUMO_FIXO_ESGOT
			if(movimentoRoteiroEmpresa.getNumeroConsumoFixoEsgoto() != null){

				arquivoMetLigacao.append(movimentoRoteiroEmpresa.getNumeroConsumoFixoEsgoto().toString() + virgula);
			}else{

				arquivoMetLigacao.append(ConstantesSistema.ZERO.toString() + virgula);
			}

			// PK_BANCO
			idBanco = repositorioFaturamento.pesquisarBancoDebitoAutomaticoImovel(movimentoRoteiroEmpresa.getImovel().getId());

			if(idBanco != null){

				arquivoMetLigacao.append(idBanco.toString() + virgula);
			}else{

				arquivoMetLigacao.append(ConstantesSistema.ZERO.toString() + virgula);
			}


			// DS_LOGRADOURO
			if(!Util.isVazioOuBranco(movimentoRoteiroEmpresa.getEnderecoImovel())){

				arquivoMetLigacao.append(aspaDupla + movimentoRoteiroEmpresa.getEnderecoImovel() + aspaDupla + virgula);
			}else{

				arquivoMetLigacao.append(aspaDupla + caracterVazio + aspaDupla + virgula);
			}

			// DS_BAIRRO
			if(!Util.isVazioOuBranco(movimentoRoteiroEmpresa.getBairroEnderecoImovel())){

				arquivoMetLigacao.append(aspaDupla + movimentoRoteiroEmpresa.getBairroEnderecoImovel() + aspaDupla + virgula);
			}else{

				arquivoMetLigacao.append(aspaDupla + caracterVazio + aspaDupla + virgula);
			}

			// NUMERO_ALTERA
			if(!Util.isVazioOuBranco(movimentoRoteiroEmpresa.getImovel().getNumeroImovel())){

				arquivoMetLigacao.append(aspaDupla + movimentoRoteiroEmpresa.getImovel().getNumeroImovel() + aspaDupla + virgula);
			}else{

				arquivoMetLigacao.append(aspaDupla + caracterVazio + aspaDupla + virgula);
			}

			// DT_VENCIMENTO_FATU
			arquivoMetLigacao.append(Util.formatarDataAAAAMMDD(movimentoRoteiroEmpresa.getDataVencimento()) + virgula);

			// N_ECONOMIAS_RES
			if(movimentoRoteiroEmpresa.getQuantidadeEconomiasResidencial() != null){

				arquivoMetLigacao.append(movimentoRoteiroEmpresa.getQuantidadeEconomiasResidencial().toString() + virgula);
			}else{

				arquivoMetLigacao.append(ConstantesSistema.ZERO.toString() + virgula);
			}

			// N_ECONOMIAS_COM
			if(movimentoRoteiroEmpresa.getQuantidadeEconomiasComercial() != null){

				arquivoMetLigacao.append(movimentoRoteiroEmpresa.getQuantidadeEconomiasComercial().toString() + virgula);
			}else{

				arquivoMetLigacao.append(ConstantesSistema.ZERO.toString() + virgula);
			}

			// N_ECONOMIAS_IND
			if(movimentoRoteiroEmpresa.getQuantidadeEconomiasIndustrial() != null){

				arquivoMetLigacao.append(movimentoRoteiroEmpresa.getQuantidadeEconomiasIndustrial().toString() + virgula);
			}else{

				arquivoMetLigacao.append(ConstantesSistema.ZERO.toString() + virgula);
			}

			// N_ECONOMIAS_PUB
			if(movimentoRoteiroEmpresa.getQuantidadeEconomiasPublica() != null){

				arquivoMetLigacao.append(movimentoRoteiroEmpresa.getQuantidadeEconomiasPublica().toString() + virgula);
			}else{

				arquivoMetLigacao.append(ConstantesSistema.ZERO.toString() + virgula);
			}

			// PK_LIGACAO_MACRO
			if(movimentoRoteiroEmpresa.getImovel().getImovelCondominio() != null){

				arquivoMetLigacao.append(movimentoRoteiroEmpresa.getImovel().getImovelCondominio().getId().toString() + virgula);
			}else{

				arquivoMetLigacao.append(ConstantesSistema.ZERO.toString() + virgula);
			}

			// DT_ULT_CORTE
			ligacaoAgua = (LigacaoAgua) getControladorUtil().pesquisar(movimentoRoteiroEmpresa.getImovel().getId(), LigacaoAgua.class,
							false);

			if(ligacaoAgua != null
							&& (movimentoRoteiroEmpresa.getLigacaoAguaSituacao().getId().equals(LigacaoAguaSituacao.SUPRIMIDO)
											|| movimentoRoteiroEmpresa.getLigacaoAguaSituacao().getId()
															.equals(LigacaoAguaSituacao.SUPR_PARC) || movimentoRoteiroEmpresa
											.getLigacaoAguaSituacao().getId().equals(LigacaoAguaSituacao.SUPRIMIDO_DEFINITIVO))){

				arquivoMetLigacao.append(Util.formatarDataAAAAMMDD(ligacaoAgua.getDataSupressao()) + virgula);
			}else if(ligacaoAgua != null && ligacaoAgua.getDataCorte() != null){

				arquivoMetLigacao.append(Util.formatarDataAAAAMMDD(ligacaoAgua.getDataCorte()) + virgula);
			}else{

				arquivoMetLigacao.append("99991231" + virgula);
			}

			// PERC_TARIFA
			arquivoMetLigacao.append(ConstantesSistema.CEM.toString() + virgula);

			// N_DIGITOS_HID
			if(movimentoRoteiroEmpresa.getNumeroDigitosLeitura() != null){

				arquivoMetLigacao.append(movimentoRoteiroEmpresa.getNumeroDigitosLeitura().toString() + virgula);
			}else{

				arquivoMetLigacao.append(ConstantesSistema.ZERO.toString() + virgula);
			}

			// FONE
			clienteUsuario = repositorioImovel.pesquisarClienteUsuarioImovel(movimentoRoteiroEmpresa.getImovel().getId());

			if(clienteUsuario != null){

				clienteFonePrincipal = repositorioCliente.pesquisarClienteFonePrincipal(clienteUsuario.getId());
			}

			if(!Util.isVazioOrNulo(clienteFonePrincipal) && !Util.isVazioOuBranco(clienteFonePrincipal[1])){

				arquivoMetLigacao.append(Util.retirarFormatacaoFone(clienteFonePrincipal[1].toString()) + virgula);
			}else{

				arquivoMetLigacao.append(ConstantesSistema.ZERO.toString() + virgula);
			}

			// Zera a variável antes de verificar a existência do HidrometroInstalacaoHistorico
			dadosHidrometro = null;

			if(ligacaoAgua != null && ligacaoAgua.getHidrometroInstalacaoHistorico() != null){

				dadosHidrometro = repositorioMicromedicao.pesquisarDadosHidrometroInstalacaoHistoricoPorId(ligacaoAgua
								.getHidrometroInstalacaoHistorico().getId());
			}else if(ligacaoAgua != null && movimentoRoteiroEmpresa.getImovel().getHidrometroInstalacaoHistorico() != null){

				dadosHidrometro = repositorioMicromedicao.pesquisarDadosHidrometroInstalacaoHistoricoPorId(movimentoRoteiroEmpresa
								.getImovel().getHidrometroInstalacaoHistorico().getId());
			}

			if(!Util.isVazioOrNulo(dadosHidrometro)){

				hidrometro = new Hidrometro();

				// PK_LACRE_HIDROMETR
				if(!Util.isVazioOuBranco(dadosHidrometro[0])){

					arquivoMetLigacao.append(ConstantesSistema.ZERO.toString() + virgula);
				}else{

					arquivoMetLigacao.append(ConstantesSistema.SIM.toString() + virgula);
				}

				// PK_HIDROMETRO
				if(!Util.isVazioOuBranco(movimentoRoteiroEmpresa.getNumeroHidrometro())){

					arquivoMetLigacao.append(aspaDupla + movimentoRoteiroEmpresa.getNumeroHidrometro().toString() + aspaDupla + virgula);
					hidrometro.setNumero(movimentoRoteiroEmpresa.getNumeroHidrometro());
				}else{

					arquivoMetLigacao.append(aspaDupla + caracterVazio + aspaDupla + virgula);
				}

				// DS_INST_HIDROMETRO
				arquivoMetLigacao.append(aspaDupla + dadosHidrometro[1].toString() + aspaDupla + virgula);

				// MARCA_HIDROMETRO
				arquivoMetLigacao.append(aspaDupla + dadosHidrometro[2].toString() + aspaDupla + virgula);

				// CAPACIDADE_HIDROME
				if(dadosHidrometro[3] != null){

					arquivoMetLigacao.append(aspaDupla + dadosHidrometro[3].toString() + aspaDupla + virgula);
				}else{

					arquivoMetLigacao.append(aspaDupla + caracterVazio + aspaDupla + virgula);
				}

				if(!Util.isVazioOuBranco(dadosHidrometro[4])){

					hidrometro.setNumeroDigitosLeitura(Util.obterShort(dadosHidrometro[4].toString()));
				}
			}else{

				arquivoMetLigacao.append(ConstantesSistema.ZERO.toString() + virgula);
				arquivoMetLigacao.append(aspaDupla + caracterVazio + aspaDupla + virgula);
				arquivoMetLigacao.append(aspaDupla + caracterVazio + aspaDupla + virgula);
				arquivoMetLigacao.append(aspaDupla + caracterVazio + aspaDupla + virgula);
				arquivoMetLigacao.append(aspaDupla + caracterVazio + aspaDupla + virgula);
				hidrometro = null;
			}

			// MEDIA_CONSUMO
			if(movimentoRoteiroEmpresa.getNumeroConsumoMedio() != null){

				arquivoMetLigacao.append(movimentoRoteiroEmpresa.getNumeroConsumoMedio().toString() + virgula);
				mediaConsumo = movimentoRoteiroEmpresa.getNumeroConsumoMedio();
			}else{

				arquivoMetLigacao.append(ConstantesSistema.ZERO.toString() + virgula);
				mediaConsumo = 0;
			}

			// VL_LEITURA_ANTERIO
			if(movimentoRoteiroEmpresa.getNumeroLeituraAnterior() != null){

				arquivoMetLigacao.append(movimentoRoteiroEmpresa.getNumeroLeituraAnterior().toString() + virgula);
				numeroLeituraAnterior = movimentoRoteiroEmpresa.getNumeroLeituraAnterior();
			}else{

				arquivoMetLigacao.append(ConstantesSistema.ZERO.toString() + virgula);
				numeroLeituraAnterior = 0;
			}

			// DT_LEITURA_ANTERIO
			if(movimentoRoteiroEmpresa.getDataLeituraAnterior() != null){

				arquivoMetLigacao.append(Util.formatarDataAAAAMMDD(movimentoRoteiroEmpresa.getDataLeituraAnterior()) + virgula);
			}else{

				arquivoMetLigacao.append("99991231" + virgula);
			}

			// IND_LEITURA_ANTERI
			if(movimentoRoteiroEmpresa.getIdLeituraSituacaoAnterior() != null){

				arquivoMetLigacao.append(movimentoRoteiroEmpresa.getIdLeituraSituacaoAnterior().toString() + virgula);
			}else{

				arquivoMetLigacao.append(ConstantesSistema.ZERO.toString() + virgula);
			}

			// DT_INST_HIDROMETRO
			if(movimentoRoteiroEmpresa.getDataInstalacaoHidrometro() != null){

				arquivoMetLigacao.append(Util.formatarDataAAAAMMDD(movimentoRoteiroEmpresa.getDataInstalacaoHidrometro()) + virgula);
			}else{

				arquivoMetLigacao.append("99991231" + virgula);
			}

			if(hidrometro != null && hidrometro.getNumeroDigitosLeitura() != null){

				// <<Inclui>> [UC0086] Calcular Faixa de Leitura Esperada
				faixaLeituraEsperada = getControladorMicromedicao().calcularFaixaLeituraEsperada(mediaConsumo, null, hidrometro,
								numeroLeituraAnterior);

				// VL_LEITURA_MIN
				arquivoMetLigacao.append(faixaLeituraEsperada[0] + virgula);

				// VL_LEITURA_MAX
				arquivoMetLigacao.append(faixaLeituraEsperada[1] + virgula);
			}else{

				arquivoMetLigacao.append(ConstantesSistema.ZERO.toString() + virgula);
				arquivoMetLigacao.append(ConstantesSistema.ZERO.toString() + virgula);
			}

			if((ligacaoAgua != null && ligacaoAgua.getHidrometroInstalacaoHistorico() != null) || hidrometro == null){

				colecaoDadosConsumoHistorico = repositorioMicromedicao.pesquisarDadosConsumoHistoricoRetroativos(movimentoRoteiroEmpresa
								.getImovel().getId(), 6, LigacaoTipo.LIGACAO_AGUA);
			}else{

				colecaoDadosConsumoHistorico = repositorioMicromedicao.pesquisarDadosConsumoHistoricoRetroativos(movimentoRoteiroEmpresa
								.getImovel().getId(), 6, LigacaoTipo.LIGACAO_ESGOTO);
			}

			qtdConsumoRealDoisPrimeirosMeses = 0;
			houveAnormalidadeACAFAA = false;
			houveAnormalidadeBC = false;
			houveFaltaLeituraAnterior = false;
			indexValidarConsumoTipo = 0;
			indexConsumoHistorico = 0;

			if(!Util.isVazioOrNulo(colecaoDadosConsumoHistorico)){

				for(Object[] dadosConsumoHistorico : colecaoDadosConsumoHistorico){

					if(indexValidarConsumoTipo < 2 && dadosConsumoHistorico[0].toString().equals(ConsumoTipo.REAL.toString())){

						qtdConsumoRealDoisPrimeirosMeses++;
					}

					if(dadosConsumoHistorico[1] != null){
						
						if(indexConsumoHistorico < 6
										&& (dadosConsumoHistorico[1].toString().equals(ConsumoAnormalidade.ALTO_CONSUMO.toString())
														|| dadosConsumoHistorico[1].toString().equals(
																		ConsumoAnormalidade.AC_FALTA_LEITURA_ANTERIOR.toString()) || dadosConsumoHistorico[1]
														.toString().equals(ConsumoAnormalidade.AC_ANORMAL_LEIT_ANTERIOR.toString()))){

							houveAnormalidadeACAFAA = true;
						}

						if(indexConsumoHistorico < 4
										&& dadosConsumoHistorico[1].toString().equals(ConsumoAnormalidade.BAIXO_CONSUMO.toString())){

							houveAnormalidadeBC = true;
						}

						if(indexConsumoHistorico < 1
										&& dadosConsumoHistorico[1].toString().equals(ConsumoAnormalidade.LEITURA_NAO_INFORMADA.toString())){

							houveFaltaLeituraAnterior = true;
						}
					}

					indexValidarConsumoTipo++;
					indexConsumoHistorico++;
				}
			}

			// IND_CONSUM_REAL_2M
			if(qtdConsumoRealDoisPrimeirosMeses == 2){

				arquivoMetLigacao.append(aspaDupla + "S" + aspaDupla + virgula);
			}else{

				arquivoMetLigacao.append(aspaDupla + caracterVazio + aspaDupla + virgula);
			}


			// IND_AC_ULT_6_MESES
			if(houveAnormalidadeACAFAA){

				arquivoMetLigacao.append(ConstantesSistema.SIM.toString() + virgula);
			}else{

				arquivoMetLigacao.append(ConstantesSistema.ZERO.toString() + virgula);
			}

			// IND_BC_ULT_4_MESES
			if(houveAnormalidadeBC){

				arquivoMetLigacao.append(ConstantesSistema.SIM.toString() + virgula);
			}else{

				arquivoMetLigacao.append(ConstantesSistema.ZERO.toString() + virgula);
			}

			clientePropietario = getControladorImovel().pesquisarClientePropietarioImovel(movimentoRoteiroEmpresa.getImovel().getId());

			// IND_CPF0_CNPJ1
			// NUMERO_CPF_CNPJ
			if(clientePropietario != null){

				// IND_CPF0_CNPJ1
				if(clientePropietario.getClienteTipo() != null){

					if(clienteUsuario.getClienteTipo().getIndicadorPessoaFisicaJuridica().equals(ClienteTipo.INDICADOR_PESSOA_JURIDICA)){
						arquivoMetLigacao.append(ConstantesSistema.SIM.toString() + virgula);

					}else{
						arquivoMetLigacao.append(ConstantesSistema.ZERO.toString() + virgula);
					}

				}else{
					arquivoMetLigacao.append(ConstantesSistema.ZERO.toString() + virgula);
				}

				// NUMERO_CPF_CNPJ
				if(clientePropietario.getCnpj() != null){

					arquivoMetLigacao.append(clientePropietario.getCnpj() + virgula);
				}else if(clientePropietario.getCpf() != null){

					arquivoMetLigacao.append(clientePropietario.getCpf() + virgula);

				}else{
					arquivoMetLigacao.append(ConstantesSistema.ZERO.toString() + virgula);
				}
			}else{

				arquivoMetLigacao.append(ConstantesSistema.ZERO.toString() + virgula);
				arquivoMetLigacao.append(ConstantesSistema.ZERO.toString() + virgula);
			}

			// PERC_IMPOSTO
			if(movimentoRoteiroEmpresa.getIndicadorImpostoFederal() != null
							&& movimentoRoteiroEmpresa.getIndicadorImpostoFederal().equals(ConstantesSistema.SIM)){

				arquivoMetLigacao.append(percentualImposto.setScale(2, BigDecimal.ROUND_UP).toString() + virgula);
			}else{

				arquivoMetLigacao.append(BigDecimal.ZERO.setScale(2).toString() + virgula);
			}

			// MES_LEITURA_1
			if(movimentoRoteiroEmpresa.getReferenciaConsumo6() != null){

				arquivoMetLigacao.append(Util.obterMes(movimentoRoteiroEmpresa.getReferenciaConsumo6()) + virgula);
			}else{

				arquivoMetLigacao.append(ConstantesSistema.ZERO.toString() + virgula);
			}

			// OCOR_LEI_LEITURA_1
			if(movimentoRoteiroEmpresa.getIdAnormalidadeLeitura6() != null){

				arquivoMetLigacao.append(movimentoRoteiroEmpresa.getIdAnormalidadeLeitura6().toString() + virgula);
			}else{

				arquivoMetLigacao.append(ConstantesSistema.ZERO.toString() + virgula);
			}

			// CONSUMO_LEITURA_1
			if(movimentoRoteiroEmpresa.getNumeroConsumo6() != null){

				arquivoMetLigacao.append(movimentoRoteiroEmpresa.getNumeroConsumo6().toString() + virgula);
			}else{

				arquivoMetLigacao.append(ConstantesSistema.ZERO.toString() + virgula);
			}

			// MES_LEITURA_2
			if(movimentoRoteiroEmpresa.getReferenciaConsumo5() != null){

				arquivoMetLigacao.append(Util.obterMes(movimentoRoteiroEmpresa.getReferenciaConsumo5()) + virgula);
			}else{

				arquivoMetLigacao.append(ConstantesSistema.ZERO.toString() + virgula);
			}

			// OCOR_LEI_LEITURA_2
			if(movimentoRoteiroEmpresa.getIdAnormalidadeLeitura5() != null){

				arquivoMetLigacao.append(movimentoRoteiroEmpresa.getIdAnormalidadeLeitura5().toString() + virgula);
			}else{

				arquivoMetLigacao.append(ConstantesSistema.ZERO.toString() + virgula);
			}

			// CONSUMO_LEITURA_2
			if(movimentoRoteiroEmpresa.getNumeroConsumo5() != null){

				arquivoMetLigacao.append(movimentoRoteiroEmpresa.getNumeroConsumo5().toString() + virgula);
			}else{

				arquivoMetLigacao.append(ConstantesSistema.ZERO.toString() + virgula);
			}

			// MES_LEITURA_3
			if(movimentoRoteiroEmpresa.getReferenciaConsumo4() != null){

				arquivoMetLigacao.append(Util.obterMes(movimentoRoteiroEmpresa.getReferenciaConsumo4()) + virgula);
			}else{

				arquivoMetLigacao.append(ConstantesSistema.ZERO.toString() + virgula);
			}

			// OCOR_LEI_LEITURA_3
			if(movimentoRoteiroEmpresa.getIdAnormalidadeLeitura4() != null){

				arquivoMetLigacao.append(movimentoRoteiroEmpresa.getIdAnormalidadeLeitura4().toString() + virgula);
			}else{

				arquivoMetLigacao.append(ConstantesSistema.ZERO.toString() + virgula);
			}

			// CONSUMO_LEITURA_3
			if(movimentoRoteiroEmpresa.getNumeroConsumo4() != null){

				arquivoMetLigacao.append(movimentoRoteiroEmpresa.getNumeroConsumo4().toString() + virgula);
			}else{

				arquivoMetLigacao.append(ConstantesSistema.ZERO.toString() + virgula);
			}

			// MES_LEITURA_4
			if(movimentoRoteiroEmpresa.getReferenciaConsumo3() != null){

				arquivoMetLigacao.append(Util.obterMes(movimentoRoteiroEmpresa.getReferenciaConsumo3()) + virgula);
			}else{

				arquivoMetLigacao.append(ConstantesSistema.ZERO.toString() + virgula);
			}

			// OCOR_LEI_LEITURA_4
			if(movimentoRoteiroEmpresa.getIdAnormalidadeLeitura3() != null){

				arquivoMetLigacao.append(movimentoRoteiroEmpresa.getIdAnormalidadeLeitura3().toString() + virgula);
			}else{

				arquivoMetLigacao.append(ConstantesSistema.ZERO.toString() + virgula);
			}

			// CONSUMO_LEITURA_4
			if(movimentoRoteiroEmpresa.getNumeroConsumo3() != null){

				arquivoMetLigacao.append(movimentoRoteiroEmpresa.getNumeroConsumo3().toString() + virgula);
			}else{

				arquivoMetLigacao.append(ConstantesSistema.ZERO.toString() + virgula);
			}

			// MES_LEITURA_5
			if(movimentoRoteiroEmpresa.getReferenciaConsumo2() != null){

				arquivoMetLigacao.append(Util.obterMes(movimentoRoteiroEmpresa.getReferenciaConsumo2()) + virgula);
			}else{

				arquivoMetLigacao.append(ConstantesSistema.ZERO.toString() + virgula);
			}

			// OCOR_LEI_LEITURA_5
			if(movimentoRoteiroEmpresa.getIdAnormalidadeLeitura2() != null){

				arquivoMetLigacao.append(movimentoRoteiroEmpresa.getIdAnormalidadeLeitura2().toString() + virgula);
			}else{

				arquivoMetLigacao.append(ConstantesSistema.ZERO.toString() + virgula);
			}

			// CONSUMO_LEITURA_5
			if(movimentoRoteiroEmpresa.getNumeroConsumo2() != null){

				arquivoMetLigacao.append(movimentoRoteiroEmpresa.getNumeroConsumo2().toString() + virgula);
			}else{

				arquivoMetLigacao.append(ConstantesSistema.ZERO.toString() + virgula);
			}

			// MES_LEITURA_6
			if(movimentoRoteiroEmpresa.getReferenciaConsumo1() != null){

				arquivoMetLigacao.append(Util.obterMes(movimentoRoteiroEmpresa.getReferenciaConsumo1()) + virgula);
			}else{

				arquivoMetLigacao.append(ConstantesSistema.ZERO.toString() + virgula);
			}

			// OCOR_LEI_LEITURA_6
			if(movimentoRoteiroEmpresa.getIdAnormalidadeLeitura1() != null){

				arquivoMetLigacao.append(movimentoRoteiroEmpresa.getIdAnormalidadeLeitura1().toString() + virgula);
			}else{

				arquivoMetLigacao.append(ConstantesSistema.ZERO.toString() + virgula);
			}

			// CONSUMO_LEITURA_6
			if(movimentoRoteiroEmpresa.getNumeroConsumo1() != null){

				arquivoMetLigacao.append(movimentoRoteiroEmpresa.getNumeroConsumo1().toString() + virgula);
			}else{

				arquivoMetLigacao.append(ConstantesSistema.ZERO.toString() + virgula);
			}

			// VL_CREDITO
			if(movimentoRoteiroEmpresa.getValorCreditos() != null){

				arquivoMetLigacao.append(movimentoRoteiroEmpresa.getValorCreditos().setScale(2).toString() + virgula);
			}else{

				arquivoMetLigacao.append(BigDecimal.ZERO.setScale(2).toString() + virgula);
			}

			// DS_RUBRICA_01
			if(movimentoRoteiroEmpresa.getDescricaoRubrica1() != null){

				arquivoMetLigacao.append(aspaDupla + movimentoRoteiroEmpresa.getDescricaoRubrica1() + aspaDupla + virgula);
			}else{

				arquivoMetLigacao.append(aspaDupla + caracterVazio + aspaDupla + virgula);
			}

			// VL_RUBRICA_01
			if(movimentoRoteiroEmpresa.getValorRubrica1() != null){

				arquivoMetLigacao.append(movimentoRoteiroEmpresa.getValorRubrica1().setScale(2).toString() + virgula);
			}else{

				arquivoMetLigacao.append(BigDecimal.ZERO.setScale(2).toString() + virgula);
			}

			// DS_RUBRICA_02
			if(movimentoRoteiroEmpresa.getDescricaoRubrica2() != null){

				arquivoMetLigacao.append(aspaDupla + movimentoRoteiroEmpresa.getDescricaoRubrica2() + aspaDupla + virgula);
			}else{

				arquivoMetLigacao.append(aspaDupla + caracterVazio + aspaDupla + virgula);
			}

			// VL_RUBRICA_02
			if(movimentoRoteiroEmpresa.getValorRubrica2() != null){

				arquivoMetLigacao.append(movimentoRoteiroEmpresa.getValorRubrica2().setScale(2).toString() + virgula);
			}else{

				arquivoMetLigacao.append(BigDecimal.ZERO.setScale(2).toString() + virgula);
			}

			// DS_RUBRICA_03
			if(movimentoRoteiroEmpresa.getDescricaoRubrica3() != null){

				arquivoMetLigacao.append(aspaDupla + movimentoRoteiroEmpresa.getDescricaoRubrica3() + aspaDupla + virgula);
			}else{

				arquivoMetLigacao.append(aspaDupla + caracterVazio + aspaDupla + virgula);
			}

			// VL_RUBRICA_03
			if(movimentoRoteiroEmpresa.getValorRubrica3() != null){

				arquivoMetLigacao.append(movimentoRoteiroEmpresa.getValorRubrica3().setScale(2).toString() + virgula);
			}else{

				arquivoMetLigacao.append(BigDecimal.ZERO.setScale(2).toString() + virgula);
			}

			// DS_RUBRICA_04
			if(movimentoRoteiroEmpresa.getDescricaoRubrica4() != null){

				arquivoMetLigacao.append(aspaDupla + movimentoRoteiroEmpresa.getDescricaoRubrica4() + aspaDupla + virgula);
			}else{

				arquivoMetLigacao.append(aspaDupla + caracterVazio + aspaDupla + virgula);
			}

			// VL_RUBRICA_04
			if(movimentoRoteiroEmpresa.getValorRubrica4() != null){

				arquivoMetLigacao.append(movimentoRoteiroEmpresa.getValorRubrica4().setScale(2).toString() + virgula);
			}else{

				arquivoMetLigacao.append(BigDecimal.ZERO.setScale(2).toString() + virgula);
			}

			// DS_RUBRICA_05
			if(movimentoRoteiroEmpresa.getDescricaoRubrica5() != null){

				arquivoMetLigacao.append(aspaDupla + movimentoRoteiroEmpresa.getDescricaoRubrica5() + aspaDupla + virgula);
			}else{

				arquivoMetLigacao.append(aspaDupla + caracterVazio + aspaDupla + virgula);
			}

			// VL_RUBRICA_05
			if(movimentoRoteiroEmpresa.getValorRubrica5() != null){

				arquivoMetLigacao.append(movimentoRoteiroEmpresa.getValorRubrica5().setScale(2).toString() + virgula);
			}else{

				arquivoMetLigacao.append(BigDecimal.ZERO.setScale(2).toString() + virgula);
			}

			// DS_RUBRICA_06
			if(movimentoRoteiroEmpresa.getDescricaoRubrica6() != null){

				arquivoMetLigacao.append(aspaDupla + movimentoRoteiroEmpresa.getDescricaoRubrica6() + aspaDupla + virgula);
			}else{

				arquivoMetLigacao.append(aspaDupla + caracterVazio + aspaDupla + virgula);
			}

			// VL_RUBRICA_06
			if(movimentoRoteiroEmpresa.getValorRubrica6() != null){

				arquivoMetLigacao.append(movimentoRoteiroEmpresa.getValorRubrica6().setScale(2).toString() + virgula);
			}else{

				arquivoMetLigacao.append(BigDecimal.ZERO.setScale(2).toString() + virgula);
			}

			// DS_RUBRICA_07
			if(movimentoRoteiroEmpresa.getDescricaoRubrica7() != null){

				arquivoMetLigacao.append(aspaDupla + movimentoRoteiroEmpresa.getDescricaoRubrica7() + aspaDupla + virgula);
			}else{

				arquivoMetLigacao.append(aspaDupla + caracterVazio + aspaDupla + virgula);
			}

			// VL_RUBRICA_07
			if(movimentoRoteiroEmpresa.getValorRubrica7() != null){

				arquivoMetLigacao.append(movimentoRoteiroEmpresa.getValorRubrica7().setScale(2).toString() + virgula);
			}else{

				arquivoMetLigacao.append(BigDecimal.ZERO.setScale(2).toString() + virgula);
			}

			// DS_RUBRICA_08
			if(movimentoRoteiroEmpresa.getDescricaoRubrica8() != null){

				arquivoMetLigacao.append(aspaDupla + movimentoRoteiroEmpresa.getDescricaoRubrica8() + aspaDupla + virgula);
			}else{

				arquivoMetLigacao.append(aspaDupla + caracterVazio + aspaDupla + virgula);
			}

			// VL_RUBRICA_08
			if(movimentoRoteiroEmpresa.getValorRubrica8() != null){

				arquivoMetLigacao.append(movimentoRoteiroEmpresa.getValorRubrica8().setScale(2).toString() + virgula);
			}else{

				arquivoMetLigacao.append(BigDecimal.ZERO.setScale(2).toString() + virgula);
			}

			// DS_RUBRICA_09
			if(movimentoRoteiroEmpresa.getDescricaoRubrica9() != null){

				arquivoMetLigacao.append(aspaDupla + movimentoRoteiroEmpresa.getDescricaoRubrica9() + aspaDupla + virgula);
			}else{

				arquivoMetLigacao.append(aspaDupla + caracterVazio + aspaDupla + virgula);
			}

			// VL_RUBRICA_09
			if(movimentoRoteiroEmpresa.getValorRubrica9() != null){

				arquivoMetLigacao.append(movimentoRoteiroEmpresa.getValorRubrica9().setScale(2).toString() + virgula);
			}else{

				arquivoMetLigacao.append(BigDecimal.ZERO.setScale(2).toString() + virgula);
			}

			// DS_RUBRICA_10
			if(movimentoRoteiroEmpresa.getDescricaoRubrica10() != null){

				arquivoMetLigacao.append(aspaDupla + movimentoRoteiroEmpresa.getDescricaoRubrica10() + aspaDupla + virgula);
			}else{

				arquivoMetLigacao.append(aspaDupla + caracterVazio + aspaDupla + virgula);
			}

			// VL_RUBRICA_10
			if(movimentoRoteiroEmpresa.getValorRubrica10() != null){

				arquivoMetLigacao.append(movimentoRoteiroEmpresa.getValorRubrica10().setScale(2).toString() + virgula);
			}else{

				arquivoMetLigacao.append(BigDecimal.ZERO.setScale(2).toString() + virgula);
			}

			// VL_LEITURA_ATUAL
			arquivoMetLigacao.append(ConstantesSistema.ZERO.toString() + virgula);

			// VL_LEITURA_ATRIBUI
			arquivoMetLigacao.append(ConstantesSistema.ZERO.toString() + virgula);

			// CONSUMO
			arquivoMetLigacao.append(ConstantesSistema.ZERO.toString() + virgula);

			// CONSUMO_FATURADO
			arquivoMetLigacao.append(ConstantesSistema.ZERO.toString() + virgula);

			// CONSUMO_MINIMO
			consumoMinimo = 0;
			if(movimentoRoteiroEmpresa.getQuantidadeEconomiasResidencial() != null){

				consumoMinimo = consumoMinimo.intValue() + (movimentoRoteiroEmpresa.getQuantidadeEconomiasResidencial().shortValue() * 10);
			}

			if(movimentoRoteiroEmpresa.getQuantidadeEconomiasComercial() != null){

				consumoMinimo = consumoMinimo.intValue() + (movimentoRoteiroEmpresa.getQuantidadeEconomiasComercial().shortValue() * 10);
			}

			if(movimentoRoteiroEmpresa.getQuantidadeEconomiasIndustrial() != null){

				consumoMinimo = consumoMinimo.intValue() + (movimentoRoteiroEmpresa.getQuantidadeEconomiasIndustrial().shortValue() * 10);
			}

			if(movimentoRoteiroEmpresa.getQuantidadeEconomiasPublica() != null){

				consumoMinimo = consumoMinimo.intValue() + (movimentoRoteiroEmpresa.getQuantidadeEconomiasPublica().shortValue() * 10);
			}

			arquivoMetLigacao.append(consumoMinimo.toString() + virgula);

			// CONSUMO_RATEIO
			arquivoMetLigacao.append(ConstantesSistema.ZERO.toString() + virgula);

			// PK_OCORRENCIA
			arquivoMetLigacao.append(ConstantesSistema.ZERO.toString() + virgula);

			// PK_OCORRENCIA_CONS
			arquivoMetLigacao.append(aspaDupla + caracterVazio + aspaDupla + virgula);

			// FATURA_EMITIDA
			arquivoMetLigacao.append(ConstantesSistema.ZERO.toString() + virgula);

			// LOCAL_FATURA_ENTRE
			arquivoMetLigacao.append(ConstantesSistema.ZERO.toString() + virgula);

			// DIAS_FATURADO
			arquivoMetLigacao.append(ConstantesSistema.ZERO.toString() + virgula);

			// DT_LEITURA_ATUAL
			arquivoMetLigacao.append(aspaDupla + dataCorrenteTimesTampDB2 + aspaDupla + virgula);

			// VL_AGUA
			arquivoMetLigacao.append(BigDecimal.ZERO.setScale(2).toString() + virgula);

			// VL_ESGOTO
			arquivoMetLigacao.append(BigDecimal.ZERO.setScale(2).toString() + virgula);

			// VL_SERVICO
			if(movimentoRoteiroEmpresa.getValorDebitos() != null){

				arquivoMetLigacao.append(movimentoRoteiroEmpresa.getValorDebitos().setScale(2).toString() + virgula);
			}else{

				arquivoMetLigacao.append(BigDecimal.ZERO.setScale(2).toString() + virgula);
			}

			// VL_DEVOLVIDO
			arquivoMetLigacao.append(BigDecimal.ZERO.setScale(2).toString() + virgula);

			// VL_SALDO_DEVOLVIDO
			arquivoMetLigacao.append(BigDecimal.ZERO.setScale(2).toString() + virgula);

			// IND_GRANDE_CONS_FA
			arquivoMetLigacao.append(ConstantesSistema.ZERO.toString() + virgula);

			// IND_CONTATAR_USU
			arquivoMetLigacao.append(aspaDupla + caracterVazio + aspaDupla + virgula);

			// IND_RELIGAR_AGUA
			arquivoMetLigacao.append(ConstantesSistema.ZERO.toString() + virgula);

			// PK_SERV_RELIGACAO
			arquivoMetLigacao.append(ConstantesSistema.ZERO.toString() + virgula);

			// VL_RELIGACAO
			arquivoMetLigacao.append(BigDecimal.ZERO.setScale(2).toString() + virgula);

			// PK_SERV_SANCAO
			arquivoMetLigacao.append(ConstantesSistema.ZERO.toString() + virgula);

			// VL_SANCAO
			arquivoMetLigacao.append(BigDecimal.ZERO.setScale(2).toString() + virgula);

			// TP_CONSUMO
			arquivoMetLigacao.append(aspaDupla + caracterVazio + aspaDupla + virgula);

			// CONDICAO_LEITURA
			arquivoMetLigacao.append(ConstantesSistema.ZERO.toString() + virgula);

			// ALTE_NUM_IMOVEL
			arquivoMetLigacao.append(aspaDupla + caracterVazio + aspaDupla + virgula);

			// ID_DV_CORT_SHD_LIG
			arquivoMetLigacao.append(aspaDupla + caracterVazio + aspaDupla + virgula);

			// ID_DV_NUM_HD
			arquivoMetLigacao.append(aspaDupla + caracterVazio + aspaDupla + virgula);

			// ID_DV_CAT_ECO
			arquivoMetLigacao.append(aspaDupla + caracterVazio + aspaDupla + virgula);

			// ID_DV_LOGRAD
			arquivoMetLigacao.append(aspaDupla + caracterVazio + aspaDupla + virgula);

			// ID_DV_REVISAO_QUA
			arquivoMetLigacao.append(aspaDupla + caracterVazio + aspaDupla + virgula);

			// ID_DV_FISC_CONSUMO
			arquivoMetLigacao.append(aspaDupla + caracterVazio + aspaDupla + virgula);

			// ID_DV_USU_N_MD_CHD
			arquivoMetLigacao.append(aspaDupla + caracterVazio + aspaDupla + virgula);

			// IND_HD_NAO_LACRADO
			arquivoMetLigacao.append(ConstantesSistema.ZERO.toString() + virgula);

			// N_FONE_CONTACTO
			arquivoMetLigacao.append(ConstantesSistema.ZERO.toString() + virgula);

			// PK_FUNCIONARIO
			arquivoMetLigacao.append(ConstantesSistema.ZERO.toString() + virgula);

			// CIDADE
			if(movimentoRoteiroEmpresa.getLocalidade() != null){

				arquivoMetLigacao.append(aspaDupla + movimentoRoteiroEmpresa.getLocalidade().getDescricao() + aspaDupla + virgula);
			}else{

				arquivoMetLigacao.append(aspaDupla + caracterVazio + aspaDupla + virgula);
			}

			// PK_CICLO
			arquivoMetLigacao.append(movimentoRoteiroEmpresa.getFaturamentoGrupo().getId() + virgula);
			
			// TIPO_COLETA_LEI
			paralisarEmissaoContas = false;
			colecaoFaturamentoSituacaoHistorico = repositorioFaturamento
							.pesquisarFaturamentoSituacaoHistoricoAtivoImovel(movimentoRoteiroEmpresa.getImovel().getId());

			if(!Util.isVazioOrNulo(colecaoFaturamentoSituacaoHistorico)){

				for(FaturamentoSituacaoHistorico faturamentoSituacaoHistorico : colecaoFaturamentoSituacaoHistorico){

					if(faturamentoSituacaoHistorico.getFaturamentoSituacaoTipo().getId()
									.equals(FaturamentoSituacaoTipo.PARALISAR_EMISSAO_CONTAS)){

						paralisarEmissaoContas = true;
						break;
					}
				}
			}

			if(ligacaoAgua != null
							&& ((ligacaoAgua.getHidrometroInstalacaoHistorico() == null && !movimentoRoteiroEmpresa
											.getLigacaoAguaSituacao().equals(LigacaoAguaSituacao.LIGADO)) || paralisarEmissaoContas)){

				arquivoMetLigacao.append(ConstantesSistema.NAO.toString() + virgula);
			}else{

				arquivoMetLigacao.append(ConstantesSistema.ZERO.toString() + virgula);
			}

			// DEBITO_AUTOMATICO
			arquivoMetLigacao.append(ConstantesSistema.ZERO.toString() + virgula);

			// DT_PROX_LEITURA
			dataProximaLeitura = repositorioFaturamento.pesquisarFaturamentoAtividadeCronogramaDataPrevista(movimentoRoteiroEmpresa
							.getFaturamentoGrupo().getId(), FaturamentoAtividade.GERAR_ARQUIVO_LEITURA, Util.somaMesAnoMesReferencia(
							anoMesReferenciaFaturamento, 1));

			if(dataProximaLeitura != null){

				arquivoMetLigacao.append(Util.formatarDataAAAAMMDD(dataProximaLeitura) + virgula);
			}else{

				arquivoMetLigacao.append("99991231" + virgula);
			}

			// DT_GERACAO_GCS
			arquivoMetLigacao.append(Util.formatarDataAAAAMMDD(new Date()) + virgula);

			// LIVRO_DO_SETOR
			arquivoMetLigacao.append(movimentoRoteiroEmpresa.getRota().getId() + virgula);

			// PK_REAVISO_LIG
			if(movimentoRoteiroEmpresa.getNumeroDocumentoCobranca() != null){

				arquivoMetLigacao.append(movimentoRoteiroEmpresa.getNumeroDocumentoCobranca().toString() + virgula);
			}else{

				arquivoMetLigacao.append(ConstantesSistema.ZERO.toString() + virgula);
			}

			// QTD_CONTAS_REVISAO
			arquivoMetLigacao.append(ConstantesSistema.ZERO.toString() + virgula);

			// VL_TOTAL_REVISAO
			arquivoMetLigacao.append(BigDecimal.ZERO.setScale(2).toString() + virgula);

			// ID_REGIONAL
			arquivoMetLigacao.append(movimentoRoteiroEmpresa.getLocalidade().getGerenciaRegional().getId().toString() + virgula);

			// VL_IMPOSTO_RETORNO
			arquivoMetLigacao.append(BigDecimal.ZERO.setScale(2).toString() + virgula);

			// DT_EMISSAO
			arquivoMetLigacao.append(aspaDupla + dataCorrenteTimesTampDB2 + aspaDupla + virgula);

			// ID_STATUS
			arquivoMetLigacao.append(ConstantesSistema.ZERO.toString() + virgula);

			// TIMEDOWNLOAD
			arquivoMetLigacao.append(aspaDupla + dataCorrenteTimesTampDB2 + aspaDupla + virgula);

			// TOTAL_EMITIDO
			arquivoMetLigacao.append(BigDecimal.ZERO.setScale(2).toString() + virgula);

			// DATA_TAREFA
			arquivoMetLigacao.append(aspaDupla + dataCorrenteTimesTampDB2 + aspaDupla + virgula);

			// GERADO_GERAOK
			arquivoMetLigacao.append(ConstantesSistema.ZERO.toString() + virgula);

			// LOGIN_GERAOK
			arquivoMetLigacao.append(ConstantesSistema.ZERO.toString() + virgula);

			// DATA_GERAOK
			arquivoMetLigacao.append(aspaDupla + dataCorrenteTimesTampDB2 + aspaDupla + virgula);

			// LOGIN_DISTRIBUIDO
			arquivoMetLigacao.append(ConstantesSistema.ZERO.toString() + virgula);

			// DATA_DISTRIBUIDO
			arquivoMetLigacao.append(aspaDupla + dataCorrenteTimesTampDB2 + aspaDupla + virgula);

			// LOGIN_REMANEJADO
			arquivoMetLigacao.append(ConstantesSistema.ZERO.toString() + virgula);

			// DATA_REMANEJADO
			arquivoMetLigacao.append(aspaDupla + dataCorrenteTimesTampDB2 + aspaDupla + virgula);

			// LOGIN_ENVIADO_COL
			arquivoMetLigacao.append(ConstantesSistema.ZERO.toString() + virgula);

			// DATA_ENVIADO_COL
			arquivoMetLigacao.append(aspaDupla + dataCorrenteTimesTampDB2 + aspaDupla + virgula);

			// ENVIADO_COL
			arquivoMetLigacao.append(ConstantesSistema.ZERO.toString() + virgula);

			// IND_FATURA_RETIDA
			arquivoMetLigacao.append(virgula);

			// VERSAO
			arquivoMetLigacao.append(aspaDupla + caracterVazio + aspaDupla + virgula);

			// REIMPRESSA
			arquivoMetLigacao.append(ConstantesSistema.ZERO.toString() + virgula);

			// REIMPRESSA_NOTIF
			arquivoMetLigacao.append(ConstantesSistema.ZERO.toString() + virgula);

			// DT_LIBERACAO
			arquivoMetLigacao.append(aspaDupla + dataCorrenteTimesTampDB2 + aspaDupla + virgula);

			// IND_EXISTE_DEBITO
			if(repositorioFaturamento.verificarDebitosPendentesImovelTipoConta(movimentoRoteiroEmpresa.getImovel().getId()) == null){

				arquivoMetLigacao.append(ConstantesSistema.ZERO.toString() + virgula);
			}else{

				arquivoMetLigacao.append(ConstantesSistema.SIM.toString() + virgula);
			}

			// DESCARREGAMENTO
			arquivoMetLigacao.append(ConstantesSistema.ZERO.toString() + virgula);

			// TEVE_FALTA_LEI_ANT
			if(houveFaltaLeituraAnterior){

				arquivoMetLigacao.append(aspaDupla + "S" + aspaDupla + virgula);
			}else{

				arquivoMetLigacao.append(aspaDupla + "N" + aspaDupla + virgula);
			}

			dadosUltimaLeituraReal = repositorioMicromedicao.pesquisarDadosUltimaLeituraReal(movimentoRoteiroEmpresa.getImovel()
							.getId());

			if(!Util.isVazioOrNulo(dadosUltimaLeituraReal)){
				
				// VL_ULT_LEIT_REAL
				arquivoMetLigacao.append(dadosUltimaLeituraReal[0].toString() + virgula);
				
				// NRO_MES_ULT_LEI_RL
				Integer numeroMesesUltimaLeituraReal = Util.subtrairReferenciasAnoMes(anoMesReferenciaFaturamento.intValue(), Util
								.obterInteger(dadosUltimaLeituraReal[1].toString()).intValue());

				if(numeroMesesUltimaLeituraReal != null){
					arquivoMetLigacao.append(String.valueOf(numeroMesesUltimaLeituraReal));
				}else{
					arquivoMetLigacao.append(ConstantesSistema.ZERO.toString());
				}
			}else{

				arquivoMetLigacao.append(ConstantesSistema.ZERO.toString() + virgula);
				arquivoMetLigacao.append(ConstantesSistema.ZERO.toString());
			}

			arquivoMetLigacao.append(System.getProperty("line.separator"));

			/*
			 * O sistema indica a geração do arquivo texto para o movimento de leitura atualizando a
			 * tabela MOVIMENTO_ROTEIRO_EMPRESA com os seguintes valores
			 */
			movimentoRoteiroEmpresa.setIndicadorGeracaoArquivoTexto(ConstantesSistema.SIM);
			movimentoRoteiroEmpresa.setIdUsuarioGeracaoArquivoTexto(usuario.getId());
			movimentoRoteiroEmpresa.setTempoGeracaoArquivoTexto(new Date());
		}

		LOGGER.info("...............Fim Geração METLIGACAO");

		return arquivoMetLigacao;
	}

	/**
	 * [UC3012] Gerar Arquivo Texto Faturamento Imediato
	 * [SB0001] – Gerar Arquivo Texto para Faturamento Imediato – Modelo 3
	 * 
	 * @author Anderson Italo
	 * @date 03/04/2013
	 */
	private void enviarEmailArquivoFaturamentoImediato(StringBuffer conteudoArquivoMetLigacao, StringBuffer conteudoArquivoMevReaviso,
					StringBuffer conteudoArquivoMensagemFaturaMe, StringBuffer conteudoArquivoMetApoio,
					StringBuffer conteudoArquivoMevTarifa, StringBuffer conteudoArquivoMevAnormalidade,
					StringBuffer conteudoArquivoMevQualidadeAgua, String nomeArquivoMetLigacao, String nomeArquivoMevReaviso,
					String nomeArquivoMensagemFaturaMe, String nomeArquivoMetApoio, String nomeArquivoMevTarifa,
					String nomeArquivoMevAnormalidade, String nomeArquivoMevQualidadeAgua, EnvioEmail envioEmail,
					Integer idFuncionalidadeIniciada, String nomeArquivoZipado) throws IOException, Exception{

		// Usuário
		Usuario usuario = null;

		// Recupera o usuário logado
		if(idFuncionalidadeIniciada != null){

			usuario = this.getControladorBatch().obterUsuarioFuncionalidadeIniciada(idFuncionalidadeIniciada);
		}else{

			// Insere o usario Batch
			usuario = Usuario.USUARIO_BATCH;
		}

		RelatorioDadosTabelasFaturamentoImediato relatorio = new RelatorioDadosTabelasFaturamentoImediato(usuario);

		relatorio.addParametro("conteudoArquivoMetLigacao", conteudoArquivoMetLigacao);
		relatorio.addParametro("conteudoArquivoMevReaviso", conteudoArquivoMevReaviso);
		relatorio.addParametro("conteudoArquivoMensagemFaturaMe", conteudoArquivoMensagemFaturaMe);
		relatorio.addParametro("conteudoArquivoMetApoio", conteudoArquivoMetApoio);
		relatorio.addParametro("conteudoArquivoMevTarifa", conteudoArquivoMevTarifa);
		relatorio.addParametro("conteudoArquivoMevAnormalidade", conteudoArquivoMevAnormalidade);
		relatorio.addParametro("conteudoArquivoMevQualidadeAgua", conteudoArquivoMevQualidadeAgua);
		relatorio.addParametro("nomeArquivoMetLigacao", nomeArquivoMetLigacao);
		relatorio.addParametro("nomeArquivoMevReaviso", nomeArquivoMevReaviso);
		relatorio.addParametro("nomeArquivoMensagemFaturaMe", nomeArquivoMensagemFaturaMe);
		relatorio.addParametro("nomeArquivoMetApoio", nomeArquivoMetApoio);
		relatorio.addParametro("nomeArquivoMevTarifa", nomeArquivoMevTarifa);
		relatorio.addParametro("nomeArquivoMevAnormalidade", nomeArquivoMevAnormalidade);
		relatorio.addParametro("nomeArquivoMevQualidadeAgua", nomeArquivoMevQualidadeAgua);
		relatorio.addParametro("nomeArquivoZipado", nomeArquivoZipado);

		relatorio.addParametro("tipoFormatoRelatorio", TarefaRelatorio.TIPO_ZIP);
		relatorio.addParametro("envioEmail", envioEmail);

		this.getControladorBatch().iniciarProcessoRelatorio(relatorio);
	}

	/**
	 * Método execParamGerarArquivoTextoFaturamentoImediatoModelo4
	 * <p>
	 * Esse método implementa a geração de arquivo para faturamento imediato para a CAERD
	 * </p>
	 * RASTREIO: [OC1026959][UC3012][SB0102]
	 * 
	 * @param parametroSistema
	 * @param anoMesReferenciaFaturamento
	 * @param idFaturamentoGrupo
	 * @param idFuncionalidadeIniciada
	 * @param idEmpresa
	 * @param idsRotas
	 * @param colecaoMovimentoRoteiroEmpresa
	 * @throws Exception
	 * @author Marlos Ribeiro
	 * @since 11/04/2013
	 */
	public void execParamGerarArquivoTextoFaturamentoImediatoModelo4(ParametroSistema parametroSistema,
					Integer anoMesReferenciaFaturamento, Integer idFaturamentoGrupo, Integer idFuncionalidadeIniciada, Integer idEmpresa,
					String idsRotas, Collection colecaoMovimentoRoteiroEmpresa) throws Exception{

		LOGGER.info("Inicio da criação arquivo faturamento MODELO 4");
		this.ejbCreate();
		Usuario usuario;
		// Recupera o usuário logado
		if(idFuncionalidadeIniciada == null) usuario = Usuario.USUARIO_BATCH;
		else usuario = this.getControladorBatch().obterUsuarioFuncionalidadeIniciada(idFuncionalidadeIniciada);


		// 1) Criar arquivo temp
		ArquivoFaturamentoImediatoCaerdHelper arquivoHelper = new ArquivoFaturamentoImediatoCaerdHelper(idFaturamentoGrupo,
						anoMesReferenciaFaturamento);

		// REGISTRO TIPO 00 - header
		arquivoHelper.addRagistroTipo0(idFaturamentoGrupo, anoMesReferenciaFaturamento);

		// REGISTRO TIPO 01 - tarifas
		preencherRegistroTipo01(anoMesReferenciaFaturamento, arquivoHelper);

		// REGISTRO TIPO 02 - Anormalidade de Leitura
		preencherRegistroTipo02(arquivoHelper);

		// REGISTRO TIPO 03,04,05,06,07 - Capa de Setores
		preencherRegistroTipo03(anoMesReferenciaFaturamento, idFaturamentoGrupo, idEmpresa, arquivoHelper, colecaoMovimentoRoteiroEmpresa,
						usuario);


		// REGISTRO TIPO 09 - Trailler
		arquivoHelper.addRagistroTipo9();

		// Atualiza MOVIMENTO_ROTEIRO_EMPRESA
		getControladorBatch().atualizarColecaoObjetoParaBatch(colecaoMovimentoRoteiroEmpresa);

		// Gerar Relatório de Totais. [SB0006 – Gerar Resumo do Processamento]
		Date dataVencimentoHeader = obterDataVencimentoRelatorio(anoMesReferenciaFaturamento, idFaturamentoGrupo, idsRotas);
		this.iniciarProcessamentoRelatorioOcorrenciaGeracaoPreFatResumo(idFaturamentoGrupo, anoMesReferenciaFaturamento,
						idFuncionalidadeIniciada, arquivoHelper.getResumoPreFaturamento(), dataVencimentoHeader);

		/*
		 * Envia o arquivo gerado para o email especificado e disponibiliza pra download no GSAN
		 */
		EnvioEmail envioEmail = getControladorCadastro().pesquisarEnvioEmail(EnvioEmail.ARQUIVO_DADOS_TABELAS_FATURAMENTO_IMEDIATO);
		this.enviarEmailArquivoFaturamentoImediato(arquivoHelper.getConteudoArquivo(), arquivoHelper.getNomeArquivo(), envioEmail,
						idFuncionalidadeIniciada);
		LOGGER.info("Fim de criação arquivo faturamento MODELO 4");

	}

	private boolean preencherRegistroTipo05(MovimentoRoteiroEmpresa mre, ArquivoFaturamentoImediatoCaerdHelper arquivoHelper,
					HashMap<Integer, String> mapDescricaoAbreviadaAnormalidadesConsumo)
					throws ControladorException{

		if(Util.isVazioOuBranco(mre.getNumeroHidrometro())) return false;

		HidrometroRelatorioOSHelper hidrometroRelatorioOSHelper = getControladorMicromedicao().obterDadosHidrometroPorTipoMedicao(
						mre.getImovel().getId(), MedicaoTipo.LIGACAO_AGUA);

		Integer numeroLeituraAnterior = mre.getNumeroLeituraAnterior();
		if(numeroLeituraAnterior == null){
			numeroLeituraAnterior = 0;
			LOGGER.error("MOVIMENTO_ROTEIRO_EMPRESA[" + mre.getId() + "] HIDROM[" + mre.getNumeroHidrometro()
							+ "] com LEITURA ANTERIOR NULL! - Provável erro na geração deste MRE.");
			LOGGER.info("PARA MOVIMENTO_ROTEIRO_EMPRESA[" + mre.getId() + "] HIDROM[" + mre.getNumeroHidrometro()
							+ "] assumindo LEITURA ANTERIOR = 0");
		}
		int[] faixaLeituraEsperada = getControladorMicromedicao().calcularFaixaLeituraEsperada(//
						mre.getNumeroConsumoMedio(), null,//
						new Hidrometro(Util.obterShort(hidrometroRelatorioOSHelper.getHidrometroNumeroDigitos())),//
						numeroLeituraAnterior);

		return arquivoHelper.addRagistroTipo5(mre, hidrometroRelatorioOSHelper, faixaLeituraEsperada,
						mapDescricaoAbreviadaAnormalidadesConsumo);
	}

	private void preencherRegistroTipo04(MovimentoRoteiroEmpresa mre, ArquivoFaturamentoImediatoCaerdHelper arquivoHelper)
					throws ErroRepositorioException, ControladorException{

		Cliente clienteResponsavel = repositorioImovel.pesquisarClienteResponsavelImovel(mre.getImovel().getId());
		DebitoAutomatico debitoAutomatico = repositorioFaturamento.pesquisarDebitoAutomaticoImovel(mre.getImovel().getId());

		String endereco = getControladorEndereco().pesquisarEndereco(mre.getImovel().getId());
		LigacaoAgua ligacaoAgua = (LigacaoAgua) getControladorUtil().pesquisar(mre.getImovel().getId(), LigacaoAgua.class, true);
		LigacaoEsgoto ligacaoEsgoto = (LigacaoEsgoto) getControladorUtil().pesquisar(mre.getImovel().getId(), LigacaoEsgoto.class, true);

		// [UC0105] - Obter Consumo Mínimo da Ligação
		int consumoMinimoLigacao = getControladorMicromedicao().obterConsumoMinimoLigacao(mre.getImovel(), null);

		String indicadorQuitacaoDebito = "N";

		// Indicador Declaração de Quitação
		// [FS0010 – Verificar indicador de mensagem de quitação de débito]
		// O sistema obtém o ano de referência para a geração da
		// declaração anual de quitação de débitos
		Integer anoBaseDeclaracaoQuitacaoDebitoAnual = Integer
						.valueOf((String) ParametroFaturamento.P_ANO_BASE_DECLARACAO_QUITACAO_DEBITO_ANUAL.executar());

		// 1. Caso o ano de referência seja diferente de “-1” e exista registro de quitação
		// pendente associado ao imóvel
		if(!Util.isVazioOuBranco(anoBaseDeclaracaoQuitacaoDebitoAnual)
						&& !anoBaseDeclaracaoQuitacaoDebitoAnual.equals(ConstantesSistema.SEM_ANO_BASE_DECLARACAO_QUITACAO_DEBITO_ANUAL)){

			// Verifica geração da declaração para o grupo no ano de referência
			FiltroQuitacaoDebitoAnual filtroQuitacaoDebitoAnual = new FiltroQuitacaoDebitoAnual();

			filtroQuitacaoDebitoAnual
							.adicionarParametro(new ParametroSimples(FiltroQuitacaoDebitoAnual.IMOVEL_ID, mre.getImovel().getId()));
			filtroQuitacaoDebitoAnual.adicionarParametro(new ParametroSimples(FiltroQuitacaoDebitoAnual.ANO_REFERENCIA,
							anoBaseDeclaracaoQuitacaoDebitoAnual));
			filtroQuitacaoDebitoAnual.adicionarParametro(new ParametroSimples(FiltroQuitacaoDebitoAnual.INDICADOR_IMPRESSAO,
							ConstantesSistema.NAO));

			// Obtém registros na tabela QUITACAO_ANUAL_DEBITO
			Collection<QuitacaoDebitoAnual> colecaoQuitacaoDebitoAnual = (Collection<QuitacaoDebitoAnual>) this.getControladorUtil()
							.pesquisar(filtroQuitacaoDebitoAnual, QuitacaoDebitoAnual.class.getName());

			// 1. Caso o ano de referência seja diferente de “-1” e exista registro de quitação
			// pendente associado ao imóvel
			if(!Util.isVazioOrNulo(colecaoQuitacaoDebitoAnual)){
				// 1.1. Atribuir o valor S ao Indicador de Mensagem de Quitação do Débito.
				indicadorQuitacaoDebito = "S";

				for(QuitacaoDebitoAnual quitacaoDebitoAnual : colecaoQuitacaoDebitoAnual){
					// 1.2. O sistema indica a emissão da declaração anual de quitação de
					// débitos
					// atualiza a tabela QUITACAO_DEBITO_ANUAL com os seguintes valores:

					// QADB_ICIMPRESSAO >> 1 (sim)
					quitacaoDebitoAnual.setIndicadorImpressao(ConstantesSistema.SIM);

					// QADB_TMULTIMAALTERACAO >> data e hora correntes
					quitacaoDebitoAnual.setUltimaAlteracao(new Date());

					// *********************************************************
					// Inclui o registro na tabela de quitação de débito anual
					this.getControladorUtil().atualizar(quitacaoDebitoAnual);
					// *********************************************************
				}
			}else{
				indicadorQuitacaoDebito = "N";
			}
		}
		// 2. Caso contrário, atribuir o valor N (zero) ao Indicador de Mensagem de Quitação do
		// Débito.
		else{
			indicadorQuitacaoDebito = "N";
		}

		arquivoHelper.addRagistroTipo4(mre, clienteResponsavel, debitoAutomatico, endereco, ligacaoAgua, ligacaoEsgoto,
						String.valueOf(consumoMinimoLigacao), indicadorQuitacaoDebito);
	}

	private void preencherRegistroTipo03(Integer anoMesReferenciaFaturamento, Integer idFaturamentoGrupo, Integer idEmpresa,
					ArquivoFaturamentoImediatoCaerdHelper arquivoHelper, Collection colecaoMovimentoRoteiroEmpresa, Usuario usuario)
					throws ErroRepositorioException, ControladorException{

		Collection colecaoMovimentoRoteiroEmpresaNova = null;

		Collection<Object[]> dadosSetor = repositorioLocalidade.pesquisarDadosResumidosSetorComercial(idEmpresa);
		Integer localidadeId;
		String localidadeDesc;
		Integer setorCod;
		Integer limiteConsumo;
		Integer indicadorAjuste;
		Integer localidadeEloId;
		QualidadeAgua qualidadeAgua;
		QualidadeAguaPadrao qualidadeAguaPadrao = (QualidadeAguaPadrao) Util.retonarObjetoDeColecao(getControladorUtil().pesquisar(
						new QualidadeAguaPadrao()));
		SistemaParametro sistemaParametro = getControladorUtil().pesquisarParametrosDoSistema();
		FiltroConsumoAnormalidade filtroConsumoAnormalidade = new FiltroConsumoAnormalidade();
		filtroConsumoAnormalidade.setCampoOrderBy(FiltroConsumoAnormalidade.ID);
		Collection<ConsumoAnormalidade> colecaoConsumoAnormalidade = getControladorUtil().pesquisar(filtroConsumoAnormalidade,
						ConsumoAnormalidade.class.getName());
		HashMap<Integer, String> mapDescricaoAbreviadaAnormalidadesConsumo = new HashMap<Integer, String>();

		for(ConsumoAnormalidade consumoAnormalidade : colecaoConsumoAnormalidade){

			mapDescricaoAbreviadaAnormalidadesConsumo.put(consumoAnormalidade.getId(), consumoAnormalidade.getDescricaoAbreviada()
							.substring(0, 2));
		}

		Object[] mensagens;
		Integer gerenciaRegionalId;
		Integer setorId;
		for(Object[] setor : dadosSetor){
			LOGGER.error("=================> INICIO PROCESSO REGISTRO TIPO 3 <=====================");
			localidadeId = (Integer) setor[0];
			localidadeDesc = (String) setor[1];
			setorCod = (Integer) setor[2];

			if(setor[3] != null){

				limiteConsumo = (Integer) setor[3];
			}else{

				limiteConsumo = sistemaParametro.getMenorConsumoGrandeUsuario();
			}

			indicadorAjuste = (Integer) setor[4];
			localidadeEloId = (Integer) setor[5];
			gerenciaRegionalId = (Integer) setor[6];
			setorId = (Integer) setor[7];

			colecaoMovimentoRoteiroEmpresaNova = new ArrayList();
			colecaoMovimentoRoteiroEmpresaNova.addAll(colecaoMovimentoRoteiroEmpresa);
			
			for(Object object : colecaoMovimentoRoteiroEmpresaNova){
				MovimentoRoteiroEmpresa mre = (MovimentoRoteiroEmpresa) object;

				if(mre.getLocalidade().getId().equals(localidadeId) && mre.getCodigoSetorComercial().equals(setorCod)){
					mensagens = getControladorFaturamento().pesquisarContaMensagem(anoMesReferenciaFaturamento, idFaturamentoGrupo,
									gerenciaRegionalId, localidadeId,
									setorId);
					qualidadeAgua = pesquisarDadosQualidadeAgua(anoMesReferenciaFaturamento, localidadeId, localidadeEloId);
					arquivoHelper.addRagistroTipo3(localidadeId, localidadeDesc, setorCod, limiteConsumo, qualidadeAgua,
									qualidadeAguaPadrao, indicadorAjuste, mensagens);
					break;
				}
			}
			LOGGER.error("=================> INICIO PROCESSO REGISTRO TIPO 4, 5, 6, 7 <=====================");
			for(Object object : colecaoMovimentoRoteiroEmpresaNova){

				MovimentoRoteiroEmpresa mre = (MovimentoRoteiroEmpresa) object;

				if(mre.getLocalidade().getId().equals(localidadeId) && mre.getCodigoSetorComercial().equals(setorCod)){

					// REGISTRO TIPO 04 - MOVIMENTO ROTEIRO EMPRESA - Dados Consumidor
					preencherRegistroTipo04(mre, arquivoHelper);

					// REGISTRO TIPO 05 - MOVIMENTO ROTEIRO EMPRESA - Dados Hidrometro
					preencherRegistroTipo05(mre, arquivoHelper, mapDescricaoAbreviadaAnormalidadesConsumo);

					// REGISTRO TIPO 06 - MOVIMENTO ROTEIRO EMPRESA - Debitos anteriores
					List<DebitoAnteriorHelper> collDebitosAnteriores = this.obterDebitosAnteriores(mre);
					List<DebitoAnteriorHelper> listDebitosAnteriores = new ArrayList<DebitoAnteriorHelper>();
					listDebitosAnteriores.addAll(collDebitosAnteriores);
					List sortFields = new ArrayList();
					sortFields.add(new BeanComparator("conta.referencia"));
					ComparatorChain multiSort = new ComparatorChain(sortFields);
					Collections.sort((List) listDebitosAnteriores, multiSort);
					String representacaoNumericaCodBarra = "";

					if(mre.getNumeroDocumentoCobranca() != null){

						CobrancaDocumento cobrancaDocumento = (CobrancaDocumento) getControladorUtil().pesquisar(
									mre.getNumeroDocumentoCobranca(), CobrancaDocumento.class, true);

						// [UC0229] Obtém a representação numérica do código de barra
						representacaoNumericaCodBarra = this.getControladorArrecadacao().obterRepresentacaoNumericaCodigoBarra(
										PagamentoTipo.PAGAMENTO_TIPO_COBANCA_MATRICULA_IMOVEL, cobrancaDocumento.getValorDocumento(),
										mre.getLocalidade().getId(), mre.getImovel().getId(), null, null, null, null,
										mre.getNumeroDocumentoCobranca().toString(), cobrancaDocumento.getDocumentoTipo().getId(), null,
										null, null, null, null, null);
					}

					arquivoHelper.addRagistroTipo6(mre, listDebitosAnteriores, representacaoNumericaCodBarra);

					// REGISTRO TIPO 07 - MOVIMENTO ROTEIRO EMPRESA - Rubricas
					arquivoHelper.addRegistroTipo7(mre);

					// Acumula valores para geração do relatório de resumo do processamento
					arquivoHelper.addResumo(mre, !Util.isVazioOrNulo(collDebitosAnteriores));

					/*
					 * O sistema indica a geração do arquivo texto para o movimento de leitura
					 * atualizando a
					 * tabela MOVIMENTO_ROTEIRO_EMPRESA com os seguintes valores
					 */
					mre.setIndicadorGeracaoArquivoTexto(ConstantesSistema.SIM);
					mre.setIdUsuarioGeracaoArquivoTexto(usuario.getId());
					mre.setTempoGeracaoArquivoTexto(new Date());

					colecaoMovimentoRoteiroEmpresa.remove(object);
				}
			}
			LOGGER.error("=================> FIM PROCESSO REGISTRO TIPO 4, 5, 6, 7 <=====================");
		}
	}

	private void preencherRegistroTipo02(ArquivoFaturamentoImediatoCaerdHelper arquivoHelper) throws ControladorException{

		List<LeituraAnormalidade> totdasLeiturasAnormalidades = null;
		try{
			totdasLeiturasAnormalidades = repositorioFaturamento.pesquisarLeituraAnormalidadeFaturamentoImediato();
		}catch(ErroRepositorioException e){
			LOGGER.error("Falha ao consultar Leitura Anormalidades", e);
		}
		int ini = 0;
		int fim = totdasLeiturasAnormalidades.size();
		while(ini < fim){
			Collection<LeituraAnormalidade> subList10 = new ArrayList(//
							totdasLeiturasAnormalidades.subList(ini, (ini + 10 <= fim) ? ini + 10 : fim));
			arquivoHelper.addRagistroTipo2(subList10);
			ini += 10;
		}
	}

	private void preencherRegistroTipo01(Integer anoMesReferenciaFaturamento, ArquivoFaturamentoImediatoCaerdHelper arquivoHelper)
					throws ErroRepositorioException, ControladorException{

		// Grava registro tipo 1
		List<Object[]> colecaoTarifas = repositorioFaturamento.pesquisarTarifasArquivoTextoFaturamentoImediato(anoMesReferenciaFaturamento);

		// [FS0004 – Verificar existência de tarifas]
		if(!Util.isVazioOrNulo(colecaoTarifas)){

			arquivoHelper.addRagistroTipo1(colecaoTarifas);
		}else{

			throw new ControladorException("atencao.tarifas_vigentes_inexistentes");
		}
	}
	/**
	 * [UC0948] Informar Dados de Leituras e Anormalidades *
	 * 
	 * @param faturamentoGrupo
	 * @param header
	 * @param nomeArquivo
	 * @throws ControladorException
	 */

	public File execParamConverterArquivoLeituraFormato3(ParametroSistema parametroSistema, File arquivoLeitura,
					FaturamentoGrupo faturamentoGrupo) throws FileNotFoundException, ControladorException, IOException, ParseException{

		Collection<String> linhas = new ArrayList<String>();
		FileReader fileReader = new FileReader(arquivoLeitura);
		BufferedReader br = new BufferedReader(fileReader);
		String nomeArquivo = arquivoLeitura.getName();
		validarNomeArquivoFormato3(nomeArquivo);
		String header = br.readLine();
		validarHeaderFormato3(faturamentoGrupo, header, nomeArquivo);
		validarTrailerETipoRegistroFormato3(linhas, br);
		File arquivoDefinitivo = new File(arquivoLeitura.getName() + "-DEFINITIVO.txt");
		FileOutputStream fout = new FileOutputStream(arquivoDefinitivo);
		PrintWriter pw = new PrintWriter(fout);
		gerarHeaderArquivoDefinitivoFormato3(linhas, nomeArquivo, header, pw);
		gerarRegistrosArquivoDefinitivoFormato3(linhas, header, pw);
		gerarTraillerArquivoDefinitivoFormato3(linhas, pw);
		pw.flush();
		pw.close();
		return arquivoDefinitivo;
	}
	/**
	 * [UC0948] Informar Dados de Leituras e Anormalidades
	 * 
	 * @param faturamentoGrupo
	 * @param header
	 * @param nomeArquivo
	 * @throws ControladorException
	 */

	private void validarNomeArquivoFormato3(String nomeArquivo) throws ControladorException{

		if(!"RET".equalsIgnoreCase(recuperarValorLinha(nomeArquivo, 0, 3))){
			throw new ControladorException("atencao.nome_arquivo_conversao_invalido", null, nomeArquivo);
		}
		String idFaturamentoGrupo = recuperarValorLinha(nomeArquivo, 3, 3);
		FaturamentoGrupo faturamentoGrupoNomeArquivo = (FaturamentoGrupo) getControladorUtil().pesquisar(
						Util.obterInteger(idFaturamentoGrupo), FaturamentoGrupo.class, false);
		if(faturamentoGrupoNomeArquivo == null){
			throw new ControladorException("atencao.nome_arquivo_conversao_invalido", null, nomeArquivo);
		}
		String referencia = recuperarValorLinha(nomeArquivo, 6, 6);
		if(Util.validarAnoMesSemBarra(referencia)){
			throw new ControladorException("atencao.nome_arquivo_conversao_invalido", null, nomeArquivo);
		}

	}

	/**
	 * [UC0948] Informar Dados de Leituras e Anormalidades
	 * [SB0102A] – Validar Header do Arquivo Leitura Formato 3
	 * 
	 * @param faturamentoGrupo
	 * @param header
	 * @param nomeArquivo
	 * @throws ControladorException
	 */

	private void validarHeaderFormato3(FaturamentoGrupo faturamentoGrupo, String header, String nomeArquivo) throws ControladorException{

		int posicaoAnterior = 0;
		int qtidadeCaracteres = 0;

		String tipoRegistro = recuperarValorLinha(header, posicaoAnterior += qtidadeCaracteres, qtidadeCaracteres = 1);
		if(!"0".equals(tipoRegistro)){
			throw new ControladorException("atencao.arquivo_sem_header", null, nomeArquivo);
		}

		String referenciaFaturamentoString = recuperarValorLinha(header, posicaoAnterior += qtidadeCaracteres, qtidadeCaracteres = 6);
		Integer referenciaFaturamento = Util.obterInteger(referenciaFaturamentoString);
		if(referenciaFaturamento == null){
			throw new ControladorException("atencao.arquivo_header_ano_mes_nao_numerico", null, referenciaFaturamentoString);
		}
		if(Util.validarAnoMesSemBarra(referenciaFaturamentoString)){
			throw new ControladorException("atencao.arquivo_header_ano_mes_invalido", null, referenciaFaturamentoString);
		}
		if(!faturamentoGrupo.getAnoMesReferencia().equals(referenciaFaturamento)){
			throw new ControladorException("atencao.arquivo_header_ano_mes_diferente_ano_mes_faturamento_grupo", null,
							referenciaFaturamentoString, faturamentoGrupo.getAnoMesReferencia().toString());
		}

		String grupoFaturamentoString = recuperarValorLinha(header, posicaoAnterior += qtidadeCaracteres, qtidadeCaracteres = 3);
		Integer idGrupoFaturamento = Util.obterInteger(grupoFaturamentoString);
		if(idGrupoFaturamento == null){
			throw new ControladorException("atencao.arquivo_header_grupo_faturamento_nao_numerico", null, grupoFaturamentoString);
		}

		if(!faturamentoGrupo.getId().equals(idGrupoFaturamento)){
			throw new ControladorException("atencao.arquivo_header_faturamento_grupo_arquivo_diferente_faturamento_grupo_tela", null,
							grupoFaturamentoString, faturamentoGrupo.getId().toString());
		}

		String dataLeituraString = recuperarValorLinha(header, posicaoAnterior += qtidadeCaracteres, qtidadeCaracteres = 8);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Integer dataLeituraNumerica = Util.obterInteger(dataLeituraString);
		if(dataLeituraNumerica == null){
			throw new ControladorException("atencao.arquivo_header_data_leitura_nao_numerico", null, dataLeituraString);
		}
		Date dataLeitura = null;
		if(Util.validarAnoMesDiaSemBarra(dataLeituraString)){
			throw new ControladorException("atencao.arquivo_header_data_leitura_invalida", null, dataLeituraString);
		}
		try{
			dataLeitura = sdf.parse(dataLeituraString);
		}catch(ParseException e){
			throw new ControladorException("atencao.arquivo_header_data_leitura_invalida", null, dataLeituraString);
		}
		if(dataLeitura.after(new Date())){
			throw new ControladorException("atencao.arquivo_header_data_leitura_invalida", null, dataLeituraString);
		}

		if(Util.getAnoMesComoInt(dataLeitura) != faturamentoGrupo.getAnoMesReferencia().intValue()
						&& Util.getAnoMesComoInt(dataLeitura) != Util.somarData(faturamentoGrupo.getAnoMesReferencia().intValue())
						&& Util.getAnoMesComoInt(dataLeitura) != Util.subtrairData(faturamentoGrupo.getAnoMesReferencia().intValue())){
			throw new ControladorException("atencao.arquivo_header_data_leitura_diferente_data_leitura_faturamento_grupo_tela", null,
							dataLeituraString, faturamentoGrupo.getAnoMesReferencia().toString());
		}
	}

	/**
	 * [UC0948] Informar Dados de Leituras e Anormalidades
	 * [SB0102] – Converter Arquivo Leitura Formato 3
	 * 
	 * @param linhas
	 * @param br
	 * @throws IOException
	 * @throws ControladorException
	 */
	private void validarTrailerETipoRegistroFormato3(Collection<String> linhas, BufferedReader br) throws IOException, ControladorException{

		Integer contadorLinhas = 0;
		String linha;
		String tipoRegistro;
		while((linha = br.readLine()) != null){
			tipoRegistro = linha.substring(0, 1);

			if("0".equals(tipoRegistro)){
				linhas.add(linha);
				contadorLinhas++;
			}else if("1".equals(tipoRegistro)){
				linhas.add(linha);
				contadorLinhas++;
			}else{
				throw new ControladorException("atencao.arquivo_retorno_registro_tipo_invalido");
			}
		}
	}

	/**
	 * [UC0948] Informar Dados de Leituras e Anormalidades
	 * [SB0102] – Converter Arquivo Leitura Formato 3
	 * 
	 * @param linhas
	 * @param nomeArquivo
	 * @param header
	 * @param pw
	 * @throws ControladorException
	 */
	private void gerarHeaderArquivoDefinitivoFormato3(Collection<String> linhas, String nomeArquivo, String header, PrintWriter pw)
					throws ControladorException{

		StringBuilder headerArquivoDefinitivo = new StringBuilder();
		// 0
		headerArquivoDefinitivo.append("0");
		// empresa
		headerArquivoDefinitivo.append(Util.completaStringComEspacoAEsquerda("", 6));
		// Indicador de transmissão ou recepção
		headerArquivoDefinitivo.append("R");
		// Grupo Faturamento - 0.03
		String grupoFaturamentoHeader = recuperarValorLinha(header, 7, 3);
		headerArquivoDefinitivo.append(Util.completarStringZeroEsquerda(Util.obterInteger(grupoFaturamentoHeader).toString(), 2));
		// Ano e mês ref. faturamento 0.02
		String anoMesReferenciaHeader = recuperarValorLinha(header, 1, 6);
		headerArquivoDefinitivo.append(Util.completarStringZeroEsquerda(anoMesReferenciaHeader, 6));
		// Data geração arquivo
		String dataGeracaoArquivo = recuperarDataCorrente();
		headerArquivoDefinitivo.append(Util.completarStringZeroEsquerda(dataGeracaoArquivo, 8));
		// Quantidade de registros
		headerArquivoDefinitivo.append(Util.completarStringZeroEsquerda(String.valueOf(linhas.size() + 2), 6));

		pw.println(headerArquivoDefinitivo.toString());
	}

	/**
	 * [UC0948] Informar Dados de Leituras e Anormalidades
	 * [SB0102] – Validar Header do Arquivo Leitura Formato 3
	 * 
	 * @param linhas
	 * @param header
	 * @param pw
	 * @throws ControladorException
	 * @throws ParseException
	 */
	private void gerarRegistrosArquivoDefinitivoFormato3(Collection<String> linhas, String header, PrintWriter pw)
					throws ControladorException, ParseException{

		StringBuilder registro = null;

		String matriculaImovel = null;
		String leituraAtualReal = null;
		String leituraFaturada = null;
		String consumoFaturado = null;
		String indicadorConfirmacaoLeitura = null;
		String codigoAnormalidadeLeitura = null;
		String codigoAnormalidadeConsumo = null;
		Map<String, Integer> mapaTipoConsumo = montarMapaTipoConsumoValidosFormato3();
		Map<String, Integer> mapaIndicadorEmissao = montarMapaIndicadorEmissaoValidosFormato3();
		String diasConsumo = null;
		String valorAgua = null;
		String valorEsgoto = null;
		String valorDebitos = null;
		String valorCrebitos = null;
		String dataLeituraAtual = null;
		String dataLeituraMes = null;
		String localidade = null;
		String setorComercial = null;

		FiltroMedicaoTipo filtroMedicaoTipo = new FiltroMedicaoTipo();
		filtroMedicaoTipo.adicionarParametro(new ParametroSimples(FiltroMedicaoTipo.DESCRICAO, MedicaoTipo.DESC_LIGACAO_AGUA));
		MedicaoTipo medicaoTipo = (MedicaoTipo) Util.retonarObjetoDeColecao(getControladorUtil().pesquisar(filtroMedicaoTipo,
						MedicaoTipo.class.getName()));

		Integer i = 1;

		for(String linha : linhas){

			registro = new StringBuilder();

			// Tipo do Registro
			registro.append("1");

			// Identificação do Agente Comercial -
			registro.append(Util.completarStringZeroEsquerda("0", 6));

			// Data de Leitura (DDMMAAAA) - 0.04
			dataLeituraMes = recuperarValorLinha(header, 10, 8);
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat sdf2 = new SimpleDateFormat("ddMMyyyy");
			dataLeituraMes = sdf2.format(sdf1.parse(dataLeituraMes));

			if(Util.isInteger(dataLeituraMes)){

				registro.append(Util.completarStringZeroEsquerda(dataLeituraMes, 8));
			}else{

				throw new ControladorException("atencao.arquivo_linha_invalido", null, i.toString(), linha);
			}

			// Matrícula do Imóvel - 1.16
			matriculaImovel = recuperarValorLinha(linha, 66, 10);
			if(Util.isInteger(matriculaImovel)){
				registro.append(recuperarValorLinha(matriculaImovel, 2, 8));
			}else{
				throw new ControladorException("atencao.arquivo_linha_invalido", null, i.toString(), linha);
			}

			// Código da Localidade 1.17
			localidade = recuperarValorLinha(linha, 76, 3);
			if(Util.isInteger(localidade)){
				registro.append(recuperarValorLinha(localidade, 0, 3));
			}else{

				throw new ControladorException("atencao.arquivo_linha_invalido", null, i.toString(), linha);
			}

			// Código da Setor 1.18
			setorComercial = recuperarValorLinha(linha, 79, 2);
			if(Util.isInteger(localidade)){
				registro.append(Util.completarStringZeroEsquerda(Util.obterInteger(setorComercial).toString(), 3));
			}else{
				throw new ControladorException("atencao.arquivo_linha_invalido", null, i.toString(), linha);
			}

			// Número da Quadra
			registro.append(Util.completarStringZeroEsquerda("0", 4));

			// Número do Lote
			registro.append(Util.completarStringZeroEsquerda("0", 4));

			// Número do Sublote
			registro.append(Util.completarStringZeroEsquerda("0", 3));

			// Tipo de Medição
			registro.append(medicaoTipo.getId().toString());

			// Leitura do Hidrômetro - 1.02
			leituraAtualReal = recuperarValorLinha(linha, 1, 8);
			if(Util.isInteger(leituraAtualReal)){
				registro.append(Util.completarStringZeroEsquerda(Util.obterInteger(leituraAtualReal).toString(), 6));
			}else{
				registro.append(Util.completarStringZeroEsquerda("0", 6));
			}
			// Leitura Faturada - 1.03
			leituraFaturada = recuperarValorLinha(linha, 9, 8);
			if(Util.converterStringParaInteger(leituraFaturada) > 0){
				registro.append(Util.completarStringZeroEsquerda(Util.obterInteger(leituraFaturada).toString(), 6));
			}else{
				registro.append(Util.completarStringZeroEsquerda("0", 6));
			}

			// Consumo Faturado - Caso 1.04
			consumoFaturado = recuperarValorLinha(linha, 17, 6);
			if(Util.isInteger(consumoFaturado)){
				registro.append(Util.completarStringZeroEsquerda(consumoFaturado, 6));
			}else{
				throw new ControladorException("atencao.arquivo_linha_invalido", null, i.toString(), linha);
			}

			// Código da Anormalidade de Leitura - 1.08
			codigoAnormalidadeLeitura = recuperarValorLinha(linha, 26, 2);
			registro.append(Util.completarStringZeroEsquerda(codigoAnormalidadeLeitura, 3));

			// Código da Anormalidade de Consumo - 1.13 [SB0102C – Converter Anormalidade Consumo
			// GCS para Anormalidade de Consumo GSAN]
			codigoAnormalidadeConsumo = converterAnormalidadeConsumoParaGsan(recuperarValorLinha(linha, 61, 2));
			registro.append(Util.completarStringZeroEsquerda(codigoAnormalidadeConsumo, 3));

			// Tipo do Consumo - 1.06 [SB0101B – Converter Tipo Consumo para Tipo Consumo GSAN]
			if(mapaTipoConsumo.containsKey(recuperarValorLinha(linha, 24, 1))){
				registro.append(Util.completarStringZeroEsquerda(mapaTipoConsumo.get(recuperarValorLinha(linha, 24, 1)).toString(), 2));
			}else{
				throw new ControladorException("atencao.arquivo_linha_tipo_consumo_invalido", null, i.toString());
			}

			// Dias de Consumo - 1.14
			diasConsumo = recuperarValorLinha(linha, 63, 2);
			if(Util.isInteger(diasConsumo)){
				registro.append(Util.completarStringZeroEsquerda(diasConsumo, 2));
			}else{
				throw new ControladorException("atencao.arquivo_linha_invalido", null, i.toString(), linha);
			}

			// Valor de Água - 1.10
			valorAgua = recuperarValorLinha(linha, 29, 11);
			if(Util.isNumero(valorAgua, false, 0)){
				registro.append(Util.completarStringZeroEsquerda(valorAgua, 11));
			}else{
				throw new ControladorException("atencao.arquivo_linha_invalido", null, i.toString(), linha);
			}

			// Valor de Esgoto - 1.11
			valorEsgoto = recuperarValorLinha(linha, 40, 11);
			if(Util.isNumero(valorEsgoto, false, 0)){
				registro.append(Util.completarStringZeroEsquerda(valorEsgoto, 11));
			}else{
				throw new ControladorException("atencao.arquivo_linha_invalido", null, i.toString(), linha);
			}

			// Valor Débitos - 1.12
			valorDebitos = recuperarValorLinha(linha, 51, 10);
			if(Util.isNumero(valorDebitos, false, 0)){
				registro.append(Util.completarStringZeroEsquerda(valorDebitos, 11));
			}else{
				throw new ControladorException("atencao.arquivo_linha_invalido", null, i.toString(), linha);
			}

			// Valor Créditos 1.22
			valorCrebitos = recuperarValorLinha(linha, 84, 8);
			if(Util.isNumero(valorCrebitos, false, 0)){
				registro.append(Util.completarStringZeroEsquerda(valorCrebitos, 11));
			}else{
				throw new ControladorException("atencao.arquivo_linha_invalido", null, i.toString(), linha);
			}

			// Indicador de Grande Consumidor
			registro.append(Util.completarStringZeroEsquerda("0", 1));

			// Indicador Emissão 1.15
			if(mapaIndicadorEmissao.containsKey(recuperarValorLinha(linha, 65, 1))){

				registro.append(Util.completarStringZeroEsquerda(mapaIndicadorEmissao.get(recuperarValorLinha(linha, 65, 1)).toString(), 1));
			}else{

				throw new ControladorException("atencao.arquivo_linha_invalido", null, i.toString());
			}

			// Indicador de Confirmação de Leitura - 1.05
			indicadorConfirmacaoLeitura = recuperarValorLinha(linha, 23, 1);

			if(indicadorConfirmacaoLeitura != null && Util.isInteger(indicadorConfirmacaoLeitura)){

				if(indicadorConfirmacaoLeitura.equals("0")){

					registro.append(Util.completarStringZeroEsquerda(LeituraSituacao.NAO_REALIZADA.toString(), 1));
				}else if(indicadorConfirmacaoLeitura.equals("1")){

					registro.append(Util.completarStringZeroEsquerda(LeituraSituacao.REALIZADA.toString(), 1));
				}else if(indicadorConfirmacaoLeitura.equals("2")){

					registro.append(Util.completarStringZeroEsquerda(LeituraSituacao.CONFIRMADA.toString(), 1));
				}else if(indicadorConfirmacaoLeitura.equals("3")){

					registro.append(Util.completarStringZeroEsquerda(LeituraSituacao.RECONFIRMADA.toString(), 1));
				}else{

					registro.append(Util.completarStringZeroEsquerda(LeituraSituacao.INDEFINIDO.toString(), 1));
				}

			}else{

				registro.append(Util.completarStringZeroEsquerda(LeituraSituacao.NAO_REALIZADA.toString(), 1));
			}

			// Valor Imposto Federal -
			registro.append(Util.completarStringZeroEsquerda("0", 11));

			// Data Vencimento Retornada -
			registro.append(Util.completarStringZeroEsquerda("0", 8));

			// Data de Leitura - 0.04
			dataLeituraAtual = recuperarValorLinha(header, 10, 8);
			SimpleDateFormat sdf5 = new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat sdf6 = new SimpleDateFormat("ddMMyyyy");
			dataLeituraAtual = sdf6.format(sdf5.parse(dataLeituraAtual));

			if(!Util.isVazioOuBranco(dataLeituraAtual)){
				registro.append(Util.completarStringZeroEsquerda(dataLeituraAtual, 8));
			}

			// Valor Rateio
			registro.append(Util.completarStringZeroEsquerda("0", 11));

			// Preencher demais posições
			registro.append(Util.completarStringZeroEsquerda("0", 1));

			i++;
			pw.println(registro.toString());
		}
	}

	/**
	 * [UC0948] Informar Dados de Leituras e Anormalidades
	 * [SB0102A] – Validar Header do Arquivo Leitura Formato 3
	 * 
	 * @param linhas
	 * @param header
	 * @param pw
	 * @throws ControladorException
	 * @throws ParseException
	 */
	private void gerarTraillerArquivoDefinitivoFormato3(Collection<String> linhas, PrintWriter pw){

		StringBuilder traillerArquivoDefinitivo = new StringBuilder();
		// Tipo do Registro
		traillerArquivoDefinitivo.append("9");
		// Quantidade Registros
		traillerArquivoDefinitivo.append(Util.completarStringZeroEsquerda(String.valueOf(linhas.size() + 2), 6));
		// Quantidade de Registros Tipo Detalhe
		traillerArquivoDefinitivo.append(Util.completarStringZeroEsquerda(String.valueOf(linhas.size()), 6));

		pw.println(traillerArquivoDefinitivo.toString());
	}

	/**
	 * [OC1111073]: Criado para corrigir o problema de mais de mil rotas do "in" do select.
	 * Autor Josenildo Neves.
	 * Date: 31/07/2013
	 * 
	 * @param idsRotas
	 * @return
	 * @throws ErroRepositorioException
	 */
	private List<Object[]> pesquisarSetorComercialFaturamentoImediatoComLimiteRotas(String idsRotas) throws ErroRepositorioException{

		List<Object[]> colecaoSetorComercial = new ArrayList<Object[]>();

		for(String idsRotasComLimite : Util.retornaListaStringComLimiteItemConsulta(idsRotas)){
			colecaoSetorComercial.addAll(repositorioFaturamento.pesquisarSetorComercialFaturamentoImediato(idsRotasComLimite));
		}

		return colecaoSetorComercial;
	}
	
	/**
	 * [OC1111073]: Criado para corrigir o problema de mais de mil rotas no "in" do select.
	 * Autor: Josenildo Neves
	 * Date: 31/07/2013.
	 * 
	 * @param anoMesFaturamento
	 * @param idFaturamentoGrupo
	 * @param idEmpresa
	 * @param idsRotas
	 * @return
	 * @throws ErroRepositorioException
	 */
	private Integer pesquisarMaiorConsumoGrandeUsuarioComLimiteRotas(Integer anoMesFaturamento, Integer idFaturamentoGrupo,
					Integer idEmpresa, String idsRotas) throws ErroRepositorioException{

		Integer retorno = null;
		List<Integer> listaConsumo = new ArrayList<Integer>();

		for(String idsRotasComLimite : Util.retornaListaStringComLimiteItemConsulta(idsRotas)){
			listaConsumo.add(this.repositorioMicromedicao.pesquisarMaiorConsumoGrandeUsuario(idsRotasComLimite, anoMesFaturamento,
							idFaturamentoGrupo, idEmpresa));
		}

		// Verifica se é o maior consumo
		if(Util.isNaoNuloBrancoZero(listaConsumo)){
			retorno = listaConsumo.get(ConstantesSistema.ZERO.intValue());
			listaConsumo.remove(ConstantesSistema.ZERO.intValue());

			for(Integer consumo : listaConsumo){
				if(!Util.compararInicialEFinal(retorno.toString(), consumo.toString(), ">")){
					retorno = consumo;
				}
			}
		}

		return retorno;
	}

	/**
	 * [UC0948] Informar Dados de Leituras e Anormalidades
	 * [SB0103] – Converter Arquivo Leitura Formato 4
	 * 
	 * @author Hebert Falcão
	 * @date 22/07/2013
	 */
	public File execParamConverterArquivoLeituraFormato4(ParametroSistema parametroSistema, File arquivoLeitura,
					FaturamentoGrupo faturamentoGrupo) throws FileNotFoundException, ControladorException, IOException, ParseException{

		String nomeArquivo = arquivoLeitura.getName();

		this.validarNomeArquivoFormato4(nomeArquivo);

		Collection<String> linhas = new ArrayList<String>();

		FileReader fileReader = new FileReader(arquivoLeitura);
		BufferedReader br = new BufferedReader(fileReader);

		this.validarTrailerFormato4(linhas, br);

		File arquivoDefinitivo = new File(arquivoLeitura.getName() + "-DEFINITIVO.txt");
		FileOutputStream fout = new FileOutputStream(arquivoDefinitivo);
		PrintWriter pw = new PrintWriter(fout);

		this.gerarHeaderArquivoDefinitivoFormato4(linhas, pw);
		this.gerarRegistrosArquivoDefinitivoFormato4(linhas, pw);
		this.gerarTraillerArquivoDefinitivoFormato4(linhas, pw);

		pw.flush();
		pw.close();

		return arquivoDefinitivo;
	}

	/**
	 * [UC0948] Informar Dados de Leituras e Anormalidades
	 * 
	 * @author Hebert Falcão
	 * @date 22/07/2013
	 */
	private void validarNomeArquivoFormato4(String nomeArquivo) throws ControladorException{

		if(!"RET".equalsIgnoreCase(recuperarValorLinha(nomeArquivo, 0, 3))){
			throw new ControladorException("atencao.nome_arquivo_conversao_invalido", null, nomeArquivo);
		}

		String idFaturamentoGrupo = recuperarValorLinha(nomeArquivo, 3, 3);
		FaturamentoGrupo faturamentoGrupoNomeArquivo = (FaturamentoGrupo) this.getControladorUtil().pesquisar(
						Util.obterInteger(idFaturamentoGrupo), FaturamentoGrupo.class, false);

		if(faturamentoGrupoNomeArquivo == null){
			throw new ControladorException("atencao.nome_arquivo_conversao_invalido", null, nomeArquivo);
		}

		String referencia = recuperarValorLinha(nomeArquivo, 6, 6);

		if(Util.validarMesAno(referencia)){
			throw new ControladorException("atencao.nome_arquivo_conversao_invalido", null, nomeArquivo);
		}
	}

	/**
	 * [UC0948] Informar Dados de Leituras e Anormalidades
	 * [SB0103] – Converter Arquivo Leitura Formato 4
	 * 
	 * @author Hebert Falcão
	 * @date 22/07/2013
	 */
	private void validarTrailerFormato4(Collection<String> linhas, BufferedReader br) throws IOException, ControladorException{

		String[] campos = null;
		String linha = null;

		while((linha = br.readLine()) != null){
			// Removendo o caracter especiais (")
			linha = linha.replaceAll("[\"]", "");

			// O lay-out do arquivo é um texto delimitado por vírgula
			campos = linha.split(",");

			// Valida a quantidade de campos delimitados por vírgula
			if(Util.isVazioOrNulo(campos) || campos.length != 46){
				throw new ControladorException("atencao.arquivo_retorno_numero_campos_inconsistente");
			}

			linhas.add(linha);
		}
	}

	/**
	 * [UC0948] Informar Dados de Leituras e Anormalidades
	 * [SB0103] – Converter Arquivo Leitura Formato 4
	 * 
	 * @author Hebert Falcão
	 * @date 22/07/2013
	 */
	private void gerarHeaderArquivoDefinitivoFormato4(Collection<String> linhas, PrintWriter pw) throws ControladorException{

		String[] campos = null;

		StringBuilder headerArquivoDefinitivo = new StringBuilder();

		for(String linha : linhas){
			// O lay-out do arquivo é um texto delimitado por vírgula
			campos = linha.split(",");

			// Retorna a primeira linha
			break;
		}

		// Tipo do Registro
		headerArquivoDefinitivo.append("0");

		// Empresa
		headerArquivoDefinitivo.append(Util.completaStringComEspacoAEsquerda("", 6));

		// Indicador de transmissão ou recepção
		headerArquivoDefinitivo.append("R");

		// Grupo de Faturamento - 1.01
		String idGrupoFaturamentoStr = campos[0];

		if(Util.isInteger(idGrupoFaturamentoStr)){
			Integer idGrupoFaturamento = Util.obterInteger(idGrupoFaturamentoStr);

			idGrupoFaturamentoStr = String.valueOf(idGrupoFaturamento);
			idGrupoFaturamentoStr = Util.completarStringZeroEsquerda(idGrupoFaturamentoStr, 3);

			headerArquivoDefinitivo.append(idGrupoFaturamentoStr);
		}else{
			throw new ControladorException("atencao.arquivo_linha_invalido");
		}

		// Ano e Mês de referência do faturamento - 1.02 concatenado ao 1.03
		String anoStr = campos[1];

		if(Util.isInteger(anoStr)){
			Integer ano = Util.obterInteger(anoStr);

			anoStr = String.valueOf(ano);
			anoStr = Util.completarStringZeroEsquerda(anoStr, 4);
		}else{
			throw new ControladorException("atencao.arquivo_linha_invalido");
		}

		String mesStr = campos[2];

		if(Util.isInteger(mesStr)){
			Integer mes = Util.obterInteger(mesStr);

			mesStr = String.valueOf(mes);
			mesStr = Util.completarStringZeroEsquerda(mesStr, 2);
		}else{
			throw new ControladorException("atencao.arquivo_linha_invalido");
		}

		String anoMesReferencia = anoStr + mesStr;
		anoMesReferencia = Util.completarStringZeroEsquerda(anoMesReferencia, 6);

		headerArquivoDefinitivo.append(anoMesReferencia);

		// Data de Geração do Arquivo
		String dataGeracaoArquivo = this.recuperarDataCorrente();
		dataGeracaoArquivo = Util.completarStringZeroEsquerda(dataGeracaoArquivo, 8);

		headerArquivoDefinitivo.append(dataGeracaoArquivo);

		// Quantidade de Registros
		int quantidadeRegistros = linhas.size();

		String quantidadeRegistrosStr = String.valueOf(quantidadeRegistros);
		quantidadeRegistrosStr = Util.completarStringZeroEsquerda(quantidadeRegistrosStr, 6);

		headerArquivoDefinitivo.append(quantidadeRegistrosStr);

		pw.println(headerArquivoDefinitivo.toString());
	}

	/**
	 * [UC0948] Informar Dados de Leituras e Anormalidades
	 * [SB0103] – Converter Arquivo Leitura Formato 4
	 * 
	 * @author Hebert Falcão
	 * @date 22/07/2013
	 */
	private void gerarRegistrosArquivoDefinitivoFormato4(Collection<String> linhas, PrintWriter pw) throws ControladorException{

		String[] campos = null;

		StringBuilder registro = null;

		Integer i = 1;

		String matriculaFuncionarioStr = null;
		Integer matriculaFuncionario = null;

		String dataLeituraMesStr = null;
		Date dataLeituraMes = null;

		String matriculaImovelStr = null;
		Integer matriculaImovel = null;

		String codigoLocalidadeStr = null;
		Integer codigoLocalidade = null;

		String codigoSetorComercialStr = null;
		Integer codigoSetorComercial = null;

		String numeroQuadraStr = null;
		Integer numeroQuadra = null;

		String numeroLoteStr = null;
		Integer numeroLote = null;

		FiltroMedicaoTipo filtroMedicaoTipo = new FiltroMedicaoTipo();
		filtroMedicaoTipo.adicionarParametro(new ParametroSimples(FiltroMedicaoTipo.DESCRICAO, MedicaoTipo.DESC_LIGACAO_AGUA));

		Collection<MedicaoTipo> colecaoMedicaoTipo = this.getControladorUtil().pesquisar(filtroMedicaoTipo, MedicaoTipo.class.getName());

		MedicaoTipo medicaoTipo = (MedicaoTipo) Util.retonarObjetoDeColecao(colecaoMedicaoTipo);

		Integer idMedicaoTipo = medicaoTipo.getId();
		String idMedicaoTipoStr = String.valueOf(idMedicaoTipo);

		String leituraAtualRealStr = null;
		Integer leituraAtualReal = null;

		String leituraFaturadaStr = null;
		Integer leituraFaturada = null;

		String consumoFaturadoStr = null;
		Integer consumoFaturado = null;

		String codigoAnormalidadeLeituraStr = null;
		Integer codigoAnormalidadeLeitura = null;

		String codigoAnormalidadeConsumoStr = null;

		String tipoConsumoStr = null;
		Integer tipoConsumo = null;
		Map<String, Integer> mapaTipoConsumo = this.montarMapaTipoConsumoValidosFormato4();

		String diasConsumoStr = null;
		Integer diasConsumo = null;

		String valorAguaStr = null;

		String valorEsgotoStr = null;

		String valorDebitosStr = null;

		String valorCreditosStr = null;

		String indicadorConfirmacaoLeituraStr = null;
		Integer indicadorConfirmacaoLeituraInt = null;

		String consumoMedidoStr = null;
		Integer consumoMedido = null;

		String indicadorReligacaoAguaStr = null;
		Integer indicadorReligacaoAgua = null;

		String codigoServicoReligacaoStr = null;
		Integer codigoServicoReligacao = null;

		String valorReligacaoStr = null;

		String codigoServicoSancaoStr = null;
		Integer codigoServicoSancao = null;

		String valorSancaoStr = null;

		String alteracaoNumeroImovelStr = null;

		String indicadorSuspeitaLigacaoSemHidrStr = null;

		String indicadorChecarNumHidrStr = null;

		String indicadorChecarCategEconomStr = null;

		String indicadorChecarLogradouroStr = null;

		String indicadorRevisarQuadraStr = null;

		String indicadorFiscalizarConsumoStr = null;

		String indicadorNaoMedidoComHidrStr = null;

		String statusRegistroStr = null;
		Integer statusRegistro = null;

		String indicadorFaturaRetidaStr = null;
		Integer indicadorFaturaRetida = null;

		String indicadorEmissaoStr = null;
		Integer indicadorEmissao = null;
		Map<String, Integer> mapaIndicadorEmissao = this.montarMapaIndicadorEmissaoValidosFormato4();

		for(String linha : linhas){
			// O lay-out do arquivo é um texto delimitado por vírgula
			campos = linha.split(",");

			registro = new StringBuilder();

			// Tipo do Registro
			registro.append("1");

			// Identificação do Agente Comercial - 1.41
			matriculaFuncionarioStr = campos[40];

			if(Util.isInteger(matriculaFuncionarioStr)){
				matriculaFuncionario = Integer.valueOf(matriculaFuncionarioStr);

				matriculaFuncionarioStr = String.valueOf(matriculaFuncionario);
				matriculaFuncionarioStr = Util.completarStringZeroEsquerda(matriculaFuncionarioStr, 6);

				registro.append(matriculaFuncionarioStr);
			}else{
				LOGGER.error("Linha: " + i);
				throw new ControladorException("atencao.arquivo_linha_invalido", null, i.toString(), linha);
			}

			// Data de Leitura - 1.19
			dataLeituraMesStr = campos[18];

			if(!Util.isVazioOuBranco(dataLeituraMesStr)){
				dataLeituraMesStr = dataLeituraMesStr.substring(0, 10);
				dataLeituraMes = Util.converteStringParaDate(dataLeituraMesStr, "yyyy-MM-dd");
				dataLeituraMesStr = Util.formatarDataSemBarraDDMMAAAA(dataLeituraMes);
			}

			if(Util.isInteger(dataLeituraMesStr)){
				dataLeituraMesStr = Util.completarStringZeroEsquerda(dataLeituraMesStr, 8);

				registro.append(dataLeituraMesStr);
			}else{
				LOGGER.error("Linha: " + i);
				throw new ControladorException("atencao.arquivo_linha_invalido", null, i.toString(), linha);
			}

			// Matrícula do Imóvel - 1.09
			matriculaImovelStr = campos[8];

			if(Util.isInteger(matriculaImovelStr)){
				matriculaImovel = Integer.valueOf(matriculaImovelStr);

				matriculaImovelStr = String.valueOf(matriculaImovel);
				matriculaImovelStr = Util.completarStringZeroEsquerda(matriculaImovelStr, 8);

				registro.append(matriculaImovelStr);
			}else{
				LOGGER.error("Linha: " + i);
				throw new ControladorException("atencao.arquivo_linha_invalido", null, i.toString(), linha);
			}

			// Código da Localidade - 1.04
			codigoLocalidadeStr = campos[3];

			if(Util.isInteger(codigoLocalidadeStr)){
				codigoLocalidade = Integer.valueOf(codigoLocalidadeStr);

				codigoLocalidadeStr = String.valueOf(codigoLocalidade);
				codigoLocalidadeStr = Util.completarStringZeroEsquerda(codigoLocalidadeStr, 3);

				registro.append(codigoLocalidadeStr);
			}else{
				LOGGER.error("Linha: " + i);
				throw new ControladorException("atencao.arquivo_linha_invalido", null, i.toString(), linha);
			}

			// Código do Setor Comercial - 1.05
			codigoSetorComercialStr = campos[4];

			if(Util.isInteger(codigoSetorComercialStr)){
				codigoSetorComercial = Integer.valueOf(codigoSetorComercialStr);

				codigoSetorComercialStr = String.valueOf(codigoSetorComercial);
				codigoSetorComercialStr = Util.completarStringZeroEsquerda(codigoSetorComercialStr, 3);

				registro.append(codigoSetorComercialStr);
			}else{
				LOGGER.error("Linha: " + i);
				throw new ControladorException("atencao.arquivo_linha_invalido", null, i.toString(), linha);
			}

			// Número da Quadra - 1.06
			numeroQuadraStr = campos[5];

			if(Util.isInteger(numeroQuadraStr)){
				numeroQuadra = Integer.valueOf(numeroQuadraStr);

				numeroQuadraStr = String.valueOf(numeroQuadra);
				numeroQuadraStr = Util.completarStringZeroEsquerda(numeroQuadraStr, 4);

				registro.append(numeroQuadraStr);
			}else{
				LOGGER.error("Linha: " + i);
				throw new ControladorException("atencao.arquivo_linha_invalido", null, i.toString(), linha);
			}

			// Número do Lote - 1.07
			numeroLoteStr = campos[6];

			if(Util.isInteger(numeroLoteStr)){
				numeroLote = Integer.valueOf(numeroLoteStr);

				numeroLoteStr = String.valueOf(numeroLote);
				numeroLoteStr = Util.completarStringZeroEsquerda(numeroLoteStr, 4);

				registro.append(numeroLoteStr);
			}else{
				LOGGER.error("Linha: " + i);
				throw new ControladorException("atencao.arquivo_linha_invalido", null, i.toString(), linha);
			}

			// Número do Sublote
			registro.append(Util.completarStringZeroEsquerda("0", 3));

			// Tipo de Medição
			registro.append(idMedicaoTipoStr);

			// Leitura do Hidrômetro - 1.10
			leituraAtualRealStr = campos[9];

			if(Util.isInteger(leituraAtualRealStr)){
				leituraAtualReal = Integer.valueOf(leituraAtualRealStr);

				leituraAtualRealStr = String.valueOf(leituraAtualReal);
			}else{
				leituraAtualRealStr = "0";
			}

			leituraAtualRealStr = Util.completarStringZeroEsquerda(leituraAtualRealStr, 6);

			registro.append(leituraAtualRealStr);

			// Leitura Faturada - 1.11
			leituraFaturadaStr = campos[10];

			if(Util.isInteger(leituraFaturadaStr)){
				leituraFaturada = Integer.valueOf(leituraFaturadaStr);

				leituraFaturadaStr = String.valueOf(leituraFaturada);
				leituraFaturadaStr = Util.completarStringZeroEsquerda(leituraFaturadaStr, 6);

				registro.append(leituraFaturadaStr);
			}else{
				LOGGER.error("Linha: " + i);
				throw new ControladorException("atencao.arquivo_linha_invalido", null, i.toString(), linha);
			}

			// Consumo Faturado - 1.13
			consumoFaturadoStr = campos[12];

			if(Util.isInteger(consumoFaturadoStr)){
				consumoFaturado = Integer.valueOf(consumoFaturadoStr);

				consumoFaturadoStr = String.valueOf(consumoFaturado);
				consumoFaturadoStr = Util.completarStringZeroEsquerda(consumoFaturadoStr, 6);

				registro.append(consumoFaturadoStr);
			}else{
				LOGGER.error("Linha: " + i);
				throw new ControladorException("atencao.arquivo_linha_invalido", null, i.toString(), linha);
			}

			// Código da Anormalidade de Leitura - 1.15
			codigoAnormalidadeLeituraStr = campos[14];

			if(Util.isInteger(codigoAnormalidadeLeituraStr)){
				codigoAnormalidadeLeitura = Integer.valueOf(codigoAnormalidadeLeituraStr);

				codigoAnormalidadeLeituraStr = String.valueOf(codigoAnormalidadeLeitura);
				codigoAnormalidadeLeituraStr = Util.completarStringZeroEsquerda(codigoAnormalidadeLeituraStr, 3);

				registro.append(codigoAnormalidadeLeituraStr);
			}else{
				LOGGER.error("Linha: " + i);
				throw new ControladorException("atencao.arquivo_linha_invalido", null, i.toString(), linha);
			}

			// Código da Anormalidade de Consumo - 1.16
			// [SB0103C – Converter Anormalidade Consumo GCS para Anormalidade de Consumo GSAN]
			codigoAnormalidadeConsumoStr = campos[15];

			codigoAnormalidadeConsumoStr = this.converterAnormalidadeConsumoParaGsan(codigoAnormalidadeConsumoStr);
			codigoAnormalidadeConsumoStr = Util.completarStringZeroEsquerda(codigoAnormalidadeConsumoStr, 3);

			registro.append(codigoAnormalidadeConsumoStr);

			// Tipo do Consumo - 1.31
			// [SB0103B – Converter Tipo Consumo para Tipo Consumo GSAN]
			tipoConsumoStr = campos[30];

			if(mapaTipoConsumo.containsKey(tipoConsumoStr)){
				tipoConsumo = mapaTipoConsumo.get(tipoConsumoStr);

				tipoConsumoStr = String.valueOf(tipoConsumo);
				tipoConsumoStr = Util.completarStringZeroEsquerda(tipoConsumoStr, 2);

				registro.append(tipoConsumoStr);
			}else{
				LOGGER.error("Linha: " + i);
				throw new ControladorException("atencao.arquivo_linha_tipo_consumo_invalido", null, i.toString());
			}

			// Dias de Consumo - 1.18
			diasConsumoStr = campos[17];

			if(Util.isInteger(diasConsumoStr)){
				diasConsumo = Integer.valueOf(diasConsumoStr);

				diasConsumoStr = String.valueOf(diasConsumo);
				diasConsumoStr = Util.completarStringZeroEsquerda(diasConsumoStr, 2);

				registro.append(diasConsumoStr);
			}else{
				LOGGER.error("Linha: " + i);
				throw new ControladorException("atencao.arquivo_linha_invalido", null, i.toString(), linha);
			}

			// Valor Água - 1.20
			valorAguaStr = campos[19];
			valorAguaStr = valorAguaStr.replaceAll("[\\+\\.]", "");

			if(Util.isNumero(valorAguaStr, false, 0)){
				valorAguaStr = Util.completarStringZeroEsquerda(valorAguaStr, 11);

				registro.append(valorAguaStr);
			}else{
				LOGGER.error("Linha: " + i);
				throw new ControladorException("atencao.arquivo_linha_invalido", null, i.toString(), linha);
			}

			// Valor Esgoto - 1.21
			valorEsgotoStr = campos[20];
			valorEsgotoStr = valorEsgotoStr.replaceAll("[\\+\\.]", "");

			if(Util.isNumero(valorEsgotoStr, false, 0)){
				valorEsgotoStr = Util.completarStringZeroEsquerda(valorEsgotoStr, 11);

				registro.append(valorEsgotoStr);
			}else{
				LOGGER.error("Linha: " + i);
				throw new ControladorException("atencao.arquivo_linha_invalido", null, i.toString(), linha);
			}

			// Valor Débitos - 1.22
			valorDebitosStr = campos[21];
			valorDebitosStr = valorDebitosStr.replaceAll("[\\+\\.]", "");

			if(Util.isNumero(valorDebitosStr, false, 0)){
				valorDebitosStr = Util.completarStringZeroEsquerda(valorDebitosStr, 11);

				registro.append(valorDebitosStr);
			}else{
				LOGGER.error("Linha: " + i);
				throw new ControladorException("atencao.arquivo_linha_invalido", null, i.toString(), linha);
			}

			// Valor Créditos - 1.23
			valorCreditosStr = campos[22];
			valorCreditosStr = valorCreditosStr.replaceAll("[\\+\\.]", "");

			if(Util.isNumero(valorCreditosStr, false, 0)){
				valorCreditosStr = Util.completarStringZeroEsquerda(valorCreditosStr, 11);

				registro.append(valorCreditosStr);
			}else{
				LOGGER.error("Linha: " + i);
				throw new ControladorException("atencao.arquivo_linha_invalido", null, i.toString(), linha);
			}

			// Indicador de Grande Consumidor
			registro.append(Util.completarStringZeroEsquerda("0", 1));

			// Indicador de Emissão em Campo - 1.17
			// [SB0103D – Converter Indicador de Emissão para o GSAN]
			indicadorEmissaoStr = campos[16];

			if(mapaIndicadorEmissao.containsKey(indicadorEmissaoStr)){
				indicadorEmissao = mapaIndicadorEmissao.get(indicadorEmissaoStr);

				indicadorEmissaoStr = String.valueOf(indicadorEmissao);
				indicadorEmissaoStr = Util.completarStringZeroEsquerda(indicadorEmissaoStr, 1);

				registro.append(indicadorEmissaoStr);
			}else{
				LOGGER.error("Linha: " + i);
				throw new ControladorException("atencao.arquivo_linha_invalido", null, i.toString(), linha);
			}

			// Indicador de Confirmação de Leitura - 1.32
			indicadorConfirmacaoLeituraStr = campos[31];

			if(Util.isInteger(indicadorConfirmacaoLeituraStr)){
				indicadorConfirmacaoLeituraInt = Integer.valueOf(indicadorConfirmacaoLeituraStr);

				indicadorConfirmacaoLeituraStr = String.valueOf(indicadorConfirmacaoLeituraInt);
			}else{
				// Caso o Indicador de Confirmação de Leitura não seja um campo numérico, atribuir o
				// valor zero ao Indicador de Confirmação de Leitura
				indicadorConfirmacaoLeituraStr = "0";
			}

			indicadorConfirmacaoLeituraStr = Util.completarStringZeroEsquerda(indicadorConfirmacaoLeituraStr, 1);

			registro.append(indicadorConfirmacaoLeituraStr);

			// Valor Imposto Federal
			registro.append(Util.completarStringZeroEsquerda("0", 11));

			// Data Vencimento Retornada
			registro.append(Util.completarStringZeroEsquerda("0", 8));

			// Data de Leitura - 1.19
			dataLeituraMesStr = campos[18];

			if(!Util.isVazioOuBranco(dataLeituraMesStr)){
				dataLeituraMesStr = dataLeituraMesStr.substring(0, 10);
				dataLeituraMes = Util.converteStringParaDate(dataLeituraMesStr, "yyyy-MM-dd");
				dataLeituraMesStr = Util.formatarDataSemBarraDDMMAAAA(dataLeituraMes);
			}

			if(Util.isInteger(dataLeituraMesStr)){
				dataLeituraMesStr = Util.completarStringZeroEsquerda(dataLeituraMesStr, 8);

				registro.append(dataLeituraMesStr);
			}else{
				LOGGER.error("Linha: " + i);
				throw new ControladorException("atencao.arquivo_linha_invalido", null, i.toString(), linha);
			}

			// Valor do Rateio
			registro.append(Util.completarStringZeroEsquerda("0", 11));

			// Indicador Modo Faturamento do Rateio
			registro.append(Util.completarStringZeroEsquerda("0", 1));

			// Consumo Medido - 1.12
			consumoMedidoStr = campos[11];

			if(Util.isInteger(consumoMedidoStr)){
				consumoMedido = Integer.valueOf(consumoMedidoStr);

				consumoMedidoStr = String.valueOf(consumoMedido);
				consumoMedidoStr = Util.completarStringZeroEsquerda(consumoMedidoStr, 6);

				registro.append(consumoMedidoStr);
			}else{
				LOGGER.error("Linha: " + i);
				throw new ControladorException("atencao.arquivo_linha_invalido", null, i.toString(), linha);
			}

			// Indicador de Religação Agua - 1.26
			indicadorReligacaoAguaStr = campos[25];

			if(Util.isInteger(indicadorReligacaoAguaStr)){
				indicadorReligacaoAgua = Integer.valueOf(indicadorReligacaoAguaStr);

				indicadorReligacaoAguaStr = String.valueOf(indicadorReligacaoAgua);
				indicadorReligacaoAguaStr = Util.completarStringZeroEsquerda(indicadorReligacaoAguaStr, 1);

				registro.append(indicadorReligacaoAguaStr);
			}else{
				LOGGER.error("Linha: " + i);
				throw new ControladorException("atencao.arquivo_linha_invalido", null, i.toString(), linha);
			}

			// Cod. Serviço Religação - 1.27
			codigoServicoReligacaoStr = campos[26];

			if(Util.isInteger(codigoServicoReligacaoStr)){
				codigoServicoReligacao = Integer.valueOf(codigoServicoReligacaoStr);

				codigoServicoReligacaoStr = String.valueOf(codigoServicoReligacao);
				codigoServicoReligacaoStr = Util.completarStringZeroEsquerda(codigoServicoReligacaoStr, 3);

				registro.append(codigoServicoReligacaoStr);
			}else{
				LOGGER.error("Linha: " + i);
				throw new ControladorException("atencao.arquivo_linha_invalido", null, i.toString(), linha);
			}

			// Valor Religação - 1.28
			valorReligacaoStr = campos[27];
			valorReligacaoStr = valorReligacaoStr.replaceAll("[\\+\\.]", "");

			if(Util.isNumero(valorReligacaoStr, false, 0)){
				valorReligacaoStr = Util.completarStringZeroEsquerda(valorReligacaoStr, 11);

				registro.append(valorReligacaoStr);
			}else{
				LOGGER.error("Linha: " + i);
				throw new ControladorException("atencao.arquivo_linha_invalido", null, i.toString(), linha);
			}

			// Cod. Serviço Sanção - 1.29
			codigoServicoSancaoStr = campos[28];

			if(Util.isInteger(codigoServicoSancaoStr)){
				codigoServicoSancao = Integer.valueOf(codigoServicoSancaoStr);

				codigoServicoSancaoStr = String.valueOf(codigoServicoSancao);
				codigoServicoSancaoStr = Util.completarStringZeroEsquerda(codigoServicoSancaoStr, 3);

				registro.append(codigoServicoSancaoStr);
			}else{
				LOGGER.error("Linha: " + i);
				throw new ControladorException("atencao.arquivo_linha_invalido", null, i.toString(), linha);
			}

			// Valor Sanção - 1.30
			valorSancaoStr = campos[29];
			valorSancaoStr = valorSancaoStr.replaceAll("[\\+\\.]", "");

			if(Util.isNumero(valorSancaoStr, false, 0)){
				valorSancaoStr = Util.completarStringZeroEsquerda(valorSancaoStr, 11);

				registro.append(valorSancaoStr);
			}else{
				LOGGER.error("Linha: " + i);
				throw new ControladorException("atencao.arquivo_linha_invalido", null, i.toString(), linha);
			}

			// Alteração Numero Imóvel - 1.33
			alteracaoNumeroImovelStr = campos[32];

			alteracaoNumeroImovelStr = Util.completaString(alteracaoNumeroImovelStr, 5);

			registro.append(alteracaoNumeroImovelStr);

			// Ind. Suspeita Ligação sem Hidr - 1.34
			indicadorSuspeitaLigacaoSemHidrStr = campos[33];

			indicadorSuspeitaLigacaoSemHidrStr = Util.completaString(indicadorSuspeitaLigacaoSemHidrStr, 1);

			registro.append(indicadorSuspeitaLigacaoSemHidrStr);

			// Ind. Checar Num. Hidr - 1.35
			indicadorChecarNumHidrStr = campos[34];

			indicadorChecarNumHidrStr = Util.completaString(indicadorChecarNumHidrStr, 1);

			registro.append(indicadorChecarNumHidrStr);

			// Ind. Checar Categ./Econom. - 1.36
			indicadorChecarCategEconomStr = campos[35];

			indicadorChecarCategEconomStr = Util.completaString(indicadorChecarCategEconomStr, 1);

			registro.append(indicadorChecarCategEconomStr);

			// Ind. Checar Logradouro - 1.37
			indicadorChecarLogradouroStr = campos[36];

			indicadorChecarLogradouroStr = Util.completaString(indicadorChecarLogradouroStr, 1);

			registro.append(indicadorChecarLogradouroStr);

			// Ind. Revisar Quadra - 1.38
			indicadorRevisarQuadraStr = campos[37];

			indicadorRevisarQuadraStr = Util.completaString(indicadorRevisarQuadraStr, 1);

			registro.append(indicadorRevisarQuadraStr);

			// Ind. Fiscalizar Consumo - 1.39
			indicadorFiscalizarConsumoStr = campos[38];

			indicadorFiscalizarConsumoStr = Util.completaString(indicadorFiscalizarConsumoStr, 1);

			registro.append(indicadorFiscalizarConsumoStr);

			// Ind. Nao Medido com Hidr - 1.40
			indicadorNaoMedidoComHidrStr = campos[39];

			indicadorNaoMedidoComHidrStr = Util.completaString(indicadorNaoMedidoComHidrStr, 1);

			registro.append(indicadorNaoMedidoComHidrStr);

			// Status Registro - 1.42
			statusRegistroStr = campos[41];

			if(Util.isInteger(statusRegistroStr)){
				statusRegistro = Integer.valueOf(statusRegistroStr);

				statusRegistroStr = String.valueOf(statusRegistro);
				statusRegistroStr = Util.completarStringZeroEsquerda(statusRegistroStr, 1);

				registro.append(statusRegistroStr);
			}else{
				LOGGER.error("Linha: " + i);
				throw new ControladorException("atencao.arquivo_linha_invalido", null, i.toString(), linha);
			}

			// Indicador Fatura Retida - 1.43
			indicadorFaturaRetidaStr = campos[42];

			if(Util.isVazioOuBranco(indicadorFaturaRetidaStr)){
				indicadorFaturaRetidaStr = "2";
			}else{
				if(Util.isInteger(indicadorFaturaRetidaStr)){
					indicadorFaturaRetida = Integer.valueOf(indicadorFaturaRetidaStr);
					indicadorFaturaRetidaStr = String.valueOf(indicadorFaturaRetida);
				}else{
					LOGGER.error("Linha: " + i);
					throw new ControladorException("atencao.arquivo_linha_invalido", null, i.toString(), linha);
				}
			}

			indicadorFaturaRetidaStr = Util.completarStringZeroEsquerda(indicadorFaturaRetidaStr, 1);

			registro.append(indicadorFaturaRetidaStr);

			i++;

			pw.println(registro.toString());
		}
	}

	/**
	 * [UC0948] Informar Dados de Leituras e Anormalidades
	 * [SB0103B] – Converter Tipo Consumo para Tipo Consumo GSAN
	 * 
	 * @author Hebert Falcão
	 * @date 22/07/2013
	 */
	private Map<String, Integer> montarMapaTipoConsumoValidosFormato4(){

		Map<String, Integer> mapaTipoConsumo = new HashMap<String, Integer>();

		mapaTipoConsumo.put(" ", 0);
		mapaTipoConsumo.put("A", 1);
		mapaTipoConsumo.put("E", 2);
		mapaTipoConsumo.put("I", 3);
		mapaTipoConsumo.put("M", 4);
		mapaTipoConsumo.put("N", 5);
		mapaTipoConsumo.put("0", 6);
		mapaTipoConsumo.put("R", 6);

		return mapaTipoConsumo;
	}

	/**
	 * [UC0948] Informar Dados de Leituras e Anormalidades
	 * [SB0103D] – Converter Indicador de Emissão para o GSAN
	 * 
	 * @author Hebert Falcão
	 * @date 22/07/2013
	 */
	private Map<String, Integer> montarMapaIndicadorEmissaoValidosFormato4(){

		Map<String, Integer> mapaIndicadorEmissao = new HashMap<String, Integer>();

		mapaIndicadorEmissao.put("1", 1); // 1= EMITIDA
		mapaIndicadorEmissao.put("0", 2); // (Sem leitura(Status Registro = 5) e outros)
		mapaIndicadorEmissao.put("2", 2); // 2= RETIDA P/ ANALISE
		mapaIndicadorEmissao.put("3", 2); // 3= NAO EMITIDA
		mapaIndicadorEmissao.put("4", 2); // 4 = PUBLICO NAO EMITIDA

		return mapaIndicadorEmissao;
	}

	/**
	 * [UC0948] Informar Dados de Leituras e Anormalidades
	 * [SB0103] – Converter Arquivo Leitura Formato 4
	 * 
	 * @author Hebert Falcão
	 * @date 22/07/2013
	 */
	private void gerarTraillerArquivoDefinitivoFormato4(Collection<String> linhas, PrintWriter pw){

		StringBuilder traillerArquivoDefinitivo = new StringBuilder();

		// Tipo do Registro
		traillerArquivoDefinitivo.append("9");

		// Quantidade de Registros
		traillerArquivoDefinitivo.append(Util.completarStringZeroEsquerda(String.valueOf(linhas.size() + 2), 6));

		// Quantidade de Registros Tipo Detalhe
		traillerArquivoDefinitivo.append(Util.completarStringZeroEsquerda(String.valueOf(linhas.size()), 6));

		pw.println(traillerArquivoDefinitivo.toString());
	}


	/**
	 * [UC0101] - Consistir Leituras e Calcular Consumos
	 * [SF0018] - Ajuste do Consumo para Múltiplo da Quantidade de Economias
	 * Correção no Ajuste Consumo Multiplo Quantidade Economias - vsm - 20/12/2008
	 * 
	 * @author eduardo henrique
	 * @date 30/01/2009
	 *       Correção no método para atribuir o 'ConsumoMinimoCreditado' (credito efetivamente
	 *       utilizado) no Consumo Historico.
	 */
	public void execParamAjustarConsumoMultiploQuantidadeEconomiasRegra1(ParametroSistema parametroSistema, Imovel imovel,
					MedicaoHistorico medicaoHistorico, ConsumoHistorico consumoHistorico, int quantidadeEconomias, int consumoMinimoLigacao)
					throws ControladorException{

		int consumoFaturado = consumoHistorico.getNumeroConsumoFaturadoMes().intValue();
		int resto = 0;

		/*
		 * Caso o consumo a ser cobrado no mês seja maior que o mínimo da ligação, obtém o resto de
		 * (Consumo a Ser Cobrado no Mês – mínimo da ligação) / Quantidade de economias
		 */
		if(consumoFaturado >= consumoMinimoLigacao){

			resto = (consumoFaturado - consumoMinimoLigacao) % quantidadeEconomias;

		}else{

			/*
			 * Caso contrário, obtém o resto da divisão entre Consumo a Ser Cobrado no Mês e a
			 * Quantidade de economias
			 */
			resto = consumoFaturado % quantidadeEconomias;
		}

		/*
		 * Parâmetro para indicar qual ação deve ser tomada quando o violume é ajustado pela
		 * quantidade
		 * de economias.Se o consumo restante obtido pelo ajuste do consumo pelo número de economias
		 * deve ser gerado como um crédito de consumo para a empresa, sendo cobrado do cliente no
		 * próximo ciclo de leitura/faturamento
		 */
		String parametroAcaoAjusteConsumoMultiplaQuantidadeEconomias = (String) ParametroMicromedicao.P_ACAO_AJUSTE_CONSUMO_MULTIPLO_QTDE_ECONOMIAS
						.executar(this, 0);

		if(medicaoHistorico == null
						|| parametroAcaoAjusteConsumoMultiplaQuantidadeEconomias.equals(AcaoAjusteConsumoMultiploQtdeEconomias.UM
										.getValor())){

			// Subtrair o resto do Consumo a Ser Cobrado no Mês
			consumoHistorico.setNumeroConsumoFaturadoMes(Integer.valueOf(consumoFaturado - resto));
		}else if(parametroAcaoAjusteConsumoMultiplaQuantidadeEconomias.equals(AcaoAjusteConsumoMultiploQtdeEconomias.DOIS.getValor())){

			// Subtrair o resto do Consumo a Ser Cobrado no Mês
			consumoHistorico.setNumeroConsumoFaturadoMes(Integer.valueOf(consumoFaturado - resto));

			// Atribuir o 'resto' ao 'Crédito Gerado' com valor positivo
			medicaoHistorico.setConsumoCreditoGerado(resto);
		}else if(parametroAcaoAjusteConsumoMultiplaQuantidadeEconomias.equals(AcaoAjusteConsumoMultiploQtdeEconomias.TRES.getValor())){

			// Subtrair o resto do Consumo a Ser Cobrado no Mês e da Leitura Atual de Faturamento
			consumoHistorico.setNumeroConsumoFaturadoMes(Integer.valueOf(consumoFaturado - resto));

			int leituraAtualFaturamento = 0;
			if(medicaoHistorico.getLeituraAtualFaturamento() != null){

				leituraAtualFaturamento = medicaoHistorico.getLeituraAtualFaturamento();
			}

			medicaoHistorico.setLeituraAtualFaturamento(leituraAtualFaturamento - resto);
		}

		if(medicaoHistorico != null){

			// Atualizando o crédito anterior
			Integer creditoAnterior = Integer.valueOf(0);
			if(medicaoHistorico.getConsumoCreditoAnterior() != null){
				creditoAnterior = medicaoHistorico.getConsumoCreditoAnterior();
			}
			medicaoHistorico.setConsumoCreditoAnterior(creditoAnterior.intValue() + resto);
		}
	}

	/**
	 * [UC0101] - Consistir Leituras e Calcular Consumos
	 * [SB0018] - Ajuste do Consumo para Múltiplo da Quantidade de Economias
	 * 
	 * @author Luciano Galvao
	 * @date 06/08/25013
	 */
	public void execParamAjustarConsumoMultiploQuantidadeEconomiasRegra2(ParametroSistema parametroSistema, Imovel imovel,
					MedicaoHistorico medicaoHistorico, ConsumoHistorico consumoHistorico, int quantidadeEconomias, int consumoMinimoLigacao)
					throws ControladorException{

		// ############# Alterado por Luciano - OC1034808 ############# - Inicio

		int consumoFaturado = consumoHistorico.getNumeroConsumoFaturadoMes().intValue();
		int resto = 0;
		Integer consumoPorEconomiaAjustado = 0;
		/*
		 * Parâmetro para indicar qual ação deve ser tomada quando o violume é ajustado pela
		 * quantidade
		 * de economias.Se o consumo restante obtido pelo ajuste do consumo pelo número de economias
		 * deve ser gerado como um crédito de consumo para a empresa, sendo cobrado do cliente no
		 * próximo ciclo de leitura/faturamento
		 */
		String parametroAcaoAjusteConsumoMultiplaQuantidadeEconomias = (String) ParametroMicromedicao.P_ACAO_AJUSTE_CONSUMO_MULTIPLO_QTDE_ECONOMIAS
						.executar(this, 0);

		// Se o valor do parâmetro P_ACAO_AJUSTE_CONSUMO_MULTIPLO_QTDE_ECONOMIAS for igual a 1 ou 2
		// ou 3
		if(parametroAcaoAjusteConsumoMultiplaQuantidadeEconomias.equals(AcaoAjusteConsumoMultiploQtdeEconomias.UM.getValor())
						|| parametroAcaoAjusteConsumoMultiplaQuantidadeEconomias.equals(AcaoAjusteConsumoMultiploQtdeEconomias.DOIS
										.getValor())
						|| parametroAcaoAjusteConsumoMultiplaQuantidadeEconomias.equals(AcaoAjusteConsumoMultiploQtdeEconomias.TRES
										.getValor())){

			/*
			 * Caso o consumo a ser cobrado no mês seja maior que o mínimo da ligação, obtém o resto
			 * de
			 * (Consumo a Ser Cobrado no Mês – mínimo da ligação) / Quantidade de economias
			 */
			if(consumoFaturado >= consumoMinimoLigacao){

				resto = (consumoFaturado - consumoMinimoLigacao) % quantidadeEconomias;

			}else{

				/*
				 * Caso contrário, obtém o resto da divisão entre Consumo a Ser Cobrado no Mês e a
				 * Quantidade de economias
				 */
				resto = consumoFaturado % quantidadeEconomias;
			}

			if(medicaoHistorico != null){

				// Atualizando o crédito anterior
				Integer creditoAnterior = Integer.valueOf(0);
				if(medicaoHistorico.getConsumoCreditoAnterior() != null){
					creditoAnterior = medicaoHistorico.getConsumoCreditoAnterior();
				}
				medicaoHistorico.setConsumoCreditoAnterior(creditoAnterior.intValue() + resto);
			}

		}else if(parametroAcaoAjusteConsumoMultiplaQuantidadeEconomias.equals(AcaoAjusteConsumoMultiploQtdeEconomias.QUATRO.getValor())){
			// Se o valor de P_ACAO_AJUSTE_CONSUMO_MULTIPLO_QTDE_ECONOMIAS for igual a 4

			// Obtém o Consumo por economia ajustado (variável com uma casa decimal) com o resultado
			// de (Consumo a Ser Cobrado no Mês / Quantidade de economias) + 0,5
			consumoPorEconomiaAjustado = new BigDecimal(consumoFaturado).divide(new BigDecimal(quantidadeEconomias), 0,
							BigDecimal.ROUND_HALF_UP).intValue();
		}

		// De acordo com a ação configurada pelo parâmetro P_ACAO_AJUSTE
		// CONSUMO_MULTIPLO_QTDE_ECONOMIAS:

		// P_ACAO_AJUSTE CONSUMO_MULTIPLO_QTDE_ECONOMIAS = Apenas ajustar consumo (1)
		if(medicaoHistorico == null
						|| parametroAcaoAjusteConsumoMultiplaQuantidadeEconomias.equals(AcaoAjusteConsumoMultiploQtdeEconomias.UM
										.getValor())){

			// Subtrair o resto do Consumo a Ser Cobrado no Mês
			consumoHistorico.setNumeroConsumoFaturadoMes(Integer.valueOf(consumoFaturado - resto));

		}else if(parametroAcaoAjusteConsumoMultiplaQuantidadeEconomias.equals(AcaoAjusteConsumoMultiploQtdeEconomias.DOIS.getValor())){
			// P_ACAO_AJUSTE CONSUMO_MULTIPLO_QTDE_ECONOMIAS = Gerar crédito de volume (2)

			// Subtrair o resto do Consumo a Ser Cobrado no Mês
			consumoHistorico.setNumeroConsumoFaturadoMes(Integer.valueOf(consumoFaturado - resto));

			// Atribuir o 'resto' ao 'Crédito Gerado' com valor positivo
			medicaoHistorico.setConsumoCreditoGerado(resto);

		}else if(parametroAcaoAjusteConsumoMultiplaQuantidadeEconomias.equals(AcaoAjusteConsumoMultiploQtdeEconomias.TRES.getValor())){
			// P_ACAO_AJUSTE CONSUMO_MULTIPLO_QTDE_ECONOMIAS = Ajustar consumo e leitura atual (3)

			// Subtrair o resto do Consumo a Ser Cobrado no Mês e da Leitura Atual de Faturamento
			consumoHistorico.setNumeroConsumoFaturadoMes(Integer.valueOf(consumoFaturado - resto));

			int leituraAtualFaturamento = 0;
			if(medicaoHistorico.getLeituraAtualFaturamento() != null){

				leituraAtualFaturamento = medicaoHistorico.getLeituraAtualFaturamento();
			}

			medicaoHistorico.setLeituraAtualFaturamento(leituraAtualFaturamento - resto);

		}else if(parametroAcaoAjusteConsumoMultiplaQuantidadeEconomias.equals(AcaoAjusteConsumoMultiploQtdeEconomias.QUATRO.getValor())){
			// P_ACAO_AJUSTE CONSUMO_MULTIPLO_QTDE_ECONOMIAS = Ajustar consumo e leitura atual para
			// maior (4)

			// O Consumo a Ser Cobrado no Mês será igual a (Consumo por economia ajustado *
			// Quantidade de economias)
			consumoHistorico.setNumeroConsumoFaturadoMes(consumoPorEconomiaAjustado * quantidadeEconomias);

			// Obtém o ano e mês de referência anterior
			Integer anoMesReferenciaAtual = Integer.valueOf(consumoHistorico.getReferenciaFaturamento());
			Integer anoMesReferenciaAnterior = Util.subtrairData(anoMesReferenciaAtual);

			// Pesquisa a medição histórico do mês anterior
			FiltroMedicaoHistorico filtroMedicaoHistorico = new FiltroMedicaoHistorico();
			filtroMedicaoHistorico.adicionarParametro(new ParametroSimples(FiltroMedicaoHistorico.IMOVEL_ID, imovel.getId()));
			filtroMedicaoHistorico.adicionarParametro(new ParametroSimples(FiltroMedicaoHistorico.ANO_MES_REFERENCIA_FATURAMENTO,
							anoMesReferenciaAnterior));
			filtroMedicaoHistorico.adicionarParametro(new ParametroSimples(FiltroMedicaoHistorico.MEDICAO_TIPO_ID, medicaoHistorico
							.getMedicaoTipo().getId()));
			filtroMedicaoHistorico.adicionarCaminhoParaCarregamentoEntidade(FiltroMedicaoHistorico.LEITURA_ANORMALIDADE_FATURADA);

			MedicaoHistorico medicaoHistoricoMesAnterior = (MedicaoHistorico) Util.retonarObjetoDeColecao(getControladorUtil().pesquisar(
							filtroMedicaoHistorico, MedicaoHistorico.class.getName()));

			// Leitura Atual de Faturamento será igual a soma da Leitura Anterior com o Consumo a
			// Ser Cobrado no Mês.
			if(medicaoHistoricoMesAnterior != null && medicaoHistoricoMesAnterior.getLeituraAtualFaturamento() != null){
				medicaoHistorico.setLeituraAtualFaturamento(medicaoHistoricoMesAnterior.getLeituraAtualFaturamento()
								+ consumoHistorico.getNumeroConsumoFaturadoMes());
			}
		}

		// ############# Alterado por Luciano - OC1034808 ############# - Fim
	}

	private Map<String, Integer> montarMapaIndicadorEmissaoValidosFormato1(){

		Map<String, Integer> mapaIndicadorEmissao = new HashMap<String, Integer>();

		mapaIndicadorEmissao.put("1", 1); // –1 - Normal - (calc. e imprime)
		mapaIndicadorEmissao.put("2", 1); // 2 - Emitida duas vezes - (calc. e imprime)
		mapaIndicadorEmissao.put("3", 3); // 3 - Conta retida - (calc. e não imprime)
		mapaIndicadorEmissao.put("4", 2); // 4 - Coleta de dados - (calc. e não imprime)
		mapaIndicadorEmissao.put("5", 2); // 5 - Usuário não localizado - (calc. e não imprime)
		mapaIndicadorEmissao.put("6", 2); // 6 - Conta especial - (calc. e não imprime)

		// Permanecem com o mesmo valor até a validação do arquivo padrão convertido
		// posteriormente muda para "1"
		mapaIndicadorEmissao.put("7", 7); // 7 - Débito automático - (calc. e impressa)
		mapaIndicadorEmissao.put("8", 8); // 8 - Débito automático p/ media - (calc. e impressa)
		mapaIndicadorEmissao.put("9", 9); // 9 - Débito automático c/ resp - (calc. e impressa)

		return mapaIndicadorEmissao;
	}

	private Map<String, Integer> montarMapaIndicadorEmissaoValidosFormato3(){

		Map<String, Integer> mapaIndicadorEmissao = new HashMap<String, Integer>();
		mapaIndicadorEmissao.put("I", 1); // I – Conta Normal (Faturado Impressa)
		mapaIndicadorEmissao.put("L", 2); // L – Só leitura (Faturado não Impresso)
		mapaIndicadorEmissao.put("N", 3); // N – Sem leitura (Não Faturado, Não lido)

		return mapaIndicadorEmissao;
	}
}