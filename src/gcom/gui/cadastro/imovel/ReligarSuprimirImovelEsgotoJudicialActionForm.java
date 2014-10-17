
package gcom.gui.cadastro.imovel;

import org.apache.struts.validator.ValidatorActionForm;

/**
 * UC - Religar Suprimir ImovelEsgotoJudicial
 * 
 * @author isilva
 * @date 08/02/2011
 */
public class ReligarSuprimirImovelEsgotoJudicialActionForm
				extends ValidatorActionForm {

	private static final long serialVersionUID = 1L;

	private String idImovel;

	private String inscricaoImovel;

	private String nomeClienteProprietario;

	private String enderecoClienteProprietario;

	/**
	 * @return the idImovel
	 */
	public String getIdImovel(){

		return idImovel;
	}

	/**
	 * @param idImovel
	 *            the idImovel to set
	 */
	public void setIdImovel(String idImovel){

		this.idImovel = idImovel;
	}

	/**
	 * @return the inscricaoImovel
	 */
	public String getInscricaoImovel(){

		return inscricaoImovel;
	}

	/**
	 * @param inscricaoImovel
	 *            the inscricaoImovel to set
	 */
	public void setInscricaoImovel(String inscricaoImovel){

		this.inscricaoImovel = inscricaoImovel;
	}

	/**
	 * @return the nomeClienteProprietario
	 */
	public String getNomeClienteProprietario(){

		return nomeClienteProprietario;
	}

	/**
	 * @param nomeClienteProprietario
	 *            the nomeClienteProprietario to set
	 */
	public void setNomeClienteProprietario(String nomeClienteProprietario){

		this.nomeClienteProprietario = nomeClienteProprietario;
	}

	/**
	 * @return the enderecoClienteProprietario
	 */
	public String getEnderecoClienteProprietario(){

		return enderecoClienteProprietario;
	}

	/**
	 * @param enderecoClienteProprietario
	 *            the enderecoClienteProprietario to set
	 */
	public void setEnderecoClienteProprietario(String enderecoClienteProprietario){

		this.enderecoClienteProprietario = enderecoClienteProprietario;
	}

}
