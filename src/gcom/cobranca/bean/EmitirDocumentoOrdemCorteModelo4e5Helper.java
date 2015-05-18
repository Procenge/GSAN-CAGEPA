
package gcom.cobranca.bean;

import gcom.cadastro.imovel.Imovel;
import gcom.util.Util;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

public class EmitirDocumentoOrdemCorteModelo4e5Helper
				implements Serializable, IEmitirArquivoAvisoOrdemCorteHelper {

	private static final long serialVersionUID = 1L;

	private Integer idCobrancaDocumento;

	private Integer idImovel;

	private Date dataApresentacaoCorte;

	private Integer codigoSetorComercial;

	private Integer numeroQuadra;

	private Short numeroLote;

	private Short numeroSubLote;

	private String nomeClienteUsuario;

	private String numeroHidrometro;

	private String localHistalacaoHidrometro;

	private Integer idOrdemServico;

	private String diametroHidrometro;

	private Date dataEmissao;

	private Date dataEntrega;

	private Collection<CobrancaDocumentoItemHelper> colecaoItemConta;

	private int sequencialImpressao;

	private int totalContasImpressao;

	private Integer idCobrancaAcaoAtividadeComando;

	private Integer numeroPagina;

	private BigDecimal valorDocumento;

	private Integer idLocalidade;

	private String nomeLocalidade;

	private String nomeMunicipio;

	private String enderecoFormatado;

	private String idTipoServico;

	private Integer idClienteUsuario;

	public void setIdClienteUsuario(Integer idClienteUsuario){

		this.idClienteUsuario = idClienteUsuario;
	}

	public Integer getIdClienteUsuario(){

		return idClienteUsuario;
	}

	public EmitirDocumentoOrdemCorteModelo4e5Helper() {

		this.sequencialImpressao = 0;
		this.totalContasImpressao = 0;
	}

	public BigDecimal getValorDocumento(){

		return valorDocumento;
	}

	public void setValorDocumento(BigDecimal valorDocumento){

		this.valorDocumento = valorDocumento;
	}

	public Integer getNumeroPagina(){

		return numeroPagina;
	}

	public void setNumeroPagina(Integer numeroPagina){

		this.numeroPagina = numeroPagina;
	}

	public Date getDataEmissao(){

		return dataEmissao;
	}

	public void setDataEmissao(Date dataEmissao){

		this.dataEmissao = dataEmissao;
	}

	public Date getDataEntrega(){

		return dataEntrega;
	}

	public void setDataEntrega(Date dataEntrega){

		this.dataEntrega = dataEntrega;
	}

	public String getNumeroHidrometro(){

		return numeroHidrometro;
	}

	public String getNumeroHidrometroFormatado(){

		String retorno = "";
		if(Util.isNaoNuloBrancoZero(numeroHidrometro) && !"null".equals(numeroHidrometro)){
			retorno = numeroHidrometro;
		}
		return retorno;
	}

	public void setNumeroHidrometro(String numeroHidrometro){

		this.numeroHidrometro = numeroHidrometro;
	}

	public String getLocalHistalacaoHidrometro(){

		return localHistalacaoHidrometro;
	}

	public String getLocalHistalacaoHidrometroFormatado(){

		String retorno = "";
		if(Util.isNaoNuloBrancoZero(localHistalacaoHidrometro) && !"null".equals(localHistalacaoHidrometro)){
			retorno = localHistalacaoHidrometro;
		}
		return retorno;
	}

	public void setLocalHistalacaoHidrometro(String localHistalacaoHidrometro){

		this.localHistalacaoHidrometro = localHistalacaoHidrometro;
	}

	public Integer getIdOrdemServico(){

		return idOrdemServico;
	}

	public String getIdOrdemServicoFormatado(){

		String retorno = "";
		if(Util.isNaoNuloBrancoZero(idOrdemServico) && !"null".equals(String.valueOf(idOrdemServico))){
			retorno = String.valueOf(idOrdemServico);
		}
		return retorno;
	}

	
	public void setIdOrdemServico(Integer idOrdemServico){

		this.idOrdemServico = idOrdemServico;
	}

	
	public String getDiametroHidrometro(){

		return diametroHidrometro;
	}

	public String getDiametroHidrometroFormatado(){

		String retorno = "";
		if(Util.isNaoNuloBrancoZero(diametroHidrometro) && !"null".equals(diametroHidrometro)){
			retorno = diametroHidrometro;
		}
		return retorno;
	}


	public void setDiametroHidrometro(String diametroHidrometro){

		this.diametroHidrometro = diametroHidrometro;
	}


	public Integer getIdCobrancaDocumento(){

		return idCobrancaDocumento;
	}

	public void setIdCobrancaDocumento(Integer idCobrancaDocumento){

		this.idCobrancaDocumento = idCobrancaDocumento;
	}

	public Integer getIdImovel(){

		return idImovel;
	}

	public void setIdImovel(Integer idImovel){

		this.idImovel = idImovel;
	}

	public Date getDataApresentacaoCorte(){

		return dataApresentacaoCorte;
	}

	public String getDataApresentacaoCorteFormatado(){

		String retorno = "";
		if(Util.isNaoNuloBrancoZero(dataApresentacaoCorte)){
			retorno = Util.formatarData(dataApresentacaoCorte);
		}
		return retorno;

	}

	public void setDataApresentacaoCorte(Date dataApresentacaoCorte){

		this.dataApresentacaoCorte = dataApresentacaoCorte;
	}


	public Integer getCodigoSetorComercial(){

		return codigoSetorComercial;
	}

	public void setCodigoSetorComercial(Integer codigoSetorComercial){

		this.codigoSetorComercial = codigoSetorComercial;
	}

	public Integer getNumeroQuadra(){

		return numeroQuadra;
	}

	public void setNumeroQuadra(Integer numeroQuadra){

		this.numeroQuadra = numeroQuadra;
	}

	public Short getNumeroLote(){

		return numeroLote;
	}

	public void setNumeroLote(Short numeroLote){

		this.numeroLote = numeroLote;
	}

	public String getNomeClienteUsuario(){

		return nomeClienteUsuario;
	}

	public void setNomeClienteUsuario(String nomeClienteUsuario){

		this.nomeClienteUsuario = nomeClienteUsuario;
	}

	public Collection<CobrancaDocumentoItemHelper> getColecaoItemConta(){

		return colecaoItemConta;
	}

	public void setColecaoItemConta(Collection<CobrancaDocumentoItemHelper> colecaoItemConta){

		this.colecaoItemConta = colecaoItemConta;
	}

	public Short getNumeroSubLote(){

		return numeroSubLote;
	}

	public void setNumeroSubLote(Short numeroSubLote){

		this.numeroSubLote = numeroSubLote;
	}

	public int getSequencialImpressao(){

		return sequencialImpressao;
	}

	public void setSequencialImpressao(int sequencialImpressao){

		this.sequencialImpressao = sequencialImpressao;
	}

	public int getTotalContasImpressao(){

		return totalContasImpressao;
	}

	public void setTotalContasImpressao(int totalContasImpressao){

		this.totalContasImpressao = totalContasImpressao;
	}

	public Integer getIdCobrancaAcaoAtividadeComando(){

		return idCobrancaAcaoAtividadeComando;
	}

	public void setIdCobrancaAcaoAtividadeComando(Integer idCobrancaAcaoAtividadeComando){

		this.idCobrancaAcaoAtividadeComando = idCobrancaAcaoAtividadeComando;
	}

	public String getEnderecoFormatado(){

		return enderecoFormatado;
	}

	public void setEnderecoFormatado(String enderecoFormatado){

		this.enderecoFormatado = enderecoFormatado;
	}

	public Integer getIdLocalidade(){

		return idLocalidade;
	}

	public void setIdLocalidade(Integer idLocalidade){

		this.idLocalidade = idLocalidade;
	}

	public String getNomeLocalidade(){

		return nomeLocalidade;
	}

	public void setNomeLocalidade(String nomeLocalidade){

		this.nomeLocalidade = nomeLocalidade;
	}

	public String getNomeMunicipio(){

		return nomeMunicipio;
	}

	public String getNomeMunicipioFormatado(){

		String retorno = "";
		if(Util.isNaoNuloBrancoZero(nomeMunicipio) && !"null".equals(nomeMunicipio)){
			retorno = nomeMunicipio;
		}

		return retorno;
	}

	public void setNomeMunicipio(String nomeMunicipio){

		this.nomeMunicipio = nomeMunicipio;
	}

	public String getInscricao(){

		Imovel imovel = new Imovel();
		imovel.setInscricaoFormatada(idLocalidade, codigoSetorComercial, numeroQuadra, numeroLote, numeroSubLote);
		return imovel.getInscricaoFormatada();

	}

	public String getIdTipoServico(){

		return idTipoServico;
	}

	public void setIdTipoServico(String idTipoServico){

		this.idTipoServico = idTipoServico;
	}

}
