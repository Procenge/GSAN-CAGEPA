
package gcom.agenciavirtual.imovel;

import java.io.Serializable;

public class ConsultarPagamentoJSONHelper
				implements Serializable {

	private String valorPagamento;

	private String datapagamento;

	private String arrecadador;

	private String situacaoAnterior;

	private String situacaoAtual;

	// Pagamentos das Contas
	private String mesAnoConta;

	private String valorConta;

	// Pagamento das Guias de Pagamento
	private String cliente;

	private String numeroDocumento;

	private String valorPrestacao;

	// Pagamento dos Débitos a Cobrar
	private String tipoDebito;

	private String valorSerCobrado;

	private String prestacao;

	public String getValorPagamento(){

		return valorPagamento;
	}

	public void setValorPagamento(String valorPagamento){

		this.valorPagamento = valorPagamento;
	}

	public String getDatapagamento(){

		return datapagamento;
	}

	public void setDatapagamento(String datapagamento){

		this.datapagamento = datapagamento;
	}

	public String getArrecadador(){

		return arrecadador;
	}

	public void setArrecadador(String arrecadador){

		this.arrecadador = arrecadador;
	}

	public String getSituacaoAnterior(){

		return situacaoAnterior;
	}

	public void setSituacaoAnterior(String situacaoAnterior){

		this.situacaoAnterior = situacaoAnterior;
	}

	public String getSituacaoAtual(){

		return situacaoAtual;
	}

	public void setSituacaoAtual(String situacaoAtual){

		this.situacaoAtual = situacaoAtual;
	}

	public String getMesAnoConta(){

		return mesAnoConta;
	}

	public void setMesAnoConta(String mesAnoConta){

		this.mesAnoConta = mesAnoConta;
	}

	public String getValorConta(){

		return valorConta;
	}

	public void setValorConta(String valorConta){

		this.valorConta = valorConta;
	}

	public String getCliente(){

		return cliente;
	}

	public void setCliente(String cliente){

		this.cliente = cliente;
	}

	public String getNumeroDocumento(){

		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento){

		this.numeroDocumento = numeroDocumento;
	}

	public String getValorPrestacao(){

		return valorPrestacao;
	}

	public void setValorPrestacao(String valorPrestacao){

		this.valorPrestacao = valorPrestacao;
	}

	public String getTipoDebito(){

		return tipoDebito;
	}

	public void setTipoDebito(String tipoDebito){

		this.tipoDebito = tipoDebito;
	}

	public String getValorSerCobrado(){

		return valorSerCobrado;
	}

	public void setValorSerCobrado(String valorSerCobrado){

		this.valorSerCobrado = valorSerCobrado;
	}

	public String getPrestacao(){

		return prestacao;
	}

	public void setPrestacao(String prestacao){

		this.prestacao = prestacao;
	}

}
