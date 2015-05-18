
package gcom.gui.faturamento.guiapagamento;

import org.apache.struts.validator.ValidatorActionForm;

/**
 * [UC3027] Filtrar Guia de Pagamento
 * 
 * @author Anderson Italo
 * @date 25/10/2011
 */
public class FiltrarGuiaPagamentoActionForm
				extends ValidatorActionForm {

	private String numeroGuia;

	private String numeroContratoParcelOrgaoPublico;

	private String matriculaImovel;

	private String inscricaoImovel;

	private String codigoClienteResponsavel;

	private String nomeClienteResponsavel;

	private String codigoClienteGuia;

	private String nomeClienteGuia;

	private String indicadorAtualizar;

	private String indicadorPesquisaClienteGuia;

	private String indicadorPesquisaClienteResponsavel;

	private String[] idTipoRelacao;

	private String idLocalidade;

	private String nomeLocalidade;

	private String periodoReferenciaFaturamentoInicial;

	private String periodoReferenciaFaturamentoFinal;

	private String periodoEmissaoInicial;

	private String periodoEmissaoFinal;

	private String periodoVencimentoInicial;

	private String periodoVencimentoFinal;

	private String[] idTipoDocumento;

	private String[] idTipoDebitoDisponiveis;

	private String[] idTipoDebitoSelecionados;

	private String numeroRA;

	private String descricaoRA;

	public String getDescricaoRA(){

		return descricaoRA;
	}

	public void setDescricaoRA(String descricaoRA){

		this.descricaoRA = descricaoRA;
	}

	public String getNumeroRA(){

		return numeroRA;
	}

	public void setNumeroRA(String numeroRA){

		this.numeroRA = numeroRA;
	}

	public String getNumeroGuia(){

		return numeroGuia;
	}

	public void setNumeroGuia(String numeroGuia){

		this.numeroGuia = numeroGuia;
	}

	public String getNumeroContratoParcelOrgaoPublico(){

		return numeroContratoParcelOrgaoPublico;
	}

	public void setNumeroContratoParcelOrgaoPublico(String numeroContratoParcelOrgaoPublico){

		this.numeroContratoParcelOrgaoPublico = numeroContratoParcelOrgaoPublico;
	}

	public String getMatriculaImovel(){

		return matriculaImovel;
	}

	public void setMatriculaImovel(String matriculaImovel){

		this.matriculaImovel = matriculaImovel;
	}

	public String getCodigoClienteResponsavel(){

		return codigoClienteResponsavel;
	}

	public void setCodigoClienteResponsavel(String codigoClienteResponsavel){

		this.codigoClienteResponsavel = codigoClienteResponsavel;
	}

	public String getCodigoClienteGuia(){

		return codigoClienteGuia;
	}

	public void setCodigoClienteGuia(String codigoClienteGuia){

		this.codigoClienteGuia = codigoClienteGuia;
	}

	public String getInscricaoImovel(){

		return inscricaoImovel;
	}

	public void setInscricaoImovel(String inscricaoImovel){

		this.inscricaoImovel = inscricaoImovel;
	}

	public String getNomeClienteResponsavel(){

		return nomeClienteResponsavel;
	}

	public void setNomeClienteResponsavel(String nomeClienteResponsavel){

		this.nomeClienteResponsavel = nomeClienteResponsavel;
	}

	public String getNomeClienteGuia(){

		return nomeClienteGuia;
	}

	public void setNomeClienteGuia(String nomeClienteGuia){

		this.nomeClienteGuia = nomeClienteGuia;
	}

	public String getIndicadorAtualizar(){

		return indicadorAtualizar;
	}

	public void setIndicadorAtualizar(String indicadorAtualizar){

		this.indicadorAtualizar = indicadorAtualizar;
	}

	public String getIndicadorPesquisaClienteGuia(){

		return indicadorPesquisaClienteGuia;
	}

	public void setIndicadorPesquisaClienteGuia(String indicadorPesquisaClienteGuia){

		this.indicadorPesquisaClienteGuia = indicadorPesquisaClienteGuia;
	}

	public String getIndicadorPesquisaClienteResponsavel(){

		return indicadorPesquisaClienteResponsavel;
	}

	public void setIndicadorPesquisaClienteResponsavel(String indicadorPesquisaClienteResponsavel){

		this.indicadorPesquisaClienteResponsavel = indicadorPesquisaClienteResponsavel;
	}

	public String[] getIdTipoRelacao(){

		return idTipoRelacao;
	}

	public void setIdTipoRelacao(String[] idTipoRelacao){

		this.idTipoRelacao = idTipoRelacao;
	}

	public String getIdLocalidade(){

		return idLocalidade;
	}

	public void setIdLocalidade(String idLocalidade){

		this.idLocalidade = idLocalidade;
	}

	public String getNomeLocalidade(){

		return nomeLocalidade;
	}

	public void setNomeLocalidade(String nomeLocalidade){

		this.nomeLocalidade = nomeLocalidade;
	}

	public String getPeriodoReferenciaFaturamentoInicial(){

		return periodoReferenciaFaturamentoInicial;
	}

	public void setPeriodoReferenciaFaturamentoInicial(String periodoReferenciaFaturamentoInicial){

		this.periodoReferenciaFaturamentoInicial = periodoReferenciaFaturamentoInicial;
	}

	public String getPeriodoReferenciaFaturamentoFinal(){

		return periodoReferenciaFaturamentoFinal;
	}

	public void setPeriodoReferenciaFaturamentoFinal(String periodoReferenciaFaturamentoFinal){

		this.periodoReferenciaFaturamentoFinal = periodoReferenciaFaturamentoFinal;
	}

	public String getPeriodoEmissaoInicial(){

		return periodoEmissaoInicial;
	}

	public void setPeriodoEmissaoInicial(String periodoEmissaoInicial){

		this.periodoEmissaoInicial = periodoEmissaoInicial;
	}

	public String getPeriodoEmissaoFinal(){

		return periodoEmissaoFinal;
	}

	public void setPeriodoEmissaoFinal(String periodoEmissaoFinal){

		this.periodoEmissaoFinal = periodoEmissaoFinal;
	}

	public String getPeriodoVencimentoInicial(){

		return periodoVencimentoInicial;
	}

	public void setPeriodoVencimentoInicial(String periodoVencimentoInicial){

		this.periodoVencimentoInicial = periodoVencimentoInicial;
	}

	public String getPeriodoVencimentoFinal(){

		return periodoVencimentoFinal;
	}

	public void setPeriodoVencimentoFinal(String periodoVencimentoFinal){

		this.periodoVencimentoFinal = periodoVencimentoFinal;
	}

	public String[] getIdTipoDocumento(){

		return idTipoDocumento;
	}

	public void setIdTipoDocumento(String[] idTipoDocumento){

		this.idTipoDocumento = idTipoDocumento;
	}

	public String[] getIdTipoDebitoDisponiveis(){

		return idTipoDebitoDisponiveis;
	}

	public void setIdTipoDebitoDisponiveis(String[] idTipoDebitoDisponiveis){

		this.idTipoDebitoDisponiveis = idTipoDebitoDisponiveis;
	}

	public String[] getIdTipoDebitoSelecionados(){

		return idTipoDebitoSelecionados;
	}

	public void setIdTipoDebitoSelecionados(String[] idTipoDebitoSelecionados){

		this.idTipoDebitoSelecionados = idTipoDebitoSelecionados;
	}

}
