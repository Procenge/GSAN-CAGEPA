
package gcom.gui.cobranca.prescricao;

import java.util.Collection;

import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.ValidatorActionForm;

/**
 * [UC3118] Inserir Comando de Simulação de Faturamento
 * 
 * @author Anderson Italo
 * @date 17/12/2013
 */
public class ComandarPrescricaoDebitosActionForm
				extends ValidatorActionForm {

	private static final long serialVersionUID = 1L;

	private String titulo;

	private String descricaoSolicitacao;

	private String indicadorSimulacao;

	private String idGerenciaRegional;

	private String idUnidadeNegocio;
	
	private String idLocalidadeElo;

	private String descricaoLocalidadeElo;

	private String idLocalidadeInicial;

	private String descricaoLocalidadeInicial;

	private String codigoSetorComercialInicial;

	private String descricaoSetorComercialInicial;

	private String idLocalidadeFinal;

	private String descricaoLocalidadeFinal;

	private String codigoSetorComercialFinal;

	private String descricaoSetorComercialFinal;

	private String numeroQuadraInicial;

	private String numeroQuadraFinal;

	private String idCliente;

	private String nomeCliente;

	private String idClienteRelacaoTipo;

	private String periodoRelacionamentoInicial;

	private String periodoRelacionamentoFinal;

	private String[] idCategoria;

	private String[] idSubCategoria;

	private String[] idLigacaoAguaSituacao;

	private String[] idLigacaoEsgotoSituacao;

	private String[] idCobrancaSituacao;

	private String periodoReferenciaDebitoInicial;

	private String periodoReferenciaDebitoFinal;

	private String periodoVencimentoDebitoInicial;

	private String periodoVencimentoDebitoFinal;

	private FormFile arquivoMatricula;

	private String arquivoDownload;

	private Collection<Integer> idsImoveis;

	private String enderecoArquivoDownload;

	private String arquivoCarregado;

	private byte[] arquivo;

	public String getIdGerenciaRegional(){

		return idGerenciaRegional;
	}

	public void setIdGerenciaRegional(String idGerenciaRegional){

		this.idGerenciaRegional = idGerenciaRegional;
	}

	public String getIdUnidadeNegocio(){

		return idUnidadeNegocio;
	}

	public void setIdUnidadeNegocio(String idUnidadeNegocio){

		this.idUnidadeNegocio = idUnidadeNegocio;
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

	public String getCodigoSetorComercialInicial(){

		return codigoSetorComercialInicial;
	}

	public void setCodigoSetorComercialInicial(String codigoSetorComercialInicial){

		this.codigoSetorComercialInicial = codigoSetorComercialInicial;
	}

	public String getDescricaoSetorComercialInicial(){

		return descricaoSetorComercialInicial;
	}

	public void setDescricaoSetorComercialInicial(String descricaoSetorComercialInicial){

		this.descricaoSetorComercialInicial = descricaoSetorComercialInicial;
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

	public String getCodigoSetorComercialFinal(){

		return codigoSetorComercialFinal;
	}

	public void setCodigoSetorComercialFinal(String codigoSetorComercialFinal){

		this.codigoSetorComercialFinal = codigoSetorComercialFinal;
	}

	public String getDescricaoSetorComercialFinal(){

		return descricaoSetorComercialFinal;
	}

	public void setDescricaoSetorComercialFinal(String descricaoSetorComercialFinal){

		this.descricaoSetorComercialFinal = descricaoSetorComercialFinal;
	}

	public String getNumeroQuadraInicial(){

		return numeroQuadraInicial;
	}

	public void setNumeroQuadraInicial(String numeroQuadraInicial){

		this.numeroQuadraInicial = numeroQuadraInicial;
	}

	public String getNumeroQuadraFinal(){

		return numeroQuadraFinal;
	}

	public void setNumeroQuadraFinal(String numeroQuadraFinal){

		this.numeroQuadraFinal = numeroQuadraFinal;
	}

	public String getIndicadorSimulacao(){

		return indicadorSimulacao;
	}

	public void setIndicadorSimulacao(String indicadorSimulacao){

		this.indicadorSimulacao = indicadorSimulacao;
	}

	public String getIdClienteRelacaoTipo(){

		return idClienteRelacaoTipo;
	}

	public void setIdClienteRelacaoTipo(String idClienteRelacaoTipo){

		this.idClienteRelacaoTipo = idClienteRelacaoTipo;
	}

	public String getTitulo(){

		return titulo;
	}

	public void setTitulo(String titulo){

		this.titulo = titulo;
	}

	public String getDescricaoSolicitacao(){

		return descricaoSolicitacao;
	}

	public void setDescricaoSolicitacao(String descricaoSolicitacao){

		this.descricaoSolicitacao = descricaoSolicitacao;
	}

	public String getIdLocalidadeElo(){

		return idLocalidadeElo;
	}

	public void setIdLocalidadeElo(String idLocalidadeElo){

		this.idLocalidadeElo = idLocalidadeElo;
	}

	public String getDescricaoLocalidadeElo(){

		return descricaoLocalidadeElo;
	}

	public void setDescricaoLocalidadeElo(String descricaoLocalidadeElo){

		this.descricaoLocalidadeElo = descricaoLocalidadeElo;
	}

	public String getIdCliente(){

		return idCliente;
	}

	public void setIdCliente(String idCliente){

		this.idCliente = idCliente;
	}

	public String getNomeCliente(){

		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente){

		this.nomeCliente = nomeCliente;
	}

	public String getPeriodoRelacionamentoInicial(){

		return periodoRelacionamentoInicial;
	}

	public void setPeriodoRelacionamentoInicial(String periodoRelacionamentoInicial){

		this.periodoRelacionamentoInicial = periodoRelacionamentoInicial;
	}

	public String getPeriodoRelacionamentoFinal(){

		return periodoRelacionamentoFinal;
	}

	public void setPeriodoRelacionamentoFinal(String periodoRelacionamentoFinal){

		this.periodoRelacionamentoFinal = periodoRelacionamentoFinal;
	}

	public String[] getIdCategoria(){

		return idCategoria;
	}

	public void setIdCategoria(String[] idCategoria){

		this.idCategoria = idCategoria;
	}
	
	public String[] getIdLigacaoAguaSituacao(){

		return idLigacaoAguaSituacao;
	}

	public void setIdLigacaoAguaSituacao(String[] idLigacaoAguaSituacao){

		this.idLigacaoAguaSituacao = idLigacaoAguaSituacao;
	}

	public String[] getIdLigacaoEsgotoSituacao(){

		return idLigacaoEsgotoSituacao;
	}

	public void setIdLigacaoEsgotoSituacao(String[] idLigacaoEsgotoSituacao){

		this.idLigacaoEsgotoSituacao = idLigacaoEsgotoSituacao;
	}

	public String getPeriodoReferenciaDebitoInicial(){

		return periodoReferenciaDebitoInicial;
	}

	public void setPeriodoReferenciaDebitoInicial(String periodoReferenciaDebitoInicial){

		this.periodoReferenciaDebitoInicial = periodoReferenciaDebitoInicial;
	}

	public String getPeriodoReferenciaDebitoFinal(){

		return periodoReferenciaDebitoFinal;
	}

	public void setPeriodoReferenciaDebitoFinal(String periodoReferenciaDebitoFinal){

		this.periodoReferenciaDebitoFinal = periodoReferenciaDebitoFinal;
	}

	public String getPeriodoVencimentoDebitoInicial(){

		return periodoVencimentoDebitoInicial;
	}

	public void setPeriodoVencimentoDebitoInicial(String periodoVencimentoDebitoInicial){

		this.periodoVencimentoDebitoInicial = periodoVencimentoDebitoInicial;
	}

	public String getPeriodoVencimentoDebitoFinal(){

		return periodoVencimentoDebitoFinal;
	}

	public void setPeriodoVencimentoDebitoFinal(String periodoVencimentoDebitoFinal){

		this.periodoVencimentoDebitoFinal = periodoVencimentoDebitoFinal;
	}

	public String[] getIdCobrancaSituacao(){

		return idCobrancaSituacao;
	}

	public void setIdCobrancaSituacao(String[] idCobrancaSituacao){

		this.idCobrancaSituacao = idCobrancaSituacao;
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

	public byte[] getArquivo(){

		return arquivo;
	}

	public void setArquivo(byte[] arquivo){

		this.arquivo = arquivo;
	}

	public String[] getIdSubCategoria(){

		return idSubCategoria;
	}

	public void setIdSubCategoria(String[] idSubCategoria){

		this.idSubCategoria = idSubCategoria;
	}

}
