
package gcom.gui.cobranca;

import gcom.cobranca.bean.ContaValoresHelper;
import gcom.cobranca.bean.GuiaPagamentoValoresHelper;
import gcom.cobranca.bean.NegociacaoDebitoImovelHelper;
import gcom.cobranca.bean.NegociacaoOpcoesParcelamentoHelper;
import gcom.fachada.Fachada;
import gcom.faturamento.credito.CreditoARealizar;
import gcom.faturamento.debito.DebitoACobrar;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.Util;

import java.math.BigDecimal;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC3031] Efetuar Negociação de Débitos
 * 
 * @author Hebert Falcão
 * @date 01/12/2011
 */
public class EfetuarNegociacaoDebitosAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		// Form
		EfetuarNegociacaoDebitosActionForm form = (EfetuarNegociacaoDebitosActionForm) actionForm;

		// Obtém a instância da fachada
		Fachada fachada = Fachada.getInstancia();

		// Obtém a sessão
		HttpSession sessao = httpServletRequest.getSession(false);

		Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");

		String idImovelStr = form.getIdImovel();
		String idImovelAntesStr = form.getIdImovelAntes();

		// Verifica se o imóvel foi alterado
		if(!idImovelStr.equals(idImovelAntesStr)){
			throw new ActionServletException("atencao.valor.imovel.alterado.necessario.pesquisar");
		}

		// Imóvel
		Integer idImovel = null;

		if(!Util.isVazioOuBranco(idImovelStr)){
			idImovel = Integer.valueOf(idImovelStr);
		}

		String inscricaoImovel = form.getInscricaoImovel();
		String enderecoImovel = form.getEnderecoImovel();

		// Resolução Diretoria
		Integer negociacaoIdRD = null;
		String negociacaoIdRDStr = form.getNegociacaoIdRD();

		if(!Util.isVazioOuBranco(negociacaoIdRDStr)){
			negociacaoIdRD = Integer.valueOf(negociacaoIdRDStr);
		}

		// Cliente Usuário
		Integer idClienteUsuario = null;
		String idClienteUsuarioStr = form.getIdClienteUsuario();

		if(!Util.isVazioOuBranco(idClienteUsuarioStr)){
			idClienteUsuario = Integer.valueOf(idClienteUsuarioStr);
		}

		Short indicadorPessoaFisicaJuridica = null;
		String indicadorPessoaFisicaJuridicaStr = form.getIndicadorPessoaFisicaJuridica();

		if(!Util.isVazioOuBranco(indicadorPessoaFisicaJuridicaStr)){
			indicadorPessoaFisicaJuridica = Short.valueOf(indicadorPessoaFisicaJuridicaStr);
		}

		String cpfCnpjCliente = "";
		String cpfCnpjClienteAntes = form.getCpfCnpjClienteAntes();

		if(!Util.isVazioOuBranco(cpfCnpjClienteAntes)){
			// CPF/CNPJ sem formatação
			cpfCnpjCliente = cpfCnpjClienteAntes;
		}else{
			cpfCnpjCliente = form.getCpfCnpjCliente();
		}

		String emailCliente = form.getEmailCliente();

		// Negociação
		Short negociacaoQuantidadeParcelas = 0;
		String negociacaoQuantidadeParcelasStr = form.getNegociacaoQuantidadeParcelas();

		if(!Util.isVazioOuBranco(negociacaoQuantidadeParcelasStr)){
			negociacaoQuantidadeParcelas = Short.valueOf(negociacaoQuantidadeParcelasStr);
		}

		BigDecimal negociacaoValorParcela = BigDecimal.ZERO;
		String negociacaoValorParcelaStr = form.getNegociacaoValorParcela();

		if(!Util.isVazioOuBranco(negociacaoValorParcelaStr)){
			negociacaoValorParcela = Util.formatarMoedaRealparaBigDecimal(negociacaoValorParcelaStr);
		}

		BigDecimal negociacaoValorEntrada = BigDecimal.ZERO;
		String negociacaoValorEntradaStr = form.getNegociacaoValorEntrada();

		if(!Util.isVazioOuBranco(negociacaoValorEntradaStr)){
			negociacaoValorEntrada = Util.formatarMoedaRealparaBigDecimal(negociacaoValorEntradaStr);
		}

		BigDecimal negociacaoValorDebitoAposDesconto = BigDecimal.ZERO;
		String negociacaoValorDebitoAposDescontoStr = form.getNegociacaoValorDebitoAposDesconto();

		if(!Util.isVazioOuBranco(negociacaoValorDebitoAposDescontoStr)){
			negociacaoValorDebitoAposDesconto = Util.formatarMoedaRealparaBigDecimal(negociacaoValorDebitoAposDescontoStr);
		}

		String negociacaoIndicadorPagamentoCartaoCredito = form.getNegociacaoIndicadorPagamentoCartaoCredito();

		BigDecimal negociacaoValorDescontoPagamentoAVista = BigDecimal.ZERO;
		String negociacaoValorDescontoPagamentoAVistaStr = form.getNegociacaoValorDescontoPagamentoAVista();

		if(!Util.isVazioOuBranco(negociacaoValorDescontoPagamentoAVistaStr)){
			negociacaoValorDescontoPagamentoAVista = Util.formatarMoedaRealparaBigDecimal(negociacaoValorDescontoPagamentoAVistaStr);
		}

		BigDecimal negociacaoValorDescontoAntiguidade = BigDecimal.ZERO;
		String negociacaoValorDescontoAntiguidadeStr = form.getNegociacaoValorDescontoAntiguidade();

		if(!Util.isVazioOuBranco(negociacaoValorDescontoAntiguidadeStr)){
			negociacaoValorDescontoAntiguidade = Util.formatarMoedaRealparaBigDecimal(negociacaoValorDescontoAntiguidadeStr);
		}

		BigDecimal negociacaoValorDescontoInatividade = BigDecimal.ZERO;
		String negociacaoValorDescontoInatividadeStr = form.getNegociacaoValorDescontoInatividade();

		if(!Util.isVazioOuBranco(negociacaoValorDescontoPagamentoAVistaStr)){
			negociacaoValorDescontoInatividade = Util.formatarMoedaRealparaBigDecimal(negociacaoValorDescontoInatividadeStr);
		}

		Integer negociacaoNumeroMesesEntreParcelas = null;
		String negociacaoNumeroMesesEntreParcelasStr = form.getNegociacaoNumeroMesesEntreParcelas();

		if(!Util.isVazioOuBranco(negociacaoNumeroMesesEntreParcelasStr)){
			negociacaoNumeroMesesEntreParcelas = Integer.valueOf(negociacaoNumeroMesesEntreParcelasStr);
		}

		Integer negociacaoNumeroParcelasALancar = null;
		String negociacaoNumeroParcelasALancarStr = form.getNegociacaoNumeroParcelasALancar();

		if(!Util.isVazioOuBranco(negociacaoNumeroParcelasALancarStr)){
			negociacaoNumeroParcelasALancar = Integer.valueOf(negociacaoNumeroParcelasALancarStr);
		}

		Integer negociacaoNumeroMesesInicioCobranca = null;
		String negociacaoNumeroMesesInicioCobrancaStr = form.getNegociacaoNumeroMesesInicioCobranca();

		if(!Util.isVazioOuBranco(negociacaoNumeroMesesInicioCobrancaStr)){
			negociacaoNumeroMesesInicioCobranca = Integer.valueOf(negociacaoNumeroMesesInicioCobrancaStr);
		}

		Integer negociacaoNumeroDiasVencimentoDaEntrada = null;
		String negociacaoNumeroDiasVencimentoDaEntradaStr = form.getNegociacaoNumeroDiasVencimentoDaEntrada();

		if(!Util.isVazioOuBranco(negociacaoNumeroDiasVencimentoDaEntradaStr)){
			negociacaoNumeroDiasVencimentoDaEntrada = Integer.valueOf(negociacaoNumeroDiasVencimentoDaEntradaStr);
		}

		BigDecimal negociacaoTaxaJuros = BigDecimal.ZERO;
		String negociacaoTaxaJurosStr = form.getNegociacaoTaxaJuros();

		if(!Util.isVazioOuBranco(negociacaoTaxaJurosStr)){
			negociacaoTaxaJuros = Util.formatarMoedaRealparaBigDecimal(negociacaoTaxaJurosStr);
		}

		NegociacaoOpcoesParcelamentoHelper negociacaoOpcoesParcelamento = (NegociacaoOpcoesParcelamentoHelper) sessao
						.getAttribute("negociacaoOpcoesParcelamento");

		// Débitos e Créditos
		NegociacaoDebitoImovelHelper negociacaoDebitoImovelHelper = (NegociacaoDebitoImovelHelper) sessao
						.getAttribute("negociacaoDebitoImovelHelper");

		Collection<ContaValoresHelper> colecaoContaValores = negociacaoDebitoImovelHelper.getColecaoContaValores();
		Collection<DebitoACobrar> colecaoDebitoACobrar = negociacaoDebitoImovelHelper.getColecaoDebitoACobrar();
		Collection<CreditoARealizar> colecaoCreditoARealizar = negociacaoDebitoImovelHelper.getColecaoCreditoARealizar();
		Collection<GuiaPagamentoValoresHelper> colecaoGuiaPagamentoValores = negociacaoDebitoImovelHelper.getColecaoGuiaPagamentoValores();

		BigDecimal valorTotalRestanteServicosACobrar = BigDecimal.ZERO;
		String valorTotalRestanteServicosACobrarStr = form.getValorTotalRestanteServicosACobrar();

		if(!Util.isVazioOuBranco(valorTotalRestanteServicosACobrarStr)){
			valorTotalRestanteServicosACobrar = Util.formatarMoedaRealparaBigDecimal(valorTotalRestanteServicosACobrarStr);
		}

		BigDecimal valorTotalRestanteParcelamentosACobrar = BigDecimal.ZERO;
		String valorTotalRestanteParcelamentosACobrarStr = form.getValorTotalRestanteParcelamentosACobrar();

		if(!Util.isVazioOuBranco(valorTotalRestanteParcelamentosACobrarStr)){
			valorTotalRestanteParcelamentosACobrar = Util.formatarMoedaRealparaBigDecimal(valorTotalRestanteParcelamentosACobrarStr);
		}

		BigDecimal valorTotalAcrescimo = BigDecimal.ZERO;
		String valorTotalAcrescimoStr = form.getValorTotalAcrescimo();

		if(!Util.isVazioOuBranco(valorTotalAcrescimoStr)){
			valorTotalAcrescimo = Util.formatarMoedaRealparaBigDecimal(valorTotalAcrescimoStr);
		}

		BigDecimal valorTotalCreditoARealizar = BigDecimal.ZERO;
		String valorTotalCreditoARealizarStr = form.getValorTotalCreditoARealizar();

		if(!Util.isVazioOuBranco(valorTotalCreditoARealizarStr)){
			valorTotalCreditoARealizar = Util.formatarMoedaRealparaBigDecimal(valorTotalCreditoARealizarStr);
		}

		BigDecimal valorTotalConta = BigDecimal.ZERO;
		String valorTotalContaStr = form.getValorTotalConta();

		if(!Util.isVazioOuBranco(valorTotalContaStr)){
			valorTotalConta = Util.formatarMoedaRealparaBigDecimal(valorTotalContaStr);
		}

		BigDecimal valorTotalGuia = BigDecimal.ZERO;
		String valorTotalGuiaStr = form.getValorTotalGuia();

		if(!Util.isVazioOuBranco(valorTotalGuiaStr)){
			valorTotalGuia = Util.formatarMoedaRealparaBigDecimal(valorTotalGuiaStr);
		}

		BigDecimal valorTotalAtualizacaoMonetaria = BigDecimal.ZERO;
		String valorTotalAtualizacaoMonetariaStr = form.getValorTotalAtualizacaoMonetaria();

		if(!Util.isVazioOuBranco(valorTotalAtualizacaoMonetariaStr)){
			valorTotalAtualizacaoMonetaria = Util.formatarMoedaRealparaBigDecimal(valorTotalAtualizacaoMonetariaStr);
		}

		BigDecimal valorTotalJurosMora = BigDecimal.ZERO;
		String valorTotalJurosMoraStr = form.getValorTotalJurosMora();

		if(!Util.isVazioOuBranco(valorTotalJurosMoraStr)){
			valorTotalJurosMora = Util.formatarMoedaRealparaBigDecimal(valorTotalJurosMoraStr);
		}

		BigDecimal valorTotalMulta = BigDecimal.ZERO;
		String valorTotalMultaStr = form.getValorTotalMulta();

		if(!Util.isVazioOuBranco(valorTotalMultaStr)){
			valorTotalMulta = Util.formatarMoedaRealparaBigDecimal(valorTotalMultaStr);
		}

		BigDecimal valorDebitoTotalAtualizado = BigDecimal.ZERO;
		String valorDebitoTotalAtualizadoStr = form.getValorDebitoTotalAtualizado();

		if(!Util.isVazioOuBranco(valorDebitoTotalAtualizadoStr)){
			valorDebitoTotalAtualizado = Util.formatarMoedaRealparaBigDecimal(valorDebitoTotalAtualizadoStr);
		}

		fachada.efetuarNegociacaoDeDebitos(idImovel, idClienteUsuario, indicadorPessoaFisicaJuridica, cpfCnpjCliente, emailCliente,
						usuarioLogado, negociacaoQuantidadeParcelas, negociacaoValorParcela, negociacaoValorEntrada,
						negociacaoValorDebitoAposDesconto, negociacaoIndicadorPagamentoCartaoCredito, colecaoContaValores,
						colecaoDebitoACobrar, colecaoCreditoARealizar, colecaoGuiaPagamentoValores, valorTotalAcrescimo,
						valorTotalRestanteServicosACobrar, valorTotalRestanteParcelamentosACobrar, negociacaoValorDescontoPagamentoAVista,
						valorTotalCreditoARealizar, inscricaoImovel, enderecoImovel, negociacaoOpcoesParcelamento, valorTotalConta,
						valorTotalGuia, valorTotalAtualizacaoMonetaria, valorTotalJurosMora, valorTotalMulta, valorDebitoTotalAtualizado,
						negociacaoValorDescontoAntiguidade, negociacaoValorDescontoInatividade, negociacaoNumeroMesesEntreParcelas,
						negociacaoNumeroParcelasALancar, negociacaoNumeroMesesInicioCobranca, negociacaoNumeroDiasVencimentoDaEntrada,
						negociacaoTaxaJuros, negociacaoIdRD);

		montarPaginaSucesso(httpServletRequest, "Negociação de Débitos Efetuada com Sucesso.", "Realizar outra Negociação de Débitos",
						"exibirEfetuarNegociacaoDebitosAction.do?menu=sim");

		return retorno;
	}
}
