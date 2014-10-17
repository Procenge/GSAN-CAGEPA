
package gcom.cobranca.bean;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

/**
 * [UC3070] Filtrar Imóvel Cobrança Administrativa
 * 
 * @author Anderson Italo
 * @date 15/09/2012
 */
public class FiltroImovelCobrancaAdministrativaHelper {

	private Integer idComando;

	private Integer[] idsEmpresa;

	private Integer idImovel;

	private Integer idCliente;

	private Integer[] idsGerenciaRegional;

	private Integer[] idsUnidadeNegocio;

	private Integer idLocalidadeInicial;

	private Short codigoSetorComercialInicial;

	private Integer numeroQuadraInicial;

	private Integer idLocalidadeFinal;

	private Short codigoSetorComercialFinal;

	private Integer numeroQuadraFinal;

	private Collection<Integer> idsImoveis;

	private Integer[] idsCategoria;

	private Integer[] idsSubcategoria;

	private Integer[] idsLigacaoAguaSituacao;

	private Integer[] idsLigacaoEsgotoSituacao;

	private BigDecimal valorDebitoInicial;

	private BigDecimal valorDebitoFinal;

	private Date periodoInclusaoInicial;

	private Date periodoInclusaoFinal;

	private Date periodoRetiradaInicial;

	private Date periodoRetiradaFinal;

	private Short indicadorSituacaoCobrancaAdministrativa;

	private Integer[] idsMotivoRetirada;

	public FiltroImovelCobrancaAdministrativaHelper() {

	}

	public Integer getIdComando(){

		return idComando;
	}

	public void setIdComando(Integer idComando){

		this.idComando = idComando;
	}

	public Integer[] getIdsEmpresa(){

		return idsEmpresa;
	}

	public void setIdsEmpresa(Integer[] idsEmpresa){

		this.idsEmpresa = idsEmpresa;
	}

	public Integer getIdImovel(){

		return idImovel;
	}

	public void setIdImovel(Integer idImovel){

		this.idImovel = idImovel;
	}

	public Integer getIdCliente(){

		return idCliente;
	}

	public void setIdCliente(Integer idCliente){

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

	public Integer getIdLocalidadeInicial(){

		return idLocalidadeInicial;
	}

	public void setIdLocalidadeInicial(Integer idLocalidadeInicial){

		this.idLocalidadeInicial = idLocalidadeInicial;
	}

	public Short getCodigoSetorComercialInicial(){

		return codigoSetorComercialInicial;
	}

	public void setCodigoSetorComercialInicial(Short codigoSetorComercialInicial){

		this.codigoSetorComercialInicial = codigoSetorComercialInicial;
	}

	public Integer getNumeroQuadraInicial(){

		return numeroQuadraInicial;
	}

	public void setNumeroQuadraInicial(Integer numeroQuadraInicial){

		this.numeroQuadraInicial = numeroQuadraInicial;
	}

	public Integer getIdLocalidadeFinal(){

		return idLocalidadeFinal;
	}

	public void setIdLocalidadeFinal(Integer idLocalidadeFinal){

		this.idLocalidadeFinal = idLocalidadeFinal;
	}

	public Short getCodigoSetorComercialFinal(){

		return codigoSetorComercialFinal;
	}

	public void setCodigoSetorComercialFinal(Short codigoSetorComercialFinal){

		this.codigoSetorComercialFinal = codigoSetorComercialFinal;
	}

	public Integer getNumeroQuadraFinal(){

		return numeroQuadraFinal;
	}

	public void setNumeroQuadraFinal(Integer numeroQuadraFinal){

		this.numeroQuadraFinal = numeroQuadraFinal;
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

	public BigDecimal getValorDebitoInicial(){

		return valorDebitoInicial;
	}

	public void setValorDebitoInicial(BigDecimal valorDebitoInicial){

		this.valorDebitoInicial = valorDebitoInicial;
	}

	public BigDecimal getValorDebitoFinal(){

		return valorDebitoFinal;
	}

	public void setValorDebitoFinal(BigDecimal valorDebitoFinal){

		this.valorDebitoFinal = valorDebitoFinal;
	}

	public Date getPeriodoInclusaoInicial(){

		return periodoInclusaoInicial;
	}

	public void setPeriodoInclusaoInicial(Date periodoInclusaoInicial){

		this.periodoInclusaoInicial = periodoInclusaoInicial;
	}

	public Date getPeriodoInclusaoFinal(){

		return periodoInclusaoFinal;
	}

	public void setPeriodoInclusaoFinal(Date periodoInclusaoFinal){

		this.periodoInclusaoFinal = periodoInclusaoFinal;
	}

	public Date getPeriodoRetiradaInicial(){

		return periodoRetiradaInicial;
	}

	public void setPeriodoRetiradaInicial(Date periodoRetiradaInicial){

		this.periodoRetiradaInicial = periodoRetiradaInicial;
	}

	public Date getPeriodoRetiradaFinal(){

		return periodoRetiradaFinal;
	}

	public void setPeriodoRetiradaFinal(Date periodoRetiradaFinal){

		this.periodoRetiradaFinal = periodoRetiradaFinal;
	}

	public Short getIndicadorSituacaoCobrancaAdministrativa(){

		return indicadorSituacaoCobrancaAdministrativa;
	}

	public void setIndicadorSituacaoCobrancaAdministrativa(Short indicadorSituacaoCobrancaAdministrativa){

		this.indicadorSituacaoCobrancaAdministrativa = indicadorSituacaoCobrancaAdministrativa;
	}

	public Integer[] getIdsMotivoRetirada(){

		return idsMotivoRetirada;
	}

	public void setIdsMotivoRetirada(Integer[] idsMotivoRetirada){

		this.idsMotivoRetirada = idsMotivoRetirada;
	}

	public Collection<Integer> getIdsImoveis(){

		return idsImoveis;
	}

	public void setIdsImoveis(Collection<Integer> idsImoveis){

		this.idsImoveis = idsImoveis;
	}

}
