
package gcom.agenciavirtual.cobranca;

import gcom.agenciavirtual.AbstractAgenciaVirtualJSONWebservice;
import gcom.batch.*;
import gcom.cadastro.imovel.FiltroImovelCobrancaSituacao;
import gcom.cadastro.imovel.ImovelCobrancaSituacao;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cobranca.CobrancaSituacao;
import gcom.cobranca.bean.ContaValoresHelper;
import gcom.cobranca.bean.ObterDebitoImovelOuClienteHelper;
import gcom.fachada.Fachada;
import gcom.faturamento.bean.EmitirContaHelper;
import gcom.faturamento.conta.Conta;
import gcom.faturamento.conta.FiltroConta;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.*;
import gcom.util.filtro.ConectorOr;
import gcom.util.filtro.ParametroNulo;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.arrecadacao.ParametroArrecadacao;
import gcom.util.parametrizacao.webservice.ParametrosAgenciaVirtual;

import java.math.BigDecimal;
import java.util.*;

import javax.ejb.CreateException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.comparators.ComparatorChain;
import org.apache.struts.action.ActionForm;

import br.com.procenge.comum.exception.NegocioException;
import br.com.procenge.comum.exception.PCGException;
import br.com.procenge.util.Constantes;
import br.com.procenge.util.MensagemUtil;


/**
 * Pesquisa os débitos de um imóvel
 * <b>Este serviço não é PÚBLICO</b>
 * Parametros no resquest.
 * <ul>
 * <li>matricula [OBRIGATORIO]</li>
 * <li>cpfcnpj [OBRIGATORIO]</li>
 * </ul>
 * URL de acesso: /agenciavirtual/obterDebitosWebservice.do
 * 
 * @author Luciano Galvão
 */
