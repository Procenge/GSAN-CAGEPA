package gcom.integracao.piramide;

import gcom.interceptor.ObjetoGcom;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

public class TabelaIntegracaoApuCsocRetidaPK
				extends ObjetoGcom {

	private static final long serialVersionUID = 1L;

	/** identifier field */
	private String codigoFilialOrigem;

	/** identifier field */
	private String codigoSistemaOrigem;

	/** identifier field */
	private String codigoImposto;

	/** identifier field */
	private String codigoCsocialApurado;

	/** identifier field */
	private String codigoCnpj;

	/** identifier field */
	private Date dataRetencao;

	/**
	 * @param codigoFilialOrigem
	 * @param codigoSistemaOrigem
	 * @param codigoImposto
	 * @param codigoCsocialApurado
	 * @param codigoCnpj
	 * @param dataRetencao
	 */
	public TabelaIntegracaoApuCsocRetidaPK(String codigoFilialOrigem, String codigoSistemaOrigem, String codigoImposto,
											String codigoCsocialApurado, String codigoCnpj, Date dataRetencao) {
		this.codigoFilialOrigem = codigoFilialOrigem;
		this.codigoSistemaOrigem = codigoSistemaOrigem;
		this.codigoImposto = codigoImposto;
		this.codigoCsocialApurado = codigoCsocialApurado;
		this.codigoCnpj = codigoCnpj;
		this.dataRetencao = dataRetencao;
	}

	/**
	 * Default Constructor
	 */
	public TabelaIntegracaoApuCsocRetidaPK() {

	}

	@Override
	public String[] retornaCamposChavePrimaria(){

		String[] retorno = new String[6];
		retorno[0] = "codigoFilialOrigem";
		retorno[1] = "codigoSistemaOrigem";
		retorno[2] = "codigoImposto";
		retorno[3] = "codigoCsocialApurado";
		retorno[4] = "codigoCnpj";
		retorno[5] = "dataRetencao";
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

	public String getCodigoImposto(){

		return codigoImposto;
	}

	public void setCodigoImposto(String codigoImposto){

		this.codigoImposto = codigoImposto;
	}

	public String getCodigoCsocialApurado(){

		return codigoCsocialApurado;
	}

	public void setCodigoCsocialApurado(String codigoCsocialApurado){

		this.codigoCsocialApurado = codigoCsocialApurado;
	}

	public String getCodigoCnpj(){

		return codigoCnpj;
	}

	public void setCodigoCnpj(String codigoCnpj){

		this.codigoCnpj = codigoCnpj;
	}

	public Date getDataRetencao(){

		return dataRetencao;
	}

	public void setDataRetencao(Date dataRetencao){

		this.dataRetencao = dataRetencao;
	}


	@Override
	public int hashCode(){

		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigoCnpj == null) ? 0 : codigoCnpj.hashCode());
		result = prime * result + ((codigoCsocialApurado == null) ? 0 : codigoCsocialApurado.hashCode());
		result = prime * result + ((codigoFilialOrigem == null) ? 0 : codigoFilialOrigem.hashCode());
		result = prime * result + ((codigoImposto == null) ? 0 : codigoImposto.hashCode());
		result = prime * result + ((codigoSistemaOrigem == null) ? 0 : codigoSistemaOrigem.hashCode());
		result = prime * result + ((dataRetencao == null) ? 0 : dataRetencao.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj){

		if(this == obj) return true;
		if(obj == null) return false;
		if(getClass() != obj.getClass()) return false;
		TabelaIntegracaoApuCsocRetidaPK other = (TabelaIntegracaoApuCsocRetidaPK) obj;
		if(codigoCnpj == null){
			if(other.codigoCnpj != null) return false;
		}else if(!codigoCnpj.equals(other.codigoCnpj)) return false;
		if(codigoCsocialApurado == null){
			if(other.codigoCsocialApurado != null) return false;
		}else if(!codigoCsocialApurado.equals(other.codigoCsocialApurado)) return false;
		if(codigoFilialOrigem == null){
			if(other.codigoFilialOrigem != null) return false;
		}else if(!codigoFilialOrigem.equals(other.codigoFilialOrigem)) return false;
		if(codigoImposto == null){
			if(other.codigoImposto != null) return false;
		}else if(!codigoImposto.equals(other.codigoImposto)) return false;
		if(codigoSistemaOrigem == null){
			if(other.codigoSistemaOrigem != null) return false;
		}else if(!codigoSistemaOrigem.equals(other.codigoSistemaOrigem)) return false;
		if(dataRetencao == null){
			if(other.dataRetencao != null) return false;
		}else if(!dataRetencao.equals(other.dataRetencao)) return false;
		return true;
	}

	@Override
	public String toString(){

		return new ToStringBuilder(this).append("codigoFilialOrigem", getCodigoFilialOrigem())
						.append("codigoSistemaOrigem", getCodigoSistemaOrigem()).append("codigoImposto", getCodigoImposto())
						.append("codigoCsocialApurado", getCodigoCsocialApurado()).append("codigoCnpj", getCodigoCnpj())
						.append("dataRetencao", getDataRetencao()).toString();
	}


}
