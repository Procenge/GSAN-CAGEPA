
package gcom.arrecadacao;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class AvisoInteligest
				implements Serializable {

	private static final long serialVersionUID = 1L;

	private gcom.arrecadacao.AvisoInteligestPK comp_id; // Ano de Emissão + Id do Documento Legado +

	// Tipo Documento

	private Integer numeroLigacao; // Imovel

	private Integer numeroSequencial;// Id Documento GSAN

	private Integer numeroEmissao; // Número da pestação no caso de guia de parcelamento

	public AvisoInteligest() {

	}

	public AvisoInteligest(AvisoInteligestPK comp_id, Integer numeroLigacao, Integer numeroSequencial) {

		super();
		this.comp_id = comp_id;
		this.numeroLigacao = numeroLigacao;
		this.numeroSequencial = numeroSequencial;
	}

	public String toString(){

		return new ToStringBuilder(this).append("comp_id", getComp_id()).toString();
	}

	public boolean equals(Object other){

		if((this == other)) return true;
		if(!(other instanceof AvisoInteligest)) return false;
		AvisoInteligest castOther = (AvisoInteligest) other;
		return new EqualsBuilder().append(this.getComp_id(), castOther.getComp_id()).isEquals();
	}

	public int hashCode(){

		return new HashCodeBuilder().append(getComp_id()).toHashCode();
	}

	public gcom.arrecadacao.AvisoInteligestPK getComp_id(){

		return comp_id;
	}

	public void setComp_id(gcom.arrecadacao.AvisoInteligestPK comp_id){

		this.comp_id = comp_id;
	}

	public Integer getNumeroLigacao(){

		return numeroLigacao;
	}

	public void setNumeroLigacao(Integer numeroLigacao){

		this.numeroLigacao = numeroLigacao;
	}

	public Integer getNumeroSequencial(){

		return numeroSequencial;
	}

	public void setNumeroSequencial(Integer numeroSequencial){

		this.numeroSequencial = numeroSequencial;
	}

	public Integer getNumeroEmissao(){

		return numeroEmissao;
	}

	public void setNumeroEmissao(Integer numeroEmissao){

		this.numeroEmissao = numeroEmissao;
	}

}
