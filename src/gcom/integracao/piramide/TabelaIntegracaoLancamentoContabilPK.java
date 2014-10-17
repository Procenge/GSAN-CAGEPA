package gcom.integracao.piramide;

import gcom.interceptor.ObjetoGcom;

import org.apache.commons.lang.builder.ToStringBuilder;

public class TabelaIntegracaoLancamentoContabilPK
				extends ObjetoGcom {

	private static final long serialVersionUID = 1L;

	/** identifier field */
	private Integer numeroIdLancamento;

	/** identifier field */
	private Integer numeroSequencial;

	/**
	 * @param numeroIdLancamento
	 * @param numeroSequencial
	 */
	public TabelaIntegracaoLancamentoContabilPK(Integer numeroIdLancamento, Integer numeroSequencial) {

		this.numeroIdLancamento = numeroIdLancamento;
		this.numeroSequencial = numeroSequencial;
	}

	/**
	 * Default Constructor
	 */
	public TabelaIntegracaoLancamentoContabilPK() {

	}

	@Override
	public String[] retornaCamposChavePrimaria(){

		String[] retorno = new String[2];
		retorno[0] = "numeroIdLancamento";
		retorno[1] = "numeroSequencial";
		return retorno;
	}

	public Integer getNumeroIdLancamento(){

		return numeroIdLancamento;
	}

	public void setNumeroIdLancamento(Integer numeroIdLancamento){

		this.numeroIdLancamento = numeroIdLancamento;
	}

	public Integer getNumeroSequencial(){

		return numeroSequencial;
	}

	public void setNumeroSequencial(Integer numeroSequencial){

		this.numeroSequencial = numeroSequencial;
	}

	@Override
	public int hashCode(){

		final int prime = 31;
		int result = 1;
		result = prime * result + ((numeroIdLancamento == null) ? 0 : numeroIdLancamento.hashCode());
		result = prime * result + ((numeroSequencial == null) ? 0 : numeroSequencial.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj){

		if(this == obj) return true;
		if(obj == null) return false;
		if(getClass() != obj.getClass()) return false;
		TabelaIntegracaoLancamentoContabilPK other = (TabelaIntegracaoLancamentoContabilPK) obj;
		if(numeroIdLancamento == null){
			if(other.numeroIdLancamento != null) return false;
		}else if(!numeroIdLancamento.equals(other.numeroIdLancamento)) return false;
		if(numeroSequencial == null){
			if(other.numeroSequencial != null) return false;
		}else if(!numeroSequencial.equals(other.numeroSequencial)) return false;
		return true;
	}

	@Override
	public String toString(){

		return new ToStringBuilder(this).append("numeroIdLancamento", getNumeroIdLancamento())
						.append("numeroSequencial", getNumeroSequencial()).toString();
	}


}
