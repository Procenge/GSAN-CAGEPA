
package gcom.agenciavirtual.cobranca;

import java.math.BigDecimal;
import java.util.Collection;

public class ColecaoDebitosJSONHelper {

	private Integer totalContas;

	private BigDecimal valorTotalDebitos;

	private BigDecimal valorTotalDebitosEmAnalise;

	private Boolean indicadorExecucaoFiscal;

	private Collection<DebitoJSONHelper> debitos;

	private Collection<ImovelDebitoJSONHelper> imovelDebitos;


	public Integer getTotalContas(){

		return totalContas;
	}

	public void setTotalContas(Integer totalContas){

		this.totalContas = totalContas;
	}

	public BigDecimal getValorTotalDebitos(){

		return valorTotalDebitos;
	}

	public void setValorTotalDebitos(BigDecimal valorTotalDebitos){

		this.valorTotalDebitos = valorTotalDebitos;
	}

	public Collection<DebitoJSONHelper> getDebitos(){

		return debitos;
	}

	public void setDebitos(Collection<DebitoJSONHelper> debitos){

		this.debitos = debitos;
	}

	public BigDecimal getValorTotalDebitosEmAnalise(){

		return valorTotalDebitosEmAnalise;
	}

	public void setValorTotalDebitosEmAnalise(BigDecimal valorTotalDebitosEmAnalise){

		this.valorTotalDebitosEmAnalise = valorTotalDebitosEmAnalise;
	}

	public Collection<ImovelDebitoJSONHelper> getImovelDebitos(){

		return imovelDebitos;
	}

	public void setImovelDebitos(Collection<ImovelDebitoJSONHelper> imovelDebitos){

		this.imovelDebitos = imovelDebitos;
	}

	public Boolean getIndicadorExecucaoFiscal(){

		return indicadorExecucaoFiscal;
	}

	public void setIndicadorExecucaoFiscal(Boolean indicadorExecucaoFiscal){

		this.indicadorExecucaoFiscal = indicadorExecucaoFiscal;
	}
}
