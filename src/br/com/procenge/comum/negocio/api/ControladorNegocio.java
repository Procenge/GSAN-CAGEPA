
package br.com.procenge.comum.negocio.api;

import br.com.procenge.comum.exception.ConcorrenciaException;
import br.com.procenge.comum.exception.NegocioException;

/**
 * @autor gilberto
 */
public interface ControladorNegocio {

	/**
	 * M�todo respons�vel por criar uma entidade.
	 * 
	 * @autor gilberto
	 * @return Uma nova entidade de neg�cio
	 */
	EntidadeNegocio criar();

	/**
	 * M�todo respons�vel por inserir uma entidade.
	 * 
	 * @autor gilberto
	 * @param entidadeNegocio
	 *            A entidade de neg�cio
	 * @return long a chave prim�ria da entidade
	 * @throws NegocioException
	 *             Caso ocorra alguma viola��o nas regras de neg�cio
	 */
	long inserir(EntidadeNegocio entidadeNegocio) throws NegocioException;

	/**
	 * M�todo respons�vel por remover uma entidade.
	 * 
	 * @autor gilberto
	 * @param entidadeNegocio
	 *            A entidade de neg�cio
	 * @throws NegocioException
	 *             Caso ocorra alguma viola��o nas regras de neg�cio
	 */
	void remover(EntidadeNegocio entidadeNegocio) throws NegocioException;

	/**
	 * M�todo respons�vel por obter uma entidade.
	 * 
	 * @autor gilberto
	 * @param chavePrimaria
	 *            A chave prim�ria da entidade de neg�cio
	 * @return EntidadeBase
	 * @throws NegocioException
	 *             Caso ocorra alguma viola��o nas regras de neg�cio
	 */
	EntidadeNegocio obter(long chavePrimaria) throws NegocioException;

	/**
	 * M�todo respons�vel por obter uma entidade.
	 * 
	 * @autor gilberto
	 * @param chavePrimaria
	 *            A chave prim�ria da entidade de neg�cio
	 * @param carregarLazy
	 *            Se o lazy ser� carregado
	 * @return EntidadeBase
	 * @throws NegocioException
	 *             Caso ocorra alguma viola��o nas regras de neg�cio
	 */
	EntidadeNegocio obter(long chavePrimaria, boolean carregarLazy) throws NegocioException;

	/**
	 * M�todo respons�vel por obter uma entidade.
	 * 
	 * @autor gilberto
	 * @param chavePrimaria
	 *            A chave prim�ria da entidade de neg�cio
	 * @param classe
	 *            A classe da entidade de neg�cio
	 * @return entidadeBase
	 * @throws NegocioException
	 *             Caso ocorra alguma viola��o nas regras de neg�cio
	 */
	EntidadeNegocio obter(long chavePrimaria, Class classe) throws NegocioException;

	/**
	 * M�todo respons�vel por obter uma entidade.
	 * 
	 * @autor gilberto
	 * @param chavePrimaria
	 *            A chave prim�ria da entidade de neg�cio
	 * @param classe
	 *            A classe da entidade de neg�cio
	 * @param carregarLazy
	 *            Se o lazy ser� carregado
	 * @return entidadeBase
	 * @throws NegocioException
	 *             Caso ocorra alguma viola��o nas regras de neg�cio
	 */
	EntidadeNegocio obter(long chavePrimaria, Class classe, boolean carregarLazy) throws NegocioException;

	/**
	 * M�todo respons�vel por atualizar uma entidade.
	 * 
	 * @autor gilberto
	 * @param entidadeNegocio
	 *            A entidade de neg�cio
	 * @throws NegocioException
	 *             Caso ocorra alguma viola��o nas regras de neg�cio
	 */
	void atualizar(EntidadeNegocio entidadeNegocio) throws ConcorrenciaException, NegocioException;

