
package gcom.cobranca.bean;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author isilva
 */
public class AcaoHelper
				implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer idCobrancaAcao;

	private Integer diasAte;

	private BigDecimal porcentagemRemuneracao;

	private Integer quantidadeDebitos;

	private BigDecimal valor;

	private BigDecimal comissao;

	private Integer parcelamentoQuantidadeDebitos;

	private BigDecimal parcelamentoValor;

	private BigDecimal parcelamentoComissao;

	private Integer totalQuantidadeDebitos;

	private BigDecimal totalValor;

	private BigDecimal totalComissao;

	public AcaoHelper() {

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
	 * @return the diasAte
	 */
	public Integer getDiasAte(){

		return diasAte;
	}

	/**
	 * @param diasAte
	 *            the diasAte to set
	 */
	public void setDiasAte(Integer diasAte){

		this.diasAte = diasAte;
	}

	/**
	 * @return the porcentagemRemuneracao
	 */
	public BigDecimal getPorcentagemRemuneracao(){

		return porcentagemRemuneracao;
	}

	/**
	 * @param porcentagemRemuneracao
	 *            the porcentagemRemuneracao to set
	 */
	public void setPorcentagemRemuneracao(BigDecimal porcentagemRemuneracao){

		this.porcentagemRemuneracao = porcentagemRemuneracao;
	}

	/**
	 * @return the quantidadeDebitos
	 */
	public Integer getQuantidadeDebitos(){

		return quantidadeDebitos;
	}

	/**
	 * @param quantidadeDebitos
	 *            the quantidadeDebitos to set
	 */
	public void setQuantidadeDebitos(Integer quantidadeDebitos){

		this.quantidadeDebitos = quantidadeDebitos;
	}

	/**
	 * @return the valor
	 */
	public BigDecimal getValor(){

		return valor;
	}

	/**
	 * @param valor
	 *            the valor to set
	 */
	public void setValor(BigDecimal valor){

		this.valor = valor;
	}

	/**
	 * @return the comissao
	 */
	public BigDecimal getComissao(){

		return comissao;
	}

	/**
	 * @param comissao
	 *            the comissao to set
	 */
	public void setComissao(BigDecimal comissao){

		this.comissao = comissao;
	}

	/**
	 * @return the parcelamentoQuantidadeDebitos
	 */
	public Integer getParcelamentoQuantidadeDebitos(){

		return parcelamentoQuantidadeDebitos;
	}

	/**
	 * @param parcelamentoQuantidadeDebitos
	 *            the parcelamentoQuantidadeDebitos to set
	 */
	public void setParcelamentoQuantidadeDebitos(Integer parcelamentoQuantidadeDebitos){

		this.parcelamentoQuantidadeDebitos = parcelamentoQuantidadeDebitos;
	}

	/**
	 * @return the parcelamentoValor
	 */
	public BigDecimal getParcelamentoValor(){

		return parcelamentoValor;
	}

	/**
	 * @param parcelamentoValor
	 *            the parcelamentoValor to set
	 */
	public void setParcelamentoValor(BigDecimal parcelamentoValor){

		this.parcelamentoValor = parcelamentoValor;
	}

	/**
	 * @return the parcelamentoComissao
	 */
	public BigDecimal getParcelamentoComissao(){

		return parcelamentoComissao;
	}

	/**
	 * @param parcelamentoComissao
	 *            the parcelamentoComissao to set
	 */
	public void setParcelamentoComissao(BigDecimal parcelamentoComissao){

		this.parcelamentoComissao = parcelamentoComissao;
	}

	/**
	 * @return the totalQuantidadeDebitos
	 */
	public Integer getTotalQuantidadeDebitos(){

		return totalQuantidadeDebitos;
	}

	/**
	 * @param totalQuantidadeDebitos
	 *            the totalQuantidadeDebitos to set
	 */
	public void setTotalQuantidadeDebitos(Integer totalQuantidadeDebitos){

		this.totalQuantidadeDebitos = totalQuantidadeDebitos;
	}

	/**
	 * @return the totalValor
	 */
	public BigDecimal getTotalValor(){

		return totalValor;
	}

	/**
	 * @param totalValor
	 *            the totalValor to set
	 */
	public void setTotalValor(BigDecimal totalValor){

		this.totalValor = totalValor;
	}

	/**
	 * @return the totalComissao
	 */
	public BigDecimal getTotalComissao(){

		return totalComissao;
	}

	/**
	 * @param totalComissao
	 *            the totalComissao to set
	 */
	public void setTotalComissao(BigDecimal totalComissao){

		this.totalComissao = totalComissao;
	}
}
