/**
 * 
 */

package gcom.relatorio.atendimentopublico;

import gcom.relatorio.RelatorioBean;

/**
 * @author Péricles Tavares
 */
public class RelatorioOSNaoExecutadaEquipeBean
				implements RelatorioBean {

	private String unidadeAtual;

	private String equipe;

	private String tipoServico;

	private String quantidade;

	private String motivoEncerramento;

	public RelatorioOSNaoExecutadaEquipeBean(String unidadeAtual, String equipe, String tipoServico, String quantidade,
												String motivoEncerramento) {

		super();
		this.unidadeAtual = unidadeAtual;
		this.equipe = equipe;
		this.tipoServico = tipoServico;
		this.quantidade = quantidade;
		this.motivoEncerramento = motivoEncerramento;
	}

	/**
	 * 
	 */
	public RelatorioOSNaoExecutadaEquipeBean() {

		// TODO Auto-generated constructor stub
	}

	public String getUnidadeAtual(){

		return unidadeAtual;
	}

	public void setUnidadeAtual(String unidadeAtual){

		this.unidadeAtual = unidadeAtual;
	}

	public String getEquipe(){

		return equipe;
	}

	public void setEquipe(String equipe){

		this.equipe = equipe;
	}

	public String getTipoServico(){

		return tipoServico;
	}

	public void setTipoServico(String tipoServico){

		this.tipoServico = tipoServico;
	}

	public String getQuantidade(){

		return quantidade;
	}

	public void setQuantidade(String quantidade){

		this.quantidade = quantidade;
	}

	public String getMotivoEncerramento(){

		return motivoEncerramento;
	}

	public void setMotivoEncerramento(String motivoEncerramento){

		this.motivoEncerramento = motivoEncerramento;
	}

}
