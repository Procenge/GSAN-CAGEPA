
package gcom.gui.cobranca;

import gcom.cobranca.BoletoBancarioMovimentacao;
import gcom.cobranca.BoletoBancarioOcorrencias;

import java.util.Collection;

public class BoletoBancarioMovimentacaoHelper {

	private static final long serialVersionUID = 1L;

	private BoletoBancarioMovimentacao boletoBancarioMovimentacao;

	private Collection<BoletoBancarioOcorrencias> colecaoBoletoBancarioOcorrencias;

	private String situacaoEnvio;

	public BoletoBancarioMovimentacao getBoletoBancarioMovimentacao(){

		return boletoBancarioMovimentacao;
	}

	public void setBoletoBancarioMovimentacao(BoletoBancarioMovimentacao boletoBancarioMovimentacao){

		this.boletoBancarioMovimentacao = boletoBancarioMovimentacao;
	}

	public Collection<BoletoBancarioOcorrencias> getColecaoBoletoBancarioOcorrencias(){

		return colecaoBoletoBancarioOcorrencias;
	}

	public void setColecaoBoletoBancarioOcorrencias(Collection<BoletoBancarioOcorrencias> colecaoBoletoBancarioOcorrencias){

		this.colecaoBoletoBancarioOcorrencias = colecaoBoletoBancarioOcorrencias;
	}

	public String getSituacaoEnvio(){

		return situacaoEnvio;
	}

	public void setSituacaoEnvio(String situacaoEnvio){

		this.situacaoEnvio = situacaoEnvio;
	}

}
