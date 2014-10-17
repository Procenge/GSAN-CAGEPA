
package gcom.gui.atendimentopublico.ordemservico;

import java.io.Serializable;
import java.util.Collection;

public class OrdemServicoSeletivaHelper
				implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Parametros
	private String simulacao;

	private String firma;

	private String nomeFirma;

	private String quantidadeMaxima;

	private String elo;

	private String nomeElo;

	private String inscricaoTipo;

	private String[] rota;

	private String servicoTipo;

	private String servicoTipoDescricao;

	private String[] localidade;

	private String[] bairro;

	private String[] logradouro;

	private String[] setor;

	private String[] quadra;

	private String faturamentoGrupo;

	private String gerenciaRegional;

	private String[] lote;

	// Caracteristicas
	private String[] perfilImovel;

	private String perfilImovelDescricao;

	private String[] categoria;

	private String categoriaDescricao;

	private String[] subCategoria;

	private String subCategoriaDescricao;

	private String intervaloQuantidadeEconomiasInicial;

	private String intervaloQuantidadeEconomiasFinal;

	private String intervaloQuantidadeDocumentosInicial;

	private String intervaloQuantidadeDocumentosFinal;

	private String intervaloNumeroMoradoresInicial;

	private String intervaloNumeroMoradoresFinal;

	private String intervaloAreaConstruidaInicial;

	private String intervaloAreaConstruidaFinal;

	private String intervaloAreaConstruidaPredefinida;

	private String imovelCondominio;

	private String mediaImovel;

	private String consumoPorEconomia;

	private String tipoMedicao;

	private String tipoMedicaoDescricao;

	private String[] ligacaoAguaSituacao;

	private String intervaloDataCorteInicial;

	private String intervaloDataCorteFinal;

	private String dataCorteInicial;

	private String dataCorteFinal;

	private String intervaloNumeroPontosUtilizacaoInicial;

	private String intervaloNumeroPontosUtilizacaoFinal;

	private String intervaloNumeroConsumoMesInicial;

	private String intervaloNumeroConsumoMesFinal;

	private Collection<ConsumoMedioImovelHelper> colecaoConsumoMedioImovel;

	private String intervaloQuantidadeContasVencidasInicial;

	private String intervaloQuantidadeContasVencidasFinal;

	private String valorTotalDebitoVencido;

	private String indicadorPreFiscalizados;

	// Hidrometro
	private String capacidade;

	private String capacidadeDescricao;

	private String[] marca;

	private String marcaDescricao;

	private String[] anormalidadeHidrometro;

	private String numeroOcorrenciasConsecutivas;

	private String mesAnoInstalacao;

	private String[] hidrometroProtecao;

	private String[] hidrometroLocalInstalacao;

	private Collection<DadosDoHidrometroHelper> colecaoDadosDoHidrometro;

	private String[] hidrometroClasseMetrologica;

	// Campos Comuns
	private String idUsuario;

	private Integer tipoFormatoRelatorio;

	// Simulação
	private String quantidadeEconomia;

	private String quantidadeDocumentos;

	private String numeroMoradores;

	private String areaConstruida;

	private String consumoEconomia;

	private Collection<Integer> colecaoImoveis;

	private String[] ligacaoEsgotoSituacao;

	private Integer idComandoOsSeletiva;

	public String getQuantidadeEconomia(){

		return quantidadeEconomia;
	}

	public void setQuantidadeEconomia(String quantidadeEconomia){

		this.quantidadeEconomia = quantidadeEconomia;
	}

	public String getQuantidadeDocumentos(){

		return quantidadeDocumentos;
	}

	public void setQuantidadeDocumentos(String quantidadeDocumentos){

		this.quantidadeDocumentos = quantidadeDocumentos;
	}

	public String getNumeroMoradores(){

		return numeroMoradores;
	}

	public void setNumeroMoradores(String numeroMoradores){

		this.numeroMoradores = numeroMoradores;
	}

	public String getAreaConstruida(){

		return areaConstruida;
	}

	public void setAreaConstruida(String areaConstruida){

		this.areaConstruida = areaConstruida;
	}

	public String getConsumoEconomia(){

		return consumoEconomia;
	}

	public void setConsumoEconomia(String consumoEconomia){

		this.consumoEconomia = consumoEconomia;
	}

	public String getSimulacao(){

		return simulacao;
	}

	public void setSimulacao(String simulacao){

		this.simulacao = simulacao;
	}

	public String getFirma(){

		return firma;
	}

	public void setFirma(String firma){

		this.firma = firma;
	}

	public String getNomeFirma(){

		return nomeFirma;
	}

	public void setNomeFirma(String nomeFirma){

		this.nomeFirma = nomeFirma;
	}

	public String getQuantidadeMaxima(){

		return quantidadeMaxima;
	}

	public void setQuantidadeMaxima(String quantidadeMaxima){

		this.quantidadeMaxima = quantidadeMaxima;
	}

	public String getElo(){

		return elo;
	}

	public void setElo(String elo){

		this.elo = elo;
	}

	public String getNomeElo(){

		return nomeElo;
	}

	public void setNomeElo(String nomeElo){

		this.nomeElo = nomeElo;
	}

	public String getInscricaoTipo(){

		return inscricaoTipo;
	}

	public void setInscricaoTipo(String inscricaoTipo){

		this.inscricaoTipo = inscricaoTipo;
	}

	public String[] getRota(){

		return rota;
	}

	public void setRota(String[] rota){

		this.rota = rota;
	}

	public String getServicoTipo(){

		return servicoTipo;
	}

	public void setServicoTipo(String servicoTipo){

		this.servicoTipo = servicoTipo;
	}

	public String[] getLocalidade(){

		return localidade;
	}

	public void setLocalidade(String[] localidade){

		this.localidade = localidade;
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

	public String getIntervaloAreaConstruidaPredefinida(){

		return intervaloAreaConstruidaPredefinida;
	}

	public void setIntervaloAreaConstruidaPredefinida(String intervaloAreaConstruidaPredefinida){

		this.intervaloAreaConstruidaPredefinida = intervaloAreaConstruidaPredefinida;
	}

	public String getImovelCondominio(){

		return imovelCondominio;
	}

	public void setImovelCondominio(String imovelCondominio){

		this.imovelCondominio = imovelCondominio;
	}

	public String getMediaImovel(){

		return mediaImovel;
	}

	public void setMediaImovel(String mediaImovel){

		this.mediaImovel = mediaImovel;
	}

	public String getConsumoPorEconomia(){

		return consumoPorEconomia;
	}

	public void setConsumoPorEconomia(String consumoPorEconomia){

		this.consumoPorEconomia = consumoPorEconomia;
	}

	public String getTipoMedicao(){

		return tipoMedicao;
	}

	public void setTipoMedicao(String tipoMedicao){

		this.tipoMedicao = tipoMedicao;
	}

	public String getTipoMedicaoDescricao(){

		return tipoMedicaoDescricao;
	}

	public void setTipoMedicaoDescricao(String tipoMedicaoDescricao){

		this.tipoMedicaoDescricao = tipoMedicaoDescricao;
	}

	public String getCapacidade(){

		return capacidade;
	}

	public void setCapacidade(String capacidade){

		this.capacidade = capacidade;
	}

	public String getCapacidadeDescricao(){

		return capacidadeDescricao;
	}

	public void setCapacidadeDescricao(String capacidadeDescricao){

		this.capacidadeDescricao = capacidadeDescricao;
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

	public String getMesAnoInstalacao(){

		return mesAnoInstalacao;
	}

	public void setMesAnoInstalacao(String mesAnoInstalacao){

		this.mesAnoInstalacao = mesAnoInstalacao;
	}

	public String getIdUsuario(){

		return idUsuario;
	}

	public void setIdUsuario(String idUsuario){

		this.idUsuario = idUsuario;
	}

	public Integer getTipoFormatoRelatorio(){

		return tipoFormatoRelatorio;
	}

	public void setTipoFormatoRelatorio(Integer tipoFormatoRelatorio){

		this.tipoFormatoRelatorio = tipoFormatoRelatorio;
	}

	public String getServicoTipoDescricao(){

		return servicoTipoDescricao;
	}

	public void setServicoTipoDescricao(String servicoTipoDescricao){

		this.servicoTipoDescricao = servicoTipoDescricao;
	}

	public void setColecaoImoveis(Collection<Integer> colecaoImoveis){

		this.colecaoImoveis = colecaoImoveis;
	}

	public Collection<Integer> getColecaoImoveis(){

		return colecaoImoveis;
	}

	public String getFaturamentoGrupo(){

		return faturamentoGrupo;
	}

	public void setFaturamentoGrupo(String faturamentoGrupo){

		this.faturamentoGrupo = faturamentoGrupo;
	}

	public String getGerenciaRegional(){

		return gerenciaRegional;
	}

	public void setGerenciaRegional(String gerenciaRegional){

		this.gerenciaRegional = gerenciaRegional;
	}

	public String[] getLote(){

		return lote;
	}

	public void setLote(String[] lote){

		this.lote = lote;
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

	public String getIntervaloDataCorteInicial(){

		return intervaloDataCorteInicial;
	}

	public void setIntervaloDataCorteInicial(String intervaloDataCorteInicial){

		this.intervaloDataCorteInicial = intervaloDataCorteInicial;
	}

	public String getIntervaloDataCorteFinal(){

		return intervaloDataCorteFinal;
	}

	public void setIntervaloDataCorteFinal(String intervaloDataCorteFinal){

		this.intervaloDataCorteFinal = intervaloDataCorteFinal;
	}

	public String getDataCorteInicial(){

		return dataCorteInicial;
	}

	public void setDataCorteInicial(String dataCorteInicial){

		this.dataCorteInicial = dataCorteInicial;
	}

	public String getDataCorteFinal(){

		return dataCorteFinal;
	}

	public void setDataCorteFinal(String dataCorteFinal){

		this.dataCorteFinal = dataCorteFinal;
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

	public String getIntervaloNumeroConsumoMesInicial(){

		return intervaloNumeroConsumoMesInicial;
	}

	public void setIntervaloNumeroConsumoMesInicial(String intervaloNumeroConsumoMesInicial){

		this.intervaloNumeroConsumoMesInicial = intervaloNumeroConsumoMesInicial;
	}

	public String getIntervaloNumeroConsumoMesFinal(){

		return intervaloNumeroConsumoMesFinal;
	}

	public void setIntervaloNumeroConsumoMesFinal(String intervaloNumeroConsumoMesFinal){

		this.intervaloNumeroConsumoMesFinal = intervaloNumeroConsumoMesFinal;
	}

	public Collection<ConsumoMedioImovelHelper> getColecaoConsumoMedioImovel(){

		return colecaoConsumoMedioImovel;
	}

	public void setColecaoConsumoMedioImovel(Collection<ConsumoMedioImovelHelper> colecaoConsumoMedioImovel){

		this.colecaoConsumoMedioImovel = colecaoConsumoMedioImovel;
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

	public String getValorTotalDebitoVencido(){

		return valorTotalDebitoVencido;
	}

	public void setValorTotalDebitoVencido(String valorTotalDebitoVencido){

		this.valorTotalDebitoVencido = valorTotalDebitoVencido;
	}

	public String getIndicadorPreFiscalizados(){

		return indicadorPreFiscalizados;
	}

	public void setIndicadorPreFiscalizados(String indicadorPreFiscalizados){

		this.indicadorPreFiscalizados = indicadorPreFiscalizados;
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

	public Collection<DadosDoHidrometroHelper> getColecaoDadosDoHidrometro(){

		return colecaoDadosDoHidrometro;
	}

	public void setColecaoDadosDoHidrometro(Collection<DadosDoHidrometroHelper> colecaoDadosDoHidrometro){

		this.colecaoDadosDoHidrometro = colecaoDadosDoHidrometro;
	}

	public Integer getIdComandoOsSeletiva(){

		return idComandoOsSeletiva;
	}

	public void setIdComandoOsSeletiva(Integer idComandoOsSeletiva){

		this.idComandoOsSeletiva = idComandoOsSeletiva;
	}

}
