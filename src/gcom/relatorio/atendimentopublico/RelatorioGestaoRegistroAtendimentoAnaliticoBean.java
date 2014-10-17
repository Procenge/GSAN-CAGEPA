
package gcom.relatorio.atendimentopublico;

import gcom.relatorio.RelatorioBean;

/**
 * [UC0XXX] Relatório Gestão de Registro Atendimento
 * 
 * @author Anderson Italo
 * @date 28/03/2011
 */
public class RelatorioGestaoRegistroAtendimentoAnaliticoBean
				implements RelatorioBean {

	private String idTipoSolicitacao;

	private String descricaoTipoSolicitacao;

	private String idEspecificacao;

	private String descricaoEspecificacao;

	private String idUnidadeAtendimento;

	private String descricaoUnidadeAtendimento;

	private String siglaUnidadeAtendimento;

	private String qtdAtendidoPorEspecificacao;

	private String percentualAtendidoPorEspecificacao;

	private String qtdAtendidoPorEspecificacaoOrigem;

	private String qtdAtendidoPorEspecificacaoOutros;

	private String qtdSolicitadoPorEspecificacao;

	private String qtdSolicitadoPorEspecificacaoOrigem;

	private String qtdSolicitadoPorEspecificacaoOutros;

	private String qtdAtendidoNPPorEspecificacao;

	private String qtdAtendidoNPPorEspecificacaoOrigem;

	private String qtdAtendidoNPPorEspecificacaoOutros;

	private String qtdAtendidoFPPorEspecificacao;

	private String qtdAtendidoFPPorEspecificacaoOrigem;

	private String qtdAtendidoFPPorEspecificacaoOutros;

	private String percAtendidoPorEspecificacao;

	private String percAtendidoPorEspecificacaoOrigem;

	private String percAtendidoPorEspecificacaoOutros;

	private String percAtendidoNPPorEspecificacao;

	private String percAtendidoNPPorEspecificacaoOrigem;

	private String percAtendidoNPPorEspecificacaoOutros;

	private String percAtendidoFPPorEspecificacao;

	private String percAtendidoFPPorEspecificacaoOrigem;

	private String percAtendidoFPPorEspecificacaoOutros;

	private String qtdResidualNoPrazoPorEspecificacao;

	private String qtdResidualNoPrazoPorEspecificacaoOrigem;

	private String qtdResidualNoPrazoPorEspecificacaoOutros;

	private String qtdResidualForaPrazoPorEspecificacao;

	private String qtdResidualForaPrazoPorEspecificacaoOrigem;

	private String qtdResidualForaPrazoPorEspecificacaoOutros;

	private String prazoPadraoExecPorEspecificacao;

	private String prazoMedioExecPorEspecificacao;

	private String prazoMedioExecPorEspecificacaoOrigem;

	private String prazoMedioExecPorEspecificacaoOutros;

	private String atrasoMedioExecPorEspecificacao;

	private String atrasoMedioExecPorEspecificacaoOrigem;

	private String atrasoMedioExecPorEspecificacaoOutros;

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

	public String getIdTipoSolicitacao(){

		return idTipoSolicitacao;
	}

	public void setIdTipoSolicitacao(String idTipoSolicitacao){

		this.idTipoSolicitacao = idTipoSolicitacao;
	}

	public String getDescricaoTipoSolicitacao(){

		return descricaoTipoSolicitacao;
	}

	public void setDescricaoTipoSolicitacao(String descricaoTipoSolicitacao){

		this.descricaoTipoSolicitacao = descricaoTipoSolicitacao;
	}

	public String getIdEspecificacao(){

		return idEspecificacao;
	}

	public void setIdEspecificacao(String idEspecificacao){

		this.idEspecificacao = idEspecificacao;
	}

	public String getDescricaoEspecificacao(){

		return descricaoEspecificacao;
	}

	public void setDescricaoEspecificacao(String descricaoEspecificacao){

		this.descricaoEspecificacao = descricaoEspecificacao;
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

	public String getQtdAtendidoPorEspecificacao(){

		return qtdAtendidoPorEspecificacao;
	}

	public void setQtdAtendidoPorEspecificacao(String qtdAtendidoPorEspecificacao){

		this.qtdAtendidoPorEspecificacao = qtdAtendidoPorEspecificacao;
	}

	public String getPercentualAtendidoPorEspecificacao(){

		return percentualAtendidoPorEspecificacao;
	}

	public void setPercentualAtendidoPorEspecificacao(String percentualAtendidoPorEspecificacao){

		this.percentualAtendidoPorEspecificacao = percentualAtendidoPorEspecificacao;
	}

	public String getQtdAtendidoPorEspecificacaoOrigem(){

		return qtdAtendidoPorEspecificacaoOrigem;
	}

	public void setQtdAtendidoPorEspecificacaoOrigem(String qtdAtendidoPorEspecificacaoOrigem){

		this.qtdAtendidoPorEspecificacaoOrigem = qtdAtendidoPorEspecificacaoOrigem;
	}

	public String getQtdAtendidoPorEspecificacaoOutros(){

		return qtdAtendidoPorEspecificacaoOutros;
	}

	public void setQtdAtendidoPorEspecificacaoOutros(String qtdAtendidoPorEspecificacaoOutros){

		this.qtdAtendidoPorEspecificacaoOutros = qtdAtendidoPorEspecificacaoOutros;
	}

	public String getQtdSolicitadoPorEspecificacao(){

		return qtdSolicitadoPorEspecificacao;
	}

	public void setQtdSolicitadoPorEspecificacao(String qtdSolicitadoPorEspecificacao){

		this.qtdSolicitadoPorEspecificacao = qtdSolicitadoPorEspecificacao;
	}

	public String getQtdSolicitadoPorEspecificacaoOrigem(){

		return qtdSolicitadoPorEspecificacaoOrigem;
	}

	public void setQtdSolicitadoPorEspecificacaoOrigem(String qtdSolicitadoPorEspecificacaoOrigem){

		this.qtdSolicitadoPorEspecificacaoOrigem = qtdSolicitadoPorEspecificacaoOrigem;
	}

	public String getQtdSolicitadoPorEspecificacaoOutros(){

		return qtdSolicitadoPorEspecificacaoOutros;
	}

	public void setQtdSolicitadoPorEspecificacaoOutros(String qtdSolicitadoPorEspecificacaoOutros){

		this.qtdSolicitadoPorEspecificacaoOutros = qtdSolicitadoPorEspecificacaoOutros;
	}

	public String getQtdAtendidoNPPorEspecificacao(){

		return qtdAtendidoNPPorEspecificacao;
	}

	public void setQtdAtendidoNPPorEspecificacao(String qtdAtendidoNPPorEspecificacao){

		this.qtdAtendidoNPPorEspecificacao = qtdAtendidoNPPorEspecificacao;
	}

	public String getQtdAtendidoNPPorEspecificacaoOrigem(){

		return qtdAtendidoNPPorEspecificacaoOrigem;
	}

	public void setQtdAtendidoNPPorEspecificacaoOrigem(String qtdAtendidoNPPorEspecificacaoOrigem){

		this.qtdAtendidoNPPorEspecificacaoOrigem = qtdAtendidoNPPorEspecificacaoOrigem;
	}

	public String getQtdAtendidoNPPorEspecificacaoOutros(){

		return qtdAtendidoNPPorEspecificacaoOutros;
	}

	public void setQtdAtendidoNPPorEspecificacaoOutros(String qtdAtendidoNPPorEspecificacaoOutros){

		this.qtdAtendidoNPPorEspecificacaoOutros = qtdAtendidoNPPorEspecificacaoOutros;
	}

	public String getQtdAtendidoFPPorEspecificacao(){

		return qtdAtendidoFPPorEspecificacao;
	}

	public void setQtdAtendidoFPPorEspecificacao(String qtdAtendidoFPPorEspecificacao){

		this.qtdAtendidoFPPorEspecificacao = qtdAtendidoFPPorEspecificacao;
	}

	public String getQtdAtendidoFPPorEspecificacaoOrigem(){

		return qtdAtendidoFPPorEspecificacaoOrigem;
	}

	public void setQtdAtendidoFPPorEspecificacaoOrigem(String qtdAtendidoFPPorEspecificacaoOrigem){

		this.qtdAtendidoFPPorEspecificacaoOrigem = qtdAtendidoFPPorEspecificacaoOrigem;
	}

	public String getQtdAtendidoFPPorEspecificacaoOutros(){

		return qtdAtendidoFPPorEspecificacaoOutros;
	}

	public void setQtdAtendidoFPPorEspecificacaoOutros(String qtdAtendidoFPPorEspecificacaoOutros){

		this.qtdAtendidoFPPorEspecificacaoOutros = qtdAtendidoFPPorEspecificacaoOutros;
	}

	public String getPercAtendidoPorEspecificacao(){

		return percAtendidoPorEspecificacao;
	}

	public void setPercAtendidoPorEspecificacao(String percAtendidoPorEspecificacao){

		this.percAtendidoPorEspecificacao = percAtendidoPorEspecificacao;
	}

	public String getPercAtendidoPorEspecificacaoOrigem(){

		return percAtendidoPorEspecificacaoOrigem;
	}

	public void setPercAtendidoPorEspecificacaoOrigem(String percAtendidoPorEspecificacaoOrigem){

		this.percAtendidoPorEspecificacaoOrigem = percAtendidoPorEspecificacaoOrigem;
	}

	public String getPercAtendidoPorEspecificacaoOutros(){

		return percAtendidoPorEspecificacaoOutros;
	}

	public void setPercAtendidoPorEspecificacaoOutros(String percAtendidoPorEspecificacaoOutros){

		this.percAtendidoPorEspecificacaoOutros = percAtendidoPorEspecificacaoOutros;
	}

	public String getPercAtendidoNPPorEspecificacao(){

		return percAtendidoNPPorEspecificacao;
	}

	public void setPercAtendidoNPPorEspecificacao(String percAtendidoNPPorEspecificacao){

		this.percAtendidoNPPorEspecificacao = percAtendidoNPPorEspecificacao;
	}

	public String getPercAtendidoNPPorEspecificacaoOrigem(){

		return percAtendidoNPPorEspecificacaoOrigem;
	}

	public void setPercAtendidoNPPorEspecificacaoOrigem(String percAtendidoNPPorEspecificacaoOrigem){

		this.percAtendidoNPPorEspecificacaoOrigem = percAtendidoNPPorEspecificacaoOrigem;
	}

	public String getPercAtendidoNPPorEspecificacaoOutros(){

		return percAtendidoNPPorEspecificacaoOutros;
	}

	public void setPercAtendidoNPPorEspecificacaoOutros(String percAtendidoNPPorEspecificacaoOutros){

		this.percAtendidoNPPorEspecificacaoOutros = percAtendidoNPPorEspecificacaoOutros;
	}

	public String getPercAtendidoFPPorEspecificacao(){

		return percAtendidoFPPorEspecificacao;
	}

	public void setPercAtendidoFPPorEspecificacao(String percAtendidoFPPorEspecificacao){

		this.percAtendidoFPPorEspecificacao = percAtendidoFPPorEspecificacao;
	}

	public String getPercAtendidoFPPorEspecificacaoOrigem(){

		return percAtendidoFPPorEspecificacaoOrigem;
	}

	public void setPercAtendidoFPPorEspecificacaoOrigem(String percAtendidoFPPorEspecificacaoOrigem){

		this.percAtendidoFPPorEspecificacaoOrigem = percAtendidoFPPorEspecificacaoOrigem;
	}

	public String getPercAtendidoFPPorEspecificacaoOutros(){

		return percAtendidoFPPorEspecificacaoOutros;
	}

	public void setPercAtendidoFPPorEspecificacaoOutros(String percAtendidoFPPorEspecificacaoOutros){

		this.percAtendidoFPPorEspecificacaoOutros = percAtendidoFPPorEspecificacaoOutros;
	}

	public String getQtdResidualNoPrazoPorEspecificacao(){

		return qtdResidualNoPrazoPorEspecificacao;
	}

	public void setQtdResidualNoPrazoPorEspecificacao(String qtdResidualNoPrazoPorEspecificacao){

		this.qtdResidualNoPrazoPorEspecificacao = qtdResidualNoPrazoPorEspecificacao;
	}

	public String getQtdResidualNoPrazoPorEspecificacaoOrigem(){

		return qtdResidualNoPrazoPorEspecificacaoOrigem;
	}

	public void setQtdResidualNoPrazoPorEspecificacaoOrigem(String qtdResidualNoPrazoPorEspecificacaoOrigem){

		this.qtdResidualNoPrazoPorEspecificacaoOrigem = qtdResidualNoPrazoPorEspecificacaoOrigem;
	}

	public String getQtdResidualNoPrazoPorEspecificacaoOutros(){

		return qtdResidualNoPrazoPorEspecificacaoOutros;
	}

	public void setQtdResidualNoPrazoPorEspecificacaoOutros(String qtdResidualNoPrazoPorEspecificacaoOutros){

		this.qtdResidualNoPrazoPorEspecificacaoOutros = qtdResidualNoPrazoPorEspecificacaoOutros;
	}

	public String getQtdResidualForaPrazoPorEspecificacao(){

		return qtdResidualForaPrazoPorEspecificacao;
	}

	public void setQtdResidualForaPrazoPorEspecificacao(String qtdResidualForaPrazoPorEspecificacao){

		this.qtdResidualForaPrazoPorEspecificacao = qtdResidualForaPrazoPorEspecificacao;
	}

	public String getQtdResidualForaPrazoPorEspecificacaoOrigem(){

		return qtdResidualForaPrazoPorEspecificacaoOrigem;
	}

	public void setQtdResidualForaPrazoPorEspecificacaoOrigem(String qtdResidualForaPrazoPorEspecificacaoOrigem){

		this.qtdResidualForaPrazoPorEspecificacaoOrigem = qtdResidualForaPrazoPorEspecificacaoOrigem;
	}

	public String getQtdResidualForaPrazoPorEspecificacaoOutros(){

		return qtdResidualForaPrazoPorEspecificacaoOutros;
	}

	public void setQtdResidualForaPrazoPorEspecificacaoOutros(String qtdResidualForaPrazoPorEspecificacaoOutros){

		this.qtdResidualForaPrazoPorEspecificacaoOutros = qtdResidualForaPrazoPorEspecificacaoOutros;
	}

	public String getPrazoPadraoExecPorEspecificacao(){

		return prazoPadraoExecPorEspecificacao;
	}

	public void setPrazoPadraoExecPorEspecificacao(String prazoPadraoExecPorEspecificacao){

		this.prazoPadraoExecPorEspecificacao = prazoPadraoExecPorEspecificacao;
	}

	public String getPrazoMedioExecPorEspecificacao(){

		return prazoMedioExecPorEspecificacao;
	}

	public void setPrazoMedioExecPorEspecificacao(String prazoMedioExecPorEspecificacao){

		this.prazoMedioExecPorEspecificacao = prazoMedioExecPorEspecificacao;
	}

	public String getPrazoMedioExecPorEspecificacaoOrigem(){

		return prazoMedioExecPorEspecificacaoOrigem;
	}

	public void setPrazoMedioExecPorEspecificacaoOrigem(String prazoMedioExecPorEspecificacaoOrigem){

		this.prazoMedioExecPorEspecificacaoOrigem = prazoMedioExecPorEspecificacaoOrigem;
	}

	public String getPrazoMedioExecPorEspecificacaoOutros(){

		return prazoMedioExecPorEspecificacaoOutros;
	}

	public void setPrazoMedioExecPorEspecificacaoOutros(String prazoMedioExecPorEspecificacaoOutros){

		this.prazoMedioExecPorEspecificacaoOutros = prazoMedioExecPorEspecificacaoOutros;
	}

	public String getAtrasoMedioExecPorEspecificacao(){

		return atrasoMedioExecPorEspecificacao;
	}

	public void setAtrasoMedioExecPorEspecificacao(String atrasoMedioExecPorEspecificacao){

		this.atrasoMedioExecPorEspecificacao = atrasoMedioExecPorEspecificacao;
	}

	public String getAtrasoMedioExecPorEspecificacaoOrigem(){

		return atrasoMedioExecPorEspecificacaoOrigem;
	}

	public void setAtrasoMedioExecPorEspecificacaoOrigem(String atrasoMedioExecPorEspecificacaoOrigem){

		this.atrasoMedioExecPorEspecificacaoOrigem = atrasoMedioExecPorEspecificacaoOrigem;
	}

	public String getAtrasoMedioExecPorEspecificacaoOutros(){

		return atrasoMedioExecPorEspecificacaoOutros;
	}

	public void setAtrasoMedioExecPorEspecificacaoOutros(String atrasoMedioExecPorEspecificacaoOutros){

		this.atrasoMedioExecPorEspecificacaoOutros = atrasoMedioExecPorEspecificacaoOutros;
	}

	public String getSiglaUnidadeAtendimento(){

		return siglaUnidadeAtendimento;
	}

	public void setSiglaUnidadeAtendimento(String siglaUnidadeAtendimento){

		this.siglaUnidadeAtendimento = siglaUnidadeAtendimento;
	}
}
