
package gcom.gui.cobranca;

import org.apache.struts.action.ActionForm;

/**
 * [UC3031] Efetuar Negociação de Débitos Parcelamento Popup
 * 
 * @author Hebert Falcão
 * @date 01/12/2011
 */
public class EfetuarNegociacaoDebitosParcelamentoPopupActionForm
				extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String idResolucaoDiretoria;

	private String numeroResolucaoDiretoria;

	private String valorEntradaInformado;

	private String valorEntradaInformadoAntes;

	private String descontoAcrescimosImpontualidade;

	private String descontoAntiguidadeDebito;

	private String descontoInatividadeLigacaoAgua;

	private String descontoSancoesRDEspecial;

	private String descontoTarifaSocialRDEspecial;

	private String valorTotalDescontos;

	private String indicadorPagamentoCartaoCredito;

	private String valorDebitoTotalAtualizado;

	private String valorTotalImpostosConta;

	private String valorDescontoPagamentoAVista;

	private String valorPagamentoAVista;

	private String indicadorQuantidadeParcelas;

	public String getIdResolucaoDiretoria(){

		return idResolucaoDiretoria;
	}

	public void setIdResolucaoDiretoria(String idResolucaoDiretoria){

		this.idResolucaoDiretoria = idResolucaoDiretoria;
	}

	public String getNumeroResolucaoDiretoria(){

		return numeroResolucaoDiretoria;
	}

	public void setNumeroResolucaoDiretoria(String numeroResolucaoDiretoria){

		this.numeroResolucaoDiretoria = numeroResolucaoDiretoria;
	}

	public String getValorEntradaInformado(){

		return valorEntradaInformado;
	}

	public void setValorEntradaInformado(String valorEntradaInformado){

		this.valorEntradaInformado = valorEntradaInformado;
	}

	public String getDescontoAcrescimosImpontualidade(){

		return descontoAcrescimosImpontualidade;
	}

	public void setDescontoAcrescimosImpontualidade(String descontoAcrescimosImpontualidade){

		this.descontoAcrescimosImpontualidade = descontoAcrescimosImpontualidade;
	}

	public String getDescontoAntiguidadeDebito(){

		return descontoAntiguidadeDebito;
	}

	public void setDescontoAntiguidadeDebito(String descontoAntiguidadeDebito){

		this.descontoAntiguidadeDebito = descontoAntiguidadeDebito;
	}

	public String getDescontoInatividadeLigacaoAgua(){

		return descontoInatividadeLigacaoAgua;
	}

	public void setDescontoInatividadeLigacaoAgua(String descontoInatividadeLigacaoAgua){

		this.descontoInatividadeLigacaoAgua = descontoInatividadeLigacaoAgua;
	}

	public String getDescontoSancoesRDEspecial(){

		return descontoSancoesRDEspecial;
	}

	public void setDescontoSancoesRDEspecial(String descontoSancoesRDEspecial){

		this.descontoSancoesRDEspecial = descontoSancoesRDEspecial;
	}

	public String getDescontoTarifaSocialRDEspecial(){

		return descontoTarifaSocialRDEspecial;
	}

	public void setDescontoTarifaSocialRDEspecial(String descontoTarifaSocialRDEspecial){

		this.descontoTarifaSocialRDEspecial = descontoTarifaSocialRDEspecial;
	}

	public String getValorTotalDescontos(){

		return valorTotalDescontos;
	}

	public void setValorTotalDescontos(String valorTotalDescontos){

		this.valorTotalDescontos = valorTotalDescontos;
	}

	public String getIndicadorPagamentoCartaoCredito(){

		return indicadorPagamentoCartaoCredito;
	}

	public void setIndicadorPagamentoCartaoCredito(String indicadorPagamentoCartaoCredito){

		this.indicadorPagamentoCartaoCredito = indicadorPagamentoCartaoCredito;
	}

	public String getValorDebitoTotalAtualizado(){

		return valorDebitoTotalAtualizado;
	}

	public void setValorDebitoTotalAtualizado(String valorDebitoTotalAtualizado){

		this.valorDebitoTotalAtualizado = valorDebitoTotalAtualizado;
	}

	public String getValorTotalImpostosConta(){

		return valorTotalImpostosConta;
	}

	public void setValorTotalImpostosConta(String valorTotalImpostosConta){

		this.valorTotalImpostosConta = valorTotalImpostosConta;
	}

	public String getValorDescontoPagamentoAVista(){

		return valorDescontoPagamentoAVista;
	}

	public void setValorDescontoPagamentoAVista(String valorDescontoPagamentoAVista){

		this.valorDescontoPagamentoAVista = valorDescontoPagamentoAVista;
	}

	public String getValorPagamentoAVista(){

		return valorPagamentoAVista;
	}

	public void setValorPagamentoAVista(String valorPagamentoAVista){

		this.valorPagamentoAVista = valorPagamentoAVista;
	}

	public String getIndicadorQuantidadeParcelas(){

		return indicadorQuantidadeParcelas;
	}

	public void setIndicadorQuantidadeParcelas(String indicadorQuantidadeParcelas){

		this.indicadorQuantidadeParcelas = indicadorQuantidadeParcelas;
	}

	public String getValorEntradaInformadoAntes(){

		return valorEntradaInformadoAntes;
	}

	public void setValorEntradaInformadoAntes(String valorEntradaInformadoAntes){

		this.valorEntradaInformadoAntes = valorEntradaInformadoAntes;
	}

}
