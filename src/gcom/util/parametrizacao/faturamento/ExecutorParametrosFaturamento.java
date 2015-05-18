
package gcom.util.parametrizacao.faturamento;

import gcom.arrecadacao.ControladorArrecadacaoLocal;
import gcom.arrecadacao.ControladorArrecadacaoLocalHome;
import gcom.atendimentopublico.ligacaoagua.FiltroLigacaoAgua;
import gcom.atendimentopublico.ligacaoagua.FiltroLigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoagua.LigacaoAgua;
import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoesgoto.FiltroLigacaoEsgotoSituacao;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoSituacao;
import gcom.batch.ControladorBatchLocal;
import gcom.batch.ControladorBatchLocalHome;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.cliente.ClienteTipo;
import gcom.cadastro.endereco.LogradouroBairro;
import gcom.cadastro.geografico.Bairro;
import gcom.cadastro.imovel.*;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cobranca.ControladorCobrancaLocal;
import gcom.cobranca.ControladorCobrancaLocalHome;
import gcom.cobranca.bean.ContaValoresHelper;
import gcom.cobranca.bean.ObterDebitoImovelOuClienteHelper;
import gcom.cobranca.parcelamento.Parcelamento;
import gcom.fachada.Fachada;
import gcom.faturamento.*;
import gcom.faturamento.bean.DescricaoServicosContaHelper;
import gcom.faturamento.bean.EmitirContaServicoHelper;
import gcom.faturamento.bean.EmitirContaTipo2Helper;
import gcom.faturamento.bean.EmitirContaUltimosConsumosLeiturasHelper;
import gcom.faturamento.consumofaixaareacategoria.ConsumoFaixaAreaCategoria;
import gcom.faturamento.consumofaixaareacategoria.FiltroConsumoFaixaAreaCategoria;
import gcom.faturamento.conta.*;
import gcom.faturamento.credito.CreditoRealizado;
import gcom.faturamento.credito.CreditoTipo;
import gcom.faturamento.credito.FiltroCreditoRealizado;
import gcom.faturamento.debito.DebitoCobrado;
import gcom.faturamento.debito.DebitoTipo;
import gcom.faturamento.debito.FiltroDebitoCobrado;
import gcom.financeiro.FinanciamentoTipo;
import gcom.micromedicao.*;
import gcom.micromedicao.consumo.*;
import gcom.micromedicao.hidrometro.HidrometroCapacidade;
import gcom.micromedicao.hidrometro.HidrometroLocalInstalacao;
import gcom.micromedicao.hidrometro.HidrometroMarca;
import gcom.micromedicao.leitura.FiltroLeituraAnormalidade;
import gcom.micromedicao.leitura.LeituraAnormalidade;
import gcom.micromedicao.leitura.LeituraSituacao;
import gcom.micromedicao.medicao.FiltroMedicaoHistorico;
import gcom.micromedicao.medicao.MedicaoHistorico;
import gcom.relatorio.faturamento.RelatorioContaTipo2;
import gcom.relatorio.faturamento.conta.RelatorioArquivoFaturamentoConvencional;
import gcom.relatorio.faturamento.conta.RelatorioContaModelo2;
import gcom.relatorio.faturamento.conta.RelatorioContaModelo3;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.*;
import gcom.util.filtro.MaiorQue;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.ExecutorParametro;
import gcom.util.parametrizacao.cadastro.ParametroCadastro;
import gcom.util.parametrizacao.cobranca.ParametroCobranca;
import gcom.util.parametrizacao.cobranca.parcelamento.ParametroParcelamento;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

import javax.ejb.CreateException;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.comparators.ComparatorChain;
import org.apache.log4j.Logger;

import br.com.procenge.parametrosistema.api.ParametroSistema;

