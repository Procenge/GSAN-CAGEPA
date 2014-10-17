
package gcom.relatorio.cobranca.cobrancaadministrativa;

import gcom.batch.Relatorio;
import gcom.cadastro.empresa.Empresa;
import gcom.cadastro.imovel.ImovelCobrancaAdministrivaItem;
import gcom.cadastro.imovel.ImovelCobrancaMotivoRetirada;
import gcom.cadastro.imovel.ImovelCobrancaSituacao;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cobranca.CobrancaAcaoAtividadeComando;
import gcom.cobranca.CobrancaDebitoSituacao;
import gcom.cobranca.DocumentoTipo;
import gcom.cobranca.bean.ItensRemuradosHelper;
import gcom.fachada.Fachada;
import gcom.gui.cobranca.CobrancaAdministrativaHelper;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.math.BigDecimal;
import java.util.*;

/**
 * [UC3060] Manter Im�vel Cobran�a Administrativa
 * 
 * @author Carlos Chrystian Ramos
 * @date 25/02/2013
 */
public class RelatorioImoveisEmCobrancaAdministrativa
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	/**
	 * Construtor da classe RelatorioImoveisEmCobrancaAdministrativa
	 */
	public RelatorioImoveisEmCobrancaAdministrativa(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_RELACAO_IMOVEIS_EM_COBRANCA_ADMINISTRATIVA);
	}

	@Deprecated
	public RelatorioImoveisEmCobrancaAdministrativa() {

		super(null, "");
	}

	public Object executar() throws TarefaException{

		// Obt�m a inst�ncia da fachada
		Fachada fachada = Fachada.getInstancia();

		// Cole��o de beans do relat�rio
		List relatorioBeans = new ArrayList();

		// Par�metros da tela de filtro
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");
		// Valor total do documento
		BigDecimal valorTotalDocumento = BigDecimal.ZERO;

		// Recupera a cole��o de im�veis em cobran�a administrativa.
		Collection<ImovelCobrancaSituacao> colecaoImovelCobrancaSituacao = (Collection<ImovelCobrancaSituacao>) getParametro("colecaoImovelCobrancaSituacao");

		if(!Util.isVazioOrNulo(colecaoImovelCobrancaSituacao)){
			Integer codigoSitucao = null;
			String pendente = "";
			String pago = "";
			String parcelado = "";
			String cancelado = "";
			String semDebitos = "";
			String executado = "";
			String excluidos = "";
			Integer qtdPendente = 0;
			Integer qtdPago = 0;
			Integer qtdParcelado = 0;
			Integer qtdCancelado = 0;
			Integer qtdSemDebitos = 0;
			Integer qtdExecutado = 0;
			Integer qtdExcluidos = 0;
			BigDecimal vlPendente = BigDecimal.ZERO;
			BigDecimal vlPago = BigDecimal.ZERO;
			BigDecimal vlParcelado = BigDecimal.ZERO;
			BigDecimal vlCancelado = BigDecimal.ZERO;
			BigDecimal vlSemDebitos = BigDecimal.ZERO;
			BigDecimal vlExecutado = BigDecimal.ZERO;
			BigDecimal vlExcluidos = BigDecimal.ZERO;
			int count = 0;

			// Bean do relat�rio e sub-relat�rio
			RelatorioImoveisEmCobrancaAdministrativaBean relatorioImoveisEmCobrancaAdministrativaBean = null;
			RelatorioImoveisEmCobrancaAdministrativaDetalheContaBean relImoveisEmCobrancaAdministrativaDetalheContaBean = null;
			RelatorioImoveisEmCobrancaAdministrativaDetalheGuiaBean relImoveisEmCobrancaAdministrativaDetalheGuiaBean = null;
			RelatorioImoveisEmCobrancaAdministrativaDetalheDebitoBean relImoveisEmCobrancaAdministrativaDetalheDebitoBean = null;
			RelatorioImoveisEmCobrancaAdministrativaDetalheRemuneracaoBean relatorioImoveisEmCobrancaAdministrativaDetalheRemuneracaoBean = null;

			// Cole��o do sub-relat�rio de contas
			Collection<RelatorioImoveisEmCobrancaAdministrativaDetalheContaBean> colecaoRelatorioImoveisEmCobrancaAdministrativaDetalheContaBean = null;

			// Cole��o do sub-relat�rio de guias
			Collection<RelatorioImoveisEmCobrancaAdministrativaDetalheGuiaBean> colecaoRelatorioImoveisEmCobrancaAdministrativaDetalheGuiaBean = null;

			// Cole��o do sub-relat�rio de guias
			Collection<RelatorioImoveisEmCobrancaAdministrativaDetalheDebitoBean> colecaoRelatorioImoveisEmCobrancaAdministrativaDetalheDebitoBean = null;

			// Cole��o do sub-relat�rio de remunera��o
			Collection<RelatorioImoveisEmCobrancaAdministrativaDetalheRemuneracaoBean> colecaoRelatorioImoveisEmCobrancaAdministrativaDetalheRemuneracaoBean = null;

			int qtdOcorrenciasRemuneracaoRealizada = 0;
			int qtdOcorrenciasRemuneracaoPendente = 0;

			BigDecimal valorItemCobradoRemuneracaoRealizada = BigDecimal.ZERO;
			BigDecimal valorItemCobradoRemuneracaoPendente = BigDecimal.ZERO;

			for(ImovelCobrancaSituacao imovelCobrancaSituacao : colecaoImovelCobrancaSituacao){

				if(!Util.isVazioOuBranco(imovelCobrancaSituacao)){
					// 1.3. Para cada im�vel em cobran�a administrativa, emitir os seguintes dados:
					// 1.3.1. Im�vel
					relatorioImoveisEmCobrancaAdministrativaBean = new RelatorioImoveisEmCobrancaAdministrativaBean();

					relatorioImoveisEmCobrancaAdministrativaBean.setIdImovel(imovelCobrancaSituacao.getImovel().getId().toString());

					// 1.3.2. Empresa
					CobrancaAcaoAtividadeComando cobrancaAcaoAtividadeComando = imovelCobrancaSituacao.getCobrancaAcaoAtividadeComando();

					if(cobrancaAcaoAtividadeComando != null){
						Empresa empresa = cobrancaAcaoAtividadeComando.getEmpresa();

						if(empresa != null){
							String descricaoEmpresa = empresa.getDescricao();
							relatorioImoveisEmCobrancaAdministrativaBean.setNomeEmpresa(descricaoEmpresa);
						}
					}

					// 1.3.3. Data da Implanta��o Cobran�a
					Date dataImplantacaoCobranca = imovelCobrancaSituacao.getDataImplantacaoCobranca();

					if(dataImplantacaoCobranca != null){
						String dataImplantacao = Util.formatarData(dataImplantacaoCobranca);
						relatorioImoveisEmCobrancaAdministrativaBean.setDataImplantacaoCobranca(dataImplantacao.toString());
					}

					// 1.3.4. Data da Retirada Cobran�a
					Date dataRetiradaCobranca = imovelCobrancaSituacao.getDataRetiradaCobranca();

					if(dataRetiradaCobranca != null){
						String dataRetirada = Util.formatarData(dataRetiradaCobranca);
						relatorioImoveisEmCobrancaAdministrativaBean.setDataRetiradaCobranca(dataRetirada.toString());

						// 1.3.5. Motivo da Retirada da Cobran�a
						ImovelCobrancaMotivoRetirada imovelCobrancaMotivoRetirada = imovelCobrancaSituacao
										.getImovelCobrancaMotivoRetirada();

						if(imovelCobrancaMotivoRetirada != null){
							String descricaoImovelCobrancaMotivoRetirada = imovelCobrancaMotivoRetirada.getDescricao();
							relatorioImoveisEmCobrancaAdministrativaBean
											.setDescricaoMotivoRetiradaCobranca(descricaoImovelCobrancaMotivoRetirada);
						}
					}

					// 1.3.6. Data da Adimpl�ncia
					Date dataAdimplencia = imovelCobrancaSituacao.getDataAdimplencia();

					if(dataAdimplencia != null){
						String dataAdimplenciaStr = Util.formatarData(dataAdimplencia);
						relatorioImoveisEmCobrancaAdministrativaBean.setDataAdimplencia(dataAdimplenciaStr.toString());

						// 1.3.7. Motivo da Adimpl�ncia
						CobrancaDebitoSituacao cobrancaDebitoSituacao = imovelCobrancaSituacao.getCobrancaDebitoSituacao();

						if(!Util.isVazioOuBranco(cobrancaDebitoSituacao)){
							String descricaoCobrancaDebitoSituacao = cobrancaDebitoSituacao.getDescricao();
							relatorioImoveisEmCobrancaAdministrativaBean.setDescricaoMotivoAdimplencia(descricaoCobrancaDebitoSituacao);
						}
					}

					// 1.3.8. Qtde. Itens Marcados
					Integer quantidadeDebito = imovelCobrancaSituacao.getQuantidadeDebito();

					if(quantidadeDebito != null){
						String quantidadeDebitoStr = Integer.toString(quantidadeDebito);
						relatorioImoveisEmCobrancaAdministrativaBean.setQuantidadeItensMarcados(quantidadeDebitoStr);
					}

					// 1.3.9. Valor Itens Marcados
					BigDecimal valorDebito = imovelCobrancaSituacao.getValorDebito();

					if(valorDebito != null){
						String valorDebitoStr = Util.formatarMoedaReal(valorDebito);
						relatorioImoveisEmCobrancaAdministrativaBean.setValorItensMarcados(valorDebitoStr);
					}

					// 1.3.10. Qtde. Itens Remuner�veis
					List<ItensRemuradosHelper> listaItensRemunerados = fachada.selecionarItensRemureraveis(imovelCobrancaSituacao);
					if(Util.isNaoNuloBrancoZero(listaItensRemunerados)){
						// relatorioImoveisEmCobrancaAdministrativaBean.setQuantidadeItensRemuneraveis(listaItensRemunerados.size()
						// + "");

						BigDecimal valorTotalItensRemuneraveis = BigDecimal.ZERO;

						for(ItensRemuradosHelper itensRemuradosHelper : listaItensRemunerados){
							valorTotalItensRemuneraveis = valorTotalItensRemuneraveis.add(itensRemuradosHelper.getValorDaBaseRemuneracao());
						}

						// 1.3.11. Valor Itens Remuner�veis
						relatorioImoveisEmCobrancaAdministrativaBean.setValorItensRemuneraveis(Util
										.formatarMoedaReal(valorTotalItensRemuneraveis));

					}

					// 1.3.12. Situa��o Atual
					String descricaoSituacaoAtualDebito = imovelCobrancaSituacao.getSituacaoAtual();

					if(descricaoSituacaoAtualDebito != null){
						relatorioImoveisEmCobrancaAdministrativaBean.setDescricaoSituacaoAtualDebito(descricaoSituacaoAtualDebito);
					}

					// 1.3.13. Dados dos Itens Vinculados � Cobran�a Administrativa
					// 1.3.11. Dados dos Itens de D�bito
					// 1.3.11.1. Para cada grupo de itens com o mesmo tipo de documento (DOTP_ID)
					Integer idCobrancaAcaoAtividadeComando = null;
					Integer idImovel = imovelCobrancaSituacao.getImovel().getId();

					if(cobrancaAcaoAtividadeComando != null && idImovel != null){
						idCobrancaAcaoAtividadeComando = cobrancaAcaoAtividadeComando.getId();
					}

					// 1.3.11.1.1. Exibir a descri��o do tipo de documento
					if(idCobrancaAcaoAtividadeComando != null && idImovel != null){
						// 1.3.11.1.2.1. Caso o tipo de documento corresponda � �CONTA�
						// Collection<CobrancaAdministrativaContaHelper>
						// colecaoCobrancaAdministrativaContaHelper = fachada
						// .pesquisarContasPeloComandoParaCobrancaAdministrativa(idCobrancaAcaoAtividadeComando,
						// idImovel);

						List<CobrancaAdministrativaHelper> colecaoCobrancaAdministrativaHelper = fachada
										.obterDadosDaConta(listaItensRemunerados);

						if(!Util.isVazioOrNulo(colecaoCobrancaAdministrativaHelper)){
							// Totalizador D�bito Conta
							BigDecimal valorParcialDebitoConta = BigDecimal.ZERO;
							BigDecimal valorTotalDebitoConta = BigDecimal.ZERO;
							int quantidadeRegistros = 0;

							// Limpando a cole��o
							colecaoRelatorioImoveisEmCobrancaAdministrativaDetalheContaBean = null;

							// Inicializando a cole��o
							colecaoRelatorioImoveisEmCobrancaAdministrativaDetalheContaBean = new ArrayList<RelatorioImoveisEmCobrancaAdministrativaDetalheContaBean>();

							for(CobrancaAdministrativaHelper cobrancaAdministrativaHelper : colecaoCobrancaAdministrativaHelper){
								// Contador de registros processados
								quantidadeRegistros = quantidadeRegistros + 1;

								relImoveisEmCobrancaAdministrativaDetalheContaBean = new RelatorioImoveisEmCobrancaAdministrativaDetalheContaBean();

								relImoveisEmCobrancaAdministrativaDetalheContaBean.setReferenciaConta(cobrancaAdministrativaHelper
												.getReferenciaConta() == null ? "" : Util
												.formatarAnoMesParaMesAno(cobrancaAdministrativaHelper.getReferenciaConta()));
								relImoveisEmCobrancaAdministrativaDetalheContaBean.setDataVencimentoConta(cobrancaAdministrativaHelper
												.getDataVencimentoConta() == null ? "" : Util.formatarData(cobrancaAdministrativaHelper
												.getDataVencimentoConta()));
								relImoveisEmCobrancaAdministrativaDetalheContaBean
												.setValorConta(cobrancaAdministrativaHelper.getValorDebito() == null ? BigDecimal.ZERO
																: cobrancaAdministrativaHelper.getValorDebito());
								relImoveisEmCobrancaAdministrativaDetalheContaBean.setDescricaoSituacaoDebito(cobrancaAdministrativaHelper
												.getSituacaoItem() == null ? "" : cobrancaAdministrativaHelper.getSituacaoItem());
								relImoveisEmCobrancaAdministrativaDetalheContaBean.setDataSituacaoDebito(cobrancaAdministrativaHelper
												.getDataSituacao() == null ? "" : Util.formatarData(cobrancaAdministrativaHelper
												.getDataSituacao()));

								valorTotalDebitoConta = valorTotalDebitoConta.add(cobrancaAdministrativaHelper.getValorDebito());
								// valorTotalDebitoConta =
								// valorTotalDebitoConta.add(cobrancaAdministrativaHelper.getValorAcrescimos());

								// Quebra da cole��o principal para exibi��o na tela de 10 registros
								if(quantidadeRegistros <= 5){
									valorParcialDebitoConta = valorParcialDebitoConta
													.add(relImoveisEmCobrancaAdministrativaDetalheContaBean.getValorConta());
									colecaoRelatorioImoveisEmCobrancaAdministrativaDetalheContaBean
													.add(relImoveisEmCobrancaAdministrativaDetalheContaBean);
								}
							}
							// Soma o valor total das contas com o valor total dos documentos.
							valorTotalDocumento = valorTotalDocumento.add(valorTotalDebitoConta);
							// Insere valor parcial d�bito conta no Bean
							relatorioImoveisEmCobrancaAdministrativaBean.setValorParcialContas(Util
											.formatarMoedaReal(valorParcialDebitoConta));
							// Insere valor total d�bito conta no Bean
							relatorioImoveisEmCobrancaAdministrativaBean.setValorTotalDebitoConta(Util
											.formatarMoedaReal(valorTotalDebitoConta));
							// Insere cole��o no Bean
							relatorioImoveisEmCobrancaAdministrativaBean
											.setColecaoRelatorioImoveisEmCobrancaAdministrativaDetalheContaBean(colecaoRelatorioImoveisEmCobrancaAdministrativaDetalheContaBean);

						}

						// 1.3.13.1.2.2. Caso o tipo de documento corresponda � �GUIA DE PAGAMENTO�
						List<CobrancaAdministrativaHelper> colecaoCobrancaAdministrativaHelperGuia = fachada
										.obterDadosDaGuiaPagamento(listaItensRemunerados);

						// Collection<CobrancaAdministrativaGuiaHelper>
						// colecaoCobrancaAdministrativaGuiaHelper = fachada
						// .pesquisarGuiasPeloComandoParaCobrancaAdministrativa(idCobrancaAcaoAtividadeComando,
						// idImovel);

						if(!Util.isVazioOrNulo(colecaoCobrancaAdministrativaHelperGuia)){
							// Totalizador D�bito Guia
							BigDecimal valorParcialDebitoGuia = BigDecimal.ZERO;
							BigDecimal valorTotalDebitoGuia = BigDecimal.ZERO;
							int quantidadeRegistros = 0;

							// Limpando a cole��o
							colecaoRelatorioImoveisEmCobrancaAdministrativaDetalheGuiaBean = null;

							// Inicializando a cole��o
							colecaoRelatorioImoveisEmCobrancaAdministrativaDetalheGuiaBean = new ArrayList<RelatorioImoveisEmCobrancaAdministrativaDetalheGuiaBean>();

							for(CobrancaAdministrativaHelper cobrancaAdministrativaHelper : colecaoCobrancaAdministrativaHelperGuia){
								// Contador de registros processados
								quantidadeRegistros = quantidadeRegistros + 1;

								relImoveisEmCobrancaAdministrativaDetalheGuiaBean = new RelatorioImoveisEmCobrancaAdministrativaDetalheGuiaBean();

								relImoveisEmCobrancaAdministrativaDetalheGuiaBean
												.setIdGuia(cobrancaAdministrativaHelper.getIdGuiaPamento() == null ? ""
																: cobrancaAdministrativaHelper.getIdGuiaPamento().toString());
								relImoveisEmCobrancaAdministrativaDetalheGuiaBean.setNumeroPrestacaoGuia(cobrancaAdministrativaHelper
												.getPrestacao() == null ? "" : cobrancaAdministrativaHelper.getPrestacao()
												.toString());
								relImoveisEmCobrancaAdministrativaDetalheGuiaBean.setDataEmissao(cobrancaAdministrativaHelper
												.getDataEmissao() == null ? "" : Util.formatarData(cobrancaAdministrativaHelper
												.getDataEmissao()));
								relImoveisEmCobrancaAdministrativaDetalheGuiaBean.setDescricaoTipoDebito(cobrancaAdministrativaHelper
												.getTipoDebito() == null ? "" : cobrancaAdministrativaHelper.getTipoDebito());
								relImoveisEmCobrancaAdministrativaDetalheGuiaBean
												.setValorPrestacao(cobrancaAdministrativaHelper.getValorDebito() == null ? BigDecimal.ZERO
																: cobrancaAdministrativaHelper.getValorDebito());
								relImoveisEmCobrancaAdministrativaDetalheGuiaBean.setDescricaoSituacaoDebito(cobrancaAdministrativaHelper
												.getSituacaoItem() == null ? "" : cobrancaAdministrativaHelper.getSituacaoItem());

								relImoveisEmCobrancaAdministrativaDetalheGuiaBean.setDataSituacaoDebito(cobrancaAdministrativaHelper
												.getDataSituacao() == null ? "" : Util.formatarData(cobrancaAdministrativaHelper
												.getDataSituacao()));

								valorTotalDebitoGuia = valorTotalDebitoGuia.add(cobrancaAdministrativaHelper.getValorDebito());
								// valorTotalDebitoGuia =
								// valorTotalDebitoGuia.add(cobrancaAdministrativaHelper.getValorAcrescimos());

								// Quebra da cole��o principal
								if(quantidadeRegistros <= 5){
									valorParcialDebitoGuia = valorParcialDebitoGuia.add(relImoveisEmCobrancaAdministrativaDetalheGuiaBean
													.getValorPrestacao());
									colecaoRelatorioImoveisEmCobrancaAdministrativaDetalheGuiaBean
													.add(relImoveisEmCobrancaAdministrativaDetalheGuiaBean);
								}
							}
							// Soma o valor total da guia com o valor total do documento.
							valorTotalDocumento = valorTotalDocumento.add(valorTotalDebitoGuia);
							// Insere valor parcial d�bito guia no Bean
							relatorioImoveisEmCobrancaAdministrativaBean.setValorParcialGuias(Util
											.formatarMoedaReal(valorParcialDebitoGuia));
							// Insere valor total d�bito guia no Bean
							relatorioImoveisEmCobrancaAdministrativaBean.setValorTotalDebitoGuia(Util
											.formatarMoedaReal(valorTotalDebitoGuia));
							// Insere cole��o no Bean
							relatorioImoveisEmCobrancaAdministrativaBean
											.setColecaoRelatorioImoveisEmCobrancaAdministrativaDetalheGuiaBean(colecaoRelatorioImoveisEmCobrancaAdministrativaDetalheGuiaBean);
						}

						// 1.3.13.1.2.3. Caso o tipo de documento corresponda � �DEBITO A COBRAR�
						List<CobrancaAdministrativaHelper> colecaoCobrancaAdministrativaDebitoHelper = fachada
										.obterDadosDoDebitoACobrar(listaItensRemunerados);

						if(!Util.isVazioOrNulo(colecaoCobrancaAdministrativaDebitoHelper)){
							// Totalizador D�bito
							BigDecimal valorParcialDebito = BigDecimal.ZERO;
							BigDecimal valorTotalDebito = BigDecimal.ZERO;
							int quantidadeRegistros = 0;

							// Limpando a cole��o
							colecaoRelatorioImoveisEmCobrancaAdministrativaDetalheDebitoBean = null;

							// Inicializando a cole��o
							colecaoRelatorioImoveisEmCobrancaAdministrativaDetalheDebitoBean = new ArrayList<RelatorioImoveisEmCobrancaAdministrativaDetalheDebitoBean>();

							for(CobrancaAdministrativaHelper cobrancaAdministrativaHelper : colecaoCobrancaAdministrativaDebitoHelper){
								// Contador de registros processados
								quantidadeRegistros = quantidadeRegistros + 1;

								relImoveisEmCobrancaAdministrativaDetalheDebitoBean = new RelatorioImoveisEmCobrancaAdministrativaDetalheDebitoBean();

								relImoveisEmCobrancaAdministrativaDetalheDebitoBean.setReferenciaDebito(cobrancaAdministrativaHelper
												.getReferenciaDebito() == null ? "" : Util
												.formatarAnoMesParaMesAno(cobrancaAdministrativaHelper.getReferenciaDebito()));
								relImoveisEmCobrancaAdministrativaDetalheDebitoBean.setTipoDebito(cobrancaAdministrativaHelper
												.getTipoDebito() == null ? "" : cobrancaAdministrativaHelper.getTipoDebito());
								relImoveisEmCobrancaAdministrativaDetalheDebitoBean
												.setParcela(cobrancaAdministrativaHelper.getPrestacao() == null ? ""
																: cobrancaAdministrativaHelper.getPrestacao().toString());
								relImoveisEmCobrancaAdministrativaDetalheDebitoBean
												.setValorDebito(cobrancaAdministrativaHelper.getValorDebito() == null ? BigDecimal.ZERO
																: cobrancaAdministrativaHelper.getValorDebito());
								relImoveisEmCobrancaAdministrativaDetalheDebitoBean
												.setValorRestanteASerCobrado(cobrancaAdministrativaHelper
												.getValorRestanteASerCobrado() == null ? BigDecimal.ZERO : cobrancaAdministrativaHelper
												.getValorRestanteASerCobrado());
								relImoveisEmCobrancaAdministrativaDetalheDebitoBean.setDescricaoSituacaoDebito(cobrancaAdministrativaHelper
												.getSituacaoItem() == null ? "" : cobrancaAdministrativaHelper.getSituacaoItem());

								relImoveisEmCobrancaAdministrativaDetalheDebitoBean.setDataSituacaoDebito(cobrancaAdministrativaHelper
												.getDataSituacao() == null ? "" : Util.formatarData(cobrancaAdministrativaHelper
												.getDataSituacao()));

								valorTotalDebito = valorTotalDebito.add(cobrancaAdministrativaHelper.getValorDebito());
								// valorTotalDebitoGuia =
								// valorTotalDebitoGuia.add(cobrancaAdministrativaHelper.getValorAcrescimos());

								// Quebra da cole��o principal
								if(quantidadeRegistros <= 5){
									valorParcialDebito = valorParcialDebito.add(relImoveisEmCobrancaAdministrativaDetalheDebitoBean
													.getValorDebito());
									colecaoRelatorioImoveisEmCobrancaAdministrativaDetalheDebitoBean
													.add(relImoveisEmCobrancaAdministrativaDetalheDebitoBean);
								}
							}
							// Soma o valor total dos debitos com o valor total dos documentos
							valorTotalDocumento = valorTotalDocumento.add(valorTotalDebito);
							// Insere valor parcial d�bito guia no Bean
							relatorioImoveisEmCobrancaAdministrativaBean.setValorParcialDebitoACobrar(Util
											.formatarMoedaReal(valorParcialDebito));
							// Insere valor total d�bito guia no Bean
							relatorioImoveisEmCobrancaAdministrativaBean.setValorTotalDebitoACobrar(Util
											.formatarMoedaReal(valorTotalDebito));
							// Insere cole��o no Bean
							relatorioImoveisEmCobrancaAdministrativaBean
											.setColecaoRelatorioImoveisEmCobrancaAdministrativaDetalheDebitoBean(colecaoRelatorioImoveisEmCobrancaAdministrativaDetalheDebitoBean);
						}

						// 1.3.12. Dados da Remunera��o:
						// 1.3.12.1. Percentual de Remunera��o Padr�o do Im�vel
						BigDecimal percentualRemuneracao = imovelCobrancaSituacao.getPercentualRemuneracao();

						relatorioImoveisEmCobrancaAdministrativaDetalheRemuneracaoBean = new RelatorioImoveisEmCobrancaAdministrativaDetalheRemuneracaoBean();

						if(percentualRemuneracao != null){
							String percentualRemuneracaoStr = Util.formataBigDecimal(percentualRemuneracao, 2, true);
							relatorioImoveisEmCobrancaAdministrativaBean.setPercentualRemuneracaoPadrao(percentualRemuneracaoStr);
						}

						// 1.3.12.2. Obt�m dados da remunera��o do im�vel
						// 1.3.12.3. Para cada conjunto de itens de d�bito com o mesmo tipo de
						// remunera��o, emitir grid �Remunera��o� com Tipo de Remunera��o, o
						// Percentual e o Valor:
						// Limpando a cole��o
						colecaoRelatorioImoveisEmCobrancaAdministrativaDetalheRemuneracaoBean = null;

						// Inicializando a cole��o
						colecaoRelatorioImoveisEmCobrancaAdministrativaDetalheRemuneracaoBean = new ArrayList<RelatorioImoveisEmCobrancaAdministrativaDetalheRemuneracaoBean>();

						Collection<Object[]> colecaoImovelCobAdmTotalizado = fachada
										.totalizarImovelCobrancaAdmPelaSituacaoCobranca(imovelCobrancaSituacao.getId());

						if(!Util.isVazioOrNulo(colecaoImovelCobAdmTotalizado)){
							for(Object[] dadosRemuneracao : colecaoImovelCobAdmTotalizado){
								relatorioImoveisEmCobrancaAdministrativaDetalheRemuneracaoBean = new RelatorioImoveisEmCobrancaAdministrativaDetalheRemuneracaoBean();

								// 1.3.12.3.1. Tipo de Remunera��o:
								relatorioImoveisEmCobrancaAdministrativaDetalheRemuneracaoBean
												.setTipoRemuneracao((String) dadosRemuneracao[0]);
								// 1.3.12.3.2. Percentual:
								relatorioImoveisEmCobrancaAdministrativaDetalheRemuneracaoBean
												.setPercentualRemuneracao((String) dadosRemuneracao[1]);
								// 1.3.12.3.3. Valor:
								relatorioImoveisEmCobrancaAdministrativaDetalheRemuneracaoBean
												.setValorRemuneracao((String) dadosRemuneracao[2]);

								// Insere valores na cole��o
								colecaoRelatorioImoveisEmCobrancaAdministrativaDetalheRemuneracaoBean
												.add(relatorioImoveisEmCobrancaAdministrativaDetalheRemuneracaoBean);
							}
							// Insere cole��o no Bean
							relatorioImoveisEmCobrancaAdministrativaBean
											.setColecaoRelatorioImoveisEmCobrancaAdministrativaDetalheRemuneracaoBean(colecaoRelatorioImoveisEmCobrancaAdministrativaDetalheRemuneracaoBean);
						}else{
							// Exibe tabela com valores zerados para o relat�rio
							// 1.3.12.3.1. Tipo de Remunera��o PADR�O
							RelatorioImoveisEmCobrancaAdministrativaDetalheRemuneracaoBean relatorioImoveisEmCobrancaAdministrativaDetalheRemuneracaoBean1 = new RelatorioImoveisEmCobrancaAdministrativaDetalheRemuneracaoBean();
							relatorioImoveisEmCobrancaAdministrativaDetalheRemuneracaoBean1
											.setTipoRemuneracao(ImovelCobrancaAdministrivaItem.PADRAO);
							relatorioImoveisEmCobrancaAdministrativaDetalheRemuneracaoBean1.setPercentualRemuneracao(Util
											.formataBigDecimal(BigDecimal.ZERO, 2, true));
							relatorioImoveisEmCobrancaAdministrativaDetalheRemuneracaoBean1.setValorRemuneracao(Util.formataBigDecimal(
											BigDecimal.ZERO, 2, true));

							// 1.3.12.3.1. Tipo de Remunera��o PARCELAMENTO
							RelatorioImoveisEmCobrancaAdministrativaDetalheRemuneracaoBean relatorioImoveisEmCobrancaAdministrativaDetalheRemuneracaoBean2 = new RelatorioImoveisEmCobrancaAdministrativaDetalheRemuneracaoBean();
							relatorioImoveisEmCobrancaAdministrativaDetalheRemuneracaoBean2
											.setTipoRemuneracao(ImovelCobrancaAdministrivaItem.PARCELAMENTO);
							relatorioImoveisEmCobrancaAdministrativaDetalheRemuneracaoBean2.setPercentualRemuneracao(Util
											.formataBigDecimal(BigDecimal.ZERO, 2, true));
							relatorioImoveisEmCobrancaAdministrativaDetalheRemuneracaoBean2.setValorRemuneracao(Util.formataBigDecimal(
											BigDecimal.ZERO, 2, true));

							// 1.3.12.3.1. Tipo de Remunera��o ESPECIAL
							RelatorioImoveisEmCobrancaAdministrativaDetalheRemuneracaoBean relatorioImoveisEmCobrancaAdministrativaDetalheRemuneracaoBean3 = new RelatorioImoveisEmCobrancaAdministrativaDetalheRemuneracaoBean();
							relatorioImoveisEmCobrancaAdministrativaDetalheRemuneracaoBean3
											.setTipoRemuneracao(ImovelCobrancaAdministrivaItem.ESPECIAL);
							relatorioImoveisEmCobrancaAdministrativaDetalheRemuneracaoBean3.setPercentualRemuneracao(Util
											.formataBigDecimal(BigDecimal.ZERO, 2, true));
							relatorioImoveisEmCobrancaAdministrativaDetalheRemuneracaoBean3.setValorRemuneracao(Util.formataBigDecimal(
											BigDecimal.ZERO, 2, true));
							RelatorioImoveisEmCobrancaAdministrativaDetalheRemuneracaoBean relatorioImoveisEmCobrancaAdministrativaDetalheRemuneracaoBean4 = new RelatorioImoveisEmCobrancaAdministrativaDetalheRemuneracaoBean();

							// 1.3.12.3.1. Tipo de Remunera��o REINCIDENCIA
							relatorioImoveisEmCobrancaAdministrativaDetalheRemuneracaoBean4
											.setTipoRemuneracao(ImovelCobrancaAdministrivaItem.REINCIDENCIA);
							relatorioImoveisEmCobrancaAdministrativaDetalheRemuneracaoBean4.setPercentualRemuneracao(Util
											.formataBigDecimal(BigDecimal.ZERO, 2, true));
							relatorioImoveisEmCobrancaAdministrativaDetalheRemuneracaoBean4.setValorRemuneracao(Util.formataBigDecimal(
											BigDecimal.ZERO, 2, true));

							// Insere valores na cole��o
							colecaoRelatorioImoveisEmCobrancaAdministrativaDetalheRemuneracaoBean
											.add(relatorioImoveisEmCobrancaAdministrativaDetalheRemuneracaoBean1);
							colecaoRelatorioImoveisEmCobrancaAdministrativaDetalheRemuneracaoBean
											.add(relatorioImoveisEmCobrancaAdministrativaDetalheRemuneracaoBean2);
							colecaoRelatorioImoveisEmCobrancaAdministrativaDetalheRemuneracaoBean
											.add(relatorioImoveisEmCobrancaAdministrativaDetalheRemuneracaoBean3);
							colecaoRelatorioImoveisEmCobrancaAdministrativaDetalheRemuneracaoBean
											.add(relatorioImoveisEmCobrancaAdministrativaDetalheRemuneracaoBean4);
							// Insere cole��o no Bean
							relatorioImoveisEmCobrancaAdministrativaBean
											.setColecaoRelatorioImoveisEmCobrancaAdministrativaDetalheRemuneracaoBean(colecaoRelatorioImoveisEmCobrancaAdministrativaDetalheRemuneracaoBean);
						}
					}

					// Acumulador da Situa��o dos D�bitos
					if(Util.isNaoNuloBrancoZero(listaItensRemunerados)){
						List<String> listaDebitoACobrar = new ArrayList<String>();
						BigDecimal valorTotal = BigDecimal.ZERO;
						Collection<Object[]> colecaoItensRemunerados = new ArrayList<Object[]>();
						Object[] itensRemunerados = null;
						int contRemuneracaoRealizada = 0;
						int contRemuneracaoPendente = 0;
						BigDecimal totalDaBaseRemuneracaoRealizada = BigDecimal.ZERO;
						BigDecimal totalDaRemuneracaoRealizada = BigDecimal.ZERO;
						BigDecimal totalDaBaseRemuneracaoPendente = BigDecimal.ZERO;
						BigDecimal totalDaRemuneracaoPendente = BigDecimal.ZERO;

						for(ItensRemuradosHelper helper : listaItensRemunerados){

							if(helper.getIndicadorRemuneracaoRealizada().equals(ConstantesSistema.SIM)){
								if(helper.getIdDocumentoTipo().equals(DocumentoTipo.DEBITO_A_COBRAR)){
									if(!listaDebitoACobrar.contains(helper.getIdentificacaoItem())){
										listaDebitoACobrar.add(helper.getIdentificacaoItem());
										contRemuneracaoRealizada++;
									}
								}else{
									contRemuneracaoRealizada++;
								}
								totalDaBaseRemuneracaoRealizada = totalDaBaseRemuneracaoRealizada.add(helper.getValorDaBaseRemuneracao());
								totalDaRemuneracaoRealizada = totalDaRemuneracaoRealizada.add((BigDecimal) helper.getValorDaRemuneracao());
							}else{
								contRemuneracaoPendente++;
								totalDaBaseRemuneracaoPendente = totalDaBaseRemuneracaoPendente.add(helper.getValorDaBaseRemuneracao());
								totalDaRemuneracaoPendente = totalDaRemuneracaoPendente.add((BigDecimal) helper.getValorDaRemuneracao());
							}
						}

						relatorioImoveisEmCobrancaAdministrativaBean.setQuantidadeItensRemuneraveis(String.valueOf(contRemuneracaoRealizada
										+ contRemuneracaoPendente));

						if(contRemuneracaoRealizada > 0){
							qtdOcorrenciasRemuneracaoRealizada = qtdOcorrenciasRemuneracaoRealizada + contRemuneracaoRealizada;
							valorItemCobradoRemuneracaoRealizada = valorItemCobradoRemuneracaoRealizada
											.add(totalDaBaseRemuneracaoRealizada);
						}

						if(contRemuneracaoPendente > 0){
							qtdOcorrenciasRemuneracaoPendente = qtdOcorrenciasRemuneracaoPendente + contRemuneracaoPendente;
							valorItemCobradoRemuneracaoPendente = valorItemCobradoRemuneracaoPendente.add(totalDaBaseRemuneracaoPendente);
						}
					}
				}

				// Adiciona �tem na cole��o
				relatorioBeans.add(relatorioImoveisEmCobrancaAdministrativaBean);
			}

			if(relatorioImoveisEmCobrancaAdministrativaBean != null){
				Collection<RelatorioImoveisEmCobrancaAdministrativaDetalheSituacaoBean> colecaoRelatorioImoveisEmCobrancaAdministrativaDetalheSituacaoBean = new ArrayList<RelatorioImoveisEmCobrancaAdministrativaDetalheSituacaoBean>();

				RelatorioImoveisEmCobrancaAdministrativaDetalheSituacaoBean relatorioImoveisEmCobrancaAdministrativaDetalheSituacaoBean = null;

				if(qtdOcorrenciasRemuneracaoRealizada > 0){
					relatorioImoveisEmCobrancaAdministrativaDetalheSituacaoBean = new RelatorioImoveisEmCobrancaAdministrativaDetalheSituacaoBean();
					relatorioImoveisEmCobrancaAdministrativaDetalheSituacaoBean.setDescricaoSituacaoDebitoItem("REMUNERA��O REALIZADA");
					relatorioImoveisEmCobrancaAdministrativaDetalheSituacaoBean.setQuantidadeOcorrenciasItem(String
									.valueOf(qtdOcorrenciasRemuneracaoRealizada));
					relatorioImoveisEmCobrancaAdministrativaDetalheSituacaoBean.setValorItemCobrado(Util
									.formatarMoedaReal(valorItemCobradoRemuneracaoRealizada));
					colecaoRelatorioImoveisEmCobrancaAdministrativaDetalheSituacaoBean
									.add(relatorioImoveisEmCobrancaAdministrativaDetalheSituacaoBean);
				}

				if(qtdOcorrenciasRemuneracaoPendente > 0){
					relatorioImoveisEmCobrancaAdministrativaDetalheSituacaoBean = new RelatorioImoveisEmCobrancaAdministrativaDetalheSituacaoBean();
					relatorioImoveisEmCobrancaAdministrativaDetalheSituacaoBean.setDescricaoSituacaoDebitoItem("REMUNERA��O PENDENTE");
					relatorioImoveisEmCobrancaAdministrativaDetalheSituacaoBean.setQuantidadeOcorrenciasItem(String
									.valueOf(qtdOcorrenciasRemuneracaoPendente));
					relatorioImoveisEmCobrancaAdministrativaDetalheSituacaoBean.setValorItemCobrado(Util
									.formatarMoedaReal(valorItemCobradoRemuneracaoPendente));
					colecaoRelatorioImoveisEmCobrancaAdministrativaDetalheSituacaoBean
									.add(relatorioImoveisEmCobrancaAdministrativaDetalheSituacaoBean);
				}

				relatorioImoveisEmCobrancaAdministrativaBean
								.setColecaoRelatorioImoveisEmCobrancaAdministrativaDetalheSituacaoBean(colecaoRelatorioImoveisEmCobrancaAdministrativaDetalheSituacaoBean);

			}
		}

		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

		// ---------------------------
		// Par�metros do relat�rio
		// ---------------------------

		Map parametros = new HashMap();
		parametros.put("imagem", sistemaParametro.getImagemRelatorio());
		parametros.put("P_NM_ESTADO", sistemaParametro.getNomeEstado());
		parametros.put("comando", (String) getParametro("comando"));
		parametros.put("empresa", (String) getParametro("empresa"));
		parametros.put("imovel", (String) getParametro("imovel"));
		parametros.put("cliente", (String) getParametro("cliente"));
		parametros.put("localidadeFinal", (String) getParametro("localidadeFinal"));
		parametros.put("localidadeInicial", (String) getParametro("localidadeInicial"));
		parametros.put("setorComercialFinal", (String) getParametro("setorComercialFinal"));
		parametros.put("setorComercialInicial", (String) getParametro("setorComercialInicial"));
		parametros.put("quadraFinal", (String) getParametro("quadraFinal"));
		parametros.put("quadraInicial", (String) getParametro("quadraInicial"));
		parametros.put("periodoInclusaoFinal", (String) getParametro("periodoInclusaoFinal"));
		parametros.put("periodoInclusaoInicial", (String) getParametro("periodoInclusaoInicial"));
		parametros.put("periodoRetiradaFinal", (String) getParametro("periodoRetiradaFinal"));
		parametros.put("periodoRetiradaInicial", (String) getParametro("periodoRetiradaInicial"));
		parametros.put("valorDebitoFinal", (String) getParametro("valorDebitoFinal"));
		parametros.put("valorDebitoInicial", (String) getParametro("valorDebitoInicial"));
		parametros.put("situacaoCobAdministrativa", (String) getParametro("situacaoCobAdministrativa"));
		parametros.put("gerenciaReginal", (String) getParametro("gerenciaReginal"));
		parametros.put("unidadeDeNegocio", (String) getParametro("unidadeDeNegocio"));
		parametros.put("categoria", (String) getParametro("categoria"));
		parametros.put("subcategoria", (String) getParametro("subcategoria"));
		parametros.put("situacaoLigacaoAgua", (String) getParametro("situacaoLigacaoAgua"));
		parametros.put("situacaoLigacaoEsgoto", (String) getParametro("situacaoLigacaoEsgoto"));
		parametros.put("descricaoMotivosRetirada", (String) getParametro("descricaoMotivosRetirada"));
		parametros.put("arquivoImoveis", (String) getParametro("arquivoImoveis"));
		parametros.put("valorTotalDocumento", Util.formatarMoedaReal(valorTotalDocumento));
		parametros.put("indRemunCobAdmPorComando", (String) getParametro("indRemunCobAdmPorComando"));

		RelatorioDataSource dataSource = new RelatorioDataSource((List) relatorioBeans);

		byte[] retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_RELACAO_IMOVEIS_EM_COBRANCA_ADMINISTRATIVA, parametros, dataSource,
						tipoFormatoRelatorio);

		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();

		try{
			persistirRelatorioConcluido(retorno, Relatorio.RELATORIO_RELACAO_IMOVEIS_EM_COBRANCA_ADMINISTRATIVA, idFuncionalidadeIniciada,
							null);
		}catch(ControladorException ex){
			ex.printStackTrace();
			throw new TarefaException("Erro ao gravar relat�rio no sistema", ex);
		}

		return retorno;
	}

	public int calcularTotalRegistrosRelatorio(){

		return -1;
	}

	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioRelacaoImoveisEmCobrancaAdministrativa", this);
	}

}
