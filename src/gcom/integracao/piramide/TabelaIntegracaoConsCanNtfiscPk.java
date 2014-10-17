package gcom.integracao.piramide;

import gcom.util.Util;

import java.io.Serializable;
import java.util.Date;


public class TabelaIntegracaoConsCanNtfiscPk
				implements Serializable {

	private String codigoFilialOrigem;

	private String codigoSistemaOrigem;

	private Date dataCancelamento;

	public TabelaIntegracaoConsCanNtfiscPk(String codigoFilialOrigem, String codigoSistemaOrigem, Date dataCancelamento) {

		super();
		this.codigoFilialOrigem = codigoFilialOrigem;
		this.codigoSistemaOrigem = codigoSistemaOrigem;
		this.dataCancelamento = dataCancelamento;
	}

	public TabelaIntegracaoConsCanNtfiscPk() {

	}

	public String[] retornaCamposChavePrimaria(){

		String[] retorno = new String[3];
		retorno[0] = "codigoFilialOrigem";
		retorno[1] = "codigoSistemaOrigem";
		retorno[2] = "dataCancelamento";
		return retorno;
	}

	public String getCodigoFilialOrigem(){

		return codigoFilialOrigem;
	}

	public void setCodigoFilialOrigem(String codigoFilialOrigem){

		this.codigoFilialOrigem = codigoFilialOrigem;
	}

	public String getCodigoSistemaOrigem(){

		return codigoSistemaOrigem;
	}

	public void setCodigoSistemaOrigem(String codigoSistemaOrigem){

		this.codigoSistemaOrigem = codigoSistemaOrigem;
	}

	public Date getDataCancelamento(){

		return dataCancelamento;
	}

	public void setDataCancelamento(Date dataCancelamento){

		this.dataCancelamento = dataCancelamento;
	}

	@Override
	public int hashCode(){

		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigoFilialOrigem == null) ? 0 : codigoFilialOrigem.hashCode());
		result = prime * result + ((codigoSistemaOrigem == null) ? 0 : codigoSistemaOrigem.hashCode());
		result = prime * result + ((dataCancelamento == null) ? 0 : dataCancelamento.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj){

		if(this == obj) return true;
		if(obj == null) return false;
		if(getClass() != obj.getClass()) return false;
		TabelaIntegracaoConsCanNtfiscPk other = (TabelaIntegracaoConsCanNtfiscPk) obj;
		if(codigoFilialOrigem == null){
			if(other.codigoFilialOrigem != null) return false;
		}else if(!codigoFilialOrigem.equals(other.codigoFilialOrigem)) return false;
		if(codigoSistemaOrigem == null){
			if(other.codigoSistemaOrigem != null) return false;
		}else if(!codigoSistemaOrigem.equals(other.codigoSistemaOrigem)) return false;
		if(dataCancelamento == null){
			if(other.dataCancelamento != null) return false;
		}else if(!dataCancelamento.equals(other.dataCancelamento)) return false;
		return true;
	}

	@Override
	public String toString(){

		return "TI_CONS_CAN_NTFISC_PK [codigoFilialOrigem=" + codigoFilialOrigem + ", codigoSistemaOrigem=" + codigoSistemaOrigem
						+ ", dataCancelamento=" + Util.formatarData(dataCancelamento) + "]";
	}

}
