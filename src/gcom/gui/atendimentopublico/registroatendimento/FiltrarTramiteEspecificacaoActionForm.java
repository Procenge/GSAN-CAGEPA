
package gcom.gui.atendimentopublico.registroatendimento;

import org.apache.struts.action.ActionForm;

/**
 * Filtrar Trâmite Especificação
 * 
 * @author Hebert Falcão
 * @date 25/03/2011
 */
public class FiltrarTramiteEspecificacaoActionForm
				extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String idSolicitacaoTipo;

	private String idSolicitacaoTipoEspecificacao;

	private String idLocalidade;

	private String descricaoLocalidade;

	private String codigoSetorComercial;

	private String descricaoSetorComercial;

	private String idMunicipio;

	private String nomeMunicipio;

	private String codigoBairro;

	private String nomeBairro;


	private String idUnidadeOrganizacionalOrigem;

	private String descricaoUnidadeOrganizacionalOrigem;

	private String idUnidadeOrganizacionalDestino;

	private String descricaoUnidadeOrganizacionalDestino;

	private String indicadorPrimeiroTramite;

	private boolean atualizar = true;

	/**
	 * @return the idSolicitacaoTipo
	 */
	public String getIdSolicitacaoTipo(){

		return idSolicitacaoTipo;
	}

	/**
	 * @param idSolicitacaoTipo
	 *            the idSolicitacaoTipo to set
	 */
	public void setIdSolicitacaoTipo(String idSolicitacaoTipo){

		this.idSolicitacaoTipo = idSolicitacaoTipo;
	}

	/**
	 * @return the idSolicitacaoTipoEspecificacao
	 */
	public String getIdSolicitacaoTipoEspecificacao(){

		return idSolicitacaoTipoEspecificacao;
	}

	/**
	 * @param idSolicitacaoTipoEspecificacao
	 *            the idSolicitacaoTipoEspecificacao to set
	 */
	public void setIdSolicitacaoTipoEspecificacao(String idSolicitacaoTipoEspecificacao){

		this.idSolicitacaoTipoEspecificacao = idSolicitacaoTipoEspecificacao;
	}

	/**
	 * @return the idLocalidade
	 */
	public String getIdLocalidade(){

		return idLocalidade;
	}

	/**
	 * @param idLocalidade
	 *            the idLocalidade to set
	 */
	public void setIdLocalidade(String idLocalidade){

		this.idLocalidade = idLocalidade;
	}

	/**
	 * @return the descricaoLocalidade
	 */
	public String getDescricaoLocalidade(){

		return descricaoLocalidade;
	}

	/**
	 * @param descricaoLocalidade
	 *            the descricaoLocalidade to set
	 */
	public void setDescricaoLocalidade(String descricaoLocalidade){

		this.descricaoLocalidade = descricaoLocalidade;
	}

	/**
	 * @return the codigoSetorComercial
	 */
	public String getCodigoSetorComercial(){

		return codigoSetorComercial;
	}

	/**
	 * @param codigoSetorComercial
	 *            the codigoSetorComercial to set
	 */
	public void setCodigoSetorComercial(String codigoSetorComercial){

		this.codigoSetorComercial = codigoSetorComercial;
	}

	/**
	 * @return the descricaoSetorComercial
	 */
	public String getDescricaoSetorComercial(){

		return descricaoSetorComercial;
	}

	/**
	 * @param descricaoSetorComercial
	 *            the descricaoSetorComercial to set
	 */
	public void setDescricaoSetorComercial(String descricaoSetorComercial){

		this.descricaoSetorComercial = descricaoSetorComercial;
	}

	/**
	 * @return the idMunicipio
	 */
	public String getIdMunicipio(){

		return idMunicipio;
	}

	/**
	 * @param idMunicipio
	 *            the idMunicipio to set
	 */
	public void setIdMunicipio(String idMunicipio){

		this.idMunicipio = idMunicipio;
	}

	/**
	 * @return the nomeMunicipio
	 */
	public String getNomeMunicipio(){

		return nomeMunicipio;
	}

	/**
	 * @param nomeMunicipio
	 *            the nomeMunicipio to set
	 */
	public void setNomeMunicipio(String nomeMunicipio){

		this.nomeMunicipio = nomeMunicipio;
	}

	/**
	 * @return the codigoBairro
	 */
	public String getCodigoBairro(){

		return codigoBairro;
	}

	/**
	 * @param codigoBairro
	 *            the codigoBairro to set
	 */
	public void setCodigoBairro(String codigoBairro){

		this.codigoBairro = codigoBairro;
	}

	/**
	 * @return the nomeBairro
	 */
	public String getNomeBairro(){

		return nomeBairro;
	}

	/**
	 * @param nomeBairro
	 *            the nomeBairro to set
	 */
	public void setNomeBairro(String nomeBairro){

		this.nomeBairro = nomeBairro;
	}


	/**
	 * @return the idUnidadeOrganizacionalOrigem
	 */
	public String getIdUnidadeOrganizacionalOrigem(){

		return idUnidadeOrganizacionalOrigem;
	}

	/**
	 * @param idUnidadeOrganizacionalOrigem
	 *            the idUnidadeOrganizacionalOrigem to set
	 */
	public void setIdUnidadeOrganizacionalOrigem(String idUnidadeOrganizacionalOrigem){

		this.idUnidadeOrganizacionalOrigem = idUnidadeOrganizacionalOrigem;
	}

	/**
	 * @return the descricaoUnidadeOrganizacionalOrigem
	 */
	public String getDescricaoUnidadeOrganizacionalOrigem(){

		return descricaoUnidadeOrganizacionalOrigem;
	}

	/**
	 * @param descricaoUnidadeOrganizacionalOrigem
	 *            the descricaoUnidadeOrganizacionalOrigem to set
	 */
	public void setDescricaoUnidadeOrganizacionalOrigem(String descricaoUnidadeOrganizacionalOrigem){

		this.descricaoUnidadeOrganizacionalOrigem = descricaoUnidadeOrganizacionalOrigem;
	}

	/**
	 * @return the idUnidadeOrganizacionalDestino
	 */
	public String getIdUnidadeOrganizacionalDestino(){

		return idUnidadeOrganizacionalDestino;
	}

	/**
	 * @param idUnidadeOrganizacionalDestino
	 *            the idUnidadeOrganizacionalDestino to set
	 */
	public void setIdUnidadeOrganizacionalDestino(String idUnidadeOrganizacionalDestino){

		this.idUnidadeOrganizacionalDestino = idUnidadeOrganizacionalDestino;
	}

	/**
	 * @return the descricaoUnidadeOrganizacionalDestino
	 */
	public String getDescricaoUnidadeOrganizacionalDestino(){

		return descricaoUnidadeOrganizacionalDestino;
	}

	/**
	 * @param descricaoUnidadeOrganizacionalDestino
	 *            the descricaoUnidadeOrganizacionalDestino to set
	 */
	public void setDescricaoUnidadeOrganizacionalDestino(String descricaoUnidadeOrganizacionalDestino){

		this.descricaoUnidadeOrganizacionalDestino = descricaoUnidadeOrganizacionalDestino;
	}

	/**
	 * @return the atualizar
	 */
	public boolean isAtualizar(){

		return atualizar;
	}

	/**
	 * @param atualizar
	 *            the atualizar to set
	 */
	public void setAtualizar(boolean atualizar){

		this.atualizar = atualizar;
	}

	public String getIndicadorPrimeiroTramite(){

		return indicadorPrimeiroTramite;
	}

	public void setIndicadorPrimeiroTramite(String indicadorPrimeiroTramite){

		this.indicadorPrimeiroTramite = indicadorPrimeiroTramite;
	}

}
