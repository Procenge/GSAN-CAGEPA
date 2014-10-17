
package gcom.atendimentopublico.ligacaoagua;

import gcom.interceptor.ObjetoGcom;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author Hugo Lima
 */
public class CorteRegistroTipo
				extends ObjetoGcom {

	private static final long serialVersionUID = 1L;

	private Integer id = 1;

	private String descricao;

	private String descricaoAbreviada;

	private Date ultimaAlteracao;

	public final static Integer LIQ_NORMAL = 1;

	public final static Integer REG_MAGNET = 2;

	public final static Integer TUB_MAGNET = 3;

	public CorteRegistroTipo() {

	}

	public CorteRegistroTipo(Integer id, String descricao, String descricaoAbreviada, Date ultimaAlteracao) {

		this.id = id;
		this.descricao = descricao;
		this.descricaoAbreviada = descricaoAbreviada;
		this.ultimaAlteracao = ultimaAlteracao;
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

	public Date getUltimaAlteracao(){

		return ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public String toString(){

		return new ToStringBuilder(this).append("id", getId()).toString();
	}

	public String[] retornaCamposChavePrimaria(){

		String[] retorno = new String[1];
		retorno[0] = "id";
		return retorno;
	}
}
