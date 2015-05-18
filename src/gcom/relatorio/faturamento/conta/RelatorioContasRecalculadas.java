
package gcom.relatorio.faturamento.conta;

import gcom.batch.Relatorio;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.faturamento.bean.CalcularValoresAguaEsgotoHelper;
import gcom.faturamento.bean.FiltroContaSimularCalculoHelper;
import gcom.faturamento.consumotarifa.ConsumoTarifa;
import gcom.faturamento.consumotarifa.ConsumoTarifaVigencia;
import gcom.faturamento.consumotarifa.FiltroConsumoTarifaVigencia;
import gcom.faturamento.conta.ContaCategoria;
import gcom.faturamento.conta.ContaCategoriaHistorico;
import gcom.faturamento.conta.FiltroContaCategoria;
import gcom.faturamento.conta.FiltroContaCategoriaHistorico;
import gcom.gui.faturamento.bean.ContaSimularCalculoDadosReaisHelper;
import gcom.micromedicao.consumo.ConsumoHistorico;
import gcom.micromedicao.consumo.FiltroConsumoHistorico;
import gcom.micromedicao.medicao.FiltroMedicaoHistorico;
import gcom.micromedicao.medicao.MedicaoHistorico;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.agendadortarefas.AgendadorTarefas;
import gcom.util.filtro.ParametroSimples;

import java.math.BigDecimal;
import java.util.*;

import org.apache.log4j.Logger;

/**
 * [UC3156] Simular Cálculo da Conta Dados Reais
 * 
 * @author Anderson Italo
 * @date 26/09/2014
 */
