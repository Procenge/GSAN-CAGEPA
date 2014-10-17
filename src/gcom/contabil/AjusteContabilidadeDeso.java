
package gcom.contabil;

import gcom.arrecadacao.ControladorArrecadacaoLocal;
import gcom.arrecadacao.ControladorArrecadacaoLocalHome;
import gcom.arrecadacao.IRepositorioArrecadacao;
import gcom.arrecadacao.RepositorioArrecadacaoHBM;
import gcom.arrecadacao.bean.OperacaoContabilHelper;
import gcom.arrecadacao.pagamento.*;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoSituacao;
import gcom.batch.ControladorBatchLocal;
import gcom.batch.ControladorBatchLocalHome;
import gcom.cadastro.imovel.*;
import gcom.cobranca.CobrancaForma;
import gcom.cobranca.CobrancaSituacao;
import gcom.cobranca.ControladorCobrancaLocal;
import gcom.cobranca.ControladorCobrancaLocalHome;
import gcom.cobranca.parcelamento.Parcelamento;
import gcom.fachada.Fachada;
import gcom.faturamento.*;
import gcom.faturamento.bean.CalcularValoresAguaEsgotoFaixaHelper;
import gcom.faturamento.bean.CalcularValoresAguaEsgotoHelper;
import gcom.faturamento.conta.*;
import gcom.faturamento.credito.CreditoARealizar;
import gcom.faturamento.credito.CreditoRealizado;
import gcom.faturamento.credito.CreditoTipo;
import gcom.faturamento.credito.FiltroCreditoARealizar;
import gcom.faturamento.debito.*;
import gcom.financeiro.lancamento.AjusteContabilidade;
import gcom.financeiro.lancamento.LancamentoItemContabil;
import gcom.micromedicao.*;
import gcom.micromedicao.medicao.FiltroMedicaoHistorico;
import gcom.micromedicao.medicao.MedicaoHistorico;
import gcom.micromedicao.medicao.MedicaoTipo;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.*;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.ExecutorParametro;
import gcom.util.parametrizacao.Parametrizacao;
import gcom.util.parametrizacao.cobranca.parcelamento.ParametroParcelamento;
import gcom.util.parametrizacao.faturamento.ExecutorParametrosFaturamento;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.zip.ZipOutputStream;

