
package gcom.gui.atendimentopublico.ordemservico;

import org.apache.struts.validator.ValidatorActionForm;

/**
 * Dados do Hidrômetro
 * 
 * @author Hebert Falcão
 * @date 12/05/2011
 */
public class DadosDoHidrometroPopupActionForm
				extends ValidatorActionForm {

	private static final long serialVersionUID = 1L;

	private String idHidrometroDiametro;

	private String descricaoHidrometroDiametro;

	private String idHidrometroCapacidade;

	private String descricaoHidrometroCapacidade;

	private String intervaloInstalacaoInicial;

	private String intervaloInstalacaoFinal;

	private String numeroLeituraAcumulada;

	public String getIdHidrometroDiametro(){

		return idHidrometroDiametro;
	}

	public void setIdHidrometroDiametro(String idHidrometroDiametro){

		this.idHidrometroDiametro = idHidrometroDiametro;
	}

	public String getIdHidrometroCapacidade(){

		return idHidrometroCapacidade;
	}

	public void setIdHidrometroCapacidade(String idHidrometroCapacidade){

		this.idHidrometroCapacidade = idHidrometroCapacidade;
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

	public String getNumeroLeituraAcumulada(){

		return numeroLeituraAcumulada;
	}

	public void setNumeroLeituraAcumulada(String numeroLeituraAcumulada){

		this.numeroLeituraAcumulada = numeroLeituraAcumulada;
	}

	public String getDescricaoHidrometroDiametro(){

		return descricaoHidrometroDiametro;
	}

	public void setDescricaoHidrometroDiametro(String descricaoHidrometroDiametro){

		this.descricaoHidrometroDiametro = descricaoHidrometroDiametro;
	}

	public String getDescricaoHidrometroCapacidade(){

		return descricaoHidrometroCapacidade;
	}

	public void setDescricaoHidrometroCapacidade(String descricaoHidrometroCapacidade){

		this.descricaoHidrometroCapacidade = descricaoHidrometroCapacidade;
	}

}
