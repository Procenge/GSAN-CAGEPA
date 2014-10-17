/**
 * 
 */

package gcom.gui.cadastro.aguaparatodos;

import java.util.Collection;

import org.apache.struts.validator.ValidatorActionForm;

/**
 * @author Bruno Ferreira dos Santos
 */
public class ConsultarImovelAguaParaTodosActionForm
				extends ValidatorActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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

	private String situacao;

	private String dataExclusao;

	private String motivoExclusao;

	private String usuarioInclusao;

	private String usuarioExclusao;

	private String dataReferenciaFaruramentoInicial;

	private String dataReferenciaFaruramentoFinal;

	private String codigoTarifa;

	private String dataValidadeTarifa;

	private String dataCadastramento;

	private String dataHabilitacao;

	private String dataHabilitacaoTime;

	private String flagBotaoHist;

	private String dataReferencia;

	public final static String EXIBE = "1";

	public final static String NAO_EXIBE = "0";

	private Collection colecaoImovelAguaParaTodos;

	public void limparFormulario(){

		setDataReferenciaFaruramentoInicial("");
		setDataReferencia("");
		setDataReferenciaFaruramentoFinal("");
		setDataExclusao("");
		setMotivoExclusao("");
		setUsuarioExclusao("");
		setDataHabilitacao("");
		setDataHabilitacaoTime("");
	}

	public Collection getColecaoImovelAguaParaTodos(){

		return colecaoImovelAguaParaTodos;
	}

	public void setColecaoImovelAguaParaTodos(Collection colecaoImovelAguaParaTodos){

		this.colecaoImovelAguaParaTodos = colecaoImovelAguaParaTodos;
	}

	public static final String NENHUM = "0";

	public static final String CONSULTAR = "1";

	public String getMatricula(){

		return matricula;
	}

	public void setMatricula(String matricula){

		this.matricula = matricula;
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

	public String getFlagOperacao(){

		return flagOperacao;
	}

	public void setFlagOperacao(String flagOperacao){

		this.flagOperacao = flagOperacao;
	}

	public String getNic(){

		return nic;
	}

	public void setNic(String nic){

		this.nic = nic;
	}

	public String getNomeContribuinte(){

		return nomeContribuinte;
	}

	public void setNomeContribuinte(String nomeContribuinte){

		this.nomeContribuinte = nomeContribuinte;
	}

	public String getSituacao(){

		return situacao;
	}

	public void setSituacao(String situacao){

		this.situacao = situacao;
	}

	public String getDataExclusao(){

		return dataExclusao;
	}

	public void setDataExclusao(String dataExclusao){

		this.dataExclusao = dataExclusao;
	}

	public String getMotivoExclusao(){

		return motivoExclusao;
	}

	public void setMotivoExclusao(String motivoExclusao){

		this.motivoExclusao = motivoExclusao;
	}

	public String getUsuarioInclusao(){

		return usuarioInclusao;
	}

	public void setUsuarioInclusao(String usuarioInclusao){

		this.usuarioInclusao = usuarioInclusao;
	}

	public String getUsuarioExclusao(){

		return usuarioExclusao;
	}

	public void setUsuarioExclusao(String usuarioExclusao){

		this.usuarioExclusao = usuarioExclusao;
	}

	public String getDataReferenciaFaruramentoInicial(){

		return dataReferenciaFaruramentoInicial;
	}

	public void setDataReferenciaFaruramentoInicial(String dataReferenciaFaruramentoInicial){

		this.dataReferenciaFaruramentoInicial = dataReferenciaFaruramentoInicial;
	}

	public String getDataReferenciaFaruramentoFinal(){

		return dataReferenciaFaruramentoFinal;
	}

	public void setDataReferenciaFaruramentoFinal(String dataReferenciaFaruramentoFinal){

		this.dataReferenciaFaruramentoFinal = dataReferenciaFaruramentoFinal;
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

	public String getDataCadastramento(){

		return dataCadastramento;
	}

	public void setDataCadastramento(String dataCadastramento){

		this.dataCadastramento = dataCadastramento;
	}

	public String getDataHabilitacao(){

		return dataHabilitacao;
	}

	public void setDataHabilitacao(String dataHabilitacao){

		this.dataHabilitacao = dataHabilitacao;
	}

	/**
	 * @param flagBotaoHist
	 *            the flagBotaoHist to set
	 */
	public void setFlagBotaoHist(String flagBotaoHist){

		this.flagBotaoHist = flagBotaoHist;
	}

	/**
	 * @return the flagBotaoHist
	 */
	public String getFlagBotaoHist(){

		return flagBotaoHist;
	}

	/**
	 * @param dataReferencia
	 *            the dataReferencia to set
	 */
	public void setDataReferencia(String dataReferencia){

		this.dataReferencia = dataReferencia;
	}

	/**
	 * @return the dataReferencia
	 */
	public String getDataReferencia(){

		return dataReferencia;
	}

	/**
	 * @param dataHabilitacaoTime
	 *            the dataHabilitacaoTime to set
	 */
	public void setDataHabilitacaoTime(String dataHabilitacaoTime){

		this.dataHabilitacaoTime = dataHabilitacaoTime;
	}

	/**
	 * @return the dataHabilitacaoTime
	 */
	public String getDataHabilitacaoTime(){

		return dataHabilitacaoTime;
	}

}
