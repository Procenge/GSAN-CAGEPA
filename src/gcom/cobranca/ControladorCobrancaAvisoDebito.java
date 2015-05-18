package gcom.cobranca;

import gcom.arrecadacao.ControladorArrecadacaoLocal;
import gcom.arrecadacao.ControladorArrecadacaoLocalHome;
import gcom.arrecadacao.PagamentoTipo;
import gcom.batch.ControladorBatchLocal;
import gcom.batch.ControladorBatchLocalHome;
import gcom.cadastro.endereco.ControladorEnderecoLocal;
import gcom.cadastro.endereco.ControladorEnderecoLocalHome;
import gcom.cadastro.imovel.ControladorImovelLocal;
import gcom.cadastro.imovel.ControladorImovelLocalHome;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cobranca.bean.*;
import gcom.cobranca.parcelamento.Parcelamento;
import gcom.faturamento.ControladorFaturamentoLocal;
import gcom.faturamento.ControladorFaturamentoLocalHome;
import gcom.faturamento.conta.Conta;
import gcom.relatorio.cobranca.RelatorioAvisoDebito;
import gcom.relatorio.cobranca.RelatorioAvisoDebitoModelo2;
import gcom.relatorio.cobranca.RelatorioAvisoDebitoModelo3;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.*;

import java.math.BigDecimal;
import java.util.*;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.comparators.ComparatorChain;
import org.apache.log4j.Logger;


