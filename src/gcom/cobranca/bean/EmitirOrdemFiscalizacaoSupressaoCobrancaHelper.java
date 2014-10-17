
package gcom.cobranca.bean;

import gcom.util.Util;

import java.io.Serializable;

/**
 * @author isilva
 * @date 21/09/2010
 */
public class EmitirOrdemFiscalizacaoSupressaoCobrancaHelper
				implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final int TAMANHO_NUMERO_OS = 6;

	private String numeroPagina;

	private String matricula;

	private String nomeCliente;

	private String endereco;

	private String bairro;

	private String hidrometro;

	private String roteiro;

	private String inscricao;

	private String horaImpressao;

	private String dataImpressao;

	private String dataComparecimento;

	private String representacaoNumericaCodBarraFormatada;

	private String representacaoNumericaCodBarraSemDigito;

	private String numero;

	private String ra;

	private String codDescricaoServico;

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

	public String getRepresentacaoNumericaCodBarraFormatada(){

		return representacaoNumericaCodBarraFormatada;
	}

	public void setRepresentacaoNumericaCodBarraFormatada(String representacaoNumericaCodBarraFormatada){

		if(representacaoNumericaCodBarraFormatada != null){
			representacaoNumericaCodBarraFormatada = Util.formatarParaCodigoBarrasInt2Of5(representacaoNumericaCodBarraFormatada);
		}

		this.representacaoNumericaCodBarraFormatada = representacaoNumericaCodBarraFormatada;
	}

	public String getRepresentacaoNumericaCodBarraSemDigito(){

		return representacaoNumericaCodBarraSemDigito;
	}

	public void setRepresentacaoNumericaCodBarraSemDigito(String representacaoNumericaCodBarraSemDigito){

		if(representacaoNumericaCodBarraSemDigito != null){
			representacaoNumericaCodBarraSemDigito = Util.formatarParaCodigoBarrasInt2Of5(representacaoNumericaCodBarraSemDigito);
		}

		this.representacaoNumericaCodBarraSemDigito = representacaoNumericaCodBarraSemDigito;
	}

	public String getMatricula(){

		return matricula;
	}

	public void setMatricula(String matricula){

		this.matricula = matricula;
	}

	public String getNomeCliente(){

		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente){

		this.nomeCliente = nomeCliente;
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

	public String getHidrometro(){

		return hidrometro;
	}

	public void setHidrometro(String hidrometro){

		this.hidrometro = hidrometro;
	}

	public String getRoteiro(){

		return roteiro;
	}

	public void setRoteiro(String roteiro){

		this.roteiro = roteiro;
	}

	public String getHoraImpressao(){

		return horaImpressao;
	}

	public void setHoraImpressao(String horaImpressao){

		this.horaImpressao = horaImpressao;
	}

	public String getDataImpressao(){

		return dataImpressao;
	}

	public void setDataImpressao(String dataImpressao){

		this.dataImpressao = dataImpressao;
	}

	public String getDataComparecimento(){

		return dataComparecimento;
	}

	public void setDataComparecimento(String dataComparecimento){

		this.dataComparecimento = dataComparecimento;
	}

	public String getInscricao(){

		return inscricao;
	}

	public void setInscricao(String inscricao){

		this.inscricao = inscricao;
	}

	/**
	 * @return the numero
	 */
	public String getNumero(){

		return numero;
	}

	/**
	 * @param numero
	 *            the numero to set
	 */
	public void setNumero(String numero){

		this.numero = numero;
	}

	/**
	 * @return the ra
	 */
	public String getRa(){

		return ra;
	}

	/**
	 * @param ra
	 *            the ra to set
	 */
	public void setRa(String ra){

		this.ra = ra;
	}

	/**
	 * @return the codDescricaoServico
	 */
	public String getCodDescricaoServico(){

		return codDescricaoServico;
	}

	/**
	 * @param codDescricaoServico
	 *            the codDescricaoServico to set
	 */
	public void setCodDescricaoServico(String codDescricaoServico){

		this.codDescricaoServico = codDescricaoServico;
	}
}
