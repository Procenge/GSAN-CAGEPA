package gcom.atendimentopublico.bean;

import java.io.Serializable;

public class PercentualCobrancaHelper
				implements Serializable {

	private static final long serialVersionUID = 1L;

	private String valor;

	private String descricao;

	public PercentualCobrancaHelper() {

	}

	public String getValor(){

		return valor;
	}

	public void setValor(String valor){

		this.valor = valor;
	}

	public String getDescricao(){

		return descricao;
	}

	public void setDescricao(String descricao){

		this.descricao = descricao;
	}

}
