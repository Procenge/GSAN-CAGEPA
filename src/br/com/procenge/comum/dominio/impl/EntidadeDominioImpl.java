
package br.com.procenge.comum.dominio.impl;

import br.com.procenge.comum.dominio.api.EntidadeDominio;

/**
 * @autor gilberto
 */
public class EntidadeDominioImpl
				implements EntidadeDominio {

	private int codigo;

	private String descricao;

	public int getCodigo(){

		return codigo;
	}

	public String getDescricao(){

		return descricao;
	}

	public void setCodigo(int codigo){

		this.codigo = codigo;
	}

	public void setDescricao(String descricao){

		this.descricao = descricao;
	}

}
