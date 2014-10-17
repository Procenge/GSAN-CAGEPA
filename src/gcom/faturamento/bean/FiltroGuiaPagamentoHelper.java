
package gcom.faturamento.bean;

import java.util.Date;

public class FiltroGuiaPagamentoHelper {

	private Integer idGuiaPagamento;

	private Integer numeroContratoParcelOrgaoPublico;

	private Integer idImovel;

	private Integer codigoClienteResponsavel;

	private Integer codigoClienteGuia;

	private String idsClienteRelacaoTipo;

	private Integer idLocalidade;

	private int anoMesReferenciaInicial;

	private int anoMesReferenciaFinal;

	private Date dataEmissaoInicial;

	private Date dataEmissaoFinal;

	private Date dataVencimentoInicial;

	private Date dataVencimentoFinal;

	private String idsDocumentoTipo;

	private String idsDebitoTipo;

	private Integer numeroPagina;

	public Integer getIdGuiaPagamento(){

		return idGuiaPagamento;
	}

	public void setIdGuiaPagamento(Integer idGuiaPagamento){

		this.idGuiaPagamento = idGuiaPagamento;
	}

	public Integer getNumeroContratoParcelOrgaoPublico(){

		return numeroContratoParcelOrgaoPublico;
	}

	public void setNumeroContratoParcelOrgaoPublico(Integer numeroContratoParcelOrgaoPublico){

		this.numeroContratoParcelOrgaoPublico = numeroContratoParcelOrgaoPublico;
	}

	public Integer getIdImovel(){

		return idImovel;
	}

	public void setIdImovel(Integer idImovel){

		this.idImovel = idImovel;
	}

	public Integer getCodigoClienteResponsavel(){

		return codigoClienteResponsavel;
	}

	public void setCodigoClienteResponsavel(Integer codigoClienteResponsavel){

		this.codigoClienteResponsavel = codigoClienteResponsavel;
	}

	public Integer getCodigoClienteGuia(){

		return codigoClienteGuia;
	}

	public void setCodigoClienteGuia(Integer codigoClienteGuia){

		this.codigoClienteGuia = codigoClienteGuia;
	}

	public String getIdsClienteRelacaoTipo(){

		return idsClienteRelacaoTipo;
	}

	public void setIdsClienteRelacaoTipo(String idsClienteRelacaoTipo){

		this.idsClienteRelacaoTipo = idsClienteRelacaoTipo;
	}

	public Integer getIdLocalidade(){

		return idLocalidade;
	}

	public void setIdLocalidade(Integer idLocalidade){

		this.idLocalidade = idLocalidade;
	}

	public int getAnoMesReferenciaInicial(){

		return anoMesReferenciaInicial;
	}

	public void setAnoMesReferenciaInicial(int anoMesReferenciaInicial){

		this.anoMesReferenciaInicial = anoMesReferenciaInicial;
	}

	public int getAnoMesReferenciaFinal(){

		return anoMesReferenciaFinal;
	}

	public void setAnoMesReferenciaFinal(int anoMesReferenciaFinal){

		this.anoMesReferenciaFinal = anoMesReferenciaFinal;
	}

	public Date getDataEmissaoInicial(){

		return dataEmissaoInicial;
	}

	public void setDataEmissaoInicial(Date dataEmissaoInicial){

		this.dataEmissaoInicial = dataEmissaoInicial;
	}

	public Date getDataEmissaoFinal(){

		return dataEmissaoFinal;
	}

	public void setDataEmissaoFinal(Date dataEmissaoFinal){

		this.dataEmissaoFinal = dataEmissaoFinal;
	}

	public Date getDataVencimentoInicial(){

		return dataVencimentoInicial;
	}

	public void setDataVencimentoInicial(Date dataVencimentoInicial){

		this.dataVencimentoInicial = dataVencimentoInicial;
	}

	public Date getDataVencimentoFinal(){

		return dataVencimentoFinal;
	}

	public void setDataVencimentoFinal(Date dataVencimentoFinal){

		this.dataVencimentoFinal = dataVencimentoFinal;
	}

	public String getIdsDocumentoTipo(){

		return idsDocumentoTipo;
	}

	public void setIdsDocumentoTipo(String idsDocumentoTipo){

		this.idsDocumentoTipo = idsDocumentoTipo;
	}

	public String getIdsDebitoTipo(){

		return idsDebitoTipo;
	}

	public void setIdsDebitoTipo(String idsDebitoTipo){

		this.idsDebitoTipo = idsDebitoTipo;
	}

	public Integer getNumeroPagina(){

		return numeroPagina;
	}

	public void setNumeroPagina(Integer numeroPagina){

		this.numeroPagina = numeroPagina;
	}
}
