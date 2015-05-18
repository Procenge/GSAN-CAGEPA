
package gcom.cobranca.bean;

import java.io.Serializable;

/**
 * @author
 */
public class IntervaloReferenciaHelper
				implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private String referenciaInicial;

	private String referenciaFinal;

	private String valorTotalContas;



	public IntervaloReferenciaHelper() {

	}

	public String getReferenciaInicial(){

		return referenciaInicial;
	}


	public void setReferenciaInicial(String referenciaInicial){

		this.referenciaInicial = referenciaInicial;
	}


	public String getReferenciaFinal(){

		return referenciaFinal;
	}


	public void setReferenciaFinal(String referenciaFinal){

		this.referenciaFinal = referenciaFinal;
	}


	public Integer getId(){

		return id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public String getValorTotalContas(){

		return valorTotalContas;
	}

	public void setValorTotalContas(String valorTotalContas){

		this.valorTotalContas = valorTotalContas;
	}

}