public class ControladorCobrancaAvisoDebito
				implements SessionBean, IControladorCobrancaAvisoDebito {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(ControladorCobrancaAvisoDebito.class);
	SessionContext sessionContext;
	protected IRepositorioCobranca repositorioCobranca = null;

	public void ejbCreate() throws CreateException{

		repositorioCobranca = RepositorioCobrancaHBM.getInstancia();

	}

	public void ejbRemove(){

	}

	public void ejbActivate(){

	}

	public void ejbPassivate(){

	}

	public void setSessionContext(SessionContext sessionContext){

		this.sessionContext = sessionContext;
	}

	private ControladorEnderecoLocal getControladorEndereco(){

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
	 * Este caso de uso gera os avisos de cobrança dos documentos de cobrança
	 * [UC0575] Emitir Aviso de Cobrança
	 * 
	 * @author Sávio Luiz
	 * @data 02/04/2007
	 * @param
	 * @return void
	 */
	public void gerarRelatorioAvisoDebitoModelo1(CobrancaAcaoAtividadeCronograma cobrancaAcaoAtividadeCronograma,
					CobrancaAcaoAtividadeComando cobrancaAcaoAtividadeComando, Date dataAtualPesquisa, CobrancaAcao acaoCobranca,
					CobrancaGrupo grupoCobranca, CobrancaCriterio cobrancaCriterio, Usuario usuario) throws ControladorException{

		List<Object> colecaoEmitirAvisoCobrancaHelper = new ArrayList<Object>();
		Collection colecaoCobrancaDocumento = null;
		Integer idCronogramaAtividadeAcaoCobranca = null;
		Integer idComandoAtividadeAcaoCobranca = null;
		Integer idAcaoCobranca = null;

		if(cobrancaAcaoAtividadeCronograma != null && cobrancaAcaoAtividadeCronograma.getId() != null){
			idCronogramaAtividadeAcaoCobranca = cobrancaAcaoAtividadeCronograma.getId();
		}
		if(cobrancaAcaoAtividadeComando != null && cobrancaAcaoAtividadeComando.getId() != null){
			idComandoAtividadeAcaoCobranca = cobrancaAcaoAtividadeComando.getId();
		}
		if(acaoCobranca != null && acaoCobranca.getId() != null){
			idAcaoCobranca = acaoCobranca.getId();
		}

		colecaoCobrancaDocumento = getControladorCobranca().pesquisarTodosCobrancaDocumentoParaEmitir(idCronogramaAtividadeAcaoCobranca,
						idComandoAtividadeAcaoCobranca, dataAtualPesquisa, idAcaoCobranca);

		if(colecaoCobrancaDocumento != null && !colecaoCobrancaDocumento.isEmpty()){

			LOGGER.info("QUANTIDADE DOC COBRANCA PRA EMITIR = " + colecaoCobrancaDocumento.size());

			int sequencialImpressao = 0;

			Iterator iteratorCobrancaDocumento = colecaoCobrancaDocumento.iterator();
			while(iteratorCobrancaDocumento.hasNext()){

				CobrancaDocumento cobrancaDocumento = (CobrancaDocumento) iteratorCobrancaDocumento.next();

				EmitirAvisoCobrancaHelper emitirAvisoCobrancaHelper = new EmitirAvisoCobrancaHelper();
				Collection colecaoCobrancaDocumentoItemConta = null;
				Collection colecaoCobrancaDocumentoItemGuiaPagamento = null;
				Collection<CobrancaDocumentoItem> colecaoCobrancaDocumentoDebitoACobrar = null;

				BigDecimal valorTotal = BigDecimal.ZERO;
				BigDecimal debitosAnteriores = BigDecimal.ZERO;

				// ------------------------------------------------------------------------------------
				// gera o numero sequencial da impressao
				int situacao = 0;
				sequencialImpressao++;
				int metadeColecao = 0;
				if(colecaoCobrancaDocumento.size() % 2 == 0){
					metadeColecao = colecaoCobrancaDocumento.size() / 2;
				}else{
					metadeColecao = (colecaoCobrancaDocumento.size() / 2) + 1;
				}
				while(situacao < 2){
					if(situacao == 0){
						situacao = 1;
						cobrancaDocumento.setSequencialImpressao(getControladorCobranca().atualizaSequencial(sequencialImpressao, situacao,
										metadeColecao));
					}else{
						situacao = 2;
						cobrancaDocumento.setSequencialImpressao(getControladorCobranca().atualizaSequencial(sequencialImpressao, situacao,
										metadeColecao));
					}
				}
				// ------------------------------------------------------------------------------------

				if(cobrancaDocumento != null){
					try{

						// pesquisa todas as contas, debitos e guias
						colecaoCobrancaDocumentoItemConta = this.repositorioCobranca
										.selecionarCobrancaDocumentoItemReferenteConta(cobrancaDocumento);

						if(colecaoCobrancaDocumentoItemConta == null){
							colecaoCobrancaDocumentoItemGuiaPagamento = this.repositorioCobranca
											.selecionarDadosCobrancaDocumentoItemReferenteGuiaPagamento(cobrancaDocumento);
						}

						if(colecaoCobrancaDocumentoItemConta == null && colecaoCobrancaDocumentoItemGuiaPagamento == null){
							colecaoCobrancaDocumentoDebitoACobrar = this.repositorioCobranca
											.selecionarCobrancaDocumentoItemReferenteDebitoACobrar(cobrancaDocumento);
						}

					}catch(ErroRepositorioException ex){
						ex.printStackTrace();
						throw new ControladorException("erro.sistema", ex);
					}

					// carregando os dados no helper do relatorio de aviso de debito
					emitirAvisoCobrancaHelper.setMatricula(cobrancaDocumento.getImovel().getIdParametrizado());

					if(cobrancaDocumento.getCliente() != null && cobrancaDocumento.getCliente().getNome() != null){
						emitirAvisoCobrancaHelper.setNomeCliente(cobrancaDocumento.getCliente().getNome());
					}else{
						emitirAvisoCobrancaHelper.setNomeCliente("");
					}

					emitirAvisoCobrancaHelper.setEndereco(cobrancaDocumento.getImovel().getEnderecoFormatadoAbreviadoSemBairro());
					emitirAvisoCobrancaHelper.setBairro(cobrancaDocumento.getImovel().getLogradouroBairro().getBairro().getNome());
					if(cobrancaDocumento.getImovel().getLigacaoAgua() != null
									&& cobrancaDocumento.getImovel().getLigacaoAgua().getHidrometroInstalacaoHistorico() != null){
						emitirAvisoCobrancaHelper.setHidrometro(cobrancaDocumento.getImovel().getLigacaoAgua()
										.getHidrometroInstalacaoHistorico().getNumeroHidrometro());
					}else{
						emitirAvisoCobrancaHelper.setHidrometro("");
					}

					emitirAvisoCobrancaHelper.setAcaoCobranca(acaoCobranca.getId().toString());

					String roteiro = cobrancaDocumento.getImovel().getSetorComercial().getId() + "-"
									+ cobrancaDocumento.getImovel().getRota().getId();

					if(cobrancaDocumento.getImovel().getNumeroSequencialRota() != null){
						roteiro = roteiro + "-" + cobrancaDocumento.getImovel().getNumeroSequencialRota();
					}

					emitirAvisoCobrancaHelper.setRoteiro(roteiro);

					emitirAvisoCobrancaHelper.setInscricao(cobrancaDocumento.getImovel().getInscricaoFormatada());

					Calendar dataAtual = Calendar.getInstance();
					Integer horas = dataAtual.get(Calendar.HOUR_OF_DAY);
					Integer minutos = dataAtual.get(Calendar.MINUTE);
					Integer segundos = dataAtual.get(Calendar.SECOND);
					emitirAvisoCobrancaHelper.setHoraImpressao("" + horas + ":" + minutos + ":" + segundos);
					emitirAvisoCobrancaHelper.setDataImpressao(Util.formatarData(dataAtual.getTime()));

					Util.adicionarNumeroDiasDeUmaData(dataAtual.getTime(), Integer.valueOf(acaoCobranca.getNumeroDiasValidade()).intValue());
					emitirAvisoCobrancaHelper.setDataComparecimento(Util.formatarData(dataAtual.getTime()));

					if(colecaoCobrancaDocumentoItemConta != null){
						// contas
						int limitador15Itens = 1;
						Collection<String> mesAno = new ArrayList<String>();
						Collection<String> vencimento = new ArrayList<String>();
						Collection<BigDecimal> valor = new ArrayList<BigDecimal>();

						Iterator<CobrancaDocumentoItem> itContas = colecaoCobrancaDocumentoItemConta.iterator();
						Integer mesAnoMaisAnterior = 300012;
						boolean teveConta = false;
						while(itContas.hasNext()){
							CobrancaDocumentoItem cobrancaDocumentoItem = itContas.next();
							teveConta = true;
							if(limitador15Itens <= 15){
								mesAno.add(Util.formatarAnoMesSemBarraParaMesAnoComBarra(cobrancaDocumentoItem.getContaGeral().getConta()
												.getReferencia()));
								vencimento.add(Util.formatarData(cobrancaDocumentoItem.getContaGeral().getConta().getDataVencimentoConta()));
								valor.add(cobrancaDocumentoItem.getContaGeral().getConta().getValorTotal());
							}else{
								debitosAnteriores = debitosAnteriores.add(cobrancaDocumentoItem.getContaGeral().getConta().getValorTotal());
							}
							if(Util.compararAnoMesReferencia(cobrancaDocumentoItem.getContaGeral().getConta().getReferencia(),
											mesAnoMaisAnterior, "<")){
								mesAnoMaisAnterior = cobrancaDocumentoItem.getContaGeral().getConta().getReferencia();
							}
							valorTotal = valorTotal.add(cobrancaDocumentoItem.getContaGeral().getConta().getValorTotal());
							limitador15Itens++;
						}

						if(teveConta){
							emitirAvisoCobrancaHelper.setDataDebitoAnterior(Util
											.formatarAnoMesSemBarraParaMesAnoComBarra(mesAnoMaisAnterior));
						}
						emitirAvisoCobrancaHelper.setMesAno(mesAno);
						emitirAvisoCobrancaHelper.setVencimento(vencimento);
						emitirAvisoCobrancaHelper.setValor(valor);

					}else if(colecaoCobrancaDocumentoItemGuiaPagamento != null){
						// guias
						Iterator<CobrancaDocumentoItem> itGuias = colecaoCobrancaDocumentoItemGuiaPagamento.iterator();
						while(itGuias.hasNext()){
							CobrancaDocumentoItem cobrancaDocumentoItem = itGuias.next();
							valorTotal = valorTotal.add(cobrancaDocumentoItem.getValorItemCobrado());
							debitosAnteriores = debitosAnteriores.add(cobrancaDocumentoItem.getValorItemCobrado());
						}
					}else if(colecaoCobrancaDocumentoDebitoACobrar != null){
						// debitos a cobrar
						Iterator<CobrancaDocumentoItem> itDebACob = colecaoCobrancaDocumentoDebitoACobrar.iterator();
						while(itDebACob.hasNext()){
							CobrancaDocumentoItem cobrancaDocumentoItem = itDebACob.next();
							valorTotal = valorTotal.add(cobrancaDocumentoItem.getDebitoACobrarGeral().getDebitoACobrar().getValorTotal());
							debitosAnteriores = debitosAnteriores.add(cobrancaDocumentoItem.getDebitoACobrarGeral().getDebitoACobrar()
											.getValorTotal());
						}
					}

				}

				emitirAvisoCobrancaHelper.setValorDebitosAnteriores(debitosAnteriores);
				emitirAvisoCobrancaHelper.setValorTotal(valorTotal);

				// ini = System.currentTimeMillis();

				// ------------------------------------------------------------------------------------------------------------
				// obtendo o codigo de barras
				String representacaoNumericaCodBarra = getControladorArrecadacao().obterRepresentacaoNumericaCodigoBarra(
								Integer.valueOf(PagamentoTipo.PAGAMENTO_TIPO_COBANCA_MATRICULA_IMOVEL), valorTotal,
								cobrancaDocumento.getImovel().getLocalidade().getId(), cobrancaDocumento.getImovel().getId(), null, null,
								null, null, cobrancaDocumento.getNumeroSequenciaDocumento() + "",
								cobrancaDocumento.getDocumentoTipo().getId(), null, null, null, null, null, null);

				// dif = System.currentTimeMillis() - ini;
				// LOGGER.info("5 - ############################## -> " + dif);
				// ini = System.currentTimeMillis();

				String representacaoNumericaCodBarraFormatada = representacaoNumericaCodBarra.substring(0, 11) + "-"
								+ representacaoNumericaCodBarra.substring(11, 12) + " " + representacaoNumericaCodBarra.substring(12, 23)
								+ "-" + representacaoNumericaCodBarra.substring(23, 24) + " "
								+ representacaoNumericaCodBarra.substring(24, 35) + "-" + representacaoNumericaCodBarra.substring(35, 36)
								+ " " + representacaoNumericaCodBarra.substring(36, 47) + "-"
								+ representacaoNumericaCodBarra.substring(47, 48);
				emitirAvisoCobrancaHelper.setRepresentacaoNumericaCodBarraFormatada(representacaoNumericaCodBarraFormatada);

				String representacaoNumericaCodBarraSemDigito = representacaoNumericaCodBarra.substring(0, 11)
								+ representacaoNumericaCodBarra.substring(12, 23) + representacaoNumericaCodBarra.substring(24, 35)
								+ representacaoNumericaCodBarra.substring(36, 47);
				emitirAvisoCobrancaHelper.setRepresentacaoNumericaCodBarraSemDigito(representacaoNumericaCodBarraSemDigito);

				colecaoEmitirAvisoCobrancaHelper.add(emitirAvisoCobrancaHelper);
				emitirAvisoCobrancaHelper = null;
			}

			// ------------------------------------------------------------------------------------------------------------------------------

			// Monta os relatorios em blocos de 1000
			if(colecaoEmitirAvisoCobrancaHelper != null && !colecaoEmitirAvisoCobrancaHelper.isEmpty()){

				LOGGER.info("QUANTIDADE TOTAL = " + colecaoEmitirAvisoCobrancaHelper.size());

				Collection<Collection<Object>> colecaoParcialEmitirAvisoCobrancaHelper = getControladorCobranca().dividirColecaoEmBlocos(
								colecaoEmitirAvisoCobrancaHelper, ConstantesSistema.QUANTIDADE_LIMITE_RELATORIOS_POR_ARQUIVO);

				int contadorBlocoContasAEmitir = 1;
				int totalBlocosContasAEmitir = (Util.dividirArredondarResultado(colecaoEmitirAvisoCobrancaHelper.size(),
								ConstantesSistema.QUANTIDADE_BLOCO_IMPRESSOES_EMISSAO_CONTA_FATURAMENTO, BigDecimal.ROUND_CEILING));

				if(totalBlocosContasAEmitir == 0){
					totalBlocosContasAEmitir = 1;
				}

				LOGGER.info("QUANTIDADE BLOCOS = " + colecaoParcialEmitirAvisoCobrancaHelper.size());

				for(Collection bloco : colecaoParcialEmitirAvisoCobrancaHelper){

					Collection<EmitirAvisoCobrancaHelper> tmp = bloco;

					String mensagemArquivo = "PARTE: " + contadorBlocoContasAEmitir + "/" + totalBlocosContasAEmitir;
					RelatorioAvisoDebito relatorioAvisoDebito = new RelatorioAvisoDebito(usuario);
					relatorioAvisoDebito.addParametro("colecaoEmitirAvisoCobrancaHelper", tmp);
					relatorioAvisoDebito.addParametro("tipoFormatoRelatorio", TarefaRelatorio.TIPO_PDF);
					relatorioAvisoDebito.addParametro("descricaoArquivo", mensagemArquivo);

					this.getControladorBatch().iniciarProcessoRelatorio(relatorioAvisoDebito);
					contadorBlocoContasAEmitir++;
				}
			}
		}
	}

	public void gerarRelatorioAvisoDebitoModelo2(CobrancaAcaoAtividadeCronograma cobrancaAcaoAtividadeCronograma,
					CobrancaAcaoAtividadeComando cobrancaAcaoAtividadeComando, Usuario usuario) throws ControladorException{

		LOGGER.info("INICIO: GERANDO PDF RELATÓRIO AVISO DE DÉBITO MODELO 2.");

		Collection<IEmitirRelatorioAvisoDebitoHelper> colecaoEmitirRelatorioAvisoDebitoHelper = null;
		Collection<IEmitirRelatorioAvisoDebitoHelper> colecaoEmitirRelatorioAvisoDebitoHelperOrdenada = null;
		Integer idCronogramaAtividadeAcaoCobranca = null;
		Integer idComandoAtividadeAcaoCobranca = null;

		if(cobrancaAcaoAtividadeCronograma != null && cobrancaAcaoAtividadeCronograma.getId() != null){
			idCronogramaAtividadeAcaoCobranca = cobrancaAcaoAtividadeCronograma.getId();
		}

		if(cobrancaAcaoAtividadeComando != null && cobrancaAcaoAtividadeComando.getId() != null){
			idComandoAtividadeAcaoCobranca = cobrancaAcaoAtividadeComando.getId();
		}

		try{

			// 1. Lista de documentos de cobrança ordenada por: localidade (LOCA_ID), setor
			// (CBDO_CDSETORCOMERCIAL), quadra (CBDO_NNQUADRA), lote (IMOV_NNLOTE da tabela IMOVEL
			// com IMOV_ID=IMOV_ID da tabela COBRANCA_DOCUMENTO) e sublote (IMOV_NNSUBLOTE da tabela
			// IMOVEL com IMOV_ID=IMOV_ID da tabela COBRANCA_DOCUMENTO).
			LOGGER.info("PESQUISA: COLEÇÃO DOCUMENTOS DE COBRANÇA.");
			colecaoEmitirRelatorioAvisoDebitoHelper = repositorioCobranca.pesquisarCobrancaDocumentoRelatorioAvisoDebito(
							idCronogramaAtividadeAcaoCobranca, idComandoAtividadeAcaoCobranca);

			// 3. Coleção ordenada e com os campos necessários para gerar o PDF já prenchidos e
			// formatados.
			if(!Util.isVazioOrNulo(colecaoEmitirRelatorioAvisoDebitoHelper)){

				// Completa os campos dos helpers que não foram retornados na pesquisa realizada
				// pelo método de formatação
				LOGGER.info("COMPLETA: CAMPOS DO HELPER PARA MONTAR O RELATÓRIO.");
				this.completarEmitirDocumentoAvisoDebitoModelo2Helper(colecaoEmitirRelatorioAvisoDebitoHelper, 9, "ANTES DE", true);

				// Ordena e metade de um lado e outra metade de outro lado.
				colecaoEmitirRelatorioAvisoDebitoHelperOrdenada = this
								.ordenarColecaoEmitirArquivoAvisoDebitoModelo2Helper(colecaoEmitirRelatorioAvisoDebitoHelper);

				List<EmitirRelatorioAvisoDebitoHelper> colecaoHelperOrdenada = (ArrayList) colecaoEmitirRelatorioAvisoDebitoHelperOrdenada;

				List<EmitirRelatorioAvisoDebitoHelper> colecaoHelperOrdenadaRelatorio = new ArrayList<EmitirRelatorioAvisoDebitoHelper>();

				// Bloco responsável por gerar o código de barras para o relatório.
				for(Integer contador = 0; contador < colecaoHelperOrdenada.size(); contador++){

					EmitirRelatorioAvisoDebitoHelper helper = colecaoHelperOrdenada.get(contador);

					String representacaoNumericaCodBarra = "";

					// [UC0229] Obtém a representação numérica do código de barra
					representacaoNumericaCodBarra = this.getControladorArrecadacao().obterRepresentacaoNumericaCodigoBarra(
									PagamentoTipo.PAGAMENTO_TIPO_COBANCA_MATRICULA_IMOVEL, helper.getValorDocumento(),
									helper.getIdLocalidade(), helper.getIdImovel(), null, null, null, null,
									helper.getNumeroSequenciaDocumento().toString(), helper.getDocumentoTipoId(), null, null, null, null,
									null, null);

					// Formata a representação númerica do código de barras
					String representacaoNumericaCodBarraFormatada = representacaoNumericaCodBarra.substring(0, 11) + "-"
									+ representacaoNumericaCodBarra.substring(11, 12) + " "
									+ representacaoNumericaCodBarra.substring(12, 23) + "-"
									+ representacaoNumericaCodBarra.substring(23, 24) + " "
									+ representacaoNumericaCodBarra.substring(24, 35) + "-"
									+ representacaoNumericaCodBarra.substring(35, 36) + " "
									+ representacaoNumericaCodBarra.substring(36, 47) + "-"
									+ representacaoNumericaCodBarra.substring(47, 48);

					helper.setRepresentacaoNumericaCodBarra(representacaoNumericaCodBarraFormatada);

					String representacaoNumericaCodBarraSemDigito = representacaoNumericaCodBarra.substring(0, 11)
									+ representacaoNumericaCodBarra.substring(12, 23) + representacaoNumericaCodBarra.substring(24, 35)
									+ representacaoNumericaCodBarra.substring(36, 47);

					helper.setRepresentacaoNumericaCodBarraSemDigito(representacaoNumericaCodBarraSemDigito);

					colecaoHelperOrdenadaRelatorio.add(helper);

				}

				LOGGER.info("RELATÓRIO: ENVIANDO PARA RELATÓRIO.");
				RelatorioAvisoDebitoModelo2 relatorio = new RelatorioAvisoDebitoModelo2(usuario);
				relatorio.addParametro("colecaoHelperOrdenada", colecaoHelperOrdenadaRelatorio);
				relatorio.addParametro("tipoFormatoRelatorio", TarefaRelatorio.TIPO_PDF);
				this.getControladorBatch().iniciarProcessoRelatorio(relatorio);

			}

			LOGGER.info("FIM: GERANDO PDF RELATÓRIO AVISO DE DÉBITO MODELO 2.");

		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}

	}

	// OC1420030
	// AVISO DE DEBITO (SAAE)
	public void gerarRelatorioAvisoDebitoModelo3(CobrancaAcaoAtividadeCronograma cobrancaAcaoAtividadeCronograma,
					CobrancaAcaoAtividadeComando cobrancaAcaoAtividadeComando, Date dataAtualPesquisa, CobrancaAcao acaoCobranca,
					CobrancaGrupo grupoCobranca, CobrancaCriterio cobrancaCriterio, Usuario usuario) throws ControladorException{

		List<Object> colecaoEmitirAvisoCobrancaHelper = new ArrayList<Object>();
		Collection colecaoCobrancaDocumento = null;
		Integer idCronogramaAtividadeAcaoCobranca = null;
		Integer idComandoAtividadeAcaoCobranca = null;
		Integer idAcaoCobranca = null;

		if(cobrancaAcaoAtividadeCronograma != null && cobrancaAcaoAtividadeCronograma.getId() != null){
			idCronogramaAtividadeAcaoCobranca = cobrancaAcaoAtividadeCronograma.getId();
		}
		if(cobrancaAcaoAtividadeComando != null && cobrancaAcaoAtividadeComando.getId() != null){
			idComandoAtividadeAcaoCobranca = cobrancaAcaoAtividadeComando.getId();
		}
		if(acaoCobranca != null && acaoCobranca.getId() != null){
			idAcaoCobranca = acaoCobranca.getId();
		}

		colecaoCobrancaDocumento = getControladorCobranca().pesquisarTodosCobrancaDocumentoParaEmitir(idCronogramaAtividadeAcaoCobranca,
						idComandoAtividadeAcaoCobranca, dataAtualPesquisa, idAcaoCobranca);

		if(colecaoCobrancaDocumento != null && !colecaoCobrancaDocumento.isEmpty()){

			LOGGER.info("QUANTIDADE DOC COBRANCA PRA EMITIR = " + colecaoCobrancaDocumento.size());

			int sequencialImpressao = 0;

			Iterator iteratorCobrancaDocumento = colecaoCobrancaDocumento.iterator();
			while(iteratorCobrancaDocumento.hasNext()){

				CobrancaDocumento cobrancaDocumento = (CobrancaDocumento) iteratorCobrancaDocumento.next();

				EmitirAvisoCobrancaHelper emitirAvisoCobrancaHelper = new EmitirAvisoCobrancaHelper();
				Collection colecaoCobrancaDocumentoItemConta = null;
				Collection colecaoCobrancaDocumentoItemGuiaPagamento = null;
				Collection<CobrancaDocumentoItem> colecaoCobrancaDocumentoDebitoACobrar = null;

				BigDecimal valorTotal = BigDecimal.ZERO;
				BigDecimal debitosAnteriores = BigDecimal.ZERO;

				// ------------------------------------------------------------------------------------
				// gera o numero sequencial da impressao
				int situacao = 0;
				sequencialImpressao++;
				int metadeColecao = 0;
				if(colecaoCobrancaDocumento.size() % 2 == 0){
					metadeColecao = colecaoCobrancaDocumento.size() / 2;
				}else{
					metadeColecao = (colecaoCobrancaDocumento.size() / 2) + 1;
				}
				while(situacao < 2){
					if(situacao == 0){
						situacao = 1;
						cobrancaDocumento.setSequencialImpressao(getControladorCobranca().atualizaSequencial(sequencialImpressao, situacao,
										metadeColecao));
					}else{
						situacao = 2;
						cobrancaDocumento.setSequencialImpressao(getControladorCobranca().atualizaSequencial(sequencialImpressao, situacao,
										metadeColecao));
					}
				}
				// ------------------------------------------------------------------------------------

				if(cobrancaDocumento != null){
					try{

						// pesquisa todas as contas, debitos e guias
						colecaoCobrancaDocumentoItemConta = this.repositorioCobranca
										.selecionarCobrancaDocumentoItemReferenteConta(cobrancaDocumento);

						if(colecaoCobrancaDocumentoItemConta == null){
							colecaoCobrancaDocumentoItemGuiaPagamento = this.repositorioCobranca
											.selecionarDadosCobrancaDocumentoItemReferenteGuiaPagamento(cobrancaDocumento);
						}

						if(colecaoCobrancaDocumentoItemConta == null && colecaoCobrancaDocumentoItemGuiaPagamento == null){
							colecaoCobrancaDocumentoDebitoACobrar = this.repositorioCobranca
											.selecionarCobrancaDocumentoItemReferenteDebitoACobrar(cobrancaDocumento);
						}

					}catch(ErroRepositorioException ex){
						ex.printStackTrace();
						throw new ControladorException("erro.sistema", ex);
					}

					// carregando os dados no helper do relatorio de aviso de debito
					emitirAvisoCobrancaHelper.setMatricula(cobrancaDocumento.getImovel().getIdParametrizado());

					if(cobrancaDocumento.getCliente() != null && cobrancaDocumento.getCliente().getNome() != null){
						emitirAvisoCobrancaHelper.setNomeCliente(cobrancaDocumento.getCliente().getNome());
					}else{
						emitirAvisoCobrancaHelper.setNomeCliente("");
					}

					emitirAvisoCobrancaHelper.setEndereco(cobrancaDocumento.getImovel().getEnderecoFormatadoAbreviadoSemBairro());
					emitirAvisoCobrancaHelper.setBairro(cobrancaDocumento.getImovel().getLogradouroBairro().getBairro().getNome());
					emitirAvisoCobrancaHelper.setCep(cobrancaDocumento.getImovel().getLogradouroCep().getCep().getCepFormatado());
					if(cobrancaDocumento.getImovel().getLigacaoAgua() != null
									&& cobrancaDocumento.getImovel().getLigacaoAgua().getHidrometroInstalacaoHistorico() != null){
						emitirAvisoCobrancaHelper.setHidrometro(cobrancaDocumento.getImovel().getLigacaoAgua()
										.getHidrometroInstalacaoHistorico().getNumeroHidrometro());
					}else{
						emitirAvisoCobrancaHelper.setHidrometro("");
					}

					emitirAvisoCobrancaHelper.setAcaoCobranca(acaoCobranca.getId().toString());

					String roteiro = cobrancaDocumento.getImovel().getSetorComercial().getId() + "-"
									+ cobrancaDocumento.getImovel().getRota().getId();

					if(cobrancaDocumento.getImovel().getNumeroSequencialRota() != null){
						roteiro = roteiro + "-" + cobrancaDocumento.getImovel().getNumeroSequencialRota();
					}

					emitirAvisoCobrancaHelper.setRoteiro(roteiro);

					emitirAvisoCobrancaHelper.setInscricao(cobrancaDocumento.getImovel().getInscricaoFormatada());

					Calendar dataAtual = Calendar.getInstance();
					Integer horas = dataAtual.get(Calendar.HOUR_OF_DAY);
					Integer minutos = dataAtual.get(Calendar.MINUTE);
					Integer segundos = dataAtual.get(Calendar.SECOND);
					emitirAvisoCobrancaHelper.setHoraImpressao("" + horas + ":" + minutos + ":" + segundos);
					emitirAvisoCobrancaHelper.setDataImpressao(Util.formatarData(dataAtual.getTime()));

					Util.adicionarNumeroDiasDeUmaData(dataAtual.getTime(), Integer.valueOf(acaoCobranca.getNumeroDiasValidade()).intValue());
					emitirAvisoCobrancaHelper.setDataComparecimento(Util.formatarData(dataAtual.getTime()));

					if(colecaoCobrancaDocumentoItemConta != null){
						// contas
						int limitador15Itens = 1;
						Collection<String> mesAno = new ArrayList<String>();
						Collection<String> vencimento = new ArrayList<String>();
						Collection<BigDecimal> valor = new ArrayList<BigDecimal>();

						Iterator<CobrancaDocumentoItem> itContas = colecaoCobrancaDocumentoItemConta.iterator();
						Integer mesAnoMaisAnterior = 300012;
						boolean teveConta = false;
						while(itContas.hasNext()){
							CobrancaDocumentoItem cobrancaDocumentoItem = itContas.next();
							teveConta = true;
							if(limitador15Itens <= 15){
								mesAno.add(Util.formatarAnoMesSemBarraParaMesAnoComBarra(cobrancaDocumentoItem.getContaGeral().getConta()
												.getReferencia()));
								vencimento.add(Util.formatarData(cobrancaDocumentoItem.getContaGeral().getConta().getDataVencimentoConta()));
								valor.add(cobrancaDocumentoItem.getContaGeral().getConta().getValorTotal());
							}else{
								debitosAnteriores = debitosAnteriores.add(cobrancaDocumentoItem.getContaGeral().getConta().getValorTotal());
							}
							if(Util.compararAnoMesReferencia(cobrancaDocumentoItem.getContaGeral().getConta().getReferencia(),
											mesAnoMaisAnterior, "<")){
								mesAnoMaisAnterior = cobrancaDocumentoItem.getContaGeral().getConta().getReferencia();
							}
							valorTotal = valorTotal.add(cobrancaDocumentoItem.getContaGeral().getConta().getValorTotal());
							limitador15Itens++;
						}

						if(teveConta){
							emitirAvisoCobrancaHelper.setDataDebitoAnterior(Util
											.formatarAnoMesSemBarraParaMesAnoComBarra(mesAnoMaisAnterior));
						}
						emitirAvisoCobrancaHelper.setMesAno(mesAno);
						emitirAvisoCobrancaHelper.setVencimento(vencimento);
						emitirAvisoCobrancaHelper.setValor(valor);

					}else if(colecaoCobrancaDocumentoItemGuiaPagamento != null){
						// guias
						Iterator<CobrancaDocumentoItem> itGuias = colecaoCobrancaDocumentoItemGuiaPagamento.iterator();
						while(itGuias.hasNext()){
							CobrancaDocumentoItem cobrancaDocumentoItem = itGuias.next();
							valorTotal = valorTotal.add(cobrancaDocumentoItem.getValorItemCobrado());
							debitosAnteriores = debitosAnteriores.add(cobrancaDocumentoItem.getValorItemCobrado());
						}
					}else if(colecaoCobrancaDocumentoDebitoACobrar != null){
						// debitos a cobrar
						Iterator<CobrancaDocumentoItem> itDebACob = colecaoCobrancaDocumentoDebitoACobrar.iterator();
						while(itDebACob.hasNext()){
							CobrancaDocumentoItem cobrancaDocumentoItem = itDebACob.next();
							valorTotal = valorTotal.add(cobrancaDocumentoItem.getDebitoACobrarGeral().getDebitoACobrar().getValorTotal());
							debitosAnteriores = debitosAnteriores.add(cobrancaDocumentoItem.getDebitoACobrarGeral().getDebitoACobrar()
											.getValorTotal());
						}
					}

				}

				emitirAvisoCobrancaHelper.setValorDebitosAnteriores(debitosAnteriores);
				emitirAvisoCobrancaHelper.setValorTotal(valorTotal);

				// ini = System.currentTimeMillis();

				// ------------------------------------------------------------------------------------------------------------
				// obtendo o codigo de barras
				String representacaoNumericaCodBarra = getControladorArrecadacao().obterRepresentacaoNumericaCodigoBarra(
								Integer.valueOf(PagamentoTipo.PAGAMENTO_TIPO_COBANCA_MATRICULA_IMOVEL), valorTotal,
								cobrancaDocumento.getImovel().getLocalidade().getId(), cobrancaDocumento.getImovel().getId(), null, null,
								null, null, cobrancaDocumento.getNumeroSequenciaDocumento() + "",
								cobrancaDocumento.getDocumentoTipo().getId(), null, null, null, null, null, null);

				// dif = System.currentTimeMillis() - ini;
				// LOGGER.info("5 - ############################## -> " + dif);
				// ini = System.currentTimeMillis();

				String representacaoNumericaCodBarraFormatada = representacaoNumericaCodBarra.substring(0, 11) + "-"
								+ representacaoNumericaCodBarra.substring(11, 12) + " " + representacaoNumericaCodBarra.substring(12, 23)
								+ "-" + representacaoNumericaCodBarra.substring(23, 24) + " "
								+ representacaoNumericaCodBarra.substring(24, 35) + "-" + representacaoNumericaCodBarra.substring(35, 36)
								+ " " + representacaoNumericaCodBarra.substring(36, 47) + "-"
								+ representacaoNumericaCodBarra.substring(47, 48);
				emitirAvisoCobrancaHelper.setRepresentacaoNumericaCodBarraFormatada(representacaoNumericaCodBarraFormatada);

				String representacaoNumericaCodBarraSemDigito = representacaoNumericaCodBarra.substring(0, 11)
								+ representacaoNumericaCodBarra.substring(12, 23) + representacaoNumericaCodBarra.substring(24, 35)
								+ representacaoNumericaCodBarra.substring(36, 47);
				emitirAvisoCobrancaHelper.setRepresentacaoNumericaCodBarraSemDigito(representacaoNumericaCodBarraSemDigito);

				colecaoEmitirAvisoCobrancaHelper.add(emitirAvisoCobrancaHelper);
				emitirAvisoCobrancaHelper = null;
			}

			// ------------------------------------------------------------------------------------------------------------------------------

			// Monta os relatorios em blocos de 1000
			if(colecaoEmitirAvisoCobrancaHelper != null && !colecaoEmitirAvisoCobrancaHelper.isEmpty()){

				LOGGER.info("QUANTIDADE TOTAL = " + colecaoEmitirAvisoCobrancaHelper.size());

				Collection<Collection<Object>> colecaoParcialEmitirAvisoCobrancaHelper = getControladorCobranca().dividirColecaoEmBlocos(
								colecaoEmitirAvisoCobrancaHelper, ConstantesSistema.QUANTIDADE_LIMITE_RELATORIOS_POR_ARQUIVO);

				int contadorBlocoContasAEmitir = 1;
				int totalBlocosContasAEmitir = (Util.dividirArredondarResultado(colecaoEmitirAvisoCobrancaHelper.size(),
								ConstantesSistema.QUANTIDADE_BLOCO_IMPRESSOES_EMISSAO_CONTA_FATURAMENTO, BigDecimal.ROUND_CEILING));

				if(totalBlocosContasAEmitir == 0){
					totalBlocosContasAEmitir = 1;
				}

				LOGGER.info("QUANTIDADE BLOCOS = " + colecaoParcialEmitirAvisoCobrancaHelper.size());

				for(Collection bloco : colecaoParcialEmitirAvisoCobrancaHelper){

					Collection<EmitirAvisoCobrancaHelper> tmp = bloco;

					String mensagemArquivo = "PARTE: " + contadorBlocoContasAEmitir + "/" + totalBlocosContasAEmitir;
					RelatorioAvisoDebitoModelo3 relatorioAvisoDebito = new RelatorioAvisoDebitoModelo3(usuario);
					relatorioAvisoDebito.addParametro("colecaoEmitirAvisoCobrancaHelper", tmp);
					relatorioAvisoDebito.addParametro("tipoFormatoRelatorio", TarefaRelatorio.TIPO_PDF);
					relatorioAvisoDebito.addParametro("descricaoArquivo", mensagemArquivo);

					this.getControladorBatch().iniciarProcessoRelatorio(relatorioAvisoDebito);
					LOGGER.info("FIM: GERANDO PDF RELATÓRIO AVISO DE DÉBITO MODELO 3.");
					contadorBlocoContasAEmitir++;
				}
			}
		}
	}

	private void completarEmitirDocumentoAvisoDebitoModelo2Helper(
					Collection<IEmitirRelatorioAvisoDebitoHelper> colecaoEmitirDocumentoAvisoDebitoModelo2Helper,
					int limiteContasExibidas, String textoContasMaisQueLimite, boolean calcularAcrescimos) throws ControladorException{

		try{

			String enderecoFormatado = null;
			CobrancaDocumentoItemHelper docItemHelper = null;
			CobrancaDocumentoItemHelper docItemHelperMaiorQueLimiteContasExibidas = null;
			SistemaParametro sistemaParametros = getControladorUtil().pesquisarParametrosDoSistema();

			for(IEmitirRelatorioAvisoDebitoHelper helper : colecaoEmitirDocumentoAvisoDebitoModelo2Helper){

				enderecoFormatado = getControladorEndereco().pesquisarEnderecoFormatado(helper.getIdImovel());
				helper.setEnderecoFormatado(enderecoFormatado);
				enderecoFormatado = null;
				List<CobrancaDocumentoItemHelper> colecaoItensCobrancaDocumentoHelper = new ArrayList<CobrancaDocumentoItemHelper>();
				CobrancaDocumento cobrancaDocumento = new CobrancaDocumento();
				cobrancaDocumento.setId(helper.getIdCobrancaDocumento());

				List<CobrancaDocumentoItem> colecaoCobrancaDocumentoItem = (List<CobrancaDocumentoItem>) this.repositorioCobranca
								.pesquisarCobrancaDocumentoItemContaAvisoDebitoModelo2(cobrancaDocumento.getId());

				int contador = 1;
				boolean maisDoQueLimiteGrid = false;
				docItemHelperMaiorQueLimiteContasExibidas = new CobrancaDocumentoItemHelper();
				docItemHelperMaiorQueLimiteContasExibidas.setAnoMesReferenciaConta(textoContasMaisQueLimite);
				docItemHelperMaiorQueLimiteContasExibidas.setValorAcrescimos(BigDecimal.ZERO);
				docItemHelperMaiorQueLimiteContasExibidas.setValorItemCobrado(BigDecimal.ZERO);
				CalcularAcrescimoPorImpontualidadeHelper calcularAcrescimoPorImpontualidade = null;
				BigDecimal valorMultasCobradas = null;
				Date dataVencimento = null;
				BigDecimal valorJuros = BigDecimal.ZERO;
				BigDecimal valorMulta = BigDecimal.ZERO;
				BigDecimal valorCorrecao = BigDecimal.ZERO;
				Conta conta = null;

				// Obtém as últimas contas em débito
				for(CobrancaDocumentoItem item : colecaoCobrancaDocumentoItem){

					docItemHelper = new CobrancaDocumentoItemHelper();

					if(contador > limiteContasExibidas){

						maisDoQueLimiteGrid = true;

						if(item.getValorAcrescimos() == null){

							item.setValorAcrescimos(BigDecimal.ZERO);
						}

						if(item.getValorItemCobrado() == null){

							item.setValorItemCobrado(BigDecimal.ZERO);
						}

						docItemHelperMaiorQueLimiteContasExibidas.setValorAcrescimos(item.getValorAcrescimos().add(
										docItemHelperMaiorQueLimiteContasExibidas.getValorAcrescimos()));
						docItemHelperMaiorQueLimiteContasExibidas.setValorItemCobrado(item.getValorItemCobrado().add(
										docItemHelperMaiorQueLimiteContasExibidas.getValorItemCobrado()));

					}else{

						conta = item.getContaGeral().getConta();
						docItemHelper.setAnoMesReferenciaConta(String.valueOf(Util.formatarAnoMesParaMesAno(conta.getReferencia())));
						docItemHelper.setDataVencimento(conta.getDataVencimentoConta());
						docItemHelper.setValorAcrescimos(item.getValorAcrescimos());
						docItemHelper.setValorItemCobrado(item.getValorItemCobrado());
						colecaoItensCobrancaDocumentoHelper.add(docItemHelper);
					}

					if(calcularAcrescimos && Util.isNaoNuloBrancoZero(item.getContaGeral())
									&& Util.isNaoNuloBrancoZero(item.getContaGeral().getConta())){

						calcularAcrescimoPorImpontualidade = new CalcularAcrescimoPorImpontualidadeHelper();
						valorMultasCobradas = getControladorFaturamento().pesquisarValorMultasCobradas(item.getContaGeral().getId());
						dataVencimento = conta.getDataVencimentoConta();

						// [UC0216] Calcular Acrescimo por Impontualidade
						calcularAcrescimoPorImpontualidade = getControladorCobranca().calcularAcrescimoPorImpontualidadeBancoDeDados(
										item.getContaGeral().getConta().getReferencia(), dataVencimento, null, item.getValorItemCobrado(),
										valorMultasCobradas, conta.getIndicadorCobrancaMulta(),
										sistemaParametros.getAnoMesArrecadacao().toString(), item.getContaGeral().getId(), new Date(),
										ConstantesSistema.SIM, ConstantesSistema.SIM, ConstantesSistema.SIM, null);

						if(calcularAcrescimoPorImpontualidade != null){

							// Valor de Correção
							if(calcularAcrescimoPorImpontualidade.getValorAtualizacaoMonetaria() != null){

								valorCorrecao = valorCorrecao.add(calcularAcrescimoPorImpontualidade.getValorAtualizacaoMonetaria()
												.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO));
							}

							// Valor de Juros
							if(calcularAcrescimoPorImpontualidade.getValorJurosMora() != null){

								valorJuros = valorJuros.add(calcularAcrescimoPorImpontualidade.getValorJurosMora().setScale(
												Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO));

									}

									// Valor de Multa
									if(calcularAcrescimoPorImpontualidade.getValorMulta() != null){

										valorMulta = valorMulta.add(calcularAcrescimoPorImpontualidade.getValorMulta().setScale(
														Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO));

									}
								}
							}

					contador++;
				}

				// Ordenar a coleção por referência ascendente
				List sortFields = new ArrayList();
				sortFields.add(new BeanComparator("anoMesReferenciaContaInteger"));

				ComparatorChain multiSort = new ComparatorChain(sortFields);
				Collections.sort(colecaoItensCobrancaDocumentoHelper, multiSort);

				if(maisDoQueLimiteGrid){

					colecaoItensCobrancaDocumentoHelper.add(docItemHelperMaiorQueLimiteContasExibidas);
				}

				if(helper instanceof EmitirRelatorioAvisoDebitoHelper){

					EmitirRelatorioAvisoDebitoHelper emitirRelatorioAvisoDebitoHelper = (EmitirRelatorioAvisoDebitoHelper) helper;
					emitirRelatorioAvisoDebitoHelper.setColecaoItemConta(colecaoItensCobrancaDocumentoHelper);

					if(!Util.isVazioOrNulo(colecaoCobrancaDocumentoItem)){

						emitirRelatorioAvisoDebitoHelper.setQuantidadeContasEmDebito(colecaoCobrancaDocumentoItem.size());
						emitirRelatorioAvisoDebitoHelper.setValorTotalDebitosCorrigidos(emitirRelatorioAvisoDebitoHelper
										.getValorDocumento().add(valorCorrecao).add(valorJuros).add(valorMulta));
					}
				}
			}

		}catch(Exception e){
			throw new ControladorException("erro.sistema", e);
		}
	}

	private Collection<IEmitirRelatorioAvisoDebitoHelper> ordenarColecaoEmitirArquivoAvisoDebitoModelo2Helper(
					Collection<IEmitirRelatorioAvisoDebitoHelper> colecaoEmitirRelatorioAvisoDebitoHelper) throws ControladorException{

		LOGGER.info("INICIO: ORDENANDO O HELPER E DIVIDINDO EM DUAS PARTES (METADE1 E METADE2).");
		Collection<IEmitirRelatorioAvisoDebitoHelper> colecaoRetorno = new ArrayList<IEmitirRelatorioAvisoDebitoHelper>();
		Map<Integer, IEmitirRelatorioAvisoDebitoHelper> colecaoHashMap = new HashMap<Integer, IEmitirRelatorioAvisoDebitoHelper>();

		try{
			// Ordenar por mais de um campo
			List camposOrdenados = new ArrayList();
			camposOrdenados.add(new BeanComparator("idLocalidade"));
			camposOrdenados.add(new BeanComparator("codigoSetorComercial"));
			camposOrdenados.add(new BeanComparator("numeroQuadra"));
			camposOrdenados.add(new BeanComparator("numeroLote"));
			camposOrdenados.add(new BeanComparator("numeroSubLote"));

			ComparatorChain esquemaOrdenacao = new ComparatorChain(camposOrdenados);
			Collections.sort((List) colecaoEmitirRelatorioAvisoDebitoHelper, esquemaOrdenacao);

			// Gerar sequencial das contas antes da ordenação
			int totalContas = colecaoEmitirRelatorioAvisoDebitoHelper.size();
			int sequencialAtual = 1;

			for(IEmitirRelatorioAvisoDebitoHelper helper : colecaoEmitirRelatorioAvisoDebitoHelper){

				helper.setSequencialImpressao(sequencialAtual);
				helper.setTotalContasImpressao(totalContas);
				sequencialAtual++;
			}
			// Fim Gerar Sequencial

			Integer contador = Integer.valueOf(1);
			for(IEmitirRelatorioAvisoDebitoHelper helper : colecaoEmitirRelatorioAvisoDebitoHelper){
				colecaoHashMap.put(contador, helper);
				contador++;
			}

			Integer metade = null;
			int resto = colecaoEmitirRelatorioAvisoDebitoHelper.size() % 2;
			if(resto == 1){
				metade = (colecaoEmitirRelatorioAvisoDebitoHelper.size() / 2) + 1;
			}else{
				metade = colecaoEmitirRelatorioAvisoDebitoHelper.size() / 2;
			}

			for(int i = 1; i <= metade; i++){
				colecaoRetorno.add(colecaoHashMap.get(Integer.valueOf(i)));
				if(colecaoHashMap.get(Integer.valueOf(i) + metade) != null){
					colecaoRetorno.add(colecaoHashMap.get(Integer.valueOf(i) + metade));
				}
			}

		}catch(Exception e){
			throw new ControladorException("erro.sistema", e);
		}
		LOGGER.info("FIM: ORDENANDO O HELPER E DIVIDINDO EM DUAS PARTES (METADE1 E METADE2).");
		return colecaoRetorno;
	}
}
