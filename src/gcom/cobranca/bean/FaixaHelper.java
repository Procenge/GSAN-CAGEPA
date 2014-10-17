
package gcom.cobranca.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author isilva
 */
public class FaixaHelper
				implements Serializable {

	private static final long serialVersionUID = 1L;

	private String faixaDebito;

	private Integer diasAte;

	private BigDecimal porcentagemRemuneracao;

	private BigDecimal valorRemuneracao;

	private Collection<AcaoHelper> acaoHelpers;

	// Parcelamento
	private String tituloParcelamentos;

	private ArrayList<BigDecimal> porcentagemParcelamentos;

	// Total por Faixa
	private Integer totalQuantidadeDebitos;

	private BigDecimal totalValor;

	private BigDecimal totalComissao;

	private BigDecimal porcentagemValor;

	public FaixaHelper() {

	}

	public FaixaHelper(String faixaDebito, Integer diasAte, BigDecimal porcentagemRemuneracao, BigDecimal valorRemuneracao,
						Collection<AcaoHelper> acaoHelpers, String tituloParcelamentos, ArrayList<BigDecimal> porcentagemParcelamentos,
						Integer totalQuantidadeDebitos, BigDecimal totalValor, BigDecimal totalComissao, BigDecimal porcentagemValor) {

		this.faixaDebito = faixaDebito;
		this.diasAte = diasAte;
		this.porcentagemRemuneracao = porcentagemRemuneracao;
		this.valorRemuneracao = valorRemuneracao;
		this.acaoHelpers = acaoHelpers;
		this.tituloParcelamentos = tituloParcelamentos;
		this.porcentagemParcelamentos = porcentagemParcelamentos;
		this.totalQuantidadeDebitos = totalQuantidadeDebitos;
		this.totalValor = totalValor;
		this.totalComissao = totalComissao;
		this.porcentagemValor = porcentagemValor;
	}

	/**
	 * @return the faixaDebito
	 */
	public String getFaixaDebito(){

		return faixaDebito;
	}

	/**
	 * @param faixaDebito
	 *            the faixaDebito to set
	 */
	public void setFaixaDebito(String faixaDebito){

		this.faixaDebito = faixaDebito;
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
	 * @return the valorRemuneracao
	 */
	public BigDecimal getValorRemuneracao(){

		return valorRemuneracao;
	}

	/**
	 * @param valorRemuneracao
	 *            the valorRemuneracao to set
	 */
	public void setValorRemuneracao(BigDecimal valorRemuneracao){

		this.valorRemuneracao = valorRemuneracao;
	}

	/**
	 * @return the acaoHelpers
	 */
	public Collection<AcaoHelper> getAcaoHelpers(){

		return acaoHelpers;
	}

	/**
	 * @param acaoHelpers
	 *            the acaoHelpers to set
	 */
	public void setAcaoHelpers(Collection<AcaoHelper> acaoHelpers){

		this.acaoHelpers = acaoHelpers;
	}

	/**
	 * @return the tituloParcelamentos
	 */
	public String getTituloParcelamentos(){

		return tituloParcelamentos;
	}

	/**
	 * @param tituloParcelamentos
	 *            the tituloParcelamentos to set
	 */
	public void setTituloParcelamentos(String tituloParcelamentos){

		this.tituloParcelamentos = tituloParcelamentos;
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

	/**
	 * @return the porcentagemValor
	 */
	public BigDecimal getPorcentagemValor(){

		return porcentagemValor;
	}

	/**
	 * @param porcentagemValor
	 *            the porcentagemValor to set
	 */
	public void setPorcentagemValor(BigDecimal porcentagemValor){

		this.porcentagemValor = porcentagemValor;
	}

	public void addPorcentagemParcelamentos(BigDecimal porcentagemParcelamento){

		if(this.getPorcentagemParcelamentos() == null || getPorcentagemParcelamentos().isEmpty()){
			this.setPorcentagemParcelamentos(new ArrayList<BigDecimal>());
		}

		this.getPorcentagemParcelamentos().add(porcentagemParcelamento);
	}
}
