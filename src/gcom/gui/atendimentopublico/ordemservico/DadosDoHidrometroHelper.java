
package gcom.gui.atendimentopublico.ordemservico;

import java.io.Serializable;

/**
 * Dados do Hidrômetro
 * 
 * @author Hebert Falcão
 * @date 12/05/2011
 */
public class DadosDoHidrometroHelper
				implements Serializable {

	private Long id;

	private Integer idHidrometroDiametro;

	private String descricaoHidrometroDiametro;

	private Integer idHidrometroCapacidade;

	private String descricaoHidrometroCapacidade;

	private String intervaloInstalacaoInicial;

	private String intervaloInstalacaoFinal;

	private Integer numeroLeituraAcumulada;

	public Long getId(){

		return id;
	}

	public void setId(Long id){

		this.id = id;
	}

	public Integer getIdHidrometroDiametro(){

		return idHidrometroDiametro;
	}

	public void setIdHidrometroDiametro(Integer idHidrometroDiametro){

		this.idHidrometroDiametro = idHidrometroDiametro;
	}

	public String getDescricaoHidrometroDiametro(){

		return descricaoHidrometroDiametro;
	}

	public void setDescricaoHidrometroDiametro(String descricaoHidrometroDiametro){

		this.descricaoHidrometroDiametro = descricaoHidrometroDiametro;
	}

	public Integer getIdHidrometroCapacidade(){

		return idHidrometroCapacidade;
	}

	public void setIdHidrometroCapacidade(Integer idHidrometroCapacidade){

		this.idHidrometroCapacidade = idHidrometroCapacidade;
	}

	public String getDescricaoHidrometroCapacidade(){

		return descricaoHidrometroCapacidade;
	}

	public void setDescricaoHidrometroCapacidade(String descricaoHidrometroCapacidade){

		this.descricaoHidrometroCapacidade = descricaoHidrometroCapacidade;
	}

	public String getIntervaloInstalacaoInicial(){

		return intervaloInstalacaoInicial;
	}

	public void setIntervaloInstalacaoInicial(String intervaloInstalacaoInicial){

		this.intervaloInstalacaoInicial = intervaloInstalacaoInicial;
	}

	public String getIntervaloInstalacaoFinal(){

		return intervaloInstalacaoFinal;
	}

	public void setIntervaloInstalacaoFinal(String intervaloInstalacaoFinal){

		this.intervaloInstalacaoFinal = intervaloInstalacaoFinal;
	}

	public Integer getNumeroLeituraAcumulada(){

		return numeroLeituraAcumulada;
	}

	public void setNumeroLeituraAcumulada(Integer numeroLeituraAcumulada){

		this.numeroLeituraAcumulada = numeroLeituraAcumulada;
	}

}
