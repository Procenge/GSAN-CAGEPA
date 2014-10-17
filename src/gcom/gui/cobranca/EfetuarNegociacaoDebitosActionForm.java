
package gcom.gui.cobranca;

import org.apache.struts.action.ActionForm;

/**
 * [UC3031] Efetuar Negociação de Débitos
 * 
 * @author Hebert Falcão
 * @date 01/12/2011
 */
public class EfetuarNegociacaoDebitosActionForm
				extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String idImovel;

	private String idImovelAntes;

	private String inscricaoImovel;

	private String situacaoAgua;

	private String situacaoEsgoto;

	private String numeroQuadra;

	private String enderecoImovel;

	private String valorTotalConta;

	private String valorTotalGuia;

	private String valorTotalAcrescimo;

	private String valorTotalMulta;

	private String valorTotalJurosMora;

	private String valorTotalAtualizacaoMonetaria;

	private String valorTotalCreditoARealizar;

	private String valorTotalRestanteServicosACobrar;

	private String valorTotalRestanteParcelamentosACobrar;

	private String valorDebitoTotalAtualizado;

	private String idClienteUsuario;

	private String nomeCliente;

	private String emailCliente;

	private String cpfCnpjCliente;

	private String cpfCnpjClienteAntes;

	private String indicadorPessoaFisicaJuridica;

	private String negociacaoIdRD;

	private String negociacaoNumeroRD;

	private String negociacaoValorDebitoTotalAtualizado;

	private String negociacaoPercentualTotalDesconto;

	private String negociacaoValorTotalDesconto;

	private String negociacaoValorDebitoAposDesconto;

	private String negociacaoValorEntrada;

	private String negociacaoValorTotalJurosFinanciamento;

	private String negociacaoQuantidadeParcelas;

	private String negociacaoValorParcela;

	private String negociacaoIndicadorPagamentoCartaoCredito;

	private String negociacaoValorDescontoPagamentoAVista;

	private String negociacaoValorDescontoInatividade;

	private String negociacaoValorDescontoAntiguidade;

	private String negociacaoNumeroMesesEntreParcelas;

	private String negociacaoNumeroParcelasALancar;

	private String negociacaoNumeroMesesInicioCobranca;

	private String negociacaoNumeroDiasVencimentoDaEntrada;

	private String negociacaoTaxaJuros;

	public String getIdImovel(){

		return idImovel;
	}

	public void setIdImovel(String idImovel){

		this.idImovel = idImovel;
	}

	public String getInscricaoImovel(){

		return inscricaoImovel;
	}

	public void setInscricaoImovel(String inscricaoImovel){

		this.inscricaoImovel = inscricaoImovel;
	}

	public String getSituacaoAgua(){

		return situacaoAgua;
	}

	public void setSituacaoAgua(String situacaoAgua){

		this.situacaoAgua = situacaoAgua;
	}

	public String getSituacaoEsgoto(){

		return situacaoEsgoto;
	}

	public void setSituacaoEsgoto(String situacaoEsgoto){

		this.situacaoEsgoto = situacaoEsgoto;
	}

	public String getEnderecoImovel(){

		return enderecoImovel;
	}

	public void setEnderecoImovel(String enderecoImovel){

		this.enderecoImovel = enderecoImovel;
	}

	public String getValorTotalRestanteServicosACobrar(){

		return valorTotalRestanteServicosACobrar;
	}

	public void setValorTotalRestanteServicosACobrar(String valorTotalRestanteServicosACobrar){

		this.valorTotalRestanteServicosACobrar = valorTotalRestanteServicosACobrar;
	}

	public String getValorTotalRestanteParcelamentosACobrar(){

		return valorTotalRestanteParcelamentosACobrar;
	}

	public void setValorTotalRestanteParcelamentosACobrar(String valorTotalRestanteParcelamentosACobrar){

		this.valorTotalRestanteParcelamentosACobrar = valorTotalRestanteParcelamentosACobrar;
	}

	public String getValorTotalConta(){

		return valorTotalConta;
	}

	public void setValorTotalConta(String valorTotalConta){

		this.valorTotalConta = valorTotalConta;
	}

	public String getValorTotalGuia(){

		return valorTotalGuia;
	}

	public void setValorTotalGuia(String valorTotalGuia){

		this.valorTotalGuia = valorTotalGuia;
	}

	public String getValorTotalAcrescimo(){

		return valorTotalAcrescimo;
	}

	public void setValorTotalAcrescimo(String valorTotalAcrescimo){

		this.valorTotalAcrescimo = valorTotalAcrescimo;
	}

	public String getValorTotalCreditoARealizar(){

		return valorTotalCreditoARealizar;
	}

	public void setValorTotalCreditoARealizar(String valorTotalCreditoARealizar){

		this.valorTotalCreditoARealizar = valorTotalCreditoARealizar;
	}

	public String getValorDebitoTotalAtualizado(){

		return valorDebitoTotalAtualizado;
	}

	public void setValorDebitoTotalAtualizado(String valorDebitoTotalAtualizado){

		this.valorDebitoTotalAtualizado = valorDebitoTotalAtualizado;
	}

	public String getValorTotalMulta(){

		return valorTotalMulta;
	}

	public void setValorTotalMulta(String valorTotalMulta){

		this.valorTotalMulta = valorTotalMulta;
	}

	public String getValorTotalJurosMora(){

		return valorTotalJurosMora;
	}

	public void setValorTotalJurosMora(String valorTotalJurosMora){

		this.valorTotalJurosMora = valorTotalJurosMora;
	}

	public String getValorTotalAtualizacaoMonetaria(){

		return valorTotalAtualizacaoMonetaria;
	}

	public void setValorTotalAtualizacaoMonetaria(String valorTotalAtualizacaoMonetaria){

		this.valorTotalAtualizacaoMonetaria = valorTotalAtualizacaoMonetaria;
	}

	public String getNomeCliente(){

		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente){

		this.nomeCliente = nomeCliente;
	}

	public String getCpfCnpjCliente(){

		return cpfCnpjCliente;
	}

	public void setCpfCnpjCliente(String cpfCnpjCliente){

		this.cpfCnpjCliente = cpfCnpjCliente;
	}

	public String getEmailCliente(){

		return emailCliente;
	}

	public void setEmailCliente(String emailCliente){

		this.emailCliente = emailCliente;
	}

	public String getIdClienteUsuario(){

		return idClienteUsuario;
	}

	public void setIdClienteUsuario(String idClienteUsuario){

		this.idClienteUsuario = idClienteUsuario;
	}

	public String getNumeroQuadra(){

		return numeroQuadra;
	}

	public void setNumeroQuadra(String numeroQuadra){

		this.numeroQuadra = numeroQuadra;
	}

	public String getIndicadorPessoaFisicaJuridica(){

		return indicadorPessoaFisicaJuridica;
	}

	public void setIndicadorPessoaFisicaJuridica(String indicadorPessoaFisicaJuridica){

		this.indicadorPessoaFisicaJuridica = indicadorPessoaFisicaJuridica;
	}

	public String getNegociacaoIdRD(){

		return negociacaoIdRD;
	}

	public void setNegociacaoIdRD(String negociacaoIdRD){

		this.negociacaoIdRD = negociacaoIdRD;
	}

	public String getNegociacaoNumeroRD(){

		return negociacaoNumeroRD;
	}

	public void setNegociacaoNumeroRD(String negociacaoNumeroRD){

		this.negociacaoNumeroRD = negociacaoNumeroRD;
	}

	public String getNegociacaoValorDebitoTotalAtualizado(){

		return negociacaoValorDebitoTotalAtualizado;
	}

	public void setNegociacaoValorDebitoTotalAtualizado(String negociacaoValorDebitoTotalAtualizado){

		this.negociacaoValorDebitoTotalAtualizado = negociacaoValorDebitoTotalAtualizado;
	}

	public String getNegociacaoPercentualTotalDesconto(){

		return negociacaoPercentualTotalDesconto;
	}

	public void setNegociacaoPercentualTotalDesconto(String negociacaoPercentualTotalDesconto){

		this.negociacaoPercentualTotalDesconto = negociacaoPercentualTotalDesconto;
	}

	public String getNegociacaoValorTotalDesconto(){

		return negociacaoValorTotalDesconto;
	}

	public void setNegociacaoValorTotalDesconto(String negociacaoValorTotalDesconto){

		this.negociacaoValorTotalDesconto = negociacaoValorTotalDesconto;
	}

	public String getNegociacaoValorDebitoAposDesconto(){

		return negociacaoValorDebitoAposDesconto;
	}

	public void setNegociacaoValorDebitoAposDesconto(String negociacaoValorDebitoAposDesconto){

		this.negociacaoValorDebitoAposDesconto = negociacaoValorDebitoAposDesconto;
	}

	public String getNegociacaoValorEntrada(){

		return negociacaoValorEntrada;
	}

	public void setNegociacaoValorEntrada(String negociacaoValorEntrada){

		this.negociacaoValorEntrada = negociacaoValorEntrada;
	}

	public String getNegociacaoValorTotalJurosFinanciamento(){

		return negociacaoValorTotalJurosFinanciamento;
	}

	public void setNegociacaoValorTotalJurosFinanciamento(String negociacaoValorTotalJurosFinanciamento){

		this.negociacaoValorTotalJurosFinanciamento = negociacaoValorTotalJurosFinanciamento;
	}

	public String getNegociacaoQuantidadeParcelas(){

		return negociacaoQuantidadeParcelas;
	}

	public void setNegociacaoQuantidadeParcelas(String negociacaoQuantidadeParcelas){

		this.negociacaoQuantidadeParcelas = negociacaoQuantidadeParcelas;
	}

	public String getNegociacaoValorParcela(){

		return negociacaoValorParcela;
	}

	public void setNegociacaoValorParcela(String negociacaoValorParcela){

		this.negociacaoValorParcela = negociacaoValorParcela;
	}

	public String getIdImovelAntes(){

		return idImovelAntes;
	}

	public void setIdImovelAntes(String idImovelAntes){

		this.idImovelAntes = idImovelAntes;
	}

	public String getCpfCnpjClienteAntes(){

		return cpfCnpjClienteAntes;
	}

	public void setCpfCnpjClienteAntes(String cpfCnpjClienteAntes){

		this.cpfCnpjClienteAntes = cpfCnpjClienteAntes;
	}

	public String getNegociacaoIndicadorPagamentoCartaoCredito(){

		return negociacaoIndicadorPagamentoCartaoCredito;
	}

	public void setNegociacaoIndicadorPagamentoCartaoCredito(String negociacaoIndicadorPagamentoCartaoCredito){

		this.negociacaoIndicadorPagamentoCartaoCredito = negociacaoIndicadorPagamentoCartaoCredito;
	}

	public String getNegociacaoValorDescontoPagamentoAVista(){

		return negociacaoValorDescontoPagamentoAVista;
	}

	public void setNegociacaoValorDescontoPagamentoAVista(String negociacaoValorDescontoPagamentoAVista){

		this.negociacaoValorDescontoPagamentoAVista = negociacaoValorDescontoPagamentoAVista;
	}

	public String getNegociacaoValorDescontoInatividade(){

		return negociacaoValorDescontoInatividade;
	}

	public void setNegociacaoValorDescontoInatividade(String negociacaoValorDescontoInatividade){

		this.negociacaoValorDescontoInatividade = negociacaoValorDescontoInatividade;
	}

	public String getNegociacaoValorDescontoAntiguidade(){

		return negociacaoValorDescontoAntiguidade;
	}

	public void setNegociacaoValorDescontoAntiguidade(String negociacaoValorDescontoAntiguidade){

		this.negociacaoValorDescontoAntiguidade = negociacaoValorDescontoAntiguidade;
	}

	public String getNegociacaoNumeroMesesEntreParcelas(){

		return negociacaoNumeroMesesEntreParcelas;
	}

	public void setNegociacaoNumeroMesesEntreParcelas(String negociacaoNumeroMesesEntreParcelas){

		this.negociacaoNumeroMesesEntreParcelas = negociacaoNumeroMesesEntreParcelas;
	}

	public String getNegociacaoNumeroParcelasALancar(){

		return negociacaoNumeroParcelasALancar;
	}

	public void setNegociacaoNumeroParcelasALancar(String negociacaoNumeroParcelasALancar){

		this.negociacaoNumeroParcelasALancar = negociacaoNumeroParcelasALancar;
	}

	public String getNegociacaoNumeroMesesInicioCobranca(){

		return negociacaoNumeroMesesInicioCobranca;
	}

	public void setNegociacaoNumeroMesesInicioCobranca(String negociacaoNumeroMesesInicioCobranca){

		this.negociacaoNumeroMesesInicioCobranca = negociacaoNumeroMesesInicioCobranca;
	}

	public String getNegociacaoNumeroDiasVencimentoDaEntrada(){

		return negociacaoNumeroDiasVencimentoDaEntrada;
	}

	public void setNegociacaoNumeroDiasVencimentoDaEntrada(String negociacaoNumeroDiasVencimentoDaEntrada){

		this.negociacaoNumeroDiasVencimentoDaEntrada = negociacaoNumeroDiasVencimentoDaEntrada;
	}

	public String getNegociacaoTaxaJuros(){

		return negociacaoTaxaJuros;
	}

	public void setNegociacaoTaxaJuros(String negociacaoTaxaJuros){

		this.negociacaoTaxaJuros = negociacaoTaxaJuros;
	}

}
