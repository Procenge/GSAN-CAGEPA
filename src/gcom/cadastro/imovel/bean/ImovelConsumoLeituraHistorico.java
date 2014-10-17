/**
 * 
 */

package gcom.cadastro.imovel.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * @author mgrb
 */
public class ImovelConsumoLeituraHistorico
				implements Serializable {

	private String referencia;

	private Date dataLeituraAnterior;

	private Date dataLeituraAtual;

	private Integer consumo;

	private Integer numeroLeitura;

	public Date getDataLeituraAnterior(){

		return dataLeituraAnterior;
	}

	public void setDataLeituraAnterior(Date dataLeituraAnterior){

		this.dataLeituraAnterior = dataLeituraAnterior;
	}

	public Date getDataLeituraAtual(){

		return dataLeituraAtual;
	}

	public void setDataLeituraAtual(Date dataLeituraAtual){

		this.dataLeituraAtual = dataLeituraAtual;
	}

	public String getReferencia(){

		return referencia;
	}

	public void setReferencia(String referencia){

		this.referencia = referencia;
	}

	public Integer getConsumo(){

		return consumo;
	}

	public void setConsumo(Integer consumo){

		this.consumo = consumo;
	}

	public Integer getNumeroLeitura(){

		return numeroLeitura;
	}

	public void setNumeroLeitura(Integer numeroLeitura){

		this.numeroLeitura = numeroLeitura;
	}
}
