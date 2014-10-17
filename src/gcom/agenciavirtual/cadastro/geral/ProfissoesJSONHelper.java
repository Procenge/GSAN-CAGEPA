
package gcom.agenciavirtual.cadastro.geral;

import java.util.Date;

/**
 * @author Eduardo Oliveira
 */
public class ProfissoesJSONHelper {

	private Integer idProfissao;

	private Integer codigoProfissao;

	private String descricaoProfissao;

	private Short indicadorUso;

	private Date ultimaAlteracao;


	public Integer getIdProfissao(){

		return idProfissao;
	}


	public void setIdProfissao(Integer idProfissao){

		this.idProfissao = idProfissao;
	}


	public Integer getCodigoProfissao(){

		return codigoProfissao;
	}


	public void setCodigoProfissao(Integer codigoProfissao){

		this.codigoProfissao = codigoProfissao;
	}


	public String getDescricaoProfissao(){

		return descricaoProfissao;
	}


	public void setDescricaoProfissao(String descricaoProfissao){

		this.descricaoProfissao = descricaoProfissao;
	}

	public Short getIndicadorUso(){

		return indicadorUso;
	}

	public void setIndicadorUso(Short indicadorUso){

		this.indicadorUso = indicadorUso;
	}


	public Date getUltimaAlteracao(){

		return ultimaAlteracao;
	}


	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}


}
