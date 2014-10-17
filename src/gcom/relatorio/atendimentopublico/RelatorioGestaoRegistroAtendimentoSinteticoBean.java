
package gcom.relatorio.atendimentopublico;

import gcom.relatorio.RelatorioBean;

/**
 * [UC0XXX] Relatório Gestão de Registro Atendimento
 * 
 * @author Anderson Italo
 * @date 29/03/2011
 */
public class RelatorioGestaoRegistroAtendimentoSinteticoBean
				implements RelatorioBean {

	private String idUnidadeAtendimento;

	private String descricaoUnidadeAtendimento;

	private String siglaUnidadeAtendimento;

	private String qtdAtendidoPorUnidade;

	private String percentualAtendidoPorUnidade;

	private String qtdAtendidoPorUnidadeOrigem;

	private String qtdAtendidoPorUnidadeOutros;

	private String qtdSolicitadoPorUnidade;

	private String qtdSolicitadoPorUnidadeOrigem;

	private String qtdSolicitadoPorUnidadeOutros;

	private String qtdAtendidoNPPorUnidade;

	private String qtdAtendidoNPPorUnidadeOrigem;

	private String qtdAtendidoNPPorUnidadeOutros;

	private String qtdAtendidoFPPorUnidade;

	private String qtdAtendidoFPPorUnidadeOrigem;

	private String qtdAtendidoFPPorUnidadeOutros;

	private String percAtendidoPorUnidade;

	private String percAtendidoPorUnidadeOrigem;

	private String percAtendidoPorUnidadeOutros;

	private String percAtendidoNPPorUnidade;

	private String percAtendidoNPPorUnidadeOrigem;

	private String percAtendidoNPPorUnidadeOutros;

	private String percAtendidoFPPorUnidade;

	private String percAtendidoFPPorUnidadeOrigem;

	private String percAtendidoFPPorUnidadeOutros;

	private String qtdResidualNoPrazoPorUnidade;

	private String qtdResidualNoPrazoPorUnidadeOrigem;

	private String qtdResidualNoPrazoPorUnidadeOutros;

	private String qtdResidualForaPrazoPorUnidade;

	private String qtdResidualForaPrazoPorUnidadeOrigem;

	private String qtdResidualForaPrazoPorUnidadeOutros;

	public String getQtdAtendidoPorUnidade(){

		return qtdAtendidoPorUnidade;
	}

	public void setQtdAtendidoPorUnidade(String qtdAtendidoPorUnidade){

		this.qtdAtendidoPorUnidade = qtdAtendidoPorUnidade;
	}

	public String getPercentualAtendidoPorUnidade(){

		return percentualAtendidoPorUnidade;
	}

	public void setPercentualAtendidoPorUnidade(String percentualAtendidoPorUnidade){

		this.percentualAtendidoPorUnidade = percentualAtendidoPorUnidade;
	}

	public String getQtdAtendidoPorUnidadeOrigem(){

		return qtdAtendidoPorUnidadeOrigem;
	}

	public void setQtdAtendidoPorUnidadeOrigem(String qtdAtendidoPorUnidadeOrigem){

		this.qtdAtendidoPorUnidadeOrigem = qtdAtendidoPorUnidadeOrigem;
	}

	public String getQtdAtendidoPorUnidadeOutros(){

		return qtdAtendidoPorUnidadeOutros;
	}

	public void setQtdAtendidoPorUnidadeOutros(String qtdAtendidoPorUnidadeOutros){

		this.qtdAtendidoPorUnidadeOutros = qtdAtendidoPorUnidadeOutros;
	}

	public String getQtdSolicitadoPorUnidade(){

		return qtdSolicitadoPorUnidade;
	}

	public void setQtdSolicitadoPorUnidade(String qtdSolicitadoPorUnidade){

		this.qtdSolicitadoPorUnidade = qtdSolicitadoPorUnidade;
	}

	public String getQtdSolicitadoPorUnidadeOrigem(){

		return qtdSolicitadoPorUnidadeOrigem;
	}

	public void setQtdSolicitadoPorUnidadeOrigem(String qtdSolicitadoPorUnidadeOrigem){

		this.qtdSolicitadoPorUnidadeOrigem = qtdSolicitadoPorUnidadeOrigem;
	}

	public String getQtdSolicitadoPorUnidadeOutros(){

		return qtdSolicitadoPorUnidadeOutros;
	}

	public void setQtdSolicitadoPorUnidadeOutros(String qtdSolicitadoPorUnidadeOutros){

		this.qtdSolicitadoPorUnidadeOutros = qtdSolicitadoPorUnidadeOutros;
	}

	public String getQtdAtendidoNPPorUnidade(){

		return qtdAtendidoNPPorUnidade;
	}

	public void setQtdAtendidoNPPorUnidade(String qtdAtendidoNPPorUnidade){

		this.qtdAtendidoNPPorUnidade = qtdAtendidoNPPorUnidade;
	}

	public String getQtdAtendidoNPPorUnidadeOrigem(){

		return qtdAtendidoNPPorUnidadeOrigem;
	}

	public void setQtdAtendidoNPPorUnidadeOrigem(String qtdAtendidoNPPorUnidadeOrigem){

		this.qtdAtendidoNPPorUnidadeOrigem = qtdAtendidoNPPorUnidadeOrigem;
	}

	public String getQtdAtendidoNPPorUnidadeOutros(){

		return qtdAtendidoNPPorUnidadeOutros;
	}

	public void setQtdAtendidoNPPorUnidadeOutros(String qtdAtendidoNPPorUnidadeOutros){

		this.qtdAtendidoNPPorUnidadeOutros = qtdAtendidoNPPorUnidadeOutros;
	}

	public String getQtdAtendidoFPPorUnidade(){

		return qtdAtendidoFPPorUnidade;
	}

	public void setQtdAtendidoFPPorUnidade(String qtdAtendidoFPPorUnidade){

		this.qtdAtendidoFPPorUnidade = qtdAtendidoFPPorUnidade;
	}

	public String getQtdAtendidoFPPorUnidadeOrigem(){

		return qtdAtendidoFPPorUnidadeOrigem;
	}

	public void setQtdAtendidoFPPorUnidadeOrigem(String qtdAtendidoFPPorUnidadeOrigem){

		this.qtdAtendidoFPPorUnidadeOrigem = qtdAtendidoFPPorUnidadeOrigem;
	}

	public String getQtdAtendidoFPPorUnidadeOutros(){

		return qtdAtendidoFPPorUnidadeOutros;
	}

	public void setQtdAtendidoFPPorUnidadeOutros(String qtdAtendidoFPPorUnidadeOutros){

		this.qtdAtendidoFPPorUnidadeOutros = qtdAtendidoFPPorUnidadeOutros;
	}

	public String getPercAtendidoPorUnidade(){

		return percAtendidoPorUnidade;
	}

	public void setPercAtendidoPorUnidade(String percAtendidoPorUnidade){

		this.percAtendidoPorUnidade = percAtendidoPorUnidade;
	}

	public String getPercAtendidoPorUnidadeOrigem(){

		return percAtendidoPorUnidadeOrigem;
	}

	public void setPercAtendidoPorUnidadeOrigem(String percAtendidoPorUnidadeOrigem){

		this.percAtendidoPorUnidadeOrigem = percAtendidoPorUnidadeOrigem;
	}

	public String getPercAtendidoPorUnidadeOutros(){

		return percAtendidoPorUnidadeOutros;
	}

	public void setPercAtendidoPorUnidadeOutros(String percAtendidoPorUnidadeOutros){

		this.percAtendidoPorUnidadeOutros = percAtendidoPorUnidadeOutros;
	}

	public String getPercAtendidoNPPorUnidade(){

		return percAtendidoNPPorUnidade;
	}

	public void setPercAtendidoNPPorUnidade(String percAtendidoNPPorUnidade){

		this.percAtendidoNPPorUnidade = percAtendidoNPPorUnidade;
	}

	public String getPercAtendidoNPPorUnidadeOrigem(){

		return percAtendidoNPPorUnidadeOrigem;
	}

	public void setPercAtendidoNPPorUnidadeOrigem(String percAtendidoNPPorUnidadeOrigem){

		this.percAtendidoNPPorUnidadeOrigem = percAtendidoNPPorUnidadeOrigem;
	}

	public String getPercAtendidoNPPorUnidadeOutros(){

		return percAtendidoNPPorUnidadeOutros;
	}

	public void setPercAtendidoNPPorUnidadeOutros(String percAtendidoNPPorUnidadeOutros){

		this.percAtendidoNPPorUnidadeOutros = percAtendidoNPPorUnidadeOutros;
	}

	public String getPercAtendidoFPPorUnidade(){

		return percAtendidoFPPorUnidade;
	}

	public void setPercAtendidoFPPorUnidade(String percAtendidoFPPorUnidade){

		this.percAtendidoFPPorUnidade = percAtendidoFPPorUnidade;
	}

	public String getPercAtendidoFPPorUnidadeOrigem(){

		return percAtendidoFPPorUnidadeOrigem;
	}

	public void setPercAtendidoFPPorUnidadeOrigem(String percAtendidoFPPorUnidadeOrigem){

		this.percAtendidoFPPorUnidadeOrigem = percAtendidoFPPorUnidadeOrigem;
	}

	public String getPercAtendidoFPPorUnidadeOutros(){

		return percAtendidoFPPorUnidadeOutros;
	}

	public void setPercAtendidoFPPorUnidadeOutros(String percAtendidoFPPorUnidadeOutros){

		this.percAtendidoFPPorUnidadeOutros = percAtendidoFPPorUnidadeOutros;
	}

	public String getQtdResidualNoPrazoPorUnidade(){

		return qtdResidualNoPrazoPorUnidade;
	}

	public void setQtdResidualNoPrazoPorUnidade(String qtdResidualNoPrazoPorUnidade){

		this.qtdResidualNoPrazoPorUnidade = qtdResidualNoPrazoPorUnidade;
	}

	public String getQtdResidualNoPrazoPorUnidadeOrigem(){

		return qtdResidualNoPrazoPorUnidadeOrigem;
	}

	public void setQtdResidualNoPrazoPorUnidadeOrigem(String qtdResidualNoPrazoPorUnidadeOrigem){

		this.qtdResidualNoPrazoPorUnidadeOrigem = qtdResidualNoPrazoPorUnidadeOrigem;
	}

	public String getQtdResidualNoPrazoPorUnidadeOutros(){

		return qtdResidualNoPrazoPorUnidadeOutros;
	}

	public void setQtdResidualNoPrazoPorUnidadeOutros(String qtdResidualNoPrazoPorUnidadeOutros){

		this.qtdResidualNoPrazoPorUnidadeOutros = qtdResidualNoPrazoPorUnidadeOutros;
	}

	public String getQtdResidualForaPrazoPorUnidade(){

		return qtdResidualForaPrazoPorUnidade;
	}

	public void setQtdResidualForaPrazoPorUnidade(String qtdResidualForaPrazoPorUnidade){

		this.qtdResidualForaPrazoPorUnidade = qtdResidualForaPrazoPorUnidade;
	}

	public String getQtdResidualForaPrazoPorUnidadeOrigem(){

		return qtdResidualForaPrazoPorUnidadeOrigem;
	}

	public void setQtdResidualForaPrazoPorUnidadeOrigem(String qtdResidualForaPrazoPorUnidadeOrigem){

		this.qtdResidualForaPrazoPorUnidadeOrigem = qtdResidualForaPrazoPorUnidadeOrigem;
	}

	public String getQtdResidualForaPrazoPorUnidadeOutros(){

		return qtdResidualForaPrazoPorUnidadeOutros;
	}

	public void setQtdResidualForaPrazoPorUnidadeOutros(String qtdResidualForaPrazoPorUnidadeOutros){

		this.qtdResidualForaPrazoPorUnidadeOutros = qtdResidualForaPrazoPorUnidadeOutros;
	}

	public String getIdUnidadeAtendimento(){

		return idUnidadeAtendimento;
	}

	public void setIdUnidadeAtendimento(String idUnidadeAtendimento){

		this.idUnidadeAtendimento = idUnidadeAtendimento;
	}

	public String getDescricaoUnidadeAtendimento(){

		return descricaoUnidadeAtendimento;
	}

	public void setDescricaoUnidadeAtendimento(String descricaoUnidadeAtendimento){

		this.descricaoUnidadeAtendimento = descricaoUnidadeAtendimento;
	}

	public String getSiglaUnidadeAtendimento(){

		return siglaUnidadeAtendimento;
	}

	public void setSiglaUnidadeAtendimento(String siglaUnidadeAtendimento){

		this.siglaUnidadeAtendimento = siglaUnidadeAtendimento;
	}
}
