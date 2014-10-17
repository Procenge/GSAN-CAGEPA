
package gcom.arrecadacao;

import gcom.cadastro.localidade.Localidade;

import java.io.Serializable;
import java.util.Date;

public class ConcessionariaLocalidade
				implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private Concessionaria concessionaria;

	private Localidade localidade;

	private Date dataVigenciaInicio;

	private Date dataVigenciaFim;

	private Date ultimaAlteracao;

	public ConcessionariaLocalidade() {

	}

	public Integer getId(){

		return id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public Concessionaria getConcessionaria(){

		return concessionaria;
	}

	public void setConcessionaria(Concessionaria concessionaria){

		this.concessionaria = concessionaria;
	}

	public Localidade getLocalidade(){

		return localidade;
	}

	public void setLocalidade(Localidade localidade){

		this.localidade = localidade;
	}

	public Date getDataVigenciaInicio(){

		return dataVigenciaInicio;
	}

	public void setDataVigenciaInicio(Date dataVigenciaInicio){

		this.dataVigenciaInicio = dataVigenciaInicio;
	}

	public Date getDataVigenciaFim(){

		return dataVigenciaFim;
	}

	public void setDataVigenciaFim(Date dataVigenciaFim){

		this.dataVigenciaFim = dataVigenciaFim;
	}

	public Date getUltimaAlteracao(){

		return ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

}
