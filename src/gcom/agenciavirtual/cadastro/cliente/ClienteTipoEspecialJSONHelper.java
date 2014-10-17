
package gcom.agenciavirtual.cadastro.cliente;

import java.util.Date;

public class ClienteTipoEspecialJSONHelper {


	private Integer id;

	private String descricao;

	private String descricaoAbreviada;

	private Date dataUltimaAlteracao;

	public ClienteTipoEspecialJSONHelper() {

	}

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

	public Date getDataUltimaAlteracao(){

		return dataUltimaAlteracao;
	}

	public void setDataUltimaAlteracao(Date dataUltimaAlteracao){

		this.dataUltimaAlteracao = dataUltimaAlteracao;
	}



}
