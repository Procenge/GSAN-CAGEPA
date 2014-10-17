
package gcom.financeiro.lancamento;

import java.io.Serializable;

public class AjusteContabilidade
				implements Serializable {

	private Integer id;

	private Integer idPagamento;

	private Integer icProcessado;

	public Integer getId(){

		return id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public Integer getIdPagamento(){

		return idPagamento;
	}

	public void setIdPagamento(Integer idPagamento){

		this.idPagamento = idPagamento;
	}

	public Integer getIcProcessado(){

		return icProcessado;
	}

	public void setIcProcessado(Integer icProcessado){

		this.icProcessado = icProcessado;
	}

}
