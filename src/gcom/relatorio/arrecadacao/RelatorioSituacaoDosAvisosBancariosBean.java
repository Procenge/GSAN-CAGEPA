
package gcom.relatorio.arrecadacao;

import gcom.relatorio.RelatorioBean;
import gcom.util.Util;

import java.math.BigDecimal;

/**
 * Situação dos avisos bancários
 * 
 * @author Hebert Falcão
 * @since 04/10/2013
 */
public class RelatorioSituacaoDosAvisosBancariosBean
				implements RelatorioBean {

	private String idBanco;

	private String nomeBanco;

	private String codigoAgencia;

	private String nomeAgencia;

	private String idAviso;

	private String dataLancamento;

	private BigDecimal valorAviso;

	private BigDecimal valorInformadoCredito;

	private BigDecimal valorCalculadoCredito;

	private BigDecimal valorInformadoDebito;

	private BigDecimal valorCalculadoDebito;

	private String situacao;

	private Integer qtdAvisosClassificadosAgencia;

	private Integer qtdAvisosNaoClassificadosAgencia;

	private Integer qtdAvisosClassificadosBanco;

	private Integer qtdAvisosNaoClassificadosBanco;

	private Integer qtdAvisosClassificados;

	private Integer qtdAvisosNaoClassificados;

	public String getIdBanco(){

		String idBancoAux = idBanco;

		if(idBancoAux != null && idBancoAux.length() < 3){
			idBancoAux = Util.completarStringZeroEsquerda(idBanco, 3);
		}

		return idBancoAux;
	}

	public String getNomeBanco(){

		return nomeBanco;
	}

	public void setNomeBanco(String nomeBanco){

		this.nomeBanco = nomeBanco;
	}

	public String getCodigoAgencia(){

		return codigoAgencia;
	}

	public void setCodigoAgencia(String codigoAgencia){

		this.codigoAgencia = codigoAgencia;
	}

	public String getNomeAgencia(){

		return nomeAgencia;
	}

	public void setNomeAgencia(String nomeAgencia){

		this.nomeAgencia = nomeAgencia;
	}

	public String getIdAviso(){

		return idAviso;
	}

	public void setIdAviso(String idAviso){

		this.idAviso = idAviso;
	}

	public String getDataLancamento(){

		return dataLancamento;
	}

	public void setDataLancamento(String dataLancamento){

		this.dataLancamento = dataLancamento;
	}

	public BigDecimal getValorAviso(){

		return valorAviso;
	}

	public void setValorAviso(BigDecimal valorAviso){

		this.valorAviso = valorAviso;
	}

	public BigDecimal getValorInformadoCredito(){

		return valorInformadoCredito;
	}

	public void setValorInformadoCredito(BigDecimal valorInformadoCredito){

		this.valorInformadoCredito = valorInformadoCredito;
	}

	public BigDecimal getValorCalculadoCredito(){

		return valorCalculadoCredito;
	}

	public void setValorCalculadoCredito(BigDecimal valorCalculadoCredito){

		this.valorCalculadoCredito = valorCalculadoCredito;
	}

	public BigDecimal getValorInformadoDebito(){

		return valorInformadoDebito;
	}

	public void setValorInformadoDebito(BigDecimal valorInformadoDebito){

		this.valorInformadoDebito = valorInformadoDebito;
	}

	public BigDecimal getValorCalculadoDebito(){

		return valorCalculadoDebito;
	}

	public void setValorCalculadoDebito(BigDecimal valorCalculadoDebito){

		this.valorCalculadoDebito = valorCalculadoDebito;
	}

	public String getSituacao(){

		return situacao;
	}

	public void setSituacao(String situacao){

		this.situacao = situacao;
	}

	public void setIdBanco(String idBanco){

		this.idBanco = idBanco;
	}

	public Integer getQtdAvisosClassificadosAgencia(){

		return qtdAvisosClassificadosAgencia;
	}

	public void setQtdAvisosClassificadosAgencia(Integer qtdAvisosClassificadosAgencia){

		this.qtdAvisosClassificadosAgencia = qtdAvisosClassificadosAgencia;
	}

	public Integer getQtdAvisosNaoClassificadosAgencia(){

		return qtdAvisosNaoClassificadosAgencia;
	}

	public void setQtdAvisosNaoClassificadosAgencia(Integer qtdAvisosNaoClassificadosAgencia){

		this.qtdAvisosNaoClassificadosAgencia = qtdAvisosNaoClassificadosAgencia;
	}

	public Integer getQtdAvisosClassificadosBanco(){

		return qtdAvisosClassificadosBanco;
	}

	public void setQtdAvisosClassificadosBanco(Integer qtdAvisosClassificadosBanco){

		this.qtdAvisosClassificadosBanco = qtdAvisosClassificadosBanco;
	}

	public Integer getQtdAvisosNaoClassificadosBanco(){

		return qtdAvisosNaoClassificadosBanco;
	}

	public void setQtdAvisosNaoClassificadosBanco(Integer qtdAvisosNaoClassificadosBanco){

		this.qtdAvisosNaoClassificadosBanco = qtdAvisosNaoClassificadosBanco;
	}

	public Integer getQtdAvisosClassificados(){

		return qtdAvisosClassificados;
	}

	public void setQtdAvisosClassificados(Integer qtdAvisosClassificados){

		this.qtdAvisosClassificados = qtdAvisosClassificados;
	}

	public Integer getQtdAvisosNaoClassificados(){

		return qtdAvisosNaoClassificados;
	}

	public void setQtdAvisosNaoClassificados(Integer qtdAvisosNaoClassificados){

		this.qtdAvisosNaoClassificados = qtdAvisosNaoClassificados;
	}

}
