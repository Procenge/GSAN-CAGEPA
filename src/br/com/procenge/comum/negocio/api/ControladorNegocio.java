
package br.com.procenge.comum.negocio.api;

import br.com.procenge.comum.exception.ConcorrenciaException;
import br.com.procenge.comum.exception.NegocioException;

/**
 * @autor gilberto
 */
public interface ControladorNegocio {

	/**
	 * Método responsável por criar uma entidade.
	 * 
	 * @autor gilberto
	 * @return Uma nova entidade de negócio
	 */
	EntidadeNegocio criar();

	/**
	 * Método responsável por inserir uma entidade.
	 * 
	 * @autor gilberto
	 * @param entidadeNegocio
	 *            A entidade de negócio
	 * @return long a chave primária da entidade
	 * @throws NegocioException
	 *             Caso ocorra alguma violação nas regras de negócio
	 */
	long inserir(EntidadeNegocio entidadeNegocio) throws NegocioException;

	/**
	 * Método responsável por remover uma entidade.
	 * 
	 * @autor gilberto
	 * @param entidadeNegocio
	 *            A entidade de negócio
	 * @throws NegocioException
	 *             Caso ocorra alguma violação nas regras de negócio
	 */
	void remover(EntidadeNegocio entidadeNegocio) throws NegocioException;

	/**
	 * Método responsável por obter uma entidade.
	 * 
	 * @autor gilberto
	 * @param chavePrimaria
	 *            A chave primária da entidade de negócio
	 * @return EntidadeBase
	 * @throws NegocioException
	 *             Caso ocorra alguma violação nas regras de negócio
	 */
	EntidadeNegocio obter(long chavePrimaria) throws NegocioException;

	/**
	 * Método responsável por obter uma entidade.
	 * 
	 * @autor gilberto
	 * @param chavePrimaria
	 *            A chave primária da entidade de negócio
	 * @param carregarLazy
	 *            Se o lazy será carregado
	 * @return EntidadeBase
	 * @throws NegocioException
	 *             Caso ocorra alguma violação nas regras de negócio
	 */
	EntidadeNegocio obter(long chavePrimaria, boolean carregarLazy) throws NegocioException;

	/**
	 * Método responsável por obter uma entidade.
	 * 
	 * @autor gilberto
	 * @param chavePrimaria
	 *            A chave primária da entidade de negócio
	 * @param classe
	 *            A classe da entidade de negócio
	 * @return entidadeBase
	 * @throws NegocioException
	 *             Caso ocorra alguma violação nas regras de negócio
	 */
	EntidadeNegocio obter(long chavePrimaria, Class classe) throws NegocioException;

	/**
	 * Método responsável por obter uma entidade.
	 * 
	 * @autor gilberto
	 * @param chavePrimaria
	 *            A chave primária da entidade de negócio
	 * @param classe
	 *            A classe da entidade de negócio
	 * @param carregarLazy
	 *            Se o lazy será carregado
	 * @return entidadeBase
	 * @throws NegocioException
	 *             Caso ocorra alguma violação nas regras de negócio
	 */
	EntidadeNegocio obter(long chavePrimaria, Class classe, boolean carregarLazy) throws NegocioException;

	/**
	 * Método responsável por atualizar uma entidade.
	 * 
	 * @autor gilberto
	 * @param entidadeNegocio
	 *            A entidade de negócio
	 * @throws NegocioException
	 *             Caso ocorra alguma violação nas regras de negócio
	 */
	void atualizar(EntidadeNegocio entidadeNegocio) throws ConcorrenciaException, NegocioException;

	/**
	 * Método responsável por tratar a pre-inserção da entidade, deverá ser sobrescrito pelas
	 * subclasses caso necessário.
	 * 
	 * @autor gilberto
	 * @param entidadeNegocio
	 *            a entidade de negócio
	 * @throws NegocioException
	 *             Caso ocorra alguma violação nas regras de negócio
	 */
	void preInsercao(EntidadeNegocio entidadeNegocio) throws NegocioException;

	/**
	 * Método responsável por tratar a pos-inserção da entidade, deverá ser sobrescrito pelas
	 * subclasses caso necessário.
	 * 
	 * @autor gilberto
	 * @param entidadeNegocio
	 *            a entidade de negócio
	 * @throws NegocioException
	 *             Caso ocorra alguma violação nas regras de negócio
	 */
	void posInsercao(EntidadeNegocio entidadeNegocio) throws NegocioException;

	/**
	 * Método responsável por tratar a pre-atualização da entidade, deverá ser sobrescrito pelas
	 * subclasses caso necessário.
	 * 
	 * @autor gilberto
	 * @param entidadeNegocio
	 *            a entidade de negócio
	 * @throws NegocioException
	 *             Caso ocorra alguma violação nas regras de negócio
	 */
	void preAtualizacao(EntidadeNegocio entidadeNegocio) throws NegocioException;

	/**
	 * Método responsável por tratar a pos-atualização da entidade, deverá ser sobrescrito pelas
	 * subclasses caso necessário.
	 * 
	 * @autor gilberto
	 * @param entidadeNegocio
	 *            a entidade de negócio
	 * @throws NegocioException
	 *             Caso ocorra alguma violação nas regras de negócio
	 */
	void posAtualizacao(EntidadeNegocio entidadeNegocio) throws NegocioException;

	/**
	 * Método responsável por tratar a pre-remoção da entidade, deverá ser sobrescrito pelas
	 * subclasses caso necessário.
	 * 
	 * @autor gilberto
	 * @param entidadeNegocio
	 *            a entidade de negócio
	 * @throws NegocioException
	 *             Caso ocorra alguma violação nas regras de negócio
	 */
	void preRemocao(EntidadeNegocio entidadeNegocio) throws NegocioException;

	/**
	 * Método responsável por tratar a pos-remoção da entidade, deverá ser sobrescrito pelas
	 * subclasses caso necessário.
	 * 
	 * @autor gilberto
	 * @param entidadeNegocio
	 *            a entidade de negócio
	 * @throws NegocioException
	 *             Caso ocorra alguma violação nas regras de negócio
	 */
	void posRemocao(EntidadeNegocio entidadeNegocio) throws NegocioException;

	/**
	 * Método responsável por validar os dados da entidade.
	 * 
	 * @autor gilberto
	 * @param entidadeNegocio
	 *            a entidade de negócio
	 * @throws NegocioException
	 *             Caso ocorra alguma violação nas regras de negócio
	 */

	void validarDadosDaEntidade(EntidadeNegocio entidadeNegocio) throws NegocioException;

	/**
	 * Método responsável por obter a classe da entidade.
	 * 
	 * @autor gilberto
	 * @return Class O tipo da classe da entidade
	 */
	Class getClasseEntidade();

	/**
	 * Método responsável por validar a versão da entidade.
	 * 
	 * @autor gilberto
	 * @param entidadeNegocio
	 *            a entidade de negócio
	 * @throws ConcorrenciaException
	 *             Caso ocorra algum problema de concorrência
	 * @throws NegocioException
	 *             Caso ocorra alguma violação nas regras de negócio
	 */
	void validarVersaoEntidade(EntidadeNegocio entidadeNegocio) throws ConcorrenciaException, NegocioException;

}
