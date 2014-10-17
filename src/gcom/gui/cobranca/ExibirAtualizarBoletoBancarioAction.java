
package gcom.gui.cobranca;

import gcom.arrecadacao.Arrecadador;
import gcom.arrecadacao.ArrecadadorMovimentoItem;
import gcom.arrecadacao.pagamento.GuiaPagamento;
import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoSituacao;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.cliente.ClienteGuiaPagamento;
import gcom.cadastro.cliente.ClienteGuiaPagamentoHistorico;
import gcom.cadastro.cliente.ClienteRelacaoTipo;
import gcom.cadastro.cliente.FiltroClienteGuiaPagamento;
import gcom.cadastro.cliente.FiltroClienteGuiaPagamentoHistorico;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.imovel.ImovelPerfil;
import gcom.cobranca.BoletoBancario;
import gcom.cobranca.BoletoBancarioLancamentoEnvio;
import gcom.cobranca.BoletoBancarioMotivoCancelamento;
import gcom.cobranca.BoletoBancarioMovimentacao;
import gcom.cobranca.BoletoBancarioOcorrencias;
import gcom.cobranca.BoletoBancarioSituacao;
import gcom.cobranca.BoletoBancarioSituacaoHistorico;
import gcom.cobranca.DocumentoTipo;
import gcom.cobranca.FiltroBoletoBancarioLancamentoEnvio;
import gcom.cobranca.FiltroBoletoBancarioMovimentacao;
import gcom.cobranca.FiltroBoletoBancarioOcorrencias;
import gcom.cobranca.FiltroBoletoBancarioSituacaoHistorico;
import gcom.cobranca.parcelamento.FiltroParcelamento;
import gcom.cobranca.parcelamento.Parcelamento;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.gui.faturamento.bean.GuiaPagamentoPrestacaoHelper;
import gcom.util.ConstantesAplicacao;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC3023] Manter Boleto Bancário
 * 
 * @author Hebert Falcão
 * @date 12/10/2011
 */
public class ExibirAtualizarBoletoBancarioAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("atualizarBoletoBancario");

		// Obtém a sessão
		HttpSession sessao = httpServletRequest.getSession(false);

		AtualizarBoletoBancarioActionForm form = (AtualizarBoletoBancarioActionForm) actionForm;

		// Obtém a instância da fachada
		Fachada fachada = Fachada.getInstancia();

		String idBoletoBancarioStr = (String) sessao.getAttribute("idBoletoBancario");

		if(!Util.isVazioOuBranco(idBoletoBancarioStr)){
			sessao.setAttribute("voltarAtualizar", "filtrar");
		}else{
			idBoletoBancarioStr = httpServletRequest.getParameter("idBoletoBancario");

			if(Util.isVazioOuBranco(idBoletoBancarioStr)){
				idBoletoBancarioStr = form.getIdBoletoBancario();
			}

			sessao.setAttribute("voltarAtualizar", "manter");
		}

		if(Util.isVazioOuBranco(idBoletoBancarioStr)){
			throw new ActionServletException("atencao.id_boleto_bancario_nao_informado");
		}

		// Apagando variáveis da sessão
		sessao.removeAttribute("indicadorAtualizar");
		sessao.removeAttribute("boletoBancarioAtualizacao");
		sessao.removeAttribute("colecaoImovelSubcategorias");
		sessao.removeAttribute("colecaoClientesImovel");
		sessao.removeAttribute("colecaoBoletoBancarioContaHelper");
		sessao.removeAttribute("colecaoGuiaPagamentoPrestacao");
		sessao.removeAttribute("colecaoBoletoBancarioMovimentacaoHelper");
		sessao.removeAttribute("colecaoBoletoBancarioLancamentoEnvio");

		form.setIdBoletoBancario(idBoletoBancarioStr);

		Integer idBoletoBancario = Integer.valueOf(idBoletoBancarioStr);

		// Pesquisa o Boleto Bancário pelo Id
		BoletoBancario boletoBancario = fachada.pesquisarBoletoBancarioPeloId(idBoletoBancario);

		if(boletoBancario == null){
			throw new ActionServletException("atencao.atualizacao.timestamp");
		}

		sessao.setAttribute("boletoBancarioAtualizacao", boletoBancario);

		// Verifica se permite alteração
		Short permitirAlteracao = ConstantesSistema.SIM;

		// Boleto Cancelado
		BoletoBancarioMotivoCancelamento boletoBancarioMotivoCancelamento = boletoBancario.getBoletoBancarioMotivoCancelamento();

		// Boleto com movimentação de envio pendete de envio, ou enviado, mas sem retorno
		Integer qtdMovPendenteOuSemRetorno = fachada.pesquisarQuantidadeMovimentacaoPendenteOuSemRetorno(idBoletoBancario);

		if(boletoBancarioMotivoCancelamento != null || qtdMovPendenteOuSemRetorno != 0){
			permitirAlteracao = ConstantesSistema.NAO;
		}

		form.setPermitirAlteracao(permitirAlteracao.toString());

		// 1.1. Arrecadador
		Arrecadador arrecadador = boletoBancario.getArrecadador();

		if(arrecadador != null){
			String codigoAgenteArrecadadorStr = "";
			Short codigoAgenteArrecadador = arrecadador.getCodigoAgente();

			if(codigoAgenteArrecadador != null){
				codigoAgenteArrecadadorStr = codigoAgenteArrecadador.toString();
			}

			form.setCodigoAgenteArrecadador(codigoAgenteArrecadadorStr);

			String nomeAgenteArrecadador = "";
			Cliente clienteArrecadador = arrecadador.getCliente();

			if(clienteArrecadador != null){
				nomeAgenteArrecadador = clienteArrecadador.getNome();
			}

			form.setNomeAgenteArrecadador(nomeAgenteArrecadador);
		}

		// 1.2. Número do Boleto Bancário
		String numeroSequencialBoletoBancarioStr = "";
		Integer numeroSequencialBoletoBancario = boletoBancario.getNumeroSequencial();

		if(numeroSequencialBoletoBancario != null){
			numeroSequencialBoletoBancarioStr = numeroSequencialBoletoBancario.toString();
		}

		form.setNumeroSequencialBoletoBancario(numeroSequencialBoletoBancarioStr);

		// 1.3. Imóvel
		Imovel imovel = boletoBancario.getImovel();

		if(imovel != null){
			Integer idImovel = imovel.getId();
			form.setIdImovel(idImovel.toString());

			//
			// 1.4. Dados do Imóvel
			//

			// 1.4.1. Inscrição
			String inscricaoFormatadaImovel = imovel.getInscricaoFormatada();
			form.setInscricaoFormatadaImovel(inscricaoFormatadaImovel);

			// 1.4.3. Situação de Água
			String descricaoLigacaoAguaSituacao = "";
			LigacaoAguaSituacao ligacaoAguaSituacao = imovel.getLigacaoAguaSituacao();

			if(ligacaoAguaSituacao != null){
				descricaoLigacaoAguaSituacao = ligacaoAguaSituacao.getDescricao();
			}

			form.setDescricaoLigacaoAguaSituacao(descricaoLigacaoAguaSituacao);

			// 1.4.4. Situação de Esgoto
			String descricaoLigacaoEsgotoSituacao = "";
			LigacaoEsgotoSituacao ligacaoEsgotoSituacao = imovel.getLigacaoEsgotoSituacao();

			if(ligacaoEsgotoSituacao != null){
				descricaoLigacaoEsgotoSituacao = ligacaoEsgotoSituacao.getDescricao();
			}

			form.setDescricaoLigacaoEsgotoSituacao(descricaoLigacaoEsgotoSituacao);

			// 1.4.5. Perfil do Imóvel
			String descricaoImovelPerfil = "";
			ImovelPerfil imovelPerfil = imovel.getImovelPerfil();

			if(imovelPerfil != null){
				descricaoImovelPerfil = imovelPerfil.getDescricao();
			}

			form.setDescricaoImovelPerfil(descricaoImovelPerfil);

			// 1.4.6. Endereço do Imóvel
			String enderecoImovel = fachada.pesquisarEndereco(idImovel);
			form.setEnderecoImovel(enderecoImovel);

			// 1.4.7. Lista das subcategorias e quantidades de economias do imóvel
			Collection colecaoImovelSubcategorias = fachada.pesquisarCategoriasImovel(idImovel);
			sessao.setAttribute("colecaoImovelSubcategorias", colecaoImovelSubcategorias);

			// 1.4.8. Lista dos clientes associados ao imóvel
			Collection colecaoClientesImovel = fachada.pesquisarClientesImovel(idImovel);
			sessao.setAttribute("colecaoClientesImovel", colecaoClientesImovel);
		}

		//
		// 1.5. Dados do Boleto
		//

		// 1.5.1. Nosso Número
		String nossoNumero = boletoBancario.getNossoNumero();
		form.setNossoNumero(nossoNumero);

		String ocorrenciaMigracao = boletoBancario.getOcorrenciaMigracao();
		form.setOcorrenciaMigracao(ocorrenciaMigracao);

		// Boleto Legado GCS
		String mensagemBoletoLegado = "";
		Integer numeroSequencialArquivoMigracao = boletoBancario.getNumeroSequencialArquivoMigracao();

		if(numeroSequencialArquivoMigracao != null){
			mensagemBoletoLegado = ConstantesAplicacao.get("mensagem.boleto.legado.gcs");
		}

		form.setMensagemBoletoLegado(mensagemBoletoLegado);

		// 1.5.2. Referência do Faturamento
		String anoMesReferenciaBoletoBancarioStr = "";
		Integer anoMesReferenciaBoletoBancario = boletoBancario.getAnoMesReferencia();

		if(anoMesReferenciaBoletoBancario != null){
			anoMesReferenciaBoletoBancarioStr = Util.formatarAnoMesParaMesAno(anoMesReferenciaBoletoBancario);
		}

		form.setAnoMesReferenciaBoletoBancario(anoMesReferenciaBoletoBancarioStr);

		// 1.5.3. Data de Entrada
		FiltroBoletoBancarioSituacaoHistorico filtroSituacaoHistorico = new FiltroBoletoBancarioSituacaoHistorico();
		filtroSituacaoHistorico.adicionarParametro(new ParametroSimples(FiltroBoletoBancarioSituacaoHistorico.BOLETO_BANCARIO_ID,
						idBoletoBancario));
		filtroSituacaoHistorico.adicionarParametro(new ParametroSimples(FiltroBoletoBancarioSituacaoHistorico.BOLETO_BANCARIO_SITUACAO_ID,
						BoletoBancarioSituacao.GERADO_NAO_ENVIADO_AO_BANCO));

		Collection<BoletoBancarioSituacaoHistorico> colecaoSituacaoHistorico = fachada.pesquisar(filtroSituacaoHistorico,
						BoletoBancarioSituacaoHistorico.class.getName());

		if(!Util.isVazioOrNulo(colecaoSituacaoHistorico)){
			BoletoBancarioSituacaoHistorico boletoBancarioSituacaoHistorico = (BoletoBancarioSituacaoHistorico) Util
							.retonarObjetoDeColecao(colecaoSituacaoHistorico);
			String dataEntradaBoletoBancarioStr = "";
			Date dataEntradaBoletoBancario = boletoBancarioSituacaoHistorico.getDataEntrada();

			if(dataEntradaBoletoBancario != null){
				dataEntradaBoletoBancarioStr = Util.formatarData(dataEntradaBoletoBancario);
			}

			form.setDataEntradaBoletoBancario(dataEntradaBoletoBancarioStr);
		}

		// 1.5.4. Data de Vencimento
		String dataVencimentoBoletoBancarioStr = "";
		Date dataVencimentoBoletoBancario = boletoBancario.getDataVencimento();

		if(dataVencimentoBoletoBancario != null){
			dataVencimentoBoletoBancarioStr = Util.formatarData(dataVencimentoBoletoBancario);
		}

		form.setDataVencimentoBoletoBancario(dataVencimentoBoletoBancarioStr);

		// 1.5.5. Valor
		String valorDebitoBoletoBancarioStr = "";
		BigDecimal valorDebitoBoletoBancario = boletoBancario.getValorDebito();

		if(valorDebitoBoletoBancario != null){
			valorDebitoBoletoBancarioStr = Util.formatarMoedaReal(valorDebitoBoletoBancario);
		}

		form.setValorDebitoBoletoBancario(valorDebitoBoletoBancarioStr);

		// 1.5.6. Situação Atual
		BoletoBancarioSituacao boletoBancarioSituacao = boletoBancario.getBoletoBancarioSituacao();

		if(boletoBancarioSituacao != null){
			Integer idSituacaoBoletoBancario = boletoBancarioSituacao.getId();
			form.setIdSituacaoBoletoBancario(idSituacaoBoletoBancario.toString());

			String descricaoSituacaoBoletoBancario = boletoBancarioSituacao.getDescricao();
			form.setDescricaoSituacaoBoletoBancario(descricaoSituacaoBoletoBancario);
		}

		//
		// 1.5.7. Dados do Documento do Boleto
		//

		// Tipo do Documento
		DocumentoTipo documentoTipoBoletoBancario = boletoBancario.getDocumentoTipo();

		if(documentoTipoBoletoBancario != null){
			// 1.5.7.1. Descrição Tipo do Documento
			String descricaoDocumentoTipoBoletoBancario = documentoTipoBoletoBancario.getDescricaoDocumentoTipo();
			form.setDescricaoDocumentoTipoBoletoBancario(descricaoDocumentoTipoBoletoBancario);

			// Verifica Tipo de Documento Associado
			Integer idDocumentoTipoBoletoBancario = documentoTipoBoletoBancario.getId();

			if(idDocumentoTipoBoletoBancario.equals(DocumentoTipo.DOCUMENTO_DE_COBRANCA)){
				Collection<BoletoBancarioContaHelper> colecaoBoletoBancarioContaHelper = fachada
								.pesquisarContasPeloBoletoBancario(idBoletoBancario);

				if(!Util.isVazioOrNulo(colecaoBoletoBancarioContaHelper)){
					sessao.setAttribute("colecaoBoletoBancarioContaHelper", colecaoBoletoBancarioContaHelper);

					// Totalizador
					BigDecimal valorTotalDebitoConta = BigDecimal.ZERO;
					BigDecimal valorTotalPagoConta = BigDecimal.ZERO;

					String valorPagoStr = null;
					BigDecimal valorPago = null;

					for(BoletoBancarioContaHelper boletoBancarioContaHelper : colecaoBoletoBancarioContaHelper){
						valorTotalDebitoConta = valorTotalDebitoConta.add(boletoBancarioContaHelper.getValorItemCobrado());
						valorTotalDebitoConta = valorTotalDebitoConta.add(boletoBancarioContaHelper.getValorAcrescimos());

						valorPagoStr = boletoBancarioContaHelper.getValorPago();

						if(!Util.isVazioOuBranco(valorPagoStr)){
							valorPago = Util.formatarMoedaRealparaBigDecimal(valorPagoStr);

							if(valorPago != null){
								valorTotalPagoConta = valorTotalPagoConta.add(valorPago);
							}
						}
					}

					sessao.setAttribute("valorTotalDebitoConta", Util.formatarMoedaReal(valorTotalDebitoConta));
					sessao.setAttribute("valorTotalPagoConta", Util.formatarMoedaReal(valorTotalPagoConta));
				}
			}else if(idDocumentoTipoBoletoBancario.equals(DocumentoTipo.GUIA_PAGAMENTO)){
				// 1.5.7.3. Guia de Pagamento
				GuiaPagamento guiaPagamento = boletoBancario.getGuiaPagamento();
				Integer idGuiaPagamento = guiaPagamento.getId();

				Integer numeroPrestacoes = boletoBancario.getNumeroPrestacoes();

				Collection<GuiaPagamentoPrestacaoHelper> colecaoGuiaPagamentoPrestacao = fachada.pesquisarGuiasPagamentoPrestacaoBoleto(
								idGuiaPagamento, numeroPrestacoes);

				if(!Util.isVazioOrNulo(colecaoGuiaPagamentoPrestacao)){
					Integer idCliente = null;

					// Pesquisa o Id do Cliente
					FiltroClienteGuiaPagamento filtroClienteGuiaPagamento = new FiltroClienteGuiaPagamento();
					filtroClienteGuiaPagamento.adicionarParametro(new ParametroSimples(FiltroClienteGuiaPagamento.GUIA_PAGAMENTO_ID,
									idGuiaPagamento));
					filtroClienteGuiaPagamento.adicionarParametro(new ParametroSimples(FiltroClienteGuiaPagamento.CLIENTE_RELACAO_TIPO_ID,
									ClienteRelacaoTipo.USUARIO));

					Collection<ClienteGuiaPagamento> colecaoClienteGuiaPagamento = fachada.pesquisar(filtroClienteGuiaPagamento,
									ClienteGuiaPagamento.class.getName());

					if(!Util.isVazioOrNulo(colecaoClienteGuiaPagamento)){
						ClienteGuiaPagamento clienteGuiaPagamento = (ClienteGuiaPagamento) Util
										.retonarObjetoDeColecao(colecaoClienteGuiaPagamento);

						Cliente cliente = clienteGuiaPagamento.getCliente();
						idCliente = cliente.getId();
					}else{
						// Histórico
						FiltroClienteGuiaPagamentoHistorico filtroClienteGuiaPagamentoHistorico = new FiltroClienteGuiaPagamentoHistorico();
						filtroClienteGuiaPagamentoHistorico.adicionarParametro(new ParametroSimples(
										FiltroClienteGuiaPagamentoHistorico.GUIA_PAGAMENTO_ID, idGuiaPagamento));
						filtroClienteGuiaPagamentoHistorico.adicionarParametro(new ParametroSimples(
										FiltroClienteGuiaPagamentoHistorico.CLIENTE_RELACAO_TIPO_ID, ClienteRelacaoTipo.USUARIO));

						Collection<ClienteGuiaPagamentoHistorico> colecaoClienteGuiaPagamentoHistorico = fachada.pesquisar(
										filtroClienteGuiaPagamentoHistorico, ClienteGuiaPagamentoHistorico.class.getName());

						if(!Util.isVazioOrNulo(colecaoClienteGuiaPagamentoHistorico)){
							ClienteGuiaPagamentoHistorico clienteGuiaPagamentoHistorico = (ClienteGuiaPagamentoHistorico) Util
											.retonarObjetoDeColecao(colecaoClienteGuiaPagamentoHistorico);

							Cliente cliente = clienteGuiaPagamentoHistorico.getCliente();
							idCliente = cliente.getId();
						}
					}

					if(idCliente != null){
						for(GuiaPagamentoPrestacaoHelper guiaPagamentoPrestacao : colecaoGuiaPagamentoPrestacao){
							guiaPagamentoPrestacao.setIdCliente(idCliente);
						}
					}

					sessao.setAttribute("colecaoGuiaPagamentoPrestacao", colecaoGuiaPagamentoPrestacao);
				}
			}
		}

		// 1.5.8. Movimentações
		Collection<BoletoBancarioMovimentacaoHelper> colecaoBoletoBancarioMovimentacaoHelper = null;

		FiltroBoletoBancarioMovimentacao filtroMovimentacao = new FiltroBoletoBancarioMovimentacao();
		filtroMovimentacao.adicionarCaminhoParaCarregamentoEntidade(FiltroBoletoBancarioMovimentacao.BOLETO_BANCARIO_LANCAMENTO_ENVIO);
		filtroMovimentacao.adicionarCaminhoParaCarregamentoEntidade(FiltroBoletoBancarioMovimentacao.BOLETO_BANCARIO_LANCAMENTO_RETORNO);
		filtroMovimentacao.adicionarCaminhoParaCarregamentoEntidade(FiltroBoletoBancarioMovimentacao.BOLETO_BANCARIO_MOVIMENTACAO_RETORNO);
		filtroMovimentacao.adicionarCaminhoParaCarregamentoEntidade(FiltroBoletoBancarioMovimentacao.ARRECADADOR_MOVIMENTO_ITEM);
		filtroMovimentacao.adicionarParametro(new ParametroSimples(FiltroBoletoBancarioMovimentacao.BOLETO_BANCARIO_ID, idBoletoBancario));
		filtroMovimentacao.setCampoOrderBy(FiltroBoletoBancarioMovimentacao.DATA_MOVIMENTACAO_ORDENACAO_DESC);

		Collection<BoletoBancarioMovimentacao> colecaoBoletoBancarioMovimentacao = fachada.pesquisar(filtroMovimentacao,
						BoletoBancarioMovimentacao.class.getName());

		if(!Util.isVazioOrNulo(colecaoBoletoBancarioMovimentacao)){
			colecaoBoletoBancarioMovimentacaoHelper = new ArrayList();

			BoletoBancarioMovimentacaoHelper boletoBancarioMovimentacaoHelper = null;

			FiltroBoletoBancarioOcorrencias filtroBoletoBancarioOcorrencias = null;

			for(BoletoBancarioMovimentacao boletoBancarioMovimentacao : colecaoBoletoBancarioMovimentacao){
				Integer idMovimentacao = boletoBancarioMovimentacao.getId();

				boletoBancarioMovimentacaoHelper = new BoletoBancarioMovimentacaoHelper();
				boletoBancarioMovimentacaoHelper.setBoletoBancarioMovimentacao(boletoBancarioMovimentacao);

				// 1.5.8.1.4. [SB0005 - Determinar Situação do Envio]
				BoletoBancarioLancamentoEnvio boletoBancarioLancamentoEnvio = boletoBancarioMovimentacao.getBoletoBancarioLancamentoEnvio();

				if(boletoBancarioLancamentoEnvio != null){
					String situacaoEnvio = this.determinarSituacaoDoEnvio(boletoBancario, boletoBancarioMovimentacao);
					boletoBancarioMovimentacaoHelper.setSituacaoEnvio(situacaoEnvio);
				}

				// 1.5.8.1.5. Ocorrências da Movimentação
				filtroBoletoBancarioOcorrencias = new FiltroBoletoBancarioOcorrencias();
				filtroBoletoBancarioOcorrencias.adicionarParametro(new ParametroSimples(
								FiltroBoletoBancarioOcorrencias.BOLETO_BANCARIO_MOVIMENTACAO_ID, idMovimentacao));
				filtroBoletoBancarioOcorrencias
								.adicionarCaminhoParaCarregamentoEntidade(FiltroBoletoBancarioOcorrencias.BOLETO_BANCARIO_MOTIVO_OCORRENCIA);
				filtroBoletoBancarioOcorrencias
								.setCampoOrderBy(FiltroBoletoBancarioOcorrencias.BOLETO_BANCARIO_MOTIVO_OCORRENCIA_DESCRICAO);

				Collection<BoletoBancarioOcorrencias> colecaoBoletoBancarioOcorrencias = fachada.pesquisar(filtroBoletoBancarioOcorrencias,
								BoletoBancarioOcorrencias.class.getName());

				boletoBancarioMovimentacaoHelper.setColecaoBoletoBancarioOcorrencias(colecaoBoletoBancarioOcorrencias);

				colecaoBoletoBancarioMovimentacaoHelper.add(boletoBancarioMovimentacaoHelper);
			}
		}

		sessao.setAttribute("colecaoBoletoBancarioMovimentacaoHelper", colecaoBoletoBancarioMovimentacaoHelper);

		// 1.5.9. Dados de Pagamento do Boleto

		BigDecimal valorPagoBoletoBancario = boletoBancario.getValorPago();

		// 1.5.9.1. Agência do Pagamento
		String agenciaPagamento = this.obterAgenciaDoPagamento(idBoletoBancario, valorPagoBoletoBancario);
		form.setAgenciaPagamento(agenciaPagamento);

		// 1.5.9.2. Data do Pagamento
		String dataPagamentoBoletoBancarioStr = "";
		Date dataPagamentoBoletoBancario = boletoBancario.getDataPagamento();

		if(dataPagamentoBoletoBancario != null){
			dataPagamentoBoletoBancarioStr = Util.formatarData(dataPagamentoBoletoBancario);
		}

		form.setDataPagamentoBoletoBancario(dataPagamentoBoletoBancarioStr);

		// 1.5.9.3. Data do Crédito
		String dataCreditoBoletoBancarioStr = "";
		Date dataCreditoBoletoBancario = boletoBancario.getDataCredito();

		if(dataCreditoBoletoBancario != null){
			dataCreditoBoletoBancarioStr = Util.formatarData(dataCreditoBoletoBancario);
		}

		form.setDataCreditoBoletoBancario(dataCreditoBoletoBancarioStr);

		// 1.5.9.4. Valor Pago
		String valorPagoBoletoBancarioStr = "";

		if(valorPagoBoletoBancario != null){
			valorPagoBoletoBancarioStr = Util.formatarMoedaReal(valorPagoBoletoBancario);
		}

		form.setValorPagoBoletoBancario(valorPagoBoletoBancarioStr);

		// 1.5.9.5. Valor Tarifa
		String valorTarifaBoletoBancarioStr = "";
		BigDecimal valorTarifaBoletoBancario = boletoBancario.getValorTarifa();

		if(valorTarifaBoletoBancario != null){
			valorTarifaBoletoBancarioStr = Util.formatarMoedaReal(valorTarifaBoletoBancario);
		}

		form.setValorTarifaBoletoBancario(valorTarifaBoletoBancarioStr);

		// 1.5.9.6. Valor Multa/Juros
		String valorMultaJurosBoletoBancarioStr = "";

		if(valorPagoBoletoBancario != null && valorDebitoBoletoBancario != null
						&& valorPagoBoletoBancario.compareTo(valorDebitoBoletoBancario) == 1){
			BigDecimal valorMultaJurosBoletoBancario = valorPagoBoletoBancario.subtract(valorDebitoBoletoBancario);
			valorMultaJurosBoletoBancarioStr = Util.formatarMoedaReal(valorMultaJurosBoletoBancario);
		}

		form.setValorMultaJurosBoletoBancario(valorMultaJurosBoletoBancarioStr);

		//
		// 1.5.10. Dados de Cancelamento do Boleto
		//

		// 1.5.10.1. Data do Cancelamento
		String dataCancelamentoBoletoBancarioStr = "";
		Date dataCancelamentoBoletoBancario = boletoBancario.getDataCancelamento();

		if(dataCancelamentoBoletoBancario != null){
			dataCancelamentoBoletoBancarioStr = Util.formatarData(dataCancelamentoBoletoBancario);
		}

		form.setDataCancelamentoBoletoBancario(dataCancelamentoBoletoBancarioStr);

		// 1.5.10.2. Motivo do Cancelamento
		String descricaoMotivoCancelamentoBoletoBancario = "";

		if(boletoBancarioMotivoCancelamento != null){
			descricaoMotivoCancelamentoBoletoBancario = boletoBancarioMotivoCancelamento.getDescricao();
		}

		form.setDescricaoMotivoCancelamentoBoletoBancario(descricaoMotivoCancelamentoBoletoBancario);

		//
		// 1.5.11. Dados do Parcelamento do Boleto
		//
		if(boletoBancario.getParcelamento() != null){

			Integer parcelamento_ID = boletoBancario.getParcelamento().getId();

			FiltroParcelamento filtroParcelamento = new FiltroParcelamento();

			filtroParcelamento.adicionarParametro(new ParametroSimples(FiltroParcelamento.ID, parcelamento_ID));

			filtroParcelamento.adicionarCaminhoParaCarregamentoEntidade("parcelamentoSituacao");

			filtroParcelamento.adicionarCaminhoParaCarregamentoEntidade("cobrancaForma");

			Collection<Parcelamento> colecaoParcelamento = fachada.pesquisar(filtroParcelamento, Parcelamento.class.getName());

			if(!Util.isVazioOrNulo(colecaoParcelamento)){
				httpServletRequest.setAttribute("colecaoParcelamento", colecaoParcelamento);
			}
		}

		//
		// 1.6. Dados da Alteração
		//

		if(permitirAlteracao == ConstantesSistema.SIM){

			// 1.6.1. Lançamento de Envio
			FiltroBoletoBancarioLancamentoEnvio filtroLancamentoEnvio = new FiltroBoletoBancarioLancamentoEnvio(
							FiltroBoletoBancarioLancamentoEnvio.DESCRICAO_LANCAMENTO_ENVIO);
			filtroLancamentoEnvio.adicionarParametro(new ParametroSimples(FiltroBoletoBancarioLancamentoEnvio.INDICADOR_COMANDO,
							ConstantesSistema.SIM));
			filtroLancamentoEnvio.adicionarParametro(new ParametroSimples(FiltroBoletoBancarioLancamentoEnvio.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			Collection<BoletoBancarioLancamentoEnvio> colecaoBoletoBancarioLancamentoEnvio = fachada.pesquisar(filtroLancamentoEnvio,
							BoletoBancarioLancamentoEnvio.class.getName());

			// FS0003 - Verifica existência de dados
			if(Util.isVazioOrNulo(colecaoBoletoBancarioLancamentoEnvio)){
				throw new ActionServletException("atencao.entidade_sem_dados_para_selecao", null, "Boleto Bancário Lançamento Envio");
			}

			sessao.setAttribute("colecaoBoletoBancarioLancamentoEnvio", colecaoBoletoBancarioLancamentoEnvio);
		}

		return retorno;
	}

	// [SB0005 - Determinar Situação do Envio]
	private String determinarSituacaoDoEnvio(BoletoBancario boletoBancario, BoletoBancarioMovimentacao boletoBancarioMovimentacao){

		String retorno = "";

		Integer numeroSequencialArquivoMigracao = boletoBancario.getNumeroSequencialArquivoMigracao();

		if(numeroSequencialArquivoMigracao != null){
			// Boleto legado de sistema
			retorno = "Enviado";
		}else{
			ArrecadadorMovimentoItem arrecadadorMovimentoItem = boletoBancarioMovimentacao.getArrecadadorMovimentoItem();

			if(arrecadadorMovimentoItem != null){
				BoletoBancarioMovimentacao boletoBancarioMovimentacaoRetorno = boletoBancarioMovimentacao
								.getBoletoBancarioMovimentacaoRetorno();

				if(boletoBancarioMovimentacaoRetorno != null){
					retorno = "Enviado e Com Retorno";
				}else{
					retorno = "Enviado e Sem Retorno";
				}

			}else{
				retorno = "Não Enviado";
			}

		}

		return retorno;
	}

	// [SB0006 - Obter Agência do Pagamento]
	private String obterAgenciaDoPagamento(Integer idBoletoBancario, BigDecimal valorPagoBoletoBancario){

		// Obtém a instância da fachada
		Fachada fachada = Fachada.getInstancia();

		String retorno = "";

		if(valorPagoBoletoBancario != null){
			String conteudoRegistro = fachada.pesquisarConteudoArrecadadorMovimentoItem(idBoletoBancario);

			if(!Util.isVazioOuBranco(conteudoRegistro)){
				System.out.println(conteudoRegistro.substring(100, 106));
				retorno = conteudoRegistro.substring(100, 104);

				Integer conteudoAux = Integer.valueOf(retorno);

				if(conteudoAux == 0){
					retorno = conteudoRegistro.substring(18, 22);
				}
			}
		}

		return retorno;
	}

}
