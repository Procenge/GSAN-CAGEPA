
package gcom.cobranca;

import gcom.interceptor.ObjetoTransacao;
import gcom.util.filtro.Filtro;

import java.util.Date;

public class OrgaoExterno
				extends ObjetoTransacao {

	private Integer id;

	private String descricao;

	private Integer indicadorUso;

	private Date ultimaAlteracao;

	public OrgaoExterno() {

		super();
	}

	public OrgaoExterno(Integer id) {

		this.id = id;
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

	public Integer getIndicadorUso(){

		return indicadorUso;
	}

	public void setIndicadorUso(Integer indicadorUso){

		this.indicadorUso = indicadorUso;
	}

	public Date getUltimaAlteracao(){

		return ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	@Override
	public Filtro retornaFiltro(){

		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] retornaCamposChavePrimaria(){

		// TODO Auto-generated method stub
		return null;
	}
}
