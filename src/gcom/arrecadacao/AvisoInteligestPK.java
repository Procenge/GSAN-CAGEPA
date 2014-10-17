
package gcom.arrecadacao;

import java.io.Serializable;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class AvisoInteligestPK
				implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer ano; // Ano de Emissão

	private Integer numeroAviso; // Id do Documento Legado (ADA)

	private Integer codigoTributo; // Tipo Documento

	public AvisoInteligestPK() {

	}

	public AvisoInteligestPK(Integer ano, Integer numeroAviso, Integer codigoTributo) {

		this.ano = ano;
		this.numeroAviso = numeroAviso;
		this.codigoTributo = codigoTributo;
	}

	// @Override
	// public String[] retornaCamposChavePrimaria() {
	// String[] retorno = new String[3];
	// retorno[0] = "ano";
	// retorno[1] = "numeroAviso";
	// retorno[2] = "codigoTributo";
	// return retorno;
	// }

	@Override
	public int hashCode(){

		return new HashCodeBuilder().append(getAno()).append(getNumeroAviso()).append(getCodigoTributo()).toHashCode();
	}

	@Override
	public boolean equals(Object obj){

		if(this == obj) return true;

		if(obj == null) return false;

		if(!(obj instanceof AvisoInteligestPK)) return false;

		final AvisoInteligestPK other = (AvisoInteligestPK) obj;
		if(ano == null){
			if(other.ano != null) return false;
		}else if(!ano.equals(other.ano)) return false;
		if(numeroAviso == null){
			if(other.numeroAviso != null) return false;
		}else if(!numeroAviso.equals(other.numeroAviso)) return false;
		if(codigoTributo == null){
			if(other.codigoTributo != null) return false;
		}else if(!codigoTributo.equals(other.codigoTributo)) return false;

		return true;
	}

	@Override
	public String toString(){

		return new ToStringBuilder(this).append("ano", getAno()).append("numeroAviso", getNumeroAviso()).append("codigoTributo",
						getCodigoTributo()).toString();
	}

	public Integer getAno(){

		return ano;
	}

	public void setAno(Integer ano){

		this.ano = ano;
	}

	public Integer getNumeroAviso(){

		return numeroAviso;
	}

	public void setNumeroAviso(Integer numeroAviso){

		this.numeroAviso = numeroAviso;
	}

	public Integer getCodigoTributo(){

		return codigoTributo;
	}

	public void setCodigoTributo(Integer codigoTributo){

		this.codigoTributo = codigoTributo;
	}
}