	/**
	 * M�todo respons�vel por tratar a pre-inser��o da entidade, dever� ser sobrescrito pelas
	 * subclasses caso necess�rio.
	 * 
	 * @autor gilberto
	 * @param entidadeNegocio
	 *            a entidade de neg�cio
	 * @throws NegocioException
	 *             Caso ocorra alguma viola��o nas regras de neg�cio
	 */
	void preInsercao(EntidadeNegocio entidadeNegocio) throws NegocioException;

	/**
	 * M�todo respons�vel por tratar a pos-inser��o da entidade, dever� ser sobrescrito pelas
	 * subclasses caso necess�rio.
	 * 
	 * @autor gilberto
	 * @param entidadeNegocio
	 *            a entidade de neg�cio
	 * @throws NegocioException
	 *             Caso ocorra alguma viola��o nas regras de neg�cio
	 */
	void posInsercao(EntidadeNegocio entidadeNegocio) throws NegocioException;

	/**
	 * M�todo respons�vel por tratar a pre-atualiza��o da entidade, dever� ser sobrescrito pelas
	 * subclasses caso necess�rio.
	 * 
	 * @autor gilberto
	 * @param entidadeNegocio
	 *            a entidade de neg�cio
	 * @throws NegocioException
	 *             Caso ocorra alguma viola��o nas regras de neg�cio
	 */
	void preAtualizacao(EntidadeNegocio entidadeNegocio) throws NegocioException;

	/**
	 * M�todo respons�vel por tratar a pos-atualiza��o da entidade, dever� ser sobrescrito pelas
	 * subclasses caso necess�rio.
	 * 
	 * @autor gilberto
	 * @param entidadeNegocio
	 *            a entidade de neg�cio
	 * @throws NegocioException
	 *             Caso ocorra alguma viola��o nas regras de neg�cio
	 */
	void posAtualizacao(EntidadeNegocio entidadeNegocio) throws NegocioException;

	/**
	 * M�todo respons�vel por tratar a pre-remo��o da entidade, dever� ser sobrescrito pelas
	 * subclasses caso necess�rio.
	 * 
	 * @autor gilberto
	 * @param entidadeNegocio
	 *            a entidade de neg�cio
	 * @throws NegocioException
	 *             Caso ocorra alguma viola��o nas regras de neg�cio
	 */
	void preRemocao(EntidadeNegocio entidadeNegocio) throws NegocioException;

	/**
	 * M�todo respons�vel por tratar a pos-remo��o da entidade, dever� ser sobrescrito pelas
	 * subclasses caso necess�rio.
	 * 
	 * @autor gilberto
	 * @param entidadeNegocio
	 *            a entidade de neg�cio
	 * @throws NegocioException
	 *             Caso ocorra alguma viola��o nas regras de neg�cio
	 */
	void posRemocao(EntidadeNegocio entidadeNegocio) throws NegocioException;

	/**
	 * M�todo respons�vel por validar os dados da entidade.
	 * 
	 * @autor gilberto
	 * @param entidadeNegocio
	 *            a entidade de neg�cio
	 * @throws NegocioException
	 *             Caso ocorra alguma viola��o nas regras de neg�cio
	 */

	void validarDadosDaEntidade(EntidadeNegocio entidadeNegocio) throws NegocioException;

	/**
	 * M�todo respons�vel por obter a classe da entidade.
	 * 
	 * @autor gilberto
	 * @return Class O tipo da classe da entidade
	 */
	Class getClasseEntidade();

	/**
	 * M�todo respons�vel por validar a vers�o da entidade.
	 * 
	 * @autor gilberto
	 * @param entidadeNegocio
	 *            a entidade de neg�cio
	 * @throws ConcorrenciaException
	 *             Caso ocorra algum problema de concorr�ncia
	 * @throws NegocioException
	 *             Caso ocorra alguma viola��o nas regras de neg�cio
	 */
	void validarVersaoEntidade(EntidadeNegocio entidadeNegocio) throws ConcorrenciaException, NegocioException;

}
