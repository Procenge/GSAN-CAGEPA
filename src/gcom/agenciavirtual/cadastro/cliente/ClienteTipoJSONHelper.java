
package gcom.agenciavirtual.cadastro.cliente;

import java.util.Date;

public class ClienteTipoJSONHelper {


	private Integer id;

	private String descricao;

	private Short indicadorPessoaFisicaJuridica;

	private Short indicadorUso;

	private gcom.cadastro.cliente.EsferaPoder esferaPoder;

	private Date dataUltimaAlteracao;

	public ClienteTipoJSONHelper() {

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
	
	public Short getIndicadorPessoaFisicaJuridica(){

		return indicadorPessoaFisicaJuridica;
	}

	public void setIndicadorPessoaFisicaJuridica(Short indicadorPessoaFisicaJuridica){

		this.indicadorPessoaFisicaJuridica = indicadorPessoaFisicaJuridica;
	}

	public Short getIndicadorUso(){

		return indicadorUso;
	}

	public void setIndicadorUso(Short indicadorUso){

		this.indicadorUso = indicadorUso;
	}

	public gcom.cadastro.cliente.EsferaPoder getEsferaPoder(){

		return esferaPoder;
	}

	public void setEsferaPoder(gcom.cadastro.cliente.EsferaPoder esferaPoder){

		this.esferaPoder = esferaPoder;
	}

	public Date getDataUltimaAlteracao(){

		return dataUltimaAlteracao;
	}

	public void setDataUltimaAlteracao(Date dataUltimaAlteracao){

		this.dataUltimaAlteracao = dataUltimaAlteracao;
	}



}
