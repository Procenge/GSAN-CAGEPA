package gcom.integracao.piramide.bean;

import java.math.BigDecimal;
import java.util.Date;

public class RetencaoHelper {

	private Date dataPagamento;

	private BigDecimal valorConta;

	private Integer codigoRegional;

	private BigDecimal valorRetidoPIS;

	private BigDecimal valorRetidoCOFFINS;

	private String cpfCliente;

	private String cnpjCliente;

	public RetencaoHelper() {

		super();
	}

	public RetencaoHelper(Date dataPagamento, BigDecimal valorConta, Integer codigoRegional, BigDecimal valorRetidoPIS,
							BigDecimal valorRetidoCOFFINS, String cpfCliente, String cnpjCliente) {

		super();
		this.dataPagamento = dataPagamento;
		this.valorConta = valorConta;
		this.codigoRegional = codigoRegional;
		this.valorRetidoPIS = valorRetidoPIS;
		this.valorRetidoCOFFINS = valorRetidoCOFFINS;
		this.cpfCliente = cpfCliente;
		this.cnpjCliente = cnpjCliente;
	}

	/**
	 * @return the dataPagamento
	 */
	public Date getDataPagamento(){

		return dataPagamento;
	}

	/**
	 * @param dataPagamento the dataPagamento to set
	 */
	public void setDataPagamento(Date dataPagamento){

		this.dataPagamento = dataPagamento;
	}

	/**
	 * @return the valorConta
	 */
	public BigDecimal getValorConta(){

		return valorConta;
	}

	/**
	 * @param valorConta the valorConta to set
	 */
	public void setValorConta(BigDecimal valorConta){

		this.valorConta = valorConta;
	}

	/**
	 * @return the codigoRegional
	 */
	public Integer getCodigoRegional(){

		return codigoRegional;
	}

	/**
	 * @param codigoRegional the codigoRegional to set
	 */
	public void setCodigoRegional(Integer codigoRegional){

		this.codigoRegional = codigoRegional;
	}

	/**
	 * @return the valorRetidoPIS
	 */
	public BigDecimal getValorRetidoPIS(){

		return valorRetidoPIS;
	}

	/**
	 * @param valorRetidoPIS the valorRetidoPIS to set
	 */
	public void setValorRetidoPIS(BigDecimal valorRetidoPIS){

		this.valorRetidoPIS = valorRetidoPIS;
	}

	/**
	 * @return the valorRetidoCOFFINS
	 */
	public BigDecimal getValorRetidoCOFFINS(){

		return valorRetidoCOFFINS;
	}

	/**
	 * @param valorRetidoCOFFINS the valorRetidoCOFFINS to set
	 */
	public void setValorRetidoCOFFINS(BigDecimal valorRetidoCOFFINS){

		this.valorRetidoCOFFINS = valorRetidoCOFFINS;
	}

	/**
	 * @return the cpfCliente
	 */
	public String getCpfCliente(){

		return cpfCliente;
	}

	/**
	 * @param cpfCliente the cpfCliente to set
	 */
	public void setCpfCliente(String cpfCliente){

		this.cpfCliente = cpfCliente;
	}

	/**
	 * @return the cnpjCliente
	 */
	public String getCnpjCliente(){

		return cnpjCliente;
	}

	/**
	 * @param cnpjCliente the cnpjCliente to set
	 */
	public void setCnpjCliente(String cnpjCliente){

		this.cnpjCliente = cnpjCliente;
	}

}
