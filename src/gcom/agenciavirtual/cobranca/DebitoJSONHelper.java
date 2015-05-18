
package gcom.agenciavirtual.cobranca;

import java.math.BigDecimal;
import java.util.Date;

public class DebitoJSONHelper {

	private Integer idConta;

	private Integer referencia;

	private Integer consumo;

	private BigDecimal valorConta;

	private Date vencimento;

	private Boolean bloquearSegundaVia;

	private Boolean exibirAcrescimos;

	private BigDecimal valorAcrescimos;

	private Boolean emRevisao;

	private Integer idGuiaPagamento;

	private BigDecimal valorGuiaPagamento;


	private Short numeroPrestacao;

	private BigDecimal valorMulta;

	private BigDecimal valorJuros;

	private BigDecimal valorCorrecaoMonetaria;

	private BigDecimal valorJurosSucumbencia;

	public Integer getIdConta(){

		return idConta;
	}

	public void setIdConta(Integer idConta){

		this.idConta = idConta;
	}

	public Integer getReferencia(){

		return referencia;
	}

	public void setReferencia(Integer referencia){

		this.referencia = referencia;
	}

	public Integer getConsumo(){

		return consumo;
	}

	public void setConsumo(Integer consumo){

		this.consumo = consumo;
	}

	public BigDecimal getValorConta(){

		return valorConta;
	}

	public void setValorConta(BigDecimal valorConta){

		this.valorConta = valorConta;
	}

	public Date getVencimento(){

		return vencimento;
	}

	public void setVencimento(Date vencimento){

		this.vencimento = vencimento;
	}

	public Boolean getBloquearSegundaVia(){

		return bloquearSegundaVia;
	}

	public void setBloquearSegundaVia(Boolean bloquearSegundaVia){

		this.bloquearSegundaVia = bloquearSegundaVia;
	}

	public Boolean getExibirAcrescimos(){

		return exibirAcrescimos;
	}

	public void setExibirAcrescimos(Boolean exibirAcrescimos){

		this.exibirAcrescimos = exibirAcrescimos;
	}

	public BigDecimal getValorAcrescimos(){

		return valorAcrescimos;
	}

	public void setValorAcrescimos(BigDecimal valorAcrescimos){

		this.valorAcrescimos = valorAcrescimos;
	}

	public Boolean getEmRevisao(){

		return emRevisao;
	}

	public void setEmRevisao(Boolean emRevisao){

		this.emRevisao = emRevisao;
	}

	public boolean isEmRevisao(){

		return emRevisao == null ? false : emRevisao.booleanValue();
	}


	public Integer getIdGuiaPagamento(){

		return idGuiaPagamento;
	}

	public void setIdGuiaPagamento(Integer idGuiaPagamento){

		this.idGuiaPagamento = idGuiaPagamento;
	}

	public BigDecimal getValorGuiaPagamento(){

		return valorGuiaPagamento;
	}

	public void setValorGuiaPagamento(BigDecimal valorGuiaPagamento){

		this.valorGuiaPagamento = valorGuiaPagamento;
	}

	public Short getNumeroPrestacao(){

		return numeroPrestacao;
	}

	public void setNumeroPrestacao(Short numeroPrestacao){

		this.numeroPrestacao = numeroPrestacao;
	}

	public BigDecimal getValorMulta(){

		return valorMulta;
	}

	public void setValorMulta(BigDecimal valorMulta){

		this.valorMulta = valorMulta;
	}

	public BigDecimal getValorJuros(){

		return valorJuros;
	}

	public void setValorJuros(BigDecimal valorJuros){

		this.valorJuros = valorJuros;
	}

	public BigDecimal getValorCorrecaoMonetaria(){

		return valorCorrecaoMonetaria;
	}

	public void setValorCorrecaoMonetaria(BigDecimal valorCorrecaoMonetaria){

		this.valorCorrecaoMonetaria = valorCorrecaoMonetaria;
	}

	public BigDecimal getValorJurosSucumbencia(){

		return valorJurosSucumbencia;
	}

	public void setValorJurosSucumbencia(BigDecimal valorJurosSucumbencia){

		this.valorJurosSucumbencia = valorJurosSucumbencia;
	}

}
