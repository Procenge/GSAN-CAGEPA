package gcom.relatorio.atendimentopublico;

import gcom.relatorio.RelatorioBean;

public class RelatorioParecerEncerramentoOSBean
				implements RelatorioBean {

	// Dados Gerais
	private String numeroOS;

	private String situacaoOS;

	private String dataGeracao;

	private String tipoServicoOSId;

	private String tipoServicoOSDescricao;

	private String observacao;

	private String unidadeGeracaoId;

	private String unidadeGeracaoDescricao;

	private String usuarioGeracaoId;

	private String usuarioGeracaoNome;

	private String dataUltimaEmissao;

	private String dataEncerramento;

	private String horaEncerramento;

	private String observacaoEncerramento;

	private String matriculaImovel;

	private String enderecoImovel;

	private String matriculaCliente;

	private String nomeCliente;

	private String rgCliente;

	private String cpfCliente;

	private String numeroRA;

	public RelatorioParecerEncerramentoOSBean(String numeroOS, String situacaoOS, String dataGeracao, String tipoServicoOSId,
												String tipoServicoOSDescricao, String observacao, String unidadeGeracaoId,
												String unidadeGeracaoDescricao, String usuarioGeracaoId, String usuarioGeracaoNome,
												String dataUltimaEmissao, String dataEncerramento, String horaEncerramento,
												String observacaoEncerramento) {

		super();
		this.numeroOS = numeroOS;
		this.situacaoOS = situacaoOS;
		this.dataGeracao = dataGeracao;
		this.tipoServicoOSId = tipoServicoOSId;
		this.tipoServicoOSDescricao = tipoServicoOSDescricao;
		this.observacao = observacao;
		this.unidadeGeracaoId = unidadeGeracaoId;
		this.unidadeGeracaoDescricao = unidadeGeracaoDescricao;
		this.usuarioGeracaoId = usuarioGeracaoId;
		this.usuarioGeracaoNome = usuarioGeracaoNome;
		this.dataUltimaEmissao = dataUltimaEmissao;
		this.dataEncerramento = dataEncerramento;
		this.horaEncerramento = horaEncerramento;
		this.observacaoEncerramento = observacaoEncerramento;
	}

	public RelatorioParecerEncerramentoOSBean(String numeroOS, String situacaoOS, String dataGeracao, String tipoServicoOSId,
												String tipoServicoOSDescricao, String observacao, String unidadeGeracaoId,
												String unidadeGeracaoDescricao, String usuarioGeracaoId, String usuarioGeracaoNome,
												String dataUltimaEmissao, String dataEncerramento, String horaEncerramento,
												String observacaoEncerramento, String matriculaImovel, String enderecoImovel,
												String matriculaCliente, String nomeCliente, String rgCliente, String cpfCliente,
												String numeroRA) {

		super();
		this.numeroOS = numeroOS;
		this.situacaoOS = situacaoOS;
		this.dataGeracao = dataGeracao;
		this.tipoServicoOSId = tipoServicoOSId;
		this.tipoServicoOSDescricao = tipoServicoOSDescricao;
		this.observacao = observacao;
		this.unidadeGeracaoId = unidadeGeracaoId;
		this.unidadeGeracaoDescricao = unidadeGeracaoDescricao;
		this.usuarioGeracaoId = usuarioGeracaoId;
		this.usuarioGeracaoNome = usuarioGeracaoNome;
		this.dataUltimaEmissao = dataUltimaEmissao;
		this.dataEncerramento = dataEncerramento;
		this.horaEncerramento = horaEncerramento;
		this.observacaoEncerramento = observacaoEncerramento;
		this.matriculaImovel = matriculaImovel;
		this.enderecoImovel = enderecoImovel;
		this.matriculaCliente = matriculaCliente;
		this.nomeCliente = nomeCliente;
		this.rgCliente = rgCliente;
		this.cpfCliente = cpfCliente;
		this.numeroRA = numeroRA;
	}

	public String getNumeroOS(){

		return numeroOS;
	}

	public void setNumeroOS(String numeroOS){

		this.numeroOS = numeroOS;
	}

	public String getSituacaoOS(){

		return situacaoOS;
	}

	public void setSituacaoOS(String situacaoOS){

		this.situacaoOS = situacaoOS;
	}

	public String getDataGeracao(){

		return dataGeracao;
	}

	public void setDataGeracao(String dataGeracao){

		this.dataGeracao = dataGeracao;
	}

	public String getTipoServicoOSId(){

		return tipoServicoOSId;
	}

	public void setTipoServicoOSId(String tipoServicoOSId){

		this.tipoServicoOSId = tipoServicoOSId;
	}

	public String getTipoServicoOSDescricao(){

		return tipoServicoOSDescricao;
	}

	public void setTipoServicoOSDescricao(String tipoServicoOSDescricao){

		this.tipoServicoOSDescricao = tipoServicoOSDescricao;
	}

	public String getObservacao(){

		return observacao;
	}

	public void setObservacao(String observacao){

		this.observacao = observacao;
	}

	public String getUnidadeGeracaoId(){

		return unidadeGeracaoId;
	}

	public void setUnidadeGeracaoId(String unidadeGeracaoId){

		this.unidadeGeracaoId = unidadeGeracaoId;
	}

	public String getUnidadeGeracaoDescricao(){

		return unidadeGeracaoDescricao;
	}

	public void setUnidadeGeracaoDescricao(String unidadeGeracaoDescricao){

		this.unidadeGeracaoDescricao = unidadeGeracaoDescricao;
	}

	public String getUsuarioGeracaoId(){

		return usuarioGeracaoId;
	}

	public void setUsuarioGeracaoId(String usuarioGeracaoId){

		this.usuarioGeracaoId = usuarioGeracaoId;
	}

	public String getUsuarioGeracaoNome(){

		return usuarioGeracaoNome;
	}

	public void setUsuarioGeracaoNome(String usuarioGeracaoNome){

		this.usuarioGeracaoNome = usuarioGeracaoNome;
	}

	public String getDataUltimaEmissao(){

		return dataUltimaEmissao;
	}

	public void setDataUltimaEmissao(String dataUltimaEmissao){

		this.dataUltimaEmissao = dataUltimaEmissao;
	}

	public String getDataEncerramento(){

		return dataEncerramento;
	}

	public void setDataEncerramento(String dataEncerramento){

		this.dataEncerramento = dataEncerramento;
	}

	public String getObservacaoEncerramento(){

		return observacaoEncerramento;
	}

	public void setObservacaoEncerramento(String observacaoEncerramento){

		this.observacaoEncerramento = observacaoEncerramento;
	}

	public String getHoraEncerramento(){

		return horaEncerramento;
	}

	public void setHoraEncerramento(String horaEncerramento){

		this.horaEncerramento = horaEncerramento;
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

	public String getMatriculaCliente(){

		return matriculaCliente;
	}

	public void setMatriculaCliente(String matriculaCliente){

		this.matriculaCliente = matriculaCliente;
	}

	public String getNomeCliente(){

		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente){

		this.nomeCliente = nomeCliente;
	}

	public String getRgCliente(){

		return rgCliente;
	}

	public void setRgCliente(String rgCliente){

		this.rgCliente = rgCliente;
	}

	public String getCpfCliente(){

		return cpfCliente;
	}

	public void setCpfCliente(String cpfCliente){

		this.cpfCliente = cpfCliente;
	}

	public String getNumeroRA(){

		return numeroRA;
	}

	public void setNumeroRA(String numeroRA){

		this.numeroRA = numeroRA;
	}

}
