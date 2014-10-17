
package gcom.relatorio.atendimentopublico;

import gcom.relatorio.RelatorioBean;

/**
 * [UC0XXX] Relatório Resumo Ordens de Serviço Encerradas
 * 
 * @author Anderson Italo
 * @date 13/05/2011
 */
public class RelatorioResumoOrdensServicoEncerradasBean
				implements RelatorioBean {

	private String idTipoServico;

	private String descricaoTipoServico;

	private String idUnidade;

	private String descricaoUnidade;

	private String siglaUnidade;

	private String qtdEncerradaPorTipoServico;

	private String qtdEncerradaComExecucaoPorTipoServico;

	private String qtdEncerradaOutrosPorTipoServico;

	private String percentualPorTipoServico;

	private String percentualComExecucaoPorTipoServico;

	private String percentualOutrosPorTipoServico;

	private String qtdEncerradaPorUnidade;

	private String qtdEncerradaComExecucaoPorUnidade;

	private String qtdEncerradaOutrosPorUnidade;

	private String percentualPorUnidade;

	private String percentualComExecucaoPorUnidade;

	private String percentualOutrosPorUnidade;

	private String qtdEncerradaComExecucaoNoPrazoPorTipoServico;

	private String qtdEncerradaComExecucaoForaPrazoPorTipoServico;

	private String percentualEncerradaComExecucaoNoPrazoPorTipoServico;

	private String percentualEncerradaComExecucaoForaPrazoPorTipoServico;

	private String qtdEncerradaComExecucaoNoPrazoPorUnidade;

	private String qtdEncerradaComExecucaoForaPrazoPorUnidade;

	private String percentualEncerradaComExecucaoNoPrazoPorUnidade;

	private String percentualEncerradaComExecucaoForaPrazoPorUnidade;

	public String getIdTipoServico(){

		return idTipoServico;
	}

	public void setIdTipoServico(String idTipoServico){

		this.idTipoServico = idTipoServico;
	}

	public String getDescricaoTipoServico(){

		return descricaoTipoServico;
	}

	public void setDescricaoTipoServico(String descricaoTipoServico){

		this.descricaoTipoServico = descricaoTipoServico;
	}

	public String getIdUnidade(){

		return idUnidade;
	}

	public void setIdUnidade(String idUnidade){

		this.idUnidade = idUnidade;
	}

	public String getDescricaoUnidade(){

		return descricaoUnidade;
	}

	public void setDescricaoUnidade(String descricaoUnidade){

		this.descricaoUnidade = descricaoUnidade;
	}

	public String getSiglaUnidade(){

		return siglaUnidade;
	}

	public void setSiglaUnidade(String siglaUnidade){

		this.siglaUnidade = siglaUnidade;
	}

	public String getQtdEncerradaPorTipoServico(){

		return qtdEncerradaPorTipoServico;
	}

	public void setQtdEncerradaPorTipoServico(String qtdEncerradaPorTipoServico){

		this.qtdEncerradaPorTipoServico = qtdEncerradaPorTipoServico;
	}

	public String getQtdEncerradaComExecucaoPorTipoServico(){

		return qtdEncerradaComExecucaoPorTipoServico;
	}

	public void setQtdEncerradaComExecucaoPorTipoServico(String qtdEncerradaComExecucaoPorTipoServico){

		this.qtdEncerradaComExecucaoPorTipoServico = qtdEncerradaComExecucaoPorTipoServico;
	}

	public String getQtdEncerradaOutrosPorTipoServico(){

		return qtdEncerradaOutrosPorTipoServico;
	}

	public void setQtdEncerradaOutrosPorTipoServico(String qtdEncerradaOutrosPorTipoServico){

		this.qtdEncerradaOutrosPorTipoServico = qtdEncerradaOutrosPorTipoServico;
	}

	public String getPercentualPorTipoServico(){

		return percentualPorTipoServico;
	}

	public void setPercentualPorTipoServico(String percentualPorTipoServico){

		this.percentualPorTipoServico = percentualPorTipoServico;
	}

	public String getPercentualComExecucaoPorTipoServico(){

		return percentualComExecucaoPorTipoServico;
	}

	public void setPercentualComExecucaoPorTipoServico(String percentualComExecucaoPorTipoServico){

		this.percentualComExecucaoPorTipoServico = percentualComExecucaoPorTipoServico;
	}

	public String getPercentualOutrosPorTipoServico(){

		return percentualOutrosPorTipoServico;
	}

	public void setPercentualOutrosPorTipoServico(String percentualOutrosPorTipoServico){

		this.percentualOutrosPorTipoServico = percentualOutrosPorTipoServico;
	}

	public String getQtdEncerradaPorUnidade(){

		return qtdEncerradaPorUnidade;
	}

	public void setQtdEncerradaPorUnidade(String qtdEncerradaPorUnidade){

		this.qtdEncerradaPorUnidade = qtdEncerradaPorUnidade;
	}

	public String getQtdEncerradaComExecucaoPorUnidade(){

		return qtdEncerradaComExecucaoPorUnidade;
	}

	public void setQtdEncerradaComExecucaoPorUnidade(String qtdEncerradaComExecucaoPorUnidade){

		this.qtdEncerradaComExecucaoPorUnidade = qtdEncerradaComExecucaoPorUnidade;
	}

	public String getQtdEncerradaOutrosPorUnidade(){

		return qtdEncerradaOutrosPorUnidade;
	}

	public void setQtdEncerradaOutrosPorUnidade(String qtdEncerradaOutrosPorUnidade){

		this.qtdEncerradaOutrosPorUnidade = qtdEncerradaOutrosPorUnidade;
	}

	public String getPercentualPorUnidade(){

		return percentualPorUnidade;
	}

	public void setPercentualPorUnidade(String percentualPorUnidade){

		this.percentualPorUnidade = percentualPorUnidade;
	}

	public String getPercentualComExecucaoPorUnidade(){

		return percentualComExecucaoPorUnidade;
	}

	public void setPercentualComExecucaoPorUnidade(String percentualComExecucaoPorUnidade){

		this.percentualComExecucaoPorUnidade = percentualComExecucaoPorUnidade;
	}

	public String getPercentualOutrosPorUnidade(){

		return percentualOutrosPorUnidade;
	}

	public void setPercentualOutrosPorUnidade(String percentualOutrosPorUnidade){

		this.percentualOutrosPorUnidade = percentualOutrosPorUnidade;
	}

	public String getQtdEncerradaComExecucaoNoPrazoPorTipoServico(){

		return qtdEncerradaComExecucaoNoPrazoPorTipoServico;
	}

	public void setQtdEncerradaComExecucaoNoPrazoPorTipoServico(String qtdEncerradaComExecucaoNoPrazoPorTipoServico){

		this.qtdEncerradaComExecucaoNoPrazoPorTipoServico = qtdEncerradaComExecucaoNoPrazoPorTipoServico;
	}

	public String getQtdEncerradaComExecucaoForaPrazoPorTipoServico(){

		return qtdEncerradaComExecucaoForaPrazoPorTipoServico;
	}

	public void setQtdEncerradaComExecucaoForaPrazoPorTipoServico(String qtdEncerradaComExecucaoForaPrazoPorTipoServico){

		this.qtdEncerradaComExecucaoForaPrazoPorTipoServico = qtdEncerradaComExecucaoForaPrazoPorTipoServico;
	}

	public String getPercentualEncerradaComExecucaoNoPrazoPorTipoServico(){

		return percentualEncerradaComExecucaoNoPrazoPorTipoServico;
	}

	public void setPercentualEncerradaComExecucaoNoPrazoPorTipoServico(String percentualEncerradaComExecucaoNoPrazoPorTipoServico){

		this.percentualEncerradaComExecucaoNoPrazoPorTipoServico = percentualEncerradaComExecucaoNoPrazoPorTipoServico;
	}

	public String getPercentualEncerradaComExecucaoForaPrazoPorTipoServico(){

		return percentualEncerradaComExecucaoForaPrazoPorTipoServico;
	}

	public void setPercentualEncerradaComExecucaoForaPrazoPorTipoServico(String percentualEncerradaComExecucaoForaPrazoPorTipoServico){

		this.percentualEncerradaComExecucaoForaPrazoPorTipoServico = percentualEncerradaComExecucaoForaPrazoPorTipoServico;
	}

	public String getQtdEncerradaComExecucaoNoPrazoPorUnidade(){

		return qtdEncerradaComExecucaoNoPrazoPorUnidade;
	}

	public void setQtdEncerradaComExecucaoNoPrazoPorUnidade(String qtdEncerradaComExecucaoNoPrazoPorUnidade){

		this.qtdEncerradaComExecucaoNoPrazoPorUnidade = qtdEncerradaComExecucaoNoPrazoPorUnidade;
	}

	public String getQtdEncerradaComExecucaoForaPrazoPorUnidade(){

		return qtdEncerradaComExecucaoForaPrazoPorUnidade;
	}

	public void setQtdEncerradaComExecucaoForaPrazoPorUnidade(String qtdEncerradaComExecucaoForaPrazoPorUnidade){

		this.qtdEncerradaComExecucaoForaPrazoPorUnidade = qtdEncerradaComExecucaoForaPrazoPorUnidade;
	}

	public String getPercentualEncerradaComExecucaoNoPrazoPorUnidade(){

		return percentualEncerradaComExecucaoNoPrazoPorUnidade;
	}

	public void setPercentualEncerradaComExecucaoNoPrazoPorUnidade(String percentualEncerradaComExecucaoNoPrazoPorUnidade){

		this.percentualEncerradaComExecucaoNoPrazoPorUnidade = percentualEncerradaComExecucaoNoPrazoPorUnidade;
	}

	public String getPercentualEncerradaComExecucaoForaPrazoPorUnidade(){

		return percentualEncerradaComExecucaoForaPrazoPorUnidade;
	}

	public void setPercentualEncerradaComExecucaoForaPrazoPorUnidade(String percentualEncerradaComExecucaoForaPrazoPorUnidade){

		this.percentualEncerradaComExecucaoForaPrazoPorUnidade = percentualEncerradaComExecucaoForaPrazoPorUnidade;
	}

}
