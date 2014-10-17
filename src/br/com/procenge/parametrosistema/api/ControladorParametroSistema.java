
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
	 * M�todo respons�vel por inserir um par�metro no sistema.
	 * 
	 * @param codigo
	 *            O c�digo do par�metro.
	 * @param descricao
	 *            A descri��o do par�metro.
	 * @param valor
	 *            O Valor do par�metro.
	 * @throws NegocioException
	 *             Caso ocora algum erro na execu��o do m�todo.
	 */
	long inserirParametroSistema(String codigo, String descricao, Object valor) throws NegocioException;

	/**
	 * M�todo respons�vel por obter um valor do par�metro atrav�s de um c�digo.
	 * 
	 * @param codigo
	 *            O c�digo do par�metro.
	 * @return Um objeto que representa o valor do par�metro.
	 * @throws NegocioException
	 *             Caso ocora algum erro na execu��o do m�todo.
	 */
	Object obterValorDoParametroPorCodigo(String codigo) throws NegocioException;

	Object obterValorDoParametroPorCodigo(String codigo, Integer idFuncionalidade) throws NegocioException;

	/**
	 * M�todo respons�vel por obter o par�metro atrav�s de um c�digo.
	 * 
	 * @param codigo
	 *            O c�digo do par�metro.
	 * @return Um objeto que representa o par�metro.
	 * @throws NegocioException
	 *             Caso ocora algum erro na execu��o do m�todo.
	 */
	Object obterParametroPorCodigo(String codigo) throws NegocioException;

	Object obterParametroPorCodigo(String codigo, Integer idFuncionalidade) throws NegocioException;

	/**
	 * M�todo respons�vel por consultar todos os parametros do sistema.
	 * 
	 * @return Uma lista de parametros.
	 * @throws NegocioException
	 *             Caso ocora algum erro na execu��o do m�todo.
	 */
	List<ParametroSistema> consultarParametroSistema() throws NegocioException;

	List<ParametroSistemaValor> obterParametroSistemaValor(ParametroSistema paramSistema) throws ControladorException;

	/**
	 * M�todo respons�vel por atualizar o valor de um par�metro no sistema.
	 * 
	 * @param codigo
	 *            O c�digo do par�metro.
	 * @param valor
	 *            O Valor do par�metro.
	 * @throws NegocioException
	 *             Caso ocora algum erro na execu��o do m�todo.
	 */
	void atualizarValorParametroSistema(String codigo, String valor) throws NegocioException;

	/**
	 * M�todo respons�vel por atualizar um par�metro do sistema.
	 * 
	 * @param parametroSistema
	 *            O parametro so sitema com os novos valores.
	 * @param usuarioLogado
	 *            O usuario que est� fazendo a alteracao
	 * @throws ControladorException
	 */
	void atualizarParametroSistema(ParametroSistema parametroSistema, Usuario usuarioLogado) throws ControladorException;

	/**
	 * M�todo respons�vel por excluir fisicmente um valor da tabela PARAMETRO_SISTEMA_VALOR
	 * 
	 * @param paramValor
	 *            O ParametroSistemaValor que ser� removido da tabela
	 * @param usuarioLogado
	 *            O usuario que est� fazendo a alteracao
	 * @throws ControladorException
	 */
	void excluirParametroSistemaValor(ParametroSistemaValor paramValor, Usuario usuarioLogado) throws ControladorException;

	/**
	 * M�todo respons�vel por inserir um registro na tabela PARAMETRO_SISTEMA_VALOR
	 * 
	 * @param paramValor
	 *            O ParametroSistemaValor que ser� inserido na tabela
	 * @param usuarioLogado
	 *            O usuario que est� fazendo a alteracao
	 * @throws ControladorException
	 */
	void inserirParametroSistemaValor(ParametroSistemaValor paramValor, Usuario usuarioLogado) throws ControladorException;

	/**
	 * M�todo respons�vel por atualizar um registro na tabela PARAMETRO_SISTEMA_VALOR
	 * 
	 * @param paramValor
	 *            O ParametroSistemaValor que ser� inserido na tabela
	 * @param usuarioLogado
	 *            O usuario que est� fazendo a alteracao
	 * @throws ControladorException
	 */
	public void atualizarParametroSistemaValor(ParametroSistemaValor paramValor, Usuario usuarioLogado) throws ControladorException;

}