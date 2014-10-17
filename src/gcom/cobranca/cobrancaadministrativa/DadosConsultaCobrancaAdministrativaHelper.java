
package gcom.cobranca.cobrancaadministrativa;

import java.io.Serializable;

/**
 * Classe com dados para consulta de cobrança administrativa.
 */
public class DadosConsultaCobrancaAdministrativaHelper implements Serializable {

	private String localidade;

	private String descricaoLocalidade;

	private String idSetorComercial;

	private String setorComercial; // == Código

	private String descricaoSetorComercial;

	private String matricula;

	private String idUnidadeNegocio;

	private String nomeUnidadeNegocio;

	/**
	 * @return the localidade
	 */
	public String getLocalidade(){

		return localidade;
	}

	/**
	 * @param localidade the localidade to set
	 */
	public void setLocalidade(String localidade){

		this.localidade = localidade;
	}

	/**
	 * @return the descricaoLocalidade
	 */
	public String getDescricaoLocalidade(){

		return descricaoLocalidade;
	}

	/**
	 * @param descricaoLocalidade the descricaoLocalidade to set
	 */
	public void setDescricaoLocalidade(String descricaoLocalidade){

		this.descricaoLocalidade = descricaoLocalidade;
	}

	/**
	 * @return the idSetorComercial
	 */
	public String getIdSetorComercial(){

		return idSetorComercial;
	}

	/**
	 * @param setorComercial the idSetorComercial to set
	 */
	public void setIdSetorComercial(String idSetorComercial){

		this.idSetorComercial = idSetorComercial;
	}

	/**
	 * @return the setorComercial
	 */
	public String getSetorComercial(){

		return setorComercial;
	}

	/**
	 * @param setorComercial the setorComercial to set
	 */
	public void setSetorComercial(String setorComercial){

		this.setorComercial = setorComercial;
	}

	/**
	 * @return the setorComercial
	 */
	public String getDescricaoSetorComercial(){

		return descricaoSetorComercial;
	}

	/**
	 * @param setorComercial the setorComercial to set
	 */
	public void setDescricaoSetorComercial(String descricaoSetorComercial){

		this.descricaoSetorComercial = descricaoSetorComercial;
	}

	/**
	 * @return the matrícula
	 */
	public String getMatricula(){

		return matricula;
	}

	/**
	 * @param matricula the matrícula to set
	 */
	public void setMatricula(String matricula){

		this.matricula = matricula;
	}

	/**
	 * @return the idUnidadeNegocio
	 */
	public String getIdUnidadeNegocio(){

		return idUnidadeNegocio;
	}

	/**
	 * @param idUnidadeNegocio the idUnidadeNegocio to set
	 */
	public void setIdUnidadeNegocio(String idUnidadeNegocio){

		this.idUnidadeNegocio = idUnidadeNegocio;
	}

	/**
	 * @return the nomeUnidadeNegocio
	 */
	public String getNomeUnidadeNegocio(){

		return nomeUnidadeNegocio;
	}

	/**
	 * @param nomeUnidadeNegocio the nomeUnidadeNegocio to set
	 */
	public void setNomeUnidadeNegocio(String nomeUnidadeNegocio){

		this.nomeUnidadeNegocio = nomeUnidadeNegocio;
	}
}
