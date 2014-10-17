
package gcom.arrecadacao;

import java.io.Serializable;
import java.util.Date;

/**
 * Parâmetros de acrescimos da emissão de documento
 * 
 * @author Hebert Falcão
 * @date 07/09/2012
 */
public class ParametroAcrescimosEmissaoDocumento
				implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private Date dataInicioValidade;

	private Date dataFimValidade;

	private Short indicadorJuros;

	private Short indicadorCorrecao;

	private Short indicadorMulta;

	private Date ultimaAlteracao;

	public Integer getId(){

		return id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public Date getDataInicioValidade(){

		return dataInicioValidade;
	}

	public void setDataInicioValidade(Date dataInicioValidade){

		this.dataInicioValidade = dataInicioValidade;
	}

	public Date getDataFimValidade(){

		return dataFimValidade;
	}

	public void setDataFimValidade(Date dataFimValidade){

		this.dataFimValidade = dataFimValidade;
	}

	public Short getIndicadorJuros(){

		return indicadorJuros;
	}

	public void setIndicadorJuros(Short indicadorJuros){

		this.indicadorJuros = indicadorJuros;
	}

	public Short getIndicadorCorrecao(){

		return indicadorCorrecao;
	}

	public void setIndicadorCorrecao(Short indicadorCorrecao){

		this.indicadorCorrecao = indicadorCorrecao;
	}

	public Short getIndicadorMulta(){

		return indicadorMulta;
	}

	public void setIndicadorMulta(Short indicadorMulta){

		this.indicadorMulta = indicadorMulta;
	}

	public Date getUltimaAlteracao(){

		return ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

}
