/**
 * 
 */

package gcom.gui.cadastro.aguaparatodos;

import org.apache.struts.validator.ValidatorActionForm;

/**
 * @author Luciano Galvão
 * @date 23/03/2012
 */
public class GerarGuiaImovelAguaParaTodosActionForm
				extends ValidatorActionForm {

	/**
	 * Constantes
	 */
	public static final String NENHUM = "0";

	public static final String GERAR = "1";

	private static final long serialVersionUID = 1L;

	/**
	 * Atributos
	 */
	private String id;

	private String flagOperacao;

	// DADOS DO IMÓVEL
	private String matricula;

	private String inscricaoFormatadaImovel;

	private String descricaoImovelPerfil;

	private String descricaoLigacaoAguaSituacao;

	private String descricaoLigacaoEsgotoSituacao;

	private String enderecoImovel;

	private String clienteNome;

	private String clienteRelacaoTipo;

	private String clienteCpf;

	private String clienteCnpj;

	// DADOS DA GUIA DE PAGAMENTO
	private String tipoDebitoId;

	private String tipoDebito;

	private String valorPadraoTipoDebito;

	private String valorLimiteTipoDebito;

	private String valorGuia;

	private String quantidadeContas;

	private String valorTotalAgua;

	private String valorTotalEsgoto;

	private String valorTotalDebito;

	private String valorTotalCredito;

	private String valorTotalImposto;

	private String valorTotalConta;

	public String getValorTotalAgua(){

		return valorTotalAgua;
	}

	public void setValorTotalAgua(String valorTotalAgua){

		this.valorTotalAgua = valorTotalAgua;
	}

	public String getValorTotalEsgoto(){

		return valorTotalEsgoto;
	}

	public void setValorTotalEsgoto(String valorTotalEsgoto){

		this.valorTotalEsgoto = valorTotalEsgoto;
	}

	public String getValorTotalDebito(){

		return valorTotalDebito;
	}

	public void setValorTotalDebito(String valorTotalDebito){

		this.valorTotalDebito = valorTotalDebito;
	}

	public String getValorTotalCredito(){

		return valorTotalCredito;
	}

	public void setValorTotalCredito(String valorTotalCredito){

		this.valorTotalCredito = valorTotalCredito;
	}

	public String getValorTotalImposto(){

		return valorTotalImposto;
	}

	public void setValorTotalImposto(String valorTotalImposto){

		this.valorTotalImposto = valorTotalImposto;
	}

	public String getValorTotalConta(){

		return valorTotalConta;
	}

	public void setValorTotalConta(String valorTotalConta){

		this.valorTotalConta = valorTotalConta;
	}

	/**
	 * SESSÃO
	 * colecaoContaValores
	 * valorAgua
	 * valorEsgoto
	 * valorDebito
	 * valorCredito
	 * valorImposto
	 * valorConta
	 * valorAcrescimo
	 */

	public String getId(){

		return id;
	}

	public void setId(String id){

		this.id = id;
	}

	/**
	 * @param matricula
	 *            the matricula to set
	 */
	public void setMatricula(String matricula){

		this.matricula = matricula;
	}

	/**
	 * @return the matricula
	 */
	public String getMatricula(){

		return matricula;
	}

	/**
	 * @param flagOperacao
	 *            the flagOperacao to set
	 */
	public void setFlagOperacao(String flagOperacao){

		this.flagOperacao = flagOperacao;
	}

	/**
	 * @return the flagOperacao
	 */
	public String getFlagOperacao(){

		return flagOperacao;
	}

	public String getInscricaoFormatadaImovel(){

		return inscricaoFormatadaImovel;
	}

	public void setInscricaoFormatadaImovel(String inscricaoFormatadaImovel){

		this.inscricaoFormatadaImovel = inscricaoFormatadaImovel;
	}

	public String getDescricaoImovelPerfil(){

		return descricaoImovelPerfil;
	}

	public void setDescricaoImovelPerfil(String descricaoImovelPerfil){

		this.descricaoImovelPerfil = descricaoImovelPerfil;
	}

	public String getDescricaoLigacaoAguaSituacao(){

		return descricaoLigacaoAguaSituacao;
	}

	public void setDescricaoLigacaoAguaSituacao(String descricaoLigacaoAguaSituacao){

		this.descricaoLigacaoAguaSituacao = descricaoLigacaoAguaSituacao;
	}

	public String getDescricaoLigacaoEsgotoSituacao(){

		return descricaoLigacaoEsgotoSituacao;
	}

	public void setDescricaoLigacaoEsgotoSituacao(String descricaoLigacaoEsgotoSituacao){

		this.descricaoLigacaoEsgotoSituacao = descricaoLigacaoEsgotoSituacao;
	}

	public String getEnderecoImovel(){

		return enderecoImovel;
	}

	public void setEnderecoImovel(String enderecoImovel){

		this.enderecoImovel = enderecoImovel;
	}

	public String getClienteNome(){

		return clienteNome;
	}

	public void setClienteNome(String clienteNome){

		this.clienteNome = clienteNome;
	}

	public String getClienteRelacaoTipo(){

		return clienteRelacaoTipo;
	}

	public void setClienteRelacaoTipo(String clienteRelacaoTipo){

		this.clienteRelacaoTipo = clienteRelacaoTipo;
	}

	public String getClienteCpf(){

		return clienteCpf;
	}

	public void setClienteCpf(String clienteCpf){

		this.clienteCpf = clienteCpf;
	}

	public String getClienteCnpj(){

		return clienteCnpj;
	}

	public void setClienteCnpj(String clienteCnpj){

		this.clienteCnpj = clienteCnpj;
	}

	public String getTipoDebito(){

		return tipoDebito;
	}

	public void setTipoDebito(String tipoDebito){

		this.tipoDebito = tipoDebito;
	}

	public String getValorPadraoTipoDebito(){

		return valorPadraoTipoDebito;
	}

	public void setValorPadraoTipoDebito(String valorPadraoTipoDebito){

		this.valorPadraoTipoDebito = valorPadraoTipoDebito;
	}

	public String getValorLimiteTipoDebito(){

		return valorLimiteTipoDebito;
	}

	public void setValorLimiteTipoDebito(String valorLimiteTipoDebito){

		this.valorLimiteTipoDebito = valorLimiteTipoDebito;
	}

	public String getValorGuia(){

		return valorGuia;
	}

	public void setValorGuia(String valorGuia){

		this.valorGuia = valorGuia;
	}

	public void setTipoDebitoId(String tipoDebitoId){

		this.tipoDebitoId = tipoDebitoId;
	}

	public String getTipoDebitoId(){

		return tipoDebitoId;
	}

	public void setQuantidadeContas(String quantidadeContas){

		this.quantidadeContas = quantidadeContas;
	}

	public String getQuantidadeContas(){

		return quantidadeContas;
	}

}