import javax.ejb.CreateException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class AjusteContabilidadeDeso
				implements Parametrizacao {

	private static Logger log = Logger.getLogger(AjusteContabilidadeDeso.class);

	private IRepositorioArrecadacao repositorioArrecadacao;
	protected IRepositorioFaturamento repositorioFaturamento;
	protected IRepositorioMicromedicao repositorioMicromedicao;

	public AjusteContabilidadeDeso() {

	}

	public void ejbCreate() throws CreateException{

		repositorioArrecadacao = RepositorioArrecadacaoHBM.getInstancia();
		repositorioFaturamento = RepositorioFaturamentoHBM.getInstancia();
		repositorioMicromedicao = RepositorioMicromedicaoHBM.getInstancia();

	}


	private ControladorContabilLocal getControladorContabil(){

		ControladorContabilLocalHome localHome = null;
		ControladorContabilLocal local = null;

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();
			localHome = (ControladorContabilLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_CONTABIL_SEJB);
			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}
	}

	private ControladorImovelLocal getControladorImovel(){

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

	private ControladorUtilLocal getControladorUtil(){

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

	private ControladorArrecadacaoLocal getControladorArrecadacao(){

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

	private ControladorFaturamentoLocal getControladorFaturamento(){

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

	private ControladorBatchLocal getControladorBatch(){

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
	

	private ControladorCobrancaLocal getControladorCobranca(){

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


	/**
	 * Ajuste Resumo ação de cobrança
	 * 
	 * @throws Exception
	 */

	public void ajusteResumoAcaoCobranca() throws Exception{

		this.ejbCreate();

		Collection colecaoImoveis = this.getControladorCobranca().pesquisarImovelEmCobrancaAdministrativaAjuste();

		Iterator it = colecaoImoveis.iterator();

		while(it.hasNext()){

			Integer idImovel = (Integer) it.next();

			// [FS0003] - Verifica usuário com débito em cobrança
			// administrativa
			FiltroImovelCobrancaSituacao filtroImovelCobrancaSituacao = new FiltroImovelCobrancaSituacao();

			filtroImovelCobrancaSituacao.adicionarCaminhoParaCarregamentoEntidade("cobrancaSituacao");

			filtroImovelCobrancaSituacao.adicionarParametro(new ParametroSimples(FiltroImovelCobrancaSituacao.IMOVEL_ID, idImovel));
			filtroImovelCobrancaSituacao.adicionarParametro(new ParametroSimples(FiltroImovelCobrancaSituacao.COBRANCA_SITUACAO,
							CobrancaSituacao.COBRANCA_ADMINISTRATIVA));

			filtroImovelCobrancaSituacao
							.adicionarCaminhoParaCarregamentoEntidade(FiltroImovelCobrancaSituacao.COBRANCA_ACAO_ATIVIDADE_COMANDO);
			filtroImovelCobrancaSituacao.setCampoOrderByDesc(FiltroImovelCobrancaSituacao.COBRANCA_ACAO_ATIVIDADE_COMANDO_ID);

			Collection colecaoImovelCobrancaSituacaoEncontrada = getControladorUtil().pesquisar(filtroImovelCobrancaSituacao,
							ImovelCobrancaSituacao.class.getName());

			Iterator itt = colecaoImovelCobrancaSituacaoEncontrada.iterator();
			if(itt.hasNext()){

				ImovelCobrancaSituacao imovelCobrancaSituacaoEncontrada = (ImovelCobrancaSituacao) itt.next();
				imovelCobrancaSituacaoEncontrada.setDataRetiradaCobranca(null);
				imovelCobrancaSituacaoEncontrada.setUltimaAlteracao(new Date());

				if(imovelCobrancaSituacaoEncontrada.getCobrancaAcaoAtividadeComando() != null){
					System.out.println("   ImovelCobrancaSituacaoEncontrada => " + " imovel = "
									+ imovelCobrancaSituacaoEncontrada.getImovel().getId() + " comando atualizado = "
									+ imovelCobrancaSituacaoEncontrada.getCobrancaAcaoAtividadeComando().getId());
				}else{
					System.out.println("   ImovelCobrancaSituacaoEncontrada => " + " imovel ="
									+ imovelCobrancaSituacaoEncontrada.getImovel().getId());
				}



				this.getControladorUtil().atualizar(imovelCobrancaSituacaoEncontrada);

			}

		}

		System.out.println("******************FIM****************************");

	}

	/**
	 * @throws ControladorException
	 * @throws ErroRepositorioException
	 */
	public void gerarDebitoACobrarContaComValorAMenor(Integer referencia, String idsGrupos) throws ControladorException,
					ErroRepositorioException{

		try{

			this.ejbCreate();

			Date dataMarcacaoGeracaoDebitoFixa = Util.converteStringParaDateHora("20/03/2014 19:00:00");
			log.info("Início Ajuste gerarDebitoACobrarContaComValorAMenor Grupos: " + idsGrupos);
			log.info("Ano Mês Referência: " + referencia.toString());

			// ...Obter imoveis/contas
			Collection colecaoDadosImoveisAjuste = repositorioMicromedicao.obterImovelParaAjusteContaComValorAMenor(referencia, idsGrupos);

			if(!Util.isVazioOrNulo(colecaoDadosImoveisAjuste)){

				log.info("Quantidade de Contas dos Grupos = " + colecaoDadosImoveisAjuste.size());

				Integer qtd = 0;
				FiltroDebitoTipo filtroDebitoTipo = new FiltroDebitoTipo();
				filtroDebitoTipo.adicionarParametro(new ParametroSimples(FiltroDebitoTipo.ID, 271));
				filtroDebitoTipo.adicionarCaminhoParaCarregamentoEntidade(FiltroDebitoTipo.FINANCIAMENTO_TIPO);
				filtroDebitoTipo.adicionarCaminhoParaCarregamentoEntidade(FiltroDebitoTipo.LANCAMENTO_ITEM_CONTABIL);
				filtroDebitoTipo.adicionarParametro(new ParametroSimples(FiltroDebitoTipo.INDICADOR_USO, ConstantesSistema.SIM));
				Collection<DebitoTipo> colecaoDebitoTipo = Fachada.getInstancia().pesquisar(filtroDebitoTipo, DebitoTipo.class.getName());
				DebitoTipo debitoTipo = (DebitoTipo) Util.retonarObjetoDeColecao(colecaoDebitoTipo);

				for(Iterator iterator = colecaoDadosImoveisAjuste.iterator(); iterator.hasNext();){

					qtd++;
					log.info("Quantidade de Contas Processadas = " + qtd);

					Object[] dadosImovelAjuste = (Object[]) iterator.next();

					BigDecimal valorCreditoRealizado = (BigDecimal) dadosImovelAjuste[0];
					Integer idImovel = (Integer) dadosImovelAjuste[1];
					Integer idConta = (Integer) dadosImovelAjuste[2];

					Imovel imovel = this.getControladorImovel().pesquisarImovel(idImovel);
					log.info("Imóvel = " + imovel.getId());

					CobrancaForma cobrancaForma = new CobrancaForma();
					cobrancaForma.setId(CobrancaForma.COBRANCA_EM_CONTA);

					// Gerar debito a cobrar com o valor do credito calculado
					DebitoACobrar debitoACobrar = new DebitoACobrar();
					debitoACobrar.setDebitoTipo(debitoTipo);
					debitoACobrar.setValorDebito(valorCreditoRealizado);
					debitoACobrar.setImovel(imovel);
					debitoACobrar.setGeracaoDebito(dataMarcacaoGeracaoDebitoFixa);
					debitoACobrar.setLocalidade(imovel.getLocalidade());
					debitoACobrar.setQuadra(imovel.getQuadra());
					debitoACobrar.setAnoMesReferenciaDebito(referencia);
					debitoACobrar.setLancamentoItemContabil(debitoTipo.getLancamentoItemContabil());
					debitoACobrar.setFinanciamentoTipo(debitoTipo.getFinanciamentoTipo());
					debitoACobrar.setCobrancaForma(cobrancaForma);
					debitoACobrar.setUltimaAlteracao(new Date());

					this.getControladorFaturamento().inserirDebitoACobrar(1, debitoACobrar, null, imovel, null, null,
									Usuario.USUARIO_BATCH, false, null, null, null);

					log.info("idDebitoTipo = " + debitoTipo.getId() + " imóvel =" + imovel.getId() + " valor do débito gerado= "
									+ valorCreditoRealizado);

					log.info("Retificando IdConta = " + idConta);

					// Carrega na conta os dados necessários para retificação
					Conta contaAtual = getControladorFaturamento().pesquisarContaRetificacao(idConta);

					Collection<DebitoCobrado> colecaoDebitoCobrado = getControladorFaturamento().obterDebitosCobradosConta(contaAtual);

					// Obtendo os créditos realizados da conta
					Collection colecaoCreditoRealizado = getControladorFaturamento().obterCreditosRealizadosConta(contaAtual);

					Collection colecaoCreditoRealizadoAtualizado = new ArrayList();

					if(!Util.isVazioOrNulo(colecaoCreditoRealizado)){

						Iterator it = colecaoCreditoRealizado.iterator();
						while(it.hasNext()){

							CreditoRealizado creditoRealizado = (CreditoRealizado) it.next();

							if(creditoRealizado.getCreditoTipo().getId().equals(CreditoTipo.DESCONTO_ACRESCIMOS_IMPONTUALIDADE)){

								CreditoRealizado creditoRealizadoNovo = new CreditoRealizado();
								creditoRealizadoNovo.setConta(contaAtual);

								CreditoTipo creditoTipo = new CreditoTipo();
								creditoTipo.setId(CreditoTipo.DEVOLUCAO_OUTROS_VALORES);
								LancamentoItemContabil lancamentoItemContabil = new LancamentoItemContabil();
								lancamentoItemContabil.setId(18);
								creditoTipo.setLancamentoItemContabil(lancamentoItemContabil);

								creditoRealizadoNovo.setCreditoTipo(creditoTipo);
								creditoRealizadoNovo.setDataHoraCreditoRealizado(new Date());
								creditoRealizadoNovo.setLancamentoItemContabil(creditoRealizado.getLancamentoItemContabil());
								creditoRealizadoNovo.setLocalidade(creditoRealizado.getLocalidade());
								creditoRealizadoNovo.setQuadra(creditoRealizado.getQuadra());
								creditoRealizadoNovo.setCodigoSetorComercial(creditoRealizado.getCodigoSetorComercial());
								creditoRealizadoNovo.setNumeroQuadra(creditoRealizado.getNumeroQuadra());
								creditoRealizadoNovo.setNumeroLote(creditoRealizado.getNumeroLote());
								creditoRealizadoNovo.setNumeroSubLote(creditoRealizado.getNumeroSubLote());
								creditoRealizadoNovo.setAnoMesReferenciaCredito(creditoRealizado.getAnoMesReferenciaCredito());
								creditoRealizadoNovo.setAnoMesCobrancaCredito(creditoRealizado.getAnoMesCobrancaCredito());
								creditoRealizadoNovo.setValorCredito(creditoRealizado.getValorCredito());
								creditoRealizadoNovo.setCreditoOrigem(creditoRealizado.getCreditoOrigem());
								creditoRealizadoNovo.setNumeroPrestacao(new Short("1"));
								creditoRealizadoNovo.setParcelamento(creditoRealizado.getParcelamento());
								creditoRealizadoNovo.setNumeroPrestacaoCredito(new Short("1"));

								colecaoCreditoRealizadoAtualizado.add(creditoRealizado);
								colecaoCreditoRealizadoAtualizado.add(creditoRealizadoNovo);

							}else{

								colecaoCreditoRealizadoAtualizado.add(creditoRealizado);
							}

						}
					}

					ContaMotivoRetificacao contaMotivoRetificacao = new ContaMotivoRetificacao();
					contaMotivoRetificacao.setId(ContaMotivoRetificacao.BAIXA_FORCADA);
					Collection colecaoCategoria = this.getControladorImovel().obterQuantidadeEconomiasCategoria(imovel);

					// Retifica a conta
					this.getControladorFaturamento().retificarConta(referencia, contaAtual, imovel, colecaoDebitoCobrado,
									colecaoCreditoRealizadoAtualizado, contaAtual.getLigacaoAguaSituacao(),
									contaAtual.getLigacaoEsgotoSituacao(), colecaoCategoria, contaAtual.getConsumoAgua().toString(),
									contaAtual.getConsumoEsgoto().toString(), contaAtual.getPercentualEsgoto().toString(),
									contaAtual.getDataVencimentoConta(), null, contaMotivoRetificacao, null, Usuario.USUARIO_BATCH, null,
									contaAtual.getConsumoTarifa(), false, null);

					// Gerar registros na tabela de ajuste para armazenar os imóveis que tiveram
					// débito/crédito gerado e conta retificada
					RelacaoTresDeso relacaoTresDeso = new RelacaoTresDeso();
					relacaoTresDeso.setAnoMesReferencia(referencia);
					relacaoTresDeso.setIdImovel(imovel.getId());
					relacaoTresDeso.setValorGeradoPrimeiraRotinaAjt(valorCreditoRealizado);

					FiltroMovimentoRoteiroEmpresa filtroMovimentoRoteiroEmpresa = new FiltroMovimentoRoteiroEmpresa();
					filtroMovimentoRoteiroEmpresa.adicionarParametro(new ParametroSimples(FiltroMovimentoRoteiroEmpresa.ANO_MES_MOVIMENTO,
									referencia));
					filtroMovimentoRoteiroEmpresa.adicionarParametro(new ParametroSimples(FiltroMovimentoRoteiroEmpresa.IMOVEL_ID, imovel
									.getId()));
					filtroMovimentoRoteiroEmpresa.adicionarCaminhoParaCarregamentoEntidade("faturamentoGrupo");

					MovimentoRoteiroEmpresa movimentoRoteiroEmpresa = (MovimentoRoteiroEmpresa) Util
									.retonarObjetoDeColecao(getControladorUtil().pesquisar(filtroMovimentoRoteiroEmpresa,
													MovimentoRoteiroEmpresa.class.getName()));

					relacaoTresDeso.setIdFaturamentoGrupo(movimentoRoteiroEmpresa.getFaturamentoGrupo().getId());
					relacaoTresDeso.setUltimaAlteracao(new Date());
					relacaoTresDeso.setIdDebitoTipo(debitoTipo.getId());

					// Classifica o pagamento caso exista
					FiltroPagamento filtroPagamento = new FiltroPagamento();
					filtroPagamento.adicionarParametro(new ParametroSimples(FiltroPagamento.IMOVEL_ID, imovel.getId()));
					filtroPagamento.adicionarParametro(new ParametroSimples(FiltroPagamento.ANO_MES_REFERENCIA_PAGAMENTO, referencia));

					Collection<Pagamento> colecaoPagamentos = getControladorUtil().pesquisar(filtroPagamento, Pagamento.class.getName());

					if(colecaoPagamentos != null && !colecaoPagamentos.isEmpty()){
						
						// Coloca esse campo com "1" fixo para indicar que teve pagamento para esse
						// imovel para essa referência
						relacaoTresDeso.setNovaMedia(1);

						for(Pagamento pagamento : colecaoPagamentos){
							
							pagamento.setPagamentoSituacaoAnterior(pagamento.getPagamentoSituacaoAtual());
						}

						getControladorArrecadacao().classificarPagamentosRegistroMovimentoArrecadadores(
											colecaoPagamentos.iterator().next());
						
						OperacaoContabilHelper helper = getControladorArrecadacao().definirOrigemOperacaoContabilPagamento(
										colecaoPagamentos.iterator().next(), true);
						if(helper.getOperacaoContabil() != null){

							getControladorContabil()
											.registrarLancamentoContabil(helper.getObjetoOrigem(), helper.getOperacaoContabil());
						}
					}

					getControladorUtil().inserir(relacaoTresDeso);
				}

				log.info("********Fim Execução! Ajuste gerarDebitoACobrarContaComValorAMenor Grupos: " + idsGrupos
								+ " Quantidade de Contas Processadas= " + qtd);
			}else{

				log.info("Quantidade de Contas dos Grupos = " + 0);
				log.info("********Fim Execução! Ajuste gerarDebitoACobrarContaComValorAMenor Grupos: " + idsGrupos
								+ " Nenhuma Conta processada!");
			}

		}catch(CreateException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	/***
	 * Desfazer pré faturamento por grupo e referência
	 * 
	 * @throws ControladorException
	 * @throws ErroRepositorioException
	 */

	public void desfazerPreFaturamentoPorGrupoERef() throws ControladorException, ErroRepositorioException{

		try{
			this.ejbCreate();

			// Atenção !!! lembra de mudar!!!
			Integer idFaturamentoGrupo = 100;
			
			Integer anoMesReferencia = 201304;
			Integer anoMesReferenciaUltimaCobranca = 201302;
			Collection collDebitoACobrarParaAtualizarPrestacoes = new ArrayList();


			// 1 Pesquisar Débitos Cobrados das contas para a ref e grupo:

			// 1.1 Pesquisar Débitos Cobrados que ainda tem prestações a vencer , que ainda não
			// estão em debito_a_cobrar_historico.
			Collection colecaoIdsImoveisComDebitoACobrar = repositorioFaturamento.pesquisarImovelDoDebitoCobradoPorPrestacaoCobradas(
							idFaturamentoGrupo, anoMesReferencia, ">");

			if(colecaoIdsImoveisComDebitoACobrar != null && !colecaoIdsImoveisComDebitoACobrar.isEmpty()){
				// 1.1.1 Pesquisar os debitos_a_cobrar e atualizar o número de prestações cobradas
				// com
				// número de prestações cobradas - 1.
				Collection colecaoDebitoACobrarParaAtualizarPrestacoesCobradas = repositorioFaturamento
								.pesquisarDebitoACobrarPorListaDeImoveis(colecaoIdsImoveisComDebitoACobrar, anoMesReferencia);

				Iterator it = colecaoDebitoACobrarParaAtualizarPrestacoesCobradas.iterator();
				while(it.hasNext()){
					DebitoACobrar debitoACobrar = (DebitoACobrar) it.next();

					short numeroPrestacaoCobradas = (short) (debitoACobrar.getNumeroPrestacaoCobradas() - 1);
					System.out.println("debitoACobrar : " + debitoACobrar.getId() + "Prest. cobradas : "
									+ debitoACobrar.getNumeroPrestacaoCobradas() + " atualizando para " + numeroPrestacaoCobradas);

					debitoACobrar.setNumeroPrestacaoCobradas(numeroPrestacaoCobradas);
					debitoACobrar.setAnoMesReferenciaUltimaCobranca(anoMesReferenciaUltimaCobranca);
					debitoACobrar.setUltimaAlteracao(new Date());

					collDebitoACobrarParaAtualizarPrestacoes.add(debitoACobrar);
				}

				// Atualizando as prestações
				this.getControladorUtil().atualizarColecaoObjetos(collDebitoACobrarParaAtualizarPrestacoes);
				System.out.println("*********************Quantidade de debitos a cobrar atualizados = "
								+ collDebitoACobrarParaAtualizarPrestacoes.size());

			}


			// Remover os debitos cobrados nessa situação :
			Collection colecaoDebitosCobradorParaRemover1 = repositorioFaturamento.pesquisarDebitoCobradoPorPrestacaoCobradas(
							idFaturamentoGrupo, anoMesReferencia, ">");
			if(colecaoDebitosCobradorParaRemover1 != null && !colecaoDebitosCobradorParaRemover1.isEmpty()){
				Collection colecaoDebitoCobradoCategoria1 = new ArrayList();
				Collection colecaoDebitoCobradoCategoriaParaRemover1 = new ArrayList();

				Iterator itDebitoCobrado1 = colecaoDebitosCobradorParaRemover1.iterator();

				while(itDebitoCobrado1.hasNext()){

					DebitoCobrado debitoCobrado1 = (DebitoCobrado) itDebitoCobrado1.next();

					FiltroDebitoCobradoCategoria filtroDebitoCobradoCategoria1 = new FiltroDebitoCobradoCategoria();
					filtroDebitoCobradoCategoria1.adicionarParametro(new ParametroSimples(FiltroDebitoCobradoCategoria.DEBITO_COBRADO_ID,
									debitoCobrado1.getId()));

					filtroDebitoCobradoCategoria1.adicionarCaminhoParaCarregamentoEntidade(FiltroDebitoCobradoCategoria.CATEGORIA);

					filtroDebitoCobradoCategoria1.adicionarCaminhoParaCarregamentoEntidade(FiltroDebitoCobradoCategoria.DEBITO_COBRADO);

					colecaoDebitoCobradoCategoria1 = this.getControladorUtil().pesquisar(filtroDebitoCobradoCategoria1,
									DebitoCobradoCategoria.class.getName());

					colecaoDebitoCobradoCategoriaParaRemover1.addAll(colecaoDebitoCobradoCategoria1);

				}

				this.getControladorUtil().removerColecaoObjetos(colecaoDebitoCobradoCategoriaParaRemover1);


				this.getControladorUtil().removerColecaoObjetos(colecaoDebitosCobradorParaRemover1);
			}

			

			System.out
							.println("*********************Quantidade de debito cobrado removidos = "
							+ colecaoDebitosCobradorParaRemover1.size());

			System.out.println("----------------------FIM DO CASO 1---------------------------------------------------------");

			// ...........................................................................................................



			// 1.2 Pesquisar Débitos Cobrados que não tem prestações a vencer , que estão em
			// debito_a_cobrar_historico.
			Collection colecaoIdsImoveisComDebitoACobrarHistorico = repositorioFaturamento
							.pesquisarIdImoveisDebitoCobradoPorPrestacaoCobradas(idFaturamentoGrupo, anoMesReferencia, "=");

			if(colecaoIdsImoveisComDebitoACobrarHistorico != null && !colecaoIdsImoveisComDebitoACobrarHistorico.isEmpty()){
				// 1.2.1 Pesquisar os debitos_a_cobrar_historico e atualizar o número de prestações
				// cobradas com
				// número de prestações cobradas - 1 e inserir devolta em debito_a_cobrar e remover
				// registro em debitos_a_cobrar_historico
				Collection colecaoDebitoACobrarHistorico = repositorioFaturamento.pesquisarDebitoACobrarHistoricoPorListaDeImoveis(
								colecaoIdsImoveisComDebitoACobrarHistorico, anoMesReferencia);

				if(colecaoDebitoACobrarHistorico != null && !colecaoDebitoACobrarHistorico.isEmpty()){
					Iterator itt = colecaoDebitoACobrarHistorico.iterator();
					Integer quantidade = 0;
					while(itt.hasNext()){

						DebitoACobrarHistorico debitoACobrarHistorico = (DebitoACobrarHistorico) itt.next();

						short numeroPrestacaoCobradasHistorico = (short) (debitoACobrarHistorico.getPrestacaoCobradas() - 1);

						System.out.println("debitoACobrar : " + debitoACobrarHistorico.getId() + " Prest. cobradas : "
										+ debitoACobrarHistorico.getPrestacaoCobradas() + " atualizando para "
										+ numeroPrestacaoCobradasHistorico);

						FiltroDebitoACobrarGeral filtroDebitoACobrarGeral = new FiltroDebitoACobrarGeral();
						filtroDebitoACobrarGeral.adicionarParametro(new ParametroSimples(FiltroDebitoACobrarGeral.ID,
										debitoACobrarHistorico.getId()));

						Collection colecaoDebitoCobradoACobrarGeral = this.getControladorUtil().pesquisar(filtroDebitoACobrarGeral,
										DebitoACobrarGeral.class.getName());

						DebitoACobrarGeral debitoACobrarGeral = (DebitoACobrarGeral) Util
										.retonarObjetoDeColecao(colecaoDebitoCobradoACobrarGeral);

						debitoACobrarGeral.setIndicadorHistorico(ConstantesSistema.NAO);

						this.getControladorUtil().atualizar(debitoACobrarGeral);

						DebitoACobrar debitoACobrar = new DebitoACobrar();

						debitoACobrar.setDebitoACobrarGeral(debitoACobrarGeral);
						debitoACobrar.setId(debitoACobrarHistorico.getId());
						debitoACobrar.setDebitoTipo(debitoACobrarHistorico.getDebitoTipo());
						debitoACobrar.setValorDebito(debitoACobrarHistorico.getValorDebito());
						debitoACobrar.setImovel(debitoACobrarHistorico.getImovel());
						debitoACobrar.setGeracaoDebito(new Date());
						debitoACobrar.setLocalidade(debitoACobrarHistorico.getLocalidade());
						debitoACobrar.setQuadra(debitoACobrarHistorico.getQuadra());
						debitoACobrar.setAnoMesReferenciaDebito(debitoACobrarHistorico.getAnoMesReferenciaDebito());
						debitoACobrar.setLancamentoItemContabil(debitoACobrarHistorico.getLancamentoItemContabil());
						debitoACobrar.setFinanciamentoTipo(debitoACobrarHistorico.getFinanciamentoTipo());
						debitoACobrar.setCobrancaForma(debitoACobrarHistorico.getCobrancaForma());
						debitoACobrar.setUltimaAlteracao(new Date());
						debitoACobrar.setAnoMesCobrancaDebito(debitoACobrarHistorico.getAnoMesCobrancaDebito());
						debitoACobrar.setDebitoCreditoSituacaoAtual(debitoACobrarHistorico.getDebitoCreditoSituacaoAtual());

						debitoACobrar.setNumeroPrestacaoDebito(debitoACobrarHistorico.getPrestacaoDebito());
						short numeroPrestacaoCobradas = (short) (debitoACobrarHistorico.getPrestacaoCobradas() - 1);
						debitoACobrar.setNumeroPrestacaoCobradas(numeroPrestacaoCobradas);


							quantidade = quantidade + 1;


						Collection colecaoDebitoACobrarCategoria = new ArrayList();
							Collection<DebitoACobrarCategoriaHistorico> colecaoDebitoACobrarCategoriaHistorico = repositorioFaturamento
										.pesquisarDebitoACobrarCategoriaHistorico(debitoACobrar.getId());


							if(colecaoDebitoACobrarCategoriaHistorico != null && !colecaoDebitoACobrarCategoriaHistorico.isEmpty()){


								Iterator ittt = colecaoDebitoACobrarCategoriaHistorico.iterator();
							DebitoACobrarCategoria debitoACobrarCategoria = new DebitoACobrarCategoria();

								while(ittt.hasNext()){

									DebitoACobrarCategoriaHistorico debitoACobrarCategoriaHistorico = (DebitoACobrarCategoriaHistorico) ittt
												.next();

									DebitoACobrarCategoriaPK debitoACobrarCategoriaPK = new DebitoACobrarCategoriaPK(debitoACobrar,
												debitoACobrarCategoriaHistorico.getCategoria());

								debitoACobrarCategoria.setComp_id(debitoACobrarCategoriaPK);
								debitoACobrarCategoria.setCategoria(debitoACobrarCategoriaHistorico.getCategoria());
								debitoACobrarCategoria.setDebitoACobrar(debitoACobrar);

								debitoACobrarCategoria.setQuantidadeEconomia(debitoACobrarCategoriaHistorico.getQuantidadeEconomia());
								debitoACobrarCategoria.setValorCategoria(debitoACobrarCategoriaHistorico.getValorCategoria());
								debitoACobrarCategoria.setUltimaAlteracao(new Date());



								

								System.out.println("debac hist = " + debitoACobrarHistorico.getId());

								colecaoDebitoACobrarCategoria.add(debitoACobrarCategoria);


								}

							repositorioFaturamento.removerDebitoACobrarCategoriaHistorico(debitoACobrarHistorico.getId());

							repositorioFaturamento.deletarDebitoACobrarHistorico(debitoACobrarHistorico.getId());

							repositorioFaturamento.inserirDebitoACobrar(debitoACobrar);

							repositorioFaturamento.inserirDebitoACobrarCategoria(debitoACobrarCategoria);

							}





						System.out.println("Quantidade de  Debito a Cobrar Inseridos = " + quantidade);

					}
				}

			
				System.out.println("Quantidade de  Debito a Cobrar Historico removidos = " + colecaoDebitoACobrarHistorico.size());
			}

	


			// // 1.2.3 Removendo debitos_a_cobrar_historico e debitos_a_cobrar_historico_categoria

			Collection colecaoDebitoCobradoParaRemover2 = repositorioFaturamento.pesquisarDebitoCobradoPorPrestacaoCobradas(
							idFaturamentoGrupo, anoMesReferencia, "=");

			if(colecaoDebitoCobradoParaRemover2 != null && !colecaoDebitoCobradoParaRemover2.isEmpty()){
				Collection colecaoDebitoCobradoCategoria2 = new ArrayList();


				Iterator itDebitoCobrado2 = colecaoDebitoCobradoParaRemover2.iterator();
				while(itDebitoCobrado2.hasNext()){
					DebitoCobrado debitoCobrado2 = (DebitoCobrado) itDebitoCobrado2.next();

					FiltroDebitoCobradoCategoria filtroDebitoCobradoCategoria2 = new FiltroDebitoCobradoCategoria();
					filtroDebitoCobradoCategoria2.adicionarParametro(new ParametroSimples(FiltroDebitoCobradoCategoria.DEBITO_COBRADO_ID,
									debitoCobrado2.getId()));

					filtroDebitoCobradoCategoria2.adicionarCaminhoParaCarregamentoEntidade(FiltroDebitoACobrarCategoriaHistorico.CATEGORIA);

					colecaoDebitoCobradoCategoria2 = this.getControladorUtil().pesquisar(filtroDebitoCobradoCategoria2,
									DebitoCobradoCategoria.class.getName());

					Iterator ittt = colecaoDebitoCobradoCategoria2.iterator();
					while(ittt.hasNext()){
						DebitoCobradoCategoria debitoCobradoCategoria2 = (DebitoCobradoCategoria) ittt.next();
						repositorioFaturamento.deletarDebitoCobradoCategoria(debitoCobradoCategoria2.getComp_id().getDebitoCobrado()
										.getId());
					}


					repositorioFaturamento.deletarDebitoCobrado(debitoCobrado2.getId());

				}

				System.out.println("Quantidade de  Debitos Cobrados removidos = " + colecaoDebitoCobradoParaRemover2.size());
			}



			System.out.println("----------------------FIM DO CASO 2---------------------------------------------------------");

			Collection colecaoContaGeral = repositorioFaturamento.pesquisarContaGeral(anoMesReferencia, idFaturamentoGrupo, 9);
			Iterator itttt = colecaoContaGeral.iterator();
			while(itttt.hasNext()){
				ContaGeral contaGeral = (ContaGeral) itttt.next();
				
				System.out.println("Conta = " + contaGeral.getId());

				repositorioFaturamento.deletarContaCategoria(contaGeral.getId());

				repositorioFaturamento.deletarClienteConta(contaGeral.getId());

				repositorioFaturamento.deletarConta(contaGeral.getId());

				repositorioFaturamento.deletarContaGeral(contaGeral.getId());

			}

			System.out.println("Quantidade de Contas removidas = " + colecaoContaGeral.size());

			System.out.println("----------------------FIM DO CASO 3---------------------------------------------------------");

			Collection colecaoMovimentoRoteiroEmpresa = repositorioFaturamento.pesquisarMovimentoRoteiroEmpresa(anoMesReferencia,
							idFaturamentoGrupo,
							9);

			System.out.println(" Movimento Roteiro Empresa Reovidos: " + colecaoMovimentoRoteiroEmpresa.size());

			// Remover movimento_roteiro_empresa
			this.getControladorUtil().removerColecaoObjetos(colecaoMovimentoRoteiroEmpresa);

			System.out.println("----------------------FIM DO CASO 4---------------------------------------------------------");

			System.out.println("----------------FIM--------------------");

		}catch(CreateException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
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


	public void executarAjustarRegistrosContaEGuiaDeso() throws Exception{

		this.ejbCreate();

		StringBuilder arquivoConferencia = new StringBuilder();

		arquivoConferencia.append("*** INICIO AJUSTE REGISTOS CONTA E GUIA " + new Date());
		arquivoConferencia.append(System.getProperty("line.separator"));

		log.info("*** INICIO AJUSTE REGISTOS CONTA E GUIA");

		String nomeArquivo = "AjusteRegistrosContaEGuiaDeso" + Util.formatarDataComTracoAAAAMMDDHHMMSS(new Date()) + ".txt";

		// this.ajustarGuiasInvalidas(arquivoConferencia);
		//
		// arquivoConferencia.append(System.getProperty("line.separator"));
		// arquivoConferencia.append(System.getProperty("line.separator"));

		this.ajustarContasInvalidas(arquivoConferencia);

		arquivoConferencia.append(System.getProperty("line.separator"));
		arquivoConferencia.append(System.getProperty("line.separator"));

		this.ajustarContasHistoricoInvalidas(arquivoConferencia);

		arquivoConferencia.append(System.getProperty("line.separator"));
		arquivoConferencia.append(System.getProperty("line.separator"));

		arquivoConferencia.append("*** FIM AJUSTE REGISTOS CONTA E GUIA " + new Date());

		log.info("*** FIM AJUSTE REGISTOS CONTA E GUIA");

		this.gerarArquivo(arquivoConferencia, nomeArquivo);
	}

	public void executarAjustarContabilidadeArrecadacao(Integer limite) throws Exception{

		this.ejbCreate();

		StringBuilder arquivoConferencia = new StringBuilder();

		arquivoConferencia.append("*** INICIO AJUSTE CONTABILIDADE ARRECADACAO " + new Date());
		arquivoConferencia.append(System.getProperty("line.separator"));

		String nomeArquivo = "AjusteContabilidadeDeso" + Util.formatarDataComTracoAAAAMMDDHHMMSS(new Date()) + ".txt";

		log.info("*** INICIO AJUSTE CONTABILIDADE ARRECADACAO");
		log.info("*** LIMITE: " + limite);

		Collection<AjusteContabilidade> colecaoAjusteContabilidade = repositorioArrecadacao
						.pesquisarPagamentoHistoricoParaAjusteContabilidadeArrecadacao(limite);

		if(!Util.isVazioOrNulo(colecaoAjusteContabilidade)){
			log.info("*** QUANTIDADE TOTAL: " + colecaoAjusteContabilidade.size());

			Collection<AjusteContabilidade> colecaoAjusteContabilidadeAtualizar = new ArrayList<AjusteContabilidade>();

			int qtd = 0;
			int qtdAux = 0;

			PagamentoHistorico pagamentoHistorico = null;
			Integer idPagamento = null;

			for(AjusteContabilidade ajusteContabilidade : colecaoAjusteContabilidade){
				idPagamento = ajusteContabilidade.getIdPagamento();

				ajusteContabilidade.setIcProcessado(1);
				colecaoAjusteContabilidadeAtualizar.add(ajusteContabilidade);

				pagamentoHistorico = new PagamentoHistorico();
				pagamentoHistorico.setId(idPagamento);

				this.getControladorContabil().registrarLancamentoContabil(pagamentoHistorico, OperacaoContabil.INCLUIR_PAGAMENTO);

				qtd = qtd + 1;
				qtdAux = qtdAux + 1;

				if(qtdAux == 500){
					log.info("*** REGISTROS PROCESSADOS: " + qtd);
					qtdAux = 0;
				}
			}

			if(!Util.isVazioOrNulo(colecaoAjusteContabilidadeAtualizar)){
				this.getControladorUtil().atualizarColecaoObjetos(colecaoAjusteContabilidadeAtualizar);
			}
		}

		arquivoConferencia.append(System.getProperty("line.separator"));
		arquivoConferencia.append("*** INICIO AJUSTE CONTABILIDADE ARRECADACAO " + new Date());

		log.info("*** FIM AJUSTE CONTABILIDADE ARRECADACAO");

		this.gerarArquivo(arquivoConferencia, nomeArquivo);
	}

	private void ajustarGuiasInvalidas(StringBuilder arquivoConferencia) throws ErroRepositorioException, ControladorException{

		log.info("*** INICIO - ajustarGuiasInvalidas ");

		Map<Integer, Imovel> mapGuiaImovel = new HashMap<Integer, Imovel>();

		Collection<Object[]> colecaoGuiaPagamentoPrestacaoInvalida = repositorioArrecadacao
						.pesquisarGuiaPagamentoPrestacaoComCategoriaInvalida();

		if(!Util.isVazioOrNulo(colecaoGuiaPagamentoPrestacaoInvalida)){
			log.info("*** QUANTIDADE GUIAS PRESTACAO INVALIDA: " + colecaoGuiaPagamentoPrestacaoInvalida.size());

			Integer idGuiaPagamento = null;
			Short numeroPrestacao = null;
			Imovel imovel = null;

			GuiaPagamento guiaPagamento = null;
			GuiaPagamentoHistorico guiaPagamentoHistorico = null;

			FiltroGuiaPagamento filtroGuiaPagamento = null;
			FiltroGuiaPagamentoHistorico filtroGuiaPagamentoHistorico = null;

			Collection<GuiaPagamento> colecaoGuiaPagamento = null;
			Collection<GuiaPagamentoHistorico> colecaoGuiaPagamentoHistorico = null;

			int qtd = 0;
			int qtdAux = 0;

			for(Object[] obj : colecaoGuiaPagamentoPrestacaoInvalida){
				idGuiaPagamento = (Integer) obj[0];
				numeroPrestacao = (Short) obj[1];
				imovel = null;

				if(mapGuiaImovel.containsKey(idGuiaPagamento)){
					imovel = mapGuiaImovel.get(idGuiaPagamento);
				}else{
					filtroGuiaPagamento = new FiltroGuiaPagamento();
					filtroGuiaPagamento.adicionarParametro(new ParametroSimples(FiltroGuiaPagamento.ID, idGuiaPagamento));

					colecaoGuiaPagamento = getControladorUtil().pesquisar(filtroGuiaPagamento, GuiaPagamento.class.getName());

					if(!Util.isVazioOrNulo(colecaoGuiaPagamento)){
						guiaPagamento = (GuiaPagamento) Util.retonarObjetoDeColecao(colecaoGuiaPagamento);
						imovel = guiaPagamento.getImovel();

						mapGuiaImovel.put(idGuiaPagamento, imovel);
					}else{
						filtroGuiaPagamentoHistorico = new FiltroGuiaPagamentoHistorico();
						filtroGuiaPagamentoHistorico.adicionarParametro(new ParametroSimples(FiltroGuiaPagamentoHistorico.ID,
										idGuiaPagamento));

						colecaoGuiaPagamentoHistorico = getControladorUtil().pesquisar(filtroGuiaPagamentoHistorico,
										GuiaPagamentoHistorico.class.getName());

						if(!Util.isVazioOrNulo(colecaoGuiaPagamentoHistorico)){
							guiaPagamentoHistorico = (GuiaPagamentoHistorico) Util.retonarObjetoDeColecao(colecaoGuiaPagamentoHistorico);
							imovel = guiaPagamentoHistorico.getImovel();

							mapGuiaImovel.put(idGuiaPagamento, imovel);
						}
					}
				}

				if(imovel != null){
					qtd = qtd + 1;
					qtdAux = qtdAux + 1;

					if(qtdAux == 500){
						log.info("*** GUIAS PRESTACAO AJUSTADAS: " + qtd);
						qtdAux = 0;
					}

					arquivoConferencia.append("*** GUIA PRESTACAO AJUSTADA: idGuiaPagamento - " + idGuiaPagamento + ";numeroPrestacao - "
									+ numeroPrestacao);
					arquivoConferencia.append(System.getProperty("line.separator"));

					this.getControladorArrecadacao().distribuirValoresGuiaPorCategoria(idGuiaPagamento, numeroPrestacao, imovel);
				}else{
					log.error("*** ERRO - GUIA PRESTACAO NAO AJUSTADA: idGuiaPagamento - " + idGuiaPagamento + ";numeroPrestacao - "
									+ numeroPrestacao);

					arquivoConferencia.append("*** ERRO - GUIA PRESTACAO NAO AJUSTADA: idGuiaPagamento - " + idGuiaPagamento
									+ ";numeroPrestacao - " + numeroPrestacao);
					arquivoConferencia.append(System.getProperty("line.separator"));
				}
			}
		}

		Collection<Object[]> colecaoGuiaPagamentoPrestacaoHistoricoInvalida = repositorioArrecadacao
						.pesquisarGuiaPagamentoPrestacaoHistoricoComCategoriaInvalida();

		if(!Util.isVazioOrNulo(colecaoGuiaPagamentoPrestacaoHistoricoInvalida)){
			log.info("*** QUANTIDADE GUIAS PRESTACAO HISTORICO INVALIDA: " + colecaoGuiaPagamentoPrestacaoHistoricoInvalida.size());

			Integer idGuiaPagamento = null;
			Short numeroPrestacao = null;
			Imovel imovel = null;

			GuiaPagamento guiaPagamento = null;
			GuiaPagamentoHistorico guiaPagamentoHistorico = null;

			FiltroGuiaPagamento filtroGuiaPagamento = null;
			FiltroGuiaPagamentoHistorico filtroGuiaPagamentoHistorico = null;

			Collection<GuiaPagamento> colecaoGuiaPagamento = null;
			Collection<GuiaPagamentoHistorico> colecaoGuiaPagamentoHistorico = null;

			int qtd = 0;
			int qtdAux = 0;

			for(Object[] obj : colecaoGuiaPagamentoPrestacaoHistoricoInvalida){
				idGuiaPagamento = (Integer) obj[0];
				numeroPrestacao = (Short) obj[1];
				imovel = null;

				if(mapGuiaImovel.containsKey(idGuiaPagamento)){
					imovel = mapGuiaImovel.get(idGuiaPagamento);
				}else{
					filtroGuiaPagamento = new FiltroGuiaPagamento();
					filtroGuiaPagamento.adicionarParametro(new ParametroSimples(FiltroGuiaPagamento.ID, idGuiaPagamento));

					colecaoGuiaPagamento = getControladorUtil().pesquisar(filtroGuiaPagamento, GuiaPagamento.class.getName());

					if(!Util.isVazioOrNulo(colecaoGuiaPagamento)){
						guiaPagamento = (GuiaPagamento) Util.retonarObjetoDeColecao(colecaoGuiaPagamento);
						imovel = guiaPagamento.getImovel();

						mapGuiaImovel.put(idGuiaPagamento, imovel);
					}else{
						filtroGuiaPagamentoHistorico = new FiltroGuiaPagamentoHistorico();
						filtroGuiaPagamentoHistorico.adicionarParametro(new ParametroSimples(FiltroGuiaPagamentoHistorico.ID,
										idGuiaPagamento));

						colecaoGuiaPagamentoHistorico = getControladorUtil().pesquisar(filtroGuiaPagamentoHistorico,
										GuiaPagamentoHistorico.class.getName());

						if(!Util.isVazioOrNulo(colecaoGuiaPagamentoHistorico)){
							guiaPagamentoHistorico = (GuiaPagamentoHistorico) Util.retonarObjetoDeColecao(colecaoGuiaPagamentoHistorico);
							imovel = guiaPagamentoHistorico.getImovel();

							mapGuiaImovel.put(idGuiaPagamento, imovel);
						}
					}
				}

				if(imovel != null){
					qtd = qtd + 1;
					qtdAux = qtdAux + 1;

					if(qtdAux == 500){
						log.info("*** GUIAS PRESTACAO HISTORICO AJUSTADAS: " + qtd);
						qtdAux = 0;
					}

					arquivoConferencia.append("*** GUIA PRESTACAO HISTORICO AJUSTADA: idGuiaPagamento - " + idGuiaPagamento
									+ ";numeroPrestacao - " + numeroPrestacao);
					arquivoConferencia.append(System.getProperty("line.separator"));

					this.getControladorArrecadacao().distribuirValoresGuiaPorCategoriaHistorico(idGuiaPagamento, numeroPrestacao, imovel);
				}else{
					log.error("*** ERRO - GUIA PRESTACAO HISTORICO NAO AJUSTADA: idGuiaPagamento - " + idGuiaPagamento
									+ ";numeroPrestacao - " + numeroPrestacao);

					arquivoConferencia.append("*** ERRO - GUIA PRESTACAO HISTORICO NAO AJUSTADA: idGuiaPagamento - " + idGuiaPagamento
									+ ";numeroPrestacao - " + numeroPrestacao);
					arquivoConferencia.append(System.getProperty("line.separator"));
				}
			}
		}

		log.info("*** FIM - ajustarGuiasInvalidas ");
	}

	private void ajustarContasInvalidas(StringBuilder arquivoConferencia) throws ControladorException, ErroRepositorioException{

		log.info("*** INICIO - ajustarContasInvalidas ");

		Collection<Integer> colecaoIdConta = repositorioArrecadacao.pesquisarContaComCategoriaInvalida();

		if(!Util.isVazioOrNulo(colecaoIdConta)){
			log.info("*** QUANTIDADE CONTA INVALIDA: " + colecaoIdConta.size());

			Map<Integer, Imovel> mapImovel = new HashMap<Integer, Imovel>();

			int qtd = 0;
			int qtdAux = 0;

			for(Integer idConta : colecaoIdConta){
				FiltroConta filtroConta = new FiltroConta();
				filtroConta.adicionarParametro(new ParametroSimples(FiltroConta.ID, idConta));

				Collection<Conta> colecaoConta = this.getControladorUtil().pesquisar(filtroConta, Conta.class.getName());

				Conta conta = (Conta) Util.retonarObjetoDeColecao(colecaoConta);

				Imovel imovel = conta.getImovel();
				Integer idImovel = imovel.getId();

				if(mapImovel.containsKey(idImovel)){
					imovel = mapImovel.get(idImovel);
				}else{
					imovel = this.getControladorImovel().pesquisarImovel(idImovel);
					mapImovel.put(idImovel, imovel);
				}

				Short indicadorFaturamentoAgua = ConstantesSistema.NAO;

				BigDecimal valorAgua = conta.getValorAgua();

				if(valorAgua != null && valorAgua.compareTo(BigDecimal.ZERO) > 0){
					indicadorFaturamentoAgua = ConstantesSistema.SIM;
				}

				Short indicadorFaturamentoEsgoto = ConstantesSistema.NAO;

				BigDecimal valorEsgoto = conta.getValorEsgoto();

				if(valorEsgoto != null && valorEsgoto.compareTo(BigDecimal.ZERO) > 0){
					indicadorFaturamentoEsgoto = ConstantesSistema.SIM;
				}

				Collection colecaoCategorias = this.getControladorImovel().obterQuantidadeEconomiasCategoria(imovel);

				FiltroMedicaoHistorico filtroMedicaoHistorico = new FiltroMedicaoHistorico();
				filtroMedicaoHistorico.adicionarParametro(new ParametroSimples(FiltroMedicaoHistorico.LIGACAO_AGUA_ID, imovel.getId()));
				filtroMedicaoHistorico.adicionarParametro(new ParametroSimples(FiltroMedicaoHistorico.ANO_MES_REFERENCIA_FATURAMENTO, conta
								.getReferencia()));
				filtroMedicaoHistorico.adicionarParametro(new ParametroSimples(FiltroMedicaoHistorico.MEDICAO_TIPO_ID,
								MedicaoTipo.LIGACAO_AGUA));

				Collection<MedicaoHistorico> colecaoMedicaoHistoricoAgua = this.getControladorUtil().pesquisar(filtroMedicaoHistorico,
								MedicaoHistorico.class.getName());

				MedicaoHistorico medicaoHistoricoAgua = null;

				if(!Util.isVazioOrNulo(colecaoMedicaoHistoricoAgua)){
					medicaoHistoricoAgua = (MedicaoHistorico) Util.retonarObjetoDeColecao(colecaoMedicaoHistoricoAgua);
				}

				Date dataLeituraAnteriorFaturamento = null;
				Date dataLeituraAtualFaturamento = null;

				if(medicaoHistoricoAgua != null){

					if(medicaoHistoricoAgua.getDataLeituraAnteriorFaturamento() != null){
						dataLeituraAnteriorFaturamento = medicaoHistoricoAgua.getDataLeituraAnteriorFaturamento();
					}

					if(medicaoHistoricoAgua.getDataLeituraAtualFaturamento() != null){
						dataLeituraAtualFaturamento = medicaoHistoricoAgua.getDataLeituraAtualFaturamento();
					}
				}

				MedicaoHistorico medicaoHistoricoPoco = null;

				if(imovel.getLigacaoEsgoto() != null && imovel.getLigacaoEsgotoSituacao() != null
								&& imovel.getLigacaoEsgotoSituacao().getId().intValue() == LigacaoEsgotoSituacao.LIGADO.intValue()){

					medicaoHistoricoPoco = this.getControladorMicromedicao().pesquisarMedicaoHistoricoTipoPoco(imovel.getId(),
									conta.getReferencia());

					if(medicaoHistoricoPoco != null){
						if(medicaoHistoricoPoco.getDataLeituraAnteriorFaturamento() != null){
							dataLeituraAnteriorFaturamento = medicaoHistoricoPoco.getDataLeituraAnteriorFaturamento();
						}

						if(medicaoHistoricoPoco.getDataLeituraAtualFaturamento() != null){
							dataLeituraAtualFaturamento = medicaoHistoricoPoco.getDataLeituraAtualFaturamento();
						}
					}
				}

				if(dataLeituraAtualFaturamento == null){
					dataLeituraAtualFaturamento = conta.getDataEmissao();
				}

				if(dataLeituraAtualFaturamento != null){
					int consumoMinimoLigacao = this.getControladorMicromedicao().obterConsumoMinimoLigacao(imovel, null);

					Collection<CalcularValoresAguaEsgotoHelper> colecaoCalcularValoresAguaEsgotoHelper = this.getControladorFaturamento()
									.calcularValoresAguaEsgoto(conta.getReferencia(), conta.getLigacaoAguaSituacao().getId(),
													conta.getLigacaoEsgotoSituacao().getId(), indicadorFaturamentoAgua,
													indicadorFaturamentoEsgoto, colecaoCategorias, conta.getConsumoAgua(),
													conta.getConsumoEsgoto(), consumoMinimoLigacao, dataLeituraAnteriorFaturamento,
													dataLeituraAtualFaturamento, conta.getPercentualEsgoto(),
													conta.getConsumoTarifa().getId(), imovel.getId());

					BigDecimal valorTotalAgua = BigDecimal.ZERO;
					BigDecimal valorTotalEsgoto = BigDecimal.ZERO;

					for(Iterator iteratorColecaoCalcularValoresAguaEsgotoHelper = colecaoCalcularValoresAguaEsgotoHelper.iterator(); iteratorColecaoCalcularValoresAguaEsgotoHelper
									.hasNext();){

						CalcularValoresAguaEsgotoHelper calcularValoresAguaEsgotoHelper = (CalcularValoresAguaEsgotoHelper) iteratorColecaoCalcularValoresAguaEsgotoHelper
										.next();

						if(calcularValoresAguaEsgotoHelper.getValorFaturadoAguaCategoria() != null){
							valorTotalAgua = valorTotalAgua.add(calcularValoresAguaEsgotoHelper.getValorFaturadoAguaCategoria());
						}

						if(calcularValoresAguaEsgotoHelper.getValorFaturadoEsgotoCategoria() != null){
							valorTotalEsgoto = valorTotalEsgoto.add(calcularValoresAguaEsgotoHelper.getValorFaturadoEsgotoCategoria());
						}
					}

					Object[] retornoDadosContaCategoriaEContaCategoriaConsumoFaixa = this
									.gerarDadosInserirContaCategoriaEInserirContaCategoriaConsumoFaixa(colecaoCategorias,
													colecaoCalcularValoresAguaEsgotoHelper, conta);

					Collection colecaoContaCategoriaAtualizar = (Collection) retornoDadosContaCategoriaEContaCategoriaConsumoFaixa[0];
					Collection colecaoContaCategoriaConsumoFaixaInserir = (Collection) retornoDadosContaCategoriaEContaCategoriaConsumoFaixa[1];

					BigDecimal valorFaturadoAgua = conta.getValorAgua();
					BigDecimal valorFaturadoEsgoto = conta.getValorEsgoto();

					boolean ajustouValor = false;

					if((valorTotalAgua.compareTo(valorFaturadoAgua) != 0 || valorTotalEsgoto.compareTo(valorFaturadoEsgoto) != 0)){
						Object[] retornoDadosContaCategoriaEContaCategoriaConsumoFaixaAjustado = this.getControladorFaturamento()
										.ajustarValoresPorCategoriaEFaixa(valorTotalAgua, valorFaturadoAgua, valorTotalEsgoto,
														valorFaturadoEsgoto, colecaoContaCategoriaConsumoFaixaInserir,
														colecaoContaCategoriaAtualizar, imovel, conta);

						colecaoContaCategoriaAtualizar = (Collection) retornoDadosContaCategoriaEContaCategoriaConsumoFaixaAjustado[0];
						colecaoContaCategoriaConsumoFaixaInserir = (Collection) retornoDadosContaCategoriaEContaCategoriaConsumoFaixaAjustado[1];

						ajustouValor = true;
					}

					if(!Util.isVazioOrNulo(colecaoContaCategoriaAtualizar)){
						qtd = qtd + 1;
						qtdAux = qtdAux + 1;

						if(qtdAux == 100){
							log.info("*** CONTAS AJUSTADAS: " + qtd);
							qtdAux = 0;
						}

						if(ajustouValor){
							arquivoConferencia.append("*** CONTA ALTEROU VALOR: idConta - " + idConta + ";valorTotalAgua - "
											+ valorTotalAgua + ";valorFaturadoAgua - " + valorFaturadoAgua + ";valorTotalEsgoto - "
											+ valorTotalEsgoto + ";valorFaturadoEsgoto - " + valorFaturadoEsgoto);
							arquivoConferencia.append(System.getProperty("line.separator"));
						}

						arquivoConferencia.append("*** CONTA AJUSTADA: idConta - " + idConta);
						arquivoConferencia.append(System.getProperty("line.separator"));

						repositorioArrecadacao.removerContaCategoria(idConta);

						this.getControladorBatch().inserirColecaoObjetoParaBatch(colecaoContaCategoriaAtualizar);

						if(!Util.isVazioOrNulo(colecaoContaCategoriaConsumoFaixaInserir)){
							this.getControladorBatch().inserirColecaoObjetoParaBatch(colecaoContaCategoriaConsumoFaixaInserir);
						}
					}else{
						log.error("*** ERRO - CONTA NAO AJUSTADA: idConta - " + idConta);

						arquivoConferencia.append("*** ERRO - CONTA NAO AJUSTADA: idConta - " + idConta);
						arquivoConferencia.append(System.getProperty("line.separator"));
					}
				}else{
					log.error("*** ERRO - CONTA NAO AJUSTADA - DATA ATUAL NULL: idConta - " + idConta);

					arquivoConferencia.append("*** ERRO - CONTA NAO AJUSTADA - DATA ATUAL NULL: idConta - " + idConta);
					arquivoConferencia.append(System.getProperty("line.separator"));
				}
			}
		}

		log.info("*** FIM - ajustarContasInvalidas ");
	}

	private void ajustarContasHistoricoInvalidas(StringBuilder arquivoConferencia) throws ControladorException, ErroRepositorioException{

		log.info("*** INICIO - ajustarContasHistoricoInvalidas ");

		Collection<Integer> colecaoIdContaHistorico = repositorioArrecadacao.pesquisarContaHistoricoComCategoriaInvalida();

		if(!Util.isVazioOrNulo(colecaoIdContaHistorico)){
			log.info("*** QUANTIDADE CONTA HISTORICO INVALIDA: " + colecaoIdContaHistorico.size());

			Map<Integer, Imovel> mapImovel = new HashMap<Integer, Imovel>();

			int qtd = 0;
			int qtdAux = 0;

			for(Integer idContaHistorico : colecaoIdContaHistorico){
				FiltroContaHistorico filtroContaHistorico = new FiltroContaHistorico();
				filtroContaHistorico.adicionarParametro(new ParametroSimples(FiltroContaHistorico.ID, idContaHistorico));

				Collection<ContaHistorico> colecaoConta = this.getControladorUtil().pesquisar(filtroContaHistorico,
								ContaHistorico.class.getName());

				ContaHistorico contaHistorico = (ContaHistorico) Util.retonarObjetoDeColecao(colecaoConta);

				Imovel imovel = contaHistorico.getImovel();
				Integer idImovel = imovel.getId();

				if(mapImovel.containsKey(idImovel)){
					imovel = mapImovel.get(idImovel);
				}else{
					imovel = this.getControladorImovel().pesquisarImovel(idImovel);
					mapImovel.put(idImovel, imovel);
				}

				Short indicadorFaturamentoAgua = ConstantesSistema.NAO;

				BigDecimal valorAgua = contaHistorico.getValorAgua();

				if(valorAgua != null && valorAgua.compareTo(BigDecimal.ZERO) > 0){
					indicadorFaturamentoAgua = ConstantesSistema.SIM;
				}

				Short indicadorFaturamentoEsgoto = ConstantesSistema.NAO;

				BigDecimal valorEsgoto = contaHistorico.getValorEsgoto();

				if(valorEsgoto != null && valorEsgoto.compareTo(BigDecimal.ZERO) > 0){
					indicadorFaturamentoEsgoto = ConstantesSistema.SIM;
				}

				Collection colecaoCategorias = this.getControladorImovel().obterQuantidadeEconomiasCategoria(imovel);

				FiltroMedicaoHistorico filtroMedicaoHistorico = new FiltroMedicaoHistorico();
				filtroMedicaoHistorico.adicionarParametro(new ParametroSimples(FiltroMedicaoHistorico.LIGACAO_AGUA_ID, imovel.getId()));
				filtroMedicaoHistorico.adicionarParametro(new ParametroSimples(FiltroMedicaoHistorico.ANO_MES_REFERENCIA_FATURAMENTO,
								contaHistorico.getAnoMesReferenciaConta()));
				filtroMedicaoHistorico.adicionarParametro(new ParametroSimples(FiltroMedicaoHistorico.MEDICAO_TIPO_ID,
								MedicaoTipo.LIGACAO_AGUA));

				Collection<MedicaoHistorico> colecaoMedicaoHistoricoAgua = this.getControladorUtil().pesquisar(filtroMedicaoHistorico,
								MedicaoHistorico.class.getName());

				MedicaoHistorico medicaoHistoricoAgua = null;

				if(!Util.isVazioOrNulo(colecaoMedicaoHistoricoAgua)){
					medicaoHistoricoAgua = (MedicaoHistorico) Util.retonarObjetoDeColecao(colecaoMedicaoHistoricoAgua);
				}

				Date dataLeituraAnteriorFaturamento = null;
				Date dataLeituraAtualFaturamento = null;

				if(medicaoHistoricoAgua != null){

					if(medicaoHistoricoAgua.getDataLeituraAnteriorFaturamento() != null){
						dataLeituraAnteriorFaturamento = medicaoHistoricoAgua.getDataLeituraAnteriorFaturamento();
					}

					if(medicaoHistoricoAgua.getDataLeituraAtualFaturamento() != null){
						dataLeituraAtualFaturamento = medicaoHistoricoAgua.getDataLeituraAtualFaturamento();
					}
				}

				MedicaoHistorico medicaoHistoricoPoco = null;

				if(imovel.getLigacaoEsgoto() != null && imovel.getLigacaoEsgotoSituacao() != null
								&& imovel.getLigacaoEsgotoSituacao().getId().intValue() == LigacaoEsgotoSituacao.LIGADO.intValue()){

					medicaoHistoricoPoco = this.getControladorMicromedicao().pesquisarMedicaoHistoricoTipoPoco(imovel.getId(),
									contaHistorico.getAnoMesReferenciaConta());

					if(medicaoHistoricoPoco != null){
						if(medicaoHistoricoPoco.getDataLeituraAnteriorFaturamento() != null){
							dataLeituraAnteriorFaturamento = medicaoHistoricoPoco.getDataLeituraAnteriorFaturamento();
						}

						if(medicaoHistoricoPoco.getDataLeituraAtualFaturamento() != null){
							dataLeituraAtualFaturamento = medicaoHistoricoPoco.getDataLeituraAtualFaturamento();
						}
					}
				}

				if(dataLeituraAtualFaturamento == null){
					dataLeituraAtualFaturamento = contaHistorico.getDataEmissao();
				}

				if(dataLeituraAtualFaturamento != null){
					int consumoMinimoLigacao = this.getControladorMicromedicao().obterConsumoMinimoLigacao(imovel, null);

					Collection<CalcularValoresAguaEsgotoHelper> colecaoCalcularValoresAguaEsgotoHelper = this.getControladorFaturamento()
									.calcularValoresAguaEsgoto(contaHistorico.getAnoMesReferenciaConta(),
													contaHistorico.getLigacaoAguaSituacao().getId(),
													contaHistorico.getLigacaoEsgotoSituacao().getId(), indicadorFaturamentoAgua,
													indicadorFaturamentoEsgoto, colecaoCategorias, contaHistorico.getConsumoAgua(),
													contaHistorico.getConsumoEsgoto(), consumoMinimoLigacao,
													dataLeituraAnteriorFaturamento, dataLeituraAtualFaturamento,
													contaHistorico.getPercentualEsgoto(), contaHistorico.getConsumoTarifa().getId(),
													imovel.getId());

					BigDecimal valorTotalAgua = BigDecimal.ZERO;
					BigDecimal valorTotalEsgoto = BigDecimal.ZERO;

					for(Iterator iteratorColecaoCalcularValoresAguaEsgotoHelper = colecaoCalcularValoresAguaEsgotoHelper.iterator(); iteratorColecaoCalcularValoresAguaEsgotoHelper
									.hasNext();){

						CalcularValoresAguaEsgotoHelper calcularValoresAguaEsgotoHelper = (CalcularValoresAguaEsgotoHelper) iteratorColecaoCalcularValoresAguaEsgotoHelper
										.next();

						if(calcularValoresAguaEsgotoHelper.getValorFaturadoAguaCategoria() != null){
							valorTotalAgua = valorTotalAgua.add(calcularValoresAguaEsgotoHelper.getValorFaturadoAguaCategoria());
						}

						if(calcularValoresAguaEsgotoHelper.getValorFaturadoEsgotoCategoria() != null){
							valorTotalEsgoto = valorTotalEsgoto.add(calcularValoresAguaEsgotoHelper.getValorFaturadoEsgotoCategoria());
						}
					}

					Object[] retornoDadosContaCategoriaEContaCategoriaConsumoFaixa = this
									.gerarDadosInserirContaCategoriaHistoricoEInserirContaCategoriaHistoricoConsumoFaixa(colecaoCategorias,
													colecaoCalcularValoresAguaEsgotoHelper, contaHistorico);

					Collection colecaoContaCategoriaHistoricoInserir = (Collection) retornoDadosContaCategoriaEContaCategoriaConsumoFaixa[0];
					Collection colecaoContaCategoriaConsumoFaixaHistoricoInserir = (Collection) retornoDadosContaCategoriaEContaCategoriaConsumoFaixa[1];

					BigDecimal valorFaturadoAgua = contaHistorico.getValorAgua();
					BigDecimal valorFaturadoEsgoto = contaHistorico.getValorEsgoto();

					boolean ajustouValor = false;

					if((valorTotalAgua.compareTo(valorFaturadoAgua) != 0 || valorTotalEsgoto.compareTo(valorFaturadoEsgoto) != 0)){
						Object[] retornoDadosContaCategoriaEContaCategoriaConsumoFaixaAjustado = this
										.ajustarValoresPorCategoriaEFaixaHistorico(valorTotalAgua, valorFaturadoAgua, valorTotalEsgoto,
														valorFaturadoEsgoto, colecaoContaCategoriaConsumoFaixaHistoricoInserir,
														colecaoContaCategoriaHistoricoInserir, imovel, contaHistorico.getPercentualEsgoto());

						colecaoContaCategoriaHistoricoInserir = (Collection) retornoDadosContaCategoriaEContaCategoriaConsumoFaixaAjustado[0];
						colecaoContaCategoriaConsumoFaixaHistoricoInserir = (Collection) retornoDadosContaCategoriaEContaCategoriaConsumoFaixaAjustado[1];

						ajustouValor = true;
					}

					if(!Util.isVazioOrNulo(colecaoContaCategoriaHistoricoInserir)){
						qtd = qtd + 1;
						qtdAux = qtdAux + 1;

						if(qtdAux == 100){
							log.info("*** CONTAS HISTORICO AJUSTADAS: " + qtd);
							qtdAux = 0;
						}

						if(ajustouValor){
							arquivoConferencia.append("*** CONTA HISTORICO ALTEROU VALOR: idContaHistorico - " + idContaHistorico
											+ ";valorTotalAgua - " + valorTotalAgua + ";valorFaturadoAgua - " + valorFaturadoAgua
											+ ";valorTotalEsgoto - " + valorTotalEsgoto + ";valorFaturadoEsgoto - " + valorFaturadoEsgoto);
							arquivoConferencia.append(System.getProperty("line.separator"));
						}

						arquivoConferencia.append("*** CONTA HISTORICO AJUSTADA: idContaHistorico - " + idContaHistorico);
						arquivoConferencia.append(System.getProperty("line.separator"));

						repositorioArrecadacao.removerContaCategoriaHistorico(idContaHistorico);

						this.getControladorBatch().inserirColecaoObjetoParaBatch(colecaoContaCategoriaHistoricoInserir);

						if(!Util.isVazioOrNulo(colecaoContaCategoriaConsumoFaixaHistoricoInserir)){
							this.getControladorBatch().inserirColecaoObjetoParaBatch(colecaoContaCategoriaConsumoFaixaHistoricoInserir);
						}
					}else{
						log.error("*** ERRO - CONTA HISTORICO NAO AJUSTADA: idContaHistorico - " + idContaHistorico);

						arquivoConferencia.append("*** ERRO - CONTA HISTORICO NAO AJUSTADA: idContaHistorico - " + idContaHistorico);
						arquivoConferencia.append(System.getProperty("line.separator"));
					}
				}else{
					log.error("*** ERRO - CONTA HISTORICO NAO AJUSTADA - DATA ATUAL NULL: idContaHistorico - " + idContaHistorico);

					arquivoConferencia.append("*** ERRO - CONTA HISTORICO NAO AJUSTADA - DATA ATUAL NULL: idContaHistorico - "
									+ idContaHistorico);
					arquivoConferencia.append(System.getProperty("line.separator"));
				}
			}
		}

		log.info("*** FIM - ajustarContasHistoricoInvalidas ");
	}

	private Object[] gerarDadosInserirContaCategoriaEInserirContaCategoriaConsumoFaixa(Collection colecaoCategorias,
					Collection colecaoCalcularValoresAguaEsgotoHelper, Conta conta) throws ControladorException{

		Object[] retorno = new Object[2];
		Collection colecaoContaCategoriaIncluir = null;
		ContaCategoria contaCategoria = null;
		ContaCategoriaPK contaCategoriaPK = null;
		Categoria categoria = null;

		ContaCategoriaConsumoFaixa contaCategoriaConsumoFaixa = null;
		Collection colecaoContaCategoriaConsumoFaixaInserir = null;

		if((colecaoCategorias != null && !colecaoCategorias.isEmpty())
						&& (colecaoCalcularValoresAguaEsgotoHelper != null && !colecaoCalcularValoresAguaEsgotoHelper.isEmpty())){

			colecaoContaCategoriaIncluir = new ArrayList();
			colecaoContaCategoriaConsumoFaixaInserir = new ArrayList();
			CalcularValoresAguaEsgotoHelper calcularValoresAguaEsgotoHelper = null;

			Iterator iteratorColecaoCategorias = colecaoCategorias.iterator();

			Iterator iteratorColecaoCalcularValoresAguaEsgotoHelper = colecaoCalcularValoresAguaEsgotoHelper.iterator();

			while(iteratorColecaoCategorias.hasNext() && iteratorColecaoCalcularValoresAguaEsgotoHelper.hasNext()){
				categoria = (Categoria) iteratorColecaoCategorias.next();
				calcularValoresAguaEsgotoHelper = (CalcularValoresAguaEsgotoHelper) iteratorColecaoCalcularValoresAguaEsgotoHelper.next();

				contaCategoria = new ContaCategoria();
				contaCategoriaPK = new ContaCategoriaPK();

				contaCategoriaPK.setConta(conta);
				contaCategoriaPK.setCategoria(categoria);
				contaCategoriaPK.setSubcategoria(Subcategoria.SUBCATEGORIA_ZERO);

				contaCategoria.setComp_id(contaCategoriaPK);
				contaCategoria.setValorAgua(calcularValoresAguaEsgotoHelper.getValorFaturadoAguaCategoria());
				contaCategoria.setConsumoAgua(calcularValoresAguaEsgotoHelper.getConsumoFaturadoAguaCategoria());
				contaCategoria.setValorEsgoto(calcularValoresAguaEsgotoHelper.getValorFaturadoEsgotoCategoria());
				contaCategoria.setConsumoEsgoto(calcularValoresAguaEsgotoHelper.getConsumoFaturadoEsgotoCategoria());
				contaCategoria.setValorTarifaMinimaAgua(calcularValoresAguaEsgotoHelper.getValorTarifaMinimaAguaCategoria());
				contaCategoria.setConsumoMinimoAgua(calcularValoresAguaEsgotoHelper.getConsumoMinimoAguaCategoria());
				contaCategoria.setValorTarifaMinimaEsgoto(calcularValoresAguaEsgotoHelper.getValorTarifaMinimaEsgotoCategoria());
				contaCategoria.setConsumoMinimoEsgoto(calcularValoresAguaEsgotoHelper.getConsumoMinimoEsgotoCategoria());
				contaCategoria.setQuantidadeEconomia(categoria.getQuantidadeEconomiasCategoria().shortValue());
				contaCategoria.setUltimaAlteracao(new Date());

				colecaoContaCategoriaIncluir.add(contaCategoria);

				Collection colecaoFaixaTarifaConsumo = calcularValoresAguaEsgotoHelper.getFaixaTarifaConsumo();

				if(colecaoFaixaTarifaConsumo != null && !colecaoFaixaTarifaConsumo.isEmpty()){
					Iterator iteratorColecaoFaixaTarifaConsumo = colecaoFaixaTarifaConsumo.iterator();

					CalcularValoresAguaEsgotoFaixaHelper calcularValoresAguaEsgotoFaixaHelper = null;

					while(iteratorColecaoFaixaTarifaConsumo.hasNext()){
						calcularValoresAguaEsgotoFaixaHelper = (CalcularValoresAguaEsgotoFaixaHelper) iteratorColecaoFaixaTarifaConsumo
										.next();

						contaCategoriaConsumoFaixa = new ContaCategoriaConsumoFaixa();
						contaCategoriaConsumoFaixa.setContaCategoria(contaCategoria);
						contaCategoriaConsumoFaixa.setValorAgua(calcularValoresAguaEsgotoFaixaHelper.getValorFaturadoAguaFaixa());
						contaCategoriaConsumoFaixa.setConsumoAgua(calcularValoresAguaEsgotoFaixaHelper.getConsumoFaturadoAguaFaixa());
						contaCategoriaConsumoFaixa.setValorEsgoto(calcularValoresAguaEsgotoFaixaHelper.getValorFaturadoEsgotoFaixa());
						contaCategoriaConsumoFaixa.setConsumoEsgoto(calcularValoresAguaEsgotoFaixaHelper.getConsumoFaturadoEsgotoFaixa());
						contaCategoriaConsumoFaixa.setConsumoFaixaInicio(calcularValoresAguaEsgotoFaixaHelper
										.getLimiteInicialConsumoFaixa());
						contaCategoriaConsumoFaixa.setConsumoFaixaFim(calcularValoresAguaEsgotoFaixaHelper.getLimiteFinalConsumoFaixa());
						contaCategoriaConsumoFaixa.setValorTarifaFaixa(calcularValoresAguaEsgotoFaixaHelper.getValorTarifaFaixa());
						contaCategoriaConsumoFaixa.setUltimaAlteracao(new Date());

						colecaoContaCategoriaConsumoFaixaInserir.add(contaCategoriaConsumoFaixa);

					}
				}
			}
		}

		retorno[0] = colecaoContaCategoriaIncluir;
		retorno[1] = colecaoContaCategoriaConsumoFaixaInserir;

		return retorno;
	}

	private Object[] gerarDadosInserirContaCategoriaHistoricoEInserirContaCategoriaHistoricoConsumoFaixa(Collection colecaoCategorias,
					Collection colecaoCalcularValoresAguaEsgotoHelper, ContaHistorico contaHistorico) throws ControladorException,
					ErroRepositorioException{

		Object[] retorno = new Object[2];
		Collection colecaoContaCategoriaHistoricoIncluir = null;
		ContaCategoriaHistorico contaCategoriaHistorico = null;
		ContaCategoriaHistoricoPK contaCategoriaHistoricoPK = null;
		Categoria categoria = null;

		ContaCategoriaConsumoFaixaHistorico contaCategoriaConsumoFaixaHistorico = null;
		Collection colecaoContaCategoriaHistoricoConsumoFaixaInserir = null;

		if((colecaoCategorias != null && !colecaoCategorias.isEmpty())
						&& (colecaoCalcularValoresAguaEsgotoHelper != null && !colecaoCalcularValoresAguaEsgotoHelper.isEmpty())){

			colecaoContaCategoriaHistoricoIncluir = new ArrayList();
			colecaoContaCategoriaHistoricoConsumoFaixaInserir = new ArrayList();
			CalcularValoresAguaEsgotoHelper calcularValoresAguaEsgotoHelper = null;

			Iterator iteratorColecaoCategoriaHistoricos = colecaoCategorias.iterator();

			Iterator iteratorColecaoCalcularValoresAguaEsgotoHelper = colecaoCalcularValoresAguaEsgotoHelper.iterator();

			while(iteratorColecaoCategoriaHistoricos.hasNext() && iteratorColecaoCalcularValoresAguaEsgotoHelper.hasNext()){
				categoria = (Categoria) iteratorColecaoCategoriaHistoricos.next();
				calcularValoresAguaEsgotoHelper = (CalcularValoresAguaEsgotoHelper) iteratorColecaoCalcularValoresAguaEsgotoHelper.next();

				contaCategoriaHistorico = new ContaCategoriaHistorico();
				contaCategoriaHistoricoPK = new ContaCategoriaHistoricoPK();

				contaCategoriaHistoricoPK.setContaHistorico(contaHistorico);
				contaCategoriaHistoricoPK.setCategoria(categoria);
				contaCategoriaHistoricoPK.setSubcategoria(Subcategoria.SUBCATEGORIA_ZERO);

				contaCategoriaHistorico.setComp_id(contaCategoriaHistoricoPK);
				contaCategoriaHistorico.setValorAgua(calcularValoresAguaEsgotoHelper.getValorFaturadoAguaCategoria());
				contaCategoriaHistorico.setConsumoAgua(calcularValoresAguaEsgotoHelper.getConsumoFaturadoAguaCategoria());
				contaCategoriaHistorico.setValorEsgoto(calcularValoresAguaEsgotoHelper.getValorFaturadoEsgotoCategoria());
				contaCategoriaHistorico.setConsumoEsgoto(calcularValoresAguaEsgotoHelper.getConsumoFaturadoEsgotoCategoria());
				contaCategoriaHistorico.setValorTarifaMinimaAgua(calcularValoresAguaEsgotoHelper.getValorTarifaMinimaAguaCategoria());
				contaCategoriaHistorico.setConsumoMinimoAgua(calcularValoresAguaEsgotoHelper.getConsumoMinimoAguaCategoria());
				contaCategoriaHistorico.setValorTarifaMinimaEsgoto(calcularValoresAguaEsgotoHelper.getValorTarifaMinimaEsgotoCategoria());
				contaCategoriaHistorico.setConsumoMinimoEsgoto(calcularValoresAguaEsgotoHelper.getConsumoMinimoEsgotoCategoria());
				contaCategoriaHistorico.setQuantidadeEconomia(categoria.getQuantidadeEconomiasCategoria().shortValue());
				contaCategoriaHistorico.setUltimaAlteracao(new Date());

				colecaoContaCategoriaHistoricoIncluir.add(contaCategoriaHistorico);

				Collection colecaoFaixaTarifaConsumo = calcularValoresAguaEsgotoHelper.getFaixaTarifaConsumo();

				if(colecaoFaixaTarifaConsumo != null && !colecaoFaixaTarifaConsumo.isEmpty()){
					Iterator iteratorColecaoFaixaTarifaConsumo = colecaoFaixaTarifaConsumo.iterator();

					CalcularValoresAguaEsgotoFaixaHelper calcularValoresAguaEsgotoFaixaHelper = null;

					Integer id = null;

					while(iteratorColecaoFaixaTarifaConsumo.hasNext()){

						id = repositorioArrecadacao.obterNextValContaCategoriaConsumoFaixaHistorico();

						calcularValoresAguaEsgotoFaixaHelper = (CalcularValoresAguaEsgotoFaixaHelper) iteratorColecaoFaixaTarifaConsumo
										.next();

						contaCategoriaConsumoFaixaHistorico = new ContaCategoriaConsumoFaixaHistorico();
						contaCategoriaConsumoFaixaHistorico.setId(id);
						contaCategoriaConsumoFaixaHistorico.setContaCategoriaHistorico(contaCategoriaHistorico);
						contaCategoriaConsumoFaixaHistorico.setValorAgua(calcularValoresAguaEsgotoFaixaHelper.getValorFaturadoAguaFaixa());
						contaCategoriaConsumoFaixaHistorico.setConsumoAgua(calcularValoresAguaEsgotoFaixaHelper
										.getConsumoFaturadoAguaFaixa());
						contaCategoriaConsumoFaixaHistorico.setValorEsgoto(calcularValoresAguaEsgotoFaixaHelper
										.getValorFaturadoEsgotoFaixa());
						contaCategoriaConsumoFaixaHistorico.setConsumoEsgoto(calcularValoresAguaEsgotoFaixaHelper
										.getConsumoFaturadoEsgotoFaixa());
						contaCategoriaConsumoFaixaHistorico.setConsumoFaixaInicio(calcularValoresAguaEsgotoFaixaHelper
										.getLimiteInicialConsumoFaixa());
						contaCategoriaConsumoFaixaHistorico.setConsumoFaixaFim(calcularValoresAguaEsgotoFaixaHelper
										.getLimiteFinalConsumoFaixa());
						contaCategoriaConsumoFaixaHistorico.setValorTarifaFaixa(calcularValoresAguaEsgotoFaixaHelper.getValorTarifaFaixa());
						contaCategoriaConsumoFaixaHistorico.setUltimaAlteracao(new Date());

						colecaoContaCategoriaHistoricoConsumoFaixaInserir.add(contaCategoriaConsumoFaixaHistorico);
					}
				}
			}
		}

		retorno[0] = colecaoContaCategoriaHistoricoIncluir;
		retorno[1] = colecaoContaCategoriaHistoricoConsumoFaixaInserir;

		return retorno;
	}

	public Object[] ajustarValoresPorCategoriaEFaixaHistorico(BigDecimal valorTotalAgua, BigDecimal valorFaturadoAgua,
					BigDecimal valorTotalEsgoto, BigDecimal valorFaturadoEsgoto,
					Collection colecaoContaCategoriaConsumoFaixaHistoricoAtualizar, Collection colecaoContaCategoriaHistoricoAtualizar,
					Imovel imovel, BigDecimal percentualEsgoto){

		Object[] retorno = new Object[2];

		BigDecimal totaValorAguaCCCF = BigDecimal.ZERO;
		BigDecimal totaValorEsgotoCCCF = BigDecimal.ZERO;

		BigDecimal valorAguaCCCF = BigDecimal.ZERO;
		BigDecimal valorEsgotoCCCF = BigDecimal.ZERO;

		BigDecimal diferencaValorFaturadoAgua = BigDecimal.ZERO;
		BigDecimal diferencaValorFaturadoEsgoto = BigDecimal.ZERO;

		Categoria categoria = null;
		Integer idCategoria = null;

		if(!Util.isVazioOrNulo(colecaoContaCategoriaConsumoFaixaHistoricoAtualizar)){

			Iterator it = colecaoContaCategoriaConsumoFaixaHistoricoAtualizar.iterator();
			while(it.hasNext()){

				ContaCategoriaConsumoFaixaHistorico contaCategoriaConsumoFaixaHistorico = (ContaCategoriaConsumoFaixaHistorico) it.next();

				// Caso seja a primeira faixa
				if(contaCategoriaConsumoFaixaHistorico.getConsumoFaixaInicio().intValue() == 0){

					// Água
					valorAguaCCCF = new BigDecimal(contaCategoriaConsumoFaixaHistorico.getConsumoFaixaFim())
									.multiply(contaCategoriaConsumoFaixaHistorico.getValorTarifaFaixa());

					// Esgoto
					valorEsgotoCCCF = (new BigDecimal(contaCategoriaConsumoFaixaHistorico.getConsumoFaixaFim())
									.multiply(contaCategoriaConsumoFaixaHistorico.getValorTarifaFaixa())).multiply(percentualEsgoto);
					valorEsgotoCCCF = valorEsgotoCCCF.divide(new BigDecimal("100"), BigDecimal.ROUND_DOWN);
				}else{

					// Água
					if(contaCategoriaConsumoFaixaHistorico.getValorAgua() != null
									&& contaCategoriaConsumoFaixaHistorico.getValorAgua().compareTo(BigDecimal.ZERO) > 0){

						valorAguaCCCF = contaCategoriaConsumoFaixaHistorico.getValorAgua();

						if(valorTotalAgua != null && valorTotalAgua.compareTo(BigDecimal.ZERO) > 0){

							valorAguaCCCF = valorAguaCCCF.divide(valorTotalAgua, BigDecimal.ROUND_DOWN);
						}
					}

					if(valorFaturadoAgua != null){

						valorAguaCCCF = valorAguaCCCF.multiply(valorFaturadoAgua);
					}

					// Esgoto
					if(contaCategoriaConsumoFaixaHistorico.getValorEsgoto() != null
									&& contaCategoriaConsumoFaixaHistorico.getValorEsgoto().compareTo(BigDecimal.ZERO) > 0){

						valorEsgotoCCCF = contaCategoriaConsumoFaixaHistorico.getValorEsgoto();
					}

					if(valorTotalEsgoto != null && valorTotalEsgoto.compareTo(BigDecimal.ZERO) > 0){

						valorEsgotoCCCF = valorEsgotoCCCF.divide(valorTotalEsgoto, BigDecimal.ROUND_DOWN);
					}

					if(valorFaturadoEsgoto != null){

						valorEsgotoCCCF = valorEsgotoCCCF.multiply(valorFaturadoEsgoto);
					}
				}

				contaCategoriaConsumoFaixaHistorico.setValorAgua(valorAguaCCCF);
				contaCategoriaConsumoFaixaHistorico.setValorEsgoto(valorEsgotoCCCF);

				// Total
				totaValorAguaCCCF = totaValorAguaCCCF.add(valorAguaCCCF);
				totaValorEsgotoCCCF = totaValorEsgotoCCCF.add(valorEsgotoCCCF);

				valorAguaCCCF = BigDecimal.ZERO;
				valorEsgotoCCCF = BigDecimal.ZERO;
			}

			if(totaValorAguaCCCF.compareTo(valorFaturadoAgua) != 0){

				diferencaValorFaturadoAgua = valorFaturadoAgua.subtract(totaValorAguaCCCF);
			}else{

				diferencaValorFaturadoAgua = BigDecimal.ZERO;
			}

			if(totaValorEsgotoCCCF.compareTo(valorFaturadoEsgoto) != 0){

				diferencaValorFaturadoEsgoto = valorFaturadoEsgoto.subtract(totaValorEsgotoCCCF);
			}else{

				diferencaValorFaturadoEsgoto = BigDecimal.ZERO;
			}

			// Caso o somatório dos valores água e de esgoto por categoria/faixa esteja
			// diferente do valor total de água e de esgoto
			if(diferencaValorFaturadoAgua.compareTo(BigDecimal.ZERO) != 0 || diferencaValorFaturadoEsgoto.compareTo(BigDecimal.ZERO) != 0){

				ContaCategoriaConsumoFaixaHistorico contaCategoriaConsumoFaixaHistoricoAnterior = null;
				Iterator itColecaoContaCategoriaConsumoFaixaInserir = colecaoContaCategoriaConsumoFaixaHistoricoAtualizar.iterator();

				while(itColecaoContaCategoriaConsumoFaixaInserir.hasNext()){

					ContaCategoriaConsumoFaixaHistorico contaCategoriaConsumoFaixaHistorico = (ContaCategoriaConsumoFaixaHistorico) itColecaoContaCategoriaConsumoFaixaInserir
									.next();

					categoria = contaCategoriaConsumoFaixaHistorico.getContaCategoriaHistorico().getComp_id().getCategoria();
					idCategoria = categoria.getId();

					if(contaCategoriaConsumoFaixaHistoricoAnterior != null
									&& !contaCategoriaConsumoFaixaHistoricoAnterior.getContaCategoriaHistorico().getComp_id()
													.getCategoria().getId().equals(idCategoria)){

						// Faz o ajuste ao final da primeira categoria quando é categoria mista
						contaCategoriaConsumoFaixaHistoricoAnterior.setValorAgua(contaCategoriaConsumoFaixaHistoricoAnterior.getValorAgua()
										.add(diferencaValorFaturadoAgua));
						contaCategoriaConsumoFaixaHistoricoAnterior.setValorEsgoto(contaCategoriaConsumoFaixaHistoricoAnterior
										.getValorEsgoto().add(diferencaValorFaturadoEsgoto));
						break;
					}else if(itColecaoContaCategoriaConsumoFaixaInserir.hasNext() == false){

						// Faz o ajuste ao final da categoria quando tem apenas uma categoria
						contaCategoriaConsumoFaixaHistorico.setValorAgua(contaCategoriaConsumoFaixaHistorico.getValorAgua().add(
										diferencaValorFaturadoAgua));
						contaCategoriaConsumoFaixaHistorico.setValorEsgoto(contaCategoriaConsumoFaixaHistorico.getValorEsgoto().add(
										diferencaValorFaturadoEsgoto));
					}

					contaCategoriaConsumoFaixaHistoricoAnterior = contaCategoriaConsumoFaixaHistorico;
				}
			}

			// Faz o ajuste de Conta Categoria
			if(!Util.isVazioOrNulo(colecaoContaCategoriaHistoricoAtualizar)){

				BigDecimal valorAguaCC = BigDecimal.ZERO;
				BigDecimal valorEsgotoCC = BigDecimal.ZERO;

				Short quantidadeEconomiasImovel = imovel.getQuantidadeEconomias();
				BigDecimal quantidadeEconomiasImovelBD = null;

				if(quantidadeEconomiasImovel != null){
					quantidadeEconomiasImovelBD = new BigDecimal(quantidadeEconomiasImovel);
				}

				Short quantidadeEconomiaCategoria = null;
				BigDecimal quantidadeEconomiaCategoriaBD = null;

				// Caso o somatório dos valores água e de esgoto por categoria esteja
				// diferente do valor total de água ou de esgoto adicionar a diferença
				// na primeira categoria
				BigDecimal valorAguaResiduo = BigDecimal.ZERO;
				if(valorFaturadoAgua != null){

					valorAguaResiduo = valorFaturadoAgua.subtract((valorFaturadoAgua.divide(quantidadeEconomiasImovelBD,
									BigDecimal.ROUND_FLOOR)).multiply(quantidadeEconomiasImovelBD));
				}

				BigDecimal valorEsgotoResiduo = BigDecimal.ZERO;
				if(valorFaturadoEsgoto != null){

					valorEsgotoResiduo = valorFaturadoEsgoto.subtract((valorFaturadoEsgoto.divide(quantidadeEconomiasImovelBD,
									BigDecimal.ROUND_FLOOR)).multiply(quantidadeEconomiasImovelBD));
				}

				for(ContaCategoriaHistorico contaCategoriaHistorico : (Collection<ContaCategoriaHistorico>) colecaoContaCategoriaHistoricoAtualizar){

					quantidadeEconomiaCategoria = contaCategoriaHistorico.getQuantidadeEconomia();

					valorAguaCC = BigDecimal.ZERO;
					valorEsgotoCC = BigDecimal.ZERO;

					if(quantidadeEconomiasImovelBD != null && quantidadeEconomiasImovelBD.compareTo(BigDecimal.ZERO) == 1
									&& quantidadeEconomiaCategoria != null && !quantidadeEconomiaCategoria.equals(new Short("0"))){

						quantidadeEconomiaCategoriaBD = new BigDecimal(quantidadeEconomiaCategoria);

						if(valorFaturadoAgua != null){

							valorAguaCC = valorFaturadoAgua.divide(quantidadeEconomiasImovelBD, BigDecimal.ROUND_DOWN);
							valorAguaCC = (valorAguaCC.multiply(quantidadeEconomiaCategoriaBD)).add(valorAguaResiduo);
							valorAguaResiduo = BigDecimal.ZERO;
						}

						if(valorFaturadoEsgoto != null){

							valorEsgotoCC = valorFaturadoEsgoto.divide(quantidadeEconomiasImovelBD, BigDecimal.ROUND_DOWN);
							valorEsgotoCC = (valorEsgotoCC.multiply(quantidadeEconomiaCategoriaBD)).add(valorEsgotoResiduo);
							valorEsgotoResiduo = BigDecimal.ZERO;
						}
					}

					contaCategoriaHistorico.setValorAgua(valorAguaCC);
					contaCategoriaHistorico.setValorEsgoto(valorEsgotoCC);

				}
			}

		}else if(!Util.isVazioOrNulo(colecaoContaCategoriaHistoricoAtualizar)){

			BigDecimal valorAguaCC = BigDecimal.ZERO;
			BigDecimal valorEsgotoCC = BigDecimal.ZERO;

			Short quantidadeEconomiasImovel = imovel.getQuantidadeEconomias();
			BigDecimal quantidadeEconomiasImovelBD = null;

			if(quantidadeEconomiasImovel != null){
				quantidadeEconomiasImovelBD = new BigDecimal(quantidadeEconomiasImovel);
			}

			Short quantidadeEconomiaCategoria = null;
			BigDecimal quantidadeEconomiaCategoriaBD = null;

			// Caso o somatório dos valores água e de esgoto por categoria esteja
			// diferente do valor total de água ou de esgoto adicionar a diferença
			// na primeira categoria
			BigDecimal valorAguaResiduo = BigDecimal.ZERO;
			if(valorFaturadoAgua != null){

				valorAguaResiduo = valorFaturadoAgua.subtract((valorFaturadoAgua
								.divide(quantidadeEconomiasImovelBD, BigDecimal.ROUND_FLOOR)).multiply(quantidadeEconomiasImovelBD));
			}

			BigDecimal valorEsgotoResiduo = BigDecimal.ZERO;
			if(valorFaturadoEsgoto != null){

				valorEsgotoResiduo = valorFaturadoEsgoto.subtract((valorFaturadoEsgoto.divide(quantidadeEconomiasImovelBD,
								BigDecimal.ROUND_FLOOR)).multiply(quantidadeEconomiasImovelBD));
			}

			for(ContaCategoriaHistorico contaCategoriaHistorico : (Collection<ContaCategoriaHistorico>) colecaoContaCategoriaHistoricoAtualizar){

				quantidadeEconomiaCategoria = contaCategoriaHistorico.getQuantidadeEconomia();

				valorAguaCC = BigDecimal.ZERO;
				valorEsgotoCC = BigDecimal.ZERO;

				if(quantidadeEconomiasImovelBD != null && quantidadeEconomiasImovelBD.compareTo(BigDecimal.ZERO) == 1
								&& quantidadeEconomiaCategoria != null && !quantidadeEconomiaCategoria.equals(new Short("0"))){

					quantidadeEconomiaCategoriaBD = new BigDecimal(quantidadeEconomiaCategoria);

					if(valorFaturadoAgua != null){

						valorAguaCC = valorFaturadoAgua.divide(quantidadeEconomiasImovelBD, BigDecimal.ROUND_DOWN);
						valorAguaCC = (valorAguaCC.multiply(quantidadeEconomiaCategoriaBD)).add(valorAguaResiduo);
						valorAguaResiduo = BigDecimal.ZERO;
					}

					if(valorFaturadoEsgoto != null){

						valorEsgotoCC = valorFaturadoEsgoto.divide(quantidadeEconomiasImovelBD, BigDecimal.ROUND_DOWN);
						valorEsgotoCC = (valorEsgotoCC.multiply(quantidadeEconomiaCategoriaBD)).add(valorEsgotoResiduo);
						valorEsgotoResiduo = BigDecimal.ZERO;
					}
				}

				contaCategoriaHistorico.setValorAgua(valorAguaCC);
				contaCategoriaHistorico.setValorEsgoto(valorEsgotoCC);

			}
		}

		retorno[0] = colecaoContaCategoriaHistoricoAtualizar;
		retorno[1] = colecaoContaCategoriaConsumoFaixaHistoricoAtualizar;

		return retorno;

	}
	
	/**
	 * 
	 */

	private Boolean exibirReferenciaNaFatura(Integer idDebitoTipo, String[] valoresParmTipoDebRefFatura){

		Boolean exibir = Boolean.FALSE;

		for(String valorParametro : valoresParmTipoDebRefFatura){

			if(StringUtils.isNumeric(valorParametro)){

				if(idDebitoTipo.equals(Integer.valueOf(valorParametro))){

					exibir = Boolean.TRUE;
					break;

				}

			}

		}

		return exibir;

	}


	private Object[] verificarExistenciaDescontoDebitoCobradoParcelamento(Integer idDebitoCobrado, Integer idConta, Integer idDebitoTipo,
					Short numeroPrestacaoDebito, Short numeroPrestacao, BigDecimal vlRubrica,
					MovimentoRoteiroEmpresa movimentoRoteiroEmpresa, Map<Integer, BigDecimal> mapCreditoResidual, Imovel imovel)
					throws ControladorException{

		// Map utilizado para acumular o crédito residual do desconto de acréscimos por
		// impontualidade
		if(mapCreditoResidual == null){
			mapCreditoResidual = new HashMap<Integer, BigDecimal>();
		}

		Object[] retorno = new Object[2];

		BigDecimal valorCreditoCalculado = BigDecimal.ZERO;

		Integer idTipoDebitoParAcresPontualidade = Integer
						.valueOf((String) ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_ACRESCIMO_IMPONTUALIDADE.executar());

		Integer idTipoDebitoParAcresPontualidadeCobAdm = Integer
						.valueOf((String) ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_ACRESCIMO_IMPONTUALIDADE_COBRANCA_ADMINISTRATIVA
										.executar());

		if(idTipoDebitoParAcresPontualidade != null
						&& idDebitoTipo != null
						&& idTipoDebitoParAcresPontualidadeCobAdm != null
						&& (idTipoDebitoParAcresPontualidade.intValue() == idDebitoTipo.intValue() || idTipoDebitoParAcresPontualidadeCobAdm
										.intValue() == idDebitoTipo.intValue())){

			FiltroDebitoCobrado filtroDebitoCobrado = new FiltroDebitoCobrado();
			filtroDebitoCobrado.adicionarParametro(new ParametroSimples(FiltroDebitoCobrado.CONTA_ID, idConta));
			filtroDebitoCobrado.adicionarParametro(new ParametroSimples(FiltroDebitoCobrado.DEBITO_TIPO_ID, idDebitoTipo));
			filtroDebitoCobrado
							.adicionarParametro(new ParametroSimples(FiltroDebitoCobrado.NUMERO_PRESTACAO_DEBITO, numeroPrestacaoDebito));
			filtroDebitoCobrado.adicionarParametro(new ParametroSimples(FiltroDebitoCobrado.NUMERO_PRESTACAO, numeroPrestacao));

			Collection<DebitoCobrado> colecaoDebitoCobrado = this.getControladorUtil().pesquisar(filtroDebitoCobrado,
							DebitoCobrado.class.getName());

			if(!Util.isVazioOrNulo(colecaoDebitoCobrado)){
				FiltroCreditoARealizar filtroCreditoARealizar = null;

				Collection<CreditoARealizar> colecaoCreditoARealizar = null;

				Parcelamento parcelamento = null;
				Integer idParcelamento = null;

				BigDecimal valorCreditoResidual = BigDecimal.ZERO;

				for(DebitoCobrado debitoCobrado : colecaoDebitoCobrado){
					parcelamento = debitoCobrado.getParcelamento();

					if(parcelamento != null){
						idParcelamento = parcelamento.getId();

						if(mapCreditoResidual.containsKey(idParcelamento)){
							// Como o crédito associado ao parcelamento já foi calculado, o sistema
							// não deve fazer uma nova consulta

							valorCreditoResidual = mapCreditoResidual.get(idParcelamento);

							vlRubrica = vlRubrica.subtract(valorCreditoResidual);

							mapCreditoResidual.put(idParcelamento, BigDecimal.ZERO);
						}else{
							filtroCreditoARealizar = new FiltroCreditoARealizar();
							filtroCreditoARealizar.adicionarParametro(new ParametroSimples(FiltroCreditoARealizar.PARCELAMENTO_ID,
											idParcelamento));
							filtroCreditoARealizar.adicionarParametro(new ParametroSimples(FiltroCreditoARealizar.ID_CREDITO_TIPO,
											CreditoTipo.DESCONTO_ACRESCIMOS_IMPONTUALIDADE));

							colecaoCreditoARealizar = this.getControladorUtil().pesquisar(filtroCreditoARealizar,
											CreditoARealizar.class.getName());

							if(!Util.isVazioOrNulo(colecaoCreditoARealizar)){
								BigDecimal vlCredCorrespParcelaMes = BigDecimal.ZERO;

								BigDecimal valorCredito = null;
								Short numeroPrestacaoCredito = null;

								Short numeroParcelaBonus = null;

								Short numeroPrestacaoRealizada = null;

								Short numeroPrestacaoRealizadaAux = null;

								BigDecimal calculo1 = null;
								BigDecimal calculo2 = null;
								BigDecimal calculo3 = null;

								BigDecimal valorResidualMesAnterior = null;
								int contador = 1;

								for(CreditoARealizar creditoARealizar : colecaoCreditoARealizar){
									valorCredito = creditoARealizar.getValorCredito();
									numeroPrestacaoCredito = creditoARealizar.getNumeroPrestacaoCredito();

									if(valorCredito != null && numeroPrestacaoCredito != null){
										vlCredCorrespParcelaMes = valorCredito.divide(new BigDecimal(numeroPrestacaoCredito), 2,
														BigDecimal.ROUND_DOWN);
									}

									numeroParcelaBonus = creditoARealizar.getNumeroParcelaBonus();

									if(numeroParcelaBonus == null){
										numeroParcelaBonus = 0;
									}

									numeroPrestacaoRealizada = creditoARealizar.getNumeroPrestacaoRealizada();

									if(numeroPrestacaoCredito != null){
										numeroPrestacaoRealizadaAux = Short.parseShort(String
														.valueOf((numeroPrestacaoCredito - numeroParcelaBonus) - 1));
									}else{
										numeroPrestacaoRealizadaAux = null;
									}

									// Caso seja a última prestação
									if(numeroPrestacaoRealizada != null && numeroPrestacaoRealizadaAux != null
													&& numeroPrestacaoRealizada.shortValue() == numeroPrestacaoRealizadaAux.shortValue()){

										if(valorCredito != null){
											calculo1 = BigDecimal.valueOf(numeroPrestacaoCredito - numeroParcelaBonus);
											calculo2 = vlCredCorrespParcelaMes.multiply(calculo1);
											calculo3 = valorCredito.subtract(calculo2);

											vlCredCorrespParcelaMes = vlCredCorrespParcelaMes.add(calculo3);
										}
									}

									valorResidualMesAnterior = creditoARealizar.getValorResidualMesAnterior();

									if(valorResidualMesAnterior == null){
										valorResidualMesAnterior = BigDecimal.ZERO;
									}

									valorCreditoCalculado = vlCredCorrespParcelaMes.add(valorResidualMesAnterior);
									vlRubrica = vlRubrica.subtract(valorCreditoCalculado);

									if(vlRubrica.compareTo(BigDecimal.ZERO) < 0){
										valorCreditoResidual = vlRubrica.abs();
										vlRubrica = BigDecimal.ZERO;
									}else{
										valorCreditoResidual = BigDecimal.ZERO;
									}

									mapCreditoResidual.put(idParcelamento, valorCreditoResidual);


								}

								CobrancaForma cobrancaForma = new CobrancaForma();
								cobrancaForma.setId(CobrancaForma.COBRANCA_EM_CONTA);

								FiltroDebitoTipo filtroDebitoTipo = new FiltroDebitoTipo();
								filtroDebitoTipo.adicionarParametro(new ParametroSimples(FiltroDebitoTipo.ID, 271));
								filtroDebitoTipo.adicionarCaminhoParaCarregamentoEntidade(FiltroDebitoTipo.FINANCIAMENTO_TIPO);
								filtroDebitoTipo.adicionarCaminhoParaCarregamentoEntidade(FiltroDebitoTipo.LANCAMENTO_ITEM_CONTABIL);
								filtroDebitoTipo.adicionarParametro(new ParametroSimples(FiltroDebitoTipo.INDICADOR_USO,
												ConstantesSistema.SIM));
								Collection<DebitoTipo> colecaoDebitoTipo = Fachada.getInstancia().pesquisar(filtroDebitoTipo,
												DebitoTipo.class.getName());
								DebitoTipo debitoTipo = (DebitoTipo) Util.retonarObjetoDeColecao(colecaoDebitoTipo);

								// Gerar debito a cobrar com o valor do credito calculado
								DebitoACobrar debitoACobrar = new DebitoACobrar();
								debitoACobrar.setDebitoTipo(debitoTipo);
								debitoACobrar.setValorDebito(valorCreditoCalculado);
								debitoACobrar.setImovel(imovel);
								debitoACobrar.setGeracaoDebito(new Date());
								debitoACobrar.setLocalidade(imovel.getLocalidade());
								debitoACobrar.setQuadra(imovel.getQuadra());
								debitoACobrar.setAnoMesReferenciaDebito(201402);
								debitoACobrar.setLancamentoItemContabil(debitoTipo.getLancamentoItemContabil());
								debitoACobrar.setFinanciamentoTipo(debitoTipo.getFinanciamentoTipo());
								debitoACobrar.setCobrancaForma(cobrancaForma);
								debitoACobrar.setUltimaAlteracao(new Date());
								debitoACobrar.setAnoMesCobrancaDebito(201402);
								debitoACobrar.setUltimaAlteracao(new Date());

								this.getControladorFaturamento().inserirDebitoACobrarSemRegistrarLancamentoContabil(1, debitoACobrar, null,
												imovel, null, null, Usuario.USUARIO_BATCH, false, null, null, null);

								System.out.println("   idDebitoTipo= " + idDebitoTipo + " imovel =" + imovel.getId() + "diferença = "
												+ valorCreditoCalculado);

							}
						}
					}
				}
			}
		}

		retorno[0] = vlRubrica;
		retorno[1] = valorCreditoCalculado;

		return retorno;
	}

	public HashSet obterContas(Integer idImovel, Integer anoMesReferencia, Integer idDebitoCreditoSituacao) throws ControladorException{

		HashSet hashContas = null;
		HashSet hashDebitosCobrados = null;

		FiltroConta filtroConta = new FiltroConta();
		filtroConta.adicionarParametro(new ParametroSimples(FiltroConta.REFERENCIA, anoMesReferencia));
		filtroConta.adicionarParametro(new ParametroSimples(FiltroConta.IMOVEL_ID, idImovel));
		filtroConta.adicionarParametro(new ParametroSimples(FiltroConta.DEBITO_CREDITO_SITUACAO_ATUAL_ID, idDebitoCreditoSituacao));

		Collection contas = this.getControladorUtil().pesquisar(filtroConta, Conta.class.getName());

		Iterator it = contas.iterator();
		while(it.hasNext()){
			hashContas = new HashSet();
			Conta conta = (Conta) it.next();
			FiltroDebitoCobrado filtroDebitoCobrado = new FiltroDebitoCobrado();
			filtroDebitoCobrado.adicionarParametro(new ParametroSimples(FiltroDebitoCobrado.CONTA_ID, conta.getId()));
			filtroDebitoCobrado.adicionarCaminhoParaCarregamentoEntidade(FiltroDebitoCobrado.DEBITO_TIPO);
			Collection debitosCobrados = this.getControladorUtil().pesquisar(filtroDebitoCobrado, DebitoCobrado.class.getName());

			hashDebitosCobrados = new HashSet();
			Iterator itDb = debitosCobrados.iterator();
			while(itDb.hasNext()){
				DebitoCobrado debitoCobrado = (DebitoCobrado) itDb.next();
				hashDebitosCobrados.add(debitoCobrado);
			}

			conta.setDebitoCobrados(hashDebitosCobrados);
			hashContas.add(conta);

		}

		return hashContas;
	}



	public ExecutorParametro getExecutorParametro(){

		return ExecutorParametrosFaturamento.getInstancia();
	}


}
