package gcom.atendimentopublico.ordemservico.bean;

import gcom.util.ConstantesSistema;

import java.io.Serializable;

public class OrdemServicoSeletivaComandoHelper
				implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private String titulo;

	private String tipoPesquisa;

	private Integer[] firma;

	private String firmaDescricao;

	private Integer[] servicoTipo;

	private String servicoTipoDescricao;

	// Período do Comando
	private String dataComandoInicial;

	private String dataComandoFinal;

	private String dataRealizacaoComandoInicial;

	private String dataRealizacaoComandoFinal;

	private String situacaoComando;

	private Integer[] elo;

	private Integer[] faturamentoGrupo;

	private Integer[] gerenciaRegional;

	private Integer[] localidade;

	private Integer[] setor;

	private Integer[] quadra;

	private Integer[] rota;

	private Integer[] lote;

	private Integer[] bairro;

	private Integer[] logradouro;

	// Caracteristicas
	private Integer[] perfilImovel;

	private String perfilImovelDescricao;

	private Integer[] categoria;

	private String categoriaDescricao;

	private Integer[] subCategoria;

	private Integer subCategoriaDescricao;

	private Integer[] ligacaoAguaSituacao;

	private Integer[] ligacaoEsgotoSituacao;

	// Utilizadas no resultado da pesquisa
	private String dataComando;

	private String dataRealizacaoComando;

	private String quantidadeMaximaOrdem;

	private int numeroPaginasPesquisa = ConstantesSistema.NUMERO_NAO_INFORMADO;

	public Integer getId(){

		return id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public String getTitulo(){

		return titulo;
	}


	public void setTitulo(String titulo){

		this.titulo = titulo;
	}


	public String getTipoPesquisa(){

		return tipoPesquisa;
	}


	public void setTipoPesquisa(String tipoPesquisa){

		this.tipoPesquisa = tipoPesquisa;
	}

	public Integer[] getFirma(){

		return firma;
	}

	public void setFirma(Integer[] firma){

		this.firma = firma;
	}

	public String getFirmaDescricao(){

		return firmaDescricao;
	}

	public void setFirmaDescricao(String firmaDescricao){

		this.firmaDescricao = firmaDescricao;
	}

	public Integer[] getServicoTipo(){

		return servicoTipo;
	}

	public void setServicoTipo(Integer[] servicoTipo){

		this.servicoTipo = servicoTipo;
	}

	public String getDataComandoInicial(){

		return dataComandoInicial;
	}

	public void setDataComandoInicial(String dataComandoInicial){

		this.dataComandoInicial = dataComandoInicial;
	}

	public String getDataComandoFinal(){

		return dataComandoFinal;
	}

	public void setDataComandoFinal(String dataComandoFinal){

		this.dataComandoFinal = dataComandoFinal;
	}

	public String getDataRealizacaoComandoInicial(){

		return dataRealizacaoComandoInicial;
	}

	public void setDataRealizacaoComandoInicial(String dataRealizacaoComandoInicial){

		this.dataRealizacaoComandoInicial = dataRealizacaoComandoInicial;
	}

	public String getDataRealizacaoComandoFinal(){

		return dataRealizacaoComandoFinal;
	}

	public void setDataRealizacaoComandoFinal(String dataRealizacaoComandoFinal){

		this.dataRealizacaoComandoFinal = dataRealizacaoComandoFinal;
	}

	public String getSituacaoComando(){

		return situacaoComando;
	}


	public void setSituacaoComando(String situacaoComando){

		this.situacaoComando = situacaoComando;
	}

	public Integer[] getElo(){

		return elo;
	}

	public void setElo(Integer[] elo){

		this.elo = elo;
	}

	public Integer[] getFaturamentoGrupo(){

		return faturamentoGrupo;
	}

	public void setFaturamentoGrupo(Integer[] faturamentoGrupo){

		this.faturamentoGrupo = faturamentoGrupo;
	}

	public Integer[] getGerenciaRegional(){

		return gerenciaRegional;
	}

	public void setGerenciaRegional(Integer[] gerenciaRegional){

		this.gerenciaRegional = gerenciaRegional;
	}

	public Integer[] getLocalidade(){

		return localidade;
	}

	public void setLocalidade(Integer[] localidade){

		this.localidade = localidade;
	}

	public Integer[] getSetor(){

		return setor;
	}

	public void setSetor(Integer[] setor){

		this.setor = setor;
	}

	public Integer[] getQuadra(){

		return quadra;
	}

	public void setQuadra(Integer[] quadra){

		this.quadra = quadra;
	}

	public Integer[] getRota(){

		return rota;
	}

	public void setRota(Integer[] rota){

		this.rota = rota;
	}

	public Integer[] getLote(){

		return lote;
	}

	public void setLote(Integer[] lote){

		this.lote = lote;
	}

	public Integer[] getBairro(){

		return bairro;
	}

	public void setBairro(Integer[] bairro){

		this.bairro = bairro;
	}


	public Integer[] getLogradouro(){

		return logradouro;
	}


	public void setLogradouro(Integer[] logradouro){

		this.logradouro = logradouro;
	}

	public Integer[] getPerfilImovel(){

		return perfilImovel;
	}

	public void setPerfilImovel(Integer[] perfilImovel){

		this.perfilImovel = perfilImovel;
	}


	public String getPerfilImovelDescricao(){

		return perfilImovelDescricao;
	}


	public void setPerfilImovelDescricao(String perfilImovelDescricao){

		this.perfilImovelDescricao = perfilImovelDescricao;
	}

	public Integer[] getCategoria(){

		return categoria;
	}

	public void setCategoria(Integer[] categoria){

		this.categoria = categoria;
	}


	public String getCategoriaDescricao(){

		return categoriaDescricao;
	}


	public void setCategoriaDescricao(String categoriaDescricao){

		this.categoriaDescricao = categoriaDescricao;
	}

	public Integer[] getSubCategoria(){

		return subCategoria;
	}

	public void setSubCategoria(Integer[] subCategoria){

		this.subCategoria = subCategoria;
	}

	public Integer getSubCategoriaDescricao(){

		return subCategoriaDescricao;
	}

	public void setSubCategoriaDescricao(Integer subCategoriaDescricao){

		this.subCategoriaDescricao = subCategoriaDescricao;
	}

	public Integer[] getLigacaoAguaSituacao(){

		return ligacaoAguaSituacao;
	}

	public void setLigacaoAguaSituacao(Integer[] ligacaoAguaSituacao){

		this.ligacaoAguaSituacao = ligacaoAguaSituacao;
	}

	public Integer[] getLigacaoEsgotoSituacao(){

		return ligacaoEsgotoSituacao;
	}

	public void setLigacaoEsgotoSituacao(Integer[] ligacaoEsgotoSituacao){

		this.ligacaoEsgotoSituacao = ligacaoEsgotoSituacao;
	}

	public static long getSerialversionuid(){

		return serialVersionUID;
	}

	public String getDataComando(){

		return dataComando;
	}

	public void setDataComando(String dataComando){

		this.dataComando = dataComando;
	}

	public String getDataRealizacaoComando(){

		return dataRealizacaoComando;
	}

	public void setDataRealizacaoComando(String dataRealizacaoComando){

		this.dataRealizacaoComando = dataRealizacaoComando;
	}

	public String getServicoTipoDescricao(){

		return servicoTipoDescricao;
	}

	public void setServicoTipoDescricao(String servicoTipoDescricao){

		this.servicoTipoDescricao = servicoTipoDescricao;
	}

	public String getQuantidadeMaximaOrdem(){

		return quantidadeMaximaOrdem;
	}

	public void setQuantidadeMaximaOrdem(String quantidadeMaximaOrdem){

		this.quantidadeMaximaOrdem = quantidadeMaximaOrdem;
	}

	public int getNumeroPaginasPesquisa(){

		return numeroPaginasPesquisa;
	}

	public void setNumeroPaginasPesquisa(int numeroPaginasPesquisa){

		this.numeroPaginasPesquisa = numeroPaginasPesquisa;
	}

}
