/**
 * 
 */

package gcom.gui.cadastro.aguaparatodos;

import org.apache.struts.validator.ValidatorActionForm;

/**
 * @author Bruno Ferreira dos Santos
 */
public class ManterImovelAguaParaTodosActionForm
				extends ValidatorActionForm {

	/**
	 * Constantes
	 */
	public static final String NENHUM = "0";

	public static final String INSERIR = "1";

	public static final String HABILITAR = "2";

	public static final String EXCLUIR = "3";

	public static final String RENOVAR = "4";

	public static final String SITUACAO_HABILITADO = "Usuário cadastrado e habilitado";

	public static final String SITUACAO_HABILITADO_RENOVADO = "Usuário habilitado com validade renovada";

	public static final String SITUACAO_EXCLUIDO = "Usuário excluído";

	public static final String SITUACAO_CADASTRADO = "Usuário cadastrado aguardando habilitação";

	private static final long serialVersionUID = 1L;

	/**
	 * Atributos
	 */
	private String id;

	private String matricula;

	private String localidade;

	private String situacaoAgua;

	private String situacaoEsgoto;

	private String inscricao;

	private String nomeCliente;

	private String endereco;

	private String telefone;

	private String categoriasEconomia;

	private String flagOperacao;

	private String nic;

	private String nomeContribuinte;

	private String mensagem;

	private String motivoExclusao;

	private String dataHabilitacao;

	private String usuarioInclusao;

	private String dataReferenciaFaruramento;

	private String codigoTarifa;

	private String dataValidadeTarifa;

	private String dataCadastramento;

	private String dataRenovacao;

	private String usuarioRenovacao;

	private String tarifa;

	private String dataNovaValidadeTarifa;

	private String situacaoCadastramento;

	public String getId(){

		return id;
	}

	public void setId(String id){

		this.id = id;
	}

	public String getDataReferenciaFaruramento(){

		return dataReferenciaFaruramento;
	}

	public void setDataReferenciaFaruramento(String dataReferenciaFaruramento){

		this.dataReferenciaFaruramento = dataReferenciaFaruramento;
	}

	public String getCodigoTarifa(){

		return codigoTarifa;
	}

	public void setCodigoTarifa(String codigoTarifa){

		this.codigoTarifa = codigoTarifa;
	}

	public String getDataValidadeTarifa(){

		return dataValidadeTarifa;
	}

	public void setDataValidadeTarifa(String dataValidadeTarifa){

		this.dataValidadeTarifa = dataValidadeTarifa;
	}

	public String getLocalidade(){

		return localidade;
	}

	public void setLocalidade(String localidade){

		this.localidade = localidade;
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

	public String getInscricao(){

		return inscricao;
	}

	public void setInscricao(String inscricao){

		this.inscricao = inscricao;
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

	public String getTelefone(){

		return telefone;
	}

	public void setTelefone(String telefone){

		this.telefone = telefone;
	}

	public String getCategoriasEconomia(){

		return categoriasEconomia;
	}

	public void setCategoriasEconomia(String categoriasEconomia){

		this.categoriasEconomia = categoriasEconomia;
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

	/**
	 * @param nic
	 *            the nic to set
	 */
	public void setNic(String nic){

		this.nic = nic;
	}

	/**
	 * @return the nic
	 */
	public String getNic(){

		return nic;
	}

	/**
	 * @param nomeContribuinte
	 *            the nomeContribuinte to set
	 */
	public void setNomeContribuinte(String nomeContribuinte){

		this.nomeContribuinte = nomeContribuinte;
	}

	/**
	 * @return the nomeContribuinte
	 */
	public String getNomeContribuinte(){

		return nomeContribuinte;
	}

	/**
	 * @param mensagem
	 *            the mensagem to set
	 */
	public void setMensagem(String mensagem){

		this.mensagem = mensagem;
	}

	/**
	 * @return the mensagem
	 */
	public String getMensagem(){

		return mensagem;
	}

	/**
	 * @param motivoExclusao
	 *            the motivoExclusao to set
	 */
	public void setMotivoExclusao(String motivoExclusao){

		this.motivoExclusao = motivoExclusao;
	}

	/**
	 * @return the motivoExclusao
	 */
	public String getMotivoExclusao(){

		return motivoExclusao;
	}

	/**
	 * @param dataHabilitacao
	 *            the dataHabilitacao to set
	 */
	public void setDataHabilitacao(String dataHabilitacao){

		this.dataHabilitacao = dataHabilitacao;
	}

	/**
	 * @return the dataHabilitacao
	 */
	public String getDataHabilitacao(){

		return dataHabilitacao;
	}

	/**
	 * @param dataCadastramento
	 *            the dataCadastramento to set
	 */
	public void setDataCadastramento(String dataCadastramento){

		this.dataCadastramento = dataCadastramento;
	}

	/**
	 * @return the dataCadastramento
	 */
	public String getDataCadastramento(){

		return dataCadastramento;
	}

	/**
	 * @param usuarioInclusao
	 *            the usuarioInclusao to set
	 */
	public void setUsuarioInclusao(String usuarioInclusao){

		this.usuarioInclusao = usuarioInclusao;
	}

	/**
	 * @return the usuarioInclusao
	 */
	public String getUsuarioInclusao(){

		return usuarioInclusao;
	}

	public void setDataRenovacao(String dataRenovacao){

		this.dataRenovacao = dataRenovacao;
	}

	public String getDataRenovacao(){

		return dataRenovacao;
	}

	public void setUsuarioRenovacao(String usuarioRenovacao){

		this.usuarioRenovacao = usuarioRenovacao;
	}

	public String getUsuarioRenovacao(){

		return usuarioRenovacao;
	}

	public void setTarifa(String tarifa){

		this.tarifa = tarifa;
	}

	public String getTarifa(){

		return tarifa;
	}

	public void setDataNovaValidadeTarifa(String dataNovaValidadeTarifa){

		this.dataNovaValidadeTarifa = dataNovaValidadeTarifa;
	}

	public String getDataNovaValidadeTarifa(){

		return dataNovaValidadeTarifa;
	}

	public void setSituacaoCadastramento(String situacaoCadastramento){

		this.situacaoCadastramento = situacaoCadastramento;
	}

	public String getSituacaoCadastramento(){

		return situacaoCadastramento;
	}
}
