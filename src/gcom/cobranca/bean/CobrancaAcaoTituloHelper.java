
package gcom.cobranca.bean;

import java.io.Serializable;

/**
 * @author isilva
 */
public class CobrancaAcaoTituloHelper
				implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer idCobrancaAcao;

	private String descricao;

	public CobrancaAcaoTituloHelper() {

	}

	public CobrancaAcaoTituloHelper(String descricao) {

		this.descricao = descricao;
	}

	public CobrancaAcaoTituloHelper(Integer idCobrancaAcao, String descricao) {

		this.idCobrancaAcao = idCobrancaAcao;
		this.descricao = descricao;
	}

	/**
	 * @return the idCobrancaAcao
	 */
	public Integer getIdCobrancaAcao(){

		return idCobrancaAcao;
	}

	/**
	 * @param idCobrancaAcao
	 *            the idCobrancaAcao to set
	 */
	public void setIdCobrancaAcao(Integer idCobrancaAcao){

		this.idCobrancaAcao = idCobrancaAcao;
	}

	/**
	 * @return the descricao
	 */
	public String getDescricao(){

		return descricao;
	}

	/**
	 * @param descricao
	 *            the descricao to set
	 */
	public void setDescricao(String descricao){

		this.descricao = descricao;
	}
}
