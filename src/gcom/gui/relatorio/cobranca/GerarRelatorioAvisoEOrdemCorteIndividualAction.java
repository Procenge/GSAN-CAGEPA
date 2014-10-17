
package gcom.gui.relatorio.cobranca;

import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao;
import gcom.cadastro.imovel.FiltroImovel;
import gcom.cadastro.imovel.Imovel;
import gcom.cobranca.CobrancaAcao;
import gcom.cobranca.FiltroCobrancaAcao;
import gcom.cobranca.bean.CobrancaAcaoIndividualHelper;
import gcom.cobranca.bean.ContaValoresHelper;
import gcom.cobranca.bean.GuiaPagamentoValoresHelper;
import gcom.cobranca.bean.ObterDebitoImovelOuClienteHelper;
import gcom.cobranca.parcelamento.Parcelamento;
import gcom.fachada.Fachada;
import gcom.faturamento.credito.CreditoARealizar;
import gcom.faturamento.credito.CreditoOrigem;
import gcom.faturamento.debito.DebitoACobrar;
import gcom.faturamento.debito.DebitoTipo;
import gcom.gui.ActionServletException;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.RelatorioVazioException;
import gcom.relatorio.cobranca.RelatorioAvisoEOrdemCorteIndividual;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.comparators.ComparatorChain;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC3052] Gerar e Emitir Aviso de Corte e Ordem de Corte Individual
 * 
 * @author Hebert Falcão
 * @date 16/03/2012
 */
