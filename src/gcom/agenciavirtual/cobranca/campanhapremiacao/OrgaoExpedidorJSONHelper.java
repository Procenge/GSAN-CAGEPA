
package gcom.agenciavirtual.cobranca.campanhapremiacao;

import java.util.Date;

/**
 * @author @ Hiroshi Goncalves, Anderson Italo
 */
public class OrgaoExpedidorJSONHelper {

	private Integer id;

	private String descricao;

	private String descricaoAbreviada;

	private Short indicadorUso;

	private Date dataUltimaAlteracao;

	public Integer getId(){

		return id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public String getDescricao(){

		return descricao;
	}

	public void setDescricao(String descricao){

		this.descricao = descricao;
	}

	public String getDescricaoAbreviada(){

		return descricaoAbreviada;
	}

	public void setDescricaoAbreviada(String descricaoAbreviada){

		this.descricaoAbreviada = descricaoAbreviada;
	}

	public Short getIndicadorUso(){

		return indicadorUso;
	}

	public void setIndicadorUso(Short indicadorUso){

		this.indicadorUso = indicadorUso;
	}

	public Date getDataUltimaAlteracao(){

		return dataUltimaAlteracao;
	}

	public void setDataUltimaAlteracao(Date dataUltimaAlteracao){

		this.dataUltimaAlteracao = dataUltimaAlteracao;
	}


}