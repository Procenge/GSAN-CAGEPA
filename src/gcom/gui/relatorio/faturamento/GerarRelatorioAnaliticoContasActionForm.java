package gcom.gui.relatorio.faturamento;

import org.apache.struts.action.ActionForm;


public class GerarRelatorioAnaliticoContasActionForm extends ActionForm {

	private static final long serialVersionUID = 1L;
	private String idGerenciaRegional;
	private String idLocalidade;

	private String nomeLocalidade;
	private String idCategoria;
	private String idCliente;

	private String nomeCliente;
	private String IdImovel;

	private String inscricao;
	private String idSituacao;

	// private String nomeSituacao;
	private String motivoRetificacao;

	private String referencia;// mesAno
	
	private String grupoFaturamento;

	private String setorComercial;

	private String setorComercialNome;

	private String idQuadra;

	private String descricaoQuadra;
	public String getIdGerenciaRegional(){

		return idGerenciaRegional;
	}

	public void setIdGerenciaRegional(String idGerenciaRegional){

		this.idGerenciaRegional = idGerenciaRegional;
	}

	public String getIdLocalidade(){

		return idLocalidade;
	}

	public void setIdLocalidade(String idLocalidade){

		this.idLocalidade = idLocalidade;
	}

	public String getIdCategoria(){

		return idCategoria;
	}

	public void setIdCategoria(String idCategoria){

		this.idCategoria = idCategoria;
	}

	public String getIdCliente(){

		return idCliente;
	}

	public void setIdCliente(String idCliente){

		this.idCliente = idCliente;
	}

	public String getIdImovel(){

		return IdImovel;
	}

	public void setIdImovel(String idImovel){

		IdImovel = idImovel;
	}

	public String getIdSituacao(){

		return idSituacao;
	}

	public void setIdSituacao(String idSituacao){

		this.idSituacao = idSituacao;
	}

	public String getMotivoRetificacao(){

		return motivoRetificacao;
	}

	public void setMotivoRetificacao(String motivoRetificacao){

		this.motivoRetificacao = motivoRetificacao;
	}

	public String getReferencia(){

		return referencia;
	}

	public void setReferencia(String referencia){

		this.referencia = referencia;
	}

	public String getNomeLocalidade(){

		return nomeLocalidade;
	}

	public void setNomeLocalidade(String nomeLocalidade){

		this.nomeLocalidade = nomeLocalidade;
	}

	public String getNomeCliente(){

		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente){

		this.nomeCliente = nomeCliente;
	}

	public String getInscricao(){

		return inscricao;
	}

	public void setInscricao(String inscricao){

		this.inscricao = inscricao;
	}

	public String getGrupoFaturamento(){

		return grupoFaturamento;
	}

	public void setGrupoFaturamento(String grupoFaturamento){

		this.grupoFaturamento = grupoFaturamento;
	}

	public String getSetorComercial(){

		return setorComercial;
	}

	public void setSetorComercial(String setorComercial){

		this.setorComercial = setorComercial;
	}

	public String getIdQuadra(){

		return idQuadra;
	}

	public void setIdQuadra(String idQuadra){

		this.idQuadra = idQuadra;
	}

	public String getSetorComercialNome(){

		return setorComercialNome;
	}

	public void setSetorComercialNome(String setorComercialNome){

		this.setorComercialNome = setorComercialNome;
	}

	public String getDescricaoQuadra(){

		return descricaoQuadra;
	}

	public void setDescricaoQuadra(String descricaoQuadra){

		this.descricaoQuadra = descricaoQuadra;
	}
}
