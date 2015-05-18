package gcom.gui.arrecadacao;

import gcom.arrecadacao.Arrecadador;


public class RegistrarMovimentoArrecadadoresHelper {

	private String nsa;

	private String fileName;

	private String ultimaAlteracao;

	private Arrecadador arrecadador;


	public String getNsa(){

		return nsa;
	}

	public void setNsa(String nsa){

		this.nsa = nsa;
	}

	public String getFileName(){

		return fileName;
	}

	public void setFileName(String fileName){

		this.fileName = fileName;
	}

	public Arrecadador getArrecadador(){

		return arrecadador;
	}

	public void setArrecadador(Arrecadador arrecadador){

		this.arrecadador = arrecadador;
	}

	public String getUltimaAlteracao(){

		return ultimaAlteracao;
	}

	public void setUltimaAlteracao(String ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public RegistrarMovimentoArrecadadoresHelper(String nsa, String fileName, String ultimaAlteracao, Arrecadador arrecadador) {

		this.nsa = nsa;
		this.fileName = fileName;
		this.ultimaAlteracao = ultimaAlteracao;
		this.arrecadador = arrecadador;
	}

}
