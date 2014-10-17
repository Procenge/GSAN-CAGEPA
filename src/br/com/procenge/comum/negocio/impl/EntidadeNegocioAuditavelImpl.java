/**
 * 
 */

package br.com.procenge.comum.negocio.impl;

import gcom.interceptor.ObjetoTransacao;

import java.util.Date;
import java.util.Map;

import br.com.procenge.comum.negocio.api.EntidadeNegocioAuditavel;

/**
 * @author gmatos
 */
public abstract class EntidadeNegocioAuditavelImpl
				extends ObjetoTransacao
				implements EntidadeNegocioAuditavel {

	private int versao;

	private long chavePrimaria;

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

	public abstract Map validarDados();

	public void carregarLazy(){

	}

	public abstract String[] retornaCamposChavePrimaria();
}
