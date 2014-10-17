
package gcom.gui.cobranca.cobrancaadministrativa;

import gcom.util.ConstantesSistema;

import java.util.Collection;

import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.ValidatorActionForm;

/**
 * [UC3070] Filtrar Imóvel Cobrança Administrativa
 * 
 * @author Anderson Italo
 * @date 07/09/2012
 */
public class FiltrarImovelCobrancaAdministrativaActionForm
				extends ValidatorActionForm {

	private String idComando;

	private String descricaoComando;

	private Integer[] idsEmpresa;

	private Integer[] idsEmpresaSelecionadas;

	private String idImovel;

	private String inscricaoImovel;

	private String idCliente;

	private String nomeCliente;

	private Integer[] idsGerenciaRegional;

	private Integer[] idsGerenciaRegionalSelecionadas;

	private Integer[] idsUnidadeNegocio;

	private Integer[] idsUnidadeNegocioSelecionadas;

	private String idLocalidadeInicial;

	private String descricaoLocalidadeInicial;

	private String codigoSetorComercialInicial;

	private String descricaoSetorComercialInicial;

	private String numeroQuadraInicial;

	private String idLocalidadeFinal;

	private String descricaoLocalidadeFinal;

	private String codigoSetorComercialFinal;

	private String descricaoSetorComercialFinal;

	private String numeroQuadraFinal;

	private FormFile arquivoMatricula;

	private String arquivoDownload;

	private Collection<Integer> idsImoveis;

	private String enderecoArquivoDownload;

	private String arquivoCarregado;

	private Integer[] idsCategoria;

	private Integer[] idsCategoriaSelecionadas;

	private Integer[] idsSubcategoria;

	private Integer[] idsSubcategoriaSelecionadas;

	private Integer[] idsLigacaoAguaSituacao;

	private Integer[] idsLigacaoAguaSituacaoSelecionadas;

	private Integer[] idsLigacaoEsgotoSituacao;

	private Integer[] idsLigacaoEsgotoSituacaoSelecionadas;

	private String valorDebitoInicial;

	private String valorDebitoFinal;

	private String periodoInclusaoInicial;

	private String periodoInclusaoFinal;

	private String periodoRetiradaInicial;

	private String periodoRetiradaFinal;

	private String indicadorSituacaoCobrancaAdministrativa;

	private Integer[] idsMotivoRetirada;

	private Integer[] idsMotivoRetiradaSelecionados;

	private String mensagemQuadraInicialInexistente;

	private String mensagemQuadraFinalInexistente;

	private String campoOrigem;

	public String getIdComando(){

		return idComando;
	}

	public void setIdComando(String idComando){

		this.idComando = idComando;
	}

	public String getDescricaoComando(){

		return descricaoComando;
	}

	public void setDescricaoComando(String descricaoComando){

		this.descricaoComando = descricaoComando;
	}

	public Integer[] getIdsEmpresa(){

		return idsEmpresa;
	}

	public void setIdsEmpresa(Integer[] idsEmpresa){

		this.idsEmpresa = idsEmpresa;
	}

	public String getIdImovel(){

		return idImovel;
	}

	public void setIdImovel(String idImovel){

		this.idImovel = idImovel;
	}

	public String getIdCliente(){

		return idCliente;
	}

	public void setIdCliente(String idCliente){

		this.idCliente = idCliente;
	}

	public Integer[] getIdsGerenciaRegional(){

		return idsGerenciaRegional;
	}

	public void setIdsGerenciaRegional(Integer[] idsGerenciaRegional){

		this.idsGerenciaRegional = idsGerenciaRegional;
	}

	public Integer[] getIdsUnidadeNegocio(){

		return idsUnidadeNegocio;
	}

	public void setIdsUnidadeNegocio(Integer[] idsUnidadeNegocio){

		this.idsUnidadeNegocio = idsUnidadeNegocio;
	}

	public String getIdLocalidadeInicial(){

		return idLocalidadeInicial;
	}

	public void setIdLocalidadeInicial(String idLocalidadeInicial){

		this.idLocalidadeInicial = idLocalidadeInicial;
	}

	public String getDescricaoLocalidadeInicial(){

		return descricaoLocalidadeInicial;
	}

	public void setDescricaoLocalidadeInicial(String descricaoLocalidadeInicial){

		this.descricaoLocalidadeInicial = descricaoLocalidadeInicial;
	}

	public String getDescricaoSetorComercialInicial(){

		return descricaoSetorComercialInicial;
	}

	public void setDescricaoSetorComercialInicial(String descricaoSetorComercialInicial){

		this.descricaoSetorComercialInicial = descricaoSetorComercialInicial;
	}

	public String getNumeroQuadraInicial(){

		return numeroQuadraInicial;
	}

	public void setNumeroQuadraInicial(String numeroQuadraInicial){

		this.numeroQuadraInicial = numeroQuadraInicial;
	}

	public String getIdLocalidadeFinal(){

		return idLocalidadeFinal;
	}

	public void setIdLocalidadeFinal(String idLocalidadeFinal){

		this.idLocalidadeFinal = idLocalidadeFinal;
	}

	public String getDescricaoLocalidadeFinal(){

		return descricaoLocalidadeFinal;
	}

	public void setDescricaoLocalidadeFinal(String descricaoLocalidadeFinal){

		this.descricaoLocalidadeFinal = descricaoLocalidadeFinal;
	}

	public String getDescricaoSetorComercialFinal(){

		return descricaoSetorComercialFinal;
	}

	public void setDescricaoSetorComercialFinal(String descricaoSetorComercialFinal){

		this.descricaoSetorComercialFinal = descricaoSetorComercialFinal;
	}

	public String getNumeroQuadraFinal(){

		return numeroQuadraFinal;
	}

	public void setNumeroQuadraFinal(String numeroQuadraFinal){

		this.numeroQuadraFinal = numeroQuadraFinal;
	}

	public FormFile getArquivoMatricula(){

		return arquivoMatricula;
	}

	public void setArquivoMatricula(FormFile arquivoMatricula){

		this.arquivoMatricula = arquivoMatricula;
	}

	public String getArquivoDownload(){

		return arquivoDownload;
	}

	public void setArquivoDownload(String arquivoDownload){

		this.arquivoDownload = arquivoDownload;
	}

	public Collection<Integer> getIdsImoveis(){

		return idsImoveis;
	}

	public void setIdsImoveis(Collection<Integer> idsImoveis){

		this.idsImoveis = idsImoveis;
	}

	public String getEnderecoArquivoDownload(){

		return enderecoArquivoDownload;
	}

	public void setEnderecoArquivoDownload(String enderecoArquivoDownload){

		this.enderecoArquivoDownload = enderecoArquivoDownload;
	}

	public String getArquivoCarregado(){

		return arquivoCarregado;
	}

	public void setArquivoCarregado(String arquivoCarregado){

		this.arquivoCarregado = arquivoCarregado;
	}

	public String getValorDebitoInicial(){

		return valorDebitoInicial;
	}

	public void setValorDebitoInicial(String valorDebitoInicial){

		this.valorDebitoInicial = valorDebitoInicial;
	}

	public String getValorDebitoFinal(){

		return valorDebitoFinal;
	}

	public void setValorDebitoFinal(String valorDebitoFinal){

		this.valorDebitoFinal = valorDebitoFinal;
	}

	public String getPeriodoInclusaoInicial(){

		return periodoInclusaoInicial;
	}

	public void setPeriodoInclusaoInicial(String periodoInclusaoInicial){

		this.periodoInclusaoInicial = periodoInclusaoInicial;
	}

	public String getPeriodoInclusaoFinal(){

		return periodoInclusaoFinal;
	}

	public void setPeriodoInclusaoFinal(String periodoInclusaoFinal){

		this.periodoInclusaoFinal = periodoInclusaoFinal;
	}

	public String getPeriodoRetiradaInicial(){

		return periodoRetiradaInicial;
	}

	public void setPeriodoRetiradaInicial(String periodoRetiradaInicial){

		this.periodoRetiradaInicial = periodoRetiradaInicial;
	}

	public String getPeriodoRetiradaFinal(){

		return periodoRetiradaFinal;
	}

	public void setPeriodoRetiradaFinal(String periodoRetiradaFinal){

		this.periodoRetiradaFinal = periodoRetiradaFinal;
	}

	public String getIndicadorSituacaoCobrancaAdministrativa(){

		return indicadorSituacaoCobrancaAdministrativa;
	}

	public void setIndicadorSituacaoCobrancaAdministrativa(String indicadorSituacaoCobrancaAdministrativa){

		this.indicadorSituacaoCobrancaAdministrativa = indicadorSituacaoCobrancaAdministrativa;
	}

	public Integer[] getIdsMotivoRetirada(){

		return idsMotivoRetirada;
	}

	public void setIdsMotivoRetirada(Integer[] idsMotivoRetirada){

		this.idsMotivoRetirada = idsMotivoRetirada;
	}

	public String getInscricaoImovel(){

		return inscricaoImovel;
	}

	public void setInscricaoImovel(String inscricaoImovel){

		this.inscricaoImovel = inscricaoImovel;
	}

	public String getNomeCliente(){

		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente){

		this.nomeCliente = nomeCliente;
	}

	public String getCodigoSetorComercialInicial(){

		return codigoSetorComercialInicial;
	}

	public void setCodigoSetorComercialInicial(String codigoSetorComercialInicial){

		this.codigoSetorComercialInicial = codigoSetorComercialInicial;
	}

	public String getCodigoSetorComercialFinal(){

		return codigoSetorComercialFinal;
	}

	public void setCodigoSetorComercialFinal(String codigoSetorComercialFinal){

		this.codigoSetorComercialFinal = codigoSetorComercialFinal;
	}

	public String getMensagemQuadraInicialInexistente(){

		return mensagemQuadraInicialInexistente;
	}

	public void setMensagemQuadraInicialInexistente(String mensagemQuadraInicialInexistente){

		this.mensagemQuadraInicialInexistente = mensagemQuadraInicialInexistente;
	}

	public String getMensagemQuadraFinalInexistente(){

		return mensagemQuadraFinalInexistente;
	}

	public void setMensagemQuadraFinalInexistente(String mensagemQuadraFinalInexistente){

		this.mensagemQuadraFinalInexistente = mensagemQuadraFinalInexistente;
	}

	public Integer[] getIdsCategoria(){

		return idsCategoria;
	}

	public void setIdsCategoria(Integer[] idsCategoria){

		this.idsCategoria = idsCategoria;
	}

	public Integer[] getIdsSubcategoria(){

		return idsSubcategoria;
	}

	public void setIdsSubcategoria(Integer[] idsSubcategoria){

		this.idsSubcategoria = idsSubcategoria;
	}

	public Integer[] getIdsLigacaoAguaSituacao(){

		return idsLigacaoAguaSituacao;
	}

	public void setIdsLigacaoAguaSituacao(Integer[] idsLigacaoAguaSituacao){

		this.idsLigacaoAguaSituacao = idsLigacaoAguaSituacao;
	}

	public Integer[] getIdsLigacaoEsgotoSituacao(){

		return idsLigacaoEsgotoSituacao;
	}

	public void setIdsLigacaoEsgotoSituacao(Integer[] idsLigacaoEsgotoSituacao){

		this.idsLigacaoEsgotoSituacao = idsLigacaoEsgotoSituacao;
	}

	public Integer[] getIdsEmpresaSelecionadas(){

		return idsEmpresaSelecionadas;
	}

	public void setIdsEmpresaSelecionadas(Integer[] idsEmpresaSelecionadas){

		this.idsEmpresaSelecionadas = idsEmpresaSelecionadas;
	}

	public Integer[] getIdsGerenciaRegionalSelecionadas(){

		return idsGerenciaRegionalSelecionadas;
	}

	public void setIdsGerenciaRegionalSelecionadas(Integer[] idsGerenciaRegionalSelecionadas){

		this.idsGerenciaRegionalSelecionadas = idsGerenciaRegionalSelecionadas;
	}

	public Integer[] getIdsCategoriaSelecionadas(){

		return idsCategoriaSelecionadas;
	}

	public void setIdsCategoriaSelecionadas(Integer[] idsCategoriaSelecionadas){

		this.idsCategoriaSelecionadas = idsCategoriaSelecionadas;
	}

	public Integer[] getIdsSubcategoriaSelecionadas(){

		return idsSubcategoriaSelecionadas;
	}

	public void setIdsSubcategoriaSelecionadas(Integer[] idsSubcategoriaSelecionadas){

		this.idsSubcategoriaSelecionadas = idsSubcategoriaSelecionadas;
	}

	public Integer[] getIdsLigacaoAguaSituacaoSelecionadas(){

		return idsLigacaoAguaSituacaoSelecionadas;
	}

	public void setIdsLigacaoAguaSituacaoSelecionadas(Integer[] idsLigacaoAguaSituacaoSelecionadas){

		this.idsLigacaoAguaSituacaoSelecionadas = idsLigacaoAguaSituacaoSelecionadas;
	}

	public Integer[] getIdsLigacaoEsgotoSituacaoSelecionadas(){

		return idsLigacaoEsgotoSituacaoSelecionadas;
	}

	public void setIdsLigacaoEsgotoSituacaoSelecionadas(Integer[] idsLigacaoEsgotoSituacaoSelecionadas){

		this.idsLigacaoEsgotoSituacaoSelecionadas = idsLigacaoEsgotoSituacaoSelecionadas;
	}

	public Integer[] getIdsMotivoRetiradaSelecionados(){

		return idsMotivoRetiradaSelecionados;
	}

	public void setIdsMotivoRetiradaSelecionados(Integer[] idsMotivoRetiradaSelecionados){

		this.idsMotivoRetiradaSelecionados = idsMotivoRetiradaSelecionados;
	}

	public Integer[] getIdsUnidadeNegocioSelecionadas(){

		return idsUnidadeNegocioSelecionadas;
	}

	public void setIdsUnidadeNegocioSelecionadas(Integer[] idsUnidadeNegocioSelecionadas){

		this.idsUnidadeNegocioSelecionadas = idsUnidadeNegocioSelecionadas;
	}

	public String getCampoOrigem(){

		return campoOrigem;
	}

	public void setCampoOrigem(String campoOrigem){

		this.campoOrigem = campoOrigem;
	}

	public void reset(){

		this.setArquivoCarregado("");
		this.setArquivoDownload("");
		this.setArquivoMatricula(null);
		this.setIdsImoveis(null);
		this.setEnderecoArquivoDownload("");
		this.setIdsEmpresaSelecionadas(null);
		this.setIdsGerenciaRegionalSelecionadas(null);
		this.setIdsUnidadeNegocioSelecionadas(null);
		this.setIdsCategoriaSelecionadas(null);
		this.setIdsSubcategoriaSelecionadas(null);
		this.setIdsLigacaoAguaSituacaoSelecionadas(null);
		this.setIdsLigacaoEsgotoSituacaoSelecionadas(null);
		this.setIdsMotivoRetiradaSelecionados(null);
		this.setIdComando(null);
		this.setDescricaoComando(null);
		this.setIdImovel(null);
		this.setInscricaoImovel(null);
		this.setIdCliente(null);
		this.setNomeCliente(null);
		this.setValorDebitoInicial(null);
		this.setValorDebitoFinal(null);
		this.setIdLocalidadeInicial(null);
		this.setDescricaoLocalidadeInicial(null);
		this.setIdLocalidadeFinal(null);
		this.setDescricaoLocalidadeFinal(null);
		this.setCodigoSetorComercialInicial(null);
		this.setDescricaoSetorComercialInicial(null);
		this.setCodigoSetorComercialFinal(null);
		this.setDescricaoSetorComercialFinal(null);
		this.setNumeroQuadraInicial(null);
		this.setMensagemQuadraInicialInexistente(null);
		this.setNumeroQuadraFinal(null);
		this.setMensagemQuadraFinalInexistente(null);
		this.setPeriodoInclusaoInicial(null);
		this.setPeriodoInclusaoFinal(null);
		this.setPeriodoRetiradaInicial(null);
		this.setPeriodoRetiradaInicial(null);
		this.setIndicadorSituacaoCobrancaAdministrativa(ConstantesSistema.TODOS.toString());
	}
}
