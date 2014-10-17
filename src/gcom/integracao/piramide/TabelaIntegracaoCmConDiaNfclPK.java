package gcom.integracao.piramide;

import java.io.Serializable;

public class TabelaIntegracaoCmConDiaNfclPK
				implements Serializable {

	private Integer codigoSequencialOrigem;

	private Integer codigoSequencialItemOrigem;

	public TabelaIntegracaoCmConDiaNfclPK(Integer codigoSequencialOrigem, Integer codigoSequencialItemOrigem) {

		this.codigoSequencialOrigem = codigoSequencialOrigem;
		this.codigoSequencialItemOrigem = codigoSequencialItemOrigem;
	}

	public TabelaIntegracaoCmConDiaNfclPK() {

	}

	public String[] retornaCamposChavePrimaria(){

		String[] retorno = new String[2];
		retorno[0] = "codigoSequencialOrigem";
		retorno[1] = "codigoSequencialItemOrigem";

		return retorno;
	}

	public Integer getCodigoSequencialOrigem(){

		return codigoSequencialOrigem;
	}

	public void setCodigoSequencialOrigem(Integer codigoSequencialOrigem){

		this.codigoSequencialOrigem = codigoSequencialOrigem;
	}

	public Integer getCodigoSequencialItemOrigem(){

		return codigoSequencialItemOrigem;
	}

	public void setCodigoSequencialItemOrigem(Integer codigoSequencialItemOrigem){

		this.codigoSequencialItemOrigem = codigoSequencialItemOrigem;
	}

	@Override
	public int hashCode(){

		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigoSequencialItemOrigem == null) ? 0 : codigoSequencialItemOrigem.hashCode());
		result = prime * result + ((codigoSequencialOrigem == null) ? 0 : codigoSequencialOrigem.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj){

		if(this == obj) return true;
		if(obj == null) return false;
		if(getClass() != obj.getClass()) return false;
		TabelaIntegracaoCmConDiaNfclPK other = (TabelaIntegracaoCmConDiaNfclPK) obj;
		if(codigoSequencialItemOrigem == null){
			if(other.codigoSequencialItemOrigem != null) return false;
		}else if(!codigoSequencialItemOrigem.equals(other.codigoSequencialItemOrigem)) return false;
		if(codigoSequencialOrigem == null){
			if(other.codigoSequencialOrigem != null) return false;
		}else if(!codigoSequencialOrigem.equals(other.codigoSequencialOrigem)) return false;
		return true;
	}

	@Override
	public String toString(){

		return "TI_CM_CON_DIA_NFCL_PK [codigoSequencialOrigem=" + codigoSequencialOrigem + ", codigoSequencialItemOrigem="
						+ codigoSequencialItemOrigem + "]";
	}

}
