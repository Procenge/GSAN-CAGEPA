
package gcom.integracao.cagepa.faturamento;

import gcom.interceptor.ObjetoGcom;

import org.apache.commons.lang.builder.ToStringBuilder;

public class MetLigacaoPK
				extends ObjetoGcom {

	private static final long serialVersionUID = 1L;

	/** identifier field */
	private Integer mesFatura;

	/** identifier field */
	private Integer anoFatura;

	/** identifier field */
	private Integer ligacaoId;

	/**
	 * @param mesFatura
	 * @param anoFatura
	 * @param ligacaoId
	 */
	public MetLigacaoPK(Integer mesFatura, Integer anoFatura, Integer ligacaoId) {

		this.mesFatura = mesFatura;
		this.anoFatura = anoFatura;
		this.ligacaoId = ligacaoId;
	}

	/**
	 * Default Constructor
	 */
	public MetLigacaoPK() {

	}

	@Override
	public String[] retornaCamposChavePrimaria(){

		String[] retorno = new String[2];
		retorno[0] = "mesFatura";
		retorno[1] = "anoFatura";
		retorno[2] = "ligacaoId";
		return retorno;
	}

	public Integer getMesFatura(){

		return mesFatura;
	}

	public void setMesFatura(Integer mesFatura){

		this.mesFatura = mesFatura;
	}

	public Integer getAnoFatura(){

		return anoFatura;
	}

	public void setAnoFatura(Integer anoFatura){

		this.anoFatura = anoFatura;
	}

	public Integer getLigacaoId(){

		return ligacaoId;
	}

	public void setLigacaoId(Integer ligacaoId){

		this.ligacaoId = ligacaoId;
	}

	@Override
	public int hashCode(){

		final int prime = 31;
		int result = 1;
		result = prime * result + ((mesFatura == null) ? 0 : mesFatura.hashCode());
		result = prime * result + ((anoFatura == null) ? 0 : anoFatura.hashCode());
		result = prime * result + ((ligacaoId == null) ? 0 : ligacaoId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj){

		if(this == obj) return true;
		if(obj == null) return false;
		if(getClass() != obj.getClass()) return false;
		MetLigacaoPK other = (MetLigacaoPK) obj;
		if(mesFatura == null){
			if(other.mesFatura != null) return false;
		}else if(!mesFatura.equals(other.mesFatura)) return false;
		if(anoFatura == null){
			if(other.anoFatura != null) return false;
		}else if(!anoFatura.equals(other.anoFatura)) return false;
		if(ligacaoId == null){
			if(other.ligacaoId != null) return false;
		}else if(!ligacaoId.equals(other.ligacaoId)) return false;
		return true;
	}

	@Override
	public String toString(){

		return new ToStringBuilder(this).append("mesFatura", getMesFatura()).append("anoFatura", getAnoFatura())
						.append("ligacaoId", getLigacaoId()).toString();
	}

}
