/**
 * 
 */

package gcom.relatorio.atendimentopublico;

import gcom.relatorio.RelatorioBean;

/**
 * @author Bruno Ferreira dos Santos
 */
public class RelatorioProdutividadeEquipeBean
				implements RelatorioBean {

	private String unidadeAtual;

	private String equipe;

	private String tipoServico;

	private String quantidade;

	private String tempoPadrao;

	private String tempoMedio;

	public RelatorioProdutividadeEquipeBean(String unidadeAtual, String equipe, String tipoServico, String quantidade, String tempoPadrao,
											String tempoMedio) {

		super();
		this.unidadeAtual = unidadeAtual;
		this.equipe = equipe;
		this.tipoServico = tipoServico;
		this.quantidade = quantidade;
		this.tempoPadrao = tempoPadrao;
		this.tempoMedio = tempoMedio;
	}

	/**
	 * 
	 */
	public RelatorioProdutividadeEquipeBean() {

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

	public String getTempoPadrao(){

		return tempoPadrao;
	}

	public void setTempoPadrao(String tempoPadrao){

		this.tempoPadrao = tempoPadrao;
	}

	public String getTempoMedio(){

		return tempoMedio;
	}

	public void setTempoMedio(String tempoMedio){

		this.tempoMedio = tempoMedio;
	}

}
