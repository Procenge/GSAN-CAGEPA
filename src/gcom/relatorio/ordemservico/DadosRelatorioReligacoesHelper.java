
package gcom.relatorio.ordemservico;

import gcom.util.Util;

public class DadosRelatorioReligacoesHelper {

	private String numeroPagina;

	private String numeroOS;

	private String numeroRA;

	private String matricula;

	private String inscricao;

	private String numeroHidrometro;

	private String cliente;

	private String endereco;

	private String bairro;

	private String tipoLigacao;

	private String categoria;

	private String tipoServico;

	private String tipoRelacao;

	/**
	 * @return the numeroPagina
	 */
	public String getNumeroPagina(){

		return numeroPagina;
	}

	/**
	 * @param numeroPagina
	 *            the numeroPagina to set
	 */
	public void setNumeroPagina(String numeroPagina){

		this.numeroPagina = numeroPagina;
	}

	public String getNumeroOS(){

		return numeroOS;
	}

	public void setNumeroOS(String numeroOS){

		if(numeroOS != null){
			numeroOS = Util.formatarParaCodigoBarrasInt2Of5(numeroOS);
		}
		this.numeroOS = numeroOS;
	}

	public String getNumeroRA(){

		return numeroRA;
	}

	public void setNumeroRA(String numeroRA){

		this.numeroRA = numeroRA;
	}

	public String getMatricula(){

		return matricula;
	}

	public void setMatricula(String matricula){

		this.matricula = matricula;
	}

	public String getInscricao(){

		return inscricao;
	}

	public void setInscricao(String inscricao){

		this.inscricao = inscricao;
	}

	public String getNumeroHidrometro(){

		return numeroHidrometro;
	}

	public void setNumeroHidrometro(String numeroHidrometro){

		this.numeroHidrometro = numeroHidrometro;
	}

	public String getCliente(){

		return cliente;
	}

	public void setCliente(String cliente){

		this.cliente = cliente;
	}

	public String getEndereco(){

		return endereco;
	}

	public void setEndereco(String endereco){

		this.endereco = endereco;
	}

	public String getBairro(){

		return bairro;
	}

	public void setBairro(String bairro){

		this.bairro = bairro;
	}

	public String getTipoLigacao(){

		return tipoLigacao;
	}

	public void setTipoLigacao(String tipoLigacao){

		this.tipoLigacao = tipoLigacao;
	}

	public String getCategoria(){

		return categoria;
	}

	public void setCategoria(String categoria){

		this.categoria = categoria;
	}

	public String getTipoServico(){

		return tipoServico;
	}

	public void setTipoServico(String tipoServico){

		this.tipoServico = tipoServico;
	}

	public String getTipoRelacao(){

		return tipoRelacao;
	}

	public void setTipoRelacao(String tipoRelacao){

		this.tipoRelacao = tipoRelacao;
	}

}
