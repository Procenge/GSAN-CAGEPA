
package gcom.cobranca;

import java.io.Serializable;
import java.util.Date;

public class BoletoBancarioSituacaoHistorico
				implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private BoletoBancario boletoBancario;

	private BoletoBancarioSituacao boletoBancarioSituacao;

	private Date dataEntrada;

	private Date ultimaAlteracao;

	/**
	 * @param id
	 * @param cobrancaBoletoBancario
	 * @param cobrancaBoletoBancarioSituacao
	 * @param dataEntrada
	 * @param ultimaAlteracao
	 */
	public BoletoBancarioSituacaoHistorico(Integer id, BoletoBancario boletoBancario, BoletoBancarioSituacao boletoBancarioSituacao,
											Date dataEntrada, Date ultimaAlteracao) {

		this.id = id;
		this.boletoBancario = boletoBancario;
		this.boletoBancarioSituacao = boletoBancarioSituacao;
		this.dataEntrada = dataEntrada;
		this.ultimaAlteracao = ultimaAlteracao;
	}

	/** default constructor */
	public BoletoBancarioSituacaoHistorico() {

	}

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
	 * @return the cobrancaBoletoBancario
	 */
	public BoletoBancario getBoletoBancario(){

		return boletoBancario;
	}

	/**
	 * @param cobrancaBoletoBancario
	 *            the cobrancaBoletoBancario to set
	 */
	public void setBoletoBancario(BoletoBancario boletoBancario){

		this.boletoBancario = boletoBancario;
	}

	/**
	 * @return the cobrancaBoletoBancarioSituacao
	 */
	public BoletoBancarioSituacao getBoletoBancarioSituacao(){

		return boletoBancarioSituacao;
	}

	/**
	 * @param cobrancaBoletoBancarioSituacao
	 *            the cobrancaBoletoBancarioSituacao to set
	 */
	public void setBoletoBancarioSituacao(BoletoBancarioSituacao boletoBancarioSituacao){

		this.boletoBancarioSituacao = boletoBancarioSituacao;
	}

	/**
	 * @return the dataEntrada
	 */
	public Date getDataEntrada(){

		return dataEntrada;
	}

	/**
	 * @param dataEntrada
	 *            the dataEntrada to set
	 */
	public void setDataEntrada(Date dataEntrada){

		this.dataEntrada = dataEntrada;
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

}
