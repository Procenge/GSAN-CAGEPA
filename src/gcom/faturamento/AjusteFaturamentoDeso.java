
package gcom.faturamento;

import gcom.arrecadacao.ControladorArrecadacaoLocal;
import gcom.arrecadacao.ControladorArrecadacaoLocalHome;
import gcom.arrecadacao.bean.OperacaoContabilHelper;
import gcom.arrecadacao.pagamento.FiltroPagamento;
import gcom.arrecadacao.pagamento.Pagamento;
import gcom.arrecadacao.pagamento.PagamentoHistorico;
import gcom.arrecadacao.pagamento.PagamentoSituacao;
import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoesgoto.ControladorLigacaoEsgotoLocal;
import gcom.atendimentopublico.ligacaoesgoto.ControladorLigacaoEsgotoLocalHome;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoSituacao;
import gcom.atendimentopublico.registroatendimento.ControladorRegistroAtendimentoLocal;
import gcom.atendimentopublico.registroatendimento.ControladorRegistroAtendimentoLocalHome;
import gcom.batch.ControladorBatchLocal;
import gcom.batch.ControladorBatchLocalHome;
import gcom.batch.IRepositorioBatch;
import gcom.batch.RepositorioBatchHBM;
import gcom.cadastro.cliente.ControladorClienteLocal;
import gcom.cadastro.cliente.ControladorClienteLocalHome;
import gcom.cadastro.imovel.*;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.Quadra;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cobranca.CobrancaForma;
import gcom.cobranca.ControladorCobrancaLocal;
import gcom.cobranca.ControladorCobrancaLocalHome;
import gcom.cobranca.DocumentoTipo;
import gcom.contabil.ControladorContabilLocal;
import gcom.contabil.ControladorContabilLocalHome;
import gcom.contabil.OperacaoContabil;
import gcom.fachada.Fachada;
import gcom.faturamento.bean.CalcularValoresAguaEsgotoHelper;
import gcom.faturamento.bean.EmitirContaHelper;
import gcom.faturamento.consumotarifa.ConsumoTarifa;
import gcom.faturamento.conta.*;
import gcom.faturamento.debito.*;
import gcom.faturamento.histograma.ControladorHistogramaLocal;
import gcom.faturamento.histograma.ControladorHistogramaLocalHome;
import gcom.integracao.acquagis.DadosAcquaGis;
import gcom.micromedicao.*;
import gcom.micromedicao.consumo.ConsumoHistorico;
import gcom.micromedicao.consumo.ConsumoTipo;
import gcom.micromedicao.consumo.LigacaoTipo;
import gcom.micromedicao.leitura.LeituraSituacao;
import gcom.micromedicao.leitura.LeituraTipo;
import gcom.micromedicao.medicao.MedicaoHistorico;
import gcom.micromedicao.medicao.MedicaoTipo;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.*;
import gcom.util.filtro.ParametroSimples;
import gcom.util.filtro.ParametroSimplesColecao;
import gcom.util.parametrizacao.faturamento.ParametroFaturamento;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.*;
import java.util.zip.ZipOutputStream;

import javax.ejb.CreateException;
import javax.ejb.EJBException;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;

public class AjusteFaturamentoDeso {

	public AjusteFaturamentoDeso() {

	}

	protected IRepositorioFaturamento repositorioFaturamento;

	protected IRepositorioMicromedicao repositorioMicromedicao;

	private IRepositorioBatch repositorioBatch = null;

	private static Logger log = Logger.getLogger(ControladorFaturamento.class);

	public void ejbCreate() throws CreateException{

		repositorioFaturamento = RepositorioFaturamentoHBM.getInstancia();
		repositorioMicromedicao = RepositorioMicromedicaoHBM.getInstancia();
		repositorioBatch = RepositorioBatchHBM.getInstancia();
	}

	/**
	 * Retorna o valor de controladorCliente
	 * 
	 * @return O valor de controladorCliente
	 */
	protected ControladorClienteLocal getControladorCliente(){

		ControladorClienteLocalHome localHome = null;
		ControladorClienteLocal local = null;

		// pega a instância do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorClienteLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_CLIENTE_SEJB);
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

	private ControladorBatchLocal getControladorBatch(){

		ControladorBatchLocalHome localHome = null;
		ControladorBatchLocal local = null;

		// pega a instância do ServiceLocator.

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

	private ControladorLigacaoEsgotoLocal getControladorLigacaoEsgoto(){

		ControladorLigacaoEsgotoLocalHome localHome = null;
		ControladorLigacaoEsgotoLocal local = null;

		// pega a instância do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();
			localHome = (ControladorLigacaoEsgotoLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_LIGACAO_ESGOTO_SEJB);
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

	/**
	 * Retorna o valor de controladorContabil
	 * 
	 * @return O valor de controladorContabil
	 */
	private ControladorContabilLocal getControladorContabil(){

		ControladorContabilLocalHome localHome = null;
		ControladorContabilLocal local = null;

		// pega a instância do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();
			localHome = (ControladorContabilLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_CONTABIL_SEJB);
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

	/**
	 * Author: Vivianne Sousa Data: 1804/03/2006
	 * Retorna o valor do Controlador Util
	 * 
	 * @return O valor de controladorUtil
	 */
	private ControladorUtilLocal getControladorUtil(){

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

	/**
	 * Retorna o valor de controladorLocalidade
	 * 
	 * @return O valor de controladorLocalidade
	 */
	private ControladorFaturamentoLocal getControladorFaturamento(){

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

	/**
	 * Retorna o valor de controladorImovel
	 * 
	 * @return O valor de controladorImovel
	 */
	private ControladorImovelLocal getControladorImovel(){

		ControladorImovelLocalHome localHome = null;
		ControladorImovelLocal local = null;

		// pega a instância do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();
			localHome = (ControladorImovelLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_IMOVEL_SEJB);
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

	/**
	 * Retorna o valor de controladorLocalidade
	 * 
	 * @return O valor de controladorLocalidade
	 */
	private ControladorMicromedicaoLocal getControladorMicromedicao(){

		ControladorMicromedicaoLocalHome localHome = null;
		ControladorMicromedicaoLocal local = null;

		// pega a instância do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorMicromedicaoLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_MICROMEDICAO_SEJB);
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

	private ControladorRegistroAtendimentoLocal getControladorRegistroAtendimento(){

		ControladorRegistroAtendimentoLocalHome localHome = null;
		ControladorRegistroAtendimentoLocal local = null;

		// pega a instância do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorRegistroAtendimentoLocalHome) locator
							.getLocalHome(ConstantesJNDI.CONTROLADOR_REGISTRO_ATENDIMENTO_SEJB);
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


	/**
	 * Retorna o valor de controladorHistograma
	 * 
	 * @return O valor de controladorHistograma
	 */
	protected ControladorHistogramaLocal getControladorHistograma(){

		ControladorHistogramaLocalHome localHome = null;
		ControladorHistogramaLocal local = null;

		// pega a instância do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorHistogramaLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_HISTOGRAMA_SEJB);
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


	/**
	 * @throws Exception
	 */

	public void executarAjusteValorDebitoCobrancaAdminstrativaCasal(Integer caso) throws Exception{

		if(caso.equals(1)){
			log.info("Iniciando executar Ajuste Valor DebitoCobrado MENOR QUE VALOR CONTA Casal ... ");
			this.executarAjusteValorDebitoCobradoValorMenorCasal();
			log.info("Fim executar Ajuste Valor DebitoCobrado MENOR QUE VALOR CONTA  ...");
		}else if(caso.equals(2)){
			log.info("Iniciando executar Ajuste Valor DebitoCobradoHistorico MENOR QUE VALOR CONTA_HISTORICO Casal ... ");
			this.executarAjusteValorDebitoCobradoHistoricoValorMenorCasal();
			log.info("Fim executarAjusteValorDebitoCobradoHistorico MENOR QUE VALOR CONTA_HISTORICO ... ");
		}else{
			log.info("Iniciando executar Ajuste Valor DebitoCobradoHistorico  MAIOR QUE VALOR CONTA_HISTORICO Casal... ");
			this.executarAjusteValorDebitoCobradoHistoricoValorMaiorCasal();
			log.info("Fim executar Ajuste Valor DebitoCobradoHistorico  MAIOR QUE VALOR CONTA_HISTORICO... ");
		}

	}

	/**
	 * caso 1
	 * 
	 * @throws Exception
	 */
	public void executarAjusteValorDebitoCobradoValorMenorCasal() throws Exception{

		log.info(" CASO 1 ");

		// Inicializa as instacias dos repositórios usados
		this.ejbCreate();

		String nomeArquivo = "AjusteDebitoCobradoCasal.TXT";
		StringBuilder arquivoConferencia = new StringBuilder();

		arquivoConferencia.append("________________________________________________________________________________________________");

		// Obtém : [0] = cnta_id, [1] cnta_vldebitos, [2] sum(dbcb_vlprestacao) , [3]
		// quantidadeDebitos

		Collection<Object[]> colecaoObjetos = repositorioFaturamento.pesquisarContasAjusteCasal();

		log.info(" Quantidade : " + colecaoObjetos.size());
		Integer contator = 0;

		Integer idConta = null;
		BigDecimal valorDebitoConta = null;
		BigDecimal valorTotalDebitosCobradosConta = null;
		Integer quantidedeDebitosConta = null;

		if(colecaoObjetos != null && !colecaoObjetos.isEmpty()){

			Iterator iterator = colecaoObjetos.iterator();

			while(iterator.hasNext()){
				contator = contator + 1;

				log.info(" Processados  : " + contator);

				Object[] obj = (Object[]) iterator.next();

				idConta = (Integer) obj[0];
				
				valorDebitoConta = (BigDecimal) obj[1];

				valorTotalDebitosCobradosConta = (BigDecimal) obj[2];

				quantidedeDebitosConta = (Integer) obj[3];

				log.info(" conta: " + idConta + " valor debito conta = " + valorDebitoConta + " valor total debito cobrado = "
								+ valorTotalDebitosCobradosConta + " quantidade debitos = " + quantidedeDebitosConta);

				if(valorDebitoConta.compareTo(valorTotalDebitosCobradosConta) > 0){
					BigDecimal diferenca = valorDebitoConta.subtract(valorTotalDebitosCobradosConta);
					log.info("diferença = " + diferenca);
					DebitoCobrado debitoCobrado = repositorioFaturamento.pesquisarMaiorIdDebitoCobradoPorIdConta(idConta);
					BigDecimal valorParaAtualizar = debitoCobrado.getValorPrestacao().add(diferenca);

					if(debitoCobrado != null){
						debitoCobrado.setValorPrestacao(valorParaAtualizar);
						debitoCobrado.setUltimaAlteracao(new Date());
						log.info(" conta: " + idConta + " debitoCobrado: " + debitoCobrado.getId() + " valor debito Cobrado atualizado= "
										+ valorParaAtualizar);

						arquivoConferencia.append(" conta: " + idConta + " debitoCobrado: " + debitoCobrado.getId()
										+ " valor Total Debitos cobrados Conta (Anterior) = " + valorTotalDebitosCobradosConta
										+ " valor debito Cobrado (Atualizado) = " + valorParaAtualizar);

						arquivoConferencia.append(System.getProperty("line.separator"));

						Collection colecaoCategoriasObterValor = repositorioFaturamento.pesquisarContaCategoria(idConta);

						Collection colecaoCategorias = new ArrayList();
						Iterator it = colecaoCategoriasObterValor.iterator();
						while(it.hasNext()){
							ContaCategoria contaCategoria = (ContaCategoria) it.next();

							FiltroCategoria filtroCategoria = new FiltroCategoria();

							filtroCategoria.adicionarParametro(new ParametroSimples(FiltroCategoria.INDICADOR_USO,
											ConstantesSistema.INDICADOR_USO_ATIVO));

							filtroCategoria.adicionarParametro(new ParametroSimples(FiltroCategoria.CODIGO, contaCategoria.getComp_id()
											.getCategoria().getId()));

							Collection colecaoPesquisa = this.getControladorUtil().pesquisar(filtroCategoria, Categoria.class.getName());

							Categoria categoria = (Categoria) Util.retonarObjetoDeColecao(colecaoPesquisa);

							categoria.setQuantidadeEconomiasCategoria(contaCategoria.getQuantidadeEconomia().intValue());

							colecaoCategorias.add(categoria);
						}

						Collection colecaoDebitosCobradoCategoria = new ArrayList();

						// [UC0185] Obter Valor por Categoria
						Collection colecaoValorPorCategoria = getControladorImovel().obterValorPorCategoria(colecaoCategorias,
										valorParaAtualizar);

						// Cria a coleção de DebitoCobradoCategoria
						Iterator colecaoValorPorCategoriaIterator = colecaoValorPorCategoria.iterator();
						Iterator colecaoCategoriasObterValorIterator = colecaoCategorias.iterator();

						while(colecaoValorPorCategoriaIterator.hasNext() && colecaoCategoriasObterValorIterator.hasNext()){
							BigDecimal valorPorCategoria = (BigDecimal) colecaoValorPorCategoriaIterator.next();
							Categoria categoria = (Categoria) colecaoCategoriasObterValorIterator.next();
							DebitoCobradoCategoriaPK debitoCobradoCategoriaPK = new DebitoCobradoCategoriaPK();
							debitoCobradoCategoriaPK.setCategoria(categoria);
							debitoCobradoCategoriaPK.setDebitoCobrado(debitoCobrado);
							DebitoCobradoCategoria debitoCobradoCategoria = new DebitoCobradoCategoria();
							debitoCobradoCategoria.setComp_id(debitoCobradoCategoriaPK);
							debitoCobradoCategoria.setDebitoCobrado(debitoCobrado);
							debitoCobradoCategoria.setCategoria(categoria);
							debitoCobradoCategoria.setValorCategoria(valorPorCategoria);
							debitoCobradoCategoria.setUltimaAlteracao(new Date());
							colecaoDebitosCobradoCategoria.add(debitoCobradoCategoria);
						}
						this.getControladorUtil().atualizar(debitoCobrado);
						this.repositorioBatch.atualizarColecaoObjetoParaBatch(colecaoDebitosCobradoCategoria);
					}
				}

			}


			arquivoConferencia.append("________________________________________________________________________________________________");
			this.gerarArquivo(arquivoConferencia, nomeArquivo);

			log.info(" ******************* Fim dos ajustes em DEBITO_COBRADO ******************************** ");

		}

	}

	/**
	 * caso 2
	 * 
	 * @throws Exception
	 */
	public void executarAjusteValorDebitoCobradoHistoricoValorMenorCasal() throws Exception{

		// Inicializa as instacias dos repositórios usados
		this.ejbCreate();

		String nomeArquivo = "AjusteValorDebitoCobradoHistoricoValorMenorCasal.TXT";
		StringBuilder arquivoConferencia = new StringBuilder();


		arquivoConferencia.append("________________________________________________________________________________________________");

		// Obtém : [0] = cnta_id, [1] cnhi_vldebitos, [2] sum(dbhi_vlprestacao) , [3]
		// quantidadeDebitos

		Collection<Object[]> colecaoObjetos = repositorioFaturamento.pesquisarContasHistoricoAjusteCasal(">");

		log.info(" Quantidade : " + colecaoObjetos.size());
		Integer contator = 0;

		Integer idConta = null;
		BigDecimal valorDebitoConta = null;
		BigDecimal valorTotalDebitosCobradosConta = null;
		Integer quantidedeDebitosConta = null;

		if(colecaoObjetos != null && !colecaoObjetos.isEmpty()){

			Iterator iterator = colecaoObjetos.iterator();

			while(iterator.hasNext()){
				Object[] obj = (Object[]) iterator.next();

				contator = contator + 1;

				log.info(" Processados  : " + contator);

				idConta = (Integer) obj[0];

				valorDebitoConta = (BigDecimal) obj[1];

				valorTotalDebitosCobradosConta = (BigDecimal) obj[2];

				quantidedeDebitosConta = (Integer) obj[3];

				log.info(" contaHistorico: " + idConta + " valor debito conta historico = " + valorDebitoConta
								+ " valor total debito cobrado historico = " + valorTotalDebitosCobradosConta + " quantidade debitos = "
								+ quantidedeDebitosConta);

				if(valorDebitoConta.compareTo(valorTotalDebitosCobradosConta) > 0){
					BigDecimal diferenca = valorDebitoConta.subtract(valorTotalDebitosCobradosConta);
					log.info("diferença = " + diferenca);
					DebitoCobradoHistorico debitoCobradoHistorico = repositorioFaturamento
									.pesquisarMaiorIdDebitoCobradoHistoricoPorIdContaHistorico(idConta);
					BigDecimal valorParaAtualizar = debitoCobradoHistorico.getValorPrestacao().add(diferenca);

					if(debitoCobradoHistorico != null){
						debitoCobradoHistorico.setValorPrestacao(valorParaAtualizar);
						debitoCobradoHistorico.setUltimaAlteracao(new Date());
						log.info(" contaHistorico: " + idConta + " debitoCobradoHistorico: " + debitoCobradoHistorico.getId()
										+ " valor debito Cobrado Historico atualizado= " + valorParaAtualizar);

						arquivoConferencia.append(" contaHistorico: " + idConta + " debitoCobradoHistorico: "
										+ debitoCobradoHistorico.getId() + " valor Total Debitos cobrados Conta (Anterior) = "
										+ valorTotalDebitosCobradosConta + " valor debito Cobrado (Atualizado) = " + valorParaAtualizar);

						arquivoConferencia.append(System.getProperty("line.separator"));

						Collection colecaoCategoriasHistoricoObterValor = repositorioFaturamento.pesquisarContaCategoriaHistorico(idConta);

						Collection colecaoCategorias = new ArrayList();
						Iterator it = colecaoCategoriasHistoricoObterValor.iterator();
						while(it.hasNext()){
							ContaCategoriaHistorico contaCategoriaHistorico = (ContaCategoriaHistorico) it.next();

							FiltroCategoria filtroCategoria = new FiltroCategoria();

							filtroCategoria.adicionarParametro(new ParametroSimples(FiltroCategoria.INDICADOR_USO,
											ConstantesSistema.INDICADOR_USO_ATIVO));

							filtroCategoria.adicionarParametro(new ParametroSimples(FiltroCategoria.CODIGO, contaCategoriaHistorico
											.getComp_id().getCategoria().getId()));

							Collection colecaoPesquisa = this.getControladorUtil().pesquisar(filtroCategoria, Categoria.class.getName());

							Categoria categoria = (Categoria) Util.retonarObjetoDeColecao(colecaoPesquisa);

							Short quantidadeEconomia = contaCategoriaHistorico.getQuantidadeEconomia();

							categoria.setQuantidadeEconomiasCategoria(quantidadeEconomia.intValue());

							colecaoCategorias.add(categoria);
						}

						Collection colecaoDebitosCobradoCategoriaHistorico = new ArrayList();

						// [UC0185] Obter Valor por Categoria
						Collection colecaoValorPorCategoria = getControladorImovel().obterValorPorCategoria(colecaoCategorias,
										valorParaAtualizar);

						// Cria a coleção de DebitoCobradoCategoria
						Iterator colecaoValorPorCategoriaIterator = colecaoValorPorCategoria.iterator();
						Iterator colecaoCategoriasObterValorIterator = colecaoCategorias.iterator();

						while(colecaoValorPorCategoriaIterator.hasNext() && colecaoCategoriasObterValorIterator.hasNext()){
							BigDecimal valorPorCategoria = (BigDecimal) colecaoValorPorCategoriaIterator.next();

							Categoria categoria = (Categoria) colecaoCategoriasObterValorIterator.next();

							DebitoCobradoCategoriaHistoricoPK debitoCobradoCategoriaHistoricoPK = new DebitoCobradoCategoriaHistoricoPK();
							debitoCobradoCategoriaHistoricoPK.setCategoria(categoria);
							debitoCobradoCategoriaHistoricoPK.setDebitoCobradoHistorico(debitoCobradoHistorico);
							DebitoCobradoCategoriaHistorico debitoCobradoCategoriaHistorico = new DebitoCobradoCategoriaHistorico();
							debitoCobradoCategoriaHistorico.setComp_id(debitoCobradoCategoriaHistoricoPK);
							debitoCobradoCategoriaHistorico.setDebitoCobradoHistorico(debitoCobradoHistorico);
							debitoCobradoCategoriaHistorico.setCategoria(categoria);
							debitoCobradoCategoriaHistorico.setValorCategoria(valorPorCategoria);
							debitoCobradoCategoriaHistorico.setUltimaAlteracao(new Date());
							colecaoDebitosCobradoCategoriaHistorico.add(debitoCobradoCategoriaHistorico);
						}
						this.getControladorUtil().atualizar(debitoCobradoHistorico);
						
						this.repositorioBatch.atualizarColecaoObjetoParaBatch(colecaoDebitosCobradoCategoriaHistorico);
					}
				}

			}

			arquivoConferencia.append("________________________________________________________________________________________________");
			this.gerarArquivo(arquivoConferencia, nomeArquivo);

			log.info(" ******************* Fim dos ajustes em DEBITO_COBRADO_HISTORICO ******************************** ");

		}

	}

	/**
	 * caso 3
	 * 
	 * @throws Exception
	 */
	public void executarAjusteValorDebitoCobradoHistoricoValorMaiorCasal() throws Exception{

		// Inicializa as instacias dos repositórios usados
		this.ejbCreate();

		String nomeArquivo = "AjusteValorDebitoCobradoHistoricoValorMaiorCasal.TXT";
		StringBuilder arquivoConferencia = new StringBuilder();

		arquivoConferencia.append("________________________________________________________________________________________________");
		arquivoConferencia.append(System.getProperty("line.separator"));

		// Obtém : [0] = cnta_id, [1] cnhi_vldebitos, [2] sum(dbhi_vlprestacao) , [3]
		// quantidadeDebitos

		Collection<Object[]> colecaoObjetos = repositorioFaturamento.pesquisarContasHistoricoAjusteCasal("<");

		log.info(" Quantidade : " + colecaoObjetos.size());
		Integer contator = 0;

		Integer idConta = null;
		BigDecimal valorDebitoConta = null;
		BigDecimal valorTotalDebitosCobradosConta = null;
		Integer quantidedeDebitosConta = null;
		BigDecimal valorRestanteDiferenca = BigDecimal.ZERO;

		if(colecaoObjetos != null && !colecaoObjetos.isEmpty()){

			Iterator iterator = colecaoObjetos.iterator();

			while(iterator.hasNext()){

				Object[] obj = (Object[]) iterator.next();

				contator = contator + 1;

				log.info(" Processados  : " + contator);

				idConta = (Integer) obj[0];
				valorDebitoConta = (BigDecimal) obj[1];
				valorTotalDebitosCobradosConta = (BigDecimal) obj[2];
				quantidedeDebitosConta = (Integer) obj[3];
				valorRestanteDiferenca = BigDecimal.ZERO;

				log.info(" contaHistorico: " + idConta + " valor debito conta historico = " + valorDebitoConta
								+ " valor total debito cobrado historico = "
								+ valorTotalDebitosCobradosConta + " quantidade debitos = " + quantidedeDebitosConta);

				if(valorDebitoConta.compareTo(valorTotalDebitosCobradosConta) < 0){

					valorRestanteDiferenca = valorTotalDebitosCobradosConta.subtract(valorDebitoConta);
					log.info("diferença = " + valorRestanteDiferenca);
					Collection collDebitoCobradoHistorico = repositorioFaturamento
									.pesquisarDebitoCobradoHistoricoPorIdContaHistorico(idConta);

					BigDecimal valorParaAtualizar = BigDecimal.ZERO;

					if(collDebitoCobradoHistorico != null){

						Iterator it = collDebitoCobradoHistorico.iterator();
						while(it.hasNext()){
							
							DebitoCobradoHistorico debitoCobradoHistorico = (DebitoCobradoHistorico) it.next();

							if(debitoCobradoHistorico.getValorPrestacao().compareTo(valorRestanteDiferenca) > 0){

								valorParaAtualizar = debitoCobradoHistorico.getValorPrestacao().subtract(valorRestanteDiferenca);
								debitoCobradoHistorico.setValorPrestacao(valorParaAtualizar);
								debitoCobradoHistorico.setUltimaAlteracao(new Date());

								log.info(" contaHistorico: " + idConta + " debitoCobradoHistorico: " + debitoCobradoHistorico.getId()
												+ " valor debito Cobrado Historico atualizado= " + valorParaAtualizar);

								arquivoConferencia.append(" contaHistorico: " + idConta + " debitoCobradoHistorico: "
												+ debitoCobradoHistorico.getId() + " valor Total Debitos cobrados Conta (Anterior) = "
												+ valorTotalDebitosCobradosConta + " valor debito Cobrado (Atualizado) = "
												+ valorParaAtualizar);

								arquivoConferencia.append(System.getProperty("line.separator"));

								// ............................................................................................................

								Collection colecaoDebitosCobradoCategoriaHistorico = obterDebitoCobradoCategoriaHistorico(
												valorParaAtualizar, debitoCobradoHistorico);
								// ..............................................................................................................
								log.info("atualizar valor do debito ");

								getControladorUtil().atualizar(debitoCobradoHistorico);
								repositorioBatch.atualizarColecaoObjetoParaBatch(colecaoDebitosCobradoCategoriaHistorico);

								// Passa para próxima conta, pois já abateu toda diferença
								break;

							}else if(debitoCobradoHistorico.getValorPrestacao().compareTo(valorRestanteDiferenca) == 0){

								// remover o debito cobrado
								log.info("remove o debito ");
								repositorioFaturamento.removerDebitoCobradoCategoriaHistorico(debitoCobradoHistorico.getId());
								repositorioFaturamento.removerDebitoCobradoHistorico(debitoCobradoHistorico.getId());

								// Passa para próxima conta, pois já abateu toda diferença
								break;

							}else{

								valorRestanteDiferenca = valorRestanteDiferenca.subtract(debitoCobradoHistorico.getValorPrestacao());

								// remover o debito cobrado
								log.info("remove o debito ");
								repositorioFaturamento.removerDebitoCobradoCategoriaHistorico(debitoCobradoHistorico.getId());
								repositorioFaturamento.removerDebitoCobradoHistorico(debitoCobradoHistorico.getId());
								
								// Quando não houver mais restante da diferança a ser abatido passa
								// para próxima conta
								if(valorRestanteDiferenca.compareTo(BigDecimal.ZERO) < 1){

									break;
								}
							}
						}
					}
				}
			}

			arquivoConferencia.append("________________________________________________________________________________________________");
			this.gerarArquivo(arquivoConferencia, nomeArquivo);

			log.info(" ******************* Fim dos ajustes em DEBITO_COBRADO_HISTORICO ******************************** ");
		}
	}

	private void recalcularDifDebitoCobradoHistorico(Integer idConta, BigDecimal diferenca){

		try{

			Collection collDebitoCobradoHistorico = repositorioFaturamento.pesquisarDebitoCobradoHistoricoPorIdContaHistorico(idConta);

			BigDecimal resto = BigDecimal.ZERO;

			boolean temResto = false;
			Iterator it = collDebitoCobradoHistorico.iterator();
			while(it.hasNext()){

				DebitoCobradoHistorico debitoCobradoHistorico = (DebitoCobradoHistorico) it.next();

				if(temResto == false){
					// Se o valor do débito cobrado for menor que a diferenca
					if(debitoCobradoHistorico.getValorPrestacao().compareTo(diferenca) < 0){

						resto = diferenca.subtract(debitoCobradoHistorico.getValorPrestacao());

						if(resto.compareTo(BigDecimal.ZERO) > 0){
							temResto = true;
						}

					}
				}else{
					if(debitoCobradoHistorico.getValorPrestacao().compareTo(diferenca) < 0){
						resto = resto.subtract(debitoCobradoHistorico.getValorPrestacao());
					}

				}
			}

		}catch(ErroRepositorioException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * @param valorParaAtualizar
	 * @param debitoCobradoHistorico
	 * @return
	 */

	private Collection obterDebitoCobradoCategoriaHistorico(BigDecimal valorParaAtualizar, DebitoCobradoHistorico debitoCobradoHistorico){

		Collection colecaoDebitosCobradoCategoriaHistorico = new ArrayList();

		Collection colecaoCategoriasHistoricoObterValor;
		try{
			colecaoCategoriasHistoricoObterValor = repositorioFaturamento.pesquisarContaCategoriaHistorico(debitoCobradoHistorico
							.getContaHistorico().getId());
			Collection colecaoCategorias = new ArrayList();

			Iterator itt = colecaoCategoriasHistoricoObterValor.iterator();
			while(itt.hasNext()){
				ContaCategoriaHistorico contaCategoriaHistorico = (ContaCategoriaHistorico) itt.next();

				FiltroCategoria filtroCategoria = new FiltroCategoria();

				filtroCategoria.adicionarParametro(new ParametroSimples(FiltroCategoria.INDICADOR_USO,
								ConstantesSistema.INDICADOR_USO_ATIVO));

				filtroCategoria.adicionarParametro(new ParametroSimples(FiltroCategoria.CODIGO, contaCategoriaHistorico.getComp_id()
								.getCategoria().getId()));

				Collection colecaoPesquisa = this.getControladorUtil().pesquisar(filtroCategoria, Categoria.class.getName());

				Categoria categoria = (Categoria) Util.retonarObjetoDeColecao(colecaoPesquisa);

				Short quantidadeEconomia = contaCategoriaHistorico.getQuantidadeEconomia();

				categoria.setQuantidadeEconomiasCategoria(quantidadeEconomia.intValue());

				colecaoCategorias.add(categoria);
			}

			// [UC0185] Obter Valor por Categoria
			Collection colecaoValorPorCategoria = getControladorImovel().obterValorPorCategoria(colecaoCategorias, valorParaAtualizar);

			// Cria a coleção de DebitoCobradoCategoria
			Iterator colecaoValorPorCategoriaIterator = colecaoValorPorCategoria.iterator();
			Iterator colecaoCategoriasObterValorIterator = colecaoCategorias.iterator();

			while(colecaoValorPorCategoriaIterator.hasNext() && colecaoCategoriasObterValorIterator.hasNext()){
				BigDecimal valorPorCategoria = (BigDecimal) colecaoValorPorCategoriaIterator.next();
				Categoria categoria = (Categoria) colecaoCategoriasObterValorIterator.next();
				DebitoCobradoCategoriaHistoricoPK debitoCobradoCategoriaHistoricoPK = new DebitoCobradoCategoriaHistoricoPK();
				debitoCobradoCategoriaHistoricoPK.setCategoria(categoria);
				debitoCobradoCategoriaHistoricoPK.setDebitoCobradoHistorico(debitoCobradoHistorico);
				DebitoCobradoCategoriaHistorico debitoCobradoCategoriaHistorico = new DebitoCobradoCategoriaHistorico();
				debitoCobradoCategoriaHistorico.setComp_id(debitoCobradoCategoriaHistoricoPK);
				debitoCobradoCategoriaHistorico.setDebitoCobradoHistorico(debitoCobradoHistorico);
				debitoCobradoCategoriaHistorico.setCategoria(categoria);
				debitoCobradoCategoriaHistorico.setValorCategoria(valorPorCategoria);
				debitoCobradoCategoriaHistorico.setUltimaAlteracao(new Date());
				colecaoDebitosCobradoCategoriaHistorico.add(debitoCobradoCategoriaHistorico);
			}

		}catch(ErroRepositorioException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(ControladorException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return colecaoDebitosCobradoCategoriaHistorico;

		
	}


	/**
	 * Método que realiza o ajuste do faturamento de serviços da DESO para referência 201211
	 * 
	 * @author Anderson Italo
	 * @date 14/11/2012
	 */
	public void executarAjusteServicos(Integer idFaturamentoGrupo, Integer anoMesReferencia) throws IOException, Exception{

		System.out.println("======INÍCIO AJUSTE 1 FATURAMENTO GRUPO: " + idFaturamentoGrupo.toString());
		this.ejbCreate();
		FiltroRota filtroRota = new FiltroRota();
		filtroRota.adicionarParametro(new ParametroSimples(FiltroRota.FATURAMENTO_GRUPO_ID, idFaturamentoGrupo));
		filtroRota.setCampoOrderBy(FiltroRota.ID_ROTA);

		Collection<Rota> colecaoRota = getControladorUtil().pesquisar(filtroRota, Rota.class.getName());
		Collection<MovimentoRoteiroEmpresa> colecaoMovimentoRoteiroEmpresa = null;
		Collection<DebitoCobrado> colecaoDebitoCobradoAjustar = new ArrayList<DebitoCobrado>();
		Collection<DebitoCobrado> colecaoDebitoCobradoConta = null;
		Collection<OperacaoContabilHelper> colecaoOperacaoContabil = new ArrayList<OperacaoContabilHelper>();
		ContaMotivoRetificacao contaMotivoRetificacao = new ContaMotivoRetificacao();
		contaMotivoRetificacao.setId(36);
		Conta contaAtual = null;
		ContaHistorico contaAjustadaHistorico = null;
		Conta contaAjustada = null;
		Pagamento pagamento = null;
		BigDecimal somatorioDebitosGerados = BigDecimal.ZERO;
		StringBuilder arquivoConferencia = new StringBuilder();
		String nomeArquivo = "AjusteFaturamentoServicos_g" + Util.adicionarZerosEsquedaNumero(3, idFaturamentoGrupo.toString())
						+ Util.formatarAnoMesParaMesAnoSemBarra(anoMesReferencia) + ".TXT";
		arquivoConferencia.append("________________________________________________________________________________________________");
		arquivoConferencia.append(System.getProperty("line.separator"));
		arquivoConferencia.append("AJUSTE FATURAMENTO SERVIÇOS DO GRUPO " + idFaturamentoGrupo.toString());
		arquivoConferencia.append(System.getProperty("line.separator"));
		arquivoConferencia.append("________________________________________________________________________________________________");
		arquivoConferencia.append(System.getProperty("line.separator"));
		arquivoConferencia.append(System.getProperty("line.separator"));
		Integer quantidadeClassificada = 0;
		Integer quantidadeNaoClassificada = 0;
		Integer quantidadeContasNaoEncontradas = 0;
		Integer quantidadeRegistros = 0;
		Integer quantidadeContaSemPagamento = 0;
		String situacaoesContaPossiveis = null;
		Imovel imovel = null;

		// Processa o ajuste por rota
		for(Rota rota : colecaoRota){

			// Obtém os registros de movimento roteiro empresa que não tiveram serviços cobrados na
			// conta atual devido ao erro do faturamento
			colecaoMovimentoRoteiroEmpresa = repositorioFaturamento.pesquisarDadosMovimentoRoteiroEmpresaAjuste1(anoMesReferencia,
							rota.getId());

			quantidadeRegistros = quantidadeRegistros + colecaoMovimentoRoteiroEmpresa.size();
			System.out.println("======AJUSTE FATURAMENTO ROTA: " + rota.getId().toString());

			for(Iterator iterator = colecaoMovimentoRoteiroEmpresa.iterator(); iterator.hasNext();){

				MovimentoRoteiroEmpresa movimentoRoteiroEmpresa = (MovimentoRoteiroEmpresa) iterator.next();
				colecaoDebitoCobradoAjustar = new ArrayList<DebitoCobrado>();
				colecaoDebitoCobradoConta = null;
				contaAtual = null;
				contaAjustadaHistorico = null;
				contaAjustada = null;
				pagamento = null;

				// Imóvel Dados Antes
				arquivoConferencia.append(" Dados Imóvel Antes: "
								+ Util.completarStringZeroEsquerda(movimentoRoteiroEmpresa.getImovel().getId().toString(), 10));
				arquivoConferencia.append(System.getProperty("line.separator"));

				// Obtém a conta do imóvel
				situacaoesContaPossiveis = DebitoCreditoSituacao.NORMAL.toString();
				contaAtual = repositorioFaturamento.pesquisarContaDaReferenciaPorImovel(movimentoRoteiroEmpresa.getImovel().getId(),
								anoMesReferencia, situacaoesContaPossiveis);

				if(contaAtual == null){

					arquivoConferencia.append("#CONTA NÃO ENCONTRADA          ");
					quantidadeContasNaoEncontradas = quantidadeContasNaoEncontradas.intValue() + 1;
					arquivoConferencia.append(System.getProperty("line.separator"));
				}else{

					// Valores da Conta Antes
					arquivoConferencia.append(" Valor Água Antes: "
									+ Util.completarStringZeroEsquerda(contaAtual.getValorAgua().toString(), 12));
					arquivoConferencia.append(" Valor Esgoto Antes: "
									+ Util.completarStringZeroEsquerda(contaAtual.getValorEsgoto().toString(), 12));
					arquivoConferencia.append(" Valor Débito Antes: "
									+ Util.completarStringZeroEsquerda(contaAtual.getDebitos().toString(), 12));
					arquivoConferencia.append(" Valor Imposto Antes: "
									+ Util.completarStringZeroEsquerda(contaAtual.getValorImposto().toString(), 12));
					arquivoConferencia.append(" Valor Total Conta Antes: "
									+ Util.completarStringZeroEsquerda(contaAtual.getValorTotalConta(), 12));
					arquivoConferencia.append(System.getProperty("line.separator"));

					// Carrega na conta os dados necessários para retificação
					contaAtual = getControladorFaturamento().pesquisarContaRetificacao(contaAtual.getId());
					imovel = contaAtual.getImovel();

					// Para cada grupo de três tipos de débitos que não foram cobrados
					if(movimentoRoteiroEmpresa.getDescricaoRubrica1() != null && movimentoRoteiroEmpresa.getDescricaoRubrica3() == null){

						obterDebitosParaAjuste(anoMesReferencia, colecaoDebitoCobradoAjustar, contaAtual, movimentoRoteiroEmpresa,
										movimentoRoteiroEmpresa.getDescricaoRubrica1(), movimentoRoteiroEmpresa.getDescricaoRubrica2(),
										movimentoRoteiroEmpresa.getNumeroPrestacaoRubrica1(),
										movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica1(),
										movimentoRoteiroEmpresa.getNumeroPrestacaoRubrica2(),
										movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica2());
					}else if(movimentoRoteiroEmpresa.getDescricaoRubrica4() != null
									&& movimentoRoteiroEmpresa.getDescricaoRubrica6() == null){

						obterDebitosParaAjuste(anoMesReferencia, colecaoDebitoCobradoAjustar, contaAtual, movimentoRoteiroEmpresa,
										movimentoRoteiroEmpresa.getDescricaoRubrica4(), movimentoRoteiroEmpresa.getDescricaoRubrica5(),
										movimentoRoteiroEmpresa.getNumeroPrestacaoRubrica4(),
										movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica4(),
										movimentoRoteiroEmpresa.getNumeroPrestacaoRubrica5(),
										movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica5());
					}else if(movimentoRoteiroEmpresa.getDescricaoRubrica7() != null
									&& movimentoRoteiroEmpresa.getDescricaoRubrica9() == null){

						obterDebitosParaAjuste(anoMesReferencia, colecaoDebitoCobradoAjustar, contaAtual, movimentoRoteiroEmpresa,
										movimentoRoteiroEmpresa.getDescricaoRubrica7(), movimentoRoteiroEmpresa.getDescricaoRubrica8(),
										movimentoRoteiroEmpresa.getNumeroPrestacaoRubrica7(),
										movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica7(),
										movimentoRoteiroEmpresa.getNumeroPrestacaoRubrica8(),
										movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica8());
					}else if(movimentoRoteiroEmpresa.getDescricaoRubrica10() != null
									&& movimentoRoteiroEmpresa.getDescricaoRubrica12() == null){

						obterDebitosParaAjuste(anoMesReferencia, colecaoDebitoCobradoAjustar, contaAtual, movimentoRoteiroEmpresa,
										movimentoRoteiroEmpresa.getDescricaoRubrica10(), movimentoRoteiroEmpresa.getDescricaoRubrica11(),
										movimentoRoteiroEmpresa.getNumeroPrestacaoRubrica10(),
										movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica10(),
										movimentoRoteiroEmpresa.getNumeroPrestacaoRubrica11(),
										movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica11());
					}else if(movimentoRoteiroEmpresa.getDescricaoRubrica13() != null
									&& movimentoRoteiroEmpresa.getDescricaoRubrica15() == null){

						obterDebitosParaAjuste(anoMesReferencia, colecaoDebitoCobradoAjustar, contaAtual, movimentoRoteiroEmpresa,
										movimentoRoteiroEmpresa.getDescricaoRubrica13(), movimentoRoteiroEmpresa.getDescricaoRubrica14(),
										movimentoRoteiroEmpresa.getNumeroPrestacaoRubrica13(),
										movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica13(),
										movimentoRoteiroEmpresa.getNumeroPrestacaoRubrica14(),
										movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica14());
					}

					// Obtém todos os débitos cobrados da conta na referência atual para remover os
					// débitos ajustados
					colecaoDebitoCobradoConta = repositorioFaturamento.pesquisarDebitosCobradoDaConta(contaAtual.getId(), null, null, null);

					if(!Util.isVazioOrNulo(colecaoDebitoCobradoConta)){

						colecaoDebitoCobradoConta.removeAll(colecaoDebitoCobradoAjustar);
					}

					// Obtendo os créditos realizados da conta
					Collection colecaoCreditoRealizado = getControladorFaturamento().obterCreditosRealizadosConta(contaAtual);

					// Chama a rotina de retificação da conta
					Integer idContaRetificada = getControladorFaturamento().retificarContaAjusteFaturamento(anoMesReferencia, contaAtual,
									contaAtual.getImovel(), colecaoDebitoCobradoConta, contaAtual.getLigacaoAguaSituacao(),
									contaAtual.getLigacaoEsgotoSituacao(), contaAtual.getConsumoAgua().toString(),
									contaAtual.getConsumoEsgoto().toString(), contaAtual.getPercentualEsgoto().toString(),
									contaAtual.getDataVencimentoConta(), contaMotivoRetificacao, Usuario.USUARIO_BATCH,
									contaAtual.getConsumoTarifa(), colecaoCreditoRealizado);

					// Obtém a nova conta ajustada
					situacaoesContaPossiveis = DebitoCreditoSituacao.RETIFICADA.toString();
					contaAjustada = (Conta) getControladorUtil().pesquisar(idContaRetificada, Conta.class, false);

					if(contaAjustada == null){

						contaAjustadaHistorico = (ContaHistorico) getControladorUtil().pesquisar(idContaRetificada, ContaHistorico.class,
										false);
					}

					// Imóvel Dados Depois
					arquivoConferencia.append(" Dados Imóvel Depois: "
									+ Util.completarStringZeroEsquerda(movimentoRoteiroEmpresa.getImovel().getId().toString(), 10));

					if(contaAjustada != null){

						// Valores da Conta Depois
						arquivoConferencia.append(" Valor Água Depois: "
										+ Util.completarStringZeroEsquerda(contaAjustada.getValorAgua().toString(), 12));
						arquivoConferencia.append(" Valor Esgoto Depois: "
										+ Util.completarStringZeroEsquerda(contaAjustada.getValorEsgoto().toString(), 12));
						arquivoConferencia.append(" Valor Débito Depois: "
										+ Util.completarStringZeroEsquerda(contaAjustada.getDebitos().toString(), 12));
						arquivoConferencia.append(" Valor Imposto Depois: "
										+ Util.completarStringZeroEsquerda(contaAjustada.getValorImposto().toString(), 12));
						arquivoConferencia.append(" Valor Total Conta Depois: "
										+ Util.completarStringZeroEsquerda(contaAjustada.getValorTotalConta(), 12));

						pagamento = repositorioFaturamento.pesquisarPagamentoConta(contaAjustada.getId(), anoMesReferencia);

						if(pagamento != null){

							arquivoConferencia.append("#PAGAMENTO NÃO CLASSIFICADO");
							quantidadeNaoClassificada = quantidadeNaoClassificada.intValue() + 1;
						}else{

							arquivoConferencia.append("#CONTA SEM PAGAMENTO     ");
							quantidadeContaSemPagamento = quantidadeContaSemPagamento.intValue() + 1;
						}

						arquivoConferencia.append(System.getProperty("line.separator"));

					}else{

						// Valores da Conta Depois
						arquivoConferencia.append(" Valor Água Depois: "
										+ Util.completarStringZeroEsquerda(contaAjustadaHistorico.getValorAgua().toString(), 12));
						arquivoConferencia.append(" Valor Esgoto Depois: "
										+ Util.completarStringZeroEsquerda(contaAjustadaHistorico.getValorEsgoto().toString(), 12));
						arquivoConferencia.append(" Valor Débito Depois: "
										+ Util.completarStringZeroEsquerda(contaAjustadaHistorico.getValorDebitos().toString(), 12));
						arquivoConferencia.append(" Valor Imposto Depois: "
										+ Util.completarStringZeroEsquerda(contaAjustadaHistorico.getValorImposto().toString(), 12));
						arquivoConferencia.append(" Valor Total Conta Depois: "
										+ Util.completarStringZeroEsquerda(contaAjustadaHistorico.getValorTotal().toString(), 12));

						arquivoConferencia.append(" #PAGAMENTO     CLASSIFICADO");
						quantidadeClassificada = quantidadeClassificada.intValue() + 1;
						arquivoConferencia.append(System.getProperty("line.separator"));
					}

					for(DebitoCobrado debitoCobrado : colecaoDebitoCobradoAjustar){

						/*
						 * Para cada imóvel, gerar débito a cobrar do valor do débito não cobrado na
						 * referência atual
						 */
						DebitoTipo debitoTipo = debitoCobrado.getDebitoTipo();
						CobrancaForma cobrancaForma = new CobrancaForma();
						cobrancaForma.setId(CobrancaForma.COBRANCA_EM_CONTA);

						DebitoACobrar debitoACobrar = new DebitoACobrar();
						debitoACobrar.setDebitoTipo(debitoTipo);
						debitoACobrar.setValorDebito(debitoCobrado.getValorPrestacao());
						debitoACobrar.setImovel(imovel);
						debitoACobrar.setGeracaoDebito(new Date());
						debitoACobrar.setLocalidade(contaAtual.getLocalidade());
						debitoACobrar.setQuadra(contaAtual.getQuadraConta());
						debitoACobrar.setAnoMesReferenciaDebito(anoMesReferencia);
						debitoACobrar.setLancamentoItemContabil(debitoTipo.getLancamentoItemContabil());
						debitoACobrar.setFinanciamentoTipo(debitoTipo.getFinanciamentoTipo());
						debitoACobrar.setCobrancaForma(cobrancaForma);
						debitoACobrar.setUltimaAlteracao(new Date());
						debitoACobrar.setAnoMesCobrancaDebito(Util.somaMesAnoMesReferencia(anoMesReferencia, 1));

						// Valor da Diferênça Lançado para o Imóvel
						arquivoConferencia.append(" Valor Prestação Débito: "
										+ Util.completarStringZeroEsquerda(debitoACobrar.getValorDebito().toString(), 12) + "#");
						somatorioDebitosGerados = somatorioDebitosGerados.add(debitoACobrar.getValorDebito());

						OperacaoContabilHelper helper = new OperacaoContabilHelper();
						helper.setObjetoOrigem(this.getControladorFaturamento().inserirDebitoACobrarSemRegistrarLancamentoContabil(1,
										debitoACobrar, null, imovel, null, null, Usuario.USUARIO_BATCH, false, null, null, null));
						helper.setOperacaoContabil(OperacaoContabil.INCLUIR_DEBITO_A_COBRAR);

						colecaoOperacaoContabil.add(helper);
					}

					arquivoConferencia.append(System.getProperty("line.separator"));
				}
			}

			System.out.println("======TOTAL IMÓVEIS AJUSTE FATURAMENTO DA ROTA: " + colecaoMovimentoRoteiroEmpresa.size());

			if(!Util.isVazioOrNulo(colecaoOperacaoContabil)){

				// Registra o lançamento contábil dos débitos a cobrar gerados
				System.out.println("====AJUSTE FATURAMENTO REGISTRAR O LANÇAMENTO CONTÁBIL DÉBITOS A COBRAR ROTA :"
								+ rota.getId().toString());
				this.getControladorContabil().registrarLancamentoContabil(colecaoOperacaoContabil);
			}
		}

		arquivoConferencia.append("________________________________________________________________________________________________");
		arquivoConferencia.append(System.getProperty("line.separator"));
		arquivoConferencia.append("QTD de Imóveis: " + quantidadeRegistros);
		arquivoConferencia.append(System.getProperty("line.separator"));
		arquivoConferencia.append("Valor Total Débitos Gerados: " + somatorioDebitosGerados.toString());
		arquivoConferencia.append(System.getProperty("line.separator"));
		arquivoConferencia.append("QTD Contas Não Encontradas: " + quantidadeContasNaoEncontradas.toString());
		arquivoConferencia.append(System.getProperty("line.separator"));
		arquivoConferencia.append("QTD de Contas Classificadas: " + quantidadeClassificada.toString());
		arquivoConferencia.append(System.getProperty("line.separator"));
		arquivoConferencia.append("QTD de Contas Não Classificadas: " + quantidadeNaoClassificada.toString().toString());
		arquivoConferencia.append(System.getProperty("line.separator"));
		arquivoConferencia.append("QTD de Contas Sem Pagamento: " + quantidadeContaSemPagamento.toString().toString());

		// Gerar relatório arquivo texto
		gerarArquivo(arquivoConferencia, nomeArquivo);
		System.out.println("======FIM AJUSTE  1 FATURAMENTO GRUPO: " + idFaturamentoGrupo.toString());
	}

	private void obterDebitosParaAjuste(Integer anoMesReferencia, Collection<DebitoCobrado> colecaoDebitoCobradoAjustar, Conta conta,
					MovimentoRoteiroEmpresa movimentoRoteiroEmpresa, String primeiraRubrica, String segundaRubrica,
					Short numeroPrestacaoPrimeiraRubrica, Short numeroPrestacaoDebitoPrimeiraRubrica, Short numeroPrestacaoSegundaRubrica,
					Short numeroPrestacaoDebitoSegundaRubrica) throws ErroRepositorioException, ControladorException{

		Collection<DebitoCobrado> colecaoDebitoCobrado = null;
		Collection<DebitoACobrar> colecaoDebitoACobrar = null;

		// Obtém os débitos cobrados para gerar um débito a cobrar a ser cobrado na
		// próxima conta
		if(primeiraRubrica != null){

			colecaoDebitoCobrado = repositorioFaturamento.pesquisarDebitosCobradoDaConta(conta.getId(), Util.obterInteger(primeiraRubrica
							.substring(0, 3)), numeroPrestacaoPrimeiraRubrica, numeroPrestacaoDebitoPrimeiraRubrica);
			colecaoDebitoCobradoAjustar.addAll(colecaoDebitoCobrado);
		}

		if(segundaRubrica != null){

			colecaoDebitoCobrado = repositorioFaturamento.pesquisarDebitosCobradoDaConta(conta.getId(), Util.obterInteger(segundaRubrica
							.substring(0, 3)), numeroPrestacaoSegundaRubrica, numeroPrestacaoDebitoSegundaRubrica);
			colecaoDebitoCobradoAjustar.addAll(colecaoDebitoCobrado);
		}

		for(DebitoCobrado debitoCobrado : colecaoDebitoCobradoAjustar){

			// Para os débitos cobrados que tiverem mais de uma parcela postergar a
			// próxima parcela de cobrança do debito a cobrar relativo ao esse tipo para o mês de
			// janeiro de 2013
			if(debitoCobrado.getNumeroPrestacao() > 1){

				colecaoDebitoACobrar = repositorioFaturamento.pesquisarDebitosACobrarDoImovel(movimentoRoteiroEmpresa.getImovel().getId(),
								debitoCobrado.getAnoMesCobrancaDebito(), debitoCobrado.getDebitoTipo().getId(), debitoCobrado
												.getNumeroPrestacao());

				if(!Util.isVazioOrNulo(colecaoDebitoACobrar)){

					for(DebitoACobrar debitoACobrar : colecaoDebitoACobrar){

						debitoACobrar.setAnoMesCobrancaDebito(201301);
						debitoACobrar.setUltimaAlteracao(new Date());
						getControladorUtil().atualizar(debitoACobrar);
					}
				}
			}
		}
	}

	private void gerarArquivo(StringBuilder sb, String nomeArquivo) throws IOException, Exception{

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
		compactado.delete();
	}

	/**
	 * Método que realiza o ajuste do faturamento de serviços da DESO para referência 201211
	 * 
	 * @author Anderson Italo
	 * @date 14/11/2012
	 */
	public void executarAjusteServicosComValorTruncado(Integer idFaturamentoGrupo, Integer anoMesReferencia) throws IOException, Exception{

		System.out.println("======INÍCIO AJUSTE 2 FATURAMENTO GRUPO: " + idFaturamentoGrupo.toString());
		this.ejbCreate();
		FiltroRota filtroRota = new FiltroRota();
		filtroRota.adicionarParametro(new ParametroSimples(FiltroRota.FATURAMENTO_GRUPO_ID, idFaturamentoGrupo));
		filtroRota.setCampoOrderBy(FiltroRota.ID_ROTA);

		Collection<Rota> colecaoRota = getControladorUtil().pesquisar(filtroRota, Rota.class.getName());
		Collection<MovimentoRoteiroEmpresa> colecaoMovimentoRoteiroEmpresa = null;
		Collection<DebitoCobrado> colecaoDebitoCobradoConta = null;
		Collection<OperacaoContabilHelper> colecaoOperacaoContabil = new ArrayList<OperacaoContabilHelper>();
		ContaMotivoRetificacao contaMotivoRetificacao = new ContaMotivoRetificacao();
		contaMotivoRetificacao.setId(36);
		Conta contaAtual = null;
		ContaHistorico contaAjustadaHistorico = null;
		Conta contaAjustada = null;
		Pagamento pagamento = null;
		BigDecimal somatorioDebitosGerados = BigDecimal.ZERO;
		StringBuilder arquivoConferencia = new StringBuilder();
		String nomeArquivo = "AjusteFatServicosComValorTruncadoNOVO_g" + Util.adicionarZerosEsquedaNumero(3, idFaturamentoGrupo.toString())
						+ Util.formatarAnoMesParaMesAnoSemBarra(anoMesReferencia) + ".TXT";
		arquivoConferencia.append("________________________________________________________________________________________________");
		arquivoConferencia.append(System.getProperty("line.separator"));
		arquivoConferencia.append("AJUSTE FATURAMENTO SERVIÇOS COM VALOR TRUNCADO DO GRUPO " + idFaturamentoGrupo.toString());
		arquivoConferencia.append(System.getProperty("line.separator"));
		arquivoConferencia.append("________________________________________________________________________________________________");
		arquivoConferencia.append(System.getProperty("line.separator"));
		arquivoConferencia.append(System.getProperty("line.separator"));
		Integer quantidadeClassificada = 0;
		Integer quantidadeNaoClassificada = 0;
		Integer quantidadeContasNaoEncontradas = 0;
		Integer quantidadeContasEncontradas = 0;
		Integer quantidadeRegistros = 0;
		Integer quantidadeContaSemPagamento = 0;
		String situacaoesContaPossiveis = null;
		Imovel imovel = null;
		HashMap<Integer, DebitoCobrado> mapDebitosCobradosAjustados = new HashMap<Integer, DebitoCobrado>();

		// Processa o ajuste por rota
		for(Rota rota : colecaoRota){

			// Obtém os registros de movimento roteiro empresa que não tiveram serviços cobrados na
			// conta atual devido ao erro do faturamento
			colecaoMovimentoRoteiroEmpresa = repositorioFaturamento.pesquisarDadosMovimentoRoteiroEmpresaAjuste2(anoMesReferencia, rota
							.getId());
			System.out.println("======AJUSTE FATURAMENTO ROTA: " + rota.getId().toString());

			for(Iterator iterator = colecaoMovimentoRoteiroEmpresa.iterator(); iterator.hasNext();){

				MovimentoRoteiroEmpresa movimentoRoteiroEmpresa = (MovimentoRoteiroEmpresa) iterator.next();
				colecaoDebitoCobradoConta = null;
				contaAtual = null;
				contaAjustadaHistorico = null;
				contaAjustada = null;
				pagamento = null;
				mapDebitosCobradosAjustados = new HashMap<Integer, DebitoCobrado>();

				// Obtém a conta do imóvel
				situacaoesContaPossiveis = DebitoCreditoSituacao.NORMAL.toString();
				contaAtual = repositorioFaturamento.pesquisarContaDaReferenciaPorImovel(movimentoRoteiroEmpresa.getImovel().getId(),
								anoMesReferencia, situacaoesContaPossiveis);
				quantidadeRegistros = quantidadeRegistros.intValue() + 1;

				if(contaAtual != null){

					// Carrega na conta os dados necessários para retificação
					contaAtual = getControladorFaturamento().pesquisarContaRetificacao(contaAtual.getId());
					imovel = contaAtual.getImovel();
					quantidadeContasEncontradas = quantidadeContasEncontradas.intValue() + 1;

					// Imóvel Dados Antes
					arquivoConferencia.append(" Dados Imóvel Antes: "
									+ Util.completarStringZeroEsquerda(movimentoRoteiroEmpresa.getImovel().getId().toString(), 10));
					arquivoConferencia.append(System.getProperty("line.separator"));

					// Valores da Conta Antes
					arquivoConferencia.append(" Valor Água Antes: "
									+ Util.completarStringZeroEsquerda(contaAtual.getValorAgua().toString(), 12));
					arquivoConferencia.append(" Valor Esgoto Antes: "
									+ Util.completarStringZeroEsquerda(contaAtual.getValorEsgoto().toString(), 12));
					arquivoConferencia.append(" Valor Débito Antes: "
									+ Util.completarStringZeroEsquerda(contaAtual.getDebitos().toString(), 12));
					arquivoConferencia.append(" Valor Imposto Antes: "
									+ Util.completarStringZeroEsquerda(contaAtual.getValorImposto().toString(), 12));
					arquivoConferencia.append(" Valor Total Conta Antes: "
									+ Util.completarStringZeroEsquerda(contaAtual.getValorTotalConta(), 12));
					arquivoConferencia.append(System.getProperty("line.separator"));

					// Para cada coluna de rubrica verifica se truncou o valor e obtém os débitos
					if(movimentoRoteiroEmpresa.getDescricaoRubrica1() != null){

						somatorioDebitosGerados = somatorioDebitosGerados.add(obterDebitosParaAjuste2(anoMesReferencia,
										mapDebitosCobradosAjustados, contaAtual, movimentoRoteiroEmpresa, movimentoRoteiroEmpresa
														.getDescricaoRubrica1(), movimentoRoteiroEmpresa.getNumeroPrestacaoRubrica1(),
										movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica1(), movimentoRoteiroEmpresa
														.getValorRubrica1(), imovel, arquivoConferencia, colecaoOperacaoContabil));
					}

					if(movimentoRoteiroEmpresa.getDescricaoRubrica2() != null){

						somatorioDebitosGerados = somatorioDebitosGerados.add(obterDebitosParaAjuste2(anoMesReferencia,
										mapDebitosCobradosAjustados, contaAtual, movimentoRoteiroEmpresa, movimentoRoteiroEmpresa
														.getDescricaoRubrica2(), movimentoRoteiroEmpresa.getNumeroPrestacaoRubrica2(),
										movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica2(), movimentoRoteiroEmpresa
														.getValorRubrica2(), imovel, arquivoConferencia, colecaoOperacaoContabil));
					}

					if(movimentoRoteiroEmpresa.getDescricaoRubrica3() != null){

						somatorioDebitosGerados = somatorioDebitosGerados.add(obterDebitosParaAjuste2(anoMesReferencia,
										mapDebitosCobradosAjustados, contaAtual, movimentoRoteiroEmpresa, movimentoRoteiroEmpresa
														.getDescricaoRubrica3(), movimentoRoteiroEmpresa.getNumeroPrestacaoRubrica3(),
										movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica3(), movimentoRoteiroEmpresa
														.getValorRubrica3(), imovel, arquivoConferencia, colecaoOperacaoContabil));
					}

					if(movimentoRoteiroEmpresa.getDescricaoRubrica4() != null){

						somatorioDebitosGerados = somatorioDebitosGerados.add(obterDebitosParaAjuste2(anoMesReferencia,
										mapDebitosCobradosAjustados, contaAtual, movimentoRoteiroEmpresa, movimentoRoteiroEmpresa
														.getDescricaoRubrica4(), movimentoRoteiroEmpresa.getNumeroPrestacaoRubrica4(),
										movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica4(), movimentoRoteiroEmpresa
														.getValorRubrica4(), imovel, arquivoConferencia, colecaoOperacaoContabil));
					}

					if(movimentoRoteiroEmpresa.getDescricaoRubrica5() != null){

						somatorioDebitosGerados = somatorioDebitosGerados.add(obterDebitosParaAjuste2(anoMesReferencia,
										mapDebitosCobradosAjustados, contaAtual, movimentoRoteiroEmpresa, movimentoRoteiroEmpresa
														.getDescricaoRubrica5(), movimentoRoteiroEmpresa.getNumeroPrestacaoRubrica5(),
										movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica5(), movimentoRoteiroEmpresa
														.getValorRubrica5(), imovel, arquivoConferencia, colecaoOperacaoContabil));
					}

					if(movimentoRoteiroEmpresa.getDescricaoRubrica6() != null){

						somatorioDebitosGerados = somatorioDebitosGerados.add(obterDebitosParaAjuste2(anoMesReferencia,
										mapDebitosCobradosAjustados, contaAtual, movimentoRoteiroEmpresa, movimentoRoteiroEmpresa
														.getDescricaoRubrica6(), movimentoRoteiroEmpresa.getNumeroPrestacaoRubrica6(),
										movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica6(), movimentoRoteiroEmpresa
														.getValorRubrica6(), imovel, arquivoConferencia, colecaoOperacaoContabil));
					}

					if(movimentoRoteiroEmpresa.getDescricaoRubrica7() != null){

						somatorioDebitosGerados = somatorioDebitosGerados.add(obterDebitosParaAjuste2(anoMesReferencia,
										mapDebitosCobradosAjustados, contaAtual, movimentoRoteiroEmpresa, movimentoRoteiroEmpresa
														.getDescricaoRubrica7(), movimentoRoteiroEmpresa.getNumeroPrestacaoRubrica7(),
										movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica7(), movimentoRoteiroEmpresa
														.getValorRubrica7(), imovel, arquivoConferencia, colecaoOperacaoContabil));
					}

					if(movimentoRoteiroEmpresa.getDescricaoRubrica8() != null){

						somatorioDebitosGerados = somatorioDebitosGerados.add(obterDebitosParaAjuste2(anoMesReferencia,
										mapDebitosCobradosAjustados, contaAtual, movimentoRoteiroEmpresa, movimentoRoteiroEmpresa
														.getDescricaoRubrica8(), movimentoRoteiroEmpresa.getNumeroPrestacaoRubrica8(),
										movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica8(), movimentoRoteiroEmpresa
														.getValorRubrica8(), imovel, arquivoConferencia, colecaoOperacaoContabil));
					}

					if(movimentoRoteiroEmpresa.getDescricaoRubrica9() != null){

						somatorioDebitosGerados = somatorioDebitosGerados.add(obterDebitosParaAjuste2(anoMesReferencia,
										mapDebitosCobradosAjustados, contaAtual, movimentoRoteiroEmpresa, movimentoRoteiroEmpresa
														.getDescricaoRubrica9(), movimentoRoteiroEmpresa.getNumeroPrestacaoRubrica9(),
										movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica9(), movimentoRoteiroEmpresa
														.getValorRubrica9(), imovel, arquivoConferencia, colecaoOperacaoContabil));
					}

					if(movimentoRoteiroEmpresa.getDescricaoRubrica10() != null){

						somatorioDebitosGerados = somatorioDebitosGerados.add(obterDebitosParaAjuste2(anoMesReferencia,
										mapDebitosCobradosAjustados, contaAtual, movimentoRoteiroEmpresa, movimentoRoteiroEmpresa
														.getDescricaoRubrica10(), movimentoRoteiroEmpresa.getNumeroPrestacaoRubrica10(),
										movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica10(), movimentoRoteiroEmpresa
														.getValorRubrica10(), imovel, arquivoConferencia, colecaoOperacaoContabil));
					}

					if(movimentoRoteiroEmpresa.getDescricaoRubrica11() != null){

						somatorioDebitosGerados = somatorioDebitosGerados.add(obterDebitosParaAjuste2(anoMesReferencia,
										mapDebitosCobradosAjustados, contaAtual, movimentoRoteiroEmpresa, movimentoRoteiroEmpresa
														.getDescricaoRubrica11(), movimentoRoteiroEmpresa.getNumeroPrestacaoRubrica11(),
										movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica11(), movimentoRoteiroEmpresa
														.getValorRubrica11(), imovel, arquivoConferencia, colecaoOperacaoContabil));
					}

					if(movimentoRoteiroEmpresa.getDescricaoRubrica12() != null){

						somatorioDebitosGerados = somatorioDebitosGerados.add(obterDebitosParaAjuste2(anoMesReferencia,
										mapDebitosCobradosAjustados, contaAtual, movimentoRoteiroEmpresa, movimentoRoteiroEmpresa
														.getDescricaoRubrica12(), movimentoRoteiroEmpresa.getNumeroPrestacaoRubrica12(),
										movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica12(), movimentoRoteiroEmpresa
														.getValorRubrica12(), imovel, arquivoConferencia, colecaoOperacaoContabil));
					}

					if(movimentoRoteiroEmpresa.getDescricaoRubrica13() != null){

						somatorioDebitosGerados = somatorioDebitosGerados.add(obterDebitosParaAjuste2(anoMesReferencia,
										mapDebitosCobradosAjustados, contaAtual, movimentoRoteiroEmpresa, movimentoRoteiroEmpresa
														.getDescricaoRubrica13(), movimentoRoteiroEmpresa.getNumeroPrestacaoRubrica13(),
										movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica13(), movimentoRoteiroEmpresa
														.getValorRubrica13(), imovel, arquivoConferencia, colecaoOperacaoContabil));
					}

					if(movimentoRoteiroEmpresa.getDescricaoRubrica14() != null){

						somatorioDebitosGerados = somatorioDebitosGerados.add(obterDebitosParaAjuste2(anoMesReferencia,
										mapDebitosCobradosAjustados, contaAtual, movimentoRoteiroEmpresa, movimentoRoteiroEmpresa
														.getDescricaoRubrica14(), movimentoRoteiroEmpresa.getNumeroPrestacaoRubrica14(),
										movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica14(), movimentoRoteiroEmpresa
														.getValorRubrica14(), imovel, arquivoConferencia, colecaoOperacaoContabil));
					}

					if(movimentoRoteiroEmpresa.getDescricaoRubrica15() != null){

						somatorioDebitosGerados = somatorioDebitosGerados.add(obterDebitosParaAjuste2(anoMesReferencia,
										mapDebitosCobradosAjustados, contaAtual, movimentoRoteiroEmpresa, movimentoRoteiroEmpresa
														.getDescricaoRubrica15(), movimentoRoteiroEmpresa.getNumeroPrestacaoRubrica15(),
										movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica15(), movimentoRoteiroEmpresa
														.getValorRubrica15(), imovel, arquivoConferencia, colecaoOperacaoContabil));
					}

					// Caso tenha débitos a serem ajustados
					if(!mapDebitosCobradosAjustados.isEmpty()){

						// Obtém todos os débitos cobrados da conta na referência atual para
						// atualizar o valor do débito ajustado
						colecaoDebitoCobradoConta = repositorioFaturamento.pesquisarDebitosCobradoDaConta(contaAtual.getId(), null, null,
										null);

						if(!Util.isVazioOrNulo(colecaoDebitoCobradoConta)){

							DebitoCobrado debitoCobradoAux = null;
							for(DebitoCobrado debitoCobrado : colecaoDebitoCobradoConta){

								debitoCobradoAux = mapDebitosCobradosAjustados.get(debitoCobrado.getId());

								if(debitoCobradoAux != null){

									debitoCobrado.setValorPrestacao(debitoCobradoAux.getValorPrestacao());
									break;
								}
							}
						}

						// Obtendo os créditos realizados da conta
						Collection colecaoCreditoRealizado = getControladorFaturamento().obterCreditosRealizadosConta(contaAtual);

						// Chama a rotina de retificação da conta
						Integer idContaRetificada = getControladorFaturamento().retificarContaAjusteFaturamento(anoMesReferencia,
										contaAtual, contaAtual.getImovel(), colecaoDebitoCobradoConta, contaAtual.getLigacaoAguaSituacao(),
										contaAtual.getLigacaoEsgotoSituacao(), contaAtual.getConsumoAgua().toString(),
										contaAtual.getConsumoEsgoto().toString(), contaAtual.getPercentualEsgoto().toString(),
										contaAtual.getDataVencimentoConta(), contaMotivoRetificacao, Usuario.USUARIO_BATCH,
										contaAtual.getConsumoTarifa(), colecaoCreditoRealizado);

						// Obtém a nova conta ajustada
						situacaoesContaPossiveis = DebitoCreditoSituacao.RETIFICADA.toString();
						contaAjustada = (Conta) getControladorUtil().pesquisar(idContaRetificada, Conta.class, false);

						if(contaAjustada == null){

							contaAjustadaHistorico = (ContaHistorico) getControladorUtil().pesquisar(idContaRetificada,
											ContaHistorico.class, false);
						}

						// Imóvel Dados Depois
						arquivoConferencia.append(" Dados Imóvel Depois: "
										+ Util.completarStringZeroEsquerda(movimentoRoteiroEmpresa.getImovel().getId().toString(), 10));

						if(contaAjustada != null){

							// Valores da Conta Depois
							arquivoConferencia.append(" Valor Água Depois: "
											+ Util.completarStringZeroEsquerda(contaAjustada.getValorAgua().toString(), 12));
							arquivoConferencia.append(" Valor Esgoto Depois: "
											+ Util.completarStringZeroEsquerda(contaAjustada.getValorEsgoto().toString(), 12));
							arquivoConferencia.append(" Valor Débito Depois: "
											+ Util.completarStringZeroEsquerda(contaAjustada.getDebitos().toString(), 12));
							arquivoConferencia.append(" Valor Imposto Depois: "
											+ Util.completarStringZeroEsquerda(contaAjustada.getValorImposto().toString(), 12));
							arquivoConferencia.append(" Valor Total Conta Depois: "
											+ Util.completarStringZeroEsquerda(contaAjustada.getValorTotalConta(), 12));

							pagamento = repositorioFaturamento.pesquisarPagamentoConta(contaAjustada.getId(), anoMesReferencia);

							if(pagamento != null){

								arquivoConferencia.append("#PAGAMENTO NÃO CLASSIFICADO");
								quantidadeNaoClassificada = quantidadeNaoClassificada.intValue() + 1;
							}else{

								arquivoConferencia.append("#CONTA SEM PAGAMENTO     ");
								quantidadeContaSemPagamento = quantidadeContaSemPagamento.intValue() + 1;
							}

							arquivoConferencia.append(System.getProperty("line.separator"));

						}else{

							// Valores da Conta Depois
							arquivoConferencia.append(" Valor Água Depois: "
											+ Util.completarStringZeroEsquerda(contaAjustadaHistorico.getValorAgua().toString(), 12));
							arquivoConferencia.append(" Valor Esgoto Depois: "
											+ Util.completarStringZeroEsquerda(contaAjustadaHistorico.getValorEsgoto().toString(), 12));
							arquivoConferencia.append(" Valor Débito Depois: "
											+ Util.completarStringZeroEsquerda(contaAjustadaHistorico.getValorDebitos().toString(), 12));
							arquivoConferencia.append(" Valor Imposto Depois: "
											+ Util.completarStringZeroEsquerda(contaAjustadaHistorico.getValorImposto().toString(), 12));
							arquivoConferencia.append(" Valor Total Conta Depois: "
											+ Util.completarStringZeroEsquerda(contaAjustadaHistorico.getValorTotal().toString(), 12));

							arquivoConferencia.append(" #PAGAMENTO     CLASSIFICADO");
							quantidadeClassificada = quantidadeClassificada.intValue() + 1;
							arquivoConferencia.append(System.getProperty("line.separator"));
						}
					}
				}else{

					arquivoConferencia.append("Imóvel sem conta: " + movimentoRoteiroEmpresa.getImovel().getId().toString() + "#########");
					quantidadeContasNaoEncontradas = quantidadeContasNaoEncontradas.intValue() + 1;
					arquivoConferencia.append(System.getProperty("line.separator"));
				}
			}

			System.out.println("======TOTAL IMÓVEIS AJUSTE FATURAMENTO DA ROTA: " + quantidadeRegistros.toString());

			if(!Util.isVazioOrNulo(colecaoOperacaoContabil)){

				// Registra o lançamento contábil dos débitos a cobrar gerados
				System.out.println("====AJUSTE FATURAMENTO REGISTRAR O LANÇAMENTO CONTÁBIL DÉBITOS A COBRAR ROTA :"
								+ rota.getId().toString());
				this.getControladorContabil().registrarLancamentoContabil(colecaoOperacaoContabil);
			}
		}

		arquivoConferencia.append("________________________________________________________________________________________________");
		arquivoConferencia.append(System.getProperty("line.separator"));
		arquivoConferencia.append("QTD de Imóveis: " + quantidadeRegistros);
		arquivoConferencia.append(System.getProperty("line.separator"));
		arquivoConferencia.append("Valor Total Débitos Gerados: " + somatorioDebitosGerados.toString());
		arquivoConferencia.append(System.getProperty("line.separator"));
		arquivoConferencia.append("QTD Contas Encontradas: " + quantidadeContasEncontradas.toString());
		arquivoConferencia.append(System.getProperty("line.separator"));
		arquivoConferencia.append("QTD Contas Não Encontradas: " + quantidadeContasNaoEncontradas.toString());
		arquivoConferencia.append(System.getProperty("line.separator"));
		arquivoConferencia.append("QTD de Contas Classificadas: " + quantidadeClassificada.toString());
		arquivoConferencia.append(System.getProperty("line.separator"));
		arquivoConferencia.append("QTD de Contas Não Classificadas: " + quantidadeNaoClassificada.toString().toString());
		arquivoConferencia.append(System.getProperty("line.separator"));
		arquivoConferencia.append("QTD de Contas Sem Pagamento: " + quantidadeContaSemPagamento.toString().toString());

		// Gerar relatório arquivo texto
		gerarArquivo(arquivoConferencia, nomeArquivo);
		System.out.println("======FIM AJUSTE 2 FATURAMENTO GRUPO: " + idFaturamentoGrupo.toString());
	}

	private BigDecimal obterDebitosParaAjuste2(Integer anoMesReferencia, HashMap<Integer, DebitoCobrado> mapDebitosCobradosAjustados,
					Conta conta, MovimentoRoteiroEmpresa movimentoRoteiroEmpresa, String rubrica, Short numeroPrestacaoRubrica,
					Short numeroPrestacaoDebitoRubrica, BigDecimal valorRubrica, Imovel imovel, StringBuilder arquivoConferencia,
					Collection<OperacaoContabilHelper> colecaoOperacaoContabil) throws ErroRepositorioException, ControladorException{

		Collection<DebitoCobrado> colecaoDebitoCobrado = new ArrayList<DebitoCobrado>();
		Collection<DebitoACobrar> colecaoDebitoACobrar = null;
		BigDecimal valorDebitoGerado = BigDecimal.ZERO;

		// Verifica se truncou o valor do débito
		int indicePonto = valorRubrica.toString().indexOf(".");
		BigDecimal valorDiferenca = BigDecimal.ZERO;
		BigDecimal valorCobrado = BigDecimal.ZERO;

		// Obtém o valor do débito cobrado com erro
		if(valorRubrica.toString().contains(".") && valorRubrica.toString().substring(indicePonto + 1).length() == 1){

			valorCobrado = new BigDecimal(valorRubrica.toString()).divide(new BigDecimal("10"));
			colecaoDebitoCobrado.addAll(repositorioFaturamento.pesquisarDebitosCobradoDaConta(conta.getId(), Util.obterInteger(rubrica
							.substring(0, 3)), numeroPrestacaoRubrica, numeroPrestacaoDebitoRubrica));
		}

		boolean primeiraVez = true;
		for(DebitoCobrado debitoCobrado : colecaoDebitoCobrado){

			if(primeiraVez){

				valorDiferenca = debitoCobrado.getValorPrestacao().subtract(valorCobrado);
				debitoCobrado.setValorPrestacao(valorCobrado);
				mapDebitosCobradosAjustados.put(debitoCobrado.getId(), debitoCobrado);
				primeiraVez = false;

				/*
				 * Para cada imóvel, gerar débito a cobrar do valor do débito não
				 * cobrado na referência atual
				 */
				DebitoTipo debitoTipo = debitoCobrado.getDebitoTipo();
				CobrancaForma cobrancaForma = new CobrancaForma();
				cobrancaForma.setId(CobrancaForma.COBRANCA_EM_CONTA);

				DebitoACobrar debitoACobrar = new DebitoACobrar();
				debitoACobrar.setDebitoTipo(debitoTipo);
				debitoACobrar.setValorDebito(valorDiferenca);
				debitoACobrar.setImovel(imovel);
				debitoACobrar.setGeracaoDebito(new Date());
				debitoACobrar.setLocalidade(conta.getLocalidade());
				debitoACobrar.setQuadra(conta.getQuadraConta());
				debitoACobrar.setAnoMesReferenciaDebito(anoMesReferencia);
				debitoACobrar.setLancamentoItemContabil(debitoTipo.getLancamentoItemContabil());
				debitoACobrar.setFinanciamentoTipo(debitoTipo.getFinanciamentoTipo());
				debitoACobrar.setCobrancaForma(cobrancaForma);
				debitoACobrar.setUltimaAlteracao(new Date());
				debitoACobrar.setAnoMesCobrancaDebito(Util.somaMesAnoMesReferencia(anoMesReferencia, 1));

				// Valor da Diferênça Lançado para o Imóvel
				arquivoConferencia.append(" Valor Prestação Débito: "
								+ Util.completarStringZeroEsquerda(debitoACobrar.getValorDebito().toString(), 12) + "#");
				valorDebitoGerado = debitoACobrar.getValorDebito();

				OperacaoContabilHelper helper = new OperacaoContabilHelper();
				helper.setObjetoOrigem(this.getControladorFaturamento().inserirDebitoACobrarSemRegistrarLancamentoContabil(1,
								debitoACobrar, null, imovel, null, null, Usuario.USUARIO_BATCH, false, null, null, null));
				helper.setOperacaoContabil(OperacaoContabil.INCLUIR_DEBITO_A_COBRAR);

				colecaoOperacaoContabil.add(helper);
			}

			// Para os débitos cobrados que tiverem mais de uma parcela postergar a
			// próxima parcela de cobrança do debito a cobrar relativo ao esse tipo para o mês de
			// janeiro de 2013
			if(debitoCobrado.getNumeroPrestacao() > 1){

				colecaoDebitoACobrar = repositorioFaturamento.pesquisarDebitosACobrarDoImovel(movimentoRoteiroEmpresa.getImovel().getId(),
								debitoCobrado.getAnoMesCobrancaDebito(), debitoCobrado.getDebitoTipo().getId(), debitoCobrado
												.getNumeroPrestacao());

				if(!Util.isVazioOrNulo(colecaoDebitoACobrar)){

					for(DebitoACobrar debitoACobrar : colecaoDebitoACobrar){

						debitoACobrar.setAnoMesCobrancaDebito(201301);
						debitoACobrar.setUltimaAlteracao(new Date());
						getControladorUtil().atualizar(debitoACobrar);
					}
				}
			}
		}

		return valorDebitoGerado;
	}

	/**
	 * Método que realiza o ajuste do faturamento de tarifas da DESO para referência 201211
	 * 
	 * @author Yara Souza
	 * @throws CreateException
	 * @date 15/11/2012
	 */
	public void executarAjusteTarifas(Integer idFaturamentoGrupo, Integer anoMesReferencia) throws CreateException{

		this.ejbCreate();

		Collection<OperacaoContabilHelper> colecaoOperacaoContabil = new ArrayList<OperacaoContabilHelper>();

		FiltroRota filtroRota = new FiltroRota();
		filtroRota.adicionarParametro(new ParametroSimples(FiltroRota.FATURAMENTO_GRUPO_ID, idFaturamentoGrupo));
		filtroRota.setCampoOrderBy(FiltroRota.ID_ROTA);

		Integer quantidadeContasGrupo = 0;

		Collection<Rota> colecaoRota;
		try{
			colecaoRota = getControladorUtil().pesquisar(filtroRota, Rota.class.getName());

			Collection<Conta> colecaoConta = null;
			Collection<ContaHistorico> colecaoContaHistorico = null;

			Collection<DebitoACobrar> colecaoDebitoACobrarAtualizar = new ArrayList<DebitoACobrar>();

			StringBuilder arquivoConferencia = new StringBuilder();
			String nomeArquivo = "g" + Util.adicionarZerosEsquedaNumero(3, idFaturamentoGrupo.toString())
							+ Util.formatarAnoMesParaMesAnoSemBarra(anoMesReferencia) + "TARIFA" + ".TXT";

			arquivoConferencia.append("__________________________________________________________________________________");
			arquivoConferencia.append(System.getProperty("line.separator"));
			arquivoConferencia.append("GRUPO -  " + idFaturamentoGrupo);
			arquivoConferencia.append(System.getProperty("line.separator"));
			arquivoConferencia.append("__________________________________________________________________________________");
			arquivoConferencia.append(System.getProperty("line.separator"));

			Integer quantidade = 0;

			BigDecimal somatorioValorTotalAgua = BigDecimal.ZERO;
			BigDecimal somatorioValorTotalEsgoto = BigDecimal.ZERO;

			BigDecimal somatorioValorTotalAguaCalculado = BigDecimal.ZERO;
			BigDecimal somatorioValorTotalEsgotoCalculado = BigDecimal.ZERO;

			BigDecimal somatorioValorTotalAguaDiferenca = BigDecimal.ZERO;
			BigDecimal somatorioValorTotalEsgotoDiferenca = BigDecimal.ZERO;

			// Processa o ajuste por rota
			for(Rota rota : colecaoRota){

				// Obtém os registros de movimento roteiro empresa que não tiveram serviços cobrados
				// na
				// conta atual devido ao erro do faturamento
				colecaoConta = repositorioFaturamento.pesquisarContaDaReferenciaPorRota(rota.getId(), 201211);

				quantidadeContasGrupo = quantidadeContasGrupo + colecaoConta.size();

				for(Iterator iterator = colecaoConta.iterator(); iterator.hasNext();){

					Conta conta = (Conta) iterator.next();

					EmitirContaHelper emitirContaHelper = repositorioFaturamento.pesquisarContaHelper(conta.getId());
					Object[] retorno = this.executarAjusteTarifaConta(conta, emitirContaHelper, arquivoConferencia, idFaturamentoGrupo,
									somatorioValorTotalAgua, somatorioValorTotalEsgoto, somatorioValorTotalAguaCalculado,
									somatorioValorTotalEsgotoCalculado, somatorioValorTotalAguaDiferenca,
									somatorioValorTotalEsgotoDiferenca);

					if(retorno[2].equals(true)){
						arquivoConferencia = (StringBuilder) retorno[0];
						arquivoConferencia.append(System.getProperty("line.separator"));
						Collection<OperacaoContabilHelper> colecaoOperacaoContabilRetorno = (Collection<OperacaoContabilHelper>) retorno[1];
						colecaoOperacaoContabil.addAll(colecaoOperacaoContabilRetorno);
						quantidade++;

						somatorioValorTotalAgua = somatorioValorTotalAgua.add((BigDecimal) retorno[3]);
						somatorioValorTotalEsgoto = somatorioValorTotalEsgoto.add((BigDecimal) retorno[4]);

						somatorioValorTotalAguaCalculado = somatorioValorTotalAguaCalculado.add((BigDecimal) retorno[5]);
						somatorioValorTotalEsgotoCalculado = somatorioValorTotalEsgotoCalculado.add((BigDecimal) retorno[6]);

						somatorioValorTotalAguaDiferenca = somatorioValorTotalAguaDiferenca.add((BigDecimal) retorno[7]);
						somatorioValorTotalEsgotoDiferenca = somatorioValorTotalEsgotoDiferenca.add((BigDecimal) retorno[8]);

					}

				}

				colecaoContaHistorico = repositorioFaturamento.pesquisarContaHistoricoDaReferenciaPorRota(rota.getId(), 201211);

				quantidadeContasGrupo = quantidadeContasGrupo + colecaoContaHistorico.size();

				for(Iterator iterator = colecaoContaHistorico.iterator(); iterator.hasNext();){

					ContaHistorico contaHistorico = (ContaHistorico) iterator.next();

					EmitirContaHelper emitirContaHelper = repositorioFaturamento.pesquisarContaHistoricoHelper(contaHistorico.getId());
					Object[] retorno = this.executarAjusteTarifaContaHistorico(contaHistorico, emitirContaHelper, arquivoConferencia,
									idFaturamentoGrupo, somatorioValorTotalAgua, somatorioValorTotalEsgoto,
									somatorioValorTotalAguaCalculado, somatorioValorTotalEsgotoCalculado, somatorioValorTotalAguaDiferenca,
									somatorioValorTotalEsgotoDiferenca);

					if(retorno[2].equals(true)){

						arquivoConferencia = (StringBuilder) retorno[0];
						arquivoConferencia.append(System.getProperty("line.separator"));
						Collection<OperacaoContabilHelper> colecaoOperacaoContabilRetorno = (Collection<OperacaoContabilHelper>) retorno[1];
						colecaoOperacaoContabil.addAll(colecaoOperacaoContabilRetorno);

						quantidade++;

						somatorioValorTotalAgua = somatorioValorTotalAgua.add((BigDecimal) retorno[3]);
						somatorioValorTotalEsgoto = somatorioValorTotalEsgoto.add((BigDecimal) retorno[4]);

						somatorioValorTotalAguaCalculado = somatorioValorTotalAguaCalculado.add((BigDecimal) retorno[5]);
						somatorioValorTotalEsgotoCalculado = somatorioValorTotalEsgotoCalculado.add((BigDecimal) retorno[6]);

						somatorioValorTotalAguaDiferenca = somatorioValorTotalAguaDiferenca.add((BigDecimal) retorno[7]);
						somatorioValorTotalEsgotoDiferenca = somatorioValorTotalEsgotoDiferenca.add((BigDecimal) retorno[8]);
					}

				}

				if(colecaoOperacaoContabil != null && !colecaoOperacaoContabil.isEmpty()){
					// Registra o lançamento contábil dos débitos a cobrar gerados
					this.getControladorContabil().registrarLancamentoContabil(colecaoOperacaoContabil);
				}

				if(colecaoDebitoACobrarAtualizar != null && !colecaoDebitoACobrarAtualizar.isEmpty()){
					// Atuliza os débitos a cobrar com mais de uma prestação para referência 201301
					getControladorBatch().atualizarColecaoObjetoParaBatch((Collection) colecaoDebitoACobrarAtualizar);
				}

			}

			arquivoConferencia.append(System.getProperty("line.separator"));
			arquivoConferencia.append("________________________________________________________________________________________________");
			arquivoConferencia.append(System.getProperty("line.separator"));
			arquivoConferencia.append("TOTAL CONTAS PROCESSADAS = " + quantidade + " / ");
			arquivoConferencia.append("TOTAL CONTAS DO GRUPO = " + quantidadeContasGrupo);
			arquivoConferencia.append(System.getProperty("line.separator"));
			arquivoConferencia.append("________________________________________________________________________________________________");
			arquivoConferencia.append(System.getProperty("line.separator"));
			arquivoConferencia.append("Somatorio Valor Total Agua 				 = " + somatorioValorTotalAgua + " / ");
			arquivoConferencia.append("Somatorio Valor Total Agua Recalculado 	 = " + somatorioValorTotalAguaCalculado + " / ");
			arquivoConferencia.append("Somatorio Valor Total Agua Diferenca  	 = " + somatorioValorTotalAguaDiferenca);
			arquivoConferencia.append(System.getProperty("line.separator"));
			arquivoConferencia.append("Somatorio Valor Total Esgoto  			 = " + somatorioValorTotalEsgoto + " / ");
			arquivoConferencia.append("Somatorio Valor Total Esgoto Recalculado  = " + somatorioValorTotalEsgotoCalculado + " / ");
			arquivoConferencia.append("Somatorio Valor Total Esgoto Diferenca  	 = " + somatorioValorTotalEsgotoDiferenca);
			arquivoConferencia.append(System.getProperty("line.separator"));

			arquivoConferencia.append("________________________________________________________________________________________________");

			if(arquivoConferencia != null && arquivoConferencia.length() > 0){
				gerarArquivo(arquivoConferencia, nomeArquivo);
			}

			System.out.println("_________________________________FIM do GRUPO = " + idFaturamentoGrupo + "___________________");

		}catch(ControladorException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(ErroRepositorioException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(Exception e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private Object[] executarAjusteTarifaConta(Conta conta, EmitirContaHelper emitirContaHelper, StringBuilder arquivoConferencia,
					Integer idFaturamentoGrupo, BigDecimal somatorioValorTotalAgua, BigDecimal somatorioValorTotalEsgoto,
					BigDecimal somatorioValorTotalAguaCalculado, BigDecimal somatorioValorTotalEsgotoCalculado,
					BigDecimal somatorioValorTotalAguaDiferenca, BigDecimal somatorioValorTotalEsgotoDiferenca) throws ControladorException{

		Object[] retorno = new Object[9];
		boolean gerouLinha = false;

		Collection<OperacaoContabilHelper> colecaoOperacaoContabil = new ArrayList<OperacaoContabilHelper>();

		Date dataLeituraAnterior = null;
		Date dataLeituraAtual = null;
		BigDecimal valorTotalAgua = BigDecimal.ZERO;
		BigDecimal valorTotalEsgoto = BigDecimal.ZERO;

		BigDecimal valorAguaConta = BigDecimal.ZERO;
		BigDecimal valorAguaRecalculado = BigDecimal.ZERO;

		BigDecimal valorEsgotoConta = BigDecimal.ZERO;
		BigDecimal valorEsgotoRecalculado = BigDecimal.ZERO;

		BigDecimal valorDiferencaAgua = BigDecimal.ZERO;
		BigDecimal valorDiferencaEsgoto = BigDecimal.ZERO;

		Collection collDebitoCobradoBase = this.getControladorFaturamento().obterDebitosCobradosConta(conta);

		if(Util.isVazioOrNulo(collDebitoCobradoBase)){
			dataLeituraAtual = this.getControladorFaturamento().buscarDataLeituraCronograma(idFaturamentoGrupo, false,
							conta.getReferencia());

			Collection<CalcularValoresAguaEsgotoHelper> colecaoValoresCalculadosAguaEsgoto;
			try{

				colecaoValoresCalculadosAguaEsgoto = this.getControladorFaturamento().obterCalcularValoresAguaEsgotoParaAjuste(
								emitirContaHelper, conta.getImovel(), dataLeituraAnterior, dataLeituraAtual);

				Iterator it2 = colecaoValoresCalculadosAguaEsgoto.iterator();

				while(it2.hasNext()){
					CalcularValoresAguaEsgotoHelper calcularValoresAguaEsgotoHelper = (CalcularValoresAguaEsgotoHelper) it2.next();

					/*
					 * Caso tenha valor de água faturado para categoria adiciona o valor de água ao
					 * valor total de água. Caso contrário soma zero.
					 */
					if(calcularValoresAguaEsgotoHelper.getValorFaturadoAguaCategoria() != null){
						valorTotalAgua = valorTotalAgua.add(calcularValoresAguaEsgotoHelper.getValorFaturadoAguaCategoria());
					}else{
						valorTotalAgua = valorTotalAgua.add(new BigDecimal("0.00"));
					}

					if(calcularValoresAguaEsgotoHelper.getValorFaturadoEsgotoCategoria() != null){
						valorTotalEsgoto = valorTotalEsgoto.add(calcularValoresAguaEsgotoHelper.getValorFaturadoEsgotoCategoria());
					}else{
						valorTotalEsgoto = valorTotalEsgoto.add(new BigDecimal("0.00"));
					}

				}

				if(valorTotalAgua.compareTo(conta.getValorAgua()) != 0 && valorTotalAgua.compareTo(conta.getValorAgua()) > 0){

					valorAguaConta = conta.getValorAgua();
					valorAguaRecalculado = valorTotalAgua;

					valorDiferencaAgua = valorAguaRecalculado.subtract(valorAguaConta);

					OperacaoContabilHelper operacaoContabilHelper = this.gerarDebitoACobrar(916, conta.getLocalidade(), conta
									.getQuadraConta(), conta.getImovel(), conta.getReferencia(), valorDiferencaAgua);

					if(operacaoContabilHelper != null){
						gerouLinha = true;

						colecaoOperacaoContabil.add(operacaoContabilHelper);

						// Valor da Diferênça Lançado para o Imóvel
						arquivoConferencia.append("imóvel: " + conta.getImovel().getId() + " valorAguaConta: " + valorAguaConta
										+ " valorAguaRecalculado: " + valorAguaRecalculado + " valorDiferenca: " + valorDiferencaAgua);

						somatorioValorTotalAgua = valorAguaConta;
						somatorioValorTotalAguaCalculado = valorAguaRecalculado;
						somatorioValorTotalAguaDiferenca = valorDiferencaAgua;

					}

				}

				if(valorTotalEsgoto.compareTo(conta.getValorEsgoto()) != 0 && valorTotalEsgoto.compareTo(conta.getValorEsgoto()) > 0){

					valorEsgotoConta = conta.getValorEsgoto();
					valorEsgotoRecalculado = valorTotalEsgoto;

					valorDiferencaEsgoto = valorEsgotoRecalculado.subtract(valorEsgotoConta);

					OperacaoContabilHelper operacaoContabilHelper = this.gerarDebitoACobrar(917, conta.getLocalidade(), conta
									.getQuadraConta(), conta.getImovel(), conta.getReferencia(), valorDiferencaEsgoto);

					if(operacaoContabilHelper != null){
						gerouLinha = true;

						colecaoOperacaoContabil.add(operacaoContabilHelper);

						// Valor da Diferênça Lançado para o Imóvel
						arquivoConferencia
										.append("imóvel: " + conta.getImovel().getId() + " valorEsgotoConta: " + valorEsgotoConta
														+ " valorEsgotoRecalculado: " + valorEsgotoRecalculado + " valorDiferenca: "
														+ valorDiferencaEsgoto);

						somatorioValorTotalEsgoto = valorEsgotoConta;
						somatorioValorTotalEsgotoCalculado = valorEsgotoRecalculado;
						somatorioValorTotalEsgotoDiferenca = valorDiferencaEsgoto;

					}

				}

			}catch(ErroRepositorioException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		retorno[0] = arquivoConferencia;
		retorno[1] = colecaoOperacaoContabil;
		retorno[2] = gerouLinha;

		retorno[3] = somatorioValorTotalAgua;
		retorno[4] = somatorioValorTotalEsgoto;
		retorno[5] = somatorioValorTotalAguaCalculado;
		retorno[6] = somatorioValorTotalEsgotoCalculado;
		retorno[7] = somatorioValorTotalAguaDiferenca;
		retorno[8] = somatorioValorTotalEsgotoDiferenca;

		return retorno;

	}

	public OperacaoContabilHelper gerarDebitoACobrar(Integer idDebitoTipo, Localidade localidade, Quadra quadra, Imovel imovel,
					Integer referencia, BigDecimal diferenca) throws ControladorException{

		boolean gerarDebito = true;
		OperacaoContabilHelper helper = null;
		/*
		 * Para cada imóvel, gerar débito a cobrar do valor do débito não cobrado na
		 * referência atual
		 */

		Collection referenciaColecao = new ArrayList();
		referenciaColecao.add(201211);
		referenciaColecao.add(201212);
		referenciaColecao.add(201301);

		CobrancaForma cobrancaForma = new CobrancaForma();
		cobrancaForma.setId(CobrancaForma.COBRANCA_EM_CONTA);

		FiltroDebitoTipo filtroDebitoTipo = new FiltroDebitoTipo();
		filtroDebitoTipo.adicionarParametro(new ParametroSimples(FiltroDebitoTipo.ID, idDebitoTipo));
		filtroDebitoTipo.adicionarCaminhoParaCarregamentoEntidade(FiltroDebitoTipo.FINANCIAMENTO_TIPO);
		filtroDebitoTipo.adicionarCaminhoParaCarregamentoEntidade(FiltroDebitoTipo.LANCAMENTO_ITEM_CONTABIL);
		filtroDebitoTipo.adicionarParametro(new ParametroSimples(FiltroDebitoTipo.INDICADOR_USO, ConstantesSistema.SIM));
		Collection<DebitoTipo> colecaoDebitoTipo = Fachada.getInstancia().pesquisar(filtroDebitoTipo, DebitoTipo.class.getName());
		DebitoTipo debitoTipo = (DebitoTipo) Util.retonarObjetoDeColecao(colecaoDebitoTipo);

		if(debitoTipo != null){

			Collection<DebitoACobrar> collDebitoACobrarBase = this.getControladorFaturamento().pesquisarDebitosACobrarDoImovel(
							imovel.getId(), referenciaColecao, idDebitoTipo);

			if(!Util.isVazioOrNulo(collDebitoACobrarBase)){
				gerarDebito = false;
			}else{

				FiltroConta filtroConta = new FiltroConta();
				filtroConta.adicionarParametro(new ParametroSimplesColecao(FiltroConta.REFERENCIA, referenciaColecao));
				filtroConta.adicionarParametro(new ParametroSimples(FiltroConta.IMOVEL_ID, imovel.getId()));
				Collection contas = this.getControladorUtil().pesquisar(filtroConta, Conta.class.getName());

				if(!Util.isVazioOrNulo(contas)){

					Iterator it = contas.iterator();
					while(it.hasNext()){

						Conta conta = (Conta) it.next();
						FiltroDebitoCobrado filtroDebitoCobrado = new FiltroDebitoCobrado();
						filtroDebitoCobrado.adicionarParametro(new ParametroSimples(FiltroDebitoCobrado.CONTA_ID, conta.getId()));
						filtroDebitoCobrado.adicionarParametro(new ParametroSimples(FiltroDebitoCobrado.DEBITO_TIPO, idDebitoTipo));
						Collection debitosCobrados = this.getControladorUtil()
										.pesquisar(filtroDebitoCobrado, DebitoCobrado.class.getName());

						if(!Util.isVazioOrNulo(debitosCobrados)){
							gerarDebito = false;
							break;
						}
					}

				}else{
					FiltroContaHistorico filtroContaHistorico = new FiltroContaHistorico();
					filtroContaHistorico.adicionarParametro(new ParametroSimplesColecao(FiltroContaHistorico.ANO_MES_REFERENCIA,
									referenciaColecao));
					filtroContaHistorico.adicionarParametro(new ParametroSimples(FiltroContaHistorico.IMOVEL_ID, imovel.getId()));
					Collection contasHistorico = this.getControladorUtil().pesquisar(filtroContaHistorico, ContaHistorico.class.getName());
					if(!Util.isVazioOrNulo(contasHistorico)){
						Iterator it = contasHistorico.iterator();
						while(it.hasNext()){

							ContaHistorico contaHistorico = (ContaHistorico) it.next();
							FiltroDebitoCobrado filtroDebitoCobrado = new FiltroDebitoCobrado();
							filtroDebitoCobrado.adicionarParametro(new ParametroSimples(FiltroDebitoCobrado.CONTA_ID, contaHistorico
											.getId()));
							filtroDebitoCobrado.adicionarParametro(new ParametroSimples(FiltroDebitoCobrado.DEBITO_TIPO, idDebitoTipo));
							Collection debitosCobrados = this.getControladorUtil().pesquisar(filtroDebitoCobrado,
											DebitoCobrado.class.getName());

							if(!Util.isVazioOrNulo(debitosCobrados)){
								gerarDebito = false;
								break;
							}
						}

					}
				}

			}

			if(gerarDebito){
				DebitoACobrar debitoACobrar = new DebitoACobrar();
				debitoACobrar.setDebitoTipo(debitoTipo);
				debitoACobrar.setValorDebito(diferenca);
				debitoACobrar.setImovel(imovel);
				debitoACobrar.setGeracaoDebito(new Date());
				debitoACobrar.setLocalidade(localidade);
				debitoACobrar.setQuadra(quadra);
				debitoACobrar.setAnoMesReferenciaDebito(referencia);
				debitoACobrar.setLancamentoItemContabil(debitoTipo.getLancamentoItemContabil());
				debitoACobrar.setFinanciamentoTipo(debitoTipo.getFinanciamentoTipo());
				debitoACobrar.setCobrancaForma(cobrancaForma);
				debitoACobrar.setUltimaAlteracao(new Date());
				debitoACobrar.setAnoMesCobrancaDebito(201301);
				debitoACobrar.setUltimaAlteracao(new Date());

				helper = new OperacaoContabilHelper();
				helper.setObjetoOrigem(this.getControladorFaturamento().inserirDebitoACobrarSemRegistrarLancamentoContabil(1,
								debitoACobrar, null, imovel, null, null, Usuario.USUARIO_BATCH, false, null, null, null));
				helper.setOperacaoContabil(OperacaoContabil.INCLUIR_DEBITO_A_COBRAR);

				System.out.println("---------> gerarDebito =" + gerarDebito + "   idDebitoTipo= " + idDebitoTipo + " imovel ="
								+ imovel.getId() + "diferença = " + diferenca);
			}

		}

		return helper;

	}

	private Object[] executarAjusteTarifaContaHistorico(ContaHistorico contaHistorico, EmitirContaHelper emitirContaHelper,
					StringBuilder arquivoConferencia, Integer idFaturamentoGrupo, BigDecimal somatorioValorTotalAgua,
					BigDecimal somatorioValorTotalEsgoto, BigDecimal somatorioValorTotalAguaCalculado,
					BigDecimal somatorioValorTotalEsgotoCalculado, BigDecimal somatorioValorTotalAguaDiferenca,
					BigDecimal somatorioValorTotalEsgotoDiferenca) throws ControladorException, ErroRepositorioException{

		Object[] retorno = new Object[9];
		boolean gerouLinha = false;

		Collection<OperacaoContabilHelper> colecaoOperacaoContabil = new ArrayList<OperacaoContabilHelper>();

		Date dataLeituraAnterior = null;
		Date dataLeituraAtual = null;
		BigDecimal valorTotalAgua = BigDecimal.ZERO;
		BigDecimal valorTotalEsgoto = BigDecimal.ZERO;

		BigDecimal valorAguaConta = BigDecimal.ZERO;
		BigDecimal valorAguaRecalculado = BigDecimal.ZERO;

		BigDecimal valorEsgotoConta = BigDecimal.ZERO;
		BigDecimal valorEsgotoRecalculado = BigDecimal.ZERO;

		BigDecimal valorDiferencaAgua = BigDecimal.ZERO;
		BigDecimal valorDiferencaEsgoto = BigDecimal.ZERO;

		dataLeituraAtual = this.getControladorFaturamento().buscarDataLeituraCronograma(idFaturamentoGrupo, false,
						contaHistorico.getAnoMesReferenciaConta());

		Collection<CalcularValoresAguaEsgotoHelper> colecaoValoresCalculadosAguaEsgoto = this.getControladorFaturamento()
						.obterCalcularValoresAguaEsgotoParaAjuste(emitirContaHelper, contaHistorico.getImovel(), dataLeituraAnterior,
										dataLeituraAtual);

		Iterator it2 = colecaoValoresCalculadosAguaEsgoto.iterator();

		while(it2.hasNext()){
			CalcularValoresAguaEsgotoHelper calcularValoresAguaEsgotoHelper = (CalcularValoresAguaEsgotoHelper) it2.next();

			/*
			 * Caso tenha valor de água faturado para categoria adiciona o valor de água ao
			 * valor total de água. Caso contrário soma zero.
			 */
			if(calcularValoresAguaEsgotoHelper.getValorFaturadoAguaCategoria() != null){
				valorTotalAgua = valorTotalAgua.add(calcularValoresAguaEsgotoHelper.getValorFaturadoAguaCategoria());
			}else{
				valorTotalAgua = valorTotalAgua.add(new BigDecimal("0.00"));
			}

			if(calcularValoresAguaEsgotoHelper.getValorFaturadoEsgotoCategoria() != null){
				valorTotalEsgoto = valorTotalEsgoto.add(calcularValoresAguaEsgotoHelper.getValorFaturadoEsgotoCategoria());
			}else{
				valorTotalEsgoto = valorTotalEsgoto.add(new BigDecimal("0.00"));
			}

		}

		if(valorTotalAgua.compareTo(contaHistorico.getValorAgua()) != 0 && valorTotalAgua.compareTo(contaHistorico.getValorAgua()) > 0){

			valorAguaConta = contaHistorico.getValorAgua();
			valorAguaRecalculado = valorTotalAgua;

			valorDiferencaAgua = valorAguaRecalculado.subtract(valorAguaConta);

			OperacaoContabilHelper operacaoContabilHelper = this.gerarDebitoACobrar(916, contaHistorico.getLocalidade(), contaHistorico
							.getQuadra(), contaHistorico.getImovel(), contaHistorico.getAnoMesReferenciaConta(), valorDiferencaAgua);

			if(operacaoContabilHelper != null){
				gerouLinha = true;

				colecaoOperacaoContabil.add(operacaoContabilHelper);

				// Valor da Diferênça Lançado para o Imóvel
				arquivoConferencia.append("imóvel: " + contaHistorico.getImovel().getId() + " valorAguaConta: " + valorAguaConta
								+ " valorAguaRecalculado: " + valorAguaRecalculado + " valorDiferenca: " + valorDiferencaAgua);

				somatorioValorTotalAgua = valorAguaConta;
				somatorioValorTotalAguaCalculado = valorAguaRecalculado;
				somatorioValorTotalAguaDiferenca = valorDiferencaAgua;

			}

		}

		if(valorTotalEsgoto.compareTo(contaHistorico.getValorEsgoto()) != 0
						&& valorTotalEsgoto.compareTo(contaHistorico.getValorEsgoto()) > 0){

			valorEsgotoConta = contaHistorico.getValorEsgoto();
			valorEsgotoRecalculado = valorTotalEsgoto;

			valorDiferencaEsgoto = valorEsgotoRecalculado.subtract(valorEsgotoConta);

			OperacaoContabilHelper operacaoContabilHelper = this.gerarDebitoACobrar(917, contaHistorico.getLocalidade(), contaHistorico
							.getQuadra(), contaHistorico.getImovel(), contaHistorico.getAnoMesReferenciaConta(), valorDiferencaEsgoto);

			if(operacaoContabilHelper != null){
				gerouLinha = true;

				colecaoOperacaoContabil.add(operacaoContabilHelper);

				// Valor da Diferênça Lançado para o Imóvel
				arquivoConferencia.append("imóvel: " + contaHistorico.getImovel().getId() + " valorAguaConta: " + valorEsgotoConta
								+ " valorAguaRecalculado: " + valorEsgotoRecalculado + " valorDiferenca: " + valorDiferencaEsgoto);

				somatorioValorTotalEsgoto = valorEsgotoConta;
				somatorioValorTotalEsgotoCalculado = valorEsgotoRecalculado;
				somatorioValorTotalEsgotoDiferenca = valorDiferencaEsgoto;
			}

		}

		retorno[0] = arquivoConferencia;
		retorno[1] = colecaoOperacaoContabil;
		retorno[2] = gerouLinha;

		retorno[3] = somatorioValorTotalAgua;
		retorno[4] = somatorioValorTotalEsgoto;
		retorno[5] = somatorioValorTotalAguaCalculado;
		retorno[6] = somatorioValorTotalEsgotoCalculado;
		retorno[7] = somatorioValorTotalAguaDiferenca;
		retorno[8] = somatorioValorTotalEsgotoDiferenca;

		return retorno;

	}

	/**
	 * Método que realiza o ajuste do faturamento de débitos a cobrar da DESO para referência 201211
	 * 
	 * @author Anderson Italo
	 * @date 26/11/2012
	 */
	public void executarAjusteDebitoACobrarMultaParcelamento() throws IOException, Exception{

		this.ejbCreate();
		System.out.println("INÍCIO AJUSTE FATURAMENTO 4 CANCELAMENTO DÉBITOS A COBRAR ");
		Collection<Object[]> colecaoDebitosCancelar = repositorioFaturamento.pesquisarDebitoACobrarParaCancelar();

		String[] idDebitoCancelamento = null;
		for(Object[] dados : colecaoDebitosCancelar){

			idDebitoCancelamento = new String[1];
			idDebitoCancelamento[0] = dados[0].toString();
			getControladorFaturamento().cancelarDebitoACobrar(idDebitoCancelamento, Usuario.USUARIO_BATCH,
							Util.obterInteger(dados[1].toString()), false);
		}
		System.out.println("FIM AJUSTE FATURAMENTO 4 CANCELAMENTO DÉBITOS A COBRAR ");
	}

	/**
	 * Método que realiza o ajuste do faturamento de serviços retificando as contas já retificadas
	 * anteriormente com erro baseado na conta original
	 * 
	 * @date 09/12/2012
	 */
	public void executarAjusteServicosRetificarContasErradas(Integer idFaturamentoGrupo, Integer anoMesReferencia) throws IOException,
					Exception{

		// Inicializa as instacias dos repositórios usados
		this.ejbCreate();

		// Declaração das variáveis
		Collection<MovimentoRoteiroEmpresa> colecaoMovimentoRoteiroEmpresa = null;
		Collection<DebitoAjusteFaturamentoDesoHelper> colecaoDebitoAjusteHelper = null;
		Collection<DebitoCobradoHistorico> colecaoDebitoCobradoContaOriginal = null;
		Collection<DebitoCobrado> colecaoDebitoCobradoContaNova = null;
		Collection<OperacaoContabilHelper> colecaoOperacaoContabil = new ArrayList<OperacaoContabilHelper>();
		ContaMotivoRetificacao contaMotivoRetificacao = new ContaMotivoRetificacao();
		contaMotivoRetificacao.setId(36);
		ContaHistorico contaOriginalRetificada = null;
		Conta contaAtual = null;
		Date dataGeracaoDebitoMarcacaoFixa = Util.converteStringParaDateHora("11/12/2012 21:00:00");
		Date dataInicialPrimeiroAjuste = Util.converteStringParaDateHora("27/11/2012 23:35:00");
		Collection<ContaHistorico> colecaoContaOriginalRetificada = new ArrayList<ContaHistorico>();
		Collection<Integer> colecaoIdsDebitoTipoRubricaImovelComProblema = null;

		Collection colecaoRelacaoTresDeso = new ArrayList();
		RelacaoTresDeso relacaoTresDeso = null;

		Boolean ignorarImovel = false;

		// Obtém as rotas do grupo
		FiltroRota filtroRota = new FiltroRota();
		filtroRota.adicionarParametro(new ParametroSimples(FiltroRota.FATURAMENTO_GRUPO_ID, idFaturamentoGrupo));
		filtroRota.setCampoOrderBy(FiltroRota.ID_ROTA);
		Collection<Rota> colecaoRota = getControladorUtil().pesquisar(filtroRota, Rota.class.getName());

		// Processa o ajuste por rota
		for(Rota rota : colecaoRota){

			// Obtém os registros de movimento roteiro empresa que não tiveram serviços cobrados na
			// conta atual(Ref. 201211) devido ao erro do faturamento
			colecaoMovimentoRoteiroEmpresa = repositorioFaturamento.pesquisarDadosMovimentoRoteiroEmpresaAjuste2(anoMesReferencia, rota
							.getId());

			// Para cada registro de movimento roteiro empresa da rota
			for(Iterator iterator = colecaoMovimentoRoteiroEmpresa.iterator(); iterator.hasNext();){

				MovimentoRoteiroEmpresa movimentoRoteiroEmpresa = (MovimentoRoteiroEmpresa) iterator.next();
				colecaoDebitoAjusteHelper = new ArrayList<DebitoAjusteFaturamentoDesoHelper>();
				colecaoDebitoCobradoContaOriginal = new ArrayList<DebitoCobradoHistorico>();
				colecaoDebitoCobradoContaNova = new ArrayList<DebitoCobrado>();
				colecaoIdsDebitoTipoRubricaImovelComProblema = new ArrayList<Integer>();

				// Obtém as contas do imóvel
				colecaoContaOriginalRetificada = repositorioFaturamento.pesquisarContaRetificadaOutroUsuarioPeriodo(movimentoRoteiroEmpresa
								.getImovel().getId(), anoMesReferencia, dataInicialPrimeiroAjuste);
				contaOriginalRetificada = repositorioFaturamento.pesquisarContaOriginalRetificada(movimentoRoteiroEmpresa.getImovel()
								.getId(), anoMesReferencia);

				// Se a conta original não foi retificada por outro usuário no período de ajuste
				if(Util.isVazioOrNulo(colecaoContaOriginalRetificada) && contaOriginalRetificada != null){
					ignorarImovel = false;

					// Para cada imóvel selecionado: criar coleção com os débitos enviados a menor
					if(!ignorarImovel && movimentoRoteiroEmpresa.getDescricaoRubrica1() != null){

						obterDebitosParaAjusteRetificarContaRetificada(contaOriginalRetificada, movimentoRoteiroEmpresa
										.getDescricaoRubrica1(), movimentoRoteiroEmpresa.getNumeroPrestacaoRubrica1(),
										movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica1(), movimentoRoteiroEmpresa
														.getValorRubrica1(), colecaoDebitoAjusteHelper, dataGeracaoDebitoMarcacaoFixa,
										colecaoOperacaoContabil, colecaoIdsDebitoTipoRubricaImovelComProblema, ignorarImovel);
					}

					if(!ignorarImovel && movimentoRoteiroEmpresa.getDescricaoRubrica2() != null){

						obterDebitosParaAjusteRetificarContaRetificada(contaOriginalRetificada, movimentoRoteiroEmpresa
										.getDescricaoRubrica2(), movimentoRoteiroEmpresa.getNumeroPrestacaoRubrica2(),
										movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica2(), movimentoRoteiroEmpresa
														.getValorRubrica2(), colecaoDebitoAjusteHelper, dataGeracaoDebitoMarcacaoFixa,
										colecaoOperacaoContabil, colecaoIdsDebitoTipoRubricaImovelComProblema, ignorarImovel);
					}

					if(!ignorarImovel && movimentoRoteiroEmpresa.getDescricaoRubrica3() != null){

						obterDebitosParaAjusteRetificarContaRetificada(contaOriginalRetificada, movimentoRoteiroEmpresa
										.getDescricaoRubrica3(), movimentoRoteiroEmpresa.getNumeroPrestacaoRubrica3(),
										movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica3(), movimentoRoteiroEmpresa
														.getValorRubrica3(), colecaoDebitoAjusteHelper, dataGeracaoDebitoMarcacaoFixa,
										colecaoOperacaoContabil, colecaoIdsDebitoTipoRubricaImovelComProblema, ignorarImovel);
					}

					if(!ignorarImovel && movimentoRoteiroEmpresa.getDescricaoRubrica4() != null){

						obterDebitosParaAjusteRetificarContaRetificada(contaOriginalRetificada, movimentoRoteiroEmpresa
										.getDescricaoRubrica4(), movimentoRoteiroEmpresa.getNumeroPrestacaoRubrica4(),
										movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica4(), movimentoRoteiroEmpresa
														.getValorRubrica4(), colecaoDebitoAjusteHelper, dataGeracaoDebitoMarcacaoFixa,
										colecaoOperacaoContabil, colecaoIdsDebitoTipoRubricaImovelComProblema, ignorarImovel);
					}

					if(!ignorarImovel && movimentoRoteiroEmpresa.getDescricaoRubrica5() != null){

						obterDebitosParaAjusteRetificarContaRetificada(contaOriginalRetificada, movimentoRoteiroEmpresa
										.getDescricaoRubrica5(), movimentoRoteiroEmpresa.getNumeroPrestacaoRubrica5(),
										movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica5(), movimentoRoteiroEmpresa
														.getValorRubrica5(), colecaoDebitoAjusteHelper, dataGeracaoDebitoMarcacaoFixa,
										colecaoOperacaoContabil, colecaoIdsDebitoTipoRubricaImovelComProblema, ignorarImovel);
					}

					if(!ignorarImovel && movimentoRoteiroEmpresa.getDescricaoRubrica6() != null){

						obterDebitosParaAjusteRetificarContaRetificada(contaOriginalRetificada, movimentoRoteiroEmpresa
										.getDescricaoRubrica6(), movimentoRoteiroEmpresa.getNumeroPrestacaoRubrica6(),
										movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica6(), movimentoRoteiroEmpresa
														.getValorRubrica6(), colecaoDebitoAjusteHelper, dataGeracaoDebitoMarcacaoFixa,
										colecaoOperacaoContabil, colecaoIdsDebitoTipoRubricaImovelComProblema, ignorarImovel);
					}

					if(!ignorarImovel && movimentoRoteiroEmpresa.getDescricaoRubrica7() != null){

						obterDebitosParaAjusteRetificarContaRetificada(contaOriginalRetificada, movimentoRoteiroEmpresa
										.getDescricaoRubrica7(), movimentoRoteiroEmpresa.getNumeroPrestacaoRubrica7(),
										movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica7(), movimentoRoteiroEmpresa
														.getValorRubrica7(), colecaoDebitoAjusteHelper, dataGeracaoDebitoMarcacaoFixa,
										colecaoOperacaoContabil, colecaoIdsDebitoTipoRubricaImovelComProblema, ignorarImovel);
					}

					if(!ignorarImovel && movimentoRoteiroEmpresa.getDescricaoRubrica8() != null){

						obterDebitosParaAjusteRetificarContaRetificada(contaOriginalRetificada, movimentoRoteiroEmpresa
										.getDescricaoRubrica8(), movimentoRoteiroEmpresa.getNumeroPrestacaoRubrica8(),
										movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica8(), movimentoRoteiroEmpresa
														.getValorRubrica8(), colecaoDebitoAjusteHelper, dataGeracaoDebitoMarcacaoFixa,
										colecaoOperacaoContabil, colecaoIdsDebitoTipoRubricaImovelComProblema, ignorarImovel);
					}

					if(!ignorarImovel && movimentoRoteiroEmpresa.getDescricaoRubrica9() != null){

						obterDebitosParaAjusteRetificarContaRetificada(contaOriginalRetificada, movimentoRoteiroEmpresa
										.getDescricaoRubrica9(), movimentoRoteiroEmpresa.getNumeroPrestacaoRubrica9(),
										movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica9(), movimentoRoteiroEmpresa
														.getValorRubrica9(), colecaoDebitoAjusteHelper, dataGeracaoDebitoMarcacaoFixa,
										colecaoOperacaoContabil, colecaoIdsDebitoTipoRubricaImovelComProblema, ignorarImovel);
					}

					if(!ignorarImovel && movimentoRoteiroEmpresa.getDescricaoRubrica10() != null){

						obterDebitosParaAjusteRetificarContaRetificada(contaOriginalRetificada, movimentoRoteiroEmpresa
										.getDescricaoRubrica10(), movimentoRoteiroEmpresa.getNumeroPrestacaoRubrica10(),
										movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica10(), movimentoRoteiroEmpresa
														.getValorRubrica10(), colecaoDebitoAjusteHelper, dataGeracaoDebitoMarcacaoFixa,
										colecaoOperacaoContabil, colecaoIdsDebitoTipoRubricaImovelComProblema, ignorarImovel);
					}

					if(!ignorarImovel && movimentoRoteiroEmpresa.getDescricaoRubrica11() != null){

						obterDebitosParaAjusteRetificarContaRetificada(contaOriginalRetificada, movimentoRoteiroEmpresa
										.getDescricaoRubrica11(), movimentoRoteiroEmpresa.getNumeroPrestacaoRubrica11(),
										movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica11(), movimentoRoteiroEmpresa
														.getValorRubrica11(), colecaoDebitoAjusteHelper, dataGeracaoDebitoMarcacaoFixa,
										colecaoOperacaoContabil, colecaoIdsDebitoTipoRubricaImovelComProblema, ignorarImovel);
					}

					if(!ignorarImovel && movimentoRoteiroEmpresa.getDescricaoRubrica12() != null){

						obterDebitosParaAjusteRetificarContaRetificada(contaOriginalRetificada, movimentoRoteiroEmpresa
										.getDescricaoRubrica12(), movimentoRoteiroEmpresa.getNumeroPrestacaoRubrica12(),
										movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica12(), movimentoRoteiroEmpresa
														.getValorRubrica12(), colecaoDebitoAjusteHelper, dataGeracaoDebitoMarcacaoFixa,
										colecaoOperacaoContabil, colecaoIdsDebitoTipoRubricaImovelComProblema, ignorarImovel);
					}

					if(!ignorarImovel && movimentoRoteiroEmpresa.getDescricaoRubrica13() != null){

						obterDebitosParaAjusteRetificarContaRetificada(contaOriginalRetificada, movimentoRoteiroEmpresa
										.getDescricaoRubrica13(), movimentoRoteiroEmpresa.getNumeroPrestacaoRubrica13(),
										movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica13(), movimentoRoteiroEmpresa
														.getValorRubrica13(), colecaoDebitoAjusteHelper, dataGeracaoDebitoMarcacaoFixa,
										colecaoOperacaoContabil, colecaoIdsDebitoTipoRubricaImovelComProblema, ignorarImovel);
					}

					if(!ignorarImovel && movimentoRoteiroEmpresa.getDescricaoRubrica14() != null){

						obterDebitosParaAjusteRetificarContaRetificada(contaOriginalRetificada, movimentoRoteiroEmpresa
										.getDescricaoRubrica14(), movimentoRoteiroEmpresa.getNumeroPrestacaoRubrica14(),
										movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica14(), movimentoRoteiroEmpresa
														.getValorRubrica14(), colecaoDebitoAjusteHelper, dataGeracaoDebitoMarcacaoFixa,
										colecaoOperacaoContabil, colecaoIdsDebitoTipoRubricaImovelComProblema, ignorarImovel);
					}

					if(!ignorarImovel && movimentoRoteiroEmpresa.getDescricaoRubrica15() != null){

						obterDebitosParaAjusteRetificarContaRetificada(contaOriginalRetificada, movimentoRoteiroEmpresa
										.getDescricaoRubrica15(), movimentoRoteiroEmpresa.getNumeroPrestacaoRubrica15(),
										movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica15(), movimentoRoteiroEmpresa
														.getValorRubrica15(), colecaoDebitoAjusteHelper, dataGeracaoDebitoMarcacaoFixa,
										colecaoOperacaoContabil, colecaoIdsDebitoTipoRubricaImovelComProblema, ignorarImovel);
					}

					// Caso tenha rubricas a serem ajustadas
					if(!Util.isVazioOrNulo(colecaoDebitoAjusteHelper)){

						// Faz o ajuste agrupando a diferença a ser gerada por tipo de débito
						for(Integer idTipoDebitoRubricaComProblema : colecaoIdsDebitoTipoRubricaImovelComProblema){

							BigDecimal valorRestanteSerCobrado = BigDecimal.ZERO;
							DebitoTipo debitoTipo = null;

							// Totaliza o valor restante a ser cobrado para o tipo de débito
							for(DebitoAjusteFaturamentoDesoHelper ajuste : colecaoDebitoAjusteHelper){

								if(idTipoDebitoRubricaComProblema.intValue() == ajuste.getDebitoTipo().getId().intValue()){

									valorRestanteSerCobrado = valorRestanteSerCobrado.add(ajuste.getValorRestanteSerCobrado());
									debitoTipo = ajuste.getDebitoTipo();
								}
							}

							// Obtém o valor residual anterior cobrado ainda não faturado
							BigDecimal valorResidualAnteriorCobradoNaoFaturado = repositorioFaturamento
											.pesquisarSomatorioDebitosACobrarNaoFaturadosGeradoPrimeiroAjuste(movimentoRoteiroEmpresa
															.getImovel().getId(), 201211, 201212, idTipoDebitoRubricaComProblema);

							// Obtém o valor residual anterior cobrado já faturado
							BigDecimal valorResidualAnteriorCobradoFaturado = repositorioFaturamento
											.pesquisarSomatorioDebitosACobrarHistoricoFaturadosGeradoPrimeiroAjuste(movimentoRoteiroEmpresa
															.getImovel().getId(), 201211, 201212, idTipoDebitoRubricaComProblema);

							BigDecimal valorResidualAnteriorCobrado = BigDecimal.ZERO;
							if(valorResidualAnteriorCobradoNaoFaturado != null){

								valorResidualAnteriorCobrado = valorResidualAnteriorCobradoNaoFaturado;
							}

							if(valorResidualAnteriorCobradoFaturado != null){

								valorResidualAnteriorCobrado = valorResidualAnteriorCobrado.add(valorResidualAnteriorCobradoFaturado);
							}

							/*
							 * Caso o valor restante a ser cobrado seja maior que valor anterior
							 * cobrado, então gerar um débito a cobrar com o valor residual a ser
							 * cobrado
							 */
							BigDecimal valorResidualASerCobrado = BigDecimal.ZERO;
							if(valorRestanteSerCobrado.compareTo(valorResidualAnteriorCobrado) == 1){

								// Carrega os dados do imóvel da conta
								contaOriginalRetificada.setImovel(this.getControladorImovel().pesquisarImovel(
												movimentoRoteiroEmpresa.getImovel().getId()));

								valorResidualASerCobrado = valorRestanteSerCobrado.subtract(valorResidualAnteriorCobrado);

								CobrancaForma cobrancaForma = new CobrancaForma();
								cobrancaForma.setId(CobrancaForma.COBRANCA_EM_CONTA);

								DebitoACobrar debitoACobrar = new DebitoACobrar();
								debitoACobrar.setDebitoTipo(debitoTipo);
								debitoACobrar.setValorDebito(valorResidualASerCobrado);
								debitoACobrar.setImovel(contaOriginalRetificada.getImovel());
								debitoACobrar.setGeracaoDebito(dataGeracaoDebitoMarcacaoFixa);
								debitoACobrar.setLocalidade(contaOriginalRetificada.getLocalidade());
								debitoACobrar.setQuadra(contaOriginalRetificada.getQuadra());
								debitoACobrar.setAnoMesReferenciaDebito(201211);
								debitoACobrar.setLancamentoItemContabil(debitoTipo.getLancamentoItemContabil());
								debitoACobrar.setFinanciamentoTipo(debitoTipo.getFinanciamentoTipo());
								debitoACobrar.setCobrancaForma(cobrancaForma);
								debitoACobrar.setUltimaAlteracao(new Date());
								debitoACobrar.setAnoMesCobrancaDebito(201301);

								OperacaoContabilHelper helper = new OperacaoContabilHelper();
								helper.setObjetoOrigem(this.getControladorFaturamento().inserirDebitoACobrarSemRegistrarLancamentoContabil(
												1, debitoACobrar, null, contaOriginalRetificada.getImovel(), null, null,
												Usuario.USUARIO_BATCH, false, null, null, null));
								helper.setOperacaoContabil(OperacaoContabil.INCLUIR_DEBITO_A_COBRAR);

								colecaoOperacaoContabil.add(helper);
							}

							relacaoTresDeso = new RelacaoTresDeso();

							relacaoTresDeso.setIdFaturamentoGrupo(idFaturamentoGrupo);

							relacaoTresDeso.setIdImovel(movimentoRoteiroEmpresa.getImovel().getId());

							relacaoTresDeso.setIdDebitoTipo(idTipoDebitoRubricaComProblema);

							relacaoTresDeso.setValorRestanteSerCobrado(valorRestanteSerCobrado);

							relacaoTresDeso.setValorGeradoPrimeiraRotinaAjt(valorResidualAnteriorCobrado);

							relacaoTresDeso.setValorGeradoRotinaAjusteAtual(valorResidualASerCobrado);

							relacaoTresDeso.setUltimaAlteracao(new Date());

							colecaoRelacaoTresDeso.add(relacaoTresDeso);
						}
					}

					// Caso seja necessário retificar a conta
					contaAtual = repositorioFaturamento.pesquisarContaAtualRetificada(movimentoRoteiroEmpresa.getImovel().getId(),
									anoMesReferencia);

					if(contaAtual != null && !Util.isVazioOrNulo(colecaoDebitoAjusteHelper)){

						// Obtém todos os débitos cobrados da conta na referência atual para
						// atualizar o valor do débito ajustado
						colecaoDebitoCobradoContaOriginal = repositorioFaturamento
										.pesquisarDebitosCobradoDaContaOriginal(contaOriginalRetificada.getId());

						if(!Util.isVazioOrNulo(colecaoDebitoCobradoContaOriginal)){

							for(DebitoCobradoHistorico debitoCobradoHistorico : colecaoDebitoCobradoContaOriginal){

								boolean encontrouDebito = false;
								boolean jaAjustouDebito = false;
								for(DebitoAjusteFaturamentoDesoHelper debitoAjusteHelper : colecaoDebitoAjusteHelper){

									if(debitoCobradoHistorico.getDebitoTipo().getId().equals(debitoAjusteHelper.getDebitoTipo().getId())
													&& debitoCobradoHistorico.getNumeroPrestacao().equals(
																	debitoAjusteHelper.getNumeroPrestacao())
													&& debitoCobradoHistorico.getNumeroPrestacaoDebito().equals(
																	debitoAjusteHelper.getNumeroPrestacaoDebito())){

										for(DebitoCobrado debitoCobradoAjustado : colecaoDebitoCobradoContaNova){

											if(debitoAjusteHelper.getDebitoTipo().getId().equals(
															debitoCobradoAjustado.getDebitoTipo().getId())
															&& debitoAjusteHelper.getNumeroPrestacao().equals(
																			debitoCobradoAjustado.getNumeroPrestacao())
															&& debitoAjusteHelper.getNumeroPrestacaoDebito().equals(
																			debitoCobradoAjustado.getNumeroPrestacaoDebito())){
												jaAjustouDebito = true;
												break;
											}
										}

										if(jaAjustouDebito == false){

											// Débito Cobrado da Contaoriginal que foi enviado com
											// valor
											// a
											// menor
											DebitoCobrado debitoCobrado = new DebitoCobrado();
											debitoCobrado.setCodigoSetorComercial(debitoCobradoHistorico.getCodigoSetorComercial());
											debitoCobrado.setNumeroQuadra(debitoCobradoHistorico.getNumeroQuadra());
											debitoCobrado.setNumeroLote(debitoCobradoHistorico.getNumeroLote());
											debitoCobrado.setNumeroSubLote(debitoCobradoHistorico.getNumeroSubLote());
											debitoCobrado.setAnoMesReferenciaDebito(debitoCobradoHistorico.getAnoMesReferenciaDebito());
											debitoCobrado.setAnoMesCobrancaDebito(debitoCobradoHistorico.getAnoMesCobrancaDebito());
											debitoCobrado.setValorPrestacao(debitoAjusteHelper.getValorEnviado());
											debitoCobrado.setNumeroPrestacao(debitoCobradoHistorico.getNumeroPrestacao());
											debitoCobrado.setNumeroPrestacaoDebito(debitoCobradoHistorico.getNumeroPrestacaoDebito());
											debitoCobrado.setUltimaAlteracao(new Date());
											debitoCobrado.setDebitoCobrado(debitoCobradoHistorico.getDebitoCobrado());
											debitoCobrado.setFinanciamentoTipo(debitoCobradoHistorico.getFinanciamentoTipo());
											debitoCobrado.setQuadra(debitoCobradoHistorico.getQuadra());
											debitoCobrado.setLocalidade(debitoCobradoHistorico.getLocalidade());
											debitoCobrado.setDebitoTipo(debitoCobradoHistorico.getDebitoTipo());
											debitoCobrado.setLancamentoItemContabil(debitoCobradoHistorico.getLancamentoItemContabil());
											debitoCobrado.setParcelamentoGrupo(debitoCobradoHistorico.getParcelamentoGrupo());
											debitoCobrado.setParcelamento(debitoCobradoHistorico.getParcelamento());
											colecaoDebitoCobradoContaNova.add(debitoCobrado);
											encontrouDebito = true;
											break;
										}else{
											break;
										}
									}
								}

								if(encontrouDebito == false && jaAjustouDebito == false){

									// Débito Cobrado da Contaoriginal que foi enviado corretamente
									DebitoCobrado debitoCobrado = new DebitoCobrado();
									debitoCobrado.setCodigoSetorComercial(debitoCobradoHistorico.getCodigoSetorComercial());
									debitoCobrado.setNumeroQuadra(debitoCobradoHistorico.getNumeroQuadra());
									debitoCobrado.setNumeroLote(debitoCobradoHistorico.getNumeroLote());
									debitoCobrado.setNumeroSubLote(debitoCobradoHistorico.getNumeroSubLote());
									debitoCobrado.setAnoMesReferenciaDebito(debitoCobradoHistorico.getAnoMesReferenciaDebito());
									debitoCobrado.setAnoMesCobrancaDebito(debitoCobradoHistorico.getAnoMesCobrancaDebito());
									debitoCobrado.setValorPrestacao(debitoCobradoHistorico.getValorPrestacao());
									debitoCobrado.setNumeroPrestacao(debitoCobradoHistorico.getNumeroPrestacao());
									debitoCobrado.setNumeroPrestacaoDebito(debitoCobradoHistorico.getNumeroPrestacaoDebito());
									debitoCobrado.setUltimaAlteracao(new Date());
									debitoCobrado.setDebitoCobrado(debitoCobradoHistorico.getDebitoCobrado());
									debitoCobrado.setFinanciamentoTipo(debitoCobradoHistorico.getFinanciamentoTipo());
									debitoCobrado.setQuadra(debitoCobradoHistorico.getQuadra());
									debitoCobrado.setLocalidade(debitoCobradoHistorico.getLocalidade());
									debitoCobrado.setDebitoTipo(debitoCobradoHistorico.getDebitoTipo());
									debitoCobrado.setLancamentoItemContabil(debitoCobradoHistorico.getLancamentoItemContabil());
									debitoCobrado.setParcelamentoGrupo(debitoCobradoHistorico.getParcelamentoGrupo());
									debitoCobrado.setParcelamento(debitoCobradoHistorico.getParcelamento());
									colecaoDebitoCobradoContaNova.add(debitoCobrado);
								}
							}
						}

						// Carrega na conta os dados necessários para retificação
						contaAtual = getControladorFaturamento().pesquisarContaRetificacao(contaAtual.getId());

						// Obtendo os créditos realizados da conta
						Collection colecaoCreditoRealizado = getControladorFaturamento().obterCreditosRealizadosConta(contaAtual);

						// Retificar a Conta <<Inclui>> [UC0150 Retificar Conta]
						getControladorFaturamento().retificarContaAjusteFaturamento(anoMesReferencia, contaAtual, contaAtual.getImovel(),
										colecaoDebitoCobradoContaNova, contaAtual.getLigacaoAguaSituacao(),
										contaAtual.getLigacaoEsgotoSituacao(), contaAtual.getConsumoAgua().toString(),
										contaAtual.getConsumoEsgoto().toString(), contaAtual.getPercentualEsgoto().toString(),
										contaAtual.getDataVencimentoConta(), contaMotivoRetificacao, Usuario.USUARIO_BATCH,
										contaAtual.getConsumoTarifa(), colecaoCreditoRealizado);
					}
				}
			}
		}

		if(!Util.isVazioOrNulo(colecaoOperacaoContabil)){

			// Registra o lançamento contábil dos débitos a cobrar gerados
			this.getControladorContabil().registrarLancamentoContabil(colecaoOperacaoContabil);
		}

		// Gerar a tabela auxiliar de relação
		if(!Util.isVazioOrNulo(colecaoRelacaoTresDeso)){
			this.getControladorBatch().inserirColecaoObjetoParaBatch(colecaoRelacaoTresDeso);
		}
	}

	private void obterDebitosParaAjusteRetificarContaRetificada(ContaHistorico contaOriginal, String rubrica, Short numeroPrestacaoRubrica,
					Short numeroPrestacaoDebitoRubrica, BigDecimal valorRubrica,
					Collection<DebitoAjusteFaturamentoDesoHelper> colecaoDebitosAjusteHelper, Date dataGeracaoDebitoMarcacaoFixa,
					Collection<OperacaoContabilHelper> colecaoOperacaoContabil,
					Collection<Integer> colecaoIdsDebitoTipoRubricaImovelComProblema, Boolean ignorarImovel)
					throws ErroRepositorioException, ControladorException{

		// Verifica se truncou o valor do débito
		int indicePonto = valorRubrica.toString().indexOf(".");
		BigDecimal valorEnviado = BigDecimal.ZERO;
		boolean debitoAMenor = false;
		DebitoAjusteFaturamentoDesoHelper debitoAjusteHelper = new DebitoAjusteFaturamentoDesoHelper();
		Integer idDebitoTipo = Util.obterInteger(rubrica.substring(0, 3));

		// Obtém o valor do débito cobrado com erro
		if(!valorRubrica.toString().contains(".")){

			valorEnviado = new BigDecimal(valorRubrica.toString()).divide(new BigDecimal("100"));
			debitoAMenor = true;
		}else if(valorRubrica.toString().contains(".") && valorRubrica.toString().substring(indicePonto + 1).length() == 1){

			valorEnviado = new BigDecimal(valorRubrica.toString()).divide(new BigDecimal("10"));
			debitoAMenor = true;
		}

		if(debitoAMenor && contaOriginal != null){
			Imovel imovel = contaOriginal.getImovel();
			Integer idImovel = imovel.getId();

			FiltroConta filtroConta = new FiltroConta();
			filtroConta.adicionarParametro(new ParametroSimples(FiltroConta.IMOVEL_ID, idImovel));
			filtroConta.adicionarParametro(new ParametroSimples(FiltroConta.REFERENCIA, 201211));
			filtroConta.adicionarCaminhoParaCarregamentoEntidade(FiltroConta.DEBITO_CREDITO_SITUACAO_ATUAL);

			Collection<Conta> colecaoConta = this.getControladorUtil().pesquisar(filtroConta, Conta.class.getName());

			if(!Util.isVazioOrNulo(colecaoConta)){
				Conta conta = (Conta) Util.retonarObjetoDeColecao(colecaoConta);

				DebitoCreditoSituacao debitoCreditoSituacaoAtual = conta.getDebitoCreditoSituacaoAtual();

				if(debitoCreditoSituacaoAtual != null){
					Integer idDebitoCreditoSituacao = debitoCreditoSituacaoAtual.getId();

					if(idDebitoCreditoSituacao.intValue() == DebitoCreditoSituacao.INCLUIDA.intValue()){
						ignorarImovel = true;

						RelacaoMotivoNaoGeracao relacaoMotivoNaoGeracao = new RelacaoMotivoNaoGeracao();
						relacaoMotivoNaoGeracao.setIdImovel(idImovel);
						relacaoMotivoNaoGeracao.setMotivo("IMOVEL COM CONTA INCLUIDA");
						relacaoMotivoNaoGeracao.setUltimaAlteracao(new Date());

						this.getControladorUtil().inserir(relacaoMotivoNaoGeracao);
					}
				}
			}else{
				ContaHistorico contaHistorico = this.repositorioFaturamento.pesquisarContaEmHistoricoIncluidaCanceladaParcelada(idImovel,
								201211);

				if(contaHistorico != null){
					DebitoCreditoSituacao debitoCreditoSituacaoAtual = contaHistorico.getDebitoCreditoSituacaoAtual();

					if(debitoCreditoSituacaoAtual != null){
						Integer idDebitoCreditoSituacao = debitoCreditoSituacaoAtual.getId();

						String motivo = null;

						if(idDebitoCreditoSituacao.intValue() == DebitoCreditoSituacao.INCLUIDA.intValue()){
							motivo = "IMOVEL COM CONTA INCLUIDA";
						}else if(idDebitoCreditoSituacao.intValue() == DebitoCreditoSituacao.CANCELADA.intValue()){
							motivo = "IMOVEL COM CONTA CANCELADA";
						}else if(idDebitoCreditoSituacao.intValue() == DebitoCreditoSituacao.PARCELADA.intValue()){
							motivo = "IMOVEL COM CONTA PARCELADA";
						}

						ignorarImovel = true;

						RelacaoMotivoNaoGeracao relacaoMotivoNaoGeracao = new RelacaoMotivoNaoGeracao();
						relacaoMotivoNaoGeracao.setIdImovel(idImovel);
						relacaoMotivoNaoGeracao.setMotivo(motivo);
						relacaoMotivoNaoGeracao.setUltimaAlteracao(new Date());

						this.getControladorUtil().inserir(relacaoMotivoNaoGeracao);
					}
				}
			}

			if(!ignorarImovel){

				/*
				 * Popula a coleção com os débitos enviados a menor para posterior retificação da
				 * conta e geração do valor residual
				 */
				BigDecimal valorDebitosContaOriginal = repositorioFaturamento.pesquisarSomatorioDebitosCobradoDaContaOriginal(contaOriginal
								.getId(), idDebitoTipo, numeroPrestacaoRubrica, numeroPrestacaoDebitoRubrica);

				debitoAjusteHelper.setDebitoTipo((DebitoTipo) getControladorUtil().pesquisar(Util.obterInteger(rubrica.substring(0, 3)),
								DebitoTipo.class, false));

				if(!colecaoIdsDebitoTipoRubricaImovelComProblema.contains(debitoAjusteHelper.getDebitoTipo().getId())){

					colecaoIdsDebitoTipoRubricaImovelComProblema.add(debitoAjusteHelper.getDebitoTipo().getId());
				}

				debitoAjusteHelper.setValorEnviado(valorEnviado);
				debitoAjusteHelper.setNumeroPrestacao(numeroPrestacaoRubrica);
				debitoAjusteHelper.setNumeroPrestacaoDebito(numeroPrestacaoDebitoRubrica);
				debitoAjusteHelper.setValorRestanteSerCobrado(valorDebitosContaOriginal.subtract(valorEnviado));
				debitoAjusteHelper.setValorResidualAnteriorCobrado(BigDecimal.ZERO);
				debitoAjusteHelper.setValorFaturado(valorRubrica);
				colecaoDebitosAjusteHelper.add(debitoAjusteHelper);
			}
		}
	}

	public void verificarImoveisComErroRelacao1e2(Collection<FaturamentoGrupo> colecaoFaturamentoGrupo, Integer anoMesReferencia)
					throws IOException, Exception{

		// Inicializa as instacias dos repositórios usados
		this.ejbCreate();

		// Declaração das variáveis
		Collection<MovimentoRoteiroEmpresa> colecaoMovimentoRoteiroEmpresa = null;
		StringBuilder arquivoRelacao1 = new StringBuilder();
		StringBuilder arquivoRelacao2 = new StringBuilder();
		Collection<ContaHistorico> colecaoContaOriginalRetificada = null;
		ContaHistorico contaOriginalRetificada = null;

		// Início de geração do relatório de sáida
		Collection colecaoRelacaoTresDeso = new ArrayList();
		RelacaoTresDeso relacaoTresDeso = null;
		String nomeArquivoRelacao2 = "Relacao2.TXT";
		Collection<DebitoAjusteFaturamentoDesoHelper> colecaoDebitoAjusteHelper = null;
		Date dataInicialPrimeiroAjuste = Util.converteStringParaDateHora("27/11/2012 23:35:00");

		for(Iterator iteratorColecaoFaturamentoGrupo = colecaoFaturamentoGrupo.iterator(); iteratorColecaoFaturamentoGrupo.hasNext();){

			FaturamentoGrupo grupo = (FaturamentoGrupo) iteratorColecaoFaturamentoGrupo.next();

			// Obtém as rotas do grupo
			FiltroRota filtroRota = new FiltroRota();
			filtroRota.adicionarParametro(new ParametroSimples(FiltroRota.FATURAMENTO_GRUPO_ID, grupo.getId()));
			filtroRota.setCampoOrderBy(FiltroRota.ID_ROTA);
			Collection<Rota> colecaoRota = getControladorUtil().pesquisar(filtroRota, Rota.class.getName());

			// Processa a verificação por rota
			for(Rota rota : colecaoRota){

				colecaoMovimentoRoteiroEmpresa = repositorioFaturamento.pesquisarDadosMovimentoRoteiroEmpresaAjuste2(anoMesReferencia, rota
								.getId());

				// Para cada registro de movimento roteiro empresa da rota
				for(Iterator iterator = colecaoMovimentoRoteiroEmpresa.iterator(); iterator.hasNext();){

					MovimentoRoteiroEmpresa movimentoRoteiroEmpresa = (MovimentoRoteiroEmpresa) iterator.next();

					// Verifica se ocorreu erro na cobrança de serviço
					colecaoDebitoAjusteHelper = new ArrayList<DebitoAjusteFaturamentoDesoHelper>();

					// Para cada imóvel selecionado: criar coleção com os débitos enviados a menor
					if(movimentoRoteiroEmpresa.getDescricaoRubrica1() != null){

						verificarCobracaServicoErrada(movimentoRoteiroEmpresa.getDescricaoRubrica1(), movimentoRoteiroEmpresa
										.getNumeroPrestacaoRubrica1(), movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica1(),
										movimentoRoteiroEmpresa.getValorRubrica1(), colecaoDebitoAjusteHelper);
					}

					if(movimentoRoteiroEmpresa.getDescricaoRubrica2() != null){

						verificarCobracaServicoErrada(movimentoRoteiroEmpresa.getDescricaoRubrica2(), movimentoRoteiroEmpresa
										.getNumeroPrestacaoRubrica2(), movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica2(),
										movimentoRoteiroEmpresa.getValorRubrica2(), colecaoDebitoAjusteHelper);
					}

					if(movimentoRoteiroEmpresa.getDescricaoRubrica3() != null){

						verificarCobracaServicoErrada(movimentoRoteiroEmpresa.getDescricaoRubrica3(), movimentoRoteiroEmpresa
										.getNumeroPrestacaoRubrica3(), movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica3(),
										movimentoRoteiroEmpresa.getValorRubrica3(), colecaoDebitoAjusteHelper);
					}

					if(movimentoRoteiroEmpresa.getDescricaoRubrica4() != null){

						verificarCobracaServicoErrada(movimentoRoteiroEmpresa.getDescricaoRubrica4(), movimentoRoteiroEmpresa
										.getNumeroPrestacaoRubrica4(), movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica4(),
										movimentoRoteiroEmpresa.getValorRubrica4(), colecaoDebitoAjusteHelper);
					}

					if(movimentoRoteiroEmpresa.getDescricaoRubrica5() != null){

						verificarCobracaServicoErrada(movimentoRoteiroEmpresa.getDescricaoRubrica5(), movimentoRoteiroEmpresa
										.getNumeroPrestacaoRubrica5(), movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica5(),
										movimentoRoteiroEmpresa.getValorRubrica5(), colecaoDebitoAjusteHelper);
					}

					if(movimentoRoteiroEmpresa.getDescricaoRubrica6() != null){

						verificarCobracaServicoErrada(movimentoRoteiroEmpresa.getDescricaoRubrica6(), movimentoRoteiroEmpresa
										.getNumeroPrestacaoRubrica6(), movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica6(),
										movimentoRoteiroEmpresa.getValorRubrica6(), colecaoDebitoAjusteHelper);
					}

					if(movimentoRoteiroEmpresa.getDescricaoRubrica7() != null){

						verificarCobracaServicoErrada(movimentoRoteiroEmpresa.getDescricaoRubrica7(), movimentoRoteiroEmpresa
										.getNumeroPrestacaoRubrica7(), movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica7(),
										movimentoRoteiroEmpresa.getValorRubrica7(), colecaoDebitoAjusteHelper);
					}

					if(movimentoRoteiroEmpresa.getDescricaoRubrica8() != null){

						verificarCobracaServicoErrada(movimentoRoteiroEmpresa.getDescricaoRubrica8(), movimentoRoteiroEmpresa
										.getNumeroPrestacaoRubrica8(), movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica8(),
										movimentoRoteiroEmpresa.getValorRubrica8(), colecaoDebitoAjusteHelper);
					}

					if(movimentoRoteiroEmpresa.getDescricaoRubrica9() != null){

						verificarCobracaServicoErrada(movimentoRoteiroEmpresa.getDescricaoRubrica9(), movimentoRoteiroEmpresa
										.getNumeroPrestacaoRubrica9(), movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica9(),
										movimentoRoteiroEmpresa.getValorRubrica9(), colecaoDebitoAjusteHelper);
					}

					if(movimentoRoteiroEmpresa.getDescricaoRubrica10() != null){

						verificarCobracaServicoErrada(movimentoRoteiroEmpresa.getDescricaoRubrica10(), movimentoRoteiroEmpresa
										.getNumeroPrestacaoRubrica10(), movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica10(),
										movimentoRoteiroEmpresa.getValorRubrica10(), colecaoDebitoAjusteHelper);
					}

					if(movimentoRoteiroEmpresa.getDescricaoRubrica11() != null){

						verificarCobracaServicoErrada(movimentoRoteiroEmpresa.getDescricaoRubrica11(), movimentoRoteiroEmpresa
										.getNumeroPrestacaoRubrica11(), movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica11(),
										movimentoRoteiroEmpresa.getValorRubrica11(), colecaoDebitoAjusteHelper);
					}

					if(movimentoRoteiroEmpresa.getDescricaoRubrica12() != null){

						verificarCobracaServicoErrada(movimentoRoteiroEmpresa.getDescricaoRubrica12(), movimentoRoteiroEmpresa
										.getNumeroPrestacaoRubrica12(), movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica12(),
										movimentoRoteiroEmpresa.getValorRubrica12(), colecaoDebitoAjusteHelper);
					}

					if(movimentoRoteiroEmpresa.getDescricaoRubrica13() != null){

						verificarCobracaServicoErrada(movimentoRoteiroEmpresa.getDescricaoRubrica13(), movimentoRoteiroEmpresa
										.getNumeroPrestacaoRubrica13(), movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica13(),
										movimentoRoteiroEmpresa.getValorRubrica13(), colecaoDebitoAjusteHelper);
					}

					if(movimentoRoteiroEmpresa.getDescricaoRubrica14() != null){

						verificarCobracaServicoErrada(movimentoRoteiroEmpresa.getDescricaoRubrica14(), movimentoRoteiroEmpresa
										.getNumeroPrestacaoRubrica14(), movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica14(),
										movimentoRoteiroEmpresa.getValorRubrica14(), colecaoDebitoAjusteHelper);
					}

					if(movimentoRoteiroEmpresa.getDescricaoRubrica15() != null){

						verificarCobracaServicoErrada(movimentoRoteiroEmpresa.getDescricaoRubrica15(), movimentoRoteiroEmpresa
										.getNumeroPrestacaoRubrica15(), movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica15(),
										movimentoRoteiroEmpresa.getValorRubrica15(), colecaoDebitoAjusteHelper);
					}

					if(!Util.isVazioOrNulo(colecaoDebitoAjusteHelper)){

						colecaoContaOriginalRetificada = repositorioFaturamento.pesquisarContaRetificadaOutroUsuarioPeriodo(
										movimentoRoteiroEmpresa.getImovel().getId(), anoMesReferencia, dataInicialPrimeiroAjuste);
						contaOriginalRetificada = repositorioFaturamento.pesquisarContaOriginalRetificada(movimentoRoteiroEmpresa
										.getImovel().getId(), anoMesReferencia);

						boolean contaRetificadaOutroUsuario = false;
						if(!Util.isVazioOrNulo(colecaoContaOriginalRetificada) && contaOriginalRetificada != null){

							contaRetificadaOutroUsuario = true;
						}

						for(DebitoAjusteFaturamentoDesoHelper ajuste : colecaoDebitoAjusteHelper){

							if(Util.isVazioOrNulo(colecaoContaOriginalRetificada) && contaOriginalRetificada != null){

								arquivoRelacao1.append(Util.completarStringZeroEsquerda(grupo.getId().toString(), 3));
								arquivoRelacao1.append(Util.completarStringZeroEsquerda(movimentoRoteiroEmpresa.getImovel().getId()
												.toString(), 10));
								arquivoRelacao1.append(Util.completarStringZeroEsquerda(ajuste.getDebitoTipo().getId().toString(), 3));
								arquivoRelacao1.append(Util.completarStringZeroEsquerda(ajuste.getValorFaturado().toString(), 12));
								arquivoRelacao1.append(Util.completarStringZeroEsquerda(ajuste.getValorEnviado().toString(), 12));
								arquivoRelacao1.append(System.getProperty("line.separator"));

								relacaoTresDeso = new RelacaoTresDeso();
								relacaoTresDeso.setIdFaturamentoGrupo(grupo.getId());
								relacaoTresDeso.setIdImovel(movimentoRoteiroEmpresa.getImovel().getId());
								relacaoTresDeso.setIdDebitoTipo(ajuste.getDebitoTipo().getId());
								relacaoTresDeso.setValorFaturado(ajuste.getValorFaturado());
								relacaoTresDeso.setValorEnviado(ajuste.getValorEnviado());
								relacaoTresDeso.setUltimaAlteracao(new Date());

								colecaoRelacaoTresDeso.add(relacaoTresDeso);
							}

							/*
							 * Verifica se a conta original já foi retificada por outro usuário no
							 * período
							 * de ajuste
							 */
							if(contaRetificadaOutroUsuario){

								arquivoRelacao2.append(Util.completarStringZeroEsquerda(grupo.getId().toString(), 3));
								arquivoRelacao2.append(Util.completarStringZeroEsquerda(movimentoRoteiroEmpresa.getImovel().getId()
												.toString(), 10));
								arquivoRelacao2.append(Util.completarStringZeroEsquerda(ajuste.getDebitoTipo().getId().toString(), 3));
								arquivoRelacao2.append(Util.completarStringZeroEsquerda(ajuste.getValorFaturado().toString(), 12));
								arquivoRelacao2.append(Util.completarStringZeroEsquerda(ajuste.getValorEnviado().toString(), 12));
								arquivoRelacao2.append(System.getProperty("line.separator"));
							}
						}
					}
				}
			}
		}

		// Gerar relatório arquivo texto
		if(!Util.isVazioOrNulo(colecaoRelacaoTresDeso)){
			this.getControladorBatch().inserirColecaoObjetoParaBatch(colecaoRelacaoTresDeso);
		}
		gerarArquivo(arquivoRelacao2, nomeArquivoRelacao2);
	}

	private void verificarCobracaServicoErrada(String rubrica, Short numeroPrestacaoRubrica, Short numeroPrestacaoDebitoRubrica,
					BigDecimal valorRubrica, Collection<DebitoAjusteFaturamentoDesoHelper> colecaoDebitoAjuste)
					throws ErroRepositorioException, ControladorException{

		// Verifica se truncou o valor do débito
		int indicePonto = valorRubrica.toString().indexOf(".");
		BigDecimal valorEnviado = BigDecimal.ZERO;

		if(!valorRubrica.toString().contains(".")){

			valorEnviado = new BigDecimal(valorRubrica.toString()).divide(new BigDecimal("100"));
		}else if(valorRubrica.toString().contains(".") && valorRubrica.toString().substring(indicePonto + 1).length() == 1){

			valorEnviado = new BigDecimal(valorRubrica.toString()).divide(new BigDecimal("10"));

		}

		if(valorEnviado.compareTo(BigDecimal.ZERO) == 1){

			DebitoTipo debitoTipo = (DebitoTipo) getControladorUtil().pesquisar(Util.obterInteger(rubrica.substring(0, 3)),
							DebitoTipo.class, false);
			DebitoAjusteFaturamentoDesoHelper ajusteHelper = new DebitoAjusteFaturamentoDesoHelper();
			ajusteHelper.setDebitoTipo(debitoTipo);
			ajusteHelper.setValorFaturado(valorRubrica);
			ajusteHelper.setValorEnviado(valorEnviado);
			colecaoDebitoAjuste.add(ajusteHelper);
		}
	}

	public void verificarImoveisComErroRelacao5(Collection<FaturamentoGrupo> colecaoFaturamentoGrupo, Integer anoMesReferencia)
					throws IOException, Exception{

		// Inicializa as instacias dos repositórios usados
		this.ejbCreate();

		// Declaração das variáveis
		Collection<MovimentoRoteiroEmpresa> colecaoMovimentoRoteiroEmpresa = null;
		StringBuilder arquivoRelacao5 = new StringBuilder();
		ContaHistorico contaRetificadaPeriodoAnteriorAjuste = null;

		// Início de geração do relatório de sáida
		String nomeArquivoRelacao5 = "Relacao5.TXT";
		Collection<DebitoAjusteFaturamentoDesoHelper> colecaoDebitoAjusteHelper = null;

		for(Iterator iteratorColecaoFaturamentoGrupo = colecaoFaturamentoGrupo.iterator(); iteratorColecaoFaturamentoGrupo.hasNext();){

			FaturamentoGrupo grupo = (FaturamentoGrupo) iteratorColecaoFaturamentoGrupo.next();

			// Obtém as rotas do grupo
			FiltroRota filtroRota = new FiltroRota();
			filtroRota.adicionarParametro(new ParametroSimples(FiltroRota.FATURAMENTO_GRUPO_ID, grupo.getId()));
			filtroRota.setCampoOrderBy(FiltroRota.ID_ROTA);
			Collection<Rota> colecaoRota = getControladorUtil().pesquisar(filtroRota, Rota.class.getName());

			// Processa a verificação por rota
			for(Rota rota : colecaoRota){

				colecaoMovimentoRoteiroEmpresa = repositorioFaturamento.pesquisarDadosMovimentoRoteiroEmpresaAjuste2(anoMesReferencia, rota
								.getId());

				// Para cada registro de movimento roteiro empresa da rota
				for(Iterator iterator = colecaoMovimentoRoteiroEmpresa.iterator(); iterator.hasNext();){

					MovimentoRoteiroEmpresa movimentoRoteiroEmpresa = (MovimentoRoteiroEmpresa) iterator.next();

					// Verifica se ocorreu erro na cobrança de serviço
					colecaoDebitoAjusteHelper = new ArrayList<DebitoAjusteFaturamentoDesoHelper>();
					contaRetificadaPeriodoAnteriorAjuste = repositorioFaturamento.pesquisarContaRetificadaPeriodoAnterior(
									movimentoRoteiroEmpresa.getImovel().getId(), anoMesReferencia);
					int quantidadeRubricas = 0;

					// Para cada imóvel selecionado: criar coleção com os débitos enviados a menor
					if(movimentoRoteiroEmpresa.getDescricaoRubrica1() != null){

						verificarCobracaServicoErradaRelacao5(movimentoRoteiroEmpresa.getDescricaoRubrica1(), movimentoRoteiroEmpresa
										.getNumeroPrestacaoRubrica1(), movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica1(),
										movimentoRoteiroEmpresa.getValorRubrica1(), colecaoDebitoAjusteHelper);
						quantidadeRubricas += 1;
					}

					if(movimentoRoteiroEmpresa.getDescricaoRubrica2() != null){

						verificarCobracaServicoErradaRelacao5(movimentoRoteiroEmpresa.getDescricaoRubrica2(), movimentoRoteiroEmpresa
										.getNumeroPrestacaoRubrica2(), movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica2(),
										movimentoRoteiroEmpresa.getValorRubrica2(), colecaoDebitoAjusteHelper);
						quantidadeRubricas += 1;
					}

					if(movimentoRoteiroEmpresa.getDescricaoRubrica3() != null){

						verificarCobracaServicoErradaRelacao5(movimentoRoteiroEmpresa.getDescricaoRubrica3(), movimentoRoteiroEmpresa
										.getNumeroPrestacaoRubrica3(), movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica3(),
										movimentoRoteiroEmpresa.getValorRubrica3(), colecaoDebitoAjusteHelper);
						quantidadeRubricas += 1;
					}

					if(movimentoRoteiroEmpresa.getDescricaoRubrica4() != null){

						verificarCobracaServicoErradaRelacao5(movimentoRoteiroEmpresa.getDescricaoRubrica4(), movimentoRoteiroEmpresa
										.getNumeroPrestacaoRubrica4(), movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica4(),
										movimentoRoteiroEmpresa.getValorRubrica4(), colecaoDebitoAjusteHelper);
						quantidadeRubricas += 1;
					}

					if(movimentoRoteiroEmpresa.getDescricaoRubrica5() != null){

						verificarCobracaServicoErradaRelacao5(movimentoRoteiroEmpresa.getDescricaoRubrica5(), movimentoRoteiroEmpresa
										.getNumeroPrestacaoRubrica5(), movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica5(),
										movimentoRoteiroEmpresa.getValorRubrica5(), colecaoDebitoAjusteHelper);
						quantidadeRubricas += 1;
					}

					if(movimentoRoteiroEmpresa.getDescricaoRubrica6() != null){

						verificarCobracaServicoErradaRelacao5(movimentoRoteiroEmpresa.getDescricaoRubrica6(), movimentoRoteiroEmpresa
										.getNumeroPrestacaoRubrica6(), movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica6(),
										movimentoRoteiroEmpresa.getValorRubrica6(), colecaoDebitoAjusteHelper);
						quantidadeRubricas += 1;
					}

					if(movimentoRoteiroEmpresa.getDescricaoRubrica7() != null){

						verificarCobracaServicoErradaRelacao5(movimentoRoteiroEmpresa.getDescricaoRubrica7(), movimentoRoteiroEmpresa
										.getNumeroPrestacaoRubrica7(), movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica7(),
										movimentoRoteiroEmpresa.getValorRubrica7(), colecaoDebitoAjusteHelper);
						quantidadeRubricas += 1;
					}

					if(movimentoRoteiroEmpresa.getDescricaoRubrica8() != null){

						verificarCobracaServicoErradaRelacao5(movimentoRoteiroEmpresa.getDescricaoRubrica8(), movimentoRoteiroEmpresa
										.getNumeroPrestacaoRubrica8(), movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica8(),
										movimentoRoteiroEmpresa.getValorRubrica8(), colecaoDebitoAjusteHelper);
						quantidadeRubricas += 1;
					}

					if(movimentoRoteiroEmpresa.getDescricaoRubrica9() != null){

						verificarCobracaServicoErradaRelacao5(movimentoRoteiroEmpresa.getDescricaoRubrica9(), movimentoRoteiroEmpresa
										.getNumeroPrestacaoRubrica9(), movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica9(),
										movimentoRoteiroEmpresa.getValorRubrica9(), colecaoDebitoAjusteHelper);
						quantidadeRubricas += 1;
					}

					if(movimentoRoteiroEmpresa.getDescricaoRubrica10() != null){

						verificarCobracaServicoErradaRelacao5(movimentoRoteiroEmpresa.getDescricaoRubrica10(), movimentoRoteiroEmpresa
										.getNumeroPrestacaoRubrica10(), movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica10(),
										movimentoRoteiroEmpresa.getValorRubrica10(), colecaoDebitoAjusteHelper);
						quantidadeRubricas += 1;
					}

					if(movimentoRoteiroEmpresa.getDescricaoRubrica11() != null){

						verificarCobracaServicoErradaRelacao5(movimentoRoteiroEmpresa.getDescricaoRubrica11(), movimentoRoteiroEmpresa
										.getNumeroPrestacaoRubrica11(), movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica11(),
										movimentoRoteiroEmpresa.getValorRubrica11(), colecaoDebitoAjusteHelper);
						quantidadeRubricas += 1;
					}

					if(movimentoRoteiroEmpresa.getDescricaoRubrica12() != null){

						verificarCobracaServicoErradaRelacao5(movimentoRoteiroEmpresa.getDescricaoRubrica12(), movimentoRoteiroEmpresa
										.getNumeroPrestacaoRubrica12(), movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica12(),
										movimentoRoteiroEmpresa.getValorRubrica12(), colecaoDebitoAjusteHelper);
						quantidadeRubricas += 1;
					}

					if(movimentoRoteiroEmpresa.getDescricaoRubrica13() != null){

						verificarCobracaServicoErradaRelacao5(movimentoRoteiroEmpresa.getDescricaoRubrica13(), movimentoRoteiroEmpresa
										.getNumeroPrestacaoRubrica13(), movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica13(),
										movimentoRoteiroEmpresa.getValorRubrica13(), colecaoDebitoAjusteHelper);
						quantidadeRubricas += 1;
					}

					if(movimentoRoteiroEmpresa.getDescricaoRubrica14() != null){

						verificarCobracaServicoErradaRelacao5(movimentoRoteiroEmpresa.getDescricaoRubrica14(), movimentoRoteiroEmpresa
										.getNumeroPrestacaoRubrica14(), movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica14(),
										movimentoRoteiroEmpresa.getValorRubrica14(), colecaoDebitoAjusteHelper);
						quantidadeRubricas += 1;
					}

					if(movimentoRoteiroEmpresa.getDescricaoRubrica15() != null){

						verificarCobracaServicoErradaRelacao5(movimentoRoteiroEmpresa.getDescricaoRubrica15(), movimentoRoteiroEmpresa
										.getNumeroPrestacaoRubrica15(), movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica15(),
										movimentoRoteiroEmpresa.getValorRubrica15(), colecaoDebitoAjusteHelper);
						quantidadeRubricas += 1;
					}

					if(!Util.isVazioOrNulo(colecaoDebitoAjusteHelper) && contaRetificadaPeriodoAnteriorAjuste != null){

						if(quantidadeRubricas == 4 || quantidadeRubricas == 5 || quantidadeRubricas == 7 || quantidadeRubricas == 8
										|| quantidadeRubricas == 10 || quantidadeRubricas == 11 || quantidadeRubricas == 13
										|| quantidadeRubricas == 14){

							for(DebitoAjusteFaturamentoDesoHelper ajuste : colecaoDebitoAjusteHelper){

								arquivoRelacao5.append(Util.completarStringZeroEsquerda(grupo.getId().toString(), 3));
								arquivoRelacao5.append(Util.completarStringZeroEsquerda(movimentoRoteiroEmpresa.getImovel().getId()
												.toString(), 10));
								arquivoRelacao5.append(Util.completarStringZeroEsquerda(ajuste.getDebitoTipo().getId().toString(), 3));
								arquivoRelacao5.append(Util.completarStringZeroEsquerda(ajuste.getValorFaturado().toString(), 12));
								arquivoRelacao5.append(Util.completarStringZeroEsquerda(ajuste.getValorEnviado().toString(), 12));
								arquivoRelacao5.append(System.getProperty("line.separator"));
							}
						}
					}
				}
			}
		}

		// Gerar relatório arquivo texto
		gerarArquivo(arquivoRelacao5, nomeArquivoRelacao5);
	}

	private void verificarCobracaServicoErradaRelacao5(String rubrica, Short numeroPrestacaoRubrica, Short numeroPrestacaoDebitoRubrica,
					BigDecimal valorRubrica, Collection<DebitoAjusteFaturamentoDesoHelper> colecaoDebitoAjuste)
					throws ErroRepositorioException, ControladorException{

		// Verifica se truncou o valor do débito
		int indicePonto = valorRubrica.toString().indexOf(".");
		BigDecimal valorEnviado = BigDecimal.ZERO;

		if(!valorRubrica.toString().contains(".")){

			valorEnviado = new BigDecimal(valorRubrica.toString()).divide(new BigDecimal("100"));
		}else if(valorRubrica.toString().contains(".") && valorRubrica.toString().substring(indicePonto + 1).length() == 1){

			valorEnviado = new BigDecimal(valorRubrica.toString()).divide(new BigDecimal("10"));
		}

		if(valorEnviado.compareTo(BigDecimal.ZERO) == 1){

			DebitoTipo debitoTipo = (DebitoTipo) getControladorUtil().pesquisar(Util.obterInteger(rubrica.substring(0, 3)),
							DebitoTipo.class, false);
			DebitoAjusteFaturamentoDesoHelper ajusteHelper = new DebitoAjusteFaturamentoDesoHelper();
			ajusteHelper.setDebitoTipo(debitoTipo);
			ajusteHelper.setValorFaturado(valorRubrica);
			ajusteHelper.setValorEnviado(valorEnviado);
			colecaoDebitoAjuste.add(ajusteHelper);
		}
	}

	public void cancelarDebitosRotinaAjusteFaturamentoDeso() throws IOException, Exception{

		this.ejbCreate();
		System.out.println("INÍCIO CANCELAMENTO DÉBITOS A COBRAR AJUSTE ");
		Collection<Object[]> colecaoDebitosCancelar = repositorioFaturamento.pesquisarDebitoACobrarParaCancelarRotinaAjuste();

		String[] idDebitoCancelamento = null;
		for(Object[] dados : colecaoDebitosCancelar){

			idDebitoCancelamento = new String[1];
			idDebitoCancelamento[0] = dados[0].toString();
			getControladorFaturamento().cancelarDebitoACobrar(idDebitoCancelamento, Usuario.USUARIO_BATCH,
							Util.obterInteger(dados[1].toString()), false);
		}
		System.out.println("FIM CANCELAMENTO DÉBITOS A COBRAR AJUSTE");
	}

	/**
	 * @throws IOException
	 * @throws Exception
	 */
	public void executarAjusteConta(Integer idGrupoFaturamento) throws IOException, Exception{

		this.ejbCreate();
		System.out.println("INÍCIO AJUSTE FATURAMENTO CONTA - GRUPO  = " + idGrupoFaturamento);
		Collection colecaoOcorrenciaGeracaoPreFaturamento = new ArrayList<Imovel>();

		Integer anoMesReferencia = 201212;

		Collection colecaoDeDebitosCobrados = new ArrayList();
		colecaoDeDebitosCobrados.add(36420);
		colecaoDeDebitosCobrados.add(30821);
		colecaoDeDebitosCobrados.add(5947103);
		colecaoDeDebitosCobrados.add(5924952);
		colecaoDeDebitosCobrados.add(5820006);
		colecaoDeDebitosCobrados.add(5935610);
		colecaoDeDebitosCobrados.add(663050);
		colecaoDeDebitosCobrados.add(663115);
		colecaoDeDebitosCobrados.add(663123);
		colecaoDeDebitosCobrados.add(663069);

		SistemaParametro sistemaParametro = getControladorUtil().pesquisarParametrosDoSistema();

		Collection colecaoImovelMovimentoRoteiroEmpresa = repositorioMicromedicao.pesquisarMovimentoRoteiroEmpresa(anoMesReferencia,
						idGrupoFaturamento, colecaoDeDebitosCobrados);

		FaturamentoGrupo faturamentoGrupo = new FaturamentoGrupo();
		faturamentoGrupo.setId(idGrupoFaturamento);

		Iterator itt = colecaoImovelMovimentoRoteiroEmpresa.iterator();
		while(itt.hasNext()){
			Imovel imovel = (Imovel) itt.next();

			Collection collImovel = repositorioMicromedicao.pesquisarImoveisParaFaturamento(imovel.getId());
			Iterator iteratorColecaoImoveis = collImovel.iterator();
			if(iteratorColecaoImoveis.hasNext()){

				Object[] arrayImovel = (Object[]) iteratorColecaoImoveis.next();
				imovel = getControladorMicromedicao().obterImovelParaFaturamento(arrayImovel);

			}

			Collection colecaoCategorias = null;
			colecaoCategorias = getControladorImovel().obterQuantidadeEconomiasCategoria(imovel);
			imovel.setImovelSubcategorias(new HashSet());

			if(colecaoCategorias != null){ // necessário
				// iterar, pois o
				// retorno é um
				// ArrayList
				for(Iterator iterator = colecaoCategorias.iterator(); iterator.hasNext();){
					Categoria categoria = (Categoria) iterator.next();
					imovel.getImovelSubcategorias().add(categoria);
				}
			}

			// [SB0002-Verifica Situação Especial de Faturamento]
			// Verifica Situação Especial de Faturamento
			imovel.setFaturamentosSituacaoHistorico(new HashSet());
			Collection colecaoSituacaoEspecialFaturamento = repositorioFaturamento.pesquisarFaturamentoSituacaoHistoricoImovel(imovel
							.getId(), anoMesReferencia);
			if(colecaoSituacaoEspecialFaturamento != null && !colecaoSituacaoEspecialFaturamento.isEmpty()){
				for(Iterator iterator = colecaoSituacaoEspecialFaturamento.iterator(); iterator.hasNext();){
					FaturamentoSituacaoHistorico faturamentoSituacaoHistoricoImovel = (FaturamentoSituacaoHistorico) iterator.next();
					imovel.getFaturamentosSituacaoHistorico().add(faturamentoSituacaoHistoricoImovel);
				}
			}

			getControladorFaturamento().gerarContaPreFaturadaImovel(imovel, faturamentoGrupo, anoMesReferencia, sistemaParametro,
							colecaoCategorias, false, colecaoOcorrenciaGeracaoPreFaturamento);

		}

		System.out.println("FIM AJUSTE FATURAMENTO CONTA  - GRUPO  = " + idGrupoFaturamento);
	}

	public void executarAjusteRetificarContasRetificadasNaPrimeiraRotinaProblemaQuantidadeRubricas(Integer idFaturamentoGrupo,
					Integer anoMesReferencia) throws IOException, Exception{

		// Inicializa as instacias dos repositórios usados
		this.ejbCreate();

		// Declaração das variáveis
		Collection<MovimentoRoteiroEmpresa> colecaoMovimentoRoteiroEmpresa = null;
		Collection<DebitoAjusteFaturamentoDesoHelper> colecaoDebitoAjusteHelper = null;
		Collection<DebitoCobrado> colecaoDebitoCobradoContaRetificada = null;
		Collection<DebitoCobrado> colecaoDebitoCobradoContaNova = null;
		Collection<OperacaoContabilHelper> colecaoOperacaoContabil = new ArrayList<OperacaoContabilHelper>();
		ContaMotivoRetificacao contaMotivoRetificacao = new ContaMotivoRetificacao();
		contaMotivoRetificacao.setId(36);
		Conta contaRetificada = null;
		ContaHistorico contaRetificadaHistorico = null;
		Date dataGeracaoDebitoMarcacaoFixa = Util.converteStringParaDateHora("20/12/2012 20:00:00");
		Date dataInicialPrimeiroAjuste = Util.converteStringParaDateHora("27/11/2012 22:00:00");
		Collection<ContaHistorico> colecaoContaOriginalRetificadaOutroUsuario = new ArrayList<ContaHistorico>();
		Collection colecaoRelacaoTresDeso = new ArrayList();
		Boolean ignorarImovel = false;
		List<RubricaMovimentoRoteiroEmpresaHelper> colecaoRubricasImovel = null;

		// Obtém as rotas do grupo
		FiltroRota filtroRota = new FiltroRota();
		filtroRota.adicionarParametro(new ParametroSimples(FiltroRota.FATURAMENTO_GRUPO_ID, idFaturamentoGrupo));
		filtroRota.setCampoOrderBy(FiltroRota.ID_ROTA);
		Collection<Rota> colecaoRota = getControladorUtil().pesquisar(filtroRota, Rota.class.getName());

		// Processa o ajuste por rota
		for(Rota rota : colecaoRota){

			// Obtém os registros de movimento roteiro empresa que não tiveram serviços cobrados na
			// conta atual(Ref. 201211) devido ao erro do faturamento
			colecaoMovimentoRoteiroEmpresa = repositorioFaturamento.pesquisarDadosMovimentoRoteiroEmpresaAjuste2(anoMesReferencia, rota
							.getId());

			// Para cada registro de movimento roteiro empresa da rota
			for(Iterator iterator = colecaoMovimentoRoteiroEmpresa.iterator(); iterator.hasNext();){

				MovimentoRoteiroEmpresa movimentoRoteiroEmpresa = (MovimentoRoteiroEmpresa) iterator.next();
				colecaoDebitoAjusteHelper = new ArrayList<DebitoAjusteFaturamentoDesoHelper>();
				colecaoDebitoCobradoContaRetificada = new ArrayList<DebitoCobrado>();
				colecaoDebitoCobradoContaNova = new ArrayList<DebitoCobrado>();
				colecaoRubricasImovel = new ArrayList<RubricaMovimentoRoteiroEmpresaHelper>();

				if(movimentoRoteiroEmpresa.getDescricaoRubrica1() != null){

					RubricaMovimentoRoteiroEmpresaHelper rubricaHelper = new RubricaMovimentoRoteiroEmpresaHelper(1,
									movimentoRoteiroEmpresa.getDescricaoRubrica1(), movimentoRoteiroEmpresa.getNumeroPrestacaoRubrica1(),
									movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica1(), movimentoRoteiroEmpresa.getValorRubrica1());
					colecaoRubricasImovel.add(rubricaHelper);
				}

				if(movimentoRoteiroEmpresa.getDescricaoRubrica2() != null){

					RubricaMovimentoRoteiroEmpresaHelper rubricaHelper = new RubricaMovimentoRoteiroEmpresaHelper(2,
									movimentoRoteiroEmpresa.getDescricaoRubrica2(), movimentoRoteiroEmpresa.getNumeroPrestacaoRubrica2(),
									movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica2(), movimentoRoteiroEmpresa.getValorRubrica2());
					colecaoRubricasImovel.add(rubricaHelper);
				}

				if(movimentoRoteiroEmpresa.getDescricaoRubrica3() != null){

					RubricaMovimentoRoteiroEmpresaHelper rubricaHelper = new RubricaMovimentoRoteiroEmpresaHelper(3,
									movimentoRoteiroEmpresa.getDescricaoRubrica3(), movimentoRoteiroEmpresa.getNumeroPrestacaoRubrica3(),
									movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica3(), movimentoRoteiroEmpresa.getValorRubrica3());
					colecaoRubricasImovel.add(rubricaHelper);
				}

				if(movimentoRoteiroEmpresa.getDescricaoRubrica4() != null){

					RubricaMovimentoRoteiroEmpresaHelper rubricaHelper = new RubricaMovimentoRoteiroEmpresaHelper(4,
									movimentoRoteiroEmpresa.getDescricaoRubrica4(), movimentoRoteiroEmpresa.getNumeroPrestacaoRubrica4(),
									movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica4(), movimentoRoteiroEmpresa.getValorRubrica4());
					colecaoRubricasImovel.add(rubricaHelper);
				}

				if(movimentoRoteiroEmpresa.getDescricaoRubrica5() != null){

					RubricaMovimentoRoteiroEmpresaHelper rubricaHelper = new RubricaMovimentoRoteiroEmpresaHelper(5,
									movimentoRoteiroEmpresa.getDescricaoRubrica5(), movimentoRoteiroEmpresa.getNumeroPrestacaoRubrica5(),
									movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica5(), movimentoRoteiroEmpresa.getValorRubrica5());
					colecaoRubricasImovel.add(rubricaHelper);
				}

				if(movimentoRoteiroEmpresa.getDescricaoRubrica6() != null){

					RubricaMovimentoRoteiroEmpresaHelper rubricaHelper = new RubricaMovimentoRoteiroEmpresaHelper(6,
									movimentoRoteiroEmpresa.getDescricaoRubrica6(), movimentoRoteiroEmpresa.getNumeroPrestacaoRubrica6(),
									movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica6(), movimentoRoteiroEmpresa.getValorRubrica6());
					colecaoRubricasImovel.add(rubricaHelper);
				}

				if(movimentoRoteiroEmpresa.getDescricaoRubrica7() != null){

					RubricaMovimentoRoteiroEmpresaHelper rubricaHelper = new RubricaMovimentoRoteiroEmpresaHelper(7,
									movimentoRoteiroEmpresa.getDescricaoRubrica7(), movimentoRoteiroEmpresa.getNumeroPrestacaoRubrica7(),
									movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica7(), movimentoRoteiroEmpresa.getValorRubrica7());
					colecaoRubricasImovel.add(rubricaHelper);
				}

				if(movimentoRoteiroEmpresa.getDescricaoRubrica8() != null){

					RubricaMovimentoRoteiroEmpresaHelper rubricaHelper = new RubricaMovimentoRoteiroEmpresaHelper(8,
									movimentoRoteiroEmpresa.getDescricaoRubrica8(), movimentoRoteiroEmpresa.getNumeroPrestacaoRubrica8(),
									movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica8(), movimentoRoteiroEmpresa.getValorRubrica8());
					colecaoRubricasImovel.add(rubricaHelper);
				}

				if(movimentoRoteiroEmpresa.getDescricaoRubrica9() != null){

					RubricaMovimentoRoteiroEmpresaHelper rubricaHelper = new RubricaMovimentoRoteiroEmpresaHelper(9,
									movimentoRoteiroEmpresa.getDescricaoRubrica9(), movimentoRoteiroEmpresa.getNumeroPrestacaoRubrica9(),
									movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica9(), movimentoRoteiroEmpresa.getValorRubrica9());
					colecaoRubricasImovel.add(rubricaHelper);
				}

				if(movimentoRoteiroEmpresa.getDescricaoRubrica10() != null){

					RubricaMovimentoRoteiroEmpresaHelper rubricaHelper = new RubricaMovimentoRoteiroEmpresaHelper(10,
									movimentoRoteiroEmpresa.getDescricaoRubrica10(), movimentoRoteiroEmpresa.getNumeroPrestacaoRubrica10(),
									movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica10(), movimentoRoteiroEmpresa
													.getValorRubrica10());
					colecaoRubricasImovel.add(rubricaHelper);
				}

				if(movimentoRoteiroEmpresa.getDescricaoRubrica11() != null){

					RubricaMovimentoRoteiroEmpresaHelper rubricaHelper = new RubricaMovimentoRoteiroEmpresaHelper(11,
									movimentoRoteiroEmpresa.getDescricaoRubrica11(), movimentoRoteiroEmpresa.getNumeroPrestacaoRubrica11(),
									movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica11(), movimentoRoteiroEmpresa
													.getValorRubrica11());
					colecaoRubricasImovel.add(rubricaHelper);
				}

				if(movimentoRoteiroEmpresa.getDescricaoRubrica12() != null){

					RubricaMovimentoRoteiroEmpresaHelper rubricaHelper = new RubricaMovimentoRoteiroEmpresaHelper(12,
									movimentoRoteiroEmpresa.getDescricaoRubrica12(), movimentoRoteiroEmpresa.getNumeroPrestacaoRubrica12(),
									movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica12(), movimentoRoteiroEmpresa
													.getValorRubrica12());
					colecaoRubricasImovel.add(rubricaHelper);
				}

				if(movimentoRoteiroEmpresa.getDescricaoRubrica13() != null){

					RubricaMovimentoRoteiroEmpresaHelper rubricaHelper = new RubricaMovimentoRoteiroEmpresaHelper(13,
									movimentoRoteiroEmpresa.getDescricaoRubrica13(), movimentoRoteiroEmpresa.getNumeroPrestacaoRubrica13(),
									movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica13(), movimentoRoteiroEmpresa
													.getValorRubrica13());
					colecaoRubricasImovel.add(rubricaHelper);
				}

				if(movimentoRoteiroEmpresa.getDescricaoRubrica14() != null){

					RubricaMovimentoRoteiroEmpresaHelper rubricaHelper = new RubricaMovimentoRoteiroEmpresaHelper(14,
									movimentoRoteiroEmpresa.getDescricaoRubrica14(), movimentoRoteiroEmpresa.getNumeroPrestacaoRubrica14(),
									movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica14(), movimentoRoteiroEmpresa
													.getValorRubrica14());
					colecaoRubricasImovel.add(rubricaHelper);
				}

				if(movimentoRoteiroEmpresa.getDescricaoRubrica15() != null){

					RubricaMovimentoRoteiroEmpresaHelper rubricaHelper = new RubricaMovimentoRoteiroEmpresaHelper(15,
									movimentoRoteiroEmpresa.getDescricaoRubrica15(), movimentoRoteiroEmpresa.getNumeroPrestacaoRubrica15(),
									movimentoRoteiroEmpresa.getNumeroPrestacaoCobradaRubrica15(), movimentoRoteiroEmpresa
													.getValorRubrica15());
					colecaoRubricasImovel.add(rubricaHelper);
				}

				int quantidadeRubricas = colecaoRubricasImovel.size();
				int indexParar = 0;

				if(quantidadeRubricas == 13 || quantidadeRubricas == 14){

					indexParar = 11;
				}else if(quantidadeRubricas == 10 || quantidadeRubricas == 11){

					indexParar = 8;
				}else if(quantidadeRubricas == 7 || quantidadeRubricas == 8){

					indexParar = 5;
				}else if(quantidadeRubricas == 4 || quantidadeRubricas == 5){

					indexParar = 2;
				}

				// Para cada imóvel selecionado: criar popula a coleção com os débitos enviados a
				// menor
				if(indexParar > 0){

					// Obtém as contas do imóvel
					colecaoContaOriginalRetificadaOutroUsuario = repositorioFaturamento.pesquisarContaRetificadaOutroUsuarioPeriodo(
									movimentoRoteiroEmpresa.getImovel().getId(), anoMesReferencia, dataInicialPrimeiroAjuste);
					contaRetificada = repositorioFaturamento.pesquisarContaOriginalRetificadaRotinaQuantidadeRubricas(
									movimentoRoteiroEmpresa.getImovel().getId(), anoMesReferencia);
					contaRetificadaHistorico = repositorioFaturamento.pesquisarContaHistoricoOriginalRetificadaRotinaQuantidadeRubricas(
									movimentoRoteiroEmpresa.getImovel().getId(), anoMesReferencia);
					ignorarImovel = false;

					for(int i = 0; i < colecaoRubricasImovel.size(); i++){

						RubricaMovimentoRoteiroEmpresaHelper rubricaHelper = colecaoRubricasImovel.get(i);
						if(!ignorarImovel){

							obterDebitosParaAjusteValorTruncadoComQuantidadeRubricasErradas(rubricaHelper.getDescricao(), rubricaHelper
											.getNumeroPrestacao(), rubricaHelper.getNumeroPrestacaoCobrada(), rubricaHelper.getValor(),
											colecaoDebitoAjusteHelper, dataGeracaoDebitoMarcacaoFixa, colecaoOperacaoContabil,
											ignorarImovel, contaRetificada, contaRetificadaHistorico, colecaoRelacaoTresDeso,
											idFaturamentoGrupo, colecaoContaOriginalRetificadaOutroUsuario, movimentoRoteiroEmpresa
															.getImovel());
						}

						if(i == indexParar || ignorarImovel){
							break;
						}
					}

					// Caso seja necessário retificar a conta
					contaRetificada = repositorioFaturamento.pesquisarContaAtualRetificada(movimentoRoteiroEmpresa.getImovel().getId(),
									anoMesReferencia);

					if(contaRetificada != null && !Util.isVazioOrNulo(colecaoDebitoAjusteHelper)){

						// Obtém todos os débitos cobrados da conta na referência atual para
						// atualizar o valor do débito ajustado
						colecaoDebitoCobradoContaRetificada = repositorioFaturamento
										.pesquisarDebitosCobradoDaContaRetificada(contaRetificada.getId());

						if(!Util.isVazioOrNulo(colecaoDebitoCobradoContaRetificada)){

							for(DebitoCobrado debitoCobrado : colecaoDebitoCobradoContaRetificada){

								boolean encontrouDebito = false;
								boolean jaAjustouDebito = false;
								for(DebitoAjusteFaturamentoDesoHelper debitoAjusteHelper : colecaoDebitoAjusteHelper){

									if(debitoCobrado.getDebitoTipo().getId().equals(debitoAjusteHelper.getDebitoTipo().getId())
													&& debitoCobrado.getNumeroPrestacao() == debitoAjusteHelper.getNumeroPrestacao()
																	.shortValue()
													&& debitoCobrado.getNumeroPrestacaoDebito() == debitoAjusteHelper
																	.getNumeroPrestacaoDebito().shortValue()){

										for(DebitoCobrado debitoCobradoAjustado : colecaoDebitoCobradoContaNova){

											if(debitoAjusteHelper.getDebitoTipo().getId().equals(
															debitoCobradoAjustado.getDebitoTipo().getId())
															&& debitoAjusteHelper.getNumeroPrestacao().equals(
																			debitoCobradoAjustado.getNumeroPrestacao())
															&& debitoAjusteHelper.getNumeroPrestacaoDebito().equals(
																			debitoCobradoAjustado.getNumeroPrestacaoDebito())){
												jaAjustouDebito = true;
												break;
											}
										}

										if(jaAjustouDebito == false){

											/*
											 * Débito Cobrado da Contaoriginal que foi enviado com
											 * valor a menor
											 */
											DebitoCobrado debitoCobradoNovo = new DebitoCobrado();
											debitoCobradoNovo.setCodigoSetorComercial(debitoCobrado.getCodigoSetorComercial());
											debitoCobradoNovo.setNumeroQuadra(debitoCobrado.getNumeroQuadra());
											debitoCobradoNovo.setNumeroLote(debitoCobrado.getNumeroLote());
											debitoCobradoNovo.setNumeroSubLote(debitoCobrado.getNumeroSubLote());
											debitoCobradoNovo.setAnoMesReferenciaDebito(debitoCobrado.getAnoMesReferenciaDebito());
											debitoCobradoNovo.setAnoMesCobrancaDebito(debitoCobrado.getAnoMesCobrancaDebito());
											debitoCobradoNovo.setValorPrestacao(debitoAjusteHelper.getValorEnviado());
											debitoCobradoNovo.setNumeroPrestacao(debitoCobrado.getNumeroPrestacao());
											debitoCobradoNovo.setNumeroPrestacaoDebito(debitoCobrado.getNumeroPrestacaoDebito());
											debitoCobradoNovo.setUltimaAlteracao(new Date());
											debitoCobradoNovo.setDebitoCobrado(debitoCobrado.getDebitoCobrado());
											debitoCobradoNovo.setFinanciamentoTipo(debitoCobrado.getFinanciamentoTipo());
											debitoCobradoNovo.setQuadra(debitoCobrado.getQuadra());
											debitoCobradoNovo.setLocalidade(debitoCobrado.getLocalidade());
											debitoCobradoNovo.setDebitoTipo(debitoCobrado.getDebitoTipo());
											debitoCobradoNovo.setLancamentoItemContabil(debitoCobrado.getLancamentoItemContabil());
											debitoCobradoNovo.setParcelamentoGrupo(debitoCobrado.getParcelamentoGrupo());
											debitoCobradoNovo.setParcelamento(debitoCobrado.getParcelamento());
											colecaoDebitoCobradoContaNova.add(debitoCobradoNovo);
											encontrouDebito = true;
											break;
										}else{
											break;
										}
									}
								}

								if(encontrouDebito == false && jaAjustouDebito == false){

									// Débito Cobrado da Contaoriginal que foi enviado corretamente
									DebitoCobrado debitoCobradoNovo = new DebitoCobrado();
									debitoCobradoNovo.setCodigoSetorComercial(debitoCobrado.getCodigoSetorComercial());
									debitoCobradoNovo.setNumeroQuadra(debitoCobrado.getNumeroQuadra());
									debitoCobradoNovo.setNumeroLote(debitoCobrado.getNumeroLote());
									debitoCobradoNovo.setNumeroSubLote(debitoCobrado.getNumeroSubLote());
									debitoCobradoNovo.setAnoMesReferenciaDebito(debitoCobrado.getAnoMesReferenciaDebito());
									debitoCobradoNovo.setAnoMesCobrancaDebito(debitoCobrado.getAnoMesCobrancaDebito());
									debitoCobradoNovo.setValorPrestacao(debitoCobrado.getValorPrestacao());
									debitoCobradoNovo.setNumeroPrestacao(debitoCobrado.getNumeroPrestacao());
									debitoCobradoNovo.setNumeroPrestacaoDebito(debitoCobrado.getNumeroPrestacaoDebito());
									debitoCobradoNovo.setUltimaAlteracao(new Date());
									debitoCobradoNovo.setDebitoCobrado(debitoCobrado.getDebitoCobrado());
									debitoCobradoNovo.setFinanciamentoTipo(debitoCobrado.getFinanciamentoTipo());
									debitoCobradoNovo.setQuadra(debitoCobrado.getQuadra());
									debitoCobradoNovo.setLocalidade(debitoCobrado.getLocalidade());
									debitoCobradoNovo.setDebitoTipo(debitoCobrado.getDebitoTipo());
									debitoCobradoNovo.setLancamentoItemContabil(debitoCobrado.getLancamentoItemContabil());
									debitoCobradoNovo.setParcelamentoGrupo(debitoCobrado.getParcelamentoGrupo());
									debitoCobradoNovo.setParcelamento(debitoCobrado.getParcelamento());
									colecaoDebitoCobradoContaNova.add(debitoCobradoNovo);
								}
							}
						}

						// Carrega na conta os dados necessários para retificação
						contaRetificada = getControladorFaturamento().pesquisarContaRetificacao(contaRetificada.getId());

						// Obtendo os créditos realizados da conta
						Collection colecaoCreditoRealizado = getControladorFaturamento().obterCreditosRealizadosConta(contaRetificada);

						// Retificar a Conta <<Inclui>> [UC0150 Retificar Conta]
						getControladorFaturamento().retificarContaAjusteFaturamento(anoMesReferencia, contaRetificada,
										contaRetificada.getImovel(), colecaoDebitoCobradoContaNova,
										contaRetificada.getLigacaoAguaSituacao(), contaRetificada.getLigacaoEsgotoSituacao(),
										contaRetificada.getConsumoAgua().toString(), contaRetificada.getConsumoEsgoto().toString(),
										contaRetificada.getPercentualEsgoto().toString(), contaRetificada.getDataVencimentoConta(),
										contaMotivoRetificacao, Usuario.USUARIO_BATCH, contaRetificada.getConsumoTarifa(),
										colecaoCreditoRealizado);
					}
				}
			}
		}

		if(!Util.isVazioOrNulo(colecaoOperacaoContabil)){

			// Registra o lançamento contábil dos débitos a cobrar gerados
			this.getControladorContabil().registrarLancamentoContabil(colecaoOperacaoContabil);
		}

		// Gerar a tabela auxiliar de relação
		if(!Util.isVazioOrNulo(colecaoRelacaoTresDeso)){
			this.getControladorBatch().inserirColecaoObjetoParaBatch(colecaoRelacaoTresDeso);
		}
	}

	private void obterDebitosParaAjusteValorTruncadoComQuantidadeRubricasErradas(String rubrica, Short numeroPrestacaoRubrica,
					Short numeroPrestacaoDebitoRubrica, BigDecimal valorRubrica,
					Collection<DebitoAjusteFaturamentoDesoHelper> colecaoDebitosAjusteHelper, Date dataGeracaoDebitoMarcacaoFixa,
					Collection<OperacaoContabilHelper> colecaoOperacaoContabil, Boolean ignorarImovel, Conta contaRetificada,
					ContaHistorico contaRetificadaHistorico, Collection<RelacaoTresDeso> colecaoRelacaoTresDeso,
					Integer idFaturamentoGrupo, Collection<ContaHistorico> colecaoContaRetificacoesOutroUsuario, Imovel imovel)
					throws ErroRepositorioException, ControladorException{

		// Verifica se truncou o valor do débito
		int indicePonto = valorRubrica.toString().indexOf(".");
		BigDecimal valorEnviado = BigDecimal.ZERO;
		boolean debitoAMenor = false;
		DebitoAjusteFaturamentoDesoHelper debitoAjusteHelper = new DebitoAjusteFaturamentoDesoHelper();
		Integer idDebitoTipo = Util.obterInteger(rubrica.substring(0, 3));

		// Obtém o valor do débito cobrado com erro
		if(!valorRubrica.toString().contains(".")){

			valorEnviado = new BigDecimal(valorRubrica.toString()).divide(new BigDecimal("100"));
			debitoAMenor = true;
		}else if(valorRubrica.toString().contains(".") && valorRubrica.toString().substring(indicePonto + 1).length() == 1){

			valorEnviado = new BigDecimal(valorRubrica.toString()).divide(new BigDecimal("10"));
			debitoAMenor = true;
		}

		if(debitoAMenor){

			Collection<Conta> colecaoConta = null;
			if(contaRetificada != null){

				imovel = contaRetificada.getImovel();
				FiltroConta filtroConta = new FiltroConta();
				filtroConta.adicionarParametro(new ParametroSimples(FiltroConta.IMOVEL_ID, imovel.getId()));
				filtroConta.adicionarParametro(new ParametroSimples(FiltroConta.REFERENCIA, 201211));
				filtroConta.adicionarCaminhoParaCarregamentoEntidade(FiltroConta.DEBITO_CREDITO_SITUACAO_ATUAL);

				colecaoConta = this.getControladorUtil().pesquisar(filtroConta, Conta.class.getName());
			}

			if(contaRetificada == null && contaRetificadaHistorico != null){

				ignorarImovel = true;
				RelacaoMotivoNaoGeracao relacaoMotivoNaoGeracaoContaHistoricoRetificada = new RelacaoMotivoNaoGeracao();
				relacaoMotivoNaoGeracaoContaHistoricoRetificada.setIdImovel(imovel.getId());
				relacaoMotivoNaoGeracaoContaHistoricoRetificada.setMotivo("IMOVEL COM RETIFICADA NO HISTORICO");
				relacaoMotivoNaoGeracaoContaHistoricoRetificada.setUltimaAlteracao(new Date());

				this.getControladorUtil().inserir(relacaoMotivoNaoGeracaoContaHistoricoRetificada);
			}

			if(!Util.isVazioOrNulo(colecaoContaRetificacoesOutroUsuario)){

				ignorarImovel = true;
				RelacaoMotivoNaoGeracao relacaoMotivoNaoGeracaoContaRetificadaOutroUsuario = new RelacaoMotivoNaoGeracao();
				relacaoMotivoNaoGeracaoContaRetificadaOutroUsuario.setIdImovel(imovel.getId());
				relacaoMotivoNaoGeracaoContaRetificadaOutroUsuario.setMotivo("IMOVEL COM CONTA RETIFICADA POR OUTROS USUÁRIOS");
				relacaoMotivoNaoGeracaoContaRetificadaOutroUsuario.setUltimaAlteracao(new Date());

				this.getControladorUtil().inserir(relacaoMotivoNaoGeracaoContaRetificadaOutroUsuario);
			}

			if(!Util.isVazioOrNulo(colecaoConta)){
				Conta conta = (Conta) Util.retonarObjetoDeColecao(colecaoConta);

				DebitoCreditoSituacao debitoCreditoSituacaoAtual = conta.getDebitoCreditoSituacaoAtual();

				if(debitoCreditoSituacaoAtual != null){
					Integer idDebitoCreditoSituacao = debitoCreditoSituacaoAtual.getId();

					if(idDebitoCreditoSituacao.intValue() == DebitoCreditoSituacao.INCLUIDA.intValue()){
						ignorarImovel = true;

						RelacaoMotivoNaoGeracao relacaoMotivoNaoGeracao = new RelacaoMotivoNaoGeracao();
						relacaoMotivoNaoGeracao.setIdImovel(imovel.getId());
						relacaoMotivoNaoGeracao.setMotivo("IMOVEL COM CONTA INCLUIDA");
						relacaoMotivoNaoGeracao.setUltimaAlteracao(new Date());

						this.getControladorUtil().inserir(relacaoMotivoNaoGeracao);
					}
				}
			}else{

				ContaHistorico contaHistorico = this.repositorioFaturamento.pesquisarContaEmHistoricoIncluidaCanceladaParcelada(imovel
								.getId(), 201211);

				if(contaHistorico != null){
					DebitoCreditoSituacao debitoCreditoSituacaoAtual = contaHistorico.getDebitoCreditoSituacaoAtual();

					if(debitoCreditoSituacaoAtual != null){
						Integer idDebitoCreditoSituacao = debitoCreditoSituacaoAtual.getId();

						String motivo = null;

						if(idDebitoCreditoSituacao.intValue() == DebitoCreditoSituacao.INCLUIDA.intValue()){
							motivo = "IMOVEL COM CONTA INCLUIDA";
						}else if(idDebitoCreditoSituacao.intValue() == DebitoCreditoSituacao.CANCELADA.intValue()){
							motivo = "IMOVEL COM CONTA CANCELADA";
						}else if(idDebitoCreditoSituacao.intValue() == DebitoCreditoSituacao.PARCELADA.intValue()){
							motivo = "IMOVEL COM CONTA PARCELADA";
						}

						ignorarImovel = true;

						RelacaoMotivoNaoGeracao relacaoMotivoNaoGeracao = new RelacaoMotivoNaoGeracao();
						relacaoMotivoNaoGeracao.setIdImovel(imovel.getId());
						relacaoMotivoNaoGeracao.setMotivo(motivo);
						relacaoMotivoNaoGeracao.setUltimaAlteracao(new Date());

						this.getControladorUtil().inserir(relacaoMotivoNaoGeracao);
					}
				}
			}

			if(!ignorarImovel && contaRetificada != null){

				/*
				 * Popula a coleção com os débitos enviados a menor para posterior retificação da
				 * conta caso necessário
				 */
				BigDecimal valorDebitosContaOriginal = repositorioFaturamento.pesquisarSomatorioDebitosCobradoDaContaRetificada(
								contaRetificada.getId(), idDebitoTipo, numeroPrestacaoRubrica, numeroPrestacaoDebitoRubrica);

				debitoAjusteHelper.setDebitoTipo((DebitoTipo) getControladorUtil().pesquisar(Util.obterInteger(rubrica.substring(0, 3)),
								DebitoTipo.class, false));

				debitoAjusteHelper.setValorEnviado(valorEnviado);
				debitoAjusteHelper.setNumeroPrestacao(numeroPrestacaoRubrica);
				debitoAjusteHelper.setNumeroPrestacaoDebito(numeroPrestacaoDebitoRubrica);
				debitoAjusteHelper.setValorRestanteSerCobrado(valorDebitosContaOriginal.subtract(valorEnviado));
				debitoAjusteHelper.setValorFaturado(valorRubrica);
				colecaoDebitosAjusteHelper.add(debitoAjusteHelper);

				RelacaoTresDeso relacaoTresDeso = new RelacaoTresDeso();
				relacaoTresDeso.setIdFaturamentoGrupo(idFaturamentoGrupo);
				relacaoTresDeso.setIdImovel(imovel.getId());
				relacaoTresDeso.setIdDebitoTipo(debitoAjusteHelper.getDebitoTipo().getId());
				relacaoTresDeso.setValorRestanteSerCobrado(debitoAjusteHelper.getValorRestanteSerCobrado());
				relacaoTresDeso.setValorEnviado(debitoAjusteHelper.getValorEnviado());
				relacaoTresDeso.setUltimaAlteracao(new Date());

				colecaoRelacaoTresDeso.add(relacaoTresDeso);

				// Carrega os dados do imóvel da conta
				imovel = this.getControladorImovel().pesquisarImovel(imovel.getId());

				// Gera o débito a cobrar com o valor restante a ser cobrado
				CobrancaForma cobrancaForma = new CobrancaForma();
				cobrancaForma.setId(CobrancaForma.COBRANCA_EM_CONTA);

				DebitoACobrar debitoACobrar = new DebitoACobrar();
				debitoACobrar.setDebitoTipo(debitoAjusteHelper.getDebitoTipo());
				debitoACobrar.setValorDebito(debitoAjusteHelper.getValorRestanteSerCobrado());
				debitoACobrar.setImovel(imovel);
				debitoACobrar.setGeracaoDebito(dataGeracaoDebitoMarcacaoFixa);
				debitoACobrar.setLocalidade(imovel.getLocalidade());
				debitoACobrar.setQuadra(imovel.getQuadra());
				debitoACobrar.setAnoMesReferenciaDebito(201211);
				debitoACobrar.setLancamentoItemContabil(debitoAjusteHelper.getDebitoTipo().getLancamentoItemContabil());
				debitoACobrar.setFinanciamentoTipo(debitoAjusteHelper.getDebitoTipo().getFinanciamentoTipo());
				debitoACobrar.setCobrancaForma(cobrancaForma);
				debitoACobrar.setUltimaAlteracao(new Date());
				debitoACobrar.setAnoMesCobrancaDebito(201301);

				OperacaoContabilHelper helper = new OperacaoContabilHelper();
				helper.setObjetoOrigem(this.getControladorFaturamento().inserirDebitoACobrarSemRegistrarLancamentoContabil(1,
								debitoACobrar, null, imovel, null, null, Usuario.USUARIO_BATCH, false, null, null, null));
				helper.setOperacaoContabil(OperacaoContabil.INCLUIR_DEBITO_A_COBRAR);

				colecaoOperacaoContabil.add(helper);
			}
		}
	}

	public void cancelarDebitosDuplicadosSegundaViaAjusteFaturamentoDeso() throws IOException, Exception{

		this.ejbCreate();
		System.out.println("INÍCIO BOLETO COBRANCA BANCARIA AJUSTE ");
		// Collection<Object[]> colecaoDebitosCancelar =
		// repositorioFaturamento.pesquisarDebitoACobrarParaCancelarDuplicados();
		// Collection<Object[]> colecaoDebitosCancelar2 =
		// repositorioFaturamento.pesquisarDebitoACobrarParaCancelarDuplicados2();
		// Collection<Integer> idsDebitosCancelados = new ArrayList<Integer>();
		//
		// if(!Util.isVazioOrNulo(colecaoDebitosCancelar)){
		//
		// String[] idDebitoCancelamento = null;
		// for(Object[] dados : colecaoDebitosCancelar){
		//
		// idDebitoCancelamento = new String[1];
		// idDebitoCancelamento[0] = dados[0].toString();
		// getControladorFaturamento().cancelarDebitoACobrar(idDebitoCancelamento,
		// Usuario.USUARIO_BATCH,
		// Util.obterInteger(dados[1].toString()), false);
		// idsDebitosCancelados.add(Util.obterInteger(dados[0].toString()));
		// }
		//
		// System.out.println("FIM CANCELAMENTO PRIMEIRA PARTE DÉBITOS A COBRAR AJUSTE: " +
		// colecaoDebitosCancelar.size());
		// }
		//
		// if(!Util.isVazioOrNulo(colecaoDebitosCancelar2)){
		//
		// String[] idDebitoCancelamento = null;
		// for(Object[] dados2 : colecaoDebitosCancelar2){
		//
		// idDebitoCancelamento = new String[1];
		// idDebitoCancelamento[0] = dados2[0].toString();
		// if(!idsDebitosCancelados.contains(Util.obterInteger(dados2[0].toString()))){
		//
		// getControladorFaturamento().cancelarDebitoACobrar(idDebitoCancelamento,
		// Usuario.USUARIO_BATCH,
		// Util.obterInteger(dados2[1].toString()), false);
		// }
		// }
		//
		// System.out.println("FIM CANCELAMENTO SEGUNDA PARTE DÉBITOS A COBRAR AJUSTE: "
		// + (colecaoDebitosCancelar2.size() - idsDebitosCancelados.size()));
		// }
		//
		// StringBuilder arquivoConferencia = new StringBuilder();
		// String nomeArquivo = "FIM_ROTINA_CANCELAMENTO_DEBITOS.TXT";
		// arquivoConferencia.append("FINALIZADA ROTINA DE CANCELAMENTO!");

		this.getControladorCobranca().gerarBoletoCobrancaBancaria(null, 201200773, 1, Usuario.USUARIO_BATCH);

		System.out.println("FIM BOLETO COBRANCA BANCARIA AJUSTE ");

	}

	private ControladorArrecadacaoLocal getControladorArrecadacao(){

		ControladorArrecadacaoLocalHome localHome = null;
		ControladorArrecadacaoLocal local = null;

		// pega a instância do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorArrecadacaoLocalHome) locator.getLocalHomePorEmpresa(ConstantesJNDI.CONTROLADOR_ARRECADACAO_SEJB);

			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}
	}

	private ControladorCobrancaLocal getControladorCobranca(){

		ControladorCobrancaLocalHome localHome = null;
		ControladorCobrancaLocal local = null;

		// pega a instância do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorCobrancaLocalHome) locator.getLocalHomePorEmpresa(ConstantesJNDI.CONTROLADOR_COBRANCA_SEJB);

			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}
	}

	public void executarAjusteBaixarPagamentosAMaior() throws IOException, Exception{

		// Inicializa as instacias dos repositórios usados
		this.ejbCreate();

		// Identificar pagamentos não classificados a maior;
		Collection<Integer> colecaoIdsPagamentosAMaior = repositorioFaturamento.pesquisarIdsPagamentosAMaiorParaClassificar();
		PagamentoSituacao pagamentoSituacaoAtual = (PagamentoSituacao) getControladorUtil().pesquisar(PagamentoSituacao.VALOR_A_BAIXAR,
						PagamentoSituacao.class, false);
		PagamentoSituacao pagamentoSituacaoAnterior = (PagamentoSituacao) getControladorUtil().pesquisar(
						PagamentoSituacao.PAGAMENTO_A_MAIOR, PagamentoSituacao.class, false);
		RelacaoTresDeso relacaoTresDeso = null;
		RelacaoMotivoNaoGeracao relacaoMotivoNaoGeracao = null;
		Collection<RelacaoTresDeso> colecaoRelacaoTresDeso = new ArrayList<RelacaoTresDeso>();
		Collection<RelacaoMotivoNaoGeracao> colecaoRelacaoMotivoNaoGeracao = new ArrayList<RelacaoMotivoNaoGeracao>();
		ContaMotivoRetificacao contaMotivoRetificacao = new ContaMotivoRetificacao();
		contaMotivoRetificacao.setId(36);

		for(Object idPagamento : colecaoIdsPagamentosAMaior){

			// Caso o pagamento seja de uma conta retificada pelas rotinas de ajuste (mot, usuário,
			// referência)
			FiltroPagamento filtroPagamento = new FiltroPagamento();
			filtroPagamento.adicionarParametro(new ParametroSimples(FiltroPagamento.ID, Util.obterInteger(idPagamento.toString())));

			filtroPagamento.adicionarCaminhoParaCarregamentoEntidade("debitoTipo");
			filtroPagamento.adicionarCaminhoParaCarregamentoEntidade("documentoTipo");
			filtroPagamento.adicionarCaminhoParaCarregamentoEntidade("localidade");
			filtroPagamento.adicionarCaminhoParaCarregamentoEntidade("imovel");
			filtroPagamento.adicionarCaminhoParaCarregamentoEntidade("imovel.localidade");
			filtroPagamento.adicionarCaminhoParaCarregamentoEntidade("imovel.setorComercial");
			filtroPagamento.adicionarCaminhoParaCarregamentoEntidade("imovel.quadra");
			filtroPagamento.adicionarCaminhoParaCarregamentoEntidade("cliente");
			filtroPagamento.adicionarCaminhoParaCarregamentoEntidade("avisoBancario");
			filtroPagamento.adicionarCaminhoParaCarregamentoEntidade("arrecadacaoForma");
			filtroPagamento.adicionarCaminhoParaCarregamentoEntidade("avisoBancario.arrecadador.cliente");
			filtroPagamento.adicionarCaminhoParaCarregamentoEntidade("conta");
			filtroPagamento.adicionarCaminhoParaCarregamentoEntidade("guiaPagamentoGeral");
			filtroPagamento.adicionarCaminhoParaCarregamentoEntidade("debitoACobrar");
			filtroPagamento.adicionarCaminhoParaCarregamentoEntidade("debitoACobrar.debitoTipo");

			Collection colecaoPagamentos = getControladorUtil().pesquisar(filtroPagamento, Pagamento.class.getName());

			Pagamento pagamento = (Pagamento) Util.retonarObjetoDeColecao(colecaoPagamentos);
			Imovel imovel = pagamento.getImovel();

			ContaHistorico contaOriginal = repositorioFaturamento.pesquisarContaOriginalRetificada(imovel.getId(), 201211);

			if(contaOriginal == null){

				relacaoMotivoNaoGeracao = new RelacaoMotivoNaoGeracao();
				relacaoMotivoNaoGeracao.setIdImovel(imovel.getId());
				relacaoMotivoNaoGeracao.setMotivo("CONTA ORIGINAL NÃO ENCONTRADA");
				relacaoMotivoNaoGeracao.setUltimaAlteracao(new Date());
				colecaoRelacaoMotivoNaoGeracao.add(relacaoMotivoNaoGeracao);
				continue;
			}

			// Atualizar a situação do pagamento para valor a baixar
			pagamento.setAnoMesReferenciaPagamento(201211);
			pagamento.setValorPagamento(pagamento.getValorPagamento());
			pagamento.setDataPagamento(pagamento.getDataPagamento());
			pagamento.setPagamentoSituacaoAtual(pagamentoSituacaoAtual);
			pagamento.setPagamentoSituacaoAnterior(pagamentoSituacaoAnterior);
			pagamento.setDebitoTipo(null);
			pagamento.setGuiaPagamentoGeral(null);
			pagamento.setNumeroPrestacao(null);
			pagamento.setDebitoACobrar(null);
			pagamento.setLocalidade(imovel.getLocalidade());
			DocumentoTipo documentoTipo = new DocumentoTipo();
			documentoTipo.setId(Integer.valueOf(DocumentoTipo.CONTA));
			pagamento.setDocumentoTipo(documentoTipo);
			pagamento.setImovel(imovel);
			pagamento.setCliente(null);

			// Gerar relação do pagamento baixado [Verificar Devolução de Valor]
			BigDecimal valorResidualGeradoAjuste = repositorioFaturamento
							.pesquisarSomatorioDebitosACobrarFaturadosGeradosRotinaAjuste(imovel.getId());

			if(valorResidualGeradoAjuste == null){

				valorResidualGeradoAjuste = BigDecimal.ZERO;
			}

			// Caso o valor do pagamento seja igual ao valor da conta original
			if(pagamento.getValorPagamento().compareTo(contaOriginal.getValorTotal()) == 0){

				Integer idConta = repositorioFaturamento.pesquisarExistenciaConta(imovel, 201211);
				if(idConta == null){

					relacaoMotivoNaoGeracao = new RelacaoMotivoNaoGeracao();
					relacaoMotivoNaoGeracao.setIdImovel(imovel.getId());
					relacaoMotivoNaoGeracao.setMotivo("CONTA NÃO ENCONTRADA!");
					relacaoMotivoNaoGeracao.setUltimaAlteracao(new Date());
					colecaoRelacaoMotivoNaoGeracao.add(relacaoMotivoNaoGeracao);
					continue;
				}

				// Verifica se vai gerar devolução
				if(valorResidualGeradoAjuste.compareTo(BigDecimal.ZERO) == 1){

					relacaoTresDeso = new RelacaoTresDeso();

					// Imóvel
					relacaoTresDeso.setIdImovel(imovel.getId());

					// Valor da Devolução
					relacaoTresDeso.setValorRestanteSerCobrado(valorResidualGeradoAjuste);
					relacaoTresDeso.setUltimaAlteracao(new Date());
					colecaoRelacaoTresDeso.add(relacaoTresDeso);
				}

				// Retificar a conta para igualar a valor do pagamento
				Conta contaAtual = getControladorFaturamento().pesquisarContaRetificacao(idConta);
				Collection<DebitoCobradoHistorico> colecaoDebitoCobrado = repositorioFaturamento
								.pesquisarDebitosCobradoDaContaOriginal(contaOriginal.getId());
				Collection<DebitoCobrado> colecaoDebitoCobradoContaNova = new ArrayList<DebitoCobrado>();

				for(DebitoCobradoHistorico debitoCobradoHistorico : colecaoDebitoCobrado){

					DebitoCobrado debitoCobrado = new DebitoCobrado();
					debitoCobrado.setCodigoSetorComercial(debitoCobradoHistorico.getCodigoSetorComercial());
					debitoCobrado.setNumeroQuadra(debitoCobradoHistorico.getNumeroQuadra());
					debitoCobrado.setNumeroLote(debitoCobradoHistorico.getNumeroLote());
					debitoCobrado.setNumeroSubLote(debitoCobradoHistorico.getNumeroSubLote());
					debitoCobrado.setAnoMesReferenciaDebito(debitoCobradoHistorico.getAnoMesReferenciaDebito());
					debitoCobrado.setAnoMesCobrancaDebito(debitoCobradoHistorico.getAnoMesCobrancaDebito());
					debitoCobrado.setValorPrestacao(debitoCobradoHistorico.getValorPrestacao());
					debitoCobrado.setNumeroPrestacao(debitoCobradoHistorico.getNumeroPrestacao());
					debitoCobrado.setNumeroPrestacaoDebito(debitoCobradoHistorico.getNumeroPrestacaoDebito());
					debitoCobrado.setUltimaAlteracao(new Date());
					debitoCobrado.setDebitoCobrado(debitoCobradoHistorico.getDebitoCobrado());
					debitoCobrado.setFinanciamentoTipo(debitoCobradoHistorico.getFinanciamentoTipo());
					debitoCobrado.setQuadra(debitoCobradoHistorico.getQuadra());
					debitoCobrado.setLocalidade(debitoCobradoHistorico.getLocalidade());
					debitoCobrado.setDebitoTipo(debitoCobradoHistorico.getDebitoTipo());
					debitoCobrado.setLancamentoItemContabil(debitoCobradoHistorico.getLancamentoItemContabil());
					debitoCobrado.setParcelamentoGrupo(debitoCobradoHistorico.getParcelamentoGrupo());
					debitoCobrado.setParcelamento(debitoCobradoHistorico.getParcelamento());
					colecaoDebitoCobradoContaNova.add(debitoCobrado);
				}

				// Obtendo os créditos realizados da conta
				Collection colecaoCreditoRealizado = getControladorFaturamento().obterCreditosRealizadosConta(contaAtual);

				// Retifica a conta para baixar pagamento
				getControladorFaturamento().retificarContaAjusteFaturamento(201211, contaAtual, contaAtual.getImovel(),
								colecaoDebitoCobradoContaNova, contaAtual.getLigacaoAguaSituacao(), contaAtual.getLigacaoEsgotoSituacao(),
								contaAtual.getConsumoAgua().toString(), contaAtual.getConsumoEsgoto().toString(),
								contaAtual.getPercentualEsgoto().toString(), contaAtual.getDataVencimentoConta(), contaMotivoRetificacao,
								Usuario.USUARIO_BATCH, contaAtual.getConsumoTarifa(), colecaoCreditoRealizado);

			}else if(pagamento.getValorPagamento().compareTo(contaOriginal.getValorTotal()) == 1){

				relacaoMotivoNaoGeracao = new RelacaoMotivoNaoGeracao();
				relacaoMotivoNaoGeracao.setIdImovel(imovel.getId());
				relacaoMotivoNaoGeracao.setMotivo("VALOR DO PAGAMENTO MAIOR QUE VALOR DA CONTA ORIGINAL");
				relacaoMotivoNaoGeracao.setUltimaAlteracao(new Date());
				colecaoRelacaoMotivoNaoGeracao.add(relacaoMotivoNaoGeracao);

				// Classificar o pagamento ("Forçar a baixa")
				getControladorArrecadacao().atualizarPagamento(pagamento, Usuario.USUARIO_BATCH, null, null, null);
			}else if(pagamento.getValorPagamento().compareTo(contaOriginal.getValorTotal()) == -1){

				relacaoMotivoNaoGeracao = new RelacaoMotivoNaoGeracao();
				relacaoMotivoNaoGeracao.setIdImovel(imovel.getId());
				relacaoMotivoNaoGeracao.setMotivo("VALOR DO PAGAMENTO MENOR QUE VALOR DA CONTA ORIGINAL");
				relacaoMotivoNaoGeracao.setUltimaAlteracao(new Date());
				colecaoRelacaoMotivoNaoGeracao.add(relacaoMotivoNaoGeracao);

				// Classificar o pagamento ("Forçar a baixa")
				getControladorArrecadacao().atualizarPagamento(pagamento, Usuario.USUARIO_BATCH, null, null, null);
			}
		}

		// Inserir na tabela de relação
		getControladorBatch().inserirColecaoObjetoParaBatch((Collection) colecaoRelacaoTresDeso);
		// Inserir na tabela de relação
		getControladorBatch().inserirColecaoObjetoParaBatch((Collection) colecaoRelacaoMotivoNaoGeracao);
	}

	/**
	 * @throws IOException
	 * @throws Exception
	 */

	// public void executarAjusteContasRetificar(Integer idGrupo) throws IOException, Exception{
	//
	// // Inicializa as instacias dos repositórios usados
	// this.ejbCreate();
	//
	// String nomeArquivo = "AjusteFatRetificaConta_g" + idGrupo + ".TXT";
	// StringBuilder arquivoConferencia = new StringBuilder();
	//
	// arquivoConferencia.append("________________________________________________________________________________________________");
	// arquivoConferencia.append(System.getProperty("line.separator"));
	//
	// Collection colecaoIdsConta = repositorioFaturamento.pesquisarIdsContaPorGrupo(idGrupo);
	//
	// ContaMotivoRetificacao contaMotivoRetificacao = new ContaMotivoRetificacao();
	// contaMotivoRetificacao.setId(155);
	//
	// for(Object idConta : colecaoIdsConta){
	//
	// Conta contaAtual =
	// getControladorFaturamento().pesquisarContaRetificacao(Util.obterInteger(idConta.toString()));
	//
	// Collection<DebitoCobrado> colecaoDebitoCobradoConta =
	// repositorioFaturamento.pesquisarDebitosCobradoDaConta(contaAtual.getId(),
	// null, null, null);
	//
	// Collection<DebitoCobrado> colecaoDebitoCobradoContaRetificar = new
	// ArrayList<DebitoCobrado>();
	// if(!Util.isVazioOrNulo(colecaoDebitoCobradoConta)){
	//
	// for(DebitoCobrado debitoCobrado : colecaoDebitoCobradoConta){
	//
	// if(!debitoCobrado.getDebitoTipo().getId().equals(DebitoTipo.ATUALIZACAO_MONETARIA)
	// && !debitoCobrado.getDebitoTipo().getId().equals(DebitoTipo.MULTA_IMPONTUALIDADE)
	// && !debitoCobrado.getDebitoTipo().getId().equals(DebitoTipo.JUROS_MORA)){
	//
	// debitoCobrado.setConta(null);
	// colecaoDebitoCobradoContaRetificar.add(debitoCobrado);
	//
	// }
	//
	// }
	// }
	//
	// Integer idContaRetificada =
	// getControladorFaturamento().retificarContaAjusteFaturamento(contaAtual.getReferencia(),
	// contaAtual,
	// contaAtual.getImovel(), colecaoDebitoCobradoContaRetificar,
	// contaAtual.getLigacaoAguaSituacao(),
	// contaAtual.getLigacaoEsgotoSituacao(), contaAtual.getConsumoAgua().toString(),
	// contaAtual.getConsumoEsgoto().toString(), contaAtual.getPercentualEsgoto().toString(),
	// contaAtual.getDataVencimentoConta(), contaMotivoRetificacao, Usuario.USUARIO_BATCH,
	// contaAtual.getConsumoTarifa());
	//
	// Conta contaAjustada = (Conta) getControladorUtil().pesquisar(idContaRetificada, Conta.class,
	// false);
	//
	// arquivoConferencia.append("contaAtual = " + contaAjustada.getId() + " - " +
	// "Quantidade debitos antes = "
	// + colecaoDebitoCobradoConta.size() + " Quantidade débitos após = " +
	// colecaoDebitoCobradoContaRetificar.size());
	// arquivoConferencia.append(System.getProperty("line.separator"));
	//
	// }
	//
	// arquivoConferencia.append("________________________________________________________________________________________________");
	// this.gerarArquivo(arquivoConferencia, nomeArquivo);
	//
	// }

	public void executarAjusteContasRetificar(Integer idGrupo) throws IOException, Exception{

		// // Inicializa as instacias dos repositórios usados
		// this.ejbCreate();
		//
		// String nomeArquivo = "AjusteFatRetificaConta_g" + idGrupo + ".TXT";
		// StringBuilder arquivoConferencia = new StringBuilder();
		//
		// arquivoConferencia.append("________________________________________________________________________________________________");
		// arquivoConferencia.append(System.getProperty("line.separator"));
		//
		// Collection colecaoIdsConta = repositorioFaturamento.pesquisarIdsContaPorGrupo(idGrupo);
		//
		// ContaMotivoRetificacao contaMotivoRetificacao = new ContaMotivoRetificacao();
		// contaMotivoRetificacao.setId(35);
		//
		// for(Object idConta : colecaoIdsConta){
		//
		// boolean primeiraVez = true;
		//
		// Conta contaAtual =
		// getControladorFaturamento().pesquisarContaRetificacao(Util.obterInteger(idConta.toString()));
		//
		// Collection<DebitoCobrado> colecaoDebitoCobradoConta =
		// repositorioFaturamento.pesquisarDebitosCobradoDaConta(contaAtual.getId(),
		// null, null, null);
		//
		// Collection<DebitoCobrado> colecaoDebitoCobradoContaRetificar = new
		// ArrayList<DebitoCobrado>();
		// if(!Util.isVazioOrNulo(colecaoDebitoCobradoConta)){
		//
		// for(DebitoCobrado debitoCobrado : colecaoDebitoCobradoConta){
		// if(!debitoCobrado.getDebitoTipo().getId().equals(64)){
		//
		// debitoCobrado.setConta(null);
		// colecaoDebitoCobradoContaRetificar.add(debitoCobrado);
		//
		// }else{
		//
		// if(primeiraVez){
		//
		// primeiraVez = false;
		// // debitoCobrado.getValorPrestacao().compareTo(new
		// // BigDecimal("23,83"))){
		// if(debitoCobrado.getValorPrestacao().compareTo(Util.formatarMoedaRealparaBigDecimal("23,83"))
		// == 0){
		// BigDecimal valor = Util.formatarMoedaRealparaBigDecimal("23,27");
		//
		// DebitoCobrado debitoCobradoNovo = new DebitoCobrado();
		// debitoCobradoNovo.setCodigoSetorComercial(debitoCobrado.getCodigoSetorComercial());
		// debitoCobradoNovo.setNumeroQuadra(debitoCobrado.getNumeroQuadra());
		// debitoCobradoNovo.setNumeroLote(debitoCobrado.getNumeroLote());
		// debitoCobradoNovo.setNumeroSubLote(debitoCobrado.getNumeroSubLote());
		// debitoCobradoNovo.setAnoMesReferenciaDebito(debitoCobrado.getAnoMesReferenciaDebito());
		// debitoCobradoNovo.setAnoMesCobrancaDebito(debitoCobrado.getAnoMesCobrancaDebito());
		// debitoCobradoNovo.setValorPrestacao(valor);
		// debitoCobradoNovo.setNumeroPrestacao(debitoCobrado.getNumeroPrestacao());
		// debitoCobradoNovo.setNumeroPrestacaoDebito(debitoCobrado.getNumeroPrestacaoDebito());
		// debitoCobradoNovo.setUltimaAlteracao(new Date());
		// debitoCobradoNovo.setDebitoCobrado(debitoCobrado.getDebitoCobrado());
		// debitoCobradoNovo.setFinanciamentoTipo(debitoCobrado.getFinanciamentoTipo());
		// debitoCobradoNovo.setQuadra(debitoCobrado.getQuadra());
		// debitoCobradoNovo.setLocalidade(debitoCobrado.getLocalidade());
		// debitoCobradoNovo.setDebitoTipo(debitoCobrado.getDebitoTipo());
		// debitoCobradoNovo.setLancamentoItemContabil(debitoCobrado.getLancamentoItemContabil());
		// debitoCobradoNovo.setParcelamentoGrupo(debitoCobrado.getParcelamentoGrupo());
		// debitoCobradoNovo.setParcelamento(debitoCobrado.getParcelamento());
		// colecaoDebitoCobradoContaRetificar.add(debitoCobradoNovo);
		// }
		//
		// }
		//
		// }
		//
		// }
		// }
		//
		// // ----------------------
		//
		// // --------------------------
		//
		// // Obtendo os créditos realizados da conta
		// Collection colecaoCreditoRealizado =
		// getControladorFaturamento().obterCreditosRealizadosConta(contaAtual);
		//
		// Integer idContaRetificada =
		// getControladorFaturamento().retificarContaAjusteFaturamento(contaAtual.getReferencia(),
		// contaAtual,
		// contaAtual.getImovel(), colecaoDebitoCobradoContaRetificar,
		// contaAtual.getLigacaoAguaSituacao(),
		// contaAtual.getLigacaoEsgotoSituacao(), contaAtual.getConsumoAgua().toString(),
		// contaAtual.getConsumoEsgoto().toString(), contaAtual.getPercentualEsgoto().toString(),
		// contaAtual.getDataVencimentoConta(), contaMotivoRetificacao, Usuario.USUARIO_BATCH,
		// contaAtual.getConsumoTarifa(), colecaoCreditoRealizado);
		//
		// Conta contaAjustada = (Conta) getControladorUtil().pesquisar(idContaRetificada,
		// Conta.class, false);
		//
		// arquivoConferencia.append("contaAtual = " + contaAjustada.getId() + " - " +
		// "Quantidade debitos antes = "
		// + colecaoDebitoCobradoConta.size() + " Quantidade débitos após = " +
		// colecaoDebitoCobradoContaRetificar.size());
		// arquivoConferencia.append(System.getProperty("line.separator"));
		//
		// }
		//
		// arquivoConferencia.append("________________________________________________________________________________________________");
		// this.gerarArquivo(arquivoConferencia, nomeArquivo);

	}

	public void executarCancelamentoDebitoACobrar() throws Exception{

		System.out.println("** executarCancelamentoDebitoACobrar - INICIO");

		this.ejbCreate();

		Collection<DebitoACobrar> colecaoDebitoACobrar = repositorioFaturamento.pesquisarDebitoACobrarSuspensoParaCancelar(1000, 9992);

		if(!Util.isVazioOrNulo(colecaoDebitoACobrar)){
			System.out.println("** colecaoDebitoACobrar " + colecaoDebitoACobrar.size());

			for(DebitoACobrar debitoACobrar : colecaoDebitoACobrar){
				this.getControladorFaturamento().cancelarDebitoACobrar(new String[] {debitoACobrar.getId().toString()},
								Usuario.USUARIO_BATCH, debitoACobrar.getImovel().getId(), true);
			}
		}

		System.out.println("** executarCancelamentoDebitoACobrar - FIM");
	}

	public void executarCancelamentoDebitoACobrarRateioCasal() throws Exception{

		System.out.println("** executarCancelamentoDebitoACobrarRateioCasal - INICIO");

		this.ejbCreate();

		Collection<DebitoACobrar> colecaoDebitoACobrar = repositorioFaturamento.pesquisarDebitoACobrarSuspensoParaCancelar(1000, 9999);

		if(!Util.isVazioOrNulo(colecaoDebitoACobrar)){

			System.out.println("** colecaoDebitoACobrar " + colecaoDebitoACobrar.size());

			for(DebitoACobrar debitoACobrar : colecaoDebitoACobrar){
				this.getControladorFaturamento().cancelarDebitoACobrar(new String[] {debitoACobrar.getId().toString()},
								Usuario.USUARIO_BATCH, debitoACobrar.getImovel().getId(), true);
			}
		}

		System.out.println("** executarCancelamentoDebitoACobrarRateioCasal - FIM");
	}

	/**
	 * @throws Exception
	 */

	public void executarAjusteDebitoACobrar() throws IOException, Exception{

		// Inicializa as instacias dos repositórios usados
		this.ejbCreate();

		String nomeArquivo = "AjusteAjusteDebitoACobrar_MACRO MEDIDOR_5920760.TXT";
		StringBuilder arquivoConferencia = new StringBuilder();
		Collection colecaoOperacaoContabil = new ArrayList();

		arquivoConferencia.append("________________________________________________________________________________________________");
		arquivoConferencia.append(System.getProperty("line.separator"));

		Collection colecaoIds = repositorioFaturamento.pesquisarIdsImovel();

		DebitoTipo debitoTipo = (DebitoTipo) getControladorUtil().pesquisar(64, DebitoTipo.class, false);

		BigDecimal valor = Util.formatarMoedaRealparaBigDecimal("44,20");

		for(Object id : colecaoIds){
			/*
			 * Popula a coleção com os débitos enviados a menor para posterior retificação da
			 * conta caso necessário
			 */

			Integer idImovel = new Integer(id.toString());

			// Carrega os dados do imóvel da conta
			Imovel imovel = this.getControladorImovel().pesquisarImovel(idImovel);

			arquivoConferencia.append("imovel = " + imovel.getId());
			System.out.println("................................imovel = " + imovel.getId());

			arquivoConferencia.append(System.getProperty("line.separator"));

			// Gera o débito a cobrar com o valor restante a ser cobrado
			CobrancaForma cobrancaForma = new CobrancaForma();
			cobrancaForma.setId(CobrancaForma.COBRANCA_EM_CONTA);

			DebitoACobrar debitoACobrar = new DebitoACobrar();
			debitoACobrar.setDebitoTipo(debitoTipo);
			debitoACobrar.setValorDebito(valor);
			debitoACobrar.setImovel(imovel);
			debitoACobrar.setGeracaoDebito(new Date());
			debitoACobrar.setLocalidade(imovel.getLocalidade());
			debitoACobrar.setQuadra(imovel.getQuadra());
			debitoACobrar.setAnoMesReferenciaDebito(201303);
			debitoACobrar.setLancamentoItemContabil(debitoTipo.getLancamentoItemContabil());
			debitoACobrar.setFinanciamentoTipo(debitoTipo.getFinanciamentoTipo());
			debitoACobrar.setCobrancaForma(cobrancaForma);
			debitoACobrar.setUltimaAlteracao(new Date());
			debitoACobrar.setAnoMesCobrancaDebito(201304);

			OperacaoContabilHelper helper = new OperacaoContabilHelper();
			helper.setObjetoOrigem(this.getControladorFaturamento().inserirDebitoACobrarSemRegistrarLancamentoContabil(1, debitoACobrar,
							null, imovel, null, null, Usuario.USUARIO_BATCH, false, null, null, null));
			helper.setOperacaoContabil(OperacaoContabil.INCLUIR_DEBITO_A_COBRAR);

			colecaoOperacaoContabil.add(helper);
		}

		if(!Util.isVazioOrNulo(colecaoOperacaoContabil)){

			// Registra o lançamento contábil dos débitos a cobrar gerados
			this.getControladorContabil().registrarLancamentoContabil(colecaoOperacaoContabil);
		}

		arquivoConferencia.append("________________________________________________________________________________________________");
		this.gerarArquivo(arquivoConferencia, nomeArquivo);

	}

	public void executarAjusteRetificarContasRetirarDebitoRateioDuplicado() throws Exception{

		// Inicializa as instacias dos repositórios usados
		this.ejbCreate();

		// Declaração das variáveis
		Collection<Integer> colecaoidsImoveis = null;
		Collection<DebitoCobrado> colecaoDebitoCobradoContaOriginal = null;
		Collection<DebitoCobrado> colecaoDebitoCobradoContaNova = null;
		ContaMotivoRetificacao contaMotivoRetificacao = new ContaMotivoRetificacao();
		contaMotivoRetificacao.setId(13);
		Conta contaAtual = null;

		// Obtém os ids de imóveis
		colecaoidsImoveis = repositorioFaturamento.pesquisarIdsImoveisAjusteRetificarContasRetirarDebitorateioDuplicado();

		for(Iterator iterator = colecaoidsImoveis.iterator(); iterator.hasNext();){

			Integer idImovel = Util.obterInteger(iterator.next().toString());

			colecaoDebitoCobradoContaOriginal = new ArrayList<DebitoCobrado>();
			colecaoDebitoCobradoContaNova = new ArrayList<DebitoCobrado>();

			FiltroConta filtroConta = new FiltroConta();
			filtroConta.adicionarParametro(new ParametroSimplesColecao(FiltroConta.REFERENCIA, 201303));
			filtroConta.adicionarParametro(new ParametroSimples(FiltroConta.IMOVEL_ID, idImovel));
			Collection<Conta> colecaoConta = this.getControladorUtil().pesquisar(filtroConta, Conta.class.getName());
			contaAtual = (Conta) Util.retonarObjetoDeColecao(colecaoConta);

			if(contaAtual != null){

				colecaoDebitoCobradoContaOriginal = repositorioFaturamento.pesquisarDebitosCobradoDaContaRetificada(contaAtual.getId());

				if(!Util.isVazioOrNulo(colecaoDebitoCobradoContaOriginal)){

					for(DebitoCobrado debitoCobradoConta : colecaoDebitoCobradoContaOriginal){

						if(debitoCobradoConta.getDebitoTipo().getId().intValue() == 50
										&& debitoCobradoConta.getAnoMesReferenciaDebito().intValue() == 201302){

							continue;
						}

						DebitoCobrado debitoCobrado = new DebitoCobrado();
						debitoCobrado.setCodigoSetorComercial(debitoCobradoConta.getCodigoSetorComercial());
						debitoCobrado.setNumeroQuadra(debitoCobradoConta.getNumeroQuadra());
						debitoCobrado.setNumeroLote(debitoCobradoConta.getNumeroLote());
						debitoCobrado.setNumeroSubLote(debitoCobradoConta.getNumeroSubLote());
						debitoCobrado.setAnoMesReferenciaDebito(debitoCobradoConta.getAnoMesReferenciaDebito());
						debitoCobrado.setAnoMesCobrancaDebito(debitoCobradoConta.getAnoMesCobrancaDebito());
						debitoCobrado.setValorPrestacao(debitoCobradoConta.getValorPrestacao());
						debitoCobrado.setNumeroPrestacao(debitoCobradoConta.getNumeroPrestacao());
						debitoCobrado.setNumeroPrestacaoDebito(debitoCobradoConta.getNumeroPrestacaoDebito());
						debitoCobrado.setUltimaAlteracao(new Date());
						debitoCobrado.setDebitoCobrado(debitoCobradoConta.getDebitoCobrado());
						debitoCobrado.setFinanciamentoTipo(debitoCobradoConta.getFinanciamentoTipo());
						debitoCobrado.setQuadra(debitoCobradoConta.getQuadra());
						debitoCobrado.setLocalidade(debitoCobradoConta.getLocalidade());
						debitoCobrado.setDebitoTipo(debitoCobradoConta.getDebitoTipo());
						debitoCobrado.setLancamentoItemContabil(debitoCobradoConta.getLancamentoItemContabil());
						debitoCobrado.setParcelamentoGrupo(debitoCobradoConta.getParcelamentoGrupo());
						debitoCobrado.setParcelamento(debitoCobradoConta.getParcelamento());
						colecaoDebitoCobradoContaNova.add(debitoCobrado);
					}
				}

				// Carrega na conta os dados necessários para retificação
				contaAtual = getControladorFaturamento().pesquisarContaRetificacao(contaAtual.getId());

				// Obtendo os créditos realizados da conta
				Collection colecaoCreditoRealizado = getControladorFaturamento().obterCreditosRealizadosConta(contaAtual);

				System.out.println("retificando conta do imóvel:" + contaAtual.getImovel().getId().toString());

				// Retificar a Conta <<Inclui>> [UC0150 Retificar Conta]
				getControladorFaturamento().retificarContaAjusteFaturamento(201303, contaAtual, contaAtual.getImovel(),
								colecaoDebitoCobradoContaNova, contaAtual.getLigacaoAguaSituacao(), contaAtual.getLigacaoEsgotoSituacao(),
								contaAtual.getConsumoAgua().toString(), contaAtual.getConsumoEsgoto().toString(),
								contaAtual.getPercentualEsgoto().toString(), contaAtual.getDataVencimentoConta(), contaMotivoRetificacao,
								Usuario.USUARIO_BATCH, contaAtual.getConsumoTarifa(), colecaoCreditoRealizado);
			}
		}
	}

	public void gerarResumoFaturamentoSimulacaoAjusteCasal(FaturamentoGrupo faturamentoGrupo, Integer anoMesCorrente,
					Integer idFaturamentoAtividadeCronograma) throws ControladorException{

		try{

			this.ejbCreate();

			log.info("**********Início Registrar Imediato Ajuste do Grupo/Referência: " + faturamentoGrupo.getId().toString() + "/"
							+ anoMesCorrente.toString());
			log.info("**********Ajuste Gerar Resumo Faturamento Simulação*************************************");

			Collection colecaoCronograma = repositorioBatch
							.pesquisarRotasProcessamentoBatchFaturamentoComandado(idFaturamentoAtividadeCronograma);

			Iterator iteratorCronograma = colecaoCronograma.iterator();
			Collection<Rota> colecaoRotas = new ArrayList();

			while(iteratorCronograma.hasNext()){

				Object[] array = (Object[]) iteratorCronograma.next();
				Rota rota = (Rota) array[1];

				if(rota.getLeituraTipo().getId().equals(LeituraTipo.LEITURA_E_ENTRADA_SIMULTANEA)
								|| rota.getLeituraTipo().getId().equals(LeituraTipo.CELULAR_MOBILE_ENTRADA_SIMULTANEA)){

					colecaoRotas.add(rota);
				}
			}

			boolean gerarAtividadeGrupoFaturamento = true;

			Object[] arrayMedicaoConsumoHistorico = null;
			SistemaParametro sistemaParametro = null;
			sistemaParametro = getControladorUtil().pesquisarParametrosDoSistema();
			ConsumoHistorico consumoHistoricoAguaImovelNaoMedido = null;
			ConsumoHistorico consumoHistoricoEsgotoImovelNaoMedido = null;

			if(colecaoRotas != null && !colecaoRotas.isEmpty()){

				// [FS0001 - Verificar existência de dados]
				if(!getControladorImovel().isImovelPorColecaoRotas(colecaoRotas)){

					throw new ControladorException("atencao.nao_ha_contas_prefaturadas_para_rotas");
				}
			}

			for(Iterator iterator = colecaoRotas.iterator(); iterator.hasNext();){

				Rota rotaFaturamento = (Rota) iterator.next();

				log.info("**********Rota a processar: " + rotaFaturamento.getId());

				FaturamentoAtivCronRota faturamentoAtivCronRota = getControladorFaturamento()
								.obterFaturamentoAtividadeCronogramaPorGrupoFaturamentoRota(
								FaturamentoAtividade.REGISTRAR_FATURAMENTO_IMEDIATO, anoMesCorrente, faturamentoGrupo, rotaFaturamento);

				Collection<MedicaoHistorico> colecaoMedicaoHistoricoImovel = new ArrayList<MedicaoHistorico>();
				Collection<ConsumoHistorico> colecaoConsumoHistoricoImovel = new ArrayList<ConsumoHistorico>();
				Collection colecaoResumoFaturamento = new ArrayList<ResumoFaturamentoSimulacao>();

				// Filtro que será usado nas pesquisas do Movimento_Roteiro
				FiltroMovimentoRoteiroEmpresa filtroMovimentoRoteiroEmpresa = new FiltroMovimentoRoteiroEmpresa();
				filtroMovimentoRoteiroEmpresa.adicionarCaminhoParaCarregamentoEntidade("leituraAnormalidade");
				filtroMovimentoRoteiroEmpresa.adicionarCaminhoParaCarregamentoEntidade("consumoAnormalidade");
				filtroMovimentoRoteiroEmpresa.adicionarCaminhoParaCarregamentoEntidade("medicaoTipo");
				filtroMovimentoRoteiroEmpresa.adicionarCaminhoParaCarregamentoEntidade("consumoTipo");
				filtroMovimentoRoteiroEmpresa.adicionarCaminhoParaCarregamentoEntidade("leiturista.funcionario");
				filtroMovimentoRoteiroEmpresa.adicionarCaminhoParaCarregamentoEntidade("ligacaoAguaSituacao");
				filtroMovimentoRoteiroEmpresa.adicionarCaminhoParaCarregamentoEntidade("ligacaoEsgotoSituacao");

				Collection<Imovel> colecaoImovel = getControladorImovel().pesquisarImoveisPorRotaComLocalidade(rotaFaturamento.getId());

				if(colecaoImovel != null && !colecaoImovel.isEmpty()){

					log.info("**********Quantidade de imóveis da rota : " + colecaoImovel.size());

					for(Iterator iteratorColecaoImovel = colecaoImovel.iterator(); iteratorColecaoImovel.hasNext();){

						Imovel imovelAux = (Imovel) iteratorColecaoImovel.next();

						Imovel imovel = this.getControladorImovel().pesquisarImovel(imovelAux.getId());

						// Obtém o MovimentoRoteiroEmpresa do Imóvel e Ano/Mês Referência da Conta
						filtroMovimentoRoteiroEmpresa.limparListaParametros();
						filtroMovimentoRoteiroEmpresa.adicionarParametro(new ParametroSimples(FiltroMovimentoRoteiroEmpresa.IMOVEL_ID,
										imovel.getId()));
						filtroMovimentoRoteiroEmpresa.adicionarParametro(new ParametroSimples(
										FiltroMovimentoRoteiroEmpresa.ANO_MES_MOVIMENTO, anoMesCorrente));
						filtroMovimentoRoteiroEmpresa.adicionarParametro(new ParametroSimples(FiltroMovimentoRoteiroEmpresa.INDICADOR_FASE,
										MovimentoRoteiroEmpresa.FASE_PROCESSADO));

						Collection colecaoMovimentoRoteiroEmpresa = getControladorUtil().pesquisar(filtroMovimentoRoteiroEmpresa,
										MovimentoRoteiroEmpresa.class.getName());
						MovimentoRoteiroEmpresa movimentoRoteiroImovel = (MovimentoRoteiroEmpresa) Util
										.retonarObjetoDeColecao(colecaoMovimentoRoteiroEmpresa);

						if(movimentoRoteiroImovel != null){

							log.info("**********Processando movimento roteiro empresa do imóvel: "
											+ movimentoRoteiroImovel.getImovel().getId().toString());

							arrayMedicaoConsumoHistorico = new Object[3];

							// Caso o imóvel seja não medido
							if(getControladorFaturamento().verificarImovelNaoMedido(imovel)){

								// 2.3.2. Atualizar na tabela MOVIMENTO_ROTEIRO_EMPRESA
								movimentoRoteiroImovel.setIndicadorFase(MovimentoRoteiroEmpresa.FASE_PROCESSADO);
								movimentoRoteiroImovel.setUltimaAlteracao(new Date());

								// Preenche com MedicaoHistorico que vai ser usado no [SB0002]
								arrayMedicaoConsumoHistorico[0] = new MedicaoHistorico();

								/*
								 * Prepara os dados de Medição Histórico e Consumo Histórico para
								 * determinar os dados do faturamento do imóvel não medido
								 */
								consumoHistoricoAguaImovelNaoMedido = new ConsumoHistorico();
								String indicadorEmissaoCampo = movimentoRoteiroImovel.getIndicadorEmissaoCampo();

								// Determina o Consumo da Ligação de Água
								if(movimentoRoteiroImovel.getLigacaoAguaSituacao() != null
												&& (movimentoRoteiroImovel.getLigacaoAguaSituacao().getIndicadorFaturamentoSituacao()
																.equals(LigacaoAguaSituacao.FATURAMENTO_ATIVO) || (movimentoRoteiroImovel
																.getLigacaoAguaSituacao().getIndicadorFaturamentoSituacao()
																.equals(LigacaoAguaSituacao.NAO_FATURAVEL)
																&& movimentoRoteiroImovel.getNumeroConsumoFaturadoAgua() != null && !movimentoRoteiroImovel
																.getNumeroConsumoFaturadoAgua().equals(Integer.valueOf(0))))){

									if(MovimentoRoteiroEmpresa.INDICADOR_EMISSAO_CAMPO_CONTA_RETIDA.equals(indicadorEmissaoCampo)){

										consumoHistoricoAguaImovelNaoMedido.setNumeroConsumoFaturadoMes(movimentoRoteiroImovel
														.getNumeroConsumoMedio());
									}else{

										consumoHistoricoAguaImovelNaoMedido.setNumeroConsumoFaturadoMes(movimentoRoteiroImovel
														.getNumeroConsumoFaturadoAgua());
									}
								}else{

									consumoHistoricoAguaImovelNaoMedido.setNumeroConsumoFaturadoMes(0);
								}

								// Indicador de Faturamento Consumo Água
								if(movimentoRoteiroImovel.getIndicadorIsencaoAgua() == null
												|| movimentoRoteiroImovel.getIndicadorIsencaoAgua().equals(ConstantesSistema.NAO)){

									consumoHistoricoAguaImovelNaoMedido.setIndicadorFaturamento(ConstantesSistema.SIM);
								}else if(movimentoRoteiroImovel.getIndicadorIsencaoAgua().equals(ConstantesSistema.SIM)){

									consumoHistoricoAguaImovelNaoMedido.setIndicadorFaturamento(ConstantesSistema.NAO);
								}

								arrayMedicaoConsumoHistorico[1] = consumoHistoricoAguaImovelNaoMedido;

								// Determina o Consumo da Ligação de Esgoto
								consumoHistoricoEsgotoImovelNaoMedido = new ConsumoHistorico();

								if(movimentoRoteiroImovel.getLigacaoEsgotoSituacao() != null
												&& (movimentoRoteiroImovel.getLigacaoEsgotoSituacao().getId()
																.equals(LigacaoEsgotoSituacao.LIGADO) || (movimentoRoteiroImovel
																.getLigacaoEsgotoSituacao().getId()
																.equals(LigacaoEsgotoSituacao.FACTIVEL_FATURADA) && movimentoRoteiroImovel
																.getLigacaoAguaSituacao().getId().equals(LigacaoAguaSituacao.LIGADO)))){

									if(MovimentoRoteiroEmpresa.INDICADOR_EMISSAO_CAMPO_CONTA_RETIDA.equals(indicadorEmissaoCampo)){

										consumoHistoricoEsgotoImovelNaoMedido.setNumeroConsumoFaturadoMes(movimentoRoteiroImovel
														.getNumeroConsumoMedio());
									}else{

										consumoHistoricoEsgotoImovelNaoMedido.setNumeroConsumoFaturadoMes(movimentoRoteiroImovel
														.getNumeroConsumoFaturadoEsgoto());
									}
								}else{

									consumoHistoricoEsgotoImovelNaoMedido.setNumeroConsumoFaturadoMes(0);
								}

								// Indicador de Faturamento Consumo Esgoto
								if(movimentoRoteiroImovel.getIndicadorIsencaoEsgoto() == null
												|| movimentoRoteiroImovel.getIndicadorIsencaoEsgoto().equals(ConstantesSistema.NAO)){

									consumoHistoricoEsgotoImovelNaoMedido.setIndicadorFaturamento(ConstantesSistema.SIM);
								}else if(movimentoRoteiroImovel.getIndicadorIsencaoEsgoto().equals(ConstantesSistema.SIM)){

									consumoHistoricoEsgotoImovelNaoMedido.setIndicadorFaturamento(ConstantesSistema.NAO);
								}

								arrayMedicaoConsumoHistorico[2] = consumoHistoricoEsgotoImovelNaoMedido;

								// ---------------------------------------------------------
								// [SB0002 – Determinar Faturamento para o Imóvel] - Inicio
								// ---------------------------------------------------------
								determinarFaturamentoImovelAjusteParaFaturamentoImediato(imovel, anoMesCorrente,
												gerarAtividadeGrupoFaturamento, faturamentoAtivCronRota, colecaoResumoFaturamento,
												sistemaParametro, false, faturamentoGrupo, arrayMedicaoConsumoHistorico,
												movimentoRoteiroImovel);
							}else{

								// [SB0001] - Gera o Histórico de Medição e o Histórico de Consumo
								if(movimentoRoteiroImovel.getMedicaoTipo() != null){

									MedicaoHistorico medicaoHistoricoImovel = new MedicaoHistorico();

									medicaoHistoricoImovel.setMedicaoTipo(movimentoRoteiroImovel.getMedicaoTipo());

									// Verifica se já existe MediçãoHistórico para Imóvel no
									// AnoMêsReferência
									MedicaoHistorico medicaoHistoricoBase = null;
									// Será utilizado no cálculo do Crédito de Consumo
									Integer numeroConsumoFaturado = null;
									if(movimentoRoteiroImovel.getMedicaoTipo().getId().equals(MedicaoTipo.LIGACAO_AGUA)){

										medicaoHistoricoBase = getControladorMicromedicao().pesquisarMedicaoHistoricoTipoAgua(
														movimentoRoteiroImovel.getImovel().getId(), anoMesCorrente);

										if(medicaoHistoricoBase != null){

											medicaoHistoricoImovel.setId(medicaoHistoricoBase.getId());
										}

										medicaoHistoricoImovel.setImovel(null);
										medicaoHistoricoImovel.setLigacaoAgua(imovel.getLigacaoAgua());

										// HidrometroInstacao Historico
										if(imovel.getLigacaoAgua() != null
														&& imovel.getLigacaoAgua().getHidrometroInstalacaoHistorico() != null){

											medicaoHistoricoImovel.setHidrometroInstalacaoHistorico(imovel.getLigacaoAgua()
															.getHidrometroInstalacaoHistorico());
										}

										numeroConsumoFaturado = movimentoRoteiroImovel.getNumeroConsumoFaturadoAgua();
										if(numeroConsumoFaturado == null){
											numeroConsumoFaturado = Integer.valueOf("0");
										}

									}else if(movimentoRoteiroImovel.getMedicaoTipo().getId().equals(MedicaoTipo.POCO)){

										medicaoHistoricoBase = getControladorMicromedicao().pesquisarMedicaoHistoricoTipoPoco(
														imovel.getId(), anoMesCorrente);
										if(medicaoHistoricoBase != null){

											medicaoHistoricoImovel.setId(medicaoHistoricoBase.getId());
										}

										medicaoHistoricoImovel.setImovel(imovel);
										medicaoHistoricoImovel.setLigacaoAgua(null);

										// HidrometroInstacao Historico
										medicaoHistoricoImovel.setHidrometroInstalacaoHistorico(imovel.getHidrometroInstalacaoHistorico());

										numeroConsumoFaturado = movimentoRoteiroImovel.getNumeroConsumoFaturadoEsgoto();
										if(numeroConsumoFaturado == null){

											numeroConsumoFaturado = Integer.valueOf("0");
										}
									}

									// Seta os Atributos comuns para Inserção e Atualização
									medicaoHistoricoImovel.setAnoMesReferencia(anoMesCorrente);
									medicaoHistoricoImovel.setDataLeituraAtualInformada(movimentoRoteiroImovel.getDataLeitura());
									medicaoHistoricoImovel.setDataLeituraAtualFaturamento(movimentoRoteiroImovel.getDataLeitura());

									if(movimentoRoteiroImovel.getNumeroLeituraFaturada() != null
													&& movimentoRoteiroImovel.getNumeroLeituraFaturada() != 0){

										medicaoHistoricoImovel.setLeituraAtualInformada(movimentoRoteiroImovel.getNumeroLeituraFaturada());
										medicaoHistoricoImovel
														.setLeituraAtualFaturamento(movimentoRoteiroImovel.getNumeroLeituraFaturada());
									}else{

										medicaoHistoricoImovel.setLeituraAtualInformada(movimentoRoteiroImovel.getNumeroLeitura());
										medicaoHistoricoImovel.setLeituraAtualFaturamento(movimentoRoteiroImovel.getNumeroLeitura());
									}

									// Verificar se Funcionario existe
									if(movimentoRoteiroImovel.getLeiturista() != null
													&& movimentoRoteiroImovel.getLeiturista().getFuncionario() != null){

										medicaoHistoricoImovel.setFuncionario(movimentoRoteiroImovel.getLeiturista().getFuncionario());
									}

									// Verificar se Anormalidade Leitura existe
									if(movimentoRoteiroImovel.getLeituraAnormalidade() != null){

										medicaoHistoricoImovel.setLeituraAnormalidadeInformada(movimentoRoteiroImovel
														.getLeituraAnormalidade());
										medicaoHistoricoImovel.setLeituraAnormalidadeFaturamento(movimentoRoteiroImovel
														.getLeituraAnormalidade());
									}

									// Indicador de Situação de Leitura
									LeituraSituacao leituraSituacaoMovimento = new LeituraSituacao();
									if(movimentoRoteiroImovel.getIndicadorConfirmacaoLeitura() != null){

										leituraSituacaoMovimento.setId(movimentoRoteiroImovel.getIndicadorConfirmacaoLeitura().intValue());
									}else{

										leituraSituacaoMovimento.setId(LeituraSituacao.NAO_REALIZADA);
									}

									medicaoHistoricoImovel.setLeituraSituacaoAtual(leituraSituacaoMovimento);

									// Busca os Dados da leitura anterior
									Object[] dadosUltimoMedicaoHistorico = repositorioMicromedicao.obterMedicaoHistoricoAnterior(
													imovel.getId(), anoMesCorrente, movimentoRoteiroImovel.getMedicaoTipo().getId());

									medicaoHistoricoImovel.setNumeroVezesConsecutivasOcorrenciaAnormalidade(null);
									Integer numeroSaldoConsumoCreditoAnterior = 0;
									LeituraSituacao leituraSituacaoAnterior = new LeituraSituacao();

									if(!Util.isVazioOrNulo(dadosUltimoMedicaoHistorico)){

										if(dadosUltimoMedicaoHistorico[2] != null){

											medicaoHistoricoImovel.setLeituraAnteriorInformada((Integer) dadosUltimoMedicaoHistorico[2]);
										}

										if(dadosUltimoMedicaoHistorico[3] != null){

											leituraSituacaoAnterior.setId((Integer) dadosUltimoMedicaoHistorico[3]);
										}else{

											leituraSituacaoAnterior.setId(LeituraSituacao.NAO_REALIZADA);
										}

										if(dadosUltimoMedicaoHistorico[4] != null){

											numeroSaldoConsumoCreditoAnterior = (Integer) dadosUltimoMedicaoHistorico[4];
										}
									}else{

										medicaoHistoricoImovel.setLeituraAnteriorInformada(null);
										leituraSituacaoAnterior.setId(LeituraSituacao.NAO_REALIZADA);
									}

									medicaoHistoricoImovel.setDataLeituraAnteriorFaturamento(movimentoRoteiroImovel
													.getDataLeituraAnterior());
									medicaoHistoricoImovel.setLeituraAnteriorFaturamento(movimentoRoteiroImovel.getNumeroLeituraAnterior());
									medicaoHistoricoImovel.setLeituraSituacaoAnterior(leituraSituacaoAnterior);
									medicaoHistoricoImovel.setConsumoMedioHidrometro(null);
									medicaoHistoricoImovel.setLeituraProcessamentoMovimento(new Date());

									// Crédito de Consumo
									Integer numeroCreditoConsumoGerado = 0;
									Integer numeroCreditoConsumoFaturado = 0;

									if(movimentoRoteiroImovel.getNumeroConsumoCreditoFaturado() != null){

										numeroCreditoConsumoFaturado = movimentoRoteiroImovel.getNumeroConsumoCreditoFaturado();
									}

									Integer numeroConsumoMedido = movimentoRoteiroImovel.getNumeroConsumoMedido();
									medicaoHistoricoImovel.setNumeroConsumoMes(numeroConsumoMedido);

									// 1 - Verifica se existirá crédito gerado para ajuste do saldo
									if(movimentoRoteiroImovel.getNumeroConsumoCreditoFaturado() != null
													&& movimentoRoteiroImovel.getLeituraAnormalidade() != null
													&& movimentoRoteiroImovel.getLeituraAnormalidade().getIndicadorCreditoConsumo() != null
													&& movimentoRoteiroImovel.getLeituraAnormalidade().getIndicadorCreditoConsumo()
																	.intValue() == 1){

										numeroCreditoConsumoGerado = movimentoRoteiroImovel.getNumeroConsumoCreditoFaturado();

										// "Zera" o consumo Faturado, já que foi utilizado para a
										// geração
										numeroCreditoConsumoFaturado = 0;
										if(numeroCreditoConsumoGerado.intValue() > 0){

											numeroCreditoConsumoGerado = numeroCreditoConsumoGerado * -1;
										}

									}else if(movimentoRoteiroImovel.getConsumoTipo() != null
													&& movimentoRoteiroImovel.getConsumoTipo().getId()
																	.equals(ConsumoTipo.CONSUMO_MEDIO_AJUSTADO)){

										int saldoConsumoGerado = (numeroConsumoMedido.intValue() - (numeroConsumoFaturado.intValue() + numeroCreditoConsumoFaturado
														.intValue()));
										numeroCreditoConsumoGerado = Integer.valueOf(saldoConsumoGerado);
									}

									// Atualiza o saldo
									numeroSaldoConsumoCreditoAnterior = Integer
													.valueOf((numeroSaldoConsumoCreditoAnterior.intValue() + numeroCreditoConsumoGerado)
																	- numeroCreditoConsumoFaturado);

									medicaoHistoricoImovel.setConsumoCreditoGerado(numeroCreditoConsumoGerado);
									medicaoHistoricoImovel.setConsumoCreditoAnterior(numeroSaldoConsumoCreditoAnterior);

									medicaoHistoricoImovel.setUltimaAlteracao(new Date());

									// Preenche com MedicaoHistorico que vai ser usado no [SB0002]
									arrayMedicaoConsumoHistorico[0] = medicaoHistoricoImovel;

									if(medicaoHistoricoImovel.getLigacaoAgua() != null || medicaoHistoricoImovel.getImovel() != null){

										colecaoMedicaoHistoricoImovel.add(medicaoHistoricoImovel);
									}

									// Gera o Histórico Consumo do Imóvel
									ConsumoHistorico consumoHistoricoImovel = new ConsumoHistorico();

									consumoHistoricoImovel.setImovel(imovel);
									consumoHistoricoImovel.setReferenciaFaturamento(anoMesCorrente);
									consumoHistoricoImovel.setIndicadorAlteracaoUltimosConsumos(ConstantesSistema.NAO);
									consumoHistoricoImovel.setConsumoRateio(Integer.valueOf("0"));
									consumoHistoricoImovel.setConsumoImovelCondominio(null);
									consumoHistoricoImovel.setIndicadorImovelCondominio(imovel.getIndicadorImovelCondominio());

									Integer idLigacaoEsgoto = imovel.getId();
									BigDecimal percentualColeta = this.getControladorLigacaoEsgoto().recuperarPercentualColetaEsgoto(
													idLigacaoEsgoto);

									if(percentualColeta != null){

										consumoHistoricoImovel.setPercentualColeta(percentualColeta);
									}else{

										consumoHistoricoImovel.setPercentualColeta(Util.formatarMoedaRealparaBigDecimal("100"));
									}

									consumoHistoricoImovel.setUltimaAlteracao(new Date());
									consumoHistoricoImovel.setRateioTipo(null);
									consumoHistoricoImovel.setIndicadorAjuste(rotaFaturamento.getIndicadorAjusteConsumo());

									// ConsumoTipo
									if(movimentoRoteiroImovel.getConsumoTipo() != null){

										consumoHistoricoImovel.setConsumoTipo(movimentoRoteiroImovel.getConsumoTipo());
									}

									// Consumo Anormalidade TODO ehc verificar se Anormalidade de
									// Consumo existe
									if(movimentoRoteiroImovel.getConsumoAnormalidade() != null){

										consumoHistoricoImovel.setConsumoAnormalidade(movimentoRoteiroImovel.getConsumoAnormalidade());
									}

									// Consumo Médio do Imóvel
									int[] consumoMedioImovel = getControladorMicromedicao().obterConsumoMedioImovel(imovel,
													sistemaParametro);

									if(consumoMedioImovel != null){

										consumoHistoricoImovel.setConsumoMedio(Integer.valueOf(consumoMedioImovel[0]));
									}

									// [UC0105] - Obter Consumo Mínimo
									Collection colecaoCategorias = getControladorImovel().obterQuantidadeEconomiasCategoria(imovel);

									int consumoMinimoLigacao = getControladorMicromedicao().obterConsumoMinimoLigacao(imovel,
													colecaoCategorias);

									if(consumoMinimoLigacao > 0){

										consumoHistoricoImovel.setConsumoMinimo(Integer.valueOf(consumoMinimoLigacao));
									}

									if(movimentoRoteiroImovel.getRota() != null){

										consumoHistoricoImovel.setRota(movimentoRoteiroImovel.getRota());
									}

									if(movimentoRoteiroImovel.getIndicadorPoco() != null
													&& movimentoRoteiroImovel.getIndicadorPoco().equals(ConstantesSistema.SIM)){

										consumoHistoricoImovel.setPocoTipo(imovel.getPocoTipo());
									}

									// Serão utilizados no [SB0002]
									if(movimentoRoteiroImovel.getLigacaoAguaSituacao() != null
													&& (movimentoRoteiroImovel.getLigacaoAguaSituacao().getIndicadorFaturamentoSituacao()
																	.equals(LigacaoAguaSituacao.FATURAMENTO_ATIVO) || (movimentoRoteiroImovel
																	.getLigacaoAguaSituacao().getIndicadorFaturamentoSituacao()
																	.equals(LigacaoAguaSituacao.NAO_FATURAVEL)
																	&& movimentoRoteiroImovel.getNumeroConsumoFaturadoAgua() != null && !movimentoRoteiroImovel
																	.getNumeroConsumoFaturadoAgua().equals(Integer.valueOf(0))))){

										ConsumoHistorico consumoHistoricoAguaImovel = new ConsumoHistorico();

										try{

											PropertyUtils.copyProperties(consumoHistoricoAguaImovel, consumoHistoricoImovel);

										}catch(IllegalAccessException e){

											e.printStackTrace();
											throw new ControladorException("erro.sistema", e);
										}catch(InvocationTargetException e){

											e.printStackTrace();
											throw new ControladorException("erro.sistema", e);
										}

										// LigaçãoTipo
										LigacaoTipo ligacaoTipoImovel = new LigacaoTipo();
										ligacaoTipoImovel.setId(LigacaoTipo.LIGACAO_AGUA);
										consumoHistoricoAguaImovel.setLigacaoTipo(ligacaoTipoImovel);

										// Verifica se já existe Consumo_Histórico na base
										Collection colecaoDadosConsumoHistoricoBase = getControladorMicromedicao()
														.pesquisarConsumoHistorico(imovel, ligacaoTipoImovel,
																		anoMesCorrente);
										if(colecaoDadosConsumoHistoricoBase != null && !colecaoDadosConsumoHistoricoBase.isEmpty()){

											Object[] dadosConsumoHistoricoBase = (Object[]) colecaoDadosConsumoHistoricoBase.iterator()
															.next();
											if(dadosConsumoHistoricoBase != null){

												consumoHistoricoAguaImovel.setId((Integer) dadosConsumoHistoricoBase[0]);
											}
										}else{

											consumoHistoricoAguaImovel.setId(null);
										}

										// Indicador de Faturamento
										if(movimentoRoteiroImovel.getIndicadorIsencaoAgua() == null
														|| movimentoRoteiroImovel.getIndicadorIsencaoAgua().equals(new Short("2"))){

											consumoHistoricoAguaImovel.setIndicadorFaturamento(ConstantesSistema.SIM);
										}else if(movimentoRoteiroImovel.getIndicadorIsencaoAgua().equals(new Short("1"))){

											consumoHistoricoAguaImovel.setIndicadorFaturamento(ConstantesSistema.NAO);
										}

										if(movimentoRoteiroImovel.getMedicaoTipo().getId().equals(MedicaoTipo.LIGACAO_AGUA)
														&& movimentoRoteiroImovel.getNumeroConsumoCreditoFaturado() != null){

											consumoHistoricoAguaImovel.setConsumoMinimoCreditado(movimentoRoteiroImovel
															.getNumeroConsumoCreditoFaturado());
										}

										if(movimentoRoteiroImovel.getMedicaoTipo().getId().equals(MedicaoTipo.LIGACAO_AGUA)
														&& movimentoRoteiroImovel.getNumeroConsumoFaturadoAgua() != null){

											Integer numeroConsumoFaturadoMes = null;

											String indicadorEmissaoCampo = movimentoRoteiroImovel.getIndicadorEmissaoCampo();

											if(MovimentoRoteiroEmpresa.INDICADOR_EMISSAO_CAMPO_CONTA_RETIDA
															.equals(indicadorEmissaoCampo)){

												numeroConsumoFaturadoMes = movimentoRoteiroImovel.getNumeroConsumoMedio();
											}else{

												numeroConsumoFaturadoMes = movimentoRoteiroImovel.getNumeroConsumoFaturadoAgua();
											}

											consumoHistoricoAguaImovel.setNumeroConsumoFaturadoMes(numeroConsumoFaturadoMes);
										}

										consumoHistoricoAguaImovel.setConsumoImovelCondominio(null);

										// Preenche com ConsumoHistorico da Agua que vai ser usado
										// no [SB0002]
										arrayMedicaoConsumoHistorico[1] = consumoHistoricoAguaImovel;
										colecaoConsumoHistoricoImovel.add(consumoHistoricoAguaImovel);
									}

									if(movimentoRoteiroImovel.getLigacaoEsgotoSituacao() != null
													&& (movimentoRoteiroImovel.getLigacaoEsgotoSituacao().getId()
																	.equals(LigacaoEsgotoSituacao.LIGADO) || (movimentoRoteiroImovel
																	.getLigacaoEsgotoSituacao().getId()
																	.equals(LigacaoEsgotoSituacao.FACTIVEL_FATURADA) && movimentoRoteiroImovel
																	.getLigacaoAguaSituacao().getId().equals(LigacaoAguaSituacao.LIGADO)))){

										ConsumoHistorico consumoHistoricoEsgotoImovel = new ConsumoHistorico();
										try{

											PropertyUtils.copyProperties(consumoHistoricoEsgotoImovel, consumoHistoricoImovel);
										}catch(IllegalAccessException iaex){

											iaex.printStackTrace();
											throw new ControladorException("erro.sistema");
										}catch(InvocationTargetException itex){

											itex.printStackTrace();
											throw new ControladorException("erro.sistema");
										}

										// LigaçãoTipo
										LigacaoTipo ligacaoTipoImovel = new LigacaoTipo();
										ligacaoTipoImovel.setId(LigacaoTipo.LIGACAO_ESGOTO);
										consumoHistoricoEsgotoImovel.setLigacaoTipo(ligacaoTipoImovel);

										// Verifica se já existe Consumo_Histórico na base
										Collection colecaoDadosConsumoHistoricoBase = getControladorMicromedicao()
														.pesquisarConsumoHistorico(imovel, ligacaoTipoImovel, anoMesCorrente);
										if(colecaoDadosConsumoHistoricoBase != null && !colecaoDadosConsumoHistoricoBase.isEmpty()){

											Object[] dadosConsumoHistoricoBase = (Object[]) colecaoDadosConsumoHistoricoBase.iterator()
															.next();
											if(dadosConsumoHistoricoBase != null){

												consumoHistoricoEsgotoImovel.setId((Integer) dadosConsumoHistoricoBase[0]);
											}
										}else{

											consumoHistoricoEsgotoImovel.setId(null);
										}

										if(movimentoRoteiroImovel.getIndicadorIsencaoEsgoto() == null
														|| movimentoRoteiroImovel.getIndicadorIsencaoEsgoto().equals(new Short("2"))){

											consumoHistoricoEsgotoImovel.setIndicadorFaturamento(ConstantesSistema.SIM);
										}else if(movimentoRoteiroImovel.getIndicadorIsencaoEsgoto().equals(new Short("1"))){

											consumoHistoricoEsgotoImovel.setIndicadorFaturamento(ConstantesSistema.NAO);
										}

										if(movimentoRoteiroImovel.getNumeroConsumoCreditoFaturado() != null){

											consumoHistoricoEsgotoImovel.setConsumoMinimoCreditado(movimentoRoteiroImovel
															.getNumeroConsumoCreditoFaturado());
										}

										if(movimentoRoteiroImovel.getNumeroConsumoFaturadoEsgoto() != null){

											Integer numeroConsumoFaturadoMes = null;
											String indicadorEmissaoCampo = movimentoRoteiroImovel.getIndicadorEmissaoCampo();

											if(MovimentoRoteiroEmpresa.INDICADOR_EMISSAO_CAMPO_CONTA_RETIDA
															.equals(indicadorEmissaoCampo)){

												numeroConsumoFaturadoMes = movimentoRoteiroImovel.getNumeroConsumoMedio();
											}else{

												numeroConsumoFaturadoMes = movimentoRoteiroImovel.getNumeroConsumoFaturadoEsgoto();
											}

											consumoHistoricoEsgotoImovel.setNumeroConsumoFaturadoMes(numeroConsumoFaturadoMes);
										}

										consumoHistoricoEsgotoImovel.setConsumoImovelCondominio(null);

										// Preenche com ConsumoHistorico o Esgoto que vai ser usado
										// no [SB0002]
										arrayMedicaoConsumoHistorico[2] = consumoHistoricoEsgotoImovel;

										colecaoConsumoHistoricoImovel.add(consumoHistoricoEsgotoImovel);
									}

									// ---------------------------------------------------------
									// [SB0002 – Determinar Faturamento para o Imóvel] - Inicio
									// ---------------------------------------------------------
									determinarFaturamentoImovelAjusteParaFaturamentoImediato(imovel, anoMesCorrente,
													gerarAtividadeGrupoFaturamento, faturamentoAtivCronRota, colecaoResumoFaturamento,
													sistemaParametro, false, faturamentoGrupo, arrayMedicaoConsumoHistorico,
													movimentoRoteiroImovel);

								}
							}
						}

						arrayMedicaoConsumoHistorico = null;
					}

				}


				// [SB0007] - Gerar Resumo da Simulação do Faturamento
				// Inserer na Base.
				if(colecaoResumoFaturamento != null && !colecaoResumoFaturamento.isEmpty()){

					getControladorFaturamento().inserirResumoSimulacaoFaturamento(colecaoResumoFaturamento);
				}
			}

			log.info("**********Fim Registrar Imediato Ajuste do Grupo/Referência: " + faturamentoGrupo.getId().toString() + "/"
							+ anoMesCorrente.toString());
		}catch(Exception e){

			e.printStackTrace();
			throw new EJBException(e);
		}
	}

	private void determinarFaturamentoImovelAjusteParaFaturamentoImediato(Imovel imovel, int anoMesFaturamento,
					boolean gerarAtividadeGrupoFaturamento, FaturamentoAtivCronRota faturamentoAtivCronRota,
					Collection colecaoResumoFaturamento, SistemaParametro sistemaParametro, boolean antecipado,
					FaturamentoGrupo faturamentoGrupo, Object[] arrayMedicaoConsumoHistorico, MovimentoRoteiroEmpresa movimentoRoteiroImovel)
					throws ControladorException, ErroRepositorioException{

		// Coleção que vai armazenar as categorias do imóvel
		Collection colecaoCategorias = null;

		// [UC0108] - Obter Quantidade de Economias por Categoria
		colecaoCategorias = this.getControladorImovel().obterQuantidadeEconomiasCategoria(imovel);

		// [UC0120] - Calcular Valores de Água e/ou Esgoto
		BigDecimal valorTotalAgua = BigDecimal.ZERO;
		BigDecimal valorTotalEsgoto = BigDecimal.ZERO;
		Collection colecaoCalcularValoresAguaEsgotoHelper = null;
		Integer anoMesReferencia = anoMesFaturamento;
		BigDecimal percentualEsgoto = BigDecimal.ZERO;
		Integer consumoFaturadoMesAgua = null;
		Integer consumoFaturadoMesEsgoto = null;
		ConsumoHistorico consumoHistoricoAgua = null;
		ConsumoHistorico consumoHistoricoEsgoto = null;
		MedicaoHistorico medicaoHistoricoAgua = null;
		MedicaoHistorico medicaoHistoricoPoco = null;
		LigacaoTipo ligacaoTipoAgua = new LigacaoTipo();
		ligacaoTipoAgua.setId(LigacaoTipo.LIGACAO_AGUA);
		LigacaoTipo ligacaoTipoEsgoto = new LigacaoTipo();
		ligacaoTipoEsgoto.setId(LigacaoTipo.LIGACAO_ESGOTO);
		consumoHistoricoAgua = (ConsumoHistorico) arrayMedicaoConsumoHistorico[1];
		BigDecimal valorFaturadoAgua = BigDecimal.ZERO;
		BigDecimal valorFaturadoEsgoto = BigDecimal.ZERO;

		// ---------------------------------------------------------------------------------
		// [SB0002] - Determinar Valores para Faturamento de Água e/ou Esgoto - Início
		// ---------------------------------------------------------------------------------

		Integer ligacaoAguaSituacaoId = imovel.getLigacaoAguaSituacao().getId();
		Integer ligacaoEsgotoSituacaoId = imovel.getLigacaoEsgotoSituacao().getId();

		Calendar data = new GregorianCalendar();
		data.set(Calendar.YEAR, Integer.parseInt(String.valueOf(anoMesFaturamento).substring(0, 4)));
		data.set(Calendar.MONTH, Integer.parseInt(String.valueOf(anoMesFaturamento).substring(4, 6)) - 1);
		data.set(Calendar.DATE, 1);
		data.add(Calendar.MONTH, -1);

		String anoMesAnterior = "";
		anoMesAnterior = data.get(Calendar.YEAR) + "";

		if((data.get(Calendar.MONTH) + 1) < 10){

			anoMesAnterior = anoMesAnterior + "0" + (data.get(Calendar.MONTH) + 1);
		}else{

			anoMesAnterior = anoMesAnterior + (data.get(Calendar.MONTH) + 1);
		}

		Date dataLeituraAnteriorFaturamento = null;
		try{

			// Determina a data de leitura anterior do faturamento
			dataLeituraAnteriorFaturamento = (Date) repositorioFaturamento.pesquisarFaturamentoAtividadeCronogramaDataRealizacao(
							faturamentoGrupo.getId(), FaturamentoAtividade.EFETUAR_LEITURA, Integer.valueOf(anoMesAnterior));

		}catch(ErroRepositorioException ex){

			throw new ControladorException("erro.sistema", ex);
		}

		// Determina a data de leitura atual do faturamento
		Date dataLeituraAtualFaturamento = null;

		try{

			dataLeituraAtualFaturamento = (Date) repositorioFaturamento.pesquisarFaturamentoAtividadeCronogramaDataRealizacao(
							faturamentoGrupo.getId(), FaturamentoAtividade.EFETUAR_LEITURA, anoMesFaturamento);

		}catch(ErroRepositorioException ex){

			throw new ControladorException("erro.sistema", ex);
		}

		// Inicializa com zero o percentual de esgoto o consumo faturado do mês de água e de
		// esgoto
		percentualEsgoto = new BigDecimal(0);
		consumoFaturadoMesAgua = Integer.valueOf("0");
		consumoFaturadoMesEsgoto = Integer.valueOf("0");

		// Seta valores iniciais para os indicadores de faturamento de água e esgoto.
		Short indicadorFaturamentoAgua = Short.valueOf("2");
		Short indicadorFaturamentoEsgoto = Short.valueOf("2");

		// Verifica se existe consumo histórico de água
		if(consumoHistoricoAgua != null){

			if((imovel.getLigacaoAguaSituacao().getIndicadorFaturamentoSituacao().intValue() == LigacaoAguaSituacao.FATURAMENTO_ATIVO
							.intValue())
							|| (imovel.getLigacaoAguaSituacao().getIndicadorFaturamentoSituacao().intValue() == LigacaoAguaSituacao.NAO_FATURAVEL
											.intValue() && consumoHistoricoAgua.getNumeroConsumoFaturadoMes() != null && consumoHistoricoAgua
											.getNumeroConsumoFaturadoMes().intValue() > 0)){

				indicadorFaturamentoAgua = Short.valueOf("1");

				// caso exista indicador de Faturamento no Consumo, será assumido o do
				// ConsumoHistorico.
				if(consumoHistoricoAgua.getIndicadorFaturamento() != null){

					// Seta o indicador faturamento água
					indicadorFaturamentoAgua = consumoHistoricoAgua.getIndicadorFaturamento();
				}

				consumoFaturadoMesAgua = consumoHistoricoAgua.getNumeroConsumoFaturadoMes();

			}
		}

		consumoHistoricoEsgoto = (ConsumoHistorico) arrayMedicaoConsumoHistorico[2];

		if(consumoHistoricoEsgoto != null
						&& (imovel.getLigacaoEsgotoSituacao().getId().intValue() == LigacaoEsgotoSituacao.LIGADO.intValue() || (imovel
										.getLigacaoEsgotoSituacao().getId().equals(LigacaoEsgotoSituacao.FACTIVEL_FATURADA) && imovel
										.getLigacaoAguaSituacao().getId().equals(LigacaoAguaSituacao.LIGADO)))){

			if(consumoHistoricoEsgoto.getIndicadorFaturamento() != null){

				// Seta o indicador faturamento esgoto
				indicadorFaturamentoEsgoto = consumoHistoricoEsgoto.getIndicadorFaturamento();
			}

			if(consumoHistoricoEsgoto.getNumeroConsumoFaturadoMes() != null){

				consumoFaturadoMesEsgoto = consumoHistoricoEsgoto.getNumeroConsumoFaturadoMes();
			}
		}

		Short pVerificarConsumoLigacaoCortada = Short.valueOf((String) ParametroFaturamento.P_VERIFICAR_CONSUMO_LIGACAO_CORTADA.executar());

		// Caso o imóvel seja (ligado) ou (cortado com ou sem consumo de água)
		if(ligacaoAguaSituacaoId.equals(LigacaoAguaSituacao.LIGADO)
						|| (ligacaoAguaSituacaoId.equals(LigacaoAguaSituacao.CORTADO) && (pVerificarConsumoLigacaoCortada
										.equals(ConstantesSistema.NAO) || (pVerificarConsumoLigacaoCortada.equals(ConstantesSistema.SIM)
										&& consumoHistoricoAgua != null && consumoHistoricoAgua.getNumeroConsumoFaturadoMes() != null && consumoHistoricoAgua
										.getNumeroConsumoFaturadoMes().intValue() != 0)))){

			if(consumoHistoricoAgua != null && consumoHistoricoAgua.getIndicadorFaturamento() != null){

				indicadorFaturamentoAgua = consumoHistoricoAgua.getIndicadorFaturamento();
			}else{

				indicadorFaturamentoAgua = ConstantesSistema.NAO;
			}
		}else{

			indicadorFaturamentoAgua = ConstantesSistema.NAO;
		}

		// 2.5.1. Caso o imóvel seja ligado de esgoto:
		if(indicadorFaturamentoEsgoto != null && indicadorFaturamentoEsgoto.equals(ConstantesSistema.SIM)){

			Short pEsgotoSuprimido = Short.valueOf((String) ParametroFaturamento.P_ISENCAO_ESGOTO_SUPRIMIDO_CORTADO_SEM_CONSUMO.executar());

			// Caso o parâmetro de isenção de esgoto para suprimido esteja ativado
			// (PASI_VLPARAMETRO = 1 na tabela PARAMETRO_SISTEMA para PASI_DSPARAMETRO
			if(pEsgotoSuprimido.equals(ConstantesSistema.SIM)){

				// Caso o imóvel seja suprimido ou cortado (LAST_ID na tabela IMOVEL com
				// o valor correspondente a SUPRIMIDO ou CORTADO) e sem consumo de água
				// (CSHI_NNCONSUMOFATURADOMES = 0 da tabela CONSUMO_HISTORICO com IMOV_ID=IMOV_ID da
				// tabela IMOVEL, LGTP_ID com o valor correspondente a “ligação de água” e
				// CSHI_AMFATURAMENTO=Ano e mês de referência), atribuir o valor dois (2).

				boolean imovelSuprimidoSemConsumo = ligacaoAguaSituacaoId.equals(LigacaoAguaSituacao.SUPRIMIDO)
								&& consumoFaturadoMesAgua.intValue() == 0;

				// Imóvel cortardo sem consumo e com o parâmetro de verificar consumo ativo
				boolean imovelCortadoSemConsumoComVerificacao = ligacaoAguaSituacaoId.equals(LigacaoAguaSituacao.CORTADO)
								&& pVerificarConsumoLigacaoCortada.equals(ConstantesSistema.SIM) && consumoFaturadoMesAgua.intValue() == 0;

				if(imovelSuprimidoSemConsumo || imovelCortadoSemConsumoComVerificacao){

					indicadorFaturamentoEsgoto = ConstantesSistema.NAO;

				}else{

					// Caso contrário, atribuir CSHI_ICFATURAMENTO da tabela
					// CONSUMO_HISTORICO com IMOV_ID=IMOV_ID da tabela IMOVEL, LGTP_ID com o valor
					// correspondente a “ligação de esgoto” e CSHI_AMFATURAMENTO=Ano e mês de
					// referência.

					if(consumoHistoricoEsgoto != null && consumoHistoricoEsgoto.getIndicadorFaturamento() != null){

						indicadorFaturamentoEsgoto = consumoHistoricoEsgoto.getIndicadorFaturamento();
						consumoHistoricoEsgoto.setReferenciaFaturamento(anoMesFaturamento);

					}

				}

			}else{

				// Caso contrário, ou seja, caso o parâmetro de isenção de esgoto para suprimido
				// esteja desativado (PASI_VLPARAMETRO = 2 na tabela PARAMETRO_SISTEMA para
				// PASI_DSPARAMETRO = ” P_ISENCAO_ESGOTO_SUPRIMIDO_CORTADO_SEM_CONSUMO”),
				// atribuir CSHI_ICFATURAMENTO da tabela CONSUMO_HISTORICO com IMOV_ID=IMOV_ID da
				// tabela IMOVEL, LGTP_ID com o valor correspondente a “ligação de esgoto” e
				// CSHI_AMFATURAMENTO=Ano e mês de referência,

				if(consumoHistoricoEsgoto != null){

					indicadorFaturamentoEsgoto = consumoHistoricoEsgoto.getIndicadorFaturamento();
					consumoHistoricoEsgoto.setReferenciaFaturamento(anoMesFaturamento);

				}

			}

		}else{

			// 2.5.2. Caso contrário, ou seja, caso o imóvel não seja ligado de esgoto, atribuir o
			// valor um (2).
			indicadorFaturamentoEsgoto = ConstantesSistema.NAO;

		}

		// Verifica se existe medição histórico
		medicaoHistoricoAgua = (MedicaoHistorico) arrayMedicaoConsumoHistorico[0];
		if(medicaoHistoricoAgua != null){

			// Seta a data de letura anterior faturamento
			if(medicaoHistoricoAgua.getDataLeituraAnteriorFaturamento() != null){

				dataLeituraAnteriorFaturamento = medicaoHistoricoAgua.getDataLeituraAnteriorFaturamento();
			}

			// Seta a data de leitura atual faturamento
			if(medicaoHistoricoAgua.getDataLeituraAtualFaturamento() != null){

				dataLeituraAtualFaturamento = medicaoHistoricoAgua.getDataLeituraAtualFaturamento();
			}
		}

		// Caso o imóvel seja ligado de esgoto
		if(imovel.getLigacaoEsgoto() != null
						&& imovel.getLigacaoEsgotoSituacao() != null
						&& (imovel.getLigacaoEsgotoSituacao().getId().intValue() == LigacaoEsgotoSituacao.LIGADO.intValue() || (imovel
										.getLigacaoEsgotoSituacao().getId().equals(LigacaoEsgotoSituacao.FACTIVEL_FATURADA) && imovel
										.getLigacaoAguaSituacao().getId().equals(LigacaoAguaSituacao.LIGADO)))){
			// consultar medicao hisotrico tipo poco
			medicaoHistoricoPoco = this.getControladorMicromedicao().pesquisarMedicaoHistoricoTipoPoco(imovel.getId(), anoMesReferencia);

			// Verifica se existe medição histórico para poço
			if(medicaoHistoricoPoco != null){

				// Seta a data de leitura anterior faturamento
				if(medicaoHistoricoPoco.getDataLeituraAnteriorFaturamento() != null){

					dataLeituraAnteriorFaturamento = medicaoHistoricoPoco.getDataLeituraAnteriorFaturamento();
				}

				// Seta a data atual de faturamento
				if(medicaoHistoricoPoco.getDataLeituraAtualFaturamento() != null){

					dataLeituraAtualFaturamento = medicaoHistoricoPoco.getDataLeituraAtualFaturamento();
				}
			}
			// Recupera o percentual de esgoto.
			percentualEsgoto = getControladorFaturamento().obterPercentualLigacaoEsgotoImovel(imovel.getId());
		}

		// [UC0105] - Obter Consumo Mínimo da Ligação
		int consumoMinimoLigacao = getControladorMicromedicao().obterConsumoMinimoLigacao(imovel, colecaoCategorias);

		// Tarifa para o imóvel (Caso exista tarifa temporária para o imóvel
		// (CSTF_IDTEMPORARIA com o valor diferente de nulo na tabela IMOVEL)
		// e a tarifa temporária esteja vigente (IMOV_DTVALIDADETARIFATEMP maior ou igual à data
		// corrente), atribuir CSTF_IDTEMPORARIA;
		// Caso contrário, atribuir CSTF_ID da tabela IMOVEL).
		ConsumoTarifa consumoTarifaConta = null;
		boolean possuiTarifaTemporariaValida = false;

		if(imovel.getConsumoTarifaTemporaria() != null && imovel.getDataValidadeTarifaTemporaria() != null
						&& Util.compararData(imovel.getDataValidadeTarifaTemporaria(), new Date()) >= 0){

			log.info("Imovel[" + imovel.getId() + "] possue tarifa [" + imovel.getConsumoTarifaTemporaria().getId().toString()
							+ "] temporária válida.");
			consumoTarifaConta = imovel.getConsumoTarifaTemporaria();
			possuiTarifaTemporariaValida = true;

			log.info("Avaliar perda de benefícios");
			// [FS0011] E [FS0014]
			consumoTarifaConta = getControladorFaturamento().verificarPerdaBeneficioDaTarifaSocialParaFaturamento(consumoFaturadoMesAgua,
							consumoMinimoLigacao, imovel);

		}else{

			// Obtém a tarifa do imóvel
			consumoTarifaConta = imovel.getConsumoTarifa();
			log.info("Imovel[" + imovel.getId() + "] tarifa padrão [" + imovel.getConsumoTarifa().getId().toString() + "].");
		}

		// Caso o imóvel esteja incluso no programa água para todos(iapt_id diferente de Null)
		if(imovel.getImovelAguaParaTodos() != null){

			// Caso o imóvel esteja incluso no programa água para todos e o consumo seja
			// maior que [PARÂMETRO – PARM_NNCONSUMOMINIMOAPT], assumir a tarifa padrão do
			// imóvel para faturamento desconsiderando a atual
			if(sistemaParametro.getNumeroConsumoMinAguaParaTodos() != null
							&& consumoFaturadoMesAgua > sistemaParametro.getNumeroConsumoMinAguaParaTodos()){

				consumoTarifaConta = imovel.getConsumoTarifa();
			}
		}

		// Caso tenha o imóvel tenha tarifa temporária válida e ela tenha sido perdida por alguma
		// regra anterior
		if(possuiTarifaTemporariaValida && !consumoTarifaConta.getId().equals(imovel.getConsumoTarifaTemporaria().getId())){

			// [UC0105] - Obter Consumo Mínimo da Ligação baseado na tarifa padrão do imóvel
			Imovel imovelAux = new Imovel();
			imovelAux.setConsumoTarifa(imovel.getConsumoTarifa());
			consumoMinimoLigacao = getControladorMicromedicao().obterConsumoMinimoLigacao(imovelAux, colecaoCategorias);
		}

		// ---------------------------------------------------------------------------------
		// [SB0003] - Determinar Valores para Faturamento de Água e/ou Esgoto - Início
		// ---------------------------------------------------------------------------------

		Integer consumoFaturadoMesAguaAux = null;

		String indicadorEmissaoCampo = null;

		if(movimentoRoteiroImovel != null){
			indicadorEmissaoCampo = movimentoRoteiroImovel.getIndicadorEmissaoCampo();
		}

		if(MovimentoRoteiroEmpresa.INDICADOR_EMISSAO_CAMPO_CONTA_RETIDA.equals(indicadorEmissaoCampo)){

			consumoFaturadoMesAguaAux = movimentoRoteiroImovel.getNumeroConsumoMedio();
		}else{

			consumoFaturadoMesAguaAux = consumoFaturadoMesAgua;
		}

		// [UC0120] - Calcular Valores de Água e/ou Esgoto
		colecaoCalcularValoresAguaEsgotoHelper = getControladorFaturamento().calcularValoresAguaEsgoto(anoMesReferencia,
						ligacaoAguaSituacaoId, ligacaoEsgotoSituacaoId, indicadorFaturamentoAgua, indicadorFaturamentoEsgoto,
						colecaoCategorias, consumoFaturadoMesAguaAux, consumoFaturadoMesEsgoto, consumoMinimoLigacao,
						dataLeituraAnteriorFaturamento, dataLeituraAtualFaturamento, percentualEsgoto, consumoTarifaConta.getId(),
						imovel.getId());

		for(Iterator iteratorColecaoCalcularValoresAguaEsgotoHelper = colecaoCalcularValoresAguaEsgotoHelper.iterator(); iteratorColecaoCalcularValoresAguaEsgotoHelper
						.hasNext();){

			CalcularValoresAguaEsgotoHelper calcularValoresAguaEsgotoHelper = (CalcularValoresAguaEsgotoHelper) iteratorColecaoCalcularValoresAguaEsgotoHelper
							.next();
			/*
			 * Caso tenha valor de água faturado para categoria adiciona o valor de água ao
			 * valor total de água.
			 */
			if(calcularValoresAguaEsgotoHelper.getValorFaturadoAguaCategoria() != null){

				valorTotalAgua = valorTotalAgua.add(calcularValoresAguaEsgotoHelper.getValorFaturadoAguaCategoria());
			}

			if(calcularValoresAguaEsgotoHelper.getValorFaturadoEsgotoCategoria() != null){

				valorTotalEsgoto = valorTotalEsgoto.add(calcularValoresAguaEsgotoHelper.getValorFaturadoEsgotoCategoria());
			}
		}

		// ---------------------------------------------------------------------------------
		// [SB0003] - Determinar Valores para Faturamento de Água e/ou Esgoto - Fim
		// ---------------------------------------------------------------------------------

		boolean gerouDivergencia = false;
		if(movimentoRoteiroImovel != null){

			// Confronta os Valores de Água e Esgoto gerados
			if(movimentoRoteiroImovel.getValorAgua() != null && !movimentoRoteiroImovel.getValorAgua().equals(new BigDecimal(0))){

				valorFaturadoAgua = movimentoRoteiroImovel.getValorAgua();
			}

			if(movimentoRoteiroImovel.getValorEsgoto() != null && !movimentoRoteiroImovel.getValorEsgoto().equals(new BigDecimal(0))){

				valorFaturadoEsgoto = movimentoRoteiroImovel.getValorEsgoto();
			}

			if(valorTotalAgua.compareTo(valorFaturadoAgua) != 0 || valorTotalEsgoto.compareTo(valorFaturadoEsgoto) != 0){

				gerouDivergencia = true;
			}
		}

		// ---------------------------------------------------------------------------------
		// [SB0004] - Gerar Creditoos Realizados - Início
		// ---------------------------------------------------------------------------------

		BigDecimal valorTotalDebitos = movimentoRoteiroImovel.getValorDebitos();
		BigDecimal valorTotalCreditos = new BigDecimal("0.00");

		if(movimentoRoteiroImovel.getValorTotalConta().compareTo(BigDecimal.ZERO) == 1){

			// ---------------------------------------------------------------------------------
			// [SB0005] - Atualizar Dados da Conta - Início

			// É necessário definir uma data de emissão na conta. Essa data é utilizada no processo
			// de arrecadação.
			Date dataEmissao = movimentoRoteiroImovel.getDataEmissao();

			if(dataEmissao == null){

				dataEmissao = movimentoRoteiroImovel.getDataLeitura();

				if(dataEmissao == null){

					dataEmissao = new Date();
				}
			}

			if(gerouDivergencia){

				if(indicadorFaturamentoAgua.equals(ConstantesSistema.NAO) && indicadorFaturamentoEsgoto.equals(ConstantesSistema.NAO)){

					indicadorFaturamentoAgua = ConstantesSistema.SIM;

					// [UC0120] - Calcular Valores de Água e/ou Esgoto
					colecaoCalcularValoresAguaEsgotoHelper = getControladorFaturamento().calcularValoresAguaEsgoto(anoMesReferencia,
									ligacaoAguaSituacaoId, ligacaoEsgotoSituacaoId, indicadorFaturamentoAgua, indicadorFaturamentoEsgoto,
									colecaoCategorias, consumoFaturadoMesAguaAux, consumoFaturadoMesEsgoto, consumoMinimoLigacao,
									dataLeituraAnteriorFaturamento, dataLeituraAtualFaturamento, percentualEsgoto,
									consumoTarifaConta.getId(), imovel.getId());
				}

			}

			// ---------------------------------------------------------------------------------
			// [SB0007] - Gerar Resumo da Simulação do Faturamento - Início
			// ---------------------------------------------------------------------------------

			Imovel imovelBase = this.getControladorCliente().obterClienteImovelResponsavel(imovel.getId());
			if(imovelBase != null){

				if(imovelBase.getClienteImoveis() != null){

					imovel.setClienteImoveis(imovelBase.getClienteImoveis());
				}
			}else{

				imovel.setClienteImoveis(null);
			}

			getControladorFaturamento().gerarResumoSimulacaoFaturamento(colecaoCategorias, valorTotalDebitos, valorTotalCreditos,
							colecaoCalcularValoresAguaEsgotoHelper, colecaoResumoFaturamento, faturamentoAtivCronRota, imovel,
							anoMesReferencia, null, faturamentoGrupo);
		}

	}

	public void executarAjusteContasEnviadasHistoricoPreFaturadasComValorZero(String idsFaturamentoGrupo) throws Exception{

		// Inicializa as instacias dos repositórios usados
		this.ejbCreate();

		// Declaração das variáveis
		String[] idsGrupoFaturamento = idsFaturamentoGrupo.split(",");
		Collection<Integer> colecaoIdsContaHistorico = null;
		Collection<ContaHistorico> colecaoContaHistorico = null;
		int quantidadeContasGrupo;

		for(int i = 0; i < idsGrupoFaturamento.length; i++){

			// Obtém as rotas do grupo
			FiltroRota filtroRota = new FiltroRota();
			filtroRota.adicionarParametro(new ParametroSimples(FiltroRota.FATURAMENTO_GRUPO_ID, Util.obterInteger(idsGrupoFaturamento[i])));
			filtroRota.setCampoOrderBy(FiltroRota.ID_ROTA);
			Collection<Rota> colecaoRota = getControladorUtil().pesquisar(filtroRota, Rota.class.getName());
			quantidadeContasGrupo = 0;

			System.out.println("Ajuste faturamento das contas do grupo: " + idsGrupoFaturamento[i]);

			// Processa o ajuste por rota
			for(Rota rota : colecaoRota){

				colecaoIdsContaHistorico = repositorioFaturamento.pesquisarIdsContaAjusteContasEnviadasHistoricoPreFaturadasComValor(rota
								.getId().toString());

				colecaoContaHistorico = new ArrayList<ContaHistorico>();
				for(Integer idContaHistorico : colecaoIdsContaHistorico){

					ContaHistorico contaHistorico = (ContaHistorico) getControladorUtil().pesquisar(idContaHistorico, ContaHistorico.class,
									false);
					colecaoContaHistorico.add(contaHistorico);
					quantidadeContasGrupo++;
				}

				if(!Util.isVazioOrNulo(colecaoContaHistorico)){

					getControladorFaturamento().transferirContasHistoricoParaConta(colecaoContaHistorico, 201306,
								DebitoCreditoSituacao.PRE_FATURADA);
				}
			}

			FaturamentoGrupo faturamentoGrupo = (FaturamentoGrupo) getControladorUtil().pesquisar(
							Util.obterInteger(idsGrupoFaturamento[i]), FaturamentoGrupo.class, false);

			System.out.println("Quantidade de Contas:--->" + quantidadeContasGrupo);
			// getControladorFaturamento().registrarFaturamentoImediatoGrupoFaturamentoAjuste(colecaoRota,
			// faturamentoGrupo, 201306);

			// coloca um registro fixo na tabela relacao_trres_deso para indicar que finalizou o
			// processamento do grupo
			RelacaoTresDeso relacaoTresDeso = new RelacaoTresDeso();
			relacaoTresDeso.setIdFaturamentoGrupo(faturamentoGrupo.getId());
			relacaoTresDeso.setUltimaAlteracao(new Date());
			relacaoTresDeso.setIdImovel(10);
			getControladorUtil().inserir(relacaoTresDeso);

		}
	}

	public void executarAjusteHidrometroInstaladoMeioCicloFaturamento(String idsFaturamentoGrupo, Integer anoMesReferencia)
					throws Exception{

		// Inicializa as instacias dos repositórios usados
		this.ejbCreate();

		// Declaração das variáveis
		String[] idsGrupoFaturamento = idsFaturamentoGrupo.split(",");

		for(int i = 0; i < idsGrupoFaturamento.length; i++){

			// Obtém as rotas do grupo
			FiltroRota filtroRota = new FiltroRota();
			filtroRota.adicionarParametro(new ParametroSimples(FiltroRota.FATURAMENTO_GRUPO_ID, Util.obterInteger(idsGrupoFaturamento[i])));
			filtroRota.setCampoOrderBy(FiltroRota.ID_ROTA);
			Collection<Rota> colecaoRota = getControladorUtil().pesquisar(filtroRota, Rota.class.getName());


			FaturamentoGrupo faturamentoGrupo = (FaturamentoGrupo) getControladorUtil().pesquisar(
							Util.obterInteger(idsGrupoFaturamento[i]), FaturamentoGrupo.class, false);

			// getControladorFaturamento().registrarFaturamentoImediatoGrupoFaturamentoAjuste(colecaoRota,
			// faturamentoGrupo, anoMesReferencia);

			// coloca um registro fixo na tabela relacao_trres_deso para indicar que finalizou o
			// processamento do grupo
			RelacaoTresDeso relacaoTresDeso = new RelacaoTresDeso();
			relacaoTresDeso.setIdFaturamentoGrupo(faturamentoGrupo.getId());
			relacaoTresDeso.setUltimaAlteracao(new Date());
			relacaoTresDeso.setIdImovel(1990);
			getControladorUtil().inserir(relacaoTresDeso);

		}
	}

	/**
	 * @param anoMesRefInicial
	 * @param anoMesRefFinal
	 * @param idsCliente
	 * @throws Exception
	 */
	public void executarAjusteContasEnviadasHistorico(Integer anoMesRefInicial, Integer anoMesRefFinal, String[] idsCliente)
					throws Exception{

		// Inicializa as instacias dos repositórios usados
		this.ejbCreate();

		// Declaração das variáveis
		Collection<Integer> colecaoIdsContaHistorico = null;
		Collection<ContaHistorico> colecaoContaHistorico = null;
		int quantidadeContas = 0;

		System.out.println("Ajuste faturamento das contas : anoMesRefInicial/anoMesRefFinal :" + anoMesRefInicial + "/" + anoMesRefFinal);

		colecaoIdsContaHistorico = repositorioFaturamento.pesquisarIdsContaAjusteContasEnviadasHistorico(anoMesRefInicial, anoMesRefFinal,
						idsCliente);

		colecaoContaHistorico = new ArrayList<ContaHistorico>();
		for(Integer idContaHistorico : colecaoIdsContaHistorico){

			ContaHistorico contaHistorico = (ContaHistorico) getControladorUtil().pesquisar(idContaHistorico, ContaHistorico.class, false);

			Collection<PagamentoHistorico> colecaoPagamentoHistorico = this.getControladorArrecadacao()
							.selecionarPagamentoHistoricoPorContaHistorico(contaHistorico.getId());

			if(colecaoPagamentoHistorico != null && colecaoPagamentoHistorico.isEmpty()){

				contaHistorico.setAnoMesReferenciaContabil(contaHistorico.getAnoMesReferenciaConta());
				contaHistorico.setContaMotivoCancelamento(null);

				ContaMotivoRevisao contaMotivoRevisao = new ContaMotivoRevisao();
				contaMotivoRevisao.setId(34);

				contaHistorico.setContaMotivoRevisao(contaMotivoRevisao);

				colecaoContaHistorico.add(contaHistorico);

				quantidadeContas++;
			}

		}

		System.out.println("Quantidade de Contas a processar:--->" + quantidadeContas);

		if(!Util.isVazioOrNulo(colecaoContaHistorico)){

			getControladorFaturamento().transferirContasHistoricoParaConta(colecaoContaHistorico, 201312, DebitoCreditoSituacao.NORMAL);
		}

		System.out.println("*************************Quantidade de Contas PROCESSADAS :--->" + quantidadeContas);

	

	}

	/**
	 * Método que realiza o ajuste do faturamento para trazer de volta as contas que estão em
	 * conta_historico para tabela de conta devido a um erro do faturamento
	 * 
	 * @author Anderson Italo
	 * @date 09/01/2014
	 */
	public void executarAjusteContasEnviadasHistoricoPreFaturadasComValorZeroIndicadorEmissaoCampo3(String idsFaturamentoGrupo)
					throws Exception{

		// Inicializa as instancias dos repositórios usados
		this.ejbCreate();

		// Declaração das variáveis
		String[] idsGrupoFaturamento = idsFaturamentoGrupo.split(",");
		Collection<Integer> colecaoIdsContaHistorico = null;
		Collection<ContaHistorico> colecaoContaHistorico = null;
		int quantidadeContasGrupo;

		for(int i = 0; i < idsGrupoFaturamento.length; i++){

			// Obtém as rotas do grupo
			FiltroRota filtroRota = new FiltroRota();
			filtroRota.adicionarParametro(new ParametroSimples(FiltroRota.FATURAMENTO_GRUPO_ID, Util.obterInteger(idsGrupoFaturamento[i])));
			filtroRota.setCampoOrderBy(FiltroRota.ID_ROTA);
			Collection<Rota> colecaoRota = getControladorUtil().pesquisar(filtroRota, Rota.class.getName());
			quantidadeContasGrupo = 0;

			System.out.println("Ajuste Faturamento Contas Enviadas Historico Pre Faturadas Com Valor Zero Indicador Emissao Campo 3 grupo: "
							+ idsGrupoFaturamento[i]);

			// Processa o ajuste por rota
			for(Rota rota : colecaoRota){

				colecaoIdsContaHistorico = repositorioFaturamento
								.pesquisarIdsContaAjusteContasEnviadasHistoricoPreFaturadasZeradasIndicadorEmissaoCampo3(rota.getId());

				colecaoContaHistorico = new ArrayList<ContaHistorico>();
				for(Integer idContaHistorico : colecaoIdsContaHistorico){

					ContaHistorico contaHistorico = (ContaHistorico) getControladorUtil().pesquisar(idContaHistorico, ContaHistorico.class,
									false);
					colecaoContaHistorico.add(contaHistorico);
					quantidadeContasGrupo++;
				}

				if(!Util.isVazioOrNulo(colecaoContaHistorico)){

					getControladorFaturamento().transferirContasHistoricoParaConta(colecaoContaHistorico, 201401,
									DebitoCreditoSituacao.PRE_FATURADA);
				}
			}

			FaturamentoGrupo faturamentoGrupo = (FaturamentoGrupo) getControladorUtil().pesquisar(
							Util.obterInteger(idsGrupoFaturamento[i]), FaturamentoGrupo.class, false);

			System.out.println("Quantidade de Contas:--->" + quantidadeContasGrupo);

			// Coloca um registro fixo na tabela relacao_tres_deso para indicar que finalizou o
			// processamento do grupo
			RelacaoTresDeso relacaoTresDeso = new RelacaoTresDeso();
			relacaoTresDeso.setIdFaturamentoGrupo(faturamentoGrupo.getId());
			relacaoTresDeso.setUltimaAlteracao(new Date());
			relacaoTresDeso.setIdImovel(201401);
			getControladorUtil().inserir(relacaoTresDeso);

		}
	}

	public void executarAjusteCoordenadasGIS(Collection colecao) throws Exception{

		// Inicializa as instancias dos repositorios usados
		this.ejbCreate();

		String[] valores = null;
		Iterator it = colecao.iterator();
		DadosAcquaGis dadosAcquaGis = new DadosAcquaGis();

		int count = 0;

		while(it.hasNext()){
			String dadosGis = (String) it.next();
			valores = dadosGis.split(";");

			if(!Util.isVazioOuBranco(valores[0])){
				dadosAcquaGis.setIdImovel(valores[0]);
				dadosAcquaGis.setNumeroCoordenadaNorte(valores[1]);
				dadosAcquaGis.setNumeroCoordenadaLeste(valores[2]);

				count = count + 1;

				this.getControladorRegistroAtendimento().atualizarCoordenadasGis(dadosAcquaGis);

				System.out.println("*********************Atualizando: Qtd = " + count + " - Imovel = " + dadosAcquaGis.getIdImovel());
			}



		}

		System.out.println("*********************Fim do executarAjusteCoordenadasGIS*********************** + Quantidade Imóveis = "
						+ count);

	}

	public void executarAjusteErroCalculoConsumoMedioPercentualColeta(Collection<Integer> colecaoReferenciasAjuste) throws Exception{

		// Inicializa as instancias dos repositórios usados
		this.ejbCreate();

		Collection<Integer> colecaoIdsLigacaoEsgotoAjuste = this.repositorioFaturamento
						.pesquisarIdsLigacaoEsgotoAjusteErroCalculoConsumoMedio();
		Date dataAtualizacaoMedia = Util.converteStringParaDateHora("16/02/2014 12:00:00");
		int quantidadeRegistrosAtualizados = 0;

		for(Integer anoMesReferencia : colecaoReferenciasAjuste){

			SistemaParametro sistemaParametro = getControladorUtil().pesquisarParametrosDoSistema();
			sistemaParametro.setAnoMesFaturamento(anoMesReferencia);

			log.info("Ajuste Média ano mês/referência: " + anoMesReferencia.toString());

			for(Iterator iterator = colecaoIdsLigacaoEsgotoAjuste.iterator(); iterator.hasNext();){

				Integer idImovel = (Integer) iterator.next();

				Imovel imovel = new Imovel();
				imovel.setId(idImovel);

				int[] consumoMedioImovel = getControladorMicromedicao().obterConsumoMedioImovel(imovel, sistemaParametro);

				if(consumoMedioImovel != null){

					Integer consumoMedioRecalculado = consumoMedioImovel[0];
					Integer qtdMesesValidoCalculo = consumoMedioImovel[1];

					if(consumoMedioRecalculado.intValue() > 0){

						Collection<ConsumoHistorico> colecaoConsumoHistorico = repositorioMicromedicao
										.pesquisarConsumoHistoricoPorReferenciaFaturamentoImovel(idImovel, anoMesReferencia);

						boolean primeiraVez = true;

						for(ConsumoHistorico consumoHistorico : colecaoConsumoHistorico){

							int consumoMedioAnterior = consumoHistorico.getConsumoMedio() != null ? consumoHistorico.getConsumoMedio() : 0;

							if(consumoMedioAnterior < consumoMedioRecalculado.intValue()){

								if(primeiraVez){

									log.info("Verificando Atualização da média do imóvel: " + idImovel.toString());
									log.info("Quantidade de meses usado no cálculo: " + qtdMesesValidoCalculo);
									log.info("Antiga Média do imóvel: " + consumoMedioAnterior);
									log.info("Nova Média do imóvel: " + consumoMedioRecalculado.toString());
									primeiraVez = false;

									RelacaoTresDeso relacaoTresDeso = new RelacaoTresDeso();
									relacaoTresDeso.setUltimaAlteracao(new Date());
									relacaoTresDeso.setIdImovel(idImovel);
									relacaoTresDeso.setAnoMesReferencia(anoMesReferencia);
									relacaoTresDeso.setMediaAnterior(consumoMedioAnterior);
									relacaoTresDeso.setNovaMedia(consumoMedioRecalculado);
									getControladorUtil().inserir(relacaoTresDeso);
								}

								consumoHistorico.setConsumoMedio(consumoMedioRecalculado);
								consumoHistorico.setUltimaAlteracao(dataAtualizacaoMedia);
								getControladorUtil().atualizar(consumoHistorico);
								quantidadeRegistrosAtualizados++;
							}
						}
					}
				}
			}
		}

		// Coloca um registro fixo na tabela relacao_tres_deso para indicar que finalizou o
		// processamento da referência
		RelacaoTresDeso relacaoTresDeso = new RelacaoTresDeso();
		relacaoTresDeso.setUltimaAlteracao(new Date());
		relacaoTresDeso.setAnoMesReferencia(205001);
		getControladorUtil().inserir(relacaoTresDeso);
		log.info("Quantidade de Registros Atualizados em consumo_historico: " + quantidadeRegistrosAtualizados);
	}

	/**
	 * @param contasHistorico
	 * @return
	 * @throws ControladorException
	 */

	public Collection conveterContasHistoricoParaConta(Collection<ContaHistorico> contasHistorico) throws ControladorException{


			// cria um objeto da conta
			Conta contaTemp = null;

			Collection colecaoContaInserir = new ArrayList();

		HashSet colecaoContaCategoriaInserir = new HashSet();

			if(contasHistorico != null && !contasHistorico.isEmpty()){
				int cont = 0;
				// laço para criar os históricos das contas canceladas
				for(ContaHistorico contaHistorico : contasHistorico){
					cont++;
					System.out.println("################### GERANDO CONTAS ATIVAS:" + cont + "#########################");

					Integer idConta = contaHistorico.getId();

					// seta a conta com os dados da conta histórico
					contaTemp = new Conta();
					ContaGeral contaGeral = new ContaGeral();
					contaGeral.setId(contaHistorico.getId());
					contaTemp.setContaGeral(contaGeral);
					contaTemp.setId(idConta);
					contaTemp.setReferencia(contaHistorico.getAnoMesReferenciaConta());
					contaTemp.setUltimaAlteracao(new Date());
					contaTemp.setImovel(contaHistorico.getImovel());
					contaTemp.setLote(contaHistorico.getLote());
					contaTemp.setSubLote(contaHistorico.getSublote());
					contaTemp.setCodigoSetorComercial(contaHistorico.getSetorComercial());
					contaTemp.setQuadra(contaHistorico.getNumeroQuadra());
					contaTemp.setQuadraConta(new Quadra(contaHistorico.getQuadra().getId()));
					contaTemp.setDigitoVerificadorConta(contaHistorico.getVerificadorConta());
					contaTemp.setIndicadorCobrancaMulta(contaHistorico.getIndicadorCobrancaMulta());
					contaTemp.setIndicadorAlteracaoVencimento(contaHistorico.getIndicadorAlteracaoVencimento());
					contaTemp.setConsumoAgua(contaHistorico.getConsumoAgua());
					contaTemp.setConsumoEsgoto(contaHistorico.getConsumoEsgoto());
					contaTemp.setConsumoRateioAgua(contaHistorico.getConsumoRateioAgua());
					contaTemp.setConsumoRateioEsgoto(contaHistorico.getConsumoRateioEsgoto());
					contaTemp.setValorAgua(contaHistorico.getValorAgua());
					contaTemp.setValorEsgoto(contaHistorico.getValorEsgoto());
					contaTemp.setDebitos(contaHistorico.getValorDebitos());
					contaTemp.setValorCreditos(contaHistorico.getValorCreditos());
					contaTemp.setValorImposto(contaHistorico.getValorImposto());
					contaTemp.setPercentualEsgoto(contaHistorico.getPercentualEsgoto());
					contaTemp.setDataVencimentoConta(contaHistorico.getDataVencimentoConta());
					contaTemp.setDataValidadeConta(contaHistorico.getDataValidadeConta());
					contaTemp.setDataInclusao(contaHistorico.getDataInclusao());
					contaTemp.setDataRevisao(contaHistorico.getDataRevisao());
					contaTemp.setDataRetificacao(contaHistorico.getDataRetificacao());
					contaTemp.setDataCancelamento(contaHistorico.getDataCancelamento());
					contaTemp.setDataEmissao(contaHistorico.getDataEmissao());
					contaTemp.setReferenciaContabil(contaHistorico.getAnoMesReferenciaContabil());
					contaTemp.setReferenciaBaixaContabil(contaHistorico.getAnoMesReferenciaBaixaContabil());
					contaTemp.setLigacaoEsgotoSituacao(contaHistorico.getLigacaoEsgotoSituacao());
					contaTemp.setLigacaoAguaSituacao(contaHistorico.getLigacaoAguaSituacao());
					contaTemp.setMotivoNaoEntregaDocumento(contaHistorico.getMotivoNaoEntregaDocumento());
					contaTemp.setLocalidade(contaHistorico.getLocalidade());
					contaTemp.setContaMotivoInclusao(contaHistorico.getContaMotivoInclusao());
					contaTemp.setContaMotivoRevisao(contaHistorico.getContaMotivoRevisao());
					contaTemp.setContaMotivoRetificacao(contaHistorico.getContaMotivoRetificacao());
					contaTemp.setContaMotivoCancelamento(contaHistorico.getContaMotivoCancelamento());
					contaTemp.setFaturamentoTipo(contaHistorico.getFaturamentoTipo());
					contaTemp.setImovelPerfil(contaHistorico.getImovelPerfil());
					contaTemp.setRegistroAtendimento(contaHistorico.getRegistroAtendimento());
					contaTemp.setConsumoTarifa(contaHistorico.getConsumoTarifa());
					contaTemp.setIndicadorDebitoConta(contaHistorico.getIndicadorDebitoConta());
					contaTemp.setFuncionarioEntrega(contaHistorico.getFuncionarioEntrega());
					contaTemp.setFuncionarioLeitura(contaHistorico.getFuncionarioLeitura());
					contaTemp.setUltimaAlteracao(new Date());
					contaTemp.setUsuario(contaHistorico.getUsuario());

					// Alterado por Eduardo Henrique 09/12/2008
					contaTemp.setDebitoCreditoSituacaoAtual(contaHistorico.getDebitoCreditoSituacaoAtual());
					contaTemp.setDebitoCreditoSituacaoAnterior(null);
					contaTemp.setDocumentoTipo(contaHistorico.getDocumentoTipo());
					contaTemp.setContaBancaria(contaHistorico.getContaBancaria());
					contaTemp.setDataVencimentoOriginal(contaHistorico.getDataVencimentoOriginal());
					contaTemp.setRota(contaHistorico.getRota());
					contaTemp.setIndicadorCobrancaAdministrativa(contaHistorico.getIndicadorCobrancaAdministrativa());
					contaTemp.setIndicadorRemuneraCobrancaAdministrativa(contaHistorico.getIndicadorRemuneraCobrancaAdministrativa());


				Collection<ContaCategoriaHistorico> colecaoContaCategoriaHistorico;
				try{
					colecaoContaCategoriaHistorico = repositorioFaturamento.pesquisarContaCategoriaHistorico(idConta);

					ContaCategoria contaCategoriaTemp = null;

					if(colecaoContaCategoriaHistorico != null && !colecaoContaCategoriaHistorico.isEmpty()){

						for(ContaCategoriaHistorico contaContaCategoriaHistorico : colecaoContaCategoriaHistorico){
							contaCategoriaTemp = new ContaCategoria();
							contaCategoriaTemp.setComp_id(new ContaCategoriaPK(contaTemp, contaContaCategoriaHistorico.getComp_id()
											.getCategoria()));
							contaCategoriaTemp.getComp_id().setSubcategoria(contaContaCategoriaHistorico.getComp_id().getSubcategoria());
							contaCategoriaTemp.setConsumoAgua(contaContaCategoriaHistorico.getConsumoAgua());
							contaCategoriaTemp.setConsumoEsgoto(contaContaCategoriaHistorico.getConsumoEsgoto());
							contaCategoriaTemp.setConsumoMinimoAgua(contaContaCategoriaHistorico.getConsumoMinimoAgua());
							contaCategoriaTemp.setConsumoMinimoEsgoto(contaContaCategoriaHistorico.getConsumoMinimoEsgoto());
							contaCategoriaTemp.setQuantidadeEconomia(contaContaCategoriaHistorico.getQuantidadeEconomia());
							contaCategoriaTemp.setUltimaAlteracao(new Date());
							contaCategoriaTemp.setValorAgua(contaContaCategoriaHistorico.getValorAgua());
							contaCategoriaTemp.setValorEsgoto(contaContaCategoriaHistorico.getValorEsgoto());
							contaCategoriaTemp.setValorTarifaMinimaAgua(contaContaCategoriaHistorico.getValorTarifaMinimaAgua());
							contaCategoriaTemp.setValorTarifaMinimaEsgoto(contaContaCategoriaHistorico.getValorTarifaMinimaEsgoto());

							colecaoContaCategoriaInserir.add(contaCategoriaTemp);

						}
						
						contaTemp.setContaCategorias(colecaoContaCategoriaInserir);
						colecaoContaInserir.add(contaTemp);
					
					}
				}catch(ErroRepositorioException e){
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			
			}
		}

		return colecaoContaInserir;


	}

	/**
	 * @param anoMesRefInicial
	 * @param anoMesRefFinal
	 * @throws Exception
	 */
	public void executarRegeracaoHistograma(Integer anoMesRefInicial, Integer anoMesRefFinal) throws Exception{

		// Inicializa as instancias dos repositorios usados
		this.ejbCreate();

		System.out.println("deletando HistogramaAguaLigacao  *************************");

		this.getControladorHistograma().deletarHistogramaAguaLigacao(anoMesRefInicial, anoMesRefFinal);

		System.out.println("deletando HistogramaAguaEconomia  *************************");
		this.getControladorHistograma().deletarHistogramaAguaEconomia(anoMesRefInicial, anoMesRefFinal);

		System.out.println("deletando HistogramaEsgotoLigacao  *************************");
		this.getControladorHistograma().deletarHistogramaEsgotoLigacao(anoMesRefInicial, anoMesRefFinal);

		System.out.println("deletando HistogramaEsgotoEconomia  *************************");
		this.getControladorHistograma().deletarHistogramaEsgotoEconomia(anoMesRefInicial, anoMesRefFinal);

		FiltroFaturamentoGrupo filtroFaturamentoGrupo = new FiltroFaturamentoGrupo();
		filtroFaturamentoGrupo.adicionarParametro(new ParametroSimples(FiltroFaturamentoGrupo.INDICADOR_USO, ConstantesSistema.SIM));
		filtroFaturamentoGrupo.adicionarParametro(new ParametroSimples(FiltroFaturamentoGrupo.ID, 30));

		Collection colecaoFaturamentoGrupo = (Collection) getControladorUtil().pesquisar(filtroFaturamentoGrupo,
						FaturamentoGrupo.class.getName());
		Iterator it = colecaoFaturamentoGrupo.iterator();
		while(it.hasNext()){
			FaturamentoGrupo faturamentoGrupo = (FaturamentoGrupo) it.next();

			System.out.println("Faturamento Grupo = " + faturamentoGrupo.getId());

			// Consultar Todas as contas do intervalor informado com situação normal
			Collection contas = this.repositorioFaturamento.obterContasIntervalo(anoMesRefInicial, anoMesRefFinal, faturamentoGrupo);
			this.getControladorFaturamento().carregarContaParaHistograma(contas);
			// chamar o método de geração do histograma.
			System.out.println("gerando Histograma INCLUSÃO  *************************");
			getControladorHistograma().gerarHistogramaAguaEsgoto(contas, ConstantesSistema.GERACAO_HISTOGRAMA_FATURAMENTO);

			contas.clear();

			Collection contasHistorico = this.repositorioFaturamento.obterContasHistoricoIntervalo(anoMesRefInicial, anoMesRefFinal,
							DebitoCreditoSituacao.NORMAL, faturamentoGrupo);
			// converter para conta.
			contas = conveterContasHistoricoParaConta(contasHistorico);
			// [UC0566 – Gerar Histograma de Água e Esgoto]
			System.out.println("gerando Histograma INCLUSÃO  *************************");
			getControladorHistograma().gerarHistogramaAguaEsgoto(contas, ConstantesSistema.GERACAO_HISTOGRAMA_FATURAMENTO);

			contasHistorico.clear();
			contas.clear();

			contasHistorico = this.repositorioFaturamento.obterContasHistoricoIntervalo(anoMesRefInicial, anoMesRefFinal,
							DebitoCreditoSituacao.INCLUIDA, faturamentoGrupo);
			// converter para conta.
			contas = conveterContasHistoricoParaConta(contasHistorico);
			// // [UC0566 – Gerar Histograma de Água e Esgoto]
			System.out.println("gerando Histograma INCLUSÃO  *************************");
			getControladorHistograma().gerarHistogramaAguaEsgoto(contas, ConstantesSistema.GERACAO_HISTOGRAMA_INCLUSAO);

			contasHistorico.clear();
			contas.clear();

			// Consultar Todas as contas histórico
			contasHistorico = this.repositorioFaturamento.obterContasHistoricoIntervalo(anoMesRefInicial, anoMesRefFinal,
							DebitoCreditoSituacao.CANCELADA, faturamentoGrupo);

			// converter para conta.
			contas = conveterContasHistoricoParaConta(contasHistorico);

			// [UC0566 – Gerar Histograma de Água e Esgoto]
			System.out.println("gerando Histograma CANCELAMENTO  *************************");
			getControladorHistograma().gerarHistogramaAguaEsgoto(contas, ConstantesSistema.GERACAO_HISTOGRAMA_CANCELAMENTO);

			// Consultar todas as contas retificadas (obter sempre a conta retificada com maior
			// data
			// de
			// ultima atualização.)

			contasHistorico.clear();
			contas.clear();

			// Consultar Todas as contas histórico
			contasHistorico = this.repositorioFaturamento.obterContasHistoricoIntervalo(anoMesRefInicial, anoMesRefFinal,
							DebitoCreditoSituacao.CANCELADA_POR_RETIFICACAO, faturamentoGrupo);

			// converter para conta.
			contas = conveterContasHistoricoParaConta(contasHistorico);

			// [UC0566 – Gerar Histograma de Água e Esgoto]
			System.out.println("gerando Histograma CANCELAMENTO POR RETIFICAÇÃO  *************************");
			getControladorHistograma().gerarHistogramaAguaEsgoto(contas, ConstantesSistema.GERACAO_HISTOGRAMA_CANCELAMENTO);

			contasHistorico.clear();
			contas.clear();

			// Consultar Todas as contas histórico
			contasHistorico = this.repositorioFaturamento.obterContasHistoricoIntervalo(anoMesRefInicial, anoMesRefFinal,
							DebitoCreditoSituacao.RETIFICADA, faturamentoGrupo);

			// converter para conta.
			contas = conveterContasHistoricoParaConta(contasHistorico);

			// [UC0566 – Gerar Histograma de Água e Esgoto]
			System.out.println("gerando Histograma RETIFICAÇÃO/INCLUSÃO *************************");
			getControladorHistograma().gerarHistogramaAguaEsgoto(contas, ConstantesSistema.GERACAO_HISTOGRAMA_INCLUSAO);

			contasHistorico.clear();
			contas.clear();

		}


		System.out.println("************************** Fim regeração histograma!  *************************");


	}

	public void executarAjusteErroGeracaoContaCategoriaConsumoFaixa(Integer anoMesReferencia, String idsGrupos) throws Exception{

		// Inicializa as instancias dos repositórios usados
		this.ejbCreate();

		log.info("Iniciando processamento ajuste faixas dos grupos: " + idsGrupos);
		log.info("Ano Mês Referência: " + anoMesReferencia.toString());

		Collection<Object[]> colecaoIdsContas = repositorioFaturamento.pesquisarIdsContaRegerarContaCategoriaEFaixa(anoMesReferencia,
						idsGrupos);

		ContaMotivoRetificacao contaMotivoRetificacao = (ContaMotivoRetificacao) getControladorUtil().pesquisar(8,
						ContaMotivoRetificacao.class, false);

		Date dataLeituraAtualAjuste = Util.converteStringParaDateHora("01/03/2014 00:01:00");
		Date dataProcessamentoAjuste = Util.converteStringParaDateHora("25/03/2014 00:00:00");
		BigDecimal valorContaAnterior = BigDecimal.ZERO;
		BigDecimal valorNovaConta = BigDecimal.ZERO;

		if(!Util.isVazioOrNulo(colecaoIdsContas)){

			log.info("Quantidade de contas: " + colecaoIdsContas.size());

			for(Object[] dadosConta : colecaoIdsContas){

				FaturamentoGrupo faturamentoGrupo = (FaturamentoGrupo) getControladorUtil().pesquisar(
								Util.obterInteger(dadosConta[2].toString()), FaturamentoGrupo.class, false);

				Conta conta = getControladorFaturamento().pesquisarContaRetificacao(Util.obterInteger(dadosConta[0].toString()));

				valorContaAnterior = conta.getValorTotalContaBigDecimal();

				Imovel imovel = this.getControladorImovel().pesquisarImovel(conta.getImovel().getId());

				FiltroMovimentoRoteiroEmpresa filtroMovimentoRoteiroEmpresa = new FiltroMovimentoRoteiroEmpresa();
				filtroMovimentoRoteiroEmpresa.adicionarParametro(new ParametroSimples(FiltroMovimentoRoteiroEmpresa.IMOVEL_ID, conta
								.getImovel().getId()));
				filtroMovimentoRoteiroEmpresa.adicionarParametro(new ParametroSimples(FiltroMovimentoRoteiroEmpresa.ANO_MES_MOVIMENTO,
								anoMesReferencia));
				filtroMovimentoRoteiroEmpresa.adicionarParametro(new ParametroSimples(FiltroMovimentoRoteiroEmpresa.INDICADOR_FASE,
								MovimentoRoteiroEmpresa.FASE_PROCESSADO));

				Collection colecaoMovimentoRoteiroEmpresa = getControladorUtil().pesquisar(filtroMovimentoRoteiroEmpresa,
								MovimentoRoteiroEmpresa.class.getName());

				MovimentoRoteiroEmpresa movimentoRoteiroImovel = (MovimentoRoteiroEmpresa) Util
								.retonarObjetoDeColecao(colecaoMovimentoRoteiroEmpresa);

				Date dataLeituraAnterior = movimentoRoteiroImovel.getDataLeituraAnterior();

				if(dataLeituraAnterior == null){

					dataLeituraAnterior = Util.subtrairNumeroDiasDeUmaData(dataLeituraAtualAjuste, 30);
				}


				log.info("***Processando Ajuste Faixas [Id da Conta ==> " + conta.getId().toString() + "] - [Id do Imóvel ==> "
								+ imovel.getId().toString() + "]");

				// [UC0108] - Quantidade de economias por categoria
				Collection colecaoCategoriaOUSubcategoria = getControladorImovel().obterQuantidadeEconomiasContaCategoria(conta);

				String consumoAgua = "";

				if(conta.getConsumoAgua() != null){

					consumoAgua = conta.getConsumoAgua().toString();
				}

				String consumoEsgoto = "";
				if(conta.getConsumoEsgoto() != null){

					consumoEsgoto = conta.getConsumoEsgoto().toString();
				}

				String percentualEsgoto = "";
				if(conta.getConsumoEsgoto() != null){

					percentualEsgoto = conta.getPercentualEsgoto().toString();
				}

				Collection<CalcularValoresAguaEsgotoHelper> calcularValoresConta = getControladorFaturamento().calcularValoresConta(
								String.valueOf(Util.formatarAnoMesParaMesAno(conta.getAnoMesReferenciaConta())),
								String.valueOf(imovel.getId()), conta.getLigacaoAguaSituacao().getId(),
								conta.getLigacaoEsgotoSituacao().getId(), colecaoCategoriaOUSubcategoria, consumoAgua, consumoEsgoto,
								percentualEsgoto, conta.getConsumoTarifa().getId(), Usuario.USUARIO_BATCH, dataLeituraAnterior,
								dataLeituraAtualAjuste);

				// Obtendo os débitos cobrados da conta
				Collection colecaoDebitoCobrado = getControladorFaturamento().obterDebitosCobradosConta(conta);

				// Obtendo os créditos realizados da conta
				Collection colecaoCreditoRealizado = getControladorFaturamento().obterCreditosRealizadosConta(conta);

				Integer idContaRetificada = getControladorFaturamento().retificarConta(conta.getAnoMesReferenciaConta(), conta, imovel,
								colecaoDebitoCobrado, colecaoCreditoRealizado, conta.getLigacaoAguaSituacao(),
								conta.getLigacaoEsgotoSituacao(), colecaoCategoriaOUSubcategoria, consumoAgua, consumoEsgoto,
								percentualEsgoto, conta.getDataVencimentoConta(), calcularValoresConta, contaMotivoRetificacao, null,
								Usuario.USUARIO_BATCH, null, conta.getConsumoTarifa(), null);

				Conta novaConta = (Conta) getControladorUtil().pesquisar(idContaRetificada, Conta.class, false);

				if(novaConta != null){

					valorNovaConta = novaConta.getValorTotalContaBigDecimal();
				}else{

					ContaHistorico novaContaHistorico = (ContaHistorico) getControladorUtil().pesquisar(idContaRetificada,
									ContaHistorico.class, false);

					valorNovaConta = novaContaHistorico.getValorTotal();
				}

				// Coloca um registro fixo na tabela relacao_tres_deso para indicar que
				// processou a
				// conta
				RelacaoTresDeso relacaoTresDeso = new RelacaoTresDeso();
				relacaoTresDeso.setUltimaAlteracao(dataProcessamentoAjuste);
				relacaoTresDeso.setAnoMesReferencia(anoMesReferencia);
				relacaoTresDeso.setIdConta(idContaRetificada);
				relacaoTresDeso.setIdImovel(imovel.getId());
				relacaoTresDeso.setIdFaturamentoGrupo(faturamentoGrupo.getId());

				// valor da conta antes da retificação
				relacaoTresDeso.setValorEnviado(valorContaAnterior);

				// valor da conta após a retificação
				relacaoTresDeso.setValorFaturado(valorNovaConta);

				getControladorUtil().inserir(relacaoTresDeso);
			}
		}

		log.info("FIM processamento ajuste faixas dos grupos: " + idsGrupos);
	}

	public void executarAjusteContaZeradasEnviarHistorico(Integer anoMesReferencia) throws Exception{

		// Inicializa as instancias dos repositórios usados
		this.ejbCreate();

		log.info("Ano Mês Referência: " + anoMesReferencia.toString());

		Collection<Object[]> colecaoIdsContas = repositorioFaturamento.pesquisarIdsContaZeradasParaEnviarHistorico(anoMesReferencia);

		Date dataProcessamentoAjuste = Util.converteStringParaDateHora("26/03/2014 00:00:00");

		if(!Util.isVazioOrNulo(colecaoIdsContas)){

			log.info("Quantidade de contas: " + colecaoIdsContas.size());

			for(Object[] dadosConta : colecaoIdsContas){

				Conta conta = (Conta) getControladorUtil().pesquisar(Util.obterInteger(dadosConta[0].toString()), Conta.class, true);

				log.info("Transferindo para Historico => CONTA ZERADA[" + conta.getId() + "]");

				// Coloca um registro fixo na tabela relacao_tres_deso para indicar que processou a
				// conta
				RelacaoTresDeso relacaoTresDeso = new RelacaoTresDeso();
				relacaoTresDeso.setUltimaAlteracao(dataProcessamentoAjuste);
				relacaoTresDeso.setAnoMesReferencia(anoMesReferencia);
				relacaoTresDeso.setIdConta(conta.getId());
				relacaoTresDeso.setIdImovel(conta.getImovel().getId());
				relacaoTresDeso.setIdFaturamentoGrupo(Util.obterInteger(dadosConta[2].toString()));

				// Valor da conta
				relacaoTresDeso.setValorFaturado(conta.getValorTotalContaBigDecimal());

				// Transfere a conta pro histórico
				getControladorFaturamento().transferirContasParaHistorico(Arrays.asList(conta), 0);
				ContaGeral contaGeral = (ContaGeral) getControladorUtil().pesquisar(conta.getContaGeral().getId(), ContaGeral.class, false);
				contaGeral.setIndicadorHistorico(ConstantesSistema.SIM);

				this.getControladorUtil().atualizar(contaGeral);

				getControladorUtil().inserir(relacaoTresDeso);
			}
		}

		log.info("FIM processamento ajuste contas zeradas enviar historico: " + anoMesReferencia.toString());
	}
}