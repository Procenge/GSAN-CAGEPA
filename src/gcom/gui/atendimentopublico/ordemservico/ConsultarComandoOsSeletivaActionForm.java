
package gcom.gui.atendimentopublico.ordemservico;

import java.util.Collection;

import org.apache.struts.validator.ValidatorForm;

public class ConsultarComandoOsSeletivaActionForm
				extends ValidatorForm {

	private String titulo;

	private String tipoPesquisa;

	private String[] firma;

	private String idFirma;
	
	private String descricaoFirma;

	private String[] servicoTipo;

	private String idServicoTipo;
	
	private String descricaoServicoTipo;

	// Período do Comando
	private String dataComandoInicial;

	private String dataComandoFinal;

	private String dataRealizacaoComandoInicial;

	private String dataRealizacaoComandoFinal;

	private String situacaoComando;

	private String[] elo;
	
	private String idElo;
	
	private String descricaoElo;
	
	private String[] faturamentoGrupo;

	private String idFaturamentoGrupo;
	
	private String descricaoFaturamentoGrupo;
	
	private String[] gerenciaRegional;

	private String nomeGerenciaRegional;

	private String[] localidade;

	private String nomeLocalidade;

	private String[] setor;

	private String[] quadra;

	private String[] rota;

	private String[] lote;

	private String[] bairro;

	private String[] logradouro;

	// Caracteristicas
	private String[] perfilImovel;

	private String perfilImovelDescricao;

	private String[] categoria;

	private String categoriaDescricao;

	private String[] subCategoria;

	private String subCategoriaDescricao;

	private String[] ligacaoAguaSituacao;

	private String[] ligacaoEsgotoSituacao;

	// Tela de Consulta único Registro
	private String quantidadeMaximaOrdens;

	private String[] imoveis;

	private String intervaloQuantidadeEconomiasInicial;

	private String intervaloQuantidadeEconomiasFinal;

	private String intervaloQuantidadeDocumentosInicial;

	private String intervaloQuantidadeDocumentosFinal;

	private String intervaloNumeroMoradoresInicial;

	private String intervaloNumeroMoradoresFinal;

	private String intervaloNumeroPontosUtilizacaoInicial;

	private String intervaloNumeroPontosUtilizacaoFinal;

	private String intervaloAreaConstruidaInicial;

	private String intervaloAreaConstruidaFinal;

	private String areaContruidaInicial;

	private String areaConstruidadeFinal;

	private String imovelCondominio;

	private String consumoPorEconomia;

	private String intervaloNumeroConsumoMesInicial;

	private String intervaloNumeroConsumoMesFinal;

	private String intervaloNumeroConsumoMedio;

	private String intervaloQuantidadeContasVencidasInicial;

	private String intervaloQuantidadeContasVencidasFinal;

	private String valorTotalDebitoVencido;

	private String[] marca;

	private String marcaDescricao;

	private String[] hidrometroClasseMetrologica;

	private String[] hidrometroProtecao;

	private String[] hidrometroLocalInstalacao;

	private String[] anormalidadeHidrometro;

	private String numeroOcorrenciasConsecutivas;

	private Collection<DadosDoHidrometroHelper> colecaoDadosDoHidrometro;

	private Collection<DadosOrdemServicoHelper> colecaoDadosOrdemServico;

	private Integer[] imovel;
	
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

	public String[] getFirma(){

		return firma;
	}

	public void setFirma(String[] firma){

		this.firma = firma;
	}

	public String getDescricaoFirma(){

		return descricaoFirma;
	}

	public void setDescricaoFirma(String descricaoFirma){

		this.descricaoFirma = descricaoFirma;
	}
	public String[] getServicoTipo(){

		return servicoTipo;
	}

	public void setServicoTipo(String[] servicoTipo){

		this.servicoTipo = servicoTipo;
	}

	public String getDescricaoServicoTipo(){

		return descricaoServicoTipo;
	}

	public void setDescricaoServicoTipo(String descricaoServicoTipo){

		this.descricaoServicoTipo = descricaoServicoTipo;
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

	public String[] getElo(){

		return elo;
	}

	public void setElo(String[] elo){

		this.elo = elo;
	}

	public String[] getFaturamentoGrupo(){

		return faturamentoGrupo;
	}

	public void setFaturamentoGrupo(String[] faturamentoGrupo){

		this.faturamentoGrupo = faturamentoGrupo;
	}

	public String[] getGerenciaRegional(){

		return gerenciaRegional;
	}

	public void setGerenciaRegional(String[] gerenciaRegional){

		this.gerenciaRegional = gerenciaRegional;
	}

	public String[] getLocalidade(){

		return localidade;
	}

	public void setLocalidade(String[] localidade){

		this.localidade = localidade;
	}

	public String[] getSetor(){

		return setor;
	}

	public void setSetor(String[] setor){

		this.setor = setor;
	}

	public String[] getQuadra(){

		return quadra;
	}

	public void setQuadra(String[] quadra){

		this.quadra = quadra;
	}

	public String[] getRota(){

		return rota;
	}

	public void setRota(String[] rota){

		this.rota = rota;
	}

	public String[] getLote(){

		return lote;
	}

	public void setLote(String[] lote){

		this.lote = lote;
	}

	public String[] getBairro(){

		return bairro;
	}

	public void setBairro(String[] bairro){

		this.bairro = bairro;
	}

	public String[] getLogradouro(){

		return logradouro;
	}

	public void setLogradouro(String[] logradouro){

		this.logradouro = logradouro;
	}

	public String[] getPerfilImovel(){

		return perfilImovel;
	}

	public void setPerfilImovel(String[] perfilImovel){

		this.perfilImovel = perfilImovel;
	}

	public String getPerfilImovelDescricao(){

		return perfilImovelDescricao;
	}

	public void setPerfilImovelDescricao(String perfilImovelDescricao){

		this.perfilImovelDescricao = perfilImovelDescricao;
	}

	public String[] getCategoria(){

		return categoria;
	}

	public void setCategoria(String[] categoria){

		this.categoria = categoria;
	}

	public String getCategoriaDescricao(){

		return categoriaDescricao;
	}

	public void setCategoriaDescricao(String categoriaDescricao){

		this.categoriaDescricao = categoriaDescricao;
	}

	public String[] getSubCategoria(){

		return subCategoria;
	}

	public void setSubCategoria(String[] subCategoria){

		this.subCategoria = subCategoria;
	}

	public String getSubCategoriaDescricao(){

		return subCategoriaDescricao;
	}

	public void setSubCategoriaDescricao(String subCategoriaDescricao){

		this.subCategoriaDescricao = subCategoriaDescricao;
	}

	public String[] getLigacaoAguaSituacao(){

		return ligacaoAguaSituacao;
	}

	public void setLigacaoAguaSituacao(String[] ligacaoAguaSituacao){

		this.ligacaoAguaSituacao = ligacaoAguaSituacao;
	}

	public String[] getLigacaoEsgotoSituacao(){

		return ligacaoEsgotoSituacao;
	}

	public void setLigacaoEsgotoSituacao(String[] ligacaoEsgotoSituacao){

		this.ligacaoEsgotoSituacao = ligacaoEsgotoSituacao;
	}

	public String getQuantidadeMaximaOrdens(){

		return quantidadeMaximaOrdens;
	}

	public void setQuantidadeMaximaOrdens(String quantidadeMaximaOrdens){

		this.quantidadeMaximaOrdens = quantidadeMaximaOrdens;
	}

	public String[] getImoveis(){

		return imoveis;
	}

	public void setImoveis(String[] imoveis){

		this.imoveis = imoveis;
	}

	public String getIntervaloQuantidadeEconomiasInicial(){

		return intervaloQuantidadeEconomiasInicial;
	}

	public void setIntervaloQuantidadeEconomiasInicial(String intervaloQuantidadeEconomiasInicial){

		this.intervaloQuantidadeEconomiasInicial = intervaloQuantidadeEconomiasInicial;
	}

	public String getIntervaloQuantidadeEconomiasFinal(){

		return intervaloQuantidadeEconomiasFinal;
	}

	public void setIntervaloQuantidadeEconomiasFinal(String intervaloQuantidadeEconomiasFinal){

		this.intervaloQuantidadeEconomiasFinal = intervaloQuantidadeEconomiasFinal;
	}

	public String getIntervaloQuantidadeDocumentosInicial(){

		return intervaloQuantidadeDocumentosInicial;
	}

	public void setIntervaloQuantidadeDocumentosInicial(String intervaloQuantidadeDocumentosInicial){

		this.intervaloQuantidadeDocumentosInicial = intervaloQuantidadeDocumentosInicial;
	}

	public String getIntervaloQuantidadeDocumentosFinal(){

		return intervaloQuantidadeDocumentosFinal;
	}

	public void setIntervaloQuantidadeDocumentosFinal(String intervaloQuantidadeDocumentosFinal){

		this.intervaloQuantidadeDocumentosFinal = intervaloQuantidadeDocumentosFinal;
	}

	public String getIntervaloNumeroMoradoresInicial(){

		return intervaloNumeroMoradoresInicial;
	}

	public void setIntervaloNumeroMoradoresInicial(String intervaloNumeroMoradoresInicial){

		this.intervaloNumeroMoradoresInicial = intervaloNumeroMoradoresInicial;
	}

	public String getIntervaloNumeroMoradoresFinal(){

		return intervaloNumeroMoradoresFinal;
	}

	public void setIntervaloNumeroMoradoresFinal(String intervaloNumeroMoradoresFinal){

		this.intervaloNumeroMoradoresFinal = intervaloNumeroMoradoresFinal;
	}

	public String getIntervaloNumeroPontosUtilizacaoInicial(){

		return intervaloNumeroPontosUtilizacaoInicial;
	}

	public void setIntervaloNumeroPontosUtilizacaoInicial(String intervaloNumeroPontosUtilizacaoInicial){

		this.intervaloNumeroPontosUtilizacaoInicial = intervaloNumeroPontosUtilizacaoInicial;
	}

	public String getIntervaloNumeroPontosUtilizacaoFinal(){

		return intervaloNumeroPontosUtilizacaoFinal;
	}

	public void setIntervaloNumeroPontosUtilizacaoFinal(String intervaloNumeroPontosUtilizacaoFinal){

		this.intervaloNumeroPontosUtilizacaoFinal = intervaloNumeroPontosUtilizacaoFinal;
	}

	public String getIntervaloAreaConstruidaInicial(){

		return intervaloAreaConstruidaInicial;
	}

	public void setIntervaloAreaConstruidaInicial(String intervaloAreaConstruidaInicial){

		this.intervaloAreaConstruidaInicial = intervaloAreaConstruidaInicial;
	}

	public String getIntervaloAreaConstruidaFinal(){

		return intervaloAreaConstruidaFinal;
	}

	public void setIntervaloAreaConstruidaFinal(String intervaloAreaConstruidaFinal){

		this.intervaloAreaConstruidaFinal = intervaloAreaConstruidaFinal;
	}

	public String getImovelCondominio(){

		return imovelCondominio;
	}

	public void setImovelCondominio(String imovelCondominio){

		this.imovelCondominio = imovelCondominio;
	}

	public String getConsumoPorEconomia(){

		return consumoPorEconomia;
	}

	public void setConsumoPorEconomia(String consumoPorEconomia){

		this.consumoPorEconomia = consumoPorEconomia;
	}

	public String getIntervaloNumeroConsumoMesInicial(){

		return intervaloNumeroConsumoMesInicial;
	}

	public void setIntervaloNumeroConsumoMesInicial(String intervaloNumeroConsumoMesInicial){

		this.intervaloNumeroConsumoMesInicial = intervaloNumeroConsumoMesInicial;
	}

	public String getIntervaloQuantidadeContasVencidasInicial(){

		return intervaloQuantidadeContasVencidasInicial;
	}

	public void setIntervaloQuantidadeContasVencidasInicial(String intervaloQuantidadeContasVencidasInicial){

		this.intervaloQuantidadeContasVencidasInicial = intervaloQuantidadeContasVencidasInicial;
	}

	public String getIntervaloQuantidadeContasVencidasFinal(){

		return intervaloQuantidadeContasVencidasFinal;
	}

	public void setIntervaloQuantidadeContasVencidasFinal(String intervaloQuantidadeContasVencidasFinal){

		this.intervaloQuantidadeContasVencidasFinal = intervaloQuantidadeContasVencidasFinal;
	}

	public String getIntervaloNumeroConsumoMesFinal(){

		return intervaloNumeroConsumoMesFinal;
	}

	public void setIntervaloNumeroConsumoMesFinal(String intervaloNumeroConsumoMesFinal){

		this.intervaloNumeroConsumoMesFinal = intervaloNumeroConsumoMesFinal;
	}

	public String getValorTotalDebitoVencido(){

		return valorTotalDebitoVencido;
	}

	public void setValorTotalDebitoVencido(String valorTotalDebitoVencido){

		this.valorTotalDebitoVencido = valorTotalDebitoVencido;
	}

	public String[] getMarca(){

		return marca;
	}

	public void setMarca(String[] marca){

		this.marca = marca;
	}

	public String getMarcaDescricao(){

		return marcaDescricao;
	}

	public void setMarcaDescricao(String marcaDescricao){

		this.marcaDescricao = marcaDescricao;
	}
	public String[] getHidrometroClasseMetrologica(){

		return hidrometroClasseMetrologica;
	}

	public void setHidrometroClasseMetrologica(String[] hidrometroClasseMetrologica){

		this.hidrometroClasseMetrologica = hidrometroClasseMetrologica;
	}

	public String[] getHidrometroProtecao(){

		return hidrometroProtecao;
	}

	public void setHidrometroProtecao(String[] hidrometroProtecao){

		this.hidrometroProtecao = hidrometroProtecao;
	}

	public String[] getHidrometroLocalInstalacao(){

		return hidrometroLocalInstalacao;
	}

	public void setHidrometroLocalInstalacao(String[] hidrometroLocalInstalacao){

		this.hidrometroLocalInstalacao = hidrometroLocalInstalacao;
	}

	public String[] getAnormalidadeHidrometro(){

		return anormalidadeHidrometro;
	}

	public void setAnormalidadeHidrometro(String[] anormalidadeHidrometro){

		this.anormalidadeHidrometro = anormalidadeHidrometro;
	}

	public String getNumeroOcorrenciasConsecutivas(){

		return numeroOcorrenciasConsecutivas;
	}

	public void setNumeroOcorrenciasConsecutivas(String numeroOcorrenciasConsecutivas){

		this.numeroOcorrenciasConsecutivas = numeroOcorrenciasConsecutivas;
	}

	public Collection<DadosDoHidrometroHelper> getColecaoDadosDoHidrometro(){

		return colecaoDadosDoHidrometro;
	}

	public void setColecaoDadosDoHidrometro(Collection<DadosDoHidrometroHelper> colecaoDadosDoHidrometro){

		this.colecaoDadosDoHidrometro = colecaoDadosDoHidrometro;
	}

	public String getIdFirma(){

		return idFirma;
	}

	public void setIdFirma(String idFirma){

		this.idFirma = idFirma;
	}

	public String getIdServicoTipo(){

		return idServicoTipo;
	}

	public void setIdServicoTipo(String idServicoTipo){

		this.idServicoTipo = idServicoTipo;
	}

	public String getIdElo(){

		return idElo;
	}

	public void setIdElo(String idElo){

		this.idElo = idElo;
	}

	public String getDescricaoElo(){

		return descricaoElo;
	}

	public void setDescricaoElo(String descricaoElo){

		this.descricaoElo = descricaoElo;
	}

	public String getIdFaturamentoGrupo(){

		return idFaturamentoGrupo;
	}

	public void setIdFaturamentoGrupo(String idFaturamentoGrupo){

		this.idFaturamentoGrupo = idFaturamentoGrupo;
	}

	public String getDescricaoFaturamentoGrupo(){

		return descricaoFaturamentoGrupo;
	}

	public void setDescricaoFaturamentoGrupo(String descricaoFaturamentoGrupo){

		this.descricaoFaturamentoGrupo = descricaoFaturamentoGrupo;
	}

	public String getNomeGerenciaRegional(){

		return nomeGerenciaRegional;
	}

	public void setNomeGerenciaRegional(String nomeGerenciaRegional){

		this.nomeGerenciaRegional = nomeGerenciaRegional;
	}

	public String getNomeLocalidade(){

		return nomeLocalidade;
	}

	public void setNomeLocalidade(String nomeLocalidade){

		this.nomeLocalidade = nomeLocalidade;
	}

	public String getAreaContruidaInicial(){

		return areaContruidaInicial;
	}

	public void setAreaContruidaInicial(String areaContruidaInicial){

		this.areaContruidaInicial = areaContruidaInicial;
	}

	public String getAreaConstruidadeFinal(){

		return areaConstruidadeFinal;
	}

	public void setAreaConstruidadeFinal(String areaConstruidadeFinal){

		this.areaConstruidadeFinal = areaConstruidadeFinal;
	}

	public String getIntervaloNumeroConsumoMedio(){

		return intervaloNumeroConsumoMedio;
	}

	public void setIntervaloNumeroConsumoMedio(String intervaloNumeroConsumoMedio){

		this.intervaloNumeroConsumoMedio = intervaloNumeroConsumoMedio;
	}

	public Collection<DadosOrdemServicoHelper> getColecaoDadosOrdemServico(){

		return colecaoDadosOrdemServico;
	}

	public void setColecaoDadosOrdemServico(Collection<DadosOrdemServicoHelper> colecaoDadosOrdemServico){
		this.colecaoDadosOrdemServico = colecaoDadosOrdemServico;
	}

	public Integer[] getImovel(){

		return imovel;
	}

	public void setImovel(Integer[] imovel){

		this.imovel = imovel;
	}

}
