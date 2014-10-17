
package br.com.procenge.util;

/**
 * @author jnogueira
 */
public enum Mes {

	JANEIRO(1, "Janeiro"), FEVEREIRO(2, "Fevereiro"), MARCO(3, "Março"), ABRIL(4, "Abril"), MAIO(5, "Maio"), JUNHO(6, "Junho"), JULHO(7,
					"Julho"), AGOSTO(8, "Agosto"), SETEMBRO(9, "Setembro"), OUTUBRO(10, "Outubro"), NOVEMBRO(11, "Novembro"), DEZEMBRO(12,
					"Dezembro");

	private int numeroCorrespondente;

	private String nomeMes;

	private Mes(int numeroCorrespondente, String nome) {

		this.numeroCorrespondente = numeroCorrespondente;
		this.nomeMes = nome;
	}

	public int getNumeroCorrespondente(){

		return numeroCorrespondente;
	}

	public String getNomeMes(){

		return nomeMes;
	}

}
