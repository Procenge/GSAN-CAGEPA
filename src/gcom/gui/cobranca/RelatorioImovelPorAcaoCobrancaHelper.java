
package gcom.gui.cobranca;

import org.apache.commons.lang.builder.EqualsBuilder;


public class RelatorioImovelPorAcaoCobrancaHelper {

	private String matricula;

	private String categoria;

	private String acao;

	private String grupo;

	private String zona;

	private String qtdDebitos;

	private String valorDivida;

	private String ordemServico;

	private String motivoEncerramento;

	private String dataEmissao;

	private String dataExecucao;

	private String executor;

	private String situacaoAcao;

	private String dataEmissaoAcao;

	public String getMatricula(){

		return matricula;
	}

	public void setMatricula(String matricula){

		this.matricula = matricula;
	}

	public String getCategoria(){

		return categoria;
	}

	public void setCategoria(String categoria){

		this.categoria = categoria;
	}

	public String getAcao(){

		return acao;
	}

	public void setAcao(String acao){

		this.acao = acao;
	}

	public String getGrupo(){

		return grupo;
	}

	public void setGrupo(String grupo){

		this.grupo = grupo;
	}

	public String getZona(){

		return zona;
	}

	public void setZona(String zona){

		this.zona = zona;
	}

	public String getQtdDebitos(){

		return qtdDebitos;
	}

	public void setQtdDebitos(String qtdDebitos){

		this.qtdDebitos = qtdDebitos;
	}

	public String getValorDivida(){

		return valorDivida;
	}

	public void setValorDivida(String valorDivida){

		this.valorDivida = valorDivida;
	}

	public String getOrdemServico(){

		return ordemServico;
	}

	public void setOrdemServico(String ordemServico){

		this.ordemServico = ordemServico;
	}

	public String getMotivoEncerramento(){

		return motivoEncerramento;
	}

	public void setMotivoEncerramento(String motivoEncerramento){

		this.motivoEncerramento = motivoEncerramento;
	}

	public String getDataEmissao(){

		return dataEmissao;
	}

	public void setDataEmissao(String dataEmissao){

		this.dataEmissao = dataEmissao;
	}

	public String getDataExecucao(){

		return dataExecucao;
	}

	public void setDataExecucao(String dataExecucao){

		this.dataExecucao = dataExecucao;
	}

	public String getExecutor(){

		return executor;
	}

	public void setExecutor(String executor){

		this.executor = executor;
	}

	public String getSituacaoAcao(){

		return situacaoAcao;
	}

	public void setSituacaoAcao(String situacaoAcao){

		this.situacaoAcao = situacaoAcao;
	}

	public String getDataEmissaoAcao(){

		return dataEmissaoAcao;
	}

	public void setDataEmissaoAcao(String dataEmissaoAcao){

		this.dataEmissaoAcao = dataEmissaoAcao;
	}

	public boolean equals(Object other){

		if((this == other)) return true;
		if(!(other instanceof RelatorioImovelPorAcaoCobrancaHelper)) return false;
		RelatorioImovelPorAcaoCobrancaHelper castOther = (RelatorioImovelPorAcaoCobrancaHelper) other;
		return new EqualsBuilder().append(this.getMatricula(), castOther.getMatricula())
						.append(this.getMatricula(), castOther.getMatricula())
.append(this.getCategoria(), castOther.getCategoria())
						.append(this.getAcao(), castOther.getAcao()).append(this.getGrupo(), castOther.getGrupo())
						.append(this.getZona(), castOther.getZona()).append(this.getQtdDebitos(), castOther.getQtdDebitos())
						.append(this.getValorDivida(), castOther.getValorDivida())
						.append(this.getOrdemServico(), castOther.getOrdemServico())
						.append(this.getMotivoEncerramento(), castOther.getMotivoEncerramento())
						.append(this.getDataEmissao(), castOther.getDataEmissao())
						.append(this.getDataExecucao(), castOther.getDataExecucao()).append(this.getExecutor(), castOther.getExecutor())
						.append(this.getSituacaoAcao(), castOther.getSituacaoAcao())
						.append(this.getDataEmissaoAcao(), castOther.getDataEmissaoAcao()).isEquals();

	}

}
