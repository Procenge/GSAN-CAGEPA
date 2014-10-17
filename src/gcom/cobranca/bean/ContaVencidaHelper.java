
package gcom.cobranca.bean;

import java.math.BigDecimal;

public class ContaVencidaHelper {

	private String referencia;

	private BigDecimal valor;

	private String vencimento;

	public String getReferencia(){

		return referencia;
	}

	public void setReferencia(String referencia){

		this.referencia = referencia;
	}

	public BigDecimal getValor(){

		return valor;
	}

	public void setValor(BigDecimal valor){

		this.valor = valor;
	}

	public String getVencimento(){

		return vencimento;
	}

	public void setVencimento(String vencimento){

		this.vencimento = vencimento;
	}

	@Override
	public boolean equals(Object obj){

		try{
			ContaVencidaHelper contaVencidaHelper = (ContaVencidaHelper) obj;
			if((contaVencidaHelper.getReferencia().equals(this.referencia)) && (contaVencidaHelper.getValor().equals(this.valor))
							&& (contaVencidaHelper.getVencimento().equals(this.vencimento))){
				return true;
			}else{
				return false;
			}
		}catch(ClassCastException ex){
			return false;
		}
	}

}