public class ObterDebitosWebservice
				extends AbstractAgenciaVirtualJSONWebservice {

	/**
	 * Obtém os débitos de um imóvel
	 * 
	 * @param imovelId
	 * @return
	 * @throws NegocioException
	 */
	private ColecaoDebitosJSONHelper obterDebitos(String imovelId, String dataInicial, String dataFinal) throws NegocioException{

		ColecaoDebitosJSONHelper colecaoDebitosJSON = null;
		Object[] argumentosMensagemArray = new Object[1];

		try{

			SistemaParametro sistemaParametro;
			sistemaParametro = ServiceLocator.getInstancia().getControladorUtil().pesquisarParametrosDoSistema();

			// Conjunto de parâmetros a serem passados para o método
			// ControladorCobranca.obterDebitoImovelOuClienteContas, de acordo com o UC3054
			int indicadorDebito = 1;
			String codigoCliente = null;
			Integer clienteRelacaoTipo = null;
			String anoMesInicialReferenciaDebito = "150001";
			String anoMesFinalReferenciaDebito = "999912";

			Date anoMesInicialVencimentoDebito = Util.converterCampoStringParaData("", "01/01/1500", "dd/mm/yyyy");
			Date anoMesFinalVencimentoDebito = Util.converterCampoStringParaData("", "31/12/9999", "dd/mm/yyyy");


			if(dataInicial != null){
				anoMesInicialVencimentoDebito = Util.converterStringParaData(dataInicial);
			}

			if(dataFinal != null){
				anoMesFinalVencimentoDebito = Util.converterStringParaData(dataFinal);
			}

			int indicadorPagamento = 2;
			int indicadorConta = 1;
			int indicadorCalcularAcrescimoImpontualidade = 1;
			int indicadorCalcularAcrescimosSucumbenciaAnterior = 1;
			Boolean indicadorContas = Boolean.TRUE;
			String anoMesArrecadacao = sistemaParametro.getAnoMesArrecadacao() + "";
			Collection idImoveis = null;

			Collection<DebitoJSONHelper> debitosJSON = null;
			DebitoJSONHelper debitoJSON = null;
			Boolean indicadorExecucaoFiscal = Boolean.FALSE;
			int referenciaParametroSistema = Fachada.getInstancia().pesquisarParametrosDoSistema().getAnoMesFaturamento();
			

			ObterDebitoImovelOuClienteHelper debitoImovelOuClienteHelper = null;
			
			if(ParametrosAgenciaVirtual.isDividaAtiva() && Util.isVazioOuBranco(imovelId)){

				FiltroProcessoIniciado filtroProcessoIniciado = new FiltroProcessoIniciado(FiltroProcessoIniciado.DATA_HORA_TERMINO);
				filtroProcessoIniciado.adicionarParametro(new ParametroSimples(FiltroProcessoIniciado.ID_PROCESSO, Integer
								.valueOf(Processo.IMPORTAR_DADOS_DIVIDA_ATIVA)));
				filtroProcessoIniciado.adicionarParametro(new ParametroSimples(FiltroProcessoIniciado.PROCESSO_SITUACAO_ID,
								ProcessoSituacao.EM_ESPERA, ConectorOr.CONECTOR_OR));
				filtroProcessoIniciado.adicionarParametro(new ParametroSimples(FiltroProcessoIniciado.PROCESSO_SITUACAO_ID,
								ProcessoSituacao.EM_PROCESSAMENTO, ConectorOr.CONECTOR_OR));
				filtroProcessoIniciado.adicionarParametro(new ParametroSimples(FiltroProcessoIniciado.PROCESSO_SITUACAO_ID,
								ProcessoSituacao.AGENDADO));

				filtroProcessoIniciado.adicionarCaminhoParaCarregamentoEntidade(FiltroProcessoIniciado.PROCESSO_SITUACAO);

				Collection<ProcessoIniciado> colecaoProcessosIniciados = getControladorUtil().pesquisar(filtroProcessoIniciado,
								ProcessoIniciado.class.getName());

				if(!Util.isVazioOrNulo(colecaoProcessosIniciados)){
					informarStatus(STATUS_TIPO_ALERTA, MensagemUtil.obterMensagem(Constantes.RESOURCE_BUNDLE,
									"atencao.ja.existe.processo.encaminhado.para.execucao"));
				}else{

					ProcessoIniciado processoIniciado = new ProcessoIniciado();

					Processo processo = new Processo();
					processo.setId(Processo.IMPORTAR_DADOS_DIVIDA_ATIVA);
					processoIniciado.setDataHoraAgendamento(new Date());

					processoIniciado.setProcesso(processo);
					processoIniciado.setUsuario(Usuario.USUARIO_AGENCIA_VIRTUAL);

					Collection colecaoSetorComercial = ServiceLocator.getInstancia().getControladorBatch()
									.pesquisarSetorComercialProcessamentoBatch();

					List<Object> colecaoParametros = new ArrayList<Object>();
					colecaoParametros.add(anoMesInicialVencimentoDebito);
					colecaoParametros.add(anoMesFinalVencimentoDebito);
					colecaoParametros.add(colecaoSetorComercial);

					ProcessoIniciadoDadoComplementarHelper helper = new ProcessoIniciadoDadoComplementarHelper();
					String periodoInscricaoDividaAtiva = dataInicial + " ate " + dataFinal;
					helper.adcionarDadoComplementar(DadoComplementarEnumerator.ANO_MES_REFERENCIA, periodoInscricaoDividaAtiva);
					processoIniciado.setDescricaoDadosComplementares(helper.getStringFormatoPesistencia());

					Integer codigoProcessoIniciadoGerado = (Integer) ServiceLocator.getInstancia().getControladorBatch()
									.inserirProcessoIniciadoOnline(processoIniciado, colecaoParametros);

					System.out.println("Processo dívida ativa id = " + codigoProcessoIniciadoGerado);

					informarStatus(STATUS_TIPO_SUCESSO,
									MensagemUtil.obterMensagem(Constantes.RESOURCE_BUNDLE, "atencao.processo.encaminhado.para.execucao"));
				}


			}else{

				// Consulta os débitos do imóvel
				 debitoImovelOuClienteHelper = ServiceLocator.getInstancia().getControladorCobranca()
								.obterDebitoImovelOuClienteContas(indicadorDebito, imovelId, codigoCliente, clienteRelacaoTipo,
												anoMesInicialReferenciaDebito, anoMesFinalReferenciaDebito, anoMesInicialVencimentoDebito,
												anoMesFinalVencimentoDebito, indicadorPagamento, indicadorConta,
												indicadorCalcularAcrescimoImpontualidade, indicadorContas, anoMesArrecadacao, idImoveis,
												new Date(), ConstantesSistema.SIM, null, ConstantesSistema.SIM, ConstantesSistema.SIM,
												ConstantesSistema.SIM, indicadorCalcularAcrescimosSucumbenciaAnterior, null);

				Collection<ContaValoresHelper> collContaValoresHelpers = debitoImovelOuClienteHelper.getColecaoContasValores();

				Integer totalContas = 0;
				BigDecimal valorTotalDebitos = BigDecimal.ZERO;
				BigDecimal valorTotalDebitosEmAnalise = BigDecimal.ZERO;

				if(!Util.isVazioOrNulo(collContaValoresHelpers)){

					/*
					 * Caso o imóvel esteja em execução fiscal (existir ocorrência na tabela
					 * IMOVEL_COBRANCA_SITUACAO com ISCB_DTRETIRADACOBRANCA = nulo e CBST_ID =
					 * CBST_ID da tabela COBRANCA_SITUACAO com CBST_CDCONSTANTE =
					 * 'EXECUCAO_FISCAL'), só deixar emitir 2a. via de conta para a última conta
					 * gerada (maior referência em CONTA ou CONTA_HISTORICO)
					 * OC1424202
					 * e referência da conta >= ano/mês da referência anterior do faturamento
					 * corrente (PARM_AMFATURAMENTO menos 1 mês da tabela SISTEMA_PARAMETRO)
					 */
					FiltroImovelCobrancaSituacao filtroImovelCobrancaSituacao = new FiltroImovelCobrancaSituacao();
					filtroImovelCobrancaSituacao.adicionarParametro(new ParametroSimples(FiltroImovelCobrancaSituacao.IMOVEL_ID, imovelId));
					filtroImovelCobrancaSituacao.adicionarParametro(new ParametroNulo(FiltroImovelCobrancaSituacao.DATA_RETIRADA_COBRANCA));
					filtroImovelCobrancaSituacao.adicionarParametro(new ParametroSimples(FiltroImovelCobrancaSituacao.ID_COBRANCA_SITUACAO,
									CobrancaSituacao.EXECUCAO_FISCAL));

					Collection<ImovelCobrancaSituacao> colecaoImovelCobrancaSituacao = this.getControladorUtil().pesquisar(
									filtroImovelCobrancaSituacao, ImovelCobrancaSituacao.class.getName());

					if(!Util.isVazioOrNulo(colecaoImovelCobrancaSituacao)){

						indicadorExecucaoFiscal = Boolean.TRUE;

						List<Conta> colecaoContasRetornadas = new ArrayList<Conta>();

						for(ContaValoresHelper contaValoresHelper : collContaValoresHelpers){

							colecaoContasRetornadas.add(contaValoresHelper.getConta());
						}

						// Ordenar a coleção por referencia
						List sortFields = new ArrayList();
						sortFields.add(new BeanComparator("referencia"));

						ComparatorChain multiSort = new ComparatorChain(sortFields);
						Collections.sort(colecaoContasRetornadas, multiSort);
						Collections.reverse(colecaoContasRetornadas);

						ContaValoresHelper contaValoresHelperMaisRecente = null;


						for(ContaValoresHelper contaValoresHelper : collContaValoresHelpers){

							if(contaValoresHelper.getConta().getId().equals(colecaoContasRetornadas.get(0).getId())){
								contaValoresHelperMaisRecente = contaValoresHelper;
								break;
							}
						}

						collContaValoresHelpers.clear();
						collContaValoresHelpers.add(contaValoresHelperMaisRecente);
					}

					Collection motivosRevisaoParaExcluir = getMotivosRevisaoParaExcluir();

					String paramNaoExibirConta = (String) ParametrosAgenciaVirtual.P_AV_CONTA_REVISAO_NAO_EXIBIR.executar();

					debitosJSON = new ArrayList<DebitoJSONHelper>();



					// Percorre a lista de contas retornada pelo Controlador para construir o objeto
					// JSON que será retornado pelo Webservice
					for(ContaValoresHelper contaValoresHelper : collContaValoresHelpers){

						if(contaValoresHelper.getConta() != null){
							Conta conta = contaValoresHelper.getConta();



								if((conta.getContaMotivoRevisao() == null) //
												|| (ConstantesSistema.SIM.toString().equals(paramNaoExibirConta) //
																&& (motivosRevisaoParaExcluir != null) //
												&& (!motivosRevisaoParaExcluir.contains(conta.getContaMotivoRevisao().getId())) //
												)){

									// Constroi o objeto DebitoJSONHelper
									debitoJSON = new DebitoJSONHelper();
									debitoJSON.setIdConta(conta.getId());
									debitoJSON.setReferencia(conta.getReferencia());
									debitoJSON.setConsumo(obterConsumoFaturado(conta));
									debitoJSON.setValorConta(conta.getValorTotal());
									debitoJSON.setVencimento(conta.getDataVencimentoConta());

								if((indicadorExecucaoFiscal) && (referenciaParametroSistema > conta.getAnoMesReferenciaConta())){
									debitoJSON.setBloquearSegundaVia(true);
								}else{
									// flag que indica se pore emitir ou não segunda via.
									debitoJSON.setBloquearSegundaVia(verificarBloqueioSegundaVia(conta.getId()));
								}

									// flag que indica para a Agência Virtual se vai exibir a coluna
									// de
									// acréscimo impontualidade.
									debitoJSON.setExibirAcrescimos(verificarExibicaoAcrescimoImpontualidade());
									debitoJSON.setValorAcrescimos(contaValoresHelper.getValorTotalContaValores());
									debitoJSON.setEmRevisao(conta.getContaMotivoRevisao() != null);
									// adiciona um item à lista de débitos
									debitosJSON.add(debitoJSON);
									// impontualiadade chamar o getValorTotalContaValores

									// Sumarizando total de contas e valor total de débitos
									totalContas++;
									valorTotalDebitos = valorTotalDebitos.add(debitoJSON.getValorConta());
									if(conta.getContaMotivoRevisao() != null){
										valorTotalDebitosEmAnalise = valorTotalDebitosEmAnalise.add(conta.getValorTotal());
									}


							}else{
								valorTotalDebitosEmAnalise = valorTotalDebitosEmAnalise.add(conta.getValorTotal());
							}
						}
					}



					// Construindo o objeto que será retornado, do tipo ColecaoDebitosJSONHelper
					colecaoDebitosJSON = new ColecaoDebitosJSONHelper();
					colecaoDebitosJSON.setTotalContas(totalContas);
					colecaoDebitosJSON.setValorTotalDebitos(valorTotalDebitos);
					colecaoDebitosJSON.setValorTotalDebitosEmAnalise(valorTotalDebitosEmAnalise);
					colecaoDebitosJSON.setDebitos(debitosJSON);
					colecaoDebitosJSON.setIndicadorExecucaoFiscal(indicadorExecucaoFiscal);
				}

				if(valorTotalDebitos.compareTo(BigDecimal.ZERO) == 0 && valorTotalDebitosEmAnalise.compareTo(BigDecimal.ZERO) == 0){
					informarStatus(STATUS_TIPO_ALERTA, MensagemUtil.obterMensagem(Constantes.RESOURCE_BUNDLE, "atencao.imovel_sem_debitos"));
				}

			}

			
			argumentosMensagemArray[0] = imovelId;

		}catch(PCGException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new NegocioException("atencao.erro.consultar.debitos.imovel", argumentosMensagemArray, e);

		}catch(ControladorException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new NegocioException("atencao.erro.consultar.debitos.imovel", argumentosMensagemArray, e);
		}

		return colecaoDebitosJSON;
	}

	@Override
	protected void preencherJSONBody(ActionForm form, HttpServletRequest request) throws Exception{

		String imovelId = null;
		if(!Util.isVazioOuBranco(recuperarParametroString("matricula", LABEL_CAMPO_MATRICULA_DO_IMOVEL, false, false, request))){
			imovelId = recuperarParametroString("matricula", LABEL_CAMPO_MATRICULA_DO_IMOVEL, false, false, request);
		}

		String dataInicial = null;
		if(!Util.isVazioOuBranco(recuperarParametroString("dataInicial", "Data Inicial da Dívida", false, false, request))){
			dataInicial = recuperarParametroString("dataInicial", "Data Inicial da Dívida", false, false, request);
		}

		String dataFinal = null;
		if(!Util.isVazioOuBranco(recuperarParametroString("dataFinal", "Data Final da Dívida", false, false, request))){
			dataFinal = recuperarParametroString("dataFinal", "Data Final da Dívida", false, false, request);
		}

		if(dataInicial != null && dataFinal != null){
			imovelId = null;
		}else{
			dataInicial = null;
			dataFinal = null;

		}

		ColecaoDebitosJSONHelper debitosJSONHelper = obterDebitos(imovelId, dataInicial, dataFinal);
		adicionarObjetoAoBody("colecaoDebitos", debitosJSONHelper);
	}

	private Boolean verificarBloqueioSegundaVia(Integer idConta) throws NegocioException, ControladorException{

		FiltroConta filtroConta = new FiltroConta();
		filtroConta.adicionarParametro(new ParametroSimples(FiltroConta.ID, idConta));
		filtroConta.adicionarCaminhoParaCarregamentoEntidade("contaMotivoRevisao");

		String indicadorNaoPermiteContaEmRevisao = (String) ParametrosAgenciaVirtual.P_AV_EMITE_2VIA_CTA_REVISAO.executar();
		Collection motivosRevisaoParaAlertar = null;
		boolean verificarMotivoRevisaoEstaPreenchido = false;

		if(ConstantesSistema.SIM.equals(Short.valueOf(indicadorNaoPermiteContaEmRevisao))){
			motivosRevisaoParaAlertar = getMotivosRevisaoSegundaViaParaAlertarNaConta();
		}else{
			verificarMotivoRevisaoEstaPreenchido = true;
		}

		Boolean retorno = Boolean.FALSE;

		Collection collContas = getControladorUtil().pesquisar(filtroConta, Conta.class.getName());
		Conta conta = (Conta) gcom.util.Util.retonarObjetoDeColecao(collContas);

		if(gcom.util.Util.isNaoNuloBrancoZero(conta.getContaMotivoRevisao())){

			if(verificarMotivoRevisaoEstaPreenchido){
				if(conta.getContaMotivoRevisao() != null){
					retorno = Boolean.TRUE;
				}
			}else{
				if(motivosRevisaoParaAlertar.contains(conta.getContaMotivoRevisao().getId())){
					retorno = Boolean.TRUE;
				}
			}

		}

		return retorno;
	}

	/**
	 * Recupera a lista de motivos de revisão que não emite 2º via.
	 */
	private Collection<Integer> getMotivosRevisaoSegundaViaParaAlertarNaConta() throws ControladorException{

		String paramMotivos = (String) ParametrosAgenciaVirtual.P_AV_CONTA_MOTIVO_REVISAO_NAO_EMITIR_2_VIA.executar();
		String[] motivosArray = paramMotivos.split(",");

		Collection<Integer> motivos = new ArrayList<Integer>();

		for(int i = 0; i < motivosArray.length; i++){
			motivos.add(Integer.parseInt(motivosArray[i]));
		}

		return motivos;
	}

	private Boolean verificarExibicaoAcrescimoImpontualidade() throws NegocioException, ControladorException{

		Boolean retorno = Boolean.FALSE;
		// Parâmetro que identifica se a empresa emite o documento com acrescimos
		String parametroTratarAcrescimosEmissaoDocumento = ParametroArrecadacao.P_TRATAR_ACRESCIMOS_EMISSAO_DOCUMENTO.executar().toString();

		if(parametroTratarAcrescimosEmissaoDocumento.equals(Short.toString(ConstantesSistema.SIM))){
			retorno = Boolean.TRUE;
		}

		return retorno;
	}

	/**
	 * Recupera a lista de motivos de revisão que deve ser desconsiderada da lista de contas
	 * 
	 * @author Luciano Galvao
	 * @return
	 * @throws ControladorException
	 */
	private Collection<Integer> getMotivosRevisaoParaExcluir() throws ControladorException{

		String paramMotivos = (String) ParametrosAgenciaVirtual.P_AV_CONTA_MOTIVO_REVISAO_NAO_EXIBIR_CONTA.executar();
		String[] motivosArray = paramMotivos.split(",");

		Collection<Integer> motivosRevisaoParaExcluir = new ArrayList<Integer>();

		for(int i = 0; i < motivosArray.length; i++){
			motivosRevisaoParaExcluir.add(Integer.parseInt(motivosArray[i]));
		}

		return motivosRevisaoParaExcluir;

	}

	/**
	 * Consulta o consumo faturado da conta
	 * 
	 * @author Luciano Galvao
	 * @date 19/03/2013
	 */
	private Integer obterConsumoFaturado(Conta conta) throws ControladorException{

		Integer consumoFaturado = new Integer(0);

		EmitirContaHelper emitirContaHelper = new EmitirContaHelper();
		emitirContaHelper.setIdLigacaoAguaSituacao(conta.getLigacaoAguaSituacao().getId());
		emitirContaHelper.setIdLigacaoEsgotoSituacao(conta.getLigacaoEsgotoSituacao().getId());
		emitirContaHelper.setConsumoAgua(conta.getConsumoAgua());
		emitirContaHelper.setConsumoEsgoto(conta.getConsumoEsgoto());

		Integer[] parmSituacao = ServiceLocator.getInstancia().getControladorFaturamento().determinarTipoLigacaoMedicao(emitirContaHelper);

		if(!gcom.util.Util.isVazioOrNulo(parmSituacao)){
			Integer tipoMedicao = parmSituacao[1];

			String[] parmsConsumo = ServiceLocator.getInstancia().getControladorFaturamento()
							.obterConsumoFaturadoConsumoMedioDiario(emitirContaHelper, tipoMedicao, null);

			if(!gcom.util.Util.isVazioOrNulo(parmsConsumo) && !gcom.util.Util.isVazioOuBranco(parmsConsumo[0])){
				consumoFaturado = Integer.valueOf(parmsConsumo[0]);
			}
		}

		return consumoFaturado;
	}

	/**
	 * Retorna o valor de controladorUtil
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

}
