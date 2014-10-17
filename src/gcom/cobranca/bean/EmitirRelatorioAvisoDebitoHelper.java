package gcom.cobranca.bean;

import gcom.cadastro.imovel.Imovel;
import gcom.util.Util;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

public class EmitirRelatorioAvisoDebitoHelper
				implements Serializable, IEmitirRelatorioAvisoDebitoHelper {

	private static final long serialVersionUID = 1L;

	private Integer idCobrancaDocumento;

	private Integer idLocalidade;

	private String descricaoLocalidade;

	private Integer idImovel;

	private Integer idSetorComercial;

	private Integer codigoSetorComercial;

	private Integer idQuadra;

	private Integer numeroQuadra;

	private Short numeroLote;

	private Short numeroSubLote;

	private String inscricaoImovel;

	private String enderecoFormatado;

	private String nomeClienteUsuario;

	private String bairroImovel;

	private Date dataEmissao;

	private Short quatidadeDiasRealizacao;

	private String dataTextoCliente;

	private Integer idOSP;

	private String numeroHidrometro;

	private Collection<CobrancaDocumentoItemHelper> colecaoItemConta;

	private int sequencialImpressao;

	private int totalContasImpressao;

	private Integer numeroPagina;

	private BigDecimal valorDocumento;

	private Integer documentoTipoId;

	private String representacaoNumericaCodBarra;

	private String representacaoNumericaCodBarraSemDigito;

	private Integer numeroSequenciaDocumento;

	private Integer quantidadeContasEmDebito;

	public BigDecimal valorTotalDebitosCorrigidos;


	/**
	 * Construtor que é utilizado no retorno da consulta do Relatório PDF Aviso de Corte modelo 5.
	 * 
	 * @param idLocalidade
	 * @param localidadeDescricao
	 * @param idImovel
	 * @param idSetorComercial
	 * @param codigoSetorComercial
	 * @param idQuadra
	 * @param numeroQuadra
	 * @param numeroLote
	 * @param numeroSubLote
	 * @param nomeCliente
	 * @param bairroImovel
	 * @param dataEmissao
	 * @param quatidadeDiasRealizacao
	 * @param numeroHidrometro
	 */
	public EmitirRelatorioAvisoDebitoHelper(Integer idCobrancaDocumento, Integer idLocalidade, String descricaoLocalidade,
											Integer idImovel, Integer idSetorComercial, Integer codigoSetorComercial, Integer idQuadra,
											Integer numeroQuadra, Short numeroLote, Short numeroSubLote, String nomeClienteUsuario,
											String bairroImovel, Date dataEmissao, Short quatidadeDiasRealizacao, Integer idOSP,
											String numeroHidrometro, BigDecimal valorDocumento, Integer documentoTipoId,
											String representacaoNumericaCodBarra, String representacaoNumericaCodBarraSemDigito) {

		super();
		this.idCobrancaDocumento = idCobrancaDocumento;
		this.idLocalidade = idLocalidade;
		this.descricaoLocalidade = descricaoLocalidade;
		this.idImovel = idImovel;
		this.idSetorComercial = idSetorComercial;
		this.codigoSetorComercial = codigoSetorComercial;
		this.idQuadra = idQuadra;
		this.numeroQuadra = numeroQuadra;
		this.numeroLote = numeroLote;
		this.numeroSubLote = numeroSubLote;
		this.nomeClienteUsuario = nomeClienteUsuario;
		this.bairroImovel = bairroImovel;
		this.dataEmissao = dataEmissao;
		this.quatidadeDiasRealizacao = quatidadeDiasRealizacao;
		this.idOSP = idOSP;
		this.numeroHidrometro = numeroHidrometro;
		this.valorDocumento = valorDocumento;
		this.documentoTipoId = documentoTipoId;
		this.representacaoNumericaCodBarra = representacaoNumericaCodBarra;
		this.representacaoNumericaCodBarraSemDigito = representacaoNumericaCodBarraSemDigito;
	}

	public EmitirRelatorioAvisoDebitoHelper(Integer idCobrancaDocumento, Integer idLocalidade, String descricaoLocalidade,
											Integer idImovel, Integer idSetorComercial, Integer codigoSetorComercial, Integer idQuadra,
											Integer numeroQuadra, Short numeroLote, Short numeroSubLote, String nomeClienteUsuario,
											String bairroImovel, Date dataEmissao, Short quatidadeDiasRealizacao, Integer idOSP,
											String numeroHidrometro, BigDecimal valorDocumento, Integer documentoTipoId,
											Integer numeroSequenciaDocumento) {

		super();
		this.idCobrancaDocumento = idCobrancaDocumento;
		this.idLocalidade = idLocalidade;
		this.descricaoLocalidade = descricaoLocalidade;
		this.idImovel = idImovel;
		this.idSetorComercial = idSetorComercial;
		this.codigoSetorComercial = codigoSetorComercial;
		this.idQuadra = idQuadra;
		this.numeroQuadra = numeroQuadra;
		this.numeroLote = numeroLote;
		this.numeroSubLote = numeroSubLote;
		this.nomeClienteUsuario = nomeClienteUsuario;
		this.bairroImovel = bairroImovel;
		this.dataEmissao = dataEmissao;
		this.quatidadeDiasRealizacao = quatidadeDiasRealizacao;
		this.idOSP = idOSP;
		this.numeroHidrometro = numeroHidrometro;
		this.valorDocumento = valorDocumento;
		this.documentoTipoId = documentoTipoId;
		this.numeroSequenciaDocumento = numeroSequenciaDocumento;

	}
	/**
	 * @return the idCobrancaDocumento
	 */
	public Integer getIdCobrancaDocumento(){

		return idCobrancaDocumento;
	}

	/**
	 * @param idCobrancaDocumento
	 *            the idCobrancaDocumento to set
	 */
	public void setIdCobrancaDocumento(Integer idCobrancaDocumento){

		this.idCobrancaDocumento = idCobrancaDocumento;
	}

	/**
	 * @return the idLocalidade
	 */
	public Integer getIdLocalidade(){

		return idLocalidade;
	}


	/**
	 * @param idLocalidade
	 *            the idLocalidade to set
	 */
	public void setIdLocalidade(Integer idLocalidade){

		this.idLocalidade = idLocalidade;
	}


	/**
	 * @return the localidadeDescricao
	 */
	public String getDescricaoLocalidade(){

		return descricaoLocalidade;
	}

	
	/**
	 * @param localidadeDescricao
	 *            the localidadeDescricao to set
	 */
	public void setDescricaoLocalidade(String descricaoLocalidade){

		this.descricaoLocalidade = descricaoLocalidade;
	}

	
	/**
	 * @return the idImovel
	 */
	public Integer getIdImovel(){

		return idImovel;
	}


	/**
	 * @param idImovel
	 *            the idImovel to set
	 */
	public void setIdImovel(Integer idImovel){

		this.idImovel = idImovel;
	}

	/**
	 * @return the idSetorComercial
	 */
	public Integer getIdSetorComercial(){

		return idSetorComercial;
	}

	/**
	 * @param idSetorComercial
	 *            the idSetorComercial to set
	 */
	public void setIdSetorComercial(Integer idSetorComercial){

		this.idSetorComercial = idSetorComercial;
	}

	/**
	 * @return the codigoSetroComercial
	 */
	public Integer getCodigoSetorComercial(){

		return codigoSetorComercial;
	}

	/**
	 * @param codigoSetroComercial
	 *            the codigoSetroComercial to set
	 */
	public void setCodigoSetorComercial(Integer codigoSetroComercial){

		this.codigoSetorComercial = codigoSetroComercial;
	}

	/**
	 * @return the idQuadra
	 */
	public Integer getIdQuadra(){

		return idQuadra;
	}

	/**
	 * @param idQuadra
	 *            the idQuadra to set
	 */
	public void setIdQuadra(Integer idQuadra){

		this.idQuadra = idQuadra;
	}

	/**
	 * @return the numeroQuadra
	 */
	public Integer getNumeroQuadra(){

		return numeroQuadra;
	}

	/**
	 * @param numeroQuadra
	 *            the numeroQuadra to set
	 */
	public void setNumeroQuadra(Integer numeroQuadra){

		this.numeroQuadra = numeroQuadra;
	}

	/**
	 * @return the numeroLote
	 */
	public Short getNumeroLote(){

		return numeroLote;
	}

	/**
	 * @param numeroLote
	 *            the numeroLote to set
	 */
	public void setNumeroLote(Short numeroLote){

		this.numeroLote = numeroLote;
	}

	/**
	 * @return the numeroSubLote
	 */
	public Short getNumeroSubLote(){

		return numeroSubLote;
	}

	/**
	 * @param numeroSubLote
	 *            the numeroSubLote to set
	 */
	public void setNumeroSubLote(Short numeroSubLote){

		this.numeroSubLote = numeroSubLote;
	}


	/**
	 * @return the inscricaoImovel
	 */
	public String getInscricaoImovel(){

		Imovel imovel = new Imovel();
		imovel.setInscricaoFormatada(getIdLocalidade(), getCodigoSetorComercial(), getNumeroQuadra(), getNumeroLote(), getNumeroSubLote());
		return imovel.getInscricaoFormatada();

	}


	/**
	 * @param inscricaoImovel
	 *            the inscricaoImovel to set
	 */
	public void setInscricaoImovel(String inscricaoImovel){

		this.inscricaoImovel = inscricaoImovel;
	}


	/**
	 * @return the nomeCliente
	 */
	public String getNomeClienteUsuario(){

		return nomeClienteUsuario;
	}


	/**
	 * @param nomeCliente
	 *            the nomeCliente to set
	 */
	public void setNomeClienteUsuario(String nomeClienteUsuario){

		this.nomeClienteUsuario = nomeClienteUsuario;
	}


	/**
	 * @return the enderecoImovel
	 */
	public String getEnderecoFormatado(){

		return enderecoFormatado;
	}


	/**
	 * @param enderecoImovel
	 *            the enderecoImovel to set
	 */
	public void setEnderecoFormatado(String enderecoFormatado){

		this.enderecoFormatado = enderecoFormatado;
	}


	/**
	 * @return the bairroImovel
	 */
	public String getBairroImovel(){

		return bairroImovel;
	}


	/**
	 * @param bairroImovel
	 *            the bairroImovel to set
	 */
	public void setBairroImovel(String bairroImovel){

		this.bairroImovel = bairroImovel;
	}

	/**
	 * @return the dataEmissao
	 */
	public Date getDataEmissao(){

		return dataEmissao;
	}

	/**
	 * @param dataEmissao
	 *            the dataEmissao to set
	 */
	public void setDataEmissao(Date dataEmissao){

		this.dataEmissao = dataEmissao;
	}

	/**
	 * @return the quatidadeDiasRealizacao
	 */
	public Short getQuatidadeDiasRealizacao(){

		return quatidadeDiasRealizacao;
	}

	/**
	 * @param quatidadeDiasRealizacao
	 *            the quatidadeDiasRealizacao to set
	 */
	public void setQuatidadeDiasRealizacao(Short quatidadeDiasRealizacao){

		this.quatidadeDiasRealizacao = quatidadeDiasRealizacao;
	}

	/**
	 * @return the dataTextoCliente
	 */
	public String getDataTextoCliente(){

		return dataTextoCliente;
	}


	/**
	 * @param dataTextoCliente
	 *            the dataTextoCliente to set
	 */
	public void setDataTextoCliente(String dataTextoCliente){

		this.dataTextoCliente = dataTextoCliente;
	}

	/**
	 * @return the idOS
	 */
	public Integer getIdOSP(){

		return idOSP;
	}

	/**
	 * @param idOS
	 *            the idOS to set
	 */
	public void setIdOSP(Integer idOSP){

		this.idOSP = idOSP;
	}

	/**
	 * @return the numeroHidrometro
	 */
	public String getNumeroHidrometro(){

		return numeroHidrometro;
	}


	/**
	 * @param numeroHidrometro
	 *            the numeroHidrometro to set
	 */
	public void setNumeroHidrometro(String numeroHidrometro){

		this.numeroHidrometro = numeroHidrometro;
	}

	/**
	 * @return the colecaoItemConta
	 */
	public Collection<CobrancaDocumentoItemHelper> getColecaoItemConta(){

		return colecaoItemConta;
	}

	/**
	 * @param colecaoItemConta
	 *            the colecaoItemConta to set
	 */
	public void setColecaoItemConta(Collection<CobrancaDocumentoItemHelper> colecaoItemConta){

		this.colecaoItemConta = colecaoItemConta;
	}

	/**
	 * @return the sequencialImpressao
	 */
	public int getSequencialImpressao(){

		return sequencialImpressao;
	}

	/**
	 * @param sequencialImpressao
	 *            the sequencialImpressao to set
	 */
	public void setSequencialImpressao(int sequencialImpressao){

		this.sequencialImpressao = sequencialImpressao;
	}

	/**
	 * @return the totalContasImpressao
	 */
	public int getTotalContasImpressao(){

		return totalContasImpressao;
	}

	/**
	 * @param totalContasImpressao
	 *            the totalContasImpressao to set
	 */
	public void setTotalContasImpressao(int totalContasImpressao){

		this.totalContasImpressao = totalContasImpressao;
	}

	/**
	 * @return the numeroPagina
	 */
	public Integer getNumeroPagina(){

		return numeroPagina;
	}

	/**
	 * @param numeroPagina
	 *            the numeroPagina to set
	 */
	public void setNumeroPagina(Integer numeroPagina){

		this.numeroPagina = numeroPagina;
	}

	/**
	 * @return the valorDocumento
	 */
	public BigDecimal getValorDocumento(){

		return valorDocumento;
	}

	/**
	 * @param valorDocumento
	 *            the valorDocumento to set
	 */
	public void setValorDocumento(BigDecimal valorDocumento){

		this.valorDocumento = valorDocumento;
	}

	/**
	 * Retorna a mátricula do imóvel formatada.
	 * 
	 * @return
	 */
	public String getMatriculaImovel(){

		String matriculaImovel = null;
		if(Util.isNaoNuloBrancoZero(getIdImovel())){
			matriculaImovel = Util.completarStringZeroEsquerda(Util.retornaMatriculaImovelFormatada(getIdImovel()), 9);
		}
		return matriculaImovel;
	}

	/**
	 * Retorna a data emissão formatada.
	 * 
	 * @return
	 */
	public String getDataTextoClienteFormatada(){

		String dataTextoClienteFormatada = "";
		if(Util.isNaoNuloBrancoZero(getDataTextoCliente())){
			dataTextoClienteFormatada = Util.formatarData(getDataTextoCliente());
		}else{
			if(Util.isNaoNuloBrancoZero(getDataEmissao()) && Util.isNaoNuloBrancoZero(getQuatidadeDiasRealizacao())){
				dataTextoClienteFormatada = Util.somaDiasAData(getDataEmissao(), getQuatidadeDiasRealizacao());
			}
		}
		return dataTextoClienteFormatada;
	}

	/**
	 * Retorna a data emissão formatada.
	 * 
	 * @return
	 */
	public String getDataEmissaoFormatada(){

		String dataEmissaoFormatada = null;
		if(Util.isNaoNuloBrancoZero(getDataEmissao())){
			dataEmissaoFormatada = Util.formatarData(getDataEmissao());
		}
		return dataEmissaoFormatada;
	}

	public String getValorDocumentoFormatado(){

		String valorDocumentoFormatado = null;
		if(Util.isNaoNuloBrancoZero(getValorDocumento())){
			valorDocumentoFormatado = Util.completarStringComValorEsquerda(Util.formataBigDecimal(getValorDocumento(), 2, true), "*", 14);
		}

		return valorDocumentoFormatado;
	}

	public Integer getDocumentoTipoId(){

		return documentoTipoId;
	}

	public void setDocumentoTipoId(Integer documentoTipoId){

		this.documentoTipoId = documentoTipoId;
	}

	public String getRepresentacaoNumericaCodBarra(){

		return representacaoNumericaCodBarra;
	}

	public void setRepresentacaoNumericaCodBarra(String representacaoNumericaCodBarra){

		this.representacaoNumericaCodBarra = representacaoNumericaCodBarra;
	}

	public String getRepresentacaoNumericaCodBarraSemDigito(){

		return representacaoNumericaCodBarraSemDigito;
	}

	public void setRepresentacaoNumericaCodBarraSemDigito(String representacaoNumericaCodBarraSemDigito){

		this.representacaoNumericaCodBarraSemDigito = representacaoNumericaCodBarraSemDigito;
	}

	public Integer getNumeroSequenciaDocumento(){

		return numeroSequenciaDocumento;
	}

	public void setNumeroSequenciaDocumento(Integer numeroSequenciaDocumento){

		this.numeroSequenciaDocumento = numeroSequenciaDocumento;
	}

	public Integer getQuantidadeContasEmDebito(){

		return quantidadeContasEmDebito;
	}

	public void setQuantidadeContasEmDebito(Integer quantidadeContasEmDebito){

		this.quantidadeContasEmDebito = quantidadeContasEmDebito;
	}

	public BigDecimal getValorTotalDebitosCorrigidos(){

		return valorTotalDebitosCorrigidos;
	}

	public void setValorTotalDebitosCorrigidos(BigDecimal valorTotalDebitosCorrigidos){

		this.valorTotalDebitosCorrigidos = valorTotalDebitosCorrigidos;
	}

}
