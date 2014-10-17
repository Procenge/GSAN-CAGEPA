
package gcom.relatorio.atendimentopublico;

import gcom.relatorio.RelatorioBean;

public class RelatorioRAComProcessoAdmJudBean
				implements RelatorioBean {

	private Boolean ehUnidadeSuperior;

	private Integer idUnidadeSuperior;

	private String descricaoUnidadeSuperior;

	private Integer idUnidadeAtendimento;

	private String descricaoUnidadeAtendimento;

	private Integer idTipoSolicitacao;

	private String descricaoTipoSolicitacao;

	private Integer idEspecificacao;

	private String descricaoEspecificacao;

	private String numeroProcessoAgencia;

	private String numeroRA;

	private String situacaoRA;

	private Integer idUnidadeAtual;

	private String descricaoUnidadeAtual;

	/**
	 * @param ehUnidadeSuperior
	 * @param idUnidadeSuperior
	 * @param descricaoUnidadeSuperior
	 * @param idUnidadeAtendimento
	 * @param descricaoUnidadeAtendimento
	 * @param idTipoSolicitacao
	 * @param descricaoTipoSolicitacao
	 * @param idEspecificacao
	 * @param descricaoEspecificacao
	 * @param numeroProcessoAgencia
	 * @param numeroRA
	 * @param situacaoRA
	 * @param idUnidadeAtual
	 * @param descricaoUnidadeAtual
	 */
	public RelatorioRAComProcessoAdmJudBean(Boolean ehUnidadeSuperior, Integer idUnidadeSuperior, String descricaoUnidadeSuperior,
											Integer idUnidadeAtendimento, String descricaoUnidadeAtendimento, Integer idTipoSolicitacao,
											String descricaoTipoSolicitacao, Integer idEspecificacao, String descricaoEspecificacao,
											String numeroProcessoAgencia, String numeroRA, String situacaoRA, Integer idUnidadeAtual,
											String descricaoUnidadeAtual) {

		super();
		this.ehUnidadeSuperior = ehUnidadeSuperior;
		this.idUnidadeSuperior = idUnidadeSuperior;
		this.descricaoUnidadeSuperior = descricaoUnidadeSuperior;
		this.idUnidadeAtendimento = idUnidadeAtendimento;
		this.descricaoUnidadeAtendimento = descricaoUnidadeAtendimento;
		this.idTipoSolicitacao = idTipoSolicitacao;
		this.descricaoTipoSolicitacao = descricaoTipoSolicitacao;
		this.idEspecificacao = idEspecificacao;
		this.descricaoEspecificacao = descricaoEspecificacao;
		this.numeroProcessoAgencia = numeroProcessoAgencia;
		this.numeroRA = numeroRA;
		this.situacaoRA = situacaoRA;
		this.idUnidadeAtual = idUnidadeAtual;
		this.descricaoUnidadeAtual = descricaoUnidadeAtual;
	}

	
	/**
	 * @return the ehUnidadeSuperior
	 */
	public Boolean getEhUnidadeSuperior(){

		return ehUnidadeSuperior;
	}


	/**
	 * @param ehUnidadeSuperior
	 *            the ehUnidadeSuperior to set
	 */
	public void setEhUnidadeSuperior(Boolean ehUnidadeSuperior){

		this.ehUnidadeSuperior = ehUnidadeSuperior;
	}


	/**
	 * @return the idUnidadeSuperior
	 */
	public Integer getIdUnidadeSuperior(){

		return idUnidadeSuperior;
	}


	/**
	 * @param idUnidadeSuperior
	 *            the idUnidadeSuperior to set
	 */
	public void setIdUnidadeSuperior(Integer idUnidadeSuperior){

		this.idUnidadeSuperior = idUnidadeSuperior;
	}


	/**
	 * @return the descricaoUnidadeSuperior
	 */
	public String getDescricaoUnidadeSuperior(){

		return descricaoUnidadeSuperior;
	}


	/**
	 * @param descricaoUnidadeSuperior
	 *            the descricaoUnidadeSuperior to set
	 */
	public void setDescricaoUnidadeSuperior(String descricaoUnidadeSuperior){

		this.descricaoUnidadeSuperior = descricaoUnidadeSuperior;
	}


	/**
	 * @return the idUnidadeAtendimento
	 */
	public Integer getIdUnidadeAtendimento(){

		return idUnidadeAtendimento;
	}


	/**
	 * @param idUnidadeAtendimento
	 *            the idUnidadeAtendimento to set
	 */
	public void setIdUnidadeAtendimento(Integer idUnidadeAtendimento){

		this.idUnidadeAtendimento = idUnidadeAtendimento;
	}


	/**
	 * @return the descricaoUnidadeAtendimento
	 */
	public String getDescricaoUnidadeAtendimento(){

		return descricaoUnidadeAtendimento;
	}


	/**
	 * @param descricaoUnidadeAtendimento
	 *            the descricaoUnidadeAtendimento to set
	 */
	public void setDescricaoUnidadeAtendimento(String descricaoUnidadeAtendimento){

		this.descricaoUnidadeAtendimento = descricaoUnidadeAtendimento;
	}


	/**
	 * @return the idTipoSolicitacao
	 */
	public Integer getIdTipoSolicitacao(){

		return idTipoSolicitacao;
	}


	/**
	 * @param idTipoSolicitacao
	 *            the idTipoSolicitacao to set
	 */
	public void setIdTipoSolicitacao(Integer idTipoSolicitacao){

		this.idTipoSolicitacao = idTipoSolicitacao;
	}


	/**
	 * @return the descricaoTipoSolicitacao
	 */
	public String getDescricaoTipoSolicitacao(){

		return descricaoTipoSolicitacao;
	}


	/**
	 * @param descricaoTipoSolicitacao
	 *            the descricaoTipoSolicitacao to set
	 */
	public void setDescricaoTipoSolicitacao(String descricaoTipoSolicitacao){

		this.descricaoTipoSolicitacao = descricaoTipoSolicitacao;
	}


	/**
	 * @return the idEspecificacao
	 */
	public Integer getIdEspecificacao(){

		return idEspecificacao;
	}


	/**
	 * @param idEspecificacao
	 *            the idEspecificacao to set
	 */
	public void setIdEspecificacao(Integer idEspecificacao){

		this.idEspecificacao = idEspecificacao;
	}


	/**
	 * @return the descricaoEspecificacao
	 */
	public String getDescricaoEspecificacao(){

		return descricaoEspecificacao;
	}


	/**
	 * @param descricaoEspecificacao
	 *            the descricaoEspecificacao to set
	 */
	public void setDescricaoEspecificacao(String descricaoEspecificacao){

		this.descricaoEspecificacao = descricaoEspecificacao;
	}


	/**
	 * @return the numeroProcessoAgencia
	 */
	public String getNumeroProcessoAgencia(){

		return numeroProcessoAgencia;
	}


	/**
	 * @param numeroProcessoAgencia
	 *            the numeroProcessoAgencia to set
	 */
	public void setNumeroProcessoAgencia(String numeroProcessoAgencia){

		this.numeroProcessoAgencia = numeroProcessoAgencia;
	}


	/**
	 * @return the numeroRA
	 */
	public String getNumeroRA(){

		return numeroRA;
	}


	/**
	 * @param numeroRA
	 *            the numeroRA to set
	 */
	public void setNumeroRA(String numeroRA){

		this.numeroRA = numeroRA;
	}


	/**
	 * @return the situacaoRA
	 */
	public String getSituacaoRA(){

		return situacaoRA;
	}


	/**
	 * @param situacaoRA
	 *            the situacaoRA to set
	 */
	public void setSituacaoRA(String situacaoRA){

		this.situacaoRA = situacaoRA;
	}

	/**
	 * @return the idUnidadeAtual
	 */
	public Integer getIdUnidadeAtual(){

		return idUnidadeAtual;
	}

	/**
	 * @param idUnidadeAtual
	 *            the idUnidadeAtual to set
	 */
	public void setIdUnidadeAtual(Integer idUnidadeAtual){

		this.idUnidadeAtual = idUnidadeAtual;
	}

	/**
	 * @return the descricaoUnidadeAtual
	 */
	public String getDescricaoUnidadeAtual(){

		return descricaoUnidadeAtual;
	}

	/**
	 * @param descricaoUnidadeAtual
	 *            the descricaoUnidadeAtual to set
	 */
	public void setDescricaoUnidadeAtual(String descricaoUnidadeAtual){

		this.descricaoUnidadeAtual = descricaoUnidadeAtual;
	}


}
