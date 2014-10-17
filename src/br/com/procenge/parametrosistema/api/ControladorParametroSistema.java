
package br.com.procenge.parametrosistema.api;

import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ControladorException;

import java.util.List;

import br.com.procenge.comum.exception.NegocioException;
import br.com.procenge.comum.negocio.api.ControladorNegocio;
import br.com.procenge.parametrosistema.impl.ParametroSistemaValor;

public interface ControladorParametroSistema
				extends ControladorNegocio {

	String BEAN_ID_CONTROLADOR_PARAMETRO_SISTEMA = "controladorParametroSistema";

	/**
	 * Método responsável por inserir um parâmetro no sistema.
	 * 
	 * @param codigo
	 *            O código do parâmetro.
	 * @param descricao
	 *            A descrição do parâmetro.
	 * @param valor
	 *            O Valor do parâmetro.
	 * @throws NegocioException
	 *             Caso ocora algum erro na execução do método.
	 */
	long inserirParametroSistema(String codigo, String descricao, Object valor) throws NegocioException;

	/**
	 * Método responsável por obter um valor do parâmetro através de um código.
	 * 
	 * @param codigo
	 *            O código do parâmetro.
	 * @return Um objeto que representa o valor do parâmetro.
	 * @throws NegocioException
	 *             Caso ocora algum erro na execução do método.
	 */
	Object obterValorDoParametroPorCodigo(String codigo) throws NegocioException;

	Object obterValorDoParametroPorCodigo(String codigo, Integer idFuncionalidade) throws NegocioException;

	/**
	 * Método responsável por obter o parâmetro através de um código.
	 * 
	 * @param codigo
	 *            O código do parâmetro.
	 * @return Um objeto que representa o parâmetro.
	 * @throws NegocioException
	 *             Caso ocora algum erro na execução do método.
	 */
	Object obterParametroPorCodigo(String codigo) throws NegocioException;

	Object obterParametroPorCodigo(String codigo, Integer idFuncionalidade) throws NegocioException;

	/**
	 * Método responsável por consultar todos os parametros do sistema.
	 * 
	 * @return Uma lista de parametros.
	 * @throws NegocioException
	 *             Caso ocora algum erro na execução do método.
	 */
	List<ParametroSistema> consultarParametroSistema() throws NegocioException;

	List<ParametroSistemaValor> obterParametroSistemaValor(ParametroSistema paramSistema) throws ControladorException;

	/**
	 * Método responsável por atualizar o valor de um parâmetro no sistema.
	 * 
	 * @param codigo
	 *            O código do parâmetro.
	 * @param valor
	 *            O Valor do parâmetro.
	 * @throws NegocioException
	 *             Caso ocora algum erro na execução do método.
	 */
	void atualizarValorParametroSistema(String codigo, String valor) throws NegocioException;

	/**
	 * Método responsável por atualizar um parâmetro do sistema.
	 * 
	 * @param parametroSistema
	 *            O parametro so sitema com os novos valores.
	 * @param usuarioLogado
	 *            O usuario que está fazendo a alteracao
	 * @throws ControladorException
	 */
	void atualizarParametroSistema(ParametroSistema parametroSistema, Usuario usuarioLogado) throws ControladorException;

	/**
	 * Método responsável por excluir fisicmente um valor da tabela PARAMETRO_SISTEMA_VALOR
	 * 
	 * @param paramValor
	 *            O ParametroSistemaValor que será removido da tabela
	 * @param usuarioLogado
	 *            O usuario que está fazendo a alteracao
	 * @throws ControladorException
	 */
	void excluirParametroSistemaValor(ParametroSistemaValor paramValor, Usuario usuarioLogado) throws ControladorException;

	/**
	 * Método responsável por inserir um registro na tabela PARAMETRO_SISTEMA_VALOR
	 * 
	 * @param paramValor
	 *            O ParametroSistemaValor que será inserido na tabela
	 * @param usuarioLogado
	 *            O usuario que está fazendo a alteracao
	 * @throws ControladorException
	 */
	void inserirParametroSistemaValor(ParametroSistemaValor paramValor, Usuario usuarioLogado) throws ControladorException;

	/**
	 * Método responsável por atualizar um registro na tabela PARAMETRO_SISTEMA_VALOR
	 * 
	 * @param paramValor
	 *            O ParametroSistemaValor que será inserido na tabela
	 * @param usuarioLogado
	 *            O usuario que está fazendo a alteracao
	 * @throws ControladorException
	 */
	public void atualizarParametroSistemaValor(ParametroSistemaValor paramValor, Usuario usuarioLogado) throws ControladorException;

}