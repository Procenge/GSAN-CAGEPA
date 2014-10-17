
package gcom.faturamento.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class DescricaoServicosContaHelper
				implements Serializable {

	private static final long serialVersionUID = 1L;

	private String tarifa;

	private String categoria;

	private String faixa;

	private String valorTarifa;

	private String valorConsumo;

	private String totalizacao;

	private String descricaoEsgoto;

	private String valorEsgoto;

	private String descricaoDebito;

	private String valorDebito;

	private String descricaoCredito;

	private String valorCredito;

	private String descricaoImposto;

	private String valorImposto;

	private BigDecimal valorConsumoSemFormatacao;

	public DescricaoServicosContaHelper() {

	}

	public String getTarifa(){

		return tarifa;
	}

	public void setTarifa(String tarifa){

		this.tarifa = tarifa;
	}

	public String getCategoria(){

		return categoria;
	}

	public void setCategoria(String categoria){

		this.categoria = categoria;
	}

	public String getFaixa(){

		return faixa;
	}

	public void setFaixa(String faixa){

		this.faixa = faixa;
	}

	public String getValorTarifa(){

		return valorTarifa;
	}

	public void setValorTarifa(String valorTarifa){

		this.valorTarifa = valorTarifa;
	}

	public String getValorConsumo(){

		return valorConsumo;
	}

	public void setValorConsumo(String valorConsumo){

		this.valorConsumo = valorConsumo;
	}

	public String getTotalizacao(){

		return totalizacao;
	}

	public void setTotalizacao(String totalizacao){

		this.totalizacao = totalizacao;
	}

	public String getDescricaoEsgoto(){

		return descricaoEsgoto;
	}

	public void setDescricaoEsgoto(String descricaoEsgoto){

		this.descricaoEsgoto = descricaoEsgoto;
	}

	public String getDescricaoDebito(){

		return descricaoDebito;
	}

	public void setDescricaoDebito(String descricaoDebito){

		this.descricaoDebito = descricaoDebito;
	}

	public String getValorDebito(){

		return valorDebito;
	}

	public void setValorDebito(String valorDebito){

		this.valorDebito = valorDebito;
	}

	public String getDescricaoCredito(){

		return descricaoCredito;
	}

	public void setDescricaoCredito(String descricaoCredito){

		this.descricaoCredito = descricaoCredito;
	}

	public String getValorCredito(){

		return valorCredito;
	}

	public void setValorCredito(String valorCredito){

		this.valorCredito = valorCredito;
	}

	public String getDescricaoImposto(){

		return descricaoImposto;
	}

	public void setDescricaoImposto(String descricaoImposto){

		this.descricaoImposto = descricaoImposto;
	}

	public String getValorImposto(){

		return valorImposto;
	}

	public void setValorImposto(String valorImposto){

		this.valorImposto = valorImposto;
	}

	public String getValorEsgoto(){

		return valorEsgoto;
	}

	public void setValorEsgoto(String valorEsgoto){

		this.valorEsgoto = valorEsgoto;
	}

	public BigDecimal getValorConsumoSemFormatacao(){

		return valorConsumoSemFormatacao;
	}

	public void setValorConsumoSemFormatacao(BigDecimal valorConsumoSemFormatacao){

		this.valorConsumoSemFormatacao = valorConsumoSemFormatacao;
	}

}
