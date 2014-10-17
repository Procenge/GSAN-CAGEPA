
package gcom.relatorio.cobranca.cobrancaadministrativa;

import gcom.relatorio.RelatorioBean;

import java.math.BigDecimal;

/**
 * [UC3060] Manter Imóvel Cobrança Administrativa
 * 
 * @author Carlos Chrystian Ramos
 * @date 22/02/2013
 */
public class RelatorioImoveisEmCobrancaAdministrativaDetalheGuiaBean
				implements RelatorioBean {

	// 1.3.11.1.2.2.1. Guia
	private String idGuia;

	// 1.3.11.1.2.2.2. Prestação
	private String numeroPrestacaoGuia;

	// 1.3.11.1.2.2.3. Data de Emissão
	private String dataEmissao;

	// 1.3.11.1.2.2.4. Tipo de Débito
	private String descricaoTipoDebito;

	// 1.3.11.1.2.2.5. Valor Prestação
	private BigDecimal valorPrestacao;

	// 1.3.11.1.2.2.6. Situação do Débito do Item
	private String descricaoSituacaoDebito;

	// 1.3.11.1.2.2.7. Data da Situação do Débito do Item
	private String dataSituacaoDebito;

	public String getIdGuia(){

		return idGuia;
	}


	public void setIdGuia(String idGuia){

		this.idGuia = idGuia;
	}

	public String getNumeroPrestacaoGuia(){

		return numeroPrestacaoGuia;
	}

	public void setNumeroPrestacaoGuia(String numeroPrestacaoGuia){

		this.numeroPrestacaoGuia = numeroPrestacaoGuia;
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

	public BigDecimal getValorPrestacao(){

		return valorPrestacao;
	}

	public void setValorPrestacao(BigDecimal valorPrestacao){

		this.valorPrestacao = valorPrestacao;
	}

	public String getDescricaoSituacaoDebito(){

		return descricaoSituacaoDebito;
	}

	public void setDescricaoSituacaoDebito(String descricaoSituacaoDebito){

		this.descricaoSituacaoDebito = descricaoSituacaoDebito;
	}

	public String getDataSituacaoDebito(){

		return dataSituacaoDebito;
	}

	public void setDataSituacaoDebito(String dataSituacaoDebito){

		this.dataSituacaoDebito = dataSituacaoDebito;
	}

}
