package gcom.gui.atendimentopublico.ligacaoesgoto;

import java.util.Date;

import org.apache.struts.action.ActionForm;

public class AtualizarPerfilLigacaoEsgotoActionForm
				extends ActionForm {

	private String numeroOS;

	private String matriculaImovel;

	private String inscricaoImovel;

	private String nomeClienteUsuario;

	private String cpfCnpjClienteUsuario;

	private String situacaoLigacaoAgua;

	private String categoriaImovel;

	private String quantidadeEconomias;

	private String situacaoLigacaoEsgoto;

	private String perfilLigacaoEsgoto;

	private String veioEncerrarOS;

	private String nomeOrdemServico;

	private Date dataConcorrencia;


	public String getNumeroOS(){

		return numeroOS;
	}

	public void setNumeroOS(String numeroOS){

		this.numeroOS = numeroOS;
	}

	public String getMatriculaImovel(){

		return matriculaImovel;
	}

	public void setMatriculaImovel(String matriculaImovel){

		this.matriculaImovel = matriculaImovel;
	}

	public String getInscricaoImovel(){

		return inscricaoImovel;
	}

	public void setInscricaoImovel(String inscricaoImovel){

		this.inscricaoImovel = inscricaoImovel;
	}

	public String getnomeClienteUsuario(){

		return nomeClienteUsuario;
	}

	public void setnomeClienteUsuario(String nomeClienteUsuario){

		this.nomeClienteUsuario = nomeClienteUsuario;
	}

	public String getcpfCnpjClienteUsuario(){

		return cpfCnpjClienteUsuario;
	}

	public void setcpfCnpjClienteUsuario(String cpfCnpjClienteUsuario){

		this.cpfCnpjClienteUsuario = cpfCnpjClienteUsuario;
	}

	public String getSituacaoLigacaoAgua(){

		return situacaoLigacaoAgua;
	}

	public void setSituacaoLigacaoAgua(String situacaoLigacaoAgua){

		this.situacaoLigacaoAgua = situacaoLigacaoAgua;
	}

	public String getCategoriaImovel(){

		return categoriaImovel;
	}

	public void setCategoriaImovel(String categoriaImovel){

		this.categoriaImovel = categoriaImovel;
	}

	public String getQuantidadeEconomias(){

		return quantidadeEconomias;
	}

	public void setQuantidadeEconomias(String quantidadeEconomias){

		this.quantidadeEconomias = quantidadeEconomias;
	}

	public String getSituacaoLigacaoEsgoto(){

		return situacaoLigacaoEsgoto;
	}

	public void setSituacaoLigacaoEsgoto(String situacaoLigacaoEsgoto){

		this.situacaoLigacaoEsgoto = situacaoLigacaoEsgoto;
	}

	public String getPerfilLigacaoEsgoto(){

		return perfilLigacaoEsgoto;
	}

	public void setPerfilLigacaoEsgoto(String perfilLigacaoEsgoto){

		this.perfilLigacaoEsgoto = perfilLigacaoEsgoto;
	}

	public String getVeioEncerrarOS(){

		return veioEncerrarOS;
	}

	public void setVeioEncerrarOS(String veioEncerrarOS){

		this.veioEncerrarOS = veioEncerrarOS;
	}

	public String getNomeOrdemServico(){

		return nomeOrdemServico;
	}

	public void setNomeOrdemServico(String nomeOrdemServico){

		this.nomeOrdemServico = nomeOrdemServico;
	}

	public Date getDataConcorrencia(){

		return dataConcorrencia;
	}

	public void setDataConcorrencia(Date dataConcorrencia){

		this.dataConcorrencia = dataConcorrencia;
	}

}
