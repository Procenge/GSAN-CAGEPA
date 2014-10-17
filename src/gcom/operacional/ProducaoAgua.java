
package gcom.operacional;

import gcom.cadastro.localidade.Localidade;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class ProducaoAgua
				implements Serializable {

	/** identifier field */
	private Integer id;

	/** persistent field */
	private int anoMesReferencia;

	/** persistent field */
	private BigDecimal volumeProduzido;

	/** persistent field */
	private Date ultimaAlteracao;

	/** persistent field */
	private Localidade localidade;

	/** full constructor */
	public ProducaoAgua(Integer id, int anoMesReferencia, BigDecimal volumeProduzido, Date ultimaAlteracao, Localidade localidade) {

		this.id = id;
		this.anoMesReferencia = anoMesReferencia;
		this.volumeProduzido = volumeProduzido;
		this.ultimaAlteracao = ultimaAlteracao;
		this.localidade = localidade;
	}

	/** default constructor */
	public ProducaoAgua() {

	}

	public Integer getId(){

		return this.id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public int getAnoMesReferencia(){

		return this.anoMesReferencia;
	}

	public void setAnoMesReferencia(int anoMesReferencia){

		this.anoMesReferencia = anoMesReferencia;
	}

	public BigDecimal getVolumeProduzido(){

		return this.volumeProduzido;
	}

	public void setVolumeProduzido(BigDecimal volumeProduzido){

		this.volumeProduzido = volumeProduzido;
	}

	public Date getUltimaAlteracao(){

		return this.ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public Localidade getLocalidade(){

		return this.localidade;
	}

	public void setLocalidade(Localidade localidade){

		this.localidade = localidade;
	}

	public String toString(){

		return new ToStringBuilder(this).append("id", getId()).toString();
	}

}
