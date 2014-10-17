
package gcom.relatorio.cobranca;

import gcom.arrecadacao.Arrecadador;
import gcom.arrecadacao.pagamento.GuiaPagamento;
import gcom.batch.Relatorio;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.cliente.FiltroCliente;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cobranca.BoletoBancario;
import gcom.cobranca.BoletoBancarioMotivoCancelamento;
import gcom.cobranca.BoletoBancarioSituacao;
import gcom.cobranca.BoletoBancarioSituacaoHistorico;
import gcom.cobranca.DocumentoTipo;
import gcom.cobranca.FiltroBoletoBancarioMotivoCancelamento;
import gcom.cobranca.FiltroBoletoBancarioSituacao;
import gcom.cobranca.FiltroBoletoBancarioSituacaoHistorico;
import gcom.cobranca.FiltroDocumentoTipo;
import gcom.fachada.Fachada;
import gcom.gui.cobranca.BoletoBancarioContaHelper;
import gcom.gui.cobranca.BoletoBancarioHelper;
import gcom.gui.faturamento.bean.GuiaPagamentoPrestacaoHelper;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * [UC3023] Manter Boleto Bancário
 * 
 * @author Hebert Falcão
 * @date 12/10/2011
 */
public class RelatorioBoletosBancarios
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	/**
	 * Construtor da classe RelatorioBoletosBancarios
	 */
	public RelatorioBoletosBancarios(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_BOLETOS_BANCARIOS);
	}

	@Deprecated
	public RelatorioBoletosBancarios() {

		super(null, "");
	}

	public Object executar() throws TarefaException{

		// Obtém a instância da fachada
		Fachada fachada = Fachada.getInstancia();

		// Coleção de beans do relatório
		List relatorioBeans = new ArrayList();

		// Totalização Geral
		BigDecimal pValorTotalBoleto = BigDecimal.ZERO;
		BigDecimal pValorTotalPago = BigDecimal.ZERO;

		// Parâmetros da tela de filtro
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");
		BoletoBancarioHelper boletoBancarioHelper = (BoletoBancarioHelper) getParametro("boletoBancarioHelper");
		boolean verificarNumeroBoletoCartaCobranca = (Boolean) getParametro("verificarNumeroBoletoCartaCobranca");
		boolean desconsiderarParametros = (Boolean) getParametro("desconsiderarParametros");

		Collection<BoletoBancario> colecaoBoletoBancario = fachada.pesquisarBoletoBancario(boletoBancarioHelper,
						verificarNumeroBoletoCartaCobranca, desconsiderarParametros, false, ConstantesSistema.NUMERO_NAO_INFORMADO);

		if(!Util.isVazioOrNulo(colecaoBoletoBancario)){
			FiltroBoletoBancarioSituacaoHistorico filtroSituacaoHistorico = null;
			Collection<BoletoBancarioSituacaoHistorico> colecaoSituacaoHistorico = null;

			// Bean do relatório e sub-relatório
			RelatorioBoletosBancariosBean relatorioBoletosBancariosBean = null;
			RelatorioBoletosBancariosDetalheContaBean relBolBancDetalheContaBean = null;
			RelatorioBoletosBancariosDetalheGuiaBean relBolBancDetalheGuiaBean = null;

			// Coleção do sub-relatório de contas
			Collection<RelatorioBoletosBancariosDetalheContaBean> colecaoRelBolBancDetalheContaBean1 = null;
			Collection<RelatorioBoletosBancariosDetalheContaBean> colecaoRelBolBancDetalheContaBean2 = null;
			Collection<RelatorioBoletosBancariosDetalheContaBean> colecaoRelBolBancDetalheContaBean3 = null;

			// Coleção do sub-relatório de guias
			Collection<RelatorioBoletosBancariosDetalheGuiaBean> colecaoRelBolBancDetalheGuiaBean = null;

			for(BoletoBancario boletoBancario : colecaoBoletoBancario){
				relatorioBoletosBancariosBean = new RelatorioBoletosBancariosBean();

				Integer idBoletoBancario = boletoBancario.getId();

				// 1.3.1. Arrecadador
				Arrecadador arrecadador = boletoBancario.getArrecadador();

				if(arrecadador != null){
					Short codigoAgenteArrecadador = arrecadador.getCodigoAgente();

					if(codigoAgenteArrecadador != null){
						String codigoAgenteArrecadadorStr = codigoAgenteArrecadador.toString();
						relatorioBoletosBancariosBean.setCodigoAgenteArrecadador(codigoAgenteArrecadadorStr);
					}

					Cliente clienteArrecadador = arrecadador.getCliente();

					if(clienteArrecadador != null){
						String nomeAgenteArrecadador = clienteArrecadador.getNome();
						relatorioBoletosBancariosBean.setNomeAgenteArrecadador(nomeAgenteArrecadador);
					}
				}

				// 1.3.2. Número do Boleto Bancário
				Integer numeroSequencialBoletoBancario = boletoBancario.getNumeroSequencial();

				if(numeroSequencialBoletoBancario != null){
					String numeroSequencialBoletoBancarioStr = numeroSequencialBoletoBancario.toString();
					relatorioBoletosBancariosBean.setNumeroSequencialBoletoBancario(numeroSequencialBoletoBancarioStr);
				}

				// 1.3.3. Imóvel
				Imovel imovel = boletoBancario.getImovel();

				if(imovel != null){
					Integer idImovel = imovel.getId();
					relatorioBoletosBancariosBean.setIdImovel(idImovel.toString());
				}

				// 1.3.4. Nosso Número
				String nossoNumero = boletoBancario.getNossoNumero();
				relatorioBoletosBancariosBean.setNossoNumero(nossoNumero);

				// 1.3.5. Data de Entrada
				filtroSituacaoHistorico = new FiltroBoletoBancarioSituacaoHistorico();
				filtroSituacaoHistorico.adicionarParametro(new ParametroSimples(FiltroBoletoBancarioSituacaoHistorico.BOLETO_BANCARIO_ID,
								idBoletoBancario));
				filtroSituacaoHistorico.adicionarParametro(new ParametroSimples(
								FiltroBoletoBancarioSituacaoHistorico.BOLETO_BANCARIO_SITUACAO_ID,
								BoletoBancarioSituacao.GERADO_NAO_ENVIADO_AO_BANCO));

				colecaoSituacaoHistorico = fachada.pesquisar(filtroSituacaoHistorico, BoletoBancarioSituacaoHistorico.class.getName());

				if(!Util.isVazioOrNulo(colecaoSituacaoHistorico)){
					BoletoBancarioSituacaoHistorico boletoBancarioSituacaoHistorico = (BoletoBancarioSituacaoHistorico) Util
									.retonarObjetoDeColecao(colecaoSituacaoHistorico);

					Date dataEntradaBoletoBancario = boletoBancarioSituacaoHistorico.getDataEntrada();

					if(dataEntradaBoletoBancario != null){
						String dataEntradaBoletoBancarioStr = Util.formatarData(dataEntradaBoletoBancario);
						relatorioBoletosBancariosBean.setDataEntradaBoletoBancario(dataEntradaBoletoBancarioStr);
					}
				}

				// 1.3.6. Data de Vencimento
				Date dataVencimentoBoletoBancario = boletoBancario.getDataVencimento();

				if(dataVencimentoBoletoBancario != null){
					String dataVencimentoBoletoBancarioStr = Util.formatarData(dataVencimentoBoletoBancario);
					relatorioBoletosBancariosBean.setDataVencimentoBoletoBancario(dataVencimentoBoletoBancarioStr);
				}

				// 1.3.7. Valor do Boleto
				BigDecimal valorDebitoBoletoBancario = boletoBancario.getValorDebito();

				if(valorDebitoBoletoBancario != null){
					relatorioBoletosBancariosBean.setValorDebitoBoletoBancario(valorDebitoBoletoBancario);

					// Totalizador Geral
					pValorTotalBoleto = pValorTotalBoleto.add(valorDebitoBoletoBancario);
				}

				// 1.3.8. Situação Atual
				BoletoBancarioSituacao boletoBancarioSituacao = boletoBancario.getBoletoBancarioSituacao();

				if(boletoBancarioSituacao != null){
					Integer idSituacaoBoletoBancario = boletoBancarioSituacao.getId();
					relatorioBoletosBancariosBean.setIdSituacaoBoletoBancario(idSituacaoBoletoBancario.toString());

					String descricaoSituacaoAbreviadaBoletoBancario = boletoBancarioSituacao.getDescricaoAbreviada();
					relatorioBoletosBancariosBean.setDescricaoAbreviadaSituacaoBoletoBancario(descricaoSituacaoAbreviadaBoletoBancario);

					String descricaoSituacaoBoletoBancario = boletoBancarioSituacao.getDescricao();
					relatorioBoletosBancariosBean.setDescricaoSituacaoBoletoBancario(descricaoSituacaoBoletoBancario);
				}

				// 1.3.9. Data do Pagamento
				Date dataPagamentoBoletoBancario = boletoBancario.getDataPagamento();

				if(dataPagamentoBoletoBancario != null){
					String dataPagamentoBoletoBancarioStr = Util.formatarData(dataPagamentoBoletoBancario);
					relatorioBoletosBancariosBean.setDataPagamentoBoletoBancario(dataPagamentoBoletoBancarioStr);
				}

				// 1.3.10. Data do Crédito
				Date dataCreditoBoletoBancario = boletoBancario.getDataCredito();

				if(dataCreditoBoletoBancario != null){
					String dataCreditoBoletoBancarioStr = Util.formatarData(dataCreditoBoletoBancario);
					relatorioBoletosBancariosBean.setDataCreditoBoletoBancario(dataCreditoBoletoBancarioStr);
				}

				// 1.3.11. Valor Pago
				BigDecimal valorPagoBoletoBancario = boletoBancario.getValorPago();

				if(valorPagoBoletoBancario != null){
					relatorioBoletosBancariosBean.setValorPagoBoletoBancario(valorPagoBoletoBancario);

					// Totalizador Geral
					pValorTotalPago = pValorTotalPago.add(valorPagoBoletoBancario);
				}

				// 1.3.12. Valor Tarifa
				BigDecimal valorTarifaBoletoBancario = boletoBancario.getValorTarifa();

				if(valorTarifaBoletoBancario != null){
					relatorioBoletosBancariosBean.setValorTarifaBoletoBancario(valorTarifaBoletoBancario);
				}

				// 1.3.13. Data do Cancelamento
				Date dataCancelamentoBoletoBancario = boletoBancario.getDataCancelamento();

				if(dataCancelamentoBoletoBancario != null){
					String dataCancelamentoBoletoBancarioStr = Util.formatarData(dataCancelamentoBoletoBancario);
					relatorioBoletosBancariosBean.setDataCancelamentoBoletoBancario(dataCancelamentoBoletoBancarioStr);
				}

				// 1.3.14. Motivo do Cancelamento
				BoletoBancarioMotivoCancelamento boletoBancarioMotivoCancelamento = boletoBancario.getBoletoBancarioMotivoCancelamento();

				if(boletoBancarioMotivoCancelamento != null){
					String descricaoMotivoCancelamentoBoletoBancario = boletoBancarioMotivoCancelamento.getDescricaoAbreviada();
					relatorioBoletosBancariosBean.setDescricaoMotivoCancelamentoBoletoBancario(descricaoMotivoCancelamentoBoletoBancario);
				}

				// 1.3.15. Descrição Tipo do Documento
				DocumentoTipo documentoTipoBoletoBancario = boletoBancario.getDocumentoTipo();

				if(documentoTipoBoletoBancario != null){
					String descricaoDocumentoTipoBoletoBancario = documentoTipoBoletoBancario.getDescricaoDocumentoTipo();
					relatorioBoletosBancariosBean.setDescricaoDocumentoTipoBoletoBancario(descricaoDocumentoTipoBoletoBancario);

					// Verifica Tipo de Documento Associado
					Integer idDocumentoTipoBoletoBancario = documentoTipoBoletoBancario.getId();

					if(idDocumentoTipoBoletoBancario.equals(DocumentoTipo.DOCUMENTO_DE_COBRANCA)){
						// 1.3.16.
						Collection<BoletoBancarioContaHelper> colecaoBoletoBancarioConta = fachada
										.pesquisarContasPeloBoletoBancario(idBoletoBancario);

						if(!Util.isVazioOrNulo(colecaoBoletoBancarioConta)){
							// Limpando as coleções
							colecaoRelBolBancDetalheContaBean1 = null;
							colecaoRelBolBancDetalheContaBean2 = null;
							colecaoRelBolBancDetalheContaBean3 = null;

							// Inicializando as coleções com base no tamanho de registros
							if(colecaoBoletoBancarioConta.size() <= 3){
								// Apresentar uma tabela de detalhe
								colecaoRelBolBancDetalheContaBean1 = new ArrayList<RelatorioBoletosBancariosDetalheContaBean>();
							}else if(colecaoBoletoBancarioConta.size() <= 6){
								// Apresentar duas tabelas de detalhe
								colecaoRelBolBancDetalheContaBean1 = new ArrayList<RelatorioBoletosBancariosDetalheContaBean>();
								colecaoRelBolBancDetalheContaBean2 = new ArrayList<RelatorioBoletosBancariosDetalheContaBean>();
							}else{
								// Apresentar três tabelas de detalhe
								colecaoRelBolBancDetalheContaBean1 = new ArrayList<RelatorioBoletosBancariosDetalheContaBean>();
								colecaoRelBolBancDetalheContaBean2 = new ArrayList<RelatorioBoletosBancariosDetalheContaBean>();
								colecaoRelBolBancDetalheContaBean3 = new ArrayList<RelatorioBoletosBancariosDetalheContaBean>();
							}

							// Inicializa as variáveis de quantidade e valor
							int quantidadeRegistros = 0;
							BigDecimal totalValorExcedenteConta = null;

							for(BoletoBancarioContaHelper boletoBancarioContaHelper : colecaoBoletoBancarioConta){
								// Contador de registros processados
								quantidadeRegistros = quantidadeRegistros + 1;

								Date dataVencimentoConta = boletoBancarioContaHelper.getDataVencimentoConta();
								Integer referencia = boletoBancarioContaHelper.getReferencia();
								BigDecimal valorConta = boletoBancarioContaHelper.getValorConta();

								relBolBancDetalheContaBean = new RelatorioBoletosBancariosDetalheContaBean();

								if(dataVencimentoConta != null){
									String dataVencimentoContaStr = Util.formatarData(dataVencimentoConta);
									relBolBancDetalheContaBean.setDataVencimentoConta(dataVencimentoContaStr);
								}

								if(referencia != null){
									String referenciaStr = Util.formatarAnoMesParaMesAno(referencia);
									relBolBancDetalheContaBean.setReferencia(referenciaStr);
								}

								relBolBancDetalheContaBean.setValorConta(valorConta);

								// Quebra da coleção principal em três grupos de no máximo 3
								// registros cada
								if(quantidadeRegistros <= 3){
									colecaoRelBolBancDetalheContaBean1.add(relBolBancDetalheContaBean);
								}else if(quantidadeRegistros <= 6){
									colecaoRelBolBancDetalheContaBean2.add(relBolBancDetalheContaBean);
								}else if(quantidadeRegistros <= 9){
									colecaoRelBolBancDetalheContaBean3.add(relBolBancDetalheContaBean);
								}else{
									if(totalValorExcedenteConta == null){
										totalValorExcedenteConta = BigDecimal.ZERO;
									}

									// Totalizador dos registros não apresentados
									totalValorExcedenteConta = totalValorExcedenteConta.add(valorConta);
								}
							}

							// Colocando a totalização dos registros não apresentados
							if(totalValorExcedenteConta != null){
								relatorioBoletosBancariosBean.setTotalValorExcedenteConta(totalValorExcedenteConta);
							}

							relatorioBoletosBancariosBean.setColecaoRelBolBancDetalheContaBean1(colecaoRelBolBancDetalheContaBean1);
							relatorioBoletosBancariosBean.setColecaoRelBolBancDetalheContaBean2(colecaoRelBolBancDetalheContaBean2);
							relatorioBoletosBancariosBean.setColecaoRelBolBancDetalheContaBean3(colecaoRelBolBancDetalheContaBean3);
						}
					}else{
						// 1.3.17
						GuiaPagamento guiaPagamento = boletoBancario.getGuiaPagamento();
						Integer idGuiaPagamento = guiaPagamento.getId();

						Integer numeroPrestacoes = boletoBancario.getNumeroPrestacoes();

						Collection<GuiaPagamentoPrestacaoHelper> colecaoGuiaPagamentoPrestacao = fachada
										.pesquisarGuiasPagamentoPrestacaoBoleto(idGuiaPagamento, numeroPrestacoes);

						if(!Util.isVazioOrNulo(colecaoGuiaPagamentoPrestacao)){
							colecaoRelBolBancDetalheGuiaBean = new ArrayList<RelatorioBoletosBancariosDetalheGuiaBean>();

							for(GuiaPagamentoPrestacaoHelper guiaPagamentoPrestacaoHelper : colecaoGuiaPagamentoPrestacao){
								Date dataEmissao = guiaPagamentoPrestacaoHelper.getDataEmissao();
								String descricaoTipoDebito = guiaPagamentoPrestacaoHelper.getDescricaoTipoDebito();
								BigDecimal valorPrestacaoTipoDebito = guiaPagamentoPrestacaoHelper.getValorPrestacaoTipoDebito();

								String idGuiaPagamentoStr = Integer.toString(idGuiaPagamento);
								String numeroPrestacoesStr = Integer.toString(numeroPrestacoes);

								relBolBancDetalheGuiaBean = new RelatorioBoletosBancariosDetalheGuiaBean();
								relBolBancDetalheGuiaBean.setIdGuia(idGuiaPagamentoStr);
								relBolBancDetalheGuiaBean.setNumeroPrestacoes(numeroPrestacoesStr);

								if(dataEmissao != null){
									String dataEmissaoStr = Util.formatarData(dataEmissao);
									relBolBancDetalheGuiaBean.setDataEmissao(dataEmissaoStr);
								}

								relBolBancDetalheGuiaBean.setDescricaoTipoDebito(descricaoTipoDebito);
								relBolBancDetalheGuiaBean.setValorPrestacaoTipoDebito(valorPrestacaoTipoDebito);

								colecaoRelBolBancDetalheGuiaBean.add(relBolBancDetalheGuiaBean);
							}

							relatorioBoletosBancariosBean.setColecaoRelBolBancDetalheGuiaBean(colecaoRelBolBancDetalheGuiaBean);
						}
					}
				}

				// Adiciona ítem na coleção
				relatorioBeans.add(relatorioBoletosBancariosBean);
			}
		}

		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

		//
		// Parâmetros do relatório
		//

		// Arrecadador
		String pArrecadador = "";

		Arrecadador arrecadador = boletoBancarioHelper.getArrecadador();

		if(arrecadador != null){
			Short codigoAgente = arrecadador.getCodigoAgente();

			if(codigoAgente != null){
				pArrecadador = Short.toString(codigoAgente);
			}

			Cliente clienteArrecadador = arrecadador.getCliente();

			if(clienteArrecadador != null){
				Integer idClienteArrecadador = clienteArrecadador.getId();

				FiltroCliente filtroCliente = new FiltroCliente();
				filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.ID, idClienteArrecadador));

				Collection<Cliente> colecaoClienteAux = fachada.pesquisar(filtroCliente, Cliente.class.getName());

				if(!Util.isVazioOrNulo(colecaoClienteAux)){
					clienteArrecadador = (Cliente) Util.retonarObjetoDeColecao(colecaoClienteAux);

					if(!Util.isVazioOuBranco(pArrecadador)){
						pArrecadador = pArrecadador + " - ";
					}

					String nomeAgenteArrecadador = clienteArrecadador.getNome();
					pArrecadador = pArrecadador + nomeAgenteArrecadador;
				}
			}
		}

		Integer numeroSequencial = boletoBancarioHelper.getNumeroSequencial();

		// Número Boleto
		String pNumeroBoleto = "";
		String pNumeroBoletoCartaCobranca = "";

		if(numeroSequencial != null){
			if(verificarNumeroBoletoCartaCobranca){
				// Número Boleto da Carta de Cobrança
				pNumeroBoletoCartaCobranca = Integer.toString(numeroSequencial);
			}else{
				// Número Boleto
				pNumeroBoleto = Integer.toString(numeroSequencial);
			}
		}

		// Matrícula do Imóvel
		String pMatriculaImovel = "";

		Imovel imovel = boletoBancarioHelper.getImovel();

		if(imovel != null){
			Integer idImovel = imovel.getId();
			pMatriculaImovel = Integer.toString(idImovel);
		}

		// Cliente Responsável
		String pClienteResponsavel = "";

		Cliente cliente = boletoBancarioHelper.getCliente();

		if(cliente != null){
			Integer idCliente = cliente.getId();
			pClienteResponsavel = Integer.toString(idCliente);

			FiltroCliente filtroCliente = new FiltroCliente();
			filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.ID, idCliente));

			Collection<Cliente> colecaoClienteAux = fachada.pesquisar(filtroCliente, Cliente.class.getName());

			if(!Util.isVazioOrNulo(colecaoClienteAux)){
				Cliente clienteAux = (Cliente) Util.retonarObjetoDeColecao(colecaoClienteAux);
				String nomeCliente = clienteAux.getNome();

				pClienteResponsavel = pClienteResponsavel + " - " + nomeCliente;
			}
		}

		// Situação do Boleto Bancário
		String pSituacaoBoleto = "";

		String[] idsBoletoBancarioSituacao = boletoBancarioHelper.getIdsBoletoBancarioSituacao();

		if(!Util.isVazioOrNulo(idsBoletoBancarioSituacao)){
			FiltroBoletoBancarioSituacao filtroSituacao = null;
			Collection<BoletoBancarioSituacao> colecaoSituacao = null;
			BoletoBancarioSituacao situacao = null;

			for(String idSituacao : idsBoletoBancarioSituacao){
				filtroSituacao = new FiltroBoletoBancarioSituacao();
				filtroSituacao.adicionarParametro(new ParametroSimples(FiltroBoletoBancarioSituacao.ID, idSituacao));

				colecaoSituacao = fachada.pesquisar(filtroSituacao, BoletoBancarioSituacao.class.getName());

				if(!Util.isVazioOrNulo(colecaoSituacao)){
					situacao = (BoletoBancarioSituacao) Util.retonarObjetoDeColecao(colecaoSituacao);
					String descricao = situacao.getDescricao();

					pSituacaoBoleto = pSituacaoBoleto + ";" + descricao;
				}
			}

			if(!Util.isVazioOuBranco(pSituacaoBoleto)){
				pSituacaoBoleto = pSituacaoBoleto.substring(1, pSituacaoBoleto.length());

				if(pSituacaoBoleto.length() > 100){
					// Truncando a descrição
					pSituacaoBoleto = pSituacaoBoleto.substring(0, 100);
					pSituacaoBoleto = pSituacaoBoleto.substring(0, pSituacaoBoleto.lastIndexOf(";")) + " ...";
				}
			}
		}

		// Tipo de Documento do Boleto Bancário
		String pTipoDocumentoBoleto = "";

		String[] idsTipoDocumento = boletoBancarioHelper.getIdsTipoDocumento();

		if(!Util.isVazioOrNulo(idsTipoDocumento)){
			FiltroDocumentoTipo filtroDocumentoTipo = null;
			Collection<DocumentoTipo> colecaoDocumentoTipo = null;
			DocumentoTipo documentoTipo = null;

			for(String idTipoDocumento : idsTipoDocumento){
				filtroDocumentoTipo = new FiltroDocumentoTipo();
				filtroDocumentoTipo.adicionarParametro(new ParametroSimples(FiltroDocumentoTipo.ID, idTipoDocumento));

				colecaoDocumentoTipo = fachada.pesquisar(filtroDocumentoTipo, DocumentoTipo.class.getName());

				if(!Util.isVazioOrNulo(colecaoDocumentoTipo)){
					documentoTipo = (DocumentoTipo) Util.retonarObjetoDeColecao(colecaoDocumentoTipo);
					String descricao = documentoTipo.getDescricaoDocumentoTipo();

					pTipoDocumentoBoleto = pTipoDocumentoBoleto + ";" + descricao;
				}
			}

			if(!Util.isVazioOuBranco(pTipoDocumentoBoleto)){
				pTipoDocumentoBoleto = pTipoDocumentoBoleto.substring(1, pTipoDocumentoBoleto.length());

				if(pTipoDocumentoBoleto.length() > 100){
					// Truncando a descrição
					pTipoDocumentoBoleto = pTipoDocumentoBoleto.substring(0, 100);
					pTipoDocumentoBoleto = pTipoDocumentoBoleto.substring(0, pTipoDocumentoBoleto.lastIndexOf(";")) + " ...";
				}
			}
		}

		// Período de Entrada
		String pPeriodoEntrada = "";

		Date dataInicialEntrada = boletoBancarioHelper.getDataInicialEntrada();
		Date dataFinalEntrada = boletoBancarioHelper.getDataFinalEntrada();

		if(dataInicialEntrada != null){
			pPeriodoEntrada = Util.formatarData(dataInicialEntrada);
		}

		if(dataFinalEntrada != null){
			if(!Util.isVazioOuBranco(pPeriodoEntrada)){
				pPeriodoEntrada = pPeriodoEntrada + " a ";
			}

			pPeriodoEntrada = pPeriodoEntrada + Util.formatarData(dataFinalEntrada);
		}

		// Período Vencimento
		String pPeriodoVencimento = "";

		Date dataInicialVencimento = boletoBancarioHelper.getDataInicialVencimento();
		Date dataFinalVencimento = boletoBancarioHelper.getDataFinalVencimento();

		if(dataInicialVencimento != null){
			pPeriodoVencimento = Util.formatarData(dataInicialVencimento);
		}

		if(dataFinalVencimento != null){
			if(!Util.isVazioOuBranco(pPeriodoVencimento)){
				pPeriodoVencimento = pPeriodoVencimento + " a ";
			}

			pPeriodoVencimento = pPeriodoVencimento + Util.formatarData(dataFinalVencimento);
		}

		// Período de Pagamento
		String pPeriodoPagamento = "";

		Date dataInicialPagamento = boletoBancarioHelper.getDataInicialPagamento();
		Date dataFinalPagamento = boletoBancarioHelper.getDataFinalPagamento();

		if(dataInicialPagamento != null){
			pPeriodoPagamento = Util.formatarData(dataInicialPagamento);
		}

		if(dataFinalPagamento != null){
			if(!Util.isVazioOuBranco(pPeriodoPagamento)){
				pPeriodoPagamento = pPeriodoPagamento + " a ";
			}

			pPeriodoPagamento = pPeriodoPagamento + Util.formatarData(dataFinalPagamento);
		}

		// Período Crédito
		String pPeriodoCredito = "";

		Date dataInicialCredito = boletoBancarioHelper.getDataInicialCredito();
		Date dataFinalCredito = boletoBancarioHelper.getDataFinalCredito();

		if(dataInicialCredito != null){
			pPeriodoCredito = Util.formatarData(dataInicialCredito);
		}

		if(dataFinalCredito != null){
			if(!Util.isVazioOuBranco(pPeriodoCredito)){
				pPeriodoCredito = pPeriodoCredito + " a ";
			}

			pPeriodoCredito = pPeriodoCredito + Util.formatarData(dataFinalCredito);
		}

		// Período de Cancelamento
		String pPeriodoCancelamento = "";

		Date dataInicialCancelamento = boletoBancarioHelper.getDataInicialCancelamento();
		Date dataFinalCancelamento = boletoBancarioHelper.getDataFinalCancelamento();

		if(dataInicialCancelamento != null){
			pPeriodoCancelamento = Util.formatarData(dataInicialCancelamento);
		}

		if(dataFinalCancelamento != null){
			if(!Util.isVazioOuBranco(pPeriodoCancelamento)){
				pPeriodoCancelamento = pPeriodoCancelamento + " a ";
			}

			pPeriodoCancelamento = pPeriodoCancelamento + Util.formatarData(dataFinalCancelamento);
		}

		// Motivo Cancelamento do Boleto
		String pMotivoCancelamentoBoleto = "";

		String[] idsMotivoCancelamento = boletoBancarioHelper.getIdsMotivoCancelamento();

		if(!Util.isVazioOrNulo(idsMotivoCancelamento)){
			FiltroBoletoBancarioMotivoCancelamento filtroMotivoCancelamento = null;
			Collection<BoletoBancarioSituacao> colecaoMotivoCancelamento = null;
			BoletoBancarioMotivoCancelamento motivoCancelamento = null;

			for(String idMotivoCancelamento : idsMotivoCancelamento){
				filtroMotivoCancelamento = new FiltroBoletoBancarioMotivoCancelamento();
				filtroMotivoCancelamento.adicionarParametro(new ParametroSimples(FiltroBoletoBancarioMotivoCancelamento.ID,
								idMotivoCancelamento));

				colecaoMotivoCancelamento = fachada.pesquisar(filtroMotivoCancelamento, BoletoBancarioMotivoCancelamento.class.getName());

				if(!Util.isVazioOrNulo(colecaoMotivoCancelamento)){
					motivoCancelamento = (BoletoBancarioMotivoCancelamento) Util.retonarObjetoDeColecao(colecaoMotivoCancelamento);
					String descricao = motivoCancelamento.getDescricao();

					pMotivoCancelamentoBoleto = pMotivoCancelamentoBoleto + ";" + descricao;
				}
			}

			if(!Util.isVazioOuBranco(pMotivoCancelamentoBoleto)){
				pMotivoCancelamentoBoleto = pMotivoCancelamentoBoleto.substring(1, pMotivoCancelamentoBoleto.length());

				if(pMotivoCancelamentoBoleto.length() > 100){
					// Truncando a descrição
					pMotivoCancelamentoBoleto = pMotivoCancelamentoBoleto.substring(0, 100);
					pMotivoCancelamentoBoleto = pMotivoCancelamentoBoleto.substring(0, pMotivoCancelamentoBoleto.lastIndexOf(";")) + " ...";
				}
			}
		}

		Map parametros = new HashMap();
		parametros.put("imagem", sistemaParametro.getImagemRelatorio());
		parametros.put("arrecadador", pArrecadador);
		parametros.put("numeroBoleto", pNumeroBoleto);
		parametros.put("numeroBoletoCartaCobranca", pNumeroBoletoCartaCobranca);
		parametros.put("matriculaImovel", pMatriculaImovel);
		parametros.put("clienteResponsavel", pClienteResponsavel);
		parametros.put("situacaoBoleto", pSituacaoBoleto);
		parametros.put("tipoDocumentoBoleto", pTipoDocumentoBoleto);
		parametros.put("periodoEntrada", pPeriodoEntrada);
		parametros.put("periodoPagamento", pPeriodoPagamento);
		parametros.put("periodoVencimento", pPeriodoVencimento);
		parametros.put("periodoCredito", pPeriodoCredito);
		parametros.put("periodoCancelamento", pPeriodoCancelamento);
		parametros.put("motivoCancelamentoBoleto", pMotivoCancelamentoBoleto);
		parametros.put("valorTotalBoleto", pValorTotalBoleto);
		parametros.put("valorTotalPago", pValorTotalPago);

		RelatorioDataSource dataSource = new RelatorioDataSource((List) relatorioBeans);

		byte[] retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_BOLETOS_BANCARIOS, parametros, dataSource, tipoFormatoRelatorio);

		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();

		try{
			persistirRelatorioConcluido(retorno, Relatorio.RELATORIO_BOLETOS_BANCARIOS, idFuncionalidadeIniciada, null);
		}catch(ControladorException ex){
			ex.printStackTrace();
			throw new TarefaException("Erro ao gravar relatório no sistema", ex);
		}

		return retorno;
	}

	public int calcularTotalRegistrosRelatorio(){

		return -1;
	}

	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioBoletosBancarios", this);
	}

}
