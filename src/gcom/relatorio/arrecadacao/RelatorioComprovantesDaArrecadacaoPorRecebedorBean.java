
package gcom.relatorio.arrecadacao;

import gcom.relatorio.RelatorioBean;
import gcom.util.Util;

import java.math.BigDecimal;

/**
 * Comprovantes da Arrecadação por Recebedor
 * 
 * @author Hebert Falcão
 * @since 28/09/2013
 */
public class RelatorioComprovantesDaArrecadacaoPorRecebedorBean
				implements RelatorioBean {

	private String idBanco;

	private String nomeBanco;

	private String codigoAgencia;

	private String nomeAgencia;

	private String nomeLocalidade;

	private String codigoContabil;

	private String dataLancamento;

	private String idAviso;

	private BigDecimal valorInformado;

	private BigDecimal valorCalculado;

	private BigDecimal valorInformadoCredito;

	private BigDecimal valorCalculadoCredito;

	private BigDecimal valorInformadoDebito;

	private BigDecimal valorCalculadoDebito;

	private String indicadorCreditoDebito;

	public String getIdBanco(){

		String idBancoAux = idBanco;

		if(idBancoAux != null && idBancoAux.length() < 3){
			idBancoAux = Util.completarStringZeroEsquerda(idBanco, 3);
		}

		return idBancoAux;
	}

	public void setIdBanco(String idBanco){

		this.idBanco = idBanco;
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

	public String getNomeLocalidade(){

		return nomeLocalidade;
	}

	public void setNomeLocalidade(String nomeLocalidade){

		this.nomeLocalidade = nomeLocalidade;
	}

	public String getCodigoContabil(){

		return codigoContabil;
	}

	public void setCodigoContabil(String codigoContabil){

		this.codigoContabil = codigoContabil;
	}

	public String getDataLancamento(){

		return dataLancamento;
	}

	public void setDataLancamento(String dataLancamento){

		this.dataLancamento = dataLancamento;
	}

	public String getIdAviso(){

		return idAviso;
	}

	public void setIdAviso(String idAviso){

		this.idAviso = idAviso;
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

	public String getIndicadorCreditoDebito(){

		return indicadorCreditoDebito;
	}

	public void setIndicadorCreditoDebito(String indicadorCreditoDebito){

		this.indicadorCreditoDebito = indicadorCreditoDebito;
	}

	public BigDecimal getValorInformado(){

		return valorInformado;
	}

	public void setValorInformado(BigDecimal valorInformado){

		this.valorInformado = valorInformado;
	}

	public BigDecimal getValorCalculado(){

		return valorCalculado;
	}

	public void setValorCalculado(BigDecimal valorCalculado){

		this.valorCalculado = valorCalculado;
	}

}
