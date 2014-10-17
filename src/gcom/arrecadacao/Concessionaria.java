
package gcom.arrecadacao;

import java.io.Serializable;

/**
 * Concessionaria
 * 
 * @author Hebert Falcão
 * @date 18/05/2012
 */
public class Concessionaria
				implements Serializable {

	private static final long serialVersionUID = 1L;

	public final static Integer EMPRESA_CONCEDENTE = 1;

	private Integer id;

	private String nome;

	private String nomeAbreviado;

	private Short indicadorEmpresaConcedente = 2;

	private Integer codigoEmpresaFebraban;

	private Integer indicadorEmpresaConcendente;

	private Short indicadorUso;

	public Concessionaria() {

	}

	public Concessionaria(Integer id) {

		this.id = id;
	}

	public Integer getId(){

		return id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public String getNome(){

		return nome;
	}

	public void setNome(String nome){

		this.nome = nome;
	}

	public String getNomeAbreviado(){

		return nomeAbreviado;
	}

	public void setNomeAbreviado(String nomeAbreviado){

		this.nomeAbreviado = nomeAbreviado;
	}

	public Short getIndicadorEmpresaConcedente(){

		return indicadorEmpresaConcedente;
	}

	public void setIndicadorEmpresaConcedente(Short indicadorEmpresaConcedente){

		this.indicadorEmpresaConcedente = indicadorEmpresaConcedente;
	}

	public Integer getCodigoEmpresaFebraban(){

		return codigoEmpresaFebraban;
	}

	public void setCodigoEmpresaFebraban(Integer codigoEmpresaFebraban){

		this.codigoEmpresaFebraban = codigoEmpresaFebraban;
	}

	public Integer getIndicadorEmpresaConcendente(){

		return indicadorEmpresaConcendente;
	}

	public void setIndicadorEmpresaConcendente(Integer indicadorEmpresaConcendente){

		this.indicadorEmpresaConcendente = indicadorEmpresaConcendente;
	}

	public Short getIndicadorUso(){

		return indicadorUso;
	}

	public void setIndicadorUso(Short indicadorUso){

		this.indicadorUso = indicadorUso;
	}

}
