
package br.com.procenge.parametrosistema.api;

import br.com.procenge.comum.negocio.api.EntidadeNegocio;

public interface ParametroSistema
				extends EntidadeNegocio, Comparable<ParametroSistema> {

	String BEAN_ID_PARAMETRO_SISTEMA = "parametroSistema";

	/**
	 * @return the codigo
	 */
	String getCodigo();

	/**
	 * @param codigo
	 *            the codigo to set
	 */
	void setCodigo(String codigo);

	/**
	 * @return the descricao
	 */
	String getDescricao();

	/**
	 * @param descricao
	 *            the descricao to set
	 */
	void setDescricao(String descricao);

	/**
	 * @return the tipoParametroSistema
	 */
	TipoParametroSistema getTipoParametroSistema();

	/**
	 * @param tipoParametroSistema
	 *            the tipoParametroSistema to set
	 */
	void setTipoParametroSistema(TipoParametroSistema tipoParametroSistema);

	/**
	 * @return the classeEntidade
	 */
	String getClasseEntidade();

	/**
	 * @param classeEntidade
	 *            the classeEntidade to set
	 */
	void setClasseEntidade(String classeEntidade);

	/**
	 * @return the valor
	 */
	String getValor();

	/**
	 * @param valor
	 *            the valor to set
	 */
	void setValor(String valor);

	/**
	 * @return the uso
	 */
	public int getUso();

	/**
	 * @param uso
	 *            the uso to set
	 */
	public void setUso(int uso);

}