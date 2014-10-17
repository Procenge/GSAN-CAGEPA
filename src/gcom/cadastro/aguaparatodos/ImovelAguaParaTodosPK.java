/**
 * 
 */

package gcom.cadastro.aguaparatodos;

import gcom.interceptor.ControleAlteracao;
import gcom.interceptor.ObjetoGcom;

import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * @author Bruno Ferreira dos Santos
 */
@ControleAlteracao()
public class ImovelAguaParaTodosPK
				extends ObjetoGcom {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

	private Long idContribuinte;

	public ImovelAguaParaTodosPK() {

	}

	public String[] retornaCamposChavePrimaria(){

		String[] retorno = new String[2];
		retorno[0] = "id";
		retorno[1] = "idContribuinte";
		return retorno;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Integer id){

		this.id = id;
	}

	/**
	 * @return the id
	 */
	public Integer getId(){

		return id;
	}

	/**
	 * @param idContribuinte
	 *            the idContribuinte to set
	 */
	public void setIdContribuinte(Long idContribuinte){

		this.idContribuinte = idContribuinte;
	}

	/**
	 * @return the idContribuinte
	 */
	public Long getIdContribuinte(){

		return idContribuinte;
	}

	public int hashCode(){

		return new HashCodeBuilder().append(getId()).append(getIdContribuinte()).toHashCode();
	}

}
