
package gcom.contabil.bean;

import gcom.contabil.LancamentoContabilSintetico;

import java.math.BigDecimal;

public class LancamentoContabilAnaliticoHelper {

	private BigDecimal valor;

	private Long codigoObjeto;

	private LancamentoContabilSintetico lancamentoContabilSintetico;

	public LancamentoContabilAnaliticoHelper(BigDecimal valor, Long codigoObjeto) {

		super();
		this.valor = valor;
		this.codigoObjeto = codigoObjeto;
	}

	public void setValor(BigDecimal valor){

		this.valor = valor;
	}

	public BigDecimal getValor(){

		return valor;
	}

	public void setCodigoObjeto(Long codigoObjeto){

		this.codigoObjeto = codigoObjeto;
	}

	public Long getCodigoObjeto(){

		return codigoObjeto;
	}

	public void setLancamentoContabilSintetico(LancamentoContabilSintetico lancamentoContabilSintetico){

		this.lancamentoContabilSintetico = lancamentoContabilSintetico;
	}

	public LancamentoContabilSintetico getLancamentoContabilSintetico(){

		return lancamentoContabilSintetico;
	}
}
