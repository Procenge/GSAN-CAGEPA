
package gcom.gui.cobranca;

import gcom.cobranca.FiltroResolucaoDiretoria;
import gcom.cobranca.ResolucaoDiretoria;
import gcom.cobranca.bean.*;
import gcom.cobranca.parcelamento.Parcelamento;
import gcom.cobranca.parcelamento.ParcelamentoPerfil;
import gcom.fachada.Fachada;
import gcom.faturamento.conta.Conta;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.math.BigDecimal;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC3031] Efetuar Negociação de Débitos Parcelamento Popup
 * 
 * @author Hebert Falcão
 * @date 01/12/2011
 */
public class ExibirEfetuarNegociacaoDebitosParcelamentoPopupAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("exibirEfetuarNegociacaoDebitosParcelamentoPopupAction");

		// Form
		EfetuarNegociacaoDebitosParcelamentoPopupActionForm form = (EfetuarNegociacaoDebitosParcelamentoPopupActionForm) actionForm;

		// Obtém a instância da fachada
		Fachada fachada = Fachada.getInstancia();

		// Obtém a sessão
		HttpSession sessao = httpServletRequest.getSession(false);

		Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");

		NegociacaoDebitoImovelHelper negociacaoDebitoImovelHelper = (NegociacaoDebitoImovelHelper) sessao
						.getAttribute("negociacaoDebitoImovelHelper");

		Collection<NegociacaoDebitoOpcoesRDHelper> colecaoNegociacaoDebitoOpcoesRDHelper = (Collection<NegociacaoDebitoOpcoesRDHelper>) sessao
						.getAttribute("colecaoNegociacaoDebitoOpcoesRDHelper");

		String idResolucaoDiretoriaStr = httpServletRequest.getParameter("idResolucaoDiretoria");

		if(!Util.isVazioOuBranco(idResolucaoDiretoriaStr)){
			idResolucaoDiretoriaStr = form.getIdResolucaoDiretoria();
		}

		Integer idResolucaoDiretoria = Integer.valueOf(idResolucaoDiretoriaStr);

		String calcular = httpServletRequest.getParameter("calcular");
		String selecionarNegociacao = httpServletRequest.getParameter("selecionarNegociacao");

		if(!Util.isVazioOuBranco(selecionarNegociacao)){
			// Negocição selecionada

			// Valor de Entrada
			BigDecimal valorEntradaInformado = null;
			String valorEntradaInformadoStr = form.getValorEntradaInformado();

			if(!Util.isVazioOuBranco(valorEntradaInformadoStr)){
				valorEntradaInformado = Util.formatarMoedaRealparaBigDecimal(valorEntradaInformadoStr);
			}

			BigDecimal valorEntradaInformadoAntes = null;
			String valorEntradaInformadoAntesStr = form.getValorEntradaInformadoAntes();

			if(!Util.isVazioOuBranco(valorEntradaInformadoAntesStr)){
				valorEntradaInformadoAntes = Util.formatarMoedaRealparaBigDecimal(valorEntradaInformadoAntesStr);
			}

			// Verifica se o botão calcular foi acionado quando o valor de entrada for alterado
			if(!valorEntradaInformadoAntes.equals(valorEntradaInformado)){
				throw new ActionServletException("atencao.valor.entrada.alterado.necessario.calcular");
			}

			String numeroResolucaoDiretoria = "";

			String quantidadeParcelasStr = "";
			Short quantidadeParcelas = null;

			String valorParcelaStr = "";
			String valorEntradaStr = "";
			String valorDebitoAposDescontoStr = "";
			String valorTotalJurosFinanciamentoStr = "";

			String numeroMesesEntreParcelasStr = "";
			String numeroParcelasALancarStr = "";
			String numeroMesesInicioCobrancaStr = "";
			String numeroDiasVencimentoDaEntradaStr = "";
			String taxaJurosStr = "";

			// Opção de parcelamento
			OpcoesParcelamentoHelper opcoesParcelamentoHelper = null;

			String indicadorQuantidadeParcelas = form.getIndicadorQuantidadeParcelas();

			if(!Util.isVazioOuBranco(indicadorQuantidadeParcelas)){
				Collection<OpcoesParcelamentoHelper> colecaoOpcoesParcelamento = (Collection<OpcoesParcelamentoHelper>) sessao
								.getAttribute("colecaoOpcoesParcelamento");

				if(!Util.isVazioOrNulo(colecaoOpcoesParcelamento)){
					Short quantidadePrestacao = null;
					String quantidadePrestacaoStr = null;

					for(OpcoesParcelamentoHelper opcoesParcelamentoHelperAux : colecaoOpcoesParcelamento){
						quantidadePrestacao = opcoesParcelamentoHelperAux.getQuantidadePrestacao();
						quantidadePrestacaoStr = Short.toString(quantidadePrestacao);

						if(quantidadePrestacaoStr.equals(indicadorQuantidadeParcelas)){
							opcoesParcelamentoHelper = opcoesParcelamentoHelperAux;
							break;
						}
					}
				}
			}

			if(opcoesParcelamentoHelper != null){
				quantidadeParcelas = opcoesParcelamentoHelper.getQuantidadePrestacao();
				quantidadeParcelasStr = Short.toString(quantidadeParcelas);

				BigDecimal valorParcela = opcoesParcelamentoHelper.getValorPrestacao();
				valorParcelaStr = Util.formatarMoedaReal(valorParcela);

				BigDecimal valorEntrada = opcoesParcelamentoHelper.getValorEntradaMinima();
				valorEntradaStr = Util.formatarMoedaReal(valorEntrada);

				BigDecimal parcelaBD = new BigDecimal(quantidadeParcelas);
				BigDecimal valorDebitoAposDesconto = valorParcela.multiply(parcelaBD).add(valorEntrada);
				valorDebitoAposDescontoStr = Util.formatarMoedaReal(valorDebitoAposDesconto);

				Integer numeroMesesEntreParcelas = opcoesParcelamentoHelper.getNumeroMesesEntreParcelas();

				if(numeroMesesEntreParcelas != null){
					numeroMesesEntreParcelasStr = Integer.toString(numeroMesesEntreParcelas);
				}

				Integer numeroParcelasALancar = opcoesParcelamentoHelper.getNumeroParcelasALancar();

				if(numeroParcelasALancar != null){
					numeroParcelasALancarStr = Integer.toString(numeroParcelasALancar);
				}

				Integer numeroMesesInicioCobranca = opcoesParcelamentoHelper.getNumeroMesesInicioCobranca();

				if(numeroMesesInicioCobranca != null){
					numeroMesesInicioCobrancaStr = Integer.toString(numeroMesesInicioCobranca);
				}

				Integer numeroDiasVencimentoDaEntrada = opcoesParcelamentoHelper.getNumeroDiasVencimentoDaEntrada();

				if(numeroDiasVencimentoDaEntrada != null){
					numeroDiasVencimentoDaEntradaStr = Integer.toString(numeroDiasVencimentoDaEntrada);
				}

				BigDecimal taxaJuros = opcoesParcelamentoHelper.getTaxaJuros();
				taxaJurosStr = Util.formatarMoedaReal(taxaJuros);
			}

			// Opções de Pagamento à Vista
			String indicadorPagamentoCartaoCredito = form.getIndicadorPagamentoCartaoCredito();

			if(!Util.isVazioOuBranco(indicadorPagamentoCartaoCredito)){
				quantidadeParcelas = new Short("0");
				quantidadeParcelasStr = Short.toString(quantidadeParcelas);

				valorParcelaStr = form.getValorPagamentoAVista();

				valorEntradaStr = Util.formatarMoedaReal(BigDecimal.ZERO);

				valorDebitoAposDescontoStr = form.getValorPagamentoAVista();
			}

			// Verifica se selecionou alguma negociação
			if(Util.isVazioOuBranco(indicadorQuantidadeParcelas) && Util.isVazioOuBranco(indicadorPagamentoCartaoCredito)){
				throw new ActionServletException("atencao.negociacao.nao.selecionada");
			}

			// Caso a negociação não seja á vista, é necessário ter uma entrada
			if(valorEntradaInformado.compareTo(BigDecimal.ZERO) == 0 && !quantidadeParcelas.equals(new Short("0"))){
				throw new ActionServletException("atencao.negociacao.sem.entrada");
			}

			// Resolução Diretoria
			FiltroResolucaoDiretoria filtroResolucaoDiretoria = new FiltroResolucaoDiretoria();
			filtroResolucaoDiretoria.adicionarParametro(new ParametroSimples(FiltroResolucaoDiretoria.CODIGO, idResolucaoDiretoriaStr));

			Collection<ResolucaoDiretoria> colecaoResolucaoDiretoria = fachada.pesquisar(filtroResolucaoDiretoria, ResolucaoDiretoria.class
							.getName());

			if(!Util.isVazioOrNulo(colecaoResolucaoDiretoria)){
				ResolucaoDiretoria resolucaoDiretoria = (ResolucaoDiretoria) Util.retonarObjetoDeColecao(colecaoResolucaoDiretoria);
				numeroResolucaoDiretoria = resolucaoDiretoria.getNumeroResolucaoDiretoria();
			}

			EfetuarNegociacaoDebitosActionForm efetuarNegociacaoDebitosActionForm = (EfetuarNegociacaoDebitosActionForm) sessao
							.getAttribute("EfetuarNegociacaoDebitosActionForm");

			// Valor Débito Atualizado
			String valorDebitoTotalAtualizadoStr = form.getValorDebitoTotalAtualizado();
			BigDecimal valorDebitoTotalAtualizado = Util.formatarMoedaRealparaBigDecimal(valorDebitoTotalAtualizadoStr);

			// Valor Juros
			BigDecimal valorDebitoAposDesconto = Util.formatarMoedaRealparaBigDecimal(valorDebitoAposDescontoStr);
			BigDecimal valorTotalJurosFinanciamento = valorDebitoAposDesconto.subtract(valorDebitoTotalAtualizado);

			if(valorTotalJurosFinanciamento.compareTo(BigDecimal.ZERO) < 0){
				valorTotalJurosFinanciamento = BigDecimal.ZERO;
			}

			valorTotalJurosFinanciamentoStr = Util.formatarMoedaReal(valorTotalJurosFinanciamento);

			// Valor Desconto
			String valorDescontoPagamentoAVistaStr = form.getValorDescontoPagamentoAVista();
			String valorDescontoAntiguidadeStr = form.getDescontoAntiguidadeDebito();
			String valorDescontoInatividadeStr = form.getDescontoInatividadeLigacaoAgua();

			BigDecimal valorDescontoAntiguidade = BigDecimal.ZERO;
			BigDecimal valorDescontoInatividade = BigDecimal.ZERO;

			if(!Util.isVazioOuBranco(valorDescontoAntiguidadeStr)){
				valorDescontoAntiguidade = Util.formatarMoedaRealparaBigDecimal(valorDescontoAntiguidadeStr);
			}

			if(!Util.isVazioOuBranco(valorDescontoInatividadeStr)){
				valorDescontoInatividade = Util.formatarMoedaRealparaBigDecimal(valorDescontoInatividadeStr);
			}

			BigDecimal valorTotalDesconto = valorDescontoInatividade.add(valorDescontoAntiguidade);
			String valorTotalDescontoStr = Util.formatarMoedaReal(valorTotalDesconto);

			// Desconto(%)
			BigDecimal percentualTotalDesconto = Util.calcularPercentualBigDecimal(valorTotalDesconto, valorDebitoTotalAtualizado);
			String percentualTotalDescontoStr = Util.formatarMoedaReal(percentualTotalDesconto);

			// Dados da Negociação - Selecionada
			efetuarNegociacaoDebitosActionForm.setNegociacaoIdRD(idResolucaoDiretoriaStr);
			efetuarNegociacaoDebitosActionForm.setNegociacaoNumeroRD(numeroResolucaoDiretoria);
			efetuarNegociacaoDebitosActionForm.setNegociacaoValorEntrada(valorEntradaStr);
			efetuarNegociacaoDebitosActionForm.setNegociacaoQuantidadeParcelas(quantidadeParcelasStr);
			efetuarNegociacaoDebitosActionForm.setNegociacaoValorParcela(valorParcelaStr);

			efetuarNegociacaoDebitosActionForm.setNegociacaoValorDebitoTotalAtualizado(valorDebitoTotalAtualizadoStr);
			efetuarNegociacaoDebitosActionForm.setNegociacaoPercentualTotalDesconto(percentualTotalDescontoStr);
			efetuarNegociacaoDebitosActionForm.setNegociacaoValorTotalDesconto(valorTotalDescontoStr);
			efetuarNegociacaoDebitosActionForm.setNegociacaoValorDescontoInatividade(valorDescontoInatividadeStr);
			efetuarNegociacaoDebitosActionForm.setNegociacaoValorDescontoAntiguidade(valorDescontoAntiguidadeStr);
			efetuarNegociacaoDebitosActionForm.setNegociacaoValorDebitoAposDesconto(valorDebitoAposDescontoStr);
			efetuarNegociacaoDebitosActionForm.setNegociacaoValorTotalJurosFinanciamento(valorTotalJurosFinanciamentoStr);
			efetuarNegociacaoDebitosActionForm.setNegociacaoIndicadorPagamentoCartaoCredito(indicadorPagamentoCartaoCredito);
			efetuarNegociacaoDebitosActionForm.setNegociacaoValorDescontoPagamentoAVista(valorDescontoPagamentoAVistaStr);
			efetuarNegociacaoDebitosActionForm.setNegociacaoNumeroMesesEntreParcelas(numeroMesesEntreParcelasStr);
			efetuarNegociacaoDebitosActionForm.setNegociacaoNumeroParcelasALancar(numeroParcelasALancarStr);
			efetuarNegociacaoDebitosActionForm.setNegociacaoNumeroMesesInicioCobranca(numeroMesesInicioCobrancaStr);
			efetuarNegociacaoDebitosActionForm.setNegociacaoNumeroDiasVencimentoDaEntrada(numeroDiasVencimentoDaEntradaStr);
			efetuarNegociacaoDebitosActionForm.setNegociacaoTaxaJuros(taxaJurosStr);

			NegociacaoOpcoesParcelamentoHelper negociacaoOpcoesParcelamento = (NegociacaoOpcoesParcelamentoHelper) sessao
							.getAttribute("opcoesParcelamento");
			sessao.setAttribute("negociacaoOpcoesParcelamento", negociacaoOpcoesParcelamento);

			sessao.setAttribute("closePage", "S");
		}else{
			// Exibir as opções para a negociação

			// Limpa atributos da sessão
			sessao.removeAttribute("closePage");
			sessao.removeAttribute("colecaoOpcoesParcelamento");

			NegociacaoDebitoOpcoesRDHelper negociacaoDebitoOpcoesRDHelper = null;
			Integer idResolucaoDiretoriaAux = null;

			for(NegociacaoDebitoOpcoesRDHelper negociacaoDebitoOpcoesRDHelperAux : colecaoNegociacaoDebitoOpcoesRDHelper){
				idResolucaoDiretoriaAux = negociacaoDebitoOpcoesRDHelperAux.getIdResolucaoDiretoria();

				if(idResolucaoDiretoriaAux.equals(idResolucaoDiretoria)){
					negociacaoDebitoOpcoesRDHelper = negociacaoDebitoOpcoesRDHelperAux;
					break;
				}
			}

			BigDecimal valorDebitoTotalAtualizado = negociacaoDebitoOpcoesRDHelper.getValorDebitoTotalAtualizado();
			Integer numeroReparcelamentoConsecutivos = negociacaoDebitoOpcoesRDHelper.getNumeroReparcelamentoConsecutivos();
			String dataVencimentoEntrada = negociacaoDebitoOpcoesRDHelper.getDataVencimentoEntrada();

			// BigDecimal valorEntradaInformado = BigDecimal.ZERO;
			BigDecimal valorEntradaInformado = null;

			if(!Util.isVazioOuBranco(calcular)){
				String valorEntradaInformadoForm = form.getValorEntradaInformado();
				valorEntradaInformado = Util.formatarMoedaRealparaBigDecimal(valorEntradaInformadoForm);
			}

			// [SB0002] - Obter Opções de Parcelamento
			NegociacaoOpcoesParcelamentoHelper opcoesParcelamento = this.obterOpcoesDeParcelamento(negociacaoDebitoImovelHelper,
							usuarioLogado, idResolucaoDiretoria, valorEntradaInformado, valorDebitoTotalAtualizado,
							numeroReparcelamentoConsecutivos, dataVencimentoEntrada);

			if(valorEntradaInformado == null && opcoesParcelamento.getValorEntradaMinima() != null){
				valorEntradaInformado = opcoesParcelamento.getValorEntradaMinima().setScale(Parcelamento.CASAS_DECIMAIS,
								Parcelamento.TIPO_ARREDONDAMENTO);
			}

			// [FS0003] - Verificar valor da entrada mínima permitida
			if(!Util.isVazioOuBranco(calcular)){
				BigDecimal valorEntradaMinima = opcoesParcelamento.getValorEntradaMinima().setScale(Parcelamento.CASAS_DECIMAIS,
								Parcelamento.TIPO_ARREDONDAMENTO);

				// [FS0015] - Verificar se o valor a parcelar é menor que o valor da parcela mínima
				if(valorEntradaInformado != null && valorEntradaInformado.compareTo(valorEntradaMinima) < 0){
					boolean temPermissaoValMinimoEntrada = fachada.verificarPermissaoValMinimoEntrada(usuarioLogado);

					if(!temPermissaoValMinimoEntrada){
						throw new ActionServletException("atencao.valor.entrada.deve.ser.maior.igual.minima", null, Util
										.formatarMoedaReal(valorEntradaMinima));
					}
				}else if(valorEntradaInformado.compareTo(valorDebitoTotalAtualizado) > 0){
					// [FS0012] - Verificar se o valor da entrada é maior que o valor a parcelar
					throw new ActionServletException("atencao.valor_entrada_nao_pode_ser_maior_que_debito_total");
				}
			}

			ParcelamentoPerfil parcelamentoPerfil = opcoesParcelamento.getParcelamentoPerfil();

			Collection<OpcoesParcelamentoHelper> colecaoOpcoesParcelamento = opcoesParcelamento.getOpcoesParcelamento();

			form.setIdResolucaoDiretoria(idResolucaoDiretoriaStr);

			String numeroResolucaoDiretoria = negociacaoDebitoOpcoesRDHelper.getNumeroResolucaoDiretoria();

			// Valor dos Descontos
			BigDecimal descontoAcrescimosImpontualidade = opcoesParcelamento.getValorDescontoAcrecismosImpotualidade().setScale(
							Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
			BigDecimal descontoAntiguidadeDebito = opcoesParcelamento.getValorDescontoAntiguidade();
			BigDecimal descontoInatividadeLigacaoAgua = opcoesParcelamento.getValorDescontoInatividade();
			BigDecimal descontoSancoesRDEspecial = opcoesParcelamento.getValorDescontoSancoesRDEspecial();
			BigDecimal descontoTarifaSocialRDEspecial = opcoesParcelamento.getValorDescontoTarifaSocialRDEspecial();

			BigDecimal valorTotalDescontos = BigDecimal.ZERO;
			valorTotalDescontos = descontoAcrescimosImpontualidade.add(descontoAntiguidadeDebito);
			valorTotalDescontos = valorTotalDescontos.add(descontoInatividadeLigacaoAgua);
			valorTotalDescontos = valorTotalDescontos.add(descontoSancoesRDEspecial);
			valorTotalDescontos = valorTotalDescontos.add(descontoTarifaSocialRDEspecial);

			// Opção de Pagamento à Vista

			Collection<ContaValoresHelper> colecaoContaValores = negociacaoDebitoImovelHelper.getColecaoContaValores();
			BigDecimal valorTotalImpostosConta = obterValorImpostosDasContasDoParcelamento(colecaoContaValores);

			BigDecimal valorDescontoPagamentoAVista = valorTotalDescontos;

			BigDecimal percentualDescontoAVista = parcelamentoPerfil.getPercentualDescontoAVista();

			BigDecimal descontoPagamentoAVistaRDEspecial = opcoesParcelamento.getValorDescontoPagamentoAVistaRDEspecial();

			if(percentualDescontoAVista != null){
				valorDescontoPagamentoAVista = valorDescontoPagamentoAVista.add(descontoPagamentoAVistaRDEspecial);
			}

			BigDecimal valorPagamentoAVista = valorDebitoTotalAtualizado.subtract(valorDescontoPagamentoAVista);
			valorPagamentoAVista = valorPagamentoAVista.subtract(valorTotalImpostosConta);

			// Seta os valores no form
			form.setNumeroResolucaoDiretoria(numeroResolucaoDiretoria);

			String descontoAcrescimosImpontualidadeStr = Util.formatarMoedaReal(descontoAcrescimosImpontualidade);
			form.setDescontoAcrescimosImpontualidade(descontoAcrescimosImpontualidadeStr);

			String descontoAntiguidadeDebitoStr = Util.formatarMoedaReal(descontoAntiguidadeDebito);
			form.setDescontoAntiguidadeDebito(descontoAntiguidadeDebitoStr);

			String descontoInatividadeLigacaoAguaStr = Util.formatarMoedaReal(descontoInatividadeLigacaoAgua);
			form.setDescontoInatividadeLigacaoAgua(descontoInatividadeLigacaoAguaStr);

			String descontoTarifaSocialRDEspecialStr = Util.formatarMoedaReal(descontoTarifaSocialRDEspecial);
			form.setDescontoTarifaSocialRDEspecial(descontoTarifaSocialRDEspecialStr);

			String descontoSancoesRDEspecialStr = Util.formatarMoedaReal(descontoSancoesRDEspecial);
			form.setDescontoSancoesRDEspecial(descontoSancoesRDEspecialStr);

			String valorTotalDescontosStr = Util.formatarMoedaReal(valorTotalDescontos);
			form.setValorTotalDescontos(valorTotalDescontosStr);

			String valorDebitoTotalAtualizadoStr = Util.formatarMoedaReal(valorDebitoTotalAtualizado);
			form.setValorDebitoTotalAtualizado(valorDebitoTotalAtualizadoStr);

			String valorTotalImpostosContaStr = Util.formatarMoedaReal(valorTotalImpostosConta);
			form.setValorTotalImpostosConta(valorTotalImpostosContaStr);

			String valorDescontoPagamentoAVistaStr = Util.formatarMoedaReal(valorDescontoPagamentoAVista);
			form.setValorDescontoPagamentoAVista(valorDescontoPagamentoAVistaStr);

			String valorPagamentoAVistaStr = Util.formatarMoedaReal(valorPagamentoAVista);
			form.setValorPagamentoAVista(valorPagamentoAVistaStr);

			String valorEntradaInformadoStr = Util.formatarMoedaReal(valorEntradaInformado);
			form.setValorEntradaInformado(valorEntradaInformadoStr);
			form.setValorEntradaInformadoAntes(valorEntradaInformadoStr);

			form.setIndicadorPagamentoCartaoCredito(null);
			form.setIndicadorQuantidadeParcelas(null);

			sessao.setAttribute("colecaoOpcoesParcelamento", colecaoOpcoesParcelamento);
			sessao.setAttribute("opcoesParcelamento", opcoesParcelamento);
		}

		return retorno;
	}

	// [SB0002] - Obter Opções de Parcelamento
	private NegociacaoOpcoesParcelamentoHelper obterOpcoesDeParcelamento(NegociacaoDebitoImovelHelper negociacaoDebitoImovelHelper,
					Usuario usuarioLogado, Integer idResolucaoDiretoria, BigDecimal valorEntrada, BigDecimal valorDebitoTotalAtualizado,
					Integer numeroReparcelamentoConsecutivos, String dataVencimentoEntrada){

		NegociacaoOpcoesParcelamentoHelper opcoesParcelamento = null;

		// Obtém a instância da fachada
		Fachada fachada = Fachada.getInstancia();

		Integer idImovel = negociacaoDebitoImovelHelper.getIdImovel();
		Integer idLigacaoAguaSituacao = negociacaoDebitoImovelHelper.getIdLigacaoAguaSituacao();
		Integer idLigacaoEsgotoSituacao = negociacaoDebitoImovelHelper.getIdLigacaoEsgotoSituacao();
		Integer idPerfilImovel = negociacaoDebitoImovelHelper.getIdPerfilImovel();
		Collection<ContaValoresHelper> colecaoContaValores = negociacaoDebitoImovelHelper.getColecaoContaValores();
		Collection<GuiaPagamentoValoresHelper> colecaoGuiaPagamentoValores = negociacaoDebitoImovelHelper.getColecaoGuiaPagamentoValores();
		BigDecimal valorTotalMulta = negociacaoDebitoImovelHelper.getValorTotalMulta();
		BigDecimal valorTotalJurosMora = negociacaoDebitoImovelHelper.getValorTotalJurosMora();
		BigDecimal valorTotalAtualizacaoMonetaria = negociacaoDebitoImovelHelper.getValorTotalAtualizacaoMonetaria();
		BigDecimal valorTotalRestanteParcelamentosACobrar = negociacaoDebitoImovelHelper.getValorTotalRestanteParcelamentosACobrar();

		String inicioIntervaloParcelamento = "01/0001";
		Integer indicadorRestabelecimento = 2;
		Integer anoMesInicialReferenciaDebito = Integer.valueOf("000101");
		Integer anoMesFinalReferenciaDebito = Integer.valueOf("999912");

		IndicadoresParcelamentoHelper indicadoresParcelamentoHelper = new IndicadoresParcelamentoHelper();
		indicadoresParcelamentoHelper.setIndicadorContasRevisao(2);
		indicadoresParcelamentoHelper.setIndicadorDebitosACobrar(1);
		indicadoresParcelamentoHelper.setIndicadorCreditoARealizar(1);
		indicadoresParcelamentoHelper.setIndicadorGuiasPagamento(1);
		indicadoresParcelamentoHelper.setIndicadorAcrescimosImpotualidade(1);

		BigDecimal valorSucumbenciaTotal = BigDecimal.ZERO;
		Integer quantidadeParcelasSucumbencia = Integer.valueOf("1");
		BigDecimal valorSucumbenciaAtual = BigDecimal.ZERO;
		BigDecimal valorDiligencias = BigDecimal.ZERO;

		opcoesParcelamento = fachada.obterOpcoesDeParcelamento(idResolucaoDiretoria, idImovel, valorEntrada, idLigacaoAguaSituacao,
						idLigacaoEsgotoSituacao, idPerfilImovel, inicioIntervaloParcelamento, indicadorRestabelecimento,
						colecaoContaValores, valorDebitoTotalAtualizado, valorTotalMulta, valorTotalJurosMora,
						valorTotalAtualizacaoMonetaria, numeroReparcelamentoConsecutivos, colecaoGuiaPagamentoValores, usuarioLogado,
						valorTotalRestanteParcelamentosACobrar, anoMesInicialReferenciaDebito, anoMesFinalReferenciaDebito,
						indicadoresParcelamentoHelper, dataVencimentoEntrada, valorSucumbenciaTotal, quantidadeParcelasSucumbencia,
						valorSucumbenciaAtual, valorDiligencias);

		return opcoesParcelamento;
	}

	private BigDecimal obterValorImpostosDasContasDoParcelamento(Collection<ContaValoresHelper> colecaoContas){

		BigDecimal valorTotalImpostos = BigDecimal.ZERO;

		if(!Util.isVazioOrNulo(colecaoContas)){
			Conta conta = null;
			BigDecimal valorImposto = null;

			for(ContaValoresHelper contaValoresHelper : colecaoContas){
				conta = contaValoresHelper.getConta();
				valorImposto = conta.getValorImposto();

				if(valorImposto != null){
					valorTotalImpostos = valorTotalImpostos.add(valorImposto);
				}
			}
		}

		return valorTotalImpostos;
	}

}
