package gcom.arrecadacao.bean;

import gcom.arrecadacao.ArrecadadorMovimento;

import java.io.Serializable;

public class FiltrarMovimentoArrecadadoresHelper
				implements Serializable {

	ArrecadadorMovimento arrecadadorMovimento;

	Integer idConcessionaria;


	String nomeConcessionaria;

	public ArrecadadorMovimento getArrecadadorMovimento(){

		return arrecadadorMovimento;
	}

	public void setArrecadadorMovimento(ArrecadadorMovimento arrecadadorMovimento){

		this.arrecadadorMovimento = arrecadadorMovimento;
	}

	public String getNomeConcessionaria(){

		return nomeConcessionaria;
	}

	public void setNomeConcessionaria(String nomeConcessionaria){

		this.nomeConcessionaria = nomeConcessionaria;
	}

	public Integer getIdConcessionaria(){

		return idConcessionaria;
	}

	public void setIdConcessionaria(Integer idConcessionaria){

		this.idConcessionaria = idConcessionaria;
	}

}
