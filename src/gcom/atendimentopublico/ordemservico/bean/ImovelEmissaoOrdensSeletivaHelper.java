package gcom.atendimentopublico.ordemservico.bean;

import gcom.gui.atendimentopublico.ordemservico.ConsumoMedioImovelHelper;
import gcom.gui.atendimentopublico.ordemservico.DadosDoHidrometroHelper;
import gcom.util.IoUtil;

import java.io.Serializable;
import java.sql.Blob;
import java.util.Collection;

import org.apache.struts.upload.FormFile;
import org.hibernate.Hibernate;

public class ImovelEmissaoOrdensSeletivaHelper
				implements Serializable {

	// Parametros
	private String simulacao;

	private String firma;

	private String nomeFirma;

	private String quantidadeMaxima;

	private String elo;

	private String nomeElo;

	private String inscricaoTipo;

	private String servicoTipo;

	private String servicoTipoDescricao;

	private String faturamentoGrupo;

	private String gerenciaRegional;

	private String[] localidade;

	private String[] bairro;

	private String[] logradouro;

	private String[] setor;

	private String[] rota;

	private String[] quadra;

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

	private String[] ligacaoEsgotoSituacao;

	private String intervaloDataCorteInicial;

	private String intervaloDataCorteFinal;

	private String dataCorteInicial;

	private String dataCorteFinal;

	private String intervaloNumeroConsumoMesInicial;

	private String intervaloNumeroConsumoMesFinal;

	private String intervaloQuantidadeContasVencidasInicial;

	private String intervaloQuantidadeContasVencidasFinal;

	private String valorTotalDebitoVencido;

	private String intervaloNumeroPontosUtilizacaoInicial;

	private String intervaloNumeroPontosUtilizacaoFinal;

	private Collection<ConsumoMedioImovelHelper> colecaoConsumoMedioImovel;

	// Hidrometro
	private String capacidade;

	private String capacidadeDescricao;

	private String[] marca;

	private String marcaDescricao;

	private String[] hidrometroClasseMetrologica;

	private String[] hidrometroProtecao;

	private String[] hidrometroLocalInstalacao;

	private String[] anormalidadeHidrometro;

	private String numeroOcorrenciasConsecutivas;

	private String mesAnoInstalacao;

	private FormFile arquivoMatricula;

	private String arquivoDownload;

	private Collection<Integer> idsImoveis;

	private String enderecoArquivoDownload;

	private Collection<DadosDoHidrometroHelper> colecaoDadosDoHidrometro;

	private String arquivoCarregado;

	private String titulo;

	private byte[] arquivo;

	private Blob ArquivoBlob;

	private String idComandoOsServicoSeletiva;


	public FormFile getArquivoMatricula(){

		return arquivoMatricula;
	}

	public byte[] getArquivo(){

		return this.arquivo;
	}

	public void setArquivo(byte[] arquivo){

		this.arquivo = arquivo;
		this.popularArquivoBlob();
	}

	public Blob getArquivoBlob(){

		if(this.arquivo != null){
			return Hibernate.createBlob(this.arquivo);
		}else{
			return null;
		}
	}

	public void setArquivoBlob(Blob ArquivoBlob){

		this.arquivo = IoUtil.toByteArray(ArquivoBlob);
	}

	private void popularArquivoBlob(){

		this.ArquivoBlob = (this.getArquivoBlob());
	}

	public void setArquivoMatricula(FormFile arquivoMatricula){

		this.arquivoMatricula = arquivoMatricula;
	}

	public String[] getAnormalidadeHidrometro(){

		return anormalidadeHidrometro;
	}

	public void setAnormalidadeHidrometro(String[] anormalidadeHidrometro){

		this.anormalidadeHidrometro = anormalidadeHidrometro;
	}

	public String getCapacidade(){

		return capacidade;
	}

	public void setCapacidade(String capacidade){

		this.capacidade = capacidade;
	}

	public String[] getCategoria(){

		return categoria;
	}

	public void setCategoria(String[] categoria){

		this.categoria = categoria;
	}

	public String getConsumoPorEconomia(){

		return consumoPorEconomia;
	}

	public void setConsumoPorEconomia(String consumoPorEconomia){

		this.consumoPorEconomia = consumoPorEconomia;
	}

	public String getElo(){

		return elo;
	}

	public void setElo(String elo){

		this.elo = elo;
	}

	public String getFirma(){

		return firma;
	}

	public void setFirma(String firma){

		this.firma = firma;
	}

	public String getImovelCondominio(){

		return imovelCondominio;
	}

	public void setImovelCondominio(String imovelCondominio){

		this.imovelCondominio = imovelCondominio;
	}

	public String getIntervaloAreaConstruidaFinal(){

		return intervaloAreaConstruidaFinal;
	}

	public void setIntervaloAreaConstruidaFinal(String intervaloAreaConstruidaFinal){

		this.intervaloAreaConstruidaFinal = intervaloAreaConstruidaFinal;
	}

	public String getIntervaloAreaConstruidaInicial(){

		return intervaloAreaConstruidaInicial;
	}

	public void setIntervaloAreaConstruidaInicial(String intervaloAreaConstruidaInicial){

		this.intervaloAreaConstruidaInicial = intervaloAreaConstruidaInicial;
	}

	public String getIntervaloAreaConstruidaPredefinida(){

		return intervaloAreaConstruidaPredefinida;
	}

	public void setIntervaloAreaConstruidaPredefinida(String intervaloAreaConstruidaPredefinida){

		this.intervaloAreaConstruidaPredefinida = intervaloAreaConstruidaPredefinida;
	}

	public String getIntervaloNumeroMoradoresFinal(){

		return intervaloNumeroMoradoresFinal;
	}

	public void setIntervaloNumeroMoradoresFinal(String intervaloNumeroMoradoresFinal){

		this.intervaloNumeroMoradoresFinal = intervaloNumeroMoradoresFinal;
	}

	public String getIntervaloNumeroMoradoresInicial(){

		return intervaloNumeroMoradoresInicial;
	}

	public void setIntervaloNumeroMoradoresInicial(String intervaloNumeroMoradoresInicial){

		this.intervaloNumeroMoradoresInicial = intervaloNumeroMoradoresInicial;
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

	public String getIntervaloQuantidadeEconomiasFinal(){

		return intervaloQuantidadeEconomiasFinal;
	}

	public void setIntervaloQuantidadeEconomiasFinal(String intervaloQuantidadeEconomiasFinal){

		this.intervaloQuantidadeEconomiasFinal = intervaloQuantidadeEconomiasFinal;
	}

	public String getIntervaloQuantidadeEconomiasInicial(){

		return intervaloQuantidadeEconomiasInicial;
	}

	public void setIntervaloQuantidadeEconomiasInicial(String intervaloQuantidadeEconomiasInicial){

		this.intervaloQuantidadeEconomiasInicial = intervaloQuantidadeEconomiasInicial;
	}

	public String[] getMarca(){

		return marca;
	}

	public void setMarca(String[] marca){

		this.marca = marca;
	}

	public String getMediaImovel(){

		return mediaImovel;
	}

	public void setMediaImovel(String mediaImovel){

		this.mediaImovel = mediaImovel;
	}

	public String getMesAnoInstalacao(){

		return mesAnoInstalacao;
	}

	public void setMesAnoInstalacao(String mesAnoInstalacao){

		this.mesAnoInstalacao = mesAnoInstalacao;
	}

	public String getNumeroOcorrenciasConsecutivas(){

		return numeroOcorrenciasConsecutivas;
	}

	public void setNumeroOcorrenciasConsecutivas(String numeroOcorrenciasConsecutivas){

		this.numeroOcorrenciasConsecutivas = numeroOcorrenciasConsecutivas;
	}

	public String[] getPerfilImovel(){

		return perfilImovel;
	}

	public void setPerfilImovel(String[] perfilImovel){

		this.perfilImovel = perfilImovel;
	}

	public String getQuantidadeMaxima(){

		return quantidadeMaxima;
	}

	public void setQuantidadeMaxima(String quantidadeMaxima){

		this.quantidadeMaxima = quantidadeMaxima;
	}

	public String[] getSubCategoria(){

		return subCategoria;
	}

	public void setSubCategoria(String[] subCategoria){

		this.subCategoria = subCategoria;
	}

	public String getSimulacao(){

		return simulacao;
	}

	public void setSimulacao(String simulacao){

		this.simulacao = simulacao;
	}

	public String getTipoMedicao(){

		return tipoMedicao;
	}

	public void setTipoMedicao(String tipoMedicao){

		this.tipoMedicao = tipoMedicao;
	}

	public String getInscricaoTipo(){

		return inscricaoTipo;
	}

	public void setInscricaoTipo(String inscricaoTipo){

		this.inscricaoTipo = inscricaoTipo;
	}

	public String getNomeElo(){

		return nomeElo;
	}

	public void setNomeElo(String nomeElo){

		this.nomeElo = nomeElo;
	}

	public void limparCamposHidrometro(){

		this.capacidade = null;
		this.marca = null;
		this.anormalidadeHidrometro = null;
		this.numeroOcorrenciasConsecutivas = null;
		this.mesAnoInstalacao = null;
		this.hidrometroClasseMetrologica = null;
		this.hidrometroProtecao = null;
		this.hidrometroLocalInstalacao = null;
		this.colecaoDadosDoHidrometro = null;
	}

	public void limparCombos(){

		this.bairro = null;
		this.rota = null;
		this.quadra = null;
		this.logradouro = null;
		this.setor = null;
		this.lote = null;
	}

	public String getNomeFirma(){

		return nomeFirma;
	}

	public void setNomeFirma(String nomeFirma){

		this.nomeFirma = nomeFirma;
	}

	public String getCapacidadeDescricao(){

		return capacidadeDescricao;
	}

	public void setCapacidadeDescricao(String capacidadeDescricao){

		this.capacidadeDescricao = capacidadeDescricao;
	}

	public String getCategoriaDescricao(){

		return categoriaDescricao;
	}

	public void setCategoriaDescricao(String categoriaDescricao){

		this.categoriaDescricao = categoriaDescricao;
	}

	public String getMarcaDescricao(){

		return marcaDescricao;
	}

	public void setMarcaDescricao(String marcaDescricao){

		this.marcaDescricao = marcaDescricao;
	}

	public String getPerfilImovelDescricao(){

		return perfilImovelDescricao;
	}

	public void setPerfilImovelDescricao(String perfilImovelDescricao){

		this.perfilImovelDescricao = perfilImovelDescricao;
	}

	public String getSubCategoriaDescricao(){

		return subCategoriaDescricao;
	}

	public void setSubCategoriaDescricao(String subCategoriaDescricao){

		this.subCategoriaDescricao = subCategoriaDescricao;
	}

	public String getTipoMedicaoDescricao(){

		return tipoMedicaoDescricao;
	}

	public void setTipoMedicaoDescricao(String tipoMedicaoDescricao){

		this.tipoMedicaoDescricao = tipoMedicaoDescricao;
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

	public String[] getSetor(){

		return setor;
	}

	public void setSetor(String[] setor){

		this.setor = setor;
		this.bairro = new String[] {"-1"};
		this.logradouro = new String[] {"-1"};
		if(setor != null && setor.length > 1){
			this.quadra = new String[] {"-1"};
			this.rota = new String[] {"-1"};
			this.lote = new String[] {"-1"};
		}
	}

	public String[] getQuadra(){

		return quadra;
	}

	public void setQuadra(String[] quadra){

		this.quadra = quadra;
		this.rota = new String[] {"-1"};

		if(quadra != null && quadra.length > 1){
			this.lote = new String[] {"-1"};
		}
	}

	public void setRota(String[] rota){

		this.rota = rota;
		this.quadra = new String[] {"-1"};
		this.lote = new String[] {"-1"};
	}

	public void setBairro(String[] bairro){

		this.bairro = bairro;
		this.logradouro = new String[] {"-1"};
		this.setor = new String[] {"-1"};
		this.quadra = new String[] {"-1"};
		this.lote = new String[] {"-1"};
		this.rota = new String[] {"-1"};
	}

	public void setLogradouro(String[] logradouro){

		this.logradouro = logradouro;
	}

	public String[] getRota(){

		return rota;
	}

	public String[] getBairro(){

		return bairro;
	}

	public String[] getLogradouro(){

		return logradouro;
	}

	public String getServicoTipoDescricao(){

		return servicoTipoDescricao;
	}

	public void setServicoTipoDescricao(String servicoTipoDescricao){

		this.servicoTipoDescricao = servicoTipoDescricao;
	}

	public void setArquivoDownload(String arquivoDownload){

		this.arquivoDownload = arquivoDownload;
	}

	public String getArquivoDownload(){

		return arquivoDownload;
	}

	public void setIdsImoveis(Collection<Integer> idsImoveis){

		this.idsImoveis = idsImoveis;
	}

	public Collection<Integer> getIdsImoveis(){

		return idsImoveis;
	}

	public void setEnderecoArquivoDownload(String enderecoArquivoDownload){

		this.enderecoArquivoDownload = enderecoArquivoDownload;
	}

	public String getEnderecoArquivoDownload(){

		return enderecoArquivoDownload;
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

	public Collection<ConsumoMedioImovelHelper> getColecaoConsumoMedioImovel(){

		return colecaoConsumoMedioImovel;
	}

	public void setColecaoConsumoMedioImovel(Collection<ConsumoMedioImovelHelper> colecaoConsumoMedioImovel){

		this.colecaoConsumoMedioImovel = colecaoConsumoMedioImovel;
	}

	public Collection<DadosDoHidrometroHelper> getColecaoDadosDoHidrometro(){

		return colecaoDadosDoHidrometro;
	}

	public void setColecaoDadosDoHidrometro(Collection<DadosDoHidrometroHelper> colecaoDadosDoHidrometro){

		this.colecaoDadosDoHidrometro = colecaoDadosDoHidrometro;
	}

	public String getArquivoCarregado(){

		return arquivoCarregado;
	}

	public void setArquivoCarregado(String arquivoCarregado){

		this.arquivoCarregado = arquivoCarregado;
	}

	public String getTitulo(){

		return titulo;
	}

	public void setTitulo(String titulo){

		this.titulo = titulo;
	}

	public String getIdComandoOsServicoSeletiva(){

		return idComandoOsServicoSeletiva;
	}

	public void setIdComandoOsServicoSeletiva(String idComandoOsServicoSeletiva){

		this.idComandoOsServicoSeletiva = idComandoOsServicoSeletiva;
	}

}
