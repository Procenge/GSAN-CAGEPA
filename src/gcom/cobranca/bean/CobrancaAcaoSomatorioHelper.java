
package gcom.cobranca.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * @author isilva
 */
public class CobrancaAcaoSomatorioHelper
				implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer idCobrancaAcao;

	private String descricaoAcao;

	// Por Faixas
	private Integer quantidadeDiasVencimento;

	private BigDecimal porcentagemFaixa;

	private BigDecimal valorFixoRemuneracao;

	private Integer quantidadeDebitos;

	private BigDecimal valor;

	private BigDecimal comissao;

	// SubTotal
	private Integer subtTotalQuantidadeDebitos;

	private BigDecimal subtTotalValor;

	private BigDecimal subtTotalComissao;

	// Parcelamentos
	private Integer parcelamentoQuantidadeDebitos;

	private ArrayList<BigDecimal> porcentagemParcelamentos;

	private BigDecimal parcelamentoValor;

	private BigDecimal parcelamentoComissao;

	// Total
	private Integer totalQuantidadeDebitos;

	private BigDecimal totalValor;

	private BigDecimal totalComissao;

	public CobrancaAcaoSomatorioHelper() {

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
	 * @return the descricaoAcao
	 */
	public String getDescricaoAcao(){

		return descricaoAcao;
	}

	/**
	 * @param descricaoAcao
	 *            the descricaoAcao to set
	 */
	public void setDescricaoAcao(String descricaoAcao){

		this.descricaoAcao = descricaoAcao;
	}

	/**
	 * @return the quantidadeDiasVencimento
	 */
	public Integer getQuantidadeDiasVencimento(){

		return quantidadeDiasVencimento;
	}

	/**
	 * @param quantidadeDiasVencimento
	 *            the quantidadeDiasVencimento to set
	 */
	public void setQuantidadeDiasVencimento(Integer quantidadeDiasVencimento){

		this.quantidadeDiasVencimento = quantidadeDiasVencimento;
	}

	/**
	 * @return the porcentagemFaixa
	 */
	public BigDecimal getPorcentagemFaixa(){

		return porcentagemFaixa;
	}

	/**
	 * @param porcentagemFaixa
	 *            the porcentagemFaixa to set
	 */
	public void setPorcentagemFaixa(BigDecimal porcentagemFaixa){

		this.porcentagemFaixa = porcentagemFaixa;
	}

	/**
	 * @return the valorFixoRemuneracao
	 */
	public BigDecimal getValorFixoRemuneracao(){

		return valorFixoRemuneracao;
	}

	/**
	 * @param valorFixoRemuneracao
	 *            the valorFixoRemuneracao to set
	 */
	public void setValorFixoRemuneracao(BigDecimal valorFixoRemuneracao){

		this.valorFixoRemuneracao = valorFixoRemuneracao;
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
	 * @return the subtTotalQuantidadeDebitos
	 */
	public Integer getSubtTotalQuantidadeDebitos(){

		return subtTotalQuantidadeDebitos;
	}

	/**
	 * @param subtTotalQuantidadeDebitos
	 *            the subtTotalQuantidadeDebitos to set
	 */
	public void setSubtTotalQuantidadeDebitos(Integer subtTotalQuantidadeDebitos){

		this.subtTotalQuantidadeDebitos = subtTotalQuantidadeDebitos;
	}

	/**
	 * @return the subtTotalValor
	 */
	public BigDecimal getSubtTotalValor(){

		return subtTotalValor;
	}

	/**
	 * @param subtTotalValor
	 *            the subtTotalValor to set
	 */
	public void setSubtTotalValor(BigDecimal subtTotalValor){

		this.subtTotalValor = subtTotalValor;
	}

	/**
	 * @return the subtTotalComissao
	 */
	public BigDecimal getSubtTotalComissao(){

		return subtTotalComissao;
	}

	/**
	 * @param subtTotalComissao
	 *            the subtTotalComissao to set
	 */
	public void setSubtTotalComissao(BigDecimal subtTotalComissao){

		this.subtTotalComissao = subtTotalComissao;
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
	 * @return the porcentagemParcelamentos
	 */
	public ArrayList<BigDecimal> getPorcentagemParcelamentos(){

		return porcentagemParcelamentos;
	}

	/**
	 * @param porcentagemParcelamentos
	 *            the porcentagemParcelamentos to set
	 */
	public void setPorcentagemParcelamentos(ArrayList<BigDecimal> porcentagemParcelamentos){

		this.porcentagemParcelamentos = porcentagemParcelamentos;
	}

	/**
	 * @param porcentagemParcelamentos
	 */
	public void addPorcentagemParcelamentos(BigDecimal porcentagemParcelamentos){

		if(this.getPorcentagemParcelamentos() == null || getPorcentagemParcelamentos().isEmpty()){
			this.setPorcentagemParcelamentos(new ArrayList<BigDecimal>());
		}

		this.getPorcentagemParcelamentos().add(porcentagemParcelamentos);
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
