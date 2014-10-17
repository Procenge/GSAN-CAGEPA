
package gcom.gui.faturamento.comandosimulacaofaturamento;

import org.apache.struts.validator.ValidatorActionForm;

/**
 * [UC3118] Inserir Comando de Simulação de Faturamento
 * 
 * @author Anderson Italo
 * @date 17/12/2013
 */
public class InserirComandoSimulacaoFaturamentoActionForm
				extends ValidatorActionForm {

	private static final long serialVersionUID = 1L;

	private String idFaturamentoGrupo;

	private String idGerenciaRegional;

	private String idUnidadeNegocio;

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

	private String codigoRotaInicial;

	private String codigoRotaFinal;

	private String descricaoRotaInicial;

	private String descricaoRotaFinal;

	private String loteInicial;

	private String loteFinal;

	private String indicadorCriterioTipoConsumoAgua;

	private String indicadorCriterioTipoConsumoEsgoto;

	private String idConsumoTarifa;

	private String tituloComando;

	public String getIdFaturamentoGrupo(){

		return idFaturamentoGrupo;
	}

	public void setIdFaturamentoGrupo(String idFaturamentoGrupo){

		this.idFaturamentoGrupo = idFaturamentoGrupo;
	}

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

	public String getDescricaoRotaInicial(){

		return descricaoRotaInicial;
	}

	public void setDescricaoRotaInicial(String descricaoRotaInicial){

		this.descricaoRotaInicial = descricaoRotaInicial;
	}

	public String getDescricaoRotaFinal(){

		return descricaoRotaFinal;
	}

	public void setDescricaoRotaFinal(String descricaoRotaFinal){

		this.descricaoRotaFinal = descricaoRotaFinal;
	}

	public String getIndicadorCriterioTipoConsumoAgua(){

		return indicadorCriterioTipoConsumoAgua;
	}

	public void setIndicadorCriterioTipoConsumoAgua(String indicadorCriterioTipoConsumoAgua){

		this.indicadorCriterioTipoConsumoAgua = indicadorCriterioTipoConsumoAgua;
	}

	public String getIndicadorCriterioTipoConsumoEsgoto(){

		return indicadorCriterioTipoConsumoEsgoto;
	}

	public void setIndicadorCriterioTipoConsumoEsgoto(String indicadorCriterioTipoConsumoEsgoto){

		this.indicadorCriterioTipoConsumoEsgoto = indicadorCriterioTipoConsumoEsgoto;
	}

	public String getIdConsumoTarifa(){

		return idConsumoTarifa;
	}

	public void setIdConsumoTarifa(String idConsumoTarifa){

		this.idConsumoTarifa = idConsumoTarifa;
	}

	public String getTituloComando(){

		return tituloComando;
	}

	public void setTituloComando(String tituloComando){

		this.tituloComando = tituloComando;
	}
}
