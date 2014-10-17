
package gcom.relatorio.cobranca.cobrancaadministrativa;

import gcom.relatorio.RelatorioBean;

/**
 * [UC3060] Manter Imóvel Cobrança Administrativa
 * 
 * @author Carlos Chrystian Ramos
 * @date 25/02/2013
 */
public class RelatorioImoveisEmCobrancaAdministrativaDetalheRemuneracaoBean
				implements RelatorioBean {

	// 1.3.12.3.1. Tipo de Remuneração:
	private String tipoRemuneracao;

	// 1.3.12.3.2. Percentual de Remuneração:
	private String percentualRemuneracao;

	// 1.3.12.3.3. Valor da Remuneração:
	private String valorRemuneracao;

	public String getTipoRemuneracao(){

		return tipoRemuneracao;
	}

	public void setTipoRemuneracao(String tipoRemuneracao){

		this.tipoRemuneracao = tipoRemuneracao;
	}

	public String getPercentualRemuneracao(){

		return percentualRemuneracao;
	}

	public void setPercentualRemuneracao(String percentualRemuneracao){

		this.percentualRemuneracao = percentualRemuneracao;
	}

	public String getValorRemuneracao(){

		return valorRemuneracao;
	}

	public void setValorRemuneracao(String valorRemuneracao){

		this.valorRemuneracao = valorRemuneracao;
	}

}
