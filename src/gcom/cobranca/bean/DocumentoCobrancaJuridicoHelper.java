
package gcom.cobranca.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class DocumentoCobrancaJuridicoHelper
				implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer idImovel; // 01

	private String inscricao; // 02

	private Integer qtdDebito; // 03

	private BigDecimal valorDebito; // 04

	private String nomeCliente; // 05

	private Integer grupoCobranca; // 06

	private Integer setorComercial; // 07

	private String roteiro; // 08

	private String enderecoImovel; // 09

	private String bairro; // 10

	private String cep; // 11

	private String enderecoEntrega; // 12

	private String ligacaoSituacao; // 13

	private String imovelCategoria; // 14

	private String celular; // 15

	private String foneRecado; // 16

	private String foneComercial; // 17

	private String foneResidencial; // 18

	private String documentoTipo1; // 19

	private String documentoNumero1; // 20

	private String documentoTipo2; // 21

	private String documentoNumero2; // 22

	private String debitoTipo; // 23

	private Integer idImovelPerfil; // 24

	private String descricaoImovelPerfil; // 24

	private String ligacaoData; // 25

	private String ligacaoTipo; // 26

	private String medidorNumero; // 27

	private String codigoBarras; // 28

	private String indicadorDocumentoEmitido; // 29

	private String dataEmissaoDocumento; // 30

	private String execucaoOSSituacao; // 31

	private String dataExecucao; // 32

	private String ultimaLeitura; // 33

	private Integer linhaSequencial; // 34

	private Integer imovelNumero; // 35

	private Integer idLocalidade;

	private Integer idDocumentoTipo;

	private Integer numeroSequenciaDocumento;

	private Integer idCobrancaDocumento;

	public Integer getIdImovel(){

		return idImovel;
	}

	public void setIdImovel(Integer idImovel){

		this.idImovel = idImovel;
	}

	public String getInscricao(){

		return inscricao;
	}

	public void setInscricao(String inscricao){

		this.inscricao = inscricao;
	}

	public Integer getQtdDebito(){

		return qtdDebito;
	}

	public void setQtdDebito(Integer qtdDebito){

		this.qtdDebito = qtdDebito;
	}

	public BigDecimal getValorDebito(){

		return valorDebito;
	}

	public void setValorDebito(BigDecimal valorDebito){

		this.valorDebito = valorDebito;
	}

	public String getNomeCliente(){

		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente){

		this.nomeCliente = nomeCliente;
	}

	public Integer getGrupoCobranca(){

		return grupoCobranca;
	}

	public void setGrupoCobranca(Integer grupoCobranca){

		this.grupoCobranca = grupoCobranca;
	}

	public Integer getSetorComercial(){

		return setorComercial;
	}

	public void setSetorComercial(Integer setorComercial){

		this.setorComercial = setorComercial;
	}

	public String getRoteiro(){

		return roteiro;
	}

	public void setRoteiro(String roteiro){

		this.roteiro = roteiro;
	}

	public String getEnderecoImovel(){

		return enderecoImovel;
	}

	public void setEnderecoImovel(String enderecoImovel){

		this.enderecoImovel = enderecoImovel;
	}

	public String getBairro(){

		return bairro;
	}

	public void setBairro(String bairro){

		this.bairro = bairro;
	}

	public String getCep(){

		return cep;
	}

	public void setCep(String cep){

		this.cep = cep;
	}

	public String getEnderecoEntrega(){

		return enderecoEntrega;
	}

	public void setEnderecoEntrega(String enderecoEntrega){

		this.enderecoEntrega = enderecoEntrega;
	}

	public String getLigacaoSituacao(){

		return ligacaoSituacao;
	}

	public void setLigacaoSituacao(String ligacaoSituacao){

		this.ligacaoSituacao = ligacaoSituacao;
	}

	public String getImovelCategoria(){

		return imovelCategoria;
	}

	public void setImovelCategoria(String imovelCategoria){

		this.imovelCategoria = imovelCategoria;
	}

	public String getCelular(){

		if(celular != null){
			return celular;
		}else{
			return "";
		}

	}

	public void setCelular(String celular){

		this.celular = celular;
	}

	public String getFoneRecado(){

		if(foneRecado != null){
			return foneRecado;
		}else{
			return "";
		}

	}

	public void setFoneRecado(String foneRecado){

		this.foneRecado = foneRecado;
	}

	public String getFoneComercial(){

		if(foneComercial != null){
			return foneComercial;
		}else{
			return "";
		}

	}

	public void setFoneComercial(String foneComercial){

		this.foneComercial = foneComercial;
	}

	public String getFoneResidencial(){

		if(foneResidencial != null){
			return foneResidencial;
		}else{
			return "";
		}

	}

	public void setFoneResidencial(String foneResidencial){

		this.foneResidencial = foneResidencial;
	}

	public String getDocumentoTipo1(){

		return documentoTipo1;
	}

	public void setDocumentoTipo1(String documentoTipo1){

		this.documentoTipo1 = documentoTipo1;
	}

	public String getDocumentoNumero1(){

		return documentoNumero1;
	}

	public void setDocumentoNumero1(String documentoNumero1){

		this.documentoNumero1 = documentoNumero1;
	}

	public String getDocumentoTipo2(){

		return documentoTipo2;
	}

	public void setDocumentoTipo2(String documentoTipo2){

		this.documentoTipo2 = documentoTipo2;
	}

	public String getDocumentoNumero2(){

		return documentoNumero2;
	}

	public void setDocumentoNumero2(String documentoNumero2){

		this.documentoNumero2 = documentoNumero2;
	}

	public String getDebitoTipo(){

		return debitoTipo;
	}

	public void setDebitoTipo(String debitoTipo){

		this.debitoTipo = debitoTipo;
	}

	public Integer getIdImovelPerfil(){

		return idImovelPerfil;
	}

	public void setIdImovelPerfil(Integer idImovelPerfil){

		this.idImovelPerfil = idImovelPerfil;
	}

	public String getLigacaoData(){

		return ligacaoData;
	}

	public void setLigacaoData(String ligacaoData){

		this.ligacaoData = ligacaoData;
	}

	public String getLigacaoTipo(){

		return ligacaoTipo;
	}

	public void setLigacaoTipo(String ligacaoTipo){

		this.ligacaoTipo = ligacaoTipo;
	}

	public String getMedidorNumero(){

		return medidorNumero;
	}

	public void setMedidorNumero(String medidorNumero){

		this.medidorNumero = medidorNumero;
	}

	public String getCodigoBarras(){

		return codigoBarras;
	}

	public void setCodigoBarras(String codigoBarras){

		this.codigoBarras = codigoBarras;
	}

	public String getIndicadorDocumentoEmitido(){

		return indicadorDocumentoEmitido;
	}

	public void setIndicadorDocumentoEmitido(String indicadorDocumentoEmitido){

		this.indicadorDocumentoEmitido = indicadorDocumentoEmitido;
	}

	public String getDataEmissaoDocumento(){

		return dataEmissaoDocumento;
	}

	public void setDataEmissaoDocumento(String dataEmissaoDocumento){

		this.dataEmissaoDocumento = dataEmissaoDocumento;
	}

	public String getExecucaoOSSituacao(){

		return execucaoOSSituacao;
	}

	public void setExecucaoOSSituacao(String execucaoOSSituacao){

		this.execucaoOSSituacao = execucaoOSSituacao;
	}

	public String getDataExecucao(){

		return dataExecucao;
	}

	public void setDataExecucao(String dataExecucao){

		this.dataExecucao = dataExecucao;
	}

	public String getUltimaLeitura(){

		return ultimaLeitura;
	}

	public void setUltimaLeitura(String ultimaLeitura){

		this.ultimaLeitura = ultimaLeitura;
	}

	public Integer getLinhaSequencial(){

		return linhaSequencial;
	}

	public void setLinhaSequencial(Integer linhaSequencial){

		this.linhaSequencial = linhaSequencial;
	}

	public Integer getImovelNumero(){

		return imovelNumero;
	}

	public void setImovelNumero(Integer imovelNumero){

		this.imovelNumero = imovelNumero;
	}

	public String getDescricaoImovelPerfil(){

		return descricaoImovelPerfil;
	}

	public void setDescricaoImovelPerfil(String descricaoImovelPerfil){

		this.descricaoImovelPerfil = descricaoImovelPerfil;
	}

	public Integer getIdLocalidade(){

		return idLocalidade;
	}

	public void setIdLocalidade(Integer idLocalidade){

		this.idLocalidade = idLocalidade;
	}

	public Integer getIdDocumentoTipo(){

		return idDocumentoTipo;
	}

	public void setIdDocumentoTipo(Integer idDocumentoTipo){

		this.idDocumentoTipo = idDocumentoTipo;
	}

	public Integer getNumeroSequenciaDocumento(){

		return numeroSequenciaDocumento;
	}

	public void setNumeroSequenciaDocumento(Integer numeroSequenciaDocumento){

		this.numeroSequenciaDocumento = numeroSequenciaDocumento;
	}

	public Integer getIdCobrancaDocumento(){

		return idCobrancaDocumento;
	}

	public void setIdCobrancaDocumento(Integer idCobrancaDocumento){

		this.idCobrancaDocumento = idCobrancaDocumento;
	}

}
