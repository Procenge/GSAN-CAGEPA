
package gcom.cadastro.cliente;

import gcom.interceptor.ControleAlteracao;
import gcom.interceptor.ObjetoTransacao;
import gcom.util.filtro.Filtro;
import gcom.util.filtro.ParametroSimples;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author Bruno Ferreira dos Santos
 */
@ControleAlteracao()
public class ClienteTipoEspecial
				extends ObjetoTransacao {

	private static final long serialVersionUID = 1L;

	/**
	 * identifier field
	 */
	private Integer id;

	/**
	 * nullable persistent field
	 */
	private String descricao;

	/**
	 * nullable persistent field
	 */
	private String nomeAbreviado;

	/**
	 * nullable persistent field
	 */
	private Date ultimaAlteracao;

	/**
	 * 
	 */
	public ClienteTipoEspecial() {

	}

	/**
	 * @param id
	 * @param descricao
	 * @param nomeAbreviado
	 */
	public ClienteTipoEspecial(Integer id, String descricao, String nomeAbreviado, Date ultimaAlteracao) {

		super();
		this.id = id;
		this.descricao = descricao;
		this.nomeAbreviado = nomeAbreviado;
		this.ultimaAlteracao = ultimaAlteracao;
	}

	public Date getUltimaAlteracao(){

		return this.ultimaAlteracao;
	}

	public Filtro retornaFiltro(){

		FiltroClienteTipoEspecial filtroClienteTipo = new FiltroClienteTipoEspecial();

		filtroClienteTipo.adicionarParametro(new ParametroSimples(FiltroClienteTipoEspecial.ID, this.getId()));

		return filtroClienteTipo;
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

	public String getNomeAbreviado(){

		return nomeAbreviado;
	}

	public void setNomeAbreviado(String nomeAbreviado){

		this.nomeAbreviado = nomeAbreviado;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;

	}

	public String[] retornaCamposChavePrimaria(){

		String[] retorno = {"id"};
		return retorno;
	}

	public String toString(){

		return new ToStringBuilder(this).append("id", getId()).toString();
	}

	@Override
	public String getDescricaoParaRegistroTransacao(){

		return getId() + " " + getDescricao();
	}

}
