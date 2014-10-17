
package gcom.relatorio.atendimentopublico;

import gcom.relatorio.RelatorioBean;

/**
 * [UC0XXX] Relatório Resumo Ordens de Serviço Encerradas
 * 
 * @author Victon Santos
 * @date 22/12/2013
 */
public class RelatorioOrdemServicoEncerradasDentroForaPrazoBean
				implements RelatorioBean, Comparable<RelatorioOrdemServicoEncerradasDentroForaPrazoBean> {

	private String numeroOS;

	private String numeroRA;

	private String matricula;

	private String endereco;

	private String solicitacao;

	private String tipoEspecificacao;

	private String dtCriacao;

	private String dtEncerramento;

	private String unAtual;

	private String unEncerramento;

	private Integer diasAtraso;

	private String qntReiteracao;

	private String dataCriacaoRA;

	private String dataEncerramentoRA;

	private String prazoRA;

	public String getNumeroOS(){

		return numeroOS;
	}

	public void setNumeroOS(String numeroOS){

		this.numeroOS = numeroOS;
	}

	public String getNumeroRA(){

		return numeroRA;
	}

	public void setNumeroRA(String numeroRA){

		this.numeroRA = numeroRA;
	}

	public String getMatricula(){

		return matricula;
	}

	public void setMatricula(String matricula){

		this.matricula = matricula;
	}

	public String getEndereco(){

		return endereco;
	}

	public void setEndereco(String endereco){

		this.endereco = endereco;
	}

	public String getDtEncerramento(){

		return dtEncerramento;
	}

	public void setDtEncerramento(String dtEncerramento){

		this.dtEncerramento = dtEncerramento;
	}

	public Integer getDiasAtraso(){

		return diasAtraso;
	}

	public void setDiasAtraso(Integer diasAtraso){

		this.diasAtraso = diasAtraso;
	}

	public String getSolicitacao(){

		return solicitacao;
	}

	public void setSolicitacao(String solicitacao){

		this.solicitacao = solicitacao;
	}

	public String getTipoEspecificacao(){

		return tipoEspecificacao;
	}

	public void setTipoEspecificacao(String tipoEspecificacao){

		this.tipoEspecificacao = tipoEspecificacao;
	}

	public String getDtCriacao(){

		return dtCriacao;
	}

	public void setDtCriacao(String dtCriacao){

		this.dtCriacao = dtCriacao;
	}

	public String getUnAtual(){

		return unAtual;
	}

	public void setUnAtual(String unAtual){

		this.unAtual = unAtual;
	}

	public String getUnEncerramento(){

		return unEncerramento;
	}

	public void setUnEncerramento(String unEncerramento){

		this.unEncerramento = unEncerramento;
	}

	public String getQntReiteracao(){

		return qntReiteracao;
	}

	public void setQntReiteracao(String qntReiteracao){

		this.qntReiteracao = qntReiteracao;
	}

	public String getDtCriacaoRA(){

		return dataCriacaoRA;
	}

	public void setDtCriacaoRA(String dtCriacaoRA){

		this.dataCriacaoRA = dtCriacaoRA;
	}

	public String getDtEncerramentoRA(){

		return dataEncerramentoRA;
	}

	public void setDtEncerramentoRA(String dtEncerramentoRA){

		this.dataEncerramentoRA = dtEncerramentoRA;
	}

	public String getPrazoRA(){

		return prazoRA;
	}

	public void setPrazoRA(String prazoRA){

		this.prazoRA = prazoRA;
	}

	public int compareTo(RelatorioOrdemServicoEncerradasDentroForaPrazoBean arg0){

		if(this.getUnEncerramento().compareToIgnoreCase(arg0.getUnEncerramento()) != 0){
			return this.getUnEncerramento().compareToIgnoreCase(arg0.getUnEncerramento());
		}
		return this.getEndereco().compareToIgnoreCase(arg0.getEndereco());
	}

}
