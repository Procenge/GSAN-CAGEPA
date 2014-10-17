
package gcom.relatorio.cobranca.prescricao;

import gcom.relatorio.RelatorioBean;

import java.math.BigDecimal;

public class SubRelatorioDebitosPrescritosGuiaPagamentoBean
				implements RelatorioBean {

	private String prestacao;

	private String dataVencimento;

	private BigDecimal valor;

	private String idGuia;

	public String getPrestacao(){

		return prestacao;
	}

	public void setPrestacao(String prestacao){

		this.prestacao = prestacao;
	}

	public String getDataVencimento(){

		return dataVencimento;
	}

	public void setDataVencimento(String dataVencimento){

		this.dataVencimento = dataVencimento;
	}

	public BigDecimal getValor(){

		return valor;
	}

	public void setValor(BigDecimal valor){

		this.valor = valor;
	}

	public String getIdGuia(){

		return idGuia;
	}

	public void setIdGuia(String idGuia){

		this.idGuia = idGuia;
	}


}