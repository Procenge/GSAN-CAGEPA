
package gcom.relatorio.cobranca.cobrancaadministrativa;

import gcom.relatorio.RelatorioBean;

/**
 * [UC3060] Manter Imóvel Cobrança Administrativa
 * 
 * @author Carlos Chrystian Ramos
 * @date 25/02/2013
 */
public class RelatorioImoveisEmCobrancaAdministrativaDetalheSituacaoBean
				implements RelatorioBean {

	// 1.4.3. Para cada grupo de itens do débito em cobrança administrativa com a mesma situação
	// 1.4.3.1. Situação
	private String descricaoSituacaoDebitoItem;

	// 1.4.3.2. Quantidade Ocorrencias
	private String quantidadeOcorrenciasItem;

	// 1.4.3.3. Valor Item Cobrado
	private String valorItemCobrado;

	public String getDescricaoSituacaoDebitoItem(){

		return descricaoSituacaoDebitoItem;
	}

	public void setDescricaoSituacaoDebitoItem(String descricaoSituacaoDebitoItem){

		this.descricaoSituacaoDebitoItem = descricaoSituacaoDebitoItem;
	}

	public String getQuantidadeOcorrenciasItem(){

		return quantidadeOcorrenciasItem;
	}

	public void setQuantidadeOcorrenciasItem(String quantidadeOcorrenciasItem){

		this.quantidadeOcorrenciasItem = quantidadeOcorrenciasItem;
	}

	public String getValorItemCobrado(){

		return valorItemCobrado;
	}

	public void setValorItemCobrado(String valorItemCobrado){

		this.valorItemCobrado = valorItemCobrado;
	}

}
