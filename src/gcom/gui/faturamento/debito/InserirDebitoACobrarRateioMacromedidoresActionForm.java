package gcom.gui.faturamento.debito;

import org.apache.struts.validator.ValidatorActionForm;

/**
 * @author Ado Rocha
 * @date 26/02/2014
 */
public class InserirDebitoACobrarRateioMacromedidoresActionForm
				extends ValidatorActionForm {

	private String idRegistroAtendimento;

	private String nomeRegistroAtendimento;

	private String matriculaImovel;

	private String enderecoImovel;

	private String idTipoLigacao;

	private String mesAnoReferenciaFaturamento;

	private String inscricaoImovel;

	private String descricaoLigacaoAguaSituacao;

	private String descricaoLigacaoEsgotoSituacao;

	private String numeroPrestacoes;

	private String valorDebitoImovel;

	private String tipoDebitosRateio;

	private String[] idImoveisCondominio;

	public String getIdRegistroAtendimento(){

		return idRegistroAtendimento;
	}

	public void setIdRegistroAtendimento(String idRegistroAtendimento){

		this.idRegistroAtendimento = idRegistroAtendimento;
	}

	public String getNomeRegistroAtendimento(){

		return nomeRegistroAtendimento;
	}

	public void setNomeRegistroAtendimento(String nomeRegistroAtendimento){

		this.nomeRegistroAtendimento = nomeRegistroAtendimento;
	}

	public String getMatriculaImovel(){

		return matriculaImovel;
	}

	public void setMatriculaImovel(String matriculaImovel){

		this.matriculaImovel = matriculaImovel;
	}


	public String getEnderecoImovel(){

		return enderecoImovel;
	}


	public void setEnderecoImovel(String enderecoImovel){

		this.enderecoImovel = enderecoImovel;
	}

	public String getIdTipoLigacao(){

		return idTipoLigacao;
	}

	public void setIdTipoLigacao(String idTipoLigacao){

		this.idTipoLigacao = idTipoLigacao;
	}

	public String getMesAnoReferenciaFaturamento(){

		return mesAnoReferenciaFaturamento;
	}

	public void setMesAnoReferenciaFaturamento(String mesAnoReferenciaFaturamento){

		this.mesAnoReferenciaFaturamento = mesAnoReferenciaFaturamento;
	}

	public String getInscricaoImovel(){

		return inscricaoImovel;
	}

	public void setInscricaoImovel(String inscricaoImovel){

		this.inscricaoImovel = inscricaoImovel;
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

	public String getNumeroPrestacoes(){

		return numeroPrestacoes;
	}

	public void setNumeroPrestacoes(String numeroPrestacoes){

		this.numeroPrestacoes = numeroPrestacoes;
	}

	public String getValorDebitoImovel(){

		return valorDebitoImovel;
	}

	public void setValorDebitoImovel(String valorDebitoImovel){

		this.valorDebitoImovel = valorDebitoImovel;
	}

	public String getTipoDebitosRateio(){

		return tipoDebitosRateio;
	}

	public void setTipoDebitosRateio(String tipoDebitosRateio){

		this.tipoDebitosRateio = tipoDebitosRateio;
	}

	public String[] getIdImoveisCondominio(){

		return idImoveisCondominio;
	}

	public void setIdImoveisCondominio(String[] idImoveisCondominio){

		this.idImoveisCondominio = idImoveisCondominio;
	}

}
