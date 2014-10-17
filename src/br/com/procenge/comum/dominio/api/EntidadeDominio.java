
package br.com.procenge.comum.dominio.api;

/**
 * @autor gilberto
 */
public interface EntidadeDominio {

	/**
	 * @return Retorna o atributo descricao.
	 */
	String getDescricao();

	/**
	 * @param descricao
	 *            O valor a ser atribuído ao atributo descricao.
	 */
	void setDescricao(String descricao);

	/**
	 * @return Retorna o atributo codigo.
	 */
	int getCodigo();

	/**
	 * @param codigo
	 *            O valor a ser atribuído ao atributo codigo.
	 */
	void setCodigo(int codigo);
}
