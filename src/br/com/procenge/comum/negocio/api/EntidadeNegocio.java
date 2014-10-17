
package br.com.procenge.comum.negocio.api;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * @autor gilberto
 */
public interface EntidadeNegocio
				extends Serializable {

	/**
	 * ATRIBUTO_CHAVE_PRIMARIA
	 */
	String ATRIBUTO_CHAVE_PRIMARIA = "chavePrimaria";

	/**
	 * @return Retorna o atributo chavePrimaria.
	 */
	long getChavePrimaria();

	/**
	 * @param chavePrimaria
	 *            O valor a ser atribu�do ao atributo chavePrimaria.
	 */
	void setChavePrimaria(long chavePrimaria);

	/**
	 * @return Retorna o atributo versao.
	 */
	int getVersao();

	/**
	 * @param versao
	 *            O valor a ser atribu�do ao atributo versao.
	 */
	void setVersao(int versao);

	/**
	 * M�todo respons�vel por incrementar a vers�o da entidade.
	 * 
	 * @autor gilberto
	 */
	void incrementarVersao();

	/**
	 * @return the ultimaAlteracao
	 */
	Date getUltimaAlteracao();

	/**
	 * @param ultimaAlteracao
	 *            the ultimaAlteracao to set
	 */
	void setUltimaAlteracao(Date ultimaAlteracao);

	/**
	 * @return Retorna o atributo map.
	 */
	Map validarDados();

	/**
	 * M�todo usado para iniciar os atributos lazy
	 */
	void carregarLazy();
}
