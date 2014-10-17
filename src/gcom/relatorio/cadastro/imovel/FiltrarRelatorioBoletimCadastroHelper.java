
package gcom.relatorio.cadastro.imovel;

import java.io.Serializable;

/**
 * Este caso de uso permite a emissão de boletins de cadastro
 * [UC0582] Emitir Boletim de Cadastro pelo
 * [UC0164] - Filtrar Imóvel por Outros Critérios
 * 
 * @author Anderson Italo
 * @date 26/04/2011
 */
public class FiltrarRelatorioBoletimCadastroHelper
				implements Serializable {

	private static final long serialVersionUID = 1L;

	private String idSituacaoLigacaoAgua;

	private String idSituacaoLigacaoEsgoto;

	private String idImovelPerfil;

	private String idGerenciaRegional;

	private String idLocalidadeInicial;

	private String idLocalidadeFinal;

	private String codigoSetorInicial;

	private String codigoSetorFinal;

	private String quadraInicial;

	private String quadraFinal;

	private String loteInicial;

	private String loteFinal;

	private String cep;

	private String idLogradouro;

	private String codigoBairro;

	private String idMunicipio;

	private String idTipoMedicao;

	private String indicadorMedicao;

	private String idSubCategoria;

	private String idCategoria;

	private String idCliente;

	private String idClienteTipo;

	private String idClienteRelacaoTipo;

	private String idUnidadeNegocio;

	private String codigoRotaInicial;

	private String codigoRotaFinal;

	private String informouDadosCliente;

	private String informouDadosLigacao;

	private String informouDadosCaracteristicas;

	private String informouDadosLocalizacao;

	private String segmentoInicial;

	private String segmentoFinal;

	private String subLoteInicial;

	private String subLoteFinal;

	public FiltrarRelatorioBoletimCadastroHelper() {

	}

	public String getIdSituacaoLigacaoAgua(){

		return idSituacaoLigacaoAgua;
	}

	public void setIdSituacaoLigacaoAgua(String idSituacaoLigacaoAgua){

		this.idSituacaoLigacaoAgua = idSituacaoLigacaoAgua;
	}

	public String getIdSituacaoLigacaoEsgoto(){

		return idSituacaoLigacaoEsgoto;
	}

	public void setIdSituacaoLigacaoEsgoto(String idSituacaoLigacaoEsgoto){

		this.idSituacaoLigacaoEsgoto = idSituacaoLigacaoEsgoto;
	}

	public String getIdImovelPerfil(){

		return idImovelPerfil;
	}

	public void setIdImovelPerfil(String idImovelPerfil){

		this.idImovelPerfil = idImovelPerfil;
	}

	public String getIdGerenciaRegional(){

		return idGerenciaRegional;
	}

	public void setIdGerenciaRegional(String idGerenciaRegional){

		this.idGerenciaRegional = idGerenciaRegional;
	}

	public String getIdLocalidadeInicial(){

		return idLocalidadeInicial;
	}

	public void setIdLocalidadeInicial(String idLocalidadeInicial){

		this.idLocalidadeInicial = idLocalidadeInicial;
	}

	public String getIdLocalidadeFinal(){

		return idLocalidadeFinal;
	}

	public void setIdLocalidadeFinal(String idLocalidadeFinal){

		this.idLocalidadeFinal = idLocalidadeFinal;
	}

	public String getCodigoSetorInicial(){

		return codigoSetorInicial;
	}

	public void setCodigoSetorInicial(String codigoSetorInicial){

		this.codigoSetorInicial = codigoSetorInicial;
	}

	public String getCodigoSetorFinal(){

		return codigoSetorFinal;
	}

	public void setCodigoSetorFinal(String codigoSetorFinal){

		this.codigoSetorFinal = codigoSetorFinal;
	}

	public String getQuadraInicial(){

		return quadraInicial;
	}

	public void setQuadraInicial(String quadraInicial){

		this.quadraInicial = quadraInicial;
	}

	public String getQuadraFinal(){

		return quadraFinal;
	}

	public void setQuadraFinal(String quadraFinal){

		this.quadraFinal = quadraFinal;
	}

	public String getLoteInicial(){

		return loteInicial;
	}

	public void setLoteInicial(String loteInicial){

		this.loteInicial = loteInicial;
	}

	public String getLoteFinal(){

		return loteFinal;
	}

	public void setLoteFinal(String loteFinal){

		this.loteFinal = loteFinal;
	}

	public String getCep(){

		return cep;
	}

	public void setCep(String cep){

		this.cep = cep;
	}

	public String getIdLogradouro(){

		return idLogradouro;
	}

	public void setIdLogradouro(String idLogradouro){

		this.idLogradouro = idLogradouro;
	}

	public String getCodigoBairro(){

		return codigoBairro;
	}

	public void setCodigoBairro(String codigoBairro){

		this.codigoBairro = codigoBairro;
	}

	public String getIdMunicipio(){

		return idMunicipio;
	}

	public void setIdMunicipio(String idMunicipio){

		this.idMunicipio = idMunicipio;
	}

	public String getIdTipoMedicao(){

		return idTipoMedicao;
	}

	public void setIdTipoMedicao(String idTipoMedicao){

		this.idTipoMedicao = idTipoMedicao;
	}

	public String getIndicadorMedicao(){

		return indicadorMedicao;
	}

	public void setIndicadorMedicao(String indicadorMedicao){

		this.indicadorMedicao = indicadorMedicao;
	}

	public String getIdSubCategoria(){

		return idSubCategoria;
	}

	public void setIdSubCategoria(String idSubCategoria){

		this.idSubCategoria = idSubCategoria;
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

	public String getIdClienteTipo(){

		return idClienteTipo;
	}

	public void setIdClienteTipo(String idClienteTipo){

		this.idClienteTipo = idClienteTipo;
	}

	public String getIdClienteRelacaoTipo(){

		return idClienteRelacaoTipo;
	}

	public void setIdClienteRelacaoTipo(String idClienteRelacaoTipo){

		this.idClienteRelacaoTipo = idClienteRelacaoTipo;
	}

	public String getIdUnidadeNegocio(){

		return idUnidadeNegocio;
	}

	public void setIdUnidadeNegocio(String idUnidadeNegocio){

		this.idUnidadeNegocio = idUnidadeNegocio;
	}

	public String getInformouDadosCliente(){

		return informouDadosCliente;
	}

	public void setInformouDadosCliente(String informouDadosCliente){

		this.informouDadosCliente = informouDadosCliente;
	}

	public String getCodigoRotaInicial(){

		return codigoRotaInicial;
	}

	public void setCodigoRotaInicial(String codigoRotaInicial){

		this.codigoRotaInicial = codigoRotaInicial;
	}

	public String getCodigoRotaFinal(){

		return codigoRotaFinal;
	}

	public void setCodigoRotaFinal(String codigoRotaFinal){

		this.codigoRotaFinal = codigoRotaFinal;
	}

	public String getInformouDadosLigacao(){

		return informouDadosLigacao;
	}

	public void setInformouDadosLigacao(String informouDadosLigacao){

		this.informouDadosLigacao = informouDadosLigacao;
	}

	public String getInformouDadosCaracteristicas(){

		return informouDadosCaracteristicas;
	}

	public void setInformouDadosCaracteristicas(String informouDadosCaracteristicas){

		this.informouDadosCaracteristicas = informouDadosCaracteristicas;
	}

	public String getInformouDadosLocalizacao(){

		return informouDadosLocalizacao;
	}

	public void setInformouDadosLocalizacao(String informouDadosLocalizacao){

		this.informouDadosLocalizacao = informouDadosLocalizacao;
	}

	public String getSegmentoInicial(){

		return segmentoInicial;
	}

	public void setSegmentoInicial(String segmentoInicial){

		this.segmentoInicial = segmentoInicial;
	}

	public String getSegmentoFinal(){

		return segmentoFinal;
	}

	public void setSegmentoFinal(String segmentoFinal){

		this.segmentoFinal = segmentoFinal;
	}

	public String getSubLoteInicial(){

		return subLoteInicial;
	}

	public void setSubLoteInicial(String subLoteInicial){

		this.subLoteInicial = subLoteInicial;
	}

	public String getSubLoteFinal(){

		return subLoteFinal;
	}

	public void setSubLoteFinal(String subLoteFinal){

		this.subLoteFinal = subLoteFinal;
	}
}
