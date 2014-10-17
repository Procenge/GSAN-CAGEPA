
package gcom.gui.cobranca;

import org.apache.struts.action.ActionForm;

public class GerarRelatorioAcompanhamentoExecucaoServicoCobrancaActionForm
				extends ActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String comando;

	private String tituloCobrancaAcaoAtividadeComando;

	private String idCobrancaAcaoAtividadeComando;

	private String descricaoGrupo;

	private String idCobrancaAcaoAtividadeCronograma;

	private String acao;

	private String situacao;

	private String religado;

	private String motivoEncerramento;

	private String servico;

	private String localidade;

	private String localidadeDescricao;

	private String grupo;

	private String[] setorComercial;

	private String[] bairro;

	private String periodoInicial;

	private String periodoFim;

	public String getGrupo(){

		return grupo;
	}

	public void setGrupo(String grupo){

		this.grupo = grupo;
	}

	public String getLocalidadeDescricao(){

		return localidadeDescricao;
	}

	public void setLocalidadeDescricao(String localidadeDescricao){

		this.localidadeDescricao = localidadeDescricao;
	}

	public String getServico(){

		return servico;
	}

	public void setServico(String servico){

		this.servico = servico;
	}

	public String getLocalidade(){

		return localidade;
	}

	public void setLocalidade(String localidade){

		this.localidade = localidade;
	}

	public String[] getSetorComercial(){

		return setorComercial;
	}

	public void setSetorComercial(String[] setorComercial){

		this.setorComercial = setorComercial;
	}

	public String[] getBairro(){

		return bairro;
	}

	public void setBairro(String[] bairro){

		this.bairro = bairro;
	}

	public String getMotivoEncerramento(){

		return motivoEncerramento;
	}

	public void setMotivoEncerramento(String motivoEncerramento){

		this.motivoEncerramento = motivoEncerramento;
	}

	public String getSituacao(){

		return situacao;
	}

	public void setSituacao(String situacao){

		this.situacao = situacao;
	}

	public String getReligado(){

		return religado;
	}

	public void setReligado(String religado){

		this.religado = religado;
	}

	public String getComando(){

		return comando;
	}

	public void setComando(String comando){

		this.comando = comando;
	}

	public String getTituloCobrancaAcaoAtividadeComando(){

		return tituloCobrancaAcaoAtividadeComando;
	}

	public void setTituloCobrancaAcaoAtividadeComando(String tituloCobrancaAcaoAtividadeComando){

		this.tituloCobrancaAcaoAtividadeComando = tituloCobrancaAcaoAtividadeComando;
	}

	public String getIdCobrancaAcaoAtividadeComando(){

		return idCobrancaAcaoAtividadeComando;
	}

	public void setIdCobrancaAcaoAtividadeComando(String idCobrancaAcaoAtividadeComando){

		this.idCobrancaAcaoAtividadeComando = idCobrancaAcaoAtividadeComando;
	}

	public String getDescricaoGrupo(){

		return descricaoGrupo;
	}

	public void setDescricaoGrupo(String descricaoGrupo){

		this.descricaoGrupo = descricaoGrupo;
	}

	public String getIdCobrancaAcaoAtividadeCronograma(){

		return idCobrancaAcaoAtividadeCronograma;
	}

	public void setIdCobrancaAcaoAtividadeCronograma(String idCobrancaAcaoAtividadeCronograma){

		this.idCobrancaAcaoAtividadeCronograma = idCobrancaAcaoAtividadeCronograma;
	}

	public String getAcao(){

		return acao;
	}

	public void setAcao(String acao){

		this.acao = acao;
	}

	/**
	 * @return the periodoInicial
	 */
	public String getPeriodoInicial(){

		return periodoInicial;
	}

	/**
	 * @param periodoInicial
	 *            the periodoInicial to set
	 */
	public void setPeriodoInicial(String periodoInicial){

		this.periodoInicial = periodoInicial;
	}

	/**
	 * @return the periodoFim
	 */
	public String getPeriodoFim(){

		return periodoFim;
	}

	/**
	 * @param periodoFim
	 *            the periodoFim to set
	 */
	public void setPeriodoFim(String periodoFim){

		this.periodoFim = periodoFim;
	}
}
