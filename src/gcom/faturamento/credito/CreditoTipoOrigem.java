package gcom.faturamento.credito;

import java.io.Serializable;
import java.util.Date;

public class CreditoTipoOrigem
				implements Serializable {

	private Integer id;

	private CreditoTipo creditoTipo;

	private CreditoOrigem creditoOrigem;

	private Date ultimaAlteracao;

	public Integer getId(){

		return id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public CreditoTipo getCreditoTipo(){

		return creditoTipo;
	}

	public void setCreditoTipo(CreditoTipo creditoTipo){

		this.creditoTipo = creditoTipo;
	}

	public CreditoOrigem getCreditoOrigem(){

		return creditoOrigem;
	}

	public void setCreditoOrigem(CreditoOrigem creditoOrigem){

		this.creditoOrigem = creditoOrigem;
	}

	public Date getUltimaAlteracao(){

		return ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

}
