
package gcom.gui.relatorio.atendimentopublico.ordemservico;

import org.apache.struts.validator.ValidatorActionForm;

/**
 * [UC1015] Relatório Resumo de Ordens de Serviço Pendentes
 * 
 * @author Anderson Italo
 * @date 06/06/2011
 */
public class GerarRelatorioResumoOrdensServicoPendentesActionForm
				extends ValidatorActionForm {

	private static final long serialVersionUID = 1L;

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

	private String[] tipoServico;

	private String[] tipoServicoSelecionados;

	String periodoGeracaoInicial;

	String periodoGeracaoFinal;

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

	public String[] getTipoServico(){

		return tipoServico;
	}

	public void setTipoServico(String[] tipoServico){

		this.tipoServico = tipoServico;
	}

	public String[] getTipoServicoSelecionados(){

		return tipoServicoSelecionados;
	}

	public void setTipoServicoSelecionados(String[] tipoServicoSelecionados){

		this.tipoServicoSelecionados = tipoServicoSelecionados;
	}

	public String getPeriodoGeracaoInicial(){

		return periodoGeracaoInicial;
	}

	public void setPeriodoGeracaoInicial(String periodoGeracaoInicial){

		this.periodoGeracaoInicial = periodoGeracaoInicial;
	}

	public String getPeriodoGeracaoFinal(){

		return periodoGeracaoFinal;
	}

	public void setPeriodoGeracaoFinal(String periodoGeracaoFinal){

		this.periodoGeracaoFinal = periodoGeracaoFinal;
	}

}
