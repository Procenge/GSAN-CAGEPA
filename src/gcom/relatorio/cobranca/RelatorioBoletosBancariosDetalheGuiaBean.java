
package gcom.relatorio.cobranca;

import gcom.relatorio.RelatorioBean;

import java.math.BigDecimal;

/**
 * [UC3023] Manter Boleto Bancário
 * 
 * @author Hebert Falcão
 * @date 12/10/2011
 */
public class RelatorioBoletosBancariosDetalheGuiaBean
				implements RelatorioBean {

	private String idGuia;

	private String numeroPrestacoes;

	private String dataEmissao;

	private String descricaoTipoDebito;

	private BigDecimal valorPrestacaoTipoDebito;

	public String getIdGuia(){

		return idGuia;
	}

	public void setIdGuia(String idGuia){

		this.idGuia = idGuia;
	}

	public String getNumeroPrestacoes(){

		return numeroPrestacoes;
	}

	public void setNumeroPrestacoes(String numeroPrestacoes){

		this.numeroPrestacoes = numeroPrestacoes;
	}

	public String getDataEmissao(){

		return dataEmissao;
	}

	public void setDataEmissao(String dataEmissao){

		this.dataEmissao = dataEmissao;
	}

	public String getDescricaoTipoDebito(){

		return descricaoTipoDebito;
	}

	public void setDescricaoTipoDebito(String descricaoTipoDebito){

		this.descricaoTipoDebito = descricaoTipoDebito;
	}

	public BigDecimal getValorPrestacaoTipoDebito(){

		return valorPrestacaoTipoDebito;
	}

	public void setValorPrestacaoTipoDebito(BigDecimal valorPrestacaoTipoDebito){

		this.valorPrestacaoTipoDebito = valorPrestacaoTipoDebito;
	}

}
