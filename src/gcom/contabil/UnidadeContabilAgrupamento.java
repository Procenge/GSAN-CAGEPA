
package gcom.contabil;

import gcom.util.parametrizacao.ExecutorParametro;

public class UnidadeContabilAgrupamento
				implements ExecutorParametro {

	public static String SETOR_COMERCIAL = "SETOR_COMERCIAL";

	public static String LOCALIDADE = "LOCALIDADE";

	public static String UNIDADE_NEGOCIO = "UNIDADE_NEGOCIO";

	public static String GERENCIA_REGIONAL = "GERENCIA_REGIONAL";

	private int id;

	private String descricao;

	public void setId(int id){

		this.id = id;
	}

	public int getId(){

		return id;
	}

	public void setDescricao(String descricao){

		this.descricao = descricao;
	}

	public String getDescricao(){

		return descricao;
	}
}
