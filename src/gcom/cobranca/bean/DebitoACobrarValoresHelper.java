
package gcom.cobranca.bean;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * [UC0214] Efetuar Parcelamento de Débitos
 * 
 * @author Hebert Falcão
 * @created 19/05/2014
 */
public class DebitoACobrarValoresHelper
				implements Serializable {

	private static final long serialVersionUID = 1L;

	private String idRegistro;

	private Integer idDebitoTipo;

	private String descricaoDebitoTipo;

	private String mesAnoReferenciaDebito;

	private String mesAnoCobrancaDebito;

	private Integer numeroParcelasACobrar;

	private BigDecimal valorRestanteASerCobrado;

	private Integer numeroDiasSuspensao;

	private Integer idFinanciamentoTipo;

	private String descricaoFinanciamentoTipo;

	public Integer getIdFinanciamentoTipo(){

		return idFinanciamentoTipo;
	}

	public void setIdFinanciamentoTipo(Integer idFinanciamentoTipo){

		this.idFinanciamentoTipo = idFinanciamentoTipo;
	}

	public String getDescricaoFinanciamentoTipo(){

		return descricaoFinanciamentoTipo;
	}

	public void setDescricaoFinanciamentoTipo(String descricaoFinanciamentoTipo){

		this.descricaoFinanciamentoTipo = descricaoFinanciamentoTipo;
	}

	public String getIdRegistro(){

		return idRegistro;
	}

	public void setIdRegistro(String idRegistro){

		this.idRegistro = idRegistro;
	}

	public Integer getIdDebitoTipo(){

		return idDebitoTipo;
	}

	public void setIdDebitoTipo(Integer idDebitoTipo){

		this.idDebitoTipo = idDebitoTipo;
	}

	public String getDescricaoDebitoTipo(){

		return descricaoDebitoTipo;
	}

	public void setDescricaoDebitoTipo(String descricaoDebitoTipo){

		this.descricaoDebitoTipo = descricaoDebitoTipo;
	}

	public String getMesAnoReferenciaDebito(){

		return mesAnoReferenciaDebito;
	}

	public void setMesAnoReferenciaDebito(String mesAnoReferenciaDebito){

		this.mesAnoReferenciaDebito = mesAnoReferenciaDebito;
	}

	public String getMesAnoCobrancaDebito(){

		return mesAnoCobrancaDebito;
	}

	public void setMesAnoCobrancaDebito(String mesAnoCobrancaDebito){

		this.mesAnoCobrancaDebito = mesAnoCobrancaDebito;
	}

	public Integer getNumeroParcelasACobrar(){

		return numeroParcelasACobrar;
	}

	public void setNumeroParcelasACobrar(Integer numeroParcelasACobrar){

		this.numeroParcelasACobrar = numeroParcelasACobrar;
	}

	public BigDecimal getValorRestanteASerCobrado(){

		return valorRestanteASerCobrado;
	}

	public void setValorRestanteASerCobrado(BigDecimal valorRestanteASerCobrado){

		this.valorRestanteASerCobrado = valorRestanteASerCobrado;
	}

	public Integer getNumeroDiasSuspensao(){

		return numeroDiasSuspensao;
	}

	public void setNumeroDiasSuspensao(Integer numeroDiasSuspensao){

		this.numeroDiasSuspensao = numeroDiasSuspensao;
	}

}
