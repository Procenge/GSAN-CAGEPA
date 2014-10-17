
package br.com.procenge.comum.negocio.impl;

import java.util.Date;
import java.util.Map;

import br.com.procenge.comum.negocio.api.EntidadeNegocio;

/**
 * @autor gilberto
 */
public abstract class EntidadeNegocioImpl
				implements EntidadeNegocio {

	private long chavePrimaria;

	private int versao;

	private Date ultimaAlteracao;

	public long getChavePrimaria(){

		return chavePrimaria;
	}

	public void setChavePrimaria(long chavePrimaria){

		this.chavePrimaria = chavePrimaria;
	}

	/**
	 * @return Retorna o atributo versao.
	 */
	public int getVersao(){

		return versao;
	}

	/**
	 * @param versao
	 *            O valor a ser atribuído ao atributo versao.
	 */
	public void setVersao(int versao){

		this.versao = versao;
	}

	public void incrementarVersao(){

		this.versao++;
	}

	/**
	 * @return the ultimaAlteracao
	 */
	public Date getUltimaAlteracao(){

		return ultimaAlteracao;
	}

	/**
	 * @param ultimaAlteracao
	 *            the ultimaAlteracao to set
	 */
	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	/**
	 * Método usado para validar os dados da entidade
	 */
	public abstract Map validarDados();

	/**
	 * Método usado para iniciar os atributos lazy
	 */
	public void carregarLazy(){

	}

}
