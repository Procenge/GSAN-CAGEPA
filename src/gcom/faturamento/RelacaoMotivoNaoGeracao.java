
package gcom.faturamento;

import java.io.Serializable;
import java.util.Date;

public class RelacaoMotivoNaoGeracao
				implements Serializable {

	private Integer id;

	private Integer idImovel;

	private String motivo;

	private Date ultimaAlteracao;

	public Integer getId(){

		return id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public Integer getIdImovel(){

		return idImovel;
	}

	public void setIdImovel(Integer idImovel){

		this.idImovel = idImovel;
	}

	public String getMotivo(){

		return motivo;
	}

	public void setMotivo(String motivo){

		this.motivo = motivo;
	}

	public Date getUltimaAlteracao(){

		return ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

}
