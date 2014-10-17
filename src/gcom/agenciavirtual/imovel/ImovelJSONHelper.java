
package gcom.agenciavirtual.imovel;

import java.util.Date;

public class ImovelJSONHelper {

	/*
	 * Matr�cula: 1022553366
	 * Inscri��o: 00.00.555066.6
	 * Cliente: Esperidi�o dos Santos Amim
	 * Endere�o: Rua dos Palmitos, 168, Espinheiro
	 * Hidr�metro: A08N544044
	 * Consumo m�dio: 10
	 * Data de atualiza��o dos dados: 11/06/2012
	 * Situa��o de �gua: Ligado
	 * Situa��o de Esgoto: Potencial
	 */
	private Integer matricula;

	private Integer idMunicipio;

	private Integer idBairro;

	private Integer idLogradouro;

	private String numeroImovel;

	private String inscricao;

	private String cliente;

	private String endereco;

	private String hidrometro;

	private Integer consumoMedio;

	private Date dataUltimaAtualizacao;

	private String situacaoAgua;

	private String situacaoEsgoto;

	public String getInscricao(){

		return inscricao;
	}

	public void setInscricao(String inscricao){

		this.inscricao = inscricao;
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

	public String getHidrometro(){

		return hidrometro;
	}

	public void setHidrometro(String hidrometro){

		this.hidrometro = hidrometro;
	}

	public Date getDataUltimaAtualizacao(){

		return dataUltimaAtualizacao;
	}

	public void setDataUltimaAtualizacao(Date dataUltimaAtualizacao){

		this.dataUltimaAtualizacao = dataUltimaAtualizacao;
	}

	public String getSituacaoAgua(){

		return situacaoAgua;
	}

	public void setSituacaoAgua(String situacaoAgua){

		this.situacaoAgua = situacaoAgua;
	}

	public String getSituacaoEsgoto(){

		return situacaoEsgoto;
	}

	public void setSituacaoEsgoto(String situacaoEsgoto){

		this.situacaoEsgoto = situacaoEsgoto;
	}

	public Integer getConsumoMedio(){

		return consumoMedio;
	}

	public void setConsumoMedio(Integer consumoMedio){

		this.consumoMedio = consumoMedio;
	}

	public Integer getMatricula(){

		return matricula;
	}

	public void setMatricula(Integer matricula){

		this.matricula = matricula;
	}

	public Integer getIdMunicipio(){

		return idMunicipio;
	}

	public void setIdMunicipio(Integer idMunicipio){

		this.idMunicipio = idMunicipio;
	}

	public Integer getIdBairro(){

		return idBairro;
	}

	public void setIdBairro(Integer idBairro){

		this.idBairro = idBairro;
	}

	public Integer getIdLogradouro(){

		return idLogradouro;
	}

	public void setIdLogradouro(Integer idLogradouro){

		this.idLogradouro = idLogradouro;
	}

	public String getNumeroImovel(){

		return numeroImovel;
	}

	public void setNumeroImovel(String numeroImovel){

		this.numeroImovel = numeroImovel;
	}
}
