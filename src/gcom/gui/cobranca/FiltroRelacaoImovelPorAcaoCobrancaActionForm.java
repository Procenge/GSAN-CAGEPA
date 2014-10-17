
package gcom.gui.cobranca;

import org.apache.struts.action.ActionForm;

public class FiltroRelacaoImovelPorAcaoCobrancaActionForm
				extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String comando;

	private String tituloCobrancaAcaoAtividadeComando;

	private String idCobrancaAcaoAtividadeComando;

	private String descricaoGrupo;

	private String idCobrancaAcaoAtividadeCronograma;

	private String[] acao;

	private String grupo;

	private String[] setorComercial;

	private String[] bairro;

	private String[] categoria;

	private String periodoInicial;

	private String periodoFim;

	private String idGrupo;

	private String idLocalidade;

	private String localidade;

	public String getIdGrupo(){

		return idGrupo;
	}

	public void setIdGrupo(String idGrupo){

		this.idGrupo = idGrupo;
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

	public String[] getAcao(){

		return acao;
	}

	public void setAcao(String[] acao){

		this.acao = acao;
	}

	public String getGrupo(){

		return grupo;
	}

	public void setGrupo(String grupo){

		this.grupo = grupo;
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

	public String[] getCategoria(){

		return categoria;
	}

	public void setCategoria(String[] categoria){

		this.categoria = categoria;
	}

	public String getPeriodoInicial(){

		return periodoInicial;
	}

	public void setPeriodoInicial(String periodoInicial){

		this.periodoInicial = periodoInicial;
	}

	public String getPeriodoFim(){

		return periodoFim;
	}

	public void setPeriodoFim(String periodoFim){

		this.periodoFim = periodoFim;
	}

	public String getIdLocalidade(){

		return idLocalidade;
	}

	public void setIdLocalidade(String idLocalidade){

		this.idLocalidade = idLocalidade;
	}

	public String getLocalidade(){

		return localidade;
	}

	public void setLocalidade(String localidade){

		this.localidade = localidade;
	}

}