public class RelatorioContasRecalculadas
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(RelatorioContasRecalculadas.class);

	public RelatorioContasRecalculadas(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_CONTAS_RECALCULADAS);
	}

	@Deprecated
	public RelatorioContasRecalculadas() {

		super(null, "");
	}

	public Object executar() throws TarefaException{

		byte[] retorno = null;

		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();

		int tipoFormatoRelatorio = TarefaRelatorio.TIPO_PDF;
		FiltroContaSimularCalculoHelper filtroContaSimularCalculoHelper = (FiltroContaSimularCalculoHelper) getParametro("filtroContaSimularCalculoHelper");
		Fachada fachada = Fachada.getInstancia();

		ConsumoTarifa consumoTarifaRecalculo = (ConsumoTarifa) fachada.pesquisar(
						filtroContaSimularCalculoHelper.getIdConsumoTarifaRecalcular(), ConsumoTarifa.class);

		FiltroConsumoTarifaVigencia filtroConsumoTarifaVigencia = new FiltroConsumoTarifaVigencia();
		filtroConsumoTarifaVigencia.adicionarParametro(new ParametroSimples(FiltroConsumoTarifaVigencia.ID, filtroContaSimularCalculoHelper
						.getIdConsumoTarifaVigenciaRecalcular()));
		filtroConsumoTarifaVigencia.adicionarCaminhoParaCarregamentoEntidade(FiltroConsumoTarifaVigencia.CONSUMO_TARIFA);

		ConsumoTarifaVigencia consumoTarifaVigencia = (ConsumoTarifaVigencia) Util.retonarObjetoDeColecao(fachada.pesquisar(
						filtroConsumoTarifaVigencia, ConsumoTarifaVigencia.class.getName()));

		Collection<Object[]> colecaoDadosSimularCalculo = fachada.pesquisarContasSimularCalculoDadosReais(filtroContaSimularCalculoHelper,
						null);

		List<RelatorioContasRecalculadasBean> colecaoBeansRelatorio = new ArrayList<RelatorioContasRecalculadasBean>();
		List<ContaSimularCalculoDadosReaisHelper> colecaoContasSimularCalculoHelper = new ArrayList<ContaSimularCalculoDadosReaisHelper>();
		ContaSimularCalculoDadosReaisHelper contaSimularCalculoHelper = null;

		for(Object[] dadosContaSimularHelper : colecaoDadosSimularCalculo){


			contaSimularCalculoHelper = new ContaSimularCalculoDadosReaisHelper();

			// Id
			contaSimularCalculoHelper.setIdConta(Util.obterInteger(dadosContaSimularHelper[0].toString()));

			// Referência da Conta
			contaSimularCalculoHelper.setAnoMesReferenciaConta(Util.obterInteger(dadosContaSimularHelper[1].toString()));

			// Situação da Conta
			if(dadosContaSimularHelper[18].toString().equals(ConstantesSistema.SIM.toString())){

				contaSimularCalculoHelper.setIndicadorHistorico(ConstantesSistema.SIM);
				contaSimularCalculoHelper.setSituacaoConta("QUITADA");
			}else{

				contaSimularCalculoHelper.setSituacaoConta("EM DÉBITO");
				contaSimularCalculoHelper.setIndicadorHistorico(ConstantesSistema.NAO);
			}

			// Valores Originais
			// Água
			if(dadosContaSimularHelper[5] != null){

				contaSimularCalculoHelper.setValorOriginalAgua(new BigDecimal(dadosContaSimularHelper[5].toString()));
				contaSimularCalculoHelper.setIndicadorFaturamentoAgua(ConstantesSistema.SIM);
			}else{

				contaSimularCalculoHelper.setIndicadorFaturamentoAgua(ConstantesSistema.NAO);
				contaSimularCalculoHelper.setValorOriginalAgua(BigDecimal.ZERO);
			}

			// Esgoto
			if(dadosContaSimularHelper[6] != null){

				contaSimularCalculoHelper.setValorOriginalEsgoto(new BigDecimal(dadosContaSimularHelper[6].toString()));
				contaSimularCalculoHelper.setIndicadorFaturamentoEsgoto(ConstantesSistema.SIM);
			}else{

				contaSimularCalculoHelper.setIndicadorFaturamentoEsgoto(ConstantesSistema.NAO);
				contaSimularCalculoHelper.setValorOriginalEsgoto(BigDecimal.ZERO);
			}

			// Total
			contaSimularCalculoHelper.setValorOriginalTotal(new BigDecimal(dadosContaSimularHelper[2].toString()));

			// Débitos
			contaSimularCalculoHelper.setValorDebitos(new BigDecimal(dadosContaSimularHelper[7].toString()));

			// Créditos
			contaSimularCalculoHelper.setValorCreditos(new BigDecimal(dadosContaSimularHelper[8].toString()));

			// Impostos
			contaSimularCalculoHelper.setValorImpostos(new BigDecimal(dadosContaSimularHelper[9].toString()));

			// Id Ligação Agua Situação
			if(dadosContaSimularHelper[11] != null){

				contaSimularCalculoHelper.setIdLigacaoAguaSituacao(Util.obterInteger(dadosContaSimularHelper[11].toString()));
			}

			// Id Ligação Esgoto Situação
			if(dadosContaSimularHelper[12] != null){

				contaSimularCalculoHelper.setIdLigacaoEsgotoSituacao(Util.obterInteger(dadosContaSimularHelper[12].toString()));
			}

			// Consumo Faturado de Água
			if(dadosContaSimularHelper[14] != null){

				contaSimularCalculoHelper.setConsumoFaturadoAgua(Util.obterInteger(dadosContaSimularHelper[14].toString()));
			}else{

				contaSimularCalculoHelper.setConsumoFaturadoAgua(0);
			}

			// Consumo Faturado de Esgoto
			if(dadosContaSimularHelper[15] != null){

				contaSimularCalculoHelper.setConsumoFaturadoEsgoto(Util.obterInteger(dadosContaSimularHelper[15].toString()));
			}else{

				contaSimularCalculoHelper.setConsumoFaturadoEsgoto(0);
			}

			// Percentual de Esgoto
			if(dadosContaSimularHelper[16] != null){

				contaSimularCalculoHelper.setPercentualEsgoto(new BigDecimal(dadosContaSimularHelper[16].toString()));
			}else{

				contaSimularCalculoHelper.setPercentualEsgoto(BigDecimal.ZERO);
			}

			// Imóvel
			contaSimularCalculoHelper.setIdImovel(Util.obterInteger(dadosContaSimularHelper[13].toString()));

			// Tarifa do Imóvel
			if(dadosContaSimularHelper[17] != null){

				contaSimularCalculoHelper.setIdConsumoTarifaImovel(Util.obterInteger(dadosContaSimularHelper[17].toString()));
			}

			colecaoContasSimularCalculoHelper.add(contaSimularCalculoHelper);
		}

		Collection<CalcularValoresAguaEsgotoHelper> colecaoCalcularValoresAguaEsgotoHelper = null;
		MedicaoHistorico medicaoHistorico = null;
		Date dataLeituraAnterior = null;
		Date dataLeituraAtual = null;
		Collection<MedicaoHistorico> colecaoMedicaoHistorico = null;
		FiltroMedicaoHistorico filtroMedicaoHistorico = new FiltroMedicaoHistorico();
		FiltroContaCategoria filtroContaCategoria = new FiltroContaCategoria();
		FiltroContaCategoriaHistorico filtroContaCategoriaHistorico = new FiltroContaCategoriaHistorico();
		Collection<Categoria> colecaoCategoria = new ArrayList<Categoria>();
		Categoria categoria = null;
		FiltroConsumoHistorico filtroConsumoHistorico = new FiltroConsumoHistorico();
		Imovel imovel = null;

		for(ContaSimularCalculoDadosReaisHelper contaHelper : colecaoContasSimularCalculoHelper){

			// Imóvel
			imovel = new Imovel(contaHelper.getIdImovel());
			imovel.setConsumoTarifa(new ConsumoTarifa(contaHelper.getIdConsumoTarifaImovel()));

			// Obtendo as Categorias da Conta
			colecaoCategoria.clear();
			if(contaHelper.getIndicadorHistorico().equals(ConstantesSistema.NAO)){

				filtroContaCategoria.limparListaParametros();
				filtroContaCategoria.adicionarParametro(new ParametroSimples(FiltroContaCategoria.CONTA_ID, contaHelper.getIdConta()));
				filtroContaCategoria.adicionarCaminhoParaCarregamentoEntidade(FiltroContaCategoria.CATEGORIA);
				Collection<ContaCategoria> colecaoContaCategoria = fachada.pesquisar(filtroContaCategoria, ContaCategoria.class.getName());

				categoria = null;
				for(ContaCategoria contaCategoria : colecaoContaCategoria){

					categoria = contaCategoria.getComp_id().getCategoria();
					categoria.setQuantidadeEconomiasCategoria(contaCategoria.getQuantidadeEconomia().intValue());
					colecaoCategoria.add(categoria);
				}
			}else{

				filtroContaCategoriaHistorico.limparListaParametros();
				filtroContaCategoriaHistorico.adicionarParametro(new ParametroSimples(FiltroContaCategoriaHistorico.CONTA_HISTORICO_ID,
								contaHelper.getIdConta()));
				filtroContaCategoriaHistorico.adicionarCaminhoParaCarregamentoEntidade(FiltroContaCategoriaHistorico.CATEGORIA);
				Collection<ContaCategoriaHistorico> colecaoContaCategoriaHistorico = fachada.pesquisar(filtroContaCategoriaHistorico,
								ContaCategoriaHistorico.class.getName());

				categoria = null;
				for(ContaCategoriaHistorico contaCategoriaHistorico : colecaoContaCategoriaHistorico){

					categoria = contaCategoriaHistorico.getComp_id().getCategoria();
					categoria.setQuantidadeEconomiasCategoria(Integer.valueOf(String.valueOf(contaCategoriaHistorico
									.getQuantidadeEconomia())));
					colecaoCategoria.add(categoria);
				}
			}

			// Obtendo as datas de leitura atual e anterior da referencia da conta
			filtroMedicaoHistorico.limparListaParametros();
			filtroMedicaoHistorico.adicionarParametro(new ParametroSimples(FiltroMedicaoHistorico.LIGACAO_AGUA_ID, imovel.getId()));
			filtroMedicaoHistorico.adicionarParametro(new ParametroSimples(FiltroMedicaoHistorico.ANO_MES_REFERENCIA_FATURAMENTO,
							contaHelper.getAnoMesReferenciaConta()));

			colecaoMedicaoHistorico = fachada.pesquisar(filtroMedicaoHistorico, MedicaoHistorico.class.getName());

			medicaoHistorico = null;
			dataLeituraAnterior = null;
			dataLeituraAtual = null;

			if(!Util.isVazioOrNulo(colecaoMedicaoHistorico)){

				medicaoHistorico = (MedicaoHistorico) Util.retonarObjetoDeColecao(colecaoMedicaoHistorico);

				if(medicaoHistorico.getDataLeituraAnteriorFaturamento() != null){

					dataLeituraAnterior = medicaoHistorico.getDataLeituraAnteriorFaturamento();

					if(medicaoHistorico.getDataLeituraAtualFaturamento() != null){

						dataLeituraAtual = medicaoHistorico.getDataLeituraAtualFaturamento();
					}else{

						dataLeituraAtual = Util.adicionarNumeroDiasDeUmaData(dataLeituraAnterior, 30);
					}
				}else{

					dataLeituraAnterior = Util.gerarDataInicialApartirAnoMesRefencia(contaHelper.getAnoMesReferenciaConta());
					dataLeituraAtual = Util.adicionarNumeroDiasDeUmaData(dataLeituraAnterior, 30);
				}
			}else{

				dataLeituraAnterior = Util.gerarDataInicialApartirAnoMesRefencia(contaHelper.getAnoMesReferenciaConta());
				dataLeituraAtual = Util.adicionarNumeroDiasDeUmaData(dataLeituraAnterior, 30);
			}

			// Obtendo o consumo mínimo da referência da conta
			filtroConsumoHistorico.limparListaParametros();
			filtroConsumoHistorico.adicionarParametro(new ParametroSimples(FiltroConsumoHistorico.IMOVEL_ID, imovel.getId()));
			filtroConsumoHistorico.adicionarParametro(new ParametroSimples(FiltroConsumoHistorico.ANO_MES_FATURAMENTO, contaHelper
							.getAnoMesReferenciaConta()));

			int consumoMinimoLigacao = 0;
			Collection<ConsumoHistorico> colecaoConsumoHistorico = fachada.pesquisar(filtroConsumoHistorico,
							ConsumoHistorico.class.getName());

			if(!Util.isVazioOrNulo(colecaoConsumoHistorico)){

				consumoMinimoLigacao = ((ConsumoHistorico) Util.retonarObjetoDeColecao(colecaoConsumoHistorico)).getConsumoMinimo();
			}

			if(consumoMinimoLigacao == 0){

				consumoMinimoLigacao = fachada.obterConsumoMinimoLigacao(imovel, colecaoCategoria);
			}

			if(contaHelper.getSituacaoConta().equals("QUITADA")){

				log.info("****Recalculando Conta Histórico - cnta_id[" + contaHelper.getIdConta().toString() + "] do Imóvel - imov_id["
							+ contaHelper.getIdImovel().toString().toString() + "]");
			}else{

				log.info("****Recalculando Conta - cnta_id[" + contaHelper.getIdConta().toString() + "] do Imóvel - imov_id["
								+ contaHelper.getIdImovel().toString().toString() + "]");
			}

			log.info("--->Referência da Conta[" + contaHelper.getAnoMesReferenciaConta().toString() + "]");
			log.info("--->Situação da Ligação de Água da Conta[" + contaHelper.getIdLigacaoAguaSituacao().toString() + "]");
			log.info("--->Situação da Ligação de Esgoto da Conta[" + contaHelper.getIdLigacaoEsgotoSituacao().toString() + "]");
			log.info("--->Consumo Faturado de Água da Conta[" + contaHelper.getConsumoFaturadoAgua().toString() + "]");
			log.info("--->Consumo Faturado de Esgoto da Conta[" + contaHelper.getConsumoFaturadoEsgoto().toString() + "]");
			log.info("--->Consumo Mínimo Informado[" + consumoMinimoLigacao + "]");
			log.info("--->Data Leitura Anterior Informada[" + Util.formatarData(dataLeituraAnterior) + "]");
			log.info("--->Data Leitura Atual Informada[" + Util.formatarData(dataLeituraAtual) + "]");
			log.info("--->Percentual de Esgoto da Conta[" + contaHelper.getPercentualEsgoto().toString() + "]");
			log.info("--->Consumo Tarifa Recáculo - cstf_id[" + consumoTarifaRecalculo.getId().toString() + "]");
			log.info("--->Consumo Tarifa Vigência Recáculo - cstv_id[" + consumoTarifaVigencia.getId().toString() + "]");

			colecaoCalcularValoresAguaEsgotoHelper = fachada.calcularValoresAguaEsgoto(contaHelper.getAnoMesReferenciaConta(),
							contaHelper.getIdLigacaoAguaSituacao(), contaHelper.getIdLigacaoEsgotoSituacao(),
							contaHelper.getIndicadorFaturamentoAgua(), contaHelper.getIndicadorFaturamentoEsgoto(), colecaoCategoria,
							contaHelper.getConsumoFaturadoAgua(), contaHelper.getConsumoFaturadoEsgoto(), consumoMinimoLigacao,
							dataLeituraAnterior, dataLeituraAtual, contaHelper.getPercentualEsgoto(), consumoTarifaRecalculo.getId(),
							imovel.getId(), consumoTarifaVigencia);

			BigDecimal valorRecalculadoAgua = BigDecimal.ZERO;
			BigDecimal valorRecalculadoEsgoto = BigDecimal.ZERO;

			for(CalcularValoresAguaEsgotoHelper calcularValoresAguaEsgotoHelper : colecaoCalcularValoresAguaEsgotoHelper){

				if(calcularValoresAguaEsgotoHelper.getValorFaturadoAguaCategoria() != null){

					valorRecalculadoAgua = valorRecalculadoAgua.add(calcularValoresAguaEsgotoHelper.getValorFaturadoAguaCategoria());
				}

				if(calcularValoresAguaEsgotoHelper.getValorFaturadoEsgotoCategoria() != null){

					valorRecalculadoEsgoto = valorRecalculadoEsgoto.add(calcularValoresAguaEsgotoHelper.getValorFaturadoEsgotoCategoria());
				}
			}

			contaHelper.setValorRecalculadoAgua(valorRecalculadoAgua);
			contaHelper.setValorRecalculadoEsgoto(valorRecalculadoEsgoto);

			// Valor da Conta Recalculada
			contaHelper.setValorRecalculadoTotal(contaHelper.getValorTotalContaComDadosRecalculo());

			// Diferença (total original - total recalculado)
			contaHelper.setValorDiferenca(contaHelper.getValorOriginalTotal().subtract(contaHelper.getValorRecalculadoTotal()));
		}

		Cliente clienteUsuarioImovel = null;
		RelatorioContasRecalculadasBean relatorioBean = null;
		for(ContaSimularCalculoDadosReaisHelper contaHelper : colecaoContasSimularCalculoHelper){

			relatorioBean = new RelatorioContasRecalculadasBean();

			relatorioBean.setMatricula(contaHelper.getIdImovelFormado());
			relatorioBean.setIdImovel(contaHelper.getIdImovel());
			relatorioBean.setIdConta(contaHelper.getIdConta());

			clienteUsuarioImovel = fachada.pesquisarClienteUsuarioImovel(contaHelper.getIdImovel());

			if(clienteUsuarioImovel != null){

				relatorioBean.setClienteUsuario(clienteUsuarioImovel.getDescricaoParaRegistroTransacao());
			}

			relatorioBean.setInscricao(fachada.pesquisarInscricaoImovel(contaHelper.getIdImovel(), true));
			relatorioBean.setEndereco(fachada.pesquisarEndereco(contaHelper.getIdImovel()));
			relatorioBean.setReferenciaConta(contaHelper.getAnoMesReferenciaContaFormado());
			relatorioBean.setSituacaoConta(contaHelper.getSituacaoConta());
			relatorioBean.setValorOriginalAgua(contaHelper.getValorOriginalAgua());
			relatorioBean.setValorOriginalEsgoto(contaHelper.getValorOriginalEsgoto());
			relatorioBean.setValorOriginalTotal(contaHelper.getValorOriginalTotal());
			relatorioBean.setValorRecalculadoAgua(contaHelper.getValorRecalculadoAgua());
			relatorioBean.setValorRecalculadoEsgoto(contaHelper.getValorRecalculadoEsgoto());
			relatorioBean.setValorRecalculadoTotal(contaHelper.getValorRecalculadoTotal());
			relatorioBean.setValorDiferenca(contaHelper.getValorDiferenca());
			colecaoBeansRelatorio.add(relatorioBean);
		}

		SistemaParametro sistemaParametro = Fachada.getInstancia().pesquisarParametrosDoSistema();
		Map parametros = new HashMap();
		parametros.put("imagem", sistemaParametro.getImagemRelatorio());
		parametros.put("tarifaRecalculo", consumoTarifaRecalculo.getDescricao());
		parametros.put("vigenciaTarifaRecalculo", consumoTarifaVigencia.getDataVigenciaFormatada());

		RelatorioDataSource ds = new RelatorioDataSource(colecaoBeansRelatorio);

		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_CONTAS_RECALCULADAS, parametros, ds, tipoFormatoRelatorio);

		try{

			this.persistirRelatorioConcluido(retorno, Relatorio.RELATORIO_CONTAS_RECALCULADAS, idFuncionalidadeIniciada,
							"RELATÓRIO CONTAS RECALCULADAS SIMULAR");
		}catch(ControladorException e){

			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relatório no sistema", e);
		}

		return retorno;
	}


	public int calcularTotalRegistrosRelatorio(){

		FiltroContaSimularCalculoHelper filtroContaSimularCalculoHelper = (FiltroContaSimularCalculoHelper) getParametro("filtroContaSimularCalculoHelper");
		int totalRegistros = Fachada.getInstancia().pesquisarTotalRegistrosContasSimularCalculoDadosReais(filtroContaSimularCalculoHelper);

		return totalRegistros;
	}

	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioContasRecalculadas", this);
	}
}