public class GerarRelatorioAvisoEOrdemCorteIndividualAction
				extends ExibidorProcessamentoTarefaRelatorio {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o retorno
		ActionForward retorno = null;

		// Obtém a instância da fachada
		Fachada fachada = Fachada.getInstancia();

		// Obtém a sessão
		HttpSession sessao = httpServletRequest.getSession(false);

		String idImovelStr = (String) sessao.getAttribute("idImovelPrincipalAba");
		Integer idImovel = null;
		if(!Util.isVazioOuBranco(idImovelStr)){
			idImovel = Integer.valueOf(idImovelStr);
		}else if(!Util.isVazioOuBranco(httpServletRequest.getParameter("imovel"))){
			idImovelStr = (String) httpServletRequest.getParameter("imovel");
			idImovel = Integer.valueOf(idImovelStr);

			// verifica se o imovel existe
			FiltroImovel filtroImovel = new FiltroImovel();
			filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, idImovel));

			Collection imoveis = fachada.pesquisar(filtroImovel, Imovel.class.getName());

			if(Util.isVazioOrNulo(imoveis)){
				throw new ActionServletException("atencao.imovel.inexistente");
			}

			// Insere a matricula do imovel na sessao
			sessao.setAttribute("idImovelPrincipalAba", idImovelStr);
		}else{
			throw new ActionServletException("atencao.imovel_nao_selecionado");
		}

		LigacaoAguaSituacao ligacaoAguaSituacao = fachada.pesquisarLigacaoAguaSituacao(idImovel);

		if(!LigacaoAguaSituacao.LIGADO.equals(ligacaoAguaSituacao.getId())){
			throw new ActionServletException("atencao.corte_apenas_imovel_ligado");
		}

		/*
		 * Tratamento novo de hugo
		 */

		String referenciaInicial = "01/0001";
		String referenciaFinal = "12/9999";
		String dataVencimentoInicial = "01/01/0001";
		// Data de vencimento final menor ou igual a data atual menos a quantidade de 1 dia
		String dataVencimentoFinal = Util.formatarData(Util.subtrairNumeroDiasDeUmaData(new Date(), 1));

		// Para auxiliar na formatação de uma data
		SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
		String mesInicial = referenciaInicial.substring(0, 2);
		String anoInicial = referenciaInicial.substring(3, referenciaInicial.length());
		String anoMesInicial = anoInicial + mesInicial;
		String mesFinal = referenciaFinal.substring(0, 2);
		String anoFinal = referenciaFinal.substring(3, referenciaFinal.length());
		String anoMesFinal = anoFinal + mesFinal;

		Date dataVencimentoDebitoI;
		Date dataVencimentoDebitoF;

		try{
			dataVencimentoDebitoI = formatoData.parse(dataVencimentoInicial);
		}catch(ParseException ex){
			dataVencimentoDebitoI = null;
		}
		try{
			dataVencimentoDebitoF = formatoData.parse(dataVencimentoFinal);
		}catch(ParseException ex){
			dataVencimentoDebitoF = null;
		}

		// seta valores constantes para chamar o metodo que consulta debitos do imovel
		Integer tipoImovel = Integer.valueOf(1);
		Integer indicadorPagamento = Integer.valueOf(1);
		Integer indicadorConta = Integer.valueOf(2);
		Integer indicadorDebito = Integer.valueOf(2);
		Integer indicadorCredito = Integer.valueOf(2);
		Integer indicadorNotas = Integer.valueOf(2);
		Integer indicadorGuias = Integer.valueOf(2);
		Integer indicadorAtualizar = Integer.valueOf(2);

		// Obtendo os débitos do imovel
		ObterDebitoImovelOuClienteHelper colecaoDebitoImovel = fachada.obterDebitoImovelOuCliente(tipoImovel.intValue(), idImovel
						.toString().trim(), null, null, anoMesInicial, anoMesFinal, dataVencimentoDebitoI, dataVencimentoDebitoF,
						indicadorPagamento.intValue(), indicadorConta.intValue(), indicadorDebito.intValue(), indicadorCredito.intValue(),
						indicadorNotas.intValue(), indicadorGuias.intValue(), indicadorAtualizar.intValue(), null, null, null, null, null,
						ConstantesSistema.SIM, ConstantesSistema.SIM, ConstantesSistema.SIM);

		Collection<ContaValoresHelper> colecaoContaValores = colecaoDebitoImovel.getColecaoContasValores();
		Collection<GuiaPagamentoValoresHelper> colecaoGuiaPagamentoValores = colecaoDebitoImovel.getColecaoGuiasPagamentoValores();

		// [UC0203] Consultar Débitos
		// [FS0013 - Verifica existência de débito prescrito]:
		fachada.verificarExistenciaDebitoPrescrito(this.getUsuarioLogado(httpServletRequest), colecaoContaValores,
						colecaoGuiaPagamentoValores);

		if(colecaoContaValores != null){
			Collection<ContaValoresHelper> colecaoContasValoresParaRemocao = new ArrayList();

			BigDecimal valorPago = null;

			for(ContaValoresHelper contaValorHelper : colecaoContaValores){
				valorPago = contaValorHelper.getValorPago();

				if(valorPago != null && valorPago.compareTo(BigDecimal.ZERO) == 1){
					colecaoContasValoresParaRemocao.add(contaValorHelper);
				}
			}

			colecaoContaValores.removeAll(colecaoContasValoresParaRemocao);
		}

		if(colecaoGuiaPagamentoValores != null){
			Collection<GuiaPagamentoValoresHelper> colecaoGuiasPagamentoValoresRemocao = new ArrayList();

			BigDecimal valorPago = null;

			for(GuiaPagamentoValoresHelper guiaPagamentoValoresHelper : colecaoGuiaPagamentoValores){
				valorPago = guiaPagamentoValoresHelper.getValorPago();

				if(valorPago != null && valorPago.compareTo(BigDecimal.ZERO) == 1){
					colecaoGuiasPagamentoValoresRemocao.add(guiaPagamentoValoresHelper);
				}
			}

			colecaoGuiaPagamentoValores.removeAll(colecaoGuiasPagamentoValoresRemocao);
		}

		ContaValoresHelper dadosConta = null;

		BigDecimal valorConta = BigDecimal.ZERO;
		BigDecimal valorAcrescimo = BigDecimal.ZERO;

		if(colecaoContaValores != null && !colecaoContaValores.isEmpty()){

			Iterator<ContaValoresHelper> colecaoContaValoresIterator = colecaoContaValores.iterator();

			// percorre a colecao de conta somando o valor para obter um valor total
			while(colecaoContaValoresIterator.hasNext()){

				dadosConta = colecaoContaValoresIterator.next();
				valorConta = valorConta.add(dadosConta.getConta().getValorTotal().setScale(Parcelamento.CASAS_DECIMAIS,
								Parcelamento.TIPO_ARREDONDAMENTO));
				if(dadosConta.getValorTotalContaValores() != null){
					valorAcrescimo = valorAcrescimo.add(dadosConta.getValorTotalContaValores().setScale(Parcelamento.CASAS_DECIMAIS,
									Parcelamento.TIPO_ARREDONDAMENTO));
				}

				if(dadosConta.getConta().getValorAgua() != null){
					dadosConta.getConta().setValorAgua(
									dadosConta.getConta().getValorAgua().setScale(Parcelamento.CASAS_DECIMAIS,
													Parcelamento.TIPO_ARREDONDAMENTO));
				}else{
					dadosConta.getConta().setValorAgua(
									BigDecimal.ZERO.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO));
				}

				if(dadosConta.getConta().getValorEsgoto() != null){
					dadosConta.getConta().setValorEsgoto(
									dadosConta.getConta().getValorEsgoto().setScale(Parcelamento.CASAS_DECIMAIS,
													Parcelamento.TIPO_ARREDONDAMENTO));
				}else{
					dadosConta.getConta().setValorEsgoto(
									BigDecimal.ZERO.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO));
				}

				if(dadosConta.getConta().getDebitos() != null){
					dadosConta.getConta().setDebitos(
									dadosConta.getConta().getDebitos().setScale(Parcelamento.CASAS_DECIMAIS,
													Parcelamento.TIPO_ARREDONDAMENTO));
				}else{
					dadosConta.getConta().setDebitos(
									BigDecimal.ZERO.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO));
				}

				if(dadosConta.getConta().getValorCreditos() != null){
					dadosConta.getConta().setValorCreditos(
									dadosConta.getConta().getValorCreditos().setScale(Parcelamento.CASAS_DECIMAIS,
													Parcelamento.TIPO_ARREDONDAMENTO));
				}else{
					dadosConta.getConta().setValorCreditos(
									BigDecimal.ZERO.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO));
				}

				if(dadosConta.getConta().getValorImposto() != null){
					dadosConta.getConta().setValorImposto(
									dadosConta.getConta().getValorImposto().setScale(Parcelamento.CASAS_DECIMAIS,
													Parcelamento.TIPO_ARREDONDAMENTO));
				}else{
					dadosConta.getConta().setValorImposto(
									BigDecimal.ZERO.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO));
				}

				if(dadosConta.getValorMulta() != null){
					dadosConta.setValorMulta(dadosConta.getValorMulta().setScale(Parcelamento.CASAS_DECIMAIS,
									Parcelamento.TIPO_ARREDONDAMENTO));
				}else{
					dadosConta.setValorMulta(BigDecimal.ZERO.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO));
				}

				if(dadosConta.getValorJurosMora() != null){
					dadosConta.setValorJurosMora(dadosConta.getValorJurosMora().setScale(Parcelamento.CASAS_DECIMAIS,
									Parcelamento.TIPO_ARREDONDAMENTO));
				}else{
					dadosConta.setValorJurosMora(BigDecimal.ZERO.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO));
				}

				if(dadosConta.getValorAtualizacaoMonetaria() != null){
					dadosConta.setValorAtualizacaoMonetaria(dadosConta.getValorAtualizacaoMonetaria().setScale(Parcelamento.CASAS_DECIMAIS,
									Parcelamento.TIPO_ARREDONDAMENTO));
				}else{
					dadosConta.setValorAtualizacaoMonetaria(BigDecimal.ZERO.setScale(Parcelamento.CASAS_DECIMAIS,
									Parcelamento.TIPO_ARREDONDAMENTO));
				}
			}
		}

		Collection<DebitoACobrar> colecaoDebitoACobrar = colecaoDebitoImovel.getColecaoDebitoACobrar();

		BigDecimal valorDebitoACobrar = new BigDecimal("0.00");
		BigDecimal valorDebitoACobrarSemJurosParcelamento = new BigDecimal("0.00");
		DebitoACobrar dadosDebito = null;

		if(colecaoDebitoACobrar != null && !colecaoDebitoACobrar.isEmpty()){
			Iterator<DebitoACobrar> colecaoDebitoACobrarIterator = colecaoDebitoACobrar.iterator();

			// percorre a colecao de debito a cobrar somando o valor para obter um valor
			// total
			while(colecaoDebitoACobrarIterator.hasNext()){

				dadosDebito = colecaoDebitoACobrarIterator.next();
				valorDebitoACobrar = valorDebitoACobrar.add(dadosDebito.getValorTotal());

				if(dadosDebito.getDebitoTipo() != null && !dadosDebito.getDebitoTipo().getId().equals(DebitoTipo.JUROS_SOBRE_PARCELAMENTO)){
					valorDebitoACobrarSemJurosParcelamento = valorDebitoACobrarSemJurosParcelamento.add(dadosDebito.getValorTotal());
				}
			}
		}

		Collection<CreditoARealizar> colecaoCreditoARealizar = colecaoDebitoImovel.getColecaoCreditoARealizar();
		Collection<CreditoARealizar> colecaoCreditoARealizarSemDescontoParcelamento = new ArrayList<CreditoARealizar>();

		BigDecimal valorCreditoARealizar = new BigDecimal("0.00");
		BigDecimal valorCreditoARealizarSemDescontosParcelamento = new BigDecimal("0.00");
		CreditoARealizar dadosCredito = null;

		if(colecaoCreditoARealizar != null && !colecaoCreditoARealizar.isEmpty()){
			Iterator<CreditoARealizar> colecaoCreditoARealizarIterator = colecaoCreditoARealizar.iterator();

			// percorre a colecao de credito a realizar somando o valor para obter um
			// valor total
			while(colecaoCreditoARealizarIterator.hasNext()){

				dadosCredito = colecaoCreditoARealizarIterator.next();
				valorCreditoARealizar = valorCreditoARealizar.add(dadosCredito.getValorTotal());

				if(dadosCredito.getCreditoOrigem() != null
								&& !dadosCredito.getCreditoOrigem().getId().equals(CreditoOrigem.DESCONTOS_CONCEDIDOS_NO_PARCELAMENTO)){
					valorCreditoARealizarSemDescontosParcelamento = valorCreditoARealizarSemDescontosParcelamento.add(dadosCredito
									.getValorTotal());
					colecaoCreditoARealizarSemDescontoParcelamento.add(dadosCredito);
				}
			}
		}

		BigDecimal valorGuiaPagamento = new BigDecimal("0.00");

		if(colecaoGuiaPagamentoValores != null && !colecaoGuiaPagamentoValores.isEmpty()){

			Iterator<GuiaPagamentoValoresHelper> colecaoGuiaPagamentoValoresHelperIterator = colecaoGuiaPagamentoValores.iterator();

			// Percorre a colecao de Prestações da Guia de Pagamento somando o valor
			// para obter o total em aberto
			while(colecaoGuiaPagamentoValoresHelperIterator.hasNext()){
				GuiaPagamentoValoresHelper dadosGuiaPagamentoValoresHelper = colecaoGuiaPagamentoValoresHelperIterator.next();
				valorGuiaPagamento = valorGuiaPagamento.add(dadosGuiaPagamentoValoresHelper.getValorTotalPrestacao().setScale(
								Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO));
			}
		}

		// Manda a colecao pelo request
		if(colecaoContaValores != null && !colecaoContaValores.isEmpty()){
			ComparatorChain sortConta = new ComparatorChain();
			sortConta.addComparator(new BeanComparator("conta.referencia"), true);
			Collections.sort((List) colecaoContaValores, sortConta);
		}

		// Soma o valor total dos debitos e subtrai dos creditos
		BigDecimal valorTotalSemAcrescimo = valorConta.add(valorDebitoACobrar);
		valorTotalSemAcrescimo = valorTotalSemAcrescimo.add(valorGuiaPagamento);
		valorTotalSemAcrescimo = valorTotalSemAcrescimo.subtract(valorCreditoARealizar);

		BigDecimal valorTotalComAcrescimo = valorTotalSemAcrescimo.add(valorAcrescimo);

		BigDecimal valorToralSemAcrescimoESemJurosParcelamento = valorConta.add(valorDebitoACobrarSemJurosParcelamento);

		valorToralSemAcrescimoESemJurosParcelamento = valorToralSemAcrescimoESemJurosParcelamento.add(valorGuiaPagamento);

		valorToralSemAcrescimoESemJurosParcelamento = valorToralSemAcrescimoESemJurosParcelamento
						.subtract(valorCreditoARealizarSemDescontosParcelamento);

		/*
		 * FIM DO TRATAMENTO
		 */

		Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");

		if(Util.isVazioOrNulo(colecaoContaValores) && Util.isVazioOrNulo(colecaoDebitoACobrar)
						&& Util.isVazioOrNulo(colecaoGuiaPagamentoValores)
						&& Util.isVazioOrNulo(colecaoCreditoARealizarSemDescontoParcelamento)){

			throw new ActionServletException("atencao.imovel_sem_debitos");
		}

		BigDecimal valorDocumento = valorToralSemAcrescimoESemJurosParcelamento;

		Collection colecaoCobrancaAcaoHelper = new ArrayList();
		CobrancaAcaoIndividualHelper cobrancaAcaoIndividualHelperAvisoCorte = null;
		CobrancaAcaoIndividualHelper cobrancaAcaoIndividualHelperCorte = null;

		cobrancaAcaoIndividualHelperAvisoCorte = new CobrancaAcaoIndividualHelper();
		cobrancaAcaoIndividualHelperAvisoCorte.setCobrancaAcao(this.obterCobrancaAcao(fachada, CobrancaAcao.AVISO_CORTE));
		cobrancaAcaoIndividualHelperAvisoCorte.setIndicadorExistenciaAcaoImovel(ConstantesSistema.SIM);

		colecaoCobrancaAcaoHelper.add(cobrancaAcaoIndividualHelperAvisoCorte);

		cobrancaAcaoIndividualHelperCorte = new CobrancaAcaoIndividualHelper();
		cobrancaAcaoIndividualHelperCorte.setCobrancaAcao(this.obterCobrancaAcao(fachada, CobrancaAcao.CORTE_FISICO));
		cobrancaAcaoIndividualHelperCorte.setIndicadorExistenciaAcaoImovel(ConstantesSistema.SIM);

		colecaoCobrancaAcaoHelper.add(cobrancaAcaoIndividualHelperCorte);

		CobrancaAcaoIndividualHelper cobrancaAcaoIndividualHelper = fachada.gerarEmitirAvisoDeCorteOrdemDeCorteIndividual(idImovel,
						valorDocumento, colecaoContaValores, colecaoDebitoACobrar, colecaoGuiaPagamentoValores,
						colecaoCreditoARealizarSemDescontoParcelamento, usuarioLogado, colecaoCobrancaAcaoHelper,
						ConstantesSistema.GERAR_EMITIR);

		if(cobrancaAcaoIndividualHelper.getCodigoRetornoAtividade().equals(ConstantesSistema.ATIVIDADE_EFETUADA_SEM_SUCESSO)){

			throw new ActionServletException("atencao.aviso.ordem.corte.nao.gerado");

		}else{
			RelatorioAvisoEOrdemCorteIndividual relatorioAvisoEOrdemCorteIndividual = cobrancaAcaoIndividualHelper.getRelatorio();

			if(relatorioAvisoEOrdemCorteIndividual != null){
				try{
					retorno = processarExibicaoRelatorio(relatorioAvisoEOrdemCorteIndividual, TarefaRelatorio.TIPO_PDF, httpServletRequest,
									httpServletResponse, actionMapping);
				}catch(RelatorioVazioException ex){
					// manda o erro para a página no request atual
					reportarErros(httpServletRequest, "atencao.relatorio.vazio");

					// seta o mapeamento de retorno para a tela de atenção de popup
					retorno = actionMapping.findForward("telaAtencaoPopup");
				}
			}
		}


		return retorno;
	}

	private CobrancaAcao obterCobrancaAcao(Fachada fachada, Integer idCobrancaAcao){

		FiltroCobrancaAcao filtroCobrancaAcao = new FiltroCobrancaAcao();
		filtroCobrancaAcao.adicionarCaminhoParaCarregamentoEntidade(FiltroCobrancaAcao.DOCUMENTO_TIPO);
		filtroCobrancaAcao.adicionarCaminhoParaCarregamentoEntidade(FiltroCobrancaAcao.COBRANCA_ACAO_PRECEDENTE);
		filtroCobrancaAcao.adicionarCaminhoParaCarregamentoEntidade(FiltroCobrancaAcao.SERVICO_TIPO);

		filtroCobrancaAcao.adicionarParametro(new ParametroSimples(FiltroCobrancaAcao.ID, idCobrancaAcao));

		Collection<CobrancaAcao> colecao = fachada.pesquisar(filtroCobrancaAcao, CobrancaAcao.class.getName());

		CobrancaAcao cobrancaAcao = (CobrancaAcao) Util.retonarObjetoDeColecao(colecao);

		return cobrancaAcao;
	}
}
