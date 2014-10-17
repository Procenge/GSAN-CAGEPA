
package gcom.relatorio.atendimentopublico;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author isilva
 * @date 25/03/2011
 */
public class EstatisticaAtendimentoPorAtendenteHelper {

	private Integer idUnidade;

	private String nomeUnidade;

	private Integer idAtendente;

	private String nomeAtendente;

	private Integer quantidadeRAAtendidas;

	private BigDecimal percentagemAtendimento;

	private Date dataRegistroAtendimento;

	private Date ultimaAlteracaoUnidade;

	private BigDecimal minutoAtendidos;

	private BigDecimal mediaAtendente;

	public EstatisticaAtendimentoPorAtendenteHelper() {

	}

	public EstatisticaAtendimentoPorAtendenteHelper(Integer idUnidade, String nomeUnidade, Integer idAtendente, String nomeAtendente,
													Integer quantidadeRAAtendidas, BigDecimal percentagemAtendimento,
													Date dataRegistroAtendimento, Date ultimaAlteracaoUnidade, BigDecimal minutoAtendidos,
													BigDecimal mediaAtendente) {

		this.idUnidade = idUnidade;
		this.nomeUnidade = nomeUnidade;
		this.idAtendente = idAtendente;
		this.nomeAtendente = nomeAtendente;
		this.quantidadeRAAtendidas = quantidadeRAAtendidas;
		this.percentagemAtendimento = percentagemAtendimento;
		this.dataRegistroAtendimento = dataRegistroAtendimento;
		this.ultimaAlteracaoUnidade = ultimaAlteracaoUnidade;
		this.minutoAtendidos = minutoAtendidos;
		this.mediaAtendente = mediaAtendente;
	}

	@Override
	public int hashCode(){

		final int prime = 31;
		int result = 1;
		result = prime * result + ((idAtendente == null) ? 0 : idAtendente.hashCode());
		result = prime * result + ((idUnidade == null) ? 0 : idUnidade.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj){

		if(this == obj) return true;
		if(obj == null) return false;
		if(!(obj instanceof EstatisticaAtendimentoPorAtendenteHelper)) return false;
		EstatisticaAtendimentoPorAtendenteHelper other = (EstatisticaAtendimentoPorAtendenteHelper) obj;
		if(idAtendente == null){
			if(other.idAtendente != null) return false;
		}else if(!idAtendente.equals(other.idAtendente)) return false;
		if(idUnidade == null){
			if(other.idUnidade != null) return false;
		}else if(!idUnidade.equals(other.idUnidade)) return false;
		return true;
	}

	/**
	 * @return the idUnidade
	 */
	public Integer getIdUnidade(){

		return idUnidade;
	}

	/**
	 * @param idUnidade
	 *            the idUnidade to set
	 */
	public void setIdUnidade(Integer idUnidade){

		this.idUnidade = idUnidade;
	}

	/**
	 * @return the nomeUnidade
	 */
	public String getNomeUnidade(){

		return nomeUnidade;
	}

	/**
	 * @param nomeUnidade
	 *            the nomeUnidade to set
	 */
	public void setNomeUnidade(String nomeUnidade){

		this.nomeUnidade = nomeUnidade;
	}

	/**
	 * @return the idAtendente
	 */
	public Integer getIdAtendente(){

		return idAtendente;
	}

	/**
	 * @param idAtendente
	 *            the idAtendente to set
	 */
	public void setIdAtendente(Integer idAtendente){

		this.idAtendente = idAtendente;
	}

	/**
	 * @return the nomeAtendente
	 */
	public String getNomeAtendente(){

		return nomeAtendente;
	}

	/**
	 * @param nomeAtendente
	 *            the nomeAtendente to set
	 */
	public void setNomeAtendente(String nomeAtendente){

		this.nomeAtendente = nomeAtendente;
	}

	/**
	 * @return the quantidadeRAAtendidas
	 */
	public Integer getQuantidadeRAAtendidas(){

		return quantidadeRAAtendidas;
	}

	/**
	 * @param quantidadeRAAtendidas
	 *            the quantidadeRAAtendidas to set
	 */
	public void setQuantidadeRAAtendidas(Integer quantidadeRAAtendidas){

		this.quantidadeRAAtendidas = quantidadeRAAtendidas;
	}

	/**
	 * @return the percentagemAtendimento
	 */
	public BigDecimal getPercentagemAtendimento(){

		return percentagemAtendimento;
	}

	/**
	 * @param percentagemAtendimento
	 *            the percentagemAtendimento to set
	 */
	public void setPercentagemAtendimento(BigDecimal percentagemAtendimento){

		this.percentagemAtendimento = percentagemAtendimento;
	}

	/**
	 * @return the dataRegistroAtendimento
	 */
	public Date getDataRegistroAtendimento(){

		return dataRegistroAtendimento;
	}

	/**
	 * @param dataRegistroAtendimento
	 *            the dataRegistroAtendimento to set
	 */
	public void setDataRegistroAtendimento(Date dataRegistroAtendimento){

		this.dataRegistroAtendimento = dataRegistroAtendimento;
	}

	/**
	 * @return the ultimaAlteracaoUnidade
	 */
	public Date getUltimaAlteracaoUnidade(){

		return ultimaAlteracaoUnidade;
	}

	/**
	 * @param ultimaAlteracaoUnidade
	 *            the ultimaAlteracaoUnidade to set
	 */
	public void setUltimaAlteracaoUnidade(Date ultimaAlteracaoUnidade){

		this.ultimaAlteracaoUnidade = ultimaAlteracaoUnidade;
	}

	/**
	 * @return the minutoAtendidos
	 */
	public BigDecimal getMinutoAtendidos(){

		return minutoAtendidos;
	}

	/**
	 * @param minutoAtendidos
	 *            the minutoAtendidos to set
	 */
	public void setMinutoAtendidos(BigDecimal minutoAtendidos){

		this.minutoAtendidos = minutoAtendidos;
	}

	/**
	 * @return the mediaAtendente
	 */
	public BigDecimal getMediaAtendente(){

		return mediaAtendente;
	}

	/**
	 * @param mediaAtendente
	 *            the mediaAtendente to set
	 */
	public void setMediaAtendente(BigDecimal mediaAtendente){

		this.mediaAtendente = mediaAtendente;
	}
}
