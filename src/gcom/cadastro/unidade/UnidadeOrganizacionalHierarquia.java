
package gcom.cadastro.unidade;

import gcom.interceptor.ObjetoGcom;

import java.util.Date;

/**
 * Unidade Organizacional Hierarquia
 * 
 * @author Hebert Falcão
 * @created 08/04/2011
 */
public class UnidadeOrganizacionalHierarquia
				extends ObjetoGcom {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private UnidadeOrganizacional unidade;

	private UnidadeOrganizacional unidadeSuperior;

	private Date ultimaAlteracao;

	/**
	 * @return the id
	 */
	public Integer getId(){

		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Integer id){

		this.id = id;
	}

	/**
	 * @return the unidade
	 */
	public UnidadeOrganizacional getUnidade(){

		return unidade;
	}

	/**
	 * @param unidade
	 *            the unidade to set
	 */
	public void setUnidade(UnidadeOrganizacional unidade){

		this.unidade = unidade;
	}

	/**
	 * @return the unidadeSuperior
	 */
	public UnidadeOrganizacional getUnidadeSuperior(){

		return unidadeSuperior;
	}

	/**
	 * @param unidadeSuperior
	 *            the unidadeSuperior to set
	 */
	public void setUnidadeSuperior(UnidadeOrganizacional unidadeSuperior){

		this.unidadeSuperior = unidadeSuperior;
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

	public String[] retornaCamposChavePrimaria(){

		String[] retorno = new String[1];
		retorno[0] = "id";
		return retorno;
	}

}