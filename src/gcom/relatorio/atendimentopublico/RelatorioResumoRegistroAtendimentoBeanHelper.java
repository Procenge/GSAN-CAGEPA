
package gcom.relatorio.atendimentopublico;

import gcom.relatorio.RelatorioBean;

/**
 * [UC0XXX] Relatório Resumo Registro Atendimento
 * 
 * @author Anderson Italo
 * @date 22/03/2011
 */
public class RelatorioResumoRegistroAtendimentoBeanHelper
				implements RelatorioBean {

	// dados dos totalizadores
	private String idTipoSolicitacaoTotal;

	private String descricaoTipoSolicitacaoTotal;

	private String idEspecificacaoTotal;

	private String descricaoEspecificacaoTotal;

	private String totalPorTipoSolicitacao;

	private String totalPorEspecificacao;

	private String percentualPorTipoSolicitacaoTotal;

	private String percentualPorEspecificacaoTotal;

	private String imprimirTotalizadorTipoSolicitacao;

	private String totalGeral;

	private String imprimirTotalizadorGeral;

	public String getIdTipoSolicitacaoTotal(){

		return idTipoSolicitacaoTotal;
	}

	public void setIdTipoSolicitacaoTotal(String idTipoSolicitacaoTotal){

		this.idTipoSolicitacaoTotal = idTipoSolicitacaoTotal;
	}

	public String getDescricaoTipoSolicitacaoTotal(){

		return descricaoTipoSolicitacaoTotal;
	}

	public void setDescricaoTipoSolicitacaoTotal(String descricaoTipoSolicitacaoTotal){

		this.descricaoTipoSolicitacaoTotal = descricaoTipoSolicitacaoTotal;
	}

	public String getIdEspecificacaoTotal(){

		return idEspecificacaoTotal;
	}

	public void setIdEspecificacaoTotal(String idEspecificacaoTotal){

		this.idEspecificacaoTotal = idEspecificacaoTotal;
	}

	public String getDescricaoEspecificacaoTotal(){

		return descricaoEspecificacaoTotal;
	}

	public void setDescricaoEspecificacaoTotal(String descricaoEspecificacaoTotal){

		this.descricaoEspecificacaoTotal = descricaoEspecificacaoTotal;
	}

	public String getTotalPorTipoSolicitacao(){

		return totalPorTipoSolicitacao;
	}

	public void setTotalPorTipoSolicitacao(String totalPorTipoSolicitacao){

		this.totalPorTipoSolicitacao = totalPorTipoSolicitacao;
	}

	public String getTotalPorEspecificacao(){

		return totalPorEspecificacao;
	}

	public void setTotalPorEspecificacao(String totalPorEspecificacao){

		this.totalPorEspecificacao = totalPorEspecificacao;
	}

	public String getPercentualPorTipoSolicitacaoTotal(){

		return percentualPorTipoSolicitacaoTotal;
	}

	public void setPercentualPorTipoSolicitacaoTotal(String percentualPorTipoSolicitacaoTotal){

		this.percentualPorTipoSolicitacaoTotal = percentualPorTipoSolicitacaoTotal;
	}

	public String getPercentualPorEspecificacaoTotal(){

		return percentualPorEspecificacaoTotal;
	}

	public void setPercentualPorEspecificacaoTotal(String percentualPorEspecificacaoTotal){

		this.percentualPorEspecificacaoTotal = percentualPorEspecificacaoTotal;
	}

	public String getImprimirTotalizadorTipoSolicitacao(){

		return imprimirTotalizadorTipoSolicitacao;
	}

	public void setImprimirTotalizadorTipoSolicitacao(String imprimirTotalizadorTipoSolicitacao){

		this.imprimirTotalizadorTipoSolicitacao = imprimirTotalizadorTipoSolicitacao;
	}

	public String getTotalGeral(){

		return totalGeral;
	}

	public void setTotalGeral(String totalGeral){

		this.totalGeral = totalGeral;
	}

	public String getImprimirTotalizadorGeral(){

		return imprimirTotalizadorGeral;
	}

	public void setImprimirTotalizadorGeral(String imprimirTotalizadorGeral){

		this.imprimirTotalizadorGeral = imprimirTotalizadorGeral;
	}
}