public class ExecutorParametrosFaturamento
				implements ExecutorParametro {

	private static final String SEPARADOR_LINHA = System.getProperty("line.separator");

	private static final int QUANTIDADE_SERVICOS_EMITIR_CONTA = 4;

	private static final String DESCRICAO_SERVICOS_OUTROS_CREDITOS = "OUTROS CRÉDITOS";

	private static final String DESCRICAO_SERVICOS_OUTROS_DEBITOS = "OUTROS DÉBITOS";

	private static final int QUANTIDADE_ULTIMOS_CONSUMOS_LEITURAS = 12;

	private static final String MESNAGEM_DEBITO_AUTOMATICO = "*** CONTA DEBITADA AUTOMATICAMENTE, NAO RECEBER ***";

	private static final ExecutorParametrosFaturamento instancia = new ExecutorParametrosFaturamento();

	private static final int TAMANHO_PARA_CALCULO_DEBITOS = 8;

	protected IRepositorioFaturamento repositorioFaturamento;

	protected IRepositorioMicromedicao repositorioMicromedicao;

	private static Logger log = Logger.getLogger(ControladorFaturamento.class);

	protected ExecutorParametrosFaturamento() {

	}

	public static ExecutorParametrosFaturamento getInstancia(){

		return instancia;
	}

	public void ejbCreate() throws CreateException{

		repositorioFaturamento = RepositorioFaturamentoHBM.getInstancia();
		repositorioMicromedicao = RepositorioMicromedicaoHBM.getInstancia();
	}

	protected ControladorBatchLocal getControladorBatch(){

		ControladorBatchLocalHome localHome = null;
		ControladorBatchLocal local = null;

		// pega a instância do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorBatchLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_BATCH_SEJB);
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

	protected ControladorArrecadacaoLocal getControladorArrecadacao(){

		ControladorArrecadacaoLocalHome localHome = null;
		ControladorArrecadacaoLocal local = null;
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

	protected ControladorFaturamentoLocal getControladorFaturamento(){

		ControladorFaturamentoLocalHome localHome = null;
		ControladorFaturamentoLocal local = null;

		// pega a instância do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorFaturamentoLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_FATURAMENTO_SEJB);
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

	protected ControladorUtilLocal getControladorUtil(){

		ControladorUtilLocalHome localHome = null;
		ControladorUtilLocal local = null;
		ServiceLocator locator = null;

		try{

			locator = ServiceLocator.getInstancia();
			localHome = (ControladorUtilLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_UTIL_SEJB);
			local = localHome.create();

			return local;
		}catch(CreateException e){

			throw new SistemaException(e);
		}catch(ServiceLocatorException e){

			throw new SistemaException(e);
		}

	}

	protected ControladorCobrancaLocal getControladorCobranca(){

		ControladorCobrancaLocalHome localHome = null;
		ControladorCobrancaLocal local = null;
		ServiceLocator locator = null;

		try{

			locator = ServiceLocator.getInstancia();
			localHome = (ControladorCobrancaLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_COBRANCA_SEJB);
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

	/**
	 * [UC3013] Gerar Declaração Anual Quitação Débitos
	 * [SB0002 – Verificar Não Geração da Declaração para o Imóvel – Modelo 1]
	 * 
	 * @author Carlos Chrystian Ramos
	 * @date 18/04/2013
	 * @throws ControladorException
	 */
	public Boolean execParamVerificarNaoGeracaoDeclaracaoImovelModelo1(ParametroSistema parametroSistema, Integer idImovel,
					Integer anoBaseDeclaracaoQuitacaoDebitoAnual) throws ControladorException, CreateException{

		this.ejbCreate();

		Boolean retorno = Boolean.TRUE;
		
		Date dataVencimentoConta = null;
		try{
			// Obter Último dia do ano, conforme ano base informado.
			dataVencimentoConta = Util.gerarDataFinalDoAnoApartirDoAnoRefencia(anoBaseDeclaracaoQuitacaoDebitoAnual);

			// Caso o imóvel tenha contas vencidas no ano de referência,
			// não gerar a declaração para o imóvel e passar para o próximo imóvel.
			boolean verificarImovelContasVencidasAnoReferencia = false;

			verificarImovelContasVencidasAnoReferencia = repositorioFaturamento.verificarImovelContasVencidasAnoReferencia(idImovel,
							dataVencimentoConta);

			if(verificarImovelContasVencidasAnoReferencia){
				retorno = Boolean.FALSE;
			}

			// Caso NÃO existam pagamentos para o imóvel no ano de referência,
			// não gerar a declaração para o imóvel e passar para o próximo imóvel.
			boolean verificarPagamentosParaImovelAnoReferencia = false;

			verificarPagamentosParaImovelAnoReferencia = repositorioFaturamento.verificarPagamentosParaImovelAnoReferencia(idImovel,
							dataVencimentoConta);

			// Caso NÃO existam pagamentos em histórico para o imóvel no ano de
			// referência, não gerar a declaração para o imóvel e passar para o próximo imóvel.
			boolean verificarPagamentosHistoricoParaImovelAnoReferencia = false;

			verificarPagamentosHistoricoParaImovelAnoReferencia = repositorioFaturamento
							.verificarPagamentosHistoricoParaImovelAnoReferencia(idImovel, dataVencimentoConta);

			if(retorno && !verificarPagamentosParaImovelAnoReferencia && !verificarPagamentosHistoricoParaImovelAnoReferencia){
				retorno = Boolean.FALSE;
			}

		}catch(ErroRepositorioException e){
			e.printStackTrace();
			throw new ControladorException("erro.sistema", e);

		}

		return retorno;
	}

	/**
	 * [UC0352] - Emitir Contas
	 * [SB0002 – Gerar Arquivo TXT Contas – Modelo 1]
	 * Responsável pela geração de arquivo tipo TXT com as contas
	 * 
	 * @author Anderson Italo
	 * @date 19/08/2011
	 * @throws ErroRepositorioException
	 * @throws ControladorException
	 * @throws IOException
	 * @throws CreateException
	 */
	public void execParamGerarArquivoTxtContasModelo1(ParametroSistema parametroSistema, List<EmitirContaTipo2Helper> listaConta,
					FaturamentoGrupo faturamentoGrupo, Integer idEmpresa, Usuario usuario,
					Collection<EmitirContaTipo2Helper> listaContaEndAlternativo, ComparatorChain sortListaConta,
					ComparatorChain sortListaContaEndAlternativo, Collection<EmitirContaTipo2Helper> listaContaBraille)
					throws ControladorException, ErroRepositorioException, IOException,
					CreateException{

		this.ejbCreate();
		StringBuilder contaTxt = new StringBuilder();

		SistemaParametro sistemaParametro = getControladorUtil().pesquisarParametrosDoSistema();

		// Gerar a primeira linha de controle do arquivo TXT
		contaTxt.append("%!" + Util.completaString("", 1379));
		contaTxt.append(SEPARADOR_LINHA);

		// Gerar a segunda linha de controle do arquivo TXT
		contaTxt.append("(des5up.jdt) STARTLM" + Util.completaString("", 1361));

		for(EmitirContaTipo2Helper contaEmitirHelper : listaConta){

			contaTxt.append(SEPARADOR_LINHA);

			// Sequência de Impressão
			String sequencialImpressaoFormatado = Util.adicionarZerosEsquedaNumero(6, "" + contaEmitirHelper.getSequencialImpressao());
			sequencialImpressaoFormatado = sequencialImpressaoFormatado.substring(0, 3) + "."
							+ sequencialImpressaoFormatado.substring(3, 6);
			contaTxt.append(sequencialImpressaoFormatado);

			// Nome do Imóvel ou Nome do cliente
			if(!Util.isVazioOuBranco(contaEmitirHelper.getNomeImovel())){

				contaTxt.append(Util.completaString(contaEmitirHelper.getNomeImovel(), 30));
			}else if(contaEmitirHelper.getNomeCliente() != null){

				contaTxt.append(Util.completaString(contaEmitirHelper.getNomeCliente(), 30));
			}else{

				contaTxt.append(Util.completaString("", 30));
			}

			// Matrícula do Imóvel
			String matriculaImovelFormatada = contaEmitirHelper.getIdImovel().toString();
			matriculaImovelFormatada = Util.adicionarZerosEsquedaNumero(10, ""
							+ Util.retornaMatriculaImovelFormatada(Util.obterInteger(matriculaImovelFormatada)));
			contaTxt.append(matriculaImovelFormatada);

			// Endereço
			// if(contaEmitirHelper.getEnderecoClienteEntrega() != null &&
			// !"".equals(contaEmitirHelper.getEnderecoClienteEntrega())){
			// contaTxt.append(Util.completaString(contaEmitirHelper.getEnderecoClienteEntrega(),
			// 50));
			// }else{
			// contaTxt.append(Util.completaString(contaEmitirHelper.getEndereco(), 50));
			// }

			// Endereço
			contaTxt.append(Util.completaString(contaEmitirHelper.getEndereco(), 50));

			// CEP
			if(!Util.isVazioOuBranco(contaEmitirHelper.getCepImovelFormatado())){

				String cepFormatado = contaEmitirHelper.getCepImovelFormatado();
				cepFormatado = Util.agruparNumeroEmMilhares(Util.obterInteger(cepFormatado.substring(0, 5))) + cepFormatado.substring(5);
				contaTxt.append(cepFormatado);
			}else{

				contaTxt.append(Util.completaString("", 10));
			}

			// Inscrição do Imóvel
			contaTxt.append(contaEmitirHelper.getInscricao());

			// Situação da Ligação de Água
			contaTxt.append(contaEmitirHelper.getIdLigacaoAguaSituacao());

			// Situação da Ligação de Esgoto
			contaTxt.append(contaEmitirHelper.getIdLigacaoEsgotoSituacao());

			// [SB0009] – Obter Código Auxiliar
			String codigoAuxiliar = "";

			// Situação da Leitura Atual obtida no [SB0005]
			String IdLeituraSituacaoAtual = "0";
			if(contaEmitirHelper.getIdLeituraSituacaoAtual() != null){

				IdLeituraSituacaoAtual = Util.completarStringZeroEsquerda(contaEmitirHelper.getIdLeituraSituacaoAtual().toString(), 1);
			}

			// Tipo de Consumo obtido no [SB0007]
			String idConsumoTipo = "0";
			if(contaEmitirHelper.getIdConsumoTipo() != null){

				idConsumoTipo = Util.completarStringZeroEsquerda(contaEmitirHelper.getIdConsumoTipo().toString(), 1);
			}

			// Anormalidade de Leitura obtida no [SB0005]
			String idLeituraAnormalidadeFaturamento = "00";
			if(contaEmitirHelper.getIdLeituraAnormalidadeFaturamento() != null){

				idLeituraAnormalidadeFaturamento = Util.completarStringZeroEsquerda(contaEmitirHelper.getIdLeituraAnormalidadeFaturamento()
								.toString(), 2);
			}

			// Tipo de Contrato - [SB0010 – Obter Mensagem de Rateio de Consumo ou Consumo Fixo de
			// Esgoto]
			String idTipoContrato = contaEmitirHelper.getIdTipoContrato().toString();

			// Anormalidade de Consumo obtida no [SB0007]
			String idConsumoAnormalidade = "0";
			if(contaEmitirHelper.getIdConsumoAnormalidade() != null){

				idConsumoAnormalidade = Util.completarStringZeroEsquerda(contaEmitirHelper.getIdConsumoAnormalidade().toString(), 1);
			}

			// Código Auxiliar
			codigoAuxiliar = IdLeituraSituacaoAtual + idConsumoTipo + idLeituraAnormalidadeFaturamento + idTipoContrato
							+ idConsumoAnormalidade;
			contaTxt.append(codigoAuxiliar);

			// Data da Leitura Anterior
			if(contaEmitirHelper.getDtLeituraAnterior() != null){

				contaTxt.append(Util.formatarData(contaEmitirHelper.getDtLeituraAnterior()).substring(0, 5));
			}else{

				contaTxt.append(Util.completaString("", 5));
			}

			// Data da Leitura Atual
			if(contaEmitirHelper.getDtLeituraAtual() != null){

				contaTxt.append(Util.formatarData(contaEmitirHelper.getDtLeituraAtual()).substring(0, 5));
			}else{

				contaTxt.append(Util.completaString("", 5));
			}

			// Dias de Consumo
			if(contaEmitirHelper.getDtLeituraAnterior() != null && contaEmitirHelper.getDtLeituraAtual() != null){
				contaTxt.append(Util.completaString(String.valueOf(Util.obterQuantidadeDiasEntreDuasDatas(contaEmitirHelper
								.getDtLeituraAnterior(), contaEmitirHelper.getDtLeituraAtual())), 2));
			}else{
				contaTxt.append(Util.completaString("", 2));
			}

			// Leitura Anterior
			if(contaEmitirHelper.getLeituraAnterior() != null){

				contaTxt.append(Util.completaStringComEspacoAEsquerda(contaEmitirHelper.getLeituraAnterior().toString(), 7));
			}else{

				contaTxt.append(Util.completaString("", 7));
			}

			// Leitura Atual
			if(contaEmitirHelper.getLeituraAtual() != null){

				contaTxt.append(Util.completaStringComEspacoAEsquerda(contaEmitirHelper.getLeituraAtual().toString(), 7));
			}else{

				contaTxt.append(Util.completaString("", 7));
			}

			// Consumo Faturado
			if(contaEmitirHelper.getConsumoFaturado() != null){

				contaTxt.append(Util.completaStringComEspacoAEsquerda(contaEmitirHelper.getConsumoFaturado().toString(), 6));
			}else{

				contaTxt.append(Util.completaString("", 6));
			}

			// Local Instalação do Hidrômetro
			if(contaEmitirHelper.getHidrometroLocalInstalacao() != null){

				contaTxt.append(Util.completaString(contaEmitirHelper.getHidrometroLocalInstalacao().getDescricao(), 10));
			}else{
				contaTxt.append(Util.completaString("", 10));
			}

			// No. Economias Categoria Residencial
			if(contaEmitirHelper.getEconResidencial() != null && contaEmitirHelper.getEconResidencial().shortValue() > 0){

				contaTxt.append(Util.completarStringZeroEsquerda(contaEmitirHelper.getEconResidencial().toString(), 3));
			}else{

				contaTxt.append(Util.completaString("", 3));
			}

			contaTxt.append(Util.completaString("", 2));

			String grupoImovel = "";

			// [SB0023] – Obter Grupo do Imóvel
			grupoImovel = this.obterGrupoImovel(contaEmitirHelper.getIdImovel());

			// Grupo do Imóvel na Categoria Residencial
			if(!grupoImovel.substring(0, 1).equals("0")){

				contaTxt.append(grupoImovel.substring(0, 1));
			}else{
				contaTxt.append(Util.completaString("", 1));
			}

			// No. Economias Categoria Comercial
			if(contaEmitirHelper.getEconComercial() != null && contaEmitirHelper.getEconComercial().shortValue() > 0){

				contaTxt.append(Util.completarStringZeroEsquerda(contaEmitirHelper.getEconComercial().toString(), 3));
			}else{

				contaTxt.append(Util.completaString("", 3));
			}

			contaTxt.append(Util.completaString("", 2));

			// Grupo do Imóvel na Categoria Comercial
			if(!grupoImovel.substring(1, 2).equals("0")){

				contaTxt.append(grupoImovel.substring(1, 2));
			}else{
				contaTxt.append(Util.completaString("", 1));
			}

			// No. Economias Categoria Industrial
			if(contaEmitirHelper.getEconIndustrial() != null && contaEmitirHelper.getEconIndustrial().shortValue() > 0){

				contaTxt.append(Util.completarStringZeroEsquerda(contaEmitirHelper.getEconIndustrial().toString(), 3));
			}else{

				contaTxt.append(Util.completaString("", 3));
			}

			contaTxt.append(Util.completaString("", 2));

			// Grupo do Imóvel na Categoria Industrial
			if(!grupoImovel.substring(2, 3).equals("0")){

				contaTxt.append(grupoImovel.substring(2, 3));
			}else{
				contaTxt.append(Util.completaString("", 1));
			}

			// No. Economias Categoria publica
			if(contaEmitirHelper.getEconPublica() != null && contaEmitirHelper.getEconPublica().shortValue() > 0){

				contaTxt.append(Util.completarStringZeroEsquerda(contaEmitirHelper.getEconPublica().toString(), 3));
			}else{

				contaTxt.append(Util.completaString("", 3));
			}

			contaTxt.append(Util.completaString("", 2));

			// Grupo do Imóvel na Categoria Público
			if(!grupoImovel.substring(3, 4).equals("0")){

				contaTxt.append(grupoImovel.substring(3, 4));
			}else{
				contaTxt.append(Util.completaString("", 1));
			}

			// Número do Hidrômetro
			if(contaEmitirHelper.getHidrometro() != null){

				contaTxt.append(Util.completaString(contaEmitirHelper.getHidrometro(), 10));
			}else{

				contaTxt.append(Util.completaString("", 10));
			}

			// Responsável
			if(contaEmitirHelper.getIdClienteResponsavel() != null && contaEmitirHelper.getIdClienteResponsavel().intValue() > 0){

				String responsavel = Util.completaStringComEspacoAEsquerdaTruncandoPelaEsquerda(contaEmitirHelper.getIdClienteResponsavel()
								.toString(), 7);
				contaTxt.append(responsavel);
			}else{

				contaTxt.append(Util.completaStringComEspacoAEsquerda("", 6) + "0");
			}

			// // Responsável
			// if(contaEmitirHelper.getIdClienteResponsavel() != null &&
			// contaEmitirHelper.getIdClienteResponsavel().intValue() > 0){
			//
			// String responsavel =
			// Util.completarStringZeroEsquerda(contaEmitirHelper.getIdClienteResponsavel().toString(),
			// 9);
			// responsavel = responsavel.substring(0, 8) + "." + responsavel.substring(8, 9);
			// responsavel = Util.retiraValoresNaoSignificativosAEsquerda(responsavel, new String[]
			// {"0"}, 10);
			// contaTxt.append(Util.completaStringComEspacoAEsquerda(responsavel, 10));
			// }else{
			//
			// contaTxt.append(Util.completaStringComEspacoAEsquerda("", 10));
			// }

			// Data Entrega
			contaTxt.append(Util.formatarData(Util.subtrairNumeroDiasDeUmaData(contaEmitirHelper.getDataVencimento(), 5)).substring(0, 5));

			// Mês/Ano de Referência
			contaTxt.append(Util.formatarAnoMesParaMesAno(contaEmitirHelper.getAnoMesConta()));

			// Data de Vencimento
			contaTxt.append(Util.formatarData(contaEmitirHelper.getDataVencimento()));

			/*
			 * Descrição dos Serviços - [SB0024 – Gerar Linhas da Descrição das
			 * Tarifas/Débitos/Créditos]
			 */
			contaTxt.append(Util.completaString(this.gerarLinhasDescricaoTarifasDebitosCreditos(contaEmitirHelper), 560));

			// Descrição dos 6 Últimos Consumos
			String consumo = "";
			if(contaEmitirHelper.getConsumo6Conta() != null){

				contaTxt.append(Util.formatarAnoMesParaMesAno(contaEmitirHelper.getAnoMes6Conta()));
				consumo = Util.retiraValoresNaoSignificativosAEsquerda(Util.completarStringZeroEsquerda(contaEmitirHelper
								.getConsumo6Conta().toString(), 6), new String[] {"0"}, 3);
				contaTxt.append(Util.completarStringComValorEsquerda(consumo, " ", 6));
			}else{

				contaTxt.append(Util.completaString("", 13));
			}

			if(contaEmitirHelper.getConsumo5Conta() != null){

				contaTxt.append(Util.formatarAnoMesParaMesAno(contaEmitirHelper.getAnoMes5Conta()));
				consumo = Util.retiraValoresNaoSignificativosAEsquerda(Util.completarStringZeroEsquerda(contaEmitirHelper
								.getConsumo5Conta().toString(), 6), new String[] {"0"}, 3);
				contaTxt.append(Util.completarStringComValorEsquerda(consumo, " ", 6));
			}else{

				contaTxt.append(Util.completaString("", 13));
			}

			if(contaEmitirHelper.getConsumo4Conta() != null){

				contaTxt.append(Util.formatarAnoMesParaMesAno(contaEmitirHelper.getAnoMes4Conta()));
				consumo = Util.retiraValoresNaoSignificativosAEsquerda(Util.completarStringZeroEsquerda(contaEmitirHelper
								.getConsumo4Conta().toString(), 6), new String[] {"0"}, 3);
				contaTxt.append(Util.completarStringComValorEsquerda(consumo, " ", 6));
			}else{

				contaTxt.append(Util.completaString("", 13));
			}

			if(contaEmitirHelper.getConsumo3Conta() != null){

				contaTxt.append(Util.formatarAnoMesParaMesAno(contaEmitirHelper.getAnoMes3Conta()));
				consumo = Util.retiraValoresNaoSignificativosAEsquerda(Util.completarStringZeroEsquerda(contaEmitirHelper
								.getConsumo3Conta().toString(), 6), new String[] {"0"}, 3);
				contaTxt.append(Util.completarStringComValorEsquerda(consumo, " ", 6));
			}else{

				contaTxt.append(Util.completaString("", 13));
			}

			if(contaEmitirHelper.getConsumo2Conta() != null){

				contaTxt.append(Util.formatarAnoMesParaMesAno(contaEmitirHelper.getAnoMes2Conta()));
				consumo = Util.retiraValoresNaoSignificativosAEsquerda(Util.completarStringZeroEsquerda(contaEmitirHelper
								.getConsumo2Conta().toString(), 6), new String[] {"0"}, 3);
				contaTxt.append(Util.completarStringComValorEsquerda(consumo, " ", 6));
			}else{

				contaTxt.append(Util.completaString("", 13));
			}

			if(contaEmitirHelper.getConsumo1Conta() != null){

				contaTxt.append(Util.formatarAnoMesParaMesAno(contaEmitirHelper.getAnoMes1Conta()));
				consumo = Util.retiraValoresNaoSignificativosAEsquerda(Util.completarStringZeroEsquerda(contaEmitirHelper
								.getConsumo1Conta().toString(), 6), new String[] {"0"}, 3);
				contaTxt.append(Util.completarStringComValorEsquerda(consumo, " ", 6));
			}else{

				contaTxt.append(Util.completaString("", 13));
			}

			// Média de Consumo
			if(contaEmitirHelper.getConsumoMedio() != null){

				consumo = Util.retiraValoresNaoSignificativosAEsquerda(Util.completarStringZeroEsquerda(contaEmitirHelper.getConsumoMedio()
								.toString(), 6), new String[] {"0"}, 4);
				contaTxt.append(Util.completarStringComValorEsquerda(consumo, " ", 6));
			}else{

				contaTxt.append(Util.completarStringZeroEsquerda("", 6));
			}

			// Valor Total a Pagar
			contaTxt.append(Util.completarStringComValorEsquerda(Util.formataBigDecimal(contaEmitirHelper.getValorTotalConta(), 2, true),
							"*", 13));

			/*
			 * Indicador de conta vencida no ano de referência do faturamento recebido – 12
			 * ocorrências com 2 caracteres cada
			 */
			contaTxt.append(Util.completaString(this.obterUltimosDozeMesesAnterioresReferenciaComContaVencida(contaEmitirHelper
							.getIdImovel(), sistemaParametro.getAnoMesFaturamento()), 24));

			/*
			 * Indicador de conta vencida do últimos cinco anos, anteriores ao ano de referência do
			 * faturamento recebido, com conta vencida – 5 ocorrências com 5 caracteres cada
			 */
			contaTxt.append(Util.completaString(this.obterUltimosCincoAnosAnterioresReferenciaComContaVencida(contaEmitirHelper
							.getIdImovel(), sistemaParametro.getAnoMesFaturamento()), 25));

			// Mensagem1 - Caso o tipo de conta seja de débito automático
			if(contaEmitirHelper.getIndicadorDebitoAutomatico().shortValue() == ConstantesSistema.SIM.shortValue()){

				contaTxt.append(Util.completaString("CONSIDERAR ESTA CONTA QUITADA SE EFETUADO O DEBITO EM CONTA CORRENTE.", 80));
			}else{

				contaTxt.append(Util.completaString("MULTA POR ATRASO SERA COBRADA NA PROXIMA FATURA.", 80));
			}

			// Mensagem2 - Parte 1 da mensagem da conta - obtida no [SB0017]
			if(contaEmitirHelper.getMsgContaParte1() != null){

				contaTxt.append(Util.completaString(contaEmitirHelper.getMsgContaParte1(), 80));
			}else{

				contaTxt.append(Util.completaString("", 80));
			}

			// [SB0030] – Gerar Código de Barras
			if(contaEmitirHelper.getIndicadorDebitoAutomatico().equals(ConstantesSistema.NAO)
							&& contaEmitirHelper.getIndicadorCodigoBarras().equals(ConstantesSistema.SIM)){

				getControladorFaturamento().gerarCodigoDeBarras(contaEmitirHelper);
				contaTxt.append(Util.completaString(contaEmitirHelper.getRepresentacaoNumericaCodBarraFormatada(), 51));
			}else{

				contaTxt.append(Util.completaString("", 51));
			}

			// Localidade
			if(contaEmitirHelper.getDescricaoLocalidade() != null){

				contaTxt.append(Util.completaString(contaEmitirHelper.getDescricaoLocalidade(), 25));
			}else{

				contaTxt.append(Util.completaString("", 25));
			}

			// Elo
			if(contaEmitirHelper.getCodigoElo() != null
							&& contaEmitirHelper.getCodigoElo().intValue() != contaEmitirHelper.getInscLocalidade().intValue()){

				contaTxt.append(Util.completaString("VINC=" + contaEmitirHelper.getCodigoElo().toString(), 8));
			}else{

				contaTxt.append(Util.completaString("", 8));
			}

			// [SB0031] – Obter Dados da Qualidade Água
			QualidadeAgua qualidadeAgua = repositorioFaturamento.pesquisarQualidadeAguaPorLocalidadeAnoMesFaturamento(sistemaParametro
							.getAnoMesFaturamento(), contaEmitirHelper.getInscLocalidade());

			if(qualidadeAgua == null && contaEmitirHelper.getCodigoElo() != null){

				qualidadeAgua = repositorioFaturamento.pesquisarQualidadeAguaPorLocalidadeAnoMesFaturamento(sistemaParametro
								.getAnoMesFaturamento(), contaEmitirHelper.getCodigoElo());
			}
			QualidadeAguaPadrao qualidadeAguaPadrao = repositorioFaturamento.retornarQualidadeAguaPadrao();

			// Turbidez Exigida
			if(qualidadeAguaPadrao.getNumeroAmostrasExigidasTurbidez() != null){

				contaTxt.append(Util
								.completaStringComEspacoAEsquerda(qualidadeAguaPadrao.getNumeroAmostrasExigidasTurbidez().toString(), 4));
			}else{

				contaTxt.append(Util.completaString("", 3) + "0");
			}

			// Turbidez Realizado
			if(qualidadeAgua != null && qualidadeAgua.getNumeroAmostrasRealizadasTurbidez() != null){

				contaTxt.append(Util.completaStringComEspacoAEsquerda(qualidadeAgua.getNumeroAmostrasRealizadasTurbidez().toString(), 4));
			}else{

				contaTxt.append(Util.completaString("", 3) + "0");
			}

			// Turbidez Atendido
			if(qualidadeAgua != null && qualidadeAgua.getNumeroAmostrasConformesTurbidez() != null){

				contaTxt.append(Util.completaStringComEspacoAEsquerda(qualidadeAgua.getNumeroAmostrasConformesTurbidez().toString(), 4));
			}else{

				contaTxt.append(Util.completaString("", 3) + "0");
			}

			// Cor Exigida
			if(qualidadeAguaPadrao.getNumeroAmostrasExigidasCor() != null){

				contaTxt.append(Util.completaStringComEspacoAEsquerda(qualidadeAguaPadrao.getNumeroAmostrasExigidasCor().toString(), 4));
			}else{

				contaTxt.append(Util.completaString("", 3) + "0");
			}

			// Cor Realizado
			if(qualidadeAgua != null && qualidadeAgua.getNumeroAmostrasRealizadasCor() != null){

				contaTxt.append(Util.completaStringComEspacoAEsquerda(qualidadeAgua.getNumeroAmostrasRealizadasCor().toString(), 4));
			}else{

				contaTxt.append(Util.completaString("", 3) + "0");
			}

			// Cor Atendido
			if(qualidadeAgua != null && qualidadeAgua.getNumeroAmostrasConformesCor() != null){

				contaTxt.append(Util.completaStringComEspacoAEsquerda(qualidadeAgua.getNumeroAmostrasConformesCor().toString(), 4));
			}else{

				contaTxt.append(Util.completaString("", 3) + "0");
			}

			// Cloro Exigida
			if(qualidadeAguaPadrao.getNumeroAmostrasExigidasCloro() != null){

				contaTxt.append(Util.completaStringComEspacoAEsquerda(qualidadeAguaPadrao.getNumeroAmostrasExigidasCloro().toString(), 4));
			}else{

				contaTxt.append(Util.completaString("", 3) + "0");
			}

			// Cloro Realizado
			if(qualidadeAgua != null && qualidadeAgua.getNumeroAmostrasRealizadasCloro() != null){

				contaTxt.append(Util.completaStringComEspacoAEsquerda(qualidadeAgua.getNumeroAmostrasRealizadasCloro().toString(), 4));
			}else{

				contaTxt.append(Util.completaString("", 3) + "0");
			}

			// Cloro Atendido
			if(qualidadeAgua != null && qualidadeAgua.getNumeroAmostrasConformesCloro() != null){

				contaTxt.append(Util.completaStringComEspacoAEsquerda(qualidadeAgua.getNumeroAmostrasConformesCloro().toString(), 4));
			}else{

				contaTxt.append(Util.completaString("", 3) + "0");
			}

			// Flúor Exigida
			if(qualidadeAguaPadrao.getNumeroAmostrasExigidasFluor() != null){

				contaTxt.append(Util.completaStringComEspacoAEsquerda(qualidadeAguaPadrao.getNumeroAmostrasExigidasFluor().toString(), 4));
			}else{

				contaTxt.append(Util.completaString("", 3) + "0");
			}

			// Flúor Realizado
			if(qualidadeAgua != null && qualidadeAgua.getNumeroAmostrasRealizadasFluor() != null){

				contaTxt.append(Util.completaStringComEspacoAEsquerda(qualidadeAgua.getNumeroAmostrasRealizadasFluor().toString(), 4));
			}else{

				contaTxt.append(Util.completaString("", 3) + "0");
			}

			// Flúor Atendido
			if(qualidadeAgua != null && qualidadeAgua.getNumeroAmostrasConformesFluor() != null){

				contaTxt.append(Util.completaStringComEspacoAEsquerda(qualidadeAgua.getNumeroAmostrasConformesFluor().toString(), 4));
			}else{

				contaTxt.append(Util.completaString("", 3) + "0");
			}

			// Coliformes Totais Exigida
			if(qualidadeAguaPadrao.getNumeroAmostrasExigidasColiformesTotais() != null){

				contaTxt.append(Util.completaStringComEspacoAEsquerda(qualidadeAguaPadrao.getNumeroAmostrasExigidasColiformesTotais()
								.toString(), 4));
			}else{

				contaTxt.append(Util.completaString("", 3) + "0");
			}

			// Coliformes Totais Realizado
			if(qualidadeAgua != null && qualidadeAgua.getNumeroAmostrasRealizadasColiformesTotais() != null){

				contaTxt.append(Util.completaStringComEspacoAEsquerda(qualidadeAgua.getNumeroAmostrasRealizadasColiformesTotais()
								.toString(), 4));
			}else{

				contaTxt.append(Util.completaString("", 3) + "0");
			}

			// Coliformes Totais Atendido
			if(qualidadeAgua != null && qualidadeAgua.getNumeroAmostrasConformesColiformesTotais() != null){

				contaTxt.append(Util.completaStringComEspacoAEsquerda(
								qualidadeAgua.getNumeroAmostrasConformesColiformesTotais().toString(), 4));
			}else{

				contaTxt.append(Util.completaString("", 3) + "0");
			}

			// Coliformes Termo Realizado
			if(qualidadeAgua != null && qualidadeAgua.getNumeroAmostrasRealizadasColiformesTermotolerantes() != null){

				contaTxt.append(Util.completaStringComEspacoAEsquerda(qualidadeAgua.getNumeroAmostrasRealizadasColiformesTermotolerantes()
								.toString(), 4));
			}else{

				contaTxt.append(Util.completaString("", 3) + "0");
			}

			// Coliformes Termo Atendido
			if(qualidadeAgua != null && qualidadeAgua.getNumeroAmostrasConformesColiformesTermotolerantes() != null){

				contaTxt.append(Util.completaStringComEspacoAEsquerda(qualidadeAgua.getNumeroAmostrasConformesColiformesTermotolerantes()
								.toString(), 4));
			}else{

				contaTxt.append(Util.completaString("", 3) + "0");
			}

			// Qualidade Água - Conclusão
			if(qualidadeAgua != null){

				contaTxt.append(Util.completaString(qualidadeAgua.getDescricaoConclusaoAnalisesRealizadas(), 110));
			}else{

				contaTxt.append(Util.completaString("", 110));
			}

			// Telefone
			String fone = "";
			if(contaEmitirHelper.getCodigoElo() != null){

				fone = repositorioFaturamento.retornarFoneLocalidade(contaEmitirHelper.getCodigoElo());

				if(fone != null){

					contaTxt.append(Util.completaString(fone, 13));
				}else{

					fone = repositorioFaturamento.retornarFoneLocalidade(contaEmitirHelper.getInscLocalidade());

					if(fone != null){

						contaTxt.append(Util.completaString(fone, 13));
					}else{

						contaTxt.append(Util.completaString("", 13));
					}
				}
			}else{

				fone = repositorioFaturamento.retornarFoneLocalidade(contaEmitirHelper.getInscLocalidade());

				if(fone != null){

					contaTxt.append(Util.completaString(fone, 13));
				}else{

					contaTxt.append(Util.completaString("", 13));
				}
			}

			contaTxt.append(SEPARADOR_LINHA);

			// Gerar a linha de código de barras
			// Caractere de controle 1
			contaTxt.append(Util.completaString("%%XGF", 5));

			// Parentese 1
			contaTxt.append(Util.completaString("(", 1));

			if(contaEmitirHelper.getIndicadorDebitoAutomatico().equals(ConstantesSistema.NAO)
							&& contaEmitirHelper.getIndicadorCodigoBarras().equals(ConstantesSistema.SIM)){

				/*
				 * Código de Barras - <<Inclui>> [UC0340 – Gerar Código de Barras] passando a
				 * representação numérica do código de barras (44 posições retornadas pelo [UC0229])
				 * sem
				 * os dígitos verificadores
				 */
				contaTxt.append(Util.completaString(contaEmitirHelper.getRepresentacaoNumericaCodBarraSemDigito(), 44));
			}else{

				contaTxt.append("00000000000000000000000000000000000000000000");
			}

			// Parentese 2
			contaTxt.append(Util.completaString(")", 1));

			// Caractere e Controle 2
			contaTxt.append(Util.completaString("printcodbar", 11));

			contaTxt.append(Util.completaString("", 1));

			// Caractere e Controle 3
			contaTxt.append(Util.completaString("PAGEBRK", 7));
		}

		String nomeArquivo = "CONTAS_EMPRESA_" + idEmpresa.toString() + "_g" + faturamentoGrupo.getId() + "_"
						+ faturamentoGrupo.getAnoMesReferencia().toString() + ".txt";

		// Envia o arquivo para ser gerado como um relatório batch
		enviarArquivoEmissaoContasParaBatch(contaTxt, nomeArquivo, usuario);
	}

	/**
	 * [UC0352] Emitir Contas
	 * [SB0023 – Obter Grupo do Imóvel].
	 * 
	 * @author Anderson Italo
	 * @data 23/08/2011
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
	 * [UC0352] Emitir Contas
	 * [SB0024] – Gerar Linhas da Descrição das Tarifas/Débitos/Créditos
	 * 
	 * @author Anderson Italo
	 * @data 24/08/2011
	 * @param idImovel
	 * @throws ControladorException
	 * @throws ErroRepositorioException
	 */
	private String gerarLinhasDescricaoTarifasDebitosCreditos(EmitirContaTipo2Helper contaEmitirHelper) throws ControladorException,
					ErroRepositorioException{

		StringBuilder retorno = new StringBuilder("");

		/*
		 * O sistema inicializa com nulos a tabela de Descrição das Tarifas/Débitos/Créditos (Índice
		 * de tarifas/débitos/créditos) variando o índice de 1 (um) até o limite de linhas
		 * (PASI_VLPARAMETRO da tabela PARAMETRO_SISTEMA com
		 * PASI_DSPARAMETRO=”P_LIMITE_EMISSAO_TARIFAS_CONTA”)).
		 */
		int indexServicos = 0;
		int limitacaoIndex = 3;
		int limiteEmissaoTarifasConta = Util.obterInteger((String) ParametroFaturamento.P_LIMITE_EMISSAO_TARIFAS_CONTA.executar(this));

		// O sistema obtém o valor total dos débitos cobrados de parcelamento para a conta
		String idsFinanciamentoTipo = ParametroParcelamento.P_FINANCIAMENTO_TIPO_PARCELAMENTO.executar();

		// Acrescenta aos Ids o tipo de financiamento Juros de Parcelamento
		idsFinanciamentoTipo = idsFinanciamentoTipo + "," + FinanciamentoTipo.JUROS_PARCELAMENTO;

		List<DescricaoServicosContaHelper> linhasDescricaoServicosConta = new ArrayList<DescricaoServicosContaHelper>();

		/*
		 * carrega coleções utilizadas posteriormente para determinar a limitação do index de
		 * tarifas
		 */
		Collection<DebitoCobrado> colecaoDebitoCobrado = null;
		Collection<DebitoCobrado> colecaoDebitoCobradoParcelamento = null;
		boolean possuiDebitos = false;
		if(contaEmitirHelper.getDebitos().compareTo(new BigDecimal("0")) == 1){

			// O sistema obtém os débitos cobrados de serviço para a conta
			colecaoDebitoCobrado = repositorioFaturamento.buscarDebitosCobradosContaEmissaoTxt(contaEmitirHelper.getIdConta(),
							FinanciamentoTipo.SERVICO_NORMAL.toString());

			// O sistema obtém os débitos cobrados de parcelamento para a conta
			colecaoDebitoCobradoParcelamento = repositorioFaturamento.buscarDebitosCobradosContaEmissaoTxt(contaEmitirHelper.getIdConta(),
							idsFinanciamentoTipo);

			if(!Util.isVazioOrNulo(colecaoDebitoCobrado) || !Util.isVazioOrNulo(colecaoDebitoCobradoParcelamento)){

				possuiDebitos = true;
				limitacaoIndex += 2;
			}
		}

		Collection<CreditoRealizado> colecaoCreditoRealizado = null;
		boolean possuiCreditos = false;
		if(contaEmitirHelper.getValorCreditos().compareTo(new BigDecimal("0")) == 1){

			// O sistema obtém os créditos realizados para a conta
			colecaoCreditoRealizado = repositorioFaturamento.buscarCreditosRealizadosContaEmissaoTxt(contaEmitirHelper.getIdConta());

			if(!Util.isVazioOrNulo(colecaoCreditoRealizado)){

				possuiCreditos = true;
				limitacaoIndex++;
			}
		}

		Collection<ContaImpostosDeduzidos> colecaoContaImpostosDeduzidos = null;
		boolean possuiImpostos = false;
		if(contaEmitirHelper.getValorImpostos().compareTo(new BigDecimal("0")) == 1){

			// O sistema obtém os impostos deduzidos para a conta
			colecaoContaImpostosDeduzidos = this.repositorioFaturamento.pesquisarContaImpostosDeduzidos(contaEmitirHelper.getIdConta());

			if(!Util.isVazioOrNulo(colecaoContaImpostosDeduzidos)){

				possuiImpostos = true;
				limitacaoIndex++;
			}
		}

		/*
		 * Caso o valor de água da conta (CNTA_VLAGUA) seja maior que zero:
		 * O sistema gera as linhas com as faixas de consumo da conta
		 * [SB0025 – Gerar Linhas das Faixas de Consumo da Conta]
		 */
		if(contaEmitirHelper.getValorAgua().compareTo(new BigDecimal("0")) == 1){

			linhasDescricaoServicosConta = this.gerarLinhasFaixaConsumoConta(contaEmitirHelper, linhasDescricaoServicosConta,
							limitacaoIndex);
		}

		/*
		 * Caso o valor de água de esgoto (CNTA_VLESGOTO) seja maior que zero
		 * [SB0026 – Gerar Linha da Tarifa de Esgoto].
		 */
		if(contaEmitirHelper.getValorEsgoto().compareTo(new BigDecimal("0")) == 1){

			linhasDescricaoServicosConta = this.gerarLinhaTarifaEsgoto(contaEmitirHelper, linhasDescricaoServicosConta);
		}

		/*
		 * Caso o valor dos débitos cobrados da conta (CNTA_VLDEBITOS) seja maior que zero
		 * [SB0027 – Gerar Linha da Tarifa de Esgoto].
		 */
		if(possuiDebitos && (possuiCreditos == false && possuiImpostos == false)){

			linhasDescricaoServicosConta = this.gerarLinhasDebitosCobrados(contaEmitirHelper, linhasDescricaoServicosConta,
							colecaoDebitoCobrado, colecaoDebitoCobradoParcelamento, 2);

		}else if(possuiDebitos && possuiCreditos && (possuiImpostos == false)){

			linhasDescricaoServicosConta = this.gerarLinhasDebitosCobrados(contaEmitirHelper, linhasDescricaoServicosConta,
							colecaoDebitoCobrado, colecaoDebitoCobradoParcelamento, 3);
		}else if(possuiDebitos && possuiCreditos && possuiImpostos){

			linhasDescricaoServicosConta = this.gerarLinhasDebitosCobrados(contaEmitirHelper, linhasDescricaoServicosConta,
							colecaoDebitoCobrado, colecaoDebitoCobradoParcelamento, 4);
		}else if(possuiDebitos && possuiImpostos && possuiCreditos == false){
			linhasDescricaoServicosConta = this.gerarLinhasDebitosCobrados(contaEmitirHelper, linhasDescricaoServicosConta,
							colecaoDebitoCobrado, colecaoDebitoCobradoParcelamento, 3);
		}

		/*
		 * Caso o valor dos créditos realizados na conta (CNTA_VLCREDITOS) seja maior que zero
		 * [SB0028 – Gerar Linhas dos Créditos Realizados].
		 */
		if(possuiCreditos && (possuiImpostos == false)){

			linhasDescricaoServicosConta = this.gerarLinhasCreditosRealizados(contaEmitirHelper, linhasDescricaoServicosConta,
							colecaoCreditoRealizado, 1);
		}else if(possuiCreditos && possuiImpostos){

			linhasDescricaoServicosConta = this.gerarLinhasCreditosRealizados(contaEmitirHelper, linhasDescricaoServicosConta,
							colecaoCreditoRealizado, 2);
		}

		/*
		 * Caso o valor dos impostos retidos da conta (CNTA_VLIMPOSTOS) seja maior que zero
		 * [SB0029 – Gerar Linhas dos Impostos Retidos].
		 */
		if(possuiImpostos){

			linhasDescricaoServicosConta = this.gerarLinhasImpostosRetidos(contaEmitirHelper, linhasDescricaoServicosConta,
							colecaoContaImpostosDeduzidos);
		}

		if(!Util.isVazioOrNulo(linhasDescricaoServicosConta)){

			for(Iterator iterator = linhasDescricaoServicosConta.iterator(); iterator.hasNext();){

				DescricaoServicosContaHelper linhaDescricaoServicos = (DescricaoServicosContaHelper) iterator.next();
				String conteudoLinha = "";
				indexServicos++;

				if(linhaDescricaoServicos.getTarifa() != null){

					conteudoLinha += linhaDescricaoServicos.getTarifa();
				}

				if(linhaDescricaoServicos.getCategoria() != null){

					conteudoLinha += linhaDescricaoServicos.getCategoria();
				}

				if(linhaDescricaoServicos.getFaixa() != null){

					conteudoLinha += linhaDescricaoServicos.getFaixa();
				}

				if(linhaDescricaoServicos.getValorTarifa() != null){

					conteudoLinha += linhaDescricaoServicos.getValorTarifa();
				}

				if(linhaDescricaoServicos.getValorConsumo() != null){

					conteudoLinha += linhaDescricaoServicos.getValorConsumo();
				}

				if(linhaDescricaoServicos.getTotalizacao() != null){

					conteudoLinha += linhaDescricaoServicos.getTotalizacao();
				}

				if(linhaDescricaoServicos.getDescricaoEsgoto() != null){

					conteudoLinha += linhaDescricaoServicos.getDescricaoEsgoto();
				}

				if(linhaDescricaoServicos.getValorEsgoto() != null){

					conteudoLinha += linhaDescricaoServicos.getValorEsgoto();
				}

				if(linhaDescricaoServicos.getDescricaoDebito() != null){

					conteudoLinha += linhaDescricaoServicos.getDescricaoDebito();
				}

				if(linhaDescricaoServicos.getValorDebito() != null){

					conteudoLinha += linhaDescricaoServicos.getValorDebito();
				}

				if(linhaDescricaoServicos.getDescricaoCredito() != null){

					conteudoLinha += linhaDescricaoServicos.getDescricaoCredito();
				}

				if(linhaDescricaoServicos.getValorCredito() != null){

					conteudoLinha += linhaDescricaoServicos.getValorCredito();
				}

				if(linhaDescricaoServicos.getDescricaoImposto() != null){

					conteudoLinha += linhaDescricaoServicos.getDescricaoImposto();
				}

				if(linhaDescricaoServicos.getValorImposto() != null){

					conteudoLinha += linhaDescricaoServicos.getValorImposto();
				}

				retorno.append(Util.completaString(conteudoLinha, 40));

				if(indexServicos == limiteEmissaoTarifasConta){
					break;
				}
			}
		}

		return retorno.toString();
	}

	/**
	 * [UC0352] Emitir Contas
	 * [SB0025] – Gerar Linhas das Faixas de Consumo da Conta
	 * 
	 * @author Anderson Italo
	 * @data 24/08/2011
	 * @param contaEmitirHelper
	 *            , linhasDescricaoServicosConta, limitacaoIndex
	 * @throws ControladorException
	 *             , ErroRepositorioException
	 */
	private List<DescricaoServicosContaHelper> gerarLinhasFaixaConsumoConta(EmitirContaTipo2Helper contaEmitirHelper,
					List<DescricaoServicosContaHelper> linhasDescricaoServicosConta, int limitacaoIndex) throws ControladorException,
					ErroRepositorioException{

		/*
		 * O sistema inicializa com nulos a tabela de Tarifa (índice de tarifas), Categoria
		 * (índice de tarifas), Faixa de Consumo (índice de tarifas), Valor da Tarifa na Faixa
		 * (índice de tarifas) e Valor de Consumo na Faixa (índice de tarifas) variando o índice de
		 * 1 (um) até o limite de emissão de tarifas na conta
		 */
		int limite = Util.obterInteger((String) ParametroFaturamento.P_LIMITE_EMISSAO_TARIFAS_CONTA.executar(this));
		int indexTarifas = 0;

		Collection colecaoContaCategoria = repositorioFaturamento.pesquisarContaCategoriaSubCategoria(contaEmitirHelper.getIdConta());

		if(colecaoContaCategoria != null && !colecaoContaCategoria.isEmpty()){

			Iterator iteratorContaCategoria = colecaoContaCategoria.iterator();
			List<ContaCategoria> colecaoContaCategoriaNaoProcessadas = new ArrayList<ContaCategoria>();
			boolean primeiraOcorrenciaCategoria = true;

			while(iteratorContaCategoria.hasNext()){

				ContaCategoria contaCategoria = (ContaCategoria) iteratorContaCategoria.next();

				BigDecimal qtdEconomia = Util.formatarMoedaRealparaBigDecimal(contaCategoria.getQuantidadeEconomia().toString(), 4);

				if(colecaoContaCategoriaNaoProcessadas.isEmpty()){

					DescricaoServicosContaHelper linhaFaixaConsumo = new DescricaoServicosContaHelper();

					FiltroContaCategoriaConsumoFaixa filtroContaCategoriaConsumoFaixa = new FiltroContaCategoriaConsumoFaixa();
					filtroContaCategoriaConsumoFaixa.adicionarParametro(new ParametroSimples(FiltroContaCategoriaConsumoFaixa.CONTA_ID,
									contaEmitirHelper.getIdConta()));
					filtroContaCategoriaConsumoFaixa.adicionarParametro(new ParametroSimples(FiltroContaCategoriaConsumoFaixa.CATEGORIA_ID,
									contaCategoria.getComp_id().getCategoria().getId()));
					filtroContaCategoriaConsumoFaixa.setCampoOrderBy(FiltroContaCategoriaConsumoFaixa.CONSUMO_FAIXA_INICIO);

					Collection<ContaCategoriaConsumoFaixa> colecaoContaCategoriaConsumoFaixa = getControladorUtil().pesquisar(
									filtroContaCategoriaConsumoFaixa, ContaCategoriaConsumoFaixa.class.getName());

					// Consumo na Categoria dividido pela Qtd de Ecônomias
					Integer consumoCategoria = 0;
					if(contaCategoria.getConsumoAgua() != null){

						consumoCategoria = (contaCategoria.getConsumoAgua().intValue() / contaCategoria.getQuantidadeEconomia());
					}

					/*
					 * O sistema obtém as categorias da conta. Para cada categoria da conta:
					 * Caso não existam dados de medição (verificar FS0002):
					 */
					if(contaEmitirHelper.getIndicadorPossuiMedicaoHistorico().equals(ConstantesSistema.NAO)){

						// Índice de tarifas = Índice de tarifas + 1
						indexTarifas++;

						/*
						 * Formata a Tarifa (Índice de tarifas) = “ÁGUA”, caso seja a primeira
						 * ocorrência das faixas de consumo da conta; caso contrário, atribuir o
						 * valor
						 * nulo à Tarifa (Índice de tarifas)
						 */
						if(primeiraOcorrenciaCategoria){

							linhaFaixaConsumo.setTarifa("ÁGUA ");
							primeiraOcorrenciaCategoria = false;
						}else{

							linhaFaixaConsumo.setTarifa(Util.completaString("", 5));
						}

						/*
						 * Formata a Categoria (Índice de tarifas) = Descrição abreviada da
						 * categoria.
						 */
						linhaFaixaConsumo.setCategoria(Util.completaString(contaCategoria.getComp_id().getCategoria()
										.getDescricaoAbreviada(), 4));

						// Formata a Faixa de Consumo (Índice de tarifas) = Consumo mínimo de água
						// para a categoria.
						if(contaCategoria.getConsumoMinimoAgua() != null){

							linhaFaixaConsumo.setFaixa(Util.completarStringComValorEsquerda(contaCategoria.getConsumoMinimoAgua()
											.toString(), " ", 7)
											+ " ");
						}else{

							linhaFaixaConsumo.setFaixa(Util.completaString("", 8));
						}

						/*
						 * Formata o Valor da Tarifa na Faixa (Índice de tarifas) = Valor da tarifa
						 * mínima de água para a categoria
						 */
						if(contaCategoria.getValorTarifaMinimaAgua() != null){

							linhaFaixaConsumo.setValorTarifa(Util.completarStringComValorEsquerda(Util.formataBigDecimal(contaCategoria
											.getValorTarifaMinimaAgua(), 4, true), " ", 8));
						}else{

							linhaFaixaConsumo.setFaixa(Util.completaString("", 8));
						}

						/*
						 * Formata o Valor de Consumo na Faixa (Índice de tarifas) = Valor de água
						 * para
						 * a categoria
						 */
						linhaFaixaConsumo.setValorConsumo(Util.completarStringComValorEsquerda(Util.formataBigDecimal(contaCategoria
										.getValorAgua(), 2, true), " ", 14));

						/*
						 * Descrição das Tarifas/Débitos/Créditos (Índice de
						 * tarifas/débitos/créditos) =
						 * Tarifa (Índice de tarifas) + Categoria (Índice de tarifas) + Faixa de
						 * Consumo
						 * (Índice de tarifas) + Valor da Tarifa na Faixa (Índice de tarifas) +
						 * Valor de
						 * Consumo na Faixa (Índice de tarifas).
						 * Retorna ao passo 4 (próxima categoria).
						 */
						linhasDescricaoServicosConta.add(linhaFaixaConsumo);

					}else if(Util.isVazioOrNulo(colecaoContaCategoriaConsumoFaixa)){

						/*
						 * Caso existam dados de medição (verificar FS0002) e não existam faixas de
						 * consumo para a conta/categoria. Índice de tarifas = Índice de tarifas +
						 * 1.
						 */
						indexTarifas++;

						/*
						 * Formata a Tarifa (Índice de tarifas) = “ÁGUA”, caso seja a primeira
						 * ocorrência das faixas de consumo da conta; caso contrário, atribuir o
						 * valor
						 * nulo à Tarifa (Índice de tarifas)
						 */
						if(primeiraOcorrenciaCategoria){

							linhaFaixaConsumo.setTarifa("ÁGUA ");
							primeiraOcorrenciaCategoria = false;
						}else{

							linhaFaixaConsumo.setTarifa(Util.completaString("", 5));
						}

						/*
						 * Formata a Categoria (Índice de tarifas) = Descrição abreviada da
						 * categoria.
						 */
						linhaFaixaConsumo.setCategoria(Util.completaString(contaCategoria.getComp_id().getCategoria()
										.getDescricaoAbreviada(), 4));

						// Formata a Faixa de Consumo (Índice de tarifas) = “CONSUMO”.
						linhaFaixaConsumo.setFaixa("CONSUMO ");

						/*
						 * Formata o Valor da Tarifa na Faixa (Índice de tarifas) = Consumo de água
						 * para
						 * a categoria
						 */
						if(contaCategoria.getConsumoAgua() != null){

							linhaFaixaConsumo.setValorTarifa(Util.completarStringComValorEsquerda(contaCategoria.getConsumoAgua()
											.toString()
											+ " M3", " ", 8));
						}else{

							linhaFaixaConsumo.setValorTarifa(Util.completarStringComValorEsquerda("0" + " M3", " ", 8));
						}

						/*
						 * Formata o Valor de Consumo na Faixa (Índice de tarifas) = Valor de água
						 * para
						 * a categoria
						 */
						linhaFaixaConsumo.setValorConsumo(Util.completarStringComValorEsquerda(Util.formataBigDecimal(contaCategoria
										.getValorAgua(), 2, true), " ", 14));

						/*
						 * Descrição das Tarifas/Débitos/Créditos (Índice de
						 * tarifas/débitos/créditos) =
						 * Tarifa (Índice de tarifas) + Categoria (Índice de tarifas) + Faixa de
						 * Consumo
						 * (Índice de tarifas) + Valor da Tarifa na Faixa (Índice de tarifas) +
						 * Valor de
						 * Consumo na Faixa (Índice de tarifas).
						 */
						linhasDescricaoServicosConta.add(linhaFaixaConsumo);

					}else if(!Util.isVazioOrNulo(colecaoContaCategoriaConsumoFaixa)){

						boolean primeiraOcorrenciaCategoriaFaixa = true;
						BigDecimal somatorioValorConsumoFaixa = new BigDecimal(0);

						/*
						 * Caso existam dados de medição (verificar FS0002) e existam faixas de
						 * consumo
						 * para a conta/categoria. O sistema obtém as faixas de consumo para a
						 * conta/categoria.
						 */
						for(Iterator iteratorContaCategoriaConsumoFaixa = colecaoContaCategoriaConsumoFaixa.iterator(); iteratorContaCategoriaConsumoFaixa
										.hasNext();){

							ContaCategoriaConsumoFaixa contaCategoriaConsumoFaixa = (ContaCategoriaConsumoFaixa) iteratorContaCategoriaConsumoFaixa
											.next();

							linhaFaixaConsumo = new DescricaoServicosContaHelper();

							// Índice de tarifas = Índice de tarifas + 1
							indexTarifas++;

							/*
							 * Caso o Índice de tarifas seja menor que o limite de emissão de
							 * tarifas na
							 * conta menos 3 (três) (reservar 3 linhas para total da categoria,
							 * total demais categorias e valor de esgoto) menos 2 (dois), caso
							 * existam débitos cobrados (reservar 2 linhas para os débitos), menos 1
							 * (um), caso existam créditos realizados (reservar 1 linha para os
							 * créditos), menos 1 (um), caso existam impostos retidos (reservar 1
							 * linha para os impostos)
							 */
							if(indexTarifas < (limite - limitacaoIndex)){

								/*
								 * Formata a Tarifa (Índice de tarifas) = “ÁGUA”, caso seja a
								 * primeira
								 * ocorrência das faixas de consumo da conta; caso contrário,
								 * atribuir o
								 * valor
								 * nulo à Tarifa (Índice de tarifas)
								 */
								if(primeiraOcorrenciaCategoria){

									linhaFaixaConsumo.setTarifa("ÁGUA ");
									primeiraOcorrenciaCategoria = false;
								}else{

									linhaFaixaConsumo.setTarifa(Util.completaString("", 5));
								}

								/*
								 * Formata a Categoria (Índice de tarifas) = Descrição abreviada
								 * da
								 * categoria , caso seja a primeira ocorrência da categoria nas
								 * faixas
								 * de
								 * consumo da conta; caso contrário, atribuir o valor nulo à
								 * Categoria
								 * (Índice de tarifas).
								 */
								if(primeiraOcorrenciaCategoriaFaixa){

									linhaFaixaConsumo.setCategoria(Util.completaString(contaCategoria.getComp_id().getCategoria()
													.getDescricaoAbreviada(), 4));
									primeiraOcorrenciaCategoriaFaixa = false;
								}else{

									linhaFaixaConsumo.setCategoria(Util.completaString("", 4));
								}

								/*
								 * Formata o Valor da Tarifa na Faixa (Índice de tarifas) =
								 * (CCCF_VLTARIFAFAIXA da tabela CONTA_CATEGORIA_CONSUMO_FAIXA)
								 */
								linhaFaixaConsumo.setValorTarifa(Util.completarStringComValorEsquerda(Util.formataBigDecimal(
												contaCategoriaConsumoFaixa.getValorTarifaFaixa(), 4, true), " ", 8));

								Integer consumoFaixa = null;
								BigDecimal valorConsumoFaixa = null;

								if(contaCategoriaConsumoFaixa.getConsumoFaixaInicio() == 0){

									// ConsumoFaixa = ContaCategoria.ConsumoMinimoAgua;
									// (conta_categoria.ctcg_nnconsumominimoagua)
									consumoFaixa = contaCategoria.getConsumoMinimoAgua();

									if(contaCategoria.getValorTarifaMinimaAgua() != null){

										if(contaCategoriaConsumoFaixa.getValorTarifaFaixa() == null){
											throw new ControladorException("atencao.valor.tarifa.faixa.nulo");
										}

										// valor da tarifa mínima de agua para categoria
										Integer consumoFaixaMinimaFinal = contaCategoriaConsumoFaixa.getConsumoFaixaFim();
										if(consumoFaixaMinimaFinal == null){
											consumoFaixaMinimaFinal = Integer.valueOf(0);
										}

										valorConsumoFaixa = contaCategoriaConsumoFaixa.getValorTarifaFaixa().multiply(
														new BigDecimal(consumoFaixaMinimaFinal.intValue()));

										// Multiplica o valor pela quantidade de economia
										valorConsumoFaixa = valorConsumoFaixa.multiply(qtdEconomia).setScale(Parcelamento.CASAS_DECIMAIS,
														BigDecimal.ROUND_DOWN);
									}

								}else{
									// ConsumoFaixa = ContaCategoria.ConsumoMinimoAgua;
									// (conta_categoria.ctcg_nnconsumominimoagua)
									consumoFaixa = contaCategoriaConsumoFaixa.getConsumoAgua();
									valorConsumoFaixa = contaCategoriaConsumoFaixa.getValorAgua();
								}

								if(consumoFaixa == null){
									consumoFaixa = Integer.valueOf(0);
								}

								if(valorConsumoFaixa == null){
									valorConsumoFaixa = BigDecimal.ZERO;
								}

								linhaFaixaConsumo.setFaixa(Util.completarStringComValorEsquerda(consumoFaixa.toString() + " X", " ", 8));
								linhaFaixaConsumo.setValorConsumo(Util.completarStringComValorEsquerda(
												Util.formataBigDecimal(valorConsumoFaixa, 2, true), " ", 14));
								linhaFaixaConsumo.setValorConsumoSemFormatacao(new BigDecimal(Util.formataBigDecimal(valorConsumoFaixa, 2,
												false).replace(",", ".")));

								somatorioValorConsumoFaixa = somatorioValorConsumoFaixa.add(linhaFaixaConsumo
												.getValorConsumoSemFormatacao());
								linhasDescricaoServicosConta.add(linhaFaixaConsumo);

								/*
								 * Caso tenha percorrido todas as faixas, acrescenta a linha de
								 * TOTAL REAL e realiza o ajuste da última faixa
								 */
								if(!iteratorContaCategoriaConsumoFaixa.hasNext()){

									// Preparar total da categoria
									// Índice de tarifas = Índice de tarifas + 1
									indexTarifas++;
									linhaFaixaConsumo = new DescricaoServicosContaHelper();

									linhaFaixaConsumo
													.setTotalizacao(Util.completarStringComValorEsquerda("TOTAL REAL", " ", 19)
																	+ Util.completarStringComValorEsquerda(Util.formataBigDecimal(
																					contaCategoria.getValorAgua(), 2, true), " ", 20));

									/*
									 * Descrição das Tarifas/Débitos/Créditos (Índice de
									 * tarifas/débitos/créditos)
									 */
									linhasDescricaoServicosConta.add(linhaFaixaConsumo);

									// [SB0032] – Verificar Ajuste Valor Última Faixa.
									verificarAjusteValorUltimaFaixa(linhasDescricaoServicosConta, contaCategoria, linhaFaixaConsumo,
													somatorioValorConsumoFaixa);

									// Passa para Próxima Categoria
									break;
								}

							}else{

								/*
								 * Caso contrário, ou seja, o Índice de tarifas seja igual ao
								 * limite
								 * de
								 * emissão de tarifas na conta menos 3 (três) (reservar 3 linhas
								 * para total da categoria, total demais categorias e valor de
								 * esgoto) menos 2 (dois), caso existam débitos cobrados (reservar 2
								 * linhas para os débitos), menos 1 (um), caso existam créditos
								 * realizados (reservar 1 linha para os créditos), menos 1 (um),
								 * caso existam impostos retidos (reservar 1 linha para os impostos)
								 */

								/*
								 * Formata a Categoria (Índice de tarifas) = Descrição abreviada
								 * da
								 * categoria , caso seja a primeira ocorrência da categoria nas
								 * faixas
								 * de consumo da conta; caso contrário, atribuir o valor nulo à
								 * Categoria (Índice de tarifas).
								 */
								if(primeiraOcorrenciaCategoriaFaixa){

									linhaFaixaConsumo.setCategoria(Util.completarStringComValorEsquerda(contaCategoria.getComp_id()
													.getCategoria().getDescricaoAbreviada(), " ", 8));
									primeiraOcorrenciaCategoriaFaixa = false;
								}else{

									linhaFaixaConsumo.setCategoria(Util.completaString("", 8));
								}

								/*
								 * Faixa de Consumo (Índice de tarifas) = “ACIMA DE” + Faixa
								 * Inicial
								 * de
								 * Consumo
								 */
								linhaFaixaConsumo.setFaixa(" ACIMA DE"
												+ Util.completarStringComValorEsquerda(contaCategoriaConsumoFaixa.getConsumoFaixaInicio()
																.toString(), " ", 8));

								/*
								 * Valor de Consumo na Faixa (Índice de tarifas) = valor de água
								 * da
								 * conta (CTCG_VLAGUA da tabela CONTA_CATEGORIA) menos os
								 * valores já
								 * acumulados para a categoria (somatório (Valor de Consumo na
								 * Faixa) na
								 * categoria)
								 */
								linhaFaixaConsumo.setValorConsumo(Util.completarStringComValorEsquerda(Util.formataBigDecimal(
												contaCategoria.getValorAgua().subtract(somatorioValorConsumoFaixa), 2, true), " ", 14));
								linhaFaixaConsumo.setValorConsumoSemFormatacao(contaCategoria.getValorAgua().subtract(
												somatorioValorConsumoFaixa));

								/*
								 * Descrição das Tarifas/Débitos/Créditos (Índice de
								 * tarifas/débitos/créditos) = Tarifa (Índice de tarifas) +
								 * Categoria
								 * (Índice de tarifas) + Faixa de Consumo (Índice de tarifas) +
								 * Valor da
								 * Tarifa na Faixa (Índice de tarifas) + Valor de Consumo na
								 * Faixa
								 * (Índice
								 * de tarifas).
								 */
								linhasDescricaoServicosConta.add(linhaFaixaConsumo);

								// Preparar total da categoria
								// Índice de tarifas = Índice de tarifas + 1
								indexTarifas++;
								linhaFaixaConsumo = new DescricaoServicosContaHelper();

								linhaFaixaConsumo.setTotalizacao(Util.completarStringComValorEsquerda("TOTAL REAL", " ", 19)
												+ Util.completarStringComValorEsquerda(Util.formataBigDecimal(
																contaCategoria.getValorAgua(), 2, true), " ", 20));

								/*
								 * Descrição das Tarifas/Débitos/Créditos (Índice de
								 * tarifas/débitos/créditos)
								 */
								linhasDescricaoServicosConta.add(linhaFaixaConsumo);

								// [SB0032] – Verificar Ajuste Valor Última Faixa.
								verificarAjusteValorUltimaFaixa(linhasDescricaoServicosConta, contaCategoria, linhaFaixaConsumo,
												somatorioValorConsumoFaixa);

								/*
								 * Preparar total das demais categorias, caso ainda existam
								 * categorias a
								 * processar
								 */
								if(iteratorContaCategoria.hasNext()){

									colecaoContaCategoriaNaoProcessadas.add(contaCategoria);
								}

								break;
							}
						}
					}
				}else{

					/*
					 * Preparar total das demais categorias, caso ainda existam
					 * categorias a
					 * processar
					 */
					if(iteratorContaCategoria.hasNext()){

						colecaoContaCategoriaNaoProcessadas.add(contaCategoria);
					}
				}
			}

			// caso ainda restem faixas/categorias a processar
			if(!Util.isVazioOrNulo(colecaoContaCategoriaNaoProcessadas)){

				DescricaoServicosContaHelper linhaFaixaConsumo = new DescricaoServicosContaHelper();

				// Categoria (Índice de tarifas) = “OUTRAS”
				linhaFaixaConsumo.setCategoria("OUTRAS ");

				/*
				 * Valor de Consumo na Faixa (Índice de tarifas) = somatório dos valores de água das
				 * categorias ainda não processadas valor de água da conta
				 */
				BigDecimal valorAguaAcumulado = new BigDecimal(0);
				for(ContaCategoria contaCategoriaNaoProcessada : colecaoContaCategoriaNaoProcessadas){

					valorAguaAcumulado = valorAguaAcumulado.add(contaCategoriaNaoProcessada.getValorAgua());
				}

				linhaFaixaConsumo.setValorConsumo(Util.completarStringComValorEsquerda(Util.formataBigDecimal(valorAguaAcumulado, 2, true),
								" ", 32));

				/*
				 * Descrição das Tarifas/Débitos/Créditos (Índice de
				 * tarifas/débitos/créditos) = Tarifa (Índice de tarifas) +
				 * Categoria
				 * (Índice de tarifas) + Faixa de Consumo (Índice de tarifas) +
				 * Valor da
				 * Tarifa na Faixa (Índice de tarifas) + Valor de Consumo na Faixa
				 * (Índice
				 * de tarifas).
				 */
				linhasDescricaoServicosConta.add(linhaFaixaConsumo);
			}
		}

		contaEmitirHelper.setIndexDescricaoTarifasServicos(indexTarifas);
		return linhasDescricaoServicosConta;
	}

	/**
	 * [UC0352] Emitir Contas
	 * [SB0026] – Gerar Linha da Tarifa de Esgoto
	 * 
	 * @author Anderson Italo
	 * @data 25/08/2011
	 * @param contaEmitirHelper
	 *            , linhasDescricaoServicosConta
	 * @throws ControladorException
	 */
	private List<DescricaoServicosContaHelper> gerarLinhaTarifaEsgoto(EmitirContaTipo2Helper contaEmitirHelper,
					List<DescricaoServicosContaHelper> linhasDescricaoServicosConta) throws ControladorException{

		DescricaoServicosContaHelper linhaTarifaEsgoto = new DescricaoServicosContaHelper();

		/*
		 * Caso o consumo de água seja igual ao volume coletado de esgoto (CNTA_NNCONSUMOAGUA =
		 * CNTA_NNCONSUMOESGOTO) e o valor de água seja diferente de zero (CNTA_VLAGUA diferente de
		 * zero):
		 */
		if((contaEmitirHelper.getConsumoAgua().intValue() == contaEmitirHelper.getConsumoEsgoto().intValue())
						&& !(contaEmitirHelper.getValorAgua().compareTo(BigDecimal.valueOf(0)) == 0)){

			/*
			 * Constante “TAR.ESG=”.
			 * Percentual de esgoto (CNTA_PCESGOTO).
			 * Constante “% TAR.ÁGUA ”.
			 */
			linhaTarifaEsgoto.setDescricaoEsgoto("TAR.ESG="
							+ Util.completaStringComEspacoAEsquerda(Util.formatarPercentual(contaEmitirHelper.getPercentualEsgotoConta()
											.doubleValue(), 2), 6) + "% TAR.ÁGUA ");
			// Valor de Esgoto
			linhaTarifaEsgoto.setValorEsgoto(Util.completarStringComValorEsquerda(Util.formataBigDecimal(
							contaEmitirHelper.getValorEsgoto(), 2, true), " ", 14));
		}else{

			/*
			 * Constante “TAR.ESG. REF. À ”.
			 * Volume coletado de esgoto (CNTA_NNCONSUMOESGOTO).
			 * Constante “M3”
			 */
			linhaTarifaEsgoto.setDescricaoEsgoto("TAR.ESG. REF. À "
							+ Util.completarStringComValorEsquerda(Util.retiraValoresNaoSignificativosAEsquerda(Util
											.completarStringComValorEsquerda(contaEmitirHelper.getConsumoEsgoto().toString(), "0", 6),
											new String[] {"0"}, 3), " ", 6) + "M3 ");
			// Valor de Esgoto
			linhaTarifaEsgoto.setValorEsgoto(Util.completarStringComValorEsquerda(Util.formataBigDecimal(
							contaEmitirHelper.getValorEsgoto(), 2, true), " ", 14));
		}

		linhasDescricaoServicosConta.add(linhaTarifaEsgoto);

		return linhasDescricaoServicosConta;
	}

	/**
	 * [UC0352] Emitir Contas
	 * [SB0027] – Gerar Linhas dos Débitos Cobrados
	 * 
	 * @author Anderson Italo
	 * @data 25/08/2011
	 * @param contaEmitirHelper
	 *            , linhasDescricaoServicosConta, colecaoDebitoCobrado, limitacaoIndex
	 * @throws ErroRepositorioException
	 *             , ControladorException
	 */
	private List<DescricaoServicosContaHelper> gerarLinhasDebitosCobrados(EmitirContaTipo2Helper contaEmitirHelper,
					List<DescricaoServicosContaHelper> linhasDescricaoServicosConta, Collection<DebitoCobrado> colecaoDebitoCobrado,
					Collection<DebitoCobrado> colecaoDebitoCobradoParcelamento, int limitacaoIndex) throws ErroRepositorioException,
					ControladorException{

		/*
		 * O sistema inicializa com nulos a tabela de Descrição do Débito (índice de débitos) e
		 * Valor do Débito (índice de débitos) variando o índice de 1 (um) até o limite de emissão
		 * de tarifas na débitos
		 */
		int limite = Util.obterInteger((String) ParametroFaturamento.P_LIMITE_EMISSAO_TARIFAS_CONTA.executar(this));
		int indexDebitos = contaEmitirHelper.getIndexDescricaoTarifasServicos();

		BigDecimal valorTotalDebitosParcelamento = somarDebitosCobradosContaEmissaoTxt(colecaoDebitoCobradoParcelamento);

		boolean possuiDebitosParcelamento = false;
		if(valorTotalDebitosParcelamento.compareTo(BigDecimal.valueOf(0)) == 1){

			possuiDebitosParcelamento = true;
		}else{

			limitacaoIndex--;
		}

		Collection<DebitoCobrado> colecaoDebitoCobradoRestantes = new ArrayList<DebitoCobrado>();

		if(!Util.isVazioOrNulo(colecaoDebitoCobrado)){
			for(DebitoCobrado debitoCobrado : colecaoDebitoCobrado){

				DescricaoServicosContaHelper linhaDebitoCobrado = new DescricaoServicosContaHelper();

				// Índice de débitos = Índice de débitos + 1
				indexDebitos++;

				/*
				 * Caso o Índice de débitos seja menor que o limite de emissão de débitos na conta
				 * menos 1 (um) (reservar 1 linha para os débitos de parcelamento), caso existam
				 * débitos
				 * de parcelamento, menos 1 (um), caso existam créditos realizados (reservar 1 linha
				 * para os créditos), menos 1 (um), caso existam impostos retidos (reservar 1 linha
				 * para
				 * os impostos)
				 */
				if(indexDebitos < (limite - limitacaoIndex)){

					// Descrição do Débito (Índice de débitos) = Descrição do tipo de débito
					linhaDebitoCobrado.setDescricaoDebito(Util.completaString(debitoCobrado.getDebitoTipo().getDescricao(), 27));

					/*
					 * Valor do Débito (Índice de débitos) = Valor do débito (DBCB_VLPRESTACAO da
					 * tabela DEBITO_COBRADO)
					 */
					linhaDebitoCobrado.setValorDebito(Util.completarStringComValorEsquerda(
									Util.formataBigDecimal(debitoCobrado.getValorPrestacao(), 2, true), " ", 12));

					/*
					 * Descrição das Tarifas/Débitos/Créditos (Índice de tarifas/débitos/créditos) =
					 * Descrição do Débito (Índice de débitos) + Valor do Débito (Índice de
					 * débitos).
					 */
					linhasDescricaoServicosConta.add(linhaDebitoCobrado);

				}else{

					/*
					 * Caso contrário, o índice de débitos seja igual ao limite de emissão de
					 * débitos na conta menos 1 (um), caso existam créditos realizados (reservar 1
					 * linha
					 * para os créditos), menos 1 (um), caso existam impostos retidos (reservar 1
					 * linha
					 * para os impostos)
					 */
					colecaoDebitoCobradoRestantes.add(debitoCobrado);
				}
			}
		}

		if(!colecaoDebitoCobradoRestantes.isEmpty()){

			DescricaoServicosContaHelper linhaDebitoCobrado = new DescricaoServicosContaHelper();

			/* Descrição do Débito (Índice de débitos) = “OUTROS DÉBITOS DE SERVIÇOS” */
			linhaDebitoCobrado.setDescricaoDebito("OUTROS DÉBITOS DE SERVIÇOS ");

			/*
			 * Valor do Débito (Índice de débitos) = somatório dos débitos cobrados
			 * restantes para a conta
			 */
			BigDecimal somatorioDebitosRestantes = new BigDecimal(0);
			for(DebitoCobrado debitoCobradoRestante : colecaoDebitoCobradoRestantes){

				somatorioDebitosRestantes = somatorioDebitosRestantes.add(debitoCobradoRestante.getValorPrestacao());
			}

			linhaDebitoCobrado.setValorDebito(Util.completarStringComValorEsquerda(Util.formataBigDecimal(somatorioDebitosRestantes, 2,
							true), " ", 12));

			linhasDescricaoServicosConta.add(linhaDebitoCobrado);
		}

		// Caso o valor total dos débitos cobrados de parcelamento para a conta seja maior que zero
		if(possuiDebitosParcelamento){

			DescricaoServicosContaHelper linhaDebitoCobrado = new DescricaoServicosContaHelper();

			// 6.3. Caso exista, em algum dos débitos cobrados, a identificação do parcelamento
			// (PARC_ID com o valor diferente de nulo):
			if(this.existeDebitoCobradoIdentificacaoParcelamento(colecaoDebitoCobradoParcelamento)){

				Boolean existeRDsDistintas = this.existemRDsDistintas(colecaoDebitoCobradoParcelamento);
				Boolean existeRDsIndicacaoEmissaoAssuntoConta = this
								.existemRDsIndicacaoEmissaoAssuntoConta(colecaoDebitoCobradoParcelamento);

				// 6.3.1. Obter as RDs dos parcelamentos identificados (a partir da tabela
				// RESOLUCAO_DIRETORIA com RDIR_ID=RDIR_ID da tabela PARCELAMENTO com PARC_ID
				// contido em
				// algum dos PARC_ID da tabela DEBITO_COBRADO).
				// 6.3.2. Caso existam RDs com a indicação de emissão do assunto na conta
				// (RDIR_ICEMISSAOASSUNTOCONTA=1) e sejam RDs distintas ou caso não existam RDs com
				// a
				// indicação de emissão do assunto na conta:
				// 6.3.2.1. Descrição do Débito (Índice de débitos) = “PARCELAMENTO DE DÉBITOS”.

				if(existeRDsDistintas || !existeRDsIndicacaoEmissaoAssuntoConta){

					linhaDebitoCobrado.setDescricaoDebito(Util.completaString("PARCELAMENTO DE DÉBITOS", 27));

				}else if(existeRDsIndicacaoEmissaoAssuntoConta && !existeRDsDistintas){

					// 6.3.3. Caso contrário, ou seja, caso existam RDs com a indicação de emissão
					// do
					// assunto na conta e não sejam RDs distintas:
					// 6.3.3.1. Descrição do Débito (Índice de débitos) = “PARCELAM. “ (10
					// caracteres) mais
					// três primeiros caracteres do assunto da RD (RDIR_DSASSUNTO) mais um espaço em
					// branco
					// mais o valor do débito correspondente aos débitos cobrados de parcelamento da
					// RD
					// (somatório de DBCB_VLPRESTACAO no formato ZZ.ZZ9,99).

					DebitoCobrado dc = null;

					if(colecaoDebitoCobradoParcelamento != null){

						dc = colecaoDebitoCobradoParcelamento.iterator().next();

					}

					if(dc != null && dc.getParcelamento() != null && dc.getParcelamento().getResolucaoDiretoria() != null){

						String descricaoDebito = "PARCELAM. "
										+ Util.completarStringCaractereDireita(dc.getParcelamento().getResolucaoDiretoria()
														.getDescricaoAssunto(), 3, ' ')
										+ Util.completaStringComEspacoAEsquerda(Util.formatarMoedaReal(valorTotalDebitosParcelamento), 9);

						linhaDebitoCobrado.setDescricaoDebito(Util.completaString(descricaoDebito, 27));

					}

				}

			}else{

				// 6.4. Caso contrário, ou seja, nenhum dos débitos cobrados têm a identificação do
				// parcelamento:
				// 6.4.1. Descrição do Débito (Índice de débitos) = “PARCELAMENTO DE DÉBITOS”.
				linhaDebitoCobrado.setDescricaoDebito(Util.completaString("PARCELAMENTO DE DÉBITOS", 27));

			}

			linhaDebitoCobrado.setValorDebito(Util.completarStringComValorEsquerda(Util.formataBigDecimal(valorTotalDebitosParcelamento, 2,
							true), " ", 12));
			linhasDescricaoServicosConta.add(linhaDebitoCobrado);

		}

		return linhasDescricaoServicosConta;

	}

	private BigDecimal somarDebitosCobradosContaEmissaoTxt(Collection<DebitoCobrado> debitosCobrados){

		BigDecimal soma = BigDecimal.ZERO;

		if(!Util.isVazioOrNulo(debitosCobrados)){
			for(DebitoCobrado debitoCobrado : debitosCobrados){
				soma = soma.add(debitoCobrado.getValorPrestacao());
			}
		}

		return soma;
	}

	/**
	 * [UC0352] Emitir Contas
	 * [SB0028] – Gerar Linhas dos Créditos Realizados
	 * 
	 * @author Anderson Italo
	 * @data 26/08/2011
	 * @param contaEmitirHelper
	 *            , linhasDescricaoServicosConta, colecaoCreditoRealizado, limitacaoIndex
	 * @throws ErroRepositorioException
	 *             , ControladorException
	 */
	private List<DescricaoServicosContaHelper> gerarLinhasCreditosRealizados(EmitirContaTipo2Helper contaEmitirHelper,
					List<DescricaoServicosContaHelper> linhasDescricaoServicosConta, Collection<CreditoRealizado> colecaoCreditoRealizado,
					int limitacaoIndex) throws ErroRepositorioException, ControladorException{

		/*
		 * O sistema inicializa com nulos a tabela de Descrição do Crédito (índice de créditos) e
		 * Valor do Crédito (índice de créditos) variando o índice de 1 (um) até o limite de emissão
		 * de créditos na conta
		 */
		int limite = Util.obterInteger((String) ParametroFaturamento.P_LIMITE_EMISSAO_TARIFAS_CONTA.executar(this));
		int indexCreditos = contaEmitirHelper.getIndexDescricaoTarifasServicos();

		Collection<CreditoRealizado> colecaoCreditosRealizadosRestantes = new ArrayList<CreditoRealizado>();

		for(CreditoRealizado creditoRealizado : colecaoCreditoRealizado){

			DescricaoServicosContaHelper linhaCreditoRealizado = new DescricaoServicosContaHelper();

			// Índice de créditos = Índice de créditos + 1
			indexCreditos++;

			/*
			 * Caso o Índice de créditos seja menor que o limite de emissão de créditos na conta
			 * menos 1 (um), caso existam impostos retidos (reservar 1 linha para os impostos)
			 */
			if(indexCreditos < (limite - limitacaoIndex)){

				// Descrição do Crédito (Índice de créditos) = Descrição do tipo de crédito
				linhaCreditoRealizado.setDescricaoCredito(Util.completaString(creditoRealizado.getCreditoTipo().getDescricao(), 27));

				// Valor do crédito (CRRZ_VLCREDITO da tabela CREDITO_REALIZADO).
				linhaCreditoRealizado.setValorCredito(Util.completarStringComValorEsquerda(Util.formataBigDecimal(creditoRealizado
								.getValorCredito(), 2, true), " ", 12));

				/*
				 * Descrição das Tarifas/Débitos/Créditos (Índice de tarifas/débitos/créditos) =
				 * Descrição do Crédito (Índice de créditos) + Valor do Crédito (Índice de
				 * créditos).
				 */
				linhasDescricaoServicosConta.add(linhaCreditoRealizado);

			}else{

				/*
				 * Caso contrário, ou seja, o Índice de créditos seja igual ao limite de emissão
				 * de créditos na conta menos 1 (um), caso existam impostos retidos (reservar 1
				 * linha para os impostos)
				 */
				colecaoCreditosRealizadosRestantes.add(creditoRealizado);
			}
		}

		if(!colecaoCreditosRealizadosRestantes.isEmpty()){

			DescricaoServicosContaHelper linhaCreditoRealizado = new DescricaoServicosContaHelper();

			// Descrição do Crédito (Índice de créditos) = “OUTROS CRÉDITOS”.
			linhaCreditoRealizado.setDescricaoCredito(Util.completaString("OUTROS CRÉDITOS", 27));

			/*
			 * Valor do Crédito (Índice de créditos) = somatório dos créditos realizados
			 * restantes para a conta
			 */
			BigDecimal somatorioCreditoRelizadosRestantes = new BigDecimal(0);
			for(CreditoRealizado creditoRealizadoRestante : colecaoCreditosRealizadosRestantes){

				somatorioCreditoRelizadosRestantes = somatorioCreditoRelizadosRestantes.add(creditoRealizadoRestante.getValorCredito());
			}

			linhaCreditoRealizado.setValorCredito(Util.completarStringComValorEsquerda(Util.formataBigDecimal(
							somatorioCreditoRelizadosRestantes, 2, true), " ", 12));

			/*
			 * Descrição das Tarifas/Débitos/Créditos (Índice de tarifas/débitos/créditos) =
			 * Descrição do Crédito (Índice de créditos) + Valor do Crédito (Índice de
			 * créditos).
			 */
			linhasDescricaoServicosConta.add(linhaCreditoRealizado);
		}

		return linhasDescricaoServicosConta;
	}

	/**
	 * [UC0352] Emitir Contas
	 * [SB0029] – Gerar Linhas dos Impostos Retidos
	 * 
	 * @author Anderson Italo
	 * @data 26/08/2011
	 * @param contaEmitirHelper
	 *            , linhasDescricaoServicosConta, colecaoContaImpostosDeduzidos
	 * @throws ErroRepositorioException
	 *             , ControladorException
	 */
	private List<DescricaoServicosContaHelper> gerarLinhasImpostosRetidos(EmitirContaTipo2Helper contaEmitirHelper,
					List<DescricaoServicosContaHelper> linhasDescricaoServicosConta,
					Collection<ContaImpostosDeduzidos> colecaoContaImpostosDeduzidos) throws ErroRepositorioException, ControladorException{

		/*
		 * O sistema inicializa com nulos a tabela de Descrição do Imposto (índice de impostos) e
		 * Valor do Imposto (índice de impostos) variando o índice de 1 (um) até o limite de emissão
		 * de impostos na conta
		 */
		int limite = Util.obterInteger((String) ParametroFaturamento.P_LIMITE_EMISSAO_TARIFAS_CONTA.executar(this));
		int indexImpostos = contaEmitirHelper.getIndexDescricaoTarifasServicos();

		Collection<ContaImpostosDeduzidos> colecaoContaImpostosDeduzidosRestantes = new ArrayList<ContaImpostosDeduzidos>();

		for(ContaImpostosDeduzidos contaImpostosDeduzidos : colecaoContaImpostosDeduzidos){

			DescricaoServicosContaHelper linhaCreditoRealizado = new DescricaoServicosContaHelper();

			// Índice de impostos = Índice de impostos + 1.
			indexImpostos++;

			// Caso o Índice de impostos seja menor que o limite de emissão de impostos na conta
			if(indexImpostos < limite){

				// Descrição do Imposto (Índice de impostos) = Descrição do tipo de imposto
				linhaCreditoRealizado.setDescricaoImposto(Util.completaString(contaImpostosDeduzidos.getImpostoTipo().getDescricao(), 27));

				// Valor do Imposto (Índice de impostos) = Valor do imposto (CNID_VLIMPOSTO
				linhaCreditoRealizado.setValorImposto(Util.completarStringComValorEsquerda(Util.formataBigDecimal(contaImpostosDeduzidos
								.getValorImposto(), 2, true), " ", 12));

				/*
				 * Descrição das Tarifas/Débitos/Créditos (Índice de
				 * tarifas/débitos/créditos) = Descrição do Imposto (Índice de impostos) + Valor
				 * do Imposto (Índice de impostos)
				 */
				linhasDescricaoServicosConta.add(linhaCreditoRealizado);

			}else{

				/*
				 * Caso contrário, ou seja, o Índice de impostos seja igual ao limite de emissão
				 * de impostos na conta
				 */
				colecaoContaImpostosDeduzidosRestantes.add(contaImpostosDeduzidos);
			}
		}

		if(!colecaoContaImpostosDeduzidosRestantes.isEmpty()){

			DescricaoServicosContaHelper linhaCreditoRealizado = new DescricaoServicosContaHelper();

			// Descrição do Imposto (Índice de impostos) = “OUTROS IMPOSTOS”
			linhaCreditoRealizado.setDescricaoCredito(Util.completaString("OUTROS IMPOSTOS", 27));

			/*
			 * Valor do Imposto (Índice de impostos) = somatório dos impostos deduzidos
			 * restantes para a conta
			 */
			BigDecimal somatorioImpostosDeduzidosRestantes = new BigDecimal(0);
			for(ContaImpostosDeduzidos contaImpostosDeduzidosRestante : colecaoContaImpostosDeduzidosRestantes){

				somatorioImpostosDeduzidosRestantes = somatorioImpostosDeduzidosRestantes.add(contaImpostosDeduzidosRestante
								.getValorImposto());
			}

			linhaCreditoRealizado.setValorImposto(Util.completarStringComValorEsquerda(Util.formataBigDecimal(
							somatorioImpostosDeduzidosRestantes, 2, true), " ", 12));

			/*
			 * Descrição das Tarifas/Débitos/Créditos (Índice de tarifas/débitos/créditos) =
			 * Descrição do Imposto (Índice de impostos) + Valor do Imposto (Índice de impostos)
			 */
			linhasDescricaoServicosConta.add(linhaCreditoRealizado);
		}

		return linhasDescricaoServicosConta;
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
		}else{

			retorno = Util.completaString("", 24);
		}

		return retorno;
	}

	/**
	 * [UC0352] Emitir Contas
	 * Obter os Ultimos cinco anos anteriores ao ano de referencia do faturamento, com conta
	 * vencida.
	 * 
	 * @author Anderson Italo
	 * @date 29/08/2011
	 * @param idImovel
	 * @param anoFaturamento
	 * @throws ControladorException
	 *             , ErroRepositorioException
	 */
	private String obterUltimosCincoAnosAnterioresReferenciaComContaVencida(Integer idImovel, Integer anoMesFaturamento)
					throws ControladorException, ErroRepositorioException{

		String retorno = "";
		Integer anoFaturamento = Util.converterStringParaInteger(anoMesFaturamento.toString().substring(0, 4)) - 1;
		int index = 0;

		Collection colecaoAnos = repositorioFaturamento.obterUltimosCincoAnosAnterioresReferenciaComContaVencida(idImovel, anoFaturamento);

		if(colecaoAnos != null){

			Iterator itAnos = colecaoAnos.iterator();
			Integer anoAtual;

			while(itAnos.hasNext()){

				anoAtual = (Integer) itAnos.next();
				index++;

				retorno = retorno + Util.completaString(anoAtual.toString(), 5);
			}

			if(retorno.length() != 25){

				retorno = retorno + Util.completaString("", 25 - retorno.length());
			}
		}else{

			retorno = Util.completaString("", 25);
		}

		return retorno;
	}

	/**
	 * [UC0352] Emitir Contas
	 * [SB0032] – Verificar Ajuste Valor Última Faixa
	 * 
	 * @author Anderson Italo
	 * @data 29/09/2011
	 * @param linhasDescricaoServicosConta
	 * @param contaCategoria
	 * @param linhaFaixaConsumo
	 * @param somatorioValorConsumoFaixa
	 * @throws ControladorException
	 */
	private void verificarAjusteValorUltimaFaixa(List<DescricaoServicosContaHelper> linhasDescricaoServicosConta,
					ContaCategoria contaCategoria, DescricaoServicosContaHelper linhaFaixaConsumo, BigDecimal somatorioValorConsumoFaixa)
					throws ControladorException{

		/*
		 * Caso os valores já acumulados para a categoria (somatório
		 * (Valor de Consumo na Faixa) na categoria) sejam diferentes do
		 * valor total da categoria
		 */
		if(!(somatorioValorConsumoFaixa.compareTo(contaCategoria.getValorAgua()) == 0)){

			BigDecimal diferenca = new BigDecimal(0);
			DescricaoServicosContaHelper ultimaOcorrenciaDescricaoServicosContaHelper = new DescricaoServicosContaHelper();
			/*
			 * adicionar ou subtrair a diferença na última ocorrência da
			 * categoria
			 */
			ultimaOcorrenciaDescricaoServicosContaHelper = linhasDescricaoServicosConta.get(linhasDescricaoServicosConta.size() - 2);

			if(somatorioValorConsumoFaixa.compareTo(contaCategoria.getValorAgua()) == 1){

				diferenca = somatorioValorConsumoFaixa.subtract(contaCategoria.getValorAgua());
				ultimaOcorrenciaDescricaoServicosContaHelper.setValorConsumo(Util.completarStringComValorEsquerda(Util.formataBigDecimal(
								ultimaOcorrenciaDescricaoServicosContaHelper.getValorConsumoSemFormatacao().subtract(diferenca), 2, true),
								" ", 14));
			}else{

				diferenca = contaCategoria.getValorAgua().subtract(somatorioValorConsumoFaixa);
				ultimaOcorrenciaDescricaoServicosContaHelper.setValorConsumo(Util.completarStringComValorEsquerda(Util.formataBigDecimal(
								ultimaOcorrenciaDescricaoServicosContaHelper.getValorConsumoSemFormatacao().add(diferenca), 2, true), " ",
								14));
			}

			linhasDescricaoServicosConta.set(linhasDescricaoServicosConta.size() - 2, ultimaOcorrenciaDescricaoServicosContaHelper);
		}
	}

	/**
	 * [UC0352] - Emitir Contas
	 * [SB0100] – Gerar Arquivo TXT Contas – Modelo 2
	 * Responsável pela geração de arquivo tipo TXT com as contas, de acordo com o Modelo 2
	 * 
	 * @author Luciano Galvao
	 * @date 21/06/2012
	 * @throws ErroRepositorioException
	 * @throws ControladorException
	 * @throws IOException
	 * @throws CreateException
	 */
	public void execParamGerarArquivoTxtContasModelo2(ParametroSistema parametroSistema, List<EmitirContaTipo2Helper> listaConta,
					FaturamentoGrupo faturamentoGrupo, Integer idEmpresa, Usuario usuario,
					Collection<EmitirContaTipo2Helper> listaContaEndAlternativo, ComparatorChain sortListaConta,
					ComparatorChain sortListaContaEndAlternativo, Collection<EmitirContaTipo2Helper> listaContaBraille)
					throws ControladorException, ErroRepositorioException, IOException,
					CreateException{

		System.out.println("+++++++++++++++++++++++++++++++");
		System.out.println("Gerando o arquivo txt");
		System.out.println("+++++++++++++++++++++++++++++++");

		this.ejbCreate();
		StringBuilder contaTxt = new StringBuilder();

		// Gera a primeira linha do arquivo
		gerarPrimeiraLinhaContasModelo2(contaTxt);

		gerarLinhasContasModelo2(listaConta, contaTxt, faturamentoGrupo.getAnoMesReferencia());

		// Gera a última linha do arquivo
		gerarUltimasLinhasContasModelo2(contaTxt);

		String nomeArquivo = "CONTAS_EMPRESA_" + idEmpresa.toString() + "_g" + faturamentoGrupo.getId() + "_"
						+ faturamentoGrupo.getAnoMesReferencia().toString() + ".txt";

		// Envia o arquivo para ser gerado como um relatório batch
		enviarArquivoEmissaoContasParaBatch(contaTxt, nomeArquivo, usuario);

		System.out.println("+++++++++++++++++++++++++++++++");
		System.out.println("Arquivo enviado para o Batch");
		System.out.println("+++++++++++++++++++++++++++++++");
	}

	/**
	 * [UC0352] Emitir Contas
	 * Gera as duas últimas linhas de controle do arquivo
	 * 
	 * @param contaTxt
	 */
	protected void gerarUltimasLinhasContasModelo2(StringBuilder contaTxt){

		contaTxt.append(Util.completaString("FINAL DO ARQUIVO - CA2FLASR", 62));
		contaTxt.append(Util.completaString("", 2176));
		contaTxt.append(Util.completaStringComEspacoAEsquerda("000000", 6));
		contaTxt.append(SEPARADOR_LINHA);
		contaTxt.append(Util.completaString("FINAL DO ARQUIVO - CA2FLASR", 62));
		contaTxt.append(Util.completaString("", 2176));
		contaTxt.append(Util.completaStringComEspacoAEsquerda("000000", 6));
	}

	/**
	 * [UC0352] Emitir Contas
	 * Gera as linhas de contas
	 * 
	 * @param listaConta
	 * @param contaTxt
	 * @throws ControladorException
	 */
	private void gerarLinhasContasModelo2(List<EmitirContaTipo2Helper> listaConta, StringBuilder contaTxt, Integer anoMesReferencia)
					throws ControladorException{

		System.out.println("+++++++++++++++++++++++++++++++");
		System.out.println("Iniciando geração do arquivo");
		System.out.println("+++++++++++++++++++++++++++++++");

		// Gera as linhas do arquivo com informações das contas
		for(EmitirContaTipo2Helper contaEmitirHelper : listaConta){

			gerarDadosGeraisContasModelo2(contaTxt, contaEmitirHelper);
			gerarServicosCobradosContasModelo2(contaTxt, contaEmitirHelper);
			gerarDebitosAnterioresContasModelo2(contaTxt, contaEmitirHelper, anoMesReferencia);
			gerarUltimosConsumosLeiturasContasModelo2(contaTxt, contaEmitirHelper, anoMesReferencia);

			contaTxt.append(SEPARADOR_LINHA);
		}

		System.out.println("+++++++++++++++++++++++++++++++");
		System.out.println("Geração do arquivo concluída");
		System.out.println("+++++++++++++++++++++++++++++++");
	}

	/**
	 * [UC0352] Emitir Contas
	 * Gera as informações de últimos consumos e leituras para o arquivo de contas emitidas - modelo
	 * 2
	 * Itens 82 a 164 do UC0352
	 * 
	 * @param contaTxt
	 * @param contaEmitirHelper
	 * @throws ControladorException
	 * @throws NumberFormatException
	 */
	private void gerarUltimosConsumosLeiturasContasModelo2(StringBuilder contaTxt, EmitirContaTipo2Helper contaEmitirHelper,
					Integer anoMesReferencia) throws NumberFormatException, ControladorException{

		String idLocalidade = "";
		String idCdSetorComercial = "";
		String idNumeroQuadra = "";
		String idNumeroLote = "";
		String mensagem1 = "";
		String mensagem2 = "";
		String mensagem3 = "";
		String numeracaoCodigoBarras = "";
		String mensagemDebitoAutomatico = "";
		String codigoBarras = "";
		String referencia = "";
		String digitoModulo11 = "";
		String codigoResponsavel = "";
		String matricula = "";
		String turbidezExigida = "";
		String turbidezRealizada = "";
		String turbidezAtendida = "";
		String corExigida = "";
		String corRealizada = "";
		String corAtendida = "";
		String cloroExigida = "";
		String cloroRealizada = "";
		String cloroAtendida = "";
		String phExigida = "";
		String phRealizada = "";
		String phAtendida = "";
		String coliformesTotaisExigida = "";
		String coliformesTotaisRealizada = "";
		String coliformesTotaisAtendida = "";
		String coliformeTermoToleranteExigida = "";
		String coliformeTermoToleranteRealizada = "";
		String coliformeTermoToleranteAtendida = "";
		String conclusao = "";
		String sequencia = "";

		// Limpa a coleção para novo processamento
		contaEmitirHelper.setListaUltimosConsumosLeituras(new ArrayList<EmitirContaUltimosConsumosLeiturasHelper>());

		Integer parametroNumeroMesesHistoricoConsumoConta = Integer
						.parseInt(ParametroFaturamento.P_NUMERO_MESES_HISTORICO_CONSUMO_EMISSAO_CONTA.executar());

		Integer anoMesFaturamento = Util.subtrairMesDoAnoMes(anoMesReferencia, parametroNumeroMesesHistoricoConsumoConta);

		Integer idMedicaoHistorico = null;
		ConsumoHistorico consumoHistorico = null;
		MedicaoHistorico medicaoHistorico = null;

		FiltroConsumoHistorico filtroConsumoHistorico = new FiltroConsumoHistorico();
		filtroConsumoHistorico.adicionarParametro(new ParametroSimples(FiltroConsumoHistorico.IMOVEL_ID, contaEmitirHelper.getIdImovel()));
		filtroConsumoHistorico.adicionarParametro(new ParametroSimples(FiltroConsumoHistorico.LIGACAO_TIPO_ID, LigacaoTipo.LIGACAO_AGUA));
		filtroConsumoHistorico.adicionarParametro(new MaiorQue(FiltroConsumoHistorico.ANO_MES_FATURAMENTO, anoMesFaturamento));
		filtroConsumoHistorico.setCampoOrderBy(FiltroConsumoHistorico.ANO_MES_FATURAMENTO);

		Collection colecaoConsumoHistorico = getControladorUtil().pesquisar(filtroConsumoHistorico, ConsumoHistorico.class.getName());

		FiltroMedicaoHistorico filtroMedicaoHistorico = new FiltroMedicaoHistorico();
		filtroMedicaoHistorico.adicionarParametro(new ParametroSimples(FiltroMedicaoHistorico.LIGACAO_AGUA_ID, contaEmitirHelper
						.getIdImovel()));
		filtroMedicaoHistorico.adicionarParametro(new MaiorQue(FiltroMedicaoHistorico.ANO_MES_REFERENCIA_FATURAMENTO, anoMesFaturamento));
		filtroMedicaoHistorico.adicionarCaminhoParaCarregamentoEntidade(FiltroMedicaoHistorico.LEITURA_ANORMALIDADE_FATURADA);
		filtroMedicaoHistorico.setCampoOrderBy(FiltroMedicaoHistorico.ANO_MES_REFERENCIA_FATURAMENTO);

		Collection colecaoMedicaoHistorico = getControladorUtil().pesquisar(filtroMedicaoHistorico, MedicaoHistorico.class.getName());

		if(!Util.isVazioOrNulo(colecaoConsumoHistorico) && !Util.isVazioOrNulo(colecaoMedicaoHistorico)){

			List listaConsumoHistorico = (List) colecaoConsumoHistorico;
			List listaMedicaoHistorico = (List) colecaoMedicaoHistorico;

			if(colecaoConsumoHistorico.size() > listaMedicaoHistorico.size()){
				System.out.println(" ### gerarUltimosConsumosLeiturasContasModelo2 :: colecaoConsumoHistorico.size()="
								+ colecaoConsumoHistorico.size() + " listaMedicaoHistorico.size()=" + listaMedicaoHistorico.size()
								+ " anoMesFaturamento=" + anoMesFaturamento + " contaEmitirHelper.getIdImovel()="
								+ contaEmitirHelper.getIdImovel());
			}

			for(int i = 0; i < colecaoConsumoHistorico.size(); i++){

				consumoHistorico = (ConsumoHistorico) listaConsumoHistorico.get(i);
				medicaoHistorico = (MedicaoHistorico) listaMedicaoHistorico.get(i);

				if(!Util.isVazioOuBranco(medicaoHistorico.getLeituraAnormalidadeFaturamento())){

					idMedicaoHistorico = medicaoHistorico.getLeituraAnormalidadeFaturamento().getId();
				}

				EmitirContaUltimosConsumosLeiturasHelper emitirContaUltimosConsumosLeiturasHelper = new EmitirContaUltimosConsumosLeiturasHelper(
								consumoHistorico.getReferenciaFaturamento(), medicaoHistorico.getLeituraAtualFaturamento(),
								idMedicaoHistorico, consumoHistorico.getNumeroConsumoFaturadoMes());

				contaEmitirHelper.addUltimosConsumosLeituras(emitirContaUltimosConsumosLeiturasHelper);

			}

		}

		// Completa a lista caso a quantidade seja menor que 12.
		if((colecaoConsumoHistorico.size() < QUANTIDADE_ULTIMOS_CONSUMOS_LEITURAS)
						&& (colecaoMedicaoHistorico.size() < QUANTIDADE_ULTIMOS_CONSUMOS_LEITURAS)){

			for(int i = contaEmitirHelper.getListaUltimosConsumosLeituras().size(); i < QUANTIDADE_ULTIMOS_CONSUMOS_LEITURAS; i++){

				EmitirContaUltimosConsumosLeiturasHelper emitirContaUltimosConsumosLeiturasHelper = new EmitirContaUltimosConsumosLeiturasHelper();

				contaEmitirHelper.addUltimosConsumosLeituras(emitirContaUltimosConsumosLeiturasHelper);

			}

		}

		// Item 130 - Localidade - verso
		if(!Util.isVazioOuBranco(contaEmitirHelper.getInscLocalidade())){
			idLocalidade = contaEmitirHelper.getInscLocalidade().toString();
		}

		// Item 131 - Codigo Setor Comercial - verso
		if(!Util.isVazioOuBranco(contaEmitirHelper.getInscSetorComercial())){
			idCdSetorComercial = contaEmitirHelper.getInscSetorComercial().toString();
		}

		// Item 132 - Quadra - verso
		if(!Util.isVazioOuBranco(contaEmitirHelper.getInscQuadra())){
			idNumeroQuadra = contaEmitirHelper.getInscQuadra().toString();
		}

		// Item 133 - Lote - verso
		if(!Util.isVazioOuBranco(contaEmitirHelper.getInscLote())){
			idNumeroLote = contaEmitirHelper.getInscLote().toString();
		}

		if(!Util.isVazioOrNulo(contaEmitirHelper.getMensagemConta())){

			// Item 134 - Mensagem 1
			if(!Util.isVazioOuBranco(contaEmitirHelper.getMensagemConta()[0])){
				mensagem1 = (String) contaEmitirHelper.getMensagemConta()[0];
			}

			// Item 135 - Mensagem 2
			if(!Util.isVazioOuBranco(contaEmitirHelper.getMensagemConta()[1])){
				mensagem2 = (String) contaEmitirHelper.getMensagemConta()[1];
			}

			// Item 136 - Mensagem 3
			if(!Util.isVazioOuBranco(contaEmitirHelper.getMensagemConta()[2])){
				mensagem3 = (String) contaEmitirHelper.getMensagemConta()[2];
			}
		}

		// Item 137 - Numeração código de barras
		if(!Util.isVazioOuBranco(contaEmitirHelper.getRepresentacaoNumericaCodBarraSemDigito())){
			numeracaoCodigoBarras = contaEmitirHelper.getRepresentacaoNumericaCodBarraSemDigito();
		}

		// Item 138 - Mensagem Débito Automático
		if(contaEmitirHelper.getIndicadorDebitoAutomatico().equals(ConstantesSistema.SIM)){
			mensagemDebitoAutomatico = MESNAGEM_DEBITO_AUTOMATICO;
		}

		// Item 139 - código de barras
		if(!Util.isVazioOuBranco(contaEmitirHelper.getRepresentacaoNumericaCodBarraFormatada())){
			codigoBarras = contaEmitirHelper.getRepresentacaoNumericaCodBarraFormatada();
		}

		// Item 140 - Referência
		if(!Util.isVazioOuBranco(contaEmitirHelper.getAnoMesConta())){
			referencia = contaEmitirHelper.getAnoMesConta().toString();
		}

		// Item 141 - Dígito
		if(!Util.isVazioOuBranco(contaEmitirHelper.getDigitoVerificadorConta())){
			digitoModulo11 = Util.obterDigitoVerificadorModulo11(contaEmitirHelper.getDigitoVerificadorConta().toString()).toString();
		}

		// Item 142 - Código Localidade
		// Como é o mesmo campo do item 130, é só repetir na linha do arquivo.

		// Item 143 - Código Responsável
		if(!Util.isVazioOuBranco(contaEmitirHelper.getIdClienteResponsavel())){
			codigoResponsavel = contaEmitirHelper.getIdClienteResponsavel().toString();
		}

		// Item 144 - Matrícula
		if(!Util.isVazioOuBranco(contaEmitirHelper.getIdImovel())){
			matricula = contaEmitirHelper.getIdImovel().toString();
		}

		SistemaParametro sistemaParametro = getControladorUtil().pesquisarParametrosDoSistema();

		QualidadeAgua qualidadeAgua = null;

		try{

			repositorioFaturamento.pesquisarQualidadeAguaPorLocalidadeAnoMesFaturamento(sistemaParametro.getAnoMesFaturamento(),
							contaEmitirHelper.getInscLocalidade());

			if(qualidadeAgua == null && contaEmitirHelper.getCodigoElo() != null){

				qualidadeAgua = repositorioFaturamento.pesquisarQualidadeAguaPorLocalidadeAnoMesFaturamento(sistemaParametro
								.getAnoMesFaturamento(), contaEmitirHelper.getCodigoElo());
			}

		}catch(ErroRepositorioException e){
			e.printStackTrace();
			throw new ControladorException("erro.sistema", e);
		}

		FiltroQualidadeAguaPadrao filtroQualidadeAguaPadrao = new FiltroQualidadeAguaPadrao();

		Collection colecaoQualidadeAguaPadrao = getControladorUtil().pesquisar(filtroQualidadeAguaPadrao,
						QualidadeAguaPadrao.class.getName());

		QualidadeAguaPadrao qualidadeAguaPadrao = (QualidadeAguaPadrao) Util.retonarObjetoDeColecao(colecaoQualidadeAguaPadrao);

		// Item 145 - Turbidez exigida
		if(!Util.isVazioOuBranco(qualidadeAguaPadrao.getNumeroAmostrasExigidasTurbidez())){
			turbidezExigida = qualidadeAguaPadrao.getNumeroAmostrasExigidasTurbidez().toString();
		}

		// Item 146 - Turbidez realizada
		if(!Util.isVazioOuBranco(qualidadeAgua) && !Util.isVazioOuBranco(qualidadeAgua.getNumeroAmostrasRealizadasTurbidez())){
			turbidezRealizada = qualidadeAgua.getNumeroAmostrasRealizadasTurbidez().toString();
		}

		// Item 147 - Turbidez atendida
		if(!Util.isVazioOuBranco(qualidadeAgua) && !Util.isVazioOuBranco(qualidadeAgua.getNumeroAmostrasConformesTurbidez())){
			turbidezAtendida = qualidadeAgua.getNumeroAmostrasConformesTurbidez().toString();
		}

		// Item 148 - Cor exigida
		if(!Util.isVazioOuBranco(qualidadeAguaPadrao.getNumeroAmostrasExigidasCor())){
			corExigida = qualidadeAguaPadrao.getNumeroAmostrasExigidasCor().toString();
		}

		// Item 149 - Cor realizada
		if(!Util.isVazioOuBranco(qualidadeAgua) && !Util.isVazioOuBranco(qualidadeAgua.getNumeroAmostrasRealizadasCor())){
			corRealizada = qualidadeAgua.getNumeroAmostrasRealizadasCor().toString();
		}

		// Item 150 - Cor atendida
		if(!Util.isVazioOuBranco(qualidadeAgua) && !Util.isVazioOuBranco(qualidadeAgua.getNumeroAmostrasConformesCor())){
			corAtendida = qualidadeAgua.getNumeroAmostrasConformesCor().toString();
		}

		// Item 151 - Cloro exigida
		if(!Util.isVazioOuBranco(qualidadeAguaPadrao.getNumeroAmostrasExigidasCloro())){
			cloroExigida = qualidadeAguaPadrao.getNumeroAmostrasExigidasCloro().toString();
		}

		// Item 152 - Cloro realizada
		if(!Util.isVazioOuBranco(qualidadeAgua) && !Util.isVazioOuBranco(qualidadeAgua.getNumeroAmostrasRealizadasCloro())){
			cloroRealizada = qualidadeAgua.getNumeroAmostrasRealizadasCloro().toString();
		}

		// Item 153 - Cloro atendida
		if(!Util.isVazioOuBranco(qualidadeAgua) && !Util.isVazioOuBranco(qualidadeAgua.getNumeroAmostrasConformesCloro())){
			cloroAtendida = qualidadeAgua.getNumeroAmostrasConformesCloro().toString();
		}

		// Item 154 - Ph exigida
		if(!Util.isVazioOuBranco(qualidadeAguaPadrao.getNumeroAmostrasExigidasPH())){
			phExigida = qualidadeAguaPadrao.getNumeroAmostrasExigidasPH().toString();
		}

		// Item 155 - Ph realizada
		if(!Util.isVazioOuBranco(qualidadeAgua) && !Util.isVazioOuBranco(qualidadeAgua.getNumeroAmostrasRealizadasPH())){
			phRealizada = qualidadeAgua.getNumeroAmostrasRealizadasPH().toString();
		}

		// Item 156 - Ph atendida
		if(!Util.isVazioOuBranco(qualidadeAgua) && !Util.isVazioOuBranco(qualidadeAgua.getNumeroAmostrasConformesPH())){
			phAtendida = qualidadeAgua.getNumeroAmostrasConformesPH().toString();
		}

		// Item 157 - Coliformes Totais exigida
		if(!Util.isVazioOuBranco(qualidadeAguaPadrao.getNumeroAmostrasExigidasColiformesTotais())){
			coliformesTotaisExigida = qualidadeAguaPadrao.getNumeroAmostrasExigidasColiformesTotais().toString();
		}

		// Item 158 - Coliformes Totais realizada
		if(!Util.isVazioOuBranco(qualidadeAgua) && !Util.isVazioOuBranco(qualidadeAgua.getNumeroAmostrasRealizadasColiformesTotais())){
			coliformesTotaisRealizada = qualidadeAgua.getNumeroAmostrasRealizadasColiformesTotais().toString();
		}

		// Item 159 - Coliformes Totais atendida
		if(!Util.isVazioOuBranco(qualidadeAgua) && !Util.isVazioOuBranco(qualidadeAgua.getNumeroAmostrasConformesColiformesTotais())){
			coliformesTotaisAtendida = qualidadeAgua.getNumeroAmostrasConformesColiformesTotais().toString();
		}

		// Item 160 - Coliforme Termo Tolerante exigido
		if(!Util.isVazioOuBranco(qualidadeAguaPadrao.getNumeroAmostrasExigidasColiformesTermotolerantes())){
			coliformeTermoToleranteExigida = qualidadeAguaPadrao.getNumeroAmostrasExigidasColiformesTermotolerantes().toString();
		}

		// Item 161 - Coliforme Termo Tolerante realizado
		if(!Util.isVazioOuBranco(qualidadeAgua)
						&& !Util.isVazioOuBranco(qualidadeAgua.getNumeroAmostrasRealizadasColiformesTermotolerantes())){
			coliformeTermoToleranteRealizada = qualidadeAgua.getNumeroAmostrasRealizadasColiformesTermotolerantes().toString();
		}

		// Item 162 - Coliforme Termo Tolerante atendido
		if(!Util.isVazioOuBranco(qualidadeAgua)
						&& !Util.isVazioOuBranco(qualidadeAgua.getNumeroAmostrasConformesColiformesTermotolerantes())){
			coliformeTermoToleranteAtendida = qualidadeAgua.getNumeroAmostrasConformesColiformesTermotolerantes().toString();
		}

		// Item 163 - Conclusão
		if(!Util.isVazioOuBranco(qualidadeAgua)){
			conclusao = qualidadeAgua.getDescricaoConclusaoAnalisesRealizadas();
		}

		// Item 164 - sequência
		sequencia = Util.converterObjetoParaString(contaEmitirHelper.getSequencialImpressao());

		// ==================================
		// Concatenando o conteúdo do arquivo
		// ==================================

		// Itens de 82 a 129 - Serviços.
		for(EmitirContaUltimosConsumosLeiturasHelper emitirContaUltimosConsumosLeiturasHelper : contaEmitirHelper
						.getListaUltimosConsumosLeituras()){

			// Item 82, 86, 90, 94, 98, 102, 106, 110, 114, 118, 122, 126 - Referência anterior.

			String referenciaAnteriorFormatada = "";

			if((emitirContaUltimosConsumosLeiturasHelper.getReferenciaAnterior() != null)
							&& (emitirContaUltimosConsumosLeiturasHelper.getReferenciaAnterior().intValue() > 0)){
				referenciaAnteriorFormatada = Util.formatarMesAnoReferencia(emitirContaUltimosConsumosLeiturasHelper
								.getReferenciaAnterior());
			}

			contaTxt.append(Util.completaStringComEspacoAEsquerda(referenciaAnteriorFormatada, 7));

			// Item 83, 87, 91, 95, 99, 103, 107, 111, 115, 119, 123, 127 - Leitura anterior.
			contaTxt.append(Util.completaStringComEspacoAEsquerda(emitirContaUltimosConsumosLeiturasHelper.getLeituraAnteriorString(), 6));

			// Item 84, 88, 92, 96, 100, 104, 108, 112, 116, 120, 124, 128 - Anormalidade anterior.
			contaTxt.append(Util.completaStringComEspacoAEsquerda(emitirContaUltimosConsumosLeiturasHelper.getAnormalidadeAnteriorString(),
							2));

			// Item 85, 89, 93, 97, 101, 105, 109, 113, 117, 121, 125, 129 - Consumo.
			contaTxt.append(Util.completaStringComEspacoAEsquerda(emitirContaUltimosConsumosLeiturasHelper.getConsumoAnteriorString(), 5));

		}

		contaTxt.append(Util.completaStringComEspacoAEsquerda(idLocalidade, 3));
		contaTxt.append(Util.completaStringComEspacoAEsquerda(idCdSetorComercial, 2));
		contaTxt.append(Util.completaStringComEspacoAEsquerda(idNumeroQuadra, 4));
		contaTxt.append(Util.completaStringComEspacoAEsquerda(idNumeroLote, 4));
		contaTxt.append(Util.completaStringComEspacoAEsquerda(mensagem1, 80));
		contaTxt.append(Util.completaStringComEspacoAEsquerda(mensagem2, 80));
		contaTxt.append(Util.completaStringComEspacoAEsquerda(mensagem3, 80));
		contaTxt.append(Util.completaStringComEspacoAEsquerda(numeracaoCodigoBarras, 64));
		contaTxt.append(Util.completaStringComEspacoAEsquerda(mensagemDebitoAutomatico, 80));
		contaTxt.append(Util.completaStringComEspacoAEsquerda(codigoBarras, 112));
		contaTxt.append(Util.completaStringComEspacoAEsquerda(referencia, 6));
		contaTxt.append(Util.completaStringComEspacoAEsquerda(digitoModulo11, 1));
		contaTxt.append(Util.completaStringComEspacoAEsquerda(idLocalidade, 3));
		contaTxt.append(Util.completaStringComEspacoAEsquerda(codigoResponsavel, 6));
		contaTxt.append(Util.completaStringComEspacoAEsquerda(matricula, 8));
		contaTxt.append(Util.completaStringComEspacoAEsquerda(turbidezExigida, 3));
		contaTxt.append(Util.completaStringComEspacoAEsquerda(turbidezRealizada, 3));
		contaTxt.append(Util.completaStringComEspacoAEsquerda(turbidezAtendida, 3));
		contaTxt.append(Util.completaStringComEspacoAEsquerda(corExigida, 3));
		contaTxt.append(Util.completaStringComEspacoAEsquerda(corRealizada, 3));
		contaTxt.append(Util.completaStringComEspacoAEsquerda(corAtendida, 3));
		contaTxt.append(Util.completaStringComEspacoAEsquerda(cloroExigida, 3));
		contaTxt.append(Util.completaStringComEspacoAEsquerda(cloroRealizada, 3));
		contaTxt.append(Util.completaStringComEspacoAEsquerda(cloroAtendida, 3));

		contaTxt.append(Util.completaStringComEspacoAEsquerda(phExigida, 3));
		contaTxt.append(Util.completaStringComEspacoAEsquerda(phRealizada, 3));
		contaTxt.append(Util.completaStringComEspacoAEsquerda(phAtendida, 3));

		contaTxt.append(Util.completaStringComEspacoAEsquerda(coliformesTotaisExigida, 3));
		contaTxt.append(Util.completaStringComEspacoAEsquerda(coliformesTotaisRealizada, 3));
		contaTxt.append(Util.completaStringComEspacoAEsquerda(coliformesTotaisAtendida, 3));

		contaTxt.append(Util.completaStringComEspacoAEsquerda(coliformeTermoToleranteExigida, 3));
		contaTxt.append(Util.completaStringComEspacoAEsquerda(coliformeTermoToleranteRealizada, 3));
		contaTxt.append(Util.completaStringComEspacoAEsquerda(coliformeTermoToleranteAtendida, 3));

		contaTxt.append(Util.completaStringComEspacoAEsquerda(conclusao, 60));
		contaTxt.append(Util.completaStringComEspacoAEsquerda(sequencia, 6));

	}

	/**
	 * [UC0352] Emitir Contas
	 * Gera as informações de débitos anteriores para o arquivo de contas emitidas - modelo 2
	 * Itens 61 a 81 do UC0352
	 * 
	 * @param contaTxt
	 * @param contaEmitirHelper
	 * @param anoMesReferencia
	 * @throws ControladorException
	 */
	protected void gerarDebitosAnterioresContasModelo2(StringBuilder contaTxt, EmitirContaTipo2Helper contaEmitirHelper,
					Integer anoMesReferencia) throws ControladorException{

		int quantidadeOutrosDebitos = 0;
		BigDecimal somaValorOutrosDebitos = BigDecimal.ZERO;
		BigDecimal somaValorTotalDebito = BigDecimal.ZERO;
		String referenciaFormatada = "";

		SistemaParametro sistemaParametro = getControladorUtil().pesquisarParametrosDoSistema();
		Date dataCorrente = new Date();
		String idImovel = contaEmitirHelper.getIdImovel().toString();
		String anoMesReferenciaInicial = "000101";
		String anoMesReferenciaFinal = String.valueOf(Util.subtrairMesDoAnoMes(anoMesReferencia, 1));
		int contador = 0;

		Integer parametroQuantidadeDiasVencimentoContaAvisoCorte = Integer
						.parseInt(ParametroCobranca.P_QUANTIDADE_DIAS_VENCIMENTO_CONTA_AVISO_CORTE.executar());

		Date dtVencimentoInicial = Util.converteStringParaDate("01/01/0001");
		Date dtVencimentoFinal = Util.subtrairNumeroDiasDeUmaData(dataCorrente, parametroQuantidadeDiasVencimentoContaAvisoCorte);

		ObterDebitoImovelOuClienteHelper debitoImovelOuClienteHelper = getControladorCobranca().obterDebitoImovelContas(1, idImovel,
						anoMesReferenciaInicial, anoMesReferenciaFinal, dtVencimentoInicial, dtVencimentoFinal);

		Collection<ContaValoresHelper> collContaValoresHelpers = debitoImovelOuClienteHelper.getColecaoContasValores();

		ContaValoresHelper contaValoresHelper = new ContaValoresHelper();

		List contaValoresHelpersList = (List) collContaValoresHelpers;

		if(contaValoresHelpersList.size() > TAMANHO_PARA_CALCULO_DEBITOS){
			int tamanhoParaCalculo = contaValoresHelpersList.size() - TAMANHO_PARA_CALCULO_DEBITOS;
			// ==================================
			// Concatenando o conteúdo do arquivo
			// ==================================
			// Itens de 61 a 79 - Outros Débitos.
			for(int i = contaValoresHelpersList.size() - 1; i >= 0; i--){
				if(i >= tamanhoParaCalculo){

					contaValoresHelper = (ContaValoresHelper) contaValoresHelpersList.get(i);

					// Item 61, 63, 65, 67, 69, 71, 73, 75 - Referencia Débito

					String referenciaDebitosAnteriores = "";

					if(contaValoresHelper.getConta().getReferencia() > 0){
						referenciaDebitosAnteriores = Util.formatarMesAnoReferencia(contaValoresHelper.getConta().getReferencia());
					}

					contaTxt.append(Util.completaString(referenciaDebitosAnteriores, 7));

					// Item 62, 64, 66, 68, 70, 72, 74, 76 - Valor Débito
					contaTxt.append(Util.completarStringComValorEsquerda(Util.formataBigDecimal(contaValoresHelper.getConta().getDebitos(),
									2, true), " ", 14));

					// Item 79 - Valor Total do Débito
					somaValorTotalDebito = somaValorTotalDebito.add(contaValoresHelper.getConta().getDebitos());

				}else{

					// Item 78 - Valor de Outros Débitos
					somaValorOutrosDebitos = somaValorOutrosDebitos.add(contaValoresHelper.getConta().getDebitos());

					// Item 79 - Valor Total do Débito
					somaValorTotalDebito = somaValorTotalDebito.add(contaValoresHelper.getConta().getDebitos());

				}

			}

			// Item 77 - Quantidade de Outros Débitos
			quantidadeOutrosDebitos = tamanhoParaCalculo;

		}else{
			for(int i = contaValoresHelpersList.size() - 1; i >= 0; i--){

				contaValoresHelper = (ContaValoresHelper) contaValoresHelpersList.get(i);

				// Item 61, 63, 65, 67, 69, 71, 73, 75 - Referencia Débito
				if(contaValoresHelper.getConta().getReferencia() > 0){
					referenciaFormatada = Util.formatarMesAnoReferencia(contaValoresHelper.getConta().getReferencia());
				}

				contaTxt.append(Util.completaString(referenciaFormatada, 7));

				// Item 62, 64, 66, 68, 70, 72, 74, 76 - Valor Débito
				contaTxt.append(Util.completarStringComValorEsquerda(Util.formataBigDecimal(contaValoresHelper.getConta().getDebitos(), 2,
								true), " ", 14));

				// Item 79 - Valor Total do Débito
				somaValorTotalDebito = somaValorTotalDebito.add(contaValoresHelper.getConta().getDebitos());

				contador++;

			}

			for(int i = contador + 1; i <= TAMANHO_PARA_CALCULO_DEBITOS; i++){

				// Item 61, 63, 65, 67, 69, 71, 73, 75 - Referencia Débito
				contaTxt.append(Util.completaString("", 7));

				// Item 62, 64, 66, 68, 70, 72, 74, 76 - Valor Débito
				contaTxt.append(Util.completarStringComValorEsquerda(Util.formataBigDecimal(BigDecimal.ZERO, 2, true), " ", 14));

				contador++;

			}

		}

		// Item 77 - Quantidade de Outros Débitos
		if(quantidadeOutrosDebitos == ConstantesSistema.ZERO){
			contaTxt.append(Util.completaString("", 3));
		}else{
			contaTxt.append(Util.completaString(quantidadeOutrosDebitos + "", 3));
		}

		// Item 78 - Valor de Outros Débitos
		contaTxt.append(Util.completarStringComValorEsquerda(Util.formataBigDecimal(somaValorOutrosDebitos, 2, true), " ", 14));

		// Item 79 - Valor Total do Débito
		contaTxt.append(Util.completarStringComValorEsquerda(Util.formataBigDecimal(somaValorTotalDebito, 2, true), " ", 14));

		obterDataBaseDebito(sistemaParametro, contaTxt);

		obterMensagemEconomiaPublica(contaEmitirHelper, collContaValoresHelpers, contaTxt);

	}

	private void obterDataBaseDebito(SistemaParametro sistemaParametro, StringBuilder contaTxt){

		String anoMesFaturamento = String.valueOf(sistemaParametro.getAnoMesFaturamento());
		Date dataFaturamento = Util.converterAnoMesParaDataMenosDoisMesesFinal(anoMesFaturamento);
		String dataBaseDebito = Util.formatarData(dataFaturamento);
		contaTxt.append(Util.completaString(dataBaseDebito, 10));
	}

	private void obterMensagemEconomiaPublica(EmitirContaTipo2Helper contaEmitirHelper, Collection collContaValoresHelpers,
					StringBuilder contaTxt){

		String mensagem = "";

		if((contaEmitirHelper.getEconPublica() == null) && (!Util.isVazioOrNulo(collContaValoresHelpers))){
			if(collContaValoresHelpers.size() < 20){
				mensagem = "IMOVEL COM DEBITO, EVITE O CORTE. PROCURE A CASAL.";
			}else if(collContaValoresHelpers.size() >= 20 && collContaValoresHelpers.size() < 25){
				mensagem = "EVITE COBRANCA JUDICIAL DO DEBITO. PROCURE CASAL.";
			}else{
				mensagem = "DEBITO SUJEITO A COBRANCA JUDICIAL.";
			}
		}

		contaTxt.append(Util.completaString(mensagem, 74));
	}

	/**
	 * [UC0352] Emitir Contas
	 * Gera as informações de serviços cobrados para o arquivo de contas emitidas - modelo 2
	 * Itens 37 a 60 do UC0352
	 * 
	 * @param contaTxt
	 * @param contaEmitirHelper
	 * @throws ControladorException
	 */
	protected void gerarServicosCobradosContasModelo2(StringBuilder contaTxt, EmitirContaTipo2Helper contaEmitirHelper)
					throws ControladorException{

		String codigoServico5 = "";
		String descricaoServico5 = "";
		String valorServico5 = "";
		String codigoServico6 = "";
		String descricaoServico6 = "";
		String valorServico6 = "";
		String codigoServico7 = "";
		String descricaoServico7 = "";
		String valorServico7 = "";
		String dataVencimento = "";
		String dataCorte = "";
		String valorAPagar = "";
		String valorServicoFormatado = "";

		// Limpa a coleção para novo processamento
		contaEmitirHelper.setListaServicos(new ArrayList<EmitirContaServicoHelper>());

		FiltroCreditoRealizado filtroCreditoRealizado = new FiltroCreditoRealizado();
		filtroCreditoRealizado.adicionarParametro(new ParametroSimples(FiltroCreditoRealizado.CONTA_ID, contaEmitirHelper.getIdConta()));
		filtroCreditoRealizado.adicionarCaminhoParaCarregamentoEntidade(FiltroCreditoRealizado.CREDITO_TIPO);
		filtroCreditoRealizado.setCampoOrderBy(FiltroCreditoRealizado.CREDITO_TIPO_ID);

		Collection colecaoCreditoRealizado = getControladorUtil().pesquisar(filtroCreditoRealizado, CreditoRealizado.class.getName());

		FiltroDebitoCobrado filtroDebitoCobrado = new FiltroDebitoCobrado();
		filtroDebitoCobrado.adicionarParametro(new ParametroSimples(FiltroDebitoCobrado.CONTA_ID, contaEmitirHelper.getIdConta()));
		filtroDebitoCobrado.adicionarCaminhoParaCarregamentoEntidade(FiltroDebitoCobrado.DEBITO_TIPO);
		filtroDebitoCobrado.adicionarCaminhoParaCarregamentoEntidade(FiltroDebitoCobrado.FINANCIAMENTO_TIPO);
		filtroDebitoCobrado.adicionarCaminhoParaCarregamentoEntidade(FiltroDebitoCobrado.PARCELAMENTO_RESOLUCAO_DIRETORIA);
		filtroDebitoCobrado.setCampoOrderBy(FiltroDebitoCobrado.DEBITO_TIPO_ID);

		Collection colecaoDebitoCobrado = getControladorUtil().pesquisar(filtroDebitoCobrado, DebitoCobrado.class.getName());

		// 5. Caso existam créditos e débitos lançados na conta:
		if(!Util.isVazioOrNulo(colecaoCreditoRealizado) && !Util.isVazioOrNulo(colecaoDebitoCobrado)){

			// 5.1. Caso a quantidade de débitos mais a quantidade de créditos lançados na conta
			// seja menor ou igual a quatro (4),
			// formata as ocorrências de crédito conforme item 3.1 seguida das
			// ocorrências de débito conforme item 4.1.
			int somaQtdCreditosDebitosConta = colecaoCreditoRealizado.size() + colecaoDebitoCobrado.size();

			if(somaQtdCreditosDebitosConta <= QUANTIDADE_SERVICOS_EMITIR_CONTA){

				quantidadeCreditosLancadosContaMenorIgualQuatro(colecaoCreditoRealizado, contaEmitirHelper);

				quantidadeDebitosLancadosContaMenorIgualQuatro(colecaoDebitoCobrado, contaEmitirHelper);

			}else{
				// 5.2. Caso contrário:
				// 5.2.1. Formata a primeira ocorrência com o valor acumulado de crédito lançado na
				// conta a exemplo da definição do item 3.2.

				quantidadeCreditosLancadosContaMaiorQuatro(colecaoCreditoRealizado, contaEmitirHelper, ConstantesSistema.ZERO);

				// 5.2.2. Formata as três ocorrências restantes com valores de débito a exemplo da
				// definição do item 4.
				if(colecaoDebitoCobrado.size() <= (QUANTIDADE_SERVICOS_EMITIR_CONTA - 1)){

					quantidadeDebitosLancadosContaMenorIgualQuatro(colecaoDebitoCobrado, contaEmitirHelper);

				}else{

					quantidadeDebitosLancadosContaMaiorQuatro(colecaoDebitoCobrado, contaEmitirHelper, ConstantesSistema.ZERO);

				}

			}

		}else{
			if(!Util.isVazioOrNulo(colecaoCreditoRealizado)){

				if(colecaoCreditoRealizado.size() <= QUANTIDADE_SERVICOS_EMITIR_CONTA){

					quantidadeCreditosLancadosContaMenorIgualQuatro(colecaoCreditoRealizado, contaEmitirHelper);

				}else{

					quantidadeCreditosLancadosContaMaiorQuatro(colecaoCreditoRealizado, contaEmitirHelper,
									(QUANTIDADE_SERVICOS_EMITIR_CONTA - 1));

				}

			}else{

				if(colecaoDebitoCobrado.size() <= QUANTIDADE_SERVICOS_EMITIR_CONTA){

					quantidadeDebitosLancadosContaMenorIgualQuatro(colecaoDebitoCobrado, contaEmitirHelper);

				}else{

					quantidadeDebitosLancadosContaMaiorQuatro(colecaoDebitoCobrado, contaEmitirHelper,
									(QUANTIDADE_SERVICOS_EMITIR_CONTA - 1));

				}

			}

		}

		// verifica se a lista de débitos tem 4 serviços, se não tiver, adiciona o que faltar.
		if(contaEmitirHelper.getListaServicos().size() < QUANTIDADE_SERVICOS_EMITIR_CONTA){

			for(int i = contaEmitirHelper.getListaServicos().size(); i < QUANTIDADE_SERVICOS_EMITIR_CONTA; i++){

				EmitirContaServicoHelper emitirContaServicoHelper = new EmitirContaServicoHelper();

				contaEmitirHelper.addListaServicos(emitirContaServicoHelper);

			}

		}

		// Item 50 - Descrição do serviço 5.
		if(!contaEmitirHelper.getValorImpostos().equals(BigDecimal.ZERO)){
			descricaoServico5 = "I.R. RETIDO NA FONTE";
		}

		// Item 50 - Descrição do serviço 5.
		valorServico5 = Util.formataBigDecimal(contaEmitirHelper.getValorImpostos(), 2, true);

		// Item 54 - Descrição do serviço 6.
		valorServico6 = Util.formataBigDecimal(BigDecimal.ZERO, 2, true);

		// Item 57 - Descrição do serviço 7.
		valorServico7 = Util.formataBigDecimal(BigDecimal.ZERO, 2, true);

		// Item 58 - Data de Vencimento
		dataVencimento = Util.formatarData(contaEmitirHelper.getDataVencimento());

		// Item 59 - Data de corte
		dataCorte = Util.somaDiasAData(contaEmitirHelper.getDataVencimento(), 10);

		// Item 60 - Valor a pagar
		valorAPagar = Util.formataBigDecimal(contaEmitirHelper.getValorTotalConta(), 2, true);

		// ==================================
		// Concatenando o conteúdo do arquivo
		// ==================================

		// Itens de 37 a 48 - Serviços.
		for(EmitirContaServicoHelper emitirContaServicoHelper : contaEmitirHelper.getListaServicos()){

			// Item 37, 40, 43, 46 - Código do serviço.
			contaTxt.append(Util.completaStringComEspacoAEsquerda(emitirContaServicoHelper.getCodigoServicoString(), 3));

			// Item 38, 41, 44, 47 - Descrição do serviço.
			contaTxt.append(Util.completaString(emitirContaServicoHelper.getDescricaoServico(), 38));

			// Item 39, 42, 45, 48 - Valor do serviço.
			if((emitirContaServicoHelper.getValorServico() != null) && !emitirContaServicoHelper.getValorServico().equals(BigDecimal.ZERO)){
				valorServicoFormatado = Util.formataBigDecimal(emitirContaServicoHelper.getValorServico(), 2, true);
			}

			contaTxt.append(Util.completaStringComEspacoAEsquerda(valorServicoFormatado, 14));

		}

		// Item 49 - Código do serviço 5.
		contaTxt.append(Util.completaStringComEspacoAEsquerda(codigoServico5, 3));
		// Item 50 - Descrição do serviço 5.
		contaTxt.append(Util.completaString(descricaoServico5, 38));
		// Item 51 - Valor do serviço 5.
		contaTxt.append(Util.completaStringComEspacoAEsquerda(valorServico5, 14));
		// Item 52 - Código do serviço 5.
		contaTxt.append(Util.completaStringComEspacoAEsquerda(codigoServico6, 3));
		// Item 53 - Descrição do serviço 5.
		contaTxt.append(Util.completaString(descricaoServico6, 38));
		// Item 54 - Valor do serviço 5.
		contaTxt.append(Util.completaStringComEspacoAEsquerda(valorServico6, 14));
		// Item 55 - Código do serviço 5.
		contaTxt.append(Util.completaStringComEspacoAEsquerda(codigoServico7, 3));
		// Item 56 - Descrição do serviço 5.
		contaTxt.append(Util.completaString(descricaoServico7, 38));
		// Item 57 - Valor do serviço 5.
		contaTxt.append(Util.completaStringComEspacoAEsquerda(valorServico7, 14));
		// Item 58 - Data de Vencimento
		contaTxt.append(Util.completaString(dataVencimento, 10));
		// Item 59 - Data de corte
		contaTxt.append(Util.completaString(dataCorte, 10));
		// Item 60 - Valor a pagar
		contaTxt.append(Util.completarStringComValorEsquerda(valorAPagar, "*", 14));

	}

	/**
	 * [UC0352] Emitir Contas
	 * [SB0101] – Formatar Débitos Cobrados/Créditos realizados
	 * 3.1. Caso a quantidade de créditos lançados na conta seja menor ou igual a quatro (4).
	 * 
	 * @param colecaoCreditoRealizado
	 */
	protected void quantidadeCreditosLancadosContaMenorIgualQuatro(Collection colecaoCreditoRealizado,
					EmitirContaTipo2Helper contaEmitirHelper){

		for(Object object : colecaoCreditoRealizado){

			CreditoRealizado creditoRealizado = (CreditoRealizado) object;

			preencherEmitirContaServicoCreditoHelper(contaEmitirHelper, creditoRealizado);

		}

	}

	/**
	 * [UC0352] Emitir Contas
	 * [SB0101] – Formatar Débitos Cobrados/Créditos realizados
	 * 3.1. Caso a quantidade de créditos lançados na conta seja menor ou igual a quatro (4).
	 * 
	 * @param colecaoCreditoRealizado
	 */
	protected void quantidadeDebitosLancadosContaMenorIgualQuatro(Collection colecaoDebitoCobrado, EmitirContaTipo2Helper contaEmitirHelper)
					throws ControladorException{

		for(Object object : colecaoDebitoCobrado){

			DebitoCobrado debitoCobrado = (DebitoCobrado) object;

			preencherEmitirContaServicoDebitoHelper(contaEmitirHelper, debitoCobrado, null);

		}

	}

	/**
	 * [UC0352] Emitir Contas
	 * [SB0101] – Formatar Débitos Cobrados/Créditos realizados
	 * 3.2. Caso contrário, gera as três (3) primeiras ocorrência conforme descrito no item anterior
	 * (3.1) e gera a quarta ocorrência da seguinte forma.
	 * 
	 * @param colecaoCreditoRealizado
	 * @param contaEmitirHelper
	 * @param qtdServicos
	 *            ( indica a quantidade de créditos que será adicionado na lista de serviços).
	 */
	protected void quantidadeCreditosLancadosContaMaiorQuatro(Collection colecaoCreditoRealizado, EmitirContaTipo2Helper contaEmitirHelper,
					int qtdServicos){

		EmitirContaServicoHelper emitirContaServicoHelper = null;

		int contador = 1;
		BigDecimal somaValorCredito = BigDecimal.ZERO;

		for(Object object : colecaoCreditoRealizado){

			CreditoRealizado creditoRealizado = (CreditoRealizado) object;

			if(contador <= qtdServicos){

				preencherEmitirContaServicoCreditoHelper(contaEmitirHelper, creditoRealizado);

			}else{

				somaValorCredito.add(creditoRealizado.getValorCredito());

			}

			contador++;

		}

		emitirContaServicoHelper = new EmitirContaServicoHelper(null, DESCRICAO_SERVICOS_OUTROS_CREDITOS, somaValorCredito);

		contaEmitirHelper.addListaServicos(emitirContaServicoHelper);

	}

	/**
	 * [UC0352] Emitir Contas
	 * [SB0101] – Formatar Débitos Cobrados/Créditos realizados
	 * 3.2. Caso contrário, gera as três (3) primeiras ocorrência conforme descrito no item anterior
	 * (3.1) e gera a quarta ocorrência da seguinte forma.
	 * 
	 * @param colecaoCreditoRealizado
	 * @param contaEmitirHelper
	 * @param qtdServicos
	 *            ( indica a quantidade de débitos que será adicionado na lista de serviços).
	 */
	protected void quantidadeDebitosLancadosContaMaiorQuatro(Collection colecaoDebitoCobrado, EmitirContaTipo2Helper contaEmitirHelper,
					int qtdServicos) throws ControladorException{

		EmitirContaServicoHelper emitirContaServicoHelper = null;

		int contador = 1;
		BigDecimal somaValorDebito = BigDecimal.ZERO;

		for(Object object : colecaoDebitoCobrado){

			DebitoCobrado debitoCobrado = (DebitoCobrado) object;

			if(contador <= qtdServicos){

				preencherEmitirContaServicoDebitoHelper(contaEmitirHelper, debitoCobrado, colecaoDebitoCobrado);

			}else{

				somaValorDebito = somaValorDebito.add(debitoCobrado.getValorPrestacao());

			}

			contador++;

		}

		emitirContaServicoHelper = new EmitirContaServicoHelper(null, DESCRICAO_SERVICOS_OUTROS_DEBITOS, somaValorDebito);

		contaEmitirHelper.addListaServicos(emitirContaServicoHelper);

	}

	private void preencherEmitirContaServicoCreditoHelper(EmitirContaTipo2Helper contaEmitirHelper, CreditoRealizado creditoRealizado){

		StringBuilder descricaoServico = new StringBuilder();
		Integer idCreditoTipo = null;

		if(!Util.isVazioOuBranco(creditoRealizado.getCreditoTipo())){

			descricaoServico.append(Util.completaString(creditoRealizado.getCreditoTipo().getDescricao(), 23));
			idCreditoTipo = creditoRealizado.getCreditoTipo().getId();
		}

		descricaoServico.append(Util.completaString("", 1));
		descricaoServico.append(Util.completaString(Util.formatarAnoMesSemBarraParaMesAnoComBarra(creditoRealizado
						.getAnoMesReferenciaCredito()), 7));
		descricaoServico.append(Util.completaString("", 1));
		if(!Util.isVazioOuBranco(creditoRealizado.getNumeroPrestacaoCredito())
						&& !Util.isVazioOuBranco(creditoRealizado.getNumeroPrestacao())){
			descricaoServico.append(Util.completaString(creditoRealizado.getNumeroPrestacaoCredito() + "/"
							+ creditoRealizado.getNumeroPrestacao(), 5));
		}else{

			descricaoServico.append(Util.completaString("", 5));

		}

		descricaoServico.append(Util.completaString("", 1));

		EmitirContaServicoHelper emitirContaServicoHelper = new EmitirContaServicoHelper(idCreditoTipo, descricaoServico.toString(),
						creditoRealizado.getValorCredito());
		contaEmitirHelper.addListaServicos(emitirContaServicoHelper);

	}

	private void preencherEmitirContaServicoDebitoHelper(EmitirContaTipo2Helper contaEmitirHelper, DebitoCobrado debitoCobrado,
					Collection<DebitoCobrado> colecaoDebitoCobrado) throws ControladorException{

		StringBuilder descricaoServico = new StringBuilder();
		Integer idDebitoTipo = null;

		if(!Util.isVazioOuBranco(debitoCobrado.getDebitoTipo())){

			String descricao = "";

			if(colecaoDebitoCobrado == null){

				descricao = this.getControladorFaturamento().formatarDescricaoDebitoCobrado(debitoCobrado.getId());

			}else{

				descricao = this.formatarDescricaoDebitos(colecaoDebitoCobrado, debitoCobrado);

			}

			descricaoServico.append(Util.completaString(descricao, 23));
			idDebitoTipo = debitoCobrado.getDebitoTipo().getId();

		}

		descricaoServico.append(Util.completaString("", 1));
		descricaoServico.append(Util.completaString(Util
						.formatarAnoMesSemBarraParaMesAnoComBarra(debitoCobrado.getAnoMesReferenciaDebito()), 7));
		descricaoServico.append(Util.completaString("", 1));

		if(!Util.isVazioOuBranco(debitoCobrado.getNumeroPrestacaoDebito()) && !Util.isVazioOuBranco(debitoCobrado.getNumeroPrestacao())){

			descricaoServico.append(Util.completaString(
							debitoCobrado.getNumeroPrestacaoDebito() + "/" + debitoCobrado.getNumeroPrestacao(), 5));

		}else{

			descricaoServico.append(Util.completaString("", 5));

		}
		descricaoServico.append(Util.completaString("", 1));

		EmitirContaServicoHelper emitirContaServicoHelper = new EmitirContaServicoHelper(idDebitoTipo, descricaoServico.toString(),
						debitoCobrado.getValorPrestacao());

		contaEmitirHelper.addListaServicos(emitirContaServicoHelper);

	}

	/**
	 * [UC0352] [SB0104]
	 * Método responsável por formatar a descrição dos débitos
	 * 
	 * @return
	 * @throws ControladorException
	 */
	private String formatarDescricaoDebitos(Collection<DebitoCobrado> colecaoDebitoCobrado, DebitoCobrado debitoCobrado)
					throws ControladorException{

		String descricaoDebito = "";

		// 1. Caso, na lista dos débitos cobrados, exista débito cobrado de parcelamento (algum dos
		// FNTP_ID da tabela DEBITO_COBRADO está contido em algum dos FNTP_ID da tabela
		// FINANCIAMENTO_TIPO com FNTP_DSFINANCIAMENTOTIPO com o valor correspondente a parcelamento
		// de água, parcelamento de esgoto, parcelamento de serviços ou juros de parcelamento):
		if(this.existeDebitoCobradoParcelamento(colecaoDebitoCobrado)){

			// 1.3. Caso exista, em algum dos débitos cobrados, a identificação do parcelamento
			// (PARC_ID
			// com o valor diferente de nulo):
			if(this.existeDebitoCobradoIdentificacaoParcelamento(colecaoDebitoCobrado)){

				Boolean rdsDistintas = this.existemRDsDistintas(colecaoDebitoCobrado);
				Boolean existeRdsIndEmissaoAssuntoConta = this.existemRDsIndicacaoEmissaoAssuntoConta(colecaoDebitoCobrado);

				// 1.3.1. Obter as RDs dos parcelamentos identificados (a partir da tabela
				// RESOLUCAO_DIRETORIA com RDIR_ID=RDIR_ID da tabela PARCELAMENTO com PARC_ID
				// contido em
				// algum dos PARC_ID da tabela DEBITO_COBRADO).

				// 1.3.2. Caso existam RDs com a indicação de emissão do assunto na conta
				// (RDIR_ICEMISSAOASSUNTOCONTA=1) e sejam RDs distintas ou caso não existam RDs com
				// a
				// indicação de emissão do assunto na conta:
				if(rdsDistintas || !existeRdsIndEmissaoAssuntoConta){

					// 1.3.2.1. Descrição do Débito = DBTP_DSDEBITOTIPO da tabela DEBITO_TIPO com
					// DBTP_ID=DBTP_ID da tabela DEBITO_COBRADO.
					if(debitoCobrado.getDebitoTipo() != null){

						descricaoDebito = debitoCobrado.getDebitoTipo().getDescricao();

					}

				}else if(existeRdsIndEmissaoAssuntoConta && !rdsDistintas){
					// 1.3.3. Caso contrário, ou seja, caso existam RDs com a indicação de emissão
					// do assunto na
					// conta e não sejam RDs distintas:

					// 1.3.3.1. Descrição do Débito = 10 primeiros caracteres de DBTP_DSDEBITOTIPO
					// da tabela
					// DEBITO_TIPO com DBTP_ID=DBTP_ID da tabela DEBITO_COBRADO mais um espaço em
					// branco mais
					// três primeiros caracteres do assunto da RD (RDIR_DSASSUNTO) mais o valor do
					// débito
					// correspondente aos débitos cobrados de parcelamento da RD (somatório de
					// DBCB_VLPRESTACAO
					// no formato ZZ.ZZ9,99).
					if(debitoCobrado.getDebitoTipo() != null){

						BigDecimal totalVlPrestacoes = this.obterTotalVlPrestacoes(colecaoDebitoCobrado);

						descricaoDebito = Util.completarStringCaractereDireita(debitoCobrado.getDebitoTipo().getDescricao(), 10, ' ')
										+ " "
										+ Util.completarStringCaractereDireita(debitoCobrado.getParcelamento().getResolucaoDiretoria()
														.getDescricaoAssunto(), 3, ' ')
										+ Util.completaStringComEspacoAEsquerda(Util.formatarMoedaReal(totalVlPrestacoes), 9);

					}

				}

			}else{

				// 1.4. Caso contrário, ou seja, nenhum dos débitos cobrados têm a identificação do
				// parcelamento:
				// 1.4.1.1. Descrição do Débito = DBTP_DSDEBITOTIPO da tabela DEBITO_TIPO com
				// DBTP_ID=DBTP_ID da tabela DEBITO_COBRADO.
				if(debitoCobrado.getDebitoTipo() != null){

					descricaoDebito = debitoCobrado.getDebitoTipo().getDescricao();

				}

			}

		}

		return descricaoDebito;

	}

	private BigDecimal obterTotalVlPrestacoes(Collection<DebitoCobrado> colecaoDebitoCobrado){

		BigDecimal total = BigDecimal.ZERO;

		if(colecaoDebitoCobrado != null){

			for(DebitoCobrado debitoCobrado : colecaoDebitoCobrado){

				total = total.add(debitoCobrado.getValorPrestacao());

			}

		}

		return total;

	}

	private Boolean existemRDsDistintas(Collection<DebitoCobrado> colecaoDebitoCobrado){

		Boolean retorno = Boolean.FALSE;
		Integer idRd = null;

		if(colecaoDebitoCobrado != null){

			for(DebitoCobrado debitoCobrado : colecaoDebitoCobrado){

				if(debitoCobrado.getParcelamento() != null
								&& debitoCobrado.getParcelamento().getResolucaoDiretoria() != null
								&& debitoCobrado.getParcelamento().getResolucaoDiretoria().getIndicadorEmissaoAssuntoConta().equals(
												ConstantesSistema.SIM)){

					if(idRd == null){

						idRd = debitoCobrado.getParcelamento().getResolucaoDiretoria().getId();

					}else if(debitoCobrado.getParcelamento().getResolucaoDiretoria().getId().intValue() != idRd.intValue()){

						retorno = Boolean.TRUE;
						break;

					}

				}

			}

		}

		return retorno;

	}

	private Boolean existemRDsIndicacaoEmissaoAssuntoConta(Collection<DebitoCobrado> colecaoDebitoCobrado){

		Boolean retorno = Boolean.FALSE;

		for(DebitoCobrado debitoCobrado : colecaoDebitoCobrado){

			if(debitoCobrado.getParcelamento() != null
							&& debitoCobrado.getParcelamento().getResolucaoDiretoria() != null
							&& debitoCobrado.getParcelamento().getResolucaoDiretoria().getIndicadorEmissaoAssuntoConta().equals(
											ConstantesSistema.SIM)){

				retorno = Boolean.TRUE;
				break;

			}

		}

		return retorno;

	}

	private Boolean existeDebitoCobradoIdentificacaoParcelamento(Collection<DebitoCobrado> colecaoDebitoCobrado){

		Boolean retorno = Boolean.FALSE;

		if(colecaoDebitoCobrado != null){

			for(DebitoCobrado debitoCobrado : colecaoDebitoCobrado){

				if(debitoCobrado.getParcelamento() != null){

					retorno = Boolean.TRUE;
					break;

				}

			}

		}

		return retorno;

	}

	private Boolean existeDebitoCobradoParcelamento(Collection<DebitoCobrado> colecaoDebitoCobrado) throws ControladorException{

		Boolean retorno = Boolean.FALSE;

		Collection<Integer> tiposParcelamento = Util
						.converterStringParaColecaoInteger(ParametroParcelamento.P_FINANCIAMENTO_TIPO_PARCELAMENTO
						.executar());

		if(colecaoDebitoCobrado != null){

			for(DebitoCobrado debitoCobrado : colecaoDebitoCobrado){

				if(debitoCobrado.getFinanciamentoTipo() != null){

					Integer idFinancimentoTipo = debitoCobrado.getFinanciamentoTipo().getId();

					if(tiposParcelamento != null && tiposParcelamento.contains(idFinancimentoTipo)){
						retorno = Boolean.TRUE;
						break;
					}

				}

			}

		}

		return retorno;

	}

	/**
	 * [UC0352] Emitir Contas
	 * Gera as informações gerais para o arquivo de contas emitidas - modelo 2
	 * Itens 1 a 36 do UC0352
	 * 
	 * @param contaTxt
	 * @param contaEmitirHelper
	 * @throws ControladorException
	 */
	private void gerarDadosGeraisContasModelo2(StringBuilder contaTxt, EmitirContaTipo2Helper contaEmitirHelper)
					throws ControladorException{

		Cliente clienteUsuario = null;
		LigacaoAgua ligacaoAgua = null;
		ConsumoTipo consumoTipo = null;
		String nomeConsumidor = "";
		String matriculaImovelFormatada = "";
		Object[] endereco = null;
		String enderecoParte1 = "";
		String enderecoParte2 = "";
		String municipioFormatado = "";
		String referenciaFormatada = "";
		String idClienteResponsavelFormatado = "";
		String campo8Hifen = "";
		String nomeClienteResponsavel = "";
		String cpfCnpjClienteUsuario = "";
		String idLigacaoAguaSituacao = "";
		String idLigacaoEsgotoSituacao = "";
		String numeroWC = "  0";
		String numeroHidrometro = "";
		String consumoMedio = "";
		String dtLeituraAtualFormatada = "";
		String leituraAnterior = "0";
		String leituraAtual = "0";
		String consumoFaturado = "0";
		String codigoAuxiliar = "";
		String tipoCobranca = "";
		String segundoEnderecoParte1 = "";
		String segundoEnderecoParte2 = "";
		String economiaResidencial = "";
		String economiaComercial = "";
		String economiaIndustrial = "";
		String economiaPublica = "";
		String inscSetorComercial = "";
		String inscQuadra = "";
		String inscLote = "";
		String descricaoAgua = "";
		String valorAgua = "";
		String descricaoEsgoto = "";
		String valorEsgoto = "";

		// =====================================================
		// Consulta o cliente USUARIO
		// =====================================================
		clienteUsuario = contaEmitirHelper.getClienteUsuario();

		// =====================================================
		// Consulta a LigacaoAgua a partir do Id do Imóvel
		// =====================================================
		FiltroLigacaoAgua filtroLigacaoAgua = new FiltroLigacaoAgua();
		filtroLigacaoAgua.adicionarParametro(new ParametroSimples(FiltroLigacaoAgua.ID, contaEmitirHelper.getIdImovel()));
		ligacaoAgua = (LigacaoAgua) Util.retonarObjetoDeColecao(Fachada.getInstancia().pesquisar(filtroLigacaoAgua,
						LigacaoAgua.class.getName()));

		// =====================================================
		// Consulta o ConsumoTipo a partir do Id
		// =====================================================
		FiltroConsumoTipo filtroConsumoTipo = new FiltroConsumoTipo();
		filtroConsumoTipo.adicionarParametro(new ParametroSimples(FiltroConsumoTipo.CODIGO, contaEmitirHelper.getIdConsumoTipo()));
		consumoTipo = (ConsumoTipo) Util.retonarObjetoDeColecao(getControladorUtil().pesquisar(filtroConsumoTipo,
						ConsumoTipo.class.getName()));

		// =====================================================
		// Tratamento das informações
		// =====================================================

		// Item 1 - Nome do Imóvel ou Nome do cliente
		if(!Util.isVazioOuBranco(contaEmitirHelper.getNomeImovel())){
			nomeConsumidor = contaEmitirHelper.getNomeImovel();

		}else if(contaEmitirHelper.getNomeCliente() != null){
			nomeConsumidor = contaEmitirHelper.getNomeCliente();
		}

		// Item 2 - Matrícula do Imóvel
		matriculaImovelFormatada = Util.retornaMatriculaImovelFormatada(Util.obterInteger(contaEmitirHelper.getIdImovel().toString()));

		endereco = ServiceLocator.getInstancia().getControladorEndereco().pesquisarEnderecoFormatadoLista(contaEmitirHelper.getIdImovel(),
						64);

		// Item 3 - Endereço - Parte 1
		if(!Util.isVazioOuBranco(endereco[6])){
			enderecoParte1 = (String) endereco[6];
		}

		// Item 4 - Endereço - Parte 2
		if(!Util.isVazioOuBranco(endereco[7])){
			enderecoParte2 = (String) endereco[7];
		}

		// Item 5 - Município
		if(!Util.isVazioOuBranco(endereco[5])){
			municipioFormatado = (String) endereco[5];
		}

		// Item 6 - Referência
		if(contaEmitirHelper.getAnoMesConta() != null){
			referenciaFormatada = Util.formatarMesAnoReferencia(contaEmitirHelper.getAnoMesConta()) + "-"
							+ Util.obterDigitoVerificadorModulo11(contaEmitirHelper.getAnoMesConta().toString());
		}

		// Item 7 - Código do responsável
		if((contaEmitirHelper.getIdClienteResponsavel() != null) && (contaEmitirHelper.getIdClienteResponsavel().intValue() > 0)){
			idClienteResponsavelFormatado = Util.retornaMatriculaImovelFormatada(contaEmitirHelper.getIdClienteResponsavel());

			// Item 8 - Hífen
			campo8Hifen = "-";
		}

		// Item 9 - Nome do responsável
		if(contaEmitirHelper.getClienteResponsavel() != null){
			nomeClienteResponsavel = contaEmitirHelper.getClienteResponsavel().getNome();
		}

		// Item 10 - CPF/CNPJ do cliente usuário
		if((clienteUsuario != null) && (clienteUsuario.getClienteTipo() != null)){
			if(clienteUsuario.getClienteTipo().getIndicadorPessoaFisicaJuridica().equals(ClienteTipo.INDICADOR_PESSOA_FISICA)){
				if(!Util.isVazioOuBranco(clienteUsuario.getCpf())){
					cpfCnpjClienteUsuario = "  " + clienteUsuario.getCpf();
				}
			}else if(clienteUsuario.getClienteTipo().getIndicadorPessoaFisicaJuridica().equals(ClienteTipo.INDICADOR_PESSOA_JURIDICA)){
				if(!Util.isVazioOuBranco(clienteUsuario.getCnpj())){
					cpfCnpjClienteUsuario = clienteUsuario.getCnpj();

				}
			}

			// Formata o CNPJ
			int length = cpfCnpjClienteUsuario.length();

			if(length > 2){
				cpfCnpjClienteUsuario = cpfCnpjClienteUsuario.substring(0, length - 2) + "-" + cpfCnpjClienteUsuario.substring(length - 2);
			}
		}

		// Item 11 - Situação da água
		if(contaEmitirHelper.getIdLigacaoAguaSituacao() != null){
			idLigacaoAguaSituacao = contaEmitirHelper.getIdLigacaoAguaSituacao().toString();
		}

		// Item 12 - Situação do esgoto
		if(contaEmitirHelper.getIdLigacaoEsgotoSituacao() != null){
			idLigacaoEsgotoSituacao = contaEmitirHelper.getIdLigacaoEsgotoSituacao().toString();
		}

		// Item 14 - Hidrômetro
		if(contaEmitirHelper.getHidrometro() != null){
			numeroHidrometro = contaEmitirHelper.getHidrometro();
		}

		// Item 15 - Média de consumo
		if(contaEmitirHelper.getConsumoMedio() != null){
			consumoMedio = contaEmitirHelper.getConsumoMedio().toString();
		}

		// Item 16 - Data da leitura
		if(contaEmitirHelper.getDtLeituraAtual() != null){
			dtLeituraAtualFormatada = Util.formatarDataDiaMesComBarra(contaEmitirHelper.getDtLeituraAtual());
		}

		// Item 17 - Leitura anterior
		if(contaEmitirHelper.getLeituraAnterior() != null){
			leituraAnterior = contaEmitirHelper.getLeituraAnterior().toString();
		}

		// Item 18 - Leitura atual
		if(contaEmitirHelper.getLeituraAtual() != null){
			leituraAtual = contaEmitirHelper.getLeituraAtual().toString();
		}

		// Item 19 - Consumo faturado
		if(contaEmitirHelper.getConsumoFaturado() != null){
			consumoFaturado = contaEmitirHelper.getConsumoFaturado().toString();
		}

		// Item 20- Código auxiliar
		codigoAuxiliar = criarCodigoAuxiliar(contaEmitirHelper, ligacaoAgua);

		// Item 21 - Tipo de cobrança
		if(!Util.isVazioOuBranco(consumoTipo)){
			if(!Util.isVazioOuBranco(consumoTipo.getDescricaoAbreviada()) && consumoTipo.getDescricaoAbreviada().equals("N")){
				tipoCobranca = "E";
			}else{
				tipoCobranca = consumoTipo.getDescricaoAbreviada();
			}
		}

		// Consulta o endereço particionado agora por 50 caracteres
		endereco = ServiceLocator.getInstancia().getControladorEndereco().pesquisarEnderecoFormatadoLista(contaEmitirHelper.getIdImovel(),
						50);

		// Item 22 - Endereço do imóvel - parte 1
		if(!Util.isVazioOuBranco(endereco[5])){
			segundoEnderecoParte1 = (String) endereco[5];
		}

		// Item 24 - Endereço do imóvel - parte 2
		if(!Util.isVazioOuBranco(endereco[6])){
			segundoEnderecoParte2 = (String) endereco[6];
		}

		// Item 26 - Economia residencial
		if((contaEmitirHelper.getEconResidencial() != null) && (contaEmitirHelper.getEconResidencial().intValue() != 0)){
			economiaResidencial = contaEmitirHelper.getEconResidencial().toString();
		}

		// Item 27 - Economia comercial
		if((contaEmitirHelper.getEconComercial() != null) && (contaEmitirHelper.getEconComercial().intValue() != 0)){
			economiaComercial = contaEmitirHelper.getEconComercial().toString();
		}

		// Item 28 - Economia industrial
		if((contaEmitirHelper.getEconIndustrial() != null) && (contaEmitirHelper.getEconIndustrial().intValue() != 0)){
			economiaIndustrial = contaEmitirHelper.getEconIndustrial().toString();
		}

		// Item 29 - Economia publica
		if((contaEmitirHelper.getEconPublica() != null) && (contaEmitirHelper.getEconPublica().intValue() != 0)){
			economiaPublica = contaEmitirHelper.getEconPublica().toString();
		}

		// Item 30 - Setor comercial
		if((contaEmitirHelper.getInscSetorComercial() != null) && (contaEmitirHelper.getInscSetorComercial().intValue() != 0)){
			inscSetorComercial = contaEmitirHelper.getInscSetorComercial().toString();
		}

		// Item 31 - Número da quadra
		if((contaEmitirHelper.getInscQuadra() != null) && (contaEmitirHelper.getInscQuadra().intValue() != 0)){
			inscQuadra = contaEmitirHelper.getInscQuadra().toString();
		}

		// Item 32 - Número do lote
		if((contaEmitirHelper.getInscLote() != null) && (contaEmitirHelper.getInscLote().intValue() != 0)){
			inscLote = contaEmitirHelper.getInscLote().toString();
		}

		// Item 33 - Descrição água
		// Item 34 - Valor água
		if((contaEmitirHelper.getValorAgua() != null) && (contaEmitirHelper.getValorAgua().compareTo(BigDecimal.ZERO) > 0)){
			descricaoAgua = "AGUA";
			valorAgua = Util.formatarMoedaReal(contaEmitirHelper.getValorAgua());
		}

		// Item 35 - Descrição esgoto
		// Item 36 - Valor esgoto
		if((contaEmitirHelper.getValorEsgoto() != null) && (contaEmitirHelper.getValorEsgoto().compareTo(BigDecimal.ZERO) > 0)){
			descricaoEsgoto = "ESGOTO";
			valorEsgoto = Util.formatarMoedaReal(contaEmitirHelper.getValorEsgoto());
		}

		// ==================================
		// Concatenando o conteúdo do arquivo
		// ==================================

		contaTxt.append(Util.completaString(nomeConsumidor, 62));
		contaTxt.append(Util.completarStringZeroEsquerda(matriculaImovelFormatada, 9));
		contaTxt.append(Util.completaString(enderecoParte1, 64));
		contaTxt.append(Util.completaString(enderecoParte2, 62));
		contaTxt.append(Util.completaString(municipioFormatado, 64));
		contaTxt.append(Util.completaString(referenciaFormatada, 9));
		contaTxt.append(Util.completaString(idClienteResponsavelFormatado, 7));
		contaTxt.append(Util.completaString(campo8Hifen, 1));
		contaTxt.append(Util.completaString(nomeClienteResponsavel, 34));
		contaTxt.append(Util.completaString(cpfCnpjClienteUsuario, 20));
		contaTxt.append(Util.completaString(idLigacaoAguaSituacao, 1));
		contaTxt.append(Util.completaString(idLigacaoEsgotoSituacao, 1));
		contaTxt.append(numeroWC);
		contaTxt.append(Util.completaStringComEspacoAEsquerda(numeroHidrometro, 10));
		contaTxt.append(Util.completaStringComEspacoAEsquerda(consumoMedio, 5));
		contaTxt.append(Util.completaString(dtLeituraAtualFormatada, 5));
		contaTxt.append(Util.completaStringComEspacoAEsquerda(leituraAnterior, 6));
		contaTxt.append(Util.completaStringComEspacoAEsquerda(leituraAtual, 6));
		contaTxt.append(Util.completaStringComEspacoAEsquerda(consumoFaturado, 5));
		contaTxt.append(Util.completaString(codigoAuxiliar, 8));
		contaTxt.append(Util.completaString(tipoCobranca, 1));
		contaTxt.append(Util.completaString(segundoEnderecoParte1, 50));
		contaTxt.append(Util.completaString(municipioFormatado, 37));
		contaTxt.append(Util.completaString(segundoEnderecoParte2, 50));
		contaTxt.append(Util.completaString("", 3));
		contaTxt.append(Util.completaStringComEspacoAEsquerda(economiaResidencial, 3));
		contaTxt.append(Util.completaStringComEspacoAEsquerda(economiaComercial, 3));
		contaTxt.append(Util.completaStringComEspacoAEsquerda(economiaIndustrial, 3));
		contaTxt.append(Util.completaStringComEspacoAEsquerda(economiaPublica, 3));
		contaTxt.append(Util.completaStringComEspacoAEsquerda(inscSetorComercial, 2));
		contaTxt.append(Util.completaStringComEspacoAEsquerda(inscQuadra, 4));
		contaTxt.append(Util.completaStringComEspacoAEsquerda(inscLote, 4));
		contaTxt.append(Util.completaString(descricaoAgua, 38));
		contaTxt.append(Util.completaStringComEspacoAEsquerda(valorAgua, 14));
		contaTxt.append(Util.completaString(descricaoEsgoto, 38));
		contaTxt.append(Util.completaStringComEspacoAEsquerda(valorEsgoto, 14));
	}

	private String criarCodigoAuxiliar(EmitirContaTipo2Helper contaEmitirHelper, LigacaoAgua ligacaoAgua){

		String codigoAuxiliar = "";

		if(contaEmitirHelper.getIdLeituraSituacaoAtual() != null){
			codigoAuxiliar = codigoAuxiliar + Util.completaString(contaEmitirHelper.getIdLeituraSituacaoAtual().toString(), 1);
		}else{
			codigoAuxiliar = codigoAuxiliar + " ";
		}

		if(contaEmitirHelper.getDescricaoTipoConsumo() != null){
			codigoAuxiliar = codigoAuxiliar + Util.completaString(contaEmitirHelper.getDescricaoTipoConsumo(), 1);
		}else{
			codigoAuxiliar = codigoAuxiliar + " ";
		}

		if(contaEmitirHelper.getIdLeituraAnormalidadeFaturamento() != null){
			codigoAuxiliar = codigoAuxiliar + Util.completaString(contaEmitirHelper.getIdLeituraAnormalidadeFaturamento().toString(), 2);
		}else if(contaEmitirHelper.getDescricaoAnormalidadeConsumo() != null){
			codigoAuxiliar = codigoAuxiliar + Util.completaString(contaEmitirHelper.getDescricaoAnormalidadeConsumo(), 2);
		}else{
			codigoAuxiliar = codigoAuxiliar + "  ";
		}

		if((ligacaoAgua != null) && (ligacaoAgua.getNumeroConsumoMinimoAgua() != null)){
			codigoAuxiliar = codigoAuxiliar + "1";
		}else if((contaEmitirHelper.getConsumoEsgoto() != null) && (contaEmitirHelper.getConsumoAgua() != null)
						&& (contaEmitirHelper.getConsumoEsgoto().compareTo(contaEmitirHelper.getConsumoAgua()) > 0)){
			codigoAuxiliar = codigoAuxiliar + "2";
		}else{
			codigoAuxiliar = codigoAuxiliar + "0";
		}

		if(contaEmitirHelper.getIdImovelPerfil() != null){
			codigoAuxiliar = codigoAuxiliar + Util.completaString(contaEmitirHelper.getIdImovelPerfil().toString(), 1);
		}else{
			codigoAuxiliar = codigoAuxiliar + " ";
		}

		if((contaEmitirHelper.getDtLeituraAtual() != null) && (contaEmitirHelper.getDtLeituraAnterior() != null)){
			long numeroDiasLeitura = Util.diferencaDias(contaEmitirHelper.getDtLeituraAnterior(), contaEmitirHelper.getDtLeituraAtual());
			codigoAuxiliar = codigoAuxiliar + Util.completaString(String.valueOf(numeroDiasLeitura), 2);
		}else{
			codigoAuxiliar = codigoAuxiliar + "  ";
		}

		return codigoAuxiliar;
	}

	protected void gerarPrimeiraLinhaContasModelo2(StringBuilder contaTxt){

		contaTxt.append(Util.completaString("INICIO DO ARQUIVO - CA2FLASR", 62));
		contaTxt.append(Util.completaString("", 2176));
		contaTxt.append(Util.completaStringComEspacoAEsquerda("000000", 6));
		contaTxt.append(SEPARADOR_LINHA);

	}

	/**
	 * [UC0352] Emitir contas
	 * Gera o arquivo de Cartas de Cobrança Bancária.
	 * 
	 * @author Luciano Galvao
	 * @date 28/06/2012
	 * @param arquivo
	 * @param nomeArquivo
	 * @throws ControladorException
	 */
	public void enviarArquivoEmissaoContasParaBatch(StringBuilder sb, String nomeArquivo, Usuario usuario) throws ControladorException{

		RelatorioArquivoFaturamentoConvencional relatorio = new RelatorioArquivoFaturamentoConvencional(usuario);

		relatorio.addParametro("arquivoTexto", sb);
		relatorio.addParametro("nomeArquivo", nomeArquivo);
		relatorio.addParametro("tipoFormatoRelatorio", TarefaRelatorio.TIPO_ZIP);

		ServiceLocator.getInstancia().getControladorBatch().iniciarProcessoRelatorio(relatorio);

	}

	/**
	 * [UC3013] Gerar Declaração Anual Quitação Débitos
	 * [SB0002] Verificar Não Geração da Declaração para o Imóvel – Modelo 2
	 * 
	 * @author Hebert Falcão
	 * @date 28/04/2013
	 */
	public Boolean execParamVerificarNaoGeracaoDeclaracaoImovelModelo2(ParametroSistema parametroSistema, Integer idImovel,
					Integer anoBaseDeclaracaoQuitacaoDebitoAnual) throws ControladorException, CreateException{

		this.ejbCreate();

		Boolean retorno = Boolean.TRUE;

		try{
			// Obter Primeiro dia do ano, conforme ano base informado.
			Date dataInicial = Util.gerarDataInicialDoAnoApartirDoAnoRefencia(anoBaseDeclaracaoQuitacaoDebitoAnual);

			Integer referenciaInicial = Util.recuperaAnoMesDaData(dataInicial);

			// Obter Último dia do ano, conforme ano base informado.
			Date dataFinal = Util.gerarDataFinalDoAnoApartirDoAnoRefencia(anoBaseDeclaracaoQuitacaoDebitoAnual);

			Integer referenciaFinal = Util.recuperaAnoMesDaData(dataFinal);

			// Caso o imóvel tenha contas vencidas no ano de referência
			boolean verificarImovelContasVencidasAnoReferencia = repositorioFaturamento.verificarImovelContasVencidasAnoReferencia(
							idImovel, dataInicial, dataFinal);

			if(verificarImovelContasVencidasAnoReferencia){
				retorno = Boolean.FALSE;
			}

			if(retorno){
				// Caso o imóvel tenha guias de pagamento vencidas no ano de referência
				boolean verificarImovelGuiasVencidasAnoReferencia = repositorioFaturamento.verificarImovelGuiasVencidasAnoReferencia(
								idImovel, dataInicial, dataFinal);

				if(verificarImovelGuiasVencidasAnoReferencia){
					retorno = Boolean.FALSE;
				}
			}

			if(retorno){
				// Caso não existam pagamentos para o imóvel no ano de referência
				boolean verificarPagamentosHistoricoParaImovelAnoReferencia = repositorioFaturamento
								.verificarPagamentosHistoricoParaImovelAnoReferencia(idImovel, referenciaInicial, referenciaFinal);

				if(!verificarPagamentosHistoricoParaImovelAnoReferencia){
					retorno = Boolean.FALSE;
				}
			}

			// if(retorno){
			// // Caso exista pagamento ou parcelamento após o ano de referência
			// boolean verificarImovelParcelamentoAnoReferencia =
			// repositorioFaturamento.verificarImovelParcelamentoAnoReferencia(
			// idImovel, referenciaFinal, dataFinal);
			//
			// if(verificarImovelParcelamentoAnoReferencia){
			// retorno = Boolean.FALSE;
			// }
			// }
		}catch(ErroRepositorioException e){
			e.printStackTrace();
			throw new ControladorException("erro.sistema", e);
		}

		return retorno;
	}
	
	/**
	 * [UC0352] - Emitir Contas
	 * [SB0001] – Gerar Contas Modelo 1
	 * Responsável pela geração PDF com a conta
	 * 
	 * @author Anderson Italo
	 * @throws CreateException
	 * @date 07/08/2013
	 */
	public void execParamGerarContaPdfModelo1(ParametroSistema parametroSistema, FaturamentoGrupo faturamentoGrupo,
					Integer anoMesReferencia, Usuario usuario, List<EmitirContaTipo2Helper> colecaoContaTipo2HelperOrdenada)
					throws ControladorException, CreateException{

		this.ejbCreate();
		int totalBlocosContasAEmitir = (Util.dividirArredondarResultado(colecaoContaTipo2HelperOrdenada.size(),
						ConstantesSistema.QUANTIDADE_BLOCO_IMPRESSOES_EMISSAO_CONTA_FATURAMENTO, BigDecimal.ROUND_CEILING));
		int blocoEmitido = 1;

		List<RelatorioContaTipo2> listaRelatoriosGerados = new ArrayList<RelatorioContaTipo2>();

		while(!colecaoContaTipo2HelperOrdenada.isEmpty()){

			List<EmitirContaTipo2Helper> colecaoGeracaoParcialContaTipo2Helper = new ArrayList<EmitirContaTipo2Helper>();

			if(colecaoContaTipo2HelperOrdenada.size() <= (ConstantesSistema.QUANTIDADE_BLOCO_IMPRESSOES_EMISSAO_CONTA_FATURAMENTO)){

				colecaoGeracaoParcialContaTipo2Helper.addAll(colecaoContaTipo2HelperOrdenada);

			}else{

				colecaoGeracaoParcialContaTipo2Helper.addAll(colecaoContaTipo2HelperOrdenada.subList(0,
								(ConstantesSistema.QUANTIDADE_BLOCO_IMPRESSOES_EMISSAO_CONTA_FATURAMENTO)));
			}

			// Inicia o Relatório
			RelatorioContaTipo2 relatorioConta = new RelatorioContaTipo2(usuario);
			int tipoRelatorio = TarefaRelatorio.TIPO_PDF;

			String descricaoGrupo = faturamentoGrupo.getDescricao();
			if(descricaoGrupo != null && descricaoGrupo.length() > 10){

				descricaoGrupo = descricaoGrupo.substring(0, 10);
			}

			String mensagemArquivo = "GRUPO:" + descricaoGrupo + " REF:" + Util.formatarAnoMesParaMesAno(anoMesReferencia) + " PARTE:"
							+ blocoEmitido + "/" + totalBlocosContasAEmitir;

			relatorioConta.addParametro("colecaoHelperContas", colecaoGeracaoParcialContaTipo2Helper);
			relatorioConta.addParametro("tipoFormatoRelatorio", tipoRelatorio);
			relatorioConta.addParametro("descricaoArquivo", mensagemArquivo);

			listaRelatoriosGerados.add(relatorioConta);

			colecaoContaTipo2HelperOrdenada.removeAll(colecaoGeracaoParcialContaTipo2Helper);
			blocoEmitido++;
		}

		// manda os relatórios para execução
		for(RelatorioContaTipo2 relatorioConta : listaRelatoriosGerados){

			getControladorBatch().iniciarProcessoRelatorio(relatorioConta);
		}
	}

	/**
	 * [UC0352] - Emitir Contas
	 * [SB0033] – Gerar Contas Modelo 3
	 * Responsável pela geração PDF com a conta
	 * 
	 * @author Anderson Italo
	 * @date 07/08/2013
	 */
	public void execParamGerarContaPdfModelo3(ParametroSistema parametroSistema, FaturamentoGrupo faturamentoGrupo,
					Integer anoMesReferencia, Usuario usuario, List<EmitirContaTipo2Helper> colecaoContaTipo2HelperOrdenada)
					throws ControladorException, ErroRepositorioException,
					IOException, CreateException{

		this.ejbCreate();

		int totalBlocosContasAEmitir = (Util.dividirArredondarResultado(colecaoContaTipo2HelperOrdenada.size(),
						ConstantesSistema.QUANTIDADE_BLOCO_IMPRESSOES_EMISSAO_CONTA_FATURAMENTO, BigDecimal.ROUND_CEILING));
		int blocoEmitido = 1;

		List<RelatorioContaModelo3> listaRelatoriosGerados = new ArrayList<RelatorioContaModelo3>();

		while(!colecaoContaTipo2HelperOrdenada.isEmpty()){

			List<EmitirContaTipo2Helper> colecaoGeracaoParcialContaTipo2Helper = new ArrayList<EmitirContaTipo2Helper>();

			if(colecaoContaTipo2HelperOrdenada.size() <= (ConstantesSistema.QUANTIDADE_BLOCO_IMPRESSOES_EMISSAO_CONTA_FATURAMENTO)){

				colecaoGeracaoParcialContaTipo2Helper.addAll(colecaoContaTipo2HelperOrdenada);

			}else{

				colecaoGeracaoParcialContaTipo2Helper.addAll(colecaoContaTipo2HelperOrdenada.subList(0,
								(ConstantesSistema.QUANTIDADE_BLOCO_IMPRESSOES_EMISSAO_CONTA_FATURAMENTO)));
			}

			if(!Util.isVazioOrNulo(colecaoGeracaoParcialContaTipo2Helper)){

				RelatorioContaModelo3 relatorioConta = new RelatorioContaModelo3(usuario);
				int tipoRelatorio = TarefaRelatorio.TIPO_PDF;

				String descricaoGrupo = faturamentoGrupo.getDescricao();
				if(descricaoGrupo != null && descricaoGrupo.length() > 10){

					descricaoGrupo = descricaoGrupo.substring(0, 10);
				}

				String mensagemArquivo = "GRUPO:" + descricaoGrupo + " REF:" + Util.formatarAnoMesParaMesAno(anoMesReferencia) + " PARTE:"
								+ blocoEmitido + "/" + totalBlocosContasAEmitir;

				relatorioConta.addParametro("tipoFormatoRelatorio", tipoRelatorio);
				relatorioConta.addParametro("descricaoArquivo", mensagemArquivo);
				relatorioConta.addParametro("colecaoContaHelper", colecaoGeracaoParcialContaTipo2Helper);
				relatorioConta.addParametro("faturamentoGrupo", faturamentoGrupo);
				relatorioConta.addParametro("anoMesReferencia", anoMesReferencia);

				listaRelatoriosGerados.add(relatorioConta);

				colecaoContaTipo2HelperOrdenada.removeAll(colecaoGeracaoParcialContaTipo2Helper);
				blocoEmitido++;
			}
		}

		for(RelatorioContaModelo3 relatorioConta : listaRelatoriosGerados){

			getControladorBatch().iniciarProcessoRelatorio(relatorioConta);
		}
	}

	/**
	 * Formato da descrição da rubrica - Modelo 1
	 * 
	 * @author Hebert Falcão
	 * @date 18/12/2013
	 */
	public String execParamFormatoDescricaoRubricaModelo1(ParametroSistema parametroSistema, String idDebitoTipo, String descricaoDebito)
					throws ControladorException, CreateException{

		this.ejbCreate();

		String tamanhoDescricaoRubricaStr = ParametroFaturamento.P_TAMANHO_DESCRICAO_RUBRICA.executar();
		Integer tamanhoDescricaoRubrica = Util.converterStringParaInteger(tamanhoDescricaoRubricaStr);

		String descricaoRubrica = (Util.completaString(idDebitoTipo + descricaoDebito, tamanhoDescricaoRubrica)).substring(0,
						tamanhoDescricaoRubrica);

		return descricaoRubrica;
	}

	/**
	 * Formato da descrição da rubrica - Modelo 2
	 * 
	 * @author Hebert Falcão
	 * @date 18/12/2013
	 */
	public String execParamFormatoDescricaoRubricaModelo2(ParametroSistema parametroSistema, String idDebitoTipo, String descricaoDebito)
					throws ControladorException, CreateException{

		this.ejbCreate();

		String tamanhoDescricaoRubricaStr = ParametroFaturamento.P_TAMANHO_DESCRICAO_RUBRICA.executar();
		Integer tamanhoDescricaoRubrica = Util.converterStringParaInteger(tamanhoDescricaoRubricaStr);

		String descricaoRubrica = (Util.completaString(idDebitoTipo + "-" + descricaoDebito, tamanhoDescricaoRubrica)).substring(0,
						tamanhoDescricaoRubrica);

		return descricaoRubrica;
	}

	/**
	 * [UC0352] - Emitir Contas
	 * [SB0002 - Gerar Arquivo TXT Contas - Modelo 3]
	 * Responsável pela geração de arquivo tipo TXT com as contas
	 * 
	 * @author Hebert Falcão
	 * @date 11/01/2014
	 */
	public void execParamGerarArquivoTxtContasModelo3(ParametroSistema parametroSistema, List<EmitirContaTipo2Helper> listaConta,
					FaturamentoGrupo faturamentoGrupo, Integer idEmpresa, Usuario usuario,
					Collection<EmitirContaTipo2Helper> listaContaEndAlternativo, ComparatorChain sortListaConta,
					ComparatorChain sortListaContaEndAlternativo, Collection<EmitirContaTipo2Helper> listaContaBraille)
					throws ControladorException, ErroRepositorioException, IOException,
					CreateException{

		this.ejbCreate();

		this.gerarArquivoContasModelo3(listaConta, sortListaConta, faturamentoGrupo, "FATURA_NORMAL", usuario, false);

		this.gerarArquivoContasModelo3((List) listaContaEndAlternativo, sortListaContaEndAlternativo, faturamentoGrupo,
						"FATURA_ENDERECO_ALTER", usuario, true);

		this.gerarArquivoContasBrailleModelo3(listaContaBraille, sortListaConta, faturamentoGrupo, usuario);
	}

	/**
	 * [UC0352] - Emitir Contas
	 * [SB0002 - Gerar Arquivo TXT Contas - Modelo 3]
	 * 
	 * @author Hebert Falcão
	 * @date 11/01/2014
	 */
	private void gerarArquivoContasModelo3(List<EmitirContaTipo2Helper> listaEmitirContaTipo2Helper, ComparatorChain ordenacao,
					FaturamentoGrupo faturamentoGrupo, String tipoArquivo, Usuario usuario, boolean enderecoAlternativo)
					throws ControladorException{

		if(!Util.isVazioOrNulo(listaEmitirContaTipo2Helper)){

			// Ordenando a coleção
			Collections.sort((List) listaEmitirContaTipo2Helper, ordenacao);

			int sequencialImpressao = 1;
			int sequencialPorSetor = 0;

			int totalContas = listaEmitirContaTipo2Helper.size();

			Map<Integer, Map<Integer, EmitirContaTipo2Helper>> mapContasPorSetorComercial = new HashMap<Integer, Map<Integer, EmitirContaTipo2Helper>>();

			Integer idSetorComercial = null;

			Map<Integer, EmitirContaTipo2Helper> mapContasPorSequencial = null;

			Collection<Integer> colecaoIdSetorComercial = new ArrayList<Integer>();

			// Define o sequencial de impressão e agrupa os registros por setor comercial. Esse
			// agrupamento é necessário para realizar a quebra e divisão do arquivo, pois ela é
			// feita pelo setor

			for(EmitirContaTipo2Helper contaTipo2Helper : listaEmitirContaTipo2Helper){
				contaTipo2Helper.setSequencialImpressao(sequencialImpressao);
				contaTipo2Helper.setTotalContasImpressao(totalContas);

				idSetorComercial = contaTipo2Helper.getIdSetorComercial();

				if(mapContasPorSetorComercial.containsKey(idSetorComercial)){
					mapContasPorSequencial = mapContasPorSetorComercial.get(idSetorComercial);
				}else{
					mapContasPorSequencial = new HashMap<Integer, EmitirContaTipo2Helper>();
					colecaoIdSetorComercial.add(idSetorComercial);
				}

				// Não pode utilizar o sequencial de impressão, pois gera erro na hora de dividir
				sequencialPorSetor = mapContasPorSequencial.size() + 1;

				// System.out.println("** idSetorComercial - " + idSetorComercial +
				// "; sequencialPorSetor - " + sequencialPorSetor);

				mapContasPorSequencial.put(sequencialPorSetor, contaTipo2Helper);

				mapContasPorSetorComercial.put(idSetorComercial, mapContasPorSequencial);

				sequencialImpressao++;
			}

			List<EmitirContaTipo2Helper> listaEmitirContaTipo2HelperOrdenada = new ArrayList<EmitirContaTipo2Helper>();

			Integer tamanho = null;
			Integer resto = null;
			Integer metade = null;

			// Não pode iterar o mapContasPorSetorComercial, pois a ordem de inclusão não é
			// respeitada
			for(Integer idSetorComercialAux : colecaoIdSetorComercial){
				// System.out.println("** idSetorComercialAux " + idSetorComercialAux);

				mapContasPorSequencial = mapContasPorSetorComercial.get(idSetorComercialAux);

				tamanho = mapContasPorSequencial.size();

				resto = tamanho % 2;

				if(resto == 1){
					metade = (tamanho / 2) + 1;
				}else{
					metade = tamanho / 2;
				}

				for(int i = 1; i <= metade; i++){
					listaEmitirContaTipo2HelperOrdenada.add(mapContasPorSequencial.get(i));

					if(mapContasPorSequencial.get(i + metade) != null){
						listaEmitirContaTipo2HelperOrdenada.add(mapContasPorSequencial.get(i + metade));
					}
				}
			}

			// Geração do arquivo
			Integer idFaturamentoGrupo = faturamentoGrupo.getId();

			Integer anoMesReferencia = faturamentoGrupo.getAnoMesReferencia();

			StringBuilder arquivo = this.gerarLinhasContasModelo3(listaEmitirContaTipo2HelperOrdenada, anoMesReferencia,
							enderecoAlternativo);

			// Envia o arquivo para ser gerado como um relatório batch
			String nomeArquivo = tipoArquivo + "_g" + idFaturamentoGrupo + "_" + anoMesReferencia + ".txt";

			this.enviarArquivoEmissaoContasParaBatch(arquivo, nomeArquivo, usuario);
		}
	}

	/**
	 * [UC0352] - Emitir Contas
	 * [SB0002 - Gerar Arquivo TXT Contas - Modelo 3]
	 * 
	 * @author Hebert Falcão
	 * @date 11/01/2014
	 */
	private StringBuilder gerarLinhasContasModelo3(List<EmitirContaTipo2Helper> listaConta, Integer anoMesReferencia,
					boolean enderecoAlternativo) throws ControladorException{

		StringBuilder contaTxt = new StringBuilder();

		SistemaParametro sistemaParametro = this.getControladorUtil().pesquisarParametrosDoSistema();

		String quebraDeSetorComerical = "";
		Integer idSetorComercialAtual = null;
		Integer idSetorComercialAnterior = null;

		Integer numeroFolha = 0;
		Integer contadorAux = 1;

		Integer qtdCaracateres = 1501;

		if(enderecoAlternativo){
			qtdCaracateres = 1651;
		}

		String numeroFolhaStr = null;

		// Gerar a linha com os dados da conta
		for(EmitirContaTipo2Helper contaEmitirHelper : listaConta){
			idSetorComercialAtual = contaEmitirHelper.getIdSetorComercial();

			if(contadorAux > 2){
				// Mudando a folha, pois o arquivo deve ter apenas duas linhas por folha.

				contadorAux = 1;
				numeroFolha = numeroFolha + 1;
			}else{
				if(idSetorComercialAnterior != null && idSetorComercialAtual.intValue() != idSetorComercialAnterior.intValue()){
					// Cria uma linha, pois o arquivo deve ter duas linhas do mesmo setor por folha

					contadorAux = 1;

					numeroFolhaStr = Integer.toString(numeroFolha);

					contaTxt.append(Util.completarStringZeroEsquerda(numeroFolhaStr, 1413));
					contaTxt.append(Util.completarStringZeroDireita("", qtdCaracateres - 1413));
					contaTxt.append(SEPARADOR_LINHA);

					numeroFolha = numeroFolha + 1;
				}
			}

			if(idSetorComercialAnterior == null || idSetorComercialAtual.intValue() != idSetorComercialAnterior.intValue()){
				// Registra a quebra do setor

				numeroFolha = numeroFolha + 1;

				idSetorComercialAnterior = idSetorComercialAtual;

				numeroFolhaStr = Integer.toString(numeroFolha);

				contaTxt.append(Util.completarStringZeroEsquerda(numeroFolhaStr, 1413));
				contaTxt.append(Util.completarStringZeroDireita("", qtdCaracateres - 1413));
				contaTxt.append(SEPARADOR_LINHA);

				contaTxt.append(Util.completarStringCaractereDireita("", qtdCaracateres, '1'));
				contaTxt.append(SEPARADOR_LINHA);

				quebraDeSetorComerical = "*";

				numeroFolha = numeroFolha + 1;
			}else{
				quebraDeSetorComerical = "";
			}

			this.gerarDadosGeraisContasParte1Modelo3(contaTxt, contaEmitirHelper);
			this.gerarUltimosConsumosLeiturasContasModelo3(contaTxt, contaEmitirHelper);
			this.gerarDebitosAnterioresContasModelo3(contaTxt, contaEmitirHelper, anoMesReferencia, sistemaParametro);
			this.gerarValoresContasModelo3(contaTxt, contaEmitirHelper);
			this.gerarDadosGeraisContasParte2Modelo3(contaTxt, contaEmitirHelper, sistemaParametro, enderecoAlternativo, numeroFolha,
							quebraDeSetorComerical);

			contaTxt.append(SEPARADOR_LINHA);

			contadorAux = contadorAux + 1;
		}

		if(contadorAux == 2){
			numeroFolhaStr = Integer.toString(numeroFolha);

			contaTxt.append(Util.completarStringZeroEsquerda(numeroFolhaStr, 1413));
			contaTxt.append(Util.completarStringZeroDireita("", qtdCaracateres - 1413));
			contaTxt.append(SEPARADOR_LINHA);
		}

		return contaTxt;
	}

	/**
	 * [UC0352] - Emitir Contas
	 * [SB0002 - Gerar Arquivo TXT Contas - Modelo 3]
	 * 
	 * @author Hebert Falcão
	 * @date 11/01/2014
	 */
	private void gerarDadosGeraisContasParte1Modelo3(StringBuilder contaTxt, EmitirContaTipo2Helper contaEmitirHelper)
					throws ControladorException{

		Integer idConta = contaEmitirHelper.getIdConta();

		FiltroConta filtroConta = new FiltroConta();
		filtroConta.adicionarParametro(new ParametroSimples(FiltroConta.ID, idConta));

		Collection<Conta> colecaoConta = this.getControladorUtil().pesquisar(filtroConta, Conta.class.getName());

		Conta conta = null;

		if(!Util.isVazioOrNulo(colecaoConta)){
			conta = (Conta) Util.retonarObjetoDeColecao(colecaoConta);
		}

		// 1. Matrícula do Imóvel [Formato: 999999999]
		String matriculaImovelStr = "";

		Integer matriculaImovel = contaEmitirHelper.getIdImovel();

		if(matriculaImovel != null){
			matriculaImovelStr = Integer.toString(matriculaImovel);
		}

		matriculaImovelStr = Util.completarStringZeroEsquerda(matriculaImovelStr, 9);
		contaTxt.append(matriculaImovelStr);

		// 2.Sequência de Impressão [Formato: 999999]
		String sequencialImpressaoStr = "";

		Integer sequencialImpressao = contaEmitirHelper.getSequencialImpressao();

		if(sequencialImpressao != null){
			sequencialImpressaoStr = Integer.toString(sequencialImpressao);
		}

		sequencialImpressaoStr = Util.completarStringZeroEsquerda(sequencialImpressaoStr, 6);
		contaTxt.append(sequencialImpressaoStr);

		// 3.Nome do Cliente
		String nomeCliente = contaEmitirHelper.getNomeCliente();
		nomeCliente = Util.completaString(nomeCliente, 25);
		contaTxt.append(nomeCliente);

		// 4.Endereço
		String endereco = contaEmitirHelper.getEndereco();
		endereco = Util.completaString(endereco, 50);
		contaTxt.append(endereco);

		// 5.Localidade
		String descricaoLocalidade = contaEmitirHelper.getDescricaoLocalidade();
		descricaoLocalidade = Util.completaString(descricaoLocalidade, 25);
		contaTxt.append(descricaoLocalidade);

		// 6.Rota Entrega [Formato: 99999]
		String idRotaStr = "";

		Rota rota = conta.getRota();

		if(rota != null){
			Integer idRota = rota.getId();
			idRotaStr = Integer.toString(idRota);
		}

		idRotaStr = Util.completarStringZeroEsquerda(idRotaStr, 5);
		contaTxt.append(idRotaStr);

		// 7.Cep [Formato: 99999999]
		String cepImovelFormatado = contaEmitirHelper.getCepImovelFormatado();

		if(cepImovelFormatado != null){
			cepImovelFormatado = cepImovelFormatado.replaceAll("-", "");
		}

		cepImovelFormatado = Util.completarStringZeroEsquerda(cepImovelFormatado, 8);
		contaTxt.append(cepImovelFormatado);

		// 8. Inscrição
		String inscLocalidadeStr = "";
		Integer inscLocalidade = contaEmitirHelper.getInscLocalidade();

		if(inscLocalidade != null){
			inscLocalidadeStr = Integer.toString(inscLocalidade);
		}

		inscLocalidadeStr = Util.completarStringZeroEsquerda(inscLocalidadeStr, 3);

		String inscSetorComercialStr = "";
		Integer inscSetorComercial = contaEmitirHelper.getInscSetorComercial();

		if(inscSetorComercial != null){
			inscSetorComercialStr = Integer.toString(inscSetorComercial);
		}

		inscSetorComercialStr = Util.completarStringZeroEsquerda(inscSetorComercialStr, 2);

		String inscQuadraStr = "";
		Integer inscQuadra = contaEmitirHelper.getInscQuadra();

		if(inscQuadra != null){
			inscQuadraStr = Integer.toString(inscQuadra);
		}

		inscQuadraStr = Util.completarStringZeroEsquerda(inscQuadraStr, 3);

		String inscLoteStr = "";
		Short inscLote = contaEmitirHelper.getInscLote();

		if(inscLote != null){
			inscLoteStr = Integer.toString(inscLote);
		}

		inscLoteStr = Util.completarStringZeroEsquerda(inscLoteStr, 4);

		String inscricao = inscLocalidadeStr + inscSetorComercialStr + inscQuadraStr + inscLoteStr;
		contaTxt.append(inscricao);

		// Mês Faturamento / Ano Faturamento
		String mesStr = "";
		String anoStr = "";

		Integer anoMesConta = contaEmitirHelper.getAnoMesConta();

		if(anoMesConta != null){
			String anoMesContaStr = Integer.toString(anoMesConta);

			mesStr = anoMesContaStr.substring(4, 6);

			Integer mes = Integer.valueOf(mesStr);

			mesStr = Util.retornaDescricaoAbreviadoMes(mes);
			mesStr = mesStr.toUpperCase();

			anoStr = anoMesContaStr.substring(0, 4);
		}

		// 9. Mês Faturamento
		mesStr = Util.completaString(mesStr, 3) + "/";
		contaTxt.append(mesStr);

		// 10.Ano Faturamento [Formato: 9999]
		anoStr = Util.completarStringZeroEsquerda(anoStr, 4);
		contaTxt.append(anoStr);

		// 11. Dígito [Formato: 9]
		contaTxt.append("0");

		// 12.Responsável [Formato: 9999999]
		String idClienteResponsavelStr = "";

		Integer idClienteResponsavel = contaEmitirHelper.getIdClienteResponsavel();

		if(idClienteResponsavel != null && idClienteResponsavel != 0){
			idClienteResponsavelStr = Integer.toString(idClienteResponsavel);
		}

		idClienteResponsavelStr = Util.completarStringZeroEsquerda(idClienteResponsavelStr, 7);
		contaTxt.append(idClienteResponsavelStr);

		// 13. Economias Residenciais
		String econResidencialStr = "";

		Short econResidencial = contaEmitirHelper.getEconResidencial();

		if(econResidencial != null && econResidencial.intValue() != 0){
			econResidencialStr = Short.toString(econResidencial);
		}

		econResidencialStr = Util.completarStringZeroEsquerda(econResidencialStr, 3);
		contaTxt.append(econResidencialStr);

		// 14. Economias Comerciais
		String econComercialStr = "";

		Short econComercial = contaEmitirHelper.getEconComercial();

		if(econComercial != null && econComercial.intValue() != 0){
			econComercialStr = Short.toString(econComercial);
		}

		econComercialStr = Util.completarStringZeroEsquerda(econComercialStr, 3);
		contaTxt.append(econComercialStr);

		// 15. Economias Industriais
		String econIndustrialStr = "";

		Short econIndustrial = contaEmitirHelper.getEconIndustrial();

		if(econIndustrial != null && econIndustrial.intValue() != 0){
			econIndustrialStr = Short.toString(econIndustrial);
		}

		econIndustrialStr = Util.completarStringZeroEsquerda(econIndustrialStr, 3);
		contaTxt.append(econIndustrialStr);

		// 16. Economias Públicas
		String econPublicaStr = "";

		Short econPublica = contaEmitirHelper.getEconPublica();

		if(econPublica != null && econPublica.intValue() != 0){
			econPublicaStr = Short.toString(econPublica);
		}

		econPublicaStr = Util.completarStringZeroEsquerda(econPublicaStr, 3);
		contaTxt.append(econPublicaStr);

		// 17.Situação Agua
		String descricaoLigacaoAguaSituacao = "";

		Integer idLigacaoAguaSituacao = contaEmitirHelper.getIdLigacaoAguaSituacao();

		if(idLigacaoAguaSituacao != null){
			FiltroLigacaoAguaSituacao filtroLigacaoAguaSituacao = new FiltroLigacaoAguaSituacao();
			filtroLigacaoAguaSituacao.adicionarParametro(new ParametroSimples(FiltroLigacaoAguaSituacao.ID, idLigacaoAguaSituacao));

			Collection<LigacaoAguaSituacao> colecaoLigacaoAguaSituacao = this.getControladorUtil().pesquisar(filtroLigacaoAguaSituacao,
							LigacaoAguaSituacao.class.getName());

			if(!Util.isVazioOrNulo(colecaoLigacaoAguaSituacao)){
				LigacaoAguaSituacao ligacaoAguaSituacao = (LigacaoAguaSituacao) Util.retonarObjetoDeColecao(colecaoLigacaoAguaSituacao);
				descricaoLigacaoAguaSituacao = ligacaoAguaSituacao.getDescricao();
			}
		}

		descricaoLigacaoAguaSituacao = Util.completaString(descricaoLigacaoAguaSituacao, 9);
		contaTxt.append(descricaoLigacaoAguaSituacao);

		// 18. Situação Esgoto
		String descricaoLigacaoEsgotoSituacao = "";

		Integer idLigacaoEsgotoSituacao = contaEmitirHelper.getIdLigacaoEsgotoSituacao();

		if(idLigacaoEsgotoSituacao != null){
			FiltroLigacaoEsgotoSituacao filtroLigacaoEsgotoSituacao = new FiltroLigacaoEsgotoSituacao();
			filtroLigacaoEsgotoSituacao.adicionarParametro(new ParametroSimples(FiltroLigacaoEsgotoSituacao.ID, idLigacaoEsgotoSituacao));

			Collection<LigacaoEsgotoSituacao> colecaoLigacaoEsgotoSituacao = this.getControladorUtil().pesquisar(
							filtroLigacaoEsgotoSituacao, LigacaoEsgotoSituacao.class.getName());

			if(!Util.isVazioOrNulo(colecaoLigacaoEsgotoSituacao)){
				LigacaoEsgotoSituacao ligacaoEsgotoSituacao = (LigacaoEsgotoSituacao) Util
								.retonarObjetoDeColecao(colecaoLigacaoEsgotoSituacao);
				descricaoLigacaoEsgotoSituacao = ligacaoEsgotoSituacao.getDescricao();
			}
		}

		descricaoLigacaoEsgotoSituacao = Util.completaString(descricaoLigacaoEsgotoSituacao, 9);
		contaTxt.append(descricaoLigacaoEsgotoSituacao);

		// 19. Data Leitura [Formato: DD/MM]
		String dataLeituraAtualStr = "";

		Date dataLeituraAtual = contaEmitirHelper.getDtLeituraAtual();

		if(dataLeituraAtual != null){
			dataLeituraAtualStr = Util.formatarDataDiaMesComBarra(dataLeituraAtual);
		}

		dataLeituraAtualStr = Util.completaString(dataLeituraAtualStr, 5);
		contaTxt.append(dataLeituraAtualStr);

		// 20. Leitura [Formato: 999999]
		String leituraAtualStr = "";

		Integer leituraAtual = contaEmitirHelper.getLeituraAtual();

		if(leituraAtual != null){
			leituraAtualStr = Integer.toString(leituraAtual);
		}

		leituraAtualStr = Util.completarStringZeroEsquerda(leituraAtualStr, 6);
		contaTxt.append(leituraAtualStr);

		// 21.Leitura Anterior [Formato: 999999]
		String leituraAnteriorStr = "";

		Integer leituraAnterior = contaEmitirHelper.getLeituraAnterior();

		if(leituraAnterior != null){
			leituraAnteriorStr = Integer.toString(leituraAnterior);
		}

		leituraAnteriorStr = Util.completarStringZeroEsquerda(leituraAnteriorStr, 6);
		contaTxt.append(leituraAnteriorStr);

		// 22. Consumo [Formato: 999999]
		String consumoFaturadoStr = "";

		Integer consumoFaturado = contaEmitirHelper.getConsumoFaturado();

		if(consumoFaturado != null){
			consumoFaturadoStr = Integer.toString(consumoFaturado);
		}

		consumoFaturadoStr = Util.completarStringZeroEsquerda(consumoFaturadoStr, 6);
		contaTxt.append(consumoFaturadoStr);

		// 23. Vencimento [Formato: DD/MM/AAAA]
		String dataVencimentoStr = "";

		Date dataVencimento = contaEmitirHelper.getDataVencimento();

		if(dataVencimento != null){
			dataVencimentoStr = Util.formatarData(dataVencimento);
		}

		dataVencimentoStr = Util.completaString(dataVencimentoStr, 10);
		contaTxt.append(dataVencimentoStr);

		// 24.Valor da Conta 1 [Formato: 9999999999]
		String valorTotalContaStr = "";

		BigDecimal valorTotalConta = contaEmitirHelper.getValorTotalConta();

		if(valorTotalConta != null){
			valorTotalContaStr = Util.formatarBigDecimalParaString(valorTotalConta, 2);
		}

		valorTotalContaStr = Util.completarStringZeroEsquerda(valorTotalContaStr, 10);
		contaTxt.append(valorTotalContaStr);

		// 25. Valor da Conta 2 [Formato: 9999999999]
		Short indicadorCodigoBarras = contaEmitirHelper.getIndicadorCodigoBarras();
		Short indicadorDebitoAutomatico = contaEmitirHelper.getIndicadorDebitoAutomatico();

		if((indicadorCodigoBarras != null && indicadorCodigoBarras.shortValue() == ConstantesSistema.NAO.shortValue())
						|| (indicadorDebitoAutomatico != null && indicadorDebitoAutomatico.shortValue() == ConstantesSistema.SIM
										.shortValue())){
			valorTotalContaStr = Util.completarStringZeroEsquerda("", 10);
		}

		contaTxt.append(valorTotalContaStr);
	}

	/**
	 * [UC0352] - Emitir Contas
	 * [SB0002 - Gerar Arquivo TXT Contas - Modelo 3]
	 * 
	 * @author Hebert Falcão
	 * @date 11/01/2014
	 */
	private void gerarUltimosConsumosLeiturasContasModelo3(StringBuilder contaTxt, EmitirContaTipo2Helper contaEmitirHelper)
					throws ControladorException{

		// 26. Últimos Consumos (ocorrendo 6 vezes)
		Object[] consumoAnormalidadeArray = null;

		Integer idTipoLigacao = contaEmitirHelper.getIdTipoLigacao();

		Integer consumoContaUltimosConsumos = null;
		String consumoContaUltimosConsumosStr = "";

		Integer anoMesContaUltimosConsumos = null;
		String anoMesContaUltimosConsumosStr = "";

		Integer mesContaUltimosConsumos = null;
		String mesContaUltimosConsumosStr = "";

		Integer consumoAnormalidadeContaUltimosConsumos = null;
		String consumoAnormalidadeContaUltimosConsumosStr = "";

		Integer leituraAnormalidadeContaUltimosConsumos = null;
		String leituraAnormalidadeContaUltimosConsumosStr = "";

		// 6Conta
		consumoContaUltimosConsumos = contaEmitirHelper.getConsumo6Conta();

		mesContaUltimosConsumosStr = "";
		consumoContaUltimosConsumosStr = "";
		consumoAnormalidadeContaUltimosConsumosStr = "";
		leituraAnormalidadeContaUltimosConsumosStr = "";

		if(consumoContaUltimosConsumos != null){
			// Mês de Consumo [Formato: Texto]
			anoMesContaUltimosConsumos = contaEmitirHelper.getAnoMes6Conta();

			if(anoMesContaUltimosConsumos != null){
				anoMesContaUltimosConsumosStr = Integer.toString(anoMesContaUltimosConsumos);

				mesContaUltimosConsumosStr = anoMesContaUltimosConsumosStr.substring(4, 6);

				mesContaUltimosConsumos = Integer.valueOf(mesContaUltimosConsumosStr);

				mesContaUltimosConsumosStr = Util.retornaDescricaoAbreviadoMes(mesContaUltimosConsumos);
				mesContaUltimosConsumosStr = mesContaUltimosConsumosStr.toUpperCase();
			}

			// Consumo Anterior [Formato: 999999]
			consumoContaUltimosConsumosStr = Integer.toString(consumoContaUltimosConsumos);

			// Anormalidades [Formato: Texto(XX-99)]
			try{
				consumoAnormalidadeArray = repositorioMicromedicao.pesquisarConsumoELeituraAnormalidadePorConsumoHistorico(
								contaEmitirHelper.getIdImovel(), anoMesContaUltimosConsumos, idTipoLigacao);
			}catch(ErroRepositorioException e){
				throw new ControladorException("erro.sistema", e);
			}

			if(consumoAnormalidadeArray != null){
				consumoAnormalidadeContaUltimosConsumos = (Integer) consumoAnormalidadeArray[0];

				if(consumoAnormalidadeContaUltimosConsumos != null){
					FiltroConsumoAnormalidade filtroConsumoAnormalidade = new FiltroConsumoAnormalidade();
					filtroConsumoAnormalidade.adicionarParametro(new ParametroSimples(FiltroConsumoAnormalidade.ID,
									consumoAnormalidadeContaUltimosConsumos));

					Collection<ConsumoAnormalidade> colecaoConsumoAnormalidade = this.getControladorUtil().pesquisar(
									filtroConsumoAnormalidade, ConsumoAnormalidade.class.getName());

					if(!Util.isVazioOrNulo(colecaoConsumoAnormalidade)){
						ConsumoAnormalidade consumoAnormalidade = (ConsumoAnormalidade) Util
										.retonarObjetoDeColecao(colecaoConsumoAnormalidade);
						consumoAnormalidadeContaUltimosConsumosStr = consumoAnormalidade.getDescricaoAbreviada();
					}
				}

				leituraAnormalidadeContaUltimosConsumos = (Integer) consumoAnormalidadeArray[1];

				if(leituraAnormalidadeContaUltimosConsumos != null){
					leituraAnormalidadeContaUltimosConsumosStr = Integer.toString(leituraAnormalidadeContaUltimosConsumos);
				}
			}
		}

		mesContaUltimosConsumosStr = Util.completaString(mesContaUltimosConsumosStr, 3);
		contaTxt.append(mesContaUltimosConsumosStr);

		consumoContaUltimosConsumosStr = Util.completarStringZeroEsquerda(consumoContaUltimosConsumosStr, 6);
		contaTxt.append(consumoContaUltimosConsumosStr);

		consumoAnormalidadeContaUltimosConsumosStr = Util.completaStringComEspacoAEsquerda(consumoAnormalidadeContaUltimosConsumosStr, 2);
		contaTxt.append(consumoAnormalidadeContaUltimosConsumosStr);

		contaTxt.append("-");

		leituraAnormalidadeContaUltimosConsumosStr = Util.completarStringZeroEsquerda(leituraAnormalidadeContaUltimosConsumosStr, 2);
		contaTxt.append(leituraAnormalidadeContaUltimosConsumosStr);

		// 5Conta
		consumoContaUltimosConsumos = contaEmitirHelper.getConsumo5Conta();

		mesContaUltimosConsumosStr = "";
		consumoContaUltimosConsumosStr = "";
		consumoAnormalidadeContaUltimosConsumosStr = "";
		leituraAnormalidadeContaUltimosConsumosStr = "";

		if(consumoContaUltimosConsumos != null){
			// Mês de Consumo [Formato: Texto]
			anoMesContaUltimosConsumos = contaEmitirHelper.getAnoMes5Conta();

			if(anoMesContaUltimosConsumos != null){
				anoMesContaUltimosConsumosStr = Integer.toString(anoMesContaUltimosConsumos);

				mesContaUltimosConsumosStr = anoMesContaUltimosConsumosStr.substring(4, 6);

				mesContaUltimosConsumos = Integer.valueOf(mesContaUltimosConsumosStr);

				mesContaUltimosConsumosStr = Util.retornaDescricaoAbreviadoMes(mesContaUltimosConsumos);
				mesContaUltimosConsumosStr = mesContaUltimosConsumosStr.toUpperCase();
			}

			// Consumo Anterior [Formato: 999999]
			consumoContaUltimosConsumosStr = Integer.toString(consumoContaUltimosConsumos);

			// Anormalidades [Formato: Texto(XX-99)]
			try{
				consumoAnormalidadeArray = repositorioMicromedicao.pesquisarConsumoELeituraAnormalidadePorConsumoHistorico(
								contaEmitirHelper.getIdImovel(), anoMesContaUltimosConsumos, idTipoLigacao);
			}catch(ErroRepositorioException e){
				throw new ControladorException("erro.sistema", e);
			}

			if(consumoAnormalidadeArray != null){
				consumoAnormalidadeContaUltimosConsumos = (Integer) consumoAnormalidadeArray[0];

				if(consumoAnormalidadeContaUltimosConsumos != null){
					FiltroConsumoAnormalidade filtroConsumoAnormalidade = new FiltroConsumoAnormalidade();
					filtroConsumoAnormalidade.adicionarParametro(new ParametroSimples(FiltroConsumoAnormalidade.ID,
									consumoAnormalidadeContaUltimosConsumos));

					Collection<ConsumoAnormalidade> colecaoConsumoAnormalidade = this.getControladorUtil().pesquisar(
									filtroConsumoAnormalidade, ConsumoAnormalidade.class.getName());

					if(!Util.isVazioOrNulo(colecaoConsumoAnormalidade)){
						ConsumoAnormalidade consumoAnormalidade = (ConsumoAnormalidade) Util
										.retonarObjetoDeColecao(colecaoConsumoAnormalidade);
						consumoAnormalidadeContaUltimosConsumosStr = consumoAnormalidade.getDescricaoAbreviada();
					}
				}

				leituraAnormalidadeContaUltimosConsumos = (Integer) consumoAnormalidadeArray[1];

				if(leituraAnormalidadeContaUltimosConsumos != null){
					leituraAnormalidadeContaUltimosConsumosStr = Integer.toString(leituraAnormalidadeContaUltimosConsumos);
				}
			}
		}

		mesContaUltimosConsumosStr = Util.completaString(mesContaUltimosConsumosStr, 3);
		contaTxt.append(mesContaUltimosConsumosStr);

		consumoContaUltimosConsumosStr = Util.completarStringZeroEsquerda(consumoContaUltimosConsumosStr, 6);
		contaTxt.append(consumoContaUltimosConsumosStr);

		consumoAnormalidadeContaUltimosConsumosStr = Util.completaStringComEspacoAEsquerda(consumoAnormalidadeContaUltimosConsumosStr, 2);
		contaTxt.append(consumoAnormalidadeContaUltimosConsumosStr);

		contaTxt.append("-");

		leituraAnormalidadeContaUltimosConsumosStr = Util.completarStringZeroEsquerda(leituraAnormalidadeContaUltimosConsumosStr, 2);
		contaTxt.append(leituraAnormalidadeContaUltimosConsumosStr);

		// 4Conta
		consumoContaUltimosConsumos = contaEmitirHelper.getConsumo4Conta();

		mesContaUltimosConsumosStr = "";
		consumoContaUltimosConsumosStr = "";
		consumoAnormalidadeContaUltimosConsumosStr = "";
		leituraAnormalidadeContaUltimosConsumosStr = "";

		if(consumoContaUltimosConsumos != null){
			// Mês de Consumo [Formato: Texto]
			anoMesContaUltimosConsumos = contaEmitirHelper.getAnoMes4Conta();

			if(anoMesContaUltimosConsumos != null){
				anoMesContaUltimosConsumosStr = Integer.toString(anoMesContaUltimosConsumos);

				mesContaUltimosConsumosStr = anoMesContaUltimosConsumosStr.substring(4, 6);

				mesContaUltimosConsumos = Integer.valueOf(mesContaUltimosConsumosStr);

				mesContaUltimosConsumosStr = Util.retornaDescricaoAbreviadoMes(mesContaUltimosConsumos);
				mesContaUltimosConsumosStr = mesContaUltimosConsumosStr.toUpperCase();
			}

			// Consumo Anterior [Formato: 999999]
			consumoContaUltimosConsumosStr = Integer.toString(consumoContaUltimosConsumos);

			// Anormalidades [Formato: Texto(XX-99)]
			try{
				consumoAnormalidadeArray = repositorioMicromedicao.pesquisarConsumoELeituraAnormalidadePorConsumoHistorico(
								contaEmitirHelper.getIdImovel(), anoMesContaUltimosConsumos, idTipoLigacao);
			}catch(ErroRepositorioException e){
				throw new ControladorException("erro.sistema", e);
			}

			if(consumoAnormalidadeArray != null){
				consumoAnormalidadeContaUltimosConsumos = (Integer) consumoAnormalidadeArray[0];

				if(consumoAnormalidadeContaUltimosConsumos != null){
					FiltroConsumoAnormalidade filtroConsumoAnormalidade = new FiltroConsumoAnormalidade();
					filtroConsumoAnormalidade.adicionarParametro(new ParametroSimples(FiltroConsumoAnormalidade.ID,
									consumoAnormalidadeContaUltimosConsumos));

					Collection<ConsumoAnormalidade> colecaoConsumoAnormalidade = this.getControladorUtil().pesquisar(
									filtroConsumoAnormalidade, ConsumoAnormalidade.class.getName());

					if(!Util.isVazioOrNulo(colecaoConsumoAnormalidade)){
						ConsumoAnormalidade consumoAnormalidade = (ConsumoAnormalidade) Util
										.retonarObjetoDeColecao(colecaoConsumoAnormalidade);
						consumoAnormalidadeContaUltimosConsumosStr = consumoAnormalidade.getDescricaoAbreviada();
					}
				}

				leituraAnormalidadeContaUltimosConsumos = (Integer) consumoAnormalidadeArray[1];

				if(leituraAnormalidadeContaUltimosConsumos != null){
					leituraAnormalidadeContaUltimosConsumosStr = Integer.toString(leituraAnormalidadeContaUltimosConsumos);
				}
			}
		}

		mesContaUltimosConsumosStr = Util.completaString(mesContaUltimosConsumosStr, 3);
		contaTxt.append(mesContaUltimosConsumosStr);

		consumoContaUltimosConsumosStr = Util.completarStringZeroEsquerda(consumoContaUltimosConsumosStr, 6);
		contaTxt.append(consumoContaUltimosConsumosStr);

		consumoAnormalidadeContaUltimosConsumosStr = Util.completaStringComEspacoAEsquerda(consumoAnormalidadeContaUltimosConsumosStr, 2);
		contaTxt.append(consumoAnormalidadeContaUltimosConsumosStr);

		contaTxt.append("-");

		leituraAnormalidadeContaUltimosConsumosStr = Util.completarStringZeroEsquerda(leituraAnormalidadeContaUltimosConsumosStr, 2);
		contaTxt.append(leituraAnormalidadeContaUltimosConsumosStr);

		// 3Conta
		consumoContaUltimosConsumos = contaEmitirHelper.getConsumo3Conta();

		mesContaUltimosConsumosStr = "";
		consumoContaUltimosConsumosStr = "";
		consumoAnormalidadeContaUltimosConsumosStr = "";
		leituraAnormalidadeContaUltimosConsumosStr = "";

		if(consumoContaUltimosConsumos != null){
			// Mês de Consumo [Formato: Texto]
			anoMesContaUltimosConsumos = contaEmitirHelper.getAnoMes3Conta();

			if(anoMesContaUltimosConsumos != null){
				anoMesContaUltimosConsumosStr = Integer.toString(anoMesContaUltimosConsumos);

				mesContaUltimosConsumosStr = anoMesContaUltimosConsumosStr.substring(4, 6);

				mesContaUltimosConsumos = Integer.valueOf(mesContaUltimosConsumosStr);

				mesContaUltimosConsumosStr = Util.retornaDescricaoAbreviadoMes(mesContaUltimosConsumos);
				mesContaUltimosConsumosStr = mesContaUltimosConsumosStr.toUpperCase();
			}

			// Consumo Anterior [Formato: 999999]
			consumoContaUltimosConsumosStr = Integer.toString(consumoContaUltimosConsumos);

			// Anormalidades [Formato: Texto(XX-99)]
			try{
				consumoAnormalidadeArray = repositorioMicromedicao.pesquisarConsumoELeituraAnormalidadePorConsumoHistorico(
								contaEmitirHelper.getIdImovel(), anoMesContaUltimosConsumos, idTipoLigacao);
			}catch(ErroRepositorioException e){
				throw new ControladorException("erro.sistema", e);
			}

			if(consumoAnormalidadeArray != null){
				consumoAnormalidadeContaUltimosConsumos = (Integer) consumoAnormalidadeArray[0];

				if(consumoAnormalidadeContaUltimosConsumos != null){
					FiltroConsumoAnormalidade filtroConsumoAnormalidade = new FiltroConsumoAnormalidade();
					filtroConsumoAnormalidade.adicionarParametro(new ParametroSimples(FiltroConsumoAnormalidade.ID,
									consumoAnormalidadeContaUltimosConsumos));

					Collection<ConsumoAnormalidade> colecaoConsumoAnormalidade = this.getControladorUtil().pesquisar(
									filtroConsumoAnormalidade, ConsumoAnormalidade.class.getName());

					if(!Util.isVazioOrNulo(colecaoConsumoAnormalidade)){
						ConsumoAnormalidade consumoAnormalidade = (ConsumoAnormalidade) Util
										.retonarObjetoDeColecao(colecaoConsumoAnormalidade);
						consumoAnormalidadeContaUltimosConsumosStr = consumoAnormalidade.getDescricaoAbreviada();
					}
				}

				leituraAnormalidadeContaUltimosConsumos = (Integer) consumoAnormalidadeArray[1];

				if(leituraAnormalidadeContaUltimosConsumos != null){
					leituraAnormalidadeContaUltimosConsumosStr = Integer.toString(leituraAnormalidadeContaUltimosConsumos);
				}
			}
		}

		mesContaUltimosConsumosStr = Util.completaString(mesContaUltimosConsumosStr, 3);
		contaTxt.append(mesContaUltimosConsumosStr);

		consumoContaUltimosConsumosStr = Util.completarStringZeroEsquerda(consumoContaUltimosConsumosStr, 6);
		contaTxt.append(consumoContaUltimosConsumosStr);

		consumoAnormalidadeContaUltimosConsumosStr = Util.completaStringComEspacoAEsquerda(consumoAnormalidadeContaUltimosConsumosStr, 2);
		contaTxt.append(consumoAnormalidadeContaUltimosConsumosStr);

		contaTxt.append("-");

		leituraAnormalidadeContaUltimosConsumosStr = Util.completarStringZeroEsquerda(leituraAnormalidadeContaUltimosConsumosStr, 2);
		contaTxt.append(leituraAnormalidadeContaUltimosConsumosStr);

		// 2Conta
		consumoContaUltimosConsumos = contaEmitirHelper.getConsumo2Conta();

		mesContaUltimosConsumosStr = "";
		consumoContaUltimosConsumosStr = "";
		consumoAnormalidadeContaUltimosConsumosStr = "";
		leituraAnormalidadeContaUltimosConsumosStr = "";

		if(consumoContaUltimosConsumos != null){
			// Mês de Consumo [Formato: Texto]
			anoMesContaUltimosConsumos = contaEmitirHelper.getAnoMes2Conta();

			if(anoMesContaUltimosConsumos != null){
				anoMesContaUltimosConsumosStr = Integer.toString(anoMesContaUltimosConsumos);

				mesContaUltimosConsumosStr = anoMesContaUltimosConsumosStr.substring(4, 6);

				mesContaUltimosConsumos = Integer.valueOf(mesContaUltimosConsumosStr);

				mesContaUltimosConsumosStr = Util.retornaDescricaoAbreviadoMes(mesContaUltimosConsumos);
				mesContaUltimosConsumosStr = mesContaUltimosConsumosStr.toUpperCase();
			}

			// Consumo Anterior [Formato: 999999]
			consumoContaUltimosConsumosStr = Integer.toString(consumoContaUltimosConsumos);

			// Anormalidades [Formato: Texto(XX-99)]
			try{
				consumoAnormalidadeArray = repositorioMicromedicao.pesquisarConsumoELeituraAnormalidadePorConsumoHistorico(
								contaEmitirHelper.getIdImovel(), anoMesContaUltimosConsumos, idTipoLigacao);
			}catch(ErroRepositorioException e){
				throw new ControladorException("erro.sistema", e);
			}

			if(consumoAnormalidadeArray != null){
				consumoAnormalidadeContaUltimosConsumos = (Integer) consumoAnormalidadeArray[0];

				if(consumoAnormalidadeContaUltimosConsumos != null){
					FiltroConsumoAnormalidade filtroConsumoAnormalidade = new FiltroConsumoAnormalidade();
					filtroConsumoAnormalidade.adicionarParametro(new ParametroSimples(FiltroConsumoAnormalidade.ID,
									consumoAnormalidadeContaUltimosConsumos));

					Collection<ConsumoAnormalidade> colecaoConsumoAnormalidade = this.getControladorUtil().pesquisar(
									filtroConsumoAnormalidade, ConsumoAnormalidade.class.getName());

					if(!Util.isVazioOrNulo(colecaoConsumoAnormalidade)){
						ConsumoAnormalidade consumoAnormalidade = (ConsumoAnormalidade) Util
										.retonarObjetoDeColecao(colecaoConsumoAnormalidade);
						consumoAnormalidadeContaUltimosConsumosStr = consumoAnormalidade.getDescricaoAbreviada();
					}
				}

				leituraAnormalidadeContaUltimosConsumos = (Integer) consumoAnormalidadeArray[1];

				if(leituraAnormalidadeContaUltimosConsumos != null){
					leituraAnormalidadeContaUltimosConsumosStr = Integer.toString(leituraAnormalidadeContaUltimosConsumos);
				}
			}
		}

		mesContaUltimosConsumosStr = Util.completaString(mesContaUltimosConsumosStr, 3);
		contaTxt.append(mesContaUltimosConsumosStr);

		consumoContaUltimosConsumosStr = Util.completarStringZeroEsquerda(consumoContaUltimosConsumosStr, 6);
		contaTxt.append(consumoContaUltimosConsumosStr);

		consumoAnormalidadeContaUltimosConsumosStr = Util.completaStringComEspacoAEsquerda(consumoAnormalidadeContaUltimosConsumosStr, 2);
		contaTxt.append(consumoAnormalidadeContaUltimosConsumosStr);

		contaTxt.append("-");

		leituraAnormalidadeContaUltimosConsumosStr = Util.completarStringZeroEsquerda(leituraAnormalidadeContaUltimosConsumosStr, 2);
		contaTxt.append(leituraAnormalidadeContaUltimosConsumosStr);

		// 1Conta
		consumoContaUltimosConsumos = contaEmitirHelper.getConsumo1Conta();

		mesContaUltimosConsumosStr = "";
		consumoContaUltimosConsumosStr = "";
		consumoAnormalidadeContaUltimosConsumosStr = "";
		leituraAnormalidadeContaUltimosConsumosStr = "";

		if(consumoContaUltimosConsumos != null){
			// Mês de Consumo [Formato: Texto]
			anoMesContaUltimosConsumos = contaEmitirHelper.getAnoMes1Conta();

			if(anoMesContaUltimosConsumos != null){
				anoMesContaUltimosConsumosStr = Integer.toString(anoMesContaUltimosConsumos);

				mesContaUltimosConsumosStr = anoMesContaUltimosConsumosStr.substring(4, 6);

				mesContaUltimosConsumos = Integer.valueOf(mesContaUltimosConsumosStr);

				mesContaUltimosConsumosStr = Util.retornaDescricaoAbreviadoMes(mesContaUltimosConsumos);
				mesContaUltimosConsumosStr = mesContaUltimosConsumosStr.toUpperCase();
			}

			// Consumo Anterior [Formato: 999999]
			consumoContaUltimosConsumosStr = Integer.toString(consumoContaUltimosConsumos);

			// Anormalidades [Formato: Texto(XX-99)]
			try{
				consumoAnormalidadeArray = repositorioMicromedicao.pesquisarConsumoELeituraAnormalidadePorConsumoHistorico(
								contaEmitirHelper.getIdImovel(), anoMesContaUltimosConsumos, idTipoLigacao);
			}catch(ErroRepositorioException e){
				throw new ControladorException("erro.sistema", e);
			}

			if(consumoAnormalidadeArray != null){
				consumoAnormalidadeContaUltimosConsumos = (Integer) consumoAnormalidadeArray[0];

				if(consumoAnormalidadeContaUltimosConsumos != null){
					FiltroConsumoAnormalidade filtroConsumoAnormalidade = new FiltroConsumoAnormalidade();
					filtroConsumoAnormalidade.adicionarParametro(new ParametroSimples(FiltroConsumoAnormalidade.ID,
									consumoAnormalidadeContaUltimosConsumos));

					Collection<ConsumoAnormalidade> colecaoConsumoAnormalidade = this.getControladorUtil().pesquisar(
									filtroConsumoAnormalidade, ConsumoAnormalidade.class.getName());

					if(!Util.isVazioOrNulo(colecaoConsumoAnormalidade)){
						ConsumoAnormalidade consumoAnormalidade = (ConsumoAnormalidade) Util
										.retonarObjetoDeColecao(colecaoConsumoAnormalidade);
						consumoAnormalidadeContaUltimosConsumosStr = consumoAnormalidade.getDescricaoAbreviada();
					}
				}

				leituraAnormalidadeContaUltimosConsumos = (Integer) consumoAnormalidadeArray[1];

				if(leituraAnormalidadeContaUltimosConsumos != null){
					leituraAnormalidadeContaUltimosConsumosStr = Integer.toString(leituraAnormalidadeContaUltimosConsumos);
				}
			}
		}

		mesContaUltimosConsumosStr = Util.completaString(mesContaUltimosConsumosStr, 3);
		contaTxt.append(mesContaUltimosConsumosStr);

		consumoContaUltimosConsumosStr = Util.completarStringZeroEsquerda(consumoContaUltimosConsumosStr, 6);
		contaTxt.append(consumoContaUltimosConsumosStr);

		consumoAnormalidadeContaUltimosConsumosStr = Util.completaStringComEspacoAEsquerda(consumoAnormalidadeContaUltimosConsumosStr, 2);
		contaTxt.append(consumoAnormalidadeContaUltimosConsumosStr);

		contaTxt.append("-");

		leituraAnormalidadeContaUltimosConsumosStr = Util.completarStringZeroEsquerda(leituraAnormalidadeContaUltimosConsumosStr, 2);
		contaTxt.append(leituraAnormalidadeContaUltimosConsumosStr);

		// 27. Média [Formato: 999999]
		String consumoMedioStr = "";

		Integer consumoMedio = contaEmitirHelper.getConsumoMedio();

		if(consumoMedio != null){
			consumoMedioStr = Integer.toString(consumoMedio);
		}

		consumoMedioStr = Util.completarStringZeroEsquerda(consumoMedioStr, 6);
		contaTxt.append(consumoMedioStr);
	}

	/**
	 * [UC0352] - Emitir Contas
	 * [SB0002 - Gerar Arquivo TXT Contas - Modelo 3]
	 * 
	 * @author Hebert Falcão
	 * @date 11/01/2014
	 */
	private void gerarDebitosAnterioresContasModelo3(StringBuilder contaTxt, EmitirContaTipo2Helper contaEmitirHelper,
					Integer anoMesReferencia, SistemaParametro sistemaParametro) throws ControladorException{

		// 28. Débitos (ocorrendo 6 vezes)

		Date dataCorrente = new Date();
		String idImovel = contaEmitirHelper.getIdImovel().toString();
		String anoMesReferenciaInicial = "000101";
		String anoMesReferenciaFinal = String.valueOf(Util.subtrairMesDoAnoMes(anoMesReferencia, 1));

		Integer parametroQuantidadeDiasVencimentoContaAvisoCorte = Integer
						.parseInt(ParametroCobranca.P_QUANTIDADE_DIAS_VENCIMENTO_CONTA_AVISO_CORTE.executar());

		Date dtVencimentoInicial = Util.converteStringParaDate("01/01/0001", true);
		Date dtVencimentoFinal = Util.subtrairNumeroDiasDeUmaData(dataCorrente, parametroQuantidadeDiasVencimentoContaAvisoCorte);

		ObterDebitoImovelOuClienteHelper debitoImovelOuClienteHelper = this.getControladorCobranca().obterDebitoImovelContas(1, idImovel,
						anoMesReferenciaInicial, anoMesReferenciaFinal, dtVencimentoInicial, dtVencimentoFinal);

		Collection<ContaValoresHelper> collContaValoresHelpers = debitoImovelOuClienteHelper.getColecaoContasValores();

		Integer tamanhoParaCalculoDebitos = 6;

		List contaValoresHelpersList = (List) collContaValoresHelpers;

		ContaValoresHelper contaValoresHelper = null;

		Conta conta = null;

		int referencia = 0;
		String referenciaStr = "";

		BigDecimal somaValorOutrosDebitos = BigDecimal.ZERO;

		BigDecimal debitos = BigDecimal.ZERO;
		String debitosStr = "";

		if(contaValoresHelpersList.size() > tamanhoParaCalculoDebitos){
			int tamanhoParaCalculo = contaValoresHelpersList.size() - tamanhoParaCalculoDebitos;

			for(int i = contaValoresHelpersList.size() - 1; i >= 0; i--){
				contaValoresHelper = (ContaValoresHelper) contaValoresHelpersList.get(i);

				conta = contaValoresHelper.getConta();

				if(i > tamanhoParaCalculo){
					// Referencia do Débito
					referencia = conta.getReferencia();

					if(referencia > 0){
						referenciaStr = Util.formatarMesAnoReferencia(referencia);
					}else{
						referenciaStr = "";
					}

					referenciaStr = Util.completaString(referenciaStr, 7);

					contaTxt.append(referenciaStr);

					// Valor do Débito
					debitos = conta.getDebitos();

					if(debitos == null){
						debitos = BigDecimal.ZERO;
					}

					debitosStr = Util.formatarBigDecimalParaString(debitos, 2);
					debitosStr = Util.completarStringZeroEsquerda(debitosStr, 10);

					contaTxt.append(debitosStr);
				}else{
					// Referencia do Débito
					referencia = conta.getReferencia();

					// Valor do Débito
					debitos = conta.getDebitos();

					if(debitos == null){
						debitos = BigDecimal.ZERO;
					}

					somaValorOutrosDebitos = somaValorOutrosDebitos.add(debitos);
				}
			}

			// Outros débitos
			referenciaStr = "OUTRO";

			referenciaStr = Util.completaString(referenciaStr, 7);

			contaTxt.append(referenciaStr);

			debitosStr = Util.formatarBigDecimalParaString(somaValorOutrosDebitos, 2);
			debitosStr = Util.completarStringZeroEsquerda(debitosStr, 10);

			contaTxt.append(debitosStr);
		}else{
			Integer contador = 0;

			for(int i = contaValoresHelpersList.size() - 1; i >= 0; i--){

				contaValoresHelper = (ContaValoresHelper) contaValoresHelpersList.get(i);

				conta = contaValoresHelper.getConta();

				// Referencia do Débito
				referencia = conta.getReferencia();

				if(referencia > 0){
					referenciaStr = Util.formatarMesAnoReferencia(referencia);
				}else{
					referenciaStr = "";
				}

				referenciaStr = Util.completaString(referenciaStr, 7);

				contaTxt.append(referenciaStr);

				// Valor do Débito
				debitos = conta.getDebitos();

				if(debitos == null){
					debitos = BigDecimal.ZERO;
				}

				debitosStr = Util.formatarBigDecimalParaString(debitos, 2);
				debitosStr = Util.completarStringZeroEsquerda(debitosStr, 10);

				contaTxt.append(debitosStr);

				contador = contador + 1;
			}

			while(contador < tamanhoParaCalculoDebitos){
				contaTxt.append(Util.completaString("", 7));
				contaTxt.append(Util.completaString("", 10));

				contador = contador + 1;
			}
		}

		// 29. Data Cobrança Anterior [Formato: DD/MM/AAAA]
		Integer anoMesArrecadacao = sistemaParametro.getAnoMesArrecadacao();
		anoMesArrecadacao = Util.subtrairMesDoAnoMes(anoMesArrecadacao, 1);

		Date dataCobrancaAnterior = Util.gerarDataApartirAnoMesRefencia(anoMesArrecadacao);

		String dataCobrancaAnteriorStr = Util.formatarData(dataCobrancaAnterior);
		dataCobrancaAnteriorStr = Util.completaString(dataCobrancaAnteriorStr, 10);

		contaTxt.append(dataCobrancaAnteriorStr);
	}

	/**
	 * [UC0352] - Emitir Contas
	 * [SB0002 - Gerar Arquivo TXT Contas - Modelo 3]
	 * 
	 * @author Hebert Falcão
	 * @date 11/01/2014
	 */
	private void gerarValoresContasModelo3(StringBuilder contaTxt, EmitirContaTipo2Helper contaEmitirHelper) throws ControladorException{

		// 30. Valores da Conta (ocorrendo 8 vezes)
		int contador = 0;

		// Água
		BigDecimal valorAgua = contaEmitirHelper.getValorAgua();

		if(valorAgua != null && valorAgua.compareTo(BigDecimal.ZERO) > 0){
			String descricao = "CONSUMO D'AGUA";
			descricao = Util.completaString(descricao, 32);
			contaTxt.append(descricao);

			String valor = Util.formatarBigDecimalParaString(valorAgua, 2);
			valor = Util.completarStringZeroEsquerda(valor, 10);
			contaTxt.append(valor);

			contador = contador + 1;
		}

		// Esgoto
		BigDecimal valorEsgoto = contaEmitirHelper.getValorEsgoto();

		if(valorEsgoto != null && valorEsgoto.compareTo(BigDecimal.ZERO) > 0){
			String descricao = "ESGOTOS";
			descricao = Util.completaString(descricao, 32);
			contaTxt.append(descricao);

			String valor = Util.formatarBigDecimalParaString(valorEsgoto, 2);
			valor = Util.completarStringZeroEsquerda(valor, 10);
			contaTxt.append(valor);

			contador = contador + 1;
		}

		// Impostos
		BigDecimal valorImpostos = contaEmitirHelper.getValorImpostos();

		if(valorImpostos != null && valorImpostos.compareTo(BigDecimal.ZERO) > 0){
			String descricao = "IMPOSTO FEDERAL";
			descricao = Util.completaString(descricao, 32);
			contaTxt.append(descricao);

			String valor = Util.formatarBigDecimalParaString(valorImpostos, 2);
			valor = Util.completarStringZeroEsquerda(valor, 10);
			contaTxt.append(valor);

			contador = contador + 1;
		}

		// Crédito
		Integer idConta = contaEmitirHelper.getIdConta();

		Collection<CreditoRealizado> colecaoCreditoRealizadoAglutinado = new ArrayList<CreditoRealizado>();

		FiltroCreditoRealizado filtroCreditoRealizado = new FiltroCreditoRealizado();
		filtroCreditoRealizado.adicionarParametro(new ParametroSimples(FiltroCreditoRealizado.CONTA_ID, idConta));
		filtroCreditoRealizado.adicionarCaminhoParaCarregamentoEntidade(FiltroCreditoRealizado.CREDITO_TIPO);

		Collection<CreditoRealizado> colecaoCreditoRealizado = this.getControladorUtil().pesquisar(filtroCreditoRealizado,
						CreditoRealizado.class.getName());

		if(!Util.isVazioOrNulo(colecaoCreditoRealizado)){
			List<CreditoRealizado> colecaoCreditoRealizadoOrdenado = new ArrayList<CreditoRealizado>();
			colecaoCreditoRealizadoOrdenado.addAll(colecaoCreditoRealizado);

			List sortFields = new ArrayList();
			sortFields.add(new BeanComparator("creditoTipo.id"));
			sortFields.add(new BeanComparator("numeroPrestacao"));
			sortFields.add(new BeanComparator("numeroPrestacaoCredito"));

			ComparatorChain multiSort = new ComparatorChain(sortFields);
			Collections.sort(colecaoCreditoRealizadoOrdenado, multiSort);

			BigDecimal acumularValorPrestacao = BigDecimal.ZERO;

			for(int i = 0; i < colecaoCreditoRealizadoOrdenado.size(); i++){
				CreditoRealizado creditoRealizado = colecaoCreditoRealizadoOrdenado.get(i);

				CreditoRealizado creditoRealizadoProximo = null;

				if(i != (colecaoCreditoRealizadoOrdenado.size() - 1)){
					creditoRealizadoProximo = colecaoCreditoRealizadoOrdenado.get(i + 1);
				}else{
					creditoRealizadoProximo = colecaoCreditoRealizadoOrdenado.get(i);
				}

				if(creditoRealizado.getCreditoTipo().getId().equals(creditoRealizadoProximo.getCreditoTipo().getId())
								&& (creditoRealizado.getNumeroPrestacao() == creditoRealizadoProximo.getNumeroPrestacao())
								&& (creditoRealizado.getNumeroPrestacaoCredito() == creditoRealizadoProximo.getNumeroPrestacaoCredito())){

					acumularValorPrestacao = acumularValorPrestacao.add(creditoRealizado.getValorCredito());

					if(i == (colecaoCreditoRealizadoOrdenado.size() - 1)){
						creditoRealizado.setValorCredito(acumularValorPrestacao);
						colecaoCreditoRealizadoAglutinado.add(creditoRealizado);
					}
				}else{
					acumularValorPrestacao = acumularValorPrestacao.add(creditoRealizado.getValorCredito());
					creditoRealizado.setValorCredito(acumularValorPrestacao);
					colecaoCreditoRealizadoAglutinado.add(creditoRealizado);
					acumularValorPrestacao = BigDecimal.ZERO;
				}
			}
		}

		// Débito
		Collection<DebitoCobrado> colecaoDebitoCobradoAglutinado = new ArrayList<DebitoCobrado>();

		FiltroDebitoCobrado filtroDebitoCobrado = new FiltroDebitoCobrado();
		filtroDebitoCobrado.adicionarParametro(new ParametroSimples(FiltroDebitoCobrado.CONTA_ID, idConta));
		filtroDebitoCobrado.adicionarCaminhoParaCarregamentoEntidade(FiltroDebitoCobrado.DEBITO_TIPO);

		Collection<DebitoCobrado> colecaoDebitoCobrado = this.getControladorUtil().pesquisar(filtroDebitoCobrado,
						DebitoCobrado.class.getName());

		if(!Util.isVazioOrNulo(colecaoDebitoCobrado)){
			List<DebitoCobrado> colecaoDebitoCobradoOrdenado = new ArrayList<DebitoCobrado>();
			colecaoDebitoCobradoOrdenado.addAll(colecaoDebitoCobrado);

			List sortFields = new ArrayList();
			sortFields.add(new BeanComparator("debitoTipo.id"));
			sortFields.add(new BeanComparator("numeroPrestacao"));
			sortFields.add(new BeanComparator("numeroPrestacaoDebito"));

			ComparatorChain multiSort = new ComparatorChain(sortFields);
			Collections.sort(colecaoDebitoCobradoOrdenado, multiSort);

			BigDecimal acumularValorPrestacao = BigDecimal.ZERO;

			for(int i = 0; i < colecaoDebitoCobradoOrdenado.size(); i++){
				DebitoCobrado debitoCobrado = colecaoDebitoCobradoOrdenado.get(i);

				DebitoCobrado debitoCobradoProximo = null;

				if(i != (colecaoDebitoCobradoOrdenado.size() - 1)){
					debitoCobradoProximo = colecaoDebitoCobradoOrdenado.get(i + 1);
				}else{
					debitoCobradoProximo = colecaoDebitoCobradoOrdenado.get(i);
				}

				if(debitoCobrado.getDebitoTipo().getId().equals(debitoCobradoProximo.getDebitoTipo().getId())
								&& (debitoCobrado.getNumeroPrestacao() == debitoCobradoProximo.getNumeroPrestacao())
								&& (debitoCobrado.getNumeroPrestacaoDebito() == debitoCobradoProximo.getNumeroPrestacaoDebito())){

					acumularValorPrestacao = acumularValorPrestacao.add(debitoCobrado.getValorPrestacao());

					if(i == (colecaoDebitoCobradoOrdenado.size() - 1)){
						debitoCobrado.setValorPrestacao(acumularValorPrestacao);
						colecaoDebitoCobradoAglutinado.add(debitoCobrado);
					}
				}else{
					acumularValorPrestacao = acumularValorPrestacao.add(debitoCobrado.getValorPrestacao());
					debitoCobrado.setValorPrestacao(acumularValorPrestacao);
					colecaoDebitoCobradoAglutinado.add(debitoCobrado);
					acumularValorPrestacao = BigDecimal.ZERO;
				}
			}
		}

		if(!Util.isVazioOrNulo(colecaoCreditoRealizadoAglutinado) && !Util.isVazioOrNulo(colecaoDebitoCobradoAglutinado)){
			// Crédito
			BigDecimal valorAux = BigDecimal.ZERO;

			CreditoTipo creditoTipo = null;
			Short numeroPrestacaoCredito = null;
			Short numeroPrestacao = null;
			BigDecimal valor = null;

			String descricao = "";
			String valorStr = "";

			for(CreditoRealizado creditoRealizado : colecaoCreditoRealizadoAglutinado){
				creditoTipo = creditoRealizado.getCreditoTipo();
				descricao = creditoTipo.getDescricao();
				numeroPrestacaoCredito = creditoRealizado.getNumeroPrestacaoCredito();
				numeroPrestacao = creditoRealizado.getNumeroPrestacao();
				valor = creditoRealizado.getValorCredito();

				if(contador < 6){
					descricao = descricao + " " + numeroPrestacaoCredito + "/" + numeroPrestacao;
					descricao = Util.completaString(descricao, 32);
					contaTxt.append(descricao);

					valorStr = Util.formatarBigDecimalParaString(valor, 2);
					valorStr = Util.completarStringZeroEsquerda(valorStr, 10);
					contaTxt.append(valorStr);
				}else{
					valorAux = valorAux.add(valor);
				}

				contador = contador + 1;
			}

			if(valorAux.compareTo(BigDecimal.ZERO) > 0){
				descricao = "OUTROS CREDITOS";
				descricao = Util.completaString(descricao, 32);
				contaTxt.append(descricao);

				valorStr = Util.formatarBigDecimalParaString(valorAux, 2);
				valorStr = Util.completarStringZeroEsquerda(valorStr, 10);
				contaTxt.append(valorStr);
			}

			// Débito
			valorAux = BigDecimal.ZERO;

			DebitoTipo debitoTipo = null;
			Short numeroPrestacaoDebito = null;

			for(DebitoCobrado debitoCobrado : colecaoDebitoCobradoAglutinado){
				debitoTipo = debitoCobrado.getDebitoTipo();
				descricao = debitoTipo.getDescricao();
				numeroPrestacaoDebito = debitoCobrado.getNumeroPrestacaoDebito();
				numeroPrestacao = debitoCobrado.getNumeroPrestacao();
				valor = debitoCobrado.getValorPrestacao();

				if(contador < 6){
					descricao = descricao + " " + numeroPrestacaoDebito + "/" + numeroPrestacao;
					descricao = Util.completaString(descricao, 32);
					contaTxt.append(descricao);

					valorStr = Util.formatarBigDecimalParaString(valor, 2);
					valorStr = Util.completarStringZeroEsquerda(valorStr, 10);
					contaTxt.append(valorStr);
				}else{
					valorAux = valorAux.add(valor);
				}

				contador = contador + 1;
			}

			if(valorAux.compareTo(BigDecimal.ZERO) > 0){
				descricao = "OUTROS DEBITOS";
				descricao = Util.completaString(descricao, 32);
				contaTxt.append(descricao);

				valorStr = Util.formatarBigDecimalParaString(valorAux, 2);
				valorStr = Util.completarStringZeroEsquerda(valorStr, 10);
				contaTxt.append(valorStr);
			}
		}else if(!Util.isVazioOrNulo(colecaoCreditoRealizadoAglutinado)){
			// Crédito
			BigDecimal valorAux = BigDecimal.ZERO;

			CreditoTipo creditoTipo = null;
			Short numeroPrestacaoCredito = null;
			Short numeroPrestacao = null;
			BigDecimal valor = null;

			String descricao = "";
			String valorStr = "";

			for(CreditoRealizado creditoRealizado : colecaoCreditoRealizadoAglutinado){
				creditoTipo = creditoRealizado.getCreditoTipo();
				descricao = creditoTipo.getDescricao();
				numeroPrestacaoCredito = creditoRealizado.getNumeroPrestacaoCredito();
				numeroPrestacao = creditoRealizado.getNumeroPrestacao();
				valor = creditoRealizado.getValorCredito();

				if(contador < 7){
					descricao = descricao + " " + numeroPrestacaoCredito + "/" + numeroPrestacao;
					descricao = Util.completaString(descricao, 32);
					contaTxt.append(descricao);

					valorStr = Util.formatarBigDecimalParaString(valor, 2);
					valorStr = Util.completarStringZeroEsquerda(valorStr, 10);
					contaTxt.append(valorStr);
				}else{
					valorAux = valorAux.add(valor);
				}

				contador = contador + 1;
			}

			if(valorAux.compareTo(BigDecimal.ZERO) > 0){
				descricao = "OUTROS CREDITOS";
				descricao = Util.completaString(descricao, 32);
				contaTxt.append(descricao);

				valorStr = Util.formatarBigDecimalParaString(valorAux, 2);
				valorStr = Util.completarStringZeroEsquerda(valorStr, 10);
				contaTxt.append(valorStr);
			}
		}else if(!Util.isVazioOrNulo(colecaoDebitoCobradoAglutinado)){
			// Débito
			BigDecimal valorAux = BigDecimal.ZERO;

			DebitoTipo debitoTipo = null;
			Short numeroPrestacaoDebito = null;
			Short numeroPrestacao = null;
			BigDecimal valor = null;

			String descricao = "";
			String valorStr = "";

			for(DebitoCobrado debitoCobrado : colecaoDebitoCobradoAglutinado){
				debitoTipo = debitoCobrado.getDebitoTipo();
				descricao = debitoTipo.getDescricao();
				numeroPrestacaoDebito = debitoCobrado.getNumeroPrestacaoDebito();
				numeroPrestacao = debitoCobrado.getNumeroPrestacao();
				valor = debitoCobrado.getValorPrestacao();

				if(contador < 7){
					descricao = descricao + " " + numeroPrestacaoDebito + "/" + numeroPrestacao;
					descricao = Util.completaString(descricao, 32);
					contaTxt.append(descricao);

					valorStr = Util.formatarBigDecimalParaString(valor, 2);
					valorStr = Util.completarStringZeroEsquerda(valorStr, 10);
					contaTxt.append(valorStr);
				}else{
					valorAux = valorAux.add(valor);
				}

				contador = contador + 1;
			}

			if(valorAux.compareTo(BigDecimal.ZERO) > 0){
				descricao = "OUTROS DEBITOS";
				descricao = Util.completaString(descricao, 32);
				contaTxt.append(descricao);

				valorStr = Util.formatarBigDecimalParaString(valorAux, 2);
				valorStr = Util.completarStringZeroEsquerda(valorStr, 10);
				contaTxt.append(valorStr);
			}
		}

		while(contador < 8){
			contaTxt.append(Util.completaString("", 32));
			contaTxt.append(Util.completaString("", 10));

			contador = contador + 1;
		}
	}

	/**
	 * [UC0352] - Emitir Contas
	 * [SB0002 - Gerar Arquivo TXT Contas - Modelo 3]
	 * 
	 * @author Hebert Falcão
	 * @date 11/01/2014
	 */
	private void gerarDadosGeraisContasParte2Modelo3(StringBuilder contaTxt, EmitirContaTipo2Helper contaEmitirHelper,
					SistemaParametro sistemaParametro, boolean enderecoAlternativo, Integer numeroFolha, String quebraDeSetorComerical)
					throws ControladorException{

		// 31. Mensagem 1
		String msgContaParte1 = contaEmitirHelper.getMsgContaParte1();

		if(msgContaParte1 == null){
			msgContaParte1 = "";
		}

		msgContaParte1 = Util.completaString(msgContaParte1, 75);
		contaTxt.append(msgContaParte1);

		// 32. Mensagem 2
		String msgContaParte2 = contaEmitirHelper.getMsgContaParte2();

		if(msgContaParte2 == null){
			msgContaParte2 = "";
		}

		msgContaParte2 = Util.completaString(msgContaParte2, 75);

		contaTxt.append(msgContaParte2);

		// 33. Mensagem 3
		String msgContaParte3 = contaEmitirHelper.getMsgContaParte3();

		if(msgContaParte3 == null){
			msgContaParte3 = "";
		}

		msgContaParte3 = Util.completaString(msgContaParte3, 75);

		contaTxt.append(msgContaParte3);

		// 34. Mensagem 4
		String msgContaParte4 = "";

		msgContaParte4 = Util.completaString(msgContaParte4, 75);

		contaTxt.append(msgContaParte4);

		// 35. Mensagem Débito Automático
		String mensagemDebitoAutomatico = "";

		Short indicadorDebitoAutomatico = contaEmitirHelper.getIndicadorDebitoAutomatico();

		if(indicadorDebitoAutomatico != null && indicadorDebitoAutomatico.equals(ConstantesSistema.SIM)){
			mensagemDebitoAutomatico = "** DEBITO AUTOMATICO **";
		}

		mensagemDebitoAutomatico = Util.completaString(mensagemDebitoAutomatico, 68);
		contaTxt.append(mensagemDebitoAutomatico);

		// 36. Quebra de Setor
		quebraDeSetorComerical = Util.completaString(quebraDeSetorComerical, 1);
		contaTxt.append(quebraDeSetorComerical);

		// 37. Representação Código de Barras
		Short indicadorCodigoBarras = contaEmitirHelper.getIndicadorCodigoBarras();

		String representacaoCodigoBarras = "";

		if(indicadorDebitoAutomatico != null && indicadorDebitoAutomatico.equals(ConstantesSistema.NAO)){
			representacaoCodigoBarras = contaEmitirHelper.getRepresentacaoNumericaCodBarraSemDigito();

			representacaoCodigoBarras = Util.formatarParaCodigoBarrasI25(representacaoCodigoBarras);
		}

		representacaoCodigoBarras = Util.completaString(representacaoCodigoBarras, 112);

		contaTxt.append(representacaoCodigoBarras);

		// 38. Numeração Código de Barras
		String numeracaoCodigoBarras = "";

		if(indicadorDebitoAutomatico != null && indicadorDebitoAutomatico.equals(ConstantesSistema.NAO) && indicadorCodigoBarras != null
						&& indicadorCodigoBarras.equals(ConstantesSistema.SIM)){
			numeracaoCodigoBarras = contaEmitirHelper.getRepresentacaoNumericaCodBarraSemDigito();
		}

		numeracaoCodigoBarras = Util.completaString(numeracaoCodigoBarras, 44);
		contaTxt.append(numeracaoCodigoBarras);

		// 39. Condição de Leitura
		String descricaoLeituraSituacaoAtual = "";

		Integer idLeituraSituacaoAtual = contaEmitirHelper.getIdLeituraSituacaoAtual();

		if(idLeituraSituacaoAtual != null){
			FiltroLeituraSituacao filtroLeituraSituacao = new FiltroLeituraSituacao();
			filtroLeituraSituacao.adicionarParametro(new ParametroSimples(FiltroLeituraSituacao.ID, idLeituraSituacaoAtual));

			Collection<LeituraSituacao> colecaoLeituraSituacao = this.getControladorUtil().pesquisar(filtroLeituraSituacao,
							LeituraSituacao.class.getName());

			if(!Util.isVazioOrNulo(colecaoLeituraSituacao)){
				LeituraSituacao leituraSituacao = (LeituraSituacao) Util.retonarObjetoDeColecao(colecaoLeituraSituacao);
				descricaoLeituraSituacaoAtual = leituraSituacao.getDescricao();
			}
		}

		descricaoLeituraSituacaoAtual = Util.completaStringComEspacoAEsquerda(descricaoLeituraSituacaoAtual, 12);
		contaTxt.append(descricaoLeituraSituacaoAtual);

		// 40. Condição de Faturamento
		String descricaoTipoConsumo = contaEmitirHelper.getDescricaoTipoConsumo();

		if(descricaoTipoConsumo == null){
			descricaoTipoConsumo = "";
		}

		descricaoTipoConsumo = Util.completaStringComEspacoAEsquerda(descricaoTipoConsumo, 14);
		contaTxt.append(descricaoTipoConsumo);

		// 41. Anormalidade de Leitura
		String descricaoLeituraAnormalidade = "";

		Integer idLeituraAnormalidadeFaturamento = contaEmitirHelper.getIdLeituraAnormalidadeFaturamento();

		if(idLeituraAnormalidadeFaturamento != null){
			FiltroLeituraAnormalidade filtroLeituraAnormalidade = new FiltroLeituraAnormalidade();
			filtroLeituraAnormalidade.adicionarParametro(new ParametroSimples(FiltroLeituraAnormalidade.ID,
							idLeituraAnormalidadeFaturamento));

			Collection<LeituraAnormalidade> colecaoLeituraAnormalidade = this.getControladorUtil().pesquisar(filtroLeituraAnormalidade,
							LeituraAnormalidade.class.getName());

			if(!Util.isVazioOrNulo(colecaoLeituraAnormalidade)){
				LeituraAnormalidade leituraAnormalidade = (LeituraAnormalidade) Util.retonarObjetoDeColecao(colecaoLeituraAnormalidade);
				descricaoLeituraAnormalidade = leituraAnormalidade.getDescricao();
			}
		}

		descricaoLeituraAnormalidade = Util.completaStringComEspacoAEsquerda(descricaoLeituraAnormalidade, 25);
		contaTxt.append(descricaoLeituraAnormalidade);

		// 42. Anormalidade de Consumo
		String descricaoConsumoAnormalidade = "";

		Integer idConsumoAnormalidade = contaEmitirHelper.getIdConsumoAnormalidade();

		if(idConsumoAnormalidade != null){
			FiltroConsumoAnormalidade filtroConsumoAnormalidade = new FiltroConsumoAnormalidade();
			filtroConsumoAnormalidade.adicionarParametro(new ParametroSimples(FiltroConsumoAnormalidade.ID, idConsumoAnormalidade));

			Collection<ConsumoAnormalidade> colecaoConsumoAnormalidade = this.getControladorUtil().pesquisar(filtroConsumoAnormalidade,
							ConsumoAnormalidade.class.getName());

			if(!Util.isVazioOrNulo(colecaoConsumoAnormalidade)){
				ConsumoAnormalidade consumoAnormalidade = (ConsumoAnormalidade) Util.retonarObjetoDeColecao(colecaoConsumoAnormalidade);
				descricaoConsumoAnormalidade = consumoAnormalidade.getDescricao();
			}
		}

		descricaoConsumoAnormalidade = Util.completaStringComEspacoAEsquerda(descricaoConsumoAnormalidade, 16);
		contaTxt.append(descricaoConsumoAnormalidade);

		// 43. Tipo de Contrato
		String tipoContrato = "";

		Integer idImovel = contaEmitirHelper.getIdImovel();
		Integer anoMesConta = contaEmitirHelper.getAnoMesConta();

		FiltroConsumoHistorico filtroConsumoHistorico = new FiltroConsumoHistorico();
		filtroConsumoHistorico.adicionarParametro(new ParametroSimples(FiltroConsumoHistorico.IMOVEL_ID, idImovel));
		filtroConsumoHistorico.adicionarParametro(new ParametroSimples(FiltroConsumoHistorico.LIGACAO_TIPO_ID, LigacaoTipo.LIGACAO_ESGOTO));
		filtroConsumoHistorico.adicionarParametro(new ParametroSimples(FiltroConsumoHistorico.ANO_MES_FATURAMENTO, anoMesConta));

		Collection<ConsumoHistorico> colecaoConsumoHistorico = this.getControladorUtil().pesquisar(filtroConsumoHistorico,
						ConsumoHistorico.class.getName());

		if(!Util.isVazioOrNulo(colecaoConsumoHistorico)){
			ConsumoHistorico consumoHistorico = (ConsumoHistorico) Util.retonarObjetoDeColecao(colecaoConsumoHistorico);

			ConsumoTipo consumoTipo = consumoHistorico.getConsumoTipo();

			if(consumoTipo != null){
				Integer idConsumoTipo = consumoTipo.getId();

				if(idConsumoTipo.intValue() == ConsumoTipo.CONSUMO_FIXO.intValue()){
					Integer consumoEsgoto = contaEmitirHelper.getConsumoEsgoto();
					tipoContrato = "ESGOTO FIXO" + consumoEsgoto + "M3";
				}
			}
		}

		tipoContrato = Util.completaString(tipoContrato, 20);
		contaTxt.append(tipoContrato);

		// 44. Grande Consumidor
		String grandeConsumidor = "";

		Integer idImovelPerfil = contaEmitirHelper.getIdImovelPerfil();

		if(idImovelPerfil != null && idImovelPerfil == ImovelPerfil.GRANDE){
			grandeConsumidor = "GRANDE CONSUMIDOR";
		}

		grandeConsumidor = Util.completaString(grandeConsumidor, 17);
		contaTxt.append(grandeConsumidor);

		// 45.Dias de Consumo [Formato: 99]
		String diasDeConsumoStr = "";

		Date dtLeituraAnterior = contaEmitirHelper.getDtLeituraAnterior();
		Date dtLeituraAtual = contaEmitirHelper.getDtLeituraAtual();

		if(dtLeituraAnterior != null && dtLeituraAtual != null){
			int diasDeConsumo = Util.obterQuantidadeDiasEntreDuasDatas(dtLeituraAnterior, dtLeituraAtual);
			diasDeConsumoStr = Integer.toString(diasDeConsumo);
		}

		diasDeConsumoStr = Util.completarStringZeroEsquerda(diasDeConsumoStr, 2);
		contaTxt.append(diasDeConsumoStr);

		// 46. Numero da Folha [Formato: 99999]
		String numeroFolhaStr = Integer.toString(numeroFolha);
		numeroFolhaStr = Util.completarStringZeroEsquerda(numeroFolhaStr, 5);
		contaTxt.append(numeroFolhaStr);

		// 47. Hidrometro
		String hidrometro = contaEmitirHelper.getHidrometro();

		if(hidrometro == null){
			hidrometro = "";
		}

		hidrometro = Util.completaString(hidrometro, 10);
		contaTxt.append(hidrometro);

		// 48. Marca Hidrometro
		String descricaoAbreviadaHidrometroMarca = "";

		HidrometroMarca hidrometroMarca = contaEmitirHelper.getHidrometroMarca();

		if(hidrometroMarca != null){
			descricaoAbreviadaHidrometroMarca = hidrometroMarca.getDescricaoAbreviada();
		}

		descricaoAbreviadaHidrometroMarca = Util.completaString(descricaoAbreviadaHidrometroMarca, 3);
		contaTxt.append(descricaoAbreviadaHidrometroMarca);

		// 49. Local Instalação Hidrometro
		String descricaoAbreviadaHidrometroLocalInstalacao = "";

		HidrometroLocalInstalacao hidrometroLocalInstalacao = contaEmitirHelper.getHidrometroLocalInstalacao();

		if(hidrometroLocalInstalacao != null){
			descricaoAbreviadaHidrometroLocalInstalacao = hidrometroLocalInstalacao.getDescricaoAbreviada();
		}

		descricaoAbreviadaHidrometroLocalInstalacao = Util.completaString(descricaoAbreviadaHidrometroLocalInstalacao, 4);
		contaTxt.append(descricaoAbreviadaHidrometroLocalInstalacao);

		// 50. Capacidade Hidrometro
		String descricaoAbreviadaHidrometroCapacidade = "";

		HidrometroCapacidade hidrometroCapacidade = contaEmitirHelper.getHidrometroCapacidade();

		if(hidrometroCapacidade != null){
			descricaoAbreviadaHidrometroCapacidade = hidrometroCapacidade.getDescricaoAbreviada();
		}

		descricaoAbreviadaHidrometroCapacidade = Util.completaString(descricaoAbreviadaHidrometroCapacidade, 6);
		contaTxt.append(descricaoAbreviadaHidrometroCapacidade);

		// 51. Data Instalação Hidrometro
		String dataInstalacaoHidrometroStr = "";

		Date dataInstalacaoHidrometro = contaEmitirHelper.getDataInstalacaoHidrometro();

		if(dataInstalacaoHidrometro != null){
			dataInstalacaoHidrometroStr = Util.formatarData(dataInstalacaoHidrometro);
		}

		dataInstalacaoHidrometroStr = Util.completaString(dataInstalacaoHidrometroStr, 10);
		contaTxt.append(dataInstalacaoHidrometroStr);

		// 52. Bairro
		String nomeBairro = "";

		FiltroImovel filtroImovel = new FiltroImovel();
		filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, idImovel));
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.LOGRADOURO_BAIRRO);
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.BAIRRO);

		Collection<Imovel> colecaoImovel = this.getControladorUtil().pesquisar(filtroImovel, Imovel.class.getName());

		if(!Util.isVazioOrNulo(colecaoImovel)){
			Imovel imovel = (Imovel) Util.retonarObjetoDeColecao(colecaoImovel);

			if(imovel != null){
				LogradouroBairro logradouroBairro = imovel.getLogradouroBairro();

				if(logradouroBairro != null){
					Bairro bairro = logradouroBairro.getBairro();

					if(bairro != null){
						nomeBairro = bairro.getNome();
					}
				}
			}
		}

		nomeBairro = Util.completaString(nomeBairro, 20);
		contaTxt.append(nomeBairro);

		// As 7 colunas abaixo nesse bloco só serão geradas no arquivo referente as contas com
		// endereço alternativo.

		if(enderecoAlternativo){
			contaTxt.append(Util.completaString("", 5));

			// Nome Postagem [Formato: Texto]
			String nomeCliente = contaEmitirHelper.getNomeCliente();

			if(nomeCliente == null){
				nomeCliente = "";
			}

			nomeCliente = Util.completaString(nomeCliente, 25);
			contaTxt.append(nomeCliente);

			Imovel imovel = new Imovel();
			imovel.setId(idImovel);

			Object[] arrayDadosEndereco = this.getControladorFaturamento().obterEnderecoEntregaClienteComDetalhamento(imovel);

			String enderecoClienteResponsavel = "";
			String bairroEntrega = "";
			String cepEntrega = "";
			String municipioEntrega = "";
			String ufEntrega = "";
			String complementoEndereco = "";

			if(arrayDadosEndereco != null){
				enderecoClienteResponsavel = (String) arrayDadosEndereco[0];
				bairroEntrega = (String) arrayDadosEndereco[3];
				cepEntrega = (String) arrayDadosEndereco[4];
				municipioEntrega = (String) arrayDadosEndereco[5];
				ufEntrega = (String) arrayDadosEndereco[6];
				complementoEndereco = (String) arrayDadosEndereco[7];
			}

			// Endereço Postagem
			if(enderecoClienteResponsavel == null){
				enderecoClienteResponsavel = "";
			}

			enderecoClienteResponsavel = Util.completaString(enderecoClienteResponsavel, 50);
			contaTxt.append(enderecoClienteResponsavel);

			// Bairro Postagem
			if(bairroEntrega == null){
				bairroEntrega = "";
			}

			bairroEntrega = Util.completaString(bairroEntrega, 20);
			contaTxt.append(bairroEntrega);

			// Cidade Postagem
			if(municipioEntrega == null){
				municipioEntrega = "";
			}

			municipioEntrega = Util.completaString(municipioEntrega, 25);
			contaTxt.append(municipioEntrega);

			// Cep Postagem
			if(cepEntrega == null){
				cepEntrega = "";
			}

			cepEntrega = Util.completarStringZeroEsquerda(cepEntrega, 8);
			contaTxt.append(cepEntrega);

			// Complemento Postagem
			if(complementoEndereco == null){
				complementoEndereco = "";
			}

			complementoEndereco = Util.completaString(complementoEndereco, 15);
			contaTxt.append(complementoEndereco);

			// UF Postagem
			if(ufEntrega == null){
				ufEntrega = "";
			}

			ufEntrega = Util.completaString(ufEntrega, 2);
			contaTxt.append(ufEntrega);
		}

		// Qualidade da água

		QualidadeAgua qualidadeAgua = null;

		try{
			qualidadeAgua = repositorioFaturamento.pesquisarQualidadeAguaPorLocalidadeAnoMesFaturamento(
							sistemaParametro.getAnoMesFaturamento(), contaEmitirHelper.getInscLocalidade());
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}

		BigDecimal numeroIndiceTurbidez = null;
		BigDecimal numeroIndicePH = null;
		BigDecimal numeroIndiceCor = null;
		BigDecimal numeroCloroResidual = null;
		BigDecimal numeroIndiceColiformesTotais = null;
		Integer anoMesReferenciaQualidadeAgua = null;

		if(qualidadeAgua != null){
			numeroIndiceTurbidez = qualidadeAgua.getNumeroIndiceTurbidez();

			numeroIndicePH = qualidadeAgua.getNumeroIndicePH();

			numeroIndiceCor = qualidadeAgua.getNumeroIndiceCor();

			numeroCloroResidual = qualidadeAgua.getNumeroCloroResidual();

			numeroIndiceColiformesTotais = qualidadeAgua.getNumeroIndiceColiformesTotais();

			anoMesReferenciaQualidadeAgua = qualidadeAgua.getAnoMesReferencia();
		}

		// 53. Turbidez [Formato: 99999]
		String numeroIndiceTurbidezStr = "";

		if(numeroIndiceTurbidez != null){
			numeroIndiceTurbidezStr = Util.formatarBigDecimalParaString(numeroIndiceTurbidez, 2);
		}

		// Preencher os zeros à esquerda com espaços em branco
		numeroIndiceTurbidezStr = Util.completaStringComEspacoAEsquerda(numeroIndiceTurbidezStr, 5);
		contaTxt.append(numeroIndiceTurbidezStr);

		// 54. PH [Formato: 99999]
		String numeroIndicePHStr = "";

		if(numeroIndicePH != null){
			numeroIndicePHStr = Util.formatarBigDecimalParaString(numeroIndicePH, 2);
		}

		// Preencher os zeros à esquerda com espaços em branco
		numeroIndicePHStr = Util.completaStringComEspacoAEsquerda(numeroIndicePHStr, 5);
		contaTxt.append(numeroIndicePHStr);

		// 55. Cor [Formato: 99999]
		String numeroIndiceCorStr = "";

		if(numeroIndiceCor != null){
			numeroIndiceCorStr = Util.formatarBigDecimalParaString(numeroIndiceCor, 2);
		}

		// Preencher os zeros à esquerda com espaços em branco
		numeroIndiceCorStr = Util.completaStringComEspacoAEsquerda(numeroIndiceCorStr, 5);
		contaTxt.append(numeroIndiceCorStr);

		// 56. Cloro [Formato: 99999]
		String numeroCloroResidualStr = "";

		if(numeroCloroResidual != null){
			numeroCloroResidualStr = Util.formatarBigDecimalParaString(numeroCloroResidual, 2);
		}

		// Preencher os zeros à esquerda com espaços em branco
		numeroCloroResidualStr = Util.completaStringComEspacoAEsquerda(numeroCloroResidualStr, 5);
		contaTxt.append(numeroCloroResidualStr);

		// 57. Coliformes Totais [Formato: Texto]
		String coliformesTotaisStr = "PRESENTE";

		if(numeroIndiceColiformesTotais == null || numeroIndiceColiformesTotais.compareTo(BigDecimal.ZERO) == 0){
			coliformesTotaisStr = "AUSENTE";
		}

		coliformesTotaisStr = Util.completaString(coliformesTotaisStr, 8);
		contaTxt.append(coliformesTotaisStr);

		// 58.Referencia da Qualidade da Agua [Formato: MM/AAAA]
		String anoMesReferenciaQualidadeAguaStr = "";

		if(anoMesReferenciaQualidadeAgua != null){
			anoMesReferenciaQualidadeAguaStr = Util.formatarMesAnoReferencia(anoMesReferenciaQualidadeAgua);
		}

		anoMesReferenciaQualidadeAguaStr = Util.completaString(anoMesReferenciaQualidadeAguaStr, 7);
		contaTxt.append(anoMesReferenciaQualidadeAguaStr);
	}

	/**
	 * [UC0352] - Emitir Contas
	 * [SB0001] – Gerar Contas Modelo 2
	 * Responsável pela geração PDF com a conta
	 * 
	 * @author Anderson Italo
	 * @throws CreateException
	 * @date 07/08/2013
	 */
	public void execParamGerarContaPdfModelo2(ParametroSistema parametroSistema, FaturamentoGrupo faturamentoGrupo,
					Integer anoMesReferencia, Usuario usuario, List<EmitirContaTipo2Helper> colecaoContaTipo2HelperOrdenada)
					throws ControladorException, CreateException{

		this.ejbCreate();

		int totalBlocosContasAEmitir = (Util.dividirArredondarResultado(colecaoContaTipo2HelperOrdenada.size(),
						ConstantesSistema.QUANTIDADE_BLOCO_IMPRESSOES_EMISSAO_CONTA_FATURAMENTO, BigDecimal.ROUND_CEILING));
		int blocoEmitido = 1;

		List<RelatorioContaModelo2> listaRelatoriosGerados = new ArrayList<RelatorioContaModelo2>();

		while(!colecaoContaTipo2HelperOrdenada.isEmpty()){

			List<EmitirContaTipo2Helper> colecaoGeracaoParcialContaTipo2Helper = new ArrayList<EmitirContaTipo2Helper>();

			if(colecaoContaTipo2HelperOrdenada.size() <= (ConstantesSistema.QUANTIDADE_BLOCO_IMPRESSOES_EMISSAO_CONTA_FATURAMENTO)){

				colecaoGeracaoParcialContaTipo2Helper.addAll(colecaoContaTipo2HelperOrdenada);

			}else{

				colecaoGeracaoParcialContaTipo2Helper.addAll(colecaoContaTipo2HelperOrdenada.subList(0,
								(ConstantesSistema.QUANTIDADE_BLOCO_IMPRESSOES_EMISSAO_CONTA_FATURAMENTO)));
			}

			if(!Util.isVazioOrNulo(colecaoGeracaoParcialContaTipo2Helper)){

				RelatorioContaModelo2 relatorioConta = new RelatorioContaModelo2(usuario);
				int tipoRelatorio = TarefaRelatorio.TIPO_PDF;

				String descricaoGrupo = faturamentoGrupo.getDescricao();
				if(descricaoGrupo != null && descricaoGrupo.length() > 10){

					descricaoGrupo = descricaoGrupo.substring(0, 10);
				}

				String mensagemArquivo = "GRUPO:" + descricaoGrupo + " REF:" + Util.formatarAnoMesParaMesAno(anoMesReferencia) + " PARTE:"
								+ blocoEmitido + "/" + totalBlocosContasAEmitir;

				relatorioConta.addParametro("tipoFormatoRelatorio", tipoRelatorio);
				relatorioConta.addParametro("descricaoArquivo", mensagemArquivo);
				relatorioConta.addParametro("colecaoContaHelper", colecaoGeracaoParcialContaTipo2Helper);
				relatorioConta.addParametro("faturamentoGrupo", faturamentoGrupo);
				relatorioConta.addParametro("anoMesReferencia", anoMesReferencia);

				listaRelatoriosGerados.add(relatorioConta);

				colecaoContaTipo2HelperOrdenada.removeAll(colecaoGeracaoParcialContaTipo2Helper);
				blocoEmitido++;
			}
		}

		for(RelatorioContaModelo2 relatorioConta : listaRelatoriosGerados){

			getControladorBatch().iniciarProcessoRelatorio(relatorioConta);
		}
	}

	private void gerarArquivoContasBrailleModelo3(Collection<EmitirContaTipo2Helper> listaEmitirContaTipo2Helper,
					ComparatorChain ordenacao,
					FaturamentoGrupo faturamentoGrupo, Usuario usuario)
					throws ControladorException{

		if(!Util.isVazioOrNulo(listaEmitirContaTipo2Helper)){

			// Ordenando a coleção
			Collections.sort((List) listaEmitirContaTipo2Helper, ordenacao);
			// Geração do arquivo
			Integer idFaturamentoGrupo = faturamentoGrupo.getId();

			Integer anoMesReferencia = faturamentoGrupo.getAnoMesReferencia();

			StringBuilder arquivo = this.gerarLinhasContasBrailleModelo3(listaEmitirContaTipo2Helper);

			// Envia o arquivo para ser gerado como um relatório batch
			String nomeArquivo = "CONTAS_BRAILLE_g"  + idFaturamentoGrupo + "_" + anoMesReferencia + ".txt";

			this.enviarArquivoEmissaoContasParaBatch(arquivo, nomeArquivo, usuario);
		}
	}

	private StringBuilder gerarLinhasContasBrailleModelo3(Collection<EmitirContaTipo2Helper> listaConta) throws ControladorException{

		StringBuilder contaTxt = new StringBuilder();

		SistemaParametro sistemaParametro = this.getControladorUtil().pesquisarParametrosDoSistema();

		// Gerar a linha com os dados da conta
		for(EmitirContaTipo2Helper contaEmitirHelper : listaConta){

			contaTxt.append(Util.completaString(sistemaParametro.getNomeAbreviadoEmpresa(), 10));

			String nomeClienteUsuario = "";
			if(contaEmitirHelper.getNomeImovel() != null && !contaEmitirHelper.getNomeImovel().equals("")){
				nomeClienteUsuario = contaEmitirHelper.getNomeImovel();
			}else if(contaEmitirHelper.getNomeCliente() != null && !contaEmitirHelper.getNomeCliente().equals("")){
				nomeClienteUsuario = contaEmitirHelper.getNomeCliente();
			}
			contaTxt.append(Util.completaString("Nome do cliente = " + nomeClienteUsuario, 70));

			contaTxt.append(Util.completaString("Endereço = " + contaEmitirHelper.getEndereco(), 130));

			contaTxt.append(Util.completaString("Matrícula do imóvel = " + contaEmitirHelper.getIdImovel(), 30));

			contaTxt.append(Util.completaString("Mês/Ano da conta = " + Util.formatarAnoMesParaMesAno(contaEmitirHelper.getAnoMesConta()),
							26));

			String leituraAnterior = "";
			if(contaEmitirHelper.getLeituraAnterior() != null){
				leituraAnterior = contaEmitirHelper.getLeituraAnterior().toString();
			}
			contaTxt.append(Util.completaString("Leitura anterior = " + leituraAnterior, 25));

			String leituraAtual = "";
			if(contaEmitirHelper.getLeituraAtual() != null){
				leituraAtual = contaEmitirHelper.getLeituraAtual().toString();
			}
			contaTxt.append(Util.completaString("Leitura atual = " + leituraAtual, 25));

			contaTxt.append(Util.completaString("Volume faturado de água (m3) = " + contaEmitirHelper.getConsumoAgua(), 35));

			String diasConsumo = "";
			if(contaEmitirHelper.getDtLeituraAnterior() != null && contaEmitirHelper.getDtLeituraAtual() != null){
				// calcula a quantidade de dias de consumo que é a
				// quantidade de dias entre a data de leitura
				// anterior e a data de leitura atual
				diasConsumo = ""
								+ Util.obterQuantidadeDiasEntreDuasDatas(contaEmitirHelper.getDtLeituraAnterior(),
												contaEmitirHelper.getDtLeituraAtual());
			}
			contaTxt.append(Util.completaString("Dias de consumo = " + diasConsumo, 20));

			contaTxt.append(Util.completaString("Data de vencimento = " + Util.formatarData(contaEmitirHelper.getDataVencimento()), 31));

			contaTxt.append(Util.completaString("Valor a pagar = " + Util.formatarMoedaReal(contaEmitirHelper.getValorTotalConta()), 30));

			String residencial = "Categoria Residencial = ";
			String comercial = "Categoria Comercial = ";
			String industrial = "Categoria Industrial  = ";
			String publica = "Categoria Pública  = ";

			if(contaEmitirHelper.getEconResidencial() != null){
				residencial += contaEmitirHelper.getEconResidencial();
			}

			if(contaEmitirHelper.getEconComercial() != null){
				comercial += contaEmitirHelper.getEconComercial();
			}

			if(contaEmitirHelper.getEconIndustrial() != null){
				industrial += contaEmitirHelper.getEconIndustrial();
			}

			if(contaEmitirHelper.getEconPublica() != null){
				publica += contaEmitirHelper.getEconPublica();
			}

			contaTxt.append(Util.completaString(residencial, 30));
			contaTxt.append(Util.completaString(comercial, 30));
			contaTxt.append(Util.completaString(industrial, 30));
			contaTxt.append(Util.completaString(publica, 30));

			contaTxt.append(Util.completaString("Valor Água = " + Util.formatarMoedaReal(contaEmitirHelper.getValorAgua()), 30));

			contaTxt.append(Util.completaString("Valor Esgoto = " + Util.formatarMoedaReal(contaEmitirHelper.getValorEsgoto()), 30));

			contaTxt.append(Util.completaString("Valor Débitos = " + Util.formatarMoedaReal(contaEmitirHelper.getDebitos()), 30));

			contaTxt.append(Util.completaString("Valor Créditos  = " + Util.formatarMoedaReal(contaEmitirHelper.getValorCreditos()), 30));

			contaTxt.append(Util.completaString("Valor Impostos =  " + Util.formatarMoedaReal(contaEmitirHelper.getValorImpostos()), 30));

			String msg = "";

			if(contaEmitirHelper.getMsgContaParte1() != null){
				msg += contaEmitirHelper.getMsgContaParte1();
			}
			if(contaEmitirHelper.getMsgContaParte2() != null){
				msg += contaEmitirHelper.getMsgContaParte2();
			}
			if(contaEmitirHelper.getMsgContaParte3() != null){
				msg += contaEmitirHelper.getMsgContaParte3();
			}

			contaTxt.append(Util.completaString(msg, 310));
			
			String rodape = "Contato = " + sistemaParametro.getNomeAbreviadoEmpresa() + ": " + sistemaParametro.getNumeroTelefone() + " - "
							+ ParametroCadastro.P_SITE_EMPRESA.executar();

			contaTxt.append(Util.completaString(rodape, 70));

			contaTxt.append(System.getProperty("line.separator"));
		}

		return contaTxt;
	}

	/**
	 * [UC3013] Gerar Declaração Anual Quitação Débitos
	 * [SB0004] - Verificar Não Geração da Declaração para o Imóvel - Modelo Default–
	 * 
	 * @author Yara Souza
	 * @date 29/09/2014
	 */
	public Boolean execParamVerificarNaoGeracaoDeclaracaoImovel(ParametroSistema parametroSistema, Integer idImovel,
					Integer anoBaseDeclaracaoQuitacaoDebitoAnual) throws ControladorException, CreateException{

		this.ejbCreate();

		Boolean retorno = Boolean.TRUE;

		try{
			// Obter Primeiro dia do ano, conforme ano base informado.
			Date dataInicial = Util.gerarDataInicialDoAnoApartirDoAnoRefencia(anoBaseDeclaracaoQuitacaoDebitoAnual);

			Integer referenciaInicial = Util.recuperaAnoMesDaData(dataInicial);

			// Obter Último dia do ano, conforme ano base informado.
			Date dataFinal = Util.gerarDataFinalDoAnoApartirDoAnoRefencia(anoBaseDeclaracaoQuitacaoDebitoAnual);

			Integer referenciaFinal = Util.recuperaAnoMesDaData(dataFinal);

			// Caso o imóvel tenha contas vencidas no ano de referência
			boolean verificarImovelContasVencidasAnoReferencia = repositorioFaturamento.verificarImovelContasVencidasAnoReferencia(
							idImovel, dataInicial, dataFinal);

			if(verificarImovelContasVencidasAnoReferencia){
				retorno = Boolean.FALSE;
			}

			if(retorno){
				// Caso o imóvel tenha guias de pagamento vencidas no ano de referência
				boolean verificarImovelGuiasVencidasAnoReferencia = repositorioFaturamento.verificarImovelGuiasVencidasAnoReferencia(
								idImovel, dataInicial, dataFinal);

				if(verificarImovelGuiasVencidasAnoReferencia){
					retorno = Boolean.FALSE;
				}
			}

			if(retorno){
				// Caso não existam pagamentos para o imóvel no ano de referência
				boolean verificarPagamentosHistoricoParaImovelAnoReferencia = repositorioFaturamento
								.verificarPagamentosHistoricoParaImovelAnoReferencia(idImovel, referenciaInicial, referenciaFinal);

				if(!verificarPagamentosHistoricoParaImovelAnoReferencia){
					retorno = Boolean.FALSE;
				}
			}

			// if(retorno){
			// // Caso exista pagamento ou parcelamento após o ano de referência
			// boolean verificarImovelParcelamentoAnoReferencia =
			// repositorioFaturamento.verificarImovelParcelamentoAnoReferencia(
			// idImovel, referenciaFinal, dataFinal);
			//
			// if(verificarImovelParcelamentoAnoReferencia){
			// retorno = Boolean.FALSE;
			// }
			// }
		}catch(ErroRepositorioException e){
			e.printStackTrace();
			throw new ControladorException("erro.sistema", e);
		}

		return retorno;
	}

	/**
	 * [UC3013] Gerar Declaração Anual Quitação Débitos
	 * [SB0002] Verificar Não Geração da Declaração para o Imóvel – Modelo Default
	 * 
	 * @author Yara Souza
	 * @date 30/09/2014
	 */
	public Boolean execParamVerificarNaoGeracaoDeclaracaoImovelModeloDefault(ParametroSistema parametroSistema, Integer idImovel,
					Integer anoBaseDeclaracaoQuitacaoDebitoAnual) throws ControladorException, CreateException{

		this.ejbCreate();

		Boolean retorno = Boolean.TRUE;

		try{

			// 1. Caso o imóvel tenha contas vencidas no ano de referência (existe ocorrência na
			// tabela CONTA com IMOV_ID=IMOV_ID da tabela IMOVEL e DCST_IDATUAL=DCST_ID da tabela
			// DEBITO_CREDITO_SITUACAO com DCST_ICVALIDO com o valor correspondente a "Sim" e
			// CNTA_DTVENCIMENTOCONTA menor ou igual ao último dia do ano de referência e o motivo
			// de revisão iniba a geração da declaração (CMRV_ICINIBEDECLARACAOQUITACAO = 1) e caso
			// seja para verificar pagamento pendente
			// ("P_VERIFICA_PAGTO_PENDENTE_DECLARACAO_QUITACAO_ANUAL" = 1) e não tenha pagamento
			// (CNTA_ICPAGAMENTO = 2 OR CNTA_ICPAGAMENTO IS NULL)), não gerar a declaração para o
			// imóvel e passar para o próximo imóvel.

			// Obter Ultimo dia do ano, conforme ano base informado.
			Date ultimoDiaDoAnoDeReferencia = Util.gerarDataFinalDoAnoApartirDoAnoRefencia(anoBaseDeclaracaoQuitacaoDebitoAnual);

			// Integer referenciaFinal = Util.recuperaAnoMesDaData(ultimoDiaDoAnoDeReferencia);

			Integer pConsiderarPgtoPendente = Util
							.converterStringParaInteger((String) ParametroFaturamento.P_VERIFICA_PAGTO_PENDENTE_DECLARACAO_QUITACAO_ANUAL
											.executar());
						
			boolean verificarImovelContasVencidasAnoReferencia = repositorioFaturamento
							.verificarImovelContasVencidasAnoReferenciaEmRevisao(idImovel, ultimoDiaDoAnoDeReferencia,
											pConsiderarPgtoPendente);

			if(verificarImovelContasVencidasAnoReferencia){
				retorno = Boolean.FALSE;
			}

			if(retorno){

				// 2. Caso o sistema deva considerar guias de pagamentos vencidas
				// (P_CONSIDERA_GUIA_PAGTO_DECLARACAO_QUITACAO_ANUAL = 1) e o imóvel tenha guias de
				// pagamento vencidas no ano de referência (existe ocorrência na tabela
				// GUIA_PAGAMENTO_PRESTACAO com GPAG_ID = GPAG_ID da tabela GUIA_PAGAMENTO com
				// IMOV_ID = IMOV_ID da tabela IMOVEL e DCST_ID = DCST_ID da tabela
				// DEBITO_CREDITO_SITUACAO com DCST_ICVALIDO com o valor correspondente a "Sim" e
				// GPPR_DTVENCIMENTO menor ou igual ao último dia do ano de referência e caso seja
				// para verificar pagamento pendente
				// ("P_VERIFICA_PAGTO_PENDENTE_DECLARACAO_QUITACAO_ANUAL" = 1) e não tenha pagamento
				// (GPPR_ICPAGAMENTO = 2 OR GPPR_ICPAGAMENTO IS NULL)), não gerar a declaração para
				// o imóvel e passar para o próximo imóvel.

				Integer pConsiderarPgtoGuiaPagamento = Util
								.converterStringParaInteger((String) ParametroFaturamento.P_CONSIDERA_GUIA_PAGTO_DECLARACAO_QUITACAO_ANUAL
												.executar());

				if(pConsiderarPgtoGuiaPagamento.equals(Integer.valueOf(ConstantesSistema.SIM.intValue()))){

					boolean verificarImovelGuiasVencidasAnoReferencia = repositorioFaturamento.verificarImovelGuiasVencidasAnoReferencia(
									idImovel, ultimoDiaDoAnoDeReferencia, pConsiderarPgtoPendente);

					if(verificarImovelGuiasVencidasAnoReferencia){
						retorno = Boolean.FALSE;
					}
				}
			}

			if(retorno){
				// 3. Caso não existam pagamentos de conta para o imóvel no ano de referência (não
				// existe ocorrência na tabela PAGAMENTO_HISTORICO com IMOV_ID=IMOV_ID da tabela
				// IMOVEL e CNTA_ID <> nulo ano e mês de PGMT_DTPAGAMENTO igual ao ano de
				// referência) e caso o sistema deva considerar guias de pagamentos vencidas
				// (P_CONSIDERA_GUIA_PAGTO_DECLARACAO_QUITACAO_ANUAL = 1) e não existam pagamentos
				// de guias de pagamento para o imóvel no ano de referência (não existe ocorrência
				// na tabela PAGAMENTO_HISTORICO com IMOV_ID=IMOV_ID da tabela IMOVEL e GPAG_ID <>
				// nulo ano e mês de PGMT_DTPAGAMENTO igual ao ano de referência), não gerar a
				// declaração para o imóvel e passar para o próximo imóvel.

				// Obter Primeiro dia do ano, conforme ano base informado.
				Date dataInicial = Util.gerarDataInicialDoAnoApartirDoAnoRefencia(anoBaseDeclaracaoQuitacaoDebitoAnual);

				Integer referenciaInicial = Util.recuperaAnoMesDaData(dataInicial);

				// Obter Último dia do ano, conforme ano base informado.
				Date dataFinal = Util.gerarDataFinalDoAnoApartirDoAnoRefencia(anoBaseDeclaracaoQuitacaoDebitoAnual);

				Integer referenciaFinal = Util.recuperaAnoMesDaData(dataFinal);

				boolean verificarPagamentosHistoricoParaImovelAnoReferencia = repositorioFaturamento
								.verificarPagamentosHistoricoParaImovelAnoReferencia(idImovel, referenciaInicial, referenciaFinal,
												pConsiderarPgtoPendente);

				if(!verificarPagamentosHistoricoParaImovelAnoReferencia){
					retorno = Boolean.FALSE;
				}
			}

		}catch(ErroRepositorioException e){
			e.printStackTrace();
			throw new ControladorException("erro.sistema", e);
		}

		return retorno;
	}

}
