
package gcom.arrecadacao.bean;

import gcom.contabil.OperacaoContabil;

public class OperacaoContabilHelper {

	private OperacaoContabil operacaoContabil;

	private Object objetoOrigem;

	public OperacaoContabil getOperacaoContabil(){

		return operacaoContabil;
	}

	public void setOperacaoContabil(OperacaoContabil operacaoContabil){

		this.operacaoContabil = operacaoContabil;
	}

	public void setObjetoOrigem(Object objetoOrigem){

		this.objetoOrigem = objetoOrigem;
	}

	public Object getObjetoOrigem(){

		return objetoOrigem;
	}

}